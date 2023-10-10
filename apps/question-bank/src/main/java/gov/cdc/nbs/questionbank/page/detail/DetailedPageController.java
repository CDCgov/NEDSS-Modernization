package gov.cdc.nbs.questionbank.page.detail;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/pages/{id}")
class DetailedPageController {

  private final DetailedPageResolver resolver;

  DetailedPageController(final DetailedPageResolver resolver) {
    this.resolver = resolver;
  }

  @GetMapping
  @PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
  Optional<DetailedPageResponse> details(@PathVariable("id") long page) {
    return resolver.resolve(page);
  }

}
