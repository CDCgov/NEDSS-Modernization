package gov.cdc.nbs.message.patient.input;

import java.time.Instant;
import gov.cdc.nbs.message.patient.event.PatientRequest;
import gov.cdc.nbs.message.patient.event.UpdatePhoneData;
import gov.cdc.nbs.message.patient.input.PatientInput.PhoneType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PhoneInput {
        private long patientId;
        private short id;
        private String number;
        private String extension;
        private PhoneType phoneType;

        public static PatientRequest toAddRequest(
                        final long userId,
                        final String requestId,
                        final PhoneInput input) {
                return new PatientRequest.AddPhone(
                                requestId,
                                input.getPatientId(),
                                userId,
                                new UpdatePhoneData(
                                                input.getPatientId(),
                                                input.getId(),
                                                requestId,
                                                userId,
                                                Instant.now(),
                                                input.getNumber(),
                                                input.getExtension(),
                                                input.getPhoneType()));
        }

        public static PatientRequest toUpdateRequest(
                        final long userId,
                        final String requestId,
                        final PhoneInput input) {
                return new PatientRequest.UpdatePhone(
                                requestId,
                                input.getPatientId(),
                                userId,
                                new UpdatePhoneData(
                                                input.getPatientId(),
                                                input.getId(),
                                                requestId,
                                                userId,
                                                Instant.now(),
                                                input.getNumber(),
                                                input.getExtension(),
                                                input.getPhoneType()));
        }
}
