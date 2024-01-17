package gov.cdc.nbs.configuration;

import java.util.Map;

public record Configuration(Features features, Map<String, String> configuration) {

}
