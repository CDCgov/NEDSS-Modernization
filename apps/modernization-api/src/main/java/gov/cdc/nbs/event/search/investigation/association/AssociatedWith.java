package gov.cdc.nbs.event.search.investigation.association;

public record AssociatedWith(
        long id,
        String local,
        String condition) {
}
