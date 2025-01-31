package gov.cdc.nedss.systemservice.ejb.casenotificationejb.dt;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ExportAlgorithmDT extends AbstractVO implements RootDTInterface {
	private static final long serialVersionUID = 1L;
	private String algorithmName;
	private String documentType;
	private String levelOfReview;
	private String recordStatusCd;
	private String receivingSystem;
	private String cityList;
	private Timestamp beginDate;
	private Timestamp endDate;	
	private Timestamp addTime;
	private Long addUserId;
	private Timestamp lastChgTime;
	private Long lastChgUserId;
	private Long exportAlgorithmUid;
	private String viewLink;
	private String editLink;
	private Long exportReceivingFacilityUid;
	ExportTriggerDT exTrDT = new ExportTriggerDT();
	ArrayList<Object>  triggerDTList = new ArrayList<Object> ();
	
	private Set<Object> exTrDTset =  new HashSet<Object>();
	
	private String recordStatusCdDescTxt;
	private String levelOfReviewDescTxt;
	private String documentTypeDescTxt;
	private String receivingSystemDescTxt;
	
	public String getAlgorithmName() {
		return algorithmName;
	}
	public void setAlgorithmName(String algorithmName) {
		this.algorithmName = algorithmName;
	}
	public String getDocumentType() {
		return documentType;
	}
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	public String getLevelOfReview() {
		return levelOfReview;
	}
	public void setLevelOfReview(String levelOfReview) {
		this.levelOfReview = levelOfReview;
	}
	public String getReceivingSystem() {
		return receivingSystem;
	}
	public void setReceivingSystem(String receivingSystem) {
		this.receivingSystem = receivingSystem;
	}
	public String getCityList() {
		return cityList;
	}
	public void setCityList(String cityList) {
		this.cityList = cityList;
	}
	public Timestamp getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Timestamp beginDate) {
		this.beginDate = beginDate;
	}
	public Timestamp getEndDate() {
		return endDate;
	}
	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}
	public String getRecordStatusCd() {
		return recordStatusCd;
	}
	public void setRecordStatusCd(String recordStatusCd) {
		this.recordStatusCd = recordStatusCd;
	}
	public boolean isEqual(Object objectname1, Object objectname2, Class<?> voClass) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public Long getExportAlgorithmUid() {
		return exportAlgorithmUid;
	}
	public void setExportAlgorithmUid(Long exportAlgorithmUid) {
		this.exportAlgorithmUid = exportAlgorithmUid;
	}
	public Timestamp getAddTime() {
		// TODO Auto-generated method stub
		return addTime;
	}
	public Long getAddUserId() {
		// TODO Auto-generated method stub
		return addUserId;
	}
	public String getJurisdictionCd() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getLastChgReasonCd() {
		// TODO Auto-generated method stub
		return null;
	}
	public Timestamp getLastChgTime() {
		// TODO Auto-generated method stub
		return lastChgTime;
	}
	public Long getLastChgUserId() {
		// TODO Auto-generated method stub
		return lastChgUserId;
	}
	public String getLocalId() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getProgAreaCd() {
		// TODO Auto-generated method stub
		return null;
	}
	public Long getProgramJurisdictionOid() {
		// TODO Auto-generated method stub
		return null;
	}
	public Timestamp getRecordStatusTime() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getSharedInd() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getStatusCd() {
		// TODO Auto-generated method stub
		return null;
	}
	public Timestamp getStatusTime() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getSuperclass() {
		// TODO Auto-generated method stub
		return null;
	}
	public Long getUid() {
		// TODO Auto-generated method stub
		return null;
	}
	public Integer getVersionCtrlNbr() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setAddTime(Timestamp addTime) {
		this.addTime = addTime;
		
	}
	public void setAddUserId(Long addUserId) {
		this.addUserId = addUserId;
		
	}
	 
	
	public void setLastChgReasonCd(String lastChgReasonCd) {
		// TODO Auto-generated method stub
		
	}
	
	

	   /**
	    * Sets the value of the itDirty property.
	    *
	    * @param boolean itDirty the new value of the ItDirty property
	    */
	   public void setItDirty(boolean itDirty)
	   {
		this.itDirty = itDirty;
	   }

	  /**
	    * get the value of the boolean property.
	    *
	    * @return ItDirty the value of the boolean value
	    */
	   public boolean isItDirty()
	   {
	    return this.itDirty;
	   }

	      //Wrapper method to invoke setItnew(boolean)...
	      /**
	       * Sets the value of the ItNew property.
	       *
	       * @param itNew boolean the new value of the boolean property
	       */
	      public void setItNew(Boolean itNew)
	      {
	       this.itNew = itNew.booleanValue();
	      }



	    /**
	    * get the value of the itNew property.
	    *
	    * @return itNew the value of the boolean property
	    */
	   public boolean isItNew()
	   {
	    return this.itNew;
	   }

	   /**
	    * Sets the value of the ItDelete property.
	    *
	    * @param itDelete boolean the value of the boolean property
	    */
	   public void setItDelete(boolean itDelete)
	   {
	        this.itDelete = itDelete;
	   }

	   /**
	    * gets the value of the ItDelete property.
	    *
	    * @return ItDelete the new value of the boolean property
	    */
	   public boolean isItDelete()
	   {
	    return this.itDelete;
	   }


	public void setLastChgTime(Timestamp lastChgTime) {
		this.lastChgTime = lastChgTime;
		
	}
	public void setLastChgUserId(Long lastChgUserId) {
		this.lastChgUserId = lastChgUserId;
		
	}
	public void setLocalId(String localId) {
		// TODO Auto-generated method stub
		
	}
	public void setProgAreaCd(String progAreaCd) {
		// TODO Auto-generated method stub
		
	}
	public void setProgramJurisdictionOid(Long programJurisdictionOid) {
		// TODO Auto-generated method stub
		
	}
	public void setRecordStatusTime(Timestamp recordStatusTime) {
		// TODO Auto-generated method stub
		
	}
	public void setSharedInd(String sharedInd) {
		// TODO Auto-generated method stub
		
	}
	public void setStatusCd(String statusCd) {
		// TODO Auto-generated method stub
		
	}
	public void setStatusTime(Timestamp statusTime) {
		// TODO Auto-generated method stub
		
	}
	public void setItNew(boolean itNew) {
		// TODO Auto-generated method stub
		
	}
	public void setJurisdictionCd(String jurisdictionCd) {
		// TODO Auto-generated method stub
		
	}
	public String getViewLink() {
		return viewLink;
	}
	public void setViewLink(String viewLink) {
		this.viewLink = viewLink;
	}
	public String getEditLink() {
		return editLink;
	}
	public void setEditLink(String editLink) {
		this.editLink = editLink;
	}
	public Long getExportReceivingFacilityUid() {
		return exportReceivingFacilityUid;
	}
	public void setExportReceivingFacilityUid(Long exportReceivingFacilityUid) {
		this.exportReceivingFacilityUid = exportReceivingFacilityUid;
	}
	public ExportTriggerDT getExTrDT() {
		return exTrDT;
	}
	public void setExTrDT(ExportTriggerDT exTrDT) {
		this.exTrDT = exTrDT;
	}
	
	public Set<Object> getExTrDTset() {
		return exTrDTset;
	}
	public void setExTrDTset(Set<Object> exTrDTset) {
		this.exTrDTset = exTrDTset;
	}
	public ArrayList<Object>  getTriggerDTList() {
		return triggerDTList;
	}
	public void setTriggerDTList(ArrayList<Object>  triggerDTList) {
		this.triggerDTList = triggerDTList;
	}
	public String getRecordStatusCdDescTxt() {
		return recordStatusCdDescTxt;
	}
	public void setRecordStatusCdDescTxt(String recordStatusCdDescTxt) {
		this.recordStatusCdDescTxt = recordStatusCdDescTxt;
	}
	public String getLevelOfReviewDescTxt() {
		return levelOfReviewDescTxt;
	}
	public void setLevelOfReviewDescTxt(String levelOfReviewDescTxt) {
		this.levelOfReviewDescTxt = levelOfReviewDescTxt;
	}
	public String getDocumentTypeDescTxt() {
		return documentTypeDescTxt;
	}
	public void setDocumentTypeDescTxt(String documentTypeDescTxt) {
		this.documentTypeDescTxt = documentTypeDescTxt;
	}
	public String getReceivingSystemDescTxt() {
		return receivingSystemDescTxt;
	}
	public void setReceivingSystemDescTxt(String receivingSystemDescTxt) {
		this.receivingSystemDescTxt = receivingSystemDescTxt;
	}
	
	
	
		

}
