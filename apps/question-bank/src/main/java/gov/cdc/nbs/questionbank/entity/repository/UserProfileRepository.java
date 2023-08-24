package gov.cdc.nbs.questionbank.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.cdc.nbs.questionbank.entity.UserProfile;



public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

}
