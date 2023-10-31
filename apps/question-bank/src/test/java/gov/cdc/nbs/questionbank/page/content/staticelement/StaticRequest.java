package gov.cdc.nbs.questionbank.page.content.staticelement;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import com.fasterxml.jackson.databind.ObjectMapper;
import gov.cdc.nbs.testing.interaction.http.Authenticated;

@Component
class StaticRequest {
    private final Authenticated authenticated;
    private final MockMvc mvc;

    StaticRequest(
            final Authenticated authenticated,
            final MockMvc mvc,
            final ObjectMapper mapper) {
        this.authenticated = authenticated;
        this.mvc = mvc;
    }

    ResultActions lineSeparatorRequest(final long page, String jsonRequestBody) throws Exception {
        return mvc.perform(
                this.authenticated.withUser(post("/api/v1/pages/{page}/content/static/line-separator", page))
                        .content(jsonRequestBody)
                        .contentType(MediaType.APPLICATION_JSON));
    }
}
