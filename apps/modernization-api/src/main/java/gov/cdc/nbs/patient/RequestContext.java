package gov.cdc.nbs.patient;

import java.time.LocalDateTime;

public record RequestContext(long requestedBy, LocalDateTime requestedAt) {}
