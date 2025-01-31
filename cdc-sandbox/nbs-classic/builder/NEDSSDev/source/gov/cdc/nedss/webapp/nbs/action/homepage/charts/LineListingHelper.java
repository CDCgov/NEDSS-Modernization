package gov.cdc.nedss.webapp.nbs.action.homepage.charts;

import gov.cdc.nedss.systemservice.ejb.nbschartsejb.dt.ChartElementDT;
import gov.cdc.nedss.systemservice.ejb.nbschartsejb.dt.ChartReportMetadataDT;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.webapp.nbs.action.localfields.html.Tag;
import gov.cdc.nedss.webapp.nbs.action.util.RulesEngineUtil;
import gov.cdc.nedss.webapp.nbs.form.homepage.HomePageForm;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @author Narendra Mallela
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: Computer Sciences Corporation</p>
 * LineListingHelper.java
 * Oct 19, 2009
 * @version 1.0
 */
public class LineListingHelper {
	

	
	@SuppressWarnings("unchecked")
	private static String createDataset(String chartId, HttpServletRequest req, boolean secured)
    {
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		RulesEngineUtil util = new RulesEngineUtil();
		int [] wY = util.CalcMMWR(formatter.format(new Date()));
		
		//Pass ChartMetaDataDT as a param, retrieve it first
		HomePageForm form = req.getSession().getAttribute("homepageForm") == null ? new HomePageForm() : (HomePageForm) req.getSession().getAttribute("homepageForm");
		Map<Object,Object> cmMap = form.getChartMetadataMap();
		ChartReportMetadataDT cmDT = (ChartReportMetadataDT) cmMap.get(chartId);

		NBSSecurityObj secObj = (NBSSecurityObj)req.getSession().getAttribute("NBSSecurityObject");
		ArrayList<Object> results = new ArrayList<Object> ();
		Object[] searchParams = new Object[] {secObj, wY, secured, cmDT};
		Object[] oParams = new Object[] { JNDINames.NBS_CHART_DAO_CLASS, "selectCaseCountsForMmwr", searchParams};
		try {
			results = (ArrayList<Object> ) ChartElementDataSet.processRequest(oParams, req.getSession());
		} catch (Exception e) {
			e.printStackTrace();
		}
		//req.setAttribute("resultList", results);
		StringBuffer sb = new StringBuffer();
		sb.append("<style type=\"text/css\">table.llc {border-color:#EEEEEE;border-style:solid;border-width:2px 0 0 2px;}table.llc {width:98%; margin:0 auto; align:center; margin-bottom:1em; font-size:12px;}table.llc thead tr th {text-decoration:none; background:#D9E1EA; padding:0em; font-weight:bold;text-align:center; border:solid 1px #CCC;}table.llc tbody tr td {vertical-align:top;text-align:center;}table.llc tbody tr.odd {background:#FFF;}table.llc tbody tr.even {background:#EEF2F6;}table.llc tbody tr td table.llc tr td {border-width:0;}table.llc tbody tr td {padding:2px; border-width:0px 1px 1px 0px; border-style:solid; border-color:#DDD;}table.llc tbody tr td.hoverDescLink a {text-decoration:none; color:#000; cursor:help;}table.llc tbody tr td.hoverDescLink a:hover {background:#FFE2BF;}</style>");
		sb.append("<table class=\"llc\"><thead><tr><th>Condition</th><th>Current MMWR Week</th><th>MMWR YTD</th></tr></thead>");
		Iterator<Object> iter = results.listIterator();
		int count1 = 0; 
		int count2 = 0;
		if(iter.hasNext()) {
			sb.append("<tbody>");
			while (iter.hasNext()) {
				ChartElementDT dt = (ChartElementDT)iter.next();
				Tag row = new Tag("tr");
				Tag col = new Tag("td"); col.add(dt.getSection());
				Tag col1 = new Tag("td"); col1.add(dt.getHitCount());
				Tag col2 = new Tag("td"); col2.add(dt.getHitCount2());
				row.add(col);row.add(col1);row.add(col2);
				sb.append(row.toString());
				count1 = count1 + dt.getHitCount().intValue();
				count2 = count2 + dt.getHitCount2().intValue();			
			}
			Tag row = new Tag("tr");
			Tag col = new Tag("td","style=\"font-weight:bold;\""); col.add("TOTAL");
			Tag col1 = new Tag("td","style=\"font-weight:bold;\""); col1.add(count1);
			Tag col2 = new Tag("td","style=\"font-weight:bold;\""); col2.add(count2);
			row.add(col);row.add(col1);row.add(col2);
			sb.append(row.toString());
		} else {
			sb.append("<tbody style=\"height:190px;\"><tr><td/><td/><td/></tr>");
		}
		sb.append("</tbody></table>");
		return sb.toString();
    }	
	
	public static String createLineListing(String chartId, HttpServletRequest req, int xCord, int yCord) {
		
		//Check if the Chart is Secured ?
		HomePageForm form = req.getSession().getAttribute("homepageForm") == null ? new HomePageForm() : (HomePageForm) req.getSession().getAttribute("homepageForm");
		Map<Object,Object> cmMap = form.getChartMetadataMap();
		ChartReportMetadataDT dt = (ChartReportMetadataDT) cmMap.get(chartId);		
    	String secIndCd = dt.getSecured_ind_cd() == null ? "N" : dt.getSecured_ind_cd();
    	boolean sec = secIndCd.equalsIgnoreCase("Y") ? true : false; 
		String dataset = createDataset(chartId, req, sec);
		
		return dataset;
	}
    
}
