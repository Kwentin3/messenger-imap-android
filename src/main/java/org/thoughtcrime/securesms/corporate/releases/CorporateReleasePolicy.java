package org.thoughtcrime.securesms.corporate.releases;

public final class CorporateReleasePolicy {
  private CorporateReleasePolicy() {}

  public static String status(CorporateReleaseMetadata metadata) {
    if (metadata.blocked) {
      return "blocked";
    }
    if (metadata.deprecated) {
      return "deprecated";
    }
    return "current";
  }
}
