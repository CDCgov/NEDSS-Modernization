package gov.cdc.nbs.entity.elasticsearch;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NestedName {
    private String firstNm;
    private String firstNmSndx;
    private String lastNm;
    private String lastNmSndx;
    private String middleNm;
    private String nmPrefix;
    private String nmSuffix;
}
