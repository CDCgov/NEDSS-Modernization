package gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.dao;

import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.nnd.dt.TransportQOutDT;
import gov.cdc.nedss.nnd.exception.NNDException;
import gov.cdc.nedss.nnd.util.NNDConstantUtil;
import gov.cdc.nedss.util.BMPBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.ResultSetUtils;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TransportQOutDAOImpl extends BMPBase{
	static final LogUtils logger = new LogUtils(TransportQOutDAOImpl.class.getName());

  private final String INSERT_TRANSPORTQOUT_SQL = "INSERT INTO transportq_out "
                                                       + "(messageCreationTime,messageId,payloadContent,processingStatus,routeInfo,service,action,"
                                                       + "priority,encryption,signature,publicKeyLdapAddress,publicKeyLdapBaseDN,publicKeyLdapDN,certificateURL,"
                                                       + "destinationFilename,messageRecipient) "
                                                       + "VALUES (CONVERT(varchar(19),GETDATE(),126),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) "+
                                                       "SELECT @@IDENTITY AS 'Identity'";

  private final String SELECT_TRANSPORTQOUT_PHINMS_PROCESSED = "SELECT RECORDID \"RecordId\", MESSAGEID \"MessageId\", PAYLOADFILE \"PayloadFile\", "+
                                                    "PAYLOADCONTENT \"PayloadContent\", DESTINATIONFILENAME \"DestinationFileName\", ROUTEINFO \"RouteInfo\", "+
                                                    "SERVICE \"Service\", ACTION \"Action\", ARGUMENTS \"Arguments\", "+
                                                    "MESSAGERECIPIENT \"MessageRecipient\", MESSAGECREATIONTIME \"MessageCreationTime\", ENCRYPTION \"Encryption\", "+
                                                    "SIGNATURE \"Signature\", PUBLICKEYLDAPADDRESS \"PublicKeyLdapAddress\", PUBLICKEYLDAPBASEDN \"PublicKeyLdapBaseDN\", "+
                                                    "PUBLICKEYLDAPDN \"PublicKeyLdapDN\", CERTIFICATEURL \"CertificateURL\", PROCESSINGSTATUS \"ProcessingStatus\", "+
                                                    "TRANSPORTSTATUS \"TransportStatus\", TRANSPORTERRORCODE \"TransportErrorCode\", APPLICATIONSTATUS \"ApplicationStatus\", "+
                                                    "APPLICATIONERRORCODE \"ApplicationErrorCode\", APPLICATIONRESPONSE \"ApplicationResponse\", MESSAGESENTTIME \"MessageSentTime\", "+
                                                    "MESSAGERECEIVEDTIME \"MessageReceivedTime\", RESPONSEMESSAGEID \"ResponseMessageId\", RESPONSEARGUMENTS \"ResponseArguments\", "+
                                                    "RESPONSELOCALFILE \"ResponseLocalFile\", RESPONSEFILENAME \"ResponseFileName\", RESPONSECONTENT \"ResponseContent\", "+
                                                    "RESPONSEMESSAGEORIGIN \"ResponseMessageOrigin\", RESPONSEMESSAGESIGNATURE \"ResponseMessageSignature\", PRIORITY \"Priority\" FROM TRANSPORTQ_OUT WHERE "+
                                                    "(APPLICATIONSTATUS != ? or APPLICATIONSTATUS is null) AND PROCESSINGSTATUS = ? AND SERVICE = ? AND (TRANSPORTSTATUS = ? or TRANSPORTSTATUS = ?)";

  private final String SELECT_SQL_RECORD_ID = "SELECT IDENT_CURRENT('TransportQ_Out')";
  private final String UPDATE_APPLICATION_STATUS = "Update transportQ_Out set applicationstatus = ? where recordId = ?";

  public TransportQOutDAOImpl() {
  }

  public void updateApplicationStatus(TransportQOutDT dt) throws NNDException {
    Connection conn = null;
    PreparedStatement pstmt = null;
    int i = 1;
    try {
      conn = getConnection(NEDSSConstants.MSGOUT);
      pstmt = conn.prepareStatement(UPDATE_APPLICATION_STATUS);
      pstmt.setString(i++, dt.getApplicationStatus());
      pstmt.setLong(i++, dt.getRecordId().longValue());
      pstmt.executeUpdate();
    } catch(Exception e) {
    	logger.fatal("Exception  = "+e.getMessage(), e);
    	throw new NNDException(e.getMessage());
    } finally {
      closeStatement(pstmt);
      releaseConnection(conn);
    }
  }

  /**
   * This method will return a collection of TranportQOutDT objects
   * where the APPLICATONSTATUS IS NOT 'LDF_LOGGED'
   * AND the PROCESSIGNSTATUS = 'done' AND the TRANSPORTSTATUS = 'success' or TRANSPORTSTATUS = 'failure'
   * @return Collection
   */
  public Collection<Object>  selectTransportQOutDone(String service) throws NEDSSSystemException {

   Connection conn = null;
   PreparedStatement pstmt = null;
   ResultSet rs = null;
   ResultSetMetaData rsmd = null;
   ResultSetUtils resultSetUtils = new ResultSetUtils();
   TransportQOutDT dt = new TransportQOutDT();
   int i = 1;
   try {
     conn = getConnection(NEDSSConstants.MSGOUT);
     pstmt = conn.prepareStatement(SELECT_TRANSPORTQOUT_PHINMS_PROCESSED);
     pstmt.setString(i++, NNDConstantUtil.LDF_LOGGED);
     pstmt.setString(i++, "done");
     pstmt.setString(i++, service);
     pstmt.setString(i++, NNDConstantUtil.PHINMS_SUCCESS);
     pstmt.setString(i++, NNDConstantUtil.PHINMS_FAILURE);
     rs = pstmt.executeQuery();
     rsmd = rs.getMetaData();
     ArrayList<Object> pnList = new ArrayList<Object> ();
    ArrayList<Object> reSetList = new ArrayList<Object> ();
    pnList = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(rs, rsmd, dt.getClass(), pnList);
    for(Iterator<Object> anIterator = pnList.iterator(); anIterator.hasNext(); )
    {
        TransportQOutDT outDt = (TransportQOutDT)anIterator.next();
        outDt.setItNew(false);
        outDt.setItDirty(false);
        outDt.setItDelete(false);
        reSetList.add(outDt);
    }

    return reSetList;



   } catch(Exception e) {
	   logger.fatal("service: "+service+" Exception  = "+e.getMessage(), e);
	   throw new NEDSSSystemException(e.getMessage());
   } finally {
     closeResultSet(rs);
     closeStatement(pstmt);
     releaseConnection(conn);
   }
 }

 public Long create(TransportQOutDT dt) throws NEDSSSystemException{
   Long recordId = null;
   try {
       recordId = insertSQLRecord(dt);
   } catch(Exception e) {
	   logger.fatal("Exception  = "+e.getMessage(), e);
     throw new NEDSSSystemException("Error creating record for MsgOut.TransportQOut");
   }
   return recordId;
 }//end of create



 private Long insertSQLRecord(TransportQOutDT dt) throws Exception {
   Connection dbConn = null;

   PreparedStatement pstmt = null;


   try {

         dbConn = getConnection(NEDSSConstants.MSGOUT);

         int i = 1;
         pstmt = (PreparedStatement)dbConn.prepareStatement(INSERT_TRANSPORTQOUT_SQL);

         if(dt.getMessageId() == null)
           pstmt.setNull(i++, Types.VARCHAR);
         else
           pstmt.setString (i++, dt.getMessageId());
         pstmt.setBinaryStream(i++, new ByteArrayInputStream(dt.getPayloadContent().getBytes("utf-8")),dt.getPayloadContent().length());
         if(dt.getProcessingStatus() == null)
           pstmt.setNull(i++, Types.VARCHAR);
         else
           pstmt.setString (i++, dt.getProcessingStatus());
         if(dt.getRouteInfo() == null)
          pstmt.setNull(i++, Types.VARCHAR);
         else
           pstmt.setString (i++, dt.getRouteInfo());
         if(dt.getService() == null)
          pstmt.setNull(i++, Types.VARCHAR);
         else
          pstmt.setString (i++, dt.getService());
         if(dt.getAction() == null)
          pstmt.setNull(i++, Types.VARCHAR);
         else
          pstmt.setString (i++, dt.getAction());
         if(dt.getPriority() == null)
          pstmt.setNull(i++, Types.VARCHAR);
         else
          pstmt.setInt (i++, dt.getPriority().intValue());
         if(dt.getEncryption() == null)
          pstmt.setNull(i++, Types.VARCHAR);
         else
          pstmt.setString (i++, dt.getEncryption());
         if(dt.getSignature() == null)
          pstmt.setNull(i++, Types.VARCHAR);
         else
          pstmt.setString (i++, dt.getSignature());
         if(dt.getPublicKeyLdapAddress() == null)
          pstmt.setNull(i++,Types.VARCHAR);
         else
          pstmt.setString (i++, dt.getPublicKeyLdapAddress());
         if(dt.getPublicKeyLdapBaseDN() == null)
          pstmt.setNull(i++, Types.VARCHAR);
         else
          pstmt.setString (i++, dt.getPublicKeyLdapBaseDN());
         if(dt.getPublicKeyLdapDN() == null)
          pstmt.setNull(i++, Types.VARCHAR);
         else
          pstmt.setString (i++, dt.getPublicKeyLdapDN());
         if(dt.getCertificateURL() == null)
          pstmt.setNull(i++, Types.VARCHAR);
         else
          pstmt.setString (i++, dt.getCertificateURL());
         // set the destinationFilename
         if(dt.getDestinationFilename() == null)
          pstmt.setNull(i++, Types.VARCHAR);
         else
          pstmt.setString (i++, dt.getDestinationFilename());
         // set the messageRecipient
         if(dt.getMessageRecipient() == null)
          pstmt.setNull(i++, Types.VARCHAR);
         else
          pstmt.setString (i++, dt.getMessageRecipient());
         /*
          * This was changed from executeQuery to executeUpdate 
          * upon  changing the SQL Server driver from 2000 to 2005
          * This resolves the defect 16881 - SC has NBS1.2HF2 code base 
          * and that is the reason it was throwing the exception with 
          * SQL 2005 drivers.   
          */
          pstmt.executeUpdate();
          ResultSet rs = pstmt.getGeneratedKeys();
		
  		  if (!rs.next())
		     throw new NEDSSSystemException("Error creating record for MsgOut.TransportQOut, record_id not returned.");
	 	
 		  Long recordId = new Long(rs.getLong(1));
          rs.close();
          
          return recordId;
      } catch (Exception e){
    	 logger.fatal("Exception  = "+e.getMessage(), e);
         throw e;
      } finally {
    	  closeStatement(pstmt);
          releaseConnection(dbConn);
      }//end of final
 }

 private Long getSQLRecordId()throws NEDSSSystemException {
   Connection conn = null;
   PreparedStatement pstmt = null;
   ResultSet rs = null;
   try {
     conn = getConnection(NEDSSConstants.MSGOUT);
     pstmt = conn.prepareStatement(SELECT_SQL_RECORD_ID);
     rs = pstmt.executeQuery();
     boolean b = rs.next();
     return new Long(rs.getLong(1));
   } catch (Exception e) {
	   logger.fatal("Exception  = "+e.getMessage(), e);
     throw new NEDSSSystemException(e.getMessage());
   } finally {
     closeResultSet(rs);
     closeStatement(pstmt);
     releaseConnection(conn);
   }
 }

 


}
