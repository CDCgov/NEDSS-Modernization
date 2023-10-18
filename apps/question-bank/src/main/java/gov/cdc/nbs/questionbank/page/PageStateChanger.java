package gov.cdc.nbs.questionbank.page;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.entity.repository.WANNDMetadataRepository;
import gov.cdc.nbs.questionbank.entity.repository.WARDBMetadataRepository;
import gov.cdc.nbs.questionbank.entity.repository.WaTemplateRepository;
import gov.cdc.nbs.questionbank.entity.repository.WaUiMetadataRepository;
import gov.cdc.nbs.questionbank.page.exception.PageUpdateException;
import gov.cdc.nbs.questionbank.page.response.PageStateResponse;
import gov.cdc.nbs.questionbank.page.util.PageConstants;
import gov.cdc.nbs.questionbank.pagerules.repository.WaRuleMetaDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PageStateChanger {

    @Autowired
    private WaTemplateRepository templateRepository;

    @Autowired
    private WaUiMetadataRepository waUiMetadataRepository;
    
    @Autowired
    private WANNDMetadataRepository wanndMetadataRepository;
    
    @Autowired
    private WARDBMetadataRepository wARDBMetadataRepository;
    
    @Autowired
    private WaRuleMetaDataRepository waRuleMetaDataRepository;
    
    

    public PageStateResponse savePageAsDraft(Long id) {
        PageStateResponse response = new PageStateResponse();
        try {
            Optional<WaTemplate> result = templateRepository.findById(id);
            if (result.isPresent()) {
                WaTemplate page = result.get();
                WaTemplate draftPage = createDraftCopy(page);
                page.setTemplateType("Published With Draft");
                templateRepository.save(page);
                templateRepository.save(draftPage);
                List<WaUiMetadata> draftMappings = copyWaTemplateUIMetaData(page, draftPage);
                waUiMetadataRepository.saveAll(draftMappings);

                response.setMessage(PageConstants.SAVE_DRAFT_SUCCESS);
                response.setTemplateId(page.getId());
            } else {
                throw new PageUpdateException(PageConstants.SAVE_DRAFT_FAIL);
            }
        } catch (PageUpdateException e) {
            throw e;
        } catch (Exception e) {
            throw new PageUpdateException(PageConstants.SAVE_DRAFT_FAIL);
        }
        return response;
    }
    
	@Transactional
	public PageStateResponse deletePageDraft(Long id) {
		PageStateResponse response = new PageStateResponse();
		try {
			Optional<WaTemplate> result = templateRepository.findById(id);
			if (result.isPresent()) {
				WaTemplate page = result.get();
				
				if (page.getTemplateType().equals(PageConstants.PUBLISHED_WITH_DRAFT)) {
					
					WaTemplate draft = templateRepository.findByTemplateNmAndTemplateType(page.getTemplateNm(),
							PageConstants.DRAFT);

					wanndMetadataRepository.deleteByWaTemplateUid(draft);
					wanndMetadataRepository.flush();
					wARDBMetadataRepository.deleteByWaTemplateUid(draft);
					wARDBMetadataRepository.flush();
					waUiMetadataRepository.deleteAllByWaTemplateUid(draft);
					waUiMetadataRepository.flush();
					waRuleMetaDataRepository.deleteByWaTemplateUid(draft.getId());
					waRuleMetaDataRepository.flush();
					templateRepository.deleteById(draft.getId());
					templateRepository.flush();

					page.setTemplateType(PageConstants.PUBLISHED);
					templateRepository.save(page);

					response.setMessage(page.getTemplateNm() + " " + PageConstants.DRAFT_DELETE_SUCCESS);
					response.setTemplateId(page.getId());	
				}
				else if( page.getTemplateType().equals(PageConstants.DRAFT)) {
                	
                	wanndMetadataRepository.deleteByWaTemplateUid(page);
					wanndMetadataRepository.flush();
					wARDBMetadataRepository.deleteByWaTemplateUid(page);
					wARDBMetadataRepository.flush();
					waUiMetadataRepository.deleteAllByWaTemplateUid(page);
					waUiMetadataRepository.flush();
					waRuleMetaDataRepository.deleteByWaTemplateUid(page.getId());
					waRuleMetaDataRepository.flush();
					templateRepository.deleteById(page.getId());
					templateRepository.flush();
					
					response.setMessage(page.getTemplateNm() + " " + PageConstants.DRAFT_DELETE_SUCCESS);
					response.setTemplateId(page.getId());
                }
				else {
                
				throw new PageUpdateException(PageConstants.DRAFT_NOT_FOUND);
				
				}
				
			} else {
				throw new PageUpdateException(PageConstants.PAGE_NOT_FOUND);
			}
		} catch (PageUpdateException e) {
			throw e;
		} catch (EntityNotFoundException a) {
			log.info("Skipping entity not found..");
		} catch (Exception e) {
			throw new PageUpdateException(PageConstants.DELETE_DRAFT_FAIL);
		}
		return response;
	}

    public List<WaUiMetadata> copyWaTemplateUIMetaData(WaTemplate page, WaTemplate clonePage) {
        List<WaUiMetadata> draftMappings = new ArrayList<>();
        List<WaUiMetadata> pages = waUiMetadataRepository.findAllByWaTemplateUid(page);
        for (WaUiMetadata original : pages) {
            WaUiMetadata clone = original;
            clone.setWaTemplateUid(clonePage);
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
        draftCopy.setConditionMappings(oldPage.getConditionMappings());
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


        return draftCopy;

    }


}
