package gov.cdc.nedss.reportadmin.dao;

import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.reportadmin.dt.DataSourceCodesetDT;
import gov.cdc.nedss.util.BMPBase;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

/**
 * Reads and writes the Data_Source_Codeset table.
 * @author Ed Jenkins
 */
public class DataSourceCodesetDAO extends BMPBase
{

    /**
     * Logger.
     */
    private static final Logger logger = Logger.getLogger(DataSourceCodesetDAO.class);

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
        "select max(data_source_codeset_uid) + 1 as data_source_codeset_uid from nbs_odse..Data_Source_Codeset;",
        "select max(data_source_codeset_uid) + 1 as data_source_codeset_uid from nbs_odse.Data_Source_Codeset",
    };

    /**
     * Views a record.
     */
    private static final String[] VIEW =
    {
        "select data_source_codeset_uid, column_uid, code_desc_cd, codeset_nm, status_cd, status_time from nbs_odse..Data_Source_Codeset where data_source_codeset_uid = ?;",
        "select data_source_codeset_uid, column_uid, code_desc_cd, codeset_nm, status_cd, status_time from nbs_odse.Data_Source_Codeset  where data_source_codeset_uid = ?",
    };

    /**
     * Adds a record.
     */
    private static final String[] ADD =
    {
        "insert into nbs_odse..Data_Source_Codeset(data_source_codeset_uid, column_uid, code_desc_cd, codeset_nm, status_cd, status_time) values (?, ?, ?, ?, ?, ?);",
        "insert into nbs_odse.Data_Source_Codeset (data_source_codeset_uid, column_uid, code_desc_cd, codeset_nm, status_cd, status_time) values (?, ?, ?, ?, ?, ?)",
    };

    /**
     * Lists all records.
     */
    private static final String[] LIST =
    {
        "select data_source_codeset_uid, column_uid, code_desc_cd, codeset_nm, status_cd, status_time from nbs_odse..Data_Source_Codeset order by data_source_codeset_uid;",
        "select data_source_codeset_uid, column_uid, code_desc_cd, codeset_nm, status_cd, status_time from nbs_odse.Data_Source_Codeset  order by data_source_codeset_uid",
    };

    /**
     * Edits a record.
     */
    private static final String[] EDIT =
    {
        "update nbs_odse..Data_Source_Codeset set data_source_codeset_uid = ?, column_uid = ?, code_desc_cd = ?, codeset_nm = ?, status_cd = ?, status_time = ? where data_source_codeset_uid = ?;",
        "update nbs_odse.Data_Source_Codeset  set data_source_codeset_uid = ?, column_uid = ?, code_desc_cd = ?, codeset_nm = ?, status_cd = ?, status_time = ? where data_source_codeset_uid = ?",
    };

    /**
     * Deletes a record.
     */
    private static final String[] DELETE =
    {
        "delete from nbs_odse..Data_Source_Codeset where data_source_codeset_uid = ?;",
        "delete from nbs_odse.Data_Source_Codeset  where data_source_codeset_uid = ?",
    };

    /**
     * Deletes a record.
     */
    private static final String[] DELETE_ALL =
    {
        "delete from nbs_odse..Data_Source_Codeset;",
        "delete from nbs_odse.Data_Source_Codeset",
    };

    /**
     * Select which database type to use.
     */
   

    /**
     * Constructor.
     */
    public DataSourceCodesetDAO()
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
                r = rs.getLong("data_source_codeset_uid");
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
    public DataSourceCodesetDT view(long uid) throws NEDSSDAOSysException
    {
        // Create return variable.
        DataSourceCodesetDT dt = new DataSourceCodesetDT();
        // Create temp variables.
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        // Execute the command and package the data.
        try
        {
            con = this.getConnection();
            logger.warn(VIEW[SERVER]);
            logger.warn("data_source_codeset_uid=" + uid);
            ps = con.prepareStatement(VIEW[SERVER], ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ps.setLong(1, uid);
            rs = ps.executeQuery();
            dt.setDataSourceCodesetUID(0L);
            if(rs.next())
            {
                long data_source_codeset_uid = rs.getLong("data_source_codeset_uid");
                if(!rs.wasNull())
                {
                    dt.setDataSourceCodesetUID(data_source_codeset_uid);
                }
                long column_uid = rs.getLong("column_uid");
                if(!rs.wasNull())
                {
                    dt.setColumnUID(column_uid);
                }
                dt.setCodeDescCD(rs.getString("code_desc_cd"));
                dt.setCodesetName(rs.getString("codeset_nm"));
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
    public void add(DataSourceCodesetDT dt) throws NEDSSDAOSysException
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
            ps.setLong(1, dt.getDataSourceCodesetUID());
            ps.setLong(2, dt.getColumnUID());
            ps.setString(3, dt.getCodeDescCD());
            ps.setString(4, dt.getCodesetName());
            ps.setString(5, "A");
            ps.setTimestamp(6, status_time);
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
                DataSourceCodesetDT dt = new DataSourceCodesetDT();
                dt.setDataSourceCodesetUID(0L);
                long data_source_codeset_uid = rs.getLong("data_source_codeset_uid");
                if(!rs.wasNull())
                {
                    dt.setDataSourceCodesetUID(data_source_codeset_uid);
                }
                long column_uid = rs.getLong("column_uid");
                if(!rs.wasNull())
                {
                    dt.setColumnUID(column_uid);
                }
                dt.setCodeDescCD(rs.getString("code_desc_cd"));
                dt.setCodesetName(rs.getString("codeset_nm"));
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
     * @param dt the record to edit.
     * @throws NEDSSDAOSysException if an error occurs.
     */
    public void edit(DataSourceCodesetDT dt) throws NEDSSDAOSysException
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
            logger.warn(EDIT[SERVER]);
            ps = con.prepareStatement(EDIT[SERVER], ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ps.setLong(1, dt.getDataSourceCodesetUID());
            ps.setLong(2, dt.getColumnUID());
            ps.setString(3, dt.getCodeDescCD());
            ps.setString(4, dt.getCodesetName());
            ps.setString(5, "A");
            ps.setTimestamp(6, status_time);
            ps.setLong(7, dt.getDataSourceCodesetUID());
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
            logger.warn("data_source_codeset_uid=" + uid);
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

    /**
     * Deletes all records.
     * @param uid the UID of the record to delete.
     * @throws NEDSSDAOSysException if an error occurs.
     */
    public void deleteAll() throws NEDSSDAOSysException
    {
        // Create temp variables.
        Connection con = null;
        PreparedStatement ps = null;
        // Execute the command and package the data.
        try
        {
            con = this.getConnection();
            logger.warn(DELETE_ALL[SERVER]);
            ps = con.prepareStatement(DELETE_ALL[SERVER], ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
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
