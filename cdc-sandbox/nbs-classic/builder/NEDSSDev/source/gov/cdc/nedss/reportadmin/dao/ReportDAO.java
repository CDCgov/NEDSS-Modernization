package gov.cdc.nedss.reportadmin.dao;

import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.reportadmin.dt.ReportDT;
import gov.cdc.nedss.util.BMPBase;
import gov.cdc.nedss.util.HTMLEncoder;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;

/**
 * Reads and writes the Report table.
 * @author Ed Jenkins
 */
public class ReportDAO extends BMPBase
{

    /**
     * Logger.
     */
    private static final Logger logger = Logger.getLogger(ReportDAO.class);

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
        "select max(report_uid) + 1 as report_uid from nbs_odse..Report;",
        "select max(report_uid) + 1 as report_uid from nbs_odse.Report",
    };

    /**
     * Views a record.
     */
    private static final String[] VIEW =
    {
        "select report_uid, data_source_uid, add_reason_cd, add_time, add_user_uid, desc_txt, effective_from_time, effective_to_time, filter_mode, is_modifiable_ind, location, owner_uid, org_access_permis, prog_area_access_permis, report_title, report_type_code, shared, status_cd, status_time, category, section_cd from nbs_odse..Report where data_source_uid = ? and report_uid = ?;",
        "select report_uid, data_source_uid, add_reason_cd, add_time, add_user_uid, desc_txt, effective_from_time, effective_to_time, filter_mode, is_modifiable_ind, location, owner_uid, org_access_permis, prog_area_access_permis, report_title, report_type_code, shared, status_cd, status_time, category, section_cd from nbs_odse.Report  where data_source_uid = ? and report_uid = ?",
    };

    /**
     * Adds a record.
     */
    private static final String[] ADD =
    {
        "insert into nbs_odse..Report(report_uid, data_source_uid, add_reason_cd, add_time, add_user_uid, desc_txt, effective_from_time, effective_to_time, filter_mode, is_modifiable_ind, location, owner_uid, org_access_permis, prog_area_access_permis, report_title, report_type_code, shared, status_cd, status_time, category, section_cd) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?);",
        "insert into nbs_odse.Report (report_uid, data_source_uid, add_reason_cd, add_time, add_user_uid, desc_txt, effective_from_time, effective_to_time, filter_mode, is_modifiable_ind, location, owner_uid, org_access_permis, prog_area_access_permis, report_title, report_type_code, shared, status_cd, status_time, category, section_cd) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)",
    };

    /**
     * Lists all records.
     */
    private static final String[] LIST =
    {
        "select report_uid, data_source_uid, add_reason_cd, add_time, add_user_uid, desc_txt, effective_from_time, effective_to_time, filter_mode, is_modifiable_ind, location, owner_uid, org_access_permis, prog_area_access_permis, report_title, report_type_code, shared, status_cd, status_time, category from nbs_odse..Report order by report_title, report_uid;",
        "select report_uid, data_source_uid, add_reason_cd, add_time, add_user_uid, desc_txt, effective_from_time, effective_to_time, filter_mode, is_modifiable_ind, location, owner_uid, org_access_permis, prog_area_access_permis, report_title, report_type_code, shared, status_cd, status_time, category from Report  order by report_title, report_uid",
    };

    /**
     * Lists all records that belong to a given data source.
     */
    private static final String[] LIST_DATA_SOURCE =
    {
        "select report_uid, data_source_uid, add_reason_cd, add_time, add_user_uid, desc_txt, effective_from_time, effective_to_time, filter_mode, is_modifiable_ind, location, owner_uid, org_access_permis, prog_area_access_permis, report_title, report_type_code, shared, status_cd, status_time, category from nbs_odse..Report where data_source_uid = ? order by report_title, report_uid;",
        "select report_uid, data_source_uid, add_reason_cd, add_time, add_user_uid, desc_txt, effective_from_time, effective_to_time, filter_mode, is_modifiable_ind, location, owner_uid, org_access_permis, prog_area_access_permis, report_title, report_type_code, shared, status_cd, status_time, category from nbs_odse.Report  where data_source_uid = ? order by report_title, report_uid",
    };

    /**
     * Edits a record.
     */
    private static final String[] EDIT =
    {
        "update nbs_odse..Report set report_title = ?, desc_txt = ?, report_type_code = ?, location = ?, shared = ?, owner_uid = ?, status_time = ?, section_cd = ? where report_uid = ?;",
        "update nbs_odse.Report  set report_title = ?, desc_txt = ?, report_type_code = ?, location = ?, shared = ?, owner_uid = ?, status_time = ?, section_cd = ? where report_uid = ?",
    };

    /**
     * Counts the number of related records in the Display_column table.
     */
    private static final String[] COUNT_DISPLAY_COLUMN =
    {
        "select count(*) as num from nbs_odse..Display_column where data_source_uid = ? and report_uid = ?;",
        "select count(*) as num from nbs_odse.Display_column  where data_source_uid = ? and report_uid = ?",
    };

    /**
     * Counts the number of related records in the Report_Filter table.
     */
    private static final String[] COUNT_REPORT_FILTER =
    {
        "select count(*) as num from nbs_odse..Report_Filter where data_source_uid = ? and report_uid = ?;",
        "select count(*) as num from nbs_odse.Report_Filter  where data_source_uid = ? and report_uid = ?",
    };

    /**
     * Deletes a record.
     */
    private static final String[] DELETE =
    {
        "delete from nbs_odse..Report where data_source_uid = ? and report_uid = ?;",
        "delete from nbs_odse.Report  where data_source_uid = ? and report_uid = ?",
    };

    
    /**
     * Constructor.
     */
    public ReportDAO()
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
                r = rs.getLong("report_uid");
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
     * @param report_uid the UID of the record to view.
     * @return a record.
     * @throws NEDSSDAOSysException if an error occurs.
     */
    public ReportDT view(long data_source_uid, long report_uid) throws NEDSSDAOSysException
    {
        // Create return variable.
        ReportDT dt = new ReportDT();
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
            dt.setDataSourceUID(0L);
            dt.setReportUID(0L);
            if(rs.next())
            {
                dt.setReportUID(report_uid);
                dt.setDataSourceUID(data_source_uid);
                dt.setDescTxt(HTMLEncoder.encodeHtml(rs.getString("desc_txt")));
                dt.setLocation(HTMLEncoder.encodeHtml(rs.getString("location")));
                long owner_uid = rs.getLong("owner_uid");
                if(!rs.wasNull())
                {
                    dt.setOwnerUID(owner_uid);
                }
                dt.setReportTitle(HTMLEncoder.encodeHtml(rs.getString("report_title")));
                dt.setReportTypeCode(HTMLEncoder.encodeHtml(rs.getString("report_type_code")));
                dt.setShared(HTMLEncoder.encodeHtml(rs.getString("shared")));
                dt.setReportSectionCode(rs.getLong("section_cd"));

            }
        }
        catch(Exception ex)
        {
        	logger.fatal("data_source_uid:"+data_source_uid+"report_uid: "+report_uid+" Exception  = "+ex.getMessage(), ex);
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
    public void add(ReportDT dt) throws NEDSSDAOSysException
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
            Timestamp add_time = new Timestamp(l);
            Timestamp status_time = new Timestamp(l);
            con = this.getConnection();
            logger.warn(ADD[SERVER]);
            ps = con.prepareStatement(ADD[SERVER], ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ps.setLong(1, dt.getReportUID());
            ps.setLong(2, dt.getDataSourceUID());
            ps.setNull(3, Types.VARCHAR);
            ps.setTimestamp(4, add_time);
            ps.setNull(5, Types.BIGINT);
            ps.setString(6, dt.getDescTxt());
            ps.setNull(7, Types.TIMESTAMP);
            ps.setNull(8, Types.TIMESTAMP);
            ps.setString(9, "B");
            ps.setString(10, "N");
            ps.setString(11, dt.getLocation());
            ps.setLong(12, dt.getOwnerUID());
            ps.setNull(13, Types.VARCHAR);
            ps.setNull(14, Types.VARCHAR);
            ps.setString(15, dt.getReportTitle());
            ps.setString(16, dt.getReportTypeCode());
            ps.setString(17, dt.getShared());
            ps.setString(18, "A");
            ps.setTimestamp(19, status_time);
            ps.setNull(20, Types.VARCHAR);
            ps.setLong(21, dt.getReportSectionCode());
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
                ReportDT dt = new ReportDT();
                dt.setDataSourceUID(0L);
                dt.setReportUID(0L);
                long report_uid = rs.getLong("report_uid");
                if(!rs.wasNull())
                {
                    dt.setReportUID(report_uid);
                }
                long data_source_uid = rs.getLong("data_source_uid");
                if(!rs.wasNull())
                {
                    dt.setDataSourceUID(data_source_uid);
                }
                dt.setDescTxt(HTMLEncoder.encodeHtml(rs.getString("desc_txt")));
                dt.setLocation(HTMLEncoder.encodeHtml(rs.getString("location")));
                long owner_uid = rs.getLong("owner_uid");
                if(!rs.wasNull())
                {
                    dt.setOwnerUID(owner_uid);
                }
                dt.setReportTitle(HTMLEncoder.encodeHtml(rs.getString("report_title")));
                dt.setReportTypeCode(HTMLEncoder.encodeHtml(rs.getString("report_type_code")));
                dt.setShared(HTMLEncoder.encodeHtml(rs.getString("shared")));
                if(rs.getDate("add_time") != null)
                	dt.setAdd_time(new Timestamp(rs.getDate("add_time").getTime()));
                
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
     * Lists all records that belong to a given data source.
     * @param data_source_uid the UID of the related data source.
     * @return a list of records.
     * @throws NEDSSDAOSysException if an error occurs.
     */
    public ArrayList<Object> listDataSource(long data_source_uid) throws NEDSSDAOSysException
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
            logger.warn(LIST_DATA_SOURCE[SERVER]);
            logger.warn("data_source_uid=" + data_source_uid);
            ps = con.prepareStatement(LIST_DATA_SOURCE[SERVER], ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ps.setLong(1, data_source_uid);
            rs = ps.executeQuery();
            while(rs.next())
            {
                ReportDT dt = new ReportDT();
                dt.setDataSourceUID(0L);
                dt.setReportUID(0L);
                long report_uid = rs.getLong("report_uid");
                if(!rs.wasNull())
                {
                    dt.setReportUID(report_uid);
                }
                dt.setDataSourceUID(data_source_uid);
                dt.setDescTxt(HTMLEncoder.encodeHtml(rs.getString("desc_txt")));
                dt.setLocation(HTMLEncoder.encodeHtml(rs.getString("location")));
                long owner_uid = rs.getLong("owner_uid");
                if(!rs.wasNull())
                {
                    dt.setOwnerUID(owner_uid);
                }
                dt.setReportTitle(HTMLEncoder.encodeHtml(rs.getString("report_title")));
                dt.setReportTypeCode(HTMLEncoder.encodeHtml(rs.getString("report_type_code")));
                dt.setShared(HTMLEncoder.encodeHtml(rs.getString("shared")));
                al.add(dt);
            }
        }
        catch(Exception ex)
        {
        	logger.fatal("data_source_uid: "+data_source_uid+" Exception  = "+ex.getMessage(), ex);
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
    public void edit(ReportDT dt) throws NEDSSDAOSysException
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
            Timestamp add_time = new Timestamp(l);
            Timestamp status_time = new Timestamp(l);
            con = this.getConnection();
            logger.warn(EDIT[SERVER]);
            ps = con.prepareStatement(EDIT[SERVER], ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            //report_title = ?, desc_txt = ?, report_type_code = ?, location = ?, shared = ?, owner_uid = ?, status_time = ? where report_uid = ?
            ps.setString(1, dt.getReportTitle());
            ps.setString(2, dt.getDescTxt());
            ps.setString(3, dt.getReportTypeCode());
            ps.setString(4, dt.getLocation());
            ps.setString(5, dt.getShared());
            ps.setLong(6, dt.getOwnerUID());
            ps.setTimestamp(7, status_time);
            ps.setLong(8, dt.getReportSectionCode());
            ps.setLong(9, dt.getReportUID());
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
     * Counts the number of related records in the Display_column table.
     * @param data_source_uid a data source.
     * @param report_uid a report.
     * @return a count.
     * @throws NEDSSDAOSysException if an error occurs.
     */
    public int countDisplayColumn(long data_source_uid, long report_uid) throws NEDSSDAOSysException
    {
        // Create return variable.
        int r = 0;
        // Create temp variables.
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        // Execute the command and package the data.
        try
        {
            con = this.getConnection();
            logger.warn(COUNT_DISPLAY_COLUMN[SERVER]);
            logger.warn("data_source_uid=" + data_source_uid);
            logger.warn("report_uid=" + report_uid);
            ps = con.prepareStatement(COUNT_DISPLAY_COLUMN[SERVER], ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ps.setLong(1, data_source_uid);
            ps.setLong(2, report_uid);
            rs = ps.executeQuery();
            if(rs.next())
            {
                r = rs.getInt("num");
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
     * Counts the number of related records in the Report_Filter table.
     * @param data_source_uid a data source.
     * @param report_uid a report.
     * @return a count.
     * @throws NEDSSDAOSysException if an error occurs.
     */
    public int countReportFilter(long data_source_uid, long report_uid) throws NEDSSDAOSysException
    {
        // Create return variable.
        int r = 0;
        // Create temp variables.
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        // Execute the command and package the data.
        try
        {
            con = this.getConnection();
            logger.warn(COUNT_REPORT_FILTER[SERVER]);
            logger.warn("data_source_uid=" + data_source_uid);
            logger.warn("report_uid=" + report_uid);
            ps = con.prepareStatement(COUNT_REPORT_FILTER[SERVER], ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ps.setLong(1, data_source_uid);
            ps.setLong(2, report_uid);
            rs = ps.executeQuery();
            if(rs.next())
            {
                r = rs.getInt("num");
            }
        }
        catch(Exception ex)
        {
        	logger.fatal("data_source_uid: "+data_source_uid+" report_uid: "+report_uid+" Exception  = "+ex.getMessage(), ex);
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
