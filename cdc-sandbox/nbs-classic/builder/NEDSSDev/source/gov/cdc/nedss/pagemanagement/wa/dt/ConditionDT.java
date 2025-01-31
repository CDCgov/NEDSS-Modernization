package gov.cdc.nedss.pagemanagement.wa.dt;

import java.lang.reflect.Field;
import java.sql.Timestamp;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;
import gov.cdc.nedss.util.StringUtils;

/**
* Name:		ConditionDT.java
* Description:	DT for Condition_Code. ConditionCd id the primary
*   key for the srte.Condition_Code table
* Copyright:	Copyright (c) 2009
* Company: 	CSC
* @author:	Gregory Tucker
*/

public class ConditionDT extends AbstractVO implements RootDTInterface{

	
	private static final long serialVersionUID = 1L;
	private String conditionCd;
	private String conditionCodesetNm;
	private String conditionSeqNum;
	private String assigningAuthorityCd;
	private String assigningAuthorityDescTxt;
	private String codeSystemCd;
	private String codeSystemDescTxt;
	private String conditionDescTxt;
	private String conditionShortNm;
	private String effectiveFromTime;
	private String effectiveToTime;
	private Integer indentLevelNbr;
	private String investigationFormCd;
	private String isModifiableInd;	
	private Long nbsUid;
	private String nndInd;
	private String parentIsCd;
	private String progAreaCd;
	private String reportableMorbidityInd;
	private String reportableSummaryInd;
	private String statusCd;
	private String nndEntityId;
	private String nndSummaryEntityIdentifier;
	private String summaryInvestigationFormCd;
	private String contactTracingEnableInd;
	private String vaccineModuleEnableInd;
	private String treatmentModuleEnableInd;
	private String labReportModuleEnableInd;
	private String morbReportModuleEnableInd;
	private String viewLink;
	private String editLink;
	private String selectLink;
	private String nndIndDescTxt;
	private String statusCdDescTxt;
	private String parentIsCdDescTxt;
	private String coInfGroupDescTxt;
	private Timestamp statusTime;
	private String PortReqIndCd;
	private String familyCd;
	private String familyCdDescTxt;
	private String pageNm;
	private String pageNmForDisplay;
	private String pageNmForDisplayPrint;
	private String coInfGroup;
	
	
	
   public String getPageNmForDisplay() {
		return pageNmForDisplay;
	}
	public void setPageNmForDisplay(String pageNmForDisplay) {
		this.pageNmForDisplay = pageNmForDisplay;
	}
	public String getPageNm() {
		return pageNm;
	}
	public void setPageNm(String pageNm) {
		this.pageNm = pageNm;
	}
	public String getFamilyCdDescTxt() {
		return familyCdDescTxt;
	}
	public void setFamilyCdDescTxt(String familyCdDescTxt) {
		this.familyCdDescTxt = familyCdDescTxt;
	}
	public String getFamilyCd() {
		return familyCd;
	}
	public void setFamilyCd(String familyCd) {
		this.familyCd = familyCd;
	}
	public String getPortReqIndCd() {
		return PortReqIndCd;
	}
	public void setPortReqIndCd(String portReqIndCd) {
		PortReqIndCd = portReqIndCd;
	}
	public String getParentIsCdDescTxt() {
		return parentIsCdDescTxt;
	}
	public void setParentIsCdDescTxt(String parentIsCdDescTxt) {
		this.parentIsCdDescTxt = parentIsCdDescTxt;
	}
	public String getNndIndDescTxt() {
		return nndIndDescTxt;
	}
	public void setNndIndDescTxt(String nndIndDescTxt) {
		this.nndIndDescTxt = nndIndDescTxt;
	}
	public String getStatusCdDescTxt() {
		return statusCdDescTxt;
	}
	public void setStatusCdDescTxt(String statusCdDescTxt) {
		this.statusCdDescTxt = statusCdDescTxt;
	}
	public String getConditionCd() {
		return conditionCd;
	}
	public void setConditionCd(String conditionCd) {
		this.conditionCd = conditionCd;
	}
	public String getConditionCodesetNm() {
		return conditionCodesetNm;
	}
	public void setConditionCodesetNm(String conditionCodesetNm) {
		this.conditionCodesetNm = conditionCodesetNm;
	}
	public String getConditionSeqNum() {
		return conditionSeqNum;
	}
	public void setConditionSeqNum(String conditionSeqNum) {
		this.conditionSeqNum = conditionSeqNum;
	}
	public String getAssigningAuthorityCd() {
		return assigningAuthorityCd;
	}
	public void setAssigningAuthorityCd(String assigningAuthorityCd) {
		this.assigningAuthorityCd = assigningAuthorityCd;
	}
	public String getAssigningAuthorityDescTxt() {
		return assigningAuthorityDescTxt;
	}
	public void setAssigningAuthorityDescTxt(String assigningAuthorityDescTxt) {
		this.assigningAuthorityDescTxt = assigningAuthorityDescTxt;
	}
	public String getCodeSystemCd() {
		return codeSystemCd;
	}
	public void setCodeSystemCd(String codeSystemCd) {
		this.codeSystemCd = codeSystemCd;
	}
	public String getCodeSystemDescTxt() {
		return codeSystemDescTxt;
	}
	public void setCodeSystemDescTxt(String codeSystemDescTxt) {
		this.codeSystemDescTxt = codeSystemDescTxt;
	}
	public String getConditionDescTxt() {
		return conditionDescTxt;
	}
	public void setConditionDescTxt(String conditionDescTxt) {
		this.conditionDescTxt = conditionDescTxt;
	}
	public String getConditionShortNm() {
		return conditionShortNm;
	}
	public void setConditionShortNm(String conditionShortNm) {
		this.conditionShortNm = conditionShortNm;
	}
	public String getEffectiveFromTime() {
		return effectiveFromTime;
	}
	public void setEffectiveFromTime(String effectiveFromTime) {
		this.effectiveFromTime = effectiveFromTime;
	}
	public String getEffectiveToTime() {
		return effectiveToTime;
	}
	public void setEffectiveToTime(String effectiveToTime) {
		this.effectiveToTime = effectiveToTime;
	}
	public Integer getIndentLevelNbr() {
		return indentLevelNbr;
	}
	public void setIndentLevelNbr(Integer indentLevelNbr) {
		this.indentLevelNbr = indentLevelNbr;
	}
	public String getInvestigationFormCd() {
		return investigationFormCd;
	}
	public void setInvestigationFormCd(String investigationFormCd) {
		this.investigationFormCd = investigationFormCd;
	}
	public String getIsModifiableInd() {
		return isModifiableInd;
	}
	public void setIsModifiableInd(String isModifiableInd) {
		this.isModifiableInd = isModifiableInd;
	}
	public Long getNbsUid() {
		return nbsUid;
	}
	public void setNbsUid(Long nbsUid) {
		this.nbsUid = nbsUid;
	}
	public String getNndInd() {
		return nndInd;
	}
	public void setNndInd(String nndInd) {
		this.nndInd = nndInd;
	}
	public String getParentIsCd() {
		return parentIsCd;
	}
	public void setParentIsCd(String parentIsCd) {
		this.parentIsCd = parentIsCd;
	}
	public String getProgAreaCd() {
		return progAreaCd;
	}
	public void setProgAreaCd(String progAreaCd) {
		this.progAreaCd = progAreaCd;
	}
	public String getReportableMorbidityInd() {
		return reportableMorbidityInd;
	}
	public void setReportableMorbidityInd(String reportableMorbidityInd) {
		this.reportableMorbidityInd = reportableMorbidityInd;
	}
	public String getReportableSummaryInd() {
		return reportableSummaryInd;
	}
	public void setReportableSummaryInd(String reportableSummaryInd) {
		this.reportableSummaryInd = reportableSummaryInd;
	}
	public String getStatusCd() {
		return statusCd;
	}
	public void setStatusCd(String statusCd) {
		this.statusCd = statusCd;
	}
	
	public String getNndEntityId() {
		return nndEntityId;
	}
	public void setNndEntityId(String nndEntityId) {
		this.nndEntityId = nndEntityId;
	}
	public String getNndSummaryEntityIdentifier() {
		return nndSummaryEntityIdentifier;
	}
	public void setNndSummaryEntityIdentifier(String nndSummaryEntityIdentifier) {
		this.nndSummaryEntityIdentifier = nndSummaryEntityIdentifier;
	}
	public String getSummaryInvestigationFormCd() {
		return summaryInvestigationFormCd;
	}
	public void setSummaryInvestigationFormCd(String summaryInvestigationFormCd) {
		this.summaryInvestigationFormCd = summaryInvestigationFormCd;
	}
	public String getContactTracingEnableInd() {
		return contactTracingEnableInd;
	}
	public void setContactTracingEnableInd(String contactTracingEnableInd) {
		this.contactTracingEnableInd = contactTracingEnableInd;
	}
	public String getVaccineModuleEnableInd() {
		return vaccineModuleEnableInd;
	}
	public void setVaccineModuleEnableInd(String vaccineModuleEnableInd) {
		this.vaccineModuleEnableInd = vaccineModuleEnableInd;
	}
	public String getTreatmentModuleEnableInd() {
		return treatmentModuleEnableInd;
	}
	public void setTreatmentModuleEnableInd(String treatmentModuleEnableInd) {
		this.treatmentModuleEnableInd = treatmentModuleEnableInd;
	}
	public String getLabReportModuleEnableInd() {
		return labReportModuleEnableInd;
	}
	public void setLabReportModuleEnableInd(String labReportModuleEnableInd) {
		this.labReportModuleEnableInd = labReportModuleEnableInd;
	}
	public String getMorbReportModuleEnableInd() {
		return morbReportModuleEnableInd;
	}
	public void setMorbReportModuleEnableInd(String morbReportModuleEnableInd) {
		this.morbReportModuleEnableInd = morbReportModuleEnableInd;
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
	public String getSelectLink() {
		return selectLink;
	}
	public void setSelectLink(String selectLink) {
		this.selectLink = selectLink;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	
	public void setLocalId(String localId) {	
	}
	@Override
	public boolean isEqual(Object objectname1, Object objectname2,
			Class<?> voClass) {
		return false;
	}
	@Override
	public Timestamp getAddTime() {
		return null;
	}
	@Override
	public Long getAddUserId() {
		return null;
	}
	@Override
	public String getJurisdictionCd() {
		return null;
	}
	@Override
	public String getLastChgReasonCd() {
		return null;
	}
	@Override
	public Timestamp getLastChgTime() {
		return null;
	}
	@Override
	public Long getLastChgUserId() {
		return null;
	}
	@Override
	public String getLocalId() {
		return null;
	}
	@Override
	public Long getProgramJurisdictionOid() {
		return null;
	}
	@Override
	public String getRecordStatusCd() {
		return null;
	}
	@Override
	public Timestamp getRecordStatusTime() {
		return null;
	}
	@Override
	public String getSharedInd() {
		return null;
	}
	@Override
	public String getSuperclass() {
		return null;
	}
	@Override
	public Long getUid() {
		return null;
	}
	@Override
	public Integer getVersionCtrlNbr() {
		return null;
	}
	@Override
	public boolean isItDelete() {
		return false;
	}
	@Override
	public boolean isItDirty() {
		return false;
	}
	@Override
	public boolean isItNew() {
		return false;
	}
	@Override
	public void setAddTime(Timestamp addTime) {
	}
	@Override
	public void setAddUserId(Long addUserId) {
	}
	@Override
	public void setItDelete(boolean itDelete) {
	}
	@Override
	public void setItDirty(boolean itDirty) {
	}
	@Override
	public void setItNew(boolean itNew) {
	}
	@Override
	public void setJurisdictionCd(String jurisdictionCd) {
	}
	@Override
	public void setLastChgReasonCd(String lastChgReasonCd) {
	}
	@Override
	public void setLastChgTime(Timestamp lastChgTime) {
	}
	@Override
	public void setLastChgUserId(Long lastChgUserId) {
	}
	@Override
	public void setProgramJurisdictionOid(Long programJurisdictionOid) {
	}
	@Override
	public void setRecordStatusCd(String recordStatusCd) {
	}
	@Override
	public void setRecordStatusTime(Timestamp recordStatusTime) {
	}
	@Override
	public void setSharedInd(String sharedInd) {
	}
	public Timestamp getStatusTime() {

        return statusTime;
    }

	public void setStatusTime(Timestamp aStatusTime) {
        statusTime = aStatusTime;
        setItDirty(true);
    }
	
    public void setStatusTime_s(String strTime) {

        if (strTime == null)

            return;

        this.setStatusTime(StringUtils.stringToStrutsTimestamp(strTime));
    }
    
    public String getCoInfGroup() {
		return coInfGroup;
	}
	public void setCoInfGroup(String coInfGroup) {
		this.coInfGroup = coInfGroup;
	}
	
    public String getCoInfGroupDescTxt() {
		return coInfGroupDescTxt;
	}
	public void setCoInfGroupDescTxt(String coInfGroupDescTxt) {
		this.coInfGroupDescTxt = coInfGroupDescTxt;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		try {
			sb.append(this.getClass().getName() + "\r\n");
			Field[] f = ConditionDT.class.getDeclaredFields();
			for (int i = 0; i < f.length; i++) {
				if (f[i] != null)
					sb.append(f[i].getName() + ":" + f[i].get(this) + "\r\n");
			}
		} catch (Exception ignore) {
		}
		return sb.toString();
	}
	public String getPageNmForDisplayPrint() {
		return pageNmForDisplayPrint;
	}
	public void setPageNmForDisplayPrint(String pageNmForDisplayPrint) {
		this.pageNmForDisplayPrint = pageNmForDisplayPrint;
	}
}
