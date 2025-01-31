package gov.cdc.nedss.deduplication.ejb.deduplicationprocessor.bean;

import gov.cdc.nedss.deduplication.helper.DeDuplicationHelper;
import gov.cdc.nedss.deduplication.helper.DeduplicationActivityLogDT;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;

import gov.cdc.nedss.webapp.nbs.util.DeDuplicationQueueHelper;
import gov.cdc.nedss.deduplication.ejb.deduplicationprocessor.dao.DeDuplicationDAO;
import gov.cdc.nedss.deduplication.ejb.deduplicationprocessor.dao.DeDuplicationActivityLogDAOImpl;
import gov.cdc.nedss.deduplication.exception.NEDSSDeduplicationException;
import gov.cdc.nedss.systemservice.exception.*;
import gov.cdc.nedss.systemservice.ejb.mprupdateejb.bean.*;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.scheduler.util.SchedulerConstants;
import gov.cdc.nedss.systemservice.util.PrepareVOUtils;
import gov.cdc.nedss.systemservice.util.NEDSSDAOFactory;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.entity.person.dt.PersonDT;
import gov.cdc.nedss.entity.person.dt.PersonNameDT;
import gov.cdc.nedss.entity.person.ejb.bean.PersonHome;
import gov.cdc.nedss.entity.person.ejb.bean.Person;
import gov.cdc.nedss.entity.person.ejb.dao.PersonRootDAOImpl;
import gov.cdc.nedss.entity.person.ejb.dao.PersonMergeDAOImpl;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.deduplication.vo.DeduplicationPatientMergeVO;
import gov.cdc.nedss.deduplication.vo.DeduplicationSimilarPatientVO;
import gov.cdc.nedss.controller.ejb.entitycontrollerejb.bean.EntityController;
import gov.cdc.nedss.controller.ejb.entitycontrollerejb.bean.EntityControllerHome;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.deduplication.vo.*;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.math.BigDecimal;

import javax.ejb.*;
import javax.rmi.PortableRemoteObject;

public class DeDuplicationProcessorEJB implements SessionBean, DeDuplicationBusinessInterface
{
	private static final long serialVersionUID = 1L;
    private static final LogUtils logger = new LogUtils(PrepareVOUtils.class.getName());
    private static PropertyUtil propertyUtil= PropertyUtil.getInstance();
    private Collection<Object> mergeDtColl;
    /**
     * @roseuid 3E65159F0203
     * @J2EE_METHOD  --  DeDuplicationProcessorEJB
     */
    public DeDuplicationProcessorEJB    ()
    {

    }

    /**
     * @roseuid 3E65159F0232
     * @J2EE_METHOD  --  ejbCreate
     * Called by the container to create a session bean instance. Its parameters typically
     * contain the information the client uses to customize the bean instance for its use.
     * It requires a matching pair in the bean class and its home interface.
     */
    public void ejbCreate    ()
    {

    }

    /**
     * @roseuid 3E65159F0271
     * @J2EE_METHOD  --  ejbRemove
     * A container invokes this method before it ends the life of the session object. This
     * happens as a result of a client's invoking a remove operation, or when a container
     * decides to terminate the session object after a timeout. This method is called with
     * no transaction context.
     */
    public void ejbRemove    ()
    {

    }

    /**
     * @roseuid 3E65159F0290
     * @J2EE_METHOD  --  ejbActivate
     * The activate method is called when the instance is activated from its 'passive' state.
     * The instance should acquire any resource that it has released earlier in the ejbPassivate()
     * method. This method is called with no transaction context.
     */
    public void ejbActivate    ()
    {

    }

    /**
     * @roseuid 3E65159F02BF
     * @J2EE_METHOD  --  ejbPassivate
     * The passivate method is called before the instance enters the 'passive' state. The
     * instance should release any resources that it can re-acquire later in the ejbActivate()
     * method. After the passivate method completes, the instance must be in a state that
     * allows the container to use the Java Serialization protocol to externalize and store
     * away the instance's state. This method is called with no transaction context.
     */
    public void ejbPassivate    ()
    {

    }

    /**
     * @roseuid 3E65159F02DE
     * @J2EE_METHOD  --  setSessionContext
     * Set the associated session context. The container calls this method after the instance
     * creation. The enterprise Bean instance should store the reference to the context
     * object in an instance variable. This method is called with no transaction context.
     */
    public void setSessionContext    (SessionContext sc)
    {

    }

    /**
     * Given a Collection<Object> of PatientRecords finds the Master Patient Record among the same using
     * business rules.
     * @roseuid 3E6CD1950190
     * @J2EE_METHOD  --  mergeMPR
     */
    public DeduplicationPatientMergeVO mergeMPR(Collection<Object> personVOCollection, String specifiedSurvivorPatientId, NBSSecurityObj nbsSecurityObj) throws RemoteException
    {
        DeDuplicationHelper deDuplicationHelper = new DeDuplicationHelper();
        Collection<Object> confirmationDtColl = null;
        PersonVO survivorPersonVO = null;
        Collection<Object> collection = null;
        DeduplicationPatientMergeVO dedupPatientMergeVo = new DeduplicationPatientMergeVO();
        try
        {
            PrepareVOUtils prepareVOUtils = new PrepareVOUtils();

            NedssUtils nedssUtils = new NedssUtils();



            boolean check = nbsSecurityObj.getPermission(NBSBOLookup.PATIENT,
                NBSOperationLookup.MERGE);

            if (check == false)
            {
               throw new NEDSSSystemException("don't have permission to merge MPR's");
            }

            survivorPersonVO = deDuplicationHelper.findSurvivor(personVOCollection, specifiedSurvivorPatientId);

            // = deDuplicationHelper.buildPersonMergeCollection(nbsSecurityObj);

            collection = deDuplicationHelper.getSupercededMPRCollection();

            updateSurvivingMPR(survivorPersonVO, collection, nbsSecurityObj, nedssUtils);

            switchSupercededMPRRevisionPUIDsToSurvivirMPRPUIDs(survivorPersonVO, collection, prepareVOUtils, nedssUtils, nbsSecurityObj, deDuplicationHelper);
            setSupercededMPRStatusToSuperceded(collection, prepareVOUtils, nedssUtils, nbsSecurityObj);

            dedupPatientMergeVo.setThePersonMergeDT(mergeDtColl);
            dedupPatientMergeVo.setTheMergeConfirmationVO(deDuplicationHelper.getMergeConfirmationVO());
            //createPersonMergeDt(mergeDtColl);

        }
        catch(Exception e)
        {
        	logger.fatal(e.getMessage(), e);
            throw new EJBException(e.getMessage(),e);  //To change body of catch statement use Options | File Templates.
        }
        return dedupPatientMergeVo;
    }

    public void createPersonMergeDt(Collection<Object> mergeDtColl, NBSSecurityObj nbsSecurityObj) throws RemoteException{
      PersonMergeDAOImpl dao = new PersonMergeDAOImpl();
      dao.create(mergeDtColl);
    }

    /**
     * Given a Surviving MPR updates the same using MPRUpdateEngine.
     * @param survivorPersonVO
     * @param collection
     * @param nbsSecurityObj
     * @param inNedssUtils
     * @throws MPRUpdateException
     * @return void
     * @throws RemoteException
     */
    private void updateSurvivingMPR(PersonVO survivorPersonVO, Collection<Object> collection, NBSSecurityObj nbsSecurityObj, NedssUtils inNedssUtils)throws MPRUpdateException, RemoteException {
    	try {

    		Object obj = inNedssUtils.lookupBean(JNDINames.MPR_UPDATE_ENGINE_EJB);
			MPRUpdateEngineHome home = (MPRUpdateEngineHome)PortableRemoteObject.narrow(
											   obj, MPRUpdateEngineHome.class);
			MPRUpdateEngine mprUpdateEngine = home.create();
			mprUpdateEngine.updateWithMPRs(survivorPersonVO, collection, nbsSecurityObj);
    	} catch(CreateException e) {
    		logger.fatal(e.getMessage(), e);
            throw new EJBException(e.getMessage(),e);
    	}//end of catch

    }

    /**
     * Given the collection of Superceded MPR's , goes thru the list of the same to update each
     * of those to reflect the ParentUID of the Surviving MPR.
     * @param inPersonVO
     * @param inCollection
     * @param inPrepareVOUtils
     * @param inNedssUtils
     * @param inNBSSecurityObj
     * @return void
     * @throws Exception
     */
    private void switchSupercededMPRRevisionPUIDsToSurvivirMPRPUIDs(PersonVO survivorPersonVO, Collection<Object> inCollection, PrepareVOUtils inPrepareVOUtils,
        NedssUtils inNedssUtils, NBSSecurityObj inNBSSecurityObj, DeDuplicationHelper deDuplicationHelper) throws Exception
    {
        Person person;
        PersonVO personVO;
        PersonVO supercededPersonVO;
        Iterator<Object> iterator;
        Long personParentUID = survivorPersonVO.getThePersonDT().getPersonParentUid();
        String localId = survivorPersonVO.getThePersonDT().getLocalId();
        mergeDtColl =  new ArrayList<Object> ();
        Timestamp ts = new Timestamp(((new java.util.Date()).getTime()));
        try
        {
            Object obj = inNedssUtils.lookupBean(JNDINames.PERSONEJB);

            PersonHome home = (PersonHome)PortableRemoteObject.narrow(
                                       obj, PersonHome.class);

            Iterator<Object> supercededMPRCollection  = inCollection.iterator();

            while(supercededMPRCollection.hasNext())
            {
                supercededPersonVO = (PersonVO)supercededMPRCollection.next();
                //see seq diagram for reason why I am sending the same object in twice.
                mergeDtColl.add(deDuplicationHelper.buildPersonMergeDt(supercededPersonVO, survivorPersonVO, inNBSSecurityObj, ts));
                Collection<Object> collection = null;
                try {
                  collection = home.findByPersonParentUid(supercededPersonVO.
                      getThePersonDT().getPersonParentUid());
                }catch(FinderException fe) {
                  //if(fe instanceof FinderException)
                    continue;
                  //else
                   // throw fe;
                }

                iterator  = collection.iterator();

                while(iterator.hasNext())
                {
                    person = (Person)iterator.next();

                    personVO = person.getPersonVO();
                    //see seq diagram specifically for not about population of personMergeDt.
                    //the attribute names are not as they appear in personMergeDt
                    PersonVO beforeMergeVo = (PersonVO)personVO.deepCopy();
                    personVO.setItDirty(true);
                    personVO.getThePersonDT().setItDirty(true);
                    inPrepareVOUtils.prepareVO(personVO.getThePersonDT(), NEDSSConstants.PATIENT, NEDSSConstants.PAT_EDIT, NEDSSConstants.PATIENT, NEDSSConstants.BASE, inNBSSecurityObj);
                    personVO.getThePersonDT().setPersonParentUid(personParentUID);
                    personVO.getThePersonDT().setLocalId(localId);
                    person.setPersonVO(personVO);
                    mergeDtColl.add(deDuplicationHelper.buildPersonMergeDt(beforeMergeVo, personVO, inNBSSecurityObj, ts));

                }

            }

        }
        catch(Exception e)
        {
        	logger.fatal(e.getMessage(), e);
            throw new EJBException(e.getMessage(),e);
        }

        return;
    }

    /**
     * Given a collection of Superceded MPR's updates the Status of each of those with 'SUPERCEDED'
     * @param inCollection
     * @param inPrepareVOUtils
     * @param inNedssUtils
     * @param inNbsSecurityObj
     * @return void
     * @throws RemoteException
     * @throws NEDSSConcurrentDataException
     * @throws Exception
     */
    private void setSupercededMPRStatusToSuperceded(Collection<Object> inCollection, PrepareVOUtils inPrepareVOUtils, NedssUtils inNedssUtils, NBSSecurityObj inNbsSecurityObj) throws RemoteException, NEDSSConcurrentDataException, Exception {
        PersonVO discardedPersonVO;
        PersonDT personDT;
        Iterator<Object> iterator = inCollection.iterator();

        try
        {
            Object obj = inNedssUtils.lookupBean(JNDINames.EntityControllerEJB);

            EntityControllerHome home = (EntityControllerHome)PortableRemoteObject.narrow(
                                       obj, EntityControllerHome.class);
            EntityController entityController = home.create();

            while(iterator.hasNext())
            {
                discardedPersonVO = (PersonVO)iterator.next();

                discardedPersonVO.setItDirty(true);
                discardedPersonVO.getThePersonDT().setItDirty(true);
                //personDT = (PersonDT) inPrepareVOUtils.prepareVO(discardedPersonVO.getThePersonDT(), "PERSON", "PAT_MERGE", "PERSON", "BASE", inNbsSecurityObj);

                inPrepareVOUtils.prepareVO(discardedPersonVO.getThePersonDT(), NEDSSConstants.PATIENT, NEDSSConstants.PER_MERGE, NEDSSConstants.PATIENT, NEDSSConstants.BASE, inNbsSecurityObj);

                personDT = discardedPersonVO.getThePersonDT();

                personDT.setItDirty(true);

                personDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_SUPERCEDED);

                entityController.setPersonInfo(personDT, inNbsSecurityObj);
            }
        }
        catch(Exception e)
        {
        	logger.fatal(e.getMessage(), e);
            throw new EJBException(e.getMessage(),e);
        }

        return;
    }
    /**
     * When passed in a groupNumber as input the method returns all the MPR's associated
     * with the Group Number (Who have been grouped 'SIMILAR' by the batch process).
     * @param groupNbr
     * @param nbsSecurityObj
     * @return Collection<Object>
     * @throws NEDSSDeduplicationException
     * @throws EJBException
     */
    public Collection<Object> getSimilarGroup(Long groupNbr, NBSSecurityObj nbsSecurityObj) throws NEDSSDeduplicationException, EJBException{
      ArrayList<Object>  arrayList = new ArrayList<Object> ();

      try {
        boolean check = nbsSecurityObj.getPermission(NBSBOLookup.PATIENT,
                NBSOperationLookup.MERGE);
        if (check == false)
        {
           throw new NEDSSSystemException("don't have permission to merge MPR's");
        }//end of if
        NedssUtils nedssUtils = new NedssUtils();
        Object obj = nedssUtils.lookupBean(JNDINames.PERSONEJB);

        PersonHome home = (PersonHome)PortableRemoteObject.narrow(
                                       obj, PersonHome.class);
        Collection<Object> personColl = home.findByGroup(groupNbr);

        if(personColl.size() == 1) {
          Person person = (Person)personColl.iterator().next();
          PersonVO pVo = person.getPersonVO();
          pVo.getThePersonDT().setGroupNbr(null);
          pVo.getThePersonDT().setGroupTime(null);
          pVo.getThePersonDT().setDedupMatchInd(null);
          pVo.setItDirty(true);
          person.setPersonVO(pVo);
          throw new NEDSSDeduplicationException();
        }
        Iterator<Object> personIt = personColl.iterator();
        while(personIt.hasNext()) {
          DeduplicationPatientMergeVO vo = new DeduplicationPatientMergeVO();
          PersonVO mpr = ((Person)personIt.next()).getPersonVO();
          vo.setMPR(mpr);
          try {
            Collection<Object> revisionColl = home.findByPersonParentUid(mpr.getThePersonDT().getPersonParentUid());
            Iterator<Object> it = revisionColl.iterator();
            ArrayList<Object>  list = new ArrayList<Object> ();
            while(it.hasNext()) {
                //Collecting all the revisions of the MPR...
                list.add(((Person)it.next()).getPersonVO());
            }
            vo.setRevisionCollection(list);
          }catch(FinderException fe) {
            //continue on, but add the vo to the arrayList.
            //This why the key word "continue" is not used here.
          }

          arrayList.add(vo);
        }//end of while
      } catch(FinderException fe) {
    	  logger.fatal("FinderException occured: " + fe.getMessage(), fe);
        throw new NEDSSDeduplicationException();
      } catch(Exception e) {
        if(e instanceof NEDSSDeduplicationException){
        	 logger.fatal("NEDSSDeduplicationException occured: " + e.getMessage(), e);
          throw (NEDSSDeduplicationException)e;
        }
        else
        	logger.fatal(e.getMessage(), e);
        throw new EJBException(e.getMessage(),e);
      }//end of catch
      return arrayList;
    }//end of getSimilarGroup();

    /**
     * Based on various business rules this method, finds same patients in the DataBase
     * and merges them in to one patient.
     * @param nbsSecurityObj
     * @throws RemoteException
     */
    public DeduplicationPatientMergeVO samePatientMatch(NBSSecurityObj nbsSecurityObj) throws RemoteException
    {
        try {
			boolean check = nbsSecurityObj.getPermission(NBSBOLookup.PATIENT,
			    NBSOperationLookup.MERGE);
			
			Timestamp tp = new Timestamp(System
					.currentTimeMillis());

			if (check == false)
			{
			   throw new NEDSSSystemException("don't have permission to merge MPR's");
			}

			DeDuplicationDAO deDuplicationDAO;

			deDuplicationDAO = (DeDuplicationDAO)NEDSSDAOFactory.getDAO(JNDINames.DEDUPLICATION_DAO_CLASS);

			Collection<Object> samePivotCollection  = null;
			try
			{
			    samePivotCollection  = deDuplicationDAO.getPivotList();
			}
			catch (NEDSSDAOSysException e)
			{
			    throw new RemoteException(e.toString());  //To change body of catch statement use Options | File Templates.
			}

			if(samePivotCollection.size() < 1)
			{
			    logger.info("Too Small a collection size to continue with executon");
			    return null;
			}

			Iterator<Object> samePivotCollectionIterator = samePivotCollection.iterator();
			Collection<Object> samePatientMatchCollection;
			BinarySearchTree samePatientMatchBinarySearchTree;
			Collection<Object> samePatientMatchCollectionFromRaceAndEthnic;
			Collection<Object> samePatientMatchCollectionFromEntity;
			Collection<Object> samePatientMatchCollectionFromPersonName;
			Collection<Object> samePatientMatchCollectionFromELPDT;
			Collection<Object> finalCollection;
			int autoMergeRecordsId = 0;
			int autoMergeRecordsSurvive = 0;
			DeduplicationPatientMergeVO deduplicationPatientMergeVO = new DeduplicationPatientMergeVO();
			Collection<Object> consolidatedMergeDTUIDsCollection  = new ArrayList<Object> ();
			Collection<Object> consolidatedSupercededMergeDTCollection  = new ArrayList<Object> ();


			try
			{
			    while(samePivotCollectionIterator.hasNext())
			    {
			        try
			        {
			            // Pass in the PersonUID of the Iterator<Object>'s next entry to the DeDuplicationDAO's
			            // methods, which inturn execute SQL to find if the entry has same patients in the DB
			            PersonNameDT personNameDT = (PersonNameDT)samePivotCollectionIterator.next();

			            Long currentPivot = personNameDT.getPersonUid();
			            Timestamp currentPivotTimeStamp = personNameDT.getAsOfDate();

			            //In case the superceded PersonUID is part of our Pivot Collection<Object> skip processing
			            //the same...
			            if(consolidatedMergeDTUIDsCollection.contains(currentPivot))
			            {
			                consolidatedMergeDTUIDsCollection.remove(currentPivot);
			                throw new Exception("Collission of Superceded MPR UID with the element of Same pivot Collection<Object>, hence skipping...");
			            }

			            samePatientMatchCollection  = deDuplicationDAO.getSamePatientsFromPerson(currentPivot);

			            if(samePatientMatchCollection.isEmpty() == true) continue;

			            // For Swift lookups using BinarySearch...
			            samePatientMatchBinarySearchTree = buildBinaryTreeGivenACollection(samePatientMatchCollection);

			            samePatientMatchCollectionFromRaceAndEthnic = deDuplicationDAO.getSamePatientsFromRaceAndEthnic(currentPivot, samePatientMatchBinarySearchTree);

			            if(samePatientMatchCollectionFromRaceAndEthnic.isEmpty() == true) continue;

			            filterTheBinaryTreeUsingCollection(samePatientMatchBinarySearchTree, samePatientMatchCollectionFromRaceAndEthnic);

			            if(samePatientMatchBinarySearchTree.isEmpty() == true) continue;

			            if(deDuplicationDAO.hasEntityIds(currentPivot)){

			                samePatientMatchCollectionFromEntity = deDuplicationDAO.getEntitysFromEntity(currentPivot, samePatientMatchBinarySearchTree);

			                if(samePatientMatchCollectionFromEntity.isEmpty() == true) continue;

			                filterTheBinaryTreeUsingCollection(samePatientMatchBinarySearchTree, samePatientMatchCollectionFromEntity);

			            }

			            if(samePatientMatchBinarySearchTree.isEmpty() == true) continue;

			            samePatientMatchCollectionFromPersonName = deDuplicationDAO.getSamePatientsFromPersonName(currentPivot, currentPivotTimeStamp, samePatientMatchBinarySearchTree);

			            if(samePatientMatchCollectionFromPersonName.isEmpty() == true) continue;

			            filterTheBinaryTreeUsingCollection(samePatientMatchBinarySearchTree, samePatientMatchCollectionFromPersonName);

			            if(samePatientMatchBinarySearchTree.isEmpty() == true) continue;

			            if(deDuplicationDAO.hasPostalLocators(currentPivot)){

			                samePatientMatchCollectionFromELPDT = deDuplicationDAO.getSamePatientsFromPostalLocator(currentPivot, samePatientMatchBinarySearchTree);

			                if(samePatientMatchCollectionFromELPDT.isEmpty() == true) continue;

			                filterTheBinaryTreeUsingCollection(samePatientMatchBinarySearchTree, samePatientMatchCollectionFromELPDT);

			            }

			            if(samePatientMatchBinarySearchTree.isEmpty() == true) continue;

			            finalCollection = samePatientMatchBinarySearchTree.toCollection();

			            // We have the collection of patients which are grouped same as
			            // the current pivot, to merge all of them put the pivot in the
			            // collection as well, ignore if it is already in there.
			            if (finalCollection.contains(currentPivot) == true && finalCollection.size() == 1)
			            {
			                throw new Exception("The pivot is trying to Merge with itself");
			            }

			            if(finalCollection.contains(currentPivot) == false)
			            {
			                finalCollection.add(currentPivot);
			            }


			            buildMergeMPRCollectionGivenUIDCollection(finalCollection, nbsSecurityObj);

			            deduplicationPatientMergeVO = mergeMPR(finalCollection, "", nbsSecurityObj);

			            buildOrAppendToSupercededPersonUidCollection(consolidatedMergeDTUIDsCollection, finalCollection);

			            consolidatedSupercededMergeDTCollection.addAll(deduplicationPatientMergeVO.getThePersonMergeDT());
			            autoMergeRecordsId += deduplicationPatientMergeVO.getTheMergeConfirmationVO().size();
			            autoMergeRecordsSurvive++;
			        }
			        catch(Exception e)
			        {
			            //Ignore the exception keep going...
			           //   continue;
			            String error = e.toString();
			            //logger.fatal("error in samePatientMatch :::" + e);
			            
			            if (error != null && error.indexOf("pivot") == -1) {

							if (error.length() < 1999)
								SchedulerConstants.setSAMEPROCESSEXCEP(error);
							else
								SchedulerConstants.setSAMEPROCESSEXCEP(error
										.substring(0, 1999));
							SchedulerConstants.setSAMEBATCHSTARTTIME(tp.toString());

							// setSameDeduplicationActivityLog(deduplicationActivityLogDT,nbsSecurityObj);
							//logger.fatal("error in samePatientMatch :" + e);
							logger.fatal(e.getMessage(), e);
							throw new Exception(e.toString());
						} else
							continue;
			        }
			    }
			}
			catch (Exception e)
			{
				logger.fatal(e.getMessage(), e);
			    throw new RemoteException(e.toString());
			}

			deduplicationPatientMergeVO.setSameRecordsID(new Integer(autoMergeRecordsId));
			deduplicationPatientMergeVO.setSurvivingRecordsID(new Integer(autoMergeRecordsSurvive));
			//Set the PersonMergeDT Collection<Object> with the collection of consolidated MergeDT's
			//which are collections by themselves...
			deduplicationPatientMergeVO.setThePersonMergeDT(consolidatedSupercededMergeDTCollection);

			return deduplicationPatientMergeVO;
		} catch (NEDSSDAOSysException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(), e);
	        throw new EJBException(e.getMessage(),e);
		}
    }

    /**
     * The method executes a Stored Procedure to determine MPR's which
     * are 'SIMILAR' in nature based on variety of business rules.
     * @param nbsSecurityObj
     * @return
     * @throws RemoteException
     */
    public DeduplicationSimilarPatientVO similarPatientMatch(NBSSecurityObj nbsSecurityObj) throws RemoteException
    {
        try {
			boolean check = nbsSecurityObj.getPermission(NBSBOLookup.PATIENT,
			    NBSOperationLookup.MERGE);
			Timestamp tp = new Timestamp(System
					.currentTimeMillis());
			DeduplicationSimilarPatientVO deduplicationSimilarPatientVO = new DeduplicationSimilarPatientVO();

			if (check == false)
			{
			   throw new NEDSSSystemException("don't have permission to merge MPR's");
			}

			DeDuplicationDAO deDuplicationDAO;
			BigDecimal similarGroupCount;

			deDuplicationDAO = (DeDuplicationDAO)NEDSSDAOFactory.getDAO(JNDINames.DEDUPLICATION_DAO_CLASS);
			try
			{
			    similarGroupCount = deDuplicationDAO.createSimilarGroups();
			    deduplicationSimilarPatientVO.setSimilarrecords(new Integer(similarGroupCount.intValue()));
			}
			catch (NEDSSDAOSysException e)
			{
				String error = e.toString();
				if(error.length()<1900)
					SchedulerConstants.setSIMILARPROCESSEXCEP(error);
				else
					SchedulerConstants.setSIMILARPROCESSEXCEP(error.substring(0, 1999));
			   SchedulerConstants.setSIMILARBATCHSTARTTIME(tp.toString());
				    logger.fatal("error in similarPatientMatch :" + e); 
				          
				throw new RemoteException(e.toString());  //To change body of catch statement use Options | File Templates.
			}
			catch (Exception e)
			{
				String error = e.toString();
				if(error.length()<1900)
					SchedulerConstants.setSIMILARPROCESSEXCEP(error);
				else
					SchedulerConstants.setSIMILARPROCESSEXCEP(error.substring(0, 1900));
			   SchedulerConstants.setSIMILARBATCHSTARTTIME(tp.toString());
				    logger.fatal("error in similarPatientMatch :" + e); 
				    throw new RemoteException(e.toString()); 
			}

			return deduplicationSimilarPatientVO;
		} catch (NEDSSDAOSysException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(), e);
	        throw new EJBException(e.getMessage(),e);
		}
    }

    /**
     * Gets the similar queues from DataBase via a call to PersonRootDAOImpl...
     * @param nbsSecurityObj
     * @return
     * @throws RemoteException
     */
    public void processSimilarQueue(NBSSecurityObj nbsSecurityObj) throws RemoteException
    {
    	  HashMap<Object, Object> hashMap = new HashMap<Object, Object>();


        try
        {
            boolean check = nbsSecurityObj.getPermission(NBSBOLookup.PATIENT, NBSOperationLookup.MERGE);

            if (check == false)
            {
                throw new NEDSSSystemException("don't have permission to merge MPR's");
            }//end of if

            PersonRootDAOImpl personRootDAOImpl;
            personRootDAOImpl = (PersonRootDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.PERSON_ROOT_DAO_CLASS);

            hashMap = personRootDAOImpl.findAllDistinctGroups();

            //The very first time the references are invalid o handle that case
            // check for their validity...
            if(DeDuplicationQueueHelper.getDedupAvailableQueue() != null) DeDuplicationQueueHelper.getDedupAvailableQueue().clear();
            DeDuplicationQueueHelper.getDedupAvailableQueue().putAll(hashMap);
            if(DeDuplicationQueueHelper.getDedupTakenQueue() != null) DeDuplicationQueueHelper.getDedupTakenQueue().clear();
            if(DeDuplicationQueueHelper.getDedupSessionQueue() != null) DeDuplicationQueueHelper.getDedupSessionQueue().clear();


        }
        catch(Exception e)
        {
        	logger.fatal(e.getMessage(), e);
	        throw new EJBException(e.getMessage(),e);  //To change body of catch statement use Options | File Templates.
        }


    }

    /**
     * Builds a unbalanced binary tree given a Collection<Object> as input param.
     * @param inSamePatientMatchCollection
     * @return BinarySearchTree
     */
    private BinarySearchTree buildBinaryTreeGivenACollection(Collection<Object> inSamePatientMatchCollection)
    {
        try {
			BinarySearchTree samePatientMatchBinaryTree;
			samePatientMatchBinaryTree = new BinarySearchTree();

			Iterator<Object> iterator = inSamePatientMatchCollection.iterator();

			while(iterator.hasNext())
			{
			    samePatientMatchBinaryTree.insert((Long)iterator.next());
			}

			return samePatientMatchBinaryTree;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(), e);
	        throw new EJBException(e.getMessage(),e);
		}
    }

    /**
     * The following method finds the intersections between the BinarySearchTree and a Collection<Object>
     * passed in as a input paramter. The intersections are populated in a new Binary Tree and the
     * same is assigned as a reference to the binary tree passed in.
     * @param inSamePatientMatchBinarySearchTree
     * @param inSamePatientMatchCollectionFromRaceAndEthnic
     */
    private void filterTheBinaryTreeUsingCollection(BinarySearchTree inSamePatientMatchBinarySearchTree, Collection<Object> inSamePatientMatchCollectionFromRaceAndEthnic)
    {
        try {
			Iterator<Object> iterator = inSamePatientMatchCollectionFromRaceAndEthnic.iterator();

			while(iterator.hasNext())
			{
			    //In case of no match action...
			    //Remove the underlying element in the collection, we don't need to
			    //carry it forward anymore...
			    Long currentUid = (Long)iterator.next();

			    if(inSamePatientMatchBinarySearchTree.find(currentUid) == null)
			    {
			        iterator.remove();
			    }
			}

			iterator = inSamePatientMatchCollectionFromRaceAndEthnic.iterator();

			if(iterator.hasNext() != false)
			{
			    inSamePatientMatchBinarySearchTree.makeEmpty();

			    while(iterator.hasNext())
			    {
			        inSamePatientMatchBinarySearchTree.insert((Long)iterator.next());
			    }
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(), e);
	        throw new EJBException(e.getMessage(),e);
		}
    }

    /**
     * Given a PersonUID Collection<Object> builds a MPR Collection<Object>...
     * @param inCollection
     * @param inNbsSecurityObj
     * @throws RemoteException
     * @throws CreateException
     * @throws EJBException
     * @throws Exception
     */
    private void buildMergeMPRCollectionGivenUIDCollection(Collection<Object> inCollection, NBSSecurityObj inNbsSecurityObj) throws RemoteException, CreateException, EJBException, Exception
    {
        NedssUtils nedssUtils = new NedssUtils();

        try
        {
            Object obj = nedssUtils.lookupBean(JNDINames.EntityControllerEJB);

            EntityControllerHome home = (EntityControllerHome)PortableRemoteObject.narrow(
                                       obj, EntityControllerHome.class);
            EntityController entityController = home.create();

            int size = inCollection.size();
            ArrayList<Object>  arrayList = (ArrayList<Object> )inCollection;

            for(int i = 0; i < size; i++)
            {
                arrayList.set(i, entityController.getMPR((Long)arrayList.get(i), inNbsSecurityObj));
            }
        }
        catch (Exception e)
        {
        	logger.fatal(e.getMessage(), e);
	        throw new EJBException(e.getMessage(),e);  //To change body of catch statement use Options | File Templates.
        }
    }
    /**
     * Given a SecurityObject, returns the number of active groups in the database
     * @param inNbsSecurityObj
     * @return Integer
     * @exception NEDSSDAOSysException
     */
    public Integer getActiveGroupNumberCount(NBSSecurityObj inNbsSecurityObj) throws NEDSSDAOSysException
    {
        try {
			boolean check = inNbsSecurityObj.getPermission(NBSBOLookup.PATIENT,
			    NBSOperationLookup.MERGE);

			if (check == false)
			{
			   throw new NEDSSSystemException("don't have permission to merge MPR's");
			}

			DeDuplicationDAO deDuplicationDAO;

			deDuplicationDAO = (DeDuplicationDAO)NEDSSDAOFactory.getDAO(JNDINames.DEDUPLICATION_DAO_CLASS);

			return deDuplicationDAO.getActiveGroupNumberCount();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(), e);
	        throw new EJBException(e.getMessage(),e);
		}
    }
  
    /**
     * The method creates a entry in the DeDuplication Activity Log and is invoked by the
     * batch process.
     * @param inDeduplicationActivityLogDT
     * @param inNbsSecurityObj
     */
    public void setSameDeduplicationActivityLog(DeduplicationActivityLogDT inDeduplicationActivityLogDT, NBSSecurityObj inNbsSecurityObj) throws NEDSSDAOSysException
    {
        try {
			boolean check = inNbsSecurityObj.getPermission(NBSBOLookup.PATIENT,
			    NBSOperationLookup.MERGE);
			
			logger.debug("into setSameDeduplicationActivityLog " );

			if (check == false)
			{
			   throw new NEDSSSystemException("don't have permission to merge MPR's");
			}

			DeDuplicationActivityLogDAOImpl dedupALDAOImpl = (DeDuplicationActivityLogDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.DEDUPLICATION_ACTIVITYLOGDAO_CLASS);

			Collection<Object> collection = new ArrayList<Object> ();
			collection.add(inDeduplicationActivityLogDT);

			try
			{
			    Timestamp endBatchTime = ((DeduplicationActivityLogDT)((ArrayList<Object> )collection).get(0)).getBatchEndTime();
			    dedupALDAOImpl.createSame(collection);
			    DeDuplicationQueueHelper.setLastBatchProcessTime(endBatchTime);
			}
			catch(Exception e)
			{
				logger.fatal(e.getMessage(), e);
			    throw new NEDSSSystemException(e.toString());
			}
			return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(), e);
	        throw new EJBException(e.getMessage(),e);
		}
    }

    /**
     * The method creates a entry in the DeDuplication Activity Log and is invoked by the
     * batch process.
     * @param inDeduplicationActivityLogDT
     * @param inNbsSecurityObj
     */
    public void setSimilarDeduplicationActivityLog(DeduplicationActivityLogDT inDeduplicationActivityLogDT, NBSSecurityObj inNbsSecurityObj) throws NEDSSDAOSysException
    {
        try {
			boolean check = inNbsSecurityObj.getPermission(NBSBOLookup.PATIENT,
			    NBSOperationLookup.MERGE);
			logger.debug("into setSimilarDeduplicationActivityLog " );
			if (check == false)
			{
			   throw new NEDSSSystemException("don't have permission to merge MPR's");
			}

			DeDuplicationActivityLogDAOImpl dedupALDAOImpl = (DeDuplicationActivityLogDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.DEDUPLICATION_ACTIVITYLOGDAO_CLASS);

			Collection<Object> collection = new ArrayList<Object> ();
			collection.add(inDeduplicationActivityLogDT);

			try
			{
			    Timestamp endBatchTime = ((DeduplicationActivityLogDT)((ArrayList<Object> )collection).get(0)).getBatchEndTime();
			    dedupALDAOImpl.createSimilar(collection);
			    DeDuplicationQueueHelper.setLastBatchProcessTime(endBatchTime);
			}
			catch(Exception e)
			{
				logger.fatal(e.getMessage(), e);
			    throw new NEDSSSystemException(e.toString());
			}
			return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(), e);
	        throw new EJBException(e.getMessage(),e);
		}
    }

    /**
     * The method creates a entry in the DeDuplication Activity Log and is invoked by the
     * batch process.
     * @param inDeduplicationActivityLogDT
     * @param inNbsSecurityObj
     */
  /*   public void setDeduplicationActivityLog(DeduplicationActivityLogDT inDeduplicationActivityLogDT, NBSSecurityObj inNbsSecurityObj) throws NEDSSDAOSysException
    {
        boolean check = inNbsSecurityObj.getPermission(NBSBOLookup.PATIENT,
            NBSOperationLookup.MERGE);

        if (check == false)
        {
           throw new NEDSSSystemException("don't have permission to merge MPR's");
        }

        DeDuplicationActivityLogDAOImpl dedupALDAOImpl = (DeDuplicationActivityLogDAOImpl)NEDSSDAOFactory.getDAO(JNDINames.DEDUPLICATION_ACTIVITYLOGDAO_CLASS);

        Collection<Object> collection = new ArrayList<Object> ();
        collection.add(inDeduplicationActivityLogDT);

        try
        {
            Timestamp endBatchTime = ((DeduplicationActivityLogDT)((ArrayList<Object> )collection).get(0)).getBatchEndTime();
            dedupALDAOImpl.create(collection);
            DeDuplicationQueueHelper.setLastBatchProcessTime(endBatchTime);
        }
        catch(Exception e)
        {
            throw new NEDSSSystemException(e.toString());
        }
        return;
    }*/

    /**
     * This method sets the property 'OVERRIDE' on the PropertyUtil
     * @param override
     * @param nbsSecurityObj
     * @throws RemoteException
     */
    public void setDedupOverride(Boolean override, NBSSecurityObj nbsSecurityObj)throws RemoteException {
      try {
		if(override.booleanValue())
		    propertyUtil.setDeDupOverride(NEDSSConstants.OVERRIDE);
		  else
		    propertyUtil.setDeDupOverride("FALSE");
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		logger.fatal(e.getMessage(), e);
        throw new EJBException(e.getMessage(),e);
	}
    }

    /**
     * This method get the property 'OVERRIDE' from PropertyUtil
     * @param nbsSecurityObj
     * @return boolean
     * @throws RemoteException
     */
    public Boolean getDedupOverride(NBSSecurityObj nbsSecurityObj) throws RemoteException {
      try {
		return new Boolean(propertyUtil.getDeDupOverride());
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		logger.fatal(e.getMessage(), e);
        throw new EJBException(e.getMessage(),e);
	}
    }

    /**
     * Returns the DeDupTaken Queue size.
     * @param nbsSecurityOb
     * @return Integer
     * @throws RemoteException
     */
    public Integer getDedupTakenQueueSize(NBSSecurityObj nbsSecurityOb) throws RemoteException {
      try {
		return new Integer(DeDuplicationQueueHelper.getDedupTakenQueue() == null?0:DeDuplicationQueueHelper.getDedupTakenQueue().size());
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		logger.fatal(e.getMessage(), e);
        throw new EJBException(e.getMessage(),e);
	}
    }
    /**
     * his method gets the DeDup Available Queue Size
     * @param nbsSecurityOb
     * @return Integer
     * @throws RemoteException
     */
    public Integer getDedupAvailableQueueSize(NBSSecurityObj nbsSecurityOb) throws RemoteException {
      try {
		return new Integer(DeDuplicationQueueHelper.getDedupAvailableQueue() == null?0:DeDuplicationQueueHelper.getDedupAvailableQueue().size());
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		logger.fatal(e.getMessage(), e);
        throw new EJBException(e.getMessage(),e);
	}
    }

    public Timestamp getLastBatchProcessTime(NBSSecurityObj nbsSecurityObj) {
      try {
		return DeDuplicationQueueHelper.getLastBatchProcessTime();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		logger.fatal(e.getMessage(), e);
        throw new EJBException(e.getMessage(),e);
	}
    }

    /**
     * The method builds the Superceded Person UID collection using the PersonUIDs from the
     * FinalCollection  , the idea is to make sure the Superceded Person UIDs do not participate
     * being Pivots if they have already been merged, even the same applies to the MPR who was determined
     * 'ACTIVE' in the group, essentially all the MPRs who participated in the merge now get added to
     * this collection.
     * @param inCollection
     * @param inFinalCollection
     */
    public void buildOrAppendToSupercededPersonUidCollection(Collection<Object> inCollection, Collection<Object> inFinalCollection)
    {
        try {
			if(inFinalCollection  == null) return;

			if(inFinalCollection.isEmpty()) return;

			Iterator<Object> iterator = inFinalCollection.iterator();

			while(iterator.hasNext())
			{   Long personUid = new Long(((PersonVO)iterator.next()).getThePersonDT().getPersonUid().longValue());
			    inCollection.add(personUid);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(), e);
	        throw new EJBException(e.getMessage(),e);
		}
    }

    /**
     *
     * @param nbsSecurityObj
     * @throws RemoteException
     */
    public void resetPatientRegistryForDedup(NBSSecurityObj nbsSecurityObj) throws RemoteException {
      try {
		DeDuplicationDAO deDuplicationDAO;
		  deDuplicationDAO = (DeDuplicationDAO)NEDSSDAOFactory.getDAO(JNDINames.DEDUPLICATION_DAO_CLASS);
		  deDuplicationDAO.resetPatientRegistryForDedup();
	} catch (NEDSSDAOSysException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		logger.fatal(e.getMessage(), e);
        throw new EJBException(e.getMessage(),e);
	}
    }
    /**
    *  removeFromMergeForDedup: this method is part of the remove from merge option (system Identified option).
    * It will call a method from DeDuplicationDAO to update the Patient table. The record for the patient Uid received as a parameter will have the following columns updated:
    * group_nbr = NULL, group_time = NULL, dedup_match_ind = 'R'
    * @param nbsSecurityObj
    * @throws RemoteException
    */
   public void removeFromMergeForDedup(String patientUid, NBSSecurityObj nbsSecurityObj) throws RemoteException {
     try {
		DeDuplicationDAO deDuplicationDAO;
		  deDuplicationDAO = (DeDuplicationDAO)NEDSSDAOFactory.getDAO(JNDINames.DEDUPLICATION_DAO_CLASS);
		  deDuplicationDAO.removePatientFromMergeForDedup(patientUid);
	} catch (NEDSSDAOSysException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		logger.fatal("Exception thrown from DeDuplicationProcessorEJB > removeFromMergeForDedup: " +e.getMessage(), e);
       throw new EJBException(e.getMessage(),e);
	}
   }
}