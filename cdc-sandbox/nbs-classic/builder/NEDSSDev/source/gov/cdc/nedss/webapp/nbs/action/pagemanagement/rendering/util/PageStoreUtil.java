package gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.rmi.PortableRemoteObject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import gov.cdc.nedss.act.publichealthcase.dt.PublicHealthCaseDT;
import gov.cdc.nedss.act.publichealthcase.vo.PublicHealthCaseVO;
import gov.cdc.nedss.association.dt.ActRelationshipDT;
import gov.cdc.nedss.association.dt.ParticipationDT;
import gov.cdc.nedss.exception.NEDSSAppConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.nbsactentity.dt.NbsActEntityDT;
import gov.cdc.nedss.page.ejb.pageproxyejb.dt.NbsAnswerDT;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.PageProxyVO;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.act.PageActProxyVO;
import gov.cdc.nedss.pagemanagement.wa.dt.BatchEntry;
import gov.cdc.nedss.pam.act.NbsCaseAnswerDT;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.bean.InvestigationProxy;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.bean.InvestigationProxyHome;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.InvestigationProxyVO;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.bean.PamProxy;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.bean.PamProxyHome;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.vo.PamProxyVO;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.util.EdxPHCRDocumentUtil;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.nbssecurity.ProgramAreaVO;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.systemservice.vo.ParticipationTypeVO;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.StringUtils;
import gov.cdc.nedss.webapp.nbs.action.pam.util.PamStoreUtil;
import gov.cdc.nedss.webapp.nbs.action.util.InvestigationUtil;
import gov.cdc.nedss.webapp.nbs.action.util.MessageLogUtil;
import gov.cdc.nedss.webapp.nbs.action.util.NBSPageConstants;
import gov.cdc.nedss.webapp.nbs.form.page.PageForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;


/**
 * Utility class to construct PageProxyVO out of PageClientVO and delegates to
 * PageProxyEJB for persistence.
 * 
 * @author Narendra Mallela
 *         <p>
 *         Copyright: Copyright (c) 2008
 *         </p>
 *         <p>
 *         Company: Computer Sciences Corporation
 *         </p>
 *         PageStoreUtil.java Jan 17, 2010
 * @version 1.0
 */
public class PageStoreUtil
{

    static final LogUtils             logger = new LogUtils(PageStoreUtil.class.getName());
    static final CachedDropDownValues srtc   = new CachedDropDownValues();

    /**
     * createHandler
     * 
     * @param form
     * @param req
     * @throws Exception
     */
    public static PageProxyVO createHandler(PageForm form, HttpServletRequest req) throws Exception
    {

        PageProxyVO proxyVO = null;
        try
        {
            NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) req.getSession().getAttribute("NBSSecurityObject");
            String userId = nbsSecurityObj.getTheUserProfile().getTheUser().getEntryID();
            PageCreateHelper pch = new PageCreateHelper();
            proxyVO = pch.create(form, req);
            if (form.getErrorList().size() == 0)
            {
            	
            	
            	
                String sCurrentTask = NBSContext.getCurrentTask(req.getSession());
                Long phcUID = ((PageActProxyVO)proxyVO).getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid();
        		String programArea = ((PageActProxyVO)proxyVO).getPublicHealthCaseVO().getThePublicHealthCaseDT().getProgAreaCd();
        		Long tempID = -1L;
                // Need to revisit
                setEntitiesForCreate(proxyVO, form, tempID, userId,phcUID, programArea,NEDSSConstants.CLASS_CD_CASE, req);
                if (sCurrentTask.equals("CreateInvestigation10") || sCurrentTask.equals("CreateInvestigation11") )
                {
                    createActRelationshipForDoc(sCurrentTask, proxyVO, req);
                }
                Long providerUid = nbsSecurityObj.getTheUserProfile().getTheUser().getProviderUid();

                MessageLogUtil.createMessageForUpdatedComments(form, (PageActProxyVO) proxyVO, providerUid);
                // send Proxy to EJB To Persist
                Long phcUid = sendProxyToPageEJB(proxyVO, req, sCurrentTask,
                        (String) form.getAttributeMap().get(NEDSSConstants.PROCESSING_DECISION));
                // Context
                setContextForCreate(proxyVO, phcUid, getProgAreaVO(req.getSession()), req.getSession());
            }
            else
            {
                form.setPageTitle(NBSPageConstants.CREATE_VARICELLA, req);
            }
        }
        catch (Exception e)
        {
            logger.fatal("Exception occured in PageStoreUtil.createHandler: " + ", " + e.getMessage(), e);
            throw new NEDSSAppException(e.getMessage(), e);
        }
        return proxyVO;
    }

    /**
     * createHandler
     * 
     * @param form
     * @param req
     * @throws Exception
     */
    public static PageProxyVO createGenericHandler(PageForm form, HttpServletRequest req) throws Exception
    {
        PageProxyVO proxyVO = null;
        try
        {
        	 PageCreateHelper pch = new PageCreateHelper();
            proxyVO = pch.createGeneric(form, req);
            if (form.getErrorList().size() == 0)
            {
                Long actUid = sendProxyToPageEJB(proxyVO, req, form.getBusinessObjectType());
                if (actUid != null)
                {
                    // store returned UID
                    if (form.getBusinessObjectType().equals(NEDSSConstants.INTERVIEW_BUSINESS_OBJECT_TYPE))
                        proxyVO.getInterviewVO().getTheInterviewDT().setInterviewUid(actUid);
                    else if( NEDSSConstants.VACCINATION_BUSINESS_OBJECT_TYPE.equals(form.getBusinessObjectType()))
                    	proxyVO.getInterventionVO().getTheInterventionDT().setInterventionUid(actUid);
                }
            }
        }
        catch (Exception e)
        {	
        	logger.fatal("Exception occured in PageStoreUtil.createGenericHandler: " + ", " + e.getMessage(), e);
        	throw new NEDSSAppException(e.getMessage(), e);
        }
        return proxyVO;
    }

    public static ProgramAreaVO getProgAreaVO(HttpSession session) throws NEDSSAppException
    {
        try {
			String conditionCd = (String) NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationCondition);
			String programArea = (String) NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationProgramArea);
			ProgramAreaVO programAreaVO = null;

			programAreaVO = srtc.getProgramAreaCondition("('" + programArea + "')", conditionCd);
			return programAreaVO;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal("Exception occured in PageStoreUtil.getProgAreaVO: " + ", " + e.getMessage(), e);
        	throw new NEDSSAppException(e.getMessage(), e);
		}
    }

    public static void setContextForCreate(PageProxyVO proxyVO, Long phcUid, ProgramAreaVO programArea,
            HttpSession session) throws NEDSSAppException
    {
        try {
			// context store
			String investigationJurisdiction = ((PageActProxyVO) proxyVO).getPublicHealthCaseVO()
			        .getThePublicHealthCaseDT().getJurisdictionCd();
			NBSContext.store(session, NBSConstantUtil.DSInvestigationUid, phcUid.toString());
			NBSContext.store(session, NBSConstantUtil.DSInvestigationJurisdiction, investigationJurisdiction);
			String progArea = (String) NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationProgramArea);
			NBSContext.store(session, NBSConstantUtil.DSInvestigationProgramArea, progArea);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("Exception occured in PageStoreUtil.setContextForCreate: phcUid: " + phcUid + ", " + e.getMessage(), e);
        	throw new NEDSSAppException(e.getMessage(), e);
		}
    }

    /**
     * 
     * @param proxyVO
     * @param request
     * @param sCurrentTask
     * @return
     * @throws NEDSSAppConcurrentDataException
     * @throws Exception
     */
    public static Long sendProxyToPageEJB(PageProxyVO proxyVO, HttpServletRequest request, String sCurrentTask,
            String processingDecision) throws NEDSSAppConcurrentDataException, Exception
    {

        try {
			HttpSession session = request.getSession();
			MainSessionCommand msCommand = null;
			Long publicHealthCaseUID = null;
			String sBeanJndiName = JNDINames.PAGE_PROXY_EJB;
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);
			ArrayList<?> resultUIDArr = new ArrayList<Object>();
			NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) request.getSession().getAttribute("NBSSecurityObject");
			translateAnswerDTsToCaseAnswerDTsAndCheckDuplicates(proxyVO,nbsSecurityObj);
			if (sCurrentTask != null
			        && (sCurrentTask.equals("CreateInvestigation2") || sCurrentTask.equals("CreateInvestigation3")
			                || sCurrentTask.equals("CreateInvestigation4") || sCurrentTask.equals("CreateInvestigation5")
			                || sCurrentTask.equals("CreateInvestigation6") || sCurrentTask.equals("CreateInvestigation7") || sCurrentTask
		                    .equals("CreateInvestigation9")|| sCurrentTask
		                    .equals("CreateInvestigation8")))
			{

			    String sMethod = "setPageProxyWithAutoAssoc";

			    Object sObservationUID = NBSContext.retrieve(session, NBSConstantUtil.DSObservationUID);
			    Object observationTypeCd = NBSContext.retrieve(session, NBSConstantUtil.DSObservationTypeCd);
			    Long DSObservationUID = new Long(sObservationUID.toString());
			    Object[] oParams = { NEDSSConstants.CASE, proxyVO, DSObservationUID, observationTypeCd.toString(),
			            processingDecision };
			    resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
			    publicHealthCaseUID = (Long) resultUIDArr.get(0);
			}
			else
			{
			    Object[] oParams = { NEDSSConstants.CASE, proxyVO };
			    String sMethod = "setPageProxyVO";
			    resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);

			    if ((resultUIDArr != null) && (resultUIDArr.size() > 0))
			    {
			        publicHealthCaseUID = (Long) resultUIDArr.get(0);
			    }

			}
			String changeCondition=(String)request.getAttribute("changeCondition");
			if(changeCondition!=null){
				if(changeCondition.equals("true")){
				//update CTContact table
				String sMethod = "changeCondition";
				Long phcUid = (Long) proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid();
				Object[] oParams = { NEDSSConstants.CASE, phcUid,
						proxyVO};
				msCommand.processRequest(sBeanJndiName, sMethod, oParams);
				}
			}
			return publicHealthCaseUID;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("Exception occured in PageStoreUtil.sendProxyToPageEJB: processingDecision: " + processingDecision + ", " + e.getMessage(), e);
        	throw new NEDSSAppException(e.getMessage(), e);
		}
    }

    
    public static Long sendProxyToPageEJBMerge(PageProxyVO proxyVO,PageProxyVO supersededProxyVO,PageProxyVO survivorProxyVOOld, String localIdSurvivor, String localIdSuperseded, HttpServletRequest request) 
    		throws NEDSSAppConcurrentDataException, Exception
    {

        try {
			HttpSession session = request.getSession();
			MainSessionCommand msCommand = null;
			Long publicHealthCaseUID = null;
			String sBeanJndiName = JNDINames.PAGE_PROXY_EJB;
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);
			ArrayList<?> resultUIDArr = new ArrayList<Object>();
			NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) request.getSession().getAttribute("NBSSecurityObject");
			translateAnswerDTsToCaseAnswerDTsAndCheckDuplicates(proxyVO,nbsSecurityObj);
			Object[] oParams = { NEDSSConstants.CASE, proxyVO, supersededProxyVO,survivorProxyVOOld, localIdSurvivor, localIdSuperseded};
			
		    String sMethod = "setPageProxyVOMerge";
		    resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);

		    if ((resultUIDArr != null) && (resultUIDArr.size() > 0))
		    {
		        publicHealthCaseUID = (Long) resultUIDArr.get(0);
		    }

			return publicHealthCaseUID;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("Exception occured in PageStoreUtil.sendProxyToPageEJBMerge: : " + e.getMessage(), e);
        	throw new NEDSSAppException(e.getMessage(), e);
		}
    }
    
    
    /**
     * 
     * @param proxyVO
     * @param request
     * @param businessObjectType
     *            i.e. IXS
     * @return
     * @throws NEDSSAppConcurrentDataException
     * @throws Exception
     */
    public static Long sendProxyToPageEJB(PageProxyVO proxyVO, HttpServletRequest request, String businessObjectType)
            throws NEDSSAppConcurrentDataException, Exception
    {
        try {
			HttpSession session = request.getSession();
			MainSessionCommand msCommand = null;
			Long actUID = null;
			String sBeanJndiName = JNDINames.PAGE_PROXY_EJB;
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);
			ArrayList<?> resultUIDArr = new ArrayList<Object>();

			Object[] oParams = { businessObjectType, proxyVO };
			String sMethod = "setPageProxyVO";
			resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);

			if ((resultUIDArr != null) && (resultUIDArr.size() > 0))
			{
			    actUID = (Long) resultUIDArr.get(0);
			}
			return actUID;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("Exception occured in PageStoreUtil.sendProxyToPageEJB: businessObjectType: " + businessObjectType + ", PublicHealthCaseUid: " + proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid() + ", " + e.getMessage(), e);
        	throw new NEDSSAppException(e.getMessage(), e);
		}
    }

    /**
     * editHandler
     * 
     * @param form
     * @param req
     * @throws Exception
     */
    public static PageProxyVO editHandler(PageForm form, HttpServletRequest req) throws Exception
    {
        try {
			PageProxyVO proxyVO = null;
			NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) req.getSession().getAttribute("NBSSecurityObject");
			String userId = nbsSecurityObj.getTheUserProfile().getTheUser().getEntryID();
			PageCreateHelper pch = new PageCreateHelper();
			proxyVO = pch.editHandler(form, req);
			if (form.getErrorList().size() == 0)
			{
				
			
			
				
			    PublicHealthCaseVO phcVO = ((PageActProxyVO) form.getPageClientVO().getOldPageProxyVO())
			            .getPublicHealthCaseVO();
		    
			    setEntitiesForEdit((PageActProxyVO) proxyVO, form, phcVO.getThePublicHealthCaseDT().getProgAreaCd(), 
			    		phcVO.getThePublicHealthCaseDT().getAddUserId(),
			    		phcVO.getThePublicHealthCaseDT().getAddTime(),
			    		userId, req);
			    
			    Long providerUid = nbsSecurityObj.getTheUserProfile().getTheUser().getProviderUid();
			    MessageLogUtil.createMessageForUpdatedComments(form, (PageActProxyVO) proxyVO, providerUid);
								    
				    PageActProxyVO pageActProxyVO = (PageActProxyVO) proxyVO;
				    PublicHealthCaseDT oldPublicHealthCaseDT = form.getPageClientVO().getOldPageProxyVO().getPublicHealthCaseVO().getThePublicHealthCaseDT();
				    PublicHealthCaseDT newPublicHealthCaseDT = proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT();
				    int newAnswerMapSize = pageActProxyVO.getPageVO().getPamAnswerDTMap().size();
				    int newAnswerBatchMapSize = pageActProxyVO.getPageVO().getPageRepeatingAnswerDTMap().size();
				    String userAgent = req.getHeader("user-agent");
				    String remoteIP = req.getRemoteAddr();
				    String requestURI = req.getRequestURI();
				    String sCurrentTask = NBSContext.getCurrentTask(req.getSession());
				    
				    logger.debug("********#Current Task :"+sCurrentTask);
				    logger.debug("********#Browser Used :"+userAgent);
				    logger.debug("********#Client IP Address Used :"+remoteIP);
				    logger.debug("********#Request URL :"+requestURI);
				    logger.debug("********#Old PublicHealthCaseDT :"+oldPublicHealthCaseDT.toString());
				    logger.debug("********#Updated PublicHealthCaseDT :"+newPublicHealthCaseDT.toString());
				    logger.debug("********#New AnswerMapSize :"+newAnswerMapSize+"");
				    logger.debug("********#New AnswerBatchMapSize :"+newAnswerBatchMapSize+"");
				    
					sendProxyToPageEJB(proxyVO, req, null, null);
			}
			else
			{
			    form.setPageTitle(NBSPageConstants.EDIT_VARICELLA, req);
			}
			return proxyVO;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("Exception occured in PageStoreUtil.sendProxyToPageEJB: PublicHealthCaseUid: " + form.getPageClientVO().getOldPageProxyVO().getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid() + ", " + e.getMessage(), e);
        	throw new NEDSSAppException(e.getMessage(), e);
		}
    }

    public static InvestigationProxyVO getInvestigationProxy(Long investigationUid, HttpServletRequest request)
            throws NEDSSAppConcurrentDataException, Exception
    {
        HttpSession session = request.getSession();
        MainSessionCommand msCommand = null;
        InvestigationProxyVO proxyVO = null;
        
        try {
        String sBeanJndiName = JNDINames.INVESTIGATION_PROXY_EJB;
        MainSessionHolder holder = new MainSessionHolder();
        msCommand = holder.getMainSessionCommand(session);
        ArrayList<?> resultUIDArr = new ArrayList<Object>();

        Object[] oParams = { investigationUid };
        String sMethod = "getInvestigationProxy";
        resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);

        if ((resultUIDArr != null) && (resultUIDArr.size() > 0))
        {
        	proxyVO = (InvestigationProxyVO) resultUIDArr.get(0);
        }
     	}catch (Exception e) {
     		logger.fatal("Exception occured in PageStoreUtil.getInvestigationProxy: investigationUid: " + investigationUid + ", " + e.getMessage(), e);
        	throw new NEDSSAppException(e.getMessage(), e);
    	}        
        return proxyVO;
    }
	/**
     * editGenericHandler - called from PageAction to process Interviews and
     * other generic pages.
     * 
     * @param form
     * @param req
     * @throws Exception
     */
    public static PageProxyVO editGenericHandler(PageForm form, HttpServletRequest req) throws Exception
    {
        try {
			PageProxyVO proxyVO = null;
			PageCreateHelper pch = new PageCreateHelper();
			proxyVO = pch.editGenericHandler(form, req);
			if (form.getErrorList().size() == 0)
			{
			    Long actUid = sendProxyToPageEJB(proxyVO, req, form.getBusinessObjectType());
			    if (actUid != null)
			        req.setAttribute("actUid", actUid.toString());
			}
			return proxyVO;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("Exception occured in PageStoreUtil.editGenericHandler: " + e.getMessage(), e);
        	throw new NEDSSAppException(e.getMessage(), e);
		}
    }

    /**
     * viewHandler
     * 
     * @param form
     * @param req
     * @throws Exception
     */
    public static void viewHandler(PageForm form, HttpServletRequest req) throws Exception
    {
        // PageViewHelper.viewHandler(form, req);
    }

    /**
     * setEntitiesForEdit creates Participations' and NBSActEntities' with types
     * of PRVs and ORGs associated with Varicella
     * 
     * @param proxyVO
     * @param form
     * @param revisionPatientUID
     * @param userId
     * @param request
     * @throws NEDSSAppException
     */ 
    public static void setEntitiesForCreate(PageProxyVO proxyVO, PageForm form, Long revisionPatientUID, String userId,
           Long actUid, String  programAreaCode, String objectType, HttpServletRequest request) throws NEDSSAppException
    {

        String stdFieldFollowupUid = form.getAttributeMap().get(NEDSSConstants.FOLLOWUP_INVESTIGATOR + "Uid") == null ? ""
                : (String) form.getAttributeMap().get(NEDSSConstants.FOLLOWUP_INVESTIGATOR + "Uid");
        String stdInterviewerUid = form.getAttributeMap().get(NEDSSConstants.INTERVIEW_INVESTIGATOR + "Uid") == null ? ""
                : (String) form.getAttributeMap().get(NEDSSConstants.INTERVIEW_INVESTIGATOR + "Uid");

        try
        {
            // ///////////////////////////////////////////////////////////////////////////////
            // Iterate through the Case Participation Types to see if they are
            // present
            // and if they are put them in the attribute map
            // /////////////////////////////////////////////////////////////////////////////// 
            
            TreeMap<Object, Object> participationTypeCaseMap = CachedDropDowns.getParticipationTypeList();

            Iterator parTypeIt = participationTypeCaseMap.values().iterator();
            while (parTypeIt.hasNext())
            {
                ParticipationTypeVO parTypeVO = (ParticipationTypeVO) parTypeIt.next();
                String entityUid = form.getAttributeMap().get(parTypeVO.getQuestionIdentifier() + "Uid") == null ? ""
                        : (String) form.getAttributeMap().get(parTypeVO.getQuestionIdentifier() + "Uid");

                // check couple of exceptions for STD setting initial
                // investigators..
                if (parTypeVO.getQuestionIdentifier() != null
                        && parTypeVO.getQuestionIdentifier().equalsIgnoreCase(
                                NEDSSConstants.INITIAL_FOLLOWUP_INVESTIGATOR)
                        && (entityUid == null || entityUid.isEmpty()) && !stdFieldFollowupUid.isEmpty())
                    entityUid = stdFieldFollowupUid;
                if (parTypeVO.getQuestionIdentifier() != null
                        && parTypeVO.getQuestionIdentifier().equalsIgnoreCase(
                                NEDSSConstants.INITIAL_INTERVIEW_INVESTIGATOR)
                        && (entityUid == null || entityUid.isEmpty()) && !stdInterviewerUid.isEmpty())
                    entityUid = stdInterviewerUid;

                
                //Check if the participation already existing in the participation collection:
                

                PageCreateHelper._setEntitiesForCreate(proxyVO, form, userId,  entityUid,
                        parTypeVO.getTypeCd(), parTypeVO.getSubjectClassCd(), actUid, programAreaCode,objectType,request);
            }
        }
        catch (NEDSSAppException e)
        {
            e.printStackTrace();
            logger.fatal("Exception occured in PageStoreUtil.setEntitiesForCreate: userId: " + userId + ", revisionPatientUID: " + revisionPatientUID + ", PublicHealthCaseUid: " + form.getPageClientVO().getOldPageProxyVO().getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid() + ", " + e.getMessage(), e);
        	throw new NEDSSAppException(e.getMessage(), e);
        }

    }

   
    /**
     * setEntitiesForEdit creates Participations' and NBSActEntities' with types
     * of PRVs and ORGs associated with Varicella
     * 
     * @param proxyVO
     * @param form
     * @param phcVO
     * @param userId
     * @param request
     */
    public static void setEntitiesForEdit(PageActProxyVO proxyVO, PageForm form, String programArea, Long addUserId, Timestamp addTime,
            String userId, HttpServletRequest request) throws NEDSSAppException
    {
        try {
			String stdFieldFollowupUid = form.getAttributeMap().get(NEDSSConstants.FOLLOWUP_INVESTIGATOR + "Uid") == null ? ""
			        : (String) form.getAttributeMap().get(NEDSSConstants.FOLLOWUP_INVESTIGATOR + "Uid");
			String stdInterviewerUid = form.getAttributeMap().get(NEDSSConstants.INTERVIEW_INVESTIGATOR + "Uid") == null ? ""
			        : (String) form.getAttributeMap().get(NEDSSConstants.INTERVIEW_INVESTIGATOR + "Uid");

			//String programArea = proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getProgAreaCd();
			NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) request.getSession().getAttribute("NBSSecurityObject");
			Long providerUid = nbsSecurityObj.getTheUserProfile().getTheUser().getProviderUid();

			PropertyUtil properties = PropertyUtil.getInstance();
			StringTokenizer st2 = new StringTokenizer(properties.getSTDProgramAreas(), ",");
			if (st2 != null)
			{
			    while (st2.hasMoreElements())
			    {
			        if (st2.nextElement().equals(programArea))
			        {
			            proxyVO.setSTDProgramArea(true);
			            break;
			        }
			    }
			}
			// ///////////////////////////////////////////////////////////////////////////////
			// Iterate through the Case Participation Types to see if they are
			// present
			// and if they are put them in the attribute map
			// ///////////////////////////////////////////////////////////////////////////////
			 
			TreeMap<Object, Object> participationTypeCaseMap = CachedDropDowns.getParticipationTypeList();
			Iterator parTypeIt = participationTypeCaseMap.values().iterator();
			while (parTypeIt.hasNext())
			{
			    ParticipationTypeVO parTypeVO = (ParticipationTypeVO) parTypeIt.next();
			    String entityUid = (form.getAttributeMap().get(parTypeVO.getQuestionIdentifier() + "Uid") == null || form.getAttributeMap().get(parTypeVO.getQuestionIdentifier() + "Uid").equals("")) ? null
			            : (String) form.getAttributeMap().get(parTypeVO.getQuestionIdentifier() + "Uid");

			    // check couple of exceptions for STD setting initial
			    // investigators..
			    if (parTypeVO.getQuestionIdentifier() != null
			            && parTypeVO.getQuestionIdentifier().equalsIgnoreCase(NEDSSConstants.INITIAL_FOLLOWUP_INVESTIGATOR)
			            && (entityUid == null || entityUid.isEmpty()) && !stdFieldFollowupUid.isEmpty())
			        entityUid = stdFieldFollowupUid;
			    if (parTypeVO.getQuestionIdentifier() != null
			            && parTypeVO.getQuestionIdentifier()
			                    .equalsIgnoreCase(NEDSSConstants.INITIAL_INTERVIEW_INVESTIGATOR)
			            && (entityUid == null || entityUid.isEmpty()) && !stdInterviewerUid.isEmpty())
			        entityUid = stdInterviewerUid;

			    PageCreateHelper.createOrDeleteParticipation(addUserId, addTime, entityUid, form, proxyVO, parTypeVO.getTypeCd(),
			            parTypeVO.getSubjectClassCd(), userId, providerUid);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("Exception occured in PageStoreUtil.setEntitiesForEdit: userId: " + userId + ", PublicHealthCaseUid: " + form.getPageClientVO().getOldPageProxyVO().getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid() + ", " + e.getMessage(), e);
        	throw new NEDSSAppException(e.getMessage(), e);
		}

    }

    public static void createActRelationshipForDoc(String sCurrentTask, PageProxyVO proxyVO, HttpServletRequest request) throws NEDSSAppException
    {
        try {
			HttpSession session = request.getSession();
			Object DSDocumentUID = NBSContext.retrieve(session, NBSConstantUtil.DSDocumentUID);
			ActRelationshipDT actDoc = new ActRelationshipDT();
			actDoc.setItNew(true);
			actDoc.setSourceActUid(new Long(DSDocumentUID.toString()));
			actDoc.setSourceClassCd(NEDSSConstants.ACT_CLASS_CD_FOR_DOC);
			actDoc.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
			actDoc.setTargetActUid(((PageActProxyVO) proxyVO).getPublicHealthCaseVO().getThePublicHealthCaseDT()
			        .getPublicHealthCaseUid());
			actDoc.setTargetClassCd(NEDSSConstants.CASE);
			actDoc.setRecordStatusCd(NEDSSConstants.ACTIVE);
			actDoc.setTypeCd(NEDSSConstants.DocToPHC);
			if (((PageActProxyVO) proxyVO).getPublicHealthCaseVO().getTheActRelationshipDTCollection() == null)
			{
			    Collection<Object> coll = new ArrayList<Object>();
			    coll.add(actDoc);
			    ((PageActProxyVO) proxyVO).getPublicHealthCaseVO().setTheActRelationshipDTCollection(coll);
			}
			else
			    ((PageActProxyVO) proxyVO).getPublicHealthCaseVO().getTheActRelationshipDTCollection().add(actDoc);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			logger.fatal("Exception occured in PageStoreUtil.createActRelationshipForDoc: sCurrentTask: " + sCurrentTask + ", PublicHealthCaseUid: " + proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid() + ", " + e.getMessage(), e);
        	throw new NEDSSAppException(e.getMessage(), e);
		}
    }

    public static void translateAnswerDTsToCaseAnswerDTsAndCheckDuplicates(PageProxyVO pageProxyVO, NBSSecurityObj nbsSecurityObj) throws NEDSSAppException
    {
        PageActProxyVO proxyVO = (PageActProxyVO) pageProxyVO;
        Map<Object, Object> answerMap = proxyVO.getPageVO().getAnswerDTMap();
        Map<Object, Object> pamAnswerMap = proxyVO.getPageVO().getPamAnswerDTMap();
        Map<Object, Object> repeatingAnswerMap = proxyVO.getPageVO().getPageRepeatingAnswerDTMap();
        Map<String, Object> duplicateMap = new HashMap<String, Object>();
        String user = nbsSecurityObj.getFullName();
        try
        {
            if (answerMap != null && answerMap.keySet().size() > 0)
            {
                Iterator<Object> ite = answerMap.keySet().iterator();
                while (ite.hasNext())
                {
                    Object key = ite.next();
                    // single entry fields
                    if (answerMap.get(key) instanceof NbsAnswerDT && !isDuplicateAnswerDT(duplicateMap, (NbsAnswerDT)answerMap.get(key), user))
                    {
                        answerMap.put(key, new NbsCaseAnswerDT((NbsAnswerDT) answerMap.get(key)));
                    }
                    // Multi-selects
                    else if (answerMap.get(key) instanceof ArrayList<?>)
                    {
                        ArrayList<?> aDTList = (ArrayList<?>) answerMap.get(key);
                        Collection<NbsCaseAnswerDT> caDTList = new ArrayList<NbsCaseAnswerDT>();
                        for (Object ansDT : aDTList)
                        {
                            if (ansDT instanceof NbsAnswerDT && !isDuplicateAnswerDT(duplicateMap, (NbsAnswerDT)ansDT, user))
                                caDTList.add(new NbsCaseAnswerDT((NbsAnswerDT) ansDT));
                            else
                            {
                                logger.debug("answerMap: Answer is not AnswerDT Instance or or its a duplicate AnswerDT");
                            }
                        }
                        answerMap.put(key, caDTList);
                    }
                    else
                    {
                        logger.debug("answerMap: Answer is not AnswerDT or ArrayList<?> Instance or it is a duplicate AnswerDT");
                    }
                }
            }
            if (pamAnswerMap != null && pamAnswerMap.keySet().size() > 0)
            {
                Iterator<Object> ite = pamAnswerMap.keySet().iterator();
                while (ite.hasNext())
                {
                    Object key = ite.next();
                    // single entry fields
                    if (pamAnswerMap.get(key) instanceof NbsAnswerDT && !isDuplicateAnswerDT(duplicateMap, (NbsAnswerDT)pamAnswerMap.get(key), user))
                    {
                        pamAnswerMap.put(key, new NbsCaseAnswerDT((NbsAnswerDT) pamAnswerMap.get(key)));
                    }
                    // Multi-selects
                    else if (pamAnswerMap.get(key) instanceof ArrayList<?>)
                    {
                        ArrayList<?> aDTList = (ArrayList<?>) pamAnswerMap.get(key);
                        Collection<NbsCaseAnswerDT> caDTList = new ArrayList<NbsCaseAnswerDT>();
                        for (Object ansDT : aDTList)
                        {
                            if (ansDT instanceof NbsAnswerDT && !isDuplicateAnswerDT(duplicateMap, (NbsAnswerDT)ansDT, user))
                                caDTList.add(new NbsCaseAnswerDT((NbsAnswerDT) ansDT));
                            else
                            {
                                logger.debug("pamAnswerMap: Answer is not AnswerDT Instance or it is a duplicate AnswerDT");
                            }
                        }
                        pamAnswerMap.put(key, caDTList);
                    }
                    else
                    {
                        logger.debug("pamAnswerMap: Answer is not AnswerDT or ArrayList<?> Instance or it is a duplicate AnswerDT");
                    }
                }
            }
            if (repeatingAnswerMap != null && repeatingAnswerMap.keySet().size() > 0)
            {
                Iterator<Object> ite = repeatingAnswerMap.keySet().iterator();
                Map<Object, Object> returnedMap = new HashMap<Object, Object>();
                while (ite.hasNext())
                {
                    Object key = ite.next();
                    if (repeatingAnswerMap.get(key) instanceof ArrayList<?>)
                    {
                        ArrayList<?> aDTList = (ArrayList<?>) repeatingAnswerMap.get(key);
                        Collection<NbsCaseAnswerDT> caDTList = new ArrayList<NbsCaseAnswerDT>();
                        for (Object ansDT : aDTList)
                        {
                            if (ansDT instanceof NbsAnswerDT && !isDuplicateAnswerDT(duplicateMap, (NbsAnswerDT)ansDT, user))
                                caDTList.add(new NbsCaseAnswerDT((NbsAnswerDT) ansDT));
                        }
                        returnedMap.put(key, caDTList);
                    }
                    else if (repeatingAnswerMap.get(key) instanceof NbsAnswerDT && !isDuplicateAnswerDT(duplicateMap, (NbsAnswerDT)repeatingAnswerMap.get(key), user))
                    {
                        returnedMap.put(key, new NbsCaseAnswerDT((NbsAnswerDT) repeatingAnswerMap.get(key)));
                    }
                    else
                    {
                        logger.debug("repeatingAnswerMap: Answer is not AnswerDT or ArrayList<?> Instance or it is a duplicate AnswerDT");
                    }
                }
                proxyVO.getPageVO().setPageRepeatingAnswerDTMap(returnedMap);

            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            logger.fatal("Exception occured in PageStoreUtil.translateAnswerDTsToCaseAnswerDTs: PublicHealthCaseUid: " + proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid() + ", " + ex.getMessage(), ex);
        	throw new NEDSSAppException(ex.getMessage(), ex);
        }

    }
    
	private static boolean isDuplicateAnswerDT(
			Map<String, Object> duplicateMap, NbsAnswerDT nbsAnswerDT,
			String user) {
		if (nbsAnswerDT != null) {
			String key = String.valueOf(nbsAnswerDT.getNbsQuestionUid())
					+ nbsAnswerDT.getSeqNbr()
					+ nbsAnswerDT.getAnswerGroupSeqNbr()
					+ nbsAnswerDT.isItDelete() + nbsAnswerDT.isItNew()
					+ nbsAnswerDT.isItDirty();
			if (duplicateMap.containsKey(key)) {
				logger.debug("The useer :"
						+ user
						+ " is trying to insert duplicate AnswerDT with public_health Case Uid "
						+ nbsAnswerDT.getActUid() + " with key " + key);
				return true;
			} else {
				duplicateMap.put(key, nbsAnswerDT);
				return false;

			}
		}
		return false;

	}

    /**
     * @param form
     * @param req
     * @return
     * @throws Exception
     */
    public static PageProxyVO mergeHandler(PageForm form, HttpServletRequest req) throws Exception
    {
        try {
			PageProxyVO proxyVO = null;
			NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) req.getSession().getAttribute("NBSSecurityObject");
			String userId = nbsSecurityObj.getTheUserProfile().getTheUser().getEntryID();
			PageCreateHelper pch = new PageCreateHelper();
			proxyVO = pch.editHandler(form, req);
			
			if (form.getErrorList().size() == 0)
			{
				 PageActProxyVO survivorProxyVOOld = ((PageActProxyVO) form.getPageClientVO().getOldPageProxyVO());
				 PageActProxyVO supersededProxyVO = (PageActProxyVO) form.getPageClientVO2().getOldPageProxyVO();

			    PageActProxyVO survivorProxyVO = (PageActProxyVO) proxyVO;
			    
				PublicHealthCaseVO survivorVO = ((PageActProxyVO) survivorProxyVO)
	                    .getPublicHealthCaseVO();
				
			    setEntitiesForEdit((PageActProxyVO) proxyVO, form, survivorProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getProgAreaCd(), 
			    		survivorProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getAddUserId(),
			    		survivorProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getAddTime(),
			    		userId, req);
			    

			   
			    
			    Long providerUid = nbsSecurityObj.getTheUserProfile().getTheUser().getProviderUid();
			    MessageLogUtil.createMessageForUpdatedComments(form, (PageActProxyVO) proxyVO, providerUid);
			    setActRelationshipForMerge((PageActProxyVO)proxyVO, form, req);

				String localIdSuperseded=(String)form.getAttributeMap2().get("caseLocalId");
				String localIdSurvivor=(String)form.getAttributeMap().get("caseLocalId");
				
				survivorVO.getThePublicHealthCaseDT().setItDirty(true); 
				survivorProxyVOOld.setItDirty(true);
				/*
				 * This is the code that sets the proxyVO as merge case so that same record is not updated multiple times
				 */
				((PageActProxyVO) proxyVO).setMergeCase(true);
				supersededProxyVO.setMergeCase(true);
				
			    sendProxyToPageEJBMerge(proxyVO,supersededProxyVO,survivorProxyVOOld, localIdSurvivor,localIdSuperseded, req);

			}
			return proxyVO;
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			logger.fatal("Exception occured in PageStoreUtil.mergeHandler: PublicHealthCaseUid: " + form.getPageClientVO().getOldPageProxyVO().getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid() + ", " + ex.getMessage(), ex);
        	throw new NEDSSAppException(ex.getMessage(), ex);
		}
    }
    
    private static void setActRelationshipForMerge(PageActProxyVO survivorProxyVO, PageForm form, HttpServletRequest req) throws NEDSSAppException{
    	try{
    		PageActProxyVO supersededProxyVO = (PageActProxyVO) form.getPageClientVO2().getOldPageProxyVO();
    		
    		for (Iterator<Object> anIterator = supersededProxyVO.getPublicHealthCaseVO().getTheActRelationshipDTCollection().iterator(); anIterator.hasNext();) {
				ActRelationshipDT actRelationshipDT = (ActRelationshipDT) anIterator.next();
				boolean found = false;
				if(actRelationshipDT!=null && !NEDSSConstants.ACT106_TYP_CD.equals(actRelationshipDT.getTypeCd())){
					
					for (Iterator<Object> anIteratorNew = survivorProxyVO.getPublicHealthCaseVO()
							.getTheActRelationshipDTCollection().iterator(); anIteratorNew.hasNext();) {
						
						ActRelationshipDT actRelationshipDTNew = (ActRelationshipDT) anIteratorNew.next();
					if(actRelationshipDT.getSourceActUid().equals(actRelationshipDTNew.getSourceActUid()) && actRelationshipDT.getSourceClassCd().equals(actRelationshipDTNew.getSourceClassCd())){
						 found = true;
						 break;
						}
					}
					if(!found){
						actRelationshipDT.setTargetActUid(survivorProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid());
						survivorProxyVO.getPublicHealthCaseVO().getTheActRelationshipDTCollection().add(actRelationshipDT);
						actRelationshipDT.setItNew(true);
						actRelationshipDT.setItDirty(false);
						actRelationshipDT.setItDelete(false);
					}
				
				}
    		}
    		survivorProxyVO.getPublicHealthCaseVO().setItDirty(true);

    	}catch(Exception ex){
    		logger.fatal("Exception occured in PageStoreUtil.setActRelationshipForMerge: PublicHealthCaseUid: " + form.getPageClientVO().getOldPageProxyVO().getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid() + ", " + ex.getMessage(), ex);
        	throw new NEDSSAppException(ex.getMessage(), ex);
    	}
    }
    
	public static void saveBatchRecordFromViewPage(BatchEntry be,
			HttpSession session, String businessObject,
			Map<Object, Object> attributeMap) {
		if (businessObject != null
				&& businessObject
						.equalsIgnoreCase(NEDSSConstants.LAB_BUSINESS_OBJECT_TYPE)) {
			try {
				LabCommonUtil.saveLabComments(be, session);
			} catch (Exception e) {
				logger.fatal("Exception while writing lab commnets: saveBatchRecordFromViewPage ");
				e.printStackTrace();
			}
		}
	}
	

	
    
}
