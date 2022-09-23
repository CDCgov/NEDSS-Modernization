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
    private Deceased deceased;
    private String address;
    private String city;
    private String state;
    private String country;
    private String zip;
    private String mortalityStatus;
    private String ethnicity;
    private String recordStatus;

    public String getDeceasedDataValue() {
        switch (this.getDeceased()) {
            case YES:
                return "Y";
            case NO:
                return "N";
            case UNKNOWN:
                return "UNK";
            default:
                throw new IllegalArgumentException("Invalid Deceased value provided: " + this.getDeceased());
        }
    }

    public static enum Deceased {
        YES,
        NO,
        UNKNOWN
    }
}
