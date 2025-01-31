package gov.cdc.nedss.act.ctcontact.ejb.dao;

import gov.cdc.nedss.act.ctcontact.dt.CTContactAttachmentDT;
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
import java.util.ArrayList;
import java.util.Collection;

/**
 * Name:		CTContactAttachmentDAO.java
 * Description:	DAO Object for CT_contact answers.
 * Copyright:	Copyright (c) 2009
 * Company: 	Computer Sciences Corporation
 */

public class CTContactAttachmentDAO extends DAOBase{
	static final LogUtils logger = new LogUtils(CTContactAttachmentDAO.class.getName());
	private static PropertyUtil propertyUtil= PropertyUtil.getInstance();
	
	private static final String SELECT_CT_CONTACT_ATTACHMENT = "SELECT attachment FROM "+ DataTables.CT_CONTACT_ATTACHMENT +" where ct_contact_attachment_uid = ?";
	private static final String SELECT_CT_CONTACT_ATTACHMENT_COLLECTION = "SELECT ct_contact_attachment_uid \"ctContactAttachmentUid\", attachment, desc_txt \"descTxt\", file_nm_txt \"fileNmTxt\", last_chg_time \"lastChgTime\", last_chg_user_id \"lastChgUserId\", ct_contact_uid \"ctContactUid\" FROM "+ DataTables.CT_CONTACT_ATTACHMENT +" where ct_contact_attachment_uid = ?";
	private static final String SELECT_CT_SUMMARY_CONTACT_ATTACHMENT_COLLECTION = "SELECT ct_contact_attachment_uid \"ctContactAttachmentUid\", desc_txt \"descTxt\", file_nm_txt \"fileNmTxt\", last_chg_time \"lastChgTime\", last_chg_user_id \"lastChgUserId\", ct_contact_uid \"ctContactUid\" FROM "+ DataTables.CT_CONTACT_ATTACHMENT +" where ct_contact_uid = ?";
	private static final String INSERT_CT_CONTACT_ATTACHMENT_SQL = "INSERT INTO " + DataTables.CT_CONTACT_ATTACHMENT + "(attachment, desc_txt, file_nm_txt, last_chg_time, last_chg_user_id, ct_contact_uid) VALUES(?,?,?,?,?,?)"+ "SELECT @@IDENTITY AS 'Identity'";
	private static final String DELETE_CT_CONTACT_ATTACHMENT = "DELETE FROM "+ DataTables.CT_CONTACT_ATTACHMENT + " WHERE ct_contact_attachment_uid= ?";
	private static final String DELETE_ALL_CT_CONTACT_ATTACHMENT = "DELETE FROM "+ DataTables.CT_CONTACT_ATTACHMENT + " WHERE ct_contact_uid= ?";

	/*
	 * Get CTContactAnswerDT Collection<Object>  Object for a given ctContactUid
	 * @return Collection<Object> of CTContactAttachmentDT
	 */
	@SuppressWarnings("unchecked")
	public Collection<Object> getContactAttachmentCollection(Long ctContactUid) throws NEDSSSystemException {
		CTContactAttachmentDT  ctContactAttachmentDT  = new CTContactAttachmentDT();
		ArrayList<Object>  ctContactAttachmentDTCollection  = new ArrayList<Object>();

		ctContactAttachmentDTCollection.add(ctContactUid);
		try
		{
			ctContactAttachmentDTCollection  = (ArrayList<Object> )preparedStmtMethod(ctContactAttachmentDT, ctContactAttachmentDTCollection, SELECT_CT_CONTACT_ATTACHMENT_COLLECTION, NEDSSConstants.SELECT);
		}
		catch (Exception ex) {
			logger.fatal("Exception in getContactAttachmentCollection:  ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return ctContactAttachmentDTCollection;
	}

	/**
	 * Retrieves the AttachmentTxt for a given ctContactAttachmentUid
	 * @param ctContactAttachmentUid
	 * @return
	 * @throws NEDSSSystemException
	 */
	public byte[] getContactAttachment(Long ctContactAttachmentUid) throws NEDSSSystemException {
			return getContactAttachmentOracle(ctContactAttachmentUid);
	}
		
	private byte[] getContactAttachmentOracle(Long ctContactAttachmentUid) throws NEDSSSystemException {
		//String attachmentTxt = null;
		byte[] bA = null;
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;		
        ResultSet resultSet = null;
		try {
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(SELECT_CT_CONTACT_ATTACHMENT);		
            preparedStmt.setLong(1, ctContactAttachmentUid.longValue());
            resultSet = preparedStmt.executeQuery();
            if (!resultSet.next()) {
                String errorString = "CTContactAttachmentDAO.getContactAttachment - could find attachment for ctContactAttachmentUid="+ctContactAttachmentUid;
    			logger.fatal(errorString);
    			throw new NEDSSSystemException(errorString);            	
            }
            Blob b = resultSet.getBlob(1);     
            
            bA = b.getBytes(1, (int)b.length());
            //attachmentTxt = bA.toString();
		} catch (Exception ex) {
			logger.fatal("Exception in getSummaryCTContactDTCollection:  ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}finally{
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		return bA;
	}
	
	
	
	/*
	 * Get CTContactAnswerDT Collection<Object>  Object for a given ctContactUid, but don't retrieve attachment Blob
	 * @return Collection<Object> of CTContactAttachmentDT
	 */
	@SuppressWarnings("unchecked")
	public Collection<Object> getSummaryCTContactAttachmentDTCollection(Long ctContactUid) throws NEDSSSystemException {
		CTContactAttachmentDT  ctContactAttachmentDT  = new CTContactAttachmentDT();
		ArrayList<Object> ctContactAttachmentDTCollection = new ArrayList<Object> ();
		ctContactAttachmentDTCollection.add(ctContactUid);
		try {
			ctContactAttachmentDTCollection  = (ArrayList<Object> )preparedStmtMethod(ctContactAttachmentDT, ctContactAttachmentDTCollection, SELECT_CT_SUMMARY_CONTACT_ATTACHMENT_COLLECTION, NEDSSConstants.SELECT);
		} catch (Exception ex) {
			logger.fatal("Exception in getSummaryCTContactDTCollection:  ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}

		return ctContactAttachmentDTCollection;
	}

	public Long insertCTContactAttachment(CTContactAttachmentDT ctContactAttachementDT) throws  NEDSSSystemException {
			return insertCTContactAttachmentSQL(ctContactAttachementDT);
	}

	private Long insertCTContactAttachmentSQL(CTContactAttachmentDT ctContactAttachementDT) throws  NEDSSSystemException {
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		int resultCount = 0;
		Long ctContactAttachmentUid = null;

		logger.debug("INSERT_CT_CONTACT_ATTACHMENT_SQL="+INSERT_CT_CONTACT_ATTACHMENT_SQL);
		
		try{
			dbConnection = getConnection();
		}catch(NEDSSSystemException nsex){
			logger.fatal("SQLException while obtaining database connection for insertCTContactAttachmentSQL ", nsex);
			throw new NEDSSSystemException(nsex.toString());
		}
		Long retUid =null;
		ResultSet rs = null;
		try {
			preparedStmt = dbConnection.prepareStatement(INSERT_CT_CONTACT_ATTACHMENT_SQL);
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
			preparedStmt.setBytes(i++,  ctContactAttachementDT.getAttachment()); //1
			preparedStmt.setString(i++, ctContactAttachementDT.getDescTxt()); //2
			preparedStmt.setString(i++, ctContactAttachementDT.getFileNmTxt()); //3
			preparedStmt.setTimestamp(i++, ctContactAttachementDT.getLastChgTime()); //4
			if(ctContactAttachementDT.getLastChgUserId()!=null)
				preparedStmt.setLong(i++, ctContactAttachementDT.getLastChgUserId().longValue()); //5
			preparedStmt.setLong(i++, ctContactAttachementDT.getCtContactUid().longValue()); //6

			resultCount = preparedStmt.executeUpdate();      


			logger.debug("resultCount in insertCTContactAttachmentSQL is " + resultCount);

		try {
			rs=preparedStmt.getGeneratedKeys();

				if (rs.next()) {
					// return CT_contact_attachment.ct_contact_attachment_uid
					ctContactAttachmentUid = new Long(rs.getLong(1));
					rs.close();
					return ctContactAttachmentUid;
				} else {
					rs.close();
					throw new NEDSSDAOAppException("Exception while inserting contact attachment"); 
				}
			} catch (Exception e) {
				throw new NEDSSDAOAppException("Exception while inserting contact attachment"); 
			}	

		}
		catch(SQLException sqlex)
		{
			logger.fatal("SQLException while inserting ctContactAttachmentDT into CT_contact_attachment: " + ctContactAttachementDT.toString(), sqlex);
			throw new NEDSSDAOSysException( sqlex.toString() );
		}
		catch(Exception ex)
		{
			logger.fatal("Error while inserting into CT_contact_attachment, ctContactAttachmentDT = " + ctContactAttachementDT.toString(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		finally
		{
			closeResultSet(rs);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
	
	}


	public void removeAllCTContactAttachments(Long ctContactUid) throws NEDSSDAOSysException, NEDSSSystemException {
		PreparedStatement preparedStmt = null;
		Connection dbConnection = null;

		try {
			logger.debug("DELETE_ALL_CT_CONTACT_ATTACHMENT = "+ DELETE_ALL_CT_CONTACT_ATTACHMENT);
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(DELETE_ALL_CT_CONTACT_ATTACHMENT);
			preparedStmt.setLong(1, ctContactUid.longValue());
			preparedStmt.executeUpdate();
		} catch (SQLException sqlex) {
			sqlex.printStackTrace();
			logger.fatal("SQLException while removeCTContactAttachments for ct_contact_uid: " + ctContactUid, sqlex);
			throw new NEDSSDAOSysException(sqlex.toString());
		} 
		finally
		{
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
	}

	
	public void removeCTContactAttachment(Long ctContactAttachmentUid) throws NEDSSDAOSysException, NEDSSSystemException {
		PreparedStatement preparedStmt = null;
		Connection dbConnection = null;

		try {
			logger.debug("DELETE_CT_CONTACT_ATTACHMENT = "+ DELETE_CT_CONTACT_ATTACHMENT);
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(DELETE_CT_CONTACT_ATTACHMENT);
			preparedStmt.setLong(1, ctContactAttachmentUid.longValue());
			preparedStmt.executeUpdate();
		} catch (SQLException sqlex) {
			logger.fatal("SQLException while removeCTContactAttachment for uid: " + ctContactAttachmentUid, sqlex);
			throw new NEDSSDAOSysException(sqlex.toString());
		} finally {
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
	}

	

	
}