package gov.cdc.nedss.act.ctcontact.ejb.dao;

import gov.cdc.nedss.act.ctcontact.dt.CTContactAttachmentDT;
import gov.cdc.nedss.act.ctcontact.dt.CTContactNoteDT;
import gov.cdc.nedss.exception.NEDSSDAOAppException;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.DataTables;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Name:		CTContactAttachmentDAO.java
 * Description:	DAO Object for CT_contact answers.
 * Copyright:	Copyright (c) 2009
 * Company: 	Computer Sciences Corporation
 */

public class CTContactNoteDAO extends DAOBase{
	static final LogUtils logger = new LogUtils(CTContactNoteDAO.class.getName());
	private static PropertyUtil propertyUtil= PropertyUtil.getInstance();
	
	private static final String SELECT_CT_CONTACT_NOTE_COLLECTION = "SELECT CT_CONTACT_NOTE_UID \"ctContactNoteUid\", CT_CONTACT_UID \"ctContactUid\", RECORD_STATUS_CD \"recordStatusCd\", RECORD_STATUS_TIME \"recordStatusTime\", ADD_TIME \"addTime\", ADD_USER_ID \"addUserId\", " + 
	   	"LAST_CHG_TIME \"lastChgTime\", LAST_CHG_USER_ID \"lastChgUserId\", NOTE \"note\", PRIVATE_IND_CD \"privateIndCd\" FROM CT_CONTACT_NOTE WHERE RECORD_STATUS_CD = 'ACTIVE' AND CT_CONTACT_UID = ?";
	private static final String INSERT_CT_CONTACT_NOTE = "INSERT INTO CT_CONTACT_NOTE (CT_CONTACT_UID, RECORD_STATUS_CD, " + 
														 "RECORD_STATUS_TIME, ADD_TIME, ADD_USER_ID, LAST_CHG_TIME, LAST_CHG_USER_ID, NOTE, " + 
														 "PRIVATE_IND_CD) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	/*
	 * Get CTContactNoteDT Collection<Object>  Object for a given ctContactUid where Notes are "ACTIVE" and either public or private but for this user
	 * @return Collection<Object> of CTContactNoteDT
	 */
	@SuppressWarnings("unchecked")
	public Collection<Object> getContactNoteCollection(Long ctContactUid) throws NEDSSSystemException {
		CTContactNoteDT  ctContactNoteDT  = new CTContactNoteDT();
		ArrayList<Object>  ctContactNoteDTCollection  = new ArrayList<Object>();

		ctContactNoteDTCollection.add(ctContactUid);
		
		try
		{
			ctContactNoteDTCollection  = (ArrayList<Object> )preparedStmtMethod(ctContactNoteDT, ctContactNoteDTCollection, SELECT_CT_CONTACT_NOTE_COLLECTION, NEDSSConstants.SELECT);
		}
		catch (Exception ex) {
			logger.fatal("Exception in getContactNoteCollection:  ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return ctContactNoteDTCollection;
	}

	public Long insertCTContactNote(CTContactNoteDT ctContactNoteDT) throws  NEDSSSystemException {
		try{
			return insertCTContactNoteSQL(ctContactNoteDT);
		}catch(Exception ex){
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}
	
	private Long insertCTContactNoteSQL(CTContactNoteDT ctContactNoteDT) throws  NEDSSSystemException {
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		int resultCount = 0;
		Long ctContactAttachmentUid = null;
		ResultSet rs = null;
		logger.debug("INSERT_CT_CONTACT_NOTE="+INSERT_CT_CONTACT_NOTE);
		
		try{
			dbConnection = getConnection();
		}catch(NEDSSSystemException nsex){
			logger.fatal("SQLException while obtaining database connection for insertCTContactNoteSQL ", nsex);
			throw new NEDSSSystemException(nsex.toString());
		}
		try {
			preparedStmt = dbConnection.prepareStatement(INSERT_CT_CONTACT_NOTE + "  SELECT @@IDENTITY AS 'Identity'");
			int i = 1;
			
			if(ctContactNoteDT.getCtContactUid() == null)
				preparedStmt.setNull(i++, Types.INTEGER); //1
            else
            	preparedStmt.setLong(i++, ctContactNoteDT.getCtContactUid().longValue()); //1
			preparedStmt.setString(i++, ctContactNoteDT.getRecordStatusCd()); //2
			if(ctContactNoteDT.getRecordStatusTime() == null)
				preparedStmt.setNull(i++, Types.TIMESTAMP); //3
            else
            	preparedStmt.setTimestamp(i++, ctContactNoteDT.getRecordStatusTime()); //3
			if(ctContactNoteDT.getAddTime() == null)
				preparedStmt.setNull(i++, Types.TIMESTAMP); //4
            else
            	preparedStmt.setTimestamp(i++, ctContactNoteDT.getAddTime()); //4
			if(ctContactNoteDT.getAddUserId() == null)
				preparedStmt.setNull(i++, Types.INTEGER); //5
            else
            	preparedStmt.setLong(i++, ctContactNoteDT.getAddUserId().longValue()); //5
			if(ctContactNoteDT.getLastChgTime() == null)
				preparedStmt.setNull(i++, Types.TIMESTAMP); //6
            else
            	preparedStmt.setTimestamp(i++, ctContactNoteDT.getLastChgTime()); //6
			if(ctContactNoteDT.getLastChgUserId() == null)
				preparedStmt.setNull(i++, Types.INTEGER); //7
            else
            	preparedStmt.setLong(i++, ctContactNoteDT.getLastChgUserId().longValue()); //7
			preparedStmt.setString(i++, ctContactNoteDT.getNote()); //8
			preparedStmt.setString(i++, ctContactNoteDT.getPrivateIndCd()); //9

			resultCount = preparedStmt.executeUpdate();
			logger.debug("resultCount in insertCTContactNote is " + resultCount);

			try {
				 rs = preparedStmt.getGeneratedKeys();

				if (rs.next()) {
					// return CT_contact_attachment.ct_contact_attachment_uid
					ctContactAttachmentUid = new Long(rs.getLong(1));
					rs.close();
					return ctContactAttachmentUid;
				} else {
					rs.close();
					throw new NEDSSDAOAppException("CTContactNoteDAO.insertCTContactNote:  failed to retrieve ct_contact_note_uid as a generated key."); 
				}
			} catch (Exception e) {
				throw new NEDSSDAOAppException("CTContactNoteDAO.insertCTContactNote:  failed to retrieve ct_contact_note_uid as a generated key."); 
			}	

		}
		catch(SQLException sqlex)
		{
			logger.fatal("SQLException while inserting ctContactNoteDT into CT_contact_note: " + ctContactNoteDT.toString(), sqlex);
			throw new NEDSSDAOSysException( sqlex.toString() );
		}
		catch(Exception ex)
		{
			logger.fatal("Error while inserting into CT_contact_note, ctContactNoteDT = " + ctContactNoteDT.toString(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		finally
		{
			closeResultSet(rs);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
	}

	
}