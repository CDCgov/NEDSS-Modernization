package gov.cdc.nbs.questionbank.page.component.tree;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import gov.cdc.nbs.questionbank.page.component.ComponentNode;
import gov.cdc.nbs.questionbank.page.component.PageNode;
import gov.cdc.nbs.questionbank.page.component.SectionNode;
import gov.cdc.nbs.questionbank.page.component.SubSectionNode;
import gov.cdc.nbs.questionbank.page.component.TabNode;
import org.junit.jupiter.api.Test;

class LayoutComponentResolverTest {

  @Test
  void should_resolve_page_component() {

    FlattenedComponent flattened = new FlattenedComponent(2L, 1002, "name-value", true, 1, 1);

    LayoutComponentResolver resolver = new LayoutComponentResolver();

    ComponentNode actual = resolver.resolve(flattened);

    assertThat(actual)
        .isInstanceOf(PageNode.class)
        .satisfies(
            component ->
                assertAll(
                    () -> assertThat(component.identifier()).isEqualTo(2L),
                    () -> assertThat(component.definition().name()).isEqualTo("name-value"),
                    () -> assertThat(component.definition().visible()).isTrue(),
                    () -> assertThat(component.definition().order()).isEqualTo(1)));
  }

  @Test
  void should_resolve_tab_component() {

    FlattenedComponent flattened = new FlattenedComponent(2L, 1010, "name-value", true, 1, 1);

    LayoutComponentResolver resolver = new LayoutComponentResolver();

    ComponentNode actual = resolver.resolve(flattened);

    assertThat(actual)
        .isInstanceOf(TabNode.class)
        .satisfies(
            component ->
                assertAll(
                    () -> assertThat(component.identifier()).isEqualTo(2L),
                    () -> assertThat(component.definition().name()).isEqualTo("name-value"),
                    () -> assertThat(component.definition().visible()).isTrue(),
                    () -> assertThat(component.definition().order()).isEqualTo(1)));
  }

  @Test
  void should_resolve_section_component() {

    FlattenedComponent flattened = new FlattenedComponent(2L, 1015, "name-value", true, 1, 1);

    LayoutComponentResolver resolver = new LayoutComponentResolver();

    ComponentNode actual = resolver.resolve(flattened);

    assertThat(actual)
        .isInstanceOf(SectionNode.class)
        .satisfies(
            component ->
                assertAll(
                    () -> assertThat(component.identifier()).isEqualTo(2L),
                    () -> assertThat(component.definition().name()).isEqualTo("name-value"),
                    () -> assertThat(component.definition().visible()).isTrue(),
                    () -> assertThat(component.definition().order()).isEqualTo(1)));
  }

  @Test
  void should_resolve_sub_section_component() {

    FlattenedComponent flattened = new FlattenedComponent(2L, 1016, "name-value", true, 1, 1);

    LayoutComponentResolver resolver = new LayoutComponentResolver();

    ComponentNode actual = resolver.resolve(flattened);

    assertThat(actual)
        .isInstanceOf(SubSectionNode.class)
        .satisfies(
            component ->
                assertAll(
                    () -> assertThat(component.identifier()).isEqualTo(2L),
                    () -> assertThat(component.definition().name()).isEqualTo("name-value"),
                    () -> assertThat(component.definition().visible()).isTrue(),
                    () -> assertThat(component.definition().order()).isEqualTo(1)));
  }

  @Test
  void should_not_resolve_unknown_layout_component_type() {

    FlattenedComponent flattened = new FlattenedComponent(2L, 1022, "invalid", true, 1, 1);

    LayoutComponentResolver resolver = new LayoutComponentResolver();

    assertThatThrownBy(() -> resolver.resolve(flattened))
        .hasMessageContaining("Unresolvable Layout Component Type");
  }
}
