package gov.cdc.nbs.event.report.lab;

import gov.cdc.nbs.event.investigation.InvestigationIdentifier;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@ScenarioScope
@Transactional
public class LabReportMother {

  private static final String CREATE =
      """
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
          effective_from_time,
          activity_to_time,
          rpt_to_state_time
      ) values (
          :identifier,
          :local,
          'LabReport',
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
          :collectedOn,
          :reportedOn,
          :receivedOn
      );
      """;

  private static final String DELETE_IN =
      """
      delete from Participation where act_class_cd = 'OBS' and act_uid in (:identifiers);
      delete from Observation where observation_uid in (:identifiers);
      delete from Act_id where act_uid in (:identifiers);
      delete from Act where class_cd = 'OBS' and act_uid in (:identifiers);
      """;

  private static final String ASSOCIATE_INVESTIGATION =
      """
      insert into Act_relationship(
          source_act_uid,
          source_class_cd,
          target_act_uid,
          target_class_cd,
          type_cd
      ) values (
          :identifier,
          'OBS',
          :investigation,
          'CASE',
          'LabReport'
      );
      """;

  private static final String PARTICIPATE_IN =
      """
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

  private static final String LAB_REPORT_CLASS_CODE = "OBS";
  private static final String PERSON_CLASS = "PSN";
  private static final String ORGANIZATION_CLASS = "ORG";
  private static final String REPORTER = "AUT";
  private static final String PATIENT_SUBJECT = "PATSBJ";
  private static final String ORDERED_BY = "ORD";

  private final MotherSettings settings;
  private final SequentialIdentityGenerator idGenerator;

  private final JdbcClient client;
  private final TestingDataCleaner<Long> cleaner;
  private final Active<AccessionIdentifier> activeAccessionIdentifier;
  private final Active<LabReportIdentifier> active;
  private final Available<LabReportIdentifier> available;

  private final RevisionMother revisionMother;

  LabReportMother(
      final MotherSettings settings,
      final SequentialIdentityGenerator idGenerator,
      final JdbcClient client,
      final Active<LabReportIdentifier> active,
      final Available<LabReportIdentifier> available,
      final Active<AccessionIdentifier> activeAccessionIdentifier,
      final RevisionMother revisionMother) {
    this.settings = settings;
    this.idGenerator = idGenerator;
    this.client = client;
    this.active = active;
    this.available = available;
    this.activeAccessionIdentifier = activeAccessionIdentifier;
    this.revisionMother = revisionMother;

    this.cleaner = new TestingDataCleaner<>(client, DELETE_IN, "identifiers");
  }

  @PreDestroy
  public void reset() {
    this.cleaner.clean();
  }

  void create(
      final PatientIdentifier patient,
      final OrganizationIdentifier organization,
      final JurisdictionIdentifier jurisdiction,
      final ProgramAreaIdentifier programArea) {

    PatientIdentifier revision = revisionMother.revise(patient);
    // Observation
    long identifier = idGenerator.next();
    String local = idGenerator.nextLocal(LAB_REPORT_CLASS_CODE);

    this.client
        .sql(CREATE)
        .param("identifier", identifier)
        .param("local", local)
        .param("addedOn", settings.createdBy())
        .param("addedBy", settings.createdBy())
        .param("programArea", programArea.code())
        .param("jurisdiction", jurisdiction.code())
        .param("oid", programArea.oid(jurisdiction))
        .param("collectedOn", RandomUtil.dateInPast())
        .param("reportedOn", RandomUtil.dateInPast())
        .param("receivedOn", RandomUtil.dateInPast())
        .update();

    forPatient(identifier, revision.id());

    reportedBy(identifier, organization.identifier());

    include(new LabReportIdentifier(identifier, local));
  }

  private void forPatient(final long identifier, final long patient) {
    this.client
        .sql(PARTICIPATE_IN)
        .param("identifier", identifier)
        .param("type", PATIENT_SUBJECT)
        .param("addedOn", settings.createdOn())
        .param("addedBy", settings.createdBy())
        .param("subject", patient)
        .param("subjectClass", PERSON_CLASS)
        .update();
  }

  void createAssociated(
      final LabReportIdentifier report, final InvestigationIdentifier investigation) {
    this.client
        .sql(ASSOCIATE_INVESTIGATION)
        .param("identifier", report.identifier())
        .param("investigation", investigation.identifier())
        .update();
  }

  private void reportedBy(final long identifier, final long organization) {
    this.client
        .sql(PARTICIPATE_IN)
        .param("identifier", identifier)
        .param("type", REPORTER)
        .param("addedOn", settings.createdOn())
        .param("addedBy", settings.createdBy())
        .param("subject", organization)
        .param("subjectClass", ORGANIZATION_CLASS)
        .update();
  }

  private void include(final LabReportIdentifier identifier) {
    this.cleaner.include(identifier.identifier());

    this.available.available(identifier);
    this.active.active(identifier);
  }

  void within(
      final LabReportIdentifier report,
      final ProgramAreaIdentifier programArea,
      final JurisdictionIdentifier jurisdiction) {
    this.client
        .sql(
            "update Observation set prog_area_cd = ?, jurisdiction_cd = ?, program_jurisdiction_oid = ? where observation_uid = ?")
        .param(programArea.code())
        .param(jurisdiction.code())
        .param(programArea.oid(jurisdiction))
        .param(report.identifier())
        .update();
  }

  void unprocessed(final LabReportIdentifier report) {
    this.client
        .sql("update Observation set record_status_cd = 'UNPROCESSED' where observation_uid = ?")
        .param(report.identifier())
        .update();
  }

  void electronic(final LabReportIdentifier report) {
    this.client
        .sql("update Observation set electronic_ind = 'Y' where observation_uid = ?")
        .param(report.identifier())
        .update();
  }

  void enteredExternally(final LabReportIdentifier report) {
    this.client
        .sql("update Observation set electronic_ind = 'E' where observation_uid = ?")
        .param(report.identifier())
        .update();
  }

  void orderedBy(final LabReportIdentifier lab, final OrganizationIdentifier organization) {
    this.client
        .sql(PARTICIPATE_IN)
        .param("identifier", lab.identifier())
        .param("type", ORDERED_BY)
        .param("addedOn", settings.createdOn())
        .param("addedBy", settings.createdBy())
        .param("subject", organization.identifier())
        .param("subjectClass", ORGANIZATION_CLASS)
        .update();
  }

  void orderedBy(final LabReportIdentifier identifier, final ProviderIdentifier provider) {
    this.client
        .sql(PARTICIPATE_IN)
        .param("identifier", identifier.identifier())
        .param("type", ORDERED_BY)
        .param("addedOn", settings.createdOn())
        .param("addedBy", settings.createdBy())
        .param("subject", provider.identifier())
        .param("subjectClass", PERSON_CLASS)
        .update();
  }

  void filledBy(final LabReportIdentifier report, final String value) {
    this.client
        .sql(
            """
            insert into Act_id (
                act_uid,
                act_id_seq,
                type_cd,
                type_desc_txt,
                root_extension_txt
            ) values (
                :identifier,
                (select count(*) from Act_id where act_uid = :identifier),
                'FN',
                'Filler Number',
                :value
            )
            """)
        .param("identifier", report.identifier())
        .param("value", value)
        .update();

    activeAccessionIdentifier.active(new AccessionIdentifier(report.identifier(), value));
  }

  void forPregnantPatient(final LabReportIdentifier report) {
    this.client
        .sql("update Observation set pregnant_ind_cd = 'Y' where observation_uid = ?")
        .param(report.identifier())
        .update();
  }

  void receivedOn(final LabReportIdentifier report, final LocalDateTime on) {
    this.client
        .sql("update Observation set rpt_to_state_time = ? where observation_uid = ?")
        .param(on)
        .param(report.identifier())
        .update();
  }

  void reportedOn(final LabReportIdentifier report, final LocalDate on) {
    this.client
        .sql("update Observation set activity_to_time = ? where observation_uid = ?")
        .param(on)
        .param(report.identifier())
        .update();
  }

  void collectedOn(final LabReportIdentifier report, final LocalDate on) {
    this.client
        .sql("update Observation set effective_from_time = ? where observation_uid = ?")
        .param(on)
        .param(report.identifier())
        .update();
  }

  void created(final LabReportIdentifier report, final long by, final LocalDate on) {
    this.client
        .sql("update Observation set add_user_id = ?, add_time = ? where observation_uid = ?")
        .param(by)
        .param(on)
        .param(report.identifier())
        .update();
  }

  void updated(final LabReportIdentifier identifier, final long by, final LocalDate on) {
    this.client
        .sql(
            """
            update Observation set
                last_chg_user_id = ?,
                last_chg_time = ?,
                version_ctrl_nbr = version_ctrl_nbr + 1
            where observation_uid = ?
            """)
        .param(by)
        .param(on)
        .param(identifier.identifier())
        .update();
  }

  void specimen(final LabReportIdentifier report, final String site) {
    this.client
        .sql(
            """
            update Observation set
                target_site_cd = ?
            where observation_uid = ?
            """)
        .param(site)
        .param(report.identifier())
        .update();
  }
}
