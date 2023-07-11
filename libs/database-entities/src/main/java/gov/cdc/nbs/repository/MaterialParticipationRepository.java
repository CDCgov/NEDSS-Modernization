package gov.cdc.nbs.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import gov.cdc.nbs.entity.odse.Act;
import gov.cdc.nbs.entity.odse.MaterialParticipation;

public interface MaterialParticipationRepository
        extends JpaRepository<MaterialParticipation, Long>, QuerydslPredicateExecutor<Act> {
    @Query("""
            SELECT
                p.act_uid,
                p.type_cd,
                p.subject_entity_uid AS entity_id,
                p.subject_class_cd,
                p.record_status_cd AS record_status,
                p.type_desc_txt,
                p.last_chg_time,
                m.cd AS material_cd,
                m.cd_desc_txt AS material_cd_desc_txt
            FROM
                participation p
            JOIN material m ON m.material_uid = p.subject_entity_uid
            WHERE
                p.act_uid = :observationUid
                """)
    List<MaterialParticipation> findAllMaterialParticipationsByObservationUid(
            @Param("observationUid") Long observationUid);
}
