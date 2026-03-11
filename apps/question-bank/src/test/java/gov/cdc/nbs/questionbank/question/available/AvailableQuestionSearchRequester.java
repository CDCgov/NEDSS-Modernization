package gov.cdc.nbs.questionbank.question.available;

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
class AvailableQuestionSearchRequester {
  private final ObjectMapper mapper;
  private final Authenticated authenticated;
  private final MockMvc mvc;

  AvailableQuestionSearchRequester(
      final ObjectMapper mapper, final Authenticated authenticated, final MockMvc mvc) {
    this.mapper = mapper;
    this.authenticated = authenticated;
    this.mvc = mvc;
  }

  ResultActions request(
      final Long pageId, final Pageable page, final AvailableQuestionCriteria criteria)
      throws Exception {

    MockHttpServletRequestBuilder builder =
        page.getSort()
            .get()
            .map(order -> "%s,%s".formatted(order.getProperty(), order.getDirection()))
            .reduce(
                createRequest(pageId, page, criteria),
                (existing, order) -> existing.param("sort", order),
                (existing, next) -> existing);

    return mvc.perform(this.authenticated.withUser(builder)).andDo(print());
  }

  private MockHttpServletRequestBuilder createRequest(
      final Long pageId, final Pageable page, final AvailableQuestionCriteria criteria)
      throws JsonProcessingException {

    byte[] content = mapper.writeValueAsBytes(criteria);

    return post("/api/v1/questions/page/{pageId}/search", pageId)
        .content(content)
        .contentType(MediaType.APPLICATION_JSON)
        .param("page", String.valueOf(page.getPageNumber()))
        .param("size", String.valueOf(page.getPageSize()));
  }
}
