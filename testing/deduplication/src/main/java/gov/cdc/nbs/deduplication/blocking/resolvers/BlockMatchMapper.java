package gov.cdc.nbs.deduplication.blocking.resolvers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import gov.cdc.nbs.deduplication.blocking.response.BlockResponse.BlockMatch;

public class BlockMatchMapper {
  private BlockMatchMapper() {}

  static RowMapper<BlockMatch> map() {
    return new RowMapper<BlockMatch>() {

      @Override
      public BlockMatch mapRow(final ResultSet resultSet, final int row) throws SQLException {
        return new BlockMatch(
            resultSet.getString(2), // person_parent_uid
            resultSet.getString(1)); // value
      }
    };
  }
}
