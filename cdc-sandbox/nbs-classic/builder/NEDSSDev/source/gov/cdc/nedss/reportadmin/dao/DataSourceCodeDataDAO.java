package gov.cdc.nedss.reportadmin.dao;

import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.reportadmin.dt.DataSourceCodeDataDT;
import gov.cdc.nedss.util.BMPBase;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

/**
 * Reads and writes the Data_Source_CodeData table.
 * @author Ed Jenkins
 */
public class DataSourceCodeDataDAO extends BMPBase
{

    /**
     * Logger.
     */
    private static final Logger logger = Logger.getLogger(DataSourceCodeDataDAO.class);

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
        "select max(data_source_codedata_uid) + 1 as data_source_codedata_uid from nbs_odse..Data_Source_CodeData;",
        "select max(data_source_codedata_uid) + 1 as data_source_codedata_uid from nbs_odse.Data_Source_CodeData",
    };

    /**
     * Views a record.
     */
    private static final String[] VIEW =
    {
        "select data_source_codedata_uid, data_source_name, column_name, codeset_name, code_desc_cd, status_cd, status_time from nbs_odse..Data_Source_CodeData where data_source_codedata_uid = ?;",
        "select data_source_codedata_uid, data_source_name, column_name, codeset_name, code_desc_cd, status_cd, status_time from nbs_odse.Data_Source_CodeData  where data_source_codedata_uid = ?",
    };

    /**
     * Adds a record.
     */
    private static final String[] ADD =
    {
        "insert into nbs_odse..Data_Source_CodeData(data_source_codedata_uid, data_source_name, column_name, codeset_name, code_desc_cd, status_cd, status_time) values (?, ?, ?, ?, ?, ?, ?);",
        "insert into nbs_odse.Data_Source_CodeData (data_source_codedata_uid, data_source_name, column_name, codeset_name, code_desc_cd, status_cd, status_time) values (?, ?, ?, ?, ?, ?, ?)",
    };

    /**
     * Lists all records.
     */
    private static final String[] LIST =
    {
        "select data_source_codedata_uid, data_source_name, column_name, codeset_name, code_desc_cd, status_cd, status_time from nbs_odse..Data_Source_CodeData order by data_source_codedata_uid;",
        "select data_source_codedata_uid, data_source_name, column_name, codeset_name, code_desc_cd, status_cd, status_time from nbs_odse.Data_Source_CodeData  order by data_source_codedata_uid",
    };

    /**
     * Edits a record.
     */
    private static final String[] EDIT =
    {
        "update nbs_odse..Data_Source_CodeData set data_source_codedata_uid = ?, data_source_name = ?, column_name = ?, codeset_name = ?, code_desc_cd = ?, status_cd = ?, status_time = ? where data_source_codedata_uid = ?;",
        "update nbs_odse.Data_Source_CodeData  set data_source_codedata_uid = ?, data_source_name = ?, column_name = ?, codeset_name = ?, code_desc_cd = ?, status_cd = ?, status_time = ? where data_source_codedata_uid = ?",
    };

    /**
     * Deletes a record.
     */
    private static final String[] DELETE =
    {
        "delete from nbs_odse..Data_Source_CodeData where data_source_codedata_uid = ?;",
        "delete from nbs_odse.Data_Source_CodeData  where data_source_codedata_uid = ?",
    };

    

    /**
     * Constructor.
     */
    public DataSourceCodeDataDAO()
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
                r = rs.getLong("data_source_codedata_uid");
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
     * Views a record.
     * @param uid the UID of the record to view.
     * @return a record.
     * @throws NEDSSDAOSysException if an error occurs.
     */
    public DataSourceCodeDataDT view(long uid) throws NEDSSDAOSysException
    {
        // Create return variable.
        DataSourceCodeDataDT dt = new DataSourceCodeDataDT();
        // Create temp variables.
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        // Execute the command and package the data.
        try
        {
            con = this.getConnection();
            logger.warn(VIEW[SERVER]);
            logger.warn("data_source_codedata_uid=" + uid);
            ps = con.prepareStatement(VIEW[SERVER], ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ps.setLong(1, uid);
            rs = ps.executeQuery();
            dt.setDataSourceCodeDataUID(0L);
            if(rs.next())
            {
                long data_source_codedata_uid = rs.getLong("data_source_codedata_uid");
                if(!rs.wasNull())
                {
                    dt.setDataSourceCodeDataUID(data_source_codedata_uid);
                }
                dt.setDataSourceName(rs.getString("data_source_name"));
                dt.setColumnName(rs.getString("column_name"));
                dt.setCodeDescCD(rs.getString("code_desc_cd"));
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
        return dt;
    }

    /**
     * Adds a record.
     * @param dt the record to add.
     * @throws NEDSSDAOSysException if an error occurs.
     */
    public void add(DataSourceCodeDataDT dt) throws NEDSSDAOSysException
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
            ps.setLong(1, dt.getDataSourceCodeDataUID());
            ps.setString(2, dt.getDataSourceName());
            ps.setString(3, dt.getColumnName());
            ps.setString(4, dt.getCodesetName());
            ps.setString(5, dt.getCodeDescCD());
            ps.setString(6, "A");
            ps.setTimestamp(7, status_time);
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
                DataSourceCodeDataDT dt = new DataSourceCodeDataDT();
                dt.setDataSourceCodeDataUID(0L);
                long data_source_codedata_uid = rs.getLong("data_source_codedata_uid");
                if(!rs.wasNull())
                {
                    dt.setDataSourceCodeDataUID(data_source_codedata_uid);
                }
                dt.setDataSourceName(rs.getString("data_source_name"));
                dt.setColumnName(rs.getString("column_name"));
                dt.setCodeDescCD(rs.getString("code_desc_cd"));
                al.add(dt);
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
     * Edits a record.
     * @param dt the record to add.
     * @throws NEDSSDAOSysException if an error occurs.
     */
    public void edit(DataSourceCodeDataDT dt) throws NEDSSDAOSysException
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
            ps.setLong(1, dt.getDataSourceCodeDataUID());
            ps.setString(2, dt.getDataSourceName());
            ps.setString(3, dt.getColumnName());
            ps.setString(4, dt.getCodesetName());
            ps.setString(5, dt.getCodeDescCD());
            ps.setString(6, "A");
            ps.setTimestamp(7, status_time);
            ps.setLong(8, dt.getDataSourceCodeDataUID());
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
            logger.warn("data_source_codedata_uid=" + uid);
            ps = con.prepareStatement(DELETE[SERVER], ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ps.setLong(1, uid);
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

}
