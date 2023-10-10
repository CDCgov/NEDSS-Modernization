package gov.cdc.nbs.questionbank.page.component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class Page extends Layout {

  private final Collection<Tab> children;

  public Page(final long identifier, final Definition definition) {
    super(identifier, Layout.Type.PAGE, definition);
    this.children = new ArrayList<>();
  }

  public void add(final Tab child) {
    this.children.add(child);
  }

  public Collection<Tab> children() {
    return List.copyOf(this.children);
  }


}
