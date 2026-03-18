package gov.cdc.nbs.event.search.labreport;

import gov.cdc.nbs.event.search.labreport.model.ResultedTest;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

@Component
public class ResultedTestFinder {
  // used in the query to retrieve LOINC ResultedTests - NBS: ObservationProcessor.java #697
  private static List<String> relatedClassCodes =
      Arrays.asList(
          "ABXBACT",
          "BC",
          "CELLMARK",
          "CHAL",
          "CHALSKIN",
          "CHEM",
          "COAG",
          "CYTO",
          "DRUG",
          "DRUG/TOX",
          "HEM",
          "HEM/BC",
          "MICRO",
          "MISC",
          "PANEL.ABXBACT",
          "PANEL.BC",
          "PANEL.CHEM",
          "PANEL.MICRO",
          "PANEL.OBS",
          "PANEL.SERO",
          "PANEL.TOX",
          "PANEL.UA",
          "SERO",
          "SPEC",
          "TOX",
          "UA",
          "VACCIN");

  private static final String QUERY =
      """
          SELECT TOP (:maxPageSize) search.resulted_test
      FROM ( SELECT DISTINCT
              lt.lab_test_desc_txt resulted_test
          FROM
              [NBS_SRTE].[dbo].[Lab_test] lt
          WHERE
              lt.test_type_cd = 'R'
              AND(lt.lab_test_desc_txt LIKE :searchText
                  OR lt.lab_test_cd LIKE :searchText)
          UNION
          SELECT DISTINCT
              lc.component_name resulted_test
          FROM
              [NBS_SRTE].[dbo].[LOINC_code] lc
          WHERE
              lc.related_class_cd in(:relatedClassCodes)
              AND(lc.component_name LIKE :searchText
                  OR lc.loinc_cd LIKE :searchText)) AS search
      ORDER BY
          search.resulted_test
                                      """;

  private final Integer maxPageSize;
  private final NamedParameterJdbcTemplate namedTemplate;

  public ResultedTestFinder(
      JdbcTemplate template, @Value("${nbs.max-page-size: 50}") final Integer maxPageSize) {
    this.namedTemplate = new NamedParameterJdbcTemplate(template);
    this.maxPageSize = maxPageSize;
  }

  /**
   * Query both resulted_test and loinc_code tables for resulted tests.
   *
   * @param searchText
   * @return
   */
  public List<ResultedTest> findDistinctResultedTests(String searchText) {
    SqlParameterSource namedParameters =
        new MapSqlParameterSource(
            Map.of(
                "maxPageSize",
                maxPageSize,
                "relatedClassCodes",
                relatedClassCodes,
                "searchText",
                "%" + searchText + "%"));

    return namedTemplate.query(
        QUERY,
        namedParameters,
        new RowMapper<ResultedTest>() {

          @Override
          public ResultedTest mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new ResultedTest(rs.getString(1));
          }
        });
  }
}
