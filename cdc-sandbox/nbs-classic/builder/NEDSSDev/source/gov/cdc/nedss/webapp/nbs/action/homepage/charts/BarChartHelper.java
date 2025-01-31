package gov.cdc.nedss.webapp.nbs.action.homepage.charts;

import gov.cdc.nedss.systemservice.ejb.nbschartsejb.dt.ChartElementDT;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.JNDINames;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;

public class BarChartHelper extends ApplicationFrame {

	private static final long serialVersionUID = 1L;

	
    public BarChartHelper(String title) {
		super(title);
	}

	private static CategoryDataset createDataset(HttpServletRequest req)
    {
		NBSSecurityObj secObj = (NBSSecurityObj)req.getSession().getAttribute("NBSSecurityObject");
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yy");
		ArrayList<Object> results = new ArrayList<Object> ();
		Object[] searchParams = new Object[] {secObj};
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

	public static JFreeChart createChart(String chartId, HttpServletRequest req)
	{
		JFreeChart jfreechart = null;
		try {
			CategoryDataset categorydataset = createDataset(req);
			jfreechart = ChartFactory.createBarChart("","Date", "# of Investigations", categorydataset, PlotOrientation.VERTICAL, false, true, false);
			CategoryPlot categoryplot = (CategoryPlot)jfreechart.getPlot();
			categoryplot.setDomainGridlinesVisible(true);
			categoryplot.setRangeCrosshairVisible(true);
			NumberAxis numberaxis = (NumberAxis)categoryplot.getRangeAxis();
			numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
			CategoryAxis categoryaxis = categoryplot.getDomainAxis();
			categoryaxis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);
		} catch (Exception ex) {
			System.out.println("Error on BarChartHelper.createChart: "+ex.getMessage());
			ex.printStackTrace();
		}
		return jfreechart;
	}


}
