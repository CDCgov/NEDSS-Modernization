package gov.cdc.nbs.questionbank.page.detail;

import gov.cdc.nbs.questionbank.page.component.ComponentNode;
import gov.cdc.nbs.questionbank.page.component.ContentNode;
import gov.cdc.nbs.questionbank.page.component.PageNode;
import gov.cdc.nbs.questionbank.page.component.SectionNode;
import gov.cdc.nbs.questionbank.page.component.StaticNode;
import gov.cdc.nbs.questionbank.page.component.SubSectionNode;
import gov.cdc.nbs.questionbank.page.component.TabNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.mock;

class PagesResponseMapperTest {

  @Test
  void should_map_page_to_response() {
    PageDescription description = new PageDescription(
        1381L,
        "page-name-value",
        "page-status-value",
        "page-description-value"
    );

    PagesResponseMapper mapper = new PagesResponseMapper();

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

    List<PagesRule> rules = List.of(
        new PagesRule(
            1087L,
            1381L,
            "logic-value",
            "values-value",
            "function-value",
            "source-value",
            "target-value"
        )
    );

    PagesResponseMapper mapper = new PagesResponseMapper();

    PagesResponse response = mapper.asResponse(description, rules);

    assertThat(response.rules()).satisfiesExactly(
        rule -> assertAll(
            () -> assertThat(rule.id()).isEqualTo(1087L),
            () -> assertThat(rule.page()).isEqualTo(1381L),
            () -> assertThat(rule.logic()).isEqualTo("logic-value"),
            () -> assertThat(rule.values()).isEqualTo("values-value"),
            () -> assertThat(rule.function()).isEqualTo("function-value"),
            () -> assertThat(rule.sourceField()).isEqualTo("source-value"),
            () -> assertThat(rule.targetField()).isEqualTo("target-value")
        )
    );
  }

  @Test
  void should_map_page_with_tab() {

    PageNode page = new PageNode(
        1663L,
        new ComponentNode.Definition(
            "page-name-value",
            true,
            1
        )
    ).add(
        new TabNode(
            1667L,
            new ComponentNode.Definition(
                "tab-name-value",
                true,
                2
            )
        )
    );

    PageDescription description = mock(PageDescription.class);

    PagesResponseMapper mapper = new PagesResponseMapper();

    PagesResponse response = mapper.asResponse(description, List.of(), page);

    assertThat(response.root()).isEqualTo(1663L);

    assertThat(response.tabs()).satisfiesExactly(
        tab -> assertAll(
            () -> assertThat(tab.id()).isEqualTo(1667L),
            () -> assertThat(tab.name()).isEqualTo("tab-name-value"),
            () -> assertThat(tab.visible()).isTrue(),
            () -> assertThat(tab.order()).isEqualTo(2)
        )
    );

  }

  @Test
  void should_map_page_with_section() {

    PageNode page = new PageNode(
        1663L,
        new ComponentNode.Definition(
            "page-name-value",
            true,
            1
        )
    ).add(
        new TabNode(
            1667L,
            new ComponentNode.Definition(
                "tab-name-value",
                true,
                2
            )
        ).add(
            new SectionNode(
                1669L,
                new ComponentNode.Definition(
                    "section-name-value",
                    true,
                    3
                )
            )
        )
    );

    PageDescription description = mock(PageDescription.class);

    PagesResponseMapper mapper = new PagesResponseMapper();

    PagesResponse response = mapper.asResponse(description, List.of(), page);

    assertThat(response.root()).isEqualTo(1663L);

    assertThat(response.tabs()).satisfiesExactly(
        tab -> assertThat(tab.sections())
            .satisfiesExactly(
                section -> assertAll(
                    () -> assertThat(section.id()).isEqualTo(1669L),
                    () -> assertThat(section.name()).isEqualTo("section-name-value"),
                    () -> assertThat(section.visible()).isTrue(),
                    () -> assertThat(section.order()).isEqualTo(3)
                )
            )
    );

  }

  @Test
  void should_map_page_with_sub_section() {

    PageNode page = new PageNode(
        1663L,
        new ComponentNode.Definition(
            "page-name-value",
            true,
            1
        )
    ).add(
        new TabNode(
            1667L,
            new ComponentNode.Definition(
                "tab-name-value",
                true,
                2
            )
        ).add(
            new SectionNode(
                1669L,
                new ComponentNode.Definition(
                    "section-name-value",
                    true,
                    3
                )
            ).add(
                new SubSectionNode(
                    1693L,
                    new ComponentNode.Definition(
                        "sub-section-name-value",
                        true,
                        4
                    )
                )
            )
        )
    );

    PageDescription description = mock(PageDescription.class);

    PagesResponseMapper mapper = new PagesResponseMapper();

    PagesResponse response = mapper.asResponse(description, List.of(), page);

    assertThat(response.root()).isEqualTo(1663L);

    assertThat(response.tabs()).satisfiesExactly(
        tab -> assertThat(tab.sections())
            .satisfiesExactly(
                section -> assertThat(section.subSections())
                    .satisfiesExactly(
                        subSection -> assertAll(
                            () -> assertThat(subSection.id()).isEqualTo(1693L),
                            () -> assertThat(subSection.name()).isEqualTo("sub-section-name-value"),
                            () -> assertThat(subSection.visible()).isTrue(),
                            () -> assertThat(subSection.order()).isEqualTo(4)
                        )
                    )
            )
    );

  }

  @Test
  void should_map_page_with_question() {

    PageNode page = new PageNode(
        1663L,
        new ComponentNode.Definition(
            "page-name-value",
            true,
            1
        )
    ).add(
        new TabNode(
            1667L,
            new ComponentNode.Definition(
                "tab-name-value",
                true,
                2
            )
        ).add(
            new SectionNode(
                1669L,
                new ComponentNode.Definition(
                    "section-name-value",
                    true,
                    3
                )
            ).add(
                new SubSectionNode(
                    1693L,
                    new ComponentNode.Definition(
                        "sub-section-name-value",
                        true,
                        4
                    )
                ).add(
                    new StaticNode(
                        1697L,
                        StaticNode.Type.HYPERLINK,
                        new ComponentNode.Definition(
                            "question-name-value",
                            true,
                            5
                        ),
                        new ContentNode.Attributes(
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
                            "default-value-value"
                        )
                    )
                )
            )
        )
    );

    PageDescription description = mock(PageDescription.class);

    PagesResponseMapper mapper = new PagesResponseMapper();

    PagesResponse response = mapper.asResponse(description, List.of(), page);

    assertThat(response.root()).isEqualTo(1663L);

    assertThat(response.tabs()).satisfiesExactly(
        tab -> assertThat(tab.sections())
            .satisfiesExactly(
                section -> assertThat(section.subSections())
                    .satisfiesExactly(
                        subSection -> assertThat(subSection.questions()).satisfiesExactly(
                            question -> assertAll(
                                () -> assertThat(question.id()).isEqualTo(1697L),
                                () -> assertThat(question.name()).isEqualTo("question-name-value"),
                                () -> assertThat(question.order()).isEqualTo(5),
                                () -> assertThat(question.isStandard()).isTrue(),
                                () -> assertThat(question.standard()).isEqualTo("standard-value"),
                                () -> assertThat(question.question()).isEqualTo("question-value"),
                                () -> assertThat(question.dataType()).isEqualTo("data-type-value"),
                                () -> assertThat(question.subGroup()).isEqualTo("sub-group-value"),
                                () -> assertThat(question.description()).isEqualTo("description-value"),
                                () -> assertThat(question.enabled()).isTrue(),
                                () -> assertThat(question.required()).isFalse(),
                                () -> assertThat(question.coInfection()).isTrue(),
                                () -> assertThat(question.mask()).isEqualTo("mask-value"),
                                () -> assertThat(question.tooltip()).isEqualTo("tool-tip-value"),
                                () -> assertThat(question.defaultValue()).isEqualTo("default-value-value"),
                                () -> assertThat(question.displayComponent()).isEqualTo(1003L)
                            )
                        )
                    )
            )
    );

  }
}

