package gov.cdc.nbs.message.patient.input;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class RaceInput {
    private long patient;
    private Instant asOf;
    private String category;
    private List<String> detailed = new ArrayList<>();

}
