package gov.cdc.nbs.patient.profile.identification;

import gov.cdc.nbs.data.pagination.WindowedPagedResultSetHandler;
import org.elasticsearch.core.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

@Component
class PatientIdentificationFinder {

  private static final String QUERY = """
      select
          count([identification].[entity_id_seq]) over(partition by [identification].[entity_uid]) as total,
          [identification].[entity_uid]           as [id],
          [identification].[entity_id_seq]        as [sequence],
          [patient].version_ctrl_nbr              as [version],
          [identification].[as_of_date]           as [as_of],
          [identification].[type_cd]              as [type_id],
          [type].code_short_desc_txt              as [type_description],
          [identification].assigning_authority_cd as [authority_id],
          [authority].code_short_desc_txt         as [authority_description],
          [identification].root_extension_txt     as [value]
      from Person [patient]
            
          join [Entity_id] [identification] on
                  [identification].[entity_uid] = [patient].person_uid
              and [identification].[record_status_cd] = 'ACTIVE'
            
          join NBS_SRTE..Code_value_general [type] on
                  [type].code_set_nm = 'EI_TYPE_PAT'
              and [type].code = [identification].[type_cd]
            
          left join NBS_SRTE..Code_value_general [authority] on
                  [authority].[code_set_nm] = 'EI_AUTH_PAT'
              and [authority].[code] = [identification].assigning_authority_cd
            
      where   [patient].person_uid = :patient
          and [patient].cd = 'PAT'
      order by [identification].[entity_uid]
      offset :offset rows
      fetch next :limit rows only
      """;
  private static final int TOTAL_COLUMN = 1;
  private static final int PATIENT_COLUMN = 2;
  private static final int SEQUENCE_COLUMN = 3;
  private static final int VERSION_COLUMN = 4;
  private static final int AS_OF_COLUMN = 5;
  private static final int TYPE_ID_COLUMN = 6;
  private static final int TYPE_DESCRIPTION_COLUMN = 7;
  private static final int AUTHORITY_ID_COLUMN = 8;
  private static final int AUTHORITY_DESCRIPTION_COLUMN = 9;
  private static final int VALUE_COLUMN = 10;

  private final NamedParameterJdbcTemplate template;
  private final PatientIdentificationRowMapper mapper;

  PatientIdentificationFinder(final NamedParameterJdbcTemplate template) {
    this.template = template;
    this.mapper = new PatientIdentificationRowMapper(new PatientIdentificationRowMapper.Column(
        PATIENT_COLUMN,
        SEQUENCE_COLUMN,
        VERSION_COLUMN,
        AS_OF_COLUMN,
        TYPE_ID_COLUMN,
        TYPE_DESCRIPTION_COLUMN,
        AUTHORITY_ID_COLUMN,
        AUTHORITY_DESCRIPTION_COLUMN,
        VALUE_COLUMN
    ));
  }

  Page<PatientIdentification> find(final long patient, final Pageable pageable) {

    SqlParameterSource parameters = new MapSqlParameterSource(
        Map.of(
            "patient", patient,
            "offset", pageable.getOffset(),
            "limit", pageable.getPageSize()
        )
    );

    WindowedPagedResultSetHandler<PatientIdentification> handler =
        new WindowedPagedResultSetHandler<>(TOTAL_COLUMN, this.mapper);

    this.template.query(QUERY, parameters, handler);

    return handler.asPaged(pageable);
  }

}
