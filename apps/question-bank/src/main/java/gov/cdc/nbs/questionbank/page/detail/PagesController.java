package gov.cdc.nbs.questionbank.page.detail;



import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/pages/{id}")
class PagesController {

  private final PagesResolver resolver;

  PagesController(final PagesResolver resolver) {
    this.resolver = resolver;
  }

  @ApiOperation(
      value = "Pages",
      notes = "Provides the details of a Page including the components and the rules",
      tags = "Pages"
  )
  @ApiImplicitParam(
      name = "Authorization",
      required = true,
      paramType = "header",
      dataTypeClass = String.class
  )
  @GetMapping
  @PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
  ResponseEntity<PagesResponse> details(@PathVariable("id") long page) {
    return resolver.resolve(page).map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

}
