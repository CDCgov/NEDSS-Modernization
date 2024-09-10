package gov.cdc.nbs.dibbs.nbs_deduplication.model.person;


import gov.cdc.nbs.dibbs.nbs_deduplication.model.BaseContainer;
import gov.cdc.nbs.dibbs.nbs_deduplication.model.RootDtoInterface;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class PersonRaceDto extends BaseContainer implements RootDtoInterface {

    private Long personUid;
    private String raceCd;
    private String addReasonCd;
    private Timestamp addTime;
    private Long addUserId;
    private Timestamp asOfDate;
    private String lastChgReasonCd;
    private Timestamp lastChgTime;
    private Long lastChgUserId;
    private String raceCategoryCd;
    private String raceDescTxt;
    private String recordStatusCd;
    private Timestamp recordStatusTime;
    private String userAffiliationTxt;
    private String progAreaCd = null;
    private String jurisdictionCd = null;
    private Long programJurisdictionOid = null;
    private String sharedInd = null;

    private Integer versionCtrlNbr;
    private Timestamp statusTime;
    private String statusCd;
    private String localId;

    public String getSuperclass() {
        return superClassType;
    }

    @Override
    public Long getUid() {
        return personUid;
    }

    public PersonRaceDto() {
        itDirty = false;
        itNew = true;
        itDelete = false;
    }


}
