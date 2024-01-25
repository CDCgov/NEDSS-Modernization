package gov.cdc.nbs.questionbank.page;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import gov.cdc.nbs.questionbank.entity.PageCondMapping;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.entity.repository.PageCondMappingRepository;
import gov.cdc.nbs.questionbank.entity.repository.WaTemplateRepository;
import gov.cdc.nbs.questionbank.entity.repository.WaUiMetadataRepository;
import gov.cdc.nbs.questionbank.page.exception.PageCreateException;
import gov.cdc.nbs.questionbank.page.request.PageCreateRequest;
import gov.cdc.nbs.questionbank.page.response.PageCreateResponse;
import gov.cdc.nbs.questionbank.page.util.PageConstants;

@Service
@Transactional
public class PageCreator {

  private final WaTemplateRepository templateRepository;
  private final PageCondMappingRepository pageConMappingRepository;
  private final WaUiMetadataRepository waUiMetadataRepository;
  private final PageValidator validator;

  public PageCreator(
      final WaTemplateRepository templateRepository,
      final PageCondMappingRepository pageConMappingRepository,
      final WaUiMetadataRepository waUiMetadataRepository,
      final PageValidator validator) {
    this.templateRepository = templateRepository;
    this.pageConMappingRepository = pageConMappingRepository;
    this.waUiMetadataRepository = waUiMetadataRepository;
    this.validator = validator;
  }

    public PageCreateResponse createPage(PageCreateRequest request, Long userId) {
       validator.validate(request);

        if (request.dataMartName() != null && !request.dataMartName().isEmpty()) {
            Optional<WaTemplate> existingDataMartNm = templateRepository.findFirstByDatamartNm(request.dataMartName());
            if (existingDataMartNm.isPresent()) {
                String finalMessage = String.format(PageConstants.ADD_PAGE_DATAMART_NAME_EXISTS,
                        request.dataMartName());
                throw new PageCreateException(finalMessage);
            }
        }

        try {
            WaTemplate newPage = buildPage(
                    request,
                    request.eventType(),
                    userId);
            WaTemplate savedPage = templateRepository.save(newPage);
            createPageCondMappings(request, savedPage, userId);

            Optional<WaTemplate> template = templateRepository.findById(request.templateId());
            if (template.isPresent()) {
                List<WaUiMetadata> uiMappings = copyWaTemplateUIMetaData(template.get(), savedPage);
                waUiMetadataRepository.saveAll(uiMappings);
            }

            return new PageCreateResponse(
                    savedPage.getId(),
                    savedPage.getTemplateNm(),
                    savedPage.getTemplateNm() + PageConstants.ADD_PAGE_MESSAGE);

        } catch (Exception e) {
            throw new PageCreateException(PageConstants.ADD_PAGE_FAIL);
        }

    }

    public List<WaUiMetadata> copyWaTemplateUIMetaData(WaTemplate template, WaTemplate newPage) {
        List<WaUiMetadata> initialPageMappings = new ArrayList<>();
        List<WaUiMetadata> pages = waUiMetadataRepository.findAllByWaTemplateUid(template);
        for (WaUiMetadata original : pages) {
            WaUiMetadata clone = WaUiMetadata.clone(original);
            clone.setWaTemplateUid(newPage);
            initialPageMappings.add(clone);
        }
        return initialPageMappings;

    }

    public void createPageCondMappings(PageCreateRequest request, WaTemplate savePaged, Long userId) {
        if (request.conditionIds() == null) {
            return;
        }
        Instant now = Instant.now();
        List<PageCondMapping> mappings = request.conditionIds()
                .stream()
                .map(c -> {
                    PageCondMapping mapping = new PageCondMapping();
                    mapping.setWaTemplateUid(savePaged);
                    mapping.setLastChgTime(now);
                    mapping.setLastChgUserId(userId);
                    mapping.setAddTime(now);
                    mapping.setAddUserId(userId);
                    mapping.setConditionCd(c);
                    return mapping;
                }).toList();

        pageConMappingRepository.saveAll(mappings);
    }

    public WaTemplate buildPage(PageCreateRequest request, String eventType, Long userId) {

        WaTemplate result = new WaTemplate();
        result.setTemplateType("Draft");
        result.setXmlPayload("XML Payload");
        result.setFormCd("PG_" + request.name());
        result.setBusObjType(eventType);
        result.setDatamartNm(request.dataMartName());
        result.setRecordStatusCd("Active");
        result.setRecordStatusTime(Instant.now());
        result.setLastChgTime(Instant.now());
        result.setLastChgUserId(userId);
        result.setDescTxt(request.pageDescription());
        result.setTemplateNm(request.name());
        result.setPublishIndCd('F');
        result.setAddTime(Instant.now());
        result.setAddUserId(userId);
        result.setNndEntityIdentifier(request.messageMappingGuide());
        if (request.conditionIds() != null && !request.conditionIds().isEmpty()) {
            result.setConditionCd(request.conditionIds().get(0));
        }
        return result;
    }

}

