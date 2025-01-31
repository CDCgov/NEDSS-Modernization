package gov.cdc.nedss.act.clinicaldocument.ejb.dao;

import gov.cdc.nedss.act.actid.dao.ActivityIdDAOImpl;
import gov.cdc.nedss.act.clinicaldocument.dt.ClinicalDocumentDT;
import gov.cdc.nedss.act.clinicaldocument.vo.ClinicalDocumentVO;
import gov.cdc.nedss.association.dao.ActRelationshipDAOImpl;
import gov.cdc.nedss.association.dao.ParticipationDAOImpl;
import gov.cdc.nedss.association.dt.ActRelationshipDT;
import gov.cdc.nedss.association.dt.ParticipationDT;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.locator.dao.ActivityLocatorParticipationDAOImpl;
import gov.cdc.nedss.systemservice.util.NEDSSDAOFactory;
import gov.cdc.nedss.util.BMPBase;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;

import java.util.Collection;
import java.util.Iterator;


public class ClinicalDocumentRootDAOImpl extends BMPBase
{
    //Fof logging
    static final LogUtils logger = new LogUtils(ClinicalDocumentRootDAOImpl.class.getName());

    private ClinicalDocumentVO cdVO = null;
    private long clinicaldocumentUID;
    private  ClinicalDocumentDAOImpl clinicaldocumentDAO = null;

    private  ActivityIdDAOImpl activityIdDAO = null;
    private  ActivityLocatorParticipationDAOImpl activityLocatorParticipationDAO = null;
    private  ActRelationshipDAOImpl clinicalDocumentActRelationshipDAOImpl = null;
    private  ParticipationDAOImpl clinicalDocumentParticipationDAOImpl = null;
    public ClinicalDocumentRootDAOImpl()
    {
    }


    public long create(Object obj) throws  NEDSSSystemException
    {
    	try{
	        this.cdVO = (ClinicalDocumentVO)obj;
	
	        /**
	        *  Inserts ClinicalDocumentDT object
	        */
	
	        if (this.cdVO != null)
	        clinicaldocumentUID = insertClinicalDocument(this.cdVO);
	        logger.debug("ClinicalDocument UID = " + clinicaldocumentUID);
	        (this.cdVO.getTheClinicalDocumentDT()).setClinicalDocumentUid(new Long(clinicaldocumentUID));
	
	
	        /**
	        * Inserts ActivityIdDT collection
	        */
	
	        if (this.cdVO != null && this.cdVO.getTheActivityIdDTCollection() != null)
	        insertActivityIDs(this.cdVO);
	
	        /**
	        * Inserts ActivityLocatorParticipationDT collection
	        */
	
	        if (this.cdVO != null && this.cdVO.getTheActivityLocatorParticipationDTCollection() != null)
	        insertActivityLocatorParticipations(this.cdVO);
	
	        this.cdVO.setItNew(false);
	        this.cdVO.setItDirty(false);
	        return ((((ClinicalDocumentVO)obj).getTheClinicalDocumentDT().getClinicalDocumentUid()).longValue());
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    public void store(Object obj) throws  NEDSSSystemException, NEDSSConcurrentDataException
    {
    	try{
	        this.cdVO = (ClinicalDocumentVO)obj;
	
	        /**
	        *  Updates ClinicalDocumentDT object
	        */
	        if(this.cdVO.getTheClinicalDocumentDT() != null && this.cdVO.getTheClinicalDocumentDT().isItNew())
	        {
	            insertClinicalDocument(this.cdVO);
	            this.cdVO.getTheClinicalDocumentDT().setItNew(false);
	            this.cdVO.getTheClinicalDocumentDT().setItDirty(false);
	        }
	        else if(this.cdVO.getTheClinicalDocumentDT() != null && this.cdVO.getTheClinicalDocumentDT().isItDirty())
	        {
	            updateClinicalDocument(this.cdVO);
	            this.cdVO.getTheClinicalDocumentDT().setItDirty(false);
	            this.cdVO.getTheClinicalDocumentDT().setItNew(false);
	        }
	
	
	
	
	        /**
	         * Updates activity ids collection
	         */
	        if(this.cdVO.getTheActivityIdDTCollection() != null)
	        {
	            updateActivityIDs(this.cdVO);
	        }
	
	        /**
	        * Updates activity locator participations collection
	        */
	        if (this.cdVO.getTheActivityLocatorParticipationDTCollection() != null)
	        {
	            updateActivityLocatorParticipations(this.cdVO);
	        }
    	}catch(NEDSSSystemException ex){
    		logger.fatal("NEDSSSystemException  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}catch(NEDSSConcurrentDataException cdex){
    		logger.fatal("NEDSSConcurrentDataException  = "+cdex.getMessage(), cdex);
    		throw new NEDSSSystemException(cdex.toString());
    	}
    }

    public void remove(long clinicaldocumentUID) throws  NEDSSSystemException
    {
    	try{
	        /**
	        * Removes ActivityLocatorParticipationDT collection
	        */
	
	//        removeActivityLocatorParticipations(clinicaldocumentUID);
	
	
	        /**
	        * Removes ActivityIdDT Collection
	        */
	
	        removeActivityIDs(clinicaldocumentUID);
	
	
	        /**
	        *  Removes ClinicalDocumentDT
	        */
	
	        removeClinicalDocument(clinicaldocumentUID);
    	}catch(NEDSSSystemException ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    public Object loadObject(long clinicaldocumentUID) throws  NEDSSSystemException
    {
    	try{
	        this.cdVO = new ClinicalDocumentVO();
	
	        /**
	        *  Selects ClinicalDocumentDT object
	        */
	
	        ClinicalDocumentDT pDT = selectClinicalDocument(clinicaldocumentUID);
	        this.cdVO.setTheClinicalDocumentDT(pDT);
	
	        /**
	        * Selects ActivityIdDT collection
	        */
	
	        Collection<Object> idColl = selectActivityIDs(clinicaldocumentUID);
	        this.cdVO.setTheActivityIdDTCollection(idColl);
	
	        /**
	        * Selects ActivityLocatorParticipationDT collection
	        */
	
	        Collection<Object> elpColl = selectActivityLocatorParticipations(clinicaldocumentUID);
	        this.cdVO.setTheActivityLocatorParticipationDTCollection(elpColl);
	
	        //Selects ActRelationshiopDTcollection
	        Collection<Object> actColl = selectActRelationshipDTCollection(clinicaldocumentUID);
	        this.cdVO.setTheActRelationshipDTCollection(actColl);
	
	        //SelectsParticipationDTCollection
	        Collection<Object> parColl = selectParticipationDTCollection(clinicaldocumentUID);
	        this.cdVO.setTheParticipationDTCollection(parColl);
	
	        this.cdVO.setItNew(false);
	        this.cdVO.setItDirty(false);
	        return this.cdVO;
    	}catch(NEDSSSystemException ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    public Long findByPrimaryKey(long clinicaldocumentUID) throws  NEDSSSystemException
    {
    	try{
	        /**
	         * Finds clinicaldocument object
	         */
	        Long clinicaldocumentPK = findClinicalDocument(clinicaldocumentUID);
	        logger.debug("Done find by primarykey!");
	        return clinicaldocumentPK;
    	}catch(NEDSSSystemException ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    private long insertClinicalDocument(ClinicalDocumentVO cdVO) throws  NEDSSSystemException
    {
        try
        {
            if(clinicaldocumentDAO == null)
            {
                clinicaldocumentDAO = (ClinicalDocumentDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.CLINICALDOCUMENT_DAO_CLASS);
            }
            logger.debug("ClinicalDocument DT = " + cdVO.getTheClinicalDocumentDT());
            clinicaldocumentUID = clinicaldocumentDAO.create(cdVO.getTheClinicalDocumentDT());
            logger.debug("ClinicalDocument root uid = " + clinicaldocumentUID);
            cdVO.getTheClinicalDocumentDT().setClinicalDocumentUid(new Long(clinicaldocumentUID));
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails insertClinicalDocument()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails insertClinicalDocument()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
        return clinicaldocumentUID;
    }



    private void insertActivityIDs(ClinicalDocumentVO cdVO) throws NEDSSSystemException, NEDSSSystemException
    {
        try
        {
            if(activityIdDAO == null)
            {
                activityIdDAO = (ActivityIdDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ACTIVITY_ID_DAO_CLASS);
            }
            clinicaldocumentUID = activityIdDAO.create((cdVO.getTheClinicalDocumentDT().getClinicalDocumentUid()).longValue(), cdVO.getTheActivityIdDTCollection());
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails insertActivityIDs()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails insertActivityIDs()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }


    private void insertActivityLocatorParticipations(ClinicalDocumentVO cdVO) throws NEDSSSystemException
    {
        try
        {
            if(activityLocatorParticipationDAO == null)
            {
                activityLocatorParticipationDAO = (ActivityLocatorParticipationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ACTIVITY_LOCATOR_PARTICIPATION_DAO_CLASS);
            }
            clinicaldocumentUID = activityLocatorParticipationDAO.create((cdVO.getTheClinicalDocumentDT().getClinicalDocumentUid()).longValue(), cdVO.getTheActivityLocatorParticipationDTCollection());
            //cdVO.getTheActivityLocatorParticipationDTCollection().setClinicalDocumentUid(new Long(clinicaldocumentUID));
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails insertActivityLocatorParticipations()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails insertActivityLocatorParticipations()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    private ClinicalDocumentDT selectClinicalDocument(long clinicaldocumentUID) throws  NEDSSSystemException
    {
        try
        {
            if(clinicaldocumentDAO == null)
            {
                clinicaldocumentDAO = (ClinicalDocumentDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.CLINICALDOCUMENT_DAO_CLASS);
            }
            return ((ClinicalDocumentDT)clinicaldocumentDAO.loadObject(clinicaldocumentUID));
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails selectClinicalDocument()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails selectClinicalDocument()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    private Collection<Object> selectActivityIDs(long aUID) throws  NEDSSSystemException
    {
        try
        {
            if(activityIdDAO == null)
            {
                activityIdDAO = (ActivityIdDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ACTIVITY_ID_DAO_CLASS);
            }
            return (activityIdDAO.load(aUID));
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails selectActivityIDs()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails selectActivityIDs()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }


    private Collection<Object> selectActivityLocatorParticipations(long aUID) throws  NEDSSSystemException
    {
        try
        {
            if(activityLocatorParticipationDAO == null)
            {
                activityLocatorParticipationDAO = (ActivityLocatorParticipationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ACTIVITY_LOCATOR_PARTICIPATION_DAO_CLASS);
            }
            return (activityLocatorParticipationDAO.load(aUID));
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails selectActivityLocatorParticipations()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails selectActivityLocatorParticipations()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    private void removeClinicalDocument(long aUID) throws  NEDSSSystemException
    {
        try
        {
            if(clinicaldocumentDAO == null)
            {
                clinicaldocumentDAO = (ClinicalDocumentDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.CLINICALDOCUMENT_DAO_CLASS);
            }
            clinicaldocumentDAO.remove(aUID);
        }
        catch(NEDSSDAOSysException ndsex)
        {
            cntx.setRollbackOnly();
            logger.fatal("Exception = "+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.getMessage());
        }
        catch(NEDSSSystemException ndapex)
        {
        	logger.fatal("Exception = "+ndapex.getMessage(), ndapex);
            cntx.setRollbackOnly();
            throw new NEDSSSystemException(ndapex.getMessage());
        }
    }


    private void removeActivityIDs(long aUID) throws  NEDSSSystemException
    {
        try
        {
            if(activityIdDAO == null)
            {
                activityIdDAO = (ActivityIdDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ACTIVITY_ID_DAO_CLASS);
            }
            activityIdDAO.remove(aUID);
        }
        catch(NEDSSDAOSysException ndsex)
        {
            cntx.setRollbackOnly();
            logger.fatal("Exception = "+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.getMessage());
        }
        catch(NEDSSSystemException ndapex)
        {
        	logger.fatal("Exception = "+ndapex.getMessage(), ndapex);
            cntx.setRollbackOnly();
            throw new NEDSSSystemException(ndapex.getMessage());
        }
    }


    private void updateClinicalDocument(ClinicalDocumentVO cdVO) throws  NEDSSSystemException, NEDSSConcurrentDataException

    {
        try
        {
            if(clinicaldocumentDAO == null)
            {
                clinicaldocumentDAO = (ClinicalDocumentDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.CLINICALDOCUMENT_DAO_CLASS);
            }
            clinicaldocumentDAO.store(cdVO.getTheClinicalDocumentDT());
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails updateClinicalDocument()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSConcurrentDataException ncdaex)
        {
            logger.fatal("Fails updateClinicalDocument() due to concurrent access! "+ncdaex.getMessage(), ncdaex);
            throw new NEDSSConcurrentDataException(ncdaex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails updateClinicalDocument()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }


    }

    private void updateActivityIDs(ClinicalDocumentVO cdVO) throws  NEDSSSystemException
    {
        try
        {
            if(activityIdDAO == null)
            {
                activityIdDAO = (ActivityIdDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ACTIVITY_ID_DAO_CLASS);
            }
            activityIdDAO.store(cdVO.getTheActivityIdDTCollection());
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails updateActivityIDs()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails updateActivityIDs()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }


    private void updateActivityLocatorParticipations(ClinicalDocumentVO cdVO)
              throws  NEDSSSystemException
    {
        try
        {
            if(activityLocatorParticipationDAO == null)
            {
                activityLocatorParticipationDAO = (ActivityLocatorParticipationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ACTIVITY_LOCATOR_PARTICIPATION_DAO_CLASS);
            }
            activityLocatorParticipationDAO.store(cdVO.getTheActivityLocatorParticipationDTCollection());
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails updateActivityLocatorParticipations()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails updateActivityLocatorParticipations()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

    private Long findClinicalDocument(long clinicaldocumentUID) throws NEDSSSystemException, NEDSSSystemException
    {
        Long findPK = null;
        try
        {
            if(clinicaldocumentDAO == null)
            {
                clinicaldocumentDAO = (ClinicalDocumentDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.CLINICALDOCUMENT_DAO_CLASS);
            }
            findPK = clinicaldocumentDAO.findByPrimaryKey(clinicaldocumentUID);
        }
        catch(NEDSSDAOSysException ndsex)
        {
            logger.fatal("Fails findClinicalDocument()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());
        }
        catch(NEDSSSystemException ndapex)
        {
            logger.fatal("Fails findClinicalDocument()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
        return findPK;
    }
    //get collection of ActRelationship from ActRelationshipDAOImpl entered by John Park
 private Collection<Object> selectActRelationshipDTCollection(long aUID)
      throws

        NEDSSSystemException
    {
        try  {
            if(clinicalDocumentActRelationshipDAOImpl == null) {
               clinicalDocumentActRelationshipDAOImpl = (ActRelationshipDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ACT_RELATIONSHIP_DAO_CLASS);
            }
            logger.debug("aUID in selectActRelationshipDTCollection  = " + aUID);
            return (clinicalDocumentActRelationshipDAOImpl.load(aUID));


        } catch(NEDSSDAOSysException ndsex) {
            logger.fatal("Fails selectActRelationshipDTCollection()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());

        } catch(NEDSSSystemException ndapex) {
            logger.fatal("Fails selectActRelationshipDTCollection()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }


    //get collection of Participation  from ParticipationDAOImpl entered by John Park
 private Collection<Object> selectParticipationDTCollection(long aUID)
      throws

        NEDSSSystemException
    {
        try  {
            if(clinicalDocumentParticipationDAOImpl == null) {
                clinicalDocumentParticipationDAOImpl = (ParticipationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.PARTICIPATION_DAO_CLASS);
            }
            logger.debug("aUID in selectParticipationDTCollection  = " + aUID);
            return (clinicalDocumentParticipationDAOImpl.loadAct(aUID));


        } catch(NEDSSDAOSysException ndsex) {
            logger.fatal("Fails selectParticipationDTCollection()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());

        } catch(NEDSSSystemException ndapex) {
            logger.fatal("Fails selectParticipationDTCollection()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

 //set a collection of participationDT and return the participationDTs with sequences- Wade Steele
 public void setParticipation(Collection<Object> partDTs)
      throws

        NEDSSSystemException
    {
        try
        {
            if(clinicalDocumentParticipationDAOImpl == null)
            {
                clinicalDocumentParticipationDAOImpl = (ParticipationDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.PARTICIPATION_DAO_CLASS);
            }
            Iterator<Object> iter = partDTs.iterator();
            while(iter.hasNext())
            {
                ParticipationDT partDT = (ParticipationDT)iter.next();
                logger.debug("Calling store on partDAO");
                clinicalDocumentParticipationDAOImpl.store(partDT);
            }

        } catch(NEDSSDAOSysException ndsex) {
            logger.fatal("Fails setParticipation()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());

        } catch(NEDSSSystemException ndapex) {
            logger.fatal("Fails setParticipation()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

  //set a collection of ActRelationships - Wade Steele
 public void setActRelationship(Collection<Object> actRelDTs)
      throws

        NEDSSSystemException
    {
        try
        {
            if(clinicalDocumentActRelationshipDAOImpl == null)
            {
                clinicalDocumentActRelationshipDAOImpl = (ActRelationshipDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.ACT_RELATIONSHIP_DAO_CLASS);
            }
            Iterator<Object> iter = actRelDTs.iterator();
            while(iter.hasNext())
            {
                ActRelationshipDT actRelDT = (ActRelationshipDT)iter.next();
                logger.debug("Calling store on ActRelationshipDAOImpl");
                clinicalDocumentActRelationshipDAOImpl.store(actRelDT);
            }

        } catch(NEDSSDAOSysException ndsex) {
            logger.fatal("Fails setActRelationship()"+ndsex.getMessage(), ndsex);
            throw new NEDSSSystemException(ndsex.toString());

        } catch(NEDSSSystemException ndapex) {
            logger.fatal("Fails setActRelationship()"+ndapex.getMessage(), ndapex);
            throw new NEDSSSystemException(ndapex.toString());
        }
    }

}//end of ClinicalDocumentRootDAOImpl class
