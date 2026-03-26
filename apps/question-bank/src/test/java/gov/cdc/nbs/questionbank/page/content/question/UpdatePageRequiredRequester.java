package gov.cdc.nbs.questionbank.page.content.question;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.cdc.nbs.questionbank.page.content.question.request.UpdatePageQuestionRequiredRequest;
import gov.cdc.nbs.testing.interaction.http.Authenticated;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@Component
public class UpdatePageRequiredRequester {

  private final MockMvc mvc;
  private final Authenticated authenticated;
  private final ObjectMapper mapper;

  UpdatePageRequiredRequester(
      final Authenticated authenticated, final MockMvc mvc, final ObjectMapper mapper) {
    this.authenticated = authenticated;
    this.mvc = mvc;
    this.mapper = mapper;
  }

  ResultActions send(long page, long questionId, UpdatePageQuestionRequiredRequest request)
      throws Exception {
    return mvc.perform(
        this.authenticated
            .withUser(put("/api/v1/pages/{page}/questions/{questionId}/required", page, questionId))
            .content(mapper.writeValueAsBytes(request))
            .contentType(MediaType.APPLICATION_JSON));
  }
}
