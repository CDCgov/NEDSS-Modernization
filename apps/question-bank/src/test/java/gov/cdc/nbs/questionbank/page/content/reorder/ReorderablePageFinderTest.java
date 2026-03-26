package gov.cdc.nbs.questionbank.page.content.reorder;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import gov.cdc.nbs.questionbank.page.content.reorder.models.PageEntry;
import gov.cdc.nbs.questionbank.page.content.reorder.models.ReorderablePage;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

@ExtendWith(MockitoExtension.class)
class ReorderablePageFinderTest {

  @Mock private JdbcTemplate template;

  @InjectMocks private ReorderablePageFinder finder;

  @Test
  @SuppressWarnings("unchecked")
  void should_find_page() {
    // given a page exists
    when(template.query(
            Mockito.anyString(),
            Mockito.any(PreparedStatementSetter.class),
            Mockito.any(RowMapper.class)))
        .thenReturn(Arrays.asList(new PageEntry(10, 1002, 1), new PageEntry(20, 1010, 2)));

    // when a request to find a page is made
    ReorderablePage page = finder.find(1l);

    // then a page should be returned
    assertNotNull(page);
  }

  @Test
  @SuppressWarnings("unchecked")
  void should_not_find_page() {
    // given a page does not exist
    when(template.query(
            Mockito.anyString(),
            Mockito.any(PreparedStatementSetter.class),
            Mockito.any(RowMapper.class)))
        .thenReturn(new ArrayList<>());

    // when a request is made then an exception should be thrown
    assertThrows(ReorderException.class, () -> finder.find(1l));
  }
}
