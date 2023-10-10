package gov.cdc.nbs.questionbank.page.component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class Section extends Layout {

  private final Collection<SubSection> children;

  public Section(final long identifier, final Definition definition) {
    super(identifier, Layout.Type.SECTION, definition);
    this.children = new ArrayList<>();
  }

  public void add(final SubSection child) {
    this.children.add(child);
  }

  public Collection<SubSection> children() {
    return List.copyOf(this.children);
  }

}
