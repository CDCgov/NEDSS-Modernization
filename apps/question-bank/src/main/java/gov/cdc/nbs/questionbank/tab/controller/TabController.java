package gov.cdc.nbs.questionbank.tab.controller;


import gov.cdc.nbs.questionbank.section.model.DeleteSectionRequest;
import gov.cdc.nbs.questionbank.section.model.DeleteSectionResponse;
import gov.cdc.nbs.questionbank.section.model.UpdateSectionRequest;
import gov.cdc.nbs.questionbank.section.model.UpdateSectionResponse;
import gov.cdc.nbs.questionbank.tab.model.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.tab.TabService;
import gov.cdc.nbs.questionbank.tab.exceptions.AddTabException;


@RestController
@RequestMapping("/api/v1/")
@PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
public class TabController {

    private final TabService tabService;

    private final UserDetailsProvider userDetailsProvider;

    public TabController(TabService tabService, UserDetailsProvider userDetailsProvider) {
        this.userDetailsProvider = userDetailsProvider;
        this.tabService = tabService;
    }

    @PostMapping("addtab")
    @ResponseBody
    public CreateTabResponse createTab(@RequestBody CreateTabRequest request) throws AddTabException {
        Long userId = userDetailsProvider.getCurrentUserDetails().getId();
        return tabService.createTab(userId, request);
    }

    @DeleteMapping("deletetab")
    @ResponseBody
    public DeleteTabResponse deleteTab(@RequestBody DeleteTabRequest request) {
        return tabService.deleteTab(request);
    }

    @PutMapping("updatetab")
    @ResponseBody
    public UpdateTabResponse updateTab(@RequestBody UpdateTabRequest request) {
        return tabService.updateTab(request);
    }
}
