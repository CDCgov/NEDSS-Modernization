package gov.cdc.nbs.deduplication.response;

import java.util.List;

public record ExactMatchResponse(List<String> matches, long timeTakenMs) {

}
