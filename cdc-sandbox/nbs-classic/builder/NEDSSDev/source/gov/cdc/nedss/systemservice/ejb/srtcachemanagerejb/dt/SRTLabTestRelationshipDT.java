package gov.cdc.nedss.systemservice.ejb.srtcachemanagerejb.dt;
/**
 * <p>Title: SRTLabTestRelationshipDT</p>
 * <p>Description: DT that holds data from the labtest_relationship table.</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Computer Sciences Corp.</p>
 * @author Mark Hankey
 * @version 1.0
 */

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import java.sql.Timestamp;


public class SRTLabTestRelationshipDT implements RootDTInterface {

  /*ordered attributes. */
  private String orderedLaboratoryId;
  private String orderedLabTestCd;
  private String orderedLabTestDescTxt;

  /*resulted attributes */
  private String resultLaboratoryId;
  private String resultLabTestCd;
  private String resultLabTestDescTxt;
  private String resultTestTypeCd;
  private Long indentLevel;


  private boolean isNew;
  private boolean isDirty;
  private boolean isDelete;

  public SRTLabTestRelationshipDT() {
  }

  public String getOrderedLabTestCd(){
    return orderedLabTestCd;
  }
  public void setOrderedLabTestCd(String aTestCd){
    orderedLabTestCd = aTestCd;
  }
  public String getOrderedLabTestDescTxt(){
    return orderedLabTestDescTxt;
  }
  public void setOrderedLabTestDescTxt(String aOtLabTestDescTxt){
    orderedLabTestDescTxt = aOtLabTestDescTxt;
  }

  public String getResultLabTestCd(){
    return resultLabTestCd;
  }
  public void setResultLabTestCd(String aTestCd){
    resultLabTestCd = aTestCd;
  }
  public Long getIndentLevel(){
   return indentLevel;
 }
 public void setIndentLevel(Long aIndentLevel){
   indentLevel = aIndentLevel;
 }
 public void setIndentLevel(Integer aIndentLevel)
 {
   indentLevel = new Long(aIndentLevel.longValue());
   setItDirty(true);
 }


  public String getResultLabTestDescTxt(){
    return resultLabTestDescTxt;
  }
  public void setResultLabTestDescTxt(String artLabTestDescTxt){
    resultLabTestDescTxt = artLabTestDescTxt;
  }

  public void setOrderedLaboratoryId(String aLabId){
    orderedLaboratoryId = aLabId;
  }
  public String getOrderedLaboratoryId(){
    return orderedLaboratoryId;
  }

  public void setResultLaboratoryId(String aLabId){
    resultLaboratoryId = aLabId;
  }

  public String getResultLaboratoryId(){
    return resultLaboratoryId;
  }

  public void setResultTestTypeCd(String aTypeCd){
    resultTestTypeCd = aTypeCd;
  }

  public String getResultTestTypeCd(){
    return resultTestTypeCd;
  }

  public Long getLastChgUserId() {
    /**@todo Implement this gov.cdc.nedss.systemservice.util.RootDTInterface method*/
    throw new java.lang.UnsupportedOperationException("Method getLastChgUserId() not yet implemented.");
  }
  public void setLastChgUserId(Long aLastChgUserId) {
    /**@todo Implement this gov.cdc.nedss.systemservice.util.RootDTInterface method*/
    throw new java.lang.UnsupportedOperationException("Method setLastChgUserId() not yet implemented.");
  }
  public String getJurisdictionCd() {
    /**@todo Implement this gov.cdc.nedss.systemservice.util.RootDTInterface method*/
    throw new java.lang.UnsupportedOperationException("Method getJurisdictionCd() not yet implemented.");
  }
  public void setJurisdictionCd(String aJurisdictionCd) {
    /**@todo Implement this gov.cdc.nedss.systemservice.util.RootDTInterface method*/
    throw new java.lang.UnsupportedOperationException("Method setJurisdictionCd() not yet implemented.");
  }
  public String getProgAreaCd() {
    /**@todo Implement this gov.cdc.nedss.systemservice.util.RootDTInterface method*/
    throw new java.lang.UnsupportedOperationException("Method getProgAreaCd() not yet implemented.");
  }
  public void setProgAreaCd(String aProgAreaCd) {
    /**@todo Implement this gov.cdc.nedss.systemservice.util.RootDTInterface method*/
    throw new java.lang.UnsupportedOperationException("Method setProgAreaCd() not yet implemented.");
  }
  public Timestamp getLastChgTime() {
    /**@todo Implement this gov.cdc.nedss.systemservice.util.RootDTInterface method*/
    throw new java.lang.UnsupportedOperationException("Method getLastChgTime() not yet implemented.");
  }
  public void setLastChgTime(Timestamp aLastChgTime) {
    /**@todo Implement this gov.cdc.nedss.systemservice.util.RootDTInterface method*/
    throw new java.lang.UnsupportedOperationException("Method setLastChgTime() not yet implemented.");
  }
  public String getLocalId() {
    /**@todo Implement this gov.cdc.nedss.systemservice.util.RootDTInterface method*/
    throw new java.lang.UnsupportedOperationException("Method getLocalId() not yet implemented.");
  }
  public void setLocalId(String aLocalId) {
    /**@todo Implement this gov.cdc.nedss.systemservice.util.RootDTInterface method*/
    throw new java.lang.UnsupportedOperationException("Method setLocalId() not yet implemented.");
  }
  public Long getAddUserId() {
    /**@todo Implement this gov.cdc.nedss.systemservice.util.RootDTInterface method*/
    throw new java.lang.UnsupportedOperationException("Method getAddUserId() not yet implemented.");
  }
  public void setAddUserId(Long aAddUserId) {
    /**@todo Implement this gov.cdc.nedss.systemservice.util.RootDTInterface method*/
    throw new java.lang.UnsupportedOperationException("Method setAddUserId() not yet implemented.");
  }
  public String getLastChgReasonCd() {
    /**@todo Implement this gov.cdc.nedss.systemservice.util.RootDTInterface method*/
    throw new java.lang.UnsupportedOperationException("Method getLastChgReasonCd() not yet implemented.");
  }
  public void setLastChgReasonCd(String aLastChgReasonCd) {
    /**@todo Implement this gov.cdc.nedss.systemservice.util.RootDTInterface method*/
    throw new java.lang.UnsupportedOperationException("Method setLastChgReasonCd() not yet implemented.");
  }
  public String getRecordStatusCd() {
    /**@todo Implement this gov.cdc.nedss.systemservice.util.RootDTInterface method*/
    throw new java.lang.UnsupportedOperationException("Method getRecordStatusCd() not yet implemented.");
  }
  public void setRecordStatusCd(String aRecordStatusCd) {
    /**@todo Implement this gov.cdc.nedss.systemservice.util.RootDTInterface method*/
    throw new java.lang.UnsupportedOperationException("Method setRecordStatusCd() not yet implemented.");
  }
  public Timestamp getRecordStatusTime() {
    /**@todo Implement this gov.cdc.nedss.systemservice.util.RootDTInterface method*/
    throw new java.lang.UnsupportedOperationException("Method getRecordStatusTime() not yet implemented.");
  }
  public void setRecordStatusTime(Timestamp aRecordStatusTime) {
    /**@todo Implement this gov.cdc.nedss.systemservice.util.RootDTInterface method*/
    throw new java.lang.UnsupportedOperationException("Method setRecordStatusTime() not yet implemented.");
  }
  public String getStatusCd() {
    /**@todo Implement this gov.cdc.nedss.systemservice.util.RootDTInterface method*/
    throw new java.lang.UnsupportedOperationException("Method getStatusCd() not yet implemented.");
  }
  public void setStatusCd(String aStatusCd) {
    /**@todo Implement this gov.cdc.nedss.systemservice.util.RootDTInterface method*/
    throw new java.lang.UnsupportedOperationException("Method setStatusCd() not yet implemented.");
  }
  public Timestamp getStatusTime() {
    /**@todo Implement this gov.cdc.nedss.systemservice.util.RootDTInterface method*/
    throw new java.lang.UnsupportedOperationException("Method getStatusTime() not yet implemented.");
  }
  public void setStatusTime(Timestamp aStatusTime) {
    /**@todo Implement this gov.cdc.nedss.systemservice.util.RootDTInterface method*/
    throw new java.lang.UnsupportedOperationException("Method setStatusTime() not yet implemented.");
  }
  public String getSuperclass() {
    /**@todo Implement this gov.cdc.nedss.systemservice.util.RootDTInterface method*/
    throw new java.lang.UnsupportedOperationException("Method getSuperclass() not yet implemented.");
  }
  public Long getUid() {
    /**@todo Implement this gov.cdc.nedss.systemservice.util.RootDTInterface method*/
    throw new java.lang.UnsupportedOperationException("Method getUid() not yet implemented.");
  }
  public void setAddTime(Timestamp aAddTime) {
    /**@todo Implement this gov.cdc.nedss.systemservice.util.RootDTInterface method*/
    throw new java.lang.UnsupportedOperationException("Method setAddTime() not yet implemented.");
  }
  public Timestamp getAddTime() {
    /**@todo Implement this gov.cdc.nedss.systemservice.util.RootDTInterface method*/
    throw new java.lang.UnsupportedOperationException("Method getAddTime() not yet implemented.");
  }
  public boolean isItNew() {
   return isNew;
  }
  public void setItNew(boolean itNew) {
    isNew = itNew;
  }
  public boolean isItDirty() {
   return isDirty;
  }
  public void setItDirty(boolean itDirty) {
    isDirty = itDirty;
  }
  public boolean isItDelete() {
    return isDelete;
  }
  public void setItDelete(boolean itDelete) {
   isDelete = itDelete;
  }
  public Long getProgramJurisdictionOid() {
    /**@todo Implement this gov.cdc.nedss.systemservice.util.RootDTInterface method*/
    throw new java.lang.UnsupportedOperationException("Method getProgramJurisdictionOid() not yet implemented.");
  }
  public void setProgramJurisdictionOid(Long aProgramJurisdictionOid) {
    /**@todo Implement this gov.cdc.nedss.systemservice.util.RootDTInterface method*/
    throw new java.lang.UnsupportedOperationException("Method setProgramJurisdictionOid() not yet implemented.");
  }
  public String getSharedInd() {
    /**@todo Implement this gov.cdc.nedss.systemservice.util.RootDTInterface method*/
    throw new java.lang.UnsupportedOperationException("Method getSharedInd() not yet implemented.");
  }
  public void setSharedInd(String aSharedInd) {
    /**@todo Implement this gov.cdc.nedss.systemservice.util.RootDTInterface method*/
    throw new java.lang.UnsupportedOperationException("Method setSharedInd() not yet implemented.");
  }
  public Integer getVersionCtrlNbr() {
    /**@todo Implement this gov.cdc.nedss.systemservice.util.RootDTInterface method*/
    throw new java.lang.UnsupportedOperationException("Method getVersionCtrlNbr() not yet implemented.");
  }

}