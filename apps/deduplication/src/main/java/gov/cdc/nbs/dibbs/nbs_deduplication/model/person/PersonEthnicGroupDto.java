package gov.cdc.nbs.dibbs.nbs_deduplication.model.person;

import gov.cdc.nbs.dibbs.nbs_deduplication.model.BaseContainer;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class PersonEthnicGroupDto extends BaseContainer {

    private Long personUid;
    private String ethnicGroupCd;
    private String addReasonCd;
    private Timestamp addTime;
    private Long addUserId;
    private String ethnicGroupDescTxt;
    private String lastChgReasonCd;
    private Timestamp lastChgTime;
    private Long lastChgUserId;
    private String recordStatusCd;
    private Timestamp recordStatusTime;
    private String userAffiliationTxt;
    private String progAreaCd = null;
    private String jurisdictionCd = null;
    private Long programJurisdictionOid = null;
    private String sharedInd = null;


    public PersonEthnicGroupDto() {
        itDirty = false;
        itNew = true;
        itDelete = false;
    }

}
