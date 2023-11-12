package gov.cdc.nbs.questionbank.template.request;

import gov.cdc.nbs.questionbank.entity.PageCondMapping;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.pagerule.WaRuleMetadata;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
public class PageManagement {
        private WaTemplate waTemplate;
        private List<PageCondMapping> pageCodeMappings;
        private PageElements pageElements;
        private List<WaRuleMetadata> waRuleMetadataList;
        }
