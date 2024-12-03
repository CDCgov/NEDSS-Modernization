package gov.cdc.nbs.dibbs.nbs_deduplication.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@JsonIgnoreProperties({"locatorUid", "addReasonCd", "addTime", "addUserId", "asOfDate", "cd", "cdDescTxt",
    "classCd", "durationAmt", "durationUnitCd", "fromTime", "lastChgReasonCd", "lastChgTime", "lastChgUserId",
    "locatorDescTxt", "recordStatusCd", "statusCd", "recordStatusTime", "statusTime", "toTime", "useCd",
    "userAffiliationTxt", "validTimeTxt", "entityUid",  "thePhysicalLocatorDto",
    "theTeleLocatorDto", "versionCtrlNbr"})
public class EntityLocatorParticipationDto extends BaseContainer {


    private Long locatorUid;
    private String addReasonCd;
    private Timestamp addTime;
    private Long addUserId;
    private Timestamp asOfDate;
    private String cd;
    private String cdDescTxt;
    private String classCd;
    private String durationAmt;
    private String durationUnitCd;
    private Timestamp fromTime;
    private String lastChgReasonCd;
    private Timestamp lastChgTime;
    private Long lastChgUserId;
    private String locatorDescTxt;
    private String recordStatusCd;
    private Timestamp recordStatusTime;
    private String statusCd;
    private Timestamp statusTime;
    private Timestamp toTime;
    private String useCd;
    private String userAffiliationTxt;
    private String validTimeTxt;
    private Long entityUid;
    private PostalLocatorDto thePostalLocatorDto;
    private PhysicalLocatorDto thePhysicalLocatorDto;
    private TeleLocatorDto theTeleLocatorDto;
    private Integer versionCtrlNbr;

    public EntityLocatorParticipationDto() {
        // This constructor is intentionally left empty.
    }



}
