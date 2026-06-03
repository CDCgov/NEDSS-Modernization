package gov.cdc.nbs.report;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@ConditionalOnProperty(
    prefix = "nbs.ui.features.report.execution",
    name = "enabled",
    havingValue = "true")
class ReportRedirectController {

  /** Intercepts legacy report add requests and re-routes to mod. Needed for auth to work. */
  @Hidden
  @GetMapping("/nbs/redirect/report/**")
  RedirectView reportRedirect(HttpServletRequest request) {
    String[] parts = request.getRequestURL().toString().split("/nbs/redirect");
    return new RedirectView(parts[1]);
  }
}
