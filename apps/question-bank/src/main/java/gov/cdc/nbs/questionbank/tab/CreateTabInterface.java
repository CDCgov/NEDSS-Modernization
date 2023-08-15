package gov.cdc.nbs.questionbank.tab;

import gov.cdc.nbs.questionbank.tab.model.CreateTabRequest;
import gov.cdc.nbs.questionbank.tab.model.CreateTabResponse;

public interface CreateTabInterface {

    CreateTabResponse createTab(Long userId, CreateTabRequest request);
}
