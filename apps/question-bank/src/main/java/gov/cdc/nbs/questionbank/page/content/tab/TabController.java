package gov.cdc.nbs.questionbank.page.content.tab;


import gov.cdc.nbs.questionbank.page.content.tab.request.DeleteTabRequest;
import gov.cdc.nbs.questionbank.page.content.tab.request.UpdateTabRequest;
import gov.cdc.nbs.questionbank.page.content.tab.response.DeleteTabResponse;
import gov.cdc.nbs.questionbank.page.content.tab.response.UpdateTabResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.page.content.tab.request.CreateTabRequest;
import gov.cdc.nbs.questionbank.page.content.tab.response.CreateTabResponse;


@RestController
@RequestMapping("/api/v1/pages/{page}/tabs/")
@PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
public class TabController {

    private final TabCreator creator;

    private final UserDetailsProvider userDetailsProvider;

    public TabController(TabCreator createTabService, UserDetailsProvider userDetailsProvider) {
        this.userDetailsProvider = userDetailsProvider;
        this.creator = createTabService;
    }

    @PostMapping
    @ResponseBody
    public CreateTabResponse createTab(
            @PathVariable("page") Long page,
            @RequestBody CreateTabRequest request) {
        Long userId = userDetailsProvider.getCurrentUserDetails().getId();
        return creator.createTab(page, userId, request);
    }

    @DeleteMapping("deletetab")
    @ResponseBody
    public DeleteTabResponse deleteTab(@RequestBody DeleteTabRequest request) {
        return creator.deleteTab(request);
    }

    @PutMapping("updatetab")
    @ResponseBody
    public UpdateTabResponse updateTab(@RequestBody UpdateTabRequest request) {
        return creator.updateTab(request);
    }

}
