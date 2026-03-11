package gov.cdc.nbs.questionbank.valueset;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.cdc.nbs.questionbank.valueset.request.ValueSetSearchRequest;
import gov.cdc.nbs.testing.interaction.http.Authenticated;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@Component
public class ValueSetOptionRequester {
  private final MockMvc mvc;
  private final Authenticated authenticated;
  private final ObjectMapper mapper;

  ValueSetOptionRequester(
      final Authenticated authenticated, final MockMvc mvc, final ObjectMapper mapper) {
    this.authenticated = authenticated;
    this.mvc = mvc;
    this.mapper = mapper;
  }

  ResultActions findAll() throws Exception {
    return mvc.perform(this.authenticated.withUser(get("/api/v1/valueset/options")));
  }

  ResultActions search(final ValueSetSearchRequest request, final Pageable pageable)
      throws Exception {
    MockHttpServletRequestBuilder builder =
        this.authenticated
            .withUser(post("/api/v1/valueset/options/search"))
            .content(mapper.writeValueAsBytes(request))
            .contentType(MediaType.APPLICATION_JSON);

    if (pageable != null) {
      builder.param("sort", pageable.getSort().toString().replace(": ", ","));
    }

    return mvc.perform(builder);
  }
}
