package gov.cdc.nbs.dibbs.nbs_deduplication.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class StateDefinedFieldDataDto extends BaseContainer
{
    private Long ldfUid;
    private String businessObjNm;
    private Timestamp addTime;
    private Long businessObjUid;
    private Timestamp lastChgTime;
    private String ldfValue;
    private Integer versionCtrlNbr;
    private String conditionCd;
    private String codeSetNm;
    private String fieldSize;
    private String dataType;
}
