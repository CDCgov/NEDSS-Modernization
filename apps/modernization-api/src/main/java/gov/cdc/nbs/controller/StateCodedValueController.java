package gov.cdc.nbs.controller;

import java.util.Collection;
import gov.cdc.nbs.codes.location.state.StateCodedValue;
import gov.cdc.nbs.codes.location.state.StateCodedValueFinder;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StateCodedValueController {

  private final StateCodedValueFinder finder;

  public StateCodedValueController(final StateCodedValueFinder finder) {
    this.finder = finder;
  }

  @Operation(
      operationId = "states",
      summary = "States Option",
      description = "Provides all States options.",
      tags = "StatesOptions")
  @GetMapping("/nbs/api/options/states")
  public Collection<StateCodedValue> findAllStates() {
    return finder.all();
  }

}
