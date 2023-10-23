package gov.cdc.nbs.questionbank.page.component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class SubSectionNode extends LayoutNode {

  private final Collection<ContentNode> children;

  public SubSectionNode(final long identifier, final Definition definition) {
    super(identifier, LayoutNode.Type.SUB_SECTION, definition);
    this.children = new ArrayList<>();
  }

  public Collection<ContentNode> children() {
    return List.copyOf(this.children);
  }

  public SubSectionNode add(final ContentNode child) {
    this.children.add(child);
    return this;
  }

}
