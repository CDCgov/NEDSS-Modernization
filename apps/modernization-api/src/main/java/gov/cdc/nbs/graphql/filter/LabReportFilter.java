package gov.cdc.nbs.graphql.filter;

import java.time.Instant;
import java.util.List;

import gov.cdc.nbs.message.enums.PregnancyStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LabReportFilter {
    private Long patientId;
    private List<String> programAreas;
    private List<Long> jurisdictions;
    private PregnancyStatus pregnancyStatus;
    private LaboratoryEventIdType eventIdType;
    private String eventId;
    private LaboratoryEventDateSearch eventDateSearch;
    private List<EntryMethod> entryMethods;
    private List<UserType> enteredBy;
    private List<EventStatus> eventStatus;
    private List<ProcessingStatus> processingStatus;
    private Long createdBy;
    private Long lastUpdatedBy;
    private LabReportProviderSearch providerSearch;
    private String resultedTest;
    private String codedResult;


    @Getter
    @Setter
    @AllArgsConstructor
    public static class LaboratoryEventDateSearch {
        private LabReportDateType eventDateType;
        private Instant from;
        private Instant to;
    }


    @Getter
    @Setter
    @AllArgsConstructor
    public static class LabReportProviderSearch {
        private ProviderType providerType;
        private Long providerId;
    }


    public enum LaboratoryEventIdType {
        ACCESSION_NUMBER,
        LAB_ID
    }


    public enum LabReportDateType {
        DATE_OF_REPORT,
        DATE_RECEIVED_BY_PUBLIC_HEALTH,
        DATE_OF_SPECIMEN_COLLECTION,
        LAB_REPORT_CREATE_DATE,
        LAST_UPDATE_DATE
    }


    public enum EntryMethod {
        ELECTRONIC,
        MANUAL
    }


    public enum UserType {
        INTERNAL,
        EXTERNAL
    }


    public enum EventStatus {
        NEW,
        UPDATE
    }


    public enum ProcessingStatus {
        PROCESSED,
        UNPROCESSED
    }


    public enum ProviderType {
        ORDERING_FACILITY,
        ORDERING_PROVIDER,
        REPORTING_FACILITY
    }
}
