package gov.cdc.nbs.patient;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class KeyValuePairResults {
    private List<KeyValuePair> content = new ArrayList<>();
    private int total = 0;
}
