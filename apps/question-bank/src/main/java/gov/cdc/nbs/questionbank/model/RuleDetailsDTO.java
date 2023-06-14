package gov.cdc.nbs.questionbank.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RuleDetailsDTO {

    private String source;
    private String anySourceValue;
    private String comparator;
    private String sourceValue;
    private String targetType;
    private String targetValue;
}
