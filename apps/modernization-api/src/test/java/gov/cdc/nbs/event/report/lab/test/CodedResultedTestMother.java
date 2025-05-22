package gov.cdc.nbs.event.report.lab.test;

import gov.cdc.nbs.event.report.lab.LabReportIdentifier;
import gov.cdc.nbs.testing.identity.SequentialIdentityGenerator;
import io.cucumber.spring.ScenarioScope;
import jakarta.annotation.PreDestroy;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
@ScenarioScope
public class CodedResultedTestMother {

  private static final String CREATE = """
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
         :lab,
         'OBS',
         'COMP'
       );
      
       insert into Obs_value_coded (
         observation_uid,
         code,
         display_name
       )
       select
         :identifier,
         lab_result_cd,
         lab_result_desc_txt
       from [NBS_SRTE]..[Lab_result]
       where lab_result_cd = :result
       and laboratory_id = 'DEFAULT'
      ;
      """;



  private final SequentialIdentityGenerator idGenerator;
  private final JdbcClient client;
  private final ResultedTestCleaner cleaner;

  private final Collection<Long> created;

  CodedResultedTestMother(
      final SequentialIdentityGenerator idGenerator,
      final JdbcClient client,
      final ResultedTestCleaner cleaner
  ) {
    this.idGenerator = idGenerator;
    this.client = client;
    this.cleaner = cleaner;
    this.created = new ArrayList<>();
  }

  @PreDestroy
  void cleanup() {
    this.cleaner.clean(created);
  }

  void create(final LabReportIdentifier lab, final String test, final String result) {
    long identifier = idGenerator.next();

    this.client.sql(CREATE)
        .param("identifier", identifier)
        .param("lab", lab.identifier())
        .param("test", test)
        .param("result", result)
        .update();

    created.add(identifier);
  }
}
