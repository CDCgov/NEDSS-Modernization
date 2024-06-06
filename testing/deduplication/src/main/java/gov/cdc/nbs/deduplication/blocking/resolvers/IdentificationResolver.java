package gov.cdc.nbs.deduplication.blocking.resolvers;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import gov.cdc.nbs.deduplication.blocking.request.BlockRequest.BlockTransformer;
import gov.cdc.nbs.deduplication.blocking.response.BlockResponse;
import gov.cdc.nbs.deduplication.blocking.response.BlockResponse.BlockMatch;

@Component
public class IdentificationResolver {
  private static final String BASE_QUERY = """
      SELECT DISTINCT
        id.root_extension_txt,
        p.person_parent_uid
      FROM
        entity_id id
        JOIN person p ON p.person_uid = id.entity_uid
      WHERE
        id.record_status_cd = 'ACTIVE'
        AND id.type_cd = ?
        AND p.cd = 'PAT'
            """;

  private static final String NO_TRANSOFORM_QUERY = """
       AND id.root_extension_txt = ?
      """;

  private static final String FIRST_FOUR_QUERY = """
       AND LEFT(id.root_extension_txt, 4) = LEFT(?, 4)
      """;

  private static final String LAST_FOUR_QUERY = """
       AND RIGHT(id.root_extension_txt, 4) = RIGHT(?, 4)
      """;
  private final JdbcTemplate template;

  public IdentificationResolver(final JdbcTemplate template) {
    this.template = template;
  }

  public BlockResponse resolve(BlockTransformer transformer, String identificationType, String value) {
    String query = BASE_QUERY + switch (transformer) {
      case FIRST_FOUR -> FIRST_FOUR_QUERY;
      case LAST_FOUR -> LAST_FOUR_QUERY;
      default -> NO_TRANSOFORM_QUERY;
    };


    List<BlockMatch> matches = template.query(
        query,
        setter -> {
          setter.setString(1, identificationType);
          setter.setString(2, value);
        },
        BlockMatchMapper.map());

    return new BlockResponse(matches);
  }
}
