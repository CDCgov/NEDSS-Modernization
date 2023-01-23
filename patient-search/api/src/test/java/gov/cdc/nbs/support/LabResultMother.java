package gov.cdc.nbs.support;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import gov.cdc.nbs.entity.srte.LabCodingSystem;
import gov.cdc.nbs.entity.srte.LabResult;
import gov.cdc.nbs.entity.srte.LabResultId;
import gov.cdc.nbs.entity.srte.SnomedCode;

public class LabResultMother {

    public static SnomedCode snomedNoGrowth() throws ParseException {
        String statusTime = "2004-02-01 00:00:00.000";
        Date date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS").parse(statusTime);
        return SnomedCode.builder()
                .id("R-42491")
                .snomedDescTxt("No growth")
                .sourceConceptId("264868006")
                .sourceVersionId("CT 1.5")
                .statusCd('A')
                .statusTime(date.toInstant())
                .nbsUid(14104)
                .paDerivationExcludeCd('N')
                .build();
    }

    public static SnomedCode snomedAbnormal() throws ParseException {
        String statusTime = "2004-02-01 00:00:00.000";
        Date date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS").parse(statusTime);
        return SnomedCode.builder()
                .id("R-42037")
                .snomedDescTxt("Abnormal")
                .sourceConceptId("263654008")
                .sourceVersionId("CT 1.5")
                .statusCd('A')
                .statusTime(date.toInstant())
                .nbsUid(14098)
                .paDerivationExcludeCd('N')
                .build();
    }

    public static LabResult localUnexplained() {
        return LabResult.builder()
                .id(new LabResultId("ANOMRES", "DEFAULT"))
                .labResultDescTxt("unexplained laboratory result")
                .nbsUid(9905L)
                .organismNameInd('N')
                .paDerivationExcludeCd('N')
                .codeSetNm("LAB_RSLT")
                .laboratory(LabCodingSystem.builder()
                        .id("DEFAULT")
                        .laboratorySystemDescTxt("Default Manual Lab")
                        .codingSystemCd("NBS")
                        .codeSystemDescTxt("NEDSS Base System")
                        .electronicLabInd('N')
                        .assigningAuthorityCd("2.16.840.1.114222")
                        .assigningAuthorityDescTxt("Centers for Disease Control")
                        .nbsUid(43L)
                        .build())
                .build();
    }

    public static LabResult localAbnormal() {
        return LabResult.builder()
                .id(new LabResultId("ABN", "DEFAULT"))
                .labResultDescTxt("abnormal")
                .nbsUid(9892L)
                .organismNameInd('N')
                .paDerivationExcludeCd('N')
                .codeSetNm("LAB_RSLT")
                .laboratory(LabCodingSystem.builder()
                        .id("DEFAULT")
                        .laboratorySystemDescTxt("Default Manual Lab")
                        .codingSystemCd("NBS")
                        .codeSystemDescTxt("NEDSS Base System")
                        .electronicLabInd('N')
                        .assigningAuthorityCd("2.16.840.1.114222")
                        .assigningAuthorityDescTxt("Centers for Disease Control")
                        .nbsUid(43L)
                        .build())
                .build();
    }
}
