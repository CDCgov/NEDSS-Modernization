package gov.cdc.nbs.questionbank.page.component;

@SuppressWarnings("javaarchitecture:S7027") //  sealed interface must list implementing classes
public abstract sealed class ComponentNode permits LayoutNode, ContentNode {

  public record Definition(
      String name,

      boolean visible,

      int order
  ) {
  }


  public interface Type {
    long identifier();

  }


  private final long identifier;
  private final ComponentNode.Type type;
  private final ComponentNode.Definition definition;

  protected ComponentNode(
      final long identifier,
      final ComponentNode.Type type,
      final Definition definition
  ) {
    this.identifier = identifier;
    this.type = type;
    this.definition = definition;
  }

  public long identifier() {
    return identifier;
  }

  public Type type() {
    return type;
  }

  public Definition definition() {
    return definition;
  }

  public String name() {
    return this.definition.name();
  }

  public boolean visible() {
    return this.definition.visible();
  }

  public int order() {
    return this.definition.order;
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "{" +
        "identifier=" + identifier +
        '}';
  }
}
