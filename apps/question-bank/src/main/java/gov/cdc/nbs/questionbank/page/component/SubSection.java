package gov.cdc.nbs.questionbank.page.component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class SubSection extends Layout {

  private final Collection<Content> children;

  public SubSection(final long identifier, final Definition definition) {
    super(identifier, Layout.Type.SUB_SECTION, definition);
    this.children = new ArrayList<>();
  }

  public void add(final Content child) {
    this.children.add(child);
  }

  public Collection<Content> children() {
    return List.copyOf(this.children);
  }

}
