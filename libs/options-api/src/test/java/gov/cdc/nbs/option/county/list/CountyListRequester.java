package gov.cdc.nbs.option.county.list;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@Component
class CountyListRequester {

  private final MockMvc mvc;

  CountyListRequester(final MockMvc mvc) {
    this.mvc = mvc;
  }

  ResultActions request(final String state) throws Exception {
    return mvc.perform(get("/nbs/api/options/counties/{state}", state)).andDo(print());
  }
}
