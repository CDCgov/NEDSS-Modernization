package gov.cdc.nbs.concept.rest;

import gov.cdc.nbs.concept.Concept;
import gov.cdc.nbs.concept.ConceptResolver;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
class ConceptAutocompleteController {

  private final ConceptResolver resolver;

  ConceptAutocompleteController(final ConceptResolver finder) {
    this.resolver = finder;
  }

  @ApiOperation(
      value = "Concept Autocomplete",
      notes = "Provides concepts grouped into a value set that have a name matching a criteria.",
      tags = {"Concepts"}
  )
  @ApiImplicitParam(
      name = "Authorization",
      required = true,
      paramType = "header",
      dataTypeClass = String.class
  )
  @GetMapping("nbs/api/concepts/{name}/search")
  ConceptsResponse specific(
      @PathVariable final String name,
      @RequestParam final String criteria,
      @RequestParam(defaultValue = "15") final int limit
  ) {
    Collection<Concept> found = this.resolver.resolve(name, criteria, limit);
    return ConceptsResponseMapper.asResponse(name, found);
  }

}
