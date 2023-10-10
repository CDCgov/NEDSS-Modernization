package gov.cdc.nbs.questionbank.page.detail;



import gov.cdc.nbs.questionbank.page.component.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
class ComponentTreeResolver {

  private final FlattenedComponentFinder finder;

  ComponentTreeResolver(final FlattenedComponentFinder finder) {
    this.finder = finder;
  }

  Optional<Component> resolve(final long page) {
    return this.finder.find(page)
        .stream()
        .collect(Treeify.asTree());
  }
}
