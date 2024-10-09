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
              ), 1, 1, '')) vaccination_ids
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
            VACCINATION_IDS_COLUMN));
  }

  Optional<SearchablePatient> find(long identifier) {
    return this.template.query(
        QUERY,
        statement -> statement.setLong(PATIENT_PARAMETER, identifier),
        this.mapper).stream()
        .findFirst();
  }
}
