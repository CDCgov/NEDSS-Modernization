package gov.cdc.nbs.questionbank.template.read;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import gov.cdc.nbs.questionbank.entity.repository.WaTemplateRepository;
import gov.cdc.nbs.questionbank.template.TemplateReader;
import gov.cdc.nbs.questionbank.template.request.TemplateSearchRequest;
import gov.cdc.nbs.questionbank.template.response.Template;

class TemplateReaderTest { 
	
 @Mock
 WaTemplateRepository templateRepository;
 
 
 @InjectMocks
 TemplateReader  templateReader;
 
 
 public TemplateReaderTest() { 
   MockitoAnnotations.openMocks(this);	 
 }
 
 void findAllTemplates() {
	 Pageable pageable = Pageable.ofSize(25);
	 Page<Template> result = templateReader.findAllTemplates(pageable);
	 
 }
 
 @Test
 void searchTemplateBasic() {
	 
 }
 
 @Test
 void searchTemplateSimple() {
	 
 }
 
 
 @Test
 void simpleSearch() {
	 TemplateSearchRequest search = new TemplateSearchRequest();
	 search.setId(1234l);
	 List<String> type = new ArrayList<String>();
	 type.add("Draft");
	 search.setTemplateType(type);
	 boolean result = templateReader.simpleSearch(search);
	 assertTrue(result);
	 search.setTemplateNm("templateNm");
	 result = templateReader.simpleSearch(search);
	 assertFalse(result);
 }

}
