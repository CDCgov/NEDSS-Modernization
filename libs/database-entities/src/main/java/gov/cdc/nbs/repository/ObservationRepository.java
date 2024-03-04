package gov.cdc.nbs.repository;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import gov.cdc.nbs.entity.odse.Observation;
import gov.cdc.nbs.entity.projections.PatientLabReport;
import gov.cdc.nbs.entity.projections.Observation2;

public interface ObservationRepository
        extends JpaRepository<Observation, Long>, QuerydslPredicateExecutor<Observation> {

    @Query(value = """
                SELECT
                o.observation_uid id,
                act.class_cd classCd,
                act.mood_cd moodCd,
                act.act_uid actUid,
                o.last_chg_time lastChange,
                o.last_chg_time observationLastChgTime,
                o.observation_uid observationUid,
                o.cd_desc_txt cdDescTxt,
                o.record_status_cd recordStatusCd,
                o.program_jurisdiction_oid programJurisdictionOid,
                o.prog_area_cd programAreaCd,
                o.jurisdiction_cd jurisdictionCd,
                jc.code_desc_txt  jurisdictionCodeDescTxt,
                o.pregnant_ind_cd pregnantIndCd,
                o.local_id localId,
                o.activity_to_time activityToTime,
                o.effective_from_time effectiveFromTime,
                o.rpt_to_state_time rptToStateTime,
                o.add_time addTime,
                o.electronic_ind electronicInd,
                o.version_ctrl_nbr versionCtrlNbr,
                o.add_user_id addUserId,
                o.last_chg_user_id lastChgUserId
            FROM observation o
                JOIN act on o.observation_uid =act.act_uid
                JOIN Participation par on par.act_uid = o.observation_uid
                JOIN person p on p.person_uid = par.subject_entity_uid
                JOIN nbs_srte.dbo.jurisdiction_code jc ON o.jurisdiction_cd = jc.code
                and o.record_status_cd in ('PROCESSED', 'UNPROCESSED')
                and o.ctrl_cd_display_form = 'LabReport'
            WHERE
                p.person_parent_uid = :personUid
                ORDER BY id
                            """,
            countQuery = """
                    SELECT COUNT(*)
                    FROM observation o
                        JOIN act on o.observation_uid =act.act_uid
                        JOIN Participation par on par.act_uid = o.observation_uid
                        JOIN person p on p.person_uid = par.subject_entity_uid
                        LEFT JOIN nbs_srte.dbo.jurisdiction_code jc ON o.jurisdiction_cd = jc.code
                        AND o.obs_domain_cd_st_1 = 'Result'
                        and o.record_status_cd in ('PROCESSED', 'UNPROCESSED')
                        and o.ctrl_cd_display_form = 'LabReport'
                    WHERE
                        p.person_parent_uid = :personUid
                                    """,
            nativeQuery = true)
    Page<PatientLabReport> findLabReportsForPatient(
            @Param("personUid") Long personUid,
            Pageable pageable);
}
