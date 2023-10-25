package gov.cdc.nbs.questionbank.page.component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class PageNode extends LayoutNode {

  private final Collection<TabNode> children;

  public PageNode(final long identifier, final Definition definition) {
    super(identifier, LayoutNode.Type.PAGE, definition);
    this.children = new ArrayList<>();
  }

  public Collection<TabNode> children() {
    return List.copyOf(this.children);
  }

  public PageNode add(final TabNode child) {
    this.children.add(child);
    return this;
  }
}
