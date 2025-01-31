package gov.cdc.nedss.reportadmin.dao;

import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.reportadmin.dt.ReportFilterValidationDT;
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
 * Reads and writes the Report_Filter_Validation table.
 * @author Ed Jenkins
 */
public class ReportFilterValidationDAO extends BMPBase
{

    /**
     * Logger.
     */
    private static final Logger logger = Logger.getLogger(ReportFilterValidationDAO.class);

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
        "select max(report_filter_validation_uid) + 1 as report_filter_validation_uid from nbs_odse..Report_Filter_Validation;",
        "select max(report_filter_validation_uid) + 1 as report_filter_validation_uid from nbs_odse.Report_Filter_Validation",
    };

    /**
     * Views a record.
     */
    private static final String[] VIEW =
    {
        "select report_filter_validation_uid, report_filter_uid, report_filter_ind, status_cd, status_time from nbs_odse..Report_Filter_Validation where report_filter_validation_uid = ?;",
        "select report_filter_validation_uid, report_filter_uid, report_filter_ind, status_cd, status_time from nbs_odse.Report_Filter_Validation  where report_filter_validation_uid = ?",
    };

    /**
     * Adds a record.
     */
    private static final String[] ADD =
    {
        "insert into nbs_odse..Report_Filter_Validation(report_filter_validation_uid, report_filter_uid, report_filter_ind, status_cd, status_time) values (?, ?, ?, ?, ?);",
        "insert into nbs_odse.Report_Filter_Validation (report_filter_validation_uid, report_filter_uid, report_filter_ind, status_cd, status_time) values (?, ?, ?, ?, ?)",
    };

    /**
     * Lists all records.
     */
    private static final String[] LIST =
    {
        "select report_filter_validation_uid, report_filter_uid, report_filter_ind, status_cd, status_time from nbs_odse..Report_Filter_Validation order by report_filter_validation_uid;",
        "select report_filter_validation_uid, report_filter_uid, report_filter_ind, status_cd, status_time from nbs_odse.Report_Filter_Validation  order by report_filter_validation_uid",
    };

    /**
     * Edits a record.
     */
    private static final String[] EDIT =
    {
        "update nbs_odse..Report_Filter_Validation set report_filter_validation_uid = ?, report_filter_uid = ?, report_filter_ind = ?, status_cd = ?, status_time = ? where report_filter_validation_uid = ?;",
        "update nbs_odse.Report_Filter_Validation  set report_filter_validation_uid = ?, report_filter_uid = ?, report_filter_ind = ?, status_cd = ?, status_time = ? where report_filter_validation_uid = ?",
    };

    /**
     * Deletes a record.
     */
    private static final String[] DELETE =
    {
        "delete from nbs_odse..Report_Filter_Validation where report_filter_uid = ?;",
        "delete from nbs_odse.Report_Filter_Validation  where report_filter_uid = ?",
    };

     /**
     * Constructor.
     */
    public ReportFilterValidationDAO()
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
                r = rs.getLong("report_filter_validation_uid");
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
        return r;
    }

    /**
     * Views a record.
     * @param uid the UID of the record to view.
     * @return a record.
     * @throws NEDSSDAOSysException if an error occurs.
     */
    public ReportFilterValidationDT view(long uid) throws NEDSSDAOSysException
    {
        // Create return variable.
        ReportFilterValidationDT dt = new ReportFilterValidationDT();
        // Create temp variables.
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        // Execute the command and package the data.
        try
        {
            con = this.getConnection();
            logger.warn(VIEW[SERVER]);
            logger.warn("report_filter_validation_uid=" + uid);
            ps = con.prepareStatement(VIEW[SERVER], ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ps.setLong(1, uid);
            rs = ps.executeQuery();
            dt.setReportFilterValidationUID(0L);
            if(rs.next())
            {
                long report_filter_validation_uid = rs.getLong("report_filter_validation_uid");
                if(!rs.wasNull())
                {
                    dt.setReportFilterValidationUID(report_filter_validation_uid);
                }
                long report_filter_uid = rs.getLong("report_filter_uid");
                if(!rs.wasNull())
                {
                    dt.setReportFilterUID(report_filter_uid);
                }
                dt.setReportFilterIndicator(rs.getString("report_filter_ind"));
            }
        }
        catch(Exception ex)
        {
        	logger.fatal("uid: "+uid+" Exception  = "+ex.getMessage(), ex);
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
    public void add(ReportFilterValidationDT dt) throws NEDSSDAOSysException
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
            ps.setLong(1, dt.getReportFilterValidationUID());
            ps.setLong(2, dt.getReportFilterUID());
            ps.setString(3, dt.getReportFilterIndicator());
            ps.setString(4, "A");
            ps.setTimestamp(5, status_time);
            ps.executeUpdate();
        }
        catch(Exception ex)
        {
        	logger.fatal("Exception  = "+ex.getMessage(), ex);
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
                ReportFilterValidationDT dt = new ReportFilterValidationDT();
                long report_filter_validation_uid = rs.getLong("report_filter_validation_uid");
                if(!rs.wasNull())
                {
                    dt.setReportFilterValidationUID(report_filter_validation_uid);
                }
                long report_filter_uid = rs.getLong("report_filter_uid");
                if(!rs.wasNull())
                {
                    dt.setReportFilterUID(report_filter_uid);
                }
                dt.setReportFilterIndicator(rs.getString("report_filter_ind"));
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

    /**
     * Edits a record.
     * @param dt the record to add.
     * @throws NEDSSDAOSysException if an error occurs.
     */
    public void edit(ReportFilterValidationDT dt) throws NEDSSDAOSysException
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
            ps.setLong(1, dt.getReportFilterValidationUID());
            ps.setLong(2, dt.getReportFilterUID());
            ps.setString(3, dt.getReportFilterIndicator());
            ps.setString(4, "A");
            ps.setTimestamp(5, status_time);
            ps.setLong(6, dt.getReportFilterValidationUID());
            ps.executeUpdate();
        }
        catch(Exception ex)
        {
        	logger.fatal("Exception  = "+ex.getMessage(), ex);
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
            logger.warn("report_filter_validation_uid=" + uid);
            ps = con.prepareStatement(DELETE[SERVER], ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ps.setLong(1, uid);
            ps.executeUpdate();
        }
        catch(Exception ex)
        {
        	logger.fatal("Exception  = "+ex.getMessage(), ex);
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
