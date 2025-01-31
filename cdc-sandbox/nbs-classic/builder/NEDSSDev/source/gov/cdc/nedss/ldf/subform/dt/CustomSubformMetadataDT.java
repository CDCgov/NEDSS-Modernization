package gov.cdc.nedss.ldf.subform.dt;

import java.sql.Timestamp;
import gov.cdc.nedss.systemservice.util.*;

public class CustomSubformMetadataDT
    implements RootDTInterface, java.io.Serializable {

  private Long customSubformMetadataUid;
  private Timestamp addTime;
  private String adminComment;
  private String businessObjectNm;
  private String classCd;
  private String conditionCd;
  private String conditionDescTxt;
  private Integer displayOrderNbr;
  private String htmlData;
  private Long importVersionNbr;
  private String deploymentCd;
  private String pageSetId;
  private String recordStatusCd;
  private Timestamp recordStatusTime;
  private String stateCd;
  private String statusCd;
  private String subformOid;
  private String subformNm;
  private Integer versionCtrlNbr;
  private Timestamp lastChgTime;

  private boolean itDirty = false;
  private boolean itNew = true;
  private boolean itDelete = false;

  /**
   *
   * @return
   */
  public Long getCustomSubformMetadataUid() {
    return this.customSubformMetadataUid;
  }

  /**
   *
   * @param subformUid
   */
  public void setCustomSubformMetadataUid(Long customSubformMetadataUid) {
    this.customSubformMetadataUid = customSubformMetadataUid;
  }

  /**
   *
   * @return
   */
  public String getSubformNm() {
    return this.subformNm;
  }

  /**
   *
   * @param subformName
   */
  public void setSubformNm(String subformName) {
    this.subformNm = subformName;
  }

  /**
   *
   * @return
   */

  public Integer getDisplayOrderNbr() {
    return this.displayOrderNbr;
  }

  /**
   *
   * @param displayOrderNbr
   */
  public void setDisplayOrderNbr(Integer displayOrderNbr) {
    this.displayOrderNbr = displayOrderNbr;
  }

  /**
   *
   * @return
   */
  public String getHtmlData() {
    return this.htmlData;
  }

  /**
   *
   * @param htmlData
   */
  public void setHtmlData(String htmlData) {
    this.htmlData = htmlData;
  }

  /**
   *
   * @return
   */
  public Long getImportVersionNbr() {
    return this.importVersionNbr;
  }

  /**
   *
   * @param importVersionNbr
   */
  public void setImportVersionNbr(Long importVersionNbr) {
    this.importVersionNbr = importVersionNbr;
  }
  /**
   *
   * @return
   */
  public String getAdminComment() {
    return this.adminComment;
  }

  /**
   *
   * @param adminComment
   */
  public void setAdminComment(String adminComment) {
    this.adminComment = adminComment;
  }

  /**
   *
   * @return
   */
  public String getBusinessObjectNm() {
    return this.businessObjectNm;
  }

  /**
   *
   * @param businessObjectNm
   */
  public void setBusinessObjectNm(String businessObjectNm) {
    this.businessObjectNm = businessObjectNm;
  }

  /**
   *
   * @return
   */

  public String getPageSetId() {
    return this.pageSetId;
  }

  /**
   *
   * @param pageSetId
   */
  public void setPageSetId(String pageSetId) {
    this.pageSetId = pageSetId;
  }

  /**
   *
   * @return
   */

  public String getConditionCd() {
    return this.conditionCd;
  }

  /**
   *
   * @param conditionCd
   */
  public void setConditionCd(String conditionCd) {
    this.conditionCd = conditionCd;
  }

  /**
   *
   * @return
   */
  public String getConditionDescTxt() {
    return this.conditionDescTxt;
  }

  /**
   *
   * @param conditionDescTxt
   */
  public void setConditionDescTxt(String conditionDescTxt) {
    this.conditionDescTxt = conditionDescTxt;
  }

  /**
   *
   * @return
   */
  public String getSubformOid() {
    return this.subformOid;
  }

  /**
   *
   * @param subformOid
   */
  public void setSubformOid(String subformOid) {
    this.subformOid = subformOid;
  }

  /**
   *
   * @return
   */
  public Timestamp getAddTime() {
    return this.addTime;
  }

  /**
   *
   * @param addTime
   */
  public void setAddTime(Timestamp addTime) {
    this.addTime = addTime;
  }

  public Timestamp getRecordStatusTime() {
    return this.recordStatusTime;
  }

  public void setRecordStatusTime(Timestamp aRecordStatusTime) {
    this.recordStatusTime = aRecordStatusTime;
  }

  /**
   *
   * @return
   */

  public String getRecordStatusCd() {
    return this.recordStatusCd;
  }

  /**
   *
   * @param recordStatusCd
   */
  public void setRecordStatusCd(String recordStatusCd) {
    this.recordStatusCd = recordStatusCd;
  }

  /**
   *
   * @return
   */
  public Integer getVersionCtrlNbr() {
    return this.versionCtrlNbr;
  }

  /**
   *
   * @param versionCtrlNbr
   */
  public void setVersionCtrlNbr(Integer versionCtrlNbr) {
    this.versionCtrlNbr = versionCtrlNbr;
  }

  /**
   *
   * @return
   */
  public String getStateCd() {
    return this.stateCd;
  }

  /**
   *
   * @param stateCd
   */
  public void setStateCd(String stateCd) {
    this.stateCd = stateCd;
  }

  /**
   *
   * @return
   */
  public String getDeploymentCd() {
    return this.deploymentCd;
  }

  /**
   *
   * @param deploymentCd
   */
  public void setDeploymentCd(String deploymentCd) {
    this.deploymentCd = deploymentCd;
  }

  /**
   *
   * @param itDirty
   */
  public void setItDirty(boolean itDirty) {
    this.itDirty = itDirty;
  }

  /**
   *
   * @return
   */
  public boolean isItDirty() {
    return itDirty;
  }

  /**
   *
   * @param itNew
   */
  public void setItNew(boolean itNew) {
    this.itNew = itNew;
  }

  /**
   *
   * @return
   */
  public boolean isItNew() {
    return itNew;
  }

  /**
   *
   * @param itDelete
   */
  public void setItDelete(boolean itDelete) {
    this.itDelete = itDelete;
  }

  /**
   *
   * @return
   */
  public boolean isItDelete() {
    return itDelete;
  }

  /**
   *
   * @param objectname1
   * @param objectname2
   * @param voClass
   * @return
   */
  public boolean isEqual(Object objectname1, Object objectname2, Class<?> voClass) {
    return true;
  }

  /**
   *
   * @param aSharedInd
   */
  public void setSharedInd(String aSharedInd) {

  }

  /**
   *
   * @return
   */
  public Long getUid() {
    return null;
  }

  /**
   *
   * @return
   */
  public String getSuperclass() {
    return null;
  }

  /**
   *
   * @return
   */
  public Timestamp getStatusTime() {
    return null;
  }

  /**
   *
   * @param aStatusTime
   */
  public void setStatusTime(Timestamp aStatusTime) {

  }

  /**
   *
   * @return
   */
  public String getStatusCd() {
    return this.statusCd;
  }

  /**
   *
   * @param aStatusCd
   */
  public void setStatusCd(String statusCd) {
    this.statusCd = statusCd;
  }

  /**
   *
   * @return
   */
  public String getLastChgReasonCd() {
    return null;
  }

  /**
   *
   * @param aLastChgReasonCd
   */
  public void setLastChgReasonCd(String aLastChgReasonCd) {

  }

  /**
   *
   * @return
   */
  public Long getLastChgUserId() {
    return null;
  }

  /**
   *
   * @param aLastChgUserId
   */
  public void setLastChgUserId(Long aLastChgUserId) {

  }

  /**
   *
   * @return
   */
  public Long getAddUserId() {
    return null;
  }

  /**
   *
   * @param aAddUserId
   */
  public void setAddUserId(Long aAddUserId) {

  }

  /**
   *
   * @return
   */
  public String getLocalId() {
    return null;
  }

  /**
   *
   * @param aLocalId
   */
  public void setLocalId(String aLocalId) {

  }

  /**
   *
   * @return
   */
  public String getProgAreaCd() {
    return null;
  }

  /**
   *
   * @param aProgAreaCd
   */
  public void setProgAreaCd(String aProgAreaCd) {

  }

  /**
   *
   * @return
   */
  public String getJurisdictionCd() {
    return null;
  }

  /**
   *
   * @param aJurisdictionCd
   */
  public void setJurisdictionCd(String aJurisdictionCd) {

  }

  public String getSharedInd() {
    return null;
  }

  /**
   *
   * @return
   */
  public Long getProgramJurisdictionOid() {
    return null;
  }

  /**
   *
   * @param aProgramJurisdictionOid
   */
  public void setProgramJurisdictionOid(Long aProgramJurisdictionOid) {

  }

  /**
   *
   * @return
   */
  public Timestamp getLastChgTime() {
    return lastChgTime;
  }

  /**
   *
   * @param aLastChgTime
   */
  public void setLastChgTime(Timestamp aLastChgTime) {
    lastChgTime = aLastChgTime;
  }

  /**
   *
   * @return
   */
  public String getClassCd() {
    return this.classCd;
  }

  /**
   *
   * @param classCd
   */
  public void setClassCd(String classCd) {
    this.classCd = classCd;
  }

}
