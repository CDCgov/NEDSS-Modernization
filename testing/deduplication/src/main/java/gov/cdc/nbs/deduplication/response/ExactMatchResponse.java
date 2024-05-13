package gov.cdc.nbs.deduplication.response;

import java.util.ArrayList;
import java.util.List;

public record ExactMatchResponse(List<ExactMatch> groups, long timeTakenMs) {

  public record ExactMatch(String patient, List<String> matches) {

    public ExactMatch(String id) {
      this(id, new ArrayList<>());
    }
  }

}
