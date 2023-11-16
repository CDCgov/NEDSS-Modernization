package gov.cdc.nbs.questionbank.page.download;

import gov.cdc.nbs.questionbank.page.PageMetaDataDownloader;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


class PageMetaDataDownloaderTest {
    @Mock
    private JdbcTemplate jdbcTemplate;
    @InjectMocks
    private PageMetaDataDownloader pageMetaDataDownloader;

    public PageMetaDataDownloaderTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Captor
    private ArgumentCaptor<PreparedStatementSetter> setterCaptor;

    @Mock
    private ResultSet resultSet;

    @Test
    void testDownloadPageMetadataByWaTemplateUid() throws IOException {
        Long waTemplateUid = 123L;
        when(pageMetaDataDownloader.findSimpleQuestionByWaTemplateUid(waTemplateUid)).thenReturn(getPageMetadata());
        when(pageMetaDataDownloader.findComprehensiveQuestionByWaTemplateUid(waTemplateUid)).thenReturn(getPageMetadata());
        when(pageMetaDataDownloader.findPageVocabularyByWaTemplateUid(waTemplateUid)).thenReturn(getPageMetadata());
        when(pageMetaDataDownloader.findPageQuestionVocabularyByWaTemplateUid(waTemplateUid)).thenReturn(getPageMetadata());
        ByteArrayInputStream result = pageMetaDataDownloader.downloadPageMetadataByWaTemplateUid(waTemplateUid);
        assertNotNull(result);
    }


    @Test
     void testPrintSheet() {

        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("TestSheet");

        String[] header = {"Name", "Age", "Date"};
        List<Object[]> dataList = Arrays.asList(
                new Object[]{"John Doe", 30, new Date()},
                new Object[]{"Jane Smith", 28, new Date()},
                new Object[]{"Tom Brown", 35, new Date()}
        );
        pageMetaDataDownloader.printSheet(workbook, sheet, header, dataList);

        Row headerRow = sheet.getRow(0);
        assertNotNull("Header row should not be null", headerRow);
        for (int i = 0; i < header.length; i++) {
            Cell cell = headerRow.getCell(i);
            assertNotNull("Header cell should not be null", cell);
            assertEquals("Header value should match", header[i], cell.getStringCellValue());
        }

        int rowIndex = 1;
        for (Object[] data : dataList) {
            Row row = sheet.getRow(rowIndex++);
            assertNotNull("Data row should not be null", row);
            int cellIndex = 0;
            for (Object cellData : data) {
                Cell cell = row.getCell(cellIndex++);
                assertNotNull("Cell should not be null", cell);
                if (cellData instanceof Number) {
                    assertEquals("Numeric data should be aligned to the right",
                            "General", cell.getCellStyle().getDataFormatString());
                }
                if (data[1] instanceof Date) {
                    assertEquals("Date cell should have the specified date format",
                            "yyyy-mm-dd hh:mm:ss.000", cell.getCellStyle().getDataFormatString());
                    assertEquals("Date should be aligned to the right",
                            HorizontalAlignment.RIGHT, cell.getCellStyle().getAlignment());
                }

            }
        }
    }


    List<Object[]> getPageMetadata() {
        List<Object[]> pageMetadata = new ArrayList<>();
        pageMetadata.add(new Object[]{"col1_val", "col2_val", "col3_val", "col4_val", "col5_val", "col6_val", "col7_val"});
        pageMetadata.add(new Object[]{"col1_val", "col2_val", "col3_val", "col4_val", "col5_val", "col6_val", "col7_val"});
        return pageMetadata;
    }

    @Test
    void testFindPageMetadataByWaTemplateUid() throws SQLException {
        Long waTemplateUid = 1L;
        when(jdbcTemplate.query(anyString(), setterCaptor.capture(), any(RowMapper.class))).thenAnswer(invocation -> {
            Object[] row = new Object[]{"value1", "value2", "value3"};
            return List.of(row);
        });
        when(resultSet.getMetaData()).thenReturn(mock(ResultSetMetaData.class));
        when(resultSet.getMetaData().getColumnCount()).thenReturn(3);
        when(resultSet.getObject(1)).thenReturn("value1");
        when(resultSet.getObject(2)).thenReturn("value2");
        when(resultSet.getObject(3)).thenReturn("value3");

        List<Object[]> simpleQuestion = pageMetaDataDownloader.findSimpleQuestionByWaTemplateUid(waTemplateUid);
        assertEquals(3, simpleQuestion.size());
        List<Object[]> comprehensiveQuestion = pageMetaDataDownloader.findComprehensiveQuestionByWaTemplateUid(waTemplateUid);
        assertEquals(3, comprehensiveQuestion.size());
        List<Object[]> pageVocabulary = pageMetaDataDownloader.findPageVocabularyByWaTemplateUid(waTemplateUid);
        assertEquals(3, pageVocabulary.size());
        List<Object[]> pageQuestionVocabulary = pageMetaDataDownloader.findPageQuestionVocabularyByWaTemplateUid(waTemplateUid);
        assertEquals(3, pageQuestionVocabulary.size());
    }


    @Test
    void testDownloadPageMetadataByWaTemplateUid_IOException() throws IOException {
        PageMetaDataDownloader yourClass = new PageMetaDataDownloader(jdbcTemplate);
        Long waTemplateUid = 1L;
        PageMetaDataDownloader mockedClass = spy(yourClass);
        doAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) throws IOException {
                throw new IOException("Error While Finding Simple Question");
            }
        }).when(mockedClass).findSimpleQuestionByWaTemplateUid(waTemplateUid);

        doAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) throws IOException {
                throw new IOException("Error While Finding Comprehensive Question");
            }
        }).when(mockedClass).findComprehensiveQuestionByWaTemplateUid(waTemplateUid);

        doAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) throws IOException {
                throw new IOException("Error While Finding Page Vocabulary");
            }
        }).when(mockedClass).findPageVocabularyByWaTemplateUid(waTemplateUid);

        doAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) throws IOException {
                throw new IOException("Error While Finding Page Question Vocabulary");
            }
        }).when(mockedClass).findPageQuestionVocabularyByWaTemplateUid(waTemplateUid);

        try {
            mockedClass.downloadPageMetadataByWaTemplateUid(waTemplateUid);
        } catch (IOException e) {
            assertEquals(IOException.class, e.getClass());
        }
    }


    @Test
    void testGetRow() throws SQLException {
        ResultSet resultSet = mock(ResultSet.class);
        ResultSetMetaData metaData = mock(ResultSetMetaData.class);
        when(resultSet.getMetaData()).thenReturn(metaData);
        when(metaData.getColumnCount()).thenReturn(3);
        when(resultSet.getObject(1)).thenReturn("value1");
        when(resultSet.getObject(2)).thenReturn("value2");
        when(resultSet.getObject(3)).thenReturn("value3");

        Object[] result = pageMetaDataDownloader.getResultSetRow(resultSet);
        assertEquals(3, result.length);
        assertEquals("value1", result[0]);
        assertEquals("value2", result[1]);
        assertEquals("value3", result[2]);
    }

}




