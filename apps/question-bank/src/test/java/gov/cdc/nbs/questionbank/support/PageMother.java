package gov.cdc.nbs.questionbank.support;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import gov.cdc.nbs.questionbank.entity.PageCondMapping;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.entity.repository.PageCondMappingRepository;
import gov.cdc.nbs.questionbank.entity.repository.WANNDMetadataRepository;
import gov.cdc.nbs.questionbank.entity.repository.WARDBMetadataRepository;
import gov.cdc.nbs.questionbank.entity.repository.WaTemplateRepository;
import gov.cdc.nbs.questionbank.entity.repository.WaUiMetadataRepository;
import gov.cdc.nbs.questionbank.page.util.PageConstants;
import gov.cdc.nbs.questionbank.pagerules.repository.WaRuleMetaDataRepository;

@Component
@Transactional
public class PageMother {
    private static final String ASEPTIC_MENINGITIS_ID = "10010";
    private static final String BRUCELLOSIS_ID = "10020";

    @Autowired
    private WaTemplateRepository repository;

    @Autowired
    private PageCondMappingRepository pageConMappingRepository;

    @Autowired
    private WaUiMetadataRepository waUiMetadatumRepository;

    @Autowired
    private WaRuleMetaDataRepository waRuleMetaDataRepository;

    @Autowired
    private WANNDMetadataRepository wanndMetadataRepository;

    @Autowired
    private WARDBMetadataRepository wARDBMetadataRepository;


    private List<WaTemplate> allPages = new ArrayList<>();

    public void clean() {
        wanndMetadataRepository.deleteAll();
        waUiMetadatumRepository.deleteAll();
        wARDBMetadataRepository.deleteAll();
        pageConMappingRepository.deleteAll();
        waRuleMetaDataRepository.deleteAll();
        repository.deleteAll();
        allPages.clear();
    }

    public void cleanCreated() {
        allPages.forEach(p -> {
            waUiMetadatumRepository.deleteAllByWaTemplateUid(p);
            pageConMappingRepository.deleteAllByWaTemplateUid(p);
            repository.delete(p);
        });
        allPages.clear();
    }

    public WaTemplate one() {
        WaTemplate page = allPages.get(0);
        if (page == null) {
            throw new IllegalStateException("No pages exist");
        }
        return page;
    }

    public WaTemplate brucellosis() {
        return allPages.stream()
                .filter(t -> t.getConditionMappings()
                        .stream()
                        .anyMatch(c -> c.getConditionCd().equals(BRUCELLOSIS_ID)))
                .findFirst()
                .orElseGet(this::createBrucellosisPage);
    }

    public WaTemplate asepticMeningitis() {
        return allPages.stream()
                .filter(t -> t.getConditionMappings()
                        .stream()
                        .anyMatch(c -> c.getConditionCd().equals(ASEPTIC_MENINGITIS_ID)))
                .findFirst()
                .orElseGet(this::createAsepticMeningitisPage);
    }


    private WaTemplate createBrucellosisPage() {
        Instant now = Instant.now();
        WaTemplate page = new WaTemplate();
        page.setTemplateNm("brucellosis page");
        page.setTemplateType("Draft");
        page.setBusObjType("INV");
        page.setNndEntityIdentifier("GEN_Case_Map_v2.0");

        page.setRecordStatusCd("Active");
        page.setRecordStatusTime(now);
        page.setAddTime(now);
        page.setAddUserId(1L);
        page.setLastChgTime(now);
        page.setLastChgUserId(1L);

        PageCondMapping conditionMapping = new PageCondMapping();
        conditionMapping.setWaTemplateUid(page);
        conditionMapping.setConditionCd(BRUCELLOSIS_ID); // From test db condition_code table
        conditionMapping.setAddTime(now);
        conditionMapping.setAddUserId(1l);
        conditionMapping.setLastChgTime(now);
        conditionMapping.setLastChgUserId(1l);

        page.setConditionMappings(Collections.singleton(conditionMapping));

        WaUiMetadata pageType = new WaUiMetadata();
        pageType.setWaTemplateUid(page);
        pageType.setNbsUiComponentUid(1002L);
        pageType.setOrderNbr(1);
        pageType.setDisplayInd("T");
        pageType.setVersionCtrlNbr(1);

        WaUiMetadata tab = new WaUiMetadata();
        tab.setWaTemplateUid(page);
        tab.setNbsUiComponentUid(1010L);
        tab.setOrderNbr(2);
        tab.setDisplayInd("T");
        tab.setVersionCtrlNbr(1);
        tab.setQuestionLabel("First tab");

        WaUiMetadata section = new WaUiMetadata();
        section.setWaTemplateUid(page);
        section.setNbsUiComponentUid(1015L);
        section.setOrderNbr(3);
        section.setDisplayInd("T");
        section.setVersionCtrlNbr(1);
        section.setQuestionLabel("First section");

        WaUiMetadata subsection = new WaUiMetadata();
        subsection.setWaTemplateUid(page);
        subsection.setNbsUiComponentUid(1016L);
        subsection.setOrderNbr(4);
        subsection.setDisplayInd("T");
        subsection.setVersionCtrlNbr(1);
        subsection.setQuestionLabel("First subsection");

        WaUiMetadata question = new WaUiMetadata();
        question.setWaTemplateUid(page);
        question.setNbsUiComponentUid(1009L);
        question.setOrderNbr(5);
        question.setDisplayInd("T");
        question.setVersionCtrlNbr(1);
        question.setQuestionLabel("First question");

        WaUiMetadata tab2 = new WaUiMetadata();
        tab2.setWaTemplateUid(page);
        tab2.setNbsUiComponentUid(1010L);
        tab2.setOrderNbr(6);
        tab2.setDisplayInd("T");
        tab2.setVersionCtrlNbr(1);
        tab2.setQuestionLabel("Second tab");

        WaUiMetadata section2 = new WaUiMetadata();
        section2.setWaTemplateUid(page);
        section2.setNbsUiComponentUid(1015L);
        section2.setOrderNbr(7);
        section2.setDisplayInd("T");
        section2.setVersionCtrlNbr(1);
        section2.setQuestionLabel("Second section");

        WaUiMetadata subsection2 = new WaUiMetadata();
        subsection2.setWaTemplateUid(page);
        subsection2.setNbsUiComponentUid(1016L);
        subsection2.setOrderNbr(8);
        subsection2.setDisplayInd("T");
        subsection2.setVersionCtrlNbr(1);
        subsection2.setQuestionLabel("Second subsection");

        WaUiMetadata question2 = new WaUiMetadata();
        question2.setWaTemplateUid(page);
        question2.setNbsUiComponentUid(1009L);
        question2.setOrderNbr(9);
        question2.setDisplayInd("T");
        question2.setVersionCtrlNbr(1);
        question2.setQuestionLabel("Second question");

        page.setUiMetadata(Arrays.asList(
                pageType,
                tab,
                section,
                subsection,
                question,
                tab2,
                section2,
                subsection2,
                question2));
        page = repository.save(page);
        allPages.add(page);
        return page;
    }


    private WaTemplate createAsepticMeningitisPage() {
        Instant now = Instant.now().plusSeconds(5);
        WaTemplate page = new WaTemplate();
        page.setTemplateNm("Aseptic Meningitis");
        page.setTemplateType("Draft");
        page.setBusObjType("INV");
        page.setNndEntityIdentifier("GEN_Case_Map_v2.0");

        page.setRecordStatusCd("Active");
        page.setRecordStatusTime(now);
        page.setAddTime(now);
        page.setAddUserId(1L);
        page.setLastChgTime(now);
        page.setLastChgUserId(1L);


        PageCondMapping conditionMapping = new PageCondMapping();
        conditionMapping.setWaTemplateUid(page);
        conditionMapping.setConditionCd(ASEPTIC_MENINGITIS_ID); // From test db condition_code table
        conditionMapping.setAddTime(now);
        conditionMapping.setAddUserId(1l);
        conditionMapping.setLastChgTime(now);
        conditionMapping.setLastChgUserId(1l);

        page.setConditionMappings(Collections.singleton(conditionMapping));

        page = repository.save(page);

        // add page detail mappings
        WaUiMetadata tab = createUiMetadata(page, PageConstants.TAB_COMPONENT, 2);
        WaUiMetadata section = createUiMetadata(page, PageConstants.SECTION_COMPONENT, 3);
        WaUiMetadata subSection = createUiMetadata(page, PageConstants.SUB_SECTION_COMPONENT, 4);
        WaUiMetadata question = createUiMetadata(page, PageConstants.SPE_QUESTION_COMPONENT, 5);
        WaUiMetadata tab2 = createUiMetadata(page, PageConstants.TAB_COMPONENT, 6);

        waUiMetadatumRepository.save(tab);
        waUiMetadatumRepository.save(section);
        waUiMetadatumRepository.save(subSection);
        waUiMetadatumRepository.save(question);
        waUiMetadatumRepository.save(tab2);

        page.setUiMetadata(Arrays.asList(
                tab,
                section,
                subSection,
                question,
                tab2));

        allPages.add(page);
        return page;
    }

    public WaTemplate createPageDraft(WaTemplate pageIn) {

        repository.save(pageIn);

        Instant now = Instant.now().plusSeconds(15);
        WaTemplate page = new WaTemplate();
        page.setTemplateNm(pageIn.getTemplateNm());
        page.setTemplateType("Draft");
        page.setBusObjType("INV");
        page.setNndEntityIdentifier("GEN_Case_Map_v2.0");

        page.setRecordStatusCd("Active");
        page.setRecordStatusTime(now);
        page.setAddTime(now);
        page.setAddUserId(1L);
        page.setLastChgTime(now);
        page.setLastChgUserId(1L);


        PageCondMapping conditionMapping = new PageCondMapping();
        conditionMapping.setWaTemplateUid(page);
        conditionMapping.setConditionCd(ASEPTIC_MENINGITIS_ID); // From test db condition_code table
        conditionMapping.setAddTime(now);
        conditionMapping.setAddUserId(1l);
        conditionMapping.setLastChgTime(now);
        conditionMapping.setLastChgUserId(1l);

        page.setConditionMappings(Collections.singleton(conditionMapping));

        page = repository.save(page);

        // add page detail mappings
        WaUiMetadata tab = createUiMetadata(page, PageConstants.TAB_COMPONENT, 2);
        WaUiMetadata section = createUiMetadata(page, PageConstants.SECTION_COMPONENT, 3);
        WaUiMetadata subSection = createUiMetadata(page, PageConstants.SUB_SECTION_COMPONENT, 4);
        WaUiMetadata question = createUiMetadata(page, PageConstants.SPE_QUESTION_COMPONENT, 5);

        waUiMetadatumRepository.save(tab);
        waUiMetadatumRepository.save(section);
        waUiMetadatumRepository.save(subSection);
        waUiMetadatumRepository.save(question);

        allPages.add(page);
        return page;
    }

    private WaUiMetadata createUiMetadata(WaTemplate aPage, Long nbsUiComponentUid, Integer orderNumber) {
        WaUiMetadata record = new WaUiMetadata();
        record.setWaTemplateUid(aPage);
        record.setNbsUiComponentUid(nbsUiComponentUid);
        record.setOrderNbr(orderNumber);
        record.setVersionCtrlNbr(0);
        return record;
    }


}
