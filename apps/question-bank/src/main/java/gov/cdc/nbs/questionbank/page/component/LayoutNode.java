package gov.cdc.nbs.questionbank.page.component;

public abstract sealed class LayoutNode extends ComponentNode permits PageNode, SectionNode, SubSectionNode, TabNode {

  public enum Type implements ComponentNode.Type {

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

  protected LayoutNode(
      final long identifier,
      final Type type,
      final Definition definition
  ) {
    super(identifier, type, definition);
  }


}
