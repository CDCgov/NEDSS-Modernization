package gov.cdc.nedss.webapp.nbs.action.ldf;

/**
 * Title:        Actions
 * Description:  initializes the ldf admin
 * Copyright:    Copyright (c) 2001
 * Company:      CSC
 * @author:jay kim
 * @version 1.0
 */

import java.io.*;
import java.util.*;

import javax.rmi.*;
import javax.servlet.*;
import javax.servlet.http.*;

import java.util.HashMap;

import org.apache.struts.action.*;

import gov.cdc.nedss.systemservice.ejb.srtmapejb.bean.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.ldf.ejb.ldfmetadataejb.bean.*;
import gov.cdc.nedss.ldf.dt.*;
import gov.cdc.nedss.webapp.nbs.form.ldf.LdfForm;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.systemservice.util.*;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.ldf.importer.dt.*;
import gov.cdc.nedss.ldf.importer.ejb.bean.CustomDataImportHome;
import gov.cdc.nedss.ldf.helper.*;
import gov.cdc.nedss.ldf.importer.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;

import java.text.*;

public class ImportLogLoad
    extends Action {

  public ImportLogLoad() {
  }

  /**
   *
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return
   * @throws IOException
   * @throws ServletException
   */
  public ActionForward execute(ActionMapping mapping,
                               ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws IOException,
      ServletException {
    HttpSession session = request.getSession();
    String sPageID = request.getParameter("pageID");
    NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session.getAttribute(
        "NBSSecurityObject");
    boolean securityCheck = nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,
        NBSOperationLookup.LDFADMINISTRATION);
    if (securityCheck != true) {
      session.setAttribute("error", "Failed at security checking.");
      throw new ServletException("Failed at security checking.");
    }

    Long logVersionNbr = null;
    HashMap<Object,Object> resultMap = null;

    //Scenario 1: get the lastest import version
    Long importUid = (Long) session.getAttribute("importUid");
    if (importUid != null) {
      logVersionNbr = getVersionNbrByImportTime(nbsSecurityObj, request,
                                                session);
    }
    else {
      //Scenario 2: get the maxi import version
      logVersionNbr = getAllVersion(nbsSecurityObj, request, session);
      //Scenario 3: get the user selected import version
      String userSelectedlogVersion = request.getParameter("logVersion");
      if (userSelectedlogVersion != null) {
        logVersionNbr = new Long(userSelectedlogVersion);
      }
    }
    resultMap = getImportDataLogByVersion(logVersionNbr, nbsSecurityObj,
                                          request, session);

    this.convertToRequest(resultMap, nbsSecurityObj, request, session);
    request.setAttribute("maxVersionNbr", logVersionNbr);
    session.setAttribute("importUid", null);
    return mapping.findForward("next");

  }

  private Long getVersionNbrByImportTime(NBSSecurityObj nbsSecurityObj,
                                         HttpServletRequest request,
                                         HttpSession session) {
    this.getAllVersion(nbsSecurityObj, request, session);
    Long logVersionNbr = null;
    try {

      MainSessionCommand msCommand = null;
      String sBeanJndiName = JNDINames.CUSTOM_DATAIMPORT_EJB;
      String sMethod = "getVersionNbrByImportTime";
      Object[] oParams = {};

      MainSessionHolder holder = new MainSessionHolder();
      msCommand = holder.getMainSessionCommand(session);
      ArrayList<?> resultUIDArr = new ArrayList<Object> ();
      resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);

      if ( (resultUIDArr != null) && (resultUIDArr.size() > 0)) {
        logVersionNbr = (Long) resultUIDArr.get(0);
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return logVersionNbr;

  }

  private Long getAllVersion(NBSSecurityObj nbsSecurityObj,
                             HttpServletRequest request, HttpSession session) {

    Collection<Object>  versionNbrColl = null;
    Long maxVersionNbr = new Long(0);
    try {

      MainSessionCommand msCommand = null;
      String sBeanJndiName = JNDINames.CUSTOM_DATAIMPORT_EJB;
      String sMethod = "getAllVersion";
      Object[] oParams = {};

      MainSessionHolder holder = new MainSessionHolder();
      msCommand = holder.getMainSessionCommand(session);
      ArrayList<?> resultUIDArr = new ArrayList<Object> ();
      resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);

      if ( (resultUIDArr != null) && (resultUIDArr.size() > 0)) {
        versionNbrColl = (Collection<Object>) resultUIDArr.get(0);
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    StringBuffer sbVersion = new StringBuffer();
    for (Iterator<Object> it = versionNbrColl.iterator(); it.hasNext(); ) {
      Long versionNbr = (Long) it.next();
      sbVersion.append(versionNbr).append("$").append(versionNbr).append("|");
      if ( (versionNbr.longValue()) > (maxVersionNbr.longValue())) {
        maxVersionNbr = versionNbr;
      }
    }
    request.setAttribute("versionNbrColl", sbVersion.toString());
    return maxVersionNbr;

  }

  private HashMap<Object,Object> getImportDataLogByVersion(Long importVerionNbr,
                                            NBSSecurityObj nbsSecurityObj,
                                            HttpServletRequest request,
                                            HttpSession session) {
    HashMap<Object,Object> resultMap = null;
    try {

      MainSessionCommand msCommand = null;
      String sBeanJndiName = JNDINames.CUSTOM_DATAIMPORT_EJB;
      String sMethod = "getImportDataLogByVersion";
      Object[] oParams = {
          importVerionNbr};

      MainSessionHolder holder = new MainSessionHolder();
      msCommand = holder.getMainSessionCommand(session);
      ArrayList<?> resultUIDArr = new ArrayList<Object> ();
      resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);

      if ( (resultUIDArr != null) && (resultUIDArr.size() > 0)) {
        resultMap = (HashMap<Object, Object>) resultUIDArr.get(0);
      }

    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return resultMap;
  }

  private void convertToRequest(HashMap<Object,Object> resultMap,
                                NBSSecurityObj nbsSecurityObj,
                                HttpServletRequest request, HttpSession session) {
    Collection<Object>  importDataLogDTColl = null;
    CdfSubformImportLogDT cdfSubformImportLogDT = null;
    Collection<Object>  dataLogDTColl = null;
    LogSummary logSummary = null;
    if (resultMap != null) {
      logSummary = (LogSummary) resultMap.get(
          "logSummary");
      if (logSummary != null) {
        request.setAttribute("importDate",
                             formatDate(logSummary.getImportDate()));
        request.setAttribute("adminComments", logSummary.getAdminComments());
        request.setAttribute("logError", logSummary.getError());

        String logStatus = logSummary.getStatus();
        request.setAttribute("logStatus", logStatus);
        dataLogDTColl = (Collection<Object>) logSummary.getDataLogSummaryColl();
        int cdfCount = 0;
        int cdfSuccessCount = 0;
        int subformCount = 0;
        int subformSuccessCount = 0;
        StringBuffer cdfSB = new StringBuffer();
        StringBuffer subformSB = new StringBuffer();
        for (Iterator<Object> it = dataLogDTColl.iterator(); it.hasNext(); ) {
          DataLogSummary dataLogSummary = (DataLogSummary) it.next();
          DFMDSummary dFMDSummary = dataLogSummary.getDFMDSummary();
          if (dFMDSummary != null) {
            cdfCount++;
            if (dataLogSummary.getStatus().equalsIgnoreCase(ImportConstants.
                SUCCESS_CODE)) {
              cdfSuccessCount++;
            }
            String dFMDString = buildDFMDString(dataLogSummary, dFMDSummary);
            cdfSB.append(dFMDString).append("|");
          }
          SubformSummary subformSummary = dataLogSummary.getSubformSummary();
          if (subformSummary != null) {
            subformCount++;
            if (dataLogSummary.getStatus().equalsIgnoreCase(ImportConstants.
                SUCCESS_CODE)) {
              subformSuccessCount++;
            }
            String subformString = buildSubformString(dataLogSummary,
                subformSummary);
            subformSB.append(subformString).append("|");

          }
        }
        request.setAttribute("cdfCount", new Integer(cdfCount));
        request.setAttribute("subformCount", new Integer(subformCount));
        request.setAttribute("cdfSuccessCount", new Integer(cdfSuccessCount));
        request.setAttribute("subformSuccessCount",
                             new Integer(subformSuccessCount));
        request.setAttribute("cdfFailureCount",
                             new Integer(cdfCount - cdfSuccessCount));
        request.setAttribute("subformFailureCount",
                             new Integer(subformCount - subformSuccessCount));
        request.setAttribute("CDFResultList", cdfSB.toString());
        request.setAttribute("SubfromResultList", subformSB.toString());

      }
    }

  }

  private String buildSubformString(DataLogSummary dataLogSummary,
                                    SubformSummary subformSummary) {
    StringBuffer sb = new StringBuffer("");
    sb.append(dataLogSummary.getStatus() == null ? "" :
              dataLogSummary.getStatus()).append("$");
    sb.append(subformSummary.getLabel() == null ? "" : subformSummary.getLabel()).
        append("$");
    sb.append(subformSummary.getPageSet() == null ? "" :
              subformSummary.getPageSet()).append("$");
    if (dataLogSummary != null &&
        (!dataLogSummary.getStatus().equalsIgnoreCase(ImportConstants.
        SUCCESS_CODE))) {
      sb.append(dataLogSummary.getError()).append("$");
    }
    else {
      sb.append(" ").append("$");

    }
    sb.append(subformSummary.getOid() == null ? "" : subformSummary.getOid()).
        append("$");
    sb.append("[[ ");
    Collection<Object>  dFMDSummaryColl = subformSummary.getDFMDSummaryColl();
    if(dFMDSummaryColl != null)
    {
      sb.append("~");//create an empty column
    for (Iterator<Object> it = dFMDSummaryColl.iterator(); it.hasNext(); ) {
      DFMDSummary dFMDSummary = (DFMDSummary) it.next();
      String dfmd = buildDFMDStringForSubform(dataLogSummary, dFMDSummary);
      sb.append(dfmd);
    }
    sb.append("~$");
    sb.append(" ]]");
    }
    return sb.toString();
  }

  private String buildDFMDStringForSubform(DataLogSummary dataLogSummary,
                                           DFMDSummary dFMDSummary) {
    StringBuffer sb = new StringBuffer("");
    if (dFMDSummary != null) {
      sb.append(dFMDSummary.getLabel() == null ? "" :
                            dFMDSummary.getLabel()).append("!");
      //DOES THIS GO IN THE SAME COLUMN?
      if (dataLogSummary != null &&
          (!dataLogSummary.getStatus().
           equalsIgnoreCase(ImportConstants.SUCCESS_CODE))) {
        sb.append(dataLogSummary.getError()).append("!");
      }
      else {
        sb.append(" ");

      }
    }
    return sb.toString();
  }

  private String buildDFMDString(DataLogSummary dataLogSummary,
                                 DFMDSummary dFMDSummary) {
    StringBuffer sb = new StringBuffer("");
    if (dataLogSummary != null && dFMDSummary != null) {
      sb.append(dataLogSummary.getStatus() == null ? "" :
                dataLogSummary.getStatus()).append("$");
      sb.append(dFMDSummary.getLabel() == null ? "" : dFMDSummary.getLabel()).
          append("$");
      sb.append(dFMDSummary.getPageSet() == null ? "" : dFMDSummary.getPageSet()).
          append("$");
      if (dataLogSummary != null &&
          (!dataLogSummary.getStatus().
           equalsIgnoreCase(ImportConstants.SUCCESS_CODE))) {
        sb.append(dataLogSummary.getError()).append("$");
      }
      else {
        sb.append(" ").append("$");
      }
      sb.append(dFMDSummary.getOid() == null ? "" : dFMDSummary.getOid()).
          append("$");
    }
    return sb.toString();
  }

  private String formatDate(java.sql.Timestamp timestamp) {
    Date date = null;
    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a ");
    if (timestamp != null) {
      date = new Date(timestamp.getTime());
    }
    if (date == null) {
      return "";
    }
    else {
      return formatter.format(date);
    }
  }

}
