package gov.cdc.nbs.questionbank.pagerules;


import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.page.content.rule.PageRuleDeleter;
import gov.cdc.nbs.questionbank.pagerules.exceptions.RuleException;
import gov.cdc.nbs.questionbank.pagerules.response.CreateRuleResponse;
import gov.cdc.nbs.questionbank.model.CreateRuleRequest;
import springfox.documentation.annotations.ApiIgnore;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;



@RestController
@PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
@RequestMapping("/api/v1/pages/{id}/rules")
public class PageRuleController {

  private final PageRuleService pageRuleService;

  private final UserDetailsProvider userDetailsProvider;



  private final PageRuleDeleter pageRuleDeleter;

  private final PageRuleCreator pageRuleCreator;

  private final PageRuleReader pageRuleReader;

  public PageRuleController(PageRuleService pageRuleService, UserDetailsProvider userDetailsProvider,
      PageRuleDeleter pageRuleDeleter, PageRuleCreator pageRuleCreator,
      PageRuleReader pageRuleReader) {
    this.userDetailsProvider = userDetailsProvider;
    this.pageRuleService = pageRuleService;
    this.pageRuleDeleter = pageRuleDeleter;
    this.pageRuleCreator = pageRuleCreator;
    this.pageRuleReader = pageRuleReader;

  }

  @PostMapping()
  @ResponseStatus(HttpStatus.CREATED)
  public CreateRuleResponse createBusinessRule(
      @RequestBody CreateRuleRequest request,
      @PathVariable("id") Long page,
      @ApiIgnore @AuthenticationPrincipal final NbsUserDetails details) {
    try {
      return pageRuleCreator.createPageRule(details.getId(), request, page);
    } catch (RuleException e) {
      return new CreateRuleResponse(null, "Error in Creating a Rules");
    }
  }

  @DeleteMapping("{ruleId}")
  public void deletePageRule(
      @PathVariable("id") Long page,
      @PathVariable Long ruleId,
      @ApiIgnore @AuthenticationPrincipal final NbsUserDetails details) {
    pageRuleDeleter.delete(page, ruleId, details.getId());
  }

  @PutMapping("/{ruleId}")
  public CreateRuleResponse updatePageRule(@PathVariable Long ruleId,
      @RequestBody CreateRuleRequest request, @PathVariable Long page) throws RuleException {
    Long userId = userDetailsProvider.getCurrentUserDetails().getId();
    return pageRuleService.updatePageRule(ruleId, request, userId, page);
  }
  @GetMapping("/{ruleId}")
  public Rule viewRuleResponse(@PathVariable Long ruleId) {
    return pageRuleReader.findByRuleId(ruleId);
  }

  @GetMapping
  public Page<Rule> getAllPageRule(@PageableDefault(size = 25) Pageable pageable,
      @PathVariable Long id) {
    return pageRuleReader.findByPageId(id, pageable);
  }

  @PostMapping("/search")
  public Page<Rule> findPageRule(@RequestBody SearchPageRuleRequest request,
      @PageableDefault(size = 25) Pageable pageable) {
    return pageRuleReader.searchPageRule(request, pageable);
  }

}
