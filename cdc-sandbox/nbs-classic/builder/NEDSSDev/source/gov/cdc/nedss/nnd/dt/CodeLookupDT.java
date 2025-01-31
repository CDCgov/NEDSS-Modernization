

package gov.cdc.nedss.nnd.dt;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

import java.lang.reflect.Field;
import java.sql.Timestamp;

/**
* Name:		CodeLookupDT.java
* Description:	VO used to retrieve code_set_nm and class_cd from SRT CodeSet table or
*               to use translation table to look up NND Standardized code 
* Copyright:	Copyright (c) 2008
* Company: 	Computer Sciences Corporation
* @author	Beau Bannerman
*/
public class CodeLookupDT extends AbstractVO implements RootDTInterface
{
	private static final long serialVersionUID = 1L;

	private String codeSetGroupId;
	private String codeSetNm;
	private String classCd;
	private String codedValue;
	private String codedValueDescription;
	private String codedValueCodingSystem;
	private String codedValueCodingSystemDescTxt;
	private String localCodedValue;
	private String localCodedValueDescription;
	private String localCodedValueCodingSystem;
	private String localCodedValueCodingSystemDescTxt;
	private String descTxt;

	/**
	 * @return the codeSetGroupId
	 */
	public String getCodeSetGroupId() {
		return codeSetGroupId;
	}

	/**
	 * @param codeSetGroupId the codeSetGroupId to set
	 */
	public void setCodeSetGroupId(String codeSetGroupId) {
		this.codeSetGroupId = codeSetGroupId;
	}

	/**
	 * @return the codeSetNm
	 */
	public String getCodeSetNm() {
		return codeSetNm;
	}

	/**
	 * @param codeSetNm the codeSetNm to set
	 */
	public void setCodeSetNm(String codeSetNm) {
		this.codeSetNm = codeSetNm;
	}

	/**
	 * @return the classCd
	 */
	public String getClassCd() {
		return classCd;
	}

	/**
	 * @param classCd the classCd to set
	 */
	public void setClassCd(String classCd) {
		this.classCd = classCd;
	}

	/**
	 * @return the codedValue
	 */
	public String getCodedValue() {
		return codedValue;
	}

	/**
	 * @param codedValue the codedValue to set
	 */
	public void setCodedValue(String codedValue) {
		this.codedValue = codedValue;
	}

	/**
	 * @return the codedValueDescription
	 */
	public String getCodedValueDescription() {
		return codedValueDescription;
	}

	/**
	 * @param codedValueDescription the codedValueDescription to set
	 */
	public void setCodedValueDescription(String codedValueDescription) {
		this.codedValueDescription = codedValueDescription;
	}

	/**
	 * @return the codedValueCodingSystem
	 */
	public String getCodedValueCodingSystem() {
		return codedValueCodingSystem;
	}

	/**
	 * @param codedValueCodingSystem the codedValueCodingSystem to set
	 */
	public void setCodedValueCodingSystem(String codedValueCodingSystem) {
		this.codedValueCodingSystem = codedValueCodingSystem;
	}

	/**
	 * @return the localCodedValue
	 */
	public String getLocalCodedValue() {
		return localCodedValue;
	}

	/**
	 * @param localCodedValue the localCodedValue to set
	 */
	public void setLocalCodedValue(String localCodedValue) {
		this.localCodedValue = localCodedValue;
	}

	/**
	 * @return the localCodedValueDescription
	 */
	public String getLocalCodedValueDescription() {
		return localCodedValueDescription;
	}

	/**
	 * @param localCodedValueDescription the localCodedValueDescription to set
	 */
	public void setLocalCodedValueDescription(String localCodedValueDescription) {
		this.localCodedValueDescription = localCodedValueDescription;
	}

	/**
	 * @return the localCodedValueCodingSystem
	 */
	public String getLocalCodedValueCodingSystem() {
		return localCodedValueCodingSystem;
	}

	/**
	 * @param localCodedValueCodingSystem the localCodedValueCodingSystem to set
	 */
	public void setLocalCodedValueCodingSystem(String localCodedValueCodingSystem) {
		this.localCodedValueCodingSystem = localCodedValueCodingSystem;
	}
	
	//RootDTInterface methods that are not implemented in new code
	
	/**
	 * @return the addReasonCd
	 */
	public String getAddReasonCd() {
		return null;
	}
	/**
	 * @param addReasonCd the addReasonCd to set
	 */
	public void setAddReasonCd(String addReasonCd) {
	}
	/**
	 * @return the addTime
	 */
	public Timestamp getAddTime() {
		return null;
	}
	/**
	 * @param addTime the addTime to set
	 */
	public void setAddTime(Timestamp addTime) {
	}
	/**
	 * @return the addUserId
	 */
	public Long getAddUserId() {
		return null;
	}
	/**
	 * @param addUserId the addUserId to set
	 */
	public void setAddUserId(Long addUserId) {
	}
	/**
	 * @return the lastChgReasonCd
	 */
	public String getLastChgReasonCd() {
		return null;
	}
	/**
	 * @param lastChgReasonCd the lastChgReasonCd to set
	 */
	public void setLastChgReasonCd(String lastChgReasonCd) {
	}
	/**
	 * @return the lastChgTime
	 */
	public Timestamp getLastChgTime() {
		return null;
	}
	/**
	 * @param lastChgTime the lastChgTime to set
	 */
	public void setLastChgTime(Timestamp lastChgTime) {
	}
	/**
	 * @return the lastChguserId
	 */
	public Long getLastChgUserId() {
		return null;
	}
	/**
	 * @param lastChguserId the lastChguserId to set
	 */
	public void setLastChgUserId(Long lastChgUserId) {
	}

	
	public boolean isEqual(Object objectname1, Object objectname2, Class<?> voClass) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public String getJurisdictionCd() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setJurisdictionCd(String aJurisdictionCd) {
		// TODO Auto-generated method stub
		
	}
	public String getProgAreaCd() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setProgAreaCd(String aProgAreaCd) {
		// TODO Auto-generated method stub
		
	}
	public String getLocalId() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setLocalId(String aLocalId) {
		// TODO Auto-generated method stub
		
	}
	public String getStatusCd() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setStatusCd(String aStatusCd) {
		// TODO Auto-generated method stub
		
	}
	public Timestamp getStatusTime() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setStatusTime(Timestamp aStatusTime) {
		// TODO Auto-generated method stub
		
	}
	public String getSuperclass() {
		// TODO Auto-generated method stub
		return null;
	}
	public Long getUid() {
		// TODO Auto-generated method stub
		return null;
	}
	public boolean isItNew() {
		// TODO Auto-generated method stub
		return false;
	}
	public void setItNew(boolean itNew) {
		// TODO Auto-generated method stub
		
	}
	public boolean isItDirty() {
		// TODO Auto-generated method stub
		return false;
	}
	public void setItDirty(boolean itDirty) {
		// TODO Auto-generated method stub
		
	}
	public boolean isItDelete() {
		// TODO Auto-generated method stub
		return false;
	}
	public void setItDelete(boolean itDelete) {
		// TODO Auto-generated method stub
		
	}
	public Long getProgramJurisdictionOid() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setProgramJurisdictionOid(Long aProgramJurisdictionOid) {
		// TODO Auto-generated method stub
		
	}
	public String getSharedInd() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setSharedInd(String aSharedInd) {
		// TODO Auto-generated method stub
		
	}
	public Integer getVersionCtrlNbr() {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * @return the recordStatusCd
	 */
	public String getRecordStatusCd() {
		return null;
	}
	/**
	 * @param recordStatusCd the recordStatusCd to set
	 */
	public void setRecordStatusCd(String recordStatusCd) {
	}
	/**
	 * @return the recordStatusTime
	 */
	public Timestamp getRecordStatusTime() {
		return null;
	}
	/**
	 * @param recordStatusTime the recordStatusTime to set
	 */
	public void setRecordStatusTime(Timestamp recordStatusTime) {
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		try {
			sb.append(this.getClass().getName() + "\r\n");
			Field[] f = CodeLookupDT.class.getDeclaredFields();
			for (int i = 0; i < f.length; i++) {
				if (f[i] != null)
					sb.append(f[i].getName() + ":" + f[i].get(this) + "\r\n");
			}
		} catch (Exception ignore) {
		}
		return sb.toString();
	}

	public String getCodedValueCodingSystemDescTxt() {
		return codedValueCodingSystemDescTxt;
	}

	public void setCodedValueCodingSystemDescTxt(
			String codedValueCodingSystemDescTxt) {
		this.codedValueCodingSystemDescTxt = codedValueCodingSystemDescTxt;
	}

	public String getLocalCodedValueCodingSystemDescTxt() {
		return localCodedValueCodingSystemDescTxt;
	}

	public void setLocalCodedValueCodingSystemDescTxt(
			String localCodedValueCodingSystemDescTxt) {
		this.localCodedValueCodingSystemDescTxt = localCodedValueCodingSystemDescTxt;
	}

	public String getDescTxt() {
		return descTxt;
	}

	public void setDescTxt(String descTxt) {
		this.descTxt = descTxt;
	}
}

