package gov.cdc.nedss.systemservice.ejb.questionmapejb.dt;

import gov.cdc.nedss.util.AbstractVO;

public class TargetValueDT extends AbstractVO{
	
	private Long targetFieldUid;
	private String targetValue;
	private String conseqIndCode;
	private String errorDescTxt;
	private Long targetValueUid;
	private String conseqIndtxt;
	private Long conseqIndUid;
	private Long errormessageUid;
	private String error_cd;
	private String viewLink;
	private String editLink;
	private String deleteLink;
	private String operatorTypeCode;
	private Long operatorTypeUid;
	private String operatorTypeDesc;
	
	public String getOperatorTypeDesc() {
		return operatorTypeDesc;
	}

	public void setOperatorTypeDesc(String operatorTypeDesc) {
		this.operatorTypeDesc = operatorTypeDesc;
	}

	public String getOperatorTypeCode() {
		return operatorTypeCode;
	}

	public void setOperatorTypeCode(String operatorTypeCode) {
		this.operatorTypeCode = operatorTypeCode;
	}

	public Long getOperatorTypeUid() {
		return operatorTypeUid;
	}

	public void setOperatorTypeUid(Long operatorTypeUid) {
		this.operatorTypeUid = operatorTypeUid;
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

	public Long getTargetFieldUid() {
		return targetFieldUid;
	}

	public void setTargetFieldUid(Long targetFieldUid) {
		this.targetFieldUid = targetFieldUid;
	}

	public String getTargetValue() {
		return targetValue;
	}

	public void setTargetValue(String targetValue) {
		this.targetValue = targetValue;
	}

	public String getConseqIndCode() {
		return conseqIndCode;
	}

	public void setConseqIndCode(String conseqIndCode) {
		this.conseqIndCode = conseqIndCode;
	}

	public String getErrorDescTxt() {
		return errorDescTxt;
	}

	public void setErrorDescTxt(String errorDescTxt) {
		this.errorDescTxt = errorDescTxt;
	}

	public Long getTargetValueUid() {
		return targetValueUid;
	}

	public void setTargetValueUid(Long targetValueUid) {
		this.targetValueUid = targetValueUid;
	}

	public String getConseqIndtxt() {
		return conseqIndtxt;
	}

	public void setConseqIndtxt(String conseqIndtxt) {
		this.conseqIndtxt = conseqIndtxt;
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

	public String getDeleteLink() {
		return deleteLink;
	}

	public void setDeleteLink(String deleteLink) {
		this.deleteLink = deleteLink;
	}

	public Long getConseqIndUid() {
		return conseqIndUid;
	}

	public void setConseqIndUid(Long conseqIndUid) {
		this.conseqIndUid = conseqIndUid;
	}

	public Long getErrormessageUid() {
		return errormessageUid;
	}

	public void setErrormessageUid(Long errormessageUid) {
		this.errormessageUid = errormessageUid;
	}

	public String getError_cd() {
		return error_cd;
	}

	public void setError_cd(String error_cd) {
		this.error_cd = error_cd;
	}
	
	
	

}
