package gov.cdc.nedss.act.ctcontact.ejb.dao;

import gov.cdc.nedss.act.ctcontact.dt.CTContactDT;
import gov.cdc.nedss.act.ctcontact.dt.CTContactHistDT;
import gov.cdc.nedss.act.ctcontact.dt.CTContactNoteDT;
import gov.cdc.nedss.act.ctcontact.vo.CTContactVO;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.nbsactentity.dao.NbsActEntityDAO;
import gov.cdc.nedss.nbsactentity.dt.NbsActEntityDT;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.act.PageActProxyVO;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CTContactRootDAO {
	static final LogUtils logger = new LogUtils(CTContactRootDAO.class.getName());

	public long create(CTContactVO contactVO)  {
		CTContactDT ctContactDT = null;
		try{
			CTContactDAOImpl ctContactDAOImpl = new CTContactDAOImpl();
			ctContactDT = ctContactDAOImpl.insertCTContact(contactVO.getcTContactDT());
			CTContactAnswerDAO ctContactAnswerDAO = new CTContactAnswerDAO();
			Collection<Object> ctContactAnswerDTCollection = new ArrayList<Object> ();
			if(contactVO.getCtContactAnswerDTMap() != null) {
				ctContactAnswerDTCollection= contactVO.getCtContactAnswerDTMap().values();
				ctContactAnswerDAO.storeCTContactDTCollection(ctContactAnswerDTCollection, ctContactDT);
			}
			
			if(contactVO.getRepeatingAnswerDTMap()!=null){
				ctContactAnswerDTCollection= contactVO.getRepeatingAnswerDTMap().values();
				ctContactAnswerDAO.storeCTContactDTCollection(ctContactAnswerDTCollection, ctContactDT);
			}
			
			Iterator<Object> anIterator = null;
			NbsActEntityDT actEntityDT = null;
			
			if(contactVO.getActEntityDTCollection()!=null)
			{
				for (anIterator = contactVO.getActEntityDTCollection().iterator(); anIterator.hasNext();) {
					actEntityDT = (NbsActEntityDT) anIterator.next();
					if (actEntityDT.getTypeCd().equalsIgnoreCase(NEDSSConstants.CONTACT_ENTITY)) {
						actEntityDT.setEntityUid(ctContactDT.getSubjectEntityUid());
						break;
					}
				}
			}
			
			storeCTActEntityContact(contactVO.getActEntityDTCollection(), ctContactDT);
	
			CTContactNoteDAO ctContactNoteDAO = new CTContactNoteDAO();
			CTContactNoteDT ctContactNoteDT = null;
			if(contactVO.getNoteDTCollection()!=null) {
				for (anIterator = contactVO.getNoteDTCollection().iterator(); anIterator.hasNext();) {
					ctContactNoteDT = (CTContactNoteDT) anIterator.next();
					if (ctContactNoteDT.isItNew())
						ctContactNoteDAO.insertCTContactNote(ctContactNoteDT);
				}
			}
		
		}catch(Exception ex){
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString(), ex);
		}
		return ctContactDT.getCtContactUid().longValue();
	}

	public Long findByPrimaryKey(long ctContactUid) {
		try{
			CTContactDAOImpl ctContactDAOImpl = new CTContactDAOImpl();
			boolean contactExisits = ctContactDAOImpl.ctContactExists(ctContactUid);
			if (contactExisits)
	            return (new Long(ctContactUid));
	        else
	            logger.error("Primary key not found in CT_CONTACT TABLE: " + ctContactUid);
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(),ex);
			throw new NEDSSSystemException(ex.toString(), ex);
		}
        return null;
	}

	public void insertNBSContactHist(CTContactVO oldContactVO, CTContactVO contactVO, String fromAction) {
		try{
			CTContactDAOImpl ctContactDAOImpl = new CTContactDAOImpl();
			CTContactDT ctContactDT =(CTContactDT)oldContactVO.getcTContactDT();
			ctContactDAOImpl.insertNBSContactHist(ctContactDT);
			CTContactAnswerDAO ctContactAnswerDAO = new CTContactAnswerDAO();
			if(!contactVO.isAssociateDissasociate())//To avoid modifying the ct_contact_answer table in case we are associating/disassociating
				ctContactAnswerDAO.insertCTContactAnswerHistoryDTCollection(oldContactVO, fromAction);
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(),ex);
			throw new NEDSSSystemException(ex.toString(), ex);
		}
	}

	public CTContactVO loadObject(long ctContactUid) {
		CTContactVO ctContactVO = new CTContactVO();
		try{
			CTContactDAOImpl ctContactDAOImpl = new CTContactDAOImpl();
			CTContactDT ctContactDT = ctContactDAOImpl.selectCTContact(ctContactUid);
			ctContactDT.setItNew(false);
			ctContactDT.setItDirty(false);
			ctContactVO.setcTContactDT(ctContactDT);
			
			CTContactAnswerDAO ctContactAnswerDAO = new CTContactAnswerDAO();
			Map<Object,Object> ctContactAnswerDTMap = ctContactAnswerDAO.getCTAnswerDTCollection(ctContactUid);
			if(ctContactAnswerDTMap!=null && ctContactAnswerDTMap.get(NEDSSConstants.NON_REPEATING_QUESTION)!=null)
				ctContactVO.setCtContactAnswerDTMap((HashMap<Object, Object>)ctContactAnswerDTMap.get(NEDSSConstants.NON_REPEATING_QUESTION));
			if(ctContactAnswerDTMap!=null && ctContactAnswerDTMap.get(NEDSSConstants.REPEATING_QUESTION)!=null)
				ctContactVO.setRepeatingAnswerDTMap((HashMap<Object, Object>)ctContactAnswerDTMap.get(NEDSSConstants.REPEATING_QUESTION));
			NbsActEntityDAO nbsCaseEntityDAO = new NbsActEntityDAO();
			Collection<Object> pamEntityDTCollection =nbsCaseEntityDAO.getActEntityDTCollection(ctContactUid);
			ctContactVO.setActEntityDTCollection(pamEntityDTCollection);
			CTContactNoteDAO ctContactNoteDAO = new CTContactNoteDAO();
			Collection<Object> noteDTCollection = ctContactNoteDAO.getContactNoteCollection(ctContactUid);
			ctContactVO.setNoteDTCollection(noteDTCollection);
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(),ex);
			throw new NEDSSSystemException(ex.toString(), ex);
		}
		return ctContactVO;
	}
	
	public static void remove(CTContactVO contactVO) {
		try{
			CTContactDAOImpl ctContactDAO = new CTContactDAOImpl();
			ctContactDAO.deleteCTContact(contactVO.getcTContactDT());
			
			CTContactAnswerDAO ctContactAnswerDAO = new CTContactAnswerDAO();
			ctContactAnswerDAO.logDelCTContactAnswerDTCollection(contactVO);
			NbsActEntityDAO nbsCaseEntityDAO = new NbsActEntityDAO();
			nbsCaseEntityDAO.logDelActEntityDTCollection(contactVO.getActEntityDTCollection(), contactVO.getcTContactDT());
			
			CTContactAttachmentDAO ctContactAttachmentDAO = new CTContactAttachmentDAO();
			ctContactAttachmentDAO.removeAllCTContactAttachments(contactVO.getcTContactDT().getCtContactUid());
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(),ex);
			throw new NEDSSSystemException(ex.toString(), ex);
		}
	}

	public static void updateCTContact(CTContactVO contactVO) throws NEDSSConcurrentDataException {
		
		try{
			CTContactDAOImpl ctContactDAOImpl = new CTContactDAOImpl();
			CTContactAnswerDAO ctContactAnswerDAO = new CTContactAnswerDAO();
			if(contactVO.getcTContactDT().getRecordStatusCd().equalsIgnoreCase(NEDSSConstants.RECORD_STATUS_LOGICAL_DELETE)) {
				remove(contactVO);
			} else {
				CTContactDT ctContactDT = ctContactDAOImpl.updateCTContact(contactVO.getcTContactDT());
			
				Collection<Object> ctContactAnswerDTCollection = new ArrayList<Object>();
			
				if (contactVO.getCtContactAnswerDTMap() != null) {
					ctContactAnswerDTCollection = contactVO.getCtContactAnswerDTMap().values();
					ctContactAnswerDAO.storeCTContactDTCollection(ctContactAnswerDTCollection, ctContactDT);
				}
				if(contactVO.getRepeatingAnswerDTMap()!=null){
					ctContactAnswerDTCollection= contactVO.getRepeatingAnswerDTMap().values();
					ctContactAnswerDAO.storeCTContactDTCollection(ctContactAnswerDTCollection, ctContactDT);
				}
				storeCTActEntityContact(contactVO.getActEntityDTCollection(), ctContactDT);
	
			}
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(),ex);
			throw new NEDSSSystemException(ex.toString(), ex);
		}
	}
	
	public static void storeCTContactNotes(Collection<Object> ctContactNoteDTCollection, CTContactDT ctContactDT) {
		try{
			if(ctContactNoteDTCollection!=null) {
				Iterator<Object> anIterator = null;
				CTContactNoteDAO ctContactNoteDAO = new CTContactNoteDAO();
				CTContactNoteDT ctContactNoteDT = null;
	
				for (anIterator = ctContactNoteDTCollection.iterator(); anIterator.hasNext();) {
					ctContactNoteDT = (CTContactNoteDT) anIterator.next();
					if (ctContactNoteDT.isItNew())
						ctContactNoteDT.setCtContactUid(ctContactDT.getCtContactUid());
						ctContactNoteDAO.insertCTContactNote(ctContactNoteDT);
				}
			}
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(),ex);
			throw new NEDSSSystemException(ex.toString(), ex);
		}
	}

	public static void storeCTActEntityContact(Collection<Object>  nbsActEntityDTCollection,RootDTInterface rootDTInterface) {
		try{
			NbsActEntityDAO nbsCaseEntityDAO = new NbsActEntityDAO();
			
			nbsCaseEntityDAO.storeActEntityDTCollection(nbsActEntityDTCollection, rootDTInterface);
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(),ex);
			throw new NEDSSSystemException(ex.toString(), ex);
		}
	}
	public void manageContactsForChangeCondition(Long oldInvestigationUID, Long newInvestigationUid,
			Long revisionUid, String programArea, Long programJurisdictionOid){
		try{
			CTContactDAOImpl contactDAOImpl = new CTContactDAOImpl();
			contactDAOImpl.updateContactsForNewInvestigation(oldInvestigationUID, newInvestigationUid, 
					revisionUid, programArea, programJurisdictionOid);
			contactDAOImpl.deLinkContactsForOldInvestigation(oldInvestigationUID);
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(),ex);
			throw new NEDSSSystemException(ex.toString(), ex);
		}
	}
	
	/**PKS Not required
	public void manageContactsForMergeInvestigation(Long oldInvestigationUID, Long newInvestigationUid,
			Long revisionUid){
		try{
			CTContactDAOImpl contactDAOImpl = new CTContactDAOImpl();
			contactDAOImpl.updateContactsForSurvivingInvestigation(oldInvestigationUID, newInvestigationUid, 
					revisionUid);
			contactDAOImpl.updatePatientNamedByContactsForSurvivingInvestigation(oldInvestigationUID, newInvestigationUid);
			//contactDAOImpl.deLinkContactsForOldInvestigation(oldInvestigationUID); 
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(),ex);
			throw new NEDSSSystemException(ex.toString(), ex);
		}
	}*/
}
