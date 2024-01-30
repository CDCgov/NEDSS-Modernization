package gov.cdc.nbs.patient.documentsrequiringreview.detail;

import gov.cdc.nbs.patient.documentsrequiringreview.DocumentRequiringReview;
import gov.cdc.nbs.patient.documentsrequiringreview.PatientActivityRequiringReview;
import gov.cdc.nbs.provider.ProviderNameRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;

@Component
class MorbidityReportDetailFinder {


  private static final FacilityProvidersRowMapper.Column FACILITY_COLUMNS = new FacilityProvidersRowMapper.Column(
      new ReportingFacilityRowMapper.Column(4),
      new ProviderNameRowMapper.Column(5, 6, 7, 8),
      null
  );
  private static final MorbidityReportDetailRowMapper.Column DEFAULT_COLUMNS =
      new MorbidityReportDetailRowMapper.Column(1, 2, 3, FACILITY_COLUMNS, 9, 10, 11);

  private static final String QUERY = """
      select
        [morbidity].[observation_uid]                       as [id],
        [morbidity].[add_time]                              as [received_on],
            coalesce(
            [morbidity].effective_from_time,
            [morbidity].effective_to_time,
            [morbidity].add_time
        )                                                   as [event_date],
        [reporting_facility].display_nm                     as [reporting_facility],
        [ordering_provider].[nm_prefix]                     as [ordering_provider_prefix],
        [ordering_provider].[first_nm]                      as [ordering_provider_first_name],
        [ordering_provider].[last_nm]                       as [ordering_provider_last_name],
        [ordering_provider].[nm_suffix]                     as [ordering_provider_suffix],
        [condition].condition_short_nm                      as [condition],
        case [morbidity].electronic_ind
            when 'Y' then 1
            else 0
        end                                                 as [electronic],
        [morbidity].[local_id]                              as [event]
      from [Observation] [morbidity]
        
          join [Participation] [subject_of_report] on
                [subject_of_report].type_cd='SubjOfMorbReport'
            and [subject_of_report].act_class_cd = 'OBS'
            and [subject_of_report].subject_class_cd = 'PSN'
            and [subject_of_report].record_status_cd = 'ACTIVE'
            and [subject_of_report].act_uid = [morbidity].observation_uid
            
          join Participation [reporting_facility_participation] on
                [reporting_facility_participation].act_uid = [morbidity].observation_uid
            and [reporting_facility_participation].type_cd = 'ReporterOfMorbReport'
            and [reporting_facility_participation].subject_class_cd = 'ORG'
          
          join Organization [reporting_facility] on
                [reporting_facility].organization_uid = [reporting_facility_participation].[subject_entity_uid]
          
          left join Participation [ordering_provider_participation] on
                [ordering_provider_participation].act_uid = [morbidity].observation_uid
            and [ordering_provider_participation].type_cd = 'PhysicianOfMorb'
            and [ordering_provider_participation].subject_class_cd = 'PSN'
          
          left join person_name [ordering_provider] on
                [ordering_provider].person_uid = [ordering_provider_participation].[subject_entity_uid]
          
          left join nbs_srte..Condition_code [condition] ON
                [condition].condition_cd = [morbidity].cd
                        
      where [morbidity].observation_uid in (:identifiers)
        """;

  private final NamedParameterJdbcTemplate template;
  private final MorbidityReportDetailRowMapper mapper;

  public MorbidityReportDetailFinder(final NamedParameterJdbcTemplate template) {
    this.template = template;
    this.mapper = new MorbidityReportDetailRowMapper(DEFAULT_COLUMNS);
  }

  Collection<DocumentRequiringReview> find(final PatientActivityRequiringReview.MorbidityReport reports) {
    return reports.identifiers().isEmpty()
        ? Collections.emptyList()
        : findAll(reports.identifiers());
  }

  private Collection<DocumentRequiringReview> findAll(final Collection<Long> identifiers) {
    SqlParameterSource parameters = new MapSqlParameterSource(
        "identifiers", identifiers
    );

    return this.template.query(QUERY, parameters, this.mapper);
  }
}
