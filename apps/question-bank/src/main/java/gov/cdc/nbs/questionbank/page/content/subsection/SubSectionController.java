package gov.cdc.nbs.questionbank.page.content.subsection;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.page.content.subsection.request.CreateSubSectionRequest;
import gov.cdc.nbs.questionbank.page.content.subsection.request.UpdateSubSectionRequest;
import gov.cdc.nbs.questionbank.page.content.subsection.response.CreateSubSectionResponse;
import gov.cdc.nbs.questionbank.page.content.subsection.response.UpdateSubSectionResponse;


@RestController
@RequestMapping("/api/v1/pages/{page}/subsections/")
@PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
public class SubsectionController {

    private final SubsectionCreator creator;
    private final SubsectionDeleter deleter;

    private final UserDetailsProvider userDetailsProvider;

    public SubsectionController(
            final SubsectionCreator createSubSectionService,
            final SubsectionDeleter deleter,
            final UserDetailsProvider userDetailsProvider) {
        this.userDetailsProvider = userDetailsProvider;
        this.deleter = deleter;
        this.creator = createSubSectionService;
    }

    @PostMapping
    @ResponseBody
    public CreateSubSectionResponse createSubSection(
            @PathVariable("page") Long page,
            @RequestBody CreateSubSectionRequest request) {
        Long userId = userDetailsProvider.getCurrentUserDetails().getId();
        return creator.createSubSection(page, userId, request);
    }


    @DeleteMapping("{subSectionId}")
    @ResponseBody
    public void deleteSubSection(
            @PathVariable("page") Long page,
            @PathVariable Long subSectionId) {
        deleter.delete(page, subSectionId);
    }

    @PutMapping("{subSectionId}")
    @ResponseBody
    public UpdateSubSectionResponse updateSubSection(@PathVariable("subSectionId") Long subSectionId,
            @RequestBody UpdateSubSectionRequest request) {
        return creator.updateSubSection(subSectionId, request);
    }

}
