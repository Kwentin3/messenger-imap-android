package org.thoughtcrime.securesms.corporate;

import java.util.Locale;

public final class CorporateInviteCodeParser {
  private CorporateInviteCodeParser() {}

  public static CorporateInvitePlaceholderState parse(String rawCode) {
    if (rawCode == null) {
      return CorporateInvitePlaceholderState.EMPTY;
    }

    String normalized = rawCode.trim().toUpperCase(Locale.ROOT);
    if (normalized.isEmpty()) {
      return CorporateInvitePlaceholderState.EMPTY;
    }
    if (normalized.startsWith("INT-") || normalized.startsWith("ORG-")) {
      return CorporateInvitePlaceholderState.INTERNAL_PLACEHOLDER;
    }
    if (normalized.startsWith("EXT-") || normalized.startsWith("GUEST-")) {
      return CorporateInvitePlaceholderState.EXTERNAL_PLACEHOLDER;
    }
    return CorporateInvitePlaceholderState.INVALID_PLACEHOLDER;
  }
}
