package gov.cdc.nbs.patient.search;

import gov.cdc.nbs.testing.interaction.http.Authenticated;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@Component
public class DecryptionRequester {

  private final Authenticated authenticated;
  private final MockMvc mvc;

  public DecryptionRequester(
      final Authenticated authenticated,
      final MockMvc mvc
  ) {
    this.authenticated = authenticated;
    this.mvc = mvc;
  }

  public ResultActions request(final String encrypted) {
    try {

      return mvc.perform(
          this.authenticated.withUser(
              post("/encryption/decrypt")
                  .content(encrypted)
          )
      ).andDo(print());
    } catch (Exception exception) {
      throw new IllegalStateException("Unable to execute Page Information Request", exception);
    }
  }

}
