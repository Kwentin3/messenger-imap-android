package org.thoughtcrime.securesms.corporate.invite;

import android.net.Uri;
import java.util.List;
import java.util.Locale;

public final class CorporateInviteParser {
  private static final String SCHEME = "messenger-imap";
  private static final String HOST = "invite";

  private CorporateInviteParser() {}

  public static CorporateInviteRoute parse(Uri uri) {
    if (uri == null || !SCHEME.equalsIgnoreCase(uri.getScheme()) || !HOST.equalsIgnoreCase(uri.getHost())) {
      return CorporateInviteRoute.empty();
    }

    String rawKind = uri.getQueryParameter("kind");
    List<String> pathSegments = uri.getPathSegments();
    if ((rawKind == null || rawKind.isEmpty()) && !pathSegments.isEmpty()) {
      rawKind = pathSegments.get(0);
    }

    String rawCode = uri.getQueryParameter("code");
    if (rawCode == null || rawCode.isEmpty()) {
      rawCode = uri.getQueryParameter("token");
    }
    if ((rawCode == null || rawCode.isEmpty()) && pathSegments.size() > 1) {
      rawCode = pathSegments.get(1);
    }

    return CorporateInviteRoute.resolved(parseKind(rawKind), redact(rawCode));
  }

  public static CorporateInviteRoute fromFallbackCode(String rawCode) {
    if (rawCode == null || rawCode.trim().isEmpty()) {
      return CorporateInviteRoute.empty();
    }
    return CorporateInviteRoute.resolved(CorporateInviteKind.UNKNOWN, redact(rawCode.trim()));
  }

  private static CorporateInviteKind parseKind(String rawKind) {
    if (rawKind == null) {
      return CorporateInviteKind.UNKNOWN;
    }

    switch (rawKind.toLowerCase(Locale.US)) {
      case "internal":
      case "employee":
        return CorporateInviteKind.INTERNAL;
      case "external":
      case "guest":
        return CorporateInviteKind.EXTERNAL;
      default:
        return CorporateInviteKind.UNKNOWN;
    }
  }

  private static String redact(String rawCode) {
    if (rawCode == null || rawCode.isEmpty()) {
      return "";
    }
    if (rawCode.length() <= 4) {
      return "****";
    }
    return rawCode.substring(0, 2) + "..." + rawCode.substring(rawCode.length() - 2);
  }
}
