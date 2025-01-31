package gov.cdc.nedss.webapp.nbs.action.pam.util;

import gov.cdc.nedss.entity.organization.dt.OrganizationNameDT;
import gov.cdc.nedss.entity.organization.vo.OrganizationVO;
import gov.cdc.nedss.entity.person.dt.PersonNameDT;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.locator.dt.EntityLocatorParticipationDT;
import gov.cdc.nedss.locator.dt.PostalLocatorDT;
import gov.cdc.nedss.locator.dt.TeleLocatorDT;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.vo.PamProxyVO;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.NbsQuestionMetadata;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.form.pam.PamForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.justformspdf.pdf.Form;
import com.justformspdf.pdf.FormCheckbox;
import com.justformspdf.pdf.FormElement;
import com.justformspdf.pdf.FormText;

/**
 * PamPrintUtil is a common class that handles PAM Specific Print Requests and delegates request appropriately
 * @author nmallela
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: Computer Sciences Corporation</p>
 * PamPrintUtil.java
 * Aug 12, 2008
 * @version
 * @updatedby: Fatima Lopez Calzado
 * Description: This code has been updated to fix data loss issue
 * Page Builder: Data Loss on Varicella and Tuberculosis Investigation. Jira defect: ND-15918. Also related to the data loss issue on Page Builder Investigations details in https://nbscentral.sramanaged.com//redmine/issues/12201
 * @version : 5.4.4 
 */
public class PamPrintUtil {

	static final LogUtils logger = new LogUtils(PamPrintUtil.class.getName());
	public static final String nedssDirectory = new StringBuffer(System.getProperty("nbs.dir")).append(File.separator).toString().intern();
	public static final String propertiesDirectory = new StringBuffer(nedssDirectory).append("Properties").append(File.separator).toString().intern();
	public static final CachedDropDownValues cache = new CachedDropDownValues();
	public static final String licKey = "43B78HB6";
	public static TreeMap<Object,Object> stateMap = cache.getStateAbbreviationsByCode("USA");
	public static TreeMap<Object,Object> stateAbbMap = cache.getStateCodes2("USA");
	public static Map<Object,Object> countryMap = cache.getCountyShortDescTxtCode();
	public static TreeMap<Object,Object> countyCodes = cache.getCountyCodes();
	private static final String LINE_BREAK = "\\r";

	/**
	 * This method sets all the PDF Form attributes to readonly to satisfy BR to not let the user edit
	 * the rendered PDF
	 * @param pForm
	 * @throws Exception
	 */
	public static void setReadOnly(Form pForm) throws Exception {
		Vector<Object>formElementList = pForm.getAllFormElements();
		 for (int i=0; i<formElementList.size(); i++) {
		    FormElement element = (FormElement) formElementList.elementAt(i);
		    if (element instanceof FormText) {
		    	element.setValue("");
		    	element.setReadOnly(true);
		    } else if(element instanceof FormCheckbox) {
		    	String name = element.getName();
		    	FormCheckbox cBox = (FormCheckbox) pForm.getElement(name);
		    	cBox.check(false);
		    	cBox.setReadOnly(true);
		    }
		 }
	}

	public static String getRaceDesc(Object[] raceArray, String cd) {
		StringBuffer raceBuffer = new StringBuffer();
		int i = raceArray.length;
		int k = 0;
		if (i > 0) {
			ArrayList<Object> races = CachedDropDowns.getRaceCodes(cd);
			for (int j = 0; j < i; j++) {
				Object subRace = raceArray[j];
				Iterator<Object>  it = races.iterator();
				while (it.hasNext()) {
					DropDownCodeDT dt = (DropDownCodeDT) it.next();
					if (dt.getKey().equalsIgnoreCase(subRace.toString())) {
						raceBuffer.append(dt.getValue());
						if (k < (i - 1)) {
							raceBuffer.append(", ");
							k++;
						}
					}
				}
			}
		}
		return raceBuffer.toString();
	}

   public static String getStateDescTxt(String sStateCd) {
		String desc = "";
		if (stateMap != null) {
			if (sStateCd != null && stateMap.get(sStateCd) != null)
				desc = (String) stateMap.get(sStateCd);
		}
		return desc;
	}
   public static String addEmptySpaces(String st) {
		return addEmptySpaces(st," ");
	}

	public static String addEmptySpaces(String st, String space) {
		StringBuffer sb = new StringBuffer();

		//sb.append(space); Commented out to fix Defect #8735 - 03/02/2016
		for (int i = 0; i < st.length(); i++) {
			sb.append(st.charAt(i)).append(space);
		}
		return sb.toString();
	}

	public static String readValue(String cd, Map<Object,Object> answerMap) {
		return answerMap.get(cd) == null ? null : answerMap.get(cd).toString();
	}

	public static PamProxyVO viewPrintLoadUtil(PamForm pamForm, HttpServletRequest req) throws Exception {

		//Call Common framework
		  HttpSession session = req.getSession();
		  pamForm.setActionMode(NEDSSConstants.VIEW_LOAD_ACTION);
		  pamForm.setFormFieldMap(new HashMap<Object,Object>());
		  String invFormCd = pamForm.getPamFormCd();
		  PamLoadUtil pamLoadUtil = new PamLoadUtil();
		  pamLoadUtil.loadQuestions(invFormCd);
		  pamLoadUtil.loadQuestionKeys(invFormCd);
		  String sPublicHealthCaseUID = (String)NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationUid);
		  PamProxyVO proxyVO = pamLoadUtil.getProxyObject(sPublicHealthCaseUID, req.getSession());
		  pamLoadUtil.setCommonAnswersForViewEdit(pamForm, proxyVO, req);
		  pamLoadUtil.setMSelectCBoxAnswersForViewEdit(pamForm, pamLoadUtil.updateMapWithQIds(proxyVO.getPamVO().getPamAnswerDTMap()));

		  return proxyVO;
	}

	public static String getVal(Object obj) {
		return obj == null ? "" : (String) obj;

	}

	/**
	 * Simple plain text answer in PDF Form
	 * @param id
	 * @param value
	 * @param pdfForm
	 */
	public static void answerPlainTxt(String id, String value, Form pdfForm) {

	   if(value != null && value.trim().length() > 0) {
			try {
				FormText txtField = (FormText) pdfForm.getElement(id);
				txtField.setValue(value);
			} catch (Exception e) {
				logger.error("PDF Form Element: " + id + " not found in answerPlainTxt() : ");
			}
	   }
	}

	/**
	 * Gird text answer in PDF Form
	 * @param id
	 * @param value
	 * @param pdfForm
	 */
	public static void answerGridBox(String id, String value, Form pdfForm) {

	   if(value != null && value.trim().length() > 0) {
			try {
				FormText txtField = (FormText) pdfForm.getElement(id);
				txtField.setValue(addEmptySpaces(value));
			} catch (Exception e) {
				logger.error("PDF Form Element: " + id + " not found in answerGridBox() : ");
			}
	   }
   }

	/**
	 * TextArea answer in PDF Form
	 * @param id
	 * @param value
	 * @param pdfForm
	 * @throws Exception
	 */
	public static void answerTxtArea(String id, String value, int charsPerLine, Form pdfForm) throws Exception {

	   if(value != null && value.trim().length() > 0) {
		   int lines = Math.round( value.length() / (float)charsPerLine);
		   if(lines == 0) lines = 1;
		   if(lines > 6) lines = 6;
		   StringBuffer sb = new StringBuffer();
		   int start = 0;
		   for(int i=0; i<lines; i++) {
				if(i== (lines-1)) {
					if(value.length() > (start+charsPerLine) )
						sb.append(value.substring(start,(start+charsPerLine)-3)).append("...");
					else
						sb.append(value.substring(start,value.length()));
				} else {
					sb.append(value.substring(start,start+charsPerLine)).append(LINE_BREAK);
				}
				start = start + charsPerLine;
			}

			FormText txtField = (FormText) pdfForm.getElement(id);
			txtField.setValue(sb.toString());
	   }

   }

	/**
	 * Displays MM/DD/YYYY Format answers in PDF Form
	 * @param id
	 * @param value
	 * @param pdfForm
	 * @throws Exception
	 */
	public static void answerYrFormat1(String id, String value, Form pdfForm) throws Exception {

		if (value != null && value.trim().length() > 0) {
			FormText date_submitted_mm = (FormText) pdfForm.getElement(id + "_1");
			date_submitted_mm.setValue(addEmptySpaces(value.substring(0, 2)));

			FormText date_submitted_dd = (FormText) pdfForm.getElement(id + "_2");
			date_submitted_dd.setValue(addEmptySpaces(value.substring(3, 5)));

			FormText date_submitted_yy = (FormText) pdfForm.getElement(id + "_3");
			date_submitted_yy.setValue(addEmptySpaces(value.substring(6, 10)));
		}

	}

	/**
	 * Displays MM/YYYY Format answers in PDF Form
	 * @param id
	 * @param value
	 * @param pdfForm
	 * @throws Exception
	 */
	public static void answerYrFormat2(String id, String value, Form pdfForm) throws Exception {

		if (value != null && value.trim().length() > 0) {
			FormText date_submitted_mm = (FormText) pdfForm.getElement(id + "_1");
			date_submitted_mm.setValue(addEmptySpaces(value.substring(0, 2)));

			FormText date_submitted_yyyy = (FormText) pdfForm.getElement(id + "_2");
			date_submitted_yyyy.setValue(addEmptySpaces(value.substring(6, 10)));
		}

	}

	/**
	 * Displays YYYY Format answers in PDF Form
	 * @param id
	 * @param value
	 * @param pdfForm
	 * @throws Exception
	 */
	public static void answerYrFormat3(String id, String value, Form pdfForm) throws Exception {

		if (value != null && value.trim().length() > 0) {
			FormText date_submitted_yyyy = (FormText) pdfForm.getElement(id);
			date_submitted_yyyy.setValue(addEmptySpaces(value));
		}

	}

	/**
	 * Checkbox answers in PDF Form
	 * @param id
	 * @param value
	 * @param pdfForm
	 * @return
	 */
	public static String answerCheckBox(String id, String value, Form pdfForm) {

	   String notFound = "";

	   if(value != null && value.trim().length() > 0) {
			String elementName = id + "_" + value;
				try {
					FormCheckbox cBox = (FormCheckbox) pdfForm.getElement(elementName);
					cBox.check(true);
					//if(elementName.endsWith("OTH")) notFound = value;
				} catch (Exception e) {
					notFound = value;
					//logger.error("PDF Form Element: " + elementName + " not found in answerCheckBox() : ");
				}
		}
	   return notFound;
	}

	/**
	 * Sample Format to split into 3 GridBoxes: 2008-OK-ABCD56789
	 * @param id
	 * @param value
	 * @param pdfForm
	 */
	public static void answerGridBoxYrStateMask(String id, String value, Form pdfForm) {

		   if(value != null && value.trim().length() > 0) {
				try {
					StringTokenizer st = new StringTokenizer(value, "-");
					String year = st.nextToken();
					String state = st.nextToken();
					String idNum = st.nextToken();
					answerGridBox(id + "_YEAR", year, pdfForm);
					answerGridBox(id + "_STATE", state, pdfForm);
					answerGridBox(id, idNum, pdfForm);

				} catch (Exception e) {
					logger.error("PDF Form Element: " + id + " not found in answerGridBoxYrStateMask() : ");
				}
		   }
	}

	/**
	 * answerFormatAgeGrid is a  3 digit age formatter in a 3 cell gridbox on PDF
	 * If there is only one number (e.g., 9 years old), populate the right most cell with the number.
	 * If there are two numbers (e.g., 20 years old), populate the right most cells with the numbers.
	 * @param id
	 * @param value
	 * @param pdfForm
	 */
	public static void answerFormatAgeGrid(String id, String value, Form pdfForm) {

		 if(value != null && value.trim().length() > 0) {
				try {
					if(value.length() == 1)
						answerGridBox(id, "  " + value, pdfForm);
					else if(value.length() == 2)
						answerGridBox(id, " " + value, pdfForm);
					else
						answerGridBox(id, value, pdfForm);

				} catch (Exception e) {
					logger.error("PDF Form Element: " + id + " not found in answerFormatAgeGrid() : ");
				}
		   }
	}

	public static void padWidthZeros(String id, String value, Form pdfForm) {

		 if(value != null && value.trim().length() > 0) {
				try {
					if(value.length() == 1)
						answerGridBox(id, "00" + value, pdfForm);
					else if(value.length() == 2)
						answerGridBox(id, "0" + value, pdfForm);
					else
						answerGridBox(id, value, pdfForm);

				} catch (Exception e) {
					logger.error("PDF Form Element: " + id + " not found in padWidthZeros() : ");
				}
		   }
	}



	public static String getPrvName(PersonVO prvVO) {

		StringBuffer stBuff = new StringBuffer("");
		if (prvVO.getThePersonNameDTCollection() != null) {
			Iterator<Object>  personNameIt = prvVO.getThePersonNameDTCollection().iterator();

			while (personNameIt.hasNext()) {
				PersonNameDT personNameDT = (PersonNameDT) personNameIt.next();

				if (personNameDT.getNmUseCd().equalsIgnoreCase("L")) {
					stBuff.append((personNameDT.getFirstNm() == null) ? "" : (personNameDT.getFirstNm() + " "));
					stBuff.append((personNameDT.getLastNm() == null) ? "": (personNameDT.getLastNm()));
					stBuff.append((personNameDT.getNmSuffix() == null) ? "": (", " + personNameDT.getNmSuffix()));
					stBuff.append((personNameDT.getNmDegree() == null) ? "": (", " + personNameDT.getNmDegree()));
				}
			}
		}
		return stBuff.toString();
	}

	public static String getPrvAddress(PersonVO prvVO) {

		return getAddress(prvVO.getTheEntityLocatorParticipationDTCollection());
	}
	public static String getPrvCityStateZip(PersonVO prvVO) {

		return getCityStateZip(prvVO.getTheEntityLocatorParticipationDTCollection());
	}
	public static String getPrvTele(PersonVO prvVO) {

		return getTele(prvVO.getTheEntityLocatorParticipationDTCollection());
	}


	/**
	 * OrgName
	 * @param orgVO
	 * @return
	 */
	public static String getOrgName(OrganizationVO orgVO) {

		StringBuffer stBuff = new StringBuffer("");
		if (orgVO.getTheOrganizationNameDTCollection() != null) {
			Iterator<Object>  orgNameIt = orgVO.getTheOrganizationNameDTCollection().iterator();
			while (orgNameIt.hasNext()) {
				OrganizationNameDT orgNmDT = (OrganizationNameDT) orgNameIt.next();
				if (orgNmDT.getNmUseCd() != "L") {
					stBuff.append((orgNmDT.getNmTxt() == null) ? "" : (orgNmDT.getNmTxt()));
				}
			}
		}
		return stBuff.toString();
	}

	public static String getOrgAddress(OrganizationVO orgVO) {

		return getAddress(orgVO.getTheEntityLocatorParticipationDTCollection());
	}

	public static String getOrgCityStateZip(OrganizationVO orgVO) {

		return getCityStateZip(orgVO.getTheEntityLocatorParticipationDTCollection());
	}
	public static String getOrgTele(OrganizationVO orgVO) {

		return getTele(orgVO.getTheEntityLocatorParticipationDTCollection());
	}

	/**
	 * returns Entity's Address Line1
	 * @param entityLocatorParticipationDTCollection
	 * @return
	 */
	private static String getAddress(Collection<Object>  entityLocatorParticipationDTCollection) {
		PostalLocatorDT postalDT = null;
		StringBuffer stBuff =  new StringBuffer("");
		if (entityLocatorParticipationDTCollection  != null) {
			Iterator<Object>  entIt = entityLocatorParticipationDTCollection.iterator();
			while (entIt.hasNext()) {
				EntityLocatorParticipationDT entityDT = (EntityLocatorParticipationDT) entIt.next();
				if (entityDT != null) {
					if (entityDT.getClassCd().equalsIgnoreCase("PST") && entityDT.getRecordStatusCd().equalsIgnoreCase("ACTIVE") && entityDT.getStatusCd().equalsIgnoreCase("A") && entityDT.getUseCd().equalsIgnoreCase("WP")) {
						postalDT = entityDT.getThePostalLocatorDT();
						stBuff.append((postalDT.getStreetAddr1() == null) ? "" : (postalDT.getStreetAddr1()));
					}
				}
			}
		}

		return stBuff.toString();
	}

	/**
	 * returns Entity's City, State and ZipCd
	 * @param entityLocatorParticipationDTCollection
	 * @return
	 */
	public static String getCityStateZip(Collection<Object>  entityLocatorParticipationDTCollection) {

		PostalLocatorDT postalDT = null;
		StringBuffer stBuff =  new StringBuffer("");
		if (entityLocatorParticipationDTCollection  != null) {
			Iterator<Object>  entIt = entityLocatorParticipationDTCollection.iterator();
			while (entIt.hasNext()) {
				EntityLocatorParticipationDT entityDT = (EntityLocatorParticipationDT) entIt.next();
				if (entityDT != null) {
					if (entityDT.getClassCd().equalsIgnoreCase("PST") && entityDT.getRecordStatusCd().equalsIgnoreCase("ACTIVE") && entityDT.getStatusCd().equalsIgnoreCase("A") && entityDT.getUseCd().equalsIgnoreCase("WP")) {
						postalDT = entityDT.getThePostalLocatorDT();
						stBuff.append((postalDT.getCityDescTxt() == null) ? "" : (postalDT.getCityDescTxt()));
						stBuff.append((postalDT.getCityDescTxt() == null && postalDT.getStateCd() == null) ? "" : (", "));
						if (postalDT.getStateCd() != null) {
							stBuff.append(stateAbbMap.get(postalDT.getStateCd()));
						}
						stBuff.append((postalDT.getZipCd() == null) ? "": (" " + postalDT.getZipCd() + " "));
					}
				}
			}
		}
		return stBuff.toString();
	}

	/**
	 * returns Entity's Work Phone with area code
	 * @param entityLocatorParticipationDTCollection
	 * @return
	 */
	public static String getTele(Collection<Object>  entityLocatorParticipationDTCollection) {

		TeleLocatorDT teleDT = null;
		StringBuffer stBuff =  new StringBuffer("");
		if (entityLocatorParticipationDTCollection  != null) {
			Iterator<Object>  entIt = entityLocatorParticipationDTCollection.iterator();
			while (entIt.hasNext()) {
				EntityLocatorParticipationDT entityDT = (EntityLocatorParticipationDT) entIt.next();
				if (entityDT != null) {
					if (entityDT.getClassCd() != null) {
						if (entityDT.getClassCd() != null && entityDT.getClassCd().equalsIgnoreCase("TELE") && entityDT.getRecordStatusCd() != null && entityDT.getRecordStatusCd().equalsIgnoreCase("ACTIVE") && entityDT.getCd() != null && entityDT.getCd().equalsIgnoreCase("PH") && entityDT.getUseCd() != null && entityDT.getUseCd().equalsIgnoreCase("WP")) {
							teleDT = entityDT.getTheTeleLocatorDT();
							stBuff.append((teleDT.getPhoneNbrTxt() == null) ? "" : (teleDT.getPhoneNbrTxt()));
						}
					}
				}
			}
		}
		return stBuff.toString();
	}

	/**
	 * retrieveDescByCodesetNm retrieves description from code and Code set
	 * @param answerMap
	 * @param id
	 * @return
	 */
	public static String retrieveDescByCodesetNm(Map<Object,Object> questionMap, Map<Object,Object> answerMap, String id) {
		String cdDescTxt = "";
		try {
			String value = readValue(id, answerMap);
			NbsQuestionMetadata metaData = (NbsQuestionMetadata) questionMap.get(id);
			cdDescTxt = cache.getDescForCode(metaData.getCodeSetNm(), value);
		} catch (Exception e) {
			logger.error("Error while retrieving description for Identifier: " + id  + ": " + e.toString());
		}

		return cdDescTxt;
	}


}