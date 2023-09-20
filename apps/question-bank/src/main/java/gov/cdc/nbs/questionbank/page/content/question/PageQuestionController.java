package gov.cdc.nbs.questionbank.page.content.question;

import gov.cdc.nbs.questionbank.page.content.question.request.OrderQuestionRequest;
import gov.cdc.nbs.questionbank.page.content.question.response.OrderQuestionResponse;
import gov.cdc.nbs.questionbank.page.content.section.response.OrderSectionResponse;
import gov.cdc.nbs.questionbank.page.content.tab.request.OrderTabRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
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

    @PutMapping("orderquestion")
    @ResponseBody
    public OrderQuestionResponse orderQuestion(
            @PathVariable("page") Long page,
            @RequestBody OrderQuestionRequest request
    ) {
        return creator.orderQuestion(page, request);
    }
}
