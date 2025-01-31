package gov.cdc.nedss.webapp.nbs.action.homepage.charts;

import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.PropertyUtil;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

/**
 * 
 * @author nmallela
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: Computer Sciences Corporation</p>
 * ChartElementDataSet.java
 * Sep 17, 2009
 * @version
 */
public class ChartElementDataSet {
	
	protected ArrayList<Object> data = new ArrayList<Object> ();
	private static Map<Object,Object> chartMap = new TreeMap<Object, Object>();
	private static ArrayList<Object> chartList = new ArrayList<Object> ();
	private static final Logger logger = Logger.getLogger(ChartElementDataSet.class);
	private static PropertyUtil propertyUtil = PropertyUtil.getInstance();

	public ArrayList<Object> getDataByChartType(String type, HttpSession session) throws Exception {
		
		NBSSecurityObj secObj = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");
		
		ArrayList<Object> results = new ArrayList<Object> ();
		Object[] searchParams = new Object[] {secObj};
		Object[] oParams = null;
		//If openInvestigations, retrieve from DAO
		if(type.equalsIgnoreCase(ChartConstants.C001)) {
			oParams = new Object[] { JNDINames.NBS_CHART_DAO_CLASS, "selectOpenInvsPast7Days", searchParams};
		} else if(type.equalsIgnoreCase(ChartConstants.C002)) {
			oParams = new Object[] { JNDINames.NBS_CHART_DAO_CLASS, "selectLabsPast7Days", searchParams};
		} else if(type.equalsIgnoreCase(ChartConstants.C003)) {
			oParams = new Object[] { JNDINames.NBS_CHART_DAO_CLASS, "selectOpenInvs", searchParams};
		} else if(type.equalsIgnoreCase(ChartConstants.C004)) {
			oParams = new Object[] { JNDINames.NBS_CHART_DAO_CLASS, "selectLabsByRepFacility", searchParams};
		} else if(type.equalsIgnoreCase(ChartConstants.C005)) {
			//return dao.selectLabsByRepFacility(secObj);
		}
		results = (ArrayList<Object> ) processRequest(oParams, session);
		return results;
	}

	public static void _clearChartCache() {
	    ExtensionFilter filter = new ChartElementDataSet().new ExtensionFilter(".png");
	    String directory = System.getProperty("java.io.tmpdir");
	    File dir = new File(directory);
	    String[] list = dir.list(filter);
	    File file;
	    if (list.length == 0) return;
	    for (int i = 0; i < list.length; i++) {
	      file = new File(directory, list[i]);file.delete();
	    }
	   }
	

	public static ArrayList<Object> getAvailableChartList() {
		if(chartList.size() == 0) {
			DropDownCodeDT dt1 = new DropDownCodeDT();DropDownCodeDT dt2 = new DropDownCodeDT();DropDownCodeDT dt3 = new DropDownCodeDT();DropDownCodeDT dt4 = new DropDownCodeDT();
			dt1.setKey(ChartConstants.C001); dt1.setValue("LabReports created in Last 7 Days");chartList.add(dt1);
			dt2.setKey(ChartConstants.C002); dt2.setValue("Cases created in Last 7 Days");chartList.add(dt2);
			dt3.setKey(ChartConstants.C003); dt3.setValue("Case Counts MMWR Week and Year");chartList.add(dt3);
			dt4.setKey(ChartConstants.C004); dt4.setValue("Case Assignments Last 14 Days");chartList.add(dt4);
		}
		return chartList;
	}

	public static Map<Object,Object> _loadQueueMap() {
		Map<Object, Object> map = new TreeMap<Object, Object>();
		map.put(ChartConstants.C001,"/nbs/LoadNavbar.do?ContextAction=GlobalInvestigations&initLoad=true&chartId=C001");
		map.put(ChartConstants.C002,"/nbs/MyTaskList1.do?ContextAction=Review&initLoad=true&labReportsCount=0&chartId=C002");
		map.put(ChartConstants.C003,"/nbs/LoadNavbar.do?ContextAction=GlobalInvestigations&initLoad=true&chartId=C003");
		map.put(ChartConstants.C004,"/nbs/MyTaskList1.do?ContextAction=Review&initLoad=true&labReportsCount=0&chartId=C004");
		map.put(ChartConstants.C005,"/nbs/LoadNavbar.do?ContextAction=GlobalInvestigations&initLoad=true&chartId=C005");
		return map;
	}
	
	public static Map<Object,Object> getChartMap() {
		if(chartMap.size() == 0) {
			Iterator<Object> iter = chartList.iterator();
			while(iter.hasNext()) {
				DropDownCodeDT dt = (DropDownCodeDT) iter.next();
				chartMap.put(dt.getKey(), dt.getKey());
			}
		}
		return chartMap;
	}

    public ArrayList<Object> getSections() {
        ArrayList<Object> list = new ArrayList<Object> ();
        return list;
    }
    
    class ExtensionFilter implements FilenameFilter {
    	  private String extension;
    	  public ExtensionFilter( String extension ) {
    	    this.extension = extension;             
    	  }
    	  
    	  public boolean accept(File dir, String name) {
    	    return (name.endsWith(extension));
    	  }
    }
    
    
	public static Object processRequest(Object[] oParams, HttpSession session) throws Exception {

		MainSessionCommand msCommand = null;
		Object obj = null;

		try {
			String sBeanJndiName = JNDINames.NBS_CHART_EJB;
			String sMethod = "processNBSChartRequest";
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);
			ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
			obj = arr.get(0);

			} catch (Exception ex) {
				logger.error("getProxy: ", ex);	
				throw new Exception(ex);
			}
			return obj;	
		}    
    
    
    

}