package gov.cdc.nbs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import gov.cdc.nbs.entity.srte.ProgramAreaCode;
import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.repository.ProgramAreaCodeRepository;

@Controller
public class ProgramAreaController {

    @Autowired
    private ProgramAreaCodeRepository programAreaCodeRepository;

    @QueryMapping()
    public Page<ProgramAreaCode> findAllProgramAreas(@Argument GraphQLPage page) {
        return programAreaCodeRepository.findAll(GraphQLPage.toPageable(page, 1000));
    }

}
