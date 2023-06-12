package gov.cdc.nbs.patientlistener;

import gov.cdc.nbs.authentication.EnableNBSAuthentication;
import gov.cdc.nbs.id.EnableNBSIdGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableNBSAuthentication
@EnableNBSIdGenerator
public class PatientListenerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PatientListenerApplication.class, args);
    }

}
