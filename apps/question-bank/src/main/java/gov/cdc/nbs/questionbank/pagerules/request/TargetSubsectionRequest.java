package gov.cdc.nbs.questionbank.pagerules.request;

import java.util.Collection;

public record TargetSubsectionRequest(int orderNbr, Collection<String> targetSubsections) {}
