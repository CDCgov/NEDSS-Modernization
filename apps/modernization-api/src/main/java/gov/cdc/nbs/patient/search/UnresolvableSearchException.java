package gov.cdc.nbs.patient.search;

/**
 * Indicates that the criteria of a search are contradictory and will never return results.
 */
public class UnresolvableSearchException extends Exception {
  public UnresolvableSearchException(final String message) {
    super(message);
  }
}
