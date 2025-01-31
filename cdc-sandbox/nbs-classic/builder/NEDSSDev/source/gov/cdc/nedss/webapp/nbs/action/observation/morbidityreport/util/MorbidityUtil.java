package gov.cdc.nedss.webapp.nbs.action.observation.morbidityreport.util;

import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.webapp.nbs.action.util.CallProxyEJB;
import gov.cdc.nedss.webapp.nbs.action.util.ErrorMessageHelper;
import gov.cdc.nedss.webapp.nbs.action.util.QuickEntryEventHelper;
import gov.cdc.nedss.controller.ejb.entitycontrollerejb.bean.EntityControllerHome;
import gov.cdc.nedss.entity.person.vo.*;
import gov.cdc.nedss.entity.person.dt.*;
import gov.cdc.nedss.entity.entityid.dt.*;
import gov.cdc.nedss.entity.organization.vo.*;
import gov.cdc.nedss.entity.organization.dt.*;
import gov.cdc.nedss.locator.dt.*;
import gov.cdc.nedss.webapp.nbs.action.util.*;
import gov.cdc.nedss.webapp.nbs.action.person.util.*;
import gov.cdc.nedss.systemservice.ejb.dbauthejb.dao.DbAuthDAOImpl;
import gov.cdc.nedss.systemservice.ejb.dbauthejb.dt.AuthUserDT;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.page.ejb.pageproxyejb.dt.NBSAttachmentDT;
import gov.cdc.nedss.proxy.ejb.entityproxyejb.bean.EntityProxyHome;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.bean.InvestigationProxyHome;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.bean.ObservationProxyHome;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.MorbidityProxyVO;
import gov.cdc.nedss.act.attachment.dt.AttachmentDT;
import gov.cdc.nedss.act.attachment.vo.AttachmentVO;
import gov.cdc.nedss.act.observation.dt.*;
import gov.cdc.nedss.act.observation.vo.*;
import gov.cdc.nedss.association.dt.*;
import gov.cdc.nedss.webapp.nbs.util.BatchEntryHelper;
import gov.cdc.nedss.webapp.nbs.form.morbidity.*;
import gov.cdc.nedss.webapp.nbs.form.util.FileUploadForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.systemservice.util.*;
import gov.cdc.nedss.webapp.nbs.action.observation.labreport.CommonLabUtil;
import gov.cdc.nedss.exception.*;

import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.SRTValues;
import gov.cdc.nedss.act.treatment.vo.TreatmentVO;
import gov.cdc.nedss.act.treatment.dt.TreatmentAdministeredDT;
import gov.cdc.nedss.act.treatment.dt.TreatmentDT;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003 CSC
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author Nedss Development
 * @version 1.1
 */

public class MorbidityUtil
{
    // For logging
    static final LogUtils logger = new LogUtils(MorbidityUtil.class.getName());
    QuickEntryEventHelper helper;
    private static GregorianCalendar currentDate;
    
    public MorbidityUtil()
    {
    }

    public PersonVO findPatientRevision(MorbidityProxyVO morbidityProxyVO)
    {
        Collection<Object> personVOColl = morbidityProxyVO.getThePersonVOCollection();
        for (Iterator<Object> it = personVOColl.iterator(); it.hasNext();)
        {
            PersonVO pvo = (PersonVO) it.next();
            if (pvo != null && pvo.getThePersonDT() != null
                    && pvo.getThePersonDT().getCd().equalsIgnoreCase(NEDSSConstants.PAT))
            {
                return pvo;
            }
        }
        return null;
    }

    public PersonVO findMasterPatientRecord(Long mprUId, HttpSession session, NBSSecurityObj secObj)
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
            logger.fatal("MorbidityUtil personVO: ", ex);
        }
        return personVO;
    }

    public MorbidityProxyVO getProxyFromEJB(Long observationUid, HttpSession session) throws java.rmi.RemoteException,
            javax.ejb.EJBException, Exception
    {

        MainSessionCommand msCommand = null;

        // try {
        String sBeanJndiName = JNDINames.OBSERVATION_PROXY_EJB;
        String sMethod = null;
        MainSessionHolder holder = new MainSessionHolder();
        ArrayList<?> arr = new ArrayList<Object>();

        sMethod = "getMorbidityProxy";
        Object[] oParams = { observationUid };
        msCommand = holder.getMainSessionCommand(session);
        arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
        if ((arr != null) && (arr.size() > 0))
        {

            MorbidityProxyVO result = (MorbidityProxyVO) arr.get(0);
            return result;
        }

        return null;
    }

    public boolean investigationAssociated(Long morbidityUid, HttpSession session) throws java.rmi.RemoteException,
            javax.ejb.EJBException, Exception
    {
        MainSessionCommand msCommand = null;

        String sBeanJndiName = JNDINames.INVESTIGATION_PROXY_EJB;
        String sMethod = null;
        MainSessionHolder holder = new MainSessionHolder();
        ArrayList<?> arr = new ArrayList<Object>();

        sMethod = "investigationAssociatedWithMorbidity";
        Object[] oParams = { morbidityUid };
        msCommand = holder.getMainSessionCommand(session);
        arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
        if ((arr != null) && (arr.size() > 0))
        {
            boolean result = ((Boolean) arr.get(0)).booleanValue();
            return result;
        }
        return false;
    }

    public void mapMorbReportToRequest(MorbidityProxyVO morbidityProxyVO, HttpServletRequest request)
    {
        Collection<ObservationVO> observationVOcoll = morbidityProxyVO.getTheObservationVOCollection();

        CachedDropDownValues cdv = new CachedDropDownValues();

        for (Iterator<ObservationVO> it = observationVOcoll.iterator(); it.hasNext();)
        {
            ObservationVO obsVO = (ObservationVO) it.next();
            ObservationDT obsDT = obsVO.getTheObservationDT();
            String ctrlCd = obsDT.getCtrlCdDisplayForm();
            if (obsDT != null && ctrlCd != null && ctrlCd.equalsIgnoreCase("MorbReport"))
            {
                String conditionPA = obsDT.getProgAreaCd() + "^" + cdv.getProgramAreaDesc(obsDT.getProgAreaCd()) + "^"
                        + obsDT.getCd();
                request.setAttribute("conditionProgArea", conditionPA);
                request.setAttribute("MRB121", obsDT.getCd() == null ? " " : obsDT.getCd());
                request.setAttribute("MRB136", obsDT.getProgAreaCd() == null ? " " : obsDT.getProgAreaCd());
                request.setAttribute("programAreaDesc", cdv.getProgramAreaDesc(obsDT.getProgAreaCd()));
                request.setAttribute("MRB137", obsDT.getJurisdictionCd() == null ? " " : obsDT.getJurisdictionCd());
                request.setAttribute("MRB142", obsDT.getSharedInd() == null ? " " : obsDT.getSharedInd());
                request.setAttribute("MRB101", StringUtils.formatDate(obsDT.getActivityToTime()) == null ? " "
                        : StringUtils.formatDate(obsDT.getActivityToTime()));
                request.setAttribute("MRB162", StringUtils.formatDate(obsDT.getRptToStateTime()) == null ? " "
                        : StringUtils.formatDate(obsDT.getRptToStateTime()));
                request.setAttribute(
                        "LAB202",
                        StringUtils.formatDate(obsDT.getAddTime()) == null ? " " : StringUtils.formatDate(obsDT
                                .getAddTime()));
                request.setAttribute("LAB203", obsDT.getAddUserName() == null ? "Unknown User" : obsDT.getAddUserName());
                request.setAttribute("LAB211", StringUtils.formatDate(obsDT.getLastChgTime()) == null ? ""
                        : StringUtils.formatDate(obsDT.getLastChgTime()));
                request.setAttribute("LAB212",
                        obsDT.getLastChgUserName() == null ? "Unknown User" : obsDT.getLastChgUserName());
                // #2566 : Number of Weeks Pregnant.
                if (obsDT.getPregnantWeek() != null)
                {
                    request.setAttribute("pregnantWeek", obsDT.getPregnantWeek());
                }
            }
        }
    }

    /**
     * to extract batch entry observationVOs from morbidity proxyVO and
     * construct an observationVO Collection then format and load into request
     * 
     **/
    public void mapBatchEntryToRequest(MorbidityProxyVO morbidityProxyVO, HttpServletRequest request)
    {
        // System.out.println("calling map batch entry to request");
        Collection<Object> labResultsCollection = mapBatchEntry(morbidityProxyVO);
        if (!labResultsCollection.isEmpty())
        {
            convertBatchEntryToRequest(labResultsCollection, request);
        }
        Collection<Object> morbTreatColl = mapBatchEntryForTreatment(morbidityProxyVO);
        if (!morbTreatColl.isEmpty())
        {
            convertTreatmentBatchEntryToRequest(morbTreatColl, request);
        }
        
        Collection<Object> morbAttachColl = mapBatchEntryForAttachment(morbidityProxyVO, request);
        if (!morbAttachColl.isEmpty())
        {
            convertAttachmentBatchEntryToRequest(morbAttachColl, request);
        }
        
        
    }

    // OA map
    public TreeMap<Object, Object> mapObsQA(MorbidityProxyVO morbidityProxyVO)
    {
        Collection<ObservationVO> observationVOcoll = morbidityProxyVO.getTheObservationVOCollection();
        TreeMap<Object, Object> treeMap = new TreeMap<Object, Object>();

        for (Iterator<ObservationVO> it = observationVOcoll.iterator(); it.hasNext();)
        {
            ObservationVO obsVO = (ObservationVO) it.next();
            ObservationDT obsDT = obsVO.getTheObservationDT();
            String mappingCd = obsDT.getCd();
            if (obsDT != null && mappingCd != null)
            {
                if (mappingCd.equalsIgnoreCase("MRB100"))
                {
                    treeMap.put("MRB100", obsVO);
                    treeMap.put("MRB100uid", obsVO.getTheObservationDT().getObservationUid());
                }
                else if (mappingCd.equalsIgnoreCase("MRB161"))
                {
                    treeMap.put("MRB161", obsVO);
                    treeMap.put("MRB161uid", obsVO.getTheObservationDT().getObservationUid());
                }
                else if (mappingCd.equalsIgnoreCase("MRB122"))
                {
                    treeMap.put("MRB122", obsVO);
                    treeMap.put("MRB122uid", obsVO.getTheObservationDT().getObservationUid());
                }
                else if (mappingCd.equalsIgnoreCase("MRB165"))
                {
                    treeMap.put("MRB165", obsVO);
                    treeMap.put("MRB165uid", obsVO.getTheObservationDT().getObservationUid());
                }
                else if (mappingCd.equalsIgnoreCase("INV145"))
                {
                    treeMap.put("INV145", obsVO);
                    treeMap.put("INV145uid", obsVO.getTheObservationDT().getObservationUid());
                }
                else if (mappingCd.equalsIgnoreCase("INV128"))
                {
                    treeMap.put("INV128", obsVO);
                    treeMap.put("INV128uid", obsVO.getTheObservationDT().getObservationUid());
                }
                else if (mappingCd.equalsIgnoreCase("MRB166"))
                {
                    treeMap.put("MRB166", obsVO);
                    treeMap.put("MRB166uid", obsVO.getTheObservationDT().getObservationUid());
                }
                else if (mappingCd.equalsIgnoreCase("MRB167"))
                {
                    treeMap.put("MRB167", obsVO);
                    treeMap.put("MRB167uid", obsVO.getTheObservationDT().getObservationUid());
                }
                else if (mappingCd.equalsIgnoreCase("INV178"))
                {
                    treeMap.put("INV178", obsVO);
                    treeMap.put("INV178uid", obsVO.getTheObservationDT().getObservationUid());
                }
                else if (mappingCd.equalsIgnoreCase("INV149"))
                {
                    treeMap.put("INV149", obsVO);
                    treeMap.put("INV149uid", obsVO.getTheObservationDT().getObservationUid());
                }
                else if (mappingCd.equalsIgnoreCase("INV148"))
                {
                    treeMap.put("INV148", obsVO);
                    treeMap.put("INV148uid", obsVO.getTheObservationDT().getObservationUid());
                }
                else if (mappingCd.equalsIgnoreCase("MRB129"))
                {
                    treeMap.put("MRB129", obsVO);
                    treeMap.put("MRB129uid", obsVO.getTheObservationDT().getObservationUid());
                }
                else if (mappingCd.equalsIgnoreCase("MRB130"))
                {
                    treeMap.put("MRB130", obsVO);
                    treeMap.put("MRB130uid", obsVO.getTheObservationDT().getObservationUid());
                }
                else if (mappingCd.equalsIgnoreCase("MRB168"))
                {
                    treeMap.put("MRB168", obsVO);
                    treeMap.put("MRB168uid", obsVO.getTheObservationDT().getObservationUid());
                }
                else if (mappingCd.equalsIgnoreCase("MRB169"))
                {
                    treeMap.put("MRB169", obsVO);
                    treeMap.put("MRB169uid", obsVO.getTheObservationDT().getObservationUid());
                }
                else if (mappingCd.equalsIgnoreCase("MRB102"))
                {
                    treeMap.put("MRB102", obsVO);
                    treeMap.put("MRB102uid", obsVO.getTheObservationDT().getObservationUid());
                }
                else if (obsDT.getCtrlCdDisplayForm() != null
                        && obsDT.getCtrlCdDisplayForm().equals(NEDSSConstants.MORBIDITY_CODE))
                {
                    treeMap.put("MRB101", obsVO); // MorbReport
                    treeMap.put("MRB101uid", obsVO.getTheObservationDT().getObservationUid());
                }
            } // finish mapping QA and MorbReport
        }
        return treeMap;
    }

    /**
     * BatchEntry Mapping retrieve old collection from proxy then create new
     * collection to load to form
     */
    public Collection<Object> mapBatchEntry(MorbidityProxyVO morbidityProxyVO)
    {
        // System.out.println("..............calling map batch entry ....................");
        Collection<Object> labResultsCollection = new ArrayList<Object>();
        Collection<ObservationVO> observationVOCollection = morbidityProxyVO.getTheObservationVOCollection();
        
        for (Iterator<ObservationVO> anIter = observationVOCollection.iterator(); anIter.hasNext();)
        {
            ObservationVO obsVO = (ObservationVO) anIter.next();

            // get only the LabReportMorb observations
            if (obsVO.getTheObservationDT().getCtrlCdDisplayForm() != null
                    && obsVO.getTheObservationDT().getCtrlCdDisplayForm().equals(NEDSSConstants.LAB_REPORT_MORB))
            {
                TreeMap<Object, Object> treeMap = new TreeMap<Object, Object>();

                treeMap.put("OrderedTest", obsVO);
                treeMap.put("OrderedTestuid", obsVO.getTheObservationDT().getObservationUid());
                treeMap.put("LAB163", obsVO.getTheObservationDT().getEffectiveFromTime());
                treeMap.put("LAB197", obsVO.getTheObservationDT().getActivityToTime());
                treeMap.put("LAB266", obsVO.getTheObservationDT().getTxt());

                //
                Collection<Object> actRelationshipColl = obsVO.getTheActRelationshipDTCollection();
                for (Iterator<Object> arItor = actRelationshipColl.iterator(); arItor.hasNext();)
                {
                    ActRelationshipDT actRelationshipDT = (ActRelationshipDT) arItor.next();
                    // if actRelationshipDT.getTypeCd().equals("COMP")
                    // then it is a Resulted Test
                    if (actRelationshipDT.getTypeCd().equals(NEDSSConstants.ACT110_TYP_CD))
                    {
                        ObservationVO itemObs = this.getObservationByUid(observationVOCollection,
                                actRelationshipDT.getSourceActUid());
                        if (itemObs != null)
                        {
                            treeMap.put("ResultTest", itemObs);
                            treeMap.put("ResultTestuid", itemObs.getTheObservationDT().getObservationUid());
                            if (itemObs.getTheObservationDT() != null)
                            {
                                if (itemObs.getTheObservationDT().getCdDescTxt() != null)
                                {
                                    treeMap.put("LAB220", itemObs.getTheObservationDT().getCdDescTxt());
                                }

                                if (itemObs.getTheObservationDT().getCtrlCdUserDefined1() != null)
                                {
                                    treeMap.put("CtrlCdUserDefined1", itemObs.getTheObservationDT()
                                            .getCtrlCdUserDefined1());
                                }

                            }

                            // Coded Result Value and Organism Name
                            // suppose to be one or the other but not both
                            // but xsp is not handled that way by struts
                            Collection<Object> obsValCodedColl = itemObs.getTheObsValueCodedDTCollection();
                            for (Iterator<Object> obsValIter = obsValCodedColl.iterator(); obsValIter.hasNext();)
                            {
                                ObsValueCodedDT obsValCodedDT = (ObsValueCodedDT) obsValIter.next();
                                if (itemObs.getTheObservationDT().getCtrlCdUserDefined1() != null
                                        && itemObs.getTheObservationDT().getCtrlCdUserDefined1().equals("Y")
                                        && (obsValCodedDT.getCode() != null)
                                        && (!obsValCodedDT.getCode().equalsIgnoreCase("NI")))
                                {
                                    treeMap.put("orgDescTxt", obsValCodedDT.getDisplayName());
                                    treeMap.put("codeSystemCd", obsValCodedDT.getCodeSystemCd());
                                    treeMap.put("searchResultRT", obsValCodedDT.getCode());
                                    treeMap.put("LAB278", obsValCodedDT.getCode());
                                    // obsValCodedDT.setDisplayName(obsValCodedDT.getCode());
                                    // obsValCodedDT.setCode("NI");
                                }
                                else if (itemObs.getTheObservationDT().getCtrlCdUserDefined1() != null
                                        && itemObs.getTheObservationDT().getCtrlCdUserDefined1().equals("N"))
                                {
                                    obsValCodedDT.setDisplayName(null);
                                    if (obsValCodedDT.getCode() != null
                                            && !obsValCodedDT.getCode().equalsIgnoreCase("NI"))
                                        treeMap.put("LAB121", obsValCodedDT.getCode());
                                }
                                else
                                // if (obsValCodedDT != null)
                                {
                                    if (obsValCodedDT.getDisplayName() != null)
                                    {
                                        treeMap.put("LAB278", obsValCodedDT.getDisplayName());
                                    }
                                    else if (obsValCodedDT.getCode() != null
                                            && (!obsValCodedDT.getCode().equalsIgnoreCase("NI")))
                                    {
                                        treeMap.put("LAB121", obsValCodedDT.getCode());
                                    }
                                }
                            }

                            // NumericResultValue is a concatenated field
                            Collection<Object> obsValNumColl = itemObs.getTheObsValueNumericDTCollection();
                            for (Iterator<Object> obsValIter = obsValNumColl.iterator(); obsValIter.hasNext();)
                            {
                                ObsValueNumericDT obsValueNumericDT = (ObsValueNumericDT) obsValIter.next();
                                if (obsValueNumericDT != null)
                                {
                                    StringBuffer sb = new StringBuffer();
                                    if (obsValueNumericDT.getComparatorCd1() != null)
                                    {
                                        sb.append(obsValueNumericDT.getComparatorCd1());
                                    }
                                    if (obsValueNumericDT.getNumericValue1() != null)
                                    {
                                        sb.append((obsValueNumericDT.getNumericValue1()).toString());
                                    }
                                    if (obsValueNumericDT.getSeparatorCd() != null)
                                    {
                                        sb.append(obsValueNumericDT.getSeparatorCd());
                                    }
                                    if (obsValueNumericDT.getNumericValue2() != null)
                                    {
                                        sb.append(obsValueNumericDT.getNumericValue2());
                                    }
                                    // make a varisble for easy debugging
                                    String sNumericResultValue = sb.toString();
                                    treeMap.put("NumericResultValue", sNumericResultValue);
                                    treeMap.put("LAB115", obsValueNumericDT.getNumericUnitCd());
                                }
                            }

                            Collection<Object> obsValTxtColl = itemObs.getTheObsValueTxtDTCollection();
                            for (Iterator<Object> obsValIter = obsValTxtColl.iterator(); obsValIter.hasNext();)
                            {
                                ObsValueTxtDT obsValTxtDT = (ObsValueTxtDT) obsValIter.next();
                                if (obsValTxtDT != null)
                                {
                                    if (obsValTxtDT.getTxtTypeCd() != null && obsValTxtDT.getTxtTypeCd().equals("N"))
                                    {
                                        treeMap.put("LAB104", obsValTxtDT.getValueTxt());
                                    }
                                    else
                                    {
                                        treeMap.put("LAB208", obsValTxtDT.getValueTxt());
                                    }
                                }
                            }
                        }
                    }
                }
                BatchEntryHelper batchEntryHelper = new BatchEntryHelper();
                ArrayList<ObservationVO> obsInBatch = new ArrayList<ObservationVO>();
                obsInBatch.add((ObservationVO) treeMap.get("OrderedTest"));
                obsInBatch.add((ObservationVO) treeMap.get("ResultTest"));
                batchEntryHelper.setObservationVOCollection(obsInBatch);
                // load the treemap into the helper for use in submit
                batchEntryHelper.setTreeMap(treeMap);
                batchEntryHelper.setUid(obsVO.getTheObservationDT().getObservationUid());
                // must be "A" to display
                batchEntryHelper.setStatusCd("A");
                labResultsCollection.add(batchEntryHelper);
            }
        }
        // pass back Collection<Object> of BatchEntryHelpers to load to the form
        return labResultsCollection;
    }

    /**
     * BatchEntry Mapping for Treatment retrieve old collection from proxy then
     * create new collection to load to form
     */
    public Collection<Object> mapBatchEntryForTreatment(MorbidityProxyVO morbidityProxyVO)
    {
        Collection<Object> morbTreatmentColl = new ArrayList<Object>();
        Collection<Object> treatmentVOCollection = morbidityProxyVO.getTheTreatmentVOCollection();

        for (Iterator<Object> anIter = treatmentVOCollection.iterator(); anIter.hasNext();)
        {
            TreatmentVO treatVO = (TreatmentVO) anIter.next();
            TreeMap<Object, Object> treeMap = new TreeMap<Object, Object>();
            treeMap.put("TreatmentVO", treatVO);
            TreatmentDT treatmentDT = treatVO.getTheTreatmentDT();
            if (treatmentDT != null)
            {
                Long treatmetnUid = treatmentDT.getTreatmentUid();
                String sTreatmetnUid = " ";
                if (treatmetnUid != null)
                    sTreatmetnUid = treatmetnUid.toString();
                treeMap.put("TreatmentVOuid", sTreatmetnUid);
                treeMap.put("TRT100", StringUtils.formatDate(treatmentDT.getActivityToTime()) == null ? " "
                        : StringUtils.formatDate(treatmentDT.getActivityToTime()));
                treeMap.put("TRT101", treatmentDT.getCd() == null ? " " : treatmentDT.getCd());
                treeMap.put("TRT111", treatmentDT.getCdDescTxt() == null ? " " : treatmentDT.getCdDescTxt());
                treeMap.put("TRT109", treatmentDT.getTxt() == null ? " " : treatmentDT.getTxt());
            }

            Collection<Object> adminTreatColl = treatVO.getTheTreatmentAdministeredDTCollection();
            for (Iterator<Object> tIter = adminTreatColl.iterator(); tIter.hasNext();)
            {
                TreatmentAdministeredDT treatAdminDT = (TreatmentAdministeredDT) tIter.next();
                if (treatAdminDT != null)
                {
                    treeMap.put("TRT102", treatAdminDT.getCd() == null ? " " : treatAdminDT.getCd());
                    treeMap.put("TRT103", treatAdminDT.getDoseQty() == null ? " " : treatAdminDT.getDoseQty());
                    treeMap.put("TRT104",
                            treatAdminDT.getDoseQtyUnitCd() == null ? " " : treatAdminDT.getDoseQtyUnitCd());
                    treeMap.put("TRT110", treatAdminDT.getRouteCd() == null ? " " : treatAdminDT.getRouteCd());
                    treeMap.put("TRT105", treatAdminDT.getIntervalCd() == null ? " " : treatAdminDT.getIntervalCd());
                    treeMap.put(
                            "TRT107",
                            treatAdminDT.getEffectiveDurationAmt() == null ? " " : treatAdminDT
                                    .getEffectiveDurationAmt());
                    treeMap.put(
                            "TRT108",
                            treatAdminDT.getEffectiveDurationUnitCd() == null ? " " : treatAdminDT
                                    .getEffectiveDurationUnitCd());
                }
                break;
            }
            BatchEntryHelper batchEntryHelper = new BatchEntryHelper();
            ArrayList<Object> treatInBatch = new ArrayList<Object>();
            treatInBatch.add(treeMap.get("TreatmentVO"));
            batchEntryHelper.setTreatmentVOCollection(treatInBatch);
            // load the treemap into the helper for use in submit
            batchEntryHelper.setTreeMap(treeMap);
            batchEntryHelper.setUid(treatVO.getTheTreatmentDT().getTreatmentUid());
            // must be "A" to display
            batchEntryHelper.setStatusCd("A");
            morbTreatmentColl.add(batchEntryHelper);
        }

        // pass back Collection<Object> of BatchEntryHelpers to load to the form
        return morbTreatmentColl;
    }
    
    /**
     * mapBatchEntryForAttachment: //TODO: Fatima
     * @param morbidityProxyVO
     * @return
     */
    public Collection<Object> mapBatchEntryForAttachment(MorbidityProxyVO morbidityProxyVO, HttpServletRequest request)
    {
        Collection<Object> morbAttachmentColl = new ArrayList<Object>();
        Collection<Object> attachmentVOCollection = new ArrayList<Object>();
        ArrayList<Object> nbsCaseAttachmentDTColl =	(ArrayList<Object>) morbidityProxyVO.getTheAttachmentVOCollection();
      
        for(int i=0; i<nbsCaseAttachmentDTColl.size(); i++){
			
			AttachmentVO attachmentVO = new AttachmentVO();
			
			
			NBSAttachmentDT nbsAttachmentDT = (NBSAttachmentDT)nbsCaseAttachmentDTColl.get(i);
			AttachmentDT attachmentDT = new AttachmentDT();
			
			attachmentDT.setLastChangeTime(StringUtils.formatDate(nbsAttachmentDT.getLastChgTime()));
			attachmentDT.setLastChangeUserId(nbsAttachmentDT.getLastChgUserId());
			attachmentDT.setFileNmTxt(nbsAttachmentDT.getFileNmTxt());
			attachmentDT.setDescTxt(nbsAttachmentDT.getDescTxt());
			attachmentDT.setAttachmentData(nbsAttachmentDT.getAttachment());
		
			attachmentDT.setNbsAttachmentUid(nbsAttachmentDT.getNbsAttachmentUid());
			//attachmentDT.setAttachment(nbsAttachmentDT.getAttachment());

			
			
			attachmentVO.setTheAttachmentDT(attachmentDT);
			attachmentVOCollection.add(attachmentVO);
		}
        List<Object> list = (List<Object>)attachmentVOCollection;
        Collections.sort(list, new Comparator() {

            public int compare(Object arg0, Object arg1) {
                if (!(arg0 instanceof AttachmentVO)) {
                    return -1;
                }
                if (!(arg1 instanceof AttachmentVO)) {
                    return -1;
                }

                AttachmentVO pers0 = (AttachmentVO)arg0;
                AttachmentVO pers1 = (AttachmentVO)arg1;

                return (int) (pers0.getTheAttachmentDT().getNbsAttachmentUid() - pers1.getTheAttachmentDT().getNbsAttachmentUid());
            }
        });
        for (Iterator<Object> anIter = attachmentVOCollection.iterator(); anIter.hasNext();)
        {
            AttachmentVO attachVO = (AttachmentVO) anIter.next();
            TreeMap<Object, Object> treeMap = new TreeMap<Object, Object>();
            treeMap.put("AttachmentVO", attachVO);
            AttachmentDT attachmentDT = attachVO.getTheAttachmentDT();
            if (attachmentDT != null)
            {
            	HttpSession session = request.getSession();
            	NBSSecurityObj secObj = (NBSSecurityObj) session.getAttribute("NBSSecurityObject");
            	String userName = getUserName(secObj, attachmentDT.getLastChangeUserId());
                Long attachmentUid = attachmentDT.getNbsAttachmentUid();
                String sAttachmentUid = " ";
                if (attachmentUid != null)
                    sAttachmentUid = attachmentUid.toString();
                treeMap.put("AttachmentVOuid", sAttachmentUid);
                treeMap.put("addTime", attachmentDT.getLastChangeTime() == null ? " "
                        : attachmentDT.getLastChangeTime());
                treeMap.put("userId", attachmentDT.getLastChangeUserId() == null ? " " : attachmentDT.getLastChangeUserId());
                treeMap.put("userName", userName == null ? " " : userName);//TODO:  Fatima
                treeMap.put("attachmentName", attachmentDT.getFileNmTxt() == null ? " " : attachmentDT.getFileNmTxt());
                treeMap.put("attachmentDescription", attachmentDT.getDescTxt() == null ? " " : attachmentDT.getDescTxt());
                treeMap.put("attachmentFile", attachmentDT.getAttachment() == null ? " " : attachmentDT.getAttachment());
                
            }
            /*
            Collection<Object> adminTreatColl = attachVO.getTheTreatmentAdministeredDTCollection();
            for (Iterator<Object> tIter = adminTreatColl.iterator(); tIter.hasNext();)
            {
                TreatmentAdministeredDT treatAdminDT = (TreatmentAdministeredDT) tIter.next();
                if (treatAdminDT != null)
                {
                    treeMap.put("TRT102", treatAdminDT.getCd() == null ? " " : treatAdminDT.getCd());
                    treeMap.put("TRT103", treatAdminDT.getDoseQty() == null ? " " : treatAdminDT.getDoseQty());
                    treeMap.put("TRT104",
                            treatAdminDT.getDoseQtyUnitCd() == null ? " " : treatAdminDT.getDoseQtyUnitCd());
                    treeMap.put("TRT110", treatAdminDT.getRouteCd() == null ? " " : treatAdminDT.getRouteCd());
                    treeMap.put("TRT105", treatAdminDT.getIntervalCd() == null ? " " : treatAdminDT.getIntervalCd());
                    treeMap.put(
                            "TRT107",
                            treatAdminDT.getEffectiveDurationAmt() == null ? " " : treatAdminDT
                                    .getEffectiveDurationAmt());
                    treeMap.put(
                            "TRT108",
                            treatAdminDT.getEffectiveDurationUnitCd() == null ? " " : treatAdminDT
                                    .getEffectiveDurationUnitCd());
                }
                break;
            }*/
            BatchEntryHelper batchEntryHelper = new BatchEntryHelper();
            ArrayList<Object> attachInBatch = new ArrayList<Object>();
            attachInBatch.add(treeMap.get("AttachmentVO"));
            batchEntryHelper.setTheAttachmentCollection(attachInBatch);
            // load the treemap into the helper for use in submit
            batchEntryHelper.setTreeMap(treeMap);
            batchEntryHelper.setUid(attachVO.getTheAttachmentDT().getNbsAttachmentUid());
            // must be "A" to display
            batchEntryHelper.setStatusCd("A");
            morbAttachmentColl.add(batchEntryHelper);
        }

        // pass back Collection<Object> of BatchEntryHelpers to load to the form
        return morbAttachmentColl;
    }

    /**
     * getUserName: this method received the userId and returns the user name and user last name
     * @param userId
     * @return
     */
    private String getUserName(NBSSecurityObj secObj, Long userId) {
		TreeMap<String,String> treeMap = new TreeMap<>();
		Collection<Object> secureUSerDTColl = new ArrayList<Object>();
		String userName = "";
		
		UserProfile userProfile = secObj.getTheUserProfile();
		Collection<Object> roles = userProfile.getTheRealizedRoleCollection();
		String programAreas="";
		
	    for(Iterator<Object> it = roles.iterator(); it.hasNext(); )
	    {
	    	RealizedRole realizedRole = (RealizedRole)it.next();
	    	programAreas+="'"+realizedRole.getProgramAreaCode()+"'";
	    	if(it.hasNext())
	    		programAreas+=",";
	    }
			DbAuthDAOImpl secureDAOImpl = new DbAuthDAOImpl();
			secureUSerDTColl = secureDAOImpl.getSecureUserDTListBasedOnProgramArea(programAreas);
			
			 if(secureUSerDTColl!=null && secureUSerDTColl.size()>0)
			  {
			  	Iterator ite = secureUSerDTColl.iterator();
			  	while(ite.hasNext() && userName.isEmpty()){
	    			AuthUserDT secureUserDT = (AuthUserDT)ite.next();
	    			
	    			if(secureUserDT.getNedssEntryId()!=null && secureUserDT.getNedssEntryId().equals(userId))
	    				userName = secureUserDT.getUserFirstNm() + " " + secureUserDT.getUserLastNm();
	    			
		      }
		    }
		  
			 return userName;
	}
    
    public void setPredefinedTreatment(Collection<Object> treatmentCollection)
    {
        // set the predefined fields
        TreatmentVO treatmentVO = null;
        Iterator<Object> itor = null;
        if (treatmentCollection != null && treatmentCollection.size() > 0)
        {
            itor = treatmentCollection.iterator();
            while (itor.hasNext())
            {
                treatmentVO = (TreatmentVO) itor.next();
                if (treatmentVO.getTheTreatmentDT().getCd() != null
                        && !treatmentVO.getTheTreatmentDT().getCd().equals("OTH"))
                {
                    CachedDropDownValues cachedDropDownValues = new CachedDropDownValues();
                    PreDefinedTreatmentDT preDefinedTreatmentDT = null;
                    preDefinedTreatmentDT = cachedDropDownValues.getPreDefinedTreatmentDT(treatmentVO
                            .getTheTreatmentDT().getCd());
                    treatmentVO.getTreatmentAdministeredDT_s(0).setCd(treatmentVO.getTheTreatmentDT().getCd());
                    treatmentVO.getTreatmentAdministeredDT_s(0).setDoseQty(preDefinedTreatmentDT.getDoseQty());
                    treatmentVO.getTreatmentAdministeredDT_s(0).setDoseQtyUnitCd(
                            preDefinedTreatmentDT.getDoseQtyUnitCd());
                    treatmentVO.getTreatmentAdministeredDT_s(0).setRouteCd(preDefinedTreatmentDT.getRouteCd());
                    treatmentVO.getTreatmentAdministeredDT_s(0).setIntervalCd(preDefinedTreatmentDT.getIntervalCd());
                    treatmentVO.getTreatmentAdministeredDT_s(0).setEffectiveDurationAmt(
                            preDefinedTreatmentDT.getDurationAmt());
                    treatmentVO.getTreatmentAdministeredDT_s(0).setEffectiveDurationUnitCd(
                            preDefinedTreatmentDT.getDurationUnitCd());
                }
            }
        }
    }

    /**
     * Access to get observation UID from observationcollection object with
     * observationUID
     * 
     * @param Collection
     *            <Object> the theObservationVOCollection
     * @param Long
     *            the observationUid
     * @return ObservationVO object
     */
    public ObservationVO getObservationByUid(Collection<ObservationVO> theObservationVOCollection, Long observationUid)
    {
        ObservationVO observationVO = null;
        Iterator<ObservationVO> anIterator = null;

        if (theObservationVOCollection != null)
        {
            anIterator = theObservationVOCollection.iterator();
            while (anIterator.hasNext())
            {
                observationVO = (ObservationVO) anIterator.next();
                if (observationVO.getTheObservationDT().getObservationUid() != null
                        && observationVO.getTheObservationDT().getObservationUid().equals(observationUid))
                {
                    logger.debug("getObservationByUid: " + observationVO.getTheObservationDT().getObservationUid());
                    return observationVO;
                }
            }
        }
        return null;
    }

    /**
     * @param InvestigationProxyVO
     *            the investigationProxyVO
     * @param HttpServletRequest
     *            the request
     */
    public void convertBatchEntryToRequest(Collection<Object> labResultsCollection, HttpServletRequest request)
    {
        StringBuffer sb = new StringBuffer("");
        Iterator<Object> itr = labResultsCollection.iterator();
        while (itr.hasNext())
        {
            BatchEntryHelper bh = (BatchEntryHelper) itr.next();
            if (bh.getTreeMap() != null)
            {
                TreeMap<Object, Object> treeMap = bh.getTreeMap();
                sb.append("labResults_s[i].observationVO_s[0].theObservationDT.effectiveFromTime_s");
                sb.append(NEDSSConstants.BATCH_PART).append(StringUtils.formatDate((Timestamp) treeMap.get("LAB163")))
                        .append(NEDSSConstants.BATCH_SECT);

                sb.append("labResults_s[i].observationVO_s[0].theObservationDT.activityToTime_s");
                sb.append(NEDSSConstants.BATCH_PART).append(StringUtils.formatDate((Timestamp) treeMap.get("LAB197")))
                        .append(NEDSSConstants.BATCH_SECT);

                sb.append("labResults_s[i].observationVO_s[0].theObservationDT.txt");
                sb.append(NEDSSConstants.BATCH_PART).append(this.replaceNull((treeMap.get("LAB266"))))
                        .append(NEDSSConstants.BATCH_SECT);

                sb.append("labResults_s[i].observationVO_s[1].theObservationDT.cdDescTxt");
                sb.append(NEDSSConstants.BATCH_PART).append(this.replaceNull(treeMap.get("LAB220")))
                        .append(NEDSSConstants.BATCH_SECT);

                sb.append("labResults_s[i].observationVO_s[1].obsValueCodedDT_s[0].displayName");
                sb.append(NEDSSConstants.BATCH_PART).append(this.replaceNull(treeMap.get("LAB278")))
                        .append(NEDSSConstants.BATCH_SECT);

                sb.append("labResults_s[i].observationVO_s[1].obsValueCodedDT_s[0].hiddenCd");
                sb.append(NEDSSConstants.BATCH_PART).append(this.replaceNull(treeMap.get("orgDescTxt")))
                        .append(NEDSSConstants.BATCH_SECT);

                sb.append("labResults_s[i].observationVO_s[1].obsValueCodedDT_s[0].cdSystemCdRT");
                sb.append(NEDSSConstants.BATCH_PART).append(this.replaceNull(treeMap.get("codeSystemCd")))
                        .append(NEDSSConstants.BATCH_SECT);

                sb.append("labResults_s[i].observationVO_s[1].obsValueCodedDT_s[0].searchResultRT");
                sb.append(NEDSSConstants.BATCH_PART).append(this.replaceNull(treeMap.get("searchResultRT")))
                        .append(NEDSSConstants.BATCH_SECT);

                sb.append("labResults_s[i].observationVO_s[1].obsValueCodedDT_s[0].code");
                sb.append(NEDSSConstants.BATCH_PART).append(this.replaceNull(treeMap.get("LAB121")))
                        .append(NEDSSConstants.BATCH_SECT);

                sb.append("labResults_s[i].observationVO_s[1].obsValueNumericDT_s[0].numericValue");
                sb.append(NEDSSConstants.BATCH_PART).append(this.replaceNull(treeMap.get("NumericResultValue")))
                        .append(NEDSSConstants.BATCH_SECT);

                sb.append("labResults_s[i].observationVO_s[1].obsValueNumericDT_s[0].numericUnitCd");
                sb.append(NEDSSConstants.BATCH_PART).append(this.replaceNull(treeMap.get("LAB115")))
                        .append(NEDSSConstants.BATCH_SECT);

                sb.append("labResults_s[i].observationVO_s[1].obsValueTxtDT_s[0].valueTxt");
                sb.append(NEDSSConstants.BATCH_PART).append(this.replaceNull(treeMap.get("LAB208")))
                        .append(NEDSSConstants.BATCH_SECT);

                sb.append("labResults_s[i].observationVO_s[1].obsValueTxtDT_s[1].valueTxt");
                sb.append(NEDSSConstants.BATCH_PART).append(this.replaceNull(treeMap.get("LAB104")))
                        .append(NEDSSConstants.BATCH_SECT);

                sb.append("labResults_s[i].statusCd");
                sb.append(NEDSSConstants.BATCH_PART).append("A").append(NEDSSConstants.BATCH_SECT);

                sb.append("labResults_s[i].observationVO_s[1].theObservationDT.ctrlCdUserDefined1");
                sb.append(NEDSSConstants.BATCH_PART).append(this.replaceNull(treeMap.get("CtrlCdUserDefined1")))
                        .append(NEDSSConstants.BATCH_SECT);

                sb.append(NEDSSConstants.BATCH_LINE);
            }
        }
        String sbToString = sb.toString();
        request.setAttribute("labResultsBatch", sbToString);
    }

    public void convertTreatmentBatchEntryToRequest(Collection<Object> morbTreatmentColl, HttpServletRequest request)
    {
        StringBuffer sb = new StringBuffer("");
        Iterator<Object> itr = morbTreatmentColl.iterator();
        while (itr.hasNext())
        {
            BatchEntryHelper bh = (BatchEntryHelper) itr.next();
            if (bh.getTreeMap() != null)
            {
                TreeMap<Object, Object> treeMap = bh.getTreeMap();

                sb.append("treatment_s[i].treatmentVO_s[0].treatmentAdministeredDT_s[0].effectiveFromTime_s");
                sb.append(NEDSSConstants.BATCH_PART).append(treeMap.get("TRT100")).append(NEDSSConstants.BATCH_SECT);

                sb.append("treatment_s[i].treatmentVO_s[0].theTreatmentDT.cd");
                sb.append(NEDSSConstants.BATCH_PART).append(treeMap.get("TRT101")).append(NEDSSConstants.BATCH_SECT);

                sb.append("treatment_s[i].treatmentVO_s[0].theTreatmentDT.cdDescTxt");
                sb.append(NEDSSConstants.BATCH_PART).append(this.replaceNull(treeMap.get("TRT111")))
                        .append(NEDSSConstants.BATCH_SECT);

                sb.append("treatment_s[i].treatmentVO_s[0].treatmentAdministeredDT_s[0].cd");
                sb.append(NEDSSConstants.BATCH_PART).append(treeMap.get("TRT102")).append(NEDSSConstants.BATCH_SECT);

                sb.append("treatment_s[i].treatmentVO_s[0].treatmentAdministeredDT_s[0].doseQty");
                sb.append(NEDSSConstants.BATCH_PART).append(treeMap.get("TRT103")).append(NEDSSConstants.BATCH_SECT);

                sb.append("treatment_s[i].treatmentVO_s[0].treatmentAdministeredDT_s[0].doseQtyUnitCd");
                sb.append(NEDSSConstants.BATCH_PART).append(treeMap.get("TRT104")).append(NEDSSConstants.BATCH_SECT);

                sb.append("treatment_s[i].treatmentVO_s[0].treatmentAdministeredDT_s[0].routeCd");
                sb.append(NEDSSConstants.BATCH_PART).append(treeMap.get("TRT110")).append(NEDSSConstants.BATCH_SECT);

                sb.append("treatment_s[i].treatmentVO_s[0].treatmentAdministeredDT_s[0].intervalCd");
                sb.append(NEDSSConstants.BATCH_PART).append(treeMap.get("TRT105")).append(NEDSSConstants.BATCH_SECT);

                sb.append("treatment_s[i].treatmentVO_s[0].treatmentAdministeredDT_s[0].effectiveDurationAmt");
                sb.append(NEDSSConstants.BATCH_PART).append(treeMap.get("TRT107")).append(NEDSSConstants.BATCH_SECT);

                sb.append("treatment_s[i].treatmentVO_s[0].treatmentAdministeredDT_s[0].effectiveDurationUnitCd");
                sb.append(NEDSSConstants.BATCH_PART).append(treeMap.get("TRT108")).append(NEDSSConstants.BATCH_SECT);

                sb.append("treatment_s[i].treatmentVO_s[0].theTreatmentDT.txt");
                sb.append(NEDSSConstants.BATCH_PART).append(this.replaceNull(treeMap.get("TRT109")))
                        .append(NEDSSConstants.BATCH_SECT);

                sb.append("treatment_s[i].statusCd");
                sb.append(NEDSSConstants.BATCH_PART).append("A").append(NEDSSConstants.BATCH_SECT);
                sb.append(NEDSSConstants.BATCH_LINE);
            }
        }
        String sbToString = sb.toString();
        request.setAttribute("treatmentResultsBatch", sbToString);
    }

    public void convertAttachmentBatchEntryToRequest(Collection<Object> morbAttachmentColl, HttpServletRequest request)
    {
        StringBuffer sb = new StringBuffer("");
        Iterator<Object> itr = morbAttachmentColl.iterator();
        while (itr.hasNext())
        {
            BatchEntryHelper bh = (BatchEntryHelper) itr.next();
            if (bh.getTreeMap() != null)
            {
                TreeMap<Object, Object> treeMap = bh.getTreeMap();

                sb.append("attachment_s[i].attachmentVO_s[0].theAttachmentDT.fileNmTxt");
                sb.append(NEDSSConstants.BATCH_PART).append(treeMap.get("attachmentName")).append(NEDSSConstants.BATCH_SECT);

                sb.append("attachment_s[i].attachmentVO_s[0].theAttachmentDT.descTxt");
                sb.append(NEDSSConstants.BATCH_PART).append(treeMap.get("attachmentDescription")).append(NEDSSConstants.BATCH_SECT);

                sb.append("attachment_s[i].attachmentVO_s[0].theAttachmentDT.attachment");
                sb.append(NEDSSConstants.BATCH_PART).append(this.replaceNull(treeMap.get("attachmentFile")))
                        .append(NEDSSConstants.BATCH_SECT);

                sb.append("attachment_s[i].attachmentVO_s[0].theAttachmentDT.lastChangeTime");
                sb.append(NEDSSConstants.BATCH_PART).append(treeMap.get("addTime")).append(NEDSSConstants.BATCH_SECT);

                sb.append("attachment_s[i].attachmentVO_s[0].theAttachmentDT.lastChgUserNm");
                sb.append(NEDSSConstants.BATCH_PART).append(treeMap.get("userName")).append(NEDSSConstants.BATCH_SECT);

                sb.append("attachment_s[i].attachmentVO_s[0].theAttachmentDT.lastChangeUserId");
                sb.append(NEDSSConstants.BATCH_PART).append(treeMap.get("userId")).append(NEDSSConstants.BATCH_SECT);
/*
                sb.append("treatment_s[i].treatmentVO_s[0].treatmentAdministeredDT_s[0].routeCd");
                sb.append(NEDSSConstants.BATCH_PART).append(treeMap.get("TRT110")).append(NEDSSConstants.BATCH_SECT);

                sb.append("treatment_s[i].treatmentVO_s[0].treatmentAdministeredDT_s[0].intervalCd");
                sb.append(NEDSSConstants.BATCH_PART).append(treeMap.get("TRT105")).append(NEDSSConstants.BATCH_SECT);

                sb.append("treatment_s[i].treatmentVO_s[0].treatmentAdministeredDT_s[0].effectiveDurationAmt");
                sb.append(NEDSSConstants.BATCH_PART).append(treeMap.get("TRT107")).append(NEDSSConstants.BATCH_SECT);

                sb.append("treatment_s[i].treatmentVO_s[0].treatmentAdministeredDT_s[0].effectiveDurationUnitCd");
                sb.append(NEDSSConstants.BATCH_PART).append(treeMap.get("TRT108")).append(NEDSSConstants.BATCH_SECT);

                sb.append("treatment_s[i].treatmentVO_s[0].theTreatmentDT.txt");
                sb.append(NEDSSConstants.BATCH_PART).append(this.replaceNull(treeMap.get("TRT109")))
                        .append(NEDSSConstants.BATCH_SECT);*/
                sb.append("attachment_s[i].attachmentVO_s[0].theAttachmentDT.nbsAttachmentUid");
                sb.append(NEDSSConstants.BATCH_PART).append(treeMap.get("AttachmentVOuid")).append(NEDSSConstants.BATCH_SECT);
                
                sb.append("attachment_s[i].statusCd"); //Changed to attachment_s[i].statusCd from treatment_s[i].statusCd 
                sb.append(NEDSSConstants.BATCH_PART).append("A").append(NEDSSConstants.BATCH_SECT);
                sb.append(NEDSSConstants.BATCH_LINE);
            }
        }
        String sbToString = sb.toString();
        request.setAttribute("attachmentsBatch", sbToString);
    }
    
    public void setPatientRevisionForCreate(Long mprUid, Long patientSubjectUid, String patientLocalId,
            MorbidityProxyVO morbidityProxyVO, MorbidityForm form, HttpServletRequest request)
    {

        PersonVO patient = null;
        Collection<Object> patientColl = new ArrayList<Object>();

        if (form.getPatient() != null)
        {
            patient = form.getPatient();
            patient.getThePersonDT().setPersonParentUid(mprUid);
            patient.getThePersonDT().setPersonUid(patientSubjectUid);
            patient.getThePersonDT().setLocalId(patientLocalId);
            patient.getThePersonDT().setCd(NEDSSConstants.PAT);
            patient.getThePersonDT().setElectronicInd(NEDSSConstants.ELECTRONIC_IND);
            patient.getThePersonDT().setRecordStatusCd("ACTIVE");
            patient.getThePersonDT().setStatusCd("A");
            patient.getThePersonDT().setStatusTime(new java.sql.Timestamp(new Date().getTime()));
            patient.getThePersonDT().setRecordStatusTime(new java.sql.Timestamp(new Date().getTime()));

            patient.getThePersonDT().setItNew(true);
            patient.getThePersonDT().setItDirty(false);

            PersonUtil.setPatientForEventCreate(patient, request);

            patient.setItNew(true);
            patient.setItDirty(false);

            patientColl.add(patient);

            if (patientColl.size() > 0)
            {
                morbidityProxyVO.setThePersonVOCollection(patientColl);
            }
        }
    } // createRevisionPatient

    public void setPatientRevisionForEdit(Long mprUid, Long patientSubjectUid, MorbidityProxyVO newProxyVO,
            MorbidityForm form, HttpServletRequest request)
    {

        PersonVO patient = null;
        Collection<Object> patientColl = new ArrayList<Object>();

        if (form.getPatient() != null)
        {
            patient = form.getPatient();
            patient.getThePersonDT().setCd(NEDSSConstants.PAT);
            patient.getThePersonDT().setElectronicInd(NEDSSConstants.ELECTRONIC_IND);
            patient.getThePersonDT().setRecordStatusCd("ACTIVE");
            patient.getThePersonDT().setStatusCd("A");
            patient.getThePersonDT().setStatusTime(new java.sql.Timestamp(new Date().getTime()));
            patient.getThePersonDT().setRecordStatusTime(new java.sql.Timestamp(new Date().getTime()));

            patient.getThePersonDT().setItNew(false);
            patient.getThePersonDT().setItDirty(true);

            PersonUtil.setPatientForEventEdit(patient, request);

            patient.setItNew(false);
            patient.setItDirty(true);

            patientColl.add(patient);

            if (patientColl.size() > 0)
            {
                newProxyVO.setThePersonVOCollection(patientColl);
            }
        }
    } // editRevisionPatient

    private Collection<Object> deleteRowActRelationshipAndObservations(ObservationVO formObservationVO,
            BatchEntryHelper batchEntryHelper)
    {
        Collection<Object> actRelationshipDeletedColl = new ArrayList<Object>();
        Collection<Object> mainActrelationshipCollection = formObservationVO.getTheActRelationshipDTCollection();
        if (mainActrelationshipCollection != null)
        {
            Long rowObservationUid = batchEntryHelper.getUid();
            if (rowObservationUid != null)
            {
                Iterator<Object> rowItor = mainActrelationshipCollection.iterator();
                while (rowItor.hasNext())
                {
                    ActRelationshipDT actRelationshipDT = (ActRelationshipDT) rowItor.next();
                    if (actRelationshipDT.getSourceActUid().longValue() == rowObservationUid.longValue())
                    {
                        actRelationshipDT.setItDelete(true);
                        actRelationshipDT.setItDirty(false);
                        actRelationshipDT.setItNew(false);
                        actRelationshipDeletedColl.add(actRelationshipDT);
                        break;
                    }
                }
            } // end of rowObservationUid if
        }
        return actRelationshipDeletedColl;
    }

    public boolean isNewOrderTest(BatchEntryHelper helper)
    {
        Collection<ObservationVO> helperColl = helper.getObservationVOCollection();
        if (helperColl != null)
        {

            for (Iterator<ObservationVO> helperIter = helperColl.iterator(); helperIter.hasNext();)
            {
                ObservationVO helperObsVO = (ObservationVO) helperIter.next();
                if (helperObsVO != null)
                {
                    ObservationDT observationDT = helperObsVO.getTheObservationDT();
                    if (observationDT != null)
                    {
                        if (observationDT.getObservationUid() == null
                                || (observationDT.getObservationUid().longValue() < 0))
                        {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public ObservationVO findOrderTest(BatchEntryHelper helper)
    {
        Collection<ObservationVO> helperColl = helper.getObservationVOCollection();
        if (helperColl != null)
        {

            for (Iterator<ObservationVO> helperIter = helperColl.iterator(); helperIter.hasNext();)
            {
                ObservationVO helperObsVO = (ObservationVO) helperIter.next();
                if (helperObsVO != null)
                {
                    ObservationDT observationDT = helperObsVO.getTheObservationDT();
                    if (observationDT != null && observationDT.getObsDomainCd().equalsIgnoreCase("Order")
                            && observationDT.getObservationUid() != null)
                    {
                        observationDT.setItDirty(true);
                        observationDT.setItNew(false);
                        helperObsVO.setItDirty(true);
                        helperObsVO.setItNew(false);
                        return helperObsVO;
                    }
                }
            }
        }
        return null;
    }

    public boolean findDeleteOrderTest(BatchEntryHelper helper)
    {
        if (helper.getStatusCd() != null && helper.getStatusCd().trim().equals("I"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public Long getMorbReportObsUidFromProxy(MorbidityProxyVO proxyVO)
    {
        Collection<ObservationVO> observationColl = proxyVO.getTheObservationVOCollection();
        for (Iterator<ObservationVO> it = observationColl.iterator(); it.hasNext();)
        {
            ObservationVO observationVO = (ObservationVO) it.next();
            if (observationVO != null
                    && observationVO.getTheObservationDT().getCtrlCdDisplayForm()
                            .equalsIgnoreCase(NEDSSConstants.MORBIDITY_CODE))
            {
                return observationVO.getTheObservationDT().getObservationUid();
            }
        }
        return null;
    }

    public void loadToTheForm(MorbidityForm form, MorbidityProxyVO proxyVO)
    {
        form.reset();
        form.setMorbidityProxy(proxyVO);
        form.setPatient(findPatientRevision(proxyVO));
        form.setMorbidityReport(getMorbReportObsVO(proxyVO));

    }

    public ObservationVO getMorbReportObsVO(MorbidityProxyVO proxyVO)
    {
        Collection<ObservationVO> observationColl = proxyVO.getTheObservationVOCollection();
        for (Iterator<ObservationVO> it = observationColl.iterator(); it.hasNext();)
        {
            ObservationVO observationVO = (ObservationVO) it.next();
            if (observationVO != null
                    && observationVO.getTheObservationDT().getCtrlCdDisplayForm() != null
                    && observationVO.getTheObservationDT().getCtrlCdDisplayForm()
                            .equalsIgnoreCase(NEDSSConstants.MORBIDITY_CODE))
            {
                return observationVO;
            }
        }
        return null;
    }

    public RoleDT getRoleForEdit(MorbidityProxyVO proxyVO, Long subjectUid)
    {
        Collection<Object> roleColl = proxyVO.getTheRoleDTCollection();
        for (Iterator<Object> it = roleColl.iterator(); it.hasNext();)
        {
            RoleDT roleDT = (RoleDT) it.next();
            if (roleDT != null && roleDT.getSubjectEntityUid().equals(subjectUid))
            {
                roleDT.setStatusTime(new java.sql.Timestamp(new Date().getTime()));
                roleDT.setRecordStatusTime(new java.sql.Timestamp(new Date().getTime()));
                roleDT.setItNew(false);
                roleDT.setItDirty(true);
                return roleDT;
            }
            else
            {
                roleDT.setSubjectEntityUid(subjectUid);
                roleDT.setStatusTime(new java.sql.Timestamp(new Date().getTime()));
                roleDT.setRecordStatusTime(new java.sql.Timestamp(new Date().getTime()));
                roleDT.setItNew(false);
                roleDT.setItDirty(true);
                return roleDT;
            }
        }
        return null;
    }

    public ParticipationDT getPartForEdit(MorbidityProxyVO proxyVO, Long observationUid, Long subjectUid,
            String subjectClassCd, String typeCd)
    {
        Collection<Object> partColl = proxyVO.getTheParticipationDTCollection();
        for (Iterator<Object> it = partColl.iterator(); it.hasNext();)
        {
            ParticipationDT participationDT = (ParticipationDT) it.next();
            if (participationDT != null && participationDT.getActUid().equals(observationUid)
                    && participationDT.getSubjectClassCd().equals(subjectClassCd)
                    && participationDT.getTypeCd().equals(typeCd)
                    && participationDT.getSubjectEntityUid().equals(subjectUid))
            {
                participationDT.setStatusTime(new java.sql.Timestamp(new Date().getTime()));
                participationDT.setRecordStatusTime(new java.sql.Timestamp(new Date().getTime()));
                participationDT.setItNew(false);
                participationDT.setItDirty(true);
                return participationDT;
            }
            else if (participationDT != null && participationDT.getActUid().equals(observationUid)
                    && participationDT.getSubjectClassCd().equals(subjectClassCd)
                    && participationDT.getTypeCd().equals(typeCd)
                    && (!participationDT.getSubjectEntityUid().equals(subjectUid)))
            {
                participationDT.setStatusTime(new java.sql.Timestamp(new Date().getTime()));
                participationDT.setRecordStatusTime(new java.sql.Timestamp(new Date().getTime()));
                participationDT.setSubjectEntityUid(subjectUid);
                participationDT.setItNew(false);
                participationDT.setItDirty(true);
                return participationDT;
            }
        }
        return null;
    }

    public void getPartForDelete(ObservationVO orderedTest, Long observationUid)
    {
        Collection<Object> partColl = orderedTest.getTheParticipationDTCollection();
        for (Iterator<Object> it = partColl.iterator(); it.hasNext();)
        {
            ParticipationDT participationDT = (ParticipationDT) it.next();
            if (participationDT != null)
            {
                participationDT.setStatusTime(new java.sql.Timestamp(new Date().getTime()));
                participationDT.setRecordStatusTime(new java.sql.Timestamp(new Date().getTime()));
                participationDT.setItNew(false);
                participationDT.setItDirty(false);
                participationDT.setItDelete(true);
                break;
            }
        }

    }

    public Collection<Object> getPartForDeleteTreatment(TreatmentVO treatmentVO)
    {
        ArrayList<Object> arrayList = new ArrayList<Object>();
        Collection<Object> partColl = treatmentVO.getTheParticipationDTCollection();
        for (Iterator<Object> it = partColl.iterator(); it.hasNext();)
        {
            ParticipationDT participationDT = (ParticipationDT) it.next();
            if (participationDT != null)
            {
                participationDT.setStatusTime(new java.sql.Timestamp(new Date().getTime()));
                participationDT.setRecordStatusTime(new java.sql.Timestamp(new Date().getTime()));
                participationDT.setItNew(false);
                participationDT.setItDirty(false);
                participationDT.setItDelete(true);
                arrayList.add(participationDT);
            }
        }
        return arrayList;
    }

    public Collection<Object> getActForDelete(ObservationVO obsVO, Long orderedTestUid)
    {
        ArrayList<Object> arrayList = new ArrayList<Object>();
        Collection<Object> actColl = obsVO.getTheActRelationshipDTCollection();
        for (Iterator<Object> it = actColl.iterator(); it.hasNext();)
        {
            ActRelationshipDT actRelationshipDT = (ActRelationshipDT) it.next();
            if (actRelationshipDT != null && actRelationshipDT.getTypeCd().equalsIgnoreCase(NEDSSConstants.LAB_REPORT)
                    && actRelationshipDT.getSourceActUid().equals(orderedTestUid))
            {
                actRelationshipDT.setStatusTime(new java.sql.Timestamp(new Date().getTime()));
                actRelationshipDT.setRecordStatusTime(new java.sql.Timestamp(new Date().getTime()));
                actRelationshipDT.setItNew(false);
                actRelationshipDT.setItDirty(false);
                actRelationshipDT.setItDelete(true);
                arrayList.add(actRelationshipDT);
                obsVO.setItDirty(true);
            }
            else if (actRelationshipDT != null
                    && actRelationshipDT.getTypeCd().equalsIgnoreCase(NEDSSConstants.ACT108_TYP_CD)
                    && actRelationshipDT.getTargetActUid().equals(orderedTestUid))
            {
                actRelationshipDT.setStatusTime(new java.sql.Timestamp(new Date().getTime()));
                actRelationshipDT.setRecordStatusTime(new java.sql.Timestamp(new Date().getTime()));
                actRelationshipDT.setItNew(false);
                actRelationshipDT.setItDirty(false);
                actRelationshipDT.setItDelete(true);
                arrayList.add(actRelationshipDT);
            }
        }
        return arrayList;
    }

    public Collection<Object> getActForDeleteTreatment(TreatmentVO treVO)
    {
        ArrayList<Object> arrayList = new ArrayList<Object>();
        Collection<Object> actColl = treVO.getTheActRelationshipDTCollection();
        for (Iterator<Object> it = actColl.iterator(); it.hasNext();)
        {
            ActRelationshipDT actRelationshipDT = (ActRelationshipDT) it.next();
            if (actRelationshipDT != null)
            {
                actRelationshipDT.setStatusTime(new java.sql.Timestamp(new Date().getTime()));
                actRelationshipDT.setRecordStatusTime(new java.sql.Timestamp(new Date().getTime()));
                actRelationshipDT.setItNew(false);
                actRelationshipDT.setItDirty(false);
                actRelationshipDT.setItDelete(true);
                arrayList.add(actRelationshipDT);
                treVO.setItDirty(true);
            }
        }
        return arrayList;
    }

    public Collection<Object> getActForEdit(MorbidityProxyVO proxyVO, String typeCd)
    {
        ArrayList<Object> arrayList = new ArrayList<Object>();
        Collection<Object> actColl = proxyVO.getTheActRelationshipDTCollection();
        for (Iterator<Object> it = actColl.iterator(); it.hasNext();)
        {
            ActRelationshipDT actRelationshipDT = (ActRelationshipDT) it.next();
            if (actRelationshipDT != null && actRelationshipDT.getTypeCd().equalsIgnoreCase(typeCd))
            {
                actRelationshipDT.setStatusTime(new java.sql.Timestamp(new Date().getTime()));
                actRelationshipDT.setRecordStatusTime(new java.sql.Timestamp(new Date().getTime()));
                actRelationshipDT.setItNew(false);
                actRelationshipDT.setItDirty(true);
                arrayList.add(actRelationshipDT);
            }
        }
        return arrayList;
    }

    public Collection<Object> getOTActForUpdate(MorbidityProxyVO proxyVO, Long observationUid, String typeCd)
    {
        ArrayList<Object> arrayList = new ArrayList<Object>();
        Collection<Object> actColl = proxyVO.getTheActRelationshipDTCollection();
        for (Iterator<Object> it = actColl.iterator(); it.hasNext();)
        {
            ActRelationshipDT actRelationshipDT = (ActRelationshipDT) it.next();
            if (actRelationshipDT != null && actRelationshipDT.getSourceActUid().equals(observationUid)
                    && actRelationshipDT.getTypeCd().equalsIgnoreCase(typeCd))
            {
                actRelationshipDT.setStatusTime(new java.sql.Timestamp(new Date().getTime()));
                actRelationshipDT.setRecordStatusTime(new java.sql.Timestamp(new Date().getTime()));
                actRelationshipDT.setItNew(false);
                actRelationshipDT.setItDirty(true);
                arrayList.add(actRelationshipDT);
            }
        }
        return arrayList;
    }

    public Collection<Object> getRTActForUpdate(MorbidityProxyVO proxyVO, Long observationUid, String typeCd)
    {
        ArrayList<Object> arrayList = new ArrayList<Object>();
        Collection<Object> actColl = proxyVO.getTheActRelationshipDTCollection();
        for (Iterator<Object> it = actColl.iterator(); it.hasNext();)
        {
            ActRelationshipDT actRelationshipDT = (ActRelationshipDT) it.next();
            if (actRelationshipDT != null && actRelationshipDT.getTargetActUid().equals(observationUid)
                    && actRelationshipDT.getTypeCd().equalsIgnoreCase(typeCd))
            {
                actRelationshipDT.setStatusTime(new java.sql.Timestamp(new Date().getTime()));
                actRelationshipDT.setRecordStatusTime(new java.sql.Timestamp(new Date().getTime()));
                actRelationshipDT.setItNew(false);
                actRelationshipDT.setItDirty(true);
                arrayList.add(actRelationshipDT);
            }
        }
        return arrayList;
    }

    public Map<Object, Object> setOrgToString(Collection<Object> orgVOColl)
    {
        Map<Object, Object> returnMap = new HashMap<Object, Object>();
        Long organizationUID = null;
        String CliaWithConditions = "";
        String displayString = "";
        CachedDropDownValues cdv = new CachedDropDownValues();

        Iterator<Object> it = orgVOColl.iterator();
        if (orgVOColl != null)
        {
            while (it.hasNext())
            {
                OrganizationVO orgVO = (OrganizationVO) it.next();
                organizationUID = orgVO.getTheOrganizationDT().getOrganizationUid();
                if (orgVO.getTheEntityIdDTCollection() != null)
                {
                    Iterator<Object> iter = orgVO.getTheEntityIdDTCollection().iterator();
                    while (iter.hasNext())
                    {
                        EntityIdDT entityIdDT = (EntityIdDT) iter.next();
                        if (entityIdDT.getAssigningAuthorityCd() == "CLIA" && entityIdDT.getTypeCd() == "FI")
                        {
                            CliaWithConditions = entityIdDT.getRootExtensionTxt();
                        }
                    }
                    helper = new QuickEntryEventHelper();
                    displayString = helper.makeORGDisplayString(orgVO);
                }

            }// while
        }
        returnMap.put("UID", organizationUID);
        returnMap.put("organization", displayString);
        if (CliaWithConditions != null && CliaWithConditions.length() > 0)
        {
            returnMap.put("CliaWithConditions", CliaWithConditions);
        }
        returnMap.put("CliaWithConditions", "DEFAULT");

        return returnMap;

    }

    public Collection<Object> setParticipationsForEdit(MorbidityProxyVO oldProxy, HttpServletRequest request)
    {

        Collection<Object> oldParticipationCollection = oldProxy.getTheParticipationDTCollection();
        Long morbUID = this.getMorbReportObsUidFromProxy(oldProxy);
        String sMorbUID = morbUID.toString();
        Collection<Object> newParticipationDTCollection = new ArrayList<Object>();

        {
            String orgReportingOrganizationUID = request.getParameter("Org-ReportingOrganizationUID");
            logger.debug(" orgReportingOrganizationUID = " + orgReportingOrganizationUID);
            this.createOrDeleteParticipation(orgReportingOrganizationUID, morbUID, newParticipationDTCollection,
                    oldParticipationCollection, NEDSSConstants.MOB_REPORTER_OF_MORB_REPORT, "ORG");
        }
        {
            String provEntityProviderUID = request.getParameter("Prov-entity.providerUID");
            logger.debug(" Prov-entity.providerUID = " + provEntityProviderUID);
            this.createOrDeleteParticipation(provEntityProviderUID, morbUID, newParticipationDTCollection,
                    oldParticipationCollection, NEDSSConstants.MOB_PHYSICIAN_OF_MORB_REPORT, "PSN");
        }
        {
            String provEntityReporterUID = request.getParameter("Prov-entity.reporterUID");
            logger.debug(" provEntityReporterUID = " + provEntityReporterUID);
            this.createOrDeleteParticipation(provEntityReporterUID, morbUID, newParticipationDTCollection,
                    oldParticipationCollection, NEDSSConstants.MOB_REPORTER_OF_MORB_REPORT, "PSN");
        }
        {
            String orgHospitalUID = request.getParameter("Org-HospitalUID");
            logger.debug(" orgHospitalUID = " + orgHospitalUID);
            this.createOrDeleteParticipation(orgHospitalUID, morbUID, newParticipationDTCollection,
                    oldParticipationCollection, NEDSSConstants.MOB_HOSP_OF_MORB_REPORT, "ORG");
        }

        return newParticipationDTCollection;

    }

    private void createOrDeleteParticipation(String newEntityUid, Long morbObsUid, Collection<Object> newParCollection,
            Collection<Object> oldParCollection, String typeCd, String classCd)
    {

        logger.debug(" newEntityUid = " + newEntityUid + " old");
        ParticipationDT oldParticipationDT = this.getParticipation(typeCd, classCd, oldParCollection);
        if (oldParticipationDT == null)
        {
            if (newEntityUid != null && !newEntityUid.trim().equals(""))
            {
                logger.info("old par in null and create new only: " + newEntityUid);
                newParCollection.add(this.createParticipation(morbObsUid, newEntityUid, classCd, typeCd));
            }
        }
        else
        {
            Long oldEntityUid = oldParticipationDT.getSubjectEntityUid();
            if (newEntityUid != null && !newEntityUid.trim().equals("")
                    && !newEntityUid.equals(oldEntityUid.toString()))
            {
                logger.info("participation changed newEntityUid: " + newEntityUid + " for typeCd " + typeCd);
                newParCollection.add(this.deleteParticipation(typeCd, classCd, oldEntityUid, oldParCollection));
                newParCollection.add(this.createParticipation(morbObsUid, newEntityUid, classCd, typeCd));
            }
            else if ((newEntityUid == null || newEntityUid.trim().equals("")))
            {
                logger.warn("no EntityUid (is cleared) or not set for typeCd: " + typeCd);
                newParCollection.add(this.deleteParticipation(typeCd, classCd, oldEntityUid, oldParCollection));
            }
        }

    } // scope

    private ParticipationDT deleteParticipation(String typeCd, String classCd, Long oldUid,
            Collection<Object> oldParticipationDTCollection)
    {

        ParticipationDT participationDT = null;
        if (oldParticipationDTCollection != null && oldParticipationDTCollection.size() > 0)
        {
            Iterator<Object> anIterator = null;
            for (anIterator = oldParticipationDTCollection.iterator(); anIterator.hasNext();)
            {
                participationDT = (ParticipationDT) anIterator.next();
                if (participationDT.getTypeCd().trim().equals(typeCd)
                        && participationDT.getSubjectClassCd().trim().equals(classCd)
                        && participationDT.getSubjectEntityUid().toString().equals(oldUid.toString()))
                {
                    logger.debug("deleting participation for typeCd: " + typeCd + " for investigation: "
                            + participationDT.getActUid());
                    participationDT.setItDelete(true);
                    participationDT.setItDirty(false);
                    participationDT.setItNew(false);

                    return participationDT;
                }
                else
                {
                    continue;
                }
            }
        }

        return participationDT;
    }

    private ParticipationDT createParticipation(Long actUid, String subjectEntityUid, String subjectClassCd,
            String typeCd)
    {
        CachedDropDownValues srtc = new CachedDropDownValues();

        logger.debug(" participation for typeCd: " + typeCd + " and " + subjectEntityUid);
        ParticipationDT participationDT = new ParticipationDT();
        participationDT.setActClassCd(NEDSSConstants.PART_ACT_CLASS_CD);
        participationDT.setActUid(actUid);
        participationDT.setSubjectClassCd(subjectClassCd);
        participationDT.setSubjectEntityUid(new Long(subjectEntityUid.trim()));
        participationDT.setTypeCd(typeCd.trim());
        participationDT.setTypeDescTxt(srtc.getDescForCode("PAR_TYPE", typeCd.trim()));
        participationDT.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
        participationDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
        participationDT.setStatusTime(new java.sql.Timestamp(new Date().getTime()));
        participationDT.setItNew(true);
        participationDT.setItDirty(false);

        return participationDT;
    }

    private ParticipationDT getParticipation(String type_cd, String classCd,
            Collection<Object> participationDTCollection)
    {

        ParticipationDT participationDT = null;
        if (participationDTCollection != null && participationDTCollection.size() > 0)
        {
            Iterator<Object> anIterator = null;
            for (anIterator = participationDTCollection.iterator(); anIterator.hasNext();)
            {
                participationDT = (ParticipationDT) anIterator.next();
                if (participationDT.getSubjectEntityUid() != null
                        && classCd.equals(participationDT.getSubjectClassCd())
                        && type_cd.equals(participationDT.getTypeCd()))
                {
                    logger.debug("participation exist for investigation: " + participationDT.getActUid()
                            + " subjectEntity: " + participationDT.getSubjectEntityUid());

                    return participationDT;
                }
                else
                {
                    continue;
                }
            }
        }

        return null;
    }


    protected PersonVO getPersonVO(String type_cd, MorbidityProxyVO morbidityProxyVO)
    {
        Collection<Object> participationDTCollection = null;
        Collection<Object> personVOCollection = null;
        ParticipationDT participationDT = null;
        PersonVO personVO = null;
        // Long phcUID =
        // investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid();
        participationDTCollection = morbidityProxyVO.getTheParticipationDTCollection();
        // logger.debug("convertProxyToRequestObj() after participationDTCollection  size: "
        // + participationDTCollection.size());
        personVOCollection = morbidityProxyVO.getThePersonVOCollection();
        // logger.debug("convertProxyToRequestObj() after personVOCollection  size: "
        // + personVOCollection.size());
        if (participationDTCollection != null)
        {
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
                        {
                            continue;
                        }
                    }
                }
                else
                {
                    continue;
                }
            }
        }
        return null;
    }

    protected PersonNameDT getPersonNameDT(String nameUserCd, PersonVO personVO)
    {
        PersonNameDT personNameDT = null;
        // logger.debug("getPersonNameDT().getThePersonNameDTCollection: " +
        // personVO.getThePersonNameDTCollection());
        Collection<Object> personNameDTCollection = personVO.getThePersonNameDTCollection();
        if (personNameDTCollection != null)
        {
            // logger.info("getPersonNameDT().getThePersonNameDTCollection: " +
            // personVO.getThePersonNameDTCollection());
            Iterator<Object> anIterator = null;
            for (anIterator = personNameDTCollection.iterator(); anIterator.hasNext();)
            {
                PersonNameDT temp = (PersonNameDT) anIterator.next();
                logger.debug("getPersonNameDT.getThePersonNameDT: " + temp.getNmUseCd());
                if (temp.getNmUseCd() != null && temp.getNmUseCd().trim().equalsIgnoreCase(nameUserCd))
                {
                    logger.info("getPersonNameDT.getPersonNameDT: " + temp.getFirstNm() + " nameType: "
                            + temp.getNmUseCd());
                    personNameDT = temp;
                }
                else
                {
                    continue;
                }
            }
        }
        return personNameDT;
    }

    private OrganizationVO getOrganizationVO(String type_cd, MorbidityProxyVO morbidityProxyVO)
    {
        Collection<Object> participationDTCollection = null;
        Collection<Object> organizationVOCollection = null;
        ParticipationDT participationDT = null;
        OrganizationVO organizationVO = null;
        // Long phcUID =
        // investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid();
        participationDTCollection = this.getMorbReportObsVO(morbidityProxyVO).getTheParticipationDTCollection();
        organizationVOCollection = morbidityProxyVO.getTheOrganizationVOCollection();
        if (participationDTCollection != null && organizationVOCollection != null)
        {
            Iterator<Object> anIterator1 = null;
            Iterator<Object> anIterator2 = null;
            for (anIterator1 = participationDTCollection.iterator(); anIterator1.hasNext();)
            {
                participationDT = (ParticipationDT) anIterator1.next();
                if (participationDT.getTypeCd() != null && (participationDT.getTypeCd().trim()).equals(type_cd))
                {
                    for (anIterator2 = organizationVOCollection.iterator(); anIterator2.hasNext();)
                    {
                        organizationVO = (OrganizationVO) anIterator2.next();
                        if (organizationVO.getTheOrganizationDT().getOrganizationUid().longValue() == participationDT
                                .getSubjectEntityUid().longValue())
                        {
                            logger.debug("getOrganizationVO: got OrganizationVO for  Organization_uid: "
                                    + organizationVO.getTheOrganizationDT().getOrganizationUid() + " and type_cd "
                                    + participationDT.getTypeCd());
                            return organizationVO;
                        }
                        else
                        {
                            continue;
                        }
                    }
                }
                else
                {
                    continue;
                }
            }
        }
        return null;
    }

    private OrganizationNameDT getOrganizationNameDT(String nameUserCd, OrganizationVO organizationVO)
    {

        OrganizationNameDT organizationNameDT = null;
        Collection<Object> organizationNameDTCollection = organizationVO.getTheOrganizationNameDTCollection();

        if (organizationNameDTCollection != null)
        {

            Iterator<Object> anIterator = null;

            for (anIterator = organizationNameDTCollection.iterator(); anIterator.hasNext();)
            {
                organizationNameDT = (OrganizationNameDT) anIterator.next();

                if (organizationNameDT.getNmUseCd() != null
                        && organizationNameDT.getNmUseCd().trim().equalsIgnoreCase(nameUserCd))
                {
                    logger.debug("getOrganizationNameDT.organizationNameDT: nameUseCd: "
                            + organizationNameDT.getNmUseCd());

                    return organizationNameDT;
                }
                else
                {

                    continue;
                }
            }
        }

        return null;
    }

    protected EntityLocatorParticipationDT getOrganizationLocatorDT(String locatorType, OrganizationVO organizationVO)
    {
        Collection<Object> entityLocatorParticipationDTCollection = organizationVO
                .getTheEntityLocatorParticipationDTCollection();
        if (entityLocatorParticipationDTCollection != null)
        {
            Iterator<Object> anIterator = null;
            for (anIterator = entityLocatorParticipationDTCollection.iterator(); anIterator.hasNext();)
            {
                EntityLocatorParticipationDT entityLocatorParticipationDT = (EntityLocatorParticipationDT) anIterator
                        .next();
                if (entityLocatorParticipationDT.getClassCd() != null
                        && entityLocatorParticipationDT.getClassCd().trim().equalsIgnoreCase(locatorType))
                {
                    logger.info("InvestigationMeaslesPreAction.getOrganizationLocatorDT: locatorType: "
                            + entityLocatorParticipationDT.getClassCd());
                    return entityLocatorParticipationDT;
                }
                else
                {
                    continue;
                }
            }
        }
        return null;
    }

    private String getCountiesByState(String stateCd)
    {
        StringBuffer parsedCodes = new StringBuffer("");
        if (stateCd != null)
        {
            // SRTValues srtValues = new SRTValues();
            CachedDropDownValues srtValues = new CachedDropDownValues();
            TreeMap<?, ?> treemap = null;

            treemap = srtValues.getCountyCodes(stateCd);

            if (treemap != null)
            {
                Set<?> set = treemap.keySet();
                Iterator<?> itr = set.iterator();
                while (itr.hasNext())
                {
                    String key = (String) itr.next();
                    String value = (String) treemap.get(key);
                    parsedCodes.append(key.trim()).append(NEDSSConstants.SRT_PART).append(value.trim())
                            .append(NEDSSConstants.SRT_LINE);
                }
            }
        }
        return parsedCodes.toString();
    }

    private EntityLocatorParticipationDT getTeleLocatorDT(String locatorType, AbstractVO abstactVO)
    {

        Collection<Object> entityLocatorParticipationDTCollection = null;
        if (abstactVO instanceof PersonVO)
        {
            entityLocatorParticipationDTCollection = ((PersonVO) abstactVO)
                    .getTheEntityLocatorParticipationDTCollection();
        }
        if (abstactVO instanceof OrganizationVO)
        {
            entityLocatorParticipationDTCollection = ((OrganizationVO) abstactVO)
                    .getTheEntityLocatorParticipationDTCollection();

        }
        if (entityLocatorParticipationDTCollection != null)
        {
            Iterator<Object> anIterator = null;
            for (anIterator = entityLocatorParticipationDTCollection.iterator(); anIterator.hasNext();)
            {
                EntityLocatorParticipationDT entityLocatorParticipationDT = (EntityLocatorParticipationDT) anIterator
                        .next();
                if (entityLocatorParticipationDT.getClassCd() != null
                        && entityLocatorParticipationDT.getClassCd().equals(NEDSSConstants.TELE)
                        && entityLocatorParticipationDT.getTheTeleLocatorDT().getPhoneNbrTxt() != null
                        && entityLocatorParticipationDT.getUseCd() != null
                        && entityLocatorParticipationDT.getUseCd().equals(NEDSSConstants.WORK_PHONE)
                        && entityLocatorParticipationDT.getCd() != null
                        && !entityLocatorParticipationDT.getCd().trim().equals("")
                        && entityLocatorParticipationDT.getCd().equals(NEDSSConstants.PHONE))
                {
                    logger.debug("getPersonLocatorDT: locatorType: " + entityLocatorParticipationDT.getClassCd());
                    return entityLocatorParticipationDT;
                }
                else
                {
                    continue;
                }
            }
        }
        return null;
    }

    private EntityLocatorParticipationDT getEntityLocatorDT(String locatorType, AbstractVO abstactVO)
    {

        Collection<Object> entityLocatorParticipationDTCollection = null;
        if (abstactVO instanceof PersonVO)
        {
            entityLocatorParticipationDTCollection = ((PersonVO) abstactVO)
                    .getTheEntityLocatorParticipationDTCollection();
        }
        if (abstactVO instanceof OrganizationVO)
        {
            entityLocatorParticipationDTCollection = ((OrganizationVO) abstactVO)
                    .getTheEntityLocatorParticipationDTCollection();

        }
        if (entityLocatorParticipationDTCollection != null)
        {
            Iterator<Object> anIterator = entityLocatorParticipationDTCollection.iterator();
            while (anIterator.hasNext())
            {
                EntityLocatorParticipationDT el = (EntityLocatorParticipationDT) anIterator.next();
                if (el.getClassCd() != null && el.getClassCd().equals(locatorType))
                {
                    logger.debug("locatorType: " + el.getClassCd());
                    return el;
                }
                else
                {
                    continue;
                }
            }
        }
        return null;
    }

    public void getNBSSecurityJurisdictions(HttpServletRequest request, NBSSecurityObj nbsSecurityObj)
            throws ServletException
    {

        String programAreaJurisdictions = nbsSecurityObj.getProgramAreaJurisdictions(
                NBSBOLookup.OBSERVATIONMORBIDITYREPORT, NBSOperationLookup.ADD);
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
            request.setAttribute("NBSSecurityJurisdictionsPA", programAreaJurisdictions);
        }

    } // getNBSSecuirityJurisdictions

    public void getFilteredProgramAreaJurisdiction(HttpServletRequest request, NBSSecurityObj nbsSecurityObj)
            throws ServletException
    {

        HttpSession session = request.getSession(false);
        String programAreas = "";
        StringBuffer stingBuffer = new StringBuffer();
		/*
		 * the following code was changed to account for Defect #11663 in
		 * NBSCentral - 02/14/2019
		 */
		TreeMap<Object, Object> treeMap = new TreeMap<Object, Object>();
		String currentTask = NBSContext.getCurrentTask(session);
		if (currentTask != null && currentTask.startsWith(NBSConstantUtil.ADD)) {
			treeMap = (TreeMap<Object, Object>) nbsSecurityObj.getProgramAreas(
					NBSBOLookup.OBSERVATIONMORBIDITYREPORT,
					NBSOperationLookup.ADD);
		} else if (currentTask != null
				&& currentTask.startsWith(NBSConstantUtil.VIEW)) {
			treeMap = (TreeMap<Object, Object>) nbsSecurityObj.getProgramAreas(
					NBSBOLookup.OBSERVATIONMORBIDITYREPORT,
					NBSOperationLookup.VIEW);
			;
		} else if (currentTask != null
				&& currentTask.startsWith(NBSConstantUtil.EDIT)) {
			treeMap = (TreeMap<Object, Object>) nbsSecurityObj.getProgramAreas(
					NBSBOLookup.OBSERVATIONMORBIDITYREPORT,
					NBSOperationLookup.EDIT);
		}/* end code change for Defect #11663 */
        
        if (treeMap != null && treeMap.size() > 0)
        {
            Iterator<Object> anIterator = treeMap.keySet().iterator();
            stingBuffer.append("(");
            while (anIterator.hasNext())
            {
                String token = (String) anIterator.next();
                String NewString = "";
                if (token.lastIndexOf("!") < 0)
                {
                    NewString = token;
                }
                else
                {
                    NewString = token.substring(0, token.lastIndexOf("!"));
                }
                stingBuffer.append("'").append(NewString).append("',");
            }
            stingBuffer.replace(stingBuffer.length() - 1, stingBuffer.length(), "");
            stingBuffer.append(")");
            programAreas = stingBuffer.toString();

        }
        else
        {
            logger.error("could not get Program Areas from security object");
            request.setAttribute("error", "could not get Program Areas from security object");
        }

        try
        {
           
            CachedDropDownValues cdv = new CachedDropDownValues();
            TreeMap<Object, Object> programAreaTreeMap = null;
            // show only active conditions on Add and All conditions for View or
            // Edit
            if (currentTask.startsWith(NBSConstantUtil.ADD))
                programAreaTreeMap = cdv.getActiveProgramAreaConditions(programAreas);
            else
                programAreaTreeMap = cdv.getAllProgramAreaConditions(programAreas);
            TreeMap<Object, Object> conditionTreeMap = new TreeMap<Object, Object>();

            // NBSContext.store(session, "programAreaTreeMap",
            // programAreaTreeMap);
            // session.setAttribute("programAreaTreeMap", programAreaTreeMap);

            String conditionCdDescString = null;
            ProgramAreaVO programAreaVO = null;

            if (programAreaTreeMap != null && programAreaTreeMap.size() > 0)
            {
                Set<Object> set = programAreaTreeMap.keySet();
                Iterator<Object> itr = set.iterator();
                while (itr.hasNext())
                {
                    String key = (String) itr.next();
                    programAreaVO = (ProgramAreaVO) programAreaTreeMap.get(key);

                    StringBuffer conditionAndProgAreaBuffer = new StringBuffer("");
                    conditionAndProgAreaBuffer.append(programAreaVO.getStateProgAreaCode()).append("^");
                    conditionAndProgAreaBuffer.append(programAreaVO.getStateProgAreaCdDesc()).append("^");
                    conditionAndProgAreaBuffer.append(programAreaVO.getConditionCd());
                    conditionTreeMap.put(conditionAndProgAreaBuffer.toString(), programAreaVO.getConditionShortNm());
                }
                conditionCdDescString = WumUtil.sortTreeMap(conditionTreeMap);
            }

            request.setAttribute("DSConditionCdDescString", conditionCdDescString);
            this.getNBSSecurityJurisdictions(request, nbsSecurityObj);
        }
        catch (Exception e)
        {
            throw new ServletException("could not get programArea or conditions or jurisdiction ");
        }
    }

    private String replaceNull(Object nullObj)
    {
        String returnStr = "";
        if (nullObj instanceof String)
        {
            returnStr = (nullObj == null) ? "" : (String) nullObj;
        }
        else if (nullObj instanceof Long)
        {
            if (nullObj == null)
            {
                returnStr = "";
            }
            else
            {
                returnStr = ((Long) nullObj).toString();
            }
        }
        else if (nullObj instanceof Double)
        {
            if (nullObj == null)
            {
                returnStr = "";
            }
            else
            {
                returnStr = ((Double) nullObj).toString();
            }
        }
        else if (nullObj instanceof Integer)
        {
            if (nullObj == null)
            {
                returnStr = "";
            }
            else
            {
                returnStr = ((Integer) nullObj).toString();
            }
        }

        else
        {
            returnStr = "";

        }
        return returnStr;

    }

    public void setExternalUserForCreate(HttpServletRequest request, HttpSession session, NBSSecurityObj secObj)
    {
        String userTypeValue = secObj.getTheUserProfile().getTheUser().getUserType();
        boolean retainCheckbox;
        if (userTypeValue != null)
        {
            boolean reportExternalUser = userTypeValue.equalsIgnoreCase(NEDSSConstants.SEC_USERTYPE_EXTERNAL);
            if (reportExternalUser)
            {
                request.setAttribute("strReportExternalUser", String.valueOf(reportExternalUser));
            }
            // if (reportExternalUser == true)
            // {
            // reportExternalUser = false;
            // }

            try
            {
                Long userReportingFacilityUid = secObj.getTheUserProfile().getTheUser().getReportingFacilityUid();
                if (userReportingFacilityUid != null)
                {
                    String reportingSourceDetails = this.getReportingFacilityDetails(
                            String.valueOf(userReportingFacilityUid.longValue()), session);
                    request.setAttribute("reportingOrgUID", userReportingFacilityUid.toString());
                    if (reportingSourceDetails != null)
                    {
                        request.setAttribute("reportingSourceDetails", reportingSourceDetails);
                        CommonLabUtil.getDropDownLists(NEDSSConstants.DEFAULT, NEDSSConstants.RESULTED_TEST_LOOKUP,
                                null, NEDSSConstants.PROGRAM_AREA_NONE, null, null, request);
                    }
                }

                else
                {
                    request.setAttribute("reportingSourceDetails", "There is no Reporting Facility for this user.");
                    // return (mapping.findForward("error"));
                }

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

    }

    private String getReportingFacilityDetails(String organizationUid, HttpSession session) throws Exception
    {

        String displayString = "";
        MainSessionCommand msCommand = null;
        String sBeanJndiName = JNDINames.ENTITY_PROXY_EJB;
        String sMethod = "getOrganization";
        Object[] oParams = new Object[] { new Long(organizationUid) };

        MainSessionHolder holder = new MainSessionHolder();
        msCommand = holder.getMainSessionCommand(session);
        ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
        OrganizationVO organizationVO = (OrganizationVO) arr.get(0);
        if (organizationVO != null)
        {
            helper = new QuickEntryEventHelper();
            displayString = helper.makeORGDisplayString(organizationVO);

        }
        return displayString;

    }

    public void convertParticipationsToRequest(MorbidityProxyVO morbidityProxyVO, HttpServletRequest request)
    {
        Long reporterOrgUid = null;
        Long hospitalUid = null;
        Long reporterUid = null;
        Long providerUid = null;

        Collection<Object> partColl = morbidityProxyVO.getTheParticipationDTCollection();
        Iterator<Object> partIt = partColl.iterator();
        while (partIt.hasNext())
        {
            ParticipationDT pdt = (ParticipationDT) partIt.next();

            if (pdt.getTypeCd().equalsIgnoreCase("HospOfMorbObs"))
            {
                hospitalUid = pdt.getSubjectEntityUid();
                // System.out.println("hospitalUid is :" + hospitalUid);

            }
            if (pdt.getTypeCd().equalsIgnoreCase("ReporterOfMorbReport")
                    && pdt.getSubjectClassCd().equalsIgnoreCase("ORG"))
            {
                reporterOrgUid = pdt.getSubjectEntityUid();
                // System.out.println("reporterOrgUid is :" + reporterOrgUid);
            }
            if (pdt.getTypeCd().equalsIgnoreCase("PhysicianOfMorb"))
            {
                providerUid = pdt.getSubjectEntityUid();
                // System.out.println("providerUid is :" + providerUid);
            }
            if (pdt.getTypeCd().equalsIgnoreCase("ReporterOfMorbReport")
                    && pdt.getSubjectClassCd().equalsIgnoreCase("PSN"))
            {
                reporterUid = pdt.getSubjectEntityUid();
                // System.out.println("reporterUid is :" + reporterUid);
            }

        }
        CommonLabUtil util = new CommonLabUtil();

        OrganizationVO reportingOrgVO = null;
        OrganizationVO hospitalOrgVO = null;

        Collection<Object> orgColl = morbidityProxyVO.getTheOrganizationVOCollection();
        Iterator<Object> it = orgColl.iterator();
        while (it.hasNext())
        {
            OrganizationVO orgVO = (OrganizationVO) it.next();

            if (reporterOrgUid != null)
            {
                if (orgVO.getTheOrganizationDT().getOrganizationUid().compareTo(reporterOrgUid) == 0)
                {
                    reportingOrgVO = orgVO;
                }
            }
            if (hospitalUid != null)
            {
                if (orgVO.getTheOrganizationDT().getOrganizationUid().compareTo(hospitalUid) == 0)
                {
                    hospitalOrgVO = orgVO;
                }
            }
        }

        PersonVO reporterVO = null;
        PersonVO providerVO = null;

        Collection<Object> persColl = morbidityProxyVO.getThePersonVOCollection();
        Iterator<Object> itPerson = persColl.iterator();
        while (itPerson.hasNext())
        {
            PersonVO personVO = (PersonVO) itPerson.next();

            if (providerUid != null)
            {
                if (personVO.getThePersonDT().getPersonUid().compareTo(providerUid) == 0)
                {
                    providerVO = personVO;
                }
            }
            if (reporterUid != null)
            {
                if (personVO.getThePersonDT().getPersonUid().compareTo(reporterUid) == 0)
                {
                    reporterVO = personVO;
                }
            }
        }

        if (reportingOrgVO != null)
        {
            request.setAttribute("reportingSourceDetails", convertOrganizationToRequest(reportingOrgVO));
            request.setAttribute("reportingOrgUID", reportingOrgVO.getTheOrganizationDT().getOrganizationUid());
        }
        else
        {
            request.setAttribute("reportingSourceDetails", "There is no Reporting Facility selected.");
        }

        if (hospitalOrgVO != null)
        {
            request.setAttribute("hospitalDetails", convertOrganizationToRequest(hospitalOrgVO));
            request.setAttribute("HospitalOrgUID", hospitalOrgVO.getTheOrganizationDT().getOrganizationUid());
        }
        else
        {
            request.setAttribute("hospitalDetails", "There is no Hospital selected.");
        }
        if (providerVO != null)
        {
            request.setAttribute("providerSourceDetails", util.convertPersonToRequest(providerVO));
            request.setAttribute("providerUID", providerVO.getThePersonDT().getPersonUid());
        }
        else
        {
            request.setAttribute("providerSourceDetails", "There is no Provider selected.");
        }
        if (reporterVO != null)
        {
            request.setAttribute("reporterSourceDetails", this.convertReporterToRequest(reporterVO));
            request.setAttribute("reporterUID", reporterVO.getThePersonDT().getPersonUid());
        }
        else
        {
            request.setAttribute("reporterSourceDetails", "There is no Reporter selected.");
        }
        setReportAgeForPrintPage(request, morbidityProxyVO);
    }

    public String convertReporterToRequest(PersonVO pvo)
    {
        helper = new QuickEntryEventHelper();
        String displayString = helper.makePRVDisplayString(pvo);
        return displayString;

    }

    /**
     * 
     * @param observationUid
     * @param session
     * @return MorbidityProxyVO
     * @throws NEDSSAppConcurrentDataException
     * @throws java.rmi.RemoteException
     * @throws javax.ejb.EJBException
     * @throws Exception
     */
    public MorbidityProxyVO getMorbidityProxyForSubmitAndCreateInv(Long observationUid, HttpSession session)
            throws NEDSSAppConcurrentDataException, java.rmi.RemoteException, javax.ejb.EJBException, Exception

    {

        MainSessionCommand msCommand = null;

        String sBeanJndiName = JNDINames.OBSERVATION_PROXY_EJB;
        String sMethod = null;
        Long investigationUid = null;
        MainSessionHolder holder = new MainSessionHolder();
        ArrayList<?> resultUIDArr = new ArrayList<Object>();

        sMethod = "getMorbidityProxy";
        Object[] oParams = { observationUid };
        msCommand = holder.getMainSessionCommand(session);
        resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
        for (int i = 0; i < resultUIDArr.size(); i++)
        {
            logger.info("Result " + i + " is: " + resultUIDArr.get(i));
        }

        if ((resultUIDArr != null) && (resultUIDArr.size() > 0))
        {

            MorbidityProxyVO proxy = (MorbidityProxyVO) resultUIDArr.get(0);
            return proxy;
        }

        return null;
    }

    public String convertOrganizationToRequest(OrganizationVO orgVO)
    {
        helper = new QuickEntryEventHelper();
        String displayString = helper.makeORGDisplayString(orgVO);
        return displayString;

    }

    private void setReportAgeForPrintPage(HttpServletRequest request, MorbidityProxyVO proxy)
    {
        // printAge and printAgeUnits information is REQUIRED for displaying
        // patient Age as header on Printable Forms (PDF)
        CachedDropDownValues cdv = new CachedDropDownValues();
        PersonDT person = getPersonVO(NEDSSConstants.MOB_SUBJECT_OF_MORB_REPORT, proxy).getThePersonDT();
        String personAge = person.getAgeReported();
        String ageUnits = cdv.getDescForCode("P_AGE_UNIT", person.getAgeReportedUnitCd());
        request.setAttribute("printAge", (String) person.getAgeReported());
        request.setAttribute("printAgeUnits", ageUnits);

    }
   
	public static void processRequest(Long nbsCaseAttachmentUid, HttpSession session) throws Exception {

		MainSessionCommand msCommand = null;
	
		try {
			String sBeanJndiName = JNDINames.PAGE_PROXY_EJB;
			String sMethod = "deleteNbsAttachment";
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);
			Object[] oParams = { nbsCaseAttachmentUid };
			ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
			

		} catch (Exception ex) {
			logger.error("Error while processRequest in FileUploadAction: " + ex.toString());	
			throw new Exception(ex);
		}
		
	}   
	
    
    public static Long processRequest(NBSAttachmentDT dt, HttpSession session)
			throws Exception {

		MainSessionCommand msCommand = null;
		Long resultUid = null;
		try {
			// Change the EJB and use the correct method
			String sBeanJndiName = JNDINames.OBSERVATION_PROXY_EJB;
			String sMethod = "setNBSAttachment";
			Object[] oParams = new Object[] { dt };
			MainSessionHolder holder = new MainSessionHolder();
			Object returnObj = CallProxyEJB.callProxyEJB(oParams,
					sBeanJndiName, sMethod, session);
			resultUid = (Long) returnObj;
		} catch (Exception ex) {
			logger
					.error("Error in FileUploadAction while  calling setNBSAttachment method : "
							+ ex.toString());
			throw new Exception(ex);
		}

		return resultUid;
	}
    
    public void createAttachment(MorbidityForm form,
            NBSSecurityObj securityObj,
            HttpSession session,
            HttpServletRequest request){
  	  

  	    /******************************************************************
  	     * batch entry attachments
  	     */
  	    Collection<Object>  attachmentsColl = form.getAttachmentCollection();
  	    MorbidityUtil morbidityUtil = new MorbidityUtil();
  	    if (attachmentsColl != null)
  	    { 
  	   // StringBuffer largeFiles = null;
  	      for (Iterator<Object> iter = attachmentsColl.iterator(); iter.hasNext(); )
  	      {
  	    	 
  	        BatchEntryHelper helper = (BatchEntryHelper) iter.next();
  	      //  TreeMap<Object,Object> batchTreeMap = helper.getTreeMap();
  	       // AttachmentVO attachmentVOold = (AttachmentVO) batchTreeMap.get("AttachmentVO");
  	      boolean deleteFlag = morbidityUtil.findDeleteOrderTest(helper);
  	    if(helper.getStatusCd().equalsIgnoreCase("I")){
  	      Collection<Object>  attachmentHelperColl = helper.getTheAttachmentCollection();

	         

  	      Iterator<Object>  itor = attachmentHelperColl.iterator();
  	      while (itor.hasNext())
  	      {
  	    	  AttachmentVO attachmentVO = (AttachmentVO) itor.next();
  	    	  AttachmentDT attachmentDT = attachmentVO.getTheAttachmentDT();
  	    	  Long nbsAttachmentUid = 	attachmentDT.getNbsAttachmentUid();
  	    	  if(nbsAttachmentUid!=null){
  	    		try{
  	  				morbidityUtil.processRequest(nbsAttachmentUid, request.getSession());
  	  				}catch(Exception e){
  	  					
  	  					System.out.println("Error while deleting Morbidity attachments.");
  	  				}
  	    	  }
  	      }
  	    }
  	    if(helper.getStatusCd().equalsIgnoreCase("A")){


  	    	//  if (!deleteFlag)
  	      //  {

  	       
				
			
  	        Collection<Object>  attachmentHelperColl = helper.getTheAttachmentCollection();

  	         

  	      	Iterator<Object>  itor = attachmentHelperColl.iterator();
  	      	while (itor.hasNext())
  	      	{
  	      	AttachmentVO attachmentVO = (AttachmentVO) itor.next();
  	      	AttachmentDT attachmentDT = attachmentVO.getTheAttachmentDT();
  	      	boolean newFlag = false;
	        if (attachmentDT.getNbsAttachmentUid() == null || attachmentDT.getNbsAttachmentUid()== 0 )
	        {
	          newFlag = true;
	        }
			if(newFlag){
  	      	
  	    	//File file = new File(attachmentDT.getAttachmentPath());
  	    
  	    	//int maxSizeInMB = PropertyUtil.getInstance().getMaxFileAttachmentSizeInMB();
  	    
  	    	//long maxSizeInBytes = maxSizeInMB * 1024 * 1024;
  	    	
  	    	//if (file.exists() && file.length() < maxSizeInBytes) {
  	    	
  	    	//String hasAttachmentErrors = (request.getAttribute("hasAttachmentErrors")==null)?"false":(String)request.getAttribute("hasAttachmentErrors");
  	    	
  	    	//if("false".equalsIgnoreCase(hasAttachmentErrors)){
  	    	NBSAttachmentDT dt = new NBSAttachmentDT();
  			dt.setItNew(true);
  			dt.setAttachmentParentUid(new Long(-1));
  			
  			Long morbUid = (Long)request.getAttribute("morbidityReportUid");
  			dt.setAttachmentParentUid(morbUid);
  			
  			// Retrieve addUserId from SecurityObject
  			NBSSecurityObj secObj = (NBSSecurityObj) request.getSession()
  					.getAttribute("NBSSecurityObject");
  			String userId = secObj.getTheUserProfile().getTheUser()
  					.getEntryID();
  			
  			//dt.setAddUserId(attachmentDT.getLastChangeUserId());
  			dt.setLastChgUserId(attachmentDT.getLastChangeUserId());
  			
  			try{
  				SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
  			    Date parsedDate = dateFormat.parse(attachmentDT.getLastChangeTime());
  			    Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
  				dt.setLastChgTime(timestamp);
  				
  			}catch(Exception e){
  				
  				//TODO: Fatima handle exception
  			}
  			// FileName
  			//String fileNm = String.valueOf(form.getFileName());
  			String fileNm = attachmentDT.getFileNmTxt();
  			dt.setFileNmTxt(fileNm);

  			// FileDescription
  			//String descTxt = String.valueOf(form.getFileDescription());
  			String descTxt = attachmentDT.getDescTxt();
  			dt.setDescTxt(descTxt);

  		
  			
  			

  			try {
  				FormFile file = getFormFile(form, attachmentDT);
  				
  				if(file!=null){
  				// Attachment
  				byte[] fileData = file.getFileData();
  				dt.setAttachment(fileData);
  				}
  			} catch (IOException e1) {
  				System.out.println("Error occured while reading file in createAttachment method in MorbReportSubmit");
  				// TODO Auto-generated catch block
  				e1.printStackTrace();
  			}
  			
  			// type code
  			dt.setTypeCd(NEDSSConstants.MORBIDITY_REPORT_CD);
  			
  			
  			// Make EJB Call - Persistance
  				try{
  				Long resultUid = morbidityUtil.processRequest(dt, request.getSession());
  				}catch(Exception e){
  					
  					System.out.println("");
  				}
  			
  			/*if (resultUid != null && resultUid.longValue() > 0) {
  				request.setAttribute("confirmation", "true");
  				dt.setNbsAttachmentUid(resultUid);
  				request.setAttribute("newAttachment",
  						_makeNewAttachmentRow(dt));
  			}*/
  		/*	}else{				form.reset();
  			request.setAttribute("fileUploadForm", form);
  			request.setAttribute("maxFileSizeExceeded", "true");
  			request.setAttribute("maxFileSizeInMB", String
  					.valueOf(maxSizeInMB));
  		      	}

  	        */
  	    	}
  	      	}
  	    	/*else{
  	    		//form.reset(); 
  	    		//request.setAttribute("fileUploadForm", form); 
  	    		request.setAttribute("hasAttachmentErrors", "true"); 
  	    		session.setAttribute("hasAttachmentErrors", "true"); 
  	    		request.setAttribute("maxFileSizeExceeded", "true"); 
  	    		request.setAttribute("maxFileSizeInMB", String.valueOf(maxSizeInMB)); 
  	    		if(largeFiles==null)
  	    			largeFiles	= new StringBuffer("[" + attachmentDT.getFileNmTxt()+"]");
  	    		else{
  	    			largeFiles = largeFiles.append(",");
  	    			largeFiles = largeFiles.append("[" + attachmentDT.getFileNmTxt()+"]");
  	    		}
  	    		request.setAttribute("largeFiles", largeFiles);
  	    	}*/
  	    //	}
  	      	//request.setAttribute("largeFiles", largeFiles);
  	      //	}
  	        }
  	      }
  	      }
  	    }
    /**
     * getFormFile: this method returns the inputFile element associated to the attachmentDT
     * @param form
     * @param attachmentDT
     * @return
     */
    public FormFile getFormFile (MorbidityForm form, AttachmentDT attachmentDT){
  	  
  		String inputFile = attachmentDT.getInputFile();
  		FormFile file = null;
  		
  		if(inputFile.equalsIgnoreCase("fileAttachment0"))
  				file = form.getFileAttachment0();
  		if(inputFile.equalsIgnoreCase("fileAttachment1"))
  				file = form.getFileAttachment1();
  		if(inputFile.equalsIgnoreCase("fileAttachment2"))
  				file = form.getFileAttachment2();
  		if(inputFile.equalsIgnoreCase("fileAttachment3"))
  				file = form.getFileAttachment3();
  		if(inputFile.equalsIgnoreCase("fileAttachment4"))
  				file = form.getFileAttachment4();
  		if(inputFile.equalsIgnoreCase("fileAttachment5"))
  				file = form.getFileAttachment5();
  		if(inputFile.equalsIgnoreCase("fileAttachment6"))
  				file = form.getFileAttachment6();
  		if(inputFile.equalsIgnoreCase("fileAttachment7"))
  				file = form.getFileAttachment7();
  		if(inputFile.equalsIgnoreCase("fileAttachment8"))
  				file = form.getFileAttachment8();
  		if(inputFile.equalsIgnoreCase("fileAttachment9"))
  				file = form.getFileAttachment9();
  		if(inputFile.equalsIgnoreCase("fileAttachment10"))
				file = form.getFileAttachment10();
  		
  		return file;
    }
    
}
