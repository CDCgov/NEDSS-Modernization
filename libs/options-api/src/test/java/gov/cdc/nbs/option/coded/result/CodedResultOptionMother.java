package gov.cdc.nbs.option.coded.result;

import io.cucumber.spring.ScenarioScope;
import jakarta.annotation.PreDestroy;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@ScenarioScope
class CodedResultOptionMother {

  private static final String DELETE_LOCAL =
      """
      delete from NBS_SRTE.dbo.Lab_result where laboratory_id = 'LAB_TESTING'
      """;

  private static final String DELETE_SNOMED_CODE =
      """
      delete from NBS_SRTE.dbo.Snomed_code where source_version_id = '4_TESTING'
      """;

  private static final String CREATE_LOCAL =
      """
      insert into NBS_SRTE.dbo.Lab_coding_system(laboratory_id) SELECT 'LAB_TESTING' WHERE NOT EXISTS (SELECT * FROM NBS_SRTE.dbo.Lab_coding_system WHERE laboratory_id='LAB_TESTING');
      insert into NBS_SRTE.dbo.Lab_result(lab_result_cd,laboratory_id,lab_result_desc_txt,organism_name_ind) values (:code, 'LAB_TESTING', :name,'N');
      """;

  private static final String CREATE_SNOMED_CODE =
      """
      insert into NBS_SRTE.dbo.Snomed_code(snomed_cd, snomed_desc_txt, source_concept_id, source_version_id) values (:code, :name, :code, '4_TESTING');
      """;

  private final NamedParameterJdbcTemplate template;

  CodedResultOptionMother(final JdbcTemplate template) {
    this.template = new NamedParameterJdbcTemplate(template);
  }

  @PreDestroy
  void reset() {
    Map<String, List<String>> parameters = Map.of();

    template.execute(
        DELETE_LOCAL, new MapSqlParameterSource(parameters), PreparedStatement::executeUpdate);
    template.execute(
        DELETE_SNOMED_CODE,
        new MapSqlParameterSource(parameters),
        PreparedStatement::executeUpdate);
  }

  void createSNOMED(final String code, final String name) {
    Map<String, ? extends Serializable> parameters =
        Map.of(
            "code", code,
            "name", name);

    template.execute(
        CREATE_SNOMED_CODE,
        new MapSqlParameterSource(parameters),
        PreparedStatement::executeUpdate);
  }

  void createLocal(final String code, final String name) {
    Map<String, ? extends Serializable> parameters =
        Map.of(
            "code", code,
            "name", name);

    template.execute(
        CREATE_LOCAL, new MapSqlParameterSource(parameters), PreparedStatement::executeUpdate);
  }
}
