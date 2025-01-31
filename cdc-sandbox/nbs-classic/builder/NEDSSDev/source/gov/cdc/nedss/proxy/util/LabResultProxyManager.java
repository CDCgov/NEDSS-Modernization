package gov.cdc.nedss.proxy.util;

import java.rmi.RemoteException;
import java.util.*;

import javax.ejb.EJBException;

import gov.cdc.nedss.act.observation.vo.ObservationVO;
import gov.cdc.nedss.act.observation.dt.EDXDocumentDT;
import gov.cdc.nedss.act.observation.dt.ObservationDT;
import gov.cdc.nedss.act.observation.ejb.dao.EDXDocumentDAOImpl;
import gov.cdc.nedss.act.observation.ejb.dao.ObservationRootDAOImpl;
import gov.cdc.nedss.act.observation.helper.ObservationProcessor;
import gov.cdc.nedss.act.intervention.ejb.dao.InterventionRootDAOImpl;
import gov.cdc.nedss.elr.ejb.msginprocessor.helper.ELRConstants;
import gov.cdc.nedss.entity.person.ejb.dao.PersonRootDAOImpl;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.entity.entityid.dt.EntityIdDT;
import gov.cdc.nedss.entity.material.ejb.dao.MaterialRootDAOImpl;
import gov.cdc.nedss.entity.material.vo.MaterialVO;
import gov.cdc.nedss.entity.organization.ejb.dao.OrganizationRootDAOImpl;
import gov.cdc.nedss.entity.organization.vo.OrganizationVO;
import gov.cdc.nedss.page.ejb.pageproxyejb.dao.AnswerRootDAOImpl;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.page.PageVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.LabResultProxyVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.MorbidityProxyVO;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.util.AbstractVO;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.association.dt.ActRelationshipDT;
import gov.cdc.nedss.association.dt.RoleDT;
import gov.cdc.nedss.association.dt.ParticipationDT;
import gov.cdc.nedss.nnd.helper.NNDMessageSenderHelper;
import gov.cdc.nedss.ldf.helper.LDFHelper;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.webapp.nbs.action.util.CommonAction;
import gov.cdc.nedss.association.dao.ActRelationshipDAOImpl;
import gov.cdc.nedss.association.dao.RoleDAOImpl;
import gov.cdc.nedss.controller.ejb.entitycontrollerejb.bean.EntityController;
import gov.cdc.nedss.controller.ejb.entitycontrollerejb.bean.EntityControllerHome;
import gov.cdc.nedss.systemservice.ejb.nbssecurityejb.helper.NBSAuthHelper;

/**
 * <p>Title: Load LabResultProxyVO</p>
 * <p>Description: This object was created to manage the loading of the LabResultProxyVO</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Computer Science Corporation</p>
 * @author Joe Daughtery
 *
 */

public class LabResultProxyManager {
  LogUtils logger = new LogUtils( (LabResultProxyManager.class).getName());
  ObservationRootDAOImpl obsRootDAO = new ObservationRootDAOImpl();
  PersonRootDAOImpl personRootDAO = new PersonRootDAOImpl();
  OrganizationRootDAOImpl organizationRootDAO = new OrganizationRootDAOImpl();
  MaterialRootDAOImpl materialRootDAO = new MaterialRootDAOImpl();

  private final int RETRIEVED_PERSONS_FOR_PROXY = 0;
  private final int RETRIEVED_ORGANIZATIONS_FOR_PROXY = 1;
  private final int RETRIEVED_MATERIALS_FOR_PROXY = 2;
  private final int RETRIEVED_PATIENT_ROLES = 3;

  private final int RETRIEVED_INTERVENTIONS_FOR_PROXY = 0;
  private final int RETRIEVED_OBSERVATIONS_FOR_PROXY = 1;
  private final int RETRIEVED_LABS_FOR_RT = 2;

  private HashMap<Object,Object> entityMap = new HashMap<Object,Object>();

  /**
   * Default construtor
   */
  public LabResultProxyManager() {

  }
  

  /**
   * Loads the labResultProxyVO representing the observationId passed in.
   * @param observationId
   * @param nbsSecurityObj
   * @return
   * @throws Exception
   */
  public LabResultProxyVO getLabResultProxyVO(Long observationId,  boolean isELR, NBSSecurityObj nbsSecurityObj)throws Exception{
    entityMap.clear();
    LabResultProxyVO lrProxyVO =  new LabResultProxyVO();
    checkMethodArgs(observationId, nbsSecurityObj);

     /**Load ordered test*/
     ObservationVO orderedTest = (ObservationVO)getActVO(NEDSSConstants.OBSERVATION_CLASS_CODE, observationId, nbsSecurityObj);

     /**Check permission*/
     checkPermissionToGetProxy(orderedTest, nbsSecurityObj);

     Collection<Object>  partColl = orderedTest.getTheParticipationDTCollection();
     if (partColl != null && partColl.size() > 0)
     {
       List<Object> allEntity = retrieveEntityForProxyVO(partColl, nbsSecurityObj);
       if (allEntity != null && allEntity.size() > 0)
       {
         lrProxyVO.setThePersonVOCollection( (Collection<Object>) allEntity.get(this.
             RETRIEVED_PERSONS_FOR_PROXY));
         lrProxyVO.setTheOrganizationVOCollection( (Collection<Object>) allEntity.get(this.
             RETRIEVED_ORGANIZATIONS_FOR_PROXY));
         lrProxyVO.setTheMaterialVOCollection( (Collection<Object>) allEntity.get(this.
             RETRIEVED_MATERIALS_FOR_PROXY));

         Object obj = allEntity.get(this.RETRIEVED_PATIENT_ROLES);
         Collection<Object>  coll = null;
         if(obj == null)
           coll = new ArrayList<Object> ();//do not want to place a null object in  for role collection
         else
           coll = (Collection<Object>)obj;

         lrProxyVO.setTheRoleDTCollection(coll);
       }
     }

     Collection<Object>  actRelColl = orderedTest.getTheActRelationshipDTCollection();

     if (actRelColl != null && actRelColl.size() > 0)
     {
       List<Object> allAct = retrieveActForProxyVO(actRelColl, nbsSecurityObj);
       if (allAct != null && allAct.size() > 0)
       {
         //Set intervention collection
         lrProxyVO.setTheInterventionVOCollection( (Collection<Object>) allAct.get(this.
             RETRIEVED_INTERVENTIONS_FOR_PROXY));

         //Set observation collection
         Collection<ObservationVO>  obsColl = (Collection<ObservationVO>) allAct.get(this.
             RETRIEVED_OBSERVATIONS_FOR_PROXY);
         if (obsColl == null)
         {
           obsColl = new ArrayList<ObservationVO> ();
         }

         //BB - civil0012298 - Retrieve User Name to b displayed instead of ID!
         if(!isELR) {
	         NBSAuthHelper helper = new NBSAuthHelper();
	         orderedTest.getTheObservationDT().setAddUserName(helper.getUserName(orderedTest.getTheObservationDT().getAddUserId()));
	         orderedTest.getTheObservationDT().setLastChgUserName(helper.getUserName(orderedTest.getTheObservationDT().getLastChgUserId()));
         }

         obsColl.add(orderedTest);
         lrProxyVO.setTheObservationVOCollection(obsColl);

         //Adds the performing lab(if any) to the organization cellection
         Collection<Object>  labColl = (Collection<Object>) allAct.get(this.
             RETRIEVED_LABS_FOR_RT);
         if (labColl != null && labColl.size() > 0)
         {
           lrProxyVO.getTheOrganizationVOCollection().addAll(labColl);
         }
       }
     }
     
		if (!isELR) {
			try {
				boolean exists = NNDMessageSenderHelper.getInstance().checkForExistingNotifications(lrProxyVO)
						.booleanValue();
				lrProxyVO.setAssociatedNotificationInd(exists);
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				ActRelationshipDAOImpl actRelationshipDAOImpl = new ActRelationshipDAOImpl();
				Collection<Object> col = actRelationshipDAOImpl.loadSource(observationId, NEDSSConstants.LAB_REPORT);
				if (col != null && col.size() > 0)
					lrProxyVO.setAssociatedInvInd(true);
			} catch (Exception e) {
				e.printStackTrace();
			}

			// get EDX Document data
			try {
				EDXDocumentDAOImpl eDXDocumentDAO = new EDXDocumentDAOImpl();
				Collection<Object> documentList = eDXDocumentDAO.selectEDXDocumentCollection(observationId);

				if (documentList != null) {
					lrProxyVO.seteDXDocumentCollection(documentList);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			// get the list of conditions associated with this Lab
			try {
				ArrayList<String> conditionList = deriveTheConditionCodeList(lrProxyVO, orderedTest, nbsSecurityObj);
				if (conditionList != null && !conditionList.isEmpty()) {
					lrProxyVO.setTheConditionsList(conditionList);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
     
		try {
			AnswerRootDAOImpl answerRootDAO = new AnswerRootDAOImpl();
			PageVO pageVO = answerRootDAO.get(observationId);

			lrProxyVO.setPageVO(pageVO);
		} catch (Exception e) {
			logger.error("Exception while getting data from NBS Answer for Lab");
			e.printStackTrace();
		}

     return lrProxyVO;





  }//end of getLabResultProxyVO(....)

  /**
   * Retrieves the Act objects associated with the proxy
   * @param actRelColl
   * @param securityObj
   * @return
   * @throws Exception
   */
  private List<Object> retrieveActForProxyVO(Collection<Object> actRelColl,
                                     NBSSecurityObj securityObj) throws Exception
  {
    List<Object> allActHolder = new ArrayList<Object> ();

    //Retrieve associated interventions
    allActHolder.add(this.RETRIEVED_INTERVENTIONS_FOR_PROXY,
                     retrieveInterventionVOsForProxyVO(actRelColl, securityObj));

    //Retrieve associated observations and performing labs of any resulted tests
    List<Object> obs_org = (List<Object>) retrieveObservationVOsForProxyVO(actRelColl,
        securityObj);
    allActHolder.add(this.RETRIEVED_OBSERVATIONS_FOR_PROXY, obs_org.get(0));
    allActHolder.add(this.RETRIEVED_LABS_FOR_RT, obs_org.get(1));

    return allActHolder;
  }

  /**
   * Loads the observations associated with the labresultproxy vo
   * @param actRelColl
   * @param securityObj
   * @return
   * @throws Exception
   */
  private Collection<Object>  retrieveObservationVOsForProxyVO(Collection<Object> actRelColl,
      NBSSecurityObj securityObj) throws Exception
  {
    List<Object> obs_org = new ArrayList<Object> ();
    Collection<Object>  theObservationVOCollection  = new ArrayList<Object> ();
    Collection<Object>  performingLabColl = new ArrayList<Object> ();
    NBSAuthHelper authHelper = new NBSAuthHelper();

    for (Iterator<Object> it = actRelColl.iterator(); it.hasNext(); )
    {
      ActRelationshipDT actRelDT = (ActRelationshipDT) it.next();

      if (actRelDT == null)
      {
        continue;
      }

      String typeCd = actRelDT.getTypeCd();
      String sourceClassCd = actRelDT.getSourceClassCd();
      String targetClassCd = actRelDT.getTargetClassCd();
      String recordStatusCd = actRelDT.getRecordStatusCd();

      //If observation...
      if (sourceClassCd != null &&
          sourceClassCd.equalsIgnoreCase(NEDSSConstants.OBSERVATION_CLASS_CODE)
          && targetClassCd != null &&
          targetClassCd.equalsIgnoreCase(NEDSSConstants.OBSERVATION_CLASS_CODE)
          && recordStatusCd != null &&
          recordStatusCd.equalsIgnoreCase(NEDSSConstants.ACTIVE))
      {
        Long observationUid = actRelDT.getSourceActUid();

        //If a processing decision observation
        if(typeCd!=null && typeCd.equals(NEDSSConstants.ACT_TYPE_PROCESSING_DECISION)){
        	ObservationVO processingDecObservationVO = (ObservationVO)getActVO(NEDSSConstants.OBSERVATION_CLASS_CODE,
                    observationUid,
                    securityObj);
        	theObservationVOCollection.add(processingDecObservationVO);
        }
      //If a Comments observation
        if (typeCd != null && typeCd.equalsIgnoreCase("APND"))
        {
          ObservationVO ordTestCommentVO = (ObservationVO)getActVO(NEDSSConstants.OBSERVATION_CLASS_CODE,
                                                  observationUid,
                                                  securityObj);

          theObservationVOCollection.add(ordTestCommentVO);
          Collection<Object>  arColl = ordTestCommentVO.getTheActRelationshipDTCollection();
          if(arColl != null){
           Iterator<Object>  arCollIter = arColl.iterator();
            while(arCollIter.hasNext()){
              ActRelationshipDT ordTestDT = (ActRelationshipDT)arCollIter.next();
              if(ordTestDT.getTypeCd().equals("COMP")){
                //add the resulted test to the collection
                ObservationVO resTestVO = (ObservationVO) getActVO(NEDSSConstants.OBSERVATION_CLASS_CODE,
                                                  ordTestDT.getSourceActUid(),
                                                  securityObj);

                //BB - civil0012298 - Retrieve User Name to be displayed instead of ID
                resTestVO.getTheObservationDT().setAddUserName(authHelper.getUserName(resTestVO.getTheObservationDT().getAddUserId()));

                theObservationVOCollection.add(resTestVO);

              }
            }

          }
        }
        //If a Resulted Test observation
        else if (typeCd != null &&
                 typeCd.equalsIgnoreCase(NEDSSConstants.ACT108_TYP_CD))
        {
          ObservationVO rtObservationVO = (ObservationVO) getActVO(
              NEDSSConstants.OBSERVATION_CLASS_CODE,
              observationUid,
              securityObj);
          if (rtObservationVO == null)
          {
            continue;
          }
          theObservationVOCollection.add(rtObservationVO); //The Resulted Test itself
          //Retrieve the RT's lab
          OrganizationVO rtPerformingLab = retrievePerformingLab(
              rtObservationVO.getTheParticipationDTCollection(), securityObj);
          if (rtPerformingLab != null)
          {
            performingLabColl.add(rtPerformingLab);
          }


          //Retrieves all reflex observations, including each ordered and its resulted
          Collection<Object>  reflexObsColl = retrieveReflexObservations(rtObservationVO.
              getTheActRelationshipDTCollection(), securityObj);
          if (reflexObsColl == null || reflexObsColl.size() <= 0)
          {
            continue;
          }
          theObservationVOCollection.addAll(reflexObsColl);
        }
      }
    }

    obs_org.add(0, theObservationVOCollection);
    obs_org.add(1, performingLabColl);
    return obs_org;
  }

  /**
   * Retrieves the Reflex Observations
   * @param actRelColl
   * @param securityObj
   * @return
   * @throws Exception
   */
  private Collection<Object>  retrieveReflexObservations(Collection<Object> actRelColl,
                                                NBSSecurityObj securityObj) throws Exception
  {
    Collection<Object>  reflexObsVOCollection  = null;

    for (Iterator<Object> it = actRelColl.iterator(); it.hasNext(); )
    {
      ActRelationshipDT actRelDT = (ActRelationshipDT) it.next();

      if (actRelDT == null)
      {
        continue;
      }

      String typeCd = actRelDT.getTypeCd();
      String sourceClassCd = actRelDT.getSourceClassCd();
      String targetClassCd = actRelDT.getTargetClassCd();
      String recordStatusCd = actRelDT.getRecordStatusCd();

      //If reflex ordered test observation...
      if (typeCd != null &&
          typeCd.equalsIgnoreCase(NEDSSConstants.ACT109_TYP_CD)
          && sourceClassCd != null &&
          sourceClassCd.equalsIgnoreCase(NEDSSConstants.OBSERVATION_CLASS_CODE)
          && targetClassCd != null &&
          targetClassCd.equalsIgnoreCase(NEDSSConstants.OBSERVATION_CLASS_CODE)
          && recordStatusCd != null &&
          recordStatusCd.equalsIgnoreCase(NEDSSConstants.ACTIVE))
      {
        Long observationUid = actRelDT.getSourceActUid();

        ObservationVO reflexObs = (ObservationVO) getActVO(NEDSSConstants.
            OBSERVATION_CLASS_CODE,
            observationUid,
            securityObj);

        if (reflexObs == null)
        {
          continue;
        }
        else
        {
          if(reflexObsVOCollection  == null) reflexObsVOCollection  = new ArrayList<Object> ();
          reflexObsVOCollection.add(reflexObs);
        }

        //Retrieves its associated reflex resulted tests
        Collection<Object>  reflexRTs = retrieveReflexRTs(reflexObs.
            getTheActRelationshipDTCollection(), securityObj);
        if (reflexRTs == null || reflexRTs.size() < 0)
        {
          continue;
        }
        reflexObsVOCollection.addAll(reflexRTs);
      }
    }
    return reflexObsVOCollection;
  }
  /**
   * Retrieves the Reflex Result Test
   * @param actRelColl
   * @param securityObj
   * @return
   * @throws Exception
   */
  private Collection<Object>  retrieveReflexRTs(Collection<Object> actRelColl,
                                       NBSSecurityObj securityObj) throws Exception
  {
    Collection<Object>  reflexRTCollection  = null;

    for (Iterator<Object> it = actRelColl.iterator(); it.hasNext(); )
    {
      ActRelationshipDT actRelDT = (ActRelationshipDT) it.next();

      if (actRelDT == null)
      {
        continue;
      }

      String typeCd = actRelDT.getTypeCd();
      String sourceClassCd = actRelDT.getSourceClassCd();
      String targetClassCd = actRelDT.getTargetClassCd();
      String recordStatusCd = actRelDT.getRecordStatusCd();

      //If reflex resulted test observation...
      if (typeCd != null &&
          typeCd.equalsIgnoreCase(NEDSSConstants.ACT110_TYP_CD)
          && sourceClassCd != null &&
          sourceClassCd.equalsIgnoreCase(NEDSSConstants.OBSERVATION_CLASS_CODE)
          && targetClassCd != null &&
          targetClassCd.equalsIgnoreCase(NEDSSConstants.OBSERVATION_CLASS_CODE)
          && recordStatusCd != null &&
          recordStatusCd.equalsIgnoreCase(NEDSSConstants.ACTIVE))
      {
        Long observationUid = actRelDT.getSourceActUid();

        ObservationVO reflexObs = (ObservationVO) getActVO(NEDSSConstants.
            OBSERVATION_CLASS_CODE,
            observationUid,
            securityObj);

        if (reflexObs == null)
        {
          continue;
        }
        if(reflexRTCollection  == null) reflexRTCollection  = new ArrayList<Object> ();
        reflexRTCollection.add(reflexObs);
      }
    }
    return reflexRTCollection;
  }


  /**
   * Loads the performing lab.
   * @param partColl
   * @param securityObj
   * @return
   * @throws Exception
   */
  private OrganizationVO retrievePerformingLab(Collection<Object> partColl,
                                               NBSSecurityObj securityObj) throws Exception
  {
    OrganizationVO lab = null;

    for (Iterator<Object> it = partColl.iterator(); it.hasNext(); )
    {
      ParticipationDT partDT = (ParticipationDT) it.next();

      if (partDT == null)
      {
        continue;
      }

      String typeCd = partDT.getTypeCd();
      String subjectClassCd = partDT.getSubjectClassCd();
      String actClassCd = partDT.getActClassCd();
      String recordStatusCd = partDT.getRecordStatusCd();

      //If performing lab...
      if (typeCd != null && typeCd.equals(NEDSSConstants.PAR122_TYP_CD)
          && subjectClassCd != null &&
          subjectClassCd.equalsIgnoreCase(NEDSSConstants.PAR122_SUB_CD)
          && actClassCd != null &&
          actClassCd.equals(NEDSSConstants.OBSERVATION_CLASS_CODE)
          && recordStatusCd != null &&
          recordStatusCd.equalsIgnoreCase(NEDSSConstants.ACTIVE))
      {
        Long organizationUid = partDT.getSubjectEntityUid();

        lab = (OrganizationVO) getEntityVO(NEDSSConstants.ORGANIZATION,
                                           organizationUid, partDT.getActUid(),
                                           securityObj);
        break; //only one lab for each RT
      }
    }
    return lab;
  }



  /**
   * Loads the Interventions
   * @param actRelColl
   * @param securityObj
   * @return
   * @throws Exception
   */
  private Collection<Object>  retrieveInterventionVOsForProxyVO(Collection<Object> actRelColl,
        NBSSecurityObj securityObj) throws Exception
    {
      Collection<Object>  theInterventionVOCollection  = null;

      for (Iterator<Object> it = actRelColl.iterator(); it.hasNext(); )
      {
        ActRelationshipDT actRelDT = (ActRelationshipDT) it.next();

        if (actRelDT == null)
        {
          continue;
        }

        String sourceClassCd = actRelDT.getSourceClassCd();
        String targetClassCd = actRelDT.getTargetClassCd();
        String recordStatusCd = actRelDT.getRecordStatusCd();

        //If intervention...
        if (sourceClassCd != null &&
            sourceClassCd.equalsIgnoreCase(NEDSSConstants.INTERVENTION_CLASS_CODE)
            && targetClassCd != null &&
            targetClassCd.equalsIgnoreCase(NEDSSConstants.OBSERVATION_CLASS_CODE)
            && recordStatusCd != null &&
            recordStatusCd.equalsIgnoreCase(NEDSSConstants.ACTIVE))
        {
          Long interventionUid = actRelDT.getSourceActUid();
          if (theInterventionVOCollection  == null)
          {
            theInterventionVOCollection  = new ArrayList<Object> ();
          }
          theInterventionVOCollection.add(getActVO(NEDSSConstants.
                                                   INTERVENTION_CLASS_CODE,
                                                   interventionUid,
                                                   securityObj));
        }
      }
      return theInterventionVOCollection;
    }

    /**
     * Loads an Act object vo
     * @param actType
     * @param anUid
     * @param securityObj
     * @return
     * @throws java.lang.Exception
     */
    private Object getActVO(String actType, Long anUid,
                          NBSSecurityObj securityObj)
  throws java.lang.Exception
  {

    Object obj = null;

    try
    {

      if (anUid != null)
      {

        //ActController actControllerRef = getActControllerRemoteInterface();

        if (actType.equalsIgnoreCase(
            NEDSSConstants.INTERVENTION_CLASS_CODE))
        {
          InterventionRootDAOImpl interventionRootDAOImpl = new InterventionRootDAOImpl();
          obj = interventionRootDAOImpl.loadObject(anUid.longValue());
        }
        else if (actType.equalsIgnoreCase(
            NEDSSConstants.OBSERVATION_CLASS_CODE))
        {
          ObservationRootDAOImpl observationRootDAOImpl = new ObservationRootDAOImpl();
          obj = observationRootDAOImpl.loadObject(anUid.longValue());
        }
      }
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
      throw new Exception("Error while retrieving a " + actType +
                             " value object. " + ex.toString());
    }

    return obj;
  }



  /**
   * Loads Entity vo objects stores the patient roll collection to be loaded to
   * the LabResultProxyVO later instead of going back to database.
   * @param partColl
   * @param securityObj
   * @return
   * @throws Exception
   */
  private List<Object> retrieveEntityForProxyVO(Collection<Object> partColl,
                                        NBSSecurityObj securityObj) throws Exception
  {
    List<Object> allEntityHolder = new ArrayList<Object> ();

    //Retrieve associated persons
    Object[] obj = retrievePersonVOsForProxyVO(partColl, securityObj);
    allEntityHolder.add(this.RETRIEVED_PERSONS_FOR_PROXY, (Collection<?>)obj[0]);

    //Retrieve associated organizations
    allEntityHolder.add(this.RETRIEVED_ORGANIZATIONS_FOR_PROXY,
                        retrieveOrganizationVOsForProxyVO(partColl, securityObj));

    //Retrieve associated materials
    allEntityHolder.add(this.RETRIEVED_MATERIALS_FOR_PROXY,
                        retrieveMaterialVOsForProxyVO(partColl, securityObj));
    Collection<?>  coll = (Collection<?>)obj[1];
    //if(coll.size() > 0)
     allEntityHolder.add(this.RETRIEVED_PATIENT_ROLES, coll);

    return allEntityHolder;
  }

  /**
   * Loads the Patient and other person objects associated with the OrderTest via the participation collection.
   * @param partColl
   * @param securityObj
   * @return
   * @throws Exception
   */
  private Object[] retrievePersonVOsForProxyVO(Collection<Object> partColl,
                                                 NBSSecurityObj securityObj) throws Exception
  {
    Object[] obj = new Object[2];
    Collection<Object>  thePersonVOCollection  = new ArrayList<Object> ();
    Collection<Object>  patientRollCollection  = new ArrayList<Object> ();
    for (Iterator<Object> it = partColl.iterator(); it.hasNext(); )
    {
      ParticipationDT partDT = (ParticipationDT) it.next();

      if (partDT == null)
      {
        continue;
      }

      String subjectClassCd = partDT.getSubjectClassCd();
      String recordStatusCd = partDT.getRecordStatusCd();
      String typeCd = partDT.getTypeCd();

      //If person...
      if (subjectClassCd != null &&
          subjectClassCd.equalsIgnoreCase(NEDSSConstants.PAR110_SUB_CD)
          && recordStatusCd != null &&
          recordStatusCd.equalsIgnoreCase(NEDSSConstants.ACTIVE))
      {
        PersonVO vo = (PersonVO)getEntityVO(NEDSSConstants.PERSON,
                                              partDT.getSubjectEntityUid(), null,
                                              securityObj);

        thePersonVOCollection.add(vo);

        //If the person is a patient, do more...
        if (typeCd != null &&
            typeCd.equalsIgnoreCase(NEDSSConstants.PAR110_TYP_CD))
        {
          if(vo.getTheRoleDTCollection().size() > 0)
            patientRollCollection.addAll(vo.getTheRoleDTCollection());
          Collection<Object>  scopedPersons = retrieveScopedPersons(vo.getThePersonDT().getPersonUid(), securityObj);
          if (scopedPersons != null && scopedPersons.size() > 0)
          {
          	Iterator<Object> ite = scopedPersons.iterator();
          	while(ite.hasNext())
          	{
          		PersonVO scopedPerson = (PersonVO)ite.next();
          		if(scopedPerson.getTheRoleDTCollection()!=null && scopedPerson.getTheRoleDTCollection().size()>0)
          			patientRollCollection.addAll(scopedPerson.getTheRoleDTCollection());
          		thePersonVOCollection.add(scopedPerson);
          	}
          }
        }
      }
    }
    obj[1] = patientRollCollection;
    obj[0] = thePersonVOCollection;
    return obj;
  }

  /**
   * Loads Organizations associated with the participation collection
   * @param partColl
   * @param securityObj
   * @return
   * @throws Exception
   */
  private Collection<Object>  retrieveOrganizationVOsForProxyVO(Collection<Object> partColl,
      NBSSecurityObj securityObj) throws Exception
  {
    Collection<Object>  theOrganizationVOCollection  = null;

    for (Iterator<Object> it = partColl.iterator(); it.hasNext(); )
    {
      ParticipationDT partDT = (ParticipationDT) it.next();

      if (partDT == null)
      {
        continue;
      }

      String subjectClassCd = partDT.getSubjectClassCd();
      String recordStatusCd = partDT.getRecordStatusCd();
      String typeCd = partDT.getTypeCd();
      //If organization...
      if (subjectClassCd != null &&
          subjectClassCd.equalsIgnoreCase(NEDSSConstants.PAR102_SUB_CD)
          && recordStatusCd != null &&
          recordStatusCd.equalsIgnoreCase(NEDSSConstants.ACTIVE))
      {
        Long organizationUid = partDT.getSubjectEntityUid();
        if (theOrganizationVOCollection  == null)
        {
          theOrganizationVOCollection  = new ArrayList<Object> ();
        }
        //if(!typeCd.equals(NEDSSConstants.PAR111_TYP_CD))
        theOrganizationVOCollection.add(getEntityVO(NEDSSConstants.ORGANIZATION,
            organizationUid, partDT.getActUid(),
            securityObj));
        //else if(typeCd.equals(NEDSSConstants.PAR111_TYP_CD)) {
          //theOrganizationVOCollection.add(getEntityVO(NEDSSConstants.ORGANIZATION,
            //organizationUid,partDT.getActUid(),
            //securityObj));
        //}
      }
    }
    return theOrganizationVOCollection;
  }

  /**
   * Loads the material associated with the participation collection passed in.
   * @param partColl
   * @param securityObj
   * @return
   * @throws Exception
   */
  private Collection<Object>  retrieveMaterialVOsForProxyVO(Collection<Object> partColl,
      NBSSecurityObj securityObj) throws Exception
  {
    Collection<Object>  theMaterialVOCollection  = null;

    for (Iterator<Object> it = partColl.iterator(); it.hasNext(); )
    {
      ParticipationDT partDT = (ParticipationDT) it.next();

      if (partDT == null)
      {
        continue;
      }

      String subjectClassCd = partDT.getSubjectClassCd();
      String recordStatusCd = partDT.getRecordStatusCd();

      //If material...
      if (subjectClassCd != null &&
          subjectClassCd.equalsIgnoreCase(NEDSSConstants.PAR104_SUB_CD)
          && recordStatusCd != null &&
          recordStatusCd.equalsIgnoreCase(NEDSSConstants.ACTIVE))
      {
        Long materialUid = partDT.getSubjectEntityUid();
        if (theMaterialVOCollection  == null)
        {
          theMaterialVOCollection  = new ArrayList<Object> ();
        }
        theMaterialVOCollection.add(getEntityVO(NEDSSConstants.MATERIAL,
                                                materialUid, null,
                                                securityObj));
      }
    }
    return theMaterialVOCollection;
  }


  /**
   * Loads the scoped entities associated with the Patient
   * @param patientRoleColl
   * @param securityObj
   * @return
   * @throws Exception
   */
  private Collection<Object>  retrieveScopedPersons(Long scopingUid,
                                           NBSSecurityObj securityObj) throws Exception
  {
    Collection<Object>  roleDTColl = new RoleDAOImpl().loadEntitiesScopedToPatient(scopingUid.longValue());
    Collection<Object>  scopedPersons = null;

    for (Iterator<Object> it = roleDTColl.iterator(); it.hasNext(); )
    {
      RoleDT roleDT = (RoleDT) it.next();
      if (roleDT == null)
      {
        continue;
      }

      String subjectClassCd = roleDT.getSubjectClassCd();
      String recordStatusCd = roleDT.getRecordStatusCd();
      //In this case the subjectEntityUid is not the patient
      Long scopingEntityUid = roleDT.getSubjectEntityUid();
      /**
       * @todo  Should the recordStatusCd be "Active" in order to load the person
       */

        if (scopedPersons == null)
        {
          scopedPersons = new ArrayList<Object> ();
        }
        if (scopingEntityUid != null)
        {
          scopedPersons.add(getEntityVO(NEDSSConstants.PERSON, scopingEntityUid, null,
                                        securityObj));
        }
      //}
    }
    return scopedPersons;
  }



  /**
   * This method is called when loading the organizationVO.  This mehtod as you can
   * see passes in the actId for use in the OrganizationRootDAOImpl when loading the
   * participation collection.  This will prevent the loading of data that is not specific for
   * the act uid.  Previously the act uid was not passed in resulting in the loading of 30,000 objects
   * when only one was required.
   * @param entityType
   * @param anUid
   * @param actUid
   * @param securityObj
   * @return
   * @throws java.lang.Exception
   */
  private Object getEntityVO(String entityType, Long anUid, Long actUid,
                             NBSSecurityObj securityObj)
  throws java.lang.Exception
  {

    Object obj = null;


    try
    {

      if(entityMap.containsKey(anUid)) {
       obj = entityMap.get(anUid);
      }else {
        if (entityType.equalsIgnoreCase(NEDSSConstants.PERSON)) {
          obj = personRootDAO.loadObject(anUid.longValue());
        }
        else if (entityType.equalsIgnoreCase(NEDSSConstants.ORGANIZATION)) {
          obj = organizationRootDAO.loadObject(anUid.longValue(),
                                               actUid.longValue());
        }
        else if (entityType.equalsIgnoreCase(NEDSSConstants.MATERIAL)) {
          obj = materialRootDAO.loadObject(anUid.longValue());
        }
        //caching to use later if needed in an other
        entityMap.put(anUid, obj);
      }//end of else
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
      throw new Exception("Error while retrieving a " + entityType +
                             " value object. " + ex.toString());
    }


    return obj;
  }




  /**
   * Determines if arguments are set to something other than null.  If null
   * throw exceptions
   * @param uid
   * @param securityObj
   */
  private void checkMethodArgs(Long uid, NBSSecurityObj securityObj)
  {
    if (uid == null || securityObj == null)
    {

      throw new NullPointerException(
          "Method arguements of getXXXProxy() cannot be null, however," +
          "\n Act/Entity uid is: " + uid +
          "\n NBSSecurityObj securityObj is: " + securityObj);
    }
  }

  /**
   * Determines if user has authorization to view the lab
   * @param rootVO
   * @param securityObj
   */
  private void checkPermissionToGetProxy(ObservationVO rootVO,
                                         NBSSecurityObj securityObj)
  {
    //Aborts the operation if not getting the permission
    ObservationDT rootDT = rootVO.getTheObservationDT();
    String nbsBOName = NBSBOLookup.OBSERVATIONLABREPORT;
    String nbsOperation = NBSOperationLookup.VIEW;

    if (!securityObj.checkDataAccess(rootDT,
                                     nbsBOName,
                                     nbsOperation))
    {
      String errorMessage = "User " + securityObj.getFullName() +
          "does not have data Access privileges for this Lab Report";
      logger.error(errorMessage);
      abort(nbsBOName, nbsOperation,
            "", "", "");
    }
  }

  /**
   * Called if person does not have permission to view lab
   * @param nbsBO
   * @param nbsOp
   * @param nbsPA
   * @param nbsJurisdiction
   * @param sharableFlag
   */
  private void abort(String nbsBO, String nbsOp, String nbsPA,
                     String nbsJurisdiction, String sharableFlag)
  {
    //This can turn into a custom exception
    throw new SecurityException(
        "Permission check fail for an operation of ObservationProxyEJB," +
        " NBS business object: " + nbsBO +
        ", NBS business operation: " + nbsOp +
        ", NBS program area: " + nbsPA +
        ", NBS jurisdiction: " + nbsJurisdiction +
        ", NBS sharable indicator: " + sharableFlag);

  }
  /**
   * deriveTheConditionCodeList - used by Associate to Investigations
   *    when associating an STD lab to a closed investigation.
   *    Condition list determines the Processing Decision to show.
   * @param labResultProxyVO
   * @param orderTest
   * @param nbsPA
   * @return list of the condition codes associated with resulted tests
   */
	private ArrayList<String> deriveTheConditionCodeList(
			LabResultProxyVO labResultProxyVO, ObservationVO orderTest,
			NBSSecurityObj securityObj) {
		ArrayList<String> derivedConditionList = new ArrayList<String>();

		//if this is not an STD Program Area - we can skip this overhead
		String programAreaCd = orderTest.getTheObservationDT().getProgAreaCd();
		if ((programAreaCd == null) || (!PropertyUtil.isStdOrHivProgramArea(programAreaCd))) {
			return derivedConditionList;
		}
				
		// Get the result tests
		Collection<Object> resultTests = new ArrayList<Object>();
		for (Iterator<ObservationVO> it = labResultProxyVO
				.getTheObservationVOCollection().iterator(); it.hasNext();) {
			ObservationVO obsVO = (ObservationVO) it.next();
			String obsDomainCdSt1 = obsVO.getTheObservationDT()
					.getObsDomainCdSt1();
			if (obsDomainCdSt1 != null
					&& obsDomainCdSt1
							.equalsIgnoreCase(NEDSSConstants.RESULTED_TEST_OBS_DOMAIN_CD)) {
				resultTests.add(obsVO);
			}
		}// result tests

		// Get the reporting lab clia
		String reportingLabCLIA = "";
		if (labResultProxyVO.getLabClia() != null
				&& labResultProxyVO.isManualLab())
			reportingLabCLIA = labResultProxyVO.getLabClia();
		else {
			if (orderTest.getTheParticipationDTCollection() != null) {
				reportingLabCLIA = getReportingLabCLIAId(orderTest.getTheParticipationDTCollection(),
					securityObj);
			}
		}
		if (reportingLabCLIA == null || reportingLabCLIA.trim().equals(""))
			reportingLabCLIA = NEDSSConstants.DEFAULT;

		// If there are resulted tests, call obs processor for the list of
		// associated conditions
		// found in the various lab test SRT tables
		if (resultTests.size() > 0) {
			ObservationProcessor obsProcessor = new ObservationProcessor();
			derivedConditionList = obsProcessor.getDerivedConditionList(
					reportingLabCLIA, resultTests, orderTest
							.getTheObservationDT().getElectronicInd());
		}

		return derivedConditionList;
	}
	///////////////////////////////////////////////////////////////////////////
    //gst - copied from ObservationProxyEJB and modified 
	private String getReportingLabCLIAId(Collection<Object> partColl,
			NBSSecurityObj securityObj) {


		// Get the reporting lab
		Long reportingLabUid = this.getUid(partColl,
				NEDSSConstants.ENTITY_UID_LIST_TYPE,
				NEDSSConstants.ORGANIZATION, NEDSSConstants.PAR111_TYP_CD,
				NEDSSConstants.PART_ACT_CLASS_CD,
				NEDSSConstants.RECORD_STATUS_ACTIVE, false);

		OrganizationVO reportingLabVO = null;
		EntityController eController = null;
		try {
			eController = getEntityControllerRemoteInterface();
			if (reportingLabUid != null) {
				reportingLabVO = eController.getOrganization(reportingLabUid,
						securityObj);
			}
		} catch (RemoteException rex) {
			rex.printStackTrace();
			throw new EJBException(
					"Error while retriving reporting organization vo, its uid is: "
							+ reportingLabUid);
		} finally {
			try {
				eController.remove();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		// Get the CLIA
		String reportingLabCLIA = null;

		if (reportingLabVO != null) {
			Collection<Object> entityIdColl = reportingLabVO
					.getTheEntityIdDTCollection();

			if (entityIdColl != null && entityIdColl.size() > 0) {
				for (Iterator<Object> it = entityIdColl.iterator(); it
						.hasNext();) {
					EntityIdDT idDT = (EntityIdDT) it.next();
					if (idDT == null) {
						continue;
					}

					String authoCd = idDT.getAssigningAuthorityCd();
					String idTypeCd = idDT.getTypeCd();
					if (authoCd == null || idTypeCd == null) {
						continue;
					}
					if (authoCd.trim().contains(NEDSSConstants.REPORTING_LAB_CLIA) &&
							idTypeCd.trim().equalsIgnoreCase(NEDSSConstants.REPORTING_LAB_FI_TYPE)) { 
						reportingLabCLIA = idDT.getRootExtensionTxt();
						break;
					}
				} //for
			} //if 
		}
		return reportingLabCLIA;
	}

	//gst copied from ObservationEJB
	private EntityController getEntityControllerRemoteInterface()
			throws EJBException {

		EntityController entityController = null;
		try {

			logger.debug("in getRemoteInterface() method");
			NedssUtils nu = new NedssUtils();
			Object obj = nu.lookupBean(JNDINames.EntityControllerEJB);
			EntityControllerHome entityControllerHome = (EntityControllerHome) javax.rmi.PortableRemoteObject
					.narrow(obj, EntityControllerHome.class);
			entityController = entityControllerHome.create();
		} catch (Exception e) {
			logger.fatal(
					"Error while creating a EntityController reference in LabResultProxyManager.",
					e);
			throw new EJBException(e.toString());
		}
		return entityController;
	} //getEntityControllerRemoteInterface  

    //gst copied from ObservationEJB
	private Long getUid(java.util.Collection<Object> associationDTColl,
			String uidListType, String uidClassCd, String uidTypeCd,
			String uidActClassCd, String uidRecordStatusCd, boolean act)

	{

		Long anUid = null;

		try {

			Iterator<Object> assocIter = associationDTColl.iterator();

			while (assocIter.hasNext()) {

				if (!act) {

					ParticipationDT partDT = (ParticipationDT) assocIter.next();

					if (((partDT.getSubjectClassCd() != null && partDT
							.getSubjectClassCd().equalsIgnoreCase(uidClassCd))
							&& (partDT.getTypeCd() != null && partDT
									.getTypeCd().equalsIgnoreCase(uidTypeCd))
							&& (partDT.getActClassCd() != null && partDT
									.getActClassCd().equalsIgnoreCase(
											uidActClassCd)) && (partDT
							.getRecordStatusCd() != null && partDT
							.getRecordStatusCd().equalsIgnoreCase(
									uidRecordStatusCd)))) {
						anUid = partDT.getSubjectEntityUid();
					}
				} else {

					ActRelationshipDT actRelDT = (ActRelationshipDT) assocIter
							.next();

					if (((actRelDT.getSourceClassCd() != null && actRelDT
							.getSourceClassCd().equalsIgnoreCase(uidClassCd))
							&& (actRelDT.getTypeCd() != null && actRelDT
									.getTypeCd().equalsIgnoreCase(uidTypeCd))
							&& (actRelDT.getTargetClassCd() != null && actRelDT
									.getTargetClassCd().equalsIgnoreCase(
											uidActClassCd)) && (actRelDT
							.getRecordStatusCd() != null && actRelDT
							.getRecordStatusCd().equalsIgnoreCase(
									uidRecordStatusCd)))) {

						if (uidListType
								.equalsIgnoreCase(NEDSSConstants.ACT_UID_LIST_TYPE)) {
							anUid = actRelDT.getTargetActUid();
						} else if (uidListType
								.equalsIgnoreCase(NEDSSConstants.SOURCE_ACT_UID_LIST_TYPE)) {
							anUid = actRelDT.getSourceActUid();
						}
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException("Error while retrieving a "
					+ uidListType + " uid. " + ex.toString());
		}

		return anUid;
	}


}