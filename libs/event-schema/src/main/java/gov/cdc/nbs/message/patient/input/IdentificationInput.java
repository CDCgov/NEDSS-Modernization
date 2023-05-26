package gov.cdc.nbs.message.patient.input;

import java.time.Instant;
import gov.cdc.nbs.message.patient.event.PatientRequest;
import gov.cdc.nbs.message.patient.event.UpdateIdentificationData;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IdentificationInput {
        long patientId;
        short id;
        private String identificationNumber;
        private String assigningAuthority;
        private String identificationType;

        public static PatientRequest toAddRequest(
                        final long userId,
                        final String requestId,
                        final IdentificationInput input) {
                return new PatientRequest.AddIdentification(
                                requestId,
                                input.getPatientId(),
                                userId,
                                new UpdateIdentificationData(
                                                input.getPatientId(),
                                                input.getId(),
                                                requestId,
                                                userId,
                                                Instant.now(),
                                                input.getIdentificationNumber(),
                                                input.getAssigningAuthority(),
                                                input.getIdentificationType()));
        }

        public static PatientRequest toUpdateRequest(
                        final long userId,
                        final String requestId,
                        final IdentificationInput input) {
                return new PatientRequest.UpdateIdentification(
                                requestId,
                                input.getPatientId(),
                                userId,
                                new UpdateIdentificationData(
                                                input.getPatientId(),
                                                input.getId(),
                                                requestId,
                                                userId,
                                                Instant.now(),
                                                input.getIdentificationNumber(),
                                                input.getAssigningAuthority(),
                                                input.getIdentificationType()));
        }
}
