package gov.cdc.nbs.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import gov.cdc.nbs.entity.odse.Participation;

public interface ParticipationRepository
                extends JpaRepository<Participation, Long>, QuerydslPredicateExecutor<Participation> {

	Optional<Participation> findByIdSubjectEntityUidAndIdActUidAndIdTypeCd(Long subjectEntityuid, Long actUid,
			String typeCd);

	@Query(value="select p.act_uid from  Participation p where p.type_cd =:typeCd AND p.subject_entity_uid in (:subjectEntityUid)", nativeQuery=true)
	List<Long> findIdActUidByIdTypeCdAndIdSubjectEntityUidIn(@Param("typeCd")String typeCd,  @Param("subjectEntityUid")List<Long> subjectEntityUid);
}
