package gov.cdc.nbs.questionbank.pagerules;


import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.model.ViewRuleResponse;
import gov.cdc.nbs.questionbank.pagerules.exceptions.RuleException;
import gov.cdc.nbs.questionbank.pagerules.response.CreateRuleResponse;
import gov.cdc.nbs.questionbank.model.CreateRuleRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;



@Slf4j
@RestController
@RequestMapping("/api/v1/")
public class PageRuleController {

    private final PageRuleService pageRuleService;

    private final UserDetailsProvider userDetailsProvider;

    public PageRuleController(PageRuleService pageRuleService, UserDetailsProvider userDetailsProvider) {
        this.userDetailsProvider = userDetailsProvider;
        this.pageRuleService = pageRuleService;
    }

    @PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
    @PostMapping("rule")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public CreateRuleResponse createBusinessRule(@RequestBody CreateRuleRequest request) {
        log.info("Request for Business Rule Creation");
        Long userId = userDetailsProvider.getCurrentUserDetails().getId();
        try {
            CreateRuleResponse ruleResponse = pageRuleService.createPageRule(userId, request);
            log.debug("Successfully added business rule with Id: {}", ruleResponse.ruleId());
            return ruleResponse;
        } catch (RuleException e) {
            return new CreateRuleResponse(null, "Error in Creating a Rule");
        }
    }

    @PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
    @DeleteMapping("rule/{ruleId}")
    @ResponseBody
    public CreateRuleResponse deletePageRule(@PathVariable Long ruleId) {
        return pageRuleService.deletePageRule(ruleId);
    }

    @PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
    @PutMapping("rule/{ruleId}")
    @ResponseBody
    public CreateRuleResponse updatePageRule(@PathVariable Long ruleId,
            @RequestBody CreateRuleRequest request) throws RuleException {
        Long userId = userDetailsProvider.getCurrentUserDetails().getId();
        return pageRuleService.updatePageRule(ruleId, request, userId);
    }

    @PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
    @GetMapping("rule/{ruleId}")
    @ResponseBody
    public ViewRuleResponse viewRuleResponse(@PathVariable Long ruleId) {
        return pageRuleService.getRuleResponse(ruleId);
    }
    @PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
    @GetMapping("rule")
    @ResponseBody
    public Page<ViewRuleResponse> getAllPageRule(@PageableDefault(size = 25) Pageable pageable){
        return pageRuleService.getAllPageRule(pageable);
    }
}
