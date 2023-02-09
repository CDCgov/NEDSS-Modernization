package gov.cdc.nbs.message;

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
    private Long patientId;
    private String patientLocalId;
    private String username;
    private PatientInput patientInput;
}
