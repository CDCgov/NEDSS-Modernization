package gov.cdc.nbs.questionbank.page;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.repository.WaTemplateRepository;
import gov.cdc.nbs.questionbank.page.response.PageStateResponse;
import gov.cdc.nbs.questionbank.page.util.PageConstants;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PageStateChange {

	@Autowired
	private WaTemplateRepository templateRepository;

	public PageStateResponse savePageAsDraft(Long id) {
		PageStateResponse response = new PageStateResponse();
		try {
			Optional<WaTemplate> result = templateRepository.findById(id);
			if (result.isPresent()) {
				WaTemplate template = result.get();
				template.setTemplateType("Draft");
				template.setPublishVersionNbr(null);
				template.setPublishIndCd('F');
				templateRepository.save(template);
				response.setMessage(template.getId() + PageConstants.SAVE_DRAFT_SUCCESS);
				response.setStatus(HttpStatus.OK);
				response.setTemplateId(template.getId());

			} else {
				response.setMessage(PageConstants.SAVE_DRAFT_FAIL);
				response.setStatus(HttpStatus.NOT_FOUND);
				response.setTemplateId(id);
			}
		} catch (Exception e) {
			response.setMessage(e.getMessage());
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setTemplateId(id);

		}
		return response;
	}

}
