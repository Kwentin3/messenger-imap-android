package org.thoughtcrime.securesms.corporate;

public final class CorporateProviderPolicy {
  public static final String MODE_PROVIDER_AGNOSTIC = "provider_agnostic";

  private CorporateProviderPolicy() {}

  public static boolean allowsCustomProvider() {
    return true;
  }
}
