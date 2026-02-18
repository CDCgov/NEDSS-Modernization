package gov.cdc.nbs.questionbank.page.component;

public final class EntryNode extends ContentNode {

  public enum Type implements ComponentNode.Type {
    READ_ONLY(1014L),
    BUTTON(1000L),
    TEXT_AREA(1009L),
    PARTICIPATION(1017L),
    ROLLING_NOTE(1019L),
    PARTICIPANT_LIST(1030L),
    PATIENT_SEARCH(1032L),
    ACTION_BUTTON(1033L),
    SET_VALUE_BUTTON(1034L),
    LOGIC_FLAG(1035L),
    ORIGINAL_DOCUMENT_LIST(1036L);

    private final long identifier;

    Type(long identifier) {
      this.identifier = identifier;
    }

    @Override
    public long identifier() {
      return this.identifier;
    }
  }

  public EntryNode(
      final long identifier,
      final Type type,
      final Definition definition,
      final Attributes attributes) {
    super(identifier, type, definition, attributes);
  }
}
