package gov.cdc.nedss.webapp.nbs.form.ruleadmin;

import gov.cdc.nedss.webapp.nbs.form.pam.FieldState;

import java.util.ArrayList;
import java.util.List;

/**
 * @author habraham2
 *
 */
public class FormField {
	
	private Object defaultValue;
	private Object value;
	private Object fieldId;
	private Object fieldType;
	private ArrayList<Object> errorMessage = new ArrayList<Object> ();
	private Integer tabId;
	private String codeSetNm;
	private String label;
	private String tooltip;
	private String errorStyleClass;
	private String questionRequiredNnd;
	private FieldState state = new FieldState();
	public Object getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(Object defaultValue) {
		this.defaultValue = defaultValue;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public FieldState getState() {
		return state;
	}
	public void setState(FieldState state) {
		this.state = state;
	}
	public Object getFieldId() {
		return fieldId;
	}
	public void setFieldId(Object fieldId) {
		this.fieldId = fieldId;
	}
	public Object getFieldType() {
		return fieldType;
	}
	public void setFieldType(Object fieldType) {
		this.fieldType = fieldType;
	}
	public Integer getTabId() {
		return tabId;
	}
	public void setTabId(Integer tabId) {
		this.tabId = tabId;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getTooltip() {
		return tooltip;
	}
	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}
	public String getCodeSetNm() {
		return codeSetNm;
	}
	public void setCodeSetNm(String codeSetNm) {
		this.codeSetNm = codeSetNm;
	}
	public String getErrorStyleClass() {
		return errorStyleClass;
	}
	public void setErrorStyleClass(String errorStyleClass) {
		this.errorStyleClass = errorStyleClass;
	}
	public void setErrorMessage(ArrayList<Object>  errorMessage) {
		this.errorMessage = errorMessage;
	}
	public ArrayList<Object> getErrorMessage() {
		return errorMessage;
	}
	public String getQuestionRequiredNnd() {
		return questionRequiredNnd;
	}
	public void setQuestionRequiredNnd(String questionRequiredNnd) {
		this.questionRequiredNnd = questionRequiredNnd;
	}
	
	
}
