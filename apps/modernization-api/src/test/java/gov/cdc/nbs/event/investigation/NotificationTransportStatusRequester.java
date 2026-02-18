package gov.cdc.nbs.event.investigation;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import gov.cdc.nbs.testing.interaction.http.Authenticated;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@Component
class NotificationTransportStatusRequester {
  private final MockMvc mvc;
  private final Authenticated authenticated;

  NotificationTransportStatusRequester(final MockMvc mvc, final Authenticated authenticated) {
    this.mvc = mvc;
    this.authenticated = authenticated;
  }

  ResultActions request(final Long investigationId) throws Exception {
    return mvc.perform(
            this.authenticated.withUser(
                get(
                    "/nbs/api/investigations/{id}/notifications/transport/status",
                    investigationId)))
        .andDo(print());
  }
}
