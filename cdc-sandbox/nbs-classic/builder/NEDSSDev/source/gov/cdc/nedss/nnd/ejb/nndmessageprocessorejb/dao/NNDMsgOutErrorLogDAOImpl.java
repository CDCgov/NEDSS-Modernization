//Source file: C:\\rational_rose_dev\\source\\gov\\cdc\\nedss\\nnd\\ejb\\nndmessageprocessorejb\\dao\\NNDMsgOutErrorLogDAOImpl.java

package gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.dao;

import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.nnd.helper.MsgOutErrorLogDT;
import gov.cdc.nedss.util.BMPBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.ResultSetUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class NNDMsgOutErrorLogDAOImpl
    extends BMPBase {

  /**
   * @roseuid 3E9C374102EA
   */
  public NNDMsgOutErrorLogDAOImpl() {

  }

 
  public static final String SELECT_PROCESSED_LOG_SQL =
      "SELECT message_uid MessageUid, " +
      "error_message_txt ErrorMessageTxt, record_status_cd RecordStatusCd, " +
      " record_status_time RecordStatusTime, status_cd StatusCd, " +
      " status_time StatusTime, notification_local_uid NotificationLocalId, " +
      " processed_log ProcessedLog " +
      " FROM msgout_error_log " +
      " WHERE processed_log = 'N'";

  public static final String UPDATE_PROCESSED_LOG_SQL =
      "UPDATE msgout_error_log " +
      " SET processed_log = ? WHERE message_uid = ?";

  //for logging
  private static final LogUtils logger = new LogUtils(NNDMsgOutErrorLogDAOImpl.class.
      getName());

  public Collection<Object>  getMsgOutStatus() throws NEDSSSystemException {

    Connection dbConnection = null;
    PreparedStatement preparedStmt = null;
    ResultSet resultSet = null;
    ResultSetMetaData resultSetMetaData = null;
    ResultSetUtils resultSetUtils = new ResultSetUtils();
    List<Object> pList = new ArrayList<Object> ();
    MsgOutErrorLogDT msgOutErrorLogDT = new MsgOutErrorLogDT();
    Collection<Object>  msgOutErrorLogDTColl = new ArrayList<Object> ();

    String sqlStmt = "";
   
      sqlStmt = SELECT_PROCESSED_LOG_SQL;
   
    /**
     * Selects all the values from msgout_error_log table
     */
    try {
      dbConnection = getConnection(NEDSSConstants.MSGOUT);
      preparedStmt = dbConnection.prepareStatement(sqlStmt);
      resultSet = preparedStmt.executeQuery();
      resultSetMetaData = resultSet.getMetaData();
      pList = (ArrayList<Object> ) resultSetUtils.mapRsToBeanList(resultSet,
          resultSetMetaData, msgOutErrorLogDT.getClass(), pList);

      if (pList != null) {
        for (Iterator<Object> anIterator = pList.iterator(); anIterator.hasNext(); ) {
          msgOutErrorLogDT = (MsgOutErrorLogDT) anIterator.next();
          msgOutErrorLogDT.setItNew(false);
          msgOutErrorLogDT.setItDirty(false);
          msgOutErrorLogDT.setItDelete(false);
          msgOutErrorLogDTColl.add(msgOutErrorLogDT);

          logger.info("msgOutErrorLogDT.getMessageUid() - : " +
                      msgOutErrorLogDT.getMessageUid());

          logger.info("msgOutErrorLogDT.getErrorMessageTxt() - : " +
                      msgOutErrorLogDT.getErrorMessageTxt());

          logger.info("msgOutErrorLogDT.getRecordStatusCd() - : " +
                      msgOutErrorLogDT.getRecordStatusCd());

          logger.info("msgOutErrorLogDT.getRecordStatusTime() - : " +
                      msgOutErrorLogDT.getRecordStatusTime());

          logger.info("msgOutErrorLogDT.getStatusCd() - : " +
                      msgOutErrorLogDT.getStatusCd());

          logger.info("msgOutErrorLogDT.getStatusTime() - : " +
                      msgOutErrorLogDT.getStatusTime());

          logger.info("msgOutErrorLogDT.getNotificationLocalId()- : " +
                      msgOutErrorLogDT.getNotificationLocalId());

          logger.info("msgOutErrorLogDT.getProcessedLog() - : " +
                      msgOutErrorLogDT.getProcessedLog());
        } //end of for

      } //end of if
    }
    catch (SQLException sex) {
      logger.fatal(
          "SQLException while selecting msgout_error_log;" +
          " messageUid = " + msgOutErrorLogDT.getMessageUid(), sex);
      throw new NEDSSSystemException(sex.getMessage());
    }
    catch (Exception ex) {
      logger.fatal(
          "Exception while selecting msgout_error_log; messageUid =" +
          msgOutErrorLogDT.getMessageUid(), ex);
      throw new NEDSSSystemException(ex.getMessage());
    }
    finally {
      closeResultSet(resultSet);
      closeStatement(preparedStmt);
      releaseConnection(dbConnection);
    }

    return msgOutErrorLogDTColl;
  }

  public void updateProcessedLog(MsgOutErrorLogDT dt) throws
      NEDSSSystemException {

    String sqlStmt = "";
   
      sqlStmt = UPDATE_PROCESSED_LOG_SQL;
  

    Connection dbConnection = null;
    PreparedStatement preparedStmt = null;

    int resultCount = 0;

    try {
      dbConnection = getConnection(NEDSSConstants.MSGOUT);
      preparedStmt = dbConnection.prepareStatement(sqlStmt);

      preparedStmt.setString(1, dt.getProcessedLog());
      preparedStmt.setLong(2, dt.getMessageUid().longValue());

      resultCount = preparedStmt.executeUpdate();

    }
    catch (SQLException sqlex) {
    	logger.fatal("SQLException while updating"+sqlex.getMessage(), sqlex);
    	throw new NEDSSSystemException("Update nbs_msgoute.msgout_error_log table failed");
    }
    catch (Exception ex) {
    	logger.fatal("Exception  = "+ex.getMessage(), ex);
    	throw new NEDSSSystemException("Update into nbs_msgoute.msgout_error_log table Failed");
    }

    finally {
      closeStatement(preparedStmt);
      releaseConnection(dbConnection);
    }
  } //end of update

}
