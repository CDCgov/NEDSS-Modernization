package gov.cdc.nbs.id;

import gov.cdc.nbs.id.IdGeneratorService.EntityType;

public class IdGenerationException extends RuntimeException {
    public IdGenerationException(EntityType type) {
        super("Failed to generate Id for type: " + type.toString());
    }
}
