package gov.cdc.nbs.deduplication.response;

import java.util.List;

public record DataIngestionMatchResponse(List<String> matches, long timeTakenMs) {

}
