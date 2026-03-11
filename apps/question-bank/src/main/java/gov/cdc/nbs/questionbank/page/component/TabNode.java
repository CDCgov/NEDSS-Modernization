package gov.cdc.nbs.questionbank.page.component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class TabNode extends LayoutNode {

  private final Collection<SectionNode> children;

  public TabNode(final long identifier, final Definition definition) {
    super(identifier, LayoutNode.Type.TAB, definition);
    this.children = new ArrayList<>();
  }

  public Collection<SectionNode> children() {
    return List.copyOf(this.children);
  }

  public TabNode add(final SectionNode child) {
    this.children.add(child);
    return this;
  }
}
