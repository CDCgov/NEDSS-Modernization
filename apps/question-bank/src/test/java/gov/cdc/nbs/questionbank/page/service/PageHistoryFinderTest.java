package gov.cdc.nbs.questionbank.page.service;


import gov.cdc.nbs.questionbank.page.model.PageHistory;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;


class PageHistoryFinderTest {

    @Mock
    JdbcTemplate jdbcTemplate;

    @InjectMocks
    PageHistoryFinder pageHistoryFinder;

    public PageHistoryFinderTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getPageHistory_validTemplateName_returnListOfPageHistory() {
        List<PageHistory> expectedPageHistory = Arrays.asList(
                new PageHistory("1.0", "09/25/2019", "User1", "Notes 1"),
                new PageHistory("2.0", "09/25/2019", "User2", "Notes 2"));
        Mockito.when(jdbcTemplate.query(anyString(), any(Object[].class), any(int[].class), any(RowMapper.class)))
                .thenReturn(expectedPageHistory);
        List<PageHistory> actualPageHistory = pageHistoryFinder.getPageHistory("validTemplateName");
        assertEquals(expectedPageHistory, actualPageHistory);
    }

    @Test
    void getPageHistory_validTemplateName_queryLabelNames() {
        List<PageHistory> expectedPageHistory = Arrays.asList(
                new PageHistory("1.0", "09/25/2019", "User1", "Notes 1"),
                new PageHistory("2.0", "09/25/2019", "User2", "Notes 2"));
        Mockito.when(jdbcTemplate.query(anyString(), any(Object[].class), any(int[].class), any(RowMapper.class)))
                .thenReturn(expectedPageHistory);
        List<PageHistory> actualPageHistory = pageHistoryFinder.getPageHistory("validTemplateName");
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
        List<PageHistory> actualPageHistory = pageHistoryFinder.getPageHistory("invalidTemplateName");
        assertEquals(expectedPageHistory, actualPageHistory);
    }

    @Test
    void getPageHistory_runtimeException_returnRuntimeExceptionWithMsg() {
        when(jdbcTemplate.query(anyString(), any(Object[].class), any(int[].class), any(RowMapper.class)))
                .thenThrow(new RuntimeException("Error Fetching Page-History by Template_nm From the Database"));
        var exception = assertThrows(RuntimeException.class, () -> pageHistoryFinder.getPageHistory("TemplateName"));
        assertTrue(exception.getMessage().contains("Error Fetching Page-History by Template_nm From the Database"));
    }

}
