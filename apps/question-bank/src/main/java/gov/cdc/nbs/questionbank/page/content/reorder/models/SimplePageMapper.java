package gov.cdc.nbs.questionbank.page.content.reorder.models;

import gov.cdc.nbs.questionbank.page.content.reorder.ReorderException;
import gov.cdc.nbs.questionbank.page.content.reorder.models.ReorderablePage.Element;
import gov.cdc.nbs.questionbank.page.content.reorder.models.ReorderablePage.Section;
import gov.cdc.nbs.questionbank.page.content.reorder.models.ReorderablePage.Subsection;
import gov.cdc.nbs.questionbank.page.content.reorder.models.ReorderablePage.Tab;
import java.util.ArrayList;
import java.util.List;

public class SimplePageMapper {

  private List<Tab> tabs = new ArrayList<>();
  private Tab currentTab = null;
  private Section currentSection = null;
  private Subsection currentSubsection = null;

  public ReorderablePage toPage(List<PageEntry> entries) {
    ReorderablePage page = null;
    for (PageEntry e : entries) {
      switch (e.component()) {
        case ReorderablePage.PAGE_TYPE:
          page = new ReorderablePage(e.id());
          break;
        case ReorderablePage.TAB:
          {
            rollUpTab();
            currentTab = new Tab(e.id());
            break;
          }
        case ReorderablePage.SECTION:
          {
            rollUpSection();
            currentSection = new Section(e.id());
            break;
          }
        case ReorderablePage.SUBSECTION:
          {
            rollUpSubsection();
            currentSubsection = new Subsection(e.id());
            break;
          }
        default:
          {
            if (currentSubsection == null) {
              throw new ReorderException("Element not included in subsection");
            }
            currentSubsection.getElements().add(new Element(e.id(), e.component()));
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
      if (currentSubsection != null) {
        currentSection.getSubsections().add(currentSubsection);
        currentSubsection = null;
      }
      if (currentSection != null) {
        rollUpSubsection();
        currentTab.getSections().add(currentSection);
        currentSection = null;
      }
      tabs.add(currentTab);
      currentTab = null;
    }
  }

  private void rollUpSection() {
    if (currentTab == null) {
      throw new ReorderException("Section not included in tab");
    }
    if (currentSection != null) {
      if (currentSubsection != null) {
        currentSection.getSubsections().add(currentSubsection);
        currentSubsection = null;
      }
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
