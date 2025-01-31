package gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.common;


import gov.cdc.nedss.act.intervention.dt.InterventionDT;
import gov.cdc.nedss.act.intervention.vo.InterventionVO;
import gov.cdc.nedss.act.interview.dt.InterviewDT;
import gov.cdc.nedss.act.interview.dt.InterviewSummaryDT;
import gov.cdc.nedss.act.interview.vo.InterviewVO;
import gov.cdc.nedss.association.dt.ParticipationDT;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.exception.NEDSSAppConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.nbsactentity.dt.NbsActEntityDT;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.PageProxyVO;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.act.PageActProxyVO;
import gov.cdc.nedss.pagemanagement.wa.dt.WaTemplateDT;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.CoinfectionSummaryVO;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.nbssecurity.ProgramAreaVO;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.StringUtils;
import gov.cdc.nedss.webapp.nbs.action.client.ClientVO;
import gov.cdc.nedss.webapp.nbs.action.contacttracing.util.CTConstants;
import gov.cdc.nedss.webapp.nbs.action.page.clientvo.PageClientVO;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageConstants;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageLoadUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.dynamicbinding.DynamicBeanBinding;
import gov.cdc.nedss.webapp.nbs.action.util.CallProxyEJB;
import gov.cdc.nedss.webapp.nbs.form.page.PageSubForm;
import gov.cdc.nedss.webapp.nbs.form.util.BaseForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.QuestionsCache;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * PageManagementCommonActionUtil - Methods for action classes that can be used
 * across business object types (INV, CON, ITX)
 *Update 6.0:- To include the logic for Lab objects and correct logic for vaccination. Lab Objects do not have condition code logic(like Intervention). Code updated 
 *to check for  busObjType for Lab and Vaccination, instead of its existence in NBSContext(to avoid try/catch for a variable as Exception)
 *Update 6.0:- setTheRenderDirectory method updated to remove redundant code.
 * * @author Gregory Tucker, Pradeep Sharma
 *         <p>
 *         Copyright: Copyright (c) 2013
 *         </p>
 *         <p>
 *         Company: Leidos
 *         </p>
 *         </p>
 *@version NBS 6.0  
 *         
 */

public class PageManagementCommonSubFormActionUtil
{

    static final LogUtils logger = new LogUtils(PageManagementCommonSubFormActionUtil.class.getName());
    public static final String PATIENT_SUBJECT_CLASS_CD = "PAT";
    /**
     * This method retrieves the wa_template and returns the form code for the
     * condition and the business object type if the template type is
     * 'Published'. Otherwise it returns an empty string
     * 
     * @param request
     * @param busObjType for vaccination and lab and NBSContext for all others(INV, CON, or IXS for Interview)
     * @return string formCd
     */
    public static String checkIfPublishedPageExists(HttpServletRequest request, String busObjType)
    {
        String conditionCd = "";
        String formCd = ""; // i.e. CT_Contact_Std
        
        if (NEDSSConstants.VACCINATION_BUSINESS_OBJECT_TYPE.equals(busObjType)
				|| NEDSSConstants.LAB_BUSINESS_OBJECT_TYPE.equals(busObjType)
				|| NEDSSConstants.ISOLATE_BUSINESS_OBJECT_TYPE
						.equals(busObjType)
				|| NEDSSConstants.SUSCEPTIBILITY_BUSINESS_OBJECT_TYPE
						.equals(busObjType)) {
			try {
				logger.warn("Condition not found in checkIfPublishedPageExists() ");
				WaTemplateDT waTemplateDT = findPublishedPageByBusinessObjType(
						busObjType, request);
				if (waTemplateDT != null)
					formCd = waTemplateDT.getFormCd();
				return formCd;
			} catch (Exception ex) {
				logger.error("busObjType: " + busObjType);
				logger.error("Exception thrown in checkIfPublishedPageExists while checking findPublishedPageByBusinessObjType: "
						+ ex.getMessage());
			}
		} else//For all other objects, the condition code must exist(contact record, Investigation, Interview)
		{
	        try
	        {
	            HttpSession session = request.getSession(true);
	           
	            conditionCd = (String) NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationCondition);
	        }
	        catch (Exception e)
	        {
	            logger.error("Error in getting the condition in checkIfPublishedPageExists " + e.getMessage());
	        }
	               
        }
        return (checkIfPublishedPageExists(request, busObjType, conditionCd));
    }
    
	public static String checkIfPublishedPageExists(
			HttpServletRequest request,
			String busObjType, 
			String conditionCd) {
		  String formCd = null;
	      WaTemplateDT waTemplateDT = new WaTemplateDT();
	        try
	        {
	            HttpSession session = request.getSession(true);
	            waTemplateDT = getWaTemplateByCondTypeAndBusObj(conditionCd, NEDSSConstants.PUBLISHED, busObjType, session);
	        }
	        catch (Exception e)
	        {
	            logger.error("checkIfPublishedPageExists: Error in getting the waTemplateByCondTypeAndBusObj "
	                    + e.getMessage());
	        }
	        // if we have a published Contact Page for the Condition return the form
	        // code
	        if (waTemplateDT.getFormCd() != null)
	            return waTemplateDT.getFormCd();

	        return formCd; // blank

	}

    public static WaTemplateDT getWaTemplateByCondTypeAndBusObj(String conditionCd, String templateType, String busObj,
            HttpSession session) throws Exception
    {

        MainSessionCommand msCommand = null;
        WaTemplateDT waTemplateDT = new WaTemplateDT();
        try
        {
            String sBeanJndiName = JNDINames.PAGE_MANAGEMENT_PROXY_EJB;
            String sMethod = "getWaTemplateByCondTypeBusObj";
            Object[] oParams = new Object[] { conditionCd, templateType, busObj };
            MainSessionHolder holder = new MainSessionHolder();
            msCommand = holder.getMainSessionCommand(session);
            ArrayList<?> aList = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
            if (aList != null && aList.size() > 0)
            {
                waTemplateDT = (WaTemplateDT) aList.get(0);

            }
        }
        catch (Exception ex)
        {
            logger.fatal("Error in getWaTemplateByCondTypeAndBusObj in Page Mgt Common Action Util: "+ex.getMessage(), ex);
            throw new Exception(ex.getMessage(), ex);
        }
        return waTemplateDT;
    }

    /**
     * Contact variable is name differently as this is name differently on the JSP. 
     * @param request
     * @param pageFormCd
     * @param busObjType
     */
    public static void setTheRenderDirectory(HttpServletRequest request, String pageFormCd, String busObjType)
    {
        String renderDir = pageFormCd;
        if (busObjType.equalsIgnoreCase(NEDSSConstants.CONTACT_BUSINESS_OBJECT_TYPE))
            request.setAttribute("renderContactDir", renderDir);
        else {
            request.setAttribute("renderDir", renderDir);
        }
    }

    /**
     * This method builds the delimited string of Program Area Jurisdiction It
     * puts the string in AttributeMap key NBSSecurityJurisdictions for use by
     * the JS
     * 
     * @param BaseForm
     * @param busObj
     *            from NBSBOLookup.CT_CONTACT or INVESTIGATION
     * @param conditionCd
     * @param nbsSecurityObj
     */
    public static void setJurisdiction(BaseForm form, String busObj, String programAreaCd, String conditionCd,
            NBSSecurityObj nbsSecurityObj) throws Exception
    {

        try
        {
            CachedDropDownValues cdv = new CachedDropDownValues();

            ProgramAreaVO programAreaVO = cdv.getProgramAreaCondition("('" + programAreaCd + "')", conditionCd);
            if (programAreaVO == null) // level 2 condition for Hepatitis
                                       // Diagnosis
                programAreaVO = cdv.getProgramAreaCondition("('" + programAreaCd + "')", 2, conditionCd);

            String programAreaJurisdictions = nbsSecurityObj.getProgramAreaJurisdictions(busObj,
                    NBSOperationLookup.VIEW);
            StringBuffer sb = new StringBuffer();

            if (programAreaJurisdictions != null && programAreaJurisdictions.length() > 0)
            {

                StringTokenizer st = new StringTokenizer(programAreaJurisdictions, "|");
                while (st.hasMoreTokens())
                {

                    String token = st.nextToken();
                    if (token.lastIndexOf("$") >= 0)
                    {

                        String programArea = token.substring(0, token.lastIndexOf("$"));
                        if (programArea != null && programArea.equals(programAreaVO.getStateProgAreaCode()))
                        {

                            String juris = token.substring(token.lastIndexOf("$") + 1);
                            sb.append(juris).append("|");
                        }
                    }
                }
                form.getAttributeMap().put("NBSSecurityJurisdictions", sb.toString());
            }
        }
        catch (Exception e)
        {
            logger.fatal("PageLoadUtil.setJurisdiction Exception thrown:" + e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }

    }

    /**
     * This user currently logged on can be associated with a Provider in the
     * security setup Use this provider as the default
     * 
     * @param request
     * @param BaseForm
     * @return personVO (Default Investigator)
     */
    public static PersonVO getDefaultInvestigatorIfPresent(HttpSession session, NBSSecurityObj nbsSecurityObj)
            throws Exception
    {
        logger.debug("getDefaultInvestigatorIfPresent: begin processing..");
        MainSessionCommand msCommand = null;
        PersonVO personVO = null;
        if (nbsSecurityObj == null || nbsSecurityObj.getTheUserProfile().getTheUser().getProviderUid() == null)
            return personVO;

        try
        {
            Long providerUID = nbsSecurityObj.getTheUserProfile().getTheUser().getProviderUid();
            String sBeanJndiName = JNDINames.ENTITY_PROXY_EJB;
            String sMethod = "getProvider";
            Object[] oParams = new Object[] { providerUID };

            MainSessionHolder holder = new MainSessionHolder();
            msCommand = holder.getMainSessionCommand(session);
            ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
            personVO = (PersonVO) arr.get(0);
        }
        catch (NumberFormatException e)
        {
            logger.error("Error in getDefaultInvestigatorIfPresent: provider UID format wrong? "+e.getMessage(), e);
            return personVO;
        }
        catch (Exception ex)
        {
            logger.error("Error in getDefaultInvestigatorIfPresent() getting provider: "+ex.getMessage(), ex);
            return personVO;
        }
        finally
        {
            msCommand = null;
        }
        return personVO;

    }

    /**
     * See if the Investigator is present in the session, if not, use the
     * provider associated with the current user. ToDo : rework this and above
     * -to remove redundancy
     * 
     * @param request
     * @param BaseForm
     * @return personVO (Default Investigator)
     */
    public static PersonVO getInvestigatorIfPresent(HttpSession session, NBSSecurityObj nbsSecurityObj)
            throws Exception
    {
        logger.debug("getInvestigatorIfPresent: begin processing..");
        MainSessionCommand msCommand = null;
        PersonVO personVO = null;
        Long providerUid = null;
        try{
	        String investigatorUidStr = (String) NBSContext.retrieve(session, NBSConstantUtil.DSInvestigatorUid);
	        if (investigatorUidStr != null && !investigatorUidStr.isEmpty())
	            providerUid = new Long(investigatorUidStr);
        }catch(NullPointerException npEx){
        	logger.debug("DSInvestigatorUid not found from session.");
        }
        
        // if null get the current userId as provider
        if (providerUid == null)
            return getDefaultInvestigatorIfPresent(session, nbsSecurityObj);

        try
        {
            String sBeanJndiName = JNDINames.ENTITY_PROXY_EJB;
            String sMethod = "getProvider";
            Object[] oParams = new Object[] { providerUid };

            MainSessionHolder holder = new MainSessionHolder();
            msCommand = holder.getMainSessionCommand(session);
            ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
            personVO = (PersonVO) arr.get(0);
        }
        catch (NumberFormatException e)
        {
            logger.error("Error in getInvestigatorIfPresent: provider UID format wrong?"+e.getMessage(), e);
            return personVO;
        }
        catch (Exception ex)
        {
            logger.error("Error in getInvestigatorIfPresent() getting provider"+ex.getMessage(), ex);
            return personVO;
        }
        finally
        {
            msCommand = null;
        }
        return personVO;

    }


    /**
     * setInterviewInformationOnForm - put the information from the InterviewDT
     * into the answer map
     * 
     * @param : form
     * @param : proxyVO with Interview DT populated
     */
    public static void setInterviewInformationOnForm(PageSubForm form, PageProxyVO proxyVO) throws NEDSSAppException
    {
        logger.debug("setInterviewInformationOnForm: begin processing..");
        try
        {
            InterviewDT ixsDT = ((PageActProxyVO) proxyVO).getInterviewVO().getTheInterviewDT();
            PageClientVO clientVO = form.getPageClientVO();
            if (ixsDT.getInterviewStatusCd() != null)
                clientVO.setAnswer(PageConstants.INTERVIEW_STATUS, ixsDT.getInterviewStatusCd());
            if (ixsDT.getInterviewDate() != null)
                clientVO.setAnswer(PageConstants.INTERVIEW_DATE, StringUtils.formatDate(ixsDT.getInterviewDate()));
            if (ixsDT.getIntervieweeRoleCd() != null)
                clientVO.setAnswer(PageConstants.INTERVIEWEE_ROLE, ixsDT.getIntervieweeRoleCd());
            if (ixsDT.getInterviewTypeCd() != null)
                clientVO.setAnswer(PageConstants.INTERVIEW_TYPE, ixsDT.getInterviewTypeCd());
            if (ixsDT.getInterviewLocCd() != null)
                clientVO.setAnswer(PageConstants.INTERVIEW_LOCATION, ixsDT.getInterviewLocCd());

        }
        catch (Exception e)
        {
            logger.fatal("PageLoadUtil.setInterviewInformationOnForm :Exception thrown " + e.getMessage(), e);
            throw new NEDSSAppException(e.getMessage(), e);
        }
    }

    /**
     * Set Interview Information From the Form for a Create or Edit submit- Set
     * info from the answerMap into the InterviewDT
     * 
     * @param : form
     * @param : proxyVO
     * @param : userId - current user
     */
    private static void setInterviewFromCreateEditSubmit(PageSubForm form, PageActProxyVO proxyVO, String userId)
            throws NEDSSAppException
    {
        logger.debug("setInterviewFromEdit: begin processing..");
        try
        {
            Map<Object, Object> answerMap = form.getPageClientVO().getAnswerMap();
            InterviewVO ixsVO = new InterviewVO();
            InterviewDT interviewDT = new InterviewDT();
            ixsVO.setTheInterviewDT(interviewDT);

            // set some values from the oldInterviewDT such as addTime and
            // versionNbr if not a create
            if ((form.getPageClientVO().getOldPageProxyVO() != null)
                    && (form.getPageClientVO().getOldPageProxyVO().getInterviewVO() != null))
            {
                ixsVO.setItNew(false);
                ixsVO.setItDirty(true);
                interviewDT.setItDirty(true);
                interviewDT.setItNew(false);
                InterviewVO oldInterviewVO = form.getPageClientVO().getOldPageProxyVO().getInterviewVO();
                if (oldInterviewVO.getTheInterviewDT().getInterviewUid() != null)
                    interviewDT.setInterviewUid(oldInterviewVO.getTheInterviewDT().getInterviewUid());
                if (oldInterviewVO.getTheInterviewDT().getAddTime() != null)
                    interviewDT.setAddTime(oldInterviewVO.getTheInterviewDT().getAddTime());
                if (oldInterviewVO.getTheInterviewDT().getAddUserId() != null)
                    interviewDT.setAddUserId(oldInterviewVO.getTheInterviewDT().getAddUserId());
                if (oldInterviewVO.getTheInterviewDT().getLocalId() != null)
                    interviewDT.setLocalId(oldInterviewVO.getTheInterviewDT().getLocalId());
                if (oldInterviewVO.getTheInterviewDT().getVersionCtrlNbr() != null)
                    interviewDT.setVersionCtrlNbr(oldInterviewVO.getTheInterviewDT().getVersionCtrlNbr());
            }
            else
            { // is a create
                ixsVO.getTheInterviewDT().setInterviewUid(new Long(-1));
                ixsVO.getTheInterviewDT().setVersionCtrlNbr(1);
                ixsVO.getTheInterviewDT().setAddUserId(Long.valueOf(userId));
                ixsVO.getTheInterviewDT().setAddTime(new Timestamp(new Date().getTime()));
                ixsVO.getTheInterviewDT().setItNew(true);
                ixsVO.getTheInterviewDT().setItDirty(false);
                ixsVO.setItNew(true);
                ixsVO.setItDirty(false);
            }

            ixsVO.getTheInterviewDT().setInterviewStatusCd(getVal(answerMap.get(PageConstants.INTERVIEW_STATUS)));
            String ixsDateStr = (String) answerMap.get(PageConstants.INTERVIEW_DATE);
            if (ixsDateStr != null)
                ixsVO.getTheInterviewDT().setInterviewDate(StringUtils.stringToStrutsTimestamp(ixsDateStr));
            ixsVO.getTheInterviewDT().setIntervieweeRoleCd(getVal(answerMap.get(PageConstants.INTERVIEWEE_ROLE)));
            ixsVO.getTheInterviewDT().setInterviewTypeCd(getVal(answerMap.get(PageConstants.INTERVIEW_TYPE)));
            ixsVO.getTheInterviewDT().setInterviewLocCd(getVal(answerMap.get(PageConstants.INTERVIEW_LOCATION)));

            ixsVO.getTheInterviewDT().setLastChgUserId(Long.valueOf(userId));
            ixsVO.getTheInterviewDT().setLastChgTime(new Timestamp(new Date().getTime()));
            ixsVO.getTheInterviewDT().setRecordStatusCd(NEDSSConstants.ACTIVE);
            ixsVO.getTheInterviewDT().setRecordStatusTime(new Timestamp(new Date().getTime()));
            proxyVO.setInterviewVO(ixsVO); // set the interview VO into the
                                           // proxy
        }
        catch (Exception e)
        {
            logger.fatal("PageManagementCommonActionUtil.setInterviewFromCreateEditSubmit :Exception thrown "+ e.getMessage(), e);
            throw new NEDSSAppException(e.getMessage(),e);
        }
    }

    public static void setCommonAnswersForGenericViewEdit(PageSubForm form, PageActProxyVO proxyVO,
            HttpServletRequest request) throws NEDSSAppException
    {
        if (form.getBusinessObjectType().equals(NEDSSConstants.INTERVIEW_BUSINESS_OBJECT_TYPE))
            setInterviewInformationOnForm(form, proxyVO);
        else if (form.getBusinessObjectType().equals(NEDSSConstants.VACCINATION_BUSINESS_OBJECT_TYPE))
        	setVaccinationInformationOnForm(form, proxyVO);
        
        //PageLoadUtil.populatePatientSummary(form, personVO, proxyVO, form.getBusinessObjectType(), request);
       
        //Populate State County
		if (form.getPageClientVO().getAnswer(PageConstants.STATE) == null)
			form.getAttributeMap().put(PageConstants.STATE + "_STATE",
					PropertyUtil.getInstance().getNBS_STATE_CODE());
		else
			form.getAttributeMap().put(PageConstants.COUNTY + "_STATE",
					form.getPageClientVO().getAnswer(PageConstants.STATE));

		
		if (form.getPageClientVO().getAnswer("DEM162_W") == null)
            form.getAttributeMap().put("DEM165_W_STATE", PropertyUtil.getInstance().getNBS_STATE_CODE());
        else
            form.getAttributeMap().put("DEM165_W_STATE", form.getPageClientVO().getAnswer("DEM162_W"));
		
		
		if (form.getPageClientVO().getAnswer(PageConstants.STATE) == null)
			form.setDwrStateSiteCounties(CachedDropDowns
					.getCountyCodes(PropertyUtil.getInstance()
							.getNBS_STATE_CODE()));
		else
			form.setDwrStateSiteCounties(CachedDropDowns
					.getCountyCodes(form.getPageClientVO().getAnswer(
							PageConstants.STATE)));
		
    }

    public static void setCommonAnswersForGenericCreate(PageSubForm form, PageActProxyVO proxyVO,
            HttpServletRequest request, String userId) throws NEDSSAppException
    {
        if (form.getBusinessObjectType().equals(NEDSSConstants.INTERVIEW_BUSINESS_OBJECT_TYPE))
            setInterviewFromCreateEditSubmit(form, proxyVO, userId); 
        if (form.getBusinessObjectType().equals(NEDSSConstants.VACCINATION_BUSINESS_OBJECT_TYPE)){
            setVaccinationFromCreateEditSubmit(form, proxyVO, userId);
            /*int tempId = -1;
            setEntitiesForCreateEdit(form, proxyVO, tempId, "0", userId);*/
        }
    }

    public static void setCommonAnswersFromGenericEdit(PageSubForm form, PageActProxyVO proxyVO,
            HttpServletRequest request, String userId) throws NEDSSAppException
    {
        if (form.getBusinessObjectType().equals(NEDSSConstants.INTERVIEW_BUSINESS_OBJECT_TYPE))
            setInterviewFromCreateEditSubmit(form, proxyVO, userId);
        else if( NEDSSConstants.VACCINATION_BUSINESS_OBJECT_TYPE.equals(form.getBusinessObjectType()))
        	setVaccinationFromCreateEditSubmit(form, proxyVO, userId);
    }

    public static Map<Object, Object> deleteProxyObject(String actUidStr, String businessObjectType, HttpSession session)
    {
        logger.debug("deleteProxyObject: begin processing..");
        MainSessionCommand msCommand = null;
        Map<Object, Object> returnMap = new HashMap<Object, Object>();
        try
        {
            Long actUID = new Long(actUidStr);
            String sBeanJndiName = JNDINames.PAGE_PROXY_EJB;
            String sMethod = "deletePageProxy";
            Object[] oParams = new Object[] { businessObjectType, actUID };
            MainSessionHolder holder = new MainSessionHolder();
            msCommand = holder.getMainSessionCommand(session);
            ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
            returnMap = (Map<Object, Object>) arr.get(0);

        }
        catch (Exception ex)
        {
            logger.error("Error occurred deleting " + businessObjectType + "(" + actUidStr + ")");
            logger.error("deleteProxyObject: "+ex.getMessage(), ex);
        }
		return returnMap;
    }

    public static Map<Object, Object> checkAssociationBeforeDelete(String actUidStr, String businessObjectType, HttpSession session)
    {
        logger.debug("checkAssociationBeforeDelete: begin processing..");
        MainSessionCommand msCommand = null;
        Map<Object, Object> returnMap = new HashMap<Object, Object>();
        try
        {
            Long actUID = new Long(actUidStr);
            String sBeanJndiName = JNDINames.PAGE_PROXY_EJB;
            String sMethod = "checkAssociationBeforeDelete";
            Object[] oParams = new Object[] { businessObjectType, actUID };
            MainSessionHolder holder = new MainSessionHolder();
            msCommand = holder.getMainSessionCommand(session);
            ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
            returnMap = (Map<Object, Object>) arr.get(0);

        }
        catch (Exception ex)
        {
            logger.error("Error occurred checkAssociationBeforeDelete " + businessObjectType + "(" + actUidStr + ")");
            logger.error("deleteProxyObject: "+ex.getMessage(), ex);
        }
		return returnMap;
    }
    
    private static String getVal(Object obj)
    {
        return obj == null ? "" : (String) obj;

    }

    /**
     * storeCreatedEditedGenericEntities() retrieves Participations' with types
     * of PRVs and ORGs from the form and stores them in both the
     * theParticipationDTCollection and pageVO.actEntityDTCollection
     * 
     * @param form
     * @param proxyVO
     * @param oldProxyVO
     *            - could be null
     * @param theQUestionMap
     * @param userId
     * @param providerUid
     *            (associated with User) (not currently used)
     * @throws Exception
     */
    /*Pradeep: It seems like dead code with no references: commented in release 6.0 
    public static void storeCreatedEditedGenericEntities(PageSubForm form, PageActProxyVO proxyVO, PageProxyVO oldProxyVO,
            Map<Object, Object> theQuestionMap, String userId, Long providerUid, HttpServletRequest request) throws Exception
    {
        
        try {
        logger.debug("storeEditedGenericEntities: begin processing..");
        Long actUid = null;

        // initialize collections if needed
        if (((PageActProxyVO) proxyVO).getTheParticipationDTCollection() == null)
        {
            Collection<Object> participationDTCollection = new ArrayList<Object>();
            Collection<Object> nbsActEntityDT = new ArrayList<Object>();
            ((PageActProxyVO) proxyVO).setTheParticipationDTCollection(participationDTCollection);
            ((PageActProxyVO) proxyVO).getPageVO().setActEntityDTCollection(nbsActEntityDT);
        }

        Long patientRevUID = null;
       */
        /*
        if (form.getBusinessObjectType().equals(NEDSSConstants.INTERVIEW_BUSINESS_OBJECT_TYPE))
            actUid = proxyVO.getInterviewVO().getTheInterviewDT().getInterviewUid();
       else if (form.getBusinessObjectType().equals(NEDSSConstants.VACCINATION_BUSINESS_OBJECT_TYPE) || form.getBusinessObjectType().equals(NEDSSConstants.LAB_BUSINESS_OBJECT_TYPE)){
            actUid = proxyVO.getInterventionVO().getTheInterventionDT().getInterventionUid();
	        if(actUid<0){
	        	//Create vaccination
	        	patientRevUID =actUid-1;
	        	int tempID = patientRevUID.intValue();
	        	Long patientUid = (Long) NBSContext.retrieve(request.getSession(), NBSConstantUtil.DSPersonSummary);
	        //	PageCreateHelper.setPatientForEventCreate(patientUid, tempID, proxyVO, form, request, userId);
	        	//CaseCreateHelper.setPatientForEventCreate( patientUid, patientRevUID.intValue(),  proxyVO,  form,  request,  userId) ;
	        }else{
	        	//Edit vaccination
	        	PersonVO personVO = PageLoadUtil.getGenericPersonVO(NEDSSConstants.SUBJECT_OF_VACCINE, form.getPageClientVO().getOldPageProxyVO());
				PageCreateHelper.setPatientForEventEdit(form, personVO, proxyVO, request, userId);
				patientRevUID = personVO.thePersonDT.getPersonUid();
				Collection personVOCollection = new ArrayList<Object>();
				personVOCollection.add(personVO);
			//	proxyVO.setThePersonVOCollection(personVOCollection);
	        }
        }*/
        /*
        CachedDropDowns cdd = new CachedDropDowns();
        TreeMap<Object, Object> participationTypeCaseMap = cdd.getParticipationTypeList();
        String subjectClassCd = "";
        Iterator quesIt = theQuestionMap.values().iterator();
        while (quesIt.hasNext())
        {
            NbsQuestionMetadata metaData = (NbsQuestionMetadata) quesIt.next();
            if (StringUtils.isEmpty(metaData.getPartTypeCd()) || !"PART".equalsIgnoreCase(metaData.getDataType()))
                continue;
            ParticipationTypeVO parTypeVO = (ParticipationTypeVO) participationTypeCaseMap
                    .get(metaData.getPartTypeCd()+NEDSSConstants.PART_CACHED_MAP_KEY_SEPARATOR+metaData.getQuestionIdentifier());
            if (parTypeVO != null)
                subjectClassCd = parTypeVO.getSubjectClassCd();
            else
            {
                subjectClassCd = "";
                logger.debug("loadGenericEntities: Found an unknown participation type while loading View? For "
                        + metaData.getPartTypeCd());
            }

            if (subjectClassCd != null && subjectClassCd.equalsIgnoreCase(NEDSSConstants.CLASS_CD_PSN) )
            {
                // PersonVO oldPersonVO =
                // PageLoadUtil.getGenericPersonVO(metaData.getPartTypeCd(),
                // oldProxyVO);
            	
            	 logger.debug("Processing Person Participation - Edit -> " + parTypeVO.getTypeCd() );
            	 String newUidSt = "";
            	 if(form.getAttributeMap().get(parTypeVO.getQuestionIdentifier() + "Uid") == null && PATIENT_SUBJECT_CLASS_CD.equals(parTypeVO.getSubjectClassCd())){
            		 newUidSt=patientRevUID.toString();
            	 }else{
            		 newUidSt= form.getAttributeMap().get(parTypeVO.getQuestionIdentifier() + "Uid") == null ? null
                        : (String) form.getAttributeMap().get(parTypeVO.getQuestionIdentifier() + "Uid");
            	 }
                processGenericParticipation(newUidSt, actUid, (PageActProxyVO) proxyVO, (PageActProxyVO) oldProxyVO,
                        parTypeVO.getTypeCd(), parTypeVO.getSubjectClassCd(), userId, providerUid, form.getBusinessObjectType());
            
            }
            else if (subjectClassCd != null && subjectClassCd.equals(NEDSSConstants.CLASS_CD_ORG))
            {
                logger.debug("Processing Organization Participation - Edit -> " + parTypeVO.getTypeCd());
                String newUidSt = form.getAttributeMap().get(parTypeVO.getQuestionIdentifier() + "Uid") == null ? null
                        : (String) form.getAttributeMap().get(parTypeVO.getQuestionIdentifier() + "Uid");
                processGenericParticipation(newUidSt, actUid, (PageActProxyVO) proxyVO, (PageActProxyVO) oldProxyVO,
                        parTypeVO.getTypeCd(), parTypeVO.getSubjectClassCd(), userId, providerUid, form.getBusinessObjectType());
            }else if (subjectClassCd != null && subjectClassCd.equalsIgnoreCase(NEDSSConstants.CLASS_CD_PAT) )
            {
            	String newUidSt=patientRevUID.toString();
                processGenericParticipation(newUidSt, actUid, (PageActProxyVO) proxyVO, (PageActProxyVO) oldProxyVO,
                        parTypeVO.getTypeCd(), parTypeVO.getSubjectClassCd(), userId, providerUid, form.getBusinessObjectType());
            
            }
            else
                logger.debug("Unsupported subjectClassCd loading View -> " + subjectClassCd);

        } // while Ques Map has next..
        } catch(Exception e){
            logger.error("Exception in storeCreatedEditedGenericEntities()::"+e.getMessage(), e );
            e.printStackTrace();
        }

    }

    */
    /**
     * processGenericParticipation() In the new approach we send all
     * participations we have and mark itNew and it dirty but we don't worry
     * about deleted participations. The back end will handle deleted. Note that
     * participations are stored in both the theParticipationDTCollection and
     * pageVO.actEntityDTCollection
     * 
     * @param newEntityUid
     *            (contains EntityUid | version)
     * @param actUid
     * @param newProxyVO
     * @param oldProxyVO
     *            - may be null
     * @param participationType
     * @param subjectClassCode
     * @param userId
     * @param providerUid
     *            (associated with User) (not currently used)
     * @throws Exception
     */
    /*Pradeep Commented out as this seems dead code--Release 6.0
    public static void processGenericParticipation(String newEntityUid, Long actUid, PageActProxyVO proxyVO,
            PageActProxyVO oldProxyVO, String typeCd, String classCd, String userId, Long providerUid, String busObjType)
            throws NEDSSAppException
    {
        logger.debug("processGenericParticipation: begin processing " + typeCd);
        Collection<Object> parCollection = new ArrayList<Object>();
        Collection<Object> entityCollection = new ArrayList<Object>();
        try
        {
            logger.debug(" newEntityUid = " + newEntityUid + " old");
            if (newEntityUid != null && newEntityUid.indexOf("|") == -1)
            {
                newEntityUid = newEntityUid + "|1";
            }
            String subjectEntityUid = PageCreateHelper.splitUid(newEntityUid);
            String versionCtrlNbr = PageCreateHelper.splitVerCtrlNbr(newEntityUid);

            Collection<Object> oldParCollection = null;
            Collection<Object> oldEntityCollection = null;
            if (oldProxyVO != null)
            {
                oldParCollection = oldProxyVO.getTheParticipationDTCollection();
                oldEntityCollection = oldProxyVO.getPageVO().getActEntityDTCollection();
            }
            // initialize if null
            if (oldParCollection == null)
                oldParCollection = new ArrayList<Object>();
            if (oldEntityCollection == null)
                oldEntityCollection = new ArrayList<Object>();

            ParticipationDT oldParticipationDT = PageCreateHelper.getParticipation(typeCd, classCd, oldParCollection);

            String actClassCd = getActClassCdByBusObjType(busObjType);
            
            if (oldParticipationDT == null)
            {
                if (subjectEntityUid != null && !subjectEntityUid.trim().equals(""))
                {
                    logger.info("Participation is new, setitNew " + subjectEntityUid);
                    
                    
                    ParticipationDT participationDT = PageCreateHelper.createParticipation(actUid, subjectEntityUid,
                            classCd, typeCd, actClassCd);
                    participationDT.setItNew(true);
                    participationDT.setItDirty(false);
                    parCollection.add(participationDT);
                    NbsActEntityDT nbsActEntity = PageCreateHelper.createPamCaseEntity(actUid, subjectEntityUid,
                            versionCtrlNbr, typeCd, userId);
                    nbsActEntity.setNbsActEntityUid(new Long(-1));
                    nbsActEntity.setLastChgUserId(Long.valueOf(userId));
                    nbsActEntity.setLastChgTime(new java.sql.Timestamp(new Date().getTime()));
                    nbsActEntity.setRecordStatusCd(NEDSSConstants.ACTIVE);
                    nbsActEntity.setRecordStatusTime(new java.sql.Timestamp(new Date().getTime()));
                    nbsActEntity.setItNew(true);
                    nbsActEntity.setItDirty(false);
                    entityCollection.add(nbsActEntity);
                }
            }
            else
            {
                Long oldEntityUid = oldParticipationDT.getSubjectEntityUid();
                if (subjectEntityUid != null && !subjectEntityUid.trim().equals("")
                        && !subjectEntityUid.equals(oldEntityUid.toString()))
                {
                    logger.info("Participation changed newEntityUid: " + subjectEntityUid + " for typeCd " + typeCd);
                    ParticipationDT participationDT = PageCreateHelper.createParticipation(actUid, subjectEntityUid,
                            classCd, typeCd, actClassCd);
                    participationDT.setItNew(true);
                    participationDT.setItDirty(false);
                    parCollection.add(participationDT);
                    NbsActEntityDT nbsActEntity = PageCreateHelper.createPamCaseEntity(actUid, subjectEntityUid,
                            versionCtrlNbr, typeCd, userId);
                    nbsActEntity.setItNew(true);
                    nbsActEntity.setItDirty(false);
                    entityCollection.add(nbsActEntity);
                    // per Pradeep, not marking the old one is delete and
                    // sending
                }
                else
                {
                	if(subjectEntityUid != null && !subjectEntityUid.trim().equals("")){	
	                    // assume unchanged - still set it dirty
	                    logger.info("Same participation " + subjectEntityUid);
	                    ParticipationDT participationDT = PageCreateHelper.createParticipation(actUid, subjectEntityUid,
	                            classCd, typeCd, actClassCd);
	                    participationDT.setItNew(false);
	                    participationDT.setItDirty(true);
	                    parCollection.add(participationDT);
	                    // find existing entity dt - should be present
	                    NbsActEntityDT oldEntityDT = PageCreateHelper.getNbsCaseEntity(typeCd, oldEntityCollection);
	                    if (oldEntityDT == null)
	                        logger.warn("processGenericParticipation: Participation exists but no corresponding nbsActEntity found?");
	                    NbsActEntityDT nbsActEntity = PageCreateHelper.createPamCaseEntity(actUid, subjectEntityUid,
	                            versionCtrlNbr, typeCd, userId);
	                    if (oldEntityDT.getNbsActEntityUid() != null)
	                        nbsActEntity.setNbsActEntityUid(oldEntityDT.getNbsActEntityUid());
	                    if (oldEntityDT.getAddTime() != null)
	                        nbsActEntity.setAddTime(oldEntityDT.getAddTime());
	                    if (oldEntityDT.getAddUserId() != null)
	                        nbsActEntity.setAddUserId(oldEntityDT.getAddUserId());
	                    if (oldEntityDT.getEntityVersionCtrlNbr() != null)
	                        nbsActEntity.setEntityVersionCtrlNbr(oldEntityDT.getEntityVersionCtrlNbr());
	                    nbsActEntity.setLastChgUserId(Long.valueOf(userId));
	                    nbsActEntity.setRecordStatusCd(NEDSSConstants.ACTIVE);
	                    nbsActEntity.setRecordStatusTime(new java.sql.Timestamp(new Date().getTime()));
	                    nbsActEntity.setLastChgTime(new java.sql.Timestamp(new Date().getTime()));
	                    nbsActEntity.setItNew(false);
	                    nbsActEntity.setItDirty(true);
	                    entityCollection.add(nbsActEntity);
                    
                	}
                } // subjectEntity same
            } // oldPart not null
            if (parCollection.size() > 0)
                proxyVO.getTheParticipationDTCollection().addAll(parCollection);
            if (entityCollection.size() > 0)
            {
                proxyVO.getPageVO().getActEntityDTCollection().addAll(entityCollection);
            }

        }
        catch (NumberFormatException e)
        {
            logger.error(e);
            logger.fatal("Error: processGenericParticipation NumberFormatException thrown, " + e.getMessage(), e);
            throw new NEDSSAppException(e.getMessage(), e);
        }
        catch (Exception e)
        {
            logger.error(e);
            logger.fatal("Error: processGenericParticipation Exception thrown, " + e.getMessage(), e);
            throw new NEDSSAppException(e.getMessage(), e);
        }
    }
*/
    /**
     * setValidationContextForInterview() Date rules i.e. presumptive interview
     * date can't be after original interview date require some information to
     * be placed into request Expects DSInterviewList to be in the session.
     * actUid may be in request if we are editing. On a create, it is not there.
     * JavaScript function ixsValidateInterviewDateOnSubmit() uses these values.
     * 
     * @param request
     */
    public static void setValidationContextForInterview(HttpServletRequest request)
    {
        ArrayList<Object> theInterviewSummary = null;
        theInterviewSummary = (ArrayList<Object>) (NBSContext.retrieve(request.getSession(),
                NBSConstantUtil.DSInterviewList));
        Timestamp originalInterviewDate = null;
        Timestamp latestPresumptiveInterviewDate = null;
        Timestamp earliestReInterviewDate = null;
        if (theInterviewSummary != null)
        {
            String actUidStr = request.getParameter("actUid");
            Long actUid = null;
            if (actUidStr != null && !actUidStr.isEmpty())
                actUid = new Long(actUidStr);
            Iterator<Object> ite = theInterviewSummary.iterator();
            while (ite.hasNext())
            {
                InterviewSummaryDT ixsDT = (InterviewSummaryDT) ite.next();
                if (actUid != null && actUid.equals(ixsDT.getInterviewUid())) {
                    //for SRT dropdown effective date functionality
                    if (ixsDT.getInterviewDate() != null)
                    	request.setAttribute("addTime", ixsDT.getInterviewDate());
                    continue; // don't worry about the one we are editing
                }
                    
                String interviewType = ixsDT.getInterviewTypeCd();
                if (interviewType.equals("INITIAL")) {
                    originalInterviewDate = ixsDT.getInterviewDate();
                } else if (interviewType.equals("PRESMPTV"))
                {
                	//could be only presumptive
                    if (latestPresumptiveInterviewDate == null)
                        latestPresumptiveInterviewDate = ixsDT.getInterviewDate();
                    else if (latestPresumptiveInterviewDate.before(ixsDT.getInterviewDate()))
                        latestPresumptiveInterviewDate = ixsDT.getInterviewDate();
                }
                else if (interviewType.equals("REINTVW"))
                {
                    if (earliestReInterviewDate == null)
                        earliestReInterviewDate = ixsDT.getInterviewDate();
                    else if (earliestReInterviewDate.after(ixsDT.getInterviewDate()))
                        earliestReInterviewDate = ixsDT.getInterviewDate();
                }
            }
        }
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        String ixsOriginalInterviewDate = originalInterviewDate == null ? "" : formatter.format(originalInterviewDate);
        String ixsLatestPresumptiveInterviewDate = latestPresumptiveInterviewDate == null ? "" : formatter
                .format(latestPresumptiveInterviewDate);
        String ixsEarliestReInterviewDate = earliestReInterviewDate == null ? "" : formatter
                .format(earliestReInterviewDate);
        request.setAttribute("ixsOriginalInterviewDate", ixsOriginalInterviewDate);
        request.setAttribute("ixsLatestPresumptiveInterviewDate", ixsLatestPresumptiveInterviewDate);
        request.setAttribute("ixsEarliestReInterviewDate", ixsEarliestReInterviewDate);
    }
    
    /**
     * setCoinfectionContext() See if CoInfections exist. If more than one exists, set the context
     * 
     * @param request
     * @param pageClientVO
     * @param fieldToSet
     * @param coinfectioExistsField 
     */
    public static void setCoinfectionContext(HttpServletRequest request, ClientVO clientVO, String coinfectioExistsField )
    {
        Long investigationUid = null;
        try {
      	  String investigationUidStr = (String)  request.getAttribute("DSInvestigationUID");
      	  if (investigationUidStr== null)
      		  investigationUidStr= (String) NBSContext.retrieve(request.getSession(), "DSInvestigationUID");
      	  if (investigationUidStr != null)
      		  investigationUid = new Long(investigationUidStr);
  		} catch (Exception e) {
  			logger.warn("In PageManagementCommonActionUtil: Investigation Uid not in scope to check for coinfections..");
  		}
         ArrayList<Object> coinfectionInvList = null;
         if (investigationUid != null)
  		try {
  			coinfectionInvList = PageLoadUtil.getSpecificCoinfectionInvListPHC(investigationUid, request);
  		} catch (NEDSSAppConcurrentDataException e) {
  			e.printStackTrace();
  		} catch (Exception e) {
  			e.printStackTrace();
  		}
        if (coinfectionInvList==null)
      	  coinfectionInvList=new ArrayList<Object>();
        // if we're in a co-infection situation and there are other invs
        // we may want them to be linked to this interview as well
        // They should have interview status set to 'I' for Interviewed to be a candidate.
        Iterator coinfIter = coinfectionInvList.iterator();
        while (coinfIter.hasNext()) {
        	CoinfectionSummaryVO coinfVO = (CoinfectionSummaryVO) coinfIter.next();
        	if (coinfVO.getPatIntvStatusCd()  == null || !coinfVO.getPatIntvStatusCd().equals(CTConstants.PatientInterviewedStatusCd))
        		coinfIter.remove();
        }
        if (coinfectionInvList.size() > 1) {
        	clientVO.getAnswerMap().put(coinfectioExistsField, "T"); //true
      	  //request.setAttribute("coInfectionInvestigationList", coinfectionInvList);
        } else clientVO.getAnswerMap().put(coinfectioExistsField, "F");   
        
        return;
    }
    

    /**
     * 
     * @param form
     * @param coinfectioExists
     * @param coinfectionInvExists 
     * @param actUidStr 
     */
    public static void setCoinfectionContextForView(HttpServletRequest request, PageSubForm form, String businessObjType, String actUidStr, String coinfectionInvExists)
    {
        Long investigationUid = null;
        try {
      	  String investigationUidStr = (String)  request.getAttribute("DSInvestigationUID");
      	  if (investigationUidStr== null)
      		  investigationUidStr= (String) NBSContext.retrieve(request.getSession(), "DSInvestigationUID");
      	  if (investigationUidStr != null)
      		  investigationUid = new Long(investigationUidStr);
  		} catch (Exception e) {
  			logger.warn("In PageManagementCommonActionUtil: Investigation Uid not in scope to check for coinfections..");
  		}
         ArrayList<Object> coinfectionInvList = null;
         if (investigationUid != null)
  		try {
  			coinfectionInvList = PageLoadUtil.getSpecificCoinfectionInvListPHC(investigationUid, request);
  		} catch (NEDSSAppConcurrentDataException e) {
  			e.printStackTrace();
  		} catch (Exception e) {
  			e.printStackTrace();
  		}
        if (coinfectionInvList==null)
      	  coinfectionInvList=new ArrayList<Object>();
        // if we're in a co-infection situation and there are other invs
        // we may want them to be linked to this interview as well
        // They should have interview status set to 'I' for Interviewed to be a candidate.
        Iterator coinfIter = coinfectionInvList.iterator();
        while (coinfIter.hasNext()) {
        	CoinfectionSummaryVO coinfVO = (CoinfectionSummaryVO) coinfIter.next();
        	if (coinfVO.getPatIntvStatusCd()  == null || !coinfVO.getPatIntvStatusCd().equals(CTConstants.PatientInterviewedStatusCd))
        		coinfIter.remove();
        }
        if (coinfectionInvList.size() > 1) {
        	form.getAttributeMap().put(coinfectionInvExists, true);
        	request.setAttribute("actUid", actUidStr);
        }  
        
        return;
    }

	/**
	 * Remove the participation type if it is present in the collection
	 * @param typeCd i.e. PerAsProviderOfOBGYN
	 * @param proxyVO
	 */
    public static void removeTheEntity(String typeCd, PageActProxyVO proxyVO) {
    	Collection<Object>  entityDTCollection = proxyVO.getPageVO().getActEntityDTCollection();
    	Collection<Object>  participationDTCollection = proxyVO.getTheParticipationDTCollection();
    	if(entityDTCollection  != null && entityDTCollection.size() > 0) {
    		Iterator<Object> iter = entityDTCollection.iterator();
    		while(iter.hasNext()) {
    			NbsActEntityDT entityDT = (NbsActEntityDT) iter.next();
    			if(entityDT.getEntityUid() != null && entityDT.getTypeCd().equalsIgnoreCase(typeCd)) {
    				iter.remove();
    				break;
    			}
    		}
    	}
    	if(participationDTCollection  != null && participationDTCollection.size() > 0) {
    		Iterator<Object> iter = participationDTCollection.iterator();
    		while(iter.hasNext()) {
    			ParticipationDT partDT = (ParticipationDT) iter.next();
    			if(partDT.getSubjectEntityUid() != null && partDT.getTypeCd().equalsIgnoreCase(typeCd)) {
    				iter.remove();
    				return;
    			}
    		}
    	}    	
    	return;  //not found
    }	
	
	
    /**
     * To find the published page based on BusinessObjectType, mainly used for VAC event.
     * Invoked while creating event for BusinessObject for whom conditionCd is not associated.
     * @param busObjType
     * @param request
     * @return WaTemplateDT
     * @throws Exception
     */
    public static WaTemplateDT findPublishedPageByBusinessObjType(String busObjType, HttpServletRequest request) throws Exception{
    	WaTemplateDT waTemplateDT = null;
		try{
			Object[] oParams = new Object[] {busObjType};
			String sBeanJndiName = JNDINames.PAGE_MANAGEMENT_PROXY_EJB;
			String sMethod = "findPageByBusinessObjType";
			Object obj = CallProxyEJB.callProxyEJB(oParams, sBeanJndiName, sMethod, request.getSession());
			ArrayList<Object> pageList = (ArrayList<Object>) obj;
			if(pageList!=null){
				for(int i=0;i<pageList.size();i++){
					waTemplateDT = (WaTemplateDT) pageList.get(i);
					if("T".equals(waTemplateDT.getPublishIndCd())){
						return waTemplateDT;
					}
				}
			}
		}catch (Exception ex) {
			logger.fatal("Error in findWatemplateUidByBusinessObjType : "+ex.getMessage(), ex);
			throw new Exception(ex.getMessage(), ex);
		}
		return null;
	}
    
    private static void setVaccinationFromCreateEditSubmit(PageSubForm form, PageActProxyVO proxyVO, String userId)
            throws NEDSSAppException
    {
        logger.debug("setVaccinationFromCreateEditSubmit: begin processing..");
        try
        {
            Map<Object, Object> answerMap = form.getPageClientVO().getAnswerMap();
            InterventionVO vacVO = new InterventionVO();
            InterventionDT vacDT = new InterventionDT();
            vacVO.setTheInterventionDT(vacDT);

            // set some values from the oldInterviewDT such as addTime and
            // versionNbr if not a create
            if ((form.getPageClientVO().getOldPageProxyVO() != null)
                    && (form.getPageClientVO().getOldPageProxyVO().getInterventionVO() != null)){
            	vacVO.setItNew(false);
            	vacVO.setItDirty(true);
            	vacDT.setItDirty(true);
            	vacDT.setItNew(false);
                InterventionVO oldInterventionVO = form.getPageClientVO().getOldPageProxyVO().getInterventionVO();
                if (oldInterventionVO.getTheInterventionDT().getInterventionUid() != null)
                	vacDT.setInterventionUid(oldInterventionVO.getTheInterventionDT().getInterventionUid());
                if (oldInterventionVO.getTheInterventionDT().getAddTime() != null)
                	vacDT.setAddTime(oldInterventionVO.getTheInterventionDT().getAddTime());
                if (oldInterventionVO.getTheInterventionDT().getAddUserId() != null)
                	vacDT.setAddUserId(oldInterventionVO.getTheInterventionDT().getAddUserId());
                if (oldInterventionVO.getTheInterventionDT().getLocalId() != null)
                	vacDT.setLocalId(oldInterventionVO.getTheInterventionDT().getLocalId());
                if (oldInterventionVO.getTheInterventionDT().getVersionCtrlNbr() != null)
                	vacDT.setVersionCtrlNbr(oldInterventionVO.getTheInterventionDT().getVersionCtrlNbr());
                vacDT.setSharedInd(oldInterventionVO.getTheInterventionDT().getSharedInd());
                vacDT.setCd(oldInterventionVO.getTheInterventionDT().getCd());
                vacDT.setCdDescTxt(oldInterventionVO.getTheInterventionDT().getCdDescTxt());
                vacDT.setCdSystemCd(oldInterventionVO.getTheInterventionDT().getCdSystemCd());
                vacDT.setCdSystemDescTxt(oldInterventionVO.getTheInterventionDT().getCdSystemCd());
            }else{ // is a create
            	vacVO.getTheInterventionDT().setInterventionUid(new Long(-1));
            	vacVO.getTheInterventionDT().setVersionCtrlNbr(1);
            	vacVO.getTheInterventionDT().setAddUserId(Long.valueOf(userId));
            	vacVO.getTheInterventionDT().setAddTime(new Timestamp(new Date().getTime()));
            	
            	vacVO.getTheInterventionDT().setCd(NEDSSConstants.VACADM_CD);
            	vacVO.getTheInterventionDT().setCdDescTxt("Vaccine Administration");
            	vacVO.getTheInterventionDT().setCdSystemCd(NEDSSConstants.NBS);
            	vacVO.getTheInterventionDT().setCdSystemDescTxt("NEDSS Base System");
                
            	vacVO.getTheInterventionDT().setItNew(true);
            	vacVO.getTheInterventionDT().setItDirty(false);
            	
            	//Check if vaccination created electronically, the set electronic_ind = 'Y' otherwise 'N'
            	
            	vacVO.getTheInterventionDT().setElectronicInd("N");
            	
            	vacVO.setItNew(true);
            	vacVO.setItDirty(false);
            }

            Map<Object,Object> questionMap = (Map)QuestionsCache.getDMBQuestionMap().get(form.getPageFormCd());
            answerMap.put(PageConstants.INTERVENTION_LOCAL_ID, vacVO.getTheInterventionDT().getLocalId());
            boolean isInterventionDTPopulated= DynamicBeanBinding.transferBeanValues(questionMap, vacVO.getTheInterventionDT(), answerMap, "INTERVENTION");
            
            //Setting TargetSiteDescription from targetSiteCd
            if(vacVO.getTheInterventionDT().getTargetSiteCd()!=null)
            	vacVO.getTheInterventionDT().setTargetSiteDescTxt(CachedDropDowns.getCodeDescTxtForCd(vacVO.getTheInterventionDT().getTargetSiteCd(), "NIP_ANATOMIC_ST"));
            //Setting effectiveFromTime same as activityFromTime
            if(vacVO.getTheInterventionDT().getActivityFromTime()!=null)
            	vacVO.getTheInterventionDT().setEffectiveFromTime(vacVO.getTheInterventionDT().getActivityFromTime());
            
            /*vacVO.getTheInterventionDT().setVaccinationInfoSourceCd(getVal(answerMap.get(PageConstants.VACC_INFO_SOURCE_CD)));
            vacVO.getTheInterventionDT().setMaterialCd(getVal(answerMap.get(PageConstants.MATERIAL_CD)));
            String effFromDateStr = (String) answerMap.get(PageConstants.EFFECTIVE_FROM_TIME);
            if (effFromDateStr != null)
            	vacVO.getTheInterventionDT().setEffectiveFromTime(StringUtils.stringToStrutsTimestamp(effFromDateStr));
            vacVO.getTheInterventionDT().setTargetSiteCd(getVal(answerMap.get(PageConstants.TARGET_SITE_CD)));
            
            String ageAtVaccinationStr = (String) answerMap.get(PageConstants.AGE_AT_VACC);
            if(ageAtVaccinationStr!=null){
            	int ageAtVaccinationInt = Integer.parseInt(ageAtVaccinationStr);
            	vacVO.getTheInterventionDT().setAgeAtVaccination(ageAtVaccinationInt);
            }
            
            vacVO.getTheInterventionDT().setAgeAtVaccinationUnitCd(getVal(answerMap.get(PageConstants.AGE_AT_VACC_UNIT_CD)));
            vacVO.getTheInterventionDT().setVaccinationMfgrCd(getVal(answerMap.get(PageConstants.VACC_MFGR_CD)));
            vacVO.getTheInterventionDT().setMaterialLotNm(getVal(answerMap.get(PageConstants.MATERIAL_LOT_NM)));
            String materialExpirationTimeStr = (String) answerMap.get(PageConstants.MATERIAL_EXPIRATION_TIME);
            if(materialExpirationTimeStr!=null)
            	vacVO.getTheInterventionDT().setMaterialExpirationTime(StringUtils.stringToStrutsTimestamp(materialExpirationTimeStr));
            
            String vaccinationDoseNbrStr = (String) answerMap.get(PageConstants.VACC_DOSE_NBR);
            if(vaccinationDoseNbrStr!=null){
            	int vaccinationDoseNbrInt = Integer.parseInt(vaccinationDoseNbrStr);
            	vacVO.getTheInterventionDT().setVaccinationDoseNbr(vaccinationDoseNbrInt);
            }*/
            
            vacVO.getTheInterventionDT().setLastChgUserId(Long.valueOf(userId));
            vacVO.getTheInterventionDT().setLastChgTime(new Timestamp(new Date().getTime()));
            vacVO.getTheInterventionDT().setRecordStatusCd(NEDSSConstants.ACTIVE);
            vacVO.getTheInterventionDT().setRecordStatusTime(new Timestamp(new Date().getTime()));
            proxyVO.setInterventionVO(vacVO); // set the interview VO into the
                                           // proxy
        }
        catch (Exception e)
        {
            logger.fatal("PageManagementCommonActionUtil.setVaccinationFromCreateEditSubmit :Exception thrown "
                    + e.getMessage(), e);
            throw new NEDSSAppException(e.getMessage(), e);
        }
    }
    
    public static void setVaccinationInformationOnForm(PageSubForm form, PageProxyVO proxyVO) throws NEDSSAppException
    {
        logger.debug("setInterviewInformationOnForm: begin processing..");
        try
        {
            InterventionDT interventionDT = ((PageActProxyVO) proxyVO).getInterventionVO().getTheInterventionDT();
            PageClientVO clientVO = form.getPageClientVO();
            
            if (interventionDT.getVaccInfoSourceCd() != null)
                clientVO.setAnswer(PageConstants.VACC_INFO_SOURCE_CD, interventionDT.getVaccInfoSourceCd());
            if (interventionDT.getMaterialCd() != null)
                clientVO.setAnswer(PageConstants.MATERIAL_CD, interventionDT.getMaterialCd());
            if (interventionDT.getActivityFromTime() != null)
            	 clientVO.setAnswer(PageConstants.ACTIVITY_FROM_TIME, StringUtils.formatDate(interventionDT.getActivityFromTime()));
            if (interventionDT.getAgeAtVacc() != null)
                clientVO.setAnswer(PageConstants.AGE_AT_VACC, interventionDT.getAgeAtVacc().toString());
            if (interventionDT.getAgeAtVaccUnitCd() != null)
                clientVO.setAnswer(PageConstants.AGE_AT_VACC_UNIT_CD, interventionDT.getAgeAtVaccUnitCd());
            if (interventionDT.getVaccMfgrCd() != null)
                clientVO.setAnswer(PageConstants.VACC_MFGR_CD, interventionDT.getVaccMfgrCd());
            if (interventionDT.getMaterialLotNm() != null)
                clientVO.setAnswer(PageConstants.MATERIAL_LOT_NM, interventionDT.getMaterialLotNm());
            if (interventionDT.getMaterialExpirationTime() != null)
           	    clientVO.setAnswer(PageConstants.MATERIAL_EXPIRATION_TIME, StringUtils.formatDate(interventionDT.getMaterialExpirationTime()));
            if (interventionDT.getVaccDoseNbr() != null)
                clientVO.setAnswer(PageConstants.VACC_DOSE_NBR, interventionDT.getVaccDoseNbr().toString());
            if (interventionDT.getTargetSiteCd() != null)
                clientVO.setAnswer(PageConstants.TARGET_SITE_CD, interventionDT.getTargetSiteCd());
            //Map<Object,Object> questionMap = (Map)QuestionsCache.getDMBQuestionMap().get(form.getPageFormCd());
            //DynamicBeanBinding.transferBeanValuesForView(questionMap, interventionDT, clientVO.getAnswerMap(), "INTERVENTION");
        }
        catch (Exception ex)
        {
            logger.fatal("PageLoadUtil.setVaccinationInformationOnForm :Exception thrown " + ex.getMessage(),ex);
            throw new NEDSSAppException(ex.getMessage(), ex);
        }
    }
    
    private static void setEntitiesForCreateEdit(PageSubForm form, PageActProxyVO proxyVO, int revisionPatientUID, String versionCtrlNbr, String userId) {
		Long interventionUid = proxyVO.getInterventionVO().getTheInterventionDT().getInterventionUid();

		Collection<Object>  entityColl = new ArrayList<Object> ();
		int vcNum = Integer.valueOf(versionCtrlNbr).intValue() + 1;
		// patient PHC participation
		NbsActEntityDT entityDT = createEntity(interventionUid, String.valueOf(revisionPatientUID), String.valueOf(vcNum), NEDSSConstants.PHC_PATIENT, userId);
		//For update
		if(entityDT.getActUid().longValue() > 0) {
			entityDT.setItNew(false);
			entityDT.setItDirty(true);
			entityDT.setItDelete(false);
			NbsActEntityDT oldDT = getNbsEntity(NEDSSConstants.PHC_PATIENT,((PageActProxyVO)form.getPageClientVO().getOldPageProxyVO()).getPageVO().getActEntityDTCollection() );
			entityDT.setNbsActEntityUid(oldDT.getNbsActEntityUid());

		}
		entityColl.add(entityDT);

		proxyVO.getPageVO().setActEntityDTCollection(entityColl);
	}
    
    public static NbsActEntityDT createEntity(Long actUid, String subjectEntityUid, String versionCtrlNbr, String typeCd, String userId) {

		NbsActEntityDT entityDT = new NbsActEntityDT();
		entityDT.setAddTime(new java.sql.Timestamp(new Date().getTime()));
		entityDT.setAddUserId(Long.valueOf(userId));
		entityDT.setEntityUid(Long.valueOf(subjectEntityUid));
		entityDT.setEntityVersionCtrlNbr(new Integer(versionCtrlNbr));
		entityDT.setActUid(actUid);
		entityDT.setTypeCd(typeCd);
		return entityDT;
	}
    
    private static NbsActEntityDT getNbsEntity(String typeCd, Collection<Object>  entityDTCollection) {

    	if(entityDTCollection  != null && entityDTCollection.size() > 0) {
    		Iterator<Object> iter = entityDTCollection.iterator();
    		while(iter.hasNext()) {
    			NbsActEntityDT entityDT = (NbsActEntityDT) iter.next();
    			if(entityDT.getEntityUid() != null && entityDT.getTypeCd().equalsIgnoreCase(typeCd)) {
    				entityDT.setItDirty(true);
    				return entityDT;
    			}
    		}
    	}
    	return null;
    }
    
    private static String getActClassCdByBusObjType(String busObjType){
    	String actClassCd = NEDSSConstants.CLASS_CD_INTV;
    	try{
    		if(NEDSSConstants.VACCINATION_BUSINESS_OBJECT_TYPE.equals(busObjType))
    			actClassCd = NEDSSConstants.CLASS_CD_INTV;
    		else if(NEDSSConstants.INTERVIEW_BUSINESS_OBJECT_TYPE.equals(busObjType))
    			actClassCd = NEDSSConstants.CLASS_CD_INTERVIEW;
    	}catch(Exception ex){
    		logger.error("getActClassCdByBusObjType :Exception thrown " + ex.toString(),ex);
    	}
    	return actClassCd;
    }
}
