package gov.cdc.nbs.questionbank.businessrules;


import gov.cdc.nbs.questionbank.businessrules.response.CreateRuleResponse;
import gov.cdc.nbs.questionbank.model.CreateRuleRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class PageRuleController {

    @Autowired
    private PageRuleService pageRuleService;

    @PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
    @RequestMapping(method = RequestMethod.POST, value = "/createPageRule", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public CreateRuleResponse createBusinessRule(@RequestBody CreateRuleRequest.ruleRequest request){
        log.info("Request for Business Rule Creation");
        long templateUid=  pageRuleService.createPageRule(request);
        log.debug("Successfully added business rule with Id: {}", templateUid);
        return new CreateRuleResponse(templateUid);
    }
}
