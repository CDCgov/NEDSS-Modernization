package gov.cdc.nbs.event.investigation;

import gov.cdc.nbs.identity.MotherSettings;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.support.organization.OrganizationIdentifier;
import gov.cdc.nbs.support.provider.ProviderIdentifier;
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
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

@Component
@ScenarioScope
public class InvestigationMother {

  private static final String CREATE =
      """
      insert into Act(act_uid, class_cd, mood_cd) values (:identifier, 'CASE','EVN');

      insert into Public_health_case (
          public_health_case_uid,
          local_id,
          version_ctrl_nbr,
          record_status_cd,
          record_status_time,
          add_time,
          add_user_id,
          prog_area_cd,
          jurisdiction_cd,
          program_jurisdiction_oid,
          cd,
          shared_ind,
          investigation_status_cd,
          case_type_cd
      ) values (
          :identifier,
          :local,
          1,
          'ACTIVE',
          :addedOn,
          :addedOn,
          :addedBy,
          :programArea,
          :jurisdiction,
          :oid,
          :condition,
          'F',
          'O',
          'I'
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
          'CASE',
          :type,
          'ACTIVE',
          :addedOn,
          :addedBy,
          :addedOn,
          :subjectClass,
          :subject
      );
      """;

  private static final String RELATE_WITH =
      """
      insert into Act_id(
          act_uid,
          act_id_seq,
          type_cd,
          assigning_authority_cd,
          root_extension_txt,
          record_status_cd,
          record_status_time,
          add_user_id,
          add_time
      ) values (
          :identifier,
          :sequence,
          :type,
          :authority,
          :value,
          'ACTIVE',
          :addedOn,
          :addedBy,
          :addedOn
      );
      """;

  private static final String DELETE_IN =
      """
      delete from Participation where act_class_cd = 'OBS' and act_uid in (:identifiers);
      delete from Public_health_case where public_health_case_uid in (:identifiers);
      delete from Act_id where act_uid in (:identifiers);
      delete from Act_relationship where source_class_cd = 'CASE' and source_act_uid in (:identifiers);
      delete from Act_relationship where target_class_cd = 'CASE' and target_act_uid in (:identifiers);
      delete from Act where class_cd = 'CASE' and act_uid in (:identifiers);
      """;

  private static final String INVESTIGATION_CODE = "CAS";

  private final SequentialIdentityGenerator idGenerator;
  private final MotherSettings settings;
  private final TestInvestigations investigations;

  private final Available<InvestigationIdentifier> available;
  private final Active<InvestigationIdentifier> active;
  private final Active<AbcCaseIdentifier> activeAbcCase;
  private final Active<StateCaseIdentifier> activeStateCase;
  private final Active<CityCountyCaseIdentifier> activeCityCountyCase;
  private final RevisionMother revisionMother;
  private final JdbcClient client;
  private final TestingDataCleaner<Long> cleaner;

  InvestigationMother(
      final MotherSettings settings,
      final SequentialIdentityGenerator idGenerator,
      final TestInvestigations investigations,
      final Available<InvestigationIdentifier> available,
      final Active<InvestigationIdentifier> active,
      final RevisionMother revisionMother,
      final Active<AbcCaseIdentifier> activeAbcCase,
      final Active<StateCaseIdentifier> activeStateCase,
      final Active<CityCountyCaseIdentifier> activeCityCountyCase,
      final JdbcClient client) {
    this.idGenerator = idGenerator;
    this.settings = settings;
    this.investigations = investigations;
    this.available = available;
    this.active = active;
    this.revisionMother = revisionMother;
    this.activeAbcCase = activeAbcCase;
    this.activeStateCase = activeStateCase;
    this.activeCityCountyCase = activeCityCountyCase;

    this.client = client;
    this.cleaner = new TestingDataCleaner<>(client, DELETE_IN, "identifiers");
  }

  @PreDestroy
  void reset() {
    this.cleaner.clean();
    this.investigations.reset();
  }

  private void participate(
      final InvestigationIdentifier investigation,
      final String type,
      final long subject,
      final String subjectClass) {
    this.client
        .sql(PARTICIPATE_IN)
        .param("identifier", investigation.identifier())
        .param("type", type)
        .param("addedOn", settings.createdOn())
        .param("addedBy", settings.createdBy())
        .param("subject", subject)
        .param("subjectClass", subjectClass)
        .update();
  }

  public InvestigationIdentifier investigation(final long subject) {
    long identifier = idGenerator.next();
    String local = idGenerator.nextLocal(INVESTIGATION_CODE);

    this.client
        .sql(CREATE)
        .param("identifier", identifier)
        .param("local", local)
        .param("addedOn", settings.createdOn())
        .param("addedBy", settings.createdBy())
        .param("programArea", "STD")
        .param("jurisdiction", "999999")
        .param("oid", 1300600015L)
        .param("condition", "42060")
        .update();

    InvestigationIdentifier created =
        new InvestigationIdentifier(identifier, local, subject, null, null);

    subjectOf(created, subject);
    include(created);
    return created;
  }

  void create(
      final PatientIdentifier patient,
      final JurisdictionIdentifier jurisdiction,
      final ProgramAreaIdentifier programArea) {
    PatientIdentifier revision = revisionMother.revise(patient);

    long identifier = idGenerator.next();
    String local = idGenerator.nextLocal(INVESTIGATION_CODE);

    this.client
        .sql(CREATE)
        .param("identifier", identifier)
        .param("local", local)
        .param("addedOn", settings.createdOn())
        .param("addedBy", settings.createdBy())
        .param("programArea", programArea.code())
        .param("jurisdiction", jurisdiction.code())
        .param("oid", programArea.oid(jurisdiction))
        .param("condition", "42060")
        .update();

    InvestigationIdentifier created =
        new InvestigationIdentifier(identifier, local, revision.id(), programArea, jurisdiction);

    subjectOf(created, revision.id());
    include(created);
  }

  private void include(final InvestigationIdentifier investigation) {
    this.investigations.available(investigation.identifier());
    this.available.available(investigation);
    this.active.active(investigation);
    this.cleaner.include(investigation.identifier());
  }

  private void subjectOf(final InvestigationIdentifier identifier, final long patient) {
    participate(identifier, "SubjOfPHC", patient, "PSN");
  }

  public void within(
      final InvestigationIdentifier investigation,
      final ProgramAreaIdentifier programArea,
      final JurisdictionIdentifier jurisdiction) {

    this.client
        .sql(
            """
            update Public_health_case set
                jurisdiction_cd = ?,
                prog_area_cd = ?,
                program_jurisdiction_oid = ?
            where public_health_case_uid = ?
            """)
        .param(jurisdiction.code())
        .param(programArea.code())
        .param(programArea.oid(jurisdiction))
        .param(investigation.identifier())
        .update();
  }

  void closed(final InvestigationIdentifier investigation, final LocalDate on) {
    this.client
        .sql(
            "update Public_health_case set investigation_status_cd = 'C', activity_to_time = ? where public_health_case_uid = ?")
        .param(on)
        .param(investigation.identifier())
        .update();
  }

  void processing(final InvestigationIdentifier investigation, final String status) {
    this.client
        .sql(
            "update Public_health_case set curr_process_state_cd = ? where public_health_case_uid = ?")
        .param(status)
        .param(investigation.identifier())
        .update();
  }

  void caseStatus(final InvestigationIdentifier investigation, final String status) {
    this.client
        .sql("update Public_health_case set case_class_cd = ? where public_health_case_uid = ?")
        .param(status)
        .param(investigation.identifier())
        .update();
  }

  void created(final InvestigationIdentifier investigation, final long by, final LocalDate on) {
    this.client
        .sql(
            "update Public_health_case set add_user_id = ?, add_time = ? where public_health_case_uid = ?")
        .param(by)
        .param(on)
        .param(investigation.identifier())
        .update();
  }

  void updated(final InvestigationIdentifier investigation, final long by, final LocalDate on) {
    this.client
        .sql(
            "update Public_health_case set last_chg_user_id = ?, last_chg_time = ? where public_health_case_uid = ?")
        .param(by)
        .param(on)
        .param(investigation.identifier())
        .update();
  }

  private void withPregnancy(final InvestigationIdentifier investigation, final String value) {
    this.client
        .sql("update Public_health_case set pregnant_ind_cd = ? where public_health_case_uid = ?")
        .param(value)
        .param(investigation.identifier())
        .update();
  }

  void forPregnantPatient(final InvestigationIdentifier investigation) {
    withPregnancy(investigation, "Y");
  }

  void forNonPregnantPatient(final InvestigationIdentifier investigation) {
    withPregnancy(investigation, "N");
  }

  void forPregnancyUnknownPatient(final InvestigationIdentifier investigation) {
    withPregnancy(investigation, "UNK");
  }

  void withCondition(final InvestigationIdentifier investigation, final String condition) {
    this.client
        .sql("update Public_health_case set cd = ? where public_health_case_uid = ?")
        .param(condition)
        .param(investigation.identifier())
        .update();
  }

  public void started(final InvestigationIdentifier investigation, final LocalDate on) {
    this.client
        .sql(
            "update Public_health_case set activity_from_time = ? where public_health_case_uid = ?")
        .param(on)
        .param(investigation.identifier())
        .update();
  }

  void reported(final InvestigationIdentifier investigation, final LocalDate on) {
    this.client
        .sql(
            "update Public_health_case set rpt_form_cmplt_time = ? where public_health_case_uid = ?")
        .param(on)
        .param(investigation.identifier())
        .update();
  }

  void relatedToABCSCase(final InvestigationIdentifier investigation, final String value) {

    relateWithIdentifier(investigation, 2, "STATE", "ABCS", value);
    activeAbcCase.active(new AbcCaseIdentifier(investigation.identifier(), value));
  }

  void relatedToCountyCase(final InvestigationIdentifier investigation, final String value) {
    relateWithIdentifier(investigation, 2, "CITY", value);
    activeCityCountyCase.active(new CityCountyCaseIdentifier(investigation.identifier(), value));
  }

  void relatedToStateCase(final InvestigationIdentifier investigation, final String value) {

    relateWithIdentifier(investigation, 1, "STATE", value);
    activeStateCase.active(new StateCaseIdentifier(investigation.identifier(), value));
  }

  private void relateWithIdentifier(
      final InvestigationIdentifier investigation,
      final int sequence,
      final String type,
      final String value) {
    relateWithIdentifier(investigation, sequence, type, null, value);
  }

  private void relateWithIdentifier(
      final InvestigationIdentifier investigation,
      final int sequence,
      final String type,
      final String authority,
      final String value) {
    this.client
        .sql(RELATE_WITH)
        .param("identifier", investigation.identifier())
        .param("sequence", sequence)
        .param("authority", authority)
        .param("type", type)
        .param("value", value)
        .param("addedOn", this.settings.createdOn())
        .param("addedBy", this.settings.createdBy())
        .update();
  }

  void relatedToOutbreak(final InvestigationIdentifier investigation, final String outbreak) {
    this.client
        .sql("update Public_health_case set outbreak_name = ? where public_health_case_uid = ?")
        .param(outbreak)
        .param(investigation.identifier())
        .update();
  }

  void reportedBy(
      final InvestigationIdentifier identifier, final OrganizationIdentifier organization) {
    participate(identifier, "OrgAsReporterOfPHC", organization.identifier(), "ORG");
  }

  void reportedBy(final InvestigationIdentifier identifier, final ProviderIdentifier provider) {
    participate(identifier, "PerAsReporterOfPHC", provider.identifier(), "PSN");
  }

  void investigatedBy(
      final InvestigationIdentifier identifier, final ProviderIdentifier investigator) {
    participate(identifier, "InvestgrOfPHC", investigator.identifier(), "PSN");
  }
}
