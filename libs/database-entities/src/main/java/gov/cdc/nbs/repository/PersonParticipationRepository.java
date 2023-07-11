package gov.cdc.nbs.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import gov.cdc.nbs.entity.odse.PersonParticipation;

@Repository
public interface PersonParticipationRepository
    extends JpaRepository<PersonParticipation, Long>, QuerydslPredicateExecutor<PersonParticipation> {
  @Query("""
      SELECT
        p.act_uid,
        p.type_cd,
        p.subject_entity_uid AS entity_id,
        p.subject_class_cd,
        p.record_status_cd AS participation_record_status,
        p.last_chg_time AS participation_last_change_time,
        p.type_desc_txt,
        person.first_nm AS [first_name],
        person.last_nm AS [last_name],
        person.local_id,
        person.birth_time,
        person.curr_sex_cd,
        person.cd AS person_cd,
        person.person_parent_uid,
        person.record_status_cd AS person_record_status,
        person.last_chg_time AS person_last_chg_time
      FROM
        participation p
        JOIN person ON person.person_uid = (
          select
            person.person_parent_uid
          from
            person
          where
            person.person_uid = p.subject_entity_uid
        )
      WHERE
        p.act_uid = :observationUid
                    """)
  List<PersonParticipation> findAllPersonParticipationsByObservationUid(
      @Param("observationUid") Long observationUid);
}
