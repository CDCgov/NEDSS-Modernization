package gov.cdc.nbs.message.patient.input;

import java.time.Instant;
import gov.cdc.nbs.message.patient.event.PatientRequest;
import gov.cdc.nbs.message.patient.event.UpdateAddressData;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddressInput {
        long patientId;
        short id;
        String streetAddress1;
        String streetAddress2;
        String city;
        String stateCode;
        String countyCode;
        String countryCode;
        String zip;
        String censusTract;

        public static PatientRequest toAddRequest(
                        final long userId,
                        final String requestId,
                        final AddressInput input) {
                return new PatientRequest.AddAddress(
                                requestId,
                                input.getPatientId(),
                                userId,
                                new UpdateAddressData(
                                                input.getPatientId(),
                                                input.getId(),
                                                requestId,
                                                userId,
                                                Instant.now(),
                                                input.getStreetAddress1(),
                                                input.getStreetAddress2(),
                                                input.getCity(),
                                                input.getStateCode(),
                                                input.getCountyCode(),
                                                input.getCountryCode(),
                                                input.getZip(),
                                                input.getCensusTract()));
        }

        public static PatientRequest toUpdateRequest(
                        final long userId,
                        final String requestId,
                        final AddressInput input) {
                return new PatientRequest.UpdateAddress(
                                requestId,
                                input.getPatientId(),
                                userId,
                                new UpdateAddressData(
                                                input.getPatientId(),
                                                input.getId(),
                                                requestId,
                                                userId,
                                                Instant.now(),
                                                input.getStreetAddress1(),
                                                input.getStreetAddress2(),
                                                input.getCity(),
                                                input.getStateCode(),
                                                input.getCountyCode(),
                                                input.getCountryCode(),
                                                input.getZip(),
                                                input.getCensusTract()));
        }
}
