package gov.cdc.nbs.redirect;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import gov.cdc.nbs.exception.RedirectionException;
import springfox.documentation.annotations.ApiIgnore;


@RestController
@RequestMapping("/nbs/redirect")
public class RedirectController {

  private static final String REDIRECT_HEADER = "NBS_REDIRECT";

  @ApiIgnore
  @GetMapping()
  public RedirectView redirect(HttpServletRequest request) {
    String location = request.getHeader(REDIRECT_HEADER);
    if (location == null) {
      throw new RedirectionException("Failed to find redirect header");
    }
    return new RedirectView(location);
  }
}
