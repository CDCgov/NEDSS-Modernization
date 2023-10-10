package gov.cdc.nbs.questionbank.page.detail;

import gov.cdc.nbs.questionbank.page.component.Component;
import gov.cdc.nbs.questionbank.page.component.Layout;
import gov.cdc.nbs.questionbank.page.component.Page;
import gov.cdc.nbs.questionbank.page.component.Section;
import gov.cdc.nbs.questionbank.page.component.SubSection;
import gov.cdc.nbs.questionbank.page.component.Tab;

class LayoutComponentResolver {

  Layout resolve(final FlattenedComponent flattened) {
    Component.Type type = ComponentTypeResolver.resolve(flattened.type());
    return resolve(type, flattened);
  }

  Layout resolve(final Component.Type type, final FlattenedComponent flattened) {

    if (Layout.Type.PAGE.equals(type)) {
      return asPage(flattened);
    } else if (Layout.Type.TAB.equals(type)) {
      return asTab(flattened);
    } else if (Layout.Type.SECTION.equals(type)) {
      return asSection(flattened);
    } else if (Layout.Type.SUB_SECTION.equals(type)) {
      return asSubSection(flattened);
    } else {
      throw new IllegalStateException("Unresolvable Layout Component Type: " + type);
    }

  }

  private Component.Definition asDefinition(final FlattenedComponent component) {
    return new Component.Definition(
        component.name(),
        component.visible(),
        component.order()
    );
  }

  private Page asPage(final FlattenedComponent component) {
    return new Page(
        component.identifier(),
        asDefinition(component)

    );
  }

  private Tab asTab(final FlattenedComponent component) {
    return new Tab(
        component.identifier(),
        asDefinition(component)
    );
  }

  private Section asSection(final FlattenedComponent component) {
    return new Section(
        component.identifier(),
        asDefinition(component)
    );
  }

  private SubSection asSubSection(final FlattenedComponent component) {
    return new SubSection(
        component.identifier(),
        asDefinition(component)
    );
  }
}
