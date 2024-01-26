package gov.cdc.nbs.patient.documentsrequiringreview.detail;

import gov.cdc.nbs.patient.documentsrequiringreview.DocumentRequiringReview;
import gov.cdc.nbs.provider.ProviderNameRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

class OrderingProviderRowMapper {
  private final ProviderNameRowMapper mapper;

  OrderingProviderRowMapper(final ProviderNameRowMapper.Column columns) {
    this.mapper = new ProviderNameRowMapper(columns);
  }

  Optional<DocumentRequiringReview.OrderingProvider> maybeMap(final ResultSet resultSet) throws SQLException {
    String name = mapper.map(resultSet);
    return name == null || name.isEmpty()
        ? Optional.empty()
        : Optional.of(new DocumentRequiringReview.OrderingProvider(name));
  }
}
