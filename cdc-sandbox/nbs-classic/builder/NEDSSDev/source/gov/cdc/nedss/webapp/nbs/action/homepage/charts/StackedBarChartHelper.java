package gov.cdc.nedss.webapp.nbs.action.homepage.charts;

import gov.cdc.nedss.systemservice.ejb.nbschartsejb.dt.ChartElementDT;
import gov.cdc.nedss.systemservice.ejb.nbschartsejb.dt.ChartReportMetadataDT;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.JNDINames;

import java.awt.Color;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;

/**
 * 
 * @author nmallela
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: Computer Sciences Corporation</p>
 * StackedBarChartHelper.java
 * Sep 29, 2009
 * @version
 */
public class StackedBarChartHelper extends ApplicationFrame {


	
    public StackedBarChartHelper(String s)
    {
        super(s);
    }


	@SuppressWarnings("unchecked")
	private static CategoryDataset createDataset(String chartId, ChartReportMetadataDT dt, HttpServletRequest req, boolean secured)
    {
		NBSSecurityObj secObj = (NBSSecurityObj)req.getSession().getAttribute("NBSSecurityObject");
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yy");
		ArrayList<Object> results = new ArrayList<Object> ();
		Object[] searchParams = new Object[] {secObj, secured, dt};
		Object[] oParams = null;
		if(chartId.equalsIgnoreCase(ChartConstants.C002))
			oParams = new Object[] { JNDINames.NBS_CHART_DAO_CLASS, "selectLabsPastXDays", searchParams};
		else if(chartId.equalsIgnoreCase(ChartConstants.C004))
			oParams = new Object[] { JNDINames.NBS_CHART_DAO_CLASS, "selectInvAssignPastXDays", searchParams};
		
		try {
			results = (ArrayList<Object> ) ChartElementDataSet.processRequest(oParams, req.getSession());
		} catch (Exception e) {
			e.printStackTrace();
		}
		Iterator iter = results.listIterator();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		while (iter.hasNext()) {
			ChartElementDT wh = (ChartElementDT)iter.next();
			if(chartId.equalsIgnoreCase(ChartConstants.C002))
				dataset.addValue(wh.getHitCount(), wh.getSection(), formatter.format(wh.getHitDate()));
			else if(chartId.equalsIgnoreCase(ChartConstants.C004))
				dataset.addValue(wh.getHitCount(), wh.getSection(), wh.getXAxis());
		}
        return dataset;
    }
    /**
     * 
     * @param req
     * @return
     */
    public static JFreeChart createChart(String chartId, ChartReportMetadataDT dt, HttpServletRequest req)
    {
    	JFreeChart jfreechart = null;
    	try {
    		String secIndCd = dt.getSecured_ind_cd() == null ? "N" : dt.getSecured_ind_cd();
    		boolean sec = secIndCd.equalsIgnoreCase("Y") ? true : false;
    		CategoryDataset categorydataset = createDataset(chartId,dt, req, sec);
    		String xAxisTitle = dt.getX_axis_title();
    		String yAxisTitle = dt.getY_axis_title();
    		ChartFactory.setChartTheme(StandardChartTheme.createLegacyTheme());
    		StackedBarRenderer.setDefaultShadowsVisible(false);
    		jfreechart = ChartFactory.createStackedBarChart("", xAxisTitle, yAxisTitle, categorydataset, PlotOrientation.VERTICAL, true, true, false);

    		CategoryPlot categoryplot = (CategoryPlot)jfreechart.getPlot();
    		categoryplot.setBackgroundPaint(Color.WHITE);
    		categoryplot.setRangeGridlinePaint(Color.BLACK);        

    		StackedBarRenderer stackedbarrenderer = (StackedBarRenderer)categoryplot.getRenderer();
    		stackedbarrenderer.setDrawBarOutline(false);
    		stackedbarrenderer.setBaseItemLabelsVisible(true);
    		stackedbarrenderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
    		//xAxis
    		CategoryAxis domainAxis = (CategoryAxis)categoryplot.getDomainAxis();
    		domainAxis.setLabelFont(new Font("arial,helvetica,clean,sans-serif", Font.BOLD, 13));

    		//yAxis

    		NumberAxis numberaxis = (NumberAxis)categoryplot.getRangeAxis();
    		numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
    		numberaxis.setLabelFont(new Font("arial,helvetica,clean,sans-serif", Font.BOLD, 13));

    		CategoryAxis categoryaxis = categoryplot.getDomainAxis();
    		categoryaxis.setCategoryLabelPositions(CategoryLabelPositions.UP_90); 
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}	
        return jfreechart;
    }
    
    public static JFreeChart createEmptyChart(HttpServletRequest req)
    {
    	JFreeChart jfreechart = null;
    	try {
    		CategoryDataset categorydataset = new DefaultCategoryDataset();
    		ChartFactory.setChartTheme(StandardChartTheme.createLegacyTheme());
    		StackedBarRenderer.setDefaultShadowsVisible(false);
    		jfreechart = ChartFactory.createStackedBarChart("", "", "", categorydataset, PlotOrientation.VERTICAL, true, true, false);

    		CategoryPlot categoryplot = (CategoryPlot)jfreechart.getPlot();
    		categoryplot.setBackgroundPaint(Color.WHITE);
    		categoryplot.setRangeGridlinePaint(Color.BLACK);        

    		StackedBarRenderer stackedbarrenderer = (StackedBarRenderer)categoryplot.getRenderer();
    		stackedbarrenderer.setDrawBarOutline(false);
    		stackedbarrenderer.setBaseItemLabelsVisible(true);
    		stackedbarrenderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
    		//xAxis
    		CategoryAxis domainAxis = (CategoryAxis)categoryplot.getDomainAxis();
    		domainAxis.setLabelFont(new Font("arial,helvetica,clean,sans-serif", Font.BOLD, 13));

    		//yAxis

    		NumberAxis numberaxis = (NumberAxis)categoryplot.getRangeAxis();
    		numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
    		numberaxis.setLabelFont(new Font("arial,helvetica,clean,sans-serif", Font.BOLD, 13));

    		CategoryAxis categoryaxis = categoryplot.getDomainAxis();
    		categoryaxis.setCategoryLabelPositions(CategoryLabelPositions.UP_90); 
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}	
        return jfreechart;
    }

}
