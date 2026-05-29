package org.thoughtcrime.securesms.corporate;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import org.thoughtcrime.securesms.BaseActionBarActivity;
import org.thoughtcrime.securesms.R;
import org.thoughtcrime.securesms.util.ViewUtil;

public class CorporateOnboardingActivity extends BaseActionBarActivity {
  private EditText inviteCodeInput;
  private TextView inviteStatusText;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.corporate_onboarding_activity);

    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      getSupportActionBar().setTitle(R.string.corporate_onboarding_title);
    }

    ViewUtil.applyWindowInsets(findViewById(R.id.content_container));

    inviteCodeInput = findViewById(R.id.corporate_invite_code_input);
    inviteStatusText = findViewById(R.id.corporate_invite_status_text);
    Button checkInviteButton = findViewById(R.id.corporate_check_invite_button);

    renderInviteState(CorporateInvitePlaceholderState.EMPTY);
    checkInviteButton.setOnClickListener((v) -> checkInviteCode());
    inviteCodeInput.setOnEditorActionListener(
        (v, actionId, event) -> {
          if (actionId == EditorInfo.IME_ACTION_DONE) {
            checkInviteCode();
            return true;
          }
          return false;
        });
  }

  private void checkInviteCode() {
    CorporateInvitePlaceholderState state =
        CorporateInviteCodeParser.parse(inviteCodeInput.getText().toString());
    inviteCodeInput.getText().clear();
    renderInviteState(state);
  }

  private void renderInviteState(CorporateInvitePlaceholderState state) {
    switch (state) {
      case INTERNAL_PLACEHOLDER:
        inviteStatusText.setText(R.string.corporate_invite_status_internal);
        break;
      case EXTERNAL_PLACEHOLDER:
        inviteStatusText.setText(R.string.corporate_invite_status_external);
        break;
      case INVALID_PLACEHOLDER:
        inviteStatusText.setText(R.string.corporate_invite_status_invalid);
        break;
      case EMPTY:
      default:
        inviteStatusText.setText(R.string.corporate_invite_status_empty);
        break;
    }
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
