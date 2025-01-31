package gov.cdc.nedss.page.ejb.pageproxyejb.util;

import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import gov.cdc.nedss.act.ctcontact.dt.CTContactAnswerDT;
import gov.cdc.nedss.act.publichealthcase.vo.PublicHealthCaseVO;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.nbsactentity.dt.NbsActEntityDT;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.act.PageActProxyVO;
import gov.cdc.nedss.pam.act.NbsCaseAnswerDT;
import gov.cdc.nedss.pam.vo.PamVO;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.dao.RetrieveSummaryVO;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.CoinfectionSummaryVO;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.vo.CTContactProxyVO;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.dao.NbsQuestionDAOImpl;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.dynamicbinding.DynamicBeanBinding;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;


/**
 * AssociatedInvestigationUpdateUtil class is a special Util class to handle co-infection Investigations, merge Lots for STD/HIV(As of Release 4.5)
 * This class needs to be expanded, reworked to include any future functionality where other associated investigation update will be required in future(After release 4.5)
 * @author Pradeep Kumar Sharma
 * @since
  */
public class AssociatedInvestigationUpdateUtil {
	static final LogUtils logger = new LogUtils(AssociatedInvestigationUpdateUtil.class.getName());
	public int versionControlNumber = 1;
	
	
	/**
	 * @param pageActProxyVO: PageActProxyVO that will update the other investigations that are part of co-infection group
	 * @param mprUid: MPR UId for the cases tied to co-infection group
	 * @param currentPhclUid: PHC_UID tied to pageActProxyVO
	 * @param nbsSecurityObj
	 * @throws RemoteException
	 * 
	 */
	public void updatForConInfectionId(PageActProxyVO pageActProxyVO,Long mprUid, Long currentPhclUid, NBSSecurityObj nbsSecurityObj) throws RemoteException {
		try{
			updateForConInfectionId(pageActProxyVO, null, mprUid,  null, currentPhclUid, null, null, nbsSecurityObj);
		}catch (Exception ex) {
			logger.fatal("CoInfectionUtil.updateCoInfectionInvestigations:-Exception There is an error while updateCoInfectionInvestigations:"+ex.getMessage(), ex);
			throw new java.rmi.RemoteException(ex.toString());
		}
	}

	/**
	 * @param pageActProxyVO:  PageActProxyVO that will update the other investigations that are part of co-infection group
	 * @param mprUid: MPR UId for the cases tied to co-infection group
	 * @param currentPhclUid: PHC_UID tied to pageActProxyVO
	 * @param coinfectionSummaryVOCollection - Used for Merge Investigation
	 * @param coinfectionIdToUpdate - coinfectionId Used for Merge Investigation
	 * @param nbsSecurityObj
	 * @throws RemoteException
	 * @throws CreateException 
	 */
	public void updateForConInfectionId(PageActProxyVO pageActProxyVO,PageActProxyVO supersededProxyVO,Long mprUid, Map<Object, Object> coInSupersededEpliLinkIdMap, Long currentPhclUid, Collection<Object> coinfectionSummaryVOCollection, String coinfectionIdToUpdate, NBSSecurityObj nbsSecurityObj) throws RemoteException, CreateException {
		try {
			RetrieveSummaryVO retrieveSummaryVO =  new RetrieveSummaryVO();
			String coninfectionId=pageActProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCoinfectionId();
			if(coinfectionSummaryVOCollection==null)
				coinfectionSummaryVOCollection = retrieveSummaryVO.getInvListForCoInfectionId(mprUid,coninfectionId);
			
			PageActProxyVO pageActProxyCopyVO = (PageActProxyVO)pageActProxyVO.deepCopy();
			Map<Object, Object> answermapMap =pageActProxyCopyVO.getPageVO().getPamAnswerDTMap();
			
			Map<Object, Object> repeatingAnswermapMap =pageActProxyCopyVO.getPageVO().getPageRepeatingAnswerDTMap();
			
			if (pageActProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().isStdOpenedFromClosed() && coinfectionSummaryVOCollection!=null && coinfectionSummaryVOCollection.size()>0) {
				//Incase of investigation opened from closed, read coinfection investigation's PageActProxyVO and read answerMap and RepeatingAnswerMap from it to update triggering investigation (which one is Opened from Closed)
				CoinfectionSummaryVO coninfectionSummaryVO1= null;
				Iterator<Object> coinfectionsummIterator1 =coinfectionSummaryVOCollection.iterator();
				while(coinfectionsummIterator1.hasNext()) { 
					coninfectionSummaryVO1= (CoinfectionSummaryVO)coinfectionsummIterator1.next();
					if(coninfectionSummaryVO1.getPublicHealthCaseUid().compareTo(currentPhclUid)!=0){	
						break;
					}
				}
				
				PageCaseUtil pageCaseUtil =  new PageCaseUtil();
				PageActProxyVO proxyVO = (PageActProxyVO) pageCaseUtil.getPageProxyVO(NEDSSConstants.CASE, coninfectionSummaryVO1.getPublicHealthCaseUid(), nbsSecurityObj);

				pageActProxyCopyVO = (PageActProxyVO)proxyVO.deepCopy();
				answermapMap = pageActProxyCopyVO.getPageVO().getPamAnswerDTMap();
				
				repeatingAnswermapMap = pageActProxyCopyVO.getPageVO().getPageRepeatingAnswerDTMap();
			}
			
			
			
			String investigationFormCd = CachedDropDowns.getConditionCdAndInvFormCd().get(pageActProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd()).toString();
			
			NbsQuestionDAOImpl nbsQuestionDAOImpl = new NbsQuestionDAOImpl();
			Map<Object, Object> mapFromQuestions = new HashMap<Object,Object>();
			Collection<Object> nbsQuestionUidCollection =nbsQuestionDAOImpl.getCoinfectionQuestionListForFormCd(investigationFormCd);
			Map<Object,Object> updatedValuesMap = new HashMap<Object, Object>();
			
			Map<Object,Object> updateValueInOtherTablesMap = new HashMap<Object, Object>(); // Map is to update values in other table then NBS_CASE_Answer
			
			if(nbsQuestionUidCollection!=null) {
				Iterator<Object> iterator = nbsQuestionUidCollection.iterator();
				while(iterator.hasNext()) {
					DropDownCodeDT dropDownCodeDT= (DropDownCodeDT)iterator.next();
					mapFromQuestions.put(dropDownCodeDT.getKey(), dropDownCodeDT);
					
					if(dropDownCodeDT.getAltValue()!=null && (dropDownCodeDT.getAltValue().contains("CASE_MANAGEMENT.") 
							|| dropDownCodeDT.getAltValue().contains("PERSON.") 
							|| dropDownCodeDT.getAltValue().contains("PUBLIC_HEALTH_CASE."))){
						updateValueInOtherTablesMap.put(dropDownCodeDT.getKey(), dropDownCodeDT.getAltValue());
					}else {
						if(answermapMap.get(dropDownCodeDT.getKey())!=null) {
							updatedValuesMap.put(dropDownCodeDT.getKey(), answermapMap.get(dropDownCodeDT.getKey()));
						} else if(answermapMap.get(dropDownCodeDT.getLongKey())!=null) {
							updatedValuesMap.put(dropDownCodeDT.getKey(), answermapMap.get(dropDownCodeDT.getLongKey()));
						} else if((repeatingAnswermapMap.get(dropDownCodeDT.getLongKey()+"")!=null || repeatingAnswermapMap.get(dropDownCodeDT.getLongKey())!=null)
								&& updatedValuesMap.get(dropDownCodeDT.getLongKey())==null){
							ArrayList list = (ArrayList)repeatingAnswermapMap.get(dropDownCodeDT.getLongKey().toString());
							if(list == null)
								list = (ArrayList)repeatingAnswermapMap.get(dropDownCodeDT.getLongKey());
							
							if(list!=null && list.size()>0)
								updatedValuesMap.put(dropDownCodeDT.getKey(), list);
						}
						else {
							//if(dropDownCodeDT.getIntValue()==null) {
								dropDownCodeDT.setValue( NEDSSConstants.NO_BATCH_ENTRY);
								updatedValuesMap.put(dropDownCodeDT.getKey(), dropDownCodeDT);
						//	}else {
						//		updatedValuesMap.put(dropDownCodeDT.getKey(), dropDownCodeDT.getIntValue());
						//	}
						}
					}
				}
				
			}
			if(coinfectionSummaryVOCollection!=null && coinfectionSummaryVOCollection.size()>0) {
				Iterator<Object> coinfectionsummIterator =coinfectionSummaryVOCollection.iterator();
				while(coinfectionsummIterator.hasNext()) { 
					CoinfectionSummaryVO coninfectionSummaryVO= (CoinfectionSummaryVO)coinfectionsummIterator.next();
					if(coninfectionSummaryVO.getPublicHealthCaseUid().compareTo(currentPhclUid)!=0 && !pageActProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().isStdOpenedFromClosed()){	
						if(coinfectionIdToUpdate!=null){//Merge Case investigation scenario
							updateCoInfectionInvest(updatedValuesMap, mapFromQuestions,pageActProxyVO, pageActProxyVO.getPublicHealthCaseVO(), supersededProxyVO.getPublicHealthCaseVO(), coInSupersededEpliLinkIdMap, coninfectionSummaryVO, coinfectionIdToUpdate, updateValueInOtherTablesMap, nbsSecurityObj);
														/**Update for closed/open cases that are part of any co-infection groups */
							}
									
						else{
								updateCoInfectionInvest(updatedValuesMap, mapFromQuestions,pageActProxyVO,  pageActProxyVO.getPublicHealthCaseVO(), null, null, coninfectionSummaryVO, null, updateValueInOtherTablesMap, nbsSecurityObj);
						}
					} else if (pageActProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().isStdOpenedFromClosed()) {
						//In case of investigation opened from closed, read coinfection investigation's PageActProxyVO and pass it to updateCoInfectionInvest
						CoinfectionSummaryVO coninfectionSummaryVO1= null;
						Iterator<Object> coinfectionsummIterator1 =coinfectionSummaryVOCollection.iterator();
						while(coinfectionsummIterator1.hasNext()) { 
							coninfectionSummaryVO1= (CoinfectionSummaryVO)coinfectionsummIterator1.next();
							if(coninfectionSummaryVO1.getPublicHealthCaseUid().compareTo(currentPhclUid)!=0){	
								break;
							}
						}
						

						updateCoInfectionInvest(updatedValuesMap, mapFromQuestions, pageActProxyVO, pageActProxyVO.getPublicHealthCaseVO(), null, null, coninfectionSummaryVO1, null, updateValueInOtherTablesMap, nbsSecurityObj);
						break;
					}
				}
			} else if(pageActProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().isStdOpenedFromClosed() && (coinfectionSummaryVOCollection==null || coinfectionSummaryVOCollection.size()==0)){
				PageCaseUtil pageCaseUtil =  new PageCaseUtil();
				pageActProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().setStdOpenedFromClosed(false);
				pageCaseUtil.setPageActProxyVO( pageActProxyVO, false, nbsSecurityObj);
			}
		} catch (NEDSSAppException | ClassNotFoundException | CloneNotSupportedException | IOException e) {
			String errorMessage="CoInfectionUtil.updateCoInfectionInvestigations:-Exception There is an error while updateCoInfectionInvestigations:"+e;
			logger.fatal(errorMessage, e);
			throw new java.rmi.RemoteException(e.toString());
		}
	}
		
	/*private void swapCoinfectionForUpdate() {
		try {
			PageCaseUtil pageCaseUtil =  new PageCaseUtil();
			PageActProxyVO proxyVO = (PageActProxyVO) pageCaseUtil.getPageProxyVO(NEDSSConstants.CASE, publicHealthCaseUid, nbsSecurityObj);
		} catch (NEDSSAppException | ClassNotFoundException | CloneNotSupportedException | IOException e) {
			logger.fatal("swapCoinfectionForUpdate:"+e.getMessage(), e);
			throw new java.rmi.RemoteException(e.toString());
		}
	}*/
	
	@SuppressWarnings("unchecked")
	private  void updateCoInfectionInvest(Map<Object, Object> mappedCoInfectionQuestions,Map<Object, Object>  fromMapQuestions, PageActProxyVO pageActProxyVO ,PublicHealthCaseVO publicHealthCaseVO,PublicHealthCaseVO supersededPublicHealthCaseVO, Map<Object, Object> coInSupersededEpliLinkIdMap, CoinfectionSummaryVO coninfectionSummaryVO, String coinfectionIdToUpdate, Map<Object, Object> updateValueInOtherTablesMap, NBSSecurityObj nbsSecurityObj) throws RemoteException {
		Long publicHealthCaseUid =null;
		try {
			String investigationFormCd = CachedDropDowns.getConditionCdAndInvFormCd().get(coninfectionSummaryVO.getConditionCd()).toString();
			if (pageActProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().isStdOpenedFromClosed()) {
				investigationFormCd = CachedDropDowns.getConditionCdAndInvFormCd().get(pageActProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd()).toString();
			}
			NbsQuestionDAOImpl nbsQuestionDAOImpl = new NbsQuestionDAOImpl();
			Collection<Object> toNbsQuestionUidCollection =nbsQuestionDAOImpl.getCoinfectionQuestionListForFormCd(investigationFormCd);
			publicHealthCaseUid=coninfectionSummaryVO.getPublicHealthCaseUid();
	    	java.util.Date dateTime = new java.util.Date();
			Timestamp lastChgTime = new Timestamp(dateTime.getTime());
			Long lastChgUserId= new Long(nbsSecurityObj.getEntryID());
			//PageProxy pageProxy=getPageProxyEJBEJBRemoteInterface();
			//PageActProxyVO proxyVO = (PageActProxyVO)pageProxy.getPageProxyVO(NEDSSConstants.CASE, publicHealthCaseUid, nbsSecurityObj);
			 PageCaseUtil pageCaseUtil =  new PageCaseUtil();
	    	   //pageCaseUtil.getPageProxyVO(typeCd, publicHealthCaseUID, nbsSecurityObj)
			 PageActProxyVO proxyVO = null;
			 PageActProxyVO coinfectionProxyVO = null;
			 if (pageActProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().isStdOpenedFromClosed()) {
				 //pageActProxyVO available, but reading it back so that it gets the answerDTMap with key as long value (nbs_question_uid) (Note: during Edit/Submit investigation from UI, sets the key as quetion_identifier (String) in nbsAnswerMap).
				 proxyVO = (PageActProxyVO) pageCaseUtil.getPageProxyVO(NEDSSConstants.CASE, pageActProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid(), nbsSecurityObj); //pageActProxyVO;
				 coinfectionProxyVO = (PageActProxyVO) pageCaseUtil.getPageProxyVO(NEDSSConstants.CASE, coninfectionSummaryVO.getPublicHealthCaseUid(), nbsSecurityObj);
				 proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().setInvestigationStatusCd(pageActProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getInvestigationStatusCd());
			 } else {
				 proxyVO = (PageActProxyVO) pageCaseUtil.getPageProxyVO(NEDSSConstants.CASE, publicHealthCaseUid, nbsSecurityObj);
			 }
			 
			if (!proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getInvestigationStatusCd().equalsIgnoreCase(NEDSSConstants.STATUS_OPEN)){
				///proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().setC
			}
			else{
				PamVO pageVO = proxyVO.getPageVO();
				if(pageVO.getPamAnswerDTMap()!=null && toNbsQuestionUidCollection!=null) {
					Iterator<Object> nbsQuestionIterator = toNbsQuestionUidCollection.iterator();
					String currentToQuestionKey = "";
					while(nbsQuestionIterator.hasNext( )) {
						try {
							DropDownCodeDT toDropDownCodeDT= (DropDownCodeDT)nbsQuestionIterator.next();
							currentToQuestionKey = toDropDownCodeDT.getKey();
							if(fromMapQuestions.get(toDropDownCodeDT.getKey())==null){
								logger.warn("TO Metadata question details: question_identifier:-"+ toDropDownCodeDT.getKey()+ "\nwith question_group_seq_nbr:-"+toDropDownCodeDT.getIntValue()+ "\nwith nbs_question_uid:-"+toDropDownCodeDT.getLongKey());
								logger.warn("AssociatedInvestigationUpdateUtil.updateCoInfectionInvest:coinfection investigation form code for the coinfection case is :"+investigationFormCd);
								logger.warn("AssociatedInvestigationUpdateUtil.updateCoInfectionInvest:The mapped question is missing in the current investigation" );
							
								continue;
							}else {
								DropDownCodeDT fromDropDownCodeDT = (DropDownCodeDT)fromMapQuestions.get(toDropDownCodeDT.getKey());
								/*if(fromDropDownCodeDT.getIntValue()!=null && toDropDownCodeDT.getIntValue()==null){
									logger.warn("TO Metadata question details: question_identifier:-"+ toDropDownCodeDT.getKey()+ "\nwith question_group_seq_nbr:-"+toDropDownCodeDT.getIntValue()+ "\nwith nbs_question_uid:-"+toDropDownCodeDT.getLongKey());
									logger.warn("AssociatedInvestigationUpdateUtil.updateCoInfectionInvest:coinfection investigation form code for the coinfection case is :"+investigationFormCd);
									logger.warn("AssociatedInvestigationUpdateUtil.updateCoInfectionInvest:The mapped question is a batch question in the current investigation, however the question is not a batch question in the coinfection investigation. Hence ignored" );
									continue;
								}else*/
								if(fromDropDownCodeDT.getIntValue()!=null && fromDropDownCodeDT.getIntValue().equals(NEDSSConstants.NO_BATCH_ENTRY)  && toDropDownCodeDT.getIntValue()!=null){
									logger.warn("TO Metadata question details: question_identifier:-"+ toDropDownCodeDT.getKey()+ "\nwith question_group_seq_nbr:-"+toDropDownCodeDT.getIntValue()+ "\nwith nbs_question_uid:-"+toDropDownCodeDT.getLongKey());
									logger.warn("From Metadata question details: question_identifier:-"+ fromDropDownCodeDT.getKey()+ "\nwith question_group_seq_nbr:-"+fromDropDownCodeDT.getIntValue()+ "\nwith nbs_question_uid:-"+fromDropDownCodeDT.getLongKey());
									logger.warn("AssociatedInvestigationUpdateUtil.updateCoInfectionInvest:coinfection investigation form code for the coinfection case is :"+investigationFormCd);
									logger.warn("AssociatedInvestigationUpdateUtil.updateCoInfectionInvestThe mapped question is a single select question in the current investigation, however the question is a batch question in the coinfection investigation. Hence ignored" );
									continue;
								}else {
									
									logger.debug("TO Metadata question details: question_identifier:-"+ toDropDownCodeDT.getKey()+ "\nwith question_group_seq_nbr:-"+toDropDownCodeDT.getIntValue()+ "\nwith nbs_question_uid:-"+toDropDownCodeDT.getLongKey());
									logger.debug("From Metadata question details: question_identifier:-"+ fromDropDownCodeDT.getKey()+ "\nwith question_group_seq_nbr:-"+fromDropDownCodeDT.getIntValue()+ "\nwith nbs_question_uid:-"+fromDropDownCodeDT.getLongKey());
									logger.debug("AssociatedInvestigationUpdateUtil.updateCoInfectionInvest:coinfection investigation form code for the coinfection case is :"+investigationFormCd);
									logger.debug("AssociatedInvestigationUpdateUtil.updateCoInfectionInvest:The mapped question is being updated" );
									if(mappedCoInfectionQuestions.get(toDropDownCodeDT.getKey())!=null && toDropDownCodeDT.getLongKey()!=null) {
									//	 if(pageVO.getPamAnswerDTMap().get(toDropDownCodeDT.getLongKey()) instanceof NbsCaseAnswerDT) {
										 if(toDropDownCodeDT.getIntValue()==null) {
											 Object object = mappedCoInfectionQuestions.get(toDropDownCodeDT.getKey());
											 if(object !=null && object instanceof DropDownCodeDT && (((DropDownCodeDT)object).getValue().equalsIgnoreCase(NEDSSConstants.NO_BATCH_ENTRY))){
												 //&& object.toString().equalsIgnoreCase(NEDSSConstants.DEL)) {
												 Object thisObj = pageVO.getPamAnswerDTMap().get(toDropDownCodeDT.getLongKey()) ;
												 if(thisObj!=null && thisObj instanceof NbsCaseAnswerDT) {
													 NbsCaseAnswerDT nbsCaseAnswerDT=(NbsCaseAnswerDT)pageVO.getPamAnswerDTMap().get(toDropDownCodeDT.getLongKey()) ;
													 nbsCaseAnswerDT.setItDelete(true);
													 nbsCaseAnswerDT.setItNew(false);
													 nbsCaseAnswerDT.setItDirty(false);
													 pageVO.getPamAnswerDTMap().put(toDropDownCodeDT.getLongKey(), nbsCaseAnswerDT);	 
												 } else if (thisObj != null && thisObj  instanceof ArrayList) { //multiSelect
													 ArrayList<?> aDTList = (ArrayList<?>) thisObj;
								                     for (Object ansDT : aDTList)
								                        {
								                            if (ansDT instanceof NbsCaseAnswerDT) {
																 NbsCaseAnswerDT nbsCaseAnswerDT=(NbsCaseAnswerDT)ansDT;
																 nbsCaseAnswerDT.setItDelete(true);
																 nbsCaseAnswerDT.setItNew(false);
																 nbsCaseAnswerDT.setItDirty(false);
								                            }
								                        }
								                     pageVO.getPamAnswerDTMap().put(toDropDownCodeDT.getLongKey(), aDTList); //multiSelect ArrayList
												 } //multiSel
											 }
											 else if(pageVO.getPamAnswerDTMap().get(toDropDownCodeDT.getLongKey())==null) {
												 Object thisObj = mappedCoInfectionQuestions.get(toDropDownCodeDT.getKey());
												 if (thisObj !=null && thisObj instanceof NbsCaseAnswerDT) {
													 NbsCaseAnswerDT fromNbsCaseAnswerDT=(NbsCaseAnswerDT)mappedCoInfectionQuestions.get(toDropDownCodeDT.getKey());
													 //NbsCaseAnswerDT nbsCaseAnswerDT=(NbsCaseAnswerDT)pageVO.getPamAnswerDTMap().get(toDropDownCodeDT.getLongKey()) ;
													 fromNbsCaseAnswerDT.setAnswerTxt(fromNbsCaseAnswerDT.getAnswerTxt());
													 fromNbsCaseAnswerDT.setActUid(publicHealthCaseVO.getThePublicHealthCaseDT().getPublicHealthCaseUid());
													 fromNbsCaseAnswerDT.setItDelete(false);
													 fromNbsCaseAnswerDT.setItNew(true);
													 fromNbsCaseAnswerDT.setItDirty(false);
													 pageVO.getPamAnswerDTMap().put(toDropDownCodeDT.getLongKey(), fromNbsCaseAnswerDT);
												 } else if (thisObj != null && thisObj  instanceof ArrayList) { //multiSelect
													 ArrayList<?> aDTList = (ArrayList<?>) thisObj;
								                     for (Object ansDT : aDTList)
								                        {
								                            if (ansDT instanceof NbsCaseAnswerDT) {
																 NbsCaseAnswerDT fromNbsCaseAnswerDT=(NbsCaseAnswerDT)ansDT;
																 //fromNbsCaseAnswerDT.setAnswerTxt(fromNbsCaseAnswerDT.getAnswerTxt());
																 fromNbsCaseAnswerDT.setActUid(publicHealthCaseVO.getThePublicHealthCaseDT().getPublicHealthCaseUid());
																 fromNbsCaseAnswerDT.setItDelete(false);
																 fromNbsCaseAnswerDT.setItNew(true);
																 fromNbsCaseAnswerDT.setItDirty(false);
								                            }
								                        }
								                     pageVO.getPamAnswerDTMap().put(toDropDownCodeDT.getLongKey(), aDTList); //multi select arrayList
												 }
											 }else if(pageVO.getPamAnswerDTMap().get(toDropDownCodeDT.getLongKey())!=null) {
												 Object thisObj = mappedCoInfectionQuestions.get(toDropDownCodeDT.getKey());
												 if(thisObj !=null && thisObj instanceof NbsCaseAnswerDT) {
													 NbsCaseAnswerDT fromNbsCaseAnswerDT=(NbsCaseAnswerDT)mappedCoInfectionQuestions.get(toDropDownCodeDT.getKey());
													 NbsCaseAnswerDT toNbsCaseAnswerDT=(NbsCaseAnswerDT)pageVO.getPamAnswerDTMap().get(toDropDownCodeDT.getLongKey()) ;
													 toNbsCaseAnswerDT.setAnswerTxt(fromNbsCaseAnswerDT.getAnswerTxt());
													 toNbsCaseAnswerDT.setItDelete(false);
													 toNbsCaseAnswerDT.setItNew(false);
													 toNbsCaseAnswerDT.setItDirty(true);
													 pageVO.getPamAnswerDTMap().put(toDropDownCodeDT.getLongKey(), toNbsCaseAnswerDT);
												 } else if (thisObj != null && thisObj  instanceof ArrayList) { //multiSelect upd
													 ArrayList<?> aFromDTList = (ArrayList<?>) mappedCoInfectionQuestions.get(toDropDownCodeDT.getKey());
													 ArrayList<NbsCaseAnswerDT> aToDTList = (ArrayList<NbsCaseAnswerDT>) pageVO.getPamAnswerDTMap().get(toDropDownCodeDT.getLongKey()) ;
													 if (aToDTList == null)
														 aToDTList = new ArrayList<NbsCaseAnswerDT>();
													 int theLastSeq = 0;
								                     for (Object fromAnsDT : aFromDTList) {
								                            if (fromAnsDT instanceof NbsCaseAnswerDT) {
																 NbsCaseAnswerDT fromNbsCaseAnswerDT=(NbsCaseAnswerDT)fromAnsDT;
																 boolean isNotThere = true;//update seq or add new or del old
											                     for (Object toAnsDT : aToDTList) {
											                    	 NbsCaseAnswerDT toNbsCaseAnswerDT=(NbsCaseAnswerDT)toAnsDT;
											                    	 if (toNbsCaseAnswerDT.getSeqNbr().intValue() == fromNbsCaseAnswerDT.getSeqNbr().intValue()) {
											                    		 isNotThere = false;
											                    		 toNbsCaseAnswerDT.setAnswerTxt(fromNbsCaseAnswerDT.getAnswerTxt());
																		 toNbsCaseAnswerDT.setItDelete(false);
																		 toNbsCaseAnswerDT.setItNew(false);
																		 toNbsCaseAnswerDT.setItDirty(true);
											                    	 }
											                     }
											                    if (isNotThere) {
											                    	NbsCaseAnswerDT newCaseAnswerDT = new NbsCaseAnswerDT((NbsCaseAnswerDT) fromNbsCaseAnswerDT);
											                    	newCaseAnswerDT.setActUid(publicHealthCaseVO.getThePublicHealthCaseDT().getPublicHealthCaseUid());
											                    	newCaseAnswerDT.setItDelete(false);
											                    	newCaseAnswerDT.setItNew(true);
											                    	newCaseAnswerDT.setItDirty(false);
											                    	newCaseAnswerDT.setSeqNbr(fromNbsCaseAnswerDT.getSeqNbr().intValue());
											                    	aToDTList.add(newCaseAnswerDT);
											                    }
									                    		 if (fromNbsCaseAnswerDT.getSeqNbr().intValue() > theLastSeq)
									                    			 theLastSeq = fromNbsCaseAnswerDT.getSeqNbr().intValue();
											                }
								                     } //fromAnsDT iter
									                   //check if any are past the last sequence number and need to be deleted
								                     for (Object toAnsDT : aToDTList) {
								                    	 NbsCaseAnswerDT toNbsCaseAnswerDT=(NbsCaseAnswerDT)toAnsDT;
								                    	 if (toNbsCaseAnswerDT.getSeqNbr().intValue() > theLastSeq) {
															 toNbsCaseAnswerDT.setItDelete(true);
															 toNbsCaseAnswerDT.setItNew(false);
															 toNbsCaseAnswerDT.setItDirty(false);
								                    	 }
								                     }
													 
								                     pageVO.getPamAnswerDTMap().put(toDropDownCodeDT.getLongKey(), aToDTList);
												 } //multisel upd
											 }
										 
									}else if(toDropDownCodeDT.getIntValue()!=null && toDropDownCodeDT.getIntValue().intValue()>0) {
										 Object objectRef = mappedCoInfectionQuestions.get(toDropDownCodeDT.getKey());
										 if(objectRef !=null && objectRef instanceof DropDownCodeDT && ((DropDownCodeDT)objectRef).getValue().equalsIgnoreCase(NEDSSConstants.NO_BATCH_ENTRY)){
											 ArrayList<NbsCaseAnswerDT> list=(ArrayList<NbsCaseAnswerDT>)pageVO.getPageRepeatingAnswerDTMap().get(toDropDownCodeDT.getLongKey()) ;
											 //pageVO.getPageRepeatingAnswerDTMap().remove(toDropDownCodeDT.getLongKey());
											 //pageVO.getPageRepeatingAnswerDTMap().put(toDropDownCodeDT.getLongKey().toString(), null);	 
											
											 
											 //ArrayList<?> list=(ArrayList<?>)mappedCoInfectionQuestions.get(toDropDownCodeDT.getKey());
											 list=changeStatus(list, proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid(),false, false,true,lastChgUserId,lastChgTime);
											 pageVO.getPageRepeatingAnswerDTMap().put(toDropDownCodeDT.getLongKey(), list);	 
	
										 }
										 else if(pageVO.getPageRepeatingAnswerDTMap().get(toDropDownCodeDT.getLongKey())==null) {
											 
											 ArrayList<NbsCaseAnswerDT> list=(ArrayList<NbsCaseAnswerDT>)mappedCoInfectionQuestions.get(toDropDownCodeDT.getKey());
											 list=changeStatus(list, proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid(),true, false,false,lastChgUserId,lastChgTime);
											
											 pageVO.getPageRepeatingAnswerDTMap().put(toDropDownCodeDT.getLongKey(), list);	 
											
										 }else if(pageVO.getPageRepeatingAnswerDTMap().get(toDropDownCodeDT.getLongKey())!=null) {
											 ArrayList<NbsCaseAnswerDT> deleteList=(ArrayList<NbsCaseAnswerDT>)pageVO.getPageRepeatingAnswerDTMap().get(toDropDownCodeDT.getLongKey());
											 deleteList=changeStatus(deleteList, proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid(),false, false,true,lastChgUserId,lastChgTime);
											
											 ArrayList<NbsCaseAnswerDT> list=(ArrayList<NbsCaseAnswerDT>)mappedCoInfectionQuestions.get(toDropDownCodeDT.getKey());
											 list=changeStatus(list, proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid(),true, false,false,lastChgUserId,lastChgTime);
											
											 deleteList.addAll(list);
											 
											 pageVO.getPageRepeatingAnswerDTMap().put(toDropDownCodeDT.getLongKey(), deleteList);	 
										 } 
									 }else {
											logger.error("\n\nPLEASE check!!!TO Metadata question details: question_identifier:-"+ toDropDownCodeDT.getKey()+ "\nwith question_group_seq_nbr:-"+toDropDownCodeDT.getIntValue()+ "\nwith nbs_question_uid:-"+toDropDownCodeDT.getLongKey());
											logger.error("PLEASE check!!!From Metadata question details: question_identifier:-"+ fromDropDownCodeDT.getKey()+ "\nwith question_group_seq_nbr:-"+fromDropDownCodeDT.getIntValue()+ "\nwith nbs_question_uid:-"+fromDropDownCodeDT.getLongKey());
											logger.error("PLEASE check!!!AssociatedInvestigationUpdateUtil.updateCoInfectionInvest:coinfection investigation form code for the coinfection case is :"+investigationFormCd);
											logger.error("PLEASE check!!!AssociatedInvestigationUpdateUtil.updateCoInfectionInvest:The mapped question is being updated" );
											
										/*NbsCaseAnswerDT currentNbsCaseAnswerDT=(NbsCaseAnswerDT)mappedCoInfectionQuestions.get(toDropDownCodeDT.getKey());
										currentNbsCaseAnswerDT.setAddTime(lastChgTime);
										currentNbsCaseAnswerDT.setAddUserId(lastChgUserId);
										currentNbsCaseAnswerDT.setItNew(true);
										currentNbsCaseAnswerDT.setActUid(publicHealthCaseUid);
										pageVO.getPamAnswerDTMap().put(toDropDownCodeDT.getKey(), currentNbsCaseAnswerDT);*/
									}
									
								}else if(mappedCoInfectionQuestions.get(toDropDownCodeDT.getKey())==null && toDropDownCodeDT.getLongKey()!=null) {
									logger.debug("TO Metadata question details: question_identifier:-"+ toDropDownCodeDT.getKey()+ "\nwith question_group_seq_nbr:-"+toDropDownCodeDT.getIntValue()+ "\nwith nbs_question_uid:-"+toDropDownCodeDT.getLongKey());
									logger.debug("From Metadata question details: question_identifier:-"+ fromDropDownCodeDT.getKey()+ "\nwith question_group_seq_nbr:-"+fromDropDownCodeDT.getIntValue()+ "\nwith nbs_question_uid:-"+fromDropDownCodeDT.getLongKey());
									logger.debug("AssociatedInvestigationUpdateUtil.updateCoInfectionInvest:coinfection investigation form code for the coinfection case is :"+investigationFormCd);
									logger.debug("AssociatedInvestigationUpdateUtil.updateCoInfectionInvest:current investigation does not have that question, however the coninfection PHC case has question. Hence ignored" );
	
									continue;
								}else if(mappedCoInfectionQuestions.get(toDropDownCodeDT.getKey())!=null && toDropDownCodeDT.getLongKey()==null) {
									logger.debug("TO Metadata question details: question_identifier:-"+ toDropDownCodeDT.getKey()+ "\nwith question_group_seq_nbr:-"+toDropDownCodeDT.getIntValue()+ "\nwith nbs_question_uid:-"+toDropDownCodeDT.getLongKey());
									logger.debug("From Metadata question details: question_identifier:-"+ fromDropDownCodeDT.getKey()+ "\nwith question_group_seq_nbr:-"+fromDropDownCodeDT.getIntValue()+ "\nwith nbs_question_uid:-"+fromDropDownCodeDT.getLongKey());
									logger.debug("AssociatedInvestigationUpdateUtil.updateCoInfectionInvest:coinfection investigation form code for the coinfection case is :"+investigationFormCd);
									logger.debug("AssociatedInvestigationUpdateUtil.updateCoInfectionInvest:current investigation does have that question, however the coninfection PHC case does not has the same question. Hence ignored" );
	
									continue;
								} 
								
							}
							
						} 
					}catch (Exception e) {
						logger.fatal("Exception is "+ e);
						String errorMessage ="Error processing co-infection question " +currentToQuestionKey + " " + e.getCause()+ e.getMessage();
						logger.fatal("CoInfectionUtil.updateCoInfectionInvestigations:-Exception There is an error while updating individual associated investigations:", errorMessage, e);
						
					}
					
				}
			
			}
		}
	    	/**
	    	 * Merge Investigation case issue where the superseded investigation should not allowed to update!!!
	    	 * 1. Only cases that are not Merge Investigation are allowed to proceed
	    	 * 2. Only cases that are Merge Investigation that are not superseded are allowed to proceed
	    	 * 3. Even Closed cases that are part of co-infection are NOW allowed to proceed with updated co-infection id(https://nbsteamdev.atlassian.net/browse/ND-9114
	    	 * 		Description Losing investigation's Coinfection is not assigned the correct Co-Infection Id when status = Closed)
	    	 * 
	    	 */
			//Set the winning investigation's coinfectionId to losing investigation's related co-infection investigations.
		    if(coinfectionIdToUpdate!=null){
		    	String survivingEpiLinkId = publicHealthCaseVO.getTheCaseManagementDT().getEpiLinkId();
		    	String supersededEpiLinkId = supersededPublicHealthCaseVO.getTheCaseManagementDT().getEpiLinkId();
		    	
		    	if(coInSupersededEpliLinkIdMap.get(proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid()) !=null) {
		    		proxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().setEpiLinkId(survivingEpiLinkId);
		    	}
		    	proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().setCoinfectionId(coinfectionIdToUpdate);
				proxyVO.setMergeCase(true);
		    }

		    // Updates coinfection question's values in tables other than NBS_Case_Answer
		    updateCoInfectionInvestForOtherTables(proxyVO, updateValueInOtherTablesMap, pageActProxyVO, publicHealthCaseVO, coinfectionProxyVO);
		    
	    	if(coinfectionIdToUpdate==null 
	    			|| (supersededPublicHealthCaseVO!= null 
	    				&& publicHealthCaseUid.compareTo(supersededPublicHealthCaseVO.getThePublicHealthCaseDT().getPublicHealthCaseUid())!=0))
	    	{
	    		updatePageProxyVOInterface(proxyVO,lastChgTime,lastChgUserId, nbsSecurityObj);

	    		if (pageActProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().isStdOpenedFromClosed()) {
	    			pageActProxyVO.getPageVO().setPamAnswerDTMap(proxyVO.getPageVO().getPamAnswerDTMap());
	    			pageActProxyVO.getPageVO().setPageRepeatingAnswerDTMap(proxyVO.getPageVO().getPageRepeatingAnswerDTMap());
	    			pageActProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().setStdOpenedFromClosed(false);
	    			Long phcUid = pageCaseUtil.setPageActProxyVO( pageActProxyVO, false, nbsSecurityObj);
	    		} else {
	    			Long phcUid = pageCaseUtil.setPageActProxyVO( proxyVO, true, nbsSecurityObj);
	    		}
	    		/*if (!pageActProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().isStdOpenedFromClosed()) {
	    			Long phcUid = pageCaseUtil.setPageActProxyVO( proxyVO, nbsSecurityObj);
	    		} else {
	    			PamRootDAO pamRootDAO = new PamRootDAO();
					pamRootDAO.editPamVO(proxyVO.getPageVO(), proxyVO.getPublicHealthCaseVO());
	    		}*/
	    		logger.debug("updateCoInfectionInvest method call completed for coinfectionIdToUpdate:"+ coinfectionIdToUpdate);
	    	}

		}catch(Exception e) {
			logger.fatal("Exception is "+ e);
			String errorMessage =e.getCause()+ e.getMessage();
			
			logger.fatal("CoInfectionUtil.updateCoInfectionInvestigations:-Exception There is an error while updateCoInfectionInvestigations:", errorMessage, e);
			throw new java.rmi.RemoteException(e.toString());
		}
	}
	
	/**
	 * 
	 * Updates coinfection question's values in tables other than NBS_Case_Answer
	 * 
	 * @param pageActProxyVOofCoinfection
	 * @param updateValueInOtherTablesMap
	 * @param pageActProxyVO
	 * @param publicHealthCaseVO
	 * @throws RemoteException
	 */
	private  void updateCoInfectionInvestForOtherTables(PageActProxyVO pageActProxyVOofCoinfection, Map<Object, Object> updateValueInOtherTablesMap, PageActProxyVO pageActProxyVO  ,PublicHealthCaseVO publicHealthCaseVO, PageActProxyVO coinfectionProxyVO) throws RemoteException {
		try {
			for (Object key : updateValueInOtherTablesMap.keySet()) {
				String dbLocation = (String) updateValueInOtherTablesMap.get(key);
				if(dbLocation!=null && dbLocation.contains("PERSON.")){
					//Commented out as its tries to update MPR concurrently within same transaction. First for current investigation's patient and then coinfection investigation's patient.
					
					/*String columnName = dbLocation.substring(dbLocation.indexOf(".")+1,dbLocation.length());
					String valueToUpdate = null;
					if (pageActProxyVO.getThePersonVOCollection() != null) {
						for (Iterator<Object> anIterator = pageActProxyVO.getThePersonVOCollection()
								.iterator(); anIterator.hasNext();) {
							PersonVO personVO = (PersonVO) anIterator.next();
							if (NEDSSConstants.PAT.equals(personVO.getThePersonDT().getCd())) {
								String getterMethod = DynamicBeanBinding.getGetterName(columnName);
								valueToUpdate = getValueForMethod(personVO.getThePersonDT(),getterMethod,personVO.getThePersonDT().getClass().getName());
								break;
							}
						}
					}
					
					if(pageActProxyVOofCoinfection.getThePersonVOCollection() != null){
						for (Iterator<Object> anIterator = pageActProxyVOofCoinfection.getThePersonVOCollection()
								.iterator(); anIterator.hasNext();) {
							PersonVO personVO = (PersonVO) anIterator.next();
							if (NEDSSConstants.PAT.equals(personVO.getThePersonDT().getCd()) && valueToUpdate!=null) {
								
								DynamicBeanBinding.populateBean(personVO.getThePersonDT(), columnName, valueToUpdate);
								
								personVO.getThePersonDT().setItDirty(true);
								personVO.getThePersonDT().setItNew(false);
								
								break;
							}
						}
					}*/
					
				}else if(dbLocation!=null && dbLocation.contains("CASE_MANAGEMENT.")){
					String columnName = dbLocation.substring(dbLocation.indexOf(".")+1,dbLocation.length());
					String getterMethod = DynamicBeanBinding.getGetterName(columnName);
					
					if(getterMethod!=null){
						String value = DynamicBeanBinding.getValueForMethod(publicHealthCaseVO.getTheCaseManagementDT(),getterMethod,publicHealthCaseVO.getTheCaseManagementDT().getClass().getName());
						if(value!=null){
							DynamicBeanBinding.populateBean(pageActProxyVOofCoinfection.getPublicHealthCaseVO().getTheCaseManagementDT(), columnName, value);
							
							pageActProxyVOofCoinfection.getPublicHealthCaseVO().getTheCaseManagementDT().setItDelete(true);
							pageActProxyVOofCoinfection.getPublicHealthCaseVO().getTheCaseManagementDT().setItNew(false);
						} 
						if(publicHealthCaseVO.getThePublicHealthCaseDT().isStdOpenedFromClosed() && coinfectionProxyVO!=null) {
							//Incase of closed to Open investigation update the triggering investigation from coinfection one.
							value = DynamicBeanBinding.getValueForMethod(coinfectionProxyVO.getPublicHealthCaseVO().getTheCaseManagementDT(),getterMethod,publicHealthCaseVO.getTheCaseManagementDT().getClass().getName());
							DynamicBeanBinding.populateBean(pageActProxyVO.getPublicHealthCaseVO().getTheCaseManagementDT(), columnName, value);
							
							pageActProxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().setItDirty(true);
							pageActProxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().setItNew(false);
						}
					}else{
						logger.debug("getterMethod does not found from columnName: "+columnName +", not updating coinfection questions.");
					}
					
				}/*else if(dbLocation!=null && dbLocation.contains("PUBLIC_HEALTH_CASE.")){
					String columnName = dbLocation.substring(dbLocation.indexOf(".")+1,dbLocation.length());
					String getterMethod = DynamicBeanBinding.getGetterName(columnName);
					String value = DynamicBeanBinding.getValueForMethod(publicHealthCaseVO.getThePublicHealthCaseDT(),getterMethod,publicHealthCaseVO.getThePublicHealthCaseDT().getClass().getName());
					
					if(value!=null){
						DynamicBeanBinding.populateBean(pageActProxyVOofCoinfection.getPublicHealthCaseVO().getThePublicHealthCaseDT(), columnName, value);
						
						pageActProxyVOofCoinfection.getPublicHealthCaseVO().getThePublicHealthCaseDT().setItDelete(true);
						pageActProxyVOofCoinfection.getPublicHealthCaseVO().getThePublicHealthCaseDT().setItNew(false);
					}
				}*/
			}
			
		}catch(Exception ex){
			logger.fatal("Exception is "+ ex);
			String errorMessage =ex.getCause()+ ex.getMessage();
			logger.fatal("CoInfectionUtil.updateCoInfectionInvestigations:-Exception There is an error while updateCoInfectionInvestigations:", errorMessage, ex);
			throw new java.rmi.RemoteException(ex.toString());
		}
	}

	
	private ArrayList<NbsCaseAnswerDT> changeStatus(ArrayList<NbsCaseAnswerDT> list,Long publicHealthCaseUid, boolean itNew, boolean itDirty, boolean itDelete,Long lastChgUserId, Timestamp lastChgTime){
		if(list!=null) {
			Iterator<NbsCaseAnswerDT> iterator= list.iterator();
			while(iterator.hasNext()) {
				NbsCaseAnswerDT caseAnswerDT =  (NbsCaseAnswerDT)iterator.next();
				caseAnswerDT.setLastChgUserId(lastChgUserId);
				caseAnswerDT.setLastChgTime(lastChgTime);
				caseAnswerDT.setActUid(publicHealthCaseUid);
				caseAnswerDT.setItNew(itNew);
				caseAnswerDT.setItDirty(itDirty);
				caseAnswerDT.setItDelete(itDelete);
			}
		}
		return list;
	}
	
	

	public void updatePageProxyVOInterface(PageActProxyVO proxyActVO,Timestamp lastChgTime, Long lastChgUserId, NBSSecurityObj nbsSecurityObj) {
		try {
			proxyActVO.setRenterant(true);
			
			
			proxyActVO.setItDirty(true);
			proxyActVO.getPublicHealthCaseVO().setItDirty(true);
			proxyActVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().setLastChgTime(lastChgTime);
			proxyActVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().setLastChgUserId((lastChgUserId));
			proxyActVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().setItDirty(true);
			//proxyActVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().setVersionCtrlNbr(proxyActVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getVersionCtrlNbr()+1);
			//Collection personVOCollection=proxyActVO.getPublicHealthCaseVO().getTheParticipationDTCollection();
			//Collection confirmationMethodCollection=proxyActVO.getPublicHealthCaseVO().getTheConfirmationMethodDTCollection();
			if (proxyActVO.getThePersonVOCollection() != null) {
				for (Iterator<Object> anIterator = proxyActVO.getThePersonVOCollection().iterator(); anIterator.hasNext();) {
					PersonVO personVO= (PersonVO)anIterator.next();
					if (personVO.getThePersonDT().getCd().equals(NEDSSConstants.PAT) && !proxyActVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().isStdOpenedFromClosed()) {
						personVO.getThePersonDT().setLastChgTime(lastChgTime);
						personVO.getThePersonDT().setLastChgUserId(lastChgUserId);
						personVO.getThePersonDT().setItDirty(true);
						personVO.getThePersonDT().setItNew(false);
						
						//personVO.getThePersonDT().setVersionCtrlNbr(personVO.getThePersonDT().getVersionCtrlNbr()+1);
					}
				}
						
				if (proxyActVO.getPageVO() != null) {
					Map<Object, Object> map = proxyActVO.getPageVO().getPamAnswerDTMap();
					if(map!=null) {
						//Map<Object, Object> returnMap =
						updateNbsCaseAnswerInterfaceValues(map, lastChgTime, lastChgUserId);
						//proxyActVO.getPageVO().getPamAnswerDTMap().clear();;
						//proxyActVO.getPageVO().setPamAnswerDTMap(returnMap);
					}
					Map<Object, Object> repeatingMap = proxyActVO.getPageVO().getPageRepeatingAnswerDTMap();
					if(repeatingMap!=null) {
						//Map<Object, Object> returnMap =
						updateNbsCaseAnswerInterfaceValues(repeatingMap, lastChgTime, lastChgUserId);
						//proxyActVO.getPageVO().getPageRepeatingAnswerDTMap().clear();
						//proxyActVO.getPageVO().setPageRepeatingAnswerDTMap(returnMap);
					}
					if(proxyActVO.getPageVO().getActEntityDTCollection()!=null && !proxyActVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().isStdOpenedFromClosed()) {
						//ArrayList<Object> returnList= new ArrayList<Object>();
						Iterator<Object> iterator = proxyActVO.getPageVO().getActEntityDTCollection().iterator();
						while(iterator.hasNext()) {
							NbsActEntityDT actEntityDT= (NbsActEntityDT)iterator.next();
							actEntityDT.setLastChgTime(lastChgTime);
							actEntityDT.setLastChgUserId(lastChgUserId);
							actEntityDT.setItDirty(true);
							actEntityDT.setItNew(false);
							//returnList.add(actEntityDT);
						}
						//proxyActVO.getPageVO().getActEntityDTCollection().clear();
						//proxyActVO.getPageVO().getActEntityDTCollection().retainAll(returnList);
						
					}
				}
			}
		} catch (Exception e) {
			logger.fatal("Error while creating a updatePageProxyVO:",e);
			throw new EJBException(e.toString());
		}
	}
	
	
	public CTContactProxyVO updateContactProxyVO(CTContactProxyVO cTContactProxyVO,Timestamp lastChgTime, Long lastChgUserId, NBSSecurityObj nbsSecurityObj) {
		try {
			//cTContactProxyVO.setRenterant(true);
			
			cTContactProxyVO.setItDirty(true);
			cTContactProxyVO.getcTContactVO().setItDirty(true);
			cTContactProxyVO.getcTContactVO().getcTContactDT().setLastChgTime(lastChgTime);
			cTContactProxyVO.getcTContactVO().getcTContactDT().setLastChgUserId((lastChgUserId));
			cTContactProxyVO.getcTContactVO().getcTContactDT().setItDirty(true);
			
			if (cTContactProxyVO.getPersonVOCollection() != null) {
				for (Iterator<Object> anIterator = cTContactProxyVO.getPersonVOCollection().iterator(); anIterator.hasNext();) {
					PersonVO personVO= (PersonVO)anIterator.next();
					if (personVO.getThePersonDT().getCd().equals(NEDSSConstants.PAT)) {
						personVO.getThePersonDT().setLastChgTime(lastChgTime);
						personVO.getThePersonDT().setLastChgUserId(lastChgUserId);
						personVO.getThePersonDT().setItDirty(true);
						personVO.getThePersonDT().setItNew(false);
					}
				}
			}
			
			if (cTContactProxyVO.getContactPersonVO()!= null) {
				PersonVO personVO= cTContactProxyVO.getContactPersonVO();
				personVO.getThePersonDT().setLastChgTime(lastChgTime);
				personVO.getThePersonDT().setLastChgUserId(lastChgUserId);
				personVO.getThePersonDT().setItDirty(true);
				personVO.getThePersonDT().setItNew(false);
			}
			
			
			if (cTContactProxyVO.getcTContactVO().getCtContactAnswerDTMap()!= null) {
				Map<Object, Object> map = cTContactProxyVO.getcTContactVO().getCtContactAnswerDTMap();
				if(map!=null) {
					updateCtContactAnswerDTMapValues(map, lastChgTime, lastChgUserId);
				}
				Map<Object, Object> repeatingMap = cTContactProxyVO.getcTContactVO().getRepeatingAnswerDTMap();
				if(repeatingMap!=null) {
					updateCtContactAnswerDTMapValues(repeatingMap, lastChgTime, lastChgUserId);
				}
				if(cTContactProxyVO.getcTContactVO().getActEntityDTCollection()!=null) {
					Iterator<Object> iterator = cTContactProxyVO.getcTContactVO().getActEntityDTCollection().iterator();
					while(iterator.hasNext()) {
						NbsActEntityDT actEntityDT= (NbsActEntityDT)iterator.next();
						actEntityDT.setLastChgTime(lastChgTime);
						actEntityDT.setLastChgUserId(lastChgUserId);
						actEntityDT.setItDirty(true);
						actEntityDT.setItNew(false);
					}
					
				}
			}
			
		} catch (Exception e) {
			logger.fatal("Error while creating a updateContactProxyVO:",e);
			throw new EJBException(e.toString());
		}
		return cTContactProxyVO;
	}
	
	private Map<Object, Object> updateCtContactAnswerDTMapValues(Map<Object, Object> map, Timestamp lastChgTime, Long lastChgUserId) {
		Map<Object, Object> returnMap = new HashMap<Object, Object>();
		
		try {
			Iterator<Object> iterator = map.keySet().iterator();
			while(iterator.hasNext()) {
				Object key = iterator.next();
				Object object = map.get(key);
				if(object instanceof CTContactAnswerDT) {
					CTContactAnswerDT contactAnswerDT = (CTContactAnswerDT)object;
					contactAnswerDT.setLastChgTime(lastChgTime);
					contactAnswerDT.setLastChgUserId(lastChgUserId);
				//caseAnswerDT.setItDirty(true);
				//caseAnswerDT.setItNew(false);
				if(!contactAnswerDT.isItDelete() && !contactAnswerDT.isItDirty() && !contactAnswerDT.isItNew()) {
					contactAnswerDT.setItDirty(false);
					contactAnswerDT.setItNew(true);
				}
				returnMap.put(key, contactAnswerDT);
				}else if(object instanceof ArrayList) {
					@SuppressWarnings("unchecked")
					ArrayList<Object>  list =(ArrayList<Object>)object;
					ArrayList<Object> returnList= new ArrayList<Object>();
					Iterator<Object> listIterator = list.iterator();
					while(listIterator.hasNext()) {
						CTContactAnswerDT contactAnswerDT = (CTContactAnswerDT)listIterator.next();
						contactAnswerDT.setLastChgTime(lastChgTime);
						contactAnswerDT.setLastChgUserId(lastChgUserId);
						contactAnswerDT.setItDirty(false);
						contactAnswerDT.setItNew(true);
						returnList.add(contactAnswerDT);
					}
					returnMap.put(key, returnList);
				}
			}
		} catch (Exception e) {
			logger.fatal("Error while creating a updateCtContactAnswerDTMapValues:",e);
			throw new EJBException(e.toString());
		}
		return returnMap;
	}
	
	
	private Map<Object, Object> updateNbsCaseAnswerInterfaceValues(Map<Object, Object> map, Timestamp lastChgTime, Long lastChgUserId) {
		Map<Object, Object> returnMap = new HashMap<Object, Object>();
		
		try {
			Iterator<Object> iterator = map.keySet().iterator();
			while(iterator.hasNext()) {
				Object key = iterator.next();
				Object object = map.get(key);
				if(object instanceof NbsCaseAnswerDT) {
				NbsCaseAnswerDT caseAnswerDT = (NbsCaseAnswerDT)object;
				caseAnswerDT.setLastChgTime(lastChgTime);
				caseAnswerDT.setLastChgUserId(lastChgUserId);
				//caseAnswerDT.setItDirty(true);
				//caseAnswerDT.setItNew(false);
				if(!caseAnswerDT.isItDelete() && !caseAnswerDT.isItDirty() && !caseAnswerDT.isItNew()) {
					caseAnswerDT.setItDirty(true);
					caseAnswerDT.setItNew(false);
				}
				returnMap.put(key, caseAnswerDT);
				}else if(object instanceof ArrayList) {
					@SuppressWarnings("unchecked")
					ArrayList<Object>  list =(ArrayList<Object>)object;
					ArrayList<Object> returnList= new ArrayList<Object>();
					Iterator<Object> listIterator = list.iterator();
					while(listIterator.hasNext()) {
						NbsCaseAnswerDT caseAnswerDT = (NbsCaseAnswerDT)listIterator.next();
						caseAnswerDT.setLastChgTime(lastChgTime);
						caseAnswerDT.setLastChgUserId(lastChgUserId);
						//caseAnswerDT.setItDirty(false);
						//caseAnswerDT.setItNew(true);
						returnList.add(caseAnswerDT);
					}
					returnMap.put(key, returnList);
				}
			}
		} catch (Exception e) {
			logger.fatal("Error while creating a setNbsCaseAnswerMap:",e);
			throw new EJBException(e.toString());
		}
		return returnMap;
	}

	/*public void updateCaseManagementEpilink (String currEpilink, String newEpilink, NBSSecurityObj securityObj)  throws java.rmi.RemoteException, javax.ejb.EJBException, NEDSSAppException, NEDSSSystemException

    {
		
    	CaseManagementDAOImpl cmDAO = new CaseManagementDAOImpl();
    	EpilinkActivityLogDaoImpl epilinkDAO = new EpilinkActivityLogDaoImpl();
    	EpilinkDT epilinkDT = new EpilinkDT();
    	epilinkDT.setOldEpilinkId(currEpilink);
    	epilinkDT.setNewEpilinkId(newEpilink);
    	epilinkDT.setProcessedDate(new Date());
    	epilinkDT.setRecordStatusCd("Active");
    	epilinkDT.setTargetTypeCd("CASE");
    	epilinkDT.setSourceTypeCd("CASE");
    	epilinkDT.setDocType("CASE");
    	epilinkDT.setActionTxt("MERGE_EPILINKID");
    	epilinkDT.setAddUserId(Long.valueOf(securityObj.getTheUserProfile().getTheUser().getEntryID()));
       try
      {
    	   List<Object> investigationslist = cmDAO.getEpilinkDTCollection(currEpilink);
    	   String investigationsString="";
    	   for(Object dt : investigationslist){
    		   if(dt instanceof CaseManagementDT){
    			   investigationsString += ((CaseManagementDT)dt).getPublicHealthCaseUid() + " ";
    		   }
    	   }
    	   epilinkDT.setInvestigationsString(investigationsString);
    	   cmDAO.updateCaseManagementEpilink(currEpilink, newEpilink);
    	   epilinkDAO.insertEpilinkLog(epilinkDT);
    		
      }catch(NEDSSAppException nae){
    	  logger.debug("Exception string is: " + nae.getErrorCd());
          throw new  EJBException(nae.toString());
      }
      catch(Exception e)
      {
          logger.debug(e.toString()+" : setPublicHealthCaseVO dataconcurrency catch: " + e.getClass());
          logger.debug("Exception string is: " + e.toString());
          throw new  EJBException(e.toString());
      }
    }*/

}
