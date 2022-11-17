package gov.cdc.nbs.graphql.input;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import gov.cdc.nbs.entity.enums.Deceased;
import gov.cdc.nbs.entity.enums.Ethnicity;
import gov.cdc.nbs.entity.enums.Gender;
import gov.cdc.nbs.entity.enums.Race;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PatientInput {
    private Name name;
    private String ssn;
    private Instant dateOfBirth;
    private Gender birthGender;
    private Gender currentGender;
    private Deceased deceased;
    private List<PostalAddress> addresses = new ArrayList<>();
    private List<PhoneNumber> phoneNumbers = new ArrayList<>();
    private List<String> emailAddresses = new ArrayList<>();
    private Ethnicity ethnicity;
    private Race race;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Name {
        private String firstName;
        private String middleName;
        private String lastName;
        private Suffix suffix;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PostalAddress {
        private String streetAddress1;
        private String streetAddress2;
        private String city;
        private String stateCode;
        private String countyCode;
        private String countryCode;
        private String zip;
        private String censusTract;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PhoneNumber {
        private String phoneNumber;
        private String extension;
        private PhoneType phoneType;
    }

    public enum Suffix {
        ESQ,
        II,
        III,
        IV,
        JR,
        SR,
        V
    }

    public enum PhoneType {
        HOME,
        CELL,
        WORK
    }

}
