package gov.cdc.nbs.option.country.list;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@Component
class CountryListRequester {

  private final MockMvc mvc;

  CountryListRequester(final MockMvc mvc) {
    this.mvc = mvc;
  }

  ResultActions complete() throws Exception {
    return mvc.perform(get("/nbs/api/options/countries")).andDo(print());
  }
}
