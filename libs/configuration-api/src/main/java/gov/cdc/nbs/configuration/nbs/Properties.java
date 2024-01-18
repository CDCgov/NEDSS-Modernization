package gov.cdc.nbs.configuration.nbs;

import java.util.List;
import java.util.Map;

public record Properties(
    List<String> stdProgramAreas,
    List<String> hivProgramAreas,
    Map<String, String> entries) {
}
