package gov.cdc.nbs.graphql;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import gov.cdc.nbs.testing.interaction.http.Authenticated;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Component
public class GraphQLRequest {

  private final ObjectMapper mapper;
  private final Authenticated authenticated;
  private final MockMvc mvc;

  public GraphQLRequest(
      final ObjectMapper mapper,
      final Authenticated authenticated,
      final MockMvc mvc
  ) {
    this.mapper = mapper;
    this.authenticated = authenticated;
    this.mvc = mvc;
  }

  public ResultActions query(
      final String query,
      final JsonNode variables
  ) {

    try {
      ObjectNode payload = mapper.createObjectNode()
          .put("query", query)
          .set("variables", variables);

      byte[] content = mapper.writeValueAsBytes(payload);

      ResultActions initial = mvc.perform(
          this.authenticated.withUser(post("/graphql"))
              .content(content)
              .contentType(MediaType.APPLICATION_JSON)
      );

      MvcResult result = initial.andReturn();
      int status = result.getResponse().getStatus();

      return status == 200
          ? mvc.perform(asyncDispatch(result))
          : initial;

    } catch (Exception exception) {
      throw new IllegalStateException((exception));
    }
  }
}



