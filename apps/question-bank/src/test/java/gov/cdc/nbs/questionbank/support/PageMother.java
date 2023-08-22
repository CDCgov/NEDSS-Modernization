package gov.cdc.nbs.questionbank.support;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gov.cdc.nbs.questionbank.condition.repository.ConditionCodeRepository;
import gov.cdc.nbs.questionbank.entity.PageCondMapping;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.repository.PageCondMappingRepository;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.entity.repository.UserProfileRepository;
import gov.cdc.nbs.questionbank.entity.repository.WaTemplateRepository;
import gov.cdc.nbs.questionbank.entity.repository.WaUiMetadataRepository;
import gov.cdc.nbs.questionbank.page.util.PageConstants;
import gov.cdc.nbs.questionbank.pagerules.repository.WaRuleMetaDataRepository;

@Component
public class PageMother {
    private static final String ASEPTIC_MENINGITIS_ID = "10010";
    private static final String BRUCELLOSIS_ID = "10020";

    @Autowired
    private WaTemplateRepository repository;

    @Autowired
    private WaUiMetadataRepository waUiMetadataRepository;
    
    @Autowired
    private PageCondMappingRepository pageConMappingRepository;
    
    @Autowired
	private ConditionCodeRepository conditionCodeRepository;

	@Autowired
	private UserProfileRepository userProfileRepository;
	
	@Autowired
	private WaUiMetadataRepository waUiMetadatumRepository;

    @Autowired
	private WaRuleMetaDataRepository waRuleMetaDataRepository;

    private List<WaTemplate> allPages = new ArrayList<>();

    public void clean() {   	
    	waUiMetadatumRepository.deleteAll();
        repository.deleteAll();
        pageConMappingRepository.deleteAll();
        waRuleMetaDataRepository.deleteAll();
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


        WaUiMetadata tab = new WaUiMetadata();
        tab.setWaTemplateUid(page);
        tab.setNbsUiComponentUid(1010L);
        tab.setOrderNbr(1);
        tab.setDisplayInd("T");
        tab.setVersionCtrlNbr(1);

        WaUiMetadata section = new WaUiMetadata();
        section.setWaTemplateUid(page);
        section.setNbsUiComponentUid(1015L);
        section.setOrderNbr(2);
        section.setDisplayInd("T");
        section.setVersionCtrlNbr(1);

        page.setUiMetadata(Arrays.asList(tab, section));
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
        WaUiMetadata tab = getwaUiMetaDtum(page, PageConstants.TAB_COMPONENT, 2);
        WaUiMetadata section = getwaUiMetaDtum(page, PageConstants.SECTION_COMPONENT, 3);
        WaUiMetadata subSection = getwaUiMetaDtum(page, PageConstants.SUB_SECTION_COMPONENT, 4);
        WaUiMetadata question = getwaUiMetaDtum(page, PageConstants.SPE_QUESTION_COMPONENT, 5);
        
        waUiMetadatumRepository.save(tab);
        waUiMetadatumRepository.save(section);
        waUiMetadatumRepository.save(subSection);
        waUiMetadatumRepository.save(question);
        
        allPages.add(page);
        return page;
    }
    
    private WaUiMetadata getwaUiMetaDtum(WaTemplate aPage, Long nbsUiComponentUid, Integer orderNumber) {
		WaUiMetadata record = new WaUiMetadata();
		record.setWaTemplateUid(aPage);
		record.setNbsUiComponentUid(nbsUiComponentUid);
		record.setOrderNbr(orderNumber);
		record.setVersionCtrlNbr(0);
		return record;
	}
    

}
