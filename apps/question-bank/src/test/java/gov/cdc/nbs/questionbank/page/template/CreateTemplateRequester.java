package gov.cdc.nbs.questionbank.page.template;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.cdc.nbs.testing.interaction.http.Authenticated;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@Component
class CreateTemplateRequester {

  private final ObjectMapper mapper;
  private final Authenticated authenticated;
  private final MockMvc mvc;

  CreateTemplateRequester(
      final ObjectMapper mapper, final Authenticated authenticated, final MockMvc mvc) {
    this.mapper = mapper;
    this.authenticated = authenticated;
    this.mvc = mvc;
  }

  ResultActions request(final long page, final CreateTemplateRequest request) {

    try {
      byte[] content = this.mapper.writeValueAsBytes(request);
      return mvc.perform(
          this.authenticated
              .withUser(post("/api/v1/pages/{page}/template", page))
              .contentType(MediaType.APPLICATION_JSON)
              .content(content));
    } catch (Exception exception) {
      throw new IllegalStateException("Unable to execute Page Information Request", exception);
    }
  }
}
