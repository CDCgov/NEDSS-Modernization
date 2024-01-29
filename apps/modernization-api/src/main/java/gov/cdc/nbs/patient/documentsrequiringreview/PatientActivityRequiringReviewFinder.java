package gov.cdc.nbs.patient.documentsrequiringreview;

import org.elasticsearch.core.Map;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
class PatientActivityRequiringReviewFinder {
  private static final String QUERY = """   
      with patient (person_uid) as (
          select
              [patient].[person_uid]
          from  Person [patient]\s
          where   [patient].person_parent_uid = :patient
          and [patient].cd = 'PAT'
          and [patient].record_status_cd = 'ACTIVE'
      )  
      select
          count([document].[nbs_document_uid]) over() as total,
          [document].nbs_document_uid         as [id],
          [document].doc_type_cd              as [type]
      from patient
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
      union
      select
          count([observation].observation_uid) over() as total,
          [observation].observation_uid       as [id],
          [observation].ctrl_cd_display_form  as [type]
      from patient
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
      union
      select
          count([observation].observation_uid) over() as total,
          [observation].observation_uid       as [id],
          [observation].ctrl_cd_display_form  as [type]
      from patient
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
      order by id
      offset :offset rows
      fetch next :pageSize rows only
      """;
  private static final PatientActivityRequiringReviewResultSetHandler.Column DEFAULT_COLUMNS =
      new PatientActivityRequiringReviewResultSetHandler.Column(1, 2, 3);
  private static final List<Long> NOOP_SECURED_DATA = List.of(-1L);

  private final NamedParameterJdbcTemplate template;

  PatientActivityRequiringReviewFinder(final NamedParameterJdbcTemplate template) {
    this.template = template;
  }

  PatientActivity find(final DocumentsRequiringReviewCriteria criteria, final Pageable pageable) {

    SqlParameterSource parameters = new MapSqlParameterSource(
        Map.of(
            "patient", criteria.patient(),
            "documents", ensured(criteria.documentScope().any()),
            "labs", ensured(criteria.labReportScope().any()),
            "morbidities", ensured(criteria.morbidityReportScope().any()),
            "offset", pageable.getOffset(),
            "pageSize", pageable.getPageSize()
        )
    );

    PatientActivityRequiringReviewResultSetHandler handler =
        new PatientActivityRequiringReviewResultSetHandler(DEFAULT_COLUMNS);
    this.template.query(
        QUERY,
        parameters,
        handler
    );

    return handler.activity();
  }

  private Collection<Long> ensured(final Collection<Long> values) {
    return values.isEmpty() ? NOOP_SECURED_DATA : values;
  }
}
