package gov.cdc.nbs.questionbank.page.content.reorder.models;

import static org.junit.jupiter.api.Assertions.*;

import gov.cdc.nbs.questionbank.page.content.reorder.ReorderException;
import gov.cdc.nbs.questionbank.page.content.reorder.models.ReorderablePage.Element;
import gov.cdc.nbs.questionbank.page.content.reorder.models.ReorderablePage.Section;
import gov.cdc.nbs.questionbank.page.content.reorder.models.ReorderablePage.Subsection;
import gov.cdc.nbs.questionbank.page.content.reorder.models.ReorderablePage.Tab;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class SimplePageMapperTest {
  private static final int PAGE_TYPE = 1002;
  private static final int TAB = 1010;
  private static final int SECTION = 1015;
  private static final int SUBSECTION = 1016;

  private List<PageEntry> pageContent() {
    List<PageEntry> list = new ArrayList<>();
    list.add(new PageEntry(10, PAGE_TYPE, 1));
    list.add(new PageEntry(20, TAB, 2));
    list.add(new PageEntry(30, SECTION, 3));
    list.add(new PageEntry(40, SUBSECTION, 4));
    list.add(new PageEntry(50, 1008, 5));
    list.add(new PageEntry(60, 1009, 6));
    list.add(new PageEntry(70, SUBSECTION, 7));
    list.add(new PageEntry(80, 1008, 8));
    list.add(new PageEntry(90, SECTION, 9));
    list.add(new PageEntry(100, SUBSECTION, 10));
    list.add(new PageEntry(110, 1008, 11));
    list.add(new PageEntry(120, 1009, 12));
    list.add(new PageEntry(130, TAB, 13));
    list.add(new PageEntry(140, SECTION, 14));
    list.add(new PageEntry(150, SUBSECTION, 15));
    list.add(new PageEntry(160, SECTION, 16));
    return list;
  }

  @Test
  void should_map_page() {
    SimplePageMapper mapper = new SimplePageMapper();
    ReorderablePage page = mapper.toPage(pageContent());
    assertNotNull(page);

    Tab firstTab = page.getTabs().get(0);
    Section firstSection = firstTab.getSections().get(0);
    Subsection firstSubsection = firstSection.getSubsections().get(0);
    Element firstElement = firstSubsection.getElements().get(0);
    Element secondElement = firstSubsection.getElements().get(1);
    Subsection secondSubsection = firstSection.getSubsections().get(1);
    Element thirdElement = secondSubsection.getElements().get(0);
    Section secondSection = firstTab.getSections().get(1);
    Subsection thirdSubsection = secondSection.getSubsections().get(0);
    Element fourthElement = thirdSubsection.getElements().get(0);
    Element fifthElement = thirdSubsection.getElements().get(1);
    Tab secondTab = page.getTabs().get(1);
    Section thirdSection = secondTab.getSections().get(0);
    Subsection fourthSubsection = thirdSection.getSubsections().get(0);
    Section fourthSection = secondTab.getSections().get(1);

    assertEquals(20, firstTab.getId());
    assertEquals(30, firstSection.getId());
    assertEquals(40, firstSubsection.getId());
    assertEquals(50, firstElement.getId());
    assertEquals(60, secondElement.getId());
    assertEquals(70, secondSubsection.getId());
    assertEquals(80, thirdElement.getId());
    assertEquals(90, secondSection.getId());
    assertEquals(100, thirdSubsection.getId());
    assertEquals(110, fourthElement.getId());
    assertEquals(120, fifthElement.getId());
    assertEquals(130, secondTab.getId());
    assertEquals(140, thirdSection.getId());
    assertEquals(150, fourthSubsection.getId());
    assertEquals(160, fourthSection.getId());
  }

  @Test
  void should_fail_due_to_missing_type() {
    SimplePageMapper mapper = new SimplePageMapper();
    // Missing page_type entry
    List<PageEntry> content = new ArrayList<>();
    content.add(new PageEntry(20, TAB, 2));
    content.add(new PageEntry(30, SECTION, 3));

    assertThrows(ReorderException.class, () -> mapper.toPage(content));
  }

  @Test
  void should_fail_due_to_missing_tab() {
    SimplePageMapper mapper = new SimplePageMapper();
    List<PageEntry> content = new ArrayList<>();
    content.add(new PageEntry(10, PAGE_TYPE, 1));
    content.add(new PageEntry(30, SECTION, 3));
    content.add(new PageEntry(40, SUBSECTION, 4));
    content.add(new PageEntry(50, 1008, 5));

    assertThrows(ReorderException.class, () -> mapper.toPage(content));
  }

  @Test
  void should_fail_due_to_missing_section() {
    SimplePageMapper mapper = new SimplePageMapper();
    List<PageEntry> content = new ArrayList<>();
    content.add(new PageEntry(10, PAGE_TYPE, 1));
    content.add(new PageEntry(20, TAB, 2));
    content.add(new PageEntry(40, SUBSECTION, 4));
    content.add(new PageEntry(50, 1008, 5));

    assertThrows(ReorderException.class, () -> mapper.toPage(content));
  }

  @Test
  void should_fail_due_to_missing_subsection() {
    SimplePageMapper mapper = new SimplePageMapper();
    List<PageEntry> content = new ArrayList<>();
    content.add(new PageEntry(10, PAGE_TYPE, 1));
    content.add(new PageEntry(20, TAB, 2));
    content.add(new PageEntry(30, SECTION, 3));
    content.add(new PageEntry(50, 1008, 5));

    assertThrows(ReorderException.class, () -> mapper.toPage(content));
  }

  @Test
  void should_fail_due_to_element_in_section() {
    SimplePageMapper mapper = new SimplePageMapper();
    List<PageEntry> content = new ArrayList<>();
    content.add(new PageEntry(10, PAGE_TYPE, 1));
    content.add(new PageEntry(20, TAB, 2));
    content.add(new PageEntry(30, SECTION, 3));
    content.add(new PageEntry(50, 1008, 4));
    content.add(new PageEntry(40, SUBSECTION, 5));

    assertThrows(ReorderException.class, () -> mapper.toPage(content));
  }

  @Test
  void should_fail_due_to_element_in_tab() {
    SimplePageMapper mapper = new SimplePageMapper();
    List<PageEntry> content = new ArrayList<>();
    content.add(new PageEntry(10, PAGE_TYPE, 1));
    content.add(new PageEntry(20, TAB, 2));
    content.add(new PageEntry(50, 1008, 3));
    content.add(new PageEntry(30, SECTION, 4));
    content.add(new PageEntry(40, SUBSECTION, 5));

    assertThrows(ReorderException.class, () -> mapper.toPage(content));
  }
}
