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

@Controller
public class CodeValueGeneralController {
    private static final String RACE_CODE_SET_NM = "RACE_CALCULATED";
    private static final String ETHNICITY_CODE_SET_NM = "PHVS_ETHNICITYGROUP_CDC_UNK";
    private static final String OUTBREAK_CODE_SET_NM = "OUTBREAK_NM";

    @Value("${nbs.max-page-size: 50}")
    private Integer maxPageSize;

    @Autowired
    private CodeValueGeneralRepository codeValueGeneralRepository;

    public Page<CodeValueGeneral> findCodeValueGeneralByCodeSetName(@Argument String codeSetName,
            @Argument GraphQLPage page) {
        return codeValueGeneralRepository.findAllByCodeSetName(codeSetName, GraphQLPage.toPageable(page, maxPageSize));
    }

    @QueryMapping()
    public Page<CodeValueGeneral> findAllRaceValues(@Argument GraphQLPage page) {
        return codeValueGeneralRepository.findAllByCodeSetName(RACE_CODE_SET_NM,
                GraphQLPage.toPageable(page, maxPageSize));
    }

    @QueryMapping()
    public Page<CodeValueGeneral> findAllEthnicityValues(@Argument GraphQLPage page) {
        return codeValueGeneralRepository.findAllByCodeSetName(ETHNICITY_CODE_SET_NM,
                GraphQLPage.toPageable(page, maxPageSize));
    }

    @QueryMapping()
    public Page<CodeValueGeneral> findAllOutbreaks(@Argument GraphQLPage page) {
        return codeValueGeneralRepository.findAllByCodeSetName(OUTBREAK_CODE_SET_NM,
                GraphQLPage.toPageable(page, maxPageSize));
    }
}
