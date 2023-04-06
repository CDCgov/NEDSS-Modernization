package gov.cdc.nbs.patient.profile;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.time.Clock;
import java.time.Instant;
import java.util.Optional;

@Controller
class PatientProfileResolver {

    private final Clock clock;
    private final PatientProfileFinder finder;

    PatientProfileResolver(
        final Clock clock,
        final PatientProfileFinder finder
    ) {
        this.clock = clock;
        this.finder = finder;
    }

    @QueryMapping("findPatientProfile")
    Optional<PatientProfile> find(
        @Argument("patient") final long patient,
        @Argument("asOf") final Instant asOf
    ) {
        return this.finder.find(patient, ensureAsOf(asOf));
    }

    private Instant ensureAsOf(final Instant asOf) {
        return asOf == null
            ? Instant.now(clock)
            : asOf;
    }
}
