package gov.cdc.nbs.deduplication.response;

import java.util.List;

public record MatchResponse(List<Match> matches, long timeTakenMs) {

  private record Match(String personUid, int percentage) {
  }
}
