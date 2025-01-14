package gov.cdc.nbs.event.report.lab.test;

import gov.cdc.nbs.event.report.lab.LabReportIdentifier;
import gov.cdc.nbs.testing.identity.SequentialIdentityGenerator;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.Map;

@Component
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
  private final NamedParameterJdbcTemplate template;

  CodedResultedTestMother(
      final SequentialIdentityGenerator idGenerator,
      final NamedParameterJdbcTemplate template
  ) {
    this.idGenerator = idGenerator;
    this.template = template;
  }

  void create(final LabReportIdentifier lab, final String test, final String result) {
    long identifier = idGenerator.next();

    SqlParameterSource parameters = new MapSqlParameterSource(
        Map.of(
            "identifier", identifier,
            "lab", lab.identifier(),
            "test", test,
            "result", result
        )
    );

    template.execute(
        CREATE,
        parameters,
        PreparedStatement::executeUpdate
    );
  }
}
