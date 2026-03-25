package gov.cdc.nbs.questionbank.template;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import gov.cdc.nbs.questionbank.template.exception.TemplateImportException;
import gov.cdc.nbs.questionbank.template.response.Template;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(MockitoExtension.class)
class TemplateImporterTest {

  private static final String SUCESS_LOCATION =
      "http://localhost:8080/nbs/PreviewTemplate.do?method=viewTemplate&srcTemplateNm=AARBOVIRAL_1_3_INV_NBS_5_4&src=Import&templateUid=1000380";

  private static final String EXISTS_LOCATION =
      "http://localhost:8080/nbs/PreviewTemplate.do?method=viewTemplate&srcTemplateNm=AARBOVIRAL_1_3_INV_NBS_5_4&src=Import&templateUid=templateAlreadyExists";

  private static final String BAD_FILE_LOCATION =
      "http://localhost:8080/nbs/PreviewTemplate.do?method=viewTemplate&srcTemplateNm=&src=Import&templateUid=TNF";

  @Mock private RestTemplate restTemplate;
  @Mock private TemplateFinder finder;

  @InjectMocks private TemplateImporter importer;

  @Test
  @SuppressWarnings("unchecked")
  void should_import_template() {
    // Given a template to import
    MultipartFile mockFile = Mockito.mock(MultipartFile.class);
    when(mockFile.getResource()).thenReturn(Mockito.mock(Resource.class));

    // and a working Classic endpoint
    HttpHeaders headers = Mockito.mock(HttpHeaders.class);
    ResponseEntity<String> response = Mockito.mock(ResponseEntity.class);
    when(response.getHeaders()).thenReturn(headers);
    when(headers.getFirst("Location")).thenReturn(SUCESS_LOCATION);
    when(restTemplate.exchange(Mockito.any(), eq(String.class))).thenReturn(response);
    Template createdTemplate = template();
    when(finder.find(1000380l)).thenReturn(createdTemplate);

    // when a template is imported
    Template template = importer.importTemplate(mockFile);

    // then the new template is returned
    assertEquals(createdTemplate, template);
  }

  @Test
  @SuppressWarnings("unchecked")
  void should_fail_already_exists() {
    // Given a template to import that already exists
    MultipartFile mockFile = Mockito.mock(MultipartFile.class);
    when(mockFile.getResource()).thenReturn(Mockito.mock(Resource.class));

    // and a working Classic endpoint
    HttpHeaders headers = Mockito.mock(HttpHeaders.class);
    ResponseEntity<String> response = Mockito.mock(ResponseEntity.class);
    when(response.getHeaders()).thenReturn(headers);
    when(headers.getFirst("Location")).thenReturn(EXISTS_LOCATION);
    when(restTemplate.exchange(Mockito.any(), eq(String.class))).thenReturn(response);

    // when a template is imported
    // then an exception is thrown
    assertThrows(TemplateImportException.class, () -> importer.importTemplate(mockFile));
  }

  @Test
  @SuppressWarnings("unchecked")
  void should_fail_bad_file() {
    // Given a template to import that already exists
    MultipartFile mockFile = Mockito.mock(MultipartFile.class);
    when(mockFile.getResource()).thenReturn(Mockito.mock(Resource.class));

    // and a working Classic endpoint
    HttpHeaders headers = Mockito.mock(HttpHeaders.class);
    ResponseEntity<String> response = Mockito.mock(ResponseEntity.class);
    when(response.getHeaders()).thenReturn(headers);
    when(headers.getFirst("Location")).thenReturn(BAD_FILE_LOCATION);
    when(restTemplate.exchange(Mockito.any(), eq(String.class))).thenReturn(response);

    // when a template is imported
    // then an exception is thrown
    assertThrows(TemplateImportException.class, () -> importer.importTemplate(mockFile));
  }

  @Test
  @SuppressWarnings("unchecked")
  void should_fail_null_location() {
    // Given a template to import that already exists
    MultipartFile mockFile = Mockito.mock(MultipartFile.class);
    when(mockFile.getResource()).thenReturn(Mockito.mock(Resource.class));

    // and a working Classic endpoint
    HttpHeaders headers = Mockito.mock(HttpHeaders.class);
    ResponseEntity<String> response = Mockito.mock(ResponseEntity.class);
    when(response.getHeaders()).thenReturn(headers);
    when(headers.getFirst("Location")).thenReturn(null);
    when(restTemplate.exchange(Mockito.any(), eq(String.class))).thenReturn(response);

    // when a template is imported
    // then an exception is thrown
    assertThrows(TemplateImportException.class, () -> importer.importTemplate(mockFile));
  }

  @Test
  @SuppressWarnings("unchecked")
  void should_fail_bad_id() {
    // Given a template to import that already exists
    MultipartFile mockFile = Mockito.mock(MultipartFile.class);
    when(mockFile.getResource()).thenReturn(Mockito.mock(Resource.class));

    // and a working Classic endpoint
    HttpHeaders headers = Mockito.mock(HttpHeaders.class);
    ResponseEntity<String> response = Mockito.mock(ResponseEntity.class);
    when(response.getHeaders()).thenReturn(headers);
    when(headers.getFirst("Location"))
        .thenReturn("srcTemplateNm=AARBOVIRAL_1_3_INV_NBS_5_4&src=Import&templateUid=abcD");
    when(restTemplate.exchange(Mockito.any(), eq(String.class))).thenReturn(response);

    // when a template is imported
    // then an exception is thrown
    assertThrows(TemplateImportException.class, () -> importer.importTemplate(mockFile));
  }

  private Template template() {
    return new Template(1000380l, "test", null, null, null, null, null, null);
  }
}
