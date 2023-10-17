package gov.cdc.nbs.questionbank.page.content.reorder;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/pages/{page}/component/{component}")
@PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
public class ReorderController {

    private final Reorderer reorderer;

    public ReorderController(final Reorderer reorderer) {
        this.reorderer = reorderer;
    }

    @PutMapping("/after/{after}")
    public void orderComponentAfter(
            @PathVariable("page") Long pageId,
            @PathVariable("component") Long component,
            @PathVariable("after") Long after) {
        reorderer.apply(pageId, component, after);
    }
}
