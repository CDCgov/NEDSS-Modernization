package gov.cdc.nbs.patient.profile.summary;

import gov.cdc.nbs.patient.profile.PatientProfile;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.time.Clock;
import java.time.LocalDate;
import java.util.Optional;

@Controller
class PatientSummaryResolver {

    private final Clock clock;
    private final PatientSummaryFinder finder;

    PatientSummaryResolver(
        final Clock clock,
        final PatientSummaryFinder finder
    ) {
        this.clock = clock;
        this.finder = finder;
    }

    @SchemaMapping("summary")
    @PreAuthorize("hasAuthority('FIND-PATIENT')")
    Optional<PatientSummary> resolve(
        final PatientProfile profile,
        @Argument("asOf") final LocalDate asOf
    ) {
        return this.finder.find(profile.id(), ensureAsOf(asOf));
    }

    private LocalDate ensureAsOf(final LocalDate asOf) {
        return asOf == null
            ? LocalDate.now(clock)
            : asOf;
    }
}
