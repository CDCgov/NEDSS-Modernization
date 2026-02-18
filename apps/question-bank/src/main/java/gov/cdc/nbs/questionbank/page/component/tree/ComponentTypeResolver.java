package gov.cdc.nbs.questionbank.page.component.tree;

import gov.cdc.nbs.questionbank.page.component.ComponentNode;
import gov.cdc.nbs.questionbank.page.component.EntryNode;
import gov.cdc.nbs.questionbank.page.component.InputNode;
import gov.cdc.nbs.questionbank.page.component.LayoutNode;
import gov.cdc.nbs.questionbank.page.component.SelectionNode;
import gov.cdc.nbs.questionbank.page.component.StaticNode;

public class ComponentTypeResolver {

  @SuppressWarnings("java:S1479")
  public static ComponentNode.Type resolve(final int type) {
    return switch (type) {
      case 1002 -> LayoutNode.Type.PAGE;
      case 1010 -> LayoutNode.Type.TAB;
      case 1015 -> LayoutNode.Type.SECTION;
      case 1016 -> LayoutNode.Type.SUB_SECTION;
      case 1003 -> StaticNode.Type.HYPERLINK;
      case 1011 -> StaticNode.Type.SUB_HEADING;
      case 1012 -> StaticNode.Type.LINE_SEPARATOR;
      case 1022 -> StaticNode.Type.TABLE;
      case 1023 -> StaticNode.Type.INFORMATION_BAR;
      case 1007 -> SelectionNode.Type.SINGLE_SELECT;
      case 1013 -> SelectionNode.Type.MULTI_SELECT;
      case 1001 -> SelectionNode.Type.CHECKBOX;
      case 1006 -> SelectionNode.Type.RADIO;
      case 1024 -> SelectionNode.Type.SINGLE_SELECT_READ_ONLY_SAVE;
      case 1025 -> SelectionNode.Type.MULTI_SELECT_READ_ONLY_SAVE;
      case 1027 -> SelectionNode.Type.SINGLE_SELECT_READ_ONLY_NO_SAVE;
      case 1028 -> SelectionNode.Type.MULTI_SELECT_READ_ONLY_NO_SAVE;
      case 1008 -> InputNode.Type.INPUT;
      case 1026 -> InputNode.Type.INPUT_READ_ONLY_SAVE;
      case 1029 -> InputNode.Type.INPUT_READ_ONLY_NO_SAVE;
      case 1014 -> EntryNode.Type.READ_ONLY;
      case 1000 -> EntryNode.Type.BUTTON;
      case 1009 -> EntryNode.Type.TEXT_AREA;
      case 1017 -> EntryNode.Type.PARTICIPATION;
      case 1019 -> EntryNode.Type.ROLLING_NOTE;
      case 1030 -> EntryNode.Type.PARTICIPANT_LIST;
      case 1032 -> EntryNode.Type.PATIENT_SEARCH;
      case 1033 -> EntryNode.Type.ACTION_BUTTON;
      case 1034 -> EntryNode.Type.SET_VALUE_BUTTON;
      case 1035 -> EntryNode.Type.LOGIC_FLAG;
      case 1036 -> EntryNode.Type.ORIGINAL_DOCUMENT_LIST;
      default -> throw new IllegalStateException("Unexpected Component Type: " + type);
    };
  }

  private ComponentTypeResolver() {}
}
