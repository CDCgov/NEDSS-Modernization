package gov.cdc.nbs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.cdc.nbs.entity.odse.AuthUser;
import gov.cdc.nbs.model.LoginRequest;
import gov.cdc.nbs.model.LoginResponse;
import gov.cdc.nbs.repository.AuthUserRepository;
import gov.cdc.nbs.support.UserMother;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AuthenticationStepDefinitions {

    @Autowired
    private AuthUserRepository authUserRepository;

    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    private AuthUser user;
    private String token;
    private MvcResult result;

    @Test
    public void debug() throws Exception {
    }

    @Given("A user exists")
    public void a_user_exists() {
        user = UserMother.clerical();
        if (!authUserRepository.findByUserId(user.getUserId()).isPresent()) {
            authUserRepository.save(user);
        }
    }

    @Given("I have authenticated as a user")
    public void i_have_authenticated_as_a_user() throws Exception {
        var response = sendLoginRequest(user.getUserId(), null);
        token = response.getToken();
    }

    @When("I try to access the patient search API")
    public void i_try_to_access_the_patient_search_API() throws Exception {
        result = mvc.perform(MockMvcRequestBuilders
                .post("/graphql")
                .header("Authorization", "Bearer " + token)
                .content(findAllPatientsQuery())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andReturn();
    }

    @Given("I have not authenticated as a user")
    public void i_have_not_authenticated_as_a_user() {
        token = null;
    }

    @Then("I get a valid response")
    public void i_get_a_valid_response() {
        assertEquals(200, result.getResponse().getStatus());
    }

    @Then("I get a 403 forbidden response")
    public void i_get_a_403_forbidden_response() {
        assertEquals(403, result.getResponse().getStatus());
    }

    private LoginResponse sendLoginRequest(String username, String password) throws Exception {
        var requestJson = mapper.writeValueAsString(new LoginRequest(username, password));
        var mvcResult = mvc.perform(MockMvcRequestBuilders
                .post("/login")
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        return mapper.readValue(mvcResult.getResponse().getContentAsString(), LoginResponse.class);
    }

    private String findAllPatientsQuery() {
        return "{\"query\":\"query namedQuery {findAllPatients {id firstNm lastNm}}\",\"operationName\":\"namedQuery\"}";
    }

}
