package gov.cdc.nbs;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.io.IOException;
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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.graphql.filter.PatientFilter;
import gov.cdc.nbs.message.enums.Gender;
import gov.cdc.nbs.model.EncryptionResponse;
import gov.cdc.nbs.support.EthnicityMother;
import gov.cdc.nbs.support.util.RandomUtil;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.DatabindException;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class EncryptionSteps {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    private PatientFilter filter;
    private String encryptedString = "";

    @Before
    public void clearContext() {
        TestContext.clear();
    }

    @Given("I send a request to the encryption endpoint")
    public void i_send_a_request_to_the_encryption_endpoint() throws JsonProcessingException, Exception {
        filter = generateRandomFilter();
        TestContext.response = mvc.perform(
                        MockMvcRequestBuilders.post("/encryption/encrypt")
                                .header("Authorization", "Bearer " + TestContext.token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(filter))
                                .accept(MediaType.ALL))
                .andReturn();
    }

    @Then("I receive an encrypted string")
    public void I_receive_an_encrypted_string() throws StreamReadException, DatabindException, IOException {
        assertEquals(HttpStatus.OK.value(), TestContext.response.getResponse().getStatus());
        EncryptionResponse er = mapper.readValue(
                TestContext.response.getResponse().getContentAsByteArray(),
                EncryptionResponse.class);
        assertNotNull(er);
        encryptedString = er.getValue();
        assertNotNull(encryptedString);
        assertTrue(encryptedString.length() > 0);
    }

    @When("I send a request to the decryption endpoint")
    public void I_send_a_request_to_the_decryption_endpoint() throws JsonProcessingException, Exception {
        TestContext.response = mvc.perform(
                        MockMvcRequestBuilders.post("/encryption/decrypt")
                                .header("Authorization", "Bearer " + TestContext.token)
                                .content(encryptedString)
                                .accept(MediaType.ALL))
                .andReturn();
    }

    @Then("I receive the original object")
    public void I_receive_the_original_object() throws StreamReadException, IOException {
        assertEquals(HttpStatus.OK.value(), TestContext.response.getResponse().getStatus());
        PatientFilter decryptedFilter = mapper.readValue(
                TestContext.response.getResponse().getContentAsByteArray(),
                PatientFilter.class);
        assertNotNull(decryptedFilter);
        assertEquals(decryptedFilter, filter);
    }

    private PatientFilter generateRandomFilter() {
        var filter = new PatientFilter(RecordStatus.ACTIVE);
        filter.setFirstName(RandomUtil.getRandomString());
        filter.setLastName(RandomUtil.getRandomString());
        filter.setAddress(RandomUtil.getRandomString());
        filter.setCity(RandomUtil.getRandomString());
        filter.setState(RandomUtil.getRandomStateCode());
        filter.setCountry(RandomUtil.getRandomString());
        filter.setDateOfBirth(RandomUtil.getRandomDateInPast());
        filter.setGender(RandomUtil.getRandomFromArray(Gender.values()));
        filter.setEthnicity(RandomUtil.getRandomFromArray(EthnicityMother.ETHNICITY_LIST));
        return filter;
    }
}
