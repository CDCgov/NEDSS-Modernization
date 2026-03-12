package gov.cdc.nbs.questionbank.template;

import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.repository.WaTemplateRepository;
import gov.cdc.nbs.questionbank.template.exception.TemplateNotFoundException;
import gov.cdc.nbs.questionbank.template.response.Template;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class TemplateFinder {
  private static final String TEMPLATE_TYPE = "TEMPLATE";
  private final WaTemplateRepository templateRepository;

  public TemplateFinder(final WaTemplateRepository templateRepository) {
    this.templateRepository = templateRepository;
  }

  public Template find(Long id) {
    WaTemplate template =
        templateRepository
            .findByIdAndTemplateType(id, TEMPLATE_TYPE)
            .orElseThrow(() -> new TemplateNotFoundException(id));
    return toTemplate(template);
  }

  public List<Template> findAllTemplates(String type) {
    return type != null
        ? templateRepository
            .findAllByTemplateTypeAndBusObjType(TEMPLATE_TYPE, type, Sort.by("templateNm"))
            .stream()
            .map(this::toTemplate)
            .toList()
        : templateRepository.findAllByTemplateType(TEMPLATE_TYPE, Sort.by("templateNm")).stream()
            .map(this::toTemplate)
            .toList();
  }

  private Template toTemplate(WaTemplate aWaTemplate) {
    return new Template(
        aWaTemplate.getId(),
        aWaTemplate.getTemplateNm(),
        aWaTemplate.getRecordStatusCd(),
        aWaTemplate.getLastChgTime(),
        aWaTemplate.getLastChgUserId(),
        aWaTemplate.getDescTxt(),
        aWaTemplate.getParentTemplateUid(),
        aWaTemplate.getSourceNm());
  }
}
