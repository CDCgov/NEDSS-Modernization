package gov.cdc.nbs.questionbank.page.component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class SubSectionNode extends LayoutNode {

  private final Collection<ContentNode> children;
  private boolean isGrouped;

  public boolean isGrouped() {
    return isGrouped;
  }

  public SubSectionNode(final long identifier, final Definition definition,boolean isGrouped) {
    super(identifier, LayoutNode.Type.SUB_SECTION, definition);
    this.isGrouped=isGrouped;
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
