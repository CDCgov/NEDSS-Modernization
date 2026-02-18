package gov.cdc.nbs.patient.events.investigation.association;

import com.google.common.collect.Multimap;
import gov.cdc.nbs.authorization.permission.scope.PermissionScope;
import gov.cdc.nbs.sql.MultiMapResultSetExtractor;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

@Component
public class AssociatedInvestigationFinder {

  private static final String QUERY =
      """
      select
          [source].source_act_uid                     as [source],
          [associated].public_health_case_uid         as [identifier],
          [associated].local_id                       as [local],
          [condition].condition_short_nm              as [condition],
          [follow_up_status].code_desc_txt            as [status]
      from act_relationship [source] with (nolock)

          join Public_health_case [associated] with (nolock) on
                  [associated].Public_health_case_uid = [source].target_act_uid
              and [associated].record_status_cd <> 'LOG_DEL'
              and [associated].program_jurisdiction_oid in (:any)

          join nbs_srte..Condition_code [condition] with (nolock) on
                  [condition].condition_cd = [associated].cd

          left join case_management [management] with (nolock) on
                  [management].public_health_case_uid = [associated].public_health_case_uid

          left join NBS_SRTE..Code_value_general [follow_up_status] with (nolock) on
                  [follow_up_status].code = [management].init_foll_up
              and [follow_up_status].code_set_nm = 'STD_NBS_PROCESSING_DECISION_ALL'

      where   [source].target_class_cd = 'CASE'
          and [source].source_act_uid in (:sources)
      order by
          [associated].local_id desc
      """;

  private final JdbcClient client;
  private final ResultSetExtractor<Multimap<Long, AssociatedInvestigation>> extractor;

  public AssociatedInvestigationFinder(final JdbcClient client) {
    this.client = client;
    this.extractor =
        new MultiMapResultSetExtractor<>(
            (rs, rowNum) -> rs.getLong(1),
            new AssociatedInvestigationRowMapper(
                new AssociatedInvestigationRowMapper.Column(2, 3, 4, 5)));
  }

  public Map<Long, Collection<AssociatedInvestigation>> find(
      final Collection<Long> sources, final PermissionScope scope) {
    return !scope.allowed() || sources.isEmpty() ? Collections.emptyMap() : execute(sources, scope);
  }

  Map<Long, Collection<AssociatedInvestigation>> execute(
      final Collection<Long> sources, final PermissionScope scope) {
    return this.client
        .sql(QUERY)
        .param("sources", sources)
        .param("any", scope.any())
        .query(extractor)
        .asMap();
  }
}
