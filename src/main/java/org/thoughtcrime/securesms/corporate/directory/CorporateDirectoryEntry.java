package org.thoughtcrime.securesms.corporate.directory;

public final class CorporateDirectoryEntry {
  public final String principalId;
  public final CorporateDirectoryPrincipalType principalType;
  public final String displayName;
  public final String email;
  public final String status;

  public CorporateDirectoryEntry(
      String principalId,
      CorporateDirectoryPrincipalType principalType,
      String displayName,
      String email,
      String status) {
    this.principalId = principalId;
    this.principalType = principalType;
    this.displayName = displayName;
    this.email = email;
    this.status = status;
  }
}
