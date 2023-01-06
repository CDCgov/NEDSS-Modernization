package gov.cdc.nbs.support;

import gov.cdc.nbs.entity.srte.CodeValueGeneral;
import gov.cdc.nbs.entity.srte.CodeValueGeneralId;

public class OutbreakMother {

    public static CodeValueGeneral testOutbreak() {
        return CodeValueGeneral.builder()
                .id(new CodeValueGeneralId("OUTBREAK_NM", "TEST"))
                .codeDescTxt("TEST Outbreak")
                .codeShortDescTxt("Test Outbreak")
                .codeSystemCd("L")
                .codeSystemDescTxt("Local")
                .build();
    }
}
