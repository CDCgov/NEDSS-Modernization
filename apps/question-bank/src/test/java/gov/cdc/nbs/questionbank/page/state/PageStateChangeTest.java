package gov.cdc.nbs.questionbank.page.state;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadatum;
import gov.cdc.nbs.questionbank.entity.repository.WaTemplateRepository;
import gov.cdc.nbs.questionbank.entity.repository.WaUiMetadatumRepository;
import gov.cdc.nbs.questionbank.page.PageStateChanger;
import gov.cdc.nbs.questionbank.page.response.PageStateResponse;
import gov.cdc.nbs.questionbank.page.util.PageConstants;

 class PageStateChangeTest {

	@Mock
	private WaTemplateRepository templateRepository;
	
	@Mock
	private WaUiMetadatumRepository waUiMetadatumRepository;

	@InjectMocks
	PageStateChanger pageStateChanger;

	public PageStateChangeTest() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void pageStateUpdateTest() {
		Long requestId = 1l;
		WaTemplate before = getTemplate(requestId);
		when(templateRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(before));
		PageStateResponse response = pageStateChanger.savePageAsDraft(requestId);
		assertEquals(requestId, response.getTemplateId());
		assertEquals(HttpStatus.OK, response.getStatus());
		assertEquals(PageConstants.SAVE_DRAFT_SUCCESS, response.getMessage());

	}

	@Test
	void pageStateExceptionTest() {
		Long requestId = 1l;
		final String message = "Could not find page invalid id provided";
		when(templateRepository.findById(Mockito.anyLong())).thenThrow(new IllegalArgumentException(message));
		PageStateResponse response = pageStateChanger.savePageAsDraft(requestId);
		assertEquals(requestId, response.getTemplateId());
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatus());
		assertEquals(message, response.getMessage());

	}

	@Test
	void pageStateNotFoundTest() {
		Long requestId = 1l;
		when(templateRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		PageStateResponse response = pageStateChanger.savePageAsDraft(requestId);
		assertEquals(requestId, response.getTemplateId());
		assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
		assertEquals(PageConstants.SAVE_DRAFT_FAIL, response.getMessage());
	}

		
	@Test
	void testCreateDraftCopy() {
		WaTemplate oldPage = getTemplate(10l);
		WaTemplate newPage = pageStateChanger.createDraftCopy(oldPage);
		assertEquals(newPage.getTemplateNm(), newPage.getTemplateNm());
		assertEquals("Draft", newPage.getTemplateType());
		assertEquals(0,newPage.getPublishVersionNbr().intValue());
		assertEquals('F',newPage.getPublishIndCd().charValue());

	}
	
	@Test
	void testCopyWaTemplateUIMetaData() {
		WaTemplate oldPage = getTemplate(10l);
		when(waUiMetadatumRepository.findAllByWaTemplateUid(Mockito.any()))
				.thenReturn(List.of(getwaUiMetaDtum(oldPage)));
		WaTemplate newPage = pageStateChanger.createDraftCopy(oldPage);
		List<WaUiMetadatum> result = pageStateChanger.copyWaTemplateUIMetaData(oldPage, newPage);
		assertNotNull(result);
		assertEquals(newPage.getId(), result.get(0).getId());

	}
	
	private WaUiMetadatum getwaUiMetaDtum(WaTemplate aPage) {
		WaUiMetadatum record = new WaUiMetadatum();
		record.setWaTemplateUid(aPage);
		return record;
	}


	private WaTemplate getTemplate(Long id) {
		WaTemplate template = new WaTemplate();
		template.setId(id);
		template.setTemplateType("Published");
		template.setPublishVersionNbr(1);
		template.setPublishIndCd('T');
		return template;
	}

}
