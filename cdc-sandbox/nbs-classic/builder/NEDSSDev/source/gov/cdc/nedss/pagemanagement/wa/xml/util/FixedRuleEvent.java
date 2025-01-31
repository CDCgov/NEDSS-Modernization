package gov.cdc.nedss.pagemanagement.wa.xml.util;

/**
 * FixedRuleEvent - This is called by MarshalPageXML and MarshalPageRules to add rule logic
 * for hardcoded rules to the XML. It was created for the STD 4.5a enhancement.
 *  
 * @author Gregory Tucker
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: Leidos</p>
 * FixedRuleEvent.java
 * @version 0.9
 */


public class FixedRuleEvent {
	private String questionId;
	private String onLoadEvent;
	private String onSubmitEvent;
	private String onSubmitErrorMsg;
	private String onChangeEvent;
	private String onBlurEvent;
	private String onKeyUpEvent;
	private String onKeyDownEvent;

	public String getQuestionId() {
		return questionId;
	}
	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}
	public String getOnLoadEvent() {
		return onLoadEvent;
	}
	public void setOnLoadEvent(String onLoadEvent) {
		this.onLoadEvent = onLoadEvent;
	}
	public String getOnSubmitEvent() {
		return onSubmitEvent;
	}
	public void setOnSubmitEvent(String onSubmitEvent) {
		this.onSubmitEvent = onSubmitEvent;
	}
	public String getOnSubmitErrorMsg() {
		return onSubmitErrorMsg;
	}
	public void setOnSubmitErrorMsg(String onSubmitErrorMsg) {
		this.onSubmitErrorMsg = onSubmitErrorMsg;
	}	
	public String getOnChangeEvent() {
		return onChangeEvent;
	}
	public void setOnChangeEvent(String onChangeEvent) {
		this.onChangeEvent = onChangeEvent;
	}
	public String getOnBlurEvent() {
		return onBlurEvent;
	}
	public void setOnBlurEvent(String onBlurEvent) {
		this.onBlurEvent = onBlurEvent;
	}
	public String getOnKeyUpEvent() {
		return onKeyUpEvent;
	}
	public void setOnKeyUpEvent(String onKeyUpEvent) {
		this.onKeyUpEvent = onKeyUpEvent;
	}
	public String getOnKeyDownEvent() {
		return onKeyDownEvent;
	}
	public void setOnKeyDownEvent(String onKeyDownEvent) {
		this.onKeyDownEvent = onKeyDownEvent;
	}

}
