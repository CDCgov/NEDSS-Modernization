package gov.cdc.nbs.controller;

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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import gov.cdc.nbs.Application;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class RedirectControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    void testRedirectSimpleSearch() throws Exception {
        var response = mvc.perform(MockMvcRequestBuilders
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
        assertEquals(HttpStatus.FOUND.value(), response.getStatus());
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

    @Test
    void testRedirectAdvancedSearch() throws Exception {
        var response = mvc.perform(MockMvcRequestBuilders
                .get("/nbs/MyTaskList1.do")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.ALL)).andReturn().getResponse();
        assertEquals(HttpStatus.FOUND.value(), response.getStatus());
        var redirectUrl = response.getRedirectedUrl();
        assertNotNull(redirectUrl);
        assertTrue(redirectUrl.contains("/search"));
    }
}
