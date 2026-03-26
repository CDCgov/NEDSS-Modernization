package gov.cdc.nbs.controller;

import gov.cdc.nbs.entity.srte.ProgramAreaCode;
import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.repository.ProgramAreaCodeRepository;
import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class ProgramAreaController {

  private final ProgramAreaCodeRepository programAreaCodeRepository;

  public ProgramAreaController(final ProgramAreaCodeRepository programAreaCodeRepository) {
    this.programAreaCodeRepository = programAreaCodeRepository;
  }

  @QueryMapping()
  public Page<ProgramAreaCode> findAllProgramAreas(@Argument GraphQLPage page) {
    return programAreaCodeRepository.findAll(GraphQLPage.toPageable(page, 1000));
  }
}
