package gov.cdc.nbs.questionbank.addsubsection.controller;


import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.addsubsection.CreateSubSectionResponse;
import gov.cdc.nbs.questionbank.addsubsection.CreateSubSectionService;
import gov.cdc.nbs.questionbank.addsubsection.model.CreateSubSectionRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/api/v1/")
@PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
public class AddSubSectionController {

    private final CreateSubSectionService createSubSectionService;

    private final UserDetailsProvider userDetailsProvider;

    public AddSubSectionController(CreateSubSectionService createSubSectionService, UserDetailsProvider userDetailsProvider) {
        this.userDetailsProvider = userDetailsProvider;
        this.createSubSectionService = createSubSectionService;
    }
    @PostMapping("addsubsection")
    @ResponseBody
    public CreateSubSectionResponse createSubSection(@RequestBody CreateSubSectionRequest request) {
        Long userId = userDetailsProvider.getCurrentUserDetails().getId();
        return createSubSectionService.createSubSection(userId, request);
    }

}