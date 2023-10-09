package gov.cdc.nbs.questionbank.page.content.reorder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import gov.cdc.nbs.questionbank.page.content.reorder.models.PageEntry;
import gov.cdc.nbs.questionbank.page.content.reorder.models.ReorderRequest;

@ExtendWith(MockitoExtension.class)
class ReordererTest {
    private static final int PAGE_TYPE = 1002;
    private static final int TAB = 1010;
    private static final int SECTION = 1015;
    private static final int SUBSECTION = 1016;

    @Mock
    private JdbcTemplate template;

    @InjectMocks
    private Reorderer reorderer;

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


    @ParameterizedTest
    @CsvSource({
            "50,150", // Place question in empty subsection
            "50,60", // Move question within current subsection
            "50,120", // Move question into another subsection
            "50,80", // Move to new subsection before existing question
            "50,100" // Move to new subsection after existing question
    })
    @SuppressWarnings("unchecked")
    void should_reorder_question(Long id, Long afterId) {
        ArgumentCaptor<Integer> orderCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Long> idCaptor = ArgumentCaptor.forClass(Long.class);
        when(template.update(Mockito.anyString(), orderCaptor.capture(), idCaptor.capture())).thenReturn(1);

        // Given a page with content
        when(template.query(
                Mockito.anyString(),
                Mockito.any(PreparedStatementSetter.class),
                Mockito.any(RowMapper.class)))
                        .thenReturn(pageContent());

        // When a request is processed to reorder 
        ReorderRequest request = new ReorderRequest(id, afterId);
        reorderer.apply(1, request);

        // Then the page is ordered properly
        Map<Long, Integer> orderMap = buildOrderMap(orderCaptor.getAllValues(), idCaptor.getAllValues());

        PageEntry moved = pageContent().stream().filter(r -> r.id() == id).findFirst().get();
        PageEntry after = pageContent().stream().filter(r -> r.id() == afterId).findFirst().get();

        orderMap.forEach((i, newPos) -> {
            PageEntry original = pageContent().stream().filter(r -> r.id() == i).findFirst().get();

            // the question that was moved
            if (i == id) {
                int expectedOrder = moved.orderNumber() > after.orderNumber()
                        ? after.orderNumber() + 1
                        : after.orderNumber();
                assertEquals(expectedOrder, newPos.intValue());
                return;
            }
            // If current is outside of the range affected by move, it should match original 
            int lower = Math.min(moved.orderNumber(), after.orderNumber());
            int upper = Math.max(moved.orderNumber(), after.orderNumber());
            if (original.orderNumber() < lower || original.orderNumber() > upper) {
                assertEquals(original.orderNumber(), newPos.intValue());
            } else {
                // Should be moved "down" one spot as the question was moved from before to after current
                assertEquals(original.orderNumber() - 1, newPos.intValue());
            }
        });
    }

    @ParameterizedTest
    @CsvSource({
            "50,30",  // Cannot place question into section
            "50,130", // Cannot place question in tab
            "50,10"  // Cannot place question outside content
    })
    @SuppressWarnings("unchecked")
    void should_throw_reorder_exception_for_question(Long id, Long afterId) {
        // Given a page with content
        when(template.query(
                Mockito.anyString(),
                Mockito.any(PreparedStatementSetter.class),
                Mockito.any(RowMapper.class)))
                        .thenReturn(pageContent());

        // When a request is processed to reorder 
        ReorderRequest request = new ReorderRequest(id, afterId);
        assertThrows(ReorderException.class, () -> reorderer.apply(1, request));
    }

    @ParameterizedTest
    @CsvSource({
            "40,70,6",
    })
    @SuppressWarnings("unchecked")
    void should_reorder_subsection(Long id, Long afterId, Integer expectedPosition) {
        ArgumentCaptor<Integer> orderCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Long> idCaptor = ArgumentCaptor.forClass(Long.class);
        when(template.update(Mockito.anyString(), orderCaptor.capture(), idCaptor.capture())).thenReturn(1);

        // Given a page with content
        when(template.query(
                Mockito.anyString(),
                Mockito.any(PreparedStatementSetter.class),
                Mockito.any(RowMapper.class)))
                        .thenReturn(pageContent());

        // When a request is processed to reorder 
        ReorderRequest request = new ReorderRequest(id, afterId);
        reorderer.apply(1, request);

        // Then the subsection should be reordered
        Map<Long, Integer> orderMap = buildOrderMap(orderCaptor.getAllValues(), idCaptor.getAllValues());
        assertEquals(expectedPosition, orderMap.get(id));

    }


    private Map<Long, Integer> buildOrderMap(List<Integer> orders, List<Long> ids) {
        Map<Long, Integer> orderMap = new HashMap<>();
        for (int i = 0; i < orders.size(); i++) {
            orderMap.put(ids.get(i), orders.get(i));
        }
        return orderMap;
    }
}
