package gov.cdc.nbs.questionbank.page.summary.search;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gov.cdc.nbs.testing.interaction.http.Authenticated;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@Component
class PageSummarySearchRequester {

  private final ObjectMapper mapper;
  private final Authenticated authenticated;
  private final MockMvc mvc;

  PageSummarySearchRequester(
      final ObjectMapper mapper, final Authenticated authenticated, final MockMvc mvc) {
    this.mapper = mapper;
    this.authenticated = authenticated;
    this.mvc = mvc;
  }

  ResultActions request(final Pageable page, final PageSummaryRequest criteria) throws Exception {

    MockHttpServletRequestBuilder builder =
        page.getSort()
            .get()
            .map(order -> "%s,%s".formatted(order.getProperty(), order.getDirection()))
            .reduce(
                createRequest(page, criteria),
                (existing, order) -> existing.param("sort", order),
                (existing, next) -> existing);

    return mvc.perform(this.authenticated.withUser(builder)).andDo(print());
  }

  private MockHttpServletRequestBuilder createRequest(
      final Pageable page, final PageSummaryRequest criteria) throws JsonProcessingException {

    byte[] content = mapper.writeValueAsBytes(criteria);

    return post("/api/v1/pages/search")
        .content(content)
        .contentType(MediaType.APPLICATION_JSON)
        .param("page", String.valueOf(page.getPageNumber()))
        .param("size", String.valueOf(page.getPageSize()));
  }
}
