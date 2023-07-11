package gov.cdc.nbs.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import gov.cdc.nbs.entity.odse.OrganizationParticipation;

@Repository
public interface OrganizationParticipationRepository
    extends JpaRepository<OrganizationParticipation, Long>, QuerydslPredicateExecutor<OrganizationParticipation> {
  @Query("""
      SELECT
        p.act_uid,
        p.type_cd,
        p.subject_entity_uid AS entity_id,
        p.subject_class_cd,
        p.record_status_cd AS record_status,
        p.type_desc_txt,
        p.last_chg_time,
        org.display_nm AS name,
        org.last_chg_time AS org_last_change_time
      FROM
        participation p
        JOIN organization org ON org.organization_uid = p.subject_entity_uid
      WHERE
        p.act_uid = :observationUid
                    """)
  List<OrganizationParticipation> findAllOrganizationParticipationsByObservationUid(
      @Param("observationUid") Long observationUid);
}
