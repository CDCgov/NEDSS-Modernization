package gov.cdc.nedss.webapp.nbs.action.pam.entity.provider;
import gov.cdc.nedss.entity.entityid.dt.EntityIdDT;
import gov.cdc.nedss.entity.person.dt.PersonDT;
import gov.cdc.nedss.entity.person.dt.PersonNameDT;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.entity.person.vo.ProviderDisplayVO;
import gov.cdc.nedss.entity.person.vo.ProviderSearchVO;
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
import gov.cdc.nedss.webapp.nbs.form.provider.EntitySearchAddProviderForm;
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
public class AddProviderAction extends DispatchAction {

	static final LogUtils logger = new LogUtils(AddProviderAction.class.getName());
	private static CachedDropDownValues srtValues = new CachedDropDownValues();
    private static TreeMap<Object,Object> treemap = srtValues.getStateCodes2("USA");


    public ActionForward addProvider(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {

    		EntitySearchAddProviderForm psForm = (EntitySearchAddProviderForm) form;
    		psForm.reset();

    		ProviderSearchVO psVO = (ProviderSearchVO)request.getSession().getAttribute("psVO");
    		String identifier = request.getParameter("identifier");
  		  	request.setAttribute("identifier", identifier);
    		if (psVO.getFirstNameOperator() != null && (psVO.getFirstNameOperator().equals("=") || psVO.getFirstNameOperator().equals("CT") || psVO.getFirstNameOperator().equals("SW"))&& psVO.getFirstName()!=null)
    			psForm.setFirstName(psVO.getFirstName());
			if (psVO.getLastNameOperator() != null && (psVO.getLastNameOperator().equals("=") || psVO.getLastNameOperator().equals("CT") || psVO.getLastNameOperator().equals("SW") ) && psVO.getLastName()!=null)
				psForm.setLastName(psVO.getLastName());
			if ((psVO.getStreetAddr1Operator().equals("=") || psVO.getStreetAddr1Operator().equals("CT") || psVO.getStreetAddr1Operator().equals("SW") )&& psVO.getStreetAddr1()!=null )
				psForm.setStreetAddress1(psVO.getStreetAddr1());
			if(psVO.getCityDescTxtOperator()!=null)
			if(psVO.getCityDescTxtOperator().equals("=") || psVO.getCityDescTxtOperator().equals("CT") || psVO.getCityDescTxtOperator().equals("SW"))
			    	psForm.setCity(psVO.getCityDescTxt());
			if(psVO.getState()!=null)
			  {   psForm.setState(psVO.getState());
			  psForm.getDwrCountiesForState(psVO.getState());
			  }else{
				  psForm.setState(PropertyUtil.getInstance().getNBS_STATE_CODE());
				  psForm.getDwrCountiesForState(PropertyUtil.getInstance().getNBS_STATE_CODE());
			  }
		   if(psVO.getZipCd()!=null)
			psForm.setZip(psVO.getZipCd());
		   if(psVO.getRootExtensionTxt()!=null)
//			  psForm.setQuickCode(psVO.getRootExtensionTxt());
		   if(psVO.getPhoneNbrTxt()!=null){
			   psForm.setTelephoneNumber(psVO.getPhoneNbrTxt());
			   }

		  request.getSession().removeAttribute("psVO");
		  psForm.setPageTitle("Add Provider", request);
		  return (mapping.findForward("addOneProvider"));

    }



    public ActionForward submitProvider(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws IOException, ServletException {

//    	request.setAttribute("SubmitStayOnError","None");
    	NBSSecurityObj securityObj = null;
    	EntitySearchAddProviderForm personForm = (EntitySearchAddProviderForm)form;
    	PersonVO personVO = null;
    	HttpSession session = request.getSession();
    	ArrayList<Object> errorList = new ArrayList<Object> ();
    	String identifier = request.getParameter("identifier");
		request.setAttribute("identifier", identifier);
    	if (session == null)
    	{
    	    logger.debug("error no session");

    	    return mapping.findForward("login");
    	}

    	Object obj = session.getAttribute("NBSSecurityObject");
    	if (obj != null)securityObj = (NBSSecurityObj)obj;

    	try{
    	personVO = createHandler(personForm, securityObj, session, request, response);
    	logger.info("Going to send personVO to EJB");
        Long UID = sendProxyToEJB(personVO,"setProvider",session);
		ProviderDisplayVO displayVO = new ProviderDisplayVO();
		displayVO.setProviderUID(UID.toString());
		String displayResult =createDisplayProvider(personVO,displayVO);
		logger.info(" About to set the PERSONUID on the DSPersonSummary to: " + UID);

		request.setAttribute("providerUID",UID.toString());
        request.getSession().setAttribute("oneProvider",displayResult);

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
               logger.fatal("ERROR - NEDSSAppException, the quickCd is not unique for Provider ");
               personForm.setPageTitle("Add Provider", request);
               return mapping.findForward("addOneProvider");
             }else{

            	 throw new ServletException(nae.getMessage(),nae);
             }

	    }catch (Exception e){

	    	throw new ServletException(e);

	    }


	    personForm.setPageTitle("Add Provider", request);
	    return (mapping.findForward("submitProvider"));
	}



private PersonVO createHandler(EntitySearchAddProviderForm personForm,
							   NBSSecurityObj securityObj,
							   HttpSession session,
							   HttpServletRequest request,
							   HttpServletResponse response)
				{

				PersonVO personVO = new PersonVO();
				try
				{
				 personVO.setItNew(true);
				 personVO.setItDirty(false);

				 // set up the DT for the EJB
				 PersonDT personDT = new PersonDT();
				 personDT.setItNew(true);
				 personDT.setItDirty(false);
				 personDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
				 personDT.setPersonUid(new Long(-1));
				 //RK: 3/3/04 : Added the following line to fix civil00010631
				 personDT.setPersonParentUid(null);
				 personDT.setCd("PRV");
				 personDT.setElectronicInd("N");
				 personDT.setAddTime(new Timestamp(new Date().getTime()));
				 personDT.setLastChgTime(new Timestamp(new Date().getTime()));
				 personDT.setRecordStatusTime(new Timestamp(new Date().getTime()));
				 personDT.setStatusTime(new Timestamp(new Date().getTime()));
				 personDT.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
				 personVO.setThePersonDT(personDT);
				 personVO = setNameCreate(personVO,personForm);
				 personVO = setIds(personVO,personForm);
				 personVO = setBasicAddresses(personVO, personForm);
				 personVO = setBasicTelephones(personVO,personForm);

				 if(personForm.getQuickCode()!=null &&  personForm.getQuickCode().length()>0){
				 	try{
					  EntityProxyHelper.getInstance().isQuickCodeUnique(personVO) ;
			         }catch(NEDSSAppException ne){

				 	}
				 }
				}catch (Exception e)
				{
				 e.printStackTrace();
				}



				return personVO;
 }
private PersonVO setNameCreate(PersonVO personVO, EntitySearchAddProviderForm  personForm){

           if((personForm.getLastName()!=null && !personForm.getLastName().trim().equals("")) ||
               (personForm.getFirstName()!=null && !personForm.getFirstName().trim().equals("")) ||
               (personForm.getMiddleName()!=null && !personForm.getMiddleName().trim().equals("")))
           {
               PersonNameDT nameDT = new PersonNameDT();
               nameDT.setItNew(true);
               nameDT.setItDirty(false);
               nameDT.setNmUseCd(NEDSSConstants.LEGAL);
               nameDT.setPersonNameSeq(new Integer(1));
               nameDT.setStatusTime(new Timestamp(new Date().getTime()));
               nameDT.setAddTime(new Timestamp(new Date().getTime()));
               nameDT.setRecordStatusTime(new Timestamp(new Date().getTime()));
               nameDT.setPersonUid(personVO.getThePersonDT().getPersonUid());
               nameDT.setRecordStatusCd(NEDSSConstants.ACTIVE);
               nameDT.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
               nameDT.setLastNm(personForm.getLastName());
               nameDT.setFirstNm(personForm.getFirstName());
               nameDT.setMiddleNm(personForm.getMiddleName());
               nameDT.setNmDegree(personForm.getDegree());
               nameDT.setNmPrefix(personForm.getPrefix());
               nameDT.setNmSuffix(personForm.getSuffix());


               Collection<Object>  allnamesDT = new ArrayList<Object> ();
               allnamesDT.add(nameDT);

               personVO.setThePersonNameDTCollection(allnamesDT);
           }

           return personVO ;

       }

private PersonVO setIds(PersonVO personVO, EntitySearchAddProviderForm personForm){



    if (personForm.getQuickCode() != null && !personForm.getQuickCode().trim().equals("")) {
    EntityIdDT iddt = null;
    iddt = new EntityIdDT();
    iddt.setEntityIdSeq(new Integer(1));
    iddt.setAddTime(new Timestamp(new Date().getTime()));
    iddt.setLastChgTime(new Timestamp(new Date().getTime()));
    iddt.setRecordStatusTime(new Timestamp(new Date().getTime()));
    iddt.setStatusTime(new Timestamp(new Date().getTime()));
    iddt.setEntityUid(personVO.getThePersonDT().getPersonUid());
    iddt.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
    iddt.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
    iddt.setTypeCd(NEDSSConstants.ENTITY_TYPECD_QEC);
    iddt.setTypeDescTxt(NEDSSConstants.ENTITY_TYPE_DESC_TXT_QEC);
    iddt.setRootExtensionTxt(personForm.getQuickCode());
    ArrayList<Object> idList = new ArrayList<Object> ();
    idList.add(iddt);
    personVO.setTheEntityIdDTCollection(idList);
 }
 return personVO ;
}
/**
 * This method is used by the Person-Add Basic screen.  The
 * address entered will be set as a home address.  This data
 * is set to the PersonVO.
 *
 * @param personVO PersonVO
 * @param personForm
 */
private PersonVO setBasicAddresses(PersonVO personVO, EntitySearchAddProviderForm personForm)
{

	    if((personForm.getCity()!=null && !personForm.getCity().equals("")) ||
	      (personForm.getStreetAddress1()!=null && !personForm.getStreetAddress1().equals("")) ||
	      (personForm.getStreetAddress2()!=null && !personForm.getStreetAddress2().equals("")) ||
	      (personForm.getZip()!=null && !personForm.getZip().equals(""))|| (personForm.getCounty()!=null && !personForm.getCounty().equals(""))
	      ||(personForm.getState()!=null && !personForm.getState().equals("")))

{


    logger.info("Inside setBasicAddresses");
    Collection<Object>  arrELP = new ArrayList<Object> ();
    EntityLocatorParticipationDT elp = new EntityLocatorParticipationDT();
    		PostalLocatorDT pl = new PostalLocatorDT();
	        pl.setCityDescTxt(personForm.getCity());
	        pl.setStreetAddr1(personForm.getStreetAddress1());
	        pl.setStreetAddr2(personForm.getStreetAddress2());
	        pl.setZipCd(personForm.getZip());
	        pl.setStateCd(personForm.getState());
	        pl.setCntyCd(personForm.getCounty());
	        pl.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
	        pl.setItNew(true);
	        pl.setItDirty(false);
	        pl.setAddTime(new Timestamp(new Date().getTime()));
	        pl.setRecordStatusTime(new Timestamp(new Date().getTime()));


//	        pl.seta


    elp.setItNew(true);
    elp.setItDirty(false);
    elp.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
    elp.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
    elp.setEntityUid(personVO.getThePersonDT().getPersonUid());
    //elp.setCd(NEDSSConstants.CURRENT); // office
    elp.setCd("O");
    elp.setClassCd(NEDSSConstants.POSTAL); // Is is required for Office
    //elp.setUseCd(NEDSSConstants.HOME);
    elp.setUseCd(NEDSSConstants.WORK_PHONE);


    elp.setThePostalLocatorDT(pl);
    arrELP.add(elp);
    logger.info("Number of address in setBasicAddresses: " + arrELP.size());
    personVO.setTheEntityLocatorParticipationDTCollection(arrELP);
}

return personVO ;

}

/**
 * This method is used by the Person-Add Basic page.  The phone
 * number entered will be set as a home phone number.  This data
 * will be set onto the PersonVO.
 *
 * @param personVO PersonVO
 * @param request HttpServletRequest
 */
private PersonVO setBasicTelephones(PersonVO personVO, EntitySearchAddProviderForm personForm)
{
	Collection<Object>  arrELP = new ArrayList<Object> ();
    logger.info("Inside setBasicTelephones");

    if(personVO.getTheEntityLocatorParticipationDTCollection()!=null){
    	 arrELP = personVO.getTheEntityLocatorParticipationDTCollection();
    }

    // This is not applicable for Add Provider under RVCT
    //  EntityLocatorParticipationDT elpHome = new EntityLocatorParticipationDT();
    // TeleLocatorDT teleDTHome = new TeleLocatorDT();
    EntityLocatorParticipationDT elpWork = new EntityLocatorParticipationDT();
    		TeleLocatorDT teleDTWork = new TeleLocatorDT();

    //Work Phone
    			if(personForm.getTelephone()!=null && !personForm.getTelephone().equals("")|| personForm.getEMail()!=null && !personForm.getEMail().equals("")){
				elpWork.setItNew(true);
				elpWork.setItDirty(false);
				elpWork.setEntityUid(personVO.getThePersonDT().getPersonUid());
				elpWork.setClassCd(NEDSSConstants.TELE);
				elpWork.setCd(NEDSSConstants.PHONE);
				elpWork.setUseCd(NEDSSConstants.WORK_PHONE);
				elpWork.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
				elpWork.setStatusCd(NEDSSConstants.STATUS_ACTIVE);

				teleDTWork.setItNew(true);
				teleDTWork.setItDirty(false);
				teleDTWork.setAddTime(new Timestamp(new Date().getTime()));
				teleDTWork.setExtensionTxt(personForm.getExt());
				teleDTWork.setPhoneNbrTxt(personForm.getTelephone());
				teleDTWork.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
				teleDTWork.setEmailAddress(personForm.getEMail());
				elpWork.setTheTeleLocatorDT(teleDTWork);
				arrELP.add(elpWork);
    }


    personVO.setTheEntityLocatorParticipationDTCollection(arrELP);
    return personVO ;
}

private Long sendProxyToEJB(PersonVO person, String paramMethodName,
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



			/*
			sBeanJndiName = "EntityControllerEJBRef";
			sMethod = "setPerson";
			*/
			Object temp = null;

			if (paramMethodName.equals("setProvider"))
			temp = person;
			Object[] oParams = { temp };

			// if(msCommand == null)
			// {
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);
			ArrayList<?> resultUIDArr = new ArrayList<Object> ();
			// }
			try
			{
				if (!EntityProxyHelper.getInstance().isQuickCodeUnique(person)) {
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
			logger.info("Created or updated a person = " +
				resultUIDArr.get(0));

			Long result = (Long)resultUIDArr.get(0);

			logger.info("Long returned from EJB is: " + result);

			//Reload QuickEntry Cache
			ArrayList<Object> qecList = CachedDropDowns.getAllQECodes(true,"PRV");
			session.setAttribute("qecList", qecList);
			return result;

			}
			else

			return null;
			}


   private String createDisplayProvider(PersonVO provider , ProviderDisplayVO displayVO) {

			StringBuffer sb = null;
			sb = new StringBuffer("");

		  	Collection<Object>  nameDTS = provider.getThePersonNameDTCollection();

		  	sb.append("<table role=\"presentation\">");
		   	if(nameDTS != null){
		  		Iterator iter = nameDTS.iterator();
		  		while(iter.hasNext()){
		  			PersonNameDT name = (PersonNameDT) iter.next();
				  		if (name.getNmUseCd() != null && name.getNmUseCd().trim().equals("L")){

				  			String lastNm = name.getLastNm();
				  			String firstNm = name.getFirstNm();


				  			if(lastNm != null && lastNm.trim().length() > 0  && firstNm != null && firstNm.trim().length() > 0){
				  				sb.append("<tr><td style=\"padding:0px;\">").append(HTMLEncoder.encodeHtml(firstNm)).append(" ").append(HTMLEncoder.encodeHtml(lastNm));
				  			}else if(lastNm != null && lastNm.trim().length() > 0 ){
				  				sb.append("<tr><td style=\"padding:0px;\">").append(HTMLEncoder.encodeHtml(lastNm));
				  			}

				  			if(name.getNmSuffix() != null && name.getNmSuffix().trim().length() > 0)
				  			sb.append(",").append(HTMLEncoder.encodeHtml(name.getNmSuffix()));

				  			if(name.getNmDegree() != null && name.getNmDegree().trim().length() > 0)

					  		sb.append(",").append(HTMLEncoder.encodeHtml(name.getNmDegree()));

				  			sb.append("</td></tr>");

			  			}

		  			}

		  	//Full Name
		  	displayVO.setFullName(sb.toString());

			Collection<Object>  elps = provider.getTheEntityLocatorParticipationDTCollection();
			if (elps != null) {
				Iterator elpIter = elps.iterator();
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
