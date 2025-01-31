package gov.cdc.nedss.systemservice.ejb.edxdocumentejb.beanhelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import gov.cdc.nedss.act.actid.dt.ActIdDT;
import gov.cdc.nedss.act.publichealthcase.dt.ConfirmationMethodDT;
import gov.cdc.nedss.act.publichealthcase.dt.PublicHealthCaseDT;
import gov.cdc.nedss.act.publichealthcase.vo.PublicHealthCaseVO;
import gov.cdc.nedss.association.dt.ActRelationshipDT;
import gov.cdc.nedss.association.dt.ParticipationDT;
import gov.cdc.nedss.entity.entityid.dt.EntityIdDT;
import gov.cdc.nedss.entity.person.dt.PersonDT;
import gov.cdc.nedss.entity.person.dt.PersonEthnicGroupDT;
import gov.cdc.nedss.entity.person.dt.PersonNameDT;
import gov.cdc.nedss.entity.person.dt.PersonRaceDT;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.locator.dt.EntityLocatorParticipationDT;
import gov.cdc.nedss.nbsactentity.dt.NbsActEntityDT;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.act.PageActProxyVO;
import gov.cdc.nedss.pam.act.NbsCaseAnswerDT;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dt.DSMUpdateAlgorithmDT;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.NbsQuestionMetadata;
import gov.cdc.nedss.systemservice.nbssecurity.ProgramAreaVO;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NBSBeanUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.dynamicbinding.DynamicBeanBinding;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.QuestionsCache;

public class EdxPHCRUpdateHelper {

	static final LogUtils logger = new LogUtils(EdxPHCRUpdateHelper.class.getName());
	static PropertyUtil propertyUtil = PropertyUtil.getInstance();
	private Map<Object, Object> questionMap;

	private Map<Object, Object> questionKeyMap;

	private Map<Object, Object> ignoreQuestionMap = new HashMap<Object, Object>();

	private Map<Object, Object> ignoreParticipantQuestionMap = new HashMap<Object, Object>();

	private Map<Object, Object> ignoreActIDQuestionMap = new HashMap<Object, Object>();

	public void loadIgnoreQuestionMap(DSMUpdateAlgorithmDT dsmUpdateAlgorithmDT) {
		String questionList = dsmUpdateAlgorithmDT == null ? null : dsmUpdateAlgorithmDT.getUpdateIgnoreList();
		if (questionList != null && questionList.length() > 0) {
			String[] questionsIdArray = questionList.split(",");
			for (String questionId : questionsIdArray) {
				if (this.getQuestionMap().get(questionId) != null)
					this.getIgnoreQuestionMap().put(questionId, this.getQuestionMap().get(questionId));
				if (this.getQuestionMap().get(questionId) != null
						&& ((NbsQuestionMetadata) this.getQuestionMap().get(questionId)).getPartTypeCd() != null)
					this.getIgnoreParticipantQuestionMap().put(
							((NbsQuestionMetadata) this.getQuestionMap().get(questionId)).getPartTypeCd(), questionId);
				if (this.getQuestionMap().get(questionId) != null
						&& ((NbsQuestionMetadata) this.getQuestionMap().get(questionId)).getDataLocation() != null
						&& ((NbsQuestionMetadata) this.getQuestionMap().get(questionId)).getDataLocation()
								.startsWith("ACT_ID"))
					this.getIgnoreActIDQuestionMap()
							.put(((NbsQuestionMetadata) this.getQuestionMap().get(questionId)).getDataCd(), questionId);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void loadQuestionKeys(String invFormCd) throws NEDSSAppException {
		try {

			if (QuestionsCache.dmbMap.containsKey(invFormCd))
				setQuestionMap((Map<Object, Object>) QuestionsCache.dmbMap.get(invFormCd));
			else if (!QuestionsCache.dmbMap.containsKey(invFormCd) && propertyUtil.getServerRestart() != null
					&& propertyUtil.getServerRestart().equals("F"))
				setQuestionMap((Map<Object, Object>) QuestionsCache.getDMBQuestionMapAfterPublish().get(invFormCd));
			else
				setQuestionMap(new HashMap<Object, Object>());
			if (getQuestionMap() == null)
				throw new Exception(
						"\n *************** Question Cache for " + invFormCd + " is empty!!! *************** \n");
			logger.debug("********#Question Map Size: " + getQuestionMap().size() + " for form: " + invFormCd);

			if (getQuestionMap() == null)
				return;
			setQuestionKeyMap(new HashMap<Object, Object>());

			Iterator iter = getQuestionMap().keySet().iterator();
			while (iter.hasNext()) {
				String key = (String) iter.next();
				NbsQuestionMetadata metaData = (NbsQuestionMetadata) getQuestionMap().get(key);
				getQuestionKeyMap().put(metaData.getNbsQuestionUid(), key);

			}
			logger.debug("********#Question Key Map Size: " + getQuestionKeyMap().size() + " for form: " + invFormCd);
		} catch (Exception e) {
			logger.fatal("loadQuestionKeys" + invFormCd);
			throw new NEDSSAppException("Exception thrown for loadQuestionKeys : invFormCd ", e);
		}
		logger.debug("********#Question Key Map Size: " + questionKeyMap.size() + " for form: " + invFormCd
				+ " Question Key Map: " + questionKeyMap.toString());
	}

	public static CachedDropDownValues cdv = new CachedDropDownValues();

	@SuppressWarnings("unchecked")
	public void loadQuestions(String invFormCd) {

		if (QuestionsCache.getDMBQuestionMap() != null)
			setQuestionMap((Map) QuestionsCache.getDMBQuestionMap().get(invFormCd));
		else if (!QuestionsCache.dmbMap.containsKey(invFormCd))
			setQuestionMap((Map<Object, Object>) QuestionsCache.getDMBQuestionMapAfterPublish().get(invFormCd));
		else {
			logger.error("PageCreateHelper: Empty question map?? for form cd = " + invFormCd);
			setQuestionMap(new HashMap<Object, Object>());
		}
	}

	public Map<Object, Object> getQuestionMap() {
		return questionMap;
	}

	public void setQuestionMap(Map<Object, Object> questionMap) {
		this.questionMap = questionMap;
	}

	public Map<Object, Object> getIgnoreQuestionMap() {
		return ignoreQuestionMap;
	}

	public Map<Object, Object> getQuestionKeyMap() {
		return questionKeyMap;
	}

	public void setQuestionKeyMap(Map<Object, Object> questionKeyMap) {
		this.questionKeyMap = questionKeyMap;
	}

	public Map<Object, Object> getIgnoreParticipantQuestionMap() {
		return ignoreParticipantQuestionMap;
	}

	public void setIgnoreParticipantQuestionMap(Map<Object, Object> ignoreParticipantQuestionMap) {
		this.ignoreParticipantQuestionMap = ignoreParticipantQuestionMap;
	}

	public Map<Object, Object> getIgnoreActIDQuestionMap() {
		return ignoreActIDQuestionMap;
	}

	public void setIgnoreActIDQuestionMap(Map<Object, Object> ignoreActIDQuestionMap) {
		this.ignoreActIDQuestionMap = ignoreActIDQuestionMap;
	}

	public void preparePageActProxyVOForUpdate(PageActProxyVO pageActProxyVO, PageActProxyVO oldPageActProxyVO, DSMUpdateAlgorithmDT dsmUpdateAlgorithmDT)
			throws NEDSSAppException {
		try {
			PublicHealthCaseVO oldPhcVO = oldPageActProxyVO.getPublicHealthCaseVO();
			PublicHealthCaseVO newPhcVO = pageActProxyVO.getPublicHealthCaseVO();
			PublicHealthCaseDT phcDT = oldPhcVO.getThePublicHealthCaseDT();
			CachedDropDownValues cdv = new CachedDropDownValues();
			ProgramAreaVO programAreaVO = cdv.getProgramAreaCondition("('" + phcDT.getProgAreaCd() + "')",
					phcDT.getCd());
			String investigationFormCd = programAreaVO.getInvestigationFormCd();
			loadQuestions(investigationFormCd);
			loadQuestionKeys(investigationFormCd);
			loadIgnoreQuestionMap(dsmUpdateAlgorithmDT);
			// set Public Health Case Core Questions

			newPhcVO.getThePublicHealthCaseDT()
					.setPublicHealthCaseUid(oldPhcVO.getThePublicHealthCaseDT().getPublicHealthCaseUid());
			newPhcVO.getThePublicHealthCaseDT().setLocalId(oldPhcVO.getThePublicHealthCaseDT().getLocalId());
			newPhcVO.getThePublicHealthCaseDT().setAddTime(oldPhcVO.getThePublicHealthCaseDT().getAddTime());
			newPhcVO.getThePublicHealthCaseDT().setAddUserId(oldPhcVO.getThePublicHealthCaseDT().getAddUserId());
			newPhcVO.getThePublicHealthCaseDT().setSharedInd(oldPhcVO.getThePublicHealthCaseDT().getSharedInd());
			newPhcVO.getThePublicHealthCaseDT().setAddTime(oldPhcVO.getThePublicHealthCaseDT().getAddTime());
			//newPhcVO.getThePublicHealthCaseDT().setMmwrWeek(oldPhcVO.getThePublicHealthCaseDT().getMmwrWeek());
			//newPhcVO.getThePublicHealthCaseDT().setMmwrYear(oldPhcVO.getThePublicHealthCaseDT().getMmwrYear());
			newPhcVO.getThePublicHealthCaseDT().setItNew(false);
			newPhcVO.getThePublicHealthCaseDT().setItDirty(true);
			newPhcVO.getThePublicHealthCaseDT()
					.setVersionCtrlNbr(oldPhcVO.getThePublicHealthCaseDT().getVersionCtrlNbr());
			newPhcVO.setItNew(false);
			newPhcVO.setItDirty(true);
			pageActProxyVO.setItDirty(true);
			pageActProxyVO.setItNew(false);

			Collection<Object> removeActRelationships = new ArrayList<Object>();

			if (newPhcVO.getTheActRelationshipDTCollection() != null
					&& newPhcVO.getTheActRelationshipDTCollection().size() > 0) {
				for (Object actRelationshipDT : safe(newPhcVO.getTheActRelationshipDTCollection())) {

					// Fix for
					// When source_act_uid and type_cd in the act_relationship already present for
					// the document ,
					// its an update and not insert. so marking the record as not new
					for (Object oldObject : safe(oldPhcVO.getTheActRelationshipDTCollection())) {
						if (((ActRelationshipDT) oldObject).getSourceActUid()
								.compareTo(((ActRelationshipDT) actRelationshipDT).getSourceActUid()) == 0
								&& ((ActRelationshipDT) oldObject).getTypeCd()
										.equals(((ActRelationshipDT) actRelationshipDT).getTypeCd())) {
							// Skip trying to insert . its an update and nothing need to be done here.
							((ActRelationshipDT) actRelationshipDT).setItNew(false);
						}
					}
					
					((ActRelationshipDT) actRelationshipDT)
							.setTargetActUid(oldPhcVO.getThePublicHealthCaseDT().getPublicHealthCaseUid());
					// remove all the remaining act relationships except for Document
					if (!((ActRelationshipDT) actRelationshipDT).getTypeCd().equals("DocToPHC"))
						removeActRelationships.add(actRelationshipDT);

				}
			}
			// remove all the remaining act relationships except for Document
			newPhcVO.getTheActRelationshipDTCollection().removeAll(removeActRelationships);

			pageActProxyVO
					.setTheNotificationSummaryVOCollection(oldPageActProxyVO.getTheNotificationSummaryVOCollection());

			// Set Act IDs (includes handling ignore records)
			handleActIDUpdates(pageActProxyVO, oldPageActProxyVO);

			// Set Confirmation Methods
			if (newPhcVO.getTheConfirmationMethodDTCollection() != null
					&& newPhcVO.getTheConfirmationMethodDTCollection().size() > 0) {
				for (int i = 0; i < newPhcVO.getTheConfirmationMethodDTCollection().size(); i++) {
					ConfirmationMethodDT cm = (ConfirmationMethodDT) ((ArrayList<Object>) newPhcVO
							.getTheConfirmationMethodDTCollection()).get(i);
					cm.setPublicHealthCaseUid(phcDT.getPublicHealthCaseUid());
				}
			}

			// set patient Data
    		Long personUid = updatePersonCRUpdate(pageActProxyVO, oldPageActProxyVO);

			// Set Entities and Participations
			setEntitiesForEdit(pageActProxyVO, oldPageActProxyVO, personUid);

			// Handle Discrete answers for Update
			handleUpdateIngoreScenarioForNBSCaseAnswer(pageActProxyVO, oldPageActProxyVO);

			updateNbsAnswersForDirty(pageActProxyVO, oldPageActProxyVO);

			// Set Repeating answers for Update
			updateNbsRepeatingAnswersForDirty(pageActProxyVO, oldPageActProxyVO);

			// Ignore Questions Update for configure Questions
			handleUpdateIngoreScenarioForCoreObjects(pageActProxyVO, oldPageActProxyVO);

		} catch (Exception ex) {
			logger.error("Error while upreparePageActProxyVOForUpdate");
			throw new NEDSSAppException(ex.getMessage(), "upreparePageActProxyVOForUpdate");
		}
	}

	public static Long updatePersonCRUpdate(PageActProxyVO pageActProxyVO, PageActProxyVO oldPageActProxyVO)
			throws NEDSSAppException {
		PersonDT matchedPersonDT = null;
		Long matchedPersonUid = null;
		Long matchedPersonParentUid = null;
		String matchedLocalId = null;
		Integer matchedVersionCtNo = null;
		Collection<Object> updatedPersonNameCollection = new ArrayList<Object>();
		Collection<Object> updatedPersonRaceCollection = new ArrayList<Object>();
		Collection<Object> updatedPersonEthnicGroupCollection = new ArrayList<Object>();
		Collection<Object> updatedtheEntityLocatorParticipationDTCollection = new ArrayList<Object>();
		Collection<Object> updatedtheEntityIdDTCollection = new ArrayList<Object>();

		HashMap<Object, Object> oldNameMap = new HashMap<Object, Object>();
		HashMap<Object, Object> oldRaceMap = new HashMap<Object, Object>();
		HashMap<Object, Object> oldEthnicGroupMap = new HashMap<Object, Object>();
		HashMap<Object, Object> oldEntityIdMap = new HashMap<Object, Object>();
		HashMap<Object, Object> oldELPMap = new HashMap<Object, Object>();
		int nameSeq = 0;
		int entityIdSeq = 0;
		try {
			Collection<Object> personCollection = oldPageActProxyVO.getThePersonVOCollection();
			for (Object obj : safe(personCollection)) {
				PersonVO personVO = (PersonVO) obj;
				String perCd = personVO.getThePersonDT().getCd();
				if (perCd != null && perCd.equalsIgnoreCase(NEDSSConstants.CLASS_CD_PAT)) {
					matchedPersonDT = personVO.getThePersonDT();

					matchedPersonUid = matchedPersonDT.getPersonUid();
					matchedPersonParentUid = matchedPersonDT.getPersonParentUid();
					matchedLocalId = matchedPersonDT.getLocalId();
					matchedVersionCtNo = matchedPersonDT.getVersionCtrlNbr();

				}
				if (perCd != null && perCd.equalsIgnoreCase(NEDSSConstants.CLASS_CD_PAT)) {

					for (Object nameobj : safe(personVO.getThePersonNameDTCollection())) {
						PersonNameDT personNameDT = (PersonNameDT) nameobj;
						if(personNameDT.getStatusCd()!=null && personNameDT.getStatusCd().equals(NEDSSConstants.A) && oldNameMap.containsKey(personNameDT.getNmUseCd())) {
							personNameDT.setItDelete(false);
							personNameDT.setItDirty(true);
							personNameDT.setItNew(false);
							personNameDT.setStatusCd(NEDSSConstants.I);
							personNameDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_INACTIVE);
							updatedPersonNameCollection.add(personNameDT);
						}
						else if(personNameDT.getStatusCd()!=null && personNameDT.getStatusCd().equals(NEDSSConstants.A))
							oldNameMap.put(personNameDT.getNmUseCd(), personNameDT);
						if (personNameDT.getPersonNameSeq() > nameSeq)
							nameSeq = personNameDT.getPersonNameSeq();
					}

					for (Object raceobj : safe(personVO.getThePersonRaceDTCollection())) {
						PersonRaceDT personRaceDT = (PersonRaceDT) raceobj;
						oldRaceMap.put(personRaceDT.getRaceCd(), personRaceDT);
					}

					for (Object ethnicGroupObj : safe(personVO.getThePersonEthnicGroupDTCollection())) {
						PersonEthnicGroupDT personEthnicGroupDT = (PersonEthnicGroupDT) ethnicGroupObj;
						oldEthnicGroupMap.put(personEthnicGroupDT.getEthnicGroupCd(), personEthnicGroupDT);
					}

					for (Object entityIDobj : safe(personVO.getTheEntityIdDTCollection())) {
						EntityIdDT entityIDDT = (EntityIdDT) entityIDobj;
						oldEntityIdMap.put(entityIDDT.getTypeCd(), entityIDDT);
						if (entityIDDT.getEntityIdSeq() > entityIdSeq)
							entityIdSeq = entityIDDT.getEntityIdSeq();
					}

					for (Object eLPobj : safe(personVO.getTheEntityLocatorParticipationDTCollection())) {
						EntityLocatorParticipationDT entityLocPartDT = (EntityLocatorParticipationDT) eLPobj;
						if(entityLocPartDT.getStatusCd()!=null && entityLocPartDT.getStatusCd().equals(NEDSSConstants.A) && oldELPMap.containsKey(entityLocPartDT.getCd() + entityLocPartDT.getClassCd() + entityLocPartDT.getUseCd())) {
							entityLocPartDT.setItDelete(false);
							entityLocPartDT.setItDirty(true);
							entityLocPartDT.setItNew(false);
							entityLocPartDT.setStatusCd(NEDSSConstants.I);
							entityLocPartDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_INACTIVE);
							if (entityLocPartDT.getThePostalLocatorDT() != null) {
								entityLocPartDT.getThePostalLocatorDT().setItNew(false);
								entityLocPartDT.getThePostalLocatorDT().setItDirty(true);
								entityLocPartDT.getThePostalLocatorDT().setItDelete(false);
							}
							if (entityLocPartDT.getTheTeleLocatorDT() != null) {
								entityLocPartDT.getTheTeleLocatorDT().setItNew(false);
								entityLocPartDT.getTheTeleLocatorDT().setItDirty(true);
								entityLocPartDT.getTheTeleLocatorDT().setItDelete(false);
							}
							updatedtheEntityLocatorParticipationDTCollection.add(entityLocPartDT);
						}
						else if(entityLocPartDT.getStatusCd()!=null && entityLocPartDT.getStatusCd().equals(NEDSSConstants.A))
						oldELPMap.put(
								entityLocPartDT.getCd() + entityLocPartDT.getClassCd() + entityLocPartDT.getUseCd(),
								entityLocPartDT);
					}

				}
			}

			for (Object perObj : safe(pageActProxyVO.getThePersonVOCollection())) {
				PersonVO personVO = (PersonVO) perObj;
				String perCd = personVO.getThePersonDT().getCd();
				if (perCd != null && perCd.equalsIgnoreCase(NEDSSConstants.CLASS_CD_PAT)) {

					personVO.getThePersonDT().setPersonUid(matchedPersonUid);
					personVO.getThePersonDT().setPersonParentUid(matchedPersonParentUid);
					personVO.getThePersonDT().setLocalId(matchedLocalId);
					personVO.getThePersonDT().setVersionCtrlNbr(matchedVersionCtNo);
					personVO.getThePersonDT().setItDirty(true);
					personVO.getThePersonDT().setItNew(false);
					personVO.setItNew(false);
					personVO.setItDirty(true);

					if (perCd != null && perCd.equalsIgnoreCase(NEDSSConstants.CLASS_CD_PAT)) {
						if (personVO.getThePersonNameDTCollection() != null
								&& personVO.getThePersonNameDTCollection().size() > 0) {
							for (Iterator<Object> it = personVO.getThePersonNameDTCollection().iterator(); it
									.hasNext();) {
								PersonNameDT personNameDT = (PersonNameDT) it.next();
								// If old proxy has it use the new and delete the old
								if (oldNameMap.get(personNameDT.getNmUseCd()) != null) {
									((PersonNameDT) oldNameMap.get(personNameDT.getNmUseCd())).setItDelete(false);
									((PersonNameDT) oldNameMap.get(personNameDT.getNmUseCd())).setItDirty(true);
									((PersonNameDT) oldNameMap.get(personNameDT.getNmUseCd())).setItNew(false);
									((PersonNameDT) oldNameMap.get(personNameDT.getNmUseCd())).setStatusCd(NEDSSConstants.I);
									((PersonNameDT) oldNameMap.get(personNameDT.getNmUseCd())).setRecordStatusCd(NEDSSConstants.RECORD_STATUS_INACTIVE);
									updatedPersonNameCollection.add(oldNameMap.get(personNameDT.getNmUseCd()));
									oldNameMap.remove(personNameDT.getNmUseCd());
								}
								// set new flag on the new name to write from CR
								personNameDT.setItNew(true);
								personNameDT.setItDirty(false);
								personNameDT.setItDelete(false);
								personNameDT.setPersonUid(matchedPersonUid);
								personNameDT.setPersonNameSeq(++nameSeq);
								updatedPersonNameCollection.add(personNameDT);
							}
						}
						// if there are any remaining old records add to the collections as dirty
						for (Object obj : safe(oldNameMap.values())) {
							PersonNameDT personNameDT = (PersonNameDT) obj;
							personNameDT.setItNew(false);
							personNameDT.setItDirty(true);
							personNameDT.setItDelete(false);
							updatedPersonNameCollection.add(personNameDT);
						}
						if (personVO.getThePersonNameDTCollection() == null)
							personVO.setThePersonNameDTCollection(new ArrayList<Object>());
						personVO.getThePersonNameDTCollection().addAll(updatedPersonNameCollection);

						/* Handle race */
						Collection<Object> personRaceCollection = personVO.getThePersonRaceDTCollection();
						if (personRaceCollection == null || personRaceCollection.size() == 0) {
							// Leave the old Race as it is by setting it dirty
							for (Object obj : safe(oldRaceMap.values())) {
								PersonRaceDT personRaceDT = (PersonRaceDT) obj;
								personRaceDT.setItDirty(true);
								personRaceDT.setItNew(false);
								personRaceDT.setItDelete(false);
								updatedPersonRaceCollection.add(personRaceDT);
							}

						} else {

							for (Object obj : safe(personVO.getThePersonRaceDTCollection())) {
								PersonRaceDT personRaceDT = (PersonRaceDT) obj;
								if (oldRaceMap.get(personRaceDT.getRaceCd()) != null) {
									personRaceDT.setItDirty(true);
									personRaceDT.setItNew(false);
									personRaceDT.setItDelete(false);
									personRaceDT.setPersonUid(matchedPersonUid);
								} else {
									personRaceDT.setItNew(true);
									personRaceDT.setItDirty(false);
									personRaceDT.setItDelete(false);
									personRaceDT.setPersonUid(matchedPersonUid);
								}

							}
							boolean found = false;
							for (Object obj : safe(oldRaceMap.values())) {
								PersonRaceDT personRaceDT2 = (PersonRaceDT) obj;
								for (Object obj1 : safe(personRaceCollection)) {
									PersonRaceDT personRaceDT = (PersonRaceDT) obj1;
									if (personRaceDT2.getRaceCd().equals(personRaceDT.getRaceCd())) {
										found = true;
										break;
									}
								}

								if (!found) {
									personRaceDT2.setItDelete(true);
									personRaceDT2.setItDirty(false);
									personRaceDT2.setItNew(false);
									personVO.getThePersonRaceDTCollection().add(personRaceDT2);
								}
							}
						}

						if (personVO.getThePersonRaceDTCollection() == null) {
							personVO.setThePersonRaceDTCollection(new ArrayList<Object>());
							personVO.getThePersonRaceDTCollection().addAll(updatedPersonRaceCollection);
						}

						/* handle Ethnic Group */

						for (Object obj : safe(personVO.getThePersonEthnicGroupDTCollection())) {
							PersonEthnicGroupDT personEthnicGroupDT = (PersonEthnicGroupDT) obj;

							if (oldEthnicGroupMap.get(personEthnicGroupDT.getEthnicGroupCd()) != null) {
								personEthnicGroupDT.setItDirty(true);
								personEthnicGroupDT.setItNew(false);
								personEthnicGroupDT.setItDelete(false);
								personEthnicGroupDT.setPersonUid(matchedPersonUid);

							} else {
								personEthnicGroupDT.setItNew(true);
								personEthnicGroupDT.setItDirty(false);
								personEthnicGroupDT.setItDelete(false);
								personEthnicGroupDT.setPersonUid(matchedPersonUid);
							}
							updatedPersonEthnicGroupCollection.add(personEthnicGroupDT);
						}

						// if there are any remaining old records add to the collections as dirty
						for (Object obj : safe(oldEthnicGroupMap.values())) {
							PersonEthnicGroupDT personEthnicGroupDT = (PersonEthnicGroupDT) obj;
							personEthnicGroupDT.setItNew(false);
							personEthnicGroupDT.setItDirty(true);
							personEthnicGroupDT.setItDelete(false);
							updatedPersonEthnicGroupCollection.add(personEthnicGroupDT);
						}
						if (personVO.getThePersonEthnicGroupDTCollection() == null) {
							personVO.setThePersonEthnicGroupDTCollection(new ArrayList<Object>());
							personVO.getThePersonEthnicGroupDTCollection().addAll(updatedPersonEthnicGroupCollection);
						}
						/* Handle Entity IDs */
						for (Object obj : safe(personVO.getTheEntityIdDTCollection())) {
							EntityIdDT entityIDDT = (EntityIdDT) obj;
							if (oldEntityIdMap.get(entityIDDT.getTypeCd()) != null) {
								((EntityIdDT) oldEntityIdMap.get(entityIDDT.getTypeCd())).setItDelete(false);
								((EntityIdDT) oldEntityIdMap.get(entityIDDT.getTypeCd())).setItNew(false);
								((EntityIdDT) oldEntityIdMap.get(entityIDDT.getTypeCd())).setItDirty(true);
								((EntityIdDT) oldEntityIdMap.get(entityIDDT.getTypeCd())).setItNew(false);
								((EntityIdDT) oldEntityIdMap.get(entityIDDT.getTypeCd())).setStatusCd(NEDSSConstants.I);
								((EntityIdDT) oldEntityIdMap.get(entityIDDT.getTypeCd())).setRecordStatusCd(NEDSSConstants.RECORD_STATUS_INACTIVE);
								updatedtheEntityIdDTCollection.add(oldEntityIdMap.get(entityIDDT.getTypeCd()));
								oldEntityIdMap.remove(entityIDDT.getTypeCd());
							}
							entityIDDT.setItNew(true);
							entityIDDT.setItDirty(false);
							entityIDDT.setItDelete(false);
							entityIDDT.setEntityUid(matchedPersonUid);
							entityIDDT.setEntityIdSeq(++entityIdSeq);
							updatedtheEntityIdDTCollection.add(entityIDDT);
						}

						// if there are any remaining old records add to the collections as dirty

						for (Object obj : safe(oldEntityIdMap.values())) {
							EntityIdDT entityIDDT = (EntityIdDT) obj;
							entityIDDT.setItNew(false);
							entityIDDT.setItDirty(true);
							entityIDDT.setItDelete(false);
							updatedtheEntityIdDTCollection.add(entityIDDT);
						}

						if (personVO.getTheEntityIdDTCollection() == null)
							personVO.setTheEntityIdDTCollection(new ArrayList<Object>());
						personVO.getTheEntityIdDTCollection().addAll(updatedtheEntityIdDTCollection);

						/* Handle Entity Locator Participations */
						for (Object obj : safe(personVO.getTheEntityLocatorParticipationDTCollection())) {
							EntityLocatorParticipationDT entityLocPartDT = (EntityLocatorParticipationDT) obj;

							if (oldELPMap.containsKey(entityLocPartDT.getCd() + entityLocPartDT.getClassCd()
									+ entityLocPartDT.getUseCd())) {
								((EntityLocatorParticipationDT) oldELPMap.get(entityLocPartDT.getCd()
										+ entityLocPartDT.getClassCd() + entityLocPartDT.getUseCd())).setItDelete(false);

								((EntityLocatorParticipationDT) oldELPMap.get(entityLocPartDT.getCd()
										+ entityLocPartDT.getClassCd() + entityLocPartDT.getUseCd())).setItDirty(true);
								((EntityLocatorParticipationDT) oldELPMap.get(entityLocPartDT.getCd()
										+ entityLocPartDT.getClassCd() + entityLocPartDT.getUseCd())).setItNew(false);
								
								((EntityLocatorParticipationDT) oldELPMap.get(entityLocPartDT.getCd()
										+ entityLocPartDT.getClassCd() + entityLocPartDT.getUseCd())).setStatusCd(NEDSSConstants.I);
								((EntityLocatorParticipationDT) oldELPMap.get(entityLocPartDT.getCd()
										+ entityLocPartDT.getClassCd() + entityLocPartDT.getUseCd())).setRecordStatusCd(NEDSSConstants.RECORD_STATUS_INACTIVE);

								
								updatedtheEntityLocatorParticipationDTCollection
										.add(oldELPMap.get(entityLocPartDT.getCd() + entityLocPartDT.getClassCd()
												+ entityLocPartDT.getUseCd()));
								
								oldELPMap.remove(entityLocPartDT.getCd() + entityLocPartDT.getClassCd()
										+ entityLocPartDT.getUseCd());
							}

							entityLocPartDT.setItNew(true);
							entityLocPartDT.setItDirty(false);
							entityLocPartDT.setItDelete(false);
							entityLocPartDT.setEntityUid(matchedPersonUid);

							if (entityLocPartDT.getThePostalLocatorDT() != null) {
								entityLocPartDT.getThePostalLocatorDT().setItNew(true);
								entityLocPartDT.getThePostalLocatorDT().setItDirty(false);
								entityLocPartDT.getThePostalLocatorDT().setItDelete(false);
							}
							if (entityLocPartDT.getTheTeleLocatorDT() != null) {
								entityLocPartDT.getTheTeleLocatorDT().setItNew(true);
								entityLocPartDT.getTheTeleLocatorDT().setItDirty(false);
								entityLocPartDT.getTheTeleLocatorDT().setItDelete(false);
							}
							updatedtheEntityLocatorParticipationDTCollection.add(entityLocPartDT);
						}

						// if there are any remaining old records add to the collections as dirty
						for (Object obj : safe(oldELPMap.values())) {
							EntityLocatorParticipationDT entityLocPartDT = (EntityLocatorParticipationDT) obj;
							entityLocPartDT.setItNew(false);
							entityLocPartDT.setItDirty(true);
							entityLocPartDT.setItDelete(false);
							if (entityLocPartDT.getThePostalLocatorDT() != null) {
								entityLocPartDT.getThePostalLocatorDT().setItNew(false);
								entityLocPartDT.getThePostalLocatorDT().setItDirty(true);
								entityLocPartDT.getThePostalLocatorDT().setItDelete(false);
							}
							if (entityLocPartDT.getTheTeleLocatorDT() != null) {
								entityLocPartDT.getTheTeleLocatorDT().setItNew(false);
								entityLocPartDT.getTheTeleLocatorDT().setItDirty(true);
								entityLocPartDT.getTheTeleLocatorDT().setItDelete(false);
							}
							updatedtheEntityLocatorParticipationDTCollection.add(entityLocPartDT);
						}

						if (personVO.getTheEntityLocatorParticipationDTCollection() == null)
							personVO.setTheEntityLocatorParticipationDTCollection(new ArrayList<Object>());
						personVO.getTheEntityLocatorParticipationDTCollection()
								.addAll(updatedtheEntityLocatorParticipationDTCollection);

					}
					personVO.setRole(null);
				}
			}
			return matchedPersonUid;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("Exception occured in EDXPHCRUpdateHelper.updatePersonCRUpdate");
			throw new NEDSSAppException(e.getMessage(), e);
		}

	}

	private void setEntitiesForEdit(PageActProxyVO proxyVO, PageActProxyVO oldProxyVO, Long personUid)
			throws NEDSSAppException {
		try {
			Collection<Object> oldPartColl = oldProxyVO.getPublicHealthCaseVO().getTheParticipationDTCollection();
			Collection<Object> newParCollection = proxyVO.getTheParticipationDTCollection();
			Collection<Object> oldEntityCollection = oldProxyVO.getPageVO().getActEntityDTCollection();
			Collection<Object> newEntityCollection = proxyVO.getPageVO().getActEntityDTCollection();
			Map<Object, Object> newPartMap = new HashMap<Object, Object>();
			Map<Object, Object> newEntityActMap = new HashMap<Object, Object>();
			Map<Object, Object> oldEntityActMap = new HashMap<Object, Object>();
			Map<Object, Object> oldPartMap = new HashMap<Object, Object>();

			PublicHealthCaseVO phcVO = oldProxyVO.getPublicHealthCaseVO();
			Long publicHealthCaseUID = phcVO.getThePublicHealthCaseDT().getPublicHealthCaseUid();

			for (Object obj : safe(newParCollection)) {
				newPartMap.put(((ParticipationDT) obj).getTypeCd(), obj);
			}
			for (Object obj : safe(newEntityCollection)) {
				newEntityActMap.put(((NbsActEntityDT) obj).getTypeCd(), obj);
			}
			for (Object obj : safe(oldEntityCollection)) {
				oldEntityActMap.put(((NbsActEntityDT) obj).getTypeCd(), obj);
			}
			for (Object obj : safe(oldPartColl)) {
				oldPartMap.put(((ParticipationDT) obj).getTypeCd(), obj);
			}
			if (oldPartColl != null && oldPartColl.size() > 0) {
				Iterator<Object> parDTIt = oldPartColl.iterator();
				while (parDTIt.hasNext()) {
					ParticipationDT parDT = (ParticipationDT) parDTIt.next();
					// For patient or ignored entity, get them from old
					if (parDT.getTypeCd().equals(NEDSSConstants.PHC_PATIENT)
							|| this.getIgnoreParticipantQuestionMap().containsKey(parDT.getTypeCd())) {
						parDT.setItDirty(true);
						parDT.setItNew(false);
						newPartMap.put(parDT.getTypeCd(), parDT);
						oldPartMap.remove(parDT.getTypeCd());
						NbsActEntityDT oldEntityActDT = (NbsActEntityDT) oldEntityActMap.get(parDT.getTypeCd());
						if (oldEntityActDT != null) {
							oldEntityActDT.setItDirty(true);
							oldEntityActDT.setItNew(false);
							newEntityActMap.put(oldEntityActDT.getTypeCd(), oldEntityActDT);
							oldEntityActMap.remove(oldEntityActDT.getTypeCd());
						}
					} else if (newPartMap.containsKey(parDT.getTypeCd())) {
						ParticipationDT newParticipationDT = (ParticipationDT) newPartMap.get(parDT.getTypeCd());
						// if same participation remove from OLD and add to new as dirty
						if (newParticipationDT.getSubjectEntityUid().equals(parDT.getSubjectEntityUid())) {
							parDT.setItDirty(true);
							parDT.setItNew(false);
							newPartMap.put(parDT.getTypeCd(), parDT);
							oldPartMap.remove(parDT.getTypeCd());
							NbsActEntityDT oldEntityActDT = (NbsActEntityDT) oldEntityActMap.get(parDT.getTypeCd());
							if (oldEntityActDT != null) {
								oldEntityActDT.setItDirty(true);
								oldEntityActDT.setItNew(false);
								newEntityActMap.put(oldEntityActDT.getTypeCd(), oldEntityActDT);
								oldEntityActMap.remove(oldEntityActDT.getTypeCd());
							}
						}
						// there is a participation of same type but with different subject, set it to
						// delete for old part
						else {
							parDT.setItDelete(true);
							parDT.setItNew(false);
							parDT.setItDirty(false);
							oldPartMap.put(parDT.getTypeCd(), parDT);
							NbsActEntityDT oldEntityActDT = (NbsActEntityDT) oldEntityActMap.get(parDT.getTypeCd());
							if (oldEntityActDT != null) {
								oldEntityActDT.setItDelete(true);
								oldEntityActDT.setItNew(false);
								oldEntityActDT.setItDirty(false);
								oldEntityActMap.put(oldEntityActDT.getTypeCd(), oldEntityActDT);
							}
						}
					}
					// if these participations are not there in the new retain them
					else {
						parDT.setItDirty(true);
						parDT.setItNew(false);
						oldPartMap.put(parDT.getTypeCd(), parDT);
						NbsActEntityDT oldEntityActDT = (NbsActEntityDT) oldEntityActMap.get(parDT.getTypeCd());
						if (oldEntityActDT != null) {
							oldEntityActDT.setItDirty(true);
							oldEntityActDT.setItNew(false);
							oldEntityActMap.put(oldEntityActDT.getTypeCd(), oldEntityActDT);
						}
					}
				}
			}

			proxyVO.setTheParticipationDTCollection(new ArrayList<Object>());
			proxyVO.getPageVO().setActEntityDTCollection(new ArrayList<Object>());
			
			//for any ignore list which are new in the map, remove them.
			for(Object obj: safe(this.getIgnoreParticipantQuestionMap().keySet())) {
				String typeCd=  (String)obj;
				if(newPartMap.containsKey(typeCd) && ((ParticipationDT)newPartMap.get(typeCd)).isItNew())
					newPartMap.remove(typeCd);
				if(newEntityActMap.containsKey(typeCd) && ((NbsActEntityDT)newEntityActMap.get(typeCd)).isItNew())
					newEntityActMap.remove(typeCd);
			}

			for (Object obj : safe(newPartMap.values())) {
				ParticipationDT newPartDT = (ParticipationDT) obj;
				newPartDT.setActUid(publicHealthCaseUID);
				proxyVO.getTheParticipationDTCollection().add(newPartDT);
			}
			// Add the remaining participation as dirty or delete
			for (Object obj : safe(oldPartMap.values())) {
				ParticipationDT oldPartDT = (ParticipationDT) obj;
				proxyVO.getTheParticipationDTCollection().add(oldPartDT);
			}

			for (Object obj : safe(newEntityActMap.values())) {
				NbsActEntityDT newEntityActDT = (NbsActEntityDT) obj;
				newEntityActDT.setActUid(publicHealthCaseUID);
				proxyVO.getPageVO().getActEntityDTCollection().add(newEntityActDT);
			}

			// Add the remaining NBS Act Entities as dirty or delete
			for (Object obj : safe(oldEntityActMap.values())) {
				NbsActEntityDT oldEntityActDT = (NbsActEntityDT) obj;
				proxyVO.getPageVO().getActEntityDTCollection().add(oldEntityActDT);
			}

		} catch (Exception e) {
			logger.fatal("Exception occured in EDXPHCRUpdateHelper.setEntitiesForEdit: PublicHealthCaseUid: "
					+ proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid() + ", "
					+ e.getMessage(), e);
			throw new NEDSSAppException(e.getMessage(), e);
		}

	}

	@SuppressWarnings("unchecked")
	private void updateNbsAnswersForDirty(PageActProxyVO proxyVO, PageActProxyVO oldProxyVO) throws NEDSSAppException {
		try {
			Map<Object, Object> oldAnswers = oldProxyVO.getPageVO().getPamAnswerDTMap();
			Map<Object, Object> newAnswers = proxyVO.getPageVO().getPamAnswerDTMap();

			// Iterate through the newAnswers and mark it accordingly
			// 1. If present in new and not present in old - mark it NEW
			// 2. If present in both - mark it DIRTY
			// 3. If present in old and not present in new - leave them there.
			Iterator<Object> iter = newAnswers.keySet().iterator();
			while (iter.hasNext()) {
				String qId = (String) iter.next();
				Object obj = newAnswers.get(qId);
				if (obj instanceof ArrayList<?>) {
					ArrayList<Object> answerList = (ArrayList<Object>) obj;

					checkAnswerListForDirty(oldAnswers, answerList, "");
				} else {
					NbsCaseAnswerDT dt = (NbsCaseAnswerDT) obj;
					Long qUid = (Long) dt.getNbsQuestionUid();
					if (oldAnswers.get(qUid) == null) {
						dt.setItNew(true);
						dt.setItDirty(false);
						dt.setItDelete(false);
					} else {
						NbsCaseAnswerDT oldDT = (NbsCaseAnswerDT) oldAnswers.get(qUid);
						dt.setItDirty(true);
						dt.setItNew(false);
						dt.setItDelete(false);
						dt.setNbsCaseAnswerUid(oldDT.getNbsCaseAnswerUid());
						// remove it from oldMap so that the leftovers in oldMap are DELETE candidates
						oldAnswers.remove(qUid);
					}
				}
			}
			
			// Add all from old to the new
			((PageActProxyVO) proxyVO).getPageVO().getPamAnswerDTMap().putAll(oldAnswers);
		} catch (Exception ex) {
			logger.fatal("Exception occured in EDXPHCRUpdateHelper.updateNbsAnswersForDirty: ," + ex.getMessage(), ex);
			throw new NEDSSAppException(ex.getMessage(), ex);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void checkAnswerListForDirty(Map<Object, Object> oldAnswers, ArrayList<Object> list,
			String strRepeating) throws NEDSSAppException {

		try {
			ArrayList<Object> oldAList = null;
			ArrayList<Object> tempList = new ArrayList<Object>();
			Iterator<Object> iter = list.iterator();
			if (strRepeating.equals("")) {
				while (iter.hasNext()) {
					NbsCaseAnswerDT dt = (NbsCaseAnswerDT) iter.next();
					Long qUid = (Long) dt.getNbsQuestionUid();
					oldAList = (ArrayList<Object>) oldAnswers.get(qUid);
					if (oldAList != null && oldAList.size() > 0) {
						Iterator<Object> oldIter = oldAList.iterator();
						while (oldIter.hasNext()) {
							NbsCaseAnswerDT oldDT = (NbsCaseAnswerDT) oldIter.next();
								oldDT.setItDelete(true);
								oldDT.setItDirty(false);
								oldDT.setItNew(false);
							}
						}
					}

		} else if (strRepeating.equals("Repeating")) {
			while (iter.hasNext()) {
				NbsCaseAnswerDT dt = (NbsCaseAnswerDT) iter.next();
				Long qUid = (Long) dt.getNbsQuestionUid();
				oldAList = (ArrayList<Object>) oldAnswers.get(qUid);
				if (oldAList != null && oldAList.size() > 0) {
					Iterator<Object> oldIter = oldAList.iterator();
					while (oldIter.hasNext()) {
						NbsCaseAnswerDT oldDT = (NbsCaseAnswerDT) oldIter.next();
						// Corrected bug in next line 9-4-2013 (GT & Jit)
						if (oldDT.getAnswerTxt() != null && dt.getAnswerTxt() != null
								&& oldDT.getAnswerTxt().equalsIgnoreCase(dt.getAnswerTxt())
								&& dt.getAnswerGroupSeqNbr() != null && oldDT.getAnswerGroupSeqNbr() != null
								&& dt.getAnswerGroupSeqNbr().intValue() == oldDT.getAnswerGroupSeqNbr()
										.intValue()) {
							dt.setItDirty(true);
							dt.setItNew(false);
							dt.setItDelete(false);
							dt.setNbsCaseAnswerUid(oldDT.getNbsCaseAnswerUid());
							tempList.add(oldDT);
						} else if (dt.getNbsCaseAnswerUid() == null) {
							dt.setItNew(true);
							dt.setItDirty(false);
							dt.setItDelete(false);
						}
					}
				}
			}
		}
		// remove tempList entries from oldMap
		if (oldAList != null)
			oldAList.removeAll(tempList);
			
		} catch (Exception ex) {
			logger.fatal("Exception occured in EDXPHCRUpdateHelper.checkAnswerListForDirty: ," + ex.getMessage(), ex);
			throw new NEDSSAppException(ex.getMessage(), ex);
		}
	}

	private static void markAnswerListForDelete(ArrayList<Object> list) {
		Iterator<Object> iter = list.iterator();
		while (iter.hasNext()) {
			NbsCaseAnswerDT dt = (NbsCaseAnswerDT) iter.next();
			dt.setItDelete(true);
			dt.setItNew(false);
			dt.setItDirty(false);

		}
	}

	@SuppressWarnings("unchecked")
	private void updateNbsRepeatingAnswersForDirty(PageActProxyVO proxyVO, PageActProxyVO oldProxyVO)
			throws NEDSSAppException {
		try {
			Map<Object, Object> oldRepeatingAnswersfromProxy = oldProxyVO.getPageVO().getPageRepeatingAnswerDTMap();
			Map<Object, Object> newRepeatingAnswers = proxyVO.getPageVO().getPageRepeatingAnswerDTMap();
			Map<Object, Object> oldRepeatingAnswers = updateMapWithQIds(oldRepeatingAnswersfromProxy);
			// Map<Object, Object> messageLogMap = new HashMap<Object, Object> ();
			// Iterate through the newAnswers and mark it accordingly
			// 1. If present in new and not present in old - mark it NEW
			// 2. If present in both - mark it DIRTY
			// 3. If present in old and not present in new - mark it DELETE
			Iterator<Object> iter = newRepeatingAnswers.keySet().iterator();
			while (iter.hasNext()) {
				Object qId = (Object) iter.next();
				Object obj = newRepeatingAnswers.get(qId);

				if (obj instanceof ArrayList<?>) {
					ArrayList<Object> answerList = (ArrayList<Object>) obj;

					checkAnswerListForDirty(oldRepeatingAnswers, answerList, "Repeating");
				} else {
					NbsCaseAnswerDT dt = (NbsCaseAnswerDT) obj;
					Long qUid = (Long) dt.getNbsQuestionUid();
					if (oldRepeatingAnswers.get(qUid) == null) {
						dt.setItNew(true);
						dt.setItDirty(false);
						dt.setItDelete(false);
					} else {
						NbsCaseAnswerDT oldDT = (NbsCaseAnswerDT) oldRepeatingAnswers.get(qUid);
						dt.setItDirty(true);
						dt.setItNew(false);
						dt.setItDelete(false);
						dt.setNbsCaseAnswerUid(oldDT.getNbsCaseAnswerUid());
						// remove it from oldMap so that the leftovers in oldMap are DELETE candidates
						oldRepeatingAnswers.remove(qUid);
					}
				}
			}
			// For the leftovers in the oldAnswers, mark them all to DELETE
			Iterator<Object> iter1 = oldRepeatingAnswers.keySet().iterator();
			while (iter1.hasNext()) {
				Object qUid = (Object) iter1.next();
				Object oldObj = oldRepeatingAnswers.get(qUid);
				if (oldObj instanceof ArrayList<?>) {
					ArrayList<Object> answerList = (ArrayList<Object>) oldObj;

					markAnswerListForDelete(answerList);
				} else {
					NbsCaseAnswerDT dt = (NbsCaseAnswerDT) oldObj;
					dt.setItDelete(true);
					dt.setItNew(false);
					dt.setItDirty(false);
				}
			}

			((PageActProxyVO) proxyVO).getPageVO().getPageRepeatingAnswerDTMap().putAll(oldRepeatingAnswers);
		} catch (Exception ex) {
			logger.fatal(
					"Exception occured in EDXPHCRUpdateHelper.updateNbsRepeatingAnswersForDirty: ," + ex.getMessage(),
					ex);
			throw new NEDSSAppException(ex.getMessage(), ex);
		}
	}

	@SuppressWarnings("unchecked")
	private void handleUpdateIngoreScenarioForCoreObjects(PageActProxyVO pageActProxyVO,
			PageActProxyVO oldPageActProxyVO) throws NEDSSAppException {
		try {

			PersonDT oldPersonDT = null;
			PersonDT newPersonDT = null;
			Map<Object, Object> ignoreMethodMap = new HashMap<Object, Object>();

			Collection<Object> oldPersonCollection = oldPageActProxyVO.getThePersonVOCollection();
			if (oldPersonCollection != null) {
				Iterator<Object> iterator = oldPersonCollection.iterator();

				while (iterator.hasNext()) {
					PersonVO personVO = (PersonVO) iterator.next();
					String perCd = personVO.getThePersonDT().getCd();
					if (perCd != null && perCd.equalsIgnoreCase(NEDSSConstants.CLASS_CD_PAT)) {
						oldPersonDT = personVO.getThePersonDT();
						break;
					}
				}
			}

			Collection<Object> newPersonCollection = pageActProxyVO.getThePersonVOCollection();
			if (newPersonCollection != null) {
				Iterator<Object> iterator = newPersonCollection.iterator();

				while (iterator.hasNext()) {
					PersonVO personVO = (PersonVO) iterator.next();
					String perCd = personVO.getThePersonDT().getCd();
					if (perCd != null && perCd.equalsIgnoreCase(NEDSSConstants.CLASS_CD_PAT)) {
						newPersonDT = personVO.getThePersonDT();
						break;
					}
				}
			}

			for (Object key : safe(getIgnoreQuestionMap().keySet())) {
				String dbLocation = ((NbsQuestionMetadata) getIgnoreQuestionMap().get(key)).getDataLocation();
				if (dbLocation != null && dbLocation.contains(".")) {
					String columnName = dbLocation.substring(dbLocation.indexOf(".") + 1, dbLocation.length());
					String tableName = dbLocation.substring(0, dbLocation.indexOf("."));
					String getterMethod = DynamicBeanBinding.getGetterName(columnName);

					if (ignoreMethodMap.get(tableName) == null)
						ignoreMethodMap.put(tableName, new HashMap<Object, Object>());

					((Map<Object, Object>) ignoreMethodMap.get(tableName)).put(getterMethod, columnName);
				}
			}

			NBSBeanUtils nUtils = new NBSBeanUtils();
			nUtils.copyNonNullPropertiesForNullDest(newPersonDT, oldPersonDT,
					(Map<Object, Object>) ignoreMethodMap.get("PERSON"));
			nUtils.copyNonNullPropertiesForNullDest(pageActProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT(),
					oldPageActProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT(),
					(Map<Object, Object>) ignoreMethodMap.get("PUBLIC_HEALTH_CASE"));
			if (pageActProxyVO.getPublicHealthCaseVO().getTheCaseManagementDT() != null
					&& oldPageActProxyVO.getPublicHealthCaseVO().getTheCaseManagementDT() != null)
				nUtils.copyNonNullPropertiesForNullDest(pageActProxyVO.getPublicHealthCaseVO().getTheCaseManagementDT(),
						oldPageActProxyVO.getPublicHealthCaseVO().getTheCaseManagementDT(),
						(Map<Object, Object>) ignoreMethodMap.get("CASE_MANAGEMENT"));
		} catch (Exception ex) {
			logger.fatal("Exception occured in EDXPHCRUpdateHelper.handleUpdateIngoreScenaio: ," + ex.getMessage(), ex);
			throw new NEDSSAppException(ex.getMessage(), ex);
		}

	}

	@SuppressWarnings("unchecked")
	public void handleUpdateIngoreScenarioForNBSCaseAnswer(PageActProxyVO pageActProxyVO,
			PageActProxyVO oldPageActProxyVO) throws NEDSSAppException {
		try {
			Map<Object, Object> oldAnswersFromProxy = oldPageActProxyVO.getPageVO().getPamAnswerDTMap();
			Map<Object, Object> oldAnswers = updateMapWithQIds(oldAnswersFromProxy);
			if (getIgnoreQuestionMap() != null && getIgnoreQuestionMap().size() > 0) {
				Set<Object> keys = getIgnoreQuestionMap().keySet();
				Iterator<Object> ite = keys.iterator();
				while (ite.hasNext()) {
					Object key = (Object) ite.next();
					Object obj = oldAnswers.get(key);
					if (obj == null) {
						pageActProxyVO.getPageVO().getPamAnswerDTMap().remove(key);
					}else if (obj instanceof ArrayList<?>) {
						ArrayList<Object> answerList = (ArrayList<Object>) obj;
						for (Object caseAnswerDT : safe(answerList)) {
							((NbsCaseAnswerDT) caseAnswerDT).setItNew(false);
							((NbsCaseAnswerDT) caseAnswerDT).setItDirty(true);
						}

					} else {
						NbsCaseAnswerDT dt = (NbsCaseAnswerDT) obj;
						dt.setItNew(false);
						dt.setItDirty(true);
					}
					// Overwrite the value in new Investigation
					logger.info("Ignoring the update for Question Uid " + key);
					if (obj != null)
						pageActProxyVO.getPageVO().getPamAnswerDTMap().put(key, obj);
				}
			}

		} catch (Exception ex) {
			logger.fatal("Exception occured in EDXPHCRUpdateHelper.handleUpdateIngoreScenaio: ," + ex.getMessage(), ex);
			throw new NEDSSAppException(ex.getMessage(), ex);
		}
	}

	private Map<Object, Object> updateMapWithQIds(Map<Object, Object> answerMap) throws NEDSSAppException {
		try {

			Map<Object, Object> returnMap = new HashMap<Object, Object>();
			if (answerMap != null && answerMap.size() > 0) {
				Iterator<Object> iter = answerMap.keySet().iterator();
				while (iter.hasNext()) {
					Long key = (Long) iter.next();
					returnMap.put(this.getQuestionKeyMap().get(key), answerMap.get(key));
				}
			}

			return returnMap;
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal(e.getMessage(), e);
			throw new NEDSSAppException(e.getMessage(), e);
		}
	}

	private void handleActIDUpdates(PageActProxyVO proxyVO, PageActProxyVO oldProxyVO) throws NEDSSAppException {

		try {

			PublicHealthCaseVO oldPhcVO = oldProxyVO.getPublicHealthCaseVO();
			PublicHealthCaseVO newPhcVO = proxyVO.getPublicHealthCaseVO();
			PublicHealthCaseDT phcDT = oldPhcVO.getThePublicHealthCaseDT();
			Collection<Object> ActIds = oldPhcVO.getTheActIdDTCollection();
			Map<Object, Object> oldActIDMap = new HashMap<Object, Object>();
			Map<Object, Object> newActIDMap = new HashMap<Object, Object>();

			if (ActIds != null && ActIds.size() > 0) {
				for (Object actIdDT : safe(ActIds)) {
					ActIdDT actId = (ActIdDT) actIdDT;
					oldActIDMap.put(actId.getTypeCd(), actId);
				}
			}

			Collection<Object> ActIdsNew = newPhcVO.getTheActIdDTCollection();
			if (ActIdsNew != null && ActIdsNew.size() > 0) {
				for (Object actIdDT : safe(ActIdsNew)) {
					ActIdDT actId = (ActIdDT) actIdDT;
					actId.setActUid(phcDT.getPublicHealthCaseUid());
					newActIDMap.put(actId.getTypeCd(), actId);
				}
			}
			
			
			if (oldActIDMap.containsKey(NEDSSConstants.ACT_ID_CITY_TYPE_CD)) {
				if (newActIDMap.containsKey(NEDSSConstants.ACT_ID_CITY_TYPE_CD)) {
					ActIdDT actDT = (ActIdDT) newActIDMap.get(NEDSSConstants.ACT_ID_CITY_TYPE_CD);
					actDT.setItDirty(true);
					actDT.setItNew(false);
				}
				if (getIgnoreActIDQuestionMap().containsKey(NEDSSConstants.ACT_ID_CITY_TYPE_CD)) {
					ActIdDT actDT = (ActIdDT) oldActIDMap.get(NEDSSConstants.ACT_ID_CITY_TYPE_CD);
					actDT.setItDirty(true);
					actDT.setItNew(false);
					newActIDMap.put(NEDSSConstants.ACT_ID_CITY_TYPE_CD, actDT);
				}
			}

			if (oldActIDMap.containsKey(NEDSSConstants.ACT_ID_STATE_TYPE_CD)) {
				if (newActIDMap.containsKey(NEDSSConstants.ACT_ID_STATE_TYPE_CD)) {
					ActIdDT actDT = (ActIdDT) newActIDMap.get(NEDSSConstants.ACT_ID_STATE_TYPE_CD);
					actDT.setItDirty(true);
					actDT.setItNew(false);
				}
				if (getIgnoreActIDQuestionMap().containsKey(NEDSSConstants.ACT_ID_STATE_TYPE_CD)) {
					ActIdDT actDT = (ActIdDT) oldActIDMap.get(NEDSSConstants.ACT_ID_STATE_TYPE_CD);
					actDT.setItDirty(true);
					actDT.setItNew(false);
					newActIDMap.put(NEDSSConstants.ACT_ID_STATE_TYPE_CD, actDT);
				}
			}

			if (oldActIDMap.containsKey(NEDSSConstants.ACT_ID_LEGACY_TYPE_CD)) {
				if (newActIDMap.containsKey(NEDSSConstants.ACT_ID_LEGACY_TYPE_CD)) {
					ActIdDT actDT = (ActIdDT) newActIDMap.get(NEDSSConstants.ACT_ID_LEGACY_TYPE_CD);
					actDT.setItDirty(true);
					actDT.setItNew(false);
					
				}
				if (getIgnoreActIDQuestionMap().containsKey(NEDSSConstants.ACT_ID_LEGACY_TYPE_CD)) {
					ActIdDT actDT = (ActIdDT) oldActIDMap.get(NEDSSConstants.ACT_ID_LEGACY_TYPE_CD);
					actDT.setItDirty(true);
					actDT.setItNew(false);
					newActIDMap.put(NEDSSConstants.ACT_ID_LEGACY_TYPE_CD, actDT);
				}
			}
			
			for (Object obj : safe(getIgnoreActIDQuestionMap().keySet())) {
				String typeCd = (String) obj;
				if(newActIDMap.containsKey(typeCd) && ((ActIdDT)newActIDMap.get(typeCd)).isItNew())
					newActIDMap.remove(typeCd);
			}

			newPhcVO.setTheActIdDTCollection(new ArrayList<Object>());
			for (Object obj : safe(newActIDMap.values())) {
				newPhcVO.getTheActIdDTCollection().add((ActIdDT) obj);
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal(e.getMessage(), e);
			throw new NEDSSAppException(e.getMessage(), e);
		}
	}
	
	public static Collection<Object> safe( Collection<Object>  other ) {
	    return other == null ? Collections.emptyList() : other;
	}

}
