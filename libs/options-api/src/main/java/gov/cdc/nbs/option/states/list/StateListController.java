package gov.cdc.nbs.option.states.list;

import gov.cdc.nbs.option.Option;
import io.swagger.v3.oas.annotations.Operation;
import java.util.Collection;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StateListController {

  private final StateListFinder finder;

  public StateListController(final StateListFinder finder) {
    this.finder = finder;
  }

  @Operation(
      operationId = "states",
      summary = "States Option",
      description = "Provides all States options.",
      tags = "StatesOptions")
  @GetMapping("/nbs/api/options/states")
  public Collection<Option> findAllStates() {
    return finder.all();
  }
}
