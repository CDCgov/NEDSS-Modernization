package gov.cdc.nbs.questionbank.condition.search;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.cdc.nbs.questionbank.condition.request.ReadConditionRequest;
import gov.cdc.nbs.testing.interaction.http.Authenticated;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@Component
public class ConditionSearchRequest {
  private final Authenticated authenticated;
  private final MockMvc mvc;

  ConditionSearchRequest(final Authenticated authenticated, final MockMvc mvc) {
    this.authenticated = authenticated;
    this.mvc = mvc;
  }

  ResultActions search(final ReadConditionRequest request, final Pageable pageable) {
    try {
      MockHttpServletRequestBuilder builder =
          post("/api/v1/conditions/search")
              .content(asJsonString(request))
              .contentType(MediaType.APPLICATION_JSON);

      if (pageable != null) {
        builder.param("sort", pageable.getSort().toString().replace(": ", ","));
      }

      return mvc.perform(this.authenticated.withUser(builder));

    } catch (Exception exception) {
      throw new IllegalStateException("Unable to execute condition search request", exception);
    }
  }

  ResultActions available() {
    return available(null);
  }

  ResultActions available(Long page) {
    try {
      String url = "/api/v1/conditions/available";
      if (page != null) {
        url += "?page=" + page.toString();
      }
      MockHttpServletRequestBuilder builder = get(url).contentType(MediaType.APPLICATION_JSON);
      return mvc.perform(this.authenticated.withUser(builder));
    } catch (Exception exception) {
      throw new IllegalStateException("Unable to execute condition search request", exception);
    }
  }

  private static String asJsonString(final Object obj) throws Exception {
    return new ObjectMapper().writeValueAsString(obj);
  }
}
