package gov.cdc.nbs.patient.profile.address.change;

import gov.cdc.nbs.support.Active;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//  Sometimes Spring gets confuses with autowired generics
@Configuration
class PatientProfileAddressChangeConfiguration {

    @Bean
    Active<NewPatientAddressInput> activeNewPatientAddressInput() {
        return new Active<>();
    }

    @Bean
    Active<UpdatePatientAddressInput> activeUpdatePatientAddressInput() {
        return new Active<>();
    }

    @Bean
    Active<DeletePatientAddressInput> activeDeletePatientAddressInput() {
        return new Active<>();
    }
}
