package gov.cdc.nbs.questionbank.page.detail;

import gov.cdc.nbs.questionbank.page.component.Component;
import gov.cdc.nbs.questionbank.page.component.Page;
import gov.cdc.nbs.questionbank.page.component.Section;
import gov.cdc.nbs.questionbank.page.component.SubSection;
import gov.cdc.nbs.questionbank.page.component.Tab;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class LayoutComponentResolverTest {

  @Test
  void should_resolve_page_component() {

    FlattenedComponent flattened = new FlattenedComponent(2L, 1002, "name-value", true, 1);

    LayoutComponentResolver resolver = new LayoutComponentResolver();

    Component actual = resolver.resolve(flattened);

    assertThat(actual).isInstanceOf(Page.class)
        .satisfies(
            component -> assertAll(
                () -> assertThat(component.identifier()).isEqualTo(2L),
                () -> assertThat(component.definition().name()).isEqualTo("name-value"),
                () -> assertThat(component.definition().visible()).isTrue(),
                () -> assertThat(component.definition().order()).isEqualTo(1)

            )
        );

  }

  @Test
  void should_resolve_tab_component() {

    FlattenedComponent flattened = new FlattenedComponent(2L, 1010, "name-value", true, 1);

    LayoutComponentResolver resolver = new LayoutComponentResolver();

    Component actual = resolver.resolve(flattened);

    assertThat(actual).isInstanceOf(Tab.class)
        .satisfies(
            component -> assertAll(
                () -> assertThat(component.identifier()).isEqualTo(2L),
                () -> assertThat(component.definition().name()).isEqualTo("name-value"),
                () -> assertThat(component.definition().visible()).isTrue(),
                () -> assertThat(component.definition().order()).isEqualTo(1)

            )
        );
  }

  @Test
  void should_resolve_section_component() {

    FlattenedComponent flattened = new FlattenedComponent(2L, 1015, "name-value", true, 1);

    LayoutComponentResolver resolver = new LayoutComponentResolver();

    Component actual = resolver.resolve(flattened);

    assertThat(actual).isInstanceOf(Section.class)
        .satisfies(
            component -> assertAll(
                () -> assertThat(component.identifier()).isEqualTo(2L),
                () -> assertThat(component.definition().name()).isEqualTo("name-value"),
                () -> assertThat(component.definition().visible()).isTrue(),
                () -> assertThat(component.definition().order()).isEqualTo(1)

            )
        );
  }

  @Test
  void should_resolve_sub_section_component() {

    FlattenedComponent flattened = new FlattenedComponent(2L, 1016, "name-value", true, 1);

    LayoutComponentResolver resolver = new LayoutComponentResolver();

    Component actual = resolver.resolve(flattened);

    assertThat(actual).isInstanceOf(SubSection.class)
        .satisfies(
            component -> assertAll(
                () -> assertThat(component.identifier()).isEqualTo(2L),
                () -> assertThat(component.definition().name()).isEqualTo("name-value"),
                () -> assertThat(component.definition().visible()).isTrue(),
                () -> assertThat(component.definition().order()).isEqualTo(1)

            )
        );
  }

  @Test
  void should_not_resolve_unknown_layout_component_type() {

    FlattenedComponent flattened = new FlattenedComponent(
        2L,
        1022,
        "invalid",
        true,
        1
    );

    LayoutComponentResolver resolver = new LayoutComponentResolver();

    assertThatThrownBy(() -> resolver.resolve(flattened))
        .hasMessageContaining("Unresolvable Layout Component Type");
  }
}
