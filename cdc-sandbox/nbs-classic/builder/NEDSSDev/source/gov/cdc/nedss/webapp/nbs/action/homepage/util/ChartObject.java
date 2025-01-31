package gov.cdc.nedss.webapp.nbs.action.homepage.util;

/**
 * 
 * @author Narendra Mallela
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: Computer Sciences Corporation</p>
 * ChartDT.java
 * Sep 12, 2009
 * @version
 */
public class ChartObject {

	private String chartId;
	private String chartNm;
	private String mapHtml;
	private String title;
	
	public String getChartNm() {
		return chartNm;
	}
	public void setChartNm(String chartNm) {
		this.chartNm = chartNm;
	}
	public String getMapHtml() {
		return mapHtml;
	}
	public void setMapHtml(String mapHtml) {
		this.mapHtml = mapHtml;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getChartId() {
		return chartId;
	}
	public void setChartId(String chartId) {
		this.chartId = chartId;
	}
	
}
