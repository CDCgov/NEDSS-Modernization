package gov.cdc.nbs.event.report.lab.test;

import gov.cdc.nbs.event.report.lab.LabReportIdentifier;
import gov.cdc.nbs.event.report.morbidity.MorbidityReportIdentifier;
import gov.cdc.nbs.identity.MotherSettings;
import gov.cdc.nbs.testing.data.TestingDataCleaner;
import gov.cdc.nbs.testing.identity.SequentialIdentityGenerator;
import io.cucumber.spring.ScenarioScope;
import jakarta.annotation.PreDestroy;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

@Component
@ScenarioScope
class MorbidityLabReportMother {

  private static final String CREATE =
      """
      -- Morbidity Reports have an associated Lab report Observation that a Resulted Test is attached to

      insert into Act(act_uid, class_cd, mood_cd) values (:identifier, 'OBS','EVN');

      insert into Observation(
          observation_uid,
          local_id,
          ctrl_cd_display_form,
          obs_domain_cd_st_1,
          version_ctrl_nbr,
          shared_ind,
          cd,
          cd_desc_txt,
          cd_system_cd,
          cd_system_desc_txt,
          record_status_cd,
          record_status_time,
          add_time,
          add_user_id
      ) values (
          :identifier,
          :local,
          'LabReportMorb',
          'Order',
          1,
          'T',
          'NI',
          'No Information Given',
          '2.16.840.1.113883',
          'Health Level Seven',
          'ACTIVE',
          :addedOn,
          :addedOn,
          :addedBy
      );

      -- associate the Lab Report with the Morbidity Report
      insert into Act_Relationship (
          source_act_uid,
          source_class_cd,
          target_act_uid,
          target_class_cd,
          type_cd,
          type_desc_txt,
          record_status_cd,
          record_status_time,
          add_time,
          add_user_id
      ) values (
          :identifier,
          'OBS',
          :morbidity,
          'OBS',
          'LabReport',
          'Laboratory Report',
          'ACTIVE',
          :addedOn,
          :addedOn,
          :addedBy
      )
      """;

  private static final String DELETE_IN =
      """
      delete from Act_relationship where source_class_cd = 'OBS' and source_act_uid in (:identifiers);
      delete from Observation where observation_uid in (:identifiers);
      delete from Act where class_cd = 'OBS' and act_uid in (:identifiers);
      """;

  private final MotherSettings settings;
  private final SequentialIdentityGenerator idGenerator;

  private final JdbcClient client;

  private final TestingDataCleaner<Long> cleaner;

  MorbidityLabReportMother(
      final MotherSettings settings,
      final SequentialIdentityGenerator idGenerator,
      final JdbcClient client) {
    this.settings = settings;
    this.idGenerator = idGenerator;
    this.client = client;
    this.cleaner = new TestingDataCleaner<>(client, DELETE_IN, "identifiers");
  }

  @PreDestroy
  void reset() {
    this.cleaner.clean();
  }

  /**
   * Creates a Laboratory Report for the given {@code morbidity}.
   *
   * @param morbidity The {@code MorbidityReportIdentifier} identifying the Morbidity report
   * @return The {@code LabReportIdentifier} for the created Laboratory Report
   */
  LabReportIdentifier create(final MorbidityReportIdentifier morbidity) {

    long identifier = idGenerator.next();
    String local = idGenerator.nextLocal("OBS");

    this.client
        .sql(CREATE)
        .param("identifier", identifier)
        .param("local", local)
        .param("addedOn", settings.createdBy())
        .param("addedBy", settings.createdBy())
        .param("morbidity", morbidity.identifier())
        .update();

    this.cleaner.include(identifier);

    return new LabReportIdentifier(identifier, local);
  }
}
