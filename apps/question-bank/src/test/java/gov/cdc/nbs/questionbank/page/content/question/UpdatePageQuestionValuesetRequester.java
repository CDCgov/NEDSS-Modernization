package gov.cdc.nbs.questionbank.page.content.question;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import com.fasterxml.jackson.databind.ObjectMapper;
import gov.cdc.nbs.questionbank.page.content.question.request.UpdatePageCodedQuestionValuesetRequest;
import gov.cdc.nbs.testing.interaction.http.Authenticated;

@Component
public class UpdatePageQuestionValuesetRequester {
  private final MockMvc mvc;
  private final Authenticated authenticated;
  private final ObjectMapper mapper;

  UpdatePageQuestionValuesetRequester(
      final Authenticated authenticated,
      final MockMvc mvc,
      final ObjectMapper mapper) {
    this.authenticated = authenticated;
    this.mvc = mvc;
    this.mapper = mapper;
  }

  ResultActions send(long page, long questionId, UpdatePageCodedQuestionValuesetRequest request) throws Exception {
    return mvc.perform(
        this.authenticated.withUser(put("/api/v1/pages/{page}/questions/coded/{questionId}/valueset", page, questionId))
            .content(mapper.writeValueAsBytes(request))
            .contentType(MediaType.APPLICATION_JSON));
  }
}
