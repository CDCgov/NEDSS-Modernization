package gov.cdc.nbs.message.patient.input;

import java.time.Instant;
import gov.cdc.nbs.message.patient.event.PatientRequest;
import gov.cdc.nbs.message.patient.event.UpdateEmailData;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmailInput {
        private long patientId;
        private short id;
        private String emailAddress;

        public static PatientRequest toAddRequest(
                        final long userId,
                        final String requestId,
                        final EmailInput input) {
                return new PatientRequest.AddEmail(
                                requestId,
                                input.getPatientId(),
                                userId,
                                new UpdateEmailData(
                                                input.getPatientId(),
                                                input.getId(),
                                                requestId,
                                                userId,
                                                Instant.now(),
                                                input.getEmailAddress()));
        }

        public static PatientRequest toUpdateRequest(
                        final long userId,
                        final String requestId,
                        final EmailInput input) {
                return new PatientRequest.UpdateEmail(
                                requestId,
                                input.getPatientId(),
                                userId,
                                new UpdateEmailData(
                                                input.getPatientId(),
                                                input.getId(),
                                                requestId,
                                                userId,
                                                Instant.now(),
                                                input.getEmailAddress()));
        }
}
