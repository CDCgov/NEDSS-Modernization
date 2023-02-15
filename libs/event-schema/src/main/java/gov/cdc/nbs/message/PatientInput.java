package gov.cdc.nbs.message;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import gov.cdc.nbs.entity.enums.Deceased;
import gov.cdc.nbs.entity.enums.Gender;
import gov.cdc.nbs.entity.enums.Suffix;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PatientInput {
    private List<Name> names;
    private String ssn;
    private Instant dateOfBirth;
    private Gender birthGender;
    private Gender currentGender;
    private Deceased deceased;
    private Instant deceasedTime;
    private String maritalStatus;
    private List<PostalAddress> addresses = new ArrayList<>();
    private List<PhoneNumber> phoneNumbers = new ArrayList<>();
    private List<String> emailAddresses = new ArrayList<>();
    private List<Identification> identifications = new ArrayList<>();
    private String ethnicityCode;
    private List<String> raceCodes;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Name {
        private String firstName;
        private String middleName;
        private String lastName;
        private Suffix suffix;
        private NameUseCd nameUseCd;
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
        private String number;
        private String extension;
        private PhoneType phoneType;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Identification {
        private String identificationNumber;
        private String assigningAuthority;
        private String identificationType;
    }

    public enum PhoneType {
        HOME,
        CELL,
        WORK
    }

    public enum NameUseCd {
        AD, // Adopted Name
        AL, // Alias Name
        A, // Artist/Stage Name
        S, // Coded Pseudo
        I, // Indigenous/Tribal
        L, // Legal
        C, // License
        M, // Maiden Name
        MO, // Mother's Name
        BR, // Name at Birth
        P, // Name of Partner/Spouse
        R, // Religious
        U
    }

}