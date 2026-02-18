package gov.cdc.nbs.questionbank.page.component.tree;

import gov.cdc.nbs.questionbank.page.component.ComponentNode;
import gov.cdc.nbs.questionbank.page.component.ContentNode;
import gov.cdc.nbs.questionbank.page.component.PageNode;
import gov.cdc.nbs.questionbank.page.component.SectionNode;
import gov.cdc.nbs.questionbank.page.component.SubSectionNode;
import gov.cdc.nbs.questionbank.page.component.TabNode;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

class Treeify {

  static Collector<FlattenedComponent, ComponentTree, Optional<ComponentNode>> asTree() {
    return Collectors.collectingAndThen(
        Collector.of(ComponentTree::new, ComponentTree::accept, ComponentTree::merge),
        ComponentTree::root);
  }

  private Treeify() {}

  private static class ComponentTree {

    private final ComponentResolver resolver;
    private final Deque<ComponentNode> stack;
    private ComponentNode root;

    private ComponentTree() {
      this(new ComponentResolver());
    }

    private ComponentTree(final ComponentResolver resolver) {
      this.resolver = resolver;
      this.stack = new ArrayDeque<>();
    }

    private ComponentTree accept(final FlattenedComponent flattened) {

      ComponentNode next = this.resolver.resolve(flattened);

      if (stack.isEmpty()) {
        this.root = next;
        stack.push(next);
      } else if (next instanceof PageNode) {
        throw new IllegalStateException("A page should be a top level next");
      } else {
        include(this.stack.peek(), next);
      }

      return this;
    }

    private void include(final ComponentNode current, final ComponentNode next) {
      if (next instanceof PageNode) {
        throw new IllegalStateException("A page should be a top level next");
      } else if (next instanceof TabNode tab && current instanceof PageNode page) {
        page.add(tab);
        this.stack.push(next);
      } else if (next instanceof SectionNode section && current instanceof TabNode tab) {
        tab.add(section);
        this.stack.push(next);
      } else if (next instanceof SubSectionNode subSection
          && current instanceof SectionNode section) {
        section.add(subSection);
        this.stack.push(next);
      } else if (next instanceof ContentNode content
          && current instanceof SubSectionNode subSection) {
        subSection.add(content);
        this.stack.push(next);
      } else {
        // next is a container that should be re-included at one level up
        includeAbove(next);
      }
    }

    private void includeAbove(final ComponentNode next) {
      //  go up
      this.stack.pop();

      //  get the next parent container
      ComponentNode current = this.stack.peek();
      include(current, next);
    }

    private ComponentTree merge(final ComponentTree other) {
      return this;
    }

    private Optional<ComponentNode> root() {
      return this.root == null ? Optional.empty() : Optional.of(this.root);
    }
  }
}
