package gov.cdc.nbs.questionbank.question;


import gov.cdc.nbs.questionbank.question.model.DisplayControlOptions;
import gov.cdc.nbs.questionbank.question.request.QuestionValidationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/questions")
@PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
@RequiredArgsConstructor
public class QuestionControllerHelper {

  private final QuestionFinder finder;
  private final QuestionManagementUtil managementUtil;


  @GetMapping("/displayControlOptions")
  public DisplayControlOptions getDisplayControlOptions() {
    return managementUtil.getDisplayControlOptions();
  }

  @PostMapping("/validate")
  public Boolean validate(@RequestBody QuestionValidationRequest request) {
    return finder.checkUnique(request);
  }

}
