package gov.cdc.nbs.questionbank.addtab;

import gov.cdc.nbs.questionbank.addtab.model.CreateTabRequest;

public interface CreateTabInterface {

    CreateUiResponse createTab(Long userId, CreateTabRequest request);
}
