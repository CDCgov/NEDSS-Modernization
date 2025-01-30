package gov.cdc.nbs.message.patient.input;

import gov.cdc.nbs.message.enums.Deceased;
import gov.cdc.nbs.message.enums.Gender;
import gov.cdc.nbs.message.enums.Suffix;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class PatientInput {
    private List<Name> names = new ArrayList<>();
    private LocalDate dateOfBirth;
    private Gender birthGender;
    private Gender currentGender;
    private Deceased deceased;
    private LocalDate deceasedTime;
    private String maritalStatus;
    private String stateHIVCase;
    private List<PostalAddress> addresses = new ArrayList<>();
    private List<PhoneNumber> phoneNumbers = new ArrayList<>();
    private List<String> emailAddresses = new ArrayList<>();
    private List<Identification> identifications = new ArrayList<>();
    private String ethnicity;
    private List<String> races = new ArrayList<>();
    private LocalDate asOf;
    private String comments;


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Name {
        private String first;
        private String middle;
        private String last;
        private Suffix suffix;
        private NameUseCd use;
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PostalAddress {
        private String streetAddress1;
        private String streetAddress2;
        private String city;
        private String state;
        private String county;
        private String country;
        private String zip;
        private String censusTract;
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PhoneNumber {
        private String number;
        private String extension;
        private String type;
        private String use;
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Identification {
        private String value;
        private String authority;
        private String type;
    }


    public enum PhoneType {
        HOME("PH", "H"),
        CELL("CP", "MC"),
        WORK("PH", "WP");

        private final String type;
        private final String use;

        PhoneType(final String type, final String use) {
            this.type = type;
            this.use = use;
        }

        public String type() {
            return type;
        }

        public String use() {
            return use;
        }
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
