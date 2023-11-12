package gov.cdc.nbs.questionbank.template.read;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import gov.cdc.nbs.questionbank.entity.*;
import gov.cdc.nbs.questionbank.entity.pagerule.WaRuleMetadata;
import gov.cdc.nbs.questionbank.entity.repository.*;
import gov.cdc.nbs.questionbank.pagerules.repository.WaRuleMetaDataRepository;
import gov.cdc.nbs.questionbank.template.request.SaveTemplateRequest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import gov.cdc.nbs.questionbank.template.TemplateReader;
import gov.cdc.nbs.questionbank.template.request.TemplateSearchRequest;
import gov.cdc.nbs.questionbank.template.response.Template;

class TemplateReaderTest {

	@Mock
	WaTemplateRepository templateRepository;
	@Mock
	private PageCondMappingRepository pageCondMappingRepository;
	@Mock
	private WaUiMetadataRepository uiMetadataRepository;
	@Mock
	private WANNDMetadataRepository wanndMetadataRepository;
	@Mock
	private WARDBMetadataRepository wardbMetadataRepository;
	@Mock
	private WaRuleMetaDataRepository waRuleMetaDataRepository;

	@InjectMocks
	TemplateReader templateReader;

	public TemplateReaderTest() {
		MockitoAnnotations.openMocks(this);
	}

	void findAllTemplates() {
		Pageable pageable = Pageable.ofSize(25);

		when(templateRepository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(getWaTemplate(1)), pageable, 1));
		Page<Template> result = templateReader.findAllTemplates(pageable);
		assertNotNull(result);
		assertNotNull(result.getContent());

	}

	@Test
	void searchTemplateBasic() {

		Pageable pageable = Pageable.ofSize(25);
		when(templateRepository.findByIdAndTemplateTypeIn(Mockito.anyLong(), Mockito.any()))
				.thenReturn(Optional.of(getWaTemplate(1)));
		TemplateSearchRequest search = new TemplateSearchRequest(1l,null,List.of("Draft"),null,null,null);
		Page<Template> result = templateReader.searchTemplate(search, pageable);
		assertNotNull(result);
		assertNotNull(result.getContent());

	}

	@Test
	void searchTemplateExpanded() {
		Pageable pageable = Pageable.ofSize(25);
		TemplateSearchRequest search = new TemplateSearchRequest(1l,"templateNm",List.of("Draft"),"ConditionCd","DataMartNm","Active");
		when(templateRepository.searchTemplate(Mockito.anyLong(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.any(), Mockito.any())).thenReturn(getTemplatePage(5, pageable));
	
		Page<Template> result = templateReader.searchTemplate(search, pageable);
		assertNotNull(result);
		assertNotNull(result.getContent());
		

	}

	@Test
	void simpleSearch() {
		TemplateSearchRequest search = new TemplateSearchRequest(1234l,null,List.of("Draft"),null,null,null);
		boolean result = templateReader.simpleSearch(search);
		assertTrue(result);
		 search = new TemplateSearchRequest(1234l,"templateNm",List.of("Draft"),null,null,null);
		result = templateReader.simpleSearch(search);
		assertFalse(result);
	}

	@Test
	void saveTemplateTest() throws Exception {
		SaveTemplateRequest request = new SaveTemplateRequest(1234L,"","");
		when(templateRepository.findById(request.waTemplateUid()))
				.thenReturn(Optional.of(getWaTemplate(1)));
		when(pageCondMappingRepository.findByWaTemplateUidAndConditionCd(Mockito.any(),Mockito.anyString()))
				.thenReturn(List.of(getMapping()));
		when(uiMetadataRepository.findByWaTemplateUid(Mockito.any()))
				.thenReturn(List.of(getwaUiMetaDtum(getWaTemplate(1))));
		when(wardbMetadataRepository.findByWaTemplateUid(Mockito.any()))
				.thenReturn(List.of(getWaRdbMetadatum(getWaTemplate(1))));
		when(wanndMetadataRepository.findByWaTemplateUid(Mockito.any()))
				.thenReturn(List.of(getWaNndMetadatum(getWaTemplate(1))));
		when(waRuleMetaDataRepository.findByWaTemplateUid(Mockito.anyLong()))
				.thenReturn(List.of(getWaRuleMetadata(getWaTemplate(1))));

		when(templateRepository.save(Mockito.any()))
				.thenReturn(getWaTemplate(1));

		Template result = templateReader.saveTemplate(request,1L);
		assertNotNull(result);
	}

	private WaRuleMetadata getWaRuleMetadata(WaTemplate aPage) {
		WaRuleMetadata record = new WaRuleMetadata();
		record.setWaTemplateUid(aPage.getId());
		return record;
	}
	private WaNndMetadatum getWaNndMetadatum(WaTemplate aPage) {
		WaNndMetadatum record = new WaNndMetadatum();
		record.setWaTemplateUid(aPage);
		return record;
	}

	private WaRdbMetadatum getWaRdbMetadatum(WaTemplate aPage) {
		WaRdbMetadatum record = new WaRdbMetadatum();
		record.setWaTemplateUid(aPage);
		return record;
	}
	private WaUiMetadata getwaUiMetaDtum(WaTemplate aPage) {
		WaUiMetadata record = new WaUiMetadata();
		record.setWaTemplateUid(aPage);
		return record;
	}

	private PageCondMapping getMapping() {
		PageCondMapping mapping = new PageCondMapping();
		mapping.setAddTime(Instant.now());
		mapping.setAddUserId(10l);
		mapping.setConditionCd("1025");
		mapping.setLastChgTime(Instant.now());
		mapping.setLastChgUserId(1000l);
		mapping.setWaTemplateUid(getWaTemplate(1));
		return mapping;
	}
	
	 private Page<WaTemplate> getTemplatePage(int max, Pageable pageable) {
	        List<WaTemplate> set = new ArrayList<WaTemplate>();
	        for (int i = 0; i < max; i++) {
	            set.add(getWaTemplate(i));
	        } ;
	        return new PageImpl<>(set, pageable, set.size());

	    }

	private WaTemplate getWaTemplate(int i) {
		WaTemplate aWaTemplate = new WaTemplate();

		aWaTemplate.setId(1l);
		aWaTemplate.setTemplateNm("TemplateNm" +i);
		aWaTemplate.setTemplateType("Draft");
		aWaTemplate.setXmlPayload("Payload");
		aWaTemplate.setPublishVersionNbr(1);
		aWaTemplate.setFormCd("formCd");
		aWaTemplate.setConditionCd("conditionCd" +i);
		aWaTemplate.setBusObjType("BusObjType");
		aWaTemplate.setDatamartNm("DatamartNm");
		aWaTemplate.setRecordStatusCd("RecordStatusCd" +i);
		aWaTemplate.setRecordStatusTime(Instant.now());
		aWaTemplate.setLastChgTime(Instant.now());
		aWaTemplate.setLastChgUserId(1l);
		aWaTemplate.setLocalId("LocalId");
		aWaTemplate.setDescTxt("DescTxt");
		aWaTemplate.setPublishIndCd('T');
		aWaTemplate.setAddTime(Instant.now());
		aWaTemplate.setAddUserId(1l);
		aWaTemplate.setNndEntityIdentifier("NndEntityIdentifier");
		aWaTemplate.setParentTemplateUid(1l);
		aWaTemplate.setSourceNm("SourceNm");
		aWaTemplate.setTemplateVersionNbr(1);
		aWaTemplate.setVersionNote("VersionNote");

		return aWaTemplate;
	}

}
