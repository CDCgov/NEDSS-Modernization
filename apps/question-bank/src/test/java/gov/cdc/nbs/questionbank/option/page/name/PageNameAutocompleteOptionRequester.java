package gov.cdc.nbs.questionbank.option.page.name;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import gov.cdc.nbs.testing.interaction.http.Authenticated;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@Component
class PageNameAutocompleteOptionRequester {

  private final Authenticated authenticated;
  private final MockMvc mvc;

  PageNameAutocompleteOptionRequester(final Authenticated authenticated, final MockMvc mvc) {
    this.authenticated = authenticated;
    this.mvc = mvc;
  }

  ResultActions complete(final String criteria) {
    try {
      return mvc.perform(
              this.authenticated.withUser(
                  get("/api/v1/options/page/names/search").param("criteria", criteria)))
          .andDo(print());
    } catch (Exception exception) {
      throw new IllegalStateException(
          "Unable to execute Page name autocomplete options Request", exception);
    }
  }

  ResultActions complete(final String criteria, final int limit) {
    try {
      return mvc.perform(
              this.authenticated.withUser(
                  get("/api/v1/options/page/names/search")
                      .param("criteria", criteria)
                      .param("limit", String.valueOf(limit))))
          .andDo(print());
    } catch (Exception exception) {
      throw new IllegalStateException(
          "Unable to execute Page name autocomplete options Request", exception);
    }
  }
}
