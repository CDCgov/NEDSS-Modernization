package gov.cdc.nedss.proxy.ejb.workupproxyejb.bean;

// Import Statements
import gov.cdc.nedss.act.intervention.dt.InterventionDT;
import gov.cdc.nedss.act.intervention.vo.InterventionVO;
import gov.cdc.nedss.act.observation.helper.ObservationProcessor;
import gov.cdc.nedss.association.dt.ParticipationDT;
import gov.cdc.nedss.controller.ejb.actcontrollerejb.bean.ActController;
import gov.cdc.nedss.controller.ejb.actcontrollerejb.bean.ActControllerHome;
import gov.cdc.nedss.controller.ejb.entitycontrollerejb.bean.EntityController;
import gov.cdc.nedss.controller.ejb.entitycontrollerejb.bean.EntityControllerHome;
import gov.cdc.nedss.entity.material.dt.MaterialDT;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.proxy.ejb.interventionproxyejb.vo.VaccinationSummaryVO;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.dao.RetrieveSummaryVO;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.dao.CTContactSummaryDAO;
import gov.cdc.nedss.proxy.ejb.workupproxyejb.vo.WorkupProxyVO;
import gov.cdc.nedss.proxy.util.EntityProxyHelper;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dao.NbsDocumentDAOImpl;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dt.SummaryDT;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.nbssecurity.ProgramAreaJurisdictionUtil;
import gov.cdc.nedss.util.CDAEventSummaryParser;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;

public class WorkupProxyEJB implements javax.ejb.SessionBean
{
	private static final long serialVersionUID = 1L;
   
    private SessionContext cntx = null;

    //For logging
    static final LogUtils logger = new LogUtils(WorkupProxyEJB.class.getName());

    /**
     * @roseuid 3BFBFEBF000F
     * @J2EE_METHOD  --  ejbCreate
     * Called by the container to create a session bean instance. Its parameters typically
     * contain the information the client uses to customize the bean instance for its use.
     * It requires a matching pair in the bean class and its home interface.
     */
 
  public void ejbCreate() throws EJBException, CreateException
    {
        //entityController = getEntityControllerRemoteInterface();
        //actController = getActControllerRemoteInterface();
    }

    /**
     * @roseuid 3BFBFEBF0019
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
     * @roseuid 3BFBFEBF002D
     * @J2EE_METHOD  --  ejbActivate
     * The activate method is called when the instance is activated from its 'passive' state.
     * The instance should acquire any resource that it has released earlier in the ejbPassivate()
     * method. This method is called with no transaction context.
     */
    public void ejbActivate    () throws EJBException
    {
    }

    /**
     * @roseuid 3BFBFEBF0037
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

    
    public SessionContext getSessionContext() {
		return cntx;
	}



	/**
     1.) With personUID, retrieve PersonVO, InvestigationSummaryVO,
     * ObservationSummaryVO and
     * ObservationDT.  Retrieve only those Investigations and Observations which are
     * linked to the Person through the Participation table.
     * 2.) Retrieve ActRelationshipDT so front-end can determine which Observations
     * are assigned and which are unassigned.
     * @roseuid 3BFC1420034E
     * @J2EE_METHOD  --  getWorkupProxyVO
     */
    @SuppressWarnings("unchecked")
	public WorkupProxyVO getWorkupProxy (Long personUID, NBSSecurityObj securityObj) throws java.rmi.RemoteException, javax.ejb.EJBException, NEDSSAppException, NEDSSSystemException
    {
    	logger.debug("in getWorkupProxy");
        if(! securityObj.getPermission(NBSBOLookup.PATIENT, NBSOperationLookup.VIEWWORKUP, ProgramAreaJurisdictionUtil.ANY_PROGRAM_AREA, ProgramAreaJurisdictionUtil.ANY_JURISDICTION))
        {
            logger.info("getWorkupProxy access denied NBSBOLookup.PATIENT = " + NBSBOLookup.PATIENT + " NBSOperationLookup.VIEWWORKUP = " +  NBSOperationLookup.VIEWWORKUP);
            throw new NEDSSAppException("getWorkupProxy access denied NBSBOLookup.PATIENT = " + NBSBOLookup.PATIENT + " NedssOperationLookup.VIEWWORKUP = " +  NBSOperationLookup.VIEWWORKUP);
        }
        logger.info("getWorkupProxy access permissions NBSBOLookup.PATIENT = " + NBSBOLookup.PATIENT + " NBSOperationLookup.VIEWWORKUP = " +  NBSOperationLookup.VIEWWORKUP);
        WorkupProxyVO wupVO = new WorkupProxyVO();
        RetrieveSummaryVO retrieveSummaryVO = new RetrieveSummaryVO();
        try
        {
            logger.debug("personUID = " + personUID);
            /**
             * Get the PersonVO
             */
            PersonVO perVO = this.getMPR(personUID, securityObj);
            /**
            * Get the collection of PersonUids
            */

            EntityProxyHelper entityProxyHelper = EntityProxyHelper.getInstance();
            Collection<Object>  activeRevisionUids = entityProxyHelper.findActivePatientUidsByParentUid(perVO.getThePersonDT().getPersonUid());
            wupVO.setActiveRevisionUidsColl(activeRevisionUids);
            ArrayList<Object> uidList = new ArrayList<Object> ();
            uidList.add(personUID);
            wupVO.setThePersonVO(perVO);
            logger.debug("getWorkupProxy() got PersonVO");

            /**
             * Get TheInvestigationSummaryVOCollection
             */
            logger.debug("getWorkupProxy() Start investigation!!!");
            //civil00017498 :we commented out this code as we are building where clause inside retrieveInvestgationSummaryVO()
           // String invDataAccessWhereClause = securityObj.getDataAccessWhereClause(NBSBOLookup.INVESTIGATION, NBSOperationLookup.VIEW);
            logger.info("getWorkupProxy access permissions NBSBOLookup.INVESTIGATION = " + NBSBOLookup.INVESTIGATION + " NBSOperationLookup.VIEW = " +  NBSOperationLookup.VIEW);
            Collection<Object>  invSummaryVOs = retrieveSummaryVO.retrieveInvestgationSummaryVO(uidList, securityObj);
            wupVO.setTheInvestigationSummaryVOCollection(invSummaryVOs);

            logger.debug("getWorkupProxy() done investigation");
            Map<Object,Object> docMap = null;
            try{
            	docMap = getDocumentSummaryColl(personUID, securityObj);
            	if(docMap != null && docMap.containsKey("DocSummaryTabColl"))
            		wupVO.setTheUnprocessesDocumentColl((Collection<Object>)docMap.get("DocSummaryTabColl"));
            	if(docMap != null && docMap.containsKey("DocSummaryEventTabColl"))
            		wupVO.setTheDocumentSummaryColl((Collection<Object>)docMap.get("DocSummaryEventTabColl"));
            }catch(Exception e){
            	logger.error("Error in getting the Document Summary for Workup");
            }
            /**
             * Get ObservationSummaryVOCollection
             */
            logger.debug("getWorkupProxy() Get ObservationSummaryVO");
            //civil00017498 :we commented out this code as we are building whereclause inside retrieveLabReportSummaryNEW()
            
           // String lrDataAccessWhereClause = securityObj.getDataAccessWhereClause(NBSBOLookup.OBSERVATIONLABREPORT, NBSOperationLookup.VIEW);
            			HashMap<Object, Object> labReportMap =new HashMap<Object, Object>() ;
						Collection<Object>  labReportSummaryVOCollection  = new ArrayList<Object> ();
						Collection<Object>  newLabReportSummaryVOCollection  = new ArrayList<Object> ();
            ObservationProcessor obsProcessor = new ObservationProcessor();
            String uidType = "PERSON_PARENT_UID";	
            try
            {
			 labReportMap = new HashMap<Object,Object>(obsProcessor.retrieveLabReportSummaryRevisited(uidList, false, securityObj, uidType));
              if(!labReportMap.isEmpty())
              {
            	  if(labReportMap.containsKey("labSummList"))
            	  {
            		  newLabReportSummaryVOCollection  = new ArrayList<Object> ((ArrayList<?> )labReportMap.get("labSummList"));
            	  }
            	  if(labReportMap.containsKey("labEventList"))
            	  {
            		  labReportSummaryVOCollection  = new ArrayList<Object> ((ArrayList<?> )labReportMap.get("labEventList"));
            	  }
              }
            }
            catch(NEDSSSystemException ex)
            {
              logger.error("The lab report security doesn't allow this collection to be returned");
            }
            	if(docMap!=null && docMap.get("LabSummaryCollFromDoc")!=null)
            		labReportSummaryVOCollection.addAll(((Map<Object, Object>)docMap.get("LabSummaryCollFromDoc")).values());
            	
    			wupVO.setTheLabReportSummaryVOCollection(labReportSummaryVOCollection);
				wupVO.setTheUnprocessedLabReportSummaryVOCollection(newLabReportSummaryVOCollection);

				Collection<Object>  morbReportSummaryVOCollection  = new ArrayList<Object> ();
				Collection<Object>  newMorbReportSummaryVOCollection  = new ArrayList<Object> ();
				HashMap<Object, Object> morbReportMap =new HashMap<Object, Object>() ;
				
				try {
					morbReportMap = new HashMap<Object, Object>(obsProcessor.retrieveMorbReportSummaryRevisited(uidList, false, securityObj, uidType));
					if(!morbReportMap.isEmpty())
					{
	            	  if(morbReportMap.containsKey("MorbSummColl"))
	            	  {
	            		  newMorbReportSummaryVOCollection  = new ArrayList<Object> ((ArrayList<?> )morbReportMap.get("MorbSummColl"));
	            	  }
	            	  if(morbReportMap.containsKey("MorbEventColl"))
	            	  {
	            		  morbReportSummaryVOCollection  = new ArrayList<Object> ((ArrayList<?> )morbReportMap.get("MorbEventColl"));
	            	  }
	              }
            }
            catch (NEDSSSystemException ex) {
              logger.error("The morbidity report security doesn't allow this collection to be returned" + ex.getMessage(), ex);
              throw new NEDSSSystemException(ex.getMessage(), ex);
              }
				
				if(docMap!=null && docMap.get("MorbSummaryCollFromDoc")!=null)
					morbReportSummaryVOCollection.addAll(((Map<Object, Object>)docMap.get("MorbSummaryCollFromDoc")).values());

             wupVO.setTheMorbReportSummaryVOCollection(morbReportSummaryVOCollection);
						 wupVO.setTheUnprocessedMorbReportSummaryVOCollection(newMorbReportSummaryVOCollection);



              /**
              * retrieveVaccinationSummaryVOMap
              */
            logger.debug("getWorkupProxy() Get VaccinationMapVO");

                //String vacDataAccessWhereClause = securityObj.getDataAccessWhereClause(NBSBOLookup.INTERVENTIONVACCINERECORD, NBSOperationLookup.VIEW);
                logger.info("getWorkupProxy access permissions NBSBOLookup.INTERVENTION = " + NBSBOLookup.INVESTIGATION + " NBSOperationLookup.VIEW = " +  NBSOperationLookup.VIEW);
                HashMap<Object,Object> vaccinationSummaryVOMap = (HashMap<Object, Object>)retrieveSummaryVO.retrieveVaccinationSummaryListForWorkup(uidList, securityObj);
                wupVO.setTheVaccinationSummaryVOCollection(vaccinationSummaryVOMap);

                /**
              * retrieveTreatmentSummaryVOMap
              */
            logger.debug("getWorkupProxy() Get TreatmentMapVO");

                /** @todo fix the where clause */
                //String vacDataAccessWhereClause = securityObj.getDataAccessWhereClause(NBSBOLookup.TREATMENT, NBSOperationLookup.VIEW);
                logger.info("getWorkupProxy access permissions NBSBOLookup.TREATMENT = " + NBSBOLookup.TREATMENT + " NBSOperationLookup.VIEW = " +  NBSOperationLookup.VIEW);
               HashMap<Object,Object> treatmentSummaryVOMap = (HashMap<Object, Object>)retrieveSummaryVO.retrieveTreatmentSummaryVOForPHCList(uidList, securityObj);
                wupVO.setTheTreatmentSummaryVOCollection(treatmentSummaryVOMap);
                if(wupVO.getTheTreatmentSummaryVOCollection()==null)
                	wupVO.setTheTreatmentSummaryVOCollection(new HashMap<Object, Object>());
                if(docMap!=null && docMap.get("TreatmentSummaryCollFromDoc")!=null)
                	wupVO.getTheTreatmentSummaryVOCollection().putAll((Map<Object, Object>)docMap.get("TreatmentSummaryCollFromDoc"));
                
                
                CTContactSummaryDAO cTContactSummaryDAO  = new CTContactSummaryDAO();
                logger.info("getWorkupProxy access permissions NBSBOLookup.CT_CONTACT = " + NBSBOLookup.CT_CONTACT + " NBSOperationLookup.VIEW = " +  NBSOperationLookup.VIEW);
                Collection<Object> contactCollection= cTContactSummaryDAO.getContactListForFile(personUID, securityObj);
                wupVO.setTheContactSummaryVOColl(contactCollection);

            logger.debug("getWorkupProxy() done investigation");
	    }
        catch(NEDSSSystemException ex)
        {
        	logger.fatal("WorkupProxyEJB.getWorkupProxy: NEDSSSystemException: " + ex.getMessage(), ex);
            throw new NEDSSSystemException(ex.getMessage(),ex);
        }
        catch(Exception e)
	    {
        	logger.fatal("WorkupProxyEJB.getWorkupProxy: Exception: " + e.getMessage(), e);
                throw new NEDSSSystemException(e.getMessage(),e);
        }
        
        
        return wupVO;
    }
    
    private HashMap<Object,Object> getDocumentSummaryColl(Long personUID, NBSSecurityObj nbsecurityObj){
    	Collection<Object>  doucumentSummaryColl = null;
    	Collection<Object>  docSummarytColl=new ArrayList<Object> ();
    	Collection<Object>  docGenSummaryColl=new ArrayList<Object> ();
    	Collection<Object>  cdaDocuments = new ArrayList<Object> ();
    	RetrieveSummaryVO rSummaryVO = new RetrieveSummaryVO();
    	HashMap<Object, Object> docMap = new HashMap<Object, Object>() ;
    	try{
	    	NbsDocumentDAOImpl nbsDocDAO = new NbsDocumentDAOImpl();
	    	doucumentSummaryColl = nbsDocDAO.getDocSummaryVOColl(personUID,nbsecurityObj);
	    	if(doucumentSummaryColl != null && doucumentSummaryColl.size()>0){
	    		Iterator<Object> iter = doucumentSummaryColl.iterator();
	    		while(iter.hasNext()){
	    			SummaryDT docSummaryDT = (SummaryDT)iter.next();
	    			docSummaryDT.setAssociationMap(rSummaryVO.getAssociatedInvList(docSummaryDT.getNbsDocumentUid(), nbsecurityObj, NEDSSConstants.ACT_CLASS_CD_FOR_DOC));
	    			if(docSummaryDT.getRecordStatusCd() != null && (docSummaryDT.getRecordStatusCd().equals(NEDSSConstants.OBS_UNPROCESSED)
	    					|| docSummaryDT.getRecordStatusCd().equals(NEDSSConstants.OBS_UNPROCESSED ))){
	    				docSummarytColl.add(docSummaryDT);
	    			}
	    			if(docSummaryDT.getRecordStatusCd() != null && !(docSummaryDT.getRecordStatusCd().equals(NEDSSConstants.RECORD_STATUS_LOGICAL_DELETE))){
	    				docGenSummaryColl.add(docSummaryDT);
	    			}
	    			if(docSummaryDT.getDocTypeCd()!=null && docSummaryDT.getDocTypeCd().equals(NEDSSConstants.CDA_PHDC_TYPE)){
	    				cdaDocuments.add(docSummaryDT);
	    			}
	    		}
	            	CDAEventSummaryParser esp = new CDAEventSummaryParser();
	            	docMap.put("DocSummaryTabColl", docSummarytColl);
	            	docMap.put("DocSummaryEventTabColl", docGenSummaryColl);
	            	docMap.put("TreatmentSummaryCollFromDoc", esp.getTreatmentMapByUid(cdaDocuments,nbsecurityObj));
	            	docMap.put("LabSummaryCollFromDoc", esp.getLabMapByUid(cdaDocuments,nbsecurityObj));
	            	docMap.put("MorbSummaryCollFromDoc", esp.getMorbMapByUid(cdaDocuments,nbsecurityObj));
	    	}
    	}catch(Exception e){
    		logger.fatal("WorkupProxyEJB.getDocumentSummaryColl: " + e.getMessage(), e);
    		throw new NEDSSSystemException(e.getMessage(),e);
    	}  
    	return docMap;
    }
 
    /**
     * @roseuid 3BFC2C37023E
     * @J2EE_METHOD  --  WorkupProxyEJB
     */
    public WorkupProxyEJB    ()
    {

    }

    /**
     * @roseuid 3BFC2C370252
     * @J2EE_METHOD  --  setSessionContext
     * Set the associated session context. The container calls this method after the instance
     * creation. The enterprise Bean instance should store the reference to the context
     * object in an instance variable. This method is called with no transaction context.
     */
    public void setSessionContext (SessionContext sessioncontext) throws EJBException,RemoteException
    {
        this.cntx = sessioncontext;
    }

    private PersonVO getPersonVO(Long personUID, NBSSecurityObj securityObj)
    {
        EntityController entityController = null;
        PersonVO pvo = null;
        try
        {
           
                entityController = this.getEntityControllerRemoteInterface();
           
            pvo = entityController.getPerson(personUID, securityObj);
        }
        catch(Exception ex)
        {
        	logger.fatal("WorkupProxyEJB.getPersonVO: " + ex.getMessage(), ex);
        	throw new NEDSSSystemException(ex.getMessage(),ex);
        }
        return pvo;
    }

    private PersonVO getMPR(Long personUID, NBSSecurityObj securityObj)
    {
        EntityController entityController = null;
        PersonVO pvo = null;
        try
        {
            entityController = this.getEntityControllerRemoteInterface();
            pvo = entityController.getMPR(personUID, securityObj);
        }
        catch(Exception ex)
        {
        	logger.fatal("WorkupProxyEJB.getMPR: " + ex.getMessage(), ex);
        	throw new NEDSSSystemException(ex.getMessage(),ex);
        }
                logger.debug("before end of getMPR in WorkupProxy");
        return pvo;
    }


    private Collection<Object>  getInterventionSummaryList(Collection<Object> listOfUid, NBSSecurityObj securityObj)
    {
        Collection<Object>  vacSummaryVOs = new ArrayList<Object> ();
        try
        {
              EntityController entityController = null;
              ActController actController = null;
             Iterator<Object>  intIter = listOfUid.iterator();
              while(intIter.hasNext())
              {
                  Long intActUid = (Long)intIter.next();
               
                      actController = getActControllerRemoteInterface();
               
                  InterventionVO intVO = actController.getIntervention(intActUid, securityObj);
                  InterventionDT intDT = intVO.getTheInterventionDT();
                  VaccinationSummaryVO vacSumVO = new VaccinationSummaryVO();
                  vacSumVO.setActivityFromTime(intDT.getActivityFromTime());
                  vacSumVO.setInterventionUid(intDT.getInterventionUid());
                  vacSumVO.setLocalId(intDT.getLocalId());

                  //Gets the material entities associated with this particular intervention
                  Collection<Object>  intPartDTs = intVO.getTheParticipationDTCollection();
                 Iterator<Object>  intPartIter = intPartDTs.iterator();
                  while(intPartIter.hasNext())
                  {
                      ParticipationDT dt = (ParticipationDT)intPartIter.next();
                      if(dt.getTypeCd()==NEDSSConstants.VACCINATION_ADMINISTERED_TYPE_CODE)
                      {
                          Long entityUid = dt.getSubjectEntityUid();
                      
                              entityController = getEntityControllerRemoteInterface();
                        
                          MaterialDT materialDT= entityController.getMaterialInfo(entityUid, securityObj);
                          String vaccineAdministered = materialDT.getNm();
                          vacSumVO.setVaccineAdministered(vaccineAdministered);
                      }
                  }
                  vacSummaryVOs.add(vacSumVO);
              }
        }
        catch(Exception ex)
        {
        	logger.fatal("WorkupProxyEJB.getInterventionSummaryList: " + ex.getMessage(), ex);
        	throw new NEDSSSystemException(ex.getMessage(),ex);
        }
        return vacSummaryVOs;
    }

    private Collection<Object>  getUidList(Collection<Object> participationDTColl, String uidType)
    {
        Collection<Object>  uidColl = new ArrayList<Object> ();

        try
        {
            Iterator<Object>  partIter = participationDTColl.iterator();
			 logger.debug(" Before Has part DT ");
             while(partIter.hasNext())
             {
                logger.debug("Has part DT");
                ParticipationDT partDT = (ParticipationDT)partIter.next();
                logger.debug(" Type Cd is: " + partDT.getTypeCd() + "; Act uid = " + partDT.getActUid());
                if(partDT.getTypeCd().equalsIgnoreCase(uidType))
                {
                    uidColl.add(partDT.getActUid());
                }
            }
        }
        catch(Exception ex)
        {
        	logger.fatal("WorkupProxyEJB.getUidList: " + ex.getMessage(), ex);
        	throw new NEDSSSystemException(ex.getMessage(),ex);
        }
        return uidColl;
    }

	private EntityController getEntityControllerRemoteInterface()throws EJBException{
	      EntityController entityController = null;
        	try{
            logger.debug("YOU ARE IN THE getRemoteInterface() method");
        
            NedssUtils nu = new NedssUtils();
            Object obj = nu.lookupBean(JNDINames.EntityControllerEJB);
            logger.debug("YOU JUST DID A JNDI LOOKUP = " + obj.toString());
            EntityControllerHome entityControllerHome =	(EntityControllerHome)javax.rmi.PortableRemoteObject.narrow(obj,EntityControllerHome.class);
            logger.debug("YOU JUST INSTANTIATED THE HOME INTERFACE");
            entityController = entityControllerHome.create();
            logger.debug("YOU NOW HAVE A REMOTE INTERFACE");
		}
		catch(Exception e)
		{
			logger.fatal("WorkupProxyEJB.getEntityControllerRemoteInterface: " + e.getMessage(), e);
			throw new EJBException(e.getMessage(),e);
		}
			return entityController;
	}

	private ActController getActControllerRemoteInterface()throws EJBException{
	ActController actController = null;
        try{
            logger.debug("YOU ARE IN THE getRemoteInterface() method");
        
            NedssUtils nu = new NedssUtils();
            Object obj = nu.lookupBean(JNDINames.ActControllerEJB);
            logger.debug("YOU JUST DID A JNDI LOOKUP");
            ActControllerHome actControllerHome =(ActControllerHome)javax.rmi.PortableRemoteObject.narrow(obj,ActControllerHome.class);
            logger.debug("YOU JUST INSTANTIATED THE HOME INTERFACE");
            actController = actControllerHome.create();
            logger.debug("YOU NOW HAVE A REMOTE INTERFACE");
		}
		catch(Exception e)
		{
            logger.fatal("WorkupProxyEJB.getActControllerRemoteInterface: " + e.getMessage(), e);
            throw new EJBException(e.getMessage(),e);
		}
			return actController;
	}

	


	public ArrayList<Object> getCoinfectionInvList(Long mprUid,
			String conditionCd, NBSSecurityObj nbsSecurityObj)
			throws java.rmi.RemoteException, javax.ejb.EJBException,
			NEDSSAppException, NEDSSSystemException

	{
		ArrayList<Object> returnList = new ArrayList<Object>();
		try {
			RetrieveSummaryVO rsVO = new RetrieveSummaryVO();
			returnList = rsVO.getCoinfectionInvList(mprUid, conditionCd);
		} catch (NEDSSSystemException nse) {
			logger.fatal("WorkupProxyEJB.getCoinfectionInvList: NEDSSSystemException: " + nse.getMessage(), nse);
			throw new NEDSSSystemException(nse.getMessage(),nse);
		} catch (Exception e) {
			logger.fatal("WorkupProxyEJB.getCoinfectionInvList: Exception: " + e.getMessage(), e);
			throw new EJBException(e.getMessage(),e);
		}
		return returnList;
	}
	
	public ArrayList<Object> getSpecificCoinfectionInvList(Long publicHealthCaseUid, NBSSecurityObj nbsSecurityObj)
			throws java.rmi.RemoteException, javax.ejb.EJBException,
			NEDSSAppException, NEDSSSystemException
	{
		ArrayList<Object> returnList = new ArrayList<Object>();
		try {
			RetrieveSummaryVO rsVO = new RetrieveSummaryVO();
			returnList = rsVO.getSpecificCoinfectionInvList(publicHealthCaseUid);
		} catch (NEDSSSystemException nse) {
			logger.fatal("WorkupProxyEJB.getSpecificCoinfectionInvList: NEDSSSystemException: " + nse.getMessage(), nse);
			throw new NEDSSSystemException(nse.getMessage(),nse);
		} catch (Exception e) {
			logger.fatal("WorkupProxyEJB.getSpecificCoinfectionInvList: Exception: " + e.getMessage(), e);
			throw new EJBException(e.getMessage(),e);
		}
		return returnList;
	}
	
	public ArrayList<Object> getSpecificCoinfectionInvListPHC(Long publicHealthCaseUid, NBSSecurityObj nbsSecurityObj)
			throws java.rmi.RemoteException, javax.ejb.EJBException,
			NEDSSAppException, NEDSSSystemException
	{
		ArrayList<Object> returnList = new ArrayList<Object>();
		try {
			RetrieveSummaryVO rsVO = new RetrieveSummaryVO();
			returnList = rsVO.getSpecificCoinfectionInvListPHC(publicHealthCaseUid);
		} catch (NEDSSSystemException nse) {
			logger.fatal("WorkupProxyEJB.getSpecificCoinfectionInvListPHC: NEDSSSystemException: " + nse.getMessage(), nse);
			throw new NEDSSSystemException(nse.getMessage(),nse);
		} catch (Exception e) {
			logger.fatal("WorkupProxyEJB.getSpecificCoinfectionInvListPHC: Exception: " + e.getMessage(), e);
			throw new EJBException(e.getMessage(),e);
		}
		return returnList;
	}
	
	public Integer getSpecificCoinfectionInvListCount(Long publicHealthCaseUid, NBSSecurityObj nbsSecurityObj)
			throws java.rmi.RemoteException, javax.ejb.EJBException,
			NEDSSAppException, NEDSSSystemException
	{
		Integer count = null;
		try {
			RetrieveSummaryVO rsVO = new RetrieveSummaryVO();
			count = rsVO.getSpecificCoinfectionInvListCount(publicHealthCaseUid);
		} catch (NEDSSSystemException nse) {
			logger.fatal("WorkupProxyEJB.getSpecificCoinfectionInvListCount: NEDSSSystemException: " + nse.getMessage(), nse);
			throw new NEDSSSystemException(nse.getMessage(),nse);
		} catch (Exception e) {
			logger.fatal("WorkupProxyEJB.getSpecificCoinfectionInvListCount: Exception: " + e.getMessage(), e);
			throw new EJBException(e.getMessage(),e);
		}
		return count;
	}
	
	public ArrayList<Object> getOpenInvList(Long mprUid,
			String conditionCd, NBSSecurityObj nbsSecurityObj)
			throws java.rmi.RemoteException, javax.ejb.EJBException,
			NEDSSAppException, NEDSSSystemException

	{
		ArrayList<Object> returnList = new ArrayList<Object>();
		try {
			RetrieveSummaryVO rsVO = new RetrieveSummaryVO();
			returnList = rsVO.getOpenInvList(mprUid, conditionCd);
		} catch (NEDSSSystemException nse) {
			logger.fatal("WorkupProxyEJB.getOpenInvList: NEDSSSystemException: " + nse.getMessage(), nse);
			throw new NEDSSSystemException(nse.getMessage(),nse);
		} catch (Exception e) {
			logger.fatal("WorkupProxyEJB.getOpenInvList: Exception: " + e.getMessage(), e);
			throw new EJBException(e.getMessage(),e);
		}
		return returnList;
	}
	/**
    1.) With personUID, retrieve PersonVO, InvestigationSummaryVO,
    *  Retrieve only those Investigations which are
    * linked to the Person through the Participation table.
    *
    */
   public  Collection<Object> getPersonInvestigationSummary (Long personUID, NBSSecurityObj securityObj) throws java.rmi.RemoteException, javax.ejb.EJBException, NEDSSAppException, NEDSSSystemException
   {

		logger.debug("in getPersonInvestigationSummary(" + personUID + ")");

        RetrieveSummaryVO retrieveSummaryVO = new RetrieveSummaryVO();
        Collection<Object>  invSummaryVOs = new ArrayList<Object> ();
       try
       {

           ArrayList<Object> uidList = new ArrayList<Object> ();
           uidList.add(personUID);

           /**
            * Get TheInvestigationSummaryVOCollection
            */
           logger.info("getPersonInvestigationSummary() access permissions NBSBOLookup.INVESTIGATION = " + NBSBOLookup.INVESTIGATION + " NBSOperationLookup.VIEW = " +  NBSOperationLookup.VIEW);
           invSummaryVOs = retrieveSummaryVO.retrieveInvestgationSummaryVO(uidList, securityObj);
       } catch(NEDSSSystemException ex)
           {
    	   logger.fatal("WorkupProxyEJB.getPersonInvestigationSummary: NEDSSSystemException: " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.getMessage(),ex);
       }  catch(Exception e)
   	       {
    	   logger.fatal("WorkupProxyEJB.getPersonInvestigationSummary: Exception: " + e.getMessage(), e);
			throw new EJBException(e.getMessage(),e);
       }           
       return invSummaryVOs;
   }	
}