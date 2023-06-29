package gov.cdc.nbs.questionbank.pagerules;


import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.pagerules.response.CreateRuleResponse;
import gov.cdc.nbs.questionbank.model.CreateRuleRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.management.BadAttributeValueExpException;


@Slf4j
@RestController
@RequestMapping("/api/v1/")
public class PageRuleController {

   private final PageRuleService pageRuleService;

   private final UserDetailsProvider userDetailsProvider;

   public PageRuleController(PageRuleService pageRuleService, UserDetailsProvider userDetailsProvider){
       this.userDetailsProvider = userDetailsProvider;
       this.pageRuleService= pageRuleService;
   }

    @PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
    @PostMapping("rule")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public CreateRuleResponse createBusinessRule(@RequestBody CreateRuleRequest.ruleRequest request){
        log.info("Request for Business Rule Creation");
        Long userId = userDetailsProvider.getCurrentUserDetails().getId();
        try{
            CreateRuleResponse ruleResponse=  pageRuleService.createPageRule(userId,request);
            log.debug("Successfully added business rule with Id: {}",ruleResponse.ruleId());
            return ruleResponse;
        }catch(BadAttributeValueExpException e){
            return new CreateRuleResponse(null, "Error in Creating a Rule");
        }
    }
}
