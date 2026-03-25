package gov.cdc.nbs.questionbank.page.component.tree;

import gov.cdc.nbs.questionbank.page.component.ComponentNode;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ComponentTreeResolver {

  private final FlattenedComponentFinder finder;

  ComponentTreeResolver(final FlattenedComponentFinder finder) {
    this.finder = finder;
  }

  public Optional<ComponentNode> resolve(final long page) {
    return this.finder.find(page).stream().collect(Treeify.asTree());
  }
}
