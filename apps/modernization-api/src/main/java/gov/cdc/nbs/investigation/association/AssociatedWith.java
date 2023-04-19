package gov.cdc.nbs.investigation.association;

public record AssociatedWith(
    long id,
    String local,
    String condition
) {
}
