// *** Generated Source File ***
// Portions Copyright (c) 1996-2001, SilverStream Software, Inc., All Rights Reserved

package gov.cdc.nedss.proxy.ejb.investigationproxyejb.bean;

import java.util.*;

import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.*;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.vo.NotificationProxyVO;
import gov.cdc.nedss.act.summaryreport.vo.*;
import gov.cdc.nedss.exception.*;

public interface InvestigationProxy
    extends javax.ejb.EJBObject {
  /**
   * @roseuid 3BF9917502DC
   * @J2EE_METHOD  --  setInvestigationProxy
   */
  public Long setInvestigationProxy(InvestigationProxyVO investigationProxyVO,
                                    NBSSecurityObj nbsSecurityObj) throws java.
      rmi.RemoteException, javax.ejb.EJBException, javax.ejb.CreateException,
      NEDSSSystemException, NEDSSConcurrentDataException;

  /**
   * @roseuid 3BF99251002F
   * @J2EE_METHOD  --  getInvestigation
   */
  public InvestigationProxyVO getInvestigationProxy(Long publicHealthCaseUID,
      NBSSecurityObj nbsSecurityObj) throws
      java.rmi.RemoteException,
      javax.ejb.EJBException,
      NEDSSSystemException,
      javax.ejb.FinderException,
      javax.ejb.CreateException;
  
  public InvestigationProxyVO getInvestigationProxyLite(Long publicHealthCaseUID,
	      NBSSecurityObj nbsSecurityObj, boolean lite) throws
	      java.rmi.RemoteException,
	      javax.ejb.EJBException,
	      NEDSSSystemException,
	      javax.ejb.FinderException,
	      javax.ejb.CreateException;

 
  public void setAssociations(Long investigationUID,  Collection<Object>  reportSumVOCollection, Collection<Object>  vaccinationSummaryVOCollection, Collection<Object>  summaryDTColl, Collection<Object> treatmentSumColl, Boolean isNNDResendCheckReuired,
		  NBSSecurityObj nbsSecurityObj) throws
		  java.rmi.RemoteException, javax.ejb.EJBException,
		  javax.ejb.CreateException, NEDSSConcurrentDataException;

 
  /**
   * @roseuid 3C32481E031F
   * @J2EE_METHOD  --  retrieveVaccinationSummaryList
   */

  
  public Collection<Object>  retrieveNotificationSummaryListForManage(Long
      investigationUID, NBSSecurityObj nbsSecurityObj) throws
      java.rmi.RemoteException,
      NEDSSSystemException,
      javax.ejb.CreateException;

  public void transferOwnership(Long publicHealthCaseUID,
                                String newJurisdictionCode, Boolean isExportCase,
                                NBSSecurityObj nbsSecurityObj) throws
      java.rmi.RemoteException,
      javax.ejb.EJBException,
      NEDSSSystemException,
      NEDSSConcurrentDataException;

  public Long setInvestigationProxyWithAutoAssoc(InvestigationProxyVO
                                                 investigationProxyVO,
                                                 Long observationUid,
                                                 String observationTypeCd,
                                                 NBSSecurityObj nBSSecurityObj) throws
      javax.ejb.EJBException,
      javax.ejb.CreateException,
      java.rmi.RemoteException,
      NEDSSSystemException,
      NEDSSConcurrentDataException;


  /**
   * @roseuid 3D1B937800C8
   * @J2EE_METHOD  --  setSummaryReportProxy
   */
  public Long setSummaryReportProxy(SummaryReportProxyVO summaryReportProxyVO,
                                    NBSSecurityObj nbsSecurityObj) throws
      javax.ejb.EJBException,
      javax.ejb.CreateException,
      java.rmi.RemoteException,
      NEDSSSystemException,
      NEDSSConcurrentDataException;

  /**
   * @roseuid 3D1B937801F4
   * @J2EE_METHOD  --  getSummaryReportsForMMWR
   */
  public Collection<Object>  getSummaryReportsForMMWR(String county,
                                             String mmwrWeek,
                                             String mmwrYear,
                                             NBSSecurityObj nbsSecurityObj) throws
      javax.ejb.EJBException,
      javax.ejb.CreateException,
      java.rmi.RemoteException,
      NEDSSSystemException,
      NEDSSConcurrentDataException;

  /**
   * @roseuid 3D1B93790015
   * @J2EE_METHOD  --  getSummaryReportProxy
   */
  public SummaryReportProxyVO getSummaryReportProxy(Long publicHealthCaseUid,
      NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException,
      javax.ejb.CreateException,
      java.rmi.RemoteException,
      NEDSSSystemException;

  public String publicHealthCaseLocalID(Long publicHealthCaseUid,
                                        NBSSecurityObj nbsSecurityObj) throws
      javax.ejb.EJBException,
      java.rmi.RemoteException, NEDSSSystemException;
  

  public boolean investigationAssociatedWithMorbidity(Long morbidityUid,
      NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException,
      java.rmi.RemoteException, NEDSSSystemException;

  public Map<Object,Object> deleteInvestigationProxy(Long publicHealthCaseUid,
          NBSSecurityObj nbsSecurityObj) throws
          java.rmi.RemoteException,
          javax.ejb.EJBException,
          javax.ejb.CreateException,
          NEDSSSystemException,
          javax.ejb.FinderException,
          NEDSSConcurrentDataException;
  public HashMap<Object,Object> getPHCConditionAndProgArea(Long publicHealthCaseUid,
			NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException,
			java.rmi.RemoteException, NEDSSSystemException;
  public Long exportOwnership( NotificationProxyVO notProxyVO, String newJurisdictionCode, NBSSecurityObj nbsSecurityObj) 
  		throws 
  			javax.ejb.EJBException, 
  			NEDSSSystemException,
  			NEDSSConcurrentDataException,
  			java.rmi.RemoteException;
  public ManageSummaryVO getManageSummaryVO( Long investigationUID,Long mprUID,
	      NBSSecurityObj nbsSecurityObj) throws java.
	      rmi.RemoteException, javax.ejb.EJBException, javax.ejb.CreateException,
	      NEDSSSystemException, NEDSSConcurrentDataException; 
  
  public String getConditionCd(String conditionCd, NBSSecurityObj nbsSecurityObj) throws java.
	      rmi.RemoteException, javax.ejb.EJBException, javax.ejb.CreateException,
	      NEDSSSystemException, NEDSSConcurrentDataException; 
  
}

