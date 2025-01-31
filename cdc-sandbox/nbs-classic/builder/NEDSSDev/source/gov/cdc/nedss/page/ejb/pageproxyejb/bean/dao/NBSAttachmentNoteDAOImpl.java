package gov.cdc.nedss.page.ejb.pageproxyejb.bean.dao;

import gov.cdc.nedss.act.attachment.dt.AttachmentDT;
import gov.cdc.nedss.act.attachment.vo.AttachmentVO;
import gov.cdc.nedss.exception.NEDSSDAOAppException;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.page.ejb.pageproxyejb.dt.NBSAttachmentDT;
import gov.cdc.nedss.page.ejb.pageproxyejb.dt.NBSNoteDT;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.DataTables;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.StringUtils;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.struts.upload.FormFile;

public class NBSAttachmentNoteDAOImpl extends DAOBase{
	
	static final LogUtils logger = new LogUtils(NBSAttachmentNoteDAOImpl.class.getName());
	
	//private static final String SELECT_CT_CONTACT_ATTACHMENT = "SELECT attachment FROM "+ DataTables.CT_CONTACT_ATTACHMENT +" where ct_contact_attachment_uid = ?";
	private static final String SELECT_NBS_ATTACHMENT_COLLECTION = "SELECT nbs_attachment_uid \"nbsAttachmentUid\", attachment, desc_txt \"descTxt\", file_nm_txt \"fileNmTxt\", last_chg_time \"lastChgTime\", last_chg_user_id \"lastChgUserId\", attachment_parent_uid \"attachmentParentUid\" FROM "+ DataTables.NBS_ATTACHMENT +" where attachment_parent_uid = ?";
	
	private static final String SELECT_NBS_ATTACHMENT = "SELECT attachment FROM "+ DataTables.NBS_ATTACHMENT +" where nbs_attachment_uid = ?";
	private static final String INSERT_NBS_ATTACHMENT_SQL = "INSERT INTO " + DataTables.NBS_ATTACHMENT + "(attachment, desc_txt, file_nm_txt, last_chg_time, last_chg_user_id, attachment_parent_uid,type_cd) VALUES(?,?,?,?,?,?,?)"+ "SELECT @@IDENTITY AS 'Identity'";
	private static final String DELETE_NBS_ATTACHMENT = "DELETE FROM "+ DataTables.NBS_ATTACHMENT + " WHERE nbs_attachment_uid= ?";
	private static final String UPDATE_CASE_FOR_NBS_ATTACHMENT = "UPDATE " + DataTables.NBS_ATTACHMENT + " set attachment_parent_uid=? where attachment_parent_uid=?";

	
	// Notes 
	
	private static final String SELECT_NBS_NOTE_COLLECTION = "SELECT NBS_NOTE_UID \"nbsNoteUid\", note_parent_UID \"noteParentUid\", RECORD_STATUS_CD \"recordStatusCd\", RECORD_STATUS_TIME \"recordStatusTime\", ADD_TIME \"addTime\", ADD_USER_ID \"addUserId\", " + 
   								"LAST_CHG_TIME \"lastChgTime\", LAST_CHG_USER_ID \"lastChgUserId\", NOTE \"note\", PRIVATE_IND_CD \"privateIndCd\" FROM NBS_NOTE WHERE RECORD_STATUS_CD = 'ACTIVE' AND NOTE_PARENT_UID = ?";
	private static final String INSERT_NBS_NOTE = "INSERT INTO NBS_NOTE (NOTE_PARENT_UID, RECORD_STATUS_CD, " + 
	 							"RECORD_STATUS_TIME, ADD_TIME, ADD_USER_ID, LAST_CHG_TIME, LAST_CHG_USER_ID, NOTE, " + 
	 							"PRIVATE_IND_CD,TYPE_CD) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
	private static final String UPDATE_CASE_FOR_NBS_NOTE = "UPDATE " + DataTables.NBS_NOTE + " set note_parent_uid=? where note_parent_uid=?";
																																																								
	
	public Collection<Object> getNbsAttachmentCollection(Long attachmentParentUid) throws NEDSSSystemException {
		NBSAttachmentDT  nBSAttachmentDT  = new NBSAttachmentDT();
		ArrayList<Object>  nbsCaseAttachmentDTColl  = new ArrayList<Object>();
		ArrayList<Object>  nbsCaseAttachmentDTCollVO  = new ArrayList<Object>();

		nbsCaseAttachmentDTColl.add(attachmentParentUid);
		try
		{
			nbsCaseAttachmentDTColl  = (ArrayList<Object> )preparedStmtMethod(nBSAttachmentDT, nbsCaseAttachmentDTColl, SELECT_NBS_ATTACHMENT_COLLECTION, NEDSSConstants.SELECT);
		}
		catch (Exception ex) {
			logger.fatal("Exception in getContactAttachmentCollection:  ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		
		for(int i=0; i<nbsCaseAttachmentDTColl.size(); i++){
			
			AttachmentVO attachmentVO = new AttachmentVO();
			
			
			NBSAttachmentDT nbsAttachmentDT = (NBSAttachmentDT)nbsCaseAttachmentDTColl.get(i);
			AttachmentDT attachmentDT = new AttachmentDT();
			
			attachmentDT.setLastChangeTime(StringUtils.formatDate(nbsAttachmentDT.getLastChgTime()));
			attachmentDT.setLastChangeUserId(nbsAttachmentDT.getLastChgUserId());
			attachmentDT.setFileNmTxt(nbsAttachmentDT.getFileNmTxt());
			attachmentDT.setDescTxt(nbsAttachmentDT.getDescTxt());
			attachmentDT.setAttachmentData(nbsAttachmentDT.getAttachment());
		
			attachmentDT.setNbsAttachmentUid(nbsAttachmentDT.getNbsAttachmentUid());
			//attachmentDT.setAttachment(nbsAttachmentDT.getAttachment());

			
			
			attachmentVO.setTheAttachmentDT(attachmentDT);
			nbsCaseAttachmentDTCollVO.add(attachmentVO);
		}
			
			
			return nbsCaseAttachmentDTColl;
	//	return nbsCaseAttachmentDTCollVO;
	}
	
	public Long insertNbsAttachment(NBSAttachmentDT nbsCaseAttachmentDT) throws  NEDSSSystemException {
		try{
				return insertNbsAttachmentSQL(nbsCaseAttachmentDT);
		}catch(Exception ex){
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}
	


	private Long insertNbsAttachmentSQL(NBSAttachmentDT nbsAttachmentDT) throws  NEDSSSystemException {
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		int resultCount = 0;
		Long nbsCaseAttachmentUid = null;

		logger.debug("INSERT_NBS_ATTACHMENT_SQL="+INSERT_NBS_ATTACHMENT_SQL);
		
		Long retUid =null;
		ResultSet rs = null;
		try {
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(INSERT_NBS_ATTACHMENT_SQL);
			int i = 1;
			//JDK1.6 has a createBlob() method on Connection Object that returns a pseudo Blob, then set Bytes from Attachment to the Blob
		//	Blob blob = dbConnection.createBlob();
/*			ByteArrayOutputStream baos=new ByteArrayOutputStream(1000);
			BufferedImage img=ImageIO.read(ctContactAttachementDT.getAttachment());
			ImageIO.write(img, "jpg", baos);
			baos.flush();
			byte[] resultimage=baos.toByteArray();
			baos.close();*/			
			//blob.setBytes(1, ctContactAttachementDT.getAttachment());			
			preparedStmt.setBytes(i++,  nbsAttachmentDT.getAttachment()); //1
			preparedStmt.setString(i++, nbsAttachmentDT.getDescTxt()); //2
			preparedStmt.setString(i++, nbsAttachmentDT.getFileNmTxt()); //3
			preparedStmt.setTimestamp(i++, nbsAttachmentDT.getLastChgTime()); //4
			if(nbsAttachmentDT.getLastChgUserId()!=null)
				preparedStmt.setLong(i++, nbsAttachmentDT.getLastChgUserId().longValue()); //5
			preparedStmt.setLong(i++, nbsAttachmentDT.getAttachmentParentUid().longValue()); //6
			preparedStmt.setString(i++, nbsAttachmentDT.getTypeCd()); //2	
			resultCount = preparedStmt.executeUpdate();      


			logger.debug("resultCount in INSERT_NBS_ATTACHMENT_SQL is " + resultCount);

		try {
			rs=preparedStmt.getGeneratedKeys();

				if (rs.next()) {
					// return CT_contact_attachment.ct_contact_attachment_uid
					nbsCaseAttachmentUid = new Long(rs.getLong(1));
					rs.close();
					return nbsCaseAttachmentUid;
				} else {
					rs.close();
					throw new NEDSSDAOAppException("insertNbsAttachmentSQL:  failed to retrieve nbs_attachment_uid as a generated key."); 
				}
			} catch (Exception e) {
				throw new NEDSSDAOAppException("insertNbsCaseAttachmentSQL:  failed to retrieve nbs_attachment_uid as a generated key."); 
			}	

		}
		catch(SQLException sqlex)
		{
			logger.fatal("SQLException while inserting NBSCaseAttachmentDT into NBS_CASE_attachment: " + nbsAttachmentDT.toString(), sqlex);
			throw new NEDSSDAOSysException( sqlex.toString() );
		}
		catch(Exception ex)
		{
			logger.fatal("Error while inserting into  NBS_CASE_attachment, ctContactAttachmentDT = " + nbsAttachmentDT.toString(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		finally
		{
			closeResultSet(rs);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
	
	}
	
	public void removeNbsAttachment(Long nbsAttachmentUid) throws NEDSSDAOSysException, NEDSSSystemException {
		PreparedStatement preparedStmt = null;
		Connection dbConnection = null;

		try {
			logger.debug("DELETE_NBS_ATTACHMENT = "+ DELETE_NBS_ATTACHMENT);
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(DELETE_NBS_ATTACHMENT);
			preparedStmt.setLong(1, nbsAttachmentUid.longValue());
			preparedStmt.executeUpdate();
		} catch (SQLException sqlex) {
			logger.fatal("SQLException while removing the NBS Attachment for uid: " + nbsAttachmentUid, sqlex);
			throw new NEDSSDAOSysException(sqlex.toString());
		} finally {
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
	}
	 
	/**
	 * 
	 * @param nbsAttachmentUid
	 * @return
	 * @throws NEDSSSystemException
	 */
		public byte[] getNBSAttachment(Long nbsAttachmentUid) throws NEDSSSystemException {
			try{
				return getNBSAttachmentOracle(nbsAttachmentUid);
			}catch(Exception ex){
				logger.fatal("Exception  = "+ex.getMessage(), ex);
				throw new NEDSSSystemException(ex.toString());
			}
		}
		private byte[] getNBSAttachmentOracle(Long nbsAttachmentUid) throws NEDSSSystemException {
			//String attachmentTxt = null;
			byte[] bA = null;
	        Connection dbConnection = null;
	        PreparedStatement preparedStmt = null;		
	        ResultSet resultSet = null;
			try {
	            dbConnection = getConnection();
	            preparedStmt = dbConnection.prepareStatement(SELECT_NBS_ATTACHMENT);		
	            preparedStmt.setLong(1, nbsAttachmentUid.longValue());
	            resultSet = preparedStmt.executeQuery();
	            if (!resultSet.next()) {
	                String errorString = "NBSAttachmentNoteDAO.getNBSAttachmentOracle - could find attachment for nbsAttachmentUid="+nbsAttachmentUid;
	    			logger.fatal(errorString);
	    			throw new NEDSSSystemException(errorString);            	
	            }
	            Blob b = resultSet.getBlob(1);     
	            
	            bA = b.getBytes(1, (int)b.length());
	            //attachmentTxt = bA.toString();
			} catch (Exception ex) {
				logger.fatal("Exception in getNBSAttachmentOracle:  ERROR = " + ex.getMessage(), ex);
				throw new NEDSSSystemException(ex.toString());
			}finally{
				closeResultSet(resultSet);
				closeStatement(preparedStmt);
				releaseConnection(dbConnection);
			}
			return bA;
		}
		
		private String getNBSAttachmentSQL(Long nbsAttachmentUid) throws NEDSSSystemException {
			String attachmentTxt = null;
	        Connection dbConnection = null;
	        PreparedStatement preparedStmt = null;		
	        ResultSet resultSet = null;
			try {
	            dbConnection = getConnection();
	            preparedStmt = dbConnection.prepareStatement(SELECT_NBS_ATTACHMENT);		
	            preparedStmt.setLong(1, nbsAttachmentUid.longValue());
	            resultSet = preparedStmt.executeQuery();
	            if (!resultSet.next()) {
	                String errorString = "NBSAttachmentNoteDAO.getNBSAttachmentSQL - could find attachment for nbsAttachmentUid="+nbsAttachmentUid;
	    			logger.fatal(errorString);
	    			throw new NEDSSSystemException(errorString);            	
	            }
	            attachmentTxt = resultSet.getString(1);
			} catch (Exception ex) {
				logger.fatal("Exception in getNBSAttachmentSQL:  ERROR = " + ex.getMessage(), ex);
				throw new NEDSSSystemException(ex.toString());
			}
			finally{
				closeResultSet(resultSet);
				closeStatement(preparedStmt);
				releaseConnection(dbConnection);
			}
			return attachmentTxt;
		}
		
	 

	/*
	 * Get NBSCaseNoteDT Collection<Object>  Object for a given nbsCaseNotesUid where Notes are "ACTIVE" and either public or private but for this user
	 * @return Collection<Object> of CTContactNoteDT
	 */
	@SuppressWarnings("unchecked")
	public Collection<Object> getNbsNoteCollection(Long nbsNoteParentUid) throws NEDSSSystemException {
		NBSNoteDT  nbsNoteDT  = new NBSNoteDT();
		ArrayList<Object>  nbsCaseNoteDTColl  = new ArrayList<Object>();

		nbsCaseNoteDTColl.add(nbsNoteParentUid);
		
		try
		{
			nbsCaseNoteDTColl  = (ArrayList<Object> )preparedStmtMethod(nbsNoteDT, nbsCaseNoteDTColl, SELECT_NBS_NOTE_COLLECTION, NEDSSConstants.SELECT);
		}
		catch (Exception ex) {
			logger.fatal("Exception in getContactNoteCollection:  ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return nbsCaseNoteDTColl;
	}
	/**
	 * To insert the record to the NBSCaseNotes table
	 * @param nbsCaseNoteDT
	 * @return
	 * @throws NEDSSSystemException
	 */
	public Long insertNbsNote(NBSNoteDT nbsNoteDT) throws  NEDSSSystemException {
		try{
			return insertNbsNoteSQL(nbsNoteDT);
		}catch(Exception ex){
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}
	
	private Long insertNbsNoteSQL(NBSNoteDT nbsNoteDT) throws  NEDSSSystemException {
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		int resultCount = 0;
		Long nbsCaseNoteUid = null;
		ResultSet rs = null;
		logger.debug("INSERT_NBS_CASE_NOTE="+INSERT_NBS_NOTE);
		try {
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(INSERT_NBS_NOTE + "  SELECT @@IDENTITY AS 'Identity'");
			int i = 1;
			
			if(nbsNoteDT.getNoteParentUid() == null)
				preparedStmt.setNull(i++, Types.INTEGER); //1
            else
            	preparedStmt.setLong(i++, nbsNoteDT.getNoteParentUid().longValue()); //1
			preparedStmt.setString(i++, nbsNoteDT.getRecordStatusCd()); //2
			if(nbsNoteDT.getRecordStatusTime() == null)
				preparedStmt.setNull(i++, Types.TIMESTAMP); //3
            else
            	preparedStmt.setTimestamp(i++, nbsNoteDT.getRecordStatusTime()); //3
			if(nbsNoteDT.getAddTime() == null)
				preparedStmt.setNull(i++, Types.TIMESTAMP); //4
            else
            	preparedStmt.setTimestamp(i++, nbsNoteDT.getAddTime()); //4
			if(nbsNoteDT.getAddUserId() == null)
				preparedStmt.setNull(i++, Types.INTEGER); //5
            else
            	preparedStmt.setLong(i++, nbsNoteDT.getAddUserId().longValue()); //5
			if(nbsNoteDT.getLastChgTime() == null)
				preparedStmt.setNull(i++, Types.TIMESTAMP); //6
            else
            	preparedStmt.setTimestamp(i++, nbsNoteDT.getLastChgTime()); //6
			if(nbsNoteDT.getLastChgUserId() == null)
				preparedStmt.setNull(i++, Types.INTEGER); //7
            else
            	preparedStmt.setLong(i++, nbsNoteDT.getLastChgUserId().longValue()); //7
			preparedStmt.setString(i++, nbsNoteDT.getNote()); //8
			preparedStmt.setString(i++, nbsNoteDT.getPrivateIndCd()); //9
			preparedStmt.setString(i++, nbsNoteDT.getTypeCd()); //10
			resultCount = preparedStmt.executeUpdate();
			logger.debug("resultCount in insertNbsNote is " + resultCount);

			try {
				 rs = preparedStmt.getGeneratedKeys();

				if (rs.next()) {
					// return CT_contact_attachment.ct_contact_attachment_uid
					nbsCaseNoteUid = new Long(rs.getLong(1));
					rs.close();
					return nbsCaseNoteUid;
				} else {
					rs.close();
					throw new NEDSSDAOAppException("NBSAttachmentDAO.insertNbsNoteSQL:  failed to retrieve nbs_note_uid as a generated key."); 
				}
			} catch (Exception e) {
				throw new NEDSSDAOAppException("NBSAttachmentDAO.insertNbsNoteSQL:  failed to retrieve nbs_note_uid as a generated key."); 
			}	

		}
		catch(SQLException sqlex)
		{
			logger.fatal("SQLException while inserting NBSCaseNoteDT into NBS_CASE_NOTE: " + nbsNoteDT.toString(), sqlex);
			throw new NEDSSDAOSysException( sqlex.toString() );
		}
		catch(Exception ex)
		{
			logger.fatal("Error while inserting NBSCaseNoteDT into NBS_CASE_NOTE:  " + nbsNoteDT.toString(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		finally
		{
			closeResultSet(rs);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}		
		
	}
	

    /**
     * updateNbsNoteCase - during change condition - point the notes to the new UID
     * @param oldCaseUid - publicHealthCase of condition changing from
     * @param newCaseUid - publicHealthCase of condition changing to
     * @return number of records updated
     */
	public int updateNbsNoteCase(Long oldCaseUid, Long newCaseUid) throws NEDSSDAOSysException, NEDSSSystemException {
		PreparedStatement preparedStmt = null;
		Connection dbConnection = null;
		int updateCount;
		try {
			logger.debug("Update_NBS_Notes = "+ UPDATE_CASE_FOR_NBS_NOTE);
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(UPDATE_CASE_FOR_NBS_NOTE);
			preparedStmt.setLong(1, newCaseUid.longValue());
			preparedStmt.setLong(2, oldCaseUid.longValue());
			updateCount = preparedStmt.executeUpdate();
		} catch (SQLException sqlex) {
			logger.fatal("SQLException while updating the NBS Note attached to " + oldCaseUid + " for  new uid: " + newCaseUid + " " + sqlex.getMessage(), sqlex);
			throw new NEDSSDAOSysException(sqlex.toString());
		} finally {
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		return updateCount;
	}
	
	
    /**
     * updateNbsAttachmentCase - during change condition - point the attachments to the new UID
     * @param oldCaseUid - publicHealthCase of condition changing from
     * @param newCaseUid - publicHealthCase of condition changing to
     * @return number of records updated
     */
	public int updateNbsAttachmentCase(Long oldCaseUid, Long newCaseUid) throws NEDSSDAOSysException, NEDSSSystemException {
		PreparedStatement preparedStmt = null;
		Connection dbConnection = null;
		int updateCount;
		try {
			logger.debug("Update_NBS_Notes = "+ UPDATE_CASE_FOR_NBS_ATTACHMENT);
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(UPDATE_CASE_FOR_NBS_ATTACHMENT);
			preparedStmt.setLong(1, newCaseUid.longValue());
			preparedStmt.setLong(2, oldCaseUid.longValue());
			updateCount = preparedStmt.executeUpdate();
		} catch (SQLException sqlex) {
			logger.fatal("SQLException while updating the NBS Attachment attached to " + oldCaseUid + " for  new uid: " + newCaseUid + " " + sqlex.getMessage(), sqlex);
			throw new NEDSSDAOSysException(sqlex.toString());
		} finally {
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		return updateCount;
	}
}
