package gov.cdc.nbs.questionbank.page.component;

public abstract sealed class ContentNode extends ComponentNode permits EntryNode, InputNode, SelectionNode, StaticNode {

  public record Attributes(
      boolean isStandardNnd,
      boolean isStandard,
      String standard,
      String question,
      String dataType,
      String subGroup,
      String description,
      boolean enabled,
      boolean required,
      boolean coInfection,
      String mask,
      String toolTip,
      String defaultValue,
      String adminComments,
      String fieldLength,
      String defaultRdbTableName,
      String rdbColumnName,
      String defaultLabelInReport,
      String dataMartColumnName,
      int nbsComponentId,
      String dataLocation,
      boolean isPublished,
      int questionGroupSeq,
      String blockName,
      Integer dataMartRepeatNumber,
      boolean appearsInBatch,
      String batchLabel,
      Integer batchWidth,
      String componentBehavior,
      String componentName,
      String classCode) {
  }


  private final Attributes attributes;


  protected ContentNode(
      final long identifier,
      final Type type,
      final Definition definition,
      final Attributes attributes) {
    super(identifier, type, definition);
    this.attributes = attributes;
  }

  public Attributes attributes() {
    return attributes;
  }

}


