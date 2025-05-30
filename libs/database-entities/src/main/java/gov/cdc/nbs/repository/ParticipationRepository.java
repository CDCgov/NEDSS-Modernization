package gov.cdc.nbs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import gov.cdc.nbs.entity.odse.Participation;
import gov.cdc.nbs.entity.projections.MaterialParticipation2;
import gov.cdc.nbs.entity.projections.OrganizationParticipation2;

public interface ParticipationRepository
                extends JpaRepository<Participation, Long>, QuerydslPredicateExecutor<Participation> {

        @Query(value = """
                        SELECT
                        p.act_uid actUid,
                        p.type_cd typeCd,
                        p.subject_entity_uid entityId,
                        p.subject_class_cd subjectClassCd,
                        p.record_status_cd recordStatus,
                        p.type_desc_txt typeDescTxt,
                        p.last_chg_time lastChgTime,
                        m.cd AS cd,
                        m.cd_desc_txt AS cdDescTxt
                                      FROM
                                          participation p
                                      JOIN material m ON m.material_uid = p.subject_entity_uid
                                      WHERE
                                          p.act_uid = :observationUid
                                          """, nativeQuery = true)
        List<MaterialParticipation2> findAllMaterialParticipationsByObservationUid(
                        @Param("observationUid") Long observationUid);

        @Query(value = """
                        SELECT
                        p.act_uid actUid,
                        p.type_cd typeCd,
                        p.subject_entity_uid entityId,
                        p.subject_class_cd subjectClassCd,
                        p.record_status_cd recordStatus,
                        p.type_desc_txt typeDescTxt,
                        p.last_chg_time lastChgTime,
                        org.display_nm name,
                        org.last_chg_time orgLastChangeTime
                                FROM
                                  participation p
                                  JOIN organization org ON org.organization_uid = p.subject_entity_uid
                                WHERE
                                  p.act_uid = :observationUid
                                              """, nativeQuery = true)
        List<OrganizationParticipation2> findAllOrganizationParticipationsByObservationUid(
                        @Param("observationUid") Long observationUid);
}
