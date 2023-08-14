package gov.cdc.nbs.patient.documentsrequiringreview;

import java.time.Instant;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public record DocumentRequiringReview(
        Long id,
        String localId,
        String type,
        Instant eventDate,
        Instant dateReceived,
        boolean isElectronic,
        boolean isUpdate,
        FacilityProviders facilityProviders,
        List<Description> descriptions) {


    public record ReportingFacility(String name) {
    }
    public record OrderingProvider(String name) {
    }
    public record SendingFacility(String name) {
    }

    public record Description(
            String title,
            String value) {
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FacilityProviders {
        // These need to be settable after the intial DocumentRequiringReview creation
        private ReportingFacility reportingFacility;
        private OrderingProvider orderingProvider;
        private SendingFacility sendingFacility;

        public FacilityProviders(String sendingFacility) {
            this.sendingFacility = new SendingFacility(sendingFacility);
        }
    }
}
