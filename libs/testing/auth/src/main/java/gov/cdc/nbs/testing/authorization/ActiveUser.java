package gov.cdc.nbs.testing.authorization;

import gov.cdc.nbs.authentication.NBSToken;

public record ActiveUser(long id, String username, long nedssEntry, NBSToken token) {
  ActiveUser(long id, String username, long nedssEntry) {
    this(id, username, nedssEntry, null);
  }
}
