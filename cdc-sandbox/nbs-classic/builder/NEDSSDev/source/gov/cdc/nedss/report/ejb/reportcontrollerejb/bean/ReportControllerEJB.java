package gov.cdc.nedss.report.ejb.reportcontrollerejb.bean;

import gov.cdc.nedss.report.ejb.datasourceejb.bean.*;
import gov.cdc.nedss.report.ejb.datasourceejb.dao.*;
import gov.cdc.nedss.report.ejb.reportejb.bean.*;
import gov.cdc.nedss.report.ejb.reportejb.dao.*;
import gov.cdc.nedss.report.ejb.sas.bean.*;
import gov.cdc.nedss.report.javaRepot.util.CR1Util;
import gov.cdc.nedss.report.javaRepot.util.CR2Util;
import gov.cdc.nedss.report.javaRepot.util.CR3Util;
import gov.cdc.nedss.report.javaRepot.util.CR4Util;
import gov.cdc.nedss.report.javaRepot.util.CR5Util;
import gov.cdc.nedss.report.javaRepot.util.Pa1Util;
import gov.cdc.nedss.report.javaRepot.util.Pa3Util;
import gov.cdc.nedss.report.javaRepot.vo.CR1VO;
import gov.cdc.nedss.report.javaRepot.vo.CR3VO;
import gov.cdc.nedss.report.util.*;
import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.systemservice.scheduler.util.SchedulerUtil;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.report.vo.*;
import gov.cdc.nedss.report.dt.*;

import java.rmi.RemoteException;
import java.sql.*;
import java.util.*;

import javax.ejb.*;
import javax.rmi.*;
/* At later date the extends from BMPBase should be removed and code making connection
to database should be transferred to DAO */
public class ReportControllerEJB extends BMPBase implements javax.ejb.SessionBean
{

    static final LogUtils logger = new LogUtils((ReportControllerEJB.class).getName());

    /**
     * @roseuid 3C17FAB7037B
     * @J2EE_METHOD  --  ReportControllerEJB
     */
    public ReportControllerEJB()
    {
    }

    /**
     * @roseuid 3C22928B0234
     * @J2EE_METHOD  --  getDataSource
     */
    /**
      * returns a DataSourceVO object given a dataSourceUID.
      */
    public DataSourceVO getDataSource(java.lang.Long dataSourceUID, NBSSecurityObj securityObj) throws javax.ejb.EJBException
    {
        logger.debug("ReportControllerEJB:getDataSource#######################Started getDataSource###########################");
        DataSourceVO dataSourceVO = new DataSourceVO();
        try
        {
            DataSource dataSource = null;
            NedssUtils nedssUtils = new NedssUtils();
            logger.debug("ReportControllerEJB:getDataSource - ReportJndiNames.DATA_SOURCE_EJB = " + JNDINames.DATA_SOURCE_EJB);
            Object obj = nedssUtils.lookupBean(JNDINames.DATA_SOURCE_EJB);
            logger.debug("ReportControllerEJB:getDataSource - obj = " + obj.toString());
            DataSourceHome home = (DataSourceHome)PortableRemoteObject.narrow(obj, DataSourceHome.class);
            dataSource = home.findByPrimaryKey(dataSourceUID);
            dataSourceVO = dataSource.getDataSourceVO();
            logger.debug("ReportControllerEJB:getDataSource  dataSourceVO.getJurisdictionSecurity(): " + dataSourceVO
                        .getTheDataSourceDT().getJurisdictionSecurity());
        }
        catch(Exception e)
        {
            logger.debug("ReportControllerEJB:getDataSource exception e = " + e + "\n");
            logger.fatal(e.getMessage(), e);
            throw new EJBException(e.getMessage(), e);
        }
        logger.debug("ReportControllerEJB:getDataSource***********************Completed getDataSource*************************");
        return dataSourceVO;
    }
    
    
    /**
     * isDataMartFromDataSourceName: returns if there's any data mart in RDB table with that name
     */
    public boolean isDataMartFromDataSourceName(java.lang.Long reportUid, java.lang.Long dataSourceUID, NBSSecurityObj securityObj) throws javax.ejb.EJBException
    {
 	   final PropertyUtil propertyUtil= PropertyUtil.getInstance();
 	   boolean isDatamart = true;
 	   Connection dbConnection = null;
        logger.debug("ReportControllerEJB:isDataMartFromDataSourceName#######################Started isDataMartFromDataSourceName###########################");
        DataSourceVO dataSourceVO = new DataSourceVO();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;  
        
        try
        {
     	   
     	   dataSourceVO = getDataSource(dataSourceUID, securityObj);  
     	   
     	// Split data source name into component parts.
 	        String data_source_name = dataSourceVO.getTheDataSourceDT().getDataSourceName();
 	        String[] arrDSN = data_source_name.split("\\x2E");
 	        StringBuffer sbDSN = new StringBuffer();
 	        String strDatabase = arrDSN[0];
 	        String strTable = arrDSN[1];
 	        String database = arrDSN[0];
 			String table = arrDSN[1];

 			// Execute the command and package the data.
 	
 				if (database.equalsIgnoreCase("nbs_rdb")) {

 					dbConnection = getConnection(NEDSSConstants.RDB);

 					sbDSN.append("rdb.");
 				} else if (database.equalsIgnoreCase("nbs_ods")) {
 					sbDSN.append("nbs_odse.");
 					dbConnection = getConnection(NEDSSConstants.ODS);
 				} else if (database.equalsIgnoreCase("nbs_msg")) {
 					sbDSN.append("nbs_msgoute.");
 					dbConnection = getConnection(NEDSSConstants.MSGOUT);
 				} else if (database.equalsIgnoreCase("nbs_srt")) {
 					sbDSN.append("nbs_srte.");
 					dbConnection = getConnection(NEDSSConstants.SRT);
 				}
				sbDSN.append(".");

 				sbDSN.append(table);
 	        String strDSN = sbDSN.toString();
 	        StringBuffer sbSQL = new StringBuffer();
 	        sbSQL.append("select * from ").append(strDSN).append(" where 1 = 0");
 	        String SQL = sbSQL.toString();
            ps = dbConnection.prepareStatement(SQL, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            rs = ps.executeQuery();
            rs.next();
            rsmd = rs.getMetaData();
            int x = 1;
            int y = rsmd.getColumnCount();
         }
        catch(Exception e)
        {
            try
            {
                if(dbConnection != null)
                {
                    dbConnection.close();
                }
            }
            catch(SQLException sex3)
            {
            	logger.fatal("SQLException in ReportControllerEJB:isDataMartFromDataSourceName:" + sex3.getMessage(), sex3);
                throw new NEDSSSystemException( sex3.getMessage(),sex3);
            }

     	   if(e!=null && e.getMessage()!=null && (e.getMessage().toLowerCase().contains("invalid object name")
     			   || e.getMessage().toLowerCase().contains("table or view does not exist"))){
     		   return false;
     	   }
     	   else{
            logger.debug("ReportControllerEJB:isDataMartFromDataSourceName exception e = " + e + "\n");
            logger.fatal(e.getMessage(), e);
            throw new EJBException(e.getMessage(), e);
     	   }
        }
        try
        {
            if(dbConnection != null)
            {
                dbConnection.close();
            }
        }
        catch(SQLException sex3)
        {
        	logger.fatal("SQLException in ReportControllerEJB:isDataMartFromDataSourceName:" + sex3.getMessage(), sex3);
            throw new NEDSSSystemException( sex3.getMessage(),sex3);
        }
        logger.debug("ReportControllerEJB:getDataSource***********************Completed isDataMartFromDataSourceName*************************");
        return isDatamart;
    }
   

    /**
     * @roseuid 3C22928903AE
     * @J2EE_METHOD  --  getDataSourceList
     */
    /**
     * The getDataSourceList method returns a Collection<Object>  of DataSourceDT objects.
     * There is an element in the Collection<Object>  for each DataSource that matches the
     * users access permissions as read from the security object.
     *
     */
    public Collection<Object>  getDataSourceList(NBSSecurityObj securityObj) throws NEDSSSystemException
    {
        logger.debug("ReportControllerEJB:getDataSourceList #################Started getDataSourceList################");
        Connection dbConnection = null;
        DataSourceDT dataSourceDT = new DataSourceDT();
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        ResultSetUtils resultSetUtils = new ResultSetUtils();
        ArrayList<Object> pList = new ArrayList<Object> ();
        ArrayList<Object> dataSourceListColl = new ArrayList<Object> ();
        /**
         * Selects DataSource from DataSource table
         */
        try
        {
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(ReportConstants.SELECT_DATASOURCE_LIST);
            resultSet = preparedStmt.executeQuery();
            resultSetMetaData = resultSet.getMetaData();
            pList = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, dataSourceDT.getClass(), pList);
            for(Iterator<Object> anIterator = pList.iterator(); anIterator.hasNext();)
            {
                logger.debug("ReportControllerEJB:getDataSourceList anIterator.hasNext()" + anIterator.hasNext());
                dataSourceDT = (DataSourceDT)anIterator.next();
                logger.debug("ReportControllerEJB:getDataSourceList dataSourceDT.getStatusCd():" + dataSourceDT.getStatusCd());
                dataSourceDT.setItNew(false);
                dataSourceDT.setItDirty(false);
                dataSourceDT.setItDelete(false);
                dataSourceListColl.add(dataSourceDT);
            }
        }
        catch(SQLException sqlex)
        {
            logger.fatal("SQLException while selecting from " + "DataSource Table:\n" + sqlex.getMessage(), sqlex);
            throw new NEDSSSystemException( sqlex.getMessage(),sqlex);
        }
        catch(Exception ex)
        {
            logger.fatal("Exception while selecting From DatSuurce table:\n" + ex.getMessage(), ex);
            throw new NEDSSSystemException(ex.getMessage(),ex);
        }
        finally
        {
            try
            {
                if(resultSet != null)
                {
                    resultSet.close();
                }
            }
            catch(SQLException sex1)
            {
                logger.fatal("SQLException :" + sex1.getMessage(), sex1);
                throw new NEDSSSystemException( sex1.getMessage(),sex1);
            }
            try
            {
                if(preparedStmt != null)
                {
                    preparedStmt.close();
                }
            }
            catch(SQLException sex2)
            {
            	logger.fatal("SQLException :" + sex2.getMessage(), sex2);
                throw new NEDSSSystemException( sex2.getMessage(),sex2);
            }
            try
            {
                if(dbConnection != null)
                {
                    dbConnection.close();
                }
            }
            catch(SQLException sex3)
            {
            	logger.fatal("SQLException :" + sex3.getMessage(), sex3);
                throw new NEDSSSystemException( sex3.getMessage(),sex3);
            }
        }
        logger.debug("ReportControllerEJB:getDataSourceList ****************Completed getDataSourceList***************");
        return dataSourceListColl;
    }

    /**
     * @roseuid 3C22928B00DF
     * @J2EE_METHOD  --  getDisplayableColumns
     */
    /**
     * The getDisplayableColumns method returns a Collection<Object>  of DataSourceColumnDT
     * objects.  The Collection<Object>  contains an element for each DataSourceColumn
     * associated with the DataSource whose displayable indicator is set to true.
     */
    public Collection<Object>  getDisplayableColumns(java.lang.Long reportUID, java.lang.Long dataSourceUID, NBSSecurityObj securityObj) throws NEDSSSystemException
    {
        logger.debug("ReportControllerEJB:getDisplayableColumns #################Started getDisplayableColumns################");
        String displayableFilter = "Y";
        Connection dbConnection = null;
        DataSourceColumnDT dataSourceColumnDT = new DataSourceColumnDT();
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        ResultSetUtils resultSetUtils = new ResultSetUtils();
        ArrayList<Object> pList = new ArrayList<Object> ();
        ArrayList<Object> dataSourceListColumnColl = new ArrayList<Object> ();
        /**
         * Selects DataSource from DataSource table
         */
        try
        {
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(ReportConstants.SELECT_DISPLAYABLE_COLUMN_LIST);
            /**
             * Needs changes here as the method may be actually be doing a select based on the securityObject Helpetr classes implementations
             */
            preparedStmt.setLong(1, dataSourceUID.longValue());
            preparedStmt.setString(2, displayableFilter);
            resultSet = preparedStmt.executeQuery();
            resultSetMetaData = resultSet.getMetaData();
            pList = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, dataSourceColumnDT.getClass(), pList);
            for(Iterator<Object> anIterator = pList.iterator(); anIterator.hasNext();)
            {
                logger.debug("ReportControllerEJB:getDisplayableColumns anIterator.hasNext()" + anIterator.hasNext());
                dataSourceColumnDT = (DataSourceColumnDT)anIterator.next();
                dataSourceColumnDT.setItNew(false);
                dataSourceColumnDT.setItDirty(false);
                dataSourceColumnDT.setItDelete(false);
                dataSourceListColumnColl.add(dataSourceColumnDT);
            }
        }
        catch(SQLException sqlex)
        {
            logger.fatal("SQLException while selecting from " + "DataSource Table:\n" + sqlex.getMessage(), sqlex);
            throw new NEDSSSystemException(sqlex.getMessage(),sqlex);
        }
        catch(Exception ex)
        {
            logger.fatal("Exception while selecting From DatSuurce table:\n" + ex.getMessage(), ex);
            throw new NEDSSSystemException(ex.getMessage(),ex);
        }
        finally
        {
            try
            {
                if(resultSet != null)
                {
                    resultSet.close();
                }
            }
            catch(SQLException sex1)
            {
            	logger.fatal("SQLException :" + sex1.getMessage(), sex1);
                throw new NEDSSSystemException( sex1.getMessage(),sex1);
            }
            try
            {
                if(preparedStmt != null)
                {
                    preparedStmt.close();
                }
            }
            catch(SQLException sex2)
            {
            	logger.fatal("SQLException :" + sex2.getMessage(), sex2);
                throw new NEDSSSystemException( sex2.getMessage(),sex2);
            }
            try
            {
                if(dbConnection != null)
                {
                    dbConnection.close();
                }
            }
            catch(SQLException sex3)
            {
            	logger.fatal("SQLException :" + sex3.getMessage(), sex3);
                throw new NEDSSSystemException( sex3.getMessage(),sex3);
            }
        }
        logger.debug("ReportControllerEJB:getDisplayableColumns***********************Completed getDisplayableColumns*************************");
        return dataSourceListColumnColl;
    }

    /**
     * @roseuid 3C22928B0338
     * @J2EE_METHOD  --  getFilterableColumns
     */
    /**
     * The getFilterableColumns method returns a Collection<Object>  of DataSourceColumnDT
     * objects.  The Collection<Object>  contains an element for each DataSourceColumn
     * associated with the DataSource whose filterable indicator is set to true.
     */
    public Collection<Object>  getFilterableColumns(java.lang.Long dataSourceUID, NBSSecurityObj securityObj) throws NEDSSSystemException
    {
        logger.debug("ReportControllerEJB:getFilterableColumns #################Started getFilterableColumns################");
        String filterableFilter = "Y";
        Connection dbConnection = null;
        DataSourceColumnDT dataSourceColumnDT = new DataSourceColumnDT();
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        ResultSetUtils resultSetUtils = new ResultSetUtils();
        ArrayList<Object> pList = new ArrayList<Object> ();
        ArrayList<Object> dataSourceListColumnColl = new ArrayList<Object> ();
        /**
         * Selects DataSourceColumn from DataSourceColumn table
         */
        try
        {
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(ReportConstants.SELECT_FILTERCOLUMN_LIST);
            /**
             * Needs changes here as the method may be actually be doing a select based on the securityObject Helpetr classes implementations
             */
            preparedStmt.setLong(1, dataSourceUID.longValue());
            preparedStmt.setString(2, filterableFilter);
            resultSet = preparedStmt.executeQuery();
            resultSetMetaData = resultSet.getMetaData();
            pList = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, dataSourceColumnDT.getClass(), pList);
            for(Iterator<Object> anIterator = pList.iterator(); anIterator.hasNext();)
            {
                dataSourceColumnDT = (DataSourceColumnDT)anIterator.next();
                dataSourceColumnDT.setItNew(false);
                dataSourceColumnDT.setItDirty(false);
                dataSourceColumnDT.setItDelete(false);
                dataSourceListColumnColl.add(dataSourceColumnDT);
            }
        }
        catch(SQLException sqlex)
        {
            logger.fatal("SQLException in ReportControllerEJB:getFilterableColumns:\n" + sqlex.getMessage(), sqlex);
            throw new NEDSSSystemException(sqlex.getMessage(),sqlex);
        }
        catch(Exception ex)
        {
            logger.fatal("Exception while in ReportControllerEJB:getFilterableColumns:\n" + ex.getMessage(), ex);
            throw new NEDSSSystemException( ex.getMessage(),ex);
        }
        finally
        {
            try
            {
                if(resultSet != null)
                {
                    resultSet.close();
                }
            }
            catch(SQLException sex1)
            {
            	logger.fatal("SQLException :" + sex1.getMessage(), sex1);
                throw new NEDSSSystemException( sex1.getMessage(),sex1);
            }
            try
            {
                if(preparedStmt != null)
                {
                    preparedStmt.close();
                }
            }
            catch(SQLException sex2)
            {
            	logger.fatal("SQLException :" + sex2.getMessage(), sex2);
                throw new NEDSSSystemException( sex2.getMessage(),sex2);
            }
            try
            {
                if(dbConnection != null)
                {
                    dbConnection.close();
                }
            }
            catch(SQLException sex3)
            {
            	logger.fatal("SQLException :" + sex3.getMessage(), sex3);
                throw new NEDSSSystemException( sex3.getMessage(),sex3);
            }
        }
        logger.debug("ReportControllerEJB:getFilterableColumns***********************Completed getFilterableColumns*************************");
        return dataSourceListColumnColl;
    }

    /**
     * @roseuid 3C229289021D
     * @J2EE_METHOD  --  getMyReportList
     */
    /**
     * The getPrivateReportList returns a Collection<Object>  of ReportDT objects.  The
     * Collection<Object>  contains an element for each report that the user Owns and can
     * access.  A user owns a report if the ownerUID matches the userUID from the
     * securityObj.  This collection can contain a combination of Private and Public
     * Reports.   A Private report is a report whose shared indicator is set to false.
     *  Users can only access private reports that they own.  Even if a user owns a
     * report, they still may not be able to access it if their current access
     * permissions do not permit it.
     */
    public Collection<Object>  getMyReportList(NBSSecurityObj securityObj) throws NEDSSSystemException
    {
        logger.debug("ReportControllerEJB:getMyReportList #################Started getMyReportList################");
        Long ownerUid = new Long(securityObj.getEntryID());
        Connection dbConnection = null;
        ReportDT reportDT = new ReportDT();
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        ResultSetUtils resultSetUtils = new ResultSetUtils();
        ArrayList<Object> pList = new ArrayList<Object> ();
        ArrayList<Object> myReportListColl = new ArrayList<Object> ();
        /**
     * Selects myReportList from Report table
     */
        try
        {
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(ReportConstants.SELECT_MYREPORT_LIST);
            /**
         * Needs changes here as the method may be actually be doing a select based on the securityObject Helpetr classes implementations
         */
            preparedStmt.setLong(1, ownerUid.longValue());
            resultSet = preparedStmt.executeQuery();
            resultSetMetaData = resultSet.getMetaData();
            pList = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, reportDT.getClass(), pList);
            for(Iterator<Object> anIterator = pList.iterator(); anIterator.hasNext();)
            {
                logger.debug("ReportControllerEJB:getMyReportList anIterator.hasNext()" + anIterator.hasNext());
                reportDT = (ReportDT)anIterator.next();
                reportDT.setItNew(false);
                reportDT.setItDirty(false);
                reportDT.setItDelete(false);
                myReportListColl.add(reportDT);
            }
        }
        catch(SQLException sqlex)
        {
            logger.fatal("SQLException in ReportControllerEJB:getMyReportList:\n" + sqlex.getMessage(), sqlex);
            throw new NEDSSSystemException(sqlex.getMessage(),sqlex);
        }
        catch(Exception ex)
        {
            logger.fatal("Exception while in ReportControllerEJB:getMyReportList:\n" + ex.getMessage(), ex);
            throw new NEDSSSystemException(ex.getMessage(),ex);
        }
        finally
        {
            try
            {
                if(resultSet != null)
                {
                    resultSet.close();
                }
            }
            catch(SQLException sex1)
            {
            	logger.fatal("SQLException :" + sex1.getMessage(), sex1);
                throw new NEDSSSystemException( sex1.getMessage(),sex1);
            }
            try
            {
                if(preparedStmt != null)
                {
                    preparedStmt.close();
                }
            }
            catch(SQLException sex2)
            {
            	logger.fatal("SQLException :" + sex2.getMessage(), sex2);
                throw new NEDSSSystemException( sex2.getMessage(),sex2);
            }
            try
            {
                if(dbConnection != null)
                {
                    dbConnection.close();
                }
            }
            catch(SQLException sex3)
            {
            	logger.fatal("SQLException :" + sex3.getMessage(), sex3);
                throw new NEDSSSystemException( sex3.getMessage(),sex3);
            }
        }
        logger.debug("ReportControllerEJB:getMyReportList ****************Completed getMyReportList***************");
        return myReportListColl;
    }

    /**
     * @roseuid 3C2292890123
     * @J2EE_METHOD  --  setReport
     */
    /**
     * The setReport method is used to update an existing report.  A user can
     * only update a report that they own (userUID = ownerUID).In this case the
     * ReportVO's setItNew is marked as false.
     * The setReport can also be used to create a new report if the ReportVO's
     * setItNew field is marked as new.
     */
    public ReportVO setReport(ReportVO reportVO, NBSSecurityObj securityObj) throws java.rmi.RemoteException, javax.ejb.EJBException, javax.ejb.CreateException
    {
        logger.debug("ReportControllerEJB:setReport started");
        Long reportUID = new Long(-1);
        //Long dataSourceUid = new Long(-1);
        ReportVO rVO = new ReportVO();
        Report report = null;
        try
        {
            NedssUtils nedssUtils = new NedssUtils();
            Object obj = nedssUtils.lookupBean(JNDINames.REPORT_EJB);
            logger.debug("ReportControllerEJB:setReport - lookup = " + obj.toString());
            ReportHome home = (ReportHome)PortableRemoteObject.narrow(obj, ReportHome.class);
            logger.debug("ReportControllerEJB:setReport - Found ReportHome: " + home);
            if(reportVO.isItNew())
            {
                logger.debug("\n\nReportControllerEJB:setReport IsItNew is called");
                report = home.create(reportVO);
                rVO = report.getReportVO();
                logger.debug("\n\nReportControllerEJB:setReport IsItNew is called with reportUID :" + reportUID);
            }
            else
            {
                logger.debug("ReportControllerEJB:setReport else is called");
                report = home.findByPrimaryKey(reportVO.getTheReportDT().getReportUid());
                report.setReportVO(reportVO);
                rVO = report.getReportVO();
                logger.debug(" ReportControllerEJB:setReport -  report Updated");
           
            }
            reportUID = rVO.getTheReportDT().getReportUid();
            logger.debug("ReportControllerEJB:setReport ****************set completed with UID *****************" + reportUID);
        }
        catch(Exception e)
        {
            logger.debug("ReportControllerEJB:setReport -  exception e = " + e + "\n");
            logger.fatal(e.getMessage(), e);
            throw new EJBException(e.getMessage(), e);
        }
        logger.debug("ReportControllerEJB:setReport ****************set completed ");
        return rVO;
    }

    /**
    * @roseuid 3C22928A01CE
    * @J2EE_METHOD  --  getReport
    */
    /**
     * For a reportUID gets the reportVO object.
     */
    public ReportVO getReport(java.lang.Long reportUID, java.lang.Long dataSourceUID, NBSSecurityObj securityObj) throws javax.ejb.EJBException
    {
        logger.debug("ReportControllerEJB:getReport #################started getReport################");
        ReportVO reportVO = new ReportVO();
        try
        {
            Report report = null;
            NedssUtils nedssUtils = new NedssUtils();
            Object obj = nedssUtils.lookupBean(JNDINames.REPORT_EJB);
            logger.debug("ReportControllerEJB:getReport - obj = " + obj.toString());
            ReportHome home = (ReportHome)PortableRemoteObject.narrow(obj, ReportHome.class);
            report = home.findByPrimaryKey(reportUID);
            reportVO = report.getReportVO();
        }
        catch(Exception e)
        {
            logger.debug("ReportControllerEJB:getReport exception e = " + e + "\n");
            logger.fatal(e.getMessage(), e);
            throw new EJBException(e.getMessage(), e);
        }
        logger.debug("ReportControllerEJB:getReport *****************Completed getReport****************");
        return reportVO;
    }

    /**
     * @roseuid 3C22928901B9
     * @J2EE_METHOD  --  getReportList
     */
    /**
     * Returns a collection for a user.  It contains three arraylists that
     * hold reports with my owner_uid, shared reports and
     * templates in that order
     */
    public Collection<Object>  getReportList(NBSSecurityObj securityObj) throws NEDSSSystemException
    {
        logger.debug("ReportControllerEJB:getReportList #################Started getReportList################");
        Connection dbConnection = null;
        Long ownerUid = new Long(securityObj.getEntryID().trim());
        ReportDT reportDT = new ReportDT();
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        ResultSetUtils resultSetUtils = new ResultSetUtils();
        ArrayList<Object> pList = new ArrayList<Object> ();
        //ArrayList<Object> myReportListColl = new ArrayList<Object> ();
        ArrayList<Object> allMyReportListColl = new ArrayList<Object> ();
        ArrayList<Object> sharedReportList = new ArrayList<Object> ();
        ArrayList<Object> myReportList = new ArrayList<Object> ();
        ArrayList<Object> templateReportList = new ArrayList<Object> ();
        ArrayList<Object> reportingFacilityReportList = new ArrayList<Object> ();

        /**
        * Selects myReportList from Report table
        */
        try
        {
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(ReportConstants.SELECT_GET_REPORT_LIST);
            preparedStmt.setLong(1, ownerUid.longValue());
            resultSet = preparedStmt.executeQuery();
            resultSetMetaData = resultSet.getMetaData();
            pList = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, reportDT.getClass(), pList);
            for(Iterator<Object> anIterator = pList.iterator(); anIterator.hasNext();)
            {
                logger.debug("ReportControllerEJB:getReportList anIterator.hasNext()" + anIterator.hasNext());
                reportDT = (ReportDT)anIterator.next();
                logger.debug("ReportControllerEJB:getReportList reached reportDT.getOrgAccessPermis(): " + reportDT.getOrgAccessPermis());
                reportDT.setItNew(false);
                reportDT.setItDirty(false);
                reportDT.setItDelete(false);
                //myReportListColl.add(reportDT);
                if (reportDT.getShared().equalsIgnoreCase("T")) {
                  templateReportList.add(reportDT);
                }else if (reportDT.getShared().equalsIgnoreCase("S")) {
                  sharedReportList.add(reportDT);
                } else if(reportDT.getShared().equalsIgnoreCase("R")) {
                  reportingFacilityReportList.add(reportDT);	
                }
                if (reportDT.getOwnerUid().equals(Long.valueOf(securityObj.getEntryID()))) {
                myReportList.add(reportDT);
                }
            }
            allMyReportListColl.add(0,myReportList);
            allMyReportListColl.add(1,sharedReportList);
            allMyReportListColl.add(2,templateReportList);
            allMyReportListColl.add(3,reportingFacilityReportList);            
        }
        catch(SQLException sqlex)
        {
            logger.fatal("SQLException in ReportControllerEJB:getReportList:\n" + sqlex.getMessage(), sqlex);
            throw new NEDSSSystemException( sqlex.getMessage(),sqlex);
        }
        catch(Exception ex)
        {
            logger.fatal("Exception while in ReportControllerEJB:getReportList:\n" + ex.getMessage(), ex);
            throw new NEDSSSystemException(ex.getMessage(),ex);
        }
        finally
        {
            try
            {
                if(resultSet != null)
                {
                    resultSet.close();
                }
            }
            catch(SQLException sex1)
            {
            	logger.fatal("SQLException :" + sex1.getMessage(), sex1);
                throw new NEDSSSystemException( sex1.getMessage(),sex1);
            }
            try
            {
                if(preparedStmt != null)
                {
                    preparedStmt.close();
                }
            }
            catch(SQLException sex2)
            {
            	logger.fatal("SQLException :" + sex2.getMessage(), sex2);
                throw new NEDSSSystemException( sex2.getMessage(),sex2);
            }
            try
            {
                if(dbConnection != null)
                {
                    dbConnection.close();
                }
            }
            catch(SQLException sex3)
            {
            	logger.fatal("SQLException :" + sex3.getMessage(), sex3);
                throw new NEDSSSystemException( sex3.getMessage(),sex3);
            }
        }
        logger.debug("ReportControllerEJB:getReportList ****************Completed getReportList***************");
        return allMyReportListColl;
    }

    /**
    * @roseuid 3C22928B003F
    * @J2EE_METHOD  --  getReportTemplateList
    */
    /**
    * The getReportTemplateList method returns a Collection<Object>  of ReportDT objects.
    * Each ReportDT object represents a report whose category is set to Template and
    * whose access permissions match the users access permissions.
    *Presently test and see if the shared indicator is set to "T" in the report table.
    *
    */
    public Collection<Object>  getReportTemplateList(NBSSecurityObj securityObj) throws NEDSSSystemException
    {
        logger.debug("ReportControllerEJB:getReportTemplateList #################Started getReportTemplateList################");
        String shared = "T";
        Connection dbConnection = null;
        ReportDT reportDT = new ReportDT();
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        ResultSetUtils resultSetUtils = new ResultSetUtils();
        ArrayList<Object> pList = new ArrayList<Object> ();
        ArrayList<Object> reportTemplateColl = new ArrayList<Object> ();
        /**
         * Selects reportTemplateCollection  from Report table
         */
        try
        {
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(ReportConstants.SELECT_TEMPLATE_REPORT_LIST);
            preparedStmt.setString(1, shared);
            resultSet = preparedStmt.executeQuery();
            resultSetMetaData = resultSet.getMetaData();
            logger.debug("ReportControllerEJB:getReportTemplateList resultSetMetaData " + resultSetMetaData);
            pList = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, reportDT.getClass(), pList);
            for(Iterator<Object> anIterator = pList.iterator(); anIterator.hasNext();)
            {
                logger.debug("ReportControllerEJB:getReportTemplateList anIterator.hasNext()" + anIterator.hasNext());
                reportDT = (ReportDT)anIterator.next();
                reportDT.setItNew(false);
                reportDT.setItDirty(false);
                reportDT.setItDelete(false);
                reportTemplateColl.add(reportDT);
            }
        }
        catch(SQLException sqlex)
        {
            logger.fatal("SQLException in ReportControllerEJB:getReportTemplateList:\n" + sqlex.getMessage(), sqlex);
            throw new NEDSSSystemException(sqlex.getMessage(),sqlex);
        }
        catch(Exception ex)
        {
            logger.fatal("Exception while in ReportControllerEJB:getReportTemplateList:\n" + ex.getMessage(), ex);
            throw new NEDSSSystemException(ex.getMessage(),ex);
        }
        finally
        {
            try
            {
                if(resultSet != null)
                {
                    resultSet.close();
                }
            }
            catch(SQLException sex1)
            {
            	logger.fatal("SQLException :" + sex1.getMessage(), sex1);
                throw new NEDSSSystemException( sex1.getMessage(),sex1);
            }
            try
            {
                if(preparedStmt != null)
                {
                    preparedStmt.close();
                }
            }
            catch(SQLException sex2)
            {
            	logger.fatal("SQLException :" + sex2.getMessage(), sex2);
                throw new NEDSSSystemException( sex2.getMessage(),sex2);
            }
            try
            {
                if(dbConnection != null)
                {
                    dbConnection.close();
                }
            }
            catch(SQLException sex3)
            {
            	logger.fatal("SQLException :" + sex3.getMessage(), sex3);
                throw new NEDSSSystemException( sex3.getMessage(),sex3);
            }
        }
        logger.debug("ReportControllerEJB:getReportTemplateList *****************Completed getReportTemplateList***************");
        return reportTemplateColl;
    }

    /**
     * @roseuid 3C17FAB703AD
     * @J2EE_METHOD  --  setSessionContext
     * Set the associated session context. The container calls this method after the instance
     * creation. The enterprise Bean instance should store the reference to the context
     * object in an instance variable. This method is called with no transaction context.
     */
    public void setSessionContext(SessionContext sessioncontext) throws EJBException, RemoteException
    {
    }

    /**
     * @roseuid 3C229289028B
     * @J2EE_METHOD  --  getSharedReportList
     */
    /**
     * Returns a collection of ReportDT objects.  The collection contains an element
     * for each shared report that matches the user's access permissions as read from
     * the SecurityObj.  A report is shared if it's shared indicator is true.  There
     * is some overlap between the reports returned by the getMyReportList method and
     * this method.  The overlap are the reports that the user owns and has shared.
     */
    public Collection<Object>  getSharedReportList(NBSSecurityObj securityObj) throws NEDSSSystemException
    {
        logger.debug("ReportControllerEJB:getSharedReportList #################Started getSharedReportList################");
        String shared = "S";
        Connection dbConnection = null;
        ReportDT reportDT = new ReportDT();
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        ResultSetUtils resultSetUtils = new ResultSetUtils();
        ArrayList<Object> pList = new ArrayList<Object> ();
        ArrayList<Object> sharedReportListColl = new ArrayList<Object> ();
        /**
         * Selects getSharedReportList from Report table
         */
        try
        {
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(ReportConstants.SELECT_SHARED_REPORT_LIST);
            /**
             * Needs changes here as the method may be actually be doing a select based on the securityObject Helpetr classes implementations
             */
            preparedStmt.setString(1, shared);
            resultSet = preparedStmt.executeQuery();
            resultSetMetaData = resultSet.getMetaData();
            logger.debug("ReportControllerEJB:getSharedReportList resultSetMetaData " + resultSetMetaData);
            pList = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, reportDT.getClass(), pList);
            for(Iterator<Object> anIterator = pList.iterator(); anIterator.hasNext();)
            {
                logger.debug("ReportControllerEJB:getSharedReportList anIterator.hasNext()" + anIterator.hasNext());
                reportDT = (ReportDT)anIterator.next();
                reportDT.setItNew(false);
                reportDT.setItDirty(false);
                reportDT.setItDelete(false);
                sharedReportListColl.add(reportDT);
            }
        }
        catch(SQLException sqlex)
        {
            logger.fatal("SQLException in ReportControllerEJB:getSharedReportList:\n" + sqlex.getMessage(), sqlex);
            throw new NEDSSSystemException(sqlex.getMessage(),sqlex);
        }
        catch(Exception ex)
        {
            logger.fatal("Exception while in ReportControllerEJB:getSharedReportList:\n" + ex.getMessage(), ex);
            throw new NEDSSSystemException(ex.getMessage(),ex);
        }
        finally
        {
            try
            {
                if(resultSet != null)
                {
                    resultSet.close();
                }
            }
            catch(SQLException sex1)
            {
            	logger.fatal("SQLException :" + sex1.getMessage(), sex1);
                throw new NEDSSSystemException( sex1.getMessage(),sex1);
            }
            try
            {
                if(preparedStmt != null)
                {
                    preparedStmt.close();
                }
            }
            catch(SQLException sex2)
            {
            	logger.fatal("SQLException :" + sex2.getMessage(), sex2);
                throw new NEDSSSystemException( sex2.getMessage(),sex2);
            }
            try
            {
                if(dbConnection != null)
                {
                    dbConnection.close();
                }
            }
            catch(SQLException sex3)
            {
            	logger.fatal("SQLException :" + sex3.getMessage(), sex3);
                throw new NEDSSSystemException( sex3.getMessage(),sex3);
            }
        }
        logger.debug("ReportControllerEJB:getSharedReportList ****************Completed getSharedReportList***************");
        return sharedReportListColl;
    }

    /**
     * @roseuid 3C0D3AC4004C
     * @J2EE_METHOD  --  AVRReportControllerEJB
     */
    public void ReportControllerEJB()
    {
    }

    /**
     * @roseuid 3C22928A02F1
     * @J2EE_METHOD  --  deleteReport
     */
    /**
      * Deletes the report given a reportUID.
      */
    public void deleteReport(java.lang.Long reportUID, java.lang.Long dataSourceUID, NBSSecurityObj securityObj)
    {
        logger.debug("ReportControllerEJB:deleteReport #################Started deleteReport################");
        ReportVO reportVO = null;
        try
        {
            Report report = null;
            NedssUtils nedssUtils = new NedssUtils();
            Object obj = nedssUtils.lookupBean(JNDINames.REPORT_EJB);
            logger.debug("ReportControllerEJB:deleteReport - obj = " + obj.toString());
            ReportHome home = (ReportHome)PortableRemoteObject.narrow(obj, ReportHome.class);
            logger.debug("ReportControllerEJB:deleteReport - HOME = " + home);
            report = home.findByPrimaryKey(reportUID);
            if (report != null)
				reportVO = report.getReportVO();
			if (reportVO != null
					&& reportVO.getTheDisplayColumnDTList() != null) {

				Iterator<Object> ite = reportVO.getTheDisplayColumnDTList().iterator();
				while (ite.hasNext()) {
					DisplayColumnDT dcDT = (DisplayColumnDT) ite.next();
					dcDT.setItDelete(true);
				}
			}
            logger.debug("ReportControllerEJB:deleteReport - report = " + report);
            if(report!=null)
            	report.remove();
            logger.debug("ReportControllerEJB:deleteReport - removeDone ! for reportUID:" + reportUID);
        }
        catch(Exception e)
        {
            logger.debug("ReportControllerEJB:deleteReport exception e = " + e + "\n");
            logger.fatal(e.getMessage(), e);
            throw new EJBException(e.getMessage(), e);
        }
        logger.debug("ReportControllerEJB:deleteReport *****************Completed deleteReport***************");
    }

    /**
     * @roseuid 3C0D3AC4006A
     * @J2EE_METHOD  --  ejbActivate
     * The activate method is called when the instance is activated from its 'passive' state.
     * The instance should acquire any resource that it has released earlier in the ejbPassivate()
     * method. This method is called with no transaction context.
     */
    public void ejbActivate()
    {
    }

    /**
     * @roseuid 3C18201D01B4
     * @J2EE_METHOD  --  ejbCreate
     * Called by the container to create a session bean instance. Its parameters typically
     * contain the information the client uses to customize the bean instance for its use.
     * It requires a matching pair in the bean class and its home interface.
     */
    public void ejbCreate()
    {
    }

    /**
     * @roseuid 3C0D3AC4007E
     * @J2EE_METHOD  --  ejbPassivate
     * The passivate method is called before the instance enters the 'passive' state. The
     * instance should release any resources that it can re-acquire later in the ejbActivate()
     * method. After the passivate method completes, the instance must be in a state that
     * allows the container to use the Java Serialization protocol to externalize and store
     * away the instance's state. This method is called with no transaction context.
     */
    public void ejbPassivate()
    {
    }

    /**
     * @roseuid 3C0D3AC40060
     * @J2EE_METHOD  --  ejbRemove
     * A container invokes this method before it ends the life of the session object. This
     * happens as a result of a client's invoking a remove operation, or when a container
     * decides to terminate the session object after a timeout. This method is called with
     * no transaction context.
     */
    public void ejbRemove()
    {
    }

    /**
     * @roseuid 3C22928C0376
     * @J2EE_METHOD  --  runReport
     */
    /**
     * The runReport method builds the parameters that will be passed to the reporting
     * package (this will be SAS for the beta).  These parameters are returned in a
     * RunReportVO.
     */
    public String runReport(ReportVO reportVO, RunReportVO runReportVO, NBSSecurityObj securityObj) throws EJBException
    {
        logger.debug("ReportControllerEJB:runReport #################Started runReport################");
        SASEngine sasEngine = null;
        String sasReturn = null;
        Long dataSourceUid = new Long(-1);
        DataSourceVO dataSourceVO = null;
        try
        {
            NedssUtils nedssUtils = new NedssUtils();
            Object obj = nedssUtils.lookupBean(JNDINames.SASEJB);
            logger.debug("ReportControllerEJB:runReport - lookup = " + obj.toString());
            dataSourceUid = reportVO.getTheReportDT().getDataSourceUid();
            dataSourceVO = getDataSource(dataSourceUid, securityObj);
            SASEngineHome home = (SASEngineHome)PortableRemoteObject.narrow(obj, SASEngineHome.class);
            logger.debug("ReportControllerEJB:runReport - Found ReportHome: " + home);
            sasEngine = home.create();
            sasReturn = sasEngine.runReport(runReportVO, reportVO, dataSourceVO, securityObj);
        }
        catch(Exception e)
        {
            logger.debug("ReportControllerEJB:setReport -  exception e = " + e + "\n");
            logger.fatal(e.getMessage(), e);
            throw new EJBException(e.getMessage(), e);
        }
        logger.debug("ReportControllerEJB:runReport *****************Completed runReport***************");
        return sasReturn;
    }
    public Object runJavaReport(ReportVO reportVO, NBSSecurityObj securityObj) throws EJBException
    {
        logger.debug("ReportControllerEJB:runReport #################Started runReport################");
        Object object=null;
        try
        {
            NedssUtils nedssUtils = new NedssUtils();
            Object obj = nedssUtils.lookupBean(JNDINames.SASEJB);
            logger.debug("ReportControllerEJB:runReport - lookup = " + obj.toString());
            if(reportVO.getTheReportDT().getReportTypeCode().startsWith("JAVA_REPORT_HTML")){
            	ReportDT reportDT=reportVO.getTheReportDT();
            	if(reportDT.getLocation().equalsIgnoreCase("CR1")){
		            CR1Util cr1Util = new CR1Util();
		    	    String epiLinkId=(String)reportVO.getReportFilterMap().get(ReportConstantUtil.TEXT_FILTER);
		    		object=cr1Util.processLinkId(epiLinkId, securityObj);
            		
            	}else if(reportDT.getLocation().equalsIgnoreCase("CR2")){
            		CR2Util cr2Util = new CR2Util();
		    	    String epiLinkId=(String)reportVO.getReportFilterMap().get(ReportConstantUtil.TEXT_FILTER);
 		    		object=cr2Util.processLinkId(epiLinkId, securityObj);
            	}else if(reportDT.getLocation().equalsIgnoreCase("CR3")){
            		CR3Util cr3Util = new CR3Util();
            		String[] diagnosis=(String[])reportVO.getReportFilterMap().get(ReportConstantUtil.CVG_CUSTOM_1);
		    	    String epiLinkId=(String)reportVO.getReportFilterMap().get(ReportConstantUtil.TEXT_FILTER);
		    	    object = cr3Util.processLinkId(epiLinkId, diagnosis, securityObj);
            	}else if(reportDT.getLocation().equalsIgnoreCase("CR4")){
					CR4Util cr4Util = new CR4Util();
            		String[] hangoutArray= (String[])reportVO.getReportFilterMap().get(ReportConstantUtil.CVG_CUSTOM_2);
            		String fromConfirmationDate=(String)reportVO.getReportFilterMap().get(ReportConstantUtil.TIME_RANGE_CODE+"_FROM_TIME");
            		String toConfirmationDate=(String)reportVO.getReportFilterMap().get(ReportConstantUtil.TIME_RANGE_CODE+"_TO_TIME");
		    	    object = cr4Util.processRequest(fromConfirmationDate, toConfirmationDate, hangoutArray, securityObj);
		    	 }else if(reportDT.getLocation().equalsIgnoreCase("CR5")){
	            		CR5Util cr5Util = new CR5Util();
	            		String[] hangoutArray=(String[])reportVO.getReportFilterMap().get(ReportConstantUtil.CVG_CUSTOM_2);
	            		String[] diagnosis=(String[])reportVO.getReportFilterMap().get(ReportConstantUtil.CVG_CUSTOM_1);
	            		String fromConfirmationDate=(String)reportVO.getReportFilterMap().get(ReportConstantUtil.TIME_RANGE_CODE+"_FROM_TIME");
	            		String toConfirmationDate=(String)reportVO.getReportFilterMap().get(ReportConstantUtil.TIME_RANGE_CODE+"_TO_TIME");
			    	    object = cr5Util.processRequest(fromConfirmationDate, toConfirmationDate, hangoutArray,diagnosis, securityObj);
			     }else if(reportDT.getLocation().equalsIgnoreCase("PA3")){
	            		Pa3Util pa3Util = new Pa3Util();
	            		String[] diagnosis=(String[])reportVO.getReportFilterMap().get(ReportConstantUtil.CVG_CUSTOM_1);
	            		String fromConfirmationDate=(String)reportVO.getReportFilterMap().get(ReportConstantUtil.TIME_RANGE_CODE+"_FROM_TIME");
	            		String toConfirmationDate=(String)reportVO.getReportFilterMap().get(ReportConstantUtil.TIME_RANGE_CODE+"_TO_TIME");
	            		//will need to add diagnosis parameter to method
			    	    object = pa3Util.processRequest(fromConfirmationDate, toConfirmationDate,diagnosis, securityObj);
		    	 }else if(reportDT.getLocation().equalsIgnoreCase("PA1")){
	            		Pa1Util pa1Util = new Pa1Util();
	            		String[] diagnosis=(String[])reportVO.getReportFilterMap().get(ReportConstantUtil.CVG_CUSTOM_1);
	            		String[] worker=(String[])reportVO.getReportFilterMap().get(ReportConstantUtil.CVG_CUSTOM_2);
	            		String fromConfirmationDate=(String)reportVO.getReportFilterMap().get(ReportConstantUtil.TIME_RANGE_CODE+"_FROM_TIME");
	            		String toConfirmationDate=(String)reportVO.getReportFilterMap().get(ReportConstantUtil.TIME_RANGE_CODE+"_TO_TIME");
	            		//will need to add diagnosis parameter to method
			    	    object = pa1Util.processRequest(fromConfirmationDate, toConfirmationDate,diagnosis,  worker, reportVO.getTheReportDT().getReportUid(),reportVO.getTheReportDT().getReportTitle(), securityObj);
				 }
            }
        }
        catch(Exception e)
        {
             logger.debug("ReportControllerEJB:setReport -  exception e = " + e + "\n");
            logger.fatal(e.getMessage(), e);
            throw new EJBException(e.getMessage(), e);
        }
        logger.debug("ReportControllerEJB:runJavaReport *****************Completed runJavaReport***************");
        return object;
    }

    /**
     * @roseuid 3C22928902F9
     * @J2EE_METHOD  --  saveAsNewReport
     */
    /**
      * ReportVO is saved as new reportVO object with new UID's for all the dependent objects.
      */
    public ReportVO saveAsNewReport(ReportVO reportVO, NBSSecurityObj securityObj)
    {
        logger.debug("ReportControllerEJB:saveAsNewReport started");
        Long reportUID = new Long(-1);
        //Long dataSourceUid = new Long(-1);
        ReportVO rVO = new ReportVO();
        Report report = null;
        try
        {
            NedssUtils nedssUtils = new NedssUtils();
            Object obj = nedssUtils.lookupBean(JNDINames.REPORT_EJB);
            logger.debug("ReportControllerEJB:saveAsNewReport - lookup = " + obj.toString());
            ReportHome home = (ReportHome)PortableRemoteObject.narrow(obj, ReportHome.class);
            logger.debug("ReportControllerEJB:saveAsNewReport - Found ReportHome: " + home);
            report = home.create(reportVO);
            rVO = report.getReportVO();
            reportUID = rVO.getTheReportDT().getReportUid();
            logger.debug("ReportControllerEJB:saveAsNewReport ****************set completed with UID *****************" + reportUID);
        }
        catch(Exception e)
        {
            logger.debug("ReportControllerEJB:saveAsNewReport -  exception e = " + e + "\n");
            logger.fatal(e.getMessage(), e);
            throw new EJBException(e.getMessage(), e);
        }
        logger.debug("ReportControllerEJB:saveAsNewReport ****************set completed ");
        return rVO;
    }

    public TreeMap<Object,Object> getDistinctColumnValues(String schema, String sqlQuery, NBSSecurityObj securityObj) throws java.rmi.RemoteException {
    	
        Connection dbConnection = null;
        Statement stmt = null;
        ResultSet resultSet = null;
        TreeMap<Object,Object> returnMap = new TreeMap<Object,Object>();

        try
        {
        	if(schema.equalsIgnoreCase(NEDSSConstants.ODS))
        		dbConnection = getConnection();
        	else
        		dbConnection = getConnection(schema);
        	
        	stmt = dbConnection.createStatement();
            resultSet = stmt.executeQuery(sqlQuery);
            int counter = 1;
            
            while(resultSet.next()) {
            	
            	returnMap.put(resultSet.getString(1),Integer.toString(counter));
            	counter++;
            	
            }

        }
        catch(SQLException sqlex)
        {
            logger.fatal("SQLException in ReportControllerEJB:getDistinctColumnValues:\n" + sqlex.getMessage(), sqlex);
            throw new NEDSSSystemException(sqlex.getMessage(),sqlex);
        }
        catch(Exception ex)
        {
            logger.fatal("Exception while in ReportControllerEJB:getDistinctColumnValues:\n" + ex.getMessage(), ex);
            throw new NEDSSSystemException(ex.getMessage(),ex);
        }
        finally
        {
            try
            {
                if(resultSet != null)
                {
                    resultSet.close();
                }
            }
            catch(SQLException sex1)
            {
            	logger.fatal("SQLException :" + sex1.getMessage(), sex1);
                throw new NEDSSSystemException( sex1.getMessage(),sex1);
            }
            try
            {
                if(stmt != null)
                {
                	stmt.close();
                }
            }
            catch(SQLException sex2)
            {
            	logger.fatal("SQLException :" + sex2.getMessage(), sex2);
                throw new NEDSSSystemException( sex2.getMessage(),sex2);
            }
            try
            {
                if(dbConnection != null)
                {
                    dbConnection.close();
                }
            }
            catch(SQLException sex3)
            {
            	logger.fatal("SQLException :" + sex3.getMessage(), sex3);
                throw new NEDSSSystemException( sex3.getMessage(),sex3);
            }
        }     
    	
    	
    	return returnMap;
    }
}
