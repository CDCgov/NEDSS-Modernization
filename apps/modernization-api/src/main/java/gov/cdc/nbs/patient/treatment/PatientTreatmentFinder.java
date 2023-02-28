package gov.cdc.nbs.patient.treatment;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

@Component
class PatientTreatmentFinder {

    public static final int PARAMETER_PARAMETER = 1;
    private static final String QUERY = """
            select distinct
                [treatment].treatment_uid  "treatment",
                [relationship].add_time as "created_on",
                [treatment].cd_desc_txt "description",
                [treatment].local_id "event",
                [administered].EFFECTIVE_FROM_TIME "treated_on",
                [provider_name].[nm_prefix] "provider_prefix",
                [provider_name].[first_nm] "provider_first_name",
                [provider_name].[last_nm] "provider_last_name",
                [provider_name].[nm_suffix] "provider_suffix",
                [investigation].public_health_case_uid "investigation_id",
                [investigation].local_id "investigation_local",
                [condition].condition_short_nm "investigation_condition"
            from  person [patient]
                        
                join participation [subject_of_treatment] on
                        [subject_of_treatment].subject_entity_uid = [patient].person_uid
                    and [subject_of_treatment].type_cd = 'SubjOfTrmt'
                    and [subject_of_treatment].ACT_CLASS_CD = 'TRMT'
                    and [subject_of_treatment].SUBJECT_CLASS_CD = 'PSN'
                    and [subject_of_treatment].record_status_cd = 'ACTIVE'
                        
                join treatment  on
                        [treatment].treatment_uid = [subject_of_treatment].act_uid
                    and [treatment].record_status_cd='ACTIVE'
                   
                        
                join treatment_administered [administered] on
                        
                        [treatment].treatment_uid = [administered].treatment_uid
                        
                join act_relationship [relationship]  on
                        [relationship].type_cd='TreatmentToPHC'
                    and [relationship].source_act_uid = [treatment].treatment_uid
                    and [relationship].source_class_cd = 'TRMT'
                    and [relationship].target_class_cd = 'CASE'
                        
                left join Participation [treatment_provider] on
                        [treatment_provider].act_uid = [treatment].[treatment_uid]
                    and [treatment_provider].type_cd = 'ProviderOfTrmt'
                    and [treatment_provider].subject_class_cd = 'PSN'
                        
                left join person_name [provider_name] on
                        [provider_name].person_uid = [treatment_provider].[subject_entity_uid]
                        
                join Public_health_case [investigation] ON
                        [investigation].Public_health_case_uid = [relationship].target_act_uid
                    and [investigation].investigation_status_cd IN ( 'O','C')
                    and [investigation].record_status_cd <> 'LOG_DEL'
                        
                join nbs_srte..Condition_code [condition] ON
                    [condition].condition_cd = [investigation].cd
                        
            where [patient].person_parent_uid = ?
            """;

    private final JdbcTemplate template;
    private final PatientTreatmentResultSetExtractor extractor;

    PatientTreatmentFinder(final DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);

        // the label mapped to fields returned by the query
        PatientTreatmentRowMapper.Label label = new PatientTreatmentRowMapper.Label(
                "treatment",
                "created_on",
                "description",
                "event",
                "treated_on",
                new PatientTreatmentProviderRowMapper.Label(
                        "provider_prefix",
                        "provider_first_name",
                        "provider_last_name",
                        "provider_suffix"
                ),
                "investigation_id",
                "investigation_local",
                "investigation_condition"
        );

        this.extractor = new PatientTreatmentResultSetExtractor(new PatientTreatmentRowMapper(label));
    }

    List<PatientTreatment> find(final long patient) {
        return this.template.query(
                QUERY,
                statement -> statement.setLong(PARAMETER_PARAMETER, patient),
                this.extractor
        );
    }
}
