package gov.cdc.nbs.labreport;

import java.time.LocalDate;
import java.util.List;
import gov.cdc.nbs.message.enums.PregnancyStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LabReportFilter {
    private Long patientId;
    private List<String> programAreas;
    private List<Long> jurisdictions;
    private PregnancyStatus pregnancyStatus;
    private LabReportEventId eventId;
    private LaboratoryEventDateSearch eventDate;
    private List<EntryMethod> entryMethods;
    private List<UserType> enteredBy;
    private List<EventStatus> eventStatus;
    private List<ProcessingStatus> processingStatus;
    private Long createdBy;
    private Long lastUpdatedBy;
    private LabReportProviderSearch providerSearch;
    private String resultedTest;
    private String codedResult;


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LaboratoryEventDateSearch {
        private LabReportDateType type;
        private LocalDate from;
        private LocalDate to;
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LabReportProviderSearch {
        private ProviderType providerType;
        private Long providerId;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LabReportEventId {
        private LaboratoryEventIdType labEventType;
        private String labEventId;
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
