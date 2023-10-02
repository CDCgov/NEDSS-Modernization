package gov.cdc.nbs.event.search.labreport;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.elasticsearch.core.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;
import gov.cdc.nbs.event.search.labreport.model.CodedResult;

@Component
public class CodedResultFinder {
    private final Integer maxPageSize;
    private final NamedParameterJdbcTemplate namedTemplate;

    private static final String QUERY = """
              SELECT
              TOP 50 search.coded_result FROM
              (
                SELECT
                  lr.lab_result_desc_txt coded_result
                FROM
                  [NBS_SRTE].[dbo].[Lab_result] lr
                WHERE
                  lr.organism_name_ind = 'N'
                  AND(
                    lr.lab_result_desc_txt LIKE :searchText
                    OR lr.lab_result_cd LIKE :searchText
                  )
                UNION
                SELECT
                  sc.snomed_desc_txt coded_result
                FROM
                  [NBS_SRTE].[dbo].[Snomed_code] sc
                WHERE
                  sc.snomed_desc_txt LIKE :searchText
                  OR sc.snomed_cd LIKE :searchText
              ) AS search
            ORDER BY
              search.coded_result
                       """;

    public CodedResultFinder(
            final JdbcTemplate template,
            @Value("${nbs.max-page-size: 50}") final Integer maxPageSize) {
        this.namedTemplate = new NamedParameterJdbcTemplate(template);
        this.maxPageSize = maxPageSize;
    }

    public List<CodedResult> findDistinctCodedResults(String searchText) {
        SqlParameterSource namedParameters = new MapSqlParameterSource(
                Map.of("maxPageSize", maxPageSize, "searchText", "%" + searchText + "%"));

        return namedTemplate.query(QUERY, namedParameters, new RowMapper<CodedResult>() {

            @Override
            public CodedResult mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new CodedResult(rs.getString(1));
            }

        });
    }

}
