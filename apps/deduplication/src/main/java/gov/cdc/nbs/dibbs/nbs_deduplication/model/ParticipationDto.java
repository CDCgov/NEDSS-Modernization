package gov.cdc.nbs.dibbs.nbs_deduplication.model;


import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class ParticipationDto extends BaseContainer {
    private String addReasonCd;
    private Timestamp addTime;
    private Long addUserId;
    private String awarenessCd;
    private String awarenessDescTxt;
    private String durationAmt;
    private String durationUnitCd;
    private Timestamp fromTime;
    private String lastChgReasonCd;
    private Timestamp lastChgTime;
    private Long lastChgUserId;
    private String recordStatusCd;
    private Timestamp recordStatusTime;
    private String statusCd;
    private Timestamp statusTime;
    private String typeCd;
    private Timestamp toTime;
    private String typeDescTxt;
    private String userAffiliationTxt;
    private String subjectEntityClassCd;
    private Long subjectEntityUid;
    private Long roleSeq;
    private String cd;
    private String actClassCd;
    private String subjectClassCd;
    private Long actUid;

    public ParticipationDto() {
        // This constructor is intentionally left empty.
    }


}
