package gov.cdc.nedss.webapp.nbs.action.homepage.charts;

import gov.cdc.nedss.systemservice.ejb.nbschartsejb.dt.ChartElementDT;
import gov.cdc.nedss.webapp.nbs.action.homepage.util.ChartObject;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.DateTickUnitType;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.imagemap.StandardToolTipTagFragmentGenerator;
import org.jfree.chart.imagemap.StandardURLTagFragmentGenerator;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieToolTipGenerator;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.xy.StackedXYAreaRenderer;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYAreaRenderer;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.chart.urls.StandardCategoryURLGenerator;
import org.jfree.chart.urls.StandardPieURLGenerator;
import org.jfree.chart.urls.TimeSeriesURLGenerator;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.DefaultTableXYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleInsets;

/**
 * ChartRenderer renders Interactive Image Charts(ex: BarCharts, PieCharts etc) for NBS Home Page depending on the Chart Type 
 * @author Narendra Mallela
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: Computer Sciences Corporation</p>
 * ChartRenderer.java
 * Sep 15, 2009
 * @version 1.0
 */  
public class ChartRenderer {

	private static final String NO_DATA_CHART = "public_nodata.png";
	private static final String ERROR_CHART = "public_error.png";
	private static final int X_COORDINATE = 285;
	private static final int Y_COORDINATE = 250;
	private static final Logger logger = Logger.getLogger(ChartRenderer.class);
	private static Map<Object,Object> queueMap = new TreeMap<Object, Object>();
	private static ArrayList<Object> chartObjList = new ArrayList<Object> ();
	private static CachedDropDownValues cdv = new CachedDropDownValues();
	private static Map<Object,Object> conditionMap = new TreeMap<Object, Object>();
	
	/**
	 * generates appropriate Chart based on the Selection which is a list of Charts to be generated
	 * @param type
	 * @param session
	 * @param pw
	 * @return
	 */
	public static String generateChart(String key, HttpServletRequest request, PrintWriter pw) {

		HttpSession session = request.getSession(); 
		String name = "";
		try {
			ChartElementDataSet._clearChartCache();
			Map<Object, Object> charts = ChartElementDataSet.getChartMap();
			if(queueMap.size() == 0)
				queueMap = ChartElementDataSet._loadQueueMap();


			if(charts.get(key).equals(ChartConstants.C001)) {
				request.setAttribute("chartTitle",ChartConstants.C001_TITLE);
				Map<Object, Object> map = generatePieChart(key, X_COORDINATE, Y_COORDINATE, session, pw); 
				name = (String)map.get("filename");
			} else if(charts.get(key).equals(ChartConstants.C002)) {
				request.setAttribute("chartTitle",ChartConstants.C002_TITLE);			
				Map<Object, Object> map = generateXYChart(key, X_COORDINATE, Y_COORDINATE, session, pw, "LabReports");
				name = (String)map.get("filename");
			} else if(charts.get(key).equals(ChartConstants.C003)) {
				request.setAttribute("chartTitle",ChartConstants.C003_TITLE);	
				Map<Object, Object> map = generatePieChart(key, X_COORDINATE, Y_COORDINATE, session, pw); 
				name = (String)map.get("filename");
			} else if(charts.get(key).equals(ChartConstants.C004)) {
				request.setAttribute("chartTitle",ChartConstants.C004_TITLE);	
				Map<Object, Object> map = generatePieChart(key, X_COORDINATE, Y_COORDINATE, session, pw); 
				name = (String)map.get("filename");
			} else if(charts.get(key).equals(ChartConstants.C005)) {
				request.setAttribute("chartTitle",ChartConstants.C005_TITLE);	
				Map<Object, Object> map= generateBarChart(key, X_COORDINATE, Y_COORDINATE, session, pw);
				name = (String)map.get("filename");
			}
		} catch (Exception ex) {
			logger.error("Error in generateChart: "+ex.getMessage());
			ex.printStackTrace();
		}	
		return name;
	}
	
	private static Map<Object,Object> generatePieChart(String type, int width, int height, HttpSession session, PrintWriter pw) {
		boolean popupMode = false;
		if(width > X_COORDINATE)
			popupMode = true;
		
		Map<Object, Object> returnMap = new TreeMap<Object, Object>();
		String filename = null;
		try {
			//  Retrieve list of WebHits
			ChartElementDataSet whDataSet = new ChartElementDataSet();
			ArrayList<Object> list = whDataSet.getDataByChartType(type, session);

			//  Throw a custom NoDataException if there is no data
			if (list.size() == 0) {
				logger.error("No data has been found to generate Chart !!!");
				throw new NoDataException();
			}

			//  Create and populate a PieDataSet
			DefaultPieDataset data = new DefaultPieDataset();
			Iterator<Object> iter = list.listIterator();
			while (iter.hasNext()) {
				ChartElementDT wh = (ChartElementDT)iter.next();
				data.setValue(wh.getSection(), wh.getHitCount());
			}

			//  Create the chart object
			PiePlot plot = new PiePlot(data);
			plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0} = {1}"));
			plot.setInsets(new RectangleInsets(0, 5, 5, 5));
			String url = String.valueOf(queueMap.get(type));
			plot.setURLGenerator(new StandardPieURLGenerator(url, "section"));
            plot.setToolTipGenerator(new StandardPieToolTipGenerator("{0}={1}({2})"));
			JFreeChart chart = new JFreeChart("", JFreeChart.DEFAULT_TITLE_FONT, plot, popupMode);
			chart.setBackgroundPaint(java.awt.Color.white);

			//  Write the chart image to the temporary directory
			ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
			filename = ServletUtilities.saveChartAsPNG(chart, width, height, info, session);
			returnMap.put("filename", filename);
			//  Write the image map to the PrintWriter			
			ChartUtilities.writeImageMap(pw, filename, info, false);
			
			String mapHTML = ChartUtilities.getImageMap(filename, info, new StandardToolTipTagFragmentGenerator(), new StandardURLTagFragmentGenerator());
			mapHTML = _updateHtmlForInvs(type, mapHTML);
			returnMap.put("mapHTML", mapHTML);
			
			pw.flush();

		} catch (NoDataException e) {
			logger.error(e.toString());
			filename =NO_DATA_CHART;
		} catch (Exception e) {
			logger.error(e.toString());
			e.printStackTrace(System.out);
			filename = ERROR_CHART;
		}
		return returnMap;
	}
	

	private static Map<Object,Object> generateXYChart(String key, int width, int height, HttpSession session, PrintWriter pw, String toolTipTxt) {
		
		boolean popupMode = false;
		if(width > X_COORDINATE)
			popupMode = true;
		
		Map<Object, Object> returnMap = new TreeMap<Object, Object>();
		String filename = null;
		try {
			ChartElementDataSet whDataSet = new ChartElementDataSet();
			ArrayList<Object> list = whDataSet.getDataByChartType(key, session);
			if (list.size() == 0) {
				logger.error("No data has been found for chart Type: " + key);
				throw new NoDataException();
			}

			//  Create and populate an XYSeries Collection
			XYSeries dataSeries = new XYSeries(toolTipTxt);
			Iterator<Object> iter = list.listIterator();
			while (iter.hasNext()) {
				ChartElementDT wh = (ChartElementDT)iter.next();
				dataSeries.add(wh.getHitDate().getTime(),wh.getHitCount());
			}
			XYSeriesCollection  xyDataset = new XYSeriesCollection(dataSeries);

			//  Create tooltip and URL generators
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
			StandardXYToolTipGenerator ttg = new StandardXYToolTipGenerator(StandardXYToolTipGenerator.DEFAULT_TOOL_TIP_FORMAT, sdf, NumberFormat.getInstance());
			String url = String.valueOf(queueMap.get(key));
			TimeSeriesURLGenerator urlg = new TimeSeriesURLGenerator(sdf, url, "series", "hitDate");
		
			DateAxis dateAxis = new DateAxis("");
			dateAxis.setDateFormatOverride(sdf);
			DateTickUnit unit = new DateTickUnit(DateTickUnitType.DAY, 1);
			dateAxis.setTickUnit(unit);
			
			NumberAxis valueAxis = new NumberAxis("");
			valueAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());			
            StandardXYItemRenderer renderer = new StandardXYItemRenderer(StandardXYItemRenderer.LINES + StandardXYItemRenderer.SHAPES,ttg, urlg);
            renderer.setSeriesShapesFilled(0,Boolean.TRUE);
            
			XYPlot plot = new XYPlot(xyDataset, dateAxis, valueAxis, renderer);

			JFreeChart chart = new JFreeChart("", JFreeChart.DEFAULT_TITLE_FONT, plot, popupMode);
			chart.setBackgroundPaint(java.awt.Color.white);

			//  Write the chart image to the temporary directory
			ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
			filename = ServletUtilities.saveChartAsPNG(chart, width, height, info, session);
			returnMap.put("filename", filename);
			
			//  Write the image map to the PrintWriter
			ChartUtilities.writeImageMap(pw, filename, info, false);
			
			String mapHTML = ChartUtilities.getImageMap(filename, info, new StandardToolTipTagFragmentGenerator(), new StandardURLTagFragmentGenerator());
			returnMap.put("mapHTML", mapHTML);
			
			pw.flush();

		} catch (NoDataException e) {
			logger.error(e.toString());
			filename = NO_DATA_CHART;
		} catch (Exception e) {
			logger.error(e.toString());
			e.printStackTrace(System.out);
			filename = ERROR_CHART;
		}
		return returnMap;
	}

	private static Map<Object,Object> generateBarChart(String type, int width, int height, HttpSession session, PrintWriter pw) {
		
		boolean popupMode = false;
		if(width > X_COORDINATE)
			popupMode = true;

		Map<Object, Object> returnMap = new TreeMap<Object, Object>();
		String filename = null;
		
		try {
			//  Retrieve list of WebHits
			ChartElementDataSet dataSet = new ChartElementDataSet();
			
			ArrayList<Object> list = dataSet.getDataByChartType(type, session);

			//  Throw a custom NoDataException if there is no data
			if (list.size() == 0) {
				logger.error("No data has been found to generate Chart !!!");
				throw new NoDataException();
			}

		 	//  Create and populate a CategoryDataset
			Iterator<Object> iter = list.listIterator();
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			while (iter.hasNext()) {
				ChartElementDT wh = (ChartElementDT)iter.next();
                dataset.addValue(wh.getHitCount(), "Counts", wh.getSectionDesc());
			}

			//  Create the chart object
			CategoryAxis categoryAxis = new CategoryAxis("");
			ValueAxis valueAxis = new NumberAxis("");
			BarRenderer renderer = new BarRenderer();
			String url = String.valueOf(queueMap.get(type));
			renderer.setSeriesItemURLGenerator(0,new StandardCategoryURLGenerator(url ,"series","section"));			
            renderer.setSeriesToolTipGenerator(0, new StandardCategoryToolTipGenerator());

            Plot plot = new CategoryPlot(dataset, categoryAxis, valueAxis, renderer);
			JFreeChart chart = new JFreeChart("", JFreeChart.DEFAULT_TITLE_FONT, plot, popupMode);
			chart.setBackgroundPaint(java.awt.Color.white);

			//  Write the chart image to the temporary directory
			ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
			filename = ServletUtilities.saveChartAsPNG(chart, width, height, info, session);
			returnMap.put("filename", filename);
			//  Write the image map to the PrintWriter
			ChartUtilities.writeImageMap(pw, filename, info, false);
			String mapHTML = ChartUtilities.getImageMap(filename, info, new StandardToolTipTagFragmentGenerator(), new StandardURLTagFragmentGenerator());
			returnMap.put("mapHTML", mapHTML);
			pw.flush();

		} catch (NoDataException e) {
			logger.error(e.toString());
			filename = NO_DATA_CHART;
		} catch (Exception e) {
			logger.error("Exception - " + e.toString());
			e.printStackTrace(System.out);
			filename = ERROR_CHART;
		}
		return returnMap;
	}

	
	
	private static String generateXYAreaChart(String type, int width, int height, HttpSession session, PrintWriter pw) {
        
		boolean popupMode = false;
		if(width > X_COORDINATE)
			popupMode = true;

		String filename = null;
        try {
            //  Retrieve list of WebHits for each section and populate a TableXYDataset
        	ChartElementDataSet whDataSet = new ChartElementDataSet();
            ArrayList<Object> sections = whDataSet.getSections();
           Iterator<Object>  sectionIter = sections.iterator();
            DefaultTableXYDataset dataset = new DefaultTableXYDataset();
            while (sectionIter.hasNext()) {
                String section = (String)sectionIter.next();
                ArrayList<Object> list = whDataSet.getDataByChartType(type, session);
                XYSeries dataSeries = new XYSeries(section, true, false);
               Iterator<Object>  webHitIter = list.iterator();
                while (webHitIter.hasNext()) {
                	ChartElementDT webHit = (ChartElementDT)webHitIter.next();
                    dataSeries.add(webHit.getHitDate().getTime(), webHit.getHitCount());
                }
                dataset.addSeries(dataSeries);
            }

            //  Throw a custom NoDataException if there is no data
            if (dataset.getItemCount() == 0) {
            	logger.error("No data has been found to generate Chart !!!");
                throw new NoDataException();
            }

            //  Create tooltip and URL generators
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.UK);
            StandardXYToolTipGenerator ttg = new StandardXYToolTipGenerator(
                    StandardXYToolTipGenerator.DEFAULT_TOOL_TIP_FORMAT,
                    sdf, NumberFormat.getInstance());
            TimeSeriesURLGenerator urlg = new TimeSeriesURLGenerator(
                    sdf, "bar_chart.jsp", "series", "hitDate");

            //  Create the X-Axis
            DateAxis xAxis = new DateAxis(null);
            xAxis.setLowerMargin(0.0);
            xAxis.setUpperMargin(0.0);

            //  Create the X-Axis
            NumberAxis yAxis = new NumberAxis(null);
            yAxis.setAutoRangeIncludesZero(true);

            //  Create the renderer
            StackedXYAreaRenderer renderer =
                    new StackedXYAreaRenderer(XYAreaRenderer.AREA_AND_SHAPES, ttg, urlg);
            renderer.setSeriesPaint(0, new Color(255, 255, 180));
            renderer.setSeriesPaint(1, new Color(206, 230, 255));
            renderer.setSeriesPaint(2, new Color(255, 230, 230));
            renderer.setSeriesPaint(3, new Color(206, 255, 206));
            renderer.setShapePaint(Color.gray);
            renderer.setShapeStroke(new BasicStroke(0.5f));
            renderer.setSeriesShape(0,new Ellipse2D.Double(-3, -3, 6, 6));
            renderer.setOutline(true);

            //  Create the plot
            XYPlot plot = new XYPlot(dataset, xAxis, yAxis, renderer);
            plot.setForegroundAlpha(0.65f);

            //  Reconfigure Y-Axis so the auto-range knows that the data is stacked
            yAxis.configure();

            //  Create the chart
            JFreeChart chart = new JFreeChart(null, JFreeChart.DEFAULT_TITLE_FONT, plot, popupMode);
            chart.setBackgroundPaint(java.awt.Color.white);

            //  Write the chart image to the temporary directory
            ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
           	filename = ServletUtilities.saveChartAsPNG(chart, width, height, info, session);

            //  Write the image map to the PrintWriter
            ChartUtilities.writeImageMap(pw, filename, info, false);
            pw.flush();


        } catch (NoDataException e) {
            System.out.println(e.toString());
            filename = NO_DATA_CHART;
        } catch (Exception e) {
            System.out.println("Exception - " + e.toString());
            e.printStackTrace(System.out);
            filename = ERROR_CHART;
        }
        return filename;
    }
	/**
	 * 
	 * @param type
	 * @param width
	 * @param height
	 * @param session
	 * @param pw
	 * @return
	 */
	private static Map<Object,Object> generateStackedBarChart(String type, int width, int height, HttpSession session, PrintWriter pw, String toolTipTxt) {
		
		boolean popupMode = false;
		if(width > X_COORDINATE)
			popupMode = true;
		
		Map<Object, Object> returnMap = new TreeMap<Object, Object>();
		String filename = null;
		try {
			//  Retrieve list of WebHits
			ChartElementDataSet whDataSet = new ChartElementDataSet();
			ArrayList<Object> list = whDataSet.getDataByChartType(type, session);

			//  Throw a custom NoDataException if there is no data
			if (list.size() == 0) {
				logger.error("No data has been found to generate Chart !!!");
				throw new NoDataException();
			}

			//  Create and populate a PieDataSet
			DefaultPieDataset data = new DefaultPieDataset();
			Iterator<Object> iter = list.listIterator();
			while (iter.hasNext()) {
				ChartElementDT wh = (ChartElementDT)iter.next();
				data.setValue(wh.getSection(), wh.getHitCount());
			}

			//  Create the chart object
			PiePlot plot = new PiePlot(data);
			plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0} = {1}"));
			plot.setInsets(new RectangleInsets(0, 5, 5, 5));
			String url = String.valueOf(queueMap.get(type));
			plot.setURLGenerator(new StandardPieURLGenerator(url, "section"));
            plot.setToolTipGenerator(new StandardPieToolTipGenerator("{0}={1}({2})"));
			JFreeChart chart = new JFreeChart("", JFreeChart.DEFAULT_TITLE_FONT, plot, popupMode);
			chart.setBackgroundPaint(java.awt.Color.white);

			//  Write the chart image to the temporary directory
			ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
			filename = ServletUtilities.saveChartAsPNG(chart, width, height, info, session);
			returnMap.put("filename", filename);
			//  Write the image map to the PrintWriter			
			ChartUtilities.writeImageMap(pw, filename, info, false);
			
			String mapHTML = ChartUtilities.getImageMap(filename, info, new StandardToolTipTagFragmentGenerator(), new StandardURLTagFragmentGenerator());
			mapHTML = _updateHtmlForInvs(type, mapHTML);
			returnMap.put("mapHTML", mapHTML);
			
			pw.flush();

		} catch (NoDataException e) {
			logger.error(e.toString());
			filename =NO_DATA_CHART;
		} catch (Exception e) {
			logger.error(e.toString());
			e.printStackTrace(System.out);
			filename = ERROR_CHART;
		}
		return returnMap;
	}
	
	
	/**
	 * Generates a Chart based on DWR Call
	 * @param key
	 * @param size
	 */
	public void generateJChart(String key, String wdth, String hght) {
		
		ChartElementDataSet._clearChartCache();
		chartObjList.clear();		
		ChartObject obj = new ChartObject();
		obj.setChartId(key);
		Map<Object, Object> map = null;
		PrintWriter pw = null;
		WebContext ctx = WebContextFactory.get();
		HttpServletRequest request = ctx.getHttpServletRequest();
		HttpSession session = request.getSession(); 
		try {
			int width = Integer.valueOf(wdth).intValue();
			int height = Integer.valueOf(hght).intValue();
			
			pw = ctx.getHttpServletResponse().getWriter();
			Map<Object, Object> charts = ChartElementDataSet.getChartMap();
			if(charts.get(key).equals(ChartConstants.C001)) {
				obj.setTitle(ChartConstants.C001_TITLE);
				map = generatePieChart(key, width, height, session, pw); 
			} else if(charts.get(key).equals(ChartConstants.C002)) {
				obj.setTitle(ChartConstants.C002_TITLE);
				map = generateStackedBarChart(key, width, height, session, pw, "LabReports");
			} else if(charts.get(key).equals(ChartConstants.C003)) {
				obj.setTitle(ChartConstants.C003_TITLE);
				map = generatePieChart(key, width, height, session, pw); 
			} else if(charts.get(key).equals(ChartConstants.C004)) {
				obj.setTitle(ChartConstants.C004_TITLE);
				map = generatePieChart(key, width, height, session, pw); 
			} else if(charts.get(key).equals(ChartConstants.C005)) {
				obj.setTitle(ChartConstants.C005_TITLE);
				map = generateBarChart(key, width, height, session, pw);
			}
			obj.setChartNm(String.valueOf(map.get("filename")));
			obj.setMapHtml(String.valueOf(map.get("mapHTML")));		
			chartObjList.add(obj);
		} catch (IOException e) {
			logger.error("Error while generating DWR Chart for chart ID: " + key + ", details: " + e.getMessage());
			e.printStackTrace();			
		}
	}
	
	/**
	 * Loads the current ChartObject
	 * @return ChartObject
	 */
	public ChartObject loadJChart() {
		if(chartObjList.size() > 0)
			return (ChartObject)chartObjList.get(0);
		else
			return new ChartObject();
	}
	
	private static String _updateHtmlForInvs(String type, String mapHTML) {
		if(type != null && (type.equalsIgnoreCase(ChartConstants.C001) ||type.equalsIgnoreCase(ChartConstants.C003))) {
			if(conditionMap.size() == 0)
				conditionMap = cdv.getConditionCodes();
			Iterator<Object> iter = conditionMap.keySet().iterator();
			while(iter.hasNext()) {
				String key = (String)iter.next();
				String st = "title=\"" + key + "=";
				if(mapHTML.indexOf(st) != -1) {
					String nSt = "title=\" " + conditionMap.get(key) + "=";
					mapHTML = mapHTML.replaceAll(st, nSt);
				}
			}		
		}
		
		return mapHTML;
	}
	

}