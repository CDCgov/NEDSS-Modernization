package gov.cdc.nbs.patient.search;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import org.hamcrest.Matcher;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.JsonPathResultMatchers;

import static gov.cdc.nbs.graphql.GraphQLErrorMatchers.accessDenied;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


public class PatientSearchVerificationSteps {

	private final Active<ResultActions> results;
	private final Active<PatientIdentifier> patient;

	PatientSearchVerificationSteps(
			final Active<ResultActions> results,
			final Active<PatientIdentifier> patient) {
		this.results = results;
		this.patient = patient;
	}

	@Then("the patient search results contain(s) the Patient ID")
	public void the_patient_search_results_contain_patient_id() throws Exception {
		this.results.active()
				.andExpect(
						jsonPath("$.data.findPatientsByFilter.content[*].shortId")
								.value(hasItem((int) this.patient.active().shortId())));
	}

	@Then("I am not able to execute the search")
	public void i_am_not_able_to_execute_the_search() throws Exception {
		this.results.active()
				.andExpect(jsonPath("$.errors[*].message")
						.value(
								hasItem(
										"User does not have permission to search for Inactive Patients")));
	}

	@Then("search result {int} has a(n) {string} of {string}")
	public void search_result_n_has_a_x_of_y(
			final int position,
			final String field,
			final String value
	) throws Exception {

		JsonPathResultMatchers pathMatcher = matchingPath(position, field);

		String finalValue = null;

		if (!value.isEmpty()) {
			finalValue = value;
		}

		this.results.active()
				.andExpect(pathMatcher.value(matchingValue(field, finalValue)));
	}

	@Then("the search results have a patient with the {nth} {string} equal to {string}")
	public void search_results_have_a_patient_with_a(final int nth, final String field, final String value)
			throws Exception {

		JsonPathResultMatchers pathMatcher = matchingPath(field, nth);

		this.results.active()
				.andExpect(pathMatcher.value(matchingValue(field, value)));
	}

	@Then("the search results have a patient with a(n) {string} equal to {string}")
	public void search_results_have_a_patient_with_a(final String field, final String value) throws Exception {

		JsonPathResultMatchers pathMatcher = matchingPath(field);

		this.results.active()
				.andExpect(pathMatcher.value(matchingValue(field, value)));
	}

	@Then("the search results have a patient without a(n) {string} equal to {string}")
	public void search_results_have_a_patient_without_a(final String field, final String value) throws Exception {

		JsonPathResultMatchers pathMatcher = matchingPath(field);

		this.results.active()
				.andExpect(pathMatcher.value(not(matchingValue(field, value))));

	}

	@Then("the search results have a patient without a(n) {string}")
	public void search_results_have_a_patient_without_a(final String field) throws Exception {

		JsonPathResultMatchers pathMatcher = matchingPath(field);

		this.results.active()
				.andExpect(pathMatcher.isEmpty());

	}

	private JsonPathResultMatchers matchingPath(final String field) {
		return matchingPath(null, field, null);
	}

	private JsonPathResultMatchers matchingPath(final int position, final String field) {
		return matchingPath(position, field, null);
	}

	private JsonPathResultMatchers matchingPath(final String field, final int position) {
		return matchingPath(null, field, position);
	}

	private JsonPathResultMatchers matchingPath(final Integer position, final String field, final Integer order) {

		String adjustedPostion = position == null ? "*" : String.valueOf(position - 1);
		String adjustedOrder = order == null ? "*" : String.valueOf(order - 1);

		return switch (field.toLowerCase()) {
			case "status" -> jsonPath("$.data.findPatientsByFilter.content[%s].status", adjustedPostion);
			case "birthday" -> jsonPath("$.data.findPatientsByFilter.content[%s].birthday", adjustedPostion);
			case "address" ->
					jsonPath("$.data.findPatientsByFilter.content[%s].addresses[%s].address", adjustedPostion, adjustedOrder);
			case "address type" ->
					jsonPath("$.data.findPatientsByFilter.content[%s].addresses[%s].type", adjustedPostion, adjustedOrder);
			case "address use" ->
					jsonPath("$.data.findPatientsByFilter.content[%s].addresses[%s].use", adjustedPostion, adjustedOrder);
			case "city" ->
					jsonPath("$.data.findPatientsByFilter.content[%s].addresses[%s].city", adjustedPostion, adjustedOrder);
			case "state" ->
					jsonPath("$.data.findPatientsByFilter.content[%s].addresses[%s].state", adjustedPostion, adjustedOrder);
			case "county" ->
					jsonPath("$.data.findPatientsByFilter.content[%s].addresses[%s].county", adjustedPostion, adjustedOrder);
			case "country" ->
					jsonPath("$.data.findPatientsByFilter.content[%s].addresses[%s].country", adjustedPostion, adjustedOrder);
			case "zip" ->
					jsonPath("$.data.findPatientsByFilter.content[%s].addresses[%s].zipcode", adjustedPostion, adjustedOrder);
			case "gender", "sex" -> jsonPath("$.data.findPatientsByFilter.content[%s].gender", adjustedPostion);
			case "first name" ->
					jsonPath("$.data.findPatientsByFilter.content[%s].names[%s].first", adjustedPostion, adjustedOrder);
			case "last name" ->
					jsonPath("$.data.findPatientsByFilter.content[%s].names[%s].last", adjustedPostion, adjustedOrder);
			case "middle name" ->
					jsonPath("$.data.findPatientsByFilter.content[%s].names[%s].middle", adjustedPostion, adjustedOrder);
			case "suffix" ->
					jsonPath("$.data.findPatientsByFilter.content[%s].names[%s].suffix", adjustedPostion, adjustedOrder);
			case "legal first name" ->
					jsonPath("$.data.findPatientsByFilter.content[%s].legalName.first", adjustedPostion);
			case "legal middle name" ->
					jsonPath("$.data.findPatientsByFilter.content[%s].legalName.middle", adjustedPostion);
			case "legal last name" ->
					jsonPath("$.data.findPatientsByFilter.content[%s].legalName.last", adjustedPostion);
			case "legal name suffix" ->
					jsonPath("$.data.findPatientsByFilter.content[%s].legalName.suffix", adjustedPostion);
			case "phone", "phone number" ->
					jsonPath("$.data.findPatientsByFilter.content[%s].phones[%s]", adjustedPostion, adjustedOrder);
			case "email", "email address" ->
					jsonPath("$.data.findPatientsByFilter.content[%s].emails[%s]", adjustedPostion, adjustedOrder);
			case "identification type" ->
					jsonPath("$.data.findPatientsByFilter.content[%s].identification[%s].type", adjustedPostion, adjustedOrder);
			case "identification value" -> jsonPath(
					"$.data.findPatientsByFilter.content[%s].identification[%s].value",
					adjustedPostion, adjustedOrder);
			case "patientid", "patient id", "short id" -> jsonPath("$.data.findPatientsByFilter.content[%s].shortId",
					adjustedPostion);
			case "local id" -> jsonPath("$.data.findPatientsByFilter.content[%s].localId", adjustedPostion);
			default -> throw new AssertionError("Unexpected property check %s".formatted(field));
		};
	}

	private Matcher<?> matchingValue(final String field, final String value) {
		return switch (field.toLowerCase()) {
			case "birthday", "sex" -> equalTo(value);
			case "patientid", "patient id", "short id" -> equalTo(Integer.parseInt(value));
			case "status" -> hasItem(PatientStatusCriteriaResolver.resolve(value).name());
			default -> hasItem(value);
		};
	}

	@Then("the search results have a patient with a(n) {string} {} name of {string}")
	public void search_result_n_has_a_x_of_y(
			final String type,
			final String field,
			final String value) throws Exception {

		JsonPathResultMatchers pathMatcher = switch (field.toLowerCase()) {
			case "first" -> jsonPath("$.data.findPatientsByFilter.content[*].names[?(@.type=='%s')].first", type);
			case "last" -> jsonPath("$.data.findPatientsByFilter.content[*].names[?(@.type=='%s')].last", type);
			default -> throw new IllegalStateException("Unexpected value: " + field);
		};

		this.results.active()
				.andExpect(pathMatcher.value(matchingValue(field, value)));
	}

	@Then("the search results have a patient with a(n) {} - {} number of {string}")
	public void search_results_have_a_patient_with_the_phone(final String type, final String use, final String number)
			throws Exception {

		this.results.active()
				.andExpect(
						jsonPath("$.data.findPatientsByFilter.content[*].detailedPhones[?(@.type=='%s' && @.use=='%s')].number",
								type, use)
								.value(number)
				);
	}

	@Then("the patient is in the search result(s)")
	public void the_patient_is_in_the_search_results() throws Exception {
		this.results.active()
				.andExpect(
						jsonPath(
								"$.data.findPatientsByFilter.content[?(@.patient=='%s')]",
								String.valueOf(this.patient.active().id()))
								.exists());
	}

	@Then("the patient is not in the search result(s)")
	public void the_patient_is_not_in_the_search_results() throws Exception {
		this.results.active()
				.andExpect(
						jsonPath(
								"$.data.findPatientsByFilter.content[?(@.patient=='%s')]",
								String.valueOf(this.patient.active().id()))
								.doesNotExist());
	}

	@Then("there is only one patient search result")
	public void there_is_only_one_patient_search_result() throws Exception {
		this.results.active()
				.andExpect(jsonPath("$.data.findPatientsByFilter.total").value(1));
	}

	@Then("there are {int} patient search results")
	public void there_is_are_x_patient_search_result(final int total) throws Exception {
		this.results.active()
				.andExpect(jsonPath("$.data.findPatientsByFilter.total").value(total));
	}

	@Then("the Patient Search Results are not accessible")
	public void the_patient_search_results_are_not_accessible() throws Exception {
		this.results.active().andExpect(accessDenied());
	}
}
