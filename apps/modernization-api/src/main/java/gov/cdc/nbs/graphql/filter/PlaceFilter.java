package gov.cdc.nbs.graphql.filter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaceFilter {
  private Long id;
  private String description;
  private String nm;
  private String streetAddr1;
  private String streetAddr2;
  private String cityCd;
  private String cityDescTxt;
  private String stateCd;
  private String zipCd;
}
