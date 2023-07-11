package gov.cdc.nbs.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import gov.cdc.nbs.entity.odse.Investigation;

public interface InvestigationRepository
    extends JpaRepository<Investigation, Long>, QuerydslPredicateExecutor<Investigation> {
  @Query("""
        SELECT
          phc.public_health_case_uid,
          phc.last_chg_time,
          phc.cd_desc_txt,
          phc.local_id,
          ar.last_chg_time AS act_relationship_last_change_time
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
      """)
  List<Investigation> findAllInvestigationsByObservationUid(
      @Param("observationUid") Long observationUid);
}
