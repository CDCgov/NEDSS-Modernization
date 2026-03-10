package gov.cdc.nbs.questionbank.page.content.reorder.models;

import gov.cdc.nbs.questionbank.page.content.reorder.ReorderException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ReorderablePage {

  public static final int PAGE_TYPE = 1002;
  public static final int TAB = 1010;
  public static final int SECTION = 1015;
  public static final int SUBSECTION = 1016;

  private final List<Tab> tabs = new ArrayList<>();
  private final Long id;

  public ReorderablePage(Long id) {
    this.id = id;
  }

  public List<Tab> getTabs() {
    return tabs;
  }

  public void move(long toMove, long afterId) {
    PageComponent componentToMove = findAndRemoveComponent(toMove);
    if (componentToMove instanceof Tab t) {
      insertTabAfter(t, afterId);
    } else if (componentToMove instanceof Section s) {
      insertSectionAfter(s, afterId);
    } else if (componentToMove instanceof Subsection ss) {
      insertSubsectionAfter(ss, afterId);
    } else if (componentToMove instanceof Element e) {
      insertElementAfter(e, afterId);
    }
  }

  /** Converts the ReorderablePage into a list of PageEntry's */
  @SuppressWarnings("javaarchitecture:S7027") // References static fields
  public List<PageEntry> toPageEntries() {
    List<PageEntry> entries = new ArrayList<>();
    int orderNumber = 1;
    entries.add(new PageEntry(id, PAGE_TYPE, orderNumber++));
    for (Tab t : tabs) {
      entries.add(new PageEntry(t.getId(), TAB, orderNumber++));
      for (Section s : t.getSections()) {
        entries.add(new PageEntry(s.getId(), SECTION, orderNumber++));
        for (Subsection ss : s.getSubsections()) {
          entries.add(new PageEntry(ss.getId(), SUBSECTION, orderNumber++));
          for (Element e : ss.getElements()) {
            entries.add(new PageEntry(e.getId(), e.getType(), orderNumber++));
          }
        }
      }
    }
    return entries;
  }

  private PageComponent findAndRemoveComponent(long id) {
    Tab tab = tabs.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    if (tab != null) {
      tabs.remove(tab);
      return tab;
    } else {
      return tabs.stream()
          .map(t -> t.findAndRemoveComponent(id))
          .filter(Objects::nonNull)
          .findFirst()
          .orElseThrow(() -> new ReorderException("Failed to find component to move"));
    }
  }

  public void insertTabAfter(Tab tab, Long afterId) {
    // a Tab can be inserted after the root element, making it the first entry in the Tabs list
    // or after another Tab contained in the list
    if (id.equals(afterId)) {
      tabs.add(0, tab);
    } else {
      Tab after =
          tabs.stream()
              .filter(t -> t.getId() == afterId)
              .findFirst()
              .orElseThrow(() -> new ReorderException("Failed to insert tab after: " + afterId));
      tabs.add(tabs.indexOf(after) + 1, tab);
    }
  }

  private void insertSectionAfter(Section section, long afterId) {
    boolean inserted =
        tabs.stream().map(t -> t.insertSectionAfter(section, afterId)).anyMatch(i -> i);
    if (!inserted) {
      throw new ReorderException("Failed to insert section after component: " + afterId);
    }
  }

  private void insertSubsectionAfter(Subsection subsection, long afterId) {
    boolean inserted =
        tabs.stream().map(t -> t.insertSubsectionAfter(subsection, afterId)).anyMatch(i -> i);
    if (!inserted) {
      throw new ReorderException("Failed to insert subsection after component: " + afterId);
    }
  }

  private void insertElementAfter(Element toMove, long afterId) {
    boolean inserted =
        tabs.stream().map(t -> t.insertElementAfter(toMove, afterId)).anyMatch(i -> i);
    if (!inserted) {
      throw new ReorderException("Failed to insert element after component: " + afterId);
    }
  }

  private abstract static class PageComponent {
    private long id;

    public PageComponent(long id) {
      this.id = id;
    }

    public long getId() {
      return id;
    }
  }

  static class Tab extends PageComponent {
    private List<Section> sections = new ArrayList<>();

    public Tab(long id) {
      super(id);
    }

    public PageComponent findAndRemoveComponent(long id) {
      Section section = sections.stream().filter(s -> s.getId() == id).findFirst().orElse(null);
      if (section != null) {
        sections.remove(section);
        return section;
      } else {
        return sections.stream()
            .map(s -> s.findAndRemoveComponent(id))
            .filter(Objects::nonNull)
            .findFirst()
            .orElse(null);
      }
    }

    private boolean insertSectionAfter(Section toMove, long afterId) {
      // a Section can be inserted after a `Tab`, making it the first entry in the sections list
      // or after a section contained in the tab
      if (super.getId() == afterId) {
        sections.add(0, toMove);
        return true;
      } else {
        // attempt to find the section to place after
        Section after =
            sections.stream().filter(s -> s.getId() == afterId).findFirst().orElse(null);
        if (after != null) {
          sections.add(sections.indexOf(after) + 1, toMove);
          return true;
        }
        return false;
      }
    }

    public boolean insertSubsectionAfter(Subsection toMove, long afterId) {
      return sections.stream().map(t -> t.insertSubsectionAfter(toMove, afterId)).anyMatch(i -> i);
    }

    public boolean insertElementAfter(Element toMove, long afterId) {
      return sections.stream().map(s -> s.insertElementAfter(toMove, afterId)).anyMatch(i -> i);
    }

    public List<Section> getSections() {
      return sections;
    }
  }

  static class Section extends PageComponent {
    private List<Subsection> subsections = new ArrayList<>();

    public Section(long id) {
      super(id);
    }

    public PageComponent findAndRemoveComponent(long id) {
      Subsection subsection =
          subsections.stream().filter(ss -> ss.getId() == id).findFirst().orElse(null);
      if (subsection != null) {
        subsections.remove(subsection);
        return subsection;
      } else {
        return subsections.stream()
            .map(ss -> ss.findAndRemoveComponent(id))
            .filter(Objects::nonNull)
            .findFirst()
            .orElse(null);
      }
    }

    public boolean insertSubsectionAfter(Subsection toMove, long afterId) {
      // a Subsection can be inserted after a `Section`, making it the first entry in the Subsection
      // list
      // or after another Subsection contained in the Section
      if (super.getId() == afterId) {
        subsections.add(0, toMove);
        return true;
      } else {
        Subsection after =
            subsections.stream().filter(s -> s.getId() == afterId).findFirst().orElse(null);
        if (after != null) {
          subsections.add(subsections.indexOf(after) + 1, toMove);
          return true;
        }
        return false;
      }
    }

    public boolean insertElementAfter(Element toMove, long afterId) {
      return subsections.stream()
          .map(ss -> ss.insertElementAfter(toMove, afterId))
          .anyMatch(i -> i);
    }

    public List<Subsection> getSubsections() {
      return subsections;
    }
  }

  static class Subsection extends PageComponent {
    private List<Element> elements = new ArrayList<>();

    public Subsection(long id) {
      super(id);
    }

    public PageComponent findAndRemoveComponent(long id) {
      Element element = elements.stream().filter(e -> e.getId() == id).findFirst().orElse(null);
      if (element != null) {
        elements.remove(element);
        return element;
      } else {
        return null;
      }
    }

    public boolean insertElementAfter(Element toMove, long afterId) {
      // an Element can be inserted after a `Subsection`, making it the first entry in the element
      // list
      // or after another Element contained in the Subsection
      if (super.getId() == afterId) {
        elements.add(0, toMove);
        return true;
      } else {
        Element after =
            elements.stream().filter(e -> e.getId() == afterId).findFirst().orElse(null);
        if (after != null) {
          elements.add(elements.indexOf(after) + 1, toMove);
          return true;
        }
        return false;
      }
    }

    public List<Element> getElements() {
      return elements;
    }
  }

  static class Element extends PageComponent {
    private int type;

    public Element(long id, int type) {
      super(id);
      this.type = type;
    }

    public int getType() {
      return type;
    }
  }
}
