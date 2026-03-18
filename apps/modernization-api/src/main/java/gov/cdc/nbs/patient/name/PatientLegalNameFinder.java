package gov.cdc.nbs.patient.name;

import gov.cdc.nbs.demographics.name.DisplayableName;
import gov.cdc.nbs.demographics.name.DisplayableNameRowMapper;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

@Component
public class PatientLegalNameFinder {

  private static final String QUERY =
      """
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

  private final JdbcClient client;
  private final RowMapper<DisplayableName> mapper;

  PatientLegalNameFinder(final JdbcClient client) {
    this.client = client;
    this.mapper = new DisplayableNameRowMapper();
  }

  public Optional<DisplayableName> find(final long patient, final LocalDate asOf) {
    return this.client
        .sql(QUERY)
        .param(patient)
        .param(asOf.atTime(23, 59, 59))
        .query(this.mapper)
        .optional();
  }
}
