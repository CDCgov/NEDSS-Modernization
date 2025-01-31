package gov.cdc.nedss.webapp.nbs.action.provider;



import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.association.dt.RoleDT;
import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.entity.person.dt.*;
import gov.cdc.nedss.entity.person.vo.*;
import gov.cdc.nedss.locator.dt.*;
import gov.cdc.nedss.webapp.nbs.form.provider.*;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.entity.entityid.dt.EntityIdDT;

import java.io.*;
import java.sql.Timestamp;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;

import gov.cdc.nedss.webapp.nbs.action.util.*;
import gov.cdc.nedss.ldf.dt.StateDefinedFieldDataDT;


/** The PersonSubmit class is class that is accessed from the front-end so that person data can be submitted to the back end.  It is used in creating new persons into the NEDSS system.  It is also used for edits to existing persons in the system.
 */
public class ProviderSubmit extends CommonAction

{

    /**
     * Instance of a String to hold value for
     * userID.
     */
    private static final Long USERID = 5150L; // don't know where to get the real useID
     

    /**
     * Instance of the LogUtils class.
     */

   
    static final LogUtils logger = new LogUtils(ProviderSubmit.class.getName());

    /** A constructor for the PersonSubmit class.
     */
    public ProviderSubmit()
    {
    	
    }

    /** This is the method that is automatically called first
     * when the PersonSubmit class is called.
     *
     * @exception IOException
     * @exception ServletException
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return an ActionForward
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form,
				 HttpServletRequest request,
				 HttpServletResponse response)
			  throws IOException, ServletException
    {

    	String editOption = null;
    	request.setAttribute("SubmitStayOnError","None");
	NBSSecurityObj securityObj = null;
	ProviderForm personForm = (ProviderForm)form;
	HttpSession session = request.getSession();
	String sCurrentTask = NBSContext.getCurrentTask(session);
	if (session == null)
	{
	    logger.debug("error no session");

	    return mapping.findForward("login");
	}

	Object obj = session.getAttribute("NBSSecurityObject");

	if (obj != null)
	    securityObj = (NBSSecurityObj)obj;

	


	// are we edit or create?
        String contextAction = request.getParameter("ContextAction");
	logger.info("contextAction is: " + contextAction);

	if (contextAction == null)
	    contextAction = (String)request.getAttribute("ContextAction");

	Long UID = null;
	try{
	    UID = (request.getParameter("providerUID") == null
		       ? null : new Long(request.getParameter("providerUID").trim()));

	    if (UID == null)
		UID = (request.getAttribute("providerUID") == null
			   ? null
			   : new Long(((String)request.getAttribute("providerUID")).trim()));


	    logger.info("UID is: " + UID);
	}catch(java.lang.NumberFormatException e){
		logger.warn("Number Format Exception in ProviderSubmit: "+e.getMessage());
		e.printStackTrace();
	}

	/******************************************
       * CREATE A NEW ONE , ADD ACTION or EDIT ACTION current task distinguishes them
       */
	if (contextAction.equalsIgnoreCase("Submit")){
	    PersonVO personVO = null;
	    if (sCurrentTask == null){
		session.setAttribute("error",  "current task is null, required for provider submit");
		throw new ServletException("current task is null, required for provider submit");
	    }
	    else if (sCurrentTask.startsWith("EditProvider"))
	    {
              editOption = request.getParameter("tba");
              logger.debug("\n\n TBA Value =" + editOption);
              if (editOption != null) {
                if (editOption.equalsIgnoreCase("n")) {
                  personVO = createHandler(personForm, securityObj, session, request, response);
                   }
                else {
                  personVO = editHandler(personForm, securityObj, session,  request, response);
                }
              }
            }
            else if (sCurrentTask.startsWith("AddProvider")){
               personVO = createHandler(personForm, securityObj, session, request, response);
            }
	    else {
		session.setAttribute("error", "didn't find a match for current task for the submit action");
		throw new ServletException("didn't find a match for current task for the submit action");
	    }
	    try
	    {

			Collection<Object>  coll = extractLdfDataCollection(personForm, request);
              HashMap<Object,Object> map = new HashMap<Object,Object>();
              if(coll != null)
              {
               Iterator<Object>  it = coll.iterator();
                while (it.hasNext()) {
                  StateDefinedFieldDataDT stateDT = (StateDefinedFieldDataDT) it.next();
                  if (stateDT != null && stateDT.getBusinessObjNm() != null) {
                    if (stateDT.getBusinessObjNm().equalsIgnoreCase(NEDSSConstants.PROVIDER_LDF)) {
                      if(sCurrentTask.startsWith("EditProvider") && !editOption.equalsIgnoreCase("n"))
                        stateDT.setItDirty(true);
                      else
                        stateDT.setItDirty(false);
                      if(map.get(stateDT.getLdfUid())== null)
                      map.put(stateDT.getLdfUid(), stateDT);
                    }
                  }
                }
                personVO.setTheStateDefinedFieldDataDTCollection(map.values());
              }

		logger.info("Going to send personVO to EJB");
               // session.setAttribute("tempProviderVO",personVO);
		UID = sendProxyToEJB(personVO, "setProvider", session);
		PersonSummaryVO DSPersonSummary = new PersonSummaryVO();
		logger.info("LINE 150: About to set the PERSONUID on the DSPersonSummary to: " + UID);
		DSPersonSummary.setPersonUid(UID);
                request.setAttribute("providerUID",UID.toString());
                NBSContext.store(session, "DSPersonSummary", DSPersonSummary);




	    }

	    catch (NEDSSAppConcurrentDataException e)
	    {
		logger.fatal(
			"ERROR - NEDSSAppConcurrentDataException, The data has been modified by another user, please recheck! ",
			e);

		return mapping.findForward("dataerror");
	    }
            catch (NEDSSAppException nae)
            {
              logger.info(nae.getMessage());
             // if(nae.getMessage().equals("Quick Code is not unique")){
             if(nae.toString().indexOf("Quick Code is not unique") != -1)
             {
               logger.fatal("ERROR - NEDSSAppException, the quickCd is not unique for Provider ");
               request.setAttribute("err111", "Quick Code must be a unique Code");
               if(sCurrentTask.startsWith("EditProvider"))
                  request.setAttribute("SubmitStayOnError","EditProvider");
               else if(sCurrentTask.startsWith("AddProvider"))
                 request.setAttribute("SubmitStayOnError","AddProvider");
              return mapping.findForward("SubmitStayOnError");
              }
            }
	    catch (Exception e)
	    {
		logger.fatal("ERROR - General error while updating person! ", e);
		throw new ServletException("ERROR - General error while updating provider! "+e.getMessage(),e);
	    }

	    if (UID == null)

	    	throw new ServletException();

	    //reset the form
	    personForm.reset();


	}

	/*******************************************
       * EDIT ACTION
       */
	else if (contextAction.equalsIgnoreCase("Edit"))
	{
	}

	/*******************************************
       * CANCEL ACTION
       */
	else if (contextAction.equalsIgnoreCase("Cancel"))
	{
	    logger.info("You are attempting to cancel. CurrentTask is: " + sCurrentTask);
	    NBSContext.store(session, "DSFileTab", "2");
	    personForm.reset();
	}


	/*******************************************
       * ADD PERSON EXTENDED
       */
	else if (contextAction.equalsIgnoreCase("AddExtended"))
	{

	    logger.info("Value of the sCurrentTask: " + sCurrentTask);
	    logger.info("Value of the contextAction: " + contextAction);

	    PersonVO personVO = createHandler(personForm, securityObj, session,
					 request, response);
	    
	    logger.info("%%%%addresses: " + personVO.getTheEntityLocatorParticipationDTCollection().size());
	    NBSContext.store(session,"DSPerson", personVO);
	    logger.info("just set personVO attribute");
	    NBSContext.store(session, "DSFileTab", "2");
	    
	}


	/********************************************
       * DELETE ACTION
       */
	else if (contextAction.equalsIgnoreCase("Inactivate"))
	{
	    logger.debug("Beginning the Inactivate Person Functionality");

	    String result = null;

	    try
	    {
                PersonVO personVO = personForm.getProvider();
                if(personVO.getThePersonDT().getPersonUid() == null)
                  personVO.getThePersonDT().setPersonUid(UID);
		result = sendProxyToEJBDelete(personVO, "inactivateProvider",
					      session);
	    }
	    catch (NEDSSAppConcurrentDataException e)
	    {
		logger.fatal(
			"ERROR - NEDSSAppConcurrentDataException, The data has been modified by another user, please recheck! ",
			e);

		return mapping.findForward("dataerror");
	    }

	    if (result.equals("viewDelete"))
	    {

		personForm.reset();
	    }
	    else if (result.equals("deleteDenied"))
	    {
		contextAction = "DeleteDenied";
	    }
	    else
	    {
		session.setAttribute("error",
				     "unexpected return statement from inactivate person");

		throw new ServletException("unexpected return statement from inactivate person");
	    }
	}

	/*******************************************
       * ReturnToSearchResults action
       */
	else if (contextAction.equalsIgnoreCase("ReturnToSearchResults"))
	{

	}
	/*********************************************
	 * VIEW FILE
	 */
	else if (contextAction.equalsIgnoreCase("ViewFile"))
	{
	    NBSContext.store(session, "DSFileTab", "1");
	}
	request.setAttribute("ContextAction",contextAction);

	logger.info("VALUE OF PERSONUID IN PERSON SUBMIT VIEW BLOCK: " + UID);
	request.setAttribute("personUID",UID);

	return mapping.findForward(contextAction);
    }

    /** This method is called to prepare the PersonVO object
     * for creating a new person in the system.
     *
     * @param personForm CompleteDemographicForm
     * @param securityObj NBSSecurityObj
     * @param session HttpSession
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return a PersonVO
     */
    private PersonVO createHandler(ProviderForm providerForm,
				   NBSSecurityObj securityObj,
				   HttpSession session,
				   HttpServletRequest request,
				   HttpServletResponse response)
    {
    
	PersonVO personVO = null;
	try
	{
	    personVO = providerForm.getProvider();
	    personVO.setItNew(true);
	    personVO.setItDirty(false);

	    // set up the DT for the EJB
	    PersonDT personDT = personVO.getThePersonDT();
	    personDT.setItNew(true);
	    personDT.setItDirty(false);
	    personDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
	    personDT.setPersonUid(new Long(-1));
           
            personDT.setPersonParentUid(null);

            personDT.setCd("PRV");
            personDT.setElectronicInd("N");
	    personDT.setAddTime(new Timestamp(new Date().getTime()));
	    personDT.setLastChgTime(new Timestamp(new Date().getTime()));
	    personDT.setRecordStatusTime(new Timestamp(new Date().getTime()));
	    personDT.setStatusTime(new Timestamp(new Date().getTime()));
	    personDT.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
            if(providerForm.getLdfCollection()!= null)
            {

            }
	    String sCurrentTask = NBSContext.getCurrentTask(session);
	    logger.info("sCurrentTask is: " + sCurrentTask);

	    // set up the person names dt
	    logger.info("Calling setNames");
	  
            setNameCreate(personVO,request);
	    request.getParameter("ContextAction");
	    logger.info("CreateHandler sCurrentTask: " + sCurrentTask);
            setIds(personVO,providerForm);

            //Add the quick code to the Entity Id Collection

           
            setAddresses(personVO, providerForm.getAddressCollection());
            setTelephones(personVO, providerForm.getTelephoneCollection());
            setRoles(personVO, request);
	}
	catch (Exception e)
	{
	    e.printStackTrace();
	}

	return personVO;
    }

    /**
     * This method prepares the PersonVO before an edit
     * occurs to person data.
     *
     * @param personForm CompleteDemographicForm
     * @param securityObj NBSSecurityObj
     * @param session HttpSession
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return a PersonVO that is ready to be sent to the EJB for updating.
     */
    private PersonVO editHandler(ProviderForm providerForm,
				 NBSSecurityObj securityObj,
				 HttpSession session,
				 HttpServletRequest request,
				 HttpServletResponse response)
    {
    	
	PersonVO personVO = null;

	try
	{
	    personVO = providerForm.getProvider();
            personVO.setItDirty(true);
            personVO.setItNew(false);
	  
            setNameEdit(personVO, request);
            

	    setIds(personVO,providerForm);
	    setAddresses(personVO, providerForm.getAddressCollection());
	    setTelephones(personVO, providerForm.getTelephoneCollection());
	    setRoles(personVO, request);
	}
	catch (Exception e)
	{
	    e.printStackTrace();
	}

	return personVO;
    }

   private void setNameCreate(PersonVO personVO, HttpServletRequest request){
	  
     Long personUID = personVO.getThePersonDT().getPersonUid();
     String lastNm = request.getParameter("provider.lastNm");
            String firstNm = request.getParameter("provider.firstNm");
            String middleNm = request.getParameter("provider.middleNm");
            String nmSuffix = request.getParameter("provider.nmSuffix");
            String nmPrefix = request.getParameter("provider.nmPrefix");
            String nmDegree = request.getParameter("provider.nmDegree");
            if((lastNm!=null && !lastNm.trim().equals("")) ||
                (firstNm!=null && !firstNm.trim().equals("")) ||
                (middleNm!=null && !middleNm.trim().equals("")))
            {
                PersonNameDT pdt = new PersonNameDT();
                pdt.setItNew(true);
                pdt.setItDirty(false);
                pdt.setNmUseCd(NEDSSConstants.LEGAL);
                pdt.setPersonNameSeq(new Integer(1));
                pdt.setStatusTime(new Timestamp(new Date().getTime()));
                pdt.setAddTime(new Timestamp(new Date().getTime()));
                pdt.setRecordStatusTime(new Timestamp(new Date().getTime()));
                pdt.setPersonUid(personUID);
                pdt.setRecordStatusCd(NEDSSConstants.ACTIVE);
                pdt.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
                pdt.setLastNm(lastNm);
                pdt.setFirstNm(firstNm);
                pdt.setMiddleNm(middleNm);
                pdt.setNmDegree(nmDegree);
                pdt.setNmPrefix(nmPrefix);
                pdt.setNmSuffix(nmSuffix);
                Collection<Object>  pdts = new ArrayList<Object> ();
                pdts.add(pdt);
                personVO.setThePersonNameDTCollection(pdts);
            }
        }



    private void setNameEdit(PersonVO personVO, HttpServletRequest request) {

      Long providerUID = personVO.getThePersonDT().getPersonUid();
      String lastNm = request.getParameter("provider.lastNm");
      String firstNm = request.getParameter("provider.firstNm");
      String middleNm = request.getParameter("provider.middleNm");
      String nmSuffix = request.getParameter("provider.nmSuffix");
      String nmPrefix = request.getParameter("provider.nmPrefix");
      String nmDegree = request.getParameter("provider.nmDegree");

      ArrayList<Object> arrName = (ArrayList<Object> ) personVO.getThePersonNameDTCollection();

      if (arrName == null||arrName.isEmpty()) {
        arrName = new ArrayList<Object> ();
        PersonNameDT pdt = new PersonNameDT();
        pdt.setItNew(false);
        pdt.setItDirty(true);
        pdt.setRecordStatusCd(NEDSSConstants.ACTIVE);
        pdt.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
        pdt.setPersonNameSeq(new Integer(1));
        pdt.setLastNm(lastNm);
        pdt.setFirstNm(firstNm);
        pdt.setMiddleNm(middleNm);
        pdt.setNmDegree(nmDegree);
        pdt.setNmPrefix(nmPrefix);
        pdt.setNmSuffix(nmSuffix);
        pdt.setPersonUid(providerUID);
        pdt.setNmUseCd(NEDSSConstants.LEGAL);
        arrName.add(pdt);
        personVO.setThePersonNameDTCollection(arrName);

      }
      else{
       Iterator<Object>  nmiter = arrName.iterator();
        while(nmiter.hasNext()){
          PersonNameDT pdt = (PersonNameDT)nmiter.next();
          if(pdt.getNmUseCd().equals(NEDSSConstants.LEGAL)){
                pdt.setItNew(false);
                pdt.setItDirty(true);
                pdt.setLastNm(lastNm);
                pdt.setFirstNm(firstNm);
                pdt.setMiddleNm(middleNm);
                pdt.setNmUseCd(NEDSSConstants.LEGAL);
                pdt.setRecordStatusCd(NEDSSConstants.ACTIVE);
                pdt.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
                pdt.setNmDegree(nmDegree);
                pdt.setNmPrefix(nmPrefix);
                pdt.setNmSuffix(nmSuffix);
                pdt.setPersonUid(providerUID);
                break;
          }
        }
      }
    }






    /**
     * This method will handle adding the person's ID's
     * to the PersonVO.
     *
     * @param personVO PersonVO
     */
    private void setIds(PersonVO personVO, ProviderForm providerForm)
    {

	Collection<Object>  ids = personVO.getTheEntityIdDTCollection();
	Long personUID = personVO.getThePersonDT().getPersonUid();
     boolean isQuickCode = false;
     String quickCode = null;
     if(providerForm.getQuickCodeIdDT()!=null)
        quickCode = providerForm.getQuickCodeIdDT().getRootExtensionTxt();

	if (ids != null)
	{

	   Iterator<Object>  itrCount = ids.iterator();

	    //need to find the max seq nbr for existing names
	    Integer maxSeqNbr = new Integer(0);

	    while (itrCount.hasNext())
	    {

		EntityIdDT idDT = (EntityIdDT)itrCount.next();

		if (idDT.getEntityIdSeq() != null && idDT.getEntityIdSeq().longValue()>0)
		{

		    if (idDT.getEntityIdSeq().compareTo(maxSeqNbr) > 0) // update the maxSeqNbr when you find a bigger one
			maxSeqNbr = idDT.getEntityIdSeq();
		}
	    }

	   Iterator<Object>  itrIds = ids.iterator();

	    while (itrIds.hasNext())
	    {

		EntityIdDT id = (EntityIdDT)itrIds.next();

		if (id.getEntityUid() == null || id.getEntityUid().longValue()==0) // this is a new one
		{
		    maxSeqNbr = new Integer(maxSeqNbr.intValue() + 1);
		    id.setEntityIdSeq(maxSeqNbr);

		   
		    if (id.getStatusCd() != null &&
			id.getStatusCd().equals(
				NEDSSConstants.STATUS_ACTIVE))
			id.setRecordStatusCd(
				NEDSSConstants.RECORD_STATUS_ACTIVE);
		    else
			id.setRecordStatusCd(
				NEDSSConstants.RECORD_STATUS_INACTIVE);

		    id.setItNew(true);
		    id.setItDirty(false);
		    id.setAddTime(new Timestamp(new Date().getTime()));
		    id.setEntityUid(personUID);
		}
          else { //this is old one
               //check if ssn exists in the collection already
               if (id.getTypeCd() != null && id.getTypeCd().equals(NEDSSConstants.ENTITY_TYPECD_QEC)) {
                  isQuickCode = true;
                  id.setRootExtensionTxt(quickCode);
                  id.setItNew(false);
                  id.setItDirty(true);
                  id.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
                  id.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
               }
               else if (id.getStatusCd() != null &&
                        id.getStatusCd().equals(NEDSSConstants.STATUS_ACTIVE)) {
                  id.setItNew(false);
                  id.setItDirty(true);
                  id.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);

               }
               else {
                  id.setItNew(false);
                  id.setItDelete(true);
                  id.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_INACTIVE);
               }

            }
            id.setLastChgTime(new Timestamp(new Date().getTime()));
         }
         if (isQuickCode == false && quickCode != null && !quickCode.trim().equals("")) {
            EntityIdDT iddt = null;
            iddt = new EntityIdDT();
            maxSeqNbr = new Integer(maxSeqNbr.intValue() + 1);
            iddt.setEntityIdSeq(maxSeqNbr);
            iddt.setAddTime(new Timestamp(new Date().getTime()));
            iddt.setLastChgTime(new Timestamp(new Date().getTime()));
            iddt.setRecordStatusTime(new Timestamp(new Date().getTime()));
            iddt.setStatusTime(new Timestamp(new Date().getTime()));
            iddt.setEntityUid(personUID);
            iddt.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
            iddt.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
            iddt.setTypeCd(NEDSSConstants.ENTITY_TYPECD_QEC);
            iddt.setTypeDescTxt(NEDSSConstants.ENTITY_TYPE_DESC_TXT_QEC);
            iddt.setRootExtensionTxt(quickCode);
            ids.add(iddt);
         }

       }
       else if (quickCode != null && !quickCode.trim().equals("")) {
        EntityIdDT iddt = null;
        iddt = new EntityIdDT();
        iddt.setEntityIdSeq(new Integer(0));
        iddt.setAddTime(new Timestamp(new Date().getTime()));
        iddt.setLastChgTime(new Timestamp(new Date().getTime()));
        iddt.setRecordStatusTime(new Timestamp(new Date().getTime()));
        iddt.setStatusTime(new Timestamp(new Date().getTime()));
        iddt.setEntityUid(personUID);
        iddt.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
        iddt.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
        iddt.setTypeCd(NEDSSConstants.ENTITY_TYPECD_QEC);
        iddt.setTypeDescTxt(NEDSSConstants.ENTITY_TYPE_DESC_TXT_QEC);
        iddt.setRootExtensionTxt(quickCode);
        ArrayList<Object> idList = new ArrayList<Object> ();
        idList.add(iddt);
        personVO.setTheEntityIdDTCollection(idList);
     }
	
    }



    private void setAddresses(PersonVO personVO, ArrayList<Object> addressList)
    {

	Long personUID = personVO.getThePersonDT().getPersonUid();

	if (addressList != null)
	{

	   Iterator<Object>  itrAddress = addressList.iterator();
	    ArrayList<Object> arrELP = (ArrayList<Object> )personVO.getTheEntityLocatorParticipationDTCollection();

	    if (arrELP == null)
		arrELP = new ArrayList<Object> ();

	    while (itrAddress.hasNext())
	    {

		EntityLocatorParticipationDT elp = (EntityLocatorParticipationDT)itrAddress.next();

		if (elp.getLocatorUid() == null || elp.getLocatorUid().longValue()==0)
		{
		    elp.setItNew(true);
		    elp.setItDirty(false);
		    elp.getThePostalLocatorDT().setItNew(true);
		    elp.getThePostalLocatorDT().setItDirty(false);
		    elp.setEntityUid(personUID);

		    
		    if (elp.getStatusCd() != null &&
			elp.getStatusCd().equals(
				NEDSSConstants.STATUS_ACTIVE))
			elp.setRecordStatusCd(
				NEDSSConstants.RECORD_STATUS_ACTIVE);
		    else
			elp.setRecordStatusCd(
				NEDSSConstants.RECORD_STATUS_INACTIVE);

		    arrELP.add(elp);
		}
		else
		{
		    elp.setItNew(false);
		    elp.setItDirty(true);
		    elp.getThePostalLocatorDT().setItNew(false);
		    elp.getThePostalLocatorDT().setItDirty(true);

		    if (elp.getStatusCd() != null &&
			elp.getStatusCd().equals(
				NEDSSConstants.STATUS_ACTIVE))
			elp.setRecordStatusCd(
				NEDSSConstants.RECORD_STATUS_ACTIVE);
		    else
			elp.setRecordStatusCd(
				NEDSSConstants.RECORD_STATUS_INACTIVE);

		    arrELP.add(elp);
		}
	    }

	    personVO.setTheEntityLocatorParticipationDTCollection(arrELP);
	}
    }

    /**
     * This method will set the person's telephone
     * numbers to the PersonVO.
     *
     * @param personVO PersonVO
     * @param telephoneList ArrayList
     */
    private void setTelephones(PersonVO personVO, ArrayList<Object> telephoneList)
    {
    	
	if (telephoneList != null)
	{

	    Long personUID = personVO.getThePersonDT().getPersonUid();
	   Iterator<Object>  itr = telephoneList.iterator();
	    ArrayList<Object> arrELP = (ArrayList<Object> )personVO.getTheEntityLocatorParticipationDTCollection();

	    if (arrELP == null)
		arrELP = new ArrayList<Object> ();

	    while (itr.hasNext())
	    {

		EntityLocatorParticipationDT elp = (EntityLocatorParticipationDT)itr.next();

		if (elp.getLocatorUid() == null ||  elp.getLocatorUid().longValue()==0) // new one
		{
		    elp.setItNew(true);
		    elp.setItDirty(false);
		    elp.getTheTeleLocatorDT().setItNew(true);
		    elp.getTheTeleLocatorDT().setItDirty(false);
		    elp.setEntityUid(personUID);

		    if (elp.getStatusCd() != null &&
			elp.getStatusCd().equals(
				NEDSSConstants.STATUS_ACTIVE))
			elp.setRecordStatusCd(
				NEDSSConstants.RECORD_STATUS_ACTIVE);
		    else
			elp.setRecordStatusCd(
				NEDSSConstants.RECORD_STATUS_INACTIVE);

		    //elp.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
		    arrELP.add(elp);
		}
		else
		{
		    elp.setItNew(false);
		    elp.setItDirty(true);
		    elp.getTheTeleLocatorDT().setItNew(false);
		    elp.getTheTeleLocatorDT().setItDirty(true);

		    if (elp.getStatusCd() != null &&
			elp.getStatusCd().equals(
				NEDSSConstants.STATUS_ACTIVE))
			elp.setRecordStatusCd(
				NEDSSConstants.RECORD_STATUS_ACTIVE);
		    else
			elp.setRecordStatusCd(
				NEDSSConstants.RECORD_STATUS_INACTIVE);

		    arrELP.add(elp);
		}
	    }

	    personVO.setTheEntityLocatorParticipationDTCollection(arrELP);
	}
    }

    private Integer getNextSeqNbrForEntityIdCollection(Collection<Object> coll){
   
      Integer maxSeqNbr = new Integer(0);
      if (coll != null){
       Iterator<Object>  itrCount = coll.iterator();

        //need to find the max seq nbr for existing names


        while (itrCount.hasNext())
        {

          EntityIdDT idDT = (EntityIdDT)itrCount.next();

          if (idDT.getEntityIdSeq() != null)
          {

            if (idDT.getEntityIdSeq().compareTo(maxSeqNbr) > 0) // update the maxSeqNbr when you find a bigger one
              maxSeqNbr = idDT.getEntityIdSeq();
          }

        }

      }
      maxSeqNbr = new Integer(maxSeqNbr.intValue() + 1);
      return maxSeqNbr;
    }

    /**
     * This method is used to set the Role data onto
     * the PersonVO.
     *
     * @param personVO PersonVO
     * @param request HttpServletRequest
     */
    private void setRoles(PersonVO personVO, HttpServletRequest request)
    {
	HttpSession session = request.getSession();
	Object obj = session.getAttribute("NBSSecurityObject");

	if (obj != null) {
	}


	Long personUID = personVO.getThePersonDT().getPersonUid();
	String[] arrRoles = request.getParameterValues("rolesList");
	Long maxSeqNbr = new Long(0);
        ArrayList<Object> roleList = new ArrayList<Object> ();

       	Collection<Object>  roleColl = personVO.getTheRoleDTCollection();

	    if (roleColl != null)
	    {
		Iterator<Object> iter = roleColl.iterator();
		if (iter != null)
		{
		    while (iter.hasNext())
		    {
			RoleDT currRoleDT = (RoleDT)iter.next();
			if (currRoleDT != null)
			{
			  logger.info("ROLE COLLECTION WASN'T NULL.  SETTING FOR DELETE");
			  currRoleDT.setItNew(false);
			  currRoleDT.setItDelete(true);
			  currRoleDT.setItDirty(false);
			  roleList.add(currRoleDT);

			}
		    }
		}
	    }


	//    if (personVO.getTheRoleDTCollection()==null){  // null if this is new

	if (arrRoles != null)
	{
	    for (int i = 0, len = arrRoles.length; i < len; i++)
	    {
		String strVal = arrRoles[i];
		RoleDT roleDT = new RoleDT();
		if(strVal!=null && !strVal.equals("")){
		    roleDT.setItNew(true);
		    roleDT.setItDelete(false);
		    roleDT.setItDirty(false);
		    roleDT.setRecordStatusCd(
			    NEDSSConstants.RECORD_STATUS_ACTIVE);
		    roleDT.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
		    roleDT.setSubjectEntityUid(personUID);
		    roleDT.setSubjectClassCd(NEDSSConstants.PROV);
		    roleDT.setCd(strVal);
		    roleDT.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
		    roleDT.setStatusTime(new Timestamp(new Date().getTime()));
		    roleDT.setAddUserId(USERID);
		    roleDT.setLastChgUserId(USERID);
		    maxSeqNbr = new Long(maxSeqNbr.intValue() + 1);
		    roleDT.setRoleSeq(maxSeqNbr);
		    roleList.add(roleDT);
		}
	    }
	}

	personVO.setTheRoleDTCollection(roleList);

    }

    /**
     * This method is to take a PersonVO and send it to
     * the EJB for persistence to the database.
     *
     * @param person PersonVO
     * @param paramMethodName String
     * @param session HttpSession
     * @exception NEDSSAppConcurrentDataException
     * @exception NEDSSAppException
     * @exception javax.ejb.EJBException
     * @exception Exception
     * @return a Long
     */
    private Long sendProxyToEJB(PersonVO person, String paramMethodName,
				HttpSession session)
			 throws NEDSSAppConcurrentDataException,
				NEDSSAppException, javax.ejb.EJBException,
				Exception
    {
    	
	logger.debug("address or telephone: " + person.getTheEntityLocatorParticipationDTCollection());
        logger.info("You are inside sendProxyToEJB method of PersonSubmit");
	/**
       * Call the mainsessioncommand
       */
	MainSessionCommand msCommand = null;


	String sBeanJndiName = JNDINames.ENTITY_PROXY_EJB;
	String sMethod = paramMethodName;


	Object temp = null;

	if (paramMethodName.equals("setProvider"))
	    temp = person;
	else if (paramMethodName.equals("deletePerson"))
	    temp = person.getThePersonDT().getPersonUid();

	Object[] oParams = { temp };

	MainSessionHolder holder = new MainSessionHolder();
	msCommand = holder.getMainSessionCommand(session);
	ArrayList<?> resultUIDArr = new ArrayList<Object> ();
	resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod,
						oParams);

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


    /**
     * This method is used to send a PersonVO to
     * the EJB for deletion.
     *
     * @param personVO PersonVO
     * @param paramMethodName String
     * @param session HttpSession
     * @exception NEDSSAppConcurrentDataException
     * @return a String
     */
    private String sendProxyToEJBDelete(PersonVO personVO,
					String paramMethodName,
					HttpSession session)
				 throws NEDSSAppConcurrentDataException
    {
    	
	/**
       * Call the mainsessioncommand
       */
	MainSessionCommand msCommand = null;

	try
	{

	    String sBeanJndiName = JNDINames.ENTITY_PROXY_EJB;
	    String sMethod = paramMethodName;
	    Object[] oParams = { personVO };


	    MainSessionHolder holder = new MainSessionHolder();
	    msCommand = holder.getMainSessionCommand(session);

	    
	    ArrayList<?> resultArr = new ArrayList<Object> ();
	    resultArr = msCommand.processRequest(sBeanJndiName, sMethod,
						 oParams);
	    logger.debug("value of resultArr1 " + resultArr);

	    boolean result;
	    String deleteFlag = "";

	    if ((resultArr != null) && (resultArr.size() > 0))
	    {
		logger.info("Delete person = " + resultArr.get(0));
		result = ((Boolean)resultArr.get(0)).booleanValue();

		if (result)
		{
		    deleteFlag = "viewDelete";
		}
		else
		{
		    deleteFlag = "deleteDenied";
		}

		return deleteFlag;
	    }
	    else
	    {
		deleteFlag = "error";

		return deleteFlag;
	    }
	}
	catch(NEDSSAppConcurrentDataException ncde)
	{
	    ncde.printStackTrace();
	    logger.fatal("Error: Could not delete record because of data concurrency.");
	    throw new NEDSSAppConcurrentDataException();

	}
	catch (Exception e)
	{
	    e.printStackTrace();

	    if (session == null)
	    {
		logger.error("Error: no session, please login");
		e.printStackTrace();

		return null;
	    }

	    logger.fatal("ERROR in ProviderSubmit Delete calling mainsession control", e);
	    e.printStackTrace();
	    return null;
	}
    }
}
