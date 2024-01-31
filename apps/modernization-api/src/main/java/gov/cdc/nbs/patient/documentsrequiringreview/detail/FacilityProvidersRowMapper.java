package gov.cdc.nbs.patient.documentsrequiringreview.detail;

import gov.cdc.nbs.patient.documentsrequiringreview.DocumentRequiringReview;
import gov.cdc.nbs.provider.ProviderNameRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FacilityProvidersRowMapper {

  record Column(
      ReportingFacilityRowMapper.Column reporting,
      ProviderNameRowMapper.Column ordering,
      SendingFacilityRowMapper.Column sending
  ) {
  }

  private final ReportingFacilityRowMapper reportingFacilityRowMapper;
  private final OrderingProviderRowMapper orderingProviderRowMapper;
  private final SendingFacilityRowMapper sendingFacilityRowMapper;

  FacilityProvidersRowMapper(final Column column) {
    this.reportingFacilityRowMapper = column.reporting() == null
        ? null
        : new ReportingFacilityRowMapper(column.reporting());

    this.orderingProviderRowMapper = column.ordering() == null
        ? null
        : new OrderingProviderRowMapper(column.ordering());

    this.sendingFacilityRowMapper = column.sending() == null
        ? null
        : new SendingFacilityRowMapper(column.sending());
  }

  DocumentRequiringReview.FacilityProviders map(final ResultSet resultSet)
      throws SQLException {

    DocumentRequiringReview.ReportingFacility reportingFacility = this.reportingFacilityRowMapper != null
        ? this.reportingFacilityRowMapper.maybeMap(resultSet).orElse(null)
        : null;

    DocumentRequiringReview.SendingFacility sendingFacility = this.sendingFacilityRowMapper != null
        ? this.sendingFacilityRowMapper.maybeMap(resultSet).orElse(null)
        : null;

    DocumentRequiringReview.OrderingProvider orderingProvider = this.orderingProviderRowMapper != null
        ? this.orderingProviderRowMapper.maybeMap(resultSet).orElse(null)
        : null;

    return new DocumentRequiringReview.FacilityProviders(
        reportingFacility,
        orderingProvider,
        sendingFacility
    );
  }
}
