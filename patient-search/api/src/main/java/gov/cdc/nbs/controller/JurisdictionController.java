package gov.cdc.nbs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import gov.cdc.nbs.entity.srte.JurisdictionCode;
import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.repository.JurisdictionCodeRepository;

@Controller
public class JurisdictionController {

    @Autowired
    private JurisdictionCodeRepository jurisdictionCodeRepository;

    @QueryMapping()
    public Page<JurisdictionCode> findAllJurisdictions(@Argument GraphQLPage page) {
        return jurisdictionCodeRepository.findAll(GraphQLPage.toPageable(page, 1000));
    }

}
