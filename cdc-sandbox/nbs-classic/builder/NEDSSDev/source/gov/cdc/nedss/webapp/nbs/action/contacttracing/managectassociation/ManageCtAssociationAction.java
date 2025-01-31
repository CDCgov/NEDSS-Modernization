package gov.cdc.nedss.webapp.nbs.action.contacttracing.managectassociation;

import gov.cdc.nedss.act.ctcontact.dt.CTContactSummaryDT;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.action.util.PaginationUtil;
import gov.cdc.nedss.webapp.nbs.form.managectassociation.ManageCTAssociateForm;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;


/**
 * Struts Action Class to handle Manage Contact Associations from View Investigation
 * actionHandler based on the InvFormCd
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: Computer Sciences Corporation</p>
 * ManageCtAssociationAction.java
 * Nov 12 2009
 * @version
 */
public class ManageCtAssociationAction extends DispatchAction {
	static final LogUtils logger = new LogUtils(ManageCtAssociationAction.class.getName());
	PropertyUtil properties = PropertyUtil.getInstance();

	public ActionForward manageContactsLoad(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws IOException, ServletException {
		ManageCTAssociateForm manageAssocForm = (ManageCTAssociateForm) form;
		try {
			String actionForward = "default";
			Collection<Object> contactsColl = new ArrayList<Object> ();
			HttpSession session = request.getSession();
			String investigationUid = "";
			String patientPersonUID = null;
			Long patientPersonUIDL = null;
			Long investigationUidL =null;
			try{
				if(request.getParameter("caseLocalId") != null)
					investigationUid = (String)request.getParameter("caseLocalId");
				if(investigationUid == null || investigationUid.equals(""))
					investigationUid=(String)NBSContext.retrieve(request.getSession(), NBSConstantUtil.DSInvestigationUid);
				investigationUidL = new Long(investigationUid);
				//String subjectPatientRevisionPhcUid=(String)NBSContext.retrieve(request.getSession(), NBSConstantUtil.DSPatientRevisionUID);

				if(request.getParameter("personMprUid") != null)
					patientPersonUID = (String)request.getParameter("personMprUid");

				if(patientPersonUID != null)
					patientPersonUIDL = Long.valueOf(patientPersonUID);
				if(patientPersonUIDL == null || patientPersonUIDL == 0 ){
					patientPersonUIDL =(Long) NBSContext.retrieve(session, NBSConstantUtil.DSPatientMPRUID);
				}
			}
			catch(Exception e){
				logger.error("Error in retrieving the  InvestigationUid or Patient Parent Uid");
				throw new ServletException(e.toString());
			}



			try{
				contactsColl = getContactSummDTColl(session,investigationUidL,patientPersonUIDL);
			}catch(Exception e){
				logger.error("Error in getting the ContactSummaryDTCollection ");
				throw new ServletException(e.toString());

			}
			Collection<Object> updatedContactsColl = updateContactSummaryColl(contactsColl, request);
			Integer queueSize = properties.getQueueSize(NEDSSConstants.MANAGE_CONTACT_ASSOCIATIONS_QUEUE_SIZE);
			manageAssocForm.getAttributeMap().put("queueSize", queueSize);

			if(updatedContactsColl != null)
				request.setAttribute("queueCount", String.valueOf(updatedContactsColl.size()));
			else
				request.setAttribute("queueCount", "0");
			manageAssocForm.setContactVOColl(updatedContactsColl);
			manageAssocForm.getAttributeMap().put(NBSConstantUtil.DSInvestigationUid, investigationUidL);
			request.setAttribute("contactsSummaryList",updatedContactsColl);
		} catch (Exception ex) {
			logger.error("Exception in ManageCtAssociationAction.manageContactsLoad encountered.."
					+ ex.getMessage());
			ex.printStackTrace();
		}
		return PaginationUtil.paginate(manageAssocForm, request, "default",mapping);
		//return (mapping.findForward(actionForward));


	}

	public Collection<Object> getContactSummDTColl(HttpSession session,Long publicHealthCaseUID, Long mprUid)throws Exception{
		Collection<Object>  contactsColl = new ArrayList<Object> ();
		MainSessionCommand msCommand = null;

		try{
			String sBeanJndiName = JNDINames.PAM_PROXY_EJB;
			String sMethod = "getNamedAsContactSummaryByCondition";
			Object[] oParams =new Object[] {publicHealthCaseUID,mprUid};
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);

			ArrayList<?> aList  = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
				contactsColl = (Collection)aList.get(0);
			}catch (Exception ex) {
				logger.fatal("Error in getNamedAsContactSummaryByCondition in Action Util: ", ex);
				throw new Exception(ex.toString());
			}
			 return contactsColl;

	}
	public ActionForward manageContactsSubmit(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws IOException, ServletException {
		try{
			HttpSession session = request.getSession();
			ManageCTAssociateForm manageAssocForm = (ManageCTAssociateForm) form;
			Collection<Object>  newAssociateContactUidColl= new ArrayList<Object> ();
			HashMap<Object,Object> ctAssMap = new HashMap<Object,Object>();

			String[] checkBoxIds = manageAssocForm.getSelectedcheckboxIds();
			if(checkBoxIds !=null){
				for(int i = 0 ; i<checkBoxIds.length ; i++){
					String observationUID = checkBoxIds[i];
					StringTokenizer st = new StringTokenizer(observationUID,":");
					while (st.hasMoreTokens()) {
						String token = st.nextToken();
						newAssociateContactUidColl.add((token.toString()));
						ctAssMap.put(token.toString(),token.toString());
					}
				}
			}
			Collection<Object>  originalContactVOColl = manageAssocForm.getContactVOColl();
			Collection<Object>  disAssociateContactUidColl =  new ArrayList<Object> ();	;
			try{
				disAssociateContactUidColl = this.getDisAssociatedColl(originalContactVOColl, ctAssMap);
			}catch(Exception e){
				logger.error("Error in finding disAssociate Collection");
			}
			Long publicHealthCaseUID = (Long)manageAssocForm.getAttributeMap().get(NBSConstantUtil.DSInvestigationUid);
			setContactsAssociateDisassociate(session,publicHealthCaseUID,newAssociateContactUidColl,disAssociateContactUidColl);
			manageAssocForm.resetCheckboxIds();
			request.setAttribute("ContactTabtoFocus", "ContactTabtoFocus");

		}catch (Exception ex) {
			logger.fatal("Error in updateAssociate in Action Util: ", ex);
			throw new ServletException(ex.toString());
		}
		return  (mapping.findForward("ContactAssociate"));
		}
	    public ActionForward manageContactsInvestigationLoad(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws IOException, ServletException {
	    	 return (mapping.findForward("ContactAssociate"));
	    }
	    private Collection<Object> getDisAssociatedColl(Collection<Object> originalContactVOColl, HashMap ctAssMap) throws Exception{
	    	Collection<Object>  newDisAssociateContactUidColl= new ArrayList<Object> ();
			if(originalContactVOColl != null && originalContactVOColl.size() >0){
				Iterator<Object> iter = originalContactVOColl.iterator();
				while(iter.hasNext()){
					CTContactSummaryDT cTContactSummaryDT = (CTContactSummaryDT)iter.next();
					if (!ctAssMap.containsKey(cTContactSummaryDT.getCtContactUid().toString()))
						newDisAssociateContactUidColl.add(cTContactSummaryDT.getCtContactUid().toString());
				}
			}
			return newDisAssociateContactUidColl;
	    }

	    public void setContactsAssociateDisassociate(HttpSession session,Long publicHealthCaseUID,Collection<Object> newAssociateContactUidColl, Collection<Object> newDisAssociateContactUidColl)throws Exception{
			Collection<Object>  contactsColl = new ArrayList<Object> ();
			MainSessionCommand msCommand = null;

			try{
				String sBeanJndiName = JNDINames.PAM_PROXY_EJB;
				String sMethod = "updateContactAssociations";
				Object[] oParams =new Object[] {publicHealthCaseUID,newAssociateContactUidColl,newDisAssociateContactUidColl};
				MainSessionHolder holder = new MainSessionHolder();
				msCommand = holder.getMainSessionCommand(session);

				ArrayList<?> aList  = msCommand.processRequest(sBeanJndiName, sMethod, oParams);

				}catch (Exception ex) {
					logger.fatal("Error in updateContactAssociations in Action Util: ", ex);
					throw new Exception(ex.toString());
				}


		}

	    private Collection<Object> updateContactSummaryColl(Collection<Object> contactColl,HttpServletRequest request){
	    	Collection<Object> updatedContactColl = new ArrayList<Object> ();
	    	CachedDropDownValues cdv = new CachedDropDownValues();

	    	if(contactColl != null && contactColl.size()>0){
	    		Iterator<Object> iter = contactColl.iterator();

	    		while(iter.hasNext()){
	    			try {
	    				CTContactSummaryDT cTContactSummaryDT = (CTContactSummaryDT)iter.next();
	    				String tempStrforSex="";
	    				String dateStr="";
	    				String tempStrAgeUnit="";
	    				String age = cTContactSummaryDT.getAgeReported()== null? "":cTContactSummaryDT.getAgeReported();

	    				if(cTContactSummaryDT.getCurrSexCd() != null){
	    					tempStrforSex = cdv.getCodeShortDescTxt(cTContactSummaryDT.getCurrSexCd(),"SEX");
	    				}
	    				if(cTContactSummaryDT.getAgeReportedUnitCd() != null){
	    					tempStrAgeUnit = cdv.getCodeShortDescTxt(cTContactSummaryDT.getAgeReportedUnitCd(),"AGE_UNIT");

	    				}

	    				if(cTContactSummaryDT.getBirthTime() != null){
	    					SimpleDateFormat dateFormat = new SimpleDateFormat(
	    							"MM/dd/yyyy");
	    					dateStr = dateFormat.format(cTContactSummaryDT.getBirthTime());
	    				}
	    				cTContactSummaryDT.setAgeDOBSex(age+" "+tempStrAgeUnit+("<BR>")+dateStr+("<BR>")+tempStrforSex);

	    				if(cTContactSummaryDT.getRelationshipCd() != null){
	    					String tempStrforRelation = cdv.getCodeShortDescTxt(cTContactSummaryDT.getRelationshipCd(),"NBS_RELATIONSHIP");
	    					cTContactSummaryDT.setRelationshipCd(tempStrforRelation);
	    				}
	    				if(cTContactSummaryDT.getDispositionCd() != null){
	    					String tempStrforDisp = cdv.getCodeShortDescTxt(cTContactSummaryDT.getDispositionCd(),"NBS_DISPO");
	    					cTContactSummaryDT.setDispositionCd(tempStrforDisp);
	    				}
	    				if(cTContactSummaryDT.getContactEntityPhcUid() != null )
	    					cTContactSummaryDT.setIsAssociatedToPHC(true);
	    				updatedContactColl.add(cTContactSummaryDT);
	    				String updateCd = cdv.getCachedConditionCodeList(cTContactSummaryDT.getSubjectPhcCd()) ;
	    				request.setAttribute("ctMessage", "The following is a list of patients who have named "+ cTContactSummaryDT.getName()
	    						+" as a contact for "+ updateCd
	    						+" Please select all contact records that should be associated with this investigation.");
	    			}catch (Exception ex) {
	    				logger.fatal("Error in updateContactAssociations.updateContactSummaryColl: ", ex);
	    			}
	    		} //hasNext
	    	}
	    	return updatedContactColl;
	    }

	}

