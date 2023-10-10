package gov.cdc.nbs.questionbank.page.detail;

import gov.cdc.nbs.questionbank.page.component.Component;
import gov.cdc.nbs.questionbank.page.component.Entry;
import gov.cdc.nbs.questionbank.page.component.Input;
import gov.cdc.nbs.questionbank.page.component.Layout;
import gov.cdc.nbs.questionbank.page.component.Selection;
import gov.cdc.nbs.questionbank.page.component.Static;

public class ComponentTypeResolver {

  @SuppressWarnings("java:S1479")
  public static Component.Type resolve(final int type) {
    return switch (type) {
      case 1002 -> Layout.Type.PAGE;
      case 1010 -> Layout.Type.TAB;
      case 1015 -> Layout.Type.SECTION;
      case 1016 -> Layout.Type.SUB_SECTION;
      case 1003 -> Static.Type.HYPERLINK;
      case 1011 -> Static.Type.SUB_HEADING;
      case 1012 -> Static.Type.LINE_SEPARATOR;
      case 1022 -> Static.Type.TABLE;
      case 1023 -> Static.Type.INFORMATION_BAR;
      case 1007 -> Selection.Type.SINGLE_SELECT;
      case 1013 -> Selection.Type.MULTI_SELECT;
      case 1001 -> Selection.Type.CHECKBOX;
      case 1006 -> Selection.Type.RADIO;
      case 1024 -> Selection.Type.SINGLE_SELECT_READ_ONLY_SAVE;
      case 1025 -> Selection.Type.MULTI_SELECT_READ_ONLY_SAVE;
      case 1027 -> Selection.Type.SINGLE_SELECT_READ_ONLY_NO_SAVE;
      case 1028 -> Selection.Type.MULTI_SELECT_READ_ONLY_NO_SAVE;
      case 1008 -> Input.Type.INPUT;
      case 1026 -> Input.Type.INPUT_READ_ONLY_SAVE;
      case 1029 -> Input.Type.INPUT_READ_ONLY_NO_SAVE;
      case 1014 -> Entry.Type.READ_ONLY;
      case 1000 -> Entry.Type.BUTTON;
      case 1009 -> Entry.Type.TEXT_AREA;
      case 1017 -> Entry.Type.PARTICIPATION;
      case 1019 -> Entry.Type.ROLLING_NOTE;
      case 1030 -> Entry.Type.PARTICIPANT_LIST;
      case 1032 -> Entry.Type.PATIENT_SEARCH;
      case 1033 -> Entry.Type.ACTION_BUTTON;
      case 1034 -> Entry.Type.SET_VALUE_BUTTON;
      case 1035 -> Entry.Type.LOGIC_FLAG;
      case 1036 -> Entry.Type.ORIGINAL_DOCUMENT_LIST;
      default -> throw new IllegalStateException("Unexpected Component Type: " + type);
    };
  }

  private ComponentTypeResolver() {
  }
}
