package gov.cdc.nbs;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class RedirectStepDefinitions {
    @Test
    public void testForDebugging() throws Exception {
    }

    @Autowired
    private MockMvc mvc;

    private MockHttpServletResponse response;

    @Given("I send a request to the NBS simple search")
    public void i_send_a_request_to_the_nbs_simple_search() throws Exception {
        response = mvc.perform(MockMvcRequestBuilders.post("/nbs/HomePage.do")).andReturn().getResponse();
    }

    @Then("I am redirected to the simple search react page")
    public void i_am_redirected_to_the_simple_search_reach_page() {
        assertEquals(HttpStatus.FOUND.value(), response.getStatus());
        var redirectUrl = response.getRedirectedUrl();
        assertNotNull(redirectUrl);
        assertTrue(redirectUrl.equals("/"));
    }

    @Given("I send a search request to the NBS simple search")
    public void I_send_a_search_request_to_the_nbs_simple_search() throws Exception {
        response = mvc.perform(MockMvcRequestBuilders
                .post("/nbs/HomePage.do")
                .param("patientSearchVO.lastName", "Doe")
                .param("patientSearchVO.firstName", "John")
                .param("patientSearchVO.birthTime", "01/01/2000")
                .param("patientSearchVO.currentSex", "M")
                .param("patientSearchVO.actType", "1000")
                .param("patientSearchVO.actId", "9876")
                .param("patientSearchVO.localID", "1234")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.ALL)).andReturn().getResponse();
    }

    @Then("My search params are passed to the simple search react page")
    public void My_search_params_are_passed_to_the_simple_search_react_page() {
        var redirectUrl = response.getRedirectedUrl();
        assertNotNull(redirectUrl);
        assertTrue(redirectUrl.contains("/?"));
        assertTrue(redirectUrl.contains("lastName=Doe"));
        assertTrue(redirectUrl.contains("firstName=John"));
        var encodedDate = URLEncoder.encode("01/01/2000", StandardCharsets.UTF_8);
        assertTrue(redirectUrl.contains("DateOfBirth=" + encodedDate));
        assertTrue(redirectUrl.contains("sex=M"));
        assertTrue(redirectUrl.contains("eventType=1000"));
        assertTrue(redirectUrl.contains("eventId=9876"));
        assertTrue(redirectUrl.contains("id=1234"));
    }

    @Given("I navigate to the NBS advanced search page")
    public void i_navigate_to_the_NBS_advanced_search_page() throws Exception {
        response = mvc.perform(MockMvcRequestBuilders.get("/nbs/MyTaskList1.do")).andReturn().getResponse();
    }

    @Then("I am redirected to the advanced search react page")
    public void i_am_redirected_to_the_advanced_search_react_page() {
        assertEquals(HttpStatus.FOUND.value(), response.getStatus());
        var redirectUrl = response.getRedirectedUrl();
        assertNotNull(redirectUrl);
        assertTrue(redirectUrl.contains("/search"));
    }

}
