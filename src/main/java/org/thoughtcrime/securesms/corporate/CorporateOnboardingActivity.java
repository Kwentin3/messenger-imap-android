package org.thoughtcrime.securesms.corporate;

import android.os.Bundle;
import android.content.Intent;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import org.thoughtcrime.securesms.BaseActionBarActivity;
import org.thoughtcrime.securesms.BuildConfig;
import org.thoughtcrime.securesms.ConnectivityActivity;
import org.thoughtcrime.securesms.R;
import org.thoughtcrime.securesms.connect.DcHelper;
import org.thoughtcrime.securesms.corporate.diagnostics.CorporateSupportDiagnosticsSummary;
import org.thoughtcrime.securesms.corporate.directory.CorporateDirectoryFixtures;
import org.thoughtcrime.securesms.corporate.directory.CorporateDirectoryManifest;
import org.thoughtcrime.securesms.corporate.directory.CorporateDirectoryPrincipalType;
import org.thoughtcrime.securesms.corporate.directory.CorporateDirectorySnapshot;
import org.thoughtcrime.securesms.corporate.external.CorporateExternalContactPolicy;
import org.thoughtcrime.securesms.corporate.invite.CorporateInviteKind;
import org.thoughtcrime.securesms.corporate.invite.CorporateInviteParser;
import org.thoughtcrime.securesms.corporate.invite.CorporateInviteRoute;
import org.thoughtcrime.securesms.corporate.releases.CorporateReleaseMetadata;
import org.thoughtcrime.securesms.corporate.releases.CorporateReleasePolicy;
import org.thoughtcrime.securesms.util.ViewUtil;

public class CorporateOnboardingActivity extends BaseActionBarActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.corporate_onboarding_activity);

    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      getSupportActionBar().setTitle(R.string.corporate_onboarding_title);
    }

    ViewUtil.applyWindowInsets(findViewById(R.id.content_container));

    TextView providerStatus = findViewById(R.id.corporate_provider_status);
    providerStatus.setText(
        CorporateProviderPolicy.allowsCustomProvider()
            ? R.string.corporate_onboarding_status_provider_custom_allowed
            : R.string.corporate_onboarding_status_provider_managed_only);

    Button transportCheckButton = findViewById(R.id.corporate_transport_check_button);
    boolean configured = DcHelper.isConfigured(getApplicationContext());
    transportCheckButton.setEnabled(configured);
    transportCheckButton.setText(
        configured
            ? R.string.corporate_transport_check_open
            : R.string.corporate_transport_check_requires_account);
    transportCheckButton.setOnClickListener(
        v -> startActivity(new Intent(this, ConnectivityActivity.class)));

    TextView diagnosticsStatus = findViewById(R.id.corporate_diagnostics_status);
    diagnosticsStatus.setText(
        CorporateSupportDiagnosticsSummary.redactedSummary(configured, configured));

    CorporateDirectorySnapshot snapshot = CorporateDirectoryFixtures.sampleSnapshot();
    CorporateDirectoryManifest manifest = CorporateDirectoryFixtures.sampleManifest(snapshot);
    TextView directoryStatus = findViewById(R.id.corporate_directory_status);
    directoryStatus.setText(
        manifest.matches(snapshot)
            ? getString(
                R.string.corporate_onboarding_status_directory_verified,
                snapshot.count(CorporateDirectoryPrincipalType.INTERNAL_MEMBER),
                snapshot.count(CorporateDirectoryPrincipalType.EXTERNAL_CONTACT))
            : getString(R.string.corporate_onboarding_status_directory_hash_mismatch));

    TextView externalContactStatus = findViewById(R.id.corporate_external_contact_status);
    externalContactStatus.setText(
        getString(
            R.string.corporate_external_contact_status,
            CorporateExternalContactPolicy.badgeFor(snapshot.entries.get(1)),
            snapshot.visibleEntriesFor(CorporateDirectoryPrincipalType.EXTERNAL_CONTACT).size()));

    CorporateReleaseMetadata releaseMetadata =
        CorporateReleaseMetadata.localDebug(BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE);
    TextView releaseStatus = findViewById(R.id.corporate_release_status);
    releaseStatus.setText(
        getString(
            R.string.corporate_release_status,
            releaseMetadata.versionName,
            releaseMetadata.channel,
            CorporateReleasePolicy.status(releaseMetadata),
            releaseMetadata.sha256));

    EditText fallbackCodeInput = findViewById(R.id.corporate_fallback_code_input);
    Button applyFallbackCodeButton = findViewById(R.id.corporate_apply_fallback_code_button);
    applyFallbackCodeButton.setOnClickListener(
        v -> applyInviteRoute(CorporateInviteParser.fromFallbackCode(fallbackCodeInput.getText().toString())));

    applyInviteRoute(CorporateInviteParser.parse(getIntent().getData()));
  }

  @Override
  protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);
    setIntent(intent);
    applyInviteRoute(CorporateInviteParser.parse(intent.getData()));
  }

  private void applyInviteRoute(CorporateInviteRoute route) {
    TextView inviteStatus = findViewById(R.id.corporate_invite_status);
    if (!route.tokenPresent) {
      inviteStatus.setText(R.string.corporate_onboarding_status_invite);
      return;
    }

    int inviteKindString;
    if (route.kind == CorporateInviteKind.INTERNAL) {
      inviteKindString = R.string.corporate_invite_kind_internal;
    } else if (route.kind == CorporateInviteKind.EXTERNAL) {
      inviteKindString = R.string.corporate_invite_kind_external;
    } else {
      inviteKindString = R.string.corporate_invite_kind_unknown;
    }

    inviteStatus.setText(
        getString(
            R.string.corporate_onboarding_status_invite_resolved,
            getString(inviteKindString),
            route.redactedCode));
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      finish();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }
}
