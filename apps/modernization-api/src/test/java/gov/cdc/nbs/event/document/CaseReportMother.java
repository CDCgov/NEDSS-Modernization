package gov.cdc.nbs.event.document;

import gov.cdc.nbs.event.investigation.InvestigationIdentifier;
import gov.cdc.nbs.identity.MotherSettings;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.authorization.jurisdiction.JurisdictionIdentifier;
import gov.cdc.nbs.testing.authorization.programarea.ProgramAreaIdentifier;
import gov.cdc.nbs.testing.data.TestingDataCleaner;
import gov.cdc.nbs.testing.identity.SequentialIdentityGenerator;
import gov.cdc.nbs.testing.support.Active;
import gov.cdc.nbs.testing.support.Available;
import io.cucumber.spring.ScenarioScope;
import jakarta.annotation.PreDestroy;
import java.time.LocalDateTime;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

@Component
@ScenarioScope
public class CaseReportMother {

  private static final String CREATE =
      """
      insert into Act(act_uid, class_cd, mood_cd) values (:identifier, 'DOC','EVN');

      merge into NBS_document_metadata [meta]
      using (values (1003, 'schemaLocation', 'documentViewXSL')) as source(nbs_document_metadata_uid, xml_schema_location, document_view_xsl)
      on [meta].nbs_document_metadata_uid = [source].nbs_document_metadata_uid
      when not matched then
          insert (nbs_document_metadata_uid, xml_schema_location, document_view_xsl)
          values (source.nbs_document_metadata_uid, source.xml_schema_location, source.document_view_xsl);

      insert into NBS_document(
          nbs_document_uid,
          local_id,
          version_ctrl_nbr,
          cd,
          add_time,
          add_user_id,
          record_status_cd,
          record_status_time,
          doc_type_cd,
          shared_ind,
          doc_payload,
          nbs_interface_uid,
          nbs_document_metadata_uid,
          prog_area_cd,
          jurisdiction_cd,
          program_jurisdiction_oid
      ) values (
          :identifier,
          :local,
          1,
          :condition,
          :addedOn,
          :addedBy,
          'ACTIVE',
          :addedOn,
          'PHC236',
          'F',
          '<?xml version="1.0"?>',
          227,
          1003,
          :programArea,
          :jurisdiction,
          :oid
      );

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
          'DOC',
          'SubjOfDoc',
          'ACTIVE',
          :addedOn,
          :addedBy,
          :addedOn,
          'PSN',
          :patient
      );
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
          'DOC',
          :investigation,
          'CASE',
          'MorbReport'
      );
      """;

  private static final String DELETE_IN =
      """
      delete from Participation where act_class_cd = 'DOC' and act_uid in (:identifiers);
      delete from nbs_document where nbs_document_uid in (:identifiers);
      delete from Act_relationship where source_class_cd = 'DOC' and source_act_uid in (:identifiers);
      delete from Act where class_cd = 'DOC' and act_uid in (:identifiers);
      """;

  private final MotherSettings settings;
  private final SequentialIdentityGenerator idGenerator;
  private final JdbcClient client;
  private final TestingDataCleaner<Long> cleaner;

  private final Active<CaseReportIdentifier> active;
  private final Available<CaseReportIdentifier> available;

  CaseReportMother(
      final MotherSettings settings,
      final SequentialIdentityGenerator idGenerator,
      final JdbcClient client,
      final Active<CaseReportIdentifier> active,
      final Available<CaseReportIdentifier> available) {
    this.settings = settings;
    this.idGenerator = idGenerator;
    this.client = client;
    this.active = active;
    this.available = available;

    this.cleaner = new TestingDataCleaner<>(client, DELETE_IN, "identifiers");
  }

  @PreDestroy
  void reset() {
    this.cleaner.clean();
  }

  void create(
      final PatientIdentifier patient,
      final ProgramAreaIdentifier programArea,
      final JurisdictionIdentifier jurisdiction) {
    // Condition: Flu activity code (Influenza)
    create(patient, programArea, jurisdiction, "10570");
  }

  void create(
      final PatientIdentifier patient,
      final ProgramAreaIdentifier programArea,
      final JurisdictionIdentifier jurisdiction,
      final String condition) {
    long identifier = idGenerator.next();
    String local = idGenerator.nextLocal("DOC");

    this.client
        .sql(CREATE)
        .param("identifier", identifier)
        .param("local", local)
        .param("condition", condition)
        .param("programArea", programArea.code())
        .param("jurisdiction", jurisdiction.code())
        .param("oid", programArea.oid(jurisdiction))
        .param("addedOn", settings.createdOn())
        .param("addedBy", settings.createdBy())
        .param("patient", patient.id())
        .update();

    CaseReportIdentifier created = new CaseReportIdentifier(identifier, local);
    include(created);
  }

  private void include(final CaseReportIdentifier report) {
    this.available.available(report);
    this.active.active(report);
    this.cleaner.include(report.identifier());
  }

  void unprocessed(final CaseReportIdentifier report) {
    this.client
        .sql("update NBS_document set record_status_cd = 'UNPROCESSED' where nbs_document_uid = ?")
        .param(report.identifier())
        .update();
  }

  void sentBy(final CaseReportIdentifier report, final String name) {
    this.client
        .sql("update NBS_document set sending_facility_nm = ? where nbs_document_uid = ?")
        .param(name)
        .param(report.identifier())
        .update();
  }

  void addedOn(final CaseReportIdentifier report, final LocalDateTime of) {
    this.client
        .sql("update NBS_document set add_time = ? where nbs_document_uid = ?")
        .param(of)
        .param(report.identifier())
        .update();
  }

  void requiresSecurityAssignment(final CaseReportIdentifier report) {
    this.client
        .sql(
            "update NBS_document set prog_area_cd = null, jurisdiction_cd = null, program_jurisdiction_oid = null where nbs_document_uid = ?")
        .param(report.identifier())
        .update();
  }

  void updated(final CaseReportIdentifier report) {
    this.client
        .sql(
            """
            update NBS_document set
                external_version_ctrl_nbr = isNull(external_version_ctrl_nbr, 0) + 1
            where nbs_document_uid = ?
            """)
        .param(report.identifier())
        .update();
  }

  void withCondition(final CaseReportIdentifier report, final String condition) {
    this.client
        .sql("update NBS_document set cd = ? where nbs_document_uid = ?")
        .param(condition)
        .param(report.identifier())
        .update();
  }

  void within(
      final CaseReportIdentifier report,
      final ProgramAreaIdentifier programArea,
      final JurisdictionIdentifier jurisdiction) {
    this.client
        .sql(
            """
            update NBS_document set
                prog_area_cd = ?,
                jurisdiction_cd = ?,
                program_jurisdiction_oid = ?
            where nbs_document_uid = ?
            """)
        .param(programArea.code())
        .param(jurisdiction.code())
        .param(programArea.oid(jurisdiction))
        .param(report.identifier())
        .update();
  }

  void associated(final CaseReportIdentifier report, final InvestigationIdentifier investigation) {
    this.client
        .sql(ASSOCIATE_INVESTIGATION)
        .param("identifier", report.identifier())
        .param("investigation", investigation.identifier())
        .update();
  }
}
