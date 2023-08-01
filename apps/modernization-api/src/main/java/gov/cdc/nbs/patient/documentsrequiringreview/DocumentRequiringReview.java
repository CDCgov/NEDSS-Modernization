package gov.cdc.nbs.patient.documentsrequiringreview;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
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

    DocumentRequiringReview(
            Long id,
            String localId,
            String type,
            Instant eventDate,
            Instant dateReceived,
            boolean isElectronic) {
        this(
                id,
                localId,
                type,
                eventDate,
                dateReceived,
                isElectronic,
                false,
                new ArrayList<>(),
                new ArrayList<>());
    }

    DocumentRequiringReview(Long id,
            String localId,
            String type,
            Instant eventDate,
            Instant dateReceived,
            boolean isUpdate,
            String facilityName,
            String condition) {
        this(
                id,
                localId,
                type,
                eventDate,
                dateReceived,
                false,
                isUpdate,
                Arrays.asList(new FacilityProvider("Sending Facility", facilityName)),
                Arrays.asList(new Description(condition, "")));
    }

    public record FacilityProvider(
            String title,
            String name) {
    }

    public record Description(
            String title,
            String value) {
    }

}
