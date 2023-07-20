package gov.cdc.nbs.questionbank.template.read;




import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;

import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import gov.cdc.nbs.questionbank.template.TemplateController;
import gov.cdc.nbs.questionbank.template.request.TemplateSearchRequest;
import gov.cdc.nbs.questionbank.template.response.Template;
import gov.cdc.nbs.questionbank.template.util.TemplateHolder;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class ReadTemplateSteps {
	
	
	@Autowired
	private TemplateController templateController;
	
	@Autowired
	private ExceptionHolder exceptionHolder;
	
	@Autowired
	private TemplateHolder templateHolder;
	
	private TemplateSearchRequest search;
	
	@Given("I make a request to retrieve all templates")
	public void i_make_a_request_to_retrieve_all_templates() {
		try {
			Page<Template> results = templateController.findAllTemplates(PageRequest.ofSize(20));
			templateHolder.setTemplateResults(results);
		} catch (AccessDeniedException e) {
			exceptionHolder.setException(e);
		} catch (AuthenticationCredentialsNotFoundException e) {
			exceptionHolder.setException(e);
		}

	}
	
	@Given("I make a request for a template that does not exist")
	public void i_make_a_request_for_a_template_that_does_not_exist() {
		try {
			search = new TemplateSearchRequest(1l,null,null,null,null,null);
			Page<Template> results = templateController.searchTemplate(search, PageRequest.ofSize(20));
			templateHolder.setTemplateResults(results);
		} catch (AccessDeniedException e) {
			exceptionHolder.setException(e);
		} catch (AuthenticationCredentialsNotFoundException e) {
			exceptionHolder.setException(e);
		}

	}
	
	@Given("I search for a template that exists")
	public void i_search_for_a_template_that_exists() {
		try {
			search = new TemplateSearchRequest(1l,"templateNm",List.of("Draft"),null,null,null);
			Page<Template> results = templateController.searchTemplate(search, PageRequest.ofSize(20));
			templateHolder.setTemplateResults(results);
		} catch (AccessDeniedException e) {
			exceptionHolder.setException(e);
		} catch (AuthenticationCredentialsNotFoundException e) {
			exceptionHolder.setException(e);
		}

	}
	
	@Then("Templates should be returned")
	public void templates_should_be_returned() {
		Page<Template> results = templateHolder.getTemplateResults();
		assertNotNull(results);
		assertTrue(results.getSize() > 0);
		
	}
	
	@Then("A template should not be returned")
	public void a_template_should_not_be_returned() {
		Page<Template> results = templateHolder.getTemplateResults();
		assertNotNull(results);
		assertEquals(0,results.getSize());
	}
	
	@Then("A template should be returned")
	public void a_template_should_be_returned() {
		Page<Template> results = templateHolder.getTemplateResults();
		assertNotNull(results);
		assertTrue(results.getSize() > 0);
		
	}



}

