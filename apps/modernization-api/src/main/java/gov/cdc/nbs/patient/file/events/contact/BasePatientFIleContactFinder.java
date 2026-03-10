package gov.cdc.nbs.patient.file.events.contact;

import com.google.common.collect.Multimap;
import gov.cdc.nbs.authorization.permission.scope.PermissionScope;
import gov.cdc.nbs.patient.events.investigation.association.AssociatedInvestigationRowMapper;
import gov.cdc.nbs.sql.MultiMapResultSetExtractor;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.simple.JdbcClient;

class BasePatientFIleContactFinder implements PatientFileContactFinder {

  private final String query;
  private final JdbcClient client;
  private final ResultSetExtractor<Multimap<String, PatientFileContacts.PatientFileContact>>
      extractor;

  BasePatientFIleContactFinder(final String query, final JdbcClient client) {
    this.query = query;
    this.client = client;
    this.extractor =
        new MultiMapResultSetExtractor<>(
            (rs, rowNum) -> rs.getString(1),
            new PatientFileContactRowMapper(
                new PatientFileContactRowMapper.Column(
                    2,
                    3,
                    4,
                    5,
                    6,
                    7,
                    8,
                    9,
                    new NamedContactRowMapper.Columns(10, 11, 12, 13, 14),
                    15,
                    16,
                    new AssociatedInvestigationRowMapper.Column(17, 18, 19, 20))));
  }

  @Override
  public List<PatientFileContacts> find(
      final long patient, PermissionScope scope, final PermissionScope associatedScope) {
    if (scope.allowed()) {
      return this.client
          .sql(query)
          .param("patient", patient)
          .param("any", scope.any())
          .param("associated", resolveAssociated(associatedScope))
          .query(extractor)
          .asMap()
          .entrySet()
          .stream()
          .map(e -> new PatientFileContacts(e.getKey(), e.getValue()))
          .toList();
    }

    return Collections.emptyList();
  }

  private Collection<Long> resolveAssociated(final PermissionScope scope) {
    Collection<Long> any = scope.any();
    return any.isEmpty() ? List.of(Long.MIN_VALUE) : any;
  }
}
