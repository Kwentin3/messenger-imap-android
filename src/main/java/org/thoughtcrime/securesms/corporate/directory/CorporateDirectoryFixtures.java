package org.thoughtcrime.securesms.corporate.directory;

import java.util.Arrays;

public final class CorporateDirectoryFixtures {
  private CorporateDirectoryFixtures() {}

  public static CorporateDirectorySnapshot sampleSnapshot() {
    return new CorporateDirectorySnapshot(
        "org-demo",
        "workspace-demo",
        1,
        Arrays.asList(
            new CorporateDirectoryEntry(
                "member-001",
                CorporateDirectoryPrincipalType.INTERNAL_MEMBER,
                "Demo Employee",
                "employee@example.com",
                "active"),
            new CorporateDirectoryEntry(
                "external-001",
                CorporateDirectoryPrincipalType.EXTERNAL_CONTACT,
                "Demo External Contact",
                "external@example.net",
                "active")));
  }

  public static CorporateDirectoryManifest sampleManifest(CorporateDirectorySnapshot snapshot) {
    return new CorporateDirectoryManifest(
        snapshot.organizationId,
        snapshot.workspaceId,
        snapshot.directoryVersion,
        CorporateDirectoryHasher.sha256(snapshot.toCanonicalPayload()),
        CorporateDirectoryState.FRESH);
  }
}
