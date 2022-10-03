package gov.cdc.nbs.graphql;

import java.time.Instant;

import gov.cdc.nbs.entity.enums.Gender;
import gov.cdc.nbs.entity.enums.Deceased;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientInput {
    private String lastName;
    private String firstName;
    private String ssn;
    private String phoneNumber;
    private Instant DateOfBirth;
    private Gender gender;
    private Deceased deceased;
    private String address;
    private String city;
    private String state;
    private String country;
    private String zip;
    private String ethnicity;
}
