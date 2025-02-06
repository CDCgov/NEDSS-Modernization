package gov.cdc.nbs.patient.profile.investigation.notification;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/nbs/api/notifications")
@PreAuthorize("hasAuthority('VIEW-INVESTIGATION')")
public class NotificationStatusController {
  private final NotificationStatusResolver resolver;

  public NotificationStatusController(final NotificationStatusResolver resolver) {
    this.resolver = resolver;
  }

  @GetMapping("/{id}/transport/status")
  public NotificationStatus findStatus(@PathVariable final String id) {
    return resolver.resolve(id);
  }
}
