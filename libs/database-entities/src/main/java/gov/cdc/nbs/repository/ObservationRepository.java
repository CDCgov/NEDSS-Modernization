package gov.cdc.nbs.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import gov.cdc.nbs.entity.odse.Observation;

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
                    """, nativeQuery = true)
    List<Observation> findAllObservationsAssociatedWithAnObservation(
            @Param("observationUid") Long observationUid);
}
