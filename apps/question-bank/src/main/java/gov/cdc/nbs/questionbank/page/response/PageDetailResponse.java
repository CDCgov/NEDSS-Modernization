package gov.cdc.nbs.questionbank.page.response;

import java.util.List;

public sealed interface PageDetailResponse {
	long id();
	
	
	public record PagedDetail (
	long id,
	String Name,
	String pageDescription,
	List<PageTab> pageTabs,
	List<PageRule> pageRules
			
	) implements PageDetailResponse {
		
	}
	
	public record PageTab(
			long id,
			 String name,
			 String visible,
			 List<PageSection> tabSections
			) implements PageDetailResponse{
		
	}
	
	public record PageSection (
			long id,
			String name,
			String visible,
			List<PageSubSection> sectionSubSections
			)implements PageDetailResponse {
		
	}
	
	public record PageSubSection (
			long id,
			String name,
			String visible,
			List<PageDetailResponse.PageQuestion> pageQuestions
			)implements PageDetailResponse {
		
	}
	
	public record PageQuestion (
			long id,
			String questionType,
			String questionIdentifier,
			String name,
			String subGroup,
			String description,
			Character coInection,
			String dataType,
			String mask,
			boolean allowFutureDates,
			String labelOnScreen,
			String questionToolTip,
			String dispay,
			String enabled,
			String required,
			String defaultLabelInReport
			)implements PageDetailResponse  {
		
	}
	
	public record PageRule (
		long id,
		long pageId,
		String logic,
		String values,
		String function, //rule_cd
		String sourceField,
		String targetField
		
	) implements PageDetailResponse  {}
	

	

}
