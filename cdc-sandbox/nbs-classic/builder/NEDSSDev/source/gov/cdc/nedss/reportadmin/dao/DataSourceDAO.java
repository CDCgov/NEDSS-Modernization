package gov.cdc.nedss.reportadmin.dao;

import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.reportadmin.dt.DataSourceDT;
import gov.cdc.nedss.util.BMPBase;
import gov.cdc.nedss.util.HTMLEncoder;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import org.apache.log4j.Logger;

/**
 * Reads and writes the Data_Source table.
 * @author Ed Jenkins
 */
public class DataSourceDAO extends BMPBase
{

    /**
     * Logger.
     */
    private static final Logger logger = Logger.getLogger(DataSourceDAO.class);

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
        "select max(data_source_uid) + 1 as data_source_uid from nbs_odse..Data_Source;",
        "select max(data_source_uid) + 1 as data_source_uid from nbs_odse.Data_Source",
    };

    /**
     * Views a record.
     */
    private static final String[] VIEW =
    {
        "select data_source_uid, column_max_len, condition_security, data_source_name, data_source_title, data_source_type_code, desc_txt, effective_from_time, effective_to_time, jurisdiction_security, org_access_permis, prog_area_access_permis, status_cd, status_time, reporting_facility_security from nbs_odse..Data_Source where data_source_uid = ?;",
        "select data_source_uid, column_max_len, condition_security, data_source_name, data_source_title, data_source_type_code, desc_txt, effective_from_time, effective_to_time, jurisdiction_security, org_access_permis, prog_area_access_permis, status_cd, status_time, reporting_facility_security from nbs_odse.Data_Source  where data_source_uid = ?",
    };

    /**
     * Adds a record.
     */
    private static final String[] ADD =
    {
        "insert into nbs_odse..Data_Source(data_source_uid, column_max_len, condition_security, data_source_name, data_source_title, data_source_type_code, desc_txt, effective_from_time, effective_to_time, jurisdiction_security, org_access_permis, prog_area_access_permis, status_cd, status_time, reporting_facility_security) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);",
        "insert into nbs_odse.Data_Source (data_source_uid, column_max_len, condition_security, data_source_name, data_source_title, data_source_type_code, desc_txt, effective_from_time, effective_to_time, jurisdiction_security, org_access_permis, prog_area_access_permis, status_cd, status_time, reporting_facility_security) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
    };

    /**
     * Lists all records.
     */
    private static final String[] LIST =
    {
        "select data_source_uid, column_max_len, condition_security, data_source_name, data_source_title, data_source_type_code, desc_txt, effective_from_time, effective_to_time, jurisdiction_security, org_access_permis, prog_area_access_permis, status_cd, status_time, reporting_facility_security from nbs_odse..Data_Source order by data_source_title, data_source_uid;",
        "select data_source_uid, column_max_len, condition_security, data_source_name, data_source_title, data_source_type_code, desc_txt, effective_from_time, effective_to_time, jurisdiction_security, org_access_permis, prog_area_access_permis, status_cd, status_time, reporting_facility_security from nbs_odse.Data_Source  order by data_source_title, data_source_uid",
    };

    /**
     * Edits a record.
     */
    private static final String[] EDIT =
    {
        "update nbs_odse..Data_Source set data_source_title = ?, desc_txt = ?, jurisdiction_security = ?, status_time = ?, reporting_facility_security=? where data_source_uid = ?;",
        "update nbs_odse.Data_Source  set data_source_title = ?, desc_txt = ?, jurisdiction_security = ?, status_time = ?, reporting_facility_security=? where data_source_uid = ?",
    };

    /**
     * Counts the number of related reports.
     */
    private static final String[] COUNT_DATA_SOURCE_COLUMN =
    {
        "select count(*) as num from nbs_odse..Data_source_column where data_source_uid = ?;",
        "select count(*) as num from nbs_odse.Data_source_column  where data_source_uid = ?",
    };

    /**
     * Counts the number of related reports.
     */
    private static final String[] COUNT_REPORT =
    {
        "select count(*) as num from nbs_odse..Report where data_source_uid = ?;",
        "select count(*) as num from nbs_odse.Report  where data_source_uid = ?",
    };

    /**
     * Deletes a record.
     */
    private static final String[] DELETE =
    {
        "delete from nbs_odse..Data_Source where data_source_uid = ?;",
        "delete from nbs_odse.Data_Source  where data_source_uid = ?",
    };

    private static final String[] CODE_VALUE_GENERAL_SRTS = 
    {
        "SELECT DISTINCT code_set_nm from NBS_SRTE..CODE_VALUE_GENERAL ORDER BY code_set_nm",
        "SELECT DISTINCT code_set_nm from NBS_SRTE.CODE_VALUE_GENERAL ORDER BY code_set_nm",
    };
    	
    private static final String[] CODE_VALUE_CLINICAL_SRTS = 
    {
        "SELECT DISTINCT code_set_nm from NBS_SRTE..CODE_VALUE_CLINICAL",
        "SELECT DISTINCT code_set_nm from NBS_SRTE.CODE_VALUE_CLINICAL",
    };

    
 
    /**
     * Constructor.
     */
    public DataSourceDAO()
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
                r = rs.getLong("data_source_uid");
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
    public DataSourceDT view(long uid) throws NEDSSDAOSysException
    {
        // Create return variable.
        DataSourceDT dt = new DataSourceDT();
        // Create temp variables.
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        // Execute the command and package the data.
        try
        {
            con = this.getConnection();
            logger.warn(VIEW[SERVER]);
            logger.warn("data_source_uid=" + uid);
            ps = con.prepareStatement(VIEW[SERVER], ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ps.setLong(1, uid);
            rs = ps.executeQuery();
            dt.setDataSourceUID(0L);
            if(rs.next())
            {
                long data_source_uid = rs.getLong("data_source_uid");
                if(!rs.wasNull())
                {
                    dt.setDataSourceUID(data_source_uid);
                }
                dt.setDataSourceName(HTMLEncoder.encodeHtml(rs.getString("data_source_name").toUpperCase()));
                dt.setDataSourceTitle(HTMLEncoder.encodeHtml(rs.getString("data_source_title")));
                dt.setDescTxt(HTMLEncoder.encodeHtml(rs.getString("desc_txt")));
                dt.setJurisdictionSecurity(HTMLEncoder.encodeHtml(rs.getString("jurisdiction_security")));
                dt.setReporting_facility_security(HTMLEncoder.encodeHtml(rs.getString("reporting_facility_security")));
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
     * Validates a data source.
     * Determines whether the data source name describes a data mart that actually exists.
     * Only applies to nbs_odse because there is a JDBC DataSource defined for it.
     * There is no JDBC DataSource defined for the rdb, so if this dt refers to "rdb",
     * this function will return true without verifying it.
     * @param dt the record to validate.
     * @throws NEDSSDAOSysException if an error occurs.
     */
	public void validate(DataSourceDT dt) throws NEDSSDAOSysException {
		// Verify parameters.
		if (dt == null) {
			throw new NEDSSDAOSysException("dt is null");
		}

		// Create temp variables.
		Connection con = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		CallableStatement sProc = null;
		// Split data source name into component parts.
		String data_source_name_v = dt.getDataSourceName();
		String[] arrDSN = data_source_name_v.split("\\x2E");
		StringBuffer sbDSN = new StringBuffer();
		String database = arrDSN[0];
		String table = arrDSN[1];

		// Execute the command and package the data.
		try {
			if (database.equalsIgnoreCase("nbs_rdb")) {

				con = getConnection(NEDSSConstants.RDB);

				if (SERVER == ORA) {
					sbDSN.append(con.getMetaData().getUserName()).append(".");
				} else {
					sbDSN.append("rdb.");
				}

			} else if (database.equalsIgnoreCase("nbs_ods")) {
				sbDSN.append("nbs_odse.");
				con = getConnection(NEDSSConstants.ODS);
			} else if (database.equalsIgnoreCase("nbs_msg")) {
				sbDSN.append("nbs_msgoute.");
				con = getConnection(NEDSSConstants.MSGOUT);
			} else if (database.equalsIgnoreCase("nbs_srt")) {
				sbDSN.append("nbs_srte.");
				con = getConnection(NEDSSConstants.SRT);
			}

			if (SERVER == MSSQL) {
				sbDSN.append(".");
			}
			sbDSN.append(table);
			String strDSN = sbDSN.toString();
			
			String sQuery = "{call GETDATASOURCE_SP(?)}";
			sProc = con.prepareCall(sQuery);
			sProc.setString(1, strDSN);
			rs = sProc.executeQuery();
			rsmd = rs.getMetaData();
			
		} catch (Exception ex) {
			logger.fatal("Exception = " + ex.getMessage(), ex);
			String strMessage = ex.getMessage();
			if (strMessage.indexOf("Invalid object name") != -1) {
				throw new NEDSSDAOSysException(
						"There is no data mart by that name.  Please enter a name that matches the name of an existing data mart.");
			}
			if (strMessage.indexOf("does not exist") != -1) {
				throw new NEDSSDAOSysException(
						"There is no data mart by that name.  Please enter a name that matches the name of an existing data mart.");
			}
			throw new NEDSSDAOSysException(ex.getMessage());
		}
		// Close the connection.
		finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception ex) {
				}
			}
			if (sProc != null) {
				try {
					sProc.close();
				} catch (Exception ex) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception ex) {
				}
			}
		}
	}

    /**
     * Adds a record.
     * @param dt the record to add.
     * @throws NEDSSDAOSysException if an error occurs.
     */
    public void add(DataSourceDT dt) throws NEDSSDAOSysException
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
            ps.setLong(1, dt.getDataSourceUID());
            ps.setNull(2, Types.SMALLINT);
            ps.setString(3, "N");
            ps.setString(4, dt.getDataSourceName());
            ps.setString(5, dt.getDataSourceTitle());
            ps.setNull(6, Types.VARCHAR);
            ps.setString(7, dt.getDescTxt());
            ps.setNull(8, Types.TIMESTAMP);
            ps.setNull(9, Types.TIMESTAMP);
            ps.setString(10, dt.getJurisdictionSecurity());
            ps.setNull(11, Types.VARCHAR);
            ps.setNull(12, Types.VARCHAR);
            ps.setString(13, "A");
            ps.setTimestamp(14, status_time);
            ps.setString(15, dt.getReporting_facility_security());
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
                DataSourceDT dt = new DataSourceDT();
                dt.setDataSourceUID(0L);
                long data_source_uid = rs.getLong("data_source_uid");
                if(!rs.wasNull())
                {
                    dt.setDataSourceUID(data_source_uid);
                }
                dt.setDataSourceName(HTMLEncoder.encodeHtml(rs.getString("data_source_name").toUpperCase()));
                dt.setDataSourceTitle(HTMLEncoder.encodeHtml(rs.getString("data_source_title")));
                dt.setDescTxt(HTMLEncoder.encodeHtml(rs.getString("desc_txt")));
                dt.setJurisdictionSecurity(HTMLEncoder.encodeHtml(rs.getString("jurisdiction_security")));
                if(rs.getDate("status_time") != null)
                	dt.setStatus_time(new Timestamp(rs.getDate("status_time").getTime()));
                dt.setReporting_facility_security(HTMLEncoder.encodeHtml(rs.getString("reporting_facility_security")));
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
    public void edit(DataSourceDT dt) throws NEDSSDAOSysException
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
            ps.setString(1, dt.getDataSourceTitle());
            ps.setString(2, dt.getDescTxt());
            ps.setString(3, dt.getJurisdictionSecurity());
            ps.setTimestamp(4, status_time);
            ps.setString(5, dt.getReporting_facility_security());
            ps.setLong(6, dt.getDataSourceUID());
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
     * Counts the number of related records in the Data_source_column table.
     * @param uid points to the related record in Data_Source.
     * @return a count.
     * @throws NEDSSDAOSysException if an error occurs.
     */
    public int countDataSourceColumn(long uid) throws NEDSSDAOSysException
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
            logger.warn(COUNT_DATA_SOURCE_COLUMN[SERVER]);
            logger.warn("data_source_uid=" + uid);
            ps = con.prepareStatement(COUNT_DATA_SOURCE_COLUMN[SERVER], ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ps.setLong(1, uid);
            rs = ps.executeQuery();
            if(rs.next())
            {
                r = rs.getInt("num");
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
     * Counts the number of related records in the Report table.
     * @param uid points to the related record in Data_Source.
     * @return a count.
     * @throws NEDSSDAOSysException if an error occurs.
     */
    public int countReport(long uid) throws NEDSSDAOSysException
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
            logger.warn(COUNT_REPORT[SERVER]);
            logger.warn("data_source_uid=" + uid);
            ps = con.prepareStatement(COUNT_REPORT[SERVER], ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ps.setLong(1, uid);
            rs = ps.executeQuery();
            if(rs.next())
            {
                r = rs.getInt("num");
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
            logger.warn("data_source_uid=" + uid);
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
    
    public ArrayList<Object> getSRTCodeSetNames(String tableName) {
    	
        ArrayList<Object> al = new ArrayList<Object> ();

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        // Execute the command and package the data.
        try
        {
            con = this.getConnection();
            if(tableName != null && tableName.equalsIgnoreCase("CODE_VALUE_GENERAL"))
            	ps = con.prepareStatement(CODE_VALUE_GENERAL_SRTS[SERVER], ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            else if(tableName != null && tableName.equalsIgnoreCase("CODE_VALUE_CLINICAL"))
            	ps = con.prepareStatement(CODE_VALUE_CLINICAL_SRTS[SERVER], ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            
            rs = ps.executeQuery();
            while(rs.next())
            {            	
                al.add(rs.getString("code_set_nm"));
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

}
