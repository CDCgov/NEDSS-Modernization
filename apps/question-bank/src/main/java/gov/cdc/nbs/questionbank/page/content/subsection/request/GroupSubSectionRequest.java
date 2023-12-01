package gov.cdc.nbs.questionbank.page.content.subsection.request;

import java.util.List;

public record GroupSubSectionRequest(long id, String blockName, List<Batch> batches) {
    public record Batch(long id, char batchTableAppearIndCd, String batchTableHeader, int batchTableColumnWidth) {
    }

}
