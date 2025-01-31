// *** Generated Source File ***
// Portions Copyright (c) 1996-2001, SilverStream Software, Inc., All Rights Reserved
//
// -- Java Code Generation Process --
package gov.cdc.nedss.controller.ejb.actcontrollerejb.bean;

import gov.cdc.nedss.act.actid.dt.ActIdDT;
import gov.cdc.nedss.act.clinicaldocument.dt.ClinicalDocumentDT;
import gov.cdc.nedss.act.clinicaldocument.ejb.bean.ClinicalDocument;
import gov.cdc.nedss.act.clinicaldocument.ejb.bean.ClinicalDocumentHome;
import gov.cdc.nedss.act.clinicaldocument.vo.ClinicalDocumentVO;
import gov.cdc.nedss.act.ctcontact.dt.CTContactNoteDT;
import gov.cdc.nedss.act.ctcontact.ejb.bean.CTContact;
import gov.cdc.nedss.act.ctcontact.ejb.bean.CTContactHome;
import gov.cdc.nedss.act.ctcontact.vo.CTContactVO;
import gov.cdc.nedss.act.intervention.dt.InterventionDT;
import gov.cdc.nedss.act.intervention.dt.Procedure1DT;
import gov.cdc.nedss.act.intervention.dt.SubstanceAdministrationDT;
import gov.cdc.nedss.act.intervention.ejb.bean.Intervention;
import gov.cdc.nedss.act.intervention.ejb.bean.InterventionHome;
import gov.cdc.nedss.act.intervention.vo.InterventionVO;
import gov.cdc.nedss.act.interview.ejb.bean.Interview;
import gov.cdc.nedss.act.interview.ejb.bean.InterviewHome;
import gov.cdc.nedss.act.interview.ejb.dao.InterviewAnswerDAOImpl;
import gov.cdc.nedss.act.interview.vo.InterviewVO;
import gov.cdc.nedss.act.notification.dt.NotificationDT;
import gov.cdc.nedss.act.notification.ejb.bean.Notification;
import gov.cdc.nedss.act.notification.ejb.bean.NotificationHome;
import gov.cdc.nedss.act.notification.vo.NotificationVO;
import gov.cdc.nedss.act.observation.dt.ObsValueCodedDT;
import gov.cdc.nedss.act.observation.dt.ObsValueCodedModDT;
import gov.cdc.nedss.act.observation.dt.ObsValueDateDT;
import gov.cdc.nedss.act.observation.dt.ObsValueNumericDT;
import gov.cdc.nedss.act.observation.dt.ObsValueTxtDT;
import gov.cdc.nedss.act.observation.dt.ObservationDT;
import gov.cdc.nedss.act.observation.dt.ObservationInterpDT;
import gov.cdc.nedss.act.observation.dt.ObservationReasonDT;
import gov.cdc.nedss.act.observation.ejb.bean.Observation;
import gov.cdc.nedss.act.observation.ejb.bean.ObservationHome;
import gov.cdc.nedss.act.observation.vo.ObservationVO;
import gov.cdc.nedss.act.patientencounter.dt.PatientEncounterDT;
import gov.cdc.nedss.act.patientencounter.ejb.bean.PatientEncounter;
import gov.cdc.nedss.act.patientencounter.ejb.bean.PatientEncounterHome;
import gov.cdc.nedss.act.patientencounter.vo.PatientEncounterVO;
import gov.cdc.nedss.act.publichealthcase.dt.ConfirmationMethodDT;
import gov.cdc.nedss.act.publichealthcase.dt.PublicHealthCaseDT;
import gov.cdc.nedss.act.publichealthcase.ejb.bean.PublicHealthCase;
import gov.cdc.nedss.act.publichealthcase.ejb.bean.PublicHealthCaseHome;
import gov.cdc.nedss.act.publichealthcase.ejb.dao.PublicHealthCaseHistoryManager;
import gov.cdc.nedss.act.publichealthcase.vo.PublicHealthCaseVO;
import gov.cdc.nedss.act.referral.dt.ReferralDT;
import gov.cdc.nedss.act.referral.ejb.bean.Referral;
import gov.cdc.nedss.act.referral.ejb.bean.ReferralHome;
import gov.cdc.nedss.act.referral.vo.ReferralVO;
import gov.cdc.nedss.act.treatment.dt.TreatmentDT;
import gov.cdc.nedss.act.treatment.ejb.bean.Treatment;
import gov.cdc.nedss.act.treatment.ejb.bean.TreatmentHome;
import gov.cdc.nedss.act.treatment.vo.TreatmentVO;
import gov.cdc.nedss.association.dao.ActRelationshipDAOImpl;
import gov.cdc.nedss.association.dao.ParticipationDAOImpl;
import gov.cdc.nedss.association.dt.ActRelationshipDT;
import gov.cdc.nedss.association.dt.ParticipationDT;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.locator.dao.EntityLocatorParticipationDAOImpl;
import gov.cdc.nedss.locator.dt.ActivityLocatorParticipationDT;
import gov.cdc.nedss.locator.dt.EntityLocatorParticipationDT;
import gov.cdc.nedss.locator.dt.PhysicalLocatorDT;
import gov.cdc.nedss.locator.dt.PostalLocatorDT;
import gov.cdc.nedss.locator.dt.TeleLocatorDT;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.util.AssocDTInterface;
import gov.cdc.nedss.systemservice.util.NEDSSDAOFactory;
import gov.cdc.nedss.systemservice.util.PrepareVOUtils;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.PropertyUtil;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

import javax.ejb.EJBException;
// Import Statements
import javax.ejb.SessionContext;
import javax.rmi.PortableRemoteObject;


public class ActControllerEJB
    implements javax.ejb.SessionBean
{

	private static final long serialVersionUID = 1L;
    //For logging
    static final LogUtils logger = new LogUtils(ActControllerEJB.class.getName());
    private SessionContext cntx;

    /**
     * @roseuid 3BF3D7200291
     * @J2EE_METHOD  --  ActControllerEJB
     */
    public ActControllerEJB()
    {
    }

    /**
     * @roseuid 3BF3D720029B
     * @J2EE_METHOD  --  ejbCreate
     * Called by the container to create a session bean instance. Its parameters typically
     * contain the information the client uses to customize the bean instance for its use.
     * It requires a matching pair in the bean class and its home interface.
     */
    public void ejbCreate()
    {
    }

    /**
     * @roseuid 3BF3D72002AF
     * @J2EE_METHOD  --  ejbRemove
     * A container invokes this method before it ends the life of the session object. This
     * happens as a result of a client's invoking a remove operation, or when a container
     * decides to terminate the session object after a timeout. This method is called with
     * no transaction context.
     */
    public void ejbRemove()
    {
    }

    /**
     * @roseuid 3BF3D72002B9
     * @J2EE_METHOD  --  ejbActivate
     * The activate method is called when the instance is activated from its 'passive' state.
     * The instance should acquire any resource that it has released earlier in the ejbPassivate()
     * method. This method is called with no transaction context.
     */
    public void ejbActivate()
    {
    }

    /**
     * @roseuid 3BF3D72002CD
     * @J2EE_METHOD  --  ejbPassivate
     * The passivate method is called before the instance enters the 'passive' state. The
     * instance should release any resources that it can re-acquire later in the ejbActivate()
     * method. After the passivate method completes, the instance must be in a state that
     * allows the container to use the Java Serialization protocol to externalize and store
     * away the instance's state. This method is called with no transaction context.
     */
    public void ejbPassivate()
    {
    }

    /**
     * @roseuid 3BFAF8DC0034
     * @J2EE_METHOD  --  setObservation
     */
    public Long setObservation(ObservationVO observationVO,NBSSecurityObj nbsSecurityObj)
								throws
								javax.ejb.EJBException,
								NEDSSConcurrentDataException,

								NEDSSSystemException

    {

        Long observationUid = new Long(-1);

        try
        {

            Observation observation = null;
            NedssUtils nedssUtils = new NedssUtils();
            Object obj = nedssUtils.lookupBean(JNDINames.ObservationEJB);
            logger.debug("ObservationEJB lookup = " + obj.toString());

            ObservationHome home = (ObservationHome)PortableRemoteObject.narrow(
                                           obj, ObservationHome.class);
            logger.debug("Found ObservationHome: " + home);

            Collection<Object> alpDTCol = observationVO.getTheActivityLocatorParticipationDTCollection();
            Collection<Object> arDTCol = observationVO.getTheActRelationshipDTCollection();
            Collection<Object> pDTCol = observationVO.getTheParticipationDTCollection();
            Collection<Object> col = null;

            if (alpDTCol != null)
            {
                col = this.iterateALPDT(alpDTCol, nbsSecurityObj);
                observationVO.setTheActivityLocatorParticipationDTCollection(
                        col);
            }

            if (arDTCol != null)
            {
                col = this.iterateARDT(arDTCol, nbsSecurityObj);
                observationVO.setTheActRelationshipDTCollection(col);
            }

            if (pDTCol != null)
            {
                col = this.iteratePDT(pDTCol, nbsSecurityObj);
                observationVO.setTheParticipationDTCollection(col);
            }

            if (observationVO.isItNew())
            {
                logger.debug("ActControllerEJB.setObservation121111 :"+observationVO.isItNew() +
                             ": VersionCtrlNm :"+observationVO.getTheObservationDT().getVersionCtrlNbr());
                observation = home.create(observationVO);
                logger.debug("observation in new is :" + observation);
                logger.debug(
                        " ActControllerEJB.setObservation() Observation Created");
                observationUid = observation.getObservationVO().getTheObservationDT()
                           .getObservationUid();
            }
            else
            {
                logger.debug("found the observation for the setObservation");

                if (observationVO.getTheObservationDT() != null) // make sure it is not null
                {
                    observation = home.findByPrimaryKey(observationVO.getTheObservationDT().getObservationUid());
                    logger.debug(
                            "observation in find by primary key is:" +
                            observation);
                    logger.debug("ActControllerEJB.setObservation :"+observationVO.isItNew() +
                                 ": VersionCtrlNm :"+observationVO.getTheObservationDT().getVersionCtrlNbr());
                    observation.setObservationVO(observationVO);
                    observationUid = observationVO.getTheObservationDT().getObservationUid();
                    logger.debug(
                            " ActControllerEJB.setObservation() Observation Updated");
                }
            }
        }
        catch (Exception e)
        {
            if( e.toString().indexOf("NEDSSConcurrentDataException")!= -1)
            {
                logger.fatal("Throwing NEDSSConcurrentDataException" + e.getMessage(),e);
                throw new NEDSSConcurrentDataException(e.getMessage(),e);
            }
            else
            {
              logger.fatal("ActControllerEJB.setObservation: "  + e.getMessage(),e);
              e.printStackTrace();
              throw new javax.ejb.EJBException(e.getMessage(),e);
            }
        }

        logger.debug("the  observationUid is " + observationUid);

        return observationUid;
    }

    /**
     * @roseuid 3BFAF8DC00FC
     * @J2EE_METHOD  --  getObservation
     */
    public ObservationVO getObservation(Long observationUid,
                                        NBSSecurityObj nbsSecurityObj)
                                 throws javax.ejb.EJBException
    {

        ObservationVO observationVO = null;

        try
        {

            Observation observation = null;
            NedssUtils nedssUtils = new NedssUtils();
            Object obj = nedssUtils.lookupBean(JNDINames.ObservationEJB);
            logger.debug("Act Controller lookup = " + obj.toString());

            ObservationHome home = (ObservationHome)PortableRemoteObject.narrow(
                                           obj, ObservationHome.class);
            logger.debug("Found ObservationHome: " + home);

            if (observationUid != null)
                observation = home.findByPrimaryKey(observationUid);
            else
                throw new javax.ejb.EJBException("ActControllerEJB.getObservation(): observationUid is NULL ");

            observationVO = observation.getObservationVO();
            logger.debug(
                    "Act Controller past the find - TheObservationDT().getVersionCtrlNbr() = " +
                    observationVO.getTheObservationDT().getVersionCtrlNbr());
            logger.debug(
                    "Act Controller Done find by primary key! for PK: " +
                    observation.getPrimaryKey());
        }
        catch (Exception e)
        {
            logger.fatal("ActControllerEJB.getObservation(): " + e.getMessage(),e);
            e.printStackTrace();
            throw new javax.ejb.EJBException(e.getMessage(),e);
        }

        return observationVO;
    }

    /**
     * @roseuid 3BFAF8DC01CE
     * @J2EE_METHOD  --  getObservationInfo
     */
    public ObservationDT getObservationInfo(Long observationUid,
                                            NBSSecurityObj nbsSecurityObj)
                                     throws javax.ejb.EJBException
    {
    	try{
	
        ObservationVO observationVO = null;
        ObservationDT obsDT = null;
        observationVO = getObservation(observationUid, nbsSecurityObj);

        if (observationVO != null)
            obsDT = observationVO.getTheObservationDT();
      
        logger.debug(
                "ActController.getObservationInfo(): ObservationDT = " +
                obsDT);

        return obsDT;
    	}
    	catch (Exception e)
    	{
        logger.fatal("ActControllerEJB.getObservationInfo:" + e.getMessage(), e);
        throw new EJBException(e.getMessage(),e);
    	}
    }

    /**
     * @roseuid 3BFAF8DC02AA
     * @J2EE_METHOD  --  setObservationInfo
     */
    public void setObservationInfo(ObservationDT observationDT,
                                   NBSSecurityObj nbsSecurityObj)
								throws
								javax.ejb.EJBException,
								NEDSSConcurrentDataException,

								NEDSSSystemException


    {
    	try{
       ObservationVO observationVO = null;

        if (observationDT.getObservationUid() != null)
            observationVO = getObservation(observationDT.getObservationUid(),
                                           nbsSecurityObj);

        if (observationVO != null)
            observationVO.setTheObservationDT(observationDT);

        if (observationVO == null)
        {
            observationVO = new ObservationVO();
            observationVO.setTheObservationDT(observationDT);
        }

        observationVO.setTheObservationDT(observationDT);
        observationVO.setItDirty(true);
        this.setObservation(observationVO, nbsSecurityObj);
    	}
    	catch (Exception e)
    	{
        logger.fatal("ActControllerEJB.setObservationInfo:" + e.getMessage(), e);
        throw new EJBException(e.getMessage(),e);
    	}
    }

    /**
     * @roseuid 3BFAF8DC0387
     * @J2EE_METHOD  --  getObservationReasons
     */
    public Collection<Object> getObservationReasons(Long observationUid,
                                            NBSSecurityObj nbsSecurityObj)
                                     throws javax.ejb.EJBException
    {
    	try{
        ObservationVO observationVO = null;
        Collection<Object> observationReasonDTs = new ArrayList<Object> ();

        if (observationUid != null)
            observationVO = getObservation(observationUid, nbsSecurityObj);

        if (observationVO != null)
            observationReasonDTs = observationVO.getTheObservationReasonDTCollection();

        return observationReasonDTs;
    	}
        catch (Exception e)
        {
            logger.fatal("ActControllerEJB.getObservationReasons:" + e.getMessage(), e);
            throw new javax.ejb.EJBException(e.getMessage(), e);
        }
    }

    /**
     * @roseuid 3BFAF8DD0085
     * @J2EE_METHOD  --  getObservationReason
     */
    public ObservationReasonDT getObservationReason(Long observationUid,
                                                    String reasonCd,
                                                    NBSSecurityObj nbsSecurityObj)
                                             throws javax.ejb.EJBException
    {
    	try{
        ObservationVO observationVO = null;
        Collection<Object> observationReasonDTs = new ArrayList<Object> ();
        ObservationReasonDT observationReasonDT = null;

        if (observationUid != null)
            observationVO = this.getObservation(observationUid, nbsSecurityObj);

        if (observationVO != null)
            observationReasonDTs = observationVO.getTheObservationReasonDTCollection();

        Iterator<Object> anIterator = null;

        if (observationReasonDTs != null)
        {

            for (anIterator = observationReasonDTs.iterator();
                 anIterator.hasNext();)
            {
                observationReasonDT = (ObservationReasonDT)anIterator.next();

                if ((reasonCd != null) &&
                    ((observationReasonDT.getReasonCd()).compareTo(reasonCd) == 0))
                {
                    logger.debug(
                            " ActController.getObservationReason() got ObservationReasonDT for observationReason = " +
                            reasonCd);

                    return observationReasonDT;
                }
                else
                {

                    continue;
                }
            }
        }

        return null;
    	}
        catch (Exception e)
        {
        logger.fatal("ActControllerEJB.getObservationReasons:" + e.getMessage(), e);
        throw new javax.ejb.EJBException(e.getMessage(), e);
		}
    }

    /**
     * @roseuid 3BFAF8DD01F8
     * @J2EE_METHOD  --  setObservationReasons
     */
    public void setObservationReasons(Collection<Object> observationReasons,
                                      NBSSecurityObj nbsSecurityObj)
								throws
								javax.ejb.EJBException,
								NEDSSConcurrentDataException,

								NEDSSSystemException


    {
    	try{
        ObservationVO observationVO = null;
        ObservationReasonDT observationReasonDT = null;
        Iterator<Object> anIterator = null;

        if (observationReasons != null)
        {
            anIterator = observationReasons.iterator();

            if (anIterator.hasNext())
            {
                observationReasonDT = (ObservationReasonDT)anIterator.next();
                observationVO = this.getObservation(observationReasonDT.getObservationUid(),
                                                    nbsSecurityObj);
                observationVO.setTheObservationReasonDTCollection(
                        observationReasons);
                this.setObservation(observationVO, nbsSecurityObj);
                logger.debug(
                        "Actcontroller.setObservationReason() has been set for the observationUid = " +
                        observationVO.getTheObservationDT().getObservationUid());
            }
        }
    	}
    	catch (Exception e)
    	{
    		logger.fatal("ActControllerEJB.setObservationReasons:" + e.getMessage(), e);
    		throw new javax.ejb.EJBException(e.getMessage(),e);
    	}
    }

    /**
     * @roseuid 3BFAF8DD02E8
     * @J2EE_METHOD  --  getObsValueNumerics
     */
    public Collection<Object> getObsValueNumerics(Long observationUid,
                                          NBSSecurityObj nbsSecurityObj)
                                   throws javax.ejb.EJBException
    {
    	try{
        ObservationVO observationVO = null;
        Collection<Object> obsValueNumericDTs = new ArrayList<Object> ();

        if (observationUid != null)
            observationVO = getObservation(observationUid, nbsSecurityObj);

        if (observationVO != null)
            obsValueNumericDTs = observationVO.getTheObsValueNumericDTCollection();

        return obsValueNumericDTs;
    	}
    	catch (Exception e)
    	{
    		logger.fatal("ActControllerEJB.getObsValueNumerics:" + e.getMessage(), e);
    		throw new javax.ejb.EJBException(e.getMessage(),e);
    	}
    }

    /**
     * @roseuid 3BFAF8DD03E2
     * @J2EE_METHOD  --  getObsValueNumeric
     */
    public ObsValueNumericDT getObsValueNumeric(Long observationUid,
                                                Short obsValueNumericSeq,
                                                NBSSecurityObj nbsSecurityObj)
                                         throws javax.ejb.EJBException
    {
    	try{
        ObservationVO observationVO = null;
        Collection<Object> obsValueNumericDTs = new ArrayList<Object> ();
        ObsValueNumericDT obsValueNumericDT = null;

        if (observationUid != null)
            observationVO = this.getObservation(observationUid, nbsSecurityObj);

        if (observationVO != null)
            obsValueNumericDTs = observationVO.getTheObsValueNumericDTCollection();

        Iterator<Object> anIterator = null;

        if (obsValueNumericDTs != null)
        {

            for (anIterator = obsValueNumericDTs.iterator();
                 anIterator.hasNext();)
            {
                obsValueNumericDT = (ObsValueNumericDT)anIterator.next();

                if ((obsValueNumericSeq != null) &&
                    (obsValueNumericDT.getObsValueNumericSeq().intValue() == obsValueNumericSeq.intValue()))
                {
                    logger.debug(
                            " ActController.getObsValueNumeric() got ObsValueNumericDT for obsValueNumeric = " +
                            obsValueNumericSeq);

                    return obsValueNumericDT;
                }
                else
                {

                    continue;
                }
            }
        }

        return null;
    	}
    	catch (Exception e)
    	{
    		logger.fatal("ActControllerEJB.getObsValueNumeric:" + e.getMessage(), e);
    		throw new javax.ejb.EJBException(e.getMessage(),e);
    	}
    }

    /**
     * @roseuid 3BFAF8DE018B
     * @J2EE_METHOD  --  setObsValueNumerics
     */
    public void setObsValueNumerics(Collection<Object> obsValueNumerics,
                                    NBSSecurityObj nbsSecurityObj)
								throws
								javax.ejb.EJBException,
								NEDSSConcurrentDataException,

								NEDSSSystemException


    {
    	try{
        ObservationVO observationVO = null;
        ObsValueNumericDT obsValueNumericDT = null;
        Iterator<Object> anIterator = null;

        if (obsValueNumerics != null)
        {
            anIterator = obsValueNumerics.iterator();

            if (anIterator.hasNext())
            {
                obsValueNumericDT = (ObsValueNumericDT)anIterator.next();
                logger.debug(
                        "the value of obsValueNumericDT.getObservationUid" +
                        obsValueNumericDT.getObservationUid());
                observationVO = this.getObservation(obsValueNumericDT.getObservationUid(),
                                                    nbsSecurityObj);
                observationVO.setTheObsValueNumericDTCollection(
                        obsValueNumerics);
                this.setObservation(observationVO, nbsSecurityObj);
                logger.debug(
                        "Actcontroller.setObsValueNumeric() has been set for the observationUid = " +
                        (obsValueNumericDT.getObservationUid()));
            }
        }
    	}
    	catch (Exception e)
    	{
    	logger.fatal("ActControllerEJB.setObsValueNumerics:" + e.getMessage(), e);
    	throw new javax.ejb.EJBException(e.getMessage(),e);
    	}
    }

    /**
     * @roseuid 3BFAF8DE028F
     * @J2EE_METHOD  --  getObsValueDates
     */
    public Collection<Object> getObsValueDates(Long observationUid,
                                       NBSSecurityObj nbsSecurityObj)
                                throws javax.ejb.EJBException
    {
    	try{
        ObservationVO observationVO = null;
        Collection<Object> obsValueDateDTs = new ArrayList<Object> ();

        if (observationUid != null)
            observationVO = getObservation(observationUid, nbsSecurityObj);

        if (observationVO != null)
            obsValueDateDTs = observationVO.getTheObsValueDateDTCollection();

        return obsValueDateDTs;
    	}
    	catch (Exception e)
    	{
    	logger.fatal("ActControllerEJB.getObservationReasons:" + e.getMessage(), e);
    	throw new javax.ejb.EJBException(e.getMessage(),e);
    	}
    }

    /**
     * @roseuid 3BFAF8DE039E
     * @J2EE_METHOD  --  getObsValueDate
     */
    public ObsValueDateDT getObsValueDate(Long observationUid,
                                          Short obsValueDateSeq,
                                          NBSSecurityObj nbsSecurityObj)
                                   throws javax.ejb.EJBException
    {
    	try{
        ObservationVO observationVO = null;
        Collection<Object> obsValueDateDTs = new ArrayList<Object> ();
        ObsValueDateDT obsValueDateDT = null;

        if (observationUid != null)
            observationVO = this.getObservation(observationUid, nbsSecurityObj);

        if (observationVO != null)
            obsValueDateDTs = observationVO.getTheObsValueDateDTCollection();

        Iterator<Object> anIterator = null;

        if (obsValueDateDTs != null)
        {

            for (anIterator = obsValueDateDTs.iterator();
                 anIterator.hasNext();)
            {
                obsValueDateDT = (ObsValueDateDT)anIterator.next();

                if ((obsValueDateSeq != null) &&
                    (obsValueDateDT.getObsValueDateSeq().intValue() == obsValueDateSeq.intValue()))
                {
                    logger.debug(
                            " ActController.getObsValueDate() got ObsValueDateDT for obsValueDate = " +
                            obsValueDateSeq);

                    return obsValueDateDT;
                }
                else
                {

                    continue;
                }
            }
        }

        return null;
    }
    	catch (Exception e)
    	{
    	logger.fatal("ActControllerEJB.getObsValueDate:" + e.getMessage(), e);
    	throw new javax.ejb.EJBException(e.getMessage(),e);
    	}
    }

    /**
     * @roseuid 3BFAF8DF0164
     * @J2EE_METHOD  --  setObsValueDates
     */
    public void setObsValueDates(Collection<Object> obsValueDates,
                                 NBSSecurityObj nbsSecurityObj)
								throws
								javax.ejb.EJBException,
								NEDSSConcurrentDataException,

								NEDSSSystemException


    {
    	try{
        ObservationVO observationVO = null;
        ObsValueDateDT obsValueDateDT = null;
        Iterator<Object> anIterator = null;

        if (obsValueDates != null)
        {
            anIterator = obsValueDates.iterator();

            if (anIterator.hasNext())
            {
                obsValueDateDT = (ObsValueDateDT)anIterator.next();
                observationVO = this.getObservation(obsValueDateDT.getObservationUid(),
                                                    nbsSecurityObj);
                observationVO.setTheObsValueDateDTCollection(obsValueDates);
                this.setObservation(observationVO, nbsSecurityObj);
                logger.debug(
                        "Actcontroller.setObsValueDate() has been set for the observationUid = " +
                        (obsValueDateDT.getObservationUid()));
            }
        }
    	}
    	catch (Exception e)
    	{
    	logger.fatal("ActControllerEJB.setObsValueDates:" + e.getMessage(), e);
    	throw new javax.ejb.EJBException(e.getMessage(),e);
    	}
    }

    /**
     * @roseuid 3BFAF8DF0287
     * @J2EE_METHOD  --  getObsValueTxts
     */
    public Collection<Object> getObsValueTxts(Long observationUid,
                                      NBSSecurityObj nbsSecurityObj)
                               throws javax.ejb.EJBException
    {
    	try{
        ObservationVO observationVO = null;
        Collection<Object> obsValueTxtDTs = new ArrayList<Object> ();

        if (observationUid != null)
            observationVO = getObservation(observationUid, nbsSecurityObj);

        if (observationVO != null)
            obsValueTxtDTs = observationVO.getTheObsValueTxtDTCollection();

        return obsValueTxtDTs;
    	}
    	catch (Exception e)
    	{
    	logger.fatal("ActControllerEJB.getObsValueTxts:" + e.getMessage(), e);
    	throw new javax.ejb.EJBException(e.getMessage(),e);
    	}
	}
    /**
     * @roseuid 3BFAF8DF03B3
     * @J2EE_METHOD  --  getObsValueTxt
     */
    public ObsValueTxtDT getObsValueTxt(Long observationUid,
                                        Short obsValueTxtSeq,
                                        NBSSecurityObj nbsSecurityObj)
                                 throws javax.ejb.EJBException
    {
    	try{
        ObservationVO observationVO = null;
        Collection<Object> obsValueTxtDTs = new ArrayList<Object> ();
        ObsValueTxtDT obsValueTxtDT = null;

        if (observationUid != null)
            observationVO = this.getObservation(observationUid, nbsSecurityObj);

        if (observationVO != null)
            obsValueTxtDTs = observationVO.getTheObsValueTxtDTCollection();

        Iterator<Object> anIterator = null;

        if (obsValueTxtDTs != null)
        {

            for (anIterator = obsValueTxtDTs.iterator(); anIterator.hasNext();)
            {
                obsValueTxtDT = (ObsValueTxtDT)anIterator.next();

                if ((obsValueTxtSeq != null) &&
                    (obsValueTxtDT.getObsValueTxtSeq().intValue() == obsValueTxtSeq.intValue()))
                {
                    logger.debug(
                            " ActController.getObsValueTxt() got ObsValueTxtDT for obsValueTxt = " +
                            obsValueTxtSeq);

                    return obsValueTxtDT;
                }
                else
                {

                    continue;
                }
            }
        }

        return null;
    	}
    	catch (Exception e)
    	{
    	logger.fatal("ActControllerEJB.getObsValueTxt:" + e.getMessage(), e);
    	throw new javax.ejb.EJBException(e.getMessage(),e);
    	}
	}

    /**
     * @roseuid 3BFAF8E00198
     * @J2EE_METHOD  --  setObsValueTxts
     */
    public void setObsValueTxts(Collection<Object> obsValueTxts,
                                NBSSecurityObj nbsSecurityObj)
								throws
								javax.ejb.EJBException,
								NEDSSConcurrentDataException,

								NEDSSSystemException


    {
    	try{
        ObservationVO observationVO = null;
        ObsValueTxtDT obsValueTxtDT = null;
        Iterator<Object> anIterator = null;

        if (obsValueTxts != null)
        {
            anIterator = obsValueTxts.iterator();

            if (anIterator.hasNext())
            {
                obsValueTxtDT = (ObsValueTxtDT)anIterator.next();
                observationVO = this.getObservation(obsValueTxtDT.getObservationUid(),
                                                    nbsSecurityObj);
                observationVO.setTheObsValueTxtDTCollection(obsValueTxts);
                this.setObservation(observationVO, nbsSecurityObj);
                logger.debug(
                        "Actcontroller.setObsValueTxt() has been set for the observationUid = " +
                        (obsValueTxtDT.getObservationUid()));
            }
        }
    	}
    	catch (Exception e)
    	{
    	logger.fatal("ActControllerEJB.setObsValueTxts:" + e.getMessage(), e);
    	throw new javax.ejb.EJBException(e.getMessage(),e);
    	}
    }

    /**
     * @roseuid 3BFAF8E002CE
     * @J2EE_METHOD  --  getObservationInterps
     */
    public Collection<Object> getObservationInterps(Long observationUid,
                                            NBSSecurityObj nbsSecurityObj)
                                     throws javax.ejb.EJBException
    {
    	try{
        ObservationVO observationVO = null;
        Collection<Object> observationInterpDTs = new ArrayList<Object> ();

        if (observationUid != null)
            observationVO = getObservation(observationUid, nbsSecurityObj);

        if (observationVO != null)
            observationInterpDTs = observationVO.getTheObservationInterpDTCollection();

        return observationInterpDTs;
    	}
    	catch (Exception e)
    	{
    	logger.fatal("ActControllerEJB.getObservationInterps:" + e.getMessage(), e);
    	throw new javax.ejb.EJBException(e.getMessage(),e);
    	}
    }

    /**
     * @roseuid 3BFAF8E10027
     * @J2EE_METHOD  --  getObservationInterp
     */
    public ObservationInterpDT getObservationInterp(Long observationUid,
                                                    String interpretationCd,
                                                    NBSSecurityObj nbsSecurityObj)
                                             throws javax.ejb.EJBException
    {

        ObservationVO observationVO = null;
        Collection<Object> observationInterpDTs = new ArrayList<Object> ();
        ObservationInterpDT observationInterpDT = null;

        if (observationUid != null)
            observationVO = this.getObservation(observationUid, nbsSecurityObj);

        if (observationVO != null)
            observationInterpDTs = observationVO.getTheObservationInterpDTCollection();

        Iterator<Object> anIterator = null;

        if (observationInterpDTs != null)
        {

            for (anIterator = observationInterpDTs.iterator();
                 anIterator.hasNext();)
            {
                observationInterpDT = (ObservationInterpDT)anIterator.next();

                if ((interpretationCd != null) &&
                    ((observationInterpDT.getInterpretationCd()).compareTo(
                             interpretationCd) == 0))
                {
                    logger.debug(
                            " ActController.getObservationInterp() got ObservationInterpDT for observationInterp = " +
                            interpretationCd);

                    return observationInterpDT;
                }
                else
                {

                    continue;
                }
            }
        }

        return null;
    }

    /**
     * @roseuid 3BFAF8E101DF
     * @J2EE_METHOD  --  setObservationInterps
     */
    public void setObservationInterps(Collection<Object> observationInterps,
                                      NBSSecurityObj nbsSecurityObj)
                              	throws javax.ejb.EJBException,
								NEDSSConcurrentDataException,

								NEDSSSystemException


    {
    	try{
        ObservationVO observationVO = null;
        ObservationInterpDT observationInterpDT = null;
        Iterator<Object> anIterator = null;

        if (observationInterps != null)
        {
            anIterator = observationInterps.iterator();

            if (anIterator.hasNext())
            {
                observationInterpDT = (ObservationInterpDT)anIterator.next();
                observationVO = this.getObservation(observationInterpDT.getObservationUid(),
                                                    nbsSecurityObj);
                observationVO.setTheObservationInterpDTCollection(
                        observationInterps);
                this.setObservation(observationVO, nbsSecurityObj);
                logger.debug(
                        "Actcontroller.setObservationInterp() has been set for the observationUid = " +
                        (observationInterpDT.getObservationUid()));
            }
        }
    	}
    	catch (Exception e)
    	{
    	logger.fatal("ActControllerEJB.setObservationInterps:" + e.getMessage(), e);
    	throw new javax.ejb.EJBException(e.getMessage(),e);
    	}
    }

    /**
     * @roseuid 3BFAF8E102EE
     * @J2EE_METHOD  --  getObsValueCodeds
     */
    public Collection<Object> getObsValueCodeds(Long observationUid,
                                        NBSSecurityObj nbsSecurityObj)
                                 throws javax.ejb.EJBException
    {
    	try{
        ObservationVO observationVO = null;
        Collection<Object> obsValueCodedDTs = new ArrayList<Object> ();

        if (observationUid != null)
            observationVO = getObservation(observationUid, nbsSecurityObj);

        if (observationVO != null)
            obsValueCodedDTs = observationVO.getTheObsValueCodedDTCollection();

        return obsValueCodedDTs;
    	}
    	catch (Exception e)
    	{
    	logger.fatal("ActControllerEJB.getObsValueCodeds:" + e.getMessage(), e);
    	throw new javax.ejb.EJBException(e.getMessage(),e);
    	}
    }

    /**
     * @roseuid 3BFAF8E2005A
     * @J2EE_METHOD  --  getObsValueCoded
     */
    public ObsValueCodedDT getObsValueCoded(Long observationUid, String code,
                                            NBSSecurityObj nbsSecurityObj)
                                     throws javax.ejb.EJBException
    {
    	try{
        ObservationVO observationVO = null;
        Collection<Object> obsValueCodedDTs = new ArrayList<Object> ();
        ObsValueCodedDT obsValueCodedDT = null;

        if (observationUid != null)
            observationVO = this.getObservation(observationUid, nbsSecurityObj);

        if (observationVO != null)
            obsValueCodedDTs = observationVO.getTheObsValueCodedDTCollection();

        Iterator<Object> anIterator = null;

        if (obsValueCodedDTs != null)
        {

            for (anIterator = obsValueCodedDTs.iterator();
                 anIterator.hasNext();)
            {
                obsValueCodedDT = (ObsValueCodedDT)anIterator.next();

                if ((code != null) &&
                    (obsValueCodedDT.getCode().compareTo(code.trim()) == 0))
                {
                    logger.debug(
                            " ActController.getObsValueCoded() got ObsValueCodedDT for code = " +
                            code);

                    return obsValueCodedDT;
                }
                else
                {

                    continue;
                }
            }
        }

        return null;
    }
    	catch (Exception e)
    	{
    	logger.fatal("ActControllerEJB.getObsValueCoded:" + e.getMessage(), e);
    	throw new javax.ejb.EJBException(e.getMessage(),e);
    	}
    }

    /**
     * @roseuid 3BFAF8E20277
     * @J2EE_METHOD  --  setObsValueCodeds
     */
    public void setObsValueCodeds(Collection<Object> obsValueCodeds,
                                  NBSSecurityObj nbsSecurityObj)
								throws
								javax.ejb.EJBException,
								NEDSSConcurrentDataException,

								NEDSSSystemException


    {
    	try{
        ObservationVO observationVO = null;
        ObsValueCodedDT obsValueCodedDT = null;
        Iterator<Object> anIterator = null;

        if (obsValueCodeds != null)
        {
            anIterator = obsValueCodeds.iterator();

            if (anIterator.hasNext())
            {
                obsValueCodedDT = (ObsValueCodedDT)anIterator.next();
                observationVO = this.getObservation(obsValueCodedDT.getObservationUid(),
                                                    nbsSecurityObj);
                observationVO.setTheObsValueCodedDTCollection(obsValueCodeds);
                this.setObservation(observationVO, nbsSecurityObj);
                logger.debug(
                        "Actcontroller.setObsValueCoded() has been set for the observationUid = " +
                        (obsValueCodedDT.getObservationUid()));
            }
        }
    	}
    	catch (Exception e)
    	{
    	logger.fatal("ActControllerEJB.setObsValueCodeds:" + e.getMessage(), e);
    	throw new javax.ejb.EJBException(e.getMessage(),e);
    	}
    }

    /**
     * @roseuid 3BFAF8E203D5
     * @J2EE_METHOD  --  getObsValueCodedMod
     */
    public ObsValueCodedModDT getObsValueCodedMod(Long observationUid,
                                                  String code,
                                                  String codeModCd,
                                                  NBSSecurityObj nbsSecurityObj)
                                           throws javax.ejb.EJBException
    {
    	try{
        ObservationVO observationVO = null;
        Collection<Object> obsValueCodedModDTs = new ArrayList<Object> ();
        Collection<Object> obsValueCodedDTs = new ArrayList<Object> ();
        ObsValueCodedDT obsValueCodedDT = null;
        ObsValueCodedModDT obsValueCodedModDT = null;

        if (observationUid != null)
            observationVO = this.getObservation(observationUid, nbsSecurityObj);

        if (observationVO != null)
            obsValueCodedDTs = observationVO.getTheObsValueCodedDTCollection();

        Iterator<Object> anIterator1 = null;

        if (obsValueCodedDTs != null)
        {

            for (anIterator1 = obsValueCodedDTs.iterator();
                 anIterator1.hasNext();)
            {
                obsValueCodedDT = (ObsValueCodedDT)anIterator1.next();

                if ((((code != null) || (code != "")) &&
                        ((obsValueCodedDT.getCode()).compareTo(code) == 0)))
                {
                    obsValueCodedModDTs = obsValueCodedDT.getTheObsValueCodedModDTCollection();

                    if (obsValueCodedModDTs != null)
                    {

                        Iterator<Object> anIterator2 = null;

                        for (anIterator2 = obsValueCodedModDTs.iterator();
                             anIterator2.hasNext();)
                        {
                            obsValueCodedModDT = (ObsValueCodedModDT)anIterator2.next();

                            if ((codeModCd != null) &&
                                ((obsValueCodedModDT.getCodeModCd()).compareTo(
                                         codeModCd) == 0))
                            {
                                logger.debug(
                                        " ActController.getObsValueCodedMod() got ObsValueCodedModDT for obsValueCodedMod = " +
                                        code);

                                return obsValueCodedModDT;
                            }
                            else
                            {

                                continue;
                            }
                        }
                    }
                }
                else
                {

                    continue;
                }
            }
        }

        return null;
    	}
    	catch (Exception e)
    	{
    	logger.fatal("ActControllerEJB.getObsValueCodedMod:" + e.getMessage(), e);
    	throw new javax.ejb.EJBException(e.getMessage(),e);
    	}
    }

    /**
     * @roseuid 3BFAF8E302D3
     * @J2EE_METHOD  --  getObsValueCodedMods
     */
    public Collection<Object> getObsValueCodedMods(Long observationUid, String code,
                                           NBSSecurityObj nbsSecurityObj)
                                    throws javax.ejb.EJBException
    {
    	try{
        ObservationVO observationVO = null;
        ObsValueCodedDT obsValueCodedDT = null;
        Collection<Object> obsValueCodedDTs = new ArrayList<Object> ();
        Collection<Object> obsValueCodedModDTs = new ArrayList<Object> ();

        if (observationUid != null)
            observationVO = getObservation(observationUid, nbsSecurityObj);

        if (observationVO != null)
            obsValueCodedDTs = observationVO.getTheObsValueCodedDTCollection();

        Iterator<Object> anIterator = null;

        if (obsValueCodedDTs != null)
        {

            for (anIterator = obsValueCodedDTs.iterator();
                 anIterator.hasNext();)
            {
                obsValueCodedDT = (ObsValueCodedDT)anIterator.next();

                if ((obsValueCodedDT != null) &&
                    ((obsValueCodedDT.getCode()).compareTo(code) == 0))
                {
                    obsValueCodedModDTs = obsValueCodedDT.getTheObsValueCodedModDTCollection();

                    return obsValueCodedModDTs;
                }
                else
                {

                    continue;
                }
            }
        }

        return obsValueCodedModDTs;
    	}
    	catch (Exception e)
    	{
    	logger.fatal("ActControllerEJB.getObsValueCodedMods:" + e.getMessage(), e);
    	throw new javax.ejb.EJBException(e.getMessage(),e);
    	}
    }

    /**
     * @roseuid 3BFAF8E40067
     * @J2EE_METHOD  --  setObsValueCodedMods
     */
    public void setObsValueCodedMods(Collection<Object> obsValueCodedMods,
                                     NBSSecurityObj nbsSecurityObj)
								throws
								javax.ejb.EJBException,
								NEDSSConcurrentDataException,

								NEDSSSystemException


    {
    	try{
        ObservationVO observationVO = null;
        ObsValueCodedDT obsValueCodedDT = new ObsValueCodedDT();
        ObsValueCodedModDT obsValueCodedModDT = null;
        Iterator<Object> anIterator = null;

        if (obsValueCodedMods != null)
        {
            logger.debug("this is inside if of setObsValueCodedMods");
            anIterator = obsValueCodedMods.iterator();

            if (anIterator.hasNext())
            {
                obsValueCodedModDT = (ObsValueCodedModDT)anIterator.next();

                String code = obsValueCodedModDT.getCode();
                logger.debug("\n\n\nString code is :" + code);
                obsValueCodedDT.setCode(code);
                logger.debug(
                        "this is inside if of obsValueCodedDT.setCode(obsValueCodedModDT.getCode()) 1" +
                        obsValueCodedDT.getCode());

                if (obsValueCodedDT.getCode() != null)
                {
                    logger.debug("this is inside if of setObsValueCodedMods 2");

                    if (obsValueCodedModDT.getObservationUid() != null)
                    {
                        obsValueCodedDT.setObservationUid(obsValueCodedModDT.getObservationUid());

                        Long ObservationUID = obsValueCodedModDT.getObservationUid();
                        obsValueCodedDT.setObservationUid(ObservationUID);
                        observationVO = this.getObservation(obsValueCodedModDT.getObservationUid(),
                                                            nbsSecurityObj);

                         obsValueCodedDT.setTheObsValueCodedModDTCollection(
                                obsValueCodedMods);

                        ArrayList<Object>  obsValueCodedColl = new ArrayList<Object> ();
                        obsValueCodedColl.add(obsValueCodedDT);
                        observationVO.setTheObsValueCodedDTCollection(
                                obsValueCodedColl);
                        this.setObservation(observationVO, nbsSecurityObj);
                        logger.debug(
                                "Actcontroller.setObsValueCodedMod() has been set for the observationUid = " +
                                (obsValueCodedModDT.getObservationUid()));
                    }
                }
            }
        }
    	}
    	catch (Exception e)
    	{
    	logger.fatal("ActControllerEJB.setObsValueCodedMods:" + e.getMessage(), e);
    	throw new javax.ejb.EJBException(e.getMessage(),e);
    	}
    }

    /**
     * @roseuid 3BFAF8E401DA
     * @J2EE_METHOD  --  setPublicHealthCase
     */
    public Long setPublicHealthCase(PublicHealthCaseVO publicHealthCaseVO,
                                    NBSSecurityObj nbsSecurityObj)
                             throws javax.ejb.EJBException, NEDSSConcurrentDataException
    {

        Long PubHealthCaseUid = new Long(-1);

        try
        {

            PublicHealthCase publicHealthCase = null;
            NedssUtils nedssUtils = new NedssUtils();
            Object obj = nedssUtils.lookupBean(
                                 JNDINames.PublicHealthCaseEJB);
            logger.debug("PublicHealthCaseEJB lookup = " + obj.toString());

            PublicHealthCaseHome home = (PublicHealthCaseHome)PortableRemoteObject.narrow(
                                                obj,PublicHealthCaseHome.class);
            logger.debug("Found PublicHealthCaseHome: " + home);

            Collection<Object> alpDTCol = publicHealthCaseVO.getTheActivityLocatorParticipationDTCollection();
            Collection<Object> arDTCol = publicHealthCaseVO.getTheActRelationshipDTCollection();
            Collection<Object> pDTCol = publicHealthCaseVO.getTheParticipationDTCollection();
            Collection<Object> col = null;

            if (alpDTCol != null)
            {
                col = this.iterateALPDT(alpDTCol, nbsSecurityObj);
                publicHealthCaseVO.setTheActivityLocatorParticipationDTCollection(
                        col);
            }

            if (arDTCol != null)
            {
                col = this.iterateARDT(arDTCol, nbsSecurityObj);
                publicHealthCaseVO.setTheActRelationshipDTCollection(col);
            }

            if (pDTCol != null)
            {
                col = this.iteratePDT(pDTCol, nbsSecurityObj);
                publicHealthCaseVO.setTheParticipationDTCollection(col);
            }

            if (publicHealthCaseVO.isItNew())
            {
                logger.debug(
                        "Found PublicHealthCase Is It New : " +
                        publicHealthCaseVO.isItNew());
                publicHealthCase = home.create(publicHealthCaseVO);
                PubHealthCaseUid = publicHealthCase.getPublicHealthCaseVO().getThePublicHealthCaseDT()
                                .getPublicHealthCaseUid();
                logger.debug(" PublichealthCaseUID New Is " +
                             PubHealthCaseUid);
            }
            else
            {
                logger.debug(
                        " ActControllerEJB.setPublicHealthCase() PublicHealthCase Updated called" +
                        publicHealthCaseVO.getThePublicHealthCaseDT().getPublicHealthCaseUid());
                publicHealthCase = home.findByPrimaryKey(publicHealthCaseVO.getThePublicHealthCaseDT().getPublicHealthCaseUid());
                publicHealthCase.setPublicHealthCaseVO(publicHealthCaseVO);
                PubHealthCaseUid = publicHealthCaseVO.getThePublicHealthCaseDT()
                  .getPublicHealthCaseUid();
            }
        }
        catch(NEDSSConcurrentDataException ex)
              {
                logger.fatal("The public health case cannot be updated as concurrent access is not allowed!" + ex.getMessage(),ex);
                throw new NEDSSConcurrentDataException(ex.getMessage(),ex);
              }
        catch (Exception e)
        {
            if( e.toString().indexOf("NEDSSConcurrentDataException")!= -1)
            {
                logger.fatal("Throwing NEDSSConcurrentDataException" + e.getMessage(),e);
                throw new NEDSSConcurrentDataException(e.getMessage(),e);
            }
            else
            {
              logger.fatal("ActControllerEJB.setPublicHealthCase exception e = " + e.getMessage(),e);
              e.printStackTrace();
              throw new javax.ejb.EJBException(e.getMessage(),e);
            }
        }

        logger.debug("phcaseuid " + PubHealthCaseUid);

        return PubHealthCaseUid;
    }

    /**
     * @roseuid 3BFAF8E40360
     * @J2EE_METHOD  --  getPublicHealthCase
     */
    public PublicHealthCaseVO getPublicHealthCase(Long publicHealthCaseUid,
                                                  NBSSecurityObj nbsSecurityObj)
                                           throws javax.ejb.EJBException
    {

        PublicHealthCaseVO publicHealthCaseCol = null;

        try
        {

            PublicHealthCase publicHealthCase = null;
            NedssUtils nedssUtils = new NedssUtils();
            Object obj = nedssUtils.lookupBean(
                                 JNDINames.PublicHealthCaseEJB);
            logger.debug("Act Controller lookup = " + obj.toString());

            PublicHealthCaseHome home = (PublicHealthCaseHome)PortableRemoteObject.narrow(
                                                obj,
                                                PublicHealthCaseHome.class);
            logger.debug("Found PublicHealthCaseHome: " + home);

            if (publicHealthCaseUid != null)
                publicHealthCase = home.findByPrimaryKey(publicHealthCaseUid);
            else
                throw new javax.ejb.EJBException("ActControllerEJB.getPublicHealthCase(): publicHealthCaseUid is NULL ");

            publicHealthCaseCol = publicHealthCase.getPublicHealthCaseVO();
            logger.debug(
                    "Act Controller past the find - publicHealthCase = " +
                    publicHealthCase.toString());
            logger.debug(
                    "Act Controller Done find by primary key! for PK: " +
                    publicHealthCase.getPrimaryKey());
        }
        catch (Exception e)
        {
            logger.fatal("ActControllerEJB.getPublicHealthCase(): " + e.getMessage(),e);
            e.printStackTrace();
            throw new javax.ejb.EJBException(e.getMessage(),e);
        }
        boolean isStdHivProgramAreaCode= false;
        PropertyUtil properties = PropertyUtil.getInstance();
		if(properties.getSTDProgramAreas()!=null){
			StringTokenizer st2 = new StringTokenizer(
					properties.getSTDProgramAreas(), ",");
			if (st2 != null) {
				while (st2.hasMoreElements()) {
					if (st2.nextElement().equals(publicHealthCaseCol.getThePublicHealthCaseDT().getProgAreaCd())) {
						isStdHivProgramAreaCode= true;
						break;
					}
				}
			}
		}
        publicHealthCaseCol.getThePublicHealthCaseDT().setStdHivProgramAreaCode(isStdHivProgramAreaCode);

        return publicHealthCaseCol;
    }

    /**
     * @roseuid 3BFAF8E500FF
     * @J2EE_METHOD  --  getPublicHealthCaseInfo
     */
    public PublicHealthCaseDT getPublicHealthCaseInfo(Long publicHealthCaseUid,
                                                      NBSSecurityObj nbsSecurityObj)
                                               throws javax.ejb.EJBException
    {
    	try{
        PublicHealthCaseVO publicHealthCaseVO = null;
        PublicHealthCaseDT publicHealthCaseDT = null;
        publicHealthCaseVO = getPublicHealthCase(publicHealthCaseUid,
                                                 nbsSecurityObj);

        if (publicHealthCaseVO != null)
            publicHealthCaseDT = publicHealthCaseVO.getThePublicHealthCaseDT();

        logger.debug(
                "ActController.getPublicHealthCaseInfo(): PublicHealthCaseDT = " +
                publicHealthCaseDT);

        return publicHealthCaseDT;
    	}
    	catch (Exception e)
    	{
    	logger.fatal("ActControllerEJB.getPublicHealthCaseInfo:" + e.getMessage(), e);
    	throw new javax.ejb.EJBException(e.getMessage(),e);
    	}
    }

    /**
     * @roseuid 3BFAF8E5028F
     * @J2EE_METHOD  --  setPublicHealthCaseInfo
     */
    public void setPublicHealthCaseInfo(PublicHealthCaseDT publicHealthCaseDT,
                                        NBSSecurityObj nbsSecurityObj)
                                 throws javax.ejb.EJBException, NEDSSConcurrentDataException
    {
    	try{
       PublicHealthCaseVO publicHealthCaseVO = null;

        if (publicHealthCaseDT.getPublicHealthCaseUid() != null)
            publicHealthCaseVO = getPublicHealthCase(publicHealthCaseDT.getPublicHealthCaseUid(),
                                                     nbsSecurityObj);

        if (publicHealthCaseVO != null)
        {
            publicHealthCaseVO.setThePublicHealthCaseDT(publicHealthCaseDT);
            publicHealthCaseVO.setItDirty(true);
        }

        if (publicHealthCaseVO == null)
        {
            publicHealthCaseVO = new PublicHealthCaseVO();
            publicHealthCaseVO.setThePublicHealthCaseDT(publicHealthCaseDT);
        }

        this.setPublicHealthCase(publicHealthCaseVO, nbsSecurityObj);
    	}
    	catch (Exception e)
    	{
    	logger.fatal("ActControllerEJB.setPublicHealthCaseInfo:" + e.getMessage(), e);
    	throw new javax.ejb.EJBException(e.getMessage(),e);
    	}
    	}

    /**
     * @roseuid 3BFAF8E60042
     * @J2EE_METHOD  --  getConfirmationMethods
     */
    public Collection<Object> getConfirmationMethods(Long publicHealthCaseUid,
                                             NBSSecurityObj nbsSecurityObj)
                                      throws javax.ejb.EJBException
    {
    	try{
        PublicHealthCaseVO publicHealthCaseVO = null;
        Collection<Object> confirmationMethodDTs = new ArrayList<Object> ();

        if (publicHealthCaseUid != null)
            publicHealthCaseVO = getPublicHealthCase(publicHealthCaseUid,
                                                     nbsSecurityObj);

        if (publicHealthCaseVO != null)
            confirmationMethodDTs = publicHealthCaseVO.getTheConfirmationMethodDTCollection();

        return confirmationMethodDTs;
    }
    catch (Exception e)
    {
    logger.fatal("ActControllerEJB.getConfirmationMethods:" + e.getMessage(), e);
    throw new javax.ejb.EJBException(e.getMessage(),e);
    }
    }

    /**
     * @roseuid 3BFAF8E601DD
     * @J2EE_METHOD  --  getConfirmationMethod
     */
    public ConfirmationMethodDT getConfirmationMethod(Long publicHealthCaseUid,
                                                      String confirmationMethodCd,
                                                      NBSSecurityObj nbsSecurityObj)
                                               throws javax.ejb.EJBException
    {
    	try{
        PublicHealthCaseVO publicHealthCaseVO = null;
        Collection<Object> confirmationMethodDTs = new ArrayList<Object> ();
        ConfirmationMethodDT confirmationMethodDT = null;

        if (publicHealthCaseUid != null)
            publicHealthCaseVO = this.getPublicHealthCase(publicHealthCaseUid,
                                                          nbsSecurityObj);

        if (publicHealthCaseVO != null)
            confirmationMethodDTs = publicHealthCaseVO.getTheConfirmationMethodDTCollection();

        Iterator<Object> anIterator = null;

        if (confirmationMethodDTs != null)
        {

            for (anIterator = confirmationMethodDTs.iterator();
                 anIterator.hasNext();)
            {
                confirmationMethodDT = (ConfirmationMethodDT)anIterator.next();

                if ((confirmationMethodCd != null) &&
                    ((confirmationMethodDT.getConfirmationMethodCd()).compareTo(
                             confirmationMethodCd) == 0))
                {
                    logger.debug(
                            " ActController.getConfirmationMethod() got ConfirmationMethodDT for confirmationMethod = " +
                            confirmationMethodCd);

                    return confirmationMethodDT;
                }
                else
                {

                    continue;
                }
            }
        }

        return null;
    	}
    	catch (Exception e)
    	{
    	logger.fatal("ActControllerEJB.getConfirmationMethod:" + e.getMessage(), e);
    	throw new javax.ejb.EJBException(e.getMessage(),e);
    	}
    }

    /**
     * @roseuid 3BFAF8E70089
     * @J2EE_METHOD  --  setConfirmationMethods
     */
    public void setConfirmationMethods(Collection<Object> confirmationMethods,
                                       NBSSecurityObj nbsSecurityObj)
                                throws javax.ejb.EJBException, NEDSSConcurrentDataException
    {
    	try{
        PublicHealthCaseVO publicHealthCaseVO = null;
        ConfirmationMethodDT confirmationMethodDT = null;
        Iterator<Object> anIterator = null;

        if (confirmationMethods != null)
        {
            anIterator = confirmationMethods.iterator();

            if (anIterator.hasNext())
            {
                confirmationMethodDT = (ConfirmationMethodDT)anIterator.next();
                publicHealthCaseVO = this.getPublicHealthCase(confirmationMethodDT.getPublicHealthCaseUid(),
                                                              nbsSecurityObj);
                publicHealthCaseVO.setTheConfirmationMethodDTCollection(
                        confirmationMethods);
                this.setPublicHealthCase(publicHealthCaseVO, nbsSecurityObj);
                logger.debug(
                        "Actcontroller.setConfirmationMethod() has been set for the publicHealthCaseUid = " +
                        (confirmationMethodDT.getPublicHealthCaseUid()));
            }
        }
    	}
    	catch (Exception e)
    	{
    	logger.fatal("ActControllerEJB.setConfirmationMethods:" + e.getMessage(), e);
    	throw new javax.ejb.EJBException(e.getMessage(),e);
    	}
    }

    /**
     * @roseuid 3BFAF8E7022E
     * @J2EE_METHOD  --  setNotification
     */
    public Long setNotification(NotificationVO notificationVO,
                                NBSSecurityObj nbsSecurityObj)
                         throws javax.ejb.EJBException,
                                NEDSSConcurrentDataException
    {

        Long notificationUid = new Long(-1);

        try
        {

            Notification notification = null;
            NedssUtils nedssUtils = new NedssUtils();
            Object obj = nedssUtils.lookupBean(JNDINames.NotificationEJB);
            logger.debug("NotificationEJB lookup = " + obj.toString());

            NotificationHome home = (NotificationHome)PortableRemoteObject.narrow(
                                            obj,NotificationHome.class);
            logger.debug("Found NotificationHome: " + home);

            Collection<Object> alpDTCol = notificationVO.getTheActivityLocatorParticipationDTCollection();
            Collection<Object> arDTCol = notificationVO.getTheActRelationshipDTCollection();
            Collection<Object> pDTCol = notificationVO.getTheParticipationDTCollection();
            Collection<Object> col = null;

            if (alpDTCol != null)
            {
                col = this.iterateALPDT(alpDTCol, nbsSecurityObj);
                notificationVO.setTheActivityLocatorParticipationDTCollection(
                        col);
            }

            if (arDTCol != null)
            {
                col = this.iterateARDT(arDTCol, nbsSecurityObj);
                notificationVO.setTheActRelationshipDTCollection(col);
            }

            if (pDTCol != null)
            {
                col = this.iteratePDT(pDTCol, nbsSecurityObj);
                notificationVO.setTheParticipationDTCollection(col);
            }

            if (notificationVO.isItNew())
            {
                notification = home.create(notificationVO);
                logger.debug(
                        " ActControllerEJB.setNotification() Notification Created");
                notificationUid = notification.getNotificationVO().getTheNotificationDT()
                            .getNotificationUid();
            }
            else
            {
                notification = home.findByPrimaryKey(notificationVO.getTheNotificationDT().getNotificationUid());
                notification.setNotificationVO(notificationVO);
                notificationUid = notificationVO.getTheNotificationDT().getNotificationUid();
                logger.debug(
                        " ActControllerEJB.setNotification() Notification Updated");
            }
        }catch(NEDSSConcurrentDataException ex)
              {
                logger.fatal("The entity cannot be updated as concurrent access is not allowed!" + ex.getMessage(),ex);
                throw new NEDSSConcurrentDataException(ex.getMessage(),ex);
              }
        catch (Exception e)
        {
            if( e.toString().indexOf("NEDSSConcurrentDataException")!= -1)
            {
                logger.fatal("Throwing NEDSSConcurrentDataException" + e.getMessage(),e);
                throw new NEDSSConcurrentDataException(e.getMessage(),e);
            }
            else
            {
                logger.fatal("ActControllerEJB.setNotification: " + e.getMessage(),e);
                e.printStackTrace();
                throw new javax.ejb.EJBException(e.getMessage(),e);
            }
        }
        return notificationUid;
    }

    /**
     * @roseuid 3BFAF8E703E7
     * @J2EE_METHOD  --  getNotification
     */
    public NotificationVO getNotification(Long notificationUid,
                                          NBSSecurityObj nbsSecurityObj)
                                   throws javax.ejb.EJBException
    {

        NotificationVO notificationCol = null;

        try
        {

            Notification notification = null;
            NedssUtils nedssUtils = new NedssUtils();
            Object obj = nedssUtils.lookupBean(JNDINames.NotificationEJB);
            logger.debug("Act Controller lookup = " + obj.toString());

            NotificationHome home = (NotificationHome)PortableRemoteObject.narrow(
                                            obj, NotificationHome.class);
            logger.debug("Found NotificationHome: " + home);

            if (notificationUid != null)
                notification = home.findByPrimaryKey(notificationUid);
            else
                throw new javax.ejb.EJBException("ActControllerEJB.getNotification(): notificationUid is NULL ");

            notificationCol = notification.getNotificationVO();
            logger.debug(
                    "Act Controller past the find - notification = " +
                    notification.toString());
            logger.debug(
                    "Act Controller Done find by primary key! for PK: " +
                    notification.getPrimaryKey());
        }
        catch (Exception e)
        {
            logger.fatal("ActControllerEJB.getNotification(): " + e.getMessage(),e);
            e.printStackTrace();
            throw new javax.ejb.EJBException(e.getMessage(),e);
        }

        return notificationCol;
    }

    /**
     * @roseuid 3BFAF8E801B7
     * @J2EE_METHOD  --  getNotificationInfo
     */
    public NotificationDT getNotificationInfo(Long notificationUid,
                                              NBSSecurityObj nbsSecurityObj)
                                       throws javax.ejb.EJBException
    {
    	try{
        NotificationVO notificationVO = null;
        NotificationDT notificationDT = null;
        notificationVO = getNotification(notificationUid, nbsSecurityObj);

        if (notificationVO != null)
            notificationDT = notificationVO.getTheNotificationDT();

        logger.debug(
                "ActController.getNotificationInfo(): NotificationDT = " +
                notificationDT);

        return notificationDT;
    	}
    	catch (Exception e)
    	{
    	logger.fatal("ActControllerEJB.getNotificationInfo:" + e.getMessage(), e);
    	throw new javax.ejb.EJBException(e.getMessage(),e);
    	}
    }

    /**
     * @roseuid 3BFAF8E80370
     * @J2EE_METHOD  --  setNotificationInfo
     */
    public void setNotificationInfo(NotificationDT notificationDT,
                                    NBSSecurityObj nbsSecurityObj)
                             throws javax.ejb.EJBException, NEDSSConcurrentDataException
    {
    	try{
        NotificationVO notificationVO = null;

        if (notificationDT.getNotificationUid() != null)
            notificationVO = getNotification(notificationDT.getNotificationUid(),
                                             nbsSecurityObj);

        if (notificationVO != null)
        {
            notificationVO.setTheNotificationDT(notificationDT);
            notificationVO.setItDirty(true);
        }

        if (notificationVO == null)
        {
            notificationVO = new NotificationVO();
            notificationVO.setTheNotificationDT(notificationDT);
        }

        this.setNotification(notificationVO, nbsSecurityObj);
    	}
    	catch (Exception e)
    	{
    	logger.fatal("ActControllerEJB.setNotificationInfo:" + e.getMessage(), e);
    	throw new javax.ejb.EJBException(e.getMessage(),e);
    	}
    }



    /**
     * @roseuid 3BFAF8E9014B
     * @J2EE_METHOD  --  setIntervention
     */
    public Long setIntervention(InterventionVO interventionVO,
                                NBSSecurityObj nbsSecurityObj)
                         throws javax.ejb.EJBException,
                                NEDSSConcurrentDataException,
                                NEDSSSystemException
    {

        Long interventionUid = new Long(-1);

        Intervention intervention = null;
        NedssUtils nedssUtils = new NedssUtils();
        Object obj = nedssUtils.lookupBean(JNDINames.InterventionEJB);
        logger.debug("InterventionEJB lookup = " + obj.toString());

        InterventionHome home = (InterventionHome)PortableRemoteObject.narrow(
                                        obj,InterventionHome.class);
        logger.debug("Found InterventionHome: " + home);

        Collection<Object> alpDTCol = interventionVO.getTheActivityLocatorParticipationDTCollection();
        Collection<Object> arDTCol = interventionVO.getTheActRelationshipDTCollection();
        Collection<Object> pDTCol = interventionVO.getTheParticipationDTCollection();
        Collection<Object> col = null;

        if (alpDTCol != null)
        {
            col = this.iterateALPDT(alpDTCol, nbsSecurityObj);
            interventionVO.setTheActivityLocatorParticipationDTCollection(col);
        }

        if (arDTCol != null)
        {
            col = this.iterateARDT(arDTCol, nbsSecurityObj);
            interventionVO.setTheActRelationshipDTCollection(col);
        }

        if (pDTCol != null)
        {
            col = this.iteratePDT(pDTCol, nbsSecurityObj);
            interventionVO.setTheParticipationDTCollection(col);
        }

        if (interventionVO.isItNew())
        {
            logger.debug("testing intervention in create");
            try
            {
            intervention = home.create(interventionVO);
            interventionUid = intervention.getInterventionVO().getTheInterventionDT()
                        .getInterventionUid();
            }
            catch(Exception ex)
            {
            	logger.fatal("intervention.getInterventionVO().getTheInterventionDT().getInterventionUid(): ", ex);
              throw new NEDSSSystemException(ex.getMessage(),ex);
            }
            logger.debug(
                    " ActControllerEJB.setIntervention() Intervention Created");
        }
        else
        {
            logger.debug("testing intervention in set");
            try
            {
                intervention = home.findByPrimaryKey(interventionVO.getTheInterventionDT().getInterventionUid());
            }
            catch(Exception ex)
            {
            	logger.fatal("intervention.getInterventionVO().getTheInterventionDT().getInterventionUid(): ", ex);
              throw new NEDSSSystemException(ex.getMessage(),ex);
            }

            try
            {
                intervention.setInterventionVO(interventionVO);
            }
            catch (NEDSSConcurrentDataException ndcex)
            {
                logger.fatal(
                        "got the NEDSSConcurrentDataException in actControllerEJB" + ndcex.getMessage(),ndcex);
                throw new NEDSSConcurrentDataException(ndcex.getMessage(),ndcex);
            }
            catch(Exception e)
            {
              if( e.toString().indexOf("NEDSSConcurrentDataException")!= -1)
              {
                  logger.fatal("Throwing NEDSSConcurrentDataException" + e.getMessage(),e);
                  throw new NEDSSConcurrentDataException( e.getMessage(),e);
              }
              else
              {
                logger.fatal("ActControllerEJB.setInterventionVO exception e = " + e + "\n", e);
                logger.error("Throwing generic exception");
                throw new javax.ejb.EJBException(e.getMessage(),e);
              }
            }

            interventionUid = interventionVO.getTheInterventionDT().getInterventionUid();
            logger.debug(
                    " ActControllerEJB.setIntervention() Intervention Updated");
        }

        logger.debug("the intervention Uid is " + interventionUid);

        return interventionUid;
    }

    /**
     * @roseuid 3BFAF8E90317
     * @J2EE_METHOD  --  getIntervention
     */
    public InterventionVO getIntervention(Long interventionUid,
                                          NBSSecurityObj nbsSecurityObj)
                                   throws javax.ejb.EJBException
    {

        InterventionVO interventionCol = null;

        try
        {

            Intervention intervention = null;
            NedssUtils nedssUtils = new NedssUtils();
            Object obj = nedssUtils.lookupBean(JNDINames.InterventionEJB);
            logger.debug("Act Controller lookup = " + obj.toString());

            InterventionHome home = (InterventionHome)PortableRemoteObject.narrow(
                                            obj, InterventionHome.class);
            logger.debug("Found InterventionHome: " + home);

            if (interventionUid != null)
                intervention = home.findByPrimaryKey(interventionUid);
            else
                throw new javax.ejb.EJBException("ActControllerEJB.getIntervention(): interventionUid is NULL ");

            interventionCol = intervention.getInterventionVO();
            logger.debug(
                    "Act Controller past the find - intervention = " +
                    intervention.toString());
            logger.debug(
                    "Act Controller Done find by primary key! for PK: " +
                    intervention.getPrimaryKey());
        }
        catch (Exception e)
        {
            logger.fatal("ActControllerEJB.getIntervention(): " + e.getMessage(),e);
            e.printStackTrace();
            throw new javax.ejb.EJBException(e.getMessage(),e);
        }

        return interventionCol;
    }

    /**
     * @roseuid 3BFAF8EA0106
     * @J2EE_METHOD  --  getInterventionInfo
     */
    public InterventionDT getInterventionInfo(Long interventionUid,
                                              NBSSecurityObj nbsSecurityObj)
                                       throws javax.ejb.EJBException
    {

        InterventionVO interventionVO = null;
        InterventionDT interventionDT = null;
        interventionVO = getIntervention(interventionUid, nbsSecurityObj);

        if (interventionVO != null)
            interventionDT = interventionVO.getTheInterventionDT();

        logger.debug(
                "ActController.getInterventionInfo(): InterventionDT = " +
                interventionDT);

        return interventionDT;
    }

    /**
     * @roseuid 3BFAF8EA02E7
     * @J2EE_METHOD  --  setInterventionInfo
     */
    public void setInterventionInfo(InterventionDT interventionDT,
                                    NBSSecurityObj nbsSecurityObj)
                             throws javax.ejb.EJBException,
                                    NEDSSConcurrentDataException,

                                    NEDSSSystemException
    {
    	try{
       InterventionVO interventionVO = null;

        if (interventionDT.getInterventionUid() != null)
        {
            logger.debug("this is if got getInterventionUid ");
            interventionVO = getIntervention(interventionDT.getInterventionUid(),
                                             nbsSecurityObj);
        }

        if (interventionVO != null)
        {
            logger.debug("this is if got interventionVO!= null ");
            interventionVO.setTheInterventionDT(interventionDT);
            interventionVO.setItDirty(true);
        }

        if (interventionVO == null)
        {
            interventionVO = new InterventionVO();
            logger.debug("this is if got interventionVO == null ");
            interventionVO.setTheInterventionDT(interventionDT);
        }

        this.setIntervention(interventionVO, nbsSecurityObj);
    }
    catch (Exception e)
    {
    logger.fatal("ActControllerEJB.setInterventionInfo:" + e.getMessage(), e);
    throw new javax.ejb.EJBException(e.getMessage(),e);
    }
    }

    /**
     * @roseuid 3BFAF8EB00DF
     * @J2EE_METHOD  --  setReferral
     */
    public Long setReferral(ReferralVO referralVO, NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException,NEDSSConcurrentDataException
      {
        Long referralUid = new Long(-1);
        try
	    {
	        Referral referral = null;
            NedssUtils nedssUtils = new NedssUtils();
            Object obj = nedssUtils.lookupBean(JNDINames.ReferralEJB);
            logger.debug("ReferralEJB lookup = " + obj.toString());
            ReferralHome home =(ReferralHome)PortableRemoteObject.narrow(obj, ReferralHome.class);
            logger.debug("Found ReferralHome: " + home);

             Collection<Object> alpDTCol =  referralVO.getTheActivityLocatorParticipationDTCollection();
             Collection<Object> arDTCol = referralVO.getTheActRelationshipDTCollection();
             Collection<Object> pDTCol = referralVO.getTheParticipationDTCollection();
             Collection<Object> col = null;
             if(alpDTCol != null)
             {
               col = this.iterateALPDT(alpDTCol,nbsSecurityObj);
               referralVO.setTheActivityLocatorParticipationDTCollection(col);
             }
             if(arDTCol != null)
             {
               col = this.iterateARDT(arDTCol,nbsSecurityObj);
               referralVO.setTheActRelationshipDTCollection(col);
             }
             if(pDTCol != null)
             {
               col = this.iteratePDT(pDTCol,nbsSecurityObj);
               referralVO.setTheParticipationDTCollection(col);
             }

		    if(referralVO.isItNew())
		    {
                        logger.debug("testing referral in create");
                        referral = home.create(referralVO);
                        referralUid =  referral.getReferralVO().getTheReferralDT().getReferralUid();
                        logger.debug(" ActControllerEJB.setReferral() Referral Created");
		    }
		    else
		    {
                        logger.debug("testing referral in set");
                        referral = home.findByPrimaryKey(referralVO.getTheReferralDT().getReferralUid());
                        try
                        {
                            referral.setReferralVO(referralVO);
                        }
                        catch(NEDSSConcurrentDataException ndcex)
                        {
                            logger.fatal("got the NEDSSConcurrentDataException in actControllerEJB"  + ndcex.getMessage(),ndcex);
                            throw new NEDSSConcurrentDataException(ndcex.getMessage(),ndcex);
                        }
                        referralUid = referralVO.getTheReferralDT().getReferralUid();
                        logger.debug(" ActControllerEJB.setReferral() Referral Updated");
	           }
            }
	    catch (Exception e)
	    {
              if( e.toString().indexOf("NEDSSConcurrentDataException")!= -1)
              {
                  logger.fatal("Throwing NEDSSConcurrentDataException" + e.getMessage(),e);
                  throw new NEDSSConcurrentDataException(e.getMessage(),e);
              }
              else
              {
                   logger.fatal("ActControllerEJB.setReferral: "  + e.getMessage(),e );
                   e.printStackTrace();
                  throw new javax.ejb.EJBException(e.getMessage(),e);
              }
	    }
        logger.debug("the referralUid is " + referralUid);
	    return referralUid;
    }

    /**
     * @roseuid 3BFAF8EB02CA
     * @J2EE_METHOD  --  getReferral
     */
    public ReferralVO getReferral(Long referralUid,
                                  NBSSecurityObj nbsSecurityObj)
                           throws javax.ejb.EJBException
    {

        ReferralVO referralCol = null;

        try
        {

            Referral referral = null;
            NedssUtils nedssUtils = new NedssUtils();
            Object obj = nedssUtils.lookupBean(JNDINames.ReferralEJB);
            logger.debug("Act Controller lookup = " + obj.toString());

            ReferralHome home = (ReferralHome)PortableRemoteObject.narrow(obj,
                                                                          ReferralHome.class);
            logger.debug("Found ReferralHome: " + home);

            if (referralUid != null)
                referral = home.findByPrimaryKey(referralUid);
            else
                throw new javax.ejb.EJBException("ActControllerEJB.getReferral(): referralUid is NULL ");

            referralCol = referral.getReferralVO();
            logger.debug(
                    "Act Controller past the find - referral = " +
                    referral.toString());
            logger.debug(
                    "Act Controller Done find by primary key! for PK: " +
                    referral.getPrimaryKey());
        }
        catch (Exception e)
        {
            logger.fatal("ActControllerEJB.getReferral(): " + e.getMessage(),e);
            e.printStackTrace();
            throw new javax.ejb.EJBException(e.getMessage(),e);
        }

        return referralCol;
    }

    /**
     * @roseuid 3BFAF8EC00E1
     * @J2EE_METHOD  --  getReferralInfo
     */
    public ReferralDT getReferralInfo(Long referralUid,
                                      NBSSecurityObj nbsSecurityObj)
                               throws javax.ejb.EJBException
    {
    	try{
        ReferralVO referralVO = null;
        ReferralDT referralDT = null;
        referralVO = getReferral(referralUid, nbsSecurityObj);

        if (referralVO != null)
            referralDT = referralVO.getTheReferralDT();

        logger.debug(
                "ActController.getReferralInfo(): ReferralDT = " +
                referralDT);

        return referralDT;
    }
    catch (Exception e)
    {
    logger.fatal("ActControllerEJB.getReferralInfo:" + e.getMessage(), e);
    throw new javax.ejb.EJBException(e.getMessage(),e);
    }
    }

    /**
     * @roseuid 3BFAF8EC02D5
     * @J2EE_METHOD  --  setReferralInfo
     */
    public void setReferralInfo(ReferralDT referralDT,
                                NBSSecurityObj nbsSecurityObj)
                         throws javax.ejb.EJBException,NEDSSConcurrentDataException
    {
    	try{
       ReferralVO referralVO = null;

        if (referralDT.getReferralUid() != null)
            referralVO = getReferral(referralDT.getReferralUid(),
                                     nbsSecurityObj);

        if (referralVO != null)
        {
            referralVO.setTheReferralDT(referralDT);
            referralVO.setItDirty(true);
        }

        if (referralVO == null)
        {
            referralVO = new ReferralVO();
            referralVO.setTheReferralDT(referralDT);
        }

        this.setReferral(referralVO, nbsSecurityObj);
    }
    catch (Exception e)
    {
    logger.fatal("ActControllerEJB.setReferralInfo:" + e.getMessage(), e);
    throw new javax.ejb.EJBException(e.getMessage(),e);
    }
    }

    /**
     * @roseuid 3BFAF8ED00F6
     * @J2EE_METHOD  --  setPatientEncounter
     */
    public Long setPatientEncounter(PatientEncounterVO patientEncounterVO,
                                    NBSSecurityObj nbsSecurityObj)
                             throws javax.ejb.EJBException, NEDSSConcurrentDataException
    {

        Long patientEncounterUid = new Long(-1);

        try
        {

            PatientEncounter patientEncounter = null;
            NedssUtils nedssUtils = new NedssUtils();
            Object obj = nedssUtils.lookupBean(
                                 JNDINames.PatientEncounterEJB);
            logger.debug("PatientEncounterEJB lookup = " + obj.toString());

            PatientEncounterHome home = (PatientEncounterHome)PortableRemoteObject.narrow(
                                                obj,PatientEncounterHome.class);
            logger.debug("Found PatientEncounterHome: " + home);

            Collection<Object> alpDTCol = patientEncounterVO.getTheActivityLocatorParticipationDTCollection();
            Collection<Object> arDTCol = patientEncounterVO.getTheActRelationshipDTCollection();
            Collection<Object> pDTCol = patientEncounterVO.getTheParticipationDTCollection();
            Collection<Object> col = null;

            if (alpDTCol != null)
            {
                col = this.iterateALPDT(alpDTCol, nbsSecurityObj);
                patientEncounterVO.setTheActivityLocatorParticipationDTCollection(
                        col);
            }

            if (arDTCol != null)
            {
                col = this.iterateARDT(arDTCol, nbsSecurityObj);
                patientEncounterVO.setTheActRelationshipDTCollection(col);
            }

            if (pDTCol != null)
            {
                col = this.iteratePDT(pDTCol, nbsSecurityObj);
                patientEncounterVO.setTheParticipationDTCollection(col);
            }

            if (patientEncounterVO.isItNew())
            {
                patientEncounter = home.create(patientEncounterVO);
                logger.debug(
                        " ActControllerEJB.setPatientEncounter() PatientEncounter Created");
                patientEncounterUid = patientEncounter.getPatientEncounterVO().getThePatientEncounterDT()
                                .getPatientEncounterUid();
            }
            else
            {
                patientEncounter = home.findByPrimaryKey(patientEncounterVO.getThePatientEncounterDT().getPatientEncounterUid());
                patientEncounter.setPatientEncounterVO(patientEncounterVO);
                patientEncounterUid = patientEncounterVO.getThePatientEncounterDT()
                  .getPatientEncounterUid();
                logger.debug(
                        " ActControllerEJB.setPatientEncounter() PatientEncounter Updated");
            }
        }
        catch (Exception e)
        {
            logger.fatal("ActControllerEJB.setPatientEncounter: " + e.getMessage(),e);
            e.printStackTrace();
            throw new javax.ejb.EJBException(e.getMessage(),e);
        }

        logger.debug("the patientEncounterUid is " + patientEncounterUid);

        return patientEncounterUid;
    }

    /**
     * @roseuid 3BFAF8ED02FF
     * @J2EE_METHOD  --  getPatientEncounter
     */
    public PatientEncounterVO getPatientEncounter(Long patientEncounterUid,
                                                  NBSSecurityObj nbsSecurityObj)
                                           throws javax.ejb.EJBException
    {

        PatientEncounterVO patientEncounterCol = null;

        try
        {

            PatientEncounter patientEncounter = null;
            NedssUtils nedssUtils = new NedssUtils();
            Object obj = nedssUtils.lookupBean(
                                 JNDINames.PatientEncounterEJB);
            logger.debug("Act Controller lookup = " + obj.toString());

            PatientEncounterHome home = (PatientEncounterHome)PortableRemoteObject.narrow(
                                                obj,
                                                PatientEncounterHome.class);
            logger.debug("Found PatientEncounterHome: " + home);

            if (patientEncounterUid != null)
                patientEncounter = home.findByPrimaryKey(patientEncounterUid);
            else
                throw new javax.ejb.EJBException("ActControllerEJB.getPatientEncounter(): patientEncounterUid is NULL ");

            patientEncounterCol = patientEncounter.getPatientEncounterVO();
            logger.debug(
                    "Act Controller past the find - patientEncounter = " +
                    patientEncounter.toString());
            logger.debug(
                    "Act Controller Done find by primary key! for PK: " +
                    patientEncounter.getPrimaryKey());
        }
        catch (Exception e)
        {
            logger.fatal("ActControllerEJB.getPatientEncounter(): " + e.getMessage(),e);
            e.printStackTrace();
            throw new javax.ejb.EJBException(e.getMessage(),e);
        }

        return patientEncounterCol;
    }

    /**
     * @roseuid 3BFAF8EE012A
     * @J2EE_METHOD  --  getPatientEncounterInfo
     */
    public PatientEncounterDT getPatientEncounterInfo(Long patientEncounterUid,
                                                      NBSSecurityObj nbsSecurityObj)
                                               throws javax.ejb.EJBException
    {
    	try{
        PatientEncounterVO patientEncounterVO = null;
        PatientEncounterDT patientEncounterDT = null;
        patientEncounterVO = getPatientEncounter(patientEncounterUid,
                                                 nbsSecurityObj);

        if (patientEncounterVO != null)
            patientEncounterDT = patientEncounterVO.getThePatientEncounterDT();

        logger.debug(
                "ActController.getPatientEncounterInfo(): PatientEncounterDT = " +
                patientEncounterDT);

        return patientEncounterDT;
    }
    catch (Exception e)
    {
    logger.fatal("ActControllerEJB.getPatientEncounterInfo:" + e.getMessage(), e);
    throw new javax.ejb.EJBException(e.getMessage(),e);
    }
    }

    /**
     * @roseuid 3BFAF8EE0347
     * @J2EE_METHOD  --  setPatientEncounterInfo
     */
    public void setPatientEncounterInfo(PatientEncounterDT patientEncounterDT,
                                        NBSSecurityObj nbsSecurityObj)
                                 throws javax.ejb.EJBException, NEDSSConcurrentDataException
    {
    	try{
        PatientEncounterVO patientEncounterVO = null;

        if (patientEncounterDT.getPatientEncounterUid() != null)
            patientEncounterVO = getPatientEncounter(patientEncounterDT.getPatientEncounterUid(),
                                                     nbsSecurityObj);

        if (patientEncounterVO != null)
        {
            patientEncounterVO.setThePatientEncounterDT(patientEncounterDT);
            patientEncounterVO.setItDirty(true);
        }

        if (patientEncounterVO == null)
        {
            patientEncounterVO = new PatientEncounterVO();
            patientEncounterVO.setThePatientEncounterDT(patientEncounterDT);
        }

        this.setPatientEncounter(patientEncounterVO, nbsSecurityObj);
    }
    catch (Exception e)
    {
    logger.fatal("ActControllerEJB.setPatientEncounterInfo:" + e.getMessage(), e);
    throw new javax.ejb.EJBException(e.getMessage(),e);
    }
    }

    /**
     * @roseuid 3BFAF8EF017B
     * @J2EE_METHOD  --  setClinicalDocument
     */
    public Long setClinicalDocument(ClinicalDocumentVO clinicalDocumentVO,
                                    NBSSecurityObj nbsSecurityObj)
                             throws javax.ejb.EJBException, NEDSSConcurrentDataException
    {
        Long clinicalDocumentUid = new Long(-1);
        try
        {
            ClinicalDocument clinicalDocument = null;
            NedssUtils nedssUtils = new NedssUtils();
            Object obj = nedssUtils.lookupBean(
                                 JNDINames.ClinicalDocumentEJB);
            logger.debug("ClinicalDocumentEJB lookup = " + obj.toString());

            ClinicalDocumentHome home = (ClinicalDocumentHome)PortableRemoteObject.narrow(
                                                obj,ClinicalDocumentHome.class);
            logger.debug("Found ClinicalDocumentHome: " + home);
            logger.debug(
                    "Got the clinicalDocumentVO.IsItNew(): " +
                    clinicalDocumentVO.isItNew());

            Collection<Object> alpDTCol = clinicalDocumentVO.getTheActivityLocatorParticipationDTCollection();
            Collection<Object> arDTCol = clinicalDocumentVO.getTheActRelationshipDTCollection();
            Collection<Object> pDTCol = clinicalDocumentVO.getTheParticipationDTCollection();
            Collection<Object> col = null;

            if (alpDTCol != null)
            {
                col = this.iterateALPDT(alpDTCol, nbsSecurityObj);
                clinicalDocumentVO.setTheActivityLocatorParticipationDTCollection(
                        col);
            }

            if (arDTCol != null)
            {
                col = this.iterateARDT(arDTCol, nbsSecurityObj);
                clinicalDocumentVO.setTheActRelationshipDTCollection(col);
            }

            if (pDTCol != null)
            {
                col = this.iteratePDT(pDTCol, nbsSecurityObj);
                clinicalDocumentVO.setTheParticipationDTCollection(col);
            }

            if (clinicalDocumentVO.isItNew())
            {
                logger.debug(
                        "Got the clinicalDocumentVO.IsItNew() inside if of create: " +
                        clinicalDocumentVO.isItNew());
                clinicalDocument = home.create(clinicalDocumentVO);
                logger.debug(
                        " ActControllerEJB.setClinicalDocument() ClinicalDocument Created");
                clinicalDocumentUid = clinicalDocument.getClinicalDocumentVO().getTheClinicalDocumentDT()
                                .getClinicalDocumentUid();
            }
            else
            {
                logger.debug(
                        "Got inside else of clinicalDocumentVO.IsItNew(): " +
                        clinicalDocumentVO.isItNew());
                clinicalDocument = home.findByPrimaryKey(clinicalDocumentVO.getTheClinicalDocumentDT().getClinicalDocumentUid());
                clinicalDocument.setClinicalDocumentVO(clinicalDocumentVO);
                clinicalDocumentUid = clinicalDocumentVO.getTheClinicalDocumentDT()
                  .getClinicalDocumentUid();
                logger.debug(
                        " ActControllerEJB.setClinicalDocument() ClinicalDocument Updated");
            }
        }
	catch(NEDSSConcurrentDataException ex)
              {
                logger.fatal("The entity cannot be updated as concurrent access is not allowed!" + ex.getMessage(),ex);
                throw new NEDSSConcurrentDataException(ex.getMessage(),ex);
              }
        catch (Exception e)
        {
             if( e.toString().indexOf("NEDSSConcurrentDataException")!= -1)
            {
                logger.fatal("Throwing NEDSSConcurrentDataException" + e.getMessage(),e);
                throw new NEDSSConcurrentDataException( e.getMessage(),e);
            }
            else
            {
              logger.fatal("ActControllerEJB.setClinicalDocument: " + e.getMessage(),e);
              e.printStackTrace();
              throw new javax.ejb.EJBException(e.getMessage(),e);
            }
        }

        logger.debug("the clinical documentUId is " + clinicalDocumentUid);

        return clinicalDocumentUid;
    }

    /**
     * @roseuid 3BFAF8EF038E
     * @J2EE_METHOD  --  getClinicalDocument
     */
    public ClinicalDocumentVO getClinicalDocument(Long clinicalDocumentUid,
                                                  NBSSecurityObj nbsSecurityObj)
                                           throws javax.ejb.EJBException
    {

        ClinicalDocumentVO clinicalDocumentCol = null;

        try
        {

            ClinicalDocument clinicalDocument = null;
            NedssUtils nedssUtils = new NedssUtils();
            Object obj = nedssUtils.lookupBean(
                                 JNDINames.ClinicalDocumentEJB);
            logger.debug("Act Controller lookup = " + obj.toString());

            ClinicalDocumentHome home = (ClinicalDocumentHome)PortableRemoteObject.narrow(
                                                obj,
                                                ClinicalDocumentHome.class);
            logger.debug("Found ClinicalDocumentHome: " + home);

            if (clinicalDocumentUid != null)
                clinicalDocument = home.findByPrimaryKey(clinicalDocumentUid);
            else
                throw new javax.ejb.EJBException(" ActControllerEJB.getClinicalDocument: clinicalDocumentUid is NULL ");

            clinicalDocumentCol = clinicalDocument.getClinicalDocumentVO();
            logger.debug(
                    "Act Controller past the find - clinicalDocument = " +
                    clinicalDocument.toString());
            logger.debug(
                    "Act Controller Done find by primary key! for PK: " +
                    clinicalDocument.getPrimaryKey());
        }
        catch (Exception e)
        {
            logger.fatal("ActControllerEJB.getClinicalDocument(): " + e.getMessage(),e);
            e.printStackTrace();
            throw new javax.ejb.EJBException(e.getMessage(),e);
        }

        return clinicalDocumentCol;
    }

    /**
     * @roseuid 3BFAF8F001CD
     * @J2EE_METHOD  --  getClinicalDocumentInfo
     */
    public ClinicalDocumentDT getClinicalDocumentInfo(Long clinicalDocumentUid,
                                                      NBSSecurityObj nbsSecurityObj)
                                               throws javax.ejb.EJBException
    {

        ClinicalDocumentVO clinicalDocumentVO = null;
        ClinicalDocumentDT clinicalDocumentDT = null;
        clinicalDocumentVO = getClinicalDocument(clinicalDocumentUid,
                                                 nbsSecurityObj);

        if (clinicalDocumentVO != null)
            clinicalDocumentDT = clinicalDocumentVO.getTheClinicalDocumentDT();

        logger.debug(
                "ActController.getClinicalDocumentInfo(): ClinicalDocumentDT = " +
                clinicalDocumentDT);

        return clinicalDocumentDT;
    }

    /**
     * @roseuid 3BFAF8F10016
     * @J2EE_METHOD  --  setClinicalDocumentInfo
     */
    public void setClinicalDocumentInfo(ClinicalDocumentDT clinicalDocumentDT,
                                        NBSSecurityObj nbsSecurityObj)
                                 throws javax.ejb.EJBException, NEDSSConcurrentDataException
    {
    	try{
        ClinicalDocumentVO clinicalDocumentVO = null;

        if (clinicalDocumentDT.getClinicalDocumentUid() != null)
            clinicalDocumentVO = getClinicalDocument(clinicalDocumentDT.getClinicalDocumentUid(),
                                                     nbsSecurityObj);

        if (clinicalDocumentVO != null)
        {
            clinicalDocumentVO.setTheClinicalDocumentDT(clinicalDocumentDT);
            clinicalDocumentVO.setItDirty(true);
        }

        if (clinicalDocumentVO == null)
        {
            clinicalDocumentVO = new ClinicalDocumentVO();
            clinicalDocumentVO.setTheClinicalDocumentDT(clinicalDocumentDT);
        }

        this.setClinicalDocument(clinicalDocumentVO, nbsSecurityObj);
    }
    catch (Exception e)
    {
    logger.fatal("ActControllerEJB.setClinicalDocumentInfo:" + e.getMessage(), e);
    throw new javax.ejb.EJBException(e.getMessage(),e);
    }
    }

    /**
     * @roseuid 3BFAF8F10246
     * @J2EE_METHOD  --  getProcedure1
     */
    public Procedure1DT getProcedure1(Long interventionUid,
                                      NBSSecurityObj nbsSecurityObj)
                               throws javax.ejb.EJBException
    {
    	try{
        InterventionVO interventionVO = null;
        Collection<Object> procedure1DTs = new ArrayList<Object> ();
        Procedure1DT procedure1DT = null;

        if (interventionUid != null)
            interventionVO = this.getIntervention(interventionUid,
                                                  nbsSecurityObj);

        if (interventionVO != null)
            procedure1DTs = interventionVO.getTheProcedure1DTCollection();

        Iterator<Object> anIterator = null;

        if (procedure1DTs != null)
        {
            anIterator = procedure1DTs.iterator();

            if (anIterator.hasNext())
            {
                procedure1DT = (Procedure1DT)anIterator.next();

                return procedure1DT;
            }
        }

        return null;
    }
    catch (Exception e)
    {
    logger.fatal("ActControllerEJB.getProcedure1:" + e.getMessage(), e);
    throw new javax.ejb.EJBException(e.getMessage(),e);
    }
    }

    /**
     * @roseuid 3BFAF8F20099
     * @J2EE_METHOD  --  setProcedure1
     */
    public void setProcedure1(Procedure1DT procedure1DT,
                              NBSSecurityObj nbsSecurityObj)
                       throws javax.ejb.EJBException,
                              NEDSSConcurrentDataException, NEDSSSystemException

    {
    	try{
        InterventionVO interventionVO = null;
        ArrayList<Object>  CollProcedure1Dt = new ArrayList<Object> ();
        CollProcedure1Dt.add(procedure1DT);
        logger.debug(
                "inside set 1" + procedure1DT.getInterventionUid() +
                "interventionVO" + interventionVO);

        if (procedure1DT.getInterventionUid() != null)
        {
            interventionVO = getIntervention(procedure1DT.getInterventionUid(),
                                             nbsSecurityObj);
            logger.debug("inside set 2" + interventionVO);
        }

        if (interventionVO != null)
        {
            interventionVO.setTheProcedure1DTCollection(CollProcedure1Dt);
        }

        if (interventionVO == null)
        {
            interventionVO = new InterventionVO();
            interventionVO.setTheParticipationDTCollection(CollProcedure1Dt);
        }

        this.setIntervention(interventionVO, nbsSecurityObj);
    }
    catch (Exception e)
    {
    logger.fatal("ActControllerEJB.setProcedure1:" + e.getMessage(), e);
    throw new javax.ejb.EJBException(e.getMessage(),e);
    }
    }

    /**
     * @roseuid 3BFAF8F202DE
     * @J2EE_METHOD  --  getSubstanceAdministration
     */
    public SubstanceAdministrationDT getSubstanceAdministration(Long interventionUid,
                                                                NBSSecurityObj nbsSecurityObj)
        throws javax.ejb.EJBException
    {
    	try{
        InterventionVO interventionVO = null;
        Collection<Object> substanceAdministrationDTs = new ArrayList<Object> ();
        SubstanceAdministrationDT substanceAdministrationDT = null;

        if (interventionUid != null)
            interventionVO = this.getIntervention(interventionUid,
                                                  nbsSecurityObj);

        if (interventionVO != null)
            substanceAdministrationDTs = interventionVO.getTheSubstanceAdministrationDTCollection();

        Iterator<Object> anIterator = null;

        if (substanceAdministrationDTs != null)
        {

            for (anIterator = substanceAdministrationDTs.iterator();
                 anIterator.hasNext();)
            {
                substanceAdministrationDT = (SubstanceAdministrationDT)anIterator.next();

                return substanceAdministrationDT;
            }
        }

        return substanceAdministrationDT;
    }
    catch (Exception e)
    {
    logger.fatal("ActControllerEJB.getSubstanceAdministration:" + e.getMessage(), e);
    throw new javax.ejb.EJBException(e.getMessage(),e);
    }
    }

    /**
     * @roseuid 3BFAF8F3013B
     * @J2EE_METHOD  --  setSubstanceAdministration
     */
    public void setSubstanceAdministration(SubstanceAdministrationDT substanceAdministrationDT,
                                           NBSSecurityObj nbsSecurityObj)
                                    throws javax.ejb.EJBException,
                                           NEDSSConcurrentDataException,

                                           NEDSSSystemException
    {
    	try{
        InterventionVO interventionVO = null;

        //InterventionDT interventionDT = null;
        ArrayList<Object>  CollSubstanceAdministrationDt = new ArrayList<Object> ();
        CollSubstanceAdministrationDt.add(substanceAdministrationDT);

        if (substanceAdministrationDT.getInterventionUid() != null)
        {
            interventionVO = getIntervention(substanceAdministrationDT.getInterventionUid(),
                                             nbsSecurityObj);
        }

        if (interventionVO != null)
            interventionVO.setTheSubstanceAdministrationDTCollection(
                    CollSubstanceAdministrationDt);

        if (interventionVO == null)
        {
            interventionVO = new InterventionVO();
            interventionVO.setTheSubstanceAdministrationDTCollection(
                    CollSubstanceAdministrationDt);
            logger.debug(
                    "the intervention VO is :" + interventionVO.toString());
        }

        this.setIntervention(interventionVO, nbsSecurityObj);
    }
    catch (Exception e)
    {
    logger.fatal("ActControllerEJB.setSubstanceAdministration:" + e.getMessage(), e);
    throw new javax.ejb.EJBException(e.getMessage(),e);
    }
    }

    /**
     * @roseuid 3BFAF8F3038A
     * @J2EE_METHOD  --  getObservationIDs
     */
    public Collection<Object> getObservationIDs(Long observationUid,
                                        NBSSecurityObj nbsSecurityObj)
                                 throws javax.ejb.EJBException
    {

        ObservationVO observationVO = null;
        Collection<Object> observations = new ArrayList<Object> ();

        if (observationUid != null)
            observationVO = this.getObservation(observationUid, nbsSecurityObj);

        if (observationVO != null)
            observations = observationVO.getTheActIdDTCollection();

        return observations;
    }

    /**
     * @roseuid 3BFAF8F401FB
     * @J2EE_METHOD  --  getObservationIDs
     */
    public Collection<Object> getObservationIDs(Long observationUid, String typeCd,
                                        NBSSecurityObj nbsSecurityObj)
                                 throws javax.ejb.EJBException
    {
    	try{
        ObservationVO observationVO = null;
        Collection<Object> actIDs = new ArrayList<Object> ();
        Collection<Object> newActIDs = new ArrayList<Object> ();
        observationVO = getObservation(observationUid, nbsSecurityObj);

        ActIdDT actIdDT = null;

        if (observationVO != null)
            actIDs = observationVO.getTheActIdDTCollection();

        if (actIDs != null)
        {

            Iterator<Object> anIterator = null;

            for (anIterator = actIDs.iterator(); anIterator.hasNext();)
            {
                actIdDT = (ActIdDT)anIterator.next();

                if ((actIdDT != null) &&
                    ((actIdDT.getTypeCd()).compareTo(typeCd) == 0))
                {
                    newActIDs.add(actIdDT);
                }
                else
                {

                    continue;
                }
            }

            return newActIDs;
        }

        return null;
    }
    catch (Exception e)
    {
    logger.fatal("ActControllerEJB.getObservationIDs:" + e.getMessage(), e);
    throw new javax.ejb.EJBException(e.getMessage(),e);
    }
    }

    /**
     * @roseuid 3BFAF8F501AC
     * @J2EE_METHOD  --  setObservationIDs
     */
    public void setObservationIDs(Collection<Object> observationIds,
                                  NBSSecurityObj nbsSecurityObj)
								throws
								javax.ejb.EJBException,
								NEDSSConcurrentDataException,

								NEDSSSystemException


    {
    	try{
        ObservationVO observationVO = null;
        ActIdDT actIdDT = null;
        Iterator<Object> anIterator = null;

        if (observationIds != null)
        {
            anIterator = observationIds.iterator();

            if (anIterator.hasNext())
            {
                actIdDT = (ActIdDT)anIterator.next();

                if (actIdDT.getActUid() != null)
                {
                    observationVO = getObservation(actIdDT.getActUid(),
                                                   nbsSecurityObj);
                }

                if (observationVO != null)
                {
                    observationVO.setTheActIdDTCollection(observationIds);
                    setObservation(observationVO, nbsSecurityObj);
                }
            }
        }
    }
    catch (Exception e)
    {
    logger.fatal("ActControllerEJB.setObservationIDs:" + e.getMessage(), e);
    throw new javax.ejb.EJBException(e.getMessage(),e);
    }
    }

    /**
     * @roseuid 3BFAF8F60027
     * @J2EE_METHOD  --  getPublicHealthCaseIDs
     */
    public Collection<Object> getPublicHealthCaseIDs(Long publicHealthCaseUid,
                                             NBSSecurityObj nbsSecurityObj)
                                      throws javax.ejb.EJBException
    {

        PublicHealthCaseVO publicHealthCaseVO = null;
        Collection<Object> publicHealthCases = new ArrayList<Object> ();
        publicHealthCaseVO = getPublicHealthCase(publicHealthCaseUid,
                                                 nbsSecurityObj);

        if (publicHealthCaseVO != null)
            publicHealthCases = publicHealthCaseVO.getTheActIdDTCollection();

        return publicHealthCases;
    }

    /**
     * @roseuid 3BFAF8F60294
     * @J2EE_METHOD  --  getPublicHealthCaseIDs
     */
    public Collection<Object> getPublicHealthCaseIDs(Long publicHealthCaseUid,
                                             String typeCd,
                                             NBSSecurityObj nbsSecurityObj)
                                      throws javax.ejb.EJBException
    {
    	try{
        PublicHealthCaseVO publicHealthCaseVO = null;
        Collection<Object> actIDs = new ArrayList<Object> ();
        Collection<Object> newActIDs = new ArrayList<Object> ();
        publicHealthCaseVO = getPublicHealthCase(publicHealthCaseUid,
                                                 nbsSecurityObj);

        ActIdDT actIdDT = null;

        if (publicHealthCaseVO != null)
            actIDs = publicHealthCaseVO.getTheActIdDTCollection();

        if (actIDs != null)
        {

            Iterator<Object> anIterator = null;

            for (anIterator = actIDs.iterator(); anIterator.hasNext();)
            {
                actIdDT = (ActIdDT)anIterator.next();

                if ((actIdDT != null) &&
                    ((actIdDT.getTypeCd()).compareTo(typeCd) == 0))
                {
                    newActIDs.add(actIdDT);
                }
                else
                {

                    continue;
                }
            }

            return newActIDs;
        }

        return null;
    }
    catch (Exception e)
    {
    logger.fatal("ActControllerEJB.getPublicHealthCaseIDs:" + e.getMessage(), e);
    throw new javax.ejb.EJBException(e.getMessage(),e);
    }
    }

    /**
     * @roseuid 3BFAF8F7026D
     * @J2EE_METHOD  --  setPublicHealthCaseIDs
     */
    public void setPublicHealthCaseIDs(Collection<Object> publicHealthCaseIds,
                                       NBSSecurityObj nbsSecurityObj)
                                throws javax.ejb.EJBException, NEDSSConcurrentDataException
    {
    	try{
        PublicHealthCaseVO publicHealthCaseVO = null;
        ActIdDT actIdDT = null;
        Iterator<Object> anIterator = null;

        if (publicHealthCaseIds != null)
        {
            anIterator = publicHealthCaseIds.iterator();

            if (anIterator.hasNext())
            {
                actIdDT = (ActIdDT)anIterator.next();

                if (actIdDT.getActUid() != null)
                {
                    logger.debug(
                            "test actIdDT.getActUid() " +
                            actIdDT.getActUid());
                    publicHealthCaseVO = getPublicHealthCase(actIdDT.getActUid(),
                                                             null);
                }

                if (publicHealthCaseVO != null)
                {
                    logger.debug("publicHealthCaseVO != null");
                    publicHealthCaseVO.setTheActIdDTCollection(
                            publicHealthCaseIds);
                    logger.debug(
                            "publicHealthCaseVO..getThePublicHealthCaseDT():  " +
                            publicHealthCaseVO.getThePublicHealthCaseDT());
                    setPublicHealthCase(publicHealthCaseVO, nbsSecurityObj);
                }
            }
        }
    }
    catch (Exception e)
    {
    logger.fatal("ActControllerEJB.setPublicHealthCaseIDs:" + e.getMessage(), e);
    throw new javax.ejb.EJBException(e.getMessage(),e);
    }
    }

    /**
     * @roseuid 3BFAF8F800FC
     * @J2EE_METHOD  --  getNotificationIDs
     */
    public Collection<Object> getNotificationIDs(Long notificationUid,
                                         NBSSecurityObj nbsSecurityObj)
                                  throws javax.ejb.EJBException
    {

        NotificationVO notificationVO = null;
        Collection<Object> notifications = new ArrayList<Object> ();
        notificationVO = getNotification(notificationUid, nbsSecurityObj);

        if (notificationVO != null)
            notifications = notificationVO.getTheActIdDTCollection();

        return notifications;
    }

    /**
     * @roseuid 3BFAF8F80387
     * @J2EE_METHOD  --  getNotificationIDs
     */
    public Collection<Object> getNotificationIDs(Long notificationUid, String typeCd,
                                         NBSSecurityObj nbsSecurityObj)
                                  throws javax.ejb.EJBException
    {
    	try{
        NotificationVO notificationVO = null;
        Collection<Object> actIDs = new ArrayList<Object> ();
        Collection<Object> newActIDs = new ArrayList<Object> ();
        notificationVO = getNotification(notificationUid, nbsSecurityObj);

        ActIdDT actIdDT = null;

        if (notificationVO != null)
            actIDs = notificationVO.getTheActIdDTCollection();

        if (actIDs != null)
        {

            Iterator<Object> anIterator = null;

            for (anIterator = actIDs.iterator(); anIterator.hasNext();)
            {
                actIdDT = (ActIdDT)anIterator.next();

                if ((actIdDT != null) &&
                    ((actIdDT.getTypeCd()).compareTo(typeCd) == 0))
                {
                    newActIDs.add(actIdDT);
                }
                else
                {

                    continue;
                }
            }

            return newActIDs;
        }

        return null;
    }
    catch (Exception e)
    {
    logger.fatal("ActControllerEJB.getNotificationIDs:" + e.getMessage(), e);
    throw new javax.ejb.EJBException(e.getMessage(),e);
    }
    }

    /**
     * @roseuid 3BFAF8F90374
     * @J2EE_METHOD  --  setNotificationIDs
     */
    public void setNotificationIDs(Collection<Object> notificationIds,
                                   NBSSecurityObj nbsSecurityObj)
                            throws javax.ejb.EJBException, NEDSSConcurrentDataException
    {
    	try{
        NotificationVO notificationVO = null;
       ActIdDT actIdDT = null;
        Iterator<Object> anIterator = null;

        if (notificationIds != null)
        {
            anIterator = notificationIds.iterator();

            if (anIterator.hasNext())
            {
                actIdDT = (ActIdDT)anIterator.next();

                if (actIdDT.getActUid() != null)
                    notificationVO = getNotification(actIdDT.getActUid(), null);

                if (notificationVO != null)
                {
                    notificationVO.setTheActIdDTCollection(notificationIds);
                    setNotification(notificationVO, nbsSecurityObj);
                }
            }
        }
    }
    catch (Exception e)
    {
    logger.fatal("ActControllerEJB.setNotificationIDs:" + e.getMessage(), e);
    throw new javax.ejb.EJBException(e.getMessage(),e);
    }
    }

    /**
     * @roseuid 3BFAF8FA0221
     * @J2EE_METHOD  --  getInterventionIDs
     */
    public Collection<Object> getInterventionIDs(Long interventionUid,
                                         NBSSecurityObj nbsSecurityObj)
                                  throws javax.ejb.EJBException
    {

        InterventionVO interventionVO = null;
        Collection<Object> interventions = new ArrayList<Object> ();
        interventionVO = getIntervention(interventionUid, nbsSecurityObj);

        if (interventionVO != null)
            interventions = interventionVO.getTheActIdDTCollection();

        return interventions;
    }

    /**
     * @roseuid 3BFAF8FB00CE
     * @J2EE_METHOD  --  getInterventionIDs
     */
    public Collection<Object> getInterventionIDs(Long interventionUid, String typeCd,
                                         NBSSecurityObj nbsSecurityObj)
                                  throws javax.ejb.EJBException
    {
    	try{
        InterventionVO interventionVO = null;
        Collection<Object> actIDs = new ArrayList<Object> ();
        Collection<Object> newActIDs = new ArrayList<Object> ();
        interventionVO = getIntervention(interventionUid, nbsSecurityObj);

        ActIdDT actIdDT = null;

        if (interventionVO != null)
            actIDs = interventionVO.getTheActIdDTCollection();

        if (actIDs != null)
        {

            Iterator<Object> anIterator = null;

            for (anIterator = actIDs.iterator(); anIterator.hasNext();)
            {
                actIdDT = (ActIdDT)anIterator.next();

                if ((actIdDT != null) &&
                    ((actIdDT.getTypeCd()).compareTo(typeCd) == 0))
                {
                    newActIDs.add(actIdDT);
                }
                else
                {

                    continue;
                }
            }

            return newActIDs;
        }

        return null;
    }
    catch (Exception e)
    {
    logger.fatal("ActControllerEJB.getInterventionIDs:" + e.getMessage(), e);
    throw new javax.ejb.EJBException(e.getMessage(),e);
    }
    }
    
    

    /**
     * @roseuid 3BFAF8FC00EE
     * @J2EE_METHOD  --  setInterventionIDs
     */
    public void setInterventionIDs(Collection<Object> interventionIds,
                                   NBSSecurityObj nbsSecurityObj)
                            throws javax.ejb.EJBException,
                                   NEDSSConcurrentDataException,
                                    NEDSSSystemException
    {
    	try{
        InterventionVO interventionVO = null;
        ActIdDT actIdDT = null;
        Iterator<Object> anIterator = null;

        if (interventionIds != null)
        {
            anIterator = interventionIds.iterator();

            if (anIterator.hasNext())
            {
                actIdDT = (ActIdDT)anIterator.next();

                if (actIdDT.getActUid() != null)
                    interventionVO = getIntervention(actIdDT.getActUid(), null);

                if (interventionVO != null)
                {
                    interventionVO.setTheActIdDTCollection(interventionIds);
                    logger.debug(
                            "interventionVO.getTheInterventionDT()" +
                            interventionVO.getTheInterventionDT());
                    setIntervention(interventionVO, nbsSecurityObj);
                }
            }
        }
    }
    catch (Exception e)
    {
    logger.fatal("ActControllerEJB.setInterventionIDs:" + e.getMessage(), e);
    throw new javax.ejb.EJBException(e.getMessage(),e);
    }
    }

    /**
     * @roseuid 3BFAF8FC0397
     * @J2EE_METHOD  --  getReferralIDs
     */
    public Collection<Object> getReferralIDs(Long referralUid,
                                     NBSSecurityObj nbsSecurityObj)
                              throws javax.ejb.EJBException
    {

        ReferralVO referralVO = null;
        Collection<Object> referrals = new ArrayList<Object> ();
        referralVO = getReferral(referralUid, nbsSecurityObj);

        if (referralVO != null)
            referrals = referralVO.getTheActivityIdDTCollection();

        return referrals;
    }

    /**
     * @roseuid 3BFAF8FD0258
     * @J2EE_METHOD  --  getReferralIDs
     */
    public Collection<Object> getReferralIDs(Long referralUid, String typeCd,
                                     NBSSecurityObj nbsSecurityObj)
                              throws javax.ejb.EJBException
    {
    	try{
        ReferralVO referralVO = null;
        Collection<Object> actIDs = new ArrayList<Object> ();
        Collection<Object> newActIDs = new ArrayList<Object> ();
        referralVO = getReferral(referralUid, nbsSecurityObj);

        ActIdDT actIdDT = null;

        if (referralVO != null)
            actIDs = referralVO.getTheActivityIdDTCollection();

        if (actIDs != null)
        {

            Iterator<Object> anIterator = null;

            for (anIterator = actIDs.iterator(); anIterator.hasNext();)
            {
                actIdDT = (ActIdDT)anIterator.next();

                if ((actIdDT != null) &&
                    ((actIdDT.getTypeCd()).compareTo(typeCd) == 0))
                {
                    newActIDs.add(actIdDT);
                }
                else
                {

                    continue;
                }
            }

            return newActIDs;
        }

        return null;
    }
    catch (Exception e)
    {
    logger.fatal("ActControllerEJB.getReferralIDs:" + e.getMessage(), e);
    throw new javax.ejb.EJBException(e.getMessage(),e);
    }
    }

    /**
     * @roseuid 3BFAF8FE0295
     * @J2EE_METHOD  --  setReferralIDs
     */
    public void setReferralIDs(Collection<Object> referralIds,
                               NBSSecurityObj nbsSecurityObj)
                        throws javax.ejb.EJBException,NEDSSConcurrentDataException
    {
    	try{
        ReferralVO referralVO = null;
        ActIdDT actIdDT = null;
        Iterator<Object> anIterator = null;

        if (referralIds != null)
        {
            anIterator = referralIds.iterator();

            if (anIterator.hasNext())
            {
                actIdDT = (ActIdDT)anIterator.next();

                if (actIdDT.getActUid() != null)
                    referralVO = getReferral(actIdDT.getActUid(), null);

                if (referralVO != null)
                {
                    referralVO.setTheActivityIdDTCollection(referralIds);
                    setReferral(referralVO, nbsSecurityObj);
                }
            }
        }
    }
    catch (Exception e)
    {
    logger.fatal("ActControllerEJB.setReferralIDs:" + e.getMessage(), e);
    throw new javax.ejb.EJBException(e.getMessage(),e);
    }
    }

    /**
     * @roseuid 3BFAF8FF0188
     * @J2EE_METHOD  --  getPatientEncounterIDs
     */
    public Collection<Object> getPatientEncounterIDs(Long patientEncounterUid,
                                             NBSSecurityObj nbsSecurityObj)
                                      throws javax.ejb.EJBException
    {

        PatientEncounterVO patientEncounterVO = null;
        Collection<Object> patientEncounters = new ArrayList<Object> ();
        patientEncounterVO = getPatientEncounter(patientEncounterUid,
                                                 nbsSecurityObj);

        if (patientEncounterVO != null)
            patientEncounters = patientEncounterVO.getTheActivityIdDTCollection();

        return patientEncounters;
    }

    /**
     * @roseuid 3BFAF900007B
     * @J2EE_METHOD  --  getPatientEncounterIDs
     */
    public Collection<Object> getPatientEncounterIDs(Long patientEncounterUid,
                                             String typeCd,
                                             NBSSecurityObj nbsSecurityObj)
                                      throws javax.ejb.EJBException
    {
    	try{
        PatientEncounterVO patientEncounterVO = null;
        Collection<Object> actIDs = new ArrayList<Object> ();
        Collection<Object> newActIDs = new ArrayList<Object> ();
        patientEncounterVO = getPatientEncounter(patientEncounterUid,
                                                 nbsSecurityObj);

        ActIdDT actIdDT = null;

        if (patientEncounterVO != null)
            actIDs = patientEncounterVO.getTheActivityIdDTCollection();

        if (actIDs != null)
        {

            Iterator<Object> anIterator = null;

            for (anIterator = actIDs.iterator(); anIterator.hasNext();)
            {
                actIdDT = (ActIdDT)anIterator.next();

                if ((actIdDT != null) &&
                    ((actIdDT.getTypeCd()).compareTo(typeCd) == 0))
                {
                    newActIDs.add(actIdDT);
                }
            }

            return newActIDs;
        }

        return null;
    }
    catch (Exception e)
    {	
    	logger.fatal("ActControllerEJB.getPatientEncounterIDs:" + e.getMessage(), e);
    	throw new javax.ejb.EJBException(e.getMessage(),e);
    }
	}

    /**
     * @roseuid 3BFAF901016D
     * @J2EE_METHOD  --  getClinicalDocumentIDs
     */
    public Collection<Object> getClinicalDocumentIDs(Long clinicalDocumentUid,
                                             NBSSecurityObj nbsSecurityObj)
                                      throws javax.ejb.EJBException
    {

        ClinicalDocumentVO clinicalDocumentVO = null;
        Collection<Object> clinicalDocuments = new ArrayList<Object> ();
        clinicalDocumentVO = getClinicalDocument(clinicalDocumentUid,
                                                 nbsSecurityObj);

        if (clinicalDocumentVO != null)
            clinicalDocuments = clinicalDocumentVO.getTheActivityIdDTCollection();

        return clinicalDocuments;
    }

    /**
     * @roseuid 3BFAF9020006
     * @J2EE_METHOD  --  getClinicalDocumentIDs
     */
    public Collection<Object> getClinicalDocumentIDs(Long clinicalDocumentUid,
                                             String typeCd,
                                             NBSSecurityObj nbsSecurityObj)
                                      throws javax.ejb.EJBException
    {
    	try{
        ClinicalDocumentVO clinicalDocumentVO = null;
        Collection<Object> actIDs = new ArrayList<Object> ();
        Collection<Object> newActIDs = new ArrayList<Object> ();
        clinicalDocumentVO = getClinicalDocument(clinicalDocumentUid,
                                                 nbsSecurityObj);

        ActIdDT actIdDT = null;

        if (clinicalDocumentVO != null)
            actIDs = clinicalDocumentVO.getTheActivityIdDTCollection();

        if (actIDs != null)
        {

            Iterator<Object> anIterator = null;

            for (anIterator = actIDs.iterator(); anIterator.hasNext();)
            {
                actIdDT = (ActIdDT)anIterator.next();

                if ((actIdDT != null) &&
                    ((actIdDT.getTypeCd()).compareTo(typeCd) == 0))
                {
                    newActIDs.add(actIdDT);
                }
                else
                {

                    continue;
                }
            }

            return newActIDs;
        }

        return null;
    	}
    	catch (Exception e)
    	{
    	logger.fatal("ActControllerEJB.getClinicalDocumentIDs:" + e.getMessage(), e);
    	throw new javax.ejb.EJBException(e.getMessage(),e);
    	}
    }

    /**
     * @roseuid 3BFAF9030102
     * @J2EE_METHOD  --  setClinicalDocumentIDs
     */
    public void setClinicalDocumentIDs(Collection<Object> clinicalDocumentIds,
                                       NBSSecurityObj nbsSecurityObj)
                                throws javax.ejb.EJBException, NEDSSConcurrentDataException
    {
    	try{
        ClinicalDocumentVO clinicalDocumentVO = null;
        ActIdDT actIdDT = null;
        Iterator<Object> anIterator = null;

        if (clinicalDocumentIds != null)
        {
            anIterator = clinicalDocumentIds.iterator();

            if (anIterator.hasNext())
            {
                actIdDT = (ActIdDT)anIterator.next();

                if (actIdDT.getActUid() != null)
                    clinicalDocumentVO = getClinicalDocument(actIdDT.getActUid(),
                                                             null);

                if (clinicalDocumentVO != null)
                {
                    clinicalDocumentVO.setTheActivityIdDTCollection(
                            clinicalDocumentIds);
                    setClinicalDocument(clinicalDocumentVO, nbsSecurityObj);
                }
            }
        }
    	}
    	catch (Exception e)
    	{
    	logger.fatal("ActControllerEJB.setClinicalDocumentIDs:" + e.getMessage(), e);
    	throw new javax.ejb.EJBException(e.getMessage(),e);
    	}
    }

    /**
     * @roseuid 3BFAF9030397
     * @J2EE_METHOD  --  getAllObservationLocators
     */
    public Collection<Object> getAllObservationLocators(Long observationUid,
                                                NBSSecurityObj nbsSecurityObj)
                                         throws javax.ejb.EJBException
    {

        ObservationVO observationVO = null;
        Collection<Object> activityLocatorParticipationDTs = new ArrayList<Object> ();
        Collection<Object> entityLocatorParticipationDTs = new ArrayList<Object> ();
        ActivityLocatorParticipationDT activityLocatorParticipationDT = null;
        EntityLocatorParticipationDAOImpl entityLocatorParticipationDAOImpl =
                null;

        try
        {

            if (observationUid != null)
                observationVO = getObservation(observationUid, nbsSecurityObj);

            if (observationVO != null)
                activityLocatorParticipationDTs = observationVO.getTheActivityLocatorParticipationDTCollection();

            entityLocatorParticipationDAOImpl = (EntityLocatorParticipationDAOImpl)NEDSSDAOFactory.getDAO(
                                                        JNDINames.ENTITY_GROUP_PARTICIPATION_DAO_CLASS);

            if (activityLocatorParticipationDTs != null &&
                entityLocatorParticipationDAOImpl != null)
            {

                Iterator<Object> anIterator = activityLocatorParticipationDTs.iterator();

                if (anIterator.hasNext())
                {
                    activityLocatorParticipationDT = (ActivityLocatorParticipationDT)anIterator.next();
                    entityLocatorParticipationDTs = entityLocatorParticipationDAOImpl.load(activityLocatorParticipationDT.getEntityUid().longValue());
                }
            }
        }
        catch (Exception e)
        {
            logger.fatal("ActControllerEJB.getObservation():" + e.getMessage(),e);
            e.printStackTrace();
            throw new javax.ejb.EJBException(e.getMessage(),e);
        }

        return entityLocatorParticipationDTs;
    }

    /**
     * @roseuid 3BFAF9040258
     * @J2EE_METHOD  --  setAllObservationLocators
     */
    public void setAllObservationLocators(Collection<Object> activityLocatorParticipationDTs,
                                          NBSSecurityObj nbsSecurityObj)
                                   throws javax.ejb.EJBException
    {

        EntityLocatorParticipationDAOImpl entityLocatorParticipationDAOImpl =
                null;
        EntityLocatorParticipationDT entityLocatorParticipationDT = null;
        ObservationDT observationDT = null;
        ActivityLocatorParticipationDT activityLocatorParticipationDT = null;
        ObservationVO observationVO = new ObservationVO();
        Iterator<Object> anIterator = null;
        logger.debug("Got the setAllObservationLocators");

        if (activityLocatorParticipationDTs != null)
        {

            try
            {
                entityLocatorParticipationDAOImpl = (EntityLocatorParticipationDAOImpl)NEDSSDAOFactory.getDAO(
                                                            JNDINames.ENTITY_GROUP_PARTICIPATION_DAO_CLASS);
                anIterator = activityLocatorParticipationDTs.iterator();

                if (anIterator.hasNext())
                {
                    activityLocatorParticipationDT = (ActivityLocatorParticipationDT)anIterator.next();
                    observationVO = getObservation(activityLocatorParticipationDT.getActUid(),
                                                   nbsSecurityObj);
                    observationDT = observationVO.getTheObservationDT();
                    entityLocatorParticipationDT = (EntityLocatorParticipationDT)entityLocatorParticipationDAOImpl.loadEntityLocatorParticipation(activityLocatorParticipationDT.getEntityUid().longValue(),
                                                                                                                                                  activityLocatorParticipationDT.getLocatorUid().longValue());

                    if (activityLocatorParticipationDT.getActUid().equals(observationDT.getObservationUid()) &&
                        activityLocatorParticipationDT.getLocatorUid().equals(entityLocatorParticipationDT.getLocatorUid()) &&
                        activityLocatorParticipationDT.getEntityUid().equals(entityLocatorParticipationDT.getEntityUid()))
                    {
                        observationVO = getObservation(observationDT.getObservationUid(),
                                                       nbsSecurityObj);
                        logger.debug("got the observationVO" +
                                     observationVO);
                        observationVO.setTheActivityLocatorParticipationDTCollection(
                                activityLocatorParticipationDTs);
                        setObservation(observationVO, nbsSecurityObj);
                    }
                }
            }
            catch (Exception e)
            {
                logger.fatal("ActControllerEJB.setAllObservationLocators():" + e.getMessage(),e);
                e.printStackTrace();
                throw new javax.ejb.EJBException(e.getMessage(),e);
            }
        }
    }

    /**
     * @roseuid 3BFAF9050123
     * @J2EE_METHOD  --  getObservationPhysicalLocators
     */
    public Collection<Object> getObservationPhysicalLocators(Long observationUid,
                                                     NBSSecurityObj nbsSecurityObj)
                                              throws javax.ejb.EJBException
    {

        ObservationVO observationVO = null;
        Collection<Object> physicalLocatorDTs = new ArrayList<Object> ();
        PhysicalLocatorDT physicalLocatorDT = null;
        Collection<Object> activityLocatorParticipationDTs = new ArrayList<Object> ();
        EntityLocatorParticipationDAOImpl entityLocatorParticipationDAOImpl =
                null;
        EntityLocatorParticipationDT entityLocatorParticipationDT = null;
        ActivityLocatorParticipationDT activityLocatorParticipationDT = null;

        if (observationUid != null)
            observationVO = getObservation(observationUid, nbsSecurityObj);

        if (observationVO != null)
            activityLocatorParticipationDTs = observationVO.getTheActivityLocatorParticipationDTCollection();

        try
        {
            entityLocatorParticipationDAOImpl = (EntityLocatorParticipationDAOImpl)NEDSSDAOFactory.getDAO(
                                                        JNDINames.ENTITY_GROUP_PARTICIPATION_DAO_CLASS);

            Iterator<Object> anIterator = null;

            if (activityLocatorParticipationDTs != null &&
                entityLocatorParticipationDAOImpl != null)
            {

                for (anIterator = activityLocatorParticipationDTs.iterator();
                     anIterator.hasNext();)
                {
                    activityLocatorParticipationDT = (ActivityLocatorParticipationDT)anIterator.next();
                    entityLocatorParticipationDT = (EntityLocatorParticipationDT)entityLocatorParticipationDAOImpl.loadEntityLocatorParticipation(activityLocatorParticipationDT.getEntityUid().longValue(),
                                                                                                                                                  activityLocatorParticipationDT.getLocatorUid().longValue());

                    if (entityLocatorParticipationDT != null &&
                        entityLocatorParticipationDT.getClassCd().equalsIgnoreCase(
                                NEDSSConstants.PHYSICAL))
                    {
                        physicalLocatorDT = entityLocatorParticipationDT.getThePhysicalLocatorDT();
                        physicalLocatorDTs.add(physicalLocatorDT);
                    }
                    else

                        continue;
                }
            }
        }
        catch (Exception e)
        {
            logger.fatal("ActControllerEJB.getObservationPhysicalLocators():" + e.getMessage(),e);
            e.printStackTrace();
            throw new javax.ejb.EJBException(e.getMessage(),e);
        }

        return physicalLocatorDTs;
    }

    /**
     * @roseuid 3BFAF90603CD
     * @J2EE_METHOD  --  getObservationPostalLocators
     */
    public Collection<Object> getObservationPostalLocators(Long observationUid,
                                                   NBSSecurityObj nbsSecurityObj)
                                            throws javax.ejb.EJBException
    {

        ObservationVO observationVO = null;
        Collection<Object> postalLocatorDTs = new ArrayList<Object> ();
        PostalLocatorDT postalLocatorDT = null;
        Collection<Object> activityLocatorParticipationDTs = new ArrayList<Object> ();
        EntityLocatorParticipationDAOImpl entityLocatorParticipationDAOImpl =
                null;
        EntityLocatorParticipationDT entityLocatorParticipationDT = null;
        ActivityLocatorParticipationDT activityLocatorParticipationDT = null;

        if (observationUid != null)
            observationVO = getObservation(observationUid, nbsSecurityObj);

        if (observationVO != null)
            activityLocatorParticipationDTs = observationVO.getTheActivityLocatorParticipationDTCollection();

        try
        {
            entityLocatorParticipationDAOImpl = (EntityLocatorParticipationDAOImpl)NEDSSDAOFactory.getDAO(
                                                        JNDINames.ENTITY_GROUP_PARTICIPATION_DAO_CLASS);

            Iterator<Object> anIterator = null;

            if (activityLocatorParticipationDTs != null &&
                entityLocatorParticipationDAOImpl != null)
            {

                for (anIterator = activityLocatorParticipationDTs.iterator();
                     anIterator.hasNext();)
                {
                    activityLocatorParticipationDT = (ActivityLocatorParticipationDT)anIterator.next();
                    entityLocatorParticipationDT = (EntityLocatorParticipationDT)entityLocatorParticipationDAOImpl.loadEntityLocatorParticipation(activityLocatorParticipationDT.getEntityUid().longValue(),
                                                                                                                                                  activityLocatorParticipationDT.getLocatorUid().longValue());

                    if (entityLocatorParticipationDT != null &&
                        entityLocatorParticipationDT.getClassCd().equalsIgnoreCase(
                                NEDSSConstants.POSTAL))
                    {
                        postalLocatorDT = entityLocatorParticipationDT.getThePostalLocatorDT();
                        postalLocatorDTs.add(postalLocatorDT);
                    }
                    else

                        continue;
                }
            }
        }
        catch (Exception e)
        {
            logger.fatal("ActControllerEJB.getObservationPostalLocators():" + e.getMessage(),e);
            e.printStackTrace();
            throw new javax.ejb.EJBException(e.getMessage(),e);
        }

        return postalLocatorDTs;
    }

    /**
     * @roseuid 3BFAF9080221
     * @J2EE_METHOD  --  getObservationTeleLocators
     */
    public Collection<Object> getObservationTeleLocators(Long observationUid,
                                                 NBSSecurityObj nbsSecurityObj)
                                          throws javax.ejb.EJBException
    {

        ObservationVO observationVO = null;
        Collection<Object> teleLocatorDTs = new ArrayList<Object> ();
        TeleLocatorDT teleLocatorDT = null;
        Collection<Object> activityLocatorParticipationDTs = new ArrayList<Object> ();
        EntityLocatorParticipationDAOImpl entityLocatorParticipationDAOImpl =
                null;
        EntityLocatorParticipationDT entityLocatorParticipationDT = null;
        ActivityLocatorParticipationDT activityLocatorParticipationDT = null;

        if (observationUid != null)
            observationVO = getObservation(observationUid, nbsSecurityObj);

        if (observationVO != null)
            activityLocatorParticipationDTs = observationVO.getTheActivityLocatorParticipationDTCollection();

        try
        {
            entityLocatorParticipationDAOImpl = (EntityLocatorParticipationDAOImpl)NEDSSDAOFactory.getDAO(
                                                        JNDINames.ENTITY_GROUP_PARTICIPATION_DAO_CLASS);

            Iterator<Object> anIterator = null;

            if (activityLocatorParticipationDTs != null &&
                entityLocatorParticipationDAOImpl != null)
            {

                for (anIterator = activityLocatorParticipationDTs.iterator();
                     anIterator.hasNext();)
                {
                    activityLocatorParticipationDT = (ActivityLocatorParticipationDT)anIterator.next();
                    entityLocatorParticipationDT = (EntityLocatorParticipationDT)entityLocatorParticipationDAOImpl.loadEntityLocatorParticipation(activityLocatorParticipationDT.getEntityUid().longValue(),
                                                                                                                                                  activityLocatorParticipationDT.getLocatorUid().longValue());

                    if (entityLocatorParticipationDT != null &&
                        entityLocatorParticipationDT.getClassCd().equalsIgnoreCase(
                                NEDSSConstants.TELE))
                    {
                        teleLocatorDT = entityLocatorParticipationDT.getTheTeleLocatorDT();
                        teleLocatorDTs.add(teleLocatorDT);
                    }
                    else

                        continue;
                }
            }
        }
        catch (Exception e)
        {
            logger.fatal("ActControllerEJB.getObservationTeleLocators():" + e.getMessage(),e);
            e.printStackTrace();
            throw new javax.ejb.EJBException(e.getMessage(),e);
        }

        return teleLocatorDTs;
    }

    /**
     * @roseuid 3BFAF90A00C6
     * @J2EE_METHOD  --  getAllPublicHealthCaseLocators
     */
    public Collection<Object> getAllPublicHealthCaseLocators(Long publicHealthCaseUid,
                                                     NBSSecurityObj nbsSecurityObj)
                                              throws javax.ejb.EJBException
    {

        PublicHealthCaseVO publicHealthCaseVO = null;
        Collection<Object> activityLocatorParticipationDTs = new ArrayList<Object> ();
        Collection<Object> entityLocatorParticipationDTs = new ArrayList<Object> ();
        ActivityLocatorParticipationDT activityLocatorParticipationDT = null;
        EntityLocatorParticipationDAOImpl entityLocatorParticipationDAOImpl =
                null;

        try
        {

            if (publicHealthCaseUid != null)
                publicHealthCaseVO = getPublicHealthCase(publicHealthCaseUid,
                                                         nbsSecurityObj);

            if (publicHealthCaseVO != null)
                activityLocatorParticipationDTs = publicHealthCaseVO.getTheActivityLocatorParticipationDTCollection();

            entityLocatorParticipationDAOImpl = (EntityLocatorParticipationDAOImpl)NEDSSDAOFactory.getDAO(
                                                        JNDINames.ENTITY_GROUP_PARTICIPATION_DAO_CLASS);

            if (activityLocatorParticipationDTs != null &&
                entityLocatorParticipationDAOImpl != null)
            {

                Iterator<Object> anIterator = activityLocatorParticipationDTs.iterator();

                if (anIterator.hasNext())
                {
                    activityLocatorParticipationDT = (ActivityLocatorParticipationDT)anIterator.next();
                    entityLocatorParticipationDTs = entityLocatorParticipationDAOImpl.load(activityLocatorParticipationDT.getEntityUid().longValue());
                }
            }
        }
        catch (Exception e)
        {
            logger.fatal("ActControllerEJB.getAllPublicHealthCaseLocators():" + e.getMessage(),e);

            //e.printStackTrace();
            throw new javax.ejb.EJBException(e.getMessage(),e);
        }

        return entityLocatorParticipationDTs;
    }

    /**
     * @roseuid 3BFAF90A03E7
     * @J2EE_METHOD  --  setAllPublicHealthCaseLocators
     */
    public void setAllPublicHealthCaseLocators(Collection<Object> activityLocatorParticipationDTs,
                                               NBSSecurityObj nbsSecurityObj)
                                        throws javax.ejb.EJBException
    {

        EntityLocatorParticipationDAOImpl entityLocatorParticipationDAOImpl =
                null;
        EntityLocatorParticipationDT entityLocatorParticipationDT = null;
        PublicHealthCaseDT publicHealthCaseDT = null;
        ActivityLocatorParticipationDT activityLocatorParticipationDT = null;
        PublicHealthCaseVO publicHealthCaseVO = new PublicHealthCaseVO();
        Iterator<Object> anIterator = null;
        logger.debug("Got the setAllPublicHealthCaseLocators");

        if (activityLocatorParticipationDTs != null)
        {

            try
            {
                entityLocatorParticipationDAOImpl = (EntityLocatorParticipationDAOImpl)NEDSSDAOFactory.getDAO(
                                                            JNDINames.ENTITY_GROUP_PARTICIPATION_DAO_CLASS);
                anIterator = activityLocatorParticipationDTs.iterator();

                if (anIterator.hasNext())
                {
                    activityLocatorParticipationDT = (ActivityLocatorParticipationDT)anIterator.next();
                    publicHealthCaseVO = getPublicHealthCase(activityLocatorParticipationDT.getActUid(),
                                                             nbsSecurityObj);
                    publicHealthCaseDT = publicHealthCaseVO.getThePublicHealthCaseDT();
                    entityLocatorParticipationDT = (EntityLocatorParticipationDT)entityLocatorParticipationDAOImpl.loadEntityLocatorParticipation(activityLocatorParticipationDT.getEntityUid().longValue(),
                                                                                                                                                  activityLocatorParticipationDT.getLocatorUid().longValue());

                    if (activityLocatorParticipationDT.getActUid().equals(publicHealthCaseDT.getPublicHealthCaseUid()) &&
                        activityLocatorParticipationDT.getLocatorUid().equals(entityLocatorParticipationDT.getLocatorUid()) &&
                        activityLocatorParticipationDT.getEntityUid().equals(entityLocatorParticipationDT.getEntityUid()))
                    {
                        publicHealthCaseVO = getPublicHealthCase(publicHealthCaseDT.getPublicHealthCaseUid(),
                                                                 nbsSecurityObj);
                        logger.debug(
                                "got the publicHealthCaseVO" +
                                publicHealthCaseVO);
                        publicHealthCaseVO.setTheActivityLocatorParticipationDTCollection(
                                activityLocatorParticipationDTs);
                        setPublicHealthCase(publicHealthCaseVO, nbsSecurityObj);
                    }
                }
            }
            catch (Exception e)
            {
                logger.fatal("ActControllerEJB.setAllPublicHealthCaseLocators():" + e.getMessage(),e);
                e.printStackTrace();
                throw new javax.ejb.EJBException(e.getMessage(),e);
            }
        }
    }

    /**
     * @roseuid 3BFAF90B02E4
     * @J2EE_METHOD  --  getPublicHealthCasePhysicalLocators
     */
    public Collection<Object> getPublicHealthCasePhysicalLocators(Long publicHealthCaseUid,
                                                          NBSSecurityObj nbsSecurityObj)
        throws javax.ejb.EJBException
    {

        PublicHealthCaseVO publicHealthCaseVO = null;
        Collection<Object> physicalLocatorDTs = new ArrayList<Object> ();
        PhysicalLocatorDT physicalLocatorDT = null;
        Collection<Object> activityLocatorParticipationDTs = new ArrayList<Object> ();
        EntityLocatorParticipationDAOImpl entityLocatorParticipationDAOImpl =
                null;
        EntityLocatorParticipationDT entityLocatorParticipationDT = null;
        ActivityLocatorParticipationDT activityLocatorParticipationDT = null;

        if (publicHealthCaseUid != null)
            publicHealthCaseVO = getPublicHealthCase(publicHealthCaseUid,
                                                     nbsSecurityObj);

        if (publicHealthCaseVO != null)
            activityLocatorParticipationDTs = publicHealthCaseVO.getTheActivityLocatorParticipationDTCollection();

        try
        {
            entityLocatorParticipationDAOImpl = (EntityLocatorParticipationDAOImpl)NEDSSDAOFactory.getDAO(
                                                        JNDINames.ENTITY_GROUP_PARTICIPATION_DAO_CLASS);

            Iterator<Object> anIterator = null;

            if (activityLocatorParticipationDTs != null &&
                entityLocatorParticipationDAOImpl != null)
            {

                for (anIterator = activityLocatorParticipationDTs.iterator();
                     anIterator.hasNext();)
                {
                    activityLocatorParticipationDT = (ActivityLocatorParticipationDT)anIterator.next();
                    entityLocatorParticipationDT = (EntityLocatorParticipationDT)entityLocatorParticipationDAOImpl.loadEntityLocatorParticipation(activityLocatorParticipationDT.getEntityUid().longValue(),
                                                                                                                                                  activityLocatorParticipationDT.getLocatorUid().longValue());

                    if (entityLocatorParticipationDT != null &&
                        entityLocatorParticipationDT.getClassCd().equalsIgnoreCase(
                                NEDSSConstants.PHYSICAL))
                    {
                        physicalLocatorDT = entityLocatorParticipationDT.getThePhysicalLocatorDT();
                        physicalLocatorDTs.add(physicalLocatorDT);
                    }
                    else

                        continue;
                }
            }
        }
        catch (Exception e)
        {
            logger.fatal("ActControllerEJB.getPublicHealthCasePhysicalLocators():" + e.getMessage(),e);
            e.printStackTrace();
            throw new javax.ejb.EJBException(e.getMessage(),e);
        }

        return physicalLocatorDTs;
    }

    /**
     * @roseuid 3BFAF90D0305
     * @J2EE_METHOD  --  getPublicHealthCasePostalLocators
     */
    public Collection<Object> getPublicHealthCasePostalLocators(Long publicHealthCaseUid,
                                                        NBSSecurityObj nbsSecurityObj)
                                                 throws javax.ejb.EJBException
    {

        PublicHealthCaseVO publicHealthCaseVO = null;
        Collection<Object> postalLocatorDTs = new ArrayList<Object> ();
        PostalLocatorDT postalLocatorDT = null;
        Collection<Object> activityLocatorParticipationDTs = new ArrayList<Object> ();
        EntityLocatorParticipationDAOImpl entityLocatorParticipationDAOImpl =
                null;
        EntityLocatorParticipationDT entityLocatorParticipationDT = null;
        ActivityLocatorParticipationDT activityLocatorParticipationDT = null;

        if (publicHealthCaseUid != null)
            publicHealthCaseVO = getPublicHealthCase(publicHealthCaseUid,
                                                     nbsSecurityObj);

        if (publicHealthCaseVO != null)
            activityLocatorParticipationDTs = publicHealthCaseVO.getTheActivityLocatorParticipationDTCollection();

        try
        {
            entityLocatorParticipationDAOImpl = (EntityLocatorParticipationDAOImpl)NEDSSDAOFactory.getDAO(
                                                        JNDINames.ENTITY_GROUP_PARTICIPATION_DAO_CLASS);

            Iterator<Object> anIterator = null;

            if (activityLocatorParticipationDTs != null &&
                entityLocatorParticipationDAOImpl != null)
            {

                for (anIterator = activityLocatorParticipationDTs.iterator();
                     anIterator.hasNext();)
                {
                    activityLocatorParticipationDT = (ActivityLocatorParticipationDT)anIterator.next();
                    entityLocatorParticipationDT = (EntityLocatorParticipationDT)entityLocatorParticipationDAOImpl.loadEntityLocatorParticipation(activityLocatorParticipationDT.getEntityUid().longValue(),
                                                                                                                                                  activityLocatorParticipationDT.getLocatorUid().longValue());

                    if (entityLocatorParticipationDT != null &&
                        entityLocatorParticipationDT.getClassCd().equalsIgnoreCase(
                                NEDSSConstants.POSTAL))
                    {
                        postalLocatorDT = entityLocatorParticipationDT.getThePostalLocatorDT();
                        postalLocatorDTs.add(postalLocatorDT);
                    }
                    else

                        continue;
                }
            }
        }
        catch (Exception e)
        {
            logger.fatal("ActControllerEJB.getPublicHealthCasePostalLocators():" + e.getMessage(),e);
            e.printStackTrace();
            throw new javax.ejb.EJBException(e.getMessage(),e);
        }

        return postalLocatorDTs;
    }

    /**
     * @roseuid 3BFAF90F01DB
     * @J2EE_METHOD  --  getPublicHealthCaseTeleLocators
     */
    public Collection<Object> getPublicHealthCaseTeleLocators(Long publicHealthCaseUid,
                                                      NBSSecurityObj nbsSecurityObj)
                                               throws javax.ejb.EJBException
    {

        PublicHealthCaseVO publicHealthCaseVO = null;
        Collection<Object> teleLocatorDTs = new ArrayList<Object> ();
        TeleLocatorDT teleLocatorDT = null;
        Collection<Object> activityLocatorParticipationDTs = new ArrayList<Object> ();
        EntityLocatorParticipationDAOImpl entityLocatorParticipationDAOImpl =
                null;
        EntityLocatorParticipationDT entityLocatorParticipationDT = null;
        ActivityLocatorParticipationDT activityLocatorParticipationDT = null;

        if (publicHealthCaseUid != null)
            publicHealthCaseVO = getPublicHealthCase(publicHealthCaseUid,
                                                     nbsSecurityObj);

        if (publicHealthCaseVO != null)
            activityLocatorParticipationDTs = publicHealthCaseVO.getTheActivityLocatorParticipationDTCollection();

        try
        {
            entityLocatorParticipationDAOImpl = (EntityLocatorParticipationDAOImpl)NEDSSDAOFactory.getDAO(
                                                        JNDINames.ENTITY_GROUP_PARTICIPATION_DAO_CLASS);

            Iterator<Object> anIterator = null;

            if (activityLocatorParticipationDTs != null &&
                entityLocatorParticipationDAOImpl != null)
            {

                for (anIterator = activityLocatorParticipationDTs.iterator();
                     anIterator.hasNext();)
                {
                    activityLocatorParticipationDT = (ActivityLocatorParticipationDT)anIterator.next();
                    entityLocatorParticipationDT = (EntityLocatorParticipationDT)entityLocatorParticipationDAOImpl.loadEntityLocatorParticipation(activityLocatorParticipationDT.getEntityUid().longValue(),
                                                                                                                                                  activityLocatorParticipationDT.getLocatorUid().longValue());

                    if (entityLocatorParticipationDT != null &&
                        entityLocatorParticipationDT.getClassCd().equalsIgnoreCase(
                                NEDSSConstants.TELE))
                    {
                        teleLocatorDT = entityLocatorParticipationDT.getTheTeleLocatorDT();
                        teleLocatorDTs.add(teleLocatorDT);
                    }
                    else

                        continue;
                }
            }
        }
        catch (Exception e)
        {
            logger.fatal("ActControllerEJB.getPublicHealthCaseTeleLocators():" + e.getMessage(),e);
            e.printStackTrace();
            throw new javax.ejb.EJBException(e.getMessage(),e);
        }

        return teleLocatorDTs;
    }

    /**
     * @roseuid 3BFAF9100219
     * @J2EE_METHOD  --  getAllNotificationLocators
     */
    public Collection<Object> getAllNotificationLocators(Long notificationUid,
                                                 NBSSecurityObj nbsSecurityObj)
                                          throws javax.ejb.EJBException
    {

        NotificationVO notificationVO = null;
        Collection<Object> activityLocatorParticipationDTs = new ArrayList<Object> ();
        Collection<Object> entityLocatorParticipationDTs = new ArrayList<Object> ();
        ActivityLocatorParticipationDT activityLocatorParticipationDT = null;
        EntityLocatorParticipationDAOImpl entityLocatorParticipationDAOImpl =
                null;

        try
        {

            if (notificationUid != null)
                notificationVO = getNotification(notificationUid,
                                                 nbsSecurityObj);

            if (notificationVO != null)
                activityLocatorParticipationDTs = notificationVO.getTheActivityLocatorParticipationDTCollection();

            entityLocatorParticipationDAOImpl = (EntityLocatorParticipationDAOImpl)NEDSSDAOFactory.getDAO(
                                                        JNDINames.ENTITY_GROUP_PARTICIPATION_DAO_CLASS);

            if (activityLocatorParticipationDTs != null &&
                entityLocatorParticipationDAOImpl != null)
            {

                Iterator<Object> anIterator = activityLocatorParticipationDTs.iterator();

                if (anIterator.hasNext())
                {
                    activityLocatorParticipationDT = (ActivityLocatorParticipationDT)anIterator.next();
                    entityLocatorParticipationDTs = entityLocatorParticipationDAOImpl.load(activityLocatorParticipationDT.getEntityUid().longValue());
                }
            }
        }
        catch (Exception e)
        {
            logger.fatal("ActControllerEJB.getAllNotificationLocators():" + e.getMessage(),e);
            e.printStackTrace();
            throw new javax.ejb.EJBException(e.getMessage(),e);
        }

        return entityLocatorParticipationDTs;
    }

    /**
     * @roseuid 3BFAF911027F
     * @J2EE_METHOD  --  setAllNotificationLocators
     */
    public void setAllNotificationLocators(Collection<Object> activityLocatorParticipationDTs,
                                           NBSSecurityObj nbsSecurityObj)
                                    throws javax.ejb.EJBException
    {

        EntityLocatorParticipationDAOImpl entityLocatorParticipationDAOImpl =
                null;
        EntityLocatorParticipationDT entityLocatorParticipationDT = null;
        NotificationDT notificationDT = null;
        ActivityLocatorParticipationDT activityLocatorParticipationDT = null;
        NotificationVO notificationVO = new NotificationVO();
        Iterator<Object> anIterator = null;
        logger.debug("Got the setAllNotificationLocators");

        if (activityLocatorParticipationDTs != null)
        {

            try
            {
                entityLocatorParticipationDAOImpl = (EntityLocatorParticipationDAOImpl)NEDSSDAOFactory.getDAO(
                                                            JNDINames.ENTITY_GROUP_PARTICIPATION_DAO_CLASS);
                anIterator = activityLocatorParticipationDTs.iterator();

                if (anIterator.hasNext())
                {
                    activityLocatorParticipationDT = (ActivityLocatorParticipationDT)anIterator.next();
                    notificationVO = getNotification(activityLocatorParticipationDT.getActUid(),
                                                     nbsSecurityObj);
                    notificationDT = notificationVO.getTheNotificationDT();
                    entityLocatorParticipationDT = (EntityLocatorParticipationDT)entityLocatorParticipationDAOImpl.loadEntityLocatorParticipation(activityLocatorParticipationDT.getEntityUid().longValue(),
                                                                                                                                                  activityLocatorParticipationDT.getLocatorUid().longValue());

                    if (activityLocatorParticipationDT.getActUid().equals(notificationDT.getNotificationUid()) &&
                        activityLocatorParticipationDT.getLocatorUid().equals(entityLocatorParticipationDT.getLocatorUid()) &&
                        activityLocatorParticipationDT.getEntityUid().equals(entityLocatorParticipationDT.getEntityUid()))
                    {
                        notificationVO = getNotification(notificationDT.getNotificationUid(),
                                                         nbsSecurityObj);
                        logger.debug("got the notificationVO" +
                                     notificationVO);
                        notificationVO.setTheActivityLocatorParticipationDTCollection(
                                activityLocatorParticipationDTs);
                        setNotification(notificationVO, nbsSecurityObj);
                    }
                }
            }
            catch (Exception e)
            {
                logger.fatal("ActControllerEJB.setAllNotificationLocators():" + e.getMessage(),e);
                e.printStackTrace();
                throw new javax.ejb.EJBException(e.getMessage(),e);
            }
        }
    }

    /**
     * @roseuid 3BFAF9120366
     * @J2EE_METHOD  --  getNotificationPhysicalLocators
     */
    public Collection<Object> getNotificationPhysicalLocators(Long notificationUid,
                                                      NBSSecurityObj nbsSecurityObj)
                                               throws javax.ejb.EJBException
    {

        NotificationVO notificationVO = null;
        Collection<Object> physicalLocatorDTs = new ArrayList<Object> ();
        PhysicalLocatorDT physicalLocatorDT = null;
        Collection<Object> activityLocatorParticipationDTs = new ArrayList<Object> ();
        EntityLocatorParticipationDAOImpl entityLocatorParticipationDAOImpl =
                null;
        EntityLocatorParticipationDT entityLocatorParticipationDT = null;
        ActivityLocatorParticipationDT activityLocatorParticipationDT = null;

        if (notificationUid != null)
            notificationVO = getNotification(notificationUid, nbsSecurityObj);

        if (notificationVO != null)
            activityLocatorParticipationDTs = notificationVO.getTheActivityLocatorParticipationDTCollection();

        try
        {
            entityLocatorParticipationDAOImpl = (EntityLocatorParticipationDAOImpl)NEDSSDAOFactory.getDAO(
                                                        JNDINames.ENTITY_GROUP_PARTICIPATION_DAO_CLASS);

            Iterator<Object> anIterator = null;

            if (activityLocatorParticipationDTs != null &&
                entityLocatorParticipationDAOImpl != null)
            {

                for (anIterator = activityLocatorParticipationDTs.iterator();
                     anIterator.hasNext();)
                {
                    activityLocatorParticipationDT = (ActivityLocatorParticipationDT)anIterator.next();
                    entityLocatorParticipationDT = (EntityLocatorParticipationDT)entityLocatorParticipationDAOImpl.loadEntityLocatorParticipation(activityLocatorParticipationDT.getEntityUid().longValue(),
                                                                                                                                                  activityLocatorParticipationDT.getLocatorUid().longValue());

                    if (entityLocatorParticipationDT != null &&
                        entityLocatorParticipationDT.getClassCd().equalsIgnoreCase(
                                NEDSSConstants.PHYSICAL))
                    {
                        physicalLocatorDT = entityLocatorParticipationDT.getThePhysicalLocatorDT();
                        physicalLocatorDTs.add(physicalLocatorDT);
                    }
                    else

                        continue;
                }
            }
        }
        catch (Exception e)
        {
            logger.fatal("ActControllerEJB.getNotificationPhysicalLocators():" + e.getMessage(),e);
            e.printStackTrace();
            throw new javax.ejb.EJBException(e.getMessage(),e);
        }

        return physicalLocatorDTs;
    }

    /**
     * @roseuid 3BFAF9140319
     * @J2EE_METHOD  --  getNotificationPostalLocators
     */
    public Collection<Object> getNotificationPostalLocators(Long notificationUid,
                                                    NBSSecurityObj nbsSecurityObj)
                                             throws javax.ejb.EJBException
    {

        NotificationVO notificationVO = null;
        Collection<Object> postalLocatorDTs = new ArrayList<Object> ();
        PostalLocatorDT postalLocatorDT = null;
        Collection<Object> activityLocatorParticipationDTs = new ArrayList<Object> ();
        EntityLocatorParticipationDAOImpl entityLocatorParticipationDAOImpl =
                null;
        EntityLocatorParticipationDT entityLocatorParticipationDT = null;
        ActivityLocatorParticipationDT activityLocatorParticipationDT = null;

        if (notificationUid != null)
            notificationVO = getNotification(notificationUid, nbsSecurityObj);

        if (notificationVO != null)
            activityLocatorParticipationDTs = notificationVO.getTheActivityLocatorParticipationDTCollection();

        try
        {
            entityLocatorParticipationDAOImpl = (EntityLocatorParticipationDAOImpl)NEDSSDAOFactory.getDAO(
                                                        JNDINames.ENTITY_GROUP_PARTICIPATION_DAO_CLASS);

            Iterator<Object> anIterator = null;

            if (activityLocatorParticipationDTs != null &&
                entityLocatorParticipationDAOImpl != null)
            {

                for (anIterator = activityLocatorParticipationDTs.iterator();
                     anIterator.hasNext();)
                {
                    activityLocatorParticipationDT = (ActivityLocatorParticipationDT)anIterator.next();
                    entityLocatorParticipationDT = (EntityLocatorParticipationDT)entityLocatorParticipationDAOImpl.loadEntityLocatorParticipation(activityLocatorParticipationDT.getEntityUid().longValue(),
                                                                                                                                                  activityLocatorParticipationDT.getLocatorUid().longValue());

                    if (entityLocatorParticipationDT != null &&
                        entityLocatorParticipationDT.getClassCd().equalsIgnoreCase(
                                NEDSSConstants.POSTAL))
                    {
                        postalLocatorDT = entityLocatorParticipationDT.getThePostalLocatorDT();
                        postalLocatorDTs.add(postalLocatorDT);
                    }
                    else

                        continue;
                }
            }
        }
        catch (Exception e)
        {
            logger.fatal("ActControllerEJB.getNotificationPostalLocators():" + e.getMessage(),e);
            e.printStackTrace();
            throw new javax.ejb.EJBException(e.getMessage(),e);
        }

        return postalLocatorDTs;
    }

    /**
     * @roseuid 3BFAF9170074
     * @J2EE_METHOD  --  getNotificationTeleLocators
     */
    public Collection<Object> getNotificationTeleLocators(Long notificationUid,
                                                  NBSSecurityObj nbsSecurityObj)
                                           throws javax.ejb.EJBException
    {

        NotificationVO notificationVO = null;
        Collection<Object> teleLocatorDTs = new ArrayList<Object> ();
        TeleLocatorDT teleLocatorDT = null;
        Collection<Object> activityLocatorParticipationDTs = new ArrayList<Object> ();
        EntityLocatorParticipationDAOImpl entityLocatorParticipationDAOImpl =
                null;
        EntityLocatorParticipationDT entityLocatorParticipationDT = null;
        ActivityLocatorParticipationDT activityLocatorParticipationDT = null;

        if (notificationUid != null)
            notificationVO = getNotification(notificationUid, nbsSecurityObj);

        if (notificationVO != null)
            activityLocatorParticipationDTs = notificationVO.getTheActivityLocatorParticipationDTCollection();

        try
        {
            entityLocatorParticipationDAOImpl = (EntityLocatorParticipationDAOImpl)NEDSSDAOFactory.getDAO(
                                                        JNDINames.ENTITY_GROUP_PARTICIPATION_DAO_CLASS);

            Iterator<Object> anIterator = null;

            if (activityLocatorParticipationDTs != null &&
                entityLocatorParticipationDAOImpl != null)
            {

                for (anIterator = activityLocatorParticipationDTs.iterator();
                     anIterator.hasNext();)
                {
                    activityLocatorParticipationDT = (ActivityLocatorParticipationDT)anIterator.next();
                    entityLocatorParticipationDT = (EntityLocatorParticipationDT)entityLocatorParticipationDAOImpl.loadEntityLocatorParticipation(activityLocatorParticipationDT.getEntityUid().longValue(),
                                                                                                                                                  activityLocatorParticipationDT.getLocatorUid().longValue());

                    if (entityLocatorParticipationDT != null &&
                        entityLocatorParticipationDT.getClassCd().equalsIgnoreCase(
                                NEDSSConstants.TELE))
                    {
                        teleLocatorDT = entityLocatorParticipationDT.getTheTeleLocatorDT();
                        teleLocatorDTs.add(teleLocatorDT);
                    }
                    else

                        continue;
                }
            }
        }
        catch (Exception e)
        {
            logger.fatal("ActControllerEJB.getNotificationTeleLocators():" + e.getMessage(),e);
            e.printStackTrace();
            throw new javax.ejb.EJBException(e.getMessage(),e);
        }

        return teleLocatorDTs;
    }

    /**
     * @roseuid 3BFAF919030C
     * @J2EE_METHOD  --  getAllInterventionLocators
     */
    public Collection<Object> getAllInterventionLocators(Long interventionUid,
                                                 NBSSecurityObj nbsSecurityObj)
                                          throws javax.ejb.EJBException
    {

        InterventionVO interventionVO = null;
        Collection<Object> activityLocatorParticipationDTs = new ArrayList<Object> ();
        Collection<Object> entityLocatorParticipationDTs = new ArrayList<Object> ();
        ActivityLocatorParticipationDT activityLocatorParticipationDT = null;
        EntityLocatorParticipationDAOImpl entityLocatorParticipationDAOImpl =
                null;

        try
        {

            if (interventionUid != null)
                interventionVO = getIntervention(interventionUid,
                                                 nbsSecurityObj);

            if (interventionVO != null)
                activityLocatorParticipationDTs = interventionVO.getTheActivityLocatorParticipationDTCollection();

            entityLocatorParticipationDAOImpl = (EntityLocatorParticipationDAOImpl)NEDSSDAOFactory.getDAO(
                                                        JNDINames.ENTITY_GROUP_PARTICIPATION_DAO_CLASS);

            if (activityLocatorParticipationDTs != null &&
                entityLocatorParticipationDAOImpl != null)
            {

                Iterator<Object> anIterator = activityLocatorParticipationDTs.iterator();

                if (anIterator.hasNext())
                {
                    activityLocatorParticipationDT = (ActivityLocatorParticipationDT)anIterator.next();
                    entityLocatorParticipationDTs = entityLocatorParticipationDAOImpl.load(activityLocatorParticipationDT.getEntityUid().longValue());
                }
            }
        }
        catch (Exception e)
        {
            logger.fatal("ActControllerEJB.getAllInterventionLocators():" + e.getMessage(),e);
            e.printStackTrace();
            throw new javax.ejb.EJBException(e.getMessage(),e);
        }

        return entityLocatorParticipationDTs;
    }

    /**
     * @roseuid 3BFAF91A03CC
     * @J2EE_METHOD  --  setAllInterventionLocators
     */
    public void setAllInterventionLocators(Collection<Object> activityLocatorParticipationDTs,
                                           NBSSecurityObj nbsSecurityObj)
                                    throws javax.ejb.EJBException
    {

        EntityLocatorParticipationDAOImpl entityLocatorParticipationDAOImpl =
                null;
        EntityLocatorParticipationDT entityLocatorParticipationDT = null;
        InterventionDT interventionDT = null;
        ActivityLocatorParticipationDT activityLocatorParticipationDT = null;
        InterventionVO interventionVO = new InterventionVO();
        Iterator<Object> anIterator = null;
        logger.debug("Got the setAllInterventionLocators");

        if (activityLocatorParticipationDTs != null)
        {

            try
            {
                entityLocatorParticipationDAOImpl = (EntityLocatorParticipationDAOImpl)NEDSSDAOFactory.getDAO(
                                                            JNDINames.ENTITY_GROUP_PARTICIPATION_DAO_CLASS);
                anIterator = activityLocatorParticipationDTs.iterator();

                if (anIterator.hasNext())
                {
                    activityLocatorParticipationDT = (ActivityLocatorParticipationDT)anIterator.next();
                    interventionVO = getIntervention(activityLocatorParticipationDT.getActUid(),
                                                     nbsSecurityObj);
                    interventionDT = interventionVO.getTheInterventionDT();
                    entityLocatorParticipationDT = (EntityLocatorParticipationDT)entityLocatorParticipationDAOImpl.loadEntityLocatorParticipation(activityLocatorParticipationDT.getEntityUid().longValue(),
                                                                                                                                                  activityLocatorParticipationDT.getLocatorUid().longValue());

                    if (activityLocatorParticipationDT.getActUid().equals(interventionDT.getInterventionUid()) &&
                        activityLocatorParticipationDT.getLocatorUid().equals(entityLocatorParticipationDT.getLocatorUid()) &&
                        activityLocatorParticipationDT.getEntityUid().equals(entityLocatorParticipationDT.getEntityUid()))
                    {
                        interventionVO = getIntervention(interventionDT.getInterventionUid(),
                                                         nbsSecurityObj);
                        logger.debug("got the interventionVO" +
                                     interventionVO);
                        interventionVO.setTheActivityLocatorParticipationDTCollection(
                                activityLocatorParticipationDTs);
                        setIntervention(interventionVO, nbsSecurityObj);
                    }
                }
            }
            catch (Exception e)
            {
                logger.fatal("ActControllerEJB.setAllInterventionLocators():" + e.getMessage(),e);
                e.printStackTrace();
                throw new javax.ejb.EJBException(e.getMessage(),e);
            }
        }
    }

    public void setAllReferralLocators(Collection<Object> activityLocatorParticipationDTs,
                                       NBSSecurityObj nbsSecurityObj)
                                throws javax.ejb.EJBException
    {

        EntityLocatorParticipationDAOImpl entityLocatorParticipationDAOImpl =
                null;
        EntityLocatorParticipationDT entityLocatorParticipationDT = null;
        ReferralDT referralDT = null;
        ActivityLocatorParticipationDT activityLocatorParticipationDT = null;
        ReferralVO referralVO = new ReferralVO();
        Iterator<Object> anIterator = null;
        logger.debug("Got the setAllReferralLocators");

        if (activityLocatorParticipationDTs != null)
        {

            try
            {
                entityLocatorParticipationDAOImpl = (EntityLocatorParticipationDAOImpl)NEDSSDAOFactory.getDAO(
                                                            JNDINames.ENTITY_GROUP_PARTICIPATION_DAO_CLASS);
                anIterator = activityLocatorParticipationDTs.iterator();

                if (anIterator.hasNext())
                {
                    activityLocatorParticipationDT = (ActivityLocatorParticipationDT)anIterator.next();
                    referralVO = getReferral(activityLocatorParticipationDT.getActUid(),
                                             nbsSecurityObj);
                    referralDT = referralVO.getTheReferralDT();
                    entityLocatorParticipationDT = (EntityLocatorParticipationDT)entityLocatorParticipationDAOImpl.loadEntityLocatorParticipation(activityLocatorParticipationDT.getEntityUid().longValue(),
                                                                                                                                                  activityLocatorParticipationDT.getLocatorUid().longValue());

                    if (activityLocatorParticipationDT.getActUid().equals(referralDT.getReferralUid()) &&
                        activityLocatorParticipationDT.getLocatorUid().equals(entityLocatorParticipationDT.getLocatorUid()) &&
                        activityLocatorParticipationDT.getEntityUid().equals(entityLocatorParticipationDT.getEntityUid()))
                    {
                        referralVO = getReferral(referralDT.getReferralUid(),
                                                 nbsSecurityObj);
                        logger.debug("got the referralVO" + referralVO);
                        referralVO.setTheActivityLocatorParticipationDTCollection(
                                activityLocatorParticipationDTs);
                        setReferral(referralVO, nbsSecurityObj);
                    }
                }
            }
            catch (Exception e)
            {
                logger.fatal("ActControllerEJB.setAllReferralLocators():" + e.getMessage(),e);
                e.printStackTrace();
                throw new javax.ejb.EJBException(e.getMessage(),e);
            }
        }
    }

    /**
     * @roseuid 3BFAF91C0090
     * @J2EE_METHOD  --  getInterventionPhysicalLocators
     */
    public Collection<Object> getInterventionPhysicalLocators(Long interventionUid,
                                                      NBSSecurityObj nbsSecurityObj)
                                               throws javax.ejb.EJBException
    {

        InterventionVO interventionVO = null;
        Collection<Object> physicalLocatorDTs = new ArrayList<Object> ();
        PhysicalLocatorDT physicalLocatorDT = null;
        Collection<Object> activityLocatorParticipationDTs = new ArrayList<Object> ();
        EntityLocatorParticipationDAOImpl entityLocatorParticipationDAOImpl =
                null;
        EntityLocatorParticipationDT entityLocatorParticipationDT = null;
        ActivityLocatorParticipationDT activityLocatorParticipationDT = null;

        if (interventionUid != null)
            interventionVO = getIntervention(interventionUid, nbsSecurityObj);

        if (interventionVO != null)
            activityLocatorParticipationDTs = interventionVO.getTheActivityLocatorParticipationDTCollection();

        try
        {
            entityLocatorParticipationDAOImpl = (EntityLocatorParticipationDAOImpl)NEDSSDAOFactory.getDAO(
                                                        JNDINames.ENTITY_GROUP_PARTICIPATION_DAO_CLASS);

            Iterator<Object> anIterator = null;

            if (activityLocatorParticipationDTs != null &&
                entityLocatorParticipationDAOImpl != null)
            {

                for (anIterator = activityLocatorParticipationDTs.iterator();
                     anIterator.hasNext();)
                {
                    activityLocatorParticipationDT = (ActivityLocatorParticipationDT)anIterator.next();
                    entityLocatorParticipationDT = (EntityLocatorParticipationDT)entityLocatorParticipationDAOImpl.loadEntityLocatorParticipation(activityLocatorParticipationDT.getEntityUid().longValue(),
                                                                                                                                                  activityLocatorParticipationDT.getLocatorUid().longValue());

                    if (entityLocatorParticipationDT != null &&
                        entityLocatorParticipationDT.getClassCd().equalsIgnoreCase(
                                NEDSSConstants.PHYSICAL))
                    {
                        physicalLocatorDT = entityLocatorParticipationDT.getThePhysicalLocatorDT();
                        physicalLocatorDTs.add(physicalLocatorDT);
                    }
                    else

                        continue;
                }
            }
        }
        catch (Exception e)
        {
            logger.fatal("ActControllerEJB.getInterventionPhysicalLocators():" + e.getMessage(),e);
            e.printStackTrace();
            throw new javax.ejb.EJBException(e.getMessage(),e);
        }

        return physicalLocatorDTs;
    }

    /**
     * @roseuid 3BFAF91F00B2
     * @J2EE_METHOD  --  getInterventionPostalLocators
     */
    public Collection<Object> getInterventionPostalLocators(Long interventionUid,
                                                    NBSSecurityObj nbsSecurityObj)
                                             throws javax.ejb.EJBException
    {

        InterventionVO interventionVO = null;
        Collection<Object> postalLocatorDTs = new ArrayList<Object> ();
        PostalLocatorDT postalLocatorDT = null;
        Collection<Object> activityLocatorParticipationDTs = new ArrayList<Object> ();
        EntityLocatorParticipationDAOImpl entityLocatorParticipationDAOImpl =
                null;
        EntityLocatorParticipationDT entityLocatorParticipationDT = null;
        ActivityLocatorParticipationDT activityLocatorParticipationDT = null;

        if (interventionUid != null)
            interventionVO = getIntervention(interventionUid, nbsSecurityObj);

        if (interventionVO != null)
            activityLocatorParticipationDTs = interventionVO.getTheActivityLocatorParticipationDTCollection();

        try
        {
            entityLocatorParticipationDAOImpl = (EntityLocatorParticipationDAOImpl)NEDSSDAOFactory.getDAO(
                                                        JNDINames.ENTITY_GROUP_PARTICIPATION_DAO_CLASS);

            Iterator<Object> anIterator = null;

            if (activityLocatorParticipationDTs != null &&
                entityLocatorParticipationDAOImpl != null)
            {

                for (anIterator = activityLocatorParticipationDTs.iterator();
                     anIterator.hasNext();)
                {
                    activityLocatorParticipationDT = (ActivityLocatorParticipationDT)anIterator.next();
                    entityLocatorParticipationDT = (EntityLocatorParticipationDT)entityLocatorParticipationDAOImpl.loadEntityLocatorParticipation(activityLocatorParticipationDT.getEntityUid().longValue(),
                                                                                                                                                  activityLocatorParticipationDT.getLocatorUid().longValue());

                    if (entityLocatorParticipationDT != null &&
                        entityLocatorParticipationDT.getClassCd().equalsIgnoreCase(
                                NEDSSConstants.POSTAL))
                    {
                        postalLocatorDT = entityLocatorParticipationDT.getThePostalLocatorDT();
                        postalLocatorDTs.add(postalLocatorDT);
                    }
                    else

                        continue;
                }
            }
        }
        catch (Exception e)
        {
            logger.fatal("ActControllerEJB.getInterventionPostalLocators():" + e.getMessage(),e);
            e.printStackTrace();
            throw new javax.ejb.EJBException(e.getMessage(),e);
        }

        return postalLocatorDTs;
    }

    /**
     * @roseuid 3BFAF92100A1
     * @J2EE_METHOD  --  getInterventionTeleLocators
     */
    public Collection<Object> getInterventionTeleLocators(Long interventionUid,
                                                  NBSSecurityObj nbsSecurityObj)
                                           throws javax.ejb.EJBException
    {

        InterventionVO interventionVO = null;
        Collection<Object> teleLocatorDTs = new ArrayList<Object> ();
        TeleLocatorDT teleLocatorDT = null;
        Collection<Object> activityLocatorParticipationDTs = new ArrayList<Object> ();
        EntityLocatorParticipationDAOImpl entityLocatorParticipationDAOImpl =
                null;
        EntityLocatorParticipationDT entityLocatorParticipationDT = null;
        ActivityLocatorParticipationDT activityLocatorParticipationDT = null;

        if (interventionUid != null)
            interventionVO = getIntervention(interventionUid, nbsSecurityObj);

        if (interventionVO != null)
            activityLocatorParticipationDTs = interventionVO.getTheActivityLocatorParticipationDTCollection();

        try
        {
            entityLocatorParticipationDAOImpl = (EntityLocatorParticipationDAOImpl)NEDSSDAOFactory.getDAO(
                                                        JNDINames.ENTITY_GROUP_PARTICIPATION_DAO_CLASS);

            Iterator<Object> anIterator = null;

            if (activityLocatorParticipationDTs != null &&
                entityLocatorParticipationDAOImpl != null)
            {

                for (anIterator = activityLocatorParticipationDTs.iterator();
                     anIterator.hasNext();)
                {
                    activityLocatorParticipationDT = (ActivityLocatorParticipationDT)anIterator.next();
                    entityLocatorParticipationDT = (EntityLocatorParticipationDT)entityLocatorParticipationDAOImpl.loadEntityLocatorParticipation(activityLocatorParticipationDT.getEntityUid().longValue(),
                                                                                                                                                  activityLocatorParticipationDT.getLocatorUid().longValue());

                    if (entityLocatorParticipationDT != null &&
                        entityLocatorParticipationDT.getClassCd().equalsIgnoreCase(
                                NEDSSConstants.TELE))
                    {
                        teleLocatorDT = entityLocatorParticipationDT.getTheTeleLocatorDT();
                        teleLocatorDTs.add(teleLocatorDT);
                    }
                    else

                        continue;
                }
            }
        }
        catch (Exception e)
        {
            logger.fatal("ActControllerEJB.getInterventionTeleLocators():" + e.getMessage(),e);
            e.printStackTrace();
            throw new javax.ejb.EJBException(e.getMessage(),e);
        }

        return teleLocatorDTs;
    }

    /**
     * @roseuid 3BFAF9230220
     * @J2EE_METHOD  --  getAllPatientEncounterLocators
     */
    public Collection<Object> getAllPatientEncounterLocators(Long patientEncounterUid,
                                                     NBSSecurityObj nbsSecurityObj)
                                              throws javax.ejb.EJBException
    {

        PatientEncounterVO patientEncounterVO = null;
        Collection<Object> activityLocatorParticipationDTs = new ArrayList<Object> ();
        Collection<Object> entityLocatorParticipationDTs = new ArrayList<Object> ();
        ActivityLocatorParticipationDT activityLocatorParticipationDT = null;
        EntityLocatorParticipationDAOImpl entityLocatorParticipationDAOImpl =
                null;

        try
        {

            if (patientEncounterUid != null)
                patientEncounterVO = getPatientEncounter(patientEncounterUid,
                                                         nbsSecurityObj);

            if (patientEncounterVO != null)
                activityLocatorParticipationDTs = patientEncounterVO.getTheActivityLocatorParticipationDTCollection();

            entityLocatorParticipationDAOImpl = (EntityLocatorParticipationDAOImpl)NEDSSDAOFactory.getDAO(
                                                        JNDINames.ENTITY_GROUP_PARTICIPATION_DAO_CLASS);

            if (activityLocatorParticipationDTs != null &&
                entityLocatorParticipationDAOImpl != null)
            {

                Iterator<Object> anIterator = activityLocatorParticipationDTs.iterator();

                if (anIterator.hasNext())
                {
                    activityLocatorParticipationDT = (ActivityLocatorParticipationDT)anIterator.next();
                    entityLocatorParticipationDTs = entityLocatorParticipationDAOImpl.load(activityLocatorParticipationDT.getEntityUid().longValue());
                }
            }
        }
        catch (Exception e)
        {
            logger.fatal("ActControllerEJB.getAllPatientEncounterLocators():" + e.getMessage(),e);
            e.printStackTrace();
            throw new javax.ejb.EJBException(e.getMessage(),e);
        }

        return entityLocatorParticipationDTs;
    }

    /**
     * @roseuid 3BFAF9240204
     * @J2EE_METHOD  --  setAllPatientEncounterLocators
     */
    public void setAllPatientEncounterLocators(Collection<Object> activityLocatorParticipationDTs,
                                               NBSSecurityObj nbsSecurityObj)
                                        throws javax.ejb.EJBException
    {

        EntityLocatorParticipationDAOImpl entityLocatorParticipationDAOImpl =
                null;
        EntityLocatorParticipationDT entityLocatorParticipationDT = null;
        PatientEncounterDT patientEncounterDT = null;
        ActivityLocatorParticipationDT activityLocatorParticipationDT = null;
        PatientEncounterVO patientEncounterVO = new PatientEncounterVO();
        Iterator<Object> anIterator = null;
        logger.debug("Got the setAllPatientEncounterLocators");

        if (activityLocatorParticipationDTs != null)
        {

            try
            {
                entityLocatorParticipationDAOImpl = (EntityLocatorParticipationDAOImpl)NEDSSDAOFactory.getDAO(
                                                            JNDINames.ENTITY_GROUP_PARTICIPATION_DAO_CLASS);
                anIterator = activityLocatorParticipationDTs.iterator();

                if (anIterator.hasNext())
                {
                    activityLocatorParticipationDT = (ActivityLocatorParticipationDT)anIterator.next();
                    patientEncounterVO = getPatientEncounter(activityLocatorParticipationDT.getActUid(),
                                                             nbsSecurityObj);
                    patientEncounterDT = patientEncounterVO.getThePatientEncounterDT();
                    entityLocatorParticipationDT = (EntityLocatorParticipationDT)entityLocatorParticipationDAOImpl.loadEntityLocatorParticipation(activityLocatorParticipationDT.getEntityUid().longValue(),
                                                                                                                                                  activityLocatorParticipationDT.getLocatorUid().longValue());

                    if (activityLocatorParticipationDT.getActUid().equals(patientEncounterDT.getPatientEncounterUid()) &&
                        activityLocatorParticipationDT.getLocatorUid().equals(entityLocatorParticipationDT.getLocatorUid()) &&
                        activityLocatorParticipationDT.getEntityUid().equals(entityLocatorParticipationDT.getEntityUid()))
                    {
                        patientEncounterVO = getPatientEncounter(patientEncounterDT.getPatientEncounterUid(),
                                                                 nbsSecurityObj);
                        logger.debug(
                                "got the patientEncounterVO" +
                                patientEncounterVO);
                        patientEncounterVO.setTheActivityLocatorParticipationDTCollection(
                                activityLocatorParticipationDTs);
                        setPatientEncounter(patientEncounterVO, nbsSecurityObj);
                    }
                }
            }
            catch (Exception e)
            {
                logger.fatal("ActControllerEJB.setAllPatientEncounterLocators():" + e.getMessage(),e);
                e.printStackTrace();
                throw new javax.ejb.EJBException(e.getMessage(),e);
            }
        }
    }

    /**
     * @roseuid 3BFAF92501E7
     * @J2EE_METHOD  --  getPatientEncounterPhysicalLocators
     */
    public Collection<Object> getPatientEncounterPhysicalLocators(Long patientEncounterUid,
                                                          NBSSecurityObj nbsSecurityObj)
        throws javax.ejb.EJBException
    {

        PatientEncounterVO patientEncounterVO = null;
        Collection<Object> physicalLocatorDTs = new ArrayList<Object> ();
        PhysicalLocatorDT physicalLocatorDT = null;
        Collection<Object> activityLocatorParticipationDTs = new ArrayList<Object> ();
        EntityLocatorParticipationDAOImpl entityLocatorParticipationDAOImpl =
                null;
        EntityLocatorParticipationDT entityLocatorParticipationDT = null;
        ActivityLocatorParticipationDT activityLocatorParticipationDT = null;

        if (patientEncounterUid != null)
            patientEncounterVO = getPatientEncounter(patientEncounterUid,
                                                     nbsSecurityObj);

        if (patientEncounterVO != null)
            activityLocatorParticipationDTs = patientEncounterVO.getTheActivityLocatorParticipationDTCollection();

        try
        {
            entityLocatorParticipationDAOImpl = (EntityLocatorParticipationDAOImpl)NEDSSDAOFactory.getDAO(
                                                        JNDINames.ENTITY_GROUP_PARTICIPATION_DAO_CLASS);

            Iterator<Object> anIterator = null;

            if (activityLocatorParticipationDTs != null &&
                entityLocatorParticipationDAOImpl != null)
            {

                for (anIterator = activityLocatorParticipationDTs.iterator();
                     anIterator.hasNext();)
                {
                    activityLocatorParticipationDT = (ActivityLocatorParticipationDT)anIterator.next();
                    entityLocatorParticipationDT = (EntityLocatorParticipationDT)entityLocatorParticipationDAOImpl.loadEntityLocatorParticipation(activityLocatorParticipationDT.getEntityUid().longValue(),
                                                                                                                                                  activityLocatorParticipationDT.getLocatorUid().longValue());

                    if (entityLocatorParticipationDT != null &&
                        entityLocatorParticipationDT.getClassCd().equalsIgnoreCase(
                                NEDSSConstants.PHYSICAL))
                    {
                        physicalLocatorDT = entityLocatorParticipationDT.getThePhysicalLocatorDT();
                        physicalLocatorDTs.add(physicalLocatorDT);
                    }
                    else

                        continue;
                }
            }
        }
        catch (Exception e)
        {
            logger.fatal("ActControllerEJB.getPatientEncounterPhysicalLocators():" + e.getMessage(),e);
            e.printStackTrace();
            throw new javax.ejb.EJBException(e.getMessage(),e);
        }

        return physicalLocatorDTs;
    }

    /**
     * @roseuid 3BFAF92701FE
     * @J2EE_METHOD  --  getPatientEncounterPostalLocators
     */
    public Collection<Object> getPatientEncounterPostalLocators(Long patientEncounterUid,
                                                        NBSSecurityObj nbsSecurityObj)
                                                 throws javax.ejb.EJBException
    {

        PatientEncounterVO patientEncounterVO = null;
        Collection<Object> postalLocatorDTs = new ArrayList<Object> ();
        PostalLocatorDT postalLocatorDT = null;
        Collection<Object> activityLocatorParticipationDTs = new ArrayList<Object> ();
        EntityLocatorParticipationDAOImpl entityLocatorParticipationDAOImpl =
                null;
        EntityLocatorParticipationDT entityLocatorParticipationDT = null;
        ActivityLocatorParticipationDT activityLocatorParticipationDT = null;

        if (patientEncounterUid != null)
            patientEncounterVO = getPatientEncounter(patientEncounterUid,
                                                     nbsSecurityObj);

        if (patientEncounterVO != null)
            activityLocatorParticipationDTs = patientEncounterVO.getTheActivityLocatorParticipationDTCollection();

        try
        {
            entityLocatorParticipationDAOImpl = (EntityLocatorParticipationDAOImpl)NEDSSDAOFactory.getDAO(
                                                        JNDINames.ENTITY_GROUP_PARTICIPATION_DAO_CLASS);

            Iterator<Object> anIterator = null;

            if (activityLocatorParticipationDTs != null &&
                entityLocatorParticipationDAOImpl != null)
            {

                for (anIterator = activityLocatorParticipationDTs.iterator();
                     anIterator.hasNext();)
                {
                    activityLocatorParticipationDT = (ActivityLocatorParticipationDT)anIterator.next();
                    entityLocatorParticipationDT = (EntityLocatorParticipationDT)entityLocatorParticipationDAOImpl.loadEntityLocatorParticipation(activityLocatorParticipationDT.getEntityUid().longValue(),
                                                                                                                                                  activityLocatorParticipationDT.getLocatorUid().longValue());

                    if (entityLocatorParticipationDT != null &&
                        entityLocatorParticipationDT.getClassCd().equalsIgnoreCase(
                                NEDSSConstants.POSTAL))
                    {
                        postalLocatorDT = entityLocatorParticipationDT.getThePostalLocatorDT();
                        postalLocatorDTs.add(postalLocatorDT);
                    }
                    else

                        continue;
                }
            }
        }
        catch (Exception e)
        {
            logger.fatal("ActControllerEJB.getPatientEncounterPostalLocators():" + e.getMessage(),e);
            e.printStackTrace();
            throw new javax.ejb.EJBException(e.getMessage(),e);
        }

        return postalLocatorDTs;
    }

    /**
     * @roseuid 3BFAF929021F
     * @J2EE_METHOD  --  getPatientEncounterTeleLocators
     */
    public Collection<Object> getPatientEncounterTeleLocators(Long patientEncounterUid,
                                                      NBSSecurityObj nbsSecurityObj)
                                               throws javax.ejb.EJBException
    {

        PatientEncounterVO patientEncounterVO = null;
        Collection<Object> teleLocatorDTs = new ArrayList<Object> ();
        TeleLocatorDT teleLocatorDT = null;
        Collection<Object> activityLocatorParticipationDTs = new ArrayList<Object> ();
        EntityLocatorParticipationDAOImpl entityLocatorParticipationDAOImpl =
                null;
        EntityLocatorParticipationDT entityLocatorParticipationDT = null;
        ActivityLocatorParticipationDT activityLocatorParticipationDT = null;

        if (patientEncounterUid != null)
            patientEncounterVO = getPatientEncounter(patientEncounterUid,
                                                     nbsSecurityObj);

        if (patientEncounterVO != null)
            activityLocatorParticipationDTs = patientEncounterVO.getTheActivityLocatorParticipationDTCollection();

        try
        {
            entityLocatorParticipationDAOImpl = (EntityLocatorParticipationDAOImpl)NEDSSDAOFactory.getDAO(
                                                        JNDINames.ENTITY_GROUP_PARTICIPATION_DAO_CLASS);

            Iterator<Object> anIterator = null;

            if (activityLocatorParticipationDTs != null &&
                entityLocatorParticipationDAOImpl != null)
            {

                for (anIterator = activityLocatorParticipationDTs.iterator();
                     anIterator.hasNext();)
                {
                    activityLocatorParticipationDT = (ActivityLocatorParticipationDT)anIterator.next();
                    entityLocatorParticipationDT = (EntityLocatorParticipationDT)entityLocatorParticipationDAOImpl.loadEntityLocatorParticipation(activityLocatorParticipationDT.getEntityUid().longValue(),
                                                                                                                                                  activityLocatorParticipationDT.getLocatorUid().longValue());

                    if (entityLocatorParticipationDT != null &&
                        entityLocatorParticipationDT.getClassCd().equalsIgnoreCase(
                                NEDSSConstants.TELE))
                    {
                        teleLocatorDT = entityLocatorParticipationDT.getTheTeleLocatorDT();
                        teleLocatorDTs.add(teleLocatorDT);
                    }
                    else

                        continue;
                }
            }
        }
        catch (Exception e)
        {
            logger.fatal("ActControllerEJB.getPatientEncounterTeleLocators():" + e.getMessage(),e);
            e.printStackTrace();
            throw new javax.ejb.EJBException(e.getMessage(),e);
        }

        return teleLocatorDTs;
    }

    /**
     * @roseuid 3BFAF92B0380
     * @J2EE_METHOD  --  getActRelationship
     */
    public ActRelationshipDT getActRelationship(Long actUid, Long sourceActUid,
                                                NBSSecurityObj nbsSecurityObj)
                                         throws javax.ejb.EJBException
    {
    	try{
        Collection<Object> actRelationshipDTs = new ArrayList<Object> ();
        ActRelationshipDT actRelationshipDT = null;
        actRelationshipDTs = getActRelationships(actUid, nbsSecurityObj);

        Iterator<Object> anIterator = null;

        for (anIterator = actRelationshipDTs.iterator(); anIterator.hasNext();)
        {
            actRelationshipDT = (ActRelationshipDT)anIterator.next();

            if ((sourceActUid != null) &&
                (actRelationshipDT.getSourceActUid().longValue() == sourceActUid.longValue()))
            {

                return actRelationshipDT;
            }
            else
            {

                continue;
            }
        }

        return null;
    }
    catch (Exception e)
    {
    logger.fatal("ActControllerEJB.getActRelationship:" + e.getMessage(), e);
    throw new javax.ejb.EJBException(e.getMessage(),e);
    }
    }

    /**
     * @roseuid 3BFAF92D02D9
     * @J2EE_METHOD  --  getActRelationships
     */
    public Collection<Object> getActRelationships(Long actUid,
                                          NBSSecurityObj nbsSecurityObj)
                                   throws javax.ejb.EJBException
    {

        ActRelationshipDAOImpl actRelationshipDaoImpl = null;
        Collection<Object> actRelationshipDTs = new ArrayList<Object> ();
       
        try
        {
            actRelationshipDaoImpl = (ActRelationshipDAOImpl)NEDSSDAOFactory.getDAO(
                                             JNDINames.ACT_RELATIONSHIP_DAO_CLASS);

            if (actRelationshipDaoImpl != null)
                actRelationshipDTs = actRelationshipDaoImpl.load(actUid);
        }
        catch (Exception e)
        {
            logger.fatal("ActControllerEJB.getActRelationships():" + e.getMessage(),e);
            e.printStackTrace();
            throw new javax.ejb.EJBException(e.getMessage(),e);
        }

        return actRelationshipDTs;
    }

    /**
     * @roseuid 3BFAF92F03A4
     * @J2EE_METHOD  --  getParticipation
     */
    public ParticipationDT getParticipation(Long subjectEntityUid, Long actUid,
                                            Integer participationSeq,
                                            String classCd, Integer roleSeq,
                                            NBSSecurityObj nbsSecurityObj)
                                     throws javax.ejb.EJBException
    {
    	try{
        Collection<Object> participationDTs = new ArrayList<Object> ();
        ParticipationDT participationDT = null;

        if ((participationSeq != null) && (classCd != null) &&
            (roleSeq != null))
        {
            participationDTs = getParticipations(subjectEntityUid, actUid,
                                                 nbsSecurityObj);

            Iterator<Object> anIterator = null;

            for (anIterator = participationDTs.iterator();
                 anIterator.hasNext();)
            {
                participationDT = (ParticipationDT)anIterator.next();

                //if((participationDT.getParticipationSeq().intValue() == participationSeq.intValue()) && ((participationDT.getSubjectClassCd()).compareTo(classCd.trim()) == 0) && ( participationDT.getRoleSeq().intValue() == roleSeq.intValue()))
                if (((participationDT.getSubjectClassCd()).compareTo(classCd.trim()) == 0) &&
                    (participationDT.getRoleSeq().intValue() == roleSeq.intValue())) /*(participationDT.getParticipationSeq().intValue() == participationSeq.intValue()) &&*/
                {

                    return participationDT;
                }
                else
                {

                    continue;
                }
            }
        }

        return null;
    }
    catch (Exception e)
    {
    logger.fatal("ActControllerEJB.getParticipation:" + e.getMessage(), e);
    throw new javax.ejb.EJBException(e.getMessage(),e);
    }
    }

    /**
     * @roseuid 3BFAF9330115
     * @J2EE_METHOD  --  getParticipations
     */
    public Collection<Object> getParticipations(Long subjectEntityUid, Long actUid,
                                        NBSSecurityObj nbsSecurityObj)
                                 throws javax.ejb.EJBException
    {

        ParticipationDAOImpl participationDAOImpl = null;
        Collection<Object> participationDTs = new ArrayList<Object> ();
        Collection<Object> newParticipationDTs = new ArrayList<Object> ();
        ParticipationDT participationDT = null;

        try
        {
            participationDAOImpl = (ParticipationDAOImpl)NEDSSDAOFactory.getDAO(
                                           JNDINames.ACT_PARTICIPATION_DAO_CLASS);

            if (participationDAOImpl != null && subjectEntityUid != null)
                participationDTs = participationDAOImpl.load(subjectEntityUid);

            Iterator<Object> anIterator = null;

            for (anIterator = participationDTs.iterator();
                 anIterator.hasNext();)
            {
                participationDT = (ParticipationDT)anIterator.next();

                if ((actUid != null) &&
                    (participationDT.getActUid().longValue()) == actUid.longValue())
                {
                    newParticipationDTs.add(participationDT);
                }
                else
                {

                    continue;
                }
            }
        }
        catch (Exception e)
        {
            logger.fatal("ActControllerEJB.getParticipations():" + e.getMessage(),e);
            e.printStackTrace();
            throw new javax.ejb.EJBException(e.getMessage(),e);
        }

        return newParticipationDTs;
    }

    /**
     * @roseuid 3C0CFD4D0066
     * @J2EE_METHOD  --  setAllClinicalDocumentLocators
     */
    public void setAllClinicalDocumentLocators(Collection<Object> activityLocatorParticipationDTs,
                                               NBSSecurityObj nbsSecurityObj)
                                        throws javax.ejb.EJBException
    {

        EntityLocatorParticipationDAOImpl entityLocatorParticipationDAOImpl =
                null;
        EntityLocatorParticipationDT entityLocatorParticipationDT = null;
        ClinicalDocumentDT clinicalDocumentDT = null;
        ActivityLocatorParticipationDT activityLocatorParticipationDT = null;
        ClinicalDocumentVO clinicalDocumentVO = new ClinicalDocumentVO();
        Iterator<Object> anIterator = null;
        logger.debug("Got the setAllClinicalDocumentLocators");

        if (activityLocatorParticipationDTs != null)
        {

            try
            {
                entityLocatorParticipationDAOImpl = (EntityLocatorParticipationDAOImpl)NEDSSDAOFactory.getDAO(
                                                            JNDINames.ENTITY_GROUP_PARTICIPATION_DAO_CLASS);
                anIterator = activityLocatorParticipationDTs.iterator();

                if (anIterator.hasNext())
                {
                    activityLocatorParticipationDT = (ActivityLocatorParticipationDT)anIterator.next();
                    clinicalDocumentVO = getClinicalDocument(activityLocatorParticipationDT.getActUid(),
                                                             nbsSecurityObj);
                    clinicalDocumentDT = clinicalDocumentVO.getTheClinicalDocumentDT();
                    entityLocatorParticipationDT = (EntityLocatorParticipationDT)entityLocatorParticipationDAOImpl.loadEntityLocatorParticipation(activityLocatorParticipationDT.getEntityUid().longValue(),
                                                                                                                                                  activityLocatorParticipationDT.getLocatorUid().longValue());
                    logger.debug(
                            "Got the activityLocatorParticipationDT.getActUid() :" +
                            activityLocatorParticipationDT.getActUid());
                    logger.debug(
                            "Got the clinicalDocumentDT.getClinicalDocumentUid() :" +
                            clinicalDocumentDT.getClinicalDocumentUid());
                    logger.debug(
                            "Got the activityLocatorParticipationDT.getLocatorUid() :" +
                            activityLocatorParticipationDT.getLocatorUid());
                    logger.debug(
                            "Got the entityLocatorParticipationDT.getLocatorUid() :" +
                            entityLocatorParticipationDT.getLocatorUid());
                    logger.debug(
                            "Got the activityLocatorParticipationDT.getEntityUid() :" +
                            activityLocatorParticipationDT.getEntityUid());
                    logger.debug(
                            "Got the entityLocatorParticipationDT.getEntityUid() :" +
                            entityLocatorParticipationDT.getEntityUid());
                    logger.debug(
                            "activityLocatorParticipationDT.getActUid() == clinicalDocumentDT.getClinicalDocumentUid() :" +
                            (activityLocatorParticipationDT.getActUid().compareTo(clinicalDocumentDT.getClinicalDocumentUid())));
                    logger.debug(
                            "Got the activityLocatorParticipationDT.getLocatorUid() == entityLocatorParticipationDT.getLocatorUid() :" +
                            (activityLocatorParticipationDT.getLocatorUid().equals(entityLocatorParticipationDT.getLocatorUid())));
                    logger.debug(
                            "Got the activityLocatorParticipationDT.getEntityUid() == entityLocatorParticipationDT.getEntityUid() :" +
                            (activityLocatorParticipationDT.getEntityUid() == entityLocatorParticipationDT.getEntityUid()));
                    logger.debug(
                            "Got the All :" +
                            (activityLocatorParticipationDT.getActUid().equals(clinicalDocumentDT.getClinicalDocumentUid()) && activityLocatorParticipationDT
                              .getLocatorUid().equals(entityLocatorParticipationDT.getLocatorUid()) &&
                                activityLocatorParticipationDT.getEntityUid().equals(entityLocatorParticipationDT.getEntityUid())));

                    if (activityLocatorParticipationDT.getActUid().equals(clinicalDocumentDT.getClinicalDocumentUid()) &&
                        activityLocatorParticipationDT.getLocatorUid().equals(entityLocatorParticipationDT.getLocatorUid()) &&
                        activityLocatorParticipationDT.getEntityUid().equals(entityLocatorParticipationDT.getEntityUid()))
                    {
                        logger.debug(
                                "Got the setAllClinicalDocumentLocators 5");
                        clinicalDocumentVO = getClinicalDocument(clinicalDocumentDT.getClinicalDocumentUid(),
                                                                 nbsSecurityObj);
                        logger.debug(
                                "got the clinicalDocumentVO" +
                                clinicalDocumentVO);
                        clinicalDocumentVO.setTheActivityLocatorParticipationDTCollection(
                                activityLocatorParticipationDTs);
                        setClinicalDocument(clinicalDocumentVO, nbsSecurityObj);
                    }
                }
            }
            catch (Exception e)
            {
                logger.fatal("ActControllerEJB.setAllClinicalDocumentLocators():" + e.getMessage(),e);

                throw new javax.ejb.EJBException(e.getMessage(),e);
            }
        }
    }

    /**
     * @roseuid 3C02C5140136
     * @J2EE_METHOD  --  setSessionContext
     * Set the associated session context. The container calls this method after the instance
     * creation. The enterprise Bean instance should store the reference to the context
     * object in an instance variable. This method is called with no transaction context.
     */
    public void setSessionContext(SessionContext sessioncontext)
                           throws EJBException, RemoteException
    {
    }

    /**
     * @J2EE_METHOD  --  approveNotification
     */

    //added by John Park - deleted  by iyer - deprecated - 03/28/02
    public void approveNotification(NotificationVO notificationVO,
                                    NBSSecurityObj nbsSecurityObj)
                             throws javax.ejb.EJBException
    {
    }

    /**
     * @roseuid 3C0E9F090028

    /**
     * @J2EE_METHOD  --  rejectNotification
     */
    public void rejectNotification(NotificationVO notificationVO,
                                   NBSSecurityObj nbsSecurityObj)
                            throws javax.ejb.EJBException
    {

        try
        {

            Notification notification = null;
            NotificationDT notificationDT = new NotificationDT();
            notificationDT = notificationVO.getTheNotificationDT();
            logger.debug(
                    "notificationDT recordCD: " +
                    notificationDT.getRecordStatusCd());
            notificationDT.setRecordStatusCd(
                    NEDSSConstants.NOTIFICATION_REJECTED_CODE);
            notificationVO.setTheNotificationDT(notificationDT);

            NedssUtils nedssUtils = new NedssUtils();
            Object obj = nedssUtils.lookupBean(JNDINames.NotificationEJB);
            logger.debug("NotificationEJB lookup = " + obj.toString());

            NotificationHome home = (NotificationHome)PortableRemoteObject.narrow(
                                            obj,NotificationHome.class);
            notification = home.findByPrimaryKey(notificationVO.getTheNotificationDT().getNotificationUid());
            notification.setNotificationVO(notificationVO);
            logger.debug(
                    " ActControllerEJB.rejectNotification() Notification Updated");

        }
        catch (Exception e)
        {
            logger.fatal("ActControllerEJB.rejectNotification: " + e.getMessage(),e);

           throw new javax.ejb.EJBException(e.getMessage(),e);
        }
    }

    
    /**
     * @roseuid 3C4DDCEC0219
     * @J2EE_METHOD  --  getObservationsByRecordStatus
     */
    public java.util.Collection<Object> getObservationsByRecordStatus(String recordStatusCd,
                                                              NBSSecurityObj nbsSecurityObj)
        throws javax.ejb.EJBException
    {

        return null;
    }

    /**
     * @roseuid 3C4DDCEE0122
     * @J2EE_METHOD  --  getNotificationsByRecordStatus
     */
    public java.util.Collection<Object> getNotificationsByRecordStatus(String recordStatusCd,
                                                               NBSSecurityObj nbsSecurityObj)
    {

        return null;
    }

    /**
     * @roseuid 3C4DDCF00034
     * @J2EE_METHOD  --  getInvestigationsByRecordStatus
     */
    public java.util.Collection<Object> getInvestigationsByRecordStatus(String recordStatusCd,
                                                                NBSSecurityObj nbsSecurityObj)
    {

        return null;
    }

    private Collection<Object> iterateALPDT(Collection<Object> dtCol,
                                    NBSSecurityObj nbsSecurityObj)
    {

        Collection<Object> retCol = new ArrayList<Object> ();
        Collection<Object> collection = new ArrayList<Object> ();
        collection = dtCol;

        Iterator<Object> anIterator = null;
        PrepareVOUtils prepareVOUtils = new PrepareVOUtils();

        if (collection != null)
        {

            try
            {

                for (anIterator = collection.iterator(); anIterator.hasNext();)
                {

                    ActivityLocatorParticipationDT alpDT = (ActivityLocatorParticipationDT)anIterator.next();
                    AssocDTInterface assocDTInterface = alpDT;
                    alpDT = (ActivityLocatorParticipationDT)prepareVOUtils.prepareAssocDT(
                                    assocDTInterface, nbsSecurityObj);
                    retCol.add(alpDT);
                }
            }
            catch (Exception e)
            {
                logger.fatal(
                        "ActControllerEJB.iterateALPDT exception e = " + e.getMessage(), e);
                throw new EJBException(e.getMessage(),e);
            }
        }

        return retCol;
    }

    private Collection<Object> iterateARDT(Collection<Object> dtCol,
                                   NBSSecurityObj nbsSecurityObj)
    {

        Collection<Object> retCol = new ArrayList<Object> ();
        Collection<Object> collection = new ArrayList<Object> ();
        Iterator<Object> anIterator = null;
        collection = dtCol;
        if (collection != null)
        {
            try
            {
                PrepareVOUtils prepareVOUtils = new PrepareVOUtils();
                for (anIterator = collection.iterator(); anIterator.hasNext();)
                {
                    ActRelationshipDT arDT = (ActRelationshipDT)anIterator.next();
                    AssocDTInterface assocDTInterface = arDT;
                    if(arDT.isItDirty() || arDT.isItNew() || arDT.isItDelete())
                    {
                        logger.debug("ardT.IsItDelete:"+ arDT.isItDelete() +":ardt.IsItNew:" + arDT.isItNew()+":ardt.IsItDirty:" + arDT.isItDirty() );
                        arDT = (ActRelationshipDT)prepareVOUtils.prepareAssocDT(
                        assocDTInterface, nbsSecurityObj);
                        retCol.add(arDT);
                    }
                }
            }
            catch (Exception e)
            {
                logger.fatal(
                        "ActControllerEJB.iterateARDT exception e = " + e.getMessage(), e);
                throw new EJBException(e.getMessage(),e);
            }
        }

        return retCol;
    }

    private Collection<Object> iteratePDT(Collection<Object> dtCol,
                                  NBSSecurityObj nbsSecurityObj)
    {

        Collection<Object> retCol = new ArrayList<Object> ();
        Collection<Object> collection = new ArrayList<Object> ();
        collection = dtCol;

        Iterator<Object> anIterator = null;

        if (collection != null)
        {

            try
            {

                PrepareVOUtils prepareVOUtils = new PrepareVOUtils();

                for (anIterator = collection.iterator(); anIterator.hasNext();)
                {

                    ParticipationDT pDT = (ParticipationDT)anIterator.next();
                    AssocDTInterface assocDTInterface = pDT;
                    if(pDT.isItDirty() || pDT.isItNew() || pDT.isItDelete())
                    {
                        logger.debug("pdT.IsItDelete"+pDT.isItDelete() +"pdt.IsItNew:" + pDT.isItNew()+"pdt.IsItDirty:" + pDT.isItDirty() );
                        pDT = (ParticipationDT)prepareVOUtils.prepareAssocDT(assocDTInterface, nbsSecurityObj);
                        retCol.add(pDT);
                    }
                }
            }
            catch (Exception e)
            {
                logger.fatal(
                        "ActControllerEJB.iteratePDT exception e = " + e.getMessage(), e);
                throw new EJBException(e.getMessage(),e);
            }
        }

        return retCol;
    }

    /**
     * @roseuid 3EA413710307
     * @J2EE_METHOD  --  setTreatment
     */

    public Long setTreatment(TreatmentVO treatmentVO,
                                NBSSecurityObj nbsSecurityObj)
                         throws javax.ejb.EJBException,
                                NEDSSConcurrentDataException,
                                NEDSSSystemException
    {

        Long treatmentUid = new Long(-1);

        //       try
        //     {
        Treatment treatment = null;
        NedssUtils nedssUtils = new NedssUtils();
        Object obj = nedssUtils.lookupBean(JNDINames.TreatmentEJB);
        logger.debug("TreatmentEJB lookup = " + obj.toString());

        TreatmentHome home = (TreatmentHome)PortableRemoteObject.narrow(
                                        obj,TreatmentHome.class);
        logger.debug("Found TreatmentHome: " + home);

        Collection<Object> alpDTCol = treatmentVO.getTheActivityLocatorParticipationDTCollection();
        Collection<Object> arDTCol = treatmentVO.getTheActRelationshipDTCollection();
        Collection<Object> pDTCol = treatmentVO.getTheParticipationDTCollection();
        Collection<Object> col = null;

        if (alpDTCol != null)
        {
            col = this.iterateALPDT(alpDTCol, nbsSecurityObj);
            treatmentVO.setTheActivityLocatorParticipationDTCollection(col);
        }

        if (arDTCol != null)
        {
            col = this.iterateARDT(arDTCol, nbsSecurityObj);
            treatmentVO.setTheActRelationshipDTCollection(col);
        }

        if (pDTCol != null)
        {
            col = this.iteratePDT(pDTCol, nbsSecurityObj);
            treatmentVO.setTheParticipationDTCollection(col);
        }

        if (treatmentVO.isItNew())
        {
            logger.debug("testing treatment in create");
            try
            {
            treatment = home.create(treatmentVO);
            treatmentUid = treatment.getTreatmentVO().getTheTreatmentDT()
                        .getTreatmentUid();
            }
            catch(Exception ex)
            {
              throw new NEDSSSystemException(ex.getMessage(),ex);
            }
            logger.debug(
                    " ActControllerEJB.setTreatment() Treatment Created");
        }
        else
        {
            logger.debug("testing treatment in set");
            try
            {
                treatment = home.findByPrimaryKey(treatmentVO.getTheTreatmentDT().getTreatmentUid());
            }
            catch(Exception ex)
            {
              throw new NEDSSSystemException(ex.getMessage(),ex);
            }

            try
            {
                treatment.setTreatmentVO(treatmentVO);
            }
            catch (NEDSSConcurrentDataException ndcex)
            {
                logger.fatal(
                        "got the NEDSSConcurrentDataException in actControllerEJB"  + ndcex.getMessage(),ndcex);
                throw new NEDSSConcurrentDataException(ndcex.getMessage(),ndcex);
            }
            catch(Exception e)
            {
              if( e.toString().indexOf("NEDSSConcurrentDataException")!= -1)
              {
                  logger.fatal("Throwing NEDSSConcurrentDataException" + e.getMessage(),e);
                  throw new NEDSSConcurrentDataException(e.getMessage(),e);
              }
              else
              {
                logger.fatal("ActControllerEJB.setTreatmentVO: " + e.getMessage(), e);
                logger.error("Throwing generic exception");
                throw new NEDSSConcurrentDataException(e.getMessage(),e);

              }
            }

            treatmentUid = treatmentVO.getTheTreatmentDT().getTreatmentUid();
            logger.debug(" ActControllerEJB.setTreatment() Treatment Updated");
        }

        logger.debug("the treatment Uid is " + treatmentUid);

        return treatmentUid;
    }

    /**
     * @roseuid 3EA4136F0058
     * @J2EE_METHOD  --  getTreatment
     */
    public TreatmentVO getTreatment(Long treatmentUid,
                                          NBSSecurityObj nbsSecurityObj)
                                   throws javax.ejb.EJBException
    {

        TreatmentVO treatmentCol = null;

        try
        {

            Treatment treatment = null;
            NedssUtils nedssUtils = new NedssUtils();
            Object obj = nedssUtils.lookupBean(JNDINames.TreatmentEJB);
            logger.debug("Act Controller lookup = " + obj.toString());

            TreatmentHome home = (TreatmentHome)PortableRemoteObject.narrow(
                                            obj, TreatmentHome.class);
            logger.debug("Found TreatmentHome: " + home);

            if (treatmentUid != null)
                treatment = home.findByPrimaryKey(treatmentUid);
            else
                throw new javax.ejb.EJBException("ActControllerEJB.getTreatment(): treatmentUid is NULL ");

            treatmentCol = treatment.getTreatmentVO();
            logger.debug(
                    "Act Controller past the find - treatment = " +
                    treatment.toString());
            logger.debug(
                    "Act Controller Done find by primary key! for PK: " +
                    treatment.getPrimaryKey());
        }
        catch (Exception e)
        {
            logger.fatal("ActControllerEJB.getTreatment(): " + e.getMessage(),e);
            e.printStackTrace();
            throw new javax.ejb.EJBException(e.getMessage(),e);
        }
        return treatmentCol;
    }

    /**
     * @roseuid 3EA413750132
     * @J2EE_METHOD  --  getTreatmentInfo
     */
    public TreatmentDT getTreatmentInfo(Long treatmentUid,
                                              NBSSecurityObj nbsSecurityObj)
                                       throws javax.ejb.EJBException
    {
    	try{
        TreatmentVO treatmentVO = null;
        TreatmentDT treatmentDT = null;
        treatmentVO = getTreatment(treatmentUid, nbsSecurityObj);

        if (treatmentVO != null)
            treatmentDT = treatmentVO.getTheTreatmentDT();

        logger.debug(
                "ActController.getTreatmentInfo(): TreatmentDT = " +
                treatmentDT);

        return treatmentDT;
    }
    catch (Exception e)
    {
    logger.fatal("ActControllerEJB.getTreatmentInfo:" + e.getMessage(), e);
    throw new javax.ejb.EJBException(e.getMessage(),e);
    }
    }

    /**
     * @roseuid 3EA413770152
     * @J2EE_METHOD  --  setTreatmentInfo
     */
    public void setTreatmentInfo(TreatmentDT treatmentDT,
                                    NBSSecurityObj nbsSecurityObj)
                             throws javax.ejb.EJBException,
                                    NEDSSConcurrentDataException,

                                    NEDSSSystemException
    {
    	try{
        TreatmentVO treatmentVO = null;

        if (treatmentDT.getTreatmentUid() != null)
        {
            logger.debug("this is if got getTreatmentUid ");
            treatmentVO = getTreatment(treatmentDT.getTreatmentUid(),
                                             nbsSecurityObj);
        }

        if (treatmentVO != null)
        {
            logger.debug("this is if got treatmentVO!= null ");
            treatmentVO.setTheTreatmentDT(treatmentDT);
            treatmentVO.setItDirty(true);
        }

        if (treatmentVO == null)
        {
            treatmentVO = new TreatmentVO();
            logger.debug("this is if got treatmentVO == null ");
            treatmentVO.setTheTreatmentDT(treatmentDT);
        }

        this.setTreatment(treatmentVO, nbsSecurityObj);
    	}
    	catch (Exception e)
    	{
    	logger.fatal("ActControllerEJB.setTreatmentInfo:" + e.getMessage(), e);
    	throw new javax.ejb.EJBException(e.getMessage(),e);
    	}
    }
    public CTContactVO setCTContact(CTContactVO  cTContactVO,NBSSecurityObj nbsSecurityObj)throws java.rmi.RemoteException,
            javax.ejb.EJBException, NEDSSConcurrentDataException{
        Long ctContactUid = new Long(-1);
        try
        {
            CTContact cTContact = null;
            NedssUtils nedssUtils = new NedssUtils();
            Object obj = nedssUtils.lookupBean(
                                 JNDINames.CTContactEJB);
            logger.debug("ActControllerEJB.setCTContactCTContactEJB lookup = " + obj.toString());
            CTContactHome home = (CTContactHome)PortableRemoteObject.narrow(
                                                obj,CTContactHome.class);
            logger.debug("ActControllerEJB.setCTContactFound CTContactHome: " + home);
            if (cTContactVO.isItNew())
            {
                logger.debug("ActControllerEJB.setCTContactFound CTContact Is It New : " +cTContactVO.isItNew());
                cTContact = home.create(cTContactVO);
                ctContactUid = cTContact.getCTContactVO().getcTContactDT().getCtContactUid();
                logger.debug("ActControllerEJB.setCTContact ctContactUid New Is " + ctContactUid);
                cTContactVO.getcTContactDT().setCtContactUid(ctContactUid);
                cTContactVO.getcTContactDT().setVersionCtrlNbr(cTContact.getCTContactVO().getcTContactDT().getVersionCtrlNbr());
            }
            else
            {
                cTContact = home.findByPrimaryKey(cTContactVO.getcTContactDT().getCtContactUid());
                cTContact.setCTContactVO(cTContactVO);
                ctContactUid = cTContactVO.getcTContactDT().getCtContactUid();
                logger.debug("ActControllerEJB.setCTContact ActControllerEJB.setCTContact() CTContact Updated called" +cTContact.getCTContactVO().getcTContactDT().getCtContactUid());
            }
        }
        catch(NEDSSConcurrentDataException ex)
              {
                logger.fatal("ActControllerEJB.setCTContact: NEDSSConcurrentDataException: concurrent access is not allowed " + ex.getMessage(),ex);
                throw new NEDSSConcurrentDataException(ex.getMessage(),ex);
              }
        catch (Exception e)
        {
            if( e.toString().indexOf("ActControllerEJB.setCTContact:- NEDSSConcurrentDataException")!= -1)
            {
                logger.fatal("ActControllerEJB.setCTContact: NEDSSConcurrentDataException: " + e.getMessage(),e);
                throw new NEDSSConcurrentDataException( e.getMessage(),e);
            }
            else
            {
              logger.fatal("ActControllerEJB.setCTContact: Exception: " + e.getMessage(), e);
              e.printStackTrace();
              throw new javax.ejb.EJBException(e.getMessage(),e);
            }
        }

        logger.debug("ctContactUid " + ctContactUid);

        return cTContactVO;
    
    }
    
    public CTContactVO getCTContact(Long ctContactUid,NBSSecurityObj nbsSecurityObj)throws java.rmi.RemoteException,javax.ejb.EJBException
	{
		CTContactVO cTContactVO = null;
		try
		{
			Long userId = new Long(Long.parseLong(nbsSecurityObj.getEntryID()));
			CTContact cTContact = null;
			NedssUtils nedssUtils = new NedssUtils();
			Object obj = nedssUtils.lookupBean(JNDINames.CTContactEJB);
			logger.debug("Act Controller lookup = " + obj.toString());
			
			CTContactHome home = (CTContactHome)PortableRemoteObject.narrow(obj,CTContactHome.class);
			logger.debug("Found CTContactHome: " + home);
			
			if (ctContactUid != null)
				cTContact = home.findByPrimaryKey(ctContactUid);
			else
				throw new javax.ejb.EJBException("ActControllerEJB.getCTContact(): cTContactUid is NULL ");
			
			cTContactVO = cTContact.getCTContactVO();
			
			// Filter out Private notes that don't belong to this user
			if (cTContactVO.getNoteDTCollection() != null) {
				Collection<Object> thisUsersNotes = new ArrayList<Object>();
				Iterator<Object> anIter = cTContactVO.getNoteDTCollection().iterator();
				while (anIter.hasNext()) {
					CTContactNoteDT ctContactNote = (CTContactNoteDT)anIter.next();
					if (ctContactNote.getPrivateIndCd().equalsIgnoreCase(NEDSSConstants.FALSE))
						thisUsersNotes.add(ctContactNote);
					else if (ctContactNote.getAddUserId().equals(userId))
						thisUsersNotes.add(ctContactNote);
				}
				cTContactVO.setNoteDTCollection(thisUsersNotes);
			}
			
			logger.debug("Act Controller past the find - cTContact = " +cTContact.toString());
			logger.debug("Act Controller Done find by primary key! for PK: " +cTContact.getPrimaryKey());
		}
		catch (Exception e)
		{
			logger.fatal("ActControllerEJB.getCTContact(): " + e.getMessage(),e);
			e.printStackTrace();
			throw new javax.ejb.EJBException(e.getMessage(),e);
		}
		return cTContactVO;
	}

    public void deleteCTContact(Long ctContactUid, NBSSecurityObj nbsSecurityObj)throws java.rmi.RemoteException,javax.ejb.EJBException {
    	try {
			CTContact ctContact = null;
			NedssUtils nedssUtils = new NedssUtils();
			Object obj = nedssUtils.lookupBean(JNDINames.CTContactEJB);
			logger.debug("Act Controller lookup = " + obj.toString());
			
			CTContactHome home = (CTContactHome)PortableRemoteObject.narrow(obj,CTContactHome.class);
			logger.debug("Found CTContactHome: " + home);
			
			if (ctContactUid != null)
				ctContact = home.findByPrimaryKey(ctContactUid);
			else
				throw new javax.ejb.EJBException("ActControllerEJB.getCTContact(): ctContactUid is NULL ");
			
			ctContact.remove();
			logger.debug("Act Controller past the find - cTContact = " +ctContact.toString());
			logger.debug("Act Controller Done find by primary key! for PK: " +ctContact.getPrimaryKey());
		} catch (Exception e) {
			logger.fatal("ActControllerEJB.getCTContact(): " + e.getMessage(),e);
			e.printStackTrace();
			throw new javax.ejb.EJBException(e.getMessage(),e);
		}    	
    }

    /**
     * @roseuid 3BFAF8E9014B
     * @J2EE_METHOD  --  setInterview
     */
    public Long setInterview(InterviewVO interviewVO,
                                NBSSecurityObj nbsSecurityObj)
                         throws javax.ejb.EJBException,
                                NEDSSConcurrentDataException,
                                NEDSSSystemException
    {

        Long interviewUid = new Long(-1);

        //       try
        //     {
        Interview interview = null;
        NedssUtils nedssUtils = new NedssUtils();
        Object obj = nedssUtils.lookupBean(JNDINames.InterviewEJB);
        logger.debug("InterviewEJB lookup = " + obj.toString());

        InterviewHome home = (InterviewHome)PortableRemoteObject.narrow(
                                        obj,InterviewHome.class);
        logger.debug("Found InterviewHome: " + home);

        if (interviewVO.isItNew())
        {
            logger.debug("testing interview in create");
            try
            {
            interview = home.create(interviewVO);
            interviewUid = interview.getInterviewVO().getTheInterviewDT()
                        .getInterviewUid();
            }
            catch(Exception ex)
            {
            	logger.fatal("ActControllerEJB.setInterview(): " + ex.getMessage(),ex);
              throw new NEDSSSystemException(ex.getMessage(),ex);
            }
            logger.debug(
                    " ActControllerEJB.setInterview() Interview Created");
        }
        else
        {
            logger.debug("testing interview in set");
            try
            {
                interview = home.findByPrimaryKey(interviewVO.getTheInterviewDT().getInterviewUid());
            }
            catch(Exception ex)
            {
              throw new NEDSSSystemException(ex.getMessage(),ex);
            }

            try
            {
                interview.setInterviewVO(interviewVO);
            }
            catch (NEDSSConcurrentDataException ndcex)
            {
                logger.fatal("ActControllerEJB.setInterview(): NEDSSConcurrentDataException: " + ndcex.getMessage(),ndcex);
                throw new NEDSSConcurrentDataException(ndcex.getMessage(),ndcex);
            }
            catch(Exception e)
            {
              if( e.toString().indexOf("NEDSSConcurrentDataException")!= -1)
              {
                  logger.fatal("ActControllerEJB.setInterview(): NEDSSConcurrentDataException: ");
                  throw new NEDSSConcurrentDataException( e.getMessage(),e);
              }
              else
              {
                logger.fatal("ActControllerEJB.setInterviewVO: Exception: " + e.getMessage(),e);
                throw new javax.ejb.EJBException(e.getMessage(),e);
              }
            }

            interviewUid = interviewVO.getTheInterviewDT().getInterviewUid();
            logger.debug(
                    " ActControllerEJB.setInterview() Interview Updated");
        }
        logger.debug("the interview Uid is " + interviewUid);
        return interviewUid;

    }

    /**
     * @roseuid 3BFAF8E90317
     * @J2EE_METHOD  --  getInterview
     */
    
    public InterviewVO getInterview(Long interviewUid,
                                          NBSSecurityObj nbsSecurityObj)
                                   throws javax.ejb.EJBException
    {

        InterviewVO interviewCol = null;

        try
        {

            Interview interview = null;
            NedssUtils nedssUtils = new NedssUtils();
            Object obj = nedssUtils.lookupBean(JNDINames.InterviewEJB);
            logger.debug("Act Controller lookup = " + obj.toString());

            InterviewHome home = (InterviewHome)PortableRemoteObject.narrow(
                                            obj, InterviewHome.class);
            logger.debug("Found InterviewHome: " + home);

            if (interviewUid != null)
                interview = home.findByPrimaryKey(interviewUid);
            else
                throw new javax.ejb.EJBException("ActControllerEJB.getInterview(): interviewUid is NULL ");

            interviewCol = interview.getInterviewVO();
            logger.debug(
                    "Act Controller past the find - interview = " +
                    interview.toString());
            logger.debug(
                    "Act Controller Done find by primary key! for PK: " +
                    interview.getPrimaryKey());
            logger.debug("Adding interview answer collection to interview vo ");
            
        }
        catch (Exception e)
        {
            logger.fatal("ActControllerEJB.getInterview(): " + e.getMessage(),e);
            e.printStackTrace();
            throw new javax.ejb.EJBException(e.getMessage(),e);
        }
        return interviewCol;
    }
    
    public Map<Object, Object> getInterviewAnswerMap(Long actUid,
            NBSSecurityObj nbsSecurityObj)
     throws javax.ejb.EJBException
	{
	
	    Map<Object, Object> interviewAnswerDTs = new HashMap<Object, Object>();
		InterviewAnswerDAOImpl interviewAnswerDAOImpl =	null;
		
		try
		{
			interviewAnswerDAOImpl = (InterviewAnswerDAOImpl)NEDSSDAOFactory.getDAO(
		                   JNDINames.INTERVIEW_ANSWER_DAO_CLASS);
		
		if (interviewAnswerDAOImpl != null)
		{
			interviewAnswerDTs = interviewAnswerDAOImpl.getInterviewAnswerDTCollection(actUid);
		
		}
		}
		catch (Exception e)
		{
		logger.fatal("ActControllerEJB.getInterviewAnswerMap():" + e.getMessage(),e);
		e.printStackTrace();
		throw new javax.ejb.EJBException(e.getMessage(),e);
		}
		
		return interviewAnswerDTs;
	}

	public PublicHealthCaseDT getPhcHist(Long publicHealthCaseUid,
			Integer versionCtrlNbr, NBSSecurityObj nbsSecurityObj)
			throws NEDSSSystemException {
		try {
			PublicHealthCaseHistoryManager phcHistoryManager = new PublicHealthCaseHistoryManager();

			return phcHistoryManager
					.getPhcHist(publicHealthCaseUid, versionCtrlNbr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("ActControllerEJB.getPhcHist(): " + e.getMessage(),e);
    		throw new javax.ejb.EJBException(e.getMessage(),e);
		}

	}
}
