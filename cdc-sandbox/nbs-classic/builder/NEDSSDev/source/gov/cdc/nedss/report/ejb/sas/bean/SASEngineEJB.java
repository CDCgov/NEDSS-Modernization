package gov.cdc.nedss.report.ejb.sas.bean;

import gov.cdc.nedss.exception.NEDSSException;
import gov.cdc.nedss.report.util.ReportConstantUtil;
import gov.cdc.nedss.report.util.ReportSQLBuilder;
import gov.cdc.nedss.report.vo.DataSourceVO;
import gov.cdc.nedss.report.vo.ReportVO;
import gov.cdc.nedss.report.vo.RunReportVO;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.sas.rmi.Connection;
import com.sas.rmi.Rocf;
import com.sas.sasserver.submit.SubmitInterface;

public class SASEngineEJB implements SessionBean {

	static final LogUtils logger = new LogUtils((SASEngineEJB.class).getName());

	static final PropertyUtil propUtil = PropertyUtil.getInstance();

	private Connection sasConnection = null;

	private SubmitInterface submit = null;

	private Rocf rocf;

	private SessionContext sessionContext;

	public SASEngineEJB() {
	}

	public void setSessionContext(SessionContext sessionContext)
			throws RemoteException {
		this.sessionContext = sessionContext;
	}

	public void ejbActivate() throws RemoteException {
	}

	public void ejbCreate() {
	}

	public void ejbPassivate() throws RemoteException {
	}

	public void ejbRemove() throws RemoteException {
	}

	public String runReport(RunReportVO runReportVO, ReportVO reportVO,
			DataSourceVO dataSourceVO, NBSSecurityObj securityObj)
			throws RemoteException {
		logger.info("\n=== runReport Start ==");
		String host = runReportVO.getHost();
		int port = runReportVO.getPort();
		String exportType = runReportVO.getExportType();
		String reportTypeCode = reportVO.getTheReportDT().getReportTypeCode();
		String sasProgramName = reportVO.getTheReportDT().getLocation();
		String dataSourceName = dataSourceVO.getTheDataSourceDT()
				.getDataSourceName();
		List displayColumns = reportVO.getTheDisplayColumnDTList();
		String reportTitle = reportVO.getTheReportDT().getReportTitle();

		String libname = null;
		String dataSetName = null;
		String whereSAS = null;
		String timePeriod = null;
		String footer = null;
		String subsetSQL = null;
		String sasLog = null;
		HashMap whereClauses = null;
		String sasouttxt = null;
		String orderClause = null;
		try {
			logger.info("\n===Servlet host==" + host);
			logger.info("\n===port==" + port);
			logger.info("\n===displayColumns==" + displayColumns);
			logger.info("\n===exportType==" + exportType);
			logger.info("\n===reportTypeCode==" + reportTypeCode);
			logger.info("\n===sasProgramName==" + sasProgramName);
			logger.info("\n===dataSourceName==" + dataSourceName);
			logger.info("\n===dataSource.JurisdictionSecurity==="
					+ dataSourceVO.getTheDataSourceDT()
							.getJurisdictionSecurity());
			logger.info("\n===securityWhereClause==="
					+ securityObj.getDataAccessWhereClause(
							NBSBOLookup.REPORTING,
							NBSOperationLookup.VIEWREPORTTEMPLATE));

			StringTokenizer sz = new StringTokenizer(dataSourceName, ".");
			libname = sz.nextToken();
			dataSetName = sz.nextToken();
			logger.info("\n===libname==" + libname);
			logger.info("\n===dataSetName==" + dataSetName);
			whereClauses = ReportSQLBuilder.buildWhereClause(reportVO,
					dataSourceVO);
			whereSAS = whereClauses.get("where").toString();

			timePeriod = whereClauses.get("timePeriod").toString();
			String END_RANGE=null;
			String BEGIN_RANGE=null;
			if( whereClauses.get("END_RANGE") != null &&  whereClauses.get("BEGIN_RANGE")!=null){
				 END_RANGE= whereClauses.get("END_RANGE").toString();
				BEGIN_RANGE=whereClauses.get("BEGIN_RANGE").toString();
			}
			footer = whereClauses.get("footer").toString();
			logger.info("\n===whereSAS==" + whereSAS);
			logger.info("\n===timePeriod==" + timePeriod);
			logger.info("\n===footer==" + footer);

			if (whereSAS.trim().equalsIgnoreCase("where")) {
				throw new NEDSSException(
						"All filters missing.  SAS report not submitted.");
			}

			subsetSQL = ReportSQLBuilder.getSubsetSql(libname, dataSetName,
					displayColumns, whereSAS);

			orderClause = ReportSQLBuilder.buildOderbyClause(reportVO);

			StringBuffer s = new StringBuffer(subsetSQL);
			String secWhere = null;
			String facilityWhere = null;
			
			//Jurisdiction Security
			if (dataSourceVO.getTheDataSourceDT().getJurisdictionSecurity().equalsIgnoreCase("Y")) {
				
				if (reportVO.getTheReportDT().getShared().equalsIgnoreCase(ReportConstantUtil.TEMPLATE_REPORT))
					secWhere = securityObj.getDataAccessWhereClause(
							NBSBOLookup.REPORTING,
							NBSOperationLookup.VIEWREPORTTEMPLATE);
				if (reportVO.getTheReportDT().getShared().equalsIgnoreCase(ReportConstantUtil.PRIVATE_REPORT))
					secWhere = securityObj.getDataAccessWhereClause(
							NBSBOLookup.REPORTING,
							NBSOperationLookup.VIEWREPORTPRIVATE);
				if (reportVO.getTheReportDT().getShared().equalsIgnoreCase(ReportConstantUtil.PUBLIC_REPORT))
					secWhere = securityObj.getDataAccessWhereClause(
							NBSBOLookup.REPORTING,
							NBSOperationLookup.VIEWREPORTPUBLIC);
				if (reportVO.getTheReportDT().getShared().equalsIgnoreCase(ReportConstantUtil.REPORTING_FACILITY_REPORT))
					secWhere = securityObj.getDataAccessWhereClause(
							NBSBOLookup.REPORTING,
							NBSOperationLookup.VIEWREPORTREPORTINGFACILITY);
			} 
			
			//If SecureByFacility is checked and default Facility is associated to the USER, add appropriate ReportingFacility where clause
			if(dataSourceVO.getTheDataSourceDT().getReportingFacilitySecurity() != null && dataSourceVO.getTheDataSourceDT().getReportingFacilitySecurity().equalsIgnoreCase("Y")) {
				Long reportingFacilityUid = securityObj.getTheUserProfile().getTheUser().getReportingFacilityUid();			
				if(reportingFacilityUid != null) {
					facilityWhere = " (REPORTING_FACILITY_UID = " + reportingFacilityUid + ")";
				}
			}
			//When both 'jurisdiction_security' and 'reporting_facility_security' are 'N'
			if(secWhere == null && facilityWhere == null) {
				s.insert(subsetSQL.indexOf(";quit;"), orderClause);
			//When 'reporting_facility_security' is 'N'				
			} else if(secWhere != null && facilityWhere == null) {
				secWhere = secWhere + orderClause;
				s.insert(subsetSQL.indexOf(";quit;"), " AND " + secWhere);
			//When 'jurisdiction_security' is 'N'	
			} else if(secWhere == null && facilityWhere != null) {
				facilityWhere = facilityWhere + orderClause;
				s.insert(subsetSQL.indexOf(";quit;"), " AND " + facilityWhere);
			//When both 'jurisdiction_security' and 'reporting_facility_security' are 'Y'
			} else if(secWhere != null && facilityWhere != null) {
				secWhere = secWhere + " AND " + facilityWhere + orderClause;
				s.insert(subsetSQL.indexOf(";quit;"), " AND " + secWhere);
			}
			
			subsetSQL = s.toString();
			
			logger.debug("\nsubsetSQL: " + subsetSQL);
			
			String sasVersion = PropertyUtil.getInstance().getSasVersion();
			if(sasVersion.equals(NEDSSConstants.SAS_VERSION9_3)) {
				initSAS93Connection();
					
			}else if(sasVersion.equals(NEDSSConstants.SAS_VERSION9_4)) {
				initSAS94Connection();
			}
			
			// Pass parameters
			String sasReportHome = PropertyUtil.getInstance().getSasLocation();

			StringBuffer stmt = new StringBuffer(" %include '" + sasReportHome
					+ "autoexec.sas" + "';");
			stmt.append("%include RPTUTIL(reportenv.sas);");
			stmt.append(" %let host=" + host + ";");
			stmt.append(" %let port=" + port + ";");
			stmt.append(" %let ExportType=" + exportType + ";");
			stmt.append(" %let DataSourceName=" + dataSetName + ";");
			stmt.append(" %let TimePeriod=" + timePeriod + ";");
			stmt.append(" %let reportTitle=" + reportTitle + ";");
			stmt.append(" %let orderClause=" + orderClause + ";");
			stmt.append(whereClauses
					.get(ReportConstantUtil.BAS_DAYS) != null ? " %let daysValue="
							+ whereClauses.get(ReportConstantUtil.BAS_DAYS)
							+ ";" : " %let daysValue=;");
			
			if( whereClauses.get("END_RANGE") != null &&  whereClauses.get("BEGIN_RANGE")!=null){
				stmt.append(" %let BEGIN_RANGE=" + BEGIN_RANGE + ";");
			stmt.append(" %let END_RANGE=" + END_RANGE + ";");
			}
			stmt.append(" %let Footer=%nrstr(\""
					+ footer.toString().replace('"', ' ') + "\");");

			// subsetting
			stmt.append(subsetSQL);
			stmt.append(" %include NBSPGM(" + sasProgramName + ") / lrecl=50000;");
			logger.debug("\nSAS Statement: " + stmt.toString());
			submit.setProgramText(stmt.toString());
			sasouttxt = submit.getOutputText();
			sasLog = submit.getLastLogText();
			if(sasouttxt != null && sasouttxt.equals("")) {
				sasouttxt = sasLog;
			}
			
			logger.debug("\nSAS Program: \n" + sasProgramName);
			logger.debug("\nSAS Output: \n" + sasouttxt);
			logger.debug("\nSAS Log: \n" + submit.getLastLogText());
		} catch (NEDSSException ne) {
			logger.fatal("NEDSSException occured: " + ne.getMessage(),ne);
			sasouttxt = "Error in runReport, Check Server Message.";
			throw new EJBException(ne.getMessage(), ne);
		} catch (Exception e) {
			if (sasConnection == null || !sasConnection.isConnected()) {
				logger.error("\nSAS Server Maybe Down!");
			}
			logger.fatal(e.getMessage(), e);
			throw new EJBException(e.getMessage(), e);
		} finally {
			rocf.stop();
			sasConnection = null;
			logger.info("\n===runReport End ==");
		}
		return sasouttxt;
	}

	private void initSAS94Connection() throws Exception {
		try {
			String sashost = propUtil.getProperties()
					.getProperty("SASServerIP");
			String sasarch = propUtil.getProperties().getProperty(
					"SASServerArchitecture");
			rocf = new Rocf();
			sasConnection = new Connection();
			com.sas.rmi.Rocf rocf = new Rocf();
			com.sas.rmi.Connection sasConnection= new Connection();
			Connection.setServerArchitecture(sasConnection,propUtil.getSASServerArchitecture());
			sasConnection.setClassFactory(rocf);
			
			
			try {
				getSasConnection(sasConnection);
				
			} catch (Exception e) {
				logger.debug("Exception thrown while making SAS connection", e);
			}finally {
				logger.error("Exception raised but not caught HERE TOO!!!!");
			} 
			sasConnection.setHost( propUtil.getSASServerIP());
			sasConnection.setPort(2323);
			sasConnection.setUsernamePrompt(propUtil.getUserNamePrompt());
			sasConnection.setPasswordPrompt(propUtil.getPassWordPrompt());
			sasConnection.setUsername( propUtil.getUserName());
			sasConnection.setPassword( propUtil.getPassWord());
			sasConnection.setLogTrap( true );
			sasConnection.setTraceProxyClient(true);
			  
			sasConnection.connect();
			
			logger.debug("The connection details are Username:"
					+ sasConnection.getUsername());
			logger.debug("The connection details are UsernamePrompt:"
					+ sasConnection.getUsernamePrompt());
			logger.debug("The connection details are PasswordPrompt:"
					+ sasConnection.getPasswordPrompt());
//			logger.debug("The connection details are Password:"
//					
//					+ sasConnection.getPassword());
			logger.debug("The connection details for command are :"
					+ sasConnection.getCommand());
			logger.debug("The connection details for Port are :"
					+ sasConnection.getPort());
			
			submit = (SubmitInterface) rocf.newInstance(SubmitInterface.class,
					sasConnection);
		} catch (Exception ex) {
			logger.fatal("System Error got! check details for connection parameters and see if the sas spawner is runing!"+ ex.getMessage(),ex);
			throw new EJBException(ex.getMessage(), ex);
		}
	}
	private void getSasConnection(Connection sasConnection) {
		try {
			sasConnection.connect();
		} catch (Exception e) {
			logger.error("Exception raised", e);
		}finally {
			logger.error("Exception raised but not caught!!!!");
		}
		
	}
	
	private void initSAS93Connection() throws Exception {
		try {
			String sashost = propUtil.getProperties()
					.getProperty("SASServerIP");
			rocf = new Rocf();
			sasConnection = new Connection();
			Connection.setServerArchitecture(sasConnection, "PC");
			logger.debug("The connection details are Username:"
					+ sasConnection.getUsername());
			logger.debug("The connection details are UsernamePrompt:"
					+ sasConnection.getUsernamePrompt());
			logger.debug("The connection details are PasswordPrompt:"
					+ sasConnection.getPasswordPrompt());
//			logger.debug("The connection details are Password:"
//					+ sasConnection.getPassword());
			logger.debug("The connection details for command are :"
					+ sasConnection.getCommand());
			logger.debug("The connection details for Port are :"
					+ sasConnection.getPort());
			sasConnection.setHost(sashost);
			submit = (SubmitInterface) rocf.newInstance(SubmitInterface.class,
					sasConnection);
		} catch (Exception ex) {
			logger.fatal("System Error got! check details for connection parameters and see if the sas spawner is runing!"+ ex.getMessage(),ex);
			throw new EJBException(ex.getMessage(), ex);
		}
	}

}