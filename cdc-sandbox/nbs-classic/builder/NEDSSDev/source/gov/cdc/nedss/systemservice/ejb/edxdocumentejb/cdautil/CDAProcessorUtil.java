package gov.cdc.nedss.systemservice.ejb.edxdocumentejb.cdautil;

import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.pagemanagement.wa.dao.PageManagementDAOImpl;
import gov.cdc.nedss.pagemanagement.wa.dt.WaTemplateDT;
import gov.cdc.nedss.phdc.cda.POCDMT000040Entry;
import gov.cdc.nedss.phdc.cda.POCDMT000040EntryRelationship;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.NbsQuestionMetadata;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.QuestionsCache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CDAProcessorUtil {
	static final LogUtils logger = new LogUtils(CDAPHCProcessor.class.getName());
	public static final String _REQUIRED = "_REQUIRED";
	@SuppressWarnings("unchecked")
	public static HashMap<Object, Object>  organizeRepeatingQuestionsInMap(POCDMT000040Entry[] entries)throws NEDSSSystemException {
		HashMap<Object, Object> organizedMap = new HashMap<Object, Object>();
		try {
			for (POCDMT000040Entry entry : entries) {
				if(entry.getOrganizer()!=null && entry.getOrganizer().getCode()!=null && entry.getOrganizer().getCode().getCode()!=null){
					Collection<POCDMT000040Entry> collection = null;
					if (organizedMap.get(entry.getOrganizer().getCode().getCode()) == null) {
						collection = new ArrayList<POCDMT000040Entry>();
						collection.add(entry);
					} else if (organizedMap
							.get(entry.getOrganizer().getCode().getCode()) != null) {
						 collection = (Collection<POCDMT000040Entry>)organizedMap.get(entry.getOrganizer().getCode().getCode());
						collection.add(entry);
					}
					if(collection!=null)
						organizedMap.put(entry.getOrganizer().getCode().getCode(), collection);
				}
			}
		}catch(Exception ex){
			logger.error("Exception in organizeRepeatingQuestionsInMap:");
			throw new NEDSSSystemException(
					"organizeRepeatingQuestionsInMap: Error while organizing repeating questions and exception is raised: ",
					ex);
		}
		return organizedMap;
		}
	
	
	@SuppressWarnings("unchecked")
	public static HashMap<Object, Object>  organizeRepeatingQuestionsInMapFromEntryRelationship(POCDMT000040EntryRelationship[] entries)throws NEDSSSystemException {
		HashMap<Object, Object> organizedMap = new HashMap<Object, Object>();
		try {
			for (POCDMT000040EntryRelationship entry : entries) {
				if(entry.getOrganizer()!=null && entry.getOrganizer().getCode()!=null && entry.getOrganizer().getCode().getCode()!=null){
					Collection<POCDMT000040EntryRelationship> collection = null;
					if (organizedMap.get(entry.getOrganizer().getCode().getCode()) == null) {
						collection = new ArrayList<POCDMT000040EntryRelationship>();
						collection.add(entry);
					} else if (organizedMap
							.get(entry.getOrganizer().getCode().getCode()) != null) {
						 collection = (Collection<POCDMT000040EntryRelationship>)organizedMap.get(entry.getOrganizer().getCode().getCode());
						collection.add(entry);
					}
					if(collection!=null)
						organizedMap.put(entry.getOrganizer().getCode().getCode(), collection);
				}
			}
		}catch(Exception ex){
			logger.error("Exception in organizeRepeatingQuestionsInMapFromEntryRelationship:");
			throw new NEDSSSystemException(
					"organizeRepeatingQuestionsInMapFromEntryRelationship: Error while organizing repeating questions and exception is raised: ",
					ex);
		}
		return organizedMap;
		}

	@SuppressWarnings("unchecked")
	public static Map<Object, Object> loadQuestions(String conditionCode, String objType)
			throws Exception {
		String InvestigationFormCd = null;
		Map<Object, Object> questionIdentifiers = null;
		try{
		PageManagementDAOImpl pageManagementDAOImpl = new PageManagementDAOImpl();
		WaTemplateDT waTemplateDT = pageManagementDAOImpl
				.findWaTemplateByConditionTypeAndBusinessObj(conditionCode,
						NEDSSConstants.PUBLISHED,objType);
		Map<Object, Object> questionMap;
		 InvestigationFormCd = waTemplateDT.getFormCd();
		 // if contact record page is not published for given condition, use the generic page.
		if(InvestigationFormCd==null && objType!=null && objType.equals(NEDSSConstants.CONTACT_BUSINESS_OBJECT_TYPE)){
			InvestigationFormCd = NEDSSConstants.CONTACT_FORMCODE;
		}

			if (QuestionsCache.dmbMap.containsKey(InvestigationFormCd))
				questionMap = (Map<Object, Object>) QuestionsCache.dmbMap
						.get(InvestigationFormCd);
			else
				questionMap = (Map<Object, Object>) QuestionsCache
						.getDMBQuestionMap().get(InvestigationFormCd);

		 questionIdentifiers = loadQuestionKeys(questionMap,
				InvestigationFormCd);
		if (questionMap == null)
			throw new Exception("\n *************** Question Cache for "
					+ InvestigationFormCd + " is empty!!! *************** \n");
		}catch( Exception ex){
			ex.printStackTrace();
			String errorString = "Question Cache for condition "+conditionCode+" and Investigation form Cd "	+ InvestigationFormCd + " is empty for object type: "+objType;
			logger.error(errorString);
			throw new NEDSSSystemException(errorString, ex);
		}
		return questionIdentifiers;
	}
	
	public static Map<Object, Object> loadQuestionKeys(
			Map<Object, Object> questionMap, String invFormCd) {
		if (questionMap == null)
			return null;
		Map<Object, Object> questionIdentifierMap = new HashMap<Object, Object>();
		Map<Object, Object> requiredQuestionIdentifierMap = new HashMap<Object, Object>();

		try {
			Iterator<Object> iter = (questionMap.keySet()).iterator();
			while (iter.hasNext()) {
				String key = (String) iter.next();
				NbsQuestionMetadata metaData = (NbsQuestionMetadata) questionMap
						.get(key);
				try {
					questionIdentifierMap.put(metaData.getQuestionIdentifier(),
							metaData);
					if (metaData.getRequiredInd() != null
							&& metaData.getRequiredInd().equalsIgnoreCase("T")
							&& metaData.getDataLocation() != null
							&& metaData.getDataLocation().equalsIgnoreCase(
									"NBS_Answer.answer_txt")) {
						requiredQuestionIdentifierMap.put(
								metaData.getQuestionIdentifier(), metaData);
					}
				} catch (Exception e) {
					logger.error(
							"NbsQuestionMetadata error thrown "
									+ metaData.toString(), e);
				}
			}
			if (requiredQuestionIdentifierMap != null
					&& requiredQuestionIdentifierMap.size() > 0)
				questionIdentifierMap.put(_REQUIRED,
						requiredQuestionIdentifierMap);
		} catch (Exception e) {
			logger.error("NbsQuestionMetadata error thrown " + e, e);
		}
		return questionIdentifierMap;
	}
}
