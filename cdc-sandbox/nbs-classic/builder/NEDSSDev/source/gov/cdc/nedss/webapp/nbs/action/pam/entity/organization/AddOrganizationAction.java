package gov.cdc.nedss.webapp.nbs.action.pam.entity.organization;
import gov.cdc.nedss.entity.entityid.dt.EntityIdDT;
import gov.cdc.nedss.entity.organization.dt.OrganizationDT;
import gov.cdc.nedss.entity.organization.dt.OrganizationNameDT;
import gov.cdc.nedss.entity.organization.vo.OrganizationDisplayVO;
import gov.cdc.nedss.entity.organization.vo.OrganizationSearchVO;
import gov.cdc.nedss.entity.organization.vo.OrganizationVO;
import gov.cdc.nedss.exception.NEDSSAppConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.locator.dt.EntityLocatorParticipationDT;
import gov.cdc.nedss.locator.dt.PostalLocatorDT;
import gov.cdc.nedss.locator.dt.TeleLocatorDT;
import gov.cdc.nedss.proxy.ejb.entityproxyejb.bean.EntityProxyHome;
import gov.cdc.nedss.proxy.util.EntityProxyHelper;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.HTMLEncoder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.action.util.NBSPageConstants;
import gov.cdc.nedss.webapp.nbs.form.organization.EntitySearchAddOrganizationForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
public class AddOrganizationAction extends DispatchAction {

	static final LogUtils logger = new LogUtils(AddOrganizationAction.class.getName());
	private static CachedDropDownValues srtValues = new CachedDropDownValues();
	private static TreeMap<Object,Object> treemap = srtValues.getStateCodes2("USA");

    public ActionForward addOrganization(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
    	EntitySearchAddOrganizationForm osForm = (EntitySearchAddOrganizationForm) form;
    	osForm.reset();
    	OrganizationSearchVO osVO = (OrganizationSearchVO)request.getSession().getAttribute("osVO");
    	String identifier = HTMLEncoder.encodeHtml(request.getParameter("identifier"));
		request.setAttribute("identifier", identifier);
		if (osVO.getNmTxtOperator() != null && (osVO.getNmTxtOperator().equals("=") || osVO.getNmTxtOperator().equals("CT") || osVO.getNmTxtOperator().equals("SW"))&& osVO.getNmTxt()!=null)
			osForm.setName(osVO.getNmTxt());
		if ((osVO.getStreetAddr1Operator().equals("=") || osVO.getStreetAddr1Operator().equals("CT") || osVO.getStreetAddr1Operator().equals("SW") )&& osVO.getStreetAddr1()!=null )
			osForm.setStreetAddress1(osVO.getStreetAddr1());
		if(osVO.getCityDescTxtOperator()!=null	&& osVO.getCityDescTxtOperator().equals("=") || osVO.getCityDescTxtOperator().equals("CT") || osVO.getCityDescTxtOperator().equals("SW"))
		    	osForm.setCity(osVO.getCityDescTxt());
		if(osVO.getStateCd()!=null)
		  {   osForm.setState(osVO.getStateCd());
		  osForm.getDwrCountiesForState(osVO.getStateCd());
		  }else{
			  osForm.setState(PropertyUtil.getInstance().getNBS_STATE_CODE());
			  osForm.getDwrCountiesForState(PropertyUtil.getInstance().getNBS_STATE_CODE());
		  }
		osForm.setCountry("840"); // USA(Default value)
	   if(osVO.getZipCd()!=null)
		   osForm.setZip(osVO.getZipCd());
	   if(osVO.getRootExtensionTxt()!=null)
//		   osForm.setQuickCode(osVO.getRootExtensionTxt());
	   if(osVO.getPhoneNbrTxt()!=null){
		   osForm.setTelephoneNumber(osVO.getPhoneNbrTxt());
		   }
		request.getSession().removeAttribute("osVO");
		osForm.setPageTitle("Add Organization", request);
    	return (mapping.findForward("addOrganization"));
    }

    public ActionForward submitOrganization(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws IOException, ServletException {
		NBSSecurityObj securityObj = null;
		EntitySearchAddOrganizationForm organizationAddForm = (EntitySearchAddOrganizationForm)form;
    	OrganizationVO organizationVO = null;
    	HttpSession session = request.getSession();
    	ArrayList<Object> errorList = new ArrayList<Object> ();
    	String identifier = request.getParameter("identifier");
		request.setAttribute("identifier", HTMLEncoder.encodeHtml(identifier));
    	if (session == null)
    	{
    	    logger.debug("error no session");

    	    return mapping.findForward("login");
    	}

    	Object obj = session.getAttribute("NBSSecurityObject");
    	if (obj != null)securityObj = (NBSSecurityObj)obj;

    	try{
    		organizationVO = createHandler(organizationAddForm, securityObj, session, request, response);
        	logger.info("Going to send personVO to EJB");
            Long UID = sendProxyToEJB(organizationVO,"setOrganization",session);
    		OrganizationDisplayVO displayVO = new OrganizationDisplayVO();
    		displayVO.setOrganizationUid(UID.toString());
    		String displayResult =createDisplayOrganization(organizationVO,displayVO);
    		logger.info(" About to set the OrganizationUID:" + UID);

    		request.setAttribute("organizationUid",HTMLEncoder.encodeHtml(UID.toString()));
            request.getSession().setAttribute("oneOrganization",displayResult);

        	}catch (NEDSSAppConcurrentDataException nae)
    	    {
    		logger.fatal("ERROR - NEDSSAppConcurrentDataException, The data has been modified by another user, please recheck! ",
    			nae);

    		throw new ServletException("ERROR - NEDSSAppConcurrentDataException, The data has been modified by another user, please recheck! "+nae.getMessage(),nae);

    	    }catch (NEDSSAppException nae){
    	    	 logger.info(nae.getMessage());
                if(nae.toString().indexOf("Quick Code is not unique") != -1)
                 {
                	ActionMessages messages = new ActionMessages();
    				messages.add(NBSPageConstants.ERROR_MESSAGES_PROPERTY,
    						new ActionMessage(NBSPageConstants.ERROR_NONUNIQUE_MESSAGE_KEY, "Quick Code"));
    				request.setAttribute("error_messages", messages);
                	logger.fatal("ERROR - NEDSSAppException, the quickCd is not unique for Organization ");
                  // request.setAttribute("err111", "Quick Code must be a unique Code");
                	organizationAddForm.setPageTitle("Add Organization", request);
                	return mapping.findForward("addOrganization");
                 }else{

                	 throw new ServletException();
                 }

    	    }catch (Exception e){

    	    	throw new ServletException(e);

    	    }

    	organizationAddForm.setPageTitle("Add Organization", request);
    	return (mapping.findForward("submitOrganization"));
	}


    private Long sendProxyToEJB(OrganizationVO organization, String paramMethodName,
    		HttpSession session)
    	 throws NEDSSAppConcurrentDataException,
    		NEDSSAppException, javax.ejb.EJBException,
    		Exception
    {

    /**
    * Call the mainsessioncommand
    */
    			MainSessionCommand msCommand = null;
    			String sBeanJndiName = JNDINames.ENTITY_PROXY_EJB;
    			String sMethod = paramMethodName;

    			Object temp = null;

    			if (paramMethodName.equals("setOrganization"))
    			temp = organization;
    			Object[] oParams = { temp };

    			// if(msCommand == null)
    			// {
    			MainSessionHolder holder = new MainSessionHolder();
    			msCommand = holder.getMainSessionCommand(session);
    			ArrayList<?> resultUIDArr = new ArrayList<Object> ();
    			// }
    			try
    			{
    				if (!EntityProxyHelper.getInstance().isQuickCodeUnique(organization)) {
    			           throw new  NEDSSAppException("Quick Code is not unique");
    			    }


    			resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod,
    							oParams);

    			}catch (NEDSSAppConcurrentDataException nse)
    		    {
    				logger.fatal(
    					"ERROR - NEDSSAppConcurrentDataException, The data has been modified by another user, please recheck! ",
    					nse);
    			throw new NEDSSAppConcurrentDataException("Concurrent Exception " + nse.getMessage());

    			}catch (NEDSSAppException nae){
    			    	logger.fatal(
    			    			"ERROR - NEDSSAppException, The data has been modified by another user, please recheck! ",
    			    			nae);

    			    	throw new NEDSSAppException("Error calling Proxy" + nae.getMessage());
    			}catch (Exception e){


    			}

    			if ((resultUIDArr != null) && (resultUIDArr.size() > 0))
    			{
    			logger.info("Created or updated a Organization = " +
    				resultUIDArr.get(0));

    			Long result = (Long)resultUIDArr.get(0);

    			logger.info("Long returned from EJB is: " + result);

    			//Reload QuickEntry Cache
    			ArrayList<Object> qecList = CachedDropDowns.getAllQECodes(true,"ORG");
    			session.setAttribute("qecListORG", qecList);
    			return result;

    			}
    			else

    			return null;
    			}

    private OrganizationVO createHandler(EntitySearchAddOrganizationForm organizationForm,
			   NBSSecurityObj securityObj,
			   HttpSession session,
			   HttpServletRequest request,
			   HttpServletResponse response)
				{

    			OrganizationVO organizationVO = new OrganizationVO();
				try
				{
					organizationVO.setItNew(true);
					organizationVO.setItDirty(false);

				// set up the DT for the EJB
				OrganizationDT organizationDT = new OrganizationDT();
				organizationDT.setItNew(true);
				organizationDT.setItDirty(false);
				organizationDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
				organizationDT.setOrganizationUid(new Long(-1));
				organizationDT.setCd("ORG");
				organizationDT.setElectronicInd("N");
				organizationDT.setAddTime(new Timestamp(new Date().getTime()));
				organizationDT.setLastChgTime(new Timestamp(new Date().getTime()));
				organizationDT.setRecordStatusTime(new Timestamp(new Date().getTime()));
				organizationDT.setStatusTime(new Timestamp(new Date().getTime()));
				organizationDT.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
				//organizationDT.setStandardIndustryClassCd();
				//organizationDT.setAddReasonCd(aAddReasonCd)//

				organizationVO.setTheOrganizationDT(organizationDT);
				organizationVO = setNameCreate(organizationVO,organizationForm);
				organizationVO = setIds(organizationVO,organizationForm);
				organizationVO = setBasicAddresses(organizationVO, organizationForm);
				organizationVO = setBasicTelephones(organizationVO,organizationForm);
					if(organizationForm.getQuickCode()!=null &&  organizationForm.getQuickCode().length()>0){
							try{
							  EntityProxyHelper.getInstance().isQuickCodeUnique(organizationVO) ;
								}
							catch(NEDSSAppException ne)
								{

								}
					}
				}catch (Exception e)
				{
				e.printStackTrace();
				}
		return organizationVO;
	}


    private OrganizationVO setNameCreate(OrganizationVO organizationVO, EntitySearchAddOrganizationForm  organizationForm){

        if((organizationForm.getName()!=null && !organizationForm.getName().trim().equals("")))
        {
            OrganizationNameDT organizationNameDT = new OrganizationNameDT();
            organizationNameDT.setItNew(true);
            organizationNameDT.setItDirty(false);
            //organizationNameDT.setProgAreaCd(aProgAreaCd)
            //organizationNameDT.setJurisdictionCd(aJurisdictionCd)
            organizationNameDT.setNmUseCd(NEDSSConstants.LEGAL);
            organizationNameDT.setStatusTime(new Timestamp(new Date().getTime()));
            organizationNameDT.setAddTime(new Timestamp(new Date().getTime()));
            organizationNameDT.setRecordStatusTime(new Timestamp(new Date().getTime()));
            organizationNameDT.setOrganizationUid(organizationVO.getTheOrganizationDT().getOrganizationUid());
            organizationNameDT.setRecordStatusCd(NEDSSConstants.ACTIVE);
            organizationNameDT.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
            organizationNameDT.setNmTxt(organizationForm.getName());



            Collection<Object>  allnamesDT = new ArrayList<Object> ();
            allnamesDT.add(organizationNameDT);

            organizationVO.setTheOrganizationNameDTCollection(allnamesDT);
        }

        return organizationVO ;

    }

//    public Collection<Object>  theOrganizationNameDTCollection;
//    public Collection<Object>  theEntityLocatorParticipationDTCollection;
//    public Collection<Object>  theEntityIdDTCollection;
//
//    //collections for role and participation object association added by John Park
//    public Collection<Object>  theParticipationDTCollection;
//    public Collection<Object>  theRoleDTCollection;

    private OrganizationVO setIds(OrganizationVO organizationVO, EntitySearchAddOrganizationForm  organizationForm){



        if (organizationForm.getQuickCode() != null && !organizationForm.getQuickCode().trim().equals("")) {
        EntityIdDT iddt = null;
        iddt = new EntityIdDT();
        iddt.setEntityIdSeq(new Integer(1));
        iddt.setAddTime(new Timestamp(new Date().getTime()));
        iddt.setLastChgTime(new Timestamp(new Date().getTime()));
        iddt.setRecordStatusTime(new Timestamp(new Date().getTime()));
        iddt.setStatusTime(new Timestamp(new Date().getTime()));
        iddt.setEntityUid(organizationVO.getTheOrganizationDT().getOrganizationUid());
        iddt.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
        iddt.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
        iddt.setTypeCd(NEDSSConstants.ENTITY_TYPECD_QEC);
        iddt.setTypeDescTxt(NEDSSConstants.ENTITY_TYPE_DESC_TXT_QEC);
        iddt.setRootExtensionTxt(organizationForm.getQuickCode());
        ArrayList<Object> idList = new ArrayList<Object> ();
        idList.add(iddt);
        organizationVO.setTheEntityIdDTCollection(idList);
     }
     return organizationVO ;
    }


    private OrganizationVO setBasicAddresses(OrganizationVO organizationVO, EntitySearchAddOrganizationForm  organizationForm)
    {

    	    if((organizationForm.getCity()!=null && !organizationForm.getCity().equals("")) ||
    	      (organizationForm.getStreetAddress1()!=null && !organizationForm.getStreetAddress1().equals("")) ||
    	      (organizationForm.getStreetAddress2()!=null && !organizationForm.getStreetAddress2().equals("")) ||
    	      (organizationForm.getZip()!=null && !organizationForm.getZip().equals(""))|| (organizationForm.getCounty()!=null && !organizationForm.getCounty().equals(""))
    	      ||(organizationForm.getState()!=null && !organizationForm.getState().equals("")))

    {


        logger.info("Inside setBasicAddresses");
        Collection<Object>  arrELP = new ArrayList<Object> ();
        EntityLocatorParticipationDT elp = new EntityLocatorParticipationDT();
        		PostalLocatorDT pl = new PostalLocatorDT();
    	        pl.setCityDescTxt(organizationForm.getCity());
    	        pl.setStreetAddr1(organizationForm.getStreetAddress1());
    	        pl.setStreetAddr2(organizationForm.getStreetAddress2());
    	        pl.setZipCd(organizationForm.getZip());
    	        pl.setStateCd(organizationForm.getState());
    	        pl.setCntyCd(organizationForm.getCounty());
    	        pl.setCntryCd(organizationForm.getCountry());
    	        pl.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
    	        pl.setItNew(true);
    	        pl.setItDirty(false);
    	        pl.setAddTime(new Timestamp(new Date().getTime()));
    	        pl.setRecordStatusTime(new Timestamp(new Date().getTime()));

//    	        pl.seta


        elp.setItNew(true);
        elp.setItDirty(false);
        elp.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
        elp.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
        elp.setEntityUid(organizationVO.getTheOrganizationDT().getOrganizationUid());
        //elp.setCd(NEDSSConstants.CURRENT); // office
        elp.setCd("O");
        elp.setClassCd(NEDSSConstants.POSTAL); // Is is required for Office
        //elp.setUseCd(NEDSSConstants.HOME);
        elp.setUseCd(NEDSSConstants.WORK_PHONE);

        elp.setThePostalLocatorDT(pl);
        arrELP.add(elp);
        logger.info("Number of address in setBasicAddresses: " + arrELP.size());
        organizationVO.setTheEntityLocatorParticipationDTCollection(arrELP);
    }

    return organizationVO ;

    }

    private OrganizationVO setBasicTelephones(OrganizationVO organizationVO, EntitySearchAddOrganizationForm  organizationForm)
    {
    	Collection<Object>  arrELP = new ArrayList<Object> ();
        logger.info("Inside setBasicTelephones on AddOrganization");

        if(organizationVO.getTheEntityLocatorParticipationDTCollection()!=null){
        	 arrELP = organizationVO.getTheEntityLocatorParticipationDTCollection();
        }

        // This is not applicable for Add Provider under RVCT
        //  EntityLocatorParticipationDT elpHome = new EntityLocatorParticipationDT();
        // TeleLocatorDT teleDTHome = new TeleLocatorDT();
        EntityLocatorParticipationDT elpWork = new EntityLocatorParticipationDT();
        		TeleLocatorDT teleDTWork = new TeleLocatorDT();

        //Work Phone
        			if(organizationForm.getTelephone()!=null && !organizationForm.getTelephone().equals("")|| organizationForm.getEMail()!=null && !organizationForm.getEMail().equals("")){
    				elpWork.setItNew(true);
    				elpWork.setItDirty(false);
    				elpWork.setEntityUid(organizationVO.getTheOrganizationDT().getOrganizationUid());
    				elpWork.setClassCd(NEDSSConstants.TELE);
    				elpWork.setCd(NEDSSConstants.PHONE);
    				elpWork.setUseCd(NEDSSConstants.WORK_PHONE);
    				elpWork.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
    				elpWork.setStatusCd(NEDSSConstants.STATUS_ACTIVE);

    				teleDTWork.setItNew(true);
    				teleDTWork.setItDirty(false);
    				teleDTWork.setAddTime(new Timestamp(new Date().getTime()));
    				teleDTWork.setExtensionTxt(organizationForm.getExt());
    				teleDTWork.setPhoneNbrTxt(organizationForm.getTelephone());
    				teleDTWork.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
    				teleDTWork.setEmailAddress(organizationForm.getEMail());
    				elpWork.setTheTeleLocatorDT(teleDTWork);
    				arrELP.add(elpWork);
        }


        organizationVO.setTheEntityLocatorParticipationDTCollection(arrELP);
        return organizationVO ;
    }

    private String createDisplayOrganization(OrganizationVO organizationVO , OrganizationDisplayVO displayVO) {

		StringBuffer sb = null;
		sb = new StringBuffer("");

	  	Collection<Object>  nameDTS = organizationVO.getTheOrganizationNameDTCollection();

	  	sb.append("<table role=\"presentation\" class=\"noStylesTable\">");
	   	if(nameDTS != null){
	  		Iterator<Object> iter = nameDTS.iterator();
	  		while(iter.hasNext()){
	  			OrganizationNameDT name = (OrganizationNameDT) iter.next();
			  		if (name.getNmUseCd() != null && name.getNmUseCd().trim().equals("L")){
			  		//if (name.getNmUseCd() != null && name.getNmUseCd().trim().equals("WP")){
			  			String orgName= name.getNmTxt();
			  			if(orgName != null && orgName.trim().length() > 0 ){
			  			sb.append("<tr><td style=\"padding:0px;\">").append(HTMLEncoder.encodeHtml(orgName));
			  			sb.append("</td></tr>");
		  			}
	  			}

	  	//Organization Name
	  	displayVO.setName(sb.toString());

		Collection<Object>  elps = organizationVO.getTheEntityLocatorParticipationDTCollection();
		if (elps != null) {
			Iterator<Object> elpIter = elps.iterator();
			while (elpIter.hasNext()) {
				EntityLocatorParticipationDT elpDT = (EntityLocatorParticipationDT) elpIter.next();

				// Postal Locator
				if (elpDT.getClassCd() != null
						&& elpDT.getClassCd().equals(NEDSSConstants.POSTAL)) {
					PostalLocatorDT postal = elpDT.getThePostalLocatorDT();
					if (postal != null
							&& (postal.getStreetAddr1() != null
									|| postal.getCityCd() != null
									|| postal.getStateCd() != null
									|| postal.getZipCd() != null || postal
									.getStreetAddr2() != null)) {
						String streetAdd1 = postal.getStreetAddr1();
						String streetAdd2 = postal.getStreetAddr2();
						String cityCd = postal.getCityCd();
						String cityDesc = postal.getCityDescTxt();
						String stateDesc = getStateDescTxt(postal.getStateCd());
						String zipCd = postal.getZipCd();
						if (streetAdd1 != null
								&& streetAdd1.trim().length() > 0)
							sb.append("<tr><td style=\"padding:0px;\">").append(HTMLEncoder.encodeHtml(streetAdd1))
									.append("</td></tr>");
						if (streetAdd2 != null
								&& streetAdd2.trim().length() > 0)
							sb.append("<tr><td style=\"padding:0px;\">").append(HTMLEncoder.encodeHtml(streetAdd2))
									.append("</td></tr>");
						sb.append("<tr><td style=\"padding:0px;\">");
						if (cityDesc != null && cityDesc.trim().length() > 0)
							sb.append(HTMLEncoder.encodeHtml(cityDesc)).append(", ");
						if (stateDesc != null
								&& stateDesc.trim().length() > 0)
							sb.append(HTMLEncoder.encodeHtml(stateDesc)).append(" ");
						if (zipCd != null && zipCd.trim().length() > 0)
							sb.append(HTMLEncoder.encodeHtml(zipCd));
						sb.append("</td></tr>");
					}
				}

				// Tele Locator
				else if (elpDT.getClassCd() != null
						&& elpDT.getClassCd().equalsIgnoreCase(
								NEDSSConstants.TELE)) {
					TeleLocatorDT tele = elpDT.getTheTeleLocatorDT();

					 if(tele.getEmailAddress()!= null && tele.getEmailAddress().trim().length() > 0){
					    	sb.append("<tr><td style=\"padding:0px;\">");
					    	sb.append(HTMLEncoder.encodeHtml(tele.getEmailAddress())).append("</td></tr>");
					    }

					if (elpDT.getUseCd() != null && (tele.getPhoneNbrTxt() != null && tele.getPhoneNbrTxt().trim().length() > 0)) {

						sb.append("<tr><td style=\"padding:0px;\">");
						//String useCd = elpDT.getUseCd();
						String phoneNbr = tele.getPhoneNbrTxt();
						sb.append(HTMLEncoder.encodeHtml(phoneNbr));
					    if(tele.getExtensionTxt()!= null && tele.getExtensionTxt().trim().length() > 0){
					    	sb.append(" ").append("<b>Ext</b>").append(".");
					    	sb.append(" ").append(HTMLEncoder.encodeHtml(tele.getExtensionTxt())).append("</td></tr>");
					    }
					    sb.append("</td></tr>");



					}


				}
			}



		  	//Address
		  	displayVO.setAddress(sb.toString());
		  	//Telephone
		  	displayVO.setTelephone(sb.toString());



		   	sb.append("</table>");
		  	//ID
		  	displayVO.setId(sb.toString());


	  	}

		}
	  }


	   	return sb.toString() ;
	}

	   	private String getStateDescTxt(String sStateCd) {
			String desc= null;
				  	if (treemap != null){
				      if(sStateCd!=null && treemap.get(sStateCd)!=null)
				      	desc = (String)treemap.get(sStateCd);
				  	}
	  	return desc;
		}

}
