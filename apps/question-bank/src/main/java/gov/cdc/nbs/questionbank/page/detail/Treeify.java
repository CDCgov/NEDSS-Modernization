package gov.cdc.nbs.questionbank.page.detail;

import gov.cdc.nbs.questionbank.page.component.Component;
import gov.cdc.nbs.questionbank.page.component.Content;
import gov.cdc.nbs.questionbank.page.component.Page;
import gov.cdc.nbs.questionbank.page.component.Section;
import gov.cdc.nbs.questionbank.page.component.SubSection;
import gov.cdc.nbs.questionbank.page.component.Tab;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

class Treeify {

  static Collector<FlattenedComponent, ComponentTree, Optional<Component>> asTree() {
    return Collectors.collectingAndThen(
        Collector.of(
            ComponentTree::new,
            ComponentTree::accept,
            ComponentTree::merge
        ),
        ComponentTree::root
    );
  }

  private Treeify() {
  }

  private static class ComponentTree {

    private final ComponentResolver resolver;
    private final Deque<Component> stack;
    private Component root;

    private ComponentTree() {
      this(new ComponentResolver());
    }

    private ComponentTree(final ComponentResolver resolver) {
      this.resolver = resolver;
      this.stack = new ArrayDeque<>();
    }

    private ComponentTree accept(final FlattenedComponent flattened) {

      Component next = this.resolver.resolve(flattened);

      if (stack.isEmpty()) {
        this.root = next;
        stack.push(next);
      } else if (next instanceof Page) {
        throw new IllegalStateException("A page should be a top level next");
      } else {
        include(this.stack.peek(), next);
      }

      return this;
    }

    private void include(final Component current, final Component next) {
      if (next instanceof Page) {
        throw new IllegalStateException("A page should be a top level next");
      } else if (next instanceof Tab tab && current instanceof Page page) {
        page.add(tab);
        this.stack.push(next);
      } else if (next instanceof Section section && current instanceof Tab tab) {
        tab.add(section);
        this.stack.push(next);
      } else if (next instanceof SubSection subSection && current instanceof Section section) {
        section.add(subSection);
        this.stack.push(next);
      } else if (next instanceof Content content && current instanceof SubSection subSection) {
        subSection.add(content);
        this.stack.push(next);
      } else {
        // next is a container that should be re-included at one level up
        includeAbove(next);
      }
    }

    private void includeAbove(final Component next) {
      //  go up
      this.stack.pop();

      //  get the next parent container
      Component current = this.stack.peek();
      include(current, next);

    }

    private ComponentTree merge(final ComponentTree other) {
      return this;
    }

    private Optional<Component> root() {
      return this.root == null
          ? Optional.empty()
          : Optional.of(this.root);
    }
  }
}
