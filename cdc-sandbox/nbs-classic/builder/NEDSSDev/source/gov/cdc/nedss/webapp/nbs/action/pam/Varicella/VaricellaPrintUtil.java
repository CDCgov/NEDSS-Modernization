package gov.cdc.nedss.webapp.nbs.action.pam.Varicella;

import gov.cdc.nedss.entity.organization.vo.OrganizationVO;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.vo.PamProxyVO;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.NbsQuestionMetadata;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.action.pam.PamClientVO.PamClientVO;
import gov.cdc.nedss.webapp.nbs.action.pam.util.PamConstants;
import gov.cdc.nedss.webapp.nbs.action.pam.util.PamPrintUtil;
import gov.cdc.nedss.webapp.nbs.form.pam.PamForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.QuestionsCache;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.justformspdf.pdf.Form;
import com.justformspdf.pdf.FormCheckbox;
import com.justformspdf.pdf.FormText;
import com.justformspdf.pdf.PDF;
import com.justformspdf.pdf.PDFReader;

/**
 * VaricellaPrintUtil is a Java PDF Print Utility that uses AcroFields to populate PDF 
 * @author Narendra Mallela
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: Computer Sciences Corporation</p>
 * VaricellaPrintUtil.java
 * Oct 15, 2008
 * @version 1.0
 */
public class VaricellaPrintUtil extends PamPrintUtil {
	
	static final LogUtils logger = new LogUtils(VaricellaPrintUtil.class.getName());
	private final static String pdfFileName = "CDCVaricellaForm.pdf";
	public static PropertyUtil propertyUtil= PropertyUtil.getInstance();
	private static Map<Object,Object> questionMap = (Map)QuestionsCache.getQuestionMap().get(NBSConstantUtil.INV_FORM_VAR);
	
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
			pamForm.getPamClientVO().setOldPamProxyVO(proxyVO);
			//demographic Answers
			setVaricellaDemographicAnswers(pdfForm, pamForm);				
			//Varicella Answers
			setVaricellaAnswers(pdfForm, pamForm, nbsSecurityObj);
			
			//render
			pdf.render();
			res.setContentType("application/pdf");
			pdf.writeTo(res.getOutputStream());	
			
			
		} catch (Exception e) {
			logger.error(e.toString());
			req.getSession().setAttribute("error", "<b>There is an error while printing the Varicella Form: </b> <br/><br/>");				
			try {
				res.sendRedirect("/nbs/error");
			} catch (IOException e1) {
				logger.error("Error while redirecting Varicella Print Error: " + e1.toString());
				e1.printStackTrace();
			}
		} finally {		
			try {
				if(fis != null) 
					fis.close();
			} catch (IOException e2) {
				logger.error("Error while closing FileInputStream for Varicella Print : " + e2.toString());
			}
		}
	}	
	
	/**
	 * VaricellaDemographicAnswers for Varicella Form
	 * @param pdfForm
	 * @param pamForm
	 * @throws Exception
	 */
	public static void setVaricellaDemographicAnswers(Form pdfForm, PamForm pamForm) throws Exception {
		logger.debug("begin VarivellaPrintUtil.setVaricellaDemographicAnswers");
		Map<Object, Object>  answerMap = pamForm.getPamClientVO().getAnswerMap(); 
		PamClientVO clientVO = pamForm.getPamClientVO();
		String value = null;
		logger.debug("setVaricellaDemographicAnswers - name info");
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
		//Name
		if(name.toString().length() > 0) {
			answerPlainTxt("Name", name.toString(), pdfForm);
		}
		logger.debug("setVaricellaDemographicAnswers - addr info");
		//Current Address Line1
		String addString1 = getAddressString(answerMap);
		if(addString1 != null && addString1.length() > 0) {
			answerPlainTxt("Address1", addString1, pdfForm);
		}
		//Current Address Line2		
		String addString2 = getCityCountyStateString(answerMap);
		if(addString2 != null && addString2.length() > 0) {
			answerPlainTxt("Address2", addString2, pdfForm);
		}		
		//Zip
		String zipCd = answerMap.get(PamConstants.ZIP) == null ? "" : (String) answerMap.get(PamConstants.ZIP);
		if(zipCd != null && zipCd.length() > 0) {			
			FormText DEM163 = (FormText) pdfForm.getElement(PamConstants.ZIP);
			if(zipCd.indexOf("-") == -1) {
				DEM163.setValue(zipCd);
			} else {
				StringTokenizer st = new StringTokenizer(zipCd, "-");
				DEM163.setValue(st.nextToken());
			}
		}
		String[] plainTxts = {"DEM238","DEM240","INV173"};
		if(plainTxts != null && plainTxts.length > 0) {
			for(int i=0; i< plainTxts.length; i++) {
				value = readValue(plainTxts[i], answerMap);				
				answerPlainTxt(plainTxts[i], value, pdfForm);
			}
		}
		logger.debug("setVaricellaDemographicAnswers - provider info");
		//Provider INV225 /Organization INV218
		String prvUid = getVal(pamForm.getAttributeMap().get("INV225Uid"));
		String orgUid = getVal(pamForm.getAttributeMap().get("INV218Uid"));
		//When both exists, go with PRV
		if((!prvUid.equals("") && !orgUid.equals("")) || (orgUid.equals("") && !prvUid.equals(""))) {
			answerEntity1(prvUid, pdfForm, pamForm, "PRV");
		} else {
			answerEntity1(orgUid, pdfForm, pamForm, "ORG");
		}
		
		//Reporting State and County
		String county = getVal(answerMap.get(PamConstants.COUNTY));
		answerPlainTxt(PamConstants.COUNTY, county, pdfForm);
		
		String state = getVal(answerMap.get(PamConstants.STATE));
		String stateDesc = CachedDropDowns.getCodeDescTxtForCd(state, NEDSSConstants.STATE_LIST);
		answerPlainTxt(PamConstants.STATE, stateDesc, pdfForm);
		logger.debug("setVaricellaDemographicAnswers - dob info");
		//DOB
		value = readValue(PamConstants.DOB, answerMap);
		if (value != null && value.trim().length() > 0) {
			answerYrFormat1(PamConstants.DOB, value, pdfForm);
		}	
		logger.debug("setVaricellaDemographicAnswers - age info");
		//Current Age
		value = readValue("INV143", answerMap);
		if (value != null && value.trim().length() > 0) {
			answerFormatAgeGrid("INV143", value, pdfForm);
		}
		logger.debug("setVaricellaDemographicAnswers - ethnicity info");
		//Ethnicity & Current Sex
		String[] chkBoxAnswers = {PamConstants.ETHNICITY, PamConstants.CURR_SEX};
		for(int i=0; i< chkBoxAnswers.length; i++) {
			value = readValue(chkBoxAnswers[i], answerMap);
			answerCheckBox(chkBoxAnswers[i], value, pdfForm);
		}		
		logger.debug("setVaricellaDemographicAnswers - race info");
		//Race
		FormCheckbox race_1 = (FormCheckbox) pdfForm.getElement(PamConstants.RACE + "_1");
		FormCheckbox race_2 = (FormCheckbox) pdfForm.getElement(PamConstants.RACE + "_2");
		FormCheckbox race_3 = (FormCheckbox) pdfForm.getElement(PamConstants.RACE + "_3");
		FormCheckbox race_4 = (FormCheckbox) pdfForm.getElement(PamConstants.RACE + "_4");
		FormCheckbox race_5 = (FormCheckbox) pdfForm.getElement(PamConstants.RACE + "_5");
		FormCheckbox race_6 = (FormCheckbox) pdfForm.getElement(PamConstants.RACE + "_6");

		if(clientVO.getAmericanIndianAlskanRace() == 1) race_1.check(true);				
		if(clientVO.getAsianRace() == 1) race_2.check(true);
		if(clientVO.getAfricanAmericanRace() == 1) race_3.check(true);
		if(clientVO.getHawaiianRace() == 1) race_4.check(true);
		if(clientVO.getWhiteRace() == 1) race_5.check(true);
		if(clientVO.getUnKnownRace() == 1) race_6.check(true);
		logger.debug("setVaricellaDemographicAnswers - birth country info");
		//12.Country of Birth Specify.....(Other than US)
		value = readValue("VAR215", answerMap);
		if (value != null && value.trim().length() > 0) {
			FormText VAR215 = (FormText) pdfForm.getElement("VAR215");
			String coutryDesc = getVal(countryMap.get(value));
			VAR215.setValue(coutryDesc);
		}
		logger.debug("end VarivellaPrintUtil.setVaricellaDemographicAnswers");
	}		
	
	/**
	 * Varicella specific PDF Answers
	 * @param pdfForm
	 * @param form
	 * @param nbsSecurityObj
	 * @throws Exception
	 */
	public static void setVaricellaAnswers(Form pdfForm, PamForm form, NBSSecurityObj nbsSecurityObj) throws Exception {
		logger.debug("begin VarivellaPrintUtil.setVaricellaAnswers");
		Map<Object, Object>  answerMap = form.getPamClientVO().getAnswerMap();
		String value = null;

		//Plain Text answers
		logger.debug("begin plain text");
		String[] plainText = {"VAR104","VAR163","VAR108","VAR110","VAR112","VAR120","VAR121","VAR125","VAR127","VAR138","INV134","VAR144","VAR177","VAR179","VAR187","VAR191","VAR194","VAR202","VAR205","VAR146","VAR147","VAR149","VAR219","VAR223","VAR227","VAR231","VAR235","VAR153","VAR157","INV165","INV166","VAR159"};
		if(plainText != null && plainText.length > 0) {
			for(int i=0; i< plainText.length; i++) {
				logger.debug("ques:"+plainText[i]);
				value = readValue(plainText[i], answerMap);
				answerPlainTxt(plainText[i], value, pdfForm);
			}
		}

		//Check Boxes
		logger.debug("begin checkbox");
		String[] chkBoxAnswers = {"VAR100","VAR103","VAR107","VAR109","VAR111","VAR113","VAR114","VAR115","VAR116","VAR117","VAR118","VAR119","VAR122","VAR126","VAR128","VAR129","VAR130","VAR131","VAR132","VAR133","VAR134","VAR135","VAR136","VAR137","VAR139","INV128","INV145","VAR143","INV144","VAR170","VAR171","VAR173","VAR174","VAR178","VAR180","VAR182","VAR183","VAR184","VAR186","VAR188","VAR189","VAR190","VAR193","VAR195","VAR196","VAR201","VAR204","VAR206","VAR208","VAR209","VAR101","VAR145","VAR162","VAR150","VAR212","VAR152","VAR154","VAR155","VAR156","VAR158","INV150","INV163","INV178","VAR160"};
		if(chkBoxAnswers != null && chkBoxAnswers.length > 0) {
			for(int i=0; i< chkBoxAnswers.length; i++) {
				logger.debug("ques:"+chkBoxAnswers[i]);
				value = readValue(chkBoxAnswers[i], answerMap);
				answerCheckBox(chkBoxAnswers[i], value, pdfForm);
			}
		}
		
		//GridBox answers
		logger.debug("begin gridbox");
		String[] gridAnswers = {"VAR151"};
		if(gridAnswers != null && gridAnswers.length > 0) {
			for(int i=0; i< gridAnswers.length; i++) {
				logger.debug("ques:"+gridAnswers[i]);
				value = readValue(gridAnswers[i], answerMap);
				answerFormatAgeGrid(gridAnswers[i], value, pdfForm);
			}
		}
		
		// MM/DD/YYYY answers
		logger.debug("begin MM/DD/YYYY");
		String[] mmddyyyyAnswers = {"INV111","INV120","INV121","INV136","INV137","VAR102","VAR123","VAR141","VAR142","INV132","INV133","INV146","VAR172","VAR175","VAR181","VAR185","VAR192","VAR200","VAR203","VAR207","VAR216","VAR220","VAR224","VAR228","VAR232","INV147"};
		if(mmddyyyyAnswers != null && mmddyyyyAnswers.length > 0) {
			for(int i=0; i< mmddyyyyAnswers.length; i++) {
				logger.debug("ques:"+mmddyyyyAnswers[i]);
				value = readValue(mmddyyyyAnswers[i], answerMap);
				answerYrFormat1(mmddyyyyAnswers[i], value, pdfForm);
			}
		}
		//Text Areas
		logger.debug("begin testArea");
		String[] textAreaAnswers = {"INV167"};
		if(textAreaAnswers != null && textAreaAnswers.length > 0) {
			for(int i=0; i< textAreaAnswers.length; i++) {
				logger.debug("ques:"+textAreaAnswers[i]);
				value = readValue(textAreaAnswers[i], answerMap);
				answerTxtArea(textAreaAnswers[i], value, 58, pdfForm);
			}
		}
		
		//Multi Selects
		logger.debug("begin multiSel");
		ArrayList<Object> answersNotFound = null;
		String [] pamMultiSelects = {"VAR105","VAR176"};
		Map<Object, Object>  arrayAnsMap = form.getPamClientVO().getArrayAnswerMap();
		if(pamMultiSelects != null && pamMultiSelects.length > 0) {
			for(int k=0; k<pamMultiSelects.length; k++) {
				logger.debug("ques:"+pamMultiSelects[k]);
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
			}			
		}
		//Display description by codes
		logger.debug("begin display desc");
		String[] cdDescs = {"VAR197","VAR198","VAR199","VAR140","VAR217","VAR218","VAR221","VAR222","VAR225","VAR226","VAR229","VAR230","VAR233","VAR234","INV151"};
		if(cdDescs != null && cdDescs.length > 0) {
			for(int i=0; i< cdDescs.length; i++) {
				logger.debug("cdDecs:"+cdDescs[i]);
				value = readValue(cdDescs[i], answerMap);
				NbsQuestionMetadata metaData = (NbsQuestionMetadata) questionMap.get(cdDescs[i]);
				String cdDescTxt = cache.getDescForCode(metaData.getCodeSetNm(), value);				
				answerPlainTxt(cdDescs[i], cdDescTxt, pdfForm);
			}
		}
		
		//COUNTRY Dropdown -> PlainText (Code -> Description) 	
		logger.debug("begin country");
		String[] cntrsDsc = {"VAR215"};
		if(cntrsDsc != null && cntrsDsc.length > 0) {
			for(int i=0; i< cntrsDsc.length; i++) {
				logger.debug("cntrsDsc:"+cntrsDsc[i]);
				value = getVal(readValue(cntrsDsc[i], answerMap));
				String coutryDesc = getVal(countryMap.get(value));
				answerPlainTxt(cntrsDsc[i], coutryDesc, pdfForm);
			}
		}		
		
		//HOSPTIAL(Organization) INV233
		logger.debug("begin hosp org");
		String orgString = getVal(form.getAttributeMap().get("INV233SearchResult"));
		answerEntity2(orgString, pdfForm, form);
		
		//Temperature VAR124 and VAR211
		logger.debug("begin temp");
		String temp = readValue("VAR124", answerMap) == null ? "" : readValue("VAR124", answerMap);		
		String units = readValue("VAR211", answerMap) == null ? "" : readValue("VAR211", answerMap);
		String temperature = "";
		if(units != null && units.equalsIgnoreCase("Cel")) {
			temperature = temp + "°C";
		}else if(units != null && units.equalsIgnoreCase("[degF]")) {
			temperature = temp + "°F";
		} else {
			temperature = temp;
		}
			answerPlainTxt("VAR124", temperature, pdfForm);
	}
	
	/**
	 * Varicella Specific Address Line1 String Format
	 * @param answerMap
	 * @return
	 */
	private static String getAddressString(Map<Object, Object>  answerMap) {
		logger.debug("begin getAddressString");
		StringBuffer sb = new StringBuffer();
		String address1 = answerMap.get(PamConstants.ADDRESS_1) == null ? "" : (String) answerMap.get(PamConstants.ADDRESS_1);
		String address2 = answerMap.get(PamConstants.ADDRESS_2) == null ? "" : (String) answerMap.get(PamConstants.ADDRESS_2);
		
		if ( address1!= null && address1.length() > 0)
			sb.append(address1).append(", ");
		if ( address2!= null && address2.length() > 0)
			sb.append(address2).append(", ");
		logger.debug("end getAddressString");
		return sb.toString();
	}
	/**
	 * Varicella City/County/State information
	 * @param answerMap
	 * @return
	 */
	private static String getCityCountyStateString(Map<Object, Object>  answerMap) {
		logger.debug("begin getCityCountyStateString");
		StringBuffer sb = new StringBuffer();		
		String city = answerMap.get(PamConstants.CITY) == null ? "" : (String) answerMap.get(PamConstants.CITY); 
		String county = answerMap.get(PamConstants.COUNTY) == null ? "" : (String) answerMap.get(PamConstants.COUNTY);
		String state = answerMap.get(PamConstants.STATE) == null ? "" : (String) answerMap.get(PamConstants.STATE);
		if (city != null && city.length() > 0)
			sb.append(city).append(", ");
		if (county != null && county.length() > 0) {
			String desc = countyCodes.get(county) == null ? "" : (String) countyCodes.get(county);
			if(!desc.equals(""))
				sb.append(desc).append(", ");		
		}
		if (state != null && state.length() > 0)
			sb.append(PamPrintUtil.getStateDescTxt(state));			
		logger.debug("end getCityCountyStateString");
		return sb.toString();
	}
	
	/**
	 * Displays Provider / Organization Information for Varicella Form
	 * @param entityUid
	 * @param pdfForm
	 * @throws Exception
	 */
	private static void answerEntity1(String entityUid, Form pdfForm, PamForm pamForm, String entityType)  throws Exception {
		logger.debug("begin answerEntity1");
		String entityNm = ""; 
		String entityAddress1 = "";
		String entityAddress2 = "";
		String entityTele = "";
		
		if(entityType.equals("PRV")) {
			PersonVO prvVO = VaricellaLoadUtil.getPersonVO(NEDSSConstants.PHC_REPORTER, pamForm.getPamClientVO().getOldPamProxyVO());
			if(prvVO != null) {
				entityNm = getPrvName(prvVO);
				entityAddress1 = getPrvAddress(prvVO);
				entityAddress2 = getPrvCityStateZip(prvVO);
				entityTele = getPrvTele(prvVO);
			}
			
		} else if(entityType.equals("ORG")) {			
			OrganizationVO orgVO = VaricellaLoadUtil.getOrganizationVO(NEDSSConstants.PHC_REPORTING_SOURCE, pamForm.getPamClientVO().getOldPamProxyVO());
			if(orgVO != null) {
				entityNm = getOrgName(orgVO);
				entityAddress1 = getOrgAddress(orgVO);
				entityAddress2 = getOrgCityStateZip(orgVO);
				entityTele = getOrgTele(orgVO);
			}
			
		}
		answerPlainTxt("entityNm", entityNm, pdfForm);
		answerPlainTxt("entityAddress1", entityAddress1, pdfForm);
		answerPlainTxt("entityAddress2", entityAddress2, pdfForm);
		answerPlainTxt("entityTele", entityTele, pdfForm);
		logger.debug("end answerEntity1");
	}

	private static void answerEntity2(String entitySt, Form pdfForm, PamForm pamForm)  throws Exception {
		logger.debug("begin answerEntity2");
		String entityNm = "N/A";
		OrganizationVO orgVO = VaricellaLoadUtil.getOrganizationVO(NEDSSConstants.HospOfADT, pamForm.getPamClientVO().getOldPamProxyVO());
		if(orgVO != null) {
			entityNm = getOrgName(orgVO);		
			answerPlainTxt("entity1Nm", entityNm, pdfForm);
		}
		logger.debug("end answerEntity1");
	}	
	
}
