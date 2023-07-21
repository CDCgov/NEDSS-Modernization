package gov.cdc.nbs.questionbank.template.read;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import gov.cdc.nbs.questionbank.entity.Codeset;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.repository.WaTemplateRepository;
import gov.cdc.nbs.questionbank.template.TemplateReader;
import gov.cdc.nbs.questionbank.template.request.TemplateSearchRequest;
import gov.cdc.nbs.questionbank.template.response.Template;

class TemplateReaderTest {

	@Mock
	WaTemplateRepository templateRepository;

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
