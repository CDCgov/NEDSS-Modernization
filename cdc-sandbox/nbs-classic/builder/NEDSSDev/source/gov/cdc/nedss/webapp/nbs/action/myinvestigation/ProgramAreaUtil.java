package gov.cdc.nedss.webapp.nbs.action.myinvestigation;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.rmi.PortableRemoteObject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import gov.cdc.nedss.association.dt.ParticipationDT;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.nbsactentity.dt.NbsActEntityDT;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.act.PageActProxyVO;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.bean.InvestigationProxy;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.bean.InvestigationProxyHome;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.InvestigationProxyVO;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.InvestigationSummaryVO;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.bean.PamProxy;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.bean.PamProxyHome;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.vo.PamProxyVO;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.util.EdxPHCRDocumentUtil;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.StringUtils;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageCreateHelper;
import gov.cdc.nedss.webapp.nbs.action.pam.util.PamStoreUtil;
import gov.cdc.nedss.webapp.nbs.action.util.QueueUtil;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;


/**
 * This is a utility class for the ProgramArea which is mainly for the filtering
 * of the investigation
 * @author habraham2
 *
 */
public class ProgramAreaUtil {
	
	static final LogUtils logger = new LogUtils(ProgramAreaLoad.class.getName());
	public static final String delimiter = " | ";
	QueueUtil queueUtil = new QueueUtil();
	

    public static void processPGTypeInvestigations(String publicHealthCaseUID,NBSSecurityObj nbsSecurityObj ,String selectedInvestigator ,
    		String dateAssigned,HttpSession session) throws Exception {
    	
    	String userId = nbsSecurityObj.getTheUserProfile().getTheUser().getEntryID();
		Long actUid=null;
		PageActProxyVO oldPageActProxyVO = EdxPHCRDocumentUtil.getPageProxyVOWithoutSummary(
				Long.valueOf(publicHealthCaseUID) , nbsSecurityObj);
		boolean isNewParticipationRequired = true;
		//Change the Participation DT
		Collection<Object> oldParticipationDTCollection=oldPageActProxyVO.getPublicHealthCaseVO().getTheParticipationDTCollection();
		Iterator<Object> oldParticipationDTIterator=oldParticipationDTCollection.iterator();
		Collection<Object> newParticipationDTList = new ArrayList	<Object>();
		while(oldParticipationDTIterator.hasNext()) {
			ParticipationDT participationDT =(ParticipationDT)oldParticipationDTIterator.next();
			if(NEDSSConstants.PHC_INVESTIGATOR.equalsIgnoreCase(participationDT.getTypeCd())) {
				//Investogator exist . delete this object . 
				if(!selectedInvestigator.equalsIgnoreCase( participationDT.getSubjectEntityUid().toString())  ) {
					participationDT.setItDelete(true);
					newParticipationDTList.add(participationDT);
					isNewParticipationRequired=true;
				}else {
					isNewParticipationRequired=false;
				}
				
			}else {
				participationDT.setItDirty(true);
				newParticipationDTList.add(participationDT);
			}
			actUid=participationDT.getActUid();
		}
		//Create new one
		if(isNewParticipationRequired) {
			ParticipationDT newParticipationDT= PageCreateHelper.createParticipation(Long.valueOf( userId), new Timestamp(System.currentTimeMillis()), userId, 
					actUid, selectedInvestigator, "PSN", NEDSSConstants.PHC_INVESTIGATOR, "CASE");
			newParticipationDTList.add(newParticipationDT);
		}
		
	
		oldPageActProxyVO.setTheParticipationDTCollection(newParticipationDTList);
		
	//Change the entities
		Integer entVersion =null;
		Iterator actEntityDTCollectionIterator=oldPageActProxyVO.getPageVO().getActEntityDTCollection().iterator();
		Collection<Object> newActEntityDTList = new ArrayList	<Object>();
		while(actEntityDTCollectionIterator.hasNext()) {
			NbsActEntityDT oldActEntityDT=(NbsActEntityDT) actEntityDTCollectionIterator.next();
			if(NEDSSConstants.PHC_INVESTIGATOR.equalsIgnoreCase(oldActEntityDT.getTypeCd())) {
				//Delete existing entity
				oldActEntityDT.setItDelete(true);
				newActEntityDTList.add(oldActEntityDT);
				entVersion=oldActEntityDT.getEntityVersionCtrlNbr();
			}else {
				oldActEntityDT.setItDirty(true);
				oldActEntityDT.setEntityVersionCtrlNbr(oldActEntityDT.getEntityVersionCtrlNbr()!=null?
						oldActEntityDT.getEntityVersionCtrlNbr().intValue()+1:1);
				newActEntityDTList.add(oldActEntityDT);
			}
			
		} 
		//Create new one
		entVersion=entVersion!=null ? entVersion.intValue()+1:1;
		
		NbsActEntityDT newActEntityDT= PageCreateHelper.createPamCaseEntity(actUid,
				selectedInvestigator, entVersion.toString(), NEDSSConstants.PHC_INVESTIGATOR, userId);
		newActEntityDTList.add(newActEntityDT);
		
		oldPageActProxyVO.getPageVO().setActEntityDTCollection(newActEntityDTList);
		
		
		//Change date assigned to investigation
		oldPageActProxyVO.setCurrentInvestigator(selectedInvestigator);
		oldPageActProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().setInvestigatorAssignedTime(
				StringUtils
				.stringToStrutsTimestamp(dateAssigned)
				);
		oldPageActProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().setItDirty(true);
		oldPageActProxyVO.setItDirty(true);
		
		String sBeanJndiName = JNDINames.PAGE_PROXY_EJB;
		MainSessionHolder holder = new MainSessionHolder();
		MainSessionCommand msCommand = holder.getMainSessionCommand(session);
		 Object[] oParams = { NEDSSConstants.CASE, oldPageActProxyVO };
		 String sMethod = "setPageProxyVO";
		 msCommand.processRequest(sBeanJndiName, sMethod, oParams);
    }
    public static void processRVCTorVarcellaTypeInvestigations(String publicHealthCaseUID,NBSSecurityObj nbsSecurityObj ,String selectedInvestigator ,
    		String dateAssigned,HttpSession session,HttpServletRequest request) throws Exception {
    	
    	String userId = nbsSecurityObj.getTheUserProfile().getTheUser().getEntryID();
		Long addUserId =null;
		Long actUid=null;
		
		PamProxyVO proxyVO = new PamProxyVO(); 
		 NedssUtils nedssUtils = new NedssUtils();
		Object object = nedssUtils.lookupBean(JNDINames.PAM_PROXY_EJB);
		PamProxyHome home = (PamProxyHome) PortableRemoteObject.narrow(object, PamProxyHome.class);
		PamProxy pamproxy = home.create();	  
        proxyVO =  pamproxy.getPamProxy(Long.valueOf(publicHealthCaseUID), nbsSecurityObj);
        boolean isNewParticipationRequired = true;
		
		//Change the Participation DT
		Collection<Object> oldParticipationDTCollection=proxyVO.getPublicHealthCaseVO().getTheParticipationDTCollection();
		Iterator<Object> oldParticipationDTIterator=oldParticipationDTCollection.iterator();
		Collection<Object> newParticipationDTList = new ArrayList	<Object>();
		while(oldParticipationDTIterator.hasNext()) {
			ParticipationDT participationDT =(ParticipationDT)oldParticipationDTIterator.next();
			if(NEDSSConstants.PHC_INVESTIGATOR.equalsIgnoreCase(participationDT.getTypeCd())) {
				//Investogator exist . delete this object . 
				if(!selectedInvestigator.equalsIgnoreCase( participationDT.getSubjectEntityUid().toString())  ) {
					participationDT.setItDelete(true);
					newParticipationDTList.add(participationDT);
					isNewParticipationRequired=true;
				}else {
					isNewParticipationRequired=false;
				}
				
			}else {
				//Just change the last change time 
				participationDT.setItDirty(true);
				newParticipationDTList.add(participationDT);
			}
			actUid=participationDT.getActUid();
		}
			//Create new one
		if(isNewParticipationRequired) {
			ParticipationDT newParticipationDT= PageCreateHelper.createParticipation(Long.valueOf(userId), new Timestamp(System.currentTimeMillis()), userId, 
				actUid, selectedInvestigator, "PSN", NEDSSConstants.PHC_INVESTIGATOR, "CASE");
			newParticipationDTList.add(newParticipationDT);
		}
		proxyVO.setTheParticipationDTCollection(newParticipationDTList);
		
	//Change the entities
		
		Iterator actEntityDTCollectionIterator=proxyVO.getPamVO().getActEntityDTCollection().iterator();
		Collection<Object> newActEntityDTList = new ArrayList	<Object>();
		Integer entVersion =null;
		while(actEntityDTCollectionIterator.hasNext()) {
			NbsActEntityDT oldActEntityDT=(NbsActEntityDT) actEntityDTCollectionIterator.next();
			if(NEDSSConstants.PHC_INVESTIGATOR.equalsIgnoreCase(oldActEntityDT.getTypeCd())) {
				//Delete existing entity
				oldActEntityDT.setItDelete(true);
				newActEntityDTList.add(oldActEntityDT);
				entVersion=oldActEntityDT.getEntityVersionCtrlNbr();
			}else {
				oldActEntityDT.setItDirty(true);
				oldActEntityDT.setEntityVersionCtrlNbr(oldActEntityDT.getEntityVersionCtrlNbr()!=null ? oldActEntityDT.getEntityVersionCtrlNbr().intValue()+1:1);
				newActEntityDTList.add(oldActEntityDT);
			}
			
		}
		entVersion=entVersion!=null ? entVersion.intValue()+1:1;
		//Create new one
		NbsActEntityDT newActEntityDT= PageCreateHelper.createPamCaseEntity(actUid,
				selectedInvestigator, entVersion.toString(), NEDSSConstants.PHC_INVESTIGATOR, userId);
		newActEntityDTList.add(newActEntityDT);
		
		proxyVO.getPamVO().setActEntityDTCollection(newActEntityDTList);
		
		//Change date assigned to investigation
		proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().setInvestigatorAssignedTime(
				StringUtils
				.stringToStrutsTimestamp(dateAssigned)
				);
		proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().setItDirty(true);
		proxyVO.setItDirty(true);
		
		PamStoreUtil.sendProxyToPamEJB(proxyVO, request, null); 
	
    }
    

    public static void processLegacyTypeInvestigations(String publicHealthCaseUID,NBSSecurityObj nbsSecurityObj ,String selectedInvestigator ,
    		String dateAssigned,HttpSession session,HttpServletRequest request) throws Exception {
    	
    	String userId = nbsSecurityObj.getTheUserProfile().getTheUser().getEntryID();
		Long actUid=null;
		
		InvestigationProxyVO investigationProxyVO = new InvestigationProxyVO(); 
		 NedssUtils nedssUtils = new NedssUtils();
		Object object = nedssUtils.lookupBean(JNDINames.INVESTIGATION_PROXY_EJB);
		InvestigationProxyHome home = (InvestigationProxyHome) PortableRemoteObject.narrow(object, InvestigationProxyHome.class);
		InvestigationProxy investigationProxy = home.create();	  
		investigationProxyVO =  investigationProxy.getInvestigationProxy(Long.valueOf(publicHealthCaseUID), nbsSecurityObj);
		boolean isNewParticipationRequired = true;
		//Change the Participation DT
		Collection<Object> oldParticipationDTCollection=investigationProxyVO.getPublicHealthCaseVO().getTheParticipationDTCollection();
		Iterator<Object> oldParticipationDTIterator=oldParticipationDTCollection.iterator();
		Collection<Object> newParticipationDTList = new ArrayList	<Object>();
		while(oldParticipationDTIterator.hasNext()) {
			ParticipationDT participationDT =(ParticipationDT)oldParticipationDTIterator.next();
			if(NEDSSConstants.PHC_INVESTIGATOR.equalsIgnoreCase(participationDT.getTypeCd())) {
				//Investogator exist . delete this object . 
				if(!(selectedInvestigator.equalsIgnoreCase( participationDT.getSubjectEntityUid().toString())) 
						) {
					participationDT.setItDelete(true);
					newParticipationDTList.add(participationDT);
					isNewParticipationRequired=true;
				}else if(participationDT.getFromTime() ==null|| !(  StringUtils.stringToStrutsTimestamp(dateAssigned).compareTo( participationDT.getFromTime()   ) ==0  )) {
					participationDT.setItDirty(true);
					participationDT.setFromTime(StringUtils.stringToStrutsTimestamp(dateAssigned));
					newParticipationDTList.add(participationDT);
					isNewParticipationRequired=false;
				}else {
					isNewParticipationRequired=false;
				}
			}else {
				participationDT.setItDirty(true);
				newParticipationDTList.add(participationDT);
			}
			
			actUid=participationDT.getActUid();
		}
		//Create new one
		if(isNewParticipationRequired) {
			ParticipationDT newParticipationDT= PageCreateHelper.createParticipation(Long.valueOf(userId), new Timestamp(System.currentTimeMillis()), userId, 
					actUid, selectedInvestigator, "PSN", NEDSSConstants.PHC_INVESTIGATOR, "CASE");
			newParticipationDT.setFromTime(StringUtils.stringToStrutsTimestamp(dateAssigned));
			newParticipationDTList.add(newParticipationDT);
		}
		
		investigationProxyVO.setTheParticipationDTCollection(newParticipationDTList);
		investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().setInvestigatorAssignedTime(
				StringUtils
				.stringToStrutsTimestamp(dateAssigned)
				);
		investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().setItDirty(true);
		investigationProxyVO.setItDirty(true);
		
		String sBeanJndiName = JNDINames.INVESTIGATION_PROXY_EJB;
		MainSessionHolder holder = new MainSessionHolder();
		MainSessionCommand msCommand = holder.getMainSessionCommand(session);
		 Object[] oParams = {  investigationProxyVO };
		 String sMethod = "setInvestigationProxy";
		 msCommand.processRequest(sBeanJndiName, sMethod, oParams);
		
    }
	
	public ArrayList<Object> getInvestigatorDropDowns(Collection<Object> invSummaryVOs) {
		Map<Object, Object>  invMap = new HashMap<Object,Object>();
		boolean addBlank = false;
		if (invSummaryVOs != null) {
			Iterator<Object> iter = invSummaryVOs.iterator();
			while (iter.hasNext()) {
				InvestigationSummaryVO invSummaryVO = (InvestigationSummaryVO) iter
						.next();
				String firstName = invSummaryVO.getInvestigatorFirstName()== null? NEDSSConstants.NO_FIRST_NAME_INVESTIGATOR : invSummaryVO.getInvestigatorFirstName();
				String lastName = invSummaryVO.getInvestigatorLastName() == null? NEDSSConstants.NO_LAST_NAME_INVESTIGATOR : invSummaryVO.getInvestigatorLastName();
				String invName = lastName + ", " +firstName;
				if (invSummaryVO.getInvestigatorLocalId() != null) {
					invMap.put(invSummaryVO.getInvestigatorLocalId(), invName);
				}
				if(invSummaryVO.getInvestigatorLocalId() == null || invSummaryVO.getInvestigatorLocalId().trim().equals("")){
					addBlank = true;
				}
			}

		}
		ArrayList<Object> filterColl  = queueUtil.getUniqueValueFromMap(invMap);
		if(addBlank) {
			DropDownCodeDT cdDT = new DropDownCodeDT();
	        cdDT.setKey(NEDSSConstants.BLANK_KEY);
	        cdDT.setValue(NEDSSConstants.BLANK_INVESTIGATOR_VALUE);
	        filterColl.add(0,cdDT);
		}
		return filterColl;
	}

	public ArrayList<Object> getJurisDropDowns(Collection<Object> invSummaryVOs) {
		Map<Object, Object>  invMap = new HashMap<Object,Object>();
		if (invSummaryVOs != null) {
			Iterator<Object>  iter = invSummaryVOs.iterator();
			while (iter.hasNext()) {
				InvestigationSummaryVO invSummaryVO = (InvestigationSummaryVO) iter
						.next();

				if (invSummaryVO.getJurisdictionCd() != null) {
					invMap.put(invSummaryVO.getJurisdictionCd(), invSummaryVO
							.getJurisdictionDescTxt());
				}

			}

		}

		return queueUtil.getUniqueValueFromMap(invMap);
	}

	public ArrayList<Object> getConditionDropDowns(Collection<Object> invSummaryVOs) {
		Map<Object, Object>  invMap = new HashMap<Object,Object>();
		if (invSummaryVOs != null) {
			Iterator<Object>  iter = invSummaryVOs.iterator();
			while (iter.hasNext()) {
				InvestigationSummaryVO invSummaryVO = (InvestigationSummaryVO) iter
						.next();
				if (invSummaryVO.getCd() != null && invSummaryVO.getConditionCodeText() != null) {
					invMap.put(invSummaryVO.getCd(), invSummaryVO
							.getConditionCodeText());
				}
			}
		}
		return queueUtil.getUniqueValueFromMap(invMap);
	}
    /*
     * We seem to be using two code sets, PHC_CLASS and PHVS_PHC_CLASS.
     * Code 2931005 is coming over with a null caseClassCdTxt value in View File. 
     * Added check for null text but need to address issue.-gt
     * Causes ERROR gov.cdc.nedss.webapp.nbs.action.util.QueueUtil  - Error in String Comparison
     */
	public ArrayList<Object> getCaseStatusDropDowns(Collection<Object> invSummaryVOs) {
		Map<Object, Object>  invMap = new HashMap<Object,Object>();
		if (invSummaryVOs != null) {
			Iterator<Object>  iter = invSummaryVOs.iterator();
			while (iter.hasNext()) {
				InvestigationSummaryVO invSummaryVO = (InvestigationSummaryVO) iter
						.next();
				if (invSummaryVO.getCaseClassCd() != null && invSummaryVO.getCaseClassCodeTxt() != null) {
					invMap.put(invSummaryVO.getCaseClassCd(), invSummaryVO
							.getCaseClassCodeTxt());
				}
				if(invSummaryVO.getCaseClassCd() == null || invSummaryVO.getCaseClassCd().trim().equals("")){
					invMap.put(NEDSSConstants.BLANK_KEY, NEDSSConstants.BLANK_VALUE);
				}
			}
		}
		return queueUtil.getUniqueValueFromMap(invMap);
	}

	public Collection<Object>  getFilteredInvestigation(Collection<Object> invSummaryVOs,
			Map<Object, Object>  searchCriteriaMap) {
		
    	String[] inv = (String[]) searchCriteriaMap.get("INVESTIGATOR");
		String[] juris = (String[]) searchCriteriaMap.get("JURISDICTION");
		String[] cond = (String[]) searchCriteriaMap.get("CONDITION");
		String[] status = (String[]) searchCriteriaMap.get("CASESTATUS");
		String[] startDate = (String[]) searchCriteriaMap.get("STARTDATE");
		String[] notif = (String[]) searchCriteriaMap.get("NOTIFICATION");
		String[] invStatus = (String[]) searchCriteriaMap.get("INVSTATUS");
		String filterPatient = null;
		if(searchCriteriaMap.get("SearchText1_FILTER_TEXT")!=null){
			filterPatient = (String) searchCriteriaMap.get("SearchText1_FILTER_TEXT");
		}
		String filterInvestigationId = null;
		if(searchCriteriaMap.get("SearchText2_FILTER_TEXT")!=null){
			filterInvestigationId = (String) searchCriteriaMap.get("SearchText2_FILTER_TEXT");
		}

		Map<Object, Object>  invMap = new HashMap<Object,Object>();
		Map<Object, Object>  jurisMap = new HashMap<Object,Object>();
		Map<Object, Object>  condMap = new HashMap<Object,Object>();
		Map<Object, Object>  statusMap = new HashMap<Object,Object>();
		Map<Object, Object>  dateMap = new HashMap<Object,Object>();
		Map<Object, Object>  notifMap = new HashMap<Object,Object>();
		Map<Object, Object>  invStatusMap = new HashMap<Object,Object>();
		
		if (inv != null && inv.length > 0)
			invMap = queueUtil.getMapFromStringArray(inv);
		if (juris != null && juris.length > 0)
			jurisMap = queueUtil.getMapFromStringArray(juris);
		if (cond != null && cond.length > 0)
			condMap = queueUtil.getMapFromStringArray(cond);
		if (status != null && status.length > 0)
			statusMap = queueUtil.getMapFromStringArray(status);
		if (startDate != null && startDate.length >0)
			dateMap = queueUtil.getMapFromStringArray(startDate);
		if (notif != null && notif.length >0)
			notifMap = queueUtil.getMapFromStringArray(notif);
		if (invStatus != null && invStatus.length > 0)
			invStatusMap = queueUtil.getMapFromStringArray(invStatus);		
		
		if(invMap != null && invMap.size()>0)
			invSummaryVOs = filterInvestigationsbyInvestigator(
				invSummaryVOs, invMap);
		if (jurisMap != null && jurisMap.size()>0)
			invSummaryVOs = filterInvestigationsbyJurisdiction(
				invSummaryVOs, jurisMap);
		if (condMap != null && condMap.size()>0)
			invSummaryVOs = filterInvestigationsbyCondition(
				invSummaryVOs, condMap);
		if (statusMap != null && statusMap.size()>0)
			invSummaryVOs = filterInvestigationsbyCaseStatus(
				invSummaryVOs, statusMap);
		if(dateMap != null && dateMap.size()>0)
			invSummaryVOs = filterInvestigationsbyStartdate(invSummaryVOs,dateMap);
		if(notifMap != null && notifMap.size()>0)
			invSummaryVOs = filterInvestigationsbyNotifRecordStatus(invSummaryVOs,notifMap);
		if(filterPatient!= null)
			invSummaryVOs = filterByText(invSummaryVOs, filterPatient, NEDSSConstants.INV_PATIENT);
		if(filterInvestigationId!= null) {
			invSummaryVOs = filterByText(invSummaryVOs,filterInvestigationId, NEDSSConstants.INV_LOCAL_ID);
		}
		if (invStatusMap != null && invStatusMap.size()>0)
				invSummaryVOs = filterInvestigationsbyInvestigationStatus(invSummaryVOs, invStatusMap);
		
		return invSummaryVOs;
		
	}

	public Collection<Object>  filterByText(
			Collection<Object>  invSummaryVOs, String filterByText,String column) {
		Collection<Object>  newInvColl = new ArrayList<Object> ();
		try{
		if (invSummaryVOs != null) {
			Iterator<Object> iter = invSummaryVOs.iterator();
			while (iter.hasNext()) {
				InvestigationSummaryVO invSummaryVO = (InvestigationSummaryVO) iter.next();
				if(column.equals(NEDSSConstants.INV_PATIENT) && invSummaryVO.getPatientFullName() != null 
						&& invSummaryVO.getPatientFullName().toUpperCase().contains(filterByText.toUpperCase())){
					newInvColl.add(invSummaryVO);
				}
				if(column.equals(NEDSSConstants.INV_LOCAL_ID) && invSummaryVO.getLocalId()!= null
					 && invSummaryVO.getLocalId().toUpperCase().contains(filterByText.toUpperCase())){
					newInvColl.add(invSummaryVO);
					 }
			}
		}
		}catch(Exception ex){
			 logger.error("Error filtering the filterByText : "+ex.getMessage());
			 throw new NEDSSSystemException(ex.getMessage());
		}
		return newInvColl;
	}
	
	public Collection<Object>  filterInvestigationsbyInvestigator(
			Collection<Object>  invSummaryVOs, Map<Object,Object> investgatorMap) {
		Collection<Object>  newInvColl = new ArrayList<Object> ();
		if (invSummaryVOs != null) {
			Iterator<Object>  iter = invSummaryVOs.iterator();
			while (iter.hasNext()) {
				InvestigationSummaryVO invSummaryVO = (InvestigationSummaryVO) iter
						.next();
				if (invSummaryVO.getInvestigatorLocalId() != null
						&& investgatorMap != null
						&& investgatorMap.containsKey(invSummaryVO
								.getInvestigatorLocalId())) {
					newInvColl.add(invSummaryVO);
				}
				if(invSummaryVO.getInvestigatorLocalId() == null || invSummaryVO.getInvestigatorLocalId().trim().equals("")){
					if(investgatorMap != null && investgatorMap.containsKey(NEDSSConstants.BLANK_KEY)){
						newInvColl.add(invSummaryVO);
					}
				}

			}

		}
		return newInvColl;

	}

	public Collection<Object>  filterInvestigationsbyJurisdiction(
			Collection<Object>  invSummaryVOs, Map<Object,Object> jurisMap) {
		Collection<Object>  newInvColl = new ArrayList<Object> ();
		if (invSummaryVOs != null) {
			Iterator<Object>  iter = invSummaryVOs.iterator();
			while (iter.hasNext()) {
				InvestigationSummaryVO invSummaryVO = (InvestigationSummaryVO) iter
						.next();
				if (invSummaryVO.getJurisdictionCd() != null
						&& jurisMap != null
						&& jurisMap.containsKey(invSummaryVO
								.getJurisdictionCd())) {
					newInvColl.add(invSummaryVO);
				}

			}

		}
		return newInvColl;

	}

	public Collection<Object>  filterInvestigationsbyCaseStatus(
			Collection<Object>  invSummaryVOs, Map<Object,Object> statusMap) {
		Collection<Object>  newInvColl = new ArrayList<Object> ();
		if (invSummaryVOs != null) {
			Iterator<Object>  iter = invSummaryVOs.iterator();
			while (iter.hasNext()) {
				InvestigationSummaryVO invSummaryVO = (InvestigationSummaryVO) iter
						.next();
				if (invSummaryVO.getCaseClassCd() != null && statusMap != null
						&& statusMap.containsKey(invSummaryVO.getCaseClassCd())) {
					newInvColl.add(invSummaryVO);
				}
				if(invSummaryVO.getCaseClassCd() == null || invSummaryVO.getCaseClassCd().trim().equals("")){
					if(statusMap != null && statusMap.containsKey(NEDSSConstants.BLANK_KEY)){
						newInvColl.add(invSummaryVO);
					}
				}

			}

		}
		return newInvColl;

	}
	
	public Collection<Object>  filterInvestigationsbyStartdate(Collection<Object> invSummaryVOs, Map<Object, Object> dateMap) {
		Map<Object, Object>  newInvMap = new HashMap<Object,Object>();
		String strDateKey = null;
		if (invSummaryVOs != null) {
			Iterator<Object>  iter = invSummaryVOs.iterator();
			while (iter.hasNext()) {
				InvestigationSummaryVO invSummaryVO = (InvestigationSummaryVO) iter
						.next();
				if (invSummaryVO.getActivityFromTime() != null && dateMap != null
						&& (dateMap.size()>0 )) {
					Collection<Object>  dateSet = dateMap.keySet();
					if(dateSet != null){
						Iterator<Object>  iSet = dateSet.iterator();
					while (iSet.hasNext()){
						 strDateKey = (String)iSet.next();
						if(!(strDateKey.equals(NEDSSConstants.DATE_BLANK_KEY))){
                    	   if(queueUtil.isDateinRange(invSummaryVO.getActivityFromTime(),strDateKey)){
                    		   newInvMap.put(invSummaryVO.getLocalId(), invSummaryVO);
                    	   }	
                           		
						}  
                       }
					  }
					}
		
				if(invSummaryVO.getActivityFromTime() == null || invSummaryVO.getActivityFromTime().equals("")){
					if(dateMap != null && dateMap.containsKey(NEDSSConstants.DATE_BLANK_KEY)){
						 newInvMap.put(invSummaryVO.getLocalId(), invSummaryVO);
					}
				}

			}
		} 	

		
		return convertInvMaptoColl(newInvMap);

	}
	
   private Collection<Object>  convertInvMaptoColl(Map<Object, Object>  invMap){
	   Collection<Object>  invColl = new ArrayList<Object> ();
	   if(invMap !=null && invMap.size()>0){
		   Collection<Object>  invKeyColl = invMap.keySet();
		  Iterator<Object>  iter = invKeyColl.iterator();
		   while(iter.hasNext()){
			   String invKey = (String)iter.next();
			   invColl.add(invMap.get(invKey));
		   }
	   }
	   return invColl;
   }

	public Collection<Object>  filterInvestigationsbyCondition(Collection<Object> invSummaryVOs,
			Map<Object, Object>  conditionMap) {
		Collection<Object>  newInvColl = new ArrayList<Object> ();
		if (invSummaryVOs != null) {
			Iterator<Object>  iter = invSummaryVOs.iterator();
			while (iter.hasNext()) {
				InvestigationSummaryVO invSummaryVO = (InvestigationSummaryVO) iter
						.next();
				if (invSummaryVO.getCd() != null && conditionMap != null
						&& conditionMap.containsKey(invSummaryVO.getCd())) {
					newInvColl.add(invSummaryVO);
				}

			}

		}
		return newInvColl;

	}

	/**
	 * Filters Queue Collection<Object>  by Notification RecordStatusCd
	 * @param invSummaryVOs
	 * @param notifRecStatMap
	 * @return
	 */
	public Collection<Object>  filterInvestigationsbyNotifRecordStatus(Collection<Object> invSummaryVOs, Map<Object,Object> notifRecStatMap) {
		Collection<Object>  newInvColl = new ArrayList<Object> ();
		if (invSummaryVOs != null) {
			Iterator<Object>  iter = invSummaryVOs.iterator();
			while (iter.hasNext()) {
				InvestigationSummaryVO invSummaryVO = (InvestigationSummaryVO) iter.next();
				String notifRecStatusCd = invSummaryVO.getNotifRecordStatusCd();
				if (notifRecStatusCd != null && notifRecStatMap != null && notifRecStatMap.containsKey(notifRecStatusCd)) {
					newInvColl.add(invSummaryVO);
				}
				if(notifRecStatusCd == null || (notifRecStatusCd != null && notifRecStatusCd.trim().equals(""))){
					if(notifRecStatMap != null && notifRecStatMap.containsKey(NEDSSConstants.BLANK_KEY)){
						newInvColl.add(invSummaryVO);
					}
				}				
			}
		}		
		return newInvColl;
	}	

	public String getSortCriteria(String sortOrder, String methodName){
		String sortOrdrStr = null;
		if(methodName != null) {
			if(methodName.equals("getActivityFromTime"))
				sortOrdrStr = "Start Date";
			else if(methodName.equals(NEDSSConstants.GETINVESTIGATORFULLNAME))
				sortOrdrStr = "Investigator";
			else if(methodName.equals("getJurisdictionDescTxt"))
				sortOrdrStr = "Jurisdiction";
			else if(methodName.equals("getPatientFullName"))
				sortOrdrStr = "Patient";
			else if(methodName.equals("getConditionCodeText"))
				sortOrdrStr = "Condition";
			else if(methodName.equals("getCaseClassCodeTxt"))				
				sortOrdrStr = "CaseStatus";				
			else if(methodName.equals("getNotifRecordStatusCd"))				
				sortOrdrStr = "Notification";
			else if(methodName.equals("getLocalId"))				
				sortOrdrStr = "Investigation ID";
			
			
		} else {
			sortOrdrStr = "Start Date";
		}
		
		if(sortOrder == null || (sortOrder != null && sortOrder.equals("1")))
			sortOrdrStr = sortOrdrStr+" in ascending order ";
		else if(sortOrder != null && sortOrder.equals("2"))
			sortOrdrStr = sortOrdrStr+" in descending order ";

		return sortOrdrStr;
			
	}
	
	/**
	 * Builds a list of dropdown values for Notification recordstatusCd field
	 * @return
	 */
	public ArrayList<Object> getNotificationValues() {
		ArrayList<Object> filterColl = new ArrayList<Object> ();
		
		DropDownCodeDT cdDT = new DropDownCodeDT();
	    cdDT.setKey(NEDSSConstants.BLANK_KEY);
		cdDT.setValue(NEDSSConstants.BLANK_VALUE);
		filterColl.add(cdDT);
		
		DropDownCodeDT cdDT1 = new DropDownCodeDT();
	    cdDT1.setKey("APPROVED");
		cdDT1.setValue("Approved");
		filterColl.add(cdDT1);
		DropDownCodeDT cdDT2 = new DropDownCodeDT();
	    cdDT2.setKey("COMPLETED");
		cdDT2.setValue("Completed");
		filterColl.add(cdDT2);
		DropDownCodeDT cdDT3 = new DropDownCodeDT();
	    cdDT3.setKey("MSG_FAIL");
		cdDT3.setValue("Failed");
		filterColl.add(cdDT3);
		DropDownCodeDT cdDT4 = new DropDownCodeDT();
	    cdDT4.setKey("PEND_APPR");
		cdDT4.setValue("Pending");
		filterColl.add(cdDT4);
		DropDownCodeDT cdDT5 = new DropDownCodeDT();
	    cdDT5.setKey("REJECTED");
		cdDT5.setValue("Rejected");
		filterColl.add(cdDT5);
		return filterColl;
	}
	
	public ArrayList<Object> getNotificationValuesfromColl(Collection<Object> invSummaryVOs) {
		Map<Object, Object>  invMap = new HashMap<Object,Object>();
		if (invSummaryVOs != null) {
			Iterator<Object>  iter = invSummaryVOs.iterator();
			while (iter.hasNext()) {
				InvestigationSummaryVO invSummaryVO = (InvestigationSummaryVO) iter
						.next();
				if (invSummaryVO.getNotifRecordStatusCd() != null) {
					invMap.put(invSummaryVO.getNotifRecordStatusCd(), invSummaryVO.getNotifStatusTransCd());
				}
				if(invSummaryVO.getNotifRecordStatusCd() == null || (invSummaryVO.getNotifRecordStatusCd() !=null && invSummaryVO.getNotifRecordStatusCd().trim().equals(""))){
					invMap.put(NEDSSConstants.BLANK_KEY, NEDSSConstants.BLANK_VALUE);
				}
			}
		}
		return queueUtil.getUniqueValueFromMap(invMap);
	}
	
	/*
	 * Method is same as getInvestigatorDropDowns without the local id check.
	 * Uses the publicHealthCaseUid instead
	 */
	public ArrayList<Object> getInvestigatorDropDownsForAssociated(Collection<Object> invSummaryVOs) {
		Map<Object, Object>  invMap = new HashMap<Object,Object>();
		if (invSummaryVOs != null) {
			Iterator<Object> iter = invSummaryVOs.iterator();
			while (iter.hasNext()) {
				InvestigationSummaryVO invSummaryVO = (InvestigationSummaryVO) iter
						.next();
				String firstName = invSummaryVO.getInvestigatorFirstName()== null? NEDSSConstants.NO_FIRST_NAME_INVESTIGATOR : invSummaryVO.getInvestigatorFirstName();
				String lastName = invSummaryVO.getInvestigatorLastName() == null? NEDSSConstants.NO_LAST_NAME_INVESTIGATOR : invSummaryVO.getInvestigatorLastName();
				String invName = lastName + ", " +firstName;
				invMap.put(invName, invName);
				invSummaryVO.setInvestigatorLocalId(invName);
			}
		}

		return queueUtil.getUniqueValueFromMap(invMap);
	}
	
	/*
	 * Method is to get the Open Or Close investigations status
	 */
	public ArrayList<Object> getInvestigationStatusDropDowns(Collection<Object> invSummaryVOs) {
		Map<Object, Object>  invMap = new HashMap<Object,Object>();
		
		CachedDropDownValues cddv = new CachedDropDownValues();
		if (invSummaryVOs != null) {
			Iterator<Object> iter = invSummaryVOs.iterator();
			while (iter.hasNext()) {
				InvestigationSummaryVO invSummaryVO = (InvestigationSummaryVO) iter
						.next();
				String currentStateDesc = null;
				if (invSummaryVO.getCurrProcessStateCd() != null)
					currentStateDesc = cddv
							.getCodeShortDescTxt(
									invSummaryVO.getCurrProcessStateCd(),
									NEDSSConstants.CM_PROCESS_STAGE);
				if (invSummaryVO.getInvestigationStatusDescTxt() != null) {
					invMap.put(
							currentStateDesc == null ? invSummaryVO
									.getInvestigationStatusDescTxt()
									: invSummaryVO
											.getInvestigationStatusDescTxt()
											+ "(" + currentStateDesc + ")",
							currentStateDesc == null ? invSummaryVO
									.getInvestigationStatusDescTxt()
									: invSummaryVO
											.getInvestigationStatusDescTxt()
											+ "(" + currentStateDesc + ")");
				}
				if (invSummaryVO.getInvestigationStatusDescTxt() == null) {
					invMap.put(NEDSSConstants.BLANK_KEY,
							NEDSSConstants.BLANK_VALUE);
				}
			}
		}

		return queueUtil.getUniqueValueFromMap(invMap);
	}
	
	/*
	 * Filter by Investigation Status (Open, Closed or Null)
	 */
	
	public Collection<Object>  filterInvestigationsbyInvestigationStatus(
			Collection<Object>  invSummaryVOs, Map<Object,Object> invStatusMap) {
		Collection<Object>  newInvColl = new ArrayList<Object> ();
		CachedDropDownValues cddv = new CachedDropDownValues();
		if (invSummaryVOs != null) {
			Iterator<Object>  iter = invSummaryVOs.iterator();
			while (iter.hasNext()) {
				InvestigationSummaryVO invSummaryVO = (InvestigationSummaryVO) iter
						.next();
				String currentStateDesc = null;
				if (invSummaryVO.getCurrProcessStateCd() != null)
					currentStateDesc = cddv
							.getCodeShortDescTxt(
									invSummaryVO.getCurrProcessStateCd(),
									NEDSSConstants.CM_PROCESS_STAGE);
				
				if (invSummaryVO.getInvestigationStatusDescTxt() != null
						&& invStatusMap != null
						&& invStatusMap.containsKey(currentStateDesc == null ? cddv
								.getCodeShortDescTxt(
										invSummaryVO.getInvestigationStatusCd(),
										NEDSSConstants.PHC_IN_STS): cddv
										.getCodeShortDescTxt(
												invSummaryVO.getInvestigationStatusCd(),
												NEDSSConstants.PHC_IN_STS)
										+ "(" + currentStateDesc + ")")) {
					newInvColl.add(invSummaryVO);
				}

			}

		}
		return newInvColl;

	}
	
}
