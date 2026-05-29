package org.thoughtcrime.securesms.corporate.diagnostics;

public final class CorporateSupportDiagnosticsSummary {
  private CorporateSupportDiagnosticsSummary() {}

  public static String redactedSummary(boolean accountConfigured, boolean transportCheckAvailable) {
    return "Support summary: accountConfigured="
        + accountConfigured
        + ", transportCheckAvailable="
        + transportCheckAvailable
        + ", rawLogsIncluded=false";
  }
}
