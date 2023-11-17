package gov.cdc.nbs.questionbank.template;


import gov.cdc.nbs.questionbank.template.response.Template;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterStyle;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

  @Operation(
      operationId = "import",
      summary = "Creates a new Template from an XML File.",
      description = "Creates a new Template by importing an XML file that describes how the template should be created."
  )
  @ApiOperation(value = "", nickname = "import")
  @PostMapping(path = "import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public Template importTemplate(
      @RequestPart("file")
      @Parameter(name = "file", style = ParameterStyle.FORM, required = true)
      MultipartFile file
  ) {
    return templateImporter.importTemplate(file);
  }

}
