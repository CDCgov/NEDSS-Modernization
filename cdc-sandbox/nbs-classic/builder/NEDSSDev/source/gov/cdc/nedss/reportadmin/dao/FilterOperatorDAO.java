package gov.cdc.nedss.reportadmin.dao;

import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.reportadmin.dt.FilterOperatorDT;
import gov.cdc.nedss.util.BMPBase;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;

/**
 * Reads and writes the Filter_Operator table.
 * @author Ed Jenkins
 */
public class FilterOperatorDAO extends BMPBase
{

    /**
     * Logger.
     */
    private static final Logger logger = Logger.getLogger(FilterOperatorDAO.class);

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
     * Gets the next available UID.
     */
    private static final String[] GET_NEXT_UID =
    {
        "select max(filter_operator_uid) + 1 as filter_operator_uid from nbs_odse..Filter_Operator;",
        "select max(filter_operator_uid) + 1 as filter_operator_uid from nbs_odse.Filter_Operator",
    };

    /**
     * Views a record.
     */
    private static final String[] VIEW =
    {
        "select filter_operator_uid, filter_operator_code, filter_operator_desc, status_cd, status_time from nbs_odse..Filter_Operator where filter_operator_uid = ?;",
        "select filter_operator_uid, filter_operator_code, filter_operator_desc, status_cd, status_time from nbs_odse.Filter_Operator  where filter_operator_uid = ?",
    };

    /**
     * Adds a record.
     */
    private static final String[] ADD =
    {
        "insert into nbs_odse..Filter_Operator(filter_operator_uid, filter_operator_code, filter_operator_desc, status_cd, status_time) values (?, ?, ?, ?, ?);",
        "insert into nbs_odse.Filter_Operator (filter_operator_uid, filter_operator_code, filter_operator_desc, status_cd, status_time) values (?, ?, ?, ?, ?)",
    };

    /**
     * Lists all records.
     */
    private static final String[] LIST =
    {
        "select filter_operator_uid, filter_operator_code, filter_operator_desc, status_cd, status_time from nbs_odse..Filter_Operator order by filter_operator_uid;",
        "select filter_operator_uid, filter_operator_code, filter_operator_desc, status_cd, status_time from nbs_odse.Filter_Operator  order by filter_operator_uid",
    };

    /**
     * Edits a record.
     */
    private static final String[] EDIT =
    {
        "update nbs_odse..Filter_Operator set filter_operator_uid = ?, filter_operator_code = ?, filter_operator_desc = ?, status_cd = ?, status_time = ? where filter_operator_uid = ?;",
        "update nbs_odse.Filter_Operator  set filter_operator_uid = ?, filter_operator_code = ?, filter_operator_desc = ?, status_cd = ?, status_time = ? where filter_operator_uid = ?",
    };

    /**
     * Deletes a record.
     */
    private static final String[] DELETE =
    {
        "delete from nbs_odse..Filter_Operator where filter_operator_uid = ?;",
        "delete from nbs_odse.Filter_Operator  where filter_operator_uid = ?",
    };

    
    /**
     * Constructor.
     */
    public FilterOperatorDAO()
    {
    }

    /**
     * Gets the next available UID.
     * @return a UID.
     * @throws NEDSSDAOSysException if an error occurs.
     */
    public long getNextUID() throws NEDSSDAOSysException
    {
        // Create return variable.
        long r = 0L;
        // Create temp variables.
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        // Execute the command and package the data.
        try
        {
            con = this.getConnection();
            logger.warn(GET_NEXT_UID[SERVER]);
            ps = con.prepareStatement(GET_NEXT_UID[SERVER], ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            rs = ps.executeQuery();
            if(rs.next())
            {
                r = rs.getLong("filter_operator_uid");
                if(rs.wasNull())
                {
                    r = 1L;
                }
            }
            else
            {
                r = 1L;
            }
        }
        catch(Exception ex)
        {
        	logger.fatal("Exception ="+ex.getMessage(), ex);
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
        return r;
    }

    /**
     * Views a record.
     * @param uid the UID of the record to view.
     * @return a record.
     * @throws NEDSSDAOSysException if an error occurs.
     */
    public FilterOperatorDT view(long uid) throws NEDSSDAOSysException
    {
        // Create return variable.
        FilterOperatorDT dt = new FilterOperatorDT();
        // Create temp variables.
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        // Execute the command and package the data.
        try
        {
            con = this.getConnection();
            logger.warn(VIEW[SERVER]);
            logger.warn("filter_operator_uid=" + uid);
            ps = con.prepareStatement(VIEW[SERVER], ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ps.setLong(1, uid);
            rs = ps.executeQuery();
            dt.setFilterOperatorUID(0L);
            if(rs.next())
            {
                long filter_operator_uid = rs.getLong("filter_operator_uid");
                if(!rs.wasNull())
                {
                    dt.setFilterOperatorUID(filter_operator_uid);
                }
                dt.setFilterOperatorCode(rs.getString("filter_operator_code"));
                dt.setFilterOperatorDesc(rs.getString("filter_operator_desc"));
            }
        }
        catch(Exception ex)
        {
        	logger.fatal("Exception ="+ex.getMessage(), ex);
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
        return dt;
    }

    /**
     * Adds a record.
     * @param dt the record to add.
     * @throws NEDSSDAOSysException if an error occurs.
     */
    public void add(FilterOperatorDT dt) throws NEDSSDAOSysException
    {
        // Verify parameters.
        if(dt == null)
        {
            throw new NEDSSDAOSysException("dt is null");
        }
        // Create temp variables.
        Connection con = null;
        PreparedStatement ps = null;
        // Execute the command and package the data.
        try
        {
            Date d = new Date();
            long l = d.getTime();
            Timestamp status_time = new Timestamp(l);
            con = this.getConnection();
            logger.warn(ADD[SERVER]);
            ps = con.prepareStatement(ADD[SERVER], ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ps.setLong(1, dt.getFilterOperatorUID());
            ps.setString(2, dt.getFilterOperatorCode());
            ps.setString(3, dt.getFilterOperatorDesc());
            ps.setString(4, "A");
            ps.setTimestamp(5, status_time);
            ps.executeUpdate();
        }
        catch(Exception ex)
        {
        	logger.fatal("Exception ="+ex.getMessage(), ex);
            throw new NEDSSDAOSysException(ex.getMessage());
        }
        // Close the connection.
        finally
        {
        	closeStatement(ps);
            releaseConnection(con);
        }
    }

    /**
     * Lists all records.
     * @return a list of records.
     * @throws NEDSSDAOSysException if an error occurs.
     */
    public ArrayList<Object> list() throws NEDSSDAOSysException
    {
        // Create return variable.
        ArrayList<Object> al = new ArrayList<Object> ();
        // Create temp variables.
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        // Execute the command and package the data.
        try
        {
            con = this.getConnection();
            logger.warn(LIST[SERVER]);
            ps = con.prepareStatement(LIST[SERVER], ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            rs = ps.executeQuery();
            while(rs.next())
            {
                FilterOperatorDT dt = new FilterOperatorDT();
                long filter_operator_uid = rs.getLong("filter_operator_uid");
                if(!rs.wasNull())
                {
                    dt.setFilterOperatorUID(filter_operator_uid);
                }
                dt.setFilterOperatorCode(rs.getString("filter_operator_code"));
                dt.setFilterOperatorDesc(rs.getString("filter_operator_desc"));
                al.add(dt);
            }
        }
        catch(Exception ex)
        {
        	logger.fatal("Exception ="+ex.getMessage(), ex);
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

    /**
     * Edits a record.
     * @param dt the record to add.
     * @throws NEDSSDAOSysException if an error occurs.
     */
    public void edit(FilterOperatorDT dt) throws NEDSSDAOSysException
    {
        // Verify parameters.
        if(dt == null)
        {
            throw new NEDSSDAOSysException("dt is null");
        }
        // Create temp variables.
        Connection con = null;
        PreparedStatement ps = null;
        // Execute the command and package the data.
        try
        {
            Date d = new Date();
            long l = d.getTime();
            Timestamp status_time = new Timestamp(l);
            con = this.getConnection();
            logger.warn(ADD[SERVER]);
            ps = con.prepareStatement(ADD[SERVER], ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ps.setLong(1, dt.getFilterOperatorUID());
            ps.setString(2, dt.getFilterOperatorCode());
            ps.setString(3, dt.getFilterOperatorDesc());
            ps.setString(4, "A");
            ps.setTimestamp(5, status_time);
            ps.setLong(6, dt.getFilterOperatorUID());
            ps.executeUpdate();
        }
        catch(Exception ex)
        {
        	logger.fatal("Exception ="+ex.getMessage(), ex);
            throw new NEDSSDAOSysException(ex.getMessage());
        }
        // Close the connection.
        finally
        {
        	closeStatement(ps);
            releaseConnection(con);
        }
    }

    /**
     * Deletes a record.
     * @param uid the UID of the record to delete.
     * @throws NEDSSDAOSysException if an error occurs.
     */
    public void delete(long uid) throws NEDSSDAOSysException
    {
        // Create temp variables.
        Connection con = null;
        PreparedStatement ps = null;
        // Execute the command and package the data.
        try
        {
            con = this.getConnection();
            logger.warn(DELETE[SERVER]);
            logger.warn("filter_operator_uid=" + uid);
            ps = con.prepareStatement(DELETE[SERVER], ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ps.setLong(1, uid);
            ps.executeUpdate();
        }
        catch(Exception ex)
        {
        	logger.fatal("Exception ="+ex.getMessage(), ex);
            throw new NEDSSDAOSysException(ex.getMessage());
        }
        // Close the connection.
        finally
        {
        	closeStatement(ps);
            releaseConnection(con);
        }
    }

}
