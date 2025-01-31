package gov.cdc.nedss.webapp.nbs.action.homepage;

import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.action.homepage.util.HomePageConstants;
import gov.cdc.nedss.webapp.nbs.action.homepage.util.HomePageUtil;
import gov.cdc.nedss.webapp.nbs.action.localfields.util.LocalFieldGenerator;
import gov.cdc.nedss.webapp.nbs.action.pam.PamClientVO.PamClientVO;
import gov.cdc.nedss.webapp.nbs.form.homepage.HomePageForm;





import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

/**
 * Struts Action Class for the new NBS HomePage with Dashboard Widgets - Gives a snapshot view of NBS Components
 * @author Narendra Mallela
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: Computer Sciences Corporation</p>
 * HomePageAction.java
 * Sep 11, 2009
 * @version
 */
public class HomePageAction extends DispatchAction {
	private static PropertyUtil propertyUtil= PropertyUtil.getInstance();
	static final LogUtils logger = new LogUtils(HomePageAction.class.getName());
    public HomePageAction()
    {
    }

    /**
     * 
     * @param mapping
     * @param aForm
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @throws ServletException
     */
    public ActionForward loadHomePage(ActionMapping mapping, ActionForm aForm, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	HomePageForm form = (HomePageForm) aForm;
    	
    	form.clearSelections();
    	//Prepopulate Demographic attributes
    	form.getPatientSearchVO().setLastNameOperator("SW");
    	form.getPatientSearchVO().setFirstNameOperator("SW");
    	form.getPatientSearchVO().setBirthTimeOperator("=");  
    	form.getPatientSearchVO().setPatientIDOperator("IN");
    	form.getPatientSearchVO().setActive(true);
    	//Load HomePage LDFs (Sets it to request)
    	try {
			LocalFieldGenerator.makeLdfHtml(NEDSSConstants.HOME_PAGE_LDF, NEDSSConstants.VIEW_LOAD_ACTION, new TreeMap<Object, Object>(), new PamClientVO(), request,"");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while loading HomePage LDFs: " + e.toString());
		}
    	try{
    		 if(request.getSession().getAttribute("observationForm")!=null){
    			 request.getSession().removeAttribute("observationForm");   	               	
    		 }
    		 if(request.getSession().getAttribute("custom_queue_in_session")!=null){
    			 request.getSession().removeAttribute("custom_queue_in_session"); 
    		 }
    	   	 request.getSession().setAttribute("msgBlock", "");
        	 request.getSession().setAttribute("errorBlock", "");
    	}catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while Cleaning up the session objects: " + e);
		}

		//Load MyReports map
    	try {
    		TreeMap<Object, Object> myReportsMap = (TreeMap<Object, Object>) HomePageUtil.getMyReports(request);
    		if(myReportsMap!=null && myReportsMap.size()>0){
	    		Set<Object> keys = myReportsMap.keySet();
	    		Iterator<Object> keysIter = keys.iterator();
	    		Collection<Object>  privateReports = new ArrayList<Object> ();
	    		while (keysIter.hasNext()) {
	    			privateReports.addAll((Collection<Object>) myReportsMap.get(keysIter.next()));
	    		}
	  			form.setReportCollection(privateReports);
    		}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while loading HomePage MyReports map: " + e.toString());
		}
		
		//Load HomePage Queues
    	Collection<Object>  homePageQueues = null;
    	ArrayList<Object>  defaultQueues =  new ArrayList<Object>();
    	ArrayList<Object>  privateQueues =  new ArrayList<Object>();
    	ArrayList<Object>  publicQueues = new ArrayList<Object>();
    	
		try {
			homePageQueues = HomePageUtil.getShortcutsByNBSSecurity(request);
			
			Iterator<Object> iterQueues = homePageQueues.iterator();
			
			String typeQueue ="default";//private or public
			
			//The following loop will separate each queue in: default, private and public.
			while(iterQueues.hasNext()){
				
				String queue = (String)iterQueues.next();
				if(queue!=null && queue.equals("--Private__Custom__Queues--")){
					typeQueue="private";//changing to the type of private custom queues
					continue;
				}
				else
					if(queue!=null && queue.equals("--Public__Custom__Queues--")){
						typeQueue="public";//changing to the type of public custom queues
						continue;
					}
					
				if(typeQueue!=null && typeQueue.equalsIgnoreCase("default"))
					defaultQueues.add(queue);
				else
					if(typeQueue!=null && typeQueue.equalsIgnoreCase("private"))
						privateQueues.add(queue);
					else
						if(typeQueue!=null && typeQueue.equalsIgnoreCase("public"))
							publicQueues.add(queue);	
			}
			
			form.setQueueCollection(defaultQueues);
			
			//If the user has permissions to view investigations, then the private and public custom queues will show:
			

		    // get security object
        	NBSSecurityObj secObj = (NBSSecurityObj) request.getSession().getAttribute("NBSSecurityObject");
    		boolean permissionInvestigation = secObj.getPermission(NBSBOLookup.INVESTIGATION,NBSOperationLookup.VIEW);
			
    		if(permissionInvestigation){
				form.setQueueCollectionCustomPrivate(privateQueues);
				form.setQueueCollectionCustomPublic(publicQueues);
    		}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while loading HomePage Queues: " + e.toString());
		}
		
		// Load the list of RSS links defined by NEDSS and states in the NEDSS.properties file
		if (PropertyUtil.getInstance().getHomePageRSSFeedLinks() != null) {
			form.setFeedsCollection((ArrayList<Object> ) PropertyUtil.getInstance().getHomePageRSSFeedLinks());
		}
		
		// Get the title for LDF dashlet from properties file
		if (PropertyUtil.getInstance().getHomePageLDFDashletTitle() != null) {
			String title = PropertyUtil.getInstance().getHomePageLDFDashletTitle();
			request.setAttribute("ldfDashletTitle", title);
		}
		
		// get the dashlets order from properties file
		if (PropertyUtil.getInstance().getHomePageDashletOrder() != null) {
			Map<String, Collection<String>> orderMap = PropertyUtil.getInstance().getHomePageDashletOrder();
			request.setAttribute("dashletOrderMap", orderMap);
		}
		//load Charts
		HomePageUtil.loadAvailableChartList(form, request);	
		String relNum = PropertyUtil.getInstance().getRelNum();
		
		
		try{
			TreeMap<Object, Object> myReportsMap = (TreeMap<Object, Object>) HomePageUtil.getMyReports(request);
		}catch(Exception e){
			
		}
		
		// set the page title	
    	request.setAttribute(HomePageConstants.PAGE_TITLE ,relNum+" "+HomePageConstants.TITLE);
    	
    	String isTest = PropertyUtil.getInstance().getTEST_ENV_FLAG();
		request.getSession().setAttribute ("isTest", isTest);
		request.getSession().removeAttribute("personSearchForm");
        return mapping.findForward("default");
    }	
    
    /**
     * To Preview HomePage Specific LDFs
     * @param mapping
     * @param aForm
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @throws ServletException
     */
    public ActionForward previewLDF(ActionMapping mapping, ActionForm aForm, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	
		try {
			HomePageForm form = (HomePageForm) aForm;
			form.setActionMode("Preview");
			form.setPageTitle("Preview Homepage LDFs", request);			
			request.setAttribute("formCode", NEDSSConstants.HOME_PAGE_LDF);
			LocalFieldGenerator.makeLdfHtml(NEDSSConstants.HOME_PAGE_LDF, "Preview", new TreeMap<Object, Object>(), new PamClientVO(), request,"");
			
			//Set the URL for the Return to Link
			HttpSession session = request.getSession();
			String page = session.getAttribute("page") == null ? "" : (String)session.getAttribute("page");
			String pageId = session.getAttribute("PageID") == null ? "" : (String)session.getAttribute("PageID");
		    String boName = session.getAttribute("businessObjectNm") == null ? "" : (String) session.getAttribute("businessObjectNm");
		    String condCd = session.getAttribute("conditionCd") == null ? "" : (String) session.getAttribute("conditionCd");
			form.getAttributeMap().put("backToManage", "/nbs/LDFLoad.do?page=" + page + "&PageID="+ pageId + "&businessObjectNm=" + boName + "&conditionCd="+ condCd);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Error while previewing LDFs: "+e.getMessage(),e);
		}    	
        return mapping.findForward("preview");
    }
    
    public ActionForward patientSearchSubmit(ActionMapping mapping, ActionForm aForm, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	HomePageForm form = (HomePageForm) aForm;
    	TreeMap<Object, Object> tm = NBSContext.getPageContext(request.getSession(), "PS089", "GlobalPatient");
    	return mapping.findForward("patientSearch");
    }
    
    

}
