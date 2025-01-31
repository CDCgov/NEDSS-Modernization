/**
 * Name:		    DAOBase
 * Description:	class that contains a method for a generic prepared statement call
 * Copyright:	Copyright (c) 2002
 * Company: 	    Computer Sciences Corporation
 * @author	    NEDSS Development Team
 * @version	    1.0
 */
package gov.cdc.nedss.util;

import gov.cdc.nedss.exception.NEDSSDAOAppException;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.util.RootDTInterface;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Name: DAOBase Description: class that contains a method for a generic
 * prepared statement call Copyright: Copyright (c) 2002 Company: Computer
 * Sciences Corporation
 * 
 * @author NEDSS Development Team
 * @version 1.0 generic class used to build and run a PreparedStatement
 */
public class DAOBase extends BMPBase {
	public DAOBase() {
	}

	static final LogUtils logger = new LogUtils(DAOBase.class.getName());

	/**
	 * 
	 * @param query
	 *            as String
	 * @return is Long
	 * @throws NEDSSDAOSysException
	 * 
	 *             this method is used to create sequence number. It will get
	 *             Data from table and increment value. It return as Long object
	 */

	public Long getMaxId(String query) throws NEDSSDAOSysException {

		// get a connection
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		Long maxId = null;

		try {
			dbConnection = getConnection();

			// sql statement passed in
			preparedStmt = dbConnection.prepareStatement(query);
			resultSet = preparedStmt.executeQuery();
			long temp = 1;
			if (resultSet.next()) {
				temp = resultSet.getLong(1);

				temp = temp + 1;
				maxId = new Long(temp);

			} else
				maxId = new Long(temp);
		} catch (SQLException sex) {
			logger.fatal("query: "+query);
			logger.fatal("SQLException  ERROR = "+sex.getMessage(), sex);
			throw new NEDSSDAOSysException(sex.toString());
		} catch (Exception ex) {
			logger.fatal("query: "+query);
			logger.fatal("Exception  ERROR = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		logger.debug("in side DAOBase.preparedStmtMethod - count  = "+ maxId);
		return maxId;
	}

	/**
	 * This method loads a PrepareStatement object using the objects in the
	 * ArrayList<Object> (because it is an ordered list) and runs either an
	 * executeQuery method or an executeUpdate depending on the queryType
	 * parameter.
	 * 
	 * If the queryType is NEDSSConstantUtil.SELECT then the executeQuery method
	 * is run, the rootDTInterface passed in is loaded using the
	 * resultSetUtils.mapRsToBean method and a rootDTInterface is returned.
	 * 
	 * If the queryType is not a NEDSSConstantUtil.SELECT it is assumed the
	 * operation is either an insert or an update which requires an
	 * executeUpdate method. The executeUpdate method returns an int that is
	 * converted to a Integer and becomes the return object.
	 * 
	 * @param rootDTInterface
	 * @param arrayList
	 * @param statement
	 * @param queryType
	 * @return Object
	 * @throws NEDSSDAOSysException
	 */
	public Object preparedStmtMethod(RootDTInterface rootDTInterface,
			ArrayList<Object> arrayList, String statement, String queryType)
			throws NEDSSDAOSysException {

		// get a connection
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;

		try {
			dbConnection = getConnection();

			// sql statement passed in
			preparedStmt = dbConnection.prepareStatement(statement);
			// only for debugging purposes
			
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
			StackTraceElement e = stacktrace[2];//maybe this number needs to be corrected
			String methodName = e.getMethodName();
			logger.debug("DAOBase.preparedStmtMethod calling method is :"+methodName);
			
			logger.debug("DAOBase.preparedStmtMethod.queryType is :"
					+ queryType);
			logger.debug("DAOBase.preparedStmtMethod.rootDTInterface is :"
					+ rootDTInterface);

			// array of objects passed in for statement parameters
			if (arrayList != null) {
				Iterator<?> it = arrayList.iterator();
				for (int i = 1; it.hasNext(); i++) {
					Object obj = it.next();
					if (obj == null) {
						// if it is null setNull
						preparedStmt.setNull(i, Types.VARCHAR);
					}
					// if String with "" vlaue set it to null.
					// Struts may be giving blank String instead of null
					// Oracle does it for you, SQL server does not.
					else if (obj.getClass().toString().equals(
							"class java.lang.String")) {
						if (((String) obj).trim().length() == 0)
							preparedStmt.setNull(i, Types.VARCHAR);
						else
							preparedStmt.setObject(i, obj.toString());
					} else {
						// if it is a timestamp treat it special (mainly for
						// oracle)
						String timeStamp = obj.getClass().toString();
						if (obj.getClass().toString().equals(
								"class java.sql.Timestamp")) {
							preparedStmt.setTimestamp(i,
									(java.sql.Timestamp) obj);
						} else {
							// else pass them all as strings driver will convert
							// to proper type
							// preparedStmt.setObject(i, obj.toString());
							String value = obj.toString();
							if (value == null || value.trim().length() == 0)
								preparedStmt.setNull(i, Types.VARCHAR);
							else
								preparedStmt.setObject(i, value);

						}
					}
				}
			}
			// if it is a select statement then you want to use executeQuery to
			// get the resultSet
			if (queryType.equals(NEDSSConstants.SELECT)) {
				resultSet = preparedStmt.executeQuery();

				ResultSetMetaData resultSetMetaData = null;
				resultSetMetaData = resultSet.getMetaData();

				ArrayList<Object> pnList = new ArrayList<Object>();
				ArrayList<Object> reSetList = new ArrayList<Object>();

				ResultSetUtils resultSetUtils = new ResultSetUtils();
				pnList = (ArrayList<Object>) resultSetUtils.mapRsToBeanList(
						resultSet, resultSetMetaData, rootDTInterface
								.getClass(), pnList);

				for (Iterator<Object> anIterator = pnList.iterator(); anIterator
						.hasNext();) {
					RootDTInterface reSetName = (RootDTInterface) anIterator
							.next();
					reSetName.setItNew(false);
					reSetName.setItDirty(false);
					reSetList.add(reSetName);
				}
				// logger.debug("return person name collection");
				return reSetList;
			}
			// else if it is a SELECT COUNT(*) then it needs to return an
			// Integer
			else if (queryType.equals(NEDSSConstants.SELECT_COUNT)) {
				int count = 0;
//				logger
//						.debug("in side DAOBase.preparedStmtMethod - statement  = "
//								+ statement);
				resultSet = preparedStmt.executeQuery();
				if (resultSet.next())
					count = resultSet.getInt(1);
				logger.debug("in side DAOBase.preparedStmtMethod - count  = "
						+ count);
				return new Integer(count);
			}
			// else if it is an insert or update then you want to use
			// executeUpdate to get an int
			else {
				int result = preparedStmt.executeUpdate();
				logger.debug("preparedStmt.executeUpdate() - result = "
						+ result);
				return new Integer(result);
			}

		} catch (SQLException sex) {
			if (logSQLException(statement))
				logger.fatal("SQLException executing: ERROR = " + sex.getMessage(), sex);
			throw new NEDSSDAOSysException(sex.toString());
		} catch (Exception ex) {
			logger.fatal("Exception: executing statement: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		// clean house
		finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
	}

	/**
	 * 
	 * @param rootDTInterface
	 * @param arrayList
	 * @param statement
	 * @param queryType
	 * @param dataSource
	 * @return
	 * @throws NEDSSDAOSysException
	 */
	public Object preparedStmtMethod(RootDTInterface rootDTInterface,
			ArrayList<Object> arrayList, String statement, String queryType,
			String odbcName) throws NEDSSDAOSysException {

		// get a connection
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;

		try {
			dbConnection = getConnection(odbcName);

			// sql statement passed in
			preparedStmt = dbConnection.prepareStatement(statement);
			// only for debugging purposes
			logger.debug("DAOBase.preparedStmtMethod.statement is :"
					+ statement);
			logger.debug("DAOBase.preparedStmtMethod.queryType is :"
					+ queryType);
			logger.debug("DAOBase.preparedStmtMethod.rootDTInterface is :"
					+ rootDTInterface);

			// array of objects passed in for statement parameters
			if (arrayList != null) {
				Iterator<Object> it = arrayList.iterator();
				for (int i = 1; it.hasNext(); i++) {
					Object obj = it.next();
					if (obj == null) {
						// if it is null setNull
						preparedStmt.setNull(i, Types.VARCHAR);
					}
					// if String with "" vlaue set it to null.
					// Struts may be giving blank String instead of null
					// Oracle does it for you, SQL server does not.
					else if (obj.getClass().toString().equals(
							"class java.lang.String")) {
						if (((String) obj).trim().length() == 0)
							preparedStmt.setNull(i, Types.VARCHAR);
						else
							preparedStmt.setObject(i, obj.toString());
					} else {
						// if it is a timestamp treat it special (mainly for
						// oracle)
						String timeStamp = obj.getClass().toString();
						if (obj.getClass().toString().equals(
								"class java.sql.Timestamp")) {
							preparedStmt.setTimestamp(i,
									(java.sql.Timestamp) obj);
						} else {
							// else pass them all as strings driver will convert
							// to proper type
							// preparedStmt.setObject(i, obj.toString());
							String value = obj.toString();
							if (value == null || value.trim().length() == 0)
								preparedStmt.setNull(i, Types.VARCHAR);
							else
								preparedStmt.setObject(i, value);

						}
					}
				}
			}
			// if it is a select statement then you want to use executeQuery to
			// get the resultSet
			if (queryType.equals(NEDSSConstants.SELECT)) {
				resultSet = preparedStmt.executeQuery();

				ResultSetMetaData resultSetMetaData = null;
				resultSetMetaData = resultSet.getMetaData();

				ArrayList<Object> pnList = new ArrayList<Object>();
				ArrayList<Object> reSetList = new ArrayList<Object>();

				ResultSetUtils resultSetUtils = new ResultSetUtils();
				pnList = (ArrayList<Object>) resultSetUtils.mapRsToBeanList(
						resultSet, resultSetMetaData, rootDTInterface
								.getClass(), pnList);

				for (Iterator<Object> anIterator = pnList.iterator(); anIterator
						.hasNext();) {
					RootDTInterface reSetName = (RootDTInterface) anIterator
							.next();
					reSetName.setItNew(false);
					reSetName.setItDirty(false);
					reSetList.add(reSetName);
				}
				// logger.debug("return person name collection");
				return reSetList;
			}
			// else if it is a SELECT COUNT(*) then it needs to return an
			// Integer
			else if (queryType.equals(NEDSSConstants.SELECT_COUNT)) {
				int count = 0;
//				logger
//						.debug("in side DAOBase.preparedStmtMethod - statement  = "
//								+ statement);
				resultSet = preparedStmt.executeQuery();
				if (resultSet.next())
					count = resultSet.getInt(1);
				logger.debug("in side DAOBase.preparedStmtMethod - count  = "
						+ count);
				return new Integer(count);
			}
			// else if it is an insert or update then you want to use
			// executeUpdate to get an int
			else {
				int result = preparedStmt.executeUpdate();
				logger.debug("preparedStmt.executeUpdate() - result = "
						+ result);
				return new Integer(result);
			}

		} catch (SQLException sex) {
			if (logSQLException(statement))
				logger.fatal("SQLException executing: ERROR = " + sex.getMessage(), sex);
			throw new NEDSSDAOSysException(sex.toString());
		} catch (Exception ex) {
			logger.fatal("Exception executing: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		// clean house
		finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
	}

	/**
	 * @param statement
	 * @return
	 */
	private boolean logSQLException(String statement) {
		//logger.debug("DAOBase.logSQLException.statement is :" + statement);

		return (!statement.startsWith("insert into df_sf_metadata_group") && !statement
				.startsWith("insert into bus_obj_df_sf_mdata_group"));
	}

	/**
	 * This method loads a PrepareStatement object using the objects in the
	 * ArrayList<Object> (because it is an ordered list) and runs either an
	 * executeQuery method or an executeUpdate depending on the queryType
	 * parameter.
	 * 
	 * If the queryType is NEDSSConstantUtil.SELECT then the executeQuery method
	 * is run, the rootDTInterface passed in is loaded using the
	 * resultSetUtils.mapRsToBean method and a rootDTInterface is returned.
	 * 
	 * If the queryType is not a NEDSSConstantUtil.SELECT it is assumed the
	 * operation is either an insert or an update which requires an
	 * executeUpdate method. The executeUpdate method returns an int that is
	 * converted to a Integer and becomes the return object.
	 * 
	 * @param rootDTInterface
	 * @param arrayList
	 * @param statement
	 * @param queryType
	 * @return Object
	 * @throws NEDSSDAOSysException
	 */
	public Object preparedStmtMethodForMap(RootDTInterface rootDTInterface,
			ArrayList<Object> arrayList, String statement, String queryType,
			String pk) throws NEDSSDAOSysException {

		// get a connection
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;

		try {
			dbConnection = getConnection();
			// sql statement passed in
			preparedStmt = dbConnection.prepareStatement(statement);
			logger.debug("DAOBase.preparedStmtMethodForMap.statement is :"
					+ statement);
			logger.debug("DAOBase.preparedStmtMethodForMap.queryType is :"
					+ queryType);
			logger
					.debug("DAOBase.preparedStmtMethodForMap.rootDTInterface is :"
							+ rootDTInterface);

			// array of objects passed in for statement parameters
			if (arrayList != null) {
				Iterator<Object> it = arrayList.iterator();
				for (int i = 1; it.hasNext(); i++) {
					Object obj = it.next();
					if (obj == null) {
						// if it is null setNull
						preparedStmt.setNull(i, Types.VARCHAR);
					} else {
						// if it is a timestamp treat it special (mainly for
						// oracle)
						String timeStamp = obj.getClass().toString();
						if (obj.getClass().toString().equals(
								"class java.sql.Timestamp")) {
							preparedStmt.setTimestamp(i,
									(java.sql.Timestamp) obj);
						} else {
							// else pass them all as strings driver will convert
							// to proper type
							preparedStmt.setObject(i, obj.toString());
						}
					}
				}
			}
			// if it is a select statement then you want to use executeQuery to
			// get the resultSet
			if (queryType.equals(NEDSSConstants.SELECT)) {
				resultSet = preparedStmt.executeQuery();

				ResultSetMetaData resultSetMetaData = null;
				resultSetMetaData = resultSet.getMetaData();

				HashMap<Object, Object> pnMap = new HashMap<Object, Object>();
				// HashMap reSetMap = new HashMap<Object,Object>();

				ResultSetUtils resultSetUtils = new ResultSetUtils();
				pnMap = (HashMap<Object, Object>) resultSetUtils
						.mapRsToBeanMap(resultSet, resultSetMetaData,
								rootDTInterface.getClass(), pk, pnMap);
				/*
				 * for(Iterator<Object> anIterator = pnMap.values().iterator();
				 * anIterator.hasNext(); ) { RootDTInterface reSetName =
				 * (RootDTInterface)anIterator.next();
				 * reSetName.setItNew(false); reSetName.setItDirty(false);
				 * reSetList.add(reSetName); }
				 */// logger.debug("return person name collection");
				return pnMap;
			}
			// else if it is a SELECT COUNT(*) then it needs to return an
			// Integer
			else if (queryType.equals(NEDSSConstants.SELECT_COUNT)) {
				int count = 0;
				logger
						.debug("in side DAOBase.preparedStmtMethod - statement  = "
								+ statement);
				resultSet = preparedStmt.executeQuery();
				if (resultSet.next())
					count = resultSet.getInt(1);
				logger.debug("in side DAOBase.preparedStmtMethod - count  = "
						+ count);
				return new Integer(count);
			}
			// else if it is an insert or update then you want to use
			// executeUpdate to get an int
			else {
				int result = preparedStmt.executeUpdate();
				logger.debug("preparedStmt.executeUpdate() - result = "
						+ result);
				return new Integer(result);
			}

		} catch (SQLException sex) {
			logger.fatal("SQLException executing: " + statement + " ERROR = "
					+ sex.getMessage(), sex);
			throw new NEDSSDAOSysException(sex.toString());
		} catch (Exception ex) {
			logger.fatal("Exception  executing: " + statement + " ERROR = "
					+ ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		// clean house
		finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
	}

	/**
	 * @param queryType
	 * @param arrayList
	 *            in inArrayList
	 * @param arrayList
	 *            in outArrayList
	 * @return ArrayList
	 * @throws NEDSSDAOSysException
	 * 
	 *             This is call store procedure and return ArryList object
	 */
	
	public ArrayList<Object> callStoredProcedureMethod(String sQuery,
			ArrayList<Object> inArrayList, ArrayList<Object> outArrayList)
			throws NEDSSDAOSysException {

		return callStoredProcedureMethod(sQuery, inArrayList, outArrayList,
				null);

	}
	
	public ArrayList<Object> callStoredProcedureMethod(String sQuery,
			ArrayList<Object> inArrayList, ArrayList<Object> outArrayList, String datasource)
			throws NEDSSDAOSysException {
		Connection dbConnection = null;
		CallableStatement sProc = null;
		ArrayList<Object> arrayList = new ArrayList<Object>();

		try {
			if(datasource==null)
				dbConnection = getConnection();
			else
				dbConnection = getConnection(datasource);
			logger.debug("DAOBase.callStoredProcedureMethod.sQuery is :"
					+ sQuery);

			sProc = dbConnection.prepareCall(sQuery);
			int tempInt = inArrayList.size();
			int tempOut = outArrayList.size() + inArrayList.size();
			int i = 1;
			if (inArrayList != null) {
				Iterator<Object> it = inArrayList.iterator();
				while (it.hasNext()) {
					Object obj = it.next();
					if (obj != null)
						sProc.setObject(i, obj.toString());
					i++;
				}
			}

			if (outArrayList != null) {
				Iterator<Object> it = outArrayList.iterator();
				while (it.hasNext()) {
					Object obj = it.next();
					if (obj != null)
						sProc.registerOutParameter(i, Integer.parseInt(obj
								.toString()));
					i++;
				}
			}

			sProc.execute();

			while (tempInt < tempOut)
				arrayList.add(sProc.getObject(++tempInt));

		} catch (SQLException sex) {
			logger.fatal("SQLException executing:"+sQuery+"  ERROR = " + sex.getMessage(), sex);
			throw new NEDSSDAOSysException(sex.toString());
		} catch (Exception ex) {
			logger.fatal("Exception  executing:"+sQuery+" ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		// clean house
		finally {
			closeStatement(sProc);
			releaseConnection(dbConnection);
		}
		// System.out.println("\narrayList.size() = " + arrayList.size() +
		// "\n");
		return arrayList;
	}

	public Object callStoredProcedureMethodWithResultSetMap(RootDTInterface rootDTInterface,
			ArrayList<Object> inArrayList, String sQuery, String pk) throws NEDSSDAOSysException {
		Connection dbConnection = null;
		CallableStatement sProc = null;
		ResultSet resultSet = null;

		try {
			dbConnection = getConnection();
			logger.debug("DAOBase.callStoredProcedureMethodWithResultSetMap.sQuery is :" + sQuery);
			sProc = dbConnection.prepareCall(sQuery);
			int i = 1;
			if (inArrayList != null) {
				Iterator<Object> it = inArrayList.iterator();
				while (it.hasNext()) {
					Object obj = it.next();
					if (obj != null)
						sProc.setObject(i, obj.toString());
					i++;
				}
			}
			resultSet = sProc.executeQuery();
			ResultSetMetaData resultSetMetaData = null;
			resultSetMetaData = resultSet.getMetaData();

			HashMap<Object, Object> pnMap = new HashMap<Object, Object>();

			ResultSetUtils resultSetUtils = new ResultSetUtils();
			pnMap = (HashMap<Object, Object>) resultSetUtils.mapRsToBeanMap(resultSet, resultSetMetaData,
					rootDTInterface.getClass(), pk, pnMap);
			return pnMap;

		} catch (SQLException sex) {
			logger.fatal("SQLException executing callStoredProcedureMethodWithResultSetMap: " + sQuery + "  ERROR = " + sex.getMessage(), sex);
			throw new NEDSSDAOSysException(sex.toString());
		} catch (Exception ex) {
			logger.fatal("Exception  executing callStoredProcedureMethodWithResultSetMap: " + sQuery + " ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		// clean house
		finally {
			closeResultSet(resultSet);
			closeStatement(sProc);
			releaseConnection(dbConnection);
		}

	}	
	
	public ArrayList<Object> callStoredProcedureMethodWithResultSet(RootDTInterface rootDTInterface, ArrayList<Object> inArrayList,
			String sQuery) throws NEDSSDAOSysException, NEDSSSystemException {
		Connection dbConnection = null;
		CallableStatement sProc = null;
		ResultSet resultSet = null;

		try {
			dbConnection = getConnection();
			logger.debug("DAOBase.callStoredProcedureMethodWithResultSet.sQuery is :" + sQuery);
			sProc = dbConnection.prepareCall(sQuery);
			int i = 1;
			if (inArrayList != null) {
				Iterator<Object> it = inArrayList.iterator();
				while (it.hasNext()) {
					Object obj = it.next();
					if (obj != null)
						sProc.setObject(i, obj.toString());
					i++;
				}
			}
			resultSet = sProc.executeQuery();
			ResultSetMetaData resultSetMetaData = null;
			resultSetMetaData = resultSet.getMetaData();

			ArrayList<Object> pnList = new ArrayList<Object>();

			ResultSetUtils resultSetUtils = new ResultSetUtils();

			resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, rootDTInterface.getClass(), pnList);
			return pnList;

		} catch (SQLException sex) {
			logger.fatal("SQLException executing callStoredProcedureMethodWithResultSet: " + sQuery + "  ERROR = "
					+ sex.getMessage(), sex);
			throw new NEDSSDAOSysException(sex.toString());
		} catch (Exception ex) {
			logger.fatal("Exception  executing callStoredProcedureMethodWithResultSet: " + sQuery + " ERROR = "
					+ ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		// clean house
		finally {
			closeResultSet(resultSet);
			closeStatement(sProc);
			releaseConnection(dbConnection);
		}

	}	
	public Object preparedStmtMethodRetUid(RootDTInterface rootDTInterface,
			ArrayList<Object> arrayList, String statement, String queryType)
			throws NEDSSDAOSysException {

		// get a connection
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;

		try {
			dbConnection = getConnection();

			// sql statement passed in
			preparedStmt = dbConnection.prepareStatement(statement);
			// only for debugging purposes
			logger.debug("DAOBase.preparedStmtMethod.statement is :"
					+ statement);
			logger.debug("DAOBase.preparedStmtMethod.queryType is :"
					+ queryType);
			logger.debug("DAOBase.preparedStmtMethod.rootDTInterface is :"
					+ rootDTInterface);

			// array of objects passed in for statement parameters
			if (arrayList != null) {
				Iterator<?> it = arrayList.iterator();
				for (int i = 1; it.hasNext(); i++) {
					Object obj = it.next();
					if (obj == null) {
						// if it is null setNull
						preparedStmt.setNull(i, Types.VARCHAR);
					}
					// if String with "" vlaue set it to null.
					// Struts may be giving blank String instead of null
					// Oracle does it for you, SQL server does not.
					else if (obj.getClass().toString().equals(
							"class java.lang.String")) {
						if (((String) obj).trim().length() == 0)
							preparedStmt.setNull(i, Types.VARCHAR);
						else
							preparedStmt.setObject(i, obj.toString());
					} else {
						// if it is a timestamp treat it special (mainly for
						// oracle)
						String timeStamp = obj.getClass().toString();
						if (obj.getClass().toString().equals(
								"class java.sql.Timestamp")) {
							preparedStmt.setTimestamp(i,
									(java.sql.Timestamp) obj);
						} else {
							// else pass them all as strings driver will convert
							// to proper type
							// preparedStmt.setObject(i, obj.toString());
							String value = obj.toString();
							if (value == null || value.trim().length() == 0)
								preparedStmt.setNull(i, Types.VARCHAR);
							else
								preparedStmt.setObject(i, value);

						}
					}
				}
			}

			// else if it is an insert or update then you want to use
			// executeUpdate to get an int

				int result = preparedStmt.executeUpdate();
				logger.debug("preparedStmt.executeUpdate() - result = "
						+ result);
				  try {
					  resultSet = preparedStmt.getGeneratedKeys();

						if (resultSet.next()) {
							// return CT_contact_attachment.ct_contact_attachment_uid
							Long insertedRecordUid = new Long(resultSet.getLong(1));
							resultSet.close();
							return insertedRecordUid;
						} else {
							resultSet.close();
							throw new NEDSSDAOAppException("Inserting to the table :  failed to retrieve the generated key."); 
						}
					} catch (Exception e) {
						throw new NEDSSDAOAppException("Inserting to the table:  failed to retrieve the generated key."); 
					}	

				


		} catch (SQLException sex) {
			if (logSQLException(statement))
				logger.fatal("SQLException executing: " + statement
						+ " ERROR = " + sex.getMessage(), sex);
			throw new NEDSSDAOSysException(sex.toString());
		} catch (Exception ex) {
			logger.fatal("Exception  executing: " + statement
						+ "Error ="+ ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		// clean house
		finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
	}

}