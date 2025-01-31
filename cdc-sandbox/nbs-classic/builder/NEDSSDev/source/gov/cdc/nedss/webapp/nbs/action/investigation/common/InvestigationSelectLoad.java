package gov.cdc.nedss.webapp.nbs.action.investigation.common;


import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;
import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.webapp.nbs.action.util.*;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.*;

public class InvestigationSelectLoad  extends Action
{


  public InvestigationSelectLoad()
  {
  }
    //private NEDSSConstants Constants = new NEDSSConstants();
    //private PropertyUtil PropertyUtil = new PropertyUtil(NEDSSConstants.PROPERTY_FILE);



    static final LogUtils logger = new LogUtils(InvestigationSelectLoad.class.getName());

    public ActionForward execute(ActionMapping mapping,
				 ActionForm aForm,
				 HttpServletRequest request,
				 HttpServletResponse response)
    throws IOException, ServletException
    {
	  logger.debug("inside the InvestigationSelectLoad class");
	  HttpSession session = request.getSession();
	  String sCurrentTask = NBSContext.getCurrentTask(session);
	  if (session == null)
	  {
	    logger.fatal("error no session");
	    return mapping.findForward("login");
	  }
	  NBSSecurityObj nbsSecurityObj = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");
	  if(nbsSecurityObj == null)
	  {
	    logger.fatal("Error: no nbsSecurityObj in the session, go back to login screen");
	    return mapping.findForward("login");
	  }
	  
	  //set logic for creating investigation from STD Lab
	  try{
		  String programAreaCd = (String)NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationProgramArea);
		  String observationType = (String)NBSContext.retrieve(session, NBSConstantUtil.DSObservationTypeCd);
			if (programAreaCd != null
					&& PropertyUtil.isStdOrHivProgramArea(programAreaCd)
					&& observationType != null
					&& observationType.equals(NEDSSConstants.LAB_REPORT))
				request.setAttribute("CreateInvestigationFromSTDorHIVLab", NEDSSConstants.TRUE);
	  }catch(Exception ex){
		  logger.debug("Context not available for " +sCurrentTask+" while setting logic for creating investigation from STD Lab");
	  }
	  //Addition for Create Investigation from Document - If you have to go the Create Load page directly
	  //String sCurrentTask=null;
	  String conditionCd = request.getParameter("ConditionCd");
	  String processingDecision = request.getParameter("markAsReviewReason");
	  if(conditionCd  != null || (conditionCd != null && !conditionCd.equals(""))){
		  String programAreas = this.getProgramAreas(request, nbsSecurityObj);
		  TreeMap<Object,Object> programAreaTreeMap = new TreeMap<Object, Object>();
		  try{
	         CachedDropDownValues cdv = new CachedDropDownValues();
		     programAreaTreeMap = cdv.getAllProgramAreaConditions(programAreas);
		     TreeMap<Object,Object> conditionTreeMap = new TreeMap<Object, Object>();
		  }catch(Exception e){
			  throw new ServletException("could not get conditions"); 
		  }
		  ProgramAreaVO programAreaVO = null;  
		if(programAreaTreeMap != null && programAreaTreeMap.containsKey(conditionCd)){
		// redirect to the form
		    
		        programAreaVO = (ProgramAreaVO) programAreaTreeMap.get(
		        		conditionCd);
		        logger.debug("programAreaCd: " + programAreaVO.getStateProgAreaCode() +
		                     " programAreaCdDesc: " +
		                     programAreaVO.getStateProgAreaCdDesc() + " conditionCd: " +
		                     programAreaVO.getConditionCd() + " conditionShortDesc: " +
		                     programAreaVO.getConditionShortNm());
		      if(!"STD".equals(programAreaVO.getStateProgAreaCode()) && !"HIV".equals(programAreaVO.getStateProgAreaCode())) {
		    
			      logger.debug("programAreaVO.getInvestigationFormCd()" +
			                   programAreaVO.getInvestigationFormCd());
			      NBSContext.store(session, NBSConstantUtil.DSInvestigationCondition,
			                       programAreaVO.getConditionCd());
			      NBSContext.store(session, NBSConstantUtil.DSInvestigationProgramArea,
			                       programAreaVO.getStateProgAreaCode());
			      NBSContext.store(session, NBSConstantUtil.DSInvestigationCode,
			                       programAreaVO.getInvestigationFormCd());
			      NBSContext.store(session, NBSConstantUtil.DSFileTab, "3");
			      NBSContext.store(session, NBSConstantUtil.DSDocConditionCD, conditionCd);
			      if(processingDecision!=null)
			    	  NBSContext.store(session, NBSConstantUtil.DSProcessingDecision, processingDecision);
			      request.getSession().setAttribute("PrevPageId", "PS233");
			      //return mapping.findForward("CreateCRInvestigation");
			      //return mapping.findForward("/nbs/LoadCreateInvestigation10.do?ContextAction=CreateCRInvestigation" ) ;
			      
			      String url = null;
			      if(sCurrentTask != null && sCurrentTask.equals("ViewDocument2")){
			    	  url = "/nbs/LoadCreateInvestigation10.do?ContextAction=CreateCRInvestigation";
			      }else if(sCurrentTask != null && sCurrentTask.equals("ViewDocument4")){
			    	  url = "/nbs/LoadCreateInvestigation11.do?ContextAction=CreateCRInvestigation"; 
			      }
			      response.sendRedirect(url);
			      return null;
		      } else {
		    	  request.setAttribute("selectedSTDCondition" , conditionCd);
		      }
		    }
		} // End of if Condition to directly go to load the page 
		  
	
	
	  ErrorMessageHelper.setErrMsgToRequest(request, "PS078");

          String sContextAction = request.getParameter("ContextAction");
          request.setAttribute("PrevContextAction", sContextAction);
          //##!! System.out.println("what is the current Action in invselectLoad=========="+sContextAction);
	  logger.info("sContextAction in InvestigationSelectLoad = " + sContextAction);
          if (sContextAction == null)
	  {
              logger.error("sContextAction is null");
              session.setAttribute("error", "No Context Action in InvestigationSelectLoad");
              throw new ServletException("sContextAction is null");
	  }

	  if(sContextAction.equals(NBSConstantUtil.AddInvestigation) || sContextAction.equalsIgnoreCase("SubmitAndCreateInvestigation") || sContextAction.equals("CreateInvestigation"))
	  {
	    String forwardStr = this.chooseInvestigation(request, session, nbsSecurityObj);
	    if(sCurrentTask!=null && (sCurrentTask.equalsIgnoreCase("ViewDocument4") || sCurrentTask.equalsIgnoreCase("ViewDocument2"))) {
	    	request.setAttribute("referralBasisDocument", NEDSSConstants.REFERRAL_BASIS_MORB);//ND-25158: To make sure Processing Decision popups open if Program area is not STD but the condition is STD when there's more han 1 condiiona ssociaed to the eICR
	    	request.setAttribute("PrevContextAction", NBSConstantUtil.ViewDocument);
	    	String selectedConditionCd = (String) request.getAttribute("selectedSTDCondition");
	    	this.chooseInvestigationForSelectedCondition(request, session, nbsSecurityObj, selectedConditionCd);
	    	request.setAttribute("isCreateInvFromViewDocument", "true");
	    	NBSContext.store(session, NBSConstantUtil.DSisCreateInvFromViewDocument, "true");
	    }
	    return mapping.findForward(forwardStr);
	  }
          else if(sContextAction.equalsIgnoreCase(NBSConstantUtil.CANCEL))
          { 
        	  String forwardStr = "XSP";
            NBSContext.store(session,NBSConstantUtil.DSFileTab,"3");
            //To take care of having the referral basis when hit cancel from create investigation
            if(sCurrentTask!=null && sCurrentTask.equals("CreateInvestigation1")){
            	 request.setAttribute("PrevContextAction", NBSConstantUtil.AddInvestigation);
            }
            
            if(sCurrentTask!=null && (sCurrentTask.equals("CreateInvestigation11") || sCurrentTask.equals("CreateInvestigation10") )) {//From Add Investigation to Choose investigation (eicr)
            	request.setAttribute("referralBasisDocument", "T2");//ND-25158: To make sure Processing Decision popups open if Program area is not STD but the condition is STD when there's more han 1 condiiona ssociaed to the eICR           	 
            	
            	try {
	            	String isCreateInvFromViewDocument = (String) NBSContext.retrieve(request.getSession(), NBSConstantUtil.DSisCreateInvFromViewDocument);
	                //String programAreaCd = (String)NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationProgramArea);
	            	if("true".equals(isCreateInvFromViewDocument)) {
		                String invConditionCd = (String)NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationCondition);
		                forwardStr = this.chooseInvestigation(request, session, nbsSecurityObj);
		                this.chooseInvestigationForSelectedCondition(request, session, nbsSecurityObj, invConditionCd);
		                request.setAttribute("referralBasisDocument", NEDSSConstants.REFERRAL_BASIS_MORB);
		                request.setAttribute("PrevContextAction", NBSConstantUtil.ViewDocument);
		                request.setAttribute("isCreateInvFromViewDocument", "true");
		                request.setAttribute("selectedSTDCondition" , invConditionCd);
		                //this.setContextForChoose(request, session);
	            	}
            	} catch (Exception ex) {
            		logger.error("DSisCreateInvFromViewDocument not available in NBSContext "+ex.getMessage());
            	}
                
            } else {
	            forwardStr = this.chooseInvestigation(request, session, nbsSecurityObj);
            }
	    return mapping.findForward(forwardStr);
          }
	  else
	  {
	   // session.setAttribute("error", "Invalid ContextAction: " + sContextAction);
	    return mapping.findForward("XSP");
	  }

      }

      private String  chooseInvestigation(HttpServletRequest request, HttpSession session, NBSSecurityObj nbsSecurityObj) throws ServletException
      {
      String programAreas =  getProgramAreas(request,nbsSecurityObj);
      StringBuffer STDConditions=new StringBuffer("");
	  try
	  {

         CachedDropDownValues cdv = new CachedDropDownValues();
	     TreeMap<Object,Object> programAreaTreeMap = cdv.getActiveProgramAreaConditions(programAreas);
	     TreeMap<Object,Object> conditionTreeMap = new TreeMap<Object, Object>();

             //NBSContext.store(session, "programAreaTreeMap", programAreaTreeMap);
	     session.setAttribute("programAreaTreeMap", programAreaTreeMap);
	     String conditionCdDescString = null;
	     ProgramAreaVO programAreaVO = null;
	     if(programAreaTreeMap != null && programAreaTreeMap.size() > 0)
	     {
		Set<Object> set = programAreaTreeMap.keySet();
		Iterator<Object> itr = set.iterator();
		while( itr.hasNext())
		{
		    String key = (String)itr.next();
		    programAreaVO = (ProgramAreaVO)programAreaTreeMap.get(key);
		    if(PropertyUtil.isStdOrHivProgramArea(programAreaVO.getStateProgAreaCode()))
		    	STDConditions.append(programAreaVO.getConditionCd()).append("|");
		    conditionTreeMap.put(key, programAreaVO.getConditionShortNm());
		    //sb.append(programAreaVO.getConditionCd()).append("$").append(programAreaVO.getConditionShortNm()).append("|");
		}
		conditionCdDescString = WumUtil.sortTreeMap(conditionTreeMap);
	     }

	     request.setAttribute("DSConditionCdDescString" , conditionCdDescString);
	     request.setAttribute("STDConditions" , STDConditions.toString());
	  }
	  catch(Exception e)
	  {
	     throw new ServletException("could not get conditions");
	  }
      this.setContextForChoose(request, session);

	  return "XSP";
      }

      private void chooseInvestigationForSelectedCondition(HttpServletRequest request, HttpSession session, NBSSecurityObj nbsSecurityObj, String selectedConditionCd) throws ServletException
      {
	      String programAreas =  getProgramAreas(request,nbsSecurityObj);
		  try {
	         CachedDropDownValues cdv = new CachedDropDownValues();
		     TreeMap<Object,Object> programAreaTreeMap = cdv.getActiveProgramAreaConditions(programAreas);
		     TreeMap<Object,Object> conditionTreeMap = new TreeMap<Object, Object>();
	
		     String conditionCdDescString = null;
		     ProgramAreaVO programAreaVO = null;
		     StringBuffer sb = new StringBuffer();
		     if(programAreaTreeMap != null && programAreaTreeMap.size() > 0) {
		    	 programAreaVO = (ProgramAreaVO) programAreaTreeMap.get(selectedConditionCd);
		    	 conditionTreeMap.put(selectedConditionCd, programAreaVO.getConditionShortNm());
		    	 sb.append(selectedConditionCd).append("$").append(programAreaVO.getConditionShortNm()).append("|");
				//conditionCdDescString = WumUtil.sortTreeMap(conditionTreeMap);
		     }
	
		     request.setAttribute("DSConditionCdDescString" , sb.toString());
		  } catch(Exception e) {
		     throw new ServletException("could not get conditions");
		  }
		  
	      //this.setContextForChoose(request, session);
	
		  //return "XSP";
      }
      
     private void setContextForChoose(HttpServletRequest request, HttpSession session)
      {

      	    String sContextAction = request.getParameter("ContextAction");
            logger.debug("sContextAction: " + sContextAction);
            TreeMap<Object,Object> tm = NBSContext.getPageContext(session, "PS078", sContextAction);
	    NBSContext.lookInsideTreeMap(tm);

	    String sCurrentTask = NBSContext.getCurrentTask(session);
	    request.setAttribute("formHref", "/nbs/" + sCurrentTask+ ".do");
	    request.setAttribute("ContextAction", tm.get("Submit"));
	    request.setAttribute("cancelButtonHref", "/nbs/" + sCurrentTask + ".do?ContextAction=" + tm.get("Cancel"));
    }
   
    public String getProgramAreas(HttpServletRequest request, NBSSecurityObj nbsSecurityObj){
    	
  	  String programAreas = "";
	  StringBuffer stingBuffer = new StringBuffer();

	  TreeMap<Object,Object> treeMap = (TreeMap<Object, Object>)nbsSecurityObj.getProgramAreas(NBSBOLookup.INVESTIGATION, NBSOperationLookup.ADD);
	  if(treeMap != null && treeMap.size() > 0)
	  {
	     logger.debug("nbsSecurityObj.getProgramAreas(): " + nbsSecurityObj.getProgramAreas(NBSBOLookup.INVESTIGATION, NBSOperationLookup.ADD));
	    Iterator<Object>  anIterator = treeMap.keySet().iterator();
	     stingBuffer.append("(");
	     while(anIterator.hasNext())
	     {


		String token = (String)anIterator.next();
		logger.debug("resultValue is : "+token);
		String NewString = "";
		if(token.lastIndexOf("!")<0)
		{
		    NewString = token;
		}
		else
		{
		    NewString = token.substring(0, token.lastIndexOf("!"));
		    logger.debug("NewString Value: "+ NewString );
		}

		stingBuffer.append("'").append(NewString).append("',");
	     }
	     stingBuffer.replace(stingBuffer.length() - 1, stingBuffer.length(),"");
	     stingBuffer.append(")");
	     programAreas = stingBuffer.toString();
	     logger.debug("programAreas: " + programAreas);

	  }
	  else
	  {
	    logger.error("could not get Program Areas from security object");
	    request.setAttribute("error", "could not get Program Areas from security object");
	    return "error";
	  }

    	return programAreas;
    }
     
}
