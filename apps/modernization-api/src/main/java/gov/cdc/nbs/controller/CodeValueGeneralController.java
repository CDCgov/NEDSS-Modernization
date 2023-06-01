package gov.cdc.nbs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;

import gov.cdc.nbs.entity.srte.CodeValueGeneral;
import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.repository.CodeValueGeneralRepository;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;

import static gov.cdc.nbs.graphql.GraphQLPage.toPageable;

@Controller
public class CodeValueGeneralController {
    private static final String RACE_CODE_SET_NM = "RACE_CALCULATED";
    private static final String ETHNICITY_CODE_SET_NM = "PHVS_ETHNICITYGROUP_CDC_UNK";
    private static final String OUTBREAK_CODE_SET_NM = "OUTBREAK_NM";
    private static final String PATIENT_IDENTIFICATION_TYPES = "EI_TYPE_PAT";

    @Value("${nbs.max-page-size: 50}")
    private Integer maxPageSize;

    @Autowired
    private CodeValueGeneralRepository codeValueGeneralRepository;

    public Page<CodeValueGeneral> findCodeValueGeneralByCodeSetName(@Argument String codeSetName,
            @Argument GraphQLPage page) {
        return codeValueGeneralRepository.findAllByCodeSetName(codeSetName, toPageable(page, maxPageSize));
    }

    @QueryMapping()
    public Page<CodeValueGeneral> findAllRaceValues(@Argument GraphQLPage page) {
        return codeValueGeneralRepository.findAllByCodeSetName(RACE_CODE_SET_NM,
                toPageable(page, maxPageSize));
    }

    @QueryMapping()
    public Page<CodeValueGeneral> findAllEthnicityValues(@Argument GraphQLPage page) {
        return codeValueGeneralRepository.findAllByCodeSetName(ETHNICITY_CODE_SET_NM,
                toPageable(page, maxPageSize));
    }

    @QueryMapping()
    public Page<CodeValueGeneral> findAllPatientIdentificationTypes(@Argument GraphQLPage page) {
        return codeValueGeneralRepository.findAllByCodeSetName(PATIENT_IDENTIFICATION_TYPES,
                toPageable(page, maxPageSize));
    }

    @QueryMapping()
    public Page<CodeValueGeneral> findAllOutbreaks(@Argument GraphQLPage page) {
        return codeValueGeneralRepository.findAllByCodeSetName(OUTBREAK_CODE_SET_NM,
                toPageable(page, maxPageSize));
    }

    @QueryMapping
    public Page<CodeValueGeneral> findAllNameTypes(@Argument GraphQLPage page) {
        return codeValueGeneralRepository.findAllByCodeSetName("P_NM_USE", toPageable(page, maxPageSize));
    }

    @QueryMapping
    public Page<CodeValueGeneral> findAllNamePrefixes(@Argument GraphQLPage page) {
        return codeValueGeneralRepository.findAllByCodeSetName("P_NM_PFX", toPageable(page, maxPageSize));
    }

    @QueryMapping
    public Page<CodeValueGeneral> findAllDegrees(@Argument GraphQLPage page) {
        return codeValueGeneralRepository.findAllByCodeSetName("P_NM_DEG", toPageable(page, maxPageSize));
    }

    @QueryMapping
    public Page<CodeValueGeneral> findAllAddressTypes(@Argument GraphQLPage page) {
        return codeValueGeneralRepository.findAllByCodeSetName("EL_TYPE_PST_PAT", toPageable(page, maxPageSize));
    }

    @QueryMapping
    public Page<CodeValueGeneral> findAllAddressUses(@Argument GraphQLPage page) {
        return codeValueGeneralRepository.findAllByCodeSetName("EL_TYPE_PST_PAT", toPageable(page, maxPageSize));
    }

    @QueryMapping
    public Page<CodeValueGeneral> findAllPhoneAndEmailType(@Argument GraphQLPage page) {
        return codeValueGeneralRepository.findAllByCodeSetName("EL_TYPE_TELE_PAT", toPageable(page, maxPageSize));
    }

    @QueryMapping
    public Page<CodeValueGeneral> findAllPhoneAndEmailUse(@Argument GraphQLPage page) {
        return codeValueGeneralRepository.findAllByCodeSetName("EL_USE_TELE_PAT", toPageable(page, maxPageSize));
    }

    @QueryMapping
    public Page<CodeValueGeneral> findAllIdentificationTypes(@Argument GraphQLPage page) {
        return codeValueGeneralRepository.findAllByCodeSetName("EI_TYPE_PAT", toPageable(page, maxPageSize));
    }
    @QueryMapping
    public Page<CodeValueGeneral> findAllAssigningAuthorities(@Argument GraphQLPage page) {
        return codeValueGeneralRepository.findAllByCodeSetName("EI_AUTH_PAT", toPageable(page, maxPageSize));
    }

}
