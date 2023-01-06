package gov.cdc.nbs.entity.elasticsearch;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class NestedAddress {
    private String streetAddr1;
    private String streetAddr2;
    private String city;
    private String state;
    private String zip;
    private String cntyCd;
    private String cntryCd;
}
