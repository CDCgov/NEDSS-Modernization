package gov.cdc.nbs.questionbank.valueset.create;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;

import gov.cdc.nbs.questionbank.entity.Codeset;
import gov.cdc.nbs.questionbank.question.util.ExceptionHolder;
import gov.cdc.nbs.questionbank.support.valueset.ValueSetMother;
import gov.cdc.nbs.questionbank.valueset.ValueSetController;
import gov.cdc.nbs.questionbank.valueset.request.ValueSetRequest;
import gov.cdc.nbs.questionbank.valueset.response.CreateValueSetResponse;
import gov.cdc.nbs.questionbank.valueset.util.ValueSetConstants;
import gov.cdc.nbs.questionbank.valueset.util.ValueSetHolder;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CreateValueSetSteps {

	@Autowired
	private ValueSetController valueSetController;

	@Autowired
	private ExceptionHolder exceptionHolder;

	@Autowired
	private ValueSetMother valueSetMother;

	@Autowired
	private ValueSetHolder valueSetHolder;

	private ValueSetRequest request;

	private CreateValueSetResponse response;

	private long codeSetGroupResult;

	@Given("Codeset Group Metadata already exists")
	public void codeset_group_metadata_already_exists() {
		try {
			Codeset result = valueSetMother.createCodeSetGroupForValueSet();
			valueSetHolder.setValueSet(result);
			codeSetGroupResult = 1l;
		} catch (AccessDeniedException e) {
			exceptionHolder.setException(e);
		} catch (AuthenticationCredentialsNotFoundException e) {
			exceptionHolder.setException(e);
		}

	}

	@Given("A codeSetNm already already exists")
	public void a_codesetnm_already_already_exists() {
		try {
			Codeset result = valueSetMother.createValueSet();
			valueSetHolder.setValueSet(result);
			codeSetGroupResult = 0l;
		} catch (AccessDeniedException e) {
			exceptionHolder.setException(e);
		} catch (AuthenticationCredentialsNotFoundException e) {
			exceptionHolder.setException(e);
		}
	}

	@Given("I am admin and a value set does not exists")
	public void i_am_admin_and_a_value_set_does_not_exists() {
		valueSetHolder.setValueSet(null);
		codeSetGroupResult = 0l;
	}

	@When("I create a value set")
	public void i_create_a_value_set() {

		try {
			if (valueSetHolder.getValueSet() != null) {
				String codeSetName = valueSetHolder.getValueSet().getValueSetNm();
				String codeSetDescTxt = valueSetHolder.getValueSet().getCodeSetDescTxt();
				request.setValueSetNm(codeSetName);
				request.setCodeSetDescTxt(codeSetDescTxt);
				request.setValueSetCode(codeSetName);
			} else {
				request.setValueSetNm("testCreateCodeSet");
				request.setCodeSetDescTxt("testCreateCodeSet");
				request.setValueSetCode("testCreateCodeSet");
			}

			ResponseEntity<CreateValueSetResponse> codeSetResult = valueSetController.createValueSet(request);
			response = codeSetResult.getBody();

		} catch (AccessDeniedException e) {
			exceptionHolder.setException(e);
		} catch (AuthenticationCredentialsNotFoundException e) {
			exceptionHolder.setException(e);
		}

	}

	@Then("Valueset should not create")
	public void valueset_should_not_create() {
		assertNotNull(response);
		if (codeSetGroupResult == 1l) {
			assertEquals(null, response.getBody());
			assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
			assertEquals(ValueSetConstants.CODE_SET_GRP_TEXT_NAME_EXISTS, response.getMessage());
		}
		if (codeSetGroupResult == 0l && response.getBody() == null) {
			assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
			assertEquals(ValueSetConstants.VALUE_SET_NAME_EXISTS, response.getMessage());
		}

	}

	@Then("Valueset should be created")
	public void valueset_should_be_created() {
		assertNull(valueSetHolder.getValueSet());
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.CREATED, response.getStatus());
		assertEquals(ValueSetConstants.SUCCESS_MESSAGE, response.getMessage());
	}
}
