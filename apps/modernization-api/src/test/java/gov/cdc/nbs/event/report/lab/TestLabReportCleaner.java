package gov.cdc.nbs.event.report.lab;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
class TestLabReportCleaner {

  private static final String DELETE_IN = """
      delete from Participation where act_class_cd = 'OBS' and act_uid in (:identifiers);
      
      delete from Observation 
      where   ctrl_cd_display_form = 'LabReport' 
          and obs_domain_cd_st_1 = 'Order'
          and observation_uid in (:identifiers);
      
      delete from Act where class_cd = 'OBS' and act_uid in (:identifiers);
      """;

  private final JdbcClient client;

  TestLabReportCleaner(final JdbcClient client) {
    this.client = client;
  }

  void clean(final Collection<Long> identifiers) {
    if (!identifiers.isEmpty()) {
      this.client.sql(DELETE_IN)
          .param("identifiers", identifiers)
          .update();
    }
  }
}
