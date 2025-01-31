package gov.cdc.nedss.webapp.nbs.action.report;

import gov.cdc.nedss.report.vo.ReportVO;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.webapp.nbs.action.report.util.ReportUtil;
import gov.cdc.nedss.webapp.nbs.form.report.ReportForm;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class ReportAction extends DispatchAction {

	static final LogUtils logger = new LogUtils(ReportAction.class.getName());

	public ActionForward LoadFilterCriteria(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		String actionForward = "default";
		ReportForm reportForm = (ReportForm) form;
		reportForm.resetMaps();
		ReportUtil ru = new ReportUtil();
		ReportVO rVO = ru.getReport(request);
		reportForm.getSearchAttributeMap().put("ReportVO", rVO);
		request.setAttribute("ReportVO", rVO);
		request.setAttribute("filterColumnMap", ru.getFilterColumns(rVO, request.getSession()));
		rVO.getReportFilterMap().putAll(reportForm.getSearchAttributeMap());
		return (mapping.findForward(actionForward));
	}

	public ActionForward LoadReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		request.setAttribute("mode", request.getParameter("mode"));
		ReportForm reportForm = (ReportForm) form;
		ReportVO rVO = (ReportVO) reportForm.getSearchAttributeMap().get(
				"ReportVO");
		rVO.getReportFilterMap().putAll(reportForm.getSearchAttributeMap());
		ReportUtil ru = new ReportUtil();
		Object obj = ru.getReportOutput(rVO, request.getSession());
		request.setAttribute(rVO.getTheReportDT().getLocation(), obj);
		request.setAttribute("reportCriteria", ru.buildReportCriteria(rVO, request.getSession()));
		request.setAttribute("reportName", rVO.getTheReportDT().getReportTitle());
		return (mapping.findForward(rVO.getTheReportDT().getLocation()));
	}

}
