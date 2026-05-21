package gov.cdc.nbs.option.person.names.list;

import gov.cdc.nbs.configuration.nbs.NbsPropertiesFinder;
import gov.cdc.nbs.configuration.nbs.Properties;
import gov.cdc.nbs.option.Option;
import io.swagger.v3.oas.annotations.Operation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class PersonNamesListController {
  private final PersonNamesListFinder finder;
  private final NbsPropertiesFinder nbsPropertiesFinder;

  PersonNamesListController(
      final PersonNamesListFinder finder, NbsPropertiesFinder nbsPropertiesFinder) {
    this.finder = finder;
    this.nbsPropertiesFinder = nbsPropertiesFinder;
  }

  @Operation(
      operationId = "stdHivWorkerNames",
      summary = "STD HIV Worker Name Option",
      description = "Provides all STD HIV program area worker names.",
      tags = "STDWorkerNameOptions")
  @GetMapping("nbs/api/options/person/stdHivWorker/names")
  Collection<Option> workers() {
    List<String> programAreas = buildProgramAreasValue();
    return finder.findWithParams(Map.of("programAreas", programAreas));
  }

  private List<String> buildProgramAreasValue() {
    Properties nbsProperties = nbsPropertiesFinder.find();
    List<String> hivProgramAreas = nbsProperties.hivProgramAreas();
    List<String> stdProgramAreas = nbsProperties.stdProgramAreas();
    List<String> stdHivProgramAreas = new ArrayList<>(hivProgramAreas);
    stdHivProgramAreas.addAll(stdProgramAreas);
    return stdHivProgramAreas;
  }
}
