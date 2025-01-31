package gov.cdc.nedss.webapp.nbs.action.coinfection;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import gov.cdc.nedss.act.interview.dt.InterviewSummaryDT;
import gov.cdc.nedss.act.interview.ejb.dao.InterviewSummaryDAO;
import gov.cdc.nedss.act.publichealthcase.vo.PublicHealthCaseVO;
import gov.cdc.nedss.exception.NEDSSAppConcurrentDataException;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.PageProxyVO;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.CoinfectionSummaryVO;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.InvestigationSummaryVO;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.vo.CTContactProxyVO;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.action.contacttracing.CtContactClientVO.CTContactClientVO;
import gov.cdc.nedss.webapp.nbs.action.contacttracing.util.CTConstants;
import gov.cdc.nedss.webapp.nbs.action.contacttracing.util.CTContactLoadUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageConstants;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageLoadUtil;
import gov.cdc.nedss.webapp.nbs.action.util.PaginationUtil;
import gov.cdc.nedss.webapp.nbs.form.coinfection.AssociateToCoinfectionForm;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import gov.cdc.nedss.util.HTMLEncoder;
/**
 * AssociateToCoinfectionAction -
 * This action class is for the popup to associate treatments, interviews, contacts, etc. with existing Co-Infection investigations.
 * Note: There is no submit for the calls from create because the information is stored in hidden fields in the parent form and the parent
 * form and submit is called from the parent. There is a submit from the View Event Associate Investigations button.
 * @author TuckerG
 * @copyright 2015 SRA International
 */
public class AssociateToCoinfectionAction extends DispatchAction {

		static final LogUtils logger = new LogUtils(AssociateToCoinfectionAction.class.getName());
		PropertyUtil properties = PropertyUtil.getInstance();

		/**
		 * associateToCoinfectionLoad
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 */
		public ActionForward associateToCoinfectionLoad(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
			AssociateToCoinfectionForm assocToCoinfForm = (AssociateToCoinfectionForm) form;
		    ArrayList<Object> coinfectionInvList = null;
		    Long investigationUid = null;
		    HttpSession session = request.getSession();

		    try {
		    	String investigationUidStr = (String)  request.getAttribute("DSInvestigationUID");
		    	if (investigationUidStr== null)
		    		  investigationUidStr= (String) NBSContext.retrieve(session, "DSInvestigationUID");
		    	if (investigationUidStr != null)
		    		  investigationUid = new Long(investigationUidStr);
		    } catch (Exception e) {
					logger.warn("In associateToCoinfection - expected Investigation not in scope to check for coinfections..");
		    }
		    if (investigationUid != null)
				try {
					coinfectionInvList = PageLoadUtil.getSpecificCoinfectionInvList(investigationUid, request);
				} catch (NEDSSAppConcurrentDataException e) {
					logger.error("Data Concurrency Exception in Assoc to Coinfection Action getting INV list: " + e.getMessage());
					e.printStackTrace();
				} catch (Exception e) {
					logger.error("Exception in Assoc to Coinfection Action getting INV list: " + e.getMessage());
					e.printStackTrace();
				}
			 if (coinfectionInvList==null)
			    	  coinfectionInvList=new ArrayList<Object>();
			 assocToCoinfForm.setCoinfectionSummaryVO(coinfectionInvList);



			 Collection<Object> updatedCoinfectionsColl = updateCoinfectionSummaryColl(coinfectionInvList, investigationUid, request);
			 Integer queueSize = properties.getQueueSize(NEDSSConstants.MANAGE_CONTACT_ASSOCIATIONS_QUEUE_SIZE);
			 assocToCoinfForm.getAttributeMap().put("queueSize", queueSize);

			 if(updatedCoinfectionsColl != null) {
				 request.setAttribute("queueCount", HTMLEncoder.encodeHtml(String.valueOf(updatedCoinfectionsColl.size())));
				request.setAttribute("coinfMessage", "The following list of investigations will be associated with the treatment. "
						+" Please deselect investigations that should not be associated with this treatment.");
			 } else
				 request.setAttribute("queueCount", HTMLEncoder.encodeHtml("0"));

			 assocToCoinfForm.setCoinfectionSummaryVO(updatedCoinfectionsColl);

			 assocToCoinfForm.getAttributeMap().put(NBSConstantUtil.DSInvestigationUid, investigationUid);
			 assocToCoinfForm.setPageTitle("Manage Co-Infection Associations", request);
			 request.setAttribute("coinfectionsSummaryList",updatedCoinfectionsColl);
			 return PaginationUtil.paginate(assocToCoinfForm, request, "default",mapping);

			//return (mapping.findForward("ManageCoinfection"));
		}


		/**
		 * Set all investigations to selected. The user can De-Select.
		 * @param coinfectionColl
		 * @param investigationUid
		 * @param request
		 * @return
		 */
	    private Collection<Object> updateCoinfectionSummaryColl(Collection<Object> coinfectionColl, Long interviewUid, Collection<Object> interviewCollection, Long investigationUid, HttpServletRequest request){
	    	 Collection<Object> updatedCoinfectionColl = new ArrayList<Object> ();
	      try {
	    	if(coinfectionColl != null && coinfectionColl.size()>0){
	    		Iterator<Object> iter = coinfectionColl.iterator();

				while(iter.hasNext()){
					CoinfectionSummaryVO coinfectionSummaryDT = (CoinfectionSummaryVO)iter.next();
					 //see if currently assoc
					 Iterator iterIxs = interviewCollection.iterator();
					 while (iterIxs.hasNext()) {
						 InterviewSummaryDT interviewSummaryDT = (InterviewSummaryDT) iterIxs.next();
						 if (interviewSummaryDT.getPublicHealthCaseUid().longValue() == coinfectionSummaryDT.getPublicHealthCaseUid().longValue()) {
							 if (interviewSummaryDT.getInterviewUid().longValue() == interviewUid.longValue())
								 coinfectionSummaryDT.setAssociated(true);
						 }
					 }
					if (coinfectionSummaryDT.getPublicHealthCaseUid().longValue()==investigationUid.longValue())
						coinfectionSummaryDT.setDisabled("disabled=\"disabled\"");
					else
						coinfectionSummaryDT.setDisabled("");
					updatedCoinfectionColl.add(coinfectionSummaryDT);
				}
	    	}
		  } catch (Exception ex) {
			  logger.error("Exception in AssociateToCoinfectionAction.updateCoinfectionSummaryColl encountered.." + ex.getMessage());
			  ex.printStackTrace();
		  }
	    	return updatedCoinfectionColl;
	    }


	    /**
		 * Set all investigations to selected. The user can De-Select.
		 * @param coinfectionColl
		 * @param investigationUid
		 * @param request
		 * @return
		 */
	    private Collection<Object> updateCoinfectionSummaryCollForContact(Collection<Object> coinfectionColl, Long investigationUid){
	      Collection<Object> updatedCoinfectionColl = new ArrayList<Object> ();
	      try {
	    	if(coinfectionColl != null && coinfectionColl.size()>0){
	    		Iterator<Object> iter = coinfectionColl.iterator();

				while(iter.hasNext()){
					CoinfectionSummaryVO coinfectionSummaryDT = (CoinfectionSummaryVO)iter.next();
					logger.debug("coinfectionSummaryDT.getPublicHealthCaseUid()-------------------------"+coinfectionSummaryDT.getPublicHealthCaseUid());
					if (coinfectionSummaryDT.getPublicHealthCaseUid().longValue()==investigationUid.longValue())
						coinfectionSummaryDT.setDisabled("disabled=\"disabled\"");
					else
						coinfectionSummaryDT.setDisabled("");
					updatedCoinfectionColl.add(coinfectionSummaryDT);
				}
	    	}
		  } catch (Exception ex) {
			  logger.error("Exception in AssociateToCoinfectionAction.updateCoinfectionSummaryCollForContact encountered.." + ex.getMessage());
			  ex.printStackTrace();
		  }
	    	return updatedCoinfectionColl;
	    }

		/**
		 * Set all investigations to selected. The user can De-Select.
		 * @param coinfectionColl
		 * @param investigationUid
		 * @param request
		 * @return
		 */
	    private Collection<Object> updateCoinfectionSummaryColl(Collection<Object> coinfectionColl,Long investigationUid, HttpServletRequest request){
	      Collection<Object> updatedCoinfectionColl = new ArrayList<Object> ();
	      try {
	    	if(coinfectionColl != null && coinfectionColl.size()>0){
	    		Iterator<Object> iter = coinfectionColl.iterator();

				while(iter.hasNext()){
					CoinfectionSummaryVO coinfectionSummaryDT = (CoinfectionSummaryVO)iter.next();
					coinfectionSummaryDT.setAssociated(true);
					if (coinfectionSummaryDT.getPublicHealthCaseUid().longValue()==investigationUid.longValue())
						coinfectionSummaryDT.setDisabled("disabled=\"disabled\"");
					else
						coinfectionSummaryDT.setDisabled("");
					updatedCoinfectionColl.add(coinfectionSummaryDT);
				}
	    	}
		  } catch (Exception ex) {
			  logger.error("Exception in AssociateToCoinfectionAction.updateCoinfectionSummaryCollForContact encountered.." + ex.getMessage());
			  ex.printStackTrace();
		  }
	      return updatedCoinfectionColl;
	    }


	    /**
	     * Get all investigations the user can associate to.
	     * @param mapping
	     * @param form
	     * @param request
	     * @param response
	     * @return
	     */
		public ActionForward associateInvestigationsLoad(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
			AssociateToCoinfectionForm assocToCoinfForm = (AssociateToCoinfectionForm) form;
		    ArrayList<Object> coinfectionInvList = new ArrayList<Object> ();
		    ArrayList<Object> investigationSummaryVOs = new ArrayList<Object> ();
		    Long personUid = null;
		    Long treatmentUid = null;
		    HttpSession session = request.getSession();
		      // Get Context Action
		    String contextAction = request.getParameter("ContextAction");
		    if (contextAction == null) {
		         contextAction = (String) request.getAttribute("ContextAction");
		    } else
		    	request.setAttribute("ContextAction", HTMLEncoder.encodeHtml(contextAction));

		    try {
		    	personUid = (Long)  request.getAttribute(NBSConstantUtil.DSPatientPersonUID);
		    	if (personUid == null)
		    		  personUid = (Long) NBSContext.retrieve(session, NBSConstantUtil.DSPatientPersonUID);
		    	String strUID = request.getParameter("treatmentUID");
		    	if (strUID != null) {
		    		treatmentUid = new Long(strUID);
		    		request.setAttribute("SourceUid", HTMLEncoder.encodeHtml(strUID));
		    	}

		    } catch (Exception e) {
					logger.warn("In associateToCoinfection - expected Patient not in scope to get Investigations..");
		    }
		    if (personUid != null && treatmentUid != null) {
			    //get the investigations associated with the person
				try {

					MainSessionHolder mainSessionHolder = new MainSessionHolder();
					MainSessionCommand msCommand = mainSessionHolder.getMainSessionCommand(session);
					String sBeanJndiName = JNDINames.OBSERVATION_PROXY_EJB;
					String sMethod = "getAssociatedInvestigations";
					//pass the patient to get the investigations for and the obsUID to set the existing associate flag
					Object[] oParms = new Object[] {personUid, treatmentUid};
					ArrayList<Object> arr = (ArrayList<Object> ) msCommand.processRequest(sBeanJndiName, sMethod, oParms);
					investigationSummaryVOs = (ArrayList<Object> ) arr.get(0);
					if (investigationSummaryVOs != null)
						assocToCoinfForm.setInvSummaryVOColl(investigationSummaryVOs);
				} catch (NEDSSAppConcurrentDataException e) {
					logger.error("Data Concurrency Exception in Assoc to Coinfection Load: " + e.getMessage());
					e.printStackTrace();
				} catch (Exception e) {
					logger.error("Exception in Assoc to Coinfection Load: " + e.getMessage());
					e.printStackTrace();
				}
				if (investigationSummaryVOs.size() > 0) {
						Iterator<Object> itr = investigationSummaryVOs.iterator();
						while (itr.hasNext()) {
							InvestigationSummaryVO invSumVO = (InvestigationSummaryVO) itr.next();
							CoinfectionSummaryVO coinfectionSummaryVO = new CoinfectionSummaryVO();
							try {
								BeanUtils.copyProperties(coinfectionSummaryVO, invSumVO);
								//some needed properties have different names..
								coinfectionSummaryVO.setConditionCd(invSumVO.getCd());
								if (invSumVO.getActivityFromTime() != null)
									coinfectionSummaryVO.setInvestigationStartDate(invSumVO.getActivityFromTime());
								if (invSumVO.getInvestigationStatusCd() != null)
									coinfectionSummaryVO.setInvestigationStatus(invSumVO.getInvestigationStatusCd());
								if (invSumVO.getInvestigatorFirstName() != null)
									coinfectionSummaryVO.setIntestigatorFirstNm(invSumVO.getInvestigatorFirstName());
								if (invSumVO.getInvestigatorLastName() != null)
									coinfectionSummaryVO.setInvestigatorLastNm(invSumVO.getInvestigatorLastName());
								if (invSumVO.getCaseClassCd() != null)
									coinfectionSummaryVO.setCaseClassCd(invSumVO.getCaseClassCd());
								coinfectionSummaryVO.setPublicHealthCaseUid(invSumVO.getPublicHealthCaseUid());
							} catch (IllegalAccessException e) {
								logger.warn("IllegalAccessException in BeanUtils.copyProperties?");
							} catch (InvocationTargetException e) {
								logger.warn("InvocationTargetException in BeanUtils.copyProperties?");
							}
							coinfectionInvList.add(coinfectionSummaryVO);
						}
				}
		    }
			if (coinfectionInvList==null)
			    	  coinfectionInvList=new ArrayList<Object>();

			Integer queueSize = properties.getQueueSize(NEDSSConstants.MANAGE_CONTACT_ASSOCIATIONS_QUEUE_SIZE);
			assocToCoinfForm.getAttributeMap().put("queueSize", queueSize);

			 if(coinfectionInvList != null)
				 request.setAttribute("queueCount", HTMLEncoder.encodeHtml(String.valueOf(coinfectionInvList.size())));
			 else
				 request.setAttribute("queueCount", HTMLEncoder.encodeHtml("0"));

			 assocToCoinfForm.setCoinfectionSummaryVO(coinfectionInvList);

			 request.setAttribute("coinfectionsSummaryList", coinfectionInvList);
			 if (coinfectionInvList.size() > 0)
				 request.setAttribute("coinfMessage", "The following list of investigations can be associated with the treatment. "
							+" Please select investigations that should be associated with this treatment.");
			 else
				 request.setAttribute("coinfMessage", "There are no investigations to associate this treatment to.");

			 assocToCoinfForm.setPageTitle("Manage Treatment Association to Investigations", request);

			 return PaginationUtil.paginate(assocToCoinfForm, request, "default",mapping);

		} //associateInvestigationsLoad method

		/**
		 * AssociateInterviewToCoinfectionLoad
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 */
		public ActionForward associateInterviewToCoinfectionLoad(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
			AssociateToCoinfectionForm assocToCoinfForm = (AssociateToCoinfectionForm) form;
		    ArrayList<Object> coinfectionInvList = null;
		    Long investigationUid = null;
		    HttpSession session = request.getSession();
			NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session
					.getAttribute("NBSSecurityObject");
			//get the params of the Interview for validating if CoInfection is a candidate for association
			String interviewDateStr = "";
			String interviewType = "";
			 try {
			     	interviewDateStr = request.getParameter("InterviewDate");
			     	interviewType = request.getParameter("InterviewType");
			    } catch (Exception e) {
						logger.warn("In associateToCoinfection - expected Investigation not in scope to check for coinfections..");
			    }

		    try {
		    	String investigationUidStr = (String)  request.getAttribute("DSInvestigationUID");
		    	if (investigationUidStr== null)
		    		  investigationUidStr= (String) NBSContext.retrieve(session, "DSInvestigationUID");
		    	if (investigationUidStr != null)
		    		  investigationUid = new Long(investigationUidStr);
		    } catch (Exception e) {
					logger.warn("In associateToCoinfection - expected Investigation not in scope to check for coinfections..");
		    }
		    String contextAction = request.getParameter("ContextAction");
		    if (investigationUid != null)
				try {
					coinfectionInvList = PageLoadUtil.getSpecificCoinfectionInvList(investigationUid, request);
				} catch (NEDSSAppConcurrentDataException e) {
					logger.error("Data Concurrency Exception in Assoc Interview to Coinfection: " + e.getMessage());
					e.printStackTrace();
				} catch (Exception e) {
					logger.error("Data Concurrency Exception in Assoc Interview to Coinfection: " + e.getMessage());
					e.printStackTrace();
				}
		     String coinfectionId = null;
			 if (coinfectionInvList==null)
			    	  coinfectionInvList=new ArrayList<Object>();
			 else {
			      Iterator<Object>  isvoIter = coinfectionInvList.iterator();
			       while (isvoIter.hasNext()) {
			    	   CoinfectionSummaryVO investigationSummaryVO = (CoinfectionSummaryVO) isvoIter.next();
			    	   if (investigationSummaryVO.getCoinfectionId() != null) {
			    		   coinfectionId = (String) investigationSummaryVO.getCoinfectionId();
			    	       break;
			    	   }//if
			       }//while
			 }
			 assocToCoinfForm.setCoinfectionSummaryVO(coinfectionInvList);
			 // we may need to review what is out there to determine which
			 //coinfections are candidates to associate to the newly created interview
			 Collection<Object> interviewCollection = new ArrayList<Object>();
			 if (coinfectionId != null) {
				 InterviewSummaryDAO interviewSummaryDAO = new InterviewSummaryDAO();
				 interviewCollection = interviewSummaryDAO
						.getInterviewListForCoinfectionId(coinfectionId,
								nbsSecurityObj);
			 }
			 //mark the original inves as read-only
			 coinfectionInvList = (ArrayList<Object>) updateCoinfectionSummaryColl(coinfectionInvList, investigationUid, request);
			 //remove any candidates that fail edit checks and provide message
			 Collection<Object> updatedCoinfectionsColl = updateCoinfectionSummaryCollForInterview(coinfectionInvList, investigationUid, interviewDateStr, interviewType, interviewCollection, new ArrayList<Long>(), request);
			 Integer queueSize = properties.getQueueSize(NEDSSConstants.MANAGE_CONTACT_ASSOCIATIONS_QUEUE_SIZE);
			 assocToCoinfForm.getAttributeMap().put("queueSize", queueSize);

			 if(updatedCoinfectionsColl != null) {
				request.setAttribute("queueCount", HTMLEncoder.encodeHtml(String.valueOf(updatedCoinfectionsColl.size())));
				request.setAttribute("coinfMessage", "The following list of investigations will be associated with this Interview. "
							+" Please deselect investigations that should not be associated with this interview.");
			 } else
				 request.setAttribute("queueCount", HTMLEncoder.encodeHtml("0"));

			 assocToCoinfForm.setCoinfectionSummaryVO(updatedCoinfectionsColl);

			 assocToCoinfForm.getAttributeMap().put(NBSConstantUtil.DSInvestigationUid, investigationUid);
			 assocToCoinfForm.setPageTitle("Manage Co-Infection Associations", request);
			 request.setAttribute("coinfectionsSummaryList",updatedCoinfectionsColl);
			 return PaginationUtil.paginate(assocToCoinfForm, request, "FromPopup",mapping);

		
		}
		/**
		 * Remove items from the list that don't meet the criteria.
		 * Place a message for Request about those investigations that were culled.
		 * @param coinfectionInvList
		 * @param investigationUid
		 * @param interviewType - type of interview - Presumptive, Initial/Original or Re-Interview
		 * @param interviewDateStr - date of interview used for date conflicts
		 * @param interviewCollection - list of Interviews associated with the CoInfection
		 * @param investigationsCurrentlyLinkedToInterview - act relationship list of investigations currently linked to the interview
		 * @param request
		 * @return
		 */
		private Collection<Object> updateCoinfectionSummaryCollForInterview(
				ArrayList<Object> coinfectionInvList,
				Long investigationUid,
				String interviewDateStr,
				String interviewType,
				Collection<Object> interviewCollection,
				ArrayList<Long> investigationsCurrentlyLinkedToInterview,
				HttpServletRequest request) {
			logger.debug("in updateCoinfectionSummaryCollForInterview()");
			CachedDropDownValues cdv = new CachedDropDownValues();

			if (coinfectionInvList.size() < 2)
				return coinfectionInvList; //no list editing to do


			//save the co-infections that are currently linked to the interview
			ArrayList<Object> currentlyLinkedList = new ArrayList<Object>();
			if (!investigationsCurrentlyLinkedToInterview.isEmpty())
				currentlyLinkedList = getInvestigationsCurrentlyLinkedToInterview(coinfectionInvList, investigationsCurrentlyLinkedToInterview);
			StringBuffer culledInvSB = new StringBuffer();
		    Iterator<Object>  isvoIter = coinfectionInvList.iterator();
		    while (isvoIter.hasNext()) {
		    	   CoinfectionSummaryVO investigationSummaryVO = (CoinfectionSummaryVO) isvoIter.next();
		    	   if (investigationSummaryVO.getPatIntvStatusCd()  == null || !investigationSummaryVO.getPatIntvStatusCd().equals(CTConstants.PatientInterviewedStatusCd)) {
		    		   String conditionDesc = cdv.getConditionDesc(investigationSummaryVO.getConditionCd());
		    		   culledInvSB.append("<ul><li>").append(conditionDesc + " Co-Infection ("+investigationSummaryVO.getLocalId() +") Patient Interview Status is not Interviewed.").append("</li></ul>");
		    				isvoIter.remove();
		    	   }//if
		     }//while
			//convert the date string into a timestamp
			java.sql.Timestamp interviewDate = null;
			try {
			  DateFormat formatter;
			  formatter = new SimpleDateFormat("MM/dd/yyyy");
		      Date ixsDate = (Date) formatter.parse(interviewDateStr);
		      interviewDate = new Timestamp(ixsDate.getTime());
		    } catch (ParseException e) {
		    	logger.error("updateCoinfectionSummaryCollForInterview: Error parsing interview date");
		    }
			String culledMessage = "";
			if (interviewType.equals("INITIAL")) {
				culledMessage = processCoinfectionListForInitial(coinfectionInvList, investigationUid, interviewCollection);
			}
			if (!culledMessage.isEmpty())
				culledInvSB.append(culledMessage);
			String culledCoinfectionsMessage = processCoinfectionListForDateConflicts(interviewType, interviewDate, coinfectionInvList, investigationUid, interviewCollection, culledInvSB.toString());
			if (!culledCoinfectionsMessage.isEmpty())
				request.setAttribute("RemovedInvestigationsInfoMsg", culledCoinfectionsMessage);
			if (!currentlyLinkedList.isEmpty())
				coinfectionInvList.addAll(currentlyLinkedList); //add currently linked back in..
			return (coinfectionInvList);
		}

		/**
		 *  getInvestigationsCurrentlyLinkedToInterview
		 * @param coinfectionInvList
		 * @param investigationsCurrentlyLinkedToInterview
		 * @return ArrayList of PublicHealthCaseUids
		 */
		private ArrayList<Object> getInvestigationsCurrentlyLinkedToInterview(
				ArrayList<Object> coinfectionInvList,
				ArrayList<Long> investigationsCurrentlyLinkedToInterview) {

			ArrayList<Object> returnList= new ArrayList<Object>();
			Iterator curInvIter = investigationsCurrentlyLinkedToInterview.iterator();
			try {
				while (curInvIter.hasNext()) {
					Long thisInvUid = (Long) curInvIter.next();
					Iterator<Object>  isvoIter = coinfectionInvList.iterator();
					while (isvoIter.hasNext()) {
			    	   CoinfectionSummaryVO investigationSummaryVO = (CoinfectionSummaryVO) isvoIter.next();
			    	   if (investigationSummaryVO.getPublicHealthCaseUid().longValue() == thisInvUid.longValue()) {
			    		   returnList.add(investigationSummaryVO);
			    		   isvoIter.remove();
			    		   break;
			    	   }
					}//Investigation Summary Iterator
				} //Uids currently linked
			  } catch (Exception ex) {
				  logger.error("Exception in AssociateToCoinfectionAction.getInvestigationsCurrentlyLinkedToInterview encountered.." + ex.getMessage());
				  ex.printStackTrace();
			  }
			return returnList;
		}

		/**
		 * getCoinfectionIdFromInvestigationList
		 * @param coinfectionInvList
		 * @return
		 */
		private String getCoinfectionIdFromInvestigationList(ArrayList<Object> coinfectionInvList) {
	      Iterator<Object>  isvoIter = coinfectionInvList.iterator();
	       while (isvoIter.hasNext()) {
	    	   CoinfectionSummaryVO investigationSummaryVO = (CoinfectionSummaryVO) isvoIter.next();
	    	   if (investigationSummaryVO.getCoinfectionId() != null) {
	    		   return (investigationSummaryVO.getCoinfectionId());
	    	   }//if
	       }//while
	       return(null);
		}
		/**
		 * processCoinfectionListForInitial - Only one Initial/Original is allowed per investigation. Remove any candidates
		 * that already have an Initial/Original Interview and produce a message.
		 * @param investigationColl
		 * @param investigationUid
		 * @param interviewCollection
		 * @return
		 */
	private String processCoinfectionListForInitial(
			Collection<Object> investigationColl, Long investigationUid,
			Collection<Object> interviewCollection) {

		CachedDropDownValues cdv = new CachedDropDownValues();
		StringBuffer sb = new StringBuffer();
		Iterator<Object> iter = investigationColl.iterator();
		try {
			while (iter.hasNext()) {
				CoinfectionSummaryVO coinfectionSummaryDT = (CoinfectionSummaryVO) iter
						.next();
				if (coinfectionSummaryDT.getPublicHealthCaseUid().longValue() == investigationUid
						.longValue())
					continue;// we won't cull the original
				String conditionDesc = cdv
						.getConditionDesc(coinfectionSummaryDT.getConditionCd());
				if (investigationAlreadyHasInitialInterview(
						coinfectionSummaryDT.getPublicHealthCaseUid(),
						interviewCollection)) {
					sb.append("<ul><li>")
							.append(conditionDesc
									+ " Co-Infection ("
									+ coinfectionSummaryDT.getLocalId()
									+ ") already has an Initial/Original Interview")
							.append("</li></ul>");
					iter.remove(); // remove this candidate from the list
				}
			}
		} catch (Exception ex) {
			logger.error("Exception in AssociateToCoinfectionAction.processCoinfectionListForInitial encountered.."
					+ ex.getMessage());
			ex.printStackTrace();
		}
		return sb.toString();
	}

		/**
		 * Walk through the list of interviews and see if the investigation already has an Initial Interview.
		 * @param publicHealthCaseUid
		 * @param interviewCollection
		 * @return
		 */
		private boolean investigationAlreadyHasInitialInterview(
				Long publicHealthCaseUid, Collection<Object> interviewCollection) {
    		Iterator<Object> iter = interviewCollection.iterator();
			while(iter.hasNext()){
				InterviewSummaryDT interviewSummaryDT = (InterviewSummaryDT) iter.next();
				if (interviewSummaryDT.getPublicHealthCaseUid().longValue() != publicHealthCaseUid.longValue())
					continue;
				if (interviewSummaryDT.getInterviewTypeCd().equalsIgnoreCase(NEDSSConstants.INITIAL_ORIGINAL_INTERVIEW_TYPE))
					return true;
			}
			return false;
		}

		/**
		 * Check for date conflicts with the co-infections existing interviews.
		 *
		 * @param interviewType - type of the new interview
		 * @param interviewDate - date of the new interview
		 * @param investigationColl - all coinfection investigations
		 * @param investigationUid - current investigation
		 * @param interviewCollection - all interviews assoc with the coinfection
		 * @param culledCoinfectionsMessage - existing investigations removed message
		 * @return date conflict message or null
		 */
	private String processCoinfectionListForDateConflicts(String interviewType,
			Timestamp interviewDate, Collection<Object> investigationColl,
			Long investigationUid, Collection<Object> interviewCollection,
			String culledCoinfectionsMessage) {

		logger.debug("in processCoinfectionListForDateConflicts()");
		CachedDropDownValues cdv = new CachedDropDownValues();
		StringBuffer sb = new StringBuffer(culledCoinfectionsMessage);
		Iterator<Object> iter = investigationColl.iterator();
		// traverse the list of investigations
		try {
			while (iter.hasNext()) {
				CoinfectionSummaryVO coinfectionSummaryDT = (CoinfectionSummaryVO) iter
						.next();
				if (coinfectionSummaryDT.getPublicHealthCaseUid().longValue() == investigationUid
						.longValue())
					continue;// no need to check the original
				String conditionDesc = cdv
						.getConditionDesc(coinfectionSummaryDT.getConditionCd());
				String dateConflictMsg = investigationHasInterviewDateConflict(
						interviewType, interviewDate,
						coinfectionSummaryDT.getPublicHealthCaseUid(),
						interviewCollection);
				if (dateConflictMsg != null && !dateConflictMsg.isEmpty()) {
					sb.append("<ul><li>")
							.append(conditionDesc + " Co-Infection ("
									+ coinfectionSummaryDT.getLocalId() + ") "
									+ dateConflictMsg).append("</li></ul>");
					iter.remove(); // remove this candidate from the list
				}
			}
		} catch (Exception ex) {
			logger.error("Exception in AssociateToCoinfectionAction.processCoinfectionListForDateConflicts encountered.."
					+ ex.getMessage());
			ex.printStackTrace();
		}
		logger.debug("leaving processCoinfectionListForDateConflicts()");
		return sb.toString();
	}

		/**
		 * Check for Date Conflicts with the coinfections existing interviews.
		 * @param interviewType - new Interview Type
		 * @param interviewDate - new date of interview
		 * @param publicHealthCaseUid - of the coinfection candidate investigation
		 * @param interviewCollection
		 * @return
		 */
	private String investigationHasInterviewDateConflict(String interviewType,
			Timestamp interviewDate, Long publicHealthCaseUid,
			Collection<Object> interviewCollection) {

		logger.debug("in investigationHasInterviewDateConflict()");
		boolean originalInterviewFound = false;
		Iterator<Object> iter = interviewCollection.iterator();
		try {
			while (iter.hasNext()) {
				InterviewSummaryDT interviewSummaryDT = (InterviewSummaryDT) iter
						.next();
				if (interviewSummaryDT.getPublicHealthCaseUid().longValue() != publicHealthCaseUid
						.longValue())
					continue;
				// Note if there is an original/initial interview
				if (interviewSummaryDT.getInterviewTypeCd().equals(
						NEDSSConstants.INITIAL_ORIGINAL_INTERVIEW_TYPE))
					originalInterviewFound = true;
				// check for date conflicts
				if (interviewType
						.equals(NEDSSConstants.REINTERVIEW_INTERVIEW_TYPE)) {
					if (interviewSummaryDT.getInterviewDate().after(
							interviewDate)
							&& !interviewSummaryDT
									.equals(NEDSSConstants.REINTERVIEW_INTERVIEW_TYPE))
						return (" has a non-Re-Interview that has a Date of Interview after the interview date");
				}
				if (interviewType
						.equals(NEDSSConstants.INITIAL_ORIGINAL_INTERVIEW_TYPE)) {
					if (interviewSummaryDT.getInterviewDate().before(
							interviewDate)
							&& interviewSummaryDT
									.equals(NEDSSConstants.REINTERVIEW_INTERVIEW_TYPE))
						return (" has a Re-Interview that has a Date of Interview before the Initial/Original interview date");
					if (interviewSummaryDT.getInterviewDate().after(
							interviewDate)
							&& interviewSummaryDT
									.equals(NEDSSConstants.PRESUMPTIVE_INTERVIEW_TYPE))
						return (" has a Presumptive interview that has a Date of Interview after the Initial/Original interview date");
				}
				if (interviewType
						.equals(NEDSSConstants.PRESUMPTIVE_INTERVIEW_TYPE)) {
					if (interviewSummaryDT.getInterviewDate().before(
							interviewDate)
							&& !interviewSummaryDT
									.equals(NEDSSConstants.PRESUMPTIVE_INTERVIEW_TYPE))
						return (" has an non-Presumptive Interview that has a Date of Interview before the Presumptive interview date");

				}
			}

			if (!originalInterviewFound
					&& interviewType
							.equals(NEDSSConstants.REINTERVIEW_INTERVIEW_TYPE))
				return (" can not have a Re-Interview without an Original/Initial interview");
		} catch (Exception ex) {
			logger.error("Exception in AssociateToCoinfectionAction.investigationHasInterviewDateConflict encountered.."
					+ ex.getMessage());
			ex.printStackTrace();
		}
		return null;
	}
		/**
		 * Called from View Interview Associate Investigations button- for an Interview which is associated to an Investigation which is a Co-Infection investigation,
		 * The interview can be associated or unassociated with coinfections, including the current Investigation if it is
		 * not the 'Source' investigation. Per the business rules, we will disable the unlinking of the investigation to the that first created the interview.
		 * This may or may not be the DSInvestigation in scope.
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 */
	public ActionForward associateInterviewInvestigationsLoad(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		AssociateToCoinfectionForm assocToCoinfForm = (AssociateToCoinfectionForm) form;
		ArrayList<Object> coinfectionInvList = new ArrayList<Object>();
		ArrayList<Object> investigationSummaryVOs = new ArrayList<Object>();

		try {
			Long investigationUid = null;
			HttpSession session = request.getSession();
			NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session
					.getAttribute("NBSSecurityObject");
			logger.debug("in associateInterviewInvestigationsLoad()");
			// Get Context Action
			String contextAction = request.getParameter("ContextAction");
			request.setAttribute("ContextAction", HTMLEncoder.encodeHtml(contextAction));
			// get InterviewUid we are viewing
			String actUidStr = request.getParameter("actUidStr");
			logger.debug("Interview Uid=" + actUidStr);
			Long actUid = new Long(actUidStr);
			assocToCoinfForm.setObsUid(actUid);

			// get the current investigation from context
			try {
				String investigationUidStr = (String) NBSContext.retrieve(
						session, "DSInvestigationUID");
				if (investigationUidStr != null)
					investigationUid = new Long(investigationUidStr); // set to
																		// current
																		// investigation
			} catch (Exception e) {
				logger.debug("In associateInterviewInvestigationsLoad - expected Investigation not in scope to check for coinfections..");
			}

			// we expect current investigation to be in scope
			if (investigationUid != null) {

				try {
					coinfectionInvList = PageLoadUtil
							.getSpecificCoinfectionInvList(investigationUid,
									request);
				} catch (NEDSSAppConcurrentDataException e) {
					logger.error("In associateInterviewInvestigationsLoad() Conurrent data access for Interview="
							+ actUidStr);
					e.printStackTrace();
				} catch (Exception e) {
					logger.error("Exception encontered - Interview="
							+ actUidStr);
					e.printStackTrace();
				}
				String coinfectionId = null;
				if (coinfectionInvList == null)
					coinfectionInvList = new ArrayList<Object>();
				else
					coinfectionId = getCoinfectionIdFromInvestigationList(coinfectionInvList);

				// we may need to review what is out there to determine which
				// coinfections are candidates to associate to the newly created
				// interview
				Collection<Object> interviewCollection = new ArrayList<Object>();
				if (coinfectionId != null) {
					InterviewSummaryDAO interviewSummaryDAO = new InterviewSummaryDAO();
					interviewCollection = interviewSummaryDAO
							.getInterviewListForCoinfectionId(coinfectionId,
									nbsSecurityObj);
				}

				// find the interview that matches the actUidStr and get the
				// source Investigation
				Long sourceInvestigationUid = null;
				InterviewSummaryDT thisInterviewSummaryDT = null;
				ArrayList<Long> currentlyLinkedList = new ArrayList<Long>();
				Iterator iterIxs = interviewCollection.iterator();
				while (iterIxs.hasNext()) {
					InterviewSummaryDT interviewSummaryDT = (InterviewSummaryDT) iterIxs
							.next();
					if (interviewSummaryDT.getInterviewUid().longValue() == actUid
							.longValue()) {
						thisInterviewSummaryDT = interviewSummaryDT;
						currentlyLinkedList.add(interviewSummaryDT
								.getPublicHealthCaseUid());
						if (interviewSummaryDT.getAddReasonCd() != null)
							sourceInvestigationUid = interviewSummaryDT
									.getPublicHealthCaseUid();
					}
				}
				// don't allow the user to de-select the source investigation..
				if (sourceInvestigationUid != null) {
					coinfectionInvList = (ArrayList<Object>) updateCoinfectionSummaryColl(
							coinfectionInvList, actUid, interviewCollection,
							sourceInvestigationUid, request);
				} else
					logger.warn("in associateInterviewInvestigationsLoad  Warning: Originating PHC not found for Interview="
							+ actUidStr);

				// Remove any co-infection investigations that are not
				// candidates to include the interview.
				// Provide a message to the user why they are not included.
				if (thisInterviewSummaryDT != null) {
					String interviewDateStr = "";
					if (thisInterviewSummaryDT.getInterviewDate() != null) {
						DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
						interviewDateStr = df.format(thisInterviewSummaryDT
								.getInterviewDate());
					}
					coinfectionInvList = (ArrayList<Object>) updateCoinfectionSummaryCollForInterview(
							coinfectionInvList, investigationUid,
							interviewDateStr,
							thisInterviewSummaryDT.getInterviewTypeCd(),
							interviewCollection, currentlyLinkedList, request);
				} // thisInterviewSummaryDT != null

			}// investigationUid != null

			if (coinfectionInvList == null)
				coinfectionInvList = new ArrayList<Object>();
			// save the list
			assocToCoinfForm.setInvSummaryVOColl(coinfectionInvList); // save
																		// current
																		// state
			Integer queueSize = properties
					.getQueueSize(NEDSSConstants.MANAGE_CONTACT_ASSOCIATIONS_QUEUE_SIZE);
			assocToCoinfForm.getAttributeMap().put("queueSize", queueSize);

			if (coinfectionInvList != null)
				request.setAttribute("queueCount",
						HTMLEncoder.encodeHtml(String.valueOf(coinfectionInvList.size())));
			else
				request.setAttribute("queueCount", HTMLEncoder.encodeHtml("0"));

			assocToCoinfForm.setCoinfectionSummaryVO(coinfectionInvList);

			request.setAttribute("coinfectionsSummaryList", coinfectionInvList);
			if (coinfectionInvList.size() > 0)
				request.setAttribute(
						"coinfMessage",
						"The following list of investigations can be associated with the Interview. "
								+ " Please select any Co-Infection investigations that should also be associated with this interview.");
			else
				request.setAttribute("coinfMessage",
						"There are no investigations to associate this Interview to.");

			assocToCoinfForm
					.setPageTitle(
							"Manage Interview Association to Co-Infection Investigations",
							request);

		} catch (Exception ex) {
			logger.error("Exception in AssociateToCoinfectionAction.associateInterviewInvestigationsLoad encountered.."
					+ ex.getMessage());
			ex.printStackTrace();
		}

		return PaginationUtil.paginate(assocToCoinfForm, request, "FromPopup",
				mapping);

	} // associateInterviewInvestigationsLoad method


		/**
		 * Called from View Contact Associate Investigations button- for a contact which is associated to an Investigation which is a Co-Infection investigation,
		 *
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 */
	public ActionForward associateContactInvestigationsLoad(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		AssociateToCoinfectionForm assocToCoinfForm = (AssociateToCoinfectionForm) form;
		CTContactProxyVO contactProxyVO = null;
		ArrayList<Object> coinfectionInvList = new ArrayList<Object>();
		// ArrayList<Object> investigationSummaryVOs = new ArrayList<Object> ();
		ArrayList<Object> contactInvList = new ArrayList<Object>();
		ArrayList<Object> displayList = new ArrayList<Object>();

		try {
			Long investigationUid = null;
			HttpSession session = request.getSession();
			NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session
					.getAttribute("NBSSecurityObject");
			logger.debug("in associateInterviewInvestigationsLoad()");
			// Get Context Action
			String contextAction = request.getParameter("ContextAction");
			request.setAttribute("ContextAction", HTMLEncoder.encodeHtml(contextAction));
			// get InvestigationUid we are viewing
			String invUidStr = request.getParameter("investigationUidStr");
			logger.debug("Investigation Uid from request =" + invUidStr);
			investigationUid = new Long(invUidStr);
			assocToCoinfForm.setOrigCaseUid(investigationUid);

			logger.info("investigationUid ------------------------------------ "
					+ investigationUid);
			if (investigationUid != null) {

				try {
					coinfectionInvList = PageLoadUtil
							.getSpecificCoinfectionInvList(investigationUid,
									request);
					assocToCoinfForm
							.setCoinfectionSummaryVO(coinfectionInvList);
				} catch (NEDSSAppConcurrentDataException e) {
					logger.error("In associateInterviewInvestigationsLoad() Conurrent data access for Interview="
							+ invUidStr);
					e.printStackTrace();
				} catch (Exception e) {
					logger.error("Exception encontered - Interview="
							+ invUidStr);
					e.printStackTrace();
				}
				String coinfectionId = null;
				if (coinfectionInvList == null)
					coinfectionInvList = new ArrayList<Object>();
				else {
					Iterator<Object> isvoIter = coinfectionInvList.iterator();
					while (isvoIter.hasNext()) {
						CoinfectionSummaryVO investigationSummaryVO = (CoinfectionSummaryVO) isvoIter
								.next();
						if (investigationSummaryVO.getCoinfectionId() != null) {
							coinfectionId = (String) investigationSummaryVO
									.getCoinfectionId();
							break;
						}// if
					}// while
				}

				// String programAreaCode=null;
				// find the investigation that matches the investigationUid,
				// disable co-infection investigation checkbox if user viewing
				// contact from same investigation
				Iterator iterInv = coinfectionInvList.iterator();
				while (iterInv.hasNext()) {
					CoinfectionSummaryVO intSummaryVO = (CoinfectionSummaryVO) iterInv
							.next();
					logger.info("intSummaryVO.getPublicHealthCaseUid()------------------------"
							+ intSummaryVO.getPublicHealthCaseUid());
					if (intSummaryVO.getPublicHealthCaseUid().longValue() == investigationUid
							.longValue()) {
						intSummaryVO.setDisabled("disabled=\"disabled\"");
						// Set associated true to select checkbox
						intSummaryVO.setAssociated(true);
						// programAreaCode = intSummaryVO.getProgAreaCd();
					} else {
						intSummaryVO.setDisabled("");
					}
				}

				// Start - get list of investigations associated with Contact
				String dsContactUID = (String) NBSContext.retrieve(
						request.getSession(), NBSConstantUtil.DSContactUID);
				assocToCoinfForm.setObsUid(new Long(dsContactUID));
				logger.info("DSContactUID----------------------------------------"
						+ dsContactUID);

				try {
					contactProxyVO = CTContactLoadUtil.populateProxyVO(
							dsContactUID, request);

					long contactInterviewId = contactProxyVO.getcTContactVO()
							.getcTContactDT().getNamedDuringInterviewUid();
					logger.debug("IterviewId from CTContactProxyVO ----------------- "
							+ contactInterviewId);

					// if contact has interview record associated.
					if (CTConstants.StdInitiatedWithoutInterviewLong != contactInterviewId) {
						updateCoinfectionInvestigationListBasedOnInterview(
								contactInterviewId, coinfectionInvList,
								coinfectionId, nbsSecurityObj);
					}

					Long personParentUid = contactProxyVO.getContactPersonVO()
							.getThePersonDT().getPersonParentUid();
					logger.info("person parent id ---------------"
							+ contactProxyVO.getContactPersonVO()
									.getThePersonDT().getPersonParentUid());
					contactInvList = PageLoadUtil.getPersonInvList(
							personParentUid, request);
					logger.info("contactInvList.size() ------------------------------ "
							+ contactInvList.size());

					ArrayList<Object> invListToRemove = new ArrayList<Object>();

					for (int i = 0; i < contactInvList.size(); i++) {
						InvestigationSummaryVO investigationSummaryVO = (InvestigationSummaryVO) contactInvList
								.get(i);

						// Remove unwanted contact's investigations from list.
						// To display open investigation / most recent closed
						// investigation

						if (NEDSSConstants.STATUS_OPEN
								.equals(investigationSummaryVO
										.getInvestigationStatusCd())) {
							for (int j = 0; j < contactInvList.size(); j++) {
								InvestigationSummaryVO invToCheck = (InvestigationSummaryVO) contactInvList
										.get(j);
								if (investigationSummaryVO.getCd().equals(
										invToCheck.getCd())
										&& NEDSSConstants.INVESTIGATION_STATUS_CODE_CLOSED
												.equals(invToCheck
														.getInvestigationStatusCd())) {
									invListToRemove.add(invToCheck);
								}
							}
						}

						if (NEDSSConstants.INVESTIGATION_STATUS_CODE_CLOSED
								.equals(investigationSummaryVO
										.getInvestigationStatusCd())) {
							for (int j = 0; j < contactInvList.size(); j++) {
								InvestigationSummaryVO invToCheck = (InvestigationSummaryVO) contactInvList
										.get(j);

								if (investigationSummaryVO.getCd().equals(
										invToCheck.getCd())
										&& investigationSummaryVO
												.getActivityFromTime()
												.before(invToCheck
														.getActivityFromTime())
										&& NEDSSConstants.INVESTIGATION_STATUS_CODE_CLOSED
												.equals(invToCheck
														.getInvestigationStatusCd())) {
									invListToRemove.add(investigationSummaryVO);
								} else if (investigationSummaryVO.getCd()
										.equals(invToCheck.getCd())
										&& investigationSummaryVO
												.getActivityFromTime()
												.after(invToCheck
														.getActivityFromTime())
										&& NEDSSConstants.INVESTIGATION_STATUS_CODE_CLOSED
												.equals(invToCheck
														.getInvestigationStatusCd())) {
									invListToRemove.add(invToCheck);
								}
							}
						}

					}

					contactInvList.removeAll(invListToRemove);

					// Set processing decision for coinfection investigations
					for (int i = 0; i < coinfectionInvList.size(); i++) {
						CoinfectionSummaryVO patientCoinfSummary = (CoinfectionSummaryVO) coinfectionInvList
								.get(i);
						if (patientCoinfSummary != null) {
							String codeSetName = PageLoadUtil
									.getProcessingDecisionForCoinfectionContacts(
											contactInvList, patientCoinfSummary
													.getConditionCd(), request);
							logger.info("codeSetName----------------------------"
									+ codeSetName);
							patientCoinfSummary
									.setProcessingDecisionCode(codeSetName);
							if (codeSetName
									.equals(CTConstants.StdCrProcessingDecisionNoInvDispo)) {
								// Secondary Referral
								InvestigationSummaryVO invSummVO = (InvestigationSummaryVO) NBSContext
										.retrieve(
												request.getSession(),
												NBSConstantUtil.DSSecondaryReferralInvSummVO);
								assocToCoinfForm
										.getAttributeMap()
										.put("SR"
												+ patientCoinfSummary
														.getConditionCd(),
												invSummVO);
							} else if (codeSetName
									.equals(CTConstants.StdCrProcessingDecisionInvDispoClosed)) {
								// Secondary Referral
								InvestigationSummaryVO invSummVO = (InvestigationSummaryVO) NBSContext
										.retrieve(
												request.getSession(),
												NBSConstantUtil.DSRecordSearchClosureInvSummVO);
								assocToCoinfForm
										.getAttributeMap()
										.put("RSC"
												+ patientCoinfSummary
														.getConditionCd(),
												invSummVO);
							}
						}
					}

					// start
					// Populate co-infection investigation list to display on
					// popup

					for (int i = 0; i < coinfectionInvList.size(); i++) {
						CoinfectionSummaryVO patientCoinfSummary = (CoinfectionSummaryVO) coinfectionInvList
								.get(i);
						logger.debug("patientCoinfSummary.getConditionCd()------------------"
								+ patientCoinfSummary.getConditionCd());

						CoinfectionSummaryVO summaryVo = null;
						for (int j = 0; j < contactInvList.size(); j++) {
							InvestigationSummaryVO investigationSummaryVO = (InvestigationSummaryVO) contactInvList
									.get(j);

							if (investigationSummaryVO != null
									&& patientCoinfSummary != null
									&& patientCoinfSummary.getConditionCd()
											.equals(investigationSummaryVO
													.getCd())) {
								summaryVo = new CoinfectionSummaryVO();
								summaryVo.setAssociated(patientCoinfSummary
										.isAssociated());
								summaryVo
										.setPublicHealthCaseUid(patientCoinfSummary
												.getPublicHealthCaseUid()); // Patients
																			// public
																			// health
																			// case
																			// uid
								summaryVo.setCheckBoxId(investigationSummaryVO
										.getCheckBoxId());
								summaryVo.setDisabled(patientCoinfSummary
										.getDisabled());
								summaryVo
										.setInvestigationStartDate(investigationSummaryVO
												.getActivityFromTime());
								summaryVo
										.setInvestigationStatus(investigationSummaryVO
												.getInvestigationStatusCd());
								summaryVo.setCaseClassCd(investigationSummaryVO
										.getCaseClassCd());
								summaryVo.setConditionCd(investigationSummaryVO
										.getCd());
								summaryVo
										.setJurisdictionCd(investigationSummaryVO
												.getJurisdictionCd());
								summaryVo.setLocalId(investigationSummaryVO
										.getLocalId());
								summaryVo
										.setCoinfectionId(investigationSummaryVO
												.getCoinfectionId());
								summaryVo
										.setProcessingDecisionCode(patientCoinfSummary
												.getProcessingDecisionCode());
								summaryVo
										.setIntestigatorFirstNm(investigationSummaryVO
												.getInvestigatorFirstName());
								summaryVo
										.setInvestigatorLastNm(investigationSummaryVO
												.getInvestigatorLastName());

								displayList.add(summaryVo);

								// Populate investigation data for associated
								// investigation on Manage Contact Association
								// to Co-infection investigations screen (if
								// view contact from Gonorrhea then populate
								// contac's Gonorrhea investigation details)
								if (patientCoinfSummary.isAssociated()) {
									PageProxyVO pageProxyVO = PageLoadUtil
											.getProxyObject(
													Long.toString(investigationSummaryVO
															.getPublicHealthCaseUid()),
													request.getSession());
									// Save contacts existing inv to get
									// CoInfectionID later
									if (contactProxyVO != null
											&& contactProxyVO.getcTContactVO()
													.getcTContactDT()
													.getContactEntityPhcUid() != null
											&& contactProxyVO.getcTContactVO()
													.getcTContactDT()
													.getContactEntityPhcUid()
													.longValue() == investigationSummaryVO
													.getPublicHealthCaseUid()
													.longValue())
										assocToCoinfForm
												.setContactPageProxyVO(pageProxyVO);
									if (pageProxyVO != null) {
										PublicHealthCaseVO phcVO = pageProxyVO
												.getPublicHealthCaseVO();

										SimpleDateFormat formatter = new SimpleDateFormat(
												"MM/dd/yyyy");
										Date date = null;

										if (phcVO != null) {

											if (phcVO
													.getThePublicHealthCaseDT() != null
													&& phcVO.getThePublicHealthCaseDT()
															.getActivityFromTime() != null) {
												date = new Date(
														phcVO.getThePublicHealthCaseDT()
																.getActivityFromTime()
																.getTime());
												assocToCoinfForm
														.getcTContactClientVO()
														.setAnswer(
																PageConstants.INV_START_DATE,
																formatter
																		.format(date));
											}

											if (phcVO
													.getThePublicHealthCaseDT() != null
													&& phcVO.getThePublicHealthCaseDT()
															.getInvestigatorAssignedTime() != null) {
												date = new Date(
														phcVO.getThePublicHealthCaseDT()
																.getActivityFromTime()
																.getTime());
												assocToCoinfForm
														.getcTContactClientVO()
														.setAnswer(
																PageConstants.DATE_ASSIGNED_TO_INVESTIGATION,
																formatter
																		.format(date));
											}

											if (phcVO.getTheCaseManagementDT() != null) {
												assocToCoinfForm
														.getcTContactClientVO()
														.setAnswer(
																PageConstants.INTERNET_FOLLOWUP,
																phcVO.getTheCaseManagementDT()
																		.getInternetFollUp());
												assocToCoinfForm
														.getcTContactClientVO()
														.setAnswer(
																PageConstants.NOTIFIABLE,
																phcVO.getTheCaseManagementDT()
																		.getInitFollUpNotifiable());
											}

											assocToCoinfForm
													.getAttributeMap()
													.put(PageConstants.INVESTIGATOR_UID,
															Long.toString(investigationSummaryVO
																	.getInvestigatorMPRUid()));
											assocToCoinfForm
													.getAttributeMap()
													.put(PageConstants.INVESTIGATOR_SEARCH_RESULT,
															investigationSummaryVO
																	.getInvestigatorFirstName()
																	+ " "
																	+ investigationSummaryVO
																			.getInvestigatorLastName());

										}
									}
								}
							}
						}

						if (summaryVo == null) {
							summaryVo = new CoinfectionSummaryVO();
							summaryVo.setConditionCd(patientCoinfSummary
									.getConditionCd());
							summaryVo
									.setProcessingDecisionCode(patientCoinfSummary
											.getProcessingDecisionCode());
							summaryVo
									.setPublicHealthCaseUid(patientCoinfSummary
											.getPublicHealthCaseUid()); // Patients
																		// public
																		// health
																		// case
																		// uid
							summaryVo.setAssociated(patientCoinfSummary
									.isAssociated());
							summaryVo.setDisabled(patientCoinfSummary
									.getDisabled());

							displayList.add(summaryVo);
						}
					}
					// end

				} catch (IOException e) {
					logger.error("associateContactInvestigationsLoad IOException: "
							+ e.getMessage());
					e.printStackTrace();
				} catch (ServletException e) {
					logger.error("associateContactInvestigationsLoad ServletException: "
							+ e.getMessage());
					e.printStackTrace();
				} catch (Exception e) {
					logger.error("associateContactInvestigationsLoad Exception: "
							+ e.getMessage());
					e.printStackTrace();
				}
				// End
			}// investigationUid != null

			if (coinfectionInvList == null)
				coinfectionInvList = new ArrayList<Object>();
			// save the list

			Integer queueSize = properties
					.getQueueSize(NEDSSConstants.MANAGE_CONTACT_ASSOCIATIONS_QUEUE_SIZE);
			assocToCoinfForm.getAttributeMap().put("queueSize", queueSize);

			if (displayList != null)
				request.setAttribute("queueCount",
						HTMLEncoder.encodeHtml(String.valueOf(displayList.size())));
			else
				request.setAttribute("queueCount", HTMLEncoder.encodeHtml("0"));

			request.setAttribute("coinfectionsSummaryList", displayList); // save
																			// current
																			// state
			if (displayList.size() > 0) {
				String contactDispName = "";
				if (contactProxyVO != null
						&& contactProxyVO.getContactPersonVO().thePersonDT
								.getFirstNm() != null)
					contactDispName = contactProxyVO.getContactPersonVO().thePersonDT
							.getFirstNm() + " ";
				if (contactProxyVO != null
						&& contactProxyVO.getContactPersonVO().thePersonDT
								.getLastNm() != null)
					contactDispName = contactDispName
							+ contactProxyVO.getContactPersonVO().thePersonDT
									.getLastNm() + " ";
				request.setAttribute(
						"coinfMessage",
						"The following list of open co-infection investigations can be associated with the "
								+ contactDispName
								+ "contact record. "
								+ " Please select any Co-Infection investigations that should also be associated with this contact.");
			} else
				request.setAttribute("coinfMessage",
						"There are no investigations to associate this Contact.");

			assocToCoinfForm
					.setPageTitle(
							"Manage Contact Association to Co-Infection Investigations",
							request);

			logger.debug("leaving associateInterviewInvestigationsLoad()");

		} catch (Exception ex) {
			logger.error("Exception in AssociateToCoinfectionAction.associateContactInvestigationsLoad encountered.."
					+ ex.getMessage());
			ex.printStackTrace();
		}

		return PaginationUtil.paginate(assocToCoinfForm, request, "ForContact",
				mapping);

	}


		/**
		 * updates coinfectionInvList based on interview associated with other investigations
		 *
		 * @param interviewId
		 * @param coinfectionInvList
		 * @param coinfectionId
		 * @param nbsSecurityObj
		 */
	private void updateCoinfectionInvestigationListBasedOnInterview(
			long interviewId, ArrayList coinfectionInvList,
			String coinfectionId, NBSSecurityObj nbsSecurityObj)
			throws Exception {

		// Get list of interviews associated with current investigation
		Collection<Object> interviewCollectionForCoinfection = new ArrayList<Object>();

		try {
			if (coinfectionId != null) {
				InterviewSummaryDAO interviewSummaryDAO = new InterviewSummaryDAO();
				interviewCollectionForCoinfection = interviewSummaryDAO
						.getInterviewListForCoinfectionId(coinfectionId,
								nbsSecurityObj);
			}

			logger.debug("interviewCollectionForCoinfection.size()------------------------"
					+ interviewCollectionForCoinfection.size());

			// collect publicHealthCaseID from interviewCollectionForCoinfection
			// for which same interview associated.
			ArrayList<Long> phcForSameIxsList = new ArrayList<Long>();

			Iterator iterIxs = interviewCollectionForCoinfection.iterator();
			while (iterIxs.hasNext()) {
				InterviewSummaryDT interviewSummaryDT = (InterviewSummaryDT) iterIxs
						.next();
				if (interviewId == interviewSummaryDT.getInterviewUid()
						.longValue()) {
					logger.debug("interviewSummaryDT.getPublicHealthCaseUid()--------------"
							+ interviewSummaryDT.getPublicHealthCaseUid());
					phcForSameIxsList.add(interviewSummaryDT
							.getPublicHealthCaseUid());
				}
			}

			logger.debug("phcForSameIxsList.size()--------------------------------- "
					+ phcForSameIxsList.size());

			// Narrow down coinfectionInvList based on interview

			logger.debug("coinfectionInvList original size ----------------------- "
					+ coinfectionInvList.size());
			ArrayList<Object> invsToRemove = new ArrayList<Object>();

			for (int i = 0; i < coinfectionInvList.size(); i++) {
				CoinfectionSummaryVO patientCoinfSummary = (CoinfectionSummaryVO) coinfectionInvList
						.get(i);

				// If the phcForSameIxsList contain investigations then keep it
				// otherwise remove from list.
				if (phcForSameIxsList.contains(patientCoinfSummary
						.getPublicHealthCaseUid())) {
					logger.debug("investigation exist in list.........................");
				} else {
					logger.debug("investigation not exist in list so Removed...................");
					invsToRemove.add(patientCoinfSummary);
				}
			}

			coinfectionInvList.removeAll(invsToRemove);

			logger.debug("coinfectionInvList size after removing elements ----------------------- "
					+ coinfectionInvList.size());
		} catch (Exception ex) {
			logger.error("Exception in AssociateToCoinfectionAction.updateCoinfectionInvestigationListBasedOnInterview encountered.."
					+ ex.getMessage());
			ex.printStackTrace();
		}
	}


		/**
		 * Used by the View Contact Associate Investigations button.
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 */
	public ActionForward associateContactToInvestigationsSubmit(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		try {
			logger.debug("Inside associateContactToInvestigationsSubmit *********************************");
			AssociateToCoinfectionForm assocToCoinfForm = (AssociateToCoinfectionForm) form;

			//string[0] contains a list of UIDs delimited by colon
			String[] selectedcheckboxIdsArr = assocToCoinfForm.getSelectedcheckboxIds();
			if (selectedcheckboxIdsArr == null || selectedcheckboxIdsArr[0].isEmpty()) {
				assocToCoinfForm.resetForm();
				return null; //nothing to do
			}


			String[] processingDecisionList = assocToCoinfForm.getProcessingDecision();
			ArrayList<Long> coinfectionListToAddContactTo = parseAssociatedInvestigationList(selectedcheckboxIdsArr[0]);

			//get the existing contact record
			CTContactProxyVO cTContactProxyVO = null;
			try {
				cTContactProxyVO = CTContactLoadUtil.populateProxyVO(assocToCoinfForm.getObsUid().toString(), request);
			} catch (IOException e) {
				logger.error("I/O Exception retreiving Contact Proxy for " + assocToCoinfForm.getObsUid().toString());
				e.printStackTrace();
			} catch (ServletException e) {
				logger.error("Servlet Exception retreiving Contact Proxy for " + assocToCoinfForm.getObsUid().toString());
				e.printStackTrace();
			}
			//ans for FF section
			CTContactClientVO ctContactClientVO = assocToCoinfForm.getcTContactClientVO();

			Iterator coinfUidIter = coinfectionListToAddContactTo.iterator();
			int i = 0;
			while (coinfUidIter.hasNext()) {
				Long coinfectionUid = (Long) coinfUidIter.next();
				String processingDecision = processingDecisionList[i];
				CoinfectionSummaryVO thisCoinfectionSummaryVO = findCoinfectionSummaryVO(coinfectionUid, assocToCoinfForm.getCoinfectionSummaryVO());
				Long newContactUid = createContactForTheCoinfection(assocToCoinfForm, thisCoinfectionSummaryVO,
						processingDecision, ctContactClientVO, cTContactProxyVO, request);
				logger.debug("New contact created is:" + newContactUid);
				i++;
			}

			assocToCoinfForm.resetForm();
			logger.debug("leaving associateContactToInvestigationsSubmit *********************************");
		} catch (Exception ex) {
			logger.error("Exception in AssociateToCoinfectionAction.updateCoinfectionInvestigationListBasedOnInterview encountered.."
					+ ex.getMessage());
			ex.printStackTrace();
		}
		return null;

	}



		/**
		 * Create a cloned contact for the coinfection.
		 * If the Processing Decision is Field Followup, create an investigation for the contact also.
		 *
		 * @param coinfectionUid
		 * @param patientRevisionUid
		 * @param processingDecision
		 * @param ctContactClientVO
		 * @param ctContactProxyVO
		 * @param request
		 * @return new contactUid
		 */
	private Long createContactForTheCoinfection(AssociateToCoinfectionForm assocToCoinfForm,
			CoinfectionSummaryVO coinfectionSummaryVO, //create contact for this co-infection and if FF investigation
			String processingDecision,
			CTContactClientVO ctContactClientVO, //from AssociateToCoinfectionForm
			CTContactProxyVO ctContactProxyVO, // existing view contact
			HttpServletRequest request) {

		Long newContactUid = null;
		try {
			Long coinfectionUid = coinfectionSummaryVO.getPublicHealthCaseUid();

			NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) request.getSession()
					.getAttribute("NBSSecurityObject");
			String investigator = (String) assocToCoinfForm.getAttributeMap().get(CTConstants.ContactFFInvestigatorUid);
			String userId = nbsSecurityObj.getTheUserProfile().getTheUser().getEntryID();

			if (processingDecision.equals(CTConstants.FieldFollowup)) {
				logger.debug("Creating Contact and Investigation for Co-Infection PublicHealthCase_uid = " +coinfectionUid.toString());
				//clone contact
				CTContactProxyVO newContactProxyVO = CTContactLoadUtil.cloneContactForCoinfection(
						coinfectionSummaryVO, //Inv to create contact for
						ctContactProxyVO, //View contact
						processingDecision,
						request);
				PageProxyVO oldFFPageProxy = assocToCoinfForm.getContactPageProxyVO();
				//create new inv
				PageProxyVO newPageProxyVO = CTContactLoadUtil.stdPopulateInvestigationFromContactForCoinfection(
						investigator,
						coinfectionSummaryVO,
						ctContactClientVO,
						newContactProxyVO,
						oldFFPageProxy,
						userId,
						request);

				newContactUid= CTContactLoadUtil.createContactAndInvestigation(newContactProxyVO, newPageProxyVO, request);
			} else {
				//ready the proxy and send to backend for create
				CTContactProxyVO newContactProxyVO = CTContactLoadUtil.cloneContactForCoinfection(coinfectionSummaryVO,
						ctContactProxyVO,
						processingDecision,
						request);
				//clear any contact investigation
				newContactProxyVO.getcTContactVO().getcTContactDT().setContactEntityPhcUid(null);

				//update for SR or RSC
				if (processingDecision.equals(CTConstants.SecondaryReferral) ||
						processingDecision.equals(CTConstants.RecordSearchClosure)) {
					InvestigationSummaryVO contactInvSummaryVO = (InvestigationSummaryVO) assocToCoinfForm.getAttributeMap().get(processingDecision+coinfectionSummaryVO.getConditionCd());
					if (contactInvSummaryVO != null) {
						newContactProxyVO.getcTContactVO().getcTContactDT().setContactEntityPhcUid(contactInvSummaryVO.getPublicHealthCaseUid());
						newContactProxyVO.getcTContactVO().getcTContactDT().setConEntityEpilinkId(contactInvSummaryVO.getEpiLinkId());
						newContactProxyVO.getcTContactVO().getcTContactDT().setDispositionCd(contactInvSummaryVO.getDispositionCd());
						//should set contact patient revision to this?
						//newContactProxyVO.getcTContactVO().getcTContactDT().setContactEntityUid(contactInvSummaryVO.getPatientRevisionUid());
					}
				}
				logger.debug("Creating SR or RSC Contact for Co-Infection PublicHealthCase_uid = " +coinfectionUid.toString());
				newContactUid = CTContactLoadUtil.createContactForCoinfection(newContactProxyVO, request);
			}
		} catch (Exception ex) {
			logger.error("Exception in AssociateToCoinfectionAction.createContactForTheCoinfection encountered.."
					+ ex.getMessage());
			ex.printStackTrace();
		}
		return newContactUid;
	}


		/**
		 * Currently used by the View Treatment Associate Investigations button.
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 */
	public ActionForward associateInvestigationsSubmit(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		try {
			AssociateToCoinfectionForm assocToCoinfForm = (AssociateToCoinfectionForm) form;
			Collection<Object>  isvoColl = assocToCoinfForm.getInvSummaryVOColl();  //stored at load
			Long sourceUid = null;
			Collection<Object>  addedAssociationsCollection= new ArrayList<Object> ();
			Collection<Object>  deletedAssociationsCollection= new ArrayList<Object> ();
			Map<Long,String> assocInvMap = new HashMap<Long, String>();

			HttpSession session = request.getSession();
			// Get Context Action
			String contextAction = request.getParameter("ContextAction");
			if (contextAction == null) {
				contextAction = (String) request.getAttribute("ContextAction");
			} else
				request.setAttribute("ContextAction", HTMLEncoder.encodeHtml(contextAction));
			String associatedInvestigationsStr = request.getParameter("AssociatedInvestigations");
			if (associatedInvestigationsStr == null) {
				logger.error("Error: Missing List of Investigation Associations");
			}
			String sourceUidStr =  request.getParameter("sourceUid");
			if (sourceUidStr == null) {
				logger.error("Error: Missing Source Uid for Investigation Associations");
			} else
				sourceUid = new Long (sourceUidStr);
			assocInvMap = parseAssociatedInvestigationListIntoMap(associatedInvestigationsStr);
			if (sourceUid != null) {


				//if nothing checked, see if something checked got unchecked..
				if (assocInvMap.size() == 0) {
					Iterator<Object>  isvoIter = isvoColl.iterator();
					while (isvoIter.hasNext()) {
						InvestigationSummaryVO investigationSummaryVO = (InvestigationSummaryVO) isvoIter.next();
						if (investigationSummaryVO.getIsAssociated()) {
							//if true with need to delete the relationship
							Long phcUID =   investigationSummaryVO.getPublicHealthCaseUid();
							deletedAssociationsCollection.add(phcUID.longValue());
						} //if set
					} //iter
				} //size=0

				//items are checked, see if they were unchecked going in, add
				if (assocInvMap.size() > 0) {
					Iterator<Long>  chkboxIter = assocInvMap.keySet().iterator();
					while (chkboxIter.hasNext()) {
						Long phcUID = (Long) chkboxIter.next();
						//find it in the list
						Iterator<Object>  isvoIter = isvoColl.iterator();
						while (isvoIter.hasNext()) {
							InvestigationSummaryVO investigationSummaryVO = (InvestigationSummaryVO) isvoIter.next();
							if (investigationSummaryVO.getPublicHealthCaseUid().longValue() == phcUID.longValue()) {
								//if it wasn't set before it is a new relation
								if (!investigationSummaryVO.getIsAssociated()) {
									addedAssociationsCollection.add(phcUID.longValue());
								} //if it wasn't assoc before
							} //if found the phcUID
						} //isvo iter
					} //not empty string
				} //find the passed checkbox value in the list
			} //walk through the checkbox list

			//walk through to see if something needs to
			//be added to the deleted list
			if (assocInvMap.size() > 0) {
				Iterator<Object>  isvoIter = isvoColl.iterator();
				while (isvoIter.hasNext()) {
					InvestigationSummaryVO investigationSummaryVO = (InvestigationSummaryVO) isvoIter.next();
					if (investigationSummaryVO.getIsAssociated()) {
						Long formPHCUid = investigationSummaryVO.getPublicHealthCaseUid();
						//if it was set and it's not in the list, we need to add to deleted..
						if (!assocInvMap.containsKey(formPHCUid))
							deletedAssociationsCollection.add(formPHCUid.longValue());
					}//isAssoc in form saved copy
				} //isvoIter
			} //items in checkbox list


			if (addedAssociationsCollection.size() != 0 || deletedAssociationsCollection.size() != 0 ) {
				try {
					logger.debug("preparing to call setTreatmentCaseAssociations()");
					MainSessionHolder mainSessionHolder = new MainSessionHolder();
					MainSessionCommand msCommand = mainSessionHolder.getMainSessionCommand(session);
					String sBeanJndiName = JNDINames.TREATMENT_PROXY_EJB;
					String sMethod = "setTreatmentCaseAssociations";
					//pass the patient to get the investigations for and the obsUID to set the existing associate flag
					Object[] oParms = new Object[] {sourceUid, NEDSSConstants.INVESTIGATION, addedAssociationsCollection, deletedAssociationsCollection};
					ArrayList<Object> arr = (ArrayList<Object> ) msCommand.processRequest(sBeanJndiName, sMethod, oParms);
					logger.debug("returned from call to setTreatmentCaseAssociations()");
				} catch (NEDSSAppConcurrentDataException e) {
					logger.error("Concurrency error calling TreatmentProxyEJBRef.setTreatmentCaseAssociations()");
					e.printStackTrace();
				} catch (Exception e) {
					logger.error("Exception calling TreatmentProxyEJBRef.setTreatmentCaseAssociations()");
					e.printStackTrace();
				}
			}
			assocToCoinfForm.resetForm();
		} catch (Exception ex) {
			logger.error("Exception in AssociateToCoinfectionAction.associateInvestigationsSubmit encountered.."
					+ ex.getMessage());
			ex.printStackTrace();
		}
		return null;

	} //associateInvestigationsSubmit method


		/**
		 * Used by the View Interview Associate Investigations button.
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 */
	public ActionForward associateInterviewsToInvestigationsSubmit(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		try {
			AssociateToCoinfectionForm assocToCoinfForm = (AssociateToCoinfectionForm) form;
			Collection<Object>  isvoColl = assocToCoinfForm.getInvSummaryVOColl();  //stored at load
			Long sourceUid = null;
			Collection<Object>  addedAssociationsCollection= new ArrayList<Object> ();
			Collection<Object>  deletedAssociationsCollection= new ArrayList<Object> ();
			Map<Long,String> assocInvMap = new HashMap<Long, String>();

			HttpSession session = request.getSession();
			// Get Context Action
			String contextAction = request.getParameter("ContextAction");
			if (contextAction == null) {
				contextAction = (String) request.getAttribute("ContextAction");
			} else
				request.setAttribute("ContextAction", HTMLEncoder.encodeHtml(contextAction));
			String associatedInvestigationsStr = request.getParameter("AssociatedInvestigations");
			if (associatedInvestigationsStr == null) {
				logger.error("Error: Missing List of Investigation Associations");
			}
			sourceUid = assocToCoinfForm.getObsUid();
			if (sourceUid == null) {
				logger.error("Error: Missing Source Uid for Investigation Associations");
			}

			assocInvMap = parseAssociatedInvestigationListIntoMap(associatedInvestigationsStr);
			if (sourceUid != null) {
				//if nothing checked, see if something checked got unchecked..
				if (assocInvMap.size() == 0) {
					Iterator<Object>  isvoIter = isvoColl.iterator();
					while (isvoIter.hasNext()) {
						CoinfectionSummaryVO investigationSummaryVO = (CoinfectionSummaryVO) isvoIter.next();
						if (investigationSummaryVO.isAssociated()) {
							//if true with need to delete the relationship
							Long phcUID =   investigationSummaryVO.getPublicHealthCaseUid();
							deletedAssociationsCollection.add(phcUID.longValue());
						} //if set
					} //iter
				} //size=0

				//items are checked, see if they were unchecked going in, add
				if (assocInvMap.size() > 0) {
					Iterator<Long>  chkboxIter = assocInvMap.keySet().iterator();
					while (chkboxIter.hasNext()) {
						Long phcUID = (Long) chkboxIter.next();
						//find it in the list
						Iterator<Object>  isvoIter = isvoColl.iterator();
						while (isvoIter.hasNext()) {
							CoinfectionSummaryVO investigationSummaryVO = (CoinfectionSummaryVO) isvoIter.next();
							if (investigationSummaryVO.getPublicHealthCaseUid().longValue() == phcUID.longValue()) {
								//if it wasn't set before it is a new relation
								if (!investigationSummaryVO.isAssociated()) {
									addedAssociationsCollection.add(phcUID.longValue());
								} //if it wasn't assoc before
							} //if found the phcUID
						} //isvo iter
					} //not empty string
				} //find the passed checkbox value in the list
			} //walk through the checkbox list

			//walk through to see if something needs to
			//be added to the deleted list
			if (assocInvMap.size() > 0) {
				Iterator<Object>  isvoIter = isvoColl.iterator();
				while (isvoIter.hasNext()) {
					CoinfectionSummaryVO investigationSummaryVO = (CoinfectionSummaryVO) isvoIter.next();
					if (investigationSummaryVO.isAssociated()) {
						Long formPHCUid = investigationSummaryVO.getPublicHealthCaseUid();
						//if it was set and it's not in the list, we need to add to deleted..
						if (!assocInvMap.containsKey(formPHCUid))
							deletedAssociationsCollection.add(formPHCUid.longValue());
					}//isAssoc in form saved copy
				} //isvoIter
			} //items in checkbox list

			if (addedAssociationsCollection.size() != 0 || deletedAssociationsCollection.size() != 0 ) {

				try {
					logger.debug("preparing to call setEventCaseAssociations()");
					MainSessionHolder mainSessionHolder = new MainSessionHolder();
					MainSessionCommand msCommand = mainSessionHolder.getMainSessionCommand(session);
					String sBeanJndiName = JNDINames.PAGE_PROXY_EJB;
					String sMethod = "setEventCaseAssociations";
					//pass the patient to get the investigations for and the obsUID to set the existing associate flag
					Object[] oParms = new Object[] {sourceUid, NEDSSConstants.INVESTIGATION, addedAssociationsCollection, deletedAssociationsCollection};
					ArrayList<Object> arr = (ArrayList<Object> ) msCommand.processRequest(sBeanJndiName, sMethod, oParms);
					//investigationSummaryVOs = (ArrayList<Object> ) arr.get(0);
					logger.debug("returned from call to setEventCaseAssociations()");
				} catch (NEDSSAppConcurrentDataException e) {
					logger.error("Concurrency error calling PAGE_PROXY_EJB.setEventCaseAssociations()");
					e.printStackTrace();
				} catch (Exception e) {
					logger.error("Exception calling PAGE_PROXY_EJB.setEventCaseAssociations()");
					e.printStackTrace();
				}	//try
			} //if not empty
			assocToCoinfForm.resetForm();
		} catch (Exception ex) {
			logger.error("Exception in AssociateToCoinfectionAction.associateInterviewsToInvestigationsSubmit encountered.."
					+ ex.getMessage());
			ex.printStackTrace();
		}
		return null;

	} //associateInterviewsToInvestigationsSubmit method



		/**
		 * Convert colon delimited list into ArrayList of Longs
		 * @param invStr
		 * @return ArrayList of longs for Investigation UIDs to associate to
		 */
		private ArrayList<Long> parseAssociatedInvestigationList(String invStr) {
		   		ArrayList<Long> invList = new ArrayList<Long>();
				StringTokenizer st = new StringTokenizer(invStr,":");
				while (st.hasMoreTokens()) {
					String token = st.nextToken();
					try {
						Long invUid = new Long (token);
						invList.add(invUid);
					} catch (Exception e) {
						logger.error("Error parsing investigation list " + invStr);
						e.printStackTrace();
					}
				}
				return invList;
		}
		/**
		 * Convert colon delimited list into Map of Longs
		 * @param invStr
		 * @return
		 */
		private Map<Long,String> parseAssociatedInvestigationListIntoMap(String invStr) {
			Map<Long,String> assocInvMap = new HashMap<Long, String>();
			StringTokenizer st = new StringTokenizer(invStr,":");
			while (st.hasMoreTokens()) {
				String token = st.nextToken();
				try {
					Long invUid = new Long (token);
					assocInvMap.put(invUid, token);
				} catch (Exception e) {
					logger.error("Error parsing investigation list " + invStr);
					e.printStackTrace();
				}
			}
			return assocInvMap;
	}

		private CoinfectionSummaryVO findCoinfectionSummaryVO(
				Long coinfectionUid, Collection<Object> coinfectionSummaryVOList) {
			if (coinfectionSummaryVOList == null || coinfectionSummaryVOList.isEmpty())
				return null;
			try {
				Iterator coinfIter = coinfectionSummaryVOList.iterator();
				while (coinfIter.hasNext()) {
					CoinfectionSummaryVO coinfectionSummaryVO = (CoinfectionSummaryVO) coinfIter.next();
					if (coinfectionSummaryVO.getPublicHealthCaseUid() != null &&
							coinfectionSummaryVO.getPublicHealthCaseUid().longValue() == coinfectionUid.longValue())
						return(coinfectionSummaryVO);
				}
			} catch (Exception e) {
				logger.error("Error in findCoinfectionSummaryVO " + e.getMessage());
				e.printStackTrace();
			}
			//not found??
			logger.warn("Investigation not found in Coinfection Summary VO list for Investigation UID = " + coinfectionUid.toString());
			return null;
		}
}//class



