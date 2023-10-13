package gov.cdc.nbs.testing.authorization;

import java.time.Instant;

public record AuthenticationSupportSettings(long createdBy, Instant createdOn) {
}
