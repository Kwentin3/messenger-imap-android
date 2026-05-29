package org.thoughtcrime.securesms.corporate;

import android.os.Bundle;
import android.content.Intent;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import org.thoughtcrime.securesms.BaseActionBarActivity;
import org.thoughtcrime.securesms.ConnectivityActivity;
import org.thoughtcrime.securesms.R;
import org.thoughtcrime.securesms.connect.DcHelper;
import org.thoughtcrime.securesms.corporate.directory.CorporateDirectoryFixtures;
import org.thoughtcrime.securesms.corporate.directory.CorporateDirectoryManifest;
import org.thoughtcrime.securesms.corporate.directory.CorporateDirectoryPrincipalType;
import org.thoughtcrime.securesms.corporate.directory.CorporateDirectorySnapshot;
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
