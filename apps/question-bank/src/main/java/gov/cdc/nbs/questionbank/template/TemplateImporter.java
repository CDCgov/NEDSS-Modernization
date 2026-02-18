package gov.cdc.nbs.questionbank.template;

import gov.cdc.nbs.questionbank.template.exception.TemplateImportException;
import gov.cdc.nbs.questionbank.template.response.Template;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Component
public class TemplateImporter {
  private static final String LOCATION = "/ManageTemplates.do?method=importTemplate&type=Import";
  private final RestTemplate restTemplate;
  private final TemplateFinder reader;

  public TemplateImporter(
      @Qualifier("classicTemplate") final RestTemplate restTemplate, final TemplateFinder reader) {
    this.restTemplate = restTemplate;
    this.reader = reader;
  }

  /**
   * Calls NBS Classic to perform the import of a template. The response from classic has a 302
   * Found status with the `Location` header giving us the necessary information to determine if the
   * import was a success. On a successful import the header will be similar to:
   * "http://localhost:8080/nbs/PreviewTemplate.do?method=viewTemplate&srcTemplateNm=AARBOVIRAL_1_3_INV_NBS_5_4&src=Import&templateUid=1000380"
   *
   * <p>On a failed import, the redirect will be null or contain "templateAlreadyExists" or
   * "templateUid=TNF"
   *
   * @param data
   * @return
   */
  public Template importTemplate(final MultipartFile file) {
    // Add file resource to multipart form
    MultiValueMap<String, Object> data = new LinkedMultiValueMap<>();
    data.add("importFile", file.getResource());

    // Send the request to classic
    RequestEntity<MultiValueMap<String, Object>> request =
        RequestEntity.post(LOCATION).contentType(MediaType.MULTIPART_FORM_DATA).body(data);

    var response = restTemplate.exchange(request, String.class);

    // Validate the response
    String location = response.getHeaders().getFirst("Location");
    if (location == null) {
      throw new TemplateImportException("Failed to import template");
    } else if (location.contains("templateUid=TNF")) {
      throw new TemplateImportException("Failed to parse template");
    } else if (location.contains("templateAlreadyExists")) {
      throw new TemplateImportException("The provided template already exists");
    }

    // Attempt to retrieve the Uid from the location response
    String uidString =
        location.substring(location.indexOf("templateUid=") + "templateUid=".length());
    try {
      Long id = Long.parseLong(uidString);
      return reader.find(id);
    } catch (NumberFormatException ex) {
      throw new TemplateImportException("Failed to find template Id");
    }
  }
}
