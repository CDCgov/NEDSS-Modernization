package gov.cdc.nbs.patient.profile.association;

public record AssociatedWith(
        long id,
        String local,
        String condition) {
}
