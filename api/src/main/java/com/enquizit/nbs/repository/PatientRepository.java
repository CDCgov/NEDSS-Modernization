package com.enquizit.nbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.enquizit.nbs.model.patient.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
        @Query("SELECT coalesce(max(p.personUid), 0) FROM Patient p")
        Long getMaxId();

}