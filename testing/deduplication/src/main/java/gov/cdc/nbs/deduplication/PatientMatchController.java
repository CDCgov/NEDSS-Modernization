package gov.cdc.nbs.deduplication;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import gov.cdc.nbs.deduplication.dataingest.DataIngestionMatchService;
import gov.cdc.nbs.deduplication.exact.ExactMatchService;
import gov.cdc.nbs.deduplication.model.PatientData;
import gov.cdc.nbs.deduplication.request.MatchRequest;
import gov.cdc.nbs.deduplication.response.DataIngestionMatchResponse;
import gov.cdc.nbs.deduplication.response.DataLoadResponse;
import gov.cdc.nbs.deduplication.response.ExactMatchResponse;
import gov.cdc.nbs.deduplication.response.RowsAffected;
import gov.cdc.nbs.deduplication.response.SimilarMatchResponse;
import gov.cdc.nbs.deduplication.similar.SimilarMatchService;


@RestController
public class PatientMatchController {
  private final SimilarMatchService similarMatchService;
  private final DataIngestionMatchService dataIngestionMatchService;
  private final ExactMatchService exactMatchService;
  private final DataManager dataManager;

  public PatientMatchController(
      final SimilarMatchService similarMatchService,
      final DataIngestionMatchService dataIngestionMatchService,
      final ExactMatchService exactMatchService,
      final DataManager dataManager) {
    this.similarMatchService = similarMatchService;
    this.dataIngestionMatchService = dataIngestionMatchService;
    this.exactMatchService = exactMatchService;
    this.dataManager = dataManager;
  }

  @PostMapping("/exact")
  public ExactMatchResponse matchExact() {
    return exactMatchService.match();
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

  @PostMapping("/load")
  public DataLoadResponse loadData(@RequestBody List<PatientData> data) {
    return dataManager.load(data);
  }

}
