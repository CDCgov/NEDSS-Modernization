package gov.cdc.nbs.questionbank.page.content.question;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import gov.cdc.nbs.testing.interaction.http.Authenticated;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@Component
public class ValidatePageQuestionDataMartRequester {
  private final MockMvc mvc;
  private final Authenticated authenticated;

  ValidatePageQuestionDataMartRequester(final Authenticated authenticated, final MockMvc mvc) {
    this.authenticated = authenticated;
    this.mvc = mvc;
  }

  ResultActions send(long page, long questionId, String dataMart) throws Exception {
    return mvc.perform(
        this.authenticated.withUser(
            get(
                "/api/v1/pages/{page}/questions/{questionId}/datamart/validate"
                    + "?datamart="
                    + dataMart,
                page,
                questionId)));
  }
}
