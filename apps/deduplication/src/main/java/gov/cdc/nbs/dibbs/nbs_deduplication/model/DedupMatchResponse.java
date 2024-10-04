package gov.cdc.nbs.dibbs.nbs_deduplication.model;

public record DedupMatchResponse(String patient, MatchType matchType) {


  public enum MatchType {
    EXACT,
    HUMAN_REVIEW,
    NONE;
  }
}
