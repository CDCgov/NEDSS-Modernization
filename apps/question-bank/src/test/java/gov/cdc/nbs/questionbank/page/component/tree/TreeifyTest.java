package gov.cdc.nbs.questionbank.page.component.tree;

import gov.cdc.nbs.questionbank.page.component.ComponentNode;
import gov.cdc.nbs.questionbank.page.component.ContentNode;
import gov.cdc.nbs.questionbank.page.component.PageNode;
import gov.cdc.nbs.questionbank.page.component.SectionNode;
import gov.cdc.nbs.questionbank.page.component.SubSectionNode;
import gov.cdc.nbs.questionbank.page.component.TabNode;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.collection;
import static org.assertj.core.api.InstanceOfAssertFactories.type;

class TreeifyTest {

  @Test
  void should_reduce_to_empty_when_there_are_no_components() {

    Optional<ComponentNode> actual = Stream.<FlattenedComponent>empty()
        .collect(Treeify.asTree());

    assertThat(actual).isNotPresent();

  }

  @Test
  void should_reduce_to_a_single_page() {

    Optional<ComponentNode> actual = Stream.of(
        new FlattenedComponent(2L, 1002, "Page: Test", true, 1)
    ).collect(Treeify.asTree());

    assertThat(actual).hasValueSatisfying(
        page -> assertThat(page).isInstanceOf(PageNode.class)
    );

  }

  @Test
  void should_reduce_to_a_page_containing_a_tabs() {

    Optional<ComponentNode> actual = Stream.of(
        new FlattenedComponent(2L, 1002, "Page: Test", true, 1),
        new FlattenedComponent(3L, 1010, "Tab", true, 2)
    ).collect(Treeify.asTree());

    assertThat(actual).hasValueSatisfying(
        page -> assertThat(page).isInstanceOf(PageNode.class)
            .asInstanceOf(type(PageNode.class))
            .extracting(PageNode::children, collection(TabNode.class))
            .satisfiesExactly(
                tab -> assertThat(tab.identifier()).isEqualTo(3L)
            )
    );

  }

  @Test
  void should_reduce_to_a_page_containing_a_sibling_tabs() {

    Optional<ComponentNode> actual = Stream.of(
        new FlattenedComponent(2L, 1002, "Page: Test", true, 1),
        new FlattenedComponent(3L, 1010, "Tab One", true, 2),
        new FlattenedComponent(5L, 1010, "Tab Two", true, 3)
    ).collect(Treeify.asTree());

    assertThat(actual).hasValueSatisfying(
        page -> assertThat(page).isInstanceOf(PageNode.class)
            .asInstanceOf(type(PageNode.class))
            .extracting(PageNode::children, collection(TabNode.class))
            .satisfiesExactly(
                tab -> assertThat(tab.identifier()).isEqualTo(3L),
                tab -> assertThat(tab.identifier()).isEqualTo(5L)
            )
    );
  }

  @Test
  void should_reduce_to_a_page_containing_a_tab_with_one_section() {

    Optional<ComponentNode> actual = Stream.of(
        new FlattenedComponent(2L, 1002, "Page: Test", true, 1),
        new FlattenedComponent(3L, 1010, "Tab", true, 2),
        new FlattenedComponent(5L, 1015, "Section", true, 3)
    ).collect(Treeify.asTree());

    assertThat(actual).hasValueSatisfying(
        page -> assertThat(page).isInstanceOf(PageNode.class)
            .asInstanceOf(type(PageNode.class))
            .extracting(PageNode::children, collection(TabNode.class))
            .satisfiesExactly(
                tab -> assertThat(tab)
                    .extracting(TabNode::children, collection(SectionNode.class))
                    .satisfiesExactly(
                        section -> assertThat(section.identifier()).isEqualTo(5L)
                    )
            )
    );

  }

  @Test
  void should_reduce_to_a_page_containing_a_tab_with_sibling_sections() {

    Optional<ComponentNode> actual = Stream.of(
        new FlattenedComponent(2L, 1002, "Page: Test", true, 1),
        new FlattenedComponent(3L, 1010, "Tab", true, 2),
        new FlattenedComponent(5L, 1015, "Section One", true, 3),
        new FlattenedComponent(7L, 1015, "Section Two", true, 4)
    ).collect(Treeify.asTree());

    assertThat(actual).hasValueSatisfying(
        page -> assertThat(page).isInstanceOf(PageNode.class)
            .asInstanceOf(type(PageNode.class))
            .extracting(PageNode::children, collection(TabNode.class))
            .satisfiesExactly(
                tab -> assertThat(tab)
                    .extracting(TabNode::children, collection(SectionNode.class))
                    .satisfiesExactly(
                        section -> assertThat(section.identifier()).isEqualTo(5L),
                        section -> assertThat(section.identifier()).isEqualTo(7L)
                    )
            )
    );

  }

  @Test
  void should_reduce_to_a_page_containing_a_tab_containing_a_section_with_one_sub_section() {

    Optional<ComponentNode> actual = Stream.of(
        new FlattenedComponent(2L, 1002, "Page: Test", true, 1),
        new FlattenedComponent(3L, 1010, "Tab", true, 2),
        new FlattenedComponent(5L, 1015, "Section", true, 3),
        new FlattenedComponent(7L, 1016, "Sub Section", true, 4)
    ).collect(Treeify.asTree());

    assertThat(actual).hasValueSatisfying(
        page -> assertThat(page).isInstanceOf(PageNode.class)
            .asInstanceOf(type(PageNode.class))
            .extracting(PageNode::children, collection(TabNode.class))
            .satisfiesExactly(
                tab -> assertThat(tab)
                    .extracting(TabNode::children, collection(SectionNode.class))
                    .satisfiesExactly(
                        section -> assertThat(section).extracting(SectionNode::children, collection(SubSectionNode.class))
                            .satisfiesExactly(
                                subSection -> assertThat(subSection.identifier()).isEqualTo(7L)
                            )
                    )
            )
    );

  }

  @Test
  void should_reduce_to_a_page_containing_a_tab_containing_a_section_with_sibling_sub_sections() {

    Optional<ComponentNode> actual = Stream.of(
        new FlattenedComponent(2L, 1002, "Page: Test", true, 1),
        new FlattenedComponent(3L, 1010, "Tab", true, 2),
        new FlattenedComponent(5L, 1015, "Section", true, 3),
        new FlattenedComponent(7L, 1016, "Sub Section One", true, 4),
        new FlattenedComponent(11L, 1016, "Sub Section One", true, 5)
    ).collect(Treeify.asTree());

    assertThat(actual).hasValueSatisfying(
        page -> assertThat(page).isInstanceOf(PageNode.class)
            .asInstanceOf(type(PageNode.class))
            .extracting(PageNode::children, collection(TabNode.class))
            .satisfiesExactly(
                tab -> assertThat(tab)
                    .extracting(TabNode::children, collection(SectionNode.class))
                    .satisfiesExactly(
                        section -> assertThat(section).extracting(SectionNode::children, collection(SubSectionNode.class))
                            .satisfiesExactly(
                                subSection -> assertThat(subSection.identifier()).isEqualTo(7L),
                                subSection -> assertThat(subSection.identifier()).isEqualTo(11L)
                            )
                    )
            )
    );

  }

  @Test
  void should_reduce_to_a_page_containing_a_tab_containing_a_section_containing_a_sub_section_with_content() {

    Optional<ComponentNode> actual = Stream.of(
        new FlattenedComponent(2L, 1002, "Page: Test", true, 1),
        new FlattenedComponent(3L, 1010, "Tab", true, 2),
        new FlattenedComponent(5L, 1015, "Section", true, 3),
        new FlattenedComponent(7L, 1016, "Sub Section One", true, 4),
        new FlattenedComponent(
            11L,
            1007,
            "name-value",
            true,
            5,
            true,
            "standard-value",
            "question-value",
            "data-type-value",
            "sub-group-value",
            "description-value",
            true,
            true,
            false,
            true,
            "mask-value",
            "tool-tip-value",
            "default-value-value",
            "value-set-value"
        )
    ).collect(Treeify.asTree());

    assertThat(actual).hasValueSatisfying(
        page -> assertThat(page).isInstanceOf(PageNode.class)
            .asInstanceOf(type(PageNode.class))
            .extracting(PageNode::children, collection(TabNode.class))
            .satisfiesExactly(
                tab -> assertThat(tab)
                    .extracting(TabNode::children, collection(SectionNode.class))
                    .satisfiesExactly(
                        section -> assertThat(section).extracting(SectionNode::children, collection(SubSectionNode.class))
                            .satisfiesExactly(
                                subSection -> assertThat(subSection).extracting(SubSectionNode::children,
                                        collection(ContentNode.class))
                                    .satisfiesExactly(content -> assertThat(content.identifier()).isEqualTo(11L))
                            )
                    )
            )
    );

  }

  @Test
  void should_reduce_to_a_page_containing_two_tabs_when_adding_a_tab_after_content() {

    Optional<ComponentNode> actual = Stream.of(
        new FlattenedComponent(2L, 1002, "Page: Test", true, 1),
        new FlattenedComponent(3L, 1010, "Tab", true, 2),
        new FlattenedComponent(5L, 1015, "Section", true, 3),
        new FlattenedComponent(7L, 1016, "Sub Section One", true, 4),
        new FlattenedComponent(
            11L,
            1007,
            "name-value",
            true,
            5,
            true,
            "standard-value",
            "question-value",
            "data-type-value",
            "sub-group-value",
            "description-value",
            true,
            true,
            false,
            true,
            "mask-value",
            "tool-tip-value",
            "default-value-value",
            "value-set-value"
        ),
        new FlattenedComponent(13L, 1010, "Tab", true, 6)
    ).collect(Treeify.asTree());

    assertThat(actual).hasValueSatisfying(
        page -> assertThat(page).isInstanceOf(PageNode.class)
            .asInstanceOf(type(PageNode.class))
            .extracting(PageNode::children, collection(TabNode.class))
            .satisfiesExactly(
                tab -> assertThat(tab.identifier()).isEqualTo(3L),
                tab -> assertThat(tab.identifier()).isEqualTo(13L)
            )
    );

  }
}
