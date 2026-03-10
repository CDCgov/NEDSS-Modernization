package gov.cdc.nbs.event.report.lab.test;

import java.util.Optional;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

@Component
class LabTestParameterResolver {

  private static final int IDENTIFIER_COLUMN = 1;
  private static final String QUERY =
      """
      select top 1
        lab_test_cd
      from NBS_SRTE.[dbo].Lab_test
      where lab_test_desc_txt = ?
          and laboratory_id = 'DEFAULT'
      """;

  private final JdbcClient client;

  LabTestParameterResolver(final JdbcClient client) {
    this.client = client;
  }

  Optional<String> resolve(final String value) {

    return this.client
        .sql(QUERY)
        .param(value)
        .query((rs, row) -> rs.getString(IDENTIFIER_COLUMN))
        .optional();
  }
}
