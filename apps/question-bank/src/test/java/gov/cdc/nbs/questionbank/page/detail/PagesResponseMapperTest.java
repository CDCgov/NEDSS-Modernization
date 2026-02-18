package gov.cdc.nbs.questionbank.page.detail;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import gov.cdc.nbs.questionbank.page.component.*;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class PagesResponseMapperTest {

  public static final int ROLLINGNOTE = 1019;

  PagesResponseMapper mapper = new PagesResponseMapper();

  @Test
  void should_map_page_to_response() {
    PageDescription description =
        new PageDescription(
            1381L, "page-name-value", "page-status-value", "page-description-value");

    PagesResponse response = mapper.asResponse(description, List.of());

    assertThat(response.id()).isEqualTo(1381L);
    assertThat(response.name()).isEqualTo("page-name-value");
    assertThat(response.status()).isEqualTo("page-status-value");
    assertThat(response.description()).isEqualTo("page-description-value");
    assertThat(response.root()).isZero();
  }

  @Test
  void should_map_page_with_rules_to_response() {
    PageDescription description = mock(PageDescription.class);

    List<PagesRule> rules =
        List.of(
            new PagesRule(
                1087L,
                1381L,
                "logic-value",
                "values-value",
                "function-value",
                "source-value",
                "target-value"));

    PagesResponse response = mapper.asResponse(description, rules);

    assertThat(response.rules())
        .satisfiesExactly(
            rule ->
                assertAll(
                    () -> assertThat(rule.id()).isEqualTo(1087L),
                    () -> assertThat(rule.page()).isEqualTo(1381L),
                    () -> assertThat(rule.logic()).isEqualTo("logic-value"),
                    () -> assertThat(rule.values()).isEqualTo("values-value"),
                    () -> assertThat(rule.function()).isEqualTo("function-value"),
                    () -> assertThat(rule.sourceField()).isEqualTo("source-value"),
                    () -> assertThat(rule.targetField()).isEqualTo("target-value")));
  }

  @Test
  void should_map_page_with_tab() {

    PageNode page =
        new PageNode(1663L, new ComponentNode.Definition("page-name-value", true, 1))
            .add(new TabNode(1667L, new ComponentNode.Definition("tab-name-value", true, 2)));

    PageDescription description = mock(PageDescription.class);

    PagesResponse response = mapper.asResponse(description, List.of(), page);

    assertThat(response.root()).isEqualTo(1663L);

    assertThat(response.tabs())
        .satisfiesExactly(
            tab ->
                assertAll(
                    () -> assertThat(tab.id()).isEqualTo(1667L),
                    () -> assertThat(tab.name()).isEqualTo("tab-name-value"),
                    () -> assertThat(tab.visible()).isTrue(),
                    () -> assertThat(tab.order()).isEqualTo(2)));
  }

  @Test
  void should_map_page_with_section() {

    PageNode page =
        new PageNode(1663L, new ComponentNode.Definition("page-name-value", true, 1))
            .add(
                new TabNode(1667L, new ComponentNode.Definition("tab-name-value", true, 2))
                    .add(
                        new SectionNode(
                            1669L, new ComponentNode.Definition("section-name-value", true, 3))));

    PageDescription description = mock(PageDescription.class);

    PagesResponse response = mapper.asResponse(description, List.of(), page);

    assertThat(response.root()).isEqualTo(1663L);

    assertThat(response.tabs())
        .satisfiesExactly(
            tab ->
                assertThat(tab.sections())
                    .satisfiesExactly(
                        section ->
                            assertAll(
                                () -> assertThat(section.id()).isEqualTo(1669L),
                                () -> assertThat(section.name()).isEqualTo("section-name-value"),
                                () -> assertThat(section.visible()).isTrue(),
                                () -> assertThat(section.order()).isEqualTo(3))));
  }

  @Test
  void should_map_page_with_sub_section() {

    PageNode page =
        new PageNode(1663L, new ComponentNode.Definition("page-name-value", true, 1))
            .add(
                new TabNode(1667L, new ComponentNode.Definition("tab-name-value", true, 2))
                    .add(
                        new SectionNode(
                                1669L, new ComponentNode.Definition("section-name-value", true, 3))
                            .add(
                                new SubSectionNode(
                                    1693L,
                                    new ComponentNode.Definition("sub-section-name-value", true, 4),
                                    false,
                                    "identifier",
                                    "block name",
                                    3))));

    PageDescription description = mock(PageDescription.class);

    PagesResponse response = mapper.asResponse(description, List.of(), page);

    assertThat(response.root()).isEqualTo(1663L);

    assertThat(response.tabs())
        .satisfiesExactly(
            tab ->
                assertThat(tab.sections())
                    .satisfiesExactly(
                        section ->
                            assertThat(section.subSections())
                                .satisfiesExactly(
                                    subSection ->
                                        assertAll(
                                            () -> assertThat(subSection.id()).isEqualTo(1693L),
                                            () ->
                                                assertThat(subSection.name())
                                                    .isEqualTo("sub-section-name-value"),
                                            () -> assertThat(subSection.visible()).isTrue(),
                                            () ->
                                                assertThat(subSection.questionIdentifier())
                                                    .isEqualTo("identifier"),
                                            () ->
                                                assertThat(subSection.blockName())
                                                    .isEqualTo("block name"),
                                            () -> assertThat(subSection.order()).isEqualTo(4)))));
  }

  @Test
  void should_map_page_with_question() {

    PageNode page =
        new PageNode(1663L, new ComponentNode.Definition("page-name-value", true, 1))
            .add(
                new TabNode(1667L, new ComponentNode.Definition("tab-name-value", true, 2))
                    .add(
                        new SectionNode(
                                1669L, new ComponentNode.Definition("section-name-value", true, 3))
                            .add(
                                new SubSectionNode(
                                        1693L,
                                        new ComponentNode.Definition(
                                            "sub-section-name-value", true, 4),
                                        false,
                                        "identifier",
                                        null,
                                        null)
                                    .add(
                                        new StaticNode(
                                            1697L,
                                            StaticNode.Type.HYPERLINK,
                                            new ComponentNode.Definition(
                                                "question-name-value", true, 5),
                                            new ContentNode.Attributes(
                                                true,
                                                true,
                                                "standard-value",
                                                "question-value",
                                                "data-type-value",
                                                "sub-group-value",
                                                "description-value",
                                                true,
                                                false,
                                                true,
                                                "mask-value",
                                                "tool-tip-value",
                                                "default-value-value",
                                                "admin-comments",
                                                "10",
                                                "default-rdb-table-name",
                                                "rdb-column-name",
                                                "default-label-in-report",
                                                "dataMart-column-name",
                                                1007,
                                                "data_location",
                                                false,
                                                1,
                                                "block_name",
                                                null,
                                                false,
                                                null,
                                                null,
                                                null,
                                                null,
                                                null))))));

    PageDescription description = mock(PageDescription.class);

    PagesResponse response = mapper.asResponse(description, List.of(), page);

    assertThat(response.root()).isEqualTo(1663L);

    assertThat(response.tabs())
        .satisfiesExactly(
            tab ->
                assertThat(tab.sections())
                    .satisfiesExactly(
                        section ->
                            assertThat(section.subSections())
                                .satisfiesExactly(
                                    subSection ->
                                        assertThat(subSection.questions())
                                            .satisfiesExactly(
                                                question ->
                                                    assertAll(
                                                        () ->
                                                            assertThat(question.id())
                                                                .isEqualTo(1697L),
                                                        () ->
                                                            assertThat(question.name())
                                                                .isEqualTo("question-name-value"),
                                                        () ->
                                                            assertThat(question.order())
                                                                .isEqualTo(5),
                                                        () ->
                                                            assertThat(question.isStandard())
                                                                .isTrue(),
                                                        () ->
                                                            assertThat(question.standard())
                                                                .isEqualTo("standard-value"),
                                                        () ->
                                                            assertThat(question.question())
                                                                .isEqualTo("question-value"),
                                                        () ->
                                                            assertThat(question.dataType())
                                                                .isEqualTo("data-type-value"),
                                                        () ->
                                                            assertThat(question.subGroup())
                                                                .isEqualTo("sub-group-value"),
                                                        () ->
                                                            assertThat(question.description())
                                                                .isEqualTo("description-value"),
                                                        () ->
                                                            assertThat(question.enabled()).isTrue(),
                                                        () ->
                                                            assertThat(question.required())
                                                                .isFalse(),
                                                        () ->
                                                            assertThat(question.coInfection())
                                                                .isTrue(),
                                                        () ->
                                                            assertThat(question.mask())
                                                                .isEqualTo("mask-value"),
                                                        () ->
                                                            assertThat(question.tooltip())
                                                                .isEqualTo("tool-tip-value"),
                                                        () ->
                                                            assertThat(question.defaultValue())
                                                                .isEqualTo("default-value-value"),
                                                        () ->
                                                            assertThat(question.displayComponent())
                                                                .isEqualTo(1003L))))));
  }

  @Test
  void return_is_groupable_subsection() {
    ContentNode.Attributes question1 = mock(ContentNode.Attributes.class);
    ContentNode.Attributes question2 = mock(ContentNode.Attributes.class);
    ContentNode contentNode1 = mock(InputNode.class);
    ContentNode contentNode2 = mock(InputNode.class);
    SubSectionNode subsection = mock(SubSectionNode.class);

    when(question1.dataLocation()).thenReturn("test_ANSWER_TXT_test");
    when(question2.dataLocation()).thenReturn("test_ANSWER_TXT_test");
    when(question1.isPublished()).thenReturn(false);
    when(question2.isPublished()).thenReturn(false);
    when(question1.nbsComponentId()).thenReturn(1007);
    when(question2.nbsComponentId()).thenReturn(1008);
    when(contentNode1.attributes()).thenReturn(question1);
    when(contentNode2.attributes()).thenReturn(question2);
    when(subsection.children()).thenReturn(Arrays.asList(contentNode1, contentNode2));
    boolean result = mapper.isSubsectionGrouable(subsection);
    assertTrue(result);
  }

  @Test
  void return_is_not_groupable_subsection_include_core() {
    ContentNode.Attributes question1 = mock(ContentNode.Attributes.class);
    ContentNode contentNode1 = mock(InputNode.class);
    SubSectionNode subsection = mock(SubSectionNode.class);

    when(question1.dataLocation()).thenReturn("test_ANSWER_TXT_test");
    when(question1.isPublished()).thenReturn(true);
    when(question1.nbsComponentId()).thenReturn(1007);
    when(contentNode1.attributes()).thenReturn(question1);
    when(subsection.children()).thenReturn(Arrays.asList(contentNode1));

    boolean result = mapper.isSubsectionGrouable(subsection);
    assertFalse(result);
  }

  @Test
  void return_is_not_groupable_subsection_include_published_question() {
    ContentNode.Attributes question1 = mock(ContentNode.Attributes.class);
    ContentNode contentNode1 = mock(InputNode.class);
    SubSectionNode subsection = mock(SubSectionNode.class);

    when(question1.isPublished()).thenReturn(false);
    when(question1.nbsComponentId()).thenReturn(1007);
    when(contentNode1.attributes()).thenReturn(question1);
    when(subsection.children()).thenReturn(Arrays.asList(contentNode1));
    when(question1.dataLocation()).thenReturn("test");
    boolean invalidDataLocation = mapper.isSubsectionGrouable(subsection);
    assertFalse(invalidDataLocation);

    when(question1.dataLocation()).thenReturn(null);
    boolean nullDataLocation = mapper.isSubsectionGrouable(subsection);
    assertFalse(nullDataLocation);
  }

  @Test
  void return_is_not_groupable_subsection_include_not_only_rolling_question() {
    ContentNode.Attributes question1 = mock(ContentNode.Attributes.class);
    ContentNode.Attributes question2 = mock(ContentNode.Attributes.class);
    ContentNode contentNode1 = mock(InputNode.class);
    ContentNode contentNode2 = mock(InputNode.class);
    SubSectionNode subsection = mock(SubSectionNode.class);

    when(question1.dataLocation()).thenReturn("test_ANSWER_TXT_test");
    when(question2.dataLocation()).thenReturn("test_ANSWER_TXT_test");
    when(question1.isPublished()).thenReturn(false);
    when(question2.isPublished()).thenReturn(false);
    when(question1.nbsComponentId()).thenReturn(1007);
    when(question2.nbsComponentId()).thenReturn(ROLLINGNOTE);
    when(contentNode1.attributes()).thenReturn(question1);
    when(contentNode2.attributes()).thenReturn(question2);
    when(subsection.children()).thenReturn(Arrays.asList(contentNode1, contentNode2));
    boolean result = mapper.isSubsectionGrouable(subsection);
    assertFalse(result);
  }

  @Test
  void return_is_groupable_subsection_include_only_rolling_question() {
    ContentNode.Attributes question1 = mock(ContentNode.Attributes.class);
    ContentNode contentNode1 = mock(InputNode.class);
    SubSectionNode subsection = mock(SubSectionNode.class);

    when(question1.isPublished()).thenReturn(false);
    when(question1.nbsComponentId()).thenReturn(ROLLINGNOTE);
    when(contentNode1.attributes()).thenReturn(question1);
    when(subsection.children()).thenReturn(Arrays.asList(contentNode1));
    when(question1.dataLocation()).thenReturn("test_ANSWER_TXT_test");
    boolean result = mapper.isSubsectionGrouable(subsection);
    assertTrue(result);
  }
}
