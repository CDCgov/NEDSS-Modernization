package gov.cdc.nedss.proxy.ejb.notificationproxyejb.dao;

import gov.cdc.nedss.act.notification.dt.NotificationDT;
import gov.cdc.nedss.act.notification.vo.NotificationVO;
import gov.cdc.nedss.act.publichealthcase.vo.PublicHealthCaseVO;
import gov.cdc.nedss.act.sqlscript.WumSqlQuery;
import gov.cdc.nedss.association.dao.ActRelationshipDAOImpl;
import gov.cdc.nedss.association.dt.ActRelationshipDT;
import gov.cdc.nedss.controller.ejb.actcontrollerejb.bean.ActController;
import gov.cdc.nedss.controller.ejb.actcontrollerejb.bean.ActControllerHome;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.vo.NotificationProxyVO;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.vo.NotificationSummaryVO;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.vo.RejectedNotificationSummaryVO;
import gov.cdc.nedss.systemservice.ejb.nbssecurityejb.helper.NBSAuthHelper;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.nbssecurity.ProgramAreaJurisdictionUtil;
import gov.cdc.nedss.systemservice.util.PrepareVOUtils;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.DataTables;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.StringUtils;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

import java.net.URLEncoder;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeMap;

import javax.rmi.PortableRemoteObject;

/**
 * Title: NotificationProxyDaoImpl Description: handles calls to the database
 * for getting and setting the notifications Copyright: Copyright (c) 2001
 * Company:CSC
 * 
 * @author nedss project team
 * @version 1.0
 */

public class NotificationProxyDaoImpl extends DAOBase
{
    public NotificationProxyDaoImpl()
    {
    }

    private static final LogUtils logger = new LogUtils(NotificationProxyDaoImpl.class.getName());

    /**
     * getNotificationsForApproval forerly getNotificationProxyVOs formerly
     * getNotificationVOs
     * 
     * @J2EE_METHOD -- getNotificationsForApproval returns validation
     *              information for BMIRD Stored Procedure
     * @param NBSSecurityObj
     * @returns Collection
     */
    @SuppressWarnings("unchecked")
    public Collection<Object> getNotificationsForApproval(NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException,
            NEDSSSystemException
    {
        String selectstatment = null;
        ArrayList<Object> notificationProxyVOCollection = new ArrayList<Object>();
        NotificationSummaryVO notificationSummaryVO = new NotificationSummaryVO();

        try
        {
            if (!nbsSecurityObj.getPermission(NBSBOLookup.NOTIFICATION, NBSOperationLookup.REVIEW,
                    ProgramAreaJurisdictionUtil.ANY_PROGRAM_AREA, ProgramAreaJurisdictionUtil.ANY_JURISDICTION))
            {
                logger.error("getNotificationVOs - NO PERMISSIONS FOR GET NOTIFICATIONS FOR REVIEW");
                throw new NEDSSSystemException("NO PERMISSIONS FOR GET NOTIFICATIONS FOR REVIEW");
            }

            String dataAccessWhereClause = nbsSecurityObj.getDataAccessWhereClause(NBSBOLookup.NOTIFICATION,
                    NBSOperationLookup.REVIEW, " Notification ");

            if (dataAccessWhereClause == null)
                dataAccessWhereClause = "";
            else
                dataAccessWhereClause = " AND " + dataAccessWhereClause;

                selectstatment = WumSqlQuery.NOTIFICATIONS_FOR_APPROVAL_SQL + dataAccessWhereClause
                        + " order by Public_health_case.Public_health_case_Uid, Notification.cd desc";

            notificationProxyVOCollection = (ArrayList<Object>) preparedStmtMethod(notificationSummaryVO, null,
                    selectstatment, NEDSSConstants.SELECT);
            CachedDropDownValues cache = new CachedDropDownValues(); 
            TreeMap<Object, Object> mapConditionCode = cache.getConditionCodes();
            TreeMap<Object, Object> mapJurisdiction = cache.getJurisdictionCodedValuesWithAll();

            String phcLocal = null;
            boolean associatedShare = false;
            boolean associatedNOTF = false;

            for (Iterator<Object> anIterator = notificationProxyVOCollection.iterator(); anIterator.hasNext();)
            {
                notificationSummaryVO = (NotificationSummaryVO) anIterator.next();
                if (notificationSummaryVO.getNotificationCd().equalsIgnoreCase(NEDSSConstants.CLASS_CD_SHARE_NOTF) || notificationSummaryVO.getNotificationCd().equalsIgnoreCase(NEDSSConstants.CLASS_CD_SHARE_NOTF_PHDC) )
                {
                    phcLocal = notificationSummaryVO.getPublicHealthCaseLocalId();
                    associatedShare = true;
                }
                else if (notificationSummaryVO.getNotificationCd().equalsIgnoreCase(NEDSSConstants.CLASS_CD_NOTF))
                {

                    if (phcLocal != null
                            && phcLocal.equalsIgnoreCase(notificationSummaryVO.getPublicHealthCaseLocalId()))
                    {
                        associatedNOTF = true;
                    }
                    else
                    {
                        associatedShare = false;
                        associatedNOTF = true;
                        phcLocal = notificationSummaryVO.getPublicHealthCaseLocalId();
                    }
                }
                else if (phcLocal != null
                        && phcLocal.equalsIgnoreCase(notificationSummaryVO.getPublicHealthCaseLocalId())
                        && (notificationSummaryVO.getNotificationCd().equalsIgnoreCase(NEDSSConstants.CLASS_CD_EXP_NOTF) ||
                        		notificationSummaryVO.getNotificationCd().equalsIgnoreCase(NEDSSConstants.CLASS_CD_EXP_NOTF_PHDC))	)
                {
                    if (associatedShare)
                        notificationSummaryVO.setShareAssocaited(true);
                    if (associatedNOTF)
                        notificationSummaryVO.setNndAssociated(true);
                    associatedNOTF = false;
                    associatedShare = false;
                }
                if (notificationSummaryVO.getCd() != null && notificationSummaryVO.getCd().trim().length() != 0)
                {
                    notificationSummaryVO.setCdTxt(((String) mapConditionCode.get(notificationSummaryVO.getCd())));
                }
                if (notificationSummaryVO.getRecipient() != null
                        && notificationSummaryVO.getRecipient().trim().length() != 0)
                {
                    String codeConverterTemp = URLEncoder.encode(notificationSummaryVO.getRecipient(), "ISO-8859-1");
                    notificationSummaryVO.setRecipient(notificationSummaryVO.getRecipient());
                    notificationSummaryVO.setCodeConverterTemp(codeConverterTemp);

                }
                if (notificationSummaryVO.getNotificationCd() != null
                        && notificationSummaryVO.getNotificationCd().trim().length() != 0)
                {
                    notificationSummaryVO.setNotificationSrtDescCd(CachedDropDowns.getCodeDescTxtForCd(
                            notificationSummaryVO.getNotificationCd(), "NBS_DOC_PURPOSE"));

                }
                if (notificationSummaryVO.getCaseClassCd() != null
                        && notificationSummaryVO.getCaseClassCd().trim().length() != 0)
                {
                    String caseStatus = cache.getDescForCode("PHC_CLASS", notificationSummaryVO.getCaseClassCd());
                    notificationSummaryVO.setCaseClassCdTxt(caseStatus);
                }
                if (notificationSummaryVO.getJurisdictionCd() != null
                        && notificationSummaryVO.getJurisdictionCd().trim().length() != 0)
                {
                    notificationSummaryVO.setJurisdictionCdTxt((String) mapJurisdiction.get(notificationSummaryVO
                            .getJurisdictionCd()));
                }
                if (notificationSummaryVO.getTxt() != null && notificationSummaryVO.getTxt().trim().length() != 0)
                {
                    String codeConverterTxtTemp = URLEncoder.encode(notificationSummaryVO.getTxt(), "ISO-8859-1");
                    notificationSummaryVO.setTxt(notificationSummaryVO.getTxt().trim());
                    notificationSummaryVO.setCodeConverterCommentTemp(codeConverterTxtTemp);

                }
                if (notificationSummaryVO.getCurrSexCd() != null
                        && notificationSummaryVO.getCurrSexCd().trim().length() != 0)
                {
                    notificationSummaryVO.setCurrSexCdDesc(CachedDropDowns.getCodeDescTxtForCd(
                            notificationSummaryVO.getCurrSexCd(), "SEX"));
                }
            } 
        }
        catch (Exception ex)
        {
            logger.fatal("Error in getNotificationsForApproval method doing selectstatment : " + selectstatment, ex);
            throw new NEDSSSystemException(ex.toString());
        }

        return notificationProxyVOCollection;
    }

    /**
     * @J2EE_METHOD -- approveNotification returns validation information for
     *              BMIRD Stored Procedure
     * @param NotificationSummaryVO
     * @param NBSSecurityObj
     * @returns Boolean
     */
    public Boolean approveNotification(NotificationSummaryVO notificationSummaryVO, NBSSecurityObj nbsSecurityObj)
            throws java.rmi.RemoteException, javax.ejb.EJBException, javax.ejb.CreateException, NEDSSSystemException,
            NEDSSConcurrentDataException
    { 
        logger.info("In approve Notification"); 
        try
        {
            if (notificationSummaryVO == null)
            {
                logger.error("NotificationProxyEJB, approvenotificationProxy(): notificationSummaryVO is null. ");
                throw new NEDSSSystemException("notificationSummaryVO is null ");
            }

            NotificationProxyVO notificationProxyVO = this.getNotificationProxy(
                    notificationSummaryVO.getNotificationUid(), nbsSecurityObj);
            String programeAreaCode = notificationProxyVO.getThePublicHealthCaseVO().getThePublicHealthCaseDT()
                    .getProgAreaCd();
            String jurisdictionCode = notificationProxyVO.getThePublicHealthCaseVO().getThePublicHealthCaseDT()
                    .getJurisdictionCd();
            String shared = notificationProxyVO.getThePublicHealthCaseVO().getThePublicHealthCaseDT().getSharedInd();

            if (!nbsSecurityObj.getPermission(NBSBOLookup.NOTIFICATION, NBSOperationLookup.REVIEW, programeAreaCode,
                    jurisdictionCode, shared))
            {
                logger.info("no review permissions for approveNotification");
                throw new NEDSSSystemException("NO REVIEW PERMISSIONS for setNotificationProxy");
            }

            NotificationVO notificationVO = notificationProxyVO.getTheNotificationVO();

            if (notificationVO == null)
            {
                logger.error("NotificationProxyEJB, rejectNotificationProxy(): notificationVO is null. ");
                throw new NEDSSSystemException("notificationVO is null ");
            }

            notificationProxyVO.setTheActRelationshipDTCollection(notificationVO.getTheActRelationshipDTCollection());

            if (notificationVO.getTheNotificationDT() == null)
            {
                logger.error("NotificationProxyEJB, rejectNotificationProxy(): NotificationDT is null. ");
                throw new NEDSSSystemException("NotificationDT is null ");
            }

            notificationProxyVO.theNotificationVO.getTheNotificationDT().setTxt(notificationSummaryVO.getTxt());
            notificationProxyVO.theNotificationVO.getTheNotificationDT().setCaseConditionCd(
                    notificationSummaryVO.getCd());
            notificationProxyVO.theNotificationVO.getTheNotificationDT().setCaseClassCd(
                    notificationSummaryVO.getCaseClassCd());
            notificationProxyVO.setItDirty(true);

            String boLookup = NBSBOLookup.NOTIFICATION;
            String triggerCd = NEDSSConstants.NOT_APR;
            String tableName = DataTables.NOTIFICATION_TABLE;
            String moduleCd = NEDSSConstants.BASE;
            PrepareVOUtils pre = new PrepareVOUtils();
            NotificationDT notificationDT = notificationProxyVO.theNotificationVO.getTheNotificationDT();
            notificationDT = (NotificationDT) pre.prepareVO(notificationDT, boLookup, triggerCd, tableName, moduleCd,
                    nbsSecurityObj);
            notificationVO.setTheNotificationDT(notificationDT);
            notificationProxyVO.setTheNotificationVO(notificationVO);

            this.setTheNotificationInfo(notificationVO, nbsSecurityObj);

            String recordStatusCd = notificationVO.getTheNotificationDT().getRecordStatusCd();
            logger.debug("-setTheNotificationInfo---recordStatusCd:" + recordStatusCd);

            if (recordStatusCd.trim().equals("APPROVED"))
            {
                return new Boolean(true);
            }
            else
            {
                return new Boolean(false);
            }
        }
        catch (NEDSSSystemException e)
        {
            logger.fatal(nbsSecurityObj.getFullName(), e.getMessage(), e);
            throw new NEDSSSystemException("approveNotification: " + e.toString());
        }
    } // end of -- approveNotification

    /**
     * @J2EE_METHOD -- setTheNotificationInfo
     * @param NotificationVO
     * @param NBSSecurityObj
     */
    public void setTheNotificationInfo(NotificationVO notificationVO, NBSSecurityObj nbsSecurityObj)
            throws java.rmi.RemoteException, javax.ejb.EJBException, javax.ejb.CreateException, NEDSSSystemException,
            NEDSSConcurrentDataException
    {
        NedssUtils nedssUtils = new NedssUtils();
        try
        {
            // initialize the activity controller
            ActController actController = null;
            Object object = nedssUtils.lookupBean(JNDINames.ActControllerEJB);
            ActControllerHome acthome = (ActControllerHome) PortableRemoteObject
                    .narrow(object, ActControllerHome.class);
            actController = acthome.create();
            notificationVO.getTheNotificationDT();
            actController.setNotification(notificationVO, nbsSecurityObj);
        }
        catch (NEDSSConcurrentDataException ex)
        {
            logger.fatal("The entity cannot be updated as concurrent access is not allowed!"+ex.getMessage(), ex);
            throw new NEDSSConcurrentDataException("Concurrent access occurred in InterventionProxyEJB : "
                    + ex.toString());
        }
        catch (NEDSSSystemException e)
        {
            logger.fatal("NotificationProxyDaoImpl-setTheNotificationInfo : " + nbsSecurityObj.getFullName(),
                    e.getMessage(), e);
            throw new NEDSSSystemException("NotificationProxyDaoImpl-setTheNotificationInfo : " + e.toString());
        }
    } // end of -- setTheNotificationInfo

    /**
     * @roseuid 3C46274003B7
     * @J2EE_METHOD -- getNotificationProxy returns validation information for
     *              BMIRD Stored Procedure
     * @param Long
     * @param NBSSecurityObj
     * @returns NotificationProxyVO
     */
    public NotificationProxyVO getNotificationProxy(Long notificationUID, NBSSecurityObj nbsSecurityObj)
            throws javax.ejb.EJBException, NEDSSSystemException
    {
        NotificationProxyVO notificationProxyVO = null;
        try
        {
            NedssUtils nedssUtils = new NedssUtils();
            Object theLookedUpObject;
            theLookedUpObject = nedssUtils.lookupBean(JNDINames.ActControllerEJB);

            ActControllerHome acHome = (ActControllerHome) PortableRemoteObject.narrow(theLookedUpObject,
                    ActControllerHome.class);
            ActController actController = acHome.create();
            NotificationVO notificationVO = actController.getNotification(notificationUID, nbsSecurityObj);
            Collection<Object> actRelationShipColl = notificationVO.getTheActRelationshipDTCollection();

            for (Iterator<Object> anIterator = actRelationShipColl.iterator(); anIterator.hasNext();)
            {
                ActRelationshipDT acdt = (ActRelationshipDT) anIterator.next();

                if (acdt.getTypeCd().compareTo(NEDSSConstants.ACT106_TYP_CD) == 0
                        || acdt.getTypeCd().compareTo(NEDSSConstants.ACT128_TYP_CD) == 0)
                {
                    Long publicHealthUID = acdt.getTargetActUid();
                    PublicHealthCaseVO publicHealthCaseVO = actController.getPublicHealthCase(publicHealthUID,
                            nbsSecurityObj);
                    if (publicHealthCaseVO == null)
                    {
                        logger.error("Did not get publicHealthCaseVO with publicHealthUID=" + publicHealthUID);
                        throw new NEDSSSystemException("Did not get publicHealthCaseVO with publicHealthUID="
                                + publicHealthUID);
                    }

                    boolean check3 = nbsSecurityObj.checkDataAccess(publicHealthCaseVO.getThePublicHealthCaseDT(),
                            NBSBOLookup.NOTIFICATION, NBSOperationLookup.REVIEW);
                    boolean check4 = nbsSecurityObj.checkDataAccess(publicHealthCaseVO.getThePublicHealthCaseDT(),
                            NBSBOLookup.NOTIFICATION, NBSOperationLookup.CREATE);
                    boolean check5 = nbsSecurityObj.checkDataAccess(publicHealthCaseVO.getThePublicHealthCaseDT(),
                            NBSBOLookup.NOTIFICATION, NBSOperationLookup.CREATENEEDSAPPROVAL);

                    if ((check3 == false) && (check4 == false) && (check5 == false))
                    {
                        logger.error("don't have permission to access notification info in publicHealthCaseVO");
                        throw new NEDSSSystemException(
                                "don't have permission to access notification info in publicHealthCaseVO");
                    }
                    else
                    {
                        notificationProxyVO = new NotificationProxyVO();
                        notificationProxyVO.setThePublicHealthCaseVO(publicHealthCaseVO);
                        notificationProxyVO.setTheNotificationVO(notificationVO);
                        notificationProxyVO.setTheActRelationshipDTCollection(actRelationShipColl);
                    }
                }
            }
        }
        catch (Exception e)
        {
            logger.error("getNotificationProxy failed !" + e.getMessage(), e);
            throw new NEDSSSystemException("getNotificationProxy failed !" + e.getMessage());
        }

        return notificationProxyVO;
    } // end of -- getNotificationProxy

    /**
     * @roseuid 3C462741005B
     * @J2EE_METHOD -- setNotificationProxy
     * @param NotificationProxyVO
     * @param NBSSecurityObj
     * @returns Long
     */
    public Long setNotificationProxy(NotificationProxyVO notificationProxyVO, NBSSecurityObj nbsSecurityObj)
            throws java.rmi.RemoteException, javax.ejb.EJBException, NEDSSSystemException, NEDSSConcurrentDataException
    {

        Long notificationUid = null;
        String permissionFlag = "";
        Collection<Object> act2 = new ArrayList<Object>();

        try
        {
            if (notificationProxyVO == null)
            {
                logger.error("NotificationProxyEJB, setNotificationProxy(): notificationproxyVO is null. ");
                throw new NEDSSSystemException("notificationproxyVO is null "); 
            }

            String programeAreaCode = notificationProxyVO.getThePublicHealthCaseVO().getThePublicHealthCaseDT()
                    .getProgAreaCd();
            String jurisdictionCode = notificationProxyVO.getThePublicHealthCaseVO().getThePublicHealthCaseDT()
                    .getJurisdictionCd();
            String shared = notificationProxyVO.getThePublicHealthCaseVO().getThePublicHealthCaseDT().getSharedInd();

            if (!nbsSecurityObj.getPermission(NBSBOLookup.NOTIFICATION, NBSOperationLookup.CREATE, programeAreaCode,
                    jurisdictionCode, shared))
            {
                logger.info("no CREATE permissions for setNotificationProxy");

                if (!nbsSecurityObj.getPermission(NBSBOLookup.NOTIFICATION, NBSOperationLookup.CREATENEEDSAPPROVAL,
                        programeAreaCode, jurisdictionCode, shared))
                {
                    logger.info("no CREATENEEDSAPPROVAL permissions for setNotificationProxy");
                    throw new NEDSSSystemException("NO CREATE PERMISSIONS for setNotificationProxy");
                }
                else
                    permissionFlag = "CREATENEEDSAPPROVAL";
            }
            else
                permissionFlag = "CREATE";
        }
        catch (Exception e)
        {
        	logger.fatal("Exception = "+e.getMessage(), e);
        	throw new NEDSSSystemException(e.toString());
        }

        // run validation to check for missing fields
        if (!validateNotification(notificationProxyVO, nbsSecurityObj))
        {
            logger.error("NotificationProxyEJB, setNotificationProxy(): validation error - required fields are missing. ");
            throw new NEDSSSystemException("validation error - required fields are missing");
        }

        NotificationVO notifVO = notificationProxyVO.getTheNotificationVO();

        if (notifVO == null)
        {
            logger.error("NotificationProxyEJB, setNotificationProxy(): notificationVO is null. ");
            throw new NEDSSSystemException("notificationVO is null ");
        }

        NotificationDT notifDT = notifVO.getTheNotificationDT();
        notifDT.setProgAreaCd(notificationProxyVO.getThePublicHealthCaseVO().getThePublicHealthCaseDT().getProgAreaCd());
        notifDT.setJurisdictionCd(notificationProxyVO.getThePublicHealthCaseVO().getThePublicHealthCaseDT()
                .getJurisdictionCd());

        if (permissionFlag.equals("CREATE"))
        {
            notifDT.setCaseConditionCd(notificationProxyVO.getThePublicHealthCaseVO().getThePublicHealthCaseDT()
                    .getCd());
        }

        if ((notifVO.isItDirty()) || (notifVO.isItNew()))
        {
            String boLookup = NBSBOLookup.NOTIFICATION;
            String triggerCd = "";
            if (permissionFlag.equals("CREATE"))
                triggerCd = NEDSSConstants.NOT_CR_APR;
            if (permissionFlag.equals("CREATENEEDSAPPROVAL"))
                triggerCd = NEDSSConstants.NOT_CR_PEND_APR;
            String tableName = DataTables.NOTIFICATION_TABLE;
            String moduleCd = NEDSSConstants.BASE;
            
			if(notifVO.isItNew() && PropertyUtil.isHIVProgramArea(notifDT.getProgAreaCd()))
            	triggerCd = NEDSSConstants.NOT_HIV;// for HIV, notification is always created as completed
			if(notifVO.isItDirty() && PropertyUtil.isHIVProgramArea(notifDT.getProgAreaCd()))
            	triggerCd = NEDSSConstants.NOT_HIV_EDIT;// for HIV, notification always stay as completed

            // prepare the VO
            PrepareVOUtils pre = new PrepareVOUtils();

            try
            {
                notifDT = (NotificationDT) pre.prepareVO(notifDT, boLookup, triggerCd, tableName, moduleCd,
                        nbsSecurityObj);
                logger.info("setNotificationProxy - LocalId = " + notifDT.getLocalId());

                if (StringUtils.isEmpty(notifDT.getCd()))
                    notifDT.setCd(NEDSSConstants.CLASS_CD_NOTIFICATION);

                notifVO.setTheNotificationDT(notifDT);

                // initialize the activity controller
                ActController actController = null;
                NedssUtils nedssUtils = new NedssUtils();
                Object object = nedssUtils.lookupBean(JNDINames.ActControllerEJB);
                logger.debug("ActController lookup = " + object.toString());

                ActControllerHome acthome = (ActControllerHome) PortableRemoteObject.narrow(object,
                        ActControllerHome.class);
                logger.debug("Found ActControllerHome: " + acthome);
                actController = acthome.create();

                Long falseUid = null;
                Long realUid = null;
                realUid = actController.setNotification(notifVO, nbsSecurityObj);
                notificationUid = realUid;
                falseUid = notifVO.getTheNotificationDT().getNotificationUid();

                if (notifVO.isItNew())
                {
                    ActRelationshipDAOImpl actRelDao = new ActRelationshipDAOImpl();
                    ActRelationshipDT actRelDT = null;
                    actRelDT = setFalseToNew(notificationProxyVO, falseUid, realUid);
                    notifDT.setNotificationUid(realUid);

                    notifVO.setTheNotificationDT(notifDT);
                    notificationProxyVO.setTheNotificationVO(notifVO);
                    act2.add(actRelDT);
                    notificationProxyVO.setTheActRelationshipDTCollection(act2);
                    actRelDao.store(actRelDT);
                }
            }
            catch (NEDSSConcurrentDataException ex)
            {
                logger.fatal("The entity cannot be updated as concurrent access is not allowed!");
                // EJB_Context.setRollbackOnly();
                throw new NEDSSConcurrentDataException(
                        "NEDSSConcurrentDataException.setNotificationProxy Concurrent access occurred in NotificationProxyEJB : "
                                + ex.toString());
            }
            catch (Exception e)
            {
                logger.fatal(nbsSecurityObj.getFullName(), e.getMessage(), e);
                e.printStackTrace();
                throw new javax.ejb.EJBException(" : " + e.toString());
            }
        } // end of if new or dirty
        return notificationUid;
    } // end of setNotificationProxy

    /**
     * This method checks for the negative uid value for any ACT & ENTITY DT
     * then compare them with respective negative values in ActRelationshipDT
     * and ParticipationDT as received from the notificationProxyVO(determined
     * in the addnotification method). As it has also got the actualUID
     * (determined in the addnotification method) it replaces them accordingly.
     */
    private ActRelationshipDT setFalseToNew(NotificationProxyVO notificationProxyVO, Long falseUid, Long actualUid)
    {

        Iterator<Object> anIterator = null; 
        ActRelationshipDT actRelationshipDT = null;
        try{
	        Collection<Object> actRelationShipColl = (ArrayList<Object>) notificationProxyVO
	                .getTheActRelationshipDTCollection();
	        Collection<Object> act2 = new ArrayList<Object>();
	
	        if (actRelationShipColl != null)
	        {
	
	            for (anIterator = actRelationShipColl.iterator(); anIterator.hasNext();)
	            {
	                actRelationshipDT = (ActRelationshipDT) anIterator.next();
	                actRelationshipDT.setSourceActUid(actualUid);
	                act2.add(actRelationshipDT);
	            }
	        }
        }catch(Exception ex){
        	logger.fatal("Exception = "+ex.getMessage(), ex);
        	throw new NEDSSSystemException(ex.toString());
        }
        return actRelationshipDT;
    }// end of setFalseToNew

    /**
     * @J2EE_METHOD -- validateBMIRDRequiredFields returns validation
     *              information for BMIRD Stored Procedure
     * @param Long
     * @return Collection
     */
    public Collection<Object> validateBMIRDRequiredFields(Long publicHealthCaseUID)
    {
        // logger.debug("About to call getConnection from validateBMIRDRequiredFields"); 
        ArrayList<Object> validatedFields = new ArrayList<Object>();
        ArrayList<Object> inArrayList = new ArrayList<Object>();
        ArrayList<Object> outArrayList = new ArrayList<Object>();
        ArrayList<Object> arrayList = new ArrayList<Object>();
        try
        {
            // *************************************************************************
            // This code is for accessing a stored procedure.
            // stored procedure call to get the issue related information
            // logger.debug("About to call stored procedure");
            // dbConnection = getConnection();
            String sQuery = "{call nnd_bmird_get_req_sp(?,?,?,?,?)}";

            // prepare the stored procedure
            // sProc = dbConnection.prepareCall(sQuery);
            // logger.debug("Value of sProc is: " + sProc);
            // System.out.println("Value of publichealcaseUID: " +
            // publicHealthCaseUID);
            // sProc.setLong(1,publicHealthCaseUID.longValue()); //1 //1
            inArrayList.add(publicHealthCaseUID); // 1 //1
            // register the output parameters
            // sProc.registerOutParameter(2, java.sql.Types.VARCHAR);//@ABCsCASE
            outArrayList.add(new Integer(java.sql.Types.VARCHAR));
            // sProc.registerOutParameter(3,
            // java.sql.Types.VARCHAR);//@abcStateCaseId
            outArrayList.add(new Integer(java.sql.Types.VARCHAR));
            // sProc.registerOutParameter(4, java.sql.Types.VARCHAR);//@dateOf
            outArrayList.add(new Integer(java.sql.Types.VARCHAR));
            // sProc.registerOutParameter(5, java.sql.Types.VARCHAR);//@bacteria
            outArrayList.add(new Integer(java.sql.Types.VARCHAR));
            // execute the stored procedure
            // logger.debug("About to execute the stored procedure");

            // sProc.execute();

            arrayList = (ArrayList<Object>) callStoredProcedureMethod(sQuery, inArrayList, outArrayList);

            int count = 0;

            String ABCsCASE = arrayList.get(count++).toString();
            String abcStateCaseId = arrayList.get(count++).toString();
            String dateOf = arrayList.get(count++).toString();
            String bacteria = arrayList.get(count++).toString();
            if (!ABCsCASE.equals(NEDSSConstants.BMIRD_ABC_CASE))
            {
                ABCsCASE = ABCsCASE.trim();
                if (ABCsCASE.equalsIgnoreCase("T"))
                {
                    // validatedFields = new ArrayList<Object> ();

                    // validatedFields.add(ABCsCASE);
                    if (abcStateCaseId != null)
                        abcStateCaseId = abcStateCaseId.trim();
                    if ((abcStateCaseId == null) || (abcStateCaseId.equals(""))
                            || (abcStateCaseId.equals(NEDSSConstants.BMIRD_ABC_STATE_CASE_ID)))
                    {
                        // put name of missing ABCs State Case ID field into
                        // return array
                        validatedFields.add(NEDSSConstants.BMIRD_ABC_STATE_CASE);
                    }
                    if (dateOf != null)
                        dateOf = dateOf.trim();
                    if ((dateOf == null) || (dateOf.equals("")) || (dateOf.equals(NEDSSConstants.BMIRD_DATE_OF)))
                    {
                        // put name of missing date field in array
                        validatedFields.add(NEDSSConstants.BMIRD_DATE_POSITIVE_CULTURE);
                    }
                    if (bacteria != null)
                        bacteria = bacteria.trim();
                    if ((bacteria == null) || (bacteria.equals("")) || (bacteria.equals(NEDSSConstants.BMIRD_BACTER)))
                    {
                        // put name of missing field in array
                        validatedFields.add(NEDSSConstants.BMIRD_BACTERIAL_SPECIES_ISOLATED);
                    }
                }
            }
            /* nothing in array - return empty array */

        }

        /*
         * catch(SQLException se) { logger.fatal(
         * "Error: SQLException while obtaining database connection for validateBMIRDRequiredFields.\n"
         * , se); throw new NEDSSDAOSysException(se.getMessage()); }
         * 
         * catch(NEDSSSystemException nse) { logger.fatal(
         * "Error: NEDSSSystemException while obtaining database connection for validateBMIRDRequiredFields.\n"
         * , nse); throw new NEDSSDAOSysException(nse.getMessage()); }
         * 
         * finally {
         * 
         * closeCallableStatement(sProc); releaseConnection(dbConnection); }
         */
        catch (Exception nse)
        {
            logger.error(
                    "Error: NEDSSSystemException while obtaining database connection for validateBMIRDRequiredFields.\n",
                    nse);
            // throw new NEDSSDAOSysException(nse.getMessage());
        }

        return validatedFields;
    }// end validateBMIRDRequiredFields

    public static void main(String[] strg)
    {
        LogUtils.setLogLevel(6);
        long uid = 470007000;
        try
        {
            NotificationProxyDaoImpl npd = new NotificationProxyDaoImpl();
            npd.validateBMIRDRequiredFields(new Long(uid));
        }
        catch (Exception e)
        {
        }
    }

    /*
     * public Collection<Object> validateBMIRDRequiredFields (Long
     * publicHealthCaseUID) {
     * //logger.debug("About to call getConnection from validateBMIRDRequiredFields"
     * ); Connection dbConnection = null;
     * 
     * //logger.debug("Just got the connection"); PreparedStatement preparedStmt
     * = null; ArrayList<Object> validatedFields = new ArrayList<Object> ();
     * CallableStatement sProc= null;
     * 
     * try {
     * //******************************************************************
     * ******* // This code is for accessing a stored procedure. // stored
     * procedure call to get the issue related information
     * //logger.debug("About to call stored procedure"); dbConnection =
     * getConnection(); String sQuery =
     * "{call nnd_bmird_get_req_sp(?,?,?,?,?)}";
     * 
     * // prepare the stored procedure sProc = dbConnection.prepareCall(sQuery);
     * //logger.debug("Value of sProc is: " + sProc);
     * //System.out.println("Value of publichealcaseUID: " +
     * publicHealthCaseUID); sProc.setLong(1,publicHealthCaseUID.longValue());
     * //1 //1 // register the output parameters sProc.registerOutParameter(2,
     * java.sql.Types.VARCHAR);//@ABCsCASE sProc.registerOutParameter(3,
     * java.sql.Types.VARCHAR);//@abcStateCaseId sProc.registerOutParameter(4,
     * java.sql.Types.VARCHAR);//@dateOf sProc.registerOutParameter(5,
     * java.sql.Types.VARCHAR);//@bacteria // execute the stored procedure
     * //logger.debug("About to execute the stored procedure");
     * 
     * sProc.execute();
     * 
     * // get the output parameters String ABCsCASE = sProc.getString(2); String
     * abcStateCaseId = sProc.getString(3); String dateOf = sProc.getString(4);
     * String bacteria = sProc.getString(5);
     * 
     * 
     * if(!ABCsCASE.equals(NEDSSConstants.BMIRD_ABC_CASE) ) { ABCsCASE =
     * ABCsCASE.trim(); if(ABCsCASE.equalsIgnoreCase("T")) { // validatedFields
     * = new ArrayList<Object> ();
     * 
     * //validatedFields.add(ABCsCASE); if (abcStateCaseId != null)
     * abcStateCaseId = abcStateCaseId.trim(); if((abcStateCaseId == null) ||
     * (abcStateCaseId.equals("")) ||
     * (abcStateCaseId.equals(NEDSSConstants.BMIRD_ABC_STATE_CASE_ID))) { //put
     * name of missing ABCs State Case ID field into return array
     * validatedFields.add(NEDSSConstants.BMIRD_ABC_STATE_CASE); } if (dateOf !=
     * null) dateOf = dateOf.trim(); if ((dateOf == null) || (dateOf.equals(""))
     * || (dateOf.equals(NEDSSConstants.BMIRD_DATE_OF))) { // put name of
     * missing date field in array
     * validatedFields.add(NEDSSConstants.BMIRD_DATE_POSITIVE_CULTURE); } if
     * (bacteria != null) bacteria = bacteria.trim(); if ((bacteria == null) ||
     * (bacteria.equals("")) || (bacteria.equals(NEDSSConstants.BMIRD_BACTER)))
     * { //put name of missing field in array
     * validatedFields.add(NEDSSConstants.BMIRD_BACTERIAL_SPECIES_ISOLATED); } }
     * } // nothing in array - return empty array return validatedFields; }
     * 
     * catch(SQLException se) { logger.fatal(
     * "Error: SQLException while obtaining database connection for validateBMIRDRequiredFields.\n"
     * , se); throw new NEDSSDAOSysException(se.getMessage()); }
     * catch(NEDSSSystemException nse) { logger.fatal(
     * "Error: NEDSSSystemException while obtaining database connection for validateBMIRDRequiredFields.\n"
     * , nse); throw new NEDSSDAOSysException(nse.getMessage()); }
     * 
     * finally {
     * 
     * closeCallableStatement(sProc); releaseConnection(dbConnection); } }//end
     * validateBMIRDRequiredFields
     */
    /**
     * @param uid
     *            @
     * @J2EE_METHOD -- validateHepatitisRequiredFields returns validation
     *              information for Hepatitis Stored Procedure
     */
    public Collection<Object> validateHepatitisRequiredFields(Long publicHealthCaseUID)
    {
        // logger.debug("About to call getConnection from validateHepatitisRequiredFields");
        Connection dbConnection = null;

        // logger.debug("Just got the connection");

        ArrayList<Object> validatedFields = null;
        CallableStatement sProc = null;

        try
        {
            // *************************************************************************
            // This code is for accessing a stored procedure.
            // stored procedure call to get the issue related information
            // logger.debug("About to call stored procedure");
            // System.out.println("Inside the NotificationDao");
            dbConnection = getConnection();
            String sQuery = "{call nnd_hepatitis_get_req_sp(?,?)}";

            // prepare the stored procedure
            sProc = dbConnection.prepareCall(sQuery);
            // logger.debug("Value of sProc is: " + sProc);

            sProc.setLong(1, publicHealthCaseUID.longValue()); // 1 //1
            // register the output parameters
            sProc.registerOutParameter(2, java.sql.Types.VARCHAR);// @diagnosis

            // execute the stored procedure
            // logger.debug("About to execute the stored procedure");

            sProc.execute();

            // get the output parameters
            String diagnosis = sProc.getString(2);
            // System.out.println("Value of diagnosis " + diagnosis);
            if (diagnosis == null || diagnosis.trim().equalsIgnoreCase("NI"))
            {
                diagnosis = "Diagnosis";
                validatedFields = new ArrayList<Object>();
                validatedFields.add(diagnosis);
                // System.out.println("Value of diagnosis " + validatedFields);
            }

            return validatedFields;
        }
        catch (SQLException se)
        {
            logger.fatal(
                    "Error: SQLException while obtaining database connection for validateHepatitisRequiredFields.\n",
                    se);
            throw new NEDSSSystemException(se.getMessage());
        }
        catch (Exception nse)
        {
            logger.fatal(
                    "Error: NEDSSSystemException while obtaining database connection for validateHepatitisRequiredFields.\n",
                    nse);
            throw new NEDSSSystemException(nse.getMessage());
        }

        finally
        {
            closeCallableStatement(sProc);
            releaseConnection(dbConnection);
        }
    }// end validateHepatitisRequiredFields

    /*
     * - validateNotification - ---------------------------
     */
    private boolean validateNotification(NotificationProxyVO notificationProxyVO, NBSSecurityObj nbsSecurityObj)
    {
        boolean validated = false;
        // not doing anything yet - but wanted to do something
        validated = true;
        return validated;
    }

    @SuppressWarnings("unchecked")
    public Collection<Object> getRejectedNotificationsQueue(NBSSecurityObj nbsSecurityObj)
            throws javax.ejb.EJBException, NEDSSSystemException
    {
        String selectstatment = null;
        ArrayList<Object> rejectednotificationVOCollection = new ArrayList<Object>();
        RejectedNotificationSummaryVO rejectedNotificationSummaryVO = new RejectedNotificationSummaryVO();
        boolean check1 = nbsSecurityObj.getPermission(NBSBOLookup.NOTIFICATION, NBSOperationLookup.CREATE,
                ProgramAreaJurisdictionUtil.ANY_PROGRAM_AREA, ProgramAreaJurisdictionUtil.ANY_JURISDICTION);
        boolean check2 = nbsSecurityObj.getPermission(NBSBOLookup.NOTIFICATION, NBSOperationLookup.CREATENEEDSAPPROVAL,
                ProgramAreaJurisdictionUtil.ANY_PROGRAM_AREA, ProgramAreaJurisdictionUtil.ANY_JURISDICTION);

        try
        {
            if (!check1 && !check2)
            {
                logger.error("getNotificationVOs - NO PERMISSIONS FOR GET REJECTED NOTIFICATIONS");
                throw new NEDSSSystemException("NO PERMISSIONS FOR GET REJECTED NOTIFICATIONS");
            }
            String dataAccessWhereClause = "";
			String dataAccessWhereClause1 = null;
			String dataAccessWhereClause2 = null;
			if(check1)
				dataAccessWhereClause1 = nbsSecurityObj.getDataAccessWhereClause(NBSBOLookup.NOTIFICATION,
					NBSOperationLookup.CREATE, "Notification");
			if(check2)
				dataAccessWhereClause2 = nbsSecurityObj.getDataAccessWhereClause(NBSBOLookup.NOTIFICATION,
					NBSOperationLookup.CREATENEEDSAPPROVAL, "Notification");
			//Need to get the where clause for both the permissions as user might have Create and Create Needs Approval in different PA Jurisdictions
			if(dataAccessWhereClause1 == null && dataAccessWhereClause2 == null)
			   dataAccessWhereClause = "";
			else if(dataAccessWhereClause1 == null && dataAccessWhereClause2 != null && dataAccessWhereClause2.trim().length()>0)
				dataAccessWhereClause = " AND " + dataAccessWhereClause2;
			else if(dataAccessWhereClause1 != null && dataAccessWhereClause1.trim().length()>0 && dataAccessWhereClause2 == null)
				dataAccessWhereClause = " AND " + dataAccessWhereClause1;
			else if(dataAccessWhereClause1 != null && dataAccessWhereClause1.trim().length()>0 && dataAccessWhereClause2 != null && dataAccessWhereClause2.trim().length()>0)
				dataAccessWhereClause = " AND (" + dataAccessWhereClause1 +" or "+ dataAccessWhereClause2+")";
			logger.debug("getRejectedNotificationsQueue : dataAccessWhereClause " +dataAccessWhereClause);
            String daysLimit = PropertyUtil.getInstance().getRejectedNotificationDaysLimit();
            if (daysLimit == null || (daysLimit != null && daysLimit.trim().equalsIgnoreCase("")))
                daysLimit = "30";
            selectstatment = WumSqlQuery.REJECTED_NOTIFICATIONS_SQL + daysLimit + dataAccessWhereClause
                        + " order by Notification.add_time";

            rejectednotificationVOCollection = (ArrayList<Object>) preparedStmtMethod(rejectedNotificationSummaryVO,
                    null, selectstatment, NEDSSConstants.SELECT);
            CachedDropDownValues cache = new CachedDropDownValues();
            TreeMap<?, ?> mapPhcClass = cache.getCodedValuesAsTreeMap("PHC_CLASS");
            mapPhcClass = cache.reverseMap(mapPhcClass); // we can add
                                                         cache.getConditionCodes();
            TreeMap<Object, Object> mapJurisdiction = cache.getJurisdictionCodedValuesWithAll();
            NBSAuthHelper helper = new NBSAuthHelper();
            for (Iterator<Object> anIterator = rejectednotificationVOCollection.iterator(); anIterator.hasNext();)
            {
                rejectedNotificationSummaryVO = (RejectedNotificationSummaryVO) anIterator.next();
                rejectedNotificationSummaryVO.setAddUserName(helper.getUserName(rejectedNotificationSummaryVO
                        .getAddUserId()));
                if (rejectedNotificationSummaryVO.getCaseStatusCd() != null
                        && rejectedNotificationSummaryVO.getCaseStatusCd().trim().length() != 0)
                {
                    rejectedNotificationSummaryVO.setCaseStatus(((String) mapPhcClass.get(rejectedNotificationSummaryVO
                            .getCaseStatusCd())));
                }
                if (rejectedNotificationSummaryVO.getJurisdictionCd() != null
                        && rejectedNotificationSummaryVO.getJurisdictionCd().trim().length() != 0)
                {
                    rejectedNotificationSummaryVO.setJurisdictionCdTxt((String) mapJurisdiction
                            .get(rejectedNotificationSummaryVO.getJurisdictionCd()));
                }
                if (rejectedNotificationSummaryVO.getRecipient() != null
                        && rejectedNotificationSummaryVO.getRecipient().trim().length() != 0)
                {
                    // String codeConverterTemp =
                    // URLEncoder.encode(rejectedNotificationSummaryVO.getRecipient(),
                    // "ISO-8859-1");
                    rejectedNotificationSummaryVO.setRecipient(rejectedNotificationSummaryVO.getRecipient());
                }
                if (rejectedNotificationSummaryVO.getNotificationCd() != null
                        && rejectedNotificationSummaryVO.getNotificationCd().trim().length() != 0)
                {
                    rejectedNotificationSummaryVO.setNotificationCd(CachedDropDowns.getCodeDescTxtForCd(
                            rejectedNotificationSummaryVO.getNotificationCd(), "NBS_DOC_PURPOSE"));

                }
            }
        }
        catch (Exception ex)
        {
            logger.fatal("Error in getRejectedNotificationsQueue method doing selectstatment : " + selectstatment, ex);
            throw new NEDSSSystemException(ex.toString());
        } 
        return rejectednotificationVOCollection;
    } 
}
