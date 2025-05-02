package gov.cdc.nbs.patient.file.demographics.summary;

import gov.cdc.nbs.demographics.indentification.DisplayableIdentification;
import gov.cdc.nbs.demographics.indentification.DisplayableIdentificationRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Collection;

@Component
class PatientDemographicsSummaryIdentificationFinder {

  private static final String QUERY = """
      select
          [type].code_short_desc_txt              as [type],
          [identification].root_extension_txt     as [value]
      from [Entity_id] [identification]
      
          join NBS_SRTE..Code_value_general [type] on
                  [type].code_set_nm = 'EI_TYPE_PAT'
              and [type].code = [identification].[type_cd]
      
      where   [identification].[entity_uid] =  ?
          and [identification].[record_status_cd] = 'ACTIVE'
          and [identification].as_of_date = (
              select
                  max([eff_as_of].as_of_date)
              from [Entity_id] [eff_as_of]
              where   [eff_as_of].entity_uid = [identification].entity_uid
                  and [eff_as_of].record_status_cd = 'ACTIVE'
                  and [eff_as_of].type_cd = [identification].[type_cd]
                  and [eff_as_of].[as_of_date] <=  ?
      )
          and [identification].entity_id_seq = (
              select
                  max(eff_seq.entity_id_seq)
              from [Entity_id] [eff_seq]
              where   [eff_seq].entity_uid = [identification].entity_uid
                  and [eff_seq].record_status_cd = 'ACTIVE'
                  and [eff_seq].type_cd = [identification].[type_cd]
                  and [eff_seq].[as_of_date] = [identification].as_of_date
      )
      order by
          [identification].as_of_date desc
      """;

  private static final int PATIENT_PARAMETER = 1;
  private static final int AS_OF_PARAMETER = 2;

  private final JdbcTemplate template;
  private final RowMapper<DisplayableIdentification> mapper;

  PatientDemographicsSummaryIdentificationFinder(final JdbcTemplate template) {
    this.template = template;
    this.mapper = new DisplayableIdentificationRowMapper();
  }

  Collection<DisplayableIdentification> find(final long patient, final LocalDate asOf) {
    return this.template.query(
        QUERY, statement -> {
          statement.setLong(PATIENT_PARAMETER, patient);
          statement.setTimestamp(AS_OF_PARAMETER, Timestamp.valueOf(asOf.atStartOfDay()));
        },
        this.mapper
    );
  }

}
