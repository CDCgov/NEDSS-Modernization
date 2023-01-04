package gov.cdc.nbs.entity.elasticsearch;

import gov.cdc.nbs.entity.enums.Suffix;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.InnerField;
import org.springframework.data.elasticsearch.annotations.MultiField;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class NestedName {
    private String firstNm;
    private String lastNm;
    private String middleNm;
    private String nmPrefix;
    private String nmSuffix;
}
