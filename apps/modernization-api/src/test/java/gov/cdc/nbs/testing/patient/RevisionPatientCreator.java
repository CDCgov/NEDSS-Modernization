package gov.cdc.nbs.testing.patient;

import gov.cdc.nbs.identity.MotherSettings;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.identity.SequentialIdentityGenerator;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

@Component
class RevisionPatientCreator {

  private static final String QUERY = """
      declare @legal table (patient bigint not null, first_nm varchar(50), middle_nm varchar(50), last_nm varchar(50));
      
      insert into @legal (
          patient,
          first_nm,
          middle_nm,
          last_nm
      )
      select
          [name].person_uid,
          [name].first_nm,
          [name].middle_nm,
          [name].last_nm
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
      
      insert into Entity(entity_uid, class_cd) values (:identifier, 'PSN');
      
      insert into Person (
        person_parent_uid,
        person_uid,
        local_id,
        version_ctrl_nbr,
        cd,
        birth_time,
        curr_sex_cd,
        first_nm,
        middle_nm,
        last_nm,
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
        [legal].first_nm,
        [legal].middle_nm,
        [legal].last_nm,
        :by,
        getDate(),
        :by,
        getDate(),
        'ACTIVE',
        getDate()
      from Person [mpr]
          left join @legal [legal] on
                  [legal].patient = [mpr].person_uid
      
      where [mpr].person_uid = :mpr;
      
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
          'L',
          [name].first_nm,
          [name].last_nm,
          :by,
          getDate(),
          :by,
          getDate(),
          'ACTIVE',
          getDate(),
          'A',
          :by
      from @legal [name];
      """;

  private final JdbcClient client;
  private final MotherSettings settings;
  private final SequentialIdentityGenerator idGenerator;

  RevisionPatientCreator(
      final JdbcClient client,
      final MotherSettings settings,
      final SequentialIdentityGenerator idGenerator
  ) {
    this.client = client;
    this.settings = settings;
    this.idGenerator = idGenerator;
  }

  PatientIdentifier revise(final PatientIdentifier patient) {

    long id = idGenerator.next();

    this.client.sql(QUERY)
        .param("mpr", patient.id())
        .param("identifier", id)
        .param("by", this.settings.createdBy())
        .update();

    return new PatientIdentifier(id, patient.shortId(), patient.local());
  }
}
