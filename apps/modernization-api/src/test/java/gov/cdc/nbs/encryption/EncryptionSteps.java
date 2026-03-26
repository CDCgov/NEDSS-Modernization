package gov.cdc.nbs.encryption;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

public class EncryptionSteps {

  private final EncryptionRequester encryptionRequester;
  private final DecryptionRequester decryptionRequester;
  private final Active<ResultActions> response;
  private final ObjectMapper mapper;
  private final Active<String> activePayload;
  private final Active<String> activeEncryptedPayload;

  EncryptionSteps(
      final EncryptionRequester encryptionRequester,
      final DecryptionRequester decryptionRequester,
      final Active<ResultActions> response,
      final ObjectMapper mapper) {
    this.encryptionRequester = encryptionRequester;
    this.decryptionRequester = decryptionRequester;
    this.response = response;
    this.mapper = mapper;
    this.activePayload = new Active<>();
    this.activeEncryptedPayload = new Active<>();
  }

  @Before("@parameter_encryption")
  public void clean() {
    this.activePayload.reset();
    this.activeEncryptedPayload.reset();
  }

  @Given("I want to encrypt the payload {string}")
  @Given("I want to encrypt the payload")
  public void i_want_to_encrypt_the_payload(final String payload) {
    this.activePayload.active(payload);
  }

  @When("I encrypt the payload")
  public void i_encrypt_the_payload() {
    this.activePayload
        .maybeActive()
        .map(this.encryptionRequester::encrypt)
        .ifPresent(this.response::active);
  }

  @Then("I receive an encrypted payload")
  public void I_receive_an_encrypted_payload() throws Exception {
    String encrypted =
        this.response
            .active()
            .andExpect(jsonPath("$.value").isString())
            .andReturn()
            .getResponse()
            .getContentAsString();

    EncryptionResponse encryptionResponse = mapper.readValue(encrypted, EncryptionResponse.class);
    this.activeEncryptedPayload.active(encryptionResponse.value());
  }

  @Given("I want to decrypt the payload {string}")
  public void i_decrypt_the_payload(final String payload) {
    this.response.active(this.decryptionRequester.request(payload));
  }

  @Given("I want to decrypt the encrypted payload")
  public void I_decrypt_the_encrypted_payload() {
    this.activeEncryptedPayload.maybeActive().ifPresent(activePayload::active);
  }

  @When("I decrypt the payload")
  public void i_decrypt_the_payload() {
    this.activePayload
        .maybeActive()
        .map(this.decryptionRequester::request)
        .ifPresent(this.response::active);
  }

  @Then("I receive the decrypted payload")
  public void I_receive_the_decrypted_payload(final String decrypted) throws Exception {
    this.response.active().andDo(print()).andExpect(content().json(decrypted));
  }
}
