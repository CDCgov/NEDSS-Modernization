package gov.cdc.nbs.questionbank.addtab.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.addtab.CreateTabService;
import gov.cdc.nbs.questionbank.addtab.CreateUiResponse;
import gov.cdc.nbs.questionbank.addtab.exceptions.AddTabException;
import gov.cdc.nbs.questionbank.addtab.model.CreateTabRequest;



@RestController
@RequestMapping("/api/v1/")
@PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
public class AddTabController {

    private final CreateTabService createTabService;

    private final UserDetailsProvider userDetailsProvider;

    public AddTabController(CreateTabService createTabService, UserDetailsProvider userDetailsProvider) {
        this.userDetailsProvider = userDetailsProvider;
        this.createTabService = createTabService;
    }

    @PostMapping("addtab")
    @ResponseBody
    public CreateUiResponse createTab(@RequestBody CreateTabRequest request) throws AddTabException {
        Long userId = userDetailsProvider.getCurrentUserDetails().getId();
        return createTabService.createTab(userId, request);
    }

}
