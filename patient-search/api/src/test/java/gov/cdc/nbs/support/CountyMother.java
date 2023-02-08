package gov.cdc.nbs.support;

import java.time.Instant;

import gov.cdc.nbs.entity.srte.StateCountyCodeValue;

public class CountyMother {
    public static StateCountyCodeValue dekalbGA() {
        var now = Instant.now();
        return StateCountyCodeValue.builder()
                .id("13089")
                .assigningAuthorityCd("2.16.840.1.113883")
                .assigningAuthorityDescTxt("Dekalb County")
                .codeShortDescTxt("Dekalb, GA")
                .effectiveFromTime(now)
                .effectiveToTime(now)
                .indentLevelNbr((short) 2)
                .isModifiableInd('N')
                .parentIsCd(StateMother.georgia().getId())
                .statusCd("A")
                .statusTime(now)
                .codeSetNm("COUNTY_CCD")
                .seqNum((short) 1)
                .nbsUid(441)
                .codeSystemCd("2.16.840.1.113883.6.93")
                .codeSystemDescTxt("FIPS 6-4 (County)")
                .build();
    }

    public static StateCountyCodeValue monroeTN() {
        var now = Instant.now();
        return StateCountyCodeValue.builder()
                .id("47123")
                .assigningAuthorityCd("2.16.840.1.113883")
                .assigningAuthorityDescTxt("Monroe County")
                .codeShortDescTxt("Monroe, TN")
                .effectiveFromTime(now)
                .effectiveToTime(now)
                .indentLevelNbr((short) 2)
                .isModifiableInd('N')
                .parentIsCd(StateMother.tennessee().getId())
                .statusCd("A")
                .statusTime(now)
                .codeSetNm("COUNTY_CCD")
                .seqNum((short) 1)
                .nbsUid(2532)
                .codeSystemCd("2.16.840.1.113883.6.93")
                .codeSystemDescTxt("FIPS 6-4 (County)")
                .build();
    }
}
