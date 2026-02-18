package gov.cdc.nbs.questionbank;

import java.time.Instant;

public record RequestContext(long requestedBy, Instant requestedAt) {}
