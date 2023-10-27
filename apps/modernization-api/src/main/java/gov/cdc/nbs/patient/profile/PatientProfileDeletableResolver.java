package gov.cdc.nbs.patient.profile;

import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.patient.PatientAssociationCountFinder;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
class PatientProfileDeletableResolver {

  private final PatientAssociationCountFinder finder;

  PatientProfileDeletableResolver(final PatientAssociationCountFinder finder) {
    this.finder = finder;
  }

  @SchemaMapping("deletable")
  boolean resolve(final PatientProfile profile) {
    return !profile.status().equals(RecordStatus.LOG_DEL.display()) && finder.count(profile.id()) == 0;
  }
}
