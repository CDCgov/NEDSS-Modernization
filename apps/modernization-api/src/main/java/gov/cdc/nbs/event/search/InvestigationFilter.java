package gov.cdc.nbs.event.search;

import gov.cdc.nbs.message.enums.PregnancyStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public final class InvestigationFilter implements EventFilter {

    private static final String UNASSIGNED = "UNASSIGNED";

    private Long patientId;
    private List<String> conditions;
    private List<String> programAreas;
    private List<Long> jurisdictions;
    private PregnancyStatus pregnancyStatus;
    private InvestigationEventId eventId;
    private EventDate eventDate;
    private Long createdBy;
    private Long lastUpdatedBy;
    private ProviderFacilitySearch providerFacilitySearch;
    private Long investigatorId;
    private InvestigationStatus investigationStatus;
    private List<String> outbreakNames;
    private List<CaseStatus> caseStatuses = new ArrayList<>();
    private List<NotificationStatus> notificationStatuses = new ArrayList<>();
    private List<ProcessingStatus> processingStatuses = new ArrayList<>();

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EventDate {
        private EventDateType type;
        private LocalDate from;
        private LocalDate to;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class InvestigationEventId {
        private IdType investigationEventType;
        private String id;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProviderFacilitySearch {
        private ReportingEntityType entityType;
        private Long id;
    }

    public enum IdType {
        ABCS_CASE_ID,
        CITY_COUNTY_CASE_ID,
        INVESTIGATION_ID,
        NOTIFICATION_ID,
        STATE_CASE_ID
    }

    public enum ReportingEntityType {
        FACILITY,
        PROVIDER
    }

    public enum ProcessingStatus {
        UNASSIGNED(InvestigationFilter.UNASSIGNED),
        AWAITING_INTERVIEW("AI"),
        CLOSED_CASE("CC"),
        FIELD_FOLLOW_UP("FF"),
        NO_FOLLOW_UP("NF"),
        OPEN_CASE("OC"),
        SURVEILLANCE_FOLLOW_UP("SF");

        private final String value;

        ProcessingStatus(String value) {
            this.value = value;
        }

        public String value() {
            return value;
        }
    }

    public enum NotificationStatus {
        UNASSIGNED(InvestigationFilter.UNASSIGNED),
        APPROVED("APPROVED"),
        COMPLETED("COMPLETED"),
        MSG_FAIL("MSG_FAIL"),
        PENDING_APPROVAL("PEND_APPR"),
        REJECTED("REJECTED");

        private final String value;

        NotificationStatus(final String value) {
            this.value = value;
        }

        public String value() {
            return value;
        }
    }

    public enum CaseStatus {
        UNASSIGNED(InvestigationFilter.UNASSIGNED),
        CONFIRMED("C"),
        NOT_A_CASE("N"),
        PROBABLE("P"),
        SUSPECT("S"),
        UNKNOWN("U");

        private final String value;

        CaseStatus(final String value) {
            this.value = value;
        }

        public String value() {
            return value;
        }
    }

    public enum EventDateType {
        DATE_OF_REPORT,
        INVESTIGATION_CLOSED_DATE,
        INVESTIGATION_CREATE_DATE,
        INVESTIGATION_START_DATE,
        LAST_UPDATE_DATE,
        NOTIFICATION_CREATE_DATE
    }

    public enum InvestigationStatus {
        OPEN,
        CLOSED
    }
}
