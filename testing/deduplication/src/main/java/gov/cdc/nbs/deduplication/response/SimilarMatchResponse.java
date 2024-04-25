package gov.cdc.nbs.deduplication.response;

import java.util.List;

public record SimilarMatchResponse(List<MatchGroup> groups, int groupCount, long timeTakenMs) {

  public record MatchGroup(List<String> personUids) {
  }
}
