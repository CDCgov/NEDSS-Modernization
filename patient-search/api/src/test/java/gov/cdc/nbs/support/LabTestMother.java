package gov.cdc.nbs.support;

import gov.cdc.nbs.entity.srte.LabCodingSystem;
import gov.cdc.nbs.entity.srte.LabTest;
import gov.cdc.nbs.entity.srte.LabTestId;
import gov.cdc.nbs.entity.srte.LoincCode;

public class LabTestMother {

    public static LoincCode loincTestAcyclovir() {
        return LoincCode.builder()
                .id("1-8")
                .componentName("ACYCLOVIR")
                .property("SUSC")
                .timeAspect("PT")
                .systemCd("ISLT")
                .scaleType("ORDQN")
                .nbsUid(1L)
                .relatedClassCd("ABXBACT")
                .paDerivationExcludeCd('N')
                .build();
    }

    public static LoincCode loincTestAmdinocillin() {
        return LoincCode.builder()
                .id("10-9")
                .componentName("AMDINOCILLIN")
                .property("SUSC")
                .timeAspect("PT")
                .systemCd("ISLT+SER")
                .scaleType("ORD")
                .methodType("MIC")
                .nbsUid(2L)
                .relatedClassCd("ABXBACT")
                .paDerivationExcludeCd('N')
                .build();
    }

    public static LabTest localTestThyroxine() {
        return LabTest.builder()
                .id(new LabTestId("001446", "44D0975929"))
                .labTestDescTxt("Thyroxine (T4), Neonatal")
                .testTypeCd("R")
                .nbsUid(40L)
                .paDerivationExcludeCd('N')

                .laboratory(LabCodingSystem.builder()
                        .id("44D0975929")
                        .laboratorySystemDescTxt(
                                "QUEST DIAGNOSTICS INC, 4230 HARDING ROAD SUITE 400, NASHVILLE, TN 37205, 6152978161")
                        .codingSystemCd("L")
                        .codeSystemDescTxt("Local")
                        .electronicLabInd('Y')
                        .assigningAuthorityCd("CLIA")
                        .assigningAuthorityDescTxt("Clinical Laboratory Improvement Act")
                        .nbsUid(42L)
                        .build())
                .build();
    }

    public static LabTest localTestHiv1() {
        return LabTest.builder()
                .id(new LabTestId("001719", "11D0255931"))
                .labTestDescTxt("HIV-1 ABS, QUAL")
                .testTypeCd("R")
                .nbsUid(41L)
                .paDerivationExcludeCd('N')
                .laboratory(LabCodingSystem.builder()
                        .id("11D0255931")
                        .laboratorySystemDescTxt(
                                "QUEST DIAGNOSTICS CLINCAL LABS INC, 1777 MONTREAL CIR, TUCKER, GA 30084, 7709349205")
                        .codingSystemCd("L")
                        .codeSystemDescTxt("Local")
                        .electronicLabInd('Y')
                        .assigningAuthorityCd("CLIA")
                        .assigningAuthorityDescTxt("Clinical Laboratory Improvement Act")
                        .nbsUid(1L)
                        .build())
                .build();
    }
}
