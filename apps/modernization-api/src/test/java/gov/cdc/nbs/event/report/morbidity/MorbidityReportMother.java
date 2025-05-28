package gov.cdc.nbs.event.report.morbidity;

import gov.cdc.nbs.identity.MotherSettings;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.support.organization.OrganizationIdentifier;
import gov.cdc.nbs.support.provider.ProviderIdentifier;
import gov.cdc.nbs.support.util.RandomUtil;
import gov.cdc.nbs.testing.authorization.jurisdiction.JurisdictionIdentifier;
import gov.cdc.nbs.testing.authorization.programarea.ProgramAreaIdentifier;
import gov.cdc.nbs.testing.data.TestingDataCleaner;
import gov.cdc.nbs.testing.identity.SequentialIdentityGenerator;
import gov.cdc.nbs.testing.patient.RevisionMother;
import gov.cdc.nbs.testing.support.Active;
import gov.cdc.nbs.testing.support.Available;
import io.cucumber.spring.ScenarioScope;
import jakarta.annotation.PreDestroy;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@ScenarioScope
public class MorbidityReportMother {

  private static final String CREATE = """
      insert into Act(act_uid, class_cd, mood_cd) values (:identifier, 'OBS','EVN');
      
      insert into Observation(
        observation_uid,
        local_id,
        ctrl_cd_display_form,
        obs_domain_cd_st_1,
        version_ctrl_nbr,
        shared_ind,
        record_status_cd,
        record_status_time,
        add_time,
        add_user_id,
        prog_area_cd,
        jurisdiction_cd,
        program_jurisdiction_oid,
        activity_to_time,
        cd
      ) values (
        :identifier,
        :local,
        'MorbReport',
        'Order',
        1,
        'N',
        'ACTIVE',
        :addedOn,
        :addedOn,
        :addedBy,
        :programArea,
        :jurisdiction,
        :oid,
        :reportedOn,
        :condition
      );
      """;

  private static final String DELETE_IN = """
      delete from Participation where act_class_cd = 'OBS' and act_uid in (:identifiers);
      delete from Observation where observation_uid in (:identifiers);
      delete from Act where class_cd = 'OBS' and act_uid in (:identifiers);
      """;

  private static final String PARTICIPATE_IN = """
      insert into Participation(
          act_uid,
          act_class_cd,
          type_cd,
          record_status_cd,
          record_status_time,
          add_user_id,
          add_time,
          subject_class_cd,
          subject_entity_uid
      ) values (
          :identifier,
          'OBS',
          :type,
          'ACTIVE',
          :addedOn,
          :addedBy,
          :addedOn,
          :subjectClass,
          :subject
      );
      """;

  private static final String MORBIDITY_CLASS_CODE = "OBS";
  private static final String PERSON_CLASS = "PSN";
  private static final String SUBJECT_OF_MORBIDITY = "SubjOfMorbReport";
  private static final String REPORTER = "ReporterOfMorbReport";
  private static final String ORGANIZATION_CLASS = "ORG";
  private static final String ORDERED_BY = "PhysicianOfMorb";

  private final MotherSettings settings;
  private final SequentialIdentityGenerator idGenerator;

  private final JdbcClient client;

  private final TestingDataCleaner<Long> cleaner;
  private final Available<MorbidityReportIdentifier> available;
  private final Active<MorbidityReportIdentifier> active;
  private final RevisionMother revisionMother;

  MorbidityReportMother(
      final MotherSettings settings,
      final SequentialIdentityGenerator idGenerator,
      final JdbcClient client,
      final Available<MorbidityReportIdentifier> available,
      final Active<MorbidityReportIdentifier> active,
      final RevisionMother revisionMother
  ) {
    this.settings = settings;
    this.idGenerator = idGenerator;
    this.client = client;
    this.available = available;
    this.active = active;
    this.revisionMother = revisionMother;

    this.cleaner = new TestingDataCleaner<>(client, DELETE_IN, "identifiers");
  }

  @PreDestroy
  void reset() {
    this.cleaner.clean();
  }

  /**
   * Creates a Morbidity Report associated with the given {@code patient}
   *
   * @param patient The identifier of the patient.
   */
  void create(
      final PatientIdentifier patient,
      final ProgramAreaIdentifier programArea,
      final JurisdictionIdentifier jurisdiction,
      final OrganizationIdentifier organization
  ) {
    PatientIdentifier revision = revisionMother.revise(patient);
    long identifier = idGenerator.next();
    String localId = idGenerator.nextLocal(MORBIDITY_CLASS_CODE);

    this.client.sql(CREATE)
        .param("identifier", identifier)
        .param("local", localId)
        .param("addedOn", settings.createdBy())
        .param("addedBy", settings.createdBy())
        .param("programArea", programArea.code())
        .param("jurisdiction", jurisdiction.code())
        .param("oid", programArea.oid(jurisdiction))
        .param("reportedOn", RandomUtil.dateInPast())
        .param("condition", "10570")
        .update();

    forPatient(identifier, revision.id());
    reportedBy(identifier, organization.identifier());

    include(new MorbidityReportIdentifier(identifier, localId, revision.id(), programArea, jurisdiction));

  }

  private void include(final MorbidityReportIdentifier identifier) {
    this.available.available(identifier);
    this.active.active(identifier);
    this.cleaner.include(identifier.identifier());
  }

  void unprocessed(final MorbidityReportIdentifier report) {
    this.client.sql("update Observation set record_status_cd = 'UNPROCESSED' where observation_uid = ?")
        .param(report.identifier())
        .update();
  }

  private void forPatient(final long identifier, final long patient) {
    this.client.sql(PARTICIPATE_IN)
        .param("identifier", identifier)
        .param("type", SUBJECT_OF_MORBIDITY)
        .param("addedOn", settings.createdOn())
        .param("addedBy", settings.createdBy())
        .param("subject", patient)
        .param("subjectClass", PERSON_CLASS)
        .update();
  }

  private void reportedBy(final long identifier, final long organization) {
    this.client.sql(PARTICIPATE_IN)
        .param("identifier", identifier)
        .param("type", REPORTER)
        .param("addedOn", settings.createdOn())
        .param("addedBy", settings.createdBy())
        .param("subject", organization)
        .param("subjectClass", ORGANIZATION_CLASS)
        .update();
  }

  void orderedBy(final MorbidityReportIdentifier identifier, final ProviderIdentifier provider) {
    this.client.sql(PARTICIPATE_IN)
        .param("identifier", identifier.identifier())
        .param("type", ORDERED_BY)
        .param("addedOn", settings.createdOn())
        .param("addedBy", settings.createdBy())
        .param("subject", provider.identifier())
        .param("subjectClass", PERSON_CLASS)
        .update();

  }

  void within(
      final MorbidityReportIdentifier report,
      final ProgramAreaIdentifier programArea,
      final JurisdictionIdentifier jurisdiction
  ) {
    this.client.sql(
            "update Observation set prog_area_cd = ?, jurisdiction_cd = ?, program_jurisdiction_oid = ? where observation_uid = ?")
        .param(programArea.code())
        .param(jurisdiction.code())
        .param(programArea.oid(jurisdiction))
        .param(report.identifier())
        .update();
  }

  void withCondition(final MorbidityReportIdentifier report, final String condition) {
    this.client.sql("update Observation set cd = ? where observation_uid = ?")
        .param(condition)
        .param(report.identifier())
        .update();
  }

  void receivedOn(final MorbidityReportIdentifier report, final LocalDateTime of) {
    this.client.sql("update Observation set add_time = ? where observation_uid = ?")
        .param(of)
        .param(report.identifier())
        .update();
  }

  void reportedOn(final MorbidityReportIdentifier report, final LocalDateTime of) {
    this.client.sql("update Observation set activity_to_time = ? where observation_uid = ?")
        .param(of)
        .param(report.identifier())
        .update();
  }

  void electronic(final MorbidityReportIdentifier report) {
    this.client.sql("update Observation set electronic_ind = 'Y' where observation_uid = ?")
        .param(report.identifier())
        .update();
  }

}
