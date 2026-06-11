package gov.cdc.nbs.option.report.sections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@Component
class ReportSectionListRequester {

  private final MockMvc mvc;

  ReportSectionListRequester(final MockMvc mvc) {
    this.mvc = mvc;
  }

  ResultActions complete() throws Exception {
    return mvc.perform(get("/nbs/api/options/report/sections")).andDo(print());
  }
}
