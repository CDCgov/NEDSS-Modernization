package gov.cdc.nbs.questionbank.businessrules;


import gov.cdc.nbs.questionbank.model.CreateRuleRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class BusinessRuleController {

    @Autowired
    private BusinessRuleService businessRuleService;

    @RequestMapping(method = RequestMethod.POST, value = "/createBusinessRule", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String createBusinessRule(@RequestBody CreateRuleRequest createRuleRequest){
        log.info("Request for Business Rule Creation");
        return businessRuleService.createBusinessRule(createRuleRequest);
    }
}
