package gov.cdc.nbs.testing.event.contact;

import gov.cdc.nbs.event.investigation.InvestigationIdentifier;
import gov.cdc.nbs.identity.MotherSettings;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.authorization.jurisdiction.JurisdictionIdentifier;
import gov.cdc.nbs.testing.authorization.programarea.ProgramAreaIdentifier;
import gov.cdc.nbs.testing.data.TestingDataCleaner;
import gov.cdc.nbs.testing.identity.SequentialIdentityGenerator;
import gov.cdc.nbs.testing.support.Active;
import jakarta.annotation.PreDestroy;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

@Component
class ContactRecordMother {

  private static final String CREATE =
      """
      insert into Act(act_uid, class_cd, mood_cd) values (:identifier, 'CT','EVN');

      insert into CT_contact (
          ct_contact_uid,
          local_id,
          subject_entity_uid,
          contact_entity_uid,
          subject_entity_phc_uid,
          prog_area_cd,
          jurisdiction_cd,
          program_jurisdiction_oid,
          version_ctrl_nbr,
          add_time,
          add_user_id,
          last_chg_time,
          last_chg_user_id,
          record_status_cd,
          record_status_time
      ) values (
          :identifier,
          :local,
          :patient,
          :named,
          :investigation,
          :programArea,
          :jurisdiction,
          :oid,
          1,
          :addedOn,
          :addedBy,
          :addedOn,
          :addedBy,
          'ACTIVE',
          :addedOn
      )
      """;

  private static final String DELETE_IN =
      """
      delete from CT_contact where CT_Contact_uid in (:identifiers);
      delete from Act where class_cd = 'CT' and act_uid in (:identifiers);
      """;

  private final JdbcClient client;
  private final Active<ContactRecordIdentifier> active;
  private final MotherSettings settings;
  private final SequentialIdentityGenerator idGenerator;
  private final TestingDataCleaner<Long> cleaner;

  ContactRecordMother(
      final JdbcClient client,
      final Active<ContactRecordIdentifier> active,
      final MotherSettings settings,
      final SequentialIdentityGenerator idGenerator) {
    this.client = client;
    this.active = active;
    this.settings = settings;
    this.idGenerator = idGenerator;
    this.cleaner = new TestingDataCleaner<>(client, DELETE_IN, "identifiers");
  }

  @PreDestroy
  void cleanup() {
    this.cleaner.clean();
  }

  void create(final PatientIdentifier named, final InvestigationIdentifier investigation) {

    long identifier = idGenerator.next();
    String local = idGenerator.nextLocal("CON");

    ProgramAreaIdentifier programArea = investigation.programArea();
    JurisdictionIdentifier jurisdiction = investigation.jurisdiction();

    this.client
        .sql(CREATE)
        .param("identifier", identifier)
        .param("local", local)
        .param("addedOn", settings.createdOn())
        .param("addedBy", settings.createdBy())
        .param("programArea", programArea.code())
        .param("jurisdiction", jurisdiction.code())
        .param("oid", programArea.oid(jurisdiction))
        .param("patient", investigation.revision())
        .param("named", named.id())
        .param("investigation", investigation.identifier())
        .update();

    include(new ContactRecordIdentifier(identifier, local));
  }

  private void include(final ContactRecordIdentifier identifier) {
    this.active.active(identifier);
    this.cleaner.include(identifier.identifier());
  }

  void createdOn(final ContactRecordIdentifier contact, final LocalDateTime on) {
    this.client
        .sql("update ct_contact set add_time = ? where ct_contact_uid = ?")
        .param(on)
        .param(contact.identifier())
        .update();
  }

  void namedOn(final ContactRecordIdentifier contact, final LocalDate on) {
    this.client
        .sql("update ct_contact set named_On_Date = ? where ct_contact_uid = ?")
        .param(on)
        .param(contact.identifier())
        .update();
  }

  void priority(final ContactRecordIdentifier contact, final String value) {
    this.client
        .sql("update ct_contact set priority_cd = ? where ct_contact_uid = ?")
        .param(value)
        .param(contact.identifier())
        .update();
  }

  void disposition(final ContactRecordIdentifier contact, final String value) {
    this.client
        .sql("update ct_contact set disposition_cd = ? where ct_contact_uid = ?")
        .param(value)
        .param(contact.identifier())
        .update();
  }

  void referralBasis(final ContactRecordIdentifier contact, final String value) {
    this.client
        .sql("update ct_contact set contact_referral_basis_cd = ? where ct_contact_uid = ?")
        .param(value)
        .param(contact.identifier())
        .update();
  }

  void processingDecision(final ContactRecordIdentifier contact, final String value) {
    this.client
        .sql("update ct_contact set processing_decision_cd = ? where ct_contact_uid = ?")
        .param(value)
        .param(contact.identifier())
        .update();
  }

  public void associated(
      final ContactRecordIdentifier contact, final InvestigationIdentifier investigation) {
    this.client
        .sql("update ct_contact set contact_entity_phc_uid = ? where ct_contact_uid = ?")
        .param(investigation.identifier())
        .param(contact.identifier())
        .update();
  }
}
