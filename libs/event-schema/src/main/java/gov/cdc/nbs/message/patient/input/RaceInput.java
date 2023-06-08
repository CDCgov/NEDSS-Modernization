package gov.cdc.nbs.message.patient.input;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
public class RaceInput {
    private long patientId;
    private Instant asOf;
    private String raceCd;
    private String raceCategoryCd;

}
