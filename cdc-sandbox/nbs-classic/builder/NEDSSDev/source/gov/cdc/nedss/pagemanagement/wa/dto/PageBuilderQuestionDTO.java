package gov.cdc.nedss.pagemanagement.wa.dto;

/**
 * This is used to transfer only the relevant value for display purposes on the 
 * page builder UI. Fields in this class match the fields in the WaQuestionDT object 
 * and are populated from it. 
 */
public class PageBuilderQuestionDTO {
	private static final long serialVersionUID = 1L;
	
	private Long waQuestionUid;
	private String defaultDisplayControl;
	private String questionLabel;
	public String getDefaultDisplayControl() {
		return defaultDisplayControl;
	}
	public void setDefaultDisplayControl(String defaultDisplayControl) {
		this.defaultDisplayControl = defaultDisplayControl;
	}
	public String getQuestionLabel() {
		return questionLabel;
	}
	public void setQuestionLabel(String questionLabel) {
		this.questionLabel = questionLabel;
	}
	public Long getWaQuestionUid() {
		return waQuestionUid;
	}
	public void setWaQuestionUid(Long waQuestionUid) {
		this.waQuestionUid = waQuestionUid;
	}
}