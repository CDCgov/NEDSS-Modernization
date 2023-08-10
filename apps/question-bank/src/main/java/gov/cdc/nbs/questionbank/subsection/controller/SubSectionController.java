package gov.cdc.nbs.questionbank.subsection.controller;


import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.subsection.model.CreateSubSectionResponse;
import gov.cdc.nbs.questionbank.subsection.SubSectionService;
import gov.cdc.nbs.questionbank.subsection.model.DeleteSubSectionRequest;
import gov.cdc.nbs.questionbank.subsection.model.CreateSubSectionRequest;
import gov.cdc.nbs.questionbank.subsection.model.DeleteSubSectionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/api/v1/")
@PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
public class SubSectionController {

    private final SubSectionService createSubSectionService;

    private final UserDetailsProvider userDetailsProvider;

    public SubSectionController(SubSectionService createSubSectionService, UserDetailsProvider userDetailsProvider) {
        this.userDetailsProvider = userDetailsProvider;
        this.createSubSectionService = createSubSectionService;
    }
    @PostMapping("addsubsection")
    @ResponseBody
    public CreateSubSectionResponse createSubSection(@RequestBody CreateSubSectionRequest request) {
        Long userId = userDetailsProvider.getCurrentUserDetails().getId();
        return createSubSectionService.createSubSection(userId, request);
    }

    @PostMapping("deletesubsection")
    @ResponseBody
    public DeleteSubSectionResponse deleteSubSection(@RequestBody DeleteSubSectionRequest request) {
        return createSubSectionService.deleteSubSection(request);
    }

}