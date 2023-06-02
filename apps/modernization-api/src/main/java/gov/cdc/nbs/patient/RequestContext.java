package gov.cdc.nbs.patient;

import java.time.Instant;

public record RequestContext(long requestedBy, Instant requestedAt) {
}
