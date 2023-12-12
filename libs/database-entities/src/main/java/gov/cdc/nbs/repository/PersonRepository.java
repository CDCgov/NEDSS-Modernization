package gov.cdc.nbs.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.entity.projections.PersonParticipation2;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>, QuerydslPredicateExecutor<Person> {
  @Query("SELECT coalesce(max(p.id), 0) FROM Person p")
  Long getMaxId();

  Page<Person> findByIdIn(List<Long> ids, Pageable pageable);

  @Query(value = "SELECT p.person_uid from Person p where p.person_parent_uid=:personParentUid", nativeQuery = true)
  List<Long> getPersonIdsByPersonParentId(@Param("personParentUid") Long personParentUid);

  @Query(value = """
        SELECT
        p.act_uid actUid,
        p.type_cd typeCd,
        p.subject_entity_uid entityId,
        p.subject_class_cd subjectClassCd,
        p.record_status_cd recordStatus,
        p.last_chg_time lastChgTime,
        p.type_desc_txt typeDescTxt,
        person.first_nm firstNm,
        person.last_nm lastNm,
        person.local_id localId,
        person.birth_time birthTime,
        person.curr_sex_cd currSexCd,
        person.cd AS person_cd personCd,
        person.person_parent_uid personParentUid,
        person.record_status_cd personRecordStatus,
        person.last_chg_time personLastChgTime
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
                    """, nativeQuery = true)
  List<PersonParticipation2> findAllPersonParticipationsByObservationUid(
      @Param("observationUid") Long observationUid);
}
