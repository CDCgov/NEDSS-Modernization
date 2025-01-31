package gov.cdc.nedss.webapp.nbs.action.util;

import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.RulesDT;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.SourceFieldDT;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.SourceValueDT;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.webapp.nbs.form.pam.FormField;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TBSpecificRules extends RuleConstants{
	static boolean iscalculated= false;
	
	 static final LogUtils logger = new LogUtils(TBSpecificRules.class.getName());
	/**
	 *  check if the sourceInd has multiple rule instances with same rule_name then discards other instances based on selected value
	 * @param ruleColl
	 * @param formFieldMap
	 * @param sourceInd
	 * @return
	 */
	public Collection<?>  handleTUB119Rule(Collection<?> ruleColl, Map<Object,Object> formFieldMap, String sourceInd) {

		FormField formField = (FormField) formFieldMap.get(sourceInd);
		String selectedValue = (String) formField.getValue();
		if(selectedValue != null && selectedValue.equals("260385009")) {
			  Collection<Object>  ruleCollection;
				try {
					boolean sameRuleName = false;
					ruleCollection  = new ArrayList<Object> ();
					ArrayList<Object> ruleNameList = new ArrayList<Object> ();
					ArrayList<Object> tempList = new ArrayList<Object> ();
					Iterator<?> ruleIter = ruleColl.iterator();
					String ruleName = null;
					while (ruleIter.hasNext()) {
						RulesDT rulesDT = (RulesDT) ruleIter.next();
						ruleName = rulesDT.getRuleName();
						ruleNameList.add(ruleName);
					}
					tempList.add(ruleName);
					sameRuleName = tempList.containsAll(ruleNameList);

					if (sameRuleName) {

						Iterator<?> iter = ruleColl.iterator();

						while (iter.hasNext()) {
							RulesDT rulesDT = (RulesDT) iter.next();
							Collection<Object>  sourceColl = rulesDT.getSourceColl();
							Iterator<Object> iter1 = sourceColl.iterator();
							while (iter1.hasNext()) {
								SourceFieldDT sourceFieldDT = (SourceFieldDT) iter1.next();
								Collection<Object>  sValueColl = sourceFieldDT.getSourceValueColl();
								Iterator<Object> iter2 = sValueColl.iterator();
								while (iter2.hasNext()) {
									SourceValueDT sourceValueDT = (SourceValueDT) iter2.next();
									String value = sourceValueDT.getSourceValue();
									if (selectedValue.equalsIgnoreCase(value))
										ruleCollection.add(rulesDT);
								}
							}

						}
						return ruleCollection;
					} else {
						return ruleColl;
					}
				} catch (Exception e) {
					logger.error("Exception in handleTUB119Rule: " + e.getMessage());
					e.printStackTrace();
					return ruleColl;
				}
		}
		return ruleColl;
	}
	

	
	
	private int getDrugRegimenYesCount(Map<Object, Object> formFieldMap) {
		int count = 0;
		int tubInitCount = 132;
		int tubFinalCount = 146;
		for (int i = tubInitCount; i <= tubFinalCount; i++) {

			if (((FormField) formFieldMap.get("TUB" + i + "")).getValue() != null
					&& ((FormField) formFieldMap.get("TUB" + i + ""))
							.getValue().equals(INITIAL_DRUG_REGIMEN_YES)) {
				count = count + 1;
			}
		}
		return count;
	}

	public ArrayList<Object> fireVercritRuleOnSubmit(String actionMode, Map<Object,Object> formFieldMap, String fieldId, Map<Object,Object> answerMap, Map<Object,Object> answerArrayMap) {
		ArrayList<Object> returnedList = new ArrayList<Object> ();
		String tub_233 = (String) ((FormField) formFieldMap.get(TUB233))
				.getValue();
		FormField fField = (FormField) formFieldMap.get(TUB266);
		String tub_266 = (String) ((FormField) formFieldMap.get(TUB266))
		.getValue();
		String tub_122 = (String) ((FormField) formFieldMap.get(TUB122))
		.getValue();
		String tub_130 = (String) ((FormField) formFieldMap.get(TUB130))
		.getValue();
		String tub_135 = (String) ((FormField) formFieldMap.get(TUB135))
		.getValue();
		String tub_120 = (String) ((FormField) formFieldMap.get(TUB120))
		.getValue();
		String tub_126 = (String) ((FormField) formFieldMap.get(TUB126))
		.getValue();
		String tub_119 = (String) ((FormField) formFieldMap.get(TUB119))
		.getValue();
		String tub_141 = (String) ((FormField) formFieldMap.get(TUB141))
		.getValue();
		String tub_144 = (String) ((FormField) formFieldMap.get(TUB144))
		.getValue();
		String tub_147 = (String) ((FormField) formFieldMap.get(TUB147))
		.getValue();
		String tub_150 = (String) ((FormField) formFieldMap.get(TUB150))
		.getValue();
		String tub_171 = (String) ((FormField) formFieldMap.get(TUB171))
		.getValue();
		String tub_172 = (String) ((FormField) formFieldMap.get(TUB172))
				.getValue();
		String tub_173 = (String) ((FormField) formFieldMap.get(TUB173))
				.getValue();
		String tub_174 = (String) ((FormField) formFieldMap.get(TUB174))
				.getValue();
		String tub_175 = (String) ((FormField) formFieldMap.get(TUB175))
				.getValue();
		String tub_176 = (String) ((FormField) formFieldMap.get(TUB176))
		.getValue();
		String tub_177 = (String) ((FormField) formFieldMap.get(TUB177))
		.getValue();
		String tub_178 = (String) ((FormField) formFieldMap.get(TUB178))
		.getValue();
		String tub_179 = (String) ((FormField) formFieldMap.get(TUB179))
		.getValue();
		String tub_180 = (String) ((FormField) formFieldMap.get(TUB180))
				.getValue();
		String tub_181 = (String) ((FormField) formFieldMap.get(TUB181))
				.getValue();
		String tub_182 = (String) ((FormField) formFieldMap.get(TUB182))
				.getValue();
		String tub_183 = (String) ((FormField) formFieldMap.get(TUB183))
				.getValue();
		String tub_184 = (String) ((FormField) formFieldMap.get(TUB184))
		.getValue();
		String tub_185 = (String) ((FormField) formFieldMap.get(TUB185))
		.getValue();
		String tub_186 = (String) ((FormField) formFieldMap.get(TUB186))
		.getValue();
		String tub_187 = (String) ((FormField) formFieldMap.get(TUB187))
		.getValue();
		String tub_188 = (String) ((FormField) formFieldMap.get(TUB188))
				.getValue();
		String tub_190 = (String) ((FormField) formFieldMap.get(TUB190))
				.getValue();		
		
		String[] tubs={tub_171,tub_172,tub_173,tub_174,tub_175,tub_176,tub_177,tub_178,tub_179,tub_180,tub_181,tub_182,tub_183,tub_184,tub_185,tub_186,tub_187,tub_188,tub_190};
		boolean rule5_status = false;
		boolean case_verified= false;
		boolean isSiteNotStarted=false;
		boolean isPPL = true;
		int j= -1;
		if(tubs != null && tubs.length > 0 ){
			for(int i=0;i<tubs.length;i++){
				 if(tubs[i] != null && tubs[i].equals("Y")){				 
				      j=i;
				     break;
				 }
			}
			for(int i1=0;i1<tubs.length;i1++){
				    	 if(i1==j){
				    	     // i1++;	
				    	      continue;
				    	 }
				    	 if(tubs[i1] != null && tubs[i1].equals("Y")){
				    		 rule5_status= true;
				    		 break;
				    	 }
				    		 
			}			
		}
		fField.getState().setValues(
				(ArrayList<Object> ) CachedDropDowns.getCodedValueForType(fField
						.getCodeSetNm()));	
		
		if(answerArrayMap != null && answerArrayMap.size()>0 && answerArrayMap.get(TUB119) != null){
			String[]  tub199s = (String [])answerArrayMap.get(TUB119);		
		for(int i=0;i<tub199s.length;i++){
			if(tub199s[i] == null || (tub199s[i] != null && tub199s[i].equals(ADDITIONAL_SITE_NOT_STATED)))
				isSiteNotStarted = true;
			if(tub199s[i] != null && ((tub199s[i].equals(ADDITIONAL_SITE_PULMONARY))||(tub199s[i].equals(ADDITIONAL_SITE_PLEURAL)) || (tub199s[i].equals(ADDITIONAL_SITE_LYMPH)))){
			   if((tub_141 != null && tub_141.equals(INITIAL_CHEST_RADIOGRAPH_ABNORMAL))  || (tub_144 != null && tub_144.equals(INITIAL_CHEST_CT_SCAN_ABNORMAL)))	
				isPPL = true;
			   else
				isPPL = false;  
			}
			
		}
		}
			if(tub_233 != null && REASON_THERAPY_STOPED_NOT_TB.equals(tub_233)){
				((FormField) formFieldMap.get(TUB266)).setValue(VERCRIT_NOT_VERIFIED);
				((FormField) formFieldMap.get(INV163))
						.setValue(CASE_STATUS_NOT_A_CASE);
				// set case verification to answer map
				answerMap.put(TUB266, VERCRIT_NOT_VERIFIED);
				// also set case status to answer map
				answerMap.put(INV163, CASE_STATUS_NOT_A_CASE);
				answerMap.put(TUB278, "");
				if(answerMap.get(TUB108)!=null && ((String)answerMap.get(TUB108)).equals(COUNT_AS_TB_CASE))
				answerMap.put(TUB108, "");
				answerMap.put(TUB110, "");
				answerMap.put(INV165, "");
				answerMap.put(INV166, "");
				case_verified = true;
				iscalculated = true;
			
		    } else if ( !case_verified &&(tub_122  != null && (SPUTUM_CULTURE_POSITIVE.equals(tub_122))) || (tub_130  != null && (TISSUE_CULTURE_POSITIVE.equals(tub_130)))) {
		    	
		    	((FormField) formFieldMap.get(TUB266)).setValue(VERCRIT_POSITIVE);
				((FormField) formFieldMap.get(INV163))
						.setValue(CASE_STATUS_CONFIRMED);
				// set case verification to answer map
				answerMap.put(TUB266, VERCRIT_POSITIVE);
				// also set case status to answer map
				answerMap.put(INV163, CASE_STATUS_CONFIRMED);
				answerMap.put(TUB278, "");
				case_verified = true;
				
				
		   } else if (!case_verified && (tub_135 != null && NUCLEIC_ACID_AMPLIFICATION_POSITIVE.equals(tub_135))) {
			  //needs to be changed from vercuit positive to vercrit positive naa
			   ((FormField) formFieldMap.get(TUB266)).setValue(VERCRIT_POSITIVE_NAA);
				((FormField) formFieldMap.get(INV163))
						.setValue(CASE_STATUS_CONFIRMED);
				// set case verification to answer map
				answerMap.put(TUB266, VERCRIT_POSITIVE_NAA);
				// also set case status to answer map
				answerMap.put(INV163, CASE_STATUS_CONFIRMED);
				answerMap.put(TUB278, "");
				case_verified = true;
				
				
				
		   } else if (!case_verified && ((tub_120 != null && SPUTUM_SMEAR_POSITIVE.equals(tub_120)) || (tub_126 != null && SMEAR_PATHOLOGY_CYTOLOGY_POSITIVE.equals(tub_126))) 
			   && (tub_122 != null && (tub_122.equals(SPUTUM_CULTURE_NOT_DONE) || tub_122.equals(SPUTUM_CULTURE_UNK)))
			   && (tub_130 != null && (tub_130.equals(TISSUE_CULTURE_NOT_DONE) || tub_130.equals(TISSUE_CULTURE_UNK)))
			   &&(tub_135 != null && (tub_135.equals(NUCLEIC_ACID_AMPLIFICATION_NOT_DONE) || tub_135.equals(NUCLEIC_ACID_AMPLIFICATION_UNK) || tub_135.equals(NUCLEIC_ACID_AMPLIFICATION_INDETERMINATE)))){	   
			  
				   ((FormField)formFieldMap.get(TUB266)).setValue(VERCRIT_POSITIVE_SMEAR);
					((FormField)formFieldMap.get(INV163)).setValue(CASE_STATUS_CONFIRMED);
					// set case verification to answer map
					answerMap.put(TUB266, VERCRIT_POSITIVE_SMEAR);
					// also set case status to answer map
					answerMap.put(INV163, CASE_STATUS_CONFIRMED);
					answerMap.put(TUB278, "");
					case_verified = true;
					
			   }
		  
	    else if (!case_verified && (answerArrayMap != null && answerArrayMap.get(TUB119) != null && !isSiteNotStarted)  
			   && (tub_122 != null && (tub_122.equals(SPUTUM_CULTURE_NOT_DONE) || tub_122.equals(SPUTUM_CULTURE_UNK) || tub_122.equals(SPUTUM_CULTURE_NEGATIVE)))
			   && (tub_130 != null && (tub_130.equals(TISSUE_CULTURE_NOT_DONE) || tub_130.equals(TISSUE_CULTURE_UNK) || tub_130.equals(TISSUE_CULTURE_NEGATIVE)))
			   && (tub_135 != null && (tub_135.equals(NUCLEIC_ACID_AMPLIFICATION_NOT_DONE) || tub_135.equals(NUCLEIC_ACID_AMPLIFICATION_UNK) || tub_135.equals(NUCLEIC_ACID_AMPLIFICATION_NEGATIVE) || tub_135.equals(NUCLEIC_ACID_AMPLIFICATION_INDETERMINATE)))	   
			   && (isPPL) 
			   //&& ((tub_141 != null && tub_141.equals(INITIAL_CHEST_RADIOGRAPH_ABNORMAL))  || (tub_144 != null && tub_144.equals(INITIAL_CHEST_CT_SCAN_ABNORMAL)))
			   && ((tub_147 != null && tub_147.equals(SKIN_TEST_POSITIVE))  || (tub_150 != null && tub_150.equals(INTERFERON_GAMMA_RELEASE_POSITIVE)))	
			   && (rule5_status))	{  
	    	
		    	((FormField)formFieldMap.get(TUB266)).setValue(VERCRIT_CLINICAL_DEFINATION);
				((FormField)formFieldMap.get(INV163)).setValue(CASE_STATUS_CONFIRMED);
				// set case verification to answer map
				answerMap.put(TUB266, VERCRIT_CLINICAL_DEFINATION);
				// also set case status to answer map
				answerMap.put(INV163, CASE_STATUS_CONFIRMED);
				answerMap.put(TUB278, "");
				case_verified = true;
	    	
	    	
	   }else{     
				   if(tub_266==null || (tub_266!=null && !(tub_266.equals(VERCRIT_NOT_VERIFIED) ||tub_266.equals(VERCRIT_PRV_VERIFIED)))){
						((FormField) formFieldMap.get(TUB266)).setValue(VERCRIT_SUSPECT);
						((FormField) formFieldMap.get(INV163))
								.setValue(CASE_STATUS_SUSPECT);
						// set case verification to answer map
						answerMap.put(TUB266, VERCRIT_SUSPECT);
						// also set case status to answer map
						answerMap.put(INV163, CASE_STATUS_SUSPECT);
						answerMap.put(TUB278, "");
						
						if(answerMap.get(TUB108)!=null && ((String)answerMap.get(TUB108)).equals(COUNT_AS_TB_CASE))
							answerMap.put(TUB108, "");
						answerMap.put(TUB110, "");
						answerMap.put(INV165, "");
						answerMap.put(INV166, "");
						iscalculated = false;
						}
						else{
							((FormField) formFieldMap.get(TUB266)).setValue(tub_266);
							answerMap.put(TUB266, tub_266);
							if(answerMap.get(INV163)==null)
							answerMap.put(INV163, CASE_STATUS_SUSPECT);
							if(tub_266.equals(VERCRIT_NOT_VERIFIED)){
								answerMap.put(INV163, CASE_STATUS_NOT_A_CASE);
								//((FormField) formFieldMap.get(INV163)).setValue(CASE_STATUS_NOT_A_CASE);
							}else if(tub_266.equals(VERCRIT_PRV_VERIFIED)){
								answerMap.put(INV163, CASE_STATUS_CONFIRMED);
								//((FormField) formFieldMap.get(INV163)).setValue(CASE_STATUS_CONFIRMED);
							}
				   
			         }
				    
				  }
		    
			case_verified = false;
			rule5_status = false;
			returnedList.add((FormField)formFieldMap.get(TUB266));
			returnedList.add((FormField)formFieldMap.get(INV163));
			return returnedList;
	}
	
	public void fireVercritRuleOnLoad(Map<Object, Object> formFieldMap) {
		String tub_266 = (String) ((FormField) formFieldMap.get(TUB266))
				.getValue();
		
		FormField fField = (FormField) formFieldMap.get(TUB108);
		FormField fField2 = (FormField) formFieldMap.get(INV163);
		FormField fField1 = (FormField) formFieldMap.get(TUB266);
		
		RulesUtility utility = new RulesUtility();
		utility.removeItemFromDropDownList(((FormField)formFieldMap.get(TUB266)).getState().getValues(),"");
		
		 if (tub_266 != null && ((VERCRIT_SUSPECT.equals(tub_266))||(VERCRIT_NOT_VERIFIED.equals(tub_266))||(VERCRIT_PRV_VERIFIED.equals(tub_266)))) {
			/*if(tub_258 != null && (tub_258.equals(TF_PAM_FALSE) || tub_258.equals("0"))){*/
			 List<Object> list = new ArrayList<Object> ();
			 list = utility.getNewDropDownList(((FormField)formFieldMap.get(TUB266)).getState().getValues(),VERCRIT_POSITIVE);
			 list = utility.getNewDropDownList(list,VERCRIT_POSITIVE_SMEAR);
			 list = utility.getNewDropDownList(list,VERCRIT_CLINICAL_DEFINATION);
			 list = utility.getNewDropDownList(list,VERCRIT_POSITIVE_NAA);
			 fField1.getState().setValues(list);		
			  			
				if(VERCRIT_PRV_VERIFIED.equals(tub_266)){
					//((FormField) formFieldMap.get(INV163)).setValue("Positive");
					ArrayList<Object> aList = (ArrayList<Object> )utility.getNewDropDownList(((FormField)formFieldMap.get(INV163)).getState().getValues(),CASE_STATUS_CONFIRMED);
					fField2.getState().setValues(aList);
					((FormField) formFieldMap.get(INV163)).getState().setEnabled(false);				
					
				}else if(VERCRIT_NOT_VERIFIED.equals(tub_266)){
					//((FormField) formFieldMap.get(INV163)).setValue("Not a Case");
					 ArrayList<Object> aList = (ArrayList<Object> )utility.getNewDropDownList(((FormField)formFieldMap.get(INV163)).getState().getValues(),CASE_STATUS_NOT_A_CASE);
					 fField2.getState().setValues(aList);
					((FormField) formFieldMap.get(INV163)).getState().setEnabled(false);
					//utility.removeItemFromDropDownList(((FormField)formFieldMap.get(TUB108)).getState().getValues(),COUNT_AS_TB_CASE);
					if(iscalculated){
						((FormField) formFieldMap.get(TUB266)).getState().setEnabled(false);
						
					}
					
				}else{
					//((FormField) formFieldMap.get(INV163)).setValue("Suspect");
					 ArrayList<Object> aList = (ArrayList<Object> )utility.getNewDropDownList(((FormField)formFieldMap.get(INV163)).getState().getValues(),CASE_STATUS_SUSPECT);
					 fField2.getState().setValues(aList);
					((FormField) formFieldMap.get(INV163)).getState().setEnabled(false);
					//utility.removeItemFromDropDownList(((FormField)formFieldMap.get(TUB108)).getState().getValues(),COUNT_AS_TB_CASE);
					
				}
			
		}else if (tub_266 != null && !((VERCRIT_SUSPECT.equals(tub_266))||(VERCRIT_NOT_VERIFIED.equals(tub_266))||(VERCRIT_PRV_VERIFIED.equals(tub_266)))){
			//Case Verification: 
			fField1.getState().setEnabled(false);
			fField1.getState().setValues(
					(ArrayList<Object> ) CachedDropDowns.getCodedValueForType(fField1
							.getCodeSetNm()));
			fField1.setValue(tub_266);
			//Case Status:
			((FormField) formFieldMap.get(INV163)).getState().setEnabled(false);
			fField.getState().setValues(
					(ArrayList<Object> ) CachedDropDowns.getCodedValueForType(fField
							.getCodeSetNm()));		
			
		}	
		
	}

	private void resetCountDate(Map<Object, Object> formFieldMap) {
		((FormField) formFieldMap.get(TUB110)).setValue(null);
		((FormField) formFieldMap.get(TUB110)).getState().setEnabled(false);
		((FormField) formFieldMap.get(INV165)).setValue(null);
		((FormField) formFieldMap.get(INV165)).getState().setEnabled(false);
		((FormField) formFieldMap.get(INV166)).setValue(null);
		((FormField) formFieldMap.get(INV166)).getState().setEnabled(false);
	}
	/**
	 * TUB239 (47. Number of Weeks of Directly Observed Therapy) 
	 * must be less than or equal to Number of Weeks between TUB170 
	 * (36. Date Therapy Started) and TUB232 (43. Date Therapy Stopped)
	 * @param ruleColl
	 * @param formFieldMap
	 * @param sourceInd
	 * @return
	 */
	public Collection<Object>  handleRule998(Collection<Object> errColl, Map<Object,Object> formFieldMap) {
		FormField ffTUB232 = (FormField) formFieldMap.get(TUB232);
		FormField ffTUB170 = (FormField) formFieldMap.get(TUB170);
		FormField ffTUB239 = (FormField) formFieldMap.get(TUB239);

		// check the date format of the form fields
		RulesEngineUtil util = new RulesEngineUtil();
		if (ffTUB232.getValue() != null && !ffTUB232.getValue().equals(""))
			util.isDateFormated(errColl, ffTUB232);
		if (ffTUB170.getValue() != null && !ffTUB170.getValue().equals(""))
			util.isDateFormated(errColl, ffTUB170);
		if (ffTUB239.getValue() != null && !(ffTUB239.getValue().equals(""))) {

			if (ffTUB232.getValue() != null && ffTUB170.getValue() != null) {

				long diff = util.weekDifference((String) ffTUB232.getValue(),
						(String) ffTUB170.getValue());
				if (diff < Long.parseLong((String) ffTUB239.getValue())) {
					String errorMessage = ERR196.replaceAll("\\(Field Name\\)",
							ffTUB239.getLabel()).replaceAll(
							"\\(Field Name1\\)", ffTUB232.getLabel())
							.replaceAll("\\(Field Name2\\)",
									ffTUB170.getLabel());

					ffTUB232.getErrorMessage().add(errorMessage);
					ffTUB232
							.setErrorStyleClass(RuleConstants.REQUIRED_FIELD_CLASS);
					ffTUB170
							.setErrorStyleClass(RuleConstants.REQUIRED_FIELD_CLASS);
					ffTUB239
							.setErrorStyleClass(RuleConstants.REQUIRED_FIELD_CLASS);
					errColl.add(ffTUB232);
					errColl.add(ffTUB239);
					errColl.add(ffTUB170);
				}

			}
		}

		return errColl;
	}
	
	public Collection<Object>  handleRule1003(Collection<Object> errColl, Map<Object,Object> formFieldMap,Map<Object, Object> answerArrayMap) {
		FormField ffTUB232 = (FormField) formFieldMap.get(TUB232);
		FormField ffTUB170 = (FormField) formFieldMap.get(TUB170);
		FormField ffTUB235 = (FormField) formFieldMap.get(TUB235);

		// check the date format of the form fields
		RulesEngineUtil util = new RulesEngineUtil();
		if (ffTUB232.getValue() != null && !ffTUB232.getValue().equals(""))
			util.isDateFormated(errColl, ffTUB232);
		if (ffTUB170.getValue() != null && !ffTUB170.getValue().equals(""))
			util.isDateFormated(errColl, ffTUB170);
		if((answerArrayMap != null && !answerArrayMap.containsKey(ffTUB235.getFieldId())) ||answerArrayMap == null) {
		//if ((ffTUB235.getValue() != null && (ffTUB235.getValue().equals(""))) || ffTUB235.getValue() == null) {

			if (ffTUB232.getValue() != null && !(ffTUB232.getValue().equals("")) && ffTUB170.getValue() != null && !(ffTUB170.getValue().equals(""))) {

				long diff = util.monthDifference((String) ffTUB232.getValue(),
						(String) ffTUB170.getValue());
				// the difference is 12 
				if (diff > 12) {
					String errorMessage = ERR201.replaceAll("\\(Field Name\\)",
							ffTUB235.getLabel());
					ffTUB232.getErrorMessage().add(errorMessage);
					ffTUB232
							.setErrorStyleClass(RuleConstants.REQUIRED_FIELD_CLASS);
					ffTUB170
							.setErrorStyleClass(RuleConstants.REQUIRED_FIELD_CLASS);
					ffTUB235.setErrorStyleClass(RuleConstants.REQUIRED_FIELD_CLASS);
					errColl.add(ffTUB232);
					errColl.add(ffTUB235);
					errColl.add(ffTUB170);
				}

			}
		}

		return errColl;
	}
	public Collection<Object>  handleRule993(Collection<Object> errColl, Map<Object,Object> formFieldMap,Map<Object, Object> answerArrayMap){
		FormField ffTUB171 = (FormField) formFieldMap.get(TUB171);
		FormField ffTUB172 = (FormField) formFieldMap.get(TUB172);
		FormField ffTUB173 = (FormField) formFieldMap.get(TUB173);
		FormField ffTUB174 = (FormField) formFieldMap.get(TUB174);
		FormField ffTUB175 = (FormField) formFieldMap.get(TUB175);
		FormField ffTUB176 = (FormField) formFieldMap.get(TUB176);
		FormField ffTUB177 = (FormField) formFieldMap.get(TUB177);
		FormField ffTUB178 = (FormField) formFieldMap.get(TUB178);
		FormField ffTUB179 = (FormField) formFieldMap.get(TUB179);
		FormField ffTUB180 = (FormField) formFieldMap.get(TUB180);
		FormField ffTUB181 = (FormField) formFieldMap.get(TUB181);
		FormField ffTUB182 = (FormField) formFieldMap.get(TUB182);
		FormField ffTUB183 = (FormField) formFieldMap.get(TUB183);
		FormField ffTUB184 = (FormField) formFieldMap.get(TUB184);
		FormField ffTUB185 = (FormField) formFieldMap.get(TUB185);
		FormField ffTUB186 = (FormField) formFieldMap.get(TUB186);
		FormField ffTUB187 = (FormField) formFieldMap.get(TUB187);
		FormField ffTUB188 = (FormField) formFieldMap.get(TUB188);
		FormField ffTUB190 = (FormField) formFieldMap.get(TUB190);
		
		FormField ffTUB170 = (FormField) formFieldMap.get(TUB170);
		
		if(ffTUB170.getValue() == null || (ffTUB170.getValue() != null && !ffTUB170.getValue().equals(""))){
			boolean metCondition = false;
			if((ffTUB171.getValue()!= null && ffTUB171.getValue().equals("Y") )
			|| (ffTUB172.getValue()!= null && ffTUB172.getValue().equals("Y"))
			|| (ffTUB173.getValue()!= null && ffTUB173.getValue().equals("Y") )
			|| (ffTUB174.getValue()!= null && ffTUB174.getValue().equals("Y"))
			|| (ffTUB175.getValue()!= null && ffTUB175.getValue().equals("Y"))
			|| (ffTUB176.getValue()!= null && ffTUB176.getValue().equals("Y"))
			|| (ffTUB177.getValue()!= null && ffTUB177.getValue().equals("Y"))
			|| (ffTUB178.getValue()!= null && ffTUB178.getValue().equals("Y"))
			|| (ffTUB179.getValue()!= null && ffTUB179.getValue().equals("Y"))
			|| (ffTUB180.getValue()!= null && ffTUB180.getValue().equals("Y"))
			|| (ffTUB181.getValue()!= null && ffTUB181.getValue().equals("Y"))
			|| (ffTUB182.getValue()!= null && ffTUB182.getValue().equals("Y"))
			|| (ffTUB183.getValue()!= null && ffTUB183.getValue().equals("Y"))
			|| (ffTUB184.getValue()!= null && ffTUB184.getValue().equals("Y"))
			|| (ffTUB185.getValue()!= null && ffTUB185.getValue().equals("Y"))
			|| (ffTUB186.getValue()!= null && ffTUB186.getValue().equals("Y"))
			|| (ffTUB187.getValue()!= null && ffTUB187.getValue().equals("Y"))
			|| (ffTUB188.getValue()!= null && ffTUB188.getValue().equals("Y"))
			|| (ffTUB190.getValue()!= null && ffTUB190.getValue().equals("Y"))){
				metCondition = true;
			    
			}
			else{
				ffTUB170.getErrorMessage().add(ERR197.replaceAll("\\(Field Name\\)", ffTUB170.getLabel()));
				ffTUB170.setErrorStyleClass(RuleConstants.REQUIRED_FIELD_CLASS);
				errColl.add(ffTUB170);
			}
		}
		
		
		
	
	
		return errColl;
		
	}
	
	public Collection<Object>  handleRule1004(Collection<Object> errColl, Map<Object,Object> formFieldMap,
			Map<Object, Object> answerArrayMap) {
		FormField ffTUB167 = (FormField) formFieldMap.get(TUB167);
		if (answerArrayMap != null
				&& answerArrayMap.containsKey(ffTUB167.getFieldId())) {
			
			String[] answers = (String[]) answerArrayMap.get(ffTUB167
					.getFieldId());
			Map<Object, Object> ansMap = new HashMap<Object,Object>();
			if (answers != null && answers.length > 0) {
				for (int i = 0; i < answers.length; i++) {
					if (answers[i] != null) {
						ansMap.put(answers[i], answers[i]);
					}
				}

			}
			if(ansMap != null && ansMap.containsValue("PHC686") && ansMap.containsValue("PHC687")){
				ffTUB167.getErrorMessage().add(ERR199.replaceAll("\\(Field Name\\)",ffTUB167.getLabel()));
				ffTUB167.setErrorStyleClass(RuleConstants.REQUIRED_FIELD_CLASS);
				errColl.add(ffTUB167);
				
			}
		}

		return errColl;

	}
	
	public Collection<Object>  handleRule989(Collection<Object> errColl, Map<Object,Object> formFieldMap) {
		FormField ffTUB147 = (FormField) formFieldMap.get(TUB147);
		FormField ffTUB149 = (FormField) formFieldMap.get(TUB149);

		if (ffTUB147 != null && ffTUB147.getValue() != null
				&& ffTUB147.getValue().equals(NEGATIVE)) {

			if (ffTUB149 != null && ffTUB149.getValue() != null && !ffTUB149.getValue().equals("")) {

				String tub149Value = (String) ffTUB149.getValue();
				int tubValue = Integer.parseInt(tub149Value);
				if ((tubValue == 99) || (tubValue < 10)) {
					return errColl;
				}else{ //if( tubValue != 99 && (tubValue > 10)){				
					ffTUB149.getErrorMessage().add(
							ERR192.replaceAll("\\(Field Name1\\)",
									ffTUB149.getLabel()).replaceAll(
									"\\(Field Name2\\)", ffTUB147.getLabel()));
					ffTUB149
							.setErrorStyleClass(RuleConstants.REQUIRED_FIELD_CLASS);
					errColl.add(ffTUB149);

				}
			}

		}
		return errColl;

	}
	
	

}
