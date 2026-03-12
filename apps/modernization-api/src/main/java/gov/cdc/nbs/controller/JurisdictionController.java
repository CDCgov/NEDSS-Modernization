package gov.cdc.nbs.controller;

import gov.cdc.nbs.entity.srte.JurisdictionCode;
import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.repository.JurisdictionCodeRepository;
import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class JurisdictionController {

  private final JurisdictionCodeRepository jurisdictionCodeRepository;

  public JurisdictionController(final JurisdictionCodeRepository jurisdictionCodeRepository) {
    this.jurisdictionCodeRepository = jurisdictionCodeRepository;
  }

  @QueryMapping()
  public Page<JurisdictionCode> findAllJurisdictions(@Argument GraphQLPage page) {
    return jurisdictionCodeRepository.findAll(GraphQLPage.toPageable(page, 1000));
  }
}
