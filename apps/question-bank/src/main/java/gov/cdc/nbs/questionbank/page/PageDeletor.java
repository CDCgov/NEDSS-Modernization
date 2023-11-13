package gov.cdc.nbs.questionbank.page;

import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.repository.WANNDMetadataRepository;
import gov.cdc.nbs.questionbank.entity.repository.WARDBMetadataRepository;
import gov.cdc.nbs.questionbank.entity.repository.WaTemplateRepository;
import gov.cdc.nbs.questionbank.entity.repository.WaUiMetadataRepository;
import gov.cdc.nbs.questionbank.page.exception.PageUpdateException;
import gov.cdc.nbs.questionbank.page.response.PageStateResponse;
import gov.cdc.nbs.questionbank.page.util.PageConstants;
import gov.cdc.nbs.questionbank.pagerules.repository.WaRuleMetaDataRepository;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class PageDeletor {
    private final WaTemplateRepository templateRepository;

    private final WaUiMetadataRepository waUiMetadataRepository;

    private final WANNDMetadataRepository wanndMetadataRepository;

    private final WARDBMetadataRepository wARDBMetadataRepository;

    private final WaRuleMetaDataRepository waRuleMetaDataRepository;

    public PageDeletor(
            final WaTemplateRepository templateRepository,
            final WaUiMetadataRepository waUiMetadataRepository,
            final WANNDMetadataRepository wanndMetadataRepository,
            final WARDBMetadataRepository wARDBMetadataRepository,
            final WaRuleMetaDataRepository waRuleMetaDataRepository) {
        this.templateRepository = templateRepository;
        this.waUiMetadataRepository = waUiMetadataRepository;
        this.wanndMetadataRepository = wanndMetadataRepository;
        this.waRuleMetaDataRepository = waRuleMetaDataRepository;
        this.wARDBMetadataRepository = wARDBMetadataRepository;
    }


    @Transactional
    public PageStateResponse deletePageDraft(Long id) {
        PageStateResponse response = new PageStateResponse();
        try {
            Optional<WaTemplate> result = templateRepository.findById(id);
            if (result.isPresent()) {
                WaTemplate page = result.get();

                if (page.getTemplateType().equals(PageConstants.PUBLISHED_WITH_DRAFT)) {

                    WaTemplate draft = templateRepository.findByFormCdAndTemplateType(page.getFormCd(),
                            PageConstants.DRAFT);

                    deleteFromRepo(draft);

                    page.setTemplateType(PageConstants.PUBLISHED);
                    templateRepository.save(page);

                    response.setMessage(page.getTemplateNm() + " " + PageConstants.DRAFT_DELETE_SUCCESS);
                    response.setTemplateId(page.getId());
                } else if (page.getTemplateType().equals(PageConstants.DRAFT)) {
                    WaTemplate published = templateRepository.findByFormCdAndTemplateType(page.getFormCd(),
                            PageConstants.PUBLISHED_WITH_DRAFT);


                    if (published != null) {
                        published.setTemplateType(PageConstants.PUBLISHED);
                        templateRepository.save(published);
                        templateRepository.flush();
                    }

                    deleteFromRepo(page);


                    response.setMessage(page.getTemplateNm() + " " + PageConstants.DRAFT_DELETE_SUCCESS);
                    response.setTemplateId(page.getId());
                } else {
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

    private void deleteFromRepo(WaTemplate page) {
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
    }

}
