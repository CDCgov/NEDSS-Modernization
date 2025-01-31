package gov.cdc.nedss.webapp.nbs.action.pam.RVCT;

import gov.cdc.nedss.act.publichealthcase.dt.PublicHealthCaseDT;
import gov.cdc.nedss.page.ejb.pageproxyejb.dt.NBSNoteDT;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.vo.PamProxyVO;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.NbsQuestionMetadata;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.StringUtils;
import gov.cdc.nedss.webapp.nbs.action.pam.PamClientVO.PamClientVO;
import gov.cdc.nedss.webapp.nbs.action.pam.util.PamConstants;
import gov.cdc.nedss.webapp.nbs.action.pam.util.PamLoadUtil;
import gov.cdc.nedss.webapp.nbs.action.pam.util.PamPrintUtil;
import gov.cdc.nedss.webapp.nbs.action.util.FileUploadUtil;
import gov.cdc.nedss.webapp.nbs.form.pam.PamForm;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.QuestionsCache;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.exolab.castor.util.Iterator;

import com.justformspdf.pdf.Form;
import com.justformspdf.pdf.FormCheckbox;
import com.justformspdf.pdf.FormText;
import com.justformspdf.pdf.PDF;
import com.justformspdf.pdf.PDFReader;

/**
 * RVCTPrintUtil is a Java PDF Print Utility that uses AcroFields to populate PDF 
 * @author Narendra Mallela
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: Computer Sciences Corporation</p>
 * RVCTPrintUtil.java
 * Jan 28, 2008
 * @version 1.1
 */
public class RVCTPrintUtil extends PamPrintUtil {
	
	static final LogUtils logger = new LogUtils(RVCTPrintUtil.class.getName());
	private final static String pdfFileName = "CDCTuberculosisForm.pdf";
	public static PropertyUtil propertyUtil= PropertyUtil.getInstance();
	private static Map<Object,Object> questionMap = (Map<Object, Object> )QuestionsCache.getQuestionMap().get(NBSConstantUtil.INV_FORM_RVCT);

	/**
	 * printForm checks for InvestigationFormCode and calls the PAM Specific Util classes to handle Print
	 * @param form
	 * @param req
	 * @param res
	 */
	public static void printForm(PamForm pamForm, HttpServletRequest req, HttpServletResponse res) {
		
		FileInputStream fis = null;
		
		try {			
			fis = new FileInputStream(propertiesDirectory + pdfFileName);
			PDFReader reader = new PDFReader(fis);
			PDF pdf = new PDF(reader);
			pdf.lk(licKey);
			Form pdfForm = pdf.getForm();
			setReadOnly(pdfForm);	
			NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) req.getSession().getAttribute("NBSSecurityObject");			
			PamProxyVO proxyVO = viewPrintLoadUtil(pamForm, req);			
			//demographic Answers
			setRVCTDemographicAnswers(pdfForm, pamForm);				
			//RVCT Answers
			setRVCTAnswers(pdfForm, pamForm, nbsSecurityObj);
			pamForm.getPamClientVO().setOldPamProxyVO(proxyVO);
			//render
			pdf.render();
			res.setContentType("application/pdf");
			pdf.writeTo(res.getOutputStream());	
			
			
		} catch (Exception e) {
			logger.error(e.toString());
			req.getSession().setAttribute("error", "<b>There is an error while printing the RVCT Form: </b> <br/><br/>");				
			try {
				res.sendRedirect("/nbs/error");
			} catch (IOException e1) {
				logger.error("Error while redirecting RVCT Print Error: " + e1.toString());
				e1.printStackTrace();
			}
		} finally {		
			try {
				if(fis != null) 
					fis.close();
			} catch (IOException e2) {
				logger.error("Error while closing FileInputStream for RVCT Print : " + e2.toString());
			}
		}
	}	
	
	public static void printNotes(PamForm pamForm, HttpServletRequest request, HttpServletResponse res)throws Exception  {
		PamProxyVO proxyVO =  new PamProxyVO();
		String sPublicHealthCaseUID = (String)request.getSession().getAttribute("DSInvUid");
		try{
			  proxyVO = PamLoadUtil.getProxyObject(sPublicHealthCaseUID, request.getSession());
		  }catch(Exception e){
			  logger.error("Error while getting the PamAssociations" + e.getMessage());
			  throw new ServletException();
		  }
	      request.setAttribute("mode", request.getParameter("mode"));
		  ArrayList<Object> noteDTs = (ArrayList<Object> ) ((PamProxyVO)proxyVO).getNbsNoteDTColl();				
		  if(noteDTs != null && noteDTs.size() > 0){		
			  NBSSecurityObj nbsSecurityObj = (NBSSecurityObj)request.getSession().getAttribute("NBSSecurityObject");
			  FileUploadUtil fileUtil = new FileUploadUtil();
		  	  ArrayList<Object> nNotesDTs =  fileUtil.updateNotesForPrivateInd(noteDTs,nbsSecurityObj);		  	  
		  	  fileUtil.updateNotesForView(nNotesDTs);		  	
			  request.setAttribute("nbsNotes", nNotesDTs);
		  } 
		    PublicHealthCaseDT phcDT = (PublicHealthCaseDT)proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT();
		    request.setAttribute("createdDate", StringUtils.formatDate(phcDT.getAddTime()));
			request.setAttribute("createdBy", phcDT.getAddUserName());

			request.setAttribute("updatedDate", StringUtils.formatDate(phcDT.getLastChgTime()));
			request.setAttribute("updatedBy", phcDT.getLastChgUserName());
	}
	
	/**
	 * RVCTDemographicAnswers for Tuberculosis Form
	 * @param pdfForm
	 * @param pamForm
	 * @throws Exception
	 */
	public static void setRVCTDemographicAnswers(Form pdfForm, PamForm pamForm) throws Exception {
		logger.debug("RVCT setDemographicAnswers - start");
		logger.debug("RVCT setDemographicAnswers - name");
		Map<Object, Object>  answerMap = pamForm.getPamClientVO().getAnswerMap(); 
		PamClientVO clientVO = pamForm.getPamClientVO();
		String value = null;		
		String lastNm = answerMap.get(PamConstants.LAST_NM) == null ? "" : (String) answerMap.get(PamConstants.LAST_NM);
		String firstNm = answerMap.get(PamConstants.FIRST_NM) == null ? "" : (String) answerMap.get(PamConstants.FIRST_NM);
		String middleNm = answerMap.get(PamConstants.MIDDLE_NM) == null ? "" : (String) answerMap.get(PamConstants.MIDDLE_NM);
		StringBuffer name = new StringBuffer();
		if(lastNm != null && lastNm.length() > 0) {
			name.append(lastNm);
		}
		if(firstNm != null && firstNm.length() > 0) {
			name.append(", ").append(firstNm);
		}		
		if(middleNm != null && middleNm.length() > 0) {
			String mi = middleNm.substring(0,1).toUpperCase();
			name.append(" ").append(mi).append(".");
		}
		if(name.toString().length() > 0) {
			String[] names = {"Name","Name_1","Name_2","Name_3","Name_4","Name_5"};
			if(names != null && names.length > 0) {
				for(int i=0; i< names.length; i++) {
					answerPlainTxt(names[i], name.toString(), pdfForm);
				}
			}			
		}
		logger.debug("RVCT setDemographicAnswers - address");
		String addString = getAddressString(answerMap);
		if(addString != null && addString.length() > 0) {
			String[] addresses = {"Address","Address_1","Address_2"};
			if(addresses != null && addresses.length > 0) {
				for(int i=0; i< addresses.length; i++) {
					answerPlainTxt(addresses[i], addString, pdfForm);
				}
			}			
		}
		
		String city = answerMap.get(PamConstants.CITY) == null ? "" : (String) answerMap.get(PamConstants.CITY);
		if(city != null && city.length() > 0) {
			FormText DEM161 = (FormText) pdfForm.getElement(PamConstants.CITY);
			DEM161.setValue(addEmptySpaces(city));
		}
		String county = answerMap.get(PamConstants.COUNTY) == null ? "" : (String) answerMap.get(PamConstants.COUNTY);
		if(county != null && county.length() > 0) {
			FormText DEM165 = (FormText) pdfForm.getElement(PamConstants.COUNTY);
			String state = getVal(answerMap.get(PamConstants.STATE));
			TreeMap<?,?> map = cache.getCountyCodes(state);
			String countyDesc = getVal(map.get(county));
			if(countyDesc != "" && countyDesc.trim().length() > 0) {
				if(countyDesc.indexOf("County") != -1)
					countyDesc = countyDesc.substring(0, countyDesc.indexOf("County")-1);
				DEM165.setValue(addEmptySpaces(countyDesc));
			}
		}
		
		String zipCd = answerMap.get(PamConstants.ZIP) == null ? "" : (String) answerMap.get(PamConstants.ZIP);
		if(zipCd != null && zipCd.length() > 0) {
			
			String[] zips = {PamConstants.ZIP,"DEM163_1","DEM163_2"};
			if(zips != null && zips.length > 0) {
				for(int i=0; i< zips.length; i++) {
					answerPlainTxt(zips[i], zipCd, pdfForm);
				}
			}			
			FormText DEM163_3 = (FormText) pdfForm.getElement("DEM163_3");
			FormText DEM163_4 = (FormText) pdfForm.getElement("DEM163_4");
			if(zipCd.indexOf("-") == -1) {
				DEM163_3.setValue(addEmptySpaces(zipCd));
			} else {
				StringTokenizer st = new StringTokenizer(zipCd, "-");
				DEM163_3.setValue(addEmptySpaces(st.nextToken()));
				DEM163_4.setValue(addEmptySpaces(st.nextToken()));				
			}
		}
		logger.debug("RVCT setDemographicAnswers - dob");
		value = readValue(PamConstants.DOB, answerMap);
		if (value != null && value.trim().length() > 0) {
			answerYrFormat1(PamConstants.DOB, value, pdfForm);
		}	
		logger.debug("RVCT setDemographicAnswers - ethnic");
		String[] chkBoxAnswers = {PamConstants.ETHNICITY, PamConstants.BIRTH_SEX};
		for(int i=0; i< chkBoxAnswers.length; i++) {
			value = readValue(chkBoxAnswers[i], answerMap);
			answerCheckBox(chkBoxAnswers[i], value, pdfForm);
		}		
		logger.debug("RVCT setDemographicAnswers - race");
		FormCheckbox race_1 = (FormCheckbox) pdfForm.getElement(PamConstants.RACE + "_1");
		FormCheckbox race_2 = (FormCheckbox) pdfForm.getElement(PamConstants.RACE + "_2");
		FormCheckbox race_3 = (FormCheckbox) pdfForm.getElement(PamConstants.RACE + "_3");
		FormCheckbox race_4 = (FormCheckbox) pdfForm.getElement(PamConstants.RACE + "_4");
		FormCheckbox race_5 = (FormCheckbox) pdfForm.getElement(PamConstants.RACE + "_5");

		if(clientVO.getAmericanIndianAlskanRace() == 1) race_1.check(true);				
		if(clientVO.getAsianRace() == 1) race_2.check(true);
		if(clientVO.getAfricanAmericanRace() == 1) race_3.check(true);
		if(clientVO.getHawaiianRace() == 1) race_4.check(true);
		if(clientVO.getWhiteRace() == 1) race_5.check(true);
		
		String[] asianList = clientVO.getAnswerArray(PamConstants.DETAILED_RACE_ASIAN);
		if(asianList != null && asianList.length > 0) {
			String asianDesc = PamPrintUtil.getRaceDesc(asianList, "2028-9");
			if(asianDesc != null && asianDesc.length() > 0) {
				FormText asian_detail = (FormText) pdfForm.getElement(PamConstants.DETAILED_RACE_ASIAN);
				asian_detail.setValue(asianDesc);
			}
		}
		String[] hawaiiList = clientVO.getAnswerArray(PamConstants.DETAILED_RACE_HAWAII);
		if(hawaiiList != null && hawaiiList.length > 0) {
			String hawDesc = PamPrintUtil.getRaceDesc(hawaiiList,"2076-8");
			if(hawDesc != null && hawDesc.length() > 0) {
				FormText hawaii_detail = (FormText) pdfForm.getElement(PamConstants.DETAILED_RACE_HAWAII);
				hawaii_detail.setValue(hawDesc);
			}			
		}
		//12.Country of Birth Specify.....(Other than US)
		String cdDescTxt = retrieveDescByCodesetNm(questionMap, answerMap, "TUB276");
		FormText TUB276 = (FormText) pdfForm.getElement("TUB276");
		TUB276.setValue(cdDescTxt);
		logger.debug("RVCT setDemographicAnswers - done");
	}//end		
	
	/**
	 * Tuberculosis specific PDF Answers
	 * @param pdfForm
	 * @param form
	 * @param nbsSecurityObj
	 * @throws Exception
	 */
	public static void setRVCTAnswers(Form pdfForm, PamForm form, NBSSecurityObj nbsSecurityObj) throws Exception {
		logger.debug("setRVCTAnswers: start");
		boolean hivSecurity = Boolean.valueOf((String)form.getSecurityMap().get("TBHIVSecurity")).booleanValue();
		Map<Object, Object>  answerMap = form.getPamClientVO().getAnswerMap();
		String value = null;

		logger.debug("setRVCTAnswers: calling handleRepeatQuestions");
		//Repeat Questions
		handleRepeatQuestions(pdfForm, form, answerMap);
		
		logger.debug("setRVCTAnswers: plain text");
		//Plain Text answers
		String[] plainText = {"TUB152","TUB168","TUB189","TUB191","TUB217","TUB219","TUB223","TUB236","TUB263","TUB265"};
		if(plainText != null && plainText.length > 0) {
			for(int i=0; i< plainText.length; i++) {
				logger.debug("ques="+plainText[i]);
				value = readValue(plainText[i], answerMap);
				answerPlainTxt(plainText[i], value, pdfForm);
			}
		}
		logger.debug("setRVCTAnswers: GridBox");
		//GridBox answers
		String[] gridAnswers = {"DEM161","INV167","TUB149","TUB155","TUB156","TUB193","TUB270","TUB271"};
		if (gridAnswers != null && gridAnswers.length > 0) {
			for (int i = 0; i < gridAnswers.length; i++) {
				logger.debug("ques="+gridAnswers[i]);
				if (!hivSecurity
						&& (gridAnswers[i].equals("TUB155") || gridAnswers[i]
								.equals("TUB156"))) {
				} else {
					value = readValue(gridAnswers[i], answerMap);
					answerGridBox(gridAnswers[i], value, pdfForm);
				}
			}
		}
		logger.debug("setRVCTAnswers: MM/DD/YYYY date");
		// MM/DD/YYYY answers
		String[] mmddyyyyAnswers = {"INV111","INV121","TUB110","DEM115","TUB123","INV146","TUB121","TUB124","TUB127","TUB131","TUB133","TUB136","TUB139","TUB148","TUB151","TUB170","TUB195","TUB221","TUB232","TUB241"};
		if(mmddyyyyAnswers != null && mmddyyyyAnswers.length > 0) {
			for(int i=0; i< mmddyyyyAnswers.length; i++) {
				logger.debug("ques="+mmddyyyyAnswers[i]);
				value = readValue(mmddyyyyAnswers[i], answerMap);
				answerYrFormat1(mmddyyyyAnswers[i], value, pdfForm);
			}
		}
		logger.debug("setRVCTAnswers: MM/YYYY date");
		// MM/YYYY answers
		String[] mmyyyyAnswers = {"TUB273"};
		if(mmyyyyAnswers != null && mmyyyyAnswers.length > 0) {
			for(int i=0; i< mmyyyyAnswers.length; i++) {
				logger.debug("ques="+mmyyyyAnswers[i]);
				value = readValue(mmyyyyAnswers[i], answerMap);
				answerYrFormat2(mmyyyyAnswers[i], value, pdfForm);
			}
		}
		logger.debug("setRVCTAnswers: YYYY date");
		// YYYY answers
		String[] yyyyAnswers = {"TUB112", "TUB110Y_1", "TUB110Y_2"};		
		for(int i=0; i< yyyyAnswers.length; i++) {
			logger.debug("ques="+yyyyAnswers[i]);
			//For TUB110_1 and TUB110_2, displaying year part of TUB110 (DateCounted)
			if(yyyyAnswers[i] == "TUB110Y_1" || yyyyAnswers[i] == "TUB110Y_2") {
				value = readValue("TUB110", answerMap);
				if(value != null)
					value = value.substring(6);
			} else {
				value = readValue(yyyyAnswers[i], answerMap);
			}
			answerYrFormat3(yyyyAnswers[i], value, pdfForm);
		}
		logger.debug("setRVCTAnswers: checkbox");
		//Check Boxes
		String[] chkBoxAnswers = {"DEM114","DEM155","DEM237","TUB108","TUB111","TUB277","TUB113","TUB117","INV145","TUB119","TUB120","TUB122","TUB125","TUB126","TUB130","TUB134","TUB135","TUB137","TUB140","TUB141","TUB142","TUB143","TUB144","TUB145","TUB146","TUB147","TUB150","TUB153","TUB154","TUB157","TUB158","TUB159","TUB160","TUB161","TUB162","TUB163","TUB164","TUB165","TUB166","TUB169","TUB171","TUB172","TUB173","TUB174","TUB175","TUB176","TUB177","TUB178","TUB179","TUB180","TUB181","TUB182","TUB183","TUB184","TUB185","TUB186","TUB187","TUB188","TUB190","TUB192","TUB194","TUB196","TUB198","TUB199","TUB200","TUB201","TUB202","TUB203","TUB204","TUB205","TUB206","TUB207","TUB208","TUB209","TUB210","TUB211","TUB212","TUB213","TUB214","TUB215","TUB216","TUB218","TUB220","TUB222","TUB224","TUB225","TUB226","TUB233","TUB234","TUB238","TUB240","TUB242","TUB244","TUB245","TUB246","TUB247","TUB248","TUB249","TUB250","TUB251","TUB252","TUB253","TUB254","TUB255","TUB256","TUB257","TUB258","TUB259","TUB260","TUB261","TUB262","TUB264"};
		if(chkBoxAnswers != null && chkBoxAnswers.length > 0) {
			for (int i = 0; i < chkBoxAnswers.length; i++) {
				logger.debug("ques="+chkBoxAnswers[i]);
				if (!hivSecurity && (chkBoxAnswers[i].equals("TUB154"))) {
				} else {
					value = readValue(chkBoxAnswers[i], answerMap);
					answerCheckBox(chkBoxAnswers[i], value, pdfForm);
				}
			}
		}
		logger.debug("setRVCTAnswers: text area");
		//Text Areas
		String[] textAreaAnswers = {"INV167","TUB270","TUB271"};
		if(textAreaAnswers != null && textAreaAnswers.length > 0) {
			for(int i=0; i< textAreaAnswers.length; i++) {
				logger.debug("ques="+textAreaAnswers[i]);
				value = readValue(textAreaAnswers[i], answerMap);
				answerTxtArea(textAreaAnswers[i], value, 110, pdfForm);
			}
		}
		logger.debug("setRVCTAnswers: multisel");
		//Multi Selects
		ArrayList<Object> answersNotFound = null;
		String [] pamMultiSelects = {"TUB114","TUB119","TUB129","TUB167","TUB228","TUB229","TUB230","TUB235","TUB237"};
		Map<Object, Object>  arrayAnsMap = form.getPamClientVO().getArrayAnswerMap();
		if(pamMultiSelects != null && pamMultiSelects.length > 0) {
			for(int k=0; k<pamMultiSelects.length; k++) {
				logger.debug("ques="+pamMultiSelects[k]);
				answersNotFound = new ArrayList<Object> ();
				String key = pamMultiSelects[k];
				Object obj = arrayAnsMap.get(key);			
				if( obj != null && obj instanceof String[]) {
					String[] list = (String[]) obj;
					for(int i=0; i< list.length; i++) {
						value = list[i];
						String st = answerCheckBox(pamMultiSelects[k], value, pdfForm);
						if(st != null && st.length() > 0)
							answersNotFound.add(st);
					}
				}
				
				//For Site Of TB Disease, display all other selected values not found on paperform.
				if(key.equalsIgnoreCase("TUB119")) {
					if(answersNotFound.size() > 0) {
						StringBuffer sb = new StringBuffer();
						for(int i=0; i<answersNotFound.size(); i++) {
							String val = getVal(answersNotFound.get(i));
							NbsQuestionMetadata metaData = (NbsQuestionMetadata) questionMap.get(key);
							String desc = getVal(cache.getDescForCode(metaData.getCodeSetNm(), val));
							sb.append(desc.equals("") ? "N/A" : desc);
							if(i < answersNotFound.size()-1)
							sb.append(", ");
						}
						FormText tBox = (FormText) pdfForm.getElement("TUB119_other");
						tBox.setValue(sb.toString());	
						//civil00015082 When nothing matched on PaperForm, Check X against Other and put the description of the Selected Value
						FormCheckbox cBox = (FormCheckbox) pdfForm.getElement(key + "_OTH");
						cBox.check(true);
						
					}
				} else {
					if(answersNotFound.size() > 0) {
						StringBuffer sb = new StringBuffer();
						for(int i=0; i<answersNotFound.size(); i++) {
							String val = getVal(answersNotFound.get(i));	
							NbsQuestionMetadata metaData = (NbsQuestionMetadata) questionMap.get(key);
							String codesetNm = metaData.getCodeSetNm() == null ? "" : metaData.getCodeSetNm();
							String desc = "";
							if(!codesetNm.equals("") && codesetNm.equals("COUNTY_CCD")) {								 
								desc = countyCodes.get(val) == null ? "" : (String) countyCodes.get(val);
							} else if(!codesetNm.equals("") && codesetNm.equals("STATE_CCD")) {								 
								desc = stateAbbMap.get(val) == null ? "" : (String) stateAbbMap.get(val);
							} else {
								 desc = getVal(cache.getDescForCode(codesetNm, val));	
							}
							
							sb.append(desc.equals("") ? "N/A" : desc);
							if(i < answersNotFound.size()-1)
							sb.append(", ");
						}
						FormText tBox = (FormText) pdfForm.getElement(key);
						tBox.setValue(sb.toString());	
					}
				}				
			}			
		}
		logger.debug("setRVCTAnswers: desc by codes");		
		//Display description by codes
		String[] cdDescs = {"TUB128","TUB132","TUB138","TUB109","TUB197","TUB243"};
		if(cdDescs != null && cdDescs.length > 0) {
			for(int i=0; i< cdDescs.length; i++) {
				logger.debug("quesDesc="+cdDescs[i]);
				String cdDescTxt = retrieveDescByCodesetNm(questionMap, answerMap, cdDescs[i]);
				answerPlainTxt(cdDescs[i], cdDescTxt, pdfForm);
			}
		}
		logger.debug("setRVCTAnswers: country dropdown");
		//COUNTRY Dropdown -> PlainText (Code -> Description) ex., Guardian1, Guardian2		
		String[] cntrsDsc = {"TUB115","TUB116"};
		if(cntrsDsc != null && cntrsDsc.length > 0) {
			for(int i=0; i< cntrsDsc.length; i++) {
				logger.debug("cntrsDsc="+cntrsDsc[i]);
				String coutryDesc = retrieveDescByCodesetNm(questionMap, answerMap, cntrsDsc[i]);
				answerPlainTxt(cntrsDsc[i], coutryDesc, pdfForm);
			}
		}		
		
		logger.debug("setRVCTAnswers: done");
	}
	
	/**
	 * RVCT Specific transformation logic for repeating questions with same Identifiers
	 * @param pdfForm
	 * @param form
	 * @param answerMap
	 */
	private static void handleRepeatQuestions(Form pdfForm, PamForm form, Map<Object,Object> answerMap) {
		logger.debug("handleRepeatQuestions - start");
		String value = null;

		//3. Case Numbers GridBoxes
		
		//State Case Number
		value = readValue(PamConstants.STATE_CASE, answerMap);
		String[] stateCases = {"INV173","INV173_3","INV173_4"};
		if(stateCases != null && stateCases.length > 0) {
			for(int i=0; i< stateCases.length; i++) {
				answerGridBoxYrStateMask(stateCases[i], value, pdfForm);
			}
		}	
		//City/County Case
		value = readValue(PamConstants.COUNTY_CASE, answerMap);
		String[] countyCases = {"INV198","INV198_1","INV198_2"};
		if(countyCases != null && countyCases.length > 0) {
			for(int i=0; i< countyCases.length; i++) {
				answerGridBoxYrStateMask(countyCases[i], value, pdfForm);
			}
		}
		//Linking StateCaseNumbers 1 and 2
		String[] linkingSCNums = {"TUB100","TUB102"};
		if(linkingSCNums != null && linkingSCNums.length > 0) {
			for(int i=0; i< linkingSCNums.length; i++) {
				value = readValue(linkingSCNums[i], answerMap);
				answerGridBoxYrStateMask(linkingSCNums[i], value, pdfForm);
			}
		}
		//Reason (Linking StateCaseNumbers 1 and 2) need to display just the first character from the description
		String[] cdDescs = {"TUB101","TUB103"};
		if(cdDescs != null && cdDescs.length > 0) {
			for(int i=0; i< cdDescs.length; i++) {
				String cdDescTxt = retrieveDescByCodesetNm(questionMap, answerMap, cdDescs[i]);
				if(!cdDescTxt.equals(""))
					answerPlainTxt(cdDescs[i], cdDescTxt.substring(0,1), pdfForm);
			}
		}		
		

		//PlainText
		String[] stateCasesTxt = {"INV173_1","INV173_2","INV173_5"};
		if(stateCasesTxt != null && stateCasesTxt.length > 0) {
			//Value is always a statecase here
			value = readValue(PamConstants.STATE_CASE, answerMap);
			for(int i=0; i< stateCasesTxt.length; i++) {
				answerPlainTxt(stateCasesTxt[i], value, pdfForm);
			}
		}	
		
		//"TUB227","TUB272" are In-State Move - City and In-State Move - City2
		String city1 = answerMap.get("TUB227") == null ? "" : (String)answerMap.get("TUB227");
		String city2 = answerMap.get("TUB272") == null ? "" : (String)answerMap.get("TUB272");
		if(city1 != "" && city2 == "") {
			answerPlainTxt("TUB227_TUB272", city1, pdfForm);
		} else if(city1 == "" && city2 != "") {
			answerPlainTxt("TUB227_TUB272", city2, pdfForm);
		} else if(city1 != "" && city2 != "") {
			String txt = city1 + ", " + city2;
			answerPlainTxt("TUB227_TUB272", txt, pdfForm);
		}		
		
		//TUB239 (No.of weeks)
		value = readValue("TUB239", answerMap);
		if (value != null && value.trim().length() > 0) {
			padWidthZeros("TUB239", value, pdfForm);
		}
		
		logger.debug("handleRepeatQuestions - done");
	}
	
	/**
	 * RVCT Specific Address String Format
	 * @param answerMap
	 * @return
	 */
	private static String getAddressString(Map<Object, Object>  answerMap) {
		logger.debug("RVCT getAddressString - start");
		StringBuffer sb = new StringBuffer();
		String address1 = answerMap.get(PamConstants.ADDRESS_1) == null ? "" : (String) answerMap.get(PamConstants.ADDRESS_1);
		String address2 = answerMap.get(PamConstants.ADDRESS_2) == null ? "" : (String) answerMap.get(PamConstants.ADDRESS_2);
		String city = answerMap.get(PamConstants.CITY) == null ? "" : (String) answerMap.get(PamConstants.CITY); 
		String state = answerMap.get(PamConstants.STATE) == null ? "" : (String) answerMap.get(PamConstants.STATE);
		
		if ( address1!= null && address1.length() > 0)
			sb.append(address1).append(", ");
		if ( address2!= null && address2.length() > 0)
			sb.append(address2).append(", ");
		if (city != null && city.length() > 0)
			sb.append(city).append(", ");
		if (state != null && state.length() > 0)
			sb.append(PamPrintUtil.getStateDescTxt(state));			
		logger.debug("RVCT getAddressString - done");
		return sb.toString();
	}
	

}
