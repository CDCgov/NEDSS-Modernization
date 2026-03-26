package gov.cdc.nbs.questionbank.page.content.reorder;

import gov.cdc.nbs.questionbank.page.content.reorder.models.PageEntry;
import gov.cdc.nbs.questionbank.page.content.reorder.models.ReorderablePage;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderUpdater {

  private static final String UPDATE =
      """
            UPDATE WA_UI_metadata
            SET order_nbr = ?
            WHERE wa_ui_metadata_uid = ?
            """;

  private final JdbcTemplate template;

  public OrderUpdater(final JdbcTemplate template) {
    this.template = template;
  }

  /**
   * Updates the database to reflect the content order of the provided ReoderablePage
   *
   * @param page
   */
  public void update(ReorderablePage page) {
    // First Tab is at order 2
    for (PageEntry entry : page.toPageEntries()) {
      template.update(UPDATE, entry.orderNumber(), entry.id());
    }
  }
}
