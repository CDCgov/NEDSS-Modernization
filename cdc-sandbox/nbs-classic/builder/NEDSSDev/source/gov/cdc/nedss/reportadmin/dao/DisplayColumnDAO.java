package gov.cdc.nedss.reportadmin.dao;

import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.util.BMPBase;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import org.apache.log4j.Logger;

/**
 * Reads and writes the Display_column table.
 * @author Ed Jenkins
 */
public class DisplayColumnDAO extends BMPBase
{

    /**
     * Logger.
     */
    private static final Logger logger = Logger.getLogger(DisplayColumnDAO.class);

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
        "select max(display_column_uid) + 1 as display_column_uid from nbs_odse..Display_column;",
        "select max(display_column_uid) + 1 as display_column_uid from nbs_odse.Display_column",
    };

    /**
     * Gets all records that belong to a given report.
     */
    private static final String[] GET_ALL =
    {
        "select column_uid from nbs_odse..Display_column where data_source_uid = ? and report_uid = ? order by sequence_nbr;",
        "select column_uid from nbs_odse.Display_column  where data_source_uid = ? and report_uid = ? order by sequence_nbr",
    };

    /**
     * Deletes all records that belong to a given report.
     */
    private static final String[] CLEAR_ALL =
    {
        "delete from nbs_odse..Display_column where data_source_uid = ? and report_uid = ?;",
        "delete from nbs_odse.Display_column  where data_source_uid = ? and report_uid = ?",
    };

    /**
     * Adds records that belong to a given report.
     */
    private static final String[] SET_ALL =
    {
        "insert into nbs_odse..Display_column(display_column_uid, column_uid, data_source_uid, report_uid, sequence_nbr, status_cd, status_time) values (?, ?, ?, ?, ?, ?, ?);",
        "insert into nbs_odse.Display_column (display_column_uid, column_uid, data_source_uid, report_uid, sequence_nbr, status_cd, status_time) values (?, ?, ?, ?, ?, ?, ?)",
    };


    /**
     * Constructor.
     */
    public DisplayColumnDAO()
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
                r = rs.getLong("display_column_uid");
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
        	logger.fatal("Exception = "+ex.getMessage(), ex);
            throw new NEDSSDAOSysException(ex.getMessage());
        }
        // Close the connection.
        finally
        {
            if(rs != null)
            {
                try
                {
                    rs.close();
                }
                catch(Exception ex)
                {
                }
            }
            if(ps != null)
            {
                try
                {
                    ps.close();
                }
                catch(Exception ex)
                {
                }
            }
            if(con != null)
            {
                try
                {
                    con.close();
                }
                catch(Exception ex)
                {
                }
            }
        }
        // Return result.
        return r;
    }

    /**
     * Gets all records that belong to a given report.
     * @param data_source_uid the data source the report belongs to.
     * @param report_uid the UID of the record to view.
     * @return a list of UIDs.
     * @throws NEDSSDAOSysException if an error occurs.
     */
    public ArrayList<Object> getAll(long data_source_uid, long report_uid) throws NEDSSDAOSysException
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
            logger.warn(GET_ALL[SERVER]);
            logger.warn("data_source_uid=" + data_source_uid);
            logger.warn("report_uid=" + report_uid);
            ps = con.prepareStatement(GET_ALL[SERVER], ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ps.setLong(1, data_source_uid);
            ps.setLong(2, report_uid);
            rs = ps.executeQuery();
            while(rs.next())
            {
                long column_uid = rs.getLong("column_uid");
                if(rs.wasNull())
                {
                    continue;
                }
                Long l = new Long(column_uid);
                al.add(l);
            }
        }
        catch(Exception ex)
        {
        	logger.fatal("Exception = "+ex.getMessage(), ex);
            throw new NEDSSDAOSysException(ex.getMessage());
        }
        // Close the connection.
        finally
        {
            if(rs != null)
            {
                try
                {
                    rs.close();
                }
                catch(Exception ex)
                {
                }
            }
            if(ps != null)
            {
                try
                {
                    ps.close();
                }
                catch(Exception ex)
                {
                }
            }
            if(con != null)
            {
                try
                {
                    con.close();
                }
                catch(Exception ex)
                {
                }
            }
        }
        // Return result.
        return al;
    }

    /**
     * Deletes all records that belong to a given report.
     * @param data_source_uid the data source the report belongs to.
     * @param report_uid the UID of the record to view.
     * @throws NEDSSDAOSysException if an error occurs.
     */
    public void clearAll(long data_source_uid, long report_uid) throws NEDSSDAOSysException
    {
        // Create temp variables.
        Connection con = null;
        PreparedStatement ps = null;
        // Execute the command and package the data.
        try
        {
            con = this.getConnection();
            logger.warn(CLEAR_ALL[SERVER]);
            logger.warn("data_source_uid=" + data_source_uid);
            logger.warn("report_uid=" + report_uid);
            ps = con.prepareStatement(CLEAR_ALL[SERVER], ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ps.setLong(1, data_source_uid);
            ps.setLong(2, report_uid);
            ps.executeUpdate();
        }
        catch(Exception ex)
        {
        	logger.fatal("Exception = "+ex.getMessage(), ex);
            throw new NEDSSDAOSysException(ex.getMessage());
        }
        // Close the connection.
        finally
        {
            if(ps != null)
            {
                try
                {
                    ps.close();
                }
                catch(Exception ex)
                {
                }
            }
            if(con != null)
            {
                try
                {
                    con.close();
                }
                catch(Exception ex)
                {
                }
            }
        }
    }

    /**
     * Adds records that belong to a given report.
     * Does nothing if the list is empty.
     * @param data_source_uid the data source the report belongs to.
     * @param report_uid the UID of the record to view.
     * @param alColumnUID a list of column_uid values.
     * @throws NEDSSDAOSysException if an error occurs.
     */
    public void setAll(long data_source_uid, long report_uid, ArrayList<Object> alColumnUID) throws NEDSSDAOSysException
    {
        // Verify parameters.
        if(alColumnUID == null)
        {
            throw new NEDSSDAOSysException("alColumnUID is null");
        }
        if(alColumnUID.size() == 0)
        {
            return;
        }
        // Create temp variables.
        Connection con = null;
        PreparedStatement ps = null;
        // Execute the command and package the data.
        try
        {
            long display_column_uid = 0L;
            long column_uid = 0L;
            int sequence_nbr = 0;
            Date d = new Date();
            long l = d.getTime();
            Timestamp status_time = new Timestamp(l);
            con = this.getConnection();
            logger.warn(SET_ALL[SERVER]);
            logger.warn("data_source_uid=" + data_source_uid);
            logger.warn("report_uid=" + report_uid);
            ps = con.prepareStatement(SET_ALL[SERVER], ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
           Iterator<Object>  i = alColumnUID.iterator();
            while(i.hasNext())
            {
                display_column_uid = this.getNextUID();
                Long L = (Long)i.next();
                column_uid = L.longValue();
                sequence_nbr++;
                ps.clearParameters();
                ps.setLong(1, display_column_uid);
                ps.setLong(2, column_uid);
                ps.setLong(3, data_source_uid);
                ps.setLong(4, report_uid);
                ps.setInt(5, sequence_nbr);
                ps.setString(6, "A");
                ps.setTimestamp(7, status_time);
                ps.executeUpdate();
            }
        }
        catch(Exception ex)
        {
        	logger.fatal("Exception = "+ex.getMessage(), ex);
            throw new NEDSSDAOSysException(ex.getMessage());
        }
        // Close the connection.
        finally
        {
            if(ps != null)
            {
                try
                {
                    ps.close();
                }
                catch(Exception ex)
                {
                }
            }
            if(con != null)
            {
                try
                {
                    con.close();
                }
                catch(Exception ex)
                {
                }
            }
        }
    }

}
