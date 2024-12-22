package gov.cdc.nbs.controller;

import gov.cdc.nbs.entity.srte.CodeValueGeneral;
import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.repository.CodeValueGeneralRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import static gov.cdc.nbs.graphql.GraphQLPage.toPageable;

@Controller
public class CodeValueGeneralController {
  private static final String RACE_CODE_SET_NM = "RACE_CALCULATED";
  private static final String ETHNICITY_CODE_SET_NM = "PHVS_ETHNICITYGROUP_CDC_UNK";
  private static final String OUTBREAK_CODE_SET_NM = "OUTBREAK_NM";
  private static final String PATIENT_IDENTIFICATION_TYPES = "EI_TYPE_PAT";

  private final Integer maxPageSize;
  private final CodeValueGeneralRepository codeValueGeneralRepository;

  public CodeValueGeneralController(
      @Value("${nbs.max-page-size: 50}") final Integer maxPageSize,
      final CodeValueGeneralRepository codeValueGeneralRepository) {
    this.maxPageSize = maxPageSize;
    this.codeValueGeneralRepository = codeValueGeneralRepository;
  }

  @QueryMapping()
  public Page<CodeValueGeneral> findAllRaceValues(@Argument GraphQLPage page) {
    return codeValueGeneralRepository.findAllByCodeSetNameOrdered(RACE_CODE_SET_NM,
        toPageable(page, maxPageSize));
  }

  @QueryMapping()
  public Page<CodeValueGeneral> findAllEthnicityValues(@Argument GraphQLPage page) {
    return codeValueGeneralRepository.findAllByCodeSetName(ETHNICITY_CODE_SET_NM,
        toPageable(page, maxPageSize));
  }

  @QueryMapping()
  public Page<CodeValueGeneral> findAllPatientIdentificationTypes(@Argument GraphQLPage page) {
    return codeValueGeneralRepository.findAllByCodeSetName(PATIENT_IDENTIFICATION_TYPES,
        toPageable(page, maxPageSize));
  }

  @QueryMapping()
  public Page<CodeValueGeneral> findAllOutbreaks(@Argument GraphQLPage page) {
    return codeValueGeneralRepository.findAllByCodeSetName(OUTBREAK_CODE_SET_NM,
        toPageable(page, maxPageSize));
  }

}
