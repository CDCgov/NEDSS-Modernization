package gov.cdc.nbs.questionbank.page;

import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import gov.cdc.nbs.testing.interaction.http.Authenticated;

@Component
public class PageRequest {
    private final Authenticated authenticated;
    private final MockMvc mvc;

    PageRequest(
            final Authenticated authenticated,
            final MockMvc mvc,
            final ObjectMapper mapper) {
        this.authenticated = authenticated;
        this.mvc = mvc;
    }
}
