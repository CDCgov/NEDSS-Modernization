

package gov.cdc.nedss.webapp.nbs.form.person;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

import gov.cdc.nedss.entity.entityid.dt.EntityIdDT;
import gov.cdc.nedss.entity.person.vo.PatientSrchResultVO;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.locator.dt.EntityLocatorParticipationDT;
import gov.cdc.nedss.pagemanagement.wa.dt.BatchEntry;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.form.util.BaseForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.action.pam.PamClientVO.PamClientVO;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

//import org.apache.struts.action.ActionForm;

public class CompleteDemographicForm extends BaseForm {

  private static final LogUtils logger = new LogUtils(CompleteDemographicForm.class.
      getName());
    private Boolean loadedFromDB = new Boolean(false);

    private PersonVO person = null;

    private String actionMode;
	private String returnToLink;
	private Map<Object,Object> attributeMap = new HashMap<Object,Object>();
    
    private ArrayList<Object> addressCollection;
    private ArrayList<Object> telephoneCollection;
    private ArrayList<Object> physicalCollection;
    private EntityLocatorParticipationDT birthAddress;
    private EntityLocatorParticipationDT deceasedAddress;
    private EntityIdDT ssnDetails;
	private ArrayList<Object> dwrCounties = new ArrayList<Object>();
	private ArrayList<Object> dwrBirthCounties = new ArrayList<Object>();
	private ArrayList<Object> dwrDeathCounties = new ArrayList<Object>();
	private String patientHomePhone;
	private String patientWorkPhone;
	private String patientWorkPhoneExt;
	private String patientCellPhone;
	private String patientEmail;
	private String patientAsOfDateGeneral;
	private String patientBirthTime;
	private String patientDeceasedDate;
	private PatientSrchResultVO patSrcResVO;
	private ArrayList<Object> subRaces = new ArrayList<Object>();
	
	// Note: for debug only - ID values stored in batchEntry.answerMap
	private String idTypeDescCd; 
	private String idAssigningAuthorityCd;
	private String idValue;
	//for ID Batch List
	private ArrayList<BatchEntry> idBatchAddrList = new ArrayList<BatchEntry>();
	private ArrayList<BatchEntry> idBatchNameList = new ArrayList<BatchEntry>();
	private ArrayList<BatchEntry> idBatchPhoneList = new ArrayList<BatchEntry>();
	private ArrayList<BatchEntry> idBatchIdenList = new ArrayList<BatchEntry>();
	private ArrayList<BatchEntry> idBatchRaceList = new ArrayList<BatchEntry>();
	
	private PamClientVO pamClientVO = new PamClientVO();
	
	//for ID Batch List
	private ArrayList<BatchEntry> idBatchEntryList = new ArrayList<BatchEntry>();
	private static int nextSeqId = 1;
	
   // private ArrayList<Object> ldfCollection;
    public void reset()
    {
      person = null;
      addressCollection=null;
      telephoneCollection=null;
      physicalCollection=null;
      birthAddress=null;
      deceasedAddress=null;
  	  patientHomePhone="";
	  patientWorkPhone="";
	  patientWorkPhoneExt="";
	  patientCellPhone="";
	  patientEmail="";
	  patientAsOfDateGeneral="";
	  patientBirthTime="";
	  patientDeceasedDate="";
	  idTypeDescCd="";
	  idAssigningAuthorityCd="";
	  idValue="";
	  pamClientVO = new PamClientVO();
	  nextSeqId = 1;
	  idBatchEntryList = new ArrayList<BatchEntry>();
	  //spanOrigin =null;
	 // resetBatch();
	//  patSrcResVO = new PatientSrchResultVO();
      super.resetLDF();
    }
    public void resetBatch(){
    	if(nextSeqId!=1)
    	 nextSeqId = 1;
   	
   	  idBatchAddrList = new ArrayList<BatchEntry>();			 
         idBatchNameList = new ArrayList<BatchEntry>();
   	  idBatchPhoneList = new ArrayList<BatchEntry>();
   	  idBatchIdenList = new ArrayList<BatchEntry>();
   	  idBatchRaceList = new ArrayList<BatchEntry>();
    	
    }
	public Object getCodedValue(String key) {
		ArrayList<Object> aList = new ArrayList<Object>();
		//if (formFieldMap.containsKey(key)) {
		//	FormField fField = (FormField) formFieldMap.get(key);
			 aList = CachedDropDowns.getCodedValueForType(key);
			 if(key != null && key.equalsIgnoreCase("EL_USE_PST_PAT")){
				 DropDownCodeDT dDownDT = null;
				 for(int i=0;i<aList.size();i++){
					 dDownDT = (DropDownCodeDT)aList.get(i);
					 if(dDownDT != null && (dDownDT.getKey().equals(NEDSSConstants.BIRTH) ||dDownDT.getKey().equals(NEDSSConstants.DEATH))){
						 aList.remove(i);
					 }
				 }
			 }
			//aList = fField.getAList();
		//}
		return aList;
	}
    public PersonVO getPerson()
    {
       if (person == null)
        person = new PersonVO();

    	return this.person;
    }

    public ArrayList<Object> getAddressCollection()
    {
      return addressCollection;
    }

    public ArrayList<Object> getTelephoneCollection()
    {
      return telephoneCollection;
    }

    public ArrayList<Object> getPhysicalCollection()
    {
      return physicalCollection;
    }

    public EntityLocatorParticipationDT getBirthAddress()
    {
       if (birthAddress == null)
        birthAddress = new EntityLocatorParticipationDT();

    	return this.birthAddress;
    }

    public EntityLocatorParticipationDT getDeceasedAddress()
    {
       if (deceasedAddress == null)
        deceasedAddress = new EntityLocatorParticipationDT();

    	return this.deceasedAddress;
    }

    public EntityIdDT getSsnDetails()
    {
       if (ssnDetails == null)
        ssnDetails = new EntityIdDT();

    	return this.ssnDetails;
    }
 /*   public ArrayList<Object> getLdfCollection()
    {
      return ldfCollection;
    }
*/

    public void setPerson(PersonVO newVal)
    {
    	this.person = newVal;
    }

    public void setAddressCollection(ArrayList<Object>  addressCollection)
    {
      this.addressCollection  = addressCollection;
    }

    public void setTelephoneCollection(ArrayList<Object>  telephoneCollection)
    {
      this.telephoneCollection  = telephoneCollection;
    }

    public void setPhysicalCollection(ArrayList<Object>  physicalCollection)
    {
      this.physicalCollection  = physicalCollection;
    }

    public void setBirthAddress(EntityLocatorParticipationDT newVal)
    {
    	this.birthAddress = newVal;
    }

    public void setDeceasedAddress(EntityLocatorParticipationDT newVal)
    {
    	this.deceasedAddress = newVal;
    }

    public void setSsnDetails(EntityIdDT newVal)
    {
    	this.ssnDetails = newVal;
    }
/*    public void setLdfCollection(ArrayList<Object>  aLdfCollection)
    {
      this.ldfCollection  = aLdfCollection;
    }
*/


    public EntityLocatorParticipationDT getAddress(int index)
    {
      // this should really be in the constructor
      if (this.addressCollection  == null)
          this.addressCollection  = new ArrayList<Object> ();

      int currentSize = this.addressCollection.size();

      // check if we have a this many personNameDTs
      if (index < currentSize)
      {
        try {
          Object[] tempArray = this.addressCollection.toArray();

          Object tempObj  = tempArray[index];

          EntityLocatorParticipationDT tempDT = (EntityLocatorParticipationDT) tempObj;

          return tempDT;
        }
        catch (Exception e) {
           //##!! System.out.println(e);
        } // do nothing just continue
      }
       EntityLocatorParticipationDT tempDT = null;

        for (int i = currentSize; i < index+1; i++)
        {
          tempDT = new EntityLocatorParticipationDT();
          this.addressCollection.add(tempDT);
        }
        return tempDT;
    }


    public EntityLocatorParticipationDT getTelephone(int index)
    {
      // this should really be in the constructor
      if (this.telephoneCollection  == null)
          this.telephoneCollection  = new ArrayList<Object> ();

      int currentSize = this.telephoneCollection.size();

      // check if we have a this many personNameDTs
      if (index < currentSize)
      {
        try {
          Object[] tempArray = this.telephoneCollection.toArray();

          Object tempObj  = tempArray[index];

          EntityLocatorParticipationDT tempDT = (EntityLocatorParticipationDT) tempObj;

          return tempDT;
        }
        catch (Exception e) {
           //##!! System.out.println(e);
        } // do nothing just continue
      }
       EntityLocatorParticipationDT tempDT = null;

        for (int i = currentSize; i < index+1; i++)
        {
          tempDT = new EntityLocatorParticipationDT();
          this.telephoneCollection.add(tempDT);
        }
        return tempDT;
    }

    public EntityLocatorParticipationDT getPhysical(int index)
    {
      // this should really be in the constructor
      if (this.physicalCollection  == null)
          this.physicalCollection  = new ArrayList<Object> ();

      int currentSize = this.physicalCollection.size();

      // check if we have a this many personNameDTs
      if (index < currentSize)
      {
        try {
          Object[] tempArray = this.physicalCollection.toArray();

          Object tempObj  = tempArray[index];

          EntityLocatorParticipationDT tempDT = (EntityLocatorParticipationDT) tempObj;

          return tempDT;
        }
        catch (Exception e) {
           //##!! System.out.println(e);
        } // do nothing just continue
      }
       EntityLocatorParticipationDT tempDT = null;

        for (int i = currentSize; i < index+1; i++)
        {
          tempDT = new EntityLocatorParticipationDT();
          this.physicalCollection.add(tempDT);
        }
        return tempDT;
    }

    public boolean isLoadedFromDB()
    {
      return (this.loadedFromDB.booleanValue());
    }

    public void setLoadedFromDB(boolean newVal)
    {
      this.loadedFromDB = new Boolean(newVal);
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
	
	public ArrayList<Object> getDwrBirthCountiesForState(String state) {
		this.dwrBirthCounties = CachedDropDowns.getCountyCodes(state);
		return dwrBirthCounties;
	}
	public ArrayList<Object> getDwrBirthCounties() {
		return dwrBirthCounties;
	}

	public void setDwrBirthCounties(ArrayList<Object> dwrBirthCounties) {
		this.dwrBirthCounties = dwrBirthCounties;
	}
	
	public ArrayList<Object> getDwrDeathCountiesForState(String state) {
		this.dwrDeathCounties = CachedDropDowns.getCountyCodes(state);
		return dwrDeathCounties;
	}
	public ArrayList<Object> getDwrDeathCounties() {
		return dwrDeathCounties;
	}

	public void setDwrDeathCounties(ArrayList<Object> dwrDeathCounties) {
		this.dwrDeathCounties = dwrDeathCounties;
	}
	
	
	public ArrayList<Object> getSubRacesForType(String raceType) {
		this.subRaces = CachedDropDowns.getRaceCodes(raceType);
		return subRaces;
	}
	public ArrayList<Object> getSubRaces() {
		return subRaces;
	}	

	public void setSubRaces(ArrayList<Object> subRaces) {
		this.subRaces = subRaces;
	}
	
	public ArrayList<Object> getStateList() {
		return CachedDropDowns.getStateList();
	}

	public ArrayList<Object> getCountryList() {
		return CachedDropDowns.getCountryList();
	}
	
	public String getPatientHomePhone() {
		return patientHomePhone;
	}
	public void setPatientHomePhone (String patientHomePhone) {
		this.patientHomePhone = patientHomePhone;
	}
	public String getPatientWorkPhone() {
		return patientWorkPhone;
	}
	public void setPatientWorkPhone (String patientWorkPhone) {
		this.patientWorkPhone = patientWorkPhone;
	}
	
	public String getPatientWorkPhoneExt() {
		return patientWorkPhoneExt;
	}
	public void setPatientWorkPhoneExt (String patientWorkPhoneExt) {
		this.patientWorkPhoneExt = patientWorkPhoneExt;
	}
	
	public String getPatientCellPhone() {
		return patientCellPhone;
	}
	public void setPatientCellPhone (String patientCellPhone) {
		this.patientCellPhone = patientCellPhone;
	}	
	public String getPatientEmail() {
		return patientEmail;
	}
	public void setPatientEmail (String patientEmail) {
		this.patientEmail = patientEmail;
	}
	public String getPatientAsOfDateGeneral() {
		if (patientAsOfDateGeneral == null || patientAsOfDateGeneral.isEmpty()) {
			Date date = new Date();
		    String DATE_FORMAT = "MM/dd/yyyy";
		    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
			patientAsOfDateGeneral = sdf.format(date);
		}
		return patientAsOfDateGeneral;
	}
	public void setPatientAsOfDateGeneral (String patientAsOfDateGeneral) {
		this.patientAsOfDateGeneral = patientAsOfDateGeneral;
	}	
	public String getPatientBirthTime() {
		return patientBirthTime;
	}
	public void setPatientBirthTime (String patientBirthTime) {
		this.patientBirthTime = patientBirthTime;
	}
	
	public String getPatientDeceasedDate() {
		return patientDeceasedDate;
	}
	public void setPatientDeceasedDate (String patientDeceasedDate) {
		this.patientDeceasedDate = patientDeceasedDate;
	}

	public String getIdTypeDescCd() {
		return idTypeDescCd;
	}
	public void setIdTypeDescCd (String idTypeDescCd) {
		this.idTypeDescCd = idTypeDescCd;
	}

	public String getIdAssigningAuthorityCd() {
		return idAssigningAuthorityCd;
	}
	public void setIdAssigningAuthorityCd (String idAssigningAuthorityCd) {
		this.idAssigningAuthorityCd = idAssigningAuthorityCd;
	}

	public String getIdValue() {
		return idValue;
	}
	public void setIdValue (String idValue) {
		this.idValue = idValue;
	}


	public PamClientVO getPamClientVO() {
		if (this.pamClientVO == null)
			this.pamClientVO = new PamClientVO();
		return pamClientVO;
	}
	public void setPamClientVO(PamClientVO pamClientVO) {
		this.pamClientVO = pamClientVO;
	}
	
    public ArrayList<BatchEntry> getAllBatchAnswer(String subSectionNm) {
    	//if (subSectionNm.equalsIgnoreCase("idSubSection"))
    		return (idBatchEntryList);
    }
    
	public static synchronized int getNextSeqId()
    {
		return nextSeqId++;
    }
    public void setBatchAnswerFromAdd(BatchEntry batchEntry) {
    	//determine what sequence number this item should be
		Iterator<BatchEntry> it1 = this.idBatchEntryList.iterator();
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
	     idBatchEntryList.add(batchEntry);
    	
    }
    public void deleteBatchAnswer(BatchEntry batchEntry) {
    	//determine what sequence number this item should be
    	int seq = batchEntry.getId();
		Iterator<BatchEntry> it1 = this.idBatchEntryList.iterator();
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
		Iterator<BatchEntry> it1 = this.idBatchEntryList.iterator();
      	 while(it1.hasNext()){
      		BatchEntry listItem = (BatchEntry)it1.next();
      		if(entrySeq == listItem.getId()){
      			it1.remove();
      			removedIt = true;
      		}
      	 }
      	 if (removedIt)
      		 idBatchEntryList.add(batchEntry);
    }
    
    public ArrayList<BatchEntry> getIdBatchEntryList () {
    	return this.idBatchEntryList;
    }	
    
    public ArrayList<BatchEntry> getAllBatchAnswer4Table(String subSectionNm) {
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
    
	
    public void setBatchAnswer(BatchEntry batchEntry,String SubSecNm) {
    	//determine what sequence number this item should be
    	Iterator<BatchEntry> it1 = null;
    	if(SubSecNm.equals("Name")&& idBatchNameList != null ){
    	 it1 = this.idBatchNameList.iterator();	
    	}else if(SubSecNm.equals("Address")&& idBatchAddrList != null ){
    	 it1 = this.idBatchAddrList.iterator();	
    	}else if(SubSecNm.equals("Phone")&& idBatchPhoneList != null ){
    	 it1 = this.idBatchPhoneList.iterator();	
    	}else if(SubSecNm.equals("Identification")&& idBatchIdenList != null ){
    	 it1 = this.idBatchIdenList.iterator();	
    	}else if(SubSecNm.equals("Race")&& idBatchRaceList != null ){
    	 it1 = this.idBatchRaceList.iterator();	
    	}
		//Iterator<BatchEntry> it1 = this.idBatchAddrList.iterator();
		int highSeq=0;
		if(it1 != null){
      	 while(it1.hasNext()){
      		BatchEntry ansItem = (BatchEntry)it1.next();
      		if(highSeq < ansItem.getId()){
      			highSeq = ansItem.getId();
      		}
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

	public void deleteBatchAnswer4Table(BatchEntry batchEntry, String tableName) {
		// determine what sequence number this item should be
		Iterator<BatchEntry> it1 = null;
		BatchEntry entryToBeRemoved = null;
		try {
			int seq = batchEntry.getId();
			if (tableName.equals("nameTable")) {
				it1 = this.idBatchNameList.iterator();
			} else if (tableName.equalsIgnoreCase("addrTable")) {
				it1 = this.idBatchAddrList.iterator();
			} else if (tableName.equalsIgnoreCase("phoneTable")) {
				it1 = this.idBatchPhoneList.iterator();
			} else if (tableName.equalsIgnoreCase("raceTable")) {
				it1 = this.idBatchRaceList.iterator();
			} else if (tableName.equalsIgnoreCase("idenTable")) {
				it1 = this.idBatchIdenList.iterator();
			}
			if (it1 != null) {
				while (it1.hasNext()) {
					BatchEntry listItem = (BatchEntry) it1.next();
					if (seq == listItem.getId()) {
						entryToBeRemoved = listItem;
					}
				}
			}
			if (entryToBeRemoved != null) {
				if (tableName.equals("nameTable")) {
					idBatchNameList.remove(entryToBeRemoved);
				} else if (tableName.equalsIgnoreCase("addrTable")) {
					idBatchAddrList.remove(entryToBeRemoved);
				} else if (tableName.equalsIgnoreCase("phoneTable")) {
					idBatchPhoneList.remove(entryToBeRemoved);
				} else if (tableName.equalsIgnoreCase("raceTable")) {
					idBatchRaceList.remove(entryToBeRemoved);
				} else if (tableName.equalsIgnoreCase("idenTable")) {
					idBatchIdenList.remove(entryToBeRemoved);
				}
			}
		} catch (Exception ex) {
			logger.error("Exception while removing entry from batch table: "
					+ tableName);
			ex.printStackTrace();
		}
	}
    
    public void updateBatchAnswer4Table(BatchEntry batchEntry,String tableName) {
    	//find the id and replace it
    	
    	boolean removedIt = false;
    	Iterator<BatchEntry> it1 = null;
    	int entrySeq = batchEntry.getId();
    	if(tableName.equals("nameTable")){
    	 it1 = this.idBatchNameList.iterator();
    	}else if(tableName.equalsIgnoreCase("addrTable")){
		 it1 = this.idBatchAddrList.iterator();
    	}else if (tableName.equalsIgnoreCase("phoneTable")){	
    		 it1 = this.idBatchPhoneList.iterator();
    			 
    	}else if (tableName.equalsIgnoreCase("raceTable")){	
    		 it1 = this.idBatchRaceList.iterator();
		  
    	}else if (tableName.equalsIgnoreCase("idenTable")){	
    		 it1 = this.idBatchIdenList.iterator();
		   
    	}
    	int count=0;
    	if(it1 != null){
      	 while(it1.hasNext()){
      		BatchEntry listItem = (BatchEntry)it1.next();
      		if(entrySeq == listItem.getId()){
      			it1.remove();
      		   if (tableName.equals("nameTable"))
      	      		idBatchNameList.add(count,batchEntry);      		
      	      	 else if(tableName.equalsIgnoreCase("addrTable"))
      	      		 idBatchAddrList.add(count,batchEntry);
      	      	 else if(tableName.equalsIgnoreCase("phoneTable"))
      	      		idBatchPhoneList.add(count,batchEntry);
      	      	 else if(tableName.equalsIgnoreCase("raceTable"))
      	      		idBatchRaceList.add(count,batchEntry);
      	      	 else if(tableName.equalsIgnoreCase("idenTable"))
      	      		idBatchIdenList.add(count,batchEntry);
      			removedIt = true;
      			break;
      		}
      		count=count+1;
      	 }
    	}
      	
    }
    
    public ArrayList<BatchEntry> getIdBatchEntryList4Table () {
    	return this.idBatchAddrList;
    }
    
   
	
	public Object getCodedValueNoBlnk(String key) {
		ArrayList<Object> list = (ArrayList<Object> ) getCodedValue(key);
		DropDownCodeDT dt = (DropDownCodeDT) list.get(0);
		if(dt.getKey() != null && dt.getKey().equals("") && dt.getValue() != null && dt.getValue().equals(""))
			list.remove(0);
		return list;
	}
	
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
	
	public ArrayList<Object> getPrimaryOccupationCodes(){
		return CachedDropDowns.getPrimaryOccupationCodes();
	}
	
	public ArrayList<Object> getLanguageCodes(){
		return CachedDropDowns.getLanguageCodes();
	}
	
	
	
    private String nmAsOf; private String nmType;private String nmPrefix; private String nmLast;
    private String nmSecLast; private String nmFirst;private String nmMiddle; private String nmSecMiddle;
    private String nmSuffix; private String nmDegree;

	public String getNmAsOf() {
		return nmAsOf;
	}
	public void setNmAsOf(String nmAsOf) {
		this.nmAsOf = nmAsOf;
	}
	public String getNmType() {
		return nmType;
	}
	public void setNmType(String nmType) {
		this.nmType = nmType;
	}
	public String getNmPrefix() {
		return nmPrefix;
	}
	public void setNmPrefix(String nmPrefix) {
		this.nmPrefix = nmPrefix;
	}
	public String getNmLast() {
		return nmLast;
	}
	public void setNmLast(String nmLast) {
		this.nmLast = nmLast;
	}
	public String getNmSecLast() {
		return nmSecLast;
	}
	public void setNmSecLast(String nmSecLast) {
		this.nmSecLast = nmSecLast;
	}
	public String getNmFirst() {
		return nmFirst;
	}
	public void setNmFirst(String nmFirst) {
		this.nmFirst = nmFirst;
	}
	public String getNmMiddle() {
		return nmMiddle;
	}
	public void setNmMiddle(String nmMiddle) {
		this.nmMiddle = nmMiddle;
	}
	public String getNmSecMiddle() {
		return nmSecMiddle;
	}
	public void setNmSecMiddle(String nmSecMiddle) {
		this.nmSecMiddle = nmSecMiddle;
	}
	public String getNmSuffix() {
		return nmSuffix;
	}
	public void setNmSuffix(String nmSuffix) {
		this.nmSuffix = nmSuffix;
	}
	public String getNmDegree() {
		return nmDegree;
	}
	public void setNmDegree(String nmDegree) {
		this.nmDegree = nmDegree;
	}
	
	private String addrAsOf;private String addrType;private String addrUse;private String addrStreet1;private String addrStreet2;
	private String addrCity;private String addrState;private String addrZip;private String addrCnty;private String addrCntry;
	private String addrComments; private String addrCensusTract;

	public String getAddrAsOf() {
		return addrAsOf;
	}
	public void setAddrAsOf(String addrAsOf) {
		this.addrAsOf = addrAsOf;
	}
	public String getAddrType() {
		return addrType;
	}
	public void setAddrType(String addrType) {
		this.addrType = addrType;
	}
	public String getAddrUse() {
		return addrUse;
	}
	public void setAddrUse(String addrUse) {
		this.addrUse = addrUse;
	}
	public String getAddrStreet1() {
		return addrStreet1;
	}
	public void setAddrStreet1(String addrStreet1) {
		this.addrStreet1 = addrStreet1;
	}
	public String getAddrStreet2() {
		return addrStreet2;
	}
	public void setAddrStreet2(String addrStreet2) {
		this.addrStreet2 = addrStreet2;
	}
	public String getAddrCity() {
		return addrCity;
	}
	public void setAddrCity(String addrCity) {
		this.addrCity = addrCity;
	}
	public String getAddrState() {
		return addrState;
	}
	public void setAddrState(String addrState) {
		this.addrState = addrState;
	}
	public String getAddrZip() {
		return addrZip;
	}
	public void setAddrZip(String addrZip) {
		this.addrZip = addrZip;
	}
	public String getAddrCnty() {
		return addrCnty;
	}
	public void setAddrCnty(String addrCnty) {
		this.addrCnty = addrCnty;
	}
	public String getAddrCntry() {
		return addrCntry;
	}
	public void setAddrCntry(String addrCntry) {
		this.addrCntry = addrCntry;
	}
	public String getAddrComments() {
		return addrComments;
	}
	public void setAddrComments(String addrComments) {
		this.addrComments = addrComments;
	}
	
	private String phAsOf;private String phType;private String phUse;private String phCntryCd;private String phNum;
	private String phExt;private String phEmail;private String phUrl;private String phComments;

	public String getPhAsOf() {
		return phAsOf;
	}
	public void setPhAsOf(String phAsOf) {
		this.phAsOf = phAsOf;
	}
	public String getPhType() {
		return phType;
	}
	public void setPhType(String phType) {
		this.phType = phType;
	}
	public String getPhUse() {
		return phUse;
	}
	public void setPhUse(String phUse) {
		this.phUse = phUse;
	}
	public String getPhCntryCd() {
		return phCntryCd;
	}
	public void setPhCntryCd(String phCntryCd) {
		this.phCntryCd = phCntryCd;
	}
	public String getPhNum() {
		return phNum;
	}
	public void setPhNum(String phNum) {
		this.phNum = phNum;
	}
	public String getPhExt() {
		return phExt;
	}
	public void setPhExt(String phExt) {
		this.phExt = phExt;
	}
	public String getPhEmail() {
		return phEmail;
	}
	public void setPhEmail(String phEmail) {
		this.phEmail = phEmail;
	}
	public String getPhUrl() {
		return phUrl;
	}
	public void setPhUrl(String phUrl) {
		this.phUrl = phUrl;
	}
	public String getPhComments() {
		return phComments;
	}
	public void setPhComments(String phComments) {
		this.phComments = phComments;
	}
    
	private String idAsOf;private String idType;private String idAssgn;private String idsValue;

	public String getIdAsOf() {
		return idAsOf;
	}
	public void setIdAsOf(String idAsOf) {
		this.idAsOf = idAsOf;
	}
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public String getIdAssgn() {
		return idAssgn;
	}
	public void setIdAssgn(String idAssgn) {
		this.idAssgn = idAssgn;
	}
	public String getIdsValue() {
		return idsValue;
	}
	public void setIdsValue(String idsValue) {
		this.idsValue = idsValue;
	}
	
	private String raceAsOf;private String raceType;private String raceDetailCat;

	public String getRaceType() {
		return raceType;
	}
	public void setRaceType(String raceType) {
		this.raceType = raceType;
	}
	public String getRaceDetailCat() {
		return raceDetailCat;
	}
	public void setRaceDetailCat(String raceDetailCat) {
		this.raceDetailCat = raceDetailCat;
	}
	public String getRaceAsOf() {
		return raceAsOf;
	}
	public void setRaceAsOf(String raceAsOf) {
		this.raceAsOf = raceAsOf;
	}
	
	private String[] spanOrigin;

	public String[] getSpanOrigin() {
		return spanOrigin;
	}
	public void setSpanOrigin(String[] spanOrigin) {
		this.spanOrigin = spanOrigin;
	}
	/**
	 * @return the addrCensusTract
	 */
	public String getAddrCensusTract() {
		return addrCensusTract;
	}
	/**
	 * @param addrCensusTract the addrCensusTract to set
	 */
	public void setAddrCensusTract(String addrCensusTract) {
		this.addrCensusTract = addrCensusTract;
	}
	
/**
 * checkPermissionSTDHIVProgramArea: this method returns true if the user has permissions for STD or HIV. It was created
 * in order to resolve the defect: ND-13280
 * @return
 */
public boolean checkPermissionSTDHIVProgramArea(){
		
		boolean hasPermission = true;
		
		WebContext ctx = WebContextFactory.get();
		HttpServletRequest request = ctx.getHttpServletRequest();
		HttpSession session = request.getSession(false);
        NBSSecurityObj nbsSecurityObj = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");
		String programAreas = nbsSecurityObj.getTheUserProfile().getTheUser().getPaaProgramArea();
		
		if(programAreas!=null)
			if(programAreas.indexOf("STD")==-1 && programAreas.indexOf("HIV")==-1)
				hasPermission = false;
		
		return hasPermission;
	}

}
    
   
