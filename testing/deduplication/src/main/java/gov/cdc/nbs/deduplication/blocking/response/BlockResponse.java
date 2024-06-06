package gov.cdc.nbs.deduplication.blocking.response;

import java.util.List;

public record BlockResponse(List<BlockMatch> matches) {
  public record BlockMatch(String patient, String matchingValue) {
  }
}
