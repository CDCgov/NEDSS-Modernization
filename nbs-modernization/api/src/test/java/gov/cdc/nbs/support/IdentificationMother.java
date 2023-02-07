package gov.cdc.nbs.support;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import gov.cdc.nbs.entity.srte.CodeValueGeneral;
import gov.cdc.nbs.entity.srte.CodeValueGeneralId;

public class IdentificationMother {

    public static final String DRIVER_LICENSE_CODE = "DL";
    public static final String SOCIAL_SECURITY_CODE = "SS";
    public static final List<String> IDENTIFICATION_CODE_LIST = Arrays.asList(
            DRIVER_LICENSE_CODE,
            SOCIAL_SECURITY_CODE);

    public static CodeValueGeneral driversLicense() {
        var now = Instant.now();
        return CodeValueGeneral.builder()
                .id(new CodeValueGeneralId("EI_TYPE", DRIVER_LICENSE_CODE))
                .codeDescTxt("Driver's license number")
                .codeShortDescTxt("Driver's license number")
                .codeSystemCd("2.16.840.1.113883")
                .codeSystemDescTxt("Health Level Seven")
                .effectiveFromTime(now)
                .indentLevelNbr((short) 1)
                .isModifiableInd('N')
                .nbsUid(1468)
                .statusCd('A')
                .statusTime(now)
                .conceptTypeCd("LOCAL")
                .conceptCode(DRIVER_LICENSE_CODE)
                .conceptNm("Driver's license number")
                .conceptPreferredNm("Driver's license number")
                .conceptStatusCd("Active")
                .conceptStatusTime(now)
                .addTime(now)
                .addUserId(99999999L)
                .build();
    }

    public static CodeValueGeneral socialSecurity() {
        var now = Instant.now();
        return CodeValueGeneral.builder()
                .id(new CodeValueGeneralId("EI_TYPE", SOCIAL_SECURITY_CODE))
                .codeDescTxt("Social Security")
                .codeShortDescTxt("Social Security")
                .codeSystemCd("2.16.840.1.113883")
                .codeSystemDescTxt("Health Level Seven")
                .effectiveFromTime(now)
                .indentLevelNbr((short) 1)
                .isModifiableInd('N')
                .nbsUid(1487)
                .statusCd('A')
                .statusTime(now)
                .conceptTypeCd("LOCAL")
                .conceptCode(SOCIAL_SECURITY_CODE)
                .conceptNm("Social Security")
                .conceptPreferredNm("Social Security")
                .conceptStatusCd("Active")
                .conceptStatusTime(now)
                .addTime(now)
                .addUserId(99999999L)
                .build();
    }
}
