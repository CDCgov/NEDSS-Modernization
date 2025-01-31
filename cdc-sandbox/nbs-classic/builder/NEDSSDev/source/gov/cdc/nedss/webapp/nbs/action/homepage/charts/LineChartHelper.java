package gov.cdc.nedss.webapp.nbs.action.homepage.charts;

import gov.cdc.nedss.systemservice.ejb.nbschartsejb.dt.ChartElementDT;
import gov.cdc.nedss.systemservice.ejb.nbschartsejb.dt.ChartReportMetadataDT;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.JNDINames;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.CategoryToolTipGenerator;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.util.ShapeUtilities;

public class LineChartHelper extends ApplicationFrame {
	private static final long serialVersionUID = 1L;

	private static final Color c = new Color(0x0066FF);
    public LineChartHelper(String title) {
		super(title);
	}

    @SuppressWarnings("unchecked")
	private static CategoryDataset createDataset(HttpServletRequest req, ChartReportMetadataDT dt, boolean secured)
    {
		NBSSecurityObj secObj = (NBSSecurityObj)req.getSession().getAttribute("NBSSecurityObject");
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yy");
		ArrayList<Object> results = new ArrayList<Object> ();
		Object[] searchParams = new Object[] {secObj, secured, dt};
		Object[] oParams = new Object[] { JNDINames.NBS_CHART_DAO_CLASS, "selectOpenInvsPastXDays", searchParams};
		try {
			results = (ArrayList<Object> ) ChartElementDataSet.processRequest(oParams, req.getSession());
		} catch (Exception e) {
			e.printStackTrace();
		}
		Iterator<Object> iter = results.listIterator();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		while (iter.hasNext()) {
			ChartElementDT wh = (ChartElementDT)iter.next();
            dataset.addValue(wh.getHitCount(), "Counts", formatter.format(wh.getHitDate()));
		}
        return dataset;
    }	
	
    public static JFreeChart createChart(String chartId, ChartReportMetadataDT dt,HttpServletRequest req) {
    	JFreeChart jfreechart = null;
    	try {
    		String secIndCd = dt.getSecured_ind_cd() == null ? "N" : dt.getSecured_ind_cd();
    		boolean sec = secIndCd.equalsIgnoreCase("Y") ? true : false; 
    		CategoryDataset categorydataset = createDataset(req, dt, sec);
    		String xAxisTitle = dt.getX_axis_title();
    		String yAxisTitle = dt.getY_axis_title();
    		jfreechart = ChartFactory.createLineChart("", xAxisTitle, yAxisTitle, categorydataset, PlotOrientation.VERTICAL, false, true, false);
    		CategoryPlot categoryplot = (CategoryPlot)jfreechart.getPlot();
    		categoryplot.setBackgroundPaint(Color.WHITE);
    		categoryplot.setRangeGridlinePaint(Color.BLACK);

    		CategoryItemRenderer itemRenderer =  categoryplot.getRenderer();
    		CategoryToolTipGenerator toolTip = new StandardCategoryToolTipGenerator("{2}", java.text.NumberFormat.getInstance());
    		itemRenderer.setBaseToolTipGenerator(toolTip);

    		//xAxis
    		CategoryAxis domainAxis = (CategoryAxis)categoryplot.getDomainAxis();
    		domainAxis.setLabelFont(new Font("arial,helvetica,clean,sans-serif", Font.BOLD, 13));

    		//yAxis
    		NumberAxis numberaxis = (NumberAxis)categoryplot.getRangeAxis();
    		numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
    		numberaxis.setLabelFont(new Font("arial,helvetica,clean,sans-serif", Font.BOLD, 13));
    		//sets an upperMargin by 20% (To show the count for data points on the top line)
    		numberaxis.setUpperMargin(0.20);

    		CategoryAxis categoryaxis = categoryplot.getDomainAxis();
    		categoryaxis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);             

    		LineAndShapeRenderer lineandshaperenderer = (LineAndShapeRenderer)categoryplot.getRenderer();
    		lineandshaperenderer.setSeriesPaint(0, c);        
    		lineandshaperenderer.setSeriesShape(0, ShapeUtilities.createDiamond(5F));
    		lineandshaperenderer.setBaseFillPaint(c);
    		lineandshaperenderer.setBaseShapesVisible(true);
    		lineandshaperenderer.setBaseItemLabelsVisible(true);
    		lineandshaperenderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());        
    		lineandshaperenderer.setDrawOutlines(true);
    		lineandshaperenderer.setUseFillPaint(true);
    		lineandshaperenderer.setSeriesStroke(0, new BasicStroke(3F));
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}	
        return jfreechart;
    }



}
