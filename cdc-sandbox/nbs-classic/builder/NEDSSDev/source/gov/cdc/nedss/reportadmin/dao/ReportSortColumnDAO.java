package gov.cdc.nedss.reportadmin.dao;

import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.reportadmin.dt.ReportSortColumnDT;
import gov.cdc.nedss.util.BMPBase;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.log4j.Logger;

/**
 * Reads and writes the Report_Sort_Column table.
 * @author Ed Jenkins
 */
public class ReportSortColumnDAO extends BMPBase
{

    /**
     * Logger.
     */
    private static final Logger logger = Logger.getLogger(ReportSortColumnDAO.class);

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
        "select max(report_sort_column_uid) + 1 as report_sort_column_uid from nbs_odse..Report_Sort_Column;",
        "select max(report_sort_column_uid) + 1 as report_sort_column_uid from nbs_odse.Report_Sort_Column",
    };

    /**
     * Views a record.
     */
    private static final String[] VIEW =
    {
        "select report_sort_column_uid, report_sort_order_code, report_sort_sequence_num, data_source_uid, report_uid, column_uid, status_cd, status_time from nbs_odse..Report_Sort_Column where data_source_uid = ? and report_uid = ?;",
        "select report_sort_column_uid, report_sort_order_code, report_sort_sequence_num, data_source_uid, report_uid, column_uid, status_cd, status_time from nbs_odse.Report_Sort_Column  where data_source_uid = ? and report_uid = ?",
    };

    /**
     * Adds a record.
     */
    private static final String[] ADD =
    {
        "insert into nbs_odse..Report_Sort_Column(report_sort_column_uid, report_sort_order_code, report_sort_sequence_num, data_source_uid, report_uid, column_uid, status_cd, status_time) values (?, ?, ?, ?, ?, ?, ?, ?);",
        "insert into nbs_odse.Report_Sort_Column (report_sort_column_uid, report_sort_order_code, report_sort_sequence_num, data_source_uid, report_uid, column_uid, status_cd, status_time) values (?, ?, ?, ?, ?, ?, ?, ?)",
    };

    /**
     * Deletes a record.
     */
    private static final String[] DELETE =
    {
        "delete from nbs_odse..Report_Sort_Column where data_source_uid = ? and report_uid = ?;",
        "delete from nbs_odse.Report_Sort_Column  where data_source_uid = ? and report_uid = ?",
    };

    /**
     * Gets the sort order SRT.
     */
    private static final String[] GET_SORT_ORDER_SRT =
    {
        "select code, code_short_desc_txt from nbs_srte..Code_value_general where code_set_nm = 'SORT_ORDER' order by code_short_desc_txt;",
        "select code, code_short_desc_txt from nbs_srte.Code_value_general  where code_set_nm = 'SORT_ORDER' order by code_short_desc_txt",
    };

   
    /**
     * Constructor.
     */
    public ReportSortColumnDAO()
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
                r = rs.getLong("report_sort_column_uid");
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
     * @param data_source_uid the data source the report belongs to.
     * @param report_uid the UID of the record to delete.
     * @return a record.
     * @throws NEDSSDAOSysException if an error occurs.
     */
    public ReportSortColumnDT view(long data_source_uid, long report_uid) throws NEDSSDAOSysException
    {
        // Create return variable.
        ReportSortColumnDT dt = new ReportSortColumnDT();
        // Create temp variables.
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        // Execute the command and package the data.
        try
        {
            con = this.getConnection();
            logger.warn(VIEW[SERVER]);
            logger.warn("data_source_uid=" + data_source_uid);
            logger.warn("report_uid=" + report_uid);
            ps = con.prepareStatement(VIEW[SERVER], ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ps.setLong(1, data_source_uid);
            ps.setLong(2, report_uid);
            rs = ps.executeQuery();
            dt.setReportSortColumnUID(0L);
            if(rs.next())
            {
                long report_sort_column_uid = rs.getLong("report_sort_column_uid");
                if(!rs.wasNull())
                {
                    dt.setReportSortColumnUID(report_sort_column_uid);
                }
                dt.setReportSortOrderCode(rs.getString("report_sort_order_code"));
                int report_sort_sequence_num = rs.getInt("report_sort_sequence_num");
                if(!rs.wasNull())
                {
                    dt.setReportSortSequenceNumber(report_sort_sequence_num);
                }
                dt.setDataSourceUID(data_source_uid);
                dt.setReportUID(report_uid);
                long column_uid = rs.getLong("column_uid");
                if(!rs.wasNull())
                {
                    dt.setColumnUID(column_uid);
                }
            }
        }
        catch(Exception ex)
        {
        	logger.fatal("data_source_uid: "+data_source_uid+", report_uid: "+report_uid+" Exception  = "+ex.getMessage(), ex);
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
    public void add(ReportSortColumnDT dt) throws NEDSSDAOSysException
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
            ps.setLong(1, dt.getReportSortColumnUID());
            ps.setString(2, dt.getReportSortOrderCode());
            ps.setInt(3, dt.getReportSortSequenceNumber());
            ps.setLong(4, dt.getDataSourceUID());
            ps.setLong(5, dt.getReportUID());
            ps.setLong(6, dt.getColumnUID());
            ps.setString(7, "A");
            ps.setTimestamp(8, status_time);
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
        	closeStatement(ps);
            releaseConnection(con);
        }
    }

    /**
     * Deletes a record.
     * @param data_source_uid the data source the report belongs to.
     * @param report_uid the UID of the record to delete.
     * @throws NEDSSDAOSysException if an error occurs.
     */
    public void delete(long data_source_uid, long report_uid) throws NEDSSDAOSysException
    {
        // Create temp variables.
        Connection con = null;
        PreparedStatement ps = null;
        // Execute the command and package the data.
        try
        {
            con = this.getConnection();
            logger.warn(DELETE[SERVER]);
            logger.warn("data_source_uid=" + data_source_uid);
            logger.warn("report_uid=" + report_uid);
            ps = con.prepareStatement(DELETE[SERVER], ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ps.setLong(1, data_source_uid);
            ps.setLong(2, report_uid);
            ps.executeUpdate();
        }
        catch(Exception ex)
        {
        	logger.fatal("data_source_uid: "+data_source_uid+", report_uid: "+report_uid+" Exception  = "+ex.getMessage(), ex);
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
     * Gets the sort order SRT.
     * @return an SRT string.
     * @throws NEDSSDAOSysException if an error occurs.
     */
    public String getSortOrderSRT() throws NEDSSDAOSysException
    {
        // Create return variable.
        String r = "";
        // Create temp variables.
        StringBuffer sb = new StringBuffer();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        // Execute the command and package the data.
        try
        {
            con = this.getConnection();
            logger.warn(GET_SORT_ORDER_SRT[SERVER]);
            ps = con.prepareStatement(GET_SORT_ORDER_SRT[SERVER], ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            rs = ps.executeQuery();
            while(rs.next())
            {
                String k = rs.getString("code");
                String v = rs.getString("code_short_desc_txt");
                sb.append(k);
                sb.append("$");
                sb.append(v);
                sb.append("|");
            }
            r = sb.toString();
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

}
