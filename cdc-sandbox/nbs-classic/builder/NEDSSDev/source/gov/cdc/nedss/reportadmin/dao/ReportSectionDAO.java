package gov.cdc.nedss.reportadmin.dao;

import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.reportadmin.dt.ReportDT;
import gov.cdc.nedss.reportadmin.dt.ReportSectionDT;
import gov.cdc.nedss.util.BMPBase;
import gov.cdc.nedss.util.HTMLEncoder;
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
 * Reads and writes the Report_Section table.
 * @author Karthik Chinnayan
 */
public class ReportSectionDAO extends BMPBase
{

    /**
     * Logger.
     */
    private static final Logger logger = Logger.getLogger(ReportSectionDAO.class);

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
        "select max( report_section_uid) + 1 as  report_section_uid from nbs_odse..Report_Section;",
        "select max( report_section_uid) + 1 as  report_section_uid from nbs_odse.Report_Section",
    };

    /**
     * Gets the next available section code
     */
    
    private static final String[] GET_NEXT_CODE = 
    {
    	"select max(section_cd) + 1 as section_cd from nbs_odse..Report_Section;",
    	"select max(section_cd) + 1 as section_cd from nbs_odse.Report_Section",
    };
    /**
     * Views a record_section.
     */
    private static final String[] VIEW =
    {
        "select  report_section_uid, section_cd, section_desc_txt, comments, last_chg_user_id, last_chg_time, add_time, add_user_id from nbs_odse..Report_Section where section_cd = ?;",
        "select  report_section_uid, section_cd, section_desc_txt, comments, last_chg_user_id, last_chg_time, add_time, add_user_id from nbs_odse.Report_Section where section_cd = ?",
    };
    
    /**
     * Views a record.
     */
    private static final String[] VIEW_REPORT =
    {
        "select  report_title,section_cd from nbs_odse..Report where section_cd = ?;",
        "select  report_title,section_cd from nbs_odse.Report where section_cd = ?",
    };
    

    /**
     * Adds a record.
     */
    private static final String[] ADD =
    {
        "insert into nbs_odse..Report_Section(section_cd, section_desc_txt, comments,last_chg_user_id,last_chg_time,add_time,add_user_id ) values (?, ?, ?, ?, ?, ?, ?);",
        "insert into nbs_odse.Report_Section (section_cd, section_desc_txt, comments,last_chg_user_id,last_chg_time,add_time,add_user_id ) values (?, ?, ?, ?, ?, ?, ?)",
    };

    /**
     * Views all section_desc_txt a record_section.
     */
    private static final String[] VIEW_NAME =
    {
        "select   section_desc_txt from nbs_odse..Report_Section ;",
        "select   section_desc_txt from nbs_odse.Report_Section",
    };
    
    /**
     * Lists all records.
     *  private Long report_section_uid;
    private String section_cd;
    private String section_desc_txt;
    private String comments;
    private Long add_user_id;
    private Timestamp add_time;
    private Long last_chg_user_id;
    private Timestamp last_chg_time;
  
     */
    private static final String[] LIST =
    {
        "select report_section_uid, section_cd, section_desc_txt, comments, add_user_id, add_time, " +
        		"last_chg_user_id, last_chg_time from nbs_odse..Report_Section order by section_desc_txt;",
        "select report_section_uid, section_cd, section_desc_txt, comments, add_user_id, add_time, " +
        		"last_chg_user_id, last_chg_time from nbs_odse.Report_Section order by section_desc_txt",
    };

    /**
     * Edits a record.
     */
    private static final String[] EDIT =
    {
        "update nbs_odse..Report_Section set section_desc_txt = ?, comments = ?,  last_chg_time=?, last_chg_user_id=? where report_section_uid = ?;",
        "update nbs_odse.Report_Section  set section_desc_txt = ?, comments = ?,  last_chg_time=?, last_chg_user_id=? where report_section_uid = ?",
    };
    
    /**
     * Edits a Report Record to Default if it is getting deleted from the Report_section.
     */
    private static final String[] EDIT_REPORT =
    {
        "update nbs_odse..Report set section_cd = 1000 where section_cd = ?;",
        "update nbs_odse.Report set section_cd = 1000 where section_cd = ?",
    };

    /**
     * Deletes a record.
     */
    private static final String[] DELETE =
    {
        "delete from nbs_odse..Report_Section where report_section_uid = ?;",
        "delete from nbs_odse.Report_Section where report_section_uid = ?",
    };


    /**
     * Constructor.
     */
    public ReportSectionDAO()
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
                r = rs.getLong("report_section_uid");
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
     * Gets the next available UID.
     * @return a UID.
     * @throws NEDSSDAOSysException if an error occurs.
     */
    public long getNextCode() throws NEDSSDAOSysException
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
            logger.warn(GET_NEXT_CODE[SERVER]);
            ps = con.prepareStatement(GET_NEXT_CODE[SERVER], ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            rs = ps.executeQuery();
            if(rs.next())
            {
                r = rs.getLong("section_cd");
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
    public ReportSectionDT view( long section_cd) throws NEDSSDAOSysException
    {
        // Create return variable.
        ReportSectionDT dt = new ReportSectionDT();
        // Create temp variables.
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        // Execute the command and package the data.
        try
        {
            con = this.getConnection();
            logger.warn(VIEW[SERVER]);
            logger.warn("section_cd=" + section_cd);
            ps = con.prepareStatement(VIEW[SERVER], ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ps.setLong(1, section_cd);
            rs = ps.executeQuery();
            dt.setReportSectionUID(0L);
            if(rs.next())
            {
                dt.setReportSectionUID(rs.getLong("report_section_uid"));
                dt.setSectionDescTxt(HTMLEncoder.encodeHtml(rs.getString("section_desc_txt")));
                dt.setComments(HTMLEncoder.encodeHtml(rs.getString("comments")));
                dt.setSectionCd(rs.getLong("section_cd"));
            }
        }
        catch(Exception ex)
        {
        	logger.fatal("section_cd: "+section_cd+" Exception  = "+ex.getMessage(), ex);
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
     * selects all report names available in the Report_section
     * @return a list of report sections.
     * @throws NEDSSDAOSysException if an error occurs.
     */
    public ArrayList<Object> selectName() throws NEDSSDAOSysException
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
            logger.warn(VIEW_NAME[SERVER]);
            ps = con.prepareStatement(VIEW_NAME[SERVER], ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            rs = ps.executeQuery();
            while(rs.next())
            {
                ReportSectionDT dt = new ReportSectionDT();
                al.add(rs.getString("section_desc_txt"));

                logger.info("# of items added available = " + al.size());
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
     * Views a record.
     * @param data_source_uid the data source the report belongs to.
     * @param report_uid the UID of the record to view.
     * @return a record.
     * @throws NEDSSDAOSysException if an error occurs.
     */
    public ArrayList<Object> viewReport( long section_cd) throws NEDSSDAOSysException
    {  
    	ArrayList<Object> al = new ArrayList<Object> ();
        // Create temp variables.
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        // Execute the command and package the data.
        try
        {
            con = this.getConnection();
            logger.warn(VIEW_REPORT[SERVER]);
            logger.warn("section_cd=" + section_cd);
            ps = con.prepareStatement(VIEW_REPORT[SERVER], ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ps.setLong(1, section_cd);
            rs = ps.executeQuery();
            while(rs.next())
            {
            	ReportDT dt = new ReportDT();
                dt.setReportTitle(rs.getString("report_title"));
                dt.setReportSectionCode(rs.getLong("section_cd"));
                al.add(dt);
                logger.info("# of items added available = " + al.size());
            }
        }
        catch(Exception ex)
        {
        	logger.fatal("section_cd: "+section_cd+" Exception  = "+ex.getMessage(), ex);
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
     * Adds a record.
     * @param dt the record to add.
     * @throws NEDSSDAOSysException if an error occurs.
     */
    public void add(ReportSectionDT dt) throws NEDSSDAOSysException
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
            ps.setLong(1, dt.getSectionCd());
            ps.setString(2, dt.getSectionDescTxt());
            ps.setString(3, dt.getComments());
            ps.setLong(4, dt.getLastChgUserUID());
            ps.setTimestamp(5,status_time );
            ps.setTimestamp(6, add_time);
            ps.setLong(7, dt.getAddUserUID());
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
     * Lists all report sections available
     * @return a list of report sections.
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
                ReportSectionDT dt = new ReportSectionDT();

                dt.setReportSectionUID(rs.getLong("report_section_uid"));
                dt.setSectionCd(rs.getLong("section_cd"));
                dt.setSectionDescTxt(HTMLEncoder.encodeHtml(rs.getString("section_desc_txt")));
                dt.setComments(HTMLEncoder.encodeHtml(rs.getString("comments")));
                dt.setAddUserUID(rs.getLong("add_user_id"));
                dt.setAddTime(new Timestamp(rs.getDate("add_time").getTime()));
                dt.setLastChgUserUID(rs.getLong("last_chg_user_id"));
                dt.setLastChgTime(new Timestamp(rs.getDate("last_chg_time").getTime()));

                al.add(dt);

                logger.info("# of items added available = " + al.size());
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
     * @param dt the record to edit.
     * @throws NEDSSDAOSysException if an error occurs.
     */
    public void edit(ReportSectionDT dt) throws NEDSSDAOSysException
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
            ps.setString(1, dt.getSectionDescTxt());
            ps.setString(2, dt.getComments());
            ps.setTimestamp(3, status_time);
            ps.setLong(4, dt.getLastChgUserUID());
            ps.setLong(5, dt.getReportSectionUID());
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
     * Edits a record.
     * @param dt the record to edit.
     * @throws NEDSSDAOSysException if an error occurs.
     */
    public void editReport(long section_cd) throws NEDSSDAOSysException
    {
        
        // Create temp variables.
        Connection con = null;
        PreparedStatement ps = null;
        // Execute the command and package the data.
        try
        {
            Date d = new Date();
            long l = d.getTime();
            con = this.getConnection();
            logger.warn(EDIT_REPORT[SERVER]);
            logger.warn("section_cd=" + section_cd);
            ps = con.prepareStatement(EDIT_REPORT[SERVER], ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ps.setLong(1, section_cd);
            ps.executeUpdate();
        }
        catch(Exception ex)
        {
        	logger.fatal("section_cd: "+section_cd+" Exception  = "+ex.getMessage(), ex);
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
    public void delete(long report_section_uid) throws NEDSSDAOSysException
    {
        // Create temp variables.
        Connection con = null;
        PreparedStatement ps = null;
        // Execute the command and package the data.
        try
        {
            con = this.getConnection();
            logger.warn(DELETE[SERVER]);
            logger.warn("report_section_uid=" + report_section_uid);
            ps = con.prepareStatement(DELETE[SERVER], ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ps.setLong(1, report_section_uid);
            ps.executeUpdate();
        }
        catch(Exception ex)
        {
        	logger.fatal("report_section_uid: "+report_section_uid+" Exception  = "+ex.getMessage(), ex);
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
