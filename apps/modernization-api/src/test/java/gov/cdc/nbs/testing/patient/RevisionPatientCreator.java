package gov.cdc.nbs.testing.patient;

import gov.cdc.nbs.identity.MotherSettings;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.identity.SequentialIdentityGenerator;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.Map;

@Component
class RevisionPatientCreator {

  private static final String QUERY = """
      insert into Entity(entity_uid, class_cd) values (:identifier, 'PSN');
      insert into Person (
        person_parent_uid,
        person_uid,
        local_id,
        version_ctrl_nbr,
        cd,
        birth_time,
        curr_sex_cd,
        add_user_id,
        add_time,
        last_chg_user_id,
        last_chg_time,
        record_status_cd,
        record_status_time
      )
      select
        [mpr].person_uid,
        :identifier,
        [mpr].local_id,
        1,
        [mpr].cd,
        [mpr].birth_time,
        [mpr].curr_sex_cd,
        :by,
        getDate(),
        :by,
        getDate(),
        'ACTIVE',
        getDate()
      from Person [mpr]
      where [mpr].person_uid = :mpr
      ;
      
      insert into Person_name (
          person_uid,
          person_name_seq,
          nm_use_cd,
          first_nm,
          last_nm,
          add_user_id,
          add_time,
          last_chg_user_id,
          last_chg_time,
          record_status_cd,
          record_status_time,
          status_cd,
          status_time
      )
      select
          :identifier,
          1,
          [name].nm_use_cd,
          [name].first_nm,
          [name].last_nm,
          [name].add_user_id,
          [name].add_time,
          [name].last_chg_user_id,
          [name].last_chg_time,
          [name].record_status_cd,
          [name].record_status_time,
          [name].status_cd,
          [name].status_time
      from Person_name [name]
      where   [name].person_uid = :mpr
          and [name].nm_use_cd = 'L'
          and [name].record_status_cd = 'ACTIVE'
          and [name].as_of_date = (
              select
                  max(eff_name.as_of_date)
              from person_name [eff_name]
              where   [eff_name].person_uid       = [name].[person_uid]
                  and [eff_name].nm_use_cd        = [name].nm_use_cd
                  and [eff_name].record_status_cd = [name].record_status_cd
                  and [eff_name].as_of_date       <= getDate()
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
      ;
      """;

  private final NamedParameterJdbcTemplate template;
  private final MotherSettings settings;
  private final SequentialIdentityGenerator idGenerator;

  RevisionPatientCreator(
      final NamedParameterJdbcTemplate template,
      final MotherSettings settings,
      final SequentialIdentityGenerator idGenerator
  ) {
    this.template = template;
    this.settings = settings;
    this.idGenerator = idGenerator;
  }

  PatientIdentifier revise(final PatientIdentifier patient) {

    long id = idGenerator.next();

    SqlParameterSource parameters = new MapSqlParameterSource(
        Map.of(
            "mpr", patient.id(),
            "identifier", id,
            "by", this.settings.createdBy()
        )
    );

    this.template.execute(
        QUERY,
        parameters,
        PreparedStatement::executeUpdate
    );

    return new PatientIdentifier(id, patient.shortId(), patient.local());
  }
}
