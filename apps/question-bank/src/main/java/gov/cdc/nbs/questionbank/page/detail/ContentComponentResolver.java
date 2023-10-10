package gov.cdc.nbs.questionbank.page.detail;

import gov.cdc.nbs.questionbank.page.component.Component;
import gov.cdc.nbs.questionbank.page.component.Content;
import gov.cdc.nbs.questionbank.page.component.Entry;
import gov.cdc.nbs.questionbank.page.component.Input;
import gov.cdc.nbs.questionbank.page.component.Selection;

class ContentComponentResolver {

  Content resolve(final FlattenedComponent flattened) {
    Component.Type type = ComponentTypeResolver.resolve(flattened.type());
    return resolve(type, flattened);
  }

  Content resolve(final Component.Type type, final FlattenedComponent flattened) {
    if (type instanceof Input.Type input) {
      return asInput(input, flattened);
    } else if (type instanceof Entry.Type entry) {
      return asEntry(entry, flattened);
    } else if (type instanceof Selection.Type selection) {
      return asSelection(selection, flattened);
    } else {
      throw new IllegalStateException("Unresolvable Content Component Type: " + type);
    }
  }

  private Component.Definition asDefinition(final FlattenedComponent component) {
    return new Component.Definition(
        component.name(),
        component.visible(),
        component.order()
    );
  }

  private Content.Attributes asAttributes(final FlattenedComponent component) {
    return new Content.Attributes(
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
        component.defaultValue()
    );
  }

  private Input asInput(final Input.Type type, final FlattenedComponent flattened) {
    return new Input(
        flattened.identifier(),
        type,
        asDefinition(flattened),
        asAttributes(flattened),
        flattened.allowFutureDates()
    );
  }

  private Entry asEntry(final Entry.Type type, final FlattenedComponent flattened) {
    return new Entry(
        flattened.identifier(),
        type,
        asDefinition(flattened),
        asAttributes(flattened)
    );
  }

  private Selection asSelection(final Selection.Type type, final FlattenedComponent flattened) {
    return new Selection(
        flattened.identifier(),
        type,
        asDefinition(flattened),
        asAttributes(flattened),
        flattened.valueSet()
    );
  }
}
