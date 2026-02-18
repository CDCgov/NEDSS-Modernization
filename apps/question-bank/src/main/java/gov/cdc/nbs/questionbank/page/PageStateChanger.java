package gov.cdc.nbs.questionbank.page;

import gov.cdc.nbs.questionbank.entity.PageCondMapping;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.entity.pagerule.WaRuleMetadata;
import gov.cdc.nbs.questionbank.entity.repository.PageCondMappingRepository;
import gov.cdc.nbs.questionbank.entity.repository.WaTemplateRepository;
import gov.cdc.nbs.questionbank.entity.repository.WaUiMetadataRepository;
import gov.cdc.nbs.questionbank.page.exception.PageUpdateException;
import gov.cdc.nbs.questionbank.page.response.PageStateResponse;
import gov.cdc.nbs.questionbank.page.util.PageConstants;
import gov.cdc.nbs.questionbank.pagerules.repository.WaRuleMetaDataRepository;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PageStateChanger {

  private final WaTemplateRepository templateRepository;
  private final WaUiMetadataRepository waUiMetadataRepository;
  private final PageCondMappingRepository pageConMappingRepository;
  private final WaRuleMetaDataRepository waRuleMetaDataRepository;

  public PageStateChanger(
      final WaTemplateRepository templateRepository,
      final WaUiMetadataRepository waUiMetadataRepository,
      final PageCondMappingRepository pageConMappingRepository,
      final WaRuleMetaDataRepository waRuleMetaDataRepository) {
    this.templateRepository = templateRepository;
    this.waUiMetadataRepository = waUiMetadataRepository;
    this.pageConMappingRepository = pageConMappingRepository;
    this.waRuleMetaDataRepository = waRuleMetaDataRepository;
  }

  public PageStateResponse savePageAsDraft(Long id) {
    PageStateResponse response = new PageStateResponse();
    try {
      WaTemplate page =
          templateRepository
              .findById(id)
              .orElseThrow(() -> new PageUpdateException(PageConstants.SAVE_DRAFT_FAIL));
      if (page.getTemplateType().equals(PageConstants.PUBLISHED_WITH_DRAFT)) {
        throw new PageUpdateException(PageConstants.SAVE_DRAFT_NOCHANGE);
      }
      WaTemplate draftPage = createDraftCopy(page);
      page.setTemplateType(PageConstants.PUBLISHED_WITH_DRAFT);
      page = templateRepository.save(page);
      draftPage = templateRepository.save(draftPage);
      pageConMappingRepository.saveAll(draftPage.getConditionMappings());

      List<WaUiMetadata> draftMappings = copyWaTemplateUIMetaData(page, draftPage);
      waUiMetadataRepository.saveAll(draftMappings);

      List<WaRuleMetadata> rules = copyRules(id, draftPage.getId());
      waRuleMetaDataRepository.saveAll(rules);

      response.setMessage(PageConstants.SAVE_DRAFT_SUCCESS);
      response.setTemplateId(draftPage.getId());

    } catch (PageUpdateException e) {
      throw e;
    } catch (Exception e) {
      throw new PageUpdateException(PageConstants.SAVE_DRAFT_FAIL);
    }
    return response;
  }

  public List<WaRuleMetadata> copyRules(long publishedPage, long newPage) {
    List<WaRuleMetadata> initialRuleMappings = new ArrayList<>();
    List<WaRuleMetadata> rules = waRuleMetaDataRepository.findByWaTemplateUid(publishedPage);
    for (WaRuleMetadata original : rules) {
      WaRuleMetadata clone = WaRuleMetadata.clone(original);
      clone.setWaTemplateUid(newPage);
      initialRuleMappings.add(clone);
    }
    return initialRuleMappings;
  }

  public List<WaUiMetadata> copyWaTemplateUIMetaData(WaTemplate page, WaTemplate clonePage) {
    List<WaUiMetadata> draftMappings = new ArrayList<>();
    List<WaUiMetadata> pages = waUiMetadataRepository.findAllByWaTemplateUid(page);
    for (WaUiMetadata original : pages) {
      WaUiMetadata clone = WaUiMetadata.clone(original, clonePage);
      draftMappings.add(clone);
    }
    return draftMappings;
  }

  public WaTemplate createDraftCopy(WaTemplate oldPage) {
    if (oldPage.getTemplateType().equals("Draft")) {
      return oldPage;
    }

    WaTemplate draftCopy = new WaTemplate();
    draftCopy.setTemplateNm(oldPage.getTemplateNm());
    draftCopy.setTemplateType("Draft");
    draftCopy.setPublishVersionNbr(0);
    draftCopy.setPublishIndCd('F');
    draftCopy.setAddTime(oldPage.getAddTime());
    draftCopy.setAddUserId(oldPage.getAddUserId());
    draftCopy.setBusObjType(oldPage.getBusObjType());
    draftCopy.setConditionCd(oldPage.getConditionCd());
    draftCopy.setDatamartNm(oldPage.getDatamartNm());
    draftCopy.setDescTxt(oldPage.getDescTxt());
    draftCopy.setFormCd(oldPage.getFormCd());
    draftCopy.setLastChgTime(oldPage.getLastChgTime());
    draftCopy.setLastChgUserId(oldPage.getLastChgUserId());
    draftCopy.setLocalId(oldPage.getLocalId());
    draftCopy.setNndEntityIdentifier(oldPage.getNndEntityIdentifier());
    draftCopy.setParentTemplateUid(oldPage.getParentTemplateUid());
    draftCopy.setRecordStatusCd(oldPage.getRecordStatusCd());
    draftCopy.setRecordStatusTime(oldPage.getRecordStatusTime());
    draftCopy.setSourceNm(oldPage.getSourceNm());
    draftCopy.setVersionNote(oldPage.getVersionNote());
    draftCopy.setXmlPayload(oldPage.getXmlPayload());
    draftCopy.setConditionMappings(
        copyConditionMappings(oldPage.getConditionMappings(), draftCopy));

    return draftCopy;
  }

  private Set<PageCondMapping> copyConditionMappings(
      Set<PageCondMapping> original, WaTemplate page) {
    if (original == null) return original;
    Set<PageCondMapping> copy = new HashSet<>();
    for (PageCondMapping con : original) {
      PageCondMapping aCopy = new PageCondMapping();
      aCopy.setAddTime(con.getAddTime());
      aCopy.setAddUserId(con.getAddUserId());
      aCopy.setConditionCd(con.getConditionCd());
      aCopy.setLastChgTime(con.getLastChgTime());
      aCopy.setLastChgUserId(con.getLastChgUserId());
      aCopy.setWaTemplateUid(page);
      copy.add(aCopy);
    }
    return copy;
  }
}
