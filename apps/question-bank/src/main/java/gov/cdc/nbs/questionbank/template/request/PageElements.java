package gov.cdc.nbs.questionbank.template.request;

import gov.cdc.nbs.questionbank.entity.WaNndMetadatum;
import gov.cdc.nbs.questionbank.entity.WaRdbMetadatum;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageElements {
    private List<WaUiMetadata> waUiMetadataList;
    private List<WaRdbMetadatum> waRdbMetadatumList;
    private List<WaNndMetadatum> waNndMetadatumList;
}
