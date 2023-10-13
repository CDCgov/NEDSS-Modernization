package gov.cdc.nbs.questionbank.page.content.reorder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import gov.cdc.nbs.questionbank.page.content.reorder.models.PageEntry;
import gov.cdc.nbs.questionbank.page.content.reorder.models.ReorderRequest;
import gov.cdc.nbs.questionbank.page.content.reorder.models.ReorderablePage;
import gov.cdc.nbs.questionbank.page.content.reorder.models.ReorderablePage.Element;
import gov.cdc.nbs.questionbank.page.content.reorder.models.ReorderablePage.Section;
import gov.cdc.nbs.questionbank.page.content.reorder.models.ReorderablePage.Subsection;
import gov.cdc.nbs.questionbank.page.content.reorder.models.ReorderablePage.Tab;

@Component
public class Reorderer {
    private static final int TAB = 1010;
    private static final int SECTION = 1015;
    private static final int SUBSECTION = 1016;

    private static final String SELECT = """
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

    private static final String UPDATE = """
            UPDATE WA_UI_metadata
            SET order_nbr = ?
            WHERE wa_ui_metadata_uid = ?
            """;


    private final JdbcTemplate template;

    public Reorderer(final JdbcTemplate template) {
        this.template = template;
    }

    /**
     * * Applies the specified ordering. Selects all contents of a page, reorders the specified item (and its content),
     * and then updates each entity to have the correct order_nbr
     * 
     * @param pageId
     * @param request {@link ReorderRequest}
     */
    public void apply(long pageId, ReorderRequest request) {
        // Get page content from db
        List<PageEntry> entries = fetchEntries(pageId);

        if (entries == null || entries.isEmpty()) {
            throw new ReorderException("Failed to find draft page with id: " + pageId);
        }

        // Construct objects from content
        SimplePageMapper mapper = new SimplePageMapper();
        ReorderablePage page = mapper.toPage(entries);

        // Find what type we are moving
        int type = entries.stream()
                .filter(e -> e.id() == request.element())
                .findFirst()
                .orElseThrow(() -> new ReorderException("Failed to find element to move"))
                .component();

        // Move item
        switch (type) {
            case TAB:
                page.moveTab(request.element(), request.afterElement());
                break;
            case SECTION:
                page.moveSection(request.element(), request.afterElement());
                break;
            case SUBSECTION:
                page.moveSubsection(request.element(), request.afterElement());
                break;
            default:
                page.moveElement(request.element(), request.afterElement());
                break;
        }

        // Update order numbers for page
        int orderNumber = 2; // First Tab is at order 2
        for (Tab t : page.getTabs()) {
            template.update(UPDATE, orderNumber++, t.getId());
            for (Section s : t.getSections()) {
                template.update(UPDATE, orderNumber++, s.getId());
                for (Subsection ss : s.getSubsections()) {
                    template.update(UPDATE, orderNumber++, ss.getId());
                    for (Element e : ss.getElements()) {
                        template.update(UPDATE, orderNumber++, e.getId());
                    }
                }
            }
        }
    }

    /**
     * Get the content of the specified page
     */
    private List<PageEntry> fetchEntries(long pageId) {
        return this.template.query(
                SELECT,
                setter -> setter.setLong(1, pageId),
                new RowMapper<PageEntry>() {

                    @Override
                    public PageEntry mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return new PageEntry(
                                rs.getLong(1),
                                rs.getInt(2),
                                rs.getInt(3));
                    }
                });
    }

}
