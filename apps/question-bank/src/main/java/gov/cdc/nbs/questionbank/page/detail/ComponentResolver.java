package gov.cdc.nbs.questionbank.page.detail;

import gov.cdc.nbs.questionbank.page.component.Component;
import gov.cdc.nbs.questionbank.page.component.Layout;

class ComponentResolver {

  private final LayoutComponentResolver layoutResolver;
  private final ContentComponentResolver contentResolver;

  ComponentResolver() {
    this(new LayoutComponentResolver(), new ContentComponentResolver());
  }

  ComponentResolver(
      final LayoutComponentResolver layoutResolver,
      final ContentComponentResolver contentResolver
  ) {
    this.layoutResolver = layoutResolver;
    this.contentResolver = contentResolver;
  }

  Component resolve(final FlattenedComponent flattened) {
    Component.Type type = ComponentTypeResolver.resolve(flattened.type());
    return resolve(type, flattened);
  }

  Component resolve(
      final Component.Type type,
      final FlattenedComponent flattened
  ) {

    if (type instanceof Layout.Type) {
      return this.layoutResolver.resolve(type, flattened);
    } else if (type != null) {
      return this.contentResolver.resolve(type, flattened);
    } else {
      throw new IllegalStateException("A Component type is required");
    }
  }
}
