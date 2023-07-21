package gov.cdc.nbs.questionbank.addtab.controller;


import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.addtab.CreateTabService;
import gov.cdc.nbs.questionbank.addtab.CreateUiResponse;
import gov.cdc.nbs.questionbank.addtab.exceptions.AddTabException;
import gov.cdc.nbs.questionbank.addtab.model.CreateTabRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;



@Slf4j
@RestController
@RequestMapping("/api/v1/")
public class AddTabController {

    private final CreateTabService createTabService;

    private final UserDetailsProvider userDetailsProvider;

    public AddTabController(CreateTabService createTabService, UserDetailsProvider userDetailsProvider) {
        this.userDetailsProvider = userDetailsProvider;
        this.createTabService = createTabService;
    }

    @PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
    @PostMapping("addtab")
    @ResponseBody
    public CreateUiResponse createTab(@RequestBody CreateTabRequest request) throws AddTabException {
        Long userId = userDetailsProvider.getCurrentUserDetails().getId();
        return createTabService.createTab(userId, request);
    }

}