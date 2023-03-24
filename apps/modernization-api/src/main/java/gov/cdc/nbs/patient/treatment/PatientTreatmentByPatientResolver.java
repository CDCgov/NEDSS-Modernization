package gov.cdc.nbs.patient.treatment;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.graphql.GraphQLPage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
class PatientTreatmentByPatientResolver {
  
  private final Integer maxPageSize;
  private final PatientTreatmentFinder finder;

  PatientTreatmentByPatientResolver(
      @Value("${nbs.max-page-size}") final int maxPageSize,
      final PatientTreatmentFinder finder
  ) {
    this.maxPageSize = maxPageSize;
    this.finder = finder;
  }

  @QueryMapping(name = "findTreatmentsForPatient")
  @PreAuthorize("hasAuthority('FIND-PATIENT') and hasAuthority('VIEW-TREATMENT')")
  Page<PatientTreatment> find(
      @Argument("patient") final long patient,
      @Argument final GraphQLPage page
  ) {
    Pageable pageable = GraphQLPage.toPageable(page, maxPageSize);
    return this.finder.find(
        patient,
        pageable
    );
  }

  @SchemaMapping("treatments")
  @PreAuthorize("hasAuthority('FIND-PATIENT') and hasAuthority('VIEW-TREATMENT')")
  List<PatientTreatment> resolve(final Person patient) {
    return this.finder.find(patient.getId());
  }
}
