package gov.cdc.nbs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import gov.cdc.nbs.entity.srte.StateCountyCodeValue;
import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.repository.StateCountyCodeRepository;

@Controller
public class CountyController {
    @Value("${nbs.max-page-size: 50}")
    private Integer maxPageSize;

    @Autowired
    private StateCountyCodeRepository stateCountyCodeRepository;

    @QueryMapping()
    public Page<StateCountyCodeValue> findAllCountyCodesForState(@Argument String stateCode,
            @Argument GraphQLPage page) {
        return stateCountyCodeRepository.findAllByParentIsCd(stateCode, GraphQLPage.toPageable(page, maxPageSize));
    }
}
