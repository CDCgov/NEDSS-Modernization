package gov.cdc.nbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SyphilisQuestionBankRepository extends JpaRepository<Questions, Long> {
}