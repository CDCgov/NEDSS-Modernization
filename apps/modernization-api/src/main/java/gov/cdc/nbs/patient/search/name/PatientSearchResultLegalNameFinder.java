package gov.cdc.nbs.patient.search.name;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Optional;

@Component
public class PatientSearchResultLegalNameFinder {

  private static final String QUERY = """
      select
          [use].[code_short_desc_txt] as [type],
          [name].first_nm,
          [name].middle_nm,
          [name].last_nm,
          [suffix].code_short_desc_txt
      from Person_name [name]

      join NBS_SRTE..Code_value_general [use] on
                 [use].[code_set_nm] = 'P_NM_USE'
             and [use].[code] = [name].nm_use_cd

          left join NBS_SRTE..Code_value_general [suffix] on
                  [suffix].[code_set_nm] = 'P_NM_SFX'
              and [suffix].[code] = [name].nm_suffix

      where   [name].person_uid = ?
          and [name].nm_use_cd = 'L'
          and [name].record_status_cd = 'ACTIVE'
          and [name].as_of_date = (
              select
                  max(eff_name.as_of_date)
              from person_name [eff_name]
              where   [eff_name].person_uid       = [name].[person_uid]
                  and [eff_name].nm_use_cd        = [name].nm_use_cd
                  and [eff_name].record_status_cd = [name].record_status_cd
                  and [eff_name].as_of_date       <= ?
          )
          and [name].person_name_seq = (
              select
                  max(seq_name.person_name_seq)
              from person_name [seq_name]
              where   [seq_name].[person_uid] = [name].person_uid
                  and [seq_name].nm_use_cd = [name].nm_use_cd
                  and [seq_name].record_status_cd = [name].record_status_cd
                  and [seq_name].[as_of_date] = [name].as_of_date
          )
      """;

  private static final int PATIENT_PARAMETER = 1;
  private static final int AS_OF_PARAMETER = 2;

  private final JdbcTemplate template;
  private final RowMapper<PatientSearchResultName> mapper;

  PatientSearchResultLegalNameFinder(final JdbcTemplate template) {
    this.template = template;
    this.mapper = new PatientSearchResultNameMapper();
  }

  public Optional<PatientSearchResultName> find(final long patient, final LocalDate asOf) {
    return this.template.query(
        QUERY,
        statement -> {
          statement.setTimestamp(AS_OF_PARAMETER, Timestamp.valueOf(asOf.atStartOfDay()));
          statement.setLong(PATIENT_PARAMETER, patient);
        },
        this.mapper).stream()
        .findFirst();
  }
}
//
