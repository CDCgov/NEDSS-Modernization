package gov.cdc.nedss.webapp.nbs.action.notification;

import java.io.*;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;

import gov.cdc.nedss.util.*;
import gov.cdc.nedss.webapp.nbs.action.util.InvestigationUtil;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.act.publichealthcase.dt.*;
import gov.cdc.nedss.act.publichealthcase.vo.*;
import gov.cdc.nedss.act.notification.dt.*;
import gov.cdc.nedss.act.notification.vo.*;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.bean.NotificationProxyHome;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.vo.*;
import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.association.dt.*;
import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.systemservice.util.*;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.*;



/**
 * Title:        NotificationSubmit
 * Description:  This is a summit action class for sending notification.
 * According to the Context Map<Object,Object> what the from page is and current task and security permission,
 * this method submits the notification and uses Struts to route the forward action.
 * Copyright:    Copyright (c) 2002
 * Company:      Computer Sciences Corporation
 * @author:      NEDSS Development Team
 * @version      NBS1.1
 */

public class NotificationSubmit
    extends Action
{

    //For logging
    static final LogUtils logger = new LogUtils(NotificationSubmit.class.getName());

    /**
     * According to the Context Map<Object,Object> what the from page is and current task and security permission,
     * this method submit a new notification.
     * @param ActionMapping
     * @param ActionForm
     * @param HttpServletRequest
     * @param HttpServletResponse
     * @return ActionForward
     * @throws IOException
     * @throws ServletException
     */
    public ActionForward execute(ActionMapping mapping, ActionForm aForm,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
                          throws IOException, ServletException
    {

        HttpSession session = request.getSession();
        String contextAction = request.getParameter("ContextAction");
        try {

        	if (contextAction.equals("Submit"))
        	{
        		String programeAreaCode = (String)NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationProgramArea);
        		String jurisdictionCode = (String)NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationJurisdiction);

        		String sharedInd = (String)NBSContext.retrieve(session, NBSConstantUtil.DSSharedInd);

        		String investigationUid=(String)NBSContext.retrieve(request.getSession(), NBSConstantUtil.DSInvestigationUid);
        		Long publicHealthCaseUid = new Long(investigationUid);

        		NBSContext.store(session, NBSConstantUtil.DSInvestigationUid, publicHealthCaseUid.toString());

        		NBSSecurityObj nbsSecurityObj = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");



        		boolean check1 = nbsSecurityObj.getPermission(
        				NBSBOLookup.NOTIFICATION,
        				NBSOperationLookup.CREATE,
        				programeAreaCode, jurisdictionCode,
        				sharedInd);
        		boolean check2 = nbsSecurityObj.getPermission(
        				NBSBOLookup.NOTIFICATION,
        				NBSOperationLookup.CREATENEEDSAPPROVAL,
        				programeAreaCode, jurisdictionCode,
        				sharedInd);

        		if ((!check1) && (!check2))
        		{
        			logger.fatal("Error: Do not have permission to create notification.");
        			return mapping.findForward(NEDSSConstants.ERROR_PAGE);
        		}

        		//M.Hankey iterate through the act relationship collection to see if there is
        		//an existing notification
        		PublicHealthCaseVO publicHealthCaseVO=InvestigationUtil.getPublicHealthCaseVO(publicHealthCaseUid, session);
        		Collection<Object>  arColl = publicHealthCaseVO.getTheActRelationshipDTCollection();
        		NotificationProxyVO notProxyVO = null;
        		String strNTF137 = request.getParameter("NTF137");
        		for(Iterator<Object> actRelIter = arColl.iterator();actRelIter.hasNext();){
        			ActRelationshipDT actDT = (ActRelationshipDT)actRelIter.next();
        			if(actDT.getTypeCd().equals(NEDSSConstants.ACT106_TYP_CD)){
        				//found existing notifacation, get vo for update
        				notProxyVO = this.getNotificationProxyVO(actDT.getSourceActUid(),session);

        				if(notProxyVO!=null && notProxyVO.getTheNotificationVO().getTheNotificationDT().getCd().equalsIgnoreCase(NEDSSConstants.CLASS_CD_NOTF)){
        					notProxyVO.setItDirty(true);
        					notProxyVO.getTheNotificationVO().setItDirty(true);

        					NotificationDT notDTu = notProxyVO.getTheNotificationVO().getTheNotificationDT();
        					notDTu.setAddTime(new java.sql.Timestamp(new Date().getTime()));
        					notDTu.setTxt(strNTF137);
        					notDTu.setCaseClassCd(publicHealthCaseVO.getThePublicHealthCaseDT().getCaseClassCd());
        					notDTu.setStatusCd("A");
        					notDTu.setStatusTime(new java.sql.Timestamp(new Date().getTime()));
        					notDTu.setSharedInd(sharedInd);
        					notDTu.setItNew(false);
        					notDTu.setItDirty(true);
        					break;
        				}
        				else
        					notProxyVO = null;
        			}
        		}
        		//if null, create new vo
        		if(notProxyVO == null){

        			PublicHealthCaseVO phcVO = new PublicHealthCaseVO();
        			phcVO.setThePublicHealthCaseDT(publicHealthCaseVO.getThePublicHealthCaseDT());
        			phcVO.setItNew(false);
        			phcVO.setItDirty(false);

        			// Create the Notification object

        			NotificationDT notDT = new NotificationDT();
        			notDT.setItNew(true);
        			notDT.setNotificationUid(new Long(-1));
        			notDT.setAddTime(new java.sql.Timestamp(new Date().getTime()));
        			notDT.setTxt(strNTF137);
        			notDT.setStatusCd("A");
        			notDT.setCaseClassCd(publicHealthCaseVO.getThePublicHealthCaseDT().getCaseClassCd());
        			notDT.setStatusTime(new java.sql.Timestamp(new Date().getTime()));
        			notDT.setVersionCtrlNbr(new Integer(1));
        			notDT.setSharedInd(sharedInd);
        			notDT.setCaseConditionCd(publicHealthCaseVO.getThePublicHealthCaseDT().getCd());
        			notDT.setAutoResendInd("F");

        			NotificationVO notVO = new NotificationVO();
        			notVO.setTheNotificationDT(notDT);
        			notVO.setItNew(true);

        			// create the act relationship between the phc & notification
        			ActRelationshipDT actDT1 = new ActRelationshipDT();
        			actDT1.setItNew(true);
        			actDT1.setTargetActUid(publicHealthCaseUid);
        			actDT1.setSourceActUid(notDT.getNotificationUid());
        			actDT1.setAddTime(new java.sql.Timestamp(new Date().getTime()));
        			actDT1.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
        			actDT1.setSequenceNbr(new Integer(1));
        			actDT1.setStatusCd("A");
        			actDT1.setStatusTime(new java.sql.Timestamp(new Date().getTime()));
        			actDT1.setTypeCd(NEDSSConstants.ACT106_TYP_CD);
        			actDT1.setSourceClassCd(NEDSSConstants.ACT106_SRC_CLASS_CD);
        			actDT1.setTargetClassCd(NEDSSConstants.ACT106_TAR_CLASS_CD);

        			notProxyVO = new NotificationProxyVO();
        			notProxyVO.setItNew(true);
        			notProxyVO.setThePublicHealthCaseVO(phcVO);
        			notProxyVO.setTheNotificationVO(notVO);

        			ArrayList<Object> actRelColl = new ArrayList<Object> ();
        			actRelColl.add(0, actDT1);
        			notProxyVO.setTheActRelationshipDTCollection(actRelColl);
        		}

        		String publicHealthCaseLocalUid = publicHealthCaseVO.getThePublicHealthCaseDT().getLocalId();
        		String conditionCdDescTxt = publicHealthCaseVO.getThePublicHealthCaseDT().getCdDescTxt();

        		HashMap<Object,Object> notifResult = this.sendProxyToEJB(notProxyVO, nbsSecurityObj, request);

        		if (notifResult == null)
        		{
        			logger.error("There was an error creating the proxy object, redirect to the submit error");

        			return (mapping.findForward(contextAction));
        		}

        		Long returnUID = (Long)notifResult.get("notificationUid");
        		logger.debug("Value of uid is: " + returnUID);
        		session.setAttribute("notificationUID", returnUID);

        		String invFormCd = request.getParameter("invFormCd");
        		//Check and see if the formCd = TB or Varicella or Malaria etc.... and populate message accordingly...
        		if(invFormCd != null && (invFormCd.equals(NBSConstantUtil.INV_FORM_RVCT)|| (invFormCd.equals(NBSConstantUtil.INV_FORM_VAR)) || (invFormCd.equals(NBSConstantUtil.INV_FORM_MALR)) ) ) {    	  
        			request.setAttribute("PamNotificationMessage", "Notification is created.");
        		}

        		Boolean sendedMsg = (Boolean)notifResult.get("sendMsg");
        		String notificationCreated = null;

        		if (sendedMsg != null)
        			notificationCreated = sendedMsg.toString();
        	}
        	else if(contextAction.equals("EditInvestigation"))
        	{

        		String commentsValue = request.getParameter("NTF137");
        		if(commentsValue != null && commentsValue.trim().length() > 0)
        			NBSContext.store(session, NBSConstantUtil.DSNotificationComments, commentsValue);
        		else
        			NBSContext.store(session, NBSConstantUtil.DSNotificationComments, "");
        	}
        }catch (Exception e) {
        	logger.error("Exception in NotificationSubmit: " + e.getMessage());
        	e.printStackTrace();
        	throw new ServletException("Error occurred in Notification Submit : "+e.getMessage());
        }

        logger.debug(" leaving notificationSubmit, contextAction: " +  contextAction);

        return mapping.findForward(contextAction);
    }

     /**
     * This is a private method to send a NotificationProxyVO to NotificationProxyEJB to create a new notification.
     * This method calls setNotificationProxy() method in NotificationProxyEJB
     * through MainSessionCommandEJB
     * @param notificationProxyVO
     * @param nbsSecurityObj
     * @param session
     */
    private HashMap<Object,Object> sendProxyToEJB(NotificationProxyVO notificationProxyVO,
                                   NBSSecurityObj nbsSecurityObj,
                                   HttpServletRequest req)
    {


        logger.info("Notification create -- sendProxyToEJB");

        Long realNotificationUid = null;
        HttpSession session = req.getSession();

        try
        {
            String sBeanJndiName = JNDINames.NOTIFICATION_PROXY_EJB;
            String sMethod = "setNotificationProxy";
            logger.debug("sending proxy to NotificationProxyEJBRef, via mainsession");

            /*** get ProgramAreaCd from PhcDT and set into notifDT */
            String programAreaCd = notificationProxyVO.getThePublicHealthCaseVO().getThePublicHealthCaseDT().getProgAreaCd();
            logger.debug("programAreaCd: " + programAreaCd);

            NotificationVO notifVO = notificationProxyVO.getTheNotificationVO();
            NotificationDT notifDT = notifVO.getTheNotificationDT();
            notifDT.setProgAreaCd(programAreaCd);
            notifVO.setTheNotificationDT(notifDT);
            notificationProxyVO.setTheNotificationVO(notifVO);

            Object[] oParams = { notificationProxyVO };
            MainSessionHolder mainSessionHolder = new MainSessionHolder();
            MainSessionCommand msCommand = mainSessionHolder.getMainSessionCommand(req.getSession());

            ArrayList<?> resultUIDArr = new ArrayList<Object> ();

            // calling NotificationProxyEJBRef.setNotificationProxy ----------
            resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);

            if ((resultUIDArr != null) && (resultUIDArr.size() > 0))
            {
                logger.info("Set notification worked!!! notificationUID = " + resultUIDArr.get(0));
                realNotificationUid = (Long)resultUIDArr.get(0);

                HashMap<Object,Object> result = new HashMap<Object,Object>();
                String pending = "";

                if (nbsSecurityObj.getPermission(NBSBOLookup.NOTIFICATION,
                                                 NBSOperationLookup.CREATE))
                {
                    pending = "false";
                    session.setAttribute("pending", pending);
                }
                else
                {
                    pending = "true";
                    session.setAttribute("pending", pending);
                }

                result.put("notificationUid", realNotificationUid);
                return result;
            }
            else
                return null;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            logger.fatal("ERROR in create notification", e);
            req.setAttribute("PamNotificationMessage", "Notification cannot be Created.");
            return null;
        }
    }//sendProxyToEJB

    private NotificationProxyVO getNotificationProxyVO(Long notificationUID,HttpSession session)
    {

        NotificationProxyVO proxy = null;
        MainSessionCommand msCommand = null;
        if (notificationUID != null)
        {

            try
            {

                String sBeanJndiName = JNDINames.NOTIFICATION_PROXY_EJB;
                String sMethod = "getNotificationProxy";
                Object[] oParams = new Object[] { notificationUID };

                //  if(msCommand == null)
                //{
                MainSessionHolder holder = new MainSessionHolder();
                msCommand = holder.getMainSessionCommand(session);

                // }
                ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
                proxy = (NotificationProxyVO)arr.get(0);

            }
            catch (Exception ex)
            {

                if (session == null)
                {
                    logger.error("Error: no session, please login");
                }

                logger.fatal("getOldProxyObject: ", ex);
                
            }
        }

        return proxy;
    }//getNotificationProxyVO

}