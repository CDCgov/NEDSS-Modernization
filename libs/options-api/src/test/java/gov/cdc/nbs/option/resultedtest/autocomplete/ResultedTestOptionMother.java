package gov.cdc.nbs.option.resultedtest.autocomplete;

import io.cucumber.spring.ScenarioScope;
import jakarta.annotation.PreDestroy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;

@Component
@ScenarioScope
class ResultedTestOptionMother {

  private static final String DELETE_LAB_TEST = """
      delete from NBS_SRTE.dbo.Lab_test
      """;

  private static final String DELETE_LOINC_CODE = """
      delete from NBS_SRTE.dbo.LOINC_code
      """;

  private static final String CREATE_LAB_TEST =
      """
              insert into NBS_SRTE.dbo.Lab_coding_system(laboratory_id) SELECT 'LABID1' WHERE NOT EXISTS (SELECT * FROM NBS_SRTE.dbo.Lab_coding_system WHERE laboratory_id='LABID1');
              insert into NBS_SRTE.dbo.Lab_test(lab_test_cd,laboratory_id,lab_test_desc_txt,test_type_cd,nbs_uid) values (:code,'LABID1',:name,'R',1);
          """;

  private static final String CREATE_LOINC_CODE =
      """
              insert into NBS_SRTE.dbo.LOINC_code(loinc_cd,component_name,related_class_cd) values (:code,:name,'ABXBACT');
          """;

  private final NamedParameterJdbcTemplate template;

  ResultedTestOptionMother(final JdbcTemplate template) {
    this.template = new NamedParameterJdbcTemplate(template);
  }

  @PreDestroy
  void reset() {
    Map<String, List<String>> parameters = Map.of();

    template.execute(
        DELETE_LAB_TEST,
        new MapSqlParameterSource(parameters),
        PreparedStatement::executeUpdate);
    template.execute(
        DELETE_LOINC_CODE,
        new MapSqlParameterSource(parameters),
        PreparedStatement::executeUpdate);

  }

  void createLoincResultedTest(final String code, final String name) {
    Map<String, ? extends Serializable> parameters = Map.of(
        "code", code,
        "name", name);

    template.execute(
        CREATE_LOINC_CODE,
        new MapSqlParameterSource(parameters),
        PreparedStatement::executeUpdate);
  }

  void createLocalResultedTest(final String code, final String name) {
    Map<String, ? extends Serializable> parameters = Map.of(
        "code", code,
        "name", name);

    template.execute(
        CREATE_LAB_TEST,
        new MapSqlParameterSource(parameters),
        PreparedStatement::executeUpdate);

  }

}
