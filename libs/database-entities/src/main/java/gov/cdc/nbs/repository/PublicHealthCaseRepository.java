package gov.cdc.nbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import gov.cdc.nbs.entity.odse.PublicHealthCase;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import gov.cdc.nbs.entity.projections.AssociatedInvestigation2;

public interface PublicHealthCaseRepository
    extends JpaRepository<PublicHealthCase, Long>, QuerydslPredicateExecutor<PublicHealthCase> {
  @Query(value = """
              SELECT
              phc.public_health_case_uid publicHealthCaseUid,
              phc.last_chg_time lastChgTime,
              phc.cd_desc_txt cdDescTxt,
              phc.local_id localId,
              ar.last_chg_time getActRelationshipLastChgTime
        FROM
          Public_health_case phc
          JOIN Act_relationship ar ON ar.target_act_uid = phc.public_health_case_uid
        WHERE
          phc.public_health_case_uid in (
            SELECT
              ar.target_act_uid
            FROM
              Act_relationship ar
            WHERE
              ar.source_act_uid = :observationUid
              AND source_class_cd = 'OBS'
              AND target_class_cd = 'CASE'
          )
      """, nativeQuery = true)
  List<AssociatedInvestigation2> findAllInvestigationsByObservationUid(
      @Param("observationUid") Long observationUid);
}
