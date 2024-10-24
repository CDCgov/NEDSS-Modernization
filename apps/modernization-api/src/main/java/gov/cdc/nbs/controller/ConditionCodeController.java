package gov.cdc.nbs.controller;

import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import gov.cdc.nbs.entity.srte.ConditionCode;
import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.repository.ConditionCodeRepository;

@Controller
public class ConditionCodeController {

  private final ConditionCodeRepository conditionCodeRepository;

  public ConditionCodeController(final ConditionCodeRepository conditionCodeRepository) {
    this.conditionCodeRepository = conditionCodeRepository;
  }

  @QueryMapping()
  public Page<ConditionCode> findAllConditionCodes(@Argument GraphQLPage page) {
    return conditionCodeRepository.findAll(GraphQLPage.toPageable(page, 1000));
  }

}
