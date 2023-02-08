package gov.cdc.nbs.support;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import gov.cdc.nbs.entity.srte.CodeValueGeneral;
import gov.cdc.nbs.entity.srte.CodeValueGeneralId;

public class RaceMother {

    public static final String WHITE_CODE = "2106-3";
    public static final String BLACK_OR_AFRICAN_AMERICAN_CODE = "2054-5";
    public static final String ASIAN_CODE = "2028-9";

    public static final List<String> RACE_LIST = Arrays.asList(
            BLACK_OR_AFRICAN_AMERICAN_CODE,
            WHITE_CODE,
            ASIAN_CODE);

    public static CodeValueGeneral blackOrAfricanAmerican() {
        var now = Instant.now();
        return CodeValueGeneral.builder()
                .id(new CodeValueGeneralId("RACE_CALCULATED", BLACK_OR_AFRICAN_AMERICAN_CODE))
                .codeDescTxt("Black or African American")
                .codeShortDescTxt("Black or African American")
                .codeSystemCd("2.16.840.1.113883.6.238")
                .codeSystemDescTxt("Race & Ethnicity - CDC")
                .effectiveFromTime(now)
                .indentLevelNbr((short) 1)
                .isModifiableInd('N')
                .nbsUid(10001142)
                .statusCd('A')
                .statusTime(now)
                .conceptTypeCd("PHIN")
                .conceptCode(BLACK_OR_AFRICAN_AMERICAN_CODE)
                .conceptNm("Black or African American")
                .conceptPreferredNm("Black or African American")
                .conceptStatusCd("Published")
                .conceptStatusTime(now)
                .addTime(now)
                .addUserId(99999999L)
                .build();
    }

    public static CodeValueGeneral white() {
        var now = Instant.now();
        return CodeValueGeneral.builder()
                .id(new CodeValueGeneralId("RACE_CALCULATED", WHITE_CODE))
                .codeDescTxt("White")
                .codeShortDescTxt("White")
                .codeSystemCd("2.16.840.1.113883.6.238")
                .codeSystemDescTxt("Race & Ethnicity - CDC")
                .effectiveFromTime(now)
                .indentLevelNbr((short) 1)
                .isModifiableInd('N')
                .nbsUid(10001144)
                .statusCd('A')
                .statusTime(now)
                .conceptTypeCd("PHIN")
                .conceptCode(WHITE_CODE)
                .conceptNm("White")
                .conceptPreferredNm("White")
                .conceptStatusCd("Published")
                .conceptStatusTime(now)
                .addTime(now)
                .addUserId(99999999L)
                .build();
    }

    public static CodeValueGeneral asian() {
        var now = Instant.now();
        return CodeValueGeneral.builder()
                .id(new CodeValueGeneralId("RACE_CALCULATED", ASIAN_CODE))
                .codeDescTxt("Asian")
                .codeShortDescTxt("Asian")
                .codeSystemCd("2.16.840.1.113883.6.238")
                .codeSystemDescTxt("Race & Ethnicity - CDC")
                .effectiveFromTime(now)
                .indentLevelNbr((short) 1)
                .isModifiableInd('N')
                .nbsUid(10001141)
                .statusCd('A')
                .statusTime(now)
                .conceptTypeCd("PHIN")
                .conceptCode(ASIAN_CODE)
                .conceptNm("Asian")
                .conceptPreferredNm("Asian")
                .conceptStatusCd("Published")
                .conceptStatusTime(now)
                .addTime(now)
                .addUserId(99999999L)
                .build();
    }
}
