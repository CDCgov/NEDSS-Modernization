//Source file: C:\\Rational_development\\gov\\cdc\\nedss\\helpers\\UidGeneratorHelper.java

package gov.cdc.nedss.nnd.helper;

import javax.naming.*;
import javax.rmi.*;
import javax.ejb.*;
import java.util.*;
import java.sql.*;

import gov.cdc.nedss.util.*;

public class MsgOutUidGeneratorHelper extends BMPBase
{
   private static Map<Object,Object> uidTable = Collections.synchronizedMap(new HashMap<Object,Object>());
   private StringUtils parser = new StringUtils();
   private short cacheCount = 1000;  //The size of each uid batch in the uid tracker
   private int tryCount = 5;
   private final String GetMaxMsgOutUid = "select max( message_uid)  from MsgOut_Receiving_Message " ;

   protected class UidTracker
   {
      long currentUID = 0;
      long maxUID = 0;
      String uidPrefixCd = null;
      String uidSuffixCd = null;
   }

   //For logging
   static final LogUtils logger = new LogUtils(MsgOutUidGeneratorHelper.class.getName());
   /**
    * @throws java.lang.Exception
    * @roseuid 3C0FEB3A03D6
    */
   public MsgOutUidGeneratorHelper() throws Exception
   {
   }


   /**
    * @param theClass
    * @return java.lang.String
    * @throws java.lang.Exception
    * @roseuid 3C0FEB3A021D
    */
   public String getMsgOutID(java.lang.String theClass) throws Exception
   {
    String strID = null;
    long msgOutUid;

    try
    {
      UidTracker tracker = (UidTracker)uidTable.get(theClass);

      if(tracker == null)
      {
          msgOutUid = getMaxMsgOutUid();

          //Initialize a key/value pair in the map
          for(int j =1; j <= tryCount; j++)
          {
              Connection dbConnection = null;
              CallableStatement callableStmt = null;

              try
              {
                  tracker = new UidTracker();

                  //Retrieves a range and sets tracker's value
                  logger.debug("Prepare the stored procedure for " + theClass);
                  dbConnection = getConnection(NEDSSConstants.MSGOUT);
                  callableStmt = dbConnection.prepareCall("{call MsgOut_GetUid(?, ?, ?, ?, ?, ?, ?)}");

                  logger.info("Register the output parameters for " + theClass);
                  int i = 1;

                  callableStmt.setString(i++, theClass);
                  callableStmt.setShort(i++, cacheCount);

                  callableStmt.registerOutParameter(i++, java.sql.Types.VARCHAR);
                  callableStmt.registerOutParameter(i++, java.sql.Types.VARCHAR);
                  callableStmt.registerOutParameter(i++, java.sql.Types.VARCHAR);
                  callableStmt.registerOutParameter(i++, java.sql.Types.VARCHAR);
                  callableStmt.registerOutParameter(i++, java.sql.Types.VARCHAR);

                  logger.info("Execute the stored procedure for " + theClass);
                  boolean result = callableStmt.execute();

                  logger.info("Retrieve and assign the output parameters for " + theClass);

                  tracker.currentUID = parser.stringToLong(callableStmt.getString(3).trim()).longValue();
                  tracker.maxUID = parser.stringToLong(callableStmt.getString(4).trim()).longValue();
                  logger.debug("current UID is: " + tracker.currentUID );
                  if ( tracker.currentUID <= msgOutUid )
		  {
                      logger.error("Error getting UIDS : " + tracker.currentUID + " : " + msgOutUid);
                      continue;

                  }
                  //Creates a new record for the uid in the map
                  uidTable.put(theClass, tracker);

                  break;
              }
              catch(Exception ex)
              {
                  logger.fatal("Error while initializing the NBS UID tracker for: " + theClass + ", try again...");
                  if(j == tryCount)
                  {
                      logger.fatal("Unable to initialize a NBS UID tracker for " + theClass);
                      throw new Exception(ex.toString());

                  }
                  else
                  {
                      logger.error("Trying again :" + j);
                      closeCallableStatement(callableStmt);
                      releaseConnection(dbConnection);
                      continue;
                  }
              }
              finally
              {
                  closeCallableStatement(callableStmt);
                  releaseConnection(dbConnection);

              }
          }
      }
      if(tracker.currentUID == tracker.maxUID)
      {
          //Recharge the uid tracker in the map
          for(int j = 1; j <= tryCount; j++)
          {

              msgOutUid = getMaxMsgOutUid();
              CallableStatement callableStmt = null;
              Connection dbConnection = null;
              try
              {
                  //Retrieves a range and sets tracker's value
                  logger.info("Prepare the stored procedure " + theClass);
                  dbConnection = getConnection(NEDSSConstants.MSGOUT);
                  callableStmt = dbConnection.prepareCall("{call MsgOut_GetUid(?, ?, ?, ?, ?, ?, ?)}");

                  logger.info("Register the output parameters for " + theClass);
                  int i = 1;

                  callableStmt.setString(i++, theClass);
                  callableStmt.setShort(i++, cacheCount);

                  callableStmt.registerOutParameter(i++, java.sql.Types.VARCHAR);
                  callableStmt.registerOutParameter(i++, java.sql.Types.VARCHAR);
                  callableStmt.registerOutParameter(i++, java.sql.Types.VARCHAR);
                  callableStmt.registerOutParameter(i++, java.sql.Types.VARCHAR);
                  callableStmt.registerOutParameter(i++, java.sql.Types.VARCHAR);

                  logger.info("Execute the stored procedure for " + theClass);
                  boolean result = callableStmt.execute();

                  logger.info("Retrieve and assign the output parameters for " + theClass);

                  tracker.currentUID = parser.stringToLong(callableStmt.getString(3).trim()).longValue();
                  tracker.maxUID = parser.stringToLong(callableStmt.getString(4).trim()).longValue();
                  if ( tracker.currentUID <= msgOutUid)
		  {
                      logger.debug("Error getting UIDS : " + tracker.currentUID + " : " + msgOutUid);
                      continue;
                  }
                 
                  logger.info("Recharged batch getMsgOutID() for " + theClass);
                  logger.info("The MsgOut uid range is from " + tracker.currentUID + " to " + tracker.maxUID);
                  break;
              }
              catch(Exception ex)
              {
                  logger.fatal("Error while recharging the MsgOut UID for: " + theClass + ", try again...", ex);
                  if(j == tryCount)
                  {
                      logger.fatal("Unable to recharge the MsgOut UID tracker for " + theClass);
                      throw new Exception(ex.toString());
                  }
                  else
                  {
                      closeCallableStatement(callableStmt);
                      releaseConnection(dbConnection);
                      continue;
                  }
              }
              finally
              {
                  closeCallableStatement(callableStmt);
                  releaseConnection(dbConnection);

              }
          }
      }

      //Returns a uid as a String
      strID = new Long(tracker.currentUID++).toString();
    }
    catch( Exception e )
    {
       logger.error("Error while attempting to provide a MsgOut uid", e);
       throw new Exception( e.toString() );
    }

    return strID;
   }

   /**
    * @param theClass
    * @return Long
    * @throws java.lang.Exception
    * @roseuid 3C0FEB3A0304
    */
   public Long getMsgOutIDLong(java.lang.String theClass) throws Exception
   {
		return Long.valueOf( getMsgOutID( theClass ) );
   }


   private long getMaxMsgOutUid() throws Exception
   {
        long maxAssignedUid ;
        Connection dbConnection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStmt = null;

        try
        {
            dbConnection = getConnection(NEDSSConstants.MSGOUT);
            preparedStmt = dbConnection.prepareStatement(GetMaxMsgOutUid);
            resultSet = preparedStmt.executeQuery();
            resultSet.next();
            maxAssignedUid = resultSet.getLong(1);
        }
         catch(Exception ex)
        {
            logger.fatal("Error getting max mesOutUID. ", ex);
            throw new Exception( ex.getMessage());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
        return maxAssignedUid;

   }

}
