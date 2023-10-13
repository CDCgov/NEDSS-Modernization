package gov.cdc.nbs.questionbank.page.content.reorder;

import java.util.ArrayList;
import java.util.List;
import gov.cdc.nbs.questionbank.page.content.reorder.models.PageEntry;
import gov.cdc.nbs.questionbank.page.content.reorder.models.ReorderablePage;
import gov.cdc.nbs.questionbank.page.content.reorder.models.ReorderablePage.Element;
import gov.cdc.nbs.questionbank.page.content.reorder.models.ReorderablePage.Section;
import gov.cdc.nbs.questionbank.page.content.reorder.models.ReorderablePage.Subsection;
import gov.cdc.nbs.questionbank.page.content.reorder.models.ReorderablePage.Tab;

public class SimplePageMapper {
    private static final int PAGE_TYPE = 1002;
    private static final int TAB = 1010;
    private static final int SECTION = 1015;
    private static final int SUBSECTION = 1016;

    private List<Tab> tabs = new ArrayList<>();
    private Tab currentTab = null;
    private Section currentSection = null;
    private Subsection currentSubsection = null;

    public ReorderablePage toPage(List<PageEntry> entries) {
        ReorderablePage page = null;
        for (PageEntry e : entries) {
            switch (e.component()) {
                case PAGE_TYPE:
                    page = new ReorderablePage(e.id());
                    break;
                case TAB: {
                    rollUpTab();
                    currentTab = new Tab(e.id());
                    break;
                }
                case SECTION: {
                    rollUpSection();
                    currentSection = new Section(e.id());
                    break;
                }
                case SUBSECTION: {
                    rollUpSubsection();
                    currentSubsection = new Subsection(e.id());
                    break;
                }
                default: {
                    if (currentSubsection == null) {
                        throw new ReorderException("Element not included in subsection");
                    }
                    currentSubsection.getElements().add(new Element(e.id()));
                }
            }
        }
        if (currentTab != null) {
            if (currentSubsection != null) {
                currentSection.getSubsections().add(currentSubsection);
            }
            if (currentSection != null) {
                currentTab.getSections().add(currentSection);
            }
            tabs.add(currentTab);
        }
        if (page == null) {
            throw new ReorderException("Failed to build page. Invalid page content");
        }
        page.getTabs().addAll(tabs);
        reset();
        return page;
    }

    private void rollUpTab() {
        if (currentTab != null) {
            rollUpSubsection();
            rollUpSection();
            tabs.add(currentTab);
            currentTab = null;
        }
    }

    private void rollUpSection() {
        if (currentTab == null) {
            throw new ReorderException("Section not included in tab");
        }
        if (currentSection != null) {
            rollUpSubsection();
            currentTab.getSections().add(currentSection);
            currentSection = null;
        }
    }

    private void rollUpSubsection() {
        if (currentSection == null) {
            throw new ReorderException("Subsection not included in section");
        }
        if (currentSubsection != null) {
            currentSection.getSubsections().add(currentSubsection);
            currentSubsection = null;
        }
    }

    private void reset() {
        tabs = new ArrayList<>();
        currentTab = null;
        currentSection = null;
        currentSubsection = null;
    }
}
