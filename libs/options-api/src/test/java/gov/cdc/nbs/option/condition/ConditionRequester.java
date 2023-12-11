package gov.cdc.nbs.option.condition;

import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@Component
class ConditionRequester {

  private final MockMvc mvc;

  ConditionRequester(final MockMvc mvc) {
    this.mvc = mvc;
  }

  ResultActions request() throws Exception {
    return mvc.perform(
        get("/nbs/api/options/conditions")
    );
  }
}
