package gov.cdc.nbs.questionbank.page.detail;

import gov.cdc.nbs.questionbank.page.component.Content;
import gov.cdc.nbs.questionbank.page.component.Entry;
import gov.cdc.nbs.questionbank.page.component.Input;
import gov.cdc.nbs.questionbank.page.component.Selection;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.InstanceOfAssertFactories.type;
import static org.junit.jupiter.api.Assertions.assertAll;

class ContentComponentResolverTest {

  @Test
  void should_resolve_input_component() {
    FlattenedComponent flattened = new FlattenedComponent(
        2L,
        1008,
        "name-value",
        true,
        1,
        "standard-value",
        "question-value",
        "data-type-value",
        "sub-group-value",
        "description-value",
        true,
        true,
        true,
        true,
        "mask-value",
        "tool-tip-value",
        "default-value-value",
        "value-set-value"
    );

    ContentComponentResolver resolver = new ContentComponentResolver();

    Content actual = resolver.resolve(flattened);

    assertThat(actual)
        .isInstanceOf(Input.class)
        .asInstanceOf(type(Input.class))
        .satisfies(
            component -> assertAll(
                () -> assertThat(component.identifier()).isEqualTo(2L),
                () -> assertThat(component.type().identifier()).isEqualTo(1008L),
                () -> assertThat(component.definition().name()).isEqualTo("name-value"),
                () -> assertThat(component.definition().visible()).isTrue(),
                () -> assertThat(component.definition().order()).isEqualTo(1),
                () -> assertThat(component.attributes().dataType()).isEqualTo("data-type-value"),
                () -> assertThat(component.attributes().subGroup()).isEqualTo("sub-group-value"),
                () -> assertThat(component.attributes().description()).isEqualTo("description-value"),
                () -> assertThat(component.attributes().enabled()).isTrue(),
                () -> assertThat(component.attributes().required()).isTrue(),
                () -> assertThat(component.attributes().coInfection()).isTrue(),
                () -> assertThat(component.attributes().mask()).isEqualTo("mask-value"),
                () -> assertThat(component.attributes().toolTip()).isEqualTo("tool-tip-value"),
                () -> assertThat(component.attributes().defaultValue()).isEqualTo("default-value-value"),
                () -> assertThat(component.allowFutureDates()).isTrue()
            )
        );
  }

  @Test
  void should_resolve_entry_component() {
    FlattenedComponent flattened = new FlattenedComponent(
        2L,
        1014,
        "name-value",
        true,
        1,
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
    );

    ContentComponentResolver resolver = new ContentComponentResolver();

    Content actual = resolver.resolve(flattened);

    assertThat(actual)
        .isInstanceOf(Entry.class)
        .asInstanceOf(type(Entry.class))
        .satisfies(
            component -> assertAll(
                () -> assertThat(component.identifier()).isEqualTo(2L),
                () -> assertThat(component.type().identifier()).isEqualTo(1014L),
                () -> assertThat(component.definition().name()).isEqualTo("name-value"),
                () -> assertThat(component.definition().visible()).isTrue(),
                () -> assertThat(component.definition().order()).isEqualTo(1),
                () -> assertThat(component.attributes().dataType()).isEqualTo("data-type-value"),
                () -> assertThat(component.attributes().subGroup()).isEqualTo("sub-group-value"),
                () -> assertThat(component.attributes().description()).isEqualTo("description-value"),
                () -> assertThat(component.attributes().enabled()).isTrue(),
                () -> assertThat(component.attributes().required()).isTrue(),
                () -> assertThat(component.attributes().coInfection()).isTrue(),
                () -> assertThat(component.attributes().mask()).isEqualTo("mask-value"),
                () -> assertThat(component.attributes().toolTip()).isEqualTo("tool-tip-value"),
                () -> assertThat(component.attributes().defaultValue()).isEqualTo("default-value-value")
            )
        );
  }

  @Test
  void should_resolve_selection_component() {
    FlattenedComponent flattened = new FlattenedComponent(
        2L,
        1007,
        "name-value",
        true,
        1,
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
    );

    ContentComponentResolver resolver = new ContentComponentResolver();

    Content actual = resolver.resolve(flattened);

    assertThat(actual)
        .isInstanceOf(Selection.class)
        .asInstanceOf(type(Selection.class))
        .satisfies(
            component -> assertAll(
                () -> assertThat(component.identifier()).isEqualTo(2L),
                () -> assertThat(component.type().identifier()).isEqualTo(1007L),
                () -> assertThat(component.definition().name()).isEqualTo("name-value"),
                () -> assertThat(component.definition().visible()).isTrue(),
                () -> assertThat(component.definition().order()).isEqualTo(1),
                () -> assertThat(component.attributes().dataType()).isEqualTo("data-type-value"),
                () -> assertThat(component.attributes().subGroup()).isEqualTo("sub-group-value"),
                () -> assertThat(component.attributes().description()).isEqualTo("description-value"),
                () -> assertThat(component.attributes().enabled()).isTrue(),
                () -> assertThat(component.attributes().required()).isTrue(),
                () -> assertThat(component.attributes().coInfection()).isTrue(),
                () -> assertThat(component.attributes().mask()).isEqualTo("mask-value"),
                () -> assertThat(component.attributes().toolTip()).isEqualTo("tool-tip-value"),
                () -> assertThat(component.attributes().defaultValue()).isEqualTo("default-value-value")
            )
        );
  }

  @Test
  void should_not_resolve_unknown_content_component_type() {

    FlattenedComponent flattened = new FlattenedComponent(
        2L,
        1002,
        "invalid",
        true,
        1
    );

    ContentComponentResolver resolver = new ContentComponentResolver();

    assertThatThrownBy(() -> resolver.resolve(flattened))
        .hasMessageContaining("Unresolvable Content Component Type");
  }
}
