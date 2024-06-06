package gov.cdc.nbs.deduplication.blocking.resolvers;

import java.time.LocalDate;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import gov.cdc.nbs.deduplication.blocking.response.BlockResponse;
import gov.cdc.nbs.deduplication.blocking.response.BlockResponse.BlockMatch;

@Component
public class BirthDateResolver {
  // Date of birth is stored as a Timestamp 1900-01-30 00:00:00.000
  private static final String QUERY = """
      SELECT DISTINCT
        birth_time,
        person_parent_uid
      FROM
        person
      WHERE
        record_status_cd = 'ACTIVE'
        AND cd = 'PAT'
        AND year(birth_time) = ?
        AND month(birth_time) = ?
        AND day(birth_time) = ?
        AND birth_time IS NOT NULL;
              """;

  private final JdbcTemplate template;

  public BirthDateResolver(final JdbcTemplate template) {
    this.template = template;
  }

  public BlockResponse resolve(String value) {
    // Currently only supports yyyy-dd-MM format
    LocalDate date = LocalDate.parse(value);
    List<BlockMatch> matches = template.query(
        QUERY,
        setter -> {
          setter.setInt(1, date.getYear());
          setter.setInt(2, date.getMonthValue());
          setter.setInt(3, date.getDayOfMonth());
        },
        BlockMatchMapper.map());
    return new BlockResponse(matches);
  }

}
