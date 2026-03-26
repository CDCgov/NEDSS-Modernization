package gov.cdc.nbs.questionbank.page.content;

import gov.cdc.nbs.id.IdGeneratorService;
import gov.cdc.nbs.id.IdGeneratorService.EntityType;
import gov.cdc.nbs.questionbank.page.exception.PageContentIdGenerationException;
import gov.cdc.nbs.questionbank.question.repository.NbsConfigurationRepository;
import org.springframework.stereotype.Component;

@Component
public class PageContentIdGenerator {

  private final IdGeneratorService idGenerator;
  private final NbsConfigurationRepository configRepository;

  public PageContentIdGenerator(
      final IdGeneratorService idGenerator, final NbsConfigurationRepository configRepository) {
    this.idGenerator = idGenerator;
    this.configRepository = configRepository;
  }

  /**
   * Ids are a combination of the NBS_ODSE.NBS_configuration.NBS_CLASS_CODE value and the next valid
   * Id from the NBS_ODSE.Local_UID_generator NBS_QUESTION_ID_LDF row
   *
   * <p>Example: GA10001 Id generation copied from NBS Classic PageManagementActionUtil #1729
   *
   * @return String
   */
  public String next() {
    String classCode =
        configRepository
            .findById("NBS_CLASS_CODE")
            .orElseThrow(PageContentIdGenerationException::new)
            .getConfigValue();
    var id = idGenerator.getNextValidId(EntityType.NBS_QUESTION_ID_LDF).getId();
    return classCode + id;
  }
}
