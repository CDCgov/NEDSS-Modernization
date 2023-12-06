package gov.cdc.nbs.questionbank.page.content.subsection.request;

import java.util.List;

public record UnGroupSubSectionRequest(long id,  List<Long> batches) {
}
