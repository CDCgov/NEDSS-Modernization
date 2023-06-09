package gov.cdc.nbs.patient.create;

import gov.cdc.nbs.id.IdGeneratorService;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.patient.identifier.PatientShortIdentifierResolver;
import org.springframework.stereotype.Component;

@Component
class PatientIdentifierGenerator {
    private final IdGeneratorService idGenerator;
    private final PatientShortIdentifierResolver resolver;


    PatientIdentifierGenerator(
        final IdGeneratorService generator,
        final PatientShortIdentifierResolver resolver
    ) {
        this.idGenerator = generator;
        this.resolver = resolver;
    }

    PatientIdentifier generate() {
        long identifier = generatePatientId();
        String local = generateLocalId();
        long shortId = resolver.resolve(local).orElse(identifier);

        return new PatientIdentifier(
            identifier,
            shortId,
            local
        );
    }

    /**
     * Calls the id generator service to retrieve the next available Id for an entity
     */
    private Long generatePatientId() {
        var generatedId = idGenerator.getNextValidId(IdGeneratorService.EntityType.NBS);
        return generatedId.getId();
    }

    /**
     * Calls the id generator service and constructs the localId with the format "prefix + id + suffix"
     */
    private String generateLocalId() {
        var generatedId = idGenerator.getNextValidId(IdGeneratorService.EntityType.PERSON);
        return generatedId.getPrefix() + generatedId.getId() + generatedId.getSuffix();
    }
}
