package gov.cdc.nbs.questionbank.page.component;

public final class SelectionNode extends ContentNode {

  public enum Type implements ComponentNode.Type {
    SINGLE_SELECT(1007L),
    MULTI_SELECT(1013L),
    CHECKBOX(1001L),
    RADIO(1006L),
    SINGLE_SELECT_READ_ONLY_SAVE(1024L),
    MULTI_SELECT_READ_ONLY_SAVE(1025L),
    SINGLE_SELECT_READ_ONLY_NO_SAVE(1027L),
    MULTI_SELECT_READ_ONLY_NO_SAVE(1028L);

    private final long identifier;

    Type(long identifier) {
      this.identifier = identifier;
    }

    @Override
    public long identifier() {
      return this.identifier;
    }
  }

  private final String valueSet;

  public SelectionNode(
      final long identifier,
      final Type type,
      final Definition definition,
      final Attributes attributes,
      final String valueSet) {
    super(identifier, type, definition, attributes);
    this.valueSet = valueSet;
  }

  public String valueSet() {
    return valueSet;
  }
}
