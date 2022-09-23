package com.enquizit.nbs.service;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
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
        var exampleMatcher = ExampleMatcher.matchingAll().withIgnoreCase()
                .withMatcher("firstNm", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("lastNm", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
        var example = Example.of(getExampleFromFilter(filter), exampleMatcher);
        return patientRepository.findAll(example, pageable);
    }

    private Patient getExampleFromFilter(PatientFilter filter) {
        var patient = new Patient();
        patient.setPersonUid(filter.getId());
        patient.setLastNm(filter.getLastName());
        patient.setFirstNm(filter.getFirstName());
        patient.setSsn(filter.getSsn());
        patient.setHmPhoneNbr(filter.getPhoneNumber());
        patient.setBirthTime(filter.getDateOfBirth());
        patient.setBirthGenderCd(filter.getGender());
        patient.setHmStreetAddr1(filter.getAddress());
        return patient;
    }

}