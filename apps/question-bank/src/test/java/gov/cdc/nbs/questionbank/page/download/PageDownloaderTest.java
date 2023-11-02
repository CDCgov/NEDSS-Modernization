package gov.cdc.nbs.questionbank.page.download;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import gov.cdc.nbs.questionbank.entity.repository.WaUiMetadataRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import com.itextpdf.text.DocumentException;
import gov.cdc.nbs.questionbank.condition.repository.ConditionCodeRepository;
import gov.cdc.nbs.questionbank.entity.PageCondMapping;
import gov.cdc.nbs.questionbank.entity.UserProfile;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.condition.ConditionCode;
import gov.cdc.nbs.questionbank.entity.repository.PageCondMappingRepository;
import gov.cdc.nbs.questionbank.entity.repository.UserProfileRepository;
import gov.cdc.nbs.questionbank.entity.repository.WaTemplateRepository;
import gov.cdc.nbs.questionbank.exception.QueryException;
import gov.cdc.nbs.questionbank.page.PageDownloader;

class PageDownloaderTest {

    @Mock
    private WaTemplateRepository templateRepository;

    @Mock
    private PageCondMappingRepository pageConMappingRepository;

    @Mock
    private ConditionCodeRepository conditionCodeRepository;

    @Mock
    private UserProfileRepository userProfileRepository;

    @Mock
    WaUiMetadataRepository waUiMetadataRepository;

    @InjectMocks
    private PageDownloader pageDownloader;

    public PageDownloaderTest() {
        MockitoAnnotations.openMocks(this);
    }

    Instant now = Instant.now();

    @Test
    void downloadLibrary() throws IOException {
        when(templateRepository.getAllPagesOrderedByName()).thenReturn(List.of(getTemplate(1l)));
        when(pageConMappingRepository.findByWaTemplateUidIn(Mockito.any())).thenReturn(List.of(getMapping()));
        when(conditionCodeRepository.findByIdIn(Mockito.anyList())).thenReturn(List.of(conditionCode()));
        ByteArrayInputStream response = pageDownloader.downloadLibrary();
        byte[] content = response.readAllBytes();
        assertNotNull(content);
        response.read(content, 0, content.length);
        String output = new String(content, StandardCharsets.UTF_8);
        assertNotNull(output);

    }

    @Test
    void downloadLibraryException() {
        when(templateRepository.getAllPagesOrderedByName()).thenThrow(new QueryException("Error downloading Page Library"));
        var exception = assertThrows(RuntimeException.class, () -> pageDownloader.downloadLibrary());
        assertTrue(exception.getMessage().contains("Error downloading Page Library"));

    }


    @Test
    void formatttedRelatedConditions() {
        ConditionCode original = conditionCode();
        when(pageConMappingRepository.findByWaTemplateUidIn(Mockito.any())).thenReturn(List.of(getMapping()));
        when(conditionCodeRepository.findByIdIn(Mockito.anyList())).thenReturn(List.of(original));
        String formattedCondition = pageDownloader.formatttedRelatedConditions(List.of(original));
        assertNotNull(formattedCondition);
        assertEquals(original.getConditionDescTxt() + "(" + original.getId() + ")", formattedCondition);
    }


    @Test
    void getLastUpdatedUserTest() {
        when(userProfileRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(getUserProfile()));
        String user = pageDownloader.getLastUpdatedUser(1l);
        assertNotNull(user);
        assertEquals("Update User", user);
    }

    @Test
    void getLastUpdatedUserTestNoProfile() {
        when(userProfileRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        String user = pageDownloader.getLastUpdatedUser(1l);
        assertNotNull(user);
        assertEquals(" ", user);
    }

    private ConditionCode conditionCode() {
        ConditionCode code = new ConditionCode();
        code.setId("1025");
        code.setConditionDescTxt("Hepatitis acute");
        return code;
    }

    private PageCondMapping getMapping() {
        PageCondMapping mapping = new PageCondMapping();
        mapping.setAddTime(Instant.now());
        mapping.setAddUserId(10l);
        mapping.setConditionCd("1025");
        mapping.setLastChgTime(Instant.now());
        mapping.setLastChgUserId(1000l);
        mapping.setWaTemplateUid(getTemplate(1l));
        return mapping;
    }

    private UserProfile getUserProfile() {
        UserProfile user = new UserProfile();
        user.setFirstNm("Update");
        user.setLastNm("User");
        return user;
    }

    private WaTemplate getTemplate(Long id) {
        WaTemplate template = new WaTemplate();
        template.setId(id);
        template.setTemplateNm("testTemplate");
        template.setTemplateType("Published");
        template.setPublishVersionNbr(1);
        template.setPublishIndCd('T');
        template.setLastChgTime(Instant.now());
        template.setLastChgUserId(100l);
        template.setBusObjType("INV");
        return template;
    }

    @Test
    void downloadLibraryPdf() throws IOException, DocumentException {
        when(templateRepository.getAllPagesOrderedByName()).thenReturn(List.of(getTemplate(1l)));
        when(pageConMappingRepository.findByWaTemplateUidIn(Mockito.any())).thenReturn(List.of(getMapping()));
        when(conditionCodeRepository.findByIdIn(Mockito.any())).thenReturn(List.of(conditionCode()));
        byte[] content = pageDownloader.downloadLibraryPDF();
        assertNotNull(content);

        String output = new String(content, StandardCharsets.UTF_8);
        assertNotNull(output);

    }

    @Test
    void downloadLibraryPdfException() {
        when(templateRepository.getAllPagesOrderedByName()).thenThrow(new QueryException("Error downloading Page Library"));
        var exception = assertThrows(RuntimeException.class, () -> pageDownloader.downloadLibraryPDF());
        assertTrue(exception.getMessage().contains("Error downloading Page Library"));

    }

    @Test
    void downloadPageMetaData() throws IOException {
        Long waTemplateUid = 100l;
        when(waUiMetadataRepository.findPageMetadataByWaTemplateUid(waTemplateUid)).thenReturn(getPageMetaData());
        ByteArrayInputStream response = pageDownloader.downloadPageMetadataByWaTemplateUid(waTemplateUid);
        byte[] content = response.readAllBytes();
        assertNotNull(content);
        response.read(content, 0, content.length);
        String output = new String(content, StandardCharsets.UTF_8);
        assertNotNull(output);

    }

    List<Object[]> getPageMetaData() {
        List<Object[]> pageMetaData = new ArrayList<>();
        pageMetaData.add(new Object[]{"col1_val", "col2_val", "col3_val", "col4_val", "col5_val", "col6_val", "col7_val"});
        pageMetaData.add(new Object[]{"col1_val", "col2_val", "col3_val", "col4_val", "col5_val", "col6_val", "col7_val"});
        return pageMetaData;
    }

    @Test
    void downloadPageMetaDataException() {
        when(waUiMetadataRepository.findPageMetadataByWaTemplateUid(100l)).thenThrow(new QueryException("Error downloading Page Metadata"));
        var exception = assertThrows(RuntimeException.class, () -> pageDownloader.downloadPageMetadataByWaTemplateUid(100l));
        assertTrue(exception.getMessage().contains("Error downloading Page Metadata"));

    }


}
