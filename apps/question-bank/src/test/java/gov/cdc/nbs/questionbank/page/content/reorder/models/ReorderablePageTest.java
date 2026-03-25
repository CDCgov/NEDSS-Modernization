package gov.cdc.nbs.questionbank.page.content.reorder.models;

import static org.junit.jupiter.api.Assertions.*;

import gov.cdc.nbs.questionbank.page.content.reorder.ReorderException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ReorderablePageTest {

  private static final int PAGE_TYPE = 1002;
  private static final int TAB = 1010;
  private static final int SECTION = 1015;
  private static final int SUBSECTION = 1016;

  private List<PageEntry> pageEntries() {
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

  private ReorderablePage testPage() {
    SimplePageMapper mapper = new SimplePageMapper();
    return mapper.toPage(pageEntries());
  }

  @ParameterizedTest
  @CsvSource({
    "50,150,15", // Place question in empty subsection
    "50,60,6", // Move question within current subsection
    "50,120,12", // Move question into another subsection
    "50,80,8", // Move to new subsection before existing question
    "50,100,10", // Move to new subsection after existing question
    "120,40,5" // Move "up" in order
  })
  void should_reorder_element(Long id, Long afterId, int expectedOrder) {
    // Given a reorderable page
    ReorderablePage page = testPage();

    // When an element is moved
    page.move(id, afterId);

    // Then the entry is ordered properly
    PageEntry entry =
        page.toPageEntries().stream().filter(e -> e.id() == id).findFirst().orElseThrow();
    assertEquals(expectedOrder, entry.orderNumber());
  }

  @ParameterizedTest
  @CsvSource({
    "50,30", // Cannot place question into section
    "50,130", // Cannot place question in tab
    "50,10", // Cannot place question at root
    "160, 100", // cannot insert section after subsection
    "160, 110", // cannot insert section after question
    "160, 10", // cannot insert section at root
    "70, 20", // cannot insert subsection into tab
    "70, 110", // cannot insert subsection after question
    "70, 10", // cannot insert subsection at root
    "20, 30", // cannot insert tab into section
    "20, 40", // cannot insert tab into subsection
    "20, 50", // cannot insert tab after question
  })
  void should_throw_reorder_exception(Long id, Long afterId) {
    // Given a reorderable page
    ReorderablePage page = testPage();

    // When attempting to move an element into an incorrect position
    // Then an exception is thrown
    assertThrows(ReorderException.class, () -> page.move(id, afterId));
  }

  @ParameterizedTest
  @CsvSource({
    "40,70,6", // can insert subsection after non empty subsection
    "150,90,10", // can insert subsection after section
    "40,140,12", // can insert subsection into empty section
    "40,70,6", // can insert section after non empty subsection
    "150,90,10", // can insert section after section
    "40,140,12", // can insert section into empty section
    "20, 130, 6", // can insert tab after another tab
    "130,10, 2" // can insert tab at root
  })
  void should_reorder(Long id, Long afterId, Integer expectedPosition) {
    // Given a reorderable page
    ReorderablePage page = testPage();

    // When a request is processed to reorder
    page.move(id, afterId);

    // Then entry should be reordered
    PageEntry movedEntry =
        page.toPageEntries().stream().filter(e -> e.id() == id).findFirst().orElseThrow();
    assertEquals(expectedPosition, movedEntry.orderNumber());
  }

  @Test
  void shoud_move_tab_to_end() {
    // Given a reorderable page
    ReorderablePage page = testPage();

    // When a component is moved
    page.move(20, 130);

    // Then all contents are ordered as expected
    List<PageEntry> expectedList = new ArrayList<>();
    expectedList.add(new PageEntry(10, PAGE_TYPE, 1));
    expectedList.add(new PageEntry(130, TAB, 2));
    expectedList.add(new PageEntry(140, SECTION, 3));
    expectedList.add(new PageEntry(150, SUBSECTION, 4));
    expectedList.add(new PageEntry(160, SECTION, 5));
    expectedList.add(new PageEntry(20, TAB, 6));
    expectedList.add(new PageEntry(30, SECTION, 7));
    expectedList.add(new PageEntry(40, SUBSECTION, 8));
    expectedList.add(new PageEntry(50, 1008, 9));
    expectedList.add(new PageEntry(60, 1009, 10));
    expectedList.add(new PageEntry(70, SUBSECTION, 11));
    expectedList.add(new PageEntry(80, 1008, 12));
    expectedList.add(new PageEntry(90, SECTION, 13));
    expectedList.add(new PageEntry(100, SUBSECTION, 14));
    expectedList.add(new PageEntry(110, 1008, 15));
    expectedList.add(new PageEntry(120, 1009, 16));

    assertArrayEquals(expectedList.toArray(), page.toPageEntries().toArray());
  }

  @Test
  void should_move_tab_to_beginning() {
    // Given a reorderable page
    ReorderablePage page = testPage();

    // When a component is moved
    page.move(130, 10);

    List<PageEntry> expectedList = new ArrayList<>();
    expectedList.add(new PageEntry(10, PAGE_TYPE, 1));
    expectedList.add(new PageEntry(130, TAB, 2));
    expectedList.add(new PageEntry(140, SECTION, 3));
    expectedList.add(new PageEntry(150, SUBSECTION, 4));
    expectedList.add(new PageEntry(160, SECTION, 5));
    expectedList.add(new PageEntry(20, TAB, 6));
    expectedList.add(new PageEntry(30, SECTION, 7));
    expectedList.add(new PageEntry(40, SUBSECTION, 8));
    expectedList.add(new PageEntry(50, 1008, 9));
    expectedList.add(new PageEntry(60, 1009, 10));
    expectedList.add(new PageEntry(70, SUBSECTION, 11));
    expectedList.add(new PageEntry(80, 1008, 12));
    expectedList.add(new PageEntry(90, SECTION, 13));
    expectedList.add(new PageEntry(100, SUBSECTION, 14));
    expectedList.add(new PageEntry(110, 1008, 15));
    expectedList.add(new PageEntry(120, 1009, 16));

    assertArrayEquals(expectedList.toArray(), page.toPageEntries().toArray());
  }

  @Test
  void should_move_section_to_another_tab() {
    // Given a reorderable page
    ReorderablePage page = testPage();

    // When a component is moved
    page.move(140, 20);

    // Then all contents are ordered as expected
    List<PageEntry> expectedList = new ArrayList<>();
    expectedList.add(new PageEntry(10, PAGE_TYPE, 1));
    expectedList.add(new PageEntry(20, TAB, 2));
    expectedList.add(new PageEntry(140, SECTION, 3));
    expectedList.add(new PageEntry(150, SUBSECTION, 4));
    expectedList.add(new PageEntry(30, SECTION, 5));
    expectedList.add(new PageEntry(40, SUBSECTION, 6));
    expectedList.add(new PageEntry(50, 1008, 7));
    expectedList.add(new PageEntry(60, 1009, 8));
    expectedList.add(new PageEntry(70, SUBSECTION, 9));
    expectedList.add(new PageEntry(80, 1008, 10));
    expectedList.add(new PageEntry(90, SECTION, 11));
    expectedList.add(new PageEntry(100, SUBSECTION, 12));
    expectedList.add(new PageEntry(110, 1008, 13));
    expectedList.add(new PageEntry(120, 1009, 14));
    expectedList.add(new PageEntry(130, TAB, 15));
    expectedList.add(new PageEntry(160, SECTION, 16));

    assertArrayEquals(expectedList.toArray(), page.toPageEntries().toArray());
  }

  @Test
  void should_move_section_to_another_section() {
    // Given a reorderable page
    ReorderablePage page = testPage();

    // When a component is moved
    page.move(30, 90);

    // Then all contents are ordered as expected
    List<PageEntry> expectedList = new ArrayList<>();
    expectedList.add(new PageEntry(10, PAGE_TYPE, 1));
    expectedList.add(new PageEntry(20, TAB, 2));
    expectedList.add(new PageEntry(90, SECTION, 3));
    expectedList.add(new PageEntry(100, SUBSECTION, 4));
    expectedList.add(new PageEntry(110, 1008, 5));
    expectedList.add(new PageEntry(120, 1009, 6));
    expectedList.add(new PageEntry(30, SECTION, 7));
    expectedList.add(new PageEntry(40, SUBSECTION, 8));
    expectedList.add(new PageEntry(50, 1008, 9));
    expectedList.add(new PageEntry(60, 1009, 10));
    expectedList.add(new PageEntry(70, SUBSECTION, 11));
    expectedList.add(new PageEntry(80, 1008, 12));
    expectedList.add(new PageEntry(130, TAB, 13));
    expectedList.add(new PageEntry(140, SECTION, 14));
    expectedList.add(new PageEntry(150, SUBSECTION, 15));
    expectedList.add(new PageEntry(160, SECTION, 16));

    assertArrayEquals(expectedList.toArray(), page.toPageEntries().toArray());
  }

  @Test
  void should_move_subsection_to_another_section() {
    // Given a reorderable page
    ReorderablePage page = testPage();

    // When a component is moved
    page.move(150, 100);

    // Then all contents are ordered as expected
    List<PageEntry> expectedList = new ArrayList<>();
    expectedList.add(new PageEntry(10, PAGE_TYPE, 1));
    expectedList.add(new PageEntry(20, TAB, 2));
    expectedList.add(new PageEntry(30, SECTION, 3));
    expectedList.add(new PageEntry(40, SUBSECTION, 4));
    expectedList.add(new PageEntry(50, 1008, 5));
    expectedList.add(new PageEntry(60, 1009, 6));
    expectedList.add(new PageEntry(70, SUBSECTION, 7));
    expectedList.add(new PageEntry(80, 1008, 8));
    expectedList.add(new PageEntry(90, SECTION, 9));
    expectedList.add(new PageEntry(100, SUBSECTION, 10));
    expectedList.add(new PageEntry(110, 1008, 11));
    expectedList.add(new PageEntry(120, 1009, 12));
    expectedList.add(new PageEntry(150, SUBSECTION, 13));
    expectedList.add(new PageEntry(130, TAB, 14));
    expectedList.add(new PageEntry(140, SECTION, 15));
    expectedList.add(new PageEntry(160, SECTION, 16));

    assertArrayEquals(expectedList.toArray(), page.toPageEntries().toArray());
  }

  @Test
  void should_move_subsection_to_after_subsection() {
    // Given a reorderable page
    ReorderablePage page = testPage();

    // When a component is moved
    page.move(40, 70);

    // Then all contents are ordered as expected
    List<PageEntry> expectedList = new ArrayList<>();
    expectedList.add(new PageEntry(10, PAGE_TYPE, 1));
    expectedList.add(new PageEntry(20, TAB, 2));
    expectedList.add(new PageEntry(30, SECTION, 3));
    expectedList.add(new PageEntry(70, SUBSECTION, 4));
    expectedList.add(new PageEntry(80, 1008, 5));
    expectedList.add(new PageEntry(40, SUBSECTION, 6));
    expectedList.add(new PageEntry(50, 1008, 7));
    expectedList.add(new PageEntry(60, 1009, 8));
    expectedList.add(new PageEntry(90, SECTION, 9));
    expectedList.add(new PageEntry(100, SUBSECTION, 10));
    expectedList.add(new PageEntry(110, 1008, 11));
    expectedList.add(new PageEntry(120, 1009, 12));
    expectedList.add(new PageEntry(130, TAB, 13));
    expectedList.add(new PageEntry(140, SECTION, 14));
    expectedList.add(new PageEntry(150, SUBSECTION, 15));
    expectedList.add(new PageEntry(160, SECTION, 16));

    assertArrayEquals(expectedList.toArray(), page.toPageEntries().toArray());
  }

  @Test
  void should_move_question_to_another_subsection() {
    // Given a reorderable page
    ReorderablePage page = testPage();

    // When a component is moved
    page.move(120, 50);

    // Then all contents are ordered as expected
    List<PageEntry> expectedList = new ArrayList<>();
    expectedList.add(new PageEntry(10, PAGE_TYPE, 1));
    expectedList.add(new PageEntry(20, TAB, 2));
    expectedList.add(new PageEntry(30, SECTION, 3));
    expectedList.add(new PageEntry(40, SUBSECTION, 4));
    expectedList.add(new PageEntry(50, 1008, 5));
    expectedList.add(new PageEntry(120, 1009, 6));
    expectedList.add(new PageEntry(60, 1009, 7));
    expectedList.add(new PageEntry(70, SUBSECTION, 8));
    expectedList.add(new PageEntry(80, 1008, 9));
    expectedList.add(new PageEntry(90, SECTION, 10));
    expectedList.add(new PageEntry(100, SUBSECTION, 11));
    expectedList.add(new PageEntry(110, 1008, 12));
    expectedList.add(new PageEntry(130, TAB, 13));
    expectedList.add(new PageEntry(140, SECTION, 14));
    expectedList.add(new PageEntry(150, SUBSECTION, 15));
    expectedList.add(new PageEntry(160, SECTION, 16));

    assertArrayEquals(expectedList.toArray(), page.toPageEntries().toArray());
  }
}
