package gov.cdc.nbs.patient.profile.investigation;

import gov.cdc.nbs.authentication.SessionCookie;
import gov.cdc.nbs.event.search.investigation.TestInvestigations;
import gov.cdc.nbs.patient.TestPatients;
import gov.cdc.nbs.support.Active;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

public class PatientProfileCompareInvestigationSteps {

        @Value("${nbs.wildfly.url:http://wildfly:7001}")
        String classicUrl;

        @Autowired
        TestPatients patients;

        @Autowired
        TestInvestigations investigations;

        @Autowired
        MockMvc mvc;

        @Autowired
        Active<SessionCookie> activeSession;

        @Autowired
        Active<MockHttpServletResponse> activeResponse;

        @Autowired
        Active<UserDetails> activeUserDetails;

        @Autowired
        @Qualifier("classic")
        MockRestServiceServer server;

        @Before
        public void clearServer() {
                server.reset();
        }

        @When("the investigations are compared from a Patient Profile")
        public void an_investigation_is_added_from_a_patient_profile() throws Exception {
                long patient = patients.one();

                List<Long> comparing = investigations.all()
                                .limit(2)
                                .toList();

                server.expect(
                                requestTo(classicUrl + "/nbs/HomePage.do?method=patientSearchSubmit"))
                                .andExpect(method(HttpMethod.GET))
                                .andRespond(withSuccess());

                server.expect(requestTo(
                                classicUrl + "/nbs/PatientSearchResults1.do?ContextAction=ViewFile&uid=" + patient))
                                .andExpect(method(HttpMethod.GET))
                                .andRespond(withSuccess());

                String request = String.format(
                                "/nbs/api/profile/%d/investigation/%d/compare/%d",
                                patient,
                                comparing.get(0),
                                comparing.get(1));

                activeResponse.active(
                                mvc.perform(
                                                MockMvcRequestBuilders.get(request)
                                                                .with(user(activeUserDetails.active()))
                                                                .cookie(activeSession.active().asCookie()))
                                                .andReturn()
                                                .getResponse());
        }

        @Then("the classic profile is prepared to compare investigations")
        public void the_classic_profile_is_prepared_to_compare_investigations() {
                server.verify();
        }

        @Then("I am redirected to Classic NBS to compare Investigation")
        public void i_am_redirected_to_classic_nbs_to_compare_investigations() {
                long patient = patients.one();

                String expected = investigations.indexed()
                                .limit(2) // Classic can only compare two investigations
                                .map(investigation -> "publicHealthCaseUID" + investigation.index() + "="
                                                + investigation.item())
                                .collect(
                                                Collectors.joining("&",
                                                                "/nbs/ViewFile1.do?ContextAction=CompareInvestigations&",
                                                                ""));

                MockHttpServletResponse response = activeResponse.active();

                assertThat(response.getRedirectedUrl()).contains(expected);

                assertThat(response.getCookies())
                                .satisfiesOnlyOnce(cookie -> {
                                        assertThat(cookie.getName()).isEqualTo("Return-Patient");
                                        assertThat(cookie.getValue()).isEqualTo(String.valueOf(patient));
                                });
        }

        @Then("I am not allowed to compare Classic NBS Investigations")
        public void i_am_not_allowed_to_compare_classic_nbs_investigations() {
                MockHttpServletResponse response = activeResponse.active();

                assertThat(response.getStatus()).isEqualTo(HttpStatus.FORBIDDEN.value());
        }
}
