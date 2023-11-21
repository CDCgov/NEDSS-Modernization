package gov.cdc.nbs.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import gov.cdc.nbs.entity.odse.Observation;
import gov.cdc.nbs.entity.projections.LabReport2;
import gov.cdc.nbs.entity.projections.Observation2;

public interface ObservationRepository
        extends JpaRepository<Observation, Long>, QuerydslPredicateExecutor<Observation> {
    @Query(value = """
             SELECT
                o2.cd,
                o2.cd_desc_txt,
                o2.obs_domain_cd_st_1,
                o2.status_cd,
                o2.alt_cd,
                o2.alt_cd_desc_txt,
                o2.alt_cd_system_cd,
                ovc.display_name,
                ovc.code AS ovc_code,
                ovc.alt_cd AS ovc_alt_cd,
                ovc.alt_cd_desc_txt AS ovc_alt_cd_desc_txt,
                ovc.alt_cd_system_cd AS ovc_alt_cd_system_cd
            FROM
                observation o2
                LEFT JOIN Obs_value_coded ovc ON ovc.observation_uid = o2.observation_uid
            WHERE
                o2.observation_uid in(
                SELECT
                    ar.source_act_uid
                FROM
                    act_relationship ar
                WHERE
                    ar.target_act_uid = :observationUid
                )
                    """, nativeQuery = true)
    List<Observation2> findAllObservationsAssociatedWithAnObservation(
            @Param("observationUid") Long observationUid);

    @Query(value = """
                SELECT
                o.observation_uid id,
                act.class_cd classCd,
                act.mood_cd moodCd,
                act.act_uid actUid,
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
                LEFT JOIN nbs_srte.dbo.jurisdiction_code jc ON o.jurisdiction_cd = jc.code
                AND o.obs_domain_cd_st_1 = 'Result'
                and o.record_status_cd in ('PROCESSED', 'UNPROCESSED')
                and o.ctrl_cd_display_form = 'LabReport'
            WHERE
                p.person_parent_uid = :personUid
                            """, nativeQuery = true)

    List<LabReport2> findAllLabReportsByPersonUid(
            @Param("personUid") Long personUid);
}
