package gov.cdc.nedss.webapp.nbs.action.util;

import gov.cdc.nedss.webapp.nbs.form.pam.FormField;

import java.util.Collection;
import java.util.Map;

public class VaricellaSpecificRules extends RuleConstants{ 
	
	public Collection<Object>  handleRule1150(Collection<Object> errColl, Map<Object,Object> formFieldMap,Map answerArrayMap) {
		FormField ffVAR108 = (FormField) formFieldMap.get(VAR108);
		FormField ffVAR110 = (FormField) formFieldMap.get(VAR110);
		FormField ffVAR112 = (FormField) formFieldMap.get(VAR112);

		// check the date format of the form fields
		RulesEngineUtil util = new RulesEngineUtil();
		if ((ffVAR112.getValue() != null && !ffVAR112.getValue().equals(""))||
			
		 (ffVAR110.getValue() != null && !ffVAR110.getValue().equals("")) || (ffVAR108.getValue() != null && 
				 !ffVAR108.getValue().equals(""))) {
			         int result = this.addFields(ffVAR108, ffVAR110, ffVAR112);
		    
					if (result >= 50) {
					String errorMessage = ERR206;
					ffVAR108.getErrorMessage().add(errorMessage);
					ffVAR108.setErrorStyleClass(RuleConstants.REQUIRED_FIELD_CLASS);
					ffVAR110.setErrorStyleClass(RuleConstants.REQUIRED_FIELD_CLASS);
					ffVAR112.setErrorStyleClass(RuleConstants.REQUIRED_FIELD_CLASS);
					errColl.add(ffVAR108);
					errColl.add(ffVAR110);
					errColl.add(ffVAR112);
				}

			}
		return errColl;
	}
	public Collection<Object>  handleRule1244(Collection<Object> errColl, Map<Object,Object> formFieldMap,
			Map answerArrayMap) {
		FormField ffVAR108 = (FormField) formFieldMap.get(VAR108);
		FormField ffVAR110 = (FormField) formFieldMap.get(VAR110);
		FormField ffVAR112 = (FormField) formFieldMap.get(VAR112);
		FormField ffVAR163 = (FormField) formFieldMap.get(VAR163);

		// check the date format of the form fields
		RulesEngineUtil util = new RulesEngineUtil();
		if ((ffVAR112.getValue() != null && !ffVAR112.getValue().equals(""))
				||

				(ffVAR110.getValue() != null && !ffVAR110.getValue().equals(""))
				|| (ffVAR108.getValue() != null && !ffVAR108.getValue().equals(
						""))) {
			int result = this.addFields(ffVAR108, ffVAR110, ffVAR112);

			if (ffVAR163.getValue() != null && !ffVAR163.getValue().equals("")) {

				if (result != Integer.parseInt((String) ffVAR163.getValue())) {

					String errorMessage = ERR210;
					ffVAR108.getErrorMessage().add(errorMessage);
					ffVAR108
							.setErrorStyleClass(RuleConstants.REQUIRED_FIELD_CLASS);
					ffVAR110
							.setErrorStyleClass(RuleConstants.REQUIRED_FIELD_CLASS);
					ffVAR112
							.setErrorStyleClass(RuleConstants.REQUIRED_FIELD_CLASS);
					ffVAR163
							.setErrorStyleClass(RuleConstants.REQUIRED_FIELD_CLASS);
					errColl.add(ffVAR108);
					errColl.add(ffVAR110);
					errColl.add(ffVAR112);
					errColl.add(ffVAR163);
				}
			}

		}
		return errColl;
	}
	


		
	


	private int addFields(FormField ff1, FormField ff2, FormField ff3) {
		int result = 0;
		int val1 = 0;
		int val2 = 0;
		int val3 = 0;
		if(ff1 != null && ff1.getValue() != null && !ff1.getValue().equals(""))
			val1 = Integer.parseInt((String) ff1.getValue());
		if(ff2 != null && ff2.getValue() != null && !ff2.getValue().equals(""))
			val2 = Integer.parseInt((String) ff2.getValue());
		if(ff3 != null && ff3.getValue() != null && !ff3.getValue().equals(""))
			val3 = Integer.parseInt((String) ff3.getValue());

		result = val1 + val2 + val3;
		return result;

	}
}
