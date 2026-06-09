package gov.cdc.nbs.option.report.datasources.columns;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@Component
class ReportDataSourceColumnsListRequester {

  private final MockMvc mvc;

  ReportDataSourceColumnsListRequester(final MockMvc mvc) {
    this.mvc = mvc;
  }

  ResultActions complete(final String dataSource) throws Exception {
    return mvc.perform(
            get("/nbs/api/options/report/datasource/columns/filterable/{dataSource}", dataSource))
        .andDo(print());
  }
}
