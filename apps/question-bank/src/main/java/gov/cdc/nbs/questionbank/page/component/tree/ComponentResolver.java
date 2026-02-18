package gov.cdc.nbs.questionbank.page.component.tree;

import gov.cdc.nbs.questionbank.page.component.ComponentNode;
import gov.cdc.nbs.questionbank.page.component.LayoutNode;

class ComponentResolver {

  private final LayoutComponentResolver layoutResolver;
  private final ContentComponentResolver contentResolver;

  ComponentResolver() {
    this(new LayoutComponentResolver(), new ContentComponentResolver());
  }

  ComponentResolver(
      final LayoutComponentResolver layoutResolver,
      final ContentComponentResolver contentResolver) {
    this.layoutResolver = layoutResolver;
    this.contentResolver = contentResolver;
  }

  ComponentNode resolve(final FlattenedComponent flattened) {
    ComponentNode.Type type = ComponentTypeResolver.resolve(flattened.type());
    return resolve(type, flattened);
  }

  ComponentNode resolve(final ComponentNode.Type type, final FlattenedComponent flattened) {

    if (type instanceof LayoutNode.Type) {
      return this.layoutResolver.resolve(type, flattened);
    } else if (type != null) {
      return this.contentResolver.resolve(type, flattened);
    } else {
      throw new IllegalStateException("A Component type is required");
    }
  }
}
