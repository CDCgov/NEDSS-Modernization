package gov.cdc.nbs.deduplication.exception;

public class ConfigurationParsingException extends RuntimeException {
  public ConfigurationParsingException() {
    super("Failed to parse configuration");
  }

}
