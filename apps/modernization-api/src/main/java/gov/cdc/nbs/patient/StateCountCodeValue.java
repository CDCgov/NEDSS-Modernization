package gov.cdc.nbs.patient;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StateCountCodeValue {
    private String code;
    private String code_desc_txt;
    private String state_cd;
}
