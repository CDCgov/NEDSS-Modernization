package gov.cdc.nedss.reportadmin.dao;

import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.reportadmin.dt.DataSourceColumnDT;
import gov.cdc.nedss.reportadmin.dt.DataSourceDT;
import gov.cdc.nedss.systemservice.uidgenerator.UidClassCodes;
import gov.cdc.nedss.systemservice.uidgenerator.UidGeneratorHelper;
import gov.cdc.nedss.util.BMPBase;
import gov.cdc.nedss.util.HTMLEncoder;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.TreeMap;

import org.apache.log4j.Logger;

/**
 * Reads and writes the Data_source_column table.
 * @author Ed Jenkins
 */
public class DataSourceColumnDAO extends BMPBase
{

    /**
     * Logger.
     */
    private static final Logger logger = Logger.getLogger(DataSourceColumnDAO.class);

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
    
    private long dataSourceCodesetUid = -1;
    
    private static final String[] GET_NEXT_UID =
    {
        "select max(column_uid) + 1 as column_uid from nbs_odse..Data_source_column;",
        "select max(column_uid) + 1 as column_uid from nbs_odse.Data_source_column",
    };

    /**
     * Views a record.
     */
    private static final String[] VIEW =
    {
    	"select dsc.column_uid, dsc.column_max_len, dsc.column_name, dsc.column_title, dsc.column_type_code, dsc.data_source_uid, dsc.desc_txt, dsc.displayable, dsc.effective_from_time, dsc.effective_to_time, dsc.filterable, dsc.status_cd, dsc.status_time, dsce.code_desc_cd, dsce.data_source_codeset_uid, dsce.codeset_nm from nbs_odse..Data_source_column  dsc LEFT OUTER JOIN Data_Source_Codeset dsce ON dsc.column_uid = dsce.column_uid where dsc.column_uid = ?;",
    	"select dsc.column_uid, dsc.column_max_len, dsc.column_name, dsc.column_title, dsc.column_type_code, dsc.data_source_uid, dsc.desc_txt, dsc.displayable, dsc.effective_from_time, dsc.effective_to_time, dsc.filterable, dsc.status_cd, dsc.status_time, dsce.code_desc_cd, data_source_codeset_uid, dsce.codeset_nm from nbs_odse.Data_source_column  dsc LEFT OUTER JOIN Data_Source_Codeset dsce ON dsc.column_uid = dsce.column_uid where dsc.column_uid = ?",
    };

    /**
     * Adds a record.
     */
    private static final String[] ADD =
    {
        "insert into nbs_odse..Data_source_column(column_uid, column_max_len, column_name, column_title, column_type_code, data_source_uid, desc_txt, displayable, effective_from_time, effective_to_time, filterable, status_cd, status_time) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);",
        "insert into nbs_odse.Data_source_column (column_uid, column_max_len, column_name, column_title, column_type_code, data_source_uid, desc_txt, displayable, effective_from_time, effective_to_time, filterable, status_cd, status_time) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
        "insert into Data_Source_Codeset (data_source_codeset_uid, column_uid, code_desc_cd, codeset_nm, status_cd, status_time) values (?, ?, ?, ?, ?, ?)",
    };

    /**
     * Lists all records that belong to a given data source.
     */
    private static final String[] LIST =
    {
        "select ds1.column_uid, ds1.column_max_len, ds1.column_name, ds1.column_title, ds1.column_type_code, ds1.data_source_uid, ds1.desc_txt, ds1.displayable, ds1.effective_from_time, ds1.effective_to_time, ds1.filterable, ds1.status_cd, ds1.status_time, ds2.codeset_nm from nbs_odse..Data_source_column ds1 left outer join nbs_odse..Data_Source_Codeset ds2 on ds1.column_uid = ds2.column_uid where ds1.data_source_uid = ? order by ds1.column_name, ds1.column_uid",
        "select ds1.column_uid, ds1.column_max_len, ds1.column_name, ds1.column_title, ds1.column_type_code, ds1.data_source_uid, ds1.desc_txt, ds1.displayable, ds1.effective_from_time, ds1.effective_to_time, ds1.filterable, ds1.status_cd, ds1.status_time, ds2.codeset_nm from nbs_odse.Data_source_column ds1 left outer join nbs_odse.Data_Source_Codeset ds2 on ds1.column_uid = ds2.column_uid where ds1.data_source_uid = ? order by ds1.column_name, ds1.column_uid",
    };

    /**
     * Lists the UIDs of all records that belong to a given data source.
     */
    private static final String[] GET_ALL =
    {
        "select column_uid from nbs_odse..Data_source_column where data_source_uid = ? order by column_name, column_uid;",
        "select column_uid from nbs_odse.Data_source_column  where data_source_uid = ? order by column_name, column_uid",
    };

    /**
     * Edits a record.
     */
    private static final String[] EDIT =
    {
        "update nbs_odse..Data_source_column set column_uid = ?, column_name = ?, column_title = ?, column_type_code = ?, data_source_uid = ?, desc_txt = ?, displayable = ?, effective_from_time = ?, effective_to_time = ?, filterable = ?, status_cd = ?, status_time = ? where column_uid = ?;",
        "update nbs_odse.Data_source_column  set column_uid = ?, column_name = ?, column_title = ?, column_type_code = ?, data_source_uid = ?, desc_txt = ?, displayable = ?, effective_from_time = ?, effective_to_time = ?, filterable = ?, status_cd = ?, status_time = ? where column_uid = ?",
    };

    /**
     * Counts the number of related records in the Display_column table.
     */
    private static final String[] COUNT_DISPLAY_COLUMN =
    {
        "select count(*) as num from nbs_odse..Display_column where column_uid = ?;",
        "select count(*) as num from nbs_odse.Display_column  where column_uid = ?",
    };

    /**
     * Counts the number of related records in the Report_Filter table.
     */
    private static final String[] COUNT_REPORT_FILTER =
    {
        "select count(*) as num from nbs_odse..Report_Filter where column_uid = ?;",
        "select count(*) as num from nbs_odse.Report_Filter  where column_uid = ?",
    };

    private static final String[] COUNT_FILTER_VALUE = 
    {
    	"select count(*) as num from nbs_odse..Filter_Value where column_uid = ?;",
    	"select count(*) as num from nbs_odse.Filter_Value where column_uid = ?",
    };
    
    /**
     * Deletes a record.
     */
    private static final String[] DELETE =
    {
        "delete from nbs_odse..Data_source_column where column_uid = ?;",
        "delete from nbs_odse.Data_source_column  where column_uid = ?",
    };

    /**
     * Gets a map of column_uid to column_name for all columns that belong to a given data source and are displayable.
     */
    private static final String[] GET_DISPLAYABLE_NAME_MAP =
    {
        "select column_uid, column_name from nbs_odse..Data_source_column where data_source_uid = ? and displayable = 'Y' order by column_name, column_uid;",
        "select column_uid, column_name from nbs_odse.Data_source_column  where data_source_uid = ? and displayable = 'Y' order by column_name, column_uid",
    };

    /**
     * Gets a map of column_uid to column_name for all columns that belong to a given data source and are displayable.
     */
    private static final String[] GET_FILTERABLE_NAME_MAP =
    {
        "select column_uid, column_name from nbs_odse..Data_source_column where data_source_uid = ? and filterable = 'Y' order by column_name, column_uid;",
        "select column_uid, column_name from nbs_odse.Data_source_column  where data_source_uid = ? and filterable = 'Y' order by column_name, column_uid",
    };

    public static final String UPDATE_DATASOURCE_CODESET = "UPDATE DATA_SOURCE_CODESET SET CODE_DESC_CD=?, CODESET_NM=?, STATUS_TIME=? WHERE column_uid = ?";
    public static final String DELETE_DATASOURCE_CODESET = "DELETE FROM DATA_SOURCE_CODESET WHERE column_uid = ?";
    
    

    /**
     * Constructor.
     */
    public DataSourceColumnDAO()
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
                r = rs.getLong("column_uid");
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
    public DataSourceColumnDT view(long uid) throws NEDSSDAOSysException
    {
        // Create return variable.
        DataSourceColumnDT dt = new DataSourceColumnDT();
        // Create temp variables.
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        // Execute the command and package the data.
        try
        {
            con = this.getConnection();
            logger.warn(VIEW[SERVER]);
            logger.warn("column_uid=" + uid);
            ps = con.prepareStatement(VIEW[SERVER], ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ps.setLong(1, uid);
            rs = ps.executeQuery();
            dt.setColumnUID(0L);
            if(rs.next())
            {
                long column_uid = rs.getLong("column_uid");
                if(!rs.wasNull())
                {
                    dt.setColumnUID(column_uid);
                }
                int column_max_len = rs.getInt("column_max_len");
                if(!rs.wasNull())
                {
                    dt.setColumnMaxLen(column_max_len);
                }
                dt.setColumnName(HTMLEncoder.encodeHtml(rs.getString("column_name")));
                dt.setColumnTitle(HTMLEncoder.encodeHtml(rs.getString("column_title")));
                dt.setColumnTypeCode(HTMLEncoder.encodeHtml(rs.getString("column_type_code")));
                long data_source_uid = rs.getLong("data_source_uid");
                if(!rs.wasNull())
                {
                    dt.setDataSourceUID(data_source_uid);
                }
                dt.setDescTxt(HTMLEncoder.encodeHtml(rs.getString("desc_txt")));
                dt.setDisplayable(HTMLEncoder.encodeHtml(rs.getString("displayable")));
                dt.setFilterable(HTMLEncoder.encodeHtml(rs.getString("filterable")));
                dt.setCodeDescCd(HTMLEncoder.encodeHtml(rs.getString("code_desc_cd")));
                dt.setDataSourceCodesetUid(rs.getString("data_source_codeset_uid"));
                dt.setCodesetNm(rs.getString("codeset_nm"));

                
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
     * getDSMetaData fetches all the column names of the table/view
     * @param dtDS
     * @return
     * @throws NEDSSDAOSysException
     */
    public ArrayList<Object> getDSMetaData(DataSourceDT dtDS) throws NEDSSDAOSysException {
    	
    	ArrayList<Object> al = new ArrayList<Object> ();
        Connection con = null;
        CallableStatement sProc = null;
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;    	
        try
        {
	        // Split data source name into component parts.
	        String data_source_name = dtDS.getDataSourceName();
	        String[] arrDSN = data_source_name.split("\\x2E");
	        StringBuffer sbDSN = new StringBuffer();
	        String database = arrDSN[0];
			String table = arrDSN[1];

			// Execute the command and package the data.
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
			
            int x = 1;
            int y = rsmd.getColumnCount();

            for(x=1; x<=y; x++)
            {
            	DataSourceColumnDT dt = new DataSourceColumnDT();
                int column_max_len = rsmd.getColumnDisplaySize(x);
                int intType = rsmd.getColumnType(x);
                switch(intType)
                {
                    case Types.CHAR:      { dt.setColumnTypeCode("STRING");   break; }
                    case Types.BIGINT:    { dt.setColumnTypeCode("INTEGER");  break; }
                    case Types.DECIMAL:   { dt.setColumnTypeCode("INTEGER");  break; }
                    case Types.DOUBLE:    { dt.setColumnTypeCode("INTEGER");  break; }
                    case Types.FLOAT:     { dt.setColumnTypeCode("INTEGER");  break; }
                    case Types.INTEGER:   { dt.setColumnTypeCode("INTEGER");  break; }
                    case Types.NUMERIC:   { dt.setColumnTypeCode("INTEGER");  break; }
                    case Types.REAL:      { dt.setColumnTypeCode("INTEGER");  break; }
                    case Types.SMALLINT:  { dt.setColumnTypeCode("INTEGER");  break; }
                    case Types.TIMESTAMP: { dt.setColumnTypeCode("DATETIME"); break; }
                    case Types.TINYINT:   { dt.setColumnTypeCode("INTEGER");  break; }
                    case Types.VARCHAR:   { dt.setColumnTypeCode("STRING");   break; }
                    default:              { dt.setColumnTypeCode("STRING");   break; }
                }                
            	dt.setColumnName(rsmd.getColumnLabel(x));
                dt.setColumnMaxLen(column_max_len);
           	
                al.add(dt);
            }
        }
        catch(Exception ex)
        {
        	logger.fatal("Exception = "+ex.getMessage(), ex);
            throw new NEDSSDAOSysException(ex.getMessage());
        }

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
            if(sProc != null)
            {
                try
                {
                	sProc.close();
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
    	return al;
    }
    /**
     * Initializes a series of records for a new data source, but only if the data source is in nbs_ods.
     * If it's in the rdb, this function will return an empty list.
     * @param dtDS the related DataSourceDT.
     * @return a list of records.
     * @throws NEDSSDAOSysException if an error occurs.
     */
    public ArrayList<Object> init(DataSourceDT dtDS) throws NEDSSDAOSysException
    {
        // Verify parameters.
        if(dtDS == null)
        {
            throw new NEDSSDAOSysException("dt is null");
        }
        // Create return variable.
        ArrayList<Object> al = new ArrayList<Object> ();
        Connection con = null;
        // Create temp variables.
        CallableStatement sProc = null;
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;
        
        try
        {
	        // Split data source name into component parts.
	        long data_source_uid = dtDS.getDataSourceUID();
	        String data_source_name = dtDS.getDataSourceName();
	        String[] arrDSN = data_source_name.split("\\x2E");
	        StringBuffer sbDSN = new StringBuffer();
	        String database = arrDSN[0];
			String table = arrDSN[1];

			// Execute the command and package the data.
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
	        // Assemble SQL.
	        String sQuery = "{call GETDATASOURCE_SP(?)}";
			sProc = con.prepareCall(sQuery);
			sProc.setString(1, strDSN);
			rs = sProc.executeQuery();
			rsmd = rs.getMetaData();
			
            int x = 1;
            int y = rsmd.getColumnCount();
            long column_uid = this.getNextUID();
            for(x=1; x<=y; x++)
            {
                DataSourceColumnDT dt = new DataSourceColumnDT();
                dt.setColumnUID(column_uid);
                column_uid++;
                int column_max_len = rsmd.getColumnDisplaySize(x);
                int intType = rsmd.getColumnType(x);
                switch(intType)
                {
                    case Types.CHAR:    { dt.setColumnMaxLen(column_max_len); break; }
                    case Types.VARCHAR: { dt.setColumnMaxLen(column_max_len); break; }
                    default:            { dt.setColumnMaxLen(0);              break; }
                }
                dt.setColumnName(rsmd.getColumnName(x));
                dt.setColumnTitle(rsmd.getColumnLabel(x));
                switch(intType)
                {
                    case Types.CHAR:      { dt.setColumnTypeCode("STRING");   break; }
                    case Types.BIGINT:    { dt.setColumnTypeCode("INTEGER");  break; }
                    case Types.DECIMAL:   { dt.setColumnTypeCode("INTEGER");  break; }
                    case Types.DOUBLE:    { dt.setColumnTypeCode("INTEGER");  break; }
                    case Types.FLOAT:     { dt.setColumnTypeCode("INTEGER");  break; }
                    case Types.INTEGER:   { dt.setColumnTypeCode("INTEGER");  break; }
                    case Types.NUMERIC:   { dt.setColumnTypeCode("INTEGER");  break; }
                    case Types.REAL:      { dt.setColumnTypeCode("INTEGER");  break; }
                    case Types.SMALLINT:  { dt.setColumnTypeCode("INTEGER");  break; }
                    case Types.TIMESTAMP: { dt.setColumnTypeCode("DATETIME"); break; }
                    case Types.TINYINT:   { dt.setColumnTypeCode("INTEGER");  break; }
                    case Types.VARCHAR:   { dt.setColumnTypeCode("STRING");   break; }
                    default:              { dt.setColumnTypeCode("STRING");   break; }
                }
                dt.setDataSourceUID(data_source_uid);
                dt.setDescTxt(rsmd.getColumnLabel(x));
                dt.setDisplayable("Y");
                dt.setFilterable("Y");
                dt.validate();
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
            if(sProc != null)
            {
                try
                {
                	sProc.close();
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
     * Initializes a series of records for a new data source.
     * Does nothing if the list is empty.
     * @param al a list of records to add.
     * @throws NEDSSDAOSysException if an error occurs.
     */
    public void init(ArrayList<Object>  al) throws NEDSSDAOSysException
    {
        // Verify parameters.
        if(al == null)
        {
            throw new NEDSSDAOSysException("al is null");
        }
        if(al.size() == 0)
        {
            return;
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
           Iterator<Object>  i = al.iterator();
            while(i.hasNext())
            {
                DataSourceColumnDT dt = (DataSourceColumnDT)i.next();
                ps.clearParameters();
                ps.setLong(1, dt.getColumnUID());
                if(dt.getColumnTypeCode().equalsIgnoreCase("STRING"))
                {
                    ps.setInt(2, dt.getColumnMaxLen());
                }
                else
                {
                    ps.setNull(2, Types.INTEGER);
                }
                ps.setString(3, dt.getColumnName());
                ps.setString(4, dt.getColumnTitle());
                ps.setString(5, dt.getColumnTypeCode());
                ps.setLong(6, dt.getDataSourceUID());
                ps.setString(7, dt.getDescTxt());
                ps.setString(8, dt.getDisplayable());
                ps.setNull(9, Types.TIMESTAMP);
                ps.setNull(10, Types.TIMESTAMP);
                ps.setString(11, dt.getFilterable());
                ps.setString(12, "A");
                ps.setTimestamp(13, status_time);
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

    /**
     * Adds a record.
     * @param dt the record to add.
     * @throws NEDSSDAOSysException if an error occurs.
     */
    public void add(DataSourceColumnDT dt) throws NEDSSDAOSysException
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
            ps.setLong(1, dt.getColumnUID());

            ps.setInt(2, dt.getColumnMaxLen());
            ps.setString(3, dt.getColumnName());
            ps.setString(4, dt.getColumnTitle());
            ps.setString(5, dt.getColumnTypeCode());
            ps.setLong(6, dt.getDataSourceUID());
            ps.setString(7, dt.getDescTxt());
            ps.setString(8, dt.getDisplayable());
            ps.setNull(9, Types.TIMESTAMP);
            ps.setNull(10, Types.TIMESTAMP);
            ps.setString(11, dt.getFilterable());
            ps.setString(12, "A");
            ps.setTimestamp(13, status_time);
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
        //Insert into Data_Source_Codeset if selected
        if(dt.getCodesetNm() != null && dt.getCodesetNm().length() > 0) {
        		addDataSourceCodeSet(dt);
        }        
    }
    
    /**
     * If an SRT Codeset is selected for a DataSourceColumn, that needs to be persisted.
     * @param dt
     * @throws NEDSSDAOSysException
     */
    private void addDataSourceCodeSet(DataSourceColumnDT dt) throws NEDSSDAOSysException {
    	
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		UidGeneratorHelper uidGen = null;
		int resultCount = 0;
		
		try {
			uidGen = new UidGeneratorHelper();
			dataSourceCodesetUid = uidGen.getNbsIDLong(UidClassCodes.NBS_CLASS_CODE).longValue();
			
		} catch (Exception e1) {
			logger.fatal("Exception = "+e1.getMessage(), e1);
			throw new NEDSSSystemException(
				  	"Error in UIDGenerator in generating a dataSourceCodesetUid= " + dataSourceCodesetUid);
		}
		
		try {
			
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(ADD[2], ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

            Date d = new Date();
            long l = d.getTime();
            Timestamp status_time = new Timestamp(l);
            
			preparedStmt.setLong(1, dataSourceCodesetUid);
			preparedStmt.setLong(2, dt.getColumnUID());
			preparedStmt.setString(3, dt.getCodeDescCd());
			preparedStmt.setString(4, dt.getCodesetNm());
			preparedStmt.setString(5, NEDSSConstants.STATUS_ACTIVE);
			preparedStmt.setTimestamp(6, status_time);
			
			resultCount = preparedStmt.executeUpdate();

			if(resultCount != 1)
			{
				throw new NEDSSSystemException("DataSourceColumnDAO:addDataSourceCodeSet Error: cannot be inserted into Data_Source_CodeSet table, " + "resultCount = " + resultCount);
			}			
			
		} catch (NEDSSSystemException e) {
			logger.fatal("NEDSSSystemException = "+e.getMessage(), e);
			throw new NEDSSSystemException("SQLException while inserting reportfilter into Data_Source_CodeSet: \n" + e.toString() +" \n" + e.getMessage());			

		} catch (SQLException e) {
			logger.fatal("SQLException = "+e.getMessage(), e);
			throw new NEDSSSystemException("SQLException while inserting reportfilter into Data_Source_CodeSet: \n" + e.toString() +" \n" + e.getMessage());			

		} finally {
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}		
    }

    /**
     * Lists all records that belong to a given data source.
     * @param uid the UID of the related data source.
     * @return a list of records.
     * @throws NEDSSDAOSysException if an error occurs.
     */
    public ArrayList<Object> list(long uid) throws NEDSSDAOSysException
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
            ps.setLong(1, uid);
            rs = ps.executeQuery();
            while(rs.next())
            {
                DataSourceColumnDT dt = new DataSourceColumnDT();
                dt.setColumnUID(0L);
                long column_uid = rs.getLong("column_uid");
                if(!rs.wasNull())
                {
                    dt.setColumnUID(column_uid);
                }
                int column_max_len = rs.getInt("column_max_len");
                if(!rs.wasNull())
                {
                    dt.setColumnMaxLen(column_max_len);
                }
                dt.setColumnName(HTMLEncoder.encodeHtml(rs.getString("column_name")));
                dt.setColumnTitle(HTMLEncoder.encodeHtml(rs.getString("column_title")));
                dt.setColumnTypeCode(HTMLEncoder.encodeHtml(rs.getString("column_type_code")));
                long data_source_uid = rs.getLong("data_source_uid");
                if(!rs.wasNull())
                {
                    dt.setDataSourceUID(data_source_uid);
                }
                dt.setDescTxt(HTMLEncoder.encodeHtml(rs.getString("desc_txt")));
                dt.setDisplayable(HTMLEncoder.encodeHtml(rs.getString("displayable")));
                dt.setFilterable(HTMLEncoder.encodeHtml(rs.getString("filterable")));
                String codeset = rs.getString("codeset_nm") == null ? "" : HTMLEncoder.encodeHtml(rs.getString("codeset_nm"));
                if(codeset.startsWith("CODE_VALUE_GENERAL") || codeset.startsWith("V_JURISDICTION_CODE") || codeset.startsWith("V_STATE_COUNTY_CODE") || codeset.startsWith("V_CONDITION_CODE") || codeset.startsWith("PROGRAM_AREA_CODE")  || codeset.startsWith("V_RACE_CODE"))
                	codeset = codeset.substring(codeset.indexOf(".")+1,codeset.length());
                dt.setCodesetNm(codeset);
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
     * Gets the column_uid of all records that belong to a given data source.
     * @param uid the UID of the related data source.
     * @return a list of UIDs.
     * @throws NEDSSDAOSysException if an error occurs.
     */
    public ArrayList<Object> getAll(long uid) throws NEDSSDAOSysException
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
            ps = con.prepareStatement(GET_ALL[SERVER], ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ps.setLong(1, uid);
            rs = ps.executeQuery();
            while(rs.next())
            {
                long column_uid = rs.getLong("column_uid");
                if(rs.wasNull())
                {
                    continue;
                }
                Long L = new Long(column_uid);
                al.add(L);
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
    public void edit(DataSourceColumnDT dt) throws NEDSSDAOSysException
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
            ps.setLong(1, dt.getColumnUID());
            /*
            if(dt.getColumnTypeCode().equalsIgnoreCase("STRING"))
            {
                ps.setInt(2, dt.getColumnMaxLen());
            }
            else
            {
                ps.setNull(2, Types.INTEGER);
            }
            */
            ps.setString(2, dt.getColumnName());
            ps.setString(3, dt.getColumnTitle());
            ps.setString(4, dt.getColumnTypeCode());
            ps.setLong(5, dt.getDataSourceUID());
            ps.setString(6, dt.getDescTxt());
            ps.setString(7, dt.getDisplayable());
            ps.setNull(8, Types.TIMESTAMP);
            ps.setNull(9, Types.TIMESTAMP);
            ps.setString(10, dt.getFilterable());
            ps.setString(11, "A");
            ps.setTimestamp(12, status_time);
            ps.setLong(13, dt.getColumnUID());
            ps.executeUpdate();
        }
        catch(Exception ex)
        { 
        	ex.printStackTrace();
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
        
        //Edit Data_Source_Codeset if selected / delete if non-CodeSet Type STRING used  
        if(dt.getCodesetNm() == null && (dt.getDataSourceCodesetUid() != null && dt.getDataSourceCodesetUid().length() > 0))  	deleteDataSourceCodeSet(dt.getColumnUID());
        else if(dt.getDataSourceCodesetUid() == null && (dt.getCodesetNm()!= null && dt.getCodesetNm().length() > 0 ))        	addDataSourceCodeSet(dt);
        else if((dt.getCodesetNm() != null && dt.getCodesetNm().length() > 0) && (dt.getDataSourceCodesetUid() != null && dt.getDataSourceCodesetUid().length() > 0))       	updateDataSourceCodeSet(dt);
                 
    }
    
    private void updateDataSourceCodeSet(DataSourceColumnDT dt) {

        Connection con = null;
        PreparedStatement ps = null;
        Date d = new Date();
        long l = d.getTime();
        Timestamp status_time = new Timestamp(l);
        
		try {			
			con = getConnection();
			ps = con.prepareStatement(UPDATE_DATASOURCE_CODESET);
			
			ps.setString(1, dt.getCodeDescCd());
			ps.setString(2, dt.getCodesetNm());
			ps.setTimestamp(3, status_time);			
			ps.setLong(4, dt.getColumnUID());
			int resultCount = 0;
			resultCount = ps.executeUpdate();
			if (resultCount != 1) {
				throw new NEDSSSystemException("Error: cannot update DATASOURCE_CODESET TABLE!! resultCount = " + resultCount);
			}
		} catch (SQLException sex) {
			logger.fatal("Exception = "+sex.getMessage(), sex);
			throw new NEDSSSystemException("SQLException while updating DATASOURCE_CODESET"+ "; id = " + dt.getColumnUID() + " :\n" + sex.getMessage());
		}
        finally {
           closeStatement(ps);
           releaseConnection(con);
        } 
    	
    }
    /**
     * 
     * @param dt
     * @throws NEDSSDAOSysException
     */
    private void deleteDataSourceCodeSet(long uid) throws NEDSSDAOSysException {
    	
        Connection con = null;
        PreparedStatement ps = null;
        
		try {			
			con = getConnection();
			ps = con.prepareStatement(DELETE_DATASOURCE_CODESET);
			ps.setLong(1, uid);
			int resultCount = 0;
			resultCount = ps.executeUpdate();
	
			if (resultCount != 1) {
				throw new NEDSSSystemException("Error: cannot delete from DATASOURCE_CODESET TABLE!! resultCount = " + resultCount);
			}
		} catch (SQLException sex) {
			logger.fatal("Exception = "+sex.getMessage(), sex);
			throw new NEDSSSystemException("SQLException while removing DATASOURCE_CODESET"+ "; id = " + uid + " :\n" + sex.getMessage());
		}
        finally {
           closeStatement(ps);
           releaseConnection(con);
        }    
    }

    /**
     * Counts the number of related records in the Display_column table.
     * @param uid points to the related record in Data_source_column.
     * @return a count.
     * @throws NEDSSDAOSysException if an error occurs.
     */
    public int countDisplayColumn(long uid) throws NEDSSDAOSysException
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
            logger.warn("column_uid=" + uid);
            ps = con.prepareStatement(COUNT_DISPLAY_COLUMN[SERVER], ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
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
     * Counts the number of related records in the Report_Filter table.
     * @param uid points to the related record in Data_source_column.
     * @return a count.
     * @throws NEDSSDAOSysException if an error occurs.
     */
    public int countReportFilter(long uid) throws NEDSSDAOSysException
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
            logger.warn("column_uid=" + uid);
            ps = con.prepareStatement(COUNT_REPORT_FILTER[SERVER], ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
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
     * 
     * @param uid
     * @return
     * @throws NEDSSDAOSysException
     */
    public int countFilterValue(long uid) throws NEDSSDAOSysException
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
            logger.warn(COUNT_FILTER_VALUE[SERVER]);
            logger.warn("column_uid=" + uid);
            ps = con.prepareStatement(COUNT_FILTER_VALUE[SERVER], ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
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
    public void delete(DataSourceColumnDT dt) throws NEDSSDAOSysException
    {
    	if(dt.getDataSourceCodesetUid() != null && dt.getDataSourceCodesetUid().length() > 0)
    		deleteDataSourceCodeSet(dt.getColumnUID());
    	
        // Create temp variables.
        Connection con = null;
        PreparedStatement ps = null;
        // Execute the command and package the data.
        try
        {
            con = this.getConnection();
            logger.warn(DELETE[SERVER]);
            logger.warn("column_uid=" + dt.getColumnUID());
            ps = con.prepareStatement(DELETE[SERVER], ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ps.setLong(1, dt.getColumnUID());
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
     * Gets a map of column_uid to column_name for all columns that belong to a given data source and are displayable.
     * @param data_source_uid the data source the columns belong to.
     * @return a map.
     * @throws NEDSSDAOSysException if an error occurs.
     */
    public TreeMap<Object,Object> getDisplayableNameMap(long data_source_uid) throws NEDSSDAOSysException
    {
        // Create return variable.
        TreeMap<Object,Object> tm = new TreeMap<Object,Object>();
        // Create temp variables.
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        // Execute the command and package the data.
        try
        {
            con = this.getConnection();
            logger.warn(GET_DISPLAYABLE_NAME_MAP[SERVER]);
            ps = con.prepareStatement(GET_DISPLAYABLE_NAME_MAP[SERVER], ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ps.setLong(1, data_source_uid);
            rs = ps.executeQuery();
            while(rs.next())
            {
                long column_uid = rs.getLong("column_uid");
                Long L = new Long(column_uid);
                String column_name = rs.getString("column_name");
                tm.put(L, column_name);
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
        return tm;
    }

    /**
     * Gets a map of column_name to column_uid for all columns that belong to a given data source and are filterable.
     * @param data_source_uid the data source the columns belong to.
     * @return a map.
     * @throws NEDSSDAOSysException if an error occurs.
     */
    public TreeMap<Object,Object> getFilterableNameMap(long data_source_uid) throws NEDSSDAOSysException
    {
        // Create return variable.
        TreeMap<Object,Object> tm = new TreeMap<Object,Object>();
        // Create temp variables.
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        // Execute the command and package the data.
        try
        {
            con = this.getConnection();
            logger.warn(GET_FILTERABLE_NAME_MAP[SERVER]);
            ps = con.prepareStatement(GET_FILTERABLE_NAME_MAP[SERVER], ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ps.setLong(1, data_source_uid);
            rs = ps.executeQuery();
            while(rs.next())
            {
                long column_uid = rs.getLong("column_uid");
                Long L = new Long(column_uid);
                String column_name = HTMLEncoder.encodeHtml(rs.getString("column_name"));
                tm.put(column_name, L);
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
        return tm;
    }

}
