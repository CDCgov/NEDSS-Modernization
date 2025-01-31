package gov.cdc.nedss.webapp.nbs.form.observation;


import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.InvestigationProxyVO;
import gov.cdc.nedss.webapp.nbs.form.util.BaseForm;

public class ManageObservationForm extends BaseForm {
	private static final long serialVersionUID = 1L;

	private InvestigationProxyVO oldInvestigationProxyVO = new InvestigationProxyVO();
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

}
