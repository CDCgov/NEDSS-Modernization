package gov.cdc.nbs.questionbank.valueset.read;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import gov.cdc.nbs.questionbank.valueset.ValueSetController;
import gov.cdc.nbs.questionbank.valueset.request.ValueSetCreateRequest;
import gov.cdc.nbs.questionbank.valueset.response.ValueSet;
import gov.cdc.nbs.questionbank.valueset.util.ValueSetHolder;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class ReadValueSetSteps {

  @Autowired
  private ValueSetController valueSetController;

  @Autowired
  private ExceptionHolder exceptionHolder;

  @Autowired
  private ValueSetHolder valueSetSearchHolder;


  private ValueSetCreateRequest request;

  @Given("A valueSet exists")
  public void a_valueSet_exists() {
    try {
      request = new ValueSetCreateRequest("type", "testValueSetCode", "testValueSetNm",
          "testCodeSetDescTxt");
      valueSetController.createValueSet(request);
    } catch (AccessDeniedException e) {
      exceptionHolder.setException(e);
    } catch (AuthenticationCredentialsNotFoundException e) {
      exceptionHolder.setException(e);
    }
  }

  @Given("I make a request to retrieve all value sets")
  public void i_make_a_request_to_retrieve_all_value_sets() {
    try {
      Page<ValueSet> results = valueSetController.findAllValueSets(PageRequest.ofSize(20));
      valueSetSearchHolder.setValueSetResults(results);
    } catch (AccessDeniedException e) {
      exceptionHolder.setException(e);
    } catch (AuthenticationCredentialsNotFoundException e) {
      exceptionHolder.setException(e);
    }
  }


  @Then("All Value sets should be returned")
  public void all_value_sets_should_be_returned() {
    Page<ValueSet> results = valueSetSearchHolder.getValueSetResults();
    assertNotNull(results);
    assertFalse(results.isEmpty());
  }


}
