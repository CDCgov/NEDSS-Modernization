package gov.cdc.nbs.patient.file.summary.drr.morbidity;

import com.google.common.collect.Multimap;
import gov.cdc.nbs.authorization.permission.scope.PermissionScope;
import gov.cdc.nbs.sql.MultiMapResultSetExtractor;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;

@Component
class MorbidityReportTreatmentFinder {

  private static final String QUERY = """
      with revisions (person_uid) as (
                select
                    [patient].[person_uid]
                from  Person [patient] with (nolock)
                where   [patient].person_parent_uid = :patient
                    and [patient].person_parent_uid <> [patient].person_uid
                    and [patient].cd = 'PAT'
                    and [patient].record_status_cd = 'ACTIVE'
            )
      select
          [treated_with_morbidity].target_act_uid as [morbidity],
          case [therapy].cd
              when 'OTH' then [therapy].cd_desc_txt
                  else [treatment].treatment_desc_txt
          end                                     as [treatment]
      from revisions
      
          join Participation [treated] with (nolock) on
                  [treated].subject_entity_uid = revisions.person_uid
              and [treated].type_cd = 'SubjOfTrmt'
              and [treated].[act_class_cd] = 'TRMT'
              and [treated].record_status_cd = 'ACTIVE'
      
          join Treatment [therapy] with (nolock) on
                  [therapy].treatment_uid = [treated].act_uid
              and [therapy].record_status_cd = 'ACTIVE'
      
          join Treatment_administered [administered] with (nolock) on
                  [administered].treatment_uid = [therapy].treatment_uid
      
          join [NBS_SRTE]..Treatment_code [treatment] with (nolock) on
                  [treatment].treatment_cd = [therapy].cd
      
          join Act_relationship [treated_with_morbidity] with (nolock) on
                  [treated_with_morbidity].type_cd = 'TreatmentToMorb'
              and [treated_with_morbidity].source_act_uid = [therapy].treatment_uid
              and [treated_with_morbidity].record_status_cd = 'ACTIVE'
      
          join Observation [morbidity] with (nolock) on
                  [morbidity].[observation_uid] = [treated_with_morbidity].target_act_uid
              and [morbidity].ctrl_cd_display_form = 'MorbReport'
              and [morbidity].obs_domain_cd_st_1 = 'Order'
              and [morbidity].record_status_cd = 'UNPROCESSED'
              and [morbidity].program_jurisdiction_oid in (:any)
      """;
  public static final int MORBIDITY_COLUMN = 1;
  public static final int TREATMENT_COLUMN = 2;

  private final JdbcClient client;
  private final ResultSetExtractor<Multimap<Long, String>> extractor;

  MorbidityReportTreatmentFinder(final JdbcClient client) {
    this.client = client;
    this.extractor = new MultiMapResultSetExtractor<>(
        (rs, rowNum) -> rs.getLong(MORBIDITY_COLUMN),
        (rs, rowNum) -> rs.getString(TREATMENT_COLUMN)
    );
  }

  Map<Long, Collection<String>> find(
      final long patient,
      final PermissionScope scope
  ) {
    return this.client.sql(QUERY)
        .param("patient", patient)
        .param("any", scope.any())
        .query(extractor)
        .asMap();
  }

}
