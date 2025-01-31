package gov.cdc.nedss.webapp.nbs.form.manage;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.InvestigationProxyVO;
import gov.cdc.nedss.webapp.nbs.form.util.BaseForm;

public class ManageEventsForm  extends BaseForm {
	private static final long serialVersionUID = 1L;
	
	private InvestigationProxyVO oldInvestigationProxyVO = new InvestigationProxyVO();
	private Collection<Object>  labSummaryVOColl;
	private Collection<Object>  morbSummaryVOColl;
	private Collection<Object>  treatmentSummaryVOColl;
	private Collection<Object>  vaccinationSummaryVOColl;
	private Collection<Object>  documentSummaryVOColl;
	private PersonVO personVo;
	private String sex;
	private String fullNm;
	private String dob;
	private String caseStatus;
	private String conditionCd;
	private String[] selectedcheckboxIds;
		

	public InvestigationProxyVO getOldInvestigationProxyVO() {
		return oldInvestigationProxyVO;
	}

	public void setOldInvestigationProxyVO(
			InvestigationProxyVO oldInvestigationProxyVO) {
		this.oldInvestigationProxyVO = oldInvestigationProxyVO;
	}

	public String[] getSelectedcheckboxIds() {
		return selectedcheckboxIds;
	}

	public void setSelectedcheckboxIds(String[] selectedcheckboxIds) {
		this.selectedcheckboxIds = selectedcheckboxIds;
	}
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
	}

	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors errors = super.validate(mapping, request);
		if (errors == null)
			errors = new ActionErrors();
		return errors;
	}

	public Collection<Object>  getTreatmentSummaryVOColl() {
		return treatmentSummaryVOColl;
	}

	public void setTreatmentSummaryVOColl(Collection<Object> treatmentSummaryVOColl) {
		this.treatmentSummaryVOColl = treatmentSummaryVOColl;
	}

	public Collection<Object>  getVaccinationSummaryVOColl() {
		return vaccinationSummaryVOColl;
	}

	public void setVaccinationSummaryVOColl(Collection<Object> vaccinationSummaryVOColl) {
		this.vaccinationSummaryVOColl = vaccinationSummaryVOColl;
	}

	public Collection<Object>  getDocumentSummaryVOColl() {
		return documentSummaryVOColl;
	}

	public void setDocumentSummaryVOColl(Collection<Object> documentSummaryVOColl) {
		this.documentSummaryVOColl = documentSummaryVOColl;
	}

	public Collection<Object>  getLabSummaryVOColl() {
		return labSummaryVOColl;
	}

	public void setLabSummaryVOColl(Collection<Object> labSummaryVOColl) {
		this.labSummaryVOColl = labSummaryVOColl;
	}

	public Collection<Object>  getMorbSummaryVOColl() {
		return morbSummaryVOColl;
	}

	public void setMorbSummaryVOColl(Collection<Object> morbSummaryVOColl) {
		this.morbSummaryVOColl = morbSummaryVOColl;
	}

	public PersonVO getPersonVo() {
		return personVo;
	}

	public void setPersonVo(PersonVO personVo) {
		this.personVo = personVo;
	}

	public String getFullNm() {
		return fullNm;
	}

	public void setFullNm(String fullNm) {
		this.fullNm = fullNm;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getCaseStatus() {
		return caseStatus;
	}

	public void setCaseStatus(String caseStatus) {
		this.caseStatus = caseStatus;
	}

	public String getConditionCd() {
		return conditionCd;
	}

	public void setConditionCd(String conditionCd) {
		this.conditionCd = conditionCd;
	}


}
