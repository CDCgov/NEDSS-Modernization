package gov.cdc.nbs.option.states.codedvalue;

import java.util.Collection;
import io.swagger.v3.oas.annotations.Operation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StateCodedValueController {

  private final StatesCodedValueFinder finder;

  public StateCodedValueController(final StatesCodedValueFinder finder) {
    this.finder = finder;
  }

  @Operation(operationId = "states", summary = "States Option", description = "Provides all States options.", tags = "StatesOptions")
  @GetMapping("/nbs/api/options/states")
  public Collection<StateCodedValue> findAllStates() {
    return finder.all();
  }

}
