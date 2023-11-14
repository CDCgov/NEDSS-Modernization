package gov.cdc.nbs.questionbank.page.service;


import gov.cdc.nbs.questionbank.page.model.PageHistory;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class PageHistoryFinderTest {

    @Mock
    JdbcTemplate jdbcTemplate;

    @InjectMocks
    PageHistoryFinder pageHistoryFinder;

    @Captor
    private ArgumentCaptor<PreparedStatementSetter> setterCaptor;


    public PageHistoryFinderTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getPageHistory_validTemplateName_returnListOfPageHistory() {
        List<PageHistory> expectedPageHistory = Arrays.asList(
                new PageHistory("1.0", "09/25/2019", "User1", "Notes 1"),
                new PageHistory("2.0", "09/25/2019", "User2", "Notes 2"));
        when(jdbcTemplate.query(anyString(), (PreparedStatementSetter) any(), any(RowMapper.class))).thenReturn(expectedPageHistory);
        List<PageHistory> actualPageHistory = pageHistoryFinder.getPageHistory(100l);
        assertEquals(expectedPageHistory, actualPageHistory);
    }

    @Test
    void getPageHistory_validTemplateName_queryLabelNames() {
        List<PageHistory> expectedPageHistory = Arrays.asList(
                new PageHistory("1.0", "09/25/2019", "User1", "Notes 1"),
                new PageHistory("2.0", "09/25/2019", "User2", "Notes 2"));
        when(jdbcTemplate.query(anyString(), (PreparedStatementSetter) any(), any(RowMapper.class))).thenReturn(expectedPageHistory);
        List<PageHistory> actualPageHistory = pageHistoryFinder.getPageHistory(100l);
        assertEquals("publishVersionNbr", "1.0", actualPageHistory.get(0).publishVersionNbr());
        assertEquals("lastUpdatedDate", "09/25/2019", actualPageHistory.get(0).lastUpdatedDate());
        assertEquals("lastUpdatedBy", "User1", actualPageHistory.get(0).lastUpdatedBy());
        assertEquals("notes", "Notes 1", actualPageHistory.get(0).notes());
    }

    @Test
    void getPageHistory_invalidTemplateName_returnEmptyList() {
        List<PageHistory> expectedPageHistory = Collections.EMPTY_LIST;
        Mockito.when(jdbcTemplate.query(anyString(), any(Object[].class), any(int[].class), any(RowMapper.class)))
                .thenReturn(expectedPageHistory);
        List<PageHistory> actualPageHistory = pageHistoryFinder.getPageHistory(100l);
        assertEquals(expectedPageHistory, actualPageHistory);
    }

    @Test
    void testGetPageHistoryMapping() throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        when(rs.getString("publishVersionNbr")).thenReturn("1.0");
        when(rs.getString("lastUpdatedDate")).thenReturn("09/25/2019");
        when(rs.getString("lastUpdatedBy")).thenReturn("John Doe");
        when(rs.getString("notes")).thenReturn("Sample note");

        RowMapper<PageHistory> pageHistoryRowMapper = (resultSet, i) -> {
            return new PageHistory(
                    resultSet.getString("publishVersionNbr"),
                    resultSet.getString("lastUpdatedDate"),
                    resultSet.getString("lastUpdatedBy"),
                    resultSet.getString("notes")
            );
        };
        PageHistory pageHistory = pageHistoryRowMapper.mapRow(rs, 1);
        assertEquals("1.0", pageHistory.publishVersionNbr());
        assertEquals("09/25/2019", pageHistory.lastUpdatedDate());
        assertEquals("John Doe", pageHistory.lastUpdatedBy());
        assertEquals("Sample note", pageHistory.notes());
    }


    @Test
    void getPageHistory_runtimeException_returnRuntimeExceptionWithMsg() {
        when(jdbcTemplate.query(anyString(), (PreparedStatementSetter) any(), any(RowMapper.class)))
                .thenThrow(new RuntimeException("Error Fetching Page-History by Template_nm From the Database"));
        var exception = assertThrows(RuntimeException.class, () -> pageHistoryFinder.getPageHistory(100l));
        assertTrue(exception.getMessage().contains("Error Fetching Page-History by Template_nm From the Database"));
    }


    @Test
      void testGetPageHistoryQueryAndMapping() {
        Long pageId = 1L;
        List<PageHistory> expectedPageHistory = Arrays.asList(
                new PageHistory("1.0", "09/25/2019", "User1", "Notes 1"));

        when(jdbcTemplate.query(anyString(), setterCaptor.capture(), any(RowMapper.class)))
                .thenAnswer(invocation -> {
                    PreparedStatementSetter setter = setterCaptor.getValue();
                    PreparedStatement preparedStatement = mock(PreparedStatement.class);
                    setter.setValues(preparedStatement);
                    List<PageHistory> pageHistoryList = new ArrayList<>();
                    ResultSet resultSet = mock(ResultSet.class);
                    when(resultSet.next()).thenReturn(true).thenReturn(false);
                    when(resultSet.getString("publishVersionNbr")).thenReturn("1.0");
                    when(resultSet.getTimestamp("lastUpdatedDate")).thenReturn(Timestamp.valueOf("2019-09-25 13:09:48.427"));
                    when(resultSet.getString("lastUpdatedBy")).thenReturn("User1");
                    when(resultSet.getString("notes")).thenReturn("Notes 1");

                    RowMapper<PageHistory> rowMapper = invocation.getArgument(2);
                    PageHistory pageHistory = rowMapper.mapRow(resultSet, 1);

                    pageHistoryList.add(pageHistory);

                    return pageHistoryList;
                });

        List<PageHistory> actualPageHistoryList = pageHistoryFinder.getPageHistory(pageId);
        assertEquals(expectedPageHistory, actualPageHistoryList);
    }


}
