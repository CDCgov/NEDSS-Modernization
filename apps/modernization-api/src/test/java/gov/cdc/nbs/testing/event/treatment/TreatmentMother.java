package gov.cdc.nbs.testing.event.treatment;

import gov.cdc.nbs.event.investigation.InvestigationIdentifier;
import gov.cdc.nbs.event.report.morbidity.MorbidityReportIdentifier;
import gov.cdc.nbs.identity.MotherSettings;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.support.organization.OrganizationIdentifier;
import gov.cdc.nbs.support.provider.ProviderIdentifier;
import gov.cdc.nbs.support.util.RandomUtil;
import gov.cdc.nbs.testing.authorization.programarea.ProgramAreaIdentifier;
import gov.cdc.nbs.testing.data.TestingDataCleaner;
import gov.cdc.nbs.testing.identity.SequentialIdentityGenerator;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.spring.ScenarioScope;
import jakarta.annotation.PreDestroy;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@ScenarioScope
class TreatmentMother {

  private static final String CREATE = """
      insert into Act(act_uid, class_cd, mood_cd) values (:identifier, 'TRMT','EVN');
      
      -- create the treatment
      insert into Treatment (
          treatment_uid,
          local_id,
          prog_area_cd,
          record_status_cd,
          record_status_time,
          add_user_id,
          add_time,
          activity_to_time,
          cd,
          cd_desc_txt,
          cd_system_cd,
          class_cd,
          shared_ind
      ) values (
          :identifier,
          :local,
          :programArea,
          'ACTIVE',
          :addedOn,
          :addedBy,
          :addedOn,
          :treatedOn,
          :treatment,
          :description,
          :class,
          'NBS',
          'T'
      );
      
      --  extra information about the administered treatment
      insert into Treatment_administered (
          treatment_uid,
          treatment_administered_seq,
          effective_from_time
      ) values (
          :identifier,
          1,
          :treatedOn
      );
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
          'TRMT',
          :type,
          'ACTIVE',
          :addedOn,
          :addedBy,
          :addedOn,
          :subjectClass,
          :subject
      );
      """;

  private static final String RELATED_TO = """
       insert into Act_Relationship (
          source_act_uid,
          source_class_cd,
          target_act_uid,
          target_class_cd,
          type_cd,
          record_status_cd,
          record_status_time
      ) values (
          :identifier,
          'TRMT',
          :target,
          :targetClass,
          :type,
          'ACTIVE',
          GETDATE()
      )
      """;

  private static final String DELETE_IN = """
      delete from Participation where act_class_cd = 'TRMT' and act_uid in (:identifiers);
      delete from Treatment_administered where treatment_uid in (:identifiers);
      delete from treatment where treatment_uid in (:identifiers);
      delete from Act_relationship where source_class_cd = 'TRMT' and source_act_uid in (:identifiers);
      delete from Act where class_cd = 'TRMT' and act_uid in (:identifiers);
      """;

  private final MotherSettings settings;
  private final SequentialIdentityGenerator idGenerator;
  private final JdbcClient client;
  private final TestingDataCleaner<Long> cleaner;

  private final Active<TreatmentIdentifier> active;

  TreatmentMother(
      final MotherSettings settings,
      final SequentialIdentityGenerator idGenerator,
      final JdbcClient client,
      final Active<TreatmentIdentifier> active
  ) {
    this.settings = settings;
    this.idGenerator = idGenerator;
    this.client = client;
    this.active = active;

    this.cleaner = new TestingDataCleaner<>(client, DELETE_IN, "identifiers");

  }

  @PreDestroy
  void cleanup() {
    this.cleaner.clean();
  }

  private TreatmentIdentifier create(
      final ProgramAreaIdentifier programArea,
      final LocalDate treatedOn,
      final String treatment,
      final String description,
      final long patient
  ) {
    long identifier = idGenerator.next();
    String local = idGenerator.nextLocal("TRMT");

    this.client.sql(CREATE)
        .param("identifier", identifier)
        .param("local", local)
        .param("programArea", programArea.code())
        .param("addedOn", settings.createdOn())
        .param("addedBy", settings.createdBy())
        .param("treatedOn", treatedOn)
        .param("treatment", treatment)
        .param("description", description)
        .param("class", description == null ? "TA" : null)
        .update();

    TreatmentIdentifier created = new TreatmentIdentifier(identifier, local);

    participate(created, "SubjOfTrmt", patient, "PSN");

    this.cleaner.include(identifier);
    this.active.active(created);

    return created;
  }

  private void participate(
      final TreatmentIdentifier treatment,
      final String type,
      final long subject,
      final String subjectClass
  ) {
    this.client.sql(PARTICIPATE_IN)
        .param("identifier", treatment.identifier())
        .param("type", type)
        .param("addedOn", settings.createdOn())
        .param("addedBy", settings.createdBy())
        .param("subject", subject)
        .param("subjectClass", subjectClass)
        .update();
  }

  void create(
      final ProgramAreaIdentifier programArea,
      final PatientIdentifier patient,
      final String treatment
  ) {
    create(
        programArea,
        RandomUtil.dateInPast(),
        treatment,
        null,
        patient.id()
    );
  }

  void createCustom(
      final PatientIdentifier patient,
      final ProgramAreaIdentifier programArea,
      final String treatment
  ) {
    create(
        programArea,
        RandomUtil.dateInPast(),
        "OTH",
        treatment,
        patient.id()
    );
  }

  void create(
      final MorbidityReportIdentifier report,
      final String treatment,
      final String description
  ) {

    TreatmentIdentifier created = create(
        report.programArea(),
        RandomUtil.dateInPast(),
        treatment,
        description,
        report.revision()
    );

    relatedTo(created, report.identifier(), "OBS", "TreatmentToMorb");
  }

  void create(
      final MorbidityReportIdentifier report,
      final String treatment
  ) {

    create(report, treatment, null);
  }

  void create(final InvestigationIdentifier investigation) {
    TreatmentIdentifier created = create(
        investigation.programArea(),
        RandomUtil.dateInPast(),
        "OTH",
        "Other",
        investigation.revision()
    );

    associated(created, investigation);
  }

  private void relatedTo(
      final TreatmentIdentifier treatment,
      final long target,
      final String targetClass,
      final String type
  ) {
    this.client.sql(RELATED_TO)
        .param("identifier", treatment.identifier())
        .param("target", target)
        .param("targetClass", targetClass)
        .param("type", type)
        .update();

  }

  void createdOn(final TreatmentIdentifier treatment, final LocalDateTime on) {
    this.client.sql("update Treatment set add_time = ? where treatment_uid = ?")
        .param(on)
        .param(treatment.identifier())
        .update();
  }

  void treatedOn(final TreatmentIdentifier treatment, final LocalDate on) {
    this.client.sql("update Treatment_administered set effective_from_time = ? where treatment_uid = ?")
        .param(on)
        .param(treatment.identifier())
        .update();
  }

  void providedBy(final TreatmentIdentifier treatment, final ProviderIdentifier provider) {
    participate(treatment, "ProviderOfTrmt", provider.identifier(), "PSN");
  }

  void reportedAt(final TreatmentIdentifier treatment, final OrganizationIdentifier organization) {
    participate(treatment, "ReporterOfTrmt", organization.identifier(), "ORG");
  }

  void associated(
      final TreatmentIdentifier treatment,
      final InvestigationIdentifier investigation
  ) {
    relatedTo(treatment, investigation.identifier(), "CASE", "TreatmentToPHC");
  }
}
