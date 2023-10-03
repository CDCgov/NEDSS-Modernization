package gov.cdc.nbs.patient.profile.summary.race;

import gov.cdc.nbs.patient.profile.summary.PatientSummary;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.Collection;

@Controller
class PatientSummaryRaceResolver {

  private final PatientSummaryRaceFinder finder;

  PatientSummaryRaceResolver(final PatientSummaryRaceFinder finder) {
    this.finder = finder;
  }

  @SchemaMapping(typeName = "PatientSummary", value = "races")
  @PreAuthorize("hasAuthority('FIND-PATIENT')")
  Collection<String> resolve(final PatientSummary summary) {
    return this.finder.find(summary.patient());
  }

}
