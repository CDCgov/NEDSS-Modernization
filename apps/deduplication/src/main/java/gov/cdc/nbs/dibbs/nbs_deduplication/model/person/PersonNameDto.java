package gov.cdc.nbs.dibbs.nbs_deduplication.model.person;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import gov.cdc.nbs.dibbs.nbs_deduplication.model.BaseContainer;
import gov.cdc.nbs.dibbs.nbs_deduplication.model.RootDtoInterface;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
@Getter
@Setter
@JsonIgnoreProperties({"personUid", "personNameSeq", "addReasonCd", "addTime", "addUserId", "asOfDate", "defaultNmInd",
    "durationAmt", "durationUnitCd", "firstNmSndx", "fromTime", "lastChgReasonCd", "lastChgTime",
    "lastChgUserId", "lastNmSndx", "lastNm2", "lastNm2Sndx", "middleNm", "middleNm2", "nmDegree",
    "nmPrefix", "nmSuffix", "nmSuffixCd", "nmUseCd", "recordStatusCd", "recordStatusTime", "statusCd", "statusTime",
    "toTime", "userAffiliationTxt", "progAreaCd", "jurisdictionCd", "programJurisdictionOid", "sharedInd", "versionCtrlNbr", "localId"})
public class PersonNameDto
        extends BaseContainer implements RootDtoInterface {

    private Long personUid;
    private Integer personNameSeq;
    private String addReasonCd;
    private Timestamp addTime;
    private Long addUserId;
    private Timestamp asOfDate;
    private String defaultNmInd;
    private String durationAmt;
    private String durationUnitCd;
    private String firstNm;
    private String firstNmSndx;
    private Timestamp fromTime;
    private String lastChgReasonCd;
    private Timestamp lastChgTime;
    private Long lastChgUserId;
    private String lastNm;
    private String lastNmSndx;
    private String lastNm2;
    private String lastNm2Sndx;
    private String middleNm;
    private String middleNm2;
    private String nmDegree;
    private String nmPrefix;
    private String nmSuffix;
    private String nmSuffixCd;
    private String nmUseCd;
    private String recordStatusCd;
    private Timestamp recordStatusTime;
    private String statusCd;
    private Timestamp statusTime;
    private Timestamp toTime;
    private String userAffiliationTxt;
    private String progAreaCd = null;
    private String jurisdictionCd = null;
    private Long programJurisdictionOid = null;
    private String sharedInd = null;

    private Integer versionCtrlNbr;
    private String localId;

    public String getSuperclass() {
        return superClassType;
    }

    @Override
    public Long getUid() {
        return personUid;
    }

    public PersonNameDto() {
        itDirty = false;
        itNew = true;
        itDelete = false;
    }

}
