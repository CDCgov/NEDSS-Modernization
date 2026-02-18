package gov.cdc.nbs.questionbank.page.component;

public final class InputNode extends ContentNode {

  public enum Type implements ComponentNode.Type {
    INPUT(1008L),
    INPUT_READ_ONLY_SAVE(1026L),

    INPUT_READ_ONLY_NO_SAVE(1029L);

    private final long identifier;

    Type(long identifier) {
      this.identifier = identifier;
    }

    @Override
    public long identifier() {
      return this.identifier;
    }
  }

  private final boolean allowFutureDates;

  public InputNode(
      final long identifier,
      final Type type,
      final Definition definition,
      final Attributes attributes,
      final boolean allowFutureDates) {
    super(identifier, type, definition, attributes);
    this.allowFutureDates = allowFutureDates;
  }

  public boolean allowFutureDates() {
    return allowFutureDates;
  }
}
