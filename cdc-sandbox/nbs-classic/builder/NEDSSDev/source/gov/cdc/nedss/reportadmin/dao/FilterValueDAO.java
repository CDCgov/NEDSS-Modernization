package gov.cdc.nedss.reportadmin.dao;

import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.reportadmin.dt.FilterValueDT;
import gov.cdc.nedss.util.BMPBase;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;

import org.apache.log4j.Logger;

/**
 * Reads and writes the Filter_value table.
 * @author Ed Jenkins
 */
public class FilterValueDAO extends BMPBase
{

    /**
     * Logger.
     */
    private static final Logger logger = Logger.getLogger(FilterValueDAO.class);

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
        "select max(value_uid) + 1 as value_uid from nbs_odse..Filter_value;",
        "select max(value_uid) + 1 as value_uid from nbs_odse.Filter_value",
    };

    /**
     * Views a record.
     */
    private static final String[] VIEW =
    {
        "select value_uid, report_filter_uid, sequence_nbr, value_type, column_uid, operator, value_txt from nbs_odse..Filter_value where value_uid = ?;",
        "select value_uid, report_filter_uid, sequence_nbr, value_type, column_uid, operator, value_txt from nbs_odse.Filter_value  where value_uid = ?",
    };

    /**
     * Adds a record.
     */
    private static final String[] ADD =
    {
        "insert into nbs_odse..Filter_value(value_uid, report_filter_uid, sequence_nbr, value_type, column_uid, operator, value_txt) values (?, ?, ?, ?, ?, ?, ?);",
        "insert into nbs_odse.Filter_value (value_uid, report_filter_uid, sequence_nbr, value_type, column_uid, operator, value_txt) values (?, ?, ?, ?, ?, ?, ?)",
    };

    /**
     * Lists all records.
     */
    private static final String[] LIST =
    {
        "select value_uid, report_filter_uid, sequence_nbr, value_type, column_uid, operator, value_txt from nbs_odse..Filter_value order by sequence_nbr;",
        "select value_uid, report_filter_uid, sequence_nbr, value_type, column_uid, operator, value_txt from nbs_odse.Filter_value  order by sequence_nbr",
    };

    /**
     * Edits a record.
     */
    private static final String[] EDIT =
    {
        "update nbs_odse..Filter_value set value_uid = ?, report_filter_uid = ?, sequence_nbr = ?, value_type = ?, column_uid = ?, operator = ?, value_txt = ? where value_uid = ?;",
        "update nbs_odse.Filter_value  set value_uid = ?, report_filter_uid = ?, sequence_nbr = ?, value_type = ?, column_uid = ?, operator = ?, value_txt = ? where value_uid = ?",
    };

    /**
     * Deletes a record.
     */
    private static final String[] DELETE =
    {
        "delete from nbs_odse..Filter_value where value_uid = ?;",
        "delete from nbs_odse.Filter_value  where value_uid = ?",
    };

    /**
     * Deletes all records that belong to a given report filter.
     */
    private static final String[] DELETE_ALL =
    {
        "delete from nbs_odse..Filter_value where report_filter_uid = ?;",
        "delete from nbs_odse.Filter_value  where report_filter_uid = ?",
    };

    
    /**
     * Constructor.
     */
    public FilterValueDAO()
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
                r = rs.getLong("value_uid");
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
    public FilterValueDT view(long uid) throws NEDSSDAOSysException
    {
        // Create return variable.
        FilterValueDT dt = new FilterValueDT();
        // Create temp variables.
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        // Execute the command and package the data.
        try
        {
            con = this.getConnection();
            logger.warn(VIEW[SERVER]);
            logger.warn("value_uid=" + uid);
            ps = con.prepareStatement(VIEW[SERVER], ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ps.setLong(1, uid);
            rs = ps.executeQuery();
            dt.setValueUID(0L);
            if(rs.next())
            {
                long value_uid = rs.getLong("value_uid");
                if(!rs.wasNull())
                {
                    dt.setValueUID(value_uid);
                }
                long report_filter_uid = rs.getLong("report_filter_uid");
                if(!rs.wasNull())
                {
                    dt.setReportFilterUID(report_filter_uid);
                }
                int sequence_nbr = rs.getInt("sequence_nbr");
                if(!rs.wasNull())
                {
                    dt.setSequenceNumber(sequence_nbr);
                }
                dt.setValueType(rs.getString("value_type"));
                long column_uid = rs.getLong("column_uid");
                if(!rs.wasNull())
                {
                    dt.setColumnUID(column_uid);
                }
                dt.setOperator(rs.getString("operator"));
                dt.setValueTxt(rs.getString("value_txt"));
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
    public void add(FilterValueDT dt) throws NEDSSDAOSysException
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
            con = this.getConnection();
            logger.warn(ADD[SERVER]);
            ps = con.prepareStatement(ADD[SERVER], ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ps.setLong(1, dt.getValueUID());
            ps.setLong(2, dt.getReportFilterUID());
            if(dt.getSequenceNumber() == 0)
            {
                ps.setNull(3, Types.SMALLINT);
            }
            else
            {
                ps.setInt(3, dt.getSequenceNumber());
            }
            ps.setString(4, dt.getValueType());
            if(dt.getColumnUID() == 0L)
            {
                ps.setNull(5, Types.BIGINT);
            }
            else
            {
                ps.setLong(5, dt.getColumnUID());
            }
            if(dt.getOperator().equalsIgnoreCase(""))
            {
                ps.setNull(6, Types.VARCHAR);
            }
            else
            {
                ps.setString(6, dt.getOperator());
            }
            if(dt.getValueTxt().equalsIgnoreCase(""))
            {
                ps.setNull(7, Types.VARCHAR);
            }
            else
            {
                ps.setString(7, dt.getValueTxt());
            }
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
                FilterValueDT dt = new FilterValueDT();
                dt.setValueUID(0L);
                long value_uid = rs.getLong("value_uid");
                if(!rs.wasNull())
                {
                    dt.setValueUID(value_uid);
                }
                long report_filter_uid = rs.getLong("report_filter_uid");
                if(!rs.wasNull())
                {
                    dt.setReportFilterUID(report_filter_uid);
                }
                int sequence_nbr = rs.getInt("sequence_nbr");
                if(!rs.wasNull())
                {
                    dt.setSequenceNumber(sequence_nbr);
                }
                dt.setValueType(rs.getString("value_type"));
                long column_uid = rs.getLong("column_uid");
                if(!rs.wasNull())
                {
                    dt.setColumnUID(column_uid);
                }
                dt.setOperator(rs.getString("operator"));
                dt.setValueTxt(rs.getString("value_txt"));
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
     * @param dt the record to edit.
     * @throws NEDSSDAOSysException if an error occurs.
     */
    public void edit(FilterValueDT dt) throws NEDSSDAOSysException
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
            con = this.getConnection();
            logger.warn(ADD[SERVER]);
            ps = con.prepareStatement(ADD[SERVER], ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ps.setLong(1, dt.getValueUID());
            ps.setLong(2, dt.getReportFilterUID());
            if(dt.getSequenceNumber() == 0)
            {
                ps.setNull(3, Types.SMALLINT);
            }
            else
            {
                ps.setInt(3, dt.getSequenceNumber());
            }
            ps.setString(4, dt.getValueType());
            if(dt.getColumnUID() == 0L)
            {
                ps.setNull(5, Types.BIGINT);
            }
            else
            {
                ps.setLong(5, dt.getColumnUID());
            }
            if(dt.getOperator().equalsIgnoreCase(""))
            {
                ps.setNull(6, Types.VARCHAR);
            }
            else
            {
                ps.setString(6, dt.getOperator());
            }
            if(dt.getValueTxt().equalsIgnoreCase(""))
            {
                ps.setNull(7, Types.VARCHAR);
            }
            else
            {
                ps.setString(7, dt.getValueTxt());
            }
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
            logger.warn("value_uid=" + uid);
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

    /**
     * Deletes all records that belong to a given report filter.
     * @param uid the UID of the related report filter.
     * @throws NEDSSDAOSysException if an error occurs.
     */
    public void deleteAll(long uid) throws NEDSSDAOSysException
    {
        // Create temp variables.
        Connection con = null;
        PreparedStatement ps = null;
        // Execute the command and package the data.
        try
        {
            con = this.getConnection();
            logger.warn(DELETE_ALL[SERVER]);
            logger.warn("report_filter_uid=" + uid);
            ps = con.prepareStatement(DELETE_ALL[SERVER], ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
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
