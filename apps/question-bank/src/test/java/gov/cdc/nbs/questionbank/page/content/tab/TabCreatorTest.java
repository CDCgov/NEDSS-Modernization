package gov.cdc.nbs.questionbank.page.content.tab;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import gov.cdc.nbs.id.IdGeneratorService;
import gov.cdc.nbs.id.IdGeneratorService.EntityType;
import gov.cdc.nbs.id.IdGeneratorService.GeneratedId;
import gov.cdc.nbs.questionbank.entity.NbsConfiguration;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.entity.repository.WaTemplateRepository;
import gov.cdc.nbs.questionbank.page.content.tab.exceptions.CreateTabException;
import gov.cdc.nbs.questionbank.page.content.tab.repository.WaUiMetaDataRepository;
import gov.cdc.nbs.questionbank.page.content.tab.request.CreateTabRequest;
import gov.cdc.nbs.questionbank.page.content.tab.response.Tab;
import gov.cdc.nbs.questionbank.question.repository.NbsConfigurationRepository;

@ExtendWith(MockitoExtension.class)
class TabCreatorTest {
    @Mock
    private WaUiMetaDataRepository repository;
    @Mock
    private WaTemplateRepository templateRepository;
    @Mock
    private IdGeneratorService idGenerator;
    @Mock
    private NbsConfigurationRepository configRepository;

    @InjectMocks
    private TabCreator creator;

    @Test
    void should_create_tab() {
        // Given a page that is a draft
        when(templateRepository.isPageDraft(1l)).thenReturn(true);
        when(templateRepository.getReferenceById(1l)).thenReturn(new WaTemplate());
        when(repository.findMaxOrderNumberByTemplateUid(1l)).thenReturn(Optional.of(1));
        ArgumentCaptor<WaUiMetadata> captor = ArgumentCaptor.forClass(WaUiMetadata.class);
        when(repository.save(captor.capture())).then(a -> a.getArgument(0));

        // And a valid configRepository
        when(configRepository.findById("NBS_CLASS_CODE")).thenReturn(Optional.of(config()));

        // And a configured Id service
        when(idGenerator.getNextValidId(EntityType.NBS_QUESTION_ID_LDF)).thenReturn(new GeneratedId(123l,"pre","suf"));

        // When a request is processed to create a tab
        Tab actual = creator.create(1l, 2l, new CreateTabRequest("tab name", true));

        // Then a tab is created
        assertNotNull(actual);
        assertEquals("tab name", actual.name());
        assertEquals(true, actual.visible());
        assertEquals("TEST123", captor.getValue().getQuestionIdentifier());
    }

    @Test
    void should_not_create_tab() {
        // Given a page that is not draft
        when(templateRepository.isPageDraft(1l)).thenReturn(false);

        // When a request is processed to create a tab
        // Then an exception is thrown
        assertThrows(CreateTabException.class, () -> creator.create(1l, 2l, null));
    }

    private NbsConfiguration config() {
        NbsConfiguration config = new NbsConfiguration();
        config.setConfigValue("TEST");
        return config;
    }
}
