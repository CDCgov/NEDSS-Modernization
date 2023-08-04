package gov.cdc.nbs.patient.documentsrequiringreview;

import java.time.Instant;
import java.util.List;

public record DocumentRequiringReview(
        Long id,
        String localId,
        String type,
        Instant eventDate,
        Instant dateReceived,
        boolean isElectronic,
        boolean isUpdate,
        List<FacilityProvider> facilityProviders,
        List<Description> descriptions) {

    public record FacilityProvider(
            String title,
            String name) {
    }

    public record Description(
            String title,
            String value) {
    }

}
