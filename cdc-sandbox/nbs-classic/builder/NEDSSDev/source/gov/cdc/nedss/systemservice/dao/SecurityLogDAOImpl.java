package gov.cdc.nedss.systemservice.dao;


import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.systemservice.nbssecurity.SecurityLogDT;
import gov.cdc.nedss.systemservice.sqlscript.SRTSQLQuery;
import gov.cdc.nedss.util.BMPBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 *  Data access object for security log.
 *  @author Ed Jenkins
 */
public class SecurityLogDAOImpl extends BMPBase
{

    private LogUtils logger = null;

    /**
     *  Default constructor.
     *  @throws NEDSSDAOSysException
     */
    public SecurityLogDAOImpl() throws NEDSSDAOSysException
    {
        logger = new LogUtils(this.getClass().getName());
    }

    /**
     *  Adds a new record to the table.
     *  @param obj a DT representing the record.
     *  @return the record's UID.
     *  @throws NEDSSDAOSysException
     */
    public long create(Object obj) throws NEDSSDAOSysException
    {
    	try{
	        SecurityLogDT dt = (SecurityLogDT)obj;
	        if(dt == null)
	        {
	            throw new NEDSSDAOSysException("Error:  obj is null or is not a SecurityLogDT.");
	        }
	        Long l = insertSecurityLog((SecurityLogDT)obj);
	        return l.longValue();
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSDAOSysException(ex.toString());
    	}
    }

    private Long insertSecurityLog(SecurityLogDT dt) throws NEDSSDAOSysException
    {
        if(dt == null)
        {
            logger.error("dt is null.");
            throw new NEDSSDAOSysException("dt is null.");
        }
        
        logger.error(dt.toString());
        Connection conn = null;
        CallableStatement cs = null;
        PreparedStatement ps = null;
        Timestamp ts = null;
        long uid = 0;
        try
        {
            //  Open a connection to the database.
            conn = getConnection();
            //  Get ready to call the stored procedure to get the next UID to use.
            cs = conn.prepareCall("{call getNextUid_sp(?, ?)}");
            cs.setString(1, NEDSSConstants.SECURITY_LOG);
            cs.registerOutParameter(2, java.sql.Types.VARCHAR);
            //  Call the stored procedure.
            cs.execute();
            //  Get the output.
            String strUID = cs.getString(2);
            String s = strUID.trim();
            uid = Long.parseLong(s);
            //  Add a new record to the security log.
            ps = conn.prepareStatement(SRTSQLQuery.SECURITY_LOG_INSERT);
            ps.setLong(1, uid);
            ps.setString(2, dt.getEventTypeCd());
            if(dt.getEventTime() == null)
            {
                java.util.Date d = new java.util.Date();
                long lngDate = d.getTime();
                ts = new Timestamp(lngDate);
            }
            else
            {
                ts = dt.getEventTime();
            }
            ps.setTimestamp(3, ts);
            ps.setString(4, dt.getSessionId());
            ps.setString(5, dt.getRemoteAddress());
            ps.setLong(6, dt.getNedssEntryId());
            ps.setString(7, dt.getFirstNm());
            ps.setString(8, dt.getLastNm());
            //  To enable logging of the remote host name,
            //  all you have to do is add a new field
            //  to the table and the INSERT query
            //  and uncomment the line below.
            //ps.setString(7, dt.getRemoteHost());
            ps.executeUpdate();
        }
        catch(SQLException sqlex)
        {
            logger.fatal("SQLException while inserting."+sqlex.getMessage(), sqlex);
            throw new NEDSSDAOSysException("Insert into table failed.", sqlex);
        }
        catch(Exception ex)
        {
            logger.fatal("Exception while inserting."+ex.getMessage(), ex);
            throw new NEDSSDAOSysException("Insert into table failed.", ex);
        }
        finally
        {
            //  A backup method, in case of exception.
            String strTime = (ts == null) ? "null" : ts.toString();
            logger.debug("--- SECURITY LOG ENTRY BEGIN ---");
            logger.debug("securityLogUid = " + uid);
            logger.debug("eventTime = " + strTime);
            logger.debug("sessionId = " + dt.getSessionId());
            logger.debug("Nedss Entry Id = " + dt.getNedssEntryId().longValue());
            logger.debug("user = " + dt.getFirstNm()+ " " +dt.getLastNm());
            logger.debug("eventTypeCd = " + dt.getEventTypeCd());
            logger.debug("remoteAddress = " + dt.getRemoteAddress());
            logger.debug("remoteHost = " + dt.getRemoteHost());
            logger.debug("--- SECURITY LOG ENTRY END ---");
            //  Close the connection.
            closeCallableStatement(cs);
            closeStatement(ps);
            releaseConnection(conn);
        }
        //  Return the UID.
        Long UID = new Long(uid);
        return UID;
    }

    /**
     *  Main.
     *  Just used for testing.
     *  @param args command-line arguments.
     */
    public static void main(String[] args)
    {
        final String strTest = "ed.test";
        try
        {
            SecurityLogDT dt = new SecurityLogDT();
            dt.setSessionId(strTest);
            dt.setUserId(strTest);
            dt.setEventTypeCd(strTest);
            dt.setRemoteAddress(strTest);
            dt.setRemoteHost(strTest);
            dt.setItNew(true);
            SecurityLogDAOImpl dao = new SecurityLogDAOImpl();
            dao.create(dt);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

}
