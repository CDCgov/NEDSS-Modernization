package gov.cdc.nbs.option.concept;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
class ConceptOptionsController {

  private final ConceptOptionFinder finder;

  ConceptOptionsController(final ConceptOptionFinder finder) {
    this.finder = finder;
  }

  @ApiOperation(
      value = "Concept Options by Value Set",
      notes = "Provides options from Concepts grouped into a value set.",
      tags = {"ConceptOptions"}
  )
  @ApiImplicitParam(
      name = "Authorization",
      required = true,
      paramType = "header",
      dataTypeClass = String.class
  )
  @GetMapping("nbs/api/options/concepts/{name}")
  ConceptOptionsResponse all(
      @PathVariable final String name
  ) {
    Collection<ConceptOption> found = this.finder.find(name);
    return ConceptOptionsResponseMapper.asResponse(name, found);
  }

}
