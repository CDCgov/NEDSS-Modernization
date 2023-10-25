package gov.cdc.nbs.questionbank.page.content.tab;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import gov.cdc.nbs.questionbank.entity.repository.WaTemplateRepository;
import gov.cdc.nbs.questionbank.entity.repository.WaUiMetadataRepository;
import gov.cdc.nbs.questionbank.page.content.tab.exceptions.DeleteTabException;

@Component
@Transactional
public class TabDeleter {
    private static final String SELECT_NEXT_COMPONENT = """
            SELECT
                nbs_ui_component_uid
            FROM
                WA_UI_metadata
            WHERE
                wa_template_uid = ?
                AND order_nbr = (
                    SELECT
                        order_nbr + 1
                    FROM
                        WA_UI_metadata
                    WHERE
                        wa_ui_metadata_uid = ?
                        AND nbs_ui_component_uid = 1010)
                ORDER BY
                    order_nbr ASC;
                    """;

    private final WaUiMetadataRepository repository;
    private final WaTemplateRepository templateRepository;
    private final JdbcTemplate template;

    public TabDeleter(
            final WaUiMetadataRepository repository,
            final WaTemplateRepository templateRepository,
            final JdbcTemplate template) {
        this.repository = repository;
        this.templateRepository = templateRepository;
        this.template = template;
    }

    public void delete(Long page, Long tab) {
        if (!templateRepository.isPageDraft(page)) {
            throw new DeleteTabException("Unable to delete tab on a published page");
        }
        if (!isTabEmpty(page, tab)) {
            throw new DeleteTabException("Unable to delete a tab with content");
        }
        // get the tabs current order_nbr
        Integer orderNbr = repository.findOrderNumber(tab)
                .orElseThrow(() -> new DeleteTabException("Failed to find tab with id: " + tab));

        // delete the tab entry
        repository.deleteById(tab);

        // reorder elements after the deleted tab
        repository.decrementOrderNumbers(orderNbr, tab);
    }

    /**
     * Get the component type of the next entry in the page. If it is null or if it is 1010 (Tab), then the tab is empty
     * 
     * @param page
     * @param tab
     * @return
     */
    private boolean isTabEmpty(Long page, Long tab) {
        List<Long> result = template.query(
                SELECT_NEXT_COMPONENT,
                t -> {
                    t.setLong(1, page);
                    t.setLong(2, tab);
                },
                (rs, row) -> rs.getLong(1));
        return result.isEmpty() || result.get(0) == 1010;
    }
}
