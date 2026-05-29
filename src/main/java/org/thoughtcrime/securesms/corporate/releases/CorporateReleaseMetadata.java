package org.thoughtcrime.securesms.corporate.releases;

public final class CorporateReleaseMetadata {
  public final String versionName;
  public final int versionCode;
  public final String channel;
  public final String sha256;
  public final boolean deprecated;
  public final boolean blocked;

  private CorporateReleaseMetadata(
      String versionName,
      int versionCode,
      String channel,
      String sha256,
      boolean deprecated,
      boolean blocked) {
    this.versionName = versionName;
    this.versionCode = versionCode;
    this.channel = channel;
    this.sha256 = sha256;
    this.deprecated = deprecated;
    this.blocked = blocked;
  }

  public static CorporateReleaseMetadata localDebug(String versionName, int versionCode) {
    return new CorporateReleaseMetadata(
        versionName, versionCode, "local-debug", "unavailable-local-debug", false, false);
  }
}
