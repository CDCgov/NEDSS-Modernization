package gov.cdc.nedss.webapp.nbs.form.homepage;

import gov.cdc.nedss.entity.person.vo.PatientSearchVO;
import gov.cdc.nedss.webapp.nbs.form.util.BaseForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class HomePageForm extends BaseForm {

	private static final long serialVersionUID = 1L;
	Map<Object,Object> searchMap = new HashMap<Object,Object>();
	private String actionMode;
	private String returnToLink;
	private Map<Object,Object> attributeMap = new HashMap<Object,Object>();
	private Collection<Object>  reportCollection  = null;
	private Collection<Object>  queueCollection  = null;
	private Collection<Object>  qccPublic  = null;
	private Collection<Object>  qccPrivate  = null;
	
	private Collection<Object>  feedsCollection  = null;
	private PatientSearchVO patientSearchVO = new PatientSearchVO();
	private ArrayList<Object> chartList = new ArrayList<Object> ();
	private Map<Object,Object> chartMetadataMap = new TreeMap<Object,Object>();
	
	public Map<Object,Object> getSearchMap() {
		return searchMap;
	}
	public void setSearchMap(Map<Object,Object> searchMap) {
		this.searchMap = searchMap;
	}
	public String getActionMode() {
		return actionMode;
	}
	public void setActionMode(String actionMode) {
		this.actionMode = actionMode;
	}
	public String getReturnToLink() {
		return returnToLink;
	}
	public void setReturnToLink(String returnToLink) {
		this.returnToLink = returnToLink;
	}
	public Map<Object,Object> getAttributeMap() {
		return attributeMap;
	}
	public void setAttributeMap(Map<Object,Object> attributeMap) {
		this.attributeMap = attributeMap;
	}
	
	public void clearSelections() {
		setSearchMap(new HashMap<Object,Object>());
		setAttributeMap(new HashMap<Object,Object>());
		setActionMode(null);
		setPatientSearchVO(new PatientSearchVO());
		setChartList(new ArrayList<Object>());
		setChartMetadataMap(new TreeMap<Object,Object>());
	}

	public Collection<Object>  getReportCollection() {
		return reportCollection;
	}
	public void setReportCollection(Collection<Object> reportCollection) {
		this.reportCollection  = reportCollection;
	}
	public Collection<Object>  getQueueCollection() {
		return queueCollection;
	}
	public void setQueueCollection(Collection<Object> queueCollection) {
		this.queueCollection  = queueCollection;
	}
	
	public Collection<Object>  getFeedsCollection() {
		return feedsCollection;
	}
	public void setFeedsCollection(Collection<Object> feedsCollection) {
		this.feedsCollection  = feedsCollection;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	public PatientSearchVO getPatientSearchVO() {
		return patientSearchVO;
	}
	public void setPatientSearchVO(PatientSearchVO patientSearchVO) {
		this.patientSearchVO = patientSearchVO;
	}	
	
	public Object getCodedValue(String key) {
		return CachedDropDowns.getCodedValueForType(key);
	}
	public ArrayList<Object> getChartList() {
		return chartList;
	}
	public void setChartList(ArrayList<Object> chartList) {
		this.chartList = chartList;
	}
	public Map<Object, Object> getChartMetadataMap() {
		return chartMetadataMap;
	}
	public void setChartMetadataMap(Map<Object, Object> chartMetadataMap) {
		this.chartMetadataMap = chartMetadataMap;
	}
	public Collection<Object> getQueueCollectionCustomPublic() {
		return qccPublic;
	}
	public Collection<Object> getQueueCollectionCustomPrivate() {
		return qccPrivate;
	}
	public void setQueueCollectionCustomPublic(
			Collection<Object> queueCollectionCustomPublic) {
		this.qccPublic = queueCollectionCustomPublic;
	}
	public void setQueueCollectionCustomPrivate(
			Collection<Object> queueCollectionCustomPrivate) {
		this.qccPrivate = queueCollectionCustomPrivate;
	}
    
}
