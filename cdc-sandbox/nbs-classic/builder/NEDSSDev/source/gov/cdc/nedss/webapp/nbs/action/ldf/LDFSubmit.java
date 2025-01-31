package gov.cdc.nedss.webapp.nbs.action.ldf;

/**
 * Title:        Actions
 * Description:  LDF Submit
 * Copyright:    Copyright (c) 2001
 * Company:      CSC
 * @author
 * @version 1.0
 */

import java.io.*;
import javax.rmi.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

import org.apache.struts.action.*;
import gov.cdc.nedss.ldf.ejb.ldfmetadataejb.bean.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.webapp.nbs.action.ldf.helper.LdfHelper;
import gov.cdc.nedss.webapp.nbs.form.ldf.*;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;
import gov.cdc.nedss.ldf.dt.*;

import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.systemservice.util.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;

public class LDFSubmit
    extends BaseLdf {
  //For logging
  static final LogUtils logger = new LogUtils(LDFSubmit.class.getName());
  private CachedDropDownValues cdv = new CachedDropDownValues();

  public LDFSubmit() {
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
  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws IOException,
      ServletException {
    HttpSession session = request.getSession();
    NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session.getAttribute(
        "NBSSecurityObject");
    boolean securityCheck = nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,
        NBSOperationLookup.LDFADMINISTRATION);
    if (securityCheck != true) {
      session.setAttribute("error", "Failed at security checking.");
      throw new ServletException("Failed at security checking.");
    }

    String sBusinessObject = request.getParameter("sBusinessObjectNm");
    if (sBusinessObject == null || sBusinessObject.trim().equalsIgnoreCase("")) {
      sBusinessObject = (String) request.getAttribute("sBusinessObjectNm");
    }

    String sConditionCd = request.getParameter("sConditionCd");
    if (sConditionCd == null || sConditionCd.trim().equalsIgnoreCase("")) {
      sConditionCd = (String) request.getAttribute("sConditionCd");
    }
    String pageName = null;
    if (request.getAttribute("pageName") != null) {
      pageName = request.getAttribute("pageName").toString();
    }
    else {
      pageName = request.getParameter("pageName");

    }
    String PageID = request.getParameter("PageID").toString();
    if (PageID == null || PageID.trim().equalsIgnoreCase("")) {
      PageID = (String) request.getAttribute("PageID");
    }

    String contextAction = request.getParameter("contextAction").toString();

    logger.debug(" business object is =========== " + sBusinessObject);
    String sPageID = request.getParameter("PageID");
    logger.debug(" page  ID is =========== " + sPageID);
    LdfForm ldfForm = (LdfForm) form;

    try {

      NedssUtils utils = new NedssUtils();
      //store the meta data
      //initialize the page id attribute
      Collection<StateDefinedFieldMetaDataDT>  ldfColl = ldfForm.getLdfCollection();
      Collection<Object>  oldColl = ldfForm.getOldCollection();
      Collection<Object>  newMetaColl = new ArrayList<Object> ();
      HashMap<Object,Object> map = new HashMap<Object,Object>();
      if (ldfColl != null) {
       Iterator<StateDefinedFieldMetaDataDT>  iterLdfColl = ldfColl.iterator();
        while (iterLdfColl.hasNext()) {
          StateDefinedFieldMetaDataDT metaDataDT = (StateDefinedFieldMetaDataDT)
              iterLdfColl.next();

          //set the page id attribute
          	metaDataDT.setConditionCd(sConditionCd);


          	String condDesc = cdv.getConditionDesc(sConditionCd);
          	//add Condition Desc Txt in MetaData(civil00012892)
          	metaDataDT.setConditionDescTxt(condDesc);

          metaDataDT.setClassCd("STATE");
          if (sBusinessObject != null && metaDataDT.getBusinessObjNm() == null &&
              sBusinessObject != "") {
            metaDataDT.setBusinessObjNm(sBusinessObject);
          }
          //Commented out the following line to fix defect civil00010482
        //  metaDataDT.setConditionDescTxt(pageName);
          metaDataDT.setLdfPageId(sPageID);
          if (metaDataDT.getDataType() == null ||
              metaDataDT.getDataType().trim().equals("")) {
            continue;
          }

          //check if deleted and delete
          if (metaDataDT.getLdfUid() != null &&
              metaDataDT.getLdfUid() != 0
              && metaDataDT.getStatusCd() != null
              && metaDataDT.getStatusCd().equals("I")) {

            metaDataDT.setActiveInd("N");
            metaDataDT.setRecordStatusCd("LDF_UPDATE");

            metaDataDT.setItNew(false);
            metaDataDT.setItDelete(true);
            metaDataDT.setItDirty(false);
            newMetaColl.add(metaDataDT);
          }
          if ( (metaDataDT.getLdfUid() == null ||
                metaDataDT.getLdfUid() == new Long(0) ||
                metaDataDT.getLdfUid().equals(""))
              && metaDataDT.getStatusCd() != null
              && metaDataDT.getStatusCd().equals("I")) {
            logger.debug("removing metaDataDT with null uid and status I");

            metaDataDT.setActiveInd(NEDSSConstants.ACTIVE_FALSE_LDF);
            iterLdfColl.remove();
          }

          // don't save same thing over again, this is temp, need to implement status flags
          if (metaDataDT.getLdfUid() != null && metaDataDT.getStatusCd() != null &&
              !metaDataDT.getStatusCd().equals("I")) {
            logger.debug("don't duplicate metadata dt");
            metaDataDT.setActiveInd(NEDSSConstants.ACTIVE_TRUE_LDF);
                /*Storing the existing collection to the map to check woth old Collection<Object>  */
            map.put(metaDataDT.getLdfUid(), metaDataDT);

          } //iterLdfColl.remove();
          if ( (metaDataDT.getLdfUid() == null ||
                (metaDataDT.getLdfUid() != null &&
                 metaDataDT.getLdfUid().compareTo(new Long(0)) == 0)) &&
              (metaDataDT.getStatusCd() != null &&
               !metaDataDT.getStatusCd().equals("I"))) {
            if (!metaDataDT.getValidationTxt().equalsIgnoreCase("JS") &&
                metaDataDT.getValidationJscriptTxt() != null) {
              metaDataDT.setValidationJscriptTxt(null);
            }

            if (sBusinessObject != null && metaDataDT.getBusinessObjNm() != null &&
                sBusinessObject != "") {
              if (metaDataDT.getDisplayOrderNbr() == null) {
                metaDataDT.setDisplayOrderNbr(new Integer(0));
              }
            }

            metaDataDT.setItNew(true);
            newMetaColl.add(metaDataDT);
          }
        }

      }
      Collection<Object>  mColl = this.setDirtyFlag(oldColl, map, utils);
      System.out.println("\n Coll size Updated " + mColl.size());
      newMetaColl.addAll(mColl);
      System.out.println("\n Total Coll size " + newMetaColl.size());
     // HttpSession session = request.getSession();
      MainSessionCommand msCommand = null;
      String sBeanJndiName = JNDINames.LDFMetaData_EJB;
      String sMethod = "setLDFMetaData";
      Object[] oParams = {
          newMetaColl};
      Collection<Object>  metaDataColl = null;
      logger.debug("setting ldfMetaData to LDFMetaDataEJB, via mainsession");

      MainSessionHolder holder = new MainSessionHolder();
      msCommand = holder.getMainSessionCommand(session);
      logger.info("mscommand in LDFSubmit class is: " +
                  msCommand);
      ArrayList<Object> resultUIDArr = new ArrayList<Object> ();
      msCommand.processRequest(sBeanJndiName, sMethod, oParams);

      // XZ (08/02/2004) replace the code block with central implementation
      // so that we may change the implementation later.
      LdfHelper.resetXSPPageCache(request.getSession());
//      ServletContext context = request.getSession().getServletContext();
//      Hashtable xspPageCache = new Hashtable();
//      context.setAttribute("xSPPageCache", xspPageCache);
      request.getSession().setAttribute("PageID", null);
      request.getSession().setAttribute("sConditionCd", null);
      request.getSession().setAttribute("sBusinessObjectNm", null);
      request.getSession().setAttribute("pageName", pageName);

      ldfForm.reset();

    }
    catch (Exception e) {

      e.printStackTrace();
    }

    request.getSession().setAttribute("PageID", null);
    request.getSession().setAttribute("sConditionCd", null);
    request.getSession().setAttribute("sBusinessObjectNm", null);
    request.getSession().setAttribute("pageName", null);
    request.getSession().setAttribute("previewBack", null);


    if (contextAction != null && contextAction.equalsIgnoreCase("save")) {

      String path = "/LDFLoad.do?pageID=" + PageID + "&businessObjectNm=" +
          sBusinessObject;
      if (sConditionCd != null && sConditionCd.trim().length() > 0) {
        path += "&conditionCd=" + sConditionCd;
      }
      return new ActionForward(path);
    }
    else {
      return mapping.findForward("next");
    }
    //return new ActionForward(/nbs/LDFAdminLoad);

  }

  private Collection<Object>  setDirtyFlag(Collection<Object> oldColl, HashMap<Object,Object> map,
                                  NedssUtils utils) {
    Collection<Object>  metaColl = new ArrayList<Object> ();
    if (oldColl != null) {
     Iterator<Object>  iter = oldColl.iterator();
      while (iter.hasNext()) {
        StateDefinedFieldMetaDataDT metaDataDT = (StateDefinedFieldMetaDataDT)
            iter.next();
        if (metaDataDT.getStatusCd() == null) {
          metaDataDT.setStatusCd("A");
        }
        if (map.containsKey(metaDataDT.getLdfUid())) {
          //  System.out.println("\n\n metaDataDT.getactive Ind old = "+metaDataDT.getActiveInd());
          //  System.out.println("\n\n metaDataDT.getactive Ind new = "+((StateDefinedFieldMetaDataDT)map.get(metaDataDT.getLdfUid())).getActiveInd());
          boolean bol = utils.equals(metaDataDT, (map.get(metaDataDT.getLdfUid())),
                                     StateDefinedFieldMetaDataDT.class);
          if (!utils.equals(metaDataDT, (map.get(metaDataDT.getLdfUid())),
                            StateDefinedFieldMetaDataDT.class)) {
            ( (StateDefinedFieldMetaDataDT) map.get(metaDataDT.getLdfUid())).
                setItDirty(true);
            ( (StateDefinedFieldMetaDataDT) map.get(metaDataDT.getLdfUid())).
                setItNew(false);
            ( (StateDefinedFieldMetaDataDT) map.get(metaDataDT.getLdfUid())).
                setActiveInd(NEDSSConstants.ACTIVE_TRUE_LDF);
            if(!((StateDefinedFieldMetaDataDT) map.get(metaDataDT.getLdfUid())).getValidationTxt().equalsIgnoreCase("JS")){
              ((StateDefinedFieldMetaDataDT) map.get(metaDataDT.getLdfUid())).setValidationJscriptTxt(null);
            }

          }
          else {
            ( (StateDefinedFieldMetaDataDT) map.get(metaDataDT.getLdfUid())).
                setItDirty(false);
            ( (StateDefinedFieldMetaDataDT) map.get(metaDataDT.getLdfUid())).
                setItNew(false);
          }
        }
        if (map.get(metaDataDT.getLdfUid()) != null) {
          metaColl.add( ( (StateDefinedFieldMetaDataDT) map.get(metaDataDT.
              getLdfUid())));
        }
      }
    }
    return metaColl;
  }

}