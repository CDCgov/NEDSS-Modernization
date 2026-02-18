package gov.cdc.nbs.questionbank.page.content.reorder;

import gov.cdc.nbs.questionbank.page.content.reorder.models.PageEntry;
import gov.cdc.nbs.questionbank.page.content.reorder.models.ReorderablePage;
import gov.cdc.nbs.questionbank.page.content.reorder.models.SimplePageMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class ReorderablePageFinder {

  private static final String SELECT =
      """
            SELECT
                wa_ui_metadata_uid,
                nbs_ui_component_uid,
                order_nbr
            FROM
                wa_template
                JOIN WA_UI_metadata ON wa_template.wa_template_uid = WA_UI_metadata.wa_template_uid
            WHERE
                    wa_template.wa_template_uid = ?
                    AND wa_template.template_type = 'Draft'
                    AND order_nbr > 0
            ORDER BY
                order_nbr ASC
                        """;

  private final JdbcTemplate template;

  public ReorderablePageFinder(final JdbcTemplate template) {
    this.template = template;
  }

  public ReorderablePage find(long pageId) {
    // Get page content from db
    List<PageEntry> entries = fetchEntries(pageId);

    if (entries.isEmpty()) {
      throw new ReorderException("Failed to find draft page with id: " + pageId);
    }

    // Construct objects from content
    SimplePageMapper mapper = new SimplePageMapper();
    return mapper.toPage(entries);
  }

  private List<PageEntry> fetchEntries(long pageId) {
    return this.template.query(
        SELECT,
        setter -> setter.setLong(1, pageId),
        new RowMapper<PageEntry>() {

          @Override
          public PageEntry mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new PageEntry(rs.getLong(1), rs.getInt(2), rs.getInt(3));
          }
        });
  }
}
