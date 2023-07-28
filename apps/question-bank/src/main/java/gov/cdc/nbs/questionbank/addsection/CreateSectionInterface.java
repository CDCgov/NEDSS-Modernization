package gov.cdc.nbs.questionbank.addsection;

import gov.cdc.nbs.questionbank.addsection.model.CreateSectionRequest;
public interface CreateSectionInterface {

    CreateSectionResponse createSection(Long userId, CreateSectionRequest request);
}
