package gov.cdc.nbs.patient.profile;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
class PatientProfileResolver {

    private final PatientProfileFinder finder;

    PatientProfileResolver(final PatientProfileFinder finder) {
        this.finder = finder;
    }

    @QueryMapping("findPatientProfile")
    Optional<PatientProfile> find(@Argument("patient") final long patient) {
        return this.finder.find(patient);
    }


}
