package gov.cdc.nbs.questionbank.page.content;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import gov.cdc.nbs.id.IdGeneratorService;
import gov.cdc.nbs.id.IdGeneratorService.EntityType;
import gov.cdc.nbs.id.IdGeneratorService.GeneratedId;
import gov.cdc.nbs.questionbank.entity.NbsConfiguration;
import gov.cdc.nbs.questionbank.page.exception.PageContentIdGenerationException;
import gov.cdc.nbs.questionbank.question.repository.NbsConfigurationRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PageContentIdGeneratorTest {

  @Mock private IdGeneratorService idGenerator;
  @Mock private NbsConfigurationRepository configRepository;

  @InjectMocks private PageContentIdGenerator generator;

  @Test
  void should_return_id() {
    // Given a valid config
    when(configRepository.findById("NBS_CLASS_CODE")).thenReturn(Optional.of(configEntry()));

    // And a valid id generator
    when(idGenerator.getNextValidId(EntityType.NBS_QUESTION_ID_LDF))
        .thenReturn(new GeneratedId(1000l, "prefix", "suffix"));

    // When a request is made for an id
    String id = generator.next();

    // Then the proper id is returned
    assertEquals("TEST_CONFIG_VALUE1000", id);
  }

  private NbsConfiguration configEntry() {
    NbsConfiguration configEntry = new NbsConfiguration();
    configEntry.setConfigValue("TEST_CONFIG_VALUE");
    return configEntry;
  }

  @Test
  void should_throw_exception_for_invalid_config() {
    // Given a invalid config
    when(configRepository.findById("NBS_CLASS_CODE")).thenReturn(Optional.empty());

    // When a request is made for an id
    // Then an exception should be thrown if no config value is returned
    assertThrows(PageContentIdGenerationException.class, () -> generator.next());
  }
}
