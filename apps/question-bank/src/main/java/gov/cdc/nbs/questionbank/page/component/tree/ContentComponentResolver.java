package gov.cdc.nbs.questionbank.page.component.tree;

import gov.cdc.nbs.questionbank.page.component.ComponentNode;
import gov.cdc.nbs.questionbank.page.component.ContentNode;
import gov.cdc.nbs.questionbank.page.component.EntryNode;
import gov.cdc.nbs.questionbank.page.component.InputNode;
import gov.cdc.nbs.questionbank.page.component.SelectionNode;
import gov.cdc.nbs.questionbank.page.component.StaticNode;

class ContentComponentResolver {

  ContentNode resolve(final FlattenedComponent flattened) {
    ComponentNode.Type type = ComponentTypeResolver.resolve(flattened.type());
    return resolve(type, flattened);
  }

  ContentNode resolve(final ComponentNode.Type type, final FlattenedComponent flattened) {
    return switch (type) {
      case InputNode.Type input -> asInput(input, flattened);
      case EntryNode.Type entry -> asEntry(entry, flattened);
      case SelectionNode.Type selection -> asSelection(selection, flattened);
      case StaticNode.Type staticNode -> asStatic(staticNode, flattened);
      case null, default ->
          throw new IllegalStateException("Unresolvable Content Component Type: " + type);
    };
  }

  private ComponentNode.Definition asDefinition(final FlattenedComponent component) {
    return new ComponentNode.Definition(component.name(), component.visible(), component.order());
  }

  private ContentNode.Attributes asAttributes(final FlattenedComponent component) {
    return new ContentNode.Attributes(
        component.isStandardNnd(),
        component.isStandard(),
        component.standard(),
        component.question(),
        component.dataType(),
        component.subGroup(),
        component.description(),
        component.enabled(),
        component.required(),
        component.coInfection(),
        component.mask(),
        component.toolTip(),
        component.defaultValue(),
        component.adminComments(),
        component.fieldLength(),
        component.defaultRdbTableName(),
        component.rdbColumnName(),
        component.defaultLabelInReport(),
        component.dataMartColumnName(),
        component.type(),
        component.dataLocation(),
        component.isPublished(),
        component.questionGroupSeq(),
        component.blockName(),
        component.dataMartRepeatNumber(),
        component.appearsInBatch(),
        component.batchLabel(),
        component.batchWidth(),
        component.componentBehavior(),
        component.componentName(),
        component.classCode());
  }

  private InputNode asInput(final InputNode.Type type, final FlattenedComponent flattened) {
    return new InputNode(
        flattened.identifier(),
        type,
        asDefinition(flattened),
        asAttributes(flattened),
        flattened.allowFutureDates());
  }

  private EntryNode asEntry(final EntryNode.Type type, final FlattenedComponent flattened) {
    return new EntryNode(
        flattened.identifier(), type, asDefinition(flattened), asAttributes(flattened));
  }

  private SelectionNode asSelection(
      final SelectionNode.Type type, final FlattenedComponent flattened) {
    return new SelectionNode(
        flattened.identifier(),
        type,
        asDefinition(flattened),
        asAttributes(flattened),
        flattened.valueSet());
  }

  private StaticNode asStatic(final StaticNode.Type type, final FlattenedComponent flattened) {
    return new StaticNode(
        flattened.identifier(), type, asDefinition(flattened), asAttributes(flattened));
  }
}
