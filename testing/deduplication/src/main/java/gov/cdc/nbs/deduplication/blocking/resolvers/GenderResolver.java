package gov.cdc.nbs.deduplication.blocking.resolvers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import gov.cdc.nbs.deduplication.blocking.response.BlockResponse;
import gov.cdc.nbs.deduplication.blocking.response.BlockResponse.BlockMatch;
import org.springframework.jdbc.core.RowMapper;


@Component
public class GenderResolver {
  private static final String QUERY = """
      SELECT DISTINCT
        birth_gender_cd,
        person_parent_uid
      FROM
        person
      WHERE
        record_status_cd = 'ACTIVE'
        AND cd = 'PAT'
        AND (birth_gender_cd = ? OR curr_sex_cd = ?)
              """;
  private final JdbcTemplate template;

  public GenderResolver(final JdbcTemplate template) {
    this.template = template;
  }

  public BlockResponse resolve(String value) {
    List<BlockMatch> matches = template.query(
        QUERY,
        setter -> {
          setter.setString(1, value);
          setter.setString(2, value);
        },
        mapper(value));
    return new BlockResponse(matches);
  }

  private RowMapper<BlockMatch> mapper(String value) {
    return new RowMapper<BlockMatch>() {

      @Override
      public BlockMatch mapRow(final ResultSet resultSet, final int row) throws SQLException {
        return new BlockMatch(
            resultSet.getString(2), // person_parent_uid
            value); // provided gender value
      }
    };
  }

}
