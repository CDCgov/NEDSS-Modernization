package gov.cdc.nbs.questionbank.page.download;


import gov.cdc.nbs.questionbank.page.PageMetaDataDownloader;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import static org.junit.Assert.*;
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
    void downloadPageMetadata() throws IOException {
        Long waTemplateUid = 100l;
        when(pageMetaDataDownloader.findPageMetadataByWaTemplateUid(waTemplateUid)).thenReturn(getPageMetadata());
        ByteArrayInputStream response = pageMetaDataDownloader.downloadPageMetadataByWaTemplateUid(waTemplateUid);
        byte[] content = response.readAllBytes();
        assertNotNull(content);
        response.read(content, 0, content.length);
        String output = new String(content, StandardCharsets.UTF_8);
        assertNotNull(output);
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
        List<Object[]> result = pageMetaDataDownloader.findPageMetadataByWaTemplateUid(waTemplateUid);
        assertEquals(3, result.size());
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


    @Test
     void testDownloadPageMetadataByWaTemplateUid_IOException() throws IOException {
        PageMetaDataDownloader yourClass = new PageMetaDataDownloader(jdbcTemplate);
        Long waTemplateUid = 1L;
        PageMetaDataDownloader mockedClass = spy(yourClass);
        doAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) throws IOException {
                throw new IOException("Mocked IOException");
            }
        }).when(mockedClass).findPageMetadataByWaTemplateUid(waTemplateUid);
        try {
            mockedClass.downloadPageMetadataByWaTemplateUid(waTemplateUid);
        } catch (IOException e) {
            assertEquals(IOException.class, e.getClass());
        }
    }
}




