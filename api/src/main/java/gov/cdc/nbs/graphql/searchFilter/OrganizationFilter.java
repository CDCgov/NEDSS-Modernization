package gov.cdc.nbs.graphql.searchFilter;

import gov.cdc.nbs.graphql.GraphQLPage;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrganizationFilter {
    private Long id;
    private String displayNm;
    private String streetAddr1;
    private String streetAddr2;
    private String cityCd;
    private String cityDescTxt;
    private String stateCd;
    private String zipCd;
}
