package gov.cdc.nbs.questionbank.page;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.addtab.WaUiMetadata;
import gov.cdc.nbs.questionbank.entity.repository.PageCondMappingRepository;
import gov.cdc.nbs.questionbank.entity.repository.WaTemplateRepository;
import gov.cdc.nbs.questionbank.entity.repository.WaUiMetadatumRepository;
import gov.cdc.nbs.questionbank.page.response.PageDetailResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PageFinder {
	@Autowired
	private WaTemplateRepository templateRepository;

	@Autowired
	private PageCondMappingRepository pageConMappingRepository;
	
	@Autowired
	private WaUiMetadatumRepository waUiMetadatumRepository;
	
	
	public PageDetailResponse getPageDetails(Long pageId) {
		

		// Get Page
		Optional<WaTemplate> page = getPageDetail(pageId);
		if(page.isEmpty()) {
		// Get Page tabs
		
		// get Sections 
		
		// Get SubSections
		}
		else {
			
		}
		return null;
	}
	
	
	
	public Optional<WaTemplate> getPageDetail(Long pageId) {
		Optional<WaTemplate> page =	templateRepository.findById(pageId);
		return page;	
	}
	
	public List<WaUiMetadata> getComponentForPage(WaTemplate page, Long componentType) {
		
	}

}
