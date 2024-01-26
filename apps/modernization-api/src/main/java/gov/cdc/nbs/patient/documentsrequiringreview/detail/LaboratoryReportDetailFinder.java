package gov.cdc.nbs.patient.documentsrequiringreview.detail;

import gov.cdc.nbs.accumulation.Accumulator;
import gov.cdc.nbs.patient.documentsrequiringreview.DocumentRequiringReview;
import gov.cdc.nbs.patient.documentsrequiringreview.PatientActivity;
import gov.cdc.nbs.provider.ProviderNameRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;

@Component
class LaboratoryReportDetailFinder {

  private static final String QUERY = """
      select
          [lab].[observation_uid]                             as [id],
          [lab].[add_time]                                    as [received_on],
          coalesce(
              [lab].effective_from_time,
              [lab].effective_to_time
          )                                                   as [event_date],
          [reporting_facility].display_nm                     as [reporting_facility],
          [ordering_provider].[nm_prefix]                     as [ordering_provider_prefix],
          [ordering_provider].[first_nm]                      as [ordering_provider_first_name],
          [ordering_provider].[last_nm]                       as [ordering_provider_last_name],
          [ordering_provider].[nm_suffix]                     as [ordering_provider_suffix],
          case [lab].electronic_ind
              when 'Y' then 1
              else 0
          end                                                 as [electronic],
          [lab].[local_id]                                    as [event],
          [lab_test].lab_test_desc_txt                        as [Lab Test],
          [coded_result].lab_result_desc_txt                  as [coded_result],
          [numeric].comparator_cd_1                           as [comparator_cd_1],
          [numeric].numeric_value_1                           as [numeric_result],
          [numeric].high_range                                as [high_range],
          [numeric].low_range                                 as [low_range],
          [numeric].numeric_unit_cd                           as [unit]
      from [Observation] [lab]
      
          join NBS_SRTE..Jurisdiction_code [jurisdiction] on
                  [jurisdiction].[code]  = [lab].[jurisdiction_cd]
      
          join [Participation] [subject_of_report] on
                  [subject_of_report].type_cd='PATSBJ'
              and [subject_of_report].act_class_cd = 'OBS'
              and [subject_of_report].subject_class_cd = 'PSN'
              and [subject_of_report].record_status_cd = 'ACTIVE'
              and [subject_of_report].act_uid = [lab].observation_uid
      
          join person [subject] with (nolock) on
                  [subject_of_report].subject_entity_uid=[subject].person_uid
      
          join Participation [reporting_facility_participation] on
                  [reporting_facility_participation].act_uid = [lab].observation_uid
              and [reporting_facility_participation].type_cd = 'AUT'
              and [reporting_facility_participation].subject_class_cd = 'ORG'
      
          join Organization [reporting_facility] on
                  [reporting_facility].organization_uid = [reporting_facility_participation].[subject_entity_uid]
      
          left join Participation [ordering_provider_participation] on
                  [ordering_provider_participation].act_uid = [lab].observation_uid
              and [ordering_provider_participation].type_cd = 'ORD'
              and [ordering_provider_participation].subject_class_cd = 'PSN'
      
          left join person_name [ordering_provider] on
                              [ordering_provider].person_uid = [ordering_provider_participation].[subject_entity_uid]
      
          left join Act_relationship [lab_result_components] on
                  [lab_result_components].target_act_uid = [lab].observation_uid
              and [lab_result_components].type_cd = 'COMP'
              and [lab_result_components].source_class_cd = 'OBS'
              and [lab_result_components].target_class_cd = 'OBS'
      
          left join observation [lab_result] on
                  [lab_result].observation_uid = [lab_result_components].[source_act_uid]
              and [lab_result].obs_domain_cd_st_1 = 'Result'
      
          left join NBS_SRTE..Lab_test [lab_test] on
                  [lab_test].lab_test_cd = [lab_result].cd
      
          left join [Obs_value_coded] [coded] on
                  [coded].[observation_uid] = [lab_result].[observation_uid]
      
          left join NBS_SRTE..Lab_result [coded_result] on
                  [coded_result].[lab_result_cd] = [coded].code
      
          left join [Obs_value_numeric] [numeric] on
                  [numeric].[observation_uid] = [lab_result].[observation_uid]
      
      where   [lab].observation_uid in (:identifiers)
      """;

  private static final ProviderNameRowMapper.Column PROVIDER_COLUMNS = new ProviderNameRowMapper.Column(5, 6, 7, 8);
  private static final LaboratoryReportDetailRowMapper.Column DEFAULT_COLUMNS =
      new LaboratoryReportDetailRowMapper.Column(1, 2, 3, 4, PROVIDER_COLUMNS, 9, 10);

  private final NamedParameterJdbcTemplate template;
  private final LaboratoryReportDetailRowMapper mapper;
  private final PatientDocumentRequiringReviewMerger merger;

  LaboratoryReportDetailFinder(final NamedParameterJdbcTemplate template) {
    this.template = template;
    this.mapper = new LaboratoryReportDetailRowMapper(DEFAULT_COLUMNS);
    this.merger = new PatientDocumentRequiringReviewMerger();
  }

  Collection<DocumentRequiringReview> find(final PatientActivity.LabReport cases) {
    return cases.identifiers().isEmpty()
        ? Collections.emptyList()
        : findAll(cases.identifiers());
  }

  private Collection<DocumentRequiringReview> findAll(final Collection<Long> identifiers) {
    SqlParameterSource parameters = new MapSqlParameterSource(
        "identifiers", identifiers
    );

    return this.template.queryForStream(
        QUERY,
        parameters,
        this.mapper
    ).collect(
        Accumulator.collecting(
            DocumentRequiringReview::id,
            this.merger::merge
        )
    );
  }

}
