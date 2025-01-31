package gov.cdc.nedss.report.ejb.reportcontrollerejb.bean;

import gov.cdc.nedss.report.vo.*;
import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;

import java.rmi.RemoteException;
import java.util.*;

import javax.ejb.*;

public interface ReportController extends javax.ejb.EJBObject
{

    /**
     * @roseuid 3C228F6603A9
     * @J2EE_METHOD  --  getDataSource
     */
    public DataSourceVO getDataSource(java.lang.Long dataSourceUID, NBSSecurityObj securityObj) throws java.rmi.RemoteException, javax.ejb.EJBException;
    public boolean isDataMartFromDataSourceName(java.lang.Long reportUid, java.lang.Long dataSourceUID, NBSSecurityObj securityObj) throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * @roseuid 3C182B8E0058
     * @J2EE_METHOD  --  getDataSourceList
     */
    /**
     * The getDataSourceList method returns a Collection<Object>  of DataSourceDT objects.
     * There is an element in the Collection<Object>  for each DataSource that matches the
     * users access permissions as read from the security object.
     */
    public Collection<Object>  getDataSourceList(NBSSecurityObj securityObj) throws java.rmi.RemoteException, NEDSSSystemException;

    /**
     * @roseuid 3C228DA10131
     * @J2EE_METHOD  --  getDisplayableColumns
     */
    /**
     * The getDisplayableColumns method returns a Collection<Object>  of DataSourceColumnDT
     * objects.  The Collection<Object>  contains an element for each DataSourceColumn
     * associated with the DataSource whose displayable indicator is set to true.
     */
    public Collection<Object>  getDisplayableColumns(java.lang.Long reportUID, java.lang.Long dataSourceUID, NBSSecurityObj securityObj) throws NEDSSSystemException, java.rmi.RemoteException;

    /**
     * @roseuid 3C228FDB0036
     * @J2EE_METHOD  --  getFilterableColumns
     */
    /**
     * The getFilterableColumns method returns a Collection<Object>  of DataSourceColumnDT
     * objects.  The Collection<Object>  contains an element for each DataSourceColumn
     * associated with the DataSource whose filterable indicator is set to true.
     */
    public Collection<Object>  getFilterableColumns(java.lang.Long dataSourceUID, NBSSecurityObj securityObj) throws NEDSSSystemException, java.rmi.RemoteException;

    /**
     * @roseuid 3C1820260167
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
    public Collection<Object>  getMyReportList(NBSSecurityObj securityObj) throws NEDSSSystemException, java.rmi.RemoteException;

    /**
     * @roseuid 3C181C550130
     * @J2EE_METHOD  --  setReport
     */
    /**
     * The setReport method is currently used to update an existing report.  A user
     * can only update a report that they own (userUID = ownerUID).
     */
    public ReportVO setReport(ReportVO reportVO, NBSSecurityObj securityObj) throws java.rmi.RemoteException, javax.ejb.EJBException, javax.ejb.CreateException;

    /**
     * @roseuid 3C2288BD0194
     * @J2EE_METHOD  --  getReport
     */
    public ReportVO getReport(java.lang.Long reportUID, java.lang.Long dataSourceUID, NBSSecurityObj securityObj) throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * @roseuid 3C181D3C0038
     * @J2EE_METHOD  --  getReportList
     */
    /**
     * Returns a collection of ReportDT objects.  The collection contains an element
     * for each report that matches the user's access permissions as read from the
     * SecurityObj.  This includes both shared and private reports.  One way to
     * implement this method would be to call both the getPrivateReportList and
     * getSharedReportList methods and combine their returned collections into one.
     * This is probably not the most efficient implementation, though.
     */
    public Collection<Object>  getReportList(NBSSecurityObj securityObj) throws NEDSSSystemException, java.rmi.RemoteException;

    /**
     * @roseuid 3C228B85004E
     * @J2EE_METHOD  --  getReportTemplateList
     */
    /**
     * The getReportTemplateList method returns a Collection<Object>  of ReportDT objects.
     * Each ReportDT object represents a report whose category is set to Template and
     * whose access permissions match the users access permissions.
     */
    public Collection<Object>  getReportTemplateList(NBSSecurityObj securityObj) throws NEDSSSystemException, java.rmi.RemoteException;

    /**
     * @roseuid 3C1822730196
     * @J2EE_METHOD  --  getSharedReportList
     */
    /**
     * Returns a collection of ReportDT objects.  The collection contains an element
     * for each shared report that matches the user's access permissions as read from
     * the SecurityObj.  A report is shared if it's shared indicator is true.  There
     * is some overlap between the reports returned by the getMyReportList method and
     * this method.  The overlap are the reports that the user owns and has shared.
     */
    public Collection<Object>  getSharedReportList(NBSSecurityObj securityObj) throws NEDSSSystemException, java.rmi.RemoteException;

    /**
     * @roseuid 3C228AD60241
     * @J2EE_METHOD  --  deleteReport
     */
    /**
     * This method provides a shortcut to deleting a report.  Rather than walk the
     * entire ReportVO setting every member to deleted, this method can be called to
     * remove the report and associated values.  This is accomplished by taking the
     * following actions based on the value of the DirtyMarkerInterface:  items marked
     * new will be ignored, items marked dirty will be deleted, items marked unchanged
     * (not dirty) will be deleted, and items marked deleted will be deleted.  For the
     * beta release, a user can only delete reports that they own (userUID = ownerUID).
     */
    public void deleteReport(java.lang.Long reportUID, java.lang.Long dataSourceUID, NBSSecurityObj securityObj) throws java.rmi.RemoteException;

    /**
     * @roseuid 3C22916200BA
     * @J2EE_METHOD  --  runReport
     */
    /**
     * The runReport method builds the parameters that will be passed to the reporting
     * package (this will be SAS for the beta).  These parameters are returned in a
     * RunReportVO.
     */
    public String runReport(ReportVO reportVO, RunReportVO runReportVO, NBSSecurityObj securityObj) throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * @roseuid 3C1825280084
     * @J2EE_METHOD  --  saveAsNewReport
     */
    /**
     * The saveAsNewReport method is used to clone an existing report.  The ReportVO
     * that is passed in is expected to be fully populated from an existing report.
     * It is also expected that any changes the user has made will be included in the
     * VO (including a new report name and description).  The method will then take
     * the VO and create new instances of each object type (ReportDT, DisplayColumnDT,
     * etc.) required.  During this process, all UIDs will be replaced with the UIDs
     * for the newly created instances.  Additionally, the report ownerUID will be set
     * to the userUID.  To determine which instances need to be created, the method
     * will use the DirtyMarkerInterface in the following manner:  items marked
     * unchanged (not dirty) will be treated as new, items marked dirty will be
     * treated as new, items marked new will be treated as new, and items marked
     * deleted will be ignored.
     */
    public ReportVO saveAsNewReport(ReportVO reportVO, NBSSecurityObj securityObj) throws java.rmi.RemoteException;

    /**
     * 
     * @param schema
     * @param sqlQuery
     * @param securityObj
     * @return
     * @throws java.rmi.RemoteException
     */
    public TreeMap<Object,Object> getDistinctColumnValues(String schema, String sqlQuery, NBSSecurityObj securityObj) throws java.rmi.RemoteException;
    public Object runJavaReport(ReportVO reportVO, NBSSecurityObj securityObj) throws java.rmi.RemoteException, javax.ejb.EJBException;

}
