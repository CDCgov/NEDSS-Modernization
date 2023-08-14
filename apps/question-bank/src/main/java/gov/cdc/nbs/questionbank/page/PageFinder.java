package gov.cdc.nbs.questionbank.page;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.entity.repository.PageCondMappingRepository;
import gov.cdc.nbs.questionbank.entity.repository.WaTemplateRepository;
import gov.cdc.nbs.questionbank.entity.repository.WaUiMetadataRepository;
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
	private WaUiMetadataRepository waUiMetadatumRepository;
	
	
	public PageDetailResponse getPageDetails(Long pageId) {
		

		// Get Page
		Optional<WaTemplate> page = getPageDetail(pageId);
		if(page.isPresent()) {
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
		List<WaUiMetadata> result = new ArrayList<WaUiMetadata>();
		
		return result;
	}

}
