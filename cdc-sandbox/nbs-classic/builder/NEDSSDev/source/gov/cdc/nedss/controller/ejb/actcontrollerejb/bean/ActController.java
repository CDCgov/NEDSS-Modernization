//
// -- Java Code Generation Process --
package gov.cdc.nedss.controller.ejb.actcontrollerejb.bean;

import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.act.observation.vo.*;
import gov.cdc.nedss.act.observation.dt.*;
import gov.cdc.nedss.act.publichealthcase.vo.*;
import gov.cdc.nedss.act.publichealthcase.dt.*;
import gov.cdc.nedss.act.notification.vo.*;
import gov.cdc.nedss.act.notification.dt.*;
import gov.cdc.nedss.act.intervention.vo.*;
import gov.cdc.nedss.act.intervention.dt.*;
import gov.cdc.nedss.act.interview.vo.InterviewVO;
import gov.cdc.nedss.act.referral.vo.*;
import gov.cdc.nedss.act.referral.dt.*;
import gov.cdc.nedss.act.patientencounter.vo.*;
import gov.cdc.nedss.act.patientencounter.dt.*;
import gov.cdc.nedss.act.clinicaldocument.vo.*;
import gov.cdc.nedss.act.clinicaldocument.dt.*;
import gov.cdc.nedss.act.ctcontact.vo.CTContactVO;
import gov.cdc.nedss.association.dt.*;
import gov.cdc.nedss.act.treatment.vo.*;
import gov.cdc.nedss.act.treatment.dt.*;

import java.util.Collection;



public interface ActController
    extends javax.ejb.EJBObject
{

    /**
     * @roseuid 3C4DD5370371

    /**
     * @J2EE_METHOD  --  setObservation
     */
    public Long setObservation    (ObservationVO observationVO, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException,javax.ejb.EJBException, NEDSSConcurrentDataException, NEDSSSystemException;

    /**
     * @roseuid 3C4DD5390125

    /**
     * @J2EE_METHOD  --  getObservation
     */
    public ObservationVO getObservation    (Long observationUID, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * @roseuid 3C4DD53A02F3

    /**
     * @J2EE_METHOD  --  getObservationInfo
     */
    public ObservationDT getObservationInfo(Long observationUID,
                                                                      NBSSecurityObj nbsSecurityObj)
        throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * @roseuid 3C4DD53C00E3

    /**
     * @J2EE_METHOD  --  setObservationInfo
     */
    public void setObservationInfo    (ObservationDT observationDT, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException, NEDSSConcurrentDataException, NEDSSSystemException;

    /**
     * @roseuid 3C4DD53D029D

    /**
     * @J2EE_METHOD  --  getObservationReasons
     */
    public java.util.Collection<Object>  getObservationReasons(Long observationUID,
                                                      NBSSecurityObj nbsSecurityObj)
                                               throws java.rmi.RemoteException,
                                                      javax.ejb.EJBException;

    /**
     * @roseuid 3C4DD53F00AB

    /**
     * @J2EE_METHOD  --  getObservationReason
     */
    public ObservationReasonDT getObservationReason(Long observationUID,
                                                                              String reasonCd,
                                                                              NBSSecurityObj nbsSecurityObj)
        throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * @roseuid 3C4DD5410018

    /**
     * @J2EE_METHOD  --  setObservationReasons
     */
    public void setObservationReasons    (java.util.Collection<Object>  observationReasons, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException,javax.ejb.EJBException, NEDSSConcurrentDataException, NEDSSSystemException;

    /**
     * @roseuid 3C4DD5420204

    /**
     * @J2EE_METHOD  --  getObsValueNumerics
     */
    public java.util.Collection<Object>  getObsValueNumerics(Long observationUID,
                                                    NBSSecurityObj nbsSecurityObj)
                                             throws java.rmi.RemoteException,
                                                    javax.ejb.EJBException;

    /**
     * @roseuid 3C4DD544003A

    /**
     * @J2EE_METHOD  --  getObsValueNumeric
     */
    public ObsValueNumericDT getObsValueNumeric(Long observationUID,
                                                                          Short obsValueNumericSeq,
                                                                          NBSSecurityObj nbsSecurityObj)
        throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * @roseuid 3C4DD54503D5

    /**
     * @J2EE_METHOD  --  setObsValueNumerics
     */
    public void setObsValueNumerics    (java.util.Collection<Object>  obsValueNumerics, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException,javax.ejb.EJBException, NEDSSConcurrentDataException, NEDSSSystemException;

    /**
     * @roseuid 3C4DD54701ED

    /**
     * @J2EE_METHOD  --  getObsValueDates
     */
    public java.util.Collection<Object>  getObsValueDates(Long observationUID,
                                                 NBSSecurityObj nbsSecurityObj)
                                          throws java.rmi.RemoteException,
                                                 javax.ejb.EJBException;

    /**
     * @roseuid 3C4DD549002D

    /**
     * @J2EE_METHOD  --  getObsValueDate
     */
    public ObsValueDateDT getObsValueDate(Long observationUID,
                                                                    Short obsValueDateSeq,
                                                                    NBSSecurityObj nbsSecurityObj)
        throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * @roseuid 3C4DD54A03DC

    /**
     * @J2EE_METHOD  --  setObsValueDates
     */
    public void setObsValueDates    (java.util.Collection<Object>  obsValueDates, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException,javax.ejb.EJBException, NEDSSConcurrentDataException, NEDSSSystemException;

    /**
     * @roseuid 3C4DD54C01FE

    /**
     * @J2EE_METHOD  --  getObsValueTxts
     */
    public java.util.Collection<Object>  getObsValueTxts(Long observationUID,
                                                NBSSecurityObj nbsSecurityObj)
                                         throws java.rmi.RemoteException,
                                                javax.ejb.EJBException;

    /**
     * @roseuid 3C4DD54E005D

    /**
     * @J2EE_METHOD  --  getObsValueTxt
     */
    public ObsValueTxtDT getObsValueTxt(Long observationUID,
                                                                  Short obsValueTxtSeq,
                                                                  NBSSecurityObj nbsSecurityObj)
        throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * @roseuid 3C4DD5500042

    /**
     * @J2EE_METHOD  --  setObsValueTxts
     */
    public void setObsValueTxts    (java.util.Collection<Object>  obsValueTxt, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException,javax.ejb.EJBException, NEDSSConcurrentDataException, NEDSSSystemException;

    /**
     * @roseuid 3C4DD5510260

    /**
     * @J2EE_METHOD  --  getObservationInterps
     */
    public java.util.Collection<Object>  getObservationInterps(Long observationUID,
                                                      NBSSecurityObj nbsSecurityObj)
                                               throws java.rmi.RemoteException,
                                                      javax.ejb.EJBException;

    /**
     * @roseuid 3C4DD55300E6

    /**
     * @J2EE_METHOD  --  getObservationInterp
     */
    public ObservationInterpDT getObservationInterp(Long observationUID,
                                                                              String interpretationCd,
                                                                              NBSSecurityObj nbsSecurityObj)
        throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * @roseuid 3C4DD55500FD

    /**
     * @J2EE_METHOD  --  setObservationInterps
     */
    public void setObservationInterps    (java.util.Collection<Object>  observationInterp, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException,javax.ejb.EJBException, NEDSSConcurrentDataException, NEDSSSystemException;

    /**
     * @roseuid 3C4DD5560343

    /**
     * @J2EE_METHOD  --  getObsValueCodeds
     */
    public java.util.Collection<Object>  getObsValueCodeds(Long observationUID,
                                                  NBSSecurityObj nbsSecurityObj)
                                           throws java.rmi.RemoteException,
                                                  javax.ejb.EJBException;

    /**
     * @roseuid 3C4DD5580210

    /**
     * @J2EE_METHOD  --  getObsValueCoded
     */
    public ObsValueCodedDT getObsValueCoded(Long observationUID,
                                                                      String code,
                                                                      NBSSecurityObj nbsSecurityObj)
        throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * @roseuid 3C4DD55A0245

    /**
     * @J2EE_METHOD  --  setObsValueCodeds
     */
    public void setObsValueCodeds    (java.util.Collection<Object>  obsValueCoded, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException,javax.ejb.EJBException, NEDSSConcurrentDataException, NEDSSSystemException;

    /**
     * @roseuid 3C4DD55C00B7

    /**
     * @J2EE_METHOD  --  getObsValueCodedMod
     */
    public ObsValueCodedModDT getObsValueCodedMod(Long observationUID,
                                                                            String code,
                                                                            String codeModCd,
                                                                            NBSSecurityObj nbsSecurityObj)
        throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * @roseuid 3C4DD55E02A5

    /**
     * @J2EE_METHOD  --  getObsValueCodedMods
     */
    public java.util.Collection<Object>  getObsValueCodedMods(Long observationUID,
                                                     String code,
                                                     NBSSecurityObj nbsSecurityObj)
                                              throws java.rmi.RemoteException,
                                                     javax.ejb.EJBException;

    /**
     * @roseuid 3C4DD5600302

    /**
     * @J2EE_METHOD  --  setObsValueCodedMods
     */
    public void setObsValueCodedMods    (java.util.Collection<Object>  obsValueCodedMod, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException,javax.ejb.EJBException, NEDSSConcurrentDataException, NEDSSSystemException;

    /**
     * @roseuid 3C4DD56201A6

    /**
     * @J2EE_METHOD  --  setPublicHealthCase
     */
    public Long setPublicHealthCase(PublicHealthCaseVO publicHealthCaseVO,
                                    NBSSecurityObj nbsSecurityObj)
                             throws java.rmi.RemoteException,
                                    javax.ejb.EJBException, NEDSSConcurrentDataException;

    /**
     * @roseuid 3C4DD564004A

    /**
     * @J2EE_METHOD  --  getPublicHealthCase
     */
    public PublicHealthCaseVO getPublicHealthCase(Long publicHealthCaseUID,
                                                                            NBSSecurityObj nbsSecurityObj)
        throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * @roseuid 3C4DD5650313

    /**
     * @J2EE_METHOD  --  getPublicHealthCaseInfo
     */
    public PublicHealthCaseDT getPublicHealthCaseInfo(Long publicHealthCaseUID,
                                                                                NBSSecurityObj nbsSecurityObj)
        throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * @roseuid 3C4DD56701FD

    /**
     * @J2EE_METHOD  --  setPublicHealthCaseInfo
     */
    public void setPublicHealthCaseInfo(PublicHealthCaseDT publicHealthCaseDT,
                                        NBSSecurityObj nbsSecurityObj)
                                 throws java.rmi.RemoteException,
                                        javax.ejb.EJBException, NEDSSConcurrentDataException;

    /**
     * @roseuid 3C4DD56900B6

    /**
     * @J2EE_METHOD  --  getConfirmationMethods
     */
    public java.util.Collection<Object>  getConfirmationMethods(Long publicHealthCaseUID,
                                                       NBSSecurityObj nbsSecurityObj)
        throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * @roseuid 3C4DD56A0388

    /**
     * @J2EE_METHOD  --  getConfirmationMethod
     */
    public ConfirmationMethodDT getConfirmationMethod(Long publicHealthCaseUID,
                                                                                String confirmationMethodCd,
                                                                                NBSSecurityObj nbsSecurityObj)
        throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * @roseuid 3C4DD56D0057

    /**
     * @J2EE_METHOD  --  setConfirmationMethods
     */
    public void setConfirmationMethods(java.util.Collection<Object>  confirmationMethod,
                                       NBSSecurityObj nbsSecurityObj)
                                throws java.rmi.RemoteException,
                                       javax.ejb.EJBException, NEDSSConcurrentDataException;

    /**
     * @roseuid 3C4DD56E0316

    /**
     * @J2EE_METHOD  --  setNotification
     */
    public Long setNotification(NotificationVO notificationVO,
                                NBSSecurityObj nbsSecurityObj)
                         throws java.rmi.RemoteException,
                                javax.ejb.EJBException,
                                NEDSSConcurrentDataException;

    /**
     * @roseuid 3C4DD57001F6

    /**
     * @J2EE_METHOD  --  getNotification
     */
    public NotificationVO getNotification(Long notificationUID,
                                                                    NBSSecurityObj nbsSecurityObj)
        throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * @roseuid 3C4DD572011D

    /**
     * @J2EE_METHOD  --  getNotificationInfo
     */
    public NotificationDT getNotificationInfo(Long notificationUID,
                                                                        NBSSecurityObj nbsSecurityObj)
        throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * @roseuid 3C4DD5740043

    /**
     * @J2EE_METHOD  --  setNotificationInfo
     */
    public void setNotificationInfo(NotificationDT notificationDT,
                                    NBSSecurityObj nbsSecurityObj)
                             throws java.rmi.RemoteException,
                                    javax.ejb.EJBException,
				    NEDSSConcurrentDataException;

    /**
     * @roseuid 3C4DD5750320

    /**
     * @J2EE_METHOD  --  setIntervention
     */
    public Long setIntervention(InterventionVO interventionVO,
                                NBSSecurityObj nbsSecurityObj)
                         throws java.rmi.RemoteException,javax.ejb.EJBException, NEDSSConcurrentDataException, NEDSSSystemException;


    /**
    * @J2EE_METHOD  --  setTreatment
    */
   /*
   public Long setTreatment(TreatmentVO treatmentVO,
                                                NBSSecurityObj nbsSecurityObj)
                                         throws java.rmi.RemoteException, NEDSSConcurrentDataException;
*/

    /**
     * @roseuid 3C4DD577021E

    /**
     * @J2EE_METHOD  --  getIntervention
     */
    public InterventionVO getIntervention(Long interventionUID,
                                                                    NBSSecurityObj nbsSecurityObj)
        throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * @roseuid 3C4DD5790159

    /**
     * @J2EE_METHOD  --  getInterventionInfo
     */
    public InterventionDT getInterventionInfo(Long interventionUID,
                                                                        NBSSecurityObj nbsSecurityObj)
        throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * @roseuid 3C4DD57B00C6

    /**
     * @J2EE_METHOD  --  setInterventionInfo
     */
    public void setInterventionInfo(InterventionDT interventionDT,
                                    NBSSecurityObj nbsSecurityObj)
                             throws java.rmi.RemoteException,
                                    javax.ejb.EJBException,
                                    NEDSSConcurrentDataException,

                                    NEDSSSystemException;

    /**
     * @roseuid 3C4DD57C03DE

    /**
     * @J2EE_METHOD  --  setReferral
     */
    public Long setReferral(ReferralVO referralVO,
                            NBSSecurityObj nbsSecurityObj)
                     throws java.rmi.RemoteException, javax.ejb.EJBException,NEDSSConcurrentDataException;

    /**
     * @roseuid 3C4DD57E032D

    /**
     * @J2EE_METHOD  --  getReferral
     */
    public ReferralVO getReferral(Long referralUID,
                                                            NBSSecurityObj nbsSecurityObj)
        throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * @roseuid 3C4DD58002CB

    /**
     * @J2EE_METHOD  --  getReferralInfo
     */
    public ReferralDT getReferralInfo(Long referralUID,
                                                                NBSSecurityObj nbsSecurityObj)
        throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * @roseuid 3C4DD5820288

    /**
     * @J2EE_METHOD  --  setReferralInfo
     */
    public void setReferralInfo(ReferralDT referralDT,
                                NBSSecurityObj nbsSecurityObj)
                         throws java.rmi.RemoteException,
                                javax.ejb.EJBException ,NEDSSConcurrentDataException;

    /**
     * @roseuid 3C4DD58401EB

    /**
     * @J2EE_METHOD  --  setPatientEncounter
     */
    public Long setPatientEncounter(PatientEncounterVO patientEncounterVO,
                                    NBSSecurityObj nbsSecurityObj)
                             throws java.rmi.RemoteException,
                                    javax.ejb.EJBException, NEDSSConcurrentDataException;

    /**
     * @roseuid 3C4DD5860158

    /**
     * @J2EE_METHOD  --  getPatientEncounter
     */
    public PatientEncounterVO getPatientEncounter(Long patientEncounterUID,
                                                                            NBSSecurityObj nbsSecurityObj)
        throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * @roseuid 3C4DD588013C

    /**
     * @J2EE_METHOD  --  getPatientEncounterInfo
     */
    public PatientEncounterDT getPatientEncounterInfo(Long patientEncounterUID,
                                                                                NBSSecurityObj nbsSecurityObj)
        throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * @roseuid 3C4DD58A0121

    /**
     * @J2EE_METHOD  --  setPatientEncounterInfo
     */
    public void setPatientEncounterInfo(PatientEncounterDT patientEncounterDT,
                                        NBSSecurityObj nbsSecurityObj)
                                 throws java.rmi.RemoteException,
                                        javax.ejb.EJBException, NEDSSConcurrentDataException;

    /**
     * @roseuid 3C4DD58C00CA

    /**
     * @J2EE_METHOD  --  setClinicalDocument
     */
    public Long setClinicalDocument    (ClinicalDocumentVO clinicalDocumentVO, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException, NEDSSConcurrentDataException;

    /**
     * @roseuid 3C4DD58E0073

    /**
     * @J2EE_METHOD  --  getClinicalDocument
     */
    public ClinicalDocumentVO getClinicalDocument(Long clinicalDocumentUID,
                                                                            NBSSecurityObj nbsSecurityObj)
        throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * @roseuid 3C4DD590009E

    /**
     * @J2EE_METHOD  --  getClinicalDocumentInfo
     */
    public ClinicalDocumentDT getClinicalDocumentInfo(Long clinicalDocumentUID,
                                                                                NBSSecurityObj nbsSecurityObj)
        throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * @roseuid 3C4DD59200B5

    /**
     * @J2EE_METHOD  --  setClinicalDocumentInfo
     */
    public void setClinicalDocumentInfo    (ClinicalDocumentDT clinicalDocumentDT, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException, NEDSSConcurrentDataException;

    /**
     * @roseuid 3C4DD5940099

    /**
     * @J2EE_METHOD  --  getProcedure1
     */
    public Procedure1DT getProcedure1(Long interventionUID,
                                                                NBSSecurityObj nbsSecurityObj)
        throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * @roseuid 3C4DD59600CE

    /**
     * @J2EE_METHOD  --  setProcedure1
     */
    public void setProcedure1(Procedure1DT procedure1DT,
                              NBSSecurityObj nbsSecurityObj)
                       throws java.rmi.RemoteException,javax.ejb.EJBException,
                              NEDSSConcurrentDataException, NEDSSSystemException;

    /**
     * @roseuid 3C4DD59800E5

    /**
     * @J2EE_METHOD  --  getSubstanceAdministration
     */
    public SubstanceAdministrationDT getSubstanceAdministration(Long interventionUID,
                                                                                          NBSSecurityObj nbsSecurityObj)
        throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * @roseuid 3C4DD59A012E

    /**
     * @J2EE_METHOD  --  setSubstanceAdministration
     */
    public void setSubstanceAdministration(SubstanceAdministrationDT substanceAdministrationDT,
                                           NBSSecurityObj nbsSecurityObj)
                                    throws java.rmi.RemoteException,javax.ejb.EJBException, NEDSSConcurrentDataException, NEDSSSystemException;

    /**
     * @roseuid 3C4DD59C014F

    /**
     * @J2EE_METHOD  --  getObservationIDs
     */
    public java.util.Collection<Object>  getObservationIDs(Long observationUID,
                                                  NBSSecurityObj nbsSecurityObj)
                                           throws java.rmi.RemoteException,
                                                  javax.ejb.EJBException;

    /**
     * @roseuid 3C4DD59E01B6

    /**
     * @J2EE_METHOD  --  getObservationIDs
     */
    public java.util.Collection<Object>  getObservationIDs(Long observationUID,
                                                  String typeCd,
                                                  NBSSecurityObj nbsSecurityObj)
                                           throws java.rmi.RemoteException,
                                                  javax.ejb.EJBException;

    /**
     * @roseuid 3C4DD5A100CA

    /**
     * @J2EE_METHOD  --  setObservationIDs
     */
    public void setObservationIDs    (java.util.Collection<Object>  observationIds, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException,javax.ejb.EJBException, NEDSSConcurrentDataException, NEDSSSystemException;

    /**
     * @roseuid 3C4DD5A30268

    /**
     * @J2EE_METHOD  --  getPublicHealthCaseIDs
     */
    public java.util.Collection<Object>  getPublicHealthCaseIDs(Long publicHealthCaseUID,
                                                       NBSSecurityObj nbsSecurityObj)
        throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * @roseuid 3C4DD5A503B5

    /**
     * @J2EE_METHOD  --  getPublicHealthCaseIDs
     */
    public java.util.Collection<Object>  getPublicHealthCaseIDs(Long publicHealthCaseUID,
                                                       String typeCd,
                                                       NBSSecurityObj nbsSecurityObj)
        throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * @roseuid 3C4DD5A8030F

    /**
     * @J2EE_METHOD  --  setPublicHealthCaseIDs
     */
    public void setPublicHealthCaseIDs(java.util.Collection<Object>  publicHealthCaseIDs,
                                       NBSSecurityObj nbsSecurityObj)
                                throws java.rmi.RemoteException,
                                       javax.ejb.EJBException, NEDSSConcurrentDataException;

    /**
     * @roseuid 3C4DD5AB00D9

    /**
     * @J2EE_METHOD  --  getNotificationIDs
     */
    public java.util.Collection<Object>  getNotificationIDs(Long notificationUID,
                                                   NBSSecurityObj nbsSecurityObj)
                                            throws java.rmi.RemoteException,
                                                   javax.ejb.EJBException;

    /**
     * @roseuid 3C4DD5AD03AD

    /**
     * @J2EE_METHOD  --  getNotificationIDs
     */
    public java.util.Collection<Object>  getNotificationIDs(Long notificationUID,
                                                   String typeCd,
                                                   NBSSecurityObj nbsSecurityObj)
                                            throws java.rmi.RemoteException,
                                                   javax.ejb.EJBException;

    /**
     * @roseuid 3C4DD5B002D5

    /**
     * @J2EE_METHOD  --  setNotificationIDs
     */
    public void setNotificationIDs(java.util.Collection<Object>  NotificationIDs,
                                   NBSSecurityObj nbsSecurityObj)
                            throws java.rmi.RemoteException,
                                   javax.ejb.EJBException,
				   NEDSSConcurrentDataException;

    /**
     * @roseuid 3C4DD5B2035A

    /**
     * @J2EE_METHOD  --  getInterventionIDs
     */
    public java.util.Collection<Object>  getInterventionIDs(Long interventionUID,
                                                   NBSSecurityObj nbsSecurityObj)
                                            throws java.rmi.RemoteException,
                                                   javax.ejb.EJBException;

    /**
     * @roseuid 3C4DD5B5006F

    /**
     * @J2EE_METHOD  --  getInterventionIDs
     */
    public java.util.Collection<Object>  getInterventionIDs(Long interventionUID,
                                                   String typeCd,
                                                   NBSSecurityObj nbsSecurityObj)
                                            throws java.rmi.RemoteException,
                                                   javax.ejb.EJBException;

    /**
     * @roseuid 3C4DD5B703B1

    /**
     * @J2EE_METHOD  --  setInterventionIDs
     */
    public void setInterventionIDs(java.util.Collection<Object>  InterventionIDs,
                                   NBSSecurityObj nbsSecurityObj)
                            throws java.rmi.RemoteException,javax.ejb.EJBException, NEDSSConcurrentDataException, NEDSSSystemException;

    /**
     * @roseuid 3C4DD5BA008A

    /**
     * @J2EE_METHOD  --  getReferralIDs
     */
    public java.util.Collection<Object>  getReferralIDs(Long referralUID,
                                               NBSSecurityObj nbsSecurityObj)
                                        throws java.rmi.RemoteException,
                                               javax.ejb.EJBException;

    /**
     * @roseuid 3C4DD5BC01A5

    /**
     * @J2EE_METHOD  --  getReferralIDs
     */
    public java.util.Collection<Object>  getReferralIDs(Long referralUID, String typeCd,
                                               NBSSecurityObj nbsSecurityObj)
                                        throws java.rmi.RemoteException,
                                               javax.ejb.EJBException;

    /**
     * @roseuid 3C4DD5BF0131

    /**
     * @J2EE_METHOD  --  setReferralIDs
     */
    public void setReferralIDs(java.util.Collection<Object>  ReferralIDs,
                               NBSSecurityObj nbsSecurityObj)
                        throws java.rmi.RemoteException,
                               javax.ejb.EJBException,NEDSSConcurrentDataException;

    /**
     * @roseuid 3C4DD5C10211

    /**
     * @J2EE_METHOD  --  getPatientEncounterIDs
     */
    public java.util.Collection<Object>  getPatientEncounterIDs(Long patientEncounterUID,
                                                       NBSSecurityObj nbsSecurityObj)
        throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * @roseuid 3C4DD5C30354

    /**
     * @J2EE_METHOD  --  getPatientEncounterIDs
     */
    public java.util.Collection<Object>  getPatientEncounterIDs(Long patientEncounterUID,
                                                       String typeCd,
                                                       NBSSecurityObj nbsSecurityObj)
        throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * @roseuid 3C4DD5C6031C

    /**
     * @J2EE_METHOD  --  getClinicalDocumentIDs
     */
    public java.util.Collection<Object>  getClinicalDocumentIDs(Long clinicalDocumentUID,
                                                       NBSSecurityObj nbsSecurityObj)
        throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * @roseuid 3C4DD5C9008C

    /**
     * @J2EE_METHOD  --  getClinicalDocumentIDs
     */
    public java.util.Collection<Object>  getClinicalDocumentIDs(Long clinicalDocumentUID,
                                                       String typeCd,
                                                       NBSSecurityObj nbsSecurityObj)
        throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * @roseuid 3C4DD5CC0072

    /**
     * @J2EE_METHOD  --  setClinicalDocumentIDs
     */
    public void setClinicalDocumentIDs    (java.util.Collection<Object>  clinicalDocumentIDs, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException, NEDSSConcurrentDataException;

    /**
     * @roseuid 3C4DD5CE01A1

    /**
     * @J2EE_METHOD  --  getAllObservationLocators
     */
    public java.util.Collection<Object>  getAllObservationLocators(Long observationUID,
                                                          NBSSecurityObj nbsSecurityObj)
        throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * @roseuid 3C4DD5D00317

    /**
     * @J2EE_METHOD  --  setAllObservationLocators
     */
    public void setAllObservationLocators(java.util.Collection<Object>  activityLocatorParticipationDTs,
                                          NBSSecurityObj nbsSecurityObj)
                                   throws java.rmi.RemoteException,
                                          javax.ejb.EJBException;

    /**
     * @roseuid 3C4DD5D30068

    /**
     * @J2EE_METHOD  --  getObservationPhysicalLocators
     */
    public java.util.Collection<Object>  getObservationPhysicalLocators(Long observationUID,
                                                               NBSSecurityObj nbsSecurityObj)
        throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * @roseuid 3C4DD5D501FB

    /**
     * @J2EE_METHOD  --  getObservationPostalLocators
     */
    public java.util.Collection<Object>  getObservationPostalLocators(Long observationUID,
                                                             NBSSecurityObj nbsSecurityObj)
        throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * @roseuid 3C4DD5D703A3

    /**
     * @J2EE_METHOD  --  getObservationTeleLocators
     */
    public java.util.Collection<Object>  getObservationTeleLocators(Long observationUID,
                                                           NBSSecurityObj nbsSecurityObj)
        throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * @roseuid 3C4DD5DA0176

    /**
     * @J2EE_METHOD  --  getAllPublicHealthCaseLocators
     */
    public java.util.Collection<Object>  getAllPublicHealthCaseLocators(Long publicHealthCaseUID,
                                                               NBSSecurityObj nbsSecurityObj)
        throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * @roseuid 3C4DD5DC0314

    /**
     * @J2EE_METHOD  --  setAllPublicHealthCaseLocators
     */
    public void setAllPublicHealthCaseLocators(java.util.Collection<Object>  activityLocatorParticipationDTs,
                                               NBSSecurityObj nbsSecurityObj)
                                        throws java.rmi.RemoteException,
                                               javax.ejb.EJBException;

    /**
     * @roseuid 3C4DD5DF00A1

    /**
     * @J2EE_METHOD  --  getPublicHealthCasePhysicalLocators
     */
    public java.util.Collection<Object>  getPublicHealthCasePhysicalLocators(Long publicHealthCaseUID,
                                                                    NBSSecurityObj nbsSecurityObj)
        throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * @roseuid 3C4DD5E10271

    /**
     * @J2EE_METHOD  --  getPublicHealthCasePostalLocators
     */
    public java.util.Collection<Object>  getPublicHealthCasePostalLocators(Long publicHealthCaseUID,
                                                                  NBSSecurityObj nbsSecurityObj)
        throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * @roseuid 3C4DD5E40062

    /**
     * @J2EE_METHOD  --  getPublicHealthCaseTeleLocators
     */
    public java.util.Collection<Object>  getPublicHealthCaseTeleLocators(Long publicHealthCaseUID,
                                                                NBSSecurityObj nbsSecurityObj)
        throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * @roseuid 3C4DD5E60250

    /**
     * @J2EE_METHOD  --  getAllNotificationLocators
     */
    public java.util.Collection<Object>  getAllNotificationLocators(Long notificationUID,
                                                           NBSSecurityObj nbsSecurityObj)
        throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * @roseuid 3C4DD5E9004C

    /**
     * @J2EE_METHOD  --  setAllNotificationLocators
     */
    public void setAllNotificationLocators(java.util.Collection<Object>  activityLocatorParticipationDTs,
                                           NBSSecurityObj nbsSecurityObj)
                                    throws java.rmi.RemoteException,
                                           javax.ejb.EJBException;

    /**
     * @roseuid 3C4DD5EB01F3

    /**
     * @J2EE_METHOD  --  getNotificationPhysicalLocators
     */
    public java.util.Collection<Object>  getNotificationPhysicalLocators(Long notificationUID,
                                                                NBSSecurityObj nbsSecurityObj)
        throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * @roseuid 3C4DD5EE000D

    /**
     * @J2EE_METHOD  --  getNotificationPostalLocators
     */
    public java.util.Collection<Object>  getNotificationPostalLocators(Long notificationUID,
                                                              NBSSecurityObj nbsSecurityObj)
        throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * @roseuid 3C4DD5F00222

    /**
     * @J2EE_METHOD  --  getNotificationTeleLocators
     */
    public java.util.Collection<Object>  getNotificationTeleLocators(Long notificationUID,
                                                            NBSSecurityObj nbsSecurityObj)
        throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * @roseuid 3C4DD5F30064

    /**
     * @J2EE_METHOD  --  getAllInterventionLocators
     */
    public java.util.Collection<Object>  getAllInterventionLocators(Long interventionUID,
                                                           NBSSecurityObj nbsSecurityObj)
        throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * @roseuid 3C4DD5F50298

    /**
     * @J2EE_METHOD  --  setAllInterventionLocators
     */
    public void setAllInterventionLocators(java.util.Collection<Object>  interventionLocators,
                                           NBSSecurityObj nbsSecurityObj)
                                    throws java.rmi.RemoteException,
                                           javax.ejb.EJBException;

    /**
     * @roseuid 3C4DD5F8007F

    /**
     * @J2EE_METHOD  --  getInterventionPhysicalLocators
     */
    public java.util.Collection<Object>  getInterventionPhysicalLocators(Long InterventionUID,
                                                                NBSSecurityObj nbsSecurityObj)
        throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * @roseuid 3C4DD5FA02BD

    /**
     * @J2EE_METHOD  --  getInterventionPostalLocators
     */
    public java.util.Collection<Object>  getInterventionPostalLocators(Long InterventionUID,
                                                              NBSSecurityObj nbsSecurityObj)
        throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * @roseuid 3C4DD5FD0127

    /**
     * @J2EE_METHOD  --  getInterventionTeleLocators
     */
    public java.util.Collection<Object>  getInterventionTeleLocators(Long interventionUID,
                                                            NBSSecurityObj nbsSecurityObj)
        throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * @roseuid 3C4DD5FF0378

    /**
     * @J2EE_METHOD  --  getAllPatientEncounterLocators
     */
    public java.util.Collection<Object>  getAllPatientEncounterLocators(Long patientEncounterUID,
                                                               NBSSecurityObj nbsSecurityObj)
        throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * @roseuid 3C4DD60201F6

    /**
     * @J2EE_METHOD  --  getPatientEncounterPhysicalLocators
     */
    public java.util.Collection<Object>  getPatientEncounterPhysicalLocators(Long patientEncounterUID,
                                                                    NBSSecurityObj nbsSecurityObj)
        throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * @roseuid 3C4DD6050060

    /**
     * @J2EE_METHOD  --  getPatientEncounterPostalLocators
     */
    public java.util.Collection<Object>  getPatientEncounterPostalLocators(Long patientEncounterUID,
                                                                  NBSSecurityObj nbsSecurityObj)
        throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * @roseuid 3C4DD6070257

    /**
     * @J2EE_METHOD  --  getPatientEncounterTeleLocators
     */
    public java.util.Collection<Object>  getPatientEncounterTeleLocators(Long patientEncounterUID,
                                                                NBSSecurityObj nbsSecurityObj)
        throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * @roseuid 3C4DD60A005D

    /**
     * @J2EE_METHOD  --  getActRelationship
     */
    public ActRelationshipDT getActRelationship(Long actUID,
                                                                      Long sourceActUID,
                                                                      NBSSecurityObj nbsSecurityObj)
        throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * @roseuid 3C4DD60D0148

    /**
     * @J2EE_METHOD  --  getActRelationships
     */
    public Collection<Object>  getActRelationships(Long actUID,
                                                    NBSSecurityObj nbsSecurityObj)
                                             throws java.rmi.RemoteException,
                                                    javax.ejb.EJBException;

    /**
     * @roseuid 3C4DD60F03CB

    /**
     * @J2EE_METHOD  --  getParticipation
     */
    public  ParticipationDT getParticipation(Long subjectEntityUID,
                                                 Long actUID,
                                                 Integer participationSeq,
                                                 String classCd,
                                                 Integer roleSeq,
                                                 NBSSecurityObj nbsSecurityObj)
        throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * @roseuid 3C4DD615035C

    /**
     * @J2EE_METHOD  --  getParticipations
     */
    public java.util.Collection<Object>  getParticipations(Long subjectEntityUID,
                                                  Long actUID,
                                                  NBSSecurityObj nbsSecurityObj)
                                           throws java.rmi.RemoteException,
                                                  javax.ejb.EJBException;

    /**
     * @roseuid 3C4DD6190109

    /**
     * @J2EE_METHOD  --  setAllClinicalDocumentLocators
     */
    public void setAllClinicalDocumentLocators(java.util.Collection<Object>  activityLocatorParticipationDTs,
                                               NBSSecurityObj nbsSecurityObj)
                                        throws java.rmi.RemoteException,
                                               javax.ejb.EJBException;

    /**
     * @roseuid 3C4DD61B0314
     * @J2EE_METHOD  --  setAllReferralLocators
     */
    public void setAllReferralLocators(java.util.Collection<Object>  activityLocatorParticipationDTs,
                                       NBSSecurityObj nbsSecurityObj)
                                throws java.rmi.RemoteException,
                                       javax.ejb.EJBException;

    /**
     * @roseuid 3C4DD847036E
     * @J2EE_METHOD  --  getObservationsByRecordStatus
     */
    public java.util.Collection<Object>  getObservationsByRecordStatus(String recordStatusCd,
                                                              NBSSecurityObj nbsSecurityObj)
        throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * @roseuid 3C4DDA390310
     * @J2EE_METHOD  --  getNotificationsByRecordStatus
     */
    public java.util.Collection<Object>  getNotificationsByRecordStatus(String recordStatusCd,
                                                               NBSSecurityObj nbsSecurityObj)
        throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * @roseuid 3C4DDC20019E
     * @J2EE_METHOD  --  getInvestigationsByRecordStatus
     */
    public java.util.Collection<Object>  getInvestigationsByRecordStatus(String resordStatusCd,
                                                                NBSSecurityObj nbsSecurityObj)
        throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * @roseuid 3EA4109E0384
     * @J2EE_METHOD  --  getTreatment
     */
    public TreatmentVO getTreatment(Long treatmentUid,
                                    NBSSecurityObj nbsSecurityObj)
        throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * @roseuid 3EA410A70307
     * @J2EE_METHOD  --  setTreatment
     */
    public java.lang.Long setTreatment(TreatmentVO treatmentVO,
                                       NBSSecurityObj nbsSecurityObj)
        throws java.rmi.RemoteException, javax.ejb.EJBException,
        NEDSSConcurrentDataException, NEDSSSystemException;

    /**
     * @roseuid 3EA410AD01AF
     * @J2EE_METHOD  --  getTreatmentInfo
     */
    public TreatmentDT getTreatmentInfo(Long treatmentUid,
                                        NBSSecurityObj nbsSecurityObj)
        throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * @roseuid 3EA410B40123
     * @J2EE_METHOD  --  setTreatmentInfo
     */
    public void setTreatmentInfo(TreatmentDT treatmentDT,
                                 NBSSecurityObj nbsSecurityObj)
        throws java.rmi.RemoteException, javax.ejb.EJBException,
        NEDSSConcurrentDataException, NEDSSSystemException;
;
public CTContactVO setCTContact(CTContactVO  cTContactVO,NBSSecurityObj nbsSecurityObj)throws java.rmi.RemoteException,
javax.ejb.EJBException, NEDSSConcurrentDataException;
public CTContactVO getCTContact(Long ctContactUid,NBSSecurityObj nbsSecurityObj)throws java.rmi.RemoteException,javax.ejb.EJBException;
public void deleteCTContact(Long ctContactUid,NBSSecurityObj nbsSecurityObj)throws java.rmi.RemoteException,javax.ejb.EJBException;


public Long setInterview(InterviewVO interviewVO,
		NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException,
		javax.ejb.EJBException, NEDSSConcurrentDataException,
		NEDSSSystemException;

public InterviewVO getInterview(Long interviewUID,
		NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException,
		javax.ejb.EJBException;
public PublicHealthCaseDT getPhcHist(Long publicHealthCaseUid,  Integer versionCtrlNbr, NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException,java.rmi.RemoteException,javax.ejb.EJBException;
}
