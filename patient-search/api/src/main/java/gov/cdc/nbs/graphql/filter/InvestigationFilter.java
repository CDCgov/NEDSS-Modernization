package gov.cdc.nbs.graphql.filter;

import java.time.Instant;
import java.util.List;

import gov.cdc.nbs.entity.enums.PregnancyStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvestigationFilter {
    private List<String> conditions;
    private List<String> programAreas;
    private List<Long> jurisdictions;
    private PregnancyStatus pregnancyStatus;
    private IdType eventIdType;
    private String eventId;
    private InvestigationEventDateSearch eventDateSearch;
    private Long createdBy;
    private Long lastUpdatedBy;
    private ProviderFacilitySearch providerFacilitySearch;
    private Long investigatorId;
    private InvestigationStatus investigationStatus;
    private List<String> outbreakNames;
    private CaseStatuses caseStatuses;
    private NotificationStatuses notificationStatuses;
    private ProcessingStatuses processingStatuses;

    @Getter
    @Setter
    public static class CaseStatuses {
        private boolean includeUnassigned;
        private List<CaseStatus> statusList;
    }

    @Getter
    @Setter
    public static class NotificationStatuses {
        private boolean includeUnassigned;
        private List<NotificationStatus> statusList;
    }

    @Getter
    @Setter
    public static class ProcessingStatuses {
        private boolean includeUnassigned;
        private List<ProcessingStatus> statusList;
    }

    @Getter
    @Setter
    public static class InvestigationEventDateSearch {
        private InvestigationEventDateType eventDateType;
        private Instant from;
        private Instant to;
    }

    @Getter
    @Setter
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
        AWAITING_INTERVIEW,
        CLOSED_CASE,
        FIELD_FOLLOW_UP,
        NO_FOLLOW_UP,
        OPEN_CASE,
        SURVEILLANCE_FOLLOW_UP
    }

    public enum NotificationStatus {
        APPROVED,
        COMPLETED,
        MESSAGE_FAILED,
        PENDING_APPROVAL,
        REJECTED
    }

    public enum CaseStatus {
        CONFIRMED,
        NOT_A_CASE,
        PROBABLE,
        SUSPECT,
        UNKNOWN
    }

    public enum InvestigationEventDateType {
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
