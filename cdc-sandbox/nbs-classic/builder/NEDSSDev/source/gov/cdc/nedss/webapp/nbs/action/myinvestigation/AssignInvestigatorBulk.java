package gov.cdc.nedss.webapp.nbs.action.myinvestigation;

import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.form.myinvestigation.ProgramAreaForm;


public class AssignInvestigatorBulk extends Action{

	 
    //For logging
    static final LogUtils logger = new LogUtils(AssignInvestigatorBulk.class.getName());

	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
     throws Exception{
		NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) request.getSession().getAttribute("NBSSecurityObject");
		HttpSession session = request.getSession();
		ProgramAreaForm programAreaForm = (ProgramAreaForm) form;
		
		String selectedInvestigator = (String) programAreaForm.getAttributeMap().get("INV207Uid");
		StringTokenizer sti = new StringTokenizer(selectedInvestigator, "|");
		selectedInvestigator=sti.nextToken();
		String selectedCheckBoxInfo= programAreaForm.getSelectedChkboxIdsInfo();
		int noOfInvestigationsChanged = 0;
		
		if(!gov.cdc.nedss.util.StringUtils.isEmpty(selectedCheckBoxInfo)) {

			
			
			StringTokenizer st = new StringTokenizer(selectedCheckBoxInfo, "|");
			while(st!=null && st.hasMoreTokens()) {
				 StringTokenizer st1 = new StringTokenizer(st.nextToken(), "@");
				 while(st1!=null && st1.hasMoreTokens()) {
						String publicHealthCaseUID = st1.nextToken();
						String invFormcd= st1.nextToken();
							if(invFormcd != null && invFormcd.equalsIgnoreCase(NEDSSConstants.INV_FORM_RVCT) ||
								invFormcd != null && invFormcd.equalsIgnoreCase(NEDSSConstants.INV_FORM_VAR)
								) {
							ProgramAreaUtil.processRVCTorVarcellaTypeInvestigations(publicHealthCaseUID,nbsSecurityObj,selectedInvestigator,
									programAreaForm.getDateAssigned(),session,request);
						}else if (invFormcd!=null && invFormcd.startsWith("PG_")) {
							ProgramAreaUtil.processPGTypeInvestigations(publicHealthCaseUID,nbsSecurityObj,selectedInvestigator,
									programAreaForm.getDateAssigned(),session);
						}else {
							ProgramAreaUtil.processLegacyTypeInvestigations(publicHealthCaseUID, nbsSecurityObj, selectedInvestigator, programAreaForm.getDateAssigned(), session, request);
						}
							noOfInvestigationsChanged++;
						
				 }
			}
			
		
		}else {
			throw new Exception("No cases selected to assign investigators.");
		}
		
		/*
		String localId= programAreaForm.getAttributeMap().get( "INV207LOCALID").toString();
		Map<Object,Object> searchCriteriaMap = programAreaForm.getSearchCriteriaArrayMap();
		String[] inv = (String[]) searchCriteriaMap.get("INVESTIGATOR");
		if(inv!=null && inv.length>0){
			String[] inv1 = new String[inv.length+1];
			System.arraycopy(inv, 0, inv1, 0, inv.length);
			inv1[inv.length] = localId;
			searchCriteriaMap.put("INVESTIGATOR",inv1);
		}
		*/ 
		//String confirmationMessage =null;
		String selectedInvestigatorName = programAreaForm.getInvestigatorSelected();
		StringTokenizer st2 = new StringTokenizer(selectedInvestigatorName, ",");
		
		
		request.setAttribute("confirmMsg", "The selected ");
		request.setAttribute("confirmMsg1",noOfInvestigationsChanged);
		request.setAttribute("confirmMsg3",st2.nextToken());
		if(noOfInvestigationsChanged>1) {
			request.setAttribute("confirmMsg2"," Investigations have been successfully assigned to ");
			//confirmationMessage ="The selected <b>"+noOfInvestigationsChanged+"</b> Investigations have been successfully assigned to <b>"+ st2.nextToken();
		}else {
			request.setAttribute("confirmMsg2"," Investigation has been successfully assigned to ");
			//confirmationMessage ="The selected <b>"+noOfInvestigationsChanged+" </b> Investigation has been successfully assigned to <b>"+ st2.nextToken();	
		}
		//programAreaForm.setMsgBlock(confirmationMessage);
		
		
		return mapping.findForward("assignInvestigatorBulkConfirmation");
	}
	
	
}
