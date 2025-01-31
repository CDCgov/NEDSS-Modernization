 package gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.rules;

import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.NbsQuestionMetadata;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.RulesDT;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.SourceFieldDT;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.SourceValueDT;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.TargetFieldDT;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.TargetValueDT;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.action.pam.util.PamConstants;
import gov.cdc.nedss.webapp.nbs.action.util.RuleConstants;
import gov.cdc.nedss.webapp.nbs.action.util.RulesEngineUtilHelper;
import gov.cdc.nedss.webapp.nbs.action.util.RulesUtility;
import gov.cdc.nedss.webapp.nbs.form.page.PageForm;
import gov.cdc.nedss.webapp.nbs.form.util.BaseForm;
import gov.cdc.nedss.webapp.nbs.form.pam.FieldState;
import gov.cdc.nedss.webapp.nbs.form.pam.FormField;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.QuestionsCache;
import gov.cdc.nedss.webapp.nbs.action.client.ClientVO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class PageRulesGenerator extends RuleConstants{
	
	static final LogUtils logger = new LogUtils(PageRulesGenerator.class.getName());
	
	private static final String requiredIfMsg = " is a required field if the associated Information Section contains data entries or data selections.";

	/**
	 * 
	 * @param questionMap
	 * @param form
	 * @return
	 */
	public Map<Object,Object> initiateForm(Map<Object,Object> questionMap, BaseForm form, ClientVO clientVO) {

		Map<Object,Object> answerMap =  clientVO.getAnswerMap();
		Map<Object,Object> answerArrayMap = clientVO.getArrayAnswerMap();
		String actionMode = form.getActionMode();


		Map<Object,Object> formFieldMap = new HashMap<Object,Object>();
		//String formCd = null;
		if (questionMap != null && questionMap.size() > 0) {
			try {
				Collection<Object>  qColl = questionMap.values();
				Iterator<Object> ite = qColl.iterator();
				while (ite.hasNext()) {
					FormField fField = new FormField();
					NbsQuestionMetadata qMetadata = (NbsQuestionMetadata) ite
							.next();
					if(qMetadata.getAList() != null)
						fField.setAList(qMetadata.getAList());

					String reqForNotif = form.getAttributeMap().get(PamConstants.REQ_FOR_NOTIF) == null ? null : (String)  form.getAttributeMap().get(PamConstants.REQ_FOR_NOTIF);
					if(actionMode != null && actionMode.equals(NEDSSConstants.CREATE_LOAD_ACTION)){
						fField.setDefaultValue(qMetadata.getDefaultValue());
						if(answerMap.get(qMetadata.getQuestionIdentifier())==null && qMetadata.getNbsUiComponentUid()!=null && !qMetadata.getNbsUiComponentUid().toString().equals(NEDSSConstants.MULTISELECT_COMPONENT) && qMetadata.getDefaultValue()!=null){
							answerMap.put(qMetadata.getQuestionIdentifier(), qMetadata.getDefaultValue());
						}
						else if(answerArrayMap.get(qMetadata.getQuestionIdentifier())==null && qMetadata.getNbsUiComponentUid()!=null && qMetadata.getNbsUiComponentUid().toString().equals(NEDSSConstants.MULTISELECT_COMPONENT) && qMetadata.getDefaultValue()!=null){
							answerArrayMap.put(qMetadata.getQuestionIdentifier(), new String[]{qMetadata.getDefaultValue()});
						}
					}
					else
						fField.setValue(answerMap
								.get(qMetadata.getQuestionIdentifier()));
					fField.setFieldId(qMetadata.getQuestionIdentifier());
					if(qMetadata.getQuestionIdentifier()== null)
						fField.setFieldId(qMetadata.getNbsUiMetadataUid().toString());


					try {
						if (answerArrayMap != null && fField.getFieldId() != null
								&& answerArrayMap.containsKey(fField.getFieldId())) {
							if (answerArrayMap.get(fField.getFieldId()) != null) {
								List<Object> multiSelVals = new ArrayList<Object> ();
								String[] answers = (String[]) answerArrayMap
										.get(fField.getFieldId());
								if (answers != null && answers.length > 0) {
									for (int i = 0; i < answers.length; i++) {
										if(answers[i] != null){
											multiSelVals.add(answers[i]);

										}
									}

									fField.getState().setMultiSelVals(multiSelVals);
								}
							}
						}
					} catch (Exception e) {
						logger.error("Error in initiate form ..answerArrayMap");
						e.printStackTrace();
					}
					//Autocomplete
					fField.setFieldAutoCompId(qMetadata.getQuestionIdentifier() + "_textbox");
					fField.setFieldAutoCompBtn(qMetadata.getQuestionIdentifier() + "_button");
					fField.setLabel(qMetadata.getQuestionLabel());
					fField.setTooltip(qMetadata.getQuestionToolTip());
					fField.setFieldType(qMetadata.getDataType());
					fField.setTabId(qMetadata.getTabId());
					fField.setCodeSetNm(qMetadata.getCodeSetNm());

					if (qMetadata.getEnableInd() != null
							&& qMetadata.getEnableInd().equals("F"))
						fField.getState().setEnabled(false);
					else
						fField.getState().setEnabled(true);
					if(qMetadata.getRequiredInd() != null){
						if(qMetadata.getRequiredInd().equals("T")){
							fField.getState().setRequired(true);
							fField.getState().setRequiredIndClass(REQUIRED_FIELD_CLASS);
						}else if((qMetadata.getRequiredInd()!= null && qMetadata.getRequiredInd().equals("TE")) && ((actionMode.equals(NEDSSConstants.EDIT_SUBMIT_ACTION))||(actionMode.equals(NEDSSConstants.EDIT_LOAD_ACTION)))){
							fField.getState().setRequired(true);
							fField.getState().setRequiredIndClass(REQUIRED_FIELD_CLASS);
						}else// if(qMetadata.getRequiredInd().equals("F")){
						{
							fField.getState().setRequired(false);
							fField.getState().setRequiredIndClass(NOT_REQUIRED_FIELD_CLASS);
						}
					}
					if(qMetadata.getFutureDateInd() != null)
					{
						if(qMetadata.getFutureDateInd().equals("T"))

							fField.getState().setFutureDtInd(true);
						else
							fField.getState().setFutureDtInd(false);
					}
					// This is for the hide and show logic of the fields in the page 
					if(qMetadata.getDisplayInd() != null){
						if(qMetadata.getDisplayInd().equals("T")){
							fField.getState().setVisible(true);
						}
						else 
							fField.getState().setVisible(false);
					}


					if(qMetadata.getQuestionRequiredNnd() != null && qMetadata.getQuestionRequiredNnd().equals("R")){
						fField.setQuestionRequiredNnd(qMetadata.getQuestionRequiredNnd());
					}
					ArrayList<?> aList = (ArrayList<?> )CachedDropDowns.getCodedValueForType(qMetadata
							.getCodeSetNm()).clone();
					fField.getState().setValues(aList);

					// This is for the vericella hyperlink - because we do not have the question Identifier, 
					// the uiMetadatUid is used for the FormFieldmap

					if(qMetadata.getQuestionIdentifier()==null)
						formFieldMap.put(qMetadata.getNbsUiMetadataUid().toString(), fField);	
					else
						formFieldMap.put(qMetadata.getQuestionIdentifier(), fField);


				}
			}catch(Exception ex) {
				logger.error("Exception in PageRulesGenerator.initiateForm: " + ex.getMessage());
				ex.printStackTrace();
			}
		}
		return formFieldMap;
	}

	public ArrayList<Object> fireRule1(String fieldId, Map<Object,Object> formFieldMap) {
		ArrayList<Object> returnedList = new ArrayList<Object> ();
		if (fieldId != null && fieldId.equals("TUB122")) {
			FormField sourceField = (FormField) formFieldMap.get("TUB122");
			FormField fField = new FormField();
			if (sourceField.getValue() != null) {
				fField = (FormField) formFieldMap.get("TUB123");
				fField.getState().setEnabled(true);
			} else {
				fField = (FormField) formFieldMap.get("TUB123");
				fField.getState().setEnabled(false);
			}
			returnedList.add(fField);
		}
		return returnedList;
	}

	/**
	 * General method to fire the rule
	 * @param sourceInd
	 * @param formFieldMap
	 * @return
	 */
	public Collection<Object>  fireRules(String sourceInd, PageForm form, Map<Object,Object> formFieldMap) {
		
		Collection<Object>  fieldColl = new ArrayList<Object> ();
		try {
			String formCode = form.getPageFormCd();
			String actionMode = form.getActionMode();
			String caseLocalId = (String)form.getAttributeMap().get("caseLocalId");
			Map<Object,Object> answerMap =  form.getPageClientVO().getAnswerMap();
			Map<Object,Object> answerArrayMap = form.getPageClientVO().getArrayAnswerMap();

			/*
			 *  Define the field Collection<Object>  to be returned
			 *  In case of Submit it will be a collection of ErrorMessage
			 */
			

			TreeMap<Object,Object> onChgRuleMap = null;
			// Get the ruleMap from the cache
			Collection<?>  ruleColl = new ArrayList<Object> ();
			TreeMap<?,?> ruleMap = null;
			long  end=0;

			if (sourceInd != null) {
				onChgRuleMap = QuestionsCache.getRuleMap();
				ruleMap = (TreeMap<?,?>) onChgRuleMap.get(formCode);
				if(ruleMap != null){
					ruleColl = (Collection<?>) ruleMap.get(sourceInd);
				}
			}
			else if(formCode != null && sourceInd == null){
				// required fields
				Collection<Object>  mapColl = formFieldMap.values();
				Iterator<Object> iter = mapColl.iterator();
				while(iter.hasNext()){
					FormField formField = (FormField)iter.next();
					this.getRequiredField(fieldColl, formField, formFieldMap,answerArrayMap);
					//(For BETA)This is a temporary fix for DEM215 AsOfDate,it is required only on create
					if(formField.getFieldId() != null && formField.getFieldId().equals("DEM215"))
						this.getDEM215RequiredField(fieldColl, formField, formFieldMap,answerArrayMap, actionMode);
					if (formField != null
							&& formField.getFieldType() != null
							&& formField.getFieldType().equals(
									NEDSSConstants.DATE) && !formField.getState().isFutureDtInd()) {
						// call the date range method
						this.dateRange(fieldColl, formField,
								formFieldMap);
					}else if(formField != null
							&& formField.getFieldType() != null
							&& formField.getFieldType().equals(
									NEDSSConstants.DATE) && formField.getState().isFutureDtInd())
					{
						this.dateRangewithFuture(fieldColl, formField,
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
				requiredForNotification(fieldColl, formFieldMap, form);
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
									this.dateRangeLimit(rulesDT, formFieldMap,
											fieldColl);
								}else if (rulesDT.getRuleName().equals(
										DATE_COMPARE)) {
									this.dateCompare(rulesDT, formFieldMap,
											fieldColl);
								} else if (rulesDT.getRuleName().equals(
										MATCH_VALUE)) {
									// call the matchValue rule
									this.matchValue(fieldColl, rulesDT,
											formFieldMap, answerArrayMap);
								} else if (rulesDT.getRuleName().equals(
										REQUIRED_IF)) {
									// call the requiredIf rule
									this.requiredIf(fieldColl, rulesDT,
											formFieldMap, actionMode);
								} else if (rulesDT.getRuleName().equals(
										VALID_LENGTH)) {
									// call the validLength rule
									this.validLength(fieldColl, rulesDT,
											formFieldMap);
								} else if (rulesDT.getRuleName().equals(
										YEAR_RANGE)) {
									// call the yearRange rule
									this
									.yearRange(fieldColl, rulesDT,
											formFieldMap,
											NEDSSConstants.YEAR_RANGE);
								} else if (rulesDT.getRuleName().equals(
										YEAR_RANGE_EQUAL)) {
									// call the yearRange rule
									this.yearRangeEqual(fieldColl, rulesDT,
											formFieldMap,
											YEAR_RANGE_EQUAL);
								}else if (rulesDT.getRuleName().equals(
										YEAR_RANGE_LIMIT)) {
									// call the yearRange rule
									this.yearRangeLimit(fieldColl, rulesDT,
											formFieldMap);
								}
								else if (rulesDT.getRuleName().equals(
										VALID_VALUE)) {
									// call the validValue rule
									this.validValue(fieldColl, rulesDT,
											formFieldMap);
								} else if (rulesDT.getRuleName().equals(
										DATE_COMPARE_LIMIT)) {
									// call the validValue rule
									this.dateCompareLimit(fieldColl, rulesDT,
											formFieldMap);
								}
								else if (rulesDT.getRuleName().equals(
										UNIQUE_CASE_NUMBER)) {
									// call the validValue rule -  Need to enter the data for this rule to test 
									this.uniqueCaseNumber(fieldColl, rulesDT,
											formFieldMap,formCode,actionMode,caseLocalId);
								}else if (rulesDT.getRuleName().equals(
										VALID_VALUE_TARGET)) {
									// call the validValue rule -  Need to enter the data for this rule to test 
									this.validValueOnTarget(fieldColl, rulesDT,
											formFieldMap);
								}else if (rulesDT.getRuleName().equals(DATE_RANGE_LIMIT_TARGET)){
									//method call
									this.dateRangeLimitOnTarget(rulesDT, formFieldMap, fieldColl);
								}else if(rulesDT.getRuleName().equals(VALID_YEAR)){
									//valid year call
									this.validYear(fieldColl, rulesDT, formFieldMap);

								}else if(rulesDT.getRuleName().equals(DIFF_SRC_VALID_VALUE_TARGET)){
									//valid year call
									this.diffSourceValidValueTarget(fieldColl, rulesDT, formFieldMap);
								}else if(rulesDT.getRuleName().equals(DIFF_SRC_FILTER_SUBMIT)){
									//valid year call
									this.diffSourceFilterSubmit(fieldColl, rulesDT, formFieldMap);
								}
							}
						}
					}
				}
			}

			end = System.currentTimeMillis();
			logger.debug(" .........End of fireRule in RulesEngineUtil..." + end);
		}catch(Exception ex) {
			logger.error("Exception in PageRulesGenerator.fireRules: " + ex.getMessage());
			ex.printStackTrace();
		}

		return fieldColl;
	}
   /**
    * Enable method for the enable rule
    * @param fieldColl
    * @param rulesDT
    * @param sourceInd
    * @param formFieldMap
    * @return
    */
	public Collection<Object>  enableold(Collection<Object> fieldColl, RulesDT rulesDT,  String sourceInd, Map<Object,Object> formFieldMap,Map<Object,Object> ruleMap ){

		try {
			// get the SourceField properties from the Map
			FormField formField = (FormField) formFieldMap.get(sourceInd);
			//String formFieldTmp = null;
			if(formField.getValue() != null && !formField.getValue().equals("")){
				// If one field has more than one enable rule execute only one
				//if((formFieldTmp == null) || (formFieldTmp!=null && !formFieldTmp.equals((String)formField.getFieldId()))){
				//	formFieldTmp = (String)formField.getFieldId();

				if(formField.getFieldType() != null && formField.getFieldType().equals(DATE)){
					return getTargetsForEnable(rulesDT,
							formFieldMap, fieldColl);
				}
				else if(formField.getFieldType() != null && formField.getFieldType().equals(DATATYPE_CODE)){

					Collection<Object>  sourceColl = rulesDT.getSourceColl();
					if (sourceColl != null && sourceColl.size() > 0) {
						Iterator<Object> sourceIter = sourceColl.iterator();

						while (sourceIter.hasNext()) {
							SourceFieldDT sourceFieldDT = (SourceFieldDT) sourceIter
									.next();
							if(sourceInd != null && sourceInd.equals(sourceFieldDT.getQuestionIdentifier())){

								Collection<Object>  sValueColl = sourceFieldDT
										.getSourceValueColl();

								if (sValueColl != null && sValueColl.size() > 0) {
									Iterator<Object> sValueIter = sValueColl.iterator();
									Iterator<Object> sValueIter1  = sValueColl.iterator();
									HashMap<Object,Object> map = new HashMap<Object,Object>();
									while (sValueIter1.hasNext()) {
										SourceValueDT svDT = (SourceValueDT) sValueIter1
												.next();
										map.put(svDT.getSourceValue(), svDT.getSourceValue());
									}
									while (sValueIter.hasNext()) {
										boolean metTheCondition = false;
										SourceValueDT svDT = (SourceValueDT) sValueIter
												.next();
										if (svDT.getSourceValue()!= null){
											if(svDT.getOperatorTypeCode() != null && svDT.getOperatorTypeCode().equals(EQUAL)){
												if(map != null && map.containsKey(formField.getValue())){
													fieldColl = this.getTargetsForEnable(rulesDT,
															formFieldMap, fieldColl);
													metTheCondition = true;
												}
												else if(map != null && !(map.containsKey(formField.getValue()))){
													fieldColl = this.getTargetsForDisable(rulesDT,
															formFieldMap, fieldColl,ruleMap);
													metTheCondition = true;
												}
											}else if(svDT.getOperatorTypeCode()!= null && svDT.getOperatorTypeCode().equals(NOTEQUAL)){
												if(!(map.containsKey(formField.getValue()))){
													fieldColl = this.getTargetsForEnable(rulesDT,
															formFieldMap, fieldColl);
													metTheCondition = true;
												}
												else if(map.containsKey(formField.getValue())){
													fieldColl = this.getTargetsForDisable(rulesDT,
															formFieldMap, fieldColl,ruleMap);
													metTheCondition = true;
												}
											}

										}
										if(metTheCondition)
											break;
									}
									/*
									 * source value collection null indicates that
									 * if the source field has a value, it should enable the target fields
									 */

									return fieldColl;
								}else{

									return this.getTargetsForEnable(rulesDT, formFieldMap, fieldColl);
								}
							}
						}
					}
				}
			}
			/*
			 * If the form field value is null disable the target for this rule
			 */
			else {
				fieldColl = this.getTargetsForDisable(rulesDT,
						formFieldMap, fieldColl,ruleMap);

			}
		}catch(Exception ex) {
			logger.error("Exception in PageRulesGenerator.enableold: " + ex.getMessage());
			ex.printStackTrace();
		}

		return fieldColl;
	}

	public Collection<Object>  enable(Collection<Object> fieldColl, RulesDT rulesDT,
			String sourceInd, Map<Object,Object> formFieldMap, Map<?,?> ruleMap) {
		logger.debug(" ..Enable mehod starts..   ");
		try {
			// get the SourceField properties from the Map
			FormField formField = (FormField) formFieldMap.get(sourceInd);
			//For coded value setting a blank value 
			if(formField.getFieldType()!= null && formField.getFieldType().equals(DATATYPE_CODE) && formField.getValue() == null){
				formField.setValue(BLNK);
			}
			if (formField.getState().getMultiSelVals() != null
					&& formField.getState().getMultiSelVals().size() > 0
					) {
				// if((answerArrayMap != null &&
				// answerArrayMap.containsKey(sourceInd)) &&
				// answerArrayMap.get(sourceInd) != null){
				// match the multiselect
				HashMap<Object,Object> answArrMap = new HashMap<Object,Object>();
				ArrayList<Object> ansList = (ArrayList<Object> ) formField.getState()
						.getMultiSelVals();
				Iterator<Object> iter = ansList.iterator();

				while (iter.hasNext()) {
					String str = (String) iter.next();
					answArrMap.put(str, str);
				}

				Collection<Object>  sourceCollm = rulesDT.getSourceColl();
				if (sourceCollm != null && sourceCollm.size() > 0) {

					Iterator<Object> sourceIterm = sourceCollm.iterator();

					while (sourceIterm.hasNext()) {
						SourceFieldDT sfDT = (SourceFieldDT) sourceIterm.next();
						Collection<Object>  svColl = sfDT.getSourceValueColl();
						if (svColl != null && svColl.size() > 0) {
							Iterator<Object> svIterm = svColl.iterator();
							while (svIterm.hasNext()) {
								SourceValueDT svDTm = (SourceValueDT) svIterm
										.next();
								if (svDTm.getSourceValue() != null
										&& answArrMap != null
										&& answArrMap.containsKey(svDTm
												.getSourceValue())) {
									return this.getTargetsForEnable(rulesDT,
											formFieldMap, fieldColl);
								} else {
									return this.getTargetsForDisable(rulesDT,
											formFieldMap, fieldColl, ruleMap);
								}

							}
						}

					}
				}

			}
			else if (formField.getValue() != null && !formField.getValue().equals("")) {
				// If one field has more than one enable rule execute only one
				// if((formFieldTmp == null) || (formFieldTmp!=null &&
				// !formFieldTmp.equals((String)formField.getFieldId()))){
				// formFieldTmp = (String)formField.getFieldId();

				if (formField.getFieldType() != null
						&& !formField.getFieldType().equals(DATATYPE_CODE)) {
					return getTargetsForEnable(rulesDT, formFieldMap, fieldColl);
				} else if (formField.getFieldType() != null
						&& formField.getFieldType().equals(
								DATATYPE_CODE)) {

					Collection<Object>  sourceColl = rulesDT.getSourceColl();

					if (sourceColl != null && sourceColl.size() > 0) {
						Map<Object,Object> srcFldMap = new HashMap<Object,Object>();
						Iterator<Object> sourceIter = sourceColl.iterator();
						Iterator<Object> sourceIter1 = sourceColl.iterator();
						while (sourceIter1.hasNext()) {
							SourceFieldDT sourceFieldDT = (SourceFieldDT) sourceIter1
									.next();
							srcFldMap.put(sourceFieldDT.getQuestionIdentifier(),
									sourceFieldDT.getQuestionIdentifier());
						}

						while (sourceIter.hasNext()) {
							SourceFieldDT sourceFieldDT = (SourceFieldDT) sourceIter
									.next();

							if (srcFldMap != null
									&& srcFldMap.containsKey((sourceInd))) {
								Collection<Object>  sValueColl = sourceFieldDT
										.getSourceValueColl();
								if (sValueColl != null && sValueColl.size() > 0) {
									Iterator<Object> sValueIter = sValueColl.iterator();
									Iterator<Object> sValueIter1 = sValueColl.iterator();
									HashMap<Object,Object> map = new HashMap<Object,Object>();
									while (sValueIter1.hasNext()) {
										SourceValueDT svDT = (SourceValueDT) sValueIter1
												.next();
										map.put(svDT.getSourceValue(), svDT
												.getSourceValue());
									}
									while (sValueIter.hasNext()) {
										boolean metTheCondition = false;
										SourceValueDT svDT = (SourceValueDT) sValueIter
												.next();
										if (svDT.getSourceValue() != null) {
											if (svDT.getOperatorTypeCode() != null
													&& svDT.getOperatorTypeCode()
													.equals(EQUAL)) {
												if (map != null
														&& map
														.containsKey(formField
																.getValue())) {
													fieldColl = this
															.getTargetsForEnable(
																	rulesDT,
																	formFieldMap,
																	fieldColl);
													metTheCondition = true;
												} else if (map != null
														&& !(map
																.containsKey(formField
																		.getValue()))) {
													fieldColl = this
															.getTargetsForDisable(
																	rulesDT,
																	formFieldMap,
																	fieldColl,
																	ruleMap);
													metTheCondition = true;
												}
											} else if (svDT.getOperatorTypeCode() != null
													&& svDT.getOperatorTypeCode()
													.equals(NOTEQUAL)) {
												if (!(map.containsKey(formField
														.getValue()))) {
													fieldColl = this
															.getTargetsForEnable(
																	rulesDT,
																	formFieldMap,
																	fieldColl);
													metTheCondition = true;
												} else if (map
														.containsKey(formField
																.getValue())) {
													fieldColl = this
															.getTargetsForDisable(
																	rulesDT,
																	formFieldMap,
																	fieldColl,
																	ruleMap);
													metTheCondition = true;
												}
											}

										}
										if (metTheCondition)
											break;
									}
									/*
									 * source value collection null indicates that
									 * if the source field has a value, it should
									 * enable the target fields
									 */
									if(formField.getValue()!= null && formField.getValue().equals("BLNK"))
										formField.setValue(null);
									return fieldColl;
								} else {
									if(formField.getValue()!= null && !formField.getValue().equals("BLNK")){
										return this.getTargetsForEnable(rulesDT,
												formFieldMap, fieldColl);

									} 
									if(formField.getValue()!= null && formField.getValue().equals("BLNK")){
										this.getTargetsForDisable(rulesDT, formFieldMap, fieldColl, ruleMap);
										formField.setValue(null);

									}

								}
							}
						}

					}
				}
				if(formField.getValue()!= null && formField.getValue().equals("BLNK")){
					formField.setValue("");
				}
			}


			/*
			 * If the form field value is null disable the target for this rule
			 */
			else {
				if(formField.getValue()!= null && formField.getValue().equals("BLNK")){
					formField.setValue(null);
				}
				fieldColl = this.getTargetsForDisable(rulesDT, formFieldMap,
						fieldColl, ruleMap);

			}
		}catch(Exception ex) {
			logger.error("Exception in PageRulesGenerator.enable: " + ex.getMessage());
			ex.printStackTrace();
		}
		logger.debug(" ..Enable mehod Ends..   ");

		return fieldColl;
	}
	/**
	 *
	 * @param fieldColl
	 * @param sourceInd
	 * @param formFieldMap
	 * @param ruleMap
	 * @return
	 */
	private Collection<Object>  getTargetsOfTargets(Collection<Object> fieldColl, String sourceInd, Map<Object,Object> formFieldMap, Map<Object,Object>  ruleMap){
		if (fieldColl!= null && fieldColl.size()>0){
			try {
				Iterator<Object> iter = fieldColl.iterator();
				while(iter.hasNext()){
					FormField tFormField = (FormField)iter.next();
					if(tFormField != null && ruleMap != null ){
						if(ruleMap.containsKey(tFormField.getFieldId())){
							ArrayList<?> ruleColl = (ArrayList<?> )ruleMap.get(tFormField.getFieldId());
							if(ruleColl != null){
								Iterator<?> ruleIter = ruleColl.iterator();
								while(ruleIter.hasNext()){
									RulesDT rDT =(RulesDT)ruleIter.next();
									if(rDT.getRuleName().equals(NEDSSConstants.ENABLE)){
										fieldColl= this.enable(fieldColl,rDT, sourceInd, formFieldMap, ruleMap);
									}
								}
							}
						}
					}
				}
			}catch(Exception ex) {
				logger.error("Exception in PageRulesGenerator.getTargetsForTargets: " + ex.getMessage());
				ex.printStackTrace();
			}
		}
		return fieldColl;
	}
    /*
	 * Method which returns the enable rule Target Collection
	 */
	private Collection<Object>  getTargetsForEnable(RulesDT rulesDT, Map<Object,Object> formFieldMap,
			Collection<Object>  fieldColl) {
		Collection<Object>  targetColl = rulesDT.getTargetColl();
		if (targetColl != null && targetColl.size() > 0) {
			try {
			Iterator<Object> targetIter = targetColl.iterator();
			while (targetIter.hasNext()) {
				TargetFieldDT targetFieldDT = (TargetFieldDT) targetIter.next();
				// The same field can be a Target for another rule
				FormField formField = (FormField)formFieldMap.get(targetFieldDT.getQuestionIdentifier());
				formField.setFieldId(targetFieldDT.getQuestionIdentifier());
				formField.setFieldAutoCompId(targetFieldDT.getQuestionIdentifier() + "_textbox");
				formField.setFieldAutoCompBtn(targetFieldDT.getQuestionIdentifier() + "_button");
				FieldState fieldState = formField.getState();
				fieldState.setEnabled(true);
				fieldState.enable();
				//formField.setValue(null);
				formField.setState(fieldState);
				fieldColl.add(formField);
				if (formFieldMap.containsKey(formField.getFieldId())) {
					formFieldMap.put(formField.getFieldId(), formField);
				}

			}
			}catch(Exception ex) {
				logger.error("Exception in PageRulesGenerator.getTargetsForEnable: " + ex.getMessage());
				ex.printStackTrace();
			}


		}
		return fieldColl;
	}

	/*
	 * Method which returns the enable rule Target Collection
	 */
	private Collection<Object>  getTargetsForDisable(RulesDT rulesDT, Map<Object,Object> formFieldMap,
			Collection<Object>  fieldColl, Map<?,?> ruleMap) {
		Collection<Object>  targetColl = rulesDT.getTargetColl();
		if (targetColl != null && targetColl.size() > 0) {
			try {
				Iterator<Object> targetIter = targetColl.iterator();
				while (targetIter.hasNext()) {
					TargetFieldDT targetFieldDT = (TargetFieldDT) targetIter.next();
					// The same field can be a Target for another rule
					FormField formField = (FormField)formFieldMap.get(targetFieldDT.getQuestionIdentifier());
					formField.setFieldId(targetFieldDT.getQuestionIdentifier());
					formField.setFieldAutoCompId(targetFieldDT.getQuestionIdentifier() + "_textbox");
					formField.setFieldAutoCompBtn(targetFieldDT.getQuestionIdentifier() + "_button");
					FieldState fieldState = formField.getState();
					fieldState.setEnabled(false);
					formField.setValue("");
					fieldState.setMultiSelVals(new ArrayList<Object>());
					formField.setState(fieldState);
					fieldColl.add(formField);
					if (formFieldMap.containsKey(formField.getFieldId())) {
						formFieldMap.put(formField.getFieldId(), formField);
					}
					// If the target field has any enable rule the following code will execute
					if(ruleMap.containsKey(formField.getFieldId())){
						ArrayList<?> ruleColl = (ArrayList<?> )ruleMap.get(formField.getFieldId());
						if(ruleColl != null){
							Iterator<?> ruleIter = ruleColl.iterator();
							while(ruleIter.hasNext()){
								RulesDT rDT =(RulesDT)ruleIter.next();
								if(rDT.getRuleName().equals(ENABLE)){
									if((formField.getValue()== null) || (formField.getValue()!=null && formField.getValue().equals(""))){
										getTargetsForDisable(rDT,formFieldMap,fieldColl,ruleMap);
									}
								}
							}
						}
					}
				}


			}catch(Exception ex) {
				logger.error("Exception in PageRulesGenerator.getTargetsForDisable: " + ex.getMessage());
				ex.printStackTrace();
			}
		}
		return fieldColl;
	}

	public Collection<Object>  disable(Collection<Object> fieldColl, RulesDT rulesDT,  String sourceInd, Map<Object,Object> formFieldMap, Map<?,?> ruleMap){
		try {
			// get the SourceField properties from the Map
			FormField formField = (FormField) formFieldMap.get(sourceInd);
			if(formField.getFieldType()!= null && formField.getFieldType().equals(DATATYPE_CODE) && formField.getValue() == null){
				formField.setValue(BLNK);
			}
			if (formField.getState().getMultiSelVals() != null
					&& formField.getState().getMultiSelVals().size() > 0
					) {

				// match the multiselect
				HashMap<Object,Object> answArrMap = new HashMap<Object,Object>();
				ArrayList<Object> ansList = (ArrayList<Object> ) formField.getState()
						.getMultiSelVals();
				Iterator<Object> iter = ansList.iterator();

				while (iter.hasNext()) {
					String str = (String) iter.next();
					answArrMap.put(str, str);
				}

				Collection<Object>  sourceCollm = rulesDT.getSourceColl();
				if (sourceCollm != null && sourceCollm.size() > 0) {

					Iterator<Object> sourceIterm = sourceCollm.iterator();

					while (sourceIterm.hasNext()) {
						SourceFieldDT sfDT = (SourceFieldDT) sourceIterm.next();
						Collection<Object>  svColl = sfDT.getSourceValueColl();
						if (svColl != null && svColl.size() > 0) {
							Iterator<Object> svIterm = svColl.iterator();
							while (svIterm.hasNext()) {
								SourceValueDT svDTm = (SourceValueDT) svIterm
										.next();
								if (svDTm.getSourceValue() != null
										&& answArrMap != null
										&& answArrMap.containsKey(svDTm
												.getSourceValue())) {
									return this.getTargetsForDisable(rulesDT,
											formFieldMap, fieldColl, ruleMap);
								} else {
									return this.getTargetsForEnable(rulesDT,
											formFieldMap, fieldColl);
								}
							}
						}
					} //while
				}

			}
			else if(formField.getValue() != null && !formField.getValue().equals("")){
				Map<Object,Object> srcFldMap = new HashMap<Object,Object>();
				Collection<Object>  sourceColl = rulesDT.getSourceColl();
				if (sourceColl != null && sourceColl.size() > 0) {
					Iterator<Object> sourceIter = sourceColl.iterator();
					Iterator<Object> sourceIter1 = sourceColl.iterator();
					while (sourceIter1.hasNext()) {
						SourceFieldDT sourceFieldDT = (SourceFieldDT) sourceIter1
								.next();
						srcFldMap.put(sourceFieldDT.getQuestionIdentifier(), sourceFieldDT.getQuestionIdentifier());
					}
					while (sourceIter.hasNext()) {
						SourceFieldDT sourceFieldDT = (SourceFieldDT) sourceIter
								.next();
						//if(sourceInd != null && sourceInd.equals(sourceFieldDT.getQuestionIdentifier())){
						if(srcFldMap.containsValue(sourceInd)){

							Collection<Object>  sValueColl = sourceFieldDT
									.getSourceValueColl();

							if (sValueColl != null && sValueColl.size() > 0) {
								Iterator<Object> sValueIter = sValueColl.iterator();
								Iterator<Object> sValueIter1 = sValueColl.iterator();
								Map<Object,Object> srcValMap = new HashMap<Object,Object>();
								while (sValueIter1.hasNext()) {
									SourceValueDT svDT1 = (SourceValueDT) sValueIter1.next();
									if(svDT1.getSourceValue() != null){
										srcValMap.put(svDT1.getSourceValue(), svDT1.getSourceValue());
									}
								}

								while (sValueIter.hasNext()) {
									SourceValueDT svDT = (SourceValueDT) sValueIter
											.next();
									boolean metTheCondition = false;
									if (svDT.getSourceValue()!= null){
										if(svDT.getOperatorTypeCode() != null && svDT.getOperatorTypeCode().equals("=")){
											if(srcValMap != null &&  srcValMap.containsKey(formField.getValue())){
												fieldColl = this.getTargetsForDisable(rulesDT, formFieldMap, fieldColl,ruleMap);
											}
											else {
												fieldColl = this.getTargetsForEnable(rulesDT, formFieldMap, fieldColl);
											}
										}else if(svDT.getOperatorTypeCode()!= null && svDT.getOperatorTypeCode().equals("!=")){
											if(srcValMap != null &&  !srcValMap.containsKey(formField.getValue())){
												//if(!(svDT.getSourceValue().equals(formField.getValue()))){
												fieldColl = this.getTargetsForDisable(rulesDT,
														formFieldMap, fieldColl,ruleMap);
											}
											else {
												fieldColl = this.getTargetsForEnable(rulesDT,
														formFieldMap, fieldColl);
											}
										}
										if(formField.getValue()!= null && formField.getValue().equals(BLNK)){
											formField.setValue(null);
										}
										return fieldColl;
									}

								}
								/*
								 * source value collection null indicates that
								 * if the source field has a value, it should enable the target fields
								 */
							}else{
								if(formField.getValue()!= null && formField.getValue().equals(BLNK))
									formField.setValue(null);
								return this.getTargetsForDisable(rulesDT, formFieldMap, fieldColl,ruleMap);
							}
						}
					}

				}
				if(formField.getValue().equals(BLNK)){
					formField.setValue(null);
				}
			}
			/*
			 * If the form field value is null disable the target for this rule
			 */
			else {
				fieldColl = this.getTargetsForEnable(rulesDT,
						formFieldMap, fieldColl);
			}
		}catch(Exception ex) {
			logger.error("Exception in PageRulesGenerator.disable: " + ex.getMessage());
			ex.printStackTrace();
		}

		return fieldColl;
	}


	public Collection<Object>  filter(Collection<Object> fieldColl, RulesDT rulesDT,  String sourceInd, Map<Object,Object> formFieldMap,  Map<Object,Object> answerArrayMap,String formCode, PageForm form){
		// get the SourceField properties from the Map
		FormField formField = (FormField) formFieldMap.get(sourceInd);


		Collection<Object>  sourceColl = rulesDT.getSourceColl();
		if (sourceColl != null && sourceColl.size() > 0) {
			try {
				Iterator<Object> sourceIter = sourceColl.iterator();
				while (sourceIter.hasNext()) {
					SourceFieldDT sourceFieldDT = (SourceFieldDT) sourceIter
							.next();
					if(sourceInd != null && sourceInd.equals(sourceFieldDT.getQuestionIdentifier())){

						Collection<Object>  sValueColl = sourceFieldDT
								.getSourceValueColl();
						if (formField.getValue() == null
								||(formField.getValue().equals("")))
							formField.setValue(BLNK);

						//else if(formField.getState().getValues()!= null && formField.getState().getValues().size()>0){
						if(formField.getState().getMultiSelVals()!= null && formField.getState().getMultiSelVals().size()>0){					 
							// if((answerArrayMap != null && answerArrayMap.containsKey(sourceInd)) && answerArrayMap.get(sourceInd) != null){
							//match the multiselect
							HashMap<Object,Object> answArrMap = new HashMap<Object,Object>();
							ArrayList<Object> ansList = (ArrayList<Object> )formField.getState().getMultiSelVals();
							Iterator<Object>  iter = ansList.iterator();

							while(iter.hasNext()){
								String str = (String)iter.next();
								answArrMap.put(str, str);
							}

							if(sourceValueMatchMultiSelect(sValueColl,answArrMap)){
								this.getTargetsForfilter(rulesDT, formFieldMap, fieldColl);
							}
							else{
								resetTargetFilters(rulesDT, formFieldMap, fieldColl);
							}
							formField.getState().setMultiSelVals(new ArrayList<Object>());

						}else if (formField.getValue() != null
								&& !(formField.getValue().equals(""))) {
							// call the method for the source value match
							if (sourceValueMatch(sValueColl, (String) formField
									.getValue())) {
								if(formField.getValue().equals(BLNK))
									formField.setValue(null);	
								this.getTargetsForfilter(rulesDT, formFieldMap,
										fieldColl);
							} else {
								if(formField.getValue().equals(BLNK))
									formField.setValue(null);	
								resetTargetFilters(rulesDT, formFieldMap, fieldColl);
							}
						}
					}
				}
			}catch(Exception ex) {
				logger.error("Exception in PageRulesGenerator.filter: " + ex.getMessage());
				ex.printStackTrace();
			}

		}

		return fieldColl;
	}
	
	public Collection<Object>  cascadingEnableOnFilterTarget(Collection<Object> fieldColl, RulesDT rulesDT,  String sourceInd, Map<Object,Object> formFieldMap,  Map<Object,Object> answerArrayMap,String formCode, PageForm form){

		Collection<Object>  newFieldColl = new ArrayList<Object> ();
		try {
			// get the SourceField properties from the Map
			FormField formField = (FormField) formFieldMap.get(sourceInd);


			Collection<Object>  sourceColl = rulesDT.getSourceColl();
			if (sourceColl != null && sourceColl.size() > 0) {
					Iterator<Object> sourceIter = sourceColl.iterator();
					while (sourceIter.hasNext()) {
						SourceFieldDT sourceFieldDT = (SourceFieldDT) sourceIter
								.next();
						if(sourceInd != null && sourceInd.equals(sourceFieldDT.getQuestionIdentifier())){

							Collection<Object>  sValueColl = sourceFieldDT
									.getSourceValueColl();
							if (formField.getValue() == null
									||(formField.getValue().equals("")))
								formField.setValue(BLNK);

							//else if(formField.getState().getValues()!= null && formField.getState().getValues().size()>0){
							if(formField.getState().getMultiSelVals()!= null && formField.getState().getMultiSelVals().size()>0){					 
								// if((answerArrayMap != null && answerArrayMap.containsKey(sourceInd)) && answerArrayMap.get(sourceInd) != null){
								//match the multiselect
								HashMap<Object,Object> answArrMap = new HashMap<Object,Object>();
								ArrayList<Object> ansList = (ArrayList<Object> )formField.getState().getMultiSelVals();
								Iterator<Object>  iter = ansList.iterator();

								while(iter.hasNext()){
									String str = (String)iter.next();
									answArrMap.put(str, str);
								}

								if(sourceValueMatchMultiSelect(sValueColl,answArrMap)){
									this.getTargetsForfilter(rulesDT, formFieldMap, fieldColl);
								}
								else{
									resetTargetFilters(rulesDT, formFieldMap, fieldColl);
								}
								formField.getState().setMultiSelVals(new ArrayList<Object>());

							}else if (formField.getValue() != null
									&& !(formField.getValue().equals(""))) {
								// call the method for the source value match
								if (sourceValueMatch(sValueColl, (String) formField
										.getValue())) {
									if(formField.getValue().equals(BLNK))
										formField.setValue(null);	
									this.getTargetsForfilter(rulesDT, formFieldMap,
											fieldColl);
								} else {
									if(formField.getValue().equals(BLNK))
										formField.setValue(null);	
									resetTargetFilters(rulesDT, formFieldMap, fieldColl);
								}
							}

						}


					}

				}
				/*
				 * Calling the enable for the target 
				 */


				if(fieldColl != null ){
					//Map onChgRuleMap = QuestionsCache.getRuleMap();
					//Map ruleMap = (TreeMap) onChgRuleMap.get(formCode);
					Iterator<Object> iter = fieldColl.iterator();
					while(iter.hasNext()){
						FormField ff = new FormField();
						try{
							ff= (FormField)iter.next();
						} catch(Exception e){
							e.printStackTrace();
						}
						if (ff.getFieldId() != null){
							String srcInd = (String)ff.getFieldId();
							newFieldColl=  this.fireRules(srcInd, form, formFieldMap);
						}

					}

				}
			}catch(Exception ex) {
				logger.error("Exception in PageRulesGenerator.cascadingEnableOnFilterTarget: " + ex.getMessage());
				ex.printStackTrace();
			}
			fieldColl.addAll(newFieldColl);
			return fieldColl;
	}
	public Collection<Object>  singleSrcSameTargetFilter(Collection<Object> fieldColl, RulesDT rulesDT,  String sourceInd, Map<Object,Object> formFieldMap,  Map<Object,Object> answerArrayMap,String formCode, PageForm form, Map<Object,Object> ruleFiredMap){
		// get the SourceField properties from the Map
		FormField formField = (FormField) formFieldMap.get(sourceInd);
		resetTargetFilters(rulesDT, formFieldMap, fieldColl);
		Collection<Object>  sourceColl = rulesDT.getSourceColl();
		if (sourceColl != null && sourceColl.size() > 0) {
			try {
				Iterator<Object> sourceIter = sourceColl.iterator();
				while (sourceIter.hasNext()) {
					SourceFieldDT sourceFieldDT = (SourceFieldDT) sourceIter
							.next();
					if(sourceInd != null && sourceInd.equals(sourceFieldDT.getQuestionIdentifier())){

						Collection<Object>  sValueColl = sourceFieldDT
								.getSourceValueColl();
						if (formField.getValue() == null
								||(formField.getValue().equals(""))){
							formField.setValue(BLNK);
						}
						if (formField.getValue() != null
								&& !(formField.getValue().equals(""))) {
							// call the method for the source value match

							if (sourceValueMatch(sValueColl, (String) formField
									.getValue())) {
								if(formField.getValue().equals(BLNK))
									formField.setValue(null);	
								this.getTargetsForfilter(rulesDT, formFieldMap,
										fieldColl);
								ruleFiredMap.put("T", "T");
							} else {
								if(formField.getValue().equals(BLNK))
									formField.setValue(null);	
								resetTargetFilters(rulesDT, formFieldMap, fieldColl);
							}
						}
					}
				}
			}catch(Exception ex) {
				logger.error("Exception in PageRulesGenerator.singleSrcSameTargetFilter: " + ex.getMessage());
				ex.printStackTrace();
			}
		}

		return fieldColl;
	}
	

	private boolean sourceValueMatch(Collection<Object> sValueColl, String formFieldValue){
		boolean sourceMatch = true;
		if (sValueColl != null && sValueColl.size() > 0) {
			try {
				Iterator<Object> sValueIter = sValueColl.iterator();
				while (sValueIter.hasNext()) {
					SourceValueDT svDT = (SourceValueDT) sValueIter
							.next();
					if (svDT.getSourceValue()!= null) {
						if(svDT.getOperatorTypeCode()!= null && svDT.getOperatorTypeCode().equals("!=")){
							if((svDT.getSourceValue().equals(formFieldValue))){
								sourceMatch=false;
								break;
							}else{
								sourceMatch=true;
							}
						}else if(svDT.getOperatorTypeCode()!= null && svDT.getOperatorTypeCode().equals("=")){
							if((svDT.getSourceValue().equals(formFieldValue))){
								sourceMatch=true;
								break;
							}else{
								sourceMatch=false;
							}
						}
					}
				}
			}catch(Exception ex) {
				logger.error("Exception in PageRulesGenerator.sourceValueMatch: " + ex.getMessage());
				ex.printStackTrace();
			}
		}
		return sourceMatch;
	}

	private boolean sourceValueMatchMultiSelect(Collection<Object> sValueColl, Map<Object,Object> answArrMap){
		boolean sourceMatch = true;
		if (sValueColl != null && sValueColl.size() > 0) {
			try {
			Iterator<Object> sValueIter = sValueColl.iterator();
			while (sValueIter.hasNext()) {
				SourceValueDT svDT = (SourceValueDT) sValueIter
						.next();
				if (svDT.getSourceValue()!= null) {
					if(svDT.getOperatorTypeCode()!= null && svDT.getOperatorTypeCode().equals("!=")){
						if(answArrMap.containsKey((svDT.getSourceValue()))){
							sourceMatch=false;
							break;
						}else{
							sourceMatch=true;
						}
							
					}
					else if(svDT.getOperatorTypeCode()!= null && svDT.getOperatorTypeCode().equals("=")){
						if(answArrMap.containsKey((svDT.getSourceValue()))){
							sourceMatch=true;
							break;
						}else{
							sourceMatch=false;
						}
					}
				}
			}
			}catch(Exception ex) {
				logger.error("Exception in PageRulesGenerator.sourceValueMatchMultiSelect: " + ex.getMessage());
				ex.printStackTrace();
			}
		}
		return sourceMatch;
	}
	/*
	 *
	 */
	private Collection<Object>  getTargetsForfilter(RulesDT rulesDT, Map<Object,Object> formFieldMap,
			Collection<Object>  fieldColl) {
		Collection<Object>  targetColl = rulesDT.getTargetColl();
		if (targetColl != null && targetColl.size() > 0) {
			try {
				Iterator<Object> targetIter = targetColl.iterator();
				while (targetIter.hasNext()) {
					TargetFieldDT targetFieldDT = (TargetFieldDT) targetIter.next();
					// The same field can be a Target for another rule
					FormField formField = (FormField)formFieldMap.get(targetFieldDT.getQuestionIdentifier());
					formField.setFieldId(targetFieldDT.getQuestionIdentifier());
					formField.setFieldAutoCompId(targetFieldDT.getQuestionIdentifier() + "_textbox");
					formField.setFieldAutoCompBtn(targetFieldDT.getQuestionIdentifier() + "_button");
					FieldState fieldState = formField.getState();
					ArrayList<Object> tValues = (ArrayList<Object> ) targetFieldDT.getTargetValueColl();
					if(tValues!= null){
						Iterator<Object> tVIter = tValues.iterator();
						while(tVIter.hasNext()){
							TargetValueDT tvDT = (TargetValueDT)tVIter.next();

							//fieldState.setValues(new ArrayList<Object>());
							RulesUtility utility = new RulesUtility();
							if(tvDT.getConseqIndCode().equals(FILTER_REMOVE)){
								List<Object> list = new ArrayList<Object> ();
								list = utility.getNewDropDownList(fieldState.getValues(),tvDT.getTargetValue());
								fieldState.setValues(list);
								if(tvDT.getTargetValue() != null && formField.getValue() != null){
									if(tvDT.getTargetValue().equals(formField.getValue())){
										formField.setValue(null);

									}
								}
							}
							// Revisit Adding the values to the filter
							else if(tvDT.getConseqIndCode().equals(FILTER_ADD)){							
								fieldState.getValues().add(tvDT.getTargetValue());

							}
						}   

						formField.setState(fieldState);
						fieldColl.add(formField);
						if (formFieldMap.containsKey(formField.getFieldId())) {
							formFieldMap.put(formField.getFieldId(), formField);
						}
					}
					else{
						NbsQuestionMetadata qMetadata = new NbsQuestionMetadata();
						fieldState.setValues(CachedDropDowns.getCodedValueForType(qMetadata
								.getCodeSetNm()));
						formField.setState(fieldState);
					}
				}
			}catch(Exception ex) {
				logger.error("Exception in PageRulesGenerator.dateRangeLimit: " + ex.getMessage());
				ex.printStackTrace();
			}
		}
		return fieldColl;
	}

	private Collection<Object>  resetTargetFilters(RulesDT rulesDT, Map<Object,Object> formFieldMap,
			Collection<Object>  fieldColl) {
		Collection<Object>  targetColl = rulesDT.getTargetColl();
		if (targetColl != null && targetColl.size() > 0) {
			Iterator<Object> targetIter = targetColl.iterator();
			while (targetIter.hasNext()) {
				TargetFieldDT targetFieldDT = (TargetFieldDT) targetIter.next();
				FormField formField = (FormField) formFieldMap
						.get(targetFieldDT.getQuestionIdentifier());
				formField.getState().setValues(
						CachedDropDowns.getCodedValueForType(formField
								.getCodeSetNm()));
				//formField.setValue(null);
				fieldColl.add(formField);
			}
		}
		return fieldColl;
	}


	/**
	 *
	 * @param errColl
	 * @param rulesDT
	 * @param formCode
	 * @param formFieldMap
	 * @return
	 * @throws java.text.ParseException
	 */
	public Collection<Object>  dateRangeLimit(RulesDT rulesDT,
			 Map<Object,Object> formFieldMap,Collection<Object>  errColl) {

		//SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		FormField formField = (FormField) formFieldMap
				.get(rulesDT.getQuestionIdentifer());
		if(formField.getValue()!=null && !formField.getValue().equals("")){
			try {
				// check the date format
				if(isDateFormated(errColl, formField)){
					Collection<Object>  sourceColl = rulesDT.getSourceColl();
					if (sourceColl != null && sourceColl.size() > 0) {
						Iterator<Object> sourceIter = sourceColl.iterator();
						while (sourceIter.hasNext()) {
							SourceFieldDT sourceFieldDT = (SourceFieldDT) sourceIter.next();
							Collection<Object>  sValueColl = sourceFieldDT.getSourceValueColl();

							if (sValueColl != null && sValueColl.size() > 0) {
								Iterator<Object> sValueIter = sValueColl.iterator();
								while (sValueIter.hasNext()) {
									SourceValueDT svDT = (SourceValueDT) sValueIter.next();
									//FormField formField = (FormField) formFieldMap
									//		.get(sourceFieldDT.getQuestionIdentifier());
									String dataType = (String) formField.getFieldType();
									if (dataType.equals(DATE)) {

										if (svDT.getSourceValue() != null
												&& svDT.getOperatorTypeCode().equals(GREATER_THAN)) {

											String formDateV = (String)formField.getValue();
											String metaDateV = svDT.getSourceValue();
											if(!(compareDateString(formDateV,metaDateV))){
												//get the targetValueDT and the Error Message
												this.getError(rulesDT, formFieldMap, errColl,metaDateV);
											}

										}

									}

								}
							}

						}
						/*
						 * source value collection null indicates that if the source
						 * field has a value, it should enable the target fields
						 */
					}
				}

			}catch(Exception ex) {
				logger.error("Exception in PageRulesGenerator.dateRangeLimit: " + ex.getMessage());
				ex.printStackTrace();
			}
		}
		return errColl;
 
 	}

	public Collection<Object>  dateRange(Collection<Object> errColl, FormField formField,
			Map<Object,Object> formFieldMap) {
		if (formField != null && formField.getValue() != null
				&& !formField.getValue().equals("")) {
			try {
				// check the date format
				if (isDateFormated(errColl, formField)) {

					String errorMessage = null;
					String dataType = (String) formField.getFieldType();
					if (dataType.equals(NEDSSConstants.DATE)) {
						String formDateV = (String) formField.getValue();
						String metaDateV = EIGHTEEN_SEVENTYFIVE;
						if (!(compareDateString(formDateV, metaDateV))) {
							errorMessage = formField.getLabel() + ERR004;
							if (formField.getErrorMessage().size() == 0)
								formField.getErrorMessage().add(errorMessage);
							formField
							.setErrorStyleClass(RuleConstants.REQUIRED_FIELD_CLASS);
							errColl.add(formField);
						} else {
							Date date = new Date();
							SimpleDateFormat dateFormat = new SimpleDateFormat(
									"MM/dd/yyyy");
							String dateStr = dateFormat.format(date);
							if (!(compareDateGreaterEqual(dateStr, formDateV))) {
								// get the targetValueDT and the Error Message
								errorMessage = formField.getLabel() + ERR004;
								if (formField.getErrorMessage().size() == 0)
									formField.getErrorMessage().add(errorMessage);
								formField
								.setErrorStyleClass(RuleConstants.REQUIRED_FIELD_CLASS);
								errColl.add(formField);
							}
						}
						// All the dates should be greater than the Date
						// of Birth
						if (formField.getValue() != null
								&& !(formField.getFieldId()
										.equals(RuleConstants.DEM115))) {
							String currField = (String) formField.getValue();
							FormField formFieldDob = (FormField) formFieldMap
									.get(RuleConstants.DEM115);
							String dobFieldVal = (String) formFieldDob.getValue();
							if (dobFieldVal != null && !dobFieldVal.equals("")) {
								if (!compareDateGreaterEqual(currField, dobFieldVal)) {
									errorMessage = formField.getLabel() + ERR189;
									if (formField.getErrorMessage().size() == 0)
										formField.getErrorMessage().add(
												errorMessage);
									formField
									.setErrorStyleClass(RuleConstants.REQUIRED_FIELD_CLASS);
									errColl.add(formField);

								}
							}
						}
					}
				}

			}catch(Exception ex) {
				logger.error("Exception in PageRulesGenerator.dateRange: " + ex.getMessage());
				ex.printStackTrace();
			}
		}
		return errColl;
	}
	public Collection<Object>  dateRangewithFuture(Collection<Object> errColl, FormField formField,
			Map<Object,Object> formFieldMap) {
		if (formField != null && formField.getValue() != null
				&& !formField.getValue().equals("")) {
			try {
				// check the date format
				if (isDateFormated(errColl, formField)) {

					String errorMessage = null;
					String dataType = (String) formField.getFieldType();
					if (dataType.equals(NEDSSConstants.DATE)) {
						String formDateV = (String) formField.getValue();
						String metaDateV = EIGHTEEN_SEVENTYFIVE;
						if (!(compareDateString(formDateV, metaDateV))) {
							errorMessage = formField.getLabel() + ERR068;
							if (formField.getErrorMessage().size() == 0)
								formField.getErrorMessage().add(errorMessage);
							formField
							.setErrorStyleClass(RuleConstants.REQUIRED_FIELD_CLASS);
							errColl.add(formField);
						}
						// All the dates should be greater than the Date
						// of Birth
						if (formField.getValue() != null
								&& !(formField.getFieldId()
										.equals(RuleConstants.DEM115))) {
							String currField = (String) formField.getValue();
							FormField formFieldDob = (FormField) formFieldMap
									.get(RuleConstants.DEM115);
							String dobFieldVal = (String) formFieldDob.getValue();
							if (dobFieldVal != null && !dobFieldVal.equals("")) {
								if (!compareDateGreaterEqual(currField, dobFieldVal)) {
									errorMessage = formField.getLabel() + ERR189;
									if (formField.getErrorMessage().size() == 0)
										formField.getErrorMessage().add(
												errorMessage);
									formField
									.setErrorStyleClass(RuleConstants.REQUIRED_FIELD_CLASS);
									errColl.add(formField);

								}
							}
						}
					}
				}
			}catch(Exception ex) {
				logger.error("Exception in PageRulesGenerator.dateRangewithFuture: " + ex.getMessage());
				ex.printStackTrace();
			}
		}
		return errColl;
	}

	private Collection<Object>  getError(RulesDT rulesDT, Map<Object,Object> formFieldMap,
			Collection<Object>  errColl) {
		Collection<Object>  targetColl = rulesDT.getTargetColl();
		FormField formField = (FormField) formFieldMap.get(rulesDT
				.getQuestionIdentifer());
		try {
			if (formField.getValue() != null && (!formField.getValue().equals(""))) {
				if (targetColl != null && targetColl.size() > 0) {
					Iterator<Object> targetIter = targetColl.iterator();
					while (targetIter.hasNext()) {
						TargetFieldDT targetFieldDT = (TargetFieldDT) targetIter
								.next();
						ArrayList<Object> tValues = (ArrayList<Object> ) targetFieldDT
								.getTargetValueColl();
						String errorMessage = null;
						if (tValues != null) {
							Iterator<Object> tVIter = tValues.iterator();
							while (tVIter.hasNext()) {
								TargetValueDT tvDT = (TargetValueDT) tVIter.next();
								errorMessage = tvDT.getErrorDescTxt().replaceAll(
										"\\(Field Name\\)",
										targetFieldDT.getQuestionLabel());


							}

						}
						formField.getErrorMessage().add(errorMessage);
						formField.setErrorStyleClass(RuleConstants.REQUIRED_FIELD_CLASS);
						errColl.add(formField);
					}
				}
			}
		}catch(Exception ex) {
			logger.error("Exception in PageRulesGenerator.getError0: " + ex.getMessage());
			ex.printStackTrace();
		}
		return errColl;
	}
	/**
	 * This is a private method to include the static date value in the error message
	 * @param rulesDT
	 * @param formFieldMap
	 * @param errColl
	 * @param sourceValueDate
	 * @return
	 */
	private Collection<Object>  getError(RulesDT rulesDT, Map<Object,Object> formFieldMap,
			Collection<Object>  errColl, String sourceValueDate) {
		Collection<Object>  targetColl = rulesDT.getTargetColl();
		FormField formField = (FormField) formFieldMap.get(rulesDT
				.getQuestionIdentifer());
		try {
			if (formField.getValue() != null && (!formField.getValue().equals(""))) {
				if (targetColl != null && targetColl.size() > 0) {
					Iterator<Object> targetIter = targetColl.iterator();
					while (targetIter.hasNext()) {
						TargetFieldDT targetFieldDT = (TargetFieldDT) targetIter
								.next();
						ArrayList<Object> tValues = (ArrayList<Object> ) targetFieldDT
								.getTargetValueColl();
						String errorMessage = null;
						if (tValues != null) {
							Iterator<Object> tVIter = tValues.iterator();
							while (tVIter.hasNext()) {
								TargetValueDT tvDT = (TargetValueDT) tVIter.next();
								errorMessage = tvDT.getErrorDescTxt().replaceAll(
										"\\(Field Name\\)",
										targetFieldDT.getQuestionLabel()).replaceAll("\\(Date\\)",  sourceValueDate);


							}

						}
						formField.getErrorMessage().add(errorMessage);
						formField.setErrorStyleClass(RuleConstants.REQUIRED_FIELD_CLASS);
						errColl.add(formField);
					}
				}
			}
		}catch(Exception ex) {
			logger.error("Exception in PageRulesGenerator.getErrir: " + ex.getMessage());
			ex.printStackTrace();
		}
		return errColl;
	}




	/**
	 * Check whether the date format is "MM/dd/YYYY"
	 * @param errColl
	 * @param formField
	 * @return
	 */
	public boolean isDateFormated(Collection<Object> errColl, FormField formField ){
		boolean isDateFormatted =  true;
	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	if(formField.getValue() != null && !((String)formField.getValue()).equals("")){
		String sDate = (String)formField.getValue();
	  try {
		Date fdate = (Date)(dateFormat.parse(sDate));
	} catch (java.text.ParseException e) {
		String errormessage =  formField.getLabel()+DATE_FORMAT_ERROR;
		if(formField.getErrorMessage().size()==0)
			formField.getErrorMessage().add(errormessage);
		formField.setErrorStyleClass(RuleConstants.REQUIRED_FIELD_CLASS);
		errColl.add(formField);
		isDateFormatted = false;
	}

	}
	return isDateFormatted;
	}

	private boolean compareDateString(String formDateV,String metaDateV){
		Date fDate = new Date();
		Date mDate = new Date();
		try{
			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
			formatter.setLenient(false);
			fDate = (Date)formatter.parse(formDateV);
			mDate = (Date) formatter.parse(metaDateV);

		} catch(ParseException ex){
			return false;
		}
		int i = fDate.compareTo(mDate);
		if(i>0)
			return true;
		else
			return false;
	}

	private boolean compareDateGreaterEqual(String formDateV,String metaDateV){
		Date fDate = new Date();
		Date mDate = new Date();
		try{
			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
			formatter.setLenient(false);
			fDate = (Date)formatter.parse(formDateV);
			mDate = (Date) formatter.parse(metaDateV);
		}catch(ParseException ex){
			return false;
		}
		int i = fDate.compareTo(mDate);
		if(i>=0)
			return true;
		else
			return false;
	}
		public long datedifference(String targetDate, String sourceDate){
			Date sDate = new Date();
			Date tDate = new Date();
			try{
				SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
				sDate = (Date)formatter.parse(sourceDate);
				tDate = (Date) formatter.parse(targetDate);
			}catch(ParseException ex){
				ex.printStackTrace();
			}

			long diff = tDate.getTime() - sDate.getTime();

			long days = (long)( diff/(1000*60*60*24) );
			return days;
		}
		
		public long weekDifference(String targetDate, String sourceDate){
			Date sDate = new Date();
			Date tDate = new Date();
			try{
				SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
				sDate = (Date)formatter.parse(sourceDate);
				tDate = (Date) formatter.parse(targetDate);
			}catch(ParseException ex){
				ex.printStackTrace();
			}

			long diff = tDate.getTime() - sDate.getTime();

			long days = (long)( diff/(1000*60*60*24) );
			long weeks = days/7;
			return weeks;
		}
		
		public long monthDifference(String targetDate, String sourceDate){
			Date sDate = new Date();
			Date tDate = new Date();
			try{
				SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
				sDate = (Date)formatter.parse(sourceDate);
				tDate = (Date) formatter.parse(targetDate);
			}catch(ParseException ex){
				ex.printStackTrace();
			}

			long diff = tDate.getTime() - sDate.getTime();

			long days = (long)( diff/(1000*60*60*24) );
			long months = days/30;
			return months;
		}



		private boolean isValidYear(Collection<Object> errColl, FormField formField) {
		boolean isValidY = true;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
		if (formField.getValue() != null
				&& !((String) formField.getValue()).equals("")) {
			int sYear = Integer.parseInt((String) formField.getValue());
			Date currDate = new Date();
			String yearStr = dateFormat.format(currDate);
			int currYear = Integer.parseInt(yearStr);
			int lessYear = Integer.parseInt(NEDSSConstants.EIGHTEEN_SEVENTYFIVE);
			if (sYear <= lessYear || sYear > currYear) {

				String errormessage = formField.getLabel()
						+ RuleConstants.ERR147;
				if (formField.getErrorMessage().size() == 0)
					formField.getErrorMessage().add(errormessage);
				formField
						.setErrorStyleClass(RuleConstants.REQUIRED_FIELD_CLASS);
				errColl.add(formField);
				isValidY = false;
			}

		}
		return isValidY;
	}
		
		public Collection<Object>  validYear(Collection<Object> errColl, RulesDT rulesDT, Map<Object,Object> formFieldMap) {
			try {
				boolean isValidY = true;
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
				FormField formField = (FormField)formFieldMap.get(rulesDT.getQuestionIdentifer());
				if (formField.getValue() != null
						&& !((String) formField.getValue()).equals("")) {
					int sYear = Integer.parseInt((String) formField.getValue());
					Date currDate = new Date();
					String yearStr = dateFormat.format(currDate);
					int currYear = Integer.parseInt(yearStr);
					int lessYear = Integer.parseInt(NEDSSConstants.EIGHTEEN_SEVENTYFIVE);
					if (sYear <= lessYear || sYear > currYear) {

						String errormessage = formField.getLabel()
								+ RuleConstants.ERR147;
						if (formField.getErrorMessage().size() == 0)
							formField.getErrorMessage().add(errormessage);
						formField
						.setErrorStyleClass(RuleConstants.REQUIRED_FIELD_CLASS);
						errColl.add(formField);

					}

				}
			}catch(Exception ex) {
				logger.error("Exception in PageRulesGenerator.validyear: " + ex.getMessage());
				ex.printStackTrace();
			}
			return errColl;
		}
	


    public Collection<Object>  getRequiredField(Collection<Object> fieldColl,
			FormField formField, Map<Object,Object> formFieldMap, Map<Object,Object> answerArrayMap) {
    	try {
    		if (formField != null) {
    			if (formField.getState().isRequired()) {
    				/*
    				 * This check if or the multiselect fields 
    				 */
    				if (answerArrayMap != null
    						&& answerArrayMap.containsKey(formField.getFieldId())) {
    					String[] answers = (String[]) answerArrayMap.get(formField
    							.getFieldId());
    					if (answers == null
    							|| (answers != null && answers.length <= 0)) {
    						if (formField.getErrorMessage().size() == 0)
    							formField.getErrorMessage().add(
    									formField.getLabel() + " is required");
    						formField
    						.setErrorStyleClass(RuleConstants.REQUIRED_FIELD_CLASS);
    						fieldColl.add(formField);
    					}
    					/*
    					 * non_multiselect fields
    					 */
    				} else if ((formField.getValue() == null || formField
    						.getValue().equals(""))) {

    					if (formField.getErrorMessage().size() == 0)
    						formField.getErrorMessage().add(
    								formField.getLabel() + " is required");
    					formField
    					.setErrorStyleClass(RuleConstants.REQUIRED_FIELD_CLASS);
    					fieldColl.add(formField);

    				}

    			}
    		}
    	}catch(Exception ex) {
    		logger.error("Exception in PageRulesGenerator.getRequiredField: " + ex.getMessage());
    		ex.printStackTrace();
    	}
    	return fieldColl;
	}

    /**
     * requiredForNotification - Used to Retrieve only the NND Required fields that are missing in the UI part(as some of the NND Req like LocalIDs are not in UI, but just stored in db)
     * @param fieldColl
     * @param formFieldMap
     * @return
     */
    public Collection<Object>  requiredForNotification(Collection<Object> fieldColl, Map<Object,Object> formFieldMap, PageForm form) {
    	
    	Map<?,?> notifReqMap = (TreeMap<?,?>) form.getAttributeMap().get("NotifReqMap");
    	if(notifReqMap != null && notifReqMap.size() > 0) {
    		Iterator<?> iter = notifReqMap.keySet().iterator();
    		while(iter.hasNext()) {
    			String questionId = (String) iter.next();
    			FormField formField = (FormField) formFieldMap.get(questionId);
    			if(formField.getValue()== null || (formField.getValue() != null && formField.getValue().equals(""))) {
    				if(formField.getErrorMessage() != null && formField.getErrorMessage().size()==0)
    					formField.getErrorMessage().add(formField.getLabel()+" is required for NND");
    				formField.setErrorStyleClass(RuleConstants.REQUIRED_FIELD_CLASS);
    				fieldColl.add(formField);
    			}
    			
    		}
    	}
    	return fieldColl;
    }
    
    public Collection<Object>  calculateMMWR(Collection<Object> fieldColl, RulesDT rulesDT,
			String sourceInd, Map<Object,Object> formFieldMap, String frmCode) {
    	ArrayList<Object> targetfields = new ArrayList<Object> ();
    	String dateValue = null;
    	
    	try {
    		
    	if (frmCode.equals(NBSConstantUtil.INV_FORM_RVCT)) {
			targetfields = (ArrayList<Object> ) rulesDT.getTargetColl();
		    dateValue = (String) ((FormField) formFieldMap.get(sourceInd))
					.getValue();
			
		}
    	else if (frmCode.equals(NBSConstantUtil.INV_FORM_VAR)) {
    		Calendar cal = Calendar.getInstance();
    	
    		SimpleDateFormat dateFormat = new SimpleDateFormat("mm/dd/yyyy");
			//Timestamp currDate = new Timestamp(new Date().getTime());
    		
			dateValue = (cal.get(Calendar.MONTH)+1) +"/" + cal.get(Calendar.DATE) +"/" + cal.get(Calendar.YEAR);
       }
		  if (dateValue != null) {
			  int[] weekAndYear = CalcMMWR(dateValue);		    
			  String mmwrYearId = null;
			  String mmwrWeekId = null;
			if (frmCode.equals(NBSConstantUtil.INV_FORM_RVCT)) {
				if (targetfields != null && targetfields.size() == 2) {
					if (((TargetFieldDT) targetfields.get(0))
							.getQuestionLabel().indexOf("Week") == -1) {
						mmwrYearId = ((TargetFieldDT) targetfields.get(0))
								.getQuestionIdentifier();
					} else {
						mmwrWeekId = ((TargetFieldDT) targetfields.get(0))
								.getQuestionIdentifier();
					}
					if (((TargetFieldDT) targetfields.get(1))
							.getQuestionLabel().indexOf("Year") == -1) {
						mmwrWeekId = ((TargetFieldDT) targetfields.get(1))
								.getQuestionIdentifier();
					} else {
						mmwrYearId = ((TargetFieldDT) targetfields.get(1))
								.getQuestionIdentifier();
					}
					if (mmwrYearId != null && mmwrWeekId != null) {
						FormField fField = (FormField) formFieldMap
								.get(mmwrWeekId);
						if (weekAndYear[0] != 0) {
							fField.setValue(Integer.toString(weekAndYear[0]));
							if(fieldColl != null)
								fieldColl.add(fField);
						}
						if (weekAndYear[1] != 0) {
							fField = (FormField) formFieldMap.get(mmwrYearId);
							fField.setValue(Integer.toString(weekAndYear[1]));
							if(fieldColl != null)
								fieldColl.add(fField);
						}

					}
				}
			} else if (frmCode.equals(NBSConstantUtil.INV_FORM_VAR)) {   
				if(fieldColl == null)
					fieldColl=new ArrayList<Object> ();
				if (weekAndYear[0] != 0){
					FormField fField = (FormField) formFieldMap
					.get(INV165);
					fField.setValue(Integer.toString(weekAndYear[0]));
					fieldColl.add(fField);
				}
				if (weekAndYear[1] != 0){
					FormField fField = (FormField) formFieldMap.get(INV166);
					fField.setValue(Integer.toString(weekAndYear[1]));
					fieldColl.add(fField);
				}
				
			}
		}else{
			FormField fField = (FormField) formFieldMap
			.get(INV165);
			fField.setValue(null);			
			FormField fField1 = (FormField) formFieldMap
			.get(INV166);
			fField1.setValue(null);
			fieldColl.add(fField);
			fieldColl.add(fField1);
			
			
		}
		}catch(Exception ex) {
			logger.error("Exception in PageRulesGenerator.calculateMMWR2: " + ex.getMessage());
			ex.printStackTrace();
		}
		return fieldColl;
	}
	/**
	 * Calculates the ordinal week # and 4-digit year for the specified date
	 * string (in mm/dd/yyyy format).
	 */
	public int[] getMMWRWeekAndYear(String date) {

		int[] weekYear = { 0, 0 };

		Date mmwrDate = null;
		try {
			mmwrDate = new SimpleDateFormat("MM/dd/yyyy").parse(date);
		} catch (Exception e) {
		}

		if (mmwrDate != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(mmwrDate);
			cal.setFirstDayOfWeek(Calendar.SUNDAY);
			cal.setMinimalDaysInFirstWeek(7);

			weekYear[0] = cal.get(Calendar.WEEK_OF_YEAR);
			weekYear[1] = cal.get(Calendar.YEAR);
		}
		return weekYear;
	}

	public int[] CalcMMWR(String pDate)
	{
	    //  Create return variable.
	    int[] r = {0,0};
	    try{
	    //  Define constants.
	    int SECOND = 1000;
	    int MINUTE = 60 * SECOND;
	    int HOUR = 60 * MINUTE;
	    int DAY = 24 * HOUR;
	    int WEEK = 7 * DAY;
	    //  Convert to date object.
	    Date varDate = new SimpleDateFormat("MM/dd/yyyy").parse(pDate);
	    Calendar cal = Calendar.getInstance();
		cal.setTime(varDate);
	    long varTime = cal.getTimeInMillis();
	    //  Get January 1st of given year.

	    Date varJan1Date = new SimpleDateFormat("MM/dd/yyyy").parse("01/01/"+cal.get(Calendar.YEAR));
	    Calendar calJan1 = Calendar.getInstance();
	    calJan1.setTime(varJan1Date);
	    int varJan1Day = calJan1.get(Calendar.DAY_OF_WEEK);
	    long varJan1Time = calJan1.getTimeInMillis();
	    //  Create temp variables.
	    long t = varJan1Time;
	    Date d = null;
	    int h = 0;
	    //  MMWR Year.
	    int y = calJan1.get(Calendar.YEAR);
	    //  MMWR Week.
	    int w = 0;
	    //  Find first day of MMWR Year.
	    if(varJan1Day < 5)
	    {
	        //  If SUN, MON, TUE, or WED, go back to nearest Sunday.
	        t -= ((varJan1Day-1) * DAY);
	        //  Loop through each week until we reach the given date.
	        while(t < varTime)
	        {
	            //  Increment the week counter.
	            w++;
	            t += WEEK;
	            //  Adjust for daylight savings time as necessary.
	            d = new Date(t);
	            Calendar cal1 = Calendar.getInstance();
	            cal1.setTime(d);
	            h = cal1.get(Calendar.HOUR);
	            if(h == 1)
	            {
	                t -= HOUR;
	            }
	            if(h == 23 || h == 11)
	            {
	                t += HOUR;
	            }
	        }
	        //  If at end of year, move on to next year if this week has
	        //  more days from next year than from this year.
	        if(w == 53)
	        {
	        	Date varNextJan1Date = new SimpleDateFormat("MM/dd/yyyy").parse("01/01/"+(cal.get(Calendar.YEAR)+1));
	        	Calendar varNextJan1Cal = Calendar.getInstance();
	        	varNextJan1Cal.setTime(varNextJan1Date);
	        	int varNextJan1Day = varNextJan1Cal.get(Calendar.DAY_OF_WEEK);
	            if(varNextJan1Day < 5)
	            {
	                y++;
	                w = 1;
	            }
	        }
	    }
	    else
	    {
	        //  If THU, FRI, or SAT, go forward to nearest Sunday.
	        t += ((7 - (varJan1Day-1)) * DAY);
	        //  Loop through each week until we reach the given date.
	        while(t <= varTime)
	        {
	            //  Increment the week counter.
	            w++;
	            d = new Date(t);
//	          s += "b, " + w + "&nbsp;&nbsp;&nbsp;&nbsp;" + d.toString() + "<br/>";
	            //  Move on to the next week.
	            t += WEEK;
	            //  Adjust for daylight savings time as necessary.
	            d = new Date(t);
	            Calendar dCal = Calendar.getInstance();
	            dCal.setTime(d);
	            h = dCal.get(Calendar.HOUR);
	            if(h == 1)
	            {
	                t -= HOUR;
	            }
	            if(h == 23)
	            {
	                t += HOUR;
	            }
	        }
	        //  If at beginning of year, move back to previous year if this week has
	        //  more days from last year than from this year.

	        if(w == 0)
	        {
	            d = new Date(t);
	            Calendar dCal1 = Calendar.getInstance();
	            dCal1.setTime(d);
	            if( (dCal1.get(Calendar.MONTH) == 0) && (dCal1.get(Calendar.DAY_OF_WEEK) <= 5) )
	            {
	                y--;
	                int a[] = CalcMMWR("12/31/" + y);
	                w = a[0];
	            }
	        }
	    }
	    //  Zero pad left.
//	    if(w < 10)
//	    {
//	        w = "0" + w;
//	    }
	    //  Assemble result.
	    r[0] = w;
	    r[1] = y;
	    }
	    catch(Exception ex)
	    {
	    	logger.error("Exception in PageRUlesGenerator.CalcMMWR: " + ex.getMessage());
	    	ex.printStackTrace();
	    }
	    //  Return result.
	    return r;
	}

	public Collection<Object>  dateCompare(RulesDT rulesDT, Map<Object,Object> formFieldMap,
			Collection<Object>  errColl) {
		try {
			Collection<Object>  srcColl = rulesDT.getSourceColl();
			FormField srcformField = (FormField) formFieldMap.get(rulesDT
					.getQuestionIdentifer());
			// check the date format of the SourceFormField
			this.isDateFormated(errColl, srcformField);
			if (srcformField.getValue() != null
					&& !(srcformField.getValue().equals(""))) {
				if (srcColl != null) {
					Iterator<Object> srcIter = srcColl.iterator();
					while (srcIter.hasNext()) {
						SourceFieldDT sourceFieldDT = (SourceFieldDT) srcIter
								.next();
						Collection<Object>  srcValueColl = sourceFieldDT
								.getSourceValueColl();
						if (srcValueColl != null) {
							Iterator<Object> srcVIter = srcValueColl.iterator();
							while (srcVIter.hasNext()) {
								SourceValueDT sourceValueDT = (SourceValueDT) srcVIter
										.next();
								String errorMessage = null;
								TargetValueDT targetValueDT = new TargetValueDT();

								if (sourceValueDT.getOperatorTypeCode().equals(
										NEDSSConstants.LESS_THAN_EQUAL)) {
									// get the target date to be compared
									TargetFieldDT targetFieldDT = new TargetFieldDT();
									Collection<Object>  targetColl = rulesDT.getTargetColl();
									if (targetColl != null && targetColl.size() > 0) {
										Iterator<Object> targetIter = targetColl.iterator();
										while (targetIter.hasNext()) {
											targetFieldDT = (TargetFieldDT) targetIter
													.next();
											// get the target Value for ErrorMessage
											Collection<Object>  tValueColl = targetFieldDT
													.getTargetValueColl();
											if (tValueColl != null) {
												Iterator<Object> tValueIter = tValueColl
														.iterator();
												while (tValueIter.hasNext()) {
													targetValueDT = (TargetValueDT) tValueIter
															.next();
												}
											}
											FormField tgtformField = (FormField) formFieldMap
													.get(targetFieldDT
															.getQuestionIdentifier());
											// check the dateFormat of the
											// TgtformField
											if (isDateFormated(errColl,
													tgtformField)) {
												if ((tgtformField != null && (tgtformField
														.getValue() != null && !(tgtformField
																.getValue().equals(""))))
																&& (srcformField != null && srcformField
																.getValue() != null)) {
													if (compareDateString(
															(String) srcformField
															.getValue(),
															(String) tgtformField
															.getValue())) {
														if (targetValueDT != null
																&& targetValueDT
																.getErrorDescTxt() != null) {
															errorMessage = (targetValueDT
																	.getErrorDescTxt()
																	.replaceAll(
																			"\\(Field Name1\\)",
																			targetFieldDT
																			.getQuestionLabel()))
																			.replaceAll(
																					"\\(Field Name2\\)",
																					sourceFieldDT
																					.getQuestionLabel());
															srcformField
															.getErrorMessage()
															.add(
																	errorMessage);
															tgtformField
															.setErrorStyleClass(RuleConstants.REQUIRED_FIELD_CLASS);
															srcformField
															.setErrorStyleClass(RuleConstants.REQUIRED_FIELD_CLASS);
															errColl
															.add(tgtformField);
															errColl
															.add(srcformField);
														}
													}
												}
											}
										}

									}
								}

							}
						}
					}
				}
			}
		}catch(Exception ex) {
			logger.error("Exception in PageRUlesGenerator.dateCompare: " + ex.getMessage());
			ex.printStackTrace();
		}
		return errColl;
	}


	private TargetFieldDT getTarget(RulesDT rulesDT){
		TargetFieldDT targetFieldDT = new TargetFieldDT();
		Collection<Object>  targetColl = rulesDT.getTargetColl();
		Iterator<Object> targetIter = targetColl.iterator();
		// this specific case has only one value in the target
		while (targetIter.hasNext()){
			targetFieldDT = (TargetFieldDT)targetIter.next();
		}

		return targetFieldDT;
	}

	public Collection<Object>  matchValue(Collection<Object> errColl, RulesDT rulesDT, Map<Object,Object> formFieldMap, Map<Object,Object> answerArrayMap){
		try {
			FormField srcformField = (FormField) formFieldMap.get(rulesDT.getQuestionIdentifer());

			String errorMessage = null;
			TargetValueDT targetValueDT = new TargetValueDT();
			// get the target
			TargetFieldDT targetFieldDT = this.getTarget(rulesDT);
			//get the target Value for ErrorMessage
			Collection<Object>  tValueColl =  targetFieldDT.getTargetValueColl();
			if(tValueColl != null){
				Iterator<Object> tValueIter = tValueColl.iterator();
				while(tValueIter.hasNext()){
					targetValueDT = (TargetValueDT)tValueIter.next();
				}
			}
			FormField tgtformField = (FormField)formFieldMap.get(targetFieldDT.getQuestionIdentifier());
			if((tgtformField != null && (tgtformField.getValue() != null && !(tgtformField.getValue().equals(""))))){
				if(answerArrayMap != null){
					if(answerArrayMap.containsKey(srcformField.getFieldId())){
						String[] answArr = (String[])answerArrayMap.get(srcformField.getFieldId());
						HashMap<Object,Object> answArrMap = new HashMap<Object,Object>();
						if(answArr!=null && answArr.length>0){

							for (int i = 0; i < answArr.length; i++) {
								answArrMap.put(answArr[i], answArr[i]);
							}
						}
						//while(iter.hasNext()){

						if(answArrMap.containsKey(tgtformField.getValue())){
							errorMessage = (targetValueDT.getErrorDescTxt().replaceAll(
									"\\(Field Name1\\)",
									srcformField.getLabel()).replaceAll(
											"\\(Field Name2\\)",
											tgtformField.getLabel()));
							tgtformField.getErrorMessage().add(errorMessage);
							tgtformField.setErrorStyleClass(RuleConstants.REQUIRED_FIELD_CLASS);
							// change the color of the source field also
							srcformField.setErrorStyleClass(RuleConstants.REQUIRED_FIELD_CLASS);
							errColl.add(tgtformField);
							errColl.add(srcformField);

						}
						//}
						//}
					}
				}
				if(srcformField.getValue() != null && !(srcformField.getValue().equals(""))){
					if (srcformField.getValue().equals(tgtformField.getValue())){
						errorMessage = (targetValueDT.getErrorDescTxt().replaceAll(
								"\\(Field Name1\\)",
								srcformField.getLabel()).replaceAll(
										"\\(Field Name2\\)",
										tgtformField.getLabel()));
						tgtformField.getErrorMessage().add(errorMessage);
						tgtformField.setErrorStyleClass(RuleConstants.REQUIRED_FIELD_CLASS);
						// change the color of the source field also
						srcformField.setErrorStyleClass(RuleConstants.REQUIRED_FIELD_CLASS);
						errColl.add(tgtformField);
						errColl.add(srcformField);
					}
				}



			}
		}catch(Exception ex) {
			logger.error("Exception in PageRUlesGenerator.dateCompare: " + ex.getMessage());
			ex.printStackTrace();
		}		
		return errColl;
	}
    /**
     * This method is basically for the As of data and 
     * also for the create Submit if it has an consed_ind as D
     * sources are the list of fields where the target is just one. 
     * If any of the source field has a value the target field can not be null
     * @param errColl
     * @param rulesDT
     * @param formFieldMap
     * @param actionMode
     * @return
     */
	public Collection<Object>  requiredIf(Collection<Object> errColl, RulesDT rulesDT,
			Map<Object,Object> formFieldMap, String actionMode) {
		try {
			FormField srcFormField = new FormField();
			FormField tgtFormField = new FormField();
			Collection<Object>  srcColl = rulesDT.getSourceColl();
			boolean metContition = false;
			if (srcColl != null) {
				Iterator<Object> srcIter = srcColl.iterator();
				while (srcIter.hasNext()) {

					SourceFieldDT sourceFieldDT = (SourceFieldDT) srcIter.next();
					// The form value for the source
					srcFormField = (FormField) formFieldMap.get(sourceFieldDT
							.getQuestionIdentifier());
					Collection<Object>  srcValColl = sourceFieldDT.getSourceValueColl();
					if (srcValColl != null && srcValColl.size() > 0) {
						Iterator<Object> svIter = srcValColl.iterator();
						while (svIter.hasNext()) {
							SourceValueDT sourceValueDT = (SourceValueDT) svIter
									.next();
							if (sourceValueDT.getOperatorTypeCode() != null
									&& sourceValueDT.getOperatorTypeCode().equals(
											EQUAL)) {
								if (sourceValueDT.getSourceValue() != null) {
									if (srcFormField != null
											&& srcFormField.getValue() != null
											&& srcFormField.getValue().equals(
													sourceValueDT.getSourceValue())) {
										metContition = true;
										break;

									}

								}
							}
						}
					}
					// this section will execute if the source value is not entered
					else if (srcFormField != null
							&& (srcFormField.getValue() != null && !srcFormField
							.getValue().equals(""))){
						metContition = true;
						break;
					}

				}
			}
			if(metContition){
				TargetFieldDT targetFieldDT = this.getTarget(rulesDT);
				tgtFormField = (FormField) formFieldMap.get(targetFieldDT
						.getQuestionIdentifier());
				if (srcFormField != null
						&& (srcFormField.getValue() != null && !srcFormField.getValue()
						.equals(""))) {
					TargetValueDT targetValueDT = new TargetValueDT();

					if (tgtFormField.getValue() == null
							|| (tgtFormField.getValue() != null && tgtFormField
							.getValue().equals(""))) {

						// get the target Value for ErrorMessage
						Collection<Object>  tValueColl = targetFieldDT.getTargetValueColl();
						if (tValueColl != null) {
							Iterator<Object> tValueIter = tValueColl.iterator();
							while (tValueIter.hasNext()) {
								targetValueDT = (TargetValueDT) tValueIter.next();
							}
						}
						String errorMessage = null;
						// if the action mode is EDIT_SUBMIT- work for edit only required fields 
						if ((actionMode != null && actionMode
								.equals(NEDSSConstants.EDIT_SUBMIT_ACTION))
								&& (rulesDT.getConseqIndCode() != null && !rulesDT
								.getConseqIndCode().equals("D"))) {

							tgtFormField.getErrorMessage().add(
									targetValueDT.getErrorDescTxt()
									.replaceAll("\\(Field Name\\)",
											tgtFormField.getLabel()));

							tgtFormField
							.setErrorStyleClass(RuleConstants.REQUIRED_FIELD_CLASS);
							//srcFormField.setErrorStyleClass(RuleConstants.REQUIRED_FIELD_CLASS);
							errColl.add(tgtFormField);
							// requiredif validation for CREATE_SUBMIT with consequence
							// ind='D' onCreateSubmit - if required for both add and edit 
						} else if (actionMode != null
								&& (actionMode
										.equals(NEDSSConstants.CREATE_SUBMIT_ACTION) || actionMode
										.equals(NEDSSConstants.EDIT_SUBMIT_ACTION))
										&& rulesDT.getConseqIndCode().equals("D")) {

							if (targetValueDT.getErrorDescTxt()!=null ) {
								tgtFormField.getErrorMessage().add(
										targetValueDT.getErrorDescTxt().replaceAll(
												"\\(Field Name1\\)",
												tgtFormField.getLabel()).replaceAll(
														"\\(Field Name2\\)",
														srcFormField.getLabel()));
							}
							tgtFormField
							.setErrorStyleClass(RuleConstants.REQUIRED_FIELD_CLASS);
							srcFormField.setErrorStyleClass(RuleConstants.REQUIRED_FIELD_CLASS);
							errColl.add(tgtFormField);

						}


					}
				} else {
					tgtFormField.setValue(null);
					errColl.add(tgtFormField);

				}
			}
		}catch(Exception ex) {
			logger.error("Exception in PageRulesGenerator.requiredIf: " + ex.getMessage());
			ex.printStackTrace();
		}
		return errColl;
	}

	public Collection<Object>  yearRange(Collection<Object> errColl, RulesDT rulesDT,
	Map<Object,Object> formFieldMap, String ruleName){
		return yearRangeCommon(errColl, rulesDT, formFieldMap,ruleName);

	}

	public Collection<Object>  yearRangeEqual(Collection<Object> errColl, RulesDT rulesDT,
			Map<Object,Object> formFieldMap, String ruleName){
		return yearRangeCommon(errColl, rulesDT, formFieldMap,ruleName);
	}


	private Collection<Object>  yearRangeCommon(Collection<Object> errColl, RulesDT rulesDT,
			Map<Object,Object> formFieldMap, String ruleName) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			FormField srcFormField = (FormField) formFieldMap.get(rulesDT
					.getQuestionIdentifer());
			FormField tgtFormField = new FormField();
			int sourceYear = 0;
			int tgtYear = 0;

			String srcValue = null;
			if (srcFormField != null && srcFormField.getValue() != null) {
				srcValue = (String) srcFormField.getValue();
			}
			// check the date format of the source
			if (srcValue != null && srcValue.length() == 4) {
				if(this.isValidYear(errColl, srcFormField)){
					sourceYear = Integer.parseInt(srcValue);
				}
			} else if ( (srcFormField.getFieldType() != null && srcFormField.getFieldType().equals(NEDSSConstants.DATE)) && ((srcValue != null && !srcValue.equals("")) && (srcValue.length() != 4))){
				if(isDateFormated(errColl, srcFormField)) {

					//if((tgtValue != null && tgtValue.length() == 4)||(isDateFormated(errColl,tgtFormField))){
					Date sourceDate = null;

					if (srcFormField != null
							&& (srcValue != null && !srcFormField.getValue().equals(""))) {
						try {
							sourceDate = dateFormat.parse((String) srcFormField
									.getValue());
							//tgtDate = dateFormat.parse((String)tgtFormField.getValue());
						} catch (Exception e) {
						}
						if (sourceDate != null) {
							Calendar cal = Calendar.getInstance();
							cal.setTime(sourceDate);
							sourceYear = cal.get(Calendar.YEAR);
						}
					}
				}
			}
			TargetFieldDT targetFieldDT = this.getTarget(rulesDT);
			tgtFormField = (FormField) formFieldMap.get(targetFieldDT
					.getQuestionIdentifier());
			String tgtValue = null;
			if (tgtFormField != null && tgtFormField.getValue() != null) {
				tgtValue = (String) tgtFormField.getValue();
			}
			if (tgtValue != null && tgtValue.length() == 4) {
				tgtYear = Integer.parseInt(tgtValue);
			} else if (((tgtFormField.getFieldType()!= null && tgtFormField.getFieldType().equals(NEDSSConstants.DATE))&&(tgtValue != null && !tgtValue.equals(""))&& tgtValue.length() != 4)){
				if (isDateFormated(errColl, tgtFormField)) {
					Date tgtDate = null;

					if (tgtFormField != null
							&& (tgtValue != null && !tgtValue.equals(""))) {
						try {
							tgtDate = dateFormat.parse(tgtValue);
							//tgtDate = dateFormat.parse((String)tgtFormField.getValue());
						} catch (Exception e) {
						}

						if (tgtDate != null) {
							Calendar cal = Calendar.getInstance();
							cal.setTime(tgtDate);
							tgtYear = cal.get(Calendar.YEAR);
						}
					}
				}
			}

			boolean isError = false;

			if ((ruleName.equals(NEDSSConstants.YEAR_RANGE))&&((sourceYear>0 && tgtYear>0) && (sourceYear >= tgtYear))) {
				isError = true;
			}
			if ((ruleName.equals(NEDSSConstants.YEAR_RANGE_EQUAL))&&((sourceYear>0 && tgtYear>0) && (sourceYear > tgtYear))) {
				isError = true;
			}
			if(isError){
				TargetValueDT targetValueDT = new TargetValueDT();
				//get the target Value for ErrorMessage
				Collection<Object>  tValueColl = targetFieldDT.getTargetValueColl();
				if (tValueColl != null) {
					Iterator<Object> tValueIter = tValueColl.iterator();
					while (tValueIter.hasNext()) {
						targetValueDT = (TargetValueDT) tValueIter.next();
					}
				}
				String errorMessage = null;
				if (targetValueDT != null) {
					errorMessage = (targetValueDT.getErrorDescTxt().replaceAll(
							"\\(Field Name1\\)", tgtFormField.getLabel())
							.replaceAll("\\(Field Name2\\)", srcFormField
									.getLabel()));
				}

				srcFormField.getErrorMessage().add(errorMessage);
				tgtFormField.setErrorStyleClass(RuleConstants.REQUIRED_FIELD_CLASS);
				srcFormField.setErrorStyleClass(RuleConstants.REQUIRED_FIELD_CLASS);
				errColl.add(tgtFormField);
				errColl.add(srcFormField);

			}
		}catch(Exception ex) {
			logger.error("Exception in PageRUlesGenerator.yearRangeCommon: " + ex.getMessage());
			ex.printStackTrace();
		}
		return errColl;
	}
	
public  Collection<Object>  yearRangeLimit(Collection<Object> errColl, RulesDT rulesDT,
			Map<Object,Object> formFieldMap) {
	try {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		FormField srcFormField = (FormField) formFieldMap.get(rulesDT
				.getQuestionIdentifer());
		FormField tgtFormField = new FormField();
		int sourceYear = 0;
		int tgtYear = 0;

		String srcValue = null;
		if (srcFormField != null && srcFormField.getValue() != null) {
			srcValue = (String) srcFormField.getValue();
		}
		// check the date format of the source
		if (srcValue != null && srcValue.length() == 4) {
			if(this.isValidYear(errColl, srcFormField)){
				sourceYear = Integer.parseInt(srcValue);
			}
		} else if ( (srcFormField.getFieldType() != null && srcFormField.getFieldType().equals(NEDSSConstants.DATE)) && ((srcValue != null && !srcValue.equals("")) && (srcValue.length() != 4))){
			if(isDateFormated(errColl, srcFormField)) {

				//if((tgtValue != null && tgtValue.length() == 4)||(isDateFormated(errColl,tgtFormField))){
				Date sourceDate = null;

				if (srcFormField != null
						&& (srcValue != null && !srcFormField.getValue().equals("")) && srcValue.length()!=4) {
					try {
						sourceDate = dateFormat.parse((String) srcFormField
								.getValue());
						//tgtDate = dateFormat.parse((String)tgtFormField.getValue());
					} catch (Exception e) {
					}
					if (sourceDate != null) {
						Calendar cal = Calendar.getInstance();
						cal.setTime(sourceDate);
						sourceYear = cal.get(Calendar.YEAR);
					}
				}
			}
		}
		TargetFieldDT targetFieldDT = this.getTarget(rulesDT);
		tgtFormField = (FormField) formFieldMap.get(targetFieldDT
				.getQuestionIdentifier());
		String tgtValue = null;
		if (tgtFormField != null && tgtFormField.getValue() != null) {
			tgtValue = (String) tgtFormField.getValue();
		}
		if (tgtValue != null && tgtValue.length() == 4) {
			tgtYear = Integer.parseInt(tgtValue);
		} else if (((tgtFormField.getFieldType()!= null && tgtFormField.getFieldType().equals(NEDSSConstants.DATE))&&(tgtValue != null && !tgtValue.equals(""))&& tgtValue.length() != 4)){
			if (isDateFormated(errColl, tgtFormField)) {
				Date tgtDate = null;

				if (tgtFormField != null
						&& (tgtValue != null && !tgtValue.equals(""))) {
					try {
						tgtDate = dateFormat.parse(tgtValue);
						//tgtDate = dateFormat.parse((String)tgtFormField.getValue());
					} catch (Exception e) {
					}

					if (tgtDate != null) {
						Calendar cal = Calendar.getInstance();
						cal.setTime(tgtDate);
						tgtYear = cal.get(Calendar.YEAR);
					}
				}
			}
		}

		boolean isError = false;
		/* because the target is year field and the rule compares the year value 
		 * of the date field with the 
		 * that as 12 months or 1 year( as per Christi the difference should be 2) */ 
		String srcVal = this.getSourceValue(rulesDT); 
		if (((sourceYear>0 && tgtYear>0) && ((sourceYear-tgtYear)<Integer.parseInt(srcVal)))) {
			isError = true;
		}
		if(isError){
			TargetValueDT targetValueDT = new TargetValueDT();
			//get the target Value for ErrorMessage
			Collection<Object>  tValueColl = targetFieldDT.getTargetValueColl();
			if (tValueColl != null) {
				Iterator<Object> tValueIter = tValueColl.iterator();
				while (tValueIter.hasNext()) {
					targetValueDT = (TargetValueDT) tValueIter.next();
				}
			}
			String errorMessage = null;
			if (targetValueDT != null) {
				errorMessage = (targetValueDT.getErrorDescTxt().replaceAll(
						"\\(Field Name1\\)", srcFormField.getLabel())
						.replaceAll("\\(Field Name2\\)", tgtFormField
								.getLabel()));
			}

			srcFormField.getErrorMessage().add(errorMessage);
			tgtFormField.setErrorStyleClass(RuleConstants.REQUIRED_FIELD_CLASS);
			srcFormField.setErrorStyleClass(RuleConstants.REQUIRED_FIELD_CLASS);
			errColl.add(tgtFormField);
			errColl.add(srcFormField);

		}
	}catch(Exception ex) {
		logger.error("Exception in PageRUlesGenerator.yearRangeLimit: " + ex.getMessage());
		ex.printStackTrace();
	}
	return errColl;
	}
	
	private String getSourceValue(RulesDT rulesDT){
		String srcVal=null;
		Collection<Object>  srcFldColl = rulesDT.getSourceColl();
		if(srcFldColl != null){
			Iterator<Object> srcIter  = srcFldColl.iterator();
			while(srcIter.hasNext()){
				SourceFieldDT srcFldDT = (SourceFieldDT)srcIter.next();
				Collection<Object>  srcValColl = srcFldDT.getSourceValueColl();
				if(srcValColl !=null){
					Iterator<Object> srcValItr = srcValColl.iterator();
					while(srcValItr.hasNext()){
						SourceValueDT srcValDT = (SourceValueDT)srcValItr.next();
						srcVal = srcValDT.getSourceValue();
						
					}
				}
			}
		}	
			
		return srcVal;
	}




public Collection<Object>  validLength(Collection<Object> errColl, RulesDT rulesDT,
			Map<Object,Object> formFieldMap) {
	try {
		FormField srcformField = (FormField) formFieldMap.get(rulesDT
				.getQuestionIdentifer());
		if (srcformField.getValue() != null
				&& !(srcformField.getValue().equals(""))) {
			Collection<Object>  srcColl = rulesDT.getSourceColl();
			if (srcColl != null) {
				Iterator<Object> srcIter = srcColl.iterator();
				while (srcIter.hasNext()) {
					SourceFieldDT sourceFieldDT = (SourceFieldDT) srcIter
							.next();
					Collection<Object>  srcVColl = sourceFieldDT.getSourceValueColl();
					if (srcVColl != null) {
						Iterator<Object> srcVIter = srcVColl.iterator();

						Iterator<Object> sValueIter1  = srcVColl.iterator();
						HashMap<Object,Object> map = new HashMap<Object,Object>();
						while (sValueIter1.hasNext()) {
							SourceValueDT svDT = (SourceValueDT) sValueIter1
									.next();
							map.put(svDT.getSourceValue(), svDT.getSourceValue());
						}
						while (srcVIter.hasNext()) {
							boolean metTheCondition = false;
							String errorMessage = null;
							SourceValueDT sourceValueDT = (SourceValueDT) srcVIter
									.next();
							if(sourceValueDT != null && sourceValueDT.getSourceValue() != null){
								// this is for the zip
								if (sourceValueDT.getOperatorTypeCode() != null
										&& sourceValueDT.getOperatorTypeCode()
										.equals("<")) {
									if (((String) srcformField.getValue()).length() < Integer
											.parseInt(sourceValueDT
													.getSourceValue())) {
										// Call the error here.
										errorMessage = this
												.getErrorMessage(rulesDT);
										metTheCondition = true;
									}

								} else if (sourceValueDT.getOperatorTypeCode() != null
										&& sourceValueDT.getOperatorTypeCode()
										.equals(">")) {
									if (((String) srcformField.getValue()).length() > Integer
											.parseInt(sourceValueDT
													.getSourceValue())) {
										// Call the error here.
										errorMessage = this
												.getErrorMessage(rulesDT);
										metTheCondition = true;
									}
								} else if (sourceValueDT.getOperatorTypeCode() != null
										|| sourceValueDT.getOperatorTypeCode()
										.equals("=")) {



									String length = String.valueOf(((String) srcformField.getValue()).length());
									if(map != null && !map.containsKey(length)){
										errorMessage = this
												.getErrorMessage(rulesDT);
										metTheCondition = true;

									}
								}
								if(errorMessage != null){
									errorMessage = errorMessage
											.replaceAll("\\(Field Name\\)",
													srcformField.getLabel());
									srcformField.getErrorMessage().add(errorMessage);
									// change the color of the source field also
									srcformField
									.setErrorStyleClass(RuleConstants.REQUIRED_FIELD_CLASS);
									errColl.add(srcformField);
								}

							}
							if(metTheCondition)
								break;
						}
					}
				}
			}
		}
	}catch(Exception ex) {
		logger.error("Exception in PageRulesGenerator.validLength: " + ex.getMessage());
		ex.printStackTrace();
	}
		return errColl;
	}

	public Collection<Object>  validValue(Collection<Object> errColl, RulesDT rulesDT,
			Map<Object,Object> formFieldMap) {
		try {
			FormField srcformField = (FormField) formFieldMap.get(rulesDT
					.getQuestionIdentifer());
			if (srcformField.getValue() != null
					&& !(srcformField.getValue().equals(""))) {
				String srcValue = (String)srcformField.getValue();
				Collection<Object>  srcColl = rulesDT.getSourceColl();
				if (srcColl != null) {
					Iterator<Object> srcIter = srcColl.iterator();
					while (srcIter.hasNext()) {
						SourceFieldDT sourceFieldDT = (SourceFieldDT) srcIter
								.next();
						Collection<Object>  srcVColl = sourceFieldDT.getSourceValueColl();
						if (srcVColl != null) {
							Iterator<Object> srcVIter = srcVColl.iterator();
							while (srcVIter.hasNext()) {
								String errorMessage = null;

								SourceValueDT sourceValueDT = (SourceValueDT) srcVIter
										.next();

								// minimum value check
								if (sourceValueDT.getOperatorTypeCode() != null
										&& sourceValueDT.getOperatorTypeCode()
										.equals("<")) {
									if (Integer.parseInt(srcValue) < Integer
											.parseInt(sourceValueDT
													.getSourceValue())) {
										// Call the error here.
										errorMessage = this
												.getErrorMessage(rulesDT);
									}

								} else if (sourceValueDT.getOperatorTypeCode() != null
										&& sourceValueDT.getOperatorTypeCode()
										.equals(">")) {
									int mtdtSourcVal;
									if(sourceValueDT.getSourceValue().equals(NEXT_YEAR)){
										//SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
										Date currDate = new Date();
										int currYear=0;
										if (currDate != null) {
											Calendar cal = Calendar.getInstance();
											cal.setTime(currDate);
											currYear = cal.get(Calendar.YEAR);
										}

										mtdtSourcVal = currYear+1;
									}
									else{
										mtdtSourcVal=Integer.parseInt(sourceValueDT.getSourceValue());
									}
									if (Integer.parseInt(srcValue) > mtdtSourcVal){
										// Call the error here.
										errorMessage = this
												.getErrorMessage(rulesDT);
									}
								}else if (sourceValueDT.getOperatorTypeCode() != null
										&& sourceValueDT.getOperatorTypeCode()
										.equals(">=")) {
									int mtdtSourcVal;

									mtdtSourcVal=Integer.parseInt(sourceValueDT.getSourceValue());

									if (Integer.parseInt(srcValue) >= mtdtSourcVal){
										// Call the error here.
										errorMessage = this
												.getErrorMessage(rulesDT);
									}
								}
								if(errorMessage != null){
									errorMessage = errorMessage
											.replaceAll("\\(Field Name\\)",
													srcformField.getLabel());
									srcformField.getErrorMessage().add(errorMessage);
									// change the color of the source field also
									srcformField
									.setErrorStyleClass(RuleConstants.REQUIRED_FIELD_CLASS);
									errColl.add(srcformField);
								}

							}
						}
					}
				}
			}
		}catch(Exception ex) {
			logger.error("Exception in PageRulesGenerator.validValue: " + ex.getMessage());
			ex.printStackTrace();
		}

		return errColl;
	}

	public Collection<Object>  dateCompareLimit(Collection<Object> errColl, RulesDT rulesDT, Map<Object,Object> formFieldMap) {
		try {
			Collection<Object>  srcColl = rulesDT.getSourceColl();
			FormField srcformField = (FormField)formFieldMap.get(rulesDT.getQuestionIdentifer());
			// check the date format of the SourceFormField
			this.isDateFormated(errColl, srcformField);
			if(srcformField.getValue() != null && !(srcformField.getValue().equals(""))){
				if(srcColl!= null){
					Iterator<Object> srcIter = srcColl.iterator();
					while( srcIter.hasNext()){
						SourceFieldDT sourceFieldDT = (SourceFieldDT)srcIter.next();
						Collection<Object>  srcValueColl = sourceFieldDT.getSourceValueColl();
						if(srcValueColl != null){
							Iterator<Object> srcVIter = srcValueColl.iterator();
							while(srcVIter.hasNext()){
								SourceValueDT sourceValueDT = (SourceValueDT)srcVIter.next();
								String errorMessage = null;
								TargetValueDT targetValueDT = new TargetValueDT();

								//if(sourceValueDT.getOperatorTypeCode().equals(NEDSSConstants.LESS_THAN)){
								// get the target date to be compared
								TargetFieldDT targetFieldDT = this.getTarget(rulesDT);
								//get the target Value for ErrorMessage
								Collection<Object>  tValueColl =  targetFieldDT.getTargetValueColl();
								if(tValueColl != null){
									Iterator<Object> tValueIter = tValueColl.iterator();
									while(tValueIter.hasNext()){
										targetValueDT = (TargetValueDT)tValueIter.next();
									}
								}
								FormField tgtformField = (FormField)formFieldMap.get(targetFieldDT.getQuestionIdentifier());
								//check the dateFormat of the TgtformField
								if(isDateFormated(errColl, tgtformField)){
									if((tgtformField != null && (tgtformField.getValue() != null && !(tgtformField.getValue().equals("")))) && (srcformField != null && srcformField.getValue()!= null)){
										long diff = datedifference((String)tgtformField.getValue(),(String)srcformField.getValue());
										if (diff<Long.parseLong(sourceValueDT.getSourceValue())){
											errorMessage = (targetValueDT.getErrorDescTxt().replaceAll(
													"\\(Field Name1\\)",
													targetFieldDT.getQuestionLabel())).replaceAll(
															"\\(Field Name2\\)",
															sourceFieldDT.getQuestionLabel());
											srcformField.getErrorMessage().add(errorMessage);
											tgtformField.setErrorStyleClass(RuleConstants.REQUIRED_FIELD_CLASS);
											srcformField.setErrorStyleClass(RuleConstants.REQUIRED_FIELD_CLASS);
											errColl.add(tgtformField);
											errColl.add(srcformField);
										}
									}
								}

								//}

							}
						}
					}
				}
			}
		}catch(Exception ex) {
			logger.error("Exception in PageRulesGenerator.dateCompareLimit: " + ex.getMessage());
			ex.printStackTrace();
		}
		return errColl;
	}

	private String getErrorMessage(RulesDT rulesDT){

		TargetValueDT targetValueDT = new TargetValueDT();
		try {
			// get the target
			TargetFieldDT targetFieldDT = this
					.getTarget(rulesDT);
			// get the target Value for ErrorMessage
			Collection<Object>  tValueColl = targetFieldDT
					.getTargetValueColl();
			if (tValueColl != null) {
				Iterator<Object> tValueIter = tValueColl.iterator();
				while (tValueIter.hasNext()) {
					targetValueDT = (TargetValueDT) tValueIter
							.next();

				}
			}
		}catch(Exception ex) {
			logger.error("Exception in PageRulesGenerator.dateCompare: " + ex.getMessage());
			ex.printStackTrace();
		}		
		return targetValueDT.getErrorDescTxt();
	}
	
/**
 * This method will throw an error message to the user, if the State case number 
 * or City/County Case Number is not unique across RVCTS/investigations
 * @param errColl
 * @param rulesDT
 * @param formFieldMap
 * @param formCode
 * @return
 */
 public Collection<Object>  uniqueCaseNumber(Collection<Object> errColl, RulesDT rulesDT, Map<Object,Object> formFieldMap,String formCode, String actionMode, String caseLocalId){
	 FormField srcformField = (FormField) formFieldMap.get(rulesDT
				.getQuestionIdentifer());
	 String caseNumber = (String)srcformField.getValue();
	 RulesEngineUtilHelper utilHelper = new RulesEngineUtilHelper();
	 int seqNum=0;
	
	 if(rulesDT.getQuestionIdentifer().equals(STATE_CASE_NUMBER))
		 seqNum = 1;
	 else if(rulesDT.getQuestionIdentifer().equals(CITY_CASE_NUMBER))
		 seqNum = 2;
	 try{
	 	 caseNumber = utilHelper.getPamProxyEJBRef().getRootExtensionTxt(formCode, seqNum, caseNumber, caseLocalId);
	 }catch(Exception e){
		 logger.error("Exception in uniqueCaseNumber during the EJB call: " + e.getMessage());
		 e.printStackTrace();
	 }
	 
		if (((caseNumber != null && !caseNumber.equals(""))
				&& srcformField.getValue() != null && !srcformField.getValue()
				.equals(""))) {
			if (srcformField.getValue().equals(caseNumber)) {
				TargetFieldDT targetFieldDT = this.getTarget(rulesDT);
				TargetValueDT targetValueDT = new TargetValueDT();
				Collection<Object>  tValueColl = targetFieldDT.getTargetValueColl();
				if (tValueColl != null) {
					Iterator<Object> tValueIter = tValueColl.iterator();
					while (tValueIter.hasNext()) {
						targetValueDT = (TargetValueDT) tValueIter.next();
					}
				}
				String errorMessage = targetValueDT.getErrorDescTxt()
						.replaceAll("\\(Field Name\\)",
								targetFieldDT.getQuestionLabel());
				srcformField.getErrorMessage().add(errorMessage);
				srcformField
						.setErrorStyleClass(RuleConstants.REQUIRED_FIELD_CLASS);

			}
		}
		errColl.add(srcformField);
		return errColl;
	}


	/**
	 * check if the sourceInd has multiple rule instances with same rule_name
	 * then discards other instances based on selected value
	 *
	 * @param ruleColl
	 * @param formFieldMap
	 * @param sourceInd
	 * @return
	 */
	private Collection<Object>  handleTUB105106Rule(Collection<Object> fieldColl,
			Collection<Object>  ruleColl, Map<Object,Object> formFieldMap, String sourceInd, Map<Object,Object> answerArrayMap) {

		String tub_105 = (String) ((FormField) formFieldMap.get(TUB105))
				.getValue();
		String tub_109 = (String) ((FormField) formFieldMap.get(TUB109))
		.getValue();
		int count=0;

		String[] tub_106 = (String[]) answerArrayMap.get(TUB106);

		if(tub_106!=null && tub_106.length>0){
			for (int i = 0; i < tub_106.length; i++) {

				if (!(tub_105.equalsIgnoreCase(RuleConstants.MAJOR_SITE_PULMONARY) || tub_106[i].equalsIgnoreCase(RuleConstants.ADDITIONAL_SITE_PULMONARY) || tub_105.equalsIgnoreCase(RuleConstants.MAJOR_SITE_MILIARY) || tub_106[i].equalsIgnoreCase(RuleConstants.ADDITIONAL_SITE_MILIARY) || tub_105.equalsIgnoreCase(RuleConstants.MAJOR_SITE_PLEURAL) || tub_106[i].equalsIgnoreCase(RuleConstants.ADDITIONAL_SITE_PLEURAL) || tub_105.equalsIgnoreCase(RuleConstants.MAJOR_SITE_LYMPH) || tub_106[i].equalsIgnoreCase(RuleConstants.ADDITIONAL_SITE_LYMPH))) {
					if (tub_109.equalsIgnoreCase(RuleConstants.SPUTUM_CULTURE_POSITIVE) && count == 0) {
						try {
							FormField formField =  (FormField) formFieldMap.get(TUB109);
							if (formField.getErrorMessage() != null	&& formField.getErrorMessage().size() == 0)
							formField.getErrorMessage().add(formField.getLabel()+ " cannot equal Postive unless Pleural, Lymphatic: intrathoracic, Miliary, or Pulmonary has been selected for 15. Major Sie of Disease or for 16. Additional Site of Disease.");
							formField.setErrorStyleClass(RuleConstants.REQUIRED_FIELD_CLASS);
							fieldColl.add(formField);
							FormField formField105 =  (FormField) formFieldMap.get(TUB105);
							FormField formField106 =  (FormField) formFieldMap.get(TUB106);
							formField105.setErrorStyleClass(RuleConstants.REQUIRED_FIELD_CLASS);
							formField106.setErrorStyleClass(RuleConstants.REQUIRED_FIELD_CLASS);

							} catch (Exception e) {
								logger.error("Exception in handleTUB105106Rule: " + e.getMessage());
								e.printStackTrace();
								return fieldColl;
						    }
						}

				      }else {
				    	  count=1;
				      }
			}
		}
		else if((tub_106==null || tub_106.toString().equals("")) && (!(tub_105==null || tub_105.equals("")))){
			try {
				if(!(tub_105.equalsIgnoreCase(RuleConstants.MAJOR_SITE_PULMONARY)  || tub_105.equalsIgnoreCase(RuleConstants.MAJOR_SITE_MILIARY)  || tub_105.equalsIgnoreCase(RuleConstants.MAJOR_SITE_PLEURAL)  || tub_105.equalsIgnoreCase(RuleConstants.MAJOR_SITE_LYMPH) )){
				if (tub_109.equalsIgnoreCase(RuleConstants.SPUTUM_CULTURE_POSITIVE)) {
				FormField formField =  (FormField) formFieldMap.get(TUB109);
				if (formField.getErrorMessage() != null	&& formField.getErrorMessage().size() == 0)
				formField.getErrorMessage().add(formField.getLabel()+ " cannot equal Postive unless Pleural, Lymphatic: intrathoracic, Miliary, or Pulmonary has been selected for 15. Major Sie of Disease or for 16. Additional Site of Disease.");
				formField.setErrorStyleClass(RuleConstants.REQUIRED_FIELD_CLASS);
				fieldColl.add(formField);
				FormField formField105 =  (FormField) formFieldMap.get(TUB105);
				FormField formField106 =  (FormField) formFieldMap.get(TUB106);
				formField105.setErrorStyleClass(RuleConstants.REQUIRED_FIELD_CLASS);
				formField106.setErrorStyleClass(RuleConstants.REQUIRED_FIELD_CLASS);
				}}
				} catch (Exception e) {
					logger.error("Exception at handleTUB105106Rule: " + e.getMessage());
					e.printStackTrace();
					return fieldColl;
			    }

		}

		else if((tub_106==null || tub_106.equals("")) && (tub_105==null || tub_105.equals(""))){
			try {
				if (tub_109.equalsIgnoreCase(RuleConstants.SPUTUM_CULTURE_POSITIVE)) {
				FormField formField =  (FormField) formFieldMap.get(TUB109);
				if (formField.getErrorMessage() != null	&& formField.getErrorMessage().size() == 0)
				formField.getErrorMessage().add(formField.getLabel()+ " cannot equal Postive unless Pleural, Lymphatic: intrathoracic, Miliary, or Pulmonary has been selected for 15. Major Sie of Disease or for 16. Additional Site of Disease.");
				formField.setErrorStyleClass(RuleConstants.REQUIRED_FIELD_CLASS);
				fieldColl.add(formField);
				FormField formField105 =  (FormField) formFieldMap.get(TUB105);
				FormField formField106 =  (FormField) formFieldMap.get(TUB106);
				formField105.setErrorStyleClass(RuleConstants.REQUIRED_FIELD_CLASS);
				formField106.setErrorStyleClass(RuleConstants.REQUIRED_FIELD_CLASS);
				}
				} catch (Exception e) {
					logger.error("Exception occurred in handleTUB105106Rule: " + e.getMessage());
					e.printStackTrace();
					return fieldColl;
			    }

		}
		count=0;

		return fieldColl;
	}
		
	public Collection<Object>  validValueOnTarget(Collection<Object> errColl, RulesDT rulesDT,
			Map<Object,Object> formFieldMap) {
		try {
			FormField srcformField = (FormField) formFieldMap.get(rulesDT
					.getQuestionIdentifer());
			String errorMessage = null;
			Collection<Object>  srcColl = rulesDT.getSourceColl();
			if (srcColl != null) {
				Iterator<Object> srcIter = srcColl.iterator();
				while (srcIter.hasNext()) {
					SourceFieldDT sourceFieldDT = (SourceFieldDT) srcIter.next();
					Collection<Object>  srcVColl = sourceFieldDT.getSourceValueColl();
					if (srcVColl != null) {
						Iterator<Object> srcVIter = srcVColl.iterator();
						Iterator<Object> srcVIter1 = srcVColl.iterator();
						Iterator<Object> sValueIter1 = srcVColl.iterator();
						HashMap<Object,Object> map = new HashMap<Object,Object>();
						while (sValueIter1.hasNext()) {
							SourceValueDT svDT = (SourceValueDT) sValueIter1.next();
							map.put(svDT.getSourceValue(), svDT.getSourceValue());
						}

						while (srcVIter.hasNext()) {
							SourceValueDT sourceValueDT = (SourceValueDT) srcVIter
									.next();

							// source value check - if the source value is what the
							// rule expected
							if (sourceValueDT.getOperatorTypeCode() != null
									&& sourceValueDT.getOperatorTypeCode().equals(
											EQUAL)) {
								if (srcformField.getValue() != null) {
									// if(sourceValueDT.getSourceValue() != null &&
									// sourceValueDT.getSourceValue().equals(srcformField.getValue())){
									if (map != null
											&& map.containsKey(srcformField
													.getValue())) {
										// Call the target method with with the
										// valid values(in range)

										errorMessage = getErrorTargetvalueNotInRange(
												rulesDT, formFieldMap);
									}
								}
							}

						}

						while (srcVIter1.hasNext()) {

							SourceValueDT sourceValueDT = (SourceValueDT) srcVIter1
									.next();

							if (sourceValueDT.getOperatorTypeCode() != null
									&& sourceValueDT.getOperatorTypeCode().equals(
											NOTEQUAL)) {
								// When it is not equal, check for null also

								if (((srcformField.getValue() != null)
										&& (map != null && !map
										.containsKey(srcformField
												.getValue()))) || srcformField.getValue() == null) {

									errorMessage = getErrorTargetvalueNotInRange(
											rulesDT, formFieldMap);
									// Call the target method with with the
									// valid values(in range)

								}

							}
						}
					}

					if (errorMessage != null) {
						TargetFieldDT targetFieldDT = this.getTarget(rulesDT);
						FormField tgtFormField = (FormField) formFieldMap
								.get(targetFieldDT.getQuestionIdentifier());
						errorMessage = errorMessage.replaceAll(
								"\\(Field Name1\\)", tgtFormField.getLabel())
								.replaceAll("\\(Field Name2\\)",
										srcformField.getLabel());
						if (tgtFormField.getErrorMessage().size() == 0)
							tgtFormField.getErrorMessage().add(errorMessage);
						// change the color of the source field also
						srcformField
						.setErrorStyleClass(RuleConstants.REQUIRED_FIELD_CLASS);
						tgtFormField
						.setErrorStyleClass(RuleConstants.REQUIRED_FIELD_CLASS);
						errColl.add(srcformField);
						errColl.add(tgtFormField);
					}


				}
			}
		} catch (Exception e) {
			logger.error("Exception occurred in validValueOnTarget: " + e.getMessage());
			e.printStackTrace();

		}
		return errColl;
	}
	
	private String getErrorTargetvalueNotInRange(RulesDT rulesDT,
			Map<Object,Object> formFieldMap) {
        String errorMessage=null;
        try {
        	TargetValueDT targetValueDT = new TargetValueDT();
        	// get the target
        	TargetFieldDT targetFieldDT = this.getTarget(rulesDT);
        	FormField tgtFormField = (FormField) formFieldMap.get(targetFieldDT
        			.getQuestionIdentifier());

        	// get the target Value for ErrorMessage
        	Collection<Object>  tValueColl = targetFieldDT.getTargetValueColl();
        	if (tValueColl != null) {
        		Iterator<Object> tValueIter = tValueColl.iterator();
        		boolean metCondition = false;
        		while (tValueIter.hasNext()) {
        			targetValueDT = (TargetValueDT) tValueIter.next();
        			if (targetValueDT.getOperatorTypeCode() != null) {
        				if (targetValueDT.getOperatorTypeCode().equals(
        						LESS_THAN_EQUAL)) {
        					if (tgtFormField.getValue() != null && !tgtFormField.getValue().equals("")
        							&& targetValueDT.getTargetValue() != null) {
        						if (Integer.parseInt((String) tgtFormField
        								.getValue()) <= Integer
        								.parseInt(targetValueDT.getTargetValue())) {
        							metCondition = true;
        							//Error
        							errorMessage = targetValueDT.getErrorDescTxt();

        						}

        					}

        				}
        				else if(targetValueDT.getOperatorTypeCode().equals(GREATER_THAN)){
        					if (tgtFormField.getValue() != null && !tgtFormField.getValue().equals("")
        							&& targetValueDT.getTargetValue() != null) {	
        						/*if (Integer.parseInt((String) tgtFormField
							.getValue()) > Integer
							.parseInt(targetValueDT.getTargetValue())) {*/
        						if ((Float.valueOf((String) tgtFormField.getValue())).floatValue() >
        						(Float.valueOf(targetValueDT.getTargetValue())).floatValue()) {
        							metCondition = true;
        							// Error
        							errorMessage = targetValueDT.getErrorDescTxt();

        						}

        					}

        				}else if(targetValueDT.getOperatorTypeCode().equals(GREATER_THAN_EQUAL)){
        					if (tgtFormField.getValue() != null && !tgtFormField.getValue().equals("")
        							&& targetValueDT.getTargetValue() != null) {
        						if (Integer.parseInt((String) tgtFormField
        								.getValue()) >= Integer
        								.parseInt(targetValueDT.getTargetValue())) {
        							// Error
        							metCondition = true;
        							errorMessage = targetValueDT.getErrorDescTxt();

        						}

        					}

        				}else if (targetValueDT.getOperatorTypeCode().equals(
        						LESS_THAN)) {
        					if (tgtFormField.getValue() != null && !tgtFormField.getValue().equals("")
        							&& targetValueDT.getTargetValue() != null) {
        						if ((Float.valueOf((String) tgtFormField.getValue())).floatValue() <
        								(Float.valueOf(targetValueDT.getTargetValue())).floatValue()) {
        							/*if (Integer.parseInt((String) tgtFormField
									.getValue()) < Integer
									.parseInt(targetValueDT.getTargetValue())) {*/
        							metCondition = true;
        							//Error
        							errorMessage = targetValueDT.getErrorDescTxt();

        						}

        					}

        				}else if(targetValueDT.getOperatorTypeCode().equals(NOTEQUAL)){
        					if (tgtFormField.getValue() != null && !tgtFormField.getValue().equals("")
        							&& targetValueDT.getTargetValue() != null) {
        						if (Integer.parseInt((String) tgtFormField
        								.getValue()) != Integer
        								.parseInt(targetValueDT.getTargetValue())) {
        							metCondition = true;
        							// Error
        							errorMessage = targetValueDT.getErrorDescTxt();

        						}

        					}

        				} else if (targetValueDT.getOperatorTypeCode().equals(NONE)) {
        					if ((tgtFormField.getValue() != null && !tgtFormField.getValue().equals(""))
        							|| (tgtFormField.getValue() == null )) {
        						metCondition = true;
        						// Error
        						errorMessage = targetValueDT.getErrorDescTxt();

        					}

        				}
        			}
        			if(metCondition)
        				break;

        		}
        	}
        } catch (Exception e) {
        	logger.error("Exception occurred in getErrorTargetvalueNotInRange: " + e.getMessage());
        	e.printStackTrace();
        }
		return errorMessage;
	}
	/**
	 * This method validate the target value based of different sources 
	 * if the value is X for source1 or Source2, the target can have a value 
	 * or the target can have the value as Z
	 * @param errColl
	 * @param rulesDT
	 * @param formFieldMap
	 * @return
	 */
	public Collection<Object>  diffSourceValidValueTarget(Collection<Object> errColl,
			RulesDT rulesDT, Map<Object,Object> formFieldMap) {
		FormField srcformField = new FormField();
		String errorMessage = null;
		Collection<Object>  srcColl = rulesDT.getSourceColl();
		if (srcColl != null) {
			try {
				Iterator<Object> srcIter = srcColl.iterator();
				boolean metCondition = false;
				while (srcIter.hasNext()) {
					SourceFieldDT sourceFieldDT = (SourceFieldDT) srcIter.next();
					srcformField = (FormField) formFieldMap.get(sourceFieldDT
							.getQuestionIdentifier());

					Collection<Object>  srcVColl = sourceFieldDT.getSourceValueColl();
					if (srcVColl != null) {
						Iterator<Object> srcVIter = srcVColl.iterator();
						Iterator<Object> srcVIter1 = srcVColl.iterator();
						Iterator<Object> sValueIter1 = srcVColl.iterator();
						HashMap<Object,Object> map = new HashMap<Object,Object>();
						while (sValueIter1.hasNext()) {
							SourceValueDT svDT = (SourceValueDT) sValueIter1.next();
							map.put(svDT.getSourceValue(), svDT.getSourceValue());
						}

						while (srcVIter.hasNext()) {
							SourceValueDT sourceValueDT = (SourceValueDT) srcVIter
									.next();

							// source value check - if the source value is what the
							// rule expected
							if (sourceValueDT.getOperatorTypeCode() != null
									&& sourceValueDT.getOperatorTypeCode().equals(
											EQUAL)) {
								if (srcformField.getValue() != null) {
									// if(sourceValueDT.getSourceValue() != null &&
									// sourceValueDT.getSourceValue().equals(srcformField.getValue())){
									if (map != null
											&& map.containsKey(srcformField
													.getValue())) {
										// Call the target method with with the
										// valid values(in range)

										metCondition = true;

									}
								}
							}

						}

					}
					if (metCondition)
						return errColl;
				}

				errorMessage = this.getValidValueOnSourceTarget(rulesDT,
						formFieldMap);

				if (errorMessage != null) {
					TargetFieldDT targetFieldDT = this.getTarget(rulesDT);
					FormField tgtFormField = (FormField) formFieldMap
							.get(targetFieldDT.getQuestionIdentifier());
					errorMessage = errorMessage.replaceAll("\\(Field Name1\\)",
							tgtFormField.getLabel()).replaceAll(
									"\\(Field Name2\\)", srcformField.getLabel());
					if (tgtFormField.getErrorMessage().size() == 0)
						tgtFormField.getErrorMessage().add(errorMessage);
					// change the color of the source field also
					srcformField
					.setErrorStyleClass(RuleConstants.REQUIRED_FIELD_CLASS);
					tgtFormField
					.setErrorStyleClass(RuleConstants.REQUIRED_FIELD_CLASS);
					errColl.add(srcformField);
					errColl.add(tgtFormField);
					//to change all the sourcefield's color to red 
					changeLabelColor(srcColl,formFieldMap);
				}
			} catch (Exception e) {
				logger.error("Exception occurred in diffSourceValidValueTarget: " + e.getMessage());
				e.printStackTrace();
			}							
		} //srcColl not null
		return errColl;

	}
	
	/**
	 * This method is for the different source filter which we made it on Submit.
	 * if the value is X for source1 or Source2, the target can not have Y value  
	 * @param errColl
	 * @param rulesDT
	 * @param formFieldMap
	 * @return
	 */
	public Collection<Object>  diffSourceFilterSubmit(Collection<Object> errColl,
			RulesDT rulesDT, Map<Object,Object> formFieldMap) {
		FormField srcformField = new FormField();
		String errorMessage = null;
		Collection<Object>  srcColl = rulesDT.getSourceColl();
		if (srcColl != null) {
			try {
				Iterator<Object> srcIter = srcColl.iterator();
				boolean metCondition = false;
				while (srcIter.hasNext()) {
					SourceFieldDT sourceFieldDT = (SourceFieldDT) srcIter.next();
					srcformField = (FormField) formFieldMap.get(sourceFieldDT
							.getQuestionIdentifier());
					Collection<Object>  srcVColl = sourceFieldDT.getSourceValueColl();
					if (srcVColl != null) {
						Iterator<Object> srcVIter = srcVColl.iterator();
						Iterator<Object> sValueIter1 = srcVColl.iterator();
						HashMap<Object,Object> map = new HashMap<Object,Object>();
						while (sValueIter1.hasNext()) {
							SourceValueDT svDT = (SourceValueDT) sValueIter1.next();
							map.put(svDT.getSourceValue(), svDT.getSourceValue());
						}

						while (srcVIter.hasNext()) {
							SourceValueDT sourceValueDT = (SourceValueDT) srcVIter
									.next();

							// source value check - if the source value is what the
							// rule expected
							if (sourceValueDT.getOperatorTypeCode() != null
									&& sourceValueDT.getOperatorTypeCode().equals(
											EQUAL)) {
								if (srcformField.getValue() != null) {
									// if(sourceValueDT.getSourceValue() != null &&
									// sourceValueDT.getSourceValue().equals(srcformField.getValue())){
									if (map != null
											&& map.containsKey(srcformField
													.getValue())) {
										// Call the target method with with the
										// valid values(in range)

										metCondition = true;
									}
								}
							}
						}
					}

					if (metCondition) {
						errorMessage = this.getValidValueOnSourceTarget(rulesDT,
								formFieldMap);

						if (errorMessage != null) {
							//If only one target field  
							TargetFieldDT targetFieldDT = this.getTarget(rulesDT);
							FormField tgtFormField = (FormField) formFieldMap
									.get(targetFieldDT.getQuestionIdentifier());
							errorMessage = errorMessage.replaceAll(
									"\\(Field Name1\\)", tgtFormField.getLabel())
									.replaceAll("\\(Field Name2\\)",
											srcformField.getLabel());
							if (tgtFormField.getErrorMessage().size() == 0)
								tgtFormField.getErrorMessage().add(errorMessage);
							// change the color of the source field also
							srcformField
							.setErrorStyleClass(RuleConstants.REQUIRED_FIELD_CLASS);
							tgtFormField
							.setErrorStyleClass(RuleConstants.REQUIRED_FIELD_CLASS);
							errColl.add(srcformField);
							errColl.add(tgtFormField);
							//to change all the sourcefield's color to red 
							changeLabelColor(srcColl,formFieldMap);
						}
					}

				}

			} catch (Exception e) {
				logger.error("Exception occurred in diffSourceFilterSubmit: " + e.getMessage());
				e.printStackTrace();
			}	
		}
		return errColl;

	}
		
	private void changeLabelColor(Collection<Object> srcColl, Map<Object,Object> formFieldMap){
		if(srcColl != null){
		Iterator<Object> srcIter1 = srcColl.iterator();
		while(srcIter1.hasNext()){
			SourceFieldDT sourceFieldDT = (SourceFieldDT) srcIter1.next();
			FormField srcformField = (FormField) formFieldMap.get(sourceFieldDT
					.getQuestionIdentifier());
			 
			if(srcformField != null)
				srcformField.setErrorStyleClass(RuleConstants.REQUIRED_FIELD_CLASS);
		}
		}
	}
		
	
	 
	private String getValidValueOnSourceTarget(RulesDT rulesDT, Map<Object,Object> formFieldMap) {
		String errorMessage = null;
		TargetFieldDT targetFieldDT = new TargetFieldDT();
		TargetValueDT targetValueDT = new TargetValueDT();
		// get the target
		Collection<Object>  targetColl = rulesDT.getTargetColl();
		if (targetColl != null) {
			try {
				Iterator<Object> targetIter = targetColl.iterator();
				while (targetIter.hasNext()) {
					targetFieldDT = (TargetFieldDT) targetIter.next();
					FormField tgtFormField = (FormField) formFieldMap
							.get(targetFieldDT.getQuestionIdentifier());
					if (tgtFormField != null && tgtFormField.getValue() != null
							&& !tgtFormField.getValue().equals("")) {
						// get the target Value for ErrorMessage
						Collection<Object>  tValueColl = targetFieldDT.getTargetValueColl();
						if (tValueColl != null) {
							Iterator<Object> tValueIter = tValueColl.iterator();
							boolean metCondition = false;
							while (tValueIter.hasNext()) {
								targetValueDT = (TargetValueDT) tValueIter.next();
								if (targetValueDT.getOperatorTypeCode() != null) {

									if (targetValueDT.getOperatorTypeCode().equals(
											EQUAL)) {
										if (tgtFormField.getValue() != null
												&& !tgtFormField.getValue().equals(
														"")
														&& targetValueDT.getTargetValue() != null) {
											if ((((String) tgtFormField
													.getFieldType()).equals(TEXT))
													|| ((String) tgtFormField
															.getFieldType())
															.equals(DATATYPE_CODE)) {
												if (((String) tgtFormField
														.getValue())
														.equals((String) targetValueDT
																.getTargetValue())) {
													metCondition = true;
													// Error
													errorMessage = targetValueDT
															.getErrorDescTxt();
												}
											} else if (((String) tgtFormField
													.getFieldType())
													.equals(NUMERIC)) {
												if (Integer
														.parseInt((String) tgtFormField
																.getValue()) == Integer
																.parseInt((String) targetValueDT
																		.getTargetValue())) {
													metCondition = true;
													// Error
													errorMessage = targetValueDT
															.getErrorDescTxt();

												}
											}
										}

									} else if (targetValueDT.getOperatorTypeCode()
											.equals(NONE)) {
										if ((tgtFormField.getValue() != null && !tgtFormField
												.getValue().equals(""))) {
											metCondition = true;
											// Error
											errorMessage = targetValueDT
													.getErrorDescTxt();

										}

									}
								}
								if (metCondition)
									break;

							}
						}
					}
				}
			} catch (Exception e) {
				logger.error("Exception occurred in getValidValueOnSourceTarget: " + e.getMessage());
				e.printStackTrace();
			}	
		}
		return errorMessage;
	}
	
	/**
	 *
	 * @param errColl
	 * @param rulesDT
	 * @param formCode
	 * @param formFieldMap
	 * @return
	 * @throws java.text.ParseException
	 */
	public Collection<Object>  dateRangeLimitOnTarget(RulesDT rulesDT,
			Map<Object,Object> formFieldMap, Collection<Object>  errColl) {


		FormField srcformField = (FormField) formFieldMap.get(rulesDT
				.getQuestionIdentifer());
		String errorMessage = null;
		if (srcformField.getValue() != null
				&& !srcformField.getValue().equals("")) {
			try {
				Collection<Object>  sourceColl = rulesDT.getSourceColl();
				if (sourceColl != null && sourceColl.size() > 0) {
					Iterator<Object> sourceIter = sourceColl.iterator();

					while (sourceIter.hasNext()) {
						SourceFieldDT sourceFieldDT = (SourceFieldDT) sourceIter
								.next();
						Collection<Object>  srcVColl = sourceFieldDT.getSourceValueColl();
						if (srcVColl != null) {
							Iterator<Object> srcVIter = srcVColl.iterator();

							Iterator<Object> sValueIter1 = srcVColl.iterator();
							HashMap<Object,Object> map = new HashMap<Object,Object>();
							while (sValueIter1.hasNext()) {
								SourceValueDT svDT = (SourceValueDT) sValueIter1
										.next();
								map.put(svDT.getSourceValue(), svDT
										.getSourceValue());
							}

							while (srcVIter.hasNext()) {
								SourceValueDT sourceValueDT = (SourceValueDT) srcVIter
										.next();

								// source value check - if the source value is what
								// the
								// rule expected
								if (sourceValueDT.getOperatorTypeCode() != null
										&& sourceValueDT.getOperatorTypeCode()
										.equals(EQUAL)) {
									if (srcformField.getValue() != null) {
										// if(sourceValueDT.getSourceValue() != null
										// &&
										// sourceValueDT.getSourceValue().equals(srcformField.getValue())){
										if (map != null
												&& map.containsKey(srcformField
														.getValue())) {
											// Call the target method with with the
											// valid values(in range)
											errorMessage = getTargetDateRange(errColl,
													rulesDT, formFieldMap);

										}
									}
								}

							}
							if (errorMessage != null) {
								TargetFieldDT targetFieldDT = this
										.getTarget(rulesDT);
								FormField tgtFormField = (FormField) formFieldMap
										.get(targetFieldDT.getQuestionIdentifier());
								errorMessage = errorMessage.replaceAll(
										"\\(Field Name\\)",
										tgtFormField.getLabel());
								if (tgtFormField.getErrorMessage().size() == 0)
									tgtFormField.getErrorMessage()
									.add(errorMessage);
								// change the color of the source field also
								srcformField
								.setErrorStyleClass(RuleConstants.REQUIRED_FIELD_CLASS);
								tgtFormField
								.setErrorStyleClass(RuleConstants.REQUIRED_FIELD_CLASS);
								errColl.add(srcformField);
								errColl.add(tgtFormField);
							}

							/*
							 * source value collection null indicates that if the
							 * source field has a value, it should enable the target
							 * fields
							 */
						}
					}
				}
			} catch (Exception e) {
				logger.error("Exception occurred in dateRangeLimitOnTarget: " + e.getMessage());
				e.printStackTrace();
			}	
		}

		return errColl;
	}
	
	public String getTargetDateRange(Collection<Object> errColl,RulesDT rulesDT, Map<Object,Object> formFieldMap) {

		String errorMessage = null;
		TargetValueDT targetValueDT = new TargetValueDT();
		// get the target
		TargetFieldDT targetFieldDT = this.getTarget(rulesDT);
		FormField tgtFormField = (FormField) formFieldMap.get(targetFieldDT
				.getQuestionIdentifier());
		if(tgtFormField.getValue() != null && !(tgtFormField.getValue().equals(""))){
			try {
				// get the target Value for ErrorMessage
				Collection<Object>  tValueColl = targetFieldDT.getTargetValueColl();
				if (tValueColl != null) {

					Iterator<Object> tValueIter = tValueColl.iterator();


					while (tValueIter.hasNext()) {
						targetValueDT = (TargetValueDT) tValueIter.next();
						// FormField formField = (FormField) formFieldMap
						// .get(sourceFieldDT.getQuestionIdentifier());
						String dataType = (String) tgtFormField.getFieldType();
						if (dataType.equals(DATE)) {
							this.isDateFormated(errColl, tgtFormField);

							if (targetValueDT.getOperatorTypeCode() != null
									&& targetValueDT.getOperatorTypeCode().equals(
											LESS_THAN)) {

								String formDateV = (String) tgtFormField.getValue();
								String metaDateV = targetValueDT.getTargetValue();
								if (!(compareDateGreaterEqual(formDateV, metaDateV))) {
									// get the targetValueDT and the Error Message
									errorMessage = targetValueDT.getErrorDescTxt();
									break;

								}
							}
						}
					}
				}
			} catch (Exception e) {
				logger.error("Exception occurred in getTargetDateRange: " + e.getMessage());
				e.printStackTrace();
			}	
		}

		return errorMessage;

	}
	public Collection<Object>  getDEM215RequiredField(Collection<Object> fieldColl,
			FormField formField, Map<Object,Object> formFieldMap, Map<Object,Object> answerArrayMap, String actionMode) {

		 if ((formField.getValue() == null || formField
					.getValue().equals(""))) {
			 if(actionMode != null && actionMode.equals(NEDSSConstants.CREATE_SUBMIT_ACTION)){

				if (formField.getErrorMessage().size() == 0)
					formField.getErrorMessage().add(
							formField.getLabel() + " is required");
				formField
						.setErrorStyleClass(RuleConstants.REQUIRED_FIELD_CLASS);
				fieldColl.add(formField);

			}
		 }

		return fieldColl;
	}


}

