package gov.cdc.nbs.patient.documentsrequiringreview.detail;

import gov.cdc.nbs.patient.documentsrequiringreview.DocumentRequiringReview;
import gov.cdc.nbs.patient.documentsrequiringreview.PatientActivity;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;

@Component
class CaseReportDetailFinder {

  private static final String QUERY = """
      select
          [document].nbs_document_uid             as [id],
          [document].add_time                     as [date_received],
          [document_type].code_short_desc_txt     as [document_type],
          [document].sending_facility_nm          as [sending_facility],
          [condition].condition_short_nm          as [condition],
          [document].local_id                     as [event_id],
          case
              when external_version_ctrl_nbr > 1
                  then 1
              else 0
          end                                     as [updated]
      from nbs_document [document] with (nolock)

          join NBS_SRTE..Code_value_general [document_type] on
                  [document_type].[code_set_nm] = 'PUBLIC_HEALTH_EVENT'
              and [document_type].code = [document].doc_type_cd

          join nbs_srte..Condition_code [condition] on
                          [condition].condition_cd = [document].cd
            
      where   [document].nbs_document_uid in (:identifiers)
      """;
  private static final CaseReportDetailRowMapper.Column DEFAULT_COLUMNS =
      new CaseReportDetailRowMapper.Column(1, 2, 3,
          new FacilityProvidersRowMapper.Column(
              null,
              null,
              new SendingFacilityRowMapper.Column(4)
          ), 5, 6, 7);

  private final NamedParameterJdbcTemplate template;
  private final CaseReportDetailRowMapper mapper;

  CaseReportDetailFinder(final NamedParameterJdbcTemplate template) {
    this.template = template;
    this.mapper = new CaseReportDetailRowMapper(DEFAULT_COLUMNS);
  }

  Collection<DocumentRequiringReview> find(final PatientActivity.CaseReport cases) {
    return cases.identifiers().isEmpty()
        ? Collections.emptyList()
        : findAll(cases.identifiers());
  }

  private Collection<DocumentRequiringReview> findAll(final Collection<Long> identifiers) {
    SqlParameterSource parameters = new MapSqlParameterSource(
        "identifiers", identifiers
    );

    return this.template.query(QUERY, parameters, this.mapper);
  }
}
