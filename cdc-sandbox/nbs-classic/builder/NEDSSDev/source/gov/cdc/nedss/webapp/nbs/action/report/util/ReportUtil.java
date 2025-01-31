package gov.cdc.nedss.webapp.nbs.action.report.util;

import gov.cdc.nedss.report.dt.DataSourceColumnDT;
import gov.cdc.nedss.report.dt.FilterCodeDT;
import gov.cdc.nedss.report.dt.ReportFilterDT;
import gov.cdc.nedss.report.util.ReportConstantUtil;
import gov.cdc.nedss.report.vo.DataSourceVO;
import gov.cdc.nedss.report.vo.ReportVO;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ReportUtil {

	static final LogUtils logger = new LogUtils((ReportUtil.class).getName());
	protected MainSessionCommand msCommand = null;
	private String sBeanJndiName = "";
	private String sMethod = "";
	private Object[] oParams = null;
	protected NBSSecurityObj secObj = null;
	private MainSessionHolder mainSessionHolder = new MainSessionHolder();
	
	@SuppressWarnings("unchecked")
	public ReportVO getReport(HttpServletRequest request)
			throws ServletException {
		ReportVO reportVO = null;
		HttpSession session = request.getSession();
		Long reportUID = new Long(
				request.getParameter(ReportConstantUtil.REPORT_UID));
		Long dataSourceUID = new Long(
				request.getParameter(ReportConstantUtil.DATASOURCE_UID));
		sBeanJndiName = JNDINames.REPORT_CONTROLLER_EJB;
		sMethod = "getReport";
		oParams = new Object[] { reportUID, dataSourceUID };
		try {
			if (msCommand == null) {
				msCommand = mainSessionHolder.getMainSessionCommand(session);
			}
			ArrayList<Object> report = (ArrayList<Object>) msCommand
					.processRequest(sBeanJndiName, sMethod, oParams);
			reportVO = (ReportVO) report.get(0);
		} catch (Exception e) {
			logger.debug("ReportUtil.getReport", e);
			throw new ServletException(e);

		}
		return reportVO;
	}

	@SuppressWarnings("unchecked")
	public DataSourceVO getDataSource(Long dataSourceUid, HttpSession session)
			throws ServletException {

		DataSourceVO dsVO = null;

		sBeanJndiName = JNDINames.REPORT_CONTROLLER_EJB;
		sMethod = "getDataSource";
		oParams = new Object[] { dataSourceUid };
		try {
			if (msCommand == null) {
				msCommand = mainSessionHolder.getMainSessionCommand(session);
			}

			ArrayList<Object> dsVOList = (ArrayList<Object>) msCommand
					.processRequest(sBeanJndiName, sMethod, oParams);
			dsVO = (DataSourceVO) dsVOList.get(0);
		} catch (Exception e) {
			logger.debug("ReportUtil.getDataSource", e);
			throw new ServletException(e);

		}

		return dsVO;

	}

	public String getFilterColumn(DataSourceVO dsVO, ReportFilterDT filterDT) {
		Long columnUid = filterDT.getColumnUid();
		if (filterDT.getTheFilterCodeDT().getFilterCode()
				.endsWith("CUSTOM_N02"))
			return "Place";
		ArrayList<Object> dataSourceColumnDTs = (ArrayList<Object>) dsVO
				.getTheDataSourceColumnDTCollection();
		for (Iterator<Object> i = dataSourceColumnDTs.iterator(); i.hasNext();) {
			DataSourceColumnDT dataSourceColumnDT = (DataSourceColumnDT) i
					.next();
			if (dataSourceColumnDT.getColumnUid().longValue()==columnUid.longValue()) {
				return dataSourceColumnDT.getColumnTitle();
			}
		}
		return "";
	}
	
	@SuppressWarnings("unchecked")
	public Object getReportOutput(ReportVO reportVO,
			HttpSession session) throws ServletException {

		Object obs = null;

		sBeanJndiName = JNDINames.REPORT_CONTROLLER_EJB;
		sMethod = "runJavaReport";
		oParams = new Object[] { reportVO };
		try {
			if (msCommand == null) {
				msCommand = mainSessionHolder.getMainSessionCommand(session);
			}

			ArrayList<Object> dsVOList = (ArrayList<Object>) msCommand
					.processRequest(sBeanJndiName, sMethod, oParams);
			obs = dsVOList.get(0);
		} catch (Exception e) {
			logger.debug("ReportUtil.getReportOutput", e);
			throw new ServletException(e);

		}
		return obs;
	}
	
	public String buildReportCriteria(ReportVO reportVO, HttpSession session)
			throws ServletException {
		Map<Object, Object> reportCriteriaMap = reportVO.getReportFilterMap();
		DataSourceVO dsVO = getDataSource(reportVO.getTheReportDT()
				.getDataSourceUid(), session);
		StringBuffer criteriaString = new StringBuffer("");
		ArrayList<Object> rfDTCollection = (ArrayList<Object>) reportVO
				.getTheReportFilterDTCollection();
		if (rfDTCollection != null && !rfDTCollection.isEmpty()) {
			Iterator<Object> ite = rfDTCollection.iterator();
			while (ite.hasNext()) {
				// See if Its Text Filter
				ReportFilterDT filterDT = (ReportFilterDT) ite.next();
				if (reportCriteriaMap.get(filterDT.getTheFilterCodeDT()
						.getFilterCode()) != null || reportCriteriaMap.get(filterDT.getTheFilterCodeDT()
								.getFilterCode()+"_FROM_TIME")!=null) {
					String columnName = getFilterColumn(dsVO, filterDT);
					criteriaString.append("<b>")
							.append(columnName)
							.append(": ").append("</b>")
							.append(getFilterValue(reportCriteriaMap,filterDT
									.getTheFilterCodeDT()))
							.append(" and ");
				}

			}
			if (criteriaString.lastIndexOf(" and ") != -1) {
				return criteriaString.substring(0, criteriaString.length() - 5);
			}

		}
		return criteriaString.toString();
	}
	
	public String getFilterValue(Map<Object, Object> reportCriteriaMap,
			FilterCodeDT filterCodeDT) {
		String filterCode = filterCodeDT.getFilterCode();
		if (filterCode.equals(ReportConstantUtil.TEXT_FILTER) || filterCode.equals(ReportConstantUtil.DAYS_FILTER))
			return (String) reportCriteriaMap.get(filterCode);
		else if (filterCode.startsWith(ReportConstantUtil.CVG_CUSTOM)) {
			String[] values = (String[]) reportCriteriaMap.get(filterCode);
			StringBuffer returnValue = new StringBuffer("");
			for (String value : values) {
				if (filterCodeDT.getFilterCodeSetNm().equals("PLACE_LIST"))
					returnValue.append(
							getPlaceDescription(value)).append(
							", ");
				else
					returnValue.append(
							CachedDropDowns.getCodeDescTxtForCd(value,
									filterCodeDT.getFilterCodeSetNm())).append(
							", ");
			}
			if (returnValue.lastIndexOf(", ") != -1) {
				return returnValue.substring(0, returnValue.length() - 2)
						.toString();
			}
			return returnValue.toString();
		} else if (filterCode.startsWith(ReportConstantUtil.TIME_RANGE_CODE)) {
			String fromTime = (String) reportCriteriaMap.get(filterCode
					+ "_FROM_TIME");
			String toTime = (String) reportCriteriaMap.get(filterCode
					+ "_TO_TIME");
			return fromTime + " - " + toTime;
		}
		return null;
	}
	
	public String getPlaceDescription(String placeUid) {
		ArrayList<Object> places = CachedDropDowns.getPlaceList();
		if (places != null) {
			Iterator<Object> ite = places.iterator();
			while (ite.hasNext()) {
				DropDownCodeDT ddDT = (DropDownCodeDT) ite.next();
				if (ddDT.getKey().equals(placeUid))
					return ddDT.getValue();
			}
		}
		return null;
	}
	
	public Map<Object, Object> getFilterColumns(ReportVO reportVO,
			HttpSession session) throws ServletException {
		Map<Object, Object> filterColumnMap = new HashMap<Object, Object>();
		DataSourceVO dsVO = getDataSource(reportVO.getTheReportDT()
				.getDataSourceUid(), session);
		ArrayList<Object> rfDTCollection = (ArrayList<Object>) reportVO
				.getTheReportFilterDTCollection();
		if (rfDTCollection != null && !rfDTCollection.isEmpty()) {
			Iterator<Object> ite = rfDTCollection.iterator();
			while (ite.hasNext()) {
				// See if Its Text Filter
				ReportFilterDT filterDT = (ReportFilterDT) ite.next();
				String columnName = null;
				if (filterDT.getTheFilterCodeDT().getFilterCode()
						.endsWith("CUSTOM_N02"))
					columnName = "Place";
				else
					columnName = getFilterColumn(dsVO, filterDT);
				filterColumnMap.put(filterDT.getTheFilterCodeDT()
						.getFilterCode(), columnName);

			}
		}
		return filterColumnMap;
	}

}
