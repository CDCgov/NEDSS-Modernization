package gov.cdc.nbs.questionbank.valueset;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import gov.cdc.nbs.testing.interaction.http.Authenticated;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@Component
public class FindValueSetRequester {

  private final MockMvc mvc;
  private final Authenticated authenticated;

  FindValueSetRequester(final Authenticated authenticated, final MockMvc mvc) {
    this.authenticated = authenticated;
    this.mvc = mvc;
  }

  ResultActions send(String valueset) throws Exception {
    return mvc.perform(this.authenticated.withUser(get("/api/v1/valueset/{valueset}", valueset)));
  }
}
