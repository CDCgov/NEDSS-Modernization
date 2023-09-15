package gov.cdc.nbs.patient.profile.address.change;

import gov.cdc.nbs.support.TestActive;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//  Sometimes Spring gets confuses with autowired generics
@Configuration
class PatientProfileAddressChangeConfiguration {

    @Bean
    TestActive<NewPatientAddressInput> activeNewPatientAddressInput() {
        return new TestActive<>();
    }

    @Bean
    TestActive<UpdatePatientAddressInput> activeUpdatePatientAddressInput() {
        return new TestActive<>();
    }

    @Bean
    TestActive<DeletePatientAddressInput> activeDeletePatientAddressInput() {
        return new TestActive<>();
    }
}
