package gov.cdc.nbs.message;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import gov.cdc.nbs.entity.enums.Deceased;
import gov.cdc.nbs.entity.enums.Gender;
import gov.cdc.nbs.entity.enums.Suffix;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PatientCreateRequest {
    private String requestId;
    private String userId;
    private PatientInput patientInput;

    @Data
    @NoArgsConstructor
    public static class PatientInput {
        private Name name;
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

    }

}
