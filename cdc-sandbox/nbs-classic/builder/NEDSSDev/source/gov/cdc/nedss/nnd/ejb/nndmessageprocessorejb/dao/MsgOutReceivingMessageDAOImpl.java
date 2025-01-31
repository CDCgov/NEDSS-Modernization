package gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.dao;

import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.nnd.helper.*;
import gov.cdc.nedss.nnd.util.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.systemservice.uidgenerator.*;

import java.math.BigDecimal;
import java.sql.*;
import java.util.*;
import javax.ejb.CreateException;
import javax.ejb.DuplicateKeyException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


public class MsgOutReceivingMessageDAOImpl
    extends BMPBase
{

    static final LogUtils logger = new LogUtils(MsgOutReceivingMessageDAOImpl.class.getName());
    private static final String INSERT =
        "INSERT INTO MsgOut_Receiving_Message ( " +
         "receiving_facility_entity_uid, " + "message_uid, " +
         "status_cd, " + "status_time " + " ) VALUES ( ?, ?, ?, ? )";

    public MsgOutReceivingMessageDAOImpl()
                                  throws NEDSSDAOSysException
    {
    }

    public long create(Object obj)
                throws NEDSSDAOSysException
    {

        MsgOutReceivingMessageDT dt = (MsgOutReceivingMessageDT)obj;

        if (dt == null)
            throw new NEDSSDAOSysException("Error: try to create null MsgOutReceivingMessageDT object.");

        long uid = insertMsgOutReceivingMessage((MsgOutReceivingMessageDT)obj);

        return uid;
    }

    private long insertMsgOutReceivingMessage(MsgOutReceivingMessageDT dt)
                                       throws NEDSSDAOSysException
    {

        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        UidGeneratorHelper uidGen = null;
        int resultCount = 0;

        try
        {
            dbConnection = getConnection(NEDSSConstants.MSGOUT);
            preparedStmt = dbConnection.prepareStatement(INSERT);

            int i = 1;
            preparedStmt.setLong(i++,
                                 dt.getReceivingFacilityEntityUid().longValue());
            preparedStmt.setLong(i++, dt.getMessageUid().longValue());
            preparedStmt.setString(i++, dt.getStatusCd());

            if (dt.getStatusTime() == null)
                preparedStmt.setTimestamp(i++,
                                          new Timestamp(new java.util.Date().getTime()));
            else
                preparedStmt.setTimestamp(i++, dt.getStatusTime());

            preparedStmt.executeUpdate();

            return dt.getMessageUid().longValue();
        }
        catch (SQLException sqlex)
        {
            logger.fatal("SQLException while inserting", sqlex);
            throw new NEDSSDAOSysException("Insert table failed");
        }
        catch (Exception ex)
        {
            throw new NEDSSDAOSysException("Insert into table Failed");
        }
        finally
        {

            // closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
    } //end of insert
}