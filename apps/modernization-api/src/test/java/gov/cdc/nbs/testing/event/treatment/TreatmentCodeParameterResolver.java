package gov.cdc.nbs.testing.event.treatment;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class TreatmentCodeParameterResolver {

  private static final String QUERY = """
      select
          treatment_cd
      from NBS_SRTE..Treatment_code
      where treatment_desc_txt = ?
      """;

  private final JdbcClient client;

  TreatmentCodeParameterResolver(final JdbcClient client) {
    this.client = client;
  }

  Optional<String> resolve(final String value) {
    return this.client.sql(QUERY)
        .param(value)
        .query(String.class)
        .optional();
  }


}
