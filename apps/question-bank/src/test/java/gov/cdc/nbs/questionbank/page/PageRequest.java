package gov.cdc.nbs.questionbank.page;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.cdc.nbs.questionbank.page.request.PagePublishRequest;
import gov.cdc.nbs.testing.interaction.http.Authenticated;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@Component
public class PageRequest {
  private final Authenticated authenticated;
  private final MockMvc mvc;

  PageRequest(final Authenticated authenticated, final MockMvc mvc) {
    this.authenticated = authenticated;
    this.mvc = mvc;
  }

  ResultActions deletePageRequest(final long page) throws Exception {
    return mvc.perform(
        this.authenticated.withUser(delete("/api/v1/pages/{page}/delete-draft", page)));
  }

  ResultActions publishPage(final long page, PagePublishRequest request) {
    try {
      return mvc.perform(
          this.authenticated
              .withUser(put("/api/v1/pages/{page}/publish", page))
              .content(asJsonString(request))
              .contentType(MediaType.APPLICATION_JSON));
    } catch (Exception exception) {
      throw new IllegalStateException("Unable to execute Page publish Request", exception);
    }
  }

  private static String asJsonString(final Object obj) throws Exception {
    return new ObjectMapper().writeValueAsString(obj);
  }
}
