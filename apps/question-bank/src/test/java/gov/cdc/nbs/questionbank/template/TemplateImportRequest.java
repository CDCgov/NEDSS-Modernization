package gov.cdc.nbs.questionbank.template;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import gov.cdc.nbs.testing.interaction.http.Authenticated;

@Component
class TemplateImportRequest {
  private final Authenticated authenticated;
  private final MockMvc mvc;

  TemplateImportRequest(
      final Authenticated authenticated,
      final MockMvc mvc) {
    this.authenticated = authenticated;
    this.mvc = mvc;
  }

  ResultActions send(TemplateXml xml) throws Exception {
    MockHttpServletRequestBuilder builder =
        multipart("/api/v1/template/import").file("file", xml.xml().getBytes());
    return mvc.perform(this.authenticated.withUser(builder)).andDo(print());
  }
}
