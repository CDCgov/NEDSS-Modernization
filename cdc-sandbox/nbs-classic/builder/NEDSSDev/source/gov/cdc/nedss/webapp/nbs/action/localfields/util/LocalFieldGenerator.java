package gov.cdc.nedss.webapp.nbs.action.localfields.util;

import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.ldf.dt.StateDefinedFieldDataDT;
import gov.cdc.nedss.ldf.dt.StateDefinedFieldMetaDataDT;
import gov.cdc.nedss.pam.act.NbsCaseAnswerDT;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.NbsQuestionMetadata;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.HTMLEncoder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.action.localfields.html.Attribute;
import gov.cdc.nedss.webapp.nbs.action.localfields.html.Tag;
import gov.cdc.nedss.webapp.nbs.action.pam.PamClientVO.PamClientVO;
import gov.cdc.nedss.webapp.nbs.action.pam.util.PamLoadUtil;
import gov.cdc.nedss.webapp.nbs.action.util.RuleConstants;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.QuestionsCache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * LocalFieldGenerator is a LDF HTML Generator based on the html types in the meta data
 * @author nmallela
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: Computer Sciences Corporation</p>
 * LocalFieldGenerator.java
 * Sep 5, 2008
 * @version
 */
public class LocalFieldGenerator {

	static final LogUtils logger = new LogUtils(LocalFieldGenerator.class.getName());
	private static final String daoName = JNDINames.LOCAL_FIELD_METADATA_DAO_CLASS;	 
	
	/**
	 * generates HTML from the ldfQuestionMap by ldfPageId and spits it back to render them accordingly
	 * @param questionMap
	 * 
	 */
	public static void makeLdfHtml(String formCd, String actionMode, Map<Object,Object> answerMap, PamClientVO clientVO, HttpServletRequest request,String appender) throws Exception {

		if(QuestionsCache.getQuestionMap()==null) return;		
		Map<Object, Object>  questionMap = (Map<Object, Object> )QuestionsCache.getQuestionMap().get(formCd);
		Collection<Object>  ldfColl = createLDFQuestions(formCd, questionMap, actionMode, request);		
		Map<Object, Object>  parentMap = getAllParentUidMap(ldfColl);
		if(parentMap != null && parentMap.size() > 0) {
			Iterator<Object>  parentIter = parentMap.keySet().iterator();
			while(parentIter.hasNext()) {
				StringBuffer sb = new StringBuffer();
				Long parentId = Long.valueOf(parentIter.next().toString());
				String ldfTab = parentMap.get(parentId) == "LDFTAB" ? "LDFTAB" : "";
				Iterator<Object>  iter = ldfColl.iterator();		
				while(iter.hasNext()) {
					NbsQuestionMetadata dt = (NbsQuestionMetadata) iter.next();
					Long parentUid = dt.getParentUid() == null ? new Long(0) :  dt.getParentUid();
					if(parentId.compareTo(parentUid) == 0) {						
						//Handle Home Page LDFs differently
						if(formCd.equalsIgnoreCase(NEDSSConstants.HOME_PAGE_LDF)) {
							sb.append(buildHomePageHTML(dt, actionMode, answerMap, clientVO));
						} else {
							if(!actionMode.equals(NEDSSConstants.VIEW_LOAD_ACTION)) {
								try {
									sb.append(buildInputHTML(dt, actionMode, answerMap, clientVO));
								} catch (Exception e) {
									logger.error("Error while building LDFs in " + actionMode + " mode: " + e.toString());
								}
							} else{							
								try {
										sb.append(buildViewHTML(dt, actionMode, answerMap, clientVO,appender));
								} catch (Exception e) {
									logger.error("Error while building LDFs in " + actionMode + " mode: " + e.toString());
								}
							}							
						}	
					}
				}
				if(ldfTab.equals("LDFTAB")) {
					request.setAttribute("LDFTAB", sb.toString());
				}else {
					request.setAttribute(parentId.toString(), sb.toString());	
				}
				
			}
		}
		if(!formCd.equalsIgnoreCase(NEDSSConstants.HOME_PAGE_LDF)) {
			//Create Patient Specific LDFs from Legacy Structure
			try {
				loadPatientLDFs(clientVO, actionMode, request,appender);
			} catch (Exception e) {
				logger.error("Error while building Patient LDFs in " + actionMode + " mode: " + e.toString());
			}			
		}
	}

	/**
	 * loadPatientLDFs loads PatientLDFs from legacy structure to the new PAM Structure
	 * @param clientVO
	 * @param actionMode
	 * @param req
	 * @throws Exception
	 */
	public static void loadPatientLDFs(PamClientVO clientVO, String actionMode, HttpServletRequest req,String appender) throws Exception {
		
		PamClientVO tempVO = new PamClientVO();
		Map<Object, Object>  tempAnswerMap = new TreeMap<Object, Object>();
		StringBuffer sb = new StringBuffer();
		
		Collection<Object>  questionColl = new ArrayList<Object> ();
		Collection<Object>  ldfColl = getLDFMetaDataFromEJB(req);
	    TreeMap<Object,Object> metaDataMap = sortLDFCollection(ldfColl);
	    //Collection<Object>  ldfColl = (Collection<Object>) metaDataMap.values();
	    if(ldfColl != null && ldfColl.size() > 0) {
		   Iterator<Object>  iter = ldfColl.iterator();
		    while(iter.hasNext()) {
		        StateDefinedFieldMetaDataDT ldt = (StateDefinedFieldMetaDataDT)iter.next();
		        Long ldfUid = ldt.getLdfUid();
		        String questionId = "PATLDF_"+ ldfUid;
		        NbsQuestionMetadata dt = new NbsQuestionMetadata();
		        dt.setNbsQuestionUid(ldfUid);
		        dt.setQuestionIdentifier(questionId);
		        
		        dt.setLdfPageId(ldt.getLdfPageId());
		        dt.setQuestionLabel(ldt.getLabelTxt());
		        dt.setOrderNbr(ldt.getDisplayOrderNbr());
		        dt.setRequiredInd(ldt.getRequiredInd());
		        dt.setCodeSetNm(ldt.getCodeSetNm());	        
		        dt.setFieldSize(ldt.getFieldSize());
		        //Translations
		        setAdditionalAttributes(ldt, dt);
		        questionColl.add(dt);
		    }	    	
	    }

	    if(actionMode.equals(NEDSSConstants.CREATE_SUBMIT_ACTION) || actionMode.equals(NEDSSConstants.EDIT_SUBMIT_ACTION)) {
	    	tempVO = clientVO;
	    	tempAnswerMap = clientVO.getAnswerMap();
	    }
	    
	    //Get the LDFDataCollection  and create AnswerMap out of it
	    if(actionMode.equals(NEDSSConstants.EDIT_LOAD_ACTION) || actionMode.equals(NEDSSConstants.VIEW_LOAD_ACTION)) {	    
		    PersonVO personVO = null;
		    if(clientVO.getOldPamProxyVO()!=null)
		    	personVO=PamLoadUtil.getPersonVO(NEDSSConstants.PHC_PATIENT, clientVO.getOldPamProxyVO());
		    if(personVO == null){
		    	personVO = (PersonVO)req.getAttribute("personVO");
		    }
		    Collection<Object>  answerColl = personVO.getTheStateDefinedFieldDataDTCollection();
		    if(answerColl != null && answerColl.size() > 0) {
			   Iterator<Object>  answerIter = answerColl.iterator();
			    while(answerIter.hasNext()) {
			    	StateDefinedFieldDataDT dt = (StateDefinedFieldDataDT) answerIter.next();
			    	StateDefinedFieldMetaDataDT metadata = (StateDefinedFieldMetaDataDT)metaDataMap.get(dt.getLdfUid());
					String dType = metadata.getDataType() == null ? "" : metadata.getDataType();
					Long fieldSize = metadata.getFieldSize() == null ? new Long(0) : Long.valueOf(metadata.getFieldSize()); 
					//Multiselect
					if(dType.equalsIgnoreCase("CV") && fieldSize.intValue() >= 2) {
						String[] list = makeAnswerList(dt.getLdfValue());
						String qId = "PATLDF_" + dt.getLdfUid();
						tempVO.setAnswerArray(qId, list);
					} else {
						NbsCaseAnswerDT answerDT = new NbsCaseAnswerDT();
						answerDT.setAnswerTxt(dt.getLdfValue());
						tempAnswerMap.put(dt.getLdfUid(), answerDT);
					}	    	
			    }		    	
		    }
	    }
	    
	    //Now iterate through the fillerColl and generate HTML appropriately	    
	   Iterator<Object>  newIter = questionColl.iterator();	    
	    
	    while(newIter.hasNext()) {
	    	
	    	NbsQuestionMetadata dt = (NbsQuestionMetadata) newIter.next();
	    	
			if(!actionMode.equals(NEDSSConstants.VIEW_LOAD_ACTION)) {				
				try {
					sb.append(buildInputHTML(dt, actionMode, tempAnswerMap, tempVO));
				} catch (Exception e) {
					logger.error("Error while building Patient LDFs in " + actionMode + " mode: " + e.toString());
				}				
			} else {				
				try {
					sb.append(buildViewHTML(dt, actionMode, tempAnswerMap, tempVO,appender));
				} catch (Exception e) {
					logger.error("Error while building Patient LDFs in " + actionMode + " mode: " + e.toString());
				}
			}
				
			req.setAttribute("NEWPAT_LDFS", sb.toString());
	    }
	}
	
	private static String[] makeAnswerList(String value) {
		StringTokenizer st = new StringTokenizer(value, "|");
		int size = st.countTokens();
		String[] returnList = new String[size];
		for(int i=0; i< size; i++) {
			String tkn = st.nextToken();
			returnList[i] = tkn;
		}
		return returnList; 
	}
	
	private static void setAdditionalAttributes(StateDefinedFieldMetaDataDT ldt, NbsQuestionMetadata dt) {
		
		String dType = ldt.getDataType() == null ? "" : ldt.getDataType();
		String vType = ldt.getValidationTxt() == null ? "" : ldt.getValidationTxt();
		if(dType.equalsIgnoreCase("CV")) {
			dt.setDataType("Coded");
			Long fieldSize = dt.getFieldSize() == null ? new Long(0) : Long.valueOf(dt.getFieldSize()); 
			if(fieldSize.intValue() >= 2)
				dt.setNbsUiComponentUid(new Long("1013"));
			else
				dt.setNbsUiComponentUid(new Long("1007"));
			
		} else if(dType.equalsIgnoreCase("ST")) {
			dt.setDataType("Text");
			if(vType.equalsIgnoreCase("TS"))
				dt.setDataType("Date");				
			else if(vType.equalsIgnoreCase("INT"))
				dt.setDataType("Numeric");
			
		} else if(dType.equalsIgnoreCase("LNK")) {
			dt.setDataType("Hyperlink");
		} else if(dType.equalsIgnoreCase("LIST_ST")) {
			dt.setDataType("TextArea");
		} else if(dType.equalsIgnoreCase("SUB")) {
			dt.setDataType("Subheading");			
		}
	}
	
	/**
	 * createLDFQuestionMap creates an LDF Map<Object, Object> <Object,Object> from data base and synch with the QuestionMap for latest info
	 * @param questionMap
	 * @return Map<Object, Object> 
	 */
	private static Collection<Object>  createLDFQuestions(String formCd, Map<Object,Object> questionMap, String actionMode, HttpServletRequest request) {

		Map<Object, Object>  ldfsInCache = fetchLDFsInQCache(questionMap);
		Map<Object, Object>  ldfsFromDb = new HashMap<Object,Object>();
		Object[] searchParams = {formCd};
		Object[] oParams = new Object[] { daoName, "retrieveLDFsForDisplay", searchParams };
		Collection<Object>  coll = null;
		try {
			coll = (Collection<Object>) LocalFieldUtil.processRequest(oParams, request.getSession());
		} catch (Exception e) {
			logger.error("Error while retrieveing LDFsForDisplay: " + e.toString());
		}
    	if (coll != null && coll.size() > 0) {    		
    		Iterator<Object>  ite = coll.iterator();
    		while (ite.hasNext()) {
    			NbsQuestionMetadata qMetadata = (NbsQuestionMetadata) ite.next();    			
   				String questionId = new String(qMetadata.getQuestionIdentifier() == null ? "" : qMetadata.getQuestionIdentifier());
   				int parentUid = qMetadata.getParentUid() == null ? 0 : qMetadata.getParentUid().intValue();
   				if(questionId.equals("")) {
   					questionId = "LDFTAB";
					qMetadata.setQuestionIdentifier(questionId);
   				}
   				ldfsFromDb.put(questionId, questionId);
   				
   				//Temp fix for CaseVerificationTab(RVCT) which appears only on Edit
   				if(parentUid == 1021 || parentUid == 1335) {
   					String reqInd = qMetadata.getRequiredInd() == null ? "" : qMetadata.getRequiredInd(); 
   					if(reqInd.equals("T") && actionMode.equals(NEDSSConstants.CREATE_SUBMIT_ACTION)) {
   						qMetadata.setRequiredInd("F");
   					}
   				}
   				questionMap.put(questionId, qMetadata);						
    		}
    	}
    	//Finally remove the logically deleted ones from the questionMap
    	Iterator<Object>  iter = ldfsInCache.keySet().iterator();
    	while(iter.hasNext()) {
    		String key = String.valueOf(iter.next());
    		if(ldfsFromDb.get(key) == null)
    			questionMap.remove(key);
    	}
    	
		return coll;
	}
	
	private static Map<Object,Object> fetchLDFsInQCache(Map<Object, Object>  questionMap) {
		Map<Object, Object>  returnMap = new HashMap<Object,Object>();
		Iterator<Object>  iter = questionMap.keySet().iterator();
		while(iter.hasNext()) {
			String key = String.valueOf(iter.next());
			if(key.startsWith("LDF"))
				returnMap.put(key, key);
		}
		return returnMap;		
	}
	
	private static String buildViewHTML(NbsQuestionMetadata dt, String actionMode, Map <Object,Object> answerMap, PamClientVO clientVO,String appender)  throws Exception {
		String reqClass = null;
		Long questionUid = dt.getNbsQuestionUid();
		String questionId = dt.getQuestionIdentifier();
		String value = "";
		String label =  HTMLEncoder.encodeHtmlAttribute(dt.getQuestionLabel());
		if(appender.equals(":"))
		label = HTMLEncoder.encodeHtmlAttribute(label + appender);
		String tooltip =  HTMLEncoder.encodeHtmlAttribute(dt.getQuestionToolTip() == null ? "" : dt.getQuestionToolTip());
		String dataType =  HTMLEncoder.encodeHtmlAttribute(dt.getDataType() == null ? "text" : dt.getDataType().trim());
		Attribute aTooltip = new Attribute("title", tooltip);
		int componentUid = dt.getNbsUiComponentUid() == null ? 0 : dt.getNbsUiComponentUid().intValue();
		
		if(dt.getRequiredInd() != null && dt.getRequiredInd().trim().equals("T"))
			reqClass = RuleConstants.REQUIRED_FIELD_CLASS;
		else
			reqClass = RuleConstants.NOT_REQUIRED_FIELD_CLASS;
		
		Object obj = answerMap.get(questionUid);
		if(obj instanceof NbsCaseAnswerDT) {
			NbsCaseAnswerDT answerDT = (NbsCaseAnswerDT)obj;
			value = HTMLEncoder.encodeHtmlAttribute(answerDT.getAnswerTxt() == null ? "" : answerDT.getAnswerTxt()) ;
		}
		//Tags
		Tag row = new Tag("tr");
		Tag labelColumn = new Tag("td", "class=fieldName");
		Tag span1 = new Tag("span", "style=" + HTMLEncoder.encodeHtmlAttribute(reqClass));
		if(dt.getRequiredInd() != null && dt.getRequiredInd().trim().equals("T"))
		   span1.add("*");
		labelColumn.add(span1);
		
		if(dataType.equalsIgnoreCase("Hyperlink")) {
			Tag span2 = new Tag("span", "");
			span2.addAttribute(aTooltip);
			span2.add(label);
			labelColumn.add(span2);			
			row.add(labelColumn);
			row = makeHyperLink(row, label, tooltip);
		} else if(dataType.equalsIgnoreCase("Subheading")) {
			makeSubHeading(row, labelColumn, label);
		} else if(dataType.equalsIgnoreCase("Coded")) {
			//For multiselects in view pages, values should be comma separated
			if(componentUid == 1013) {
				String[] answerList = clientVO.getAnswerArray(questionId) == null ? new String[1] : clientVO.getAnswerArray(questionId);
				value = HTMLEncoder.encodeHtmlAttribute(getCodeDescTxtForMultiSelect(answerList, dt.getCodeSetNm()));
			//For single selects	
			} else if(componentUid == 1007) {
				if(value != null)
					value = HTMLEncoder.encodeHtmlAttribute(getCodeDescTxtForSelect(value, dt.getCodeSetNm()));
			}
				Tag span2 = new Tag("span", "");
				span2.addAttribute(aTooltip);
				span2.add(label);
				labelColumn.add(span2);
				row.add(labelColumn);						
				Tag valueColumn = new Tag("td");
				valueColumn.add(value);
				row.add(valueColumn);				
			
		} else {
			//Simple label and value
			Tag span2 = new Tag("span", "style=");
			span2.addAttribute(aTooltip);
			span2.add(label);
			labelColumn.add(span2);
			row.add(labelColumn);		
			
			Tag valueColumn = new Tag("td");
			valueColumn.add(value);
			row.add(valueColumn);
		}

		return row.toString();
	}
	
	/**
	 * buildInputHTML builds a html record to enter data row based on the NbsQuestionMetadata
	 * @param dt, actionMode, answerMap
	 * @return java.lang.String
	 */
	private static String buildInputHTML(NbsQuestionMetadata dt, String actionMode, Map<Object,Object> answerMap, PamClientVO clientVO) {
		
		String reqClass = null;
		String value = "";
		String tooltip =  HTMLEncoder.encodeHtmlAttribute(dt.getQuestionToolTip() == null ? "" : dt.getQuestionToolTip());
		String label =  HTMLEncoder.encodeHtmlAttribute(dt.getQuestionLabel() == null ? "" : dt.getQuestionLabel());
		String dataType =  HTMLEncoder.encodeHtmlAttribute(dt.getDataType() == null ? "text" : dt.getDataType().trim());
		String questionId = HTMLEncoder.encodeHtmlAttribute(dt.getQuestionIdentifier());
		String size = HTMLEncoder.encodeHtmlAttribute(dt.getFieldSize() == null ? "20" : dt.getFieldSize());
		String reqInd = HTMLEncoder.encodeHtmlAttribute(dt.getRequiredInd() == null ? "" : dt.getRequiredInd());
		String futureDtInd = HTMLEncoder.encodeHtmlAttribute(dt.getFutureDateInd() == null ? "" : dt.getFutureDateInd());
		int componentUid = dt.getNbsUiComponentUid() == null ? 0 : dt.getNbsUiComponentUid().intValue();
		
		String name = "pamClientVO.answer(" + questionId + ")";
		Long questionUid = dt.getNbsQuestionUid();
		Object obj = answerMap.get(questionUid);
		//Check if obj is null, value is directly mapped to questionId (from FORM)
		if(obj == null) {
			if(actionMode.equals(NEDSSConstants.CREATE_SUBMIT_ACTION) || actionMode.equals(NEDSSConstants.EDIT_SUBMIT_ACTION)) {
				value = HTMLEncoder.encodeHtmlAttribute(answerMap.get(questionId) == null ? "" : (String) answerMap.get(questionId));		
			}
		} else if(obj instanceof NbsCaseAnswerDT) {
			NbsCaseAnswerDT answerDT = (NbsCaseAnswerDT)obj;
			value = HTMLEncoder.encodeHtmlAttribute(answerDT.getAnswerTxt() == null ? "" : answerDT.getAnswerTxt()) ;
		}		

		//Common Tags
		Tag input = new Tag("input");		
		Tag select = new Tag("select");
		Tag image = new Tag("img");
		Tag textarea = new Tag("textarea");
		Tag div = new Tag("div");
		Tag bold = new Tag("b");
		
		//common Tags and attributes
		Attribute textType = new Attribute("type", "text");
		Attribute idAttribute = new Attribute("id", HTMLEncoder.encodeHtmlAttribute(questionId));
		Attribute nameAttribute = new Attribute("name", HTMLEncoder.encodeHtmlAttribute(name));
		Attribute valueAttribute = new Attribute("value", HTMLEncoder.encodeHtmlAttribute(value));
		Attribute sizeAttribute = new Attribute("size", HTMLEncoder.encodeHtmlAttribute(size));
		Attribute maxLength = new Attribute("maxlength", "10");
		Attribute aTooltip = new Attribute("title", HTMLEncoder.encodeHtmlAttribute(tooltip));
		Attribute imgSrc = new Attribute("src", "calendar.gif");
		
		
		
		if(reqInd.equals("T"))
			reqClass = RuleConstants.REQUIRED_FIELD_CLASS;
		else
			reqClass = RuleConstants.NOT_REQUIRED_FIELD_CLASS;

		Tag row = new Tag("tr");
		Tag labelColumn = new Tag("td", "class=fieldName");
		Tag span1 = new Tag("span", "style=" + reqClass);
		if(reqInd.equals("T"))
			span1.add("*");
		labelColumn.add(span1);

		//Tag span2 = new Tag("span", "style=" + reqClass);
		Tag span2 = new Tag("span");
		if(reqInd.equals("T") && (actionMode.equals(NEDSSConstants.CREATE_SUBMIT_ACTION) || actionMode.equals(NEDSSConstants.EDIT_SUBMIT_ACTION))) {
			if(value.equals("") )
				span2.addAttribute(new Attribute("style", reqClass));			
		}
		span2.addAttribute(aTooltip);
		span2.add(label);
		labelColumn.add(span2);
		row.add(labelColumn);
		
		Tag valueColumn = new Tag("td");
		//Based on the datatype, build html types...
		if(dataType.equalsIgnoreCase("Text")) {
			input.addAttribute(idAttribute);
			input.addAttribute(textType);
			input.addAttribute(aTooltip);
			input.addAttribute(sizeAttribute);
			input.addAttribute(nameAttribute);
			input.addAttribute(valueAttribute);
			maxLength = new Attribute("maxlength", "2000");
			input.addAttribute(maxLength);
			valueColumn.add(input);
			
		} else if(dataType.equalsIgnoreCase("Date")) {
			input.addAttribute(idAttribute);
			input.addAttribute(textType);
			sizeAttribute = new Attribute("size", "10");
			input.addAttribute(sizeAttribute);
			input.addAttribute(maxLength);
			input.addAttribute(nameAttribute);
			input.addAttribute(valueAttribute);
			Attribute onkeyup = null;
			if(futureDtInd != null && futureDtInd.equals("T"))
				onkeyup = new Attribute("onkeyup", "DateMaskFuture(this)");
			else
				onkeyup = new Attribute("onkeyup", "DateMask(this,null,event)");
			
			input.addAttribute(onkeyup);
			input.addAttribute(aTooltip);
			valueColumn.add(input);
			
			image.addAttribute(imgSrc);
			String imgId = dt.getQuestionIdentifier() == null ? "" : (dt.getQuestionIdentifier() + "Icon");
			image.addAttribute(new Attribute("id", imgId));
			String getCalcDt = "getCalDate('"+ dt.getQuestionIdentifier() + "','" + imgId + "'); return false;";
			image.addAttribute(new Attribute("onclick", getCalcDt));
			valueColumn.add(image);
			
		} else if(dataType.equalsIgnoreCase("Numeric")) {
			input.addAttribute(idAttribute);
			input.addAttribute(textType);
			input.addAttribute(sizeAttribute);
			maxLength = new Attribute("maxlength", size);
			input.addAttribute(maxLength);
			input.addAttribute(nameAttribute);
			input.addAttribute(valueAttribute);
			Attribute onkeyup = new Attribute("onkeyup", "isNumeric(this)");
			input.addAttribute(onkeyup);
			input.addAttribute(aTooltip);
			valueColumn.add(input);
		} else if(dataType.equalsIgnoreCase("Coded")) {
			
			String optionList = "";
			//Single Select
			if(componentUid == 1007) {
				try {
					optionList = buildOptionsListWithSelectedValues(value, dt.getCodeSetNm());
				} catch (Exception e) {
					logger.error("Error while building LDF Select optionList  for codesetNm: " + dt.getCodeSetNm() + ": " + e.toString());
				}				
				String selectStart = renderNBSSelectStartElement(questionId, tooltip, name);				
				valueColumn.add(selectStart + optionList + "</select></div>");				
				
			//Multi-Select				
			} else if(componentUid == 1013) {		
				Map<Object, Object>  vMap = new TreeMap();
				String[] valueList = clientVO.getAnswerArray(questionId);
				if(valueList != null && valueList.length > 0) {
					for (int i = 1; i <= valueList.length; i++) {
						String val = valueList[i-1]; 
						vMap.put(val, val);
					}
				}
				try {
					optionList = buildOptionsListWithMultiSelectedValues(vMap, dt.getCodeSetNm());
				} catch (Exception e) {
					logger.error("Error while building LDF Select optionList  for codesetNm: " + dt.getCodeSetNm() + ": " + e.toString());
				}				
				name = "pamClientVO.answerArray(" + questionId + ")";
				select.addAttribute(new Attribute("id", questionId));
				select.addAttribute(new Attribute("name", name));
				select.addAttribute(new Attribute("multiple","multiple"));
				select.addAttribute(new Attribute("size","4"));
				String divId = questionId + "-selectedValues";
				select.addAttribute(new Attribute("onchange", "displaySelectedOptions(this, '" + divId + "')"));				
				select.add(optionList);
				div.addAttribute(new Attribute("class", "multiSelectBlock"));
				Tag italic = new Tag("i");
				italic.add("(Use Ctrl to select more than one)");
				div.add(italic);
				div.add("<br/>");
				div.add(select);
				div.add("<br/>");
				Tag sDiv = new Tag("div");
				sDiv.addAttribute(new Attribute("id", divId));
				sDiv.addAttribute(new Attribute("style", "margin:0.25em;"));
				bold.add("Selected Values: ");
				sDiv.add(bold);
				div.add(sDiv);				
				valueColumn.add(div);
			}			
		} else if(dataType.equalsIgnoreCase("Readonly")) {
			valueColumn.add(value);
		
		} else if(dataType.equalsIgnoreCase("TextArea")) {
			textarea.addAttribute(nameAttribute);
			textarea.addAttribute(new Attribute("style","WIDTH: 500px; HEIGHT: 100px;"));
			textarea.addAttribute(new Attribute("onkeydown","checkMaxLength(this);"));
			textarea.addAttribute(new Attribute("onkeyup","checkMaxLength(this);"));
			textarea.add(value);
			valueColumn.add(textarea);			
			
		} if(dataType.equalsIgnoreCase("Subheading")) {
			makeSubHeading(row, labelColumn, label);
			
		} else if(dataType.equalsIgnoreCase("Hyperlink")) {
			row = makeHyperLink(row, label, tooltip);
		}
		
		row.add(valueColumn);
		
		return row.toString();
	}
	
	/**
	 * getAllParentUidMap is a set of all available Tabs & Sections that are parents of the LDF to display accordingly.
	 * @param questionMap
	 * @return
	 */
	private static Map<Object,Object> getAllParentUidMap(Collection<Object>  ldfQColl) {
		Map<Object, Object>  parentMap = new TreeMap<Object, Object>();		
		try {
			Iterator<Object>  iter = ldfQColl.iterator();
			while(iter.hasNext()) {
				NbsQuestionMetadata dt = (NbsQuestionMetadata) iter.next();
				if(dt.getLdfPageId() != null && dt.getLdfPageId().length() > 0) {
					//put everything in the map except TAB(which does not have a parent defined !!)
					if(dt.getParentUid() != null) {
						if(parentMap.get(dt.getParentUid()) == null)
							parentMap.put(dt.getParentUid(), dt.getParentUid());
						//Once scenario when parentUid is null is for TAB, and since we are adding only 1 LDF Tab, just put it
					} else {
						parentMap.put(dt.getNbsUiMetadataUid(), "LDFTAB");
					}
					
				}
			}
		} catch (Exception e) {
			logger.error("Error while getting Parent Uids for LDFs: " + e.toString());
		}	
		return parentMap;
	}
	
	/**
	 * NBS Specific Type ahead
	 * @param identifier
	 * @param tooltip
	 * @param name
	 * @return
	 */
    private static String renderNBSSelectStartElement(String identifier, String tooltip, String name) {
    	
    	StringBuffer results = new StringBuffer();
    	try {
			String width = "20";
			String textbox = identifier + "_textbox";
			String button = identifier + "_button";        
			results.append("<input size=\"").append(width).append("\" type=\"text\"");
			prepareAttribute(results, "name", textbox);
			if(!tooltip.equals(""))
				results.append(" title=\"").append(tooltip).append("\"");
			
			results.append(" onfocus=\"storeCaret(this);AutocompleteStoreOnFocusValue(this);\"");
			results.append(" onkeydown=\"CheckTab('").append(identifier).append("',this);\"");
			results.append(" onkeyup=\"AutocompleteComboBox('").append(textbox).append("','").append(identifier).append("',true);storeCaret(this);\"");
			results.append(" onblur=\"AutocompleteSynch('").append(textbox).append("','").append(identifier).append("');\"");
			results.append(" value=\"\">");
			//dropdown box as image
			results.append("<img align=\"top\" alt=\"Choose option\" border=\"0\" src=\"type-ahead2.gif\"");
			results.append(" name=\"").append(button).append("\"");
			results.append(" onclick=\"AutocompleteExpandListbox('").append(textbox).append("','").append(identifier).append("');\"/>");
			//div to display the select-box
			results.append("<div class=\"page_name_auto_complete\">");

			//USUAL select box
			results.append("<select ");
			prepareAttribute(results, "name", name);
			prepareAttribute(results, "size", "5");
			prepareAttribute(results, "id", identifier);
			//hook NBS specific other Select attributes here
			results.append(" AUTOCOMPLETE=\"off\"");
			results.append(" class=\"none\" ");     
			results.append(" onclick=\"AutocompleteComboBox('").append(textbox).append("','").append(identifier).append("',true,true);this.className=&quot;none&quot;;\"");
			results.append(" onkeyup=\"AutocompleteComboBox('").append(textbox).append("','").append(identifier).append("',true,true);\"");
			results.append(" onblur=\"this.className='none';\"");
			results.append(" typeahead=\"true\"");
			results.append(">");
		} catch (Exception e) {
			logger.error("Error while creating NBS new Select Tag Element: " + e.toString());
		}
        
        return results.toString();
    }
    
    protected static void prepareAttribute(StringBuffer handlers, String name, Object value) {
            if (value != null) {
                handlers.append(" ");
                handlers.append(name);
                handlers.append("=\"");
                handlers.append(value);
                handlers.append("\"");
            }
    }	
    
    /**
     * Builds option list
     * @param answer
     * @param codeSetNm
     * @return
     */
	private static String buildOptionsListWithSelectedValues(String answer, String codeSetNm) {
		String options = getCodedValues(codeSetNm);
		StringBuffer sb = new StringBuffer("<option value=\"\"></option>");
			if (options != null && options.length() > 0) {
				
				StringTokenizer st = new StringTokenizer(options, "|");
				while (st.hasMoreTokens()) {
					String token = st.nextToken();					
					String key = token.substring(0,token.indexOf("$"));
					String value = token.substring(token.indexOf("$")+1);
					Tag option = new Tag("option");
					option.addAttribute(new Attribute("value",key));
					if(!key.equals("") && answer.equalsIgnoreCase(key))
						option.addAttribute(new Attribute("selected","selected"));
					if(!value.equals(""))
						option.add(value);
					sb.append(option.toString());					
				}
			}
			return sb.toString();
		}    
	
	
	private static String buildOptionsListWithMultiSelectedValues(Map<Object, Object>  multiMap, String codeSetNm) {
		String options = getCodedValues(codeSetNm);
		StringBuffer sb = new StringBuffer("<option value=\"\"></option>");
			if (options != null && options.length() > 0) {
				
				StringTokenizer st = new StringTokenizer(options, "|");
				while (st.hasMoreTokens()) {
					String token = st.nextToken();					
					String key = token.substring(0,token.indexOf("$"));
					String value = token.substring(token.indexOf("$")+1);
					Tag option = new Tag("option");
					option.addAttribute(new Attribute("value",key));
					String answer = multiMap.get(key) == null ? "" : (String) multiMap.get(key);
					if(!key.equals("") && answer.equalsIgnoreCase(key))
						option.addAttribute(new Attribute("selected","selected"));
					if(!value.equals(""))
						option.add(value);
					sb.append(option.toString());					
				}
			}
			return sb.toString();
		}
	
	private static String getCodedValues(String type) {
		CachedDropDownValues srtStatic = new CachedDropDownValues();
		//GetSRTCodes srtStatic = new GetSRTCodes();

		//PHC_TYPE - Condition_Code
		if (type.equalsIgnoreCase("PHC_TYPE"))
			return srtStatic.getConditionCode();
		//P_RACE_CAT - ROOT Parent_cd  P_RACE - Sub categories
		else if (
			type.equalsIgnoreCase("P_RACE")
				|| type.equalsIgnoreCase("P_RACE_CAT"))
			return srtStatic.getRaceCodesByCodeSet(type);
		//P_LANG - Language_Code
		else if (type.equalsIgnoreCase("P_LANG"))
			return srtStatic.getLanguageCode();
		//O_NAICS - NAICS_industry_code
		else if (type.equalsIgnoreCase("O_NAICS"))
			return srtStatic.getNAICSGetIndustryCode();
		//P_OCCUP Occupation_code
		else if (type.equalsIgnoreCase("P_OCCUP"))
			return srtStatic.getOccupationCode();
		//PLS_CNTRY - country code
		else if (type.equalsIgnoreCase("PSL_CNTRY"))
			return srtStatic.getCountryCodesAsString();
		//STATE_CCD - state codes
		else if (type.equalsIgnoreCase("STATE_CCD"))
			return srtStatic.getStateCodes("USA");
		else {
			String retStr = null;
			retStr = srtStatic.getSAICDefinedCodedValues(type);
			if (retStr != null && retStr.trim().length() != 0)
				return retStr;
			else
				return srtStatic.getCodedValues(type);
		}

	}

	private static Tag makeHyperLink(Tag row, String label, String tooltip) {
		
		try {
			if(! tooltip.equals("")) {
				Tag hyperlink = new Tag("a");
				Tag valueColumn = new Tag("td");
				String hyperLnk = tooltip.substring(0,tooltip.indexOf("("));
				String lnkUrl = tooltip.substring(tooltip.indexOf("(")+1, tooltip.indexOf(")"));
				hyperlink.addAttribute(new Attribute("href", HTMLEncoder.encodeHtml(lnkUrl)));
				hyperlink.addAttribute(new Attribute("target", "_blank"));
				hyperlink.add(HTMLEncoder.encodeHtml(hyperLnk));
				valueColumn.add(hyperlink);			
				row.add(valueColumn);
			//For Patient LDF Scenario where tooltip is missing in metadata				
			} else {
				row = new Tag("tr");
				Tag labelColumn = new Tag("td", "class=fieldName");
				String displayLabel = label.substring(0,label.indexOf("(")); 
				labelColumn.add(HTMLEncoder.encodeHtml(displayLabel));
				row.add(labelColumn);
				Tag valueColumn = new Tag("td");
				String lnkUrl = label.substring(label.indexOf("(")+1, label.indexOf(")"));
				Tag hyperlink = new Tag("a");
				hyperlink.addAttribute(new Attribute("href", HTMLEncoder.encodeHtml(lnkUrl)));
				hyperlink.addAttribute(new Attribute("target", "_blank"));
				hyperlink.add(HTMLEncoder.encodeHtml(displayLabel));
				valueColumn.add(hyperlink);			
				row.add(valueColumn);				
			}
			
		} catch (Exception e) {
			logger.error("LDF of type Hyperlink not added properly...Please check and add again !!!: " + e.toString());
		}
		return row;
	}
	
	private static void makeSubHeading(Tag row, Tag labelColumn, String label) {
		row.clear();
		labelColumn = new Tag("td");
		labelColumn.addAttribute(new Attribute("colspan", "2"));			
		Attribute sh = new Attribute("style","FONT-WEIGHT: bold;COLOR: #000;");
		labelColumn.addAttribute(sh);
		labelColumn.add(HTMLEncoder.encodeHtml(label));			
		row.add(labelColumn);		
		
	}


	private static String getCodeDescTxtForSelect(String code, String codeSetNm) {
		String options  = getCodedValues(codeSetNm);
		StringBuffer desc = new StringBuffer("");
		StringTokenizer st = new StringTokenizer(options, "|");
		while (st.hasMoreTokens()) {
			String token = st.nextToken();					
			String key = token.substring(0,token.indexOf("$"));
			String value = token.substring(token.indexOf("$")+1);
			if (key.equals(code)) {
				desc.append(value);
				break;
			}			
		}
		return desc.toString();
	}
	
	private static String getCodeDescTxtForMultiSelect(Object[] codes, String codeSetNm) {
		String options  = getCodedValues(codeSetNm);
		StringBuffer desc = new StringBuffer("");
		String output = "";
		if (codes.length > 0) {
			for (int i = 0; i < codes.length; i++) {
				String cd = (String)codes[i];
				StringTokenizer st = new StringTokenizer(options, "|");
				while (st.hasMoreTokens()) {
					String token = st.nextToken();					
					String key = token.substring(0,token.indexOf("$"));
					String value = token.substring(token.indexOf("$")+1);
					if (key.equals(cd)) {
						desc.append(value);
						desc.append(", ");
					}			
				}
			}
			if(desc.length()>2)
			output=desc.substring(0, desc.length() - 2);
		}
		return output;
	}

	/**
	 * 
	 * @param sPageID
	 * @param request
	 * @param session
	 * @return
	 */
	private static Collection<Object>  getLDFMetaDataFromEJB(HttpServletRequest request) {
		//PagesetId '4' corresponds to Patient
		String sPageID = "4";
		ArrayList<Object> dtList = null;

		try {
			Object[] searchParams = { sPageID };
			Object[] oParams = new Object[] { daoName, "selectMetaDataByPageIdforLDF", searchParams};
			dtList = (ArrayList<Object> ) LocalFieldUtil.processRequest(oParams, request.getSession());
		} catch (Exception e) {		
			logger.error("Error while calling getLDFMetaDataFromEJB in LocalFieldgenerator: " + e.toString());
		}
		
		return dtList;
	}
	
	  private static TreeMap<Object,Object> sortLDFCollection(Collection<Object>  metaDataColl) {
		TreeMap<Object, Object> returnMap = new TreeMap<Object, Object>();

		Iterator<Object>  it = metaDataColl.iterator();
		while (it.hasNext()) {
			StateDefinedFieldMetaDataDT metaDataDT = (StateDefinedFieldMetaDataDT) it.next();
			if (metaDataDT.getClassCd() != null && metaDataDT.getClassCd().equalsIgnoreCase("STATE")) {
				returnMap.put(metaDataDT.getLdfUid(), metaDataDT);
			} 
		}
		return returnMap;
	}	
	
	/**
	 * HomePage LDFs support currently SubHeading, HyperLinks and Comments(readonly text)
	 * @param dt
	 * @param actionMode
	 * @param answerMap
	 * @param clientVO
	 * @return
	 * @throws Exception
	 */  
	private static String buildHomePageHTML(NbsQuestionMetadata dt, String actionMode, Map<Object,Object> answerMap, PamClientVO clientVO)  throws Exception {
			Long questionUid = dt.getNbsQuestionUid();
			String returnSt = "";
			String label =  dt.getQuestionLabel();
			String tooltip =  dt.getQuestionToolTip() == null ? "" : dt.getQuestionToolTip();
			String dataType =  dt.getDataType() == null ? "text" : dt.getDataType().trim();
			String value = "";
			Object obj = answerMap.get(questionUid);
			if(obj instanceof NbsCaseAnswerDT) {
				NbsCaseAnswerDT answerDT = (NbsCaseAnswerDT)obj;
				value = answerDT.getAnswerTxt() == null ? "" : answerDT.getAnswerTxt() ;
			}
			//Tags
			String hyperLnk = ""; String lnkUrl = ""; 
			if(dataType.equalsIgnoreCase("Hyperlink")) {
				try {
					hyperLnk = tooltip.substring(0,tooltip.indexOf("("));
					lnkUrl = tooltip.substring(tooltip.indexOf("(")+1, tooltip.indexOf(")"));
				} catch (RuntimeException e) {
					e.printStackTrace();
					logger.error("Incorrect HyperLink for HomePage LDFs with questionUid: " + questionUid + ", stack is: " + e.getMessage());
				}
				if(hyperLnk.length() > 0 && lnkUrl.length() > 0)
					returnSt = "<tr><td colspan=\"2\"><span title=\"" + HTMLEncoder.encodeHtml(tooltip) + "\">" +  HTMLEncoder.encodeHtml(label) + "</span>&nbsp;<a href=\"" + HTMLEncoder.encodeHtml(lnkUrl) + "\" target=\"_blank\">" + HTMLEncoder.encodeHtml(hyperLnk) + "</a></td></tr>" ;
				else
					returnSt = "<tr><td colspan=\"2\" style=\"padding:5px;\" ><span title=\"" + HTMLEncoder.encodeHtml(tooltip) + "\">" +  HTMLEncoder.encodeHtml(label) + "</span></td></tr>" ;
			} else if(dataType.equalsIgnoreCase("Subheading")) {
				Tag row = new Tag("tr");
				Tag labelColumn = new Tag("td", "class=fieldName");
				makeSubHeading(row, labelColumn, label);
				returnSt = row.toString();
			} else {
				returnSt = "<tr><td colspan=\"2\"><span title=\"" + HTMLEncoder.encodeHtml(tooltip) + "\">" +  HTMLEncoder.encodeHtml(label) + "</span></td></tr>";
			}

			return returnSt;
		}	  
	
}
