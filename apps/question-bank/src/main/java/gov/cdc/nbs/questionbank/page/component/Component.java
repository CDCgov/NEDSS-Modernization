package gov.cdc.nbs.questionbank.page.component;

public abstract sealed class Component permits Layout, Content {

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
  private final Component.Type type;
  private final Component.Definition definition;

  protected Component(
      final long identifier,
      final Component.Type type,
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

  @Override
  public String toString() {
    return getClass().getSimpleName() + "{" +
        "identifier=" + identifier +
        '}';
  }
}
