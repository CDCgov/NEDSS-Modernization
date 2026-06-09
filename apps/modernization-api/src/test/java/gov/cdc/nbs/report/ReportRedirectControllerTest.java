package gov.cdc.nbs.report;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.servlet.view.RedirectView;

class ReportRedirectControllerTest {

  private ReportRedirectController redirectController = new ReportRedirectController();

  @Test
  void should_issue_redirect() {
    // Given a request with a redirect header
    HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
    when(request.getRequestURL())
        .thenReturn(
            new StringBuffer(
                "http://something.com/nbs/redirect/report/a/futher/path?query_param=1"));

    // When the redirect is processed
    RedirectView resp = redirectController.reportRedirect(request);

    // Then the redirect is issued to the proper location
    assertNotNull(resp);
    String location = resp.getUrl();
    assertEquals("/report/a/futher/path?query_param=1", location);
  }
}
