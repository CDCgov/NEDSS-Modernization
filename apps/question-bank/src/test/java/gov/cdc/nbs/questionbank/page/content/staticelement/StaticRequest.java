package gov.cdc.nbs.questionbank.page.content.staticelement;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import com.fasterxml.jackson.databind.ObjectMapper;
import gov.cdc.nbs.questionbank.page.content.staticelement.request.DeleteElementRequest;
import gov.cdc.nbs.questionbank.page.content.staticelement.request.StaticContentRequests;
import gov.cdc.nbs.questionbank.page.content.staticelement.request.UpdateStaticRequests;
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

    ResultActions lineSeparatorRequest(final long page, StaticContentRequests requests) throws Exception {
        return mvc.perform(
                this.authenticated.withUser(post("/api/v1/pages/{page}/content/static/line-separator", page))
                        .content(asJsonString(requests))
                        .contentType(MediaType.APPLICATION_JSON));
    }

    ResultActions hyperlinkRequest(final long page, StaticContentRequests requests) throws Exception {
        return mvc.perform(
                this.authenticated.withUser(post("/api/v1/pages/{page}/content/static/hyperlink", page))
                        .content(asJsonString(requests))
                        .contentType(MediaType.APPLICATION_JSON));
    }

    ResultActions originalElecDocListRequest(final long page, StaticContentRequests requests) throws Exception {
        return mvc.perform(
                this.authenticated.withUser(post("/api/v1/pages/{page}/content/static/original-elec-doc-list", page))
                        .content(asJsonString(requests))
                        .contentType(MediaType.APPLICATION_JSON));
    }

    ResultActions readOnlyCommentsRequest(final long page, StaticContentRequests requests) throws Exception {
        return mvc.perform(
                this.authenticated.withUser(post("/api/v1/pages/{page}/content/static/read-only-comments", page))
                        .content(asJsonString(requests))
                        .contentType(MediaType.APPLICATION_JSON));
    }

    ResultActions readOnlyParticipantsListRequest(final long page, StaticContentRequests requests) throws Exception {
        return mvc.perform(
                this.authenticated.withUser(post("/api/v1/pages/{page}/content/static/read-only-participants-list", page))
                        .content(asJsonString(requests))
                        .contentType(MediaType.APPLICATION_JSON));
    }

    ResultActions deleteStaticElementRequest(final long page, DeleteElementRequest requests) throws Exception {
        return mvc.perform(
                this.authenticated.withUser(delete("/api/v1/pages/{page}/content/static/delete-static-element", page))
                        .content(asJsonString(requests))
                        .contentType(MediaType.APPLICATION_JSON));
    }

    ResultActions updateHyperlinkRequest(UpdateStaticRequests.UpdateHyperlink request, final long page, final long componentId) throws Exception {
        return mvc.perform(
                this.authenticated.withUser(post("/api/v1/pages/{page}/content/static/{componentId}/update/hyperlink", page, componentId))
                        .content(asJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON));
    }

    private static String asJsonString(final Object obj) throws Exception {
        return new ObjectMapper().writeValueAsString(obj);
    }
}

