package com.enquizit.nbs.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.enquizit.nbs.model.patient.Patient;
import com.enquizit.nbs.model.patient.PatientFilter;
import com.enquizit.nbs.repository.PatientRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PatientService {
    private final int MAX_PAGE_SIZE = 50;

    private final PatientRepository patientRepository;

    public Page<Patient> findPatientsByFilter(PatientFilter filter) {
        var pageable = PageRequest.of(filter.getPageNumber(),
                filter.getPageSize() == 0 ? MAX_PAGE_SIZE : Math.min(filter.getPageSize(), MAX_PAGE_SIZE));
        return patientRepository.findByFilter(filter.getId(),
                filter.getLastName(),
                filter.getFirstName(),
                filter.getSsn(),
                filter.getPhoneNumber(),
                filter.getDateOfBirth(),
                filter.getGender(),
                filter.getAddress(),
                filter.getCity(),
                filter.getState(),
                filter.getCountry(),
                filter.getZip(),
                filter.getEthnicity(),
                filter.getRecordStatus(),
                pageable);
    }

}