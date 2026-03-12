package gov.cdc.nbs.questionbank.page.component.tree;

import gov.cdc.nbs.questionbank.page.component.ComponentNode;
import gov.cdc.nbs.questionbank.page.component.LayoutNode;
import gov.cdc.nbs.questionbank.page.component.PageNode;
import gov.cdc.nbs.questionbank.page.component.SectionNode;
import gov.cdc.nbs.questionbank.page.component.SubSectionNode;
import gov.cdc.nbs.questionbank.page.component.TabNode;

class LayoutComponentResolver {

  LayoutNode resolve(final FlattenedComponent flattened) {
    ComponentNode.Type type = ComponentTypeResolver.resolve(flattened.type());
    return resolve(type, flattened);
  }

  LayoutNode resolve(final ComponentNode.Type type, final FlattenedComponent flattened) {

    if (LayoutNode.Type.PAGE.equals(type)) {
      return asPage(flattened);
    } else if (LayoutNode.Type.TAB.equals(type)) {
      return asTab(flattened);
    } else if (LayoutNode.Type.SECTION.equals(type)) {
      return asSection(flattened);
    } else if (LayoutNode.Type.SUB_SECTION.equals(type)) {
      return asSubSection(flattened);
    } else {
      throw new IllegalStateException("Unresolvable Layout Component Type: " + type);
    }
  }

  private ComponentNode.Definition asDefinition(final FlattenedComponent component) {
    return new ComponentNode.Definition(component.name(), component.visible(), component.order());
  }

  private PageNode asPage(final FlattenedComponent component) {
    return new PageNode(component.identifier(), asDefinition(component));
  }

  private TabNode asTab(final FlattenedComponent component) {
    return new TabNode(component.identifier(), asDefinition(component));
  }

  private SectionNode asSection(final FlattenedComponent component) {
    return new SectionNode(component.identifier(), asDefinition(component));
  }

  private SubSectionNode asSubSection(final FlattenedComponent component) {
    return new SubSectionNode(
        component.identifier(),
        asDefinition(component),
        component.isGrouped(),
        component.question(),
        component.blockName(),
        component.dataMartRepeatNumber());
  }
}
