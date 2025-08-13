package gov.cdc.nbs.testing.event.record.birth;

import gov.cdc.nbs.event.investigation.InvestigationIdentifier;
import gov.cdc.nbs.identity.MotherSettings;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.support.organization.OrganizationIdentifier;
import gov.cdc.nbs.testing.data.TestingDataCleaner;
import gov.cdc.nbs.testing.identity.SequentialIdentityGenerator;
import gov.cdc.nbs.testing.patient.RevisionMother;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.spring.ScenarioScope;
import jakarta.annotation.PreDestroy;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@ScenarioScope
class BirthRecordMother {

  private final static String CREATE = """
      insert into Act(act_uid, class_cd, mood_cd) values (:identifier, 'DOCCLIN','EVN');
      
      insert into Clinical_document(
          clinical_document_uid,
          local_id,
          cd,
          version_ctrl_nbr,
          shared_ind,
          record_status_cd,
          record_status_time,
          add_time,
          add_user_id
      ) values (
          :identifier,
          :local,
          'BIR',
          1,
          'T',
          'ACTIVE',
          :addedOn,
          :addedOn,
          :addedBy
      );
      
      insert into Act_id (
          act_uid,
          act_id_seq,
          type_cd,
          root_extension_txt,
          record_status_cd,
          record_status_time,
          add_time,
          add_user_id
      ) values (
          :identifier,
          1,
          'FILENO',
          :certificate,
          'ACTIVE',
          :addedOn,
          :addedOn,
          :addedBy
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
          'DOCCLIN',
          :type,
          'ACTIVE',
          :addedOn,
          :addedBy,
          :addedOn,
          :subjectClass,
          :subject
      );
      """;

  private static final String ASSOCIATE_INVESTIGATION = """
      insert into Act_relationship(
          source_act_uid,
          source_class_cd,
          target_act_uid,
          target_class_cd,
          type_cd
      ) values (
          :identifier,
          'DOCCLIN',
          :investigation,
          'CASE',
          'BIR'
      );
      """;

  private static final String DELETE_IN = """
      delete from nbs_answer where act_uid in (:identifiers);
      delete from Participation where act_class_cd = 'DOCCLIN' and act_uid in (:identifiers);
      delete from Clinical_Document where clinical_document_uid in (:identifiers);
      delete from Act_relationship where source_class_cd = 'DOCCLIN' and source_act_uid in (:identifiers);
      delete from Act_id where act_uid in (:identifiers);
      delete from Act where class_cd = 'DOCCLIN' and act_uid in (:identifiers);
      """;

  private final MotherSettings settings;
  private final SequentialIdentityGenerator idGenerator;
  private final JdbcClient client;
  private final TestingDataCleaner<Long> cleaner;
  private final RevisionMother revisionMother;

  private final Active<BirthRecordIdentifier> active;

  BirthRecordMother(
      final MotherSettings settings,
      final SequentialIdentityGenerator idGenerator,
      final JdbcClient client,
      final RevisionMother revisionMother,
      final Active<BirthRecordIdentifier> active
  ) {
    this.settings = settings;
    this.idGenerator = idGenerator;
    this.client = client;
    this.revisionMother = revisionMother;
    this.active = active;

    this.cleaner = new TestingDataCleaner<>(client, DELETE_IN, "identifiers");
  }

  @PreDestroy
  void cleanup() {
    this.cleaner.clean();
  }

  void create(final PatientIdentifier patient, final String certificate) {
    PatientIdentifier revision = revisionMother.revise(patient);
    long identifier = idGenerator.next();
    String local = idGenerator.nextLocal("CDI");

    this.client.sql(CREATE)
        .param("identifier", identifier)
        .param("local", local)
        .param("certificate", certificate)
        .param("addedOn", settings.createdBy())
        .param("addedBy", settings.createdBy())
        .update();

    forPatient(identifier, revision.id());

    include(new BirthRecordIdentifier(identifier, local));
  }

  private void include(final BirthRecordIdentifier record) {
    this.active.active(record);
    this.cleaner.include(record.identifier());
  }

  private void forPatient(final long identifier, final long patient) {
    this.client.sql(PARTICIPATE_IN)
        .param("identifier", identifier)
        .param("type", "SubjOfBirth")
        .param("addedOn", settings.createdOn())
        .param("addedBy", settings.createdBy())
        .param("subject", patient)
        .param("subjectClass", "PSN")
        .update();
  }

  void receivedOn(final BirthRecordIdentifier record, final LocalDateTime on) {
    this.client.sql("update Clinical_document set add_time = ? where clinical_document_uid = ?")
        .param(on)
        .param(record.identifier())
        .update();
  }

  void collectedOn(final BirthRecordIdentifier record, final LocalDate on) {
    this.client.sql("update Clinical_document set record_status_time = ? where clinical_document_uid = ?")
        .param(on)
        .param(record.identifier())
        .update();
  }

  void bornAt(final BirthRecordIdentifier record, final OrganizationIdentifier organization) {
    this.client.sql(PARTICIPATE_IN)
        .param("identifier", record.identifier())
        .param("type", "FacilityOfBirth")
        .param("addedOn", settings.createdOn())
        .param("addedBy", settings.createdBy())
        .param("subject", organization.identifier())
        .param("subjectClass", "ORG")
        .update();
  }

  void associated(
      final BirthRecordIdentifier record,
      final InvestigationIdentifier investigation
  ) {
    this.client.sql(ASSOCIATE_INVESTIGATION)
        .param("identifier", record.identifier())
        .param("investigation", investigation.identifier())
        .update();
  }

  private static final String QUESTION = """
      merge into nbs_answer [answer]
      using (
          select
              :identifier  as [act_uid],
              :answer      as [answer_txt],
              [question].nbs_question_uid,
              [question].version_ctrl_nbr as [nbs_question_version_ctrl_nbr]
          from nbs_question [question]
          where [question].question_identifier = :question
      ) as source
      on      [answer].act_uid            = [source].act_uid
          and [answer].nbs_question_uid   = [source].nbs_question_uid
      when matched then
          update set
              answer_txt = [source].answer_txt
      when not matched then
          insert (
              act_uid,
              answer_txt,
              nbs_question_uid,
              nbs_question_version_ctrl_nbr,
              record_status_cd,
              record_status_time,
              last_chg_user_id,
              last_chg_time
          ) values (
              [source].act_uid,
              [source].answer_txt,
              [source].nbs_question_uid,
              [source].nbs_question_version_ctrl_nbr,
              'ACTIVE',
              :addedOn,
              :addedBy,
              :addedOn
          );
      """;

  private void question(
      final BirthRecordIdentifier record,
      final String question,
      final String answer
  ) {
    this.client.sql(QUESTION)
        .param("identifier", record.identifier())
        .param("question", question)
        .param("answer", answer)
        .param("addedOn", settings.createdOn())
        .param("addedBy", settings.createdBy())
        .update();
  }

  void motherName(
      final BirthRecordIdentifier record,
      final String first,
      final String middle,
      final String last,
      final String suffix
  ) {

    if (first != null) {
      question(record, "MTH201", first);
    }

    if (middle != null) {
      question(record, "MTH202", middle);
    }

    if (last != null) {
      question(record, "MTH203", last);
    }

    if (suffix != null) {
      question(record, "MTH204", suffix);
    }
  }

  void motherAddress(
      final BirthRecordIdentifier record,
      final String address,
      final String address2,
      final String city,
      final String state,
      final String county,
      final String zip
  ) {
    if (address != null) {
      question(record, "DEM159_MTH", address);
    }

    if (address2 != null) {
      question(record, "DEM160_MTH", address2);
    }

    if (city != null) {
      question(record, "MTH209", city);
    }

    if (state != null) {
      question(record, "MTH166", state);
    }

    if (county != null) {
      question(record, "MTH168", county);
    }

    if (zip != null) {
      question(record, "MTH169", zip);
    }
  }
}
