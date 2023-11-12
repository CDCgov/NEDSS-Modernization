package gov.cdc.nbs.questionbank.template;

import java.io.StringWriter;
import java.time.Instant;
import java.util.*;

import gov.cdc.nbs.questionbank.entity.*;
import gov.cdc.nbs.questionbank.entity.pagerule.WaRuleMetadata;
import gov.cdc.nbs.questionbank.entity.repository.*;
import gov.cdc.nbs.questionbank.pagerules.repository.WaRuleMetaDataRepository;
import gov.cdc.nbs.questionbank.question.exception.TemplateCreationException;
import gov.cdc.nbs.questionbank.template.request.PageElements;
import gov.cdc.nbs.questionbank.template.request.PageManagement;
import gov.cdc.nbs.questionbank.template.request.SaveTemplateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import gov.cdc.nbs.questionbank.template.request.TemplateSearchRequest;
import gov.cdc.nbs.questionbank.template.response.Template;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

@Component
public class TemplateReader {

	private final WaTemplateRepository templateRepository;

	private final PageCondMappingRepository pageCondMappingRepository;

	private final WaUiMetadataRepository uiMetadataRepository;
	private final WANNDMetadataRepository wanndMetadataRepository;
	private final WARDBMetadataRepository wardbMetadataRepository;
	private final WaRuleMetaDataRepository waRuleMetaDataRepository;

	public TemplateReader(final WaTemplateRepository templateRepository,final PageCondMappingRepository pageCondMappingRepository,
						  final WaUiMetadataRepository uiMetadataRepository,final WANNDMetadataRepository wanndMetadataRepository,
						  final WARDBMetadataRepository wardbMetadataRepository,final WaRuleMetaDataRepository waRuleMetaDataRepository){
		this.templateRepository=templateRepository;
		this.pageCondMappingRepository=pageCondMappingRepository;
		this.uiMetadataRepository=uiMetadataRepository;
		this.wanndMetadataRepository=wanndMetadataRepository;
		this.wardbMetadataRepository=wardbMetadataRepository;
		this.waRuleMetaDataRepository=waRuleMetaDataRepository;
	}

	public Page<Template> findAllTemplates(Pageable pageable) {
		Page<WaTemplate> templates = templateRepository.findAll(pageable);
		List<Template> results = toTempate(templates);
		return new PageImpl<>(results, pageable, templates.getTotalElements());
	}

	public Page<Template> searchTemplate(TemplateSearchRequest search, Pageable pageable) {

		List<Template> pageResult = new ArrayList<>();
		if (simpleSearch(search)) {
			Optional<WaTemplate> result = templateRepository.findByIdAndTemplateTypeIn(search.id(),
					search.templateType());
			if (result.isPresent()) {
				pageResult.add(convertTemplate(result.get()));
			}
			return new PageImpl<>(pageResult, pageable, pageResult.size());

		} else {
			Page<WaTemplate> result= templateRepository.searchTemplate(search.id(), search.templateNm(),
					search.conditionCd(), search.dataMartNm(), search.recordStatusCd(),
					search.templateType(), pageable);
			pageResult = toTempate(result);
			
			return new PageImpl<>(pageResult, pageable, result.getTotalElements());

		}
	}

	public boolean simpleSearch(TemplateSearchRequest search) {
		return (( search.id()!=null && search.id() > 0l && search.templateType() != null) && search.conditionCd() == null
				&& search.dataMartNm() == null && search.recordStatusCd() == null
				&& search.templateNm() == null);
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
				aWaTemplate.getRecordStatusCd(),
				aWaTemplate.getLastChgTime(),
				aWaTemplate.getLastChgUserId(),
				aWaTemplate.getDescTxt(),
				aWaTemplate.getParentTemplateUid(),
				aWaTemplate.getSourceNm()
				);
	}

	public Template saveTemplate(SaveTemplateRequest request,Long userId)  {
		Optional<WaTemplate> templateOptional = templateRepository.findById(request.waTemplateUid());
		if(templateOptional.isPresent()) {
			WaTemplate template = templateOptional.get();
			String conditionCode = template.getConditionCd();
			List<PageCondMapping> pageCodeMappings = pageCondMappingRepository.findByWaTemplateUidAndConditionCd(template,
					conditionCode);
			List<WaUiMetadata> waUiMetadataList = uiMetadataRepository.findByWaTemplateUid(template);
			List<WaRdbMetadatum> waRdbMetadatumList = wardbMetadataRepository.findByWaTemplateUid(template);
			List<WaNndMetadatum> waNndMetadatumList = wanndMetadataRepository.findByWaTemplateUid(template);
			PageElements pageElements = new PageElements(waUiMetadataList, waRdbMetadatumList, waNndMetadatumList);
			List<WaRuleMetadata> waRuleMetadataList = waRuleMetaDataRepository.findByWaTemplateUid(template.getId());
			PageManagement pageManagement = new PageManagement(template, pageCodeMappings, pageElements, waRuleMetadataList);
			String xmlPayLoad = marshallXmlPayLoad(pageManagement);

			WaTemplate waTemplateDT = new WaTemplate();
			waTemplateDT.setXmlPayload(xmlPayLoad);
			waTemplateDT.setTemplateType("TEMPLATE");
			waTemplateDT.setTemplateNm(request.templateName());
			waTemplateDT.setDescTxt(request.templateDescription());
			Instant aAddTime = Instant.now();
			waTemplateDT.setLastChgTime(aAddTime);
			waTemplateDT.setLastChgUserId(userId);
			waTemplateDT.setAddTime(aAddTime);
			waTemplateDT.setAddUserId(userId);
			waTemplateDT.setFormCd(null);
			waTemplateDT.setConditionCd(null);
			waTemplateDT.setPublishIndCd('F');

			waTemplateDT.setPublishVersionNbr(template.getPublishVersionNbr());
			waTemplateDT.setBusObjType(template.getBusObjType());
			waTemplateDT.setDatamartNm(template.getDatamartNm());
			waTemplateDT.setNndEntityIdentifier(template.getNndEntityIdentifier());
			waTemplateDT.setParentTemplateUid(template.getParentTemplateUid());
			waTemplateDT.setRecordStatusCd(template.getRecordStatusCd());
			waTemplateDT.setRecordStatusTime(template.getRecordStatusTime());
			waTemplateDT.setSourceNm(template.getSourceNm());
			waTemplateDT.setVersionNote(template.getVersionNote());
			waTemplateDT.setConditionMappings(copyConditionMappings(template.getConditionMappings(), waTemplateDT));

			WaTemplate waTemplateDTn = templateRepository.save(waTemplateDT);
			return convertTemplate(waTemplateDTn);
		}else {
			throw new TemplateCreationException("Template not found");
		}

	}

	private String marshallXmlPayLoad(PageManagement pageManagement) {
		String xmlString = "";
		try {
			JAXBContext context = JAXBContext.newInstance(PageManagement.class);
			Marshaller m = context.createMarshaller();

			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE); // To format XML

			StringWriter sw = new StringWriter();
			m.marshal(pageManagement, sw);
			xmlString = sw.toString();

		} catch (JAXBException e) {
			throw new TemplateCreationException(e.getMessage());
		}

		return xmlString;
	}

	private Set<PageCondMapping> copyConditionMappings(Set<PageCondMapping> original, WaTemplate page) {
		if (original == null)
			return original;
		Set<PageCondMapping> copy = new HashSet<>();
		for (PageCondMapping con : original) {
			PageCondMapping aCopy = new PageCondMapping();
			aCopy.setAddTime(con.getAddTime());
			aCopy.setAddUserId(con.getAddUserId());
			aCopy.setConditionCd(con.getConditionCd());
			aCopy.setLastChgTime(con.getLastChgTime());
			aCopy.setLastChgUserId(con.getLastChgUserId());
			aCopy.setWaTemplateUid(page);
			copy.add(aCopy);

		}
		return copy;
	}
}
