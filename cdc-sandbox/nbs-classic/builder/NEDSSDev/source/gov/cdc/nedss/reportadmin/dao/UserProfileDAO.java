package gov.cdc.nedss.reportadmin.dao;

import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.reportadmin.dt.UserProfileDT;
import gov.cdc.nedss.util.BMPBase;
import gov.cdc.nedss.util.HTMLEncoder;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.apache.log4j.Logger;

/**
 * Reads the USER_PROFILE table.
 * @author Ed Jenkins
 */
public class UserProfileDAO extends BMPBase
{

    /**
     * Logger.
     */
    private static final Logger logger = Logger.getLogger(UserProfileDAO.class);

    /**
     * Gets options from NEDSS.properties.
     */
    private static final PropertyUtil np = PropertyUtil.getInstance();

    /**
     * MS SQL Server.
     */
    private static final int MSSQL = 0;

    /**
     * Oracle.
     */
    private static final int ORA = 1;

    /**
     * Server type.
     */
    private static int SERVER = 0;

    /**
     * Lists all records.
     */
    private static final String[] LIST =
    {
        "select NEDSS_ENTRY_ID, FIRST_NM, LAST_UPD_TIME, LAST_NM from nbs_odse..USER_PROFILE order by FIRST_NM, LAST_NM;",
        "select NEDSS_ENTRY_ID, FIRST_NM, LAST_UPD_TIME, LAST_NM from nbs_odse.USER_PROFILE  order by FIRST_NM, LAST_NM",
    };

  

    /**
     * Constructor.
     */
    public UserProfileDAO()
    {
    }

    /**
     * Lists all records.
     * @return a list of records.
     * @throws NEDSSDAOSysException if an error occurs.
     */
    public ArrayList<Object>  list() throws NEDSSDAOSysException
    {
        // Create return variable.
        ArrayList<Object>  al = new ArrayList<Object> ();
        // Create temp variables.
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        // Execute the command and package the data.
        try
        {
            con = this.getConnection();
            //logger.warn(LIST[SERVER]);
            ps = con.prepareStatement(LIST[SERVER], ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            rs = ps.executeQuery();
            while(rs.next())
            {
                UserProfileDT dt = new UserProfileDT();
                dt.setNEDSS_ENTRY_ID(0L);
                long NEDSS_ENTRY_ID = rs.getLong("NEDSS_ENTRY_ID");
                if(!rs.wasNull())
                {
                    dt.setNEDSS_ENTRY_ID(NEDSS_ENTRY_ID);
                }
                dt.setFIRST_NM(HTMLEncoder.encodeHtml(rs.getString("FIRST_NM")));
                Timestamp t = rs.getTimestamp("LAST_UPD_TIME");
                if(!rs.wasNull())
                {
                    long l = t.getTime();
                    dt.setLAST_UPD_TIME(l);
                }
                dt.setLAST_NM(HTMLEncoder.encodeHtml(rs.getString("LAST_NM")));
                al.add(dt);
                
                }
        }
        catch(Exception ex)
        {
        	logger.fatal("Exception  = "+ex.getMessage(), ex);
            throw new NEDSSDAOSysException(ex.getMessage());
        }
        // Close the connection.
        finally
        {
        	closeResultSet(rs);
			closeStatement(ps);
			releaseConnection(con);
        }
        // Return result.
        return al;
    }

    public ArrayList<Object>  getUsersWithValidEmailLst() throws NEDSSDAOSysException
    {
        // Create return variable.
        ArrayList<Object>  al = new ArrayList<Object> ();
        // Create temp variables.
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        // Execute the command and package the data.
        try
        {
            con = this.getConnection();
            ps = con.prepareStatement("select distinct up.NEDSS_ENTRY_ID, up.FIRST_NM, up.LAST_UPD_TIME, up.LAST_NM "+ 
            		"from USER_PROFILE up, user_email ue where up.nedss_entry_id= ue.nedss_entry_id order by up.FIRST_NM, LAST_NM");
            rs = ps.executeQuery();
            while(rs.next())
            {
                UserProfileDT dt = new UserProfileDT();
                dt.setNEDSS_ENTRY_ID(0L);
                long NEDSS_ENTRY_ID = rs.getLong("NEDSS_ENTRY_ID");
                if(!rs.wasNull())
                {
                    dt.setNEDSS_ENTRY_ID(NEDSS_ENTRY_ID);
                }
                dt.setFIRST_NM(rs.getString("FIRST_NM"));
                Timestamp t = rs.getTimestamp("LAST_UPD_TIME");
                if(!rs.wasNull())
                {
                    long l = t.getTime();
                    dt.setLAST_UPD_TIME(l);
                }
                dt.setLAST_NM(rs.getString("LAST_NM"));
                al.add(dt);
            }
        }
        catch(Exception ex)
        {
        	logger.fatal("Exception  = "+ex.getMessage(), ex);
            throw new NEDSSDAOSysException(ex.getMessage());
        }
        // Close the connection.
        finally
        {
        	closeResultSet(rs);
			closeStatement(ps);
			releaseConnection(con);
        }
        // Return result.
        return al;
    }

    public ArrayList<Object>  getUsersWithActiveAlerts() throws NEDSSDAOSysException
    {
        // Create return variable.
        ArrayList<Object>  al = new ArrayList<Object> ();
        // Create temp variables.
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        // Execute the command and package the data.
        try
        {
            con = this.getConnection();
            ps = con.prepareStatement("select distinct user_profile.NEDSS_ENTRY_ID  from USER_PROFILE user_profile, Alert alert, alert_user alert_user "+
            		"where user_profile.nedss_entry_id= alert_user.nedss_entry_id and alert_user.alert_uid=alert.alert_uid and alert.record_status_cd='ACTIVE Order by user_profile.Nedss_entry_id");
            rs = ps.executeQuery();
            while(rs.next())
            {
                UserProfileDT dt = new UserProfileDT();
                dt.setNEDSS_ENTRY_ID(0L);
                long NEDSS_ENTRY_ID = rs.getLong("NEDSS_ENTRY_ID");
                if(!rs.wasNull())
                {
                    dt.setNEDSS_ENTRY_ID(NEDSS_ENTRY_ID);
                }
                dt.setFIRST_NM(rs.getString("FIRST_NM"));
                Timestamp t = rs.getTimestamp("LAST_UPD_TIME");
                if(!rs.wasNull())
                {
                    long l = t.getTime();
                    dt.setLAST_UPD_TIME(l);
                }
                dt.setLAST_NM(rs.getString("LAST_NM"));
                al.add(dt);
            }
        }
        catch(Exception ex)
        {
        	logger.fatal("Exception  = "+ex.getMessage(), ex);
            throw new NEDSSDAOSysException(ex.getMessage());
        }
        // Close the connection.
        finally
        {
        	closeResultSet(rs);
			closeStatement(ps);
			releaseConnection(con);
        }
        // Return result.
        return al;
    }

}
