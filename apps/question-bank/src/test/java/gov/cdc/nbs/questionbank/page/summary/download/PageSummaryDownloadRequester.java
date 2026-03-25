package gov.cdc.nbs.questionbank.page.summary.download;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.cdc.nbs.questionbank.page.summary.search.PageSummaryRequest;
import gov.cdc.nbs.testing.interaction.http.Authenticated;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@Component
public class PageSummaryDownloadRequester {
  private final Authenticated authenticated;
  private final MockMvc mvc;
  private final ObjectMapper mapper;

  PageSummaryDownloadRequester(
      final Authenticated authenticated, final MockMvc mvc, final ObjectMapper mapper) {
    this.authenticated = authenticated;
    this.mvc = mvc;
    this.mapper = mapper;
  }

  ResultActions csv(final PageSummaryRequest request, final Pageable pageable) throws Exception {
    MockHttpServletRequestBuilder builder =
        post("/api/v1/pages/csv")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(request));
    if (pageable != null) {
      builder.param("sort", pageable.getSort().toString());
    }
    return mvc.perform(this.authenticated.withUser(builder)).andDo(print());
  }

  ResultActions pdf(final PageSummaryRequest request, final Pageable pageable) throws Exception {
    MockHttpServletRequestBuilder builder =
        post("/api/v1/pages/pdf")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(request));
    if (pageable != null) {
      builder.param("sort", pageable.getSort().toString());
    }
    return mvc.perform(this.authenticated.withUser(builder)).andDo(print());
  }
}
