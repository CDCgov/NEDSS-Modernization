package gov.cdc.nbs.questionbank.page.content.reorder.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import gov.cdc.nbs.questionbank.page.content.reorder.ReorderException;

public class ReorderablePage {
    private List<Tab> tabs;

    public ReorderablePage(List<Tab> tabs) {
        this.tabs = tabs;
    }

    public List<Tab> getTabs() {
        return tabs;
    }

    public void moveTab(long tabId, long afterId) {
        Tab toMove = tabs.stream()
                .filter(t -> t.getId() == tabId)
                .findFirst()
                .orElseThrow(() -> new ReorderException("Failed to find tab"));
        Tab after = tabs.stream()
                .filter(t -> t.getId() == afterId)
                .findFirst()
                .orElse(null);

        tabs.remove(toMove);
        int index = tabs.indexOf(after) + 1;
        tabs.add(index, toMove);
    }

    public void moveSection(long sectionId, long afterId) {
        Section toMove = findAndRemoveSection(sectionId);
        boolean inserted = insertSectionAfter(toMove, afterId);
        if (!inserted) {
            throw new ReorderException("Failed to insert section after element");
        }
    }

    private Section findAndRemoveSection(long id) {
        return tabs.stream()
                .map(t -> t.findAndRemoveSection(id))
                .findFirst()
                .orElseThrow(() -> new ReorderException("Failed to find section"));
    }

    private boolean insertSectionAfter(Section section, long afterId) {
        // Could be a Tab or an existing Section
        Optional<Tab> tab = tabs.stream().filter(t -> t.getId() == afterId).findFirst();
        if (tab.isPresent()) {
            tab.get().getSections().add(0, section);
            return true;
        } else {
            return tabs.stream()
                    .map(t -> t.insertSectionAfter(section, afterId))
                    .anyMatch(i -> i);
        }
    }

    public void moveSubsection(long subsectionId, long afterId) {
        Subsection toMove = findAndRemoveSubsection(subsectionId);
        boolean inserted = insertSubsectionAfter(toMove, afterId);
        if (!inserted) {
            throw new ReorderException("Failed to insert subsection after element");
        }
    }

    private Subsection findAndRemoveSubsection(long subsectionId) {
        return tabs.stream()
                .map(t -> t.findAndRemoveSubsection(subsectionId))
                .filter(Objects::nonNull)
                .findFirst()
                .orElseThrow(() -> new ReorderException("Failed to find subsection"));
    }

    private boolean insertSubsectionAfter(Subsection subsection, long afterId) {
        return tabs.stream()
                .map(t -> t.insertSubsectionAfter(subsection, afterId))
                .anyMatch(i -> i);
    }

    public void moveElement(long elementId, long afterId) {
        Element toMove = findAndRemoveElement(elementId);
        boolean inserted = insertElementAfter(toMove, afterId);
        if (!inserted) {
            throw new ReorderException("Failed to insert element");
        }
    }

    private Element findAndRemoveElement(long elementId) {
        return tabs.stream()
                .map(t -> t.findAndRemoveElement(elementId))
                .filter(Objects::nonNull)
                .findFirst()
                .orElseThrow(() -> new ReorderException("Failed to find element"));
    }

    private boolean insertElementAfter(Element toMove, long afterId) {
        return tabs.stream()
                .map(t -> t.insertElementAfter(toMove, afterId))
                .anyMatch(i -> i);
    }

    public static class Tab {
        private List<Section> sections = new ArrayList<>();
        private long id;

        public Tab(long id) {
            this.id = id;
        }

        public boolean insertElementAfter(Element toMove, long afterId) {
            return sections.stream()
                    .map(s -> s.insertElementAfter(toMove, afterId))
                    .anyMatch(i -> i);
        }

        public Element findAndRemoveElement(long elementId) {
            return sections.stream()
                    .map(s -> s.findAndRemoveElement(elementId))
                    .filter(Objects::nonNull)
                    .findFirst()
                    .orElse(null);
        }

        public boolean insertSubsectionAfter(Subsection subsection, long afterId) {
            // Could be a Section or an existing Subsection
            Optional<Section> section = sections.stream()
                    .filter(t -> t.getId() == afterId)
                    .findFirst();
            if (section.isPresent()) {
                section.get().getSubsections().add(0, subsection);
                return true;
            } else {
                return sections.stream()
                        .map(t -> t.insertSubsectionAfter(subsection, afterId))
                        .anyMatch(i -> i);
            }
        }

        private Subsection findAndRemoveSubsection(long subsectionId) {
            return sections.stream()
                    .map(s -> s.findAndRemoveSubsection(subsectionId))
                    .filter(Objects::nonNull)
                    .findFirst()
                    .orElse(null);
        }

        private Section findAndRemoveSection(long id) {
            Section section = sections.stream()
                    .filter(s -> s.getId() == id)
                    .findFirst()
                    .orElse(null);
            if (section != null) {
                sections.remove(section);
            }
            return section;
        }

        private boolean insertSectionAfter(Section section, long id) {
            Section after = sections.stream()
                    .filter(s -> s.getId() == id)
                    .findFirst()
                    .orElse(null);
            if (after != null) {
                sections.add(sections.indexOf(after) + 1, section);
                return true;
            }
            return false;
        }

        public long getId() {
            return id;
        }

        public List<Section> getSections() {
            return sections;
        }

    }

    public static class Section {
        private List<Subsection> subsections = new ArrayList<>();
        private long id;

        public long getId() {
            return id;
        }

        public boolean insertElementAfter(Element toMove, long afterId) {
            // Could be Subsection or Element
            Optional<Subsection> subsection = subsections.stream()
                    .filter(t -> t.getId() == afterId)
                    .findFirst();
            if (subsection.isPresent()) {
                subsection.get().getElements().add(0, toMove);
                return true;
            } else {
                return subsections.stream()
                        .map(ss -> ss.insertElementAfter(toMove, afterId))
                        .anyMatch(i -> i);
            }
        }

        public Element findAndRemoveElement(long elementId) {
            return subsections.stream()
                    .map(s -> s.findAndRemoveElement(elementId))
                    .filter(Objects::nonNull)
                    .findFirst()
                    .orElse(null);
        }

        public Section(long id) {
            this.id = id;
        }

        public boolean insertSubsectionAfter(Subsection subsection, long afterId) {
            Subsection after = subsections.stream()
                    .filter(s -> s.getId() == afterId)
                    .findFirst()
                    .orElse(null);
            if (after != null) {
                subsections.add(subsections.indexOf(after) + 1, subsection);
                return true;
            }
            return false;
        }

        private Subsection findAndRemoveSubsection(long subsectionId) {
            Subsection subsection = subsections.stream()
                    .filter(s -> s.getId() == subsectionId)
                    .findFirst()
                    .orElse(null);
            if (subsection != null) {
                subsections.remove(subsection);
            }
            return subsection;
        }


        public List<Subsection> getSubsections() {
            return subsections;
        }
    }

    public static class Subsection {
        private List<Element> elements = new ArrayList<>();
        private long id;

        public long getId() {
            return id;
        }

        public boolean insertElementAfter(Element toMove, long afterId) {
            Element after = elements.stream()
                    .filter(e -> e.getId() == afterId)
                    .findFirst()
                    .orElse(null);
            if (after != null) {
                elements.add(elements.indexOf(after) + 1, toMove);
                return true;
            }
            return false;
        }

        public Element findAndRemoveElement(long elementId) {
            Element element = elements.stream()
                    .filter(s -> s.getId() == elementId)
                    .findFirst()
                    .orElse(null);
            if (element != null) {
                elements.remove(element);
            }
            return element;
        }

        public Subsection(long id) {
            this.id = id;
        }

        public List<Element> getElements() {
            return elements;
        }
    }

    public static class Element {
        private long id;

        public long getId() {
            return id;
        }

        public Element(long id) {
            this.id = id;
        }

    }
}
