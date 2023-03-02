package gov.cdc.nbs.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import gov.cdc.nbs.entity.odse.Participation;

public interface ParticipationRepository
                extends JpaRepository<Participation, Long>, QuerydslPredicateExecutor<Participation> {

        Optional<Participation> findByIdSubjectEntityUidAndIdActUidAndIdTypeCd(Long subjectEntityuid, Long actUid,
                        String typeCd);
        
        @Query("select actUid from  Participation where subjectEntityUid in (?1) AND typeCd = '?2'")
        List<Long> getActIdsBySubjectEntityUids(List<Long> subjectEntityIds, String reportType);
}
