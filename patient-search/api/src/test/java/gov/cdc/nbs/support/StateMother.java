package gov.cdc.nbs.support;

import java.time.Instant;

import gov.cdc.nbs.entity.srte.StateCode;

public class StateMother {

    public static StateCode georgia() {
        var now = Instant.now();
        return StateCode.builder()
                .id("13")
                .assigningAuthorityCd("2.16.840.1.113883")
                .assigningAuthorityDescTxt("Health Level Seven")
                .stateNm("GA")
                .codeDescTxt("Georgia")
                .effectiveFromTime(now)
                .effectiveToTime(now)
                .indentLevelNbr((short) 1)
                .isModifiableInd('Y')
                .keyInfoTxt("GA Georgia")
                .parentIsCd("ROOT")
                .statusCd("A")
                .statusTime(now)
                .codeSetNm("STATE_CCD")
                .seqNum((short) 1)
                .nbsUid(11)
                .codeSystemCd("2.16.840.1.113883.6.92")
                .codeSystemDescTxt("FIPS 5-2(State)")
                .build();
    }

    public static StateCode tennessee() {
        var now = Instant.now();
        return StateCode.builder()
                .id("47")
                .assigningAuthorityCd("2.16.840.1.113883")
                .assigningAuthorityDescTxt("Health Level Seven")
                .stateNm("TN")
                .codeDescTxt("Tennessee")
                .effectiveFromTime(now)
                .effectiveToTime(now)
                .indentLevelNbr((short) 1)
                .isModifiableInd('Y')
                .keyInfoTxt("TN Tennessee")
                .parentIsCd("ROOT")
                .statusCd("A")
                .statusTime(now)
                .codeSetNm("STATE_CCD")
                .seqNum((short) 1)
                .nbsUid(43)
                .codeSystemCd("2.16.840.1.113883.6.92")
                .codeSystemDescTxt("FIPS 5-2(State)")
                .build();
    }
}
