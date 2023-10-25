package gov.cdc.nbs.questionbank.page.content.reorder;

import org.springframework.stereotype.Component;
import gov.cdc.nbs.questionbank.page.content.reorder.models.ReorderablePage;

@Component
public class Reorderer {
    private final ReorderablePageFinder finder;
    private final OrderUpdater orderUpdater;

    public Reorderer(
            final ReorderablePageFinder finder,
            final OrderUpdater orderUpdater) {
        this.finder = finder;
        this.orderUpdater = orderUpdater;
    }

    public void apply(long pageId, long toMoveId, Long afterId) {
        // Get the specified page
        ReorderablePage page = finder.find(pageId);

        // Perform the move operation
        page.move(toMoveId, afterId);

        // Update the db to reflect the reorder
        orderUpdater.update(page);
    }

}
