package gov.cdc.nedss.webapp.nbs.action.contacttracing.util;

import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.RulesDT;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.action.pam.util.PamConstants;
import gov.cdc.nedss.webapp.nbs.action.util.RuleConstants;
import gov.cdc.nedss.webapp.nbs.action.util.RulesEngineUtil;
import gov.cdc.nedss.webapp.nbs.action.util.TBSpecificRules;
import gov.cdc.nedss.webapp.nbs.action.util.VaricellaSpecificRules;
import gov.cdc.nedss.webapp.nbs.form.contacttracing.CTContactForm;
import gov.cdc.nedss.webapp.nbs.form.pam.FormField;
import gov.cdc.nedss.webapp.nbs.form.pam.PamForm;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.QuestionsCache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class CTRulesEngineUtil extends RuleConstants{
	
	static final LogUtils logger = new LogUtils(CTRulesEngineUtil.class.getName());
	
	public Collection<Object>  fireRules(String sourceInd, CTContactForm form, Map<Object,Object> formFieldMap) {

		String formCode = form.getPageFormCd();
		String actionMode = form.getActionMode();
		String caseLocalId = (String)form.getAttributeMap().get("caseLocalId");
		Map<Object,Object> answerMap =  form.getcTContactClientVO().getAnswerMap();
		Map<Object,Object> answerArrayMap = form.getcTContactClientVO().getArrayAnswerMap();

		/*
		 *  Define the field Collection<Object>  to be returned
		 *  In case of Submit it will be a collection of ErrorMessage
		 */
		Collection<Object>  fieldColl = new ArrayList<Object> ();
		long  end=0;
		try {
			TreeMap<Object,Object> onChgRuleMap = null;
			// Get the ruleMap from the cache
			Collection<?>  ruleColl = new ArrayList<Object> ();
			TreeMap<?,?> ruleMap = null;

			RulesEngineUtil reUtil = new RulesEngineUtil();
			if (sourceInd != null) {
				onChgRuleMap = QuestionsCache.getContactRuleMap();
				ruleMap = (TreeMap<?,?>) onChgRuleMap.get(formCode);
				if(ruleMap != null){
					ruleColl = (Collection<?>) ruleMap.get(sourceInd);
				}


				if (ruleColl != null && ruleColl.size() > 0) {
					Iterator<?> ruleIter = ruleColl.iterator();
					Map<Object,Object> ruleFiredMap = new HashMap<Object,Object>();

					while (ruleIter.hasNext()) {
						RulesDT rulesDT = (RulesDT) ruleIter.next();
						if (rulesDT.getRuleName().equals(ENABLE)) {
							// Call the enable method
							reUtil.enable(fieldColl, rulesDT, sourceInd,
									formFieldMap, ruleMap);
						} /*else if (rulesDT.getRuleName().equals(
							NEDSSConstants.FILTER)) {
						// call the filter rule
						reUtil.filter(fieldColl, rulesDT, sourceInd,
								formFieldMap, answerArrayMap, formCode, form);
					}*/ else if (rulesDT.getRuleName()
							.equals(NEDSSConstants.MMWR)) {
						// call the MMWR rule
						reUtil.calculateMMWR(fieldColl, rulesDT, sourceInd,
								formFieldMap,formCode);
					} else if (rulesDT.getRuleName().equals(
							NEDSSConstants.DISABLE)) {
						// call the disable rule
						reUtil.disable(fieldColl, rulesDT, sourceInd,
								formFieldMap, ruleMap);
					}
					}
				}

			}

			else if(formCode != null && sourceInd == null){
				// required fields
				Collection<Object>  mapColl = formFieldMap.values();
				Iterator<Object> iter = mapColl.iterator();
				while(iter.hasNext()){
					FormField formField = (FormField)iter.next();
					reUtil.getRequiredField(fieldColl, formField, formFieldMap,answerArrayMap);
					//(For BETA)This is a temporary fix for DEM215 AsOfDate,it is required only on create
					if(formField.getFieldId() != null && formField.getFieldId().equals("DEM215"))
						reUtil.getDEM215RequiredField(fieldColl, formField, formFieldMap,answerArrayMap, actionMode);
					if (formField != null
							&& formField.getFieldType() != null
							&& formField.getFieldType().equals(
									NEDSSConstants.DATE) && !formField.getState().isFutureDtInd()) {
						// call the date range method
						reUtil.dateRange(fieldColl, formField,
								formFieldMap);
					}else if(formField != null
							&& formField.getFieldType() != null
							&& formField.getFieldType().equals(
									NEDSSConstants.DATE) && formField.getState().isFutureDtInd())
					{
						reUtil.dateRangewithFuture(fieldColl, formField,
								formFieldMap);
					}
				}

				ruleMap = QuestionsCache.getFormRuleMap();
				if(ruleMap!= null){
					ruleColl = (Collection<?>) ruleMap.get(formCode);
				}

			}
			String reqForNotif = form.getAttributeMap().get(PamConstants.REQ_FOR_NOTIF) == null ? null : (String)  form.getAttributeMap().get(PamConstants.REQ_FOR_NOTIF);
			String noReqForNotifCheck = form.getAttributeMap().get(PamConstants.NO_REQ_FOR_NOTIF_CHECK) == null ? null : (String)  form.getAttributeMap().get(PamConstants.NO_REQ_FOR_NOTIF_CHECK);
			if ((actionMode != null && (actionMode
					.equals(NEDSSConstants.EDIT_LOAD_ACTION) || (actionMode
							.equals(NEDSSConstants.EDIT_SUBMIT_ACTION))))
							&& reqForNotif != null && formCode != null &&(noReqForNotifCheck == null ||(noReqForNotifCheck!=null && noReqForNotifCheck.equalsIgnoreCase("false"))) ) {
				// Call the required for notification method
				//requiredForNotification(fieldColl, formFieldMap, form);
			}
			if (formCode != null && sourceInd == null) {
				if (ruleColl != null && ruleColl.size() > 0) {
					Iterator<?> ruleIter = ruleColl.iterator();
					while (ruleIter.hasNext()) {
						RulesDT rulesDT = (RulesDT) ruleIter.next();

						// Firing rules upon submit of the form
						if (sourceInd == null && formCode != null) {
							if (formCode.equals(rulesDT.getFormCode())) {

								if (rulesDT.getRuleName().equals(
										DATE_RANGE_LIMIT)) {
									// new rule - not tested
									reUtil.dateRangeLimit(rulesDT, formFieldMap,
											fieldColl);
								}else if (rulesDT.getRuleName().equals(
										DATE_COMPARE)) {
									reUtil.dateCompare(rulesDT, formFieldMap,
											fieldColl);
								} else if (rulesDT.getRuleName().equals(
										MATCH_VALUE)) {
									// call the matchValue rule
									reUtil.matchValue(fieldColl, rulesDT,
											formFieldMap, answerArrayMap);
								} else if (rulesDT.getRuleName().equals(
										REQUIRED_IF)) {
									// call the requiredIf rule
									reUtil.requiredIf(fieldColl, rulesDT,
											formFieldMap, actionMode);
								} else if (rulesDT.getRuleName().equals(
										VALID_LENGTH)) {
									// call the validLength rule
									reUtil.validLength(fieldColl, rulesDT,
											formFieldMap);
								} else if (rulesDT.getRuleName().equals(
										YEAR_RANGE)) {
									// call the yearRange rule
									reUtil
									.yearRange(fieldColl, rulesDT,
											formFieldMap,
											NEDSSConstants.YEAR_RANGE);
								} else if (rulesDT.getRuleName().equals(
										YEAR_RANGE_EQUAL)) {
									// call the yearRange rule
									reUtil.yearRangeEqual(fieldColl, rulesDT,
											formFieldMap,
											YEAR_RANGE_EQUAL);
								}else if (rulesDT.getRuleName().equals(
										YEAR_RANGE_LIMIT)) {
									// call the yearRange rule
									reUtil.yearRangeLimit(fieldColl, rulesDT,
											formFieldMap);
								}
								else if (rulesDT.getRuleName().equals(
										VALID_VALUE)) {
									// call the validValue rule
									reUtil.validValue(fieldColl, rulesDT,
											formFieldMap);
								} else if (rulesDT.getRuleName().equals(
										DATE_COMPARE_LIMIT)) {
									// call the validValue rule
									reUtil.dateCompareLimit(fieldColl, rulesDT,
											formFieldMap);
								}
								else if (rulesDT.getRuleName().equals(
										UNIQUE_CASE_NUMBER)) {
									// call the validValue rule -  Need to enter the data for this rule to test 
									reUtil.uniqueCaseNumber(fieldColl, rulesDT,
											formFieldMap,formCode,actionMode,caseLocalId);
								}else if (rulesDT.getRuleName().equals(
										VALID_VALUE_TARGET)) {
									// call the validValue rule -  Need to enter the data for this rule to test 
									reUtil.validValueOnTarget(fieldColl, rulesDT,
											formFieldMap);
								}else if (rulesDT.getRuleName().equals(DATE_RANGE_LIMIT_TARGET)){
									//method call
									reUtil.dateRangeLimitOnTarget(rulesDT, formFieldMap, fieldColl);
								}else if(rulesDT.getRuleName().equals(VALID_YEAR)){
									//valid year call
									reUtil.validYear(fieldColl, rulesDT, formFieldMap);

								}else if(rulesDT.getRuleName().equals(DIFF_SRC_VALID_VALUE_TARGET)){
									//valid year call
									reUtil.diffSourceValidValueTarget(fieldColl, rulesDT, formFieldMap);
								}else if(rulesDT.getRuleName().equals(DIFF_SRC_FILTER_SUBMIT)){
									//valid year call
									reUtil.diffSourceFilterSubmit(fieldColl, rulesDT, formFieldMap);
								}

							}
						}
					}
				}

			}



		} catch (Exception ex) {
			logger.error("Exception in CTRulesEngineUtil.fireRules encountered.." + ex.getMessage());
			ex.printStackTrace();
		}
		end = System.currentTimeMillis();
		logger.debug(" .........End of fireRule in RulesEngineUtil..." + end);

		return fieldColl;
	}

}
