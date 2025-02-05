package gov.cdc.nbs.patient.profile.investigation.notification;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/nbs/api/notification")
@PreAuthorize("hasAuthority('VIEW-INVESTIGATION')")
public class NotificationStatusController {
  private final NotificationStatusResolver resolver;

  public NotificationStatusController(final NotificationStatusResolver resolver) {
    this.resolver = resolver;
  }

  @GetMapping("/transport/status")
  public NotificationStatus findStatus(@RequestParam final String notificationId) {
    return resolver.resolve(notificationId);
  }
}
