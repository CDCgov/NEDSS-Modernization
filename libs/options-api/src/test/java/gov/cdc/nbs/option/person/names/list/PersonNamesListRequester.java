package gov.cdc.nbs.option.person.names.list;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@Component
class PersonNamesListRequester {

  private final MockMvc mvc;

  PersonNamesListRequester(final MockMvc mvc) {
    this.mvc = mvc;
  }

  ResultActions request() throws Exception {
    return mvc.perform(get("/nbs/api/options/person/stdHivWorker/names")).andDo(print());
  }
}
