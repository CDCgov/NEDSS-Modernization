package gov.cdc.nbs.questionbank.pagerules;


import gov.cdc.nbs.questionbank.pagerules.response.CreateRuleResponse;
import gov.cdc.nbs.questionbank.model.CreateRuleRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@Slf4j
@RestController
@RequestMapping("/api/v1/")
public class PageRuleController {

    @Autowired
    private PageRuleService pageRuleService;

    @PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
    @PostMapping("rule")
    @ResponseBody
    public CreateRuleResponse createBusinessRule(@RequestBody CreateRuleRequest.ruleRequest request){
        log.info("Request for Business Rule Creation");
        BigInteger ruleId=  pageRuleService.createPageRule(request);
        log.debug("Successfully added business rule with Id: {}", ruleId);
        return new CreateRuleResponse(ruleId);
    }
}
