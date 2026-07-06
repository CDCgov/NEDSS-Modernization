package gov.cdc.nbs.datasource.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
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

  // =========================================================================
  // CACHING & NORMALIZATION TESTS
  // =========================================================================

  @Test
  void should_cache_value_and_only_query_database_once_on_subsequent_calls() {
    // Configure database to return a value on the initial lookup
    when(jdbcClient.sql(anyString()).param(anyString()).query(String.class).single())
        .thenReturn("10000");

    // Clear the setup invocation count so the counter starts clean
    clearInvocations(jdbcClient);

    // Invoke finder twice for the same config key
    String firstCallResult = finder.getConfigValue("REPORT_MAX_ROW_LIMIT_RUN");
    String secondCallResult = finder.getConfigValue("REPORT_MAX_ROW_LIMIT_RUN");

    // Both streams resolve to the correct value
    assertThat(firstCallResult).isEqualTo("10000");
    assertThat(secondCallResult).isEqualTo("10000");

    // Assert the underlying SQL client was executed exactly ONE time.
    verify(jdbcClient, times(1)).sql(anyString());
  }

  @Test
  void should_normalize_key_case_and_successfully_leverage_cache() {
    when(jdbcClient.sql(anyString()).param(anyString()).query(String.class).single())
        .thenReturn("RDB");

    // Request values using alternate casings across call boundaries
    String lowerCaseResult = finder.getConfigValue("report_db_rdb");
    String upperCaseResult = finder.getConfigValue("REPORT_DB_RDB");

    assertThat(lowerCaseResult).isEqualTo("RDB");
    assertThat(upperCaseResult).isEqualTo("RDB");
  }

  @Test
  void should_evict_cache_and_query_database_again_after_clear_cache_is_triggered() {
    when(jdbcClient.sql(anyString()).param(anyString()).query(String.class).single())
        .thenReturn("NBS_SRTE");

    // Clear the setup invocation count so the counter starts clean
    clearInvocations(jdbcClient);

    // First call populates cache (DB hit count = 1)
    finder.getConfigValue("REPORT_DB_NBS_SRT");

    // Trigger cache clear execution context
    finder.clearCache();

    // Next call must bypass memory and evaluate against database rules again (DB hit count = 2)
    finder.getConfigValue("REPORT_DB_NBS_SRT");

    verify(jdbcClient, times(2)).sql(anyString());
  }

  @Test
  void should_return_empty_string_immediately_without_db_interaction_when_key_is_null_or_blank() {
    // Guard parameters catch garbage input patterns
    assertThat(finder.getConfigValue(null)).isEmpty();
    assertThat(finder.getConfigValue("")).isEmpty();
    assertThat(finder.getConfigValue("    ")).isEmpty();

    // Verify the client was never called
    verifyNoInteractions(jdbcClient);
  }

  // =========================================================================
  // ANOMALY EXCEPTION HANDLING TESTS
  // =========================================================================

  @Test
  void should_return_empty_string_when_key_does_not_exist() {
    when(jdbcClient.sql(anyString()).param(anyString()).query(String.class).single())
        .thenThrow(new EmptyResultDataAccessException(1));

    String result = finder.getConfigValue("missing_key");

    assertThat(result).isEmpty();
  }

  @Test
  void should_return_empty_string_when_duplicate_keys_exist() {
    when(jdbcClient.sql(anyString()).param(anyString()).query(String.class).single())
        .thenThrow(
            new IncorrectResultSizeDataAccessException("Expected 1 row but found multiple", 1, 2));

    String result = finder.getConfigValue("duplicate_key");

    assertThat(result).isEmpty();
  }

  @Test
  void should_return_empty_string_when_value_and_default_are_null() {
    when(jdbcClient.sql(anyString()).param(anyString()).query(String.class).single())
        .thenThrow(
            new TypeMismatchDataAccessException(
                "Result payload resolved to completely null types"));

    String result = finder.getConfigValue("null_value_key");

    assertThat(result).isEmpty();
  }

  @Test
  void should_successfully_prime_all_configured_keys() {
    when(jdbcClient.sql(anyString()).param(anyString()).query(String.class).single())
        .thenReturn("MOCKED_VALUE");

    clearInvocations(jdbcClient);

    org.junit.jupiter.api.Assertions.assertDoesNotThrow(() -> finder.loadConfigurations());

    // Verifies that the internal loop hit the database client 8 distinct times (one for each key)
    verify(jdbcClient, times(4)).sql(anyString());
  }

  @Test
  void should_throw_exception_during_priming_if_a_key_is_missing() {
    // Simulate a missing database row by throwing an exception on lookup
    when(jdbcClient.sql(anyString()).param(anyString()).query(String.class).single())
        .thenThrow(new EmptyResultDataAccessException(1));

    // Verify it throws the expected critical startup exception
    org.junit.jupiter.api.Assertions.assertThrows(
        IllegalStateException.class, () -> finder.loadConfigurations());
  }
}
