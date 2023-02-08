package gov.cdc.nbs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import gov.cdc.nbs.entity.srte.StateCode;
import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.repository.StateCodeRepository;

@Controller
public class StateController {
    @Value("${nbs.max-page-size: 50}")
    private Integer maxPageSize;

    @Autowired
    private StateCodeRepository stateCodeRepository;

    @QueryMapping()
    public Page<StateCode> findAllStateCodes(@Argument GraphQLPage page) {
        return stateCodeRepository.findAll(GraphQLPage.toPageable(page, maxPageSize));
    }
}
