package gov.cdc.nedss.systemservice.ejb.pamconversionejb.bean;

import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.page.ejb.portproxyejb.dao.PortPageDAO;
import gov.cdc.nedss.pagemanagement.wa.dt.WaTemplateDT;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.InvestigationProxyVO;
import gov.cdc.nedss.systemservice.ejb.pamconversionejb.dao.PamConversionDAO;
import gov.cdc.nedss.systemservice.ejb.pamconversionejb.dao.PamConversionLegacyDAO;
import gov.cdc.nedss.systemservice.ejb.pamconversionejb.dao.PamConversionLoggerDAO;
import gov.cdc.nedss.systemservice.ejb.pamconversionejb.dao.ReversePamConversionDAO;
import gov.cdc.nedss.systemservice.ejb.pamconversionejb.dt.NBSConversionConditionDT;
import gov.cdc.nedss.systemservice.ejb.pamconversionejb.dt.NBSConversionErrorDT;
import gov.cdc.nedss.systemservice.ejb.pamconversionejb.dt.NBSConversionMasterDT;
import gov.cdc.nedss.systemservice.ejb.pamconversionejb.util.ConvertToNewStructure;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.PropertyUtil;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;


/**
 * @updated Release 4.1
 * @author Pradeep Sharma
 *
 */
public class PamConversionEJB implements SessionBean
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static final LogUtils logger = new LogUtils( (PamConversionEJB.class).getName()); 
	private static PropertyUtil propertyUtil= PropertyUtil.getInstance();
	private SessionContext sessionContext;

	public void ejbCreate()
	{
	}

	public void ejbRemove() throws RemoteException
	{
	}

	public void ejbActivate() throws RemoteException
	{
		logger.debug("*** ejbActivate ...");
	}

	public void ejbPassivate() throws RemoteException
	{
		logger.debug("*** ejbPassivate ...");
	}

	public void setSessionContext(SessionContext sessionContext) throws
	RemoteException
	{
		this.sessionContext = sessionContext;
	}


	public void NBSConverter(){

	}

	public void NBSPrerun(){

	}
	public void updateConditionCode(String conditionCd,WaTemplateDT waTemplateDT, Timestamp recordStatusTime, NBSSecurityObj nbsSecurityObj) throws RemoteException{
		try {
			PamConversionLegacyDAO pamConversionLegacyDAO = new PamConversionLegacyDAO();
			pamConversionLegacyDAO.updateConditionCode(conditionCd, waTemplateDT,recordStatusTime);
		} catch (NEDSSSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	}
	public WaTemplateDT updateCondition(String cdToBeTranslated, NBSSecurityObj nbsSecurityObj) throws RemoteException{
		try {
			ConvertToNewStructure convertToNewStructure =new ConvertToNewStructure();
			WaTemplateDT waTemplateDT =convertToNewStructure.getWaTemplateByConditionCd(cdToBeTranslated);
			return waTemplateDT;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	}
	/**
	 * Logically delete the State Defined LDFs
	 * @param cdToBeTranslated
	 * @param lastChgTime
	 * @param nbsSecurityObj
	 * @throws RemoteException
	 */
	public void removeMetadata(String cdToBeTranslated, Timestamp lastChgTime, NBSSecurityObj nbsSecurityObj) throws RemoteException{
		try {
			ConvertToNewStructure convertToNewStructure =new ConvertToNewStructure();
			convertToNewStructure.removeMetadata(cdToBeTranslated, lastChgTime);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	}
	public WaTemplateDT getWaTemplateByConditionCd(String cdToBeTranslated, NBSSecurityObj nbsSecurityObj) throws RemoteException{
		try {
			ConvertToNewStructure convertToNewStructure =new ConvertToNewStructure();
			WaTemplateDT waTemplateDT =convertToNewStructure.getWaTemplateByConditionCd(cdToBeTranslated);
			return waTemplateDT;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
		
	}
	
	public Map<Object,Object>  getCachedConditionCodeIdMap(Long conditionCdGroupId, NBSSecurityObj nbsSecurityObj) throws RemoteException{
		try {
			PamConversionDAO pamConversionDAO = new PamConversionDAO();
			Map<Object,Object> map = pamConversionDAO.getCachedConditionCodeIdMap(conditionCdGroupId);
			return map;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	}

	
	public InvestigationProxyVO  getOldInvestigationProxyVO(String cd,NBSSecurityObj nbsSecurityObj)throws RemoteException{

		PamConversionLegacyDAO pamConversionLegacyDAO =new PamConversionLegacyDAO();
		InvestigationProxyVO  investigationProxyVO= new InvestigationProxyVO();
		try {
			investigationProxyVO = pamConversionLegacyDAO.getDataFromOldStructure( cd);
		} catch (Exception e) {
			logger.fatal("Exception in getOldInvestigationProxyVO:  ERROR = " + e.getMessage(),e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
		return investigationProxyVO;
	}

	public  InvestigationProxyVO convertToNewStructure(InvestigationProxyVO investigationProxyVO, NBSSecurityObj nbsSecurityObj) throws RemoteException{
		if(investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT()!=null){
			NBSConversionMasterDT nBSConversionMasterDT = new NBSConversionMasterDT();
			nBSConversionMasterDT.setActUid(investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid());
			nBSConversionMasterDT.setProcessTypeInd("Production");
			java.util.Date dateTime = new java.util.Date();
			Timestamp startTime = new Timestamp(dateTime.getTime());
			nBSConversionMasterDT.setStartTime(startTime);
			String recordStatusCd=investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getRecordStatusCd();
			String conditionCd = investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd();
			try{
				//set NbsConversionConditionUid in nBSConversionMasterDT
				PamConversionDAO pamConversionDAO = new PamConversionDAO();
				if (pamConversionDAO.conditionCd == null || !pamConversionDAO.conditionCd.equals(conditionCd))
					pamConversionDAO.initializePamConversionMapping();
				NBSConversionConditionDT nbsConversionConditionDT = pamConversionDAO.getNBSConversionConditionDTForCondition(investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd(), null);
				nBSConversionMasterDT.setNbsConversionConditionUid(nbsConversionConditionDT.getNbsConversionConditionUid());
				Map<Object,Object> questionIdMap = pamConversionDAO.getCachedQuestionIdMap(investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd());
				ConvertToNewStructure convertToNewStructure = new ConvertToNewStructure();
				Collection<Object>  coll =null;
				Map<Object,Object> map = convertToNewStructure.convertToNewStructure(investigationProxyVO, questionIdMap, pamConversionDAO, nbsSecurityObj );
				if(map.get("ERROR")!=null){
					coll =(ArrayList<Object> )map.get("ERROR"); 
				}
				if(coll!=null && coll.size()>0){
					nBSConversionMasterDT.setNBSConversionErrorDTCollection(coll);
					nBSConversionMasterDT.setProcessMessageTxt("There exists "+coll.size()+" Errors. Please check NBS_conversion_error table for further details." );
					nBSConversionMasterDT.setStatusCd("Fail");
					investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().setRecordStatusCd("OPEN");
					PamConversionLegacyDAO pamConversionLegacyDAO = new PamConversionLegacyDAO();
					pamConversionLegacyDAO.updatePublicHealthCaseDT(investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT());
				}else{
					nBSConversionMasterDT.setStatusCd("Pass");
					String localId = investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getLocalId();
					if (localId != null)
						nBSConversionMasterDT.setProcessMessageTxt("Conversion successful for "+localId);
					else
						nBSConversionMasterDT.setProcessMessageTxt("Public Health Case conversion successful");
				}
				PamConversionLegacyDAO pamConversionLegacyDAO = new PamConversionLegacyDAO();
				if( coll!=null && coll.size()>0){
					pamConversionLegacyDAO.updatePublicHealthCaseDT(investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT());
					investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().setRecordStatusCd(recordStatusCd);
				}
				InvestigationProxyVO invProxyVO = (InvestigationProxyVO)map.get("investigationProxyVO");
				java.util.Date endDate= new java.util.Date();
				Timestamp endTime = new Timestamp(endDate.getTime());
				nBSConversionMasterDT.setEndTime(endTime);
				writeToLogMaster(nBSConversionMasterDT,nbsSecurityObj);
				return invProxyVO;
			}catch(Exception ex){
				java.util.Date endDate= new java.util.Date();
				Timestamp endTime = new Timestamp(endDate.getTime());
				nBSConversionMasterDT.setEndTime(endTime);
				nBSConversionMasterDT.setStatusCd("Fail");
				nBSConversionMasterDT.setProcessTypeInd("Production");
				nBSConversionMasterDT.setProcessMessageTxt(ex.toString());
				PamConversionLegacyDAO pamConversionLegacyDAO = new PamConversionLegacyDAO();
				investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().setRecordStatusCd("OPEN");
				pamConversionLegacyDAO.updatePublicHealthCaseDT(investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT());
				writeToLogMaster(nBSConversionMasterDT, nbsSecurityObj);
				logger.fatal(ex.getMessage(), ex);
				throw new RemoteException(ex.getMessage(), ex);
			}
		}else{
			logger.error("publicHealthCaseDT is null"+investigationProxyVO.getPublicHealthCaseVO());
			
			return null;
		}

	}

	public Integer getNumberOfCasesToTransfer(String cd, String coversionType, NBSSecurityObj nbsSecurityOb)throws RemoteException{
		try {
			PamConversionLegacyDAO pamConversionLegacyDAO = new PamConversionLegacyDAO();
			int numberOfCases = pamConversionLegacyDAO.getPublicHealthDTCount(cd, coversionType);
			return new Integer(numberOfCases);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(),e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	}
	public void writeToLogMaster(NBSConversionMasterDT nBSConversionMasterDT,NBSSecurityObj nbsSecurityOb) throws RemoteException{
		try {
			PamConversionLoggerDAO pamConversionLoggerDAO = new PamConversionLoggerDAO();
			pamConversionLoggerDAO.insertNBSConversionMasterDT(nBSConversionMasterDT);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(),e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	}
	
	public HashMap<Object,Object> checkMappingErrors(Boolean isPrerun, String conditionCd, String conversionType, NBSSecurityObj nbsSecurityObj) throws RemoteException{
		try {
			HashMap<Object,Object> returnMap = new HashMap<Object,Object>();
			java.util.Date startDate= new java.util.Date();
			NBSConversionMasterDT nBSConversionMasterDT = new NBSConversionMasterDT();
			Timestamp startTime = new Timestamp(startDate.getTime());
				PamConversionDAO pamConversionDAO = new PamConversionDAO();
				pamConversionDAO.initializePamConversionMapping();
				
				Long conditionCdGroupId = null;
				NBSConversionConditionDT nbsConversionConditionDT = pamConversionDAO.getNBSConversionConditionDTForCondition(conditionCd,null);
				if(nbsConversionConditionDT != null){
					conditionCdGroupId= nbsConversionConditionDT.getConditionCdGroupId();
				}
				//pamConversionDAO.getGroupIdForCondition(conditionCd);
				
				//This condition not in the nbs_conversion_condition - user probably made a typo..
				if (conditionCdGroupId == null) {
					returnMap.put("NO_COND_FOUND", " Note: The condition code entered is not in the nbs_conversion_condition table.\n Please correct the condition code and try again..\n Batch process ended.");
					return returnMap;
				}
			    returnMap.put("CONDITION_CD_GROUP_ID", conditionCdGroupId);
				StringBuffer buff= new StringBuffer();
				int noCasesToBeTransferred = 0;
				noCasesToBeTransferred = getNumberOfCasesToTransfer(conditionCd, conversionType, nbsSecurityObj);
				buff.append("\nFor condition Code \""+conditionCd +"\", "+ noCasesToBeTransferred + " case(s) require conversion to new PageBuilder format." );
					
				Collection<Object>  mappingErrorColl = pamConversionDAO.getNBSConvserionsCollection(conditionCd, conditionCdGroupId);
				nBSConversionMasterDT=pamConversionDAO.checkIfPageExistsForCondition(conditionCd);
				if(nBSConversionMasterDT.getProcessMessageTxt()!=null && nBSConversionMasterDT.getProcessMessageTxt().length()>10){
						if(isPrerun.booleanValue()){
							nBSConversionMasterDT.setProcessTypeInd("Prerun");
						}else{
							nBSConversionMasterDT.setProcessTypeInd("Production");
						}
						returnMap.put("NO_PAGE_FOUND", nBSConversionMasterDT);
				}else{
						nBSConversionMasterDT.setNBSConversionErrorDTCollection(mappingErrorColl);
						returnMap.put("NUMBER_OF_CASES", new Integer(noCasesToBeTransferred));
						if(isPrerun.booleanValue()){
							nBSConversionMasterDT.setProcessTypeInd("Prerun");
						}else{
							nBSConversionMasterDT.setProcessTypeInd("Production");
							returnMap.put("ERROR", "NBS legacy to PageBuilder conversion aborted. There exists " +mappingErrorColl.size()+" mapping Error(s). Please check nbs_conversion_error for details." + buff.toString());
						}
							if(mappingErrorColl.size()>0){
								returnMap.put("mapping_error", new Integer(mappingErrorColl.size()));
								nBSConversionMasterDT.setStatusCd("Fail");
								nBSConversionMasterDT.setProcessMessageTxt("There exists "+mappingErrorColl.size()+" mapping Error(s). Please check NBS_conversion_error table for further details."+ buff.toString() );
								returnMap.put("ERROR", "NBS Prerun completed. There exists " +mappingErrorColl.size()+" mapping Error(s). Please check nbs_conversion_error for details. " +buff.toString());
							}else{
								nBSConversionMasterDT.setProcessMessageTxt("There exists "+mappingErrorColl.size()+" mapping Error(s)." +buff.toString());
								returnMap.put("ERROR", "NBS Prerun completed. There exists no mapping Errors." +buff.toString() );
								nBSConversionMasterDT.setStatusCd("Pass");
							}
					}
					nBSConversionMasterDT.setNbsConversionConditionUid(nbsConversionConditionDT.getNbsConversionConditionUid());
					java.util.Date endDate= new java.util.Date();
					Timestamp endTime = new Timestamp(endDate.getTime());
					nBSConversionMasterDT.setStartTime(startTime);
					nBSConversionMasterDT.setEndTime(endTime);
					try {
						writeToLogMaster(nBSConversionMasterDT,nbsSecurityObj);
					} catch (RemoteException e) {
						logger.error("Error while writing to Log files:"+ e);
					}
					
				return returnMap;
		} catch (NEDSSSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(),e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	}
	
	public ArrayList<Object> getNBSConversionMasterLogByCondition(String conditionCd, Long nbsConversionPageMgmtUid, NBSSecurityObj nbsSecurityObj) throws RemoteException, Exception{
		try{
			logger.debug("conditionCd: "+conditionCd);
			PamConversionLoggerDAO pamConversionLoggerDAO = new PamConversionLoggerDAO();
			ArrayList<Object> coreQuestionsList = pamConversionLoggerDAO.getNBSConversionMasterLogByCondition(conditionCd, nbsConversionPageMgmtUid);
			return coreQuestionsList;
		}catch (Exception ex) {
			logger.fatal("PamConversionEJB.getNBSConversionMasterLogByCondition: conditionCd:"+conditionCd+", Exception: " + ex.getMessage(), ex);
			throw new Exception(ex.toString(),ex);
		}
	}
	
	public void writeToLogError(NBSConversionErrorDT nbsConversionErrorDT,NBSSecurityObj nbsSecurityOb) throws RemoteException{
		try {
			PamConversionLoggerDAO pamConversionLoggerDAO = new PamConversionLoggerDAO();
			pamConversionLoggerDAO.insertNBSConversionErrorDT(nbsConversionErrorDT);
		} catch (Exception ex) {
			logger.fatal("Exception: "+ex.getMessage(),ex);
			throw new NEDSSSystemException(ex.getMessage(), ex);
		}
	}
	
	/**
	 * @param conditionCd
	 * @param nbsSecurityOb
	 * @return
	 * @throws RemoteException
	 */
	public boolean postConversionMappingCorrections(String conditionCd, NBSSecurityObj nbsSecurityOb) throws RemoteException{
		try {
			PamConversionLegacyDAO pamConversionLegacyDAO =new PamConversionLegacyDAO();
			boolean success = pamConversionLegacyDAO.postConversionMappingCorrections(conditionCd);
			return success;
		} catch (Exception ex) {
			logger.fatal("Exception: "+ex.getMessage(),ex);
			throw new NEDSSSystemException(ex.getMessage(), ex);
		}
	}
	
}
