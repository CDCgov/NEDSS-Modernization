package gov.cdc.nbs.questionbank.condition;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import gov.cdc.nbs.questionbank.support.condition.ConditionMother;
import org.springframework.beans.factory.annotation.Autowired;
import gov.cdc.nbs.questionbank.condition.controller.ConditionController;
import gov.cdc.nbs.questionbank.entity.condition.ConditionCode;
import gov.cdc.nbs.questionbank.condition.request.CreateConditionRequest;
import gov.cdc.nbs.questionbank.condition.response.CreateConditionResponse;
import gov.cdc.nbs.questionbank.condition.util.ConditionHolder;
import gov.cdc.nbs.questionbank.support.UserMother;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.access.AccessDeniedException;


public class CreateConditionSteps {
    @Autowired
    private UserMother userMother;

    @Autowired
    private ConditionController conditionController;

    @Autowired
    private ExceptionHolder exceptionHolder;

    @Autowired
    private ConditionMother conditionMother;

    @Autowired
    private ConditionHolder conditionHolder;

    private CreateConditionRequest request;
    private CreateConditionResponse response;

    private long result;


    @Given("ConditionCd already exists")
    public void a_conditioncd_already_exists(){
        try {
            userMother.adminUser();
            ConditionCode val = conditionMother.createCondition();
            conditionHolder.setConditionCode(val);
            result = 0L;
            request = new CreateConditionRequest();
            request.setCodeSystemDescTxt("Test1234");
            request.setId("T1234567");
            request.setConditionShortNm("Test1234");
            request.setProgAreaCd("Test");
            request.setFamilyCd("Test");
            request.setCoinfectionGrpCd("Test");
            request.setNndInd('Y');
            request.setReportableMorbidityInd('Y');
            request.setReportableSummaryInd('Y');
            request.setContactTracingEnableInd('Y');
        } catch(AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch(AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        }
    }

    @Given("A condition name already exists")
    public void a_conditionnm_already_exists(){
        try {
            userMother.adminUser();
            ConditionCode val = conditionMother.createCondition();
            conditionHolder.setConditionCode(val);
            result = 0L;
            request = new CreateConditionRequest();
            request.setCodeSystemDescTxt("Test1234");
            request.setId("T1234567");
            request.setConditionShortNm("Sample Text");
            request.setProgAreaCd("Test");
            request.setFamilyCd("Test");
            request.setCoinfectionGrpCd("Test");
            request.setNndInd('Y');
            request.setReportableMorbidityInd('Y');
            request.setReportableSummaryInd('Y');
            request.setContactTracingEnableInd('Y');
        } catch (AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch (AuthenticationCredentialsNotFoundException e){
            exceptionHolder.setException(e);
        }
    }

    @Given("I am an admin user and a condition does not exist")
    public void i_am_an_admin_user_and_a_condition_does_not_exist() {
        userMother.adminUser();
        conditionHolder.setConditionCode(null);
        request = new CreateConditionRequest();
        request.setCodeSystemDescTxt("Test1234");
        request.setId("A1234567");
        request.setConditionShortNm("Sample");
        request.setProgAreaCd("STD");
        request.setFamilyCd("Test");
        request.setCoinfectionGrpCd("Test");
        request.setNndInd('Y');
        request.setReportableMorbidityInd('Y');
        request.setReportableSummaryInd('Y');
        request.setContactTracingEnableInd('Y');
        result = 0L;
    }

    @When("I send a create condition request")
    public void create_condition() {
        ResponseEntity<CreateConditionResponse> val = conditionController.createCondition(request);
        response = val.getBody();
        conditionHolder.setCreateConditionResponse(response);
    }

    @Then("the condition is created")
    public void the_condition_is_created(){
        assertNull(conditionHolder.getConditionCode());
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatus());
    }

    @Then("A condition creation exception is thrown")
    public void a_condition_creation_exception_is_thrown(){
        assertNotNull(response);
        if(result == 0L || result == 1L) {
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
        }
    }
}
