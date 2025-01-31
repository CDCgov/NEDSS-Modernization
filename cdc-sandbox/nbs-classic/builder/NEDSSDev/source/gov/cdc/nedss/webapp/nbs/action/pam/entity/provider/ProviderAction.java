package gov.cdc.nedss.webapp.nbs.action.pam.entity.provider;

import gov.cdc.nedss.entity.entityid.dt.EntityIdDT;
import gov.cdc.nedss.entity.person.dt.PersonNameDT;
import gov.cdc.nedss.entity.person.util.DisplayPersonList;
import gov.cdc.nedss.entity.person.vo.PersonSrchResultVO;
import gov.cdc.nedss.entity.person.vo.ProviderSearchResultsVO;
import gov.cdc.nedss.entity.person.vo.ProviderSearchVO;
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
import gov.cdc.nedss.webapp.nbs.form.provider.ProviderSearchForm;
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


public class ProviderAction extends DispatchAction
{
	static final LogUtils logger = new LogUtils(ProviderAction.class.getName());
	private static CachedDropDownValues srtValues = new CachedDropDownValues();
    private static TreeMap<Object,Object> treemap = srtValues.getStateCodes2("USA");

	
	public ActionForward searchLoad(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		
		  ProviderSearchForm psForm = (ProviderSearchForm) form;
		  psForm.reset();
		  //psForm.getProviderSearch().setLastNameOperator(PropertyUtil.getInstance().getPersonSearchNameOperatorDefault());
		  psForm.getProviderSearch().setLastNameOperator(PropertyUtil.getInstance().getPersonSearchNameOperatorDefault());
		  psForm.getProviderSearch().setFirstNameOperator(PropertyUtil.getInstance().getPersonSearchNameOperatorDefault());
		  psForm.getProviderSearch().setStreetAddr1Operator("CT");
		  psForm.getProviderSearch().setCityDescTxtOperator("CT");		
		  String identifier = request.getParameter("identifier");
		  request.setAttribute("identifier", HTMLEncoder.encodeHtml(identifier));
		  return (mapping.findForward("searchProvider"));
	}
	public ActionForward searchSubmit(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		  
        ArrayList<?> personList = new ArrayList<Object> ();
        ProviderSearchForm psForm = (ProviderSearchForm)form;
        ProviderSearchVO psVO = (ProviderSearchVO)psForm.getProviderSearch();
        String identifier = HTMLEncoder.encodeHtml(request.getParameter("identifier"));
        request.setAttribute("identifier", HTMLEncoder.encodeHtml(request.getParameter("identifier")));
        psVO.setActive(true);
        psVO.setStatusCodeActive("ACTIVE");
        if (psVO != null)
        {
            MainSessionCommand msCommand = null;
            String sBeanJndiName = "";
            String sMethod = "";
            Object[] oParams = null;
            sBeanJndiName = JNDINames.ENTITY_PROXY_EJB;
            sMethod = "findProvider";
            oParams = new Object[]
            {
                psVO, new Integer(PropertyUtil.getInstance().getNumberOfRows()),
                new Integer(0)
            };
            try
            {
                MainSessionHolder holder = new MainSessionHolder();
                msCommand = holder.getMainSessionCommand(request.getSession());
                ArrayList<?> arrList = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
                personList = (ArrayList<?> )arrList.get(0);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }       
            
            DisplayPersonList  list = (DisplayPersonList)personList.get(0);
            ArrayList<Object> personSrchResultVOList = list.getList();
            int totalCount = list.getTotalCounts();
            request.setAttribute("ResultsCount", HTMLEncoder.encodeHtml(String.valueOf(totalCount)));
            String scString = this.buildSearchCriteriaString(psVO);
            request.getSession().setAttribute("psVO", psVO);
            request.setAttribute("SearchCriteria", scString);
            HttpSession session = request.getSession(false);
    		NBSSecurityObj secObj = (NBSSecurityObj)session.getAttribute(
    				"NBSSecurityObject");
					
					boolean bAddButton = secObj.getPermission(NBSBOLookup.PROVIDER,
    						NBSOperationLookup.MANAGE);
            if(!bAddButton || identifier.equalsIgnoreCase("INV208") || identifier.equalsIgnoreCase("INV209") || identifier.equalsIgnoreCase("INV210"))
            	 request.setAttribute("addButton", "display:none");
            else
            	request.setAttribute("addButton", "display:''");
            request.getSession().setAttribute("ProviderSrchResults", personSrchResultVOList);
            request.setAttribute("providersList", makeProviderResults(personSrchResultVOList,identifier));
            request.setAttribute("RefineSearchLink", "Provider.do?method=refineSearch&identifier="+identifier);
            request.setAttribute("NewSearchLink", "Provider.do?method=searchLoad&identifier="+identifier);
           
        }
        
        return (mapping.findForward("searchResultsProvider"));
	}

	public ActionForward refineSearch(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		String identifier = HTMLEncoder.encodeHtml(request.getParameter("identifier"));
		request.setAttribute("identifier", identifier);
		return (mapping.findForward("searchProvider"));
	}
	
	public ActionForward loadProvider(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		
		return (mapping.findForward("loadResults"));
	}
	
	
		
	private ArrayList<Object> makeProviderResults(ArrayList<Object> results, String id) {
		
		ArrayList<Object> returnList = new ArrayList<Object> ();
		StringBuffer sb = null;
		
	  	Iterator<Object> mainIter = results.iterator();
	  	
	  	while(mainIter.hasNext()) {
	  		
	  		sb = new StringBuffer("");
	  		ProviderSearchResultsVO displayVO = new ProviderSearchResultsVO();
	  		
	  		PersonSrchResultVO resultsVO = (PersonSrchResultVO) mainIter.next();
	  		//set PersonUID
	  		displayVO.setPersonUID(resultsVO.getPersonUID());
	  		
	  		Collection<Object>  namesColl = resultsVO.getPersonNameColl();
	  		Collection<Object>  locatorsColl = resultsVO.getPersonLocatorsColl();
	  		Collection<Object>  idColl = resultsVO.getPersonIdColl();
	  		
	  		Iterator<Object> namesIter = namesColl.iterator();
	  		sb.append("<table role=\"presentation\">");
	  		while(namesIter.hasNext()) {
	  			PersonNameDT name = (PersonNameDT) namesIter.next();
	  			if (name.getNmUseCd() != null && name.getNmUseCd().trim().equals("Legal")){

	  				String useCd = name.getNmUseCd();
		  			String lastNm = name.getLastNm();
		  			String firstNm = name.getFirstNm();
		  			if(useCd != null && useCd.trim().length() > 0)
		  				sb.append("<tr><td><i>").append(useCd).append("</i></td></tr>");
		  			if(lastNm != null && lastNm.trim().length() > 0)
		  				sb.append("<tr><td>").append(lastNm);
		  			if((lastNm != null && lastNm.trim().length() > 0) && (firstNm != null && firstNm.trim().length() > 0))
			  			sb.append(",").append("</td></tr>");
		  			if(firstNm != null && firstNm.trim().length() > 0)
		  				sb.append("<tr><td>").append(firstNm).append("</td></tr>");	  				
	  			}
	  		}
	  		sb.append("</table>");
	  		//Full Name
	  		displayVO.setFullName(sb.toString());
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
		  	Iterator<Object> idsIter = idColl.iterator();
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
			ProviderSearchResultsVO  psrVO = (ProviderSearchResultsVO) ite.next();			
			String url = "<a href=\"javascript:populateInvestigator('" + psrVO.getPersonUID().toString() + "','" + id + "');\">Select</a>"; 
			psrVO.setActionLink(url);
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





