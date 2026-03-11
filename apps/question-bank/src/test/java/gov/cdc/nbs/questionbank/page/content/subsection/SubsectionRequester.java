package gov.cdc.nbs.questionbank.page.content.subsection;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.cdc.nbs.questionbank.page.content.subsection.request.GroupSubSectionRequest;
import gov.cdc.nbs.testing.interaction.http.Authenticated;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@Component
public class SubsectionRequester {

  private final Authenticated authenticated;
  private final MockMvc mockMvc;

  public SubsectionRequester(final Authenticated authenticated, final MockMvc mockMvc) {
    this.authenticated = authenticated;
    this.mockMvc = mockMvc;
  }

  ResultActions subsectionGroup(
      final long page, final long subsection, GroupSubSectionRequest request) throws Exception {
    return mockMvc.perform(
        this.authenticated
            .withUser(post("/api/v1/pages/{page}/subsections/{subsection}/group", page, subsection))
            .content(asJsonString(request))
            .contentType(MediaType.APPLICATION_JSON));
  }

  ResultActions testing(final long page) throws Exception {
    return mockMvc.perform(
        this.authenticated
            .withUser(post("/api/v1/pages/{page}/subsections/testing", page))
            .contentType(MediaType.APPLICATION_JSON));
  }

  private static String asJsonString(final Object obj) throws Exception {
    return new ObjectMapper().writeValueAsString(obj);
  }
}
