package gov.cdc.nbs.questionbank.condition.request;

import lombok.Data;

@Data
public class ReadConditionRequest {

    private String searchText;

    private String filterField;

    private String filterValue;

    private String singleCharFilterField;

    private Character singleCharValueField;

}
