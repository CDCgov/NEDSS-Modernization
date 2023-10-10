package gov.cdc.nbs.questionbank.page.component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class Tab extends Layout {

  private final Collection<Section> children;

  public Tab(final long identifier, final Definition definition) {
    super(identifier, Layout.Type.TAB, definition);
    this.children = new ArrayList<>();
  }

  public void add(final Section child) {
    this.children.add(child);
  }

  public Collection<Section> children() {
    return List.copyOf(this.children);
  }

}
