package gov.cdc.nbs.questionbank.page.content.tab;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.questionbank.page.content.tab.request.CreateTabRequest;
import gov.cdc.nbs.questionbank.page.content.tab.request.UpdateTabRequest;
import gov.cdc.nbs.questionbank.page.content.tab.response.Tab;
import springfox.documentation.annotations.ApiIgnore;


@RestController
@RequestMapping("/api/v1/pages/{page}/tabs")
@PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
public class TabController {

    private final TabCreator creator;
    private final TabUpdater updater;
    private final TabDeleter deleter;

    public TabController(
            final TabCreator creator,
            final TabUpdater updater,
            final TabDeleter deleter) {
        this.creator = creator;
        this.updater = updater;
        this.deleter = deleter;
    }

    @PostMapping
    public Tab createTab(
            @PathVariable("page") Long page,
            @RequestBody CreateTabRequest request,
            @ApiIgnore @AuthenticationPrincipal final NbsUserDetails details) {
        return creator.create(page, request, details.getId());
    }

    @DeleteMapping("{tabId}")
    public void deleteTab(
            @PathVariable("page") Long page,
            @PathVariable Long tabId,
            @ApiIgnore @AuthenticationPrincipal final NbsUserDetails details) {
        deleter.delete(page, tabId, details.getId());
    }

    @PutMapping("{tabId}")
    public Tab updateTab(
            @PathVariable("page") Long page,
            @PathVariable Long tabId,
            @RequestBody UpdateTabRequest request,
            @ApiIgnore @AuthenticationPrincipal final NbsUserDetails details) {
        return updater.update(page, tabId, request, details.getId());
    }

}
