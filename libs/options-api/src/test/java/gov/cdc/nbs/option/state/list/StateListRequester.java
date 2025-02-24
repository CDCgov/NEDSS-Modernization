package gov.cdc.nbs.option.state.list;

import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@Component
class StateListRequester {

  private final MockMvc mvc;

  StateListRequester(final MockMvc mvc) {
    this.mvc = mvc;
  }

  ResultActions complete() throws Exception {
    return mvc.perform(
        get("/nbs/api/options/states")).andDo(print());
  }
}
