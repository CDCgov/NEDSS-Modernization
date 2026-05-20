package gov.cdc.nbs.option.report.distinct;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@Component
class DistinctValuesListRequester {

  private final MockMvc mvc;

  DistinctValuesListRequester(final MockMvc mvc) {
    this.mvc = mvc;
  }

  ResultActions request(final String id) throws Exception {
    return mvc.perform(get("/nbs/api/options/report/distinct-values/{id}", id)).andDo(print());
  }
}
