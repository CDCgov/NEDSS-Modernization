package gov.cdc.nbs.encryption;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import gov.cdc.nbs.testing.interaction.http.Authenticated;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@Component
class EncryptionRequester {

  private final Authenticated authenticated;
  private final MockMvc mvc;

  EncryptionRequester(final Authenticated authenticated, final MockMvc mvc) {
    this.authenticated = authenticated;
    this.mvc = mvc;
  }

  ResultActions encrypt(final String payload) {
    try {

      return mvc.perform(
          this.authenticated.withUser(
              post("/encryption/encrypt")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(payload)));
    } catch (Exception exception) {
      throw new IllegalStateException("Unable to execute Encryption Request", exception);
    }
  }
}
