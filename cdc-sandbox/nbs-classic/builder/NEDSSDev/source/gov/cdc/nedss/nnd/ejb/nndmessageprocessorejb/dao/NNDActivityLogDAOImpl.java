package gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.dao;

import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.nnd.helper.NNDActivityLogDT;
import gov.cdc.nedss.systemservice.uidgenerator.UidClassCodes;
import gov.cdc.nedss.systemservice.uidgenerator.UidGeneratorHelper;
import gov.cdc.nedss.util.BMPBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NNDActivityLogDAOImpl extends BMPBase
{
 static final LogUtils logger = new LogUtils(NNDActivityLogDAOImpl.class.getName());

    private static final String INSERT
      = "INSERT INTO NND_Activity_log ( "
      + "nnd_activity_log_uid, "
      + "nnd_activity_log_seq, "
      + "error_message_txt, "
      + "local_id, "
      + "record_status_cd, "
      + "record_status_time, "
      + "status_cd, "
      + "status_time, "
      + "service"
      + " ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?)";

   private final String UPDATE_RECORD_STATUS_CD = "update nnd_activity_log set record_status_cd = ? where local_id = ?";


    public NNDActivityLogDAOImpl()
      throws NEDSSSystemException
    {

    }
    public Long create(NNDActivityLogDT dt) {
      if(dt == null)
        throw new NEDSSSystemException("Error: try to create null NNDActivityLogDT object.");
      return insertNNDActivityLog(dt);
    }

    public long create(long uid, Object obj)
      throws NEDSSSystemException
    {
    	try{
	        NNDActivityLogDT dt = (NNDActivityLogDT)obj;
	        if(dt == null)
	           throw new NEDSSSystemException("Error: try to create null NNDActivityLogDT object.");
	        insertNNDActivityLog((NNDActivityLogDT)obj);
	        return uid;
    	}catch(Exception ex){
    		logger.fatal("uid: "+uid+" Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }



    public boolean updateRecordStatusCd(NNDActivityLogDT dt) throws NEDSSSystemException {
      boolean successfulUpdate = true;
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      int numberUpdated = 0;
      try {
        conn = getConnection(NEDSSConstants.ODS);
        pstmt = conn.prepareStatement(UPDATE_RECORD_STATUS_CD);
        int i = 1;
        pstmt.setString(i++, dt.getRecordStatusCd());
        pstmt.setString(i++, dt.getLocalId());
        numberUpdated = pstmt.executeUpdate();
        if(numberUpdated == 0)
          successfulUpdate = false;
        return successfulUpdate;
      } catch(Exception e) {
    	  logger.fatal("Exception  = "+e.getMessage(), e);
        throw new NEDSSSystemException(e.getMessage());
      } finally {
        closeResultSet(rs);
        closeStatement(pstmt);
        releaseConnection(conn);
      }
    }


      private Long insertNNDActivityLog(NNDActivityLogDT dt)
      throws NEDSSSystemException {

        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        long uid = 0;
        UidGeneratorHelper uidGen = null;
        int resultCount = 0;

        try
        {
        dbConnection = getConnection(NEDSSConstants.ODS);

        preparedStmt = dbConnection.prepareStatement(INSERT);
        int i = 1;

	 /**
            * get Uid from MsgOutUidGeneratorHelper and update table
            */
           if(dt.getNndActivityLogUid() == null) {
            uidGen = new UidGeneratorHelper();
            uid = uidGen.getNbsIDLong(UidClassCodes.NBS_CLASS_CODE).longValue();
           } else {
             uid = dt.getNndActivityLogUid().longValue();
           }

		        preparedStmt.setLong(i++, uid);

		        preparedStmt.setInt(i++, dt.getNndActivityLogSeq().intValue());

		        if(dt.getErrorMessageTxt()!=null && dt.getErrorMessageTxt().length()>2000){
		        	preparedStmt.setString(i++, dt.getErrorMessageTxt().substring(0, 2000));
		        }
		        else
		        	preparedStmt.setString(i++, dt.getErrorMessageTxt());

		        preparedStmt.setString(i++, dt.getLocalId());

		        preparedStmt.setString(i++, dt.getRecordStatusCd());

		        preparedStmt.setTimestamp(i++, dt.getRecordStatusTime());

		        preparedStmt.setString(i++, dt.getStatusCd());

		        preparedStmt.setTimestamp(i++, dt.getStatusTime());

		        preparedStmt.setString(i++, dt.getService());

                resultCount = preparedStmt.executeUpdate();

                return new Long(uid);
            }
            catch (SQLException sqlex)
            {
                logger.fatal("SQLException while inserting",
                             sqlex);
                throw new NEDSSSystemException("Insert table failed");
            }
            catch (Exception ex)
            {
                 throw new NEDSSSystemException("Insert into table Failed");
            }

        finally
        {
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
    } //end of insert


}

