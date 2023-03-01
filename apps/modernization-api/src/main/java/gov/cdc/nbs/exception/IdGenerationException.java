package gov.cdc.nbs.exception;

import gov.cdc.nbs.service.IdGeneratorService.EntityType;

public class IdGenerationException extends RuntimeException {
    public IdGenerationException(EntityType type) {
        super("Failed to generate Id for type: " + type.toString());
    }
}
