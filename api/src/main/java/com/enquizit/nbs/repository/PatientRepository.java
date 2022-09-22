package com.enquizit.nbs.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.enquizit.nbs.model.patient.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
        @Query("SELECT coalesce(max(p.personUid), 0) FROM Patient p")
        Long getMaxId();

        // Query fails if you use '%:paramerter%', works with CONCAT call
        @Query("SELECT p from Patient p WHERE " +
                        "(:id IS NULL OR p.personUid = :id) AND " +
                        "(:lastName IS NULL OR p.lastNm LIKE CONCAT('%', :lastName, '%')) AND " +
                        "(:firstName IS NULL OR p.firstNm LIKE CONCAT('%', :firstName, '%')) AND " +
                        "(:ssn IS NULL OR p.ssn = :ssn) AND " +
                        "(:phoneNumber IS NULL OR p.hmPhoneNbr = :phoneNumber) AND " +
                        "(:phoneNumber IS NULL OR p.wkPhoneNbr = :phoneNumber) AND " +
                        "(:phoneNumber IS NULL OR p.cellPhoneNbr = :phoneNumber) AND " +
                        "(:dob IS NULL OR p.birthTime = :dob) AND " +
                        "(:gender IS NULL OR p.administrativeGenderCd = :gender) AND " +
                        "(:gender IS NULL OR p.birthGenderCd = :gender) AND " +
                        "(:gender IS NULL OR p.preferredGenderCd = :gender) AND " +
                        "(:gender IS NULL OR p.additionalGenderCd = :gender) AND " +
                        "(:address IS NULL OR p.hmStreetAddr1 = :address) AND " +
                        "(:address IS NULL OR p.hmStreetAddr2 = :address) AND " +
                        "(:address IS NULL OR p.wkStreetAddr1 = :address) AND " +
                        "(:address IS NULL OR p.wkStreetAddr2 = :address) AND " +
                        "(:city IS NULL OR p.hmCityCd = :city) AND " +
                        "(:city IS NULL OR p.wkCityCd = :city) AND " +
                        "(:state IS NULL OR p.hmStateCd = :state) AND " +
                        "(:state IS NULL OR p.wkStateCd = :state) AND " +
                        "(:state IS NULL OR p.birthStateCd = :state) AND " +
                        "(:country IS NULL OR p.hmCntryCd = :country) AND " +
                        "(:country IS NULL OR p.wkCntryCd = :country) AND " +
                        "(:country IS NULL OR p.birthCntryCd = :country) AND " +
                        "(:zip IS NULL OR p.hmZipCd = :zip) AND " +
                        "(:zip IS NULL OR p.wkZipCd = :zip) AND " +
                        "(:ethnicity IS NULL OR p.ethnicityGroupCd = :ethnicity) AND " +
                        "(:recordStatus IS NULL OR p.recordStatusCd = :recordStatus)")
        Page<Patient> findByFilter(
                        @Param("id") Long id,
                        @Param("lastName") String lastName,
                        @Param("firstName") String firstName,
                        @Param("ssn") String ssn,
                        @Param("phoneNumber") String phoneNumber,
                        @Param("dob") LocalDateTime dateOfBirth,
                        @Param("gender") String gender,
                        @Param("address") String address,
                        @Param("city") String city,
                        @Param("state") String state,
                        @Param("country") String country,
                        @Param("zip") String zip,
                        @Param("ethnicity") String ethnicity,
                        @Param("recordStatus") String recordStatus,
                        Pageable pageable);

}