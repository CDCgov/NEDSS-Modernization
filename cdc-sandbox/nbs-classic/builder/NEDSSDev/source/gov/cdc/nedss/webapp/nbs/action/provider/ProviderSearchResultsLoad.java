package gov.cdc.nedss.webapp.nbs.action.provider;


import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.entity.person.vo.*;

import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;



import java.io.*;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;


/**
 * This class is for preparing the Person Search Results page
 * for display of search results.
 */
public class ProviderSearchResultsLoad
    extends Action
{
    /**
     * This is the constructor for the PersonSearchResultsLoad
     * class
     */
    public ProviderSearchResultsLoad()
    {
    }

    /**
     * This method is controls the execution of the
     * PersonSearchResultsLoad logic, and dictates
     * the navigation.
     *
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @exception IOException
     * @exception ServletException
     * @return ActionForward
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form,
				 HttpServletRequest request,
				 HttpServletResponse response)
			  throws IOException, ServletException
    {


	HttpSession session = request.getSession(false);
	NBSSecurityObj secObj = (NBSSecurityObj)session.getAttribute(
					"NBSSecurityObject");
	String contextAction = request.getParameter("ContextAction");

	if (contextAction == null)
	    contextAction = (String)request.getAttribute("ContextAction");
         request.setAttribute("PersonName",session.getAttribute("PersonName"));


	/***************************************************
	   * SUBMIT ACTION
	   */
	if (contextAction.equalsIgnoreCase("Submit") ||
	    contextAction.equalsIgnoreCase("Next") ||
	    contextAction.equalsIgnoreCase("Prev") ||
	    contextAction.equalsIgnoreCase("ReturnToSearchResults") ||
	    contextAction.equalsIgnoreCase("Cancel"))
	{

	    TreeMap<Object,Object> tm = NBSContext.getPageContext(session, "PS197",  contextAction);
	    String sCurrTask = NBSContext.getCurrentTask(session);
	    NBSContext.lookInsideTreeMap(tm);

	     // add button security
	    boolean bAddButton = secObj.getPermission(NBSBOLookup.PROVIDER,"MANAGE");
		 request.setAttribute("addButton", String.valueOf(bAddButton));

	    if(secObj.getPermission(NBSBOLookup.PROVIDER, "MANAGE"))
	    {
		 request.setAttribute("addButtonHref", "/nbs/" + sCurrTask + ".do?ContextAction=" + tm.get("Add"));
	    }
	    request.setAttribute("refineSearchHref", "/nbs/" + sCurrTask + ".do?ContextAction=" + tm.get("RefineSearch"));
	    request.setAttribute("newSearchHref", "/nbs/" + sCurrTask +	 ".do?ContextAction=" + tm.get("NewSearch"));
	    request.setAttribute("viewHref", "/nbs/" + sCurrTask + ".do?ContextAction=" + tm.get("View"));
	    request.setAttribute("nextHref", "/nbs/" + sCurrTask + ".do?ContextAction=" + tm.get("Next"));
	    request.setAttribute("prevHref", "/nbs/" + sCurrTask + ".do?ContextAction=" + tm.get("Prev"));


	    try
	    {
		request.setAttribute("DSFromIndex",    NBSContext.retrieve(session, "DSFromIndex"));
	    }
	    catch (Exception e)
	    {

		// OK
	    }

	    request.setAttribute("DSSearchResults", NBSContext.retrieve(session, "DSSearchResults"));


                String strCurrentIndex = (String)request.getParameter("currentIndex");

	}
	/*********************************************************
	 * ENTITY SEARCH
	 */
	else if (contextAction.equalsIgnoreCase("EntitySearch"))
	{
	    request.setAttribute("refineSearchHref","/nbs/LoadFindProvider3.do?ContextAction=EntitySearch");
	    request.setAttribute("newSearchHref","/nbs/LoadFindProvider3.do?ContextAction=EntitySearch&mode=new");
	    request.setAttribute("nextHref", "/nbs/FindProvider3.do?ContextAction=EntitySearch&mode=Next");
            request.setAttribute("prevHref", "/nbs/FindProvider3.do?ContextAction=EntitySearch&mode=Next");
	    request.setAttribute("DSSearchResults", NBSContext.retrieve(session, "DSSearchResults"));
	}
        try {

          ProviderSearchVO psVO  = (ProviderSearchVO) NBSContext.retrieve(session,
              "DSSearchCriteria");
          String scString = this.buildSearchCriteriaString(psVO);
          request.setAttribute("DSSearchCriteriaString", scString);
        }
        catch (Exception e) {
          session.setAttribute("error",
                               "DSSearchCriteria not available in Object Store");
          e.printStackTrace();
        }



	return mapping.findForward("XSP");
    }
private String buildSearchCriteriaString(ProviderSearchVO psVO) {
    //  build the criteria string
StringBuffer sQuery = new StringBuffer("");
CachedDropDownValues cache = new CachedDropDownValues();

if (psVO.getLastName() != null &&
    !psVO.getLastName().equals(""))
    sQuery.append("Last Name").append(
            " " +
            cache.getDescForCode("SEARCH_SNDX",
                                 psVO.getLastNameOperator()) +
            " ").append("'" + psVO.getLastName() + "'").append(
            ", ");

if (psVO.getFirstName() != null &&
    !psVO.getFirstName().equals(""))
    sQuery.append("First Name").append(
            " " +
            cache.getDescForCode("SEARCH_SNDX",
                                 psVO.getFirstNameOperator()) +
            " ").append("'" + psVO.getFirstName() + "'").append(
            ", ");



if (psVO.getStreetAddr1() != null &&
    !psVO.getStreetAddr1().equals(""))
    sQuery.append("Street Address").append(
            " " +
            cache.getDescForCode("SEARCH_SNDX",
                                 psVO.getStreetAddr1Operator()) +
            " ").append("'" + psVO.getStreetAddr1() + "'").append(
            ", ");

if (psVO.getCityDescTxt() != null &&
    !psVO.getCityDescTxt().equals(""))
    sQuery.append("City").append(
            " " +
            cache.getDescForCode("SEARCH_SNDX",
                                 psVO.getCityDescTxtOperator()) +
            " ").append("'" + psVO.getCityDescTxt() + "'").append(
            ", ");

if (psVO.getState() != null && !psVO.getState().equals(""))
    sQuery.append("State Equal ").append(
            "'" + getStateDescTxt(psVO.getState()) + "'").append(
            ", ");

if (psVO.getZipCd() != null && !psVO.getZipCd().equals(""))
    sQuery.append("Zip Equal ").append(
            "'" + psVO.getZipCd() + "'").append(", ");

if (psVO.getLocalID() != null && !psVO.getLocalID().equals(""))
    sQuery.append("Person ID Equal ").append(
            "'" + psVO.getLocalID() + "'").append(", ");

if (psVO.getRootExtensionTxt() != null &&
    !psVO.getRootExtensionTxt().equals(""))
{

    if (psVO.getTypeCd() != null &&
        !psVO.getTypeCd().equals(""))
        sQuery.append(getIDDescTxt(psVO.getTypeCd())).append(
                " Equal ").append("'" + psVO.getRootExtensionTxt() +
                            "'").append(", ");
}

if (psVO.getPhoneNbrTxt() != null &&
    !psVO.getPhoneNbrTxt().equals(""))
    sQuery.append("Telephone Contains ").append(
               "'" + psVO.getPhoneNbrTxt() + "'").append(
            ", ");



String findRole = getRoleDescTxt(psVO.getRole(),cache.getCodedValues("RL_TYPE_PRV"));

if (psVO.getRole() != null && !psVO.getRole().equals(""))
    sQuery.append("Role Equal ").append(
            "'" +
            cache.getDescForCode("RL_TYPE_PRV", psVO.getRole()) +
            "'").append(", ");

return sQuery.toString();

}

  private String getStateDescTxt(String sStateCd)
  {

      CachedDropDownValues srtValues = new CachedDropDownValues();
      TreeMap<Object,Object> treemap = srtValues.getStateCodes2("USA");
      String desc = "";

      if (sStateCd != null && treemap.get(sStateCd) != null)
          desc = (String)treemap.get(sStateCd);

      return desc;
  }


      private String getIDDescTxt(String id)
      {



          CachedDropDownValues cache = new CachedDropDownValues();
          TreeMap<?,?> map = cache.getCodedValuesAsTreeMap("EI_TYPE_PRV");
          map = cache.reverseMap(map); // we can add another method that do not do reverse
          String desc = (String)map.get(id);

          return desc;
      }

      private String getRoleDescTxt(String role , String descTxt)
      {
           String returnStr ="";
           if (role != null){
            if(!role.equals("") && descTxt != null){
              String findRole = descTxt.substring(descTxt.indexOf(role));
              String s = findRole.substring(findRole.indexOf("$")+1);
              return s.substring(0,s.indexOf("|")+1);
            }
          }
        return returnStr;
      }

}
