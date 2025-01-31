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
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.*;
import gov.cdc.nedss.webapp.nbs.action.ldf.helper.SubformHelper;
import gov.cdc.nedss.systemservice.nbssecurity.*;

public class LDFLoad
    extends Action {
  //For logging
  static final LogUtils logger = new LogUtils(LDFLoad.class.getName());

  static final String LOCAL_SOURCE = "i";
  static final String COLLABORATIVE_SOURCE = "j";
  static final String LEGACY_SOURCE = "k";

  public LDFLoad() {
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
    LdfForm ldfForm = (LdfForm) form;
    HttpSession session = request.getSession();

    NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session.getAttribute(
        "NBSSecurityObject");
    boolean securityCheck = nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,
        NBSOperationLookup.LDFADMINISTRATION);
    if (securityCheck != true) {
      session.setAttribute("error", "Failed at security checking.");
      throw new ServletException("Failed at security checking.");
    }

    String sPageID = HTMLEncoder.encodeHtml(request.getParameter("PageID"));
    if (sPageID == null) {
      sPageID = (String) request.getAttribute("PageID");
    }

    if (sPageID == null || sPageID.equals("") || sPageID.equals(" ")) {
      sPageID = (String) session.getAttribute("PageID");
    }

    String sPageName = HTMLEncoder.encodeHtml(request.getParameter("page"));
    if (sPageName == null) {
      sPageName = (String) request.getAttribute("page");
    }

    if (sPageName == null || sPageName.equals("") || sPageName.equals(" ")) {
      sPageName = (String) session.getAttribute("page");
    }

    String businessObjectNm = request.getParameter("businessObjectNm");
    if (businessObjectNm == null) {
      businessObjectNm = (String) session.getAttribute("businessObjectNm");
    }
    String conditionCd = request.getParameter("conditionCd");
    if (conditionCd == null) {
      conditionCd = (String) session.getAttribute("conditionCd");
    }
    // Setting the values to session
    request.setAttribute("BO", request.getParameter("page"));
    session.setAttribute("PageID", sPageID);
    session.setAttribute("page", sPageName);
    session.setAttribute("businessObjectNm", businessObjectNm);
    session.setAttribute("conditionCd", conditionCd);

    //Pam Specific Routing of LDFs
    if(sPageID.equals("53") || sPageID.equals("33") || sPageID.equals("300")) {
    	return mapping.findForward("localFields");
    }    
    
    String DSPageName = new String("Manage " + sPageName + " Page");
    request.setAttribute("DSPageName", DSPageName);

    //initialize previous LDFs created by the admin
    Collection<Object>  metaDataColl = getLDFMetaDataFromEJB(sPageID, request, session);
    HashMap<Object,Object> metaDataMap = sortLDFCDFCollection(metaDataColl);
    Collection<Object>  ldfColl = (Collection<Object>) metaDataMap.get("ldfColl");
    ldfForm.setOldCollection((ArrayList<Object> )ldfColl);
    Collection<Object>  cdfColl = (Collection<Object>) metaDataMap.get("cdfColl");
    Collection<Object>  legacyColl = (Collection<Object>) metaDataMap.get("legacyColl");

    if (session.getAttribute("previewBack") != null &&
        session.getAttribute("previewBack").equals("true")) {
      ldfColl = (Collection<Object>) session.getAttribute("ldfColl");
    }

    if (ldfColl != null && ldfColl.size() > 0) {
      String ldfString = convertToString(ldfColl, LOCAL_SOURCE);
      request.setAttribute("parsedLDFString", ldfString);
    }

    if (cdfColl != null && cdfColl.size() > 0) {
      String cdfString = convertToString(cdfColl, COLLABORATIVE_SOURCE);
      request.setAttribute("parsedCollabString", cdfString);
    }

    if (legacyColl != null && legacyColl.size() > 0) {
      String legacyString = convertToString(legacyColl, LEGACY_SOURCE);
      request.setAttribute("parsedLegacyString", legacyString);
    }

    //creates subform tabs in admin page
    try {
      makeSubformsForRequest(businessObjectNm, conditionCd, request);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    initializeDropDown(request);
    return mapping.findForward("next");

  }

  /**
   * 3/31/04:RK: Logic moved to CachedDropDownValues for consistency
   * @param treemap - stores key value information from database
   * @return the parsed treemap for xsl translation

  private String getLdfSrt(TreeMap treemap) {
    final int ALLOWED_LENGTH = 85;

    StringBuffer codedValues = new StringBuffer("");

    StringBuffer sbuff = new StringBuffer("");
    TreeMap<Object,Object> treeMap = null;

    try {
      treeMap = treemap;
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    if (treeMap != null && treeMap.values() != null) {

      Set s = new TreeSet(treeMap.values());
     Iterator<Object>  it = s.iterator();

      while (it.hasNext()) {

        String sortedValue = (String) it.next();
       Iterator<Object>  anIterator = treeMap.entrySet().iterator();

        while (anIterator.hasNext()) {

          Map.Entry map = (Map.Entry) anIterator.next();

          if ( (String) map.getValue() == sortedValue) {

            if ( ( (String) map.getValue()).equalsIgnoreCase("Other")) {
              String key = (String) map.getKey();
              String value = (String) map.getValue();
              value = (value.length() >= ALLOWED_LENGTH ? value.substring(0,ALLOWED_LENGTH):value);
              sbuff.append(key.trim()).append(NEDSSConstants.SRT_PART).append(
                  value.trim())
                  .append(NEDSSConstants.SRT_LINE);
            }
            else {
              String key = (String) map.getKey();
              String value = (String) map.getValue();
              value = (value.length() >= ALLOWED_LENGTH ? value.substring(0,ALLOWED_LENGTH):value);
              codedValues.append(key.trim()).append(NEDSSConstants.SRT_PART).
                  append(value.trim())
                  .append(NEDSSConstants.SRT_LINE);
            }
          }
        }
      }
    }

    codedValues.append(sbuff);

    return codedValues.toString();
  }
********************/
  private String buildBatchString(StateDefinedFieldMetaDataDT metaDataDT,
                                  String sourceType) {
    StringBuffer singleMetaDataBS = new StringBuffer("");
    if (metaDataDT.getDataType() != null &&
        metaDataDT.getDataType().equalsIgnoreCase("Hidden")) {
      metaDataDT.setStatusCd("I");
    }
    if (metaDataDT.getCachedCodeSetNm() != null &&
        metaDataDT.getCachedCodeSetNm().trim() != "") {
      metaDataDT.setDataType(metaDataDT.getCachedDataType());
      metaDataDT.setCodeSetNm(metaDataDT.getCachedCodeSetNm());
    }

    //label
    singleMetaDataBS.append("ldf").append(sourceType).append("[").append(
        sourceType).append("].labelTxt").
        append(
        NEDSSConstants.BATCH_PART);
    singleMetaDataBS.append(metaDataDT.getLabelTxt() == null ? "" :
                            metaDataDT.getLabelTxt());
    singleMetaDataBS.append(NEDSSConstants.BATCH_SECT);

    //display width
    singleMetaDataBS.append("ldf").append(sourceType).append("[").append(
        sourceType).append("].fieldSize").
        append(
        NEDSSConstants.BATCH_PART);
    singleMetaDataBS.append(metaDataDT.getFieldSize() == null ? "" :
                            metaDataDT.getFieldSize());
    singleMetaDataBS.append(NEDSSConstants.BATCH_SECT);

    //type
    singleMetaDataBS.append("ldf").append(sourceType).append("[").append(
        sourceType).append("].dataType").
        append(
        NEDSSConstants.BATCH_PART);
    singleMetaDataBS.append(metaDataDT.getDataType() == null ? "" :
                            metaDataDT.getDataType());
    singleMetaDataBS.append(NEDSSConstants.BATCH_SECT);

    //SRT code set
    singleMetaDataBS.append("ldf").append(sourceType).append("[").append(
        sourceType).append("].codeSetNm").
        append(
        NEDSSConstants.BATCH_PART);
    singleMetaDataBS.append(metaDataDT.getCodeSetNm() == null ? "" :
                            metaDataDT.getCodeSetNm());
    singleMetaDataBS.append(NEDSSConstants.BATCH_SECT);

    //Validation type
    singleMetaDataBS.append("ldf").append(sourceType).append("[").append(
        sourceType).append("].validationTxt").
        append(
        NEDSSConstants.BATCH_PART);
    singleMetaDataBS.append(metaDataDT.getValidationTxt() == null ? "" :
                            metaDataDT.getValidationTxt());
    singleMetaDataBS.append(NEDSSConstants.BATCH_SECT);

    //validation java script
    singleMetaDataBS.append("ldf").append(sourceType).append("[").append(
        sourceType).append(
        "].validationJscriptTxt").append(
        NEDSSConstants.BATCH_PART);
    singleMetaDataBS.append(metaDataDT.getValidationJscriptTxt() == null ?
                            "" :
                            metaDataDT.getValidationJscriptTxt());
    singleMetaDataBS.append(NEDSSConstants.BATCH_SECT);

    //required
    singleMetaDataBS.append("ldf").append(sourceType).append("[").append(
        sourceType).append("].requiredInd").
        append(
        NEDSSConstants.BATCH_PART);
    singleMetaDataBS.append(metaDataDT.getRequiredInd() == null ? "" :
                            metaDataDT.getRequiredInd());
    singleMetaDataBS.append(NEDSSConstants.BATCH_SECT);

    //Display order
    singleMetaDataBS.append("ldf").append(sourceType).append("[").append(
        sourceType).append(
        "].displayOrderNbr").append(
        NEDSSConstants.BATCH_PART);
    singleMetaDataBS.append(metaDataDT.getDisplayOrderNbr() == null ?
                            new Integer(0) :
                            metaDataDT.getDisplayOrderNbr());
    singleMetaDataBS.append(NEDSSConstants.BATCH_SECT);

    //Administrative Comments
    singleMetaDataBS.append("ldf").append(sourceType).append("[").append(
        sourceType).append("].adminComment").
        append(
        NEDSSConstants.BATCH_PART);
    singleMetaDataBS.append(metaDataDT.getAdminComment() == null ? "" :
                            metaDataDT.getAdminComment());
    singleMetaDataBS.append(NEDSSConstants.BATCH_SECT);

    //National Identifier
    singleMetaDataBS.append("ldf").append(sourceType).append("[").append(
        sourceType).append("].cdcNationalId").
        append(
        NEDSSConstants.BATCH_PART);
    singleMetaDataBS.append(metaDataDT.getCdcNationalId() == null ? "" :
                            metaDataDT.getCdcNationalId());
    singleMetaDataBS.append(NEDSSConstants.BATCH_SECT);

    //Object Identifier (OID)
    singleMetaDataBS.append("ldf").append(sourceType).append("[").append(
        sourceType).append("].ldfOid").
        append(
        NEDSSConstants.BATCH_PART);
    singleMetaDataBS.append(metaDataDT.getLdfOid() == null ? "" :
                            metaDataDT.getLdfOid());
    singleMetaDataBS.append(NEDSSConstants.BATCH_SECT);

    //NND message indicator
    singleMetaDataBS.append("ldf").append(sourceType).append("[").append(
        sourceType).append("].nndInd").
        append(
        NEDSSConstants.BATCH_PART);
    singleMetaDataBS.append(metaDataDT.getNndInd() == null ? "" :
                            metaDataDT.getNndInd());
    singleMetaDataBS.append(NEDSSConstants.BATCH_SECT);

    //ldf uid
    singleMetaDataBS.append("ldf").append(sourceType).append("[").append(
        sourceType).append("].ldfUid").
        append(
        NEDSSConstants.BATCH_PART);
    singleMetaDataBS.append(metaDataDT.getLdfUid() == null ? new Long(0) :
                            metaDataDT.getLdfUid());
    singleMetaDataBS.append(NEDSSConstants.BATCH_SECT);

    //status code
    singleMetaDataBS.append("ldf").append(sourceType).append("[").append(
        sourceType).append("].statusCd").
        append(
        NEDSSConstants.BATCH_PART);
    singleMetaDataBS.append(metaDataDT.getStatusCd() == null ? "A" :
                            metaDataDT.getStatusCd());
    singleMetaDataBS.append(NEDSSConstants.BATCH_SECT);

    //version ctrl nbr
    singleMetaDataBS.append("ldf").append(sourceType).append("[").append(
        sourceType).append(
        "].versionCtrlNbr").append(
        NEDSSConstants.BATCH_PART);
    singleMetaDataBS.append(metaDataDT.getVersionCtrlNbr());
    singleMetaDataBS.append(NEDSSConstants.BATCH_SECT);

    //classCd
    singleMetaDataBS.append("ldf").append(sourceType).append("[").append(
        sourceType).append(
        "].classCd").append(
        NEDSSConstants.BATCH_PART);
    singleMetaDataBS.append(metaDataDT.getClassCd());
    singleMetaDataBS.append(NEDSSConstants.BATCH_SECT);

    // end of row delimiter
    singleMetaDataBS.append(NEDSSConstants.BATCH_LINE);

    return singleMetaDataBS.toString();

  }

  private void makeSubformsForRequest(String businessObject, String conditionCd,
                                      HttpServletRequest req) throws Exception {
    SubformHelper helper = SubformHelper.getInstance();
    helper.makeSubformTabsForAdminPage(businessObject, conditionCd, req);
  }

  private Collection<Object>  getLDFMetaDataFromEJB(String sPageID,
                                           HttpServletRequest request,
                                           HttpSession session) {
    Collection<Object>  metaDataColl = null;
    MainSessionCommand msCommand = null;
    String sBeanJndiName = JNDINames.LDFMetaData_EJB;
    String sMethod = "getLDFMetaDataByPageId";
    Object[] oParams = {
        sPageID};
    try {
      MainSessionHolder holder = new MainSessionHolder();
      msCommand = holder.getMainSessionCommand(session);
      logger.info("mscommand in BaseLDF class is: " +
                  msCommand);
      ArrayList<?> resultUIDArr = new ArrayList<Object> ();

      resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);

      if ( (resultUIDArr != null) && (resultUIDArr.size() > 0)) {
        metaDataColl = (ArrayList<Object> ) resultUIDArr.get(0);
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return metaDataColl;
  }

  private HashMap<Object,Object> sortLDFCDFCollection(Collection<Object> metaDataColl) {
    HashMap<Object,Object> returnMap = new HashMap<Object,Object>();
    ArrayList<Object> ldfColl = new ArrayList<Object> ();
    ArrayList<Object> cdfColl = new ArrayList<Object> ();
    ArrayList<Object> legacyColl = new ArrayList<Object> ();

   Iterator<Object>  it = metaDataColl.iterator();
    while (it.hasNext()) {
      StateDefinedFieldMetaDataDT metaDataDT = (StateDefinedFieldMetaDataDT)
          it.next();
      if (metaDataDT.getClassCd() != null &&
          metaDataDT.getClassCd().equalsIgnoreCase("STATE")) {
        ldfColl.add(metaDataDT);

      }
      else if (metaDataDT.getClassCd() != null &&
               metaDataDT.getClassCd().equalsIgnoreCase("CDC")) {
        cdfColl.add(metaDataDT);
      }
      else if (metaDataDT.getClassCd() != null &&
               metaDataDT.getClassCd().equalsIgnoreCase("DM")) {
        legacyColl.add(metaDataDT);
      }
    }

    returnMap.put("ldfColl", ldfColl);
    returnMap.put("cdfColl", cdfColl);
    returnMap.put("legacyColl", legacyColl);
    return returnMap;
  }

  private void initializeDropDown(HttpServletRequest request) {

    //SRTMap srtm = null;
    Map<Object,Object> map = new TreeMap<Object, Object>();
   // NEDSSConstants Constants = new NEDSSConstants();

   // NedssUtils nu = new NedssUtils();
    try {

/*   3/31/04: Created a method getLDFDropDowns in CachedDropDownValues to be consistent
     and also restrict dropdown values length to 85 for UI not to blowup
      Object objref = nu.lookupBean(Constants.SRT_MAP);

      //logger.debug("objref " + objref);
      SRTMapHome home = (SRTMapHome) PortableRemoteObject.narrow(objref,
          SRTMapHome.class);
      srtm = home.create();
*/
      CachedDropDownValues cache = new CachedDropDownValues();
      request.setAttribute("SrtDropDownsSelects",
                           cache.getLDFDropdowns());
    }
    catch (Exception e) {
      e.printStackTrace();
    }

  }

  private String convertToString(Collection<Object> metaDataColl,
                                 String source) {

    CachedDropDownValues srtValues = new CachedDropDownValues();
    StringBuffer metaDataBuffer = new StringBuffer("");
   Iterator<Object>  it = metaDataColl.iterator();
    while (it.hasNext()) {
      StateDefinedFieldMetaDataDT metaDataDT = (StateDefinedFieldMetaDataDT)
          it.next();
      metaDataBuffer.append(buildBatchString(metaDataDT,
                                             source));
    }
    if (metaDataBuffer.length() < 1) {
      metaDataBuffer.append(" ");
    }
    String returnString = metaDataBuffer.toString();
    return returnString;
  }

}
