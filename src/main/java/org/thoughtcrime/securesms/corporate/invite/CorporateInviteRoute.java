package org.thoughtcrime.securesms.corporate.invite;

public final class CorporateInviteRoute {
  public final CorporateInviteKind kind;
  public final String redactedCode;
  public final boolean tokenPresent;

  private CorporateInviteRoute(
      CorporateInviteKind kind, String redactedCode, boolean tokenPresent) {
    this.kind = kind;
    this.redactedCode = redactedCode;
    this.tokenPresent = tokenPresent;
  }

  public static CorporateInviteRoute empty() {
    return new CorporateInviteRoute(CorporateInviteKind.UNKNOWN, "", false);
  }

  public static CorporateInviteRoute resolved(CorporateInviteKind kind, String redactedCode) {
    return new CorporateInviteRoute(kind, redactedCode, redactedCode != null && !redactedCode.isEmpty());
  }
}
