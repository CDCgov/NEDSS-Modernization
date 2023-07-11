package gov.cdc.nbs.questionbank.page;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import java.time.Instant;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.entity.PageCondMapping;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.repository.WaTemplateRepository;
import gov.cdc.nbs.questionbank.page.exception.PageNotFoundException;
import gov.cdc.nbs.questionbank.page.exception.PageUpdateException;
import gov.cdc.nbs.questionbank.page.request.UpdatePageDetailsRequest;

@ExtendWith(MockitoExtension.class)
class PageUpdaterTest {

    @Mock
    private WaTemplateRepository repository;

    @Mock
    private UserDetailsProvider userDetailsProvider;

    @InjectMocks
    private PageUpdater updater;

    @Test
    void should_throw_not_found_exception() {
        // when no page exists
        when(repository.findByIdAndTemplateTypeIn(eq(1L), Mockito.any())).thenReturn(Optional.empty());

        // then a not found exception is thrown
        assertThrows(PageNotFoundException.class, () -> updater.update(1L, null));
    }

    @Test
    void should_not_remove_conditions() {
        when(repository.save(Mockito.any())).thenAnswer(a -> a.getArgument(0));

        // given I am logged in
        when(userDetailsProvider.getCurrentUserDetails()).thenReturn((userDetails()));

        // given a page exists
        when(repository.findByIdAndTemplateTypeIn(1L, List.of("Draft", "Published")))
                .thenReturn(Optional.of(template("Published", null)));

        // when an update request is sent
        UpdatePageDetailsRequest request = updateRequest();
        WaTemplate updatedTemplate = updater.update(1L, request);

        // Then the supplied condition is added and the existing one is not removed
        assertEquals(2, updatedTemplate.getConditionMappings().size());
    }

    @Test
    void should_remove_conditions() {
        when(repository.save(Mockito.any())).thenAnswer(a -> a.getArgument(0));

        // given I am logged in
        when(userDetailsProvider.getCurrentUserDetails()).thenReturn((userDetails()));

        // given a page exists
        when(repository.findByIdAndTemplateTypeIn(1L, List.of("Draft", "Published")))
                .thenReturn(Optional.of(template("Draft", null)));

        // when an update request is sent
        UpdatePageDetailsRequest request = updateRequest();
        WaTemplate updatedTemplate = updater.update(1L, request);

        // Then the supplied condition is added and the existing one is not removed
        assertEquals(1, updatedTemplate.getConditionMappings().size());
        assertEquals("new condition", updatedTemplate.getConditionMappings().iterator().next().getConditionCd());
    }

    @Test
    void should_not_change_datamart_name() {
        when(repository.save(Mockito.any())).thenAnswer(a -> a.getArgument(0));

        // given I am logged in
        when(userDetailsProvider.getCurrentUserDetails()).thenReturn((userDetails()));

        // given a page exists
        when(repository.findByIdAndTemplateTypeIn(1L, List.of("Draft", "Published")))
                .thenReturn(Optional.of(template("Published", null)));

        // when an update request is sent
        UpdatePageDetailsRequest request = updateRequest();
        WaTemplate updatedTemplate = updater.update(1L, request);

        // then the datamart is not changed
        assertEquals("original data mart", updatedTemplate.getDatamartNm());
    }

    @Test
    void should_not_change_datamart_name_publish_version() {
        when(repository.save(Mockito.any())).thenAnswer(a -> a.getArgument(0));

        // given I am logged in
        when(userDetailsProvider.getCurrentUserDetails()).thenReturn((userDetails()));

        // given a page exists
        when(repository.findByIdAndTemplateTypeIn(1L, List.of("Draft", "Published")))
                .thenReturn(Optional.of(template("Draft", 1)));

        // when an update request is sent
        UpdatePageDetailsRequest request = updateRequest();
        WaTemplate updatedTemplate = updater.update(1L, request);

        // then the datamart is not changed
        assertEquals("original data mart", updatedTemplate.getDatamartNm());
    }

    @Test
    void should_change_datamart_name() {
        when(repository.save(Mockito.any())).thenAnswer(a -> a.getArgument(0));

        // given I am logged in
        when(userDetailsProvider.getCurrentUserDetails()).thenReturn((userDetails()));

        // given a page exists with templateType of "Draft" and null publishVersionNbr
        when(repository.findByIdAndTemplateTypeIn(1L, List.of("Draft", "Published")))
                .thenReturn(Optional.of(template("Draft", null)));

        // when an update request is sent
        UpdatePageDetailsRequest request = updateRequest();
        WaTemplate updatedTemplate = updater.update(1L, request);

        // then the datamart is not changed
        assertEquals("updated datamart", updatedTemplate.getDatamartNm());
    }

    @Test
    void should_not_add_condition() {
        when(repository.save(Mockito.any())).thenAnswer(a -> a.getArgument(0));

        // given I am logged in
        when(userDetailsProvider.getCurrentUserDetails()).thenReturn((userDetails()));

        // given a page exists with templateType of "Draft" and null publishVersionNbr
        when(repository.findByIdAndTemplateTypeIn(1L, List.of("Draft", "Published")))
                .thenReturn(Optional.of(template("Draft", null)));

        // when an update request is sent
        UpdatePageDetailsRequest request = new UpdatePageDetailsRequest(
                "updated name",
                "updated mmg",
                "updated datamart",
                "updated description",
                Collections.singleton("conditionCd"));
        WaTemplate updatedTemplate = updater.update(1L, request);

        // then the condition is not changed
        assertEquals(1, updatedTemplate.getConditionMappings().size());
        PageCondMapping mapping = updatedTemplate.getConditionMappings().iterator().next();
        assertEquals(2L, mapping.getId().longValue());
        assertEquals("conditionCd", mapping.getConditionCd());
    }

    @Test
    void should_throw_bad_request_duplicate_name() {
        // given a page exists with templateType of "Draft" and null publishVersionNbr
        when(repository.findByIdAndTemplateTypeIn(1L, List.of("Draft", "Published")))
                .thenReturn(Optional.of(template("Draft", null)));

        // the datamart name doesn't exist
        when(repository.existsByDatamartNmAndIdNot("updated datamart", 1L)).thenReturn(false);

        // and a page with the updated name already exists
        when(repository.existsByTemplateNmAndIdNot("updated name", 1L)).thenReturn(true);

        // when an update request is sent
        UpdatePageDetailsRequest request = new UpdatePageDetailsRequest(
                "updated name",
                "updated mmg",
                "updated datamart",
                "updated description",
                Collections.singleton("conditionCd"));
        PageUpdateException ex = assertThrows( PageUpdateException.class, () ->updater.update(1L, request));
        assertEquals("The specified Page Name already exists", ex.getMessage());
    }

    @Test
    void should_throw_bad_request_duplicate_data_mart_name() {
        // given a page exists with templateType of "Draft" and null publishVersionNbr
        when(repository.findByIdAndTemplateTypeIn(1L, List.of("Draft", "Published")))
                .thenReturn(Optional.of(template("Draft", null)));

        // and a page with the updated name already exists
        when(repository.existsByDatamartNmAndIdNot("updated datamart", 1L)).thenReturn(true);

        // when an update request is sent
        UpdatePageDetailsRequest request = new UpdatePageDetailsRequest(
                "updated name",
                "updated mmg",
                "updated datamart",
                "updated description",
                Collections.singleton("conditionCd"));
        PageUpdateException ex = assertThrows( PageUpdateException.class, () ->updater.update(1L, request));
        assertEquals("The specified Data Mart Name already exists", ex.getMessage());

    }


    private WaTemplate template(String templateType, Integer publishVersionNbr) {
        WaTemplate page = new WaTemplate();
        page.setId(1L);
        page.setBusObjType("INV");
        page.setDatamartNm("original data mart");
        page.setTemplateNm("template name");
        page.setNndEntityIdentifier("original mmg");
        page.setDescTxt("original description");
        page.setConditionMappings(conditionMappings(page));
        page.setTemplateType(templateType);
        page.setPublishVersionNbr(publishVersionNbr);
        return page;
    }

    private Set<PageCondMapping> conditionMappings(WaTemplate template) {
        Instant now = Instant.now();
        Set<PageCondMapping> mappings = new HashSet<>();
        mappings.add(
                new PageCondMapping(
                        2L,
                        template,
                        "conditionCd",
                        now,
                        100L,
                        now,
                        100L));
        return mappings;
    }

    private UpdatePageDetailsRequest updateRequest() {
        return new UpdatePageDetailsRequest(
                "updated name",
                "updated mmg",
                "updated datamart",
                "updated description",
                Collections.singleton("new condition"));
    }

    private NbsUserDetails userDetails() {
        return new NbsUserDetails(
                23L,
                "test",
                "test",
                "test",
                false,
                false,
                null,
                null,
                null,
                "token",
                true);
    }
}
