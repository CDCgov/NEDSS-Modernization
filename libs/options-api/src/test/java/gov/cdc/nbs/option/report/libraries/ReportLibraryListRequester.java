package gov.cdc.nbs.option.report.libraries;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@Component
class ReportLibraryListRequester {

  private final MockMvc mvc;

  ReportLibraryListRequester(final MockMvc mvc) {
    this.mvc = mvc;
  }

  ResultActions complete() throws Exception {
    return mvc.perform(get("/nbs/api/options/report/libraries")).andDo(print());
  }
}
