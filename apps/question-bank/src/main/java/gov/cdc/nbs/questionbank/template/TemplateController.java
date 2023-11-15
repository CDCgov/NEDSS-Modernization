package gov.cdc.nbs.questionbank.template;


import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import gov.cdc.nbs.questionbank.template.response.Template;

@RestController
@RequestMapping("/api/v1/template/")
@PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
public class TemplateController {

  private final TemplateFinder finder;
  private final TemplateImporter templateImporter;

  public TemplateController(
      final TemplateFinder finder,
      final TemplateImporter templateImporter) {
    this.finder = finder;
    this.templateImporter = templateImporter;
  }

  @GetMapping
  public List<Template> findAllTemplates() {
    return finder.findAllTemplates();
  }

  @PostMapping(path = "import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public Template importTemplate(@RequestPart("fileInput") MultipartFile file) {
    return templateImporter.importTemplate(file);
  }

}
