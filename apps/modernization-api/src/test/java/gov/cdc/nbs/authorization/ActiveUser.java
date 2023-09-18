package gov.cdc.nbs.authorization;

import gov.cdc.nbs.authentication.NBSToken;

public record ActiveUser(long id, String username, NBSToken token) {
}
