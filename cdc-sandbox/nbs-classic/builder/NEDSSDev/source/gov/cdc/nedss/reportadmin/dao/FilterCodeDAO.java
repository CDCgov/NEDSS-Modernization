package gov.cdc.nedss.reportadmin.dao;

import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.reportadmin.dt.FilterCodeDT;
import gov.cdc.nedss.util.BMPBase;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.log4j.Logger;

/**
 * Reads the Filter_code table.
 * NOTE:  It is not recommended that administrators update this table apart from an NBS upgrade.
 * When changes are needed to this table, they will be handled by an SQL script accompanied by
 * a matching update to nbs.ear.
 * @author Ed Jenkins
 */
public class FilterCodeDAO extends BMPBase
{

    /**
     * Logger.
     */
    private static final Logger logger = Logger.getLogger(FilterCodeDAO.class);

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
     * Views a record.
     */
    private static final String[] VIEW =
    {
        "select filter_uid, code_table, desc_txt, effective_from_time, effective_to_time, filter_code, filter_code_set_nm, filter_type, filter_name, status_cd, status_time from nbs_odse..Filter_code where filter_uid = ?;",
        "select filter_uid, code_table, desc_txt, effective_from_time, effective_to_time, filter_code, filter_code_set_nm, filter_type, filter_name, status_cd, status_time from nbs_odse.Filter_code  where filter_uid = ?",
    };

    /**
     * Lists all records.
     */
    private static final String[] LIST =
    {
        "select filter_uid, code_table, desc_txt, effective_from_time, effective_to_time, filter_code, filter_code_set_nm, filter_type, filter_name, status_cd, status_time from nbs_odse..Filter_code order by filter_name;",
        "select filter_uid, code_table, desc_txt, effective_from_time, effective_to_time, filter_code, filter_code_set_nm, filter_type, filter_name, status_cd, status_time from nbs_odse.Filter_code  order by filter_name",
    };

    

    /**
     * Constructor.
     */
    public FilterCodeDAO()
    {
    }

    /**
     * Views a record.
     * @param uid the UID of the record to view.
     * @return a record.
     * @throws NEDSSDAOSysException if an error occurs.
     */
    public FilterCodeDT view(long uid) throws NEDSSDAOSysException
    {
        // Create return variable.
        FilterCodeDT dt = new FilterCodeDT();
        // Create temp variables.
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        // Execute the command and package the data.
        try
        {
            con = this.getConnection();
            logger.warn(VIEW[SERVER]);
            logger.warn("filter_uid=" + uid);
            ps = con.prepareStatement(VIEW[SERVER], ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ps.setLong(1, uid);
            rs = ps.executeQuery();
            dt.setFilterUID(0L);
            if(rs.next())
            {
                long filter_uid = rs.getLong("filter_uid");
                if(!rs.wasNull())
                {
                    dt.setFilterUID(filter_uid);
                }
                dt.setCodeTable(rs.getString("code_table"));
                dt.setDescTxt(rs.getString("desc_txt"));
                dt.setFilterCode(rs.getString("filter_code"));
                dt.setFilterCodeSetName(rs.getString("filter_code_set_nm"));
                dt.setFilterType(rs.getString("filter_type"));
                dt.setFilterName(rs.getString("filter_name"));
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
                FilterCodeDT dt = new FilterCodeDT();
                dt.setFilterUID(0L);
                long filter_uid = rs.getLong("filter_uid");
                if(!rs.wasNull())
                {
                    dt.setFilterUID(filter_uid);
                }
                dt.setCodeTable(rs.getString("code_table"));
                dt.setDescTxt(rs.getString("desc_txt"));
                dt.setFilterCode(rs.getString("filter_code"));
                dt.setFilterCodeSetName(rs.getString("filter_code_set_nm"));
                dt.setFilterType(rs.getString("filter_type"));
                dt.setFilterName(rs.getString("filter_name"));
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

}
