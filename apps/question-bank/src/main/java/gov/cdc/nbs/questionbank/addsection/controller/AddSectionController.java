package gov.cdc.nbs.questionbank.addsection.controller;


import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.addsection.CreateSectionResponse;
import gov.cdc.nbs.questionbank.addsection.CreateSectionService;
import gov.cdc.nbs.questionbank.addsection.model.CreateSectionRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/api/v1/")
@PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
public class AddSectionController {

    private final CreateSectionService createSectionService;

    private final UserDetailsProvider userDetailsProvider;

    public AddSectionController(CreateSectionService createSectionService, UserDetailsProvider userDetailsProvider) {
        this.userDetailsProvider = userDetailsProvider;
        this.createSectionService = createSectionService;
    }

    @PostMapping("addsection")
    @ResponseBody
    public CreateSectionResponse createSection(@RequestBody CreateSectionRequest request) {
        Long userId = userDetailsProvider.getCurrentUserDetails().getId();
        return createSectionService.createSection(userId, request);
    }

}