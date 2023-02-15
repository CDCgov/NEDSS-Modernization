package gov.cdc.nbs.patientlistener.exception;

import gov.cdc.nbs.patientlistener.service.IdGeneratorService.EntityType;

public class IdGenerationException extends RuntimeException {
    public IdGenerationException(EntityType type) {
        super("Failed to generate Id for type: " + type.toString());
    }
}
