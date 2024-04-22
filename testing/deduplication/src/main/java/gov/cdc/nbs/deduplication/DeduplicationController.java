package gov.cdc.nbs.deduplication;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import gov.cdc.nbs.deduplication.exact.ExactMatchService;
import gov.cdc.nbs.deduplication.request.MatchRequest;
import gov.cdc.nbs.deduplication.response.MatchResponse;
import gov.cdc.nbs.deduplication.response.RowsAffected;
import gov.cdc.nbs.deduplication.similar.SimilarMatchService;


@RestController
public class DeduplicationController {
  private final SimilarMatchService similarMatchService;
  private final ExactMatchService exactMatchService;
  private final DataManager dataManager;

  public DeduplicationController(
      final SimilarMatchService similarMatchService,
      final ExactMatchService exactMatchService,
      final DataManager dataManager) {
    this.similarMatchService = similarMatchService;
    this.exactMatchService = exactMatchService;
    this.dataManager = dataManager;
  }

  @PostMapping("/similar")
  public MatchResponse matchSimilar() {
    return similarMatchService.match();
  }

  @PostMapping("/exact")
  public MatchResponse matchExact(@RequestBody MatchRequest request) {
    return exactMatchService.match(request);
  }

  @PutMapping("/reset")
  public RowsAffected resetData() {
    return new RowsAffected(dataManager.reset());
  }

}
