package gov.cdc.nbs.option.person.names;

import gov.cdc.nbs.configuration.nbs.NbsPropertiesFinder;
import gov.cdc.nbs.configuration.nbs.Properties;
import java.util.ArrayList;
import java.util.List;

public final class StdHivProgramAreaFinder {
  public static List<String> values(final NbsPropertiesFinder nbsPropertiesFinder) {
    Properties nbsProperties = nbsPropertiesFinder.find();
    List<String> hivProgramAreas = nbsProperties.hivProgramAreas();
    List<String> stdProgramAreas = nbsProperties.stdProgramAreas();
    List<String> stdHivProgramAreas = new ArrayList<>(hivProgramAreas);
    stdHivProgramAreas.addAll(stdProgramAreas);
    return stdHivProgramAreas;
  }

  private StdHivProgramAreaFinder() {}
}
