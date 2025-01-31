package gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.printfillableforms;

import gov.cdc.nedss.act.ctcontact.dt.CTContactSummaryDT;
import gov.cdc.nedss.act.interview.dt.InterviewSummaryDT;
import gov.cdc.nedss.act.publichealthcase.dt.PublicHealthCaseDT;
import gov.cdc.nedss.act.treatment.dt.TreatmentAdministeredDT;
import gov.cdc.nedss.act.treatment.ejb.dao.TreatmentAdministeredDAOImpl;
import gov.cdc.nedss.association.dt.ActRelationshipDT;
import gov.cdc.nedss.association.dt.ParticipationDT;
import gov.cdc.nedss.entity.entityid.dt.EntityIdDT;
import gov.cdc.nedss.entity.person.dt.PersonDT;
import gov.cdc.nedss.entity.person.dt.PersonNameDT;
import gov.cdc.nedss.entity.person.dt.PersonRaceDT;
import gov.cdc.nedss.entity.person.ejb.bean.Person;
import gov.cdc.nedss.entity.person.ejb.bean.PersonHome;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.entity.place.vo.PlaceVO;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.locator.dt.EntityLocatorParticipationDT;
import gov.cdc.nedss.nbsactentity.dt.NbsActEntityDT;
import gov.cdc.nedss.page.ejb.pageproxyejb.dt.NbsAnswerDT;
import gov.cdc.nedss.page.ejb.pageproxyejb.util.InterviewCaseUtil;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.PageProxyVO;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.act.PageActProxyVO;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.page.PageVO;
import gov.cdc.nedss.pam.act.NbsCaseAnswerDT;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.InvestigationSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.LabReportSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.MorbReportSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.ResultedTestSummaryVO;
import gov.cdc.nedss.proxy.ejb.treatmentproxyejb.vo.TreatmentProxyVO;
import gov.cdc.nedss.proxy.ejb.treatmentproxyejb.vo.TreatmentSummaryVO;
import gov.cdc.nedss.proxy.ejb.workupproxyejb.bean.WorkupProxyHome;
import gov.cdc.nedss.proxy.ejb.workupproxyejb.vo.WorkupProxyVO;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.NbsQuestionMetadata;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NbsAnswerDTComparator;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.StringUtils;
import gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageConstants;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageLoadUtil;
import gov.cdc.nedss.webapp.nbs.action.pam.util.PamPrintUtil;
import gov.cdc.nedss.webapp.nbs.action.place.PlaceUtil;
import gov.cdc.nedss.webapp.nbs.action.util.CallProxyEJB;
import gov.cdc.nedss.webapp.nbs.action.util.InvestigationUtil;
import gov.cdc.nedss.webapp.nbs.form.page.PageForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

import java.io.File;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.rmi.PortableRemoteObject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;



//import com.ibm.icu.text.DateFormat;
import java.text.DateFormat;
/**
 * PrintUtil is a common class that handles STD Specific Print Requests and delegates request appropriately
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: Leidos</p>
 * PrintUtil.java
 * Oct 15, 2013
 * @version
 */

/**
 * PagePrintProxyToPrintableForm: is a common class that handles STD Specific
 * Print Requests and delegates request appropriately
 * <p>
 * Copyright: Copyright (c) 2013
 * </p>
 * <p>
 * Company: Leidos
 * </p>
 *
 * @author Pradeep Kumar Sharma
 * Description: This code has been updated to fix data loss issue
 * Page Builder: Data Loss on Page Builder Investigations details in https://nbscentral.sramanaged.com//redmine/issues/12201
 * @updatedby : Pradeep Sharma
 * @version : 5.4.4 */
public class PagePrintProxyToPrintableForm {

	static final LogUtils logger = new LogUtils(
			PagePrintProxyToPrintableForm.class.getName());
	public static final String nedssDirectory = new StringBuffer(
			System.getProperty("nbs.dir")).append(File.separator).toString()
			.intern();
	public static final String propertiesDirectory = new StringBuffer(
			nedssDirectory).append("Properties").append(File.separator)
			.toString().intern();
	public static final CachedDropDownValues cache = new CachedDropDownValues();
	public static Map<Object, Object> countryMap = cache
			.getCountyShortDescTxtCode();
	public static TreeMap<Object, Object> countyCodes = cache.getCountyCodes();


	/**
	 *
	 * @param cd
	 * @param answerMap
	 * @return
	 */
	public static String readValue(String cd, Map<Object, Object> answerMap) {
		return answerMap.get(cd) == null ? null : answerMap.get(cd).toString();
	}

	/**
	 *
	 * @param pageForm
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public static PageProxyVO viewPrintLoadUtil(PageForm pageForm,
			HttpServletRequest req)  throws Exception{

		// Call Common framework
		HttpSession session = req.getSession();
		pageForm.setActionMode(NEDSSConstants.VIEW_LOAD_ACTION);
		pageForm.setFormFieldMap(new HashMap<Object, Object>());
		String invFormCd = pageForm.getPageFormCd();
		PageLoadUtil pageLoadUtil = new PageLoadUtil();
		pageLoadUtil.loadQuestionKeys(invFormCd);
		String sPublicHealthCaseUID = (String) NBSContext.retrieve(session,
				NBSConstantUtil.DSInvestigationUid);

		PageProxyVO proxyVO = (PageActProxyVO) getPrintProxyObject(
				sPublicHealthCaseUID, req.getSession());
		// Load common PAT, INV answers and put it in answerMap for UI & Rules
		// to work
		pageLoadUtil.setCommonAnswersForViewEdit(pageForm, proxyVO, req);
		// Pam Specific Answers
		pageLoadUtil.setMSelectCBoxAnswersForViewEdit(pageForm, pageLoadUtil
				.updateMapWithQIds(((PageActProxyVO) proxyVO).getPageVO()
						.getPamAnswerDTMap()));
		//if(pageForm.getFormName().equals(NEDSSConstants.CDC_INTERVIEW_RECORD_FORM)){
		//	try {
		//		PageLoadUtil.setContactInformation(pageForm, proxyVO, session);
		//	} catch (NEDSSAppException e) {
		//		logger.debug("Exception while Setting contact Information in viewPrintLoadUtil: " + e.getMessage());
		//	}
		//}

		return proxyVO;
	}

	/**
	 *
	 * @param obj
	 * @return
	 */
	public static String getVal(Object obj) {
		return obj == null ? PrintConstants.EMPTY_STRING : (String) obj;

	}

	/**
	 *
	 * @param repeatingAnswerMap
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> processRepeatingQuestions(
			Map repeatingAnswerMap) throws Exception {
		Map<String, String> returnMap = new HashMap<String, String>();
		try {

			PageLoadUtil pageLoadUtil  = new PageLoadUtil();
			Map<Object, Object> map = pageLoadUtil.getQuestionMap();
			if (map != null) {
				Collection<Object> mappedQuestions = map.values();
				Iterator<Object> iter = mappedQuestions.iterator();

				while (iter.hasNext()) {
					NbsQuestionMetadata metadata = (NbsQuestionMetadata) iter
							.next();
					if (metadata.getQuestionIdentifier().equalsIgnoreCase(
							PrintConstants.INV272)) {
						checkifExists(returnMap, repeatingAnswerMap, metadata);
					} else if (metadata.getQuestionIdentifier()
							.equalsIgnoreCase(PrintConstants.NBS247)) {
						checkifExists(returnMap, repeatingAnswerMap, metadata);
					} else if (metadata.getQuestionIdentifier()
							.equalsIgnoreCase(PrintConstants.STD121)) {
						checkifExists(returnMap, repeatingAnswerMap, metadata);
					} else if (metadata.getQuestionIdentifier()
							.equalsIgnoreCase(PrintConstants.NBS249)) {
						checkifExists(returnMap, repeatingAnswerMap, metadata);
					}else if (metadata.getQuestionIdentifier()
							.equalsIgnoreCase(PrintConstants.NBS136)) {
						checkifExists(returnMap, repeatingAnswerMap, metadata);
					}else if (metadata.getQuestionIdentifier()
							.equalsIgnoreCase(PrintConstants.NBS200)) {
						checkifExists(returnMap, repeatingAnswerMap, metadata);
					}else if (metadata.getQuestionIdentifier()
							.equalsIgnoreCase(PrintConstants.NBS243)) {
						checkifExists(returnMap, repeatingAnswerMap, metadata);
					}else if (metadata.getQuestionIdentifier()
							.equalsIgnoreCase(PrintConstants.NBS290)) {
						checkifExists(returnMap, repeatingAnswerMap, metadata);
					}else if (metadata.getQuestionIdentifier()
							.equalsIgnoreCase(PrintConstants.NBS152)) {
						checkifExists(returnMap, repeatingAnswerMap, metadata,"");
					}else if (metadata.getQuestionIdentifier()
							.equalsIgnoreCase(PrintConstants.NBS185)) {
						checkifExists(returnMap, repeatingAnswerMap, metadata,"");
					} else if (metadata.getQuestionIdentifier()
							.equalsIgnoreCase(PrintConstants.NBS268)) {
						checkifExists(returnMap, repeatingAnswerMap, metadata,"");
					}else if (metadata.getQuestionIdentifier()
							.equalsIgnoreCase(PrintConstants.NBS195)) {
						checkifExists(returnMap, repeatingAnswerMap, metadata,"");
					} else if (metadata.getQuestionIdentifier()
							.equalsIgnoreCase(PrintConstants.STD115)) {
						checkifExists(returnMap, repeatingAnswerMap, metadata);
					}else if (metadata.getQuestionIdentifier()
							.equalsIgnoreCase(PrintConstants.NBS250)) {
						checkifExists(returnMap, repeatingAnswerMap, metadata);
					}else if (metadata.getQuestionIdentifier()
							.equalsIgnoreCase(PrintConstants.NBS251)) {
						checkifExists(returnMap, repeatingAnswerMap, metadata);
					}else if (metadata.getQuestionIdentifier()
							.equalsIgnoreCase(PrintConstants.NBS252)) {
						checkifExists(returnMap, repeatingAnswerMap, metadata);
					}else if (metadata.getQuestionIdentifier()
							.equalsIgnoreCase(PrintConstants.NBS253)) {
						checkifExists(returnMap, repeatingAnswerMap, metadata);
					}else if (metadata.getQuestionIdentifier()
							.equalsIgnoreCase(PrintConstants.STD116)) {
						checkifExists(returnMap, repeatingAnswerMap, metadata);
					}else if (metadata.getQuestionIdentifier()
							.equalsIgnoreCase(PrintConstants.NBS246)) {
						checkifExistsCD(returnMap, repeatingAnswerMap, metadata);
					}
				}
			}
		} catch (Exception e) {
			logger.error(e);
			logger.error("PagePrintProxyToPrintableForm.processRepeatingQuestions Exception thrown, "
					+ e);
			throw new Exception(e.toString());
		}
		return returnMap;
	}


	/**
	 *
	 * @param returnMap
	 * @param repeatingAnswerMap
	 * @param metadata
	 * @return
	 * @throws Exception
	 */
	public static void processRepeatingQuestions(
			Map<Object, Object> repeatingAnswerMap, Map<Object, Object> answerMap,Map<String, String> returnMap,String questionIdentifierParam,int i) throws Exception {
		try {
			PageLoadUtil pageLoadUtil = new PageLoadUtil();
			Map<Object, Object> map = pageLoadUtil.getQuestionMap();
			Map<Object, Object> interviewMap = PagePrintUtil.getInterviewQuestionMap();
			Map<Object, Object> contactMap = PagePrintUtil.getContactQuestionMap();
			if (map != null) {
				Collection<Object> mappedQuestions = map.values();
				Iterator<Object> iter = mappedQuestions.iterator();

				while (iter.hasNext()) {
					NbsQuestionMetadata metadata = (NbsQuestionMetadata) iter
							.next();
					if (metadata.getQuestionIdentifier().equalsIgnoreCase(
							questionIdentifierParam)) {
						checkifExists(returnMap, repeatingAnswerMap, answerMap,  metadata,i );
					}
				}
			}
			if (interviewMap != null) {
				Collection<Object> mappedQuestions = interviewMap.values();
				Iterator<Object> iter = mappedQuestions.iterator();

				while (iter.hasNext()) {
					NbsQuestionMetadata metadata = (NbsQuestionMetadata) iter
							.next();
					if (metadata.getQuestionIdentifier().equalsIgnoreCase(
							questionIdentifierParam)) {
						checkifExists(returnMap, repeatingAnswerMap, answerMap,  metadata,i);
					}
				}
			}
			if (contactMap != null) {
				Collection<Object> mappedQuestions = contactMap.values();
				Iterator<Object> iter = mappedQuestions.iterator();

				while (iter.hasNext()) {
					NbsQuestionMetadata metadata = (NbsQuestionMetadata) iter
							.next();
					if (metadata.getQuestionIdentifier().equalsIgnoreCase(
							questionIdentifierParam)) {
						checkifExists(returnMap, repeatingAnswerMap, answerMap,  metadata,i);
					}
				}
			}
		}catch (Exception e) {
			logger.error(e);
			logger.error("PagePrintProxyToPrintableForm.processRepeatingQuestions Exception thrown, "
					+ e);
			throw new Exception(e.toString());
		}
	}
	private static Map<String, String> checkifExists(
			Map<String, String> returnMap,
			Map<Object, Object> repeatingAnswerMap, NbsQuestionMetadata metadata)
			throws Exception {
		try {
			if (repeatingAnswerMap.containsKey(metadata.getNbsQuestionUid())) {
				ArrayList<Object> list = (ArrayList<Object>) repeatingAnswerMap
						.get(metadata.getNbsQuestionUid());
				if (list != null && list.size() > 0) {
					Iterator<Object> it = list.iterator();
					while (it.hasNext()) {
						NbsCaseAnswerDT caseDT = (NbsCaseAnswerDT) it.next();
						if(caseDT != null &&caseDT.getAnswerTxt() != null ){

						returnMap.put(metadata.getQuestionIdentifier() + "_"
								+ caseDT.getAnswerGroupSeqNbr(),
								mapCode(metadata.getQuestionIdentifier(),caseDT.getAnswerTxt()));
						}

					}
				}
			}
		} catch (Exception e) {
			logger.error(e);
			logger.error("PagePrintProxyToPrintableForm.checkifExists Exception thrown, "
					+ e);
			throw new Exception(e);
		}
		return returnMap;
	}

	private static String mapCode(String questionIdentifier, String answerTxt) {
		if(questionIdentifier.equalsIgnoreCase("STD121")){
			if(answerTxt.equalsIgnoreCase(PrintConstants.Anus_Rectum)){
				answerTxt="A";
			}else if(answerTxt.equalsIgnoreCase(PrintConstants.Penis)){
				answerTxt="B";
			}else if(answerTxt.equalsIgnoreCase(PrintConstants.Scrotum)){
				answerTxt="C";
			}else if(answerTxt.equalsIgnoreCase(PrintConstants.Vagina)){
				answerTxt="D";
			}else if(answerTxt.equalsIgnoreCase(PrintConstants.Cervix)){
				answerTxt="E";
			}else if(answerTxt.equalsIgnoreCase(PrintConstants.Naso_Pharynx)){
				answerTxt="F";
			}else if(answerTxt.equalsIgnoreCase(PrintConstants.Mouth_Oral_Cavity)){
				answerTxt="G";
			}else if(answerTxt.equalsIgnoreCase(PrintConstants.Eye_Conjunctiva)){
				answerTxt="H";
			}else if(answerTxt.equalsIgnoreCase(PrintConstants.Head)){
				answerTxt="I";
			}else if(answerTxt.equalsIgnoreCase(PrintConstants.Torso)){
				answerTxt="J";
			}else if(answerTxt.equalsIgnoreCase(PrintConstants.Extremities)){
				answerTxt="K";
			}else if(answerTxt.equalsIgnoreCase(PrintConstants.STD121_Not_Applicable)){
				answerTxt="N";
			}else if(answerTxt.equalsIgnoreCase(PrintConstants.STD121_Other)){
				answerTxt="O";
			}else if(answerTxt.equalsIgnoreCase(PrintConstants.STD121_Unknown)){
				answerTxt="U";
			}
		}else if(questionIdentifier.equalsIgnoreCase("IXS100")){
			if(answerTxt.equalsIgnoreCase(PrintConstants.UNABLE)){
				answerTxt=PrintConstants.IXS100_REFUSED_GRID;
			}else{
				answerTxt=PrintConstants.IXS100_ACCEPTED_GRID;
			}
		}else if(questionIdentifier.equalsIgnoreCase("IXS105")){
			if(answerTxt.equalsIgnoreCase(PrintConstants.REINTVW)){
				answerTxt="RI";
			}else if(answerTxt.equalsIgnoreCase(PrintConstants.PRESMPTV)){
				answerTxt="P";
			}else if(answerTxt.equalsIgnoreCase(PrintConstants.CLUSTER)){
				answerTxt="C";
			}else if(answerTxt.equalsIgnoreCase(PrintConstants.INITIAL)){
				answerTxt="OI";
			}
		}else if(questionIdentifier.equalsIgnoreCase("NBS151")){
			if(answerTxt.equalsIgnoreCase(PrintConstants.Administrative_Closure)){
				answerTxt="A";
			}else if(answerTxt.equalsIgnoreCase(PrintConstants.Record_Search_Closure)){
				answerTxt="R";
			}else if(answerTxt.equalsIgnoreCase(PrintConstants.Send_OOJ)){
				answerTxt="S";
			}else if(answerTxt.equalsIgnoreCase(PrintConstants.Insufficient_Information)){
				answerTxt="I";
			}else if(answerTxt.equalsIgnoreCase(PrintConstants.Not_Program_Priority)){
				answerTxt="N";
			}else if(answerTxt.equalsIgnoreCase(PrintConstants.Field_Follow_Up)){
				answerTxt="F";
			}
		}

		return answerTxt;
	}

	private static Map<String, String> checkifExistsCD(
			Map<String, String> returnMap,
			Map<Object, Object> repeatingAnswerMap, NbsQuestionMetadata metadata)
			throws Exception {
		try {
			if (repeatingAnswerMap!=null && repeatingAnswerMap.containsKey(metadata.getNbsQuestionUid())) {
				ArrayList<Object> list = (ArrayList<Object>) repeatingAnswerMap
						.get(metadata.getNbsQuestionUid());
				if (list != null && list.size() > 0) {
					Iterator<Object> it = list.iterator();
					while (it.hasNext()) {
						NbsCaseAnswerDT caseDT = (NbsCaseAnswerDT) it.next();
						if(caseDT != null &&caseDT.getAnswerTxt() != null ){
							String value = caseDT.getAnswerTxt();
							if ( metadata.getCodeSetNm() != null && value!=null){
								value =cache.getDescForCode(metadata.getCodeSetNm(), value);
							}
						returnMap.put(metadata.getQuestionIdentifier() + caseDT.getAnswerTxt() + "_"
								+ caseDT.getAnswerGroupSeqNbr(),
								value);
						}

					}
				}
			}
		} catch (Exception e) {
			logger.error(e);
			logger.error("PagePrintProxyToPrintableForm.checkifExists Exception thrown, "
					+ e);
			throw new Exception(e);
		}
		return returnMap;
	}

	private static Map<String, String> checkifExists(
			Map<String, String> returnMap,
			Map<Object, Object> repeatingAnswerMap, NbsQuestionMetadata metadata, String notes)
			throws Exception {
		try {
			if (repeatingAnswerMap.containsKey(metadata.getNbsQuestionUid())) {
				ArrayList<Object> list = (ArrayList<Object>) repeatingAnswerMap
						.get(metadata.getNbsQuestionUid());
				ArrayList<NbsCaseAnswerDT> set = new ArrayList<NbsCaseAnswerDT>();
				ArrayList<Object> sortedList = new ArrayList<Object>();
				ArrayList<Long> sortedUids =new ArrayList <Long>(); 
				Map<Long, Date> tempMap = new TreeMap<Long, Date>(Collections.reverseOrder());
                /* Steps for sorting the notes by updated timestamp. 
                 * Extract the timestamp from the note
                 * Parsing the timestamp into date format and storing in a sorted treemap
                 * Using the keys from the sorted treemap to populate a new listed of sorted notes
                 */
                SimpleDateFormat formatter;
		        Date date = null;
		        formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm");    
                         
				if (list != null) {
					//Collections.reverse(list);
					Iterator<Object> listIter1 = list.iterator();
					while(listIter1.hasNext()){
						NbsCaseAnswerDT caseDT = (NbsCaseAnswerDT) listIter1.next();
						String note = caseDT.getAnswerTxt();
						
						int startIndex = note.indexOf("~");
						int endIndex = note.indexOf("~~");
						String noteAddUpdateTimestamp = note.substring(startIndex+1, endIndex);
						
				        try {
				            date = (Date) formatter.parse(noteAddUpdateTimestamp);
				        } catch (Exception e) {
				            e.printStackTrace();
				        }
				        
				        tempMap.put(caseDT.getNbsCaseAnswerUid(),date);
					}
					for (Long key : tempMap.keySet()){
						sortedUids.add(key);
					}
					
					Iterator<Long> sortedUidsIter = sortedUids.iterator();
					while(sortedUidsIter.hasNext()){
					Long sortedCaseAnswerUid = sortedUidsIter.next();
					Iterator<Object> listIter2 = list.iterator();
					while(listIter2.hasNext()){
						NbsCaseAnswerDT caseDT = (NbsCaseAnswerDT) listIter2.next();
						Long nbsCaseAnswerUid = caseDT.getNbsCaseAnswerUid();
						if(nbsCaseAnswerUid.equals(sortedCaseAnswerUid)){
							sortedList.add(caseDT);
							}
						}
					}
					for (Object o : sortedList) {
						if (o instanceof NbsCaseAnswerDT) {
							set.add((NbsCaseAnswerDT) o);
						}
					}
				}
				
				
				
				if ( set.size() > 0) {
					Iterator<NbsCaseAnswerDT> it = set.iterator();
					while (it.hasNext()) {
						NbsCaseAnswerDT caseDT =  it.next();
						logger.debug("caseDT is:" + caseDT.toString());
						if(caseDT.getAnswerTxt() != null ){
							logger.debug("caseDT uid is :" + caseDT.getNbsCaseAnswerUid());
							notes = notes +  addNewlinesToNotes(caseDT.getAnswerTxt(),metadata.getQuestionIdentifier()) + "\n" ;
						}notes.length();
					}
					//truncating the notes and appending '...' to fit in the pdf notes box
					
						//For notes textbox stretching full page width and Half Page Length
						if (metadata.getQuestionIdentifier().equals(PrintConstants.NBS152)){
							if( notes.length()>PrintConstants.NotesFullWidthHalfPageCharLimit ){
							notes=notes.substring(0, PrintConstants.NotesFullWidthHalfPageCharLimit)+"...";
							}
						}
						//For notes textbox stretching full page width and Full Page Length
						else if (metadata.getQuestionIdentifier().equals(PrintConstants.NBS195)){
							if( notes.length()>PrintConstants.NotesFullWidthFullPageCharLimit ){
							notes=notes.substring(0, PrintConstants.NotesFullWidthFullPageCharLimit)+"...";
							}
						}
						//For notes textbox stretching half page width
						else{ 
							if( notes.length()>PrintConstants.NotesHalfWidthCharLimit ){
							notes=notes.substring(0, PrintConstants.NotesHalfWidthCharLimit)+"...";
							}
						}
					
					returnMap.put(metadata.getQuestionIdentifier() ,
							notes);

					//Note: we removed the NBS268_2 Supervisor Review notes from the form to create more room for the FR Notes
					//if(metadata.getQuestionIdentifier().equalsIgnoreCase(PrintConstants.NBS268) && notes.length()> (16*75)){
					//	returnMap.put(metadata.getQuestionIdentifier() ,
					//			notes.substring(0, (15*75*2)));
					//	returnMap.put(metadata.getQuestionIdentifier() + "_2" ,
					//			notes.substring((15*75*2)));
					//}
				}
			}
		} catch (Exception e) {
			logger.error(e);
			logger.error("PagePrintProxyToPrintableForm.checkifExists Exception thrown, "
					+ e);
			throw new Exception(e);
		}
		return returnMap;
	}

	private static String addNewlinesToNotes(String answerTxt, String quesId) {
		int lineLength = PrintConstants.HalfLineLength;
		if (quesId.equals(PrintConstants.NBS152) || quesId.equals(PrintConstants.NBS195))
			lineLength = PrintConstants.FullLineLength;
		if (answerTxt.length() < (lineLength+6))
			return answerTxt; //no newlines needed

		if (answerTxt.contains("\n"))
			return answerTxt;

		int j = 0;
		int prevPos = 0;
		String retString = "";
		try {
			for (int i = 0; i < answerTxt.length(); ++ i) {
				++j;
				if (j > lineLength && answerTxt.charAt(i)  == ' ') {
					retString = retString + answerTxt.substring(prevPos, prevPos + j) + "\n"; //add newline
					prevPos = prevPos + j;
					j = 0;
				}
			}
			if (j > 0)
				retString = retString + answerTxt.substring(prevPos, answerTxt.length());
		} catch (Exception e) {
			logger.warn("PagePrintProxyToPrintableForm.addNewlinesToText Exception thrown, " + e);
			return answerTxt;
		}
		return retString;
	}

	@SuppressWarnings("unchecked")
	private static void checkifExists(
			Map<String, String> returnMap,
			Map<Object, Object> repeatingAnswerMap,Map<Object, Object> answerMap,NbsQuestionMetadata metadata, int i)
			throws Exception {
		try {
				if (repeatingAnswerMap != null && repeatingAnswerMap.containsKey(metadata.getNbsQuestionUid())) {
					ArrayList<Object> list = (ArrayList<Object>) repeatingAnswerMap
							.get(metadata.getNbsQuestionUid());
					if (list != null && list.size() > 0) {
						Iterator<Object> it = list.iterator();
						while (it.hasNext()) {
							NbsAnswerDT caseDT = (NbsAnswerDT) it.next();
							if(caseDT != null &&caseDT.getAnswerTxt() != null ){
								String seq =  (caseDT.getAnswerGroupSeqNbr()!= null ? String.valueOf(caseDT.getAnswerGroupSeqNbr()) : "1");

									returnMap.put(metadata.getQuestionIdentifier()+"_" + i + "_"
											+ seq,
											caseDT.getAnswerTxt());
								}
						}
					}
				}else if(answerMap != null && answerMap.containsKey(metadata.getNbsQuestionUid())){
					NbsAnswerDT caseDT = (NbsAnswerDT)answerMap.get(metadata.getNbsQuestionUid());
					if(caseDT != null &&caseDT.getAnswerTxt() != null ){

							returnMap.put(metadata.getQuestionIdentifier()+"_" + i + "_"
									+ "1",
									caseDT.getAnswerTxt());
					}
				}
		} catch (Exception e) {
			logger.error(e);
			logger.error("PagePrintProxyToPrintableForm.checkifExists Exception thrown, "
					+ e);
			throw new Exception(e);
		}

	}

	/**
	 *
	 * @param sPublicHealthCaseUID
	 * @param session
	 * @return
	 * @throws Exception
	 */
	public static PageProxyVO getPrintProxyObject(String sPublicHealthCaseUID,
			HttpSession session) throws Exception {

		PageProxyVO proxy = null;
		MainSessionCommand msCommand = null;

		try {
			Long publicHealthCaseUID = new Long(sPublicHealthCaseUID);
			String sBeanJndiName = JNDINames.PAGE_PROXY_EJB;
			String sMethod = "getPageProxyVO";
			Object[] oParams = new Object[] { NEDSSConstants.PRINT_CDC_CASE,
					publicHealthCaseUID };
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);
			ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod,
					oParams);
			proxy = (PageProxyVO) arr.get(0);

		} catch (Exception ex) {
			logger.fatal("getProxy: ", ex);
			throw new Exception(ex);
		}

		return proxy;
	}



	public static void populateReferralBasisValues(
			PageProxyVO proxyVO,Map<String, String> referralBasisMap, int index, HttpSession session)  {
		PageActProxyVO actProxyVO = (PageActProxyVO) proxyVO;
		int i = index;
		String conCond = checkInvStartedFromConRec(proxyVO, session);
		String text= "";

			text = getAltCond(actProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd());

				if(conCond != null &&actProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getReferralBasisCd() != null && actProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getReferralBasisCd().contains("P")){
					referralBasisMap.put("NBS110_P_" + i + "_CD", actProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getReferralBasisCd());
					referralBasisMap.put("NBS110_P"  , actProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getReferralBasisCd());
					referralBasisMap.put("NBS110","P");
					referralBasisMap.put("NBS150_P_" + i, conCond);
				}else if(conCond != null && actProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getReferralBasisCd() != null &&
						(actProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getReferralBasisCd().contains("A") || actProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getReferralBasisCd().contains("S")||  actProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getReferralBasisCd().contains("C"))){
					referralBasisMap.put("NBS110_A_" + i + "_CD", actProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getReferralBasisCd());
					referralBasisMap.put("NBS110_A" ,actProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getReferralBasisCd());
					referralBasisMap.put("NBS110","A");
					referralBasisMap.put("NBS150_A_" + i, conCond);
				}else if(actProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getReferralBasisCd() != null && actProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getReferralBasisCd().contains("T")){
					referralBasisMap.put("NBS110_T_" + i + "_CD", actProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getReferralBasisCd());
					referralBasisMap.put("NBS110_T" ,actProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getReferralBasisCd());
					referralBasisMap.put("NBS110","T");
					referralBasisMap.put("NBS150_T_" + i, text);
				}else if (actProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getReferralBasisCd() != null){

					referralBasisMap.put("NBS179_O_" + i + "_CD", actProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getReferralBasisCd());
					referralBasisMap.put("NBS179_O",actProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getReferralBasisCd());
					referralBasisMap.put("NBS110","OOJ");
					referralBasisMap.put("NBS150_O_" + i, text);
				}

			}

	private static String getAltCond(String cd) {
		String text;
		text = cd;
		if(text != null){
			CachedDropDownValues cddv = new CachedDropDownValues();
			String shortcd = cddv.getDiagnosisCodeForConceptCode(text);
			if(shortcd.equalsIgnoreCase("350"))//Gonorrhea and Gonorrhea resistant have the same concept_code=10280
				shortcd="300";
			text = shortcd != null ? shortcd : text;
		}
		return text;
	}



	private static String checkInvStartedFromConRec(PageProxyVO proxyVO,
			HttpSession session) {
		Collection<Object> contactColl = null;
		String contactCond = null;
		try {
		contactColl = ((PageActProxyVO) proxyVO)
				.getTheCTContactSummaryDTCollection();

		Iterator<Object> itr = contactColl.iterator();
		Boolean isFrnCon = Boolean.FALSE;

		while (itr.hasNext()) {

			CTContactSummaryDT dt = (CTContactSummaryDT) itr.next();

			if(dt.isPatientNamedByContact()&&dt.getSubjectPhcLocalId() != null){
					String sMethod ="getPHCUidForLoaclId";
					String sBeanJndiName=JNDINames.PAGE_MANAGEMENT_PROXY_EJB;
					Object[] oParams = {dt.getSubjectPhcLocalId()};
					Long conPhcUid = (Long)CallProxyEJB.callProxyEJB(oParams, sBeanJndiName, sMethod, session);
				if(conPhcUid != null){
					PageActProxyVO contactProxyvo = (PageActProxyVO) PageLoadUtil
							.getProxyObject(
									String.valueOf(conPhcUid),
									session);

					if (contactProxyvo.getTheCTContactSummaryDTCollection() != null) {
						Iterator<Object> it = contactProxyvo
								.getTheCTContactSummaryDTCollection().iterator();
						while (it.hasNext()) {
							CTContactSummaryDT condt = (CTContactSummaryDT) it.next();
							if (condt.isContactNamedByPatient()
									&& condt.getContactEntityPhcUid().equals(
											proxyVO.getPublicHealthCaseVO()
													.getThePublicHealthCaseDT()
													.getPublicHealthCaseUid())) {
								isFrnCon = Boolean.TRUE;
								contactCond = contactProxyvo.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd();
								if(contactCond != null){
									CachedDropDownValues cddv = new CachedDropDownValues();
									String shortcd = cddv.getDiagnosisCodeForConceptCode(contactCond);
									contactCond =shortcd != null ? shortcd :contactCond;
								}
							}
							if(isFrnCon){

								break;
							}
						}
					}
					if(isFrnCon){
						break;
					}
				}
			}
		}
		} catch (Exception ex) {
			logger.error("Exception in PagePrintProxyToPrintableForm.checkInvStartedFromConRec: "+ex.getMessage());
			ex.printStackTrace();
		}

		return contactCond;
	}

	public static void populateInterviewRecord( PageProxyVO proxyVO,
			Map<String, String> interviewRecordMap, int i, HttpSession session, PageForm pageForm) {
		PageActProxyVO actProxyVO = (PageActProxyVO) proxyVO;
		Map<Object, Object>  answerMap = pageForm.getPageClientVO().getAnswerMap();
		if(actProxyVO.getPublicHealthCaseVO().getTheCaseManagementDT() != null){
			if(actProxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().getFldFollUpDispo() != null){
				String dispo = actProxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().getFldFollUpDispo();
				if(dispo != null &&dispo.equals(PrintConstants.FF_OOJ)){
					 dispo = dispo + "/" + actProxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().getFieldFollUpOojOutcome();
				}
				interviewRecordMap.put("NBS173_" + i + "_CD" ,dispo);

			}

			if(actProxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().getActRefTypeCd() != null){
				interviewRecordMap.put("NBS177_" + i, actProxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().getActRefTypeCd());
				interviewRecordMap.put("NBS177" , actProxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().getActRefTypeCd());
			}
			if(actProxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().getFldFollUpNotificationPlan() != null){
				interviewRecordMap.put("NBS167_" + i, actProxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().getFldFollUpNotificationPlan());
				interviewRecordMap.put("NBS167" , actProxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().getFldFollUpNotificationPlan());
			}
			if(actProxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().getSurvClosedDate() != null){
				String initdate = DateFormat.getDateInstance(DateFormat.MEDIUM).format( actProxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().getSurvClosedDate());
				interviewRecordMap.put("NBS147_" + i, initdate );
			}
			if(actProxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().getSurvAssignedDate() != null){
				String initdate = DateFormat.getDateInstance(DateFormat.MEDIUM).format( actProxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().getSurvAssignedDate());
				interviewRecordMap.put("NBS146_" + i, initdate );
			}
			if(actProxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().getSurvProviderContact() != null){
				interviewRecordMap.put("NBS148_" + i, actProxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().getSurvProviderContact() );
			}
			if(actProxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().getSurvPatientFollUp() != null){
				interviewRecordMap.put("NBS151_" + i, mapCode("NBS151",actProxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().getSurvPatientFollUp() ));
			}
		}
		if(actProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getLocalId() != null){
			interviewRecordMap.put("New_CaseNo_" + i, actProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getLocalId().substring(3,11));
		}
//		String cd = actProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd() ;
//		if(cd != null){
//			CachedDropDownValues cddv = new CachedDropDownValues();
//			String shortcd = cddv.getDiagnosisCodeForConceptCode(cd);
//			cd = shortcd != null ? shortcd : cd;
//		}
		
		String cd = (String) answerMap.get("NBS136");
		interviewRecordMap.put("NBS136_" + i , cd);
		if(actProxyVO.getTheInterviewSummaryDTCollection()!=null && actProxyVO.getTheInterviewSummaryDTCollection().size()>0){
			Iterator iterator= actProxyVO.getTheInterviewSummaryDTCollection().iterator();
			while(iterator.hasNext()){
				InterviewSummaryDT interviewSummaryDT = (InterviewSummaryDT)iterator.next();
				if(interviewSummaryDT != null){
					if(interviewSummaryDT.getInterviewTypeCd()!= null){
						interviewRecordMap.put("IXS105_" + i ,mapCode("IXS105",interviewSummaryDT.getInterviewTypeCd()));
						if(interviewSummaryDT.getInterviewTypeCd().equalsIgnoreCase("INITIAL")&&
							proxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().getFldFollUpDispoDate() != null ){
								String dispodate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(proxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().getFldFollUpDispoDate());
								interviewRecordMap.put("NBS174_" + i, dispodate);
							}
						}
						if(interviewSummaryDT.getInterviewStatusCd() != null){
							interviewRecordMap.put("IXS100_" + i ,mapCode("IXS100",interviewSummaryDT.getInterviewStatusCd()));
						}
						if(interviewSummaryDT.getInterviewDate() != null){
							interviewRecordMap.put("IXS101_" + i ,StringUtils.formatDate(interviewSummaryDT.getInterviewDate()));
						}
				}
			}
		}

		try {
			PersonVO interviewer = PageLoadUtil.getPersonVO("InterviewerOfPHC", actProxyVO);
			//PersonVO dispoFldFupInvestgrOfPHC = PageLoadUtil.getPersonVO("DispoFldFupInvestgrOfPHC", actProxyVO);
			PersonVO fldFupInvestgrOfPHC = PageLoadUtil.getPersonVO("FldFupInvestgrOfPHC", actProxyVO);

			if(fldFupInvestgrOfPHC != null){
				 //String fullName = dispoFldFupInvestgrOfPHC.getEntityIdDT_s(0).getRootExtensionTxt();
				String fullName = fldFupInvestgrOfPHC.getEntityIdDT_s(0).getRootExtensionTxt();
				interviewRecordMap.put("NBS175_" + i, checkNull(fullName));
			}

			if(interviewer != null){
				 String interviewerfullName = interviewer.getEntityIdDT_s(0).getRootExtensionTxt();
				interviewRecordMap.put("NBS186_" + i, checkNull(interviewerfullName));
			}



		} catch (NEDSSAppException e) {
			logger.error("NEDSS App Exception in PagePrintProxyToPrintableForm.populateInterviewRec: "+e.getMessage());
		} catch (Exception e) {
			logger.error("Exception in PagePrintProxyToPrintableForm.populateInterviewRec: "+e.getMessage());
			e.printStackTrace();
		}

	}

	private static String checkNull(String s){
		return  s != null? s: "";
	}


	private static StringBuffer getTreatmentNameCode(TreatmentSummaryVO treamentSummVO){
	StringBuffer treatmentNameCode =new StringBuffer();
		if(treamentSummVO.getTreatmentNameCode() != null && treamentSummVO.getTreatmentNameCode().equals("OTH")){
			TreatmentAdministeredDAOImpl dao = new TreatmentAdministeredDAOImpl();
			Collection<Object> coll =dao.load(treamentSummVO.getTreatmentUid());
			Object[] array =coll.toArray();
			if(array.length >0) {

				TreatmentAdministeredDT dt = (TreatmentAdministeredDT) array[0];
				if(dt.getCd() != null){
				String desc =CachedDropDowns.getCodeDescTxtForCd(dt.getCd(),"TREAT_DRUG");
				String text = desc != null? desc : dt.getCd();
				treatmentNameCode.append(text).append(", ");
				}
				if(dt.getDoseQty() != null){
					treatmentNameCode.append(dt.getDoseQty()).append(" ");
				}
				if(dt.getDoseQtyUnitCd()  != null){
					String desc =CachedDropDowns.getCodeDescTxtForCd(dt.getDoseQtyUnitCd() ,"TREAT_DOSE_UNIT");
					String text = desc != null? desc : dt.getDoseQtyUnitCd();
					treatmentNameCode.append(text).append(", ");
					}
				if(dt.getRouteCd() != null){
					String desc =CachedDropDowns.getCodeDescTxtForCd(dt.getRouteCd(),"TREAT_ROUTE");
					String text = desc != null? desc : dt.getRouteCd();
					treatmentNameCode.append(text).append(", ");
					}
				if(dt.getIntervalCd() != null){
					String desc =CachedDropDowns.getCodeDescTxtForCd(dt.getIntervalCd(),"TREAT_FREQ_UNIT");
					String text = desc != null? desc : dt.getIntervalCd();
					treatmentNameCode.append(text).append(" X ");
					}
				if(dt.getEffectiveDurationAmt() != null){
					treatmentNameCode.append(dt.getEffectiveDurationAmt()).append(" ");
				}

				if(dt.getEffectiveDurationUnitCd() != null){
					String desc =CachedDropDowns.getCodeDescTxtForCd(dt.getEffectiveDurationUnitCd(),"TREAT_DUR_UNIT");
					String text = desc != null? desc : dt.getEffectiveDurationUnitCd();
					treatmentNameCode.append(text);
					}
			}


		}else{
			treatmentNameCode.append(treamentSummVO.getCustomTreatmentNameCode());
		}
		return treatmentNameCode;
	}


	public static Map<String, String> populateTreatmentValuesWithProvider(
			PageProxyVO proxyVO,Map<String, String> treatmentSummaryMap,int i, HttpSession session, boolean withprovider) throws Exception {
		PageActProxyVO actProxyVO = (PageActProxyVO) proxyVO;

		try {
			Collection<Object> coll = actProxyVO
					.getTheTreatmentSummaryVOCollection();
			if (coll != null) {
				Iterator<Object> it = coll.iterator();
				while (it.hasNext()) {
					TreatmentSummaryVO treamentSummVO = (TreatmentSummaryVO) it
							.next();
					ArrayList<String> TreatmentUids = new ArrayList<String>();
					Iterator ite = treatmentSummaryMap.entrySet().iterator();
					while (ite.hasNext()){
						Map.Entry pair = (Map.Entry)ite.next();
						if(pair.getKey().toString().startsWith("TR_UID")){
							TreatmentUids.add(pair.getValue().toString());
						}
					}
					if(!TreatmentUids.contains(treamentSummVO.getTreatmentUid().toString())){
					String dateValue = StringUtils.formatDate(treamentSummVO
							.getActivityFromTime());

					String treatmentInfo = getTreatmentNameCode(treamentSummVO).toString();;

					treatmentSummaryMap.put("TR_DATE_" + i, checkNull(dateValue));

					treatmentSummaryMap.put("TR_INFO_" + i, checkNull(treatmentInfo));

					NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session.getAttribute("NBSSecurityObject");
		            String sBeanJndiName = JNDINames.TREATMENT_PROXY_EJB;
		            String sMethod = "getTreatmentProxy";
					Object[] oParams ={treamentSummVO.getTreatmentUid()};


					TreatmentProxyVO treatmentProxyVO =(TreatmentProxyVO)CallProxyEJB.callProxyEJB(oParams , sBeanJndiName, sMethod, session);
					PersonVO provider =  PageLoadUtil.getPerson(NEDSSConstants.PROVIDER_OF_TREATMENT, treatmentProxyVO.getTheTreatmentVO().getTheParticipationDTCollection(), treatmentProxyVO.getThePersonVOCollection());
					String providerFullName = PrintConstants.EMPTY_STRING;
					if(provider != null){
						providerFullName = provider.getThePersonDT().getFullName();
					}
					treatmentSummaryMap.put("TR_PROVIDER_" + i ,providerFullName );
					treatmentSummaryMap.put("TR_UID" + i, treamentSummVO.getTreatmentUid().toString());
					String key = PrintConstants.TR101_ + i + PrintConstants._CD;
					String value;
					if(withprovider){
					value = checkNull(dateValue) + PrintConstants.SPACE_STRING + checkNull(treatmentInfo) + PrintConstants.SPACE_STRING + providerFullName;
					}else{
						value = checkNull(dateValue) + PrintConstants.SPACE_STRING + checkNull(treatmentInfo) ;
					}
					treatmentSummaryMap.put(key, value );
					i++;
						}
				
				}
			}
			treatmentSummaryMap.put("rxindex", String.valueOf(i));
			return treatmentSummaryMap;
		} catch (Exception e) {
			logger.error("Error while retrieving populateTreatmentValues:  "
					+ e.toString());
			throw new Exception(e.toString());
		}

	}



	/**
	 *
	 * @param proxyVO
	 * @param i
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> populateEntityValues(PageProxyVO proxyVO,
			int i) throws Exception {
		try {
			PageActProxyVO actProxyVO = (PageActProxyVO) proxyVO;
			Map<String, String> patientMap = new HashMap<String, String>();
			Long patientUid = null;
			Long assignedToUid = null;
			Long physicianUid = null;

			Collection<Object> partColl = actProxyVO.getPublicHealthCaseVO()
					.getTheParticipationDTCollection();
			if (partColl != null) {
				Iterator<Object> it = partColl.iterator();
				while (it.hasNext()) {
					ParticipationDT partDT = (ParticipationDT) it.next();
					if (partDT.getTypeCd().equals(NEDSSConstants.PHC_PATIENT)) {
						patientUid = partDT.getSubjectEntityUid();
					} else if (partDT.getTypeCd().equals(
							NEDSSConstants.Surveillance_Investigator_OF_PHC)) {
						assignedToUid = partDT.getSubjectEntityUid();
					} else if (partDT.getTypeCd().equals(
							NEDSSConstants.PHC_PHYSICIAN)) {
						physicianUid = partDT.getSubjectEntityUid();
					}
				}
			}
			Collection<Object> coll = actProxyVO.getThePersonVOCollection();
			if (coll != null) {
				Iterator<Object> it = coll.iterator();
				while (it.hasNext()) {
					PersonVO personVO = (PersonVO) it.next();
					if (assignedToUid != null && personVO.getThePersonDT().getPersonUid()
							.compareTo(assignedToUid) == 0) {
						Collection<Object> entityColl = personVO
								.getTheEntityIdDTCollection();
						if (entityColl != null) {
							Iterator<Object> iter = entityColl.iterator();
							while (iter.hasNext()) {
								EntityIdDT entityDT = (EntityIdDT) iter.next();
								if (entityDT.getTypeCd().equals(
										NEDSSConstants.ENTITY_TYPECD_QEC)) {
									patientMap.put(PrintConstants.NBS145_ + i,
											entityDT.getRootExtensionTxt());
								}
							}
						}
						continue;
					} else if (personVO.getThePersonDT().getPersonUid()
							.compareTo(patientUid) == 0) {
						PersonDT patientDT = personVO.getThePersonDT();
						if (patientDT.getAgeReported() != null)
							patientMap.put(PrintConstants.DEM118, patientDT
									.getAgeReported().toString());
						ArrayList al = (ArrayList) personVO.getThePersonRaceDTCollection();
						if(al != null){
							for(i=0;i<al.size();i++){
								PersonRaceDT dt = ((PersonRaceDT)al.get(i));
								String race =dt.getRaceCd();
								convertRaceCD(patientMap, race,i);
							}
						}

						if (patientDT.getEthnicGroupInd() != null) {
							String ethnicity = patientDT.getEthnicGroupInd();
							//convertEthCd(patientMap, ethnicity);
						}
						

						Long PersonParentUid=patientDT.getPersonParentUid();
						NedssUtils nedssUtils = new NedssUtils();
						Object obj = nedssUtils.lookupBean(JNDINames.PERSONEJB);
						logger.debug("PersonEJB lookup = " + obj.toString());
						PersonHome home = (PersonHome) PortableRemoteObject.narrow(obj,PersonHome.class);
						Person person = null;
						person = home.findByPrimaryKey(PersonParentUid);
						if (person.getPersonVO().getThePersonDT().getBirthGenderCd() != null) {
						String Birth_Sex = person.getPersonVO().getThePersonDT().getBirthGenderCd();	
						patientMap.put(PrintConstants.BIRTH_SEX, Birth_Sex);
						}

						//Physician INV182
						} else if (physicianUid != null && personVO.getThePersonDT().getPersonUid()
								.compareTo(physicianUid) == 0) {
							patientMap.putAll(PagePrintUtil.putEntityNameAndAddressIntoMap(personVO, PrintConstants.PROVIDER_NAME_FACILITY, true, 
									PrintConstants.PROVIDER_ADDRESS, PrintConstants.PROVIDER_CITY, PrintConstants.STATE_STATE_SHORT_NAME, 
									null, PrintConstants.PROVIDER_PHONE, null));
						} //physician
					}
				return patientMap;
			}
		} catch (Exception e) {
			logger.error("Error while retrieving populateEntityValues:  "
					+ e.toString());
			throw new Exception(e.toString());
		}
		return null;
	}

	protected static void convertEthCd(Map<String, String> patientMap,
			String ethnicity) throws Exception {
		if (ethnicity
				.equalsIgnoreCase(PrintConstants.RACE_2135_2))
			ethnicity = PrintConstants.RACE_H;
		else if (ethnicity
				.equalsIgnoreCase(PrintConstants.RACE_2186_5))
			ethnicity = PrintConstants.RACE_N;
		else if (ethnicity
				.equalsIgnoreCase("UNK"))
				ethnicity =PrintConstants.RACE_U;
		patientMap.put(PrintConstants.DEM155,ethnicity);
	}

	protected static void convertRaceCD(Map<String, String> patientMap,
			String race, int i) {
		if(race.equals("1002-5")){
		patientMap.put(PrintConstants.RACE_DEM152 + i, PrintConstants.RACE_1002_5);
		}
		if(race.equals("2028-9")){
			patientMap.put(PrintConstants.RACE_DEM152 + i, PrintConstants.RACE_2028_9);
			}
		if(race.equals("2054-5")){
			patientMap.put(PrintConstants.RACE_DEM152 + i, PrintConstants.RACE_2054_5);
			}
		if(race.equals("2076-8")){
			patientMap.put(PrintConstants.RACE_DEM152 + i, PrintConstants.RACE_2076_8);
			}
		if(race.equals("2106-3")){
			patientMap.put(PrintConstants.RACE_DEM152 + i, PrintConstants.RACE_2106_3);
			}
		if(race.equals("ASKU") || race.equals("NI")|| race.equals("U")){
			patientMap.put(PrintConstants.RACE_DEM152 + i, PrintConstants.RACE_U);
			}
		if(race.equals("NASK") ){
			patientMap.put(PrintConstants.RACE_DEM152 + i, PrintConstants.RACE_NASK);
			}
		if(race.equals("PHC1175")){
				patientMap.put(PrintConstants.RACE_DEM152 + i, PrintConstants.RACE_PHC_1175);
				}
	}


	public static Map<String, String> populateLabValues(Long observationUid,Collection<Object> theLabReportSummaryVOCollection,
			 Map<String, String> labSummaryMap,int index) throws Exception {
        if (theLabReportSummaryVOCollection == null)
        	return labSummaryMap;


		try {
			int i = index;
			Collection<Object> coll = theLabReportSummaryVOCollection;
			ArrayList<LabReportSummaryVO> al = new ArrayList<LabReportSummaryVO>();


			for(Object o : coll){
				al.add((LabReportSummaryVO) o);
			}
			Collections.sort(al);
			if (al != null) {

				Iterator<LabReportSummaryVO> it = al.iterator();
				while (it.hasNext()) {
					LabReportSummaryVO labReportSummaryVO =(LabReportSummaryVO)it
							.next();
					
					ArrayList<String> LabReportUids = new ArrayList<String>();
					Iterator ite = labSummaryMap.entrySet().iterator();
					while (ite.hasNext()){
						Map.Entry pair = (Map.Entry)ite.next();
						if(pair.getKey().toString().startsWith("LAB_UID")){
							LabReportUids.add(pair.getValue().toString());
						}
					}
					if(!LabReportUids.contains(labReportSummaryVO.getObservationUid().toString())){
					// if(proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getReferralBasisCd().equalsIgnoreCase(MessageConstants.REFERRAL_CODE_FOR_LAB)){
					if (observationUid != null
							&& labReportSummaryVO.getObservationUid()
									.compareTo(observationUid) == 0) {
					}
					
					if (labReportSummaryVO
							.getTheResultedTestSummaryVOCollection() != null) {
						Iterator<Object> iter = labReportSummaryVO
								.getTheResultedTestSummaryVOCollection()
								.iterator();
						while (iter.hasNext()) {
							ResultedTestSummaryVO resultedSummaryVO = (ResultedTestSummaryVO) iter
									.next();
							
							
					
							String LAB220key = PrintConstants.LAB220_ + i;
							labSummaryMap.put(LAB220key, resultedSummaryVO.getResultedTest());

							String LAB220CDkey = PrintConstants.LAB_DETAILS + i;
							StringBuffer testValue = new StringBuffer();
							String resultCD="";
							if(resultedSummaryVO.getCodedResultValue() != null){
								CachedDropDownValues cddv= new CachedDropDownValues();
								//String rcd = cddv.getCdForCodedResultDescTxt(resultedSummaryVO.getCodedResultValue(), "LAB_RSLT");
								String rcd = resultedSummaryVO.getCodedResultValue();
								
								resultCD = (!rcd.isEmpty()) ? rcd :resultedSummaryVO.getCodedResultValue() ;
							}
							testValue
									.append(resultCD
											 == null ? PrintConstants.EMPTY_STRING
											: resultCD + "/");
							testValue
									.append(resultedSummaryVO
											.getNumericResultValue1() == null ? PrintConstants.EMPTY_STRING
											: resultedSummaryVO
													.getNumericResultValue1());
							testValue
									.append(resultedSummaryVO
											.getNumericResultSeperator() == null ? PrintConstants.EMPTY_STRING
											: resultedSummaryVO
													.getNumericResultSeperator());
							testValue
									.append(resultedSummaryVO
											.getNumericResultValue2() == null ? PrintConstants.EMPTY_STRING
											: resultedSummaryVO
													.getNumericResultValue2());
							testValue
									.append(resultedSummaryVO
											.getNumericResultUnits() == null ? PrintConstants.EMPTY_STRING
											: resultedSummaryVO
													.getNumericResultUnits());
							labSummaryMap
									.put(LAB220CDkey, testValue.toString());
							labSummaryMap.put(PrintConstants.ORD3_ + i,
									labReportSummaryVO.getReportingFacility());

							//Same date for every resulted test
							String LAB163key = PrintConstants.LAB163_ + i;
							
							if(labReportSummaryVO.getObservationUid() != null)
								labSummaryMap.put("LAB_UID" + i, labReportSummaryVO.getObservationUid().toString());
						
							if (labReportSummaryVO.getDateCollected() != null)
								labSummaryMap.put(LAB163key, StringUtils
										.formatDate(labReportSummaryVO
												.getDateCollected()));
							else{
								labSummaryMap.put(LAB163key, StringUtils
										.formatDate(labReportSummaryVO.getDateReceived()));
							}

							i++;
						}
					}else{
						i++;
					}

				}
			}
			}

			labSummaryMap.put("labIndex",String.valueOf(i));
			return labSummaryMap;
		} catch (Exception e) {
			logger.debug("Error while retrieving populateLabValues:  "
					+ e);
			logger.error("Error while retrieving populateLabValues:  "
					+ e.toString());
			throw new Exception(e.toString());
		}
	}



	public static Map<String, String> populateLabValuesWithProgramArea(Long observationUid,
			PageProxyVO proxyVO,Map<String, String> labSummaryMap, ArrayList<String> labsProcessed) throws Exception {
		PageActProxyVO actProxyVO = (PageActProxyVO) proxyVO;

		int stdLabIndex = 1;
		int hivLabIndex = 1;  
		if (labSummaryMap.containsKey("StdLabIndex"))
			stdLabIndex = Integer.parseInt(labSummaryMap.get("StdLabIndex"));
		if (labSummaryMap.containsKey("HivLabIndex"))
			hivLabIndex = Integer.parseInt(labSummaryMap.get("HivLabIndex"));

		try {

			Collection<Object> coll = actProxyVO
					.getTheLabReportSummaryVOCollection();


			
			if (coll != null) {
				int thisIndex = stdLabIndex;
				Iterator<Object> collIter = coll.iterator();
				while (collIter.hasNext()) {

					LabReportSummaryVO labReportSummaryVO = (LabReportSummaryVO) collIter.next();
					if(!labsProcessed.contains(labReportSummaryVO.getLocalId())){
					//There is an STD and a HIV lab section on the form
					//We prepend the Program Area to the key and append the item number
								String PrAreaCD = labReportSummaryVO.getProgramArea();
					if (PropertyUtil.isSTDProgramArea(PrAreaCD)) {
						PrAreaCD = PrintConstants.STD;
						thisIndex = stdLabIndex;
					} else if (PropertyUtil.isHIVProgramArea(PrAreaCD)) {
						PrAreaCD = PrintConstants.HIV;
						thisIndex = hivLabIndex;
					} else PrAreaCD = PrintConstants.STD;

					if (thisIndex > 4)
						continue; //out of room

								if (observationUid != null
										&& labReportSummaryVO.getObservationUid()
												.compareTo(observationUid) == 0) {

									StringBuffer providerBuffer =labReportSummaryVO.getProviderDataForPrintVO()!= null?
											populateFromPrintVO(
											labSummaryMap, labReportSummaryVO)
											:new StringBuffer();

									if(providerBuffer.toString().trim().length() >0 ) providerBuffer.insert(0,PrAreaCD);

									labSummaryMap.put(PrintConstants.OTHER_INFO,
											providerBuffer.toString());
								}



					labSummaryMap.put(PrAreaCD+"TestType" +thisIndex, labReportSummaryVO.getType());
											if (labReportSummaryVO
													.getTheResultedTestSummaryVOCollection() != null) {
						

												Iterator<Object> iter = labReportSummaryVO
														.getTheResultedTestSummaryVOCollection()
														.iterator();
												while (iter.hasNext()) {
							
							
													ResultedTestSummaryVO resultedSummaryVO = (ResultedTestSummaryVO) iter
															.next();
							
							String LAB220key = PrAreaCD+ PrintConstants.LAB220_ + thisIndex;
													labSummaryMap.put(LAB220key,
															resultedSummaryVO.getResultedTest());

							String LAB220CDkey = PrAreaCD +PrintConstants.LAB_QUAN_RESUL + thisIndex;


													StringBuffer testValue = new StringBuffer();
													testValue
															.append(resultedSummaryVO
																	.getNumericResultCompare() == null ? PrintConstants.EMPTY_STRING
																	: resultedSummaryVO
																			.getNumericResultCompare());
													testValue
															.append(resultedSummaryVO
																	.getNumericResultValue1() == null ? PrintConstants.EMPTY_STRING
																	: resultedSummaryVO
																			.getNumericResultValue1());
													testValue
															.append(resultedSummaryVO
																	.getNumericResultSeperator() == null ? PrintConstants.EMPTY_STRING
																	: resultedSummaryVO
																			.getNumericResultSeperator());
													testValue
															.append(resultedSummaryVO
																	.getNumericResultValue2() == null ? PrintConstants.EMPTY_STRING
																	: resultedSummaryVO
																			.getNumericResultValue2());
													testValue
															.append(resultedSummaryVO
																	.getNumericResultUnits() == null ? PrintConstants.EMPTY_STRING
																	: resultedSummaryVO
																			.getNumericResultUnits());
							String LAB121_CD_Key = PrAreaCD + PrintConstants.LAB121_CD_ + thisIndex;
													labSummaryMap.put(LAB121_CD_Key, checkNull(resultedSummaryVO.getCodedResultValue()));
													labSummaryMap
															.put(LAB220CDkey, testValue.toString());

							labSummaryMap.put(PrAreaCD +PrintConstants.ORD3_ + thisIndex,
															checkNull(labReportSummaryVO.getReportingFacility()));

							labSummaryMap.put(PrAreaCD+"SpecimenSource" +thisIndex, getSpecimenSourceCode(labReportSummaryVO.getSpecimenSource()) );
							
							
							//Date for every resulted test of a lab report
							String LAB163key =PrAreaCD + PrintConstants.LAB163_ + thisIndex;
							if (labReportSummaryVO.getDateCollected() != null)
								labSummaryMap.put(LAB163key, StringUtils
										.formatDate(labReportSummaryVO
												.getDateCollected()));
							else{
								labSummaryMap.put(LAB163key, StringUtils
										.formatDate(labReportSummaryVO.getDateReceived()));
							}
							
							if (PrAreaCD.equals(PrintConstants.STD)) 
								++stdLabIndex;
							else
								++hivLabIndex;
							
							thisIndex++;
						
						} //hasNext lab result
										}else{
						labSummaryMap.put(PrAreaCD+"SpecimenSource" +thisIndex, getSpecimenSourceCode(labReportSummaryVO.getSpecimenSource()) );
						if (PrAreaCD.equals(PrintConstants.STD)) 
							++stdLabIndex;
						else
							++hivLabIndex;
										}
					labsProcessed.add(labReportSummaryVO.getLocalId());
				}
				} //has next
			} //coll != null

			labSummaryMap.put("StdLabIndex", String.valueOf(stdLabIndex));
			labSummaryMap.put("HivLabIndex", String.valueOf(hivLabIndex));
			return labSummaryMap;
			
		} catch (Exception e) {
			logger.debug("Error while retrieving populateLabValues:  "
					+ e.getMessage());
			logger.error("Error while retrieving populateLabValues:  "
					+ e.toString());
			throw new Exception(e.toString());
		}
	}



	private static String getSpecimenSourceCode(String specimenSourceTxt) {
		String returnString = PrintConstants.EMPTY_STRING;

		if (specimenSourceTxt != null) {
			String specimenSource = CachedDropDowns.getCdForCdDescTxt(specimenSourceTxt,
					"SPECMN_SRC");
			if (specimenSource != null) {
				if (specimenSource.equalsIgnoreCase("ASP")) {
					returnString = "08";
				} else if (specimenSource.equalsIgnoreCase("BLD")
						|| specimenSource.equalsIgnoreCase("BLDA")
						|| specimenSource.equalsIgnoreCase("BLDC")
						|| specimenSource.equalsIgnoreCase("BLDCO")
						|| specimenSource.equalsIgnoreCase("BLDV")) {
					returnString = "13";
				} else if (specimenSource.equalsIgnoreCase("CSF")) {
					returnString = "14";
				} else if (specimenSource.equalsIgnoreCase("CVX")) {
					returnString = "01";
				} else if (specimenSource.equalsIgnoreCase("LESEX")) {
					returnString = "03";
				} else if (specimenSource.equalsIgnoreCase("LESG")) {
					returnString = "02";
				} else if (specimenSource.equalsIgnoreCase("LMAS")) {
					returnString = "04";
				} else if (specimenSource.equalsIgnoreCase("ORH")) {
					returnString = "07";
				} else if (specimenSource.equalsIgnoreCase("REC")) {
					returnString = "09";
				} else if (specimenSource.equalsIgnoreCase("UR")) {
					returnString = "11";
				} else if (specimenSource.equalsIgnoreCase("URTH")) {
					returnString = "10";
				} else if (specimenSource.equalsIgnoreCase("31389004")) {
					returnString = "05";
				} else if (specimenSource.equalsIgnoreCase("GENV")) {
					returnString = "12";
				} else if (specimenSource.equalsIgnoreCase("NA")) {
					returnString = "88";
				} else if (specimenSource.equalsIgnoreCase("UNK")) {
					returnString = "99";
				}
			}
		}
		return returnString;
	}

	public static StringBuffer populateOtherInfoFromPrintVO(Long observationUid, PageForm pageForm, PageActProxyVO actProxyVO, Map<String, String> labSummaryMap, HttpServletRequest request){
		
		NBSSecurityObj nbsSecurityObj = null;
		HttpSession session = request.getSession();
		StringBuffer otherInfo = new StringBuffer();
		Long entityUID = null;
		
		if(pageForm.getPageClientVO().getAnswer(PageConstants.OTHER_IDENTIFYING_INFORMATION) != null)otherInfo.append("Other Identifying Info: " + pageForm.getPageClientVO().getAnswer(PageConstants.OTHER_IDENTIFYING_INFORMATION));
		
		Iterator<Object> actEntityIter = actProxyVO.getPageVO().getActEntityDTCollection().iterator();
		while (actEntityIter.hasNext()) {
			NbsActEntityDT  actEntityDT = (NbsActEntityDT) actEntityIter.next();
			if(actEntityDT.getTypeCd().equals(NEDSSConstants.PHC_PHYSICIAN)){
			 entityUID = actEntityDT.getEntityUid();
			if (entityUID != null && entityUID > 0) {
				//PersonVO providerInfoVO=getProvider(entityUID,nbsSecurityObj,request);
				try{
				PersonVO providerInfo=InvestigationUtil.getInvestigator(entityUID.toString(),nbsSecurityObj,request);
				if(providerInfo.getThePersonDT().getFirstNm() != null)otherInfo.append("\n" + "Provider: " + providerInfo.getThePersonDT().getFirstNm());
				if(providerInfo.getThePersonDT().getLastNm() != null)otherInfo.append(" " + providerInfo.getThePersonDT().getLastNm());
				Iterator<Object> entityLocatorIter = providerInfo.theEntityLocatorParticipationDTCollection.iterator();
				while (entityLocatorIter.hasNext()){
					EntityLocatorParticipationDT entityLocator = (EntityLocatorParticipationDT) entityLocatorIter.next();
					if(entityLocator.getEntityUid().equals(entityUID) && entityLocator.getThePostalLocatorDT() != null){
						if(entityLocator.getThePostalLocatorDT().getStreetAddr1() != null)otherInfo.append(", " + entityLocator.getThePostalLocatorDT().getStreetAddr1());
						if(entityLocator.getThePostalLocatorDT().getStreetAddr2() != null)otherInfo.append(", " + entityLocator.getThePostalLocatorDT().getStreetAddr2());
						if(entityLocator.getThePostalLocatorDT().getCityDescTxt() != null)otherInfo.append(", " + entityLocator.getThePostalLocatorDT().getCityDescTxt());
						if(entityLocator.getThePostalLocatorDT().getZipCd() != null)otherInfo.append(" - " + entityLocator.getThePostalLocatorDT().getZipCd());
						if(entityLocator.getThePostalLocatorDT().getCntryDescTxt() != null)otherInfo.append( "," + entityLocator.getThePostalLocatorDT().getCntryDescTxt());
						}
					if(entityLocator.getEntityUid().equals(entityUID) && entityLocator.getTheTeleLocatorDT() != null){
						if(entityLocator.getTheTeleLocatorDT().getEmailAddress() != null)otherInfo.append("\n"+ "Email: " + entityLocator.getTheTeleLocatorDT().getEmailAddress());
						if(entityLocator.getTheTeleLocatorDT().getPhoneNbrTxt() != null)otherInfo.append(", "+ "Phone: " + entityLocator.getTheTeleLocatorDT().getPhoneNbrTxt());
						}
					}
				} catch (Exception e) {
					logger.error("Error while retrieving populateOtherIdentifyingInformationValues:  "
							+ e.toString());
				}
				
				}
			}
		}
		
		labSummaryMap.put(PrintConstants.OTHER_INFO, otherInfo.toString());
		
		return otherInfo;
	}
	private static StringBuffer populateFromPrintVO(Map<String, String> labSummaryMap,LabReportSummaryVO labReportSummaryVO) {
		StringBuffer facilityBuff = new StringBuffer();
		facilityBuff
				.append(labReportSummaryVO
						.getProviderFullNameForPrint() == null ? PrintConstants.EMPTY_STRING
						: labReportSummaryVO
								.getProviderFullNameForPrint());
		String name = labReportSummaryVO
				.getProviderDataForPrintVO()
				.getFacilityName();
		facilityBuff
				.append(name == null ? " " : "/"
						+ name);
		String Providerkey = PrintConstants.PROVIDER_NAME_FACILITY;
		labSummaryMap.put(Providerkey, facilityBuff.toString());
		StringBuffer facilityAddress = new StringBuffer();
		facilityAddress
				.append(labReportSummaryVO
						.getProviderDataForPrintVO()
						.getFacilityAddress1() == null ? PrintConstants.EMPTY_STRING
						: labReportSummaryVO
								.getProviderDataForPrintVO()
								.getFacilityAddress1());
		facilityAddress
				.append(labReportSummaryVO
						.getProviderDataForPrintVO()
						.getFacilityAddress2() == null ? PrintConstants.EMPTY_STRING
						: " "
								+ labReportSummaryVO
										.getProviderDataForPrintVO()
										.getFacilityAddress2());
		labSummaryMap.put(PrintConstants.PROVIDER_ADDRESS,
				facilityAddress.toString());

		if (labReportSummaryVO.getProviderDataForPrintVO()
				.getFacilityCity() != null)
			labSummaryMap.put(PrintConstants.PROVIDER_CITY,
					labReportSummaryVO
							.getProviderDataForPrintVO()
							.getFacilityCity());

		{
			String facilityStateCode = labReportSummaryVO
					.getProviderDataForPrintVO()
					.getFacilityState();

			String facilityStateDesc = cache
					.getStateAbbreviationByCode(facilityStateCode);
			if (facilityStateDesc != null
					&& !facilityStateDesc
							.trim()
							.equalsIgnoreCase(
									PrintConstants.EMPTY_STRING)) {
				labSummaryMap.put(
						PrintConstants.STATE_SHORT_NAME,
						facilityStateDesc.toString());
			}
		}
		labSummaryMap.put(PrintConstants.PROVIDER_PHONE,
				labReportSummaryVO.getProviderDataForPrintVO()
						.getProviderPhone());
		StringBuffer providerBuffer = new StringBuffer();
		providerBuffer
				.append(labReportSummaryVO
						.getProviderFullNameForPrint() == null ? PrintConstants.EMPTY_STRING
						: labReportSummaryVO
								.getProviderFullNameForPrint());
		providerBuffer
				.append(labReportSummaryVO
						.getProviderDataForPrintVO()
						.getProviderStreetAddress1() == null ? PrintConstants.EMPTY_STRING
						: "\n"
								+ labReportSummaryVO
										.getProviderDataForPrintVO()
										.getProviderStreetAddress1());
		providerBuffer
				.append(labReportSummaryVO
						.getProviderDataForPrintVO()
						.getProviderCity() == null ? PrintConstants.EMPTY_STRING
						: "\n"
								+ labReportSummaryVO
										.getProviderDataForPrintVO()
										.getProviderCity());
		{
			String providerStateCode = labReportSummaryVO
					.getProviderDataForPrintVO()
					.getProviderState();

			String providerDesc = cache
					.getStateAbbreviationByCode(providerStateCode);
			if (providerDesc != null
					&& !providerDesc.trim().equalsIgnoreCase(
							PrintConstants.EMPTY_STRING)) {
				providerBuffer.append("\n" + providerDesc);
			}
		}
		return providerBuffer;
	}

	/**
	 *
	 * @param observationUid
	 * @param proxyVO
	 * @param index
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> populateMorbidityValues(
			Long observationUid, PageProxyVO proxyVO,Map<String, String> morbSummaryMap, int index) throws Exception {
		PageActProxyVO actProxyVO = (PageActProxyVO) proxyVO;

		try {
			Collection<Object> coll = actProxyVO
					.getTheMorbReportSummaryVOCollection();
			if (coll != null) {
				Iterator<Object> it = coll.iterator();
				while (it.hasNext()) {
					MorbReportSummaryVO morbReportSummaryVO = (MorbReportSummaryVO) it
							.next();
					if (observationUid != null
							&& morbReportSummaryVO.getObservationUid()
									.compareTo(observationUid) == 0) {
						StringBuffer facilityBuff = new StringBuffer();
						facilityBuff
								.append(morbReportSummaryVO
										.getProviderFullNameForPrint() == null ? PrintConstants.EMPTY_STRING
										: morbReportSummaryVO
												.getProviderFullNameForPrint());
						facilityBuff
								.append(morbReportSummaryVO
										.getProviderDataForPrintVO()
										.getFacility() == null ? " " : "/"
										+ morbReportSummaryVO
												.getProviderDataForPrintVO()
												.getFacility());
						String Providerkey = PrintConstants.PROVIDER_NAME_FACILITY;
						morbSummaryMap
								.put(Providerkey, facilityBuff.toString());
						StringBuffer facilityAddress = new StringBuffer();
						facilityAddress
								.append(morbReportSummaryVO
										.getProviderDataForPrintVO()
										.getFacilityAddress1() == null ? PrintConstants.EMPTY_STRING
										: morbReportSummaryVO
												.getProviderDataForPrintVO()
												.getFacilityAddress1());
						facilityAddress
								.append(morbReportSummaryVO
										.getProviderDataForPrintVO()
										.getFacilityAddress2() == null ? PrintConstants.EMPTY_STRING
										: " "
												+ morbReportSummaryVO
														.getProviderDataForPrintVO()
														.getFacilityAddress2());
						morbSummaryMap.put(PrintConstants.PROVIDER_ADDRESS,
								facilityAddress.toString());

						if (morbReportSummaryVO.getProviderDataForPrintVO()
								.getFacilityCity() != null)
							morbSummaryMap.put(PrintConstants.PROVIDER_CITY,
									morbReportSummaryVO
											.getProviderDataForPrintVO()
											.getFacilityCity());

						{
							String facilityStateCode = morbReportSummaryVO
									.getProviderDataForPrintVO()
									.getFacilityState();

							String facilityStateDesc = cache
									.getStateAbbreviationByCode(facilityStateCode);
							if (facilityStateDesc != null
									&& !facilityStateDesc
											.trim()
											.equalsIgnoreCase(
													PrintConstants.EMPTY_STRING)) {
								morbSummaryMap.put(
										PrintConstants.STATE_STATE_SHORT_NAME,
										facilityStateDesc.toString());
							}
						}
						morbSummaryMap.put(PrintConstants.PROVIDER_PHONE,
								morbReportSummaryVO.getProviderDataForPrintVO()
										.getProviderPhone());
						StringBuffer providerBuffer = new StringBuffer();
						providerBuffer
								.append(morbReportSummaryVO
										.getProviderFullNameForPrint() == null ? PrintConstants.EMPTY_STRING
										: morbReportSummaryVO
												.getProviderFullNameForPrint());
						providerBuffer
								.append(morbReportSummaryVO
										.getProviderDataForPrintVO()
										.getProviderStreetAddress1() == null ? PrintConstants.EMPTY_STRING
										: "\n"
												+ morbReportSummaryVO
														.getProviderDataForPrintVO()
														.getProviderStreetAddress1());
						providerBuffer
								.append(morbReportSummaryVO
										.getProviderDataForPrintVO()
										.getProviderCity() == null ? PrintConstants.EMPTY_STRING
										: "\n"
												+ morbReportSummaryVO
														.getProviderDataForPrintVO()
														.getProviderCity());
						{
							String providerStateCode = morbReportSummaryVO
									.getProviderDataForPrintVO()
									.getProviderState();

							String providerDesc = cache
									.getStateAbbreviationByCode(providerStateCode);
							if (providerDesc != null
									&& !providerDesc.trim().equalsIgnoreCase(
											PrintConstants.EMPTY_STRING)) {
								providerBuffer.append("\n" + providerDesc);
							}
						}
						morbSummaryMap.put(PrintConstants.OTHER_INFO,
								providerBuffer.toString());


					}
					
					populateLabValues(observationUid, morbReportSummaryVO.getTheLabReportSummaryVOColl(), morbSummaryMap, index);
				}
			}

			return morbSummaryMap;
		} catch (Exception e) {
			logger.error("Error while retrieving populateMorbidityValues:  "
					+ e.toString());
			throw new Exception(e.toString());
		}

	}

	/**
	 *
	 * @param proxyVO
	 * @return
	 * @throws Exception
	 */
	public static ActRelationshipDT getSourceAct(PageProxyVO proxyVO)
			throws Exception {
		try {
			Timestamp addTime = proxyVO.getPublicHealthCaseVO()
					.getThePublicHealthCaseDT().getAddTime();
			Collection<Object> coll = proxyVO.getPublicHealthCaseVO()
					.getTheActRelationshipDTCollection();
			Iterator<Object> iterator = coll.iterator();
			while (iterator.hasNext()) {
				ActRelationshipDT ar = (ActRelationshipDT) iterator.next();
//				DateFormat format =  DateFormat.getDateInstance(DateFormat.MEDIUM);
//
//				if (ar.getFromTime() != null
//						&& format.format(addTime).equals(format.format(ar.getFromTime())) )
				if (ar.getFromTime() != null
						&& addTime.compareTo(ar.getFromTime())==0 )
					return ar;
			}
		} catch (Exception e) {
			logger.error("Error while retrieving populateMorbidityValues:  "
					+ e.toString());
			throw new Exception(e.toString());
		}

		return null;

	}

	public static Map<String, String> getCoinfectionInvestigations(HttpServletRequest request) throws NEDSSAppException,Exception{
		Map<String, String> returnMap = new HashMap<String, String>();
		String sPublicHealthCaseUID = (String) NBSContext.retrieve(request.getSession(),
				NBSConstantUtil.DSInvestigationUid);

		PageProxyVO proxyVO = (PageActProxyVO) getPrintProxyObject(
				sPublicHealthCaseUID, request.getSession());

		String coinfCd = proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCoinfectionId();
		Long currPHCUid = proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid();

		PersonVO personVO;
		try {
			personVO = PageLoadUtil.getPersonVO(NEDSSConstants.PHC_PATIENT, proxyVO);

			Long personUID = personVO.getThePersonDT().getPersonParentUid();

			 String sBeanJndiName = JNDINames.WORKUP_PROXY_EJB;
	         String sMethod = "getWorkupProxy";
	         Object[] oParams = new Object[] {personUID};
	         MainSessionHolder holder = new MainSessionHolder();
	         MainSessionCommand msCommand;

				msCommand = holder.getMainSessionCommand(request.getSession());

	         ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod,
	                                                  oParams);
	         WorkupProxyVO workupProxyVO = (WorkupProxyVO) arr.get(0);

			Collection<Object> coll = workupProxyVO.getTheInvestigationSummaryVOCollection();

			for(Object o : coll){
				if (o instanceof InvestigationSummaryVO){
					if (((InvestigationSummaryVO)o).getCoinfectionId() != null &&((InvestigationSummaryVO)o).getCoinfectionId().equals(coinfCd) && ((InvestigationSummaryVO)o).getPublicHealthCaseUid().longValue() != currPHCUid.longValue()){
						returnMap.put(((InvestigationSummaryVO)o).getPublicHealthCaseUid().toString(), ((InvestigationSummaryVO)o).getConditionCodeText());
					}
				}
			}
		} catch (NEDSSAppException e) {
			logger.error("Error while retrieving getCoInfectionInvestigation:  "
					+ e.toString());
			throw new NEDSSAppException(e.toString());
		} catch (Exception e) {
			logger.error("Error while retrieving getCoInfectionInvestigation:  "
					+ e.toString());
			throw new Exception(e.toString());
		}

		return returnMap;
	}


	public static Long getCoinfectionInvestigations(String sPublicHealthCaseUID,HttpSession session) throws NEDSSAppException,Exception{

		PageProxyVO proxyVO = (PageActProxyVO) getPrintProxyObject(
				sPublicHealthCaseUID, session);

		String coinfCd = proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCoinfectionId();
		Long currPHCUid = proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid();

		PersonVO personVO;
		try {
			personVO = PageLoadUtil.getPersonVO(NEDSSConstants.PHC_PATIENT, proxyVO);

			Long personUID = personVO.getThePersonDT().getPersonParentUid();

			 String sBeanJndiName = JNDINames.WORKUP_PROXY_EJB;
	         String sMethod = "getWorkupProxy";
	         Object[] oParams = new Object[] {personUID};
	         MainSessionHolder holder = new MainSessionHolder();
	         MainSessionCommand msCommand;

				msCommand = holder.getMainSessionCommand(session);

	         ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod,
	                                                  oParams);
	         WorkupProxyVO workupProxyVO = (WorkupProxyVO) arr.get(0);

			Collection<Object> coll = workupProxyVO.getTheInvestigationSummaryVOCollection();

			for(Object o : coll){
				if (o instanceof InvestigationSummaryVO){
					if (((InvestigationSummaryVO)o).getCoinfectionId() != null &&((InvestigationSummaryVO)o).getCoinfectionId().equals(coinfCd) && ((InvestigationSummaryVO)o).getPublicHealthCaseUid().longValue() != currPHCUid.longValue()){
						return((InvestigationSummaryVO)o).getPublicHealthCaseUid();
					}
				}
			}
		} catch (NEDSSAppException e) {
			logger.error("Error while retrieving getCoInfectionInvestigation:  "
					+ e.toString());
			throw new NEDSSAppException(e.toString());
		} catch (Exception e) {
			logger.error("Error while retrieving getCoInfectionInvestigation:  "
					+ e.toString());
			throw new Exception(e.toString());
		}

		return 0L;
	}

	public static List<InvestigationSummaryVO> getCoinfectionInvestigations(Long personUID,HttpSession session) throws NEDSSAppException,Exception{
		List<InvestigationSummaryVO> list = new ArrayList<InvestigationSummaryVO>();
		try{
			 String sBeanJndiName = JNDINames.WORKUP_PROXY_EJB;
	         String sMethod = "getWorkupProxy";
	         Object[] oParams = new Object[] {personUID};
	         MainSessionHolder holder = new MainSessionHolder();
	         MainSessionCommand msCommand;

				msCommand = holder.getMainSessionCommand(session);

	         ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod,
	                                                  oParams);
	         WorkupProxyVO workupProxyVO = (WorkupProxyVO) arr.get(0);

			Collection<Object> coll = workupProxyVO.getTheInvestigationSummaryVOCollection();

			for(Object o : coll){
				if (o instanceof InvestigationSummaryVO){
					if (((InvestigationSummaryVO)o).getCoinfectionId() != null ){
						list.add(((InvestigationSummaryVO)o));
					}
				}
			}
	} catch (NEDSSAppException e) {
		logger.error("Error while retrieving getCoInfectionInvestigation:  "
				+ e.toString());
		throw new NEDSSAppException(e.toString());
	} catch (Exception e) {
		logger.error("Error while retrieving getCoInfectionInvestigation:  "
				+ e.toString());
		throw new Exception(e.toString());
	}

	return list;
	}



	public static void populateMappedCase(Map<String, String> mappedDiagnosis,
			PageProxyVO proxyVO, int i, PageForm pageForm) {
		mappedDiagnosis.put("Case_Id_" + i, proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getLocalId());
		//mappedDiagnosis.put("Cond_cd_" + i, PamPrintUtil.addEmptySpaces(checkNull( getAltCond(proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd()))));
		mappedDiagnosis.put("Cond_cd_" + i,PamPrintUtil.addEmptySpaces(checkNull((String) pageForm.getPageClientVO().getAnswerMap().get("NBS136"))));
		mappedDiagnosis.put("NBS160_" + i, proxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().getFieldRecordNumber());


	}

	public static void populateMappedConditions(
			Map<String, String> mappedDiagnosis, PageActProxyVO proxyVO,HttpSession session,int i) {
//		PageActProxyVO actProxyVO = (PageActProxyVO)proxyVO;
		try {
			for(Object o : proxyVO.getTheCTContactSummaryDTCollection()){
				CTContactSummaryDT dt = (CTContactSummaryDT)o;
				if(dt.isPatientNamedByContact()&& dt.getContactProcessingDecisionCd().equals(NEDSSConstants.FF) ){
					mappedDiagnosis.put("op_cond_" + i, checkNull(getAltCond(dt.getSubjectPhcCd())));
					if(dt.getSubjectEntityPhcUid() != null) mappedDiagnosis.put("OP_Case_Id_" + i, dt.getSubjectPhcLocalId());
				}
			}
			String importedCD=proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getDiseaseImportedCd();
		if(importedCD != null){
			mappedDiagnosis.put("INV152_" + i, importedCD);
			if(importedCD.contains("C") ) {
				String desc =CachedDropDowns.getCodeDescTxtForCd(checkNull(proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getImportedCountryCd()),"PSL_CNTRY");
				desc = desc != null ? desc :checkNull(proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getImportedCountryCd());

				mappedDiagnosis.put("ImportLocation_" + i, desc);
			}
			if(importedCD.contains("S") ){
				String desc =CachedDropDowns.getCodeDescTxtForCd(checkNull(proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getImportedStateCd()),"STATE_CCD");
				desc = desc != null ? desc :checkNull(proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getImportedStateCd());

				mappedDiagnosis.put("ImportLocation_" + i,desc);
			}
			if(importedCD.contains("J") ){
					if (proxyVO.getPublicHealthCaseVO()
							.getThePublicHealthCaseDT().getImportedStateCd() != null) {
						String countyCd = proxyVO.getPublicHealthCaseVO()
								.getThePublicHealthCaseDT()
								.getImportedCountyCd();
						if (countyCd != null) {
							if (countyCodes.containsKey(countyCd)) {
								countyCd = (String) countyCodes.get(countyCd);
							}

						}
						mappedDiagnosis.put("ImportLocation_" + i,
								checkNull(proxyVO.getPublicHealthCaseVO()
										.getThePublicHealthCaseDT()
										.getImportedCityDescTxt())
										+ " " + countyCd);
					}
			}
		}
		mappedDiagnosis.put(i+"_INV159", PamPrintUtil.addEmptySpaces(getDMPrintCode(checkNull(proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getDetectionMethodCd()))));
		mappedDiagnosis.put(i+"_INV152", checkNull(proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getDiseaseImportedCd()));
		if(proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getInfectiousToDate() != null
				&& proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getInfectiousFromDate() != null){
			Calendar after = Calendar.getInstance();
			after.setTimeInMillis(proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getInfectiousToDate().getTime());
			Calendar before = Calendar.getInstance();
			before.setTimeInMillis(proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getInfectiousFromDate().getTime());
			int months = elapsed(before,after,Calendar.MONTH);
			mappedDiagnosis.put("IxPeriod_"+ i,String.valueOf(months));
		}
		if(proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getActivityFromTime() != null){

			mappedDiagnosis.put("INV147_" + i, StringUtils.formatDate(proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getActivityFromTime()));
		}
		if(proxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().getPatIntvStatusCd() != null ){

			if(	proxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().getPatIntvStatusCd().equals("I")){
				mappedDiagnosis.put("NBS192_"+i, "Yes");
			}else{
				String desc =CachedDropDowns.getCodeDescTxtForCd(proxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().getPatIntvStatusCd(),"PAT_INTVW_STATUS");
				desc = desc != null ? desc :proxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().getPatIntvStatusCd();
				mappedDiagnosis.put("Not_Ixd_Oth" + i ,desc);
				mappedDiagnosis.put("NBS192_"+i, "No");
			}
		}

		PersonVO interviewer = PageLoadUtil.getPersonVO("InterviewerOfPHC", (PageActProxyVO)proxyVO);


		PersonVO initInterviewerOfPHC = PageLoadUtil.getPersonVO("InitInterviewerOfPHC", (PageActProxyVO)proxyVO);

		if(interviewer != null && initInterviewerOfPHC != null
				&&(
							proxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().getInitInterviewAssignedDate() != null
							&& proxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().getInterviewAssignedDate() != null
								&& proxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().getInitInterviewAssignedDate()
									  .compareTo(proxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().getInterviewAssignedDate()) != 0
						  ||
							initInterviewerOfPHC.getThePersonDT().getPersonParentUid().compareTo(interviewer.getThePersonDT().getPersonParentUid())!=0 )){


			 String initnterviewerfullName = initInterviewerOfPHC.getEntityIdDT_s(0).getRootExtensionTxt();
			 mappedDiagnosis.put(i +"_NBS188" , checkNull(initnterviewerfullName));
			 mappedDiagnosis.put(i +"_NBS189" , StringUtils.formatDate(proxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().getInitInterviewAssignedDate()));


			 String interviewerfullName = interviewer.getEntityIdDT_s(0).getRootExtensionTxt();
			 if(interviewerfullName != null){
			 mappedDiagnosis.put(i +"_NBS186" , interviewerfullName);
			 mappedDiagnosis.put( i +"_NBS187" , StringUtils.formatDate(proxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().getInterviewAssignedDate()));
			 }
		}else{
			if(interviewer != null){

				 String interviewerfullName = interviewer.getEntityIdDT_s(0).getRootExtensionTxt();
				 mappedDiagnosis.put(i +"_NBS188" , checkNull(interviewerfullName));
			}
 			if(proxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().getInterviewAssignedDate() != null){
			mappedDiagnosis.put(i +"_NBS189" , StringUtils.formatDate(proxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().getInterviewAssignedDate()));
			}
		}
		getInterviewVOInfo(mappedDiagnosis, proxyVO, session, i);

		processRepeatingQuestions(((PageActProxyVO)proxyVO).getPageVO().getPageRepeatingAnswerDTMap(),((PageActProxyVO)proxyVO).getPageVO().getPamAnswerDTMap(), mappedDiagnosis, "NBS136", i);

		processRepeatingQuestions(((PageActProxyVO)proxyVO).getPageVO().getPageRepeatingAnswerDTMap(),((PageActProxyVO)proxyVO).getPageVO().getPamAnswerDTMap(), mappedDiagnosis, "NBS129", i);
		processRepeatingQuestions(((PageActProxyVO)proxyVO).getPageVO().getPageRepeatingAnswerDTMap(),((PageActProxyVO)proxyVO).getPageVO().getPamAnswerDTMap(), mappedDiagnosis, "NBS130", i);
		processRepeatingQuestions(((PageActProxyVO)proxyVO).getPageVO().getPageRepeatingAnswerDTMap(),((PageActProxyVO)proxyVO).getPageVO().getPamAnswerDTMap(), mappedDiagnosis, "NBS131", i);
		processRepeatingQuestions(((PageActProxyVO)proxyVO).getPageVO().getPageRepeatingAnswerDTMap(),((PageActProxyVO)proxyVO).getPageVO().getPamAnswerDTMap(), mappedDiagnosis, "NBS132", i);
		processRepeatingQuestions(((PageActProxyVO)proxyVO).getPageVO().getPageRepeatingAnswerDTMap(),((PageActProxyVO)proxyVO).getPageVO().getPamAnswerDTMap(), mappedDiagnosis, "NBS133", i);
		processRepeatingQuestions(((PageActProxyVO)proxyVO).getPageVO().getPageRepeatingAnswerDTMap(),((PageActProxyVO)proxyVO).getPageVO().getPamAnswerDTMap(), mappedDiagnosis, "NBS134", i);

			if(proxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().getCaseClosedDate() != null){
				mappedDiagnosis.put("NBS196_"+i, StringUtils.formatDate(proxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().getCaseClosedDate()) );
			}

			PersonVO closureInvestgrOfPHC =PageLoadUtil.getPersonVO("ClosureInvestgrOfPHC", proxyVO);
			if(closureInvestgrOfPHC != null){
				mappedDiagnosis.put("NBS197_"+i,closureInvestgrOfPHC.getEntityIdDT_s(0).getRootExtensionTxt());
			}

			PersonVO supPersonVO =PageLoadUtil.getPersonVO("CASupervisorOfPHC", proxyVO);

			if(supPersonVO != null){
				mappedDiagnosis.put("NBS190_"+i,supPersonVO.getEntityIdDT_s(0).getRootExtensionTxt());
			}



			if(proxyVO.getTheLabReportSummaryVOCollection() != null ){
				TreeSet<LabReportSummaryVO> set = new TreeSet<LabReportSummaryVO>();
				for(Object o : proxyVO.getTheLabReportSummaryVOCollection()){
					set.add((LabReportSummaryVO) o);
				}

				if(set.size() >0) mappedDiagnosis.put("LAB201_"+i, StringUtils.formatDate(set.first().getDateReceived()));
			}

		} catch (NEDSSAppException e) {
			logger.error("PagePrintProxyToPrintableForm.populateMappedConditions Exception thrown, "
					+ e);
		} catch (Exception e) {
			logger.error("PagePrintProxyToPrintableForm.populateMappedConditions Exception thrown, "
					+ e);		}

	}

	private static String getDMPrintCode(String detMethod) {
		if(detMethod.equals(PrintConstants.Screening_procedure)){
			return "20";
		}else if(detMethod.equals(PrintConstants.Self_referral)){
			return "21";
		}else if(detMethod.equals(PrintConstants.INV159_other)){
			return "88";
		}else if(detMethod.equals(PrintConstants.Cluster_related)){
			return "24";
		}else if(detMethod.equals(PrintConstants.Health_Department_Referred_Partner)){
			return "23";
		}else if(detMethod.equals(PrintConstants.Patient_Referred_Partner)){
			return "22";
		}
		return detMethod;
	}

	private static void getInterviewVOInfo(Map<String, String> mappedDiagnosis,
			PageActProxyVO proxyVO, HttpSession session, int i)
			throws RemoteException, Exception, NEDSSAppException {
		if(proxyVO.getTheInterviewSummaryDTCollection()!=null && proxyVO.getTheInterviewSummaryDTCollection().size()>0){
			Iterator iterator= proxyVO.getTheInterviewSummaryDTCollection().iterator();
			while(iterator.hasNext()){
				InterviewSummaryDT interviewSummaryDT = (InterviewSummaryDT)iterator.next();
				if(interviewSummaryDT != null){
						InterviewCaseUtil interviewCaseUtil = new InterviewCaseUtil();
						NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session.getAttribute("NBSSecurityObject");
						PageProxyVO interviewPageProxyVO = interviewCaseUtil.getPageProxyVO(interviewSummaryDT.getInterviewUid(), nbsSecurityObj);

						if(interviewSummaryDT.getInterviewTypeCd()!= null && interviewSummaryDT.getInterviewTypeCd().equalsIgnoreCase("INITIAL")){
							mappedDiagnosis.put("IXS106_"+i, checkNull(interviewSummaryDT.getInterviewLocCd()));

							processRepeatingQuestions(((PageActProxyVO)interviewPageProxyVO).getPageVO().getPageRepeatingAnswerDTMap(), ((PageActProxyVO)interviewPageProxyVO).getPageVO().getAnswerDTMap(),mappedDiagnosis, "IXS107", i);
							processRepeatingQuestions(((PageActProxyVO)interviewPageProxyVO).getPageVO().getPageRepeatingAnswerDTMap(), ((PageActProxyVO)interviewPageProxyVO).getPageVO().getAnswerDTMap(),mappedDiagnosis, "IXS108", i);
							processRepeatingQuestions(((PageActProxyVO)interviewPageProxyVO).getPageVO().getPageRepeatingAnswerDTMap(), ((PageActProxyVO)interviewPageProxyVO).getPageVO().getAnswerDTMap(),mappedDiagnosis, "IXS109", i);
							processRepeatingQuestions(((PageActProxyVO)interviewPageProxyVO).getPageVO().getPageRepeatingAnswerDTMap(), ((PageActProxyVO)interviewPageProxyVO).getPageVO().getAnswerDTMap(),mappedDiagnosis, "IXS111", i);



						}


						List<PersonVO> intrvwerOfInterviewlist = PageLoadUtil.getPersonVOCollection("IntrvwerOfInterview", interviewPageProxyVO);
						if(intrvwerOfInterviewlist.size() >0){
//
							if(interviewSummaryDT.getInterviewTypeCd()!= null && interviewSummaryDT.getInterviewTypeCd().equalsIgnoreCase("INITIAL") && interviewSummaryDT.getInterviewDate() != null){
								mappedDiagnosis.put("IXS101_"+i, StringUtils.formatDate(interviewSummaryDT.getInterviewDate() ));
							}
							if(interviewSummaryDT.getInterviewTypeCd()!= null && interviewSummaryDT.getInterviewTypeCd().equalsIgnoreCase("REINTVW") && interviewSummaryDT.getInterviewDate() != null){
								mappedDiagnosis.put("1stReInt_Dt"+i, StringUtils.formatDate(interviewSummaryDT.getInterviewDate() ));
							}
							if(interviewSummaryDT.getInterviewTypeCd()!= null && interviewSummaryDT.getInterviewTypeCd().equalsIgnoreCase("INITIAL")){
								mappedDiagnosis.put("IXS102_"+i,((PersonVO)intrvwerOfInterviewlist.get(0)).getEntityIdDT_s(0).getRootExtensionTxt());
							}
							if(interviewSummaryDT.getInterviewTypeCd()!= null && interviewSummaryDT.getInterviewTypeCd().equalsIgnoreCase("REINTVW")){
								mappedDiagnosis.put("1st_ReInt_DIS"+i,((PersonVO)intrvwerOfInterviewlist.get(0)).getEntityIdDT_s(0).getRootExtensionTxt());
							}

						}
					}
			}
		}
	}




	public static void populatePlaceRecord(PageActProxyVO proxyVO,
			Map<String, String> mappedDiagnosis, HttpSession session) {
		try {
			 Map<String,String> returnmap = processRepeatingQuestions(((PageActProxyVO)proxyVO).getPageVO().getPageRepeatingAnswerDTMap());
			 List<String> hPlaceUids= new ArrayList<String>();
			 List<String> sPlaceUids= new ArrayList<String>();

//				public static final String NBS290
			 for(String key : returnmap.keySet()){
				 if(key.contains(PrintConstants.NBS243)){
					String[] uids = returnmap.get(key).split("\\^");
					if(uids.length>0)
					 hPlaceUids.add(uids[0]);
				 }
				 if(key.contains(PrintConstants.NBS290)){
					 String[] uids = returnmap.get(key).split("\\^");
						if(uids.length>0)

					 sPlaceUids.add(uids[0]);
				 }
			 }

			 for(int i=0; i<hPlaceUids.size();i++){
				 PlaceVO placeVO =PlaceUtil.getThePlaceVO(Long.valueOf(hPlaceUids.get(i)), session);
				 mappedDiagnosis.put("HPLC005_" +(i+1), checkNull(placeVO.getThePlaceDT().getCd()));
				 mappedDiagnosis.put("HPLC007_" +(i+1), checkNull(placeVO.getNm()));
			 }

			 for(int i=0; i<sPlaceUids.size();i++){
				 PlaceVO placeVO =PlaceUtil.getThePlaceVO(Long.valueOf(sPlaceUids.get(i)), session);
				 mappedDiagnosis.put("PLC005_" +(i+1), checkNull(placeVO.getThePlaceDT().getCd()));
				 mappedDiagnosis.put("PLC007_" +(i+1), checkNull(placeVO.getNm()));
			 }
		} catch (Exception e) {
			logger.error("Exception in populatePlaceRecord: " + e.getMessage());
			e.printStackTrace();
		}

	}


	public static int elapsed(Calendar before, Calendar after, int field) {
	    Calendar clone = (Calendar) before.clone(); // Otherwise changes are been reflected.
	    int elapsed = -1;
	    while (!clone.after(after)) {
	        clone.add(field, 1);
	        elapsed++;
	    }
	    return elapsed;
	}
}