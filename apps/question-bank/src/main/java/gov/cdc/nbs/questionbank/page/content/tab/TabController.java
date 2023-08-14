package gov.cdc.nbs.questionbank.page.content.tab;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
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

}
