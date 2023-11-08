package gov.cdc.nbs.patient.document;

import gov.cdc.nbs.authorization.permission.Permission;
import gov.cdc.nbs.authorization.permission.scope.PermissionScope;
import gov.cdc.nbs.authorization.permission.scope.PermissionScopeResolver;
import gov.cdc.nbs.graphql.GraphQLPage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@Controller
class PatientDocumentByPatientResolver {

  private static final Permission PERMISSION = new Permission("View", "Document");

  private final PermissionScopeResolver resolver;
  private final int maxPageSize;
  private final PatientDocumentFinder finder;

  PatientDocumentByPatientResolver(
      final PermissionScopeResolver resolver,
      @Value("${nbs.max-page-size}") final int maxPageSize,
      final PatientDocumentFinder finder
  ) {
    this.resolver = resolver;
    this.maxPageSize = maxPageSize;
    this.finder = finder;
  }

  @QueryMapping(name = "findDocumentsForPatient")
  @PreAuthorize("hasAuthority('FIND-PATIENT') and hasAuthority('VIEW-DOCUMENT')")
  Page<PatientDocument> find(
      @Argument("patient") final long patient,
      @Argument final GraphQLPage page
  ) {
    PermissionScope scope = resolver.resolve(PERMISSION);
    Pageable pageable = GraphQLPage.toPageable(page, maxPageSize);
    return this.finder.find(
        patient,
        scope,
        pageable
    );
  }

}
