package gov.cdc.nbs.patient.demographics.address;

import gov.cdc.nbs.id.IdGeneratorService;
import gov.cdc.nbs.patient.demographic.AddressIdentifierGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class AddressIdentifierGeneratorConfiguration {

  @Bean
  AddressIdentifierGenerator addressIdentifierGenerator(final IdGeneratorService service) {
    return () -> service.getNextValidId(IdGeneratorService.EntityType.NBS).getId();
  }
}
