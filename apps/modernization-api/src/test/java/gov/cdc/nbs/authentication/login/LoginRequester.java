package gov.cdc.nbs.authentication.login;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import gov.cdc.nbs.testing.interaction.http.Authenticated;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@Component
class LoginRequester {

  private final ObjectMapper mapper;
  private final Authenticated authenticated;
  private final MockMvc mvc;

  LoginRequester(ObjectMapper mapper, final Authenticated authenticated, final MockMvc mvc) {
    this.mapper = mapper;
    this.authenticated = authenticated;
    this.mvc = mvc;
  }

  ResultActions login(final String user) {
    try {

      JsonNode payload = JsonNodeFactory.instance.objectNode().put("username", user);

      byte[] content = mapper.writeValueAsBytes(payload);

      return mvc.perform(
          this.authenticated.withUser(
              post("/login").contentType(MediaType.APPLICATION_JSON).content(content)));
    } catch (Exception exception) {
      throw new IllegalStateException("Unable to execute Encryption Request", exception);
    }
  }
}
