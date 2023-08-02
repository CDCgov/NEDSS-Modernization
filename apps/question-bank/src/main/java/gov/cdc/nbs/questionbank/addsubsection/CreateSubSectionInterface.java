package gov.cdc.nbs.questionbank.addsubsection;

import gov.cdc.nbs.questionbank.addsubsection.model.CreateSubSectionRequest;

public interface CreateSubSectionInterface {

    CreateSubSectionResponse createSubSection(Long userId, CreateSubSectionRequest request);
}
