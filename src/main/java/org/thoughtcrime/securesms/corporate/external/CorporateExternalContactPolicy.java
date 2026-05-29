package org.thoughtcrime.securesms.corporate.external;

import org.thoughtcrime.securesms.corporate.directory.CorporateDirectoryEntry;
import org.thoughtcrime.securesms.corporate.directory.CorporateDirectoryPrincipalType;

public final class CorporateExternalContactPolicy {
  private CorporateExternalContactPolicy() {}

  public static boolean isExternal(CorporateDirectoryEntry entry) {
    return entry.principalType == CorporateDirectoryPrincipalType.EXTERNAL_CONTACT;
  }

  public static String badgeFor(CorporateDirectoryEntry entry) {
    return isExternal(entry) ? "External" : "Internal";
  }
}
