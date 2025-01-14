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
@NoArgsConstructor
public final class InvestigationFilter  {

  private static final String UNASSIGNED = "UNASSIGNED";

  private Long patientId;
  private List<String> conditions = new ArrayList<>();
  private List<String> programAreas = new ArrayList<>();
  private List<Long> jurisdictions = new ArrayList<>();
  private PregnancyStatus pregnancyStatus;
  private InvestigationEventId eventId;
  private EventDate eventDate;
  private String createdBy;
  private String lastUpdatedBy;
  private ProviderFacilitySearch providerFacilitySearch;
  private Long investigatorId;
  private InvestigationStatus investigationStatus;
  private List<String> outbreakNames = new ArrayList<>();
  private List<CaseStatus> caseStatuses = new ArrayList<>();
  private List<NotificationStatus> notificationStatuses = new ArrayList<>();
  private List<ProcessingStatus> processingStatuses = new ArrayList<>();
  private String reportingFacilityId;
  private String reportingProviderId;

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
    ABCS_CASE_ID("ABCS_CASE_ID","ABC Case ID"),
    CITY_COUNTY_CASE_ID("CITY_COUNTY_CASE_ID","City Case ID"),
    INVESTIGATION_ID("INVESTIGATION_ID","Investigation ID"),
    NOTIFICATION_ID("NOTIFICATION_ID","Notification ID"),
    STATE_CASE_ID("STATE_CASE_ID","State Case ID")
    ;

    private final String value;
    private final String display;

    IdType(final String value, final String display) {
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
    MESSAGE_FAILED("MSG_FAIL"),
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
    OPEN("O"),
    CLOSED("C");

    private final String value;

    InvestigationStatus(final String value) {
      this.value = value;
    }

    public String value() {
      return value;
    }
  }

  public void withCondition(final String condition) {
    this.conditions.add(condition);
  }

  public void withOutbreak(final String outbreak) {
    this.outbreakNames.add(outbreak);
  }


  public void withProgramArea(final String programArea) {
    this.programAreas.add(programArea);
  }

  public void withJurisdiction(final long jurisdiction) {
    this.jurisdictions.add(jurisdiction);
  }

  public void withProcessingStatus(final ProcessingStatus status) {
    this.processingStatuses.add(status);
  }

  public void withCaseStatus(final CaseStatus status) {
    this.caseStatuses.add(status);
  }

  public void withNotificationStatus(final NotificationStatus status) {
    this.notificationStatuses.add(status);
  }


  public Optional<InvestigationEventId> abcsCase() {
    if (this.eventId != null
        && this.eventId.getId() != null
        && this.eventId.getInvestigationEventType() == IdType.ABCS_CASE_ID) {
      return Optional.of(this.eventId);
    }
    return Optional.empty();
  }

  public Optional<InvestigationEventId> stateCase() {
    if (this.eventId != null
        && this.eventId.getId() != null
        && this.eventId.getInvestigationEventType() == IdType.STATE_CASE_ID) {
      return Optional.of(this.eventId);
    }
    return Optional.empty();
  }

  public Optional<InvestigationEventId> countyCase() {
    if (this.eventId != null
        && this.eventId.getId() != null
        && this.eventId.getInvestigationEventType() == IdType.CITY_COUNTY_CASE_ID) {
      return Optional.of(this.eventId);
    }
    return Optional.empty();
  }

  public Optional<InvestigationEventId> notification() {
    if (this.eventId != null
        && this.eventId.getId() != null
        && this.eventId.getInvestigationEventType() == IdType.NOTIFICATION_ID) {
      return Optional.of(this.eventId);
    }
    return Optional.empty();
  }

  public Optional<InvestigationEventId> caseId() {
    if (this.eventId != null
        && this.eventId.getId() != null
        && this.eventId.getInvestigationEventType() == IdType.INVESTIGATION_ID) {
      return Optional.of(this.eventId);
    }
    return Optional.empty();
  }

  public Optional<EventDate> createdOn() {
    return (this.eventDate != null && this.eventDate.type == EventDateType.INVESTIGATION_CREATE_DATE)
        ? Optional.of(this.eventDate)
        : Optional.empty();
  }

  public Optional<EventDate> updatedOn() {
    return (this.eventDate != null && this.eventDate.type == EventDateType.LAST_UPDATE_DATE)
        ? Optional.of(this.eventDate)
        : Optional.empty();
  }

  public Optional<EventDate> reportedOn() {
    return (this.eventDate != null && this.eventDate.type == EventDateType.DATE_OF_REPORT)
        ? Optional.of(this.eventDate)
        : Optional.empty();
  }

  public Optional<EventDate> startedOn() {
    return (this.eventDate != null && this.eventDate.type == EventDateType.INVESTIGATION_START_DATE)
        ? Optional.of(this.eventDate)
        : Optional.empty();
  }

  public Optional<EventDate> closedOn() {
    return (this.eventDate != null && this.eventDate.type == EventDateType.INVESTIGATION_CLOSED_DATE)
        ? Optional.of(this.eventDate)
        : Optional.empty();
  }

  public Optional<EventDate> notifiedOn() {
    return (this.eventDate != null && this.eventDate.type == EventDateType.NOTIFICATION_CREATE_DATE)
        ? Optional.of(this.eventDate)
        : Optional.empty();
  }

  public Optional<Long> reportingFacility() {
    if (this.reportingFacilityId != null) {
      return Optional.of(Long.parseLong(this.reportingFacilityId));
    }
    return (this.providerFacilitySearch != null
        && this.providerFacilitySearch.getEntityType() == ReportingEntityType.FACILITY)
            ? Optional.of(this.providerFacilitySearch.getId())
            : Optional.empty();
  }

  public Optional<Long> reportingProvider() {
    if (this.reportingProviderId != null) {
      return Optional.of(Long.parseLong(this.reportingProviderId));
    }
    return (this.providerFacilitySearch != null
        && this.providerFacilitySearch.getEntityType() == ReportingEntityType.PROVIDER)
            ? Optional.of(this.providerFacilitySearch.getId())
            : Optional.empty();
  }
}
