package gov.cdc.nbs.questionbank.entity.repository;

import gov.cdc.nbs.questionbank.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {}
