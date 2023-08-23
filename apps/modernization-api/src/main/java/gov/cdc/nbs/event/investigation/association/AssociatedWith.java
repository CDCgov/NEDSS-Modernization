package gov.cdc.nbs.event.investigation.association;

public record AssociatedWith(
        long id,
        String local,
        String condition) {
}
