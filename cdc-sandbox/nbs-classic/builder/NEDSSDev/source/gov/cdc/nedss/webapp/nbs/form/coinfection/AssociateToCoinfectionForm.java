package gov.cdc.nedss.webapp.nbs.form.coinfection;
import java.util.Collection;
import java.util.HashMap;

import gov.cdc.nedss.page.ejb.pageproxyejb.vo.PageProxyVO;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.InvestigationSummaryVO;
import gov.cdc.nedss.webapp.nbs.action.contacttracing.CtContactClientVO.CTContactClientVO;
import gov.cdc.nedss.webapp.nbs.form.util.BaseForm;
public class AssociateToCoinfectionForm extends BaseForm {
	
	private static final long serialVersionUID = 1L;
	private Collection<Object>  coinfectionSummaryVO ;
	private Collection<Object>  invSummaryVOColl ;
	private Long obsUid = null;
	private Long origCaseUid = null; //originating investigation if any
	private String obsTypeCd = null;
	private String[] selectedcheckboxIds;
    private String cancelButtonHref;
    private String formHref;
    private CTContactClientVO cTContactClientVO = new CTContactClientVO();
    private String[] processingDecision;
    PageProxyVO contactPageProxyVO = null; 
    
    public CTContactClientVO getcTContactClientVO() {
		return cTContactClientVO;
	}

	public void setcTContactClientVO(CTContactClientVO cTContactClientVO) {
		this.cTContactClientVO = cTContactClientVO;
	}

	
	public String[] getProcessingDecision() {
		return processingDecision;
	}

	public void setProcessingDecision(String[] processingDecision) {
		this.processingDecision = processingDecision;
	}

	public void resetForm() {
    	coinfectionSummaryVO = null;
    	invSummaryVOColl = null;
    	obsUid = null;
    	origCaseUid = null;
    	resetCheckboxIds();
    	processingDecision = null;
    	cTContactClientVO.setAnswerMap(new HashMap<Object,Object>());
    	cTContactClientVO.setAnswerArrayMap(new HashMap<Object,Object>());
    	contactPageProxyVO = null;
    	this.setAttributeMap(new HashMap<Object,Object>());
    }

	public Long getObsUid() {
		return obsUid;
	}

	public void setObsUid(Long obsUid) {
		this.obsUid = obsUid;
	}
	
	public Long getOrigCaseUid() {
		return origCaseUid;
	}

	public void setOrigCaseUid(Long origCaseUid) {
		this.origCaseUid = origCaseUid;
	}	
	
	public PageProxyVO getContactPageProxyVO() {
		return contactPageProxyVO;
	}

	public void setContactPageProxyVO(PageProxyVO contactPageProxyVO) {
		this.contactPageProxyVO = contactPageProxyVO;
	}

	public String getObsTypeCd() {
		return obsTypeCd;
	}

	public void setObsTypeCd(String obsTypeCd) {
		this.obsTypeCd = obsTypeCd;
	}
	
	public String[] getSelectedcheckboxIds() {
		return selectedcheckboxIds;
	}

	public void setSelectedcheckboxIds(String[] selectedcheckboxIds) {
		this.selectedcheckboxIds = selectedcheckboxIds;
	}

	public void updateCheckboxIds(String checkboxIds) {
		if(checkboxIds!=null && !checkboxIds.trim().equals("")){
		String[] values = checkboxIds.split(":");
		this.selectedcheckboxIds = values ;
		}
	}		
   public void resetCheckboxIds(){
	   this.selectedcheckboxIds =null;
	   
   }
	
   	public String getFormHref() {
		return formHref;
	}

	public void setFormHref(String formHref) {
		this.formHref = formHref;
	}

	public String getCancelButtonHref() {
		return cancelButtonHref;
	}

	public void setCancelButtonHref(String cancelButtonHref) {
		this.cancelButtonHref = cancelButtonHref;
	}

	public Collection<Object> getCoinfectionSummaryVO() {
		return coinfectionSummaryVO;
	}

	public void setCoinfectionSummaryVO(Collection<Object> coInfectionSummaryVO) {
		this.coinfectionSummaryVO = coInfectionSummaryVO;
	}	

	public Collection<Object>  getInvSummaryVOColl() {
		return invSummaryVOColl;
	}
	public void setInvSummaryVOColl(Collection<Object> invSummaryVOColl) {
		this.invSummaryVOColl = invSummaryVOColl;
	}	
	
}

