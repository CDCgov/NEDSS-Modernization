package gov.cdc.nbs.message.patient.event;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class PatientDeleteEvent {
    private String requestId;


    public PatientDeleteEvent(String requestId) {
        this.requestId = requestId;
    }

}
