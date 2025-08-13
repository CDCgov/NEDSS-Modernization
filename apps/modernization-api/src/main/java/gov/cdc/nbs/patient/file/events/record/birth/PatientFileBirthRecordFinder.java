package gov.cdc.nbs.patient.file.events.record.birth;

import gov.cdc.nbs.sql.AccumulatingResultSetExtractor;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
class PatientFileBirthRecordFinder {

  private static final String QUERY = """
      with revisions (person_uid, mpr_id) as (
          select
              [patient].[person_uid],
              [patient].person_parent_uid
          from  Person [patient] with (nolock)
          where   [patient].person_parent_uid = ?
              and [patient].person_parent_uid <> [patient].person_uid
              and [patient].cd = 'PAT'
              and [patient].record_status_cd = 'ACTIVE'
      )
      select
          [revisions].mpr_id                          as [patient],
          [record].clinical_document_uid              as [identifier],
          [record].local_id                           as [local],
          [record].add_time                           as [recevied_on],
          [birth_facility].display_nm                 as [birth_facility],
          [record].record_status_time                 as [collected_on],
          [certificate].root_extension_txt            as [certificate],
          [mother_info_question].question_identifier  as [question],
          case [mother_info_question].question_identifier
              when 'MTH166' then [state].code_desc_txt
              when 'MTH168' then [county].code_desc_txt
              when 'MTH204' then [suffix].code_short_desc_txt
              else [mother_info_answer].answer_txt
          end             as [mother_info_answer]
      from revisions
      
          join Participation as [subject_of_record]  with (nolock) on
                  [subject_of_record].subject_entity_uid = revisions.person_uid
              and [subject_of_record].type_cd = 'SubjOfBirth'
              and [subject_of_record].record_status_cd = 'ACTIVE'
              and [subject_of_record].subject_class_cd = 'PSN'
      
          join Clinical_document as [record]  with (nolock) on
                  [record].clinical_document_uid = [subject_of_record].act_uid
              and [record].cd = 'BIR'
              and [record].record_status_cd = 'ACTIVE'
      
          join Act_id as [certificate] with (nolock) on
                  [certificate].act_uid = [record].clinical_document_uid
              and [certificate].record_status_cd = 'ACTIVE'
      
          left join Participation AS [facility_of_birth]  with (nolock) ON\s
                  [record].clinical_document_uid = [facility_of_birth].act_uid
              and [facility_of_birth].record_status_cd = 'ACTIVE'
              and [facility_of_birth].act_class_cd = 'DOCCLIN'
              and [facility_of_birth].type_cd = 'FacilityOfBirth'
              and [facility_of_birth].subject_class_cd = 'ORG'
      
          left join Organization [birth_facility] with (nolock) on
                  [birth_facility].organization_uid = [facility_of_birth].[subject_entity_uid]
      
          left join nbs_answer [mother_info_answer] with (nolock) on
                  [mother_info_answer].act_uid = [record].clinical_document_uid
              and [mother_info_answer].[record_status_cd] = 'ACTIVE'
      
          left join NBS_question [mother_info_question] with (nolock) on
                  [mother_info_question].[nbs_question_uid] = [mother_info_answer].[nbs_question_uid]
              and [mother_info_question].question_identifier in ( 'MTH201','MTH202','MTH203','MTH204','DEM159_MTH','DEM160_MTH','MTH209','MTH166','MTH169','MTH168')
      
          left join NBS_SRTE..Code_value_general [suffix] on
                  [suffix].[code_set_nm] = 'P_NM_SFX'
              and [suffix].[code] = [mother_info_answer].[answer_txt]
              and [mother_info_question].question_identifier = 'MTH204'
      
          left join NBS_SRTE..State_county_code_value [county] with (nolock) on
                  [county].[code] = [mother_info_answer].[answer_txt]
              and [mother_info_question].question_identifier = 'MTH168'
      
          left join NBS_SRTE..State_code [state] with (nolock) on
                  [state].state_cd = [mother_info_answer].[answer_txt]
              and [mother_info_question].question_identifier = 'MTH166'
      order by
          [record].local_id desc
      """;

  private final JdbcClient client;
  private final ResultSetExtractor<Collection<PatientFileBirthRecord>> extractor;

  PatientFileBirthRecordFinder(final JdbcClient client) {
    this.client = client;
    this.extractor = new AccumulatingResultSetExtractor<>(
        (resultSet, rowNum) -> resultSet.getString(2),
        new PatientFileBirthRecordRowMapper(),
        PatientFileBirthRecordMerger::merge
    );

  }

  Collection<PatientFileBirthRecord> find(final long patient) {
    return this.client.sql(QUERY)
        .param(patient)
        .query(this.extractor);
  }
}
