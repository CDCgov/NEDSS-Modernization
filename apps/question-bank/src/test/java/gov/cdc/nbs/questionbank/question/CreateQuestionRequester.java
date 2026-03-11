package gov.cdc.nbs.questionbank.question;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.cdc.nbs.questionbank.question.request.create.CreateCodedQuestionRequest;
import gov.cdc.nbs.questionbank.question.request.create.CreateDateQuestionRequest;
import gov.cdc.nbs.questionbank.question.request.create.CreateNumericQuestionRequest;
import gov.cdc.nbs.questionbank.question.request.create.CreateTextQuestionRequest;
import gov.cdc.nbs.testing.interaction.http.Authenticated;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@Component
public class CreateQuestionRequester {
  private final MockMvc mvc;
  private final Authenticated authenticated;
  private final ObjectMapper mapper;

  CreateQuestionRequester(
      final Authenticated authenticated, final MockMvc mvc, final ObjectMapper mapper) {
    this.authenticated = authenticated;
    this.mvc = mvc;
    this.mapper = mapper;
  }

  ResultActions send(CreateTextQuestionRequest request) throws Exception {
    return mvc.perform(
        this.authenticated
            .withUser(post("/api/v1/questions/text"))
            .content(mapper.writeValueAsBytes(request))
            .contentType(MediaType.APPLICATION_JSON));
  }

  ResultActions send(CreateDateQuestionRequest request) throws Exception {
    return mvc.perform(
        this.authenticated
            .withUser(post("/api/v1/questions/date"))
            .content(mapper.writeValueAsBytes(request))
            .contentType(MediaType.APPLICATION_JSON));
  }

  ResultActions send(CreateNumericQuestionRequest request) throws Exception {
    return mvc.perform(
        this.authenticated
            .withUser(post("/api/v1/questions/numeric"))
            .content(mapper.writeValueAsBytes(request))
            .contentType(MediaType.APPLICATION_JSON));
  }

  ResultActions send(CreateCodedQuestionRequest request) throws Exception {
    return mvc.perform(
        this.authenticated
            .withUser(post("/api/v1/questions/coded"))
            .content(mapper.writeValueAsBytes(request))
            .contentType(MediaType.APPLICATION_JSON));
  }
}
