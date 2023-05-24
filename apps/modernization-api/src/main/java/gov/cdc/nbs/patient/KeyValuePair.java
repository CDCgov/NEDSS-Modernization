package gov.cdc.nbs.patient;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KeyValuePair {
    private String key = "";
    private String value = "";
}
