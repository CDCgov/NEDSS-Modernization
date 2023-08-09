package gov.cdc.nbs.questionbank.page;

import java.util.List;

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
		return null;
	}
	
	
	
	public WaTemplate getPageDetail(Long pageId) {
		
	}
	
	public List<WaUiMetadata> getComponentForPage(WaTemplate page, Long componentType) {
		
	}

}
