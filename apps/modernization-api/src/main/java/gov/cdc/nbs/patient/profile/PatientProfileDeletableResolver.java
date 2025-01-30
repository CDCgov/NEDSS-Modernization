package gov.cdc.nbs.patient.profile;

import gov.cdc.nbs.patient.PatientAssociationCountFinder;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import static gov.cdc.nbs.patient.profile.PatientProfile.STATUS_INACTIVE;

@Controller
class PatientProfileDeletableResolver {

  private final PatientAssociationCountFinder finder;

  PatientProfileDeletableResolver(final PatientAssociationCountFinder finder) {
    this.finder = finder;
  }

  @SchemaMapping("deletable")
  boolean resolve(final PatientProfile profile) {
    return !profile.status().equals(STATUS_INACTIVE) && finder.count(profile.id()) == 0;
  }
}
