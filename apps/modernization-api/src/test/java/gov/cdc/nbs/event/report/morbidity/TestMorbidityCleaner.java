package gov.cdc.nbs.event.report.morbidity;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
class TestMorbidityCleaner {

  private final static String DELETE_IN = """
      delete from Participation where act_class_cd = 'OBS' and and act_uid in (:identifiers);
      delete from Observation where observation_uid in (:identifiers);
      delete from Act where class_cd = 'OBS' and act_uid in (:identifiers);
      """;

  private final JdbcClient client;

  TestMorbidityCleaner(final JdbcClient client) {
    this.client = client;
  }

  void clean(final Collection<Long> ids) {
    if(!ids.isEmpty()) {
      this.client.sql(DELETE_IN)
          .param("identifiers", ids)
          .update();
    }
  }


}
