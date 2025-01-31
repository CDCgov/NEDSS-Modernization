package gov.cdc.nedss.webapp.nbs.form.file;
import gov.cdc.nedss.entity.person.vo.PatientSearchVO;
import gov.cdc.nedss.entity.person.vo.PatientSrchResultVO;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.pagemanagement.wa.dt.BatchEntry;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.action.pam.PamClientVO.PamClientVO;
import gov.cdc.nedss.webapp.nbs.form.util.BaseForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

public class FileDetailsForm extends BaseForm{
	

	/*  private static final long serialVersionUID = 1L;		  
	 
		private String actionMode;
		private String returnToLink;
		private Map<Object,Object> attributeMap = new HashMap<Object,Object>();
		private PersonVO patientVoCollection;
		private PatientSrchResultVO patSrcResVO;
		private ArrayList<Object> dwrCounties = new ArrayList<Object>();
		
		//for ID Batch List
		private ArrayList<BatchEntry> idBatchAddrList = new ArrayList<BatchEntry>();
		private ArrayList<BatchEntry> idBatchNameList = new ArrayList<BatchEntry>();
		private ArrayList<BatchEntry> idBatchPhoneList = new ArrayList<BatchEntry>();
		private ArrayList<BatchEntry> idBatchIdenList = new ArrayList<BatchEntry>();
		private ArrayList<BatchEntry> idBatchRaceList = new ArrayList<BatchEntry>();
		private static int nextSeqId = 1;
		
		
		public PatientSrchResultVO getPatSrcResVO() {
			return patSrcResVO;
		}
		public void setPatSrcResVO(PatientSrchResultVO patSrcResVO) {
			this.patSrcResVO = patSrcResVO;
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
		
		
		
		public static long getSerialVersionUID() {
			return serialVersionUID;
		}
		
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {


		  ActionErrors errors = super.validate(mapping,request);
		    if(errors == null)
		    	errors = new ActionErrors();
	    return errors;
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
		public PersonVO getPatientVoCollection() {
			return patientVoCollection;
		}
		public void setPatientVoCollection(PersonVO patientVoCollection) {
			this.patientVoCollection = patientVoCollection;
		}
		
		  public ArrayList<BatchEntry> getAllBatchAddrAnswer(String subSectionNm) {
		    	if (subSectionNm.equalsIgnoreCase("nameTable"))	{	    		
			    return (idBatchNameList);
		    	}else if (subSectionNm.equalsIgnoreCase("addrTable")){		    		
				    return (idBatchAddrList);
		    	}else if (subSectionNm.equalsIgnoreCase("phoneTable")){		    		
				    return (idBatchPhoneList);
		    	}else if (subSectionNm.equalsIgnoreCase("raceTable")){		    		
				    return (idBatchRaceList);
		    	}else if (subSectionNm.equalsIgnoreCase("idenTable")){		    		
				    return (idBatchIdenList);
		    }
		    	return null;
		  }
		    
			public static synchronized int getNextSeqId()
		    {
				return nextSeqId++;
		    }
		    public void setBatchAnswer(BatchEntry batchEntry,String SubSecNm) {
		    	//determine what sequence number this item should be
				Iterator<BatchEntry> it1 = this.idBatchAddrList.iterator();
				int highSeq=0;
		      	 while(it1.hasNext()){
		      		BatchEntry ansItem = (BatchEntry)it1.next();
		      		if(highSeq < ansItem.getId()){
		      			highSeq = ansItem.getId();
		      		}
		      	 }
		      	 
			     if(highSeq != 0 ){
			    	 batchEntry.setId(highSeq+1);
		 	     }
		 	     else{
		 	    	batchEntry.setId(getNextSeqId());
		 		 }
			     if(SubSecNm.equals("Name"))
			    	 idBatchNameList.add(batchEntry);
			     else if(SubSecNm.equals("Address"))
			     idBatchAddrList.add(batchEntry);
			     else if(SubSecNm.equals("Phone"))
			    	 idBatchPhoneList.add(batchEntry);
			     else if(SubSecNm.equals("Identification"))
			    	 idBatchIdenList.add(batchEntry);
			     else if(SubSecNm.equals("Race"))
			    	 idBatchRaceList.add(batchEntry);			     
		    	
		    }
		    public void deleteBatchAnswer(BatchEntry batchEntry) {
		    	//determine what sequence number this item should be
		    	int seq = batchEntry.getId();
				Iterator<BatchEntry> it1 = this.idBatchAddrList.iterator();
		      	 while(it1.hasNext()){
		      		BatchEntry listItem = (BatchEntry)it1.next();
		      		if(seq == listItem.getId()){
		      			it1.remove();
		      		}
		      	 }	
		    }
		    
		    public void updateBatchAnswer(BatchEntry batchEntry) {
		    	//find the id and replace it
		    	boolean removedIt = false;
		    	int entrySeq = batchEntry.getId();
				Iterator<BatchEntry> it1 = this.idBatchAddrList.iterator();
		      	 while(it1.hasNext()){
		      		BatchEntry listItem = (BatchEntry)it1.next();
		      		if(entrySeq == listItem.getId()){
		      			it1.remove();
		      			removedIt = true;
		      		}
		      	 }
		      	 if (removedIt)
		      		idBatchAddrList.add(batchEntry);
		    }
		    
		    public ArrayList<BatchEntry> getIdBatchEntryList () {
		    	return this.idBatchAddrList;
		    }
		    
		    // private ArrayList<Object> ldfCollection;
		    public void reset()
		    {	      
			     nextSeqId = 1;
			     idBatchAddrList = new ArrayList<BatchEntry>();			 
			     idBatchNameList = new ArrayList<BatchEntry>();
				 idBatchPhoneList = new ArrayList<BatchEntry>();
				 idBatchIdenList = new ArrayList<BatchEntry>();
				 idBatchRaceList = new ArrayList<BatchEntry>();		    
		    }
		    
		    public ArrayList<Object> getDwrCountiesForState(String state) {
				this.dwrCounties = CachedDropDowns.getCountyCodes(state);
				return dwrCounties;
			}
			public ArrayList<Object> getDwrCounties() {
				return dwrCounties;
			}
			
			public ArrayList<Object> getDwrDefaultStateCounties() {
				return CachedDropDowns.getCountyCodes(PropertyUtil.getInstance()
						.getNBS_STATE_CODE());
			}

			public void setDwrCounties(ArrayList<Object> dwrCounties) {
				this.dwrCounties = dwrCounties;
			}	

			public ArrayList<Object> getCountryList() {
				return CachedDropDowns.getCountryList();
			}*/

		
	
}
