package gov.cdc.nedss.systemservice.ejb.edxdocumentejb.util;

import gov.cdc.nedss.act.actid.dt.ActIdDT;
import gov.cdc.nedss.act.publichealthcase.dt.CaseManagementDT;
import gov.cdc.nedss.act.publichealthcase.dt.ConfirmationMethodDT;
import gov.cdc.nedss.act.publichealthcase.dt.PublicHealthCaseDT;
import gov.cdc.nedss.act.publichealthcase.vo.PublicHealthCaseVO;
import gov.cdc.nedss.dsm.ActionType;
import gov.cdc.nedss.dsm.AdvancedCriteriaType;
import gov.cdc.nedss.dsm.AlgorithmDocument;
import gov.cdc.nedss.dsm.AlgorithmDocument.Algorithm;
import gov.cdc.nedss.dsm.CodedType;
import gov.cdc.nedss.dsm.CreateInvestigationType;
import gov.cdc.nedss.dsm.CreateInvestigationWithNNDType;
import gov.cdc.nedss.dsm.CriteriaType;
import gov.cdc.nedss.dsm.DefaultValueType;
import gov.cdc.nedss.dsm.DeleteDocumentType;
import gov.cdc.nedss.dsm.InvestigationDefaultValuesType;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.PageProxyVO;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.act.PageActProxyVO;
import gov.cdc.nedss.pam.act.NbsCaseAnswerDT;
import gov.cdc.nedss.pam.vo.PamVO;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.vo.PamProxyVO;
import gov.cdc.nedss.systemservice.ejb.casenotificationejb.dt.ExportReceivingFacilityDT;
import gov.cdc.nedss.systemservice.ejb.decisionsupportejb.bean.DSMAlgorithm;
import gov.cdc.nedss.systemservice.ejb.decisionsupportejb.bean.DSMAlgorithmHome;
import gov.cdc.nedss.systemservice.ejb.decisionsupportejb.dt.DSMAlgorithmDT;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dt.EdxRuleAlgorothmManagerDT;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dt.EdxRuleManageDT;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.NbsQuestionMetadata;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.StringUtils;
import gov.cdc.nedss.webapp.nbs.action.decisionsupportmanagement.util.DecisionSupportConstants;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.ejb.CreateException;
import javax.ejb.EJBException;

import org.apache.xmlbeans.XmlOptions;

/**
 * ValidateDecisionSupport is a utility class that supports the parsing of the
 * XML decision
 * 
 * @author Pradeep Kumar Sharma
 * 
 */

public class ValidateDecisionSupport {
	static final LogUtils logger = new LogUtils(ValidateDecisionSupport.class.getName());

	public static EdxRuleAlgorothmManagerDT validateProxyVO(Object obj, Collection<Object> algDtColl, NBSSecurityObj nbsSecurityObj) {
		try {
			PamVO pamVO = null;
			PublicHealthCaseVO publicHealthCaseVO = null;
			ExportReceivingFacilityDT pageExportReceivingFacilityDT = null;
			if (obj instanceof PageProxyVO) {
				PageActProxyVO pageProxyVO = (PageActProxyVO) obj;
				publicHealthCaseVO = pageProxyVO.getPublicHealthCaseVO();
				pageExportReceivingFacilityDT = pageProxyVO.getExportReceivingFacilityDT();
				pamVO = pageProxyVO.getPageVO();

			} else if (obj instanceof PamProxyVO) {
				PamProxyVO pamProxyVO = (PamProxyVO) obj;
				publicHealthCaseVO = pamProxyVO.getPublicHealthCaseVO();
				pageExportReceivingFacilityDT = pamProxyVO.getExportReceivingFacilityDT();
				pamVO = pamProxyVO.getPamVO();
			}
			EdxRuleAlgorothmManagerDT edxRuleAlgorothmManagerDT = new EdxRuleAlgorothmManagerDT();
			
			// TreeMap<Object, Object> condAndFormCdTreeMap =
			// CachedDropDowns.getConditionCdAndInvFormCd();
			if(publicHealthCaseVO==null){
				logger.error("ValidateDecisionSupport.validateProxyVO Exception thrown:publicHealthCaseVO is null-" + publicHealthCaseVO);
				throw new EJBException("ERROR:-ValidateDecisionSupport.validateProxyVO No action has been specified. Please check why publicHealthCaseVO is null." + publicHealthCaseVO);
			}
			//Collection<Object> algColl = createAlgorithmMap(publicHealthCaseVO.getThePublicHealthCaseDT().getCd(), nbsSecurityObj);
			TreeMap<Long, EdxRuleAlgorothmManagerDT> selected = new TreeMap<Long, EdxRuleAlgorothmManagerDT>();
			Map<Object, Object> questionIdentifierMap = EdxPHCRDocumentUtil.loadQuestions(publicHealthCaseVO.getThePublicHealthCaseDT().getCd());
			if (algDtColl != null && algDtColl.size() > 0) {
				Collection<Object> algColl = createAlgorithmMap(algDtColl, nbsSecurityObj);
				Iterator<Object> outerIter = algColl.iterator();
				while (outerIter.hasNext()) {
					edxRuleAlgorothmManagerDT = (EdxRuleAlgorothmManagerDT) outerIter.next();
					boolean basicCriteriaMatch = false;
					boolean foundMatch = false;
					Collection<Object> sendingFacilityColl = edxRuleAlgorothmManagerDT.getSendingFacilityColl();
					if (sendingFacilityColl != null) {
						Iterator<Object> it = sendingFacilityColl.iterator();
						while (it.hasNext()) {
							ExportReceivingFacilityDT exportReceivingFacilityDT = (ExportReceivingFacilityDT) it.next();
							if (exportReceivingFacilityDT.getReceivingSystemDescTxt().equalsIgnoreCase(pageExportReceivingFacilityDT.getReceivingSystemDescTxt())
									&& exportReceivingFacilityDT.getReceivingSystemOid().equalsIgnoreCase(pageExportReceivingFacilityDT.getReceivingSystemOid())) {
								basicCriteriaMatch = true;
							}
						}
					} else {
						basicCriteriaMatch = true;
					}

					if (basicCriteriaMatch) {
						Map<Object, Object> criteriaMap = edxRuleAlgorothmManagerDT.getEdxRuleAdvCriteriaDTMap();
						if (criteriaMap != null && criteriaMap.size() > 0) {
							Set<Object> criteriaSet = criteriaMap.keySet();
							Iterator<?> criteriaIterator = criteriaSet.iterator();
							while (criteriaIterator.hasNext()) {
								String questionId = (String) criteriaIterator.next();
								Object object = criteriaMap.get(questionId);
								if (object instanceof EdxRuleManageDT) {
									EdxRuleManageDT edxRuleManageDT = (EdxRuleManageDT) object;
									NbsQuestionMetadata criteriaMetaData = (NbsQuestionMetadata) questionIdentifierMap.get(questionId);
									if(criteriaMetaData != null)
										foundMatch = processRuleMetadata(publicHealthCaseVO, pamVO, edxRuleManageDT, criteriaMetaData);
									if (!foundMatch)
										break;
								} else if (object instanceof Collection<?>) {
									Collection<?> collection = (ArrayList<?>) object;
									Iterator<?> iter = collection.iterator();
									while (iter.hasNext()) {
										EdxRuleManageDT edxRuleManageDT = (EdxRuleManageDT) iter.next();
										NbsQuestionMetadata criteriaMetaData = (NbsQuestionMetadata) questionIdentifierMap.get(questionId);
										if(criteriaMetaData != null)
											foundMatch = processRuleMetadata(publicHealthCaseVO, pamVO, edxRuleManageDT, criteriaMetaData);
										if (!foundMatch)
											break;
									}
								}
							}
						} else {
							foundMatch = true;
						}
					}
					if (foundMatch) {
						Map<Object, Object> applyMap = edxRuleAlgorothmManagerDT.getEdxRuleApplyDTMap();
						if (applyMap != null && applyMap.size() > 0) {
							Set<Object> set = applyMap.keySet();
							Iterator<?> iterator = set.iterator();
							while (iterator.hasNext()) {
								String questionId = (String) iterator.next();
						
								EdxRuleManageDT edxRuleManageDT = (EdxRuleManageDT) applyMap.get(questionId);

								NbsQuestionMetadata metaData = (NbsQuestionMetadata) questionIdentifierMap.get(questionId);

								if (metaData.getDataLocation() != null && metaData.getDataLocation().trim().toUpperCase().startsWith("PUBLIC_HEALTH_CASE")) {
									processNbsObject(edxRuleManageDT, publicHealthCaseVO, metaData);
								} else if(metaData.getDataLocation() != null && metaData.getDataLocation().trim().toUpperCase().startsWith("ACT_ID")){
									processActIds(edxRuleManageDT, publicHealthCaseVO, metaData);
								} else if (metaData.getDataLocation() != null && metaData.getDataLocation().trim().toUpperCase().startsWith("NBS_CASE_ANSWER")) {
									processNBSCaseAnswerDT(edxRuleManageDT, publicHealthCaseVO, pamVO, metaData);
								} else if (metaData.getDataLocation() != null && metaData.getDataLocation().trim().toUpperCase().startsWith("CONFIRMATION_METHOD.CONFIRMATION_METHOD_CD")) {
									processConfirmationMethodCodeDT(edxRuleManageDT, publicHealthCaseVO, metaData);
								} else if (metaData.getDataLocation() != null && metaData.getDataLocation().trim().toUpperCase().startsWith("CONFIRMATION_METHOD.CONFIRMATION_METHOD_TIME")) {
									processConfirmationMethodTimeDT(edxRuleManageDT, publicHealthCaseVO, metaData);
								}

							}
						}
						edxRuleAlgorothmManagerDT.setObject(obj);
						// break;
						long updTime = edxRuleAlgorothmManagerDT.getLastChgTime().getTime();
						selected.put(updTime, edxRuleAlgorothmManagerDT);
					}else{
						edxRuleAlgorothmManagerDT.setDsmAlgorithmName(null);
						edxRuleAlgorothmManagerDT.setDsmAlgorithmUid(null);
					}
				}

			}
			if (!selected.isEmpty()) {
				edxRuleAlgorothmManagerDT = selected.get(selected.lastKey());
			}
			edxRuleAlgorothmManagerDT.setConditionName(CachedDropDowns.getConditionDesc(publicHealthCaseVO.getThePublicHealthCaseDT().getCd()));

			return edxRuleAlgorothmManagerDT;
		} catch (Exception e) {
			logger.error("ValidateDecisionSupport.validateProxyVO Exception thrown:-" + e);
			throw new EJBException("ERROR:-ValidateDecisionSupport.validateProxyVO No action has been specified. Please check." + e, e);
		}
	}

	private static boolean processRuleMetadata(PublicHealthCaseVO publicHealthCaseVO, PamVO pamVO, EdxRuleManageDT edxRuleManageDT, NbsQuestionMetadata criteriaMetaData) {
		boolean foundMatch = false;
		if (criteriaMetaData.getDataLocation() != null && criteriaMetaData.getDataLocation().trim().toUpperCase().startsWith("PUBLIC_HEALTH_CASE")) {
			boolean foundNBSObjectMatch = checkNbsObject(edxRuleManageDT, publicHealthCaseVO.getThePublicHealthCaseDT(), criteriaMetaData);
			if ((foundNBSObjectMatch && !foundMatch) || (foundNBSObjectMatch && foundMatch)) {
				foundMatch = foundNBSObjectMatch;
			} else if ((!foundNBSObjectMatch && foundMatch) || (!foundNBSObjectMatch && !foundMatch)) {
				foundMatch = false;
			}
		} else if (criteriaMetaData.getDataLocation() != null && criteriaMetaData.getDataLocation().trim().toUpperCase().startsWith("NBS_CASE_ANSWER")) {
			boolean foundNBSCaseMatch = checkNBSCaseAnswerDT(edxRuleManageDT, pamVO, criteriaMetaData);
			if ((foundNBSCaseMatch && !foundMatch) || (foundNBSCaseMatch && foundMatch)) {
				foundMatch = foundNBSCaseMatch;
			} else if ((!foundNBSCaseMatch && foundMatch) || (!foundNBSCaseMatch && !foundMatch)) {
				foundMatch = false;
			}
		} else if (criteriaMetaData.getDataLocation() != null && criteriaMetaData.getDataLocation().trim().toUpperCase().startsWith("CONFIRMATION_METHOD.CONFIRMATION_METHOD_TIME")) {
			Collection<Object> collection = publicHealthCaseVO.getTheConfirmationMethodDTCollection();
			if (collection != null && collection.size() > 0) {
				boolean foundNBSCollObjectMatch = false;
				Iterator<Object> confItetrator = collection.iterator();
				while (confItetrator.hasNext()) {
					ConfirmationMethodDT confirmationMethodDT = (ConfirmationMethodDT) confItetrator.next();
					foundNBSCollObjectMatch = checkNbsObject(edxRuleManageDT, confirmationMethodDT, criteriaMetaData);
					if (foundNBSCollObjectMatch) {
						foundMatch = foundNBSCollObjectMatch;
						return foundNBSCollObjectMatch;
					}
				}
				if (!foundNBSCollObjectMatch) {
					foundMatch = false;
				}
			}
		} else if (criteriaMetaData.getDataLocation() != null && criteriaMetaData.getDataLocation().trim().toUpperCase().startsWith("CONFIRMATION_METHOD.CONFIRMATION_METHOD_CD")) {
			Collection<Object> collection = publicHealthCaseVO.getTheConfirmationMethodDTCollection();
			if (collection != null && collection.size() > 0) {
				boolean foundNBSCollObjectMatch = false;
				Iterator<Object> confItetrator = collection.iterator();
				while (confItetrator.hasNext()) {
					ConfirmationMethodDT confirmationMethodDT = (ConfirmationMethodDT) confItetrator.next();
					if (confirmationMethodDT.getConfirmationMethodCd().equals(edxRuleManageDT.getValue())) {
						return true;
					}
					// foundNBSCollObjectMatch
					// =checkNbsObject(edxRuleManageDT,confirmationMethodDT,
					// criteriaMetaData);
					/*
					 * if(foundNBSCollObjectMatch){
					 * foundMatch=foundNBSCollObjectMatch; return
					 * foundNBSCollObjectMatch; }
					 */
				}
				if (!foundNBSCollObjectMatch) {
					return foundNBSCollObjectMatch;
				}
			}
		}/*
		 * else if(criteriaMetaData.getDataLocation()!=null &&
		 * criteriaMetaData.getDataLocation
		 * ().trim().toUpperCase().startsWith("PERSON.")){ Collection<Object>
		 * collection = pageActProxyVO.getThePersonVOCollection();
		 * if(collection!=null && collection.size()>0){ boolean
		 * foundNBSCollObjectMatch =false; Iterator<Object>
		 * innerIterator=collection.iterator(); while(innerIterator.hasNext()){
		 * PersonDT personDT = (PersonDT)innerIterator.next();
		 * if(personDT.getCd().equalsIgnoreCase(NEDSSConstants.PAT)){
		 * foundNBSCollObjectMatch =checkNbsObject(edxRuleManageDT,personDT,
		 * criteriaMetaData); if(foundNBSCollObjectMatch){
		 * foundMatch=foundNBSCollObjectMatch; return foundNBSCollObjectMatch; }
		 * } } if(!foundNBSCollObjectMatch){ foundMatch = false; } } }else
		 * if(criteriaMetaData.getDataLocation()!=null &&
		 * criteriaMetaData.getDataLocation
		 * ().trim().toUpperCase().startsWith("PARTICIPATION.")){
		 * Collection<Object> collection =
		 * pageActProxyVO.getThePersonVOCollection(); if(collection!=null &&
		 * collection.size()>0){ boolean foundNBSCollObjectMatch =false;
		 * Iterator<Object> innerIterator=collection.iterator();
		 * while(innerIterator.hasNext()){ PersonDT personDT =
		 * (PersonDT)innerIterator.next();
		 * if(personDT.getCd().equalsIgnoreCase(NEDSSConstants.PAT)){
		 * foundNBSCollObjectMatch =checkNbsObject(edxRuleManageDT,personDT,
		 * criteriaMetaData); if(foundNBSCollObjectMatch){
		 * foundMatch=foundNBSCollObjectMatch; return foundNBSCollObjectMatch; }
		 * } } if(!foundNBSCollObjectMatch){ foundMatch = false; } } }
		 */
		return foundMatch;

	}
	
	public static void processActIds(EdxRuleManageDT edxRuleManageDT,
			PublicHealthCaseVO publicHealthCaseVO, NbsQuestionMetadata metaData) {
		String behavior = edxRuleManageDT.getBehavior();
		boolean isOverwrite = false;
		if (behavior.equalsIgnoreCase("1")) {
			isOverwrite = true;
		} else if (behavior.equalsIgnoreCase("2")) {
			isOverwrite = false;
		}
		Collection<Object> actIdColl = publicHealthCaseVO
				.getTheActIdDTCollection();
		if (actIdColl != null && actIdColl.size() > 0) {
			Iterator<Object> ite = actIdColl.iterator();
			ActIdDT actIdDT = (ActIdDT) ite.next();
			if (actIdDT.getTypeCd() != null
					&& actIdDT.getTypeCd().equalsIgnoreCase(
							NEDSSConstants.ACT_ID_STATE_TYPE_CD)
					&& metaData.getDataCd() != null
					&& metaData.getDataCd().equalsIgnoreCase(
							NEDSSConstants.ACT_ID_STATE_TYPE_CD)) {
				if (isOverwrite)
					actIdDT.setRootExtensionTxt(edxRuleManageDT
							.getDefaultStringValue());
				else if (!isOverwrite && actIdDT.getRootExtensionTxt() == null)
					actIdDT.setRootExtensionTxt(edxRuleManageDT
							.getDefaultStringValue());
			} else if (actIdDT.getTypeCd() != null
					&& actIdDT.getTypeCd().equalsIgnoreCase("CITY")
					&& metaData.getDataCd() != null
					&& metaData.getDataCd().equalsIgnoreCase("CITY")) {
				if (isOverwrite)
					actIdDT.setRootExtensionTxt(edxRuleManageDT
							.getDefaultStringValue());
				else if (!isOverwrite && actIdDT.getRootExtensionTxt() == null)
					actIdDT.setRootExtensionTxt(edxRuleManageDT
							.getDefaultStringValue());
			}

		}
	}

	public static void processNBSCaseAnswerDT(EdxRuleManageDT edxRuleManageDT, PublicHealthCaseVO publicHealthCaseVO, PamVO pamVO, NbsQuestionMetadata metaData) {
		String behavior = edxRuleManageDT.getBehavior();
		boolean isOverwrite = false;
		if (behavior.equalsIgnoreCase("1")) {
			isOverwrite = true;
		} else if (behavior.equalsIgnoreCase("2")) {
			isOverwrite = false;
		}
		String value = edxRuleManageDT.getDefaultStringValue();
		if(value!=null && value.equalsIgnoreCase(NEDSSConstants.USE_CURRENT_DATE))
			value=new SimpleDateFormat("MM/dd/yyyy").format(Calendar.getInstance().getTime());
		edxRuleManageDT.setDefaultStringValue(value);
		Map<Object,Object> answerMap = pamVO.getPamAnswerDTMap();
		logger.debug("EdxRuleManageDT value is " + edxRuleManageDT.toString());
		if (isOverwrite) {
			Collection<Object> list = new ArrayList<Object>();
			if (metaData.getNbsUiComponentUid().compareTo(1013L) == 0) {
				Collection<Object> toValueColl = edxRuleManageDT.getDefaultCodedValueColl();
				if (toValueColl != null) {
					Iterator<?> iterator = toValueColl.iterator();
					int i = 1;
					while (iterator.hasNext()) {
						String code = (String) iterator.next();
						NbsCaseAnswerDT nbsAnswerDT = new NbsCaseAnswerDT();
						nbsAnswerDT.setAnswerTxt(code);
						nbsAnswerDT.setNbsQuestionUid(metaData.getNbsQuestionUid());
						nbsAnswerDT.setSeqNbr(i++);
						EdxPHCRDocumentUtil.setStandardNBSCaseAnswerVals(publicHealthCaseVO, nbsAnswerDT);
						list.add(nbsAnswerDT);
					}
				}
				answerMap.put(metaData.getQuestionIdentifier(), list);
			} else {
				String code = "";
				Collection<Object> toValueColl = edxRuleManageDT.getDefaultCodedValueColl();
				if (toValueColl != null) {
					Iterator<?> iterator = toValueColl.iterator();
					while (iterator.hasNext()) {
						code = (String) iterator.next();
					}
				}
				NbsCaseAnswerDT nbsAnswerDT = new NbsCaseAnswerDT();
				if (code != null)
					nbsAnswerDT.setAnswerTxt(code);
				if (edxRuleManageDT.getDefaultNumericValue() != null)
					nbsAnswerDT.setAnswerTxt(edxRuleManageDT.getDefaultNumericValue());
				if (edxRuleManageDT.getDefaultStringValue() != null)
					nbsAnswerDT.setAnswerTxt(edxRuleManageDT.getDefaultStringValue());
				nbsAnswerDT.setNbsQuestionUid(metaData.getNbsQuestionUid());
				nbsAnswerDT.setSeqNbr(0);
				EdxPHCRDocumentUtil.setStandardNBSCaseAnswerVals(publicHealthCaseVO, nbsAnswerDT);
				answerMap.put(metaData.getQuestionIdentifier(), nbsAnswerDT);
			}
		} else {
			if (pamVO.getPamAnswerDTMap().get(metaData.getQuestionIdentifier()) == null) {
				Collection<Object> list = new ArrayList<Object>();
				if (metaData.getNbsUiComponentUid().compareTo(1013L) == 0) {
					Collection<Object> toValueColl = edxRuleManageDT.getDefaultCodedValueColl();
					if (toValueColl != null) {
						Iterator<?> iterator = toValueColl.iterator();
						int i = 1;
						while (iterator.hasNext()) {
							String code = (String) iterator.next();
							NbsCaseAnswerDT nbsAnswerDT = new NbsCaseAnswerDT();
							nbsAnswerDT.setAnswerTxt(code);
							nbsAnswerDT.setNbsQuestionUid(metaData.getNbsQuestionUid());
							nbsAnswerDT.setSeqNbr(i++);
							EdxPHCRDocumentUtil.setStandardNBSCaseAnswerVals(publicHealthCaseVO, nbsAnswerDT);
							list.add(nbsAnswerDT);
						}
					}
					answerMap.put(metaData.getQuestionIdentifier(), list);
				} else {
					String code = "";
					Collection<Object> toValueColl = edxRuleManageDT.getDefaultCodedValueColl();
					if (toValueColl != null) {
						Iterator<?> iterator = toValueColl.iterator();
						while (iterator.hasNext()) {
							code = (String) iterator.next();
						}
					}
					NbsCaseAnswerDT nbsAnswerDT = new NbsCaseAnswerDT();
					if (code != null)
						nbsAnswerDT.setAnswerTxt(code);
					if (edxRuleManageDT.getDefaultNumericValue() != null)
						nbsAnswerDT.setAnswerTxt(edxRuleManageDT.getDefaultNumericValue());
					if (edxRuleManageDT.getDefaultStringValue() != null)
						nbsAnswerDT.setAnswerTxt(edxRuleManageDT.getDefaultStringValue());
					nbsAnswerDT.setNbsQuestionUid(metaData.getNbsQuestionUid());
					nbsAnswerDT.setSeqNbr(0);
					EdxPHCRDocumentUtil.setStandardNBSCaseAnswerVals(publicHealthCaseVO, nbsAnswerDT);
					answerMap.put(metaData.getQuestionIdentifier(), nbsAnswerDT);
				}
			} else {
				logger.debug("pageActProxyVO.getPageVO().getPamAnswerDTMap().get(metaData.getQuestionIdentifier())!=null for  metaData.getQuestionIdentifier():-" + metaData.getQuestionIdentifier());
				logger.error(edxRuleManageDT.toString());
			}
		}
		pamVO.setPamAnswerDTMap(answerMap);
	}

	public static PublicHealthCaseVO processConfirmationMethodTimeDT(EdxRuleManageDT edxRuleManageDT, PublicHealthCaseVO publicHealthCaseVO, NbsQuestionMetadata metaData) {
		String behavior = edxRuleManageDT.getBehavior();
		boolean isOverwrite = false;
		if (behavior.equalsIgnoreCase("1")) {
			isOverwrite = true;
		} else if (behavior.equalsIgnoreCase("2")) {
			isOverwrite = false;
		}
		String time = edxRuleManageDT.getDefaultStringValue();

		//If the date selected is current date, the date is translated to MM/dd/yyyy
		if(time!=null && time.equalsIgnoreCase(NEDSSConstants.USE_CURRENT_DATE))
			time=new SimpleDateFormat("MM/dd/yyyy").format(Calendar.getInstance().getTime());

		logger.debug("EdxRuleManageDT value is " + edxRuleManageDT.toString());
		if (isOverwrite) {
			Collection<Object> list = new ArrayList<Object>();
				if (time != null && publicHealthCaseVO.getTheConfirmationMethodDTCollection() != null) {
					Iterator<?> iterator = publicHealthCaseVO.getTheConfirmationMethodDTCollection().iterator();
					while (iterator.hasNext()) {
						ConfirmationMethodDT confirmDT = (ConfirmationMethodDT) iterator.next();
						confirmDT.setConfirmationMethodTime_s(time);
						list.add(confirmDT);
					}
					publicHealthCaseVO.setTheConfirmationMethodDTCollection(list);
				} else {
					ConfirmationMethodDT confirmDT = new ConfirmationMethodDT();
					confirmDT.setConfirmationMethodTime_s(time);
					confirmDT.setPublicHealthCaseUid(publicHealthCaseVO.getThePublicHealthCaseDT().getPublicHealthCaseUid());
					confirmDT.setItNew(true);
					
					
					//check previous code entered:
					Collection<Object> confirmColl = publicHealthCaseVO.getTheConfirmationMethodDTCollection();
					if(confirmColl!=null){
					Iterator<Object> cofirmIt = confirmColl.iterator();
					String code;
					
					while (cofirmIt.hasNext()) {
						ConfirmationMethodDT confirmDTCode = (ConfirmationMethodDT) cofirmIt.next();
						if (confirmDTCode.getConfirmationMethodCd() != null){
							code = confirmDTCode.getConfirmationMethodCd();
							confirmDT.setConfirmationMethodCd(code);
						break;	
						}
					}
					}
					
					
					
					
					
					
					list.add(confirmDT);
					publicHealthCaseVO.setTheConfirmationMethodDTCollection(list);
				}

		} else {
			boolean loopbreak = false;
			if (publicHealthCaseVO.getTheConfirmationMethodDTCollection() == null) {
				Collection<Object> list = new ArrayList<Object>();
				if (metaData.getNbsUiComponentUid().compareTo(1013L) == 0) {
					ConfirmationMethodDT confirmDT = new ConfirmationMethodDT();
					confirmDT.setConfirmationMethodTime_s(time);
					confirmDT.setPublicHealthCaseUid(publicHealthCaseVO.getThePublicHealthCaseDT().getPublicHealthCaseUid());
					confirmDT.setItNew(true);
					list.add(confirmDT);
					publicHealthCaseVO.setTheConfirmationMethodDTCollection(list);
				} else {
					
					if(publicHealthCaseVO.getTheConfirmationMethodDTCollection()!=null){
					Iterator<?> iterator = publicHealthCaseVO.getTheConfirmationMethodDTCollection().iterator();
					while (iterator.hasNext()) {
						ConfirmationMethodDT confirmDT = (ConfirmationMethodDT) iterator.next();
						if (confirmDT.getConfirmationMethodTime() != null) {
							loopbreak = true;
							break;
						}
						confirmDT.setConfirmationMethodTime_s(time);
						list.add(confirmDT);
					}
					if (!loopbreak)
						publicHealthCaseVO.setTheConfirmationMethodDTCollection(list);

					logger.error("This should not happen! There is some critical error in the metadata! Please check." + metaData.toString());
					}
					else{//if the getTheConfirmationMethodDTCollection == null, overwrite
						
						ConfirmationMethodDT confirmDT = new ConfirmationMethodDT();
						confirmDT.setConfirmationMethodTime_s(time);
						confirmDT.setPublicHealthCaseUid(publicHealthCaseVO.getThePublicHealthCaseDT().getPublicHealthCaseUid());
						confirmDT.setItNew(true);		
						
						list.add(confirmDT);
						publicHealthCaseVO.setTheConfirmationMethodDTCollection(list);	
					}
					}
			} else {
				logger.debug("publicHealthCaseVO().getTheConfirmationMethodDTCollection()!=null for  metaData.getQuestionIdentifier():-" + metaData.getQuestionIdentifier());
				logger.error(edxRuleManageDT.toString());
			}
		}
		return publicHealthCaseVO;
	}
	
/**
 * processConfirmationMethodCodeDTRequired(): Confirmation method is required if the date as been entered. If there's no method but there's a date, the Confirmation method is NA
 * @param publicHealthCaseVO
 */
	
public static void processConfirmationMethodCodeDTRequired(PublicHealthCaseVO publicHealthCaseVO){
		
		Collection<Object> confirmColl = publicHealthCaseVO.getTheConfirmationMethodDTCollection();
		
		if(confirmColl!=null){
			Iterator<Object> cofirmIt = confirmColl.iterator();
			
			while (cofirmIt.hasNext()) {
				ConfirmationMethodDT confirmDT = (ConfirmationMethodDT) cofirmIt.next();
				if (confirmDT.getConfirmationMethodCd() == null && confirmDT.getConfirmationMethodTime()!=null){
					confirmDT.setConfirmationMethodCd("NA");
					Collection<Object> list = new ArrayList<Object>();
					list.add(confirmDT);
					publicHealthCaseVO.setTheConfirmationMethodDTCollection(list);
				}
		
			}
		}
	}


	public static void processConfirmationMethodCodeDT(EdxRuleManageDT edxRuleManageDT, PublicHealthCaseVO publicHealthCaseVO, NbsQuestionMetadata metaData) {
		String behavior = edxRuleManageDT.getBehavior();
		boolean isOverwrite = false;
		if (behavior.equalsIgnoreCase("1")) {
			isOverwrite = true;
		} else if (behavior.equalsIgnoreCase("2")) {
			isOverwrite = false;
		}
		logger.debug("EdxRuleManageDT value is " + edxRuleManageDT.toString());
		if (isOverwrite) {
			Collection<Object> list = new ArrayList<Object>();
			if (metaData.getNbsUiComponentUid().compareTo(1013L) == 0) {
				Collection<Object> toValueColl = edxRuleManageDT.getDefaultCodedValueColl();
				if (toValueColl != null) {
					Iterator<?> iterator = toValueColl.iterator();
					while (iterator.hasNext()) {
						String code = (String) iterator.next();
						ConfirmationMethodDT confirmDT = new ConfirmationMethodDT();
						confirmDT.setConfirmationMethodCd(code);
						confirmDT.setPublicHealthCaseUid(publicHealthCaseVO.getThePublicHealthCaseDT().getPublicHealthCaseUid());
						confirmDT.setItNew(true);
						
						
						
						//check the previous time entered:
						Collection<Object> confirmColl = publicHealthCaseVO.getTheConfirmationMethodDTCollection();
						if(confirmColl!=null){
						Iterator<Object> cofirmIt = confirmColl.iterator();
						Timestamp time = null;
						
						while (cofirmIt.hasNext()) {
							ConfirmationMethodDT confirmDTTime = (ConfirmationMethodDT) cofirmIt.next();
							if (confirmDTTime.getConfirmationMethodTime() != null){
								time = confirmDTTime.getConfirmationMethodTime();
								confirmDT.setConfirmationMethodTime(time);
							break;	
							}
						}
						}
						
						
						list.add(confirmDT);
					}
				}
				publicHealthCaseVO.setTheConfirmationMethodDTCollection(list);
			}
		} else {
			if (publicHealthCaseVO.getTheConfirmationMethodDTCollection() == null) {
				Collection<Object> list = new ArrayList<Object>();
				if (metaData.getNbsUiComponentUid().compareTo(1013L) == 0) {
					Collection<Object> toValueColl = edxRuleManageDT.getDefaultCodedValueColl();
					if (toValueColl != null) {
						Iterator<?> iterator = toValueColl.iterator();
						while (iterator.hasNext()) {
							String code = (String) iterator.next();
							ConfirmationMethodDT confirmDT = new ConfirmationMethodDT();
							confirmDT.setConfirmationMethodCd(code);
							confirmDT.setPublicHealthCaseUid(publicHealthCaseVO.getThePublicHealthCaseDT().getPublicHealthCaseUid());
							confirmDT.setItNew(true);
							list.add(confirmDT);
						}
					}
					publicHealthCaseVO.setTheConfirmationMethodDTCollection(list);
				} else {
					logger.error("This should not happen! There is some critical error in the metadata! Please check." + metaData.toString());
				}
			} else {
				Collection<Object> list = new ArrayList<Object>();
				Collection<Object> toValueColl = edxRuleManageDT.getDefaultCodedValueColl();
				if (toValueColl != null) {
					Timestamp time = null;
					Iterator<?> iterator = toValueColl.iterator();
					while (iterator.hasNext()) {
						String code = (String) iterator.next();
						Collection<Object> confirmColl = publicHealthCaseVO.getTheConfirmationMethodDTCollection();
						Iterator<Object> cofirmIt = confirmColl.iterator();
						boolean matchFound = false;
						while (cofirmIt.hasNext()) {
							ConfirmationMethodDT confirmDT = (ConfirmationMethodDT) cofirmIt.next();
							if (confirmDT.getConfirmationMethodTime() != null)
								time = confirmDT.getConfirmationMethodTime();
							if (confirmDT.getConfirmationMethodCd() == null || confirmDT.getConfirmationMethodCd().trim().equals("")) {
								break;
							} else {
								if (confirmDT.getConfirmationMethodCd().equals(code)) {
									break;
								} else {
									list.add(confirmDT);
								}
							}
							confirmDT.setConfirmationMethodCd(code);
						}
						if (!matchFound) {
							ConfirmationMethodDT confirmDT = new ConfirmationMethodDT();
							confirmDT.setConfirmationMethodCd(code);
							confirmDT.setConfirmationMethodTime(time);
							confirmDT.setPublicHealthCaseUid(publicHealthCaseVO.getThePublicHealthCaseDT().getPublicHealthCaseUid());
							confirmDT.setItNew(true);
							list.add(confirmDT);
						}
					}
					publicHealthCaseVO.setTheConfirmationMethodDTCollection(list);
				}
			}
		}
	}

	private static boolean checkNBSCaseAnswerDT(EdxRuleManageDT edxRuleManageDT, PamVO pamVO, NbsQuestionMetadata metaData) {
		logger.debug("EdxRuleManageDT value is " + edxRuleManageDT.toString());

		Collection<Object> nbsCaseAnswerColl = pamVO.getPamAnswerDTMap().values();
		if (nbsCaseAnswerColl != null) {
			Iterator<?> iterator = nbsCaseAnswerColl.iterator();
			while (iterator.hasNext()) {
				NbsCaseAnswerDT nbsCaseAnswerDT = (NbsCaseAnswerDT) iterator.next();

				if (metaData.getDataType().equalsIgnoreCase(NEDSSConstants.NBS_QUESTION_DATATYPE_TEXT) || metaData.getDataType().equalsIgnoreCase(NEDSSConstants.NBS_QUESTION_DATATYPE_CODED_VALUE)) {
					String logic = edxRuleManageDT.getLogic();
					if (logic.equalsIgnoreCase("CT")) {
						if (nbsCaseAnswerDT.getAnswerTxt().indexOf(edxRuleManageDT.getValue()) >= 0) {
							return true;
						}
					} else if (logic.equalsIgnoreCase("=")) {
						if (nbsCaseAnswerDT.getAnswerTxt().equals(edxRuleManageDT.getValue())) {
							return true;
						}
					} else if (logic.equalsIgnoreCase("!=")) {
						if (!nbsCaseAnswerDT.getAnswerTxt().equals(edxRuleManageDT.getValue())) {
							return true;
						}
					}

				} else if (metaData.getDataType().equalsIgnoreCase(NEDSSConstants.NBS_QUESTION_DATATYPE_DATETIME) || metaData.getDataType().equalsIgnoreCase(NEDSSConstants.DATETIME_DATATYPE)
						|| metaData.getDataType().equalsIgnoreCase(NEDSSConstants.NBS_QUESTION_DATATYPE_DATE) || metaData.getDataType().equalsIgnoreCase(NEDSSConstants.NBS_QUESTION_DATATYPE_NUMERIC)) {
					long sourceValue = 0;
					long advanceCriteria = 0;
					if (!metaData.getDataType().equalsIgnoreCase(NEDSSConstants.NBS_QUESTION_DATATYPE_NUMERIC)) {
						Timestamp time = StringUtils.stringToStrutsTimestamp(nbsCaseAnswerDT.getAnswerTxt());
						sourceValue = time.getTime();
						advanceCriteria = time.getTime();

					} else {
						if (nbsCaseAnswerDT.getAnswerTxt() != null)
							sourceValue = Long.parseLong(nbsCaseAnswerDT.getAnswerTxt());
						else
							sourceValue = new Long(0);
						if (edxRuleManageDT.getValue() != null)
							advanceCriteria = Long.parseLong(edxRuleManageDT.getValue());
						else
							advanceCriteria = new Long(0);

					}
					String logic = edxRuleManageDT.getLogic();

					if (logic.equalsIgnoreCase("!=")) {
						if (sourceValue != advanceCriteria) {
							return true;
						}
					} else if (logic.equalsIgnoreCase(">")) {
						if (sourceValue > advanceCriteria) {
							return true;
						}
					} else if (logic.equalsIgnoreCase("=>")) {
						if ((sourceValue == advanceCriteria) || (sourceValue > advanceCriteria)) {
							return true;
						}
					} else if (logic.equalsIgnoreCase("<")) {
						if (sourceValue < advanceCriteria) {
							return true;
						}
					} else if (logic.equalsIgnoreCase("<=")) {
						if ((sourceValue == advanceCriteria) || (sourceValue < advanceCriteria)) {
							return true;
						}
					} else if (logic.equalsIgnoreCase("=")) {
						if (sourceValue == advanceCriteria) {
							return true;
						}
					}

					if (nbsCaseAnswerDT.getAnswerTxt().equals(edxRuleManageDT.getValue()))
						return true;
				}
			}
		}

		return false;
	}
	
	public static boolean checkNbsObject(EdxRuleManageDT edxRuleManageDT, Object object, NbsQuestionMetadata metaData) {
		String dataLocation = metaData.getDataLocation();
		String setMethodName = dataLocation.replaceAll("_", "");
		setMethodName = "SET" + setMethodName.substring(setMethodName.indexOf(".") + 1, setMethodName.length());

		String getMethodName = dataLocation.replaceAll("_", "");
		getMethodName = "GET" + getMethodName.substring(getMethodName.indexOf(".") + 1, getMethodName.length());

		Class<?> phcClass = object.getClass();
		try {
			Method[] methodList = phcClass.getDeclaredMethods();
			for (int i = 0; i < methodList.length; i++) {
				Method method = (Method) methodList[i];
				if (method.getName().equalsIgnoreCase(getMethodName)) {
					//System.out.println(method.getName());
					Object ob = method.invoke(object, (Object[]) null);
					
					String logic = edxRuleManageDT.getLogic();
					
					if (ob == null && logic.equalsIgnoreCase("!="))
						return true;
					else if (ob == null)
						return false;
					
					if (metaData.getDataType().equalsIgnoreCase(
							NEDSSConstants.NBS_QUESTION_DATATYPE_TEXT)
							&& (metaData.getMask() == null || (!metaData
									.getMask().equals(
											NEDSSConstants.NUMERIC_CODE) && !metaData
									.getMask()
									.equals(NEDSSConstants.NBS_QUESTION_DATATYPE_MASK_NUM_YYYY)))
							|| metaData
									.getDataType()
									.equalsIgnoreCase(
											NEDSSConstants.NBS_QUESTION_DATATYPE_CODED_VALUE)) {
						if (logic.equalsIgnoreCase("CT") && edxRuleManageDT.getValue()!=null) {
							// for multi-selects separated by commas
							String[] values = edxRuleManageDT.getValue().split(
									",");
							for (String value : values) {
								if (!(ob.toString().indexOf(value) >= 0)) {
									return false;
								}
							}
							return true;
						} else if (logic.equalsIgnoreCase("=")) {
							if (ob.toString().equals(edxRuleManageDT.getValue())) {
								return true;
							}
						} else if (logic.equalsIgnoreCase("!=")) {
							if (!ob.toString().equals(edxRuleManageDT.getValue())) {
								return true;
							}

						}

					} else if (metaData.getDataType().equalsIgnoreCase(
							NEDSSConstants.NBS_QUESTION_DATATYPE_DATETIME)
							|| metaData.getDataType().equalsIgnoreCase(
									NEDSSConstants.DATETIME_DATATYPE)
							|| metaData.getDataType().equalsIgnoreCase(
									NEDSSConstants.NBS_QUESTION_DATATYPE_DATE)
							|| metaData
									.getDataType()
									.equalsIgnoreCase(
											NEDSSConstants.NBS_QUESTION_DATATYPE_NUMERIC)
							|| (metaData.getMask() != null && (metaData
									.getMask().equals(
											NEDSSConstants.NUMERIC_CODE) || metaData
									.getMask()
									.equals(NEDSSConstants.NBS_QUESTION_DATATYPE_MASK_NUM_YYYY))))
							 {
						long sourceValue = 0;
						long advanceCriteria = 0;
						if (metaData.getDataType().toUpperCase().indexOf(NEDSSConstants.DATE_DATATYPE)>=0) {
							Timestamp time = (Timestamp) (ob);
							sourceValue = time.getTime();
							Timestamp adCrtTime = StringUtils.stringToStrutsTimestamp(edxRuleManageDT.getValue());
							advanceCriteria = adCrtTime.getTime();

						} else {
							if (ob != null) {
								sourceValue = Long.parseLong(ob.toString());
							} else
								sourceValue = new Long(0);
							if (edxRuleManageDT.getValue() != null)
								advanceCriteria = Long.parseLong(edxRuleManageDT.getValue());
							else
								advanceCriteria = new Long(0);
						}
						

						if (logic.equalsIgnoreCase("!=")) {
							if (sourceValue != advanceCriteria) {
								return true;
							}
						} else if (logic.equalsIgnoreCase(">")) {
							if (sourceValue > advanceCriteria) {
								return true;
							}
						} else if (logic.equalsIgnoreCase(">=")) {
							if ((sourceValue == advanceCriteria) || (sourceValue > advanceCriteria)) {
								return true;
							}
						} else if (logic.equalsIgnoreCase("<")) {
							if (sourceValue < advanceCriteria) {
								return true;
							}
						} else if (logic.equalsIgnoreCase("<=")) {
							if ((sourceValue == advanceCriteria) || (sourceValue < advanceCriteria)) {
								return true;
							}
						} else if (logic.equalsIgnoreCase("=")) {
							if (sourceValue == advanceCriteria) {
								return true;
							}
						}

					} else
						return false;

				} else {
					// return false;
				}
			}
		} catch (SecurityException e) {
			logger.error("ValidateDecisionSupport- checkNbsObject. SecurityException thrown :" + e);
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			logger.error("ValidateDecisionSupport- checkNbsObject. IllegalArgumentException thrown :" + e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("ValidateDecisionSupport- checkNbsObject. Exception thrown :" + e);
			e.printStackTrace();
		}
		return false;
	}

	public static void processPHCObject(EdxRuleManageDT edxRuleManageDT, PublicHealthCaseDT publicHealthCaseDT, NbsQuestionMetadata metaData) {
		String behavior = edxRuleManageDT.getBehavior();
		boolean isOverwrite = false;
		if (behavior.equalsIgnoreCase("1")) {
			isOverwrite = true;
		} else if (behavior.equalsIgnoreCase("2")) {
			isOverwrite = false;
		}
		String dataLocation = metaData.getDataLocation();
		/*
		 * String setMethodName = dataLocation.replaceAll("_", "");
		 * setMethodName = "SET"+ setMethodName.substring(
		 * setMethodName.indexOf(".")+1, setMethodName.length());
		 */
		getCurrentDateValue(edxRuleManageDT);
		String getMethodName = dataLocation.replaceAll("_", "");
		getMethodName = "GET" + getMethodName.substring(getMethodName.indexOf(".") + 1, getMethodName.length());

		Class<?> phcClass = publicHealthCaseDT.getClass();
		try {
			Method[] methodList = phcClass.getDeclaredMethods();
			for (int i = 0; i < methodList.length; i++) {
				Method method = (Method) methodList[i];
				if (method.getName().equalsIgnoreCase(getMethodName)) {
					String setMethodName = method.getName().replaceAll("get", "set");

					Method setMethod = null;
					if (metaData.getDataType().equalsIgnoreCase(NEDSSConstants.NBS_QUESTION_DATATYPE_TEXT) || metaData.getDataType().equalsIgnoreCase(NEDSSConstants.NBS_QUESTION_DATATYPE_CODED_VALUE)) {
						setMethod = phcClass.getMethod(setMethodName, new String().getClass());

					} else if (metaData.getDataType().equalsIgnoreCase(NEDSSConstants.NBS_QUESTION_DATATYPE_DATETIME) || metaData.getDataType().equalsIgnoreCase(NEDSSConstants.DATETIME_DATATYPE)
							|| metaData.getDataType().equalsIgnoreCase(NEDSSConstants.NBS_QUESTION_DATATYPE_DATE)) {
						setMethod = phcClass.getMethod(setMethodName, new Timestamp(0).getClass());
					} else if (metaData.getDataType().equalsIgnoreCase(NEDSSConstants.NBS_QUESTION_DATATYPE_NUMERIC)) {
						setMethod = phcClass.getMethod(setMethodName, new Integer(0).getClass());
					} else {
						logger.error("ValidateDecisionSupport.processNbsObject: There is an error, there seems to be metaData.getDataType() that is dufferent from the expected value" + metaData.toString());
					}
					Object ob = method.invoke(publicHealthCaseDT, (Object[]) null);
					if (isOverwrite) {
						setMethod(publicHealthCaseDT, setMethod, edxRuleManageDT);
					} else if (!isOverwrite && ob == null) {
						setMethod(publicHealthCaseDT, setMethod, edxRuleManageDT);
					} else {
						// do nothing
					}
				} else {
					// do nothing
				}
			}
		} catch (SecurityException e) {
			logger.error("ValidateDecisionSupport- processNbsObject. SecurityException thrown :" + e);
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			logger.error("ValidateDecisionSupport- processNbsObject. IllegalArgumentException thrown :" + e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("ValidateDecisionSupport- processNbsObject. Exception thrown :" + e);
			e.printStackTrace();
		}
		// System.out.println("solution is :"+publicHealthCaseDT.toString());
	}
	
	public static void processNbsObject(EdxRuleManageDT edxRuleManageDT, PublicHealthCaseVO publicHealthCaseVO, NbsQuestionMetadata metaData){
		PublicHealthCaseDT publicHealthCaseDT = publicHealthCaseVO.getThePublicHealthCaseDT();	
		processNBSObjectDT( edxRuleManageDT, publicHealthCaseVO, publicHealthCaseDT, metaData);
		}

	/*public static void processNbsObject(EdxRuleManageDT edxRuleManageDT, PublicHealthCaseVO publicHealthCaseVO, NbsQuestionMetadata metaData) {
		String behavior = edxRuleManageDT.getBehavior();
		boolean isOverwrite = false;
		PublicHealthCaseDT publicHealthCaseDT = publicHealthCaseVO.getThePublicHealthCaseDT();

		if (behavior.equalsIgnoreCase("1")) {
			isOverwrite = true;
		} else if (behavior.equalsIgnoreCase("2")) {
			isOverwrite = false;
		}
		String dataLocation = metaData.getDataLocation();
		

		String getMethodName = dataLocation.replaceAll("_", "");
		getMethodName = "GET" + getMethodName.substring(getMethodName.indexOf(".") + 1, getMethodName.length());

		Class<?> phcClass = publicHealthCaseDT.getClass();
		try {
			Method[] methodList = phcClass.getDeclaredMethods();
			for (int i = 0; i < methodList.length; i++) {
				Method method = (Method) methodList[i];
				if (method.getName().equalsIgnoreCase(getMethodName)) {
					String setMethodName = method.getName().replaceAll("get", "set");

					Method setMethod = null;
					if (metaData.getDataType().equalsIgnoreCase(NEDSSConstants.NBS_QUESTION_DATATYPE_TEXT) || metaData.getDataType().equalsIgnoreCase(NEDSSConstants.NBS_QUESTION_DATATYPE_CODED_VALUE)) {
						setMethod = phcClass.getMethod(setMethodName, new String().getClass());

					} else if (metaData.getDataType().equalsIgnoreCase(NEDSSConstants.NBS_QUESTION_DATATYPE_DATETIME) || metaData.getDataType().equalsIgnoreCase(NEDSSConstants.DATETIME_DATATYPE)
							|| metaData.getDataType().equalsIgnoreCase(NEDSSConstants.NBS_QUESTION_DATATYPE_DATE)) {
						setMethod = phcClass.getMethod(setMethodName, new Timestamp(0).getClass());
					} else if (metaData.getDataType().equalsIgnoreCase(NEDSSConstants.NBS_QUESTION_DATATYPE_NUMERIC)) {
						if(method.getReturnType().equals(Integer.class))
							setMethod = phcClass.getMethod(setMethodName, new Integer(0).getClass());
						else if(method.getReturnType().equals(Long.class))
							 setMethod = phcClass.getMethod(setMethodName, new Long(0).getClass());
						else if(method.getReturnType().equals(BigDecimal.class))
							 setMethod = phcClass.getMethod(setMethodName, new BigDecimal(0).getClass());						
					} else {
						logger.error("ValidateDecisionSupport.processNbsObject: There is an error, there seems to be metaData.getDataType() that is dufferent from the expected value" + metaData.toString());
					}
					Object ob = method.invoke(publicHealthCaseDT, (Object[]) null);
					if (isOverwrite) {
						setMethod(publicHealthCaseDT, setMethod, edxRuleManageDT);
					} else if (!isOverwrite && ob == null) {
						setMethod(publicHealthCaseDT, setMethod, edxRuleManageDT);
					} else {
						// do nothing
					}
				} else {
					// do nothing
				}
			}
		} catch (SecurityException e) {
			logger.error("ValidateDecisionSupport- processNbsObject. SecurityException thrown :" + e);
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			logger.error("ValidateDecisionSupport- processNbsObject. IllegalArgumentException thrown :" + e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("ValidateDecisionSupport- processNbsObject. Exception thrown :" + e);
			e.printStackTrace();
		}
		// System.out.println("solution is :"+publicHealthCaseDT.toString());
	}*/

	public static void setMethod(Object nbsObject, Method setMethod, EdxRuleManageDT edxRuleManageDT) {
		try {
			Class<?>[] parameterArray = setMethod.getParameterTypes();
			for (int j = 0; j < parameterArray.length; j++) {
				Object object = parameterArray[j];
				if (object.toString().equalsIgnoreCase("class java.math.BigDecimal")) {
					if(edxRuleManageDT.getDefaultNumericValue()!=null)
						setMethod.invoke(nbsObject, new BigDecimal(edxRuleManageDT.getDefaultNumericValue()));
					else
						setMethod.invoke(nbsObject, new BigDecimal(edxRuleManageDT.getDefaultStringValue()));
				} else if (object.toString().equalsIgnoreCase("class java.lang.String")) {
					if(edxRuleManageDT.getDefaultStringValue()!=null && !edxRuleManageDT.getDefaultStringValue().trim().equals(""))
						setMethod.invoke(nbsObject, edxRuleManageDT.getDefaultStringValue());
					else if(edxRuleManageDT.getDefaultCommentValue()!=null && !edxRuleManageDT.getDefaultCommentValue().trim().equals(""))
						setMethod.invoke(nbsObject, edxRuleManageDT.getDefaultCommentValue());
				} else if (object.toString().equalsIgnoreCase("class java.sql.Timestamp")) {
					setMethod.invoke(nbsObject, StringUtils.stringToStrutsTimestamp(edxRuleManageDT.getDefaultStringValue()));
				} else if (object.toString().equalsIgnoreCase("class java.lang.Integer")) {
					if(edxRuleManageDT.getDefaultNumericValue()!=null)
						setMethod.invoke(nbsObject, new Integer(edxRuleManageDT.getDefaultNumericValue()));
					else
						setMethod.invoke(nbsObject, new Integer(edxRuleManageDT.getDefaultStringValue()));
				} else if (object.toString().equalsIgnoreCase("class java.lang.Long")) {
					if(edxRuleManageDT.getDefaultNumericValue()!=null)
						setMethod.invoke(nbsObject, new Long(edxRuleManageDT.getDefaultNumericValue()));
					else
						setMethod.invoke(nbsObject, new Long(edxRuleManageDT.getDefaultStringValue()));
				}
			}
		} catch (IllegalArgumentException e) {
			logger.error("ValidateDecisionSupport- setMethod. IllegalArgumentException thrown :" + e);
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			logger.error("ValidateDecisionSupport- setMethod. IllegalAccessException thrown :" + e);
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			logger.error("ValidateDecisionSupport- setMethod. InvocationTargetException thrown :" + e);
			e.printStackTrace();
		}
	}
	
	private static void getCurrentDateValue(EdxRuleManageDT edxRuleManageDT) {
		if (edxRuleManageDT.getDefaultStringValue() != null
				&& edxRuleManageDT.getDefaultStringValue().equals(
						NEDSSConstants.USE_CURRENT_DATE))
			edxRuleManageDT.setDefaultStringValue(StringUtils
					.formatDate(new Timestamp((new Date()).getTime())));
	}
	/**
	 * createAlgorithmMap - Change the collection from DSMAlgorithmDT list to EdxRuleAlgorothmManagerDT list
	 * @param conditionCd
	 * @param nbsSecurityObj
	 * @return collection of EdxRuleAlgorothmManagerDT
	 */
	public static Collection<Object> createAlgorithmMap(String conditionCd, NBSSecurityObj nbsSecurityObj) {
		Collection<Object> coll = getDSMAlgorithmCollection(conditionCd, nbsSecurityObj);
		Collection<Object> returnColl = new ArrayList<Object>();
		if (coll != null && coll.size() > 0) {
			Iterator<Object> iter = coll.iterator();
			while (iter.hasNext()) {
				DSMAlgorithmDT dSMAlgorithmDT = (DSMAlgorithmDT) iter.next();
				try {
					EdxRuleAlgorothmManagerDT edxRuleAlgorothmManagerDT = parseDSMAlgorithmDT(dSMAlgorithmDT);
					returnColl.add(edxRuleAlgorothmManagerDT);
				} catch (Exception e) {
					logger.error("ValidateDecisionSupport.createAlgorithmMap failure for condition_code:" + conditionCd);
				}
			}

		}
		return returnColl;
	}
	
	
	/**
	 * Change the collection from DSMAlgorithmDT list to EdxRuleAlgorothmManagerDT list
	 * @param algolColl - Existing collection of DSMAlgorithmDT for the condition
	 * @param nbsSecurityObj
	 * @return collection of EdxRuleAlgorothmManagerDT
	 */
	public static Collection<Object> createAlgorithmMap(Collection<Object> algolColl, NBSSecurityObj nbsSecurityObj) {
		//Collection<Object> coll = getDSMAlgorithmCollection(conditionCd, nbsSecurityObj);
		Collection<Object> returnColl = new ArrayList<Object>();
		if (algolColl != null && algolColl.size() > 0) {
			Iterator<Object> iter = algolColl.iterator();
			while (iter.hasNext()) {
				DSMAlgorithmDT dSMAlgorithmDT = (DSMAlgorithmDT) iter.next();
				try {
					EdxRuleAlgorothmManagerDT edxRuleAlgorothmManagerDT = parseDSMAlgorithmDT(dSMAlgorithmDT);
					returnColl.add(edxRuleAlgorothmManagerDT);
				} catch (Exception e) {
					logger.error("ValidateDecisionSupport.createAlgorithmMap failure for algorithm collection:" + algolColl.size());
				}
			}
		}
		return returnColl;
	}
	public static AlgorithmDocument parseAlgorithmDocumentTypeXml(String xmlPayLoadContent) throws Exception {
		// Create an instance of a type generated from schema to hold the XML.
		AlgorithmDocument algorithmDocument = null;
		try {
			// Set up the validation error listener.
			ArrayList<Object> validationErrors = new ArrayList<Object>();
			XmlOptions validationOptions = new XmlOptions();
			validationOptions.setErrorListener(validationErrors);

			// Parse the instance into the type generated from the schema.
			algorithmDocument = AlgorithmDocument.Factory.parse(xmlPayLoadContent);

			// During validation, errors are added to the ArrayList<Object> for
			// retrieval and printing by the printErrors method.
			boolean isValid = algorithmDocument.validate(validationOptions);

			// Print the errors if the XML is invalid.
			if (!isValid) {
				Iterator<Object> iter = validationErrors.iterator();
				StringBuffer buff = new StringBuffer();
				buff.append("Inbound message has failed in ValidateDecisionSupport.parseAlgorithmDocumentTypeXml. XML parsing failed ");
				while (iter.hasNext()) {
					buff.append(iter.next() + "\n");
				}
				logger.error(buff.toString());
				logger.error("ValidateDecisionSupport- parseAlgorithmDocumentTypeXml. NEDSSSystemException thrown :" + buff.toString());
				throw new Exception(buff.toString());
			}
		} catch (Exception e) {
			logger.error("ValidateDecisionSupport- parseAlgorithmDocumentTypeXml. NEDSSSystemException thrown :" + e);
			logger.error(e.toString());
			e.printStackTrace();
			throw new Exception(e);
		}

		logger.debug("Received XML:\n" + algorithmDocument.toString());
		return algorithmDocument;
	}

	private static Collection<Object> getDSMAlgorithmCollection(String conditionCd, NBSSecurityObj nbsSecurityObj) {
		Collection<Object> coll = new ArrayList<Object>();
		try {
			NedssUtils nu = new NedssUtils();
			Object obj = nu.lookupBean(JNDINames.DSMAlgorithmEJB);
			DSMAlgorithmHome DSMAlgorithmHome = (DSMAlgorithmHome) javax.rmi.PortableRemoteObject.narrow(obj, DSMAlgorithmHome.class);
			DSMAlgorithm dSMAlgorithm = DSMAlgorithmHome.create();
			coll = dSMAlgorithm.selectDSMAlgorithmForCondition(conditionCd, nbsSecurityObj);
		} catch (NEDSSSystemException e) {
			logger.error("ValidateDecisionSupport- getDSMAlgorithmCollection. NEDSSSystemException thrown :" + e);
			e.printStackTrace();
		} catch (ClassCastException e) {
			logger.error("ValidateDecisionSupport- getDSMAlgorithmCollection. ClassCastException thrown :" + e);
			e.printStackTrace();
		} catch (RemoteException e) {
			logger.error("ValidateDecisionSupport- getDSMAlgorithmCollection. RemoteException thrown :" + e);
			e.printStackTrace();
		} catch (EJBException e) {
			logger.error("ValidateDecisionSupport- getDSMAlgorithmCollection. EJBException thrown :" + e);
			e.printStackTrace();
		} catch (CreateException e) {
			logger.error("ValidateDecisionSupport- getDSMAlgorithmCollection. CreateException thrown :" + e);
			e.printStackTrace();
		}
		return coll;
	}

	private static EdxRuleAlgorothmManagerDT parseDSMAlgorithmDT(DSMAlgorithmDT dSMAlgorithmDT) throws Exception {

		EdxRuleAlgorothmManagerDT edxRuleAlgorothmManagerDT = new EdxRuleAlgorothmManagerDT();
		edxRuleAlgorothmManagerDT.setLastChgTime(dSMAlgorithmDT.getLastChgTime());
		edxRuleAlgorothmManagerDT.setDsmAlgorithmUid(dSMAlgorithmDT.getDsmAlgorithmUid());
		String payloadContent = dSMAlgorithmDT.getAlgorithmPayload();
		AlgorithmDocument algorithmDocument = parseAlgorithmDocumentTypeXml(payloadContent);
		Map<Object, Object> advanceCriteriaMap = new HashMap<Object, Object>();
		Map<Object, Object> applicationMap = new HashMap<Object, Object>();
		Algorithm algorithm = algorithmDocument.getAlgorithm();
		edxRuleAlgorothmManagerDT.setDsmAlgorithmName(algorithm.getAlgorithmName());
		if (algorithm.getApplyToSendingSystems() != null && algorithm.getApplyToSendingSystems().getSendingSystemArray() != null) {
			CodedType[] codedArray = algorithm.getApplyToSendingSystems().getSendingSystemArray();

			if (codedArray != null && codedArray.length > 0) {
				Collection<Object> coll = new ArrayList<Object>();
				for (int j = 0; j < codedArray.length; j++) {
					ExportReceivingFacilityDT exportReceivingFacilityDT = new ExportReceivingFacilityDT();
					CodedType codedType = codedArray[j];
					exportReceivingFacilityDT.setReceivingSystemDescTxt(codedType.getCode());
					exportReceivingFacilityDT.setReceivingSystemOid(codedType.getCodeSystemCode());
					coll.add(exportReceivingFacilityDT);
				}
				edxRuleAlgorothmManagerDT.setSendingFacilityColl(coll);
			}
		}
		/**
		 * Disabled for this iteration
		 * 
		 * Collection<Object> collAppyTo=new ArrayList<Object>();
		 * 
		 * if(dSMAlgorithmDT.getApplyTo().indexOf("^")>0){ collAppyTo=
		 * splitString(dSMAlgorithmDT.getApplyTo()); }else{ String applyTo =
		 * dSMAlgorithmDT.getApplyTo(); collAppyTo.add(applyTo); }
		 * 
		 * Collection<Object> collReportingSystemList=new ArrayList<Object>();
		 * if(dSMAlgorithmDT.getReportingSystemList().indexOf("^")>0){
		 * collReportingSystemList=
		 * splitString(dSMAlgorithmDT.getReportingSystemList()); }else{ String
		 * reportingSystemList = dSMAlgorithmDT.getReportingSystemList();
		 * collReportingSystemList.add(reportingSystemList); }
		 * 
		 * Collection<Object> collFrequency=new ArrayList<Object>();
		 * if(dSMAlgorithmDT.getFrequency().indexOf("^")>0){ collFrequency=
		 * splitString(dSMAlgorithmDT.getFrequency()); }else{ String frequency =
		 * dSMAlgorithmDT.getFrequency(); collFrequency.add(frequency); }
		 */
		/*
		 * if(dSMAlgorithmDT.getConditionList().indexOf("^")>0){ map=
		 * splitConditionString(map,
		 * dSMAlgorithmDT.getAlgorithmNm(),dSMAlgorithmDT.getConditionList());
		 * }else{ String conditionCode = dSMAlgorithmDT.getConditionList();
		 * map.put(dSMAlgorithmDT.getAlgorithmNm()+"^"+conditionCode+"^",
		 * conditionCode); }
		 */

		AdvancedCriteriaType advanceCriteriaType = algorithm.getAdvancedCriteria();
		if (advanceCriteriaType != null) {
			for (int i = 0; i < advanceCriteriaType.getCriteriaArray().length; i++) {
				CriteriaType criteriaType = advanceCriteriaType.getCriteriaArray()[i];
				CodedType criteriaQuestionType = criteriaType.getCriteriaQuestion();
				CodedType criteriaLogicType = criteriaType.getCriteriaLogic();

				if (criteriaType.getCriteriaStringValue() == null && criteriaType.getCriteriaCodedValueArray().length > 0) {
					Collection<Object> coll = new ArrayList<Object>();
					for (int j = 0; j < criteriaType.getCriteriaCodedValueArray().length; j++) {
						EdxRuleManageDT edxRuleManageDT = new EdxRuleManageDT();
						edxRuleManageDT.setDsmAlgorithmUid(dSMAlgorithmDT.getDsmAlgorithmUid());
						edxRuleManageDT.setQuestionId(criteriaQuestionType.getCode());
						edxRuleManageDT.setLogic(criteriaLogicType.getCode());
						edxRuleManageDT.setAdvanceCriteria(true);
						edxRuleManageDT.setValue(criteriaType.getCriteriaCodedValueArray()[j].getCode());
						coll.add(edxRuleManageDT);
					}
					advanceCriteriaMap.put(criteriaQuestionType.getCode(), coll);
				} else {
					EdxRuleManageDT edxRuleManageDT = new EdxRuleManageDT();
					edxRuleManageDT.setDsmAlgorithmUid(dSMAlgorithmDT.getDsmAlgorithmUid());
					edxRuleManageDT.setQuestionId(criteriaQuestionType.getCode());
					edxRuleManageDT.setLogic(criteriaLogicType.getCode());
					edxRuleManageDT.setAdvanceCriteria(true);
					edxRuleManageDT.setValue(criteriaType.getCriteriaStringValue());
					advanceCriteriaMap.put(criteriaQuestionType.getCode(), edxRuleManageDT);
				}
			}
		}

		if (advanceCriteriaMap != null && advanceCriteriaMap.size() > 0)
			edxRuleAlgorothmManagerDT.setEdxRuleAdvCriteriaDTMap(advanceCriteriaMap);

		ActionType actionType = algorithm.getAction();
		if (actionType.getCreateInvestigation() != null) {
			CreateInvestigationType specificActionType = actionType.getCreateInvestigation();
			edxRuleAlgorothmManagerDT.setAction(DecisionSupportConstants.CREATE_INVESTIGATION_VALUE);
			// edxRuleAlgorothmManagerDT.setAction(actionType.getCreateInvestigation().getUpdateAction().getCode());
			specificActionType.getAlert();
			InvestigationDefaultValuesType investigationDefaultValuesType = specificActionType.getInvestigationDefaultValues();
			if (investigationDefaultValuesType != null)
				parseInvestigationDefaultValuesType(applicationMap, investigationDefaultValuesType);

			CodedType failureToCreateType = specificActionType.getOnFailureToCreateInvestigation();
			edxRuleAlgorothmManagerDT.setOnFailureToCreateInv(failureToCreateType.getCode());
			if (specificActionType.getUpdateAction() != null)
				edxRuleAlgorothmManagerDT.setUpdateAction(specificActionType.getUpdateAction().getCode());
		} else if (actionType.getCreateInvestigationWithNND() != null) {
			CreateInvestigationWithNNDType specificActionType = actionType.getCreateInvestigationWithNND();
			specificActionType.getAlert();
			InvestigationDefaultValuesType investigationDefaultValuesType = specificActionType.getInvestigationDefaultValues();
			edxRuleAlgorothmManagerDT.setAction(DecisionSupportConstants.CREATE_INVESTIGATION_WITH_NND_VALUE);
			// edxRuleAlgorothmManagerDT.setAction(actionType.getCreateInvestigationWithNND().getUpdateAction().getCode());
			if (investigationDefaultValuesType != null)
				parseInvestigationDefaultValuesType(applicationMap, investigationDefaultValuesType);
			CodedType failureToCreateType = specificActionType.getOnFailureToCreateInvestigation();
			edxRuleAlgorothmManagerDT.setOnFailureToCreateInv(failureToCreateType.getCode());
			if (specificActionType.getUpdateAction() != null)
				edxRuleAlgorothmManagerDT.setUpdateAction(specificActionType.getUpdateAction().getCode());
			edxRuleAlgorothmManagerDT.setNndComment(specificActionType.getNNDComment());
			if (specificActionType.getOnFailureToCreateNND() != null)
				edxRuleAlgorothmManagerDT.setOnFailureToCreateNND(specificActionType.getOnFailureToCreateNND().getCode());

		} else if (actionType.getDeleteDocument() != null) {
			// edxRuleAlgorothmManagerDT.setAction(PURGE_INV_RETAIN_EV);
			DeleteDocumentType specificActionType = actionType.getDeleteDocument();
			specificActionType.getAlert();
			specificActionType.getComment();
			specificActionType.getReasonForDeletion();
		}
		if (applicationMap != null && applicationMap.size() > 0)
			edxRuleAlgorothmManagerDT.setEdxRuleApplyDTMap(applicationMap);
		return edxRuleAlgorothmManagerDT;
	}

	public static void parseInvestigationDefaultValuesType(Map<Object, Object> map, InvestigationDefaultValuesType investigationDefaultValuesType) {
		DefaultValueType[] defaultValueTypeArray = investigationDefaultValuesType.getDefaultValueArray();
		for (int i = 0; i < defaultValueTypeArray.length; i++) {
			DefaultValueType defaultValueType = defaultValueTypeArray[i];
			CodedType defaultQuestion = defaultValueType.getDefaultQuestion();

			EdxRuleManageDT edxRuleManageDT = new EdxRuleManageDT();
			edxRuleManageDT.setQuestionId(defaultQuestion.getCode());

			if (defaultValueType.getDefaultBehavior() != null) {
				CodedType defaultBehaviorType = defaultValueType.getDefaultBehavior();
				edxRuleManageDT.setBehavior(defaultBehaviorType.getCode());
			}

			CodedType[] defaultCodedValueArray = defaultValueType.getDefaultCodedValueArray();
			if(defaultValueType.getDefaultParticipation()!=null){
				try{
				edxRuleManageDT.setParticipationTypeCode(defaultValueType.getDefaultParticipation().getParticipationType().getCode());
				edxRuleManageDT.setParticipationUid(new Long(defaultValueType.getDefaultParticipation().getEntityUid()));
				edxRuleManageDT.setParticipationClassCode(defaultValueType.getDefaultParticipation().getEntityClass());
				}catch(Exception e){
					logger.error("The defaultValueType exception is not valid for code and/or uid and/or classCode. Please check: ", defaultValueType);
				}
			}else if (defaultValueType.getDefaultStringValue() != null) {
				edxRuleManageDT.setDefaultStringValue(defaultValueType.getDefaultStringValue());

			}else if (defaultValueType.getDefaultCommentValue() != null) {
				edxRuleManageDT.setDefaultCommentValue(defaultValueType.getDefaultCommentValue());
			}else if (defaultCodedValueArray != null) {
				Collection<Object> toValueColl = new ArrayList<Object>();
				for (int j = 0; j < defaultCodedValueArray.length; j++) {
					CodedType codedType = defaultCodedValueArray[j];
					toValueColl.add(codedType.getCode());
				}
				edxRuleManageDT.setDefaultCodedValueColl(toValueColl);
			} else if (defaultValueType.getDefaultNumericValue() != null) {
				defaultValueType.getDefaultNumericValue().getValue1();
				edxRuleManageDT.setDefaultNumericValue(defaultValueType.getDefaultNumericValue().getValue1() + "");
			}
			map.put(edxRuleManageDT.getQuestionId(), edxRuleManageDT);
		}
	}

	/**
	 * Case Management is contains fields only used by STD/HIV cases.
	 * @param edxRuleManageDT
	 * @param publicHealthCaseVO
	 * @param metaData
	 */
	public static void processNBSCaseManagementDT(EdxRuleManageDT edxRuleManageDT, PublicHealthCaseVO publicHealthCaseVO, NbsQuestionMetadata metaData){
		if (publicHealthCaseVO.getTheCaseManagementDT() != null) {
			CaseManagementDT caseManagementDT = publicHealthCaseVO.getTheCaseManagementDT();	
			caseManagementDT.setCaseManagementDTPopulated(true);
			processNBSObjectDT( edxRuleManageDT, publicHealthCaseVO, caseManagementDT, metaData);
		} else logger.error("********Decision Support Setting Case Management value for non-STD/HIV Case?? Check STD_PROGRAM_AREAS setting in Property file*********");
	}
	public static void processNBSObjectDT(EdxRuleManageDT edxRuleManageDT, PublicHealthCaseVO publicHealthCaseVO, Object object, NbsQuestionMetadata metaData) {
				String behavior = edxRuleManageDT.getBehavior();
		boolean isOverwrite = false;
		
		if (behavior.equalsIgnoreCase("1")) {
			isOverwrite = true;
		} else if (behavior.equalsIgnoreCase("2")) {
			isOverwrite = false;
		}
		String dataLocation = metaData.getDataLocation();
		/*
		 * String setMethodName = dataLocation.replaceAll("_", "");
		 * setMethodName = "SET"+ setMethodName.substring(
		 * setMethodName.indexOf(".")+1, setMethodName.length());
		 */
		
		String getMethodName = dataLocation.replaceAll("_", "");
		getMethodName = "GET" + getMethodName.substring(getMethodName.indexOf(".") + 1, getMethodName.length());

		Class<?> phcClass = object.getClass();
		try {
			Method[] methodList = phcClass.getDeclaredMethods();
			for (int i = 0; i < methodList.length; i++) {
				Method method = (Method) methodList[i];
				if (method.getName().equalsIgnoreCase(getMethodName)) {
					String setMethodName = method.getName().replaceAll("get", "set");

					Method setMethod = null;
					if (metaData.getDataType().equalsIgnoreCase(NEDSSConstants.NBS_QUESTION_DATATYPE_TEXT) || metaData.getDataType().equalsIgnoreCase(NEDSSConstants.NBS_QUESTION_DATATYPE_CODED_VALUE)) {
						setMethod = phcClass.getMethod(setMethodName, new String().getClass());

					} else if (metaData.getDataType().equalsIgnoreCase(NEDSSConstants.NBS_QUESTION_DATATYPE_DATETIME) || metaData.getDataType().equalsIgnoreCase(NEDSSConstants.DATETIME_DATATYPE)
							|| metaData.getDataType().equalsIgnoreCase(NEDSSConstants.NBS_QUESTION_DATATYPE_DATE)) {
						getCurrentDateValue(edxRuleManageDT); 
						setMethod = phcClass.getMethod(setMethodName, new Timestamp(0).getClass());
					} else if (metaData.getDataType().equalsIgnoreCase(NEDSSConstants.NBS_QUESTION_DATATYPE_NUMERIC)) {
						if(method.getReturnType().equals(Integer.class))
							setMethod = phcClass.getMethod(setMethodName, new Integer(0).getClass());
						else if(method.getReturnType().equals(Long.class))
							 setMethod = phcClass.getMethod(setMethodName, new Long(0).getClass());
						else if(method.getReturnType().equals(BigDecimal.class))
							 setMethod = phcClass.getMethod(setMethodName, new BigDecimal(0).getClass());
						else if(method.getReturnType().equals(String.class)) // Added because question INV139's datatype is NUMERIC in nbs_ui_metadata table but the datatype is varchar in Public_Health_Case table.
							 setMethod = phcClass.getMethod(setMethodName, new String().getClass());
					} else {
						logger.error("ValidateDecisionSupport.processNbsObject: There is an error, there seems to be metaData.getDataType() that is dufferent from the expected value" + metaData.toString());
					}
					Object ob = method.invoke(object, (Object[]) null);
					if (isOverwrite) {
						setMethod(object, setMethod, edxRuleManageDT);
					} else if (!isOverwrite && ob == null) {
						setMethod(object, setMethod, edxRuleManageDT);
					} else {
						// do nothing
					}
				} else {
					// do nothing
				}
			}
		} catch (SecurityException e) {
			logger.error("ValidateDecisionSupport- processNbsObject. SecurityException thrown :" + e);
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			logger.error("ValidateDecisionSupport- processNbsObject. IllegalArgumentException thrown :" + e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("ValidateDecisionSupport- processNbsObject. Exception thrown :" + e);
			e.printStackTrace();
		}
		// System.out.println("solution is :"+publicHealthCaseDT.toString());
	}
}