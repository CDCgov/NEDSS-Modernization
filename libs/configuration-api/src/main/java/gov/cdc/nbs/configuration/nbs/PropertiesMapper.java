package gov.cdc.nbs.configuration.nbs;

import static gov.cdc.nbs.configuration.nbs.NbsPropertiesFinder.CODE_BASE;
import static gov.cdc.nbs.configuration.nbs.NbsPropertiesFinder.HIV_PROGRAM_AREAS;
import static gov.cdc.nbs.configuration.nbs.NbsPropertiesFinder.STD_PROGRAM_AREAS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PropertiesMapper {
  private PropertiesMapper() {}

  // entries from the database to be included in the exposed Properties object
  private static final List<String> includedProperties = Arrays.asList(CODE_BASE);

  public static Properties toProperties(Map<String, String> map) {
    return new Properties(
        tokenize(map.get(STD_PROGRAM_AREAS)),
        tokenize(map.get(HIV_PROGRAM_AREAS)),
        map.entrySet().stream()
            .filter(e -> includedProperties.contains(e.getKey()))
            .collect(Collectors.toMap(Entry::getKey, Entry::getValue)));
  }

  // NBS Classic tokenizes STD and HIV program areas in the cachedStdProgramArea method of the
  // PropertyUtil
  private static List<String> tokenize(String original) {
    if (original == null) {
      return new ArrayList<>();
    }
    return Stream.of(original.split(",")).map(s -> s.toUpperCase().trim()).toList();
  }
}
