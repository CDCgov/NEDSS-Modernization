package gov.cdc.nbs.questionbank.page;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.cdc.nbs.questionbank.page.content.question.request.AddQuestionRequest;
import gov.cdc.nbs.testing.interaction.http.Authenticated;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@Component
class AddQuestionToPageRequester {

  private final Authenticated authenticated;
  private final MockMvc mvc;
  private final ObjectMapper mapper;

  AddQuestionToPageRequester(
      final Authenticated authenticated, final MockMvc mvc, final ObjectMapper mapper) {
    this.authenticated = authenticated;
    this.mvc = mvc;
    this.mapper = mapper;
  }

  ResultActions request(long page, long subsection, AddQuestionRequest request) throws Exception {
    return mvc.perform(
            this.authenticated
                .withUser(
                    post(
                        "/api/v1/pages/{page}/subsection/{subsection}/questions", page, subsection))
                .content(mapper.writeValueAsBytes(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andDo(print());
  }
}
