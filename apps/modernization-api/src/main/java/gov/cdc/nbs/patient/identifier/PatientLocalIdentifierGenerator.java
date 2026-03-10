package gov.cdc.nbs.patient.identifier;

import gov.cdc.nbs.id.IdGeneratorService;
import org.springframework.stereotype.Component;

@Component
public class PatientLocalIdentifierGenerator {
  private final IdGeneratorService service;

  public PatientLocalIdentifierGenerator(final IdGeneratorService service) {
    this.service = service;
  }

  public String generate() {
    var generatedId = service.getNextValidId(IdGeneratorService.EntityType.PERSON);
    return generatedId.getPrefix() + generatedId.getId() + generatedId.getSuffix();
  }
}
