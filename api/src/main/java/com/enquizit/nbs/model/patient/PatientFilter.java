package com.enquizit.nbs.model.patient;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientFilter {
    private int pageNumber;
    private int pageSize;
    private Long id;
    private String lastName;
    private String firstName;
    private String ssn;
    private String phoneNumber;
    private LocalDateTime dateOfBirth;
    private String dateOfBirthOperator;
    private String gender;
    private String disease;
    private String address;
    private String city;
    private String state;
    private String country;
    private String zip;
    private String mortalityStatus;
    private String ethnicity;
    private String recordStatus;
}
