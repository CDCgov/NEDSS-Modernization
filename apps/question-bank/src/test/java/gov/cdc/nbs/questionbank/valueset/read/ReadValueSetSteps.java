package gov.cdc.nbs.questionbank.valueset.read;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;


import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import gov.cdc.nbs.questionbank.valueset.ValueSetController;
import gov.cdc.nbs.questionbank.valueset.request.ValueSetSearchRequest;
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

	private ValueSetSearchRequest search;

	@Given("I search for a value set that exists")
	public void i_search_for_a_value_set_that_exists() {
		try {
			search = new ValueSetSearchRequest();
			search.setCodeSetName("codeSetNm");
			Page<ValueSet> results = valueSetController.searchValueSet(search, PageRequest.ofSize(20));
			valueSetSearchHolder.setValueSetResults(results);
		} catch (AccessDeniedException e) {
			exceptionHolder.setException(e);
		} catch (AuthenticationCredentialsNotFoundException e) {
			exceptionHolder.setException(e);
		}

	}

	@Given("I make a request for a value set that does not exist")
	public void i_make_a_request_for_a_value_set_that_does_not_exist() {
		try {
			search = new ValueSetSearchRequest();
			Page<ValueSet> results = valueSetController.searchValueSet(search, PageRequest.ofSize(20));
			valueSetSearchHolder.setValueSetResults(results);
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

	@Then("Value sets should be returned")
	public void value_sets_should_be_returned() {
		Page<ValueSet> results = valueSetSearchHolder.getValueSetResults();
		assertNotNull(results);
		assertTrue(results.getSize() > 0);
	}

	@Then("A value sets should not be returned")
	public void a_value_sets_should_not_be_returned() {
		Page<ValueSet> results = valueSetSearchHolder.getValueSetResults();
		assertNotNull(results);
		assertEquals(0,results.getSize());

	}

	@Then("A value set should be returned")
	public void a_value_set_should_be_returned() {
		Page<ValueSet> results = valueSetSearchHolder.getValueSetResults();
		assertNotNull(results);
		assertTrue(results.getSize() > 0);
	}

}
