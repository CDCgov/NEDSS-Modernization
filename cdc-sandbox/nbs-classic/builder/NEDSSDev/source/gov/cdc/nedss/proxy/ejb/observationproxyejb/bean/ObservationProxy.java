//
// -- Java Code Generation Process --

package gov.cdc.nedss.proxy.ejb.observationproxyejb.bean;

// Import Statements
import javax.ejb.CreateException;
import javax.ejb.EJBObject;

import gov.cdc.nedss.act.observation.dt.EDXDocumentDT;
import gov.cdc.nedss.act.observation.vo.ObservationVO;
import gov.cdc.nedss.association.dt.ActRelationshipDT;
import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.page.ejb.pageproxyejb.dt.NBSAttachmentDT;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.InvestigationSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.*;
import gov.cdc.nedss.proxy.ejb.workupproxyejb.vo.WorkupProxyVO;
import gov.cdc.nedss.elr.helper.ELRActivityLogSearchDT;
import gov.cdc.nedss.entity.observation.vo.*;

import javax.ejb.EJBException;

import java.rmi.RemoteException;

import javax.ejb.*;

import java.util.*;

public interface ObservationProxy extends javax.ejb.EJBObject
{

    /**
     * @roseuid 3C92251A0280

    /**
     * @J2EE_METHOD  --  setLabResultProxy
     */
    public Map<Object,Object> setLabResultProxy    (LabResultProxyVO labResultProxyVO, NBSSecurityObj securityObj)
		throws java.rmi.RemoteException, javax.ejb.CreateException, NEDSSConcurrentDataException;

    public Long setNBSAttachment(NBSAttachmentDT nbsAttachmentDT, NBSSecurityObj nbsSecurityObj)throws RemoteException, EJBException, NEDSSSystemException, NEDSSConcurrentDataException, CreateException;
    /**
     * @roseuid 3C92251B006D

    /**
     * @J2EE_METHOD  --  getLabResultProxy
     */
    public LabResultProxyVO getLabResultProxy    (Long observationUID, NBSSecurityObj securityObj) throws java.rmi.RemoteException, javax.ejb.EJBException, NEDSSSystemException;

    public LabResultProxyVO getLabResultProxyByType    (Long observationUID, boolean isELR, NBSSecurityObj securityObj) throws java.rmi.RemoteException, javax.ejb.EJBException, NEDSSSystemException;

    /**
     * @roseuid 3C92251B0251

    /**
     * @J2EE_METHOD  --  getGenericObservationProxy
     */
    public GenericObservationProxyVO getGenericObservationProxy    (Long observationUID, NBSSecurityObj securityObj) throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * @roseuid 3C92251C004E

    /**
     * @J2EE_METHOD  --  setGenericObservationProxy
     */
    public Long setGenericObservationProxy    (GenericObservationProxyVO genericObservationProxyVO, NBSSecurityObj securityObj) throws java.rmi.RemoteException, javax.ejb.EJBException, NEDSSConcurrentDataException;

    /**
     * @roseuid 3C922535034B
     * @J2EE_METHOD  --  deleteLabResultProxy
     */
    public boolean deleteLabResultProxy    (Long observationUid, NBSSecurityObj securityObj) throws javax.ejb.EJBException, javax.ejb.CreateException,java.rmi.RemoteException,javax.ejb.FinderException, NEDSSSystemException, NEDSSConcurrentDataException;

    public boolean deleteMorbidityProxy    (Long observationUid, NBSSecurityObj securityObj) throws java.rmi.RemoteException, javax.ejb.EJBException, NEDSSConcurrentDataException;
    /**
     * @roseuid 3C9225E003B9
     * @J2EE_METHOD  --  deleteGenericObservationProxy
     */
    public boolean deleteGenericObservationProxy    (Long observationUid, NBSSecurityObj securityObj) throws java.rmi.RemoteException, javax.ejb.EJBException, NEDSSConcurrentDataException;

     /**
     * @roseuid 3CEA39440109
     * @J2EE_METHOD  --  setMorbidityProxy
     */
    public Collection<Object>  setMorbidityProxy    ( MorbidityProxyVO morbidityProxyVO, NBSSecurityObj nbsSecurityObj)   throws java.rmi.RemoteException, javax.ejb.EJBException, javax.ejb.CreateException, NEDSSSystemException, NEDSSConcurrentDataException;

  /**
     * @roseuid 3CEA38DE01A5
     * @J2EE_METHOD  --  getMorbidityProxy
     */
    public MorbidityProxyVO getMorbidityProxy    (Long observationUID, NBSSecurityObj nbsSecurityObj)  throws java.rmi.RemoteException, javax.ejb.EJBException, NEDSSSystemException, javax.ejb.FinderException, javax.ejb.CreateException;

 /**
     * @roseuid 3D0F376502D4
     * @J2EE_METHOD  --  setMorbidityProxyWithAutoAssoc
     */
    public Collection<Object>  setMorbidityProxyWithAutoAssoc    (MorbidityProxyVO morbidityProxyVO, Long investigationUid, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException, javax.ejb.CreateException, NEDSSSystemException, NEDSSConcurrentDataException;


       /**
     * @roseuid 3D0F339C00BE
     * @J2EE_METHOD  --  setLabResultProxyWithAutoAssoc
     */
    public Map<Object,Object> setLabResultProxyWithAutoAssoc    (LabResultProxyVO labResultProxyVO, Long investigationUid, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException, FinderException, CreateException, NEDSSSystemException,  NEDSSConcurrentDataException;


	/**
     * @roseuid 3D20773A0301
     * @J2EE_METHOD  --  processObservation
     */
    public boolean processObservation    (Long observationUid, NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException, javax.ejb.CreateException,java.rmi.RemoteException,javax.ejb.FinderException, NEDSSSystemException, NEDSSConcurrentDataException;

    public boolean processObservationWithProcessingDecision (Long observationUid, String processingDecisionCd, String processingDecisionTxt, NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException, javax.ejb.CreateException,java.rmi.RemoteException,javax.ejb.FinderException, NEDSSSystemException, NEDSSConcurrentDataException;

    public boolean unProcessObservation    (Long observationUid, NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException, javax.ejb.CreateException,java.rmi.RemoteException,javax.ejb.FinderException, NEDSSSystemException, NEDSSConcurrentDataException;

    /**
	 * @roseuid
	 * @J2EE_METHOD -- getObservationLocalID
	 */
	public String getObservationLocalID    (Long observationUid, NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException,javax.ejb.CreateException,java.rmi.RemoteException,javax.ejb.FinderException, NEDSSSystemException, NEDSSConcurrentDataException;

    /**
     *
     * @J2EE_METHOD  --  transferOwnership
     *
     */

public void transferOwnership(Long observationUID, String newProgramAreaCode, String newJurisdictionCode,String Cascading, NBSSecurityObj nbsSecurityObj)
  throws java.rmi.RemoteException, javax.ejb.EJBException, javax.ejb.CreateException, javax.ejb.FinderException, NEDSSSystemException, NEDSSConcurrentDataException;


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
     * @roseuid 3C87D69D03D5
     * @J2EE_METHOD  --  findPerson
     */

    //public Collection<Object>  findPerson    (ObservationSearchVO thePersonSearchVO, int cacheNumber, int fromIndex, NBSSecurityObj nbsSecurityObj) throws RemoteException, EJBException,NEDSSSystemException;

	public Collection<Object>  findOrderedTestName(String clia, Long labId, String searchString, String searchType, int cacheNumber, int fromIndex, NBSSecurityObj nbsSecurityObj) throws RemoteException, EJBException,NEDSSSystemException;
	public Collection<Object>  findDrugTestName(String clia, Long labId, String searchString, String searchType, int cacheNumber, int fromIndex, NBSSecurityObj nbsSecurityObj) throws RemoteException, EJBException,NEDSSSystemException;
	public Collection<Object>  findDrugTestNameOrCode(String clia, Long labId, String searchString, String searchType, int cacheNumber, int fromIndex, NBSSecurityObj nbsSecurityObj) throws RemoteException, EJBException,NEDSSSystemException;
	public Collection<Object>  findResultedTestName(String clia, Long labId, String searchString, String searchType, int cacheNumber, int fromIndex, NBSSecurityObj nbsSecurityObj) throws RemoteException, EJBException,NEDSSSystemException;
	public Collection<Object>  findLabResultedTestName(String clia, Long labId, String searchString, String searchType, NBSSecurityObj nbsSecurityObj) throws RemoteException, EJBException,NEDSSSystemException;
	public Collection<Object>  findLabCodedResultByCode(String clia, Long labId, String searchCode, String searchType, NBSSecurityObj nbsSecurityObj) throws RemoteException, EJBException,NEDSSSystemException;
	public Collection<Object>  findLabCodedResult(String clia, Long labId, String searchString, String searchType, NBSSecurityObj nbsSecurityObj) throws RemoteException, EJBException,NEDSSSystemException;
	public Collection<Object> findLabResultedTestByCode(String clia, Long labId,String searchCode, String searchType, NBSSecurityObj nbsSecurityObj) throws RemoteException, EJBException, NEDSSSystemException;
	public Collection<Object>  findDrugsByName(String clia, Long labId, String searchString, String searchType, String method, int cacheNumber, int fromIndex, NBSSecurityObj nbsSecurityObj) throws RemoteException, EJBException,NEDSSSystemException;
	public Collection<Object>  findOrganismsByName(String clia, Long labId, String searchString, String searchType, int cacheNumber, int fromIndex, NBSSecurityObj nbsSecurityObj) throws RemoteException, EJBException,NEDSSSystemException;

        /**
         *
         * @param observationVOCollection
         * @param actRelationshipDTCollection
         * @param nbsSecurityObj
         * @throws RemoteException
         */
        public void addUserComments(Collection<Object> observationVOCollection, Collection<Object>  actRelationshipDTCollection, NBSSecurityObj nbsSecurityObj) throws RemoteException;

        /**
         *
         * @param testNameCondition
         * @param labClia
         * @param labUid
         * @param programAreaCd
         * @return
         * @throws RemoteException
         */
        public Boolean getOrganismReqdIndicatorForResultedTest(String testNameCondition,
                                                         String labClia,
                                                         Long labUid,
                                                         String programAreaCd,
                                                         NBSSecurityObj nbsSecurityObj)
                                                         throws RemoteException;

       public String getLaboratorySystemDescTxt(String laboratory_id,
                                                 NBSSecurityObj nbsSecurityObj) throws RemoteException;
        public TreeMap<Object,Object> getCodeSystemDescription(String laboratory_id, NBSSecurityObj nbsSecurityObj) throws RemoteException;
        public Boolean isAssociatedWithMorb(Long observationUID, NBSSecurityObj nbsSecurityObj)  throws java.rmi.RemoteException, javax.ejb.EJBException, NEDSSSystemException, javax.ejb.FinderException, javax.ejb.CreateException;

        public Integer associatedInvestigationCheck(Long ObservationUid,
                NBSSecurityObj nbsSecurityObj) throws java.
      rmi.RemoteException, javax.ejb.EJBException, javax.ejb.CreateException,
      NEDSSSystemException;
        public EDXDocumentDT getXMLDocument    (Long documentUid, NBSSecurityObj securityObj) throws javax.ejb.EJBException, javax.ejb.CreateException,java.rmi.RemoteException,javax.ejb.FinderException, NEDSSSystemException, NEDSSConcurrentDataException;
        
        public Collection<Object>  getAssociatedInvestigations    (Long personUID, Long observationUid, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException, NEDSSAppException, NEDSSSystemException;
        
        public void setAssociatedInvestigations(Long observationUID,  String obsTypeCd, String processingDecisionCd,Collection<Object>  newPhcRelations, Collection<Object> deletePhcRelations, String businessTriggerSpecified, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.CreateException, javax.ejb.EJBException, NEDSSAppException, NEDSSSystemException;
        public Collection<Object> getAssociatedPublicHealthCases(Long uid, NBSSecurityObj securityObj) throws java.rmi.RemoteException, javax.ejb.CreateException, NEDSSConcurrentDataException;
        public void setLabInvAssociation(Long labUid,Long investigationUid, NBSSecurityObj nbsSecurityObj)throws java.rmi.RemoteException,javax.ejb.EJBException, javax.ejb.FinderException,javax.ejb.CreateException, NEDSSSystemException,NEDSSConcurrentDataException;
        /**
         * Creates a collection of ActivityLogReports
         * @param fromDate : String
         * @param toDate : Sring
         * @param statusList : ArrayList
         * @param nbsSecurityObj : NBSSecurityObj
         * @return Collection
         * @throw NEDSSSystemException, RemoteException
         */
        public Collection<Object>  getActivityLogReport(ELRActivityLogSearchDT elrActivityLogSearchDT, NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException, RemoteException;
        public String getCodeSetGroupIdFromQuestionIdentifier(String questionIdentifier, NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException, RemoteException;;
}
