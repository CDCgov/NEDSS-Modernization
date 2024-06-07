package gov.cdc.nbs.deduplication.blocking.resolvers;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import gov.cdc.nbs.deduplication.blocking.request.BlockTransformer;
import gov.cdc.nbs.deduplication.blocking.response.BlockResponse;
import gov.cdc.nbs.deduplication.blocking.response.BlockResponse.BlockMatch;

@Component
public class FirstNameResolver {
  private static final String BASE_QUERY = """
      SELECT DISTINCT
        pn.first_nm,
        p.person_parent_uid
      FROM
        person_name pn
        JOIN person p ON p.person_uid = pn.person_uid
      WHERE
        pn.record_status_cd = 'ACTIVE'
        AND pn.nm_use_cd = 'L'
        AND p.cd = 'PAT'
          """;

  private static final String NO_TRANSOFORM_QUERY = """
       AND pn.first_nm = ?
      """;

  private static final String FIRST_FOUR_QUERY = """
       AND LEFT(pn.first_nm, 4) = LEFT(?, 4)
      """;

  private static final String LAST_FOUR_QUERY = """
       AND RIGHT(pn.first_nm, 4) = RIGHT(?, 4)
      """;

  private final JdbcTemplate template;

  public FirstNameResolver(final JdbcTemplate template) {
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
