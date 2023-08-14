package gov.cdc.nbs.questionbank.page.content.question;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.page.content.question.request.AddQuestionRequest;
import gov.cdc.nbs.questionbank.page.content.question.response.AddQuestionResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/pages/{page}/questions/")
@PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
public class PageQuestionController {

    private final UserDetailsProvider userDetailsProvider;
    private final PageQuestionCreator creator;


    public PageQuestionController(
            final PageQuestionCreator contentManager,
            final UserDetailsProvider userDetailsProvider) {
        this.userDetailsProvider = userDetailsProvider;
        this.creator = contentManager;
    }

    @PostMapping
    public AddQuestionResponse addQuestionToPage(
            @PathVariable("page") Long pageId,
            @RequestBody AddQuestionRequest request) {
        log.debug("Received add question to page request");
        Long userId = userDetailsProvider.getCurrentUserDetails().getId();
        Long componentId = creator.addQuestion(pageId, request, userId);
        log.debug("COmpleted add question to page request");
        return new AddQuestionResponse(componentId);
    }
}
