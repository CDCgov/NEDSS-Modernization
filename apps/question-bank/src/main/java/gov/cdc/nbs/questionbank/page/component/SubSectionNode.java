package gov.cdc.nbs.questionbank.page.component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class SubSectionNode extends LayoutNode {

  private final Collection<ContentNode> children;
  private boolean isGrouped;
  private String questionIdentifier;
  private String blockName;
  private Integer dataMartRepeatNumber;

  public boolean isGrouped() {
    return isGrouped;
  }

  public String questionIdentifier() {
    return questionIdentifier;
  }

  public String blockName() {
    return blockName;
  }

  public Integer dataMartRepeatNumber() {
    return dataMartRepeatNumber;
  }

  public SubSectionNode(
      final long identifier,
      final Definition definition,
      boolean isGrouped,
      String questionIdentifier,
      String blockName,
      Integer dataMartRepeatNumber) {
    super(identifier, LayoutNode.Type.SUB_SECTION, definition);
    this.isGrouped = isGrouped;
    this.questionIdentifier = questionIdentifier;
    this.blockName = blockName;
    this.dataMartRepeatNumber = dataMartRepeatNumber;
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
