package gov.cdc.nbs.patient.documentsrequiringreview;

import org.elasticsearch.core.Map;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
class PatientActivityRequiringReviewFinder {
  private static final String QUERY = """
      select
          count([document].[nbs_document_uid]) over() as total,
          [document].nbs_document_uid         as [id],
          [document].add_time                 as [date_received],
          [document].add_time                 as [event_date],
          [document_type].code_short_desc_txt as [document_type],
          0                                   as [electronic],
          case
              when external_version_ctrl_nbr > 1
                  then 1
              else 0
          end                                 as [updated],
          [document].local_id                 as [event_id]
      from Person [patient]
              join Participation [participation] with (nolock) on
                  [participation].subject_class_cd = 'PSN'
              and [participation].record_status_cd = 'ACTIVE'
              and [participation].act_class_cd = 'DOC'
              and [participation].type_cd = 'SubjOfDoc'
              and [participation].subject_entity_uid = [patient].[person_uid]
            
              join nbs_document [document] with (nolock) on
                  [document].nbs_document_uid = [participation].act_uid
              and [document].record_status_cd = 'UNPROCESSED'
              and [document].program_jurisdiction_oid in (:documents)
            
          join NBS_SRTE..Code_value_general [document_type] on
                  [document_type].[code_set_nm] = 'PUBLIC_HEALTH_EVENT'
              and [document_type].code = [document].doc_type_cd
            
      where   [patient].person_parent_uid = :patient
          and [patient].cd = 'PAT'
          and [patient].record_status_cd = 'ACTIVE'
      union
      select
          count([observation].observation_uid) over() as total,
          [observation].observation_uid       as [id],
          [observation].add_time              as [date_received],
          coalesce(
              [observation].effective_from_time,
              [observation].[activity_to_time],
              [observation].add_time
          )                                   as [event_date],
          [observation].ctrl_cd_display_form  as [document_type],
          case [observation].electronic_ind
              when 'Y' then 1
              else 0
          end                                 as [electronic],
          0                                   as [updated],
          [observation].[local_id]            as [event_id]
      from Person [patient]
              join Participation [participation] with (nolock) on
                  [participation].subject_class_cd = 'PSN'
              and [participation].record_status_cd = 'ACTIVE'
              and [participation].act_class_cd = 'OBS'
              and [participation].type_cd = 'PATSBJ'
              and [participation].subject_entity_uid = [patient].[person_uid]
            
          join [Observation] [observation] on
                  [observation].observation_uid = [participation].act_uid
              and [observation].record_status_cd = 'UNPROCESSED'
              and [observation].program_jurisdiction_oid in (:labs)
            
      where   [patient].person_parent_uid = :patient
          and [patient].cd = 'PAT'
          and [patient].record_status_cd = 'ACTIVE'
      union
      select
          count([observation].observation_uid) over() as total,
          [observation].observation_uid       as [id],
          [observation].add_time              as [date_received],
          coalesce(
              [observation].effective_from_time,
              [observation].[activity_to_time],
              [observation].add_time
          )                                   as [event_date],
          [observation].ctrl_cd_display_form  as [document_type],
          case [observation].electronic_ind
              when 'Y' then 1
              else 0
          end                                 as [electronic],
          0                                   as [updated],
          [observation].[local_id]            as [event_id]
      from Person [patient]
              join Participation [participation] with (nolock) on
                  [participation].subject_class_cd = 'PSN'
              and [participation].record_status_cd = 'ACTIVE'
              and [participation].act_class_cd = 'OBS'
              and [participation].type_cd = 'SubjOfMorbReport'
              and [participation].subject_entity_uid = [patient].[person_uid]
            
          join [Observation] [observation] on
                  [observation].observation_uid = [participation].act_uid
              and [observation].record_status_cd = 'UNPROCESSED'
              and [observation].program_jurisdiction_oid in (:morbidities)
            
      where   [patient].person_parent_uid = :patient
          and [patient].cd = 'PAT'
          and [patient].record_status_cd = 'ACTIVE'
      """;
  private static final PatientActivityRequiringReviewRowMapper.Column DEFAULT_COLUMNS =
      new PatientActivityRequiringReviewRowMapper.Column(1, 2, 3);
  private static final List<Long> NOOP_SECURED_DATA = List.of(-1L);

  private final NamedParameterJdbcTemplate template;
  private final RowMapper<PatientActivityRequiringReview> mapper;

  PatientActivityRequiringReviewFinder(final NamedParameterJdbcTemplate template) {
    this.template = template;
    this.mapper = new PatientActivityRequiringReviewRowMapper(DEFAULT_COLUMNS);
  }

  List<PatientActivityRequiringReview> find(final DocumentsRequiringReviewCriteria criteria) {

    SqlParameterSource parameters = new MapSqlParameterSource(
        Map.of(
            "patient", criteria.patient(),
            "documents", ensured(criteria.documentScope().any()),
            "labs", ensured(criteria.labReportScope().any()),
            "morbidities", ensured(criteria.morbidityReportScope().any())
        )
    );

    return this.template.query(
        QUERY,
        parameters,
        this.mapper
    );
  }

  private Collection<Long> ensured(final Collection<Long> values) {
    return values.isEmpty() ? NOOP_SECURED_DATA : values;
  }
}
