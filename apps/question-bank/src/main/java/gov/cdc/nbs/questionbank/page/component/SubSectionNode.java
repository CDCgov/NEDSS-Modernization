package gov.cdc.nbs.questionbank.page.component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class SubSectionNode extends LayoutNode {

  private final Collection<ContentNode> children;
  private boolean isGrouped;
  private String questionIdentifier;

  public boolean isGrouped() {
    return isGrouped;
  }

  public String questionIdentifier() {
    return questionIdentifier;
  }

  public SubSectionNode(
      final long identifier,
      final Definition definition,
      boolean isGrouped,
      String questionIdentifier) {
    super(identifier, LayoutNode.Type.SUB_SECTION, definition);
    this.isGrouped = isGrouped;
    this.questionIdentifier = questionIdentifier;
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
