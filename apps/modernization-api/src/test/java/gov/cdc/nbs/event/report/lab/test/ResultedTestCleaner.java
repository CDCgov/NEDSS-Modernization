package gov.cdc.nbs.event.report.lab.test;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
 class ResultedTestCleaner {

  private final static String DELETE_IN = """
      delete from Participation where act_class_cd = 'OBS' and and act_uid in (:identifiers);
      
      delete from Obs_value_coded where observation_uid in (:identifiers);
      
      delete from Observation 
      where   ctrl_cd_display_form = 'LabReport' 
          and obs_domain_cd_st_1 = 'Result'
          and observation_uid in (:identifiers);
      
      delete from Act_relationship
      where   source_act_uid in (:identifiers);
          and source_class_cd = 'OBS'
          and type_cd = 'COMP'
      
      delete from Act where class_cd = 'OBS' and act_uid in (:identifiers);
      """;

  private final JdbcClient client;

  ResultedTestCleaner(final JdbcClient client) {
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
