package gov.cdc.nbs.questionbank.page.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import gov.cdc.nbs.questionbank.page.model.PageHistory;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;

class PageHistoryFinderTest {

  @Mock JdbcTemplate jdbcTemplate;

  @InjectMocks PageHistoryFinder pageHistoryFinder;

  @Captor private ArgumentCaptor<PreparedStatementSetter> setterCaptor;

  public PageHistoryFinderTest() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @SuppressWarnings("unchecked")
  void getPageHistory_validTemplateName_returnListOfPageHistory() {
    Pageable pageable = getPageable();
    List<PageHistory> expectedPageHistory =
        Arrays.asList(
            new PageHistory("1.0", "09/25/2019", "User1", "Notes 1"),
            new PageHistory("2.0", "09/25/2019", "User2", "Notes 2"));
    when(jdbcTemplate.query(anyString(), (PreparedStatementSetter) any(), any(RowMapper.class)))
        .thenReturn(expectedPageHistory);
    when(jdbcTemplate.query(
            any(String.class),
            any(PreparedStatementSetter.class),
            any(SingleColumnRowMapper.class)))
        .thenReturn(Collections.singletonList(10));

    Page<PageHistory> actualPageHistory = pageHistoryFinder.getPageHistory(100l, pageable);
    assertEquals(expectedPageHistory, actualPageHistory.toList());
  }

  @Test
  @SuppressWarnings("unchecked")
  void getPageHistory_validTemplateName_queryLabelNames() {
    Pageable pageable = getPageable();
    List<PageHistory> expectedPageHistory =
        Arrays.asList(
            new PageHistory("1.0", "09/25/2019", "User1", "Notes 1"),
            new PageHistory("2.0", "09/25/2019", "User2", "Notes 2"));
    when(jdbcTemplate.query(anyString(), (PreparedStatementSetter) any(), any(RowMapper.class)))
        .thenReturn(expectedPageHistory);
    when(jdbcTemplate.query(
            any(String.class),
            any(PreparedStatementSetter.class),
            any(SingleColumnRowMapper.class)))
        .thenReturn(Collections.singletonList(10));

    Page<PageHistory> actualPageHistory = pageHistoryFinder.getPageHistory(100l, pageable);
    PageHistory pageHistory = actualPageHistory.stream().toList().get(0);
    assertEquals("1.0", pageHistory.publishVersionNbr(), "publishVersionNbr");
    assertEquals("09/25/2019", pageHistory.lastUpdatedDate(), "lastUpdatedDate");
    assertEquals("User1", pageHistory.lastUpdatedBy(), "lastUpdatedBy");
    assertEquals("Notes 1", pageHistory.notes(), "notes");
  }

  @Test
  @SuppressWarnings("unchecked")
  void getPageHistory_invalidTemplateName_returnEmptyList() {
    Pageable pageable = getPageable();
    List<PageHistory> expectedPageHistory = Collections.EMPTY_LIST;
    Mockito.when(
            jdbcTemplate.query(
                anyString(), any(Object[].class), any(int[].class), any(RowMapper.class)))
        .thenReturn(expectedPageHistory);
    when(jdbcTemplate.query(
            any(String.class),
            any(PreparedStatementSetter.class),
            any(SingleColumnRowMapper.class)))
        .thenReturn(Collections.singletonList(10));

    Page<PageHistory> actualPageHistory = pageHistoryFinder.getPageHistory(100l, pageable);
    assertTrue(actualPageHistory.toList().isEmpty());
  }

  @Test
  void testGetPageHistoryMapping() throws SQLException {
    ResultSet rs = mock(ResultSet.class);
    when(rs.getString("publishVersionNbr")).thenReturn("1.0");
    when(rs.getString("lastUpdatedDate")).thenReturn("09/25/2019");
    when(rs.getString("lastUpdatedBy")).thenReturn("John Doe");
    when(rs.getString("notes")).thenReturn("Sample note");

    RowMapper<PageHistory> pageHistoryRowMapper =
        (resultSet, i) -> {
          return new PageHistory(
              resultSet.getString("publishVersionNbr"),
              resultSet.getString("lastUpdatedDate"),
              resultSet.getString("lastUpdatedBy"),
              resultSet.getString("notes"));
        };
    PageHistory pageHistory = pageHistoryRowMapper.mapRow(rs, 1);
    assertEquals("1.0", pageHistory.publishVersionNbr());
    assertEquals("09/25/2019", pageHistory.lastUpdatedDate());
    assertEquals("John Doe", pageHistory.lastUpdatedBy());
    assertEquals("Sample note", pageHistory.notes());
  }

  @Test
  @SuppressWarnings("unchecked")
  void getPageHistory_runtimeException_returnRuntimeExceptionWithMsg() {
    Pageable pageable = getPageable();
    when(jdbcTemplate.query(anyString(), (PreparedStatementSetter) any(), any(RowMapper.class)))
        .thenThrow(
            new RuntimeException("Error Fetching Page-History by Template_nm From the Database"));
    var exception =
        assertThrows(
            RuntimeException.class, () -> pageHistoryFinder.getPageHistory(100l, pageable));
    assertTrue(
        exception
            .getMessage()
            .contains("Error Fetching Page-History by Template_nm From the Database"));
  }

  @Test
  @SuppressWarnings("unchecked")
  void testGetPageHistoryQueryAndMapping() {
    Pageable pageable = getPageable();
    Long pageId = 1L;
    List<PageHistory> resultList =
        Arrays.asList(new PageHistory("1.0", "09/25/2019", "User1", "Notes 1"));
    Page<PageHistory> expectedPageHistory = new PageImpl<>(resultList, pageable, 10);
    when(jdbcTemplate.query(anyString(), setterCaptor.capture(), any(RowMapper.class)))
        .thenAnswer(
            invocation -> {
              PreparedStatementSetter setter = setterCaptor.getValue();
              PreparedStatement preparedStatement = mock(PreparedStatement.class);
              setter.setValues(preparedStatement);
              List<PageHistory> pageHistoryList = new ArrayList<>();
              ResultSet resultSet = mock(ResultSet.class);
              when(resultSet.next()).thenReturn(true).thenReturn(false);
              when(resultSet.getString("publishVersionNbr")).thenReturn("1.0");
              when(resultSet.getString("lastUpdatedDate")).thenReturn("09/25/2019");
              when(resultSet.getString("lastUpdatedBy")).thenReturn("User1");
              when(resultSet.getString("notes")).thenReturn("Notes 1");
              RowMapper<PageHistory> rowMapper = invocation.getArgument(2);
              PageHistory pageHistory = rowMapper.mapRow(resultSet, 1);
              pageHistoryList.add(pageHistory);
              return pageHistoryList;
            });
    when(jdbcTemplate.query(
            any(String.class), setterCaptor.capture(), any(SingleColumnRowMapper.class)))
        .thenAnswer(
            invocation -> {
              PreparedStatementSetter setter = setterCaptor.getValue();
              PreparedStatement preparedStatement = mock(PreparedStatement.class);
              setter.setValues(preparedStatement);
              return Collections.singletonList(10);
            });
    Page<PageHistory> actualPageHistoryList = pageHistoryFinder.getPageHistory(pageId, pageable);
    assertEquals(expectedPageHistory, actualPageHistoryList);
  }

  private Pageable getPageable() {
    Pageable pageable = mock(Pageable.class);
    Sort sort = mock(Sort.class);
    when(sort.isSorted()).thenReturn(true);
    when(sort.toString()).thenReturn("searchField");
    when(pageable.getSort()).thenReturn(sort);
    return pageable;
  }
}
