package gov.cdc.nbs.option.person.stdhivworker.names;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@Component
class StdHivWorkerOptionRequester {

  private final MockMvc mvc;

  StdHivWorkerOptionRequester(final MockMvc mvc) {
    this.mvc = mvc;
  }

  ResultActions request() throws Exception {
    return mvc.perform(get("/nbs/api/options/person/std-hiv-worker/names")).andDo(print());
  }
}
