package gov.cdc.nedss.proxy.ejb.entityproxyejb.bean;

import gov.cdc.nedss.association.dt.ParticipationDT;
import gov.cdc.nedss.association.dt.RoleDT;
import gov.cdc.nedss.controller.ejb.entitycontrollerejb.bean.EntityController;
import gov.cdc.nedss.controller.ejb.entitycontrollerejb.bean.EntityControllerHome;
import gov.cdc.nedss.entity.entityid.dt.EntityIdDT;
import gov.cdc.nedss.entity.organization.dt.OrganizationDT;
import gov.cdc.nedss.entity.organization.ejb.dao.OrganizationDAOImpl;
import gov.cdc.nedss.entity.organization.util.DisplayOrganizationList;
import gov.cdc.nedss.entity.organization.vo.OrganizationSearchVO;
import gov.cdc.nedss.entity.organization.vo.OrganizationVO;
import gov.cdc.nedss.entity.person.dt.PersonDT;
import gov.cdc.nedss.entity.person.dt.PersonNameDT;
import gov.cdc.nedss.entity.person.dt.PersonRaceDT;
import gov.cdc.nedss.entity.person.ejb.dao.PersonDAOImpl;
import gov.cdc.nedss.entity.person.ejb.dao.PersonHistoryManager;
import gov.cdc.nedss.entity.person.vo.PatientSearchVO;
import gov.cdc.nedss.entity.person.vo.PersonSearchVO;
import gov.cdc.nedss.entity.person.vo.PersonSummaryVO;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.entity.person.vo.ProviderSearchVO;
import gov.cdc.nedss.entity.place.vo.PlaceSearchVO;
import gov.cdc.nedss.entity.place.vo.PlaceVO;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.ldf.helper.LDFHelper;
import gov.cdc.nedss.locator.dt.EntityLocatorParticipationDT;
import gov.cdc.nedss.proxy.ejb.entityproxyejb.dao.EntityProxyDAOImpl;
import gov.cdc.nedss.proxy.ejb.entityproxyejb.dao.FindPersonDAOImpl;
import gov.cdc.nedss.proxy.util.EntityProxyHelper;
import gov.cdc.nedss.systemservice.ejb.jurisdictionejb.bean.Jurisdiction;
import gov.cdc.nedss.systemservice.ejb.jurisdictionejb.bean.JurisdictionHome;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.util.AssocDTInterface;
import gov.cdc.nedss.systemservice.util.PrepareVOUtils;
import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;
import gov.cdc.nedss.util.DataTables;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;
import javax.rmi.PortableRemoteObject;
//import gov.cdc.nedss.ldf.dt.StateDefinedFieldDT;

/**
 * Title:       EntityProxyEJB.java
 * Description: This is session EJB. This session bean acts as a proxy for all the Entities to go to the individual EJB.
 * navigate to your
 * Copyright:   Copyright (c) 2001
 * Company:     csc
 * @author:     Nedss Development Team
 * @version     1.0
 */
public class EntityProxyEJB implements javax.ejb.SessionBean 
{
    static final LogUtils  logger = new LogUtils(EntityProxyEJB.class.getName());

    private SessionContext cntx;

    /**
     * @roseuid 3C4C477E01F3
     * @J2EE_METHOD -- EntityProxyEJB
     */
    public EntityProxyEJB()
    {
    }

    /**
     * A container invokes this method before it ends the life of the session
     * object. This happens as a result of a client's invoking a remove
     * operation, or when a container decides to terminate the session object
     * after a timeout. This method is called with no transaction context.
     * 
     * @roseuid 3C4C477F01B8
     * @J2EE_METHOD -- ejbRemove
     */
    public void ejbRemove()
    {
    }

    /**
     * The activate method is called when the instance is activated from its
     * 'passive' state. The instance should acquire any resource that it has
     * released earlier in the ejbPassivate() method. This method is called with
     * no transaction context.
     * 
     * @roseuid 3C4C477F035D
     * @J2EE_METHOD -- ejbActivate
     */
    public void ejbActivate()
    {
    }

    /**
     * The passivate method is called before the instance enters the 'passive'
     * state. The instance should release any resources that it can re-acquire
     * later in the ejbActivate() method. After the passivate method completes,
     * the instance must be in a state that allows the container to use the Java
     * Serialization protocol to externalize and store away the instance's
     * state. This method is called with no transaction context.
     * 
     * @roseuid 3C4C47800123
     * @J2EE_METHOD -- ejbPassivate
     */
    public void ejbPassivate()
    {
    }

    /**
     * Called by the container to create a session bean instance. Its parameters
     * typically contain the information the client uses to customize the bean
     * instance for its use. It requires a matching pair in the bean class and
     * its home interface.
     * 
     * @roseuid 3C4C84870286
     * @J2EE_METHOD -- ejbCreate
     */
    public void ejbCreate()
    {
    }

  /**
   * This method check the permission of the user to add the person and
   * sends the PersonVO values to the database
   * @roseuid 3C55D7910059
   * @J2EE_METHOD  --  setPerson
   * @param personVO  the PersonVO
   * @param nbsSecurityObj   the NBSSecurityObj
   * @return  personUid  the Long value
   * @throws EJBException
   * @throws NEDSSConcurrentDataException
   */

  public Long setPerson(PersonVO personVO, NBSSecurityObj nbsSecurityObj) throws
      javax.ejb.EJBException, NEDSSConcurrentDataException {
    logger.info("setting person-----------------");

    Long personUid = null;

    try {

      if (personVO.isItNew()) {

        /**
         * Check the  permission to add a person
         */
        boolean check1 = nbsSecurityObj.getPermission(NBSBOLookup.PATIENT,
            NBSOperationLookup.ADD);

        if (check1 == false) {
          logger.error("don't have permission to add personVO");
          throw new NEDSSSystemException(
              "don't have permission to add personVO");
        }
        else {
          EntityProxyDAOImpl entityProxyDAOImpl = new EntityProxyDAOImpl();
          /**
           * To set the value of city Code
           */
          personVO = entityProxyDAOImpl.setCityCode(personVO);

          PrepareVOUtils prepareVOUtils = new PrepareVOUtils();
          /**
           * Send the PersonDT to the prepareVOUtils to set the values for
           * few properties of the DT  and getting an updated new PersonDT
           */
          PersonDT newPersonDT = (PersonDT) prepareVOUtils.prepareVO(personVO.
              getThePersonDT(),
              NEDSSConstants.PATIENT,
              NEDSSConstants.PER_CR,
              DataTables.PERSON_TABLE,
              NEDSSConstants.BASE,
              nbsSecurityObj);
          personVO.setThePersonDT(newPersonDT);
          NedssUtils nedssUtils = new NedssUtils();
          Object theLookedUpObject;
          logger.debug("get reference for EntityControllerEJB");
          theLookedUpObject = nedssUtils.lookupBean(
              JNDINames.EntityControllerEJB);

          EntityControllerHome ecHome = (EntityControllerHome)
              PortableRemoteObject.narrow(
              theLookedUpObject,
              EntityControllerHome.class);
          EntityController entityController = ecHome.create();
          personUid = entityController.setPerson(personVO,nbsSecurityObj);
          if (personVO.getTheStateDefinedFieldDataDTCollection() != null
					&& personVO.getTheStateDefinedFieldDataDTCollection().size() > 0) {
         LDFHelper ldfHelper = LDFHelper.getInstance();
         ldfHelper.setLDFCollection(personVO.getTheStateDefinedFieldDataDTCollection(), personVO.getLdfUids(),
                      NEDSSConstants.PATIENT_LDF,null,personUid,nbsSecurityObj);
          }
        }
      }
      else if (personVO.isItDirty()) {

        /**
         * checking the permission to edit the PersonVO object
         */
        boolean check2 = nbsSecurityObj.getPermission(
            NBSBOLookup.PATIENT,
            NBSOperationLookup.EDIT);

        if (check2 == false) {
          logger.error("don't have permission to edit person info");
          throw new NEDSSSystemException(
              "don't have permission to edit person info");
        }
        else {
          EntityProxyDAOImpl entityProxyDAOImpl = new EntityProxyDAOImpl();
          personVO = entityProxyDAOImpl.setCityCode(personVO);

          /**
           * Send the PersonDT to the prepareVOUtils to set the values for
               * few properties(related to Edit) of the DT  and getting an updated new PersonDT
           */
          PrepareVOUtils prepareVOUtils = new PrepareVOUtils();
          PersonDT newPersonDT = (PersonDT) prepareVOUtils.prepareVO(personVO.
              getThePersonDT(),
              NEDSSConstants.PATIENT,
              NEDSSConstants.PER_EDIT,
              DataTables.PERSON_TABLE,
              NEDSSConstants.BASE,
              nbsSecurityObj);
          personVO.setThePersonDT(newPersonDT);
          if (personVO.getTheStateDefinedFieldDataDTCollection() != null
					&& personVO.getTheStateDefinedFieldDataDTCollection().size() > 0) {
          LDFHelper ldfHelper = LDFHelper.getInstance();
          ldfHelper.setLDFCollection(personVO.getTheStateDefinedFieldDataDTCollection(), personVO.getLdfUids(),
                           NEDSSConstants.PATIENT_LDF,null,newPersonDT.getUid(),nbsSecurityObj);
          }

          logger.debug("Inside entityProxyEJB.setPerson() and under itDirty=true and AFTER prepareVOUtils with PER_EDIT, PersonDT has "
                       + " record status of " + newPersonDT.getRecordStatusCd());

          NedssUtils nedssUtils = new NedssUtils();
          Object theLookedUpObject;
          logger.debug("get reference for EntityControllerEJB");
          theLookedUpObject = nedssUtils.lookupBean(
              JNDINames.EntityControllerEJB);

          EntityControllerHome ecHome = (EntityControllerHome)
              PortableRemoteObject.narrow(
              theLookedUpObject,
              EntityControllerHome.class);
          EntityController entityController = ecHome.create();
          personUid = entityController.setPerson(personVO,
                                                 nbsSecurityObj);

          return personUid;
        }
      }
    }
    catch (NEDSSConcurrentDataException ex) {
      logger.fatal("EntityProxyEJB.setPerson: NEDSSConcurrentDataException: " + ex.getMessage(),ex);
      throw new NEDSSConcurrentDataException(ex.getMessage(),ex);
    }
    catch (Exception e) {
      logger.fatal("EntityProxyEJB.setPerson: Exception: " + e.getMessage(),e);
      throw new NEDSSSystemException(e.getMessage(),e);
    }

    return personUid;
  }
  /**
    * @roseuid 3E6FA35A0308
    * @J2EE_METHOD  --  setProvider
    * @param personVO
    * @param nbsSecurityObj
    * @throws EJBException
    * @throws NEDSSException
    * @throws NEDSSConcurrentDataException
    */
   public java.lang.Long setProvider    (PersonVO personVO, NBSSecurityObj nbsSecurityObj) throws EJBException, NEDSSException, NEDSSConcurrentDataException, NEDSSAppException
   {
	   try{
     Long personUID = new Long(-1);
     if(personVO.isItNew() || personVO.isItDirty()){
       boolean check1 = nbsSecurityObj.getPermission("PROVIDER", "MANAGE");
       if(!check1){
         throw new NEDSSException("User does not have permission to call EntityProxyEJB.setProvider()");
       }
       try{
         if (!EntityProxyHelper.getInstance().isQuickCodeUnique((AbstractVO)personVO)) {
           throw new  NEDSSAppException("Quick Code is not unique");
         }
       }
       catch(NEDSSAppException nae){
         logger.error(nae.getMessage(),nae);
         throw new NEDSSException(nae.getMessage(), nae);
       }
       String businessTriggerCd = null;
       if(personVO.isItNew())
         businessTriggerCd = "PRV_CR";
       else
         businessTriggerCd = "PRV_EDIT";

       NedssUtils nedssUtils = new NedssUtils();
       Object theLookedUpObject;
       logger.debug("get reference for EntityControllerEJB");
       theLookedUpObject = nedssUtils.lookupBean(JNDINames.EntityControllerEJB);
       EntityControllerHome ecHome = (EntityControllerHome)
              PortableRemoteObject.narrow(
              theLookedUpObject,
              EntityControllerHome.class);
       try{
       EntityController entityController = ecHome.create();

       personUID = entityController.setProvider(personVO, businessTriggerCd, nbsSecurityObj);
       //personUID =  entityController.getProvider(personVO.getThePersonDT().getPersonUid(),nbsSecurityObj).getThePersonDT().getPersonUid();
       if (personVO.getTheStateDefinedFieldDataDTCollection() != null
				&& personVO.getTheStateDefinedFieldDataDTCollection().size() > 0) {
         logger.debug("Inside setLDF of EntityProxy for provider");
         // code for new ldf backend.
         LDFHelper ldfHelper = LDFHelper.getInstance();
         ldfHelper.setLDFCollection(personVO.getTheStateDefinedFieldDataDTCollection() , personVO.getLdfUids(),
         NEDSSConstants.PROVIDER_LDF,
         null,
         personUID,nbsSecurityObj
         );
        }

     }
       catch(Exception e){
         throw new NEDSSException("Error calling entity controller"+e.getMessage());
       }
       }
       return personUID;
   }catch(Exception e){
       logger.fatal("EntityProxyEJB.setProvider:"+e.getMessage(),e);
		throw new NEDSSSystemException(e.getMessage(),e);
   }
   }
   
    public java.lang.Long setPlace(PlaceVO placeVO, NBSSecurityObj nbsSecurityObj) throws EJBException, NEDSSException,
            NEDSSConcurrentDataException, NEDSSAppException
    {
    	try{
        Long placeUID = new Long(-1);
        if (placeVO.isItNew() || placeVO.isItDirty())
        {
            // TODO: Replace with Place permissions
            boolean check1 = nbsSecurityObj.getPermission("PROVIDER", "MANAGE");
            if (!check1)
            {
                throw new NEDSSException("User does not have permission to call EntityProxyEJB.setProvider()");
            }
            String businessTriggerCd = null;
            // TODO: Replace with Place businessTriggers
            if (placeVO.isItNew())
                businessTriggerCd = "PRV_CR";
            else
                businessTriggerCd = "PRV_EDIT";

            NedssUtils nedssUtils = new NedssUtils();
            Object theLookedUpObject;
            logger.debug("get reference for EntityControllerEJB");
            theLookedUpObject = nedssUtils.lookupBean(JNDINames.EntityControllerEJB);
            EntityControllerHome ecHome = (EntityControllerHome) PortableRemoteObject.narrow(theLookedUpObject,
                    EntityControllerHome.class);
            try
            {
                EntityController entityController = ecHome.create();

                placeUID = entityController.setPlace(placeVO, businessTriggerCd, nbsSecurityObj);
                /*
                 * if(placeVO.getTheStateDefinedFieldDataDTCollection() != null)
                 * { logger.debug("Inside setLDF of EntityProxy for provider");
                 * // code for new ldf backend. LDFHelper ldfHelper =
                 * LDFHelper.getInstance(); ldfHelper.setLDFCollection(placeVO.
                 * getTheStateDefinedFieldDataDTCollection() ,
                 * placeVO.getLdfUids(), NEDSSConstants.PROVIDER_LDF, null,
                 * personUID,nbsSecurityObj ); }
                 */
            }
            catch (Exception e)
            {
                throw new NEDSSException("Error calling entity controller" + e.getMessage());
            }
        }
        return placeUID;
    	}catch(Exception e){
    	       logger.fatal("EntityProxyEJB.setPlace:"+e.getMessage(),e);
    			throw new NEDSSSystemException(e.getMessage(),e);
    	   }
    }

  /**
   * The method sets the Person during merge
   * @roseuid
   * @J2EE_METHOD  --  setPersonForMerge
   * @param personVO   the PersonVO object
   * @param nbsSecurityObj the NBSSecurityObj
   * @return personUid  the Long
   * @throws EJBException
   * @throws NEDSSSystemException
   * @throws NEDSSConcurrentDataException
   */
  private Long setPersonForMerge(PersonVO personVO,
                                 NBSSecurityObj nbsSecurityObj) throws javax.
      ejb.EJBException, NEDSSSystemException, NEDSSConcurrentDataException {
    logger.info("setting person during merge-----------------");

    Long personUid = null;

    try {

      /**
       * checking the user permission for merging the person
       */
      boolean check2 = nbsSecurityObj.getPermission(
          NBSBOLookup.PATIENT,
          NBSOperationLookup.MERGE);
      /**
       * Throwing Exceptions if the user don't have permission to merge persons
       */
      if (check2 == false) {
        logger.error("don't have permission to merge persons");
        throw new NEDSSSystemException("don't have permission to merge persons");
      }

      else {
        NedssUtils nedssUtils = new NedssUtils();
        Object theLookedUpObject;
        logger.debug("get reference for EntityControllerEJB");
        theLookedUpObject = nedssUtils.lookupBean(
            JNDINames.EntityControllerEJB);

        EntityControllerHome ecHome = (EntityControllerHome)
            PortableRemoteObject.narrow(
            theLookedUpObject,
            EntityControllerHome.class);
        EntityController entityController = ecHome.create();

        personUid = entityController.setPerson(personVO,
                                               nbsSecurityObj);

        return personUid;
      }
    }
    catch (NEDSSConcurrentDataException ex) {
      logger.fatal("The entity cannot be updated as concurrent access is not allowed!");
      logger.debug("EntityProxyEJB.setPersonForMerge: Concurrent access occurred in EntityProxyEJB : " +
                   ex.toString());
      throw new NEDSSConcurrentDataException("EntityProxyEJB.setPersonForMerge: Concurrent access occurred in EntityProxyEJB : " +
                                             ex.getMessage(),ex);
    }
    catch (Exception e) {
      logger.fatal("EntityProxyEJB.setPersonForMerge: " + e.getMessage(),e);
      e.printStackTrace();
      throw new NEDSSSystemException(e.getMessage(),e);
    }

  }

  /**
   * Set the associated session context. The container calls this method after the instance
   * creation. The enterprise Bean instance should store the reference to the context
   * object in an instance variable. This method is called with no transaction context.
   * @param sessioncontext   the SessionContext
   * @throws EJBException
   * @throws RemoteException
   * @roseuid 3C55D9CC0169
   * @J2EE_METHOD  --  setSessionContext
   */
  public void setSessionContext(SessionContext sessioncontext) throws
      EJBException, RemoteException {
  }

  /**
   * Gets the person values from the database
   * @roseuid 3C55D79100E5
   * @J2EE_METHOD  --   getPerson
   * @param personUid   the Long
   * @param nbsSecurityObj   the NBSSecurityObj
   * @return  personVO    the PersonVO object
   * @throws EJBException
   * @throws NEDSSSystemException
   * @throws NEDSSConcurrentDataException
   */
  public PersonVO getPerson(Long personUid, NBSSecurityObj nbsSecurityObj) throws
      javax.ejb.EJBException, NEDSSSystemException,
      NEDSSConcurrentDataException {
    logger.info("getting person-----------------");

    try {

      /* No need of getPermisiion method as per the design 05/31/2002  */
      NedssUtils nedssUtils = new NedssUtils();
      Object theLookedUpObject;
      logger.debug("get reference for EntityControllerEJB");
      /**
       * get the reference of EntityControllerEJB
       */
      theLookedUpObject = nedssUtils.lookupBean(
          JNDINames.EntityControllerEJB);

      EntityControllerHome ecHome = (EntityControllerHome) PortableRemoteObject.
          narrow(
          theLookedUpObject,
          EntityControllerHome.class);
      EntityController entityController = ecHome.create();
      PersonVO personVO = entityController.getPerson(personUid,
          nbsSecurityObj);
			if (personVO.getThePersonDT() != null && (personVO.getThePersonDT().getElectronicInd() != null
					&& !personVO.getThePersonDT().getElectronicInd().equals(NEDSSConstants.ELECTRONIC_IND_ELR))) {
				// for LDFs
				ArrayList<Object> ldfList = new ArrayList<Object>();
				try {
					// ldfList = (ArrayList<Object> ) getLDFCollection(personUid.longValue());
					// code for new ldf back end condition code is null for person
					LDFHelper ldfHelper = LDFHelper.getInstance();
					ldfList = (ArrayList<Object>) ldfHelper.getLDFCollection(personUid, null, nbsSecurityObj);

				} catch (Exception e) {
					logger.error("Exception occured while retrieving LDFCollection<Object>  = " + e.toString());
				}

				if (ldfList != null) {
					logger.debug("Before setting LDFCollection<Object>  = " + ldfList.size());
					personVO.setTheStateDefinedFieldDataDTCollection(ldfList);
				}
			}

      /**
       * Check the user permission to view person details
       
      boolean check2 = nbsSecurityObj.checkDataAccess(personVO.getThePersonDT(),
          "PERSON", "VIEW");

      if (check2 == false) {
        logger.error("don't have permission to access person info");
        throw new NEDSSSystemException(
            "don't have permission to access person info");
      }
      else {

        return personVO;
      }
      */
      return personVO;
    }
    catch (Exception e) {
      logger.fatal("EntityProxyEJB.getPerson: " + e.getMessage(),e);
      e.printStackTrace();
      throw new NEDSSSystemException(e.getMessage(),e);
    }
  }


  /**
    * @roseuid 3D9B4AFD026B
    * @J2EE_METHOD  --  getProvider
    * @param personUid
    * @param nbsSecurityObj
    * @throws EJBException
    * @throws NEDSSSystemException
    * @throws NEDSSConcurrentDataException
    */
   public PersonVO getProvider    (Long personUid, NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException, NEDSSSystemException, NEDSSConcurrentDataException
   {
     logger.info("getting provider-----------------");

   try {

     /* No need of getPermisiion method as per the design 05/31/2002  */
     NedssUtils nedssUtils = new NedssUtils();
     Object theLookedUpObject;
     logger.debug("get reference for EntityControllerEJB");
     /**
      * get the reference of EntityControllerEJB
      */
     theLookedUpObject = nedssUtils.lookupBean(
         JNDINames.EntityControllerEJB);

     EntityControllerHome ecHome = (EntityControllerHome) PortableRemoteObject.
         narrow(
         theLookedUpObject,
         EntityControllerHome.class);
     EntityController entityController = ecHome.create();
     PersonVO personVO = entityController.getProvider(personUid,
         nbsSecurityObj);
     if (personVO.getThePersonDT() != null && (personVO.getThePersonDT().getElectronicInd() != null
				&& !personVO.getThePersonDT().getElectronicInd().equals(NEDSSConstants.ELECTRONIC_IND_ELR))) {
     //for LDFs
     ArrayList<Object> ldfList = new ArrayList<Object> ();
     try
     {
       LDFHelper ldfHelper = LDFHelper.getInstance();
       ldfList = (ArrayList<Object> )ldfHelper.getLDFCollection(personUid,null,nbsSecurityObj);
      }
      catch (Exception e) {
        logger.error(
          "Exception occured while retrieving LDFCollection<Object>  in getProvider()= " +
          e.toString());
      }

      if (ldfList != null) {
        logger.debug("Before setting LDFCollection<Object>  = " + ldfList.size());
        personVO.setTheStateDefinedFieldDataDTCollection(ldfList);
      }
     }

     /**
      * Check the user permission to view person details - As per Release1.1.1 we don't have to
      * check the security here. To match with the design, commenting out this portion
      */
    /* boolean check2 = nbsSecurityObj.checkDataAccess(personVO.getThePersonDT(),
         "PROVIDER", "MANAGE");

     if (check2 == false) {
       logger.error("don't have permission to access person info");
       throw new NEDSSSystemException(
           "don't have permission to access person info");
     } */
    return personVO;

   }
   catch (Exception e) {
     logger.fatal("EntityProxyEJB:getProvider:" + e.getMessage(),e);
     e.printStackTrace();
     throw new NEDSSSystemException(e.getMessage(),e);
   }

   }

  /**
   * Access the information of person History
   * @roseuid 3C55D79101EA
   * @J2EE_METHOD  --  getPersonHist
   * @param personUid   the Long -
   * @param personHistSeq     the Integer
   * @param nbsSecurityObj    the NBSSecurityObj
   * @return personVO    the PersonVO
   * @throws EJBException
   * @throws NEDSSSystemException
   */
  public PersonVO getPersonHist(Long personUid, Integer personHistSeq,
                                NBSSecurityObj nbsSecurityObj) throws javax.ejb.
      EJBException, NEDSSSystemException {
    logger.info("getting personHist-----------------");

    try {

      PersonVO personVO = null;
      /**
       * Check the user permission to view the person history
       */
      boolean check1 = nbsSecurityObj.getPermission(NBSBOLookup.PATIENT,
          NBSOperationLookup.VIEW);

      if (check1 == false) {
        logger.error("don't have permission to view person");
        throw new NEDSSSystemException("don't have permission to view person");
      }
      else {

        PersonHistoryManager personHistoryManager =
            new PersonHistoryManager();
        personVO = personHistoryManager.load(personUid, personHistSeq);
      }

     /* boolean check2 = nbsSecurityObj.checkDataAccess(personVO.getThePersonDT(),
          "PERSON", "VIEW");

      if (check2 == false) {
        logger.error("don't have permission to view person");
        throw new NEDSSSystemException("don't have permission to view person");
      }
      else {

        return personVO;
      }
      */
      return personVO;
    }
    catch (Exception e) {
      logger.fatal("EntityProxyEJB: getPersonHist:" + e.getMessage(), e);
      throw new NEDSSSystemException(e.getMessage(), e);
    }
  }

  /**
   * @roseuid 3C87D74E037F
   * @J2EE_METHOD  --  findPerson
   */
  /**
   * It takes the Search Criteria(PersonSearchVO) as a parameter
   * and returns the search results  as collection of PersonSrchResultVO
   * @param thePersonSearchVO  the PersonSearchVO Object
   * @param cacheNumber    the int
   * @param fromIndex     the int
   * @param nbsSecurityObj    the NBSSecurityObj
   * @return displayPersonList    the Collection<Object>  of PersonSrchResultVO
   * @throws EJBException
   * @throws NEDSSSystemException
   */
  public Collection<Object>  findPerson(PersonSearchVO thePersonSearchVO,
                               int cacheNumber, int fromIndex,
                               NBSSecurityObj nbsSecurityObj) throws
      EJBException, NEDSSSystemException {
    logger.info("!----------find an person-----------------");

    boolean check1 = false;
    boolean check2 = false;
    boolean checkActive = thePersonSearchVO.isActive();
    boolean checkInActive = thePersonSearchVO.isInActive();
   String dataAccessWhereClause = "";
    ArrayList<Object> displayPersonList = new ArrayList<Object> ();

    try {

      if (checkActive == true) {
        check1 = nbsSecurityObj.getPermission(NBSBOLookup.PATIENT,
                                              NBSOperationLookup.FIND);
        /*  Sets the flags for Active and Inactive in the SearchVO based on the security */

      }
      checkInActive = thePersonSearchVO.isInActive();
      if (thePersonSearchVO.isSuperceded() || thePersonSearchVO.isInActive()) {
        check2 = nbsSecurityObj.getPermission(NBSBOLookup.PATIENT,
                                              NBSOperationLookup.FINDINACTIVE);
      }
      if ( (check1 == false) && (check2 == false)) {

        logger.error(
            "don't have permission to search Person's information");
        throw new NEDSSSystemException(
            "don't have permission to search Person's information");
      }
      else if ( (check1 == true) && (check2 == true)) {
        String whereClauseActive = nbsSecurityObj.getDataAccessWhereClause(
            NBSBOLookup.PATIENT,
            NBSOperationLookup.FIND);
        //System.out.println("The where clause value for active " + whereClauseActive);
        if (whereClauseActive == null) {
          whereClauseActive = "";
        }
        else {
          whereClauseActive = " AND " + whereClauseActive;

        }
        String whereClauseInActive = nbsSecurityObj.getDataAccessWhereClause(
            NBSBOLookup.PATIENT,
            NBSOperationLookup.FINDINACTIVE);
        //System.out.println("The where clause for Inactive " +  whereClauseInActive);

        if (whereClauseInActive == null) {
          whereClauseInActive = "";
        }
        else {
          whereClauseInActive = " AND " + whereClauseInActive;

        }
        dataAccessWhereClause = whereClauseActive + whereClauseInActive;
        thePersonSearchVO.setDataAccessWhereClause(dataAccessWhereClause);
        thePersonSearchVO.setStatusCodeActive(NEDSSConstants.
                                              RECORD_STATUS_ACTIVE);
        thePersonSearchVO.setStatusCodeInActive(NEDSSConstants.
                                                RECORD_STATUS_LOGICAL_DELETE);

        FindPersonDAOImpl findPersonDAOImpl = new FindPersonDAOImpl();
        displayPersonList = findPersonDAOImpl.findPersonsByKeyWords(
            thePersonSearchVO,
            cacheNumber, fromIndex);
        return displayPersonList;
      }
      else if ( (check1 == true) && (check2 == false)) {
        dataAccessWhereClause = nbsSecurityObj.getDataAccessWhereClause(
            NBSBOLookup.PATIENT,
            NBSOperationLookup.FIND);
        //System.out.println("The where clause value for active  when check1 is true " + dataAccessWhereClause);

        if (dataAccessWhereClause == null) {
          dataAccessWhereClause = " ";
        }
        else {
          dataAccessWhereClause = " AND " + dataAccessWhereClause;

        }
        thePersonSearchVO.setStatusCodeActive(NEDSSConstants.
                                              RECORD_STATUS_ACTIVE);
        thePersonSearchVO.setDataAccessWhereClause(dataAccessWhereClause);

        FindPersonDAOImpl findPersonDAOImpl = new FindPersonDAOImpl();
        displayPersonList = findPersonDAOImpl.findPersonsByKeyWords(
            thePersonSearchVO,
            cacheNumber, fromIndex);
        return displayPersonList;
      }
      else if ( (check1 == false) && (check2 == true)) {
        dataAccessWhereClause = nbsSecurityObj.getDataAccessWhereClause(
            NBSBOLookup.PATIENT,
            NBSOperationLookup.FIND);
        //System.out.println("The where clause value for active  when check1 is false and check2 is true: " + dataAccessWhereClause);

        if (dataAccessWhereClause == null) {
          dataAccessWhereClause = " ";
        }
        else {
          dataAccessWhereClause = " AND " + dataAccessWhereClause;
        }
        thePersonSearchVO.setStatusCodeActive(NEDSSConstants.
                                              RECORD_STATUS_LOGICAL_DELETE);
        thePersonSearchVO.setDataAccessWhereClause(dataAccessWhereClause);

        FindPersonDAOImpl findPersonDAOImpl = new FindPersonDAOImpl();
        displayPersonList = findPersonDAOImpl.findPersonsByKeyWords(
            thePersonSearchVO,
            cacheNumber, fromIndex);
        return displayPersonList;
      }
    } // end of try
    catch (Exception e) {
      logger.fatal(" EntityProxyEJB.findPerson: " + e.getMessage(),e);
      e.printStackTrace();
      throw new NEDSSSystemException(e.getMessage(),e);
    }

    return displayPersonList;
  }

  //*******************************************************************************************/
  //BEGIN: findPatient(PatientSearchVO thePatientSearchVO,
  //                           int cacheNumber, int fromIndex,
  //                           NBSSecurityObj nbsSecurityObj)
  //*******************************************************************************************/
  /**
   * It takes the Search Criteria(PatientSearchVO) as a parameter
   * and returns the search results  as collection of PatientSrchResultVO
   * @param thePatientSearchVO
   * @param cacheNumber
   * @param fromIndex
   * @param nbsSecurityObj
   * @return Collection
   */
  public Collection<Object>  findPatient(PatientSearchVO thePatientSearchVO,
                               int cacheNumber, int fromIndex,
                               NBSSecurityObj nbsSecurityObj)
  {
    logger.info("!----------find Patients-----------------");

    boolean findActivePermission = false;
    boolean findInActiveORSupercededPermission = false;
    boolean checkActiveStatus = false;
    boolean checkInActiveStatus = false;
    boolean checkSupercededStatus = false;
    String recordStatusCdToSearch = "";	
    String dataAccessWhereClause = "";
    final String AND = " AND ";
    ArrayList<Object>  displayPersonList = new ArrayList<Object> ();

    try
    {
      //Check what kinds of patients to search for: active, inactive, and/or superceded
      checkActiveStatus = thePatientSearchVO.isActive();
      checkInActiveStatus = thePatientSearchVO.isInActive();
      checkSupercededStatus = thePatientSearchVO.isSuperceded();

      //Check if the user have the permission to conduct the search for active records
      if (checkActiveStatus)
      {
        findActivePermission = nbsSecurityObj.getPermission(NBSBOLookup.PATIENT,
                                              NBSOperationLookup.FIND);
      }

      //Check if the user have the permission to conduct the search for inactive/superceded records
      if (checkSupercededStatus || checkInActiveStatus)
      {
        findInActiveORSupercededPermission = nbsSecurityObj.getPermission(NBSBOLookup.PATIENT,
                                              NBSOperationLookup.FINDINACTIVE);
      }

      //Abort if both permission checks fail
      if ( !findActivePermission && !findInActiveORSupercededPermission) {

        logger.fatal(
            "don't have permission to search Patient's information - abort");
        throw new NEDSSSystemException(
            "don't have permission to search Patient's information - abort");
      }

      //Data access where clause to read active records
      String whereClauseActive = " ";
      if(findActivePermission)
      {
        whereClauseActive = nbsSecurityObj.getDataAccessWhereClause(
           NBSBOLookup.PATIENT,
           NBSOperationLookup.FIND);
        whereClauseActive = whereClauseActive != null ? AND + whereClauseActive : " ";
        thePatientSearchVO.setStatusCodeActive(NEDSSConstants.RECORD_STATUS_ACTIVE);
        recordStatusCdToSearch = "'" + NEDSSConstants.RECORD_STATUS_ACTIVE + "'";
        dataAccessWhereClause = whereClauseActive;
      }

      //Data access where clause to read inactive and/or superceded records
      String whereClauseInActiveSuperceded = " ";
      if(findInActiveORSupercededPermission)
      {
        whereClauseInActiveSuperceded = nbsSecurityObj.getDataAccessWhereClause(
            NBSBOLookup.PATIENT,
            NBSOperationLookup.FINDINACTIVE);
        whereClauseInActiveSuperceded = whereClauseInActiveSuperceded != null ? AND +
            whereClauseInActiveSuperceded : " ";

        dataAccessWhereClause += whereClauseInActiveSuperceded;

        if(checkInActiveStatus)
        {
          thePatientSearchVO.setStatusCodeInActive(NEDSSConstants.
                                                RECORD_STATUS_LOGICAL_DELETE);
          recordStatusCdToSearch += recordStatusCdToSearch.equals("") ?
              "'" +NEDSSConstants.RECORD_STATUS_LOGICAL_DELETE+"'" :
              " , " + "'"+NEDSSConstants.RECORD_STATUS_LOGICAL_DELETE+"'";
        }
        if(checkSupercededStatus)
        {
          thePatientSearchVO.setStatusCodeSuperCeded(NEDSSConstants.RECORD_STATUS_SUPERCEDED);
          recordStatusCdToSearch += recordStatusCdToSearch.equals("") ?
              "'" +NEDSSConstants.RECORD_STATUS_SUPERCEDED+"'" :
              " , " + "'"+NEDSSConstants.RECORD_STATUS_SUPERCEDED+"'";

        }
      }

      //The compound data access where clause is
      String recordStatusWhereClause = AND + "p.record_status_cd in (" + recordStatusCdToSearch + ")";
      String personCdWhereClause = AND + "p.cd = 'PAT'";
      dataAccessWhereClause += recordStatusWhereClause;
      dataAccessWhereClause += personCdWhereClause;
      thePatientSearchVO.setDataAccessWhereClause(dataAccessWhereClause);

      //Do the finding
      EntityProxyHelper helper = EntityProxyHelper.getInstance();

      return helper.findPatientsByKeyWords(thePatientSearchVO, cacheNumber, fromIndex, nbsSecurityObj);
    } // end of try
    catch (Exception e)
    {
      logger.fatal("EntityProxyEJB.findPatient:"  + e.getMessage(),e);
      throw new NEDSSSystemException(e.getMessage(),e);
    }
  }
  
  /**
   * findPatientByQuery: this is used from Home Page Custom Queues. While saving a Queue from Event Search, the query used it is stored in the DB.
   * Then, if user open the custom queue from Home Page, the query stored in the DB will be retrieved and executed during this method to get the list
   * of patients.
   * @param thePatientSearchVO
   * @param cacheNumber
   * @param fromIndex
   * @param finalQuery
   * @param nbsSecurityObj
   * @return
   */
  public Collection<Object>  findPatientByQuery(PatientSearchVO thePatientSearchVO,
          int cacheNumber, int fromIndex, String finalQuery,
          NBSSecurityObj nbsSecurityObj)
          
          {    
            //Do the finding
	  		EntityProxyHelper helper = EntityProxyHelper.getInstance();

	  		return helper.findPatientsByQuery(thePatientSearchVO, finalQuery, cacheNumber, fromIndex, nbsSecurityObj);
      
          }
  
  
  //###################################################################/
  //END: Collection<Object>  findPatient(PatientSearchVO thePatientSearchVO,
  //                          int cacheNumber, int fromIndex,
  //                          NBSSecurityObj nbsSecurityObj)
  //###################################################################/


  /**
  * @roseuid 3D615B080384
  * @J2EE_METHOD  --  findProvider
  * @param theProviderSearchVO
  * @param cacheNumber
  * @param fromIndex
  * @param nbsSecurityObj
  * @throws RemoteException
  * @throws EJBException
  * @throws NEDSSSystemException
  **/
  public Collection<Object>  findProvider(ProviderSearchVO theProviderSearchVO,
                             int cacheNumber, int fromIndex,   NBSSecurityObj nbsSecurityObj) throws
        RemoteException, EJBException, NEDSSSystemException {
    logger.info("find provider---------------------------------");
    ArrayList<Object> displayProviderList = new ArrayList<Object> ();
    try{
    displayProviderList = EntityProxyHelper.getInstance().findProvidersByKeyWords(theProviderSearchVO, cacheNumber, fromIndex);
    }
    catch(Exception e){
      logger.fatal("EntityProxyEJB.findProvider: " + e.getMessage(),e);
      throw new NEDSSSystemException(e.getMessage(),e);
    }
    return displayProviderList;
}

  /**
   * @roseuid 3E6FB44B0015
   * @J2EE_METHOD  --  inactivateProvider
   * @param personVO
   * @param nbsSecurityObj
   * @throws EJBException
   * @throws NEDSSException
   * @throws NEDSSConcurrentDataException
   */
  public boolean inactivateProvider    (PersonVO personVO, NBSSecurityObj nbsSecurityObj) throws EJBException, NEDSSException, NEDSSConcurrentDataException
  {
     logger.info("inactivating provider---------------------------");
     boolean check1 = nbsSecurityObj.checkDataAccess(personVO.getThePersonDT(),
     "PROVIDER", "MANAGE");
     if(!check1){
       throw new NEDSSException("User does not have manage provider permission needed to inactivate provider");
     }
     personVO.setItDirty(true);
     personVO.setItDelete(false);
     personVO.getThePersonDT().setItDirty(true);


     try{

       NedssUtils nedssUtils = new NedssUtils();
       Object theLookedUpObject;
       logger.debug("getting hold of EntityControllerEJB");
       theLookedUpObject = nedssUtils.lookupBean(JNDINames.EntityControllerEJB);

       EntityControllerHome ecHome = (EntityControllerHome) PortableRemoteObject.
           narrow(theLookedUpObject, EntityControllerHome.class);
       EntityController entityController = ecHome.create();
       Long personUID = entityController.setProvider(personVO, "PRV_INA",
           nbsSecurityObj);
     }
     catch(Exception e){
       logger.fatal("EntityProxyEJB.inactivateProvider " + e.getMessage(),e);
       throw new NEDSSException(e.getMessage(),e);

     }
    logger.info("finished inactivating provider---------------------");
    return true;



  }

  /**
   * Sending the Person object with the delete attributes to the database thorugh entityController
   * @roseuid 3C91264A0333
   * @J2EE_METHOD  --  deletePerson
   * @param personVO    the PersonVO object
   * @param nbsSecurityObj   the NBSSecurityObj
   * @return   the boolean(false if the Person is having the active participation else true )
   * @throws RemoteException
   * @throws EJBException
   * @throws NEDSSSystemException
   * @throws NEDSSConcurrentDataException
   */
  public boolean deletePerson(PersonVO personVO, NBSSecurityObj nbsSecurityObj) throws
      java.rmi.RemoteException,
      javax.ejb.EJBException, NEDSSSystemException,
      NEDSSConcurrentDataException

  {
    logger.info("deleting person-----------------");

    try {

      /**
       * Checkint the user permission for deleting the person
       */

      boolean check1 = nbsSecurityObj.getPermission(NBSBOLookup.PATIENT,
          NBSOperationLookup.DELETE);

      if (check1 == false) {
        logger.error(
            "don't have permission to delete person's information");
        throw new NEDSSSystemException(
            "don't have permission to delete person's information");
      }

      int count = 0;
      PersonDAOImpl personDao = new PersonDAOImpl();
      /**
       * Checking whether any active participation exists for the person
       */
      count = personDao.checkDeletePerson(personVO.getThePersonDT().
                                          getPersonUid());

      if (count > 0) {
        logger.error(
            "Unable to delete this person due to Active Participations");

        return false;
      }
      else {

        NedssUtils nedssUtils = new NedssUtils();
        Object theLookedUpObject;
        logger.debug("preparing to delete person info");
        logger.debug("get reference for EntityControllerEJB");
        theLookedUpObject = nedssUtils.lookupBean(
            JNDINames.EntityControllerEJB);

        EntityControllerHome ecHome = (EntityControllerHome)
            PortableRemoteObject.narrow(
            theLookedUpObject,
            EntityControllerHome.class);
        EntityController entityController = ecHome.create();
        PersonDT personDT = entityController.getPersonInfo(personVO.
            getThePersonDT().getPersonUid(),
            nbsSecurityObj);
        Integer aVctrlNbr = personDT.getVersionCtrlNbr();
        Integer bVctrlNbr = personVO.getThePersonDT().getVersionCtrlNbr();
        if (aVctrlNbr != null && bVctrlNbr != null &&
            bVctrlNbr.intValue() != aVctrlNbr.intValue()) {
          logger.error("Throwing NEDSSConcurrentDataException");
          throw new NEDSSConcurrentDataException
              ("NEDSSConcurrentDataException: The data has been modified by other user, please verify!");
        }

        personDT.setItDirty(true);
        personDT.setItDelete(false);
        personDT.setItNew(false);

        /**
             * setting the required attributes of the PersonDT in the prepareVOUtils
         */
        PrepareVOUtils prepareVOUtils = new PrepareVOUtils();
        PersonDT newPersonDT = (PersonDT) prepareVOUtils.prepareVO(
            personDT, NEDSSConstants.PATIENT, NEDSSConstants.PAT_DEL,
            DataTables.PERSON_TABLE,NEDSSConstants.BASE,
            nbsSecurityObj);
        entityController.setPersonInfo(newPersonDT, nbsSecurityObj);

        return true;
      }
    }
    catch (NEDSSConcurrentDataException ex) {
      logger.fatal("EntityProxyEJB.deletePerson: NEDSSConcurrentDataException: " + ex.getMessage(), ex);
      throw new NEDSSConcurrentDataException(ex.getMessage(), ex);

    }
    catch (Exception e) {
      logger.fatal("EntityProxyEJB.deletePerson: Exception: " + e.getMessage(), e);
      throw new NEDSSSystemException(e.getMessage(), e);
    }
  }

  /**
   * deletes MasterPatientRecord if no revisions are associated
   * @param personVO
   * @param nbsSecurityObj
   * @return
   * @throws javax.ejb.EJBException
   * @throws NEDSSSystemException
   * @throws NEDSSConcurrentDataException
   * @roseuid 3D99CB7700A0
   * @J2EE_METHOD  --  deleteMPR
   */
  public boolean deleteMPR    (PersonVO personVO, NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException, NEDSSSystemException, NEDSSConcurrentDataException
  {
    logger.info("********** deleteMPR method call **********");

    try {

      /**
       * Checkint the user permission for deleting the person
       */

      boolean check1 = nbsSecurityObj.getPermission(NBSBOLookup.PATIENT,
          NBSOperationLookup.DELETE);

      if (check1 == false) {
        logger.error(
            "don't have permission to delete MPR's information");
        throw new NEDSSSystemException(
            "don't have permission to delete MPR's information");
      }

      int count = 0;

      EntityProxyHelper entityProxyHelper = EntityProxyHelper.getInstance();
      Collection<Object>  uidList = entityProxyHelper.findActivePatientUidsByParentUid(personVO.getThePersonDT().getPersonUid());
      count = uidList.size();
      if (count > 1) {
        logger.error("Unable to delete this MPR due to Active Revisions");

        return false;
      }
      else {

        NedssUtils nedssUtils = new NedssUtils();
        Object theLookedUpObject;
        logger.debug("preparing to delete person info");
        logger.debug("get reference for EntityControllerEJB");
        theLookedUpObject = nedssUtils.lookupBean(
            JNDINames.EntityControllerEJB);

        EntityControllerHome ecHome = (EntityControllerHome)
            PortableRemoteObject.narrow(
            theLookedUpObject,
            EntityControllerHome.class);
        EntityController entityController = ecHome.create();
        PersonDT personDT = entityController.getPersonInfo(personVO.
            getThePersonDT().getPersonUid(),
            nbsSecurityObj);
        Integer aVctrlNbr = personDT.getVersionCtrlNbr();
        Integer bVctrlNbr = personVO.getThePersonDT().getVersionCtrlNbr();
        if (aVctrlNbr != null && bVctrlNbr != null &&
            bVctrlNbr.intValue() != aVctrlNbr.intValue()) {
          logger.error("Throwing NEDSSConcurrentDataException");
          throw new NEDSSConcurrentDataException
              ("NEDSSConcurrentDataException: The data has been modified by other user, please verify!");
        }

        personDT.setItDirty(true);
        personDT.setItDelete(false);
        personDT.setItNew(false);

        personVO.setThePersonDT(personDT);

        entityController.setMPR(personVO, NEDSSConstants.PAT_DEL, nbsSecurityObj);

        return true;
      }
    }
    catch (NEDSSConcurrentDataException ex) {
      logger.fatal("EntityProxyEJB.deleteMPR: NEDSSConcurrentDataException: " + ex.getMessage(),ex);
      throw new NEDSSConcurrentDataException(ex.getMessage(),ex);

    }
    catch (Exception e) {
    	logger.fatal("EntityProxyEJB.deleteMPR: Exception: " + e.getMessage(),e);
        throw new NEDSSConcurrentDataException(e.getMessage(),e);
    }

  }

  /**
   * Sending  OrganizationVO to the database to set the values to the appropriate tables
   * @roseuid 3D9B4B010177
   * @J2EE_METHOD  --  setOrganization
   * @param organizationVO    the OrganizationVO
   * @param nbsSecurityObj    the NBSSecurityObj
   * @return organizationUid    the Long
   * @throws EJBException
   * @throws NEDSSSystemException
   * @throws NEDSSConcurrentDataException
   */
  public Long setOrganization(OrganizationVO organizationVO,
                              NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException,
      javax.ejb.EJBException, NEDSSSystemException, NEDSSConcurrentDataException,NEDSSAppException {
    logger.debug(
        " ************     inside set organization ejb ########################");
    Long organizationUid = null;

    try {

      if (organizationVO.isItNew() || organizationVO.isItDirty())  {

        boolean check1 = nbsSecurityObj.getPermission(
            NBSBOLookup.ORGANIZATION,
            NBSOperationLookup.MANAGE);

        if (check1 == false) {
          logger.error("don't have permission to add organizationVO");
          throw new NEDSSSystemException(
              "don't have permission to add organizationVO");
        }
        else {

          /*  PrepareVOUtils prepareVOUtils = new PrepareVOUtils();
            OrganizationDT newOrganizationDT = (OrganizationDT) prepareVOUtils.
                prepareVO(organizationVO.getTheOrganizationDT(),
                          "ORGANIZATION",
                          NEDSSConstants.ORG_CR,
                          DataTables.ORGANIZATION_TABLE,
                          NEDSSConstants.BASE,
                          nbsSecurityObj);
            organizationVO.setTheOrganizationDT(newOrganizationDT);
            NedssUtils nedssUtils = new NedssUtils();
            Object theLookedUpObject;
            logger.debug("get reference for EntityControllerEJB");
            theLookedUpObject = nedssUtils.lookupBean(
                JNDINames.EntityControllerEJB);
            EntityControllerHome ecHome = (EntityControllerHome)
                PortableRemoteObject.narrow(
                theLookedUpObject,
                EntityControllerHome.class);
            EntityController entityController = ecHome.create();
            organizationUid = entityController.setOrganization(organizationVO,
                nbsSecurityObj);
                   }
                 }
                 else if (organizationVO.isItDirty()) {
                   boolean check2 = nbsSecurityObj.getPermission(
              NBSBOLookup.ORGANIZATION,
              NBSOperationLookup.EDIT);
                   if (check2 == false) {
            logger.error(
                "don't have permission to edit organization info");
            throw new NEDSSSystemException(
                "don't have permission to edit organization info");
                   }
                   else {*/

          /**
               * setting the attributes to the OrganizationDT and throguh PrepareVOUtils
           */
          /*  PrepareVOUtils prepareVOUtils = new PrepareVOUtils();
            OrganizationDT newOrganizationDT = (OrganizationDT) prepareVOUtils.
                prepareVO(organizationVO.getTheOrganizationDT(),
                          "ORGANIZATION",
                          "ORG_EDIT",
                          "ORGANIZATION",
                          "BASE",
                          nbsSecurityObj);
            organizationVO.setTheOrganizationDT(newOrganizationDT);
            NedssUtils nedssUtils = new NedssUtils();
            Object theLookedUpObject;
            logger.debug("get reference for EntityControllerEJB"); */

          /**
           * Check whether the quick code is unique
           */

       // if(!(organizationVO.isItDirty()))
        //{
          boolean quickBoolean = EntityProxyHelper.getInstance().
              isQuickCodeUnique(organizationVO);
          if (quickBoolean == false) {
            logger.error("The quickCd is not unique for Organization");
            throw new NEDSSAppException(
                " The quickCd is not unique for Organization");
          }
        //}


            /**
             * getting the reference for EntityControllerEJB
             */

            NedssUtils nedssUtils = new NedssUtils();
            Object theLookedUpObject;
            theLookedUpObject = nedssUtils.lookupBean(
                JNDINames.EntityControllerEJB);

            EntityControllerHome ecHome = (EntityControllerHome)
                PortableRemoteObject.narrow(
                theLookedUpObject,
                EntityControllerHome.class);
            EntityController entityController = ecHome.create();
            String businessTriggerCode = null;
            if (organizationVO.isItNew())
              businessTriggerCode = NEDSSConstants.ORG_CR;
            else
              businessTriggerCode = NEDSSConstants.ORG_EDIT;

             organizationUid = entityController.setOrganization(organizationVO,
                businessTriggerCode, nbsSecurityObj);
              //return organizationUid;

        }
        //prepare the state defined field collection
        // sd 040182003 move this code to StateDefinedProxy in function setLDFCollection.
     /*   if (organizationVO.getTheStateDefinedFieldDTCollection() != null) {
         Iterator<Object>  iter = organizationVO.getTheStateDefinedFieldDTCollection().
              iterator();

          while (iter.hasNext()) {

            StateDefinedFieldDT sdfDT = (StateDefinedFieldDT) iter.next();
            sdfDT.setBusinessObjType(NEDSSConstants.ORGANIZATION); // use a constant here
            sdfDT.setBusinessObjUid(organizationUid);

          } */

          //setLDFCollection(organizationVO.getTheStateDefinedFieldDTCollection(),
          //                 NEDSSConstants.ORGANIZATION_LDF,organizationUid );
         // code for new ldf backend.
        if (organizationVO.getTheOrganizationDT() != null && (organizationVO.getTheOrganizationDT().getElectronicInd() != null
				&& !organizationVO.getTheOrganizationDT().getElectronicInd().equals(NEDSSConstants.ELECTRONIC_IND_ELR))) {
         LDFHelper ldfHelper = LDFHelper.getInstance();
         ldfHelper.setLDFCollection(organizationVO.getTheStateDefinedFieldDataDTCollection(), organizationVO.getLdfUids(),
                           NEDSSConstants.ORGANIZATION_LDF,null,organizationUid,nbsSecurityObj);
        }


       // }
     //   else {
       //   logger.debug(
         //     "organizationVO.getTheStateDefinedFieldDTCollection() == null");
        //}
      }

    } // for try
    catch (NEDSSConcurrentDataException ex) {
      logger.fatal("EntityProxyEJB.setOrganization: NEDSSConcurrentDataException: " + ex.getMessage(),ex);
      throw new NEDSSConcurrentDataException(ex.getMessage(),ex);
    }
    catch (Exception e) {
    	logger.fatal("EntityProxyEJB.setOrganization: Exception: " + e.getMessage(),e);
      throw new NEDSSAppException(e.getMessage(),e);
    }

    return organizationUid;

  }

  /**
   * Getting the values for OrganizationVO from the database
   * @roseuid 3D9B4B020092
   * @J2EE_METHOD  --  getOrganization
   * @param organizationUid    the Long
   * @param nbsSecurityObj     the NBSSecurityObj
   * @return organizationVO    the OrganizationVO
   * @throws EJBException
   * @throws NEDSSSystemException
   */
  public OrganizationVO getOrganization(Long organizationUid,
                                        NBSSecurityObj nbsSecurityObj) throws
      java.rmi.RemoteException,
      javax.ejb.EJBException,
      NEDSSSystemException {
      logger.info("getting organization-----------------");

    try {

      /* No need of getpermission  as per the design  05/31/02
       */
      NedssUtils nedssUtils = new NedssUtils();
      Object theLookedUpObject;
      logger.debug("get reference for EntityControllerEJB");
      /**
       * getting the reference for EntityController EJB
       */
      theLookedUpObject = nedssUtils.lookupBean(
          JNDINames.EntityControllerEJB);

      EntityControllerHome ecHome = (EntityControllerHome)
          PortableRemoteObject.narrow(
          theLookedUpObject,
          EntityControllerHome.class);
      EntityController entityController = ecHome.create();
      OrganizationVO organizationVO = entityController.getOrganization(
          organizationUid,
          nbsSecurityObj);
      /** Removed the security as per the new design **/
      /**boolean check2 = nbsSecurityObj.checkDataAccess(organizationVO.
          getTheOrganizationDT(),
          "ORGANIZATION",
          "VIEW");

      if (check2 == false) {
        logger.error(
            "don't have permission to access organization info");
        throw new NEDSSSystemException(
            "don't have permission to  access organization info");
      }
      else { **/
      if (organizationVO.getTheOrganizationDT() != null && (organizationVO.getTheOrganizationDT().getElectronicInd() != null
				&& !organizationVO.getTheOrganizationDT().getElectronicInd().equals(NEDSSConstants.ELECTRONIC_IND_ELR))) {
        //for LDFs
        ArrayList<Object> ldfList = new ArrayList<Object> ();
        try
        {
             //ldfList = (ArrayList<Object> ) getLDFCollection(organizationUid.longValue());
             //code for new ldf back end condition code is null for person
              LDFHelper ldfHelper = LDFHelper.getInstance();
              ldfList = (ArrayList<Object> )ldfHelper.getLDFCollection(organizationUid,null,nbsSecurityObj);


        }
        catch (Exception e) {
          logger.error(
              "Exception occured while retrieving LDFCollection<Object>  = " +
              e.toString());
        }

        if (ldfList != null) {
          logger.debug("Before setting LDFCollection<Object>  = " + ldfList.size());
          organizationVO.setTheStateDefinedFieldDataDTCollection(ldfList);
        }
      }
        logger.debug("\n\n The OrganizationVO = "+organizationVO.toString());
        return organizationVO;

    }
    catch (Exception e) {
      logger.fatal("EntityProxyEJB.getOrganization: " + e.getMessage(),e);
      throw new NEDSSSystemException(e.getMessage(),e);
    }
  }

  /**
   * Sets the delete attributes of the Organization tables
   * @param organizationVO  the OrganizationVO
   * @param nbsSecurityObj  the NBSSecurityObj
   * @return boolean(ture if we can delete , false if it has an active participation)
   * @throws RemoteException
   * @throws EJBException
   * @throws NEDSSSystemException
   * @throws NEDSSConcurrentDataException
   */
  public boolean deleteOrganization(OrganizationVO organizationVO,
                                    NBSSecurityObj nbsSecurityObj) throws
      java.rmi.RemoteException,
      javax.ejb.EJBException,
      NEDSSSystemException,
      NEDSSConcurrentDataException {
    logger.info("deleting organization-----------------");

    try {

      boolean check1 = nbsSecurityObj.getPermission(
          NBSBOLookup.ORGANIZATION,
          NBSOperationLookup.DELETE);

      if (check1 == false) {
        logger.error(
            "don't have permission to delete organization's information");
        throw new NEDSSSystemException(
            "don't have permission to delete organizations's information");
      }

      int count = 0;
      OrganizationDAOImpl organizationDao = new OrganizationDAOImpl();
      count = organizationDao.checkDeleteOrganization(organizationVO.
          getTheOrganizationDT().getOrganizationUid());

      if (count > 0) {
        logger.error(
            "Unable to delete this organization due to Active Participations");

        return false;
      }
      else {

        NedssUtils nedssUtils = new NedssUtils();
        Object theLookedUpObject;
        logger.debug("preparing to delete organization info");
        logger.debug("get reference for EntityControllerEJB");
        theLookedUpObject = nedssUtils.lookupBean(
            JNDINames.EntityControllerEJB);

        EntityControllerHome ecHome = (EntityControllerHome)
            PortableRemoteObject.narrow(
            theLookedUpObject,
            EntityControllerHome.class);
        EntityController entityController = ecHome.create();
        OrganizationDT organizationDT = entityController.getOrganizationInfo(
            organizationVO.getTheOrganizationDT().getOrganizationUid(),
            nbsSecurityObj);

        Integer aVctrlNbr = organizationDT.getVersionCtrlNbr();
        Integer bVctrlNbr = organizationVO.getTheOrganizationDT().
            getVersionCtrlNbr();
        if (aVctrlNbr != null && bVctrlNbr != null &&
            bVctrlNbr.intValue() != aVctrlNbr.intValue()) {
          logger.error("Throwing NEDSSConcurrentDataException");
          throw new NEDSSConcurrentDataException
              ("NEDSSConcurrentDataException: The data has been modified by other user, please verify!");
        }
        organizationDT.setItDirty(true);
        organizationDT.setItDelete(false);

        /*     organizationDT.setItNew(false);  */
        PrepareVOUtils prepareVOUtils = new PrepareVOUtils();
        OrganizationDT newOrganizationDT = (OrganizationDT) prepareVOUtils.
            prepareVO(
            organizationDT,
            "ORGANIZATION",
            "ORG_DEL",
            "ORGANIZATION",
            "BASE",
            nbsSecurityObj);
        try {
          entityController.setOrganizationInfo(newOrganizationDT,
                                               nbsSecurityObj);
        }
        catch (NEDSSConcurrentDataException ex) {
          logger.fatal(
              "The entity cannot be deleted as concurrent access is not allowed!");
          throw new NEDSSConcurrentDataException(
              "Concurrent access occurred in EntityProxyEJB : " + ex.toString());
        }

        return true;
      }
    }
    catch (Exception e) {
      logger.fatal("Error in deleteOrganization " + e);
      if (e.toString().indexOf("NEDSSConcurrentDataException") != -1) {
        logger.fatal("EntityProxyEJB.deleteOrganization" + e.getMessage(),e);
        throw new NEDSSConcurrentDataException(e.getMessage(),e);
      }
      return true;
    }
  }
  /**
 * This method helps to inactivate the Organization, Sets the required attributes
 * @roseuid 3D9B34D800CF
 * @J2EE_METHOD  --  inactivateOrganization
 * @param organizationVO  the OrganizationVO
 * @param nbsSecurityObj  the NBSSecurityObj
 * @return boolean(ture if we can inactivate, false if it has an active participation)
 * @throws RemoteException
 * @throws EJBException
 * @throws NEDSSSystemException
 * @throws NEDSSConcurrentDataException
 */
public boolean inactivateOrganization(OrganizationVO organizationVO,
                                  NBSSecurityObj nbsSecurityObj) throws
    java.rmi.RemoteException,
    javax.ejb.EJBException,
    NEDSSSystemException,
    NEDSSConcurrentDataException {
  logger.info("inactivating organization-----------------");

  try {

    boolean check1 = nbsSecurityObj.getPermission(
        NBSBOLookup.ORGANIZATION,
        "MANAGE");

    if (check1 == false) {
      logger.error(
          "don't have permission to delete organization's information");
      throw new NEDSSSystemException(
          "don't have permission to delete organizations's information");
    }
      /* Sets the necessary attributes for the organizationDT to inactivate */
      organizationVO.setItDirty(true);
      organizationVO.setItDelete(false);
      organizationVO.setItNew(false);
      organizationVO.getTheOrganizationDT().setItDirty(true);
      organizationVO.getTheOrganizationDT().setItDelete(false);

      NedssUtils nedssUtils = new NedssUtils();
      Object theLookedUpObject;
      logger.debug("preparing to delete organization info");
      logger.debug("get reference for EntityControllerEJB");
      theLookedUpObject = nedssUtils.lookupBean(
          JNDINames.EntityControllerEJB);

      EntityControllerHome ecHome = (EntityControllerHome)
          PortableRemoteObject.narrow(
          theLookedUpObject,
          EntityControllerHome.class);
      EntityController entityController = ecHome.create();

      /*OrganizationDT organizationDT = entityController.getOrganizationInfo(
          organizationVO.getTheOrganizationDT().getOrganizationUid(),
          nbsSecurityObj);

      Integer aVctrlNbr = organizationDT.getVersionCtrlNbr();
      Integer bVctrlNbr = organizationVO.getTheOrganizationDT().
          getVersionCtrlNbr();
      if (aVctrlNbr != null && bVctrlNbr != null &&
          bVctrlNbr.intValue() != aVctrlNbr.intValue()) {
        logger.error("Throwing NEDSSConcurrentDataException");
        throw new NEDSSConcurrentDataException
            ("NEDSSConcurrentDataException: The data has been modified by other user, please verify!");
     } */


      /*     organizationDT.setItNew(false);  */
     /* PrepareVOUtils prepareVOUtils = new PrepareVOUtils();
      OrganizationDT newOrganizationDT = (OrganizationDT) prepareVOUtils.
          prepareVO(
          organizationDT,
          "ORGANIZATION",
          "ORG_DEL",
          "ORGANIZATION",
          "BASE",
          nbsSecurityObj); */
    try {
         Long organizationUID = entityController.setOrganization(organizationVO,NEDSSConstants.ORG_INA,
                                             nbsSecurityObj);
      }
      catch (NEDSSConcurrentDataException ex) {
        logger.fatal(
            "The entity cannot be deleted as concurrent access is not allowed!");
        throw new NEDSSConcurrentDataException(
            "Concurrent access occurred in EntityProxyEJB : " + ex.toString());
      }

      return true;
    }
    catch (Exception e) {
    logger.fatal("Error in deleteOrganization " + e.getMessage(),e);
    if (e.toString().indexOf("NEDSSConcurrentDataException") != -1) {
      logger.fatal("EntityProxyEJB.inactivateOrganization: " + e.getMessage(),e);
      throw new NEDSSConcurrentDataException(e.getMessage(),e);
    }
    return true;
  }
}

	public boolean inactivatePlace(PlaceVO placeVO,
			NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException,
			javax.ejb.EJBException, NEDSSSystemException,
			NEDSSConcurrentDataException {
		logger.info("--inactivating place--");

		try {

			boolean check1 = nbsSecurityObj.getPermission(NBSBOLookup.PROVIDER,
					"MANAGE");

			if (check1 == false) {
				logger.error("don't have permission to delete place's information");
				throw new NEDSSSystemException(
						"don't have permission to delete place's information");
			}
			/* Sets the necessary attributes for the placeDT to inactivate */
			placeVO.setItDirty(true);
			placeVO.setItDelete(false);
			placeVO.setItNew(false);
			placeVO.getThePlaceDT().setItDirty(true);
			placeVO.getThePlaceDT().setItDelete(false);

			NedssUtils nedssUtils = new NedssUtils();
			Object theLookedUpObject;
			logger.debug("preparing to delete place info");
			logger.debug("get reference for EntityControllerEJB");
			theLookedUpObject = nedssUtils
					.lookupBean(JNDINames.EntityControllerEJB);

			EntityControllerHome ecHome = (EntityControllerHome) PortableRemoteObject
					.narrow(theLookedUpObject, EntityControllerHome.class);
			EntityController entityController = ecHome.create();
			try {
				entityController.setPlace(placeVO, NEDSSConstants.PLC_INA,
						nbsSecurityObj);
			} catch (NEDSSConcurrentDataException ex) {
				logger.fatal("The entity cannot be deleted as concurrent access is not allowed!");
				throw new NEDSSConcurrentDataException(
						"Concurrent access occurred in EntityProxyEJB : "
								+ ex.toString());
			}

			return true;
		} catch (Exception e) {
			logger.fatal("Error in inactivatePlace " + e.getMessage(),e);
			if (e.toString().indexOf("NEDSSConcurrentDataException") != -1) {
				logger.fatal("EntityProxyEJB.inactivatePlace: " + e.getMessage(),e);
				throw new NEDSSConcurrentDataException(e.getMessage(),e);
			}
			return true;
		}
	}


  /**
   * Sends the search criteria(OrganizationSearchVO) to the database
   * and getting the Result back (collection of OrganizationSearchResultVO )
   * @roseuid 3D99CB79032D
   * @J2EE_METHOD  --  findOrganization
   * @param theOrganizationSearchVO    the OrganizationSearchVO
   * @param cacheNumber   the int
   * @param fromIndex     the int
   * @param nbsSecurityObj   the NBSSecurityObj
   * @return     the collection of OrganizationSearchResultVO
   * @throws EJBException
   * @throws NEDSSSystemException
   */

    public Collection<Object> findOrganization(OrganizationSearchVO theOrganizationSearchVO, int cacheNumber,
            int fromIndex, NBSSecurityObj nbsSecurityObj) throws EJBException, NEDSSSystemException
    {
        logger.info("find an organization-----------------");
        ArrayList<Object> displayOrganizationList = new ArrayList<Object>();
        try
        {
            EntityProxyHelper entityProxyHelper = EntityProxyHelper.getInstance();
            displayOrganizationList = entityProxyHelper.findOrganizationsByKeyWords(theOrganizationSearchVO,
                    cacheNumber, fromIndex);
       

        Iterator<Object> srchResult = displayOrganizationList.iterator();
        while (srchResult.hasNext())
        {
            DisplayOrganizationList list = (DisplayOrganizationList) srchResult.next();
        }
        } 
        catch (Exception e)
        {
            logger.fatal("EntityProxyEJB.findOrganization " + e.getMessage(),e);
            throw new NEDSSSystemException(e.getMessage(),e);
        }
        return displayOrganizationList;
    }
  
    public Collection<Object> findPlace(PlaceSearchVO thePlaceSearchVO, int cacheNumber,
            int fromIndex, NBSSecurityObj nbsSecurityObj) throws EJBException, NEDSSSystemException
    {
        ArrayList<Object> displayOrganizationList = new ArrayList<Object>();
        try
        {
            EntityProxyHelper entityProxyHelper = EntityProxyHelper.getInstance();
            displayOrganizationList = entityProxyHelper.findPlacesByKeyWords(thePlaceSearchVO,cacheNumber, fromIndex);
        }
        catch (Exception e)
        {
            logger.fatal("EntityProxyEJB.findOrganization: " + e.getMessage(),e); 
            throw new NEDSSSystemException(e.getMessage(),e);
        }  
        return displayOrganizationList;
    }

  /**
   * Getting the person Summary from the database
   * @roseuid 3D10C195037F
   * @J2EE_METHOD  --  getPersonSummary
   * @param personUid       the Long
   * @param nbsSecurityObj     the NBSSecurityObj
   * @return personSummaryVO    the  PersonSummaryVO
   */
  public PersonSummaryVO getPersonSummary(Long personUid,
                                          NBSSecurityObj nbsSecurityObj) {
    logger.info("---------------getting personSummary----------------");
    PersonSummaryVO personSummaryVO = new PersonSummaryVO();

    try {
      NedssUtils nedssUtils = new NedssUtils();
      Object theLookedUpObject;
      logger.debug("getting hold of EntityControllerEJB");
      theLookedUpObject = nedssUtils.lookupBean(JNDINames.EntityControllerEJB);
      EntityControllerHome ecHome = (EntityControllerHome)
          PortableRemoteObject.narrow(theLookedUpObject,
                                      EntityControllerHome.class);
      EntityController entityController = ecHome.create();
      PersonVO personVO = entityController.getPerson(personUid,
          nbsSecurityObj);
      
        CachedDropDownValues cValues = new CachedDropDownValues();
        personSummaryVO.setPersonUid(personUid);
        logger.debug("PersonSummaryVO.getPersonSummary and personUID is " +
                     personSummaryVO.getPersonUid());
        logger.debug("PersonSummaryVO.getPersonSummary and personUID is " +
                     personVO.getThePersonDT().getPersonUid());
        personSummaryVO.setLocalId(personVO.getThePersonDT().getLocalId());
        logger.debug("PersonSummaryVO.getPersonSummary and localUID is " +
                     personSummaryVO.getLocalId());
        personSummaryVO.setDOB(personVO.getThePersonDT().getBirthTime());
        logger.debug("PersonSummaryVO.getPersonSummary and DOB is " +
                     personSummaryVO.getDOB());
        personSummaryVO.setCurrentSex(personVO.getThePersonDT().getCurrSexCd());
        logger.debug("PersonSummaryVO.getPersonSummary and CurrentSex is " +
                     personSummaryVO.getCurrentSex());

        ArrayList<Object> personNameColl = (ArrayList<Object> ) personVO.
            getThePersonNameDTCollection();
       Iterator<Object>  it = personNameColl.iterator();
        while (it.hasNext()) {
          PersonNameDT pdt = (PersonNameDT) it.next();
          //boolean nameDoesnotExists = true;
          logger.debug("PersonSummaryVO.getNmUseCd:" + pdt.getNmUseCd());
          if (pdt.getRecordStatusCd().equalsIgnoreCase(NEDSSConstants.ACTIVE)
              && pdt.getNmUseCd() != null
              && pdt.getNmUseCd().equalsIgnoreCase(NEDSSConstants.LEGAL_NAME)) {
            logger.debug(
                "PersonSummaryVO.getPersonSummary and nameDoesnotExist is " +
                pdt.getNmUseCd());
            personSummaryVO.setFirstName(pdt.getFirstNm());
            personSummaryVO.setLastName(pdt.getLastNm());
            logger.debug(
                "PersonSummaryVO.getPersonSummary and firstName, Lastname is " +
                personSummaryVO.getFirstName() + "," +
                personSummaryVO.getLastName());
          } //end if for checking nameused cd of legal type
          /**if(nameDoesnotExists)
                             {
            personSummaryVO.setFirstName(null);
            personSummaryVO.setLastName(null);
            logger.debug("PersonSummaryVO.getPersonSummary in else and firstName, Lastname is " + personSummaryVO.getFirstName()+"," +personSummaryVO.getLastName());
                             }*/
          //end if for setting name to null if there exists none
        } //end while

        //to set the derived Jurisdiction Code
        ArrayList<Object> personHomeAddresses = (ArrayList<Object> ) personVO.
            getTheEntityLocatorParticipationDTCollection();
       Iterator<Object>  iterator = personHomeAddresses.iterator();
        logger.debug("outside the iterator personHomeAddresses size:" +
                     personHomeAddresses.size());
        String code = null;
        ArrayList<Object> currentList = new ArrayList<Object> ();
        ArrayList<Object> houseList = new ArrayList<Object> ();
        ArrayList<Object> aptList = new ArrayList<Object> ();

        // create three new Arralist with only 3 types of address information.
        // current, home and appartment
        while (iterator.hasNext()) {
          logger.debug("inside the iterator");
          EntityLocatorParticipationDT entityLocatorPart = (
              EntityLocatorParticipationDT) iterator.next();
          if (entityLocatorPart.getUseCd().equals(NEDSSConstants.BIRTHPLACE)
              &&
              entityLocatorPart.getCd().equals(NEDSSConstants.
                                               BIRTHDELIVERYADDRESS)
              && entityLocatorPart.getClassCd().equals(NEDSSConstants.POSTAL)) {
            String cd = entityLocatorPart.getThePostalLocatorDT().getCntryCd();
            personSummaryVO.setCountryOfBirth(cd);
          }

          if (entityLocatorPart != null && entityLocatorPart.getUseCd() != null
              && entityLocatorPart.getClassCd() != null
              && entityLocatorPart.getRecordStatusCd() != null) {
            if (entityLocatorPart.getUseCd().equals(NEDSSConstants.HOME)
                &&
                entityLocatorPart.getClassCd().equals(NEDSSConstants.POSTAL)
                &&
                entityLocatorPart.getRecordStatusCd().equals(NEDSSConstants.
                ACTIVE)) {
              if (entityLocatorPart.getCd().equals(NEDSSConstants.CURRENT)) {
                currentList.add(entityLocatorPart);
              }
              else if (entityLocatorPart.getCd().equals(NEDSSConstants.HOME)) {
                houseList.add(entityLocatorPart);
              }
              else if (entityLocatorPart.getCd().equals(NEDSSConstants.
                  STATUS_ACTIVE)) {
                aptList.add(entityLocatorPart);
              }
            }
          } // while

          boolean codeFound = false;
          for (Iterator<Object> anIterator = currentList.iterator();
               anIterator.hasNext(); ) {
            entityLocatorPart = (EntityLocatorParticipationDT) anIterator.
                next();
            if (entityLocatorPart.getThePostalLocatorDT().getPostalLocatorUid().
                equals(entityLocatorPart.getLocatorUid())) {
              String zipCD = entityLocatorPart.getThePostalLocatorDT().
                  getZipCd();
              if (zipCD != null) { //try with zip code first
                code = cValues.getJurisditionCD(zipCD,
                                                NEDSSConstants.
                                                ZIPFORJURISDICTION);
                if (code != null) {
                  personSummaryVO.setDefaultJurisdictionCd(code);
                  codeFound = true;
                }
              }
              // try with city code if zip code is either null or no matching fipsCode is found for zipcode
              if ( (zipCD != null && codeFound == false) || (zipCD == null)) {

                String cityCode = entityLocatorPart.getThePostalLocatorDT().
                    getCityCd();
                if (cityCode != null) {
                  int count = cValues.getCountOfCdNotNull(cityCode);
                  if (count > 1) {
                    personSummaryVO.setDefaultJurisdictionCd(null);
                  }
                  else {
                    code = cValues.getJurisditionCD(cityCode,
                        NEDSSConstants.CITYFORJURISDICTION);
                    if (code != null) {
                      personSummaryVO.setDefaultJurisdictionCd(code);
                      codeFound = true;
                    }
                  }
                }

              }
            }
          } // end if c

          if (codeFound == false) {
            for (Iterator<Object> anIterator = houseList.iterator();
                 anIterator.hasNext(); ) {
              entityLocatorPart = (EntityLocatorParticipationDT) anIterator.
                  next();
              if (entityLocatorPart.getThePostalLocatorDT().
                  getPostalLocatorUid().equals(entityLocatorPart.
                                               getLocatorUid())) {
                String zipCD = entityLocatorPart.getThePostalLocatorDT().
                    getZipCd();
                if (zipCD != null) {
                  code = cValues.getJurisditionCD(zipCD,
                                                  NEDSSConstants.
                                                  ZIPFORJURISDICTION);
                  if (code != null) {
                    personSummaryVO.setDefaultJurisdictionCd(code);
                    codeFound = true;
                  }
                }
                else { // try with city code

                  String cityCode = entityLocatorPart.getThePostalLocatorDT().
                      getCityCd();
                  if (cityCode != null) {
                    int count = cValues.getCountOfCdNotNull(cityCode);
                    if (count > 1) {
                      personSummaryVO.setDefaultJurisdictionCd(null);
                    }
                    else {
                      code = cValues.getJurisditionCD(cityCode,
                          NEDSSConstants.CITYFORJURISDICTION);
                      if (code != null) {
                        personSummaryVO.setDefaultJurisdictionCd(code);
                        codeFound = true;
                      }
                    }
                  }

                }
              }
            }
          } // end if false

          if (codeFound == false) {
            for (Iterator<Object> anIterator = aptList.iterator(); anIterator.hasNext(); ) {
              entityLocatorPart = (EntityLocatorParticipationDT) anIterator.
                  next();
              if (entityLocatorPart.getThePostalLocatorDT().
                  getPostalLocatorUid().equals(entityLocatorPart.
                                               getLocatorUid())) {
                String zipCD = entityLocatorPart.getThePostalLocatorDT().
                    getZipCd();
                if (zipCD != null) {
                  code = cValues.getJurisditionCD(zipCD,
                                                  NEDSSConstants.
                                                  ZIPFORJURISDICTION);
                  if (code != null) {
                    personSummaryVO.setDefaultJurisdictionCd(code);
                    codeFound = true;
                  }
                }
                else { // try with city code

                  String cityCode = entityLocatorPart.getThePostalLocatorDT().
                      getCityCd();
                  if (cityCode != null) {
                    int count = cValues.getCountOfCdNotNull(cityCode);
                    if (count > 1) {
                      personSummaryVO.setDefaultJurisdictionCd(null);
                    }
                    else {
                      code = cValues.getJurisditionCD(cityCode,
                          NEDSSConstants.CITYFORJURISDICTION);
                      if (code != null) {
                        personSummaryVO.setDefaultJurisdictionCd(code);
                        codeFound = true;
                      }
                    }
                  }

                }
              }
            }
          } // end if false
        }

    } //end try
    catch (Exception e) {
      logger.fatal("EntityProxyEJB.getPersonSummary: " + e.getMessage(),e);
      throw new NEDSSSystemException(e.getMessage(),e);
    }
    return personSummaryVO;
  }

  /**
   * Getting the values to PersonVOs from the database based on the uids which are passed
   * @roseuid
   * @J2EE_METHOD  --  getPersons
   * @param personUidCollection     the collection of personuids
   * @param nbsSecurityObj    the NBSSecurityObj
   * @return personVOCollection    the Collection<Object>  of PersonVO objects
   * @throws RemoteException
   * @throws EJBException
   * @throws NEDSSSystemException
   */
  public Collection<Object>  getPersons(Collection<Object> personUidCollection,
                               NBSSecurityObj nbsSecurityObj) throws java.rmi.
      RemoteException, javax.ejb.EJBException, NEDSSSystemException {

    ArrayList<Object> personVOCollection  = new ArrayList<Object> ();

    try {

      logger.debug("Number of elements in UID collection that is passed to EntityProxy.getPersons is " +
                   personUidCollection.size());
      if (personUidCollection.size() > 0) {
        // Loop through the collection of Person UIDs
       Iterator<Object>  itr = personUidCollection.iterator();

        while (itr.hasNext()) {

          Long personUid = ( (Long) itr.next());

          logger.info("Processing person uid " + personUid.toString() +
                      " in EntityProxy.getPersons");

          // Get person VO for each UID

          PersonVO personVO = getPerson(personUid, nbsSecurityObj);

          //Add this person VO to the collection
          personVOCollection.add(personVO);
        }
      }
    }
    catch (Exception e) {
      logger.fatal("EntityProxyEJB.getPersons: " + e.getMessage(),e);
      throw new NEDSSSystemException(e.getMessage(),e);
    }

    return personVOCollection;

  }

  /**
   * Merging two persons
   * @roseuid
   * @J2EE_METHOD  --  mergePersons
   * @param survivingPersonVOParam    the PersonVO
   * @param supercededPersonVOParam    the PersonVO
   * @param nbsSecurityObj     the NBSSecurityObj
   * @throws RemoteException
   * @throws EJBException
   * @throws ClassNotFoundException
   * @throws CloneNotSupportedException
   * @throws IOException
   * @throws NEDSSSystemException
   * @throws NEDSSConcurrentDataException
   */
  public void mergePersons(PersonVO survivingPersonVOParam,
                           PersonVO supercededPersonVOParam,
                           NBSSecurityObj nbsSecurityObj) throws java.rmi.
      RemoteException, javax.ejb.EJBException, ClassNotFoundException,
      CloneNotSupportedException, IOException, NEDSSSystemException,
      NEDSSConcurrentDataException {

    try {
      String errorMessage = null;

      boolean hasMergePerm = nbsSecurityObj.getPermission(NBSBOLookup.PATIENT,
          NBSOperationLookup.MERGE);

      if (!hasMergePerm) {
        errorMessage = "User " + nbsSecurityObj.getFullName()
            + "does not have permission to merge persons";
        logger.error(errorMessage);
        throw new NEDSSSystemException(errorMessage);
      }
      else {
        Long supercededPersonUid = supercededPersonVOParam.getThePersonDT().
            getPersonUid();
        Long survivingPersonUid = survivingPersonVOParam.getThePersonDT().
            getPersonUid();
        // Get person VO for surviving record
        PersonVO survivingPersonVO = getPerson(survivingPersonUid,
                                               nbsSecurityObj);

        // Check if the surviving record is active
        logger.debug("The surviving person record with local UID "
                     + survivingPersonVO.getThePersonDT().getLocalId()
                     + " has a status of " +
                     survivingPersonVO.getThePersonDT().getRecordStatusCd().
                     trim());

        if (! (survivingPersonVO.getThePersonDT().getRecordStatusCd().trim().
               equalsIgnoreCase(NEDSSConstants.RECORD_STATUS_ACTIVE))) {
          errorMessage = "The surviving person record with local UID "
              + survivingPersonVO.getThePersonDT().getLocalId()
              + " is not Active";
          logger.error(errorMessage);
          throw new NEDSSConcurrentDataException(errorMessage);
        }

        // Get person VO for superceded record
        PersonVO supercededPersonVO = getPerson(supercededPersonUid,
                                                nbsSecurityObj);

        logger.debug("test one size:" +
                     supercededPersonVO.getThePersonNameDTCollection().size());

        // Check if the superceded record is active
        logger.debug("The superceded person record with local UID "
                     + supercededPersonVO.getThePersonDT().getLocalId()
                     + " has a status of " +
                     supercededPersonVO.getThePersonDT().getRecordStatusCd().
                     trim());
        if (! (supercededPersonVO.getThePersonDT().getRecordStatusCd().trim().
               equalsIgnoreCase(NEDSSConstants.RECORD_STATUS_ACTIVE))) {
          errorMessage = "The superceded person record with local UID "
              + supercededPersonVO.getThePersonDT().getLocalId()
              + " is not Active";
          logger.error(errorMessage);
          throw new NEDSSConcurrentDataException(errorMessage);
        }

        // Copy from superceded to surviving
        copyToSurviving(survivingPersonVO, supercededPersonVO,
                        survivingPersonVOParam.getThePersonDT().
                        getDescription());

        // Update superceding
        logger.debug("test three size:" +
                     supercededPersonVO.getThePersonNameDTCollection().size());

        updateSuperceding(supercededPersonVO, supercededPersonVOParam,
                          nbsSecurityObj,
                          survivingPersonVO.getThePersonDT().getLocalId());

        // Persist superceding
        logger.debug(
            "Fully updated PersonDT of Superceded VO just BEFORE setPerson has "
            + " record status of " +
            supercededPersonVO.getThePersonDT().getRecordStatusCd());

        logger.debug("Dirty Flag of Superceded VO just BEFORE setPerson is " +
                     supercededPersonVO.isItDirty());

        Long retSupUid = setPersonForMerge(supercededPersonVO, nbsSecurityObj);
        logger.debug(
            "Saved the superceded personVO and the return person uid is " +
            retSupUid);

        // Update surviving
        updateSurving(survivingPersonVO, survivingPersonVOParam,
                      nbsSecurityObj);

        // Persist surviving
        Long retSurvUid = setPersonForMerge(survivingPersonVO, nbsSecurityObj);
        logger.debug(
            "Saved the surviving personVO and the return person uid is " +
            retSurvUid);
      }
    }
    catch (NEDSSConcurrentDataException cde) {
      logger.fatal("EntityProxyEJB.mergePersons: " + cde.getMessage(),cde);
      throw new NEDSSConcurrentDataException(cde.getMessage(),cde);
    }
    catch (Exception e) {
      logger.fatal("EntityProxyEJB.mergePersons: " + e.getMessage(),e);
      throw new NEDSSSystemException(e.getMessage(),e);
    }

  }

  /**
   * Updating the PersonVO which is passed as a parameter
   * @param survivingPersonVO    the PersonVO
   * @param survivingPersonVONew    the PersonVO
   * @param nbsSecurityObj    the NBSSecurityObj
   * @throws NEDSSSystemException
   * @throws NEDSSConcurrentDataException
   */
  private void updateSurving(PersonVO survivingPersonVO,
                             PersonVO survivingPersonVONew,
                             NBSSecurityObj nbsSecurityObj) throws
      NEDSSSystemException, NEDSSConcurrentDataException {

    try {
		// Dirty flag
		survivingPersonVONew.setItDirty(true);
		survivingPersonVONew.getThePersonDT().setItDirty(true);

		// Prepare surviving PersonDT
		RootDTInterface rootDTInterface = survivingPersonVONew.getThePersonDT();

		PrepareVOUtils prepareVOUtils = new PrepareVOUtils();
		logger.error("Number of participations in surviving: " +
		             survivingPersonVONew.getTheParticipationDTCollection().size());
		logger.debug("Inside updateSurving(), just BEFORE prepareVO utils");
		PersonDT newSurvivingPersonDT = (PersonDT) prepareVOUtils.prepareVO(
		    rootDTInterface,
		    NBSBOLookup.PATIENT, NEDSSConstants.PER_EDIT,
		    DataTables.PERSON_TABLE,
		    NEDSSConstants.BASE, nbsSecurityObj);
		logger.debug("Inside updateSurving(), just AFTER prepareVO utils");

		// Inside surviving PersonVO, replace  old PersonDT with this new surviving DT
		survivingPersonVO.setThePersonDT(newSurvivingPersonDT);
		logger.debug("Set the surviving PersonVO with newly prepared PersonDT");
	} catch (Exception e) {
		 logger.fatal("EntityProxyEJB.updateSurving: " + e.getMessage(),e);
	      throw new NEDSSSystemException(e.getMessage(),e);
	}

  }

  /**
   * Updating the PersonVO which is superceding with the appropriate values
   * @param supercededPersonVO    the PersonVO
   * @param nbsSecurityObj   the NBSSecurityObj
   * @param survivingPersonLocalId    the String
   * @throws NEDSSSystemException
   * @throws NEDSSConcurrentDataException
   */
  private void updateSuperceding(PersonVO supercededPersonVO,
                                 PersonVO supercededPersonVONew,
                                 NBSSecurityObj nbsSecurityObj,
                                 String survivingPersonLocalId) throws
      NEDSSSystemException, NEDSSConcurrentDataException {
   
    try {
		// Description
		supercededPersonVONew.getThePersonDT().setDescription(
		    "This record has been merged into the record of Person ID: "
		    + survivingPersonLocalId);

		// Set the Dirty flags
		supercededPersonVONew.setItDirty(true);
		supercededPersonVONew.getThePersonDT().setItDirty(true);

		PrepareVOUtils prepareVOUtils = new PrepareVOUtils();

		logger.debug("Inside updateSuperceding(), just BEFORE prepareVO utils");

		// Prepare superceded PersonDT
		RootDTInterface rootDTInterface = supercededPersonVONew.getThePersonDT();
		PersonDT newSupercededPersonDT = (PersonDT) prepareVOUtils.prepareVO(
		    rootDTInterface,
		    NBSBOLookup.PATIENT, NEDSSConstants.PER_MERGE,
		    DataTables.PERSON_TABLE,
		    NEDSSConstants.BASE, nbsSecurityObj);

		logger.debug(
		    "New PersonDT for superceded VO returned by PrepareVOUtils has "
		    + " record status of " + newSupercededPersonDT.getRecordStatusCd()
		    + " and record status timestamp of " +
		    newSupercededPersonDT.getRecordStatusTime());

		// Inside superceded PersonVO, replace  old PersonDT with this new superceded DT
		supercededPersonVO.setThePersonDT(newSupercededPersonDT);

		logger.debug("Latest PersonDT that is set to Superceded VO has "
		             + " record status of " +
		             supercededPersonVO.getThePersonDT().getRecordStatusCd()
		             + " and record status timestamp of " +
		             supercededPersonVO.getThePersonDT().getRecordStatusTime());

		logger.debug("Inside updateSuperceding(), AFTER prepareVO utils");

		// Set RoleDT record status to inactive
		//updateSupercededRecordStatus(supercededPersonVO, nbsSecurityObj, "RoleDT");

		// Set ParticipationDT record status and status cd to inactive
		updateSupercededRecordStatus(supercededPersonVO, nbsSecurityObj,
		                             "ParticipationDT");
	} catch (Exception e) {
		 logger.fatal("EntityProxyEJB.updateSuperceding: " + e.getMessage(),e);
	      throw new NEDSSSystemException(e.getMessage(),e);
	}
  }

  /**
   * Updating the recordstatus for the PersonVO
   * @param supercededPersonVO   the  PersonVO
   * @param nbsSecurityObj    the  NBSSecurityObj
   * @param type   the String
   * @throws NEDSSSystemException
   */
  private void updateSupercededRecordStatus(PersonVO supercededPersonVO,
                                            NBSSecurityObj nbsSecurityObj,
                                            String type) throws
      NEDSSSystemException {

    try {
		ArrayList<Object> supercededObjDTCollection  = null;
		ArrayList<Object> supercededObjDTCollectionCopy = null;
   Iterator<Object>  supItr = null;
		AssocDTInterface assocDTInterface = null;
		AssocDTInterface supercededObjDT = null;
		AssocDTInterface preparedSupercededObjDT = null;
		int index = -1;

		if (type.equalsIgnoreCase("RoleDT")) {
		  supercededObjDTCollection  = (ArrayList<Object> ) supercededPersonVO.
		      getTheRoleDTCollection();
		}
		else if (type.equalsIgnoreCase("ParticipationDT")) {
		  supercededObjDTCollection  = (ArrayList<Object> ) supercededPersonVO.
		      getTheParticipationDTCollection();
		}

		// Make a copy of our superceded collection
		supercededObjDTCollectionCopy = new ArrayList<Object> (supercededObjDTCollection);

		supItr = supercededObjDTCollectionCopy.iterator();

		// Iterate through the copy collection
		while (supItr.hasNext()) {

		  index = index + 1;
		  if (type.equalsIgnoreCase("RoleDT")) {
		    supercededObjDT = ( (RoleDT) supItr.next());

		  }
		  else if (type.equalsIgnoreCase("ParticipationDT")) {
		    supercededObjDT = ( (ParticipationDT) supItr.next());

		    //------------
		    //Commented out based on discussion with Chase and Beau on 7/19/02
		    //supercededObjDT.setStatusCd(NEDSSConstants.STATUS_INACTIVE);
		    //logger.debug("Set status code for " + type	+ " to " + 	NEDSSConstants.STATUS_INACTIVE);
		    //------------
		  }

		  //------------
		  //Commented out based on discussion with Chase and Beau on 7/19/02
		  //supercededObjDT.setRecordStatusCd(NEDSSConstants.INACTIVE);
		  //logger.debug("Set record status code for " + type	+ " to " + 	NEDSSConstants.INACTIVE);
		  //------------

		  supercededObjDT.setItDelete(true);

		  // Prepare Assoc RoleDT
		  assocDTInterface = supercededObjDT;

		  PrepareVOUtils prepareVOUtils = new PrepareVOUtils();

		  if (type.equalsIgnoreCase("RoleDT")) {
		    preparedSupercededObjDT = (RoleDT) prepareVOUtils.prepareAssocDT(
		        assocDTInterface, nbsSecurityObj);

		  }
		  else if (type.equalsIgnoreCase("ParticipationDT")) {
		    preparedSupercededObjDT = (ParticipationDT) prepareVOUtils.
		        prepareAssocDT(
		        assocDTInterface, nbsSecurityObj);
		  }

		  // Replace the RoleDT/ParticipationDT in the
		  // RoleDT/ParticipationDT collection with this prepared roleDT/ParticipationDT
		  supercededObjDTCollection.remove(index);
		  //supItr.remove();
		  logger.debug("Removed " + type + " number- " + index +
		               " from superceded " + type + "Collection<Object>  ");

		  supercededObjDTCollection.add(index, preparedSupercededObjDT);
		  logger.debug("Added the newly prepared" + type + " number- " + index +
		               " to superceded " + type + "Collection<Object>  ");
		}

		// Update this new superceded collection to the superceded Person VO
		if (type.equalsIgnoreCase("RoleDT")) {
		  supercededPersonVO.setTheRoleDTCollection(supercededObjDTCollection);
		}
		else if (type.equalsIgnoreCase("ParticipationDT")) {
		  supercededPersonVO.setTheParticipationDTCollection(
		      supercededObjDTCollection);
		}
		logger.debug("Set the superceded PersonVO with newly prepared " + type +
		             "Collection<Object>  ");
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("EntityProxyEJB.updateSupercededRecordStatus: " + e.getMessage(),e);
		throw new NEDSSSystemException(e.getMessage(),e);
	}

  }

  /**
   * Copy the supercededPersonVO to the surviving PersonVO
   * @param survivingPersonVO
   * @param supercededPersonVO
   * @param mergeComments
   * @throws NEDSSSystemException
   * @throws ClassNotFoundException
   * @throws CloneNotSupportedException
   * @throws IOException
   */
  private void copyToSurviving(PersonVO survivingPersonVO,
                               PersonVO supercededPersonVO,
                               String mergeComments) throws
      NEDSSSystemException, ClassNotFoundException,
      CloneNotSupportedException, IOException {
      
    try {
		// Copy Participation
		copyUniquesParticipationDT(survivingPersonVO, supercededPersonVO);
   
		// Copy merge comments
		survivingPersonVO.getThePersonDT().setDescription(mergeComments);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("EntityProxyEJB.copyToSurviving: " + e.getMessage(),e);
		throw new NEDSSSystemException(e.getMessage(),e);
	}
  }

  /**
   * Copying the values of PersonVO
   * @param survivingPersonVO
   * @param supercededPersonVO
   */
  private void copyIfUnknowns(PersonVO survivingPersonVO,
                              PersonVO supercededPersonVO) {
    logger.debug("2-1");
    // Curr Sex Cd
    String supCurrSexCode = supercededPersonVO.getThePersonDT().getCurrSexCd();
    String survCurrSexCode = survivingPersonVO.getThePersonDT().getCurrSexCd();

    logger.debug("Surv curr sex code is " + survCurrSexCode +
                 " and Sup curr sex code is " + supCurrSexCode);

    if (supCurrSexCode != null) {
      if ( (supCurrSexCode.trim().length() > 0) &&
          (!supCurrSexCode.trim().equalsIgnoreCase(NEDSSConstants.UNKNOWN))) {
        if (survCurrSexCode != null) {
          if ( (survCurrSexCode.trim().equalsIgnoreCase(NEDSSConstants.
              UNKNOWN))
              || (survCurrSexCode.trim().length() <= 0)) {
            survivingPersonVO.getThePersonDT().setCurrSexCd(supCurrSexCode.
                trim());
            logger.debug("Oops surv blank or unk, setting sup value ");
          }
        }
        else {
          survivingPersonVO.getThePersonDT().setCurrSexCd(supCurrSexCode.trim());
          logger.debug("Oops surv null, setting sup value ");
        }
      }
    }

    logger.debug("2-2");
    // Birth gender Cd
    String supBirthGenderCd = supercededPersonVO.getThePersonDT().
        getBirthGenderCd();
    String survBirthGenderCd = survivingPersonVO.getThePersonDT().
        getBirthGenderCd();
    logger.debug("Surv Birth sex code is " + survBirthGenderCd +
                 " and Sup Birth sex code is " + supBirthGenderCd);

    if (supBirthGenderCd != null) {
      if ( (supBirthGenderCd.trim().length() > 0) &&
          (!supBirthGenderCd.trim().equalsIgnoreCase(NEDSSConstants.UNKNOWN))) {
        if (survBirthGenderCd != null) {
          if ( (survBirthGenderCd.trim().equalsIgnoreCase(NEDSSConstants.
              UNKNOWN))
              || (survBirthGenderCd.trim().length() <= 0)) {
            survivingPersonVO.getThePersonDT().setBirthGenderCd(
                supBirthGenderCd.trim());
            logger.debug("Oops surv blank or unk, setting sup value ");
          }
        }
        else {
          survivingPersonVO.getThePersonDT().setBirthGenderCd(
              supBirthGenderCd.trim());
          logger.debug("Oops surv null, setting sup value ");
        }
      }
    }

    logger.debug("2-3");
    // Multiple Birth Cd
    String supMultipleBirthInd = supercededPersonVO.getThePersonDT().
        getMultipleBirthInd();
    String survMultipleBirthInd = survivingPersonVO.getThePersonDT().
        getMultipleBirthInd();
    logger.debug("Surv Multi birth code is " + survMultipleBirthInd +
                 " and Sup Multi birth code is " + supMultipleBirthInd);
    if (supMultipleBirthInd != null) {
      if ( (supMultipleBirthInd.trim().length() > 0) &&
          (!supMultipleBirthInd.
           trim().equalsIgnoreCase(NEDSSConstants.UNKNOWN))) {
        if (survMultipleBirthInd != null) {
          if ( (survMultipleBirthInd.trim().equalsIgnoreCase(NEDSSConstants.
              UNKNOWN))
              || (survMultipleBirthInd.trim().length() <= 0)) {
            survivingPersonVO.getThePersonDT().setMultipleBirthInd(
                supMultipleBirthInd.trim());
            logger.debug("Oops surv blank or unk, setting sup value ");
          }
        }
        else {
          survivingPersonVO.getThePersonDT().setMultipleBirthInd(
              supMultipleBirthInd.trim());
          logger.debug("Oops surv null, setting sup value ");
        }
      }
    }

    logger.debug("2-4");
    // Deceased Ind Cd
    String supDeceasedIndCd = supercededPersonVO.getThePersonDT().
        getDeceasedIndCd();
    String survDeceasedIndCd = survivingPersonVO.getThePersonDT().
        getDeceasedIndCd();
    logger.debug("Surv Deceased code is " + survDeceasedIndCd +
                 " and Sup Deceased code is " + supDeceasedIndCd);
    if (supDeceasedIndCd != null) {
      if ( (supDeceasedIndCd.trim().length() > 0) &&
          (!supDeceasedIndCd.trim().equalsIgnoreCase(NEDSSConstants.UNKNOWN))) {
        if (survDeceasedIndCd != null) {
          if ( (survDeceasedIndCd.trim().equalsIgnoreCase(NEDSSConstants.
              UNKNOWN))
              || (survDeceasedIndCd.trim().length() <= 0)) {
            survivingPersonVO.getThePersonDT().setDeceasedIndCd(
                supDeceasedIndCd.trim());
            logger.debug("Oops surv blank or unk, setting sup value ");
          }
        }
        else {
          survivingPersonVO.getThePersonDT().setDeceasedIndCd(
              supDeceasedIndCd.trim());
          logger.debug("Oops surv null, setting sup value ");
        }
      }
    }

    logger.debug("2-5" +
                 survivingPersonVO.getThePersonDT().getMaritalStatusCd());
    // Marital Status Cd
    String supMaritalStatusCd = supercededPersonVO.getThePersonDT().
        getMaritalStatusCd();
    String survMaritalStatusCd = survivingPersonVO.getThePersonDT().
        getMaritalStatusCd();
    logger.debug("Surv Marital code is " + survMaritalStatusCd +
                 " and Sup Marital code is " + supMaritalStatusCd);
    if (supMaritalStatusCd != null) {
      if ( (supMaritalStatusCd.trim().length() > 0) &&
          (!supMaritalStatusCd.trim().equalsIgnoreCase(NEDSSConstants.UNKNOWN))) {
        if (survMaritalStatusCd != null) {
          if ( (survMaritalStatusCd.trim().equalsIgnoreCase(NEDSSConstants.
              UNKNOWN))
              || (survMaritalStatusCd.trim().length() <= 0)) {
            survivingPersonVO.getThePersonDT().setMaritalStatusCd(
                supMaritalStatusCd.trim());
            logger.debug("Oops surv blank or unk, setting sup value ");
          }
        }
        else {
          survivingPersonVO.getThePersonDT().setMaritalStatusCd(
              supMaritalStatusCd.trim());
          logger.debug("Oops surv null, setting sup value ");
        }
      }
    }

  }

  /**
   * Copying the unique EntityLocatorParticipationDTs in the PersonVO
   * @param survivingPersonVO    the PersonVO
   * @param supercededPersonVO   the PersonVO
   * @throws ClassNotFoundException
   * @throws CloneNotSupportedException
   * @throws IOException
   */
  private void copyUniquesEntityLocatorParticipationDT(PersonVO
      survivingPersonVO,
      PersonVO supercededPersonVO) throws ClassNotFoundException,
      CloneNotSupportedException,
      IOException {

   try {
	Iterator<Object>  supItr = null;
	   Iterator<Object>  survItr = null;
	    boolean uniqIdViolation = false;
	    Collection<Object>  supercededEntityLocatorParticipationDTCollection  = null;
	    Collection<Object>  survivingEntityLocatorParticipationDTCollection  = null;
	    EntityLocatorParticipationDT supercededEntityLocatorParticipationDT = null;
	    EntityLocatorParticipationDT survivingEntityLocatorParticipationDT = null;
	
	    supercededEntityLocatorParticipationDTCollection  = supercededPersonVO.
	        getTheEntityLocatorParticipationDTCollection();
	
	    if (supercededEntityLocatorParticipationDTCollection  != null) {
	      supItr = supercededEntityLocatorParticipationDTCollection.iterator();
	
	      while (supItr.hasNext()) { //begin ... iterate superceded collection
	        // Check for unique id violation
	        supercededEntityLocatorParticipationDT = (
	            EntityLocatorParticipationDT) supItr.next();
	
	        survivingEntityLocatorParticipationDTCollection  =
	            survivingPersonVO.getTheEntityLocatorParticipationDTCollection();
	
	        // Start with no unique id violation
	        uniqIdViolation = false;
	        if (survivingEntityLocatorParticipationDTCollection  != null) {
	          survItr = survivingEntityLocatorParticipationDTCollection.iterator();
	
	          while (survItr.hasNext()) { //begin ... iterate surviving collection
	
	            survivingEntityLocatorParticipationDT = (
	                EntityLocatorParticipationDT) survItr.next();
	            if ( (survivingEntityLocatorParticipationDT.getEntityUid().equals(
	                supercededEntityLocatorParticipationDT.getEntityUid()))
	                &&
	                (survivingEntityLocatorParticipationDT.getLocatorUid().
	                 equals(supercededEntityLocatorParticipationDT.getLocatorUid()))) {
	              uniqIdViolation = true;
	            }
	
	            // Since we found a unique id violation, no need to find further
	            if (uniqIdViolation) {
	              break;
	            }
	          } //end ... iterate surviving collection
	        }
	
	        // Append to surviving collection only if there is no unique id violation
	        if (!uniqIdViolation) {
	
	          // If they are birth place, death place or home address, copy
	          // them only if they are not present in surviving. For all others,
	          // copy them irrespective of their presence in surviving
	          if (supercededEntityLocatorParticipationDT.getStatusCd() != null &&
	              supercededEntityLocatorParticipationDT.getClassCd() != null &&
	              supercededEntityLocatorParticipationDT.getUseCd() != null &&
	              supercededEntityLocatorParticipationDT.getStatusCd().equals("A") &&
	              supercededEntityLocatorParticipationDT.getClassCd().equals(
	              "PST") &&
	              supercededEntityLocatorParticipationDT.getCd().equals("BDL") &&
	              supercededEntityLocatorParticipationDT.getUseCd().equals("BIR")) {
	            if (!isPresentInSurvivingBirthPlaceDeathPlaceHomeAddress(
	                survivingEntityLocatorParticipationDTCollection, "BirthPlace")) {
	              addEntityLocPart(supercededEntityLocatorParticipationDT,
	                               survivingEntityLocatorParticipationDTCollection,
	                               survivingPersonVO);
	            }
	            else if (supercededEntityLocatorParticipationDT.getStatusCd() != null &&
	                     supercededEntityLocatorParticipationDT.getClassCd() != null &&
	                     supercededEntityLocatorParticipationDT.getUseCd() != null &&
	                     supercededEntityLocatorParticipationDT.getStatusCd().
	                     equals("A") &&
	                     supercededEntityLocatorParticipationDT.getClassCd().
	                     equals("PST") &&
	                     supercededEntityLocatorParticipationDT.
	                     getCd().equals("U") &&
	                     supercededEntityLocatorParticipationDT.getUseCd().equals(
	                "DTH")) {
	              if (!isPresentInSurvivingBirthPlaceDeathPlaceHomeAddress(
	                  survivingEntityLocatorParticipationDTCollection,
	                  "DeathPlace")) {
	                addEntityLocPart(supercededEntityLocatorParticipationDT,
	                    survivingEntityLocatorParticipationDTCollection,
	                    survivingPersonVO);
	              }
	            }
	            else if (supercededEntityLocatorParticipationDT.getStatusCd() != null &&
	                     supercededEntityLocatorParticipationDT.getClassCd() != null &&
	                     supercededEntityLocatorParticipationDT.getUseCd() != null &&
	                     supercededEntityLocatorParticipationDT.getStatusCd().
	                     equals("A") &&
	                     supercededEntityLocatorParticipationDT.getClassCd().
	                     equals("PST") &&
	                     supercededEntityLocatorParticipationDT.
	                     getCd().equals(NEDSSConstants.CURRENT) &&
	                     supercededEntityLocatorParticipationDT.getUseCd().equals(
	                NEDSSConstants.HOME)) {
	              if (!isPresentInSurvivingBirthPlaceDeathPlaceHomeAddress(
	                  survivingEntityLocatorParticipationDTCollection,
	                  "HomeAddress")) {
	                addEntityLocPart(supercededEntityLocatorParticipationDT,
	                    survivingEntityLocatorParticipationDTCollection,
	                    survivingPersonVO);
	              }
	            }
	            else {
	              // Add the superceded to surviving collection
	              addEntityLocPart(supercededEntityLocatorParticipationDT,
	                               survivingEntityLocatorParticipationDTCollection,
	                               survivingPersonVO);
	            }
	          }
	
	        } //end ... iterate superceded collection
	      }
	    }
} catch (Exception e) {
	// TODO Auto-generated catch block
	logger.fatal("EntityProxyEJB.copyUniquesEntityLocatorParticipationDT: " + e.getMessage(),e);
	throw new NEDSSSystemException(e.getMessage(),e);
}

  }

  /**
   *
   * @param supercededEntityLocatorParticipationDT   the EntityLocatorParticipationDT
   * @param survivingEntityLocatorParticipationDTCollection    the Collection<Object>  of EntityLocatorParticipationDTs
   * @param survivingPersonVO    the PersonVO
   * @throws ClassNotFoundException
   * @throws CloneNotSupportedException
   * @throws IOException
   */
  private void addEntityLocPart(EntityLocatorParticipationDT
                                supercededEntityLocatorParticipationDT,
                                Collection
                                survivingEntityLocatorParticipationDTCollection,
                                PersonVO survivingPersonVO) throws
      ClassNotFoundException, CloneNotSupportedException, IOException {
    try {
		logger.debug("Entered addEntityLocPart()");
		EntityLocatorParticipationDT entityLocatorParticipationDT = (
		    EntityLocatorParticipationDT) supercededEntityLocatorParticipationDT.
		    deepCopy();
		entityLocatorParticipationDT.setItNew(true);
		entityLocatorParticipationDT.setEntityUid(survivingPersonVO.
		                                          getThePersonDT().getPersonUid());
		survivingEntityLocatorParticipationDTCollection.add(
		    entityLocatorParticipationDT);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal("EntityProxyEJB.addEntityLocPart: " + e.getMessage(),e);
		throw new NEDSSSystemException(e.getMessage(),e);
	}

  }

  /**
   * Copies the Unique PersonNames in PersonVO
   * @param survivingPersonVO
   * @param supercededPersonVO
   * @throws NEDSSSystemException
   * @throws ClassNotFoundException
   * @throws CloneNotSupportedException
   * @throws IOException
   */
  private void copyUniquesPersonNameDT(PersonVO survivingPersonVO,
                                       PersonVO supercededPersonVO) throws
      NEDSSSystemException, ClassNotFoundException,
      CloneNotSupportedException, IOException {

   try {
	Iterator<Object>  supItr = null;
	   Iterator<Object>  survItr = null;
	    boolean uniqIdViolation = false;
	    Collection<Object>  supercededPersonNameDTCollection  = null;
	    Collection<Object>  survivingPersonNameDTCollection  = null;
	    PersonNameDT supercededPersonNameDT = null;
	    PersonNameDT survivingPersonNameDT = null;
	
	    supercededPersonNameDTCollection  = supercededPersonVO.
	        getThePersonNameDTCollection();
	
	    if (supercededPersonNameDTCollection  != null) {
	      supItr = supercededPersonNameDTCollection.iterator();
	
	      while (supItr.hasNext()) { //begin ... iterate superceded collection
	        // Check for unique id violation
	        supercededPersonNameDT = (PersonNameDT) supItr.next();
	
	        survivingPersonNameDTCollection  =
	            survivingPersonVO.getThePersonNameDTCollection();
	
	        // Start with no unique id violation
	        uniqIdViolation = false;
	        if (survivingPersonNameDTCollection  != null) {
	          survItr = survivingPersonNameDTCollection.iterator();
	
	          while (survItr.hasNext()) { //begin ... iterate surviving collection
	
	            survivingPersonNameDT = (PersonNameDT) survItr.next();
	            if ( (survivingPersonNameDT.getPersonUid().equals(
	                supercededPersonNameDT.getPersonUid()))
	                &&
	                (survivingPersonNameDT.getPersonNameSeq().equals(
	                supercededPersonNameDT.
	                getPersonNameSeq()))) {
	              uniqIdViolation = true;
	            }
	
	            // Since we found a unique id violation, no need to find further
	            if (uniqIdViolation) {
	              logger.debug("will break now!");
	              break;
	            }
	          } //end ... iterate surviving collection
	        }
	
	        // Append to surviving collection only if there is no unique id violation
	        if (!uniqIdViolation) {
	
	          if (!isPresentInSurvivingLegalName(survivingPersonNameDTCollection)) {
	            // Add the superceded to surviving collection
	            PersonNameDT pdt = (PersonNameDT) supercededPersonNameDT.deepCopy();
	            pdt.setItNew(true);
	            pdt.setPersonUid(survivingPersonVO.getThePersonDT().getPersonUid());
	            pdt.setPersonNameSeq(new Integer(getPersonDTMaxSeqNum(
	                survivingPersonNameDTCollection) + 1));
	
	            survivingPersonNameDTCollection.add(pdt);
	          }
	        }
	
	      } //end ... iterate superceded collection
	    }
} catch (Exception e) {
	// TODO Auto-generated catch block
	logger.fatal("EntityProxyEJB.copyUniquesPersonNameDT: " + e.getMessage(),e);
	throw new NEDSSSystemException(e.getMessage(),e);
}
  }

  /**
   * Copies the Unique PersonRace in the PersonVO
   * @param survivingPersonVO
   * @param supercededPersonVO
   * @throws NEDSSSystemException
   * @throws ClassNotFoundException
   * @throws CloneNotSupportedException
   * @throws IOException
   */
  private void copyUniquesPersonRaceDT(PersonVO survivingPersonVO,
                                       PersonVO supercededPersonVO) throws
      NEDSSSystemException, ClassNotFoundException,
      CloneNotSupportedException, IOException {

   try {
	Iterator<Object>  supItr = null;
	   Iterator<Object>  survItr = null;
	    boolean uniqIdViolation = false;
	    Collection<Object>  supercededPersonRaceDTCollection  = null;
	    Collection<Object>  survivingPersonRaceDTCollection  = null;
	    PersonRaceDT supercededPersonRaceDT = null;
	    PersonRaceDT survivingPersonRaceDT = null;
	
	    supercededPersonRaceDTCollection  = supercededPersonVO.
	        getThePersonRaceDTCollection();
	
	    if (supercededPersonRaceDTCollection  != null) {
	      supItr = supercededPersonRaceDTCollection.iterator();
	
	      while (supItr.hasNext()) { //begin ... iterate superceded collection
	        // Check for unique id violation
	        supercededPersonRaceDT = (PersonRaceDT) supItr.next();
	
	        survivingPersonRaceDTCollection  =
	            survivingPersonVO.getThePersonRaceDTCollection();
	
	        // Start with no unique id violation
	        uniqIdViolation = false;
	        if (survivingPersonRaceDTCollection  != null) {
	          survItr = survivingPersonRaceDTCollection.iterator();
	
	          while (survItr.hasNext()) { //begin ... iterate surviving collection
	
	            survivingPersonRaceDT = (PersonRaceDT) survItr.next();
	            if ( (survivingPersonRaceDT.getPersonUid().equals(
	                supercededPersonRaceDT.getPersonUid()))) {
	              uniqIdViolation = true;
	            }
	
	            // Since we found a unique id violation, no need to find further
	            if (uniqIdViolation) {
	              logger.debug("will break now!");
	              break;
	            }
	          } //end ... iterate surviving collection
	        }
	
	        // Append to surviving collection only if there is no unique id violation
	        if (!uniqIdViolation) {
	
	          logger.debug("Number in PersonRaceDTCollection: " +
	                       survivingPersonRaceDTCollection.size());
	          // Add the superceded to surviving collection if it's not already there
	          PersonRaceDT pdt = (PersonRaceDT) supercededPersonRaceDT.deepCopy();
	          //check to see if it is in the surviving collection
	         Iterator<Object>  survIter = survivingPersonRaceDTCollection.iterator();
	          boolean foundDuplicate = false;
	          while (survIter.hasNext()) {
	            PersonRaceDT survDT = (PersonRaceDT) survIter.next();
	            if (pdt.getRaceCd().equals(survDT.getRaceCd())) {
	              //This means it's already in the surviving collection
	              foundDuplicate = true;
	              break; //kick out of while loop
	            }
	          } //end while... finished check for duplicates
	
	          if (!foundDuplicate) {
	            pdt.setItNew(true);
	            pdt.setPersonUid(survivingPersonVO.getThePersonDT().getPersonUid());
	            survivingPersonRaceDTCollection.add(pdt);
	          }
	
	        }
	
	      } //end ... iterate superceded collection
	    }
} catch (Exception e) {
	// TODO Auto-generated catch block
	logger.fatal("EntityProxyEJB.copyUniquesPersonRaceDT: " + e.getMessage(),e);
	throw new NEDSSSystemException(e.getMessage(),e);
}
  }


    /**
     * Gets the Maximum Sequence Number
     * 
     * @param names
     *            the Collection
     * @return int - the Maximum Sequence Number
     */
    private int getPersonDTMaxSeqNum(Collection<Object> names)
    {
        Integer maxSeqNbr = new Integer(0);
        if (names != null)
        {
            try {
				Iterator<Object> itrCount = names.iterator();

				// need to find the max seq nbr for existing names
				while (itrCount.hasNext())
				{

				    PersonNameDT nameDT = (PersonNameDT) itrCount.next();

				    if (nameDT.getPersonNameSeq() != null)
				    {
				        if (nameDT.getPersonNameSeq().compareTo(maxSeqNbr) > 0)
				        { // update the maxSeqNbr when you find a bigger one
				            maxSeqNbr = nameDT.getPersonNameSeq();
				        }
				    }
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.fatal("EntityProxyEJB.getPersonDTMaxSeqNum: " + e.getMessage(),e);
				throw new NEDSSSystemException(e.getMessage(),e);
			}
        }
        return maxSeqNbr.intValue();
    }

    /**
     * Gets the Greatest RoleSequenceNumber from the Roles
     * 
     * @param roles
     *            the Collection
     * @return int
     * @throws NEDSSSystemException
     */
    private int getRoleDTMaxSeqNum(Collection<Object> roles) throws NEDSSSystemException
    {
        Long maxSeqNbr = new Long(0);

        try
        {
            if (roles != null)
            {
                Iterator<Object> itrCount = roles.iterator();
                // need to find the max seq nbr for existing roles
                while (itrCount.hasNext())
                { // begin...while(itrCount.hasNext())
                    RoleDT roleDT = (RoleDT) itrCount.next();
                    if (roleDT.getRoleSeq() != null)
                    {
                        if (roleDT.getRoleSeq().compareTo(maxSeqNbr) > 0)
                        { // update the maxSeqNbr when you find a bigger one
                            maxSeqNbr = roleDT.getRoleSeq();
                        }
                    }
                } // end...while(itrCount.hasNext())
            }
        }
        catch (Exception e)
        {
            logger.fatal("EntityProxyEJB.getRoleDTMaxSeqNum: "  + e.getMessage(),e);
            throw new NEDSSSystemException(e.getMessage(),e);
        }
        return maxSeqNbr.intValue();
    }

    /**
     * 
     * @param ids
     *            the Collection
     * @return int
     */
    private int getEntityIdDTMaxSeqNum(Collection<Object> ids)
    {
        Integer maxSeqNbr = new Integer(0);
        if (ids != null)
        {
            try {
				Iterator<Object> itrCount = ids.iterator();
				while (itrCount.hasNext())
				{
				    EntityIdDT idDT = (EntityIdDT) itrCount.next();
				    if (idDT.getEntityIdSeq() != null)
				    {
				        if (idDT.getEntityIdSeq().compareTo(maxSeqNbr) > 0)
				        { // update the maxSeqNbr when you find a bigger one
				            maxSeqNbr = idDT.getEntityIdSeq();
				        }
				    }
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.fatal("EntityProxyEJB.getEntityIdDTMaxSeqNum: "  + e.getMessage(),e);
	            throw new NEDSSSystemException(e.getMessage(),e);
			}
        }
        return maxSeqNbr.intValue();
    }

  /**
   * Copying the Unique  ParticipationDTs
   * @param survivingPersonVO     the PersonVO
   * @param supercededPersonVO    the PersonVO
   * @throws ClassNotFoundException
   * @throws CloneNotSupportedException
   * @throws IOException
   */
  private void copyUniquesParticipationDT(PersonVO survivingPersonVO,
                                          PersonVO supercededPersonVO) throws
      ClassNotFoundException, CloneNotSupportedException,
      IOException {

   try {
	Iterator<Object>  supItr = null;
	   Iterator<Object>  survItr = null;
	    boolean uniqIdViolation = false;
	    Collection<Object>  supercededParticipationDTCollection  = null;
	    Collection<Object>  survivingParticipationDTCollection  = null;
	    ParticipationDT supercededParticipationDT = null;
	    ParticipationDT survivingParticipationDT = null;
	
	    supercededParticipationDTCollection  = supercededPersonVO.
	        getTheParticipationDTCollection();
	    logger.error("Number of superceded Participations: " +
	                 supercededParticipationDTCollection.size());
	    if (supercededParticipationDTCollection  != null) { //begin...if (supercededParticipationDTCollection  != null)
	      supItr = supercededParticipationDTCollection.iterator();
	
	      while (supItr.hasNext()) { //begin ... iterate superceded collection
	        // Check for unique id violation
	        supercededParticipationDT = (ParticipationDT) supItr.next();
	        logger.error(supercededParticipationDT.getActUid());
	        logger.error(supercededParticipationDT.getRoleSeq());
	
	        survivingParticipationDTCollection  =
	            survivingPersonVO.getTheParticipationDTCollection();
	
	        // Start with no unique id violation
	        uniqIdViolation = false;
	
	        if (survivingParticipationDTCollection  != null) {
	          survItr = survivingParticipationDTCollection.iterator();
	
	          while (survItr.hasNext()) { //begin ... iterate surviving collection
	
	            survivingParticipationDT = (ParticipationDT) survItr.next();
	            if ( (survivingParticipationDT.getActUid().equals(
	                supercededParticipationDT.getActUid()))
	                &&
	                (survivingParticipationDT.getTypeCd().equals(
	                supercededParticipationDT.
	                getTypeCd()))) {
	              uniqIdViolation = true;
	            }
	
	            // Since we found a unique id violation, no need to find further
	            if (uniqIdViolation) {
	              break;
	            }
	          } //end ... iterate surviving collection
	        }
	
	        // Append to surviving collection only if there is no unique id violation
	        if (!uniqIdViolation) {
	          logger.error("No uniqueID violation");
	          // Add the superceded to surviving collection
	          ParticipationDT participationDT = (ParticipationDT)
	              supercededParticipationDT.deepCopy();
	          logger.error(
	              "value of surviving PersonUID to write to superceded participation: " +
	              survivingPersonVO.getThePersonDT().getPersonUid());
	          participationDT.setSubjectEntityUid(survivingPersonVO.
	                                              getThePersonDT().getPersonUid());
	          logger.error("value of personUID after a get: " +
	                       participationDT.getSubjectEntityUid());
	          participationDT.setItNew(true);
	          survivingParticipationDTCollection.add(participationDT);
	          logger.debug("Adding sup participations to surv");
	        }
	
	      } //end ... iterate superceded collection
	    } //end...if (supercededParticipationDTCollection  != null)
} catch (Exception e) {
	// TODO Auto-generated catch block
	logger.fatal("EntityProxyEJB.copyUniquesParticipationDT: " + e.getMessage(),e);
	throw new NEDSSSystemException(e.getMessage(),e);
}
  }

  /**
   * Copies unique RoleDTs in PersonVOs
   * @param survivingPersonVO    the PersonVO
   * @param supercededPersonVO    the PersonVO
   * @throws NEDSSSystemException
   * @throws ClassNotFoundException
   * @throws CloneNotSupportedException
   * @throws IOException
   */
  private void copyUniquesRoleDT(PersonVO survivingPersonVO,
                                 PersonVO supercededPersonVO) throws
      NEDSSSystemException, ClassNotFoundException,
      CloneNotSupportedException, IOException {

   try {
	Iterator<Object>  supItr = null;
	   Iterator<Object>  survItr = null;
	    boolean uniqIdViolation = false;
	    Collection<Object>  supercededRoleDTCollection  = null;
	    Collection<Object>  survivingRoleDTCollection  = null;
	    RoleDT supercededRoleDT = null;
	    RoleDT survivingRoleDT = null;
	
	    logger.debug("Entered copyUniquesRoleDT method");
	
	    supercededRoleDTCollection  = supercededPersonVO.getTheRoleDTCollection();
	
	    if (supercededRoleDTCollection  != null) { //begin...if (supercededRoleDTCollection  != null)
	      supItr = supercededRoleDTCollection.iterator();
	
	      while (supItr.hasNext()) { //begin ... iterate superceded collection
	        // Check for unique id violation
	        supercededRoleDT = (RoleDT) supItr.next();
	
	        logger.debug("Sup role cd is " + supercededRoleDT.getCd());
	
	        if (survivingPersonVO == null) {
	          logger.debug("Oh gosh! Surv Pers VO is NULL");
	        }
	        else {
	          survivingRoleDTCollection  = survivingPersonVO.
	              getTheRoleDTCollection();
	
	          // Start with no unique id violation
	          uniqIdViolation = false;
	
	          if (survivingRoleDTCollection  != null) { //begin...if (survivingRoleDTCollection  != null)
	            survItr = survivingRoleDTCollection.iterator();
	
	            while (survItr.hasNext()) { //begin ... iterate surviving collection
	
	              survivingRoleDT = (RoleDT) survItr.next();
	
	              logger.debug("Surv role cd is " + survivingRoleDT.getCd());
	
	              if ( (survivingRoleDT.getSubjectEntityUid().equals(
	                  supercededRoleDT.getSubjectEntityUid()))
	                  && (survivingRoleDT.getCd().equals(supercededRoleDT.getCd()))
	                  &&
	                  (survivingRoleDT.getRoleSeq().equals(supercededRoleDT.
	                  getRoleSeq()))) {
	
	                uniqIdViolation = true;
	              }
	
	              // Since we found a unique id violation, no need to find further
	              if (uniqIdViolation) {
	                break;
	              }
	            } //end ... iterate surviving collection
	          } //end...if (survivingRoleDTCollection  != null)
	          else {
	            logger.debug("Surv DT coll is NULL");
	          }
	
	          // Append to surviving collection only if there is no unique id violation
	          if (!uniqIdViolation) {
	            // Add the superceded to surviving collection
	            logger.debug("Before supercededRoleDT deep copy");
	            RoleDT roleDT = (RoleDT) supercededRoleDT.deepCopy();
	            logger.debug("Before setitnew");
	            roleDT.setItNew(true);
	            logger.debug("Before setScopingEntityUid");
	            roleDT.setScopingEntityUid(survivingPersonVO.getThePersonDT().
	                                       getPersonUid());
	            logger.debug("Before setRoleSeq");
	            roleDT.setRoleSeq(new Long(getRoleDTMaxSeqNum(
	                survivingRoleDTCollection) + 1));
	            logger.debug("Before survivingRoleDTCollection.add");
	            survivingRoleDTCollection.add(roleDT);
	            logger.debug("After Adding sup roles to surv");
	          }
	        }
	
	      } //end ... iterate superceded collection
	    }
} catch (Exception e) {
	// TODO Auto-generated catch block
	logger.fatal("EntityProxyEJB.copyUniquesRoleDT: " + e.getMessage(),e);
	throw new NEDSSSystemException(e.getMessage(),e);
}
  }

  /**
   * Copies the unique UniquesEntityIdDTs in PersonVOs
   * @param survivingPersonVO    the PersonVO
   * @param supercededPersonVO   the PersonVO
   * @throws NEDSSSystemException
   * @throws ClassNotFoundException
   * @throws CloneNotSupportedException
   * @throws IOException
   */
  private void copyUniquesEntityIdDT(PersonVO survivingPersonVO,
                                     PersonVO supercededPersonVO) throws
      NEDSSSystemException, ClassNotFoundException,
      CloneNotSupportedException, IOException {

   try {
	Iterator<Object>  supItr = null;
	   Iterator<Object>  survItr = null;
	    boolean uniqIdViolation = false;
	    Collection<Object>  supercededEntityIdDTCollection  = null;
	    Collection<Object>  survivingEntityIdDTCollection  = null;
	    EntityIdDT supercededEntityIdDT = null;
	    EntityIdDT survivingEntityIdDT = null;
	
	    supercededEntityIdDTCollection  = supercededPersonVO.
	        getTheEntityIdDTCollection();
	
	    if (supercededEntityIdDTCollection  != null) {
	      supItr = supercededEntityIdDTCollection.iterator();
	
	      while (supItr.hasNext()) { //begin ... iterate superceded collection
	        // Check for unique id violation
	        supercededEntityIdDT = (EntityIdDT) supItr.next();
	
	        survivingEntityIdDTCollection  =
	            survivingPersonVO.getTheEntityIdDTCollection();
	
	        // Start with no unique id violation
	        uniqIdViolation = false;
	        if (survivingEntityIdDTCollection  != null) {
	          survItr = survivingEntityIdDTCollection.iterator();
	
	          while (survItr.hasNext()) { //begin ... iterate surviving collection
	
	            survivingEntityIdDT = (EntityIdDT) survItr.next();
	
	            if ( (survivingEntityIdDT.getEntityUid().equals(
	                supercededEntityIdDT.getEntityUid()))
	                &&
	                (survivingEntityIdDT.getEntityIdSeq().equals(
	                supercededEntityIdDT.
	                getEntityIdSeq()))) {
	
	              uniqIdViolation = true;
	            }
	
	            // Since we found a unique id violation, no need to find further
	            if (uniqIdViolation) {
	              break;
	            }
	          } //end ... iterate surviving collection
	        }
	
	        // Append to surviving collection only if there is no unique id violation
	        // Append to surviving collection only if there is no unique id violation
	        if (!uniqIdViolation) {
	
	          // Donot copy if the surviving record already has SSN
	          if (!isPresentInSurvivingSSN(survivingEntityIdDTCollection)) {
	            // Add the superceded to surviving collection
	            EntityIdDT entityDT = (EntityIdDT) supercededEntityIdDT.deepCopy();
	            supercededEntityIdDT.setItNew(false);
	            entityDT.setItNew(true);
	            entityDT.setEntityUid(survivingPersonVO.getThePersonDT().
	                                  getPersonUid());
	            entityDT.setEntityIdSeq(new Integer(getEntityIdDTMaxSeqNum(
	                survivingEntityIdDTCollection) + 1));
	            survivingEntityIdDTCollection.add(entityDT);
	            logger.debug("Adding sup entity ids to surv");
	          }
	        }
	
	      } //end ... iterate superceded collection
	    }
} catch (Exception e) {
	// TODO Auto-generated catch block
	logger.fatal("EntityProxyEJB.copyUniquesEntityIdDT: " + e.getMessage(),e);
	throw new NEDSSSystemException(e.getMessage(),e);
}
  }

    /**
     * checking whether the person is having SSN as the ID
     * 
     * @param survivingEntityIdDTCollection
     *            the Collection<Object> of EntityIdDTs from personVO
     * @return boolean (true if SSN is there else false)
     */
    private boolean isPresentInSurvivingSSN(Collection<?> survivingEntityIdDTCollection)
    {
        try {
			Iterator<?> survItr = null;
			EntityIdDT survivingEntityIdDT = null;

			// Start with no unique id violation
			boolean isPresent = false;

			logger.debug("Entered isPresentInSurvivingSSN()");
			if (survivingEntityIdDTCollection != null)
			{
			    survItr = survivingEntityIdDTCollection.iterator();

			    logger.debug("The size of surviving person entity id dt collection is "
			            + survivingEntityIdDTCollection.size());

			    while (survItr.hasNext())
			    { // begin ... iterate surviving collection

			        survivingEntityIdDT = (EntityIdDT) survItr.next();

			        logger.debug("GetAssigningAuthorityCode-" + survivingEntityIdDT.getAssigningAuthorityCd());
			        logger.debug("GetTypeCd-" + survivingEntityIdDT.getTypeCd());
			        if ((survivingEntityIdDT.getAssigningAuthorityCd().equals("SSA"))
			                && (survivingEntityIdDT.getTypeCd().equals("SS")))
			        {

			            isPresent = true;
			            // Since we found it, no need to find further
			            break;
			        }

			    } // end ... iterate surviving collection
			}
			return isPresent;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityProxyEJB.isPresentInSurvivingSSN: " + e.getMessage(),e);
			throw new NEDSSSystemException(e.getMessage(),e);
		}
    }

    /**
     * checking whether the person has a Legal Name
     * 
     * @param survivingPersonNameDTCollection
     *            the collection of PersonNameDTs
     * @return boolean - returns true if the person has a legal name else false
     */
    private boolean isPresentInSurvivingLegalName(Collection<?> survivingPersonNameDTCollection)
    {

        try {
			Iterator<?> survItr = null;
			PersonNameDT survivingPersonNameDT = null;

			// Start with no unique id violation
			boolean isPresent = false;

			logger.debug("Entered isPresentInSurvivingLegalName()");
			if (survivingPersonNameDTCollection != null)
			{
			    survItr = survivingPersonNameDTCollection.iterator();

			    logger.debug("The size of surviving person name dt collection is " + survivingPersonNameDTCollection.size());

			    while (survItr.hasNext())
			    { // begin ... iterate surviving collection

			        survivingPersonNameDT = (PersonNameDT) survItr.next();

			        logger.debug("GetNmUseCd-" + survivingPersonNameDT.getNmUseCd());
			        if (survivingPersonNameDT.getNmUseCd().equals("L"))
			        {

			            isPresent = true;
			            // Since we found it, no need to find further
			            break;
			        }
			    } // end ... iterate surviving collection

			}
			return isPresent;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("EntityProxyEJB.isPresentInSurvivingLegalName: " + e.getMessage(),e);
			throw new NEDSSSystemException(e.getMessage(),e);
		}
    }

  /**
   * Checking whether the person has a Birthplace, DeathPlace or Home Address
   * @param survivingEntityLocatorParticipationDTCollection
   * @param type     the String
   * @return   boolean
   */
  private boolean isPresentInSurvivingBirthPlaceDeathPlaceHomeAddress(
      Collection<Object>  survivingEntityLocatorParticipationDTCollection,
      String type) {

   try {
	Iterator<Object>  survItr = null;
	    EntityLocatorParticipationDT survivingEntityLocatorParticipationDT = null;
	
	    // Start with no unique id violation
	    boolean isPresent = false;
	
	    logger.debug(
	        "Entered isPresentInSurvivingBirthPlaceDeathPlaceHomeAddress()");
	    if (survivingEntityLocatorParticipationDTCollection  != null) {
	      survItr = survivingEntityLocatorParticipationDTCollection.iterator();
	
	      logger.debug(
	          "The size of surviving person entity locator participation dt collection is " +
	          survivingEntityLocatorParticipationDTCollection.size());
	
	      while (survItr.hasNext()) { //begin ... iterate surviving collection
	
	        survivingEntityLocatorParticipationDT = (EntityLocatorParticipationDT)
	            survItr.next();
	
	        logger.debug("getUseCd-" +
	                     survivingEntityLocatorParticipationDT.getUseCd());
	        logger.debug("getCd-" + survivingEntityLocatorParticipationDT.getCd());
	
	        if (type.equalsIgnoreCase("BirthPlace")) {
	
	          if ( (survivingEntityLocatorParticipationDT.getUseCd().equals("BIR"))
	              && (survivingEntityLocatorParticipationDT.getCd().equals("BDL"))
	              && (survivingEntityLocatorParticipationDT.getClassCd() != null)
	              && (survivingEntityLocatorParticipationDT.getUseCd() != null)
	              &&
	              (survivingEntityLocatorParticipationDT.getStatusCd().equals("A"))
	              &&
	              (survivingEntityLocatorParticipationDT.getClassCd().equals("PST"))) {
	
	            logger.debug(
	                "Inside isPresentInSurvivingBirthPlaceDeathPlaceHomeAddress, found " +
	                type);
	            isPresent = true;
	            // Since we found it, no need to find further
	            break;
	          }
	        }
	        else if (type.equalsIgnoreCase("DeathPlace")) {
	          if ( (survivingEntityLocatorParticipationDT.getUseCd().equals("DTH"))
	              && (survivingEntityLocatorParticipationDT.getCd().equals("U"))
	              && (survivingEntityLocatorParticipationDT.getClassCd() != null)
	              && (survivingEntityLocatorParticipationDT.getUseCd() != null)
	              &&
	              (survivingEntityLocatorParticipationDT.getStatusCd().equals("A"))
	              &&
	              (survivingEntityLocatorParticipationDT.getClassCd().equals("PST"))) {
	
	            logger.debug(
	                "Inside isPresentInSurvivingBirthPlaceDeathPlaceHomeAddress, found " +
	                type);
	            isPresent = true;
	            // Since we found it, no need to find further
	            break;
	          }
	
	        }
	        else if (type.equalsIgnoreCase("HomeAddress")) {
	          if ( (survivingEntityLocatorParticipationDT.getUseCd().equals(
	              NEDSSConstants.HOME))
	              &&
	              (survivingEntityLocatorParticipationDT.getCd().equals(
	              NEDSSConstants.
	              CURRENTADDRESS))
	              && (survivingEntityLocatorParticipationDT.getClassCd() != null)
	              && (survivingEntityLocatorParticipationDT.getUseCd() != null)
	              &&
	              (survivingEntityLocatorParticipationDT.getStatusCd().equals("A"))
	              &&
	              (survivingEntityLocatorParticipationDT.getClassCd().equals("PST"))) {
	
	            logger.debug(
	                "Inside isPresentInSurvivingBirthPlaceDeathPlaceHomeAddress, found " +
	                type);
	            isPresent = true;
	            // Since we found it, no need to find further
	            break;
	          }
	        }
	
	      } //end ... iterate surviving collection
	
	    }
	
	    return isPresent;
} catch (Exception e) {
	// TODO Auto-generated catch block
	logger.fatal("EntityProxyEJB.isPresentInSurvivingBirthPlaceDeathPlaceHomeAddress: " + e.getMessage(),e);
	throw new NEDSSSystemException(e.getMessage(),e);
}

  }

    /**
     * Setting the values for the collection of Persons
     * 
     * @roseuid 3D615B0B03D9
     * @J2EE_METHOD -- setPersons
     * @param personVOCollection
     *            the collection of PersonVOs
     * @param nbsSecurityObj
     *            the NBSSecurityObj
     * @return personUidCollection the collection personUids
     * @throws RemoteException
     * @throws EJBException
     * @throws NEDSSSystemException
     * @throws NEDSSConcurrentDataException
     */
    public Collection<Object> setPersons(Collection<Object> personVOCollection, NBSSecurityObj nbsSecurityObj)
            throws java.rmi.RemoteException, javax.ejb.EJBException, NEDSSSystemException, NEDSSConcurrentDataException
    {
        ArrayList<Object> personUidCollection = new ArrayList<Object>();

        Iterator<Object> itr = personVOCollection.iterator();
        try
        {
            while (itr.hasNext())
            {
                PersonVO personVO = (PersonVO) itr.next();
                Long personUid = setPerson(personVO, nbsSecurityObj);
                personUidCollection.add(personUid);
            }
        }
        catch (Exception e)
        {
            logger.fatal("EntityProxyEJB.setPersons: " + e.getMessage(),e);
            throw new NEDSSSystemException(e.getMessage(),e);
        }
        return personUidCollection;
    }

    /**
     * Setting the values for the collection of organizations
     * 
     * @roseuid 3D615B0C014F
     * @J2EE_METHOD -- setOrganizations
     * @param organizationVOCollection
     *            the collection of OrganizationVOs
     * @param nbsSecurityObj
     *            the NBSSecurityObj
     * @return collection of organizationUIDs
     * @throws RemoteException
     * @throws EJBException
     * @throws NEDSSSystemException
     * @throws NEDSSConcurrentDataException
     */
    public Collection<Object> setOrganizations(Collection<Object> organizationVOCollection,
            NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException,
            NEDSSSystemException, NEDSSConcurrentDataException
    {
        ArrayList<Object> organizationUidCollection = new ArrayList<Object>();

        Iterator<Object> itr = organizationVOCollection.iterator();
        try
        {
            while (itr.hasNext())
            {
                OrganizationVO organizationVO = (OrganizationVO) itr.next();
                Long organizationUid = setOrganization(organizationVO, nbsSecurityObj);
                organizationUidCollection.add(organizationUid);
            }
        }
        catch (Exception e)
        {
            logger.fatal("EntityProxyEJB.setOrganizations: " + e.getMessage(),e);
            throw new NEDSSSystemException(e.getMessage(),e);
        }
        return organizationUidCollection;
    }

  /**
       * This method first checks to see if the user has view permissions for Master Patient Record (MPR)
       * and then gets the MPR values from the database
       *
       * @roseuid 3E6F839501FF
       * @J2EE_METHOD  --  getMPR
       * @param personUid   Long
       * @param nbsSecurityObj  NBSSecurityObj
       * @return  personVO PersonVO object
       * @throws EJBException
       * @throws NEDSSSystemException
       * @throws NEDSSConcurrentDataException
       */
      public PersonVO getMPR    (Long personUid, NBSSecurityObj nbsSecurityObj) throws
          EJBException, NEDSSSystemException
        {

          logger.info("getting MPR -----------------------");
          
          if(personUid == null)
          return null;

          try{

            /* Check the user permisson to view Patient
             */
            PersonVO personVO = null;
            boolean checkView = nbsSecurityObj.getPermission(NBSBOLookup.PATIENT, NBSOperationLookup.VIEW);

            if(!checkView){
              logger.error("User does not have permission to access patient info");
              throw new NEDSSSystemException("Don't have permission to view Patient");
            }
            else{ //obtain reference to EntityControllerEJB
              logger.debug("get reference of EntityControllerEJB");
              NedssUtils nedssUtils = new NedssUtils();

              Object lookUpEntityController = nedssUtils.lookupBean(JNDINames.EntityControllerEJB);
              Object lookUpJurisdiction = nedssUtils.lookupBean(JNDINames.JURISDICTION_EJB);

              EntityControllerHome ecHome = (EntityControllerHome) PortableRemoteObject.narrow(lookUpEntityController, EntityControllerHome.class);
              EntityController entityController = ecHome.create();
              personVO = entityController.getMPR(personUid, nbsSecurityObj);

              JurisdictionHome jurHome = (JurisdictionHome) PortableRemoteObject.narrow(lookUpJurisdiction, JurisdictionHome.class);
              Jurisdiction jurisdiction = jurHome.create();
              //get the jurisdiction for the patient
              Collection<Object>  jurColl = jurisdiction.findJurisdictionForPatient(personVO);


                if (jurColl !=null && jurColl.size() == 1) {

                  for(Iterator<Object> i = jurColl.iterator(); i.hasNext();){
                    //should only contain one jurisdiction code
                    String jurCd = (String) i.next();
                    personVO.setDefaultJurisdictionCd(jurCd);
                  }


                }
                else {
                  logger.info(
                      "No Jurisdiction is found or multiple jurisdictions are found and the " +
                      "juridsdiction can't be derived");

                }
                if (personVO.getThePersonDT() != null && (personVO.getThePersonDT().getElectronicInd() != null
        				&& !personVO.getThePersonDT().getElectronicInd().equals(NEDSSConstants.ELECTRONIC_IND_ELR))) {
                //for LDFs
                ArrayList<Object> ldfList = new ArrayList<Object> ();
                try
                {
                  //ldfList = (ArrayList<Object> ) getLDFCollection(personUid.longValue());
                  //code for new ldf back end condition code is null for person
                    LDFHelper ldfHelper = LDFHelper.getInstance();
                    ldfList = (ArrayList<Object> ) ldfHelper.getLDFCollection(personUid,null,nbsSecurityObj);


                }
                catch (Exception e) {
                  logger.error(
                      "Exception occured while retrieving LDFCollection<Object>  = " +
                      e.toString());
                }

                if (ldfList != null) {
                  logger.debug("Before setting LDFCollection<Object>  = " + ldfList.size());
                  personVO.setTheStateDefinedFieldDataDTCollection(ldfList);
                }
                }

              return personVO;

            }//else

          }//try
          catch (Exception e){
            logger.fatal("EntityProxyEJB.getMPR: " + e.getMessage(),e);
            throw new NEDSSSystemException(e.getMessage(),e);
          }//catch

        }


    /**
     * This method checks the permission of the user to add the Master Patient
     * Record (MPR) and sends the PersonVO values to the database
     * 
     * @roseuid 3D9B4AFB03A9
     * @J2EE_METHOD -- setMPR
     * @param personVO
     *            the PersonVO
     * @param nbsSecurityObj
     *            the NBSSecurityObj
     * @return personUid the Long value
     * @throws EJBException
     * @throws NEDSSSystemException
     * @throws NEDSSConcurrentDataException
     */
    public Long setMPR(PersonVO personVO, NBSSecurityObj nbsSecurityObj) throws EJBException, NEDSSSystemException,
            NEDSSConcurrentDataException
    {
        logger.info("setting MPR----------------------");

        Long personUid = null;

        try
        {
            if (personVO.isItNew())  // is it an add
            {
                // check the permission to add an MPR
                boolean checkAdd = nbsSecurityObj.getPermission(NBSBOLookup.PATIENT, NBSOperationLookup.ADD);

                if (!checkAdd)
                {
                    logger.error("Don't have permission to add personVO");
                    throw new NEDSSSystemException("Don't have permission to add personVO");
                }
                else
                {
                    String businessTriggerCD = "PAT_CR";
                    logger.debug("get reference of EntityControllerEJB");

                    NedssUtils nedssUtils = new NedssUtils();

                    Object theLookedUpObject = nedssUtils.lookupBean(JNDINames.EntityControllerEJB);

                    EntityControllerHome ecHome = (EntityControllerHome) PortableRemoteObject.narrow(theLookedUpObject,
                            EntityControllerHome.class);
                    EntityController entityController = ecHome.create();
                    personUid = entityController.setMPR(personVO, businessTriggerCD, nbsSecurityObj);
                    // setLDFCollection(personVO.getTheStateDefinedFieldDTCollection(),
                    // NEDSSConstants.PERSON_LDF,personUid );
                    // code for new ldf backend.
					if (personVO.getTheStateDefinedFieldDataDTCollection() != null
							&& personVO.getTheStateDefinedFieldDataDTCollection().size() > 0) {
						LDFHelper ldfHelper = LDFHelper.getInstance();
						ldfHelper.setLDFCollection(personVO.getTheStateDefinedFieldDataDTCollection(),
								personVO.getLdfUids(), NEDSSConstants.PATIENT_LDF, null, personUid, nbsSecurityObj);
					}
                }
            }
            else if (personVO.isItDirty())
            {// is it an edit

                // check the permission to edit a MPR
                boolean checkEdit = nbsSecurityObj.getPermission(NBSBOLookup.PATIENT, NBSOperationLookup.EDIT);

                if (!checkEdit)
                {
                    logger.error("Don't have permission to add personVO");
                    throw new NEDSSSystemException("Don't have permission to add personVO");
                }
                else
                {
                    String businessTriggerCD = "PAT_EDIT";
                    logger.debug("get reference of EntityControllerEJB");
                    NedssUtils nedssUtils = new NedssUtils();

                    Object theLookedUpObject = nedssUtils.lookupBean(JNDINames.EntityControllerEJB);

                    EntityControllerHome ecHome = (EntityControllerHome) PortableRemoteObject.narrow(theLookedUpObject,
                            EntityControllerHome.class);
                    EntityController entityController = ecHome.create();
                    personUid = entityController.setMPR(personVO, businessTriggerCD, nbsSecurityObj);
                    // setLDFCollection(personVO.getTheStateDefinedFieldDTCollection(),
                    // NEDSSConstants.PERSON_LDF,personUid );
                    // code for new ldf backend.
                    if (personVO.getTheStateDefinedFieldDataDTCollection() != null
							&& personVO.getTheStateDefinedFieldDataDTCollection().size() > 0) {
                    LDFHelper ldfHelper = LDFHelper.getInstance();
                    ldfHelper.setLDFCollection(personVO.getTheStateDefinedFieldDataDTCollection(),
                            personVO.getLdfUids(), NEDSSConstants.PATIENT_LDF, null, personUid, nbsSecurityObj);
                    }
                }
            }// end of else if

        }// end of try
        catch (NEDSSConcurrentDataException ex)
        {
            logger.fatal("EntityProxyEJB.setMPR: NEDSSConcurrentDataException. Concurrent access occurred in EntityProxyEJB : " + ex.getMessage(),ex);
            throw new NEDSSConcurrentDataException(ex.getMessage(),ex);
        }
        catch (Exception e)
        {
            logger.error("EntityProxyEJB.setMPR: " + e);
            throw new NEDSSSystemException(e.getMessage(),e);
        }// end of catch

        logger.info("EntityProxyEJB.setMPR returning personUid: " + personUid);
        return personUid;
    }

    /**
     * Look up reporting lab's clia number
     * 
     * @param organizationUid
     * @return String
     */
    public String organizationCLIALookup(Long organizationUid, NBSSecurityObj nbsSecurityObj)
    {
        EntityProxyHelper epHelper = EntityProxyHelper.getInstance();
        return epHelper.organizationCLIALookup(organizationUid);
    }
    
    public Collection<Object> getPersonByEpilinkCollection (Long publicHealthcaseUID,  NBSSecurityObj securityObj)  throws java.rmi.RemoteException, javax.ejb.EJBException, NEDSSSystemException

    {
    	 FindPersonDAOImpl personDAO = new FindPersonDAOImpl();
    	
       try
      {
    	 return personDAO.getPersonByEpilinkCollection(publicHealthcaseUID);
      }
      catch(Exception e)
      {
          logger.debug(e.toString()+" : setPublicHealthCaseVO dataconcurrency catch: " + e.getClass());
          logger.debug("Exception string is: " + e.toString());
          throw new  EJBException(e.toString());
      }
      
    }
}
