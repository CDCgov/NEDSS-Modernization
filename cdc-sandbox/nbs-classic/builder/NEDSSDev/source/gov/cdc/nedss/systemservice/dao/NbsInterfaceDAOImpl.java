package gov.cdc.nedss.systemservice.dao;

import gov.cdc.nedss.act.sqlscript.WumSqlQuery;
import gov.cdc.nedss.exception.NEDSSDAOAppException;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.dt.NbsInterfaceDT;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dt.EdxLabInformationDT;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.labutil.EdxELRConstants;
import gov.cdc.nedss.systemservice.exception.ResultSetUtilsException;
import gov.cdc.nedss.systemservice.genericXMLParser.MsgXMLMappingDT;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.ResultSetUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * Code updated to get specific row from NBS Interface uid
 * EdxPHCRDocumentEJB code in updated to reflect the Performance related changes to process thousands of ELRs per hour 
 * Code update again on 7/26/2020 for NBS release 5.4.7/6.0.7 for parallel process of ELRs
 @author Pradeep.Sharma
 @updatedRelease: 5.4.7/6.0.7
 *@updatedBy: Pradeep Sharma
 */
public class NbsInterfaceDAOImpl  extends DAOBase{
	static final LogUtils logger = new LogUtils(NbsInterfaceDAOImpl.class.getName());
	private static PropertyUtil propertyUtil= PropertyUtil.getInstance();
	public Collection<Object> getSQLUnprocessedInterfaceUIDCollection(String docTypeCd){
		String SELECT_QUEUED_CASES = "SELECT nbs_interface_uid \"nbsInterfaceUid\" "
				+" FROM NBS_interface" 
				+" WHERE record_status_cd in ('QUEUED','"+NEDSSConstants.ORIG_XML_QUEUED+"') and  imp_exp_Ind_cd='I' and doc_type_cd='"+docTypeCd+"'";
		NbsInterfaceDT nbsInterfaceDT  = new NbsInterfaceDT();
		ArrayList<Object>  unprocessedInterfaceUIDCollection = new ArrayList<Object> ();

		//unprocessedInterfaceUIDCollection.add(docTypeCd);
		try
		{
			unprocessedInterfaceUIDCollection = (ArrayList<Object> )preparedStmtMethod(nbsInterfaceDT, unprocessedInterfaceUIDCollection, SELECT_QUEUED_CASES, NEDSSConstants.SELECT,NEDSSConstants.MSGOUT);
		}
		catch (Exception ex) {
			logger.fatal("NbsInterfaceDAOImpl.insertNBSInterface:  Exception in get queuedCollection:  ERROR = " + ex.getMessage() ,ex);
			throw new NEDSSSystemException(ex.toString(), ex);
		}
		return unprocessedInterfaceUIDCollection;
	}
	@SuppressWarnings("unchecked")
	public NbsInterfaceDT getInterfaceDTByUID(Long uid){
		NbsInterfaceDT nbsInterfaceDT  = new NbsInterfaceDT();
		try{
			String SELECT_QUEUED_CASES = 
					"SELECT " 											+
							"	nbs_interface_uid 	\"nbsInterfaceUid\", " 		+
							"	payload 			\"xmlPayLoadContent\", " 	+
							"	imp_exp_ind_cd 		\"impExpIndCd\", " 			+
							"	record_status_cd 	\"recordStatusCd\", " 		+
							"	record_status_time 	\"recordStatusTime\", " 	+
							"	add_time 			\"addTime\", " 				+
							"	system_nm 			\"systemNm\", "				+
							" 	doc_type_cd			\"docTypeCd\", "			+
							//" 	cda_payload			\"cdaPayload\" "		+
							" 	original_payload	\"originalPayload\", "		+
							" 	original_doc_type_cd \"originalDocTypeCd\" "	+
							" FROM " 											+
							"	NBS_interface " 								+	 
							" WHERE " 											+
							"	nbs_interface_uid 	="+uid;


			ArrayList<Object>  unprocessedInterfaceCollection = new ArrayList<Object> ();
			//		unprocessedInterfaceCollection.add(uid);
			try
			{
				unprocessedInterfaceCollection = (ArrayList<Object> )preparedStmtMethod(nbsInterfaceDT, unprocessedInterfaceCollection, SELECT_QUEUED_CASES, NEDSSConstants.SELECT,NEDSSConstants.MSGOUT);
			}
			catch (Exception ex) {
				logger.fatal("NbsInterfaceDAOImpl.insertNBSInterface:  Exception in get queuedCollection:  ERROR = " + ex.getMessage(), ex);
				throw new NEDSSSystemException(ex.toString(), ex);
			}

			Iterator it = unprocessedInterfaceCollection.iterator();

			while(it.hasNext()){
				nbsInterfaceDT = (NbsInterfaceDT)it.next();
			}
		}catch(Exception ex){
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return nbsInterfaceDT;
	}

	@SuppressWarnings("unchecked")
	public Collection getUnprocessedLabsCollection(){
		String SELECT_QUEUED_CASES = "SELECT nbs_interface_uid \"nbsInterfaceUid\", "
				+" FROM NBS_interface" 
				+" WHERE record_status_cd='QUEUED' and  imp_exp_Ind_cd='I'";
			NbsInterfaceDT nbsInterfaceDT  = new NbsInterfaceDT();
			ArrayList<Object>  unprocessedInterfaceUIDCollection = new ArrayList<Object> ();
			try
			{
				unprocessedInterfaceUIDCollection = (ArrayList<Object> )preparedStmtMethod(nbsInterfaceDT, unprocessedInterfaceUIDCollection, SELECT_QUEUED_CASES, NEDSSConstants.SELECT,NEDSSConstants.MSGOUT);
			}
			catch (Exception ex) {
				logger.fatal("NbsInterfaceDAOImpl.insertNBSInterface:  Exception in get queuedCollection:  ERROR = " + ex.getMessage(), ex);
				throw new NEDSSSystemException(ex.toString(), ex);
			}
			return unprocessedInterfaceUIDCollection;
	}


	public Long insertNBSInterface(NbsInterfaceDT dt)throws NEDSSDAOSysException, NEDSSDAOAppException, NEDSSSystemException	
	{
		Long interfaceUid = null;
		try{
			interfaceUid =  insertNBSInterfaceSQL(dt);
		}catch(Exception e){
			logger.fatal("Exception  Error inserting record for NBS_interface = "+e.getMessage(), e);
			throw new NEDSSSystemException("Error inserting record for NBS_interface", e);

		}
		return interfaceUid;
	}
	private Long insertNBSInterfaceSQL(NbsInterfaceDT dt) throws NEDSSDAOSysException, NEDSSDAOAppException, NEDSSSystemException{

		Connection dbConnection = null;
		PreparedStatement pStmt = null;
		ResultSet resultSet = null;
		int resultCount = 0;
		int i = 1;
		String sql="";
		Long interfaceUid = null;
		ResultSet rs = null;
		sql = WumSqlQuery.INSERT_NBS_INTERFACE_SQL;

		try
		{

			dbConnection = getConnection(NEDSSConstants.MSGOUT);


			pStmt = dbConnection.prepareStatement(sql);   

			pStmt.setString(i++, dt.getXmlPayLoadContent());
			pStmt.setString(i++, dt.getImpExpIndCd());
			pStmt.setString(i++, dt.getRecordStatusCd());
			pStmt.setTimestamp(i++, dt.getRecordStatusTime());
			pStmt.setTimestamp(i++, dt.getAddTime());
			pStmt.setString(i++, dt.getSystemNm());
			pStmt.setString(i++, dt.getDocTypeCd());
			resultCount = pStmt.executeUpdate();
			if (resultCount != 1)
			{
				throw new NEDSSSystemException("NbsInterfaceDAOImpl.insertNBSInterface:  none or more than one records inserted at a time, resultCount = " +
						resultCount);
			}
			//For SQL, get generated key and return;

			try {
				rs = pStmt.getGeneratedKeys();

				if (rs.next()){
					// return NBS_interface.nbs_interface_uid
					interfaceUid = new Long(rs.getLong(1));
					rs.close();
					return interfaceUid;
				}
				else {
					rs.close();
					throw new NEDSSDAOAppException("NbsInterfaceDAOImpl.insertNBSInterface:  failed to retrieve nbs_interface_uid as a generated key."); 
				}
			} catch (Exception e) {
				logger.fatal("Exception  NbsInterfaceDAOImpl.insertNBSInterface:  failed to retrieve nbs_interface_uid as a generated key. "+e.getMessage(), e);
				throw new NEDSSDAOAppException("NbsInterfaceDAOImpl.insertNBSInterface:  failed to retrieve nbs_interface_uid as a generated key.", e); 
			}	

		}	
		catch(SQLException sqlex)
		{
			String errorMsg = "NbsInterfaceDAOImpl.insertNBSInterface:  SQLException while inserting BLOB into NBS_Interface:" + 
					dt.toString() + "\n";
			logger.fatal(errorMsg, sqlex);
			throw new NEDSSDAOSysException( sqlex.toString(), sqlex );
		}
		catch(Exception ex)
		{
			String errorMsg = "NbsInterfaceDAOImpl.insertNBSInterface:  SQLException while inserting BLOB into NBS_Interface:" + 
					dt.toString() + "\n";
			logger.fatal(errorMsg, ex);
			throw new NEDSSSystemException(errorMsg + ex.toString(), ex);
		}			

		finally{
			closeResultSet(rs);
			closeResultSet(resultSet);
			closeStatement(pStmt);
			releaseConnection(dbConnection);
		}



	}

	public Collection<Object> retrieveNBSInterface()throws NEDSSSystemException{

		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		ResultSetMetaData resultSetMetaData = null;
		ResultSetUtils resultSetUtils = new ResultSetUtils();
		String sqlQuery = null;

		try {
			dbConnection = getConnection(NEDSSConstants.MSGOUT);
		} catch (NEDSSSystemException nsex) {
			logger.fatal("NbsInterfaceDAOImpl.retrieveNBSInterface:  SQLException while obtaining database connection "
					+ "for case Notification: retrieveNBSInterface()  in CaseNotificationDAO", nsex);
			throw new NEDSSSystemException(nsex.getMessage(), nsex);
		}
		//  logic to check if code has separate table
		
			sqlQuery = WumSqlQuery.GET_NBS_INTERFACE_FIELDS_SQL;
		try {
			preparedStmt = dbConnection
					.prepareStatement(sqlQuery);
			resultSet = preparedStmt.executeQuery();
			ArrayList<Object>  nBSInterfaceList = new ArrayList<Object> ();
			if(resultSet!=null){
				resultSetMetaData = resultSet.getMetaData();


				nBSInterfaceList = (ArrayList<Object> ) resultSetUtils.mapRsToBeanList(resultSet,
						resultSetMetaData, NbsInterfaceDT.class, nBSInterfaceList);

				logger.debug("NbsInterfaceDAOImpl.retrieveNBSInterface:  Returned Trigger Fields list");
			}
			return nBSInterfaceList;
		} catch (SQLException se) {
			logger.fatal("SQLException NbsInterfaceDAOImpl.retrieveNBSInterface:  SQLException while selecting  NBS Interface Field List: CaseNotificationDAOImpl "+se.getMessage(), se);
			throw new NEDSSDAOSysException("NbsInterfaceDAOImpl.retrieveNBSInterface:  SQLException while selecting "
					+ "NBS Interface Field List: CaseNotificationDAOImpl " +se.getMessage(), se);
		} catch (ResultSetUtilsException reuex) {
			logger.fatal("NbsInterfaceDAOImpl.retrieveNBSInterface:  Error in result set handling while selecting NBS Interface Fields: CaseNotificationDAOImpl.",
					reuex);
			throw new NEDSSDAOSysException(reuex.toString(), reuex);
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
	}


	public void updateNbsInterfaceDT (NbsInterfaceDT nbsInterfaceDT)throws  NEDSSSystemException
	{
		String UPDATE_NBS_INTERFACE="UPDATE NBS_interface SET record_status_cd =?, record_status_time =?, observation_uid =? WHERE nbs_interface_uid=?";
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		int resultCount = 0;
		logger.debug("NbsInterfaceDAOImpl.updateNbsInterfaceDT:  UPDATE_NBS_INTERFACE="+ UPDATE_NBS_INTERFACE);
		try{
			dbConnection = getConnection(NEDSSConstants.MSGOUT);
			preparedStmt = dbConnection.prepareStatement( UPDATE_NBS_INTERFACE);
			int i = 1;
			preparedStmt.setString(i++, nbsInterfaceDT.getRecordStatusCd()); //1
			preparedStmt.setTimestamp(i++, nbsInterfaceDT.getRecordStatusTime()); //2
			if(nbsInterfaceDT.getObservationUid()!=null && nbsInterfaceDT.getObservationUid()>0)
				preparedStmt.setLong(i++, nbsInterfaceDT.getObservationUid().longValue());//3
			else 
				preparedStmt.setNull(i++, Types.BIGINT);//3
			preparedStmt.setLong(i++, nbsInterfaceDT.getNbsInterfaceUid().longValue());//4
			resultCount = preparedStmt.executeUpdate();
			logger.debug("resultCount is " + resultCount);
		}
		catch(SQLException sqlex)
		{
			logger.fatal("NbsInterfaceDAOImpl.updateNbsInterfaceDT:  SQLException while updateNbsInterfaceDT " + "updateNbsInterfaceDT into Nbs_Interface:"+nbsInterfaceDT.toString(), sqlex);
			throw new NEDSSDAOSysException( sqlex.toString(), sqlex );
		}
		catch(Exception ex)
		{
			logger.fatal("NbsInterfaceDAOImpl.updateNbsInterfaceDT:  Error while update into updateNbsInterfaceDT, updateNbsInterfaceDT="+ nbsInterfaceDT.toString(), ex);
			throw new NEDSSSystemException(ex.toString(), ex);
		}
		finally
		{
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}

	}//end of update method


/*	public  int getSQLUnprocessedInterfaceCount(String cd){
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		int numberOfCasesNeedsToBeImported = 0;
		int resultCount = 0;
		ResultSet resultSet=null;
		String SELECT_QUEUED_ELR_CASES = "SELECT count(*) "
				+" FROM NBS_interface" 
				+" WHERE record_status_cd='QUEUED' and  imp_exp_Ind_cd='I'  and doc_type_cd =?";
		logger.debug(" COUNT_PHC_CASES="+ SELECT_QUEUED_ELR_CASES);
		
		try{
			dbConnection = getConnection(NEDSSConstants.MSGOUT);
		}catch(NEDSSSystemException nsex){
			logger.fatal("SQLException while obtaining database connection for getSQLUnprocessedInterfaceCount ", nsex);
			throw new NEDSSSystemException(nsex.toString());
		}
		try{
			preparedStmt = dbConnection.prepareStatement( SELECT_QUEUED_ELR_CASES);
			int i = 1;
			preparedStmt.setString(i++, cd); 

			resultSet = preparedStmt.executeQuery();
			if (resultSet.next())
			{
				numberOfCasesNeedsToBeImported = resultSet.getInt(1);
			}
			logger.debug("resultCount is " +numberOfCasesNeedsToBeImported);
		}
		catch(SQLException sqlex)
		{
			logger.fatal("SQLException while NbsInterfaceDAOImpl.getSQLUnprocessedInterfaceCount: cd :"+cd, sqlex);
			throw new NEDSSDAOSysException( sqlex.toString(), sqlex );
		}
		catch(Exception ex)
		{
			logger.fatal("NbsInterfaceDAOImpl.Exception while getSQLUnprocessedInterfaceCount: cd :"+cd+ ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString(), ex);
		}
		finally
		{
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		return numberOfCasesNeedsToBeImported;
	}//end
*/


	public  ArrayList<Object> getSQLUnprocessedInterfaceList(String cd){
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ArrayList<Object> numberOfCasesNeedsToBeImported = new ArrayList<Object>();
		ResultSet resultSet=null;
		String SELECT_QUEUED_ELR_CASES = "SELECT nbs_interface_uid "
				+" FROM NBS_interface" 
				+" WHERE record_status_cd='QUEUED' and  imp_exp_Ind_cd='I'  and doc_type_cd =? order by nbs_interface_uid ASC";
		logger.debug(" COUNT_PHC_CASES="+ SELECT_QUEUED_ELR_CASES);
		try{
			dbConnection = getConnection(NEDSSConstants.MSGOUT);
		}catch(NEDSSSystemException nsex){
			logger.fatal("SQLException while obtaining database connection for getSQLUnprocessedInterfaceCount ", nsex);
			throw new NEDSSSystemException(nsex.toString());
		}
		//updated code to call for UpdateObservationMatch_SP
		try {
			PropertyUtil propertyUtil =PropertyUtil.getInstance();
			String numberOfYears = propertyUtil.getBackYearsForELRMatching();
			int numberOfNumberInt = Integer.parseInt(numberOfYears);
			this.updateObservationUid(numberOfNumberInt);
		} catch (NEDSSSystemException e) {
			logger.fatal("SQLException while updateObservationUid for NBS_INTERFACE table:-", e);
			throw new NEDSSSystemException(e.toString());
		}


		try{
			preparedStmt = dbConnection.prepareStatement( SELECT_QUEUED_ELR_CASES);
			int i = 1;
			preparedStmt.setString(i++, cd); 

			resultSet = preparedStmt.executeQuery();
			while (resultSet.next()) {
				numberOfCasesNeedsToBeImported.add(resultSet.getLong(1));
		    }
			logger.debug("resultCount is " +numberOfCasesNeedsToBeImported);
		}
		catch(SQLException sqlex)
		{
			logger.fatal("SQLException while NbsInterfaceDAOImpl.getSQLUnprocessedInterfaceCount: cd :"+cd, sqlex);
			throw new NEDSSDAOSysException( sqlex.toString(), sqlex );
		}
		catch(Exception ex)
		{
			logger.fatal("NbsInterfaceDAOImpl.Exception while getSQLUnprocessedInterfaceCount: cd :"+cd+ ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString(), ex);
		}
		finally
		{
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		return numberOfCasesNeedsToBeImported;
	}//end

	
	public  Map<Integer,ArrayList<Object>> getUnProcessedElrDTMap(String cd, int batches){
		Connection dbConnection = null;
		PreparedStatement preparedStmt1 = null;
		PreparedStatement preparedStmt2= null;
		Long totalNumberOfPendingCases=(long) 0;
		Map<Integer,ArrayList<Object>> mappedResult =  new HashMap<Integer,ArrayList<Object>>();
		ArrayList<Object> numberOfCasesNeedsToBeImported = new ArrayList<Object>();
		ArrayList<Object> listOfRerunLabs=new ArrayList<Object>();
		ResultSet resultSet1=null;
		ResultSet resultSet2=null;
		Integer batchNumber=1;
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date1 = new Date();
		logger.debug(dateFormat.format(date1));
		
		
		
		String SELECT_COUNT_QUEUED_ELR_CASES = "select  count(*) from NBS_MSGOUTE..NBS_interface " 
				+" WHERE record_status_cd='QUEUED' and  imp_exp_Ind_cd='I' and doc_type_cd =? ";

		String SELECT_QUEUED_ELR_CASES = "select  filler_order_nbr, lab_clia, specimen_coll_date , add_time, order_test_code, nbs_interface_uid, observation_uid from NBS_MSGOUTE..NBS_interface " 
				+" WHERE record_status_cd='QUEUED' and  imp_exp_Ind_cd='I' and doc_type_cd =? "
				+" group by filler_order_nbr, specimen_coll_date ,lab_clia, add_time, order_test_code, nbs_interface_uid, observation_uid "
				+" order by filler_order_nbr, specimen_coll_date, lab_clia, add_time, order_test_code, nbs_interface_uid, observation_uid ASC ";
		
		logger.debug(" SELECT_COUNT_QUEUED_ELR_CASES QUERY ="+ SELECT_COUNT_QUEUED_ELR_CASES);
		logger.debug(" SELECT_QUEUED_ELR_CASES QUERY ="+ SELECT_QUEUED_ELR_CASES);
		try{
			dbConnection = getConnection(NEDSSConstants.MSGOUT);
		}catch(NEDSSSystemException ex){
			logger.fatal("SQLException while obtaining database connection for getSQLUnprocessedInterfaceCount ", ex);
			throw new NEDSSSystemException(ex.toString());
		}
		
		//updated code to call for UpdateObservationMatch_SP
		try {
			PropertyUtil propertyUtil =PropertyUtil.getInstance();
			String numberOfYears = propertyUtil.getBackYearsForELRMatching();
			int numberOfNumberInt = Integer.parseInt(numberOfYears);
			this.updateObservationUid(numberOfNumberInt);
		} catch (NEDSSSystemException e) {
			logger.fatal("SQLException while updateObservationUid for NBS_INTERFACE table:-", e);
			throw new NEDSSSystemException(e.toString());
		}


		try{
			preparedStmt1 = dbConnection.prepareStatement( SELECT_COUNT_QUEUED_ELR_CASES);
			preparedStmt1.setString(1, cd); 
			resultSet1 = preparedStmt1.executeQuery();
			while (resultSet1.next()) {
				totalNumberOfPendingCases=resultSet1.getLong(1);
			}
			Long pendingCasesePerBatch=totalNumberOfPendingCases/batches;
			
			preparedStmt2 = dbConnection.prepareStatement( SELECT_QUEUED_ELR_CASES);
			preparedStmt2.setString(1, cd); 
			
			resultSet2 = preparedStmt2.executeQuery();
			String priorFillerOrderNbr= "";
			String priorLabClia = "";
			Timestamp priorSpecimenCollDate= java.sql.Timestamp.valueOf("1970-01-31 00:00:00");
			long counter = 0;
			int batchCounter=0;
			
			while (resultSet2.next()) {
				counter = counter+1;
				batchCounter = batchCounter+1;
				String fillerOrderNbr= resultSet2.getString(1);
				String labClia = resultSet2.getString(2);
				Timestamp specimenCollDate= resultSet2.getTimestamp(3);
				Timestamp add_time= resultSet2.getTimestamp(4);
				Long nbsInterfaceUid = resultSet2.getLong(6);
				Long observationUid = resultSet2.getLong(7);
				logger.debug("labClia: "+ labClia+ "\nfillerOrderNbr:"+ fillerOrderNbr+"\nspecimenCollDate"+ specimenCollDate+"\nadd_time:"+add_time
						+"\nnbsInterfaceUid:"+ nbsInterfaceUid+"\nobservationUid:"+observationUid);
				if(observationUid!=null && observationUid>0) {
					listOfRerunLabs.add(nbsInterfaceUid);
				}else{
					if(   (fillerOrderNbr !=null && labClia!=null && priorSpecimenCollDate!=null)
							&&(fillerOrderNbr.equalsIgnoreCase( priorFillerOrderNbr)) //same lab updated!!!
							&& (labClia.equalsIgnoreCase(priorLabClia))
							&& (specimenCollDate.equals(priorSpecimenCollDate))) {
						numberOfCasesNeedsToBeImported.add(nbsInterfaceUid);
	
					}else {// new lab 
						priorFillerOrderNbr =fillerOrderNbr;
						priorLabClia=labClia;
						priorSpecimenCollDate=specimenCollDate;
						if(batchCounter>pendingCasesePerBatch) {
							numberOfCasesNeedsToBeImported.add(nbsInterfaceUid);
							mappedResult.put(batchNumber++, numberOfCasesNeedsToBeImported);
							numberOfCasesNeedsToBeImported= new ArrayList<Object>();
							batchCounter = 0;
						}else {
							numberOfCasesNeedsToBeImported.add(nbsInterfaceUid);
						}
					}
					if(totalNumberOfPendingCases.intValue() == counter) {
						mappedResult.put(batchNumber++, numberOfCasesNeedsToBeImported);
					}
				}
		    }
			mappedResult.put(EdxELRConstants.UPDATED_LAT_LIST, listOfRerunLabs);
			logger.debug("resultCount is " +numberOfCasesNeedsToBeImported);
			Date date2 = new Date();
			
			logger.debug("Initial time to get the unprocessed ELR's:"+dateFormat.format(date1));
			logger.debug("Final time to get the unprocessed ELR's:"+dateFormat.format(date2));
			
		}
		catch(SQLException sqlex)
		{
			logger.fatal("SQLException while NbsInterfaceDAOImpl.getSQLUnprocessedInterfaceCount: cd :"+cd, sqlex);
			throw new NEDSSDAOSysException( sqlex.toString(), sqlex );
		}
		catch(Exception ex)
		{
			logger.fatal("NbsInterfaceDAOImpl.Exception while getSQLUnprocessedInterfaceCount: cd :"+cd+ ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString(), ex);
		}
		finally
		{
			closeResultSet(resultSet1);
			closeStatement(preparedStmt1);
			closeResultSet(resultSet2);
			closeStatement(preparedStmt2);
			releaseConnection(dbConnection);
		}
		return mappedResult;
	}//end


	public NbsInterfaceDT getUnprocessedInterfaceDT( String docTypeCd){
		String SELECT_QUEUED_CASES = 
				"	nbs_interface_uid 	\"nbsInterfaceUid\", " 		+
						"	payload 			\"xmlPayLoadContent\", " 	+
						"	imp_exp_ind_cd 		\"impExpIndCd\", " 			+
						"	record_status_cd 	\"recordStatusCd\", " 		+
						"	record_status_time 	\"recordStatusTime\", " 	+
						"	add_time 			\"addTime\", " 				+
						"	system_nm 			\"systemNm\", "				+
						" 	doc_type_cd			\"docTypeCd\" "				+
						" FROM " 											+
						"	NBS_interface " 								+	 
						" WHERE " 											+
						"	record_status_cd	='QUEUED' " 				+
						"AND  " 											+
						"	imp_exp_Ind_cd		='I' ";

		String query=null;
		String SELECT_FROM_PHC_TABLE_SQL = "Select TOP 1  "+SELECT_QUEUED_CASES+ " and doc_type_cd=? ";
		String SELECT_FROM_PHC_TABLE_ORACLE = "Select  "+SELECT_QUEUED_CASES+ "  and doc_type_cd=?  and  ROWNUM <= 1 ";
		PropertyUtil propertyUtil = PropertyUtil.getInstance();
		query=SELECT_FROM_PHC_TABLE_SQL;
		NbsInterfaceDT nbsInterfaceDT  = new NbsInterfaceDT();
		ArrayList<Object>  unprocessedInterfaceCollection = new ArrayList<Object> ();
		unprocessedInterfaceCollection.add(docTypeCd);
		try
		{
			unprocessedInterfaceCollection = (ArrayList<Object> )preparedStmtMethod(nbsInterfaceDT, unprocessedInterfaceCollection, query, NEDSSConstants.SELECT,NEDSSConstants.MSGOUT);
		}
		catch (Exception ex) {
			logger.fatal("NbsInterfaceDAOImpl.getUnprocessedInterfaceDT:  Exception :  ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString(), ex);
		}

		Iterator it = unprocessedInterfaceCollection.iterator();

		while(it.hasNext()){
			nbsInterfaceDT = (NbsInterfaceDT)it.next();
		}
		return nbsInterfaceDT;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<MsgXMLMappingDT> getOriginalDocumentXMLMapping(
			String origicalDocumentType) {

		MsgXMLMappingDT mappingDT = new MsgXMLMappingDT();
		ArrayList<Object> parameters = new ArrayList<Object>();
		parameters.add(origicalDocumentType);
		// Get the information from the XML_Mapping_table
		String xmlMappingTableQuery = "Select xml_path \"xmlPath\", xml_tag \"xmlTag\", translation_table_nm \"translationTableNm\","
				+ " question_identifier \"questionIdentifier\", QUES_CODE_SYSTEM_CD \"quesCodeSystemCd\", QUES_CODE_SYSTEM_DESC_TXT \"quesCodeSystemDescTxt\", QUES_DISPLAY_TXT \"quesDisplayTxt\", "
				+ " repeat_group_seq_nbr \"repeatGroupSeqNbr\", question_data_type \"questionDataType\", column_nm \"columnNm\", part_type_cd \"partTypeCd\""
				+ " from MSG_XML_mapping where doc_type_cd =? order by TRANSLATION_TABLE_NM desc";

		ArrayList<MsgXMLMappingDT> eICRMappingTable = (ArrayList<MsgXMLMappingDT>) preparedStmtMethod(
				mappingDT, parameters, xmlMappingTableQuery, NEDSSConstants.SELECT,
				NEDSSConstants.MSGOUT);
		return eICRMappingTable;
	}
	
	public NbsInterfaceDT getSpecificUnprocessedInterfaceDT( String docTypeCd){
		String SELECT_QUEUED_CASES = 
				"	nbs_interface_uid 	\"nbsInterfaceUid\", " 		+
						"	payload 			\"xmlPayLoadContent\", " 	+
						"	imp_exp_ind_cd 		\"impExpIndCd\", " 			+
						"	record_status_cd 	\"recordStatusCd\", " 		+
						"	record_status_time 	\"recordStatusTime\", " 	+
						"	add_time 			\"addTime\", " 				+
						"	system_nm 			\"systemNm\", "				+
						" 	doc_type_cd			\"docTypeCd\" "				+
						" FROM " 											+
						"	NBS_interface  WTIH (NOLOCK) " 								+	 
						" WHERE " 											+
						"	record_status_cd	='QUEUED' " 				+
						"AND  " 											+
						"	imp_exp_Ind_cd		='I' ";

		String query=null;
		String SELECT_FROM_PHC_TABLE_SQL = "Select TOP 1  "+SELECT_QUEUED_CASES+ " and doc_type_cd=? ";
		PropertyUtil propertyUtil = PropertyUtil.getInstance();
		query=SELECT_FROM_PHC_TABLE_SQL;
		NbsInterfaceDT nbsInterfaceDT  = new NbsInterfaceDT();
		ArrayList<Object>  unprocessedInterfaceCollection = new ArrayList<Object> ();
		unprocessedInterfaceCollection.add(docTypeCd);
		try
		{
			unprocessedInterfaceCollection = (ArrayList<Object> )preparedStmtMethod(nbsInterfaceDT, unprocessedInterfaceCollection, query, NEDSSConstants.SELECT,NEDSSConstants.MSGOUT);
		}
		catch (Exception ex) {
			logger.fatal("NbsInterfaceDAOImpl.getUnprocessedInterfaceDT:  Exception :  ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString(), ex);
		}

		Iterator it = unprocessedInterfaceCollection.iterator();

		while(it.hasNext()){
			nbsInterfaceDT = (NbsInterfaceDT)it.next();
		}
		return nbsInterfaceDT;
	}

	public NbsInterfaceDT selectNbsInterfaceDT(long nbsInterfaceUID) throws NEDSSSystemException {
		NbsInterfaceDT nbsInterfaceDT = new NbsInterfaceDT();
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		ResultSetMetaData resultSetMetaData = null;
		ResultSetUtils resultSetUtils = new ResultSetUtils();
		String SELECT_QUEUED_CASE = 
				"SELECT " 											+
						"	nbs_interface_uid 	\"nbsInterfaceUid\", " 		+
						"	payload 			\"xmlPayLoadContent\", " 	+
						"	imp_exp_ind_cd 		\"impExpIndCd\", " 			+
						"	record_status_cd 	\"recordStatusCd\", " 		+
						"	record_status_time 	\"recordStatusTime\", " 	+
						"	add_time 			\"addTime\", " 				+
						"	system_nm 			\"systemNm\", "				+
						" 	doc_type_cd			\"docTypeCd\", "			+
						//" 	cda_payload			\"cdaPayload\" "		+
						" 	original_payload	\"originalPayload\", "		+
						" 	original_doc_type_cd \"originalDocTypeCd\", "	+
						" 	observation_uid \"observationUid\" "			+
						" FROM " 											+
						"	NBS_MSGOUTE..NBS_interface  WTIH (NOLOCK) " 								+	 
						" WHERE " 											+
						"	record_status_cd 	=\'QUEUED\' " 				+
						" and	nbs_interface_uid 	=?";

		/**
		 * Selects a NbsInterfact from NBS_INTERFACE table
		 */
		try {
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(SELECT_QUEUED_CASE);
			preparedStmt.setLong(1, nbsInterfaceUID);
			resultSet = preparedStmt.executeQuery();
			resultSetMetaData = resultSet.getMetaData();
			nbsInterfaceDT = (NbsInterfaceDT) resultSetUtils.mapRsToBean(resultSet, resultSetMetaData,
					nbsInterfaceDT.getClass());
			nbsInterfaceDT.setItNew(false);
			nbsInterfaceDT.setItDirty(false);
			nbsInterfaceDT.setItDelete(false);
		} catch (SQLException sqlex) {
			logger.fatal("SQLException while selecting " + "a NBS_INTERFACE DT ; uid = " + nbsInterfaceUID, sqlex);
			throw new NEDSSDAOSysException(sqlex.getMessage());
		} catch (Exception ex) {
			logger.fatal("Exception while selecting " + "a NBS_INTERFACE DT; uid = " + nbsInterfaceUID, ex);
			throw new NEDSSDAOSysException(ex.getMessage());
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		logger.debug("return a NBS_INTERFACE DT object");
		return nbsInterfaceDT;
	}// end of selecting a selectNbsInterfaceDT

	/**
	 * 
	 * @param numberOfYears
	 * @throws NEDSSSystemException
	 */
	public void updateObservationUid(int numberOfYears) throws NEDSSSystemException {
		try {
			ArrayList<Object> inArrayList = new ArrayList<Object>();
			ArrayList<Object> outArrayList = new ArrayList<Object>();
			ArrayList<Object> arrayList = new ArrayList<Object>();
			inArrayList.add(numberOfYears);
			String sQuery = "{call UpdateObservationMatch_SP(?)}";
			arrayList = (ArrayList<Object>) callStoredProcedureMethod(sQuery, inArrayList, outArrayList, NEDSSConstants.MSGOUT);
		} catch (Exception ex) {
			logger.fatal("An Exception occurred when matching NBS_INTERFACE table with observation:", ex);
			throw new NEDSSSystemException(ex.getMessage());
		}
	}
 
	/**
	 * 
	 * @param EdxLabInformationDT
	 * @throws NEDSSSystemException
	 */
	public void updateNBSInterfaceRecord(EdxLabInformationDT edxLabInformationDT) throws NEDSSSystemException {
		try {
			long nbsInterfaceUid=edxLabInformationDT.getNbsInterfaceUid();
			Timestamp aEffectiveFromTime=edxLabInformationDT.getRootObservationVO().getTheObservationDT().getEffectiveFromTime();
			ArrayList<Object> inArrayList = new ArrayList<Object>();
			ArrayList<Object> outArrayList = new ArrayList<Object>();
			ArrayList<Object> arrayList = new ArrayList<Object>();
			inArrayList.add(nbsInterfaceUid);
			inArrayList.add(aEffectiveFromTime);
			String sQuery = "{call UpdateSpecimenCollDate_SP(?,?)}";
			arrayList = (ArrayList<Object>) callStoredProcedureMethod(sQuery, inArrayList, outArrayList, NEDSSConstants.MSGOUT);
		} catch (Exception ex) {
			logger.fatal("An Exception occurred when matching NBS_INTERFACE table with observation:", ex);
			throw new NEDSSSystemException(ex.getMessage());
		}
	}

	/**
	 * 
	 * @param nbsInterfaceDT
	 * @throws NEDSSSystemException
	 */
	/*public void updateNBSInterfaceRecord (EdxLabInformationDT edxLabInformationDT)throws  NEDSSSystemException
	{
		long nbsInterfaceUid=edxLabInformationDT.getNbsInterfaceUid();
		Timestamp aEffectiveFromTime=edxLabInformationDT.getSpecimenCollectionTime();
		String UPDATE_NBS_INTERFACE_SPEC_COLL="UPDATE NBS_MSGOUTE.dbo.NBS_interface set specimen_coll_date=? where nbs_interface_uid=?";
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		int resultCount = 0;
		logger.debug("NbsInterfaceDAOImpl.updateNbsInterfaceDTSpecimenCollDate:  UPDATE_NBS_INTERFACE_SPEC_COLL="+ UPDATE_NBS_INTERFACE_SPEC_COLL);
		try{
			dbConnection = getConnection(NEDSSConstants.MSGOUT);
			preparedStmt = dbConnection.prepareStatement( UPDATE_NBS_INTERFACE_SPEC_COLL);
			int i = 1;
			preparedStmt.setTimestamp(i++, aEffectiveFromTime); //1
			preparedStmt.setLong(i++, nbsInterfaceUid);//2
			resultCount = preparedStmt.executeUpdate();
			logger.debug("resultCount is " + resultCount);
		}
		catch(SQLException sqlex)
		{
			logger.fatal("NbsInterfaceDAOImpl.updateNbsInterfaceDTSpecimenCollDate:  SQLException while updateNbsInterfaceDT " + "updateNbsInterfaceDT into Nbs_Interface:"+edxLabInformationDT.getNbsInterfaceUid(), sqlex);
			throw new NEDSSDAOSysException( sqlex.toString(), sqlex );
		}
		catch(Exception ex)
		{
			logger.fatal("NbsInterfaceDAOImpl.updateNbsInterfaceDTSpecimenCollDate:  Error while update into updateNbsInterfaceDT, updateNbsInterfaceDT="+ edxLabInformationDT.getNbsInterfaceUid(), ex);
			throw new NEDSSSystemException(ex.toString(), ex);
		}
		finally
		{
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}

	}//end of update method

*/
}
