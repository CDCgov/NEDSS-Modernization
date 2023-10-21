package gov.cdc.nbs.concept.rest;

import gov.cdc.nbs.concept.Concept;
import gov.cdc.nbs.concept.ConceptFinder;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
class ConceptsController {

  private final ConceptFinder finder;

  ConceptsController(final ConceptFinder finder) {
    this.finder = finder;
  }

  @ApiOperation(
      value = "Concepts of a Value Set",
      notes = "Provides concepts grouped into a value set.",
      tags = {"Concepts"}
  )
  @ApiImplicitParam(
      name = "Authorization",
      required = true,
      paramType = "header",
      dataTypeClass = String.class
  )
  @GetMapping("nbs/api/concepts/{name}")
  ConceptsResponse all(
      @PathVariable final String name
  ) {
    Collection<Concept> found = this.finder.find(name);
    return ConceptsResponseMapper.asResponse(name, found);
  }

}
