package gov.cdc.nedss.webapp.nbs.form.nbsdocument;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dt.NBSDocumentDT;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dt.SummaryDT;
import gov.cdc.nedss.webapp.nbs.form.util.BaseForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;

public class NbsDocumentForm extends BaseForm{
	
	private SummaryDT summaryDt = new SummaryDT();
	private Map<Object,Object> attributeMap = new HashMap<Object,Object>();
	private NBSDocumentDT nbsDocumentDt = new NBSDocumentDT();
	
	
	public ArrayList<Object> getJurisdictionList(){
		return CachedDropDowns.getJurisdictionNoExpList();
	}
	public ArrayList<Object> getProgramAreaList(){
		return CachedDropDowns.getProgramAreaList();
	}
	
	public SummaryDT getSummaryDt() {
		return summaryDt;
	}
	public void setSummaryDt(SummaryDT summaryDt) {
		this.summaryDt = summaryDt;
	}
	public void clearAll() {
		getAttributeMap().clear();
	}
	public Map<Object,Object> getAttributeMap() {
		return attributeMap;
	}
	public void setAttributeMap(Map<Object,Object> attributeMap) {
		this.attributeMap = attributeMap;
	}
	public NBSDocumentDT getNbsDocumentDt() {
		return nbsDocumentDt;
	}
	public void setNbsDocumentDt(NBSDocumentDT nbsDocumentDt) {
		this.nbsDocumentDt = nbsDocumentDt;
	}
	public void clearSelections() {
		this.setAttributeMap(new HashMap<Object,Object>());
		this.setSummaryDt(new SummaryDT());
	}
}
