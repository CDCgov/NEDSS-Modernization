package gov.cdc.nbs.message;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class PatientUpdateRequest {
    private String requestId;
    private PatientUpdateParams params;

    public PatientUpdateRequest(String requestId, PatientUpdateParams params) {
        this.requestId = requestId;
        this.params = params;
    }

}
