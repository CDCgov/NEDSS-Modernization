package gov.cdc.nedss.webapp.nbs.form.iisquery;

import gov.cdc.nedss.entity.person.vo.PatientSearchVO;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.vaccination.iis.dt.PatientSearchResultDT;
import gov.cdc.nedss.vaccination.iis.dt.VaccinationSearchResultDT;
import gov.cdc.nedss.webapp.nbs.form.util.BaseForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.queue.GenericQueueUtil;
import gov.cdc.nedss.webapp.nbs.queue.QueueColumnDT;
import gov.cdc.nedss.webapp.nbs.queue.QueueDT;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class IISQueryForm extends BaseForm {
	private Map<Object,Object> searchMap = new HashMap<Object,Object>();
	private PatientSearchVO patientSearch = null;
	private QueueDT queueDT;
	private ArrayList<QueueColumnDT> queueCollection;
	private String stringQueueCollection;
	private Map<Object,Object> searchCriteriaArrayMap = new HashMap<Object,Object>();
	private Collection<Object> patientSearchList = new ArrayList<Object>();
	private String CLASS_NAME = "gov.cdc.nedss.vaccination.iis.dt.PatientSearchResultDT";
	
	private Collection<Object> vaccinationSearchList = new ArrayList<Object>();
	private String CLASS_NAME_VACC = "gov.cdc.nedss.vaccination.iis.dt.VaccinationSearchResultDT";
	
	private ArrayList<Object> vaccAdDate = new ArrayList<Object> ();
	
	private Map<String, VaccinationSearchResultDT> vaccinationMap = new HashMap<String, VaccinationSearchResultDT>();
	
	private PatientSearchResultDT selectedPatient;
	private PatientSearchResultDT patientFromVaccinationResponse;
	
	GenericQueueUtil genericQueueUtil = new GenericQueueUtil();
	
	private String vaccinationResponseHL7Msg;
	
	private Map<String,String> errorAttributeMap = new HashMap<String,String>();
	
	public Map<Object, Object> getSearchMap() {
		return searchMap;
	}
	public void setSearchMap(Map<Object, Object> searchMap) {
		this.searchMap = searchMap;
	}
	
	public PatientSearchVO getPersonSearch(){
		if (patientSearch == null)
			patientSearch = new PatientSearchVO();
	
		return this.patientSearch;
	}
	public void setPersonSearch(PatientSearchVO psVo){
		 patientSearch = psVo;
	}
	
	 public Object getCodedValue(String codesetNm) {
		 return CachedDropDowns.getCodedValueForType(codesetNm);
	 }
		
	public Object getCodedValueNoBlnk(String key) {
		ArrayList<Object> list = (ArrayList<Object> ) getCodedValue(key);
		DropDownCodeDT dt = (DropDownCodeDT) list.get(0);
		if(dt.getKey() != null && dt.getKey().equals("") && dt.getValue() != null && dt.getValue().equals(""))
			list.remove(0);
		return list;
	}
	
	public ArrayList<Object> getStateList() {
		return CachedDropDowns.getStateList();
	}
	public QueueDT getQueueDT() {
		return queueDT;
	}
	public void setQueueDT(QueueDT queueDT) {
		this.queueDT = queueDT;
	}
	public ArrayList<QueueColumnDT> getQueueCollection() {
		return queueCollection;
	}
	public void setQueueCollection(ArrayList<QueueColumnDT> queueCollection) {
		this.queueCollection = queueCollection;
	}
	public String getStringQueueCollection() {
		return stringQueueCollection;
	}
	public void setStringQueueCollection(String stringQueueCollection) {
		this.stringQueueCollection = stringQueueCollection;
	}
	public Map<Object, Object> getSearchCriteriaArrayMap() {
		return searchCriteriaArrayMap;
	}
	public void setSearchCriteriaArrayMap(Map<Object, Object> searchCriteriaArrayMap) {
		this.searchCriteriaArrayMap = searchCriteriaArrayMap;
	}
	public Collection<Object> getPatientSearchList() {
		return patientSearchList;
	}
	public void setPatientSearchList(Collection<Object> patientSearchList) {
		this.patientSearchList = patientSearchList;
	}
	public String getCLASS_NAME() {
		return CLASS_NAME;
	}
	public void setCLASS_NAME(String cLASS_NAME) {
		CLASS_NAME = cLASS_NAME;
	}
	
	public void initializeDropDowns(Collection<Object> observationColls) {
		ArrayList<ArrayList<Object>> dropdownsToInitialize = new ArrayList<ArrayList<Object>>();
		dropdownsToInitialize = genericQueueUtil.initializeDropdowns(observationColls, queueDT, CLASS_NAME);
	}
	
	public String[] getAnswerArray(String key) {
		return (String[])searchCriteriaArrayMap.get(key);
	}

	public void setAnswerArray(String key, String[] answer) {
		if(answer.length > 0) {
			String [] answerList = new String[answer.length];
			boolean selected = false;
			for(int i=1; i<=answer.length; i++) {
				String answerTxt = answer[i-1];
				if(!answerTxt.equals("")) {
					selected = true;
					answerList[i-1] = answerTxt;
				}
			}
			if(selected)
				searchCriteriaArrayMap.put(key,answerList);
		}
	}

	public void setAnswerArrayText(String key, String answer) {
		if(answer!=null && answer.length() > 0) {
			String newKey = key+"_FILTER_TEXT";
			searchCriteriaArrayMap.put(newKey,answer);
		}
	}
	public Collection<Object> getVaccinationSearchList() {
		return vaccinationSearchList;
	}
	public void setVaccinationSearchList(Collection<Object> vaccinationSearchList) {
		this.vaccinationSearchList = vaccinationSearchList;
	}
	
	public void initializeVaccinationDropDowns(Collection<Object> observationColls) {
		
		ArrayList<ArrayList<Object>> dropdownsToInitialize = new ArrayList<ArrayList<Object>>();
		dropdownsToInitialize = genericQueueUtil.initializeDropdowns(observationColls, queueDT, CLASS_NAME_VACC);
		vaccAdDate = dropdownsToInitialize.get(0);
	}
	public String getCLASS_NAME_VACC() {
		return CLASS_NAME_VACC;
	}
	public void setCLASS_NAME_VACC(String cLASS_NAME_VACC) {
		CLASS_NAME_VACC = cLASS_NAME_VACC;
	}
	public ArrayList<Object> getVaccAdminDate() {
		return vaccAdDate;
	}
	public void setVaccAdminDate(ArrayList<Object> vaccAdminDate) {
		this.vaccAdDate = vaccAdminDate;
	}
	public Map<String, VaccinationSearchResultDT> getVaccinationMap() {
		return vaccinationMap;
	}
	public void setVaccinationMap(Map<String, VaccinationSearchResultDT> vaccinationMap) {
		this.vaccinationMap = vaccinationMap;
	}
	public PatientSearchResultDT getSelectedPatient() {
		return selectedPatient;
	}
	public void setSelectedPatient(PatientSearchResultDT selectedPatient) {
		this.selectedPatient = selectedPatient;
	}
	public PatientSearchResultDT getPatientFromVaccinationResponse() {
		return patientFromVaccinationResponse;
	}
	public void setPatientFromVaccinationResponse(
			PatientSearchResultDT patientFromVaccinationResponse) {
		this.patientFromVaccinationResponse = patientFromVaccinationResponse;
	}
	public String getVaccinationResponseHL7Msg() {
		return vaccinationResponseHL7Msg;
	}
	public void setVaccinationResponseHL7Msg(String vaccinationResponseHL7Msg) {
		this.vaccinationResponseHL7Msg = vaccinationResponseHL7Msg;
	}
	public void clearAll() {
		getAttributeMap().clear();
		vaccAdDate = new ArrayList();
		searchCriteriaArrayMap = new HashMap<Object,Object>();
	}
	public Map<String, String> getErrorAttributeMap() {
		return errorAttributeMap;
	}
	public void setErrorAttributeMap(Map<String, String> errorAttributeMap) {
		this.errorAttributeMap = errorAttributeMap;
	}
	
}
