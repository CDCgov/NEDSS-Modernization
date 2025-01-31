package gov.cdc.nedss.webapp.nbs.action.deduplication;

/**
 * Title:        MergeCandidateListSubmit
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      CSC
 * @author
 * @version 1.0
 */

import java.io.*;
import java.util.*;
import java.sql.Timestamp;

import javax.servlet.http.*;
import javax.servlet.*;

import org.apache.struts.action.*;

import gov.cdc.nedss.proxy.ejb.entityproxyejb.bean.EntityProxyHome;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.systemservice.util.PrepareVOUtils;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.webapp.nbs.action.person.util.PersonUtil;
import gov.cdc.nedss.webapp.nbs.action.person.util.SearchResultPersonUtil;
import gov.cdc.nedss.webapp.nbs.action.util.ErrorMessageHelper;
import gov.cdc.nedss.webapp.nbs.action.util.PaginationUtil;
import gov.cdc.nedss.entity.person.dt.*;
import gov.cdc.nedss.entity.person.vo.*;
import gov.cdc.nedss.locator.dt.*;
import gov.cdc.nedss.webapp.nbs.form.person.*;
import gov.cdc.nedss.entity.entityid.dt.EntityIdDT;
import gov.cdc.nedss.association.dt.RoleDT;
import gov.cdc.nedss.deduplication.dedupbatchprocess.DeDuplicationSimilarBatchProcessor;
import gov.cdc.nedss.deduplication.ejb.deduplicationprocessor.bean.DeDuplicationProcessorHome;
import gov.cdc.nedss.deduplication.vo.*;
//for the old way using entity
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;
import gov.cdc.nedss.webapp.nbs.util.DeDuplicationQueueHelper;

public class MergeCandidateListSubmit extends Action
{

	//For logging
	static final LogUtils logger = new LogUtils(MergeCandidateListSubmit.class.getName());

	public MergeCandidateListSubmit()
	{
	}

	   /**
      * Handles the loading of the page for comparing the two records for merging.
      * @J2EE_METHOD  --  execute
      * @param mapping       the ActionMapping
      * @param form     the ActionForm
      * @return request    the  HttpServletRequest
      * @return response    the  HttpServletResponse
      * @throws IOException
      * @throws ServletException
      **/
	public ActionForward execute(ActionMapping mapping,
				 ActionForm form,
				 HttpServletRequest request,
				 HttpServletResponse response)
	throws IOException, ServletException
	{
	//	int num = 1;
		
	//	if(num==1)
	//	  return mapping.findForward("Merge");
	//	((Map)request.getSession().getAttribute("attributeMap")).get("searchCriteria")
 	
		 SearchResultPersonUtil srpUtil = new SearchResultPersonUtil();
          HttpSession session = request.getSession(false);
          NBSSecurityObj secObj = (NBSSecurityObj) session.getAttribute(
              "NBSSecurityObject");

          Collection<?>  mergePatientsCollection;
          Collection<?>  retMergeConfirmationVO;


          String contextAction = request.getParameter("ContextAction");
          String contextTaskName = (String)request.getAttribute("ContextTaskName");
         
           if(contextTaskName == null)
            contextTaskName = (String)NBSContext.getCurrentTask(session);

          if (contextAction == null)
            contextAction = (String) request.getAttribute("ContextAction");

      //    if (contextAction.equalsIgnoreCase("NewSearch"))
      //      return mapping.findForward("Merge");
        
          if(contextTaskName.equalsIgnoreCase("MergeCandidateList2")) {
 
        	request.setAttribute("Mode1", "SystemIdentified");  
        	PersonSearchForm psForm = (PersonSearchForm)form;
    		request.setAttribute("PageTitle","Merge Candidate List");
    		psForm.setPageTitle("Merge Candidate List", request);
    			
            Integer groupNumber = getGroupNbr(session);
            // Handle System Identified Group related person merges here...
            if (contextAction.equalsIgnoreCase("Merge") &&
                contextTaskName.equalsIgnoreCase("MergeCandidateList2")) {
              try {
            	  
            	  String listOfMergeCandidates =  (String)request.getParameter("merge");
                  
                  String[] mergeCandidateList = listOfMergeCandidates.split(",");
                
                  
                  
                //mergePatientsCollection  = getPersonVOs(request, session, secObj);
                mergePatientsCollection  = getSimilarGroupToMerge(groupNumber,
                		mergeCandidateList);
                //is a Survivor Specified? 
                String specifiedSurvivorPatientId = "";
                
                
                String survivorListString = request.getParameter("survivor");
                String[] survivorList = survivorListString.split(",");
                
                
                
                if (survivorList != null && !survivorList[0].isEmpty()) {
               	 specifiedSurvivorPatientId = survivorList[0];
                } else {
                	//per Christi, they always want the oldest by default
                	//they can override with property setting
                	if (PropertyUtil.isMergeCandidateDefaultSurvivorOldest())
                		specifiedSurvivorPatientId = findOldestPatientId(mergePatientsCollection);
                }
                retMergeConfirmationVO = sendProxyToMeregPersons(
                    mergePatientsCollection, specifiedSurvivorPatientId, session, secObj);
                try {
                updateNonSelectedGroups(getNonSelectedGroupMembers(groupNumber,
                    mergePatientsCollection), session, secObj);
                } catch(Exception e) {
                  if (e instanceof NEDSSAppException) {
                    NEDSSAppException nae = (NEDSSAppException) e;
                    String errorNumber = nae.getErrorCd();
                    if(errorNumber.equals("ERR109")) {
                      //do nothing
                      //This was a result of attempting to set the group_nbr to null of
                      //a record that was not selected to be merged, but someone updated it
                      //prior to this attempt to set the group_nbr to null.  So eat the
                      //data concurrent exception and continue on.  Use does not care that someone
                      //else has updated a record the he has not selected to be merged.
                    } else {
                    	throw new ServletException(e);
                    }
                  } else {
                	  throw new ServletException(e);
                    //throw e;
                  }
                }
                //NBSContext.store(session, "DSMergeConfirmation",
                                 //retMergeConfirmationVO);
              NBSContext.store(session,"DSMergeConfirmation",retMergeConfirmationVO);
              String confirmationMergeMessage = createConfirmationMessage(retMergeConfirmationVO);
              session.setAttribute("confirmationMessage", confirmationMergeMessage);
             
              }
              catch (Exception e) {
                if (e instanceof NEDSSAppException) {
                  NEDSSAppException nae = (NEDSSAppException) e;
                  String errorNumber = nae.getErrorCd();
                  //if errorCd is 109, dedup needs to clean up statefull information
                  //no one else should do this if not related to deduplication.
                  if (errorNumber.equals("ERR109")) {
                    PersonVO pvo = ((DeduplicationPatientMergeVO)((Collection<?>)DeDuplicationQueueHelper.getDedupTakenQueue().get(groupNumber)).iterator().next()).getMPR();
                    DeDuplicationQueueHelper.getDedupTakenQueue().remove(groupNumber);
                    DeDuplicationQueueHelper.getDedupSessionQueue().remove(session.
                        getId());
                    DeDuplicationQueueHelper.getDedupAvailableQueue().put(groupNumber, pvo.getThePersonDT().getGroupTime());
                    return ErrorMessageHelper.redirectToPage("PS158", errorNumber);
                  }
                  //redirect to generic error page.
                  return ErrorMessageHelper.redirectToPage("PS175", errorNumber);
                } //end of if
                //redirect to generic error page.
                return ErrorMessageHelper.redirectToPage("PS175", null);
              }

              try {
                processPostMergeOrNoMergeForSystemIdentified(groupNumber, session,
                    contextAction, secObj);
              }
              catch (Exception e) {
                if (e instanceof NEDSSAppException) {
                  NEDSSAppException nae = (NEDSSAppException) e;
                  if (nae.getErrorCd().equals("ERR109")) {
                    logger.info("ERROR ,  DataConcurrent exception recieved.", e);
                    //redirect to dataconcurrent message page.
                    return ErrorMessageHelper.redirectToPage("PS158", nae.getErrorCd());
                  }
                  //redirect to generic error page
                  return ErrorMessageHelper.redirectToPage("PS175", nae.getErrorCd());
                }
                return ErrorMessageHelper.redirectToPage("PS175", null);
              }
/*
 * 
 * ActionForward strURL= mapping.findForward(contextAction);
     		 ActionForward forwardNew = new ActionForward();
     		 forwardNew.setPath(strURL.toString());			
     		 return forwardNew;	
           */
              return mapping.findForward(contextAction);
            }

            if (contextAction.equalsIgnoreCase("NoMerge") &&
                contextTaskName.equalsIgnoreCase("MergeCandidateList2")) {
              try {
                processPostMergeOrNoMergeForSystemIdentified(groupNumber, session,
                    contextAction, secObj);
              }
              catch (Exception e) {
                if (e instanceof NEDSSAppException) {
                  NEDSSAppException nae = (NEDSSAppException) e;
                  if (nae.getErrorCd().equals("ERR109")) {
                    //We eat the exception and forward to LOAD Action class...
                    DeDuplicationQueueHelper.getDedupTakenQueue().remove(groupNumber);
                    DeDuplicationQueueHelper.getDedupSessionQueue().remove(session.
                        getId());
                    return mapping.findForward(contextAction);
                  }
                  //For All other error codes redirect to generic error page
                  return ErrorMessageHelper.redirectToPage("PS175", nae.getErrorCd());
                }
                return ErrorMessageHelper.redirectToPage("PS175", null);
              }

              return mapping.findForward(contextAction);
            }

            if (contextAction.equalsIgnoreCase("Cancel") &&
                contextTaskName.equalsIgnoreCase("MergeCandidateList2")) {
              //Adjust the HashMap's
              processContextActionCancel(groupNumber, session);
              return mapping.findForward(contextAction);
            }
            
            if (contextAction.equalsIgnoreCase("Skip") &&
                    contextTaskName.equalsIgnoreCase("MergeCandidateList2")) {
                  //Adjust the HashMap's
                  processContextActionSkip(groupNumber, session);
                  return mapping.findForward(contextAction);
                }
            if (contextAction.equalsIgnoreCase("RemoveFromMerge") &&
                    contextTaskName.equalsIgnoreCase("MergeCandidateList2")) {
            	
            	  String listOfRemoveMerge =  (String)request.getParameter("removeFromMerge");
                  //List of all the patients selected to be removed from merge.
                  String[] removeMergeList = listOfRemoveMerge.split(",");
                
	              	try{
	            
	              		
	              		for(int j = 0; j<removeMergeList.length; j++){
	              			String patientUid = removeMergeList[j];
	              			removeFromMergeForDedup(patientUid, session, secObj);
	              			
	              			
	              		}
	              	}catch(Exception e){			

	              		logger.fatal("error in removeFromMergeForDedup of Deduplication Similar batch process : "+e);         	
        	
        	    }
          		
                  //Adjust the HashMap's
	              //Update the DB and choose what group to show in the current page
                 processContextActionRemoveFromMerge(groupNumber, session);
                  return mapping.findForward(contextAction);
                }
            
          }
         
            
          
          //end of if (contextTaskName.equalsIgnoreCase("MergeCandidateList2")) around line 92

       //   contextAction="Merge";
      //    contextTaskName = "MergeCandidateList1";
            // get the personVOs for the persons to be manually merged
            if (contextAction.equalsIgnoreCase("Merge")&&
                contextTaskName.equalsIgnoreCase("MergeCandidateList1")) {
            try {
             mergePatientsCollection  = this.getPersonVOs(request,
                session, secObj);
             
             //is a Survivor Specified? 
             String specifiedSurvivorPatientId = "";
             String survivorListString = request.getParameter("survivor");
             String[] survivorList = survivorListString.split(",");
             if (survivorList != null && !survivorList[0].isEmpty()) {
            	 specifiedSurvivorPatientId = survivorList[0];
             } else {
            	 //per Christi, they always want the oldest by default, they can override with property setting
            	 //if it is not there, it defaults to oldest.
            	 if (PropertyUtil.isMergeCandidateDefaultSurvivorOldest())
            		 specifiedSurvivorPatientId = findOldestPatientId(mergePatientsCollection);
             }
            /*
             * call the method on proxy merge the selected person and get back the collection of survivor and merged persons
             */
               retMergeConfirmationVO = this.sendProxyToMeregPersons(mergePatientsCollection, specifiedSurvivorPatientId, session, secObj);
               //NBSContext.store(session,"DSMergeConfirmation",this.UpdateVOWithNameCdDesc(retMergeConfirmationVO));
         //     NBSContext.store(session,"DSMergeConfirmation",retMergeConfirmationVO);
              
              
             
              String confirmationMergeMessage = createConfirmationMessage(retMergeConfirmationVO);
              session.setAttribute("confirmationMessage", confirmationMergeMessage);
              request.setAttribute("MergePatient", "true");
             }
            catch (Exception e) {

              if(e instanceof NEDSSAppException) {
                NEDSSAppException nae = (NEDSSAppException)e;
                if (nae.getErrorCd() != null && nae.getErrorCd().equals("ERR109")) {
                  logger.fatal(
                      "ERROR , The data has been modified by another user, please recheck! ",
                      nae);

                  return ErrorMessageHelper.redirectToPage("PS158", nae.getErrorCd());
                }
                return ErrorMessageHelper.redirectToPage("PS175", nae.getErrorCd());
              } else {
                return ErrorMessageHelper.redirectToPage("PS175", null);
              }
            }//end of catch
            return mapping.findForward(contextAction);
          }
          else if (contextAction.equals("NewSearch")) {
        	  request.setAttribute("MergePatient","true");
            return mapping.findForward(contextAction);
          }
          else if (contextAction.equals("RefineSearch")) {
        	  PersonSearchForm psForm = (PersonSearchForm)form;
        	  psForm.setSearchCriteriaArrayMap(new HashMap<Object,Object>());
        	  request.setAttribute("MergePatient","true");
            return mapping.findForward(contextAction);
          }

          else if (contextAction.equals("Cancel")&&
                contextTaskName.equalsIgnoreCase("MergeCandidateList1")) {
            return mapping.findForward(contextAction);
          }
          else if("filterPatientSubmit".equalsIgnoreCase(contextAction)){
        	  request.setAttribute("candidateQueueCreationDate", request.getSession().getAttribute("candidateQueueCreationDate"));
    			request.setAttribute("groupsAvailableToMerge", request.getSession().getAttribute("groupsAvailableToMerge"));
    			
  	    	PersonSearchForm psForm = (PersonSearchForm)form;

  	    	srpUtil.showButton(request,(String) psForm.getAttributeMap().get("DSSearchCriteriaString"));
  	    	return srpUtil.filterPatientSubmit( mapping,  form,  request,  response);
  	      }
  		else if("removeFilter".equalsIgnoreCase(contextAction))
  		{
  			request.setAttribute("candidateQueueCreationDate", request.getSession().getAttribute("candidateQueueCreationDate"));
  			request.setAttribute("groupsAvailableToMerge", request.getSession().getAttribute("groupsAvailableToMerge"));
  			if(contextTaskName!=null && contextTaskName.equalsIgnoreCase("MergeCandidateList2")){
					request.setAttribute("Mode1","SystemIdentified");
					request.setAttribute("contextAction", contextAction);
  			}
  			else
  				if(contextTaskName!=null && contextTaskName.equalsIgnoreCase("MergeCandidateList1")){
					request.setAttribute("Mode1","ManualMerge");
					request.setAttribute("contextAction", contextAction);
  				}
			
  			
  			PersonSearchForm psForm = (PersonSearchForm)form;
  			srpUtil.showButton(request,(String) psForm.getAttributeMap().get("DSSearchCriteriaString"));
  			String scString = (String) psForm.getAttributeMap().get("DSSearchCriteriaString");
  			String reportTp = (String) psForm.getAttributeMap().get("reportType");
  			ArrayList<Object> investigatorDDList = (ArrayList<Object>) psForm.getAttributeMap().get("investigatorDDList");
  			ArrayList<Object> jurisdictionDDList = (ArrayList<Object>) psForm.getAttributeMap().get("jurisdictionDDList");
  			ArrayList<Object> conditionDDList = (ArrayList<Object>) psForm.getAttributeMap().get("conditionDDList");
  			ArrayList<Object> CaseStatusDDList = (ArrayList<Object>) psForm.getAttributeMap().get("CaseStatusDDList");
  			ArrayList<Object> startDateDDList = (ArrayList<Object>) psForm.getAttributeMap().get("startDateDDList");
  			ArrayList<Object> notificationDDList = (ArrayList<Object>) psForm.getAttributeMap().get("notificationDDList");
  	    	
  			ArrayList<Object> conditionDD = (ArrayList<Object>) psForm.getAttributeMap().get("conditionDD");
  			ArrayList<Object> observationTypeDDList = (ArrayList<Object>) psForm.getAttributeMap().get("observationTypeDDList");
  			ArrayList<Object> descriptionDDList = (ArrayList<Object>) psForm.getAttributeMap().get("descriptionDDList");
  			String totalRecords = (String) psForm.getAttributeMap().get("totalRecords");
  			
  			Object searchCriteria = null;
  			Map<Object, Object> searchCriteriaColl = new TreeMap<Object, Object>();
  			
  			if(((Map)request.getSession().getAttribute("attributeMap"))!=null)	
  				searchCriteria = ((Map)request.getSession().getAttribute("attributeMap")).get("searchCriteria");

  			if(searchCriteria==null && (contextTaskName!=null && contextTaskName.equalsIgnoreCase("MergeCandidateList2"))){//There's sorting from System Identified
  				String sortSt  =  (String)((Map)request.getSession().getAttribute("attributeMap")).get("sortSt");
  				searchCriteriaColl.put("sortSt", sortSt);
  			}
  			
  			psForm.clearAll();
  		
  	
  			if(((Map)request.getSession().getAttribute("attributeMap"))!=null)	
  				((Map)request.getSession().getAttribute("attributeMap")).put("searchCriteria",searchCriteria);
  			
  			if(searchCriteria==null && (contextTaskName!=null && contextTaskName.equalsIgnoreCase("MergeCandidateList2"))){
  				psForm.getAttributeMap().put("searchCriteria",searchCriteriaColl);
  			}else
  				psForm.getAttributeMap().put("searchCriteria",searchCriteria);
  			
  			
  			
  			if(contextTaskName!=null &&  !contextTaskName.equalsIgnoreCase("MergeCandidateList2"))//There's no search criteria from System Identified
  				psForm.getAttributeMap().put("DSSearchCriteriaString", scString);
  
  			
  	    	psForm.getAttributeMap().put("reportType", reportTp);
  	    	psForm.getAttributeMap().put("investigatorDDList", investigatorDDList);
  	    	psForm.getAttributeMap().put("jurisdictionDDList", jurisdictionDDList);
  	    	psForm.getAttributeMap().put("conditionDDList", conditionDDList);
  	    	psForm.getAttributeMap().put("CaseStatusDDList", CaseStatusDDList);
  	    	psForm.getAttributeMap().put("startDateDDList", startDateDDList);
  	    	psForm.getAttributeMap().put("notificationDDList", notificationDDList);
  	    	
  	    	psForm.getAttributeMap().put("conditionDD", conditionDD);
  	    	psForm.getAttributeMap().put("observationTypeDDList", observationTypeDDList);
  	    	psForm.getAttributeMap().put("descriptionDDList", descriptionDDList);
  	    	psForm.getAttributeMap().put("totalRecords", totalRecords);
  	    	//request.setAttribute("ActionMode", "InitLoad");	
  	    	return srpUtil.filterPatientSubmit( mapping,  form,  request,  response);
  		}
  	    else if("sortingByColumn".equalsIgnoreCase(contextAction)){
  	    	
  	    	
  	    	if(contextTaskName!=null && contextTaskName.equalsIgnoreCase("MergeCandidateList1")){
  	    		request.setAttribute("Mode1", "ManualMerge");  //ND-27717
  	    	}
  	    	
  	    	
  	    	request.setAttribute("candidateQueueCreationDate", request.getSession().getAttribute("candidateQueueCreationDate"));
  			request.setAttribute("groupsAvailableToMerge", request.getSession().getAttribute("groupsAvailableToMerge"));
 
  	    	ArrayList<Object> patientVoCollection = null;
  	    	try{
  	    		patientVoCollection = (ArrayList<Object> )NBSContext.retrieve(request.getSession() ,NBSConstantUtil.DSPersonListFull);
  	    	}catch(Exception ex)
  	    	{
  	    		logger.error("MergeCandidateListSubmit.execute: DSPersonListFull not found in the NBSContext: " + ex.getMessage());
  	    		 patientVoCollection = (ArrayList )request.getSession().getAttribute("personList");
  	    		NBSContext.store(request.getSession() ,NBSConstantUtil.DSPersonListFull, patientVoCollection);
  	    	}
  			srpUtil.filterPatientSubmit(mapping, form, request, response);
  			
  			
  	    	//ArrayList<Object> patientVoCollection = (ArrayList )request.getSession().getAttribute("personList");
  	    	//ArrayList<Object> patientVoCollection = (ArrayList<Object> )NBSContext.retrieve(request.getSession() ,NBSConstantUtil.DSPersonListFull);
  	    	PersonSearchForm psForm = (PersonSearchForm)form;
  	    	try{
  	    		srpUtil.showButton(request,(String) psForm.getAttributeMap().get("DSSearchCriteriaString"));
  	    		patientVoCollection = (ArrayList<Object>) srpUtil.filterPatient(psForm, request);
  				srpUtil.sortPatientLibarary(psForm,patientVoCollection,true,request);
  					
  				
  				
  	    		psForm.getAttributeMap().put("queueCount", String.valueOf(patientVoCollection.size()));
  	    		
  				request.setAttribute("queueCount", String.valueOf(patientVoCollection.size()));
  				request.setAttribute("personList", patientVoCollection);
  	    	}catch(Exception e){
  	    		System.out.println(e.getMessage());
	    	}
	    	return PaginationUtil.personPaginate(psForm, request, "searchResultLoad",mapping);
	      
  	    	}
            //View File Action
	  	    else if (contextAction.equalsIgnoreCase("ViewFile"))
	  	    {
	  	        Long personUID = new Long((String)request.getParameter("uid"));
	  	        NBSContext.store(session, "DSPatientPersonUID", personUID);
	  	        NBSContext.store(session,"DSFileTab","2");
	  	        return mapping.findForward(contextAction);
	  	    }
          return null;
        }

		private Integer getGroupNbr(HttpSession session) {
          DeDuplicationQueueHelper dh = (DeDuplicationQueueHelper)session.getAttribute("DQH");
          if(dh!=null)
          {
            return (Integer) dh.getDedupSessionQueue().get(session.getId());
          }
          else
          {
            return null;
          }
        }


        private void updateNonSelectedGroups(Collection<?> nonSelectedPvo, HttpSession session, NBSSecurityObj secObj) throws Exception {
          if(nonSelectedPvo.size() > 0) {

              MainSessionCommand msCommand = null;
              String sBeanJndiName = JNDINames.EntityControllerEJB;
              String sMethod = "setMPR";

              MainSessionHolder holder = new MainSessionHolder();
              msCommand = holder.getMainSessionCommand(session);

              //Collection<Object>  collection =  (Collection)DeDuplicationQueueHelper.getDedupTakenQueue().get(groupNumber);

             Iterator<?>  iterator = nonSelectedPvo.iterator();

              while (iterator.hasNext()) {
                PersonVO personVO = (PersonVO) iterator.next();
                personVO.setItDirty(true);
                personVO.getThePersonDT().setItDirty(true);
                personVO.getThePersonDT().setGroupNbr(null);
                personVO.getThePersonDT().setGroupTime(null);

                Object[] oParams = new Object[] {
                    personVO, "PAT_NO_MERGE"};

                msCommand.processRequest(sBeanJndiName, sMethod, oParams);
              }//end of while

          }//end of if
        }

        private Collection<Object>  getNonSelectedGroupMembers(Integer groupNbr, Collection<?>  selectedColl) {
          if(selectedColl == null) return null;


          List<?> totalGroupCollection  = (List<?>)DeDuplicationQueueHelper.getDedupTakenQueue().get(groupNbr);
         Iterator<?>  selectedColliterator = selectedColl.iterator();

          List<Object> personUIDsInTotalGroupList = new ArrayList<Object> ();
          List<Object> personUIDsInSelectedList = new ArrayList<Object> ();
          Collection<Object>  nonSelectedCollection  = new ArrayList<Object> ();

          int totalGroupCollectionSize = totalGroupCollection.size();
          int counter = 0;

          while(counter < totalGroupCollectionSize)
          {
            personUIDsInTotalGroupList.add(((DeduplicationPatientMergeVO) totalGroupCollection.get(counter)).getMPR().getThePersonDT().getPersonUid());
            counter++;
          }

          while(selectedColliterator.hasNext())
          {
            personUIDsInSelectedList.add(((PersonVO) selectedColliterator.next()).getThePersonDT().getPersonUid());
          }

          totalGroupCollectionSize = totalGroupCollection.size();

          while(totalGroupCollectionSize-- > 0)
          {
            if(personUIDsInSelectedList.contains(personUIDsInTotalGroupList.get(totalGroupCollectionSize)) == false)
          {
            nonSelectedCollection.add(((DeduplicationPatientMergeVO) totalGroupCollection.get(totalGroupCollectionSize)).getMPR());
          }
        }

        return nonSelectedCollection;


        }

        private Collection<Object>  getSimilarGroupToMerge(Integer groupNbr, String[] mergeCandidateList) {
        	ArrayList<Object> list = new ArrayList<Object> ();
        	Collection<Object>  retPerVOColl = null;
        	Collection<Object>  sysIdCollection  = new ArrayList<Object> ();

        	try {
        		Collection<?>  pvoGroup = (Collection<?>)DeDuplicationQueueHelper.getDedupTakenQueue().get(groupNbr);

        		for(int i =0; i<mergeCandidateList.length; i++)
        		{
        			Long selectedId = new Long(mergeCandidateList[i]);
        			Iterator<?>  it = pvoGroup.iterator();
        			while(it.hasNext()) {
        				PersonVO pvo = ((DeduplicationPatientMergeVO)it.next()).getMPR();
        				if(pvo.getThePersonDT().getPersonUid().longValue() == selectedId.longValue()) {
        					list.add(pvo);
        					break;
        				}//end of if
        			}//end of while
        		}//end of while
        	} catch (Exception ex) {
        		logger.error("Exception encountered in CompareMergeLoad.getSimilarGroupToMerge() " + ex.getMessage());
        		ex.printStackTrace();
        	}
        	return list;
        }

        /**
           * Retrieves the personVOs of the persons to be merged.
           * @J2EE_METHOD  --  getPersonVOs
           * @param request       the HttpServletRequest
           * @return session    the  HttpSession
           * @return secObj    the  NBSSecurityObj
           **/
             private Collection<?>  getPersonVOs (HttpServletRequest request, HttpSession session,
                     NBSSecurityObj secObj) throws Exception
             {


                     Collection<?>  retPerVOColl = null;
                     Collection<Object>  sysIdCollection  = null;
                     String listOfMergeCandidates =  (String)request.getParameter("merge");
                     
                     String[] mergeCandidateList = listOfMergeCandidates.split(",");
                     sysIdCollection  = new ArrayList<Object> ();

                     // Get the Uids for the patients to be merged and add it to the collection

                     for(int i =0; i<mergeCandidateList.length; i++)
                     {
                        sysIdCollection.add(new Long(mergeCandidateList[i]));
                     }
                     // call proxy to get the collection of the personVOs to be merged
                     if(sysIdCollection!=null)
                            retPerVOColl = sendProxyToGetPersons(sysIdCollection, session, secObj);

                    // Make sure that the PersonVO's returned as part of the above collection
                    // have a record status code of 'ACTIVE', otherwise raise DataConc Exception and
                    // get out...

                   Iterator<?>  iterator = retPerVOColl.iterator();

                    while(iterator.hasNext())
                    {
                        PersonVO personVO = (PersonVO)iterator.next();
                        if(!personVO.getThePersonDT().getRecordStatusCd().equals("ACTIVE"))
                        {
                            throw new NEDSSConcurrentDataException("ERR109");
                        }
                    }

                    return retPerVOColl;

             }
              /**
                * Retrieves the personVOs of the persons to be merged.
                 * @J2EE_METHOD  --  getPersonVOs
                 * @param sysIdCollection        the Collection
                 * @return session    the  HttpSession
                 * @return secObj    the  NBSSecurityObj
                 * @throws NEDSSAppConcurrentDataException
                **/
                private Collection<?>  sendProxyToGetPersons(Collection<Object> sysIdCollection, HttpSession session,
                      NBSSecurityObj secObj) throws Exception
               {
               /**
               * Call the mainsessioncommand
               */

               MainSessionCommand msCommand = null;
               try
               {
                      String sBeanJndiName = JNDINames.ENTITY_PROXY_EJB;
                      String sMethod = "getPersons";

                      //##!! System.out.println("Number of elements in UID collection that is passed to EntityProxy.getPersons is " +	sysIdCollection.size());

                      Object[] oParams = new Object[]{sysIdCollection};

                      MainSessionHolder holder = new MainSessionHolder();
                      msCommand = holder.getMainSessionCommand(session);

                      //##!! System.out.println("Jndi name= " + sBeanJndiName + " Method= " + sMethod);

                      Collection<?>  resultPerVOs = (ArrayList<?> )msCommand.processRequest(sBeanJndiName, sMethod, oParams).get(0);

                      if ((resultPerVOs != null)) //&& (resultPerVOs.size() > 0))
                      {
                              logger.debug("Found PersonVOs");
                              return resultPerVOs;
                      }
                      else
                      {
                              return null;
                      }
              }

              finally
              {
                      msCommand = null;

              }

      }
            
                
        /**
         * updateDedupIndForRemovedPatientRecord: it will remove from the collection those patient records that have been removed from merge.
         * That way will know how many records are left in the group: if <=1, we need to jump to next one, if any. if >1 we need to show the current group
         * @param dedupTakenQueue
         */
                
        private void   updateDedupIndForRemovedPatientRecord(	HashMap<Object,Object> dedupTakenQueue, String patientUid){
        	
        	
        	Iterator it = dedupTakenQueue.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                ArrayList<DeduplicationPatientMergeVO> patientsVO =  (ArrayList<DeduplicationPatientMergeVO> )pair.getValue();
                for(int i=0; i<patientsVO.size(); i++){
                	PersonVO mpr = ((DeduplicationPatientMergeVO)patientsVO.get(i)).getMPR();
                	String personUid = mpr.getThePersonDT().getPersonUid()+"";
                	if(patientUid!=null && personUid.equalsIgnoreCase(patientUid)){
                		patientsVO.remove(i);
                		i--;
                	}
                	
                }
               // System.out.println(pair.getKey() + " = = =  " + pair.getValue());
        	
        }
        }
        
        /**
         * removeFromMergeForDedup: removes the patient from the current group (from the collection object) and updates the DB to set person.dedup_match_ind = R,
         *  person.group_nbr = NULL,  person.group_time = NULL,
         * @param patientUid
         * @param session
         * @param secObj
         * @throws Exception
         */
      private void  removeFromMergeForDedup(String patientUid, HttpSession session,
                        NBSSecurityObj secObj) throws Exception
                 {
    	  		HashMap<Object,Object> dedupTakenQueue = DeDuplicationQueueHelper.getDedupTakenQueue();
    	  		//Removes the patient from the collection object
                updateDedupIndForRemovedPatientRecord(dedupTakenQueue, patientUid);
                
                
                    
                 //Updates the DB:
                 /**
                 * Call the mainsessioncommand
                 */

                 MainSessionCommand msCommand = null;
                 try
                 {
                        String sBeanJndiName = JNDINames.DEDUPLICATION_PROCESSOR_EJB;
                        String sMethod = "removeFromMergeForDedup";


                        //##!! System.out.println("Number of elements in UID collection that is passed to EntityProxy.getPersons is " +	sysIdCollection.size());

                        Object[] oParams = new Object[]{patientUid};

                        MainSessionHolder holder = new MainSessionHolder();
                        msCommand = holder.getMainSessionCommand(session);

                        //##!! System.out.println("Jndi name= " + sBeanJndiName + " Method= " + sMethod);

                        DeduplicationPatientMergeVO dedupPatientMergeVO = (DeduplicationPatientMergeVO)msCommand.processRequest(sBeanJndiName, sMethod, oParams).get(0);
                }
                finally
                {
                        msCommand = null;
                }

        }
                
                
      private Collection<Object>  sendProxyToMeregPersons(Collection<?> pVOCollection, String specifiedSurvivorPatientId, HttpSession session,
                    NBSSecurityObj secObj) throws Exception
             {
             /**
             * Call the mainsessioncommand
             */

             MainSessionCommand msCommand = null;
             try
             {
                    String sBeanJndiName = JNDINames.DEDUPLICATION_PROCESSOR_EJB;
                    String sMethod = "mergeMPR";


                    //##!! System.out.println("Number of elements in UID collection that is passed to EntityProxy.getPersons is " +	sysIdCollection.size());

                    Object[] oParams = new Object[]{pVOCollection, specifiedSurvivorPatientId};

                    MainSessionHolder holder = new MainSessionHolder();
                    msCommand = holder.getMainSessionCommand(session);

                    //##!! System.out.println("Jndi name= " + sBeanJndiName + " Method= " + sMethod);

                    DeduplicationPatientMergeVO dedupPatientMergeVO = (DeduplicationPatientMergeVO)msCommand.processRequest(sBeanJndiName, sMethod, oParams).get(0);

                    if ((dedupPatientMergeVO.getTheMergeConfirmationVO() != null)) //&& (resultPerVOs.size() > 0))
                    {
                            logger.debug("Found PersonVOs");
                            Object[] oParam = new Object[]{dedupPatientMergeVO.getThePersonMergeDT()};
                            String method = "createPersonMergeDt";
                            msCommand.processRequest(sBeanJndiName, method, oParam);
                            return dedupPatientMergeVO.getTheMergeConfirmationVO();
                    }
                    else
                    {
                            return null;
                    }
            }
            finally
            {
                    msCommand = null;
            }

    }
    /**
     * Processes user actions Merge and NoMerge
     * @param groupNumber
     * @param session
     * @param contextAction
     * @param secObj
     * @throws NEDSSAppException
     */
    private void processPostMergeOrNoMergeForSystemIdentified(Integer groupNumber, HttpSession session, String contextAction, NBSSecurityObj secObj) throws Exception
    {

        synchronized(this)
        {

            if (contextAction.equalsIgnoreCase("Merge")) {
              HashMap<Object,Object> hashMap = DeDuplicationQueueHelper.getDedupTakenQueue();
              hashMap.remove(groupNumber);

              hashMap = DeDuplicationQueueHelper.getDedupSessionQueue();
              hashMap.remove(session.getId());
              return;
            }

            if (contextAction.equalsIgnoreCase("NoMerge")) {

              // Go to the DataBase and null out the groupNumber field and groupTime field associated
              // with the PVO's of this groupNumber...
              MainSessionCommand msCommand = null;
              String sBeanJndiName = JNDINames.EntityControllerEJB;
              String sMethod = "setMPR";

              MainSessionHolder holder = new MainSessionHolder();
              msCommand = holder.getMainSessionCommand(session);

              Collection<?>  collection = (Collection<?>) DeDuplicationQueueHelper.
                  getDedupTakenQueue().get(groupNumber);

                //Remove the group number
                HashMap<Object,Object> hashMap = DeDuplicationQueueHelper.getDedupTakenQueue();
                hashMap.remove(groupNumber);

                hashMap = DeDuplicationQueueHelper.getDedupSessionQueue();
                hashMap.remove(session.getId());

                if(collection!=null){
             Iterator<?>  iterator = collection.iterator();

              //while (iterator.hasNext()) {
                //ArrayList<Object> deDupMergeArrayList = (ArrayList<Object> ) iterator.next();
                //Iterator ite = deDupMergeArrayList.iterator();
                while(iterator.hasNext())
                {
                  DeduplicationPatientMergeVO dedupPatientMergeVO = (DeduplicationPatientMergeVO)iterator.next();
                  PersonVO personVO = (PersonVO)dedupPatientMergeVO.getMPR();
                  personVO.setItDirty(true);
                  personVO.setItNew(false);
                  personVO.getThePersonDT().setItDirty(true);
                  personVO.getThePersonDT().setItNew(false);
                  personVO.getThePersonDT().setGroupNbr(null);
                  personVO.getThePersonDT().setGroupTime(null);

                  Object[] oParams = new Object[]
                      {personVO, "PAT_NO_MERGE"};
                  msCommand.processRequest(sBeanJndiName, sMethod, oParams);
                }
            	HashMap<Object,Object> deduplicationAvailableQueue = DeDuplicationQueueHelper.getDedupAvailableQueue();
            	HashMap<Object,Object> deduplicationAvailableQueueSkipped = DeDuplicationQueueHelper.getDedupAvailableQueueSkipped();
                
            	//In case we have already skipped all the collection, we will take the skipped ones and move them to the available queue
            	if (deduplicationAvailableQueue==null || deduplicationAvailableQueue.size()==0)
            		if(deduplicationAvailableQueueSkipped!=null && deduplicationAvailableQueueSkipped.size()>0){
            			DeDuplicationQueueHelper.setDedupAvailableQueue(deduplicationAvailableQueueSkipped);
            			deduplicationAvailableQueue = DeDuplicationQueueHelper.getDedupAvailableQueue();
            			DeDuplicationQueueHelper.setDedupAvailableQueueSkipped(new HashMap<Object,Object>());
            		}
                
                
                
              }
            }
              return;

        }//end of sync
    }

    /**
     * Given a groupNumber and on cancel action from the user the method sets the HashMap's appropriately
     * @param inGroupNumber
     * @param inSession
     */
    private void processContextActionCancel(Integer inGroupNumber, HttpSession inSession)
    {
        synchronized(this)
        {
            HashMap<Object,Object> deduplicationAvailableQueue = DeDuplicationQueueHelper.getDedupAvailableQueue();
            HashMap<Object,Object> dedupTakenQueue = DeDuplicationQueueHelper.getDedupTakenQueue();
            HashMap<Object,Object> dedupSkippedQueue = DeDuplicationQueueHelper.getDedupAvailableQueueSkipped();
     
            //Since the value of the object in the DeDupTakenQueue is a collection of PVO's
            //we need to grab the first one and get a PersonDT out of it and then get the groupTime on it
            //to set it on the AvailableQueue
            if(dedupTakenQueue!=null)
            {
              Collection<?>  collection = (Collection<?>) dedupTakenQueue.get(inGroupNumber);
              if(collection != null)
              {
               Iterator<?>  iterator = collection.iterator();

                if (iterator.hasNext())
                {
                  DeduplicationPatientMergeVO dedupPatientMergeVO = (DeduplicationPatientMergeVO) iterator.next();
                  deduplicationAvailableQueue.put(inGroupNumber,
                                                  dedupPatientMergeVO.getMPR().
                                                  getThePersonDT().getGroupTime());
                }
              }

              dedupTakenQueue.remove(inGroupNumber);

              if(DeDuplicationQueueHelper.getDedupSessionQueue()!=null)DeDuplicationQueueHelper.getDedupSessionQueue().remove(inSession.getId());
              
 
              
              //ND-27735: we need to restore the groups skipped into the the available queue
              
              if(dedupSkippedQueue!=null)
              {
            	  
            	  
            	  
            	  for (Map.Entry<Object, Object> entry : dedupSkippedQueue.entrySet()) {
            		    Object keyGroupNumber = entry.getKey();
            		    Timestamp collectionSkippedTimeStamp = (Timestamp) dedupSkippedQueue.get(keyGroupNumber);
            		    
                        if(collectionSkippedTimeStamp != null){
                                             deduplicationAvailableQueue.put(keyGroupNumber,collectionSkippedTimeStamp);
                        }
            		}

            	  dedupSkippedQueue.clear();
            	  
              }
              
            }
        }
    }
    
    /**
     * getMaxGroupNumber: returns the maximum group number on the list
     * @param deduplicationAvailableQueue
     * @return
     */
    private int getMaxGroupNumber(HashMap<Object,Object> deduplicationAvailableQueue){
    	
    	int maxNumber = 0;
    	  Iterator it = deduplicationAvailableQueue.entrySet().iterator();
    	    while (it.hasNext()) {
    	        Map.Entry pair = (Map.Entry)it.next();
    	        int intKey = (int)pair.getKey();
    	      //  it.remove(); // avoids a ConcurrentModificationException
    	        
    	     //   int intKey = Integer.parseInt(key);
    	        if(intKey > maxNumber)
    	        	maxNumber = intKey;
    	    }
    	    
    	return maxNumber;
    }
    
    /**
     * Given a groupNumber and on skip action from the user the method sets the HashMap's appropriately
     * @param inGroupNumber
     * @param inSession
     */
    private void processContextActionSkip(Integer inGroupNumber, HttpSession inSession)
    {
        synchronized(this)
        {
        	HashMap<Object,Object> deduplicationAvailableQueue = DeDuplicationQueueHelper.getDedupAvailableQueue();
        	HashMap<Object,Object> deduplicationAvailableQueueSkipped = DeDuplicationQueueHelper.getDedupAvailableQueueSkipped();
            
        	//In case we have already skipped all the collection, we will take the skipped ones and move them to the available queue
        	if (deduplicationAvailableQueue==null || deduplicationAvailableQueue.size()==0)
        		if(deduplicationAvailableQueueSkipped!=null && deduplicationAvailableQueueSkipped.size()>0){
        			DeDuplicationQueueHelper.setDedupAvailableQueue(deduplicationAvailableQueueSkipped);
        			deduplicationAvailableQueue = DeDuplicationQueueHelper.getDedupAvailableQueue();
        			DeDuplicationQueueHelper.setDedupAvailableQueueSkipped(new HashMap<Object,Object>());
        		}
        	
            HashMap<Object,Object> dedupTakenQueue = DeDuplicationQueueHelper.getDedupTakenQueue();
            /*int numberInQueue = deduplicationAvailableQueue.size();
            numberInQueue=getMaxGroupNumber(deduplicationAvailableQueue);
            numberInQueue++;*/
            
            //Since the value of the object in the DeDupTakenQueue is a collection of PVO's
            //we need to grab the first one and get a PersonDT out of it and then get the groupTime on it
            //to set it on the AvailableQueue
            if(dedupTakenQueue!=null)
            {
              Collection<?>  collection = (Collection<?>) dedupTakenQueue.get(inGroupNumber);
              if(collection != null)
              {
               Iterator<?>  iterator = collection.iterator();

                if (iterator.hasNext())
                {
                  DeduplicationPatientMergeVO dedupPatientMergeVO = (DeduplicationPatientMergeVO) iterator.next();
                  deduplicationAvailableQueueSkipped.put(inGroupNumber,
                                                  dedupPatientMergeVO.getMPR().
                                                  getThePersonDT().getGroupTime());
                }
              }

              if(deduplicationAvailableQueue.size()== 0 && deduplicationAvailableQueueSkipped.size()==1)//There's only 1 group, and we are skipping it, it should show it again.
            	  DeDuplicationQueueHelper.setDedupAvailableQueue(deduplicationAvailableQueueSkipped);
              dedupTakenQueue.remove(inGroupNumber);

              if(DeDuplicationQueueHelper.getDedupSessionQueue()!=null)
            	  DeDuplicationQueueHelper.getDedupSessionQueue().remove(inSession.getId());
            }
        }
    }
    
    /**
     * processContextActionRemoveFromMerge: this method will choose what group to show in the page:
     * - If there's more than 1 patient in the current group, the current group will keep showing
     * - If there's 1 or 0 patients remaining in the current group:
     * 		- if there's more groups in the available queue, it will be taken from there
     * 		- if there's no more groups in the available queue, it will get it from the skipped queue (like skip option works).
     * @param inGroupNumber
     * @param inSession
     */
    private void processContextActionRemoveFromMerge(Integer inGroupNumber, HttpSession inSession)
    {
        synchronized(this)
        {
        	HashMap<Object,Object> deduplicationAvailableQueue = DeDuplicationQueueHelper.getDedupAvailableQueue();
        	HashMap<Object,Object> deduplicationAvailableQueueSkipped = DeDuplicationQueueHelper.getDedupAvailableQueueSkipped();
            
        	
        	
            HashMap<Object,Object> dedupTakenQueue = DeDuplicationQueueHelper.getDedupTakenQueue();
          
            //Since the value of the object in the DeDupTakenQueue is a collection of PVO's
            //we need to grab the first one and get a PersonDT out of it and then get the groupTime on it
            //to set it on the AvailableQueue
            if(dedupTakenQueue!=null)
            {
              Collection<?>  collection = (Collection<?>) dedupTakenQueue.get(inGroupNumber);
              if(collection != null)
              {
               Iterator<?>  iterator = collection.iterator();
               int numberOfPatientsRemainingInCollection = collection.size();
               
               if(numberOfPatientsRemainingInCollection>1){//If there's more than 1 patient, we will show the same collection again
                if (iterator.hasNext())
                {
                  DeduplicationPatientMergeVO dedupPatientMergeVO = (DeduplicationPatientMergeVO) iterator.next();
                  deduplicationAvailableQueue.put(inGroupNumber,
                                                  dedupPatientMergeVO.getMPR().
                                                  getThePersonDT().getGroupTime());
                }
              }else{//If there's only 1 or none patients in the collection, then we will go to next page.
            	  //If there's only collections in the skipped one, then, we will get the patients from the skipped collection
            	  
              	//In case we have already skipped all the collection, we will take the skipped ones and move them to the available queue
              	if (deduplicationAvailableQueue==null || deduplicationAvailableQueue.size()==0)
              		if(deduplicationAvailableQueueSkipped!=null && deduplicationAvailableQueueSkipped.size()>0){
              			DeDuplicationQueueHelper.setDedupAvailableQueue(deduplicationAvailableQueueSkipped);
              			deduplicationAvailableQueue = DeDuplicationQueueHelper.getDedupAvailableQueue();
              			DeDuplicationQueueHelper.setDedupAvailableQueueSkipped(new HashMap<Object,Object>());
              		}
            	  
            	  
              }
               
              }

              //Do we need this??
              if(DeDuplicationQueueHelper.getDedupSessionQueue()!=null)
            	  DeDuplicationQueueHelper.getDedupSessionQueue().remove(inSession.getId());
            }
        }
    }
    
    
    /**
     * findOldestPatientId - find the patient id that is less than all the others
     * Per christi the users want to default to the oldest patient id
     * @param personVOCollection
     * @return
     */
    private String findOldestPatientId(Collection<?> personVOCollection) {
    	Iterator personIter = personVOCollection.iterator();
    	String oldestPersonId = null;
    	while (personIter.hasNext()) {
    		PersonVO personVO = (PersonVO) personIter.next();
    		String personID = PersonUtil.getDisplayLocalID(personVO.getThePersonDT().getLocalId());
    		if (oldestPersonId == null)
    			oldestPersonId = personID;
    		else {
    			Long currentOldestPersonId = new Long(oldestPersonId);
    			Long thisPersonId = new Long(personID);
    			if (thisPersonId.compareTo(currentOldestPersonId) < 0)
    				oldestPersonId = personID;
    		}
    		
    	} //while hasNext
    	
	return oldestPersonId;
}


	public String createConfirmationMessage( Collection mergeConfirmationVO1){
		
		//"The following patients have been successfully merged into 88011 (First Name1 Middle Name1 Patient_1, Legal):
		//88029 (First Name1 Middle Name1 Patient_1, Legal) ";"
		 String message = "The following patients have been successfully merged into ";
         MergeConfirmationVO survivorVO = new MergeConfirmationVO();
         MergeConfirmationVO mergeConfirmationVO = new MergeConfirmationVO();
         if(mergeConfirmationVO1!=null)
         {
	         Iterator itr = mergeConfirmationVO1.iterator();
	         while(itr.hasNext())
	         {
	           mergeConfirmationVO = (MergeConfirmationVO)itr.next();
	           if(mergeConfirmationVO!=null && mergeConfirmationVO.getSurvivor().booleanValue()==true)
	           survivorVO = mergeConfirmationVO;
	           
	         }	
	         String firstName = survivorVO.getFirstName()==null?"": survivorVO.getFirstName();
	         String middleName = survivorVO.getMiddleName()==null?"": survivorVO.getMiddleName();
	         String lastName = survivorVO.getLastName()==null?"": survivorVO.getLastName();
	         String nameUseCdDesc = survivorVO.getNmUseCdDesc()==null?"": survivorVO.getNmUseCdDesc();
	         message+=PersonUtil.getDisplayLocalID(survivorVO.getLocalId())+" "+firstName+" "+middleName+" "+lastName+", "+nameUseCdDesc;
	           
	         
	         message+="<ul>";
	         Iterator itr1 = mergeConfirmationVO1.iterator();
	         while(itr1.hasNext())
	         {
	           mergeConfirmationVO = (MergeConfirmationVO)itr1.next();
	           
	           firstName = mergeConfirmationVO.getFirstName()==null?"": mergeConfirmationVO.getFirstName();
		       middleName = mergeConfirmationVO.getMiddleName()==null?"": mergeConfirmationVO.getMiddleName();
		       lastName = mergeConfirmationVO.getLastName()==null?"": mergeConfirmationVO.getLastName();
		       nameUseCdDesc = mergeConfirmationVO.getNmUseCdDesc()==null?"": mergeConfirmationVO.getNmUseCdDesc();
		         
		         
	           if(mergeConfirmationVO!=null && mergeConfirmationVO.getSurvivor().booleanValue()!=true)
	        	   message+="<li>"+PersonUtil.getDisplayLocalID(mergeConfirmationVO.getLocalId())+"      "+ firstName+"      "+ middleName+"      "+ lastName+", "+nameUseCdDesc+"</li>";
		     
	         }
	         message+="</ul>";
         }
         return message;
	}
	
}