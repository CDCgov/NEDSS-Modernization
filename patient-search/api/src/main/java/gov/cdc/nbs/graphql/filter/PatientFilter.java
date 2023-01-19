package gov.cdc.nbs.graphql.filter;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import gov.cdc.nbs.entity.enums.Deceased;
import gov.cdc.nbs.entity.enums.Ethnicity;
import gov.cdc.nbs.entity.enums.Gender;
import gov.cdc.nbs.entity.enums.IdentificationType;
import gov.cdc.nbs.entity.enums.Race;
import gov.cdc.nbs.entity.enums.RecordStatus;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@JsonInclude(Include.NON_NULL)
public class PatientFilter {
    private Long id;
    private String lastName;
    private String firstName;
    private Race race;
    private Identification identification;
    private String ssn;
    private String phoneNumber;
    private String email;
    private Instant dateOfBirth;
    private String dateOfBirthOperator;
    private Gender gender;
    private Deceased deceased;
    private String address;
    private String city;
    private String state;
    private String country;
    private String zip;
    private String mortalityStatus;
    private Ethnicity ethnicity;
    private RecordStatus recordStatus;
    private String treatmentId;
    private String vaccinationId;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Identification {
        private String identificationNumber;
        private IdentificationType identificationType;
    }
}
