package gov.cdc.nbs.event.vaccination;

import gov.cdc.nbs.event.investigation.InvestigationIdentifier;
import gov.cdc.nbs.identity.MotherSettings;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.patient.profile.vaccination.VaccinationIdentifier;
import gov.cdc.nbs.support.organization.OrganizationIdentifier;
import gov.cdc.nbs.support.provider.ProviderIdentifier;
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

@Component
@ScenarioScope
class VaccinationMother {

  private static final String DELETE_IN =
      """
      delete from Participation where act_class_cd = 'INTV' and act_uid in (:identifiers);
      delete from Intervention where intervention_uid in (:identifiers);
      delete from Act_relationship where source_act_uid in (:identifiers);
      delete from Act where class_cd = 'INTV' and act_uid in (:identifiers);
      """;

  private static final String CREATE =
      """
      insert into Act(act_uid, class_cd, mood_cd) values (:identifier, 'INTV','EVN');

      insert into Intervention(
        intervention_uid,
        local_id,
        material_cd,
        version_ctrl_nbr,
        shared_ind,
        record_status_cd,
        record_status_time,
        add_time,
        add_user_id
      ) values (
        :identifier,
        :local,
        :vaccine,
        1,
        'N',
        'ACTIVE',
        :addedOn,
        :addedOn,
        :addedBy
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
          'INTV',
          :investigation,
          'CASE',
          '1180'
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
          'INTV',
          :type,
          'ACTIVE',
          :addedOn,
          :addedBy,
          :addedOn,
          :subjectClass,
          :subject
      );
      """;

  private final JdbcClient client;
  private final MotherSettings settings;
  private final SequentialIdentityGenerator idGenerator;
  private final Available<VaccinationIdentifier> available;
  private final Active<VaccinationIdentifier> active;
  private final RevisionMother revisionMother;
  private final TestingDataCleaner<Long> cleaner;

  VaccinationMother(
      final JdbcClient client,
      MotherSettings settings,
      SequentialIdentityGenerator idGenerator,
      final Available<VaccinationIdentifier> available,
      final Active<VaccinationIdentifier> active,
      final RevisionMother revisionMother) {
    this.client = client;
    this.settings = settings;
    this.idGenerator = idGenerator;
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
   * Creates a Vaccination associated with the given {@code patient}
   *
   * @param patient The identifier of the patient.
   */
  void create(final PatientIdentifier patient, final String vaccine) {
    PatientIdentifier revision = revisionMother.revise(patient);
    long identifier = idGenerator.next();
    String localId = idGenerator.nextLocal("INT");

    this.client
        .sql(CREATE)
        .param("identifier", identifier)
        .param("local", localId)
        .param("vaccine", vaccine)
        .param("addedOn", settings.createdBy())
        .param("addedBy", settings.createdBy())
        .update();

    forPatient(identifier, revision.id());

    include(new VaccinationIdentifier(identifier, localId));
  }

  private void include(final VaccinationIdentifier identifier) {
    this.available.available(identifier);
    this.active.active(identifier);
    this.cleaner.include(identifier.identifier());
  }

  private void forPatient(final long identifier, final long patient) {
    this.client
        .sql(PARTICIPATE_IN)
        .param("identifier", identifier)
        .param("type", "SubOfVacc")
        .param("addedOn", settings.createdOn())
        .param("addedBy", settings.createdBy())
        .param("subject", patient)
        .param("subjectClass", "PAT")
        .update();
  }

  void createdOn(final VaccinationIdentifier report, final LocalDateTime on) {
    this.client
        .sql("update Intervention set add_time = ? where intervention_uid = ?")
        .param(on)
        .param(report.identifier())
        .update();
  }

  void performedBy(final VaccinationIdentifier identifier, final ProviderIdentifier provider) {
    this.client
        .sql(PARTICIPATE_IN)
        .param("identifier", identifier.identifier())
        .param("type", "PerformerOfVacc")
        .param("addedOn", settings.createdOn())
        .param("addedBy", settings.createdBy())
        .param("subject", provider.identifier())
        .param("subjectClass", "PSN")
        .update();
  }

  void performedAt(
      final VaccinationIdentifier identifier, final OrganizationIdentifier organiztion) {
    this.client
        .sql(PARTICIPATE_IN)
        .param("identifier", identifier.identifier())
        .param("type", "PerformerOfVacc")
        .param("addedOn", settings.createdOn())
        .param("addedBy", settings.createdBy())
        .param("subject", organiztion.identifier())
        .param("subjectClass", "ORG")
        .update();
  }

  void administeredOn(final VaccinationIdentifier vaccination, final LocalDate on) {
    this.client
        .sql("update Intervention set activity_from_time = ? where intervention_uid = ?")
        .param(on)
        .param(vaccination.identifier())
        .update();
  }

  void associated(
      final VaccinationIdentifier vaccination, final InvestigationIdentifier investigation) {
    this.client
        .sql(ASSOCIATE_INVESTIGATION)
        .param("identifier", vaccination.identifier())
        .param("investigation", investigation.identifier())
        .update();
  }
}
