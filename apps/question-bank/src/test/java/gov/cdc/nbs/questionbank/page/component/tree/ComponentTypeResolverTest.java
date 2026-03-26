package gov.cdc.nbs.questionbank.page.component.tree;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import gov.cdc.nbs.questionbank.page.component.ComponentNode;
import gov.cdc.nbs.questionbank.page.component.EntryNode;
import gov.cdc.nbs.questionbank.page.component.InputNode;
import gov.cdc.nbs.questionbank.page.component.LayoutNode;
import gov.cdc.nbs.questionbank.page.component.SelectionNode;
import gov.cdc.nbs.questionbank.page.component.StaticNode;
import org.junit.jupiter.api.Test;

class ComponentTypeResolverTest {

  @Test
  void should_resolve_page() {
    ComponentNode.Type actual = ComponentTypeResolver.resolve(1002);

    assertThat(actual).isEqualTo(LayoutNode.Type.PAGE);
  }

  @Test
  void should_resolve_tab() {
    ComponentNode.Type actual = ComponentTypeResolver.resolve(1010);

    assertThat(actual).isEqualTo(LayoutNode.Type.TAB);
  }

  @Test
  void should_resolve_section() {
    ComponentNode.Type actual = ComponentTypeResolver.resolve(1015);

    assertThat(actual).isEqualTo(LayoutNode.Type.SECTION);
  }

  @Test
  void should_resolve_sub_section() {
    ComponentNode.Type actual = ComponentTypeResolver.resolve(1016);

    assertThat(actual).isEqualTo(LayoutNode.Type.SUB_SECTION);
  }

  @Test
  void should_resolve_hyperlink() {
    ComponentNode.Type actual = ComponentTypeResolver.resolve(1003);

    assertThat(actual).isEqualTo(StaticNode.Type.HYPERLINK);
  }

  @Test
  void should_resolve_sub_heading() {
    ComponentNode.Type actual = ComponentTypeResolver.resolve(1011);

    assertThat(actual).isEqualTo(StaticNode.Type.SUB_HEADING);
  }

  @Test
  void should_resolve_line_separator() {
    ComponentNode.Type actual = ComponentTypeResolver.resolve(1012);

    assertThat(actual).isEqualTo(StaticNode.Type.LINE_SEPARATOR);
  }

  @Test
  void should_resolve_table() {
    ComponentNode.Type actual = ComponentTypeResolver.resolve(1022);

    assertThat(actual).isEqualTo(StaticNode.Type.TABLE);
  }

  @Test
  void should_resolve_information_bar() {
    ComponentNode.Type actual = ComponentTypeResolver.resolve(1023);

    assertThat(actual).isEqualTo(StaticNode.Type.INFORMATION_BAR);
  }

  @Test
  void should_resolve_single_select() {
    ComponentNode.Type actual = ComponentTypeResolver.resolve(1007);

    assertThat(actual).isEqualTo(SelectionNode.Type.SINGLE_SELECT);
  }

  @Test
  void should_resolve_multi_select() {
    ComponentNode.Type actual = ComponentTypeResolver.resolve(1013);

    assertThat(actual).isEqualTo(SelectionNode.Type.MULTI_SELECT);
  }

  @Test
  void should_resolve_checkbox() {
    ComponentNode.Type actual = ComponentTypeResolver.resolve(1001);

    assertThat(actual).isEqualTo(SelectionNode.Type.CHECKBOX);
  }

  @Test
  void should_resolve_radio() {
    ComponentNode.Type actual = ComponentTypeResolver.resolve(1006);

    assertThat(actual).isEqualTo(SelectionNode.Type.RADIO);
  }

  @Test
  void should_resolve_single_select_read_only_save() {
    ComponentNode.Type actual = ComponentTypeResolver.resolve(1024);

    assertThat(actual).isEqualTo(SelectionNode.Type.SINGLE_SELECT_READ_ONLY_SAVE);
  }

  @Test
  void should_resolve_single_select_read_only_no_save() {
    ComponentNode.Type actual = ComponentTypeResolver.resolve(1027);

    assertThat(actual).isEqualTo(SelectionNode.Type.SINGLE_SELECT_READ_ONLY_NO_SAVE);
  }

  @Test
  void should_resolve_multi_select_read_only_save() {
    ComponentNode.Type actual = ComponentTypeResolver.resolve(1025);

    assertThat(actual).isEqualTo(SelectionNode.Type.MULTI_SELECT_READ_ONLY_SAVE);
  }

  @Test
  void should_resolve_multi_select_read_only_no_save() {
    ComponentNode.Type actual = ComponentTypeResolver.resolve(1028);

    assertThat(actual).isEqualTo(SelectionNode.Type.MULTI_SELECT_READ_ONLY_NO_SAVE);
  }

  @Test
  void should_resolve_read_only() {
    ComponentNode.Type actual = ComponentTypeResolver.resolve(1014);

    assertThat(actual).isEqualTo(EntryNode.Type.READ_ONLY);
  }

  @Test
  void should_resolve_input() {
    ComponentNode.Type actual = ComponentTypeResolver.resolve(1008);

    assertThat(actual).isEqualTo(InputNode.Type.INPUT);
  }

  @Test
  void should_resolve_button() {
    ComponentNode.Type actual = ComponentTypeResolver.resolve(1000);

    assertThat(actual).isEqualTo(EntryNode.Type.BUTTON);
  }

  @Test
  void should_resolve_text_area() {
    ComponentNode.Type actual = ComponentTypeResolver.resolve(1009);

    assertThat(actual).isEqualTo(EntryNode.Type.TEXT_AREA);
  }

  @Test
  void should_resolve_participation() {
    ComponentNode.Type actual = ComponentTypeResolver.resolve(1017);

    assertThat(actual).isEqualTo(EntryNode.Type.PARTICIPATION);
  }

  @Test
  void should_resolve_rolling_note() {
    ComponentNode.Type actual = ComponentTypeResolver.resolve(1019);

    assertThat(actual).isEqualTo(EntryNode.Type.ROLLING_NOTE);
  }

  @Test
  void should_resolve_participant_list() {
    ComponentNode.Type actual = ComponentTypeResolver.resolve(1030);

    assertThat(actual).isEqualTo(EntryNode.Type.PARTICIPANT_LIST);
  }

  @Test
  void should_resolve_patient_search() {
    ComponentNode.Type actual = ComponentTypeResolver.resolve(1032);

    assertThat(actual).isEqualTo(EntryNode.Type.PATIENT_SEARCH);
  }

  @Test
  void should_resolve_action_button() {
    ComponentNode.Type actual = ComponentTypeResolver.resolve(1033);

    assertThat(actual).isEqualTo(EntryNode.Type.ACTION_BUTTON);
  }

  @Test
  void should_resolve_set_value_button() {
    ComponentNode.Type actual = ComponentTypeResolver.resolve(1034);

    assertThat(actual).isEqualTo(EntryNode.Type.SET_VALUE_BUTTON);
  }

  @Test
  void should_resolve_logic_flag() {
    ComponentNode.Type actual = ComponentTypeResolver.resolve(1035);

    assertThat(actual).isEqualTo(EntryNode.Type.LOGIC_FLAG);
  }

  @Test
  void should_resolve_original_document_list() {
    ComponentNode.Type actual = ComponentTypeResolver.resolve(1036);

    assertThat(actual).isEqualTo(EntryNode.Type.ORIGINAL_DOCUMENT_LIST);
  }

  @Test
  void should_resolve_input_read_only_save() {
    ComponentNode.Type actual = ComponentTypeResolver.resolve(1026);

    assertThat(actual).isEqualTo(InputNode.Type.INPUT_READ_ONLY_SAVE);
  }

  @Test
  void should_resolve_input_read_only_no_save() {
    ComponentNode.Type actual = ComponentTypeResolver.resolve(1029);

    assertThat(actual).isEqualTo(InputNode.Type.INPUT_READ_ONLY_NO_SAVE);
  }

  @Test
  void should_throw_exception_for_unexpected_type() {

    assertThatThrownBy(() -> ComponentTypeResolver.resolve(1))
        .hasMessageContaining("Component Type: 1");
  }
}
