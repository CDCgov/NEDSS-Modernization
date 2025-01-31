package gov.cdc.nedss.webapp.nbs.action.observation.labreport;

import java.text.*;
import java.util.*;

import javax.servlet.http.*;

import java.sql.*;
import java.util.Date;




//import org.apache.struts.action.*;
import gov.cdc.nedss.act.observation.dt.*;
import gov.cdc.nedss.act.observation.vo.*;
import gov.cdc.nedss.association.dt.*;
import gov.cdc.nedss.act.actid.dt.*;
import gov.cdc.nedss.controller.ejb.entitycontrollerejb.bean.EntityControllerHome;
import gov.cdc.nedss.entity.material.vo.*;
import gov.cdc.nedss.entity.organization.vo.*;
import gov.cdc.nedss.entity.organization.dt.*;
import gov.cdc.nedss.entity.person.dt.*;
import gov.cdc.nedss.entity.person.vo.*;
import gov.cdc.nedss.entity.entityid.dt.*;
import gov.cdc.nedss.locator.dt.*;
import gov.cdc.nedss.proxy.ejb.entityproxyejb.bean.EntityProxyHome;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.bean.ObservationProxyHome;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.*;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.systemservice.util.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.webapp.nbs.action.person.util.*;
import gov.cdc.nedss.webapp.nbs.action.util.QuickEntryEventHelper;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.*;
import gov.cdc.nedss.entity.material.dt.*;
import gov.cdc.nedss.webapp.nbs.servlet.*;
import gov.cdc.nedss.systemservice.ejb.srtcachemanagerejb.bean.*;

import java.rmi.*;

import javax.ejb.*;
import javax.rmi.*;

import gov.cdc.nedss.webapp.nbs.action.ldf.BaseLdf;
import gov.cdc.nedss.webapp.nbs.form.observation.*;
import gov.cdc.nedss.webapp.nbs.form.morbidity.*;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.ldf.dt.*;


/**
 * Title:        CommonLabUtil.java
 * Description:	This is a util class to be used by view and edit classes for commmon functionality
 * Copyright:	Copyright (c) 2001
 * Company: 	Computer Sciences Corporation
 * @author	Pradeep Kumar Sharma
 * @version	1.0
 */

public class CommonLabUtil extends BaseLdf
{ 
	static final LogUtils logger = new LogUtils(CommonLabUtil.class.getName());
	static final CachedDropDownValues cdv = new CachedDropDownValues();
	static final QuickEntryEventHelper helper = new QuickEntryEventHelper();
    
     

	/**
     * @method : getLabResultProxyVO
     * @params : String, HttpSession
     * @returnType : LabResultProxyVO
     */
    public LabResultProxyVO getLabResultProxyVO(Long observationUID, HttpSession session)
    {
        LabResultProxyVO labResultProxyVO = null;
        MainSessionCommand msCommand = null;

        if (observationUID == null)
        {
            logger.error("observationUID is null inside getLabResultProxyVO method");
            return null;
        }
        else if (observationUID != null)
        {
            try
            {
                logger.debug("observationUID inside getLabResultProxyVO method is: " + observationUID);

                String sBeanJndiName = JNDINames.OBSERVATION_PROXY_EJB;
                String sMethod = "getLabResultProxy";
                Object[] oParams = new Object[] { observationUID };
                MainSessionHolder holder = new MainSessionHolder();
                msCommand = holder.getMainSessionCommand(session);

                ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
                labResultProxyVO = (LabResultProxyVO) arr.get(0);
            }
            catch (Exception ex)
            {

                if (session == null)
                {
                    logger.debug("Error: no session, please login");

                }
                logger.fatal("getLabResultProxyVO: ", ex);
            }
        }
        return labResultProxyVO;
    }

    /**
     * @method : getCodeSystemDescription
     * @params : String, HttpSession
     * @returnType : Treemap
     */
    public TreeMap<?, ?> getCodeSystemDescription(String Laboratory_id, HttpSession session)
    {
        TreeMap<?, ?> treeMap = null;
        MainSessionCommand msCommand = null;

        if (Laboratory_id == null)
        {
            logger.error("Laboratory_id is null inside getCodeSystemDescription method");
            return null;
        }
        else if (Laboratory_id != null)
        {
            try
            {
                logger.debug("Laboratory_id inside getCodeSystemDescription method is: " + Laboratory_id);

                String sBeanJndiName = JNDINames.OBSERVATION_PROXY_EJB;
                String sMethod = "getCodeSystemDescription";
                Object[] oParams = new Object[] { Laboratory_id };
                MainSessionHolder holder = new MainSessionHolder();
                msCommand = holder.getMainSessionCommand(session);

                ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
                treeMap = (TreeMap<?, ?>) arr.get(0);
            }
            catch (Exception ex)
            {

                if (session == null)
                {
                    logger.debug("Error: no session, please login");

                }
                logger.fatal("getCodeSystemDescription: ", ex);
            }
        }
        return treeMap;
    }

    protected PersonVO findMasterPatientRecord(Long mprUId, HttpSession session, NBSSecurityObj secObj)
    {

        PersonVO personVO = null;
        MainSessionCommand msCommand = null;

        try
        {
            String sBeanJndiName = JNDINames.EntityControllerEJB;
            String sMethod = "getMPR";
            Object[] oParams = new Object[] { mprUId };
            MainSessionHolder holder = new MainSessionHolder();
            msCommand = holder.getMainSessionCommand(session);

            ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
            personVO = (PersonVO) arr.get(0);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            if (session == null)
            {
                logger.error("Error: no session, please login");
            }

            logger.fatal("personVO: ", ex);
        }

        return personVO;
    }

    protected String formatDate(java.sql.Timestamp timestamp)
    {
        Date date = null;
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        if (timestamp != null)
        {
            date = new Date(timestamp.getTime());
        }
        if (date == null)
        {
            return "";
        }
        else
        {
            return formatter.format(date);
        }
    }

    protected static String formatDateWithTime(Timestamp timestamp)
    {
        // Timestamp timestampp = Timestamp.valueOf("2005-07-18 16:04:35.057");
        Date date = null;
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm aaa");
        if (timestamp != null)
        {
            date = new Date(timestamp.getTime());
        }
        if (date == null)
        {
            return "";
        }
        else
        {
            return formatter.format(date);
        }
    }

    protected void convertActIdInToRequestObject(LabResultProxyVO labResultProxyVO, HttpServletRequest request)
    {
        if (labResultProxyVO.getTheActIdDTCollection() != null)
        {
            Iterator<Object> actIDIT = labResultProxyVO.getTheActIdDTCollection().iterator();
            while (actIDIT.hasNext())
            {
                ActIdDT actIdDT = (ActIdDT) actIDIT.next();
                if (actIdDT.getRootExtensionTxt() != null
                        && actIdDT.getTypeCd().equalsIgnoreCase(NEDSSConstants.FILLER_NUMBER_FOR_ACCESSION_NUMBER))
                {
                    request.setAttribute("LAB125", actIdDT.getRootExtensionTxt());
                    logger.debug("+++++++++++ actIdDT.getRootExtensionTxt() " + actIdDT.getRootExtensionTxt());
                }
                else
                {
                    request.setAttribute("LAB125", "");

                }

            }
        }
    }

    protected void convertMaterialInToRequestObject(LabResultProxyVO labResultProxyVO, HttpServletRequest request)
    {
        if (labResultProxyVO.getTheMaterialVOCollection() != null)
        {
            Iterator<Object> materialIT = labResultProxyVO.getTheMaterialVOCollection().iterator();
            while (materialIT.hasNext())
            {
                MaterialVO materialVO = (MaterialVO) materialIT.next();
                MaterialDT materialDT = materialVO.getTheMaterialDT();

                if (materialDT.getCd() != null)
                {
                    request.setAttribute("SpecimenSource", materialDT.getCd());
                }
                else
                {
                    request.setAttribute("SpecimenSource", "");

                } 
            }
        }
    }

    protected void convertLAB214RequestObject(ObservationVO lab112VO, LabResultProxyVO labResultProxyVO,
            HttpServletRequest request)
    {

        ObservationVO obsVO214 = null;
        Long Obs214Uid = null;
        // Long obs112Uid = lab112VO.getTheObservationDT().getObservationUid();

        if (lab112VO.getTheActRelationshipDTCollection() != null)
        {
            ActRelationshipDT actRelDT = null;
            Iterator<Object> it = lab112VO.getTheActRelationshipDTCollection().iterator();
            while (it.hasNext())
            {
                actRelDT = (ActRelationshipDT) it.next();
                if (actRelDT.getTypeCd().equalsIgnoreCase(NEDSSConstants.CAUS)
                        && actRelDT.getTargetClassCd().equalsIgnoreCase(NEDSSConstants.CLASS_CD_OBS)
                        && actRelDT.getSourceClassCd().equalsIgnoreCase(NEDSSConstants.CLASS_CD_OBS)
                        && actRelDT.getTypeDescTxt().equalsIgnoreCase(NEDSSConstants.IS_CAUSE_FOR)
                        && actRelDT.getTargetActUid().compareTo(lab112VO.getTheObservationDT().getObservationUid()) == 0)
                {
                    Obs214Uid = actRelDT.getSourceActUid();
                    obsVO214 = fetchObservationVO(Obs214Uid, labResultProxyVO.getTheObservationVOCollection());
                    if (obsVO214.getObsValueTxtDT_s(0).getValueTxt() != null)
                    {
                        request.setAttribute("LAB214", obsVO214.getObsValueTxtDT_s(0).getValueTxt());
                    }
                    else
                    {
                        request.setAttribute("LAB214", "");
                    }

                }
            }
        } 
    }

    /**
     * @method : fetchObservationVO
     * @params : ObservationUID, ObservationVOCollection
     * @returnType : ObservationVO
     */
    private ObservationVO fetchObservationVO(Long sUID, Collection<ObservationVO> obsColl)
    {

        Iterator<ObservationVO> obsIter = obsColl.iterator();

        while (obsIter.hasNext())
        { 
            ObservationVO observationVO = (ObservationVO) obsIter.next();

            if (observationVO.getTheObservationDT().getObservationUid().compareTo(sUID) == 0)

                return observationVO;
        }

        return null;
    } // fetchObservationVO

    protected ObservationVO convertProxyToObs112(LabResultProxyVO labResultProxyVO, HttpServletRequest request)
    {
        ObservationVO obs112 = null;
        ArrayList<ObservationVO> list = (ArrayList<ObservationVO>) labResultProxyVO.getTheObservationVOCollection();
        HttpSession session = request.getSession();
        CachedDropDownValues cdv = new CachedDropDownValues();
        Object objObsUid = NBSContext.retrieve(session, "DSObservationUID");
        Long observationUid = null;
        if (objObsUid instanceof String)
            observationUid = new Long((String) objObsUid);
        else if (objObsUid instanceof Long)
            observationUid = (Long) objObsUid;

        if (list != null)
        {
            Iterator<ObservationVO> lab112It = list.iterator();
            while (lab112It.hasNext())
            {
                ObservationVO temp112 = (ObservationVO) lab112It.next();
                if (temp112.getTheObservationDT().getObservationUid().compareTo(observationUid) == 0)
                {
                    obs112 = temp112;

                    ObservationDT obsDt112 = obs112.getTheObservationDT();

                    if (obsDt112.getProgAreaCd() != null)
                    {
                        request.setAttribute("programAreaCd", obsDt112.getProgAreaCd());
                    }
                    else
                    {
                        request.setAttribute("programAreaCd", ""); 
                    }
                    if (obsDt112.getJurisdictionCd() != null)
                    {
                        request.setAttribute("jurisdictionCd", obsDt112.getJurisdictionCd());
                    }
                    else
                    {
                        request.setAttribute("jurisdictionCd", ""); 
                    }

                    request.setAttribute("sharedInd", obsDt112.getSharedInd());
                    if (obsDt112.getElectronicInd() != null && obsDt112.getElectronicInd().equalsIgnoreCase("Y")
                            && obsDt112.getEffectiveFromTime() != null
                            && !obsDt112.getEffectiveFromTime().toString().toString().equalsIgnoreCase(""))
                    {
                        request.setAttribute("dateSpecimenCollected",
                                formatDateWithTime(obsDt112.getEffectiveFromTime()));
                    }
                    else if (obsDt112.getEffectiveFromTime() != null
                            && !obsDt112.getEffectiveFromTime().toString().toString().equalsIgnoreCase(""))
                    {
                        request.setAttribute("dateSpecimenCollected", formatDate(obsDt112.getEffectiveFromTime()));
                    }
                    else
                        request.setAttribute("dateSpecimenCollected", "");
                    
                    if (obsDt112.getElectronicInd() != null)
                    {
                        if (obsDt112.getElectronicInd().equals("N") || obsDt112.getElectronicInd().equals("E"))
                        {
                            request.setAttribute("LAB112", obsDt112.getCdDescTxt());
                            if (obsDt112.getCdDescTxt() != null && obsDt112.getCdDescTxt().equals("NI"))
                                request.setAttribute("LAB112", "No Information Given");
                        }
                    }
                        
                    
                    if( obsDt112.getPregnantIndCd() != null )
                    {
                        request.setAttribute("pregnantIndCd",  obsDt112.getPregnantIndCd()  );
                    }
                    if( obsDt112.getPregnantWeek() != null )
                    {
                        request.setAttribute("pregnantWeek", obsDt112.getPregnantWeek() );
                    }
                    

                    // check for eletronic Ind. first
                    if (obsDt112.getElectronicInd() != null)
                    {
                        if (obsDt112.getElectronicInd().equalsIgnoreCase("Y"))
                        {
                            String LAB112ELR = "";
                            if (obsDt112.getAltCdDescTxt() != null)
                                LAB112ELR = obsDt112.getCdDescTxt() + " (" + obsDt112.getAltCdDescTxt() + ")";
                            else
                                LAB112ELR = obsDt112.getCdDescTxt();
                            request.setAttribute("LAB112", LAB112ELR);

                            String LAB269AndAssociatedVals = "";
                            String lab268lab270 = "";
                            if (obsDt112.getCdSystemCd() != null && obsDt112.getCdSystemDescTxt() != null)
                            {
                                lab268lab270 = "(" + obsDt112.getCdSystemCd() + " " + obsDt112.getCdSystemDescTxt()
                                        + ")";
                            }
                            else if (obsDt112.getCdSystemCd() != null && obsDt112.getCdSystemDescTxt() == null)
                            {
                                lab268lab270 = "(" + obsDt112.getCdSystemCd() + ")";
                            }
                            else if (obsDt112.getCdSystemCd() == null && obsDt112.getCdSystemDescTxt() != null)
                                lab268lab270 = "(" + obsDt112.getCdSystemDescTxt() + ")";
                            if (obsDt112.getCd() != null)
                                LAB269AndAssociatedVals = obsDt112.getCd() + lab268lab270;
                            else
                                LAB269AndAssociatedVals = lab268lab270;
                            String lab272andAssociates = "";
                            String lab271lab273 = "";
                            if (obsDt112.getAltCdSystemCd() != null)
                                if (obsDt112.getAltCdSystemDescTxt() != null)
                                {
                                    lab271lab273 = "(" + obsDt112.getAltCdSystemCd() + " "
                                            + obsDt112.getAltCdSystemDescTxt() + ")";
                                }
                                else
                                {
                                    lab271lab273 = "(" + obsDt112.getAltCdSystemCd() + ")";
                                }

                            else if (obsDt112.getAltCdSystemCd() != null)
                                if (obsDt112.getAltCdSystemDescTxt() == null)
                                {
                                    lab271lab273 = "(" + obsDt112.getAltCdSystemCd() + ")";
                                }
                                else if (obsDt112.getAltCdSystemCd() == null
                                        && obsDt112.getAltCdSystemDescTxt() != null)
                                    lab271lab273 = "(" + obsDt112.getAltCdSystemDescTxt() + ")";
                            
                            if (obsDt112.getAltCd() != null)
                                lab272andAssociates = obsDt112.getAltCd() + lab271lab273;
                            else
                                lab272andAssociates = lab271lab273;
                            String lab269andlab272 = "";
                            if (LAB269AndAssociatedVals != null && !LAB269AndAssociatedVals.equals("")
                                    && lab272andAssociates != null && !lab272andAssociates.equals(""))
                                lab269andlab272 = LAB269AndAssociatedVals + "/" + lab272andAssociates;
                            else if (LAB269AndAssociatedVals != null && !LAB269AndAssociatedVals.equals("")
                                    && (lab272andAssociates == null || lab272andAssociates.equals("")))
                                lab269andlab272 = LAB269AndAssociatedVals;
                            else if ((LAB269AndAssociatedVals == null || LAB269AndAssociatedVals.equals(""))
                                    && (lab272andAssociates != null && !lab272andAssociates.equals("")))
                                lab269andlab272 = lab272andAssociates;
                            request.setAttribute("lab269andlab272", lab269andlab272);
                            if (obsDt112.getStatusCd() != null)
                            {
                                request.setAttribute("LAB196", cdv.getDescForCode("ACT_OBJ_ST", obsDt112.getStatusCd()));
                                
                            }
                            else
                            {
                                request.setAttribute("LAB196", "");
                            }
                        }
                    }

                    if (obsDt112.getCdDescTxt() != null)
                    {
                        request.setAttribute("OrderedTestName", obsDt112.getCdDescTxt());
                    }
                    else
                    {
                        request.setAttribute("OrderedTestName", "");
                    }

                    if (obsDt112.getActivityToTime() != null)
                    {
                        request.setAttribute("LAB197", formatDate(obsDt112.getActivityToTime()));
                    }
                    else
                    {
                        request.setAttribute("LAB197", "");
                    }
                    if (obsDt112.getRptToStateTime() != null)
                    {
                        request.setAttribute("LAB201", formatDate(obsDt112.getRptToStateTime()));
                    }
                    else
                    {
                        request.setAttribute("LAB201", "");
                    }

                   
                    if (obsDt112.getTargetSiteCd() != null)
                    {
                        String specimenSiteDesc = "";
                        specimenSiteDesc = cdv.getDescForCode("ANATOMIC_SITE", obsDt112.getTargetSiteCd());
                        request.setAttribute("SpecimenSitelAB112forView", specimenSiteDesc);
                        request.setAttribute("SpecimenSitelAB112", obsDt112.getTargetSiteCd());
                    }
                    else
                    {
                        request.setAttribute("SpecimenSitelAB112forView", "");
                        request.setAttribute("SpecimenSitelAB112", ""); 
                    }

                    if (obsDt112.getElectronicInd() != null && obsDt112.getElectronicInd().equalsIgnoreCase("Y")
                            && !obsDt112.getElectronicInd().trim().equalsIgnoreCase(""))
                    {

                        String specimenSite = "";

                        if (obsDt112.getTargetSiteDescTxt() != null && obsDt112.getTargetSiteCd() != null)
                        {
                            specimenSite = obsDt112.getTargetSiteDescTxt() + "(" + obsDt112.getTargetSiteCd() + ")";
                        }
                        else if (obsDt112.getTargetSiteDescTxt() != null && obsDt112.getTargetSiteCd() == null)
                        {
                            specimenSite = obsDt112.getTargetSiteDescTxt();
                        }
                        else if (obsDt112.getTargetSiteDescTxt() == null && obsDt112.getTargetSiteCd() != null)
                        {
                            String specimenSiteDesc = cdv.getDescForCode("ANATOMIC_SITE", obsDt112.getTargetSiteCd());
                            if (specimenSiteDesc != null)
                            {
                                specimenSite = specimenSiteDesc;
                            }
                            specimenSite += "(" + obsDt112.getTargetSiteCd() + ")";
                        }
                        request.setAttribute("SpecimenSitelAB112forView", specimenSite);
                    }

                    if (obsDt112.getElectronicInd() != null && !obsDt112.getElectronicInd().equalsIgnoreCase("E")
                            && !obsDt112.getElectronicInd().trim().equalsIgnoreCase(""))
                    {
                        if (obsDt112.getTargetSiteDescTxt() != null
                                && !obsDt112.getTargetSiteDescTxt().trim().equalsIgnoreCase(""))
                            request.setAttribute("SpecimenSite", obsDt112.getTargetSiteDescTxt());
                        else
                            request.setAttribute("SpecimenSite", "");
                    }
                    else
                    {
                        if (obsDt112.getElectronicInd() != null && obsDt112.getTargetSiteCd() != null
                                && !obsDt112.getElectronicInd().equalsIgnoreCase("E")
                                && !obsDt112.getTargetSiteCd().trim().equalsIgnoreCase(""))
                            request.setAttribute("SpecimenSite", obsDt112.getTargetSiteCd());
                        else
                            request.setAttribute("SpecimenSite", "N/A");
                    }
                    request.setAttribute("LAB202", formatDate(obsDt112.getAddTime()));
                    if (obsDt112.getElectronicInd() != null
                            && (!obsDt112.getElectronicInd().equalsIgnoreCase("Y") || !obsDt112.getElectronicInd()
                                    .equalsIgnoreCase("E")))
                    {
                        request.setAttribute("LAB203", obsDt112.getAddUserName()); 
                        // BB  -  civil00012298  -  used Name field now to display
                    }
                    else
                    {
                        request.setAttribute("LAB203", obsDt112.getAddUserName() + " - External");
                     // BB  -  civil00012298  -  used Name field now to display

                    }
                    request.setAttribute("LAB211", formatDate(obsDt112.getLastChgTime()));
                    request.setAttribute("LAB212", obsDt112.getLastChgUserName());  
                    // BB  -  civil00012298  -  used Name field now to display
                    
                    if (obsDt112.getActivityToTime() != null
                            && !obsDt112.getActivityToTime().toString().trim().equalsIgnoreCase(""))
                    {
                        request.setAttribute("LAB197", formatDate(obsDt112.getActivityToTime()));
                    }
                    else
                    {
                        request.setAttribute("LAB197", ""); 
                    }
                    if (obsDt112.getRptToStateTime() != null
                            && !obsDt112.getRptToStateTime().toString().trim().equalsIgnoreCase(""))
                    {
                        request.setAttribute("LAB201", formatDate(obsDt112.getRptToStateTime()));
                    }
                    else
                    {
                        request.setAttribute("LAB201", ""); 
                    }

                    Iterator<Object> actIDIT = temp112.getTheActIdDTCollection().iterator();
                    boolean valueSet = false;
                    while (actIDIT.hasNext())
                    {
                        ActIdDT actIdDT = (ActIdDT) actIDIT.next();
                        if (actIdDT.getRootExtensionTxt() != null
                                && actIdDT.getTypeCd().equalsIgnoreCase(
                                        NEDSSConstants.FILLER_NUMBER_FOR_ACCESSION_NUMBER))
                        {
                            request.setAttribute("LAB125", actIdDT.getRootExtensionTxt());
                            // System.out.println("+++++++++++ actIdDT.getRootExtensionTxt() "
                            // +
                            // actIdDT.getRootExtensionTxt());
                            valueSet = true;
                        }
                        else if (!valueSet)
                        {
                            request.setAttribute("LAB125", ""); 
                        }

                    }

                    request.setAttribute("LAB212", obsDt112.getLastChgUserName()); 
                    // BB  -  civil00012298  -  used Name field now to display
                    
                    if (temp112.getTheObservationDT().getProcessingDecisionCd() != null
                            && !temp112.getTheObservationDT().getProcessingDecisionCd().trim().equals(""))
                    {
                        request.setAttribute("markAsReviewReason", temp112.getTheObservationDT()
                                .getProcessingDecisionCd());
                        request.setAttribute("markAsReviewReasonDesc", CachedDropDowns.getCodeDescTxtForCd(temp112
                                .getTheObservationDT().getProcessingDecisionCd(),
                                NEDSSConstants.STD_PROCESSING_DECISION_LIST_SYPHILIS_AND_NONSYPHILIS));
                    }

                    break;
                } 
            }
        }
        return obs112;
    }

    protected void convertProxyToRequest(LabResultProxyVO labResultProxyVO, HttpServletRequest request,
            NBSSecurityObj secObj)
    {
        // Long reportingUID = null;
        String programAreaCode = null;
        ObservationVO lab112VO = null;
        if (labResultProxyVO.getTheObservationVOCollection() != null)
        {
            Iterator<ObservationVO> labIt = labResultProxyVO.getTheObservationVOCollection().iterator();
            // Long observationUid = (Long)
            // request.getSession().getAttribute("DSObservationUID");
            HttpSession session = request.getSession();

            Long observationUid = null;
            Object objObsUID = NBSContext.retrieve(session, "DSObservationUID");
            if (objObsUID instanceof String)
            {
                observationUid = new Long((String) objObsUID);
            }
            else if (objObsUID instanceof Long)
            {
                observationUid = (Long) objObsUID;
            }
            while (labIt.hasNext())
            {
                ObservationVO obsVO = (ObservationVO) labIt.next();
                if (obsVO.getTheObservationDT().getObservationUid().compareTo(observationUid) == 0)
                {
                    lab112VO = obsVO;
                    programAreaCode = lab112VO.getTheObservationDT().getProgAreaCd();
                    request.setAttribute("dateReceivedHidden",
                            StringUtils.formatDate(obsVO.getTheObservationDT().getAddTime()));
                }
            }
        }
        PersonVO pvo = getPatientVO(labResultProxyVO, lab112VO);
        pvo = this.findMasterPatientRecord(pvo.getThePersonDT().getPersonUid(), request.getSession(), secObj);
        /*
         * Added Business ObjectUID as a parameter for createXSP() method - CDF
         * modification
         */

        createXSP(NEDSSConstants.PATIENT_LDF, pvo.getThePersonDT().getPersonUid(), pvo, null, request);
        String contextAction = request.getParameter("ContextAction");
        convertPersonToRequest(pvo, request, secObj);
      
        if (lab112VO.getTheParticipationDTCollection() != null)
            convertParticipationsToRequest(labResultProxyVO, lab112VO, request);

        // To populate the dropDowns of resulted Test and Ordered Tests
        // if(reportingUID != null)
        // getDropDownLists(reportingUID.toString(), programAreaCode, request);
        if (lab112VO.getTheObservationDT() != null && lab112VO.getTheObservationDT().getElectronicInd() != null)
        {
            if (lab112VO.getTheObservationDT().getElectronicInd().equalsIgnoreCase("Y"))
            {
                convertOtherInformationToRequest(labResultProxyVO, lab112VO, pvo, request);
            }
        }

    }



    protected Long convertParticipationsToRequest(LabResultProxyVO labResultProxyVO, ObservationVO lab112VO,
            HttpServletRequest request)
    {
        Long materialUid = null;
        Long receivingOrgUid = null;
        Long orderFacilityUid = null;
        Long reportingLabUid = null;
        Long providerUid = null;
        Iterator<Object> participationIt = lab112VO.getTheParticipationDTCollection().iterator();
        boolean flag = true;
        while (participationIt.hasNext())
        {
            ParticipationDT pdt = (ParticipationDT) participationIt.next();
            if (pdt.getActClassCd().equalsIgnoreCase(NEDSSConstants.CLASS_CD_OBS)
                    && pdt.getRecordStatusCd().equalsIgnoreCase("ACTIVE") && pdt.getStatusCd().equalsIgnoreCase("A")
                    && pdt.getSubjectClassCd().equalsIgnoreCase("MAT") && pdt.getTypeCd().equalsIgnoreCase("SPC")
                    && pdt.getTypeDescTxt().equalsIgnoreCase("Specimen"))
            {
                materialUid = pdt.getSubjectEntityUid();
                if (labResultProxyVO.getTheMaterialVOCollection() != null)
                    convertMaterialToRequest(labResultProxyVO.getTheMaterialVOCollection(), lab112VO, materialUid,
                            request);
            }

            if (pdt.getActClassCd().equalsIgnoreCase(NEDSSConstants.CLASS_CD_OBS)
                    && pdt.getRecordStatusCd().equalsIgnoreCase("ACTIVE") && pdt.getStatusCd().equalsIgnoreCase("A")
                    && pdt.getSubjectClassCd().equalsIgnoreCase(NEDSSConstants.ORGANIZATION)
                    && pdt.getTypeCd().equalsIgnoreCase("AUT") && pdt.getTypeDescTxt().equalsIgnoreCase("Author"))
            {
                reportingLabUid = pdt.getSubjectEntityUid();
            }
            if (pdt.getActClassCd().trim().equalsIgnoreCase(NEDSSConstants.CLASS_CD_OBS)
                    && pdt.getRecordStatusCd().trim().equalsIgnoreCase("ACTIVE")
                    && pdt.getStatusCd().trim().equalsIgnoreCase("A")
                    && pdt.getSubjectClassCd().trim().equalsIgnoreCase(NEDSSConstants.ORGANIZATION)
                    && pdt.getTypeCd().trim().equalsIgnoreCase("TRC")
                    && pdt.getTypeDescTxt().trim().equalsIgnoreCase("Tracker"))
            {
                receivingOrgUid = pdt.getSubjectEntityUid();
                convertOtherInformationRecngOrgToRequest(labResultProxyVO, receivingOrgUid, request);
            }
            if (pdt.getActClassCd().equalsIgnoreCase(NEDSSConstants.CLASS_CD_OBS)
                    && pdt.getRecordStatusCd().equalsIgnoreCase("ACTIVE") && pdt.getStatusCd().equalsIgnoreCase("A")
                    && pdt.getSubjectClassCd().equalsIgnoreCase(NEDSSConstants.ORGANIZATION)
                    && pdt.getTypeCd().equalsIgnoreCase("ORD") && pdt.getTypeDescTxt().equalsIgnoreCase("Orderer"))
            {
                orderFacilityUid = pdt.getSubjectEntityUid();
            }
            if (labResultProxyVO.getTheOrganizationVOCollection() != null)
            {
                convertOrganizationsToRequest(labResultProxyVO.getTheOrganizationVOCollection(), lab112VO,
                        reportingLabUid, orderFacilityUid, request);
            }

            if (pdt.getActClassCd().equalsIgnoreCase(NEDSSConstants.CLASS_CD_OBS)
                    && pdt.getRecordStatusCd().equalsIgnoreCase("ACTIVE") && pdt.getStatusCd().equalsIgnoreCase("A")
                    && pdt.getSubjectClassCd().equalsIgnoreCase(NEDSSConstants.PERSON)
                    && pdt.getTypeCd().equalsIgnoreCase("ORD") && pdt.getTypeDescTxt().equalsIgnoreCase("Orderer"))
            {
                providerUid = pdt.getSubjectEntityUid();
            }

            if (labResultProxyVO.getThePersonVOCollection() != null && providerUid != null && flag)
            {
                convertProviderToRequest(labResultProxyVO.getThePersonVOCollection(), lab112VO, providerUid, request);
                request.setAttribute("providerUID", providerUid);
                flag = false;
                // providerUid = null;
            }
            else if (providerUid == null)
            {
                request.setAttribute("providerDetails", "There is no Ordering Provider selected.");
            } 
        } 
        return reportingLabUid;
    }
 
    protected void convertPersonToRequest(PersonVO personVO, HttpServletRequest request, NBSSecurityObj secObj)
    {

        CachedDropDownValues cachedConverter = new CachedDropDownValues();
        TreeMap<Object, Object> raceMap = cachedConverter.getRaceCodes();
        ArrayList<Object> pNamelist = (ArrayList<Object>) personVO.getThePersonNameDTCollection();
        String electronicInd = personVO.getThePersonDT().getElectronicInd();
        request.setAttribute("ELRelectronicInd", electronicInd);
        Iterator<Object> pNameIt = pNamelist.iterator();
        while (pNameIt.hasNext())
        {
            PersonNameDT pNameDT = (PersonNameDT) pNameIt.next();
            if (pNameDT != null)
            {
                if (pNameDT.getNmUseCd().equalsIgnoreCase(NEDSSConstants.LEGAL_NAME))
                {
                    /* Name Details if we are seeing ELR data */
                    if (request.getAttribute("ELRelectronicInd") != null
                            && request.getAttribute("ELRelectronicInd").equals("Y"))
                    {
                        StringBuffer personLegalDetails = new StringBuffer("");
                        personLegalDetails.append(pNameDT.getNmPrefix() == null ? "" : pNameDT.getNmPrefix()); // 101
                        personLegalDetails.append(pNameDT.getFirstNm() == null ? "" : " " + pNameDT.getFirstNm()); // 104
                        personLegalDetails.append(pNameDT.getMiddleNm() == null ? "" : " " + pNameDT.getMiddleNm()); // 105
                        personLegalDetails.append(pNameDT.getMiddleNm2() == null ? "" : " " + pNameDT.getMiddleNm2()); // 106
                        personLegalDetails.append(pNameDT.getLastNm() == null ? "" : " " + pNameDT.getLastNm()); // 102
                        personLegalDetails.append(pNameDT.getLastNm2() == null ? "" : " " + pNameDT.getLastNm2()); // 103
                        personLegalDetails.append(pNameDT.getNmSuffix() == null ? "" : ", " + pNameDT.getNmSuffix()); // 107
                        personLegalDetails.append(pNameDT.getNmDegree() == null ? "" : ", " + pNameDT.getNmDegree()); // 108
                        request.setAttribute("personLegalDetails", personLegalDetails.toString());
                    }
                    /* Name Details, if we are seeing a manual Lab Data */
                    else if ((request.getAttribute("ELRelectronicInd") == null)
                            || (request.getAttribute("ELRelectronicInd") != null && request.getAttribute(
                                    "ELRelectronicInd").equals("N")))
                    {

                        StringBuffer personLegalDetails = new StringBuffer("");
                        personLegalDetails.append(pNameDT.getFirstNm() == null ? "" : " " + pNameDT.getFirstNm()); // 104
                        personLegalDetails.append(pNameDT.getMiddleNm() == null ? "" : " " + pNameDT.getMiddleNm()); // 105
                        personLegalDetails.append(pNameDT.getLastNm() == null ? "" : " " + pNameDT.getLastNm()); // 102
                        personLegalDetails.append(pNameDT.getNmSuffix() == null ? "" : ", " + pNameDT.getNmSuffix()); // 107
                        request.setAttribute("personLegalDetails", personLegalDetails.toString());

                    }
                }
                if (pNameDT.getNmUseCd().equalsIgnoreCase(NEDSSConstants.ALIAS_NAME))
                {
                    StringBuffer personAliasDetails = new StringBuffer("");
                    personAliasDetails.append(pNameDT.getNmPrefix() == null ? "" : pNameDT.getNmPrefix()); // 101
                    personAliasDetails.append(pNameDT.getFirstNm() == null ? "" : " " + pNameDT.getFirstNm()); // 104
                    personAliasDetails.append(pNameDT.getMiddleNm() == null ? "" : " " + pNameDT.getMiddleNm()); // 105
                    personAliasDetails.append(pNameDT.getMiddleNm2() == null ? "" : " " + pNameDT.getMiddleNm2()); // 106
                    personAliasDetails.append(pNameDT.getLastNm() == null ? "" : " " + pNameDT.getLastNm()); // 102
                    personAliasDetails.append(pNameDT.getLastNm2() == null ? "" : " " + pNameDT.getLastNm2()); // 103
                    personAliasDetails.append(pNameDT.getNmSuffix() == null ? "" : ", " + pNameDT.getNmSuffix()); // 107
                    personAliasDetails.append(pNameDT.getNmDegree() == null ? "" : ", " + pNameDT.getNmDegree()); // 108
                    request.setAttribute("personAliasDetails", personAliasDetails.toString());
                }
            }
        }

        ArrayList<Object> entityCollections = (ArrayList<Object>) personVO
                .getTheEntityLocatorParticipationDTCollection();
        Iterator<Object> locatorIt = entityCollections.iterator();
        while (locatorIt.hasNext())
        {
            EntityLocatorParticipationDT entityLocatorDT = (EntityLocatorParticipationDT) locatorIt.next();
            // ELP111 for home address
            if (entityLocatorDT.getCd() != null
                    && entityLocatorDT.getClassCd() != null && entityLocatorDT.getRecordStatusCd() != null
                    && entityLocatorDT.getUseCd() != null)
            {
                if (entityLocatorDT.getCd().equalsIgnoreCase("H")
                        &&
                        entityLocatorDT.getClassCd().equalsIgnoreCase("PST")
                        && entityLocatorDT.getRecordStatusCd().equalsIgnoreCase("ACTIVE")
                        && entityLocatorDT.getUseCd().equalsIgnoreCase("H"))
                {
                    StringBuffer homeAddress = new StringBuffer("");
                    PostalLocatorDT postalDT = entityLocatorDT.getThePostalLocatorDT();
                    if (postalDT != null)
                    {
                        if (request.getAttribute("ELRelectronicInd") != null
                                && request.getAttribute("ELRelectronicInd").equals("Y"))
                        {
                            homeAddress.append(postalDT.getStreetAddr1() == null ? "" : postalDT.getStreetAddr1());
                            homeAddress.append(postalDT.getStreetAddr2() == null ? " " : " "
                                    + postalDT.getStreetAddr2());
                            homeAddress.append(postalDT.getCityDescTxt() == null ? "<br/> " : "<br/>"
                                    + postalDT.getCityDescTxt());
                            homeAddress.append((postalDT.getCityDescTxt() != null) ? ", " : "");
                            homeAddress.append(postalDT.getStateCd() == null ? " " : " "
                                    + this.getStateDescTxt(postalDT.getStateCd()));
                            homeAddress.append(postalDT.getZipCd() == null ? " " : " " + postalDT.getZipCd());
                            homeAddress.append(postalDT.getCntyCd() == null ? " " : " "
                                    + this.getCountiesByDesc(postalDT.getCntyCd()));
                            if (postalDT.getCntryCd() == null)
                                homeAddress.append("");
                            else if (postalDT.getCntryCd() != null)
                            {
                                String homeCountryDesc = "";
                                if (cdv.getCountryCodesAsTreeMap() != null)
                                    homeCountryDesc = (String) cdv.getCountryCodesAsTreeMap()
                                            .get(postalDT.getCntryCd());
                                homeAddress.append(" " + homeCountryDesc);
                            }
                            /*
                             * homeAddress.append(postalDT.getCntryCd() == null
                             * ? " " : " " +
                             * cdv.getCountryCodesAsTreeMap().get(postalDT
                             * .getCntryCd()));
                             */
                        }
                        else if ((request.getAttribute("ELRelectronicInd") == null)
                                || (request.getAttribute("ELRelectronicInd") != null && request.getAttribute(
                                        "ELRelectronicInd").equals("N")))

                        {
                            homeAddress.append(postalDT.getStreetAddr1() == null ? "" : postalDT.getStreetAddr1());
                            homeAddress.append(postalDT.getCityDescTxt() == null ? "<br/> " : "<br/>"
                                    + postalDT.getCityDescTxt());
                            homeAddress
                                    .append((postalDT.getCityDescTxt() != null && postalDT.getStateCd() != null) ? ", "
                                            : "");
                            String stateDesc = this.getStateDescTxt(postalDT.getStateCd());
                            if ((stateDesc == null)
                                    || (stateDesc != null && stateDesc.trim().equalsIgnoreCase(""))
                                    && (postalDT.getStateCd() != null && !postalDT.getStateCd().trim()
                                            .equalsIgnoreCase("")))
                                stateDesc = postalDT.getStateCd();
                            else
                            {
                                if (stateDesc == null)
                                    stateDesc = "";
                            }
                            homeAddress.append(stateDesc);
                            homeAddress.append(postalDT.getZipCd() == null ? " " : " " + postalDT.getZipCd());
                            homeAddress.append(postalDT.getCntyCd() == null ? " " : " "
                                    + this.getCountiesByDesc(postalDT.getCntyCd()));
                            /*
                             * homeAddress.append(postalDT.getCntryCd() == null
                             * ? " " : " " +
                             * cdv.getCountryCodesAsTreeMap().get(postalDT
                             * .getCntryCd()));
                             */
                        }

                    }
                    request.setAttribute("homeAddress", homeAddress.toString());

                }

                // ELP111 for home address
                if (entityLocatorDT.getCd().equalsIgnoreCase("PH")
                        &&
                        // entityLocatorDT.getCdDescTxt().equalsIgnoreCase("Phone")
                        // &&
                        entityLocatorDT.getClassCd().equalsIgnoreCase("TELE")
                        && entityLocatorDT.getRecordStatusCd().equalsIgnoreCase("ACTIVE")
                        && entityLocatorDT.getUseCd().equalsIgnoreCase("H")
                        && entityLocatorDT.getStatusCd()!=null 
                		&& entityLocatorDT.getStatusCd().equals(NEDSSConstants.STATUS_ACTIVE))
                {
                    TeleLocatorDT teleDT = entityLocatorDT.getTheTeleLocatorDT();

                    request.setAttribute("Home177", teleDT.getPhoneNbrTxt());
                    request.setAttribute("Home181", teleDT.getExtensionTxt());

                }
                // ELP111 for home address
                if (entityLocatorDT.getCd().equalsIgnoreCase("PH")
                        &&
                        // entityLocatorDT.getCdDescTxt().equalsIgnoreCase("Phone")&&
                        entityLocatorDT.getClassCd().equalsIgnoreCase("TELE")
                        && entityLocatorDT.getRecordStatusCd().equalsIgnoreCase("ACTIVE")
                        && entityLocatorDT.getUseCd().equalsIgnoreCase("WP")
                        && entityLocatorDT.getStatusCd()!=null 
                		&& entityLocatorDT.getStatusCd().equals(NEDSSConstants.STATUS_ACTIVE))
                {
                    TeleLocatorDT teleDT = entityLocatorDT.getTheTeleLocatorDT();

                    request.setAttribute("Work177", teleDT.getPhoneNbrTxt());
                    request.setAttribute("Work181", teleDT.getExtensionTxt());

                }
            }

        }
        if (personVO.getTheEntityIdDTCollection() != null)
        {
            Iterator<Object> ssnIter = personVO.getTheEntityIdDTCollection().iterator();
            while (ssnIter.hasNext())
            {
                EntityIdDT entityIdDT = (EntityIdDT) ssnIter.next();
                if (entityIdDT.getTypeCd() != null && entityIdDT.getTypeCd().equalsIgnoreCase("SS") && entityIdDT.getStatusCd()!=null && entityIdDT.getStatusCd().equals(NEDSSConstants.STATUS_ACTIVE))
                {
                    request.setAttribute("DEM133", entityIdDT.getRootExtensionTxt());
                }
            }
        }
        StringBuffer entityList = new StringBuffer("");
        if (personVO.getTheEntityIdDTCollection() != null)
        {
            Iterator<Object> entityIter = personVO.getTheEntityIdDTCollection().iterator();
            while (entityIter.hasNext())
            {
                EntityIdDT entityIdDT = (EntityIdDT) entityIter.next();
                if (entityIdDT.getTypeCd() != null && !entityIdDT.getTypeCd().equalsIgnoreCase("SS") && entityIdDT.getStatusCd()!=null && entityIdDT.getStatusCd().equals(NEDSSConstants.STATUS_ACTIVE))
                {
                    String typeCd = cdv.getDescForCode("EI_TYPE_PAT", entityIdDT.getTypeCd()) == null ? " " : cdv
                            .getDescForCode("EI_TYPE_PAT", entityIdDT.getTypeCd());

                    entityList.append(typeCd).append("$");
                    entityList
                            .append(entityIdDT.getRootExtensionTxt() == null ? " " : entityIdDT.getRootExtensionTxt())
                            .append("$");
                    entityList.append(
                            entityIdDT.getAssigningAuthorityCd() == null ? " " : entityIdDT.getAssigningAuthorityCd())
                            .append("$");
                    entityList
                            .append(entityIdDT.getValidToTime() == null ? " " : StringUtils.formatDate(entityIdDT
                                    .getValidToTime())).append("$").append("|");

                }
            }
            // System.out.println("\n entityIdList "+entityList.toString());
            request.setAttribute("entityIdList", entityList.toString());
        }

        request.setAttribute("DEM113", personVO.getThePersonDT().getCurrSexCd());

        if (personVO.getThePersonRaceDTCollection() != null)
        {
            Iterator<Object> raceIt = personVO.getThePersonRaceDTCollection().iterator();
            StringBuffer raceBuffer = new StringBuffer("");
            while (raceIt.hasNext())
            {
                PersonRaceDT personRaceDT = (PersonRaceDT) raceIt.next();
                if (personRaceDT.getRaceCategoryCd() != null && personRaceDT.getRaceCategoryCd() != "")
                {
                    if (raceBuffer.length() == 0)
                        raceBuffer.append(raceMap.get(personRaceDT.getRaceCategoryCd()));
                    else
                        raceBuffer.append(", ").append(raceMap.get(personRaceDT.getRaceCategoryCd()));
                }
            }
            request.setAttribute("personRace", raceBuffer.toString());
        }
        request.setAttribute("dateOfBirth", StringUtils.formatDate(personVO.getThePersonDT().getBirthTime()));
        String ageAndUnits = "";
        if (personVO.getThePersonDT().getAgeReported() != null
                && personVO.getThePersonDT().getAgeReportedUnitCd() != null)
            ageAndUnits = personVO.getThePersonDT().getAgeReported() + " "
                    + cdv.getDescForCode("AGE_UNIT", personVO.getThePersonDT().getAgeReportedUnitCd());
        request.setAttribute("ageReported", ageAndUnits);
        // request.setAttribute("ageUnits",
        // pvo.getThePersonDT().getAgeReportedUnitCd());
        request.setAttribute("isDeceased", personVO.getThePersonDT().getDeceasedIndCd());
        request.setAttribute("DEM128", StringUtils.formatDate(personVO.getThePersonDT().getDeceasedTime()));
        request.setAttribute("DEM140", personVO.getThePersonDT().getMaritalStatusCd());
        request.setAttribute("DEM155", personVO.getThePersonDT().getEthnicGroupInd());
        request.setAttribute("DEM196", personVO.getThePersonDT().getDescription());
    }

    protected void convertMaterialToRequest(Collection<Object> materialColl, ObservationVO lab112VO, Long materialUid,
            HttpServletRequest request)
    {

        Iterator<Object> matIt = materialColl.iterator();
        while (matIt.hasNext())
        {
            MaterialVO mvo = (MaterialVO) matIt.next();
            if (mvo.getTheMaterialDT().getMaterialUid().compareTo(materialUid) == 0)
            {
                if (mvo.getTheMaterialDT().getCd() != null)
                    request.setAttribute("SpecimenSourceMaterial", mvo.getTheMaterialDT().getCd());

                /*
                 * if(lab112VO.getTheObservationDT().getElectronicInd()!= null
                 * && lab112VO.getTheObservationDT().getElectronicInd().trim()!=
                 * "") {
                 */
                String ResultComments = "";
                if (lab112VO.getTheObsValueTxtDTCollection() != null)

                {
                    Iterator<Object> iter = lab112VO.getTheObsValueTxtDTCollection().iterator();
                    while (iter.hasNext())
                    {
                        ObsValueTxtDT obsValueTxtDT = (ObsValueTxtDT) iter.next();
                        if (obsValueTxtDT.getTxtTypeCd() != null && obsValueTxtDT.getTxtTypeCd().equals("N"))
                            ResultComments = obsValueTxtDT.getValueTxt();
                    }

                }

                // System.out.println("checking value for
                // mvo.getTheMaterialDT().getCdDescTxt()" +
                // mvo.getTheMaterialDT().getCdDescTxt());
                // System.out.println("checking value for
                // mvo.getTheMaterialDT().getCd()" +
                // mvo.getTheMaterialDT().getCd());
                if (mvo.getTheMaterialDT().getCdDescTxt() != null && mvo.getTheMaterialDT().getCdDescTxt().trim() != "")
                    request.setAttribute("SpecimenSource", mvo.getTheMaterialDT().getCdDescTxt());
                else if (mvo.getTheMaterialDT().getCd() != null && !mvo.getTheMaterialDT().getCd().trim().equals(""))
                {
                    request.setAttribute("SpecimenSource",
                            cdv.getDescForCode("SPECMN_SRC", mvo.getTheMaterialDT().getCd()));
                }
                else
                    request.setAttribute("SpecimenSource", "");

                if (mvo.getTheMaterialDT().getCd() != null && mvo.getTheMaterialDT().getCd().trim() != "")
                    request.setAttribute("SpecimenSourceCD", mvo.getTheMaterialDT().getCd());
                else
                    request.setAttribute("SpecimenSourceCD", "");

                if (mvo.getTheMaterialDT().getDescription() != null
                        && !mvo.getTheMaterialDT().getDescription().trim().equalsIgnoreCase(""))
                    request.setAttribute("SpecimenDetails", mvo.getTheMaterialDT().getDescription());
                else
                    request.setAttribute("SpecimenDetails", "");

                StringBuffer materialBuff = new StringBuffer("");
                materialBuff
                        .append((mvo.getTheMaterialDT().getQty() == null ? "N/A" : mvo.getTheMaterialDT().getQty()));
                materialBuff.append((mvo.getTheMaterialDT().getQtyUnitCd() == null ? "" : " "
                        + mvo.getTheMaterialDT().getQtyUnitCd()));
                request.setAttribute("materialDetails", materialBuff);
                StringBuffer materialDangerCd = new StringBuffer("");
                materialDangerCd.append(mvo.getTheMaterialDT().getRiskDescTxt() == null ? "N/A" : mvo
                        .getTheMaterialDT().getRiskDescTxt());
                materialDangerCd.append(mvo.getTheMaterialDT().getRiskCd() == null ? "" : " "
                        + mvo.getTheMaterialDT().getRiskCd());
                request.setAttribute("materialDangerCd", materialDangerCd);

                /*
                 * } else { // mvo.getTheMaterialDT().getCd() == null &&
                 * mvo.getTheMaterialDT().getCd().trim()=="")
                 * request.setAttribute("SpecimenSource",
                 * mvo.getTheMaterialDT().getCd());
                 * request.setAttribute("SpecimenDetails", "N/A"); }
                 */
            }
        }
    }

    protected void convertOrganizationsToRequest(Collection<Object> orgColl, ObservationVO lab112VO, Long reportingUid,
            Long orderingUid, HttpServletRequest request)
    {
        OrganizationVO orgVO = null;
        OrganizationDT orgDT = null;
        PostalLocatorDT postalDT = null;
        TeleLocatorDT teleDT = null;
        Iterator<Object> itOrg = orgColl.iterator();

        while (itOrg.hasNext())
        {
            String displayString = "";
            orgVO = (OrganizationVO) itOrg.next();
            orgDT = orgVO.getTheOrganizationDT();
            if (orgVO.getTheOrganizationNameDTCollection() != null)
            {
                
                displayString = helper.makeORGDisplayString(orgVO);

                if (reportingUid != null)
                {
                    if (orgVO.getTheOrganizationDT().getOrganizationUid().compareTo(reportingUid) == 0)
                    {
                        request.setAttribute("reportingOrgDetails", displayString);
                        request.setAttribute("reportingSourceDetails", displayString);
                        request.setAttribute("reportingFacilityUID", reportingUid);
                        try
                        {
                            String labId = this.getLabId(reportingUid, request.getSession());
                            if (labId == null || labId.equals(""))
                                labId = "DEFAULT";

                            request.setAttribute("labId", labId);
                        }
                        catch (Exception e)
                        {

                        }
                    }
                }
                if (orderingUid != null)
                {
                    if (orgVO.getTheOrganizationDT().getOrganizationUid().compareTo(orderingUid) == 0)
                    {
                        if (displayString.length() <= 0)
                        {
                            request.setAttribute("orderingOrgDetails", "There is no Ordering Facility selected.");
                        }
                        else
                        {
                            request.setAttribute("orderingOrgDetails", displayString);
                            request.setAttribute("orderingOrgDetailsUID", reportingUid);
                        }
                    }
                }
                else if (orderingUid == null)
                {
                    request.setAttribute("orderingOrgDetails", "There is no Ordering Facility selected.");
                }
                displayString = null; 
            } 
        }// while
    }

    protected void convertProviderToRequest(Collection<Object> personVOColl, ObservationVO lab112VO, Long providerUid,
            HttpServletRequest request)
    {

        PersonVO pVO = null;
        // PersonDT personDT = null;
        // PostalLocatorDT postalDT = null;
        // TeleLocatorDT teleDT = null;
        Iterator<Object> itPVO = personVOColl.iterator();
        request.setAttribute("providerUID", providerUid);
        while (itPVO.hasNext())
        { 
            pVO = (PersonVO) itPVO.next();
            if (pVO.getThePersonDT().getPersonUid().compareTo(providerUid) == 0)
            {
                String displayString = "";
                displayString = helper.makePRVDisplayString(pVO);
                request.setAttribute("providerDetails", displayString);

            }
            request.setAttribute("providerUID", providerUid);
        } // While loop END while (itPVO.hasNext())
    }
 
    /**
     * Extracts a PersonVO object from a LabResultProxyVO object
     * 
     * @param type_cd
     *            the person type code
     * @param proxy
     *            the labResultProxyVO containing the PersonVO to be extracted
     * @return the PersonVO object
     */
    protected PersonVO getPersonVO(String type_cd, LabResultProxyVO proxy, Long obsUid)
    {
        Collection<ObservationVO> ObservationVOCollection = proxy.getTheObservationVOCollection();
        Collection<Object> participationDTCollection = null;
        Collection<Object> personVOCollection = null;
        ParticipationDT participationDT = null;
        PersonVO personVO = null;
        ObservationVO obsLabVO = null;

        Iterator<ObservationVO> itor = null;
        if (ObservationVOCollection != null && ObservationVOCollection.size() > 0)
        {
            itor = ObservationVOCollection.iterator();
            while (itor.hasNext())
            {
                obsLabVO = (ObservationVO) itor.next();
                if ((obsLabVO.getTheObservationDT().getObservationUid()).compareTo(obsUid) == 0)
                    break;

            }
        }
        participationDTCollection = obsLabVO.getTheParticipationDTCollection();

        personVOCollection = proxy.getThePersonVOCollection();

        if (participationDTCollection != null)
        {
            logger.debug("participationDTCollection  size: " + participationDTCollection.size());
            Iterator<Object> anIterator1 = null;
            Iterator<Object> anIterator2 = null;

            for (anIterator1 = participationDTCollection.iterator(); anIterator1.hasNext();)
            {
                participationDT = (ParticipationDT) anIterator1.next();

                if (participationDT.getTypeCd() != null && (participationDT.getTypeCd()).compareTo(type_cd) == 0)
                {

                    // logger.debug("convertProxyToRequestObj() got participationDT for  type_cd: "
                    // + participationDT.getTypeCd());
                    for (anIterator2 = personVOCollection.iterator(); anIterator2.hasNext();)
                    {
                        personVO = (PersonVO) anIterator2.next();

                        if (personVO.getThePersonDT().getPersonUid().longValue() == participationDT
                                .getSubjectEntityUid().longValue())
                        {

                            // logger.debug("convertProxyToRequestObj() got personVO for  person_uid: "
                            // +
                            // personVO.getThePersonDT().getPersonUid().longValue()
                            // + " and type_cd " + participationDT.getTypeCd());
                            return personVO;
                        }
                        else 
                            continue;
                    }
                }
                else 
                    continue;
            }
        }

        return null;
    }
 
    private ObservationVO findObservationLAB112(Collection<ObservationVO> coll, Long oldObsUID)
    {
        if (coll == null)
            return null;

        Iterator<ObservationVO> itor = coll.iterator();

        while (itor.hasNext())
        {
            ObservationVO obsVO = (ObservationVO) itor.next();
            ObservationDT obsDT = obsVO.getTheObservationDT();

            if (obsDT == null)
                continue;

            if (obsDT.getObservationUid().equals(oldObsUID))
                return obsVO; // found it!
        }

        // didn't find one
        return null;
    }


   private ArrayList<Object> findResultedTest(Long UID,LabResultProxyVO labResultProxyVO,HttpServletRequest request){
        ArrayList<Object> resultedTestVOCollection  = new ArrayList<Object> ();
        CachedDropDownValues cdv = new CachedDropDownValues();
        Collection<ObservationVO>  obsColl = labResultProxyVO.getTheObservationVOCollection();
        ObservationVO obsVO112= findObservationLAB112(obsColl,UID);

        if(obsVO112 == null)
           return null;
        if(obsVO112.getTheActRelationshipDTCollection() == null)
          return null;
       Iterator<Object>  obsVO112Iterator = obsVO112.getTheActRelationshipDTCollection().iterator();
        while (obsVO112Iterator.hasNext())
        {
          Collection<Object>  susceptibilityCollection  = new ArrayList<Object> ();
          Map<Object,Object> trackIsolateHashMap = new HashMap<Object,Object>();

          ActRelationshipDT actrelLAB112 = (ActRelationshipDT)obsVO112Iterator.next();
          if (actrelLAB112 == null)
               continue;
          if(actrelLAB112.getTypeCd().equals("COMP")){
            Long sourceActUidLAB112 = actrelLAB112.getSourceActUid();
            //System.out.println("\n\n  sourceActUidLAB112  " + sourceActUidLAB112);

            ObservationVO obsVO = fetchObservationVO(sourceActUidLAB112, obsColl);
            if(obsVO.getTheObservationDT().getCd().equalsIgnoreCase(NEDSSConstants.LAB_330))
            {
            	String cdDesc ="";
            	if(obsVO.getObsValueCodedDT_s(0).getCode() !=null)
            		cdDesc =cdv.getDescForCode( "PHVSFB_SPCMNPTSTATUS", obsVO.getObsValueCodedDT_s(0).getCode());
				request.setAttribute(NEDSSConstants.LAB_330, cdDesc);
            	continue;
            }
            /**
             * Begin Converting the code to the resulted text Descritpion - using Cd and CdSystemCd
             */
            String cdSystemCdResult = null;
            String obsCdResult = null;
            String cdDescTxtResult = null;
            if (obsVO.getTheObservationDT().getCdSystemCd() != null)
              cdSystemCdResult = obsVO.getTheObservationDT().getCdSystemCd();
            if (obsVO.getTheObservationDT().getCd()!= null)
              obsCdResult = obsVO.getTheObservationDT().getCd();

            ResultedTestVO resultedTestVO  = new ResultedTestVO();

            resultedTestVO.setTheIsolateVO(obsVO);

            if(obsVO != null && obsVO.getTheActRelationshipDTCollection() != null){

             Iterator<Object>  obsVOLAB220Iterator = obsVO.getTheActRelationshipDTCollection().iterator();

              while(obsVOLAB220Iterator.hasNext()){
                ActRelationshipDT actrelLAB220 = (ActRelationshipDT)obsVOLAB220Iterator.next();
                Long sourceActUidLAB220 = actrelLAB220.getSourceActUid();
                // System.out.println("\n\n  sourceActUidLAB220  " + sourceActUidLAB220);

                ObservationVO obsVOLAB222 = fetchObservationVO(sourceActUidLAB220, obsColl);
                resultedTestVO.setTheSusceptibilityVO(obsVOLAB222);

                if(obsVOLAB222 != null && obsVOLAB222.getTheActRelationshipDTCollection() != null){

                 Iterator<Object>  obsVOLAB222Iterator = obsVOLAB222.getTheActRelationshipDTCollection().iterator();

                   while(obsVOLAB222Iterator.hasNext()){
                     ActRelationshipDT actrelLAB222 = (ActRelationshipDT)obsVOLAB222Iterator.next();
                     Long sourceActUidLAB222 = actrelLAB222.getSourceActUid();
                     //System.out.println("\n\n  sourceActUidLAB222  " + sourceActUidLAB222);
                     ObservationVO dependentObsVO= fetchObservationVO(sourceActUidLAB222, obsColl);
                     /* Converting the code to descrption  Now using CdDescTxt and CdSystemCd Needs to chage it */
                     String cdSystemCd = null;
                     String obsCd = null;
                     String cdDescTxt = null;
                     if(dependentObsVO.getTheObservationDT().getCdSystemCd() != null )
                      cdSystemCd = dependentObsVO.getTheObservationDT().getCdSystemCd();
                     if(dependentObsVO.getTheObservationDT().getCd()!=null)        // use getCd()
                       obsCd = dependentObsVO.getTheObservationDT().getCd();       // Use getCd()
                     if(cdSystemCd !=null&& obsCd != null)
                     {
                       if(cdSystemCd.equals("LN"))
                        cdDescTxt = cdv.getResultedTestDesc(obsCd);
                       else if(!cdSystemCd.equals("LN"))
                         cdDescTxt = cdv.getResultedTestDescLab(cdSystemCd,obsCd);
                     }
                     if(cdDescTxt!=null)
                    	 dependentObsVO.getTheObservationDT().setCdDescTxt(cdDescTxt);
                     
                     if(dependentObsVO.getTheObservationDT().getCtrlCdDisplayForm()!=null 
      					   && dependentObsVO.getTheObservationDT().getCtrlCdDisplayForm().equalsIgnoreCase(NEDSSConstants.LAB_REPORT))
      				   susceptibilityCollection.add(dependentObsVO);
                     else
                    	 trackIsolateHashMap.put(dependentObsVO.getTheObservationDT().getCd(),dependentObsVO );

                     //susceptibilityCollection.add(obsVOLAB110);
                   }
                   resultedTestVO.setTheSusceptibilityCollection(susceptibilityCollection);
                   resultedTestVO.setTheTrackIsolateVOHaspMap(trackIsolateHashMap);
                   }
              }
            }
            resultedTestVOCollection.add(resultedTestVO);
          }

        } // End while Loop obsVO112Iterator.hasNext()
        return resultedTestVOCollection;
       }

       private ArrayList<Object> findResultedTestforELR(Long UID,LabResultProxyVO labResultProxyVO,HttpServletRequest request){
         ArrayList<Object> resultedTestVOCollection  = new ArrayList<Object> ();
         CachedDropDownValues cdv = new CachedDropDownValues();
         Collection<ObservationVO>  obsColl = labResultProxyVO.getTheObservationVOCollection();
         ObservationVO obsVO112= findObservationLAB112(obsColl,UID);

         if(obsVO112 == null)
            return null;
         if(obsVO112.getTheActRelationshipDTCollection() == null)
           return null;
        Iterator<Object>  obsVO112Iterator = obsVO112.getTheActRelationshipDTCollection().iterator();
         while (obsVO112Iterator.hasNext())
         {
           Collection<Object>  susceptibilityCollection  = new ArrayList<Object> ();

           ActRelationshipDT actrelLAB112 = (ActRelationshipDT)obsVO112Iterator.next();
           if (actrelLAB112 == null)
                continue;
           if(actrelLAB112.getTypeCd().equals("COMP")){
             Long sourceActUidLAB112 = actrelLAB112.getSourceActUid();
             //System.out.println("\n\n  sourceActUidLAB112  " + sourceActUidLAB112);

             ObservationVO obsVOLAB220 = fetchObservationVO(sourceActUidLAB112, obsColl);
             /**
              * Begin Converting the code to the resulted text Descritpion - using Cd and CdSystemCd
              */
             String cdSystemCdResult = null;
             String obsCdResult = null;
             String cdDescTxtResult = null;
             if (obsVOLAB220.getTheObservationDT().getCdSystemCd() != null)
               cdSystemCdResult = obsVOLAB220.getTheObservationDT().getCdSystemCd();
             if (obsVOLAB220.getTheObservationDT().getCd()!= null)
               obsCdResult = obsVOLAB220.getTheObservationDT().getCd();
             if (cdSystemCdResult != null && obsCdResult != null) {
               if (cdSystemCdResult.equals("LN"))
                 cdDescTxtResult = cdv.getResultedTestDesc(obsCdResult);
               else if (!cdSystemCdResult.equals("LN"))
                 cdDescTxtResult = cdv.getResultedTestDescLab(cdSystemCdResult, obsCdResult);
             }
             if (cdDescTxtResult != null)
               obsVOLAB220.getTheObservationDT().setCdDescTxt(cdDescTxtResult);
            /* End Converting the code to the resulted text Descritpion   */

             ResultedTestVO resultedTestVO  = new ResultedTestVO();

             resultedTestVO.setTheIsolateVO(obsVOLAB220);

             if(obsVOLAB220 != null && obsVOLAB220.getTheActRelationshipDTCollection() != null){

              Iterator<Object>  obsVOLAB220Iterator = obsVOLAB220.getTheActRelationshipDTCollection().iterator();

               while(obsVOLAB220Iterator.hasNext()){
                 ActRelationshipDT actrelLAB220 = (ActRelationshipDT)obsVOLAB220Iterator.next();
                 Long sourceActUidLAB220 = actrelLAB220.getSourceActUid();
                 // System.out.println("\n\n  sourceActUidLAB220  " + sourceActUidLAB220);

                 ObservationVO obsVOLAB222 = fetchObservationVO(sourceActUidLAB220, obsColl);
                 resultedTestVO.setTheSusceptibilityVO(obsVOLAB222);

                 if(obsVOLAB222 != null && obsVOLAB222.getTheActRelationshipDTCollection() != null){

                  Iterator<Object>  obsVOLAB222Iterator = obsVOLAB222.getTheActRelationshipDTCollection().iterator();

                    while(obsVOLAB222Iterator.hasNext()){
                      ActRelationshipDT actrelLAB222 = (ActRelationshipDT)obsVOLAB222Iterator.next();
                      Long sourceActUidLAB222 = actrelLAB222.getSourceActUid();
                      //System.out.println("\n\n  sourceActUidLAB222  " + sourceActUidLAB222);
                      ObservationVO obsVOLAB110 = fetchObservationVO(sourceActUidLAB222, obsColl);
                      /* Converting the code to descrption  Now using CdDescTxt and CdSystemCd Needs to chage it */
                      String cdSystemCd = null;
                      String obsCd = null;
                      String cdDescTxt = null;
                      if(obsVOLAB110.getTheObservationDT().getCdSystemCd() != null )
                       cdSystemCd = obsVOLAB110.getTheObservationDT().getCdSystemCd();
                      if(obsVOLAB110.getTheObservationDT().getCd()!=null)        // use getCd()
                        obsCd = obsVOLAB110.getTheObservationDT().getCd();       // Use getCd()
                      if(cdSystemCd !=null&& obsCd != null)
                      {
                        if(cdSystemCd.equals("LN"))
                         cdDescTxt = cdv.getResultedTestDesc(obsCd);
                        else if(!cdSystemCd.equals("LN"))
                          cdDescTxt = cdv.getResultedTestDescLab(cdSystemCd,obsCd);
                      }
                      if(cdDescTxt!=null)
                        obsVOLAB110.getTheObservationDT().setCdDescTxt(cdDescTxt);

                      susceptibilityCollection.add(obsVOLAB110);
                    }
                    resultedTestVO.setTheSusceptibilityCollection(susceptibilityCollection);
                 }
               }
             }
             resultedTestVOCollection.add(resultedTestVO);
           }

         } // End while Loop obsVO112Iterator.hasNext()
         return resultedTestVOCollection;
        }

    private Long getparticipationUid(ObservationVO obsVO)
    {
        Long participationUid = null;
        if (obsVO.getTheParticipationDTCollection() != null)
        {
            Iterator<Object> iter = obsVO.getTheParticipationDTCollection().iterator();
            while (iter.hasNext())
            {
                ParticipationDT participationDT = (ParticipationDT) iter.next();
                if (participationDT.getTypeCd().equals("PRF"))
                {
                    participationUid = participationDT.getSubjectEntityUid();
                }
            }
        }
        return participationUid;
    }

    private OrganizationVO getOrganizationVO(Collection<Object> orgCollection, Long uid)
    {
        OrganizationVO organizationVO = new OrganizationVO();
        if (orgCollection != null)
        {
            Iterator<Object> iter = orgCollection.iterator();
            while (iter.hasNext())
            {
                organizationVO = (OrganizationVO) iter.next();
                if (organizationVO.getTheOrganizationDT().getOrganizationUid().compareTo(uid) == 0)
                {
                    organizationVO.getOrganizationNameDT_s(0).getNmTxt();
                    return organizationVO;

                }
            }
        }
        return organizationVO;
    }


   protected void convertBatchObservationsToRequest(ObservationVO lab112VO, LabResultProxyVO labResultProxyVO,
                                     HttpServletRequest request, NBSSecurityObj secObj)
   {
     StringBuffer rsBuff = new StringBuffer("");
     Collection<Object>  obsResultedColl = new ArrayList<Object> ();
     Collection<Object>  obsResultedTestUids = new ArrayList<Object> ();
     Collection<Object>  obsCollectionsForSusAndReflex = new ArrayList<Object> ();
     ObservationVO obsVO214 = null;
     Long Obs214Uid = null;
     Long obs112Uid = lab112VO.getTheObservationDT().getObservationUid();

     if(lab112VO.getTheActRelationshipDTCollection()!= null)
     {
       ActRelationshipDT actRelDT = null;
      Iterator<Object>  it = lab112VO.getTheActRelationshipDTCollection().iterator();
       while(it.hasNext())
       {
         actRelDT =  (ActRelationshipDT)it.next();
         if(actRelDT.getTypeCd().equalsIgnoreCase("COMP") && actRelDT.getTargetClassCd().equalsIgnoreCase(NEDSSConstants.CLASS_CD_OBS)
         && actRelDT.getSourceClassCd().equalsIgnoreCase(NEDSSConstants.CLASS_CD_OBS) && actRelDT.getTypeDescTxt().equalsIgnoreCase("Has Component")
         && actRelDT.getTargetActUid().compareTo(lab112VO.getTheObservationDT().getObservationUid())==0)
         {
           obsResultedTestUids.add(actRelDT.getSourceActUid());
         }
         if(actRelDT.getTypeCd().equalsIgnoreCase(NEDSSConstants.CAUS) && actRelDT.getTargetClassCd().equalsIgnoreCase(NEDSSConstants.CLASS_CD_OBS)
         && actRelDT.getSourceClassCd().equalsIgnoreCase(NEDSSConstants.CLASS_CD_OBS) && actRelDT.getTypeDescTxt().equalsIgnoreCase(NEDSSConstants.IS_CAUSE_FOR)
         && actRelDT.getTargetActUid().compareTo(lab112VO.getTheObservationDT().getObservationUid())==0)
         {
             Obs214Uid = actRelDT.getSourceActUid();
         }

       }
     }
     ArrayList<Object> susListUidColl = new ArrayList<Object> ();
     if (obsResultedTestUids.size() > 0 &&
         labResultProxyVO.getTheObservationVOCollection() != null) {
        ActRelationshipDT actRelationDT = null;
      Iterator<Object>  resultedTestIt = obsResultedTestUids.iterator();
       while (resultedTestIt.hasNext()) {
         Long ObservationUid = (Long) resultedTestIt.next();
        Iterator<ObservationVO>  labIt = labResultProxyVO.getTheObservationVOCollection().
             iterator();
         while (labIt.hasNext()) {
           ObservationVO obsVO = (ObservationVO) labIt.next();
           if (obsVO.getTheObservationDT().getObservationUid().compareTo(ObservationUid) == 0) {
            // obsResultedColl.add(obsVO);
             rsBuff.append("Resulted Test:").append(obsVO.getTheObservationDT().getCdDescTxt()).append("<br/>");
             if(obsVO.getTheActRelationshipDTCollection()!= null)
             {
              Iterator<Object>  actRelIt = obsVO.getTheActRelationshipDTCollection().iterator();
               while(actRelIt.hasNext())
               {
                 actRelationDT =(ActRelationshipDT)actRelIt.next();
                 susListUidColl.add(actRelationDT.getSourceActUid());
               }
             }
           }
           else if (obsVO.getTheObservationDT().getObservationUid().compareTo(obs112Uid) == 0) {

           }
           else if(Obs214Uid != null && obsVO.getTheObservationDT().getObservationUid().compareTo(Obs214Uid)== 0)
           {
             obsVO214= obsVO;
           }

           else {
             obsCollectionsForSusAndReflex.add(obsVO);
           }
         }
       }
     }

     obsResultedColl =  this.findResultedTest(obs112Uid,labResultProxyVO,request);
     request.setAttribute("obsResultedColl", obsResultedColl);
     //System.out.println("obsCollectionsForSusAndReflex  size is :" + obsCollectionsForSusAndReflex.size());
     Collection<Object>  susVOColl = new ArrayList<Object> ();
     Collection<Object>  reflexVOColl = new ArrayList<Object> ();
     if (obsCollectionsForSusAndReflex.size() > 0 && susListUidColl!= null) {
      Iterator<Object>  susIt = susListUidColl.iterator();
       while(susIt.hasNext())
       {
         Long susLong = (Long)susIt.next();
        Iterator<Object>  susIterator = obsCollectionsForSusAndReflex.iterator();
         while(susIterator.hasNext())
         {
           ObservationVO obsVO = (ObservationVO)susIterator.next();
           if(obsVO.getTheObservationDT().getObservationUid().compareTo(susLong)==0)
           {
             susVOColl.add(obsVO);
           }
           else
           {
             reflexVOColl.add(obsVO);
           }
         }
       }
     }
     request.setAttribute("rsBuff", rsBuff);
//     request.setAttribute("obsResultedColl", obsResultedColl);
//     request.setAttribute("obsVO214", obsVO214);
     request.setAttribute("susVOColl", susVOColl);
     request.setAttribute("reflexVOColl", reflexVOColl);


   }

    protected ParticipationDT genericParticipationCreate(String actClassCd, Long actUid, Timestamp fromTime,
            String recordStatusCd, String statusCd, String subjectClassCd, Long subjectEntityUid, String typeCd,
            String typeDescTxt)
    {
        ParticipationDT participationDT = new ParticipationDT();
        participationDT.setActClassCd(actClassCd);
        participationDT.setActUid(actUid);
        participationDT.setFromTime(fromTime);
        participationDT.setRecordStatusCd(recordStatusCd);
        participationDT.setStatusCd(statusCd);
        participationDT.setSubjectClassCd(subjectClassCd);
        participationDT.setSubjectEntityUid(subjectEntityUid);
        participationDT.setTypeCd(typeCd);
        participationDT.setTypeDescTxt(typeDescTxt);
        participationDT.setItNew(true);

        return participationDT;
    }

    protected ParticipationDT getParticipationDT(Collection<Object> partici, String subject, String type_cd,
            String type_desc)
    {

        if (partici != null)
        {
            Iterator<Object> it = partici.iterator();
            while (it.hasNext())
            {
                ParticipationDT part = (ParticipationDT) it.next();
                // System.out.println("outside" + part.getTypeCd());
                if (part.getTypeCd().equalsIgnoreCase(type_cd) && part.getSubjectClassCd().equalsIgnoreCase(subject)
                        && part.getTypeDescTxt().equalsIgnoreCase(type_desc))
                {
                    // System.out.println("Inside");
                    return part;
                }
            }
        }
        return null;
    }

    protected RoleDT getRoleDT(Collection<Object> roleColl, String subject)
    {

        if (roleColl != null)
        {
            Iterator<Object> it = roleColl.iterator();
            while (it.hasNext())
            {
                RoleDT roleDT = (RoleDT) it.next();
                if (roleDT.getSubjectClassCd().equalsIgnoreCase(subject))
                {
                    return roleDT;
                }
            }
        }
        return null;
    }

protected Map<Object,Object> getOrganization(String organizationUid, HttpSession session)
    throws    Exception
{
  Map<Object,Object> returnMap = new HashMap<Object,Object>();
  StringBuffer result = new StringBuffer("");
  MainSessionCommand msCommand = null;
  String sBeanJndiName = JNDINames.ENTITY_PROXY_EJB;
  String sMethod = "getOrganization";
  Object[] oParams = new Object[] {
      new Long(organizationUid)};

  MainSessionHolder holder = new MainSessionHolder();
  msCommand = holder.getMainSessionCommand(session);
  ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
  OrganizationVO organizationVO = (OrganizationVO) arr.get(0);
  if (organizationVO != null) {

    if (organizationVO.getTheOrganizationNameDTCollection() != null) {
     Iterator<Object>  orgNameIt = organizationVO.
          getTheOrganizationNameDTCollection().
          iterator();
      while (orgNameIt.hasNext()) {
        OrganizationNameDT orgName = (OrganizationNameDT) orgNameIt.
            next();
        result.append(orgName.getNmTxt());
      }
    }
    if (organizationVO.getTheEntityLocatorParticipationDTCollection() != null) {
      if (organizationVO.getTheEntityLocatorParticipationDTCollection() != null) {
       Iterator<Object>  orgLocatorIt = organizationVO.
            getTheEntityLocatorParticipationDTCollection().iterator();
        while (orgLocatorIt.hasNext()) {
          EntityLocatorParticipationDT entityLocatorDT = (
              EntityLocatorParticipationDT) orgLocatorIt.next();
          if (entityLocatorDT != null) {
            PostalLocatorDT postaLocatorDT = entityLocatorDT.
                getThePostalLocatorDT();
            if (postaLocatorDT != null) {
              if (postaLocatorDT.getStreetAddr1() != null) {
                result.append("<br/>").append(postaLocatorDT.
                                             getStreetAddr1());
              }
              if (postaLocatorDT.getStreetAddr2() != null) {
                result.append("<br/>").append(postaLocatorDT.
                                             getStreetAddr2());
              }
              if (postaLocatorDT.getCityCd() != null) {
                result.append("<br/>").append(postaLocatorDT.getCityCd());

              }
              if (postaLocatorDT.getStateCd() != null) {
                result.append(", ").append(postaLocatorDT.getStateCd());

              }
              if (postaLocatorDT.getZipCd() != null) {
                result.append(" ").append(postaLocatorDT.getZipCd());

              }
            }
          }
          TeleLocatorDT telelocatorDT = entityLocatorDT.
              getTheTeleLocatorDT();
          if (telelocatorDT != null) {
            if (telelocatorDT.getPhoneNbrTxt() != null) {
              result.append("<br/>").append(telelocatorDT.
                                           getPhoneNbrTxt());
            }
            if (telelocatorDT.getExtensionTxt() != null) {
              result.append(" Ext. ").append(telelocatorDT.
                                             getExtensionTxt());
            }
            break;
          }
        }
      }
    }
  }

  returnMap.put("UID", organizationUid);
  returnMap.put("result", result.toString());
  return returnMap;

}

/**
 * getDropDownLists method gets ther drop downs for SRT Filttering mechanism on View(and in some add and edit condition)
 * @param reportingUID String
 * @param type String
 * @param conditionCodeCode String
 * @param programAreaCode String
 * @param orderedTestCode String
 * @param resultedTestCode String
 * @param request HttpServletRequest
 */
public static void getDropDownLists(String reportingUID, String type, String conditionCodeCode, String programAreaCode, String orderedTestCode, String resultedTestCode, HttpServletRequest request)
  {
    String resultedDropDowns = null;
    String orderedDropDowns = null;
    /**Commented out on Feb 11
    String specimenSourceDropDowns = null;
    String specimenSiteDropDowns = null;
    */
   if(programAreaCode== null)
    {
      programAreaCode = NEDSSConstants.PROGRAM_AREA_NONE;
    }
    String labId =  null;
    if(reportingUID.equalsIgnoreCase(NEDSSConstants.DEFAULT))
    {
      labId =NEDSSConstants.DEFAULT;
    }
    else
      labId = NedssCodeLookupServlet.getCliaValue(reportingUID, request.getSession());
      if(labId== null || (labId!=null && labId.trim().equalsIgnoreCase("")))
        labId = NEDSSConstants.DEFAULT;
    if(type.equalsIgnoreCase( NEDSSConstants.ORDERED_TEST_LOOKUP +NEDSSConstants.RESULTED_TEST_LOOKUP)){
      resultedDropDowns = NedssCodeLookupServlet.getDropDownValues(labId, NEDSSConstants.RESULTED_TEST_LOOKUP, conditionCodeCode,   programAreaCode, orderedTestCode,  resultedTestCode, request);
      orderedDropDowns = NedssCodeLookupServlet.getDropDownValues(labId, NEDSSConstants.ORDERED_TEST_LOOKUP, conditionCodeCode,   programAreaCode, orderedTestCode,  resultedTestCode, request);
    }
    else if(type.equalsIgnoreCase( NEDSSConstants.ORDERED_TEST_LOOKUP)){
       orderedDropDowns = NedssCodeLookupServlet.getDropDownValues(labId, NEDSSConstants.ORDERED_TEST_LOOKUP, conditionCodeCode,   programAreaCode, orderedTestCode,  resultedTestCode, request);
    }
    else if(type.equalsIgnoreCase( NEDSSConstants.RESULTED_TEST_LOOKUP)){
      resultedDropDowns = NedssCodeLookupServlet.getDropDownValues(labId, NEDSSConstants.RESULTED_TEST_LOOKUP,  conditionCodeCode,  programAreaCode, orderedTestCode,  resultedTestCode, request);
    }
    else
      logger.error("The type of dropdown list desired is invalid");

    request.setAttribute("OrderedTestNameCollection", orderedDropDowns);
    request.setAttribute("OrderedTestNameTest", "true");
    request.setAttribute("ResultedTestNameCollection", resultedDropDowns);
  }

   public Set<Object> getObjectListFromProxy(String labId, String progAreaCd,
                                   String sTestType, HttpSession session) {
   Set<Object> aSet = null;
   SRTCacheManager srtManager = null;
   try{
     NedssUtils nedssUtils = new NedssUtils();
     Object objref = nedssUtils.lookupBean(JNDINames.SRT_CACHEMANAGER_EJB);
     SRTCacheManagerHome home = (SRTCacheManagerHome) PortableRemoteObject.
         narrow(objref, SRTCacheManagerHome.class);
     srtManager = home.create();

     if(sTestType.equals(NEDSSConstants.ORDERED_TEST_LOOKUP))
     {
       if((progAreaCd== null) || (progAreaCd.equals("")))
       {
         aSet = (TreeSet<Object>)srtManager.getOrderedTestNames(labId);
       }
       else if((progAreaCd!= null) || (progAreaCd!= ""))
       {
         aSet = (TreeSet<Object>)srtManager.getOrderedTestNames(labId, progAreaCd);
       }
       else
       {
         logger.error("This Situation should not happen! Both prgArea and LabId are null");
       }
     }
     if(sTestType.equals(NEDSSConstants.RESULTED_TEST_LOOKUP))
     {
       if((progAreaCd== null) || (progAreaCd== ""))
       {
         aSet = (TreeSet<Object>)srtManager.getResultedTestNames(labId);
       }
       else if((progAreaCd!= null))
       {
         aSet = (TreeSet<Object>)srtManager.getResultedTestNames(labId, progAreaCd);
       }
       else
       {
         logger.error("This Situation should not happen! Both prgArea and LabId are null");
       }
     }

   }
   catch (CreateException e) {
    logger.error(e);
   }
   catch (RemoteException e) {
     logger.error(e);
   }
   return aSet;
 }

    protected void convertLAB214RequestObject(LabResultProxyVO labResultProxyVO, HttpServletRequest request)
    {

        if (labResultProxyVO.getTheObservationVOCollection() != null)
        {
            Iterator<ObservationVO> it = labResultProxyVO.getTheObservationVOCollection().iterator();

            while (it.hasNext())
            {
                ObservationVO lab214VO = (ObservationVO) it.next();
                ObservationDT lab214 = lab214VO.getTheObservationDT();
                if (lab214.getCd() != null && lab214.getCd().equals("LAB214"))
                    if (lab214VO.getObsValueTxtDT_s(0).getValueTxt() != null)
                    {
                        request.setAttribute("LAB214", lab214VO.getObsValueTxtDT_s(0).getValueTxt());
                    }
                    else
                    {
                        request.setAttribute("LAB214", "");
                    }

            }
        }
    }

    public void getNBSSecurityJurisdictionsPA(HttpServletRequest request, NBSSecurityObj nbsSecurityObj,
            String contextAction)
    {

        String programAreaJurisdictions = nbsSecurityObj.getProgramAreaJurisdictions(NBSBOLookup.OBSERVATIONLABREPORT,
                NBSOperationLookup.ADD);

        StringBuffer stringBuffer = new StringBuffer();
        if (programAreaJurisdictions != null && programAreaJurisdictions.length() > 0)
        { // "PA$J|PA$J|PA$J|"
          // change the navigation depending on programArea
            logger.info("programAreaJurisdictions: " + programAreaJurisdictions);
            StringTokenizer st = new StringTokenizer(programAreaJurisdictions, "|");
            while (st.hasMoreTokens())
            {
                String token = st.nextToken();
                if (token.lastIndexOf("$") >= 0)
                {
                    String programArea = token.substring(0, token.lastIndexOf("$"));
                    String juris = token.substring(token.lastIndexOf("$") + 1);
                    stringBuffer.append(juris).append("|");
                }
            }
            request.setAttribute("NBSSecurityJurisdictions", stringBuffer.toString());
        }

        TreeMap<Object, Object> treeMap = nbsSecurityObj.getProgramAreas(NBSBOLookup.OBSERVATIONLABREPORT,
                NBSOperationLookup.ADD);
        // logger.debug("treeMap: " + treeMap);
        StringBuffer sb = new StringBuffer();
        if (treeMap != null)
        {
            Set<Object> s = new TreeSet<Object>(treeMap.values());
            if (contextAction.equalsIgnoreCase("AddLabDataEntry")
                    || contextAction.equalsIgnoreCase("SubmitAndLoadLabDE1"))
            {
                sb.append("NONE").append(NEDSSConstants.SRT_PART);
                sb.append("Unknown").append(NEDSSConstants.SRT_LINE);
            }
            Iterator<Object> it = s.iterator();
            while (it.hasNext())
            {
                String sortedValue = (String) it.next();
                Iterator<?> anIterator = treeMap.entrySet().iterator();

                while (anIterator.hasNext())
                {
                    Map.Entry map = (Map.Entry) anIterator.next();
                    if ((String) map.getValue() == sortedValue)
                    {
                        String key = (String) map.getKey();
                        String value = (String) map.getValue();
                        sb.append(key.trim()).append(NEDSSConstants.SRT_PART);
                        sb.append(value.trim()).append(NEDSSConstants.SRT_LINE);
                        logger.info(key + " : " + value);

                    }
                }
            }
        }
        request.setAttribute("FilteredPrograAreas", sb.toString());

    } // getJurisdictionsPA


 public int setPatientLabMorbForCreate(Long personSubjectUid,Collection<Object>  personsColl,
                                 PersonVO personVO,
                                 HttpServletRequest request,int tempID){
   String contextAction = request.getParameter("ContextAction");


   if (personVO != null)
   {
     String generalAsOfDate = request.getParameter("generalAsOfDate");
     //this is for lefnav Lab or Morb and without entity search for patient
     if((contextAction.equalsIgnoreCase("SubmitAndLoadLabDE1") || contextAction.equalsIgnoreCase("SubmitAndLoadMorbDE1")) && personSubjectUid == null){
       personVO.getThePersonDT().setPersonUid(new Long(tempID--));
       //personVO.getThePersonDT().setPersonParentUid(null);
       PersonDT pdt = personVO.getThePersonDT();
       personVO.setItNew(true);
       personVO.setItDirty(false);

       pdt.setItNew(true);
       pdt.setItDirty(false);
       pdt.setCd(NEDSSConstants.PAT);
       pdt.setElectronicInd(NEDSSConstants.ELECTRONIC_IND);
       pdt.setAddTime(new Timestamp(new Date().getTime()));
       pdt.setRecordStatusTime(new Timestamp(new Date().getTime()));
       pdt.setStatusTime(new Timestamp(new Date().getTime()));
       pdt.setStatusCd(NEDSSConstants.STATUS_ACTIVE);

       if(pdt.getMaritalStatusCd() != null && pdt.getMaritalStatusCd().trim().length() > 0)
       pdt.setAsOfDateGeneral_s(generalAsOfDate);

       if(pdt.getBirthTime() != null || (pdt.getCurrSexCd() != null && pdt.getCurrSexCd().trim().length() > 0)
          || (pdt.getAgeReported() != null && pdt.getAgeReported().trim().length() > 0)
          || (pdt.getAgeReportedUnitCd() != null && pdt.getAgeReportedUnitCd().trim().length() > 0)
          || pdt.getBirthTimeCalc() != null)
       pdt.setAsOfDateSex_s(generalAsOfDate);

       if(pdt.getDescription() != null && pdt.getDescription().trim().length()>0)
        pdt.setAsOfDateAdmin_s(generalAsOfDate);

       //if you have date of death, set the DeceasedIndCd to Y
       if(pdt.getDeceasedTime() != null)
       {
         pdt.setDeceasedIndCd("Y");
         pdt.setAsOfDateMorbidity_s(generalAsOfDate);
       }

       if(pdt.getEthnicGroupInd() != null && pdt.getEthnicGroupInd().trim().length()>0)
       pdt.setAsOfDateEthnicity_s(generalAsOfDate);

       PersonUtil.setNames(personVO, request);
       PersonUtil.setAddressesForCreate(personVO, request);
       PersonUtil.setRaceForCreate(personVO, request);
       PersonUtil.setIds(personVO, request);

       Collection<Object>  arrELP = personVO.getTheEntityLocatorParticipationDTCollection();

       if (arrELP == null) 
       {
          arrELP = new ArrayList<Object> ();

       }
       EntityLocatorParticipationDT elp = new EntityLocatorParticipationDT();
       TeleLocatorDT teleDTHome = new TeleLocatorDT();
       EntityLocatorParticipationDT elpWork = new EntityLocatorParticipationDT();
       TeleLocatorDT teleDTWork = new TeleLocatorDT();
       String phone = request.getParameter("homePhoneNbrTxt");

       logger.info("phone: " + phone );

       //Home Phone
        if (phone != null && !phone.equals("")) 
        {
          elp.setItNew(true);
          elp.setItDirty(false);
          elp.setEntityUid(personVO.getThePersonDT().getPersonUid());
          elp.setClassCd(NEDSSConstants.TELE);
          elp.setCd(NEDSSConstants.PHONE);
          elp.setUseCd(NEDSSConstants.HOME);
          elp.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
          elp.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
          elp.setAsOfDate_s(generalAsOfDate);
          teleDTHome.setPhoneNbrTxt(phone);
          teleDTHome.setItNew(true);
          teleDTHome.setItDirty(false);
          teleDTHome.setAddTime(new Timestamp(new Date().getTime()));
          teleDTHome.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
          elp.setTheTeleLocatorDT(teleDTHome);

          arrELP.add(elp);
       }
       personVO.setTheEntityLocatorParticipationDTCollection(arrELP);
       personsColl.add(personVO);
     }
     else{
       if(personVO.getThePersonDT().getMaritalStatusCd() != null && personVO.getThePersonDT().getMaritalStatusCd().trim().length() > 0)
        personVO.getThePersonDT().setAsOfDateGeneral_s(generalAsOfDate);
     //To store the AsofDate for Sex and Birth Information
     if(personVO.getThePersonDT().getBirthTime() != null || (personVO.getThePersonDT().getCurrSexCd() != null && personVO.getThePersonDT().getCurrSexCd().trim().length() > 0)
      || (personVO.getThePersonDT().getAgeReported() != null && personVO.getThePersonDT().getAgeReported().trim().length() > 0)
      || (personVO.getThePersonDT().getAgeReportedUnitCd() != null && personVO.getThePersonDT().getAgeReportedUnitCd().trim().length() > 0)
      || personVO.getThePersonDT().getBirthTimeCalc() != null)
       personVO.getThePersonDT().setAsOfDateSex_s(generalAsOfDate);
       personVO.getThePersonDT().setPersonParentUid(personSubjectUid);
       personVO.getThePersonDT().setPersonUid(new Long(tempID--));
       // If the patient Comment is not null
       if(personVO.getThePersonDT().getDescription() != null && personVO.getThePersonDT().getDescription().trim().length()>0)
        personVO.getThePersonDT().setAsOfDateAdmin_s(generalAsOfDate);

       //If ethnicGroupInd is not null, store the AsofDate
       if(personVO.getThePersonDT().getEthnicGroupInd() != null && personVO.getThePersonDT().getEthnicGroupInd().trim().length()>0)
       personVO.getThePersonDT().setAsOfDateEthnicity_s(generalAsOfDate);

       //if you have date of death, set the DeceasedIndCd to Y
        if(personVO.getThePersonDT().getDeceasedTime() != null)
       {
         personVO.getThePersonDT().setDeceasedIndCd("Y");
         personVO.getThePersonDT().setAsOfDateMorbidity_s(generalAsOfDate);
       }

       personVO.getThePersonDT().setCd(NEDSSConstants.PAT);
       PersonUtil.setPatientForEventCreate(personVO, request);
       personVO.setItNew(true);
       personVO.getThePersonDT().setItNew(true);
       personsColl.add(personVO);
     }
   }
   return tempID;
  }

    private String getLabId(Long organizationUid, HttpSession session) throws java.rmi.RemoteException,
            javax.ejb.EJBException, Exception
    {

        MainSessionCommand msCommand = null;
        String systemDescTxt = null;
        // try {
        String sBeanJndiName = JNDINames.ENTITY_PROXY_EJB;
        String sMethod = null;
        Long obsUid = null;
        MainSessionHolder holder = new MainSessionHolder();
        ArrayList<?> resultUIDArr = null;

        sMethod = "organizationCLIALookup";
        Object[] oParams = { organizationUid };
        msCommand = holder.getMainSessionCommand(session);
        resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);

        for (int i = 0; i < resultUIDArr.size(); i++)
        {
            logger.info("Result " + i + " is: " + resultUIDArr.get(i));
        }

        if ((resultUIDArr != null) && (resultUIDArr.size() > 0))
        {

            systemDescTxt = (String) resultUIDArr.get(0);

            return systemDescTxt;
        }

        return null;
    } // sendProxyToEJB

    public static String getCliaValue(String uid, HttpSession session)
    {
        Long orgUIDLong = new Long(uid);
        MainSessionCommand msCommand = null;
        String sBeanJndiName = JNDINames.ENTITY_PROXY_EJB;
        String sMethod = "organizationCLIALookup";
        Object[] oParams = new Object[] { orgUIDLong };

        String CliaNumber = null;
        try
        {
            MainSessionHolder holder = new MainSessionHolder();
            msCommand = holder.getMainSessionCommand(session);
            ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
            CliaNumber = (String) arr.get(0);
        }
        catch (Exception ex)
        {
            logger.error("There was some error in getting clia number from the database");
        }
        return CliaNumber;

    }

    private String getStateDescTxt(String sStateCd)
    {

        String desc = "";
        if (sStateCd != null && !sStateCd.trim().equals(""))
        {
            CachedDropDownValues srtValues = new CachedDropDownValues();
            TreeMap<Object, Object> treemap = srtValues.getStateCodes2("USA");
            if (treemap != null)
            {
                if (sStateCd != null && treemap.get(sStateCd) != null)
                {
                    desc = (String) treemap.get(sStateCd);
                }
            }
        }

        return desc;
    }

    private String getCountiesByState(String stateCd, String countyCd)
    {

        String parsedCodes = "";
        if (stateCd != null)
        {
            // SRTValues srtValues = new SRTValues();
            CachedDropDownValues srtValues = new CachedDropDownValues();
            TreeMap<?, ?> treemap = null;
            treemap = srtValues.getCountyCodes(stateCd);
            if (treemap != null)
            {
                /*
                 * Set set = treemap.keySet(); Iterator<Object> itr =
                 * set.iterator(); while(itr.hasNext()) { String key =
                 * (String)itr.next(); String value = (String)treemap.get(key);
                 * parsedCodes
                 * .append(key.trim()).append(NEDSSConstants.SRT_PART)
                 * .append(value.trim()).append(NEDSSConstants.SRT_LINE); }
                 */
                parsedCodes = (String) treemap.get(countyCd);
            }
        }

        if (parsedCodes != null)
            return parsedCodes.toString();
        else
            return "";
    }
    
    private String getCountiesByDesc(String countyCd)
    {

        String cntyDesc = "";
        if (countyCd != null)
        {
            CachedDropDownValues srtValues = new CachedDropDownValues();
            cntyDesc = srtValues.getCntyDescTxt(countyCd);
        }

        return cntyDesc;
    }

   protected void convertProxyToRequestForEdit(LabResultProxyVO labResultProxyVO,
                                      HttpServletRequest request, NBSSecurityObj secObj) {
       Long reportingUID = null;
       String programAreaCode = null;
       ObservationVO lab112VO = null;
       if(labResultProxyVO.getTheObservationVOCollection()!=null)
       {
        Iterator<ObservationVO>  labIt = labResultProxyVO.getTheObservationVOCollection().
             iterator();
      //     Long observationUid = (Long) request.getSession().getAttribute("DSObservationUID");
           HttpSession session = request.getSession();

           Long observationUid = null;
           Object objObsUID = NBSContext.retrieve(session,"DSObservationUID");
           if (objObsUID instanceof String){
           observationUid = new Long((String)objObsUID);
           }
           else if (objObsUID instanceof Long){
             observationUid = (Long) objObsUID;
           }
         while (labIt.hasNext()) {
           ObservationVO obsVO = (ObservationVO) labIt.next();
           if (obsVO.getTheObservationDT().getObservationUid().compareTo(observationUid)== 0) {
             lab112VO = obsVO;
             programAreaCode = lab112VO.getTheObservationDT().getProgAreaCd();
           }
         }
       }
       PersonVO pvo = getPatientVO(labResultProxyVO, lab112VO);
       pvo = this.findMasterPatientRecord(pvo.getThePersonDT().getPersonUid(),
                                              request.getSession(), secObj);
         String contextAction = request.getParameter("ContextAction");

         if (contextAction != null && contextAction.startsWith("ObservationLabIDOnEvents")) {
           convertPersonToRequest(pvo, request, secObj);
         }



       if(lab112VO.getTheParticipationDTCollection()!= null)
         reportingUID =  convertParticipationsToRequestForEdit(labResultProxyVO, lab112VO, request);

       //To populate the dropDowns of resulted Test and Ordered Tests
       if(reportingUID != null)
         getDropDownLists(reportingUID.toString(),
                          NEDSSConstants.ORDERED_TEST_LOOKUP +NEDSSConstants.RESULTED_TEST_LOOKUP, null,
                          programAreaCode, null,null, request);


   }
   protected Long convertParticipationsToRequestForEdit(LabResultProxyVO labResultProxyVO,ObservationVO lab112VO,
                                      HttpServletRequest request)
   {
       Long materialUid = null;
       Long orderFacilityUid = null;
       Long reportingLabUid = null;
       Long providerUid = null;
      Iterator<Object>  participationIt = lab112VO.getTheParticipationDTCollection().iterator();
       while(participationIt.hasNext())
       {
         ParticipationDT pdt = (ParticipationDT)participationIt.next();
         if(pdt.getActClassCd().equalsIgnoreCase(NEDSSConstants.CLASS_CD_OBS) && pdt.getRecordStatusCd().equalsIgnoreCase("ACTIVE") && pdt.getStatusCd().equalsIgnoreCase("A")
            && pdt.getSubjectClassCd().equalsIgnoreCase("MAT") && pdt.getTypeCd().equalsIgnoreCase("SPC") && pdt.getTypeDescTxt().equalsIgnoreCase( "Specimen"))
         {
           materialUid = pdt.getSubjectEntityUid();
           if(labResultProxyVO.getTheMaterialVOCollection()!= null)
             convertMaterialToRequest( labResultProxyVO.getTheMaterialVOCollection(), lab112VO, materialUid, request);
         }

         if(pdt.getActClassCd().equalsIgnoreCase(NEDSSConstants.CLASS_CD_OBS) && pdt.getRecordStatusCd().equalsIgnoreCase("ACTIVE") && pdt.getStatusCd().equalsIgnoreCase("A")
            && pdt.getSubjectClassCd().equalsIgnoreCase(NEDSSConstants.ORGANIZATION) && pdt.getTypeCd().equalsIgnoreCase("AUT") && pdt.getTypeDescTxt().equalsIgnoreCase("Author"))
         {
           reportingLabUid = pdt.getSubjectEntityUid();
         }
         if(pdt.getActClassCd().equalsIgnoreCase(NEDSSConstants.CLASS_CD_OBS) && pdt.getRecordStatusCd().equalsIgnoreCase("ACTIVE") && pdt.getStatusCd().equalsIgnoreCase("A")
            && pdt.getSubjectClassCd().equalsIgnoreCase(NEDSSConstants.ORGANIZATION) && pdt.getTypeCd().equalsIgnoreCase("ORD") && pdt.getTypeDescTxt().equalsIgnoreCase("Orderer"))
         {
           orderFacilityUid = pdt.getSubjectEntityUid();
         }

         if(pdt.getActClassCd().equalsIgnoreCase(NEDSSConstants.CLASS_CD_OBS) && pdt.getRecordStatusCd().equalsIgnoreCase("ACTIVE") && pdt.getStatusCd().equalsIgnoreCase("A")
            && pdt.getSubjectClassCd().equalsIgnoreCase(NEDSSConstants.PERSON) && pdt.getTypeCd().equalsIgnoreCase("ORD") && pdt.getTypeDescTxt().equalsIgnoreCase("Orderer"))
         {
           providerUid = pdt.getSubjectEntityUid();
         }


       }
       if (labResultProxyVO.getTheOrganizationVOCollection() != null) {
        Iterator<Object>  it = labResultProxyVO.getTheOrganizationVOCollection().iterator();
         while (it.hasNext()) {
           OrganizationVO orgVO = (OrganizationVO) it.next();
           if (orgVO.getTheOrganizationDT().getOrganizationUid().compareTo(
               reportingLabUid) == 0) {
             request.setAttribute("reportingSourceDetails",
                                  convertOrganizationToRequest(orgVO));
             request.setAttribute("reportingSourceDetail",
                                  convertOrganizationToRequest(orgVO));
             request.setAttribute("reportingFacilityUID", reportingLabUid);
             try
            {
              String labId = this.getLabId(reportingLabUid, request.getSession());
              if(labId == null || labId.equals(""))
                 labId= "DEFAULT";

              request.setAttribute("labId", labId);
            }catch(Exception e){

            }

           }
           if (orderFacilityUid!= null && orgVO.getTheOrganizationDT().getOrganizationUid().compareTo(
               orderFacilityUid) == 0) {
             request.setAttribute("orderingOrgDetails",
                                  convertOrganizationToRequest(orgVO));
             request.setAttribute("orderingOrgDetailsUID", orderFacilityUid);
           }
           else
           request.setAttribute("orderingOrgDetails",
                     "There is no Ordering Facility selected.");

         }
       }
       if (labResultProxyVO.getThePersonVOCollection() != null && providerUid!= null) {

        Iterator<Object>  it = labResultProxyVO.getThePersonVOCollection().iterator();
         while (it.hasNext()) {
           PersonVO personVO = (PersonVO) it.next();
           if (personVO.getThePersonDT().getPersonUid().compareTo(providerUid) ==
               0) {
             request.setAttribute("providerDetails",
                                  convertPersonToRequest(personVO));
             request.setAttribute("providerUID", providerUid);
           }

         }
       }
       if(providerUid == null)
       {
         request.setAttribute("providerDetails",
                                 "There is no Ordering Provider selected.");
       }

       return reportingLabUid;
     }

    public String convertOrganizationToRequest(OrganizationVO orgVO)
    {
        String displayString = helper.makeORGDisplayString(orgVO);
        return displayString;
    }

    public String convertPersonToRequest(PersonVO pvo)
    {
        String displayString = helper.makePRVDisplayString(pvo);
        return displayString;

    }

  protected void convertOtherInformationToRequest(LabResultProxyVO labResultProxyVO, ObservationVO obs112VO,PersonVO patientVO, HttpServletRequest request)
  {

    request.setAttribute("clinicalInformation",(obs112VO.getTheObservationDT().getTxt() ==null?"": obs112VO.getTheObservationDT().getTxt()));
    request.setAttribute("priorityCode",(obs112VO.getTheObservationDT().getPriorityCd() ==null?"": obs112VO.getTheObservationDT().getPriorityCd()));
    //request.setAttribute("orderComments",(obs112VO.getTheObservationDT().getTxt() ==null?"": obs112VO.getTheObservationDT().getTxt()));
    //Order Comments (LAB266) should not point to any value.  Leave blank for R1.1.4. Defect-civil00012496
    request.setAttribute("orderComments","");
    StringBuffer obsReasonBuff= new StringBuffer("");
    Collection<Object>  obsreasonColl = obs112VO.getTheObservationReasonDTCollection();
    if(obsreasonColl!= null)
    {
     Iterator<Object>  it = obsreasonColl.iterator();
      while(it.hasNext())
      {
        ObservationReasonDT obsReasonDT = (ObservationReasonDT)it.next();
        obsReasonBuff.append("[");
        obsReasonBuff.append(obsReasonDT.getReasonDescTxt()== null? "N/A":obsReasonDT.getReasonDescTxt());
        obsReasonBuff.append(obsReasonDT.getReasonCd()== null? "(N/A)": "  ("+obsReasonDT.getReasonCd()+")");
        obsReasonBuff.append("] ");
        
        request.setAttribute("obsReasons", obsReasonBuff);
      }
    }
    Collection<Object>  actIdDtColl = obs112VO.getTheActIdDTCollection();
    if(actIdDtColl!= null)
    {
     Iterator<Object>  it = actIdDtColl.iterator();
      while(it.hasNext())
      {
        ActIdDT actDT = (ActIdDT)it.next();
        if(!(actDT.getTypeCd().equalsIgnoreCase("FN")))
        {
          request.setAttribute("messageControlID", actDT.getRootExtensionTxt());
          break;
        }
      }
    }

    Collection<Object>  PersonColl = labResultProxyVO.getThePersonVOCollection();
    StringBuffer particBuff = new StringBuffer();
    TreeMap<Object,Object> personMap = new TreeMap<Object,Object>();
    if(PersonColl!= null)
    {
     Iterator<Object>  it  = PersonColl.iterator();
      while(it.hasNext())
      {
        PersonVO personVO = (PersonVO)it.next();
        //if(patientUID!= null && personVO.getThePersonDT().getPersonUid().compareTo(patientUID)==0)
        //{
          //patientVO = personVO;
        //}
        personMap.put(personVO.getThePersonDT().getPersonUid(), personVO);
      }
    }
    PersonVO procurerVO = convertOtherInformationMiscDetailsToRequest(labResultProxyVO, obs112VO, patientVO, personMap, request);
    convertOtherInformationMiscParticipantsToRequest(labResultProxyVO, obs112VO, patientVO, procurerVO, personMap, request);
  }


  protected PersonVO convertOtherInformationMiscDetailsToRequest(LabResultProxyVO labResultProxyVO, ObservationVO obs112VO,PersonVO patientVO, TreeMap<Object,Object> personMap, HttpServletRequest request)
  {
    StringBuffer birthPlaceBuff = new StringBuffer("");
    Long patientUID = null;
    PersonVO patientContactVO = null;
    Long patientContactUID = null;
    PersonVO personProcurerVO = null;
    Long personProcurerUID = null;
    Collection<Object>  copyToProviderUIDs = new ArrayList<Object> ();
    Collection<Object>  copyToProviderVOs = new ArrayList<Object> ();


    /*to get The patientUID */
          patientUID = patientVO.getThePersonDT().getPersonUid();

    //To get The patient and related roles
    if(labResultProxyVO.getTheRoleDTCollection()!= null)
    {
     Iterator<Object>  roleIt = labResultProxyVO.getTheRoleDTCollection().iterator();
      while(roleIt.hasNext())
      {
        RoleDT roleDT = (RoleDT)roleIt.next();
        if(roleDT.getSubjectClassCd().equalsIgnoreCase("CON"))
        {
            patientContactUID = roleDT.getSubjectEntityUid();
            if(roleDT.getCd()== null || roleDT.getCd()=="")
            {
              request.setAttribute("alternateContactRelationship", "N/A");
            }
            else
            {
              request.setAttribute("alternateContactRelationship", roleDT.getCdDescTxt());
             }
         }
         if(roleDT.getCd().equalsIgnoreCase("SPP") && roleDT.getSubjectClassCd().equalsIgnoreCase("PSN"))
         {
            personProcurerUID = roleDT.getSubjectEntityUid();
         }
         if(roleDT.getCd().equalsIgnoreCase("CT") && roleDT.getScopingEntityUid()!=null && roleDT.getScopingEntityUid().compareTo(patientVO.getThePersonDT().getPersonUid())==0)
         {
           copyToProviderUIDs.add(roleDT.getSubjectEntityUid());
          }
        }
      }
      Collection<Object>  entityColl = patientVO.getTheEntityLocatorParticipationDTCollection();
      if(entityColl!= null)
      {
       Iterator<Object>  entityIt = entityColl.iterator();
        while (entityIt.hasNext()) {
          EntityLocatorParticipationDT eLPDT = (EntityLocatorParticipationDT)
              entityIt.next();
          if (eLPDT.getClassCd().equalsIgnoreCase("PST") &&
              eLPDT.getUseCd().equalsIgnoreCase("BIR") &&
              eLPDT.getCd().equalsIgnoreCase("F")) {
            PostalLocatorDT postalDT = eLPDT.getThePostalLocatorDT();
            birthPlaceBuff.append(postalDT.getCityDescTxt() == null ? "" :
                                  postalDT.getCityDescTxt());
            if (postalDT.getStateCd() != null) {
              CachedDropDownValues cdv = new CachedDropDownValues();
              try {
                birthPlaceBuff.append(cdv.
                                      getCachedStateCodeList().
                                      get(postalDT.getStateCd()).toString());
              }
              catch (Exception ex) {
              }
            }
            birthPlaceBuff.append(postalDT.getCntyCd() == null ? "" :
                                  postalDT.getCntyCd());
            birthPlaceBuff.append(postalDT.getCntryCd() == null ? "" :
                                  postalDT.getCntryCd());
            request.setAttribute("PatientBirthPlace", birthPlaceBuff.toString());
            birthPlaceBuff.setLength(0); //Added after MPR LAB Update

          }
        }
      }

              StringBuffer multipleBirthInd = new StringBuffer("");
              PersonDT personDT = patientVO.getThePersonDT();
              multipleBirthInd.append(personDT.getMultipleBirthInd() ==null? "":cdv.getDescForCode("YNU",personDT.getMultipleBirthInd()));
              multipleBirthInd.append(personDT.getBirthOrderNbr()==null ? "":"/"+personDT.getBirthOrderNbr().toString());
              request.setAttribute("multipleBirth",multipleBirthInd.toString());


    //while(personIt

    Collection<Object>  personVOColl = labResultProxyVO.getThePersonVOCollection();
    if(personVOColl!= null)
    {
     Iterator<Object>  personList = personVOColl.iterator();
      while(personList.hasNext())
      {
        PersonVO personVO = (PersonVO)personList.next();
        if(patientContactUID!= null && personVO.getThePersonDT().getPersonUid().compareTo(patientContactUID)==0)
        {
          patientContactVO = personVO;
           StringBuffer alternateContactBuff = new StringBuffer();
           StringBuffer otherContactPhoneDetails = new StringBuffer();
           Collection<Object>  personNameDTColl = patientContactVO.getThePersonNameDTCollection();
          if(personNameDTColl!= null)
          {
           Iterator<Object>  personNameIt = personNameDTColl.iterator();
            while (personNameIt.hasNext())
            {
              PersonNameDT personNameDT = (PersonNameDT)personNameIt.next();
              if(personNameDT.getNmUseCd().equalsIgnoreCase(NEDSSConstants.LEGAL))
              {
                alternateContactBuff.append(personNameDT.getNmPrefix()==null? "":personNameDT.getNmPrefix());
                alternateContactBuff.append(personNameDT.getFirstNm()==null? "":" "+personNameDT.getFirstNm());
                alternateContactBuff.append(personNameDT.getMiddleNm()==null? "":" "+personNameDT.getMiddleNm());
                alternateContactBuff.append(personNameDT.getLastNm()==null? "":" "+personNameDT.getLastNm());
                alternateContactBuff.append(personNameDT.getNmSuffix()==null? "": ", "+personNameDT.getNmSuffix());
              }
            }
          }
          Collection<Object>  entityLocatorsColl = patientContactVO.getTheEntityLocatorParticipationDTCollection();
          if(entityLocatorsColl!= null)
          {
           Iterator<Object>  entityLocatorIt = entityLocatorsColl.iterator();
            while(entityLocatorIt.hasNext())
            {
              EntityLocatorParticipationDT  entityDT = (EntityLocatorParticipationDT)entityLocatorIt.next();
              if(entityDT.getClassCd().equalsIgnoreCase("PST") && entityDT.getUseCd().equalsIgnoreCase("H"))
              {
                PostalLocatorDT postalDT = entityDT.getThePostalLocatorDT();
                if(postalDT!= null)
                {
                  alternateContactBuff.append(postalDT.getStreetAddr1()==null? "":"<br/>"+ postalDT.getStreetAddr1());
                  alternateContactBuff.append(postalDT.getStreetAddr2()==null? "":"<br/>"+ postalDT.getStreetAddr2());
                  alternateContactBuff.append("<br/>");
                  //alternateContactBuff.append(postalDT.getCityCd()==null? "":"<br/>"+ postalDT.getCityCd());
                  alternateContactBuff.append(postalDT.getCityDescTxt()==null? "":postalDT.getCityDescTxt()+", ");
                  if( postalDT.getStateCd() != null)
                  {
                    CachedDropDownValues cdv = new CachedDropDownValues();
                    try {
                      alternateContactBuff.append(cdv.getCachedStateCodeList().get(postalDT.getStateCd()).toString());
                    }
                    catch (Exception ex) {
                    }
                  }
                  alternateContactBuff.append(postalDT.getStateCd()==null? "":" "+ postalDT.getStateCd());
                  alternateContactBuff.append(postalDT.getZipCd()==null? "":" "+ postalDT.getZipCd());
                  if(postalDT.getCntyDescTxt()!=null)
                	  alternateContactBuff.append(postalDT.getCntyDescTxt()==null? "":" "+ postalDT.getCntyDescTxt());
                  else if(postalDT.getStateCd()!=null && postalDT.getCntyCd()!=null)
                	  alternateContactBuff.append(getCountiesByState(postalDT.getStateCd(), postalDT.getCntyCd()));
                  
                  String homeCountryDesc = "";
                  if(cdv.getCountryCodesAsTreeMap()!=null && postalDT.getCntryCd()!=null)
                  {
                	  homeCountryDesc = (String)cdv.getCountryCodesAsTreeMap().get(postalDT.getCntryCd());
                      alternateContactBuff.append(homeCountryDesc);
                  }
                  else
                	  alternateContactBuff.append(postalDT.getCntryDescTxt()==null? "":" "+ postalDT.getCntryCd());
                }
              }
              if(entityDT.getClassCd().equalsIgnoreCase("TELE") && entityDT.getUseCd().equalsIgnoreCase("H"))
              {
                TeleLocatorDT teleDT = entityDT.getTheTeleLocatorDT();
                if(teleDT!= null)
                {
                  otherContactPhoneDetails.append(teleDT.getPhoneNbrTxt()==null?"":""+ teleDT.getPhoneNbrTxt());
                  otherContactPhoneDetails.append((teleDT.getExtensionTxt()==null && teleDT.getExtensionTxt()==null)?"":"<b> Ext.<b>");
                  otherContactPhoneDetails.append(teleDT.getExtensionTxt()==null?"":" "+ teleDT.getExtensionTxt());
                }
              }
            }
          }
          request.setAttribute("alternateContactDetails", alternateContactBuff);
          request.setAttribute("otherContactPhoneDetails", otherContactPhoneDetails);

        }
        if(personProcurerUID!= null && personVO.getThePersonDT().getPersonUid().compareTo(personProcurerUID)==0)
        {
          personProcurerVO = personVO;
         // request.setAttribute("personProcurerVO", personProcurerVO);
        }


      }
    }

  if(copyToProviderUIDs!= null && copyToProviderUIDs.size()>0)
     {
      Iterator<Object>  copyProviderList = copyToProviderUIDs.iterator();
       while(copyProviderList.hasNext())
       {
         Long personUid = (Long)copyProviderList.next();
         if(personMap!= null)
         {
           if(personMap.get(personUid)!= null)
           copyToProviderVOs.add((PersonVO)personMap.get(personUid));
         }
       }
       if(copyToProviderVOs.size()>0)
       convertCopyToProviders(copyToProviderVOs, request);
     }

  return personProcurerVO;
  }



  protected void convertOtherInformationMiscParticipantsToRequest(LabResultProxyVO
                                                  labResultProxyVO, ObservationVO lab112VO, PersonVO patientVO, PersonVO procurerVO,
                                                  TreeMap<Object,Object> personMap, HttpServletRequest request) {

     Long patientUID = null;
     Long astResultIntrepUID = null;
     Long resultIntrepUID = null;
     Long transciptionistUID = null;
     Long labTechnicianUID = null;
     Collection<Object>  participationColl = lab112VO.getTheParticipationDTCollection();
     if(participationColl!= null)
     {
      Iterator<Object>  it = participationColl.iterator();
       while(it.hasNext())
       {
         ParticipationDT partDT = (ParticipationDT)it.next();
         if(partDT.getTypeCd().equalsIgnoreCase("PATSBJ") && partDT.getTypeCd().equalsIgnoreCase("PSN"))
         {
           patientUID = partDT.getSubjectEntityUid();
         }

         if(partDT.getTypeCd().equalsIgnoreCase("VRF") && partDT.getTypeDescTxt().equalsIgnoreCase("Verifier"))
         {
           resultIntrepUID = partDT.getSubjectEntityUid();
         }

         if(partDT.getTypeCd().equalsIgnoreCase("ASS") && partDT.getTypeDescTxt().equalsIgnoreCase("Assistant"))
         {
           astResultIntrepUID = partDT.getSubjectEntityUid();
         }
         if(partDT.getTypeCd().equalsIgnoreCase("PRF") && partDT.getTypeDescTxt().equalsIgnoreCase("Performer"))
         {
           labTechnicianUID = partDT.getSubjectEntityUid();
         }
         if(partDT.getTypeCd().equalsIgnoreCase("ENT") && partDT.getTypeDescTxt().equalsIgnoreCase("Enterer"))
         {
           transciptionistUID = partDT.getSubjectEntityUid();
         }
       }
     }
    // ArrayList<Object> participantsColl = new ArrayList<Object> ();
     //Collection<Object>  PersonColl = labResultProxyVO.getThePersonVOCollection();
     StringBuffer particBuff = new StringBuffer();
     PersonVO personVO = null;
     if(resultIntrepUID!= null && personMap.get(resultIntrepUID)!=null)
     {
       personVO = (PersonVO)personMap.get(resultIntrepUID);
       particBuff.append(convertRolesTorequestWithPersonVO( personVO)).append("$");
       particBuff.append(convertParticipantsTorequest(personVO)).append("$").append("|");
      // participantsColl.add(personVO);
     }

     if(astResultIntrepUID!= null && personMap.get(astResultIntrepUID)!=null)
     {
       personVO = (PersonVO)personMap.get(astResultIntrepUID);
       particBuff.append(convertRolesTorequestWithPersonVO( personVO)).append("$");
       particBuff.append(convertParticipantsTorequest(personVO)).append("$").append("|");
      // participantsColl.add(personVO);
     }
     if(labTechnicianUID!= null && personMap.get(labTechnicianUID)!=null)
     {
       personVO = (PersonVO)personMap.get(labTechnicianUID);
       particBuff.append(convertRolesTorequestWithPersonVO( personVO)).append("$");
       particBuff.append(convertParticipantsTorequest(personVO)).append("$").append("|");
       // participantsColl.add(personVO);
     }
     if(transciptionistUID!= null && personMap.get(transciptionistUID)!=null)
     {
       personVO = (PersonVO)personMap.get(transciptionistUID);
       particBuff.append(convertRolesTorequestWithPersonVO( personVO)).append("$");
       particBuff.append(convertParticipantsTorequest(personVO)).append("$").append("|");
       //participantsColl.add(personVO);
     }
    if((procurerVO!= null && personVO== null)  || (procurerVO!= null && personVO!= null && personVO.getThePersonDT().getPersonUid().compareTo(procurerVO.getThePersonDT().getPersonUid())!=0))
     {
       particBuff.append(convertRolesTorequestWithPersonVO( procurerVO)).append("$");
       particBuff.append("$").append(convertParticipantsTorequest(procurerVO));
       // participantsColl.add(procurerVO);
     }
   else if(procurerVO!= null && personVO.getThePersonDT().getPersonUid().compareTo(procurerVO.getThePersonDT().getPersonUid())==0)
     {
       particBuff.append(convertRolesTorequestWithPersonVO( personVO)).append("$");
       particBuff.append("$").append(convertParticipantsTorequest(personVO));
       // participantsColl.add(personVO);
     }


     request.setAttribute("participantsColl", particBuff);

  }
  
  protected void convertOtherInformationRecngOrgToRequest(LabResultProxyVO
                                                  labResultProxyVO, Long receivingOrgUID,
                                                  HttpServletRequest request) {
    Collection<Object>  orgColl = labResultProxyVO.getTheOrganizationVOCollection();
    if (orgColl != null) {
     Iterator<Object>  it = orgColl.iterator();
      while(it.hasNext())
      {
        StringBuffer orgBuff = new StringBuffer("");
        OrganizationVO orgVO = (OrganizationVO) it.next();
        if (orgVO.getTheOrganizationDT().getOrganizationUid().compareTo(receivingOrgUID) == 0) {
          OrganizationDT orgDT = orgVO.getTheOrganizationDT();
          if (orgVO.getTheOrganizationNameDTCollection() != null) {
           Iterator<Object>  orgNameIt = orgVO.getTheOrganizationNameDTCollection().
                iterator();
            while (orgNameIt.hasNext()) {
              OrganizationNameDT orgNmDT = (OrganizationNameDT) orgNameIt.next();
              if (orgNmDT.getNmUseCd().equals("L")) {
                orgBuff.append( (orgNmDT.getNmTxt() == null) ? "" :
                               (orgNmDT.getNmTxt()));
              }

											if (orgVO.getTheOrganizationDT().
                             getStandardIndustryDescTxt() != null && orgVO.getTheOrganizationDT().getStandardIndustryClassCd()== null )
							{
								 orgBuff.append(orgVO.getTheOrganizationDT().
                             getStandardIndustryDescTxt() == null ? "" : "(" +
                             orgVO.getTheOrganizationDT().
                             getStandardIndustryDescTxt()+")") ;
            	}
						  if (orgVO.getTheOrganizationDT().
                             getStandardIndustryDescTxt() == null && orgVO.getTheOrganizationDT().getStandardIndustryClassCd()!= null )
							{
               orgBuff.append(orgVO.getTheOrganizationDT().
                             getStandardIndustryClassCd() == null ? "" :
                             orgVO.getTheOrganizationDT().
                             getStandardIndustryClassCd()+ ")");

              }
							if (orgVO.getTheOrganizationDT().
                             getStandardIndustryDescTxt() != null && orgVO.getTheOrganizationDT().getStandardIndustryClassCd()!= null )
							{
		                orgBuff.append(orgVO.getTheOrganizationDT().
                             getStandardIndustryDescTxt() == null ? "(" : "(" +
                             orgVO.getTheOrganizationDT().
                             getStandardIndustryDescTxt()+" ") ;

										 orgBuff.append(orgVO.getTheOrganizationDT().
                             getStandardIndustryClassCd() == null ? ")" :
                             orgVO.getTheOrganizationDT().
                             getStandardIndustryClassCd()+ ")");

							}

           }
          }
            if (orgVO.getTheEntityIdDTCollection() != null) {
             Iterator<Object>  itEntity = orgVO.getTheEntityIdDTCollection().iterator();
               while (itEntity.hasNext()) {
                EntityIdDT entityDT = (EntityIdDT) itEntity.next();
                if(entityDT!= null)
                {
                  if (entityDT.getTypeCd().equalsIgnoreCase("FI") ) {
                    orgBuff.append( (entityDT.getRootExtensionTxt() == null) ? "" : "<br/><b>ID: </b>"+(entityDT.getRootExtensionTxt()));
                    orgBuff.append( (entityDT.getTypeCd() == null) ? "" : "("+(entityDT.getTypeCd()));
                    orgBuff.append( (entityDT.getAssigningAuthorityCd() == null) ?"" :" "+ (entityDT.getAssigningAuthorityCd()));
                    orgBuff.append( (entityDT.getAssigningAuthorityDescTxt() == null) ? "" : " "+(entityDT.getAssigningAuthorityDescTxt())).append(")");
                  }
                }
              }
            }
            request.setAttribute("receivingFacilityDetails", orgBuff.toString());
         }
      }
    }
  }

    private String convertParticipantsTorequest(PersonVO personVO)
    {
        StringBuffer personDetails = new StringBuffer("");

        ArrayList<Object> pNamelist = (ArrayList<Object>) personVO.getThePersonNameDTCollection();
        Iterator<Object> pNameIt = pNamelist.iterator();
        while (pNameIt.hasNext())
        {
            PersonNameDT pNameDT = (PersonNameDT) pNameIt.next();
            if (pNameDT != null)
            {
                if (pNameDT.getNmUseCd().equalsIgnoreCase(NEDSSConstants.LEGAL_NAME))
                {

                    personDetails.append(pNameDT.getNmPrefix() == null ? "" : " " + pNameDT.getNmPrefix()); // NPP006

                    personDetails.append(pNameDT.getFirstNm() == null ? "" : " " + pNameDT.getFirstNm()); // NPP008
                    personDetails.append(pNameDT.getMiddleNm() == null ? "" : " " + pNameDT.getMiddleNm()); // NPP008

                    personDetails.append(pNameDT.getLastNm() == null ? "" : " " + pNameDT.getLastNm()); // NPP007
                    personDetails.append(pNameDT.getNmSuffix() == null ? "" : ", " + pNameDT.getNmSuffix()); // NPP010
                    personDetails.append(pNameDT.getNmDegree() == null ? "" : ", " + pNameDT.getNmDegree()); // NPP060
                }
            }
            if (personVO.getTheEntityIdDTCollection() != null)
            {
                Iterator<Object> itEntity = personVO.getTheEntityIdDTCollection().iterator();
                while (itEntity.hasNext())
                {
                    EntityIdDT entityDT = (EntityIdDT) itEntity.next();
                    if (entityDT != null)
                    {
                        if (entityDT.getTypeCd().equalsIgnoreCase("FI"))
                        {
                            personDetails.append("$[[~").append(
                                    (entityDT.getRootExtensionTxt() == null) ? "" : "<br/>"
                                            + (entityDT.getRootExtensionTxt()));
                            personDetails.append((entityDT.getTypeCd() == null) ? "" : " " + (entityDT.getTypeCd()));
                            personDetails.append((entityDT.getAssigningAuthorityCd() == null) ? "" : " "
                                    + (entityDT.getAssigningAuthorityCd()));
                            personDetails.append(
                                    (entityDT.getAssigningAuthorityDescTxt() == null) ? "" : " "
                                            + (entityDT.getAssigningAuthorityDescTxt())).append("~]]|");
                        }
                    }
                }
            }
            else
                personDetails.append("|");
        }
        return personDetails.toString();
    }

    private String convertRolesTorequest(LabResultProxyVO labResultProxyVO, PersonVO personVO)
    {
        StringBuffer personBuff = new StringBuffer();
        Collection<Object> roleColl = labResultProxyVO.getTheRoleDTCollection();
        if (roleColl != null)
        {
            Iterator<Object> roleIt = roleColl.iterator();
            while (roleIt.hasNext())
            {
                RoleDT roleDT = (RoleDT) roleIt.next();
                if (roleDT != null
                        && roleDT.getSubjectEntityUid().compareTo(personVO.getThePersonDT().getPersonUid()) == 0)
                {
                    personBuff.append(roleDT.getCdDescTxt() == null ? "" : roleDT.getCdDescTxt());
                    personBuff.append(roleDT.getCd() == null ? "" : "(" + roleDT.getCd() + ")");
                    break;
                }
            }
        }
        return personBuff.toString();
    }

    private String convertRolesTorequestWithPersonVO(PersonVO personVO)
    {
        StringBuffer personBuff = new StringBuffer();
        Collection<?> roleColl = personVO.getTheRoleDTCollection();
        if (roleColl != null)
        {
            Iterator<?> roleIt = roleColl.iterator();
            while (roleIt.hasNext())
            {
                RoleDT roleDT = (RoleDT) roleIt.next();
                if (roleDT != null
                        && roleDT.getSubjectEntityUid().compareTo(personVO.getThePersonDT().getPersonUid()) == 0)
                {
                    personBuff.append(roleDT.getCdDescTxt() == null ? "" : roleDT.getCdDescTxt());
                    personBuff.append(roleDT.getCd() == null ? "" : "(" + roleDT.getCd() + ")");
                    break;
                }
            }
        }
        return personBuff.toString();
    }

  private void convertCopyToProviders(Collection<Object>  copyToPersonColl, HttpServletRequest request)
  {


    Collection<Object>  copyToProvDetails =  new ArrayList<Object> ();
       Iterator<Object>  pIt = copyToPersonColl.iterator();
        //CachedDropDownValues cachedConverter = new CachedDropDownValues();
        while(pIt.hasNext())
        {
          StringBuffer sbuff = new StringBuffer("");
          PersonVO pvo = (PersonVO)pIt.next();

            ArrayList<Object> pNamelist = (ArrayList<Object> ) pvo.getThePersonNameDTCollection();
           Iterator<Object>  pNameIt = pNamelist.iterator();
            while (pNameIt.hasNext()) {
              PersonNameDT pNameDT = (PersonNameDT) pNameIt.next();
              if (pNameDT != null) {
                if (pNameDT.getNmUseCd().equalsIgnoreCase(NEDSSConstants.LEGAL_NAME)) {
                  sbuff.append(pNameDT.getNmPrefix() == null ? "" :
                                            pNameDT.getNmPrefix());
                  sbuff.append(pNameDT.getFirstNm() == null ? "" :
                                            " " + pNameDT.getFirstNm());
                  sbuff.append(pNameDT.getMiddleNm() == null ? "" :
                                            " " + pNameDT.getMiddleNm());
                  sbuff.append(pNameDT.getLastNm() == null ? "" :
                                            " " + pNameDT.getLastNm());
                  sbuff.append(pNameDT.getNmSuffix() == null ? "" :
                                            ", " + pNameDT.getNmSuffix());
                  sbuff.append(pNameDT.getNmDegree() == null ? "" :
                                            ", " + pNameDT.getNmDegree());
                }
              }
            }

            ArrayList<Object> entityCollections = (ArrayList<Object> ) pvo.getTheEntityLocatorParticipationDTCollection();
           Iterator<Object>  locatorIt = entityCollections.iterator();
            while (locatorIt.hasNext()) {
              EntityLocatorParticipationDT entityLocatorDT = (
                  EntityLocatorParticipationDT) locatorIt.next();
              //ELP111 for office address
              if(entityLocatorDT!= null && entityLocatorDT.getCd()!= null && //entityLocatorDT.getCdDescTxt()!= null &&
              entityLocatorDT.getClassCd()!= null && entityLocatorDT.getRecordStatusCd()!= null &&
              entityLocatorDT.getUseCd()!= null)
              {
                if (entityLocatorDT.getCd().equalsIgnoreCase("O") &&
                  entityLocatorDT.getClassCd().equalsIgnoreCase("PST") &&
                  entityLocatorDT.getRecordStatusCd().equalsIgnoreCase("ACTIVE")
                  && entityLocatorDT.getUseCd().equalsIgnoreCase("WP")) {
                  PostalLocatorDT postalDT = entityLocatorDT.getThePostalLocatorDT();
                  if (postalDT != null) {
                    sbuff.append(postalDT.getStreetAddr1() == null ? "" :
                                      "<br/>"+ postalDT.getStreetAddr1());
                    sbuff.append(postalDT.getStreetAddr2() == null ? " " :
                                       "<br/>" + postalDT.getStreetAddr2());
                    sbuff.append(postalDT.getCityDescTxt() == null ? "<br/> " :
                                       "<br/>" + postalDT.getCityDescTxt());
                    sbuff.append( (postalDT.getCityDescTxt() != null &&
                                         postalDT.getStateCd() != null) ? ", " : "");
                    sbuff.append(postalDT.getStateCd() == null ? " " :
                                       " " + this.getStateDescTxt(postalDT.getStateCd()));
                    sbuff.append(postalDT.getZipCd() == null ? " " :
                                       " " + postalDT.getZipCd());
                  }
                }

              }
              if(entityLocatorDT!=null && entityLocatorDT.getCd()!= null && //entityLocatorDT.getCdDescTxt()!= null &&
             entityLocatorDT.getClassCd()!= null && entityLocatorDT.getRecordStatusCd()!= null &&
             entityLocatorDT.getUseCd()!= null)
             {
               if (entityLocatorDT.getCd().equalsIgnoreCase("PH") &&
                 entityLocatorDT.getClassCd().equalsIgnoreCase("TELE") &&
                 entityLocatorDT.getRecordStatusCd().equalsIgnoreCase("ACTIVE")
                 && entityLocatorDT.getUseCd().equalsIgnoreCase("WP")) {
                 StringBuffer phoneDetails = new StringBuffer("");
                 TeleLocatorDT teleDT = entityLocatorDT.getTheTeleLocatorDT();
                 if (teleDT != null) {
                   phoneDetails.append(teleDT.getPhoneNbrTxt() == null ? "" :
                                      teleDT.getPhoneNbrTxt());
                   phoneDetails.append((teleDT.getExtensionTxt() == null) &&
                   (teleDT.getExtensionTxt() == null) ? " " :
                   " <b>Ext. </b>" );
                   phoneDetails.append(teleDT.getExtensionTxt() == null ? " " :
                                      " " + teleDT.getExtensionTxt());
                 }
               }
             }
            }
            if(pvo.getTheEntityIdDTCollection()!= null)
            {
             Iterator<Object>  iter = pvo.getTheEntityIdDTCollection().iterator();
              while(iter.hasNext())
              {
                EntityIdDT entityIdDT = (EntityIdDT)iter.next();
                if(entityIdDT.getTypeCd()!=null && entityIdDT.getTypeCd().equalsIgnoreCase("FI"))
                {
                  sbuff.append(entityIdDT.getRootExtensionTxt()== null? "N/A":entityIdDT.getRootExtensionTxt());
                  sbuff.append(entityIdDT.getAssigningAuthorityCd()== null? "N/A":entityIdDT.getAssigningAuthorityCd());
                }
              }
            }
          copyToProvDetails.add(sbuff.toString());
        }

        request.setAttribute("copyToProvDetails", copyToProvDetails);


  }

    private PersonVO getPatientVO(LabResultProxyVO labproxyVO, ObservationVO lab112VO)
    {
        PersonVO patientVO = null;
        Long patientUid = null;
        if (lab112VO.getTheParticipationDTCollection() != null)
        {
            Iterator<Object> it = lab112VO.getTheParticipationDTCollection().iterator();
            while (it.hasNext())
            {
                ParticipationDT partDT = (ParticipationDT) it.next();
                if (partDT != null && partDT.getTypeCd().equalsIgnoreCase("PATSBJ")
                        && partDT.getActClassCd().equalsIgnoreCase("OBS"))
                {
                    patientUid = partDT.getSubjectEntityUid();
                }

            }
        }
        if (labproxyVO.getThePersonVOCollection() != null)
        {
            Iterator<Object> it = labproxyVO.getThePersonVOCollection().iterator();
            while (it.hasNext())
            {
                PersonVO personVO = (PersonVO) it.next();
                if (personVO != null && personVO.getThePersonDT().getPersonUid().compareTo(patientUid) == 0)
                    patientVO = personVO;
            }
        }
        return patientVO;
    }

    // LDF load methods
    /*
     * Added business ObjectUID as a parameter for CreateXSP method in all Edit
     * and View Load as a part of CDF changes
     */
    /**
     * @TBD Release 6.0, Commented out as LDF will be planned out as new type of answers
  
    public void loadLabLDFAdd(HttpServletRequest request)
    {
        LabResultProxyVO labResultProxyVO = new LabResultProxyVO();
        createXSP(NEDSSConstants.LABREPORT_LDF, labResultProxyVO, null, request);
    }
	*/
    public void loadPatientLDFAdd(HttpServletRequest request)
    {
        PersonVO personVO = new PersonVO();
        createXSP(NEDSSConstants.PATIENT_LDF, personVO, null, request);
    }

    public void loadMorbLDFAdd(HttpServletRequest request)
    {
        MorbidityProxyVO morbidityProxyVO = new MorbidityProxyVO();
        createXSP(NEDSSConstants.MORBREPORT_LDF, morbidityProxyVO, null, request);
    }

    /**
     * @TBD Release 6.0, Commented out as LDF will be planned out as new type of answers
  	public void loadLabLDFEdit(LabResultProxyVO labResultProxyVO, Long lab112UID, HttpServletRequest request)
    {
        // prepareLDF(labResultProxyVO, request);
        // ObservationVO observationVO =
        // (ObservationVO)convertProxyToObs112(labResultProxyVO,request);
        // Long observationUid =
        // observationVO.getTheObservationDT().getObservationUid();
        createXSP(NEDSSConstants.LABREPORT_LDF, lab112UID, labResultProxyVO, null, request);
    }*/

    public void loadMorbLDFEdit(MorbidityProxyVO morbidityProxyVO, Long ObservationUid, HttpServletRequest request)
    {
        // prepareLDF(morbidityProxyVO, request);
        createXSP(NEDSSConstants.MORBREPORT_LDF, ObservationUid, morbidityProxyVO, null, request);
    }
    /**
     * @TBD Release 6.0, Commented out as LDF will be planned out as new type of answers
  
    public void loadLabLDFView(LabResultProxyVO labResultProxyVO, Long lab112UID, HttpServletRequest request)
    {
        // ObservationVO observationVO =
        // (ObservationVO)convertProxyToObs112(labResultProxyVO,request);
        // Long observationUid =
        // observationVO.getTheObservationDT().getObservationUid();
        createXSP(NEDSSConstants.LABREPORT_LDF, lab112UID, labResultProxyVO, null, request);
    }*/

    public void loadMorbLDFView(MorbidityProxyVO morbidityProxyVO, Long observationUid, HttpServletRequest request)
    {
        createXSP(NEDSSConstants.MORBREPORT_LDF, observationUid, morbidityProxyVO, null, request);
    }

  //submit LDF methods

  public void submitPatientLDF(PersonVO personVO, ObservationForm observationForm, boolean newTest,HttpServletRequest request)
  {
    // if (PropertyUtil.getLDF() == true)
    // {
        if(observationForm.getLdfCollection()!= null)
        {
          ArrayList<Object> list = new ArrayList<Object> ();
          // use the new API to retrieve custom field collection
          // to handle multiselect fields (xz 01/11/2005)
          Collection<Object>  coll = extractLdfDataCollection(observationForm, request);
          for (Iterator<Object>  it = coll.iterator(); it.hasNext(); )
          {
            StateDefinedFieldDataDT  stateDT = (StateDefinedFieldDataDT)it.next();
            if (stateDT != null && stateDT.getBusinessObjNm() != null && stateDT.getBusinessObjNm().equalsIgnoreCase(NEDSSConstants.PATIENT_LDF))
            {
              if(newTest==true)
                stateDT.setItDirty(false);
              else
                stateDT.setItDirty(true);
              list.add(stateDT);
            }
          }
          personVO.setTheStateDefinedFieldDataDTCollection(list);
        }
    // }
  }

    public void submitPatientLDFMorb(PersonVO personVO, MorbidityForm morbForm, boolean newTest,
            HttpServletRequest request)
    {
        // if (PropertyUtil.getLDF() == true)
        // {
        if (morbForm.getLdfCollection() != null)
        {
            ArrayList<Object> list = new ArrayList<Object>();
            // use the new API to retrieve custom field collection
            // to handle multiselect fields (xz 01/11/2005)
            Collection<Object> coll = extractLdfDataCollection(morbForm, request);
            for (Iterator<Object> it = coll.iterator(); it.hasNext();)
            {
                StateDefinedFieldDataDT stateDT = (StateDefinedFieldDataDT) it.next();
                if (stateDT != null && stateDT.getBusinessObjNm() != null
                        && stateDT.getBusinessObjNm().equalsIgnoreCase(NEDSSConstants.PATIENT_LDF))
                {
                    if (newTest == true)
                        stateDT.setItDirty(false);
                    else
                        stateDT.setItDirty(true);
                    list.add(stateDT);
                }
            }
            personVO.setTheStateDefinedFieldDataDTCollection(list);
        }
        // }
    }

    /**
     * @TBD Release 6.0, Commented out as LDF will be planned out as new type of answers
  
    public void submitLabLDF(LabResultProxyVO labResultProxyVO, ObservationForm observationForm, boolean newTest,
            HttpServletRequest request)
    {
        // if (PropertyUtil.getLDF() == true)
        // {
        if (observationForm.getLdfCollection() != null)
        {
            ArrayList<Object> list = new ArrayList<Object>();
            // use the new API to retrieve custom field collection
            // to handle multiselect fields (xz 01/11/2005)
            Collection<Object> coll = extractLdfDataCollection(observationForm, request);
            for (Iterator<Object> it = coll.iterator(); it.hasNext();)
            {
                StateDefinedFieldDataDT stateDT = (StateDefinedFieldDataDT) it.next();
                if (stateDT != null && stateDT.getBusinessObjNm() != null
                        && stateDT.getBusinessObjNm().equalsIgnoreCase(NEDSSConstants.LABREPORT_LDF))
                {
                    if (newTest == true)
                        stateDT.setItDirty(false);
                    else
                        stateDT.setItDirty(true);
                    list.add(stateDT);
                }
            }
            labResultProxyVO.setTheStateDefinedFieldDataDTCollection(list);
        }
        // }
    }
*/
    public void submitMorbLDF(MorbidityProxyVO morbidityProxyVO, MorbidityForm morbForm, boolean newTest,
            HttpServletRequest request)
    {
        // if (PropertyUtil.getLDF() == true)
        // {
        if (morbForm.getLdfCollection() != null)
        {
            ArrayList<Object> list = new ArrayList<Object>();
            // use the new API to retrieve custom field collection
            // to handle multiselect fields (xz 01/11/2005)
            Collection<Object> coll = extractLdfDataCollection(morbForm, request);
            for (Iterator<Object> it = coll.iterator(); it.hasNext();)
            {
                StateDefinedFieldDataDT stateDT = (StateDefinedFieldDataDT) it.next();
                if (stateDT != null && stateDT.getBusinessObjNm() != null
                        && stateDT.getBusinessObjNm().equalsIgnoreCase(NEDSSConstants.MORBREPORT_LDF))
                {
                    if (newTest == true)
                        stateDT.setItDirty(false);
                    else
                        stateDT.setItDirty(true);
                    list.add(stateDT);
                }
            }
            morbidityProxyVO.setTheStateDefinedFieldDataDTCollection(list);
        }
        // }
    }

    public ObservationVO getDIsplayValueOfDropDownForIsolate(ObservationVO obsVO)
    {
        CachedDropDownValues cdv = new CachedDropDownValues();
        String displayName = "";
        ObservationDT obsDT = obsVO.getTheObservationDT();
        if (obsDT.getCd().equalsIgnoreCase(NEDSSConstants.LAB_329A))
        {
            displayName = cdv.getDescForCode("YN", obsVO.getObsValueCodedDT_s(0).getCode());
        }
        else if (obsDT.getCd().equalsIgnoreCase(NEDSSConstants.LAB_331))
        {
            displayName = cdv.getDescForCode("YNU", obsVO.getObsValueCodedDT_s(0).getCode());
        }
        else if (obsDT.getCd().equalsIgnoreCase(NEDSSConstants.LAB_332))
        {
            displayName = cdv.getDescForCode("PHVSFB_SPECFORW", obsVO.getObsValueCodedDT_s(0).getCode());
        }
        else if (obsDT.getCd().equalsIgnoreCase(NEDSSConstants.LAB_336))
        {
            displayName = cdv.getDescForCode("YNU", obsVO.getObsValueCodedDT_s(0).getCode());
        }
        else if (obsDT.getCd().equalsIgnoreCase(NEDSSConstants.LAB_337))
        {
            displayName = cdv.getDescForCode("YN", obsVO.getObsValueCodedDT_s(0).getCode());
        }
        else if (obsDT.getCd().equalsIgnoreCase(NEDSSConstants.LAB_338))
        {
            displayName = cdv.getDescForCode("YNU", obsVO.getObsValueCodedDT_s(0).getCode());
        }
        else if (obsDT.getCd().equalsIgnoreCase(NEDSSConstants.LAB_345))
        {
            displayName = cdv.getDescForCode("YN", obsVO.getObsValueCodedDT_s(0).getCode());
        }
        else if (obsDT.getCd().equalsIgnoreCase(NEDSSConstants.LAB_346))
        {
            displayName = cdv.getDescForCode("YNU", obsVO.getObsValueCodedDT_s(0).getCode());
        }
        else if (obsDT.getCd().equalsIgnoreCase(NEDSSConstants.LAB_347))
        {
            displayName = cdv.getDescForCode("PHVSFB_ISOLATNO", obsVO.getObsValueCodedDT_s(0).getCode());
        }
        else if (obsDT.getCd().equalsIgnoreCase(NEDSSConstants.LAB_351))
        {
            displayName = cdv.getDescForCode("YN", obsVO.getObsValueCodedDT_s(0).getCode());
        }
        else if (obsDT.getCd().equalsIgnoreCase(NEDSSConstants.LAB_352))
        {
            displayName = cdv.getDescForCode("PHVSFB_ISOLATAV", obsVO.getObsValueCodedDT_s(0).getCode());
        }
        else if (obsDT.getCd().equalsIgnoreCase(NEDSSConstants.LAB_353))
        {
            displayName = cdv.getDescForCode("PHVSFB_SPECAVAL", obsVO.getObsValueCodedDT_s(0).getCode());
        }
        else if (obsDT.getCd().equalsIgnoreCase(NEDSSConstants.LAB_355))
        {
            displayName = cdv.getDescForCode("PHVSFB_CDCLABSH", obsVO.getObsValueCodedDT_s(0).getCode());
        }
        else if (obsDT.getCd().equalsIgnoreCase(NEDSSConstants.LAB_358))
        {
            displayName = cdv.getDescForCode("YN", obsVO.getObsValueCodedDT_s(0).getCode());
        }
        else if (obsDT.getCd().equalsIgnoreCase(NEDSSConstants.LAB_359))
        {
            displayName = cdv.getDescForCode("PHVSFB_CONTAMIN", obsVO.getObsValueCodedDT_s(0).getCode());
        }
        else if (obsDT.getCd().equalsIgnoreCase(NEDSSConstants.LAB_330))
        {
            displayName = cdv.getDescForCode("PHVSFB_SPCMNPTSTATUS", obsVO.getObsValueCodedDT_s(0).getCode());
        }
        else if (obsDT.getCd().equalsIgnoreCase(NEDSSConstants.LAB_363))
        {
            displayName = cdv.getDescForCode("YNU", obsVO.getObsValueCodedDT_s(0).getCode());
        }

        obsVO.getObsValueCodedDT_s(0).setDisplayName(displayName);
        return obsVO;
    }
}
