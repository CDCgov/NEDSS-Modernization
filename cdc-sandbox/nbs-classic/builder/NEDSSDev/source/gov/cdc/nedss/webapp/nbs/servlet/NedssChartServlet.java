package gov.cdc.nedss.webapp.nbs.servlet;

import gov.cdc.nedss.systemservice.ejb.nbschartsejb.dt.ChartReportMetadataDT;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.action.homepage.charts.ChartConstants;
import gov.cdc.nedss.webapp.nbs.action.homepage.charts.LineChartHelper;
import gov.cdc.nedss.webapp.nbs.action.homepage.charts.StackedBarChartHelper;
import gov.cdc.nedss.webapp.nbs.form.homepage.HomePageForm;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.JFreeChart;

/**
 * 
 * @author Narendra Mallela
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: Computer Sciences Corporation</p>
 * NedssChartRendererServlet.java
 * Sep 15, 2009
 * @version 1.0
 */
public class NedssChartServlet extends HttpServlet {

	private static final int X_COORDINATE = 285;
	private static final int Y_COORDINATE = 250;
	
	//Eventually these values would be read from metadata
	private static final int X_COORDINATE_1 = 750;
	private static final int Y_COORDINATE_1 = 470;

	static final LogUtils logger = new LogUtils(NedssChartServlet.class.getName());
	private static PropertyUtil propertyUtil= PropertyUtil.getInstance();
	
	public void init() throws ServletException {

	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        response.setContentType("image/png");
        response.setHeader("Cache-Control","no-cache, no-store,must-revalidate, max-age=-1");
        response.setHeader("Pragma","no-cache, no-store"); response.setDateHeader ("Expires", -1);
        ServletOutputStream os = response.getOutputStream();
        try {
        	RenderedImage obj = getChart(request);
        	if(obj != null){
        		ImageIO.write(obj, "png", os);
        	}	
        } catch(IOException ioe) {
        	//This happens if someone access NavBar before Charts are loaded.. (Low Priority)
        	logger.error("ClientAbortException:  java.net.SocketException: socket write error: Connection reset by peer\t" + ioe.toString());        	
		} catch (Exception e) {
			logger.error("\n\n\n Exception in processRequest of NedssChartServlet : " + e.getCause().toString());
			e.printStackTrace();
		}
        response.flushBuffer();
        os.flush();
        os.close();
        
    }
	
	/**
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private RenderedImage getChart(HttpServletRequest request) throws Exception {
		
		HomePageForm form = request.getSession().getAttribute("homepageForm") == null ? new HomePageForm() : (HomePageForm) request.getSession().getAttribute("homepageForm");
		Map<Object,Object> cmMap = form.getChartMetadataMap();
		BufferedImage bi = null;
		boolean popupChart = request.getParameter("popup") == null ? false : true;
        String chartId = request.getParameter("charts") == null ? String.valueOf(form.getAttributeMap().get("charts")) : request.getParameter("charts");
       
        if(chartId==null || chartId.equals("null")) {
        	
        	JFreeChart chart = StackedBarChartHelper.createEmptyChart(request);
        	request.setAttribute("chartTitle", "Pick Report from Drop-Down to Display");
        	//ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
        	if(popupChart)
        		bi = chart.createBufferedImage(X_COORDINATE_1, Y_COORDINATE_1);
        	else
        		bi = chart.createBufferedImage(X_COORDINATE, Y_COORDINATE);
        	return bi;
        }
        
			ChartReportMetadataDT dt = (ChartReportMetadataDT) cmMap.get(chartId);
	        //TBD Load ChartObject from Metadata and pass it as param for Security, Add'l params etc        
	    	request.setAttribute("chartTitle", dt.getChart_report_desc_txt());
	
			if (chartId.equals(ChartConstants.C002) || chartId.equals(ChartConstants.C004)) {
	        	//JFreeChart chart = BarChartHelper.createChart(chartId, request);
	        	JFreeChart chart = StackedBarChartHelper.createChart(chartId, dt, request);
	        	//ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
	        	if(popupChart)
	        		bi = chart.createBufferedImage(X_COORDINATE_1, Y_COORDINATE_1);
	        	else
	        		bi = chart.createBufferedImage(X_COORDINATE, Y_COORDINATE);
	
	        } else if (chartId.equals(ChartConstants.C001)) {
	        	JFreeChart chart = LineChartHelper.createChart(chartId, dt, request);        	
	        	if(popupChart)
	        		bi = chart.createBufferedImage(X_COORDINATE_1, Y_COORDINATE_1);
	        	else
	        		bi = chart.createBufferedImage(X_COORDINATE, Y_COORDINATE);
	        	
	        } else {
	        	//States can render their own Images
				try {
					Object obj = loadImage(chartId, dt, popupChart, request);
					if(obj!= null){
						bi = (BufferedImage) obj;
					}	
					
				} catch (Exception e) {
					logger.error("\n\n\nError while rendering Chart For: " + chartId + "\t" + e.getCause().toString());
					e.printStackTrace();
				}
	        }	
	       
    	return bi;
	}
	
	@SuppressWarnings("unchecked")
	private Object loadImage(String chartId, ChartReportMetadataDT dt, boolean popupChart, HttpServletRequest req) throws Exception {
		
		req.setAttribute("DBTYPE", 	NEDSSConstants.SQL_SERVER_ID);
		
		Object obj = null;
		ClassLoader loader = ClassLoader.getSystemClassLoader();
		if(dt.getExternal_class_nm()!=null){
			Class daoClass = loader.loadClass(dt.getExternal_class_nm());
	
			Constructor constructor = daoClass.getConstructor(String.class);
			Object daoCls = (Object) constructor.newInstance("");
			
			Method[] gettingMethods = daoClass.getMethods();
			ArrayList objAr = new ArrayList();
			if(popupChart) {
				objAr.add(X_COORDINATE_1);objAr.add(Y_COORDINATE_1);
			} else {	
				objAr.add(X_COORDINATE);objAr.add(Y_COORDINATE);
			}
			//Add secured Indicator too
	    	String secIndCd = dt.getSecured_ind_cd() == null ? "N" : dt.getSecured_ind_cd();
	    	objAr.add(secIndCd);
			objAr.add(req);
		    Method mMeth = null;
		    for(int i = 0; i < gettingMethods.length; i++)
		    {
		        Method method = (Method)gettingMethods[i];
		        String methodName = method.getName();
		        if(methodName.equals(dt.getExternal_method_nm())) {
		        	mMeth = method;
		        	break;
		        }
			}
			obj = mMeth.invoke(daoCls, objAr.toArray());	
		}
		return obj;
		
	}
	
	
	
}
