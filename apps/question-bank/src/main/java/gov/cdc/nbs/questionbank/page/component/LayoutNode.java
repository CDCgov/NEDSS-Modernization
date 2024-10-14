package gov.cdc.nbs.questionbank.page.component;

@SuppressWarnings(
    //  Sealed classes require the implementing classes be listed if not in the same file
    "javaarchitecture:S7027"
)
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
