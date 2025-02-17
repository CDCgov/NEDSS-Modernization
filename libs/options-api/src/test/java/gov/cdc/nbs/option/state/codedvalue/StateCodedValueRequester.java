package gov.cdc.nbs.option.state.codedvalue;

import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@Component
class StateCodedValueRequester {

  private final MockMvc mvc;

  StateCodedValueRequester(final MockMvc mvc) {
    this.mvc = mvc;
  }

  ResultActions complete() throws Exception {
    return mvc.perform(
        get("/nbs/api/options/states")).andDo(print());
  }
}
