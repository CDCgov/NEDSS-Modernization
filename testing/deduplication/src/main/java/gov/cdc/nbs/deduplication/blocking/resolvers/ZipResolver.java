package gov.cdc.nbs.deduplication.blocking.resolvers;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import gov.cdc.nbs.deduplication.blocking.request.BlockTransformer;
import gov.cdc.nbs.deduplication.blocking.response.BlockResponse;
import gov.cdc.nbs.deduplication.blocking.response.BlockResponse.BlockMatch;

@Component
public class ZipResolver {
  private static final String BASE_QUERY = """
      SELECT DISTINCT
        pl.zip_cd,
        p.person_parent_uid
      FROM
        postal_locator pl
      JOIN Entity_locator_participation elp ON elp.locator_uid = pl.postal_locator_uid
      JOIN person p ON elp.entity_uid = p.person_uid
      WHERE
      elp.record_status_cd = 'ACTIVE'
      AND p.cd = 'PAT'
        """;

  private static final String NO_TRANSOFORM_QUERY = """
       AND pl.zip_cd = ?
      """;

  private static final String FIRST_FOUR_QUERY = """
       AND LEFT(pl.zip_cd, 4) = LEFT(?, 4)
      """;

  private static final String LAST_FOUR_QUERY = """
       AND RIGHT(pl.zip_cd, 4) = RIGHT(?, 4)
      """;
  private final JdbcTemplate template;

  public ZipResolver(final JdbcTemplate template) {
    this.template = template;
  }

  public BlockResponse resolve(BlockTransformer transformer, String value) {
    String query = BASE_QUERY + switch (transformer) {
      case FIRST_FOUR -> FIRST_FOUR_QUERY;
      case LAST_FOUR -> LAST_FOUR_QUERY;
      default -> NO_TRANSOFORM_QUERY;
    };


    List<BlockMatch> matches = template.query(
        query,
        setter -> setter.setString(1, value),
        BlockMatchMapper.map());

    return new BlockResponse(matches);
  }
}
