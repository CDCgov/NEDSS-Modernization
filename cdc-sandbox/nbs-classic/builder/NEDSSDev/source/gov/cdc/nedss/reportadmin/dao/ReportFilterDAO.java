package gov.cdc.nedss.reportadmin.dao;

import gov.cdc.nedss.entity.sqlscript.NEDSSSqlQuery;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.reportadmin.dt.ReportFilterDT;
import gov.cdc.nedss.systemservice.uidgenerator.UidClassCodes;
import gov.cdc.nedss.systemservice.uidgenerator.UidGeneratorHelper;
import gov.cdc.nedss.util.BMPBase;
import gov.cdc.nedss.util.HTMLEncoder;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;
import java.util.TreeMap;

import org.apache.log4j.Logger;

/**
 * Reads and writes the Report_Filter table.
 * @author Ed Jenkins
 */
public class ReportFilterDAO extends BMPBase
{

    /**
     * Logger.
     */
    private static final Logger logger = Logger.getLogger(ReportFilterDAO.class);

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
        "select max(report_filter_uid) + 1 as report_filter_uid from nbs_odse..Report_Filter;",
        "select max(report_filter_uid) + 1 as report_filter_uid from nbs_odse.Report_Filter",
    };

    /**
     * Views a record.
     */
    private static final String[] VIEW =
    {
        "select report_filter_uid, report_uid, data_source_uid, filter_uid, status_cd, max_value_cnt, min_value_cnt, column_uid from nbs_odse..Report_Filter where report_filter_uid = ?;",
        "select report_filter_uid, report_uid, data_source_uid, filter_uid, status_cd, max_value_cnt, min_value_cnt, column_uid from nbs_odse.Report_Filter  where report_filter_uid = ?",
    };

    /**
     * Adds a record.
     */
    private static final String[] ADD =
    {
        "insert into nbs_odse..Report_Filter(report_filter_uid, report_uid, data_source_uid, filter_uid, status_cd, max_value_cnt, min_value_cnt, column_uid) values (?, ?, ?, ?, ?, ?, ?, ?);",
        "insert into nbs_odse.Report_Filter (report_filter_uid, report_uid, data_source_uid, filter_uid, status_cd, max_value_cnt, min_value_cnt, column_uid) values (?, ?, ?, ?, ?, ?, ?, ?)",
    };

    /**
     * Lists all records that belong to a given report.
     */
    private static final String[] LIST =
    {
    	"select report_filter_uid, report_uid, data_source_uid, filter_uid, status_cd, max_value_cnt, min_value_cnt, column_uid from nbs_odse..Report_Filter where data_source_uid = ? and report_uid = ?;",
        "select report_filter_uid, report_uid, data_source_uid, filter_uid, status_cd, max_value_cnt, min_value_cnt, column_uid from nbs_odse.Report_Filter  where data_source_uid = ? and report_uid = ?",
    };

    /**
     * Lists the names of all filters that belong to a given report.
     */
    private static final String[] LIST_NAME =
    {
        "select rf.report_filter_uid, fc.filter_name from nbs_odse..Filter_code fc inner join nbs_odse..Report_Filter rf on fc.filter_uid = rf.filter_uid where rf.data_source_uid = ? and rf.report_uid = ? order by fc.filter_name;",
        "select rf.report_filter_uid, fc.filter_name from nbs_odse.Filter_code  fc inner join nbs_odse.Report_Filter rf on fc.filter_uid = rf.filter_uid where rf.data_source_uid = ? and rf.report_uid = ? order by fc.filter_name",
    };

    /**
     * Edits a record.
     */
    private static final String[] EDIT =
    {
        "update nbs_odse..Report_Filter set report_filter_uid = ?, report_uid = ?, data_source_uid = ?, filter_uid = ?, status_cd = ?, max_value_cnt = ?, min_value_cnt = ?, column_uid = ? where report_filter_uid = ?;",
        "update nbs_odse.Report_Filter  set report_filter_uid = ?, report_uid = ?, data_source_uid = ?, filter_uid = ?, status_cd = ?, max_value_cnt = ?, min_value_cnt = ?, column_uid = ? where report_filter_uid = ?",
    };

    /**
     * Counts the number of related filter values.
     */
    private static final String[] COUNT_FILTER_VALUE =
    {
        "select count(*) as num from nbs_odse..Filter_value where report_filter_uid = ?;",
        "select count(*) as num from nbs_odse.Filter_value  where report_filter_uid = ?",
    };

    /**
     * Deletes a record.
     */
    private static final String[] DELETE =
    {
        "delete from nbs_odse..Report_Filter where report_filter_uid = ?;",
        "delete from nbs_odse.Report_Filter  where report_filter_uid = ?",
    };

    /**
     * Deletes all records that belong to a given report.
     */
    private static final String[] DELETE_ALL =
    {
        "delete from nbs_odse..Report_Filter where data_source_uid = ? and report_uid = ?;",
        "delete from nbs_odse.Report_Filter  where data_source_uid = ? and report_uid = ?",
    };

    /**
     * Lists all records from Filter_code that are not used in Report_Filter for a given report.
     */
    private static final String[] LIST_AVAILABLE_FILTER_CODE =
    {
        "select fc.filter_uid, fc.filter_name from nbs_odse..Filter_code fc where fc.filter_uid not in ( select rf.filter_uid from nbs_odse..Report_Filter rf where (rf.data_source_uid = ?) and (rf.report_uid = ?) ) order by fc.filter_name;",
        "select fc.filter_uid, fc.filter_name from nbs_odse.Filter_code  fc where fc.filter_uid not in ( select rf.filter_uid from nbs_odse.Report_Filter  rf where (rf.data_source_uid = ?) and (rf.report_uid = ?) ) order by fc.filter_name",
    };

    public static final String SELECT_REPORTFILTER =
    	"SELECT rf.report_filter_uid \"reportFilterUid\", " +
    	"rf.report_uid \"reportUid\", " +
    	"rf.data_source_uid \"dataSourceUid\", " +
    	"rf.filter_uid \"filterUid\", " +
    	"rf.status_cd \"statusCd\", " +
    	"rf.max_value_cnt \"maxValueCnt\", " +
    	"rf.min_value_cnt \"minValueCnt\", " +
    	"rf.column_uid \"columnUid\", " +
    	"rfv.report_filter_validation_uid \"reportFilterValidationUid\", " +
    	"rfv.report_filter_ind \"reportFilterInd\" " +
    	"FROM report_filter rf " +
    	"LEFT OUTER JOIN " +
    	"report_filter_validation rfv ON rf.report_filter_uid = rfv.report_filter_uid " +
    	"WHERE rf.report_filter_uid = ?";

    public static final String SELECT_ALL_REPORTFILTERS = 
    	
    	"SELECT RF.filter_uid, RF.report_filter_uid, FC.filter_name, RF.min_value_cnt, RF.max_value_cnt, RF.column_uid, " +
        "RFV.report_filter_ind " +
        "FROM Report_Filter RF INNER JOIN " +
        "Filter_code FC ON RF.filter_uid = FC.filter_uid LEFT OUTER JOIN " +
        "Report_Filter_Validation RFV ON RF.report_filter_uid = RFV.report_filter_uid " +
        "WHERE RF.data_source_uid = ? and RF.report_uid = ? " + 
        "order by RF.report_filter_uid";
    	
    
    public static final String INSERT_REPORTFILTER_VALIDATION = "INSERT INTO report_filter_validation(report_filter_validation_uid, report_filter_uid, report_filter_ind,status_cd,status_time) VALUES(?,?,?,?,?)";
    public static final String DELETE_REPORTFILTER_VALIDATION = "DELETE FROM report_filter_validation WHERE report_filter_uid = ?";
    public static final String UPDATE_REPORTFILTER_VALIDATION = "UPDATE report_filter_validation SET report_filter_ind=? where report_filter_uid = ?";
    private long reportfilterValidationUID = -1;
    

    /**
     * Constructor.
     */
    public ReportFilterDAO()
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
                r = rs.getLong("report_filter_uid");
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
    public ReportFilterDT view(long uid) throws NEDSSDAOSysException
    {
        // Create return variable.
        ReportFilterDT dt = new ReportFilterDT();
        // Create temp variables.
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        // Execute the command and package the data.
        try
        {
            con = this.getConnection();
            logger.warn(VIEW[SERVER]);
            logger.warn("report_filter_uid=" + uid);
            ps = con.prepareStatement(SELECT_REPORTFILTER, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ps.setLong(1, uid);
            rs = ps.executeQuery();
            dt.setReportFilterUID(0L);
            if(rs.next())
            {
                long report_filter_uid = rs.getLong("reportFilterUid");
                if(!rs.wasNull())
                {
                    dt.setReportFilterUID(report_filter_uid);
                }
                long report_uid = rs.getLong("reportUid");
                if(!rs.wasNull())
                {
                    dt.setReportUID(report_uid);
                }
                long data_source_uid = rs.getLong("dataSourceUid");
                if(!rs.wasNull())
                {
                    dt.setDataSourceUID(data_source_uid);
                }
                long filter_uid = rs.getLong("filterUid");
                if(!rs.wasNull())
                {
                    dt.setFilterUID(filter_uid);
                }
                int max_value_cnt = rs.getInt("maxValueCnt");
                if(!rs.wasNull())
                {
                    dt.setMaxValueCount(max_value_cnt);
                }
                int min_value_cnt = rs.getInt("minValueCnt");
                if(!rs.wasNull())
                {
                    dt.setMinValueCount(min_value_cnt);
                }
                long column_uid = rs.getLong("columnUid");
                if(!rs.wasNull())
                {
                    dt.setColumnUID(column_uid);
                }
                String report_filter_validation_uid = rs.getString("reportFilterValidationUid");
                if(!rs.wasNull())
                	dt.setReportFilterValidationUid(HTMLEncoder.encodeHtml(report_filter_validation_uid));

                String report_filter_ind = rs.getString("reportFilterInd");
                if(!rs.wasNull())
                	dt.setReportFilterInd(HTMLEncoder.encodeHtml(report_filter_ind));
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
    public void add(ReportFilterDT dt) throws NEDSSDAOSysException
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
            ps.setLong(1, dt.getReportFilterUID());
            ps.setLong(2, dt.getReportUID());
            ps.setLong(3, dt.getDataSourceUID());
            ps.setLong(4, dt.getFilterUID());
            ps.setString(5, "A");
            ps.setInt(6, dt.getMaxValueCount());
            ps.setInt(7, dt.getMinValueCount());
            if(dt.getColumnUID() == 0L)
            {
                ps.setNull(8, Types.BIGINT);
            }
            else
            {
                ps.setLong(8, dt.getColumnUID());
            }
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
        insertReportFilterValidation(dt);
    }
	private void insertReportFilterValidation(ReportFilterDT reportFilterDT) throws NEDSSSystemException {

		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		UidGeneratorHelper uidGen = null;
		int resultCount = 0;
        Date d = new Date();
        long l = d.getTime();
        Timestamp status_time = new Timestamp(l);

 		try
		{
			uidGen = new UidGeneratorHelper();
			reportfilterValidationUID = uidGen.getNbsIDLong(UidClassCodes.NBS_CLASS_CODE).longValue();
			dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(NEDSSSqlQuery.INSERT_ENTITY);

			int i = 1;
			preparedStmt.setLong(i++, reportfilterValidationUID);
			preparedStmt.setString(i++, NEDSSConstants.REPORTFILTER_VALIDATION);
			resultCount = preparedStmt.executeUpdate();
		}
		catch(SQLException sex)
		{
			logger.fatal("SQLException  = "+sex.getMessage(), sex);
			throw new NEDSSSystemException("SQLException while inserting " +
							"uid for REPORT_FILTER_VALIDATION TABLE: \n" + sex.toString() + " \n" + sex.getMessage());
		}
		catch(Exception ex)
		{
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException("Exception while inserting " +
							"uid for REPORT_FILTER_VALIDATION TABLE: \n" + ex.toString() + " \n" + ex.getMessage());
		}
		finally
		{
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}

        //insert into REPORT_FILTER_VALIDATION
		if (reportfilterValidationUID < 1)
		{
			throw new NEDSSSystemException(
		  	"Error in reading new entity uid, entity uid = " + reportfilterValidationUID);
		}
		try {
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(INSERT_REPORTFILTER_VALIDATION);
			int i = 1;
			preparedStmt.setLong(i++,reportfilterValidationUID);

			preparedStmt.setLong(i++,reportFilterDT.getReportFilterUID());
			//if Report_Filter_ind(validation for basic filters) is null, set it to "N" as default
			String valid = "N";
			if (reportFilterDT.getReportFilterInd() != null && reportFilterDT.getReportFilterInd().trim().length() > 0 )
				preparedStmt.setString(i++,reportFilterDT.getReportFilterInd());
			else
				 preparedStmt.setString(i++, valid);

			preparedStmt.setString(i++, "A");
			preparedStmt.setTimestamp(i++, status_time);
			resultCount = preparedStmt.executeUpdate();

			if(resultCount != 1)
			{
				throw new NEDSSSystemException("ReportFilterDAOImpl:insertReportFilterValidation Error: cannot be inserted into REPORT_FILTER_VALIDATION table, " + "resultCount = " + resultCount);
			}

		} catch (NEDSSSystemException e) {
			logger.fatal("NEDSSSystemException  = "+e.getMessage(), e);
			throw new NEDSSSystemException(e.toString());
		} catch (SQLException e) {
			logger.fatal("SQLException  = "+e.getMessage(), e);
			throw new NEDSSSystemException("SQLException while inserting reportfilter into REPORT_FILTER_VALIDATION: \n" + e.toString() +" \n" + e.getMessage());
		}
		finally {
           closeStatement(preparedStmt);
           releaseConnection(dbConnection);
		}
	}

    /**
     * Lists all records that belong to a given report.
     * @return a list of records.
     * @throws NEDSSDAOSysException if an error occurs.
     */
    public String listAllFilters(long data_source_uid, long report_uid) throws NEDSSDAOSysException
    {
        // Create temp variables.
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuffer rFilters = new StringBuffer();
        // Execute the command and package the data.
        try
        {
            con = this.getConnection();
            logger.warn(LIST[SERVER]);
            ps = con.prepareStatement(SELECT_ALL_REPORTFILTERS, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            
            ps.setLong(1, data_source_uid);
            ps.setLong(2, report_uid);
            
            rs = ps.executeQuery();
            while(rs.next())
            {
            	long filter_uid = rs.getLong("filter_uid");
                long report_filter_uid = rs.getLong("report_filter_uid");
                String filterName = rs.getString("filter_name");
                int min_value_cnt = rs.getInt("min_value_cnt");
                int max_value_cnt = rs.getInt("max_value_cnt");
                Long column_uid = rs.getLong("column_uid");

                String filterInd = rs.getString("report_filter_ind");
                if(filterInd != null && filterInd.equalsIgnoreCase("Y"))
                	filterInd = "Yes"; 
                else
                	filterInd = "No";
                
                rFilters.append(report_filter_uid).append("$");
                rFilters.append(filterName).append("$");
                
                //Derive Type from Min & Max Values
                if(filter_uid == 1 || filter_uid == 2 || filter_uid == 3 || filter_uid == 8 || filter_uid == 9 || filter_uid == 10 || filter_uid == 16 || filter_uid == 19) {
                	
                	if(min_value_cnt == 1 && max_value_cnt == 1)
                		rFilters.append("Single Select").append("$");
                	else if(min_value_cnt == 1 && max_value_cnt == -1)
                		rFilters.append("Multi Select").append("$");
                } else if(filter_uid == 6 || filter_uid == 13) {
                	//how to handle Time Period??
                	rFilters.append("Year Select").append("$");
                } else if(filter_uid == 5 || filter_uid == 12 || filter_uid == 18 || filter_uid == 23) {
                	rFilters.append("Plain Text").append("$");
                } 
                else {
                	rFilters.append("Advanced Criteria").append("$");
                }
                rFilters.append(column_uid).append("$");                
                rFilters.append(filterInd);
                
                rFilters.append("|");
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
        return rFilters.toString();
    }

    /**
     * Lists the names of all filters that belong to a given report.
     * @param data_source_uid the UID of the related data source.
     * @param report_uid the UID of the related report.
     * @return a map of Report_Filter.report_filter_uid to Filter_code.filter_name.
     * @throws NEDSSDAOSysException if an error occurs.
     */
    public TreeMap<Object,Object> listName(long data_source_uid, long report_uid) throws NEDSSDAOSysException
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
            logger.warn(LIST_NAME[SERVER]);
            logger.warn("data_source_uid=" + data_source_uid);
            logger.warn("report_uid=" + report_uid);
            ps = con.prepareStatement(LIST_NAME[SERVER], ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ps.setLong(1, data_source_uid);
            ps.setLong(2, report_uid);
            rs = ps.executeQuery();
            while(rs.next())
            {
                long report_filter_uid = rs.getLong("report_filter_uid");
                String filter_name = rs.getString("filter_name");
                Long L = new Long(report_filter_uid);
                tm.put(filter_name, L);
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
        return tm;
    }

    /**
     * Edits a record.
     * @param dt the record to edit.
     * @throws NEDSSDAOSysException if an error occurs.
     */
    public void edit(ReportFilterDT dt) throws NEDSSDAOSysException
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
            ps.setLong(1, dt.getReportFilterUID());
            ps.setLong(2, dt.getReportUID());
            ps.setLong(3, dt.getDataSourceUID());
            ps.setLong(4, dt.getFilterUID());
            ps.setString(5, "A");
            ps.setInt(6, dt.getMaxValueCount());
            ps.setInt(7, dt.getMinValueCount());
            if(dt.getColumnUID() == 0L)
            {
                ps.setNull(8, Types.BIGINT);
            }
            else
            {
                ps.setLong(8, dt.getColumnUID());
            }
            ps.setLong(9, dt.getReportFilterUID());
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

        try{
	        if((dt.getReportFilterValidationUid()!= null && dt.getReportFilterValidationUid().length() > 0)&& (dt.getReportFilterInd()!= null && dt.getReportFilterInd().equalsIgnoreCase("N") ))
	        	deleteReportFilterValidation(dt.getReportFilterUID());
	        else if(dt.getReportFilterValidationUid() == null)
	        	insertReportFilterValidation(dt);
	        else if(dt.getReportFilterValidationUid() != null && dt.getReportFilterValidationUid().length() > 0)
	        	updateReportFilterValidation(dt);
        }catch(Exception ex){
        	logger.fatal("Exception  = "+ex.getMessage(), ex);
            throw new NEDSSDAOSysException(ex.getMessage());
        }
    }
    private void updateReportFilterValidation(ReportFilterDT dt) {

        Connection con = null;
        PreparedStatement ps = null;

        //Deletes from Report_Filter_Validation table by using report_filter_uid
		try {
			con = getConnection();
			ps = con.prepareStatement(UPDATE_REPORTFILTER_VALIDATION);
			ps.setString(1, dt.getReportFilterInd());
			ps.setLong(2, dt.getReportFilterUID());			
			int resultCount = 0;
			resultCount = ps.executeUpdate();

			if (resultCount != 1) {
				throw new NEDSSSystemException("Error: cannot update  REPORTFILTER_VALIDATION TABLE!! resultCount = " + resultCount);
			}
		}catch (SQLException sex) {
			logger.fatal("SQLException  = "+sex.getMessage(), sex);
			throw new NEDSSSystemException("SQLException while updating "+ "reportfilter; id = " + dt.getReportFilterInd() + " :\n" + sex.getMessage());
		}
        finally {
           closeStatement(ps);
           releaseConnection(con);
        }
    }
    private void deleteReportFilterValidation(long uid) {

        Connection con = null;
        PreparedStatement ps = null;

        //Deletes from Report_Filter_Validation table by using report_filter_uid
		try {
			con = getConnection();
			ps = con.prepareStatement(DELETE_REPORTFILTER_VALIDATION);
			ps.setLong(1, uid);
			int resultCount = 0;
			resultCount = ps.executeUpdate();

			if (resultCount != 1) {
				throw new NEDSSSystemException("Error: cannot delete reportfilter from REPORTFILTER_VALIDATION TABLE!! resultCount = " + resultCount);
			}
		} catch (SQLException sex) {
			logger.fatal("SQLException  = "+sex.getMessage(), sex);
			throw new NEDSSSystemException("SQLException while removing "+ "reportfilter; id = " + uid + " :\n" + sex.getMessage());
		}
        finally {
           closeStatement(ps);
           releaseConnection(con);
        }
    }
    /**
     * Counts the number of related records in the Filter_value table.
     * @param uid points to the related record in Report_Filter.
     * @return a count.
     * @throws NEDSSDAOSysException if an error occurs.
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
            logger.warn("report_filter_uid=" + uid);
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
     * Deletes a record.
     * @param uid the UID of the record to delete.
     * @throws NEDSSDAOSysException if an error occurs.
     */
    public void delete(ReportFilterDT dt) throws NEDSSDAOSysException
    {
    	if(dt.getReportFilterValidationUid() != null && dt.getReportFilterValidationUid().length() > 0)
    		deleteReportFilterValidation(dt.getReportFilterUID());

        // Create temp variables.
        Connection con = null;
        PreparedStatement ps = null;

        // Execute the command and package the data.
        try
        {
            con = this.getConnection();
            logger.warn(DELETE[SERVER]);
            logger.warn("report_filter_uid=" + dt.getReportFilterUID());
            ps = con.prepareStatement(DELETE[SERVER], ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ps.setLong(1, dt.getReportFilterUID());
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
     * Deletes all records that belong to a given report.
     * @param data_source_uid the UID of the related data source.
     * @param report_uid the UID of the related report.
     * @throws NEDSSDAOSysException if an error occurs.
     */
    public void deleteAll(long data_source_uid, long report_uid) throws NEDSSDAOSysException
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
            ps = con.prepareStatement(DELETE_ALL[SERVER], ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
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

    /**
     * Lists all records from Filter_code that are not used in Report_Filter for a given report.
     * @param data_source_uid the UID of the related data source.
     * @param report_uid the UID of the related report.
     * @return a map of filter_uid to filter_name.
     * @throws NEDSSDAOSysException if an error occurs.
     */
    public TreeMap<Object,Object> getAvailableFilterMap(long data_source_uid, long report_uid) throws NEDSSDAOSysException
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
            logger.warn(LIST_AVAILABLE_FILTER_CODE[SERVER]);
            ps = con.prepareStatement(LIST_AVAILABLE_FILTER_CODE[SERVER], ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ps.setLong(1, data_source_uid);
            ps.setLong(2, report_uid);
            rs = ps.executeQuery();
            while(rs.next())
            {
                long filter_uid = rs.getLong("filter_uid");
                String filter_name = rs.getString("filter_name");
                if(rs.wasNull())
                {
                    continue;
                }
                Long L = new Long(filter_uid);
                tm.put(filter_name, L);
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
        return tm;
    }

}
