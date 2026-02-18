package gov.cdc.nbs.configuration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@Component
class ConfigurationRequester {

  private final MockMvc mvc;

  ConfigurationRequester(final MockMvc mvc) {
    this.mvc = mvc;
  }

  ResultActions request() throws Exception {
    return mvc.perform(get("/nbs/api/configuration")).andDo(print());
  }
}
