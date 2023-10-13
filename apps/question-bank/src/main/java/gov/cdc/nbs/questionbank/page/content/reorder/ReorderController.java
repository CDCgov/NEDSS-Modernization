package gov.cdc.nbs.questionbank.page.content.reorder;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import gov.cdc.nbs.questionbank.page.content.reorder.models.ReorderRequest;

@RestController
@RequestMapping("/api/v1/pages/{page}/reorder/")
@PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
public class ReorderController {

    private final Reorderer reorderer;

    public ReorderController(final Reorderer reorderer) {
        this.reorderer = reorderer;
    }

    @PostMapping
    public void reorderItem(
            @PathVariable("page") Long pageId,
            @RequestBody ReorderRequest request) {
        reorderer.apply(pageId, request);
    }
}
