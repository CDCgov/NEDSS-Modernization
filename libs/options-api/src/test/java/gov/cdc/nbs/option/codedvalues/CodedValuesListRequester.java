package gov.cdc.nbs.option.codedvalues;

import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@Component
class CodedValuesRequester {

  private final MockMvc mvc;

  CodedValuesRequester(final MockMvc mvc) {
    this.mvc = mvc;
  }

  ResultActions complete(String path) throws Exception {
    return mvc.perform(
        get("/nbs/api/options/codedvalues/" + path)).andDo(print());
  }
}
