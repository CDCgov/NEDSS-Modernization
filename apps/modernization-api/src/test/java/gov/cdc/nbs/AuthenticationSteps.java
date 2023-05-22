package gov.cdc.nbs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.cdc.nbs.authentication.entity.AuthUserRepository;
import gov.cdc.nbs.model.LoginRequest;
import gov.cdc.nbs.support.UserMother;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AuthenticationSteps {

    @Autowired
    private AuthUserRepository authUserRepository;

    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    @Before
    public void clearContext() {
        TestContext.clear();
    }

    @Given("A user exists")
    public void a_user_exists() {
        var user = UserMother.clerical();
        if (!authUserRepository.findByUserId(user.getUserId()).isPresent()) {
            authUserRepository.save(user);
        }
        TestContext.user = user;
    }

    @Given("I have authenticated as a user")
    public void i_have_authenticated_as_a_user() throws Exception {
        var user = TestContext.user;
        var response = mvc.perform(MockMvcRequestBuilders
                .post("/login")
                .content(mapper.writeValueAsBytes(new LoginRequest(user.getUserId(), "")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        var tokenCookie = response.getResponse().getCookie("nbs_token");
        assertNotNull(tokenCookie);
        TestContext.token = tokenCookie.getValue();
    }

    @When("I try to access the patient search API")
    public void i_try_to_access_the_patient_search_API() throws Exception {
        var token = TestContext.token;
        TestContext.response = mvc.perform(MockMvcRequestBuilders
                .post("/graphql")
                .header("Authorization", "Bearer " + token)
                .content(findAllPatientsQuery())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andReturn();
    }

    @Given("I have not authenticated as a user")
    public void i_have_not_authenticated_as_a_user() {
        TestContext.token = null;
    }

    @Then("I get a valid response")
    public void i_get_a_valid_response() {
        assertEquals(HttpStatus.OK.value(), TestContext.response.getResponse().getStatus());
    }

    @Then("I get a 401 unauthorized response")
    public void i_get_a_401_unauthorized_response() {
        assertEquals(HttpStatus.UNAUTHORIZED.value(), TestContext.response.getResponse().getStatus());
    }

    private String findAllPatientsQuery() {
        return "{\"query\":\"query namedQuery {findAllPatients {id firstNm lastNm}}\",\"operationName\":\"namedQuery\"}";
    }

}
