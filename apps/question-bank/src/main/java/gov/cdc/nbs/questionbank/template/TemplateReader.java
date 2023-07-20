package gov.cdc.nbs.questionbank.template;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.repository.WaTemplateRepository;
import gov.cdc.nbs.questionbank.template.request.TemplateSearchRequest;
import gov.cdc.nbs.questionbank.template.response.Template;

@Service
public class TemplateReader {

	@Autowired
	private WaTemplateRepository templateRepository;

	public Page<Template> findAllTemplates(Pageable pageable) {
		Page<WaTemplate> templates = templateRepository.findAll(pageable);
		List<Template> results = toTempate(templates);
		return new PageImpl<>(results, pageable, templates.getTotalElements());
	}

	public Page<Template> searchTemplate(TemplateSearchRequest search, Pageable pageable) {

		List<Template> pageResult = new ArrayList<>();
		if (simpleSearch(search)) {
			Optional<WaTemplate> result = templateRepository.findByIdAndTemplateTypeIn(search.getId(),
					search.getTemplateType());
			if (result.isPresent()) {
				pageResult.add(convertTemplate(result.get()));
			}
			return new PageImpl<>(pageResult, pageable, pageResult.size());

		} else {
			Page<WaTemplate> result= templateRepository.searchTemplate(search.getId(), search.getTemplateNm(),
					search.getConditionCd(), search.getDataMartNm(), search.getRecordStatusCd(),
					search.getTemplateType(), pageable);
			pageResult = toTempate(result);
			
			return new PageImpl<>(pageResult, pageable, result.getTotalElements());

		}
	}

	public boolean simpleSearch(TemplateSearchRequest search) {
		return ((search.getId() > 0l && search.getTemplateType() != null) && search.getConditionCd() == null
				&& search.getDataMartNm() == null && search.getRecordStatusCd() == null
				&& search.getTemplateNm() == null);
	}

	private List<Template> toTempate(Page<WaTemplate> templates) {
		List<Template> results = new ArrayList<>();

		for (WaTemplate aWaTemplate : templates.getContent()) {
			results.add(convertTemplate(aWaTemplate));
		}

		return results;
	}

	private Template convertTemplate(WaTemplate aWaTemplate) {
		return new Template(
				aWaTemplate.getId(),
				aWaTemplate.getTemplateNm(),
				aWaTemplate.getTemplateType(),
				aWaTemplate.getXmlPayload(),
				aWaTemplate.getPublishVersionNbr(),
				aWaTemplate.getFormCd(),
				aWaTemplate.getConditionCd(),
				aWaTemplate.getBusObjType(),
				aWaTemplate.getDatamartNm(),
				aWaTemplate.getRecordStatusCd(),
				aWaTemplate.getRecordStatusTime(),
				aWaTemplate.getLastChgTime(),
				aWaTemplate.getLastChgUserId(),
				aWaTemplate.getLocalId(),
				aWaTemplate.getDescTxt(),
				aWaTemplate.getPublishIndCd(),
				aWaTemplate.getAddTime(),
				aWaTemplate.getAddUserId(),
				aWaTemplate.getNndEntityIdentifier(),
				aWaTemplate.getParentTemplateUid(),
				aWaTemplate.getSourceNm(),
				aWaTemplate.getTemplateVersionNbr(),
				aWaTemplate.getVersionNote()
				);
	}

}
