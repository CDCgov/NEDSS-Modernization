package gov.cdc.nbs.patient.documentsrequiringreview.detail;

import gov.cdc.nbs.patient.documentsrequiringreview.DocumentRequiringReview;
import gov.cdc.nbs.provider.ProviderNameRowMapper;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OrderingProviderRowMapperTest {

  private final ProviderNameRowMapper.Column columns = new ProviderNameRowMapper.Column(41, 43, 47, 53);

  @Test
  void should_map_when_provider_preset() throws SQLException {
    OrderingProviderRowMapper mapper = new OrderingProviderRowMapper(columns);

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getString(columns.prefix())).thenReturn("prefix");
    when(resultSet.getString(columns.first())).thenReturn("first-name");
    when(resultSet.getString(columns.last())).thenReturn("last-name");
    when(resultSet.getString(columns.suffix())).thenReturn("suffix");

    Optional<DocumentRequiringReview.OrderingProvider> mapped = mapper.maybeMap(resultSet);

    assertThat(mapped).hasValueSatisfying(
        actual -> assertThat(actual.name()).isEqualTo("prefix first-name last-name suffix")
    );
  }

  @Test
  void should_not_map_when_provider_not_present() throws SQLException {
    OrderingProviderRowMapper mapper = new OrderingProviderRowMapper(columns);

    ResultSet resultSet = mock(ResultSet.class);

    Optional<DocumentRequiringReview.OrderingProvider> mapped = mapper.maybeMap(resultSet);

    assertThat(mapped).isEmpty();
  }

}
