package gov.cdc.nedss.pagemanagement.wa.xml;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeMap;

import org.apache.xmlbeans.XmlOptions;

import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.vo.PageElementVO;
import gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.vo.PageManagementProxyVO;
import gov.cdc.nedss.pagemanagement.util.PageMetaConstants;
import gov.cdc.nedss.pagemanagement.wa.dt.WaNndMetadataDT;
import gov.cdc.nedss.pagemanagement.wa.dt.WaQuestionDT;
import gov.cdc.nedss.pagemanagement.wa.dt.WaRdbMetadataDT;
import gov.cdc.nedss.pagemanagement.wa.dt.WaTemplateDT;
import gov.cdc.nedss.pagemanagement.wa.dt.WaUiMetadataDT;
//Note: xmlbeans jar file maps here..
import gov.cdc.nedss.pagemanagement.wa.xml.util.ActionButtonType;
import gov.cdc.nedss.pagemanagement.wa.xml.util.CodedQuestionType;
import gov.cdc.nedss.pagemanagement.wa.xml.util.DataMartInfoType;
import gov.cdc.nedss.pagemanagement.wa.xml.util.DateQuestionType;
import gov.cdc.nedss.pagemanagement.wa.xml.util.HeaderType;
import gov.cdc.nedss.pagemanagement.wa.xml.util.MarshallPageRules;
import gov.cdc.nedss.pagemanagement.wa.xml.util.MaskToJavaScriptFunction;
import gov.cdc.nedss.pagemanagement.wa.xml.util.MessagingInfoType;
import gov.cdc.nedss.pagemanagement.wa.xml.util.NumericQuestionType;
import gov.cdc.nedss.pagemanagement.wa.xml.util.OnBatchAddFunctionType;
import gov.cdc.nedss.pagemanagement.wa.xml.util.OrigDocListType;
import gov.cdc.nedss.pagemanagement.wa.xml.util.PageElementDependentJavaScript;
import gov.cdc.nedss.pagemanagement.wa.xml.util.PageElementType;
import gov.cdc.nedss.pagemanagement.wa.xml.util.PageInfoDocument;
import gov.cdc.nedss.pagemanagement.wa.xml.util.PageInfoType;
import gov.cdc.nedss.pagemanagement.wa.xml.util.PageTemplateType;
import gov.cdc.nedss.pagemanagement.wa.xml.util.PageType;
import gov.cdc.nedss.pagemanagement.wa.xml.util.ParticipationQuestionType;
import gov.cdc.nedss.pagemanagement.wa.xml.util.PatientSearchType;
import gov.cdc.nedss.pagemanagement.wa.xml.util.QuestionLibraryType;
import gov.cdc.nedss.pagemanagement.wa.xml.util.SectionType;
import gov.cdc.nedss.pagemanagement.wa.xml.util.SetValuesButtonType;
import gov.cdc.nedss.pagemanagement.wa.xml.util.StaticCommentType;
import gov.cdc.nedss.pagemanagement.wa.xml.util.StaticInfoBarType;
import gov.cdc.nedss.pagemanagement.wa.xml.util.StaticLinkType;
import gov.cdc.nedss.pagemanagement.wa.xml.util.StaticParticipantListType;
import gov.cdc.nedss.pagemanagement.wa.xml.util.SubSectionType;
import gov.cdc.nedss.pagemanagement.wa.xml.util.TabType;
import gov.cdc.nedss.pagemanagement.wa.xml.util.TextAreaType;
import gov.cdc.nedss.pagemanagement.wa.xml.util.TextQuestionType;
import gov.cdc.nedss.pagemanagement.wa.xml.util.UserInterfaceMetadataType;
import gov.cdc.nedss.systemservice.vo.ParticipationTypeVO;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;

/**
 * MarshallPageXML - Create XML for a DMB Page using the UI metadata
 *  passed in the PageManagementProxyVO. Also write out XML for Export.
 * @author Gregory Tucker
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: CSC</p>
 * MarshallPageXML.java
 * Feb 03, 2010
 * @version 0.9
 */

public class MarshallPageXML {

	static final LogUtils logger = new LogUtils(MarshallPageXML.class.getName());
	private static final String STRUCTURED_NUMERIC_STYLE_CLASS = "structuredNumericField";
	private static final String RELATED_UNITS_STYLE_CLASS = "relatedUnitsField";

	/**
	 * GeneratePageXMLFile from Page Management Proxy VO
	 * This method uses the xmlbeans jar generated from the schema.
	 * The schema jar dmbPageSchema.jar maps to pagemanagement.util.
	 * This method assumes the collection is in the logical order:
	 *   i.e. page - section - subsection - element.
	 * Note that setting an element to null will cause xmlbeans to
	 * generate the element with a null indicator: i.e.
	 * <Required xsi:nil="true" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"/>
	 * Thus, the checks for null before setting optional elements.
	 *
	 * @param PageManagementProxyVO containing page elements
	 * @param String file to make
	 * @return empty string if successful, otherwise error message suitable for display
	 *
	 */
	public String GeneratePageXMLFile(PageManagementProxyVO pmProxyVO, String fileToMake) throws NEDSSSystemException
    {
		
		//define the xmlbeans generated attributes..
		java.util.HashMap<String, PageElementType> quesMap = new java.util.HashMap<String, PageElementType>();
		//batchQUesMap has question Identifier and subsection Question Identifier
		java.util.HashMap<String, String> batchQuesMap = new java.util.HashMap<String, String>();
		//we'll build a list of the batch functions to call on Add button -- if there are any 
		//map(BatchSubsection question id, OnBatchAddFunction)
		java.util.HashMap<String, OnBatchAddFunctionType> batchQuesAddFunctionMap = new java.util.HashMap<String, OnBatchAddFunctionType>();

		PageInfoDocument  pageInfoDoc = null;
		PageInfoType pageInfo = null;
		TabType tab = null;
		SectionType section = null;
		SubSectionType subSection = null;
		PageElementType element = null;
		boolean inBatchSubsection = false;
		String currentSubsectionId = "";
		String dataType = null;
		String controlName = null;



		String returnTemplateType=""; //bus_obj_type and template type

		//util for mask
		MaskToJavaScriptFunction maskFunct = new MaskToJavaScriptFunction();

		//get the template for the page
		WaTemplateDT waTemp = pmProxyVO.getWaTemplateDT();
		
		//get any dynamic rules for the page
		Collection <Object> waRule = pmProxyVO.getWaRuleMetadataDTCollection();
		
		//get a blank new XML document
		pageInfoDoc = PageInfoDocument.Factory.newInstance();
		pageInfo = pageInfoDoc.addNewPageInfo();
		// add the header to the xml
		HeaderType header = pageInfo.addNewHeader();

		if (waTemp != null) {
			addHeaderToXMLQuestionLib(header, waTemp);
			PageTemplateType pgTemp = pageInfo.addNewTemplate();
			addTemplateToXML(pgTemp, waTemp);
			returnTemplateType = waTemp.getTemplateType();
		}
		// add the page components to the xml
		PageType page = pageInfo.addNewPage();
		Collection<Object> peVoColl = pmProxyVO.getThePageElementVOCollection();
		Iterator<Object> iter = peVoColl.iterator();
	try {
		while (iter.hasNext()) {
			Object nextItem = iter.next();
		    PageElementVO peVO = (PageElementVO) nextItem;
		    WaUiMetadataDT uiMeta = peVO.getWaUiMetadataDT();
		    
		    if (uiMeta == null) {
		   		logger.warn("Marshaller: Unexpected null UIMetadataDT in peVO? ");
	    		continue;
		    }
		    addUIMetadataToXML(pageInfo, uiMeta);

		    //waQues will be null for page, section, tab, subsection
		    WaQuestionDT waQues = peVO.getWaQuestionDT();
		    if (waQues != null)
		    	addQuestionToXMLQuestionLib(pageInfo, waQues);  //add each question to the question lib in case exported

		    // rdb and nnd may be null
			WaRdbMetadataDT waRDB = peVO.getWaRdbMetadataDT();
			if (waRDB != null)
				populateDataMartInfo(pageInfo, waRDB, uiMeta.getQuestionIdentifier());
			WaNndMetadataDT waNND = peVO.getWaNndMetadataDT();
			if (waNND != null)
				populateMessagingInfo(pageInfo, waNND, uiMeta.getQuestionIdentifier());

			//we will switch on the type of UI component
		    Long uiComponentUID= uiMeta.getNbsUiComponentUid();
		    if (uiComponentUID == null) {
		   		logger.warn("Marshaller: Unexpected null Ui Component in UIMetadataDT in peVO? ");
	    		continue;
		    }
		    //skip hidden NND Messaging fields
		    if(uiMeta.getStandardNndIndCd() == null || uiMeta.getStandardNndIndCd().equals("T"))
		    	continue;
		    ////////////////////////////////Process the UI Element////////////////////////////////
		    dataType = null;
		    dataType = uiMeta.getDataType();  //Coded, Text, Date, Numeric
		    element = null; //to be safe..
		    int uiComponentType = uiComponentUID.intValue();
		    controlName = lookupControlName(uiComponentType);
		    switch (uiComponentType) {
		    	case PageMetaConstants.aPAGE:
		    		logger.debug("page processing..");
		    		page.setPageName(uiMeta.getQuestionLabel());  //should be in the Template Page Nm
		    		page.setConditionCd(waTemp.getConditionCd());
		    		page.setFormCd(waTemp.getFormCd());
		    		page.setBusinessObject(waTemp.getBusObjType());
		    		if (waTemp.getDataMartNm() != null)
		    			page.setDataMart(waTemp.getDataMartNm());		    		
		    		tab = null;
		    		break;
		    	case PageMetaConstants.aTAB:
		    		logger.debug("tab processing..");
		    		tab = page.addNewPageTab();
		    		tab.setTabName(uiMeta.getQuestionLabel());
		    		tab.setTabDescription(uiMeta.getQuestionLabel() + " For "+waTemp.getMessageId());
		    		if (uiMeta.getDisplayInd() != null)
		    		tab.setVisible(uiMeta.getDisplayInd());
		    		section = null;
		    		subSection = null;
		    		break;
		    	case PageMetaConstants.aSECTION:
		    		logger.debug("section processing..");
		    		System.out.println("section processing..");
	    			section = tab.addNewSection();
	    			section.setSectionName(uiMeta.getQuestionLabel());
	    			if (uiMeta.getDisplayInd() != null)
	    				section.setVisible(uiMeta.getDisplayInd());
	    			subSection = null;
	    			break;
		    	case PageMetaConstants.aSUBSECTION:
		    		logger.debug("sub-section processing..");
		    		System.out.println("subSection processing..");
	    			subSection = section.addNewSubSection();
	    			subSection.setSubSectionName(uiMeta.getQuestionLabel());
	    			if(uiMeta.getEntryMethod()!=null)
	    				subSection.setEntryMethod(uiMeta.getEntryMethod());
	    			if (uiMeta.getDisplayInd() != null)
	    			subSection.setVisible(uiMeta.getDisplayInd());
	    			if (uiMeta.getQuestionIdentifier() != null) {
		    			subSection.setQuestionUniqueName(uiMeta.getQuestionIdentifier());
		    			currentSubsectionId = uiMeta.getQuestionIdentifier();
	    			}
	    			if(uiMeta.getQuestionGroupSeqNbr()!= null) {
	    				inBatchSubsection = true;
	    				subSection.setAllowBatchEntry("Y");
	    				//add an entry in the map for each subsection - will pass to rules for call on Add
	    				OnBatchAddFunctionType onBatchAddFunction = page.addNewOnBatchAddFunction();
	    				onBatchAddFunction.setBatchSubsectionIdentifier(uiMeta.getQuestionIdentifier());
	    				batchQuesAddFunctionMap.put(uiMeta.getQuestionIdentifier(), onBatchAddFunction);
	    			} 
	    			else {
	    				inBatchSubsection = false;
	    				subSection.setAllowBatchEntry("N");
	    			}
	    			
	    			if(uiMeta.getQuestionGroupSeqNbr()!= null && uiMeta.getEntryMethod()!=null && uiMeta.getEntryMethod().equals(NEDSSConstants.USER_VIEW)){
	    				inBatchSubsection = true;
	    				subSection.setAllowBatchEntry("E");
	    			}
	    			break;
		    	case PageMetaConstants.aSINGLESELECT:
		    	case PageMetaConstants.aSINGLESELECTWITHSEARCH:
		    	case PageMetaConstants.aSINGLESELECTREADONLYSAVE:
		    	case PageMetaConstants.aSINGLESELECTREADONLYNOSAVE:		    	
	    		case PageMetaConstants.aMULTISELECT:
	    		case PageMetaConstants.aMULTISELECTREADONLYSAVE:
	    		case PageMetaConstants.aMULTISELECTREADONLYNOSAVE:	    			
	    		case PageMetaConstants.aCHECKBOX:
	    		case PageMetaConstants.aRADIO:
	    		case PageMetaConstants.aLOGICFLAG:
	    			if (dataType != null && !dataType.equalsIgnoreCase("Coded")) {
	    				logger.error("Marshaller: Element processing - Expected coded type??");
	    				continue;
	    			}
		    		logger.debug("element processing");
		    		element = subSection.addNewPageElement();
		    		if(uiMeta.getSubGroupNm()!=null && uiMeta.getSubGroupNm().equals(NEDSSConstants.HIV_SUB_GROUP)){
		    			element.setIsHIVElement("Y");
		    			subSection.setHasHIVQuestions("Y");
		    		}
		    		CodedQuestionType codedQues = element.addNewCodedQuestion();
		    		codedQues.setDisplayLabel(uiMeta.getQuestionLabel());
		    		if (uiMeta.getQuestionIdentifier() != null) {
		    			quesMap.put(uiMeta.getQuestionIdentifier(), element);
		    			codedQues.setQuestionUniqueName(uiMeta.getQuestionIdentifier());
		    			if (inBatchSubsection)
		    				batchQuesMap.put(uiMeta.getQuestionIdentifier(), currentSubsectionId);
		    		}
		    		codedQues.setControlType(controlName);
		    		if (uiMeta.getRecordStatusCd() != null)
		    			element.setStatusCd(uiMeta.getRecordStatusCd());
		    		if(uiMeta.getCodeSetName()!= null)
		    			codedQues.setValueSetName(uiMeta.getCodeSetName());
		    		if (uiMeta.getQuestionToolTip() != null)
		    			codedQues.setToolTip(uiMeta.getQuestionToolTip());
		    		if (uiMeta.getRequiredInd()!= null)
		    			codedQues.setRequired(uiMeta.getRequiredInd());
		    		if (uiMeta.getDisplayInd() != null)
		    			codedQues.setVisible(uiMeta.getDisplayInd());
		    		if (uiMeta.getEnableInd() != null)
		    			codedQues.setDisabled(getDisabledStr(uiMeta.getEnableInd()));
		    		if (uiMeta.getOtherValIndCd()!= null) {
		    			if (uiMeta.getOtherValIndCd().equals("T")) {
		    				codedQues.setOtherEntryAllowed(uiMeta.getOtherValIndCd());
		    				String onChangeStr = String.format("enableOrDisableOther('%s')", uiMeta.getQuestionIdentifier());
		    				codedQues.setOnChange(onChangeStr);
		    			}
		    		}
		    		if(uiMeta.getEntryMethod()!=null)
		    			codedQues.setEntryMethod(uiMeta.getEntryMethod());
		    		break;
	    		case PageMetaConstants.aINPUT:
	    		case PageMetaConstants.aINPUTREADONLYSAVE:
	    		case PageMetaConstants.aINPUTREADONLYNOSAVE:
	    			if (dataType != null && (dataType.equalsIgnoreCase("Date") || dataType.equalsIgnoreCase("DateTime"))) {
	    				element = subSection.addNewPageElement();
	    				if(uiMeta.getSubGroupNm()!=null && uiMeta.getSubGroupNm().equals(NEDSSConstants.HIV_SUB_GROUP)){
			    			element.setIsHIVElement("Y");
			    			subSection.setHasHIVQuestions("Y");
			    		}
	    				DateQuestionType dateQues = element.addNewDateQuestion();
	    	    		dateQues.setDisplayLabel(uiMeta.getQuestionLabel());
	    	    		dateQues.setControlType(controlName);
	    	    		if(uiMeta.getEntryMethod()!=null)
	    	    			dateQues.setEntryMethod(uiMeta.getEntryMethod());
			    		if (uiMeta.getDisplayInd() != null)
			    			dateQues.setVisible(uiMeta.getDisplayInd());
			    		if (uiMeta.getEnableInd() != null)
			    			dateQues.setDisabled(getDisabledStr(uiMeta.getEnableInd()));
			    		if (uiMeta.getRecordStatusCd() != null)
			    			element.setStatusCd(uiMeta.getRecordStatusCd());
	    	    		if (uiMeta.getQuestionToolTip() != null)
	    	    			dateQues.setToolTip(uiMeta.getQuestionToolTip());
			    		if (uiMeta.getQuestionIdentifier() != null) {
			    			quesMap.put(uiMeta.getQuestionIdentifier(), element);
			    			dateQues.setQuestionUniqueName(uiMeta.getQuestionIdentifier());
			    			if (inBatchSubsection)
			    				batchQuesMap.put(uiMeta.getQuestionIdentifier(), currentSubsectionId);
			    		}
			    		dateQues.setOnKeyUp("DateMask(this,null,event)");
	    				if(uiMeta.getFutureDateIndCd()!= null) {
	    				  dateQues.setAllowFutureDate(uiMeta.getFutureDateIndCd());
	    				  if (uiMeta.getFutureDateIndCd().equalsIgnoreCase("T"))
	    					  dateQues.setOnKeyUp("DateMaskFuture(this,null,event)");
	    				}
	    				if(uiMeta.getRequiredInd()!= null)
	    				  dateQues.setRequired(uiMeta.getRequiredInd());

	    			} else if (dataType != null && dataType.equalsIgnoreCase("Text")) {
	    			    element = subSection.addNewPageElement();
	    			    if(uiMeta.getSubGroupNm()!=null && uiMeta.getSubGroupNm().equals(NEDSSConstants.HIV_SUB_GROUP)){
			    			element.setIsHIVElement("Y");
			    			subSection.setHasHIVQuestions("Y");
			    		}
	    				TextQuestionType textQues = element.addNewTextQuestion();
	    	    		textQues.setDisplayLabel(uiMeta.getQuestionLabel());
	    	    		if(uiMeta.getEntryMethod()!=null)
	    	    			textQues.setEntryMethod(uiMeta.getEntryMethod());
			    		if (uiMeta.getRecordStatusCd() != null)
			    			element.setStatusCd(uiMeta.getRecordStatusCd());
	    	    		if (uiMeta.getQuestionToolTip() != null)
	    	    			textQues.setToolTip(uiMeta.getQuestionToolTip());
			    		textQues.setControlType(controlName);
			    		if(uiMeta.getRequiredInd() != null)
			    			textQues.setRequired(uiMeta.getRequiredInd());
			    		if (uiMeta.getDisplayInd() != null)
			    			textQues.setVisible(uiMeta.getDisplayInd());
			    		if (uiMeta.getEnableInd() != null)
			    			textQues.setDisabled(getDisabledStr(uiMeta.getEnableInd()));
			    		//field length currently set and max length is null
			    		if (uiMeta.getFieldLength() != null)
			    			textQues.setDisplayLength(uiMeta.getFieldLength());
			    		else if (uiMeta.getMaxLength() != null)
			    			textQues.setDisplayLength(uiMeta.getMaxLength().toString());
			    		else textQues.setDisplayLength(PageMetaConstants.TEXTBOX_DEFAULT_SIZE);
			    		if (uiMeta.getMaxLength()!=null)
			    			textQues.setMaxLength(uiMeta.getMaxLength().toString());
			    		else
			    			textQues.setMaxLength(textQues.getDisplayLength());

			    		if (uiMeta.getQuestionIdentifier() != null) {
			    			quesMap.put(uiMeta.getQuestionIdentifier(), element);
			    			textQues.setQuestionUniqueName(uiMeta.getQuestionIdentifier());
			    			if (inBatchSubsection)
			    				batchQuesMap.put(uiMeta.getQuestionIdentifier(), currentSubsectionId);
			    		}
			    		//the min/max check for text is for MMWR which is text
	    				if (uiMeta.getMinValue() != null) {
	    					String onBlurStr = "pgCheckFieldMinMax(this," + uiMeta.getMinValue().toString() +","
	    					     +uiMeta.getMaxValue().toString() +")";
	    					if (uiMeta.getMinValue() == 0 && uiMeta.getMaxValue() == 0)
	    						logger.warn("Marshaller: Min and Max values are both zero for Question " + uiMeta.getQuestionIdentifier());
	    					else
	    						textQues.setOnBlur(onBlurStr);
	    				}
	    				if (uiMeta.getMask() != null && !uiMeta.getMask().isEmpty()) {
	    					String maskStr = maskFunct.getJavaScriptForOnKeyUpMask(uiMeta.getMask(), textQues.getOnKeyUp());
	    					if (!maskStr.isEmpty())
	    						textQues.setOnKeyUp(maskStr);
	    					//see if onblur JavaScript function to call
	    					maskStr = maskFunct.getJavaScriptForOnBlurMask(uiMeta.getMask(), textQues.getOnBlur());
	    				
	    					if (!maskStr.isEmpty())
	    						textQues.setOnBlur(maskStr);
	    					maskStr = maskFunct.getStyleClassForMask(uiMeta.getMask());
	    					if (!maskStr.isEmpty())
	    						textQues.setFieldStyleClass(maskStr);
	    				}
	    			} else if (dataType != null && dataType.equalsIgnoreCase("Numeric")) {
	    				element = subSection.addNewPageElement();
	    				if(uiMeta.getSubGroupNm()!=null && uiMeta.getSubGroupNm().equals(NEDSSConstants.HIV_SUB_GROUP)){
			    			element.setIsHIVElement("Y");
			    			subSection.setHasHIVQuestions("Y");
			    		}
	    				NumericQuestionType numerQues = element.addNewNumericQuestion();
	    				numerQues.setDisplayLabel(uiMeta.getQuestionLabel());
	    				if(uiMeta.getEntryMethod()!=null)
	    					numerQues.setEntryMethod(uiMeta.getEntryMethod());
	    				if (uiMeta.getQuestionIdentifier() != null) {
	    					quesMap.put(uiMeta.getQuestionIdentifier(), element);
	    					numerQues.setQuestionUniqueName(uiMeta.getQuestionIdentifier());
	    					if (inBatchSubsection)
			    				batchQuesMap.put(uiMeta.getQuestionIdentifier(), currentSubsectionId);
	    				}
			    		if (uiMeta.getRecordStatusCd() != null)
			    			element.setStatusCd(uiMeta.getRecordStatusCd());
	    				if (uiMeta.getQuestionToolTip() != null)
	    					numerQues.setToolTip(uiMeta.getQuestionToolTip());
	    				numerQues.setControlType(controlName);
	    				if (uiMeta.getMask() != null)
	    					numerQues.setDisplayMask(uiMeta.getMask());
	    				if (uiMeta.getRequiredInd() != null)
	    					numerQues.setRequired(uiMeta.getRequiredInd());
			    		if (uiMeta.getDisplayInd() != null)
			    			numerQues.setVisible(uiMeta.getDisplayInd());
			    		if (uiMeta.getEnableInd() != null)
			    			numerQues.setDisabled(getDisabledStr(uiMeta.getEnableInd()));
			    		//fieldLength is usually present and maxLength is null
	    				if (uiMeta.getFieldLength()!= null)
	    					numerQues.setDisplayLength(uiMeta.getFieldLength());
	    				else if (numerQues.getMaxLength() != null)
	    					numerQues.setDisplayLength(uiMeta.getMaxLength().toString());
	    				else numerQues.setDisplayLength(PageMetaConstants.NUMERICAL_DEFAULT_MAX_LENGTH);
	    				if(uiMeta.getMaxLength()!= null)
	    					numerQues.setMaxLength(uiMeta.getMaxLength().toString());
	    				else
	    					numerQues.setMaxLength(numerQues.getDisplayLength());

	    				if (uiMeta.getMinValue() != null) {
	    					String onBlurStr = "pgCheckFieldMinMax(this," + uiMeta.getMinValue().toString() +","
	    					     +uiMeta.getMaxValue().toString() +")";
	    					if (uiMeta.getMinValue() == 0 && uiMeta.getMaxValue() == 0)
	    						logger.warn("Marshaller: Min and Max values are both zero for Question " + uiMeta.getQuestionIdentifier());
	    					else
	    						numerQues.setOnBlur(onBlurStr);
	    				}
	    					
	    				//get the related units codeset name
	    				if (uiMeta.getUnitValueCodeSetNm() != null) {
	    					numerQues.setRelatedUnitsValueSetName(uiMeta.getUnitValueCodeSetNm());
	    					//for styleClass for final validation check
	    					String relatedUnitsClassName = RELATED_UNITS_STYLE_CLASS;
	    					if (inBatchSubsection)
	    						relatedUnitsClassName= relatedUnitsClassName.concat(currentSubsectionId);
	    					if (numerQues.getFieldStyleClass() != null)
	    						numerQues.setFieldStyleClass(numerQues.getFieldStyleClass() + " " + relatedUnitsClassName);
	    					else 
	    						numerQues.setFieldStyleClass(relatedUnitsClassName); 
	    				}
	    				if (uiMeta.getMask() != null && !uiMeta.getMask().isEmpty()) {
	    					//add styleClass if Structured Numeric mask
	    					if (uiMeta.getMask().equalsIgnoreCase(MaskToJavaScriptFunction.STRUCTURED_NUMERIC_MASK_CODE))
	    					{
	    						if (inBatchSubsection)
		    						numerQues.setFieldStyleClass(STRUCTURED_NUMERIC_STYLE_CLASS  + currentSubsectionId); //for styleClass for final validation check
	    						else
	    							numerQues.setFieldStyleClass(STRUCTURED_NUMERIC_STYLE_CLASS);
	    					}			
	    					//see if onkeyup JavaScript function to call
	    					String maskStr = maskFunct.getJavaScriptForOnKeyUpMask(uiMeta.getMask(), numerQues.getOnKeyUp());
	    					if (!maskStr.isEmpty())
	    						numerQues.setOnKeyUp(maskStr);
	    					//see if onblur JavaScript function to call
	    					maskStr = maskFunct.getJavaScriptForOnBlurMask(uiMeta.getMask(), numerQues.getOnBlur());
	    					if (!maskStr.isEmpty())
	    						numerQues.setOnBlur(maskStr);
	    					maskStr = maskFunct.getStyleClassForMask(uiMeta.getMask());
	    					if (!maskStr.isEmpty())
	    						numerQues.setFieldStyleClass(maskStr);
	    				} else numerQues.setOnKeyUp(MaskToJavaScriptFunction.isNumericCharacterEnteredJS); //default validation
	    			}
	    			break;
	    		case PageMetaConstants.aTEXTAREA:
	    		case PageMetaConstants.aROLLINGNOTE:
	    			element = subSection.addNewPageElement();
	    			if(uiMeta.getSubGroupNm()!=null && uiMeta.getSubGroupNm().equals(NEDSSConstants.HIV_SUB_GROUP)){
		    			element.setIsHIVElement("Y");
		    			subSection.setHasHIVQuestions("Y");
		    		}
	    			TextAreaType textArea = element.addNewTextArea();
    	    		textArea.setDisplayLabel(uiMeta.getQuestionLabel());
		    		textArea.setControlType(controlName);
		    		if (uiMeta.getQuestionIdentifier() != null) {
		    			textArea.setQuestionUniqueName(uiMeta.getQuestionIdentifier());
		    			quesMap.put(uiMeta.getQuestionIdentifier(), element);
		    			if (inBatchSubsection)
		    				batchQuesMap.put(uiMeta.getQuestionIdentifier(), currentSubsectionId);
		    		}
		    		textArea.setControlType(controlName);
		    		if(uiMeta.getEntryMethod()!=null)
		    			textArea.setEntryMethod(uiMeta.getEntryMethod());
		    		//make sure we don't overrun 2000
		    		//note that textarea doesn't have a fieldlength or maxlength property 
		    			//rolling note appends the user and date so it can't be 2000
	    			if (uiComponentType == PageMetaConstants.aROLLINGNOTE)
	    				uiMeta.setFieldLength(PageMetaConstants.ROLLING_NOTE_MAX_LENGTH);
		    		if (uiMeta.getFieldLength() != null) {
		    			if (uiComponentType == PageMetaConstants.aROLLINGNOTE)
		    				uiMeta.setFieldLength(PageMetaConstants.ROLLING_NOTE_MAX_LENGTH);
		    			textArea.setOnKeyUp("checkTextAreaLength(this, " +uiMeta.getFieldLength() +")");
		    			textArea.setMaxLength(uiMeta.getFieldLength());
		    		} else textArea.setOnKeyUp("checkMaxLength(this)");

		    		if (uiMeta.getRecordStatusCd() != null)
		    			element.setStatusCd(uiMeta.getRecordStatusCd());
	  	    		if (uiMeta.getQuestionToolTip() != null)
	  	    			textArea.setToolTip(uiMeta.getQuestionToolTip());
		    		if (uiMeta.getRequiredInd() != null)
		    			textArea.setRequired(uiMeta.getRequiredInd());
		    		if (uiMeta.getDisplayInd() != null)
		    			textArea.setVisible(uiMeta.getDisplayInd());
		    		if (uiMeta.getEnableInd() != null)
		    			textArea.setDisabled(getDisabledStr(uiMeta.getEnableInd()));
		    		//Note: width and height are defaulted in the xsl - not currently in the data
		    		
		    		// for rolling notes - Add a Date and User element to the metadata
		    		if (inBatchSubsection && controlName.equals(PageMetaConstants.ROLLINGNOTE_CONTROL)) {
		    			//add the function to fill in the User and Date when the note is added or updated
		    			textArea.setOnChange("rollingNoteSetUserDate('" + uiMeta.getQuestionIdentifier() + "')");
		    			//if we have a rolling note in a batch entry, we are automatically adding a date and user field
		    			//add the date field
	    				element = subSection.addNewPageElement();
	    				DateQuestionType dateQues = element.addNewDateQuestion();
	    	    		dateQues.setDisplayLabel(PageMetaConstants.RollingNoteDateDisplayLabel);
	    	    		dateQues.setControlType(PageMetaConstants.TEXTBOX_CONTROL);
			    		dateQues.setVisible("F");
			    		dateQues.setDisabled("enabled");
			    		element.setStatusCd(NEDSSConstants.RECORD_STATUS_Active);
	    	    		dateQues.setToolTip(PageMetaConstants.RollingNoteDateDisplayTip);
			    		//quesMap.put(uiMeta.getQuestionIdentifier(), element);
			    		dateQues.setQuestionUniqueName(uiMeta.getQuestionIdentifier()+"Date");
			    		//batchQuesMap.put(uiMeta.getQuestionIdentifier(), currentSubsectionId);
			    		dateQues.setOnKeyUp("DateMask(this,null,event)");
	    				dateQues.setAllowFutureDate("F");
	    				dateQues.setRequired(uiMeta.getRequiredInd());
	    				//add a user field
		    			element = subSection.addNewPageElement();
		    			TextQuestionType textQues = element.addNewTextQuestion();
		    	    	textQues.setDisplayLabel(PageMetaConstants.RollingNoteUserDisplayLabel);
				    	element.setStatusCd(NEDSSConstants.RECORD_STATUS_Active);
		    	    	textQues.setToolTip(PageMetaConstants.RollingNoteUserDisplayTip);
		    	    	textQues.setControlType(PageMetaConstants.TEXTBOX_CONTROL);
		    	    	textQues.setRequired("F");
				    	textQues.setVisible("F");
				    	textQues.setDisabled("enabled");
				    	textQues.setDisplayLength("30");
				    	textQues.setMaxLength("30");
				    	textQues.setQuestionUniqueName(uiMeta.getQuestionIdentifier() + "User");
				    	//batchQuesMap.put(uiMeta.getQuestionIdentifier(), currentSubsectionId);
		    		}
	    			break;
	    		case PageMetaConstants.aPARTICIPATION:
	    			element = subSection.addNewPageElement();
	    			if(uiMeta.getSubGroupNm()!=null && uiMeta.getSubGroupNm().equals(NEDSSConstants.HIV_SUB_GROUP)){
		    			element.setIsHIVElement("Y");
		    			subSection.setHasHIVQuestions("Y");
		    		}
	    			ParticipationQuestionType partQues = element.addNewParticipationQuestion();
    	    		partQues.setDisplayLabel(uiMeta.getQuestionLabel());
		    		partQues.setControlType(controlName);
		    		if(uiMeta.getEntryMethod()!=null)
		    			partQues.setEntryMethod(uiMeta.getEntryMethod());
		    		if (uiMeta.getQuestionIdentifier() != null) {
		    			partQues.setQuestionUniqueName(uiMeta.getQuestionIdentifier());
		    			quesMap.put(uiMeta.getQuestionIdentifier(), element);
		    		}

		    		//participation type has to be there for the xsl to work..
		    		//need to rework this to get from the cache
		    		partQues.setParticipationType(findParType(uiMeta.getPartTypeCd(),uiMeta.getQuestionIdentifier()));

		    		if (uiMeta.getRecordStatusCd() != null)
		    			element.setStatusCd(uiMeta.getRecordStatusCd());
    	    		if (uiMeta.getQuestionToolTip() != null)
    	    			partQues.setToolTip(uiMeta.getQuestionToolTip());
		    		if (uiMeta.getRequiredInd() != null)
		    			partQues.setRequired(uiMeta.getRequiredInd());
		    		if (uiMeta.getDisplayInd() != null)
		    			partQues.setVisible(uiMeta.getDisplayInd());
		    		if (uiMeta.getEnableInd() != null)
		    			partQues.setDisabled(getDisabledStr(uiMeta.getEnableInd()));

		    		break;

		    	//static element types...
	    		case PageMetaConstants.aHYPERLINK:
	    			element = subSection.addNewPageElement();
	    			if(uiMeta.getSubGroupNm()!=null && uiMeta.getSubGroupNm().equals(NEDSSConstants.HIV_SUB_GROUP)){
		    			element.setIsHIVElement("Y");
		    			subSection.setHasHIVQuestions("Y");
		    		}
	    			StaticLinkType staticLink = element.addNewStaticLink(); //1003
	    			staticLink.setDisplayLabel(uiMeta.getQuestionLabel());
	    			staticLink.setLinkValue(uiMeta.getDefaultValue());
	    			staticLink.setQuestionUniqueName(uiMeta.getQuestionIdentifier());
		    		break;

	    		case PageMetaConstants.aREADONLY:
	    			element = subSection.addNewPageElement();
	    			if(uiMeta.getSubGroupNm()!=null && uiMeta.getSubGroupNm().equals(NEDSSConstants.HIV_SUB_GROUP)){
		    			element.setIsHIVElement("Y");
		    			subSection.setHasHIVQuestions("Y");
		    		}
	    			StaticCommentType staticComment = element.addNewStaticComment(); //1014
	    			staticComment.setCommentText(uiMeta.getQuestionLabel());
	    			staticComment.setQuestionUniqueName(uiMeta.getQuestionIdentifier());
		    		break;

	    		case PageMetaConstants.aLINESEPARATOR:
	    			element = subSection.addNewPageElement();
	    			StaticInfoBarType horizRule = element.addNewStaticInfoBar();  //1012
	    			if (uiMeta.getAdminComment() != null)
	    				horizRule.setInfoBarDescription(uiMeta.getAdminComment());
	    			horizRule.setQuestionUniqueName(uiMeta.getQuestionIdentifier());
		    		break;
	    		case PageMetaConstants.aPATIENTSEARCH:
	    			element = subSection.addNewPageElement();
	    			PatientSearchType patientSearch = element.addNewPatientSearch();  //1032
	    			if (uiMeta.getAdminComment() != null)
	    				patientSearch.setToolTip(uiMeta.getAdminComment());
	    			patientSearch.setQuestionUniqueName(uiMeta.getQuestionIdentifier());
		    		break;
	    		case PageMetaConstants.aACTIONBUTTON:
	    			element = subSection.addNewPageElement();
	    			ActionButtonType actionButton = element.addNewActionButton();  //1033
	    			if (uiMeta.getAdminComment() != null)
	    				actionButton.setToolTip(uiMeta.getAdminComment());
	    			if (uiMeta.getQuestionLabel() != null)
	    				actionButton.setDisplayLabel(uiMeta.getQuestionLabel());
	    			if (uiMeta.getQuestionIdentifier() != null) 
		    			actionButton.setQuestionUniqueName(uiMeta.getQuestionIdentifier());
	    			if (uiMeta.getDefaultValue() != null) 
		    			actionButton.setQuestionDefaultValue(uiMeta.getDefaultValue());
	    			actionButton.setQuestionUniqueName(uiMeta.getQuestionIdentifier());
		    		break;
	    		case PageMetaConstants.aSETVALUEBUTTON:
	    			element = subSection.addNewPageElement();
	    			SetValuesButtonType setValuesButton = element.addNewSetValuesButton();  //1034
	    			if (uiMeta.getAdminComment() != null)
	    				setValuesButton.setToolTip(uiMeta.getAdminComment());
	    			if (uiMeta.getQuestionLabel() != null)
	    				setValuesButton.setDisplayLabel(uiMeta.getQuestionLabel());
		    		if (uiMeta.getQuestionIdentifier() != null) 
		    			setValuesButton.setQuestionUniqueName(uiMeta.getQuestionIdentifier());
		    		setValuesButton.setQuestionUniqueName(uiMeta.getQuestionIdentifier());
		    		
		    		String repeatingOrDiscrete = "D";//D = Discrete, R = Repeating
		    		if(uiMeta.getBlockName()!=null && !uiMeta.getBlockName().isEmpty())
		    			repeatingOrDiscrete = "R";
		    		
		    		
		    		if (uiMeta.getDefaultValue() != null) //R==DefaultValues... or D = DefaultValues...
		    			setValuesButton.setDefaultValue(repeatingOrDiscrete+"=="+uiMeta.getDefaultValue());

		    		break;
	    		case PageMetaConstants.aPARTICIPANTLIST:
	    			element = subSection.addNewPageElement();
	    			StaticParticipantListType participantList = element.addNewStaticParticipantList();  //1030
	    			
	    			if (uiMeta.getAdminComment() != null)
	    				participantList.setParticipantListDescription(uiMeta.getAdminComment());
	    			participantList.setQuestionUniqueName(uiMeta.getQuestionIdentifier());
		    		break;
	    		case PageMetaConstants.aORIGDOCLIST:
	    			element = subSection.addNewPageElement();
	    			OrigDocListType origDocList = element.addNewOrigDocList();  //1036
	    			
	    			if (uiMeta.getAdminComment() != null)
	    				origDocList.setOrigDocDescription(uiMeta.getAdminComment());
	    			origDocList.setQuestionUniqueName(uiMeta.getQuestionIdentifier());
		    		break;

		    	default:
		    		logger.warn("Unhandled component type " + uiComponentUID + " in peVO collection??? " );
		    		System.out.println("Unhandled component type >> " + uiComponentUID + "<< in peVO collection" );
		    		break;
		       } //case
		} //has next
	} catch (Exception e) {
		if (dataType == null) dataType = "";
		logger.error("Marshaller error generating XML last element was <" +controlName + "> data type "  +dataType + " Msg:" +e.getMessage());
		e.printStackTrace();
		throw new NEDSSSystemException("Marshaller error generating XML on element " +controlName + " " +dataType + " " +e.toString());
	}
		//set any last minute dependencies  -->   probably be passed/set from Builder rule processor
		PageElementDependentJavaScript depJS = new PageElementDependentJavaScript();
		try {
			depJS.setPageElementDependentJavascriptFunctions(quesMap);
		} catch (Exception e) {
			logger.error("Marshaller error adding JavaScript functions to XML " +e.getMessage());
			throw new NEDSSSystemException("Marshaller error adding JavaScript functions to XML " + e.toString());
		}
		//check rule collection and process any rules added in Page Builder
		if (waRule != null) {
			MarshallPageRules ruleMarshaller = new MarshallPageRules();
			try {
				ruleMarshaller.addRulesToXML(waRule, quesMap, batchQuesMap, batchQuesAddFunctionMap, pageInfo);
				
			} catch (Exception e) {
				logger.error("Marshaller error adding rules to XML " +e.getMessage());
				throw new NEDSSSystemException("Marshaller error adding rules to XML " + e.toString());
			}
		}
		XmlOptions opts = new XmlOptions();
		if (!fileToMake.isEmpty()) {
			File outFile = null;
			outFile = new File(fileToMake);
    	
			opts.setSavePrettyPrint();
			opts.setSavePrettyPrintIndent(4);
			try {
				pageInfoDoc.save(outFile, opts);
			} catch (IOException e) {
				logger.error("Marshaller error writing XML to <" +fileToMake + "> output file? " +e.getMessage());
				e.printStackTrace();
				throw new NEDSSSystemException("Marshaller error writing to output file " + e.toString());
			}
		}
		//if no file to make, set the XML into the wa_template->XmlPayload
		//Using document builder or Oracle takes away line returns
		if (fileToMake.isEmpty()) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			try {
				pageInfoDoc.save(baos, opts);
			} catch (IOException e) {
				logger.error("Marshaller error writing XML to output stream? " +e.getMessage());
				e.printStackTrace();
				throw new NEDSSSystemException("Marshaller error writing to output stream " + e.toString());
			}
			waTemp.setXmlPayload(baos.toString());
	
		} //fileToMake.isEmpty()
		return returnTemplateType;	//return the template type i.e. TEMPLATE or Published or Draft
	}//GeneratePageXMLFile

	/**
	 * addHeaderToXMLQuestionLib
	 *
	 *
	 * @param HeaderType - header ComplexType
	 * @param WaUiMetadataDT - UI Metadata has some fields we need
	 *
	 */
	private void addHeaderToXMLQuestionLib(HeaderType header, WaTemplateDT waTemp)
	{
	if (waTemp.getTemplateNm() != null)
		header.setPageTemplateUsed(waTemp.getTemplateNm());
	else header.setPageTemplateUsed("Template Name is Null");
	header.setStatus("Published");
	if (waTemp.getPublishVersionNbr() != null)
		header.setVersionID(waTemp.getPublishVersionNbr().toString());
	}


	/**
	 * addTemplateToXML
	 * Add the data from the Template used for the Page
	 * @param PageTemplateType (see schema)
	 * @param WaTemplateDT - template dt
	 *
	 */
	private void addTemplateToXML(PageTemplateType pgTemp, WaTemplateDT waTemp)
	{
		if (waTemp.getBusObjType() != null)
			pgTemp.setTemplateBusinessObjectType(waTemp.getBusObjType());
		if (waTemp.getConditionCd() != null)
			pgTemp.setTemplateConditionCd(waTemp.getConditionCd());
		if (waTemp.getConditionCdDesc() != null)
		    pgTemp.setTemplateConditionCdDesc(waTemp.getConditionCdDesc());
		if (waTemp.getDataMartNm() != null)
			pgTemp.setTemplateDataMartNm(waTemp.getDataMartNm());
		if (waTemp.getDescTxt() != null)
			pgTemp.setTemplateDescriptiveTxt(waTemp.getDescTxt());
		if (waTemp.getEditLink() != null)
			pgTemp.setTemplateDefaultEditLink(waTemp.getEditLink());
		if (waTemp.getViewLink() != null)
			pgTemp.setTemplateDefaultViewLink(waTemp.getViewLink());
		if (waTemp.getFormCd() != null)
			pgTemp.setTemplateFormCd(waTemp.getFormCd());
		if (waTemp.getMessageId() != null)
			pgTemp.setTemplateMessageProfileId(waTemp.getMessageId());
		if (waTemp.getTemplateNm() != null)
			pgTemp.setTemplateNm(waTemp.getTemplateNm());
		if (waTemp.getPageNm() != null)
			pgTemp.setTemplatePageName(waTemp.getPageNm());
		if (waTemp.getTemplateType() != null)
			pgTemp.setTemplateType(waTemp.getTemplateType());
		if (waTemp.getVersion() != null)
			pgTemp.setTemplateVersion(waTemp.getVersion());
		if (waTemp.getPublishVersionNbr() != null)
			pgTemp.setTemplatePublishVersionNbr(waTemp.getPublishVersionNbr().toString());
	}




	/**
	 * addQuestionToXMLQuestionLib
	 * Add each question used in the page to the Question Library in the XML in case of Export.
	 * Some of the question information is present in the UI elements i.e. Textbox label.
	 * For ease of export/import, the Question data is repeated in the XML element questionLibEntry.
	 * All fields are exported that are not 'local'fields (i.e. uid).
	 * Note that many of the 'fields' may not be populated.
	 *
	 * Also note that if a field is null, don't add null to the XML. (It causes problems.)
	 *
	 * @param PageInfo - root page element
	 * @param WaQuestionDT - question dt
	 *
	 */
	private void addQuestionToXMLQuestionLib(PageInfoType pageInfo, WaQuestionDT waQues)
	{

		//Question Identifier must be present
		//if (waQues.getQuestionIdentifier() == null)
		//	return;
		//Add a new question lib entry to the XML
		QuestionLibraryType newQues = pageInfo.addNewQuestionLibEntry();
		if (waQues.getQuestionIdentifier() != null)
			newQues.setQuestionIdentifier(waQues.getQuestionIdentifier());
		if (waQues.getAdminComment() != null)
			newQues.setQuestionAdminComment(waQues.getAdminComment());
		if (waQues.getDataCd()!= null)
			newQues.setQuestionDataCd(waQues.getDataCd());
		if (waQues.getDataLocation()!= null)
			newQues.setQuestionDataLocation(waQues.getDataLocation());
		if (waQues.getRdbcolumnNm()!= null)
			newQues.setQuestionRDBColumnName(waQues.getRdbcolumnNm());
		if (waQues.getDataType()!= null)
			newQues.setQuestionDataType(waQues.getDataType());
		if (waQues.getDataTypeDesc()!= null)
			newQues.setQuestionDataTypeDesc(waQues.getDataTypeDesc());
		if (waQues.getQuestionDataTypeNnd()!= null)
			newQues.setQuestionDataTypeNND(waQues.getQuestionDataTypeNnd());
		if (waQues.getQuestionDataTypeNndDesc()!= null)
			newQues.setQuestionDataTypeNNDDesc(waQues.getQuestionDataTypeNndDesc());
		if (waQues.getDataUseCd()!= null)
			newQues.setQuestionDataUseCd(waQues.getDataUseCd());
		if (waQues.getEditLink()!= null)
			newQues.setQuestionDefaultEditLink(waQues.getEditLink());
		if (waQues.getViewLink()!= null)
			newQues.setQuestionDefaultViewLink(waQues.getViewLink());
		if (waQues.getDefaultValue()!= null)
			newQues.setQuestionDefaultValue(waQues.getDefaultValue());
		if (waQues.getDescription()!= null)
			newQues.setQuestionDesc(waQues.getDescription());
		if (waQues.getEntryMethod()!= null)
			newQues.setQuestionEntryMethod(waQues.getEntryMethod());
		if (waQues.getFieldLength()!= null)
			newQues.setQuestionFieldLength(waQues.getFieldLength().toString());
		if (waQues.getFutureDateIndCd()!= null)
			newQues.setQuestionFutureDateInd(waQues.getFutureDateIndCd());
		if (waQues.getGroupDesc()!= null)
			newQues.setQuestionGroupDesc(waQues.getGroupDesc());
		if (waQues.getGroupNm()!= null)
			newQues.setQuestionGroupNm(waQues.getGroupNm());
		if (waQues.getQuestionGroupSeqNbr()!= null)
			newQues.setQuestionGroupSeqNbr(waQues.getQuestionGroupSeqNbr());
		if (waQues.getHl7Segment()!= null)
			newQues.setQuestionHL7Segment(waQues.getHl7Segment());
		if (waQues.getHl7SegmentDesc()!= null)
			newQues.setQuestionHL7SegmentDesc(waQues.getHl7SegmentDesc());
		if (waQues.getQuestionIdentifierNnd()!= null)
			newQues.setQuestionIdentifierNND(waQues.getQuestionIdentifierNnd());
		if (waQues.getQuestionLabel()!= null)
			newQues.setQuestionLabel(waQues.getQuestionLabel());
		if (waQues.getQuestionLabelNnd()!= null)
			newQues.setQuestionLabelNND(waQues.getQuestionLabelNnd());
		if (waQues.getLegacyDataLocation()!= null)
			newQues.setQuestionLegacyDataLocation(waQues.getLegacyDataLocation());
		if (waQues.getLocalId()!= null)
			newQues.setQuestionLocalId(waQues.getLocalId());
		if (waQues.getMask()!= null)
			newQues.setQuestionMask(waQues.getMask());
		if (waQues.getMaskDesc()!= null)
			newQues.setQuestionMaskDesc(waQues.getMaskDesc());
		if (waQues.getMaxValue()!= null)
			newQues.setQuestionMaxValue(waQues.getMaxValue().toString());
		if (waQues.getMinValue()!= null)
			newQues.setQuestionMinValue(waQues.getMinValue().toString());
		if (waQues.getQuestionNm()!= null)
			newQues.setQuestionNm(waQues.getQuestionNm());
		if (waQues.getNndMsgInd()!= null)
			newQues.setQuestionNNDMsgInd(waQues.getNndMsgInd());
		if (waQues.getNndMsgIndDesc()!= null)
			newQues.setQuestionNNDMsgIndDesc(waQues.getNndMsgIndDesc());
		if (waQues.getQuestionOid()!= null)
			newQues.setQuestionOID(waQues.getQuestionOid());
		if (waQues.getQuestionOidSystemTxt()!= null)
			newQues.setQuestionOIDSystemText(waQues.getQuestionOidSystemTxt());
		if (waQues.getOrderGrpId()!= null)
			newQues.setQuestionOrderGroupId(waQues.getOrderGrpId());
		if (waQues.getPartTypeCd()!= null)
			newQues.setQuestionParticipationTypeCd(waQues.getPartTypeCd());
		if (waQues.getParticipationTypeDesc()!= null)
			newQues.setQuestionParticipationTypeDesc(waQues.getParticipationTypeDesc());
		if (waQues.getUserDefinedColumnNm()!= null)
			newQues.setQuestionUserDefinedColumnName(waQues.getUserDefinedColumnNm());
		if (waQues.getRdbTableNm()!= null)
			newQues.setQuestionRDBTableName(waQues.getRdbTableNm());
		if (waQues.getRecordStatusCd()!= null)
			newQues.setQuestionRecStatusCd(waQues.getRecordStatusCd());
		if (waQues.getRelatedUnitInd()!= null)
			newQues.setQuestionRelatedUnitInd(waQues.getRelatedUnitInd());
		if (waQues.getRelatedUnitIndDesc()!= null)
			newQues.setQuestionRelatedUnitIndDesc(waQues.getRelatedUnitIndDesc());
		if (waQues.getRepeatsIndCd()!= null)
			newQues.setQuestionRepeatsIndicatorCd(waQues.getRepeatsIndCd());
		if (waQues.getReportAdminColumnNm()!= null)
			newQues.setQuestionReportAdminColumnNm(waQues.getReportAdminColumnNm());
		if (waQues.getQuestionReqNnd()!= null)
			newQues.setQuestionRequiredNND(waQues.getQuestionReqNnd());
		if (waQues.getQuestionReqNndDesc()!= null)
			newQues.setQuestionRequiredNNDDesc(waQues.getQuestionReqNndDesc());
		if (waQues.getSubGroupDesc()!= null)
			newQues.setQuestionSubGroupDesc(waQues.getSubGroupDesc());
		if (waQues.getSubGroupNm()!= null)
			newQues.setQuestionSubGroupNm(waQues.getSubGroupNm());
		if (waQues.getQuestionToolTip()!= null)
			newQues.setQuestionToolTip(waQues.getQuestionToolTip());
		if (waQues.getQuestionType()!= null)
			newQues.setQuestionType(waQues.getQuestionType());
		if (waQues.getQuestionTypeDesc()!= null)
			newQues.setQuestionTypeDesc(waQues.getQuestionTypeDesc());
		if (waQues.getNbsUiComponentUid()!= null)
			newQues.setQuestionUIComponentTypeCd(waQues.getNbsUiComponentUid().toString());
		if (waQues.getQuestionUnitIdentifier()!= null)
			newQues.setQuestionUnitIdentifier(waQues.getQuestionUnitIdentifier());
		if (waQues.getUnitParentIndentifier()!= null)
			newQues.setQuestionUnitParentIdentifier(waQues.getUnitParentIndentifier());
		if (waQues.getVersionCtrlNbr()!= null)
			newQues.setQuestionVersionControlNbr(waQues.getVersionCtrlNbr().toString());

	}


	/**
	 * addUIMetadataToXML
	 * Add each UI Metadata used in the page to the UI MetaData list in the XML in case of Export.
	 * UI Metadata is a 'customization' of the base Question. It also holds page, section and
	 * subsection information.
	 *
	 * @param PageInfo - root page element
	 * @param WaUiMetadataDT - UI Metadata
	 *
	 */
	private void addUIMetadataToXML(PageInfoType pageInfo, WaUiMetadataDT uiMeta)
	{
		//Add a new UI Metadata entry to the XML
		UserInterfaceMetadataType newMeta = pageInfo.addNewUIMetaEntry();
		if (uiMeta.getQuestionIdentifier() != null)
			newMeta.setMetaQuestionIdentifier(uiMeta.getQuestionIdentifier());
		if (uiMeta.getAdminComment() != null)
			newMeta.setMetaQuestionAdminComment(uiMeta.getAdminComment());
		if (uiMeta.getCodeSetName() != null)
			newMeta.setMetaQuestionCodeSetNm(uiMeta.getCodeSetName());
		if (uiMeta.getQuestionOid() != null)
			newMeta.setMetaQuestionOID(uiMeta.getQuestionOid());
		if (uiMeta.getQuestionOidSystemTxt() != null)
			newMeta.setMetaQuestionOIDSystemText(uiMeta.getQuestionOidSystemTxt());
		if (uiMeta.getCssStyle() != null)
			newMeta.setMetaQuestionCSSStyle(uiMeta.getCssStyle());
		if (uiMeta.getDataCd() != null)
			newMeta.setMetaQuestionDataCd(uiMeta.getDataCd());
		if (uiMeta.getDataLocation() != null)
			newMeta.setMetaQuestionDataLocation(uiMeta.getDataLocation());
		if (uiMeta.getDataType() != null)
			newMeta.setMetaQuestionDataType(uiMeta.getDataType());
		if (uiMeta.getQuestionDataTypeNnd() != null)
			newMeta.setMetaQuestionDataTypeNND(uiMeta.getQuestionDataTypeNnd());
		if (uiMeta.getDataUseCd() != null)
			newMeta.setMetaQuestionDataUseCd(uiMeta.getDataUseCd());
		if (uiMeta.getQuestionNm() != null)
			newMeta.setMetaQuestionNm(uiMeta.getQuestionNm());
		//MetaQuestionDesc
		if (uiMeta.getOtherValIndCd() != null)
			newMeta.setMetaQuestionOtherValueIndCd(uiMeta.getOtherValIndCd());
		if (uiMeta.getDefaultDisplayControl() != null)
			newMeta.setMetaQuestionDefaultDisplayControl(uiMeta.getDefaultDisplayControl());
		if (uiMeta.getDefaultValue() != null)
			newMeta.setMetaQuestionDefaultValue(uiMeta.getDefaultValue());
		if (uiMeta.getDisplayInd() != null)
			newMeta.setMetaQuestionDisplayInd(uiMeta.getDisplayInd());
		if (uiMeta.getEnableInd() != null)
			newMeta.setMetaQuestionEnableInd(uiMeta.getEnableInd());
		if (uiMeta.getEntryMethod() != null)
			newMeta.setMetaQuestionEntryMethod(uiMeta.getEntryMethod());
		if (uiMeta.getFieldLength() != null)
			newMeta.setMetaQuestionFieldSize(uiMeta.getFieldLength());
		if (uiMeta.getFutureDateIndCd() != null)
			newMeta.setMetaQuestionFutureDateInd(uiMeta.getFutureDateIndCd());
		if (uiMeta.getQuestionGroupSeqNbr() != null)
			newMeta.setMetaQuestionGroupNbr(uiMeta.getQuestionGroupSeqNbr().toString());
		if (uiMeta.getGroupNm() != null)
			newMeta.setMetaQuestionGroupNm(uiMeta.getGroupNm());
		if (uiMeta.getHl7Segment() != null)
			newMeta.setMetaQuestionHL7Segment(uiMeta.getHl7Segment());
		//newMeta.setMetaQuestionHL7SegmentDesc();
		if (uiMeta.getQuestionIdentifierNnd() != null)
			newMeta.setMetaQuestionIdentifierNND(uiMeta.getQuestionIdentifierNnd());
		if (uiMeta.getIsIncludeInDatamart() != null)
			newMeta.setMetaQuestionIncludeInDataMart(uiMeta.getIsIncludeInDatamart());
		if (uiMeta.getQuestionLabel() != null)
			newMeta.setMetaQuestionLabel(uiMeta.getQuestionLabel());
		if (uiMeta.getQuestionLabelNnd() != null)
			newMeta.setMetaQuestionLabelNND(uiMeta.getQuestionLabelNnd());
		if (uiMeta.getLocalId() != null)
			newMeta.setMetaQuestionLocalId(uiMeta.getLocalId());
		if (uiMeta.getMask() != null)
			newMeta.setMetaQuestionMask(uiMeta.getMask());
		if (uiMeta.getFieldLength() != null)
			newMeta.setMetaQuestionMaxLength(uiMeta.getFieldLength().toString());
		if (uiMeta.getMaxValue() != null)
			newMeta.setMetaQuestionMaxValue(uiMeta.getMaxValue().toString());
		if (uiMeta.getMinValue() != null)
			newMeta.setMetaQuestionMinValue(uiMeta.getMinValue().toString());
		if (uiMeta.getNndMsgInd() != null)
			newMeta.setMetaQuestionNNDMsgInd(uiMeta.getNndMsgInd());
		if (uiMeta.getOrderGrpId() != null)
			newMeta.setMetaQuestionOrderGroupId(uiMeta.getOrderGrpId());
		if (uiMeta.getOrderNbr() != null)
			newMeta.setMetaQuestionOrderNbr(uiMeta.getOrderNbr().toString());
		//newMeta.setMetaQuestionParticipationTypeCd();
		if (uiMeta.getParticipationTypeDesc() != null)
			newMeta.setMetaQuestionParticipationTypeDesc(uiMeta.getParticipationTypeDesc());
		if (uiMeta.getPublishIndCd() != null)
			newMeta.setMetaQuestionPublishInd(uiMeta.getPublishIndCd());
		if (uiMeta.getRdbcolumnNm() != null)
			newMeta.setMetaQuestionRDBColumnName(uiMeta.getRdbcolumnNm());
		if (uiMeta.getUserDefinedColumnNm() != null)
			newMeta.setMetaQuestionUserDefinedColumnName(uiMeta.getUserDefinedColumnNm());
		if (uiMeta.getRdbTableNm() != null)
			newMeta.setMetaQuestionRDBTableName(uiMeta.getRdbTableNm());
		if (uiMeta.getRecordStatusCd() != null)
			newMeta.setMetaQuestionRecStatusCd(uiMeta.getRecordStatusCd());
		if (uiMeta.getReportLabel() != null)
			newMeta.setMetaQuestionReportLabel(uiMeta.getReportLabel());
		if (uiMeta.getIsRequired() != null)
			newMeta.setMetaQuestionRequired(uiMeta.getIsRequired());
		if (uiMeta.getQuestionReqNnd() != null)
			newMeta.setMetaQuestionRequiredNND(uiMeta.getQuestionReqNnd());
		if (uiMeta.getIsSecured() != null)
			newMeta.setMetaQuestionSecuredInd(uiMeta.getIsSecured());
		if (uiMeta.getStandardNndIndCd() != null)
			newMeta.setMetaQuestionStandardNndIndCd(uiMeta.getStandardNndIndCd());
		if (uiMeta.getSubGroupNm() != null)
			newMeta.setMetaQuestionSubGroupNm(uiMeta.getSubGroupNm());
		if (uiMeta.getTabName() != null)
			newMeta.setMetaQuestionTabName(uiMeta.getTabName());
		if (uiMeta.getTabOrderId() != null)
			newMeta.setMetaQuestionTabOrder(uiMeta.getTabOrderId().toString());
		if (uiMeta.getQuestionToolTip() != null)
			newMeta.setMetaQuestionToolTip(uiMeta.getQuestionToolTip());
		if (uiMeta.getQuestionType()!= null)
			newMeta.setMetaQuestionType(uiMeta.getQuestionType());
		if (uiMeta.getQuestionTypeDesc() != null)
			newMeta.setMetaQuestionTypeDesc(uiMeta.getQuestionTypeDesc());
		if (uiMeta.getNbsUiComponentUid() != null)
		{
			if(uiMeta.getNbsUiComponentUid().toString().equalsIgnoreCase("1031"))//TODO: Delete these 2 lines!!
				System.out.println("Coded with search");
			newMeta.setMetaQuestionUIComponentTypeCd(uiMeta.getNbsUiComponentUid().toString());
		}
		if (uiMeta.getQuestionUnitIdentifier() != null)
			newMeta.setMetaQuestionUnitIdentifier(uiMeta.getQuestionUnitIdentifier());
		if (uiMeta.getUnitTypeCd() != null)
			newMeta.setMetaQuestionUnitTypeCd(uiMeta.getUnitTypeCd());
		if (uiMeta.getUnitValue()!= null)
			newMeta.setMetaQuestionUnitValue(uiMeta.getUnitValue());
		if (uiMeta.getUnitValueCodeSetNm() != null)
			newMeta.setMetaQuestionUnitValueCodeSetNm(uiMeta.getUnitValueCodeSetNm());
		if (uiMeta.getVersionCtrlNbr() != null)
			newMeta.setMetaQuestionVersionControlNbr(uiMeta.getVersionCtrlNbr().toString());
	}
	/**
	 * LookupControlName
	 *    May need to pass data type to refine the control name.
	 * @param uiComponentTYpe
	 * @return controlName. empty string if unsuccessful
	 *
	 */
	private String lookupControlName(int componentType)
	{
		String controlNm = "";
		switch (componentType) {

		case PageMetaConstants.aINPUT:
			controlNm = PageMetaConstants.TEXTBOX_CONTROL;
			break;
		case PageMetaConstants.aINPUTREADONLYSAVE:
			controlNm = PageMetaConstants.TEXTBOX_READONLY_SAVE_CONTROL;
			break;
		case PageMetaConstants.aINPUTREADONLYNOSAVE:
			controlNm = PageMetaConstants.TEXTBOX_READONLY_NOSAVE_CONTROL;
			break;	
		case PageMetaConstants.aSINGLESELECT:
			controlNm = PageMetaConstants.SINGLESELECT_CONTROL;
			break;
		case PageMetaConstants.aSINGLESELECTREADONLYSAVE:
			controlNm = PageMetaConstants.SINGLESELECT_READONLY_SAVE_CONTROL;
			break;	
		case PageMetaConstants.aSINGLESELECTREADONLYNOSAVE:
			controlNm = PageMetaConstants.SINGLESELECT_READONLY_NOSAVE_CONTROL;
			break;			
		case PageMetaConstants.aMULTISELECT:
			controlNm = PageMetaConstants.MULTISELECT_CONTROL;
			break;
		case PageMetaConstants.aMULTISELECTREADONLYSAVE:
			controlNm = PageMetaConstants.MULTISELECT_READONLY_SAVE_CONTROL;
			break;
		case PageMetaConstants.aMULTISELECTREADONLYNOSAVE:
			controlNm = PageMetaConstants.MULTISELECT_READONLY_NOSAVE_CONTROL;
			break;			
		case PageMetaConstants.aCHECKBOX:
			controlNm = PageMetaConstants.CHECKBOX_CONTROL;
			break;
		case PageMetaConstants.aRADIO:
			controlNm = PageMetaConstants.RADIO_CONTROL;
			break;
		case PageMetaConstants.aBUTTON:
			controlNm = PageMetaConstants.BUTTON_CONTROL;
			break;
		case PageMetaConstants.aHYPERLINK:
			controlNm = PageMetaConstants.HYPERLINK_CONTROL;
			break;
		case PageMetaConstants.aTEXTAREA:
			controlNm = PageMetaConstants.TEXTAREA_CONTROL;
			break;
		case PageMetaConstants.aROLLINGNOTE:
			controlNm = PageMetaConstants.ROLLINGNOTE_CONTROL;
			break;
		case PageMetaConstants.aSUBHEADING:
			controlNm = PageMetaConstants.SUBHEADING_CONTROL;
			break;
		case PageMetaConstants.aLINESEPARATOR:
			controlNm = PageMetaConstants.LINESEPARATOR_CONTROL;
			break;
		case PageMetaConstants.aPARTICIPATION:
			controlNm = PageMetaConstants.PARTICIPATION_CONTROL;
			break;
		case PageMetaConstants.aTABLE:
			controlNm = PageMetaConstants.TABLE_CONTROL;
			break;
		case PageMetaConstants.aINFORMATIONBAR:
			controlNm = PageMetaConstants.INFORMATIONBAR_CONTROL;
			break;
		case PageMetaConstants.aPARTICIPANTLIST:
			controlNm = PageMetaConstants.PARTICIPANTLIST_CONTROL;
			break;
		case PageMetaConstants.aSINGLESELECTWITHSEARCH:
			controlNm = PageMetaConstants.SINGLESELECTSEARCH_CONTROL;
			break;
		case PageMetaConstants.aPATIENTSEARCH:
			controlNm = PageMetaConstants.PATIENT_SEARCH_CONTROL;
			break;
		case PageMetaConstants.aACTIONBUTTON:
			controlNm = PageMetaConstants.ACTION_BUTTON_CONTROL;
			break;
		case PageMetaConstants.aSETVALUEBUTTON:
			controlNm = PageMetaConstants.SET_VALUE_BUTTON_CONTROL;
			break;
		case PageMetaConstants.aLOGICFLAG:
			controlNm = PageMetaConstants.LOGIC_FLAG_CONTROL;
			break;
		case PageMetaConstants.aORIGDOCLIST:
			controlNm = PageMetaConstants.ORIGDOCLIST_CONTROL;
			break;
		}
		return controlNm;
	}

	/*
	 * Determine if the Participation Type is an Organization, Person or Contact
	 * @return ORG, PSN, CON 
	 */
	private String findParType(String parTypeCd, String questionId)
	{
		TreeMap<Object, Object> participationTypeCaseMap = CachedDropDowns.getParticipationTypeList();
		String subjectClassCd = "unknown";
		ParticipationTypeVO parTypeVO = (ParticipationTypeVO) participationTypeCaseMap.get(parTypeCd+NEDSSConstants.PART_CACHED_MAP_KEY_SEPARATOR+ questionId);
		if (parTypeVO != null)
			subjectClassCd = parTypeVO.getSubjectClassCd();
		return subjectClassCd;
	}

	/**
	 * getDisabledStr
	 * return true or false if disbled
	 * @param WaTemplateDT - template dt
	 *
	 */
	private String getDisabledStr(String enableInd)
	{
		if (enableInd.equalsIgnoreCase("F"))
			return "disabled";
		return "enabled";
	}


	/**
	 * Populate Messaging Info - put NND info from the DT into the XML
	 *
	 * @param WaNndMetadataDT from the PageElementVO
	 * @param alllocated MessagingInfoType
	 *
	 */
	private void populateMessagingInfo(PageInfoType pageInfo, WaNndMetadataDT waNND, String questionIdentifier) {
		if (questionIdentifier == null)
			return;
		MessagingInfoType msgInfo = pageInfo.addNewNNDEntry();
		msgInfo.setMsgQuestionIdentifier(questionIdentifier); // this may get reset from the DT
		if (waNND.getQuestionDataTypeNnd()!= null)
			msgInfo.setMsgDataType(waNND.getQuestionDataTypeNnd());
		if (waNND.getTranslationTableNm() != null)
			msgInfo.setMsgElementTranslationTableNm(waNND.getTranslationTableNm());
		if (waNND.getXmlDataType() != null)
			msgInfo.setMsgELementXMLDataType(waNND.getXmlDataType());
		if (waNND.getXmlPath() != null)
			msgInfo.setMsgELementXMLPath(waNND.getXmlPath());
		if (waNND.getXmlTag() != null)
			msgInfo.setMsgELementXMLTag(waNND.getXmlTag());
		if (waNND.getHl7SegmentField()!= null)
			msgInfo.setMsgHl7SegmentField(waNND.getHl7SegmentField());
		//msgInfo.setMsgIncludeInMessage();
		if (waNND.getQuestionLabelNnd() != null)
			msgInfo.setMsgLabelInMessage(waNND.getQuestionLabelNnd());
		if (waNND.getLocalId() != null)
			msgInfo.setMsgLocalId(waNND.getLocalId());
		if (waNND.getOrderGroupId() != null)
			msgInfo.setMsgOrderGroupId(waNND.getOrderGroupId());
		if (waNND.getPartTypeCd() != null)
			msgInfo.setMsgPartTypeCd(waNND.getPartTypeCd());
		if (waNND.getQuestionDataTypeNnd() != null)
			msgInfo.setMsgQuestionDataTypeNND(waNND.getQuestionDataTypeNnd());
		if (waNND.getQuestionIdentifier() != null)
			msgInfo.setMsgQuestionIdentifier(waNND.getQuestionIdentifier());
		if (waNND.getQuestionIdentifierNnd() != null)
			msgInfo.setMsgQuestionIdentifierNND(waNND.getQuestionIdentifierNnd());
		if (waNND.getQuestionLabelNnd() != null)
			msgInfo.setMsgQuestionLabelNND(waNND.getQuestionLabelNnd());
		if (waNND.getQuestionOrderNnd() != null)
			msgInfo.setMsgQuestionOrderNND(waNND.getQuestionOrderNnd().toString());
		if (waNND.getRecStatusCd() != null)
			msgInfo.setMsgRecStatusCd(waNND.getRecStatusCd());
		if (waNND.getRepeatGroupSeqNbr() != null)
			msgInfo.setMsgRepeatGroupSeqNbr(waNND.getRepeatGroupSeqNbr().toString());
		if (waNND.getQuestionRequiredNnd() != null)
			msgInfo.setMsgRequiredInMessage(waNND.getQuestionRequiredNnd());
		if (waNND.getMsgTriggerIndCd() != null)
			msgInfo.setMsgTriggerIndCd(waNND.getMsgTriggerIndCd());

	}

	/**
	 * Populate RDB Info - put RDB info from the DT into the XML
	 *
	 * @param WaRdbMetadataDT from the PageElementVO
	 * @param alllocated RdbInfoType
	 *
	 */
	private void populateDataMartInfo(PageInfoType pageInfo, WaRdbMetadataDT waRDB, String questionIdentifier) {
		if (questionIdentifier == null)
			return;
		if (waRDB.getRdbcolumNm() == null)
			return;
		DataMartInfoType rdbInfo = pageInfo.addNewRDBEntry();
		if (waRDB.getUserDefinedColumnNm() != null)
			rdbInfo.setDmUserDefinedColumnName(waRDB.getUserDefinedColumnNm());
		if (waRDB.getLocalId() != null)
			rdbInfo.setDmLocalId(waRDB.getLocalId());
		if (waRDB.getRdbcolumNm() != null)
			rdbInfo.setDmRDBColumnName(waRDB.getUserDefinedColumnNm());
		if (waRDB.getRdbTableNm() != null)
			rdbInfo.setDmRDBTableName(waRDB.getRdbTableNm());
		if (waRDB.getRecStatusCd() != null)
			rdbInfo.setDmRecStatusCd(waRDB.getRecStatusCd());
		if (waRDB.getRptAdminColumnNm() != null)
			rdbInfo.setDmReportAdminColumnName(waRDB.getRptAdminColumnNm());
		if (waRDB.getVersionCtrlNbr() != null)
			rdbInfo.setDmVersionControllNbr(waRDB.getVersionCtrlNbr().toString());
	}


		
		
}
	
	

