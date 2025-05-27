package gov.cdc.nbs.testing.event.treatment;

import gov.cdc.nbs.event.investigation.InvestigationIdentifier;
import gov.cdc.nbs.event.report.morbidity.MorbidityReportIdentifier;
import gov.cdc.nbs.identity.MotherSettings;
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
      )
      
      --  relate it to the patient
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
          'SubjOfTrmt',
          'ACTIVE',
          :addedOn,
          :addedBy,
          :addedOn,
          'PSN',
          :patient
      );
      
      -- relate it to the event
      insert into Act_Relationship (
          source_act_uid,
          source_class_cd,
          target_act_uid,
          target_class_cd,
          type_cd,
          record_status_cd,
          record_status_time,
          add_user_id,
          add_time
      ) values (
          :identifier,
          'TRMT',
          :target,
          :targetClass,
          :type,
          'ACTIVE',
          :addedOn,
          :addedBy,
          :addedOn
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

  private void create(
      final ProgramAreaIdentifier programArea,
      final LocalDate treatedOn,
      final String treatment,
      final String description,
      final long patient,
      final long target,
      final String targetClass,
      final String type
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
        .param("patient", patient)
        .param("target", target)
        .param("targetClass", targetClass)
        .param("type", type)
        .update();

    this.cleaner.include(identifier);
    this.active.active(new TreatmentIdentifier(identifier, local));
  }

  void create(
      final MorbidityReportIdentifier report,
      final String treatment,
      final String description
  ) {

    create(
        report.programArea(),
        RandomUtil.dateInPast(),
        treatment,
        description,
        report.revision(),
        report.identifier(),
        "OBS",
        "TreatmentToMorb"
    );
  }

  void create(
      final MorbidityReportIdentifier report,
      final String treatment
  ) {

    create(report, treatment, null);
  }

  void create(final InvestigationIdentifier investigation) {
    create(
        investigation.programArea(),
        RandomUtil.dateInPast(),
        "OTH",
        "Other",
        investigation.revision(),
        investigation.identifier(),
        "CASE",
        "TreatmentToPHC"
    );
  }
}
