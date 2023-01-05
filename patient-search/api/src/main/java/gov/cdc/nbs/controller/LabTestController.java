package gov.cdc.nbs.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import gov.cdc.nbs.entity.srte.LabTest;
import gov.cdc.nbs.entity.srte.LoincCode;
import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.repository.LabTestRepository;
import gov.cdc.nbs.repository.LoincCodeRepository;

@Controller
public class LabTestController {
    @Value("${nbs.max-page-size: 50}")
    private Integer MAX_PAGE_SIZE;

    @Autowired
    private LabTestRepository labTestRepository;

    @Autowired
    private LoincCodeRepository loincCodeRepository;

    static List<String> relatedClassCodes = Arrays.asList("ABXBACT", "BC", "CELLMARK", "CHAL", "CHALSKIN", "CHEM",
            "COAG", "CYTO", "DRUG", "DRUG/TOX", "HEM", "HEM/BC", "MICRO", "MISC", "PANEL.ABXBACT", "PANEL.BC",
            "PANEL.CHEM", "PANEL.MICRO", "PANEL.OBS", "PANEL.SERO", "PANEL.TOX", "PANEL.UA", "SERO", "SPEC", "TOX",
            "UA", "VACCIN");

    @QueryMapping
    public Page<LabTest> findLocalLabTest(@Argument String searchText, @Argument GraphQLPage page) {
        return labTestRepository.findTests(searchText, GraphQLPage.toPageable(page, MAX_PAGE_SIZE));
    }

    @QueryMapping
    public Page<LoincCode> findLoincLabTest(@Argument String searchText, @Argument GraphQLPage page) {
        return loincCodeRepository.findTest(searchText, relatedClassCodes, GraphQLPage.toPageable(page, MAX_PAGE_SIZE));
    }
}
