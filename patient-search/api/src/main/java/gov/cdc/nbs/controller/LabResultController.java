package gov.cdc.nbs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import gov.cdc.nbs.entity.srte.LabResult;
import gov.cdc.nbs.entity.srte.SnomedCode;
import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.repository.LabResultRepository;
import gov.cdc.nbs.repository.SnomedCodeRepository;

@Controller
public class LabResultController {
    @Value("${nbs.max-page-size: 50}")
    private Integer maxPageSize;

    @Autowired
    private LabResultRepository labResultRepository;

    @Autowired
    private SnomedCodeRepository snomedCodeRepository;

    @QueryMapping
    public Page<LabResult> findLocalCodedResults(@Argument String searchText, @Argument GraphQLPage page) {
        return labResultRepository.findLabResults(searchText, GraphQLPage.toPageable(page, maxPageSize));
    }

    @QueryMapping
    public Page<SnomedCode> findSnomedCodedResults(@Argument String searchText, @Argument GraphQLPage page) {
        return snomedCodeRepository.findSnomedCodes(searchText, GraphQLPage.toPageable(page, maxPageSize));
    }
}
