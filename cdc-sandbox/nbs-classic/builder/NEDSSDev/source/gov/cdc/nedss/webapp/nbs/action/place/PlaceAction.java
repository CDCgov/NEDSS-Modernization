package gov.cdc.nedss.webapp.nbs.action.place;

import gov.cdc.nedss.entity.entityid.dt.EntityIdDT;
import gov.cdc.nedss.entity.place.dt.PlaceDT;
import gov.cdc.nedss.entity.place.vo.PlaceSearchVO;
import gov.cdc.nedss.entity.place.vo.PlaceVO;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.locator.dt.EntityLocatorParticipationDT;
import gov.cdc.nedss.locator.dt.PostalLocatorDT;
import gov.cdc.nedss.locator.dt.TeleLocatorDT;
import gov.cdc.nedss.proxy.util.EntityProxyHelper;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.HTMLEncoder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.StringUtils;
import gov.cdc.nedss.webapp.nbs.action.util.NBSPageConstants;
import gov.cdc.nedss.webapp.nbs.form.place.PlaceForm;
import gov.cdc.nedss.webapp.nbs.form.util.BaseForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class PlaceAction extends DispatchAction
{
    static final LogUtils         logger      = new LogUtils(PlaceAction.class.getName());
    public static final String[] SEARCH_KEYS = new String[] { "nm", "typeCd", "streetAddr1", "city", "state", "zip", "phoneNbrTxt" };
    public static final String GLOBAL_PLACE = "GlobalPlace";

    
    public ActionForward findRefine(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response)
    {
    	PlaceForm osForm = (PlaceForm) form;
        request.setAttribute("action", "refine");
        return find(mapping, form, request,response);
    }
    public ActionForward find(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response)
    {
    	
        PlaceForm osForm = (PlaceForm) form;
        if(request.getAttribute("action") == null || !request.getAttribute("action").equals("refine"))
        	osForm.reset();
        if( GLOBAL_PLACE.equalsIgnoreCase(request.getParameter("ContextAction")))  osForm.reset();
        if (request.getParameter("ContextAction")!= null)  osForm.setContextAction(request.getParameter("ContextAction"));
        
        if (request.getParameter("identifier")!= null && !request.getParameter("identifier").equals("null") && !request.getParameter("identifier").equals(""))
        	osForm.setContextAction(null);
        else
        	osForm.setContextAction(GLOBAL_PLACE);
        
        PlaceSearchVO placeSearch = osForm.getPlaceSearch();

        if (StringUtils.isEmpty(placeSearch.getNmOperator()))
        {
            placeSearch.setNmOperator(PropertyUtil.getInstance().getOrganizationSearchNameOperatorDefault());
        } 
        if (StringUtils.isEmpty(placeSearch.getStreetAddr1Operator()))
        {
            placeSearch.setStreetAddr1Operator("CT");
        }
        if (StringUtils.isEmpty(placeSearch.getCityOperator()))
        {
            placeSearch.setCityOperator("CT");
        }
        request.setAttribute(NEDSSConstants.PAGE_TITLE, "Find Place");
        return (mapping.findForward("find"));
    }

    public ActionForward show(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response)
    {
    	

        PlaceForm placeForm = (PlaceForm) form;
        //Clear the form if coming from view place to add new place.
        if ("add".equalsIgnoreCase(request.getParameter("mode")) )
        {
        	placeForm.resetWithoutsearch();
        }
        if (request.getParameter("identifier")!= null && !request.getParameter("identifier").equals("null") && !request.getParameter("identifier").equals(""))
        	placeForm.setContextAction(null);
        else
        	placeForm.setContextAction(GLOBAL_PLACE);
        if (isPlaceUidSet(placeForm, request))

        {
            PlaceVO p = PlaceUtil.getThePlaceVO(placeForm.getPlace().getPlaceUid(), request.getSession());
            placeForm.setPlace(p);
            placeForm.setQuick(p.getEntityIdDT_s(0));
            
            if ("edit".equalsIgnoreCase(request.getParameter("mode")))
            {
                request.setAttribute(NEDSSConstants.PAGE_TITLE, "Edit Place");
                placeForm.setReasonForEdit("overwrite");
            }
            else
            {
                request.setAttribute("mode", "view");
                request.setAttribute(NEDSSConstants.PAGE_TITLE, "View Place");
                return mapping.findForward("view");
            }
        }
        else
        {
            // Check Search Criteria and set the values 
            PlaceSearchVO search = placeForm.getPlaceSearch();
            PlaceVO pvo = placeForm.getPlace();
            pvo.setNm( search.getNm() );
            pvo.setCd( search.getTypeCd() ); 
            request.setAttribute(NEDSSConstants.PAGE_TITLE, "Add Place");
        }
        return (mapping.findForward("add"));
    }

    public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response)
    {
        PlaceForm placeForm = (PlaceForm) form;
        PlaceVO placeVO = placeForm.getPlace();
		String sCurrentTask = NBSContext.getCurrentTask(request.getSession());
		
        if (request.getParameter("identifier")!= null && !request.getParameter("identifier").equals("null") && !request.getParameter("identifier").equals(""))
        	placeForm.setContextAction(null);
        else
        	placeForm.setContextAction(GLOBAL_PLACE);
        
        JsonObject addJsonObject= BaseForm.toJson(placeForm.getPlaceSearch(), new String[] { "nm", "typeCd", "streetAddr1", "city", "state", "zip", "phoneNbrTxt" } );
        request.setAttribute("addJsonObject", addJsonObject);
        
        try {
			if (!EntityProxyHelper.getInstance().isQuickCodeUnique(placeVO))
			{
				ActionErrors errors = new ActionErrors();
				errors.add(NBSPageConstants.ERROR_MESSAGES_PROPERTY, new ActionMessage("errors.invalid","Quick Code" ));
				request.setAttribute("error_messages", errors);
				 return (mapping.findForward("add"));
				
			}
		} catch (NEDSSAppException e1) {
    		logger.error("Exception in PlaceAction.add: " + e1.getMessage());
    		e1.printStackTrace();
		}
        placeVO.setItNew(true);
        PlaceDT placeDt = placeVO.getThePlaceDT();
        placeVO.getThePlaceDT().setPlaceUid(new Long(-1));
        placeVO.getThePlaceDT().setItNew(true);
        placeVO.getThePlaceDT().setItDirty(false);
        placeVO.getThePlaceDT().setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
        placeVO.getThePlaceDT().setStatusCd(NEDSSConstants.STATUS_ACTIVE);
        placeVO.getThePlaceDT().setVersionCtrlNbr(new Integer(1));
        placeVO.getThePlaceDT().setSharedInd("T");

        EntityIdDT ent = placeVO.getEntityIdDT_s(0);
        
        ent.setRootExtensionTxt(placeForm.getQuick().getRootExtensionTxt());
        ent.setEntityIdSeq(new Integer(1));
        ent.setItNew(true);
        ent.setTypeCd(NEDSSConstants.ENTITY_TYPECD_QEC);
        ent.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);

        if(placeVO.getTheEntityIdDTCollection().isEmpty()){
        	placeVO.getTheEntityIdDTCollection().add(ent);
        }else{
        	ArrayList list = new ArrayList();
        	list.add(ent);
        	placeVO.setTheEntityIdDTCollection(list);
        }
        try
        {
            setEntityLocatorParticipations(placeVO, null);
            MainSessionCommand msCommand = null;
            String sBeanJndiName = JNDINames.ENTITY_PROXY_EJB;
            String sMethod = "setPlace";
            Object[] oParams = { placeVO };
            MainSessionHolder holder = new MainSessionHolder();
            msCommand = holder.getMainSessionCommand(request.getSession());
            ArrayList<?> resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
            String msg = "Place";
            ActionMessages messages = new ActionMessages();
            messages.add(NBSPageConstants.SUCCESS_MESSAGES_PROPERTY, new ActionMessage(
                    NBSPageConstants.SAVE_SUCCESS_MESSAGE_KEY, msg));
            request.setAttribute("success_messages", messages);
            Long puid = (Long)resultUIDArr.get(0);
            
            if( !GLOBAL_PLACE.equalsIgnoreCase(placeForm.getContextAction()))
            { 
                PlaceSearchVO placeSearch = new PlaceSearchVO();
                placeSearch.setPlaceUid(puid);
                List placeList = PlaceUtil.getPlaceResults(placeSearch, request.getSession());
                if( placeList != null && placeList.size() > 0)
                {
                    request.setAttribute("newPlace", placeList.get(0) );
                    request.setAttribute("placeUid", (Long)resultUIDArr.get(0));
                    request.setAttribute("placeName", ((PlaceVO)placeList.get(0)).getTypeName() );
                }
                //return (mapping.findForward("add"));
            } 
            placeForm.getPlace().setPlaceUid(puid);
        }
        catch (Exception e)
        {
            logger.error("Exception in adding Place()::", e);
    		e.printStackTrace();
        }
        if (sCurrentTask.contains("EditInvestigation") || sCurrentTask.contains("CreateInvestigation"))
        	return null;  //modal dialog ending
        		
        return show(mapping, form, request, response);
    }

    public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response)
    {
        PlaceForm placeForm = (PlaceForm) form;
        PlaceVO placeVO = placeForm.getPlace();
        if(placeForm.getReasonForEdit() != null && ! placeForm.getReasonForEdit().equals("overwrite")){
	        try {
				if (!EntityProxyHelper.getInstance().isQuickCodeUnique(placeVO))
				{
					ActionErrors errors = new ActionErrors();
					errors.add(NBSPageConstants.ERROR_MESSAGES_PROPERTY, new ActionMessage("errors.invalid","Quick Code" ));
					request.setAttribute("error_messages", errors);
					 return (mapping.findForward("add"));
				}
			} catch (NEDSSAppException e1) {
	    		logger.error("Exception in PlaceAction.update: " + e1.getMessage());
	    		e1.printStackTrace();
			}
        }
        Long puid = placeVO.getPlaceUid();
        placeVO.setItNew(false);
        placeVO.setItDirty(true);

        PlaceDT placeDt = placeVO.getThePlaceDT();
        placeDt.setItNew(false);
        placeDt.setItDirty(true);
        placeDt.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
        placeDt.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
        placeDt.setSharedInd("T");

        PlaceVO dbPlaceVO = PlaceUtil.getThePlaceVO(puid, request.getSession());

        try
        {
            placeVO.getThePlaceDT().setVersionCtrlNbr(dbPlaceVO.getThePlaceDT().getVersionCtrlNbr());
            setEntityLocatorParticipations(placeVO, dbPlaceVO.getTheEntityLocatorParticipationDTCollection());
            
            ArrayList<Object> list = (ArrayList<Object>) dbPlaceVO.getTheEntityIdDTCollection();
           ((EntityIdDT)list.get(0)).setRootExtensionTxt(placeForm.getQuick().getRootExtensionTxt());
            placeVO.setTheEntityIdDTCollection(list);
            
            MainSessionCommand msCommand = null;
            String sBeanJndiName = JNDINames.ENTITY_PROXY_EJB;
            String sMethod = "setPlace";
            Object[] oParams = { placeVO };
            MainSessionHolder holder = new MainSessionHolder();
            msCommand = holder.getMainSessionCommand(request.getSession());
            ArrayList<?> resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
            String msg = "Place";
            ActionMessages messages = new ActionMessages();
            messages.add(NBSPageConstants.SUCCESS_MESSAGES_PROPERTY, new ActionMessage(
                    NBSPageConstants.UPDATE_SUCCESS_MESSAGE_KEY, msg));
            request.setAttribute("success_messages", messages);
            placeForm.getPlace().setPlaceUid(puid);
        }
        catch (Exception e)
        {
            logger.error("Exception in adding Place()::", e);
            e.printStackTrace();
        }
        return show(mapping, form, request, response);
    }
    
    public ActionForward inactivate(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response)
    {
        PlaceForm placeForm = (PlaceForm) form;
        PlaceVO placeVO = placeForm.getPlace();
        Long puid = placeVO.getPlaceUid(); 

        placeVO = PlaceUtil.getThePlaceVO(puid, request.getSession());

        try
        { 
            placeVO.getThePlaceDT().setItNew(false);
            placeVO.getThePlaceDT().setItDirty(true);
            placeVO.getThePlaceDT().setRecordStatusCd(NEDSSConstants.RECORD_STATUS_INACTIVE);
            placeVO.getThePlaceDT().setStatusCd(NEDSSConstants.STATUS_INACTIVE);
            MainSessionCommand msCommand = null;
            String sBeanJndiName = JNDINames.ENTITY_PROXY_EJB;
            String sMethod = "inactivatePlace";
            Object[] oParams = { placeVO };
            MainSessionHolder holder = new MainSessionHolder();
            msCommand = holder.getMainSessionCommand(request.getSession());
            ArrayList<?> resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod, oParams); 
        }
        catch (Exception e)
        {
            logger.error("Exception in inactivate Place()::", e);
            e.printStackTrace();
        }
        return mapping.findForward("Inactivate") ;
    }

    public ActionForward findResults(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response)
    {
        try
        {
            PlaceForm osForm = (PlaceForm) form;
            if (request.getParameter("identifier")!= null && !request.getParameter("identifier").equals("null") && !request.getParameter("identifier").equals(""))
            	osForm.setContextAction(null);
            else
            	osForm.setContextAction(GLOBAL_PLACE);
            osForm.resetWithoutsearch();
            PlaceSearchVO placeSearch = osForm.getPlaceSearch();
            List placeList = PlaceUtil.getPlaceResults(placeSearch, request.getSession());
            request.setAttribute("placeList", placeList);
            request.setAttribute(NEDSSConstants.PAGE_TITLE, "Search Results Place");

            request.setAttribute("RefineSearch", BaseForm.toJson(placeSearch, SEARCH_KEYS).toString());
            request.setAttribute("ResultsCount", placeList.size());
            request.setAttribute("SearchCriteria", buildSearchCriteriaString(placeSearch));
        }
        catch (Exception e)
        {
            logger.error("Exception in findResults()::", e);
            e.printStackTrace();
        }
        return (mapping.findForward("findResults"));
    }
    
    
    private void setEntityLocatorParticipations(PlaceVO placeVO, Collection elps) throws Exception
    {
        Long uid = placeVO.getThePlaceDT().getPlaceUid();
        ArrayList arrELP = (ArrayList) placeVO.getTheEntityLocatorParticipationDTCollection();
        
        List ar = new ArrayList();

        for (int i = 0; arrELP != null && i < arrELP.size(); i++)
        {
            EntityLocatorParticipationDT elp = (EntityLocatorParticipationDT) arrELP.get(i);
            ar.add( markElp(elps, elp, uid) );
        }
        placeVO.setTheEntityLocatorParticipationDTCollection(ar);
    }
 
    private EntityLocatorParticipationDT markElp(Collection origElps, EntityLocatorParticipationDT elp, Long uid)
    {
        if (origElps != null)
        {
            Iterator eIter = origElps.iterator();
            while (eIter.hasNext())
            {
            	if( NEDSSConstants.POSTAL.equalsIgnoreCase(elp.getClassCd()))
                {
            		PostalLocatorDT posDt = elp.getThePostalLocatorDT();
           			if (posDt.getCntryCd() != null && posDt.getCntryCd().isEmpty()) 
        				posDt.setCntryCd(null); //gst- update field fix
            		if(posDt.getCntryCd() != null){
	            		for(Object o : CachedDropDowns.getCountryList()){
	            			if(o instanceof DropDownCodeDT){
	            				if(((DropDownCodeDT)o).getKey().equals(posDt.getCntryCd()))
	            				{
	            					posDt.setCntryDescTxt(((DropDownCodeDT)o).getValue());
	            					elp.setThePostalLocatorDT(posDt);
	            					break;
	            				}	
	            			}
	            		}
            		}
                }
            	
                EntityLocatorParticipationDT tmpElp = (EntityLocatorParticipationDT) eIter.next(); 
                if (isEqual(tmpElp.getEntityUid(), elp.getEntityUid()) && isEqual(tmpElp.getLocatorUid(), elp.getLocatorUid()))
                {
                    setProperties(tmpElp, PlaceForm.ENT_PROPS, elp);
                    tmpElp.setItDirty(true);
                    tmpElp.setItNew(false);
                    TeleLocatorDT tel = null;
                    PostalLocatorDT pos =null;
                    
                    if (NEDSSConstants.TELE.equalsIgnoreCase(elp.getClassCd()))
                    {
                        tel = elp.getTheTeleLocatorDT();
                        setProperties(tmpElp.getTheTeleLocatorDT(), PlaceForm.TEL_PROPS, tel);
                        if (tmpElp.getLocatorDescTxt() != null && tmpElp.getLocatorDescTxt().isEmpty())
                        	tmpElp.setLocatorDescTxt(null); //comments
                        fixEmptyTeleLocatorFields(tmpElp.getTheTeleLocatorDT());                        
                        tmpElp.getTheTeleLocatorDT().setItDirty(true);
                        tmpElp.getTheTeleLocatorDT().setItNew(false);
                    }
                    else if( NEDSSConstants.POSTAL.equalsIgnoreCase(elp.getClassCd()))
                    {
                        pos = elp.getThePostalLocatorDT(); 
                        setProperties(tmpElp.getThePostalLocatorDT(), PlaceForm.POSTAL_PROPS, pos);
                        if (tmpElp.getLocatorDescTxt() != null && tmpElp.getLocatorDescTxt().isEmpty())
                        	tmpElp.setLocatorDescTxt(null); //comments
                        fixEmptyPostalLocatorFields(tmpElp.getThePostalLocatorDT());
                        tmpElp.getThePostalLocatorDT().setItDirty(true);
                        tmpElp.getThePostalLocatorDT().setItNew(false);
                    }
                    
                    if( NEDSSConstants.STATUS_INACTIVE.equalsIgnoreCase(elp.getStatusCd()))
                    {
                        tmpElp.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_INACTIVE);
                        tmpElp.setStatusCd(NEDSSConstants.STATUS_INACTIVE);
                    }
                    return tmpElp;
                }
            }
        }
        Timestamp ts = new Timestamp(new Date().getTime());
       
        elp.setAddTime(ts);
        elp.setStatusTime(ts);
        elp.setItNew(true);
        elp.setItDirty(false);
        elp.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
        elp.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
        elp.setEntityUid(uid);
        return elp;
    }
    

	private void setProperties(Object obj, String[] props, Object srcObj)
    {
        try
        {
            for (int i = 0; i < props.length; i++)
            {
                BeanUtils.copyProperty(obj, props[i], BeanUtils.getProperty(srcObj, props[i]));
            }
        }
        catch(Exception e)
        {
            logger.error("Exception in setProperties()::", e);
            e.printStackTrace();
        }
    }

    /*
     * fixEmptyPostalLocatorFields - Updated postal fields that are cleared come over as empty strings.
     * 	They should be nulls. See clearEmptyAddressFields in place.js.
     */
    private void fixEmptyPostalLocatorFields(PostalLocatorDT thePostalLocatorDT) {
		if (thePostalLocatorDT.getStreetAddr1() != null && thePostalLocatorDT.getStreetAddr1().isEmpty())
			thePostalLocatorDT.setStreetAddr1(null);
		if (thePostalLocatorDT.getStreetAddr2() != null && thePostalLocatorDT.getStreetAddr2().isEmpty())
			thePostalLocatorDT.setStreetAddr2(null);
		if (thePostalLocatorDT.getCntyCd() != null && thePostalLocatorDT.getCntyCd().isEmpty())
			thePostalLocatorDT.setCntyCd(null);		
		if (thePostalLocatorDT.getCntryCd() != null && thePostalLocatorDT.getCntryCd().isEmpty())
			thePostalLocatorDT.setCntryCd(null);		
		if (thePostalLocatorDT.getCensusTract() != null && thePostalLocatorDT.getCensusTract().isEmpty())
			thePostalLocatorDT.setCensusTract(null);
		if (thePostalLocatorDT.getStateCd() != null && thePostalLocatorDT.getStateCd().isEmpty())
			thePostalLocatorDT.setStateCd(null);
		if (thePostalLocatorDT.getZipCd() != null && thePostalLocatorDT.getZipCd().isEmpty())
			thePostalLocatorDT.setZipCd(null);
		if (thePostalLocatorDT.getCityDescTxt() != null && thePostalLocatorDT.getCityDescTxt().isEmpty())
			thePostalLocatorDT.setCityDescTxt(null);		
		
	}
    
    /*
     * fixEmptyTeleLocatorFields - Updated Phone, Email, URL fields that are cleared come over as empty strings.
     * 	They should be nulls. See clearEmptyPhoneFields in place.js. 
     */
    private void fixEmptyTeleLocatorFields(TeleLocatorDT theTeleLocatorDT) {

		if (theTeleLocatorDT.getEmailAddress() != null && theTeleLocatorDT.getEmailAddress().isEmpty())
			theTeleLocatorDT.setEmailAddress(null);
		if (theTeleLocatorDT.getExtensionTxt() != null && theTeleLocatorDT.getExtensionTxt().isEmpty())
			theTeleLocatorDT.setExtensionTxt(null);
		if (theTeleLocatorDT.getProgAreaCd() != null && theTeleLocatorDT.getProgAreaCd().isEmpty())
			theTeleLocatorDT.setProgAreaCd(null);
		if (theTeleLocatorDT.getPhoneNbrTxt() != null && theTeleLocatorDT.getPhoneNbrTxt().isEmpty())
			theTeleLocatorDT.setPhoneNbrTxt(null);
		if (theTeleLocatorDT.getUrlAddress() != null && theTeleLocatorDT.getUrlAddress().isEmpty())
			theTeleLocatorDT.setUrlAddress(null);	
		if (theTeleLocatorDT.getCntryCd() != null && theTeleLocatorDT.getCntryCd().isEmpty())
			theTeleLocatorDT.setCntryCd(null);
	}
    
    
    private boolean isPlaceUidSet(PlaceForm pForm, HttpServletRequest request)
    {
        if (pForm.getPlace() != null && pForm.getPlace().getPlaceUid() != null
                && pForm.getPlace().getPlaceUid().longValue() != 0)
            return true;
        else if (request.getParameter("placeUid") != null)
        {
            pForm.getPlace().getThePlaceDT().setPlaceUid(Long.valueOf(request.getParameter("placeUid")));
            return true;
        }
        return false;
    }

    public ActionForward counties(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response)
    {
        try
        {
            PlaceForm placeForm = (PlaceForm) form;
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            JsonObject jsonObject = new JsonObject();
            ArrayList counties = placeForm.getDwrCountiesForState(placeForm.getState());
            JsonArray jar = new JsonArray();
            for (int i = 0; i < counties.size(); i++)
            {
                DropDownCodeDT dDownDT = (DropDownCodeDT) counties.get(i);

                JsonObject j = new JsonObject();
                j.addProperty("key", dDownDT.getKey());
                j.addProperty("value", dDownDT.getValue());
                jar.add(j);
            }
            jsonObject.add("results", jar);
            out.print(jsonObject);
            out.flush();
        }
        catch (Exception e)
        {

        }
        return null;
    }

    public ActionForward checkQuickEntryCode(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response)
    {
        try
        {
            JsonObject jsonObject = new JsonObject();
            PlaceForm placeForm = (PlaceForm) form;
            PlaceVO place = placeForm.getPlace();
            place.setItNew(true);
            place.setItDirty(false);
            EntityIdDT quick = placeForm.getQuick();
            String result = "unique";
            if (!StringUtils.isEmpty(quick.getRootExtensionTxt()))
            {
                quick.setTypeCd(NEDSSConstants.ENTITY_TYPECD_QEC);
                List el = new ArrayList();
                el.add(quick);
                place.setTheEntityIdDTCollection(el);

                if (!EntityProxyHelper.getInstance().isQuickCodeUnique(place))
                {
                    result = "exists";
                    if (!"GlobalPage".equalsIgnoreCase(request.getParameter(NEDSSConstants.CONTEXT_ACTION)))
                    {
                        // Get the details of the place for this quick code.
                    	
                        PlaceSearchVO ps = new PlaceSearchVO();
                        ps.setRootExtensionTxt(quick.getRootExtensionTxt());
                        ps.setTypeCd(NEDSSConstants.ENTITY_TYPECD_QEC);
                        List plResults = PlaceUtil.getPlaceResults(ps, request.getSession());
                        if (plResults != null && plResults.size() > 0)
                        {
                            PlaceVO pvo = (PlaceVO) plResults.get(0);
                            jsonObject.addProperty("placeUid", pvo.getPlaceUid());
                            jsonObject.addProperty("name", pvo.getNm());
                            Gson g = new Gson();
                            PlaceSearchVO a = pvo.getLatestAddress();
                            a.setState(CachedDropDowns.getStateName(a.getState()));
                            jsonObject.add("address", g.toJsonTree(pvo.getLatestAddress()));
                            jsonObject.add("telephone", g.toJsonTree(pvo.getLatestPhone()));
                        }
                    }
                }
            }
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            jsonObject.addProperty("results", result);
            out.print(jsonObject);
            out.flush();
        }
        catch (Exception e)
        {
        	logger.warn("Exception on Place Check QUick Entry Code json call - " + e);
        }
        return null;
    }

    private String buildSearchCriteriaString(PlaceSearchVO placeSearch)
    {
        // build the criteria string
        StringBuffer sQuery = new StringBuffer("");
        CachedDropDownValues cache = new CachedDropDownValues();

        if (!StringUtils.isEmpty(placeSearch.getNm()))
            sQuery.append("Name").append(" " + cache.getDescForCode("SEARCH_SNDX", placeSearch.getNmOperator()) + " ")
                    .append("'" + placeSearch.getNm() + "'").append(", ");

        if (!StringUtils.isEmpty(placeSearch.getStreetAddr1()))
            sQuery.append("Street Address")
                    .append(" " + cache.getDescForCode("SEARCH_SNDX", placeSearch.getStreetAddr1Operator()) + " ")
                    .append("'" + placeSearch.getStreetAddr1() + "'").append(", ");

        if (!StringUtils.isEmpty(placeSearch.getCity()))
            sQuery.append("City")
                    .append(" " + cache.getDescForCode("SEARCH_SNDX", placeSearch.getCityOperator()) + " ")
                    .append("'" + placeSearch.getCity() + "'").append(", ");

        if (!StringUtils.isEmpty(placeSearch.getState()))
            sQuery.append("State Equal ").append("'" + getStateDescTxt(placeSearch.getState()) + "'").append(", ");

        if (!StringUtils.isEmpty(placeSearch.getZip()))
            sQuery.append("Zip Equal ").append("'" + placeSearch.getZip() + "'").append(", ");

        if (placeSearch.getPhoneNbrTxt() != null && !placeSearch.getPhoneNbrTxt().equals(""))
            sQuery.append("Telephone Contains ").append("'" + placeSearch.getPhoneNbrTxt() + "'").append(", ");

        return sQuery.toString();

    }

    private String getStateDescTxt(String sStateCd)
    {
        return CachedDropDowns.getStateName(sStateCd);
    }

    private boolean isEqual(Object obj1, Object obj2)
    {
        return (obj1 != null && obj2 != null && obj1.equals(obj2));
    }
}