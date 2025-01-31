package gov.cdc.nedss.webapp.nbs.action.person;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import gov.cdc.nedss.entity.person.vo.PatientSearchVO;
import gov.cdc.nedss.locator.dt.PhysicalLocatorDT;
import gov.cdc.nedss.systemservice.ejb.dbauthejb.dao.DbAuthDAOImpl;
import gov.cdc.nedss.systemservice.ejb.dbauthejb.dt.AuthUserDT;
import gov.cdc.nedss.systemservice.genericXMLParser.GenericXMLParser;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.nbssecurity.ProgramAreaVO;
import gov.cdc.nedss.systemservice.nbssecurity.RealizedRole;
import gov.cdc.nedss.systemservice.nbssecurity.User;
import gov.cdc.nedss.systemservice.nbssecurity.UserProfile;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.util.HTMLEncoder;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.WumUtil;
import gov.cdc.nedss.webapp.nbs.action.util.ErrorMessageHelper;
import gov.cdc.nedss.webapp.nbs.form.person.PersonSearchForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

/**
 * This is the action class that is used to prepare the Person Search page for
 * use.
 */
public class PersonSearchLoad extends Action {
	
	
	static final LogUtils logger = new LogUtils(PersonSearchLoad.class.getName());
	/**
	 * This is the constructor for the PersonSearchLoad class.
	 */
	public PersonSearchLoad() {
	}
	 private String sBeanJndiName = "";
	    private String sMethod = "";
	/**
	 * The perform method is the method that gets executed first when a request
	 * is made to this action class.
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @exception IOException
	 * @exception ServletException
	 * @return ActionForward
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		PropertyUtil propertyUtil = PropertyUtil.getInstance();

		HttpSession session = request.getSession(false);
		NBSSecurityObj secObj = (NBSSecurityObj) session.getAttribute("NBSSecurityObject");
		String strType = request.getParameter("OperationType");
		String confirmationMergeMessage = (String)session.getAttribute("confirmationMessage");
		request.setAttribute("confirmationMergeMessage",confirmationMergeMessage);
		session.removeAttribute("confirmationMessage");


		if (strType == null)
			strType = (String) request.getAttribute("OperationType");
		String contextAction = request.getParameter("ContextAction");

		if (contextAction == null)
			contextAction = (String) request.getAttribute("ContextAction");
		
		String mergePatient = (String)request.getAttribute("MergePatient");//In case we are coming from Refine from Merge Patient or after merging, return to Find Patient Search Criteria
		
		if(contextAction!=null && contextAction.equalsIgnoreCase("GlobalMP_ManualSearch"))
			request.setAttribute("MergePatient", "true");

		ErrorMessageHelper.setErrMsgToRequest(request, "ps089");
		if (request.getParameter("PersonName") != null && !request.getParameter("PersonName").equals(""))
			session.setAttribute("PersonName", request.getParameter("PersonName"));
		String PersonName = (String) session.getAttribute("PersonName");
		String abcIndicator = PropertyUtil.getInstance().getABCSTATE();
		if (abcIndicator != null
				&& (abcIndicator.trim().equals("ABCSTATE_ABCQUESTION") || abcIndicator.trim().equals("ABCQUESTION"))) {
			request.setAttribute("abcsInd", "T");
		} else {
			request.setAttribute("abcsInd", "F");
		}
		if (contextAction.equalsIgnoreCase("GlobalPatient") || contextAction.equalsIgnoreCase("GlobalMP_ManualSearch")
				|| contextAction.equalsIgnoreCase("ReturnToFindPatient") || contextAction.equalsIgnoreCase("NewSearch")
				|| contextAction.equalsIgnoreCase("Merge")) {

			// context
			TreeMap<Object,Object> tm = NBSContext.getPageContext(session, "PS089", contextAction);

			String sCurrTask = NBSContext.getCurrentTask(session);
			if (sCurrTask.startsWith("FindPatient1")) {
				session.setAttribute("PersonName", null);
				PersonName = (String) session.getAttribute("PersonName");
			}
			request.setAttribute("PersonName", PersonName);
			if (request.getParameter("Mode1") != null && request.getParameter("Mode1") != "") {
				request.setAttribute("formHref", "/nbs/" + sCurrTask + ".do?Mode1=" + request.getParameter("Mode1"));
			}else
				if(mergePatient!=null && mergePatient.equalsIgnoreCase("true"))//When it comes from merge patientsfa
					request.setAttribute("formHref", "/nbs/" + sCurrTask + ".do?Mode1=" + "ManualMerge");
				else {
				request.setAttribute("formHref", "/nbs/" + sCurrTask + ".do");
			}
			request.setAttribute("ContextAction", tm.get("Submit"));
			boolean bStatusCheckbox = secObj.getPermission(NBSBOLookup.PATIENT, NBSOperationLookup.FINDINACTIVE);
			request.setAttribute("sec-status", String.valueOf(bStatusCheckbox));
			
			int labRows=PropertyUtil.getInstance().getLabNumberOfRows();
			//request.setAttribute("showLabReportOption", labRows == 0?"false":"true");
			
			PersonSearchForm psForm = (PersonSearchForm) form;
			PatientSearchVO psVO = new PatientSearchVO();
			psVO.setActive(true);
			psForm.setPersonSearch(psVO);
			
			if(psForm.getSearchCriteriaArrayMap().size() == 0)
				request.setAttribute("ActionMode", HTMLEncoder.encodeHtml("InitLoad"));		
			psForm.setActionMode(HTMLEncoder.encodeHtml("FindPatient"));
			psForm.setPageTitle("Find Patient",request);
			
	        psForm.setUserCreateList(getusers(secObj));
				     
			boolean permissionLab = secObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT,NBSOperationLookup.VIEW);
    		boolean permissionMorb = secObj.getPermission(NBSBOLookup.OBSERVATIONMORBIDITYREPORT,NBSOperationLookup.VIEW);
    		boolean permissionCase = secObj.getPermission(NBSBOLookup.DOCUMENT,NBSOperationLookup.VIEW);
    		boolean permissionInvestigation = secObj.getPermission(NBSBOLookup.INVESTIGATION,NBSOperationLookup.VIEW);
    		
    		psForm.getAttributeMap().put("permissionLab",permissionLab);
    		psForm.getAttributeMap().put("permissionMorb",permissionMorb);
    		psForm.getAttributeMap().put("permissionCase",permissionCase);
    		psForm.getAttributeMap().put("permissionInvestigation",permissionInvestigation);
    		psForm.getAttributeMap().put("showLabReportOption",labRows == 0?false:true);
    		 
    		 //permission of Program areas. 
    		
    		String programAreasLab = getSecurityProgramAreas(secObj, NBSBOLookup.OBSERVATIONLABREPORT);
		     psForm.setProgramAreasLab(programAreasLab);
		     String programAreasMorb = getSecurityProgramAreas(secObj, NBSBOLookup.OBSERVATIONMORBIDITYREPORT);
		     psForm.setProgramAreasMorb(programAreasMorb);
		     String programAreasCase = getSecurityProgramAreas(secObj, NBSBOLookup.DOCUMENT);
		     psForm.setProgramAreasCase(programAreasCase);
		     String programAreasInvestigation = getSecurityProgramAreas(secObj, NBSBOLookup.INVESTIGATION);
		     psForm.setProgramAreasInvestigation(programAreasInvestigation);
		     
		     
		     psForm.setJurisdictions(getSecurityJurisdiction(secObj,NBSBOLookup.INVESTIGATION));
		    
		     psForm.setConditionL(chooseInvestigation(programAreasInvestigation));	
		   
		     request.setAttribute("PageTitle","Find Patient");

		} else if (contextAction.equalsIgnoreCase("RefineSearch")) {
			
			String mode = request.getParameter("Mode1");
			String pageTitle = (String)request.getAttribute("PageTitle");
			if(pageTitle==null || pageTitle.isEmpty())
					request.setAttribute("PageTitle","Find Patient");
			
			TreeMap<Object,Object> tm = NBSContext.getPageContext(session, "PS089", contextAction);

			String sCurrTask = NBSContext.getCurrentTask(session);
			if(mode!=null)
				request.setAttribute("formHref", "/nbs/" + sCurrTask + ".do?Mode1="+mode);
			else
			request.setAttribute("formHref", "/nbs/" + sCurrTask + ".do");
			request.setAttribute("PersonName", PersonName);
			request.setAttribute("ContextAction", tm.get("Submit"));

			boolean bStatusCheckbox = secObj.getPermission(NBSBOLookup.PATIENT, NBSOperationLookup.FINDINACTIVE);
			request.setAttribute("sec-status", String.valueOf(bStatusCheckbox));
			request.setAttribute("DSSearchCriteria", NBSContext.retrieve(session, "DSSearchCriteria"));
			boolean eventTab = isDataEnteredInEventTab((PatientSearchVO) (NBSContext.retrieve(session,
					"DSSearchCriteria")));
			
			PersonSearchForm psForm = (PersonSearchForm)form;
			PatientSearchVO psVO = (PatientSearchVO) (NBSContext.retrieve(session,"DSSearchCriteria"));
		    psVO.setLocalID(psVO.getOldLocalID());
		    psVO.setOldLocalID(new String());
			psForm.setPersonSearch(psVO);
			
			request.setAttribute("LNameOp", HTMLEncoder.encodeHtml(psVO.getLastNameOperator()));
			request.setAttribute("FNameOp", HTMLEncoder.encodeHtml(psVO.getFirstNameOperator()));
			request.setAttribute("DOBOp", HTMLEncoder.encodeHtml(psVO.getBirthTimeOperator()));
			request.setAttribute("StrAddOp", HTMLEncoder.encodeHtml(psVO.getStreetAddr1Operator()));
			request.setAttribute("CityOp", HTMLEncoder.encodeHtml(psVO.getCityDescTxtOperator()));
			request.setAttribute("EIDOp", HTMLEncoder.encodeHtml(psVO.getDocOperator()));
			request.setAttribute("DateOp", HTMLEncoder.encodeHtml(psVO.getDateOperator()));
			request.setAttribute("TestOP", HTMLEncoder.encodeHtml(psVO.getResultedTestCodeDropdown()));
			request.setAttribute("CodedOp", HTMLEncoder.encodeHtml(psVO.getCodeResultOrganismDropdown()));
			int labRows=PropertyUtil.getInstance().getLabNumberOfRows();
			//request.setAttribute("showLabReportOption", labRows == 0?"false":"true");
			
			
		//	psVO.getR
			boolean permissionLab = secObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT,NBSOperationLookup.VIEW);
    		boolean permissionMorb = secObj.getPermission(NBSBOLookup.OBSERVATIONMORBIDITYREPORT,NBSOperationLookup.VIEW);
    		boolean permissionCase = secObj.getPermission(NBSBOLookup.DOCUMENT,NBSOperationLookup.VIEW);
    		boolean permissionInvestigation = secObj.getPermission(NBSBOLookup.INVESTIGATION,NBSOperationLookup.VIEW);
    		
    		psForm.getAttributeMap().put("permissionLab",permissionLab);
    		psForm.getAttributeMap().put("permissionMorb",permissionMorb);
    		psForm.getAttributeMap().put("permissionCase",permissionCase);
    		psForm.getAttributeMap().put("permissionInvestigation",permissionInvestigation);
    		psForm.getAttributeMap().put("showLabReportOption",labRows == 0?false:true);
    				//permission of Program areas. 
    		request.setAttribute("EventTypeCd", psVO.getReportType());
    		String programAreasLab = getSecurityProgramAreas(secObj, NBSBOLookup.OBSERVATIONLABREPORT);
		     psForm.setProgramAreasLab(programAreasLab);
		     String programAreasMorb = getSecurityProgramAreas(secObj, NBSBOLookup.OBSERVATIONMORBIDITYREPORT);
		     psForm.setProgramAreasMorb(programAreasMorb);
		     String programAreasCase = getSecurityProgramAreas(secObj, NBSBOLookup.DOCUMENT);
		     psForm.setProgramAreasCase(programAreasCase);
		     String programAreasInvestigation = getSecurityProgramAreas(secObj, NBSBOLookup.INVESTIGATION);
		     psForm.setProgramAreasInvestigation(programAreasInvestigation);
		     
		     psForm.setUserCreateList(getusers(secObj));
		     psForm.setJurisdictions(getSecurityJurisdiction(secObj,NBSBOLookup.INVESTIGATION));
		     psForm.setConditionL(chooseInvestigation(programAreasInvestigation));
			if (eventTab) {
				request.setAttribute("DSFileTab", "2");
			} else {
				request.setAttribute("DSFileTab", "1");
			}
		} else if (contextAction.equalsIgnoreCase("EntitySearch")) {

			request.setAttribute("formHref", "/nbs/FindPatient6.do");
			request.setAttribute("ContextAction", "EntitySearch");
			request.setAttribute("roleSecurity", String.valueOf(secObj.getPermission(NBSBOLookup.PATIENT,
					NBSOperationLookup.ROLEADMINISTRATION)));


			request.setAttribute("PersonName", PersonName);

			request.setAttribute("VOLookup", (request.getParameter("VO") == null ? "" : (String) request
					.getParameter("VO")));

			String mode = request.getParameter("mode");

			if (mode == null)
				request.setAttribute("DSSearchCriteria", session.getAttribute("SearchCriteria"));

		}
		if (propertyUtil.isEventBasedSearchDisabled()) {
			return mapping.findForward("XSP1");
		} else {
			return mapping.findForward("XSP");
		}
	}

	public String getusers(NBSSecurityObj secObj) {
		TreeMap<String,String> treeMap = new TreeMap<>();
		Collection<Object> secureUSerDTColl = new ArrayList<Object>();

		
		UserProfile userProfile = secObj.getTheUserProfile();
		Collection<Object> roles = userProfile.getTheRealizedRoleCollection();
		String programAreas="";
		
	    for(Iterator<Object> it = roles.iterator(); it.hasNext(); )
	    {
	    	RealizedRole realizedRole = (RealizedRole)it.next();
	    	programAreas+="'"+realizedRole.getProgramAreaCode()+"'";
	    	if(it.hasNext())
	    		programAreas+=",";
	    }
			DbAuthDAOImpl secureDAOImpl = new DbAuthDAOImpl();
			secureUSerDTColl = secureDAOImpl.getSecureUserDTListBasedOnProgramArea(programAreas);
			
			 if(secureUSerDTColl!=null && secureUSerDTColl.size()>0)
			  {
			  	Iterator ite = secureUSerDTColl.iterator();
			  	while(ite.hasNext()){
	    			AuthUserDT secureUserDT = (AuthUserDT)ite.next();
	    			User user = new User();
	    			user.setUserID(secureUserDT.getUserId());
	    			if(secureUserDT.getNedssEntryId() != null)
	    				user.setEntryID(String.valueOf(secureUserDT.getNedssEntryId()));
	    			//user.setComments(secureUserDT.getUserComments());
	    			user.setFirstName(secureUserDT.getUserFirstNm());
	    			user.setLastName(secureUserDT.getUserLastNm());
	    			user.setUserID(secureUserDT.getUserId());
	    		
		      	
		      	 String key = user.getUserID();
		      	 String value = user.getLastName() +", " + user.getFirstName();
		      	 treeMap.put(key, value);
		      }
		    }
		  StringBuffer cachedUserListBuffer = new StringBuffer("");
		     StringBuffer other = new StringBuffer("");
		     

		
		       if (treeMap != null)
		       {
		           performCaseInsensitiveSort(treeMap,other, cachedUserListBuffer);
		
		       }

		       cachedUserListBuffer.append(other);
		       return cachedUserListBuffer.toString();
	}

	private boolean isDataEnteredInEventTab(PatientSearchVO vo) {
		if (vo.getReportType() != null && vo.getReportType().trim().length() != 0) {
			return true;
		}
		return false;
	}
	private String getSecurityProgramAreas(NBSSecurityObj secObj, String businessObjectLookupName){
		String programAreas = "";
	      StringBuffer stingBuffer = new StringBuffer();
	   
	      TreeMap<Object,Object> treeMap1 = (TreeMap<Object,Object>) secObj.getProgramAreas(businessObjectLookupName,NBSOperationLookup.VIEW);
	      
	      if (treeMap1 != null && treeMap1.size() > 0)
	        {
	            Iterator<Object> anIterator = treeMap1.keySet().iterator();
	            stingBuffer.append("(");
	            while (anIterator.hasNext())
	            {
	                String token = (String) anIterator.next();
	                String NewString = "";
	                if (token.lastIndexOf("!") < 0)
	                {
	                    NewString = token;
	                }
	                else
	                {
	                    NewString = token.substring(0, token.lastIndexOf("!"));
	                }
	                stingBuffer.append("'").append(NewString).append("',");
	            }
	            stingBuffer.replace(stingBuffer.length() - 1, stingBuffer.length(), "");
	            stingBuffer.append(")");
	          
	        programAreas = (stingBuffer).toString();
	     }
	      return programAreas;
	}	
	
public ArrayList<Object> chooseInvestigation(String programAreas)
    {		
		 ArrayList<Object>  list = new ArrayList<Object>();
    StringBuffer STDConditions=new StringBuffer("");
	  try
	  {

       CachedDropDownValues cdv = new CachedDropDownValues();
       if((programAreas != null) || (programAreas != "")){
    	   TreeMap<Object,Object> programAreaTreeMap = cdv.getActiveProgramAreaConditions(programAreas);
	     TreeMap<Object,Object> conditionTreeMap = new TreeMap<Object, Object>();

       
	    
	  //   String conditionCdDescString = null;
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
		//conditionCdDescString = WumUtil.sortTreeMap(conditionTreeMap);
		if (conditionTreeMap != null && conditionTreeMap.size() > 0) {
			//	Set<Object> key set = juris.keySet();
				TreeSet<Object> set1 = new TreeSet<Object>(new Comparator<Object>() {
		            public int compare(Object obj, Object obj1) {
		            	
		            	Object value =(Object)((Map.Entry<Object,Object>) obj).getValue().toString().toUpperCase();
		            	Object value1 =(Object)((Map.Entry<Object,Object>) obj1).getValue().toString().toUpperCase();
		                return ((Comparable<Object>) value ).compareTo(value1);
		            }
		        });
		        
		        set1.addAll(conditionTreeMap.entrySet());
		        DropDownCodeDT dDownDT = new DropDownCodeDT();
				/*dDownDT.setKey("");
				dDownDT.setValue("");*/
				list.add(dDownDT);
		        for (Iterator<Object> i = set1.iterator(); i.hasNext();) {
		            Map.Entry<?,?> entry = (Map.Entry<?,?>) i.next();
		            dDownDT = new DropDownCodeDT();
					dDownDT.setKey((String) entry.getKey());
					dDownDT.setValue((String) entry.getValue());
					list.add(dDownDT);
		        }
		}
	     }
       }
	    
	  
	  }
	  catch(Exception e)
	  {
	     
	  }
return list;

	  
    }
	private ArrayList<Object> getSecurityJurisdiction(NBSSecurityObj secObj, String businessObjectLookupName){
		
	      ArrayList<Object>  list = null;
			try {
				
					list = new ArrayList<Object> ();

						TreeMap<Object,Object> juris = (TreeMap<Object,Object>) secObj.getJurisdiction(businessObjectLookupName,NBSOperationLookup.VIEW);
						if (juris != null && juris.size() > 0) {
						//	Set<Object> key set = juris.keySet();
							TreeSet<Object> set = new TreeSet<Object>(new Comparator<Object>() {
					            public int compare(Object obj, Object obj1) {
					                return ((Comparable<Object>) ((Map.Entry<Object,Object>) obj).getValue()).compareTo(((Map.Entry<Object,Object>) obj1).getValue());
					            }
					        });
					        
					        set.addAll(juris.entrySet());
					        DropDownCodeDT dDownDT = new DropDownCodeDT();
							dDownDT.setKey(""); dDownDT.setValue("");
							list.add(dDownDT);
					        for (Iterator<Object> i = set.iterator(); i.hasNext();) {
					            Map.Entry<?,?> entry = (Map.Entry<?,?>) i.next();
					            dDownDT = new DropDownCodeDT();
								dDownDT.setKey((String) entry.getKey());
								dDownDT.setValue((String) entry.getValue());
								list.add(dDownDT);
					        }
					}
				
			} catch (Exception ex) {
				logger.error("Error while loading jurisdictions in getJurisdictionList: CachedDropDowns. ");
			}
			return list;
	}
	private void performCaseInsensitiveSort(TreeMap<?,?> treeMap,StringBuffer other, StringBuffer values)
    {
      TreeMap<Object,Object> filter = new TreeMap<Object,Object>();
     Iterator<?>  iter = treeMap.entrySet().iterator();
      while(iter.hasNext())
      {
        Map.Entry<?,?> map = (Map.Entry<?,?>) iter.next();
        String key = (String) map.getKey();
        String value = (String) map.getValue();
        	if(key!=null && 
        			(key.trim().equals(NEDSSConstants.HIV_SRT_CODE) || 
        					key.trim().equals(NEDSSConstants.AIDS_SRT_CODE))){
        		if(!PropertyUtil.getInstance().getHIVAidsDisplayInd().equals("Y")){
        	        		continue;
        	}		
        }
        if(value == null || value.trim().equals(""))
          continue;
        else
          filter.put(key,value);
      }
      Set<Object> valueSet = new TreeSet<Object>(filter.values());
      Iterator<Object> valueIter = valueSet.iterator();
      Set<Object> s = new TreeSet<Object>();
      while(valueIter.hasNext())
      {
        String value = null;
        String temp = (String)valueIter.next();
        if(temp != null)
        {
          value = temp.toUpperCase();
          s.add(value);
        }
      }

      Iterator<Object> it = s.iterator();

      while (it.hasNext()) {

        String sortedValue = (String) it.next();
       Iterator<?>   anIterator = treeMap.entrySet().iterator();

        while (anIterator.hasNext()) {

          Map.Entry<?,?> map = (Map.Entry<?,?>) anIterator.next();
          String mapEntryValue = null;
          if(map.getValue() != null)
              mapEntryValue = ((String) map.getValue()).toUpperCase();

          if(mapEntryValue != null)
          {
            if (mapEntryValue.equals(sortedValue)) {

              if ( ( (String) map.getValue()).equalsIgnoreCase("Other")) {
                String key = (String) map.getKey();
                String value = (String) map.getValue();
                other.append(key.trim()).append(NEDSSConstants.SRT_PART).append(
                    value.trim())
                    .append(NEDSSConstants.SRT_LINE);

              }
              else {

                String key = (String) map.getKey();
                String value = (String) map.getValue();
                values.append(key.trim()).append(NEDSSConstants.SRT_PART).
                    append(value.trim())
                    .append(NEDSSConstants.SRT_LINE);
              }
              break;
            }
          }
        }
      }


    }
	
}