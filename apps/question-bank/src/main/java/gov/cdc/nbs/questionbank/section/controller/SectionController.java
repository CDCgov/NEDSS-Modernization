package gov.cdc.nbs.questionbank.section.controller;


import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.section.model.CreateSectionResponse;
import gov.cdc.nbs.questionbank.section.SectionService;
import gov.cdc.nbs.questionbank.section.model.CreateSectionRequest;
import gov.cdc.nbs.questionbank.section.model.DeleteSectionRequest;
import gov.cdc.nbs.questionbank.section.model.DeleteSectionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/api/v1/")
@PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
public class SectionController {

    private final SectionService createSectionService;

    private final UserDetailsProvider userDetailsProvider;

    public SectionController(SectionService createSectionService, UserDetailsProvider userDetailsProvider) {
        this.userDetailsProvider = userDetailsProvider;
        this.createSectionService = createSectionService;
    }

    @PostMapping("addsection")
    @ResponseBody
    public CreateSectionResponse createSection(@RequestBody CreateSectionRequest request) {
        Long userId = userDetailsProvider.getCurrentUserDetails().getId();
        return createSectionService.createSection(userId, request);
    }

    @PostMapping("deletesection")
    @ResponseBody
    public DeleteSectionResponse deleteSection(@RequestBody DeleteSectionRequest request) {
        return createSectionService.deleteSection(request);
    }

}