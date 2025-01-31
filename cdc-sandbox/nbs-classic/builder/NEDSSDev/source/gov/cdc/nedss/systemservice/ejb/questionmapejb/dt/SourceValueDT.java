package gov.cdc.nedss.systemservice.ejb.questionmapejb.dt;

import gov.cdc.nedss.util.AbstractVO;

public class SourceValueDT extends AbstractVO {
	
	private Long sourceValueUid;
	private String sourceValue;
	private String operatorTypeCode;
	private Long operatorTypeUid;
	private Long sourceFieldUid;
	private String viewLink;
	private String editLink;
	private String deleteLink;
	private String operatorTypeDesc;

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

	public String getDeleteLink() {
		return deleteLink;
	}

	public void setDeleteLink(String deleteLink) {
		this.deleteLink = deleteLink;
	}

	public String getOperatorTypeDesc() {
		return operatorTypeDesc;
	}

	public void setOperatorTypeDesc(String operatorTypeDesc) {
		this.operatorTypeDesc = operatorTypeDesc;
	}

	public boolean isEqual(Object objectname1, Object objectname2, Class<?> voClass) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isItDelete() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isItDirty() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isItNew() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setItDelete(boolean itDelete) {
		// TODO Auto-generated method stub
		
	}

	public void setItDirty(boolean itDirty) {
		// TODO Auto-generated method stub
		
	}

	public void setItNew(boolean itNew) {
		// TODO Auto-generated method stub
		
	}

	public Long getSourceValueUid() {
		return sourceValueUid;
	}

	public void setSourceValueUid(Long sourceValueUid) {
		this.sourceValueUid = sourceValueUid;
	}

	public String getSourceValue() {
		return sourceValue;
	}

	public void setSourceValue(String sourceValue) {
		this.sourceValue = sourceValue;
	}

	public String getOperatorTypeCode() {
		return operatorTypeCode;
	}

	public void setOperatorTypeCode(String operatorTypeCode) {
		this.operatorTypeCode = operatorTypeCode;
	}

	public Long getSourceFieldUid() {
		return sourceFieldUid;
	}

	public void setSourceFieldUid(Long sourceFieldUid) {
		this.sourceFieldUid = sourceFieldUid;
	}

	public Long getOperatorTypeUid() {
		return operatorTypeUid;
	}

	public void setOperatorTypeUid(Long operatorTypeUid) {
		this.operatorTypeUid = operatorTypeUid;
	}
	
	
	

}
