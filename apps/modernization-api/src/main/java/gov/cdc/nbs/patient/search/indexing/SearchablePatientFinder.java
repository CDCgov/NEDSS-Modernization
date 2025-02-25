package gov.cdc.nbs.patient.search.indexing;

import gov.cdc.nbs.patient.search.SearchablePatient;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class SearchablePatientFinder {

  private static final String QUERY =
      """
          select
              person_uid,
              local_id,
              record_status_cd,
              birth_time,
              deceased_ind_cd,
              curr_sex_cd,
              ethnic_group_ind,

              --documentIds
              (SELECT STUFF(
              (
                SELECT ','+ cast(documentId as varchar)
                FROM (
                SELECT DISTINCT
                  doc.local_id documentId
                FROM
                  nbs_document doc WITH (NOLOCK)
                    JOIN Participation par WITH (NOLOCK)
                      ON doc.nbs_document_uid = par.act_uid
                    AND par.subject_class_cd = 'PSN'
                      AND par.record_status_cd = 'ACTIVE'
                      AND par.act_class_cd = 'DOC'
                      AND par.type_cd = 'SubjOfDoc'
                      AND par.subject_entity_uid IN (select person_uid from Person p2 where p2.person_parent_uid=[patient].person_parent_uid)
                    JOIN NBS_SRTE..Code_value_general cvg
                    ON cvg.code_set_nm = 'PUBLIC_HEALTH_EVENT'
                    AND cvg.code = doc.doc_type_cd
                ) tmp
                FOR XML PATH('')
              ), 1, 1, '')) document_ids,

              --morbidityReportIds
              (SELECT STUFF(
              (
                SELECT ','+ cast(morbidityReportId as varchar)
                FROM (
                SELECT DISTINCT
                  obs.local_id morbidityReportId
                  FROM
                  Observation obs
                  JOIN Participation par WITH (NOLOCK)
                    ON par.act_uid = obs.observation_uid
                  AND par.subject_class_cd = 'PSN'
                      AND par.record_status_cd = 'ACTIVE'
                      AND par.act_class_cd = 'OBS'
                      AND par.type_cd = 'SubjOfMorbReport'
                    AND par.subject_entity_uid IN (select person_uid from Person p2 where p2.person_parent_uid=[patient].person_parent_uid)
                ) tmp
                FOR XML PATH('')
              ), 1, 1, '')) morbidity_report_ids,

              --treatmentIds
              (SELECT STUFF(
              (
                SELECT ','+ cast(treatmentId as varchar)
                FROM (
                SELECT DISTINCT
                  trt.local_id treatmentId
                FROM
                  Treatment trt with (NOLOCK)
                  JOIN Participation par WITH (NOLOCK)
                    ON par.act_uid = trt.treatment_uid
                  AND par.subject_entity_uid IN (select person_uid from Person p2 where p2.person_parent_uid=[patient].person_parent_uid)
                    AND par.act_class_cd = 'TRMT'
                    AND par.record_status_cd = 'ACTIVE'
                    AND par.subject_class_cd='PSN'
                    AND par.type_cd = 'SubjOfTrmt'
                ) tmp
                FOR XML PATH('')
              ), 1, 1, '')) treatment_ids,

              --vaccinationIds
              (SELECT STUFF(
              (
                SELECT ','+ cast(vaccinationId as varchar)
                FROM (
              SELECT DISTINCT
                inv.local_id vaccinationId
              FROM
                Intervention inv WITH (NOLOCK)
                JOIN Participation par WITH (NOLOCK)
                  ON inv.intervention_uid = par.act_uid
                AND par.subject_class_cd='PAT'
                  AND par.type_cd = 'SubOfVacc'
                AND par.subject_entity_uid IN (select person_uid from Person p2 where p2.person_parent_uid=[patient].person_parent_uid)
                ) tmp
                FOR XML PATH('')
              ), 1, 1, '')) vaccination_ids,

                --State Case Ids
                (SELECT STUFF(
                (
                  SELECT ','+ cast(stateCaseId as varchar)
                  FROM (
                  SELECT ai.root_extension_txt stateCaseId
                  FROM
                    Public_health_case phc
                    JOIN Act_id ai ON phc.public_health_case_uid = ai.act_uid
                    and ai.type_cd='STATE'
                    and (ai.assigning_authority_cd <> 'ABCS' OR ai.assigning_authority_cd IS NULL)
                    and ai.root_extension_txt is not null
                    and ai.root_extension_txt<>''
                    JOIN participation par ON par.act_uid = phc.public_health_case_uid
                      AND par.subject_entity_uid IN (select person_uid from Person p2 where p2.person_parent_uid=[patient].person_parent_uid)
                  ) tmp
                  FOR XML PATH('')
                ), 1, 1, '')) state_case_ids,

                --ABCS Case IDs
                (SELECT STUFF(
                (
                  SELECT ','+ cast(abcsCaseId as varchar)
                  FROM (
                  SELECT ai.root_extension_txt abcsCaseId
                  FROM
                    Public_health_case phc
                    JOIN Act_id ai ON phc.public_health_case_uid = ai.act_uid
                      and ai.act_id_seq=2
                    and ai.type_cd='STATE'
                    and ai.assigning_authority_cd='ABCS'
                    and ai.root_extension_txt is not null
                    and ai.root_extension_txt<>''
                    JOIN participation par ON par.act_uid = phc.public_health_case_uid
                      AND par.subject_entity_uid IN (select person_uid from Person p2 where p2.person_parent_uid=[patient].person_parent_uid)
                  ) tmp
                  FOR XML PATH('')
                ), 1, 1, '')) abcs_case_ids,

                --City Case Ids
                (SELECT STUFF(
                (
                  SELECT ','+ cast(cityCaseId as varchar)
                  FROM (
                  SELECT ai.root_extension_txt cityCaseId
                  FROM
                    Public_health_case phc
                    JOIN Act_id ai ON phc.public_health_case_uid = ai.act_uid
                    and ai.type_cd='CITY'
                    and ai.root_extension_txt is not null
                    and ai.root_extension_txt<>''
                    JOIN participation par ON par.act_uid = phc.public_health_case_uid
                      AND par.subject_entity_uid IN (select person_uid from Person p2 where p2.person_parent_uid=[patient].person_parent_uid)
                  ) tmp
                  FOR XML PATH('')
                ), 1, 1, '')) city_case_ids,

                --Notification Ids
                (SELECT STUFF(
                (
                  SELECT ','+ cast(notificationId as varchar)
                  FROM (
                  SELECT noti.local_id notificationId
                  FROM
                    Public_health_case phc
                    JOIN Act_relationship ar ON ar.target_act_uid=phc.public_health_case_uid
                      AND ar.type_cd='Notification'
                    AND ar.target_class_cd='CASE'
                    AND ar.source_class_cd='NOTF'
                    AND ar.record_status_cd='ACTIVE'
                    JOIN notification noti ON noti.notification_uid=ar.source_act_uid
                    JOIN participation par ON par.act_uid = phc.public_health_case_uid
                      AND par.subject_entity_uid IN (select person_uid from Person p2 where p2.person_parent_uid=[patient].person_parent_uid)
                    ) tmp
                    FOR XML PATH('')
                  ), 1, 1, '')) notification_ids,

                --Investigation Ids
                (SELECT STUFF(
                (
                  SELECT ','+ cast(investigationId as varchar)
                  FROM (
                  SELECT phc.local_id investigationId
                  FROM
                    Public_health_case phc
                    JOIN participation par ON par.act_uid = phc.public_health_case_uid
                      AND par.subject_entity_uid IN (select person_uid from Person p2 where p2.person_parent_uid=[patient].person_parent_uid)
                    ) tmp
                    FOR XML PATH('')
                  ), 1, 1, '')) investigation_ids,


                --Lab Report Ids
                (SELECT STUFF(
                (
                  SELECT ','+ cast(labReportId as varchar)
                  FROM (
                  SELECT
                    obs.local_id labReportId
                  FROM
                    observation obs
                    JOIN participation par ON par.act_uid = obs.observation_uid
                      AND par.subject_entity_uid IN (select person_uid from Person p2 where p2.person_parent_uid=[patient].person_parent_uid)
                    ) tmp
                    FOR XML PATH('')
                  ), 1, 1, '')) lab_report_ids,


                --Accession Ids
                (SELECT STUFF(
                (
                  SELECT ','+ cast(accessionId as varchar)
                  FROM (
                  SELECT ai.root_extension_txt accessionId
                  FROM
                    observation obs
                    JOIN act_id ai ON ai.act_uid = obs.observation_uid
                      AND ai.type_cd='FN'
                      and ai.root_extension_txt is not null
                      and ai.root_extension_txt<>''
                    JOIN participation par ON par.act_uid = obs.observation_uid
                      AND par.subject_entity_uid IN (select person_uid from Person p2 where p2.person_parent_uid=[patient].person_parent_uid)
                    ) tmp
                    FOR XML PATH('')
                  ), 1, 1, '')) accession_ids,

                --sort_email_address
                (SELECT MAX(tl.email_address) sort_email_address
                FROM
                  Entity_locator_participation elp WITH (NOLOCK)
                  JOIN Tele_locator tl WITH (NOLOCK) ON elp.locator_uid = tl.tele_locator_uid
                WHERE
                  elp.entity_uid IN (select person_uid from Person p2 where p2.person_parent_uid=[patient].person_parent_uid)
                  AND elp.class_cd = 'TELE'
                  AND elp.status_cd = 'A'
                  AND tl.email_address IS NOT NULL
                  AND elp.as_of_date = (select max(as_of_date)
                    FROM Entity_locator_participation elp2 WITH (NOLOCK)
                    JOIN Tele_locator tl2 WITH (NOLOCK) ON elp2.locator_uid = tl2.tele_locator_uid
                    WHERE
                      elp2.entity_uid IN (select person_uid from Person p2 where p2.person_parent_uid=[patient].person_parent_uid)
                      AND elp2.class_cd = 'TELE'
                      AND elp2.status_cd = 'A'
                      AND tl2.email_address IS NOT NULL)) sort_email_address

          from person [patient]
          where cd = 'PAT'
          and person_uid = ?
          """;
  private static final int PATIENT_PARAMETER = 1;
  private static final int IDENTIFIER_COLUMN = 1;
  private static final int LOCAL_COLUMN = 2;
  private static final int STATUS_COLUMN = 3;
  private static final int BIRTHDAY_COLUMN = 4;
  private static final int DECEASED_COLUMN = 5;
  private static final int GENDER_COLUMN = 6;
  private static final int ETHNICITY_COLUMN = 7;
  private static final int DOCUMENT_IDS_COLUMN = 8;
  private static final int MORBIDITY_REPORT_IDS_COLUMN = 9;
  private static final int TREATMENT_IDS_COLUMN = 10;
  private static final int VACCINATION_IDS_COLUMN = 11;
  private static final int STATE_CASE_IDS_COLUMN = 12;
  private static final int ABCS_CASE_IDS_COLUMN = 13;
  private static final int CITY_CASE_IDS_COLUMN = 14;
  private static final int NOTIFICATION_IDS_COLUMN = 15;
  private static final int INVESTIGATION_IDS_COLUMN = 16;
  private static final int LAB_REPORT_IDS_COLUMN = 17;
  private static final int ACCESSION_IDS_COLUMN = 18;
  private static final int SORT_EMAIL_COLUMN = 19;

  private final JdbcTemplate template;
  private final SearchablePatientRowMapper mapper;

  SearchablePatientFinder(final JdbcTemplate template) {
    this.template = template;
    this.mapper = new SearchablePatientRowMapper(
        new SearchablePatientRowMapper.Column(
            IDENTIFIER_COLUMN,
            LOCAL_COLUMN,
            STATUS_COLUMN,
            BIRTHDAY_COLUMN,
            DECEASED_COLUMN,
            GENDER_COLUMN,
            ETHNICITY_COLUMN,
            DOCUMENT_IDS_COLUMN,
            MORBIDITY_REPORT_IDS_COLUMN,
            TREATMENT_IDS_COLUMN,
            VACCINATION_IDS_COLUMN,
            ABCS_CASE_IDS_COLUMN,
            CITY_CASE_IDS_COLUMN,
            STATE_CASE_IDS_COLUMN,
            ACCESSION_IDS_COLUMN,
            INVESTIGATION_IDS_COLUMN,
            LAB_REPORT_IDS_COLUMN,
            NOTIFICATION_IDS_COLUMN,
            SORT_EMAIL_COLUMN));
  }

  Optional<SearchablePatient> find(long identifier) {
    return this.template.query(
        QUERY,
        statement -> statement.setLong(PATIENT_PARAMETER, identifier),
        this.mapper).stream()
        .findFirst();
  }
}
