package gov.cdc.nbs.patient.events.report.morbidity;

import com.google.common.collect.Multimap;
import gov.cdc.nbs.sql.MultiMapResultSetExtractor;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
class AssociatedLabReportFinder {

  private static final String QUERY = """
      select
          [relationship].target_act_uid   as [morbidity],
          [relationship].source_act_uid   as [lab_report]
      from Act_relationship [relationship]\s
      
      where   [relationship].target_act_uid in (:identifiers)
          and [relationship].target_class_cd = 'OBS'
          and [relationship].source_class_cd = 'OBS'
          and [relationship].[type_cd] = 'LabReport'
      """;
  public static final int MORBIDITY_COLUMN = 1;
  public static final int LABORATORY_REPORT_COLUMN = 2;

  private final JdbcClient client;
  private final ResultSetExtractor<Multimap<Long, Long>> extractor;

  AssociatedLabReportFinder(final JdbcClient client) {
    this.client = client;
    this.extractor = new MultiMapResultSetExtractor<>(
        (rs, rowNum) -> rs.getLong(MORBIDITY_COLUMN),
        (rs, rowNum) -> rs.getLong(LABORATORY_REPORT_COLUMN)
    );
  }

  Multimap<Long, Long> find(final Collection<Long> identifiers) {
    return this.client.sql(QUERY)
        .param("identifiers", identifiers)
        .query(extractor);
  }
}
