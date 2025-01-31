package gov.cdc.nedss.systemservice.ejb.pamconversionejb.bean;

import gov.cdc.nedss.pagemanagement.wa.dt.WaTemplateDT;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.InvestigationProxyVO;
import gov.cdc.nedss.systemservice.ejb.pamconversionejb.dt.NBSConversionConditionDT;
import gov.cdc.nedss.systemservice.ejb.pamconversionejb.dt.NBSConversionErrorDT;
import gov.cdc.nedss.systemservice.ejb.pamconversionejb.dt.NBSConversionMasterDT;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJBObject;

/**
 * 
 * @author Pradeep Sharma
 *
 */
public interface PamConversion extends EJBObject {

	/*public HashMap<Object,Object> dataConverter(Integer userDefinedCasesToBeTranslated,NBSSecurityObj nbsSecurityObj) throws RemoteException;*/
	public InvestigationProxyVO convertToNewStructure(InvestigationProxyVO investigationProxyVO, NBSSecurityObj nbsSecurityObj) throws RemoteException;
	public void writeToLogMaster(NBSConversionMasterDT nBSConversionMasterD,NBSSecurityObj nbsSecurityObjT) throws RemoteException;
	public HashMap<Object,Object> checkMappingErrors(Boolean isPrerun, String conditionCd, String coversionType, NBSSecurityObj nbsSecurityObj) throws RemoteException;		
	public Map<Object,Object>  getCachedConditionCodeIdMap(Long conditionCdGroupId, NBSSecurityObj nbsSecurityObj) throws RemoteException;
	public WaTemplateDT getWaTemplateByConditionCd(String cdToBeTranslated, NBSSecurityObj nbsSecurityObj) throws RemoteException;
	public WaTemplateDT updateCondition(String cdToBeTranslated, NBSSecurityObj nbsSecurityObj) throws RemoteException;
	public void removeMetadata(String cdToBeTranslated, Timestamp lastChgTime, NBSSecurityObj nbsSecurityObj) throws RemoteException;
	public void updateConditionCode(String conditionCd,WaTemplateDT waTemplateDT, Timestamp recordStatusTime, NBSSecurityObj nbsSecurityObj)throws RemoteException;
	public Integer getNumberOfCasesToTransfer(String cd, String coversionType, NBSSecurityObj nbsSecurityOb)throws RemoteException;
	public InvestigationProxyVO  getOldInvestigationProxyVO(String cd,NBSSecurityObj nbsSecurityObj)throws RemoteException;
	public ArrayList<Object> getNBSConversionMasterLogByCondition(String conditionCd, Long nbsConversionPageMgmtUid, NBSSecurityObj nbsSecurityObj) throws RemoteException;
	public void writeToLogError(NBSConversionErrorDT nbsConversionErrorDT,NBSSecurityObj nbsSecurityOb) throws RemoteException;
	public boolean postConversionMappingCorrections(String conditionCd, NBSSecurityObj nbsSecurityOb) throws RemoteException;
}