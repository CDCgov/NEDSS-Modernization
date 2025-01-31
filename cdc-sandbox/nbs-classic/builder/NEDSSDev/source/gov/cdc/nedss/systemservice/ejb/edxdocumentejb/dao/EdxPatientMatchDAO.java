package gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dt.EdxPatientMatchDT;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;

/**
 * 
 * @author Sathya L
 * @since Release 4.3
 * 
 */

public class EdxPatientMatchDAO extends DAOBase {
	static final LogUtils logger = new LogUtils(
			EdxPatientMatchDAO.class.getName());

	public void setEdxPatientMatchDT(EdxPatientMatchDT edxPatientMatchDT)
			throws NEDSSSystemException {
		String INSERT_EDX_PATIENT_MATCH = "INSERT INTO EDX_PATIENT_MATCH (PATIENT_UID,MATCH_STRING, TYPE_CD,MATCH_STRING_HASHCODE)VALUES(?,?,?,?)";
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		int resultCount = 0;
		logger.debug("INSERT_EDX_PATIENT_MATCH=" + INSERT_EDX_PATIENT_MATCH);
		dbConnection = getConnection();
		try {
			preparedStmt = dbConnection
					.prepareStatement(INSERT_EDX_PATIENT_MATCH);
			int i = 1;

			if (edxPatientMatchDT.getPatientUid() == null)
				preparedStmt.setNull(i++, Types.INTEGER); // 1
			else
				preparedStmt.setLong(i++, edxPatientMatchDT.getPatientUid()); // 1
			preparedStmt.setString(i++, edxPatientMatchDT.getMatchString()); // 2
			preparedStmt.setString(i++, edxPatientMatchDT.getTypeCd()); // 3
			if (edxPatientMatchDT.getMatchStringHashCode() == null)
				preparedStmt.setNull(i++, Types.INTEGER);// 4
			else
				preparedStmt.setLong(i++,
						edxPatientMatchDT.getMatchStringHashCode());// 4
			resultCount = preparedStmt.executeUpdate();
			logger.debug("resultCount in insertEdxPatientMatchDT is "
					+ resultCount);
		} catch (SQLException sqlex) {
			logger.fatal("SQLException while inserting "
					+ "insertAutoLabInvDT into INSERT_AUTO_INV:"
					+ edxPatientMatchDT.toString(), sqlex);
			throw new NEDSSDAOSysException(sqlex.toString(), sqlex);
		} catch (Exception ex) {
			logger.fatal(
					"Error while inserting into EDX_ENTITY_MATCH, edxPatientMatchDT="
							+ edxPatientMatchDT.toString(), ex);
			throw new NEDSSSystemException(ex.toString(), ex);
		} finally {
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}

	}// end of insert method



	public EdxPatientMatchDT getEdxPatientMatchOnUid(Long edxPatientMatchUid)
			throws NEDSSDAOSysException, NEDSSSystemException {
		String SELECT_EDX_PATIENT_MATCH = "Select PATIENT_UID \"patientUid\" ,MATCH_STRING \"matchString\", TYPE_CD \"typeCd\" ,MATCH_STRING_HASHCODE \"matchStringHashCode\" from EDX_PATIENT_MATCH Where edx_patient_match_uid = ? ";
		EdxPatientMatchDT edxPatientMatchDT = new EdxPatientMatchDT();
		ArrayList<Object> paramList = new ArrayList<Object>();
		paramList.add(edxPatientMatchUid);
		try {
			paramList = (ArrayList<Object>) preparedStmtMethod(
					edxPatientMatchDT, paramList, SELECT_EDX_PATIENT_MATCH,
					NEDSSConstants.SELECT);
			if (paramList.size() > 0)
				return (EdxPatientMatchDT) paramList.get(0);
		} catch (Exception ex) {
			logger.fatal("Exception in EdsPatientMatchDAO.getEdxPatientMatchOnUid for match uid ="
					+ edxPatientMatchUid + ": ERROR = " + ex);
			throw new NEDSSSystemException(ex.toString(), ex);
		}
		return edxPatientMatchDT;
	}

	/*
	public EdxPatientMatchDT getEdxPatientMatchOnHashedCode(long matchHashCd, String cd)
			throws NEDSSDAOSysException, NEDSSSystemException {
		String SELECT_EDX_PATIENT_MATCH = "Select distinct PATIENT_UID \"patientUid\" ,MATCH_STRING \"matchString\", TYPE_CD \"typeCd\" ,MATCH_STRING_HASHCODE \"matchStringHashCode\" from EDX_PATIENT_MATCH Where MATCH_STRING_HASHCODE = ? and TYPE_CD = ? order by PATIENT_UID desc ";
		EdxPatientMatchDT edxPatientMatchDT = new EdxPatientMatchDT();
		ArrayList<Object> paramList = new ArrayList<Object>();
		paramList.add(matchHashCd);
		paramList.add(cd);
		try {
			ArrayList<Object> paramList1 = (ArrayList<Object>) preparedStmtMethod(
					edxPatientMatchDT, paramList, SELECT_EDX_PATIENT_MATCH,
					NEDSSConstants.SELECT);
		
			if(paramList1 != null && paramList1.size() > 0)
			{
				edxPatientMatchDT =(EdxPatientMatchDT) paramList1.get(0);
				if (paramList1.size() > 1)
				 {
					edxPatientMatchDT.setMultipleMatch(true);
				 }
			}
			
		} catch (Exception ex) {
			logger.fatal("Exception in EdxPatientMatchDAO.findWaQuestion for question uid ="
					+ matchHashCd + ": ERROR = " + ex);
			throw new NEDSSSystemException(ex.toString(), ex);
		}
		return edxPatientMatchDT;
	}
	*/
	
	/*
	 * Above routine modified to use Match String instead of Hash Code bacause Java hash found to 
	 * return duplicates on occasion. Note the the table EDX_PATIENT_MATCH
	 * should have the match string column indexed.
	 */
	public EdxPatientMatchDT getEdxPatientMatchOnMatchString(String typeCd, String matchString)
			throws NEDSSDAOSysException, NEDSSSystemException {
		//String SELECT_EDX_PATIENT_MATCH = "Select distinct PATIENT_UID \"patientUid\" ,MATCH_STRING \"matchString\", TYPE_CD \"typeCd\" ,MATCH_STRING_HASHCODE \"matchStringHashCode\" from EDX_PATIENT_MATCH Where TYPE_CD = ? and MATCH_STRING = ? order by PATIENT_UID desc ";
		EdxPatientMatchDT edxPatientMatchDT = new EdxPatientMatchDT();
		if (typeCd == null || matchString == null)
			return edxPatientMatchDT;
		//ArrayList<Object> paramList = new ArrayList<Object>();

		//paramList.add(typeCd); //PAT or NOK (next of kin)
		//paramList.add(matchString);
		try {
			ArrayList<Object> inArrayList= new ArrayList<Object> ();
	        ArrayList<Object>  outArrayList= new ArrayList<Object> ();
	        ArrayList<Object> arrayList = new ArrayList<Object> ();
	        inArrayList.add(typeCd);
	        inArrayList.add(matchString);
	        outArrayList.add(new Integer(java.sql.Types.BIGINT));
	        outArrayList.add(new Integer(java.sql.Types.BIGINT));
			String sQuery  = "{call GETEDXPATIENTMATCH_SP(?,?,?,?)}";
			arrayList = (ArrayList<Object> )callStoredProcedureMethod(sQuery,inArrayList,outArrayList);
			/* Code replaced with a Stored Procedure for for PHDC Import query optimization
			ArrayList<Object> paramList1 = (ArrayList<Object>) preparedStmtMethod(
					edxPatientMatchDT, paramList, SELECT_EDX_PATIENT_MATCH,
					NEDSSConstants.SELECT);
		
			if(paramList1 != null && paramList1.size() > 0)
			{
				edxPatientMatchDT =(EdxPatientMatchDT) paramList1.get(0);
				if (paramList1.size() > 1)
				 {
					edxPatientMatchDT.setMultipleMatch(true);
				 }
			}
			*/
			if(arrayList != null && arrayList.size() > 0)
			{
				edxPatientMatchDT.setPatientUid((Long) arrayList.get(0));
				edxPatientMatchDT.setMatchStringHashCode((Long) arrayList.get(1));
				edxPatientMatchDT.setTypeCd(typeCd);
				edxPatientMatchDT.setMatchString(matchString);
			}
			
		} catch (Exception ex) {
			logger.fatal("Exception in EdxPatientMatchDAO.getEdxPatientMatchOnMatchString for typeCd="
					+ typeCd + " match string=" + matchString + ": ERROR = " + ex);
			throw new NEDSSSystemException(ex.toString(), ex);
		}
		return edxPatientMatchDT;
	}
}
