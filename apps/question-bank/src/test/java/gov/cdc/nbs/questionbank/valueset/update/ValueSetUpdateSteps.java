package gov.cdc.nbs.questionbank.valueset.update;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;

import gov.cdc.nbs.questionbank.entity.Codeset;
import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import gov.cdc.nbs.questionbank.support.valueset.ValueSetMother;
import gov.cdc.nbs.questionbank.valueset.ValueSetController;
import gov.cdc.nbs.questionbank.valueset.request.ValueSetUpdateRequest;
import gov.cdc.nbs.questionbank.valueset.response.UpdatedValueSetResponse;
import gov.cdc.nbs.questionbank.valueset.util.ValueSetConstants;
import gov.cdc.nbs.questionbank.valueset.util.ValueSetHolder;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ValueSetUpdateSteps {

	@Autowired
	private ValueSetController valueSetController;

	@Autowired
	private ExceptionHolder exceptionHolder;

	@Autowired
	private ValueSetMother valueSetMother;

	@Autowired
	private ValueSetHolder valueSetHolder;

	private ValueSetUpdateRequest update;
	private UpdatedValueSetResponse response;

	@Given("I update a value set that does not exist")
	public void i_update_a_value_set_that_does_not_exist() {

		try {
			update = new ValueSetUpdateRequest("codeSetNm","codeSetNm",null);
			ResponseEntity<UpdatedValueSetResponse> result = valueSetController.updateValueSet(update);
			response = result.getBody();
		} catch (AccessDeniedException e) {
			exceptionHolder.setException(e);
		} catch (AuthenticationCredentialsNotFoundException e) {
			exceptionHolder.setException(e);
		}
	}

	@Given("I am an admin user and value set exists")
	public void i_am_an_admin_user_and_value_set_exists() {
		try {
			Codeset result = valueSetMother.createCodeSetGroupForValueSet();
			valueSetHolder.setValueSet(result);
		} catch (AccessDeniedException e) {
			exceptionHolder.setException(e);
		} catch (AuthenticationCredentialsNotFoundException e) {
			exceptionHolder.setException(e);
		}
	}

	@When("I update a value set that exists")
	public void i_update_a_value_set_that_exists() {
		try {
			update = new ValueSetUpdateRequest(valueSetHolder.getValueSet().getId().getCodeSetNm(),"updateCodeSetNm","updateCodeSetDesc");
			ResponseEntity<UpdatedValueSetResponse> responseResult = valueSetController.updateValueSet(update);
			response = responseResult.getBody();
		} catch (AccessDeniedException e) {
			exceptionHolder.setException(e);
		} catch (AuthenticationCredentialsNotFoundException e) {
			exceptionHolder.setException(e);
		}

	}

	@Then("A value set should not be updated")
	public void a_value_set_should_not_be_updated() {
		assertNotNull(response);
		assertNull(response.getBody());
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
		assertEquals(ValueSetConstants.CODE_SET_NOT_FOUND, response.getMessage());
	}

	@Then(" A value set should be updated")
	public void a_value_set_should_be_updated() {
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.OK, response.getStatus());
		assertEquals(ValueSetConstants.UPDATE_SUCCESS_MESSAGE, response.getMessage());
	}

}
