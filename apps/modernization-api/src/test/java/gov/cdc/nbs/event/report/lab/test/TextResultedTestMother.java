package gov.cdc.nbs.event.report.lab.test;

import gov.cdc.nbs.event.report.lab.LabReportIdentifier;
import gov.cdc.nbs.testing.data.TestingDataCleaner;
import gov.cdc.nbs.testing.identity.SequentialIdentityGenerator;
import io.cucumber.spring.ScenarioScope;
import jakarta.annotation.PreDestroy;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

@Component
@ScenarioScope
class TextResultedTestMother {

  private static final String CREATE =
      """
       insert into Act (
         act_uid,
         class_cd,
         mood_cd
       ) values (
         :identifier,
         'OBS',
         'EVN'
       );

       insert into Observation(
         observation_uid,
         ctrl_cd_display_form,
         obs_domain_cd_st_1,
         cd,
         cd_desc_txt,
         version_ctrl_nbr,
         shared_ind
       )
       select
         :identifier,
         'LabReport',
         'Result',
         lab_test_cd,
         lab_test_desc_txt,
         1,
         'N'
       from [NBS_SRTE]..[Lab_test]
       where lab_test_cd = :test
      ;

        insert into Act_relationship(
          source_act_uid,
          source_class_cd,
          target_act_uid,
          target_class_cd,
          type_cd
       ) values (
          :identifier,
          'OBS',
          :observation,
          'OBS',
          'COMP'
       );

      insert into Obs_value_txt (
          observation_uid,
          obs_value_txt_seq,
          value_txt,
          txt_type_cd
      ) values (
          :identifier,
          (select count(*) + 1 from Obs_value_txt where observation_uid = :identifier),
          :result,
          null
      )
      ;
      """;

  private static final String DELETE_IN =
      """
      delete from Participation where act_class_cd = 'OBS' and act_uid in (:identifiers);

      delete from Obs_value_txt where observation_uid in (:identifiers);

      delete from Observation
      where   obs_domain_cd_st_1 = 'Result'
          and observation_uid in (:identifiers);

      delete from Act_relationship
      where   source_act_uid in (:identifiers)
          and source_class_cd = 'OBS'
          and type_cd = 'COMP';

      delete from Act where class_cd = 'OBS' and act_uid in (:identifiers);
      """;

  private final SequentialIdentityGenerator idGenerator;
  private final JdbcClient client;
  private final TestingDataCleaner<Long> cleaner;

  TextResultedTestMother(final SequentialIdentityGenerator idGenerator, final JdbcClient client) {
    this.idGenerator = idGenerator;
    this.client = client;
    this.cleaner = new TestingDataCleaner<>(client, DELETE_IN, "identifiers");
  }

  @PreDestroy
  void cleanup() {
    this.cleaner.clean();
  }

  void create(final LabReportIdentifier report, final String test, final String result) {
    long identifier = idGenerator.next();

    this.client
        .sql(CREATE)
        .param("identifier", identifier)
        .param("observation", report.identifier())
        .param("test", test)
        .param("result", result)
        .update();

    this.cleaner.include(identifier);
  }
}
