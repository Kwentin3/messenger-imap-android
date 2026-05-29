package org.thoughtcrime.securesms.corporate.directory;

public final class CorporateDirectoryManifest {
  public final String organizationId;
  public final String workspaceId;
  public final long directoryVersion;
  public final String directoryHash;
  public final CorporateDirectoryState state;

  public CorporateDirectoryManifest(
      String organizationId,
      String workspaceId,
      long directoryVersion,
      String directoryHash,
      CorporateDirectoryState state) {
    this.organizationId = organizationId;
    this.workspaceId = workspaceId;
    this.directoryVersion = directoryVersion;
    this.directoryHash = directoryHash;
    this.state = state;
  }

  public boolean matches(CorporateDirectorySnapshot snapshot) {
    return organizationId.equals(snapshot.organizationId)
        && workspaceId.equals(snapshot.workspaceId)
        && directoryVersion == snapshot.directoryVersion
        && directoryHash.equals(CorporateDirectoryHasher.sha256(snapshot.toCanonicalPayload()));
  }
}
