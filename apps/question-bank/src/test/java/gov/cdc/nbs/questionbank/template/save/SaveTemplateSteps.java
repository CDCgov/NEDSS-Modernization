package gov.cdc.nbs.questionbank.template.save;

import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import gov.cdc.nbs.questionbank.support.UserMother;
import gov.cdc.nbs.questionbank.template.TemplateController;
import gov.cdc.nbs.questionbank.template.request.SaveTemplateRequest;
import gov.cdc.nbs.questionbank.template.response.Template;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.Assert.*;

public class SaveTemplateSteps {

    @Autowired
    private TemplateController templateController;

    @Autowired
    private ExceptionHolder exceptionHolder;

    private Template response;

    @Autowired
    private UserMother userMother;

    @Given("I make a request to save template with watemplateuid does not exist")
    public void i_make_a_request_to_save_template_does_not_exist() {
        try {
            SaveTemplateRequest request = new SaveTemplateRequest(1L, "TestTemplate", "Test Template API");
            response = templateController.saveTemplate(request);
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        }catch(Exception e){
            exceptionHolder.setException(e);
        }
    }

    @Given("I make a request to save template")
    public void i_make_a_request_to_save_template() {
        try {
            SaveTemplateRequest request = new SaveTemplateRequest(1000317L, "TestTemplate", "Test Template API");
            response = templateController.saveTemplate(request);
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        }catch(Exception e){
            exceptionHolder.setException(e);
        }
    }

    @Then("Template should be saved")
    public void template_should_be_saved() {
        assertNull(response);
    }

    @Given("I am an admin user in template")
    public void i_am_admin_user() {
        userMother.adminUser();
    }
    @Given("I am not logged in template")
    public void i_am_not_logged_in() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    @Then("a no credentials found exception is thrown in template")
    public void a_not_authorized_exception_is_thrown() {
        assertNotNull(exceptionHolder.getException());
        assertTrue(exceptionHolder.getException() instanceof AuthenticationCredentialsNotFoundException);
    }
}
