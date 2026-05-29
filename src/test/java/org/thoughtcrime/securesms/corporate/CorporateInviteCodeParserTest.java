package org.thoughtcrime.securesms.corporate;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class CorporateInviteCodeParserTest {
  @Test
  public void parseReturnsEmptyForMissingCode() {
    assertThat(CorporateInviteCodeParser.parse(null)).isEqualTo(CorporateInvitePlaceholderState.EMPTY);
    assertThat(CorporateInviteCodeParser.parse("   ")).isEqualTo(CorporateInvitePlaceholderState.EMPTY);
  }

  @Test
  public void parseRecognizesInternalInvitePrefixes() {
    assertThat(CorporateInviteCodeParser.parse("INT-TEST-001"))
        .isEqualTo(CorporateInvitePlaceholderState.INTERNAL_PLACEHOLDER);
    assertThat(CorporateInviteCodeParser.parse(" org-test-001 "))
        .isEqualTo(CorporateInvitePlaceholderState.INTERNAL_PLACEHOLDER);
  }

  @Test
  public void parseRecognizesExternalInvitePrefixes() {
    assertThat(CorporateInviteCodeParser.parse("EXT-TEST-001"))
        .isEqualTo(CorporateInvitePlaceholderState.EXTERNAL_PLACEHOLDER);
    assertThat(CorporateInviteCodeParser.parse(" guest-test-001 "))
        .isEqualTo(CorporateInvitePlaceholderState.EXTERNAL_PLACEHOLDER);
  }

  @Test
  public void parseRejectsUnknownPrefix() {
    assertThat(CorporateInviteCodeParser.parse("BADCODE"))
        .isEqualTo(CorporateInvitePlaceholderState.INVALID_PLACEHOLDER);
  }
}
