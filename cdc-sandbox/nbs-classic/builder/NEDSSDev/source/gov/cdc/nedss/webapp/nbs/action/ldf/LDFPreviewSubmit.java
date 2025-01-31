package gov.cdc.nedss.webapp.nbs.action.ldf;

/**
 * Title:        Actions
 * Description:  LDF Preview Submit
 * Copyright:    Copyright (c) 2001
 * Company:      CSC
 * @author
 * @version 1.0
 */

import java.io.*;
import java.lang.reflect.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;
import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.ldf.dt.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.webapp.nbs.form.ldf.*;
import gov.cdc.nedss.entity.organization.vo.OrganizationVO;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.proxy.ejb.interventionproxyejb.vo.VaccinationProxyVO;
import gov.cdc.nedss.proxy.ejb.treatmentproxyejb.vo.TreatmentProxyVO;

import gov.cdc.nedss.ldf.vo.LdfBaseVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.LabResultProxyVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.MorbidityProxyVO;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.InvestigationProxyVO;
import gov.cdc.nedss.webapp.nbs.action.ldf.helper.SubformHelper;
import gov.cdc.nedss.systemservice.nbssecurity.*;

public class LDFPreviewSubmit
    extends BaseLdf {
  //For logging
  static final LogUtils logger = new LogUtils(LDFPreviewSubmit.class.getName());
  static StringBuffer sv;
  private SubformHelper helper = SubformHelper.getInstance();
  private String specialChars = "^^^^^^";
  public LDFPreviewSubmit() {
  }

  public ActionForward execute(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response) throws IOException, ServletException {

    LdfForm ldfForm = (LdfForm) form;
    ArrayList<StateDefinedFieldMetaDataDT> previewMetaDataColl = new ArrayList<StateDefinedFieldMetaDataDT> ();

    ArrayList<StateDefinedFieldMetaDataDT> ldfMetaDataColl = (ArrayList<StateDefinedFieldMetaDataDT> ) ldfForm.getLdfCollection();
    if(ldfMetaDataColl != null){
      for(Iterator<StateDefinedFieldMetaDataDT> it = ldfMetaDataColl.iterator(); it.hasNext();){
        StateDefinedFieldMetaDataDT metaDataDT = (
              StateDefinedFieldMetaDataDT)
              it.next();
          if (metaDataDT.getClassCd() == null|| metaDataDT.getClassCd().trim().equals("")) {
            metaDataDT.setClassCd("STATE");
          }
      }
      previewMetaDataColl.addAll(ldfMetaDataColl);
    }
    HttpSession session = request.getSession(true);
    session.setAttribute("ldfColl", ldfMetaDataColl);

    ArrayList<StateDefinedFieldMetaDataDT> cdfMetaDataColl = (ArrayList<StateDefinedFieldMetaDataDT> ) ldfForm.getCdfCollection();
    ArrayList<StateDefinedFieldMetaDataDT> legacyMetaDataColl = (ArrayList<StateDefinedFieldMetaDataDT> ) ldfForm.getLegacyCollection();

    //combine three types of custom fields into one collection

    if(cdfMetaDataColl != null)
    previewMetaDataColl.addAll(cdfMetaDataColl);
    if(legacyMetaDataColl != null)
    previewMetaDataColl.addAll(legacyMetaDataColl);

    NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session.getAttribute(
        "NBSSecurityObject");
    boolean securityCheck = nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,
        NBSOperationLookup.LDFADMINISTRATION);
    if (securityCheck != true) {
      session.setAttribute("error", "Failed at security checking.");
      throw new ServletException("Failed at security checking.");
    }

    Collection<Object>  newMetaColl = new ArrayList<Object> ();
    StringBuffer metaDataBuffer = new StringBuffer("");

    String sBusinessObject = HTMLEncoder.encodeHtml(request.getParameter("BusinessObject"));
    if (sBusinessObject == null || sBusinessObject.equals("") || sBusinessObject.equals("null") || 
        sBusinessObject.equals(" ")) {
      sBusinessObject = HTMLEncoder.encodeHtml((String) session.getAttribute("businessObjectNm"));
    }
    String conditionCd = HTMLEncoder.encodeHtml(request.getParameter("conditionCd"));
    if (conditionCd == null || conditionCd.equals("") || conditionCd.equals("null") || conditionCd.equals(" ")) {
      conditionCd = HTMLEncoder.encodeHtml((String) session.getAttribute("conditionCd"));

    }

    try {

      helper.updateSubformMetaDataForPreview(sBusinessObject, conditionCd,
                                             previewMetaDataColl, request);

    } catch(Exception e) {
    }

    if (previewMetaDataColl != null) {
      /* Need to do set '0' for null DisplayOrderNumber before Sort, else the CompareTo for DisplayOrderNumber will
       give you NullPointerException  */
     Iterator<StateDefinedFieldMetaDataDT>  it = previewMetaDataColl.iterator();
      while (it.hasNext()) {
        StateDefinedFieldMetaDataDT metaDataDT = (StateDefinedFieldMetaDataDT)
            it.next();
        if (metaDataDT.getDisplayOrderNbr() == null) {
          metaDataDT.setDisplayOrderNbr(new Integer(0));
        }
      }
      /* Sorting the Collection<Object>  using Comparable Interface */
      Collections.sort(previewMetaDataColl);
     Iterator<StateDefinedFieldMetaDataDT>  iter = previewMetaDataColl.iterator();

      while (iter.hasNext()) {

        StateDefinedFieldMetaDataDT metaDataDT = (StateDefinedFieldMetaDataDT)
            iter.next();
        if (sBusinessObject != null && sBusinessObject.trim() != "") {
          metaDataDT.setBusinessObjNm(sBusinessObject);
        }
        if (metaDataDT.getDataType() == null ||
            metaDataDT.getDataType().trim().equals("")) {
          continue;
        }
        if (metaDataDT.getCodeSetNm() != null && !metaDataDT.getCodeSetNm().trim().equals("")) {
          String options = getCodedValues(metaDataDT.getCodeSetNm());
          metaDataDT.setCachedCodeSetNm(metaDataDT.getCodeSetNm());
          metaDataDT.setCodeSetNm(options);
          metaDataDT.setCachedDataType(metaDataDT.getDataType());
        }

        if (metaDataDT.getJavaScriptFunctionName() == null) {
          metaDataDT.setJavaScriptFunctionName("ldfNullValue");
        }

        if (metaDataDT.getStatusCd() != null && metaDataDT.getStatusCd().equalsIgnoreCase("I")) {
          metaDataDT.setDataType("Hidden");
        }
        else {
          newMetaColl.add(metaDataDT);
        }
      }
    }
    try {
     // request.setAttribute("parsedLDFString", metaDataBuffer.toString());
      HashMap<Object,Object> map = new HashMap<Object,Object>();
      boolean flag = false;
      String extXSP = null;
      String extXSP1 = null;

      if (sBusinessObject.equals(NEDSSConstants.ORGANIZATION_LDF)) {
        LdfBaseVO orgVO = new OrganizationVO();
        extXSP = _createXSP(orgVO, newMetaColl, map);
        extXSP1 = getBlankXSP(extXSP);
        request.setAttribute("previewXSP", extXSP1);
      }
      else if (sBusinessObject.equals(NEDSSConstants.PROVIDER_LDF)) {
        LdfBaseVO personVO = new PersonVO();
        extXSP = _createXSP(personVO, newMetaColl, map);
        extXSP1 = getBlankXSP(extXSP);
        request.setAttribute("previewXSP", extXSP1);
      }
      else if (sBusinessObject.equals(NEDSSConstants.VACCINATION_LDF)) {
        LdfBaseVO vaccinationProxyVO = new VaccinationProxyVO();
        extXSP = _createXSP(vaccinationProxyVO, newMetaColl, map);
        extXSP1 = getBlankXSP(extXSP);
        request.setAttribute("previewXSP", extXSP1);
      }
      else if (sBusinessObject.equals(NEDSSConstants.TREATMENT_LDF)) {
        LdfBaseVO treatmentProxyVO = new TreatmentProxyVO();
        extXSP = _createXSP(treatmentProxyVO, newMetaColl, map);
        extXSP1 = getBlankXSP(extXSP);
        request.setAttribute("previewXSP", extXSP1);
      }
      /**
       * @TBD Release 6.0, Commented out as LDF will be planned out as new type of answers
    	else if (sBusinessObject.equals(NEDSSConstants.LABREPORT_LDF)) {
        LdfBaseVO labResultProxyVO = new LabResultProxyVO();
        extXSP = _createXSP(labResultProxyVO, newMetaColl, map);
        extXSP1 = getBlankXSP(extXSP);
        request.setAttribute("previewXSP", extXSP1);
      }*/
      else if (sBusinessObject.equals(NEDSSConstants.MORBREPORT_LDF)) {
        LdfBaseVO morbidityProxyVO = new MorbidityProxyVO();
        extXSP = _createXSP(morbidityProxyVO, newMetaColl, map);
        extXSP1 = getBlankXSP(extXSP);
        request.setAttribute("previewXSP", extXSP1);
      }
      else if (sBusinessObject.equals(NEDSSConstants.INVESTIGATION_LDF)) {
        LdfBaseVO investigationProxyVO = new InvestigationProxyVO();
        extXSP = _createXSP(investigationProxyVO, newMetaColl, map);
        extXSP1 = getBlankXSP(extXSP);
        request.setAttribute("previewXSP", extXSP1);
      }
      else if (sBusinessObject.equals(NEDSSConstants.HOME_PAGE_LDF)) {
          LdfBaseVO baseVO = new LdfBaseVO();
          //extXSP = _createXSP(baseVO, newMetaColl, map);
          //extXSP1 = getBlankXSP(extXSP);
          createLDFsForHomePage(request, newMetaColl, "previewXSP");
          //request.setAttribute("previewXSP", extXSP1);
       }
      else if (!sBusinessObject.equals(NEDSSConstants.INVESTIGATION_LDF) &&
               conditionCd != null) {
        LdfBaseVO investigationProxyVO = new InvestigationProxyVO();
        extXSP = _createXSP(investigationProxyVO, newMetaColl, map);
        extXSP1 = getBlankXSP(extXSP);
        request.setAttribute("previewXSP", extXSP1);
      }
      request.setAttribute("doPreview", "yes");
      Collection<Object>  subforms = helper.extractSubforms(sBusinessObject, null, conditionCd, request);
      if(subforms != null && subforms.size() > 0)
      setSubformValidations(request);
    }
    catch (Exception e) {
      // nothing to be done. You will never get this exception.

    }
    String sPageID = HTMLEncoder.encodeHtml(request.getParameter("PageID"));
    if (sPageID == null || sPageID.equals("") || sPageID.equals(" ")) {
      sPageID = HTMLEncoder.encodeHtml((String) session.getAttribute("PageID"));
    }
    if (sPageID != null) {
      sPageID = sPageID.trim().toLowerCase().replace(' ', '_');
    }

    String sPageName = HTMLEncoder.encodeHtml(request.getParameter("page"));
   if (sPageName == null || sPageName.equals("") || sPageName.equals("null") || sPageName.equals(" ")) {
     sPageName = HTMLEncoder.encodeHtml((String) session.getAttribute("page"));
   }


    if (sBusinessObject != null) {
      sBusinessObject.toLowerCase();
    }
    logger.debug("business Object is: " + sBusinessObject);
    String sPreview = sPageID + "_preview";
    logger.debug("page id is: " + sPageID + " and preview is: " + sPreview);
    // return new ActionForward( diseaseform + "/" +sPageID +"/" + sPreview);

    request.setAttribute("PageID", sPageID);
    request.setAttribute("page", sPageName);
    request.setAttribute("BusinessObject", sBusinessObject);

    session.setAttribute("PageID", sPageID);
    session.setAttribute("page", sPageName);

    session.setAttribute("businessObjectNm", sBusinessObject);
    session.setAttribute("conditionCd", conditionCd);
    session.setAttribute("previewBack", "true");
    if (conditionCd == null || conditionCd.equals("") || conditionCd.equals(" ")) {
      return mapping.findForward(sBusinessObject);
    }
    else if (! (sBusinessObject.equals(NEDSSConstants.INVESTIGATION_LDF)) &&
             (conditionCd != null || !conditionCd.equals("") ||
              !conditionCd.equals(" "))) {
      return mapping.findForward(conditionCd);
    }


   // ldfForm.reset();
    return mapping.findForward(sBusinessObject);
    //return new ActionForward(sBusinessObject);

  }


  private String getBlankXSP(String extXSP) {
    if (extXSP.equalsIgnoreCase(NEDSSConstants.BLANK_XSPTAG_LDF)) {
      return extXSP;
    }
    StringTokenizer st = new StringTokenizer(extXSP, specialChars);
    String arr[] = new String[st.countTokens()];
    StringBuffer buff = new StringBuffer();
    boolean primeNumber = false;
    for (int i = 0; st.hasMoreTokens(); i++) {
      primeNumber = !primeNumber;
      arr[i] = st.nextToken();
      if (primeNumber) {
        buff.append(arr[i]);
      }
      else {
      }
    }

    return buff.toString();
  }

  public void setSubformValidations(HttpServletRequest request) {
    if(sv == null ) {
      sv = new StringBuffer();
      sv.append("<group name=\"Subform Validations\">");
      sv.append("<line>");
      sv.append("<element type=\"raw\">");
      sv.append("<span>");
      sv.append("<table role=\"presentation\" BORDER=\"0\" WIDTH=\"100%\">");
      sv.append("<TR>");
      sv.append("<TD>");
      sv.append(
          "<BUTTON type=\"button\" ONCLICK=\"callValidateData();\">Run Validation Scripts</BUTTON>");
      sv.append("</TD>");
      sv.append("<TD>");
      sv.append(
          "<BUTTON type=\"button\" ONCLICK=\"return callDisableData();\">Run Disable Scripts</BUTTON>");
      sv.append("</TD>");
      sv.append("<TD>");
      sv.append(
          "<BUTTON type=\"button\" ONCLICK=\"return callEnableData();\">Run Enable Scripts</BUTTON>");
      sv.append("</TD>");
      sv.append("</TR>");
      sv.append("<TR>");
      sv.append("<TD>");
      sv.append(
          "<BUTTON type=\"button\" ONCLICK=\"return callLoadData();\">Load Data</BUTTON>");
      sv.append("</TD>");
      sv.append("<TD>");
      sv.append(
          "<BUTTON type=\"button\" ONCLICK=\"return callSaveData();\">Save Data</BUTTON>");
      sv.append("</TD>");
      sv.append("<TD>");
      sv.append(
          "<BUTTON type=\"button\" ONCLICK=\"return callClearData();\">Clear Data</BUTTON>");
      sv.append("</TD>");
      sv.append("</TR>");
      sv.append("</TABLE>");
      sv.append("</span>");
      sv.append("</element>");
      sv.append("</line>");
      sv.append("</group>");
    }
    request.setAttribute("subformValidations", sv.toString());
  }

}
