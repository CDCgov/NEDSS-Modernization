package gov.cdc.nbs.support;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import gov.cdc.nbs.entity.srte.CodeValueGeneral;
import gov.cdc.nbs.entity.srte.CodeValueGeneralId;

public class EthnicityMother {
    // values from
    // https://phinvads.cdc.gov/vads/ViewValueSet.action?id=35D34BBC-617F-DD11-B38D-00188B398520
    public static final String HISPANIC_OR_LATINO_CODE = "2135-2";
    public static final String NOT_HISPANIC_OR_LATINO_CODE = "2186-5";
    public static final String UNKNOWN_CODE = "UNK";
    public static final List<String> ETHNICITY_LIST = Arrays.asList(
            HISPANIC_OR_LATINO_CODE,
            NOT_HISPANIC_OR_LATINO_CODE,
            UNKNOWN_CODE);

    public static CodeValueGeneral hispanicOrLatino() {
        var now = Instant.now();
        return CodeValueGeneral.builder()
                .id(new CodeValueGeneralId("PHVS_ETHNICITYGROUP_CDC_UNK", HISPANIC_OR_LATINO_CODE))
                .codeDescTxt("Hispanic or Latino")
                .codeShortDescTxt("Hispanic or Latino")
                .codeSystemCd("2.16.840.1.113883.6.238")
                .codeSystemDescTxt("Race & Ethnicity - CDC")
                .effectiveFromTime(now)
                .indentLevelNbr((short) 1)
                .isModifiableInd('Y')
                .nbsUid(10004782)
                .statusCd('A')
                .statusTime(now)
                .conceptTypeCd("PHIN")
                .conceptCode(HISPANIC_OR_LATINO_CODE)
                .conceptNm("Hispanic or Latino")
                .conceptStatusCd("Published")
                .conceptStatusTime(now)
                .addTime(now)
                .addUserId(99999999L)
                .build();
    }

    public static CodeValueGeneral notHispanicOrLatino() {
        var now = Instant.now();
        return CodeValueGeneral.builder()
                .id(new CodeValueGeneralId("PHVS_ETHNICITYGROUP_CDC_UNK", NOT_HISPANIC_OR_LATINO_CODE))
                .codeDescTxt("Not Hispanic or Latino")
                .codeShortDescTxt("Not Hispanic or Latino")
                .codeSystemCd("2.16.840.1.113883.6.238")
                .codeSystemDescTxt("Race & Ethnicity - CDC")
                .effectiveFromTime(now)
                .indentLevelNbr((short) 1)
                .isModifiableInd('Y')
                .nbsUid(10004783)
                .statusCd('A')
                .statusTime(now)
                .conceptTypeCd("PHIN")
                .conceptCode(NOT_HISPANIC_OR_LATINO_CODE)
                .conceptNm("Not Hispanic or Latino")
                .conceptStatusCd("Published")
                .conceptStatusTime(now)
                .addTime(now)
                .addUserId(99999999L)
                .build();
    }

    public static CodeValueGeneral unknown() {
        var now = Instant.now();
        return CodeValueGeneral.builder()
                .id(new CodeValueGeneralId("PHVS_ETHNICITYGROUP_CDC_UNK", UNKNOWN_CODE))
                .codeDescTxt("unknown")
                .codeShortDescTxt("unknown")
                .codeSystemCd("")
                .codeSystemDescTxt("NullFlavor")
                .effectiveFromTime(now)
                .indentLevelNbr((short) 1)
                .isModifiableInd('Y')
                .nbsUid(10004784)
                .statusCd('A')
                .statusTime(now)
                .conceptTypeCd("PHIN")
                .conceptCode(UNKNOWN_CODE)
                .conceptNm("Not Hispanic or Latino")
                .conceptStatusCd("Published")
                .conceptStatusTime(now)
                .addTime(now)
                .addUserId(99999999L)
                .build();
    }
}
