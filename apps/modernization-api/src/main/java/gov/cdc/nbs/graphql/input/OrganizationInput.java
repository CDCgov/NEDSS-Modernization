package gov.cdc.nbs.graphql.input;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrganizationInput {
  private String addReasonCd;
  private LocalDateTime addTime;
  private Long addUserId;
  private String cd;
  private String cdDescTxt;
  private String description;
  private String durationAmt;
  private String durationUnitCd;
  private LocalDateTime fromTime;
  private String lastChgReasonCd;
  private LocalDateTime lastChgTime;
  private Long lastChgUserId;
  private String localId;
  private String recordStatusCd;
  private LocalDateTime recordStatusTime;
  private String standardIndustryClassCd;
  private String standardIndustryDescTxt;
  private String statusCd;
  private LocalDateTime statusTime;
  private LocalDateTime toTime;
  private String userAffiliationTxt;
  private String displayNm;
  private String streetAddr1;
  private String streetAddr2;
  private String cityCd;
  private String cityDescTxt;
  private String stateCd;
  private String cntyCd;
  private String cntryCd;
  private String zipCd;
  private String phoneNbr;
  private String phoneCntryCd;
  private Short versionCtrlNbr;
  private String electronicInd;
  private String edxInd;
}
