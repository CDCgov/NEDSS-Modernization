package gov.cdc.nbs.questionbank.page.component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class SectionNode extends LayoutNode {

  private final Collection<SubSectionNode> children;

  public SectionNode(final long identifier, final Definition definition) {
    super(identifier, LayoutNode.Type.SECTION, definition);
    this.children = new ArrayList<>();
  }

  public Collection<SubSectionNode> children() {
    return List.copyOf(this.children);
  }

  public SectionNode add(final SubSectionNode child) {
    this.children.add(child);
    return this;
  }
}
