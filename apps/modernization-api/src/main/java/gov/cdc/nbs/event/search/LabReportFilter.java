package gov.cdc.nbs.event.search;

import gov.cdc.nbs.message.enums.PregnancyStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public final class LabReportFilter {
  private Long patientId;
  private List<String> programAreas = new ArrayList<>();
  private List<Long> jurisdictions = new ArrayList<>();
  private PregnancyStatus pregnancyStatus;
  private LabReportEventId eventId;
  private LaboratoryEventDateSearch eventDate;
  private List<EntryMethod> entryMethods = new ArrayList<>();
  private List<UserType> enteredBy = new ArrayList<>();
  private List<EventStatus> eventStatus = new ArrayList<>();
  private List<ProcessingStatus> processingStatus = new ArrayList<>();
  private Long createdBy;
  private Long lastUpdatedBy;
  private LabReportProviderSearch providerSearch;
  private Long orderingLabId;
  private Long orderingProviderId;
  private Long reportingLabId;
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
    ACCESSION_NUMBER("ACCESSION_NUMBER", "Accession Number"),
    LAB_ID("LAB_ID", "Lab Id");

    private final String value;
    private final String display;

    LaboratoryEventIdType(final String value, final String display) {
      this.value = value;
      this.display = display;
    }

    public String value() {
      return value;
    }

    public String display() {
      return display;
    }
  }


  public enum LabReportDateType {
    DATE_OF_REPORT,
    DATE_RECEIVED_BY_PUBLIC_HEALTH,
    DATE_OF_SPECIMEN_COLLECTION,
    LAB_REPORT_CREATE_DATE,
    LAST_UPDATE_DATE
  }


  public enum EntryMethod {
    ELECTRONIC("Y"),
    MANUAL("N");

    private final String value;

    EntryMethod(final String value) {
      this.value = value;
    }

    public String value() {
      return value;
    }
  }


  public enum UserType {
    INTERNAL(null),
    EXTERNAL("E");

    private final String value;

    UserType(final String value) {
      this.value = value;
    }

    public String value() {
      return value;
    }
  }


  public enum EventStatus {
    NEW,
    UPDATE
  }


  public enum ProcessingStatus {
    PROCESSED,
    UNPROCESSED;
  }


  public enum ProviderType {
    ORDERING_FACILITY,
    ORDERING_PROVIDER,
    REPORTING_FACILITY
  }

  public LabReportFilter withResultedTest(final String resultedTest) {
    this.resultedTest = resultedTest;
    return this;
  }

  public LabReportFilter withCodedResult(final String codedResult) {
    this.codedResult = codedResult;
    return this;
  }

  public LabReportFilter withEventStatus(final EventStatus status) {
    this.eventStatus.add(status);
    return this;
  }

  public boolean includeNew() {
    return this.eventStatus.contains(EventStatus.NEW);
  }

  public boolean includeUpdated() {
    return this.eventStatus.contains(EventStatus.UPDATE);
  }

  public Optional<LabReportEventId> accession() {
    if (this.eventId != null
        && this.eventId.getLabEventId() != null
        && this.eventId.getLabEventType() == LaboratoryEventIdType.ACCESSION_NUMBER) {
      return Optional.of(this.eventId);
    }
    return Optional.empty();
  }

  public Optional<LabReportEventId> labId() {
    if (this.eventId != null
        && this.eventId.getLabEventId() != null
        && this.eventId.getLabEventType() == LaboratoryEventIdType.LAB_ID) {
      return Optional.of(this.eventId);
    }
    return Optional.empty();
  }

  public Optional<LaboratoryEventDateSearch> reportedOn() {
    return (this.eventDate != null && this.eventDate.type == LabReportDateType.DATE_OF_REPORT)
        ? Optional.of(this.eventDate)
        : Optional.empty();
  }

  public Optional<LaboratoryEventDateSearch> receivedOn() {
    return (this.eventDate != null && this.eventDate.type == LabReportDateType.DATE_RECEIVED_BY_PUBLIC_HEALTH)
        ? Optional.of(this.eventDate)
        : Optional.empty();
  }

  public Optional<LaboratoryEventDateSearch> collectedOn() {
    return (this.eventDate != null && this.eventDate.type == LabReportDateType.DATE_OF_SPECIMEN_COLLECTION)
        ? Optional.of(this.eventDate)
        : Optional.empty();
  }

  public Optional<LaboratoryEventDateSearch> createdOn() {
    return (this.eventDate != null && this.eventDate.type == LabReportDateType.LAB_REPORT_CREATE_DATE)
        ? Optional.of(this.eventDate)
        : Optional.empty();
  }

  public Optional<LaboratoryEventDateSearch> updatedOn() {
    return (this.eventDate != null && this.eventDate.type == LabReportDateType.LAST_UPDATE_DATE)
        ? Optional.of(this.eventDate)
        : Optional.empty();
  }

  public Optional<Long> orderingProvider() {
    if (this.orderingProviderId != null) {
      return Optional.of(this.orderingProviderId);
    }
    return (this.providerSearch != null && this.providerSearch.getProviderType() == ProviderType.ORDERING_PROVIDER)
        ? Optional.of(this.providerSearch.getProviderId())
        : Optional.empty();
  }

  public Optional<Long> orderingFacility() {
    if (this.orderingLabId != null) {
      return Optional.of(this.orderingLabId);
    }
    return (this.providerSearch != null && this.providerSearch.getProviderType() == ProviderType.ORDERING_FACILITY)
        ? Optional.of(this.providerSearch.getProviderId())
        : Optional.empty();
  }

  public Optional<Long> reportingFacility() {
    if (this.reportingLabId != null) {
      return Optional.of(this.reportingLabId);
    }
    return (this.providerSearch != null && this.providerSearch.getProviderType() == ProviderType.REPORTING_FACILITY)
        ? Optional.of(this.providerSearch.getProviderId())
        : Optional.empty();
  }

  public Optional<String> withResultedTest() {
    return (this.resultedTest == null || this.resultedTest.isEmpty())
        ? Optional.empty()
        : Optional.of(this.resultedTest);
  }

  public Optional<String> withCodedResult() {
    return (this.codedResult == null || this.codedResult.isEmpty())
        ? Optional.empty()
        : Optional.of(this.codedResult);
  }
}
