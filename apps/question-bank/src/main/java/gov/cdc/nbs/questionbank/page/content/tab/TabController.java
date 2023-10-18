package gov.cdc.nbs.questionbank.page.content.tab;


import gov.cdc.nbs.questionbank.page.content.tab.request.UpdateTabRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.page.content.tab.request.CreateTabRequest;
import gov.cdc.nbs.questionbank.page.content.tab.response.Tab;


@RestController
@RequestMapping("/api/v1/pages/{page}/tabs/")
@PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
public class TabController {

    private final TabCreator creator;
    private final TabUpdater updater;
    private final TabDeleter deleter;

    private final UserDetailsProvider userDetailsProvider;

    public TabController(
            final TabCreator creator,
            final TabUpdater updater,
            final UserDetailsProvider userDetailsProvider,
            final TabDeleter deleter) {
        this.userDetailsProvider = userDetailsProvider;
        this.creator = creator;
        this.updater = updater;
        this.deleter = deleter;
    }

    @PostMapping
    public Tab createTab(
            @PathVariable("page") Long page,
            @RequestBody CreateTabRequest request) {
        Long userId = userDetailsProvider.getCurrentUserDetails().getId();
        return creator.create(page, userId, request);
    }

    @DeleteMapping("{tabId}")
    public void deleteTab(@PathVariable("page") Long page, @PathVariable Long tabId) {
        deleter.deleteTab(page, tabId);
    }

    @PutMapping("{tabId}")
    public Tab updateTab(
            @PathVariable("page") Long page,
            @PathVariable Long tabId,
            @RequestBody UpdateTabRequest request) {
        return updater.update(page, tabId, request);
    }

}
