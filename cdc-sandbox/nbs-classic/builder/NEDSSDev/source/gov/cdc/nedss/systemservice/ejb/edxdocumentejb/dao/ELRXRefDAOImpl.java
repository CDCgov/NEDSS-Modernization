package gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dao;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.util.*;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author
 * @version 1.0
 */

public class ELRXRefDAOImpl extends BMPBase{
  static final LogUtils logger = new LogUtils( ELRXRefDAOImpl.class.getName());
  private static PropertyUtil propertyUtil= PropertyUtil.getInstance();

  private static final String ELR_CODE_X_REF_SQL = "SELECT to_code FROM nbs_srte..elr_xref WHERE from_code_set_nm = ? "+
  "and from_code = ? and to_code_set_nm = ?";
  
  /**
   * Default Constructor
   */
  public ELRXRefDAOImpl() {

  }//end of constructor

  /**
   * Performs a translation of the "fromCode" parameter based on the "fromCodeSetNm" and
   * "toCodeSetNm" parameters.
   * @param fromCodeSetNm : String
   * @param fromCode : String
   * @param toCodeSetNm : String
   * @return String
   * @throws NEDSSSystemException
   */
	public String findToCode(String fromCodeSetNm, String fromCode, String toCodeSetNm) throws NEDSSSystemException {
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		ResultSetMetaData resultSetMetaData = null;
		String sql = null;
		String toCode = null;
		
		 sql = ELR_CODE_X_REF_SQL;
		

		try {
			dbConnection = getConnection(NEDSSConstants.ODS);
		} catch (NEDSSSystemException nsex) {
			logger.fatal("SQLException while obtaining database connection " + nsex);
			throw new NEDSSSystemException(nsex.toString());
		} // end of catch
		try {
			preparedStmt = dbConnection.prepareStatement(sql);
			preparedStmt.setString(1, fromCodeSetNm);
			preparedStmt.setString(2, fromCode);
			preparedStmt.setString(3, toCodeSetNm);

			resultSet = preparedStmt.executeQuery();
			if (resultSet.next()) {
				toCode = resultSet.getString(1);
			} // end of if
			return toCode;
		} catch (SQLException se) {
			se.printStackTrace();
			throw new NEDSSSystemException("Error: SQLException while selecting \n" + se.getMessage());
		}

		catch (Exception ex) {
			ex.printStackTrace();
			throw new NEDSSSystemException("Exception while selection .... " + ex.getMessage());
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
	}// end of findToCode()

}//end of class