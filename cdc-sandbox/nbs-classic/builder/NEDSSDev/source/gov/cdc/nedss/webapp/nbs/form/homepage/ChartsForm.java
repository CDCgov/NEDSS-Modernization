package gov.cdc.nedss.webapp.nbs.form.homepage;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.webapp.nbs.action.homepage.charts.ChartConstants;
import gov.cdc.nedss.webapp.nbs.action.homepage.charts.LineListingHelper;
import gov.cdc.nedss.webapp.nbs.form.util.BaseForm;

/**
 * 
 * @author nmallela
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: Computer Sciences Corporation</p>
 * ChartsForm.java
 * Oct 20, 2009
 * @version
 */
public class ChartsForm extends BaseForm {
	
	private static ArrayList<Object> chartList = new ArrayList<Object> ();
	private static final int X_COORDINATE = 285;
	private static final int Y_COORDINATE = 250;
	
	//Eventually these values would be read from metadata
	private static final int X_COORDINATE_1 = 750;
	private static final int Y_COORDINATE_1 = 470;	
	private String html;

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}
	
	public String getLineListings(String chartId) {
		
		WebContext ctx = WebContextFactory.get();
		HttpServletRequest req = ctx.getHttpServletRequest();
		return LineListingHelper.createLineListing(chartId, req, X_COORDINATE_1, Y_COORDINATE_1);		
	}
	

	public static ArrayList<Object> getAvailableChartList() {
		if(chartList.size() == 0) {
			WebContext ctx = WebContextFactory.get();
			HttpServletRequest req = ctx.getHttpServletRequest();
			String sMethod = "getAvailableChartList";
			chartList = (ArrayList) processRequest(new String[0], sMethod, req.getSession());
		}
		return chartList;
	}	
	
	private static Object processRequest(Object[] oParams, String sMethod, HttpSession session) {

		MainSessionCommand msCommand = null;
		Object obj = null;

		try {
			String sBeanJndiName = JNDINames.NBS_CHART_EJB;
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);
			ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
			obj = arr.get(0);

			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return obj;	
		}	
	
	
	
}