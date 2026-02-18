package gov.cdc.nbs.questionbank.page.component.tree;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.InstanceOfAssertFactories.type;
import static org.junit.jupiter.api.Assertions.assertAll;

import gov.cdc.nbs.questionbank.page.component.ContentNode;
import gov.cdc.nbs.questionbank.page.component.EntryNode;
import gov.cdc.nbs.questionbank.page.component.InputNode;
import gov.cdc.nbs.questionbank.page.component.SelectionNode;
import gov.cdc.nbs.questionbank.page.component.StaticNode;
import org.junit.jupiter.api.Test;

class ContentComponentResolverTest {

  @Test
  void should_resolve_input_component() {
    FlattenedComponent flattened =
        new FlattenedComponent(
            2L,
            1008,
            "name-value",
            true,
            1,
            1,
            false,
            false,
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
            "value-set-value",
            "admin-comments",
            "10",
            "default-rdb-table-name",
            "rdb-column-name",
            "default-label-in-report",
            "dataMart-column-name",
            false,
            "data_location",
            true,
            "block_name",
            null,
            false,
            null,
            null,
            null,
            null,
            null);

    ContentComponentResolver resolver = new ContentComponentResolver();

    ContentNode actual = resolver.resolve(flattened);

    assertThat(actual)
        .isInstanceOf(InputNode.class)
        .asInstanceOf(type(InputNode.class))
        .satisfies(
            component ->
                assertAll(
                    () -> assertThat(component.identifier()).isEqualTo(2L),
                    () -> assertThat(component.type().identifier()).isEqualTo(1008L),
                    () -> assertThat(component.definition().name()).isEqualTo("name-value"),
                    () -> assertThat(component.definition().visible()).isTrue(),
                    () -> assertThat(component.definition().order()).isEqualTo(1),
                    () ->
                        assertThat(component.attributes().dataType()).isEqualTo("data-type-value"),
                    () ->
                        assertThat(component.attributes().subGroup()).isEqualTo("sub-group-value"),
                    () ->
                        assertThat(component.attributes().description())
                            .isEqualTo("description-value"),
                    () -> assertThat(component.attributes().enabled()).isTrue(),
                    () -> assertThat(component.attributes().required()).isTrue(),
                    () -> assertThat(component.attributes().coInfection()).isTrue(),
                    () -> assertThat(component.attributes().mask()).isEqualTo("mask-value"),
                    () -> assertThat(component.attributes().toolTip()).isEqualTo("tool-tip-value"),
                    () ->
                        assertThat(component.attributes().defaultValue())
                            .isEqualTo("default-value-value"),
                    () -> assertThat(component.allowFutureDates()).isTrue(),
                    () -> assertThat(component.attributes().isPublished()).isTrue()));
  }

  @Test
  void should_resolve_static_component() {
    FlattenedComponent flattened =
        new FlattenedComponent(
            2L,
            1003,
            "name-value",
            true,
            1,
            1,
            false,
            false,
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
            null,
            "admin-comments",
            "10",
            "default-rdb-table-name",
            "rdb-column-name",
            "default-label-in-report",
            "dataMart-column-name",
            false,
            "data_location",
            true,
            "block_name",
            null,
            false,
            null,
            null,
            null,
            null,
            null);

    ContentComponentResolver resolver = new ContentComponentResolver();

    ContentNode actual = resolver.resolve(flattened);

    assertThat(actual)
        .isInstanceOf(StaticNode.class)
        .asInstanceOf(type(StaticNode.class))
        .satisfies(
            component ->
                assertAll(
                    () -> assertThat(component.identifier()).isEqualTo(2L),
                    () -> assertThat(component.type().identifier()).isEqualTo(1003L),
                    () -> assertThat(component.definition().name()).isEqualTo("name-value"),
                    () -> assertThat(component.definition().visible()).isTrue(),
                    () -> assertThat(component.definition().order()).isEqualTo(1),
                    () ->
                        assertThat(component.attributes().dataType()).isEqualTo("data-type-value"),
                    () ->
                        assertThat(component.attributes().subGroup()).isEqualTo("sub-group-value"),
                    () ->
                        assertThat(component.attributes().description())
                            .isEqualTo("description-value"),
                    () -> assertThat(component.attributes().enabled()).isTrue(),
                    () -> assertThat(component.attributes().required()).isTrue(),
                    () -> assertThat(component.attributes().coInfection()).isTrue(),
                    () -> assertThat(component.attributes().mask()).isEqualTo("mask-value"),
                    () -> assertThat(component.attributes().toolTip()).isEqualTo("tool-tip-value"),
                    () ->
                        assertThat(component.attributes().defaultValue())
                            .isEqualTo("default-value-value")));
  }

  @Test
  void should_resolve_entry_component() {
    FlattenedComponent flattened =
        new FlattenedComponent(
            2L,
            1014,
            "name-value",
            true,
            1,
            1,
            false,
            false,
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
            "value-set-value",
            "admin-comments",
            "10",
            "default-rdb-table-name",
            "rdb-column-name",
            "default-label-in-report",
            "dataMart-column-name",
            false,
            "data_location",
            true,
            "block_name",
            null,
            false,
            null,
            null,
            null,
            null,
            null);

    ContentComponentResolver resolver = new ContentComponentResolver();

    ContentNode actual = resolver.resolve(flattened);

    assertThat(actual)
        .isInstanceOf(EntryNode.class)
        .asInstanceOf(type(EntryNode.class))
        .satisfies(
            component ->
                assertAll(
                    () -> assertThat(component.identifier()).isEqualTo(2L),
                    () -> assertThat(component.type().identifier()).isEqualTo(1014L),
                    () -> assertThat(component.definition().name()).isEqualTo("name-value"),
                    () -> assertThat(component.definition().visible()).isTrue(),
                    () -> assertThat(component.definition().order()).isEqualTo(1),
                    () ->
                        assertThat(component.attributes().dataType()).isEqualTo("data-type-value"),
                    () ->
                        assertThat(component.attributes().subGroup()).isEqualTo("sub-group-value"),
                    () ->
                        assertThat(component.attributes().description())
                            .isEqualTo("description-value"),
                    () -> assertThat(component.attributes().enabled()).isTrue(),
                    () -> assertThat(component.attributes().required()).isTrue(),
                    () -> assertThat(component.attributes().coInfection()).isTrue(),
                    () -> assertThat(component.attributes().mask()).isEqualTo("mask-value"),
                    () -> assertThat(component.attributes().toolTip()).isEqualTo("tool-tip-value"),
                    () ->
                        assertThat(component.attributes().defaultValue())
                            .isEqualTo("default-value-value")));
  }

  @Test
  void should_resolve_selection_component() {
    FlattenedComponent flattened =
        new FlattenedComponent(
            2L,
            1007,
            "name-value",
            true,
            1,
            1,
            false,
            false,
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
            "value-set-value",
            "admin-comments",
            "10",
            "default-rdb-table-name",
            "rdb-column-name",
            "default-label-in-report",
            "dataMart-column-name",
            false,
            "data_location",
            true,
            "block_name",
            null,
            false,
            null,
            null,
            null,
            null,
            null);

    ContentComponentResolver resolver = new ContentComponentResolver();

    ContentNode actual = resolver.resolve(flattened);

    assertThat(actual)
        .isInstanceOf(SelectionNode.class)
        .asInstanceOf(type(SelectionNode.class))
        .satisfies(
            component ->
                assertAll(
                    () -> assertThat(component.identifier()).isEqualTo(2L),
                    () -> assertThat(component.type().identifier()).isEqualTo(1007L),
                    () -> assertThat(component.definition().name()).isEqualTo("name-value"),
                    () -> assertThat(component.definition().visible()).isTrue(),
                    () -> assertThat(component.definition().order()).isEqualTo(1),
                    () ->
                        assertThat(component.attributes().dataType()).isEqualTo("data-type-value"),
                    () ->
                        assertThat(component.attributes().subGroup()).isEqualTo("sub-group-value"),
                    () ->
                        assertThat(component.attributes().description())
                            .isEqualTo("description-value"),
                    () -> assertThat(component.attributes().enabled()).isTrue(),
                    () -> assertThat(component.attributes().required()).isTrue(),
                    () -> assertThat(component.attributes().coInfection()).isTrue(),
                    () -> assertThat(component.attributes().mask()).isEqualTo("mask-value"),
                    () -> assertThat(component.attributes().toolTip()).isEqualTo("tool-tip-value"),
                    () ->
                        assertThat(component.attributes().defaultValue())
                            .isEqualTo("default-value-value")));
  }

  @Test
  void should_not_resolve_unknown_content_component_type() {

    FlattenedComponent flattened = new FlattenedComponent(2L, 1002, "invalid", true, 1, 1);

    ContentComponentResolver resolver = new ContentComponentResolver();

    assertThatThrownBy(() -> resolver.resolve(flattened))
        .hasMessageContaining("Unresolvable Content Component Type");
  }
}
