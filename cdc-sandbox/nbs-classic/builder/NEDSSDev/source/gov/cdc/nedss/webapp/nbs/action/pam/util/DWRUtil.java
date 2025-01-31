package gov.cdc.nedss.webapp.nbs.action.pam.util;

import gov.cdc.nedss.act.notification.dt.NotificationDT;
import gov.cdc.nedss.act.notification.vo.NotificationVO;
import gov.cdc.nedss.act.publichealthcase.dt.PublicHealthCaseDT;
import gov.cdc.nedss.act.publichealthcase.vo.PublicHealthCaseVO;
import gov.cdc.nedss.association.dt.ActRelationshipDT;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.PageProxyVO;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.act.PageActProxyVO;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.bean.NotificationProxyHome;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.vo.NotificationProxyVO;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.NbsQuestionMetadata;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageLoadUtil;
import gov.cdc.nedss.webapp.nbs.action.util.DecoratorUtil;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.QuestionsCache;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.vo.PamProxyVO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class DWRUtil
{

    static final LogUtils                  logger         = new LogUtils(DWRUtil.class.getName());
    private static HashMap<Object, Object> nndRequiredMap = new HashMap<Object, Object>();

    public static ArrayList<Object> updateNotifications(PublicHealthCaseVO publicHealthCaseVO, String comments,
            HttpServletRequest req, String invFormCd) throws NEDSSAppException
    {

        ArrayList<Object> returnList = new ArrayList<Object>();
        HttpSession session = req.getSession();

        PublicHealthCaseDT phcDT = publicHealthCaseVO.getThePublicHealthCaseDT();
        Long phcUID = phcDT.getPublicHealthCaseUid();
        String programeAreaCode = phcDT.getProgAreaCd();
        String jurisdictionCode = phcDT.getJurisdictionCd();
        String sharedInd = phcDT.getSharedInd();
        NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session.getAttribute(NEDSSConstants.NBS_SECURITY_OBJECT);

        boolean check1 = nbsSecurityObj.getPermission(NBSBOLookup.NOTIFICATION, NBSOperationLookup.CREATE,
                programeAreaCode, jurisdictionCode, sharedInd);
        boolean check2 = nbsSecurityObj.getPermission(NBSBOLookup.NOTIFICATION, NBSOperationLookup.CREATENEEDSAPPROVAL,
                programeAreaCode, jurisdictionCode, sharedInd);

        if ((!check1) && (!check2))
        {
            returnList.add("You do not have permission to create Notification.");
            return returnList;
        }

        Collection<Object> arColl = publicHealthCaseVO.getTheActRelationshipDTCollection();
        NotificationProxyVO notProxyVO = null;
        for (Iterator<Object> actRelIter = arColl.iterator(); actRelIter.hasNext();)
        {
            ActRelationshipDT actDT = (ActRelationshipDT) actRelIter.next();
            if (  actDT.getTypeCd().equals(NEDSSConstants.ACT106_TYP_CD) && actDT.isNNDInd())
            {
                notProxyVO = getNotificationProxyVO(actDT.getSourceActUid(), session);
                notProxyVO.setItDirty(true);
                notProxyVO.getTheNotificationVO().setItDirty(true);

                NotificationDT notDTu = notProxyVO.getTheNotificationVO().getTheNotificationDT();
                notDTu.setAddTime(new java.sql.Timestamp(new Date().getTime()));
                notDTu.setTxt(comments);
                notDTu.setCaseClassCd(phcDT.getCaseClassCd());
                notDTu.setStatusCd("A");
                notDTu.setStatusTime(new java.sql.Timestamp(new Date().getTime()));
                notDTu.setSharedInd(sharedInd);
                notDTu.setItNew(false);
                notDTu.setItDirty(true);
                notDTu.setMmwrWeek(phcDT.getMmwrWeek() );
                notDTu.setMmwrYear(phcDT.getMmwrYear() );
                break;
            } 
        }
        // if null, create new vo
        if (notProxyVO == null)
        {
            PublicHealthCaseVO phcVO = new PublicHealthCaseVO();
            phcVO.setThePublicHealthCaseDT(phcDT);
            phcVO.setItNew(false);
            phcVO.setItDirty(false);

            // Create the Notification object

            NotificationDT notDT = new NotificationDT();
            notDT.setItNew(true);
            notDT.setNotificationUid(new Long(-1));
            notDT.setAddTime(new java.sql.Timestamp(new Date().getTime()));
            notDT.setTxt(comments);
            notDT.setStatusCd("A");
            notDT.setCaseClassCd(phcDT.getCaseClassCd());
            notDT.setStatusTime(new java.sql.Timestamp(new Date().getTime()));
            notDT.setVersionCtrlNbr(new Integer(1));
            notDT.setSharedInd(sharedInd);
            notDT.setCaseConditionCd(phcDT.getCd());
            notDT.setAutoResendInd("F");
            notDT.setMmwrWeek( phcDT.getMmwrWeek() );
            notDT.setMmwrYear(phcDT.getMmwrYear() );

            NotificationVO notVO = new NotificationVO();
            notVO.setTheNotificationDT(notDT);
            notVO.setItNew(true);

            // create the act relationship between the phc & notification
            ActRelationshipDT actDT1 = new ActRelationshipDT();
            actDT1.setItNew(true);
            actDT1.setTargetActUid(phcUID);
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

            ArrayList<Object> actRelColl = new ArrayList<Object>();
            actRelColl.add(0, actDT1);
            notProxyVO.setTheActRelationshipDTCollection(actRelColl);
        }

        HashMap<Object, Object> notifResult = sendProxyToEJB(notProxyVO, nbsSecurityObj, req);
        ArrayList<Object> notifSummary = null;
        if (notifResult == null)
        {
            returnList.add("There was an error while creating Notification.");
        }
        else if (invFormCd != null
                && (invFormCd.equalsIgnoreCase(NBSConstantUtil.INV_FORM_RVCT) || invFormCd
                        .equalsIgnoreCase(NBSConstantUtil.INV_FORM_VAR)))
        {
            returnList.add("A Notification has been created for this Investigation.");
            PamProxyVO proxyVO = PamLoadUtil.getProxyObject(String.valueOf(phcUID), session);
            // getPublicHealthCaseVO of PamProxyEJB
            publicHealthCaseVO.setTheActRelationshipDTCollection(proxyVO.getPublicHealthCaseVO()
                    .getTheActRelationshipDTCollection());
            notifSummary = (ArrayList<Object>) proxyVO.getTheNotificationSummaryVOCollection();
            DecoratorUtil util = new DecoratorUtil();
            String notifDisplayHTML = util.buildNotificationList(notifSummary);
            req.setAttribute("notificationList", notifDisplayHTML);
            returnList.add(notifDisplayHTML);
        }
        else
        {
            returnList.add("A Notification has been created for this Investigation.");
            PageProxyVO proxyVO = PageLoadUtil.getProxyObject(String.valueOf(phcUID), session);
            // getPublicHealthCaseVO of PageProxyEJB
            Collection<Object> actRelationshipDTColl = ((PageActProxyVO) proxyVO).getPublicHealthCaseVO()
                    .getTheActRelationshipDTCollection();

            publicHealthCaseVO.setTheActRelationshipDTCollection(actRelationshipDTColl);
            notifSummary = (ArrayList<Object>) ((PageActProxyVO) proxyVO).getTheNotificationSummaryVOCollection();
            DecoratorUtil util = new DecoratorUtil();
            String notifDisplayHTML = util.buildNotificationList(notifSummary);
            req.setAttribute("notificationList", notifDisplayHTML);
            returnList.add(notifDisplayHTML);
        }
        return returnList;
    }

    private static HashMap<Object, Object> sendProxyToEJB(NotificationProxyVO notificationProxyVO,
            NBSSecurityObj nbsSecurityObj, HttpServletRequest req)
    {
        Long realNotificationUid = null;
        HttpSession session = req.getSession();

        try
        {
            String sBeanJndiName = JNDINames.NOTIFICATION_PROXY_EJB;
            String sMethod = "setNotificationProxy";
            String programAreaCd = notificationProxyVO.getThePublicHealthCaseVO().getThePublicHealthCaseDT()
                    .getProgAreaCd();
            NotificationVO notifVO = notificationProxyVO.getTheNotificationVO();
            NotificationDT notifDT = notifVO.getTheNotificationDT();
            notifDT.setProgAreaCd(programAreaCd);
            notifVO.setTheNotificationDT(notifDT);
            notificationProxyVO.setTheNotificationVO(notifVO);

            Object[] oParams = { notificationProxyVO };
            MainSessionHolder mainSessionHolder = new MainSessionHolder();
            MainSessionCommand msCommand = mainSessionHolder.getMainSessionCommand(req.getSession());
            ArrayList<?> resultUIDArr = new ArrayList<Object>();
            resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
            if ((resultUIDArr != null) && (resultUIDArr.size() > 0))
            {
                realNotificationUid = (Long) resultUIDArr.get(0);
                HashMap<Object, Object> result = new HashMap<Object, Object>();
                String pending = "";

                if (nbsSecurityObj.getPermission(NBSBOLookup.NOTIFICATION, NBSOperationLookup.CREATE))
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
            logger.error(e.toString());
            return null;
        }
    }// sendProxyToEJB

    private static NotificationProxyVO getNotificationProxyVO(Long notificationUID, HttpSession session)
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
                MainSessionHolder holder = new MainSessionHolder();
                msCommand = holder.getMainSessionCommand(session);
                ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
                proxy = (NotificationProxyVO) arr.get(0);
            }
            catch (Exception ex)
            {
                if (session == null)
                {
                    logger.error("Error: no session, please login");
                }
            }
        }
        return proxy;
    }

    /**
     * getRequiredFieldsNotif: returns a list of pairs Question Identifier | Label of the required fields for creating a NND notification
     * @param publicHealthCaseVO
     * @param questionMap
     * @param formCd
     * @param request
     * @return
     * @throws Exception
     */
    public static  Collection<Object> getRequiredFieldsNotif(PublicHealthCaseVO publicHealthCaseVO, Map<?, ?> questionMap,
            String formCd, HttpServletRequest request) throws Exception
    {

        PublicHealthCaseDT phcDT = publicHealthCaseVO.getThePublicHealthCaseDT();
        Collection<Object> notifReqColl = new ArrayList<Object>();
        // calls NotificationProxy, and returns values if validated fields are
        // missing
        Long publicHealthCaseUid = phcDT.getPublicHealthCaseUid();
        Map<?, ?> resultFromEJB = null;
        try
        {
            
            if (QuestionsCache.getQuestionMapEJBRef() != null)
            {
                notifReqColl = QuestionsCache.getQuestionMapEJBRef().retrieveQuestionRequiredNnd(formCd);
            }
        //    resultFromEJB = (TreeMap<?, ?>) sendProxyToEJBValidate(publicHealthCaseUid, notifReqColl, formCd, request);
       //     request.getSession().setAttribute("NotifReqMap", resultFromEJB);
        }
        catch (Exception ex)
        {
            logger.error("Error while calling EJB in DWRUtil Notif Required Fields: " + ex.toString());
            throw new Exception(ex.toString());
        }

        return notifReqColl;
    }
    
    
    public static Map<?, ?> createNotification(PublicHealthCaseVO publicHealthCaseVO, Map<?, ?> questionMap,
            String formCd, HttpServletRequest request) throws Exception
    {

        PublicHealthCaseDT phcDT = publicHealthCaseVO.getThePublicHealthCaseDT();
        // calls NotificationProxy, and returns values if validated fields are
        // missing
        Long publicHealthCaseUid = phcDT.getPublicHealthCaseUid();
        Map<?, ?> resultFromEJB = null;
        try
        {
            Collection<Object> notifReqColl = new ArrayList<Object>();
            if (QuestionsCache.getQuestionMapEJBRef() != null)
            {
                notifReqColl = QuestionsCache.getQuestionMapEJBRef().retrieveQuestionRequiredNnd(formCd);
            }
            resultFromEJB = (TreeMap<?, ?>) sendProxyToEJBValidate(publicHealthCaseUid, notifReqColl, formCd, request);
            request.getSession().setAttribute("NotifReqMap", resultFromEJB);
        }
        catch (Exception ex)
        {
            logger.error("Error while calling EJB in DWRUtil Notif Required Fields: " + ex.toString());
            throw new Exception(ex.toString());
        }

        return resultFromEJB;
    }

    /**
     * 
     * @param publicHealthCaseUID
     * @param map
     * @param notifReqColl
     * @param formCd
     * @param req
     * @return
     * @throws Exception
     */
    private static Map<?, ?> sendProxyToEJBValidate(Long publicHealthCaseUID, Collection<Object> notifReqColl,
            String formCd, HttpServletRequest req) throws Exception
    {

        MainSessionCommand msCommand = null;

        // Identify the req fields for Notification from questionMap where
        // question_required_nnd='R'
        if (nndRequiredMap.get(formCd) == null || ((HashMap<?, ?>) nndRequiredMap.get(formCd)).size() == 0)
        {
            Map<Object, Object> subMap = new HashMap<Object, Object>();
            if (notifReqColl != null && notifReqColl.size() > 0)
            {
                Iterator<Object> iter = notifReqColl.iterator();
                while (iter.hasNext())
                {
                    NbsQuestionMetadata metaData = (NbsQuestionMetadata) iter.next();
                    subMap.put(metaData.getNbsQuestionUid(), metaData);
                }
            }
            nndRequiredMap.put(formCd, subMap);
        }

        Map<?, ?> result = null;
        try
        {

            String sBeanJndiName = JNDINames.NOTIFICATION_PROXY_EJB;
            String sMethod = "validatePAMNotficationRequiredFields";
            Object[] oParams = { publicHealthCaseUID, nndRequiredMap.get(formCd), formCd };
            MainSessionHolder holder = new MainSessionHolder();
            msCommand = holder.getMainSessionCommand(req.getSession());

            ArrayList<?> resultUIDArr = new ArrayList<Object>();
            resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
            if ((resultUIDArr != null) && (resultUIDArr.size() > 0))
            {
                result = (TreeMap<?, ?>) resultUIDArr.get(0);
            }
        }
        catch (Exception e)
        {
            logger.error("Error in sendProxyToEJBValidate for Notifications: " + e.toString());
            throw new Exception(e.toString());
        }
        return result;
    } 
    
    public static String checkMorbForCaseAssociations(Long ObservationUid, HttpServletRequest req) {
		String localId = null;
		try {
			String sBeanJndiName = JNDINames.OBSERVATION_PROXY_EJB;
			String sMethod = "checkMorbForCaseAssociations";
			Object[] oParams = { ObservationUid };
			MainSessionHolder mainSessionHolder = new MainSessionHolder();
			MainSessionCommand msCommand = mainSessionHolder.getMainSessionCommand(req.getSession());
			ArrayList<?> resultUIDArr = new ArrayList<Object>();
			resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
			if ((resultUIDArr != null) && (resultUIDArr.size() > 0)) {
				localId = (String) resultUIDArr.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.toString());
			return null;
		}
		return localId;
	}// checkMorbForCaseAssociations
}
