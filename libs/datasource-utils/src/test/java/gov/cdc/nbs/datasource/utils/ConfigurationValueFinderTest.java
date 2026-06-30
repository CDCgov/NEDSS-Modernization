package gov.cdc.nbs.datasource.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.dao.TypeMismatchDataAccessException;
import org.springframework.jdbc.core.simple.JdbcClient;

@ExtendWith(MockitoExtension.class)
class ConfigurationValueFinderTest {

  // The RETURNS_DEEP_STUBS flag instructs Mockito to automatically mock
  // every intermediate step in the fluent chain (.sql().param().query().single())
  @Mock(answer = Answers.RETURNS_DEEP_STUBS)
  private JdbcClient jdbcClient;

  @InjectMocks private ConfigurationValueFinder finder;

  @Test
  void should_return_config_value_when_found() {
    when(jdbcClient.sql(anyString()).param(anyString()).query(String.class).single())
        .thenReturn("NBS_ODSE");

    String result = finder.getConfigValue("nbs_ods");

    assertThat(result).isEqualTo("NBS_ODSE");
  }

  @Test
  void should_return_empty_string_when_key_does_not_exist() {
    // Given the client chain throws an empty data exception (simulating no row found)
    when(jdbcClient.sql(anyString()).param(anyString()).query(String.class).single())
        .thenThrow(new EmptyResultDataAccessException(1));

    String result = finder.getConfigValue("missing_key");

    assertThat(result).isEmpty();
  }

  @Test
  void should_return_empty_string_when_duplicate_keys_exist() {
    // Given the unique key constraint is broken in the DB and returns multiple configuration rows
    when(jdbcClient.sql(anyString()).param(anyString()).query(String.class).single())
        .thenThrow(
            new IncorrectResultSizeDataAccessException("Expected 1 row but found multiple", 1, 2));

    String result = finder.getConfigValue("duplicate_key");

    assertThat(result).isEmpty();
  }

  @Test
  void should_return_empty_string_when_value_and_default_are_null() {
    // Given a row is found but both target columns resolve to null, triggering a data type
    // conversion fault
    when(jdbcClient.sql(anyString()).param(anyString()).query(String.class).single())
        .thenThrow(
            new TypeMismatchDataAccessException(
                "Result payload resolved to completely null types"));

    String result = finder.getConfigValue("null_value_key");

    assertThat(result).isEmpty();
  }
}
