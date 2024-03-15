package gov.cdc.nbs.questionbank.pagerules;


import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.page.content.rule.PageRuleDeleter;
import gov.cdc.nbs.questionbank.pagerules.exceptions.RuleException;
import gov.cdc.nbs.questionbank.pagerules.request.RuleRequest;
import springfox.documentation.annotations.ApiIgnore;
import java.util.List;
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

  private final PageRuleFinder pageRuleFinder;

  public PageRuleController(PageRuleService pageRuleService, UserDetailsProvider userDetailsProvider,
      PageRuleDeleter pageRuleDeleter, PageRuleCreator pageRuleCreator,
      PageRuleFinder pageRuleFinder) {
    this.userDetailsProvider = userDetailsProvider;
    this.pageRuleService = pageRuleService;
    this.pageRuleDeleter = pageRuleDeleter;
    this.pageRuleCreator = pageRuleCreator;
    this.pageRuleFinder = pageRuleFinder;

  }

  @PostMapping()
  @ResponseStatus(HttpStatus.CREATED)
  public Rule createBusinessRule(
      @RequestBody RuleRequest request,
      @PathVariable("id") Long page,
      @ApiIgnore @AuthenticationPrincipal final NbsUserDetails details) {
    return pageRuleCreator.createPageRule(details.getId(), request, page);
  }

  @DeleteMapping("{ruleId}")
  public void deletePageRule(
      @PathVariable("id") Long page,
      @PathVariable Long ruleId,
      @ApiIgnore @AuthenticationPrincipal final NbsUserDetails details) {
    pageRuleDeleter.delete(page, ruleId, details.getId());
  }

  @PutMapping("/{ruleId}")
  public Rule updatePageRule(@PathVariable Long ruleId,
      @RequestBody RuleRequest request, @PathVariable Long id) throws RuleException {
    Long userId = userDetailsProvider.getCurrentUserDetails().getId();
    return pageRuleService.updatePageRule(ruleId, request, userId, id);
  }

  @GetMapping("/{ruleId}")
  public Rule viewRuleResponse(@PathVariable Long ruleId) {
    return pageRuleFinder.findByRuleId(ruleId);
  }

  @PostMapping("/search")
  public Page<Rule> findPageRule(@PathVariable("id") Long pageId,
      @RequestBody SearchPageRuleRequest request,
      @PageableDefault(size = 25, sort = "add_time") Pageable pageable) {
    return pageRuleFinder.searchPageRule(pageId, request, pageable);
  }

  @GetMapping("/getAll")
  public List<Rule> getAllRules(@PathVariable("id") Long pageId) {
    return pageRuleFinder.getAllRules(pageId);
  }
}
