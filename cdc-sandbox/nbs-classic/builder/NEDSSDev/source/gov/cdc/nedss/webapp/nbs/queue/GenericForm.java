package gov.cdc.nedss.webapp.nbs.queue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;



import gov.cdc.nedss.webapp.nbs.action.myinvestigation.ProgramAreaUtil;
import gov.cdc.nedss.webapp.nbs.action.observation.review.util.ObservationReviewQueueUtil;
import gov.cdc.nedss.webapp.nbs.action.util.QueueUtil;
import gov.cdc.nedss.webapp.nbs.form.util.BaseForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.queue.QueueDT;

public class GenericForm extends BaseForm {
	
	GenericQueueUtil genericQueueUtil = new GenericQueueUtil();
	
	Map<Object,Object> searchCriteriaArrayMap = new HashMap<Object,Object>();
	
	private ArrayList<Object> column1List = new ArrayList<Object> ();
	private ArrayList<Object> column2List = new ArrayList<Object> ();
	private ArrayList<Object> column3List = new ArrayList<Object> ();
	private ArrayList<Object> column4List = new ArrayList<Object> (); 
	private ArrayList<Object> column5List = new ArrayList<Object> (); 
	private ArrayList<Object> column6List = new ArrayList<Object> ();
	private ArrayList<Object> column7List = new ArrayList<Object> ();
	private ArrayList<Object> column8List = new ArrayList<Object> ();
	private ArrayList<Object> column9List = new ArrayList<Object> (); 
	private ArrayList<Object> column10List = new ArrayList<Object> (); 
	private ArrayList<ArrayList<Object>> columnsList = new ArrayList<ArrayList<Object>> (); 
	
	private Collection<Object>  elementColl;
	
	private QueueDT queueDT;
	/*
	public void initializeDropDowns(Collection<Object> observationColls, String className, int index) {
		
		
		columnsList.add(column1List);
		columnsList.add(column2List);
		columnsList.add(column3List);
		columnsList.add(column4List);
		columnsList.add(column5List);
		columnsList.add(column6List);
		columnsList.add(column7List);
		columnsList.add(column8List);
		columnsList.add(column9List);
		columnsList.add(column10List);
		
		ArrayList<ArrayList<Object>> dropdownsToInitialize = new ArrayList<ArrayList<Object>>();

		dropdownsToInitialize = genericQueueUtil.initializeDropdowns(observationColls, queueDT, className);
		for(int i=0; i<index; i++){
			ArrayList<Object> column = columnsList.get(i);
			column=dropdownsToInitialize.get(i);
		}//TODO: delete??
		setElementColl(observationColls);
	}
	 */
	public GenericQueueUtil getGenericQueueUtil() {
		return genericQueueUtil;
	}

	public void setGenericQueueUtil(GenericQueueUtil genericQueueUtil) {
		this.genericQueueUtil = genericQueueUtil;
	}

	public Map<Object, Object> getSearchCriteriaArrayMap() {
		return searchCriteriaArrayMap;
	}

	public void setSearchCriteriaArrayMap(Map<Object, Object> searchCriteriaArrayMap) {
		this.searchCriteriaArrayMap = searchCriteriaArrayMap;
	}

	public ArrayList<Object> getColumn1List() {
		return column1List;
	}

	public void setColumn1List(ArrayList<Object> column1List) {
		this.column1List = column1List;
	}

	public ArrayList<Object> getColumn2List() {
		return column2List;
	}

	public void setColumn2List(ArrayList<Object> column2List) {
		this.column2List = column2List;
	}

	public ArrayList<Object> getColumn3List() {
		return column3List;
	}

	public void setColumn3List(ArrayList<Object> column3List) {
		this.column3List = column3List;
	}

	public ArrayList<Object> getColumn4List() {
		return column4List;
	}

	public void setColumn4List(ArrayList<Object> column4List) {
		this.column4List = column4List;
	}

	public ArrayList<Object> getColumn5List() {
		return column5List;
	}

	public void setColumn5List(ArrayList<Object> column5List) {
		this.column5List = column5List;
	}

	public ArrayList<Object> getColumn6List() {
		return column6List;
	}

	public void setColumn6List(ArrayList<Object> column6List) {
		this.column6List = column6List;
	}

	public ArrayList<Object> getColumn7List() {
		return column7List;
	}

	public void setColumn7List(ArrayList<Object> column7List) {
		this.column7List = column7List;
	}

	public ArrayList<Object> getColumn8List() {
		return column8List;
	}

	public void setColumn8List(ArrayList<Object> column8List) {
		this.column8List = column8List;
	}

	public ArrayList<Object> getColumn9List() {
		return column9List;
	}

	public void setColumn9List(ArrayList<Object> column9List) {
		this.column9List = column9List;
	}

	public ArrayList<Object> getColumn10List() {
		return column10List;
	}

	public void setColumn10List(ArrayList<Object> column10List) {
		this.column10List = column10List;
	}

	public QueueDT getQueueDT() {
		return queueDT;
	}

	public void setQueueDT(QueueDT queueDT) {
		this.queueDT = queueDT;
	}

	public Collection<Object> getElementColl() {
		return elementColl;
	}

	public void setElementColl(Collection<Object> observationColl) {
		this.elementColl = observationColl;
	}
}
