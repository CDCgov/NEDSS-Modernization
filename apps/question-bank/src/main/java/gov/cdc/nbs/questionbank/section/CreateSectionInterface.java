package gov.cdc.nbs.questionbank.section;

import gov.cdc.nbs.questionbank.section.model.CreateSectionRequest;
import gov.cdc.nbs.questionbank.section.model.CreateSectionResponse;

public interface CreateSectionInterface {

    CreateSectionResponse createSection(Long userId, CreateSectionRequest request);
}
