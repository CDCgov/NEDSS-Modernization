package gov.cdc.nbs.patient.file;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PatientFileFinder {

  private static final String QUERY = """
      with id_settings([prefix], [suffix],[initial]) as   (
        select
          [generator].UID_prefix_cd     as [prefix],
          [generator].UID_suffix_CD     as [suffix],
          cast([configuration].config_value as bigint)  as [initial]
        from Local_UID_generator [generator] WITH (NOLOCK),
          NBS_configuration [configuration] WITH (NOLOCK)
        where [generator].class_name_cd = 'PERSON'
          and [generator].type_cd = 'LOCAL'
          and [configuration].config_key = 'SEED_VALUE'
      )
      select
          [patient].person_uid                    as [id],
          cast(
              substring(
                  [patient].local_id,
                  len(id_settings.prefix) + 1,
                  len([patient].local_id) - len(id_settings.prefix) - len(id_settings.suffix)
              ) as bigint
          ) - id_settings.initial                 as [patient_id],
          [patient].local_id                      as [local],
          case [patient].[record_status_cd]
              when 'LOG_DEL' then 'INACTIVE'
              when 'SUPERCEDED' then 'SUPERSEDED'
                  else [patient].[record_status_cd]
          end                                     as [status],
          [current_gender].code_short_desc_txt    as [sex],
          [patient].birth_time                    as [birthday],
          [patient].deceased_time                 as [deceased_on],
          [patient].version_ctrl_nbr              as [version]
      from id_settings, Person [patient]
      
          left join NBS_SRTE..Code_value_general [current_gender] on
                  [current_gender].code_set_nm = 'SEX'
              and [current_gender].[code] = [patient].[curr_sex_cd]
      
      where   [patient].cd = 'PAT'
          and [patient].person_uid = [patient].person_parent_uid
          and [patient].local_id = ?
      """;

  private final JdbcClient client;
  private final RowMapper<PatientFile> mapper;

  PatientFileFinder(final JdbcTemplate template, final JdbcClient client) {
    this.client = client;
    this.mapper = new PatientFileRowMapper(
        new PatientFileRowMapper.Columns(
            1, 2, 3, 4, 5, 6, 7
        )
    );
  }

  Optional<PatientFile> find(final String local) {
    return this.client.sql(QUERY).param(local).query(this.mapper).optional();
  }
}
