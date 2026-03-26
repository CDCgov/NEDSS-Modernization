package gov.cdc.nbs.questionbank.page.component;

public final class StaticNode extends ContentNode {

  public enum Type implements ComponentNode.Type {
    HYPERLINK(1003L),
    SUB_HEADING(1011L),
    LINE_SEPARATOR(1012L),
    TABLE(1022L),
    INFORMATION_BAR(1023L);

    private final long identifier;

    Type(long identifier) {
      this.identifier = identifier;
    }

    @Override
    public long identifier() {
      return this.identifier;
    }
  }

  public StaticNode(
      final long identifier,
      final Type type,
      final Definition definition,
      final Attributes attributes) {
    super(identifier, type, definition, attributes);
  }
}
