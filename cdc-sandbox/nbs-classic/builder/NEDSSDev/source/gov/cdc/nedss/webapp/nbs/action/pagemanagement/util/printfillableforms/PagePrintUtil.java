package gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.printfillableforms;

import gov.cdc.nedss.association.dt.ActRelationshipDT;
import gov.cdc.nedss.entity.person.dt.PersonDT;
import gov.cdc.nedss.entity.person.dt.PersonNameDT;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.locator.dt.EntityLocatorParticipationDT;
import gov.cdc.nedss.locator.dt.PostalLocatorDT;
import gov.cdc.nedss.locator.dt.TeleLocatorDT;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.PageProxyVO;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.act.PageActProxyVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.LabReportSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.MorbReportSummaryVO;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.NbsQuestionMetadata;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.MessageConstants;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.action.pam.util.PamPrintUtil;
import gov.cdc.nedss.webapp.nbs.form.page.PageForm;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.QuestionsCache;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.cos.COSName;
//import org.apache.pdfbox.cos.COSString;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationWidget;
//import org.apache.pdfbox.pdmodel.interactive.annotation.PDAppearanceDictionary;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDCheckBox;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.interactive.form.PDRadioButton;
//import org.apache.pdfbox.pdmodel.interactive.form.PDRadioCollection;
//import org.apache.pdfbox.pdmodel.interactive.form.PDRadioButton;

//import org.apache.pdfbox.pdmodel.interactive.form.PDTextbox;
//import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;


/**
 * PagePrintUtil is a Java PDF Print Utility that uses AcroFields to populate PDF 
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: Leidos</p>
 * PagePrintUtil.java
 * Oct 15, 2013
 * @author Pradeep Kumar Sharma
 * @version 1.1
 */
public class PagePrintUtil extends PagePrintProxyToPrintableForm {
	
	static final LogUtils logger = new LogUtils(PagePrintUtil.class.getName());
	public static PropertyUtil propertyUtil= PropertyUtil.getInstance();
	private static Map<Object,Object> questionMap = null;
	private static Map<Object,Object> contactQuestionMap = null;
	private static Map<Object,Object> interviewQuestionMap = null;
	private static PDDocument pdfDocument;

	@SuppressWarnings("unchecked")
	public static void loadQuestionMap(String formCd){
		questionMap = (Map<Object, Object> )QuestionsCache.getDMBQuestionMap().get(formCd);
		
	}
	public static void loadContactQuestionMap(String formCd){
		contactQuestionMap = (Map<Object, Object> )QuestionsCache.getDMBQuestionMap().get(formCd);
		
	}
	public static void loadInterviewQuestionMap(String formCd){
		interviewQuestionMap = (Map<Object, Object> )QuestionsCache.getDMBQuestionMap().get(formCd);
		
	}
	
	public static void addQuestionMap(String formCd){
		if (formCd != null && !formCd.isEmpty()){
			 Map<Object,Object> questionMapToAdd = (Map<Object, Object> )QuestionsCache.getDMBQuestionMap().get(formCd);
			 questionMap.putAll(questionMapToAdd);
		}
		
	}
	
	/**
	 * printForm checks for InvestigationFormCode and calls the PAM Specific Util classes to handle Print
	 * @param form
	 * @param req
	 * @param res
	 * @throws Exception 
	 */
	public static void printForm(PageForm pageForm, HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		
		
		try {	
			//String fileName=NedssUtils.CleanStringPath(FilenameUtils.normalize(propertiesDirectory + pageForm.getFormName() +".pdf"));
			pdfDocument = PDDocument.load(new File(NedssUtils.CleanStringPath(FilenameUtils.normalize(propertiesDirectory + pageForm.getFormName() +".pdf"))));
			PDDocumentCatalog docCatalog = pdfDocument.getDocumentCatalog();
			PDAcroForm acroForm = docCatalog.getAcroForm();
			PageProxyVO proxyVO = viewPrintLoadUtil(pageForm, req);	
			fillPDForm(acroForm, pageForm, proxyVO,req);

			res.setContentType("application/pdf");
			
			pdfDocument.save(res.getOutputStream());
			
			
		} catch (Exception e) {
			logger.error(e.toString());
			req.getSession().setAttribute("error", "<b>There is an error while printing the Form: </b> <br/><br/>");				
			try {
				res.sendRedirect("/nbs/error");
			} catch (IOException e1) {
				logger.error("Error while redirecting Print Error: " + e1.toString());
				throw new Exception(e);
			}
		} finally {		
			try {
				
				if(pdfDocument!=null)
					pdfDocument.close();
			} catch (IOException e2) {
				logger.error("Error while closing FileInputStream for Print : " + e2.toString());
			}
		}
	}
	
	
public static void printBlankForm(PageForm pageForm, HttpServletRequest req, HttpServletResponse res) throws Exception {
		try {	
			//String filename = NedssUtils.CleanStringPath(FilenameUtils.normalize(propertiesDirectory + pageForm.getFormName() +".pdf"));
			pdfDocument = PDDocument.load(new File(NedssUtils.CleanStringPath(FilenameUtils.normalize(propertiesDirectory + pageForm.getFormName() +".pdf"))));
			res.setContentType("application/pdf");
			
			pdfDocument.save(res.getOutputStream());
			
		} catch (Exception e) {
			logger.error(e.toString());
			req.getSession().setAttribute("error", "<b>There is an error while printing the Form: </b> <br/><br/>");				
			try {
				res.sendRedirect("/nbs/error");
			} catch (IOException e1) {
				logger.error("Error while redirecting Print Error: " + e1.toString());
				throw new Exception(e);
			}
		} finally {		
			try {
				if(pdfDocument!=null)
					pdfDocument.close();
			} catch (IOException e2) {
				logger.error("Error while closing FileInputStream for Print : " + e2.toString());
			}
		}
	}


public static void printFieldRecordForm(PageForm pageForm, HttpServletRequest req, HttpServletResponse res) throws Exception {
	
	String pdfFileName = NEDSSConstants.CDC_FIELD_RECORD_FORM_PDF;
	try {			
		pdfDocument = PDDocument.load(new File(propertiesDirectory + pdfFileName));
		PDDocumentCatalog docCatalog = pdfDocument.getDocumentCatalog();
		PDAcroForm acroForm = docCatalog.getAcroForm();
		PageProxyVO proxyVO = viewPrintLoadUtil(pageForm, req);			
		fillPDForm(acroForm, pageForm, proxyVO, req);				
		res.setContentType("application/pdf");
		
		pdfDocument.save(res.getOutputStream());
		
		
	} catch (Exception e) {
		logger.error(e.toString());
		req.getSession().setAttribute("error", "<b>There is an error while printing the Form: </b> <br/><br/>");				
		try {
			res.sendRedirect("/nbs/error");
		} catch (IOException e1) {
			logger.error("Error while redirecting Print Error: " + e1.toString());
			throw new Exception(e);
		}
	} finally {		
		try {
			if(pdfDocument!=null)
				pdfDocument.close();
		} catch (IOException e2) {
			logger.error("Error while closing FileInputStream for Print : " + e2.toString());
		}
	}
}


static void setField(PDField field, String name, String value)throws Exception {
	setField(field,name,value, Boolean.TRUE);
}
	/**
	 * 
	 * @param field
	 * @param name
	 * @param value
	 * @throws Exception
	 */
	static void setField(PDField field, String name, String value,Boolean grid) throws Exception {
		try {//field.getDictionary().setString(COSName.DA,"/Helv 12 Tf 0 g");
			 //field.getDictionary().setString(COSName.DA, "/Cour 20 Tf 0 g");field = new PDTextbox(acroForm, field.getDictionary());
			 
			if (field != null && value!=null
					&& field.getClass()
							.getName()
							.equals("org.apache.pdfbox.pdmodel.interactive.form.PDTextbox")) {
				if(grid){
				value = setValueForGridFields(name,value);
				}
				//COSDictionary dict = ((PDField)field).getDictionary();
				//COSString defaultAppearance = (COSString) dict.getDictionaryObject(COSName.DA);				
				//org.apache.pdfbox.pdmodel.interactive.form.PDTextbox pdTextBox = (PDTextbox) field;
				//pdTextBox.setValue(value);
				field.setValue(value);

			} else if (field != null
					&& field.getClass()
							.getName()
							.equals("org.apache.pdfbox.pdmodel.interactive.form.PDCheckbox")) {
				logger.debug("PDCheckbox field name:" + field.getPartialName());
				((PDCheckBox) field).check();
			} else if (field != null && value!=null
					&& field.getClass()
							.getName()
							.equals("org.apache.pdfbox.pdmodel.interactive.form.PDRadioButton")) {
				logger.debug("PDRadioCollection field name:" + field.getPartialName());
				if( field.getPartialName().equals("STDLAB121_CD_1")){
					logger.debug("PDRadioCollection field name:" + field.getPartialName());
				}
				
				try{
				field.setValue(value);
				}catch(IllegalArgumentException e){
					logger.error("setField: Error while setting field values for name:"+ name  +"and value="+value +" and IOException raises: " + e);
				}
				
				List<PDAnnotationWidget> widgets = field.getWidgets();
				if(widgets!=null)
				for(int i=0; i<widgets.size();i++){
					Set<COSName> COSNames=	 ((COSDictionary)widgets.get(i).getAppearance().getNormalAppearance().getCOSObject()).keySet();
					String COSNameKey = ((COSName)COSNames.toArray()[0]).getName();
		
						if(COSNameKey.trim().equalsIgnoreCase(value.trim()) ){
							field.setValue(COSNameKey);
						}
	
				}
				
				
				/*
				if(((PDRadioCollection) field).getKids() != null){
				for(Object o :((PDRadioCollection) field).getKids()){
					if(o instanceof PDCheckbox){
//						logger.debug("PDRadioCollection field :kid - PDCheckbox -name:" + ((PDCheckbox)o).getOnValue());

						if(((PDCheckbox)o).getOnValue().trim().equalsIgnoreCase(value.trim()) ){
							((PDCheckbox)o).check();
						}

					}
				}
				
				}*/
			} else {
				logger.debug("No field found with name:" + name);
				if(value != null) field.setValue(value);
			}
			
		} catch (IOException e) {
			logger.error("setField: Error while setting field values for name:"+ name  +"and value="+value +" and IOException raises: " + e);
			throw new Exception(e);
		}
	}
	
	private static String  setValueForGridFields(String fieldName, String value) {
		if (fieldName.contains(PrintConstants.NBS217)
				||fieldName.contains(PrintConstants.NBS219)
				||fieldName.contains(PrintConstants.NBS130)
				||fieldName.contains(PrintConstants.NBS132)
				||fieldName.contains(PrintConstants.NBS134)
				||fieldName.contains(PrintConstants.NBS136)
				||fieldName.contains(PrintConstants.NBS250)
				){
			value = PamPrintUtil.addEmptySpaces(value,"  ");
		}
		if (fieldName.contains(PrintConstants.INV200)
				||fieldName.contains(PrintConstants.IXS101)
				||fieldName.contains(PrintConstants.NBS143)
				||fieldName.contains(PrintConstants.NBS167)
				||fieldName.contains(PrintConstants.NBS177)
				||fieldName.contains(PrintConstants.NBS258)
				||fieldName.contains(PrintConstants.NBS267)
				){
			value =value.length()<2? "0" + value : value;
			value = PamPrintUtil.addEmptySpaces(value,"  ");
		}
		if(fieldName.contains(PrintConstants.DEM118)){
			value =value.length()<2? "0" + value : value;
			value =value.length()<3? "0" + value : value;
			value = PamPrintUtil.addEmptySpaces(value," ");
		}
		return value;
	}
	static void setField(PDField field, String name, String... value) throws Exception {
		try {
			if (field != null && value!=null
					&& field.getClass()
							.getName()
							.equals("org.apache.pdfbox.pdmodel.interactive.form.PDTextbox")) {
				field.setValue(value[0]);

			} else if (field != null
					&& field.getClass()
							.getName()
							.equals("org.apache.pdfbox.pdmodel.interactive.form.PDCheckbox")) {
				logger.debug("PDCheckbox field name:" + field.getPartialName());
				((PDCheckBox) field).check();
			} else if (field != null && value!=null
					&& field.getClass()
							.getName()
							.equals("org.apache.pdfbox.pdmodel.interactive.form.PDRadioButton")) {
				logger.debug("PDRadioCollection field name:" + field.getPartialName());
				
				
				List<PDAnnotationWidget> widgets = field.getWidgets();
				//for(int i=0; i<widgets.size();i++){
				if(widgets!=null){
					Set<COSName> COSNames=	 ((COSDictionary)widgets.get(0).getAppearance().getNormalAppearance().getCOSObject()).keySet();
					String COSNameKey = ((COSName)COSNames.toArray()[0]).getName();
					for(int j=0;j<value.length;j++){
						if(COSNameKey.trim().equalsIgnoreCase(value[j].trim()) ){
							field.setValue(COSNameKey);
						}
	
					}
				}
		
				/*
				
				//((PDRadioCollection) field).setValue(value[0]);//???????????
				for(Object o :COSNames){
					if(o instanceof PDCheckbox){
						logger.debug("PDRadioCollection field :kid - PDCheckbox -name:" + ((PDCheckbox)o).getOnValue());
						for(int i=0;i<value.length;i++){
							if(((PDCheckbox)o).getOnValue().trim().equalsIgnoreCase(value[i].trim()) ){
								((PDCheckbox)o).check();

						}

					}
				}
				}*/
				
				
				//((PDRadioCollection) field).setValue(value[0]);
				/*
				for(Object o :((PDRadioCollection) field).getKids()){
					if(o instanceof PDCheckbox){
						logger.debug("PDRadioCollection field :kid - PDCheckbox -name:" + ((PDCheckbox)o).getOnValue());
						for(int i=0;i<value.length;i++){
							if(((PDCheckbox)o).getOnValue().trim().equalsIgnoreCase(value[i].trim()) ){
								((PDCheckbox)o).check();
							}
						}
					}
					
				}*/
			} else {
				logger.debug("No field found with name:" + name);
				if(value != null) field.setValue(value[0]);
			}
			
		} catch (IOException e) {
			logger.error("setField: Error while setting field values for name:"+ name  +"and value="+value +" and IOException raises: " + e);
			throw new Exception(e);
		}
	}
	
	/**
	 * RVCTDemographicAnswers for Tuberculosis Form
	 * @param pdfForm
	 * @param pamForm
	 * @throws Exception
	 */
	private static void fillPDForm(PDAcroForm acroForm, PageForm pageForm,PageProxyVO proxyVO,HttpServletRequest req) throws Exception {
		
		
		try {
			Map<String, String> mappedInvRepeatValues = processRepeatingQuestions(((PageActProxyVO)proxyVO).getPageVO().getPageRepeatingAnswerDTMap());
			Map<String, String> mappedDrugValues = processDrugValues(mappedInvRepeatValues);
			ActRelationshipDT actRelationshipDT = getSourceAct(proxyVO);
			Map<String, String> mappedLabValues = new HashMap<String, String>();
			Map<String, String> mappedTreatments =  new HashMap<String, String>();
			Map<String, String> mappedDiagnosis = new HashMap<String, String>();
			Map<String, String> mappedReferralBasis = new HashMap<String, String>();
			Map<String, String>  mappedInterviewRecord =  new HashMap<String, String>();
			Map<String, String> mappedOOJ =  new HashMap<String, String>();
			Map<String, String> mappedMorbValues =new HashMap<String, String>();
			int i = 1; //what is this used for??
			
			Long sourceUid=null;
			if(actRelationshipDT!=null)
				sourceUid=actRelationshipDT.getSourceActUid();
			
			Map<String, String> mappedEntityValues= populateEntityValues(proxyVO, i);
			
			getMappedValues(pageForm,proxyVO, mappedLabValues, mappedTreatments,mappedDiagnosis,mappedReferralBasis,mappedInterviewRecord,mappedOOJ,mappedMorbValues,sourceUid,req, mappedEntityValues);
			
			//what is this for?
			//if(proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getReferralBasisCd()!=null &&
			//		proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getReferralBasisCd().equalsIgnoreCase(MessageConstants.REFERRAL_CODE_FOR_MORB)){				
			//}
			
			Map<Object, Object>  answerMap = pageForm.getPageClientVO().getAnswerMap(); 
			
			String age = "";  //is this used anywhere??
			Map<String, String> mappedEtc =  new HashMap<String, String>();
			populateMappedEtc(proxyVO,mappedEtc);

			if(mappedEntityValues!=null){
				age = mappedEntityValues.get(PrintConstants.DEM118);
			}
			
			//presently set to 1 so that we can make this code flexible for 2 nd Investigation for print
			@SuppressWarnings("unchecked")
			List<PDField> pdfFormFields = acroForm.getFields();
			for(PDField pdfField: pdfFormFields){
				if(pdfField != null){
				pdfField.setReadOnly(true);
				String key =pdfField.getPartialName();
				
				if(mappedTreatments!=null && mappedTreatments.containsKey(key)){
					String value = mappedTreatments.get(key);
					setField(pdfField,key,value);
					continue;
				}else if(mappedInvRepeatValues!=null && mappedInvRepeatValues.containsKey(key)){
					String value = mappedInvRepeatValues.get(key);
					setField(pdfField,key,value);
					continue;
				}else if(mappedMorbValues!=null && mappedMorbValues.containsKey(key)){
					String value = mappedMorbValues.get(key);
					setField(pdfField,key,value);
					continue;
				}else if(mappedLabValues!=null && mappedLabValues.containsKey(key)){
					String value = mappedLabValues.get(key);
					setField(pdfField,key,value);
					continue;
				}else if(mappedEntityValues!=null && mappedEntityValues.containsKey(key)){
					String value = mappedEntityValues.get(key);
					setField(pdfField,key,value);
					continue;
				}else if(key.contains(PrintConstants.DEM152+"_")){
					StringBuffer sb = new StringBuffer();
					for(String s : mappedEntityValues.keySet()){
						if(s.contains(PrintConstants.DEM152+"_")){
							sb.append(mappedEntityValues.get(s) +":");
						}
					}
					String[] s= sb.toString().split(":");
					if(s.length>0) 	setField(pdfField,key,s);
					continue;
				}else if(mappedDiagnosis!=null && mappedDiagnosis.containsKey(key)){
					String value = mappedDiagnosis.get(key);
					setField(pdfField,key,value);
					continue;
				}else if(mappedReferralBasis !=null && mappedReferralBasis.containsKey(key)){
					String value =mappedReferralBasis.get(key);
					if(key.endsWith("_CD")){
						key = key.substring(0, key.length()-7);	
						NbsQuestionMetadata metaData = (NbsQuestionMetadata) questionMap.get(key);
						
						if (metaData!=null && metaData.getCodeSetNm() != null && value!=null){
							value =cache.getDescForCode(metaData.getCodeSetNm(), value);
						}
					}else if(key.contains("_")){
						key = key.substring(0, key.length()-4);	
					}
					
					setField(pdfField,pdfField.getPartialName(),value);
					continue;
				}else if(mappedInterviewRecord!=null && mappedInterviewRecord.containsKey(key)){
					String value = mappedInterviewRecord.get(key);
					if(key.contains("NBS136") && pageForm.getFormName().equals(NEDSSConstants.CDC_FIELD_RECORD_FORM )){
						setField(pdfField,key,value,false);
					}else{
					setField(pdfField,key,value);
					}
					continue;
				}else if(mappedEtc!=null && mappedEtc.containsKey(key)){
					String value = mappedEtc.get(key);
					setField(pdfField,key,value);
					continue;
				} else if(mappedDrugValues!=null && mappedDrugValues.containsKey(key)){
					String value = mappedDrugValues.get(key);
					setField(pdfField,key,value);
					continue;
				}else if(key.contains("DEM102v")){
					String value =(String)answerMap.get("DEM102");
					setField(pdfField,pdfField.getPartialName(),value,Boolean.FALSE);
					continue;
				}else if(key.contains("DEM104v")){
					String value =(String)answerMap.get("DEM104");
					setField(pdfField,pdfField.getPartialName(),value,Boolean.FALSE);
					continue;
				}else if(key.startsWith("Case_Id_1") && pageForm.getFormName().equals(NEDSSConstants.CDC_INTERVIEW_RECORD_FORM )){
					if (mappedDiagnosis.containsKey("Case_Id_1")) {
						String value =(String)mappedDiagnosis.get("Case_Id_1");
						setField(pdfField,pdfField.getPartialName(),value,Boolean.FALSE);
					}
					continue;	
				}else if(key.startsWith("Case_Id_2") && pageForm.getFormName().equals(NEDSSConstants.CDC_INTERVIEW_RECORD_FORM )){
					if (mappedDiagnosis.containsKey("Case_Id_2")) {
						String value =(String)mappedDiagnosis.get("Case_Id_2");
						setField(pdfField,pdfField.getPartialName(),value,Boolean.FALSE);
					}
					continue;	
				}else{
				
					if(key.endsWith("_"+ i))
						key = key.substring(0, key.length()-2);	
					logger.debug("key is "+key);
					
					if(answerMap.containsKey(key)){
						if(key.equalsIgnoreCase(PrintConstants.DEM162)){
							String value =(String)answerMap.get(key);
							String stateDesc=cache.getStateAbbreviationByCode(value);
							setField(pdfField,pdfField.getPartialName(),stateDesc);
							continue;
						}else if(key.equalsIgnoreCase(PrintConstants.NBS181)){
							//if Disposition = K (OOJ),
							String value =(String)answerMap.get(key);
							
							//investigation started from Patient File Events (OOJ P/C), 
							if(value == null || value.length() ==0){
								value =(String)answerMap.get(PrintConstants.NBS113);
							}
							setField(pdfField,pdfField.getPartialName(),value);
							continue;
						}else if(key.equalsIgnoreCase(PrintConstants.NBS181)){
							//if Disposition = K (OOJ),
							String value =(String)answerMap.get(key);
							
							//investigation started from Patient File Events (OOJ P/C), 
							if(value == null || value.length() ==0){
								value =(String)answerMap.get(PrintConstants.NBS113);
							}
							setField(pdfField,pdfField.getPartialName(),value);
							continue;
						}else if(key.equalsIgnoreCase(PrintConstants.NBS118)){
							//if Disposition = K (OOJ),
							String value =(String)answerMap.get(key);
							if(((PageActProxyVO)proxyVO).getPublicHealthCaseVO().getThePublicHealthCaseDT().getReferralBasisCd().equals("P2")){
								value =(String)answerMap.get(PrintConstants.NBS121);
							}
							
							setField(pdfField,pdfField.getPartialName(),value);
							continue;
						}else if(key.equalsIgnoreCase(PrintConstants.NBS119)){
							//if Disposition = K (OOJ),
							String value =(String)answerMap.get(key);
							if(((PageActProxyVO)proxyVO).getPublicHealthCaseVO().getThePublicHealthCaseDT().getReferralBasisCd().equals("P2")){
								value =(String)answerMap.get(PrintConstants.NBS122);
							}
							
							setField(pdfField,pdfField.getPartialName(),value);
							continue;
						}else if(key.equalsIgnoreCase(PrintConstants.NBS120)){
							//if Disposition = K (OOJ),
							String value =(String)answerMap.get(key);
							if(((PageActProxyVO)proxyVO).getPublicHealthCaseVO().getThePublicHealthCaseDT().getReferralBasisCd().equals("P2")){
								value =(String)answerMap.get(PrintConstants.NBS123);
							}
							
							setField(pdfField,pdfField.getPartialName(),value);
							continue;
						}else if(key.equalsIgnoreCase(PrintConstants.DEM177)){
							String value =(String)answerMap.get(key);

							if(pageForm.getFormName().equals(NEDSSConstants.CDC_FIELD_RECORD_FORM)){
								if(value != null && value.length() !=0){
									value=value + "(H) \n";
								}
//								if(answerMap.containsKey(PrintConstants.NBS002) && answerMap.get(PrintConstants.NBS002) != null && !((String)answerMap.get(PrintConstants.NBS002)).isEmpty() ) value= value + (String)answerMap.get(PrintConstants.NBS002) + "(W)" ;
							}
							setField(pdfField,pdfField.getPartialName(),value);
							continue;
						}else if(key.equalsIgnoreCase(PrintConstants.NBS006)){
							String value =(String)answerMap.get(key);
							if(value != null && value.length() !=0){
								value=value + "(C) ";
							}
							setField(pdfField,pdfField.getPartialName(),value);
							continue;
						}else if(key.equalsIgnoreCase(PrintConstants.DEM165)){
							CachedDropDownValues cdv = new CachedDropDownValues();
							
							String value =(String)answerMap.get(key);
							if(value != null && !value.isEmpty()){
								value =cdv.getCntyDescTxt(value);
							}
							
							setField(pdfField,pdfField.getPartialName(),value);
							continue;
						}else if(key.contains("NBS136") && pageForm.getFormName().equals(NEDSSConstants.CDC_FIELD_RECORD_FORM )){
							String value =(String)answerMap.get(key);
							setField(pdfField,key,value,false);
							continue;						
						}else if(answerMap.containsKey(key)){
							String value =(String)answerMap.get(key);
							if(value != null){
							setField(pdfField,pdfField.getPartialName(),value);
							}
							continue;
						}
					}
					else if (key.endsWith(PrintConstants._CD)){
						key = key.substring(0, key.length()-3);	
						logger.debug("key is "+key);
						
						NbsQuestionMetadata metaData = (NbsQuestionMetadata) questionMap.get(key);
						String value =(String)answerMap.get(key);
						if(key.equalsIgnoreCase(PrintConstants.NBS179)){
							if(value == null || value.length() ==0 && answerMap.containsKey(PrintConstants.NBS111)){
								value =(String)answerMap.get(PrintConstants.NBS111);
								metaData = (NbsQuestionMetadata) questionMap.get(PrintConstants.NBS111);
							}
						}
						if(key.equalsIgnoreCase(PrintConstants.NBS111)){
							if(value == null || value.length() ==0 && answerMap.containsKey(PrintConstants.INV107)){
								value =(String)answerMap.get(PrintConstants.INV107);
								metaData = (NbsQuestionMetadata) questionMap.get(PrintConstants.INV107);
							}
						}
						
						if (metaData!=null && metaData.getCodeSetNm() != null && value!=null){
							value =cache.getDescForCode(metaData.getCodeSetNm(), value);
						}else{
							 value =(String)answerMap.get(key);
						}
						setField(pdfField,pdfField.getPartialName(),value);
						continue;
					}else if (key.endsWith(PrintConstants._RCD)){
						key = key.substring(0, key.length()-4);	
						logger.debug("RCD key is "+key);
						
						NbsQuestionMetadata metaData = (NbsQuestionMetadata) questionMap.get(key);
						String value =(String)answerMap.get(key);
						
						if (metaData!=null && metaData.getCodeSetNm() != null && value!=null){
							value =cache.getCdForCodedResultDescTxt( value,metaData.getCodeSetNm());
						}else{
							 value =(String)answerMap.get(key);
						}
						setField(pdfField,pdfField.getPartialName(),value);
						continue;
					}
					else if(pdfField.getPartialName().equalsIgnoreCase(PrintConstants.PATIENT_COMPLETE_NAME)){
						String name="";
						if(answerMap.get(PrintConstants.LAST_NAME)!=null && !answerMap.get(PrintConstants.LAST_NAME).toString().trim().equals("")){
							name=name + " " +(String)answerMap.get(PrintConstants.LAST_NAME );	
						}
						if(answerMap.get(PrintConstants.FIRST_NAME)!=null && !answerMap.get(PrintConstants.FIRST_NAME).toString().trim().equals("")){
							name=name + " " +(String)answerMap.get(PrintConstants.FIRST_NAME );	
						}
						if(answerMap.get(PrintConstants.MIDDLE_NAME)!=null && !answerMap.get(PrintConstants.MIDDLE_NAME).toString().trim().equals("")){
							name=name + " " +(String)answerMap.get(PrintConstants.MIDDLE_NAME );	
						}
						setField(pdfField,PrintConstants.PATIENT_COMPLETE_NAME,name);
						continue;
					}
					else if(pdfField.getPartialName().equalsIgnoreCase(PrintConstants.PATIENT_COMPLETE_PHONE)){
						String phone=(String)answerMap.get(PrintConstants.PATIENT_PHONE);
						if(answerMap.get(PrintConstants.PATIENT_EXTN)!=null && !answerMap.get(PrintConstants.PATIENT_EXTN).toString().trim().equals("")){
							phone=phone+PrintConstants.PATIENT_EXTN_CONST+ (String)answerMap.get(PrintConstants.PATIENT_EXTN);	
						}
						setField(pdfField,PrintConstants.PATIENT_COMPLETE_PHONE,phone);
						continue;
					}else if(pdfField.getPartialName().equalsIgnoreCase(PrintConstants.STREET_ADDRESS)){
						String streetAddress1=(String)answerMap.get(PrintConstants.DEM159);
						if(answerMap.get(PrintConstants.DEM160)!=null && !answerMap.get(PrintConstants.DEM160).toString().trim().equals("")){
							streetAddress1=streetAddress1+" "+ (String)answerMap.get(PrintConstants.DEM160);	
						}
						setField(pdfField,PrintConstants.STREET_ADDRESS,streetAddress1);
						continue;
					}else{
						logger.debug("Not mapped\n"+pdfField.getPartialName());
					}						
				}
			}
			}
		} catch (IOException e) {
			logger.error("fillPDForm: Error while filling up the form and IOException raises: " + e);
			throw new Exception(e);
		}
	}//end	
	
	private static Map<String, String> processDrugValues(
			Map<String, String> mappedInvRepeatValues) {
		Map<String, String> returnMap = new HashMap();
		for(String key : mappedInvRepeatValues.keySet()){
			
		if(key.contains("STD115")){
			String[] keyToken = key.split("_");
			String endsWith ="";
			if(keyToken.length>1) endsWith= keyToken[1];
			String drug = mappedInvRepeatValues.get(key);
			if(drug.equals(PrintConstants.STD116_COCAINE)){
				returnMap.put(PrintConstants.STD116_COCAINE, mappedInvRepeatValues.get("STD116_" + endsWith));
			}else if(drug.equals(PrintConstants.STD116_CRACK)){
				returnMap.put(PrintConstants.STD116_CRACK, mappedInvRepeatValues.get("STD116_" + endsWith));
			}else if(drug.equals(PrintConstants.STD116_EDM)){
				returnMap.put(PrintConstants.STD116_EDM, mappedInvRepeatValues.get("STD116_" + endsWith));
			}else if(drug.equals(PrintConstants.STD116_HEROIN)){
				returnMap.put(PrintConstants.STD116_HEROIN, mappedInvRepeatValues.get("STD116_" + endsWith));
			}else if(drug.equals(PrintConstants.STD116_METH)){
				returnMap.put(PrintConstants.STD116_METH, mappedInvRepeatValues.get("STD116_" + endsWith));
			}else if(drug.equals(PrintConstants.STD116_NITRATES)){
				returnMap.put(PrintConstants.STD116_NITRATES, mappedInvRepeatValues.get("STD116_" + endsWith));
			}else if(drug.equals(PrintConstants.STD116_NONE)){
				returnMap.put(PrintConstants.STD116_NONE, mappedInvRepeatValues.get("STD116_" + endsWith));
			}else if(drug.contains(PrintConstants.OTH)){
//				String[] tokens = drug.split(":");
//				if(tokens != null && tokens.length>1)
				returnMap.put(PrintConstants.Drugs_Oth,drug);
			}
			
		}
				
		}
		return returnMap;
	}
	public static void populateMappedEtc(PageProxyVO proxyVO, Map<String, String> mappedEtc) {
		PageActProxyVO actProxyVO = (PageActProxyVO) proxyVO;
		if(actProxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().getInitFollUp()!=null && actProxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().getInitFollUp().equals(PrintConstants.INIT_FOLL_INSUFF_INFO)){
			mappedEtc.put("IxOnly", "Yes");
		}else{
			mappedEtc.put("IxOnly", "No");
		}
		
		
	}


	private static void getMappedValues(PageForm pageForm,PageProxyVO proxyVO,Map<String, String> mappedLabValues,Map<String, String> mappedTreatments,Map<String, String>  mappedDiagnosis,Map<String, String> mappedReferralBasis,Map<String, String>  mappedInterviewRecord,Map<String, String> mappedOOJ,Map<String, String> mappedMorbValues, Long sourceUid,HttpServletRequest req,  Map<String, String> mappedEntityValues) throws Exception 
			{
		Long uid =new Long(pageForm.getCoinfCondInvUid());
		PageProxyVO coproxyVO=null;
		if(uid != 0){
			try {
				coproxyVO = (PageActProxyVO) getPrintProxyObject(
						String.valueOf(uid), req.getSession());
			} catch (Exception e) {
				logger.debug("Exception in getMappedValues- Coinfection - getPrintProxyObject " + e.getMessage());
			}
		}
		if(pageForm.getFormName().equals(NEDSSConstants.CDC_INTERVIEW_RECORD_FORM )){
			populateMappedCase(mappedDiagnosis,proxyVO,1, pageForm);
			if(coproxyVO != null) populateMappedCase(mappedDiagnosis,coproxyVO,2, pageForm);
			  try {
				  //For avoiding process the same lab report twice when 2 conditions have the same lab report
				  ArrayList<String> labsProcessed = new ArrayList<String>();
				  //If both the conditions belong to one program area(STd / HIV) 
				populateLabValuesWithProgramArea(sourceUid, proxyVO, mappedLabValues, labsProcessed);
				 
				 if(coproxyVO != null) populateLabValuesWithProgramArea(sourceUid, coproxyVO, mappedLabValues, labsProcessed);
			} catch (Exception e) {
				logger.debug("Exception in getMappedValues- CIR - populateLabValuesWithProgramArea " + e.getMessage());
			}
			 
			  
			  populateMappedConditions(mappedDiagnosis,(PageActProxyVO)proxyVO,req.getSession(),1);
			  
			  
			  if(coproxyVO != null) populateMappedConditions(mappedDiagnosis,(PageActProxyVO)coproxyVO,req.getSession(),2);
			 
			  populatePlaceRecord((PageActProxyVO)proxyVO,mappedDiagnosis,req.getSession());
			  
			  getTreatments(proxyVO, mappedTreatments, req, coproxyVO, true);
			  
		}else {
			mappedDiagnosis.put("NBS150_1", proxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().getSurvProvDiagnosis());
			if(coproxyVO != null)mappedDiagnosis.put("NBS150_2",  coproxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().getSurvProvDiagnosis());
	
			
			try {
				int labNo= 1;
				
//				if(proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getReferralBasisCd()!=null &&
//						proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getReferralBasisCd().equalsIgnoreCase(MessageConstants.REFERRAL_CODE_FOR_MORB)){
					 populateMorbidityValues(sourceUid,proxyVO,mappedMorbValues,labNo);
					 if(coproxyVO != null) populateMorbidityValues(sourceUid,coproxyVO,mappedMorbValues,labNo);
					 
					 Collection<Object>  labColl= null;
					 labColl =getLabReportCollection((PageActProxyVO)proxyVO);
					 if(((PageActProxyVO) proxyVO).getTheLabReportSummaryVOCollection() != null){
					 labColl.addAll(((PageActProxyVO)proxyVO).getTheLabReportSummaryVOCollection());
					 }
					 populateLabValues(sourceUid, labColl, mappedLabValues,labNo);
				
					 labNo = Integer.parseInt(mappedLabValues.get("labIndex"));
					Collection<Object>  coinfLabColl= null;
					if(labNo< 5 &&coproxyVO != null){	
						 coinfLabColl =getLabReportCollection((PageActProxyVO)coproxyVO);
						 if(((PageActProxyVO) coproxyVO).getTheLabReportSummaryVOCollection() != null){
							 coinfLabColl.addAll(((PageActProxyVO)coproxyVO).getTheLabReportSummaryVOCollection());
						 }
						 populateLabValues(sourceUid,coinfLabColl,mappedLabValues,labNo);
					}
				
					populateOtherInfoFromPrintVO(sourceUid, pageForm,(PageActProxyVO) proxyVO,mappedLabValues, req);
					
				
			} catch (Exception e) {
				logger.debug("Exception in getMappedValues- CFR - populateLabValues " + e.getMessage());
			}
			
			//map referral basis
			try {
				populateReferralBasisValues(proxyVO,mappedReferralBasis,1,req.getSession());
				if(coproxyVO != null)	populateReferralBasisValues(coproxyVO,mappedReferralBasis,2,req.getSession());
			} catch (Exception e) {
				logger.debug("Exception in getMappedValues- CFR - populateReferralBasisValues " + e.getMessage());
			}
			
			
			populateInterviewRecord(proxyVO,mappedInterviewRecord,1,req.getSession(), pageForm);
			if(coproxyVO != null)populateInterviewRecord(coproxyVO,mappedInterviewRecord,2,req.getSession(),pageForm);
			
			if(pageForm.getFormName().equals(NEDSSConstants.CDC_FIELD_RECORD_FORM)){
			getTreatments(proxyVO, mappedTreatments, req, coproxyVO, true);
			}else{
				getTreatments(proxyVO, mappedTreatments, req, coproxyVO, false);
			}
		}

		
	}
	
	private static Collection<Object>  getLabReportCollection(PageActProxyVO actProxyVO) {
		
		Collection<Object>  returnColl = new ArrayList<Object>();
		Collection<Object> coll = actProxyVO
				.getTheMorbReportSummaryVOCollection();
		if (coll != null) {
			Iterator<Object> it = coll.iterator();
			while (it.hasNext()) {
				MorbReportSummaryVO morbReportSummaryVO = (MorbReportSummaryVO) it
						.next();
				if(morbReportSummaryVO.getTheLabReportSummaryVOColl() != null)  returnColl.addAll(morbReportSummaryVO.getTheLabReportSummaryVOColl());
			}
		}
		return returnColl;
	}
	private static void getTreatments(PageProxyVO proxyVO,
			Map<String, String> mappedTreatments, HttpServletRequest req,
			PageProxyVO coproxyVO, Boolean withProvider) {
		try {
			populateTreatmentValuesWithProvider(proxyVO,mappedTreatments,1,req.getSession(),withProvider);
			int rxIndex = Integer.parseInt(mappedTreatments.get("rxindex"));
			if(coproxyVO != null) populateTreatmentValuesWithProvider(coproxyVO,mappedTreatments,rxIndex,req.getSession(),withProvider);
		} catch (Exception e) {
			logger.debug("Exception in getMappedValues- CIR - getTreatments " + e.getMessage());
		}
	}
	
	public static Map<Object, Object> getContactQuestionMap() {
		return contactQuestionMap;
	}
	public static void setContactQuestionMap(Map<Object, Object> contactQuestionMap) {
		PagePrintUtil.contactQuestionMap = contactQuestionMap;
	}
	public static Map<Object, Object> getInterviewQuestionMap() {
		return interviewQuestionMap;
	}
	public static void setInterviewQuestionMap(
			Map<Object, Object> interviewQuestionMap) {
		PagePrintUtil.interviewQuestionMap = interviewQuestionMap;
	}
	/**
	 * Return a map of the entity fields. Specify the keys for the field. A null key indicates the field is not needed.
	 * @param proVO - personVO
	 * @param nameKey - key to store full name under
	 * @param includePrefix - if true include prefix in name
	 * @param streetAdrKey - key to use to store street address
	 * @param cityAdrKey - key for City
	 * @param stateAdrKey - key for State
	 * @param zipAdrKey - key for zip
	 * @param wkPhoneKey - key for work phone
	 * @param emailKey - key for email
	 * @return
	 */
	public static Map<String, String> putEntityNameAndAddressIntoMap(PersonVO proVO, String nameKey, boolean includePrefix, String streetAdrKey, String cityAdrKey, String stateAdrKey, String zipAdrKey, String wkPhoneKey, String emailKey) {
		
		Map<String, String> returnMap = new HashMap<String, String>();
		
		PersonDT personDT = null;
		PostalLocatorDT postalDT = null;
		TeleLocatorDT teleDT = null;

		StringBuffer stBuff = new StringBuffer("");
		personDT = proVO.getThePersonDT();

		if (proVO.getThePersonNameDTCollection() != null) {

			Iterator personNameIt = proVO.getThePersonNameDTCollection()
					.iterator();
			while (personNameIt.hasNext()) {
				PersonNameDT personNameDT = (PersonNameDT) personNameIt.next();
				if (personNameDT.getNmUseCd().equalsIgnoreCase("L")) {
					if (includePrefix)
						stBuff.append((personNameDT.getNmPrefix() == null) ? ""
							: (personNameDT.getNmPrefix() + " "));
					stBuff.append((personNameDT.getFirstNm() == null) ? ""
							: (personNameDT.getFirstNm() + " "));
					stBuff.append((personNameDT.getLastNm() == null) ? ""
							: (personNameDT.getLastNm()));
					stBuff.append((personNameDT.getNmSuffix() == null) ? ""
							: (", " + personNameDT.getNmSuffix()));
					stBuff.append(
							(personNameDT.getNmDegree() == null) ? ""
									: (", " + personNameDT.getNmDegree()));
					if (nameKey != null)
						returnMap.put(nameKey, stBuff.toString());
				}
			}
		}

		if (proVO.getTheEntityLocatorParticipationDTCollection() != null) {
			Iterator entIt = proVO
					.getTheEntityLocatorParticipationDTCollection().iterator();
			while (entIt.hasNext()) {
				EntityLocatorParticipationDT entityDT = (EntityLocatorParticipationDT) entIt
						.next();
				if (entityDT != null) {
					if (entityDT.getCd() != null
							&& entityDT.getCd().equalsIgnoreCase("O")
							&& entityDT.getClassCd() != null
							&& entityDT.getClassCd().equalsIgnoreCase("PST")
							&& entityDT.getRecordStatusCd() != null
							&& entityDT.getRecordStatusCd().equalsIgnoreCase(
									"ACTIVE")
							&& entityDT.getUseCd().equalsIgnoreCase("WP")) {
						postalDT = entityDT.getThePostalLocatorDT();
						stBuff = new StringBuffer("");
						stBuff.append((postalDT.getStreetAddr1() == null) ? ""
								: (postalDT.getStreetAddr1()));
						stBuff.append((postalDT.getStreetAddr2() == null) ? ""
								: (", " + postalDT.getStreetAddr2()));
						if (streetAdrKey != null)
							returnMap.put(streetAdrKey, stBuff.toString());
						if (cityAdrKey != null) {
							String cityStr = postalDT.getCityDescTxt() == null ? ""
								: (", " + postalDT.getCityDescTxt());
							returnMap.put(cityAdrKey, cityStr);
							
						}
						if (stateAdrKey != null) {
								String stateStr = postalDT.getStateCd() == null ? "" 
										: getStateDescTxt(postalDT.getStateCd());
								returnMap.put(stateAdrKey, stateStr);
						}
						if (zipAdrKey != null)
							returnMap.put(zipAdrKey, postalDT.getZipCd() == null ? ""
									: postalDT.getZipCd());

					} //address fields

					if (entityDT.getClassCd() != null) {
						if (entityDT.getClassCd() != null
								&& entityDT.getClassCd().equalsIgnoreCase(
										"TELE")
								&& entityDT.getRecordStatusCd() != null
								&& entityDT.getRecordStatusCd()
										.equalsIgnoreCase("ACTIVE")
								&& entityDT.getCd() != null
								&& entityDT.getCd().equalsIgnoreCase("PH")
								&& entityDT.getUseCd() != null
								&& entityDT.getUseCd().equalsIgnoreCase("WP")) {
							teleDT = entityDT.getTheTeleLocatorDT();

							if (emailKey != null)
								returnMap.put(emailKey, teleDT.getEmailAddress() == null ? ""
											: (teleDT.getEmailAddress()));
							if (wkPhoneKey != null) {
								stBuff = new StringBuffer("");
								stBuff.append((teleDT.getPhoneNbrTxt() == null) ? ""
											: (teleDT.getPhoneNbrTxt() + " "));
								String ext = "";
								if(teleDT.getExtensionTxt()!=null && !teleDT.getExtensionTxt().equals("0.0")){
									ext = teleDT.getExtensionTxt().replace(".0", "");
									stBuff.append("Ext." + ext);
								}
								returnMap.put(wkPhoneKey, stBuff.toString());
							
							}
						}
					}//phone get class cd
				}
			}
		}
		return returnMap;
	}
	private static String getStateDescTxt(String sStateCd) {

		String desc = "";
		if (sStateCd != null && !sStateCd.trim().equals("")) {
			CachedDropDownValues srtValues = new CachedDropDownValues();
			TreeMap treemap = srtValues.getStateCodes2("USA");
			if (treemap != null) {
				if (sStateCd != null && treemap.get(sStateCd) != null) {
					desc = (String) treemap.get(sStateCd);
				}
			}
		}

		return desc;
	}
}
