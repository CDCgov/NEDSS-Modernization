package gov.cdc.nbs.patient.document;

import gov.cdc.nbs.entity.odse.Person;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
class PatientDocumentByPatientResolver {

  private final PatientDocumentFinder finder;

  PatientDocumentByPatientResolver(final PatientDocumentFinder finder) {
    this.finder = finder;
  }

  @QueryMapping(name = "findDocumentsForPatient")
  @PreAuthorize("hasAuthority('FIND-PATIENT') and hasAuthority('VIEW-DOCUMENT')")
  List<PatientDocument> find(@Argument("patient") final long patient) {
    return this.finder.find(patient);
  }

  @SchemaMapping("documents")
  @PreAuthorize("hasAuthority('FIND-PATIENT') and hasAuthority('VIEW-DOCUMENT')")
  List<PatientDocument> resolve(final Person patient) {
    return this.finder.find(patient.getId());
  }
}
