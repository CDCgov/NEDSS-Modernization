package gov.cdc.nedss.webapp.nbs.action.pam.entity.organization;

import gov.cdc.nedss.entity.entityid.dt.EntityIdDT;
import gov.cdc.nedss.entity.organization.dt.OrganizationNameDT;
import gov.cdc.nedss.entity.organization.util.DisplayOrganizationList;
import gov.cdc.nedss.entity.organization.vo.OrganizationSearchVO;
import gov.cdc.nedss.entity.organization.vo.OrganizationSrchResultVO;
import gov.cdc.nedss.locator.dt.EntityLocatorParticipationDT;
import gov.cdc.nedss.locator.dt.PostalLocatorDT;
import gov.cdc.nedss.locator.dt.TeleLocatorDT;
import gov.cdc.nedss.proxy.ejb.entityproxyejb.bean.EntityProxyHome;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.HTMLEncoder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.form.organization.OrganizationSearchForm;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class OrganizationAction extends DispatchAction {
	static final LogUtils logger = new LogUtils(OrganizationAction.class.getName());
	private static CachedDropDownValues srtValues = new CachedDropDownValues();
    private static TreeMap<Object,Object> treemap = srtValues.getStateCodes2("USA");

    public ActionForward searchOrganization(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
    	OrganizationSearchForm osForm = (OrganizationSearchForm)form;
    	osForm.reset();
    	//osForm.getOrganizationSearch().setStateCd(PropertyUtil.getInstance().getOrganizationSearchNameOperatorDefault());
    	osForm.getOrganizationSearch().setNmTxtOperator(PropertyUtil.getInstance().getOrganizationSearchNameOperatorDefault());
    	osForm.getOrganizationSearch().setStreetAddr1Operator("CT");
    	osForm.getOrganizationSearch().setCityDescTxtOperator("CT");
    	String identifier = request.getParameter("identifier");
		request.setAttribute("identifier", identifier);
    	return (mapping.findForward("searchOrganization"));
	}
	public ActionForward searchSubmit(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		ArrayList<?> orgList = new ArrayList<Object> ();
        OrganizationSearchForm osForm = (OrganizationSearchForm)form;
        OrganizationSearchVO osVO = osForm.getOrganizationSearch();
        String identifier = HTMLEncoder.encodeHtml(request.getParameter("identifier"));
        request.setAttribute("identifier", identifier);
        osVO.setActive(true);
        osVO.setStatusCodeActive("ACTIVE");
        if (osVO != null)
        {
            MainSessionCommand msCommand = null;
            String sBeanJndiName = "";
            String sMethod = "";
            Object[] oParams = null;
            sBeanJndiName = JNDINames.ENTITY_PROXY_EJB;
            sMethod = "findOrganization";
            oParams = new Object[]
            {
            	osVO, new Integer(PropertyUtil.getInstance().getNumberOfRows()),
                new Integer(0)
            };
            try
            {
                MainSessionHolder holder = new MainSessionHolder();
                msCommand = holder.getMainSessionCommand(request.getSession());
                ArrayList<?> arrList = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
                orgList = (ArrayList<?> )arrList.get(0);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }


            DisplayOrganizationList  list = (DisplayOrganizationList)orgList.get(0);
            ArrayList<Object> organizationSrchResultVOList = list.getList();
            int totalCount = list.getTotalCounts();
            request.setAttribute("ResultsCount", HTMLEncoder.encodeHtml(String.valueOf(totalCount)));
            String scString = this.buildSearchCriteriaString(osVO);
            request.getSession().setAttribute("osVO", osVO);
            request.setAttribute("SearchCriteria", scString);
            
            HttpSession session = request.getSession(false);
    		NBSSecurityObj secObj = (NBSSecurityObj)session.getAttribute(
    				"NBSSecurityObject");
					
					boolean bAddButton = secObj.getPermission(NBSBOLookup.ORGANIZATION,
    						NBSOperationLookup.MANAGE);
            if(!bAddButton || identifier.equalsIgnoreCase("INV185") || identifier.equalsIgnoreCase("INV186"))
           	 request.setAttribute("addButton", "display:none");
           else
           	request.setAttribute("addButton", "display:''");
            
            request.getSession().setAttribute("OrganizationSrchResults", organizationSrchResultVOList);
            request.setAttribute("organizationList", makeOrganizationResults(organizationSrchResultVOList,identifier));
            request.setAttribute("RefineSearchLink", "PamOrganization.do?method=refineSearch&identifier="+identifier);
            request.setAttribute("NewSearchLink", "PamOrganization.do?method=searchOrganization&identifier="+identifier);

        }

		return (mapping.findForward("searchOrganizationResults"));
	}
	public ActionForward refineSearch(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		String identifier = HTMLEncoder.encodeHtml(request.getParameter("identifier"));
		request.setAttribute("identifier", identifier);
		return (mapping.findForward("searchOrganization"));
	}

	public ActionForward loadOrganization(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {

		return (mapping.findForward("loadOrganizationResults"));
	}
	private ArrayList<Object> makeOrganizationResults(ArrayList<Object> results, String id) {

		ArrayList<Object> returnList = new ArrayList<Object> ();
		StringBuffer sb = null;

	  	Iterator<Object> mainIter = results.iterator();

	  	while(mainIter.hasNext()) {

	  		sb = new StringBuffer("");
	  		OrganizationSrchResultVO displayVO = new OrganizationSrchResultVO();

	  		OrganizationSrchResultVO resultsVO = (OrganizationSrchResultVO) mainIter.next();
	  		//set OrganizationUID
	  		displayVO.setOrganizationUID((resultsVO.getOrganizationUID()));

	  		Collection<Object>  namesColl = resultsVO.getOrganizationNameColl();
	  		Collection<Object>  locatorsColl = resultsVO.getOrganizationLocatorsColl();
	  		Collection<Object>  idColl = resultsVO.getOrganizationIdColl();

	  		Iterator<Object> orgNameIt = namesColl.iterator();
	  		sb.append("<table role=\"presentation\">");
	  		while(orgNameIt.hasNext()) {
	  			OrganizationNameDT orgName = (OrganizationNameDT) orgNameIt.next();
	  				if(orgName.getNmTxt() != null && orgName.getNmTxt().trim().length() > 0){
		  				sb.append("<tr><td>").append(orgName.getNmTxt()).append("</td></tr>");
	  			}
	  		}
	  		sb.append("</table>");
	  		// Name
	  		displayVO.setName(sb.toString());
	  		sb = new StringBuffer("<table role=\"presentation\">");
	  		StringBuffer sb1 = new StringBuffer("<table role=\"presentation\">");
		  	Iterator<Object> locatorIter = locatorsColl.iterator();
		  	while(locatorIter.hasNext()) {
		  		EntityLocatorParticipationDT elpDT = (EntityLocatorParticipationDT) locatorIter.next();
		  		//Postal Locator
		  		if(elpDT.getClassCd() != null && elpDT.getClassCd().equalsIgnoreCase("PST")) {
		  			String useCd = elpDT.getUseCd();
		  			if(useCd != null && useCd.trim().length() > 0)
		  				sb.append("<tr><td><i>").append(useCd).append("</i></td></tr>");
		  			PostalLocatorDT postal = elpDT.getThePostalLocatorDT();
		  			if (postal != null && (postal.getStreetAddr1() != null || postal.getCityCd() != null || postal.getStateCd() != null || postal.getZipCd() != null)) {

		  				String streetAdd1 = postal.getStreetAddr1();
			  			String streetAdd2 = postal.getStreetAddr2();
			  			String cityCd = postal.getCityCd();
			  			String stateDesc = getStateDescTxt(postal.getStateCd());
			  			String zipCd = postal.getZipCd();
			  			if(streetAdd1 != null && streetAdd1.trim().length() > 0)
			  				sb.append("<tr><td>").append(streetAdd1).append("</td></tr>");
			  			if(streetAdd2!= null && streetAdd2.trim().length() > 0)
			  				sb.append("<tr><td>").append(streetAdd2).append("</td></tr>");
			  			sb.append("<tr><td>");
			  			if(cityCd != null && cityCd.trim().length() > 0)
			  				sb.append(cityCd).append(", ");
			  			if(stateDesc != null && stateDesc.trim().length() > 0)
			  				sb.append(stateDesc).append(" ");
			  			if(zipCd != null && zipCd.trim().length() > 0)
			  				sb.append(zipCd);
			  			sb.append("</td></tr>");
		  			}
		  		}
		  		//Tele Locator
		  		if(elpDT.getClassCd() != null && elpDT.getClassCd().equalsIgnoreCase("TELE")) {
		  			TeleLocatorDT tele = elpDT.getTheTeleLocatorDT();
		  			if(elpDT.getUseCd() != null && (tele.getPhoneNbrTxt() != null && tele.getPhoneNbrTxt().trim().length() > 0)) {
			  			String useCd = elpDT.getUseCd();
			  			String phoneNbr = tele.getPhoneNbrTxt();
			  			sb1.append("<tr><td><i>").append(useCd).append("</i></td></tr>");
			  			sb1.append("<tr><td>").append(phoneNbr).append("</td></tr>");
		  			}
		  		}
		  	}
		  	sb.append("</table>");
		  	sb1.append("</table>");
		  	//Address
		  	displayVO.setAddress(sb.toString());
		  	//Telephone
		  	displayVO.setTelephone(sb1.toString());

		  	sb = new StringBuffer("<table role=\"presentation\">");
		  	Iterator idsIter = idColl.iterator();
		  	while(idsIter.hasNext()) {

		  		EntityIdDT ids  = (EntityIdDT) idsIter.next();
				if(ids != null) {
					String  typeDesc = null;
					String rootExtTxt = ids.getRootExtensionTxt();
					if (ids.getTypeCd() != null)
						typeDesc =  ids.getTypeCd();
					else
						typeDesc = "Quick Entry Code";
					if(rootExtTxt != null && rootExtTxt.trim().length() > 0) {
						sb.append("<tr><td><i>").append(typeDesc).append("</i></td></tr>");
						sb.append("<tr><td>").append(rootExtTxt).append("</td></tr>");
					}
				}
		  	}
		  	sb.append("</table>");
		  	//ID
		  	displayVO.setId(sb.toString());

		  	//add DisplayVO to the returnList
		  	returnList.add(displayVO);
	  	}
		Iterator<Object> ite = returnList.iterator();
		while (ite.hasNext()) {
			OrganizationSrchResultVO  osrVO = (OrganizationSrchResultVO) ite.next();
			String url = "<a href=\"javascript:populateOrganization('" + osrVO.getOrganizationUID().toString() + "','" + id + "');\">Select</a>";
			osrVO.setActionLink(url);
		}

		return returnList;
	}


   private String getStateDescTxt(String sStateCd) {
   		String desc= null;
      	if (treemap != null){
          if(sStateCd!=null && treemap.get(sStateCd)!=null)
          	desc = (String)treemap.get(sStateCd);
      	}
      	return desc;
    }
   private String buildSearchCriteriaString(OrganizationSearchVO osVO) {
	    //  build the criteria string
	StringBuffer sQuery = new StringBuffer("");
	CachedDropDownValues cache = new CachedDropDownValues();

	if (osVO.getNmTxt()!= null &&
	    !osVO.getNmTxt().equals(""))
	    sQuery.append("Name").append(
	            " " +
	            cache.getDescForCode("SEARCH_SNDX",
	            		osVO.getNmTxtOperator()) +
	            " ").append("'" + osVO.getNmTxt() + "'").append(
	            ", ");

	if (osVO.getStreetAddr1() != null &&
	    !osVO.getStreetAddr1().equals(""))
	    sQuery.append("Street Address").append(
	            " " +
	            cache.getDescForCode("SEARCH_SNDX",
	            		osVO.getStreetAddr1Operator()) +
	            " ").append("'" + osVO.getStreetAddr1() + "'").append(
	            ", ");

	if (osVO.getCityDescTxt() != null &&
	    !osVO.getCityDescTxt().equals(""))
	    sQuery.append("City").append(
	            " " +
	            cache.getDescForCode("SEARCH_SNDX",
	            		osVO.getCityDescTxtOperator()) +
	            " ").append("'" + osVO.getCityDescTxt() + "'").append(
	            ", ");

	if (osVO.getStateCd() != null && !osVO.getStateCd().equals(""))
	    sQuery.append("State Equal ").append(
	            "'" + getStateDescTxt(osVO.getStateCd()) + "'").append(
	            ", ");

	if (osVO.getZipCd() != null && !osVO.getZipCd().equals(""))
	    sQuery.append("Zip Equal ").append(
	            "'" + osVO.getZipCd() + "'").append(", ");

	if (osVO.getLocalID() != null && !osVO.getLocalID().equals(""))
	    sQuery.append("Organization ID Equal ").append(
	            "'" + osVO.getLocalID() + "'").append(", ");

	if (osVO.getRootExtensionTxt() != null &&
	    !osVO.getRootExtensionTxt().equals(""))
	{

	    if (osVO.getTypeCd() != null &&
	        !osVO.getTypeCd().equals(""))
	    //    sQuery.append(getIDDescTxt(osVO.getTypeCd())).append(
	          sQuery.append(osVO.getTypeCd()).append(
	                " Equal ").append("'" + osVO.getRootExtensionTxt() +
	                            "'").append(", ");
	}

	if (osVO.getPhoneNbrTxt() != null &&
	    !osVO.getPhoneNbrTxt().equals(""))
	    sQuery.append("Telephone Contains ").append(
	               "'" + osVO.getPhoneNbrTxt() + "'").append(
	            ", ");

	String findRole = getRoleDescTxt(osVO.getRoleCd(),cache.getCodedValues("RL_TYPE_PRV"));

	if (osVO.getRoleCd() != null && !osVO.getRoleCd().equals(""))
	    sQuery.append("Role Equal ").append(
	            "'" +
	            cache.getDescForCode("RL_TYPE_PRV", osVO.getRoleCd()) +
	            "'").append(", ");

	return sQuery.toString();

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
