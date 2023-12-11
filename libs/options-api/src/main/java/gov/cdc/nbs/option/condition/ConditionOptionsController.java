package gov.cdc.nbs.option.condition;

import gov.cdc.nbs.option.Option;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
class ConditionOptionsController {

  private final ConditionOptionFinder finder;

  ConditionOptionsController(final ConditionOptionFinder finder) {
    this.finder = finder;
  }

  @Operation(
      operationId = "all",
      summary = "Condition Option",
      description = "Provides options from Conditions that have a name matching a criteria.",
      tags = "ConditionOptions"
  )
  @ApiOperation(value = "", nickname = "all")
  @GetMapping("nbs/api/options/conditions")
  Collection<Option> all() {
    return this.finder.find();
  }

}
