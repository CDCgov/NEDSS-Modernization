package gov.cdc.nbs.questionbank.question;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gov.cdc.nbs.questionbank.question.addable.AddableQuestionCriteria;
import gov.cdc.nbs.testing.interaction.http.Authenticated;

@Component
class AddableQuestionSearchRequester {
  private final ObjectMapper mapper;
  private final Authenticated authenticated;
  private final MockMvc mvc;

  AddableQuestionSearchRequester(
      final ObjectMapper mapper,
      final Authenticated authenticated,
      final MockMvc mvc) {
    this.mapper = mapper;
    this.authenticated = authenticated;
    this.mvc = mvc;
  }

  ResultActions request(
      final Long pageId,
      final Pageable page,
      final AddableQuestionCriteria criteria) throws Exception {

    MockHttpServletRequestBuilder builder = page.getSort().get()
        .map(order -> String.format("%s,%s", order.getProperty(), order.getDirection()))
        .reduce(
            createRequest(pageId, page, criteria),
            (existing, order) -> existing.param("sort", order),
            (existing, next) -> existing);

    return mvc.perform(this.authenticated.withUser(builder)).andDo(print());
  }

  private MockHttpServletRequestBuilder createRequest(
      final Long pageId,
      final Pageable page,
      final AddableQuestionCriteria criteria) throws JsonProcessingException {

    byte[] content = mapper.writeValueAsBytes(criteria);

    return post("/api/v1/questions/page/{pageId}/search", pageId)
        .content(content)
        .contentType(MediaType.APPLICATION_JSON)
        .param("page", String.valueOf(page.getPageNumber()))
        .param("size", String.valueOf(page.getPageSize()));
  }
}
