package gov.cdc.nedss.webapp.nbs.form.pam;

import java.util.ArrayList;
import java.util.List;

public class FieldState {

	private static List<Object> emptyList = new ArrayList<Object> ();

	private boolean visible = true;
	private boolean enabled = true;
	private boolean required = true;
	private boolean futureDtInd = false;
	private List<?> values = null;
	private List<Object> styles = null;
	private String requiredIndClass;
	private List<Object> multiSelVals = new ArrayList<Object> ();
	

	/** Default constructor */
	public FieldState() {}

	/** Copy constructor (caution: shares lists!) */
	public FieldState(FieldState source) {
		visible = source.visible;
		enabled = source.enabled;
		required = source.required;
		values = source.values;
		styles = source.styles;
		futureDtInd = source.futureDtInd;
	}

	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public String getEnabledValue() {
		return Boolean.toString(isEnabled());
	}
	public void setEnabledValue(String value) {
		setEnabled(Boolean.valueOf(value).booleanValue());
	}
	public String getDisabledString() {
		return isEnabled() ? "InputFieldLabel" : "InputDisabledLabel";
	}
	public boolean isDisabled() {
		return !enabled;
	}

	public List getValues() {
		return (values == null) ? emptyList : values;
	}
	public void setValues(List<?>values) {
		this.values = values;
	}
	// Actions //

	public void enable() {
		setEnabled(true);
	}
	public void disable() {
		setEnabled(false);
	}
	public String getRequiredIndClass() {
		return requiredIndClass;
	}

	public void setRequiredIndClass(String requiredIndClass) {
		this.requiredIndClass = requiredIndClass;
	}

	public List<Object> getMultiSelVals() {
		return multiSelVals;
	}

	public void setMultiSelVals(List<Object>multiSelVals) {
		this.multiSelVals = multiSelVals;
	}

	public boolean isFutureDtInd() {
		return futureDtInd;
	}

	public void setFutureDtInd(boolean futureDtInd) {
		this.futureDtInd = futureDtInd;
	}
	public String getVisibleString() {
		return isVisible()? "fieldName":"InVisible" ;
	}
	
}
