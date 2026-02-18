package gov.cdc.nbs.patient.events.report.morbidity;

import com.google.common.collect.Multimap;
import gov.cdc.nbs.sql.MultiMapResultSetExtractor;
import java.util.Collection;
import java.util.Map;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

@Component
public class MorbidityReportTreatmentFinder {

  private static final String QUERY =
      """
      select
          [treated_with_morbidity].target_act_uid as [morbidity],
          case [therapy].cd
              when 'OTH' then [therapy].cd_desc_txt
                  else [treatment].treatment_desc_txt
          end                                     as [treatment]
      from Act_relationship [treated_with_morbidity] with (nolock)

          join Participation [treated] with (nolock) on
                  [treated].act_uid = [treated_with_morbidity].source_act_uid
              and [treated].act_class_cd = 'TRMT'
              and [treated].type_cd = 'SubjOfTrmt'
              and [treated].record_status_cd = 'ACTIVE'

          left join Treatment [therapy] with (nolock) on
                  [therapy].treatment_uid = [treated].act_uid
              and [therapy].record_status_cd = 'ACTIVE'

          left join Treatment_administered [administered] with (nolock) on
                  [administered].treatment_uid = [therapy].treatment_uid

          left join [NBS_SRTE]..Treatment_code [treatment] with (nolock) on
                  [treatment].treatment_cd = [therapy].cd

      where [treated_with_morbidity].target_act_uid in (:identifiers)
          and [treated_with_morbidity].type_cd = 'TreatmentToMorb'
          and [treated_with_morbidity].record_status_cd = 'ACTIVE'
      order by
          [therapy].activity_to_time
      """;

  public static final int MORBIDITY_COLUMN = 1;
  public static final int TREATMENT_COLUMN = 2;

  private final JdbcClient client;
  private final ResultSetExtractor<Multimap<Long, String>> extractor;

  public MorbidityReportTreatmentFinder(final JdbcClient client) {
    this.client = client;
    this.extractor =
        new MultiMapResultSetExtractor<>(
            (rs, rowNum) -> rs.getLong(MORBIDITY_COLUMN),
            (rs, rowNum) -> rs.getString(TREATMENT_COLUMN));
  }

  public Map<Long, Collection<String>> find(final Collection<Long> identifiers) {

    if (identifiers.isEmpty()) {
      return Map.of();
    }

    return this.client.sql(QUERY).param("identifiers", identifiers).query(extractor).asMap();
  }
}
