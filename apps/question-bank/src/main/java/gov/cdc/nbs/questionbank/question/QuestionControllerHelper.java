package gov.cdc.nbs.questionbank.question;

import gov.cdc.nbs.questionbank.question.model.DisplayControlOptions;
import gov.cdc.nbs.questionbank.question.request.QuestionValidationRequest;
import gov.cdc.nbs.questionbank.question.response.QuestionValidationResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/questions")
@PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
public class QuestionControllerHelper {

  private final QuestionFinder finder;
  private final QuestionManagementUtil managementUtil;

  public QuestionControllerHelper(
      final QuestionFinder finder, final QuestionManagementUtil managementUtil) {
    this.finder = finder;
    this.managementUtil = managementUtil;
  }

  @GetMapping("/displayControlOptions")
  public DisplayControlOptions getDisplayControlOptions() {
    return managementUtil.getDisplayControlOptions();
  }

  @PostMapping("/validate")
  public QuestionValidationResponse validate(@RequestBody QuestionValidationRequest request) {
    return finder.checkUnique(request);
  }
}
