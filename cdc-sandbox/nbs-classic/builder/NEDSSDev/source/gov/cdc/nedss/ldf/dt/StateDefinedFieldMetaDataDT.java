//Source file: C:\\All NEDSS Related\\scratch\\gov\\gov\\cdc\\nedss\\ldf\\dt\\StateDefinedFieldMetaDataDT.java

package gov.cdc.nedss.ldf.dt;
import gov.cdc.nedss.util.AbstractVO;
import gov.cdc.nedss.systemservice.util.RootDTInterface;
import java.sql.Timestamp;
import java.util.*;

public class StateDefinedFieldMetaDataDT
	extends AbstractVO
	implements RootDTInterface, Comparable {
	private Long ldfUid;
	private String activeInd;
	private String adminComment;
	private String businessObjNm;
	private String categoryType;
	private String cdcNationalId;
	private String classCd;
	private String codeSetNm;
	private String conditionCd;
	private String conditionDescTxt;
	private String dataType;
	private Integer displayOrderNbr;
	private String fieldSize;
	private String labelTxt;
	private String validationTxt;
	private String requiredInd;
	private String ldfPageId;
	private String validationJscriptTxt;
	private Timestamp addTime;
	private String deploymentCd;
	private String stateCd;
	private Timestamp recordStatusTime;
	private String recordStatusCd;
	private Long customSubformMetadataUid;
	private String htmlTag;
	private Long importVersionNbr;
	private String nndInd;
	private String ldfOid;
	private Integer versionCtrlNbr;
	private String nbsQuestionInd;  //Indicator that this DT represents data from the updated NBS Question/Answer structure

	// these are fields that are not in the database table,
	// but are referenced in our codebase.  leave them in for now.
	// we will clean them up later.  Shannon Zheng 1/12/2004
	private String statusCd;
	private String cachedCodeSetNm;
	private String cachedDataType;
	private String javaScriptFunctionName;
	private Timestamp lastChgTime;

	/**
	 * @roseuid 3F4E4B250169
	 */
	public StateDefinedFieldMetaDataDT() {

	}

	/**
	 * Access method for the ldfUid property.
	 *
	 * @return   the current value of the ldfUid property
	 */
	public Long getLdfUid() {
		return ldfUid;
	}

	/**
	 * Sets the value of the ldfUid property.
	 *
	 * @param aLdfUid the new value of the ldfUid property
	 */
	public void setLdfUid(Long aLdfUid) {
		ldfUid = aLdfUid;
	}

	public void setLdfPageId(String aPageId) {
		ldfPageId = aPageId;
	}
	public String getLdfPageId() {
		return ldfPageId;
	}
	/**
	 * Access method for the activeInd property.
	 *
	 * @return   the current value of the activeInd property
	 */
	public String getActiveInd() {
		return activeInd;
	}

	/**
	 * Sets the value of the activeInd property.
	 *
	 * @param aActiveInd the new value of the activeInd property
	 */
	public void setActiveInd(String aActiveInd) {
		activeInd = aActiveInd;
	}

	/**
	 * Access method for the adminComment property.
	 *
	 * @return   the current value of the adminComment property
	 */
	public String getAdminComment() {
		return adminComment;
	}

	/**
	 * Sets the value of the adminComment property.
	 *
	 * @param aAdminComment the new value of the adminComment property
	 */
	public void setAdminComment(String aAdminComment) {
		adminComment = aAdminComment;
	}

	/**
	 * Access method for the businessObjNm property.
	 *
	 * @return   the current value of the businessObjNm property
	 */
	public String getBusinessObjNm() {
		return businessObjNm;
	}

	/**
	 * Sets the value of the businessObjNm property.
	 *
	 * @param aBusinessObjNm the new value of the businessObjNm property
	 */
	public void setBusinessObjNm(String aBusinessObjNm) {
		businessObjNm = aBusinessObjNm;
	}

	/**
	 * Access method for the categoryType property.
	 *
	 * @return   the current value of the categoryType property
	 */
	public String getCategoryType() {
		return categoryType;
	}

	/**
	 * Sets the value of the categoryType property.
	 *
	 * @param aCategoryType the new value of the categoryType property
	 */
	public void setCategoryType(String aCategoryType) {
		categoryType = aCategoryType;
	}

	/**
	 * Access method for the cdcNationalId property.
	 *
	 * @return   the current value of the cdcNationalId property
	 */
	public String getCdcNationalId() {
		return cdcNationalId;
	}

	/**
	 * Sets the value of the cdcNationalId property.
	 *
	 * @param aCdcNationalId the new value of the cdcNationalId property
	 */
	public void setCdcNationalId(String aCdcNationalId) {
		cdcNationalId = aCdcNationalId;
	}

	/**
	 * Access method for the classCd property.
	 *
	 * @return   the current value of the classCd property
	 */
	public String getClassCd() {
		return classCd;
	}

	/**
	 * Sets the value of the classCd property.
	 *
	 * @param aClassCd the new value of the classCd property
	 */
	public void setClassCd(String aClassCd) {
		classCd = aClassCd;
	}

	/**
	 * Access method for the codeSetNm property.
	 *
	 * @return   the current value of the codeSetNm property
	 */
	public String getCodeSetNm() {
		return codeSetNm;
	}

	/**
	 * Sets the value of the codeSetNm property.
	 *
	 * @param aCodeSetNm the new value of the codeSetNm property
	 */
	public void setCodeSetNm(String aCodeSetNm) {
		codeSetNm = aCodeSetNm;
	}

	/**
	 * Access method for the conditionCd property.
	 *
	 * @return   the current value of the conditionCd property
	 */
	public String getConditionCd() {
		return conditionCd;
	}

	/**
	 * Sets the value of the conditionCd property.
	 *
	 * @param aConditionCd the new value of the conditionCd property
	 */
	public void setConditionCd(String aConditionCd) {
		conditionCd = aConditionCd;
	}

	/**
	 * Access method for the conditionDescTxt property.
	 *
	 * @return   the current value of the conditionDescTxt property
	 */
	public String getConditionDescTxt() {
		return conditionDescTxt;
	}

	/**
	 * Sets the value of the conditionDescTxt property.
	 *
	 * @param aConditionDescTxt the new value of the conditionDescTxt property
	 */
	public void setConditionDescTxt(String aConditionDescTxt) {
		conditionDescTxt = aConditionDescTxt;
	}

	/**
	 * Access method for the dataType property.
	 *
	 * @return   the current value of the dataType property
	 */
	public String getDataType() {
		return dataType;
	}

	/**
	 * Sets the value of the dataType property.
	 *
	 * @param aDataType the new value of the dataType property
	 */
	public void setDataType(String aDataType) {
		dataType = aDataType;
	}
	/**
	 * Access method for the displayOrderNbr property.
	 *
	 * @return   the current value of the displayOrderNbr property
	 */
	public Integer getDisplayOrderNbr() {
		return displayOrderNbr;
	}

	/**
	 * Sets the value of the displayOrderNbr property.
	 *
	 * @param aDisplayOrderNbr the new value of the displayOrderNbr property
	 */
	public void setDisplayOrderNbr(Integer aDisplayOrderNbr) {
		displayOrderNbr = aDisplayOrderNbr;
	}

	/**
	 * Access method for the fieldSize property.
	 *
	 * @return   the current value of the fieldSize property
	 */
	public String getFieldSize() {
		return fieldSize;
	}

	/**
	 * Sets the value of the fieldSize property.
	 *
	 * @param aFieldSize the new value of the fieldSize property
	 */
	public void setFieldSize(String aFieldSize) {
		fieldSize = aFieldSize;
	}

	/**
	 * Access method for the labelTxt property.
	 *
	 * @return   the current value of the labelTxt property
	 */
	public String getLabelTxt() {
		return labelTxt;
	}

	/**
	 * Sets the value of the labelTxt property.
	 *
	 * @param aLabelTxt the new value of the labelTxt property
	 */
	public void setLabelTxt(String aLabelTxt) {
		labelTxt = aLabelTxt;
	}

	/**
	 * Access method for the validationTxt property.
	 *
	 * @return   the current value of the validationTxt property
	 */
	public String getValidationTxt() {
		return validationTxt;
	}

	/**
	 * Sets the value of the validationTxt property.
	 *
	 * @param aValidationTxt the new value of the validationTxt property
	 */
	public void setValidationTxt(String aValidationTxt) {
		validationTxt = aValidationTxt;
	}
	/**
	* Access method for the versionCtrlNbr property.
	*
	* @return   the current value of the versionCtrlNbr property
	*/
	public Integer getVersionCtrlNbr() {
		return versionCtrlNbr;
	}

	/**
	 * Sets the value of the versionCtrlNbr property.
	 *
	 * @param aVersionCtrlNbr the new value of the versionCtrlNbr property
	 */
	public void setVersionCtrlNbr(Integer aVersionCtrlNbr) {
		versionCtrlNbr = aVersionCtrlNbr;
	}

	/**
	* @param itDirty
	* @roseuid 3E3040CB01C4
	*/
	public void setItDirty(boolean itDirty) {
		this.itDirty = itDirty;
	}

	/**
	* @return boolean
	* @roseuid 3E3040CB01E2
	*/
	public boolean isItDirty() {
		return itDirty;
	}

	/**
	* @param itNew
	* @roseuid 3E3040CB01F6
	*/
	public void setItNew(boolean itNew) {
		this.itNew = itNew;
	}

	/**
	* @return boolean
	* @roseuid 3E3040CB0214
	*/
	public boolean isItNew() {
		return itNew;
	}

	/**
	* @param itDelete
	* @roseuid 3E3040CB021E
	*/
	public void setItDelete(boolean itDelete) {
		this.itDelete = itDelete;
	}

	/**
	* @return boolean
	* @roseuid 3E3040CB023C
	*/
	public boolean isItDelete() {
		return itDelete;
	}
	/**
	* @param objectname1
	* @param objectname2
	* @param voClass
	* @return boolean
	* @roseuid 3E3040CB0188
	*/
	public boolean isEqual(
		Object objectname1,
		Object objectname2,
		Class<?> voClass) {
		return true;
	}

	/**
	* @param aSharedInd
	* @roseuid 3E3046570338
	*/
	public void setSharedInd(String aSharedInd) {

	}
	/**
	* @return java.lang.String
	* @roseuid 3E3046570324
	*/
	public String getSharedInd() {
		return null;
	}

	/**
	* @return java.lang.Long
	* @roseuid 3E30465702E8
	*/
	public Long getProgramJurisdictionOid() {
		return null;
	}

	/**
	* @param aProgramJurisdictionOid
	* @roseuid 3E30465702FC
	*/
	public void setProgramJurisdictionOid(Long aProgramJurisdictionOid) {

	}
	/**
	 * @return java.lang.Long
	 * @roseuid 3E30465702CA
	 */
	public Long getUid() {
		return null;
	}

	/**
	 * @return java.lang.String
	 * @roseuid 3E30465702B6
	 */
	public String getSuperclass() {
		return null;
	}

	/**
	 * @return java.sql.Timestamp
	 * @roseuid 3E304657027A
	 */
	public Timestamp getStatusTime() {
		return null;
	}

	/**
	 * @param aStatusTime
	 * @roseuid 3E304657028E
	 */
	public void setStatusTime(Timestamp aStatusTime) {

	}

	/**
	* @return java.sql.Timestamp
	* @roseuid 3E3046570202
	*/
	public Timestamp getRecordStatusTime() {
		return recordStatusTime;
	}

	/**
	* @param aRecordStatusTime
	* @roseuid 3E3046570216
	*/
	public void setRecordStatusTime(Timestamp recordStatusTime) {
		this.recordStatusTime = recordStatusTime;
	}
	/**
	* @return java.lang.String
	* @roseuid 3E30465701C5
	*/
	public String getRecordStatusCd() {
		return this.recordStatusCd;
	}

	/**
	* @param aRecordStatusCd
	* @roseuid 3E30465701DA
	*/
	public void setRecordStatusCd(String recordStatusCd) {
		this.recordStatusCd = recordStatusCd;
	}
	/**
	* @return java.lang.String
	* @roseuid 3E3046570193
	*/
	public String getLastChgReasonCd() {
		return null;
	}

	/**
	* @param aLastChgReasonCd
	* @roseuid 3E30465701A7
	*/
	public void setLastChgReasonCd(String aLastChgReasonCd) {

	}

	/**
	* @return java.lang.Long
	* @roseuid 3E3046570085
	*/
	public Long getLastChgUserId() {
		return null;
	}

	/**
	* @param aLastChgUserId
	* @roseuid 3E3046570099
	*/
	public void setLastChgUserId(Long aLastChgUserId) {

	}
	/**
	* @return java.lang.Long
	* @roseuid 3E3046570157
	*/
	public Long getAddUserId() {
		return null;
	}

	/**
	* @param aAddUserId
	* @roseuid 3E304657016B
	*/
	public void setAddUserId(Long aAddUserId) {

	}
	/**
	* @return java.lang.String
	* @roseuid 3E3046570125
	*/
	public String getLocalId() {
		return null;
	}

	/**
	* @param aLocalId
	* @roseuid 3E3046570139
	*/
	public void setLocalId(String aLocalId) {

	}
	/**
	* @return java.lang.String
	* @roseuid 3E30465700E9
	*/
	public String getProgAreaCd() {
		return null;
	}

	/**
	* @param aProgAreaCd
	* @roseuid 3E30465700FD
	*/
	public void setProgAreaCd(String aProgAreaCd) {

	}
	/**
	 * @return java.lang.String
	 * @roseuid 3E30465700B7
	 */
	public String getJurisdictionCd() {
		return null;
	}

	/**
	 * @param aJurisdictionCd
	 * @roseuid 3E30465700CB
	 */
	public void setJurisdictionCd(String aJurisdictionCd) {

	}
	/**
	 * @return java.lang.String
	 */
	public String getValidationJscriptTxt() {
		return validationJscriptTxt;
	}

	/**
	 * @param aJurisdictionCd
	 */
	public void setValidationJscriptTxt(String aValidationJscriptTxt) {
		validationJscriptTxt = aValidationJscriptTxt;
	}

	/**
	  * Access method for the addTime property.
	  *
	  * @return   the current value of the addTime property
	  */
	public Timestamp getAddTime() {
		return addTime;
	}

	/**
	 * Sets the value of the addTime property.
	 *
	 * @param aAddTime the new value of the addTime property
	 */
	public void setAddTime(Timestamp aAddTime) {
		addTime = aAddTime;
	}

	/**
	* Access method for the requiredInd property.
	*
	* @return   the current value of the requiredInd property
	*/
	public String getRequiredInd() {
		return requiredInd;
	}

	/**
	 * Sets the value of the requiredInd property.
	 *
	 * @param aRequiredInd the new value of the requiredInd property
	 */
	public void setRequiredInd(String aRequiredInd) {
		requiredInd = aRequiredInd;
	}

	/**
	* Access method for the deploymentCd property.
	*
	* @return   the current value of the deploymentCd property
	*/
	public String getDeploymentCd() {
		return deploymentCd;
	}

	/**
	 * Sets the value of the deploymentCd property.
	 *
	 * @param aDeploymentCd the new value of the deploymentCd property
	 */
	public void setDeploymentCd(String aDeploymentCd) {
		deploymentCd = aDeploymentCd;
	}

	/**
	 * Access method for the stateCd property.
	 *
	 * @return   the current value of the stateCd property
	 */
	public String getStateCd() {
		return stateCd;
	}

	/**
	 * Sets the value of the stateCd property.
	 *
	 * @param aRequiredInd the new value of the stateCd property
	 */
	public void setStateCd(String aStateCd) {
		stateCd = aStateCd;
	}

	public boolean itDirty() {
		return itDirty;
	}

	public int compareTo(Object o) {
		if (getDisplayOrderNbr()
			.compareTo(((StateDefinedFieldMetaDataDT) o).getDisplayOrderNbr())
			== 0)
			return getLabelTxt().compareTo(
				((StateDefinedFieldMetaDataDT) o).getLabelTxt());
		else
			return getDisplayOrderNbr().compareTo(
				((StateDefinedFieldMetaDataDT) o).getDisplayOrderNbr());

		// return getLdfUid().compareTo( ((StateDefinedFieldMetaDataDT) o).getLdfUid() );
	}
	/**
	 * @return
	 */
	public Long getCustomSubformMetadataUid() {
		return customSubformMetadataUid;
	}

	/**
	 * @return
	 */
	public String getHtmlTag() {
		return htmlTag;
	}

	/**
	 * @return
	 */
	public Long getImportVersionNbr() {
		return importVersionNbr;
	}

	/**
	 * @return
	 */
	public String getLdfOid() {
		return ldfOid;
	}

	/**
	 * @return
	 */
	public String getNndInd() {
		return nndInd;
	}

	/**
	 * @param long1
	 */
	public void setCustomSubformMetadataUid(Long long1) {
		customSubformMetadataUid = long1;
	}

	/**
	 * @param string
	 */
	public void setHtmlTag(String string) {
		htmlTag = string;
	}

	/**
	 * @param integer
	 */
	public void setImportVersionNbr(Long aImportVersionNbr) {
		importVersionNbr = aImportVersionNbr;
	}

	/**
	 * @param string
	 */
	public void setLdfOid(String string) {
		ldfOid = string;
	}

	/**
	 * @param string
	 */
	public void setNndInd(String string) {
		nndInd = string;
	}

	/**
	 * @return
	 */
	public String getCachedCodeSetNm() {
		return cachedCodeSetNm;
	}

	/**
	 * @return
	 */
	public String getCachedDataType() {
		return cachedDataType;
	}

	/**
	 * @return
	 */
	public String getJavaScriptFunctionName() {
		return javaScriptFunctionName;
	}

	/**
	 * @return
	 */
	public Timestamp getLastChgTime() {
		return lastChgTime;
	}

	/**
	 * @return
	 */
	public String getStatusCd() {
		return statusCd;
	}

	/**
	 * @param string
	 */
	public void setCachedCodeSetNm(String string) {
		cachedCodeSetNm = string;
	}

	/**
	 * @param string
	 */
	public void setCachedDataType(String string) {
		cachedDataType = string;
	}

	/**
	 * @param string
	 */
	public void setJavaScriptFunctionName(String string) {
		javaScriptFunctionName = string;
	}

	/**
	 * @param timestamp
	 */
	public void setLastChgTime(Timestamp timestamp) {
		lastChgTime = timestamp;
	}

	/**
	 * @param string
	 */
	public void setStatusCd(String string) {
		statusCd = string;
	}

	public String getNbsQuestionInd() {
		return nbsQuestionInd;
	}

	public void setNbsQuestionInd(String nbsQuestionInd) {
		this.nbsQuestionInd = nbsQuestionInd;
	}
	
}
