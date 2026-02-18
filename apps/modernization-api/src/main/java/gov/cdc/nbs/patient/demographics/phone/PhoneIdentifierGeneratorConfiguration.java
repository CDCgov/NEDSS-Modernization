package gov.cdc.nbs.patient.demographics.phone;

import gov.cdc.nbs.id.IdGeneratorService;
import gov.cdc.nbs.patient.demographic.phone.PhoneIdentifierGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class PhoneIdentifierGeneratorConfiguration {

  @Bean
  PhoneIdentifierGenerator phoneIdentifierGenerator(final IdGeneratorService service) {
    return () -> service.getNextValidId(IdGeneratorService.EntityType.NBS).getId();
  }
}
