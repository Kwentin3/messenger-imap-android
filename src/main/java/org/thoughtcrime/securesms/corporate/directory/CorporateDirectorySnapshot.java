package org.thoughtcrime.securesms.corporate.directory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public final class CorporateDirectorySnapshot {
  public final String organizationId;
  public final String workspaceId;
  public final long directoryVersion;
  public final List<CorporateDirectoryEntry> entries;

  public CorporateDirectorySnapshot(
      String organizationId,
      String workspaceId,
      long directoryVersion,
      List<CorporateDirectoryEntry> entries) {
    this.organizationId = organizationId;
    this.workspaceId = workspaceId;
    this.directoryVersion = directoryVersion;
    this.entries = Collections.unmodifiableList(new ArrayList<>(entries));
  }

  public int count(CorporateDirectoryPrincipalType type) {
    int count = 0;
    for (CorporateDirectoryEntry entry : entries) {
      if (entry.principalType == type) {
        count++;
      }
    }
    return count;
  }

  public List<CorporateDirectoryEntry> visibleEntriesFor(CorporateDirectoryPrincipalType viewerType) {
    if (viewerType == CorporateDirectoryPrincipalType.INTERNAL_MEMBER) {
      return entries;
    }

    List<CorporateDirectoryEntry> visibleEntries = new ArrayList<>();
    for (CorporateDirectoryEntry entry : entries) {
      if (entry.principalType == CorporateDirectoryPrincipalType.EXTERNAL_CONTACT) {
        visibleEntries.add(entry);
      }
    }
    return Collections.unmodifiableList(visibleEntries);
  }

  public String toCanonicalPayload() {
    List<CorporateDirectoryEntry> sortedEntries = new ArrayList<>(entries);
    sortedEntries.sort(Comparator.comparing(entry -> entry.principalId));

    StringBuilder payload = new StringBuilder();
    payload.append(organizationId).append('\n');
    payload.append(workspaceId).append('\n');
    payload.append(directoryVersion).append('\n');
    for (CorporateDirectoryEntry entry : sortedEntries) {
      payload
          .append(entry.principalId)
          .append('|')
          .append(entry.principalType.name())
          .append('|')
          .append(entry.displayName)
          .append('|')
          .append(entry.email)
          .append('|')
          .append(entry.status)
          .append('\n');
    }
    return payload.toString();
  }
}
