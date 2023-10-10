package gov.cdc.nbs.questionbank.page.detail;

import gov.cdc.nbs.questionbank.page.component.Component;
import gov.cdc.nbs.questionbank.page.component.Entry;
import gov.cdc.nbs.questionbank.page.component.Input;
import gov.cdc.nbs.questionbank.page.component.Layout;
import gov.cdc.nbs.questionbank.page.component.Selection;
import gov.cdc.nbs.questionbank.page.component.Static;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ComponentTypeResolverTest {


  @Test
  void should_resolve_page() {
    Component.Type actual = ComponentTypeResolver.resolve(1002);

    assertThat(actual).isEqualTo(Layout.Type.PAGE);
  }

  @Test
  void should_resolve_tab() {
    Component.Type actual = ComponentTypeResolver.resolve(1010);

    assertThat(actual).isEqualTo(Layout.Type.TAB);
  }

  @Test
  void should_resolve_section() {
    Component.Type actual = ComponentTypeResolver.resolve(1015);

    assertThat(actual).isEqualTo(Layout.Type.SECTION);
  }

  @Test
  void should_resolve_sub_section() {
    Component.Type actual = ComponentTypeResolver.resolve(1016);

    assertThat(actual).isEqualTo(Layout.Type.SUB_SECTION);
  }

  @Test
  void should_resolve_hyperlink() {
    Component.Type actual = ComponentTypeResolver.resolve(1003);

    assertThat(actual).isEqualTo(Static.Type.HYPERLINK);
  }

  @Test
  void should_resolve_sub_heading() {
    Component.Type actual = ComponentTypeResolver.resolve(1011);

    assertThat(actual).isEqualTo(Static.Type.SUB_HEADING);
  }

  @Test
  void should_resolve_line_separator() {
    Component.Type actual = ComponentTypeResolver.resolve(1012);

    assertThat(actual).isEqualTo(Static.Type.LINE_SEPARATOR);
  }

  @Test
  void should_resolve_table() {
    Component.Type actual = ComponentTypeResolver.resolve(1022);

    assertThat(actual).isEqualTo(Static.Type.TABLE);
  }

  @Test
  void should_resolve_information_bar() {
    Component.Type actual = ComponentTypeResolver.resolve(1023);

    assertThat(actual).isEqualTo(Static.Type.INFORMATION_BAR);
  }

  @Test
  void should_resolve_single_select() {
    Component.Type actual = ComponentTypeResolver.resolve(1007);

    assertThat(actual).isEqualTo(Selection.Type.SINGLE_SELECT);
  }

  @Test
  void should_resolve_multi_select() {
    Component.Type actual = ComponentTypeResolver.resolve(1013);

    assertThat(actual).isEqualTo(Selection.Type.MULTI_SELECT);
  }

  @Test
  void should_resolve_checkbox() {
    Component.Type actual = ComponentTypeResolver.resolve(1001);

    assertThat(actual).isEqualTo(Selection.Type.CHECKBOX);
  }

  @Test
  void should_resolve_radio() {
    Component.Type actual = ComponentTypeResolver.resolve(1006);

    assertThat(actual).isEqualTo(Selection.Type.RADIO);
  }

  @Test
  void should_resolve_single_select_read_only_save() {
    Component.Type actual = ComponentTypeResolver.resolve(1024);

    assertThat(actual).isEqualTo(Selection.Type.SINGLE_SELECT_READ_ONLY_SAVE);
  }

  @Test
  void should_resolve_single_select_read_only_no_save() {
    Component.Type actual = ComponentTypeResolver.resolve(1027);

    assertThat(actual).isEqualTo(Selection.Type.SINGLE_SELECT_READ_ONLY_NO_SAVE);
  }

  @Test
  void should_resolve_multi_select_read_only_save() {
    Component.Type actual = ComponentTypeResolver.resolve(1025);

    assertThat(actual).isEqualTo(Selection.Type.MULTI_SELECT_READ_ONLY_SAVE);
  }

  @Test
  void should_resolve_multi_select_read_only_no_save() {
    Component.Type actual = ComponentTypeResolver.resolve(1028);

    assertThat(actual).isEqualTo(Selection.Type.MULTI_SELECT_READ_ONLY_NO_SAVE);
  }

  @Test
  void should_resolve_read_only() {
    Component.Type actual = ComponentTypeResolver.resolve(1014);

    assertThat(actual).isEqualTo(Entry.Type.READ_ONLY);
  }

  @Test
  void should_resolve_input() {
    Component.Type actual = ComponentTypeResolver.resolve(1008);

    assertThat(actual).isEqualTo(Input.Type.INPUT);
  }

  @Test
  void should_resolve_button() {
    Component.Type actual = ComponentTypeResolver.resolve(1000);

    assertThat(actual).isEqualTo(Entry.Type.BUTTON);
  }

  @Test
  void should_resolve_text_area() {
    Component.Type actual = ComponentTypeResolver.resolve(1009);

    assertThat(actual).isEqualTo(Entry.Type.TEXT_AREA);
  }

  @Test
  void should_resolve_participation() {
    Component.Type actual = ComponentTypeResolver.resolve(1017);

    assertThat(actual).isEqualTo(Entry.Type.PARTICIPATION);
  }

  @Test
  void should_resolve_rolling_note() {
    Component.Type actual = ComponentTypeResolver.resolve(1019);

    assertThat(actual).isEqualTo(Entry.Type.ROLLING_NOTE);
  }

  @Test
  void should_resolve_participant_list() {
    Component.Type actual = ComponentTypeResolver.resolve(1030);

    assertThat(actual).isEqualTo(Entry.Type.PARTICIPANT_LIST);
  }

  @Test
  void should_resolve_patient_search() {
    Component.Type actual = ComponentTypeResolver.resolve(1032);

    assertThat(actual).isEqualTo(Entry.Type.PATIENT_SEARCH);
  }

  @Test
  void should_resolve_action_button() {
    Component.Type actual = ComponentTypeResolver.resolve(1033);

    assertThat(actual).isEqualTo(Entry.Type.ACTION_BUTTON);
  }

  @Test
  void should_resolve_set_value_button() {
    Component.Type actual = ComponentTypeResolver.resolve(1034);

    assertThat(actual).isEqualTo(Entry.Type.SET_VALUE_BUTTON);
  }

  @Test
  void should_resolve_logic_flag() {
    Component.Type actual = ComponentTypeResolver.resolve(1035);

    assertThat(actual).isEqualTo(Entry.Type.LOGIC_FLAG);
  }

  @Test
  void should_resolve_original_document_list() {
    Component.Type actual = ComponentTypeResolver.resolve(1036);

    assertThat(actual).isEqualTo(Entry.Type.ORIGINAL_DOCUMENT_LIST);
  }

  @Test
  void should_resolve_input_read_only_save() {
    Component.Type actual = ComponentTypeResolver.resolve(1026);

    assertThat(actual).isEqualTo(Input.Type.INPUT_READ_ONLY_SAVE);
  }

  @Test
  void should_resolve_input_read_only_no_save() {
    Component.Type actual = ComponentTypeResolver.resolve(1029);

    assertThat(actual).isEqualTo(Input.Type.INPUT_READ_ONLY_NO_SAVE);
  }

  @Test
  void should_throw_exception_for_unexpected_type() {

    assertThatThrownBy(() -> ComponentTypeResolver.resolve(1))
        .hasMessageContaining("Component Type: 1");

  }
}
