package gov.cdc.nbs.message.patient.input;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class EthnicityInput {
    private long patient;
    private Instant asOf;
    private String ethnicGroup;
    private String unknownReason;
    private List<String> detailed = new ArrayList<>();

}
