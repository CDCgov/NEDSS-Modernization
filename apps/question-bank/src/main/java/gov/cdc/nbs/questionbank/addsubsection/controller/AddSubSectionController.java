package gov.cdc.nbs.questionbank.addsubsection.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.addsubsection.CreateSubSectionResponse;
import gov.cdc.nbs.questionbank.addsubsection.CreateSubSectionService;
import gov.cdc.nbs.questionbank.addsubsection.model.CreateSubSectionRequest;


@RestController
@RequestMapping("/api/v1/")
@PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
public class AddSubSectionController {

    private final CreateSubSectionService createSubSectionService;

    private final UserDetailsProvider userDetailsProvider;

    public AddSubSectionController(CreateSubSectionService createSubSectionService,
            UserDetailsProvider userDetailsProvider) {
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
