package gov.cdc.nbs.questionbank.page;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.entity.pagerule.WaRuleMetadata;
import gov.cdc.nbs.questionbank.entity.repository.WaTemplateRepository;
import gov.cdc.nbs.questionbank.entity.repository.WaUiMetadataRepository;
import gov.cdc.nbs.questionbank.page.exception.PageNotFoundException;
import gov.cdc.nbs.questionbank.page.response.PageDetailResponse;
import gov.cdc.nbs.questionbank.page.response.PageDetailResponse.PagedDetail;
import gov.cdc.nbs.questionbank.page.util.PageConstants;
import gov.cdc.nbs.questionbank.pagerules.repository.WaRuleMetaDataRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PageFinder {
	@Autowired
	private WaTemplateRepository templateRepository;

	@Autowired
	private WaUiMetadataRepository waUiMetadatumRepository;

	@Autowired
	private WaRuleMetaDataRepository waRuleMetaDataRepository;

	public PageDetailResponse.PagedDetail getPageDetails(Long pageId) {

		// Get Page
		Optional<WaTemplate> page = getPageDetail(pageId);
		if (page.isPresent()) {
			// Get Page tabs
			List<WaUiMetadata> pageTabs = getTabsForPage(page.get());
			List<PageDetailResponse.PageTab> tabs = new ArrayList<>();
			for (int i = 0; i < pageTabs.size(); i++) {
				List<PageDetailResponse.PageSection> sections = new ArrayList<>();
				Integer min = -1;
				Integer max = -1;
				if (i == pageTabs.size() - 1 && pageTabs.size() > 1) {
					min = pageTabs.get(i - 1).getOrderNbr();
					max = pageTabs.get(i).getOrderNbr();
				} else if (i == pageTabs.size() - 1 && pageTabs.size() == 1) {
					min = pageTabs.get(i).getOrderNbr();
					max = pageTabs.get(i).getOrderNbr() + 1;
				} else {
					min = pageTabs.get(i).getOrderNbr();
					max = pageTabs.get(i + 1).getOrderNbr();
				}
				// get Sections
				List<WaUiMetadata> tabSections = getChildComponents(page.get(), PageConstants.SECTION_COMPONENT, min,
						max);
				for (int j = 0; j < tabSections.size(); j++) {
					List<PageDetailResponse.PageSubSection> subSections = new ArrayList<>();
					Integer minSectionOrder = -1;
					Integer maxSectionOrder = -1;
					if (j == tabSections.size() - 1 && tabSections.size() > 1) {
						minSectionOrder = tabSections.get(j - 1).getOrderNbr();
						maxSectionOrder = tabSections.get(j).getOrderNbr();
					} else if (j == tabSections.size() - 1 && tabSections.size() == 1) {
						minSectionOrder = tabSections.get(j).getOrderNbr();
						maxSectionOrder = tabSections.get(j).getOrderNbr() + 1;
					} else {
						minSectionOrder = tabSections.get(j).getOrderNbr();
						maxSectionOrder = tabSections.get(j + 1).getOrderNbr();
					}
					// Get SubSections
					List<WaUiMetadata> SectionSubSections = getChildComponents(page.get(),
							PageConstants.SUB_SECTION_COMPONENT, minSectionOrder, maxSectionOrder);
					for (int k = 0; k < SectionSubSections.size(); k++) {
						Integer minSubSectionOrder = -1;
						Integer maxSubSectionOrder = -1;
						if (k == SectionSubSections.size() - 1 && SectionSubSections.size() > 1) {
							minSubSectionOrder = SectionSubSections.get(k - 1).getOrderNbr();
							maxSubSectionOrder = SectionSubSections.get(k).getOrderNbr();
						} else if (k == SectionSubSections.size() - 1 && SectionSubSections.size() == 0) {
							minSubSectionOrder = SectionSubSections.get(k).getOrderNbr();
							maxSubSectionOrder = SectionSubSections.get(k).getOrderNbr() + 1;
						} else {
							minSubSectionOrder = SectionSubSections.get(k).getOrderNbr();
							maxSubSectionOrder = SectionSubSections.get(k + 1).getOrderNbr();
						}

						// questions for subsection
						List<WaUiMetadata> generalQuestions = getChildComponents(page.get(),
								PageConstants.GEN_QUESTION_COMPONENT, minSubSectionOrder, maxSubSectionOrder);
						List<WaUiMetadata> specificQuestions = getChildComponents(page.get(),
								PageConstants.SPE_QUESTION_COMPONENT, minSubSectionOrder, maxSubSectionOrder);
						List<WaUiMetadata> questions = mergeOrderedQuetionLists(generalQuestions, specificQuestions);
						List<PageDetailResponse.PageQuestion> resultQuestions = buildQuestions(questions);
						PageDetailResponse.PageSubSection aSubSection = new PageDetailResponse.PageSubSection(
								SectionSubSections.get(k).getId(), SectionSubSections.get(k).getQuestionLabel(),
								SectionSubSections.get(k).getDisplayInd(), resultQuestions);
						subSections.add(aSubSection);
					} // End SubSection building

					PageDetailResponse.PageSection aSection = new PageDetailResponse.PageSection(
							tabSections.get(j).getId(), tabSections.get(j).getQuestionLabel(),
							tabSections.get(j).getDisplayInd(), subSections);
					sections.add(aSection);
				} // EndSection building
				PageDetailResponse.PageTab aTab = new PageDetailResponse.PageTab(pageTabs.get(i).getId(),
						pageTabs.get(i).getQuestionLabel(), pageTabs.get(i).getDisplayInd(), sections);
				tabs.add(aTab);
			} // End tab
			List<PagedDetail.PageRule> rules = getPageRules(page.get().getId());
			PageDetailResponse.PagedDetail response = new PageDetailResponse.PagedDetail(page.get().getId(),
					page.get().getTemplateNm(), page.get().getDescTxt(), tabs, rules);
			return response;
		} else {
			throw new PageNotFoundException(PageConstants.PAGE_NOT_EXISTS + pageId);
		}
	}
	
	public List<WaUiMetadata> getChildComponents(WaTemplate page, Long nbsUiComponentUid, Integer orderNmbrMin,
			Integer orderNmbrMax) {
		return waUiMetadatumRepository.findOrderedChildComponents(page.getId(), nbsUiComponentUid, orderNmbrMin,
				orderNmbrMax);
	}
	

	public List<WaUiMetadata> mergeOrderedQuetionLists(List<WaUiMetadata> questions1, List<WaUiMetadata> questions2) {
		if(questions1.isEmpty() && !questions2.isEmpty()) {
			return questions2;
		}
		if(!questions1.isEmpty() && questions2.isEmpty()) {
			return questions1;
		}
		if(questions1.isEmpty() && questions2.isEmpty()) {
			return new ArrayList<>();
		}
		Integer qOneLastItemOrder = questions1.get(questions1.size() - 1).getOrderNbr();
		Integer qTwoLastItemOrder = questions2.get(questions2.size() - 1).getOrderNbr();
		if (qOneLastItemOrder.compareTo(qTwoLastItemOrder) > 0) {
			questions2.addAll(questions1);
			return questions2;
		} else {
			questions1.addAll(questions2);
			return questions1;
		}
	}



	public List<PagedDetail.PageRule> getPageRules(Long pageId) {
		List<PagedDetail.PageRule> rules = new ArrayList<>();
		List<WaRuleMetadata> rawRules = waRuleMetaDataRepository.findByWaTemplateUid(pageId);
		for (WaRuleMetadata rawRule : rawRules) {
			PagedDetail.PageRule rule = new PagedDetail.PageRule(rawRule.getId(), pageId, rawRule.getLogic(),
					rawRule.getSourceValues(), rawRule.getRuleCd(), rawRule.getRuleDescText(), rawRule.getTargetType());
			rules.add(rule);
		}

		return rules;

	}
	
	private List<WaUiMetadata> getTabsForPage(WaTemplate page) {
		return waUiMetadatumRepository.findOrderedTabsForPage(page.getId(), PageConstants.TAB_COMPONENT);
	}

	
	private List<PageDetailResponse.PageQuestion> buildQuestions(List<WaUiMetadata> questions) {
		List<PageDetailResponse.PageQuestion> results = new ArrayList<>();
		int i = 0;
		while (i < questions.size()) {
			WaUiMetadata aQuestion = questions.get(i);
			results.add(new PageDetailResponse.PageQuestion(aQuestion.getId(), aQuestion.getQuestionType(),
					aQuestion.getQuestionIdentifier(), aQuestion.getQuestionNm(), aQuestion.getSubGroupNm(),
					aQuestion.getDescTxt(), aQuestion.getCoinfectionIndCd(), aQuestion.getDataType(),
					aQuestion.getMask(), toBoolean(aQuestion.getFutureDateIndCd(), 'T'), aQuestion.getQuestionLabel(),
					aQuestion.getQuestionToolTip(), aQuestion.getDisplayInd(), aQuestion.getEnableInd(),
					aQuestion.getRequiredInd(), aQuestion.getDefaultValue()));
			i++;
		}

		return results;
	}
	
	private Optional<WaTemplate> getPageDetail(Long pageId) {
		Optional<WaTemplate> page = templateRepository.findById(pageId);
		return page;
	}
	
	private boolean toBoolean(Character character, Character trueValue) {
		if (character == null) {
			return false;
		} else {
			return character.equals(trueValue);
		}

	}

}
