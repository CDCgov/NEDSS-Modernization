package gov.cdc.nedss.systemservice.ejb.pamconversionejb.dao;

import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.page.ejb.portproxyejb.dt.MappedFromToQuestionFieldsDT;
import gov.cdc.nedss.systemservice.ejb.pamconversionejb.dt.NBSConversionErrorDT;
import gov.cdc.nedss.systemservice.ejb.pamconversionejb.dt.NBSConversionMasterDT;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
/**
 * PamConvserionLoggerDAO
 * Description:
 * Copyright:    Copyright (c) 2008
 * @author Pradeep Sharma
 */
public class PamConversionLoggerDAO  extends DAOBase{
	static final LogUtils logger = new LogUtils(PamConversionLoggerDAO.class.getName());
	private String INSERT_INTO_LOG="INSERT INTO NBS_conversion_error (error_cd,error_message_txt, nbs_conversion_mapping_uid, condition_cd_group_id, nbs_conversion_master_uid) VALUES(?,?,?,?,?)";
	private String INSERT_INTO_PROCESS_MASTER ="INSERT INTO  NBS_conversion_master (act_uid, end_time, start_time, process_type_ind, process_message_txt, status_cd, NBS_conversion_condition_uid ) VALUES (?,?,?,?,?,?,?)";

	private final String SELECT_NBS_CONVERSION_MASTER_SQL = "select process_type_ind \"processTypeInd\", process_message_txt \"processMessageTxt\", status_cd \"statusCd\", start_time \"startTime\", end_time \"endTime\" from NBS_conversion_master where nbs_conversion_condition_uid in (select nbs_conversion_condition_uid from nbs_conversion_condition where condition_cd=? and NBS_Conversion_Page_Mgmt_uid= ?) order by nbs_conversion_master_uid desc";
	
	//private final String SELECT_NBS_CONVERSION_MASTER_ORACLE = "select process_type_ind \"processTypeInd\", process_message_txt \"processMessageTxt\", status_cd \"statusCd\", start_time \"startTime\", end_time \"endTime\" from NBS_conversion_master where nbs_conversion_condition_uid in (select nbs_conversion_condition_uid from nbs_conversion_condition where condition_cd=? and ROWNUM =1 order by add_time desc) order by start_time desc";
	
	public void insertLog(Collection<Object> coll){
		try{
			if(coll!=null){
				Iterator<Object> it = coll.iterator();
				while(it.hasNext()){
					NBSConversionErrorDT nBSConversionErrorDT= (NBSConversionErrorDT)it.next();
					insertNBSConversionErrorDT(nBSConversionErrorDT);
				}
			}
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	public  void insertNBSConversionErrorDT (NBSConversionErrorDT nBSConversionErrorDT)throws  NEDSSSystemException
	{
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		int resultCount = 0;
		logger.debug("INSERT_INTO_LOG="+INSERT_INTO_LOG);
		try{
			dbConnection = getConnection();
		}catch(NEDSSSystemException nsex){
			logger.fatal("SQLException while obtaining database connection for insertNBSConversionErrorDT ", nsex);
			throw new NEDSSSystemException(nsex.toString());
		}
		try{
			preparedStmt = dbConnection.prepareStatement(INSERT_INTO_LOG);
			int i = 1;
			preparedStmt.setString(i++, nBSConversionErrorDT.getErrorCd()); //1
			String trimmedString = trimToTwoKLength(nBSConversionErrorDT.getErrorMessageTxt());
			preparedStmt.setString(i++, trimmedString); //2
			if(nBSConversionErrorDT.getNbsConversionMappingUid()!=null)
				preparedStmt.setLong(i++, nBSConversionErrorDT.getNbsConversionMappingUid().longValue());//3
			else
				preparedStmt.setLong(i++,0);
			if(nBSConversionErrorDT.getConditionCdGroupId()!=null)
				preparedStmt.setLong(i++, nBSConversionErrorDT.getConditionCdGroupId().longValue());//4
			else
				preparedStmt.setLong(i++,0);//4
			preparedStmt.setLong(i++, nBSConversionErrorDT.getNbsConversionMasterUid().longValue());//5
			resultCount = preparedStmt.executeUpdate();
			logger.debug("resultCount in insertNBSConversionErrorDT is " + resultCount);
		}
		catch(SQLException sqlex)
		{
			logger.fatal("SQLException while inserting " +
					"NBSConversionErrorDT into NBS_conversion_error:"+nBSConversionErrorDT.toString(), sqlex);
			throw new NEDSSDAOSysException( sqlex.toString(), sqlex );
		}
		catch(Exception ex)
		{
			logger.fatal("Error while inserting into NBS_conversion_error, NBSConversionErrorDT="+ nBSConversionErrorDT.toString(), ex);
			throw new NEDSSSystemException(ex.toString(), ex);
		}
		finally
		{
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}

	}//end of insert method

	public void insertNBSConversionMasterDT(NBSConversionMasterDT conversionMasterDT) {
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		PreparedStatement preparedStmt2 =null;
		ResultSet  rs = null;
		int resultCount = 0;
		logger.debug("INSERT_INTO_PROCESS_MASTER="+INSERT_INTO_PROCESS_MASTER);
		dbConnection = getConnection();
		try{
			preparedStmt = dbConnection.prepareStatement(INSERT_INTO_PROCESS_MASTER);
			int i = 1;
			if(conversionMasterDT.getActUid()!=null)
				preparedStmt.setLong(i++, conversionMasterDT.getActUid().longValue());//1
			else
				preparedStmt.setNull(i++, Types.BIGINT);//1
			preparedStmt.setTimestamp(i++, conversionMasterDT.getEndTime( ));//2
			preparedStmt.setTimestamp(i++, conversionMasterDT.getStartTime( ));//3
			preparedStmt.setString(i++, conversionMasterDT.getProcessTypeInd());//4
			String trimmedString = null;
			if(conversionMasterDT.getProcessMessageTxt()!=null)
				trimmedString=trimToMaxLength(conversionMasterDT.getProcessMessageTxt(), 250);
			preparedStmt.setString(i++, trimmedString);//5
			preparedStmt.setString(i++, conversionMasterDT.getStatusCd());//6
			if(conversionMasterDT.getNbsConversionConditionUid()!=null)
				preparedStmt.setLong(i++, conversionMasterDT.getNbsConversionConditionUid());//7
			else
				preparedStmt.setNull(i++, Types.BIGINT);//7
			
			resultCount = preparedStmt.executeUpdate();
			
			preparedStmt2 = dbConnection.prepareStatement("SELECT MAX(nbs_conversion_master_uid) from nbs_conversion_master");
			Long  nbsConversionMasterUid = null;
			rs = preparedStmt2.executeQuery();
			if (rs.next()) {
				logger.debug("nbs_conversion_master_uid = " + rs.getLong(1));
				nbsConversionMasterUid=new Long( rs.getLong(1));
			}
			logger.debug("resultCount in insertNBSConversionMasterDT is " + resultCount);
			
			if(conversionMasterDT.getNBSConversionErrorDTCollection()!=null && conversionMasterDT.getNBSConversionErrorDTCollection().size()>0){
				Iterator<Object> it = conversionMasterDT.getNBSConversionErrorDTCollection().iterator();
				while(it.hasNext()){
					NBSConversionErrorDT nBSConversionErrorDT = (NBSConversionErrorDT)it.next();
					nBSConversionErrorDT.setNbsConversionMasterUid(nbsConversionMasterUid);
					insertNBSConversionErrorDT(nBSConversionErrorDT);
					
				}
			}
		}
		catch(SQLException sqlex)
		{
			logger.fatal("SQLException while inserting " +
					"NBSConversionErrorDT into NBS_conversion_master:"+conversionMasterDT.toString(), sqlex);
			throw new NEDSSDAOSysException( sqlex.toString(), sqlex );
		}
		catch(Exception ex)
		{
			logger.fatal("Error while inserting into NBS_conversion_master, NBSConversionErrorDT="+ conversionMasterDT.toString(), ex);
			throw new NEDSSSystemException(ex.toString(), ex);
		}
		finally
		{
			closeResultSet(rs);
			closeStatement(preparedStmt);
			closeStatement(preparedStmt2);
			releaseConnection(dbConnection);
		}

	}
	
	
	
	public static String trimToTwoKLength(String s){
		if(s!=null && s.length()>4000){
			return s.substring(0, 3900);
		}
		else
			return s;
	}
	

	public static String trimToMaxLength(String s, int maxlength){
		if(s!=null && s.length()>maxlength){
			return s.substring(0, (maxlength-1));
		}
		else
			return s;
	}
	
	
	public ArrayList<Object> getNBSConversionMasterLogByCondition(String conditionCd, Long nbsConversionPageMgmtUid) throws NEDSSDAOSysException, NEDSSSystemException {
		ArrayList<Object>  nbsConvMasterLogList = new ArrayList<Object> ();
		
		NBSConversionMasterDT nbsConversionMasterDT = new NBSConversionMasterDT();
		try{
			logger.debug("conditionCd: "+conditionCd);
			nbsConvMasterLogList.add(conditionCd);
			nbsConvMasterLogList.add(nbsConversionPageMgmtUid);
			//String databaseType = PropertyUtil.getInstance().getDatabaseServerType();
			String query="";
			
			/*if(NEDSSConstants.ORACLE_ID.equalsIgnoreCase(databaseType)){
				query = SELECT_NBS_CONVERSION_MASTER_ORACLE;
			}else{*/
				query = SELECT_NBS_CONVERSION_MASTER_SQL;
			//}
			
			nbsConvMasterLogList = (ArrayList<Object>) preparedStmtMethod(nbsConversionMasterDT, nbsConvMasterLogList, query, NEDSSConstants.SELECT);
			logger.debug("getNBSConversionMasterLogByCondition - nbsConvMasterLogList size: "+nbsConvMasterLogList.size());
		}catch(NEDSSDAOSysException daoSysEx){
			logger.fatal("NEDSSDAOSysException getNBSConversionMasterLogByCondition for conditionCd:"+conditionCd+" Exception: "+daoSysEx.getMessage(), daoSysEx);
			throw new NEDSSDAOSysException(daoSysEx.toString());
		}catch(NEDSSSystemException sysEx){
			logger.fatal("NEDSSSystemException getNBSConversionMasterLogByCondition for conditionCd:"+conditionCd+" Exception: "+sysEx.getMessage(), sysEx);
			throw new NEDSSSystemException(sysEx.toString());
		}catch(Exception ex){
			logger.fatal("Exception getNBSConversionMasterLogByCondition for conditionCd:"+conditionCd+" Exception: "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return nbsConvMasterLogList;
	}
}
