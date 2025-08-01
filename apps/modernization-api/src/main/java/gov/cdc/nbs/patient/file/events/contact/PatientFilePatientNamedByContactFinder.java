package gov.cdc.nbs.patient.file.events.contact;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

@Component
class PatientFilePatientNamedByContactFinder extends BasePatientFIleContactFinder {

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
        ),
        revisions (person_uid, mpr_id) as (
            select
                [patient].[person_uid],
                [patient].person_parent_uid
            from  Person [patient] with (nolock)
            where   [patient].person_parent_uid = :patient
                and [patient].person_parent_uid <> [patient].person_uid
                and [patient].cd = 'PAT'
                and [patient].record_status_cd = 'ACTIVE'
      )
      select
              [condition].[condition_desc_txt]                as [condition],
              [revisions].mpr_id                              as [patient],
              [contact_record].[CT_Contact_uid]               as [identifier],
              [contact_record].local_id                       as [local],
              [contact_record].contact_referral_basis_cd      as [referral_basis],
              [processing_descision].code_desc_txt            as [processing_descision],
              [contact_record].[add_time]                     as [created_on],
              [contact_record].named_On_Date                  as [named_on],
              cast(
                      substring(
                              [named].local_id,
                              len(id_settings.prefix) + 1,
                              len([named].local_id) - len(id_settings.prefix) - len(id_settings.suffix)
                      ) as bigint
              ) - id_settings.initial                         as [name_patient_id],
              [named].first_nm                                as [named_first_name],
              [named].middle_nm                               as [named_middle_name],
              [named].last_nm                                 as [named_last_name],
              [suffix].code_short_desc_txt                    as [named_suffix],
              [priority].code_desc_txt                        as [priority],
              [disposition].code_desc_txt                     as [disposition],
              [associated].public_health_case_uid             as [associated_identifier],
              [associated].local_id                           as [associstad_local],
              [associated_condition].condition_short_nm       as [associated_condition],
              [follow_up_status].code_desc_txt                as [associated_status]
      from id_settings, revisions
              join CT_contact [contact_record] with (nolock) on
                          [contact_record].contact_entity_uid = [revisions].person_uid
                      and [contact_record].record_status_cd = 'ACTIVE'
                      and [contact_record].program_jurisdiction_oid in (:any)
      
              join public_health_case [investigation] with (nolock) on
                      [investigation].public_health_case_uid = [contact_record].subject_entity_phc_uid
      
              join nbs_srte..Condition_code [condition] on
                      [condition].condition_cd = [investigation].cd
      
              join Person [named] with (nolock) on
                          [named].person_uid = [contact_record].subject_entity_uid
      
              left join NBS_SRTE..Code_value_general [suffix] on
                          [suffix].[code_set_nm] = 'P_NM_SFX'
                      and [suffix].[code] = [named].nm_suffix
      
              left join NBS_SRTE..Code_value_general [processing_descision] with (nolock) on
                          [processing_descision].[code] = [contact_record].[processing_decision_cd]
                      and [processing_descision].code_set_nm = 'STD_CONTACT_RCD_PROCESSING_DECISION'
      
              left join NBS_SRTE..Code_value_general [priority] on
                          [priority].code = [contact_record].priority_cd
                      and [priority].code_set_nm = 'NBS_PRIORITY'
      
              left join NBS_SRTE..Code_value_general [disposition] with (nolock) on
                          [disposition].code = [contact_record].disposition_cd
                      and [disposition].code_set_nm = 'NBS_DISPO'
      
              --  Associated investigations
              left join public_health_case [associated] with (nolock) on
                          [associated].PUBLIC_HEALTH_CASE_UID  = [contact_record].SUBJECT_ENTITY_PHC_UID
                      and [associated].program_jurisdiction_oid in (:associated)
      
              left join nbs_srte..Condition_code [associated_condition] with (nolock) on
                              [associated_condition].condition_cd = [associated].cd
      
              left join case_management [management] with (nolock) on
                      [management].public_health_case_uid = [associated].public_health_case_uid
      
              left join NBS_SRTE..Code_value_general [follow_up_status] with (nolock) on
                          [follow_up_status].code = [management].init_foll_up
                      and [follow_up_status].code_set_nm = 'STD_NBS_PROCESSING_DECISION_ALL'
      
      order by
          [contact_record].local_id desc
      """;



  PatientFilePatientNamedByContactFinder(final JdbcClient client) {
    super(QUERY, client);
  }

}
