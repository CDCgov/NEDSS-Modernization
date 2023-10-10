package gov.cdc.nbs.questionbank.page.component;

public abstract sealed class Layout extends Component permits Page, Section, SubSection, Tab {

  public enum Type implements Component.Type {

    PAGE(1002L),
    TAB(1010L),
    SECTION(1015L),
    SUB_SECTION(1016L);

    private final long identifier;

    Type(final long identifier) {
      this.identifier = identifier;
    }

    @Override
    public long identifier() {
      return this.identifier;
    }
  }

  protected Layout(
      final long identifier,
      final Type type,
      final Definition definition
  ) {
    super(identifier, type, definition);
  }

}
