package gov.cdc.nbs.message;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class PatientUpdateEvent {
    private String requestId;
    private PatientUpdateParams params;
}
