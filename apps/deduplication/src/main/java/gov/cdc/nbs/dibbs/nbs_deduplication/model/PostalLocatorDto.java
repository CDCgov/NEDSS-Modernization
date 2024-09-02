package gov.cdc.nbs.dibbs.nbs_deduplication.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@JsonIgnoreProperties({"postalLocatorUid", "addReasonCd", "addTime", "addUserId", "censusBlockCd", "censusMinorCivilDivisionCd",
    "censusTrackCd", "cityCd", "cntryCd", "cntyCd", "cntyDescTxt", "lastChgReasonCd", "lastChgTime", "lastChgUserId",
    "MSACongressDistrictCd", "recordStatusCd", "recordStatusTime", "regionDistrictCd", "userAffiliationTxt", "geocodeMatchInd",
    "withinCityLimitsInd", "progAreaCd", "jurisdictionCd", "programJurisdictionOid", "sharedInd", "censusTract"})
public class PostalLocatorDto extends BaseContainer {

    private String streetAddr1;
    private String streetAddr2;
    private String cityDescTxt;
    private String stateCd;
    private String cntryDescTxt;
    private String zipCd;


    private Long postalLocatorUid;
    private String addReasonCd;
    private Timestamp addTime;
    private Long addUserId;
    private String censusBlockCd;
    private String censusMinorCivilDivisionCd;
    private String censusTrackCd;
    private String cityCd;

    private String cntryCd;

    private String cntyCd;
    private String cntyDescTxt;
    private String lastChgReasonCd;
    private Timestamp lastChgTime;
    private Long lastChgUserId;
    private String MSACongressDistrictCd;
    private String recordStatusCd;
    private Timestamp recordStatusTime;
    private String regionDistrictCd;
    private String userAffiliationTxt;
    private String geocodeMatchInd;
    private String withinCityLimitsInd;
    private String progAreaCd = null;
    private String jurisdictionCd = null;
    private Long programJurisdictionOid = null;
    private String sharedInd = null;

    private String censusTract;

    public PostalLocatorDto() {
        // Default constructor
        itDirty = false;
        itNew = false;
        itDelete = false;
    }

}
