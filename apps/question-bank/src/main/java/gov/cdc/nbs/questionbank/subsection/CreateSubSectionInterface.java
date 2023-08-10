package gov.cdc.nbs.questionbank.subsection;

import gov.cdc.nbs.questionbank.subsection.model.CreateSubSectionResponse;
import gov.cdc.nbs.questionbank.subsection.model.CreateSubSectionRequest;

public interface CreateSubSectionInterface {

    CreateSubSectionResponse createSubSection(Long userId, CreateSubSectionRequest request);
}
