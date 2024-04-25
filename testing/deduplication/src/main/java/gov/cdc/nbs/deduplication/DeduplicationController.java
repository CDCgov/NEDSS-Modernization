package gov.cdc.nbs.deduplication;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import gov.cdc.nbs.deduplication.dataingest.DataIngestionMatchService;
import gov.cdc.nbs.deduplication.request.MatchRequest;
import gov.cdc.nbs.deduplication.response.DataIngestionMatchResponse;
import gov.cdc.nbs.deduplication.response.RowsAffected;
import gov.cdc.nbs.deduplication.response.SimilarMatchResponse;
import gov.cdc.nbs.deduplication.similar.SimilarMatchService;


@RestController
public class DeduplicationController {
  private final SimilarMatchService similarMatchService;
  private final DataIngestionMatchService dataIngestionMatchService;
  private final DataManager dataManager;

  public DeduplicationController(
      final SimilarMatchService similarMatchService,
      final DataIngestionMatchService dataIngestionMatchService,
      final DataManager dataManager) {
    this.similarMatchService = similarMatchService;
    this.dataIngestionMatchService = dataIngestionMatchService;
    this.dataManager = dataManager;
  }

  @PostMapping("/similar")
  public SimilarMatchResponse matchSimilar() {
    return similarMatchService.match();
  }

  @PostMapping("/data-ingestion")
  public DataIngestionMatchResponse matchDataIngestion(@RequestBody MatchRequest request) {
    return dataIngestionMatchService.match(request);
  }

  @PutMapping("/reset")
  public RowsAffected resetData() {
    return new RowsAffected(dataManager.reset());
  }

}
