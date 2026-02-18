package gov.cdc.nbs.questionbank.valueset.concept;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import gov.cdc.nbs.testing.interaction.http.Authenticated;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@Component
public class ListConceptsRequest {
  private final MockMvc mvc;
  private final Authenticated authenticated;

  ListConceptsRequest(final Authenticated authenticated, final MockMvc mvc) {
    this.authenticated = authenticated;
    this.mvc = mvc;
  }

  ResultActions send(String codeset) throws Exception {
    return mvc.perform(
        this.authenticated.withUser(get("/api/v1/valueset/{codeSetNm}/concepts", codeset)));
  }
}
