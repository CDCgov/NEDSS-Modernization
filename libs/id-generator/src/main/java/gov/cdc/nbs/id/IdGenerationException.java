package gov.cdc.nbs.id;

import gov.cdc.nbs.id.IdGeneratorService.EntityType;

@SuppressWarnings("javaarchitecture:S7027")
public class IdGenerationException extends RuntimeException {
    public IdGenerationException(EntityType type) {
        super("Failed to generate Id for type: " + type.toString());
    }
}
